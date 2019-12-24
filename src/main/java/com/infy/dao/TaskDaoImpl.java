package com.infy.dao;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.infy.entity.HibernateUtility; 
import com.infy.entity.TaskEntity;
import com.infy.entity.User1;
import com.infy.exception.TaskManagerException;
import com.infy.model.Task;
import com.infy.model.TaskStatus;
import com.infy.model.User;


@SuppressWarnings("unchecked")
public class TaskDaoImpl implements TaskDao{
	
	static Logger logger = Logger.getLogger(TaskDaoImpl.class);
	SessionFactory factory;
	Session session;
	private static DecimalFormat df2 = new DecimalFormat("#.##");

	@Override
	public Integer createTask(Task task) throws TaskManagerException, Exception {
		try {
			factory = HibernateUtility.getSessionFactory();

			if(factory!=null)
			{
				session = factory.openSession();	
				session.getTransaction().begin();
				TaskEntity taskEn = new TaskEntity();
				if(task.gettId()!=null)
				{
					taskEn = (TaskEntity) session.get(TaskEntity.class, task.gettId());
					if(taskEn!=null)
						throw new TaskManagerException("Task.Dao.TASK_ALREADY_EXIST");
				}

				taskEn.settExpEff(task.gettExpEff());
				taskEn.settDesc(task.gettDesc());
				taskEn.settOwner(task.gettOwner());
				taskEn.settStatus(task.gettStatus());
				taskEn.settName(task.gettName());
				Integer id = (Integer) session.save(taskEn);
				session.getTransaction().commit();
				return id;					
			}

		}
		catch (HibernateException e) {
			throw new TaskManagerException("User.Task.DATABASE_PROBLEM");

		}
		catch (TaskManagerException e) {
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if(session!=null)
				session.close();
		}


		return null;
	}


	@Override
	public List<Task> getAllTaskByUserId(Integer usrId) throws TaskManagerException, Exception {
		try {
			factory = HibernateUtility.getSessionFactory();

			if(factory!=null)
			{
				session = factory.openSession();
				User1 usrEn = (User1) session.get(User1.class, usrId);
				if(usrEn==null)
					throw new TaskManagerException("User.Dao.USER_NOT_FOUND");
				if(usrEn.getRole().getrId()!=2)
					throw new TaskManagerException("User.Dao.MANAGER_NOT_EXIST");
				String sql = "FROM TaskEntity where tOwner = :usrId and tStatus='NEW'";


				Query<TaskEntity> q = session.createQuery(sql);
				q.setParameter("usrId", usrId);

				List<TaskEntity> taskEns = q.list();

				if(taskEns==null || taskEns.size()==0)
					throw new TaskManagerException("Task.Dao.NO_TASK_BY_USER");
				List<Task> taskList = new ArrayList<Task>();

				for(TaskEntity tE: taskEns)
				{
					Task t = tE.taskEnityToModel();
					taskList.add(t);				
				}

				return taskList;


			}

		}
		catch (HibernateException e) {
			throw new TaskManagerException("User.Task.DATABASE_PROBLEM");

		}
		catch (TaskManagerException e) {
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if(session!=null)
				session.close();
		}		
		return null;
	}

	@Override
	public List<Integer> assignTaskToUser(List<User> usr) throws TaskManagerException, Exception {
		try {
			factory = HibernateUtility.getSessionFactory();

			if(factory!=null)
			{
				List<Integer> ansList = new ArrayList<Integer>();
				session = factory.openSession();
				session.getTransaction().begin();
				for(User us : usr)
				{
					User1 usrEn = (User1) session.get(User1.class, us.getUsrId());
					if(usrEn==null)
						throw new TaskManagerException("User.Dao.USER_NOT_FOUND");
					List<Task> tks = us.getTasks();
					if(tks==null || tks.size()==0)
						throw new TaskManagerException("Task.Dao.TASK_NOT_PRESENT");
					List<TaskEntity> taskEn = usrEn.getTask();
					for(Task t : tks)
					{
						TaskEntity tE = (TaskEntity) session.get(TaskEntity.class, t.gettId());
						if(tE==null)
							throw new TaskManagerException("Task.Dao.TASK_ID_NOT_EXIST");
						System.out.println(tE.gettName());
						if(!tE.gettStatus().equals(TaskStatus.NEW))
							throw new TaskManagerException("Task.Dao.TASK_ALREADY_ASSIGNED");
						if(null!=t.gettAllDate())
						{
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");							
							tE.settAllDate(LocalDateTime.parse(t.gettAllDate(), formatter));
						}

						tE.settStatus(t.gettStatus()); 					
						taskEn.add(tE);
					}			
//					usrEn.getTask().add(taskEn);
//					session.save(usrEn);
					ansList.add(us.getUsrId());
				}
				session.getTransaction().commit();				
				return ansList;														
			}

		}
		catch (HibernateException e) {
			throw new TaskManagerException("User.Task.DATABASE_PROBLEM");

		}
		catch (TaskManagerException e) {
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if(session!=null)
				session.close();
		}		
		return null;
	}


