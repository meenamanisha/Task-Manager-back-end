package com.infy.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

public class TaskDaoImpl implements TaskDao{

	SessionFactory factory;
	Session session;
	
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
				 
				
				@SuppressWarnings("unchecked")
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
					
					List<TaskEntity> taskEn = new ArrayList<TaskEntity>();
					for(Task t : tks)
					{
						
						TaskEntity tE = (TaskEntity) session.get(TaskEntity.class, t.gettId());
						if(tE==null)
							throw new TaskManagerException("Task.Dao.TASK_ID_NOT_EXIST");
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
					usrEn.setTask(taskEn);
					session.save(usrEn);
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
	public List<Task> getAllPendingTask(Integer usrId) throws TaskManagerException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
	

}
