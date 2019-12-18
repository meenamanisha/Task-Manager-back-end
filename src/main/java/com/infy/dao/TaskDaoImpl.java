package com.infy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.infy.entity.HibernateUtility; 
import com.infy.entity.TaskEntity;
import com.infy.entity.UserEntity;
import com.infy.exception.TaskManagerException;
import com.infy.model.Task;

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
			throw e;

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
				UserEntity usrEn = (UserEntity) session.get(UserEntity.class, usrId);
				if(usrEn==null)
					throw new TaskManagerException("User.Dao.USER_NOT_FOUND");
				if(usrEn.getRole().getrId()!=2)
					throw new TaskManagerException("User.Dao.MANAGER_NOT_EXIST");
				String sql = "FROM TaskEntity where tOwner = :usrId";
				Query q = session.createQuery(sql);
				q.setParameter("usrId", usrId);
				
				@SuppressWarnings("unchecked")
				List<TaskEntity> taskEns = q.list();
				
				if(taskEns==null)
					throw new TaskManagerException("Task.Service.NO_TASK_BY_USER");
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
			throw e;

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
