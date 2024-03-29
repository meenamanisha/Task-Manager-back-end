package com.taskmanager.dao;

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

import com.taskmanager.entity.HibernateUtility;
import com.taskmanager.entity.TaskEntity;
import com.taskmanager.entity.User1;
import com.taskmanager.exception.TaskManagerException;
import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.model.User;


@SuppressWarnings("unchecked")
public class 	TaskDaoImpl implements TaskDao{

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

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");							
				taskEn.settCreatDate(LocalDateTime.parse(task.gettCreatDate(), formatter));				
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
	public List<Task> getAllTaskByUserId(Integer usrId, String taskDet) throws TaskManagerException, Exception {
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
				if(taskDet.equals("userTask"))
				{ 
					for(TaskEntity tE : taskEn)
					{					
						Task t = tE.taskEnityToModel();
						if(t.gettStatus().equals(TaskStatus.IN_PROCESS))
							continue;
						User1 usrMan = (User1) session.get(User1.class, t.gettOwner());
						if(usrMan==null)
							throw new TaskManagerException("User.Dao.MANAGER_NOT_EXIST");
						t.setTaskOwner(usrMan.getUsrName());						
						taskList.add(t);					
					}

				}

				else if(taskDet.equalsIgnoreCase("assignedTask"))
				{
					for(TaskEntity tE : taskEn)
					{					
						Task t = tE.taskEnityToModel();
						if(t.gettStatus().equals(TaskStatus.IN_PROCESS))
						{

							User1 usrMan = (User1) session.get(User1.class, t.gettOwner());
							if(usrMan==null)
								throw new TaskManagerException("User.Dao.MANAGER_NOT_EXIST");
							t.setTaskOwner(usrMan.getUsrName());						
							taskList.add(t);					
						}
					}
				}
				return taskList; 							

				//				if(usrEn.getRole().getrId()!=2)
				//					throw new TaskManagerException("User.Dao.MANAGER_NOT_EXIST");
				//				String sql = "FROM TaskEntity where tOwner = :usrId";
				//				
				//				Query<TaskEntity> q = session.createQuery(sql);
				//				q.setParameter("usrId", usrId);
				//
				//				List<TaskEntity> taskEns = q.list();
				//
				//				if(taskEns==null || taskEns.size()==0)
				//					throw new TaskManagerException("Task.Dao.NO_TASK_BY_USER");
				//				List<Task> taskList = new ArrayList<Task>();
				//
				//				for(TaskEntity tE: taskEns)
				//				{
				//					Task t = tE.taskEnityToModel();
				//					Query<Object[]> q1 = session.createSQLQuery("select usrId, usrName from user1 where usrId in( select user1_usrId from user1_task where task_tid = :id)");
				//					q1.setParameter("id", tE.gettId());
				//					
				//					List<Object[]> rows = q1.getResultList(); 
				//					
				//					for(Object[] row : rows){		
				//						t.settUserId(Integer.parseInt(row[0].toString()));
				//						t.settUserName(row[1].toString());
				//						
				//					}
				////					System.out.println(q1.getResultList().get(0));
				//					taskList.add(t);				
				//				}
				//				
				//
				//				return taskList;
				//

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
	public List<User> allAssignedTask(Integer usrId) throws TaskManagerException, Exception {
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
							if(tE.gettStatus().equals(TaskStatus.PENDING_TO_VERFIFY) )
							{							
								Task t =  tE.taskEnityToModel();
								taskL.add(t);							
							}														
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
					tE.settStatus(t.gettStatus()	);
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


	@Override
	public List<Task> getAllUserCreatedTask(Integer usrId ) throws TaskManagerException, Exception {
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
				List<TaskEntity> taskEns = null;
				String sql = "FROM TaskEntity where tOwner = :usrId and tStatus='NEW'";				
				Query<TaskEntity> q = session.createQuery(sql);
				q.setParameter("usrId", usrId);
				taskEns = q.list();					
				if(taskEns==null || taskEns.size()==0)
					throw new TaskManagerException("Task.Dao.NO_TASK_BY_USER");
				List<Task> taskList = new ArrayList<Task>();
				for(TaskEntity tE: taskEns)
				{
					Task t = tE.taskEnityToModel();
					taskList.add(t);	

				}


				return taskList;


				//
				//				for(TaskEntity tE: taskEns)
				//				{
				//					Task t = tE.taskEnityToModel();
				//					Query<Object[]> q1 = session.createSQLQuery("select usrId, usrName from user1 where usrId in( select user1_usrId from user1_task where task_tid = :id)");
				//					q1.setParameter("id", tE.gettId());
				//					
				//					List<Object[]> rows = q1.getResultList(); 
				//					
				//					for(Object[] row : rows){		
				//						t.settUserId(Integer.parseInt(row[0].toString()));
				//						t.settUserName(row[1].toString());
				//						
				//					}
				////					System.out.println(q1.getResultList().get(0));
				//					taskList.add(t);				
				//				}
				//				
				//
				//				return taskList;



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
	public List<Task> getAllPendingTask(Integer usrId) throws TaskManagerException, Exception {		

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

				String sql = "from TaskEntity  where tOwner=:usrId and tStatus='PENDING_TO_VERFIFY'";				
				Query<TaskEntity> q = session.createQuery(sql);
				//String sql = "select * from user1 where usrId in ( select distinct user1_usrId from user1_task where task_tid in (select tid from task where towner ='1045151'))";
				//Query<User1> q = session.createSQLQuery(sql);

				q.setParameter("usrId", usrId);					
				List<TaskEntity> taskEn = q.list();					
				if(taskEn==null || taskEn.size()==0)
					throw new TaskManagerException("Task.Dao.NO_TASK_FOR_UPDATTION");
				List<Task> taskList = new ArrayList<Task>();					
				for(TaskEntity taskE : taskEn)
				{				
					Task t = taskE.taskEnityToModel();	
					Query<Object[]> q1 = session.createSQLQuery("select usrId, usrName from user1 where usrId = ( select user1_usrId from user1_task where task_tid = :id)");
					q1.setParameter("id", taskE.gettId());							
					List<Object[]> rows = q1.getResultList(); 							
					for(Object[] row : rows){		
						t.settUserId(Integer.parseInt(row[0].toString()));
						t.settUserName(row[1].toString());

					}							
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
	public List<Task> getAllCreatedTask(Integer usrId) throws TaskManagerException, Exception {
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

				String sql = "from TaskEntity  where tOwner=:usrId and tStatus!='PENDING_TO_VERFIFY'";				
				Query<TaskEntity> q = session.createQuery(sql);
				q.setParameter("usrId", usrId);					
				List<TaskEntity> taskEn = q.list();					
				if(taskEn==null || taskEn.size()==0)
					throw new TaskManagerException("Task.Dao.NO_TASK_FOR_UPDATTION");
				List<Task> taskList = new ArrayList<Task>();					
				for(TaskEntity taskE : taskEn)
				{				
					Task t = taskE.taskEnityToModel();	
					Query<Object[]> q1 = session.createSQLQuery("select usrId, usrName from user1 where usrId = ( select user1_usrId from user1_task where task_tid = :id)");
					q1.setParameter("id", taskE.gettId());							
					List<Object[]> rows = q1.getResultList(); 							
					for(Object[] row : rows){		
						t.settUserId(Integer.parseInt(row[0].toString()));
						t.settUserName(row[1].toString());

					}							
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
	public List<Task> getWeekDashBoard(Integer usrId) throws TaskManagerException, Exception {
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
//				String hql = "from TaskEntity  WHERE WEEKOFYEAR(tCreatDate)=WEEKOFYEAR(NOW()) and tOwner=:usrId and tStatus in ( 'IN_PROCESS' , 'CANCELLED' )";
				String hql = "from TaskEntity  WHERE WEEKOFYEAR(tCreatDate)=WEEKOFYEAR(NOW()) and tOwner=:usrId";				
				Query<TaskEntity> q = session.createQuery(hql);
				q.setParameter("usrId", usrId);					
				List<TaskEntity> taskEn = q.list();					
				if(taskEn==null || taskEn.size()==0)
					throw new TaskManagerException("Task.Dao.NO_FOR_WEEK");
				List<Task> taskList = new ArrayList<Task>();					
				for(TaskEntity taskE : taskEn)
				{				
					Task t = taskE.taskEnityToModel();	
					Query<Object[]> q1 = session.createSQLQuery("select usrId, usrName from user1 where usrId = ( select user1_usrId from user1_task where task_tid = :id)");
					q1.setParameter("id", taskE.gettId());							
					List<Object[]> rows = q1.getResultList(); 							
					for(Object[] row : rows){		
						t.settUserId(Integer.parseInt(row[0].toString()));
						t.settUserName(row[1].toString());

					}							
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