	@Override
	public List<User> getAllPendingTask(Integer usrId) throws TaskManagerException, Exception {
		try {
			factory = HibernateUtility.getSessionFactory();
			if(factory!=null)
			{
				session = factory.openSession();
				User1 usrEn = (User1) session.get(User1.class, usrId);
				if(usrEn==null)
					throw new TaskManagerException("User.Dao.USER_NOT_FOUND");
				if(usrEn.getRole().getrId()!=2)
					throw new TaskManagerException("User.Dao.MANAGER_NOT_EXIST");

				String sql = "select DISTINCT e from User1 e left join e.task t  where t.tOwner=:usrId and t.tStatus='PENDING_TO_VERFIFY'";				
				Query<User1> q = session.createQuery(sql);
//				Query<User1> q = session.createSQLQuery(sql);
				
				q.setParameter("usrId", usrId);

				List<User1> usrEnLi = q.list();

				if(usrEnLi==null || usrEnLi.size()==0)
					throw new TaskManagerException("Task.Dao.NO_TASK_FOR_UPDATTION");
				List<User> usrList = new ArrayList<User>();

				for(User1 urE : usrEnLi)
				{				

					User usr = urE.userEntityToModel();
					if( urE.getTask().size()!=0)
					{
						List<TaskEntity> lTask = urE.getTask();
						List<Task> taskL = new ArrayList<Task>();
						for(TaskEntity tE:lTask)
						{
							if(tE.gettStatus().equals(TaskStatus.COMPLETED) )
								continue;							
							Task t =  tE.taskEnityToModel();
							taskL.add(t);							
						}
						usr.setTasks(taskL);
					}
					usrList.add(usr);				
				}
				return usrList;


			}

		}
		catch (HibernateException e) {
			throw new TaskManagerException("User.Task.DATABASE_PROBLEM");

		}
		catch (TaskManagerException e) {
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if(session!=null)
				session.close();
		}		
		return null;

	}


	@Override
	public List<Integer> verifyPendingTask(Integer usrId, List<User> us) throws TaskManagerException, Exception {
		try {
			factory = HibernateUtility.getSessionFactory();

			if(factory!=null)
			{
				List<Integer> ansList = new ArrayList<Integer>();
				session = factory.openSession();
				session.getTransaction().begin();
				User1 usrEn = (User1) session.get(User1.class, usrId);
				if(usrEn==null)
					throw new TaskManagerException("User.Dao.USER_NOT_FOUND");
				if(usrEn.getRole().getrId()!=2)
					throw new TaskManagerException("User.Dao.MANAGER_NOT_EXIST");

				for(User usr:us)
				{
					List<Task> tsl = usr.getTasks();
					if(tsl==null || tsl.size()==0)
						throw new TaskManagerException("Task.Dao.TASK_NOT_PRESENT");
					for(Task t: tsl)
					{
						TaskEntity tE = (TaskEntity) session.get(TaskEntity.class, t.gettId());
						tE.settStatus(TaskStatus.COMPLETED);
						ansList.add(tE.gettId());
					}						
				}
				session.getTransaction().commit();
				return ansList;														
			}

		}
		catch (HibernateException e) {
			throw new TaskManagerException("User.Task.DATABASE_PROBLEM");

		}
		catch (TaskManagerException e) {
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if(session!=null)
				session.close();
		}		
		return null;

	}


	@Override
	public List<Integer> userProcessedTask(Integer usrId, List<Task> tasks) throws TaskManagerException, Exception {
		try {
			factory = HibernateUtility.getSessionFactory();

			if(factory!=null)
			{
				session = factory.openSession();	
				session.getTransaction().begin();
				User1 usrEn = (User1) session.get(User1.class, usrId);
				if(usrEn==null)
					throw new TaskManagerException("User.Dao.USER_NOT_FOUND");
				if(usrEn.getRole().getrId()!=3)
					throw new TaskManagerException("User.Dao.NO_AUTHORITY");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				List<Integer> ansList = new ArrayList<Integer>();
				
				for(Task t:tasks)
				{
					TaskEntity tE = (TaskEntity) session.get(TaskEntity.class, t.gettId());
					if(tE==null)
						throw new TaskManagerException("Task.Dao.TASK_ID_NOT_EXIST");
					tE.settStatus(TaskStatus.PENDING_TO_VERFIFY);
					tE.settCompDate(LocalDateTime.parse(t.gettCompDate(),formatter));
					long minutes = tE.gettAllDate().until(tE.gettCompDate(), ChronoUnit.MINUTES);					
					tE.settActEff(Double.valueOf(df2.format(minutes/60.0)));										
					ansList.add(t.gettId());
					
				}
				
				session.getTransaction().commit();
				return ansList;						
				
				
									
			}

		}
		catch (HibernateException e) {
			throw new TaskManagerException("User.Task.DATABASE_PROBLEM");

		}
		catch (TaskManagerException e) {
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if(session!=null)
				session.close();
		}
		return null;
	}


	@Override
	public List<Task> getAssignedTaskOfUser(Integer usrId) throws TaskManagerException, Exception {
		try {
			factory = HibernateUtility.getSessionFactory();
			if(factory!=null)
			{
				session = factory.openSession();					
				User1 usrEn = (User1) session.get(User1.class, usrId);
				if(usrEn==null)
					throw new TaskManagerException("User.Dao.USER_NOT_FOUND");
				if(usrEn.getRole().getrId()!=3)
					throw new TaskManagerException("User.Dao.NO_AUTHORITY");
				List<TaskEntity> taskEn = usrEn.getTask();
				if(taskEn==null || taskEn.size()==0)
					throw new TaskManagerException("Task.Dao.NO_TASK");
				List<Task> taskList = new ArrayList<Task>();
				for(TaskEntity tE : taskEn)
				{					
					Task t = tE.taskEnityToModel();
					User1 usrMan = (User1) session.get(User1.class, t.gettOwner());
					if(usrMan==null)
						throw new TaskManagerException("User.Dao.MANAGER_NOT_EXIST");
					t.setTaskOwner(usrMan.getUsrName());
					
					taskList.add(t);					
				}
				return taskList; 					
			}
		}
		catch (HibernateException e) {
			throw new TaskManagerException("User.Task.DATABASE_PROBLEM");
		}
		catch (TaskManagerException e) {
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if(session!=null)
				session.close();
		}
		return null;
	}
}
