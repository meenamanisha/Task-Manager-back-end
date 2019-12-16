package com.infy.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query; 
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.infy.entity.UserEntity;
import com.infy.exception.TaskManagerException;
import com.infy.entity.HibernateUtility;
import com.infy.entity.RoleEntity;
import com.infy.entity.TaskEntity;
import com.infy.model.Role;
import com.infy.model.Task;
import com.infy.model.User;

public class UserDaoImpl implements UserDao {

	SessionFactory factory;
	Session session;


	@Override
	public User getEmployeeById(int empId) throws TaskManagerException,Exception  {
		try {
			factory = HibernateUtility.getSessionFactory();

			if(factory!=null)
			{
				session = factory.openSession();
				UserEntity usrEn = (UserEntity)session.get(UserEntity.class, empId);
				User usr = usrEn.userEntityToModel();
				//				
				//				if(!empEn.getTasks().isEmpty())
				//				{
				//					List<Task> tasks = new ArrayList<Task>();					
				//					for(TaskEntity tE : empEn.getTasks())
				//					{
				//						Task t = tE.taskEnityToModel();
				//						tasks.add(t);						
				//					}
				//					
				//				}
				return usr;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if(session!=null)
				session.close();
		}


		return null;
	}


	@Override
	public Integer registerUser(User user) throws TaskManagerException,Exception  {

		try {
			factory = HibernateUtility.getSessionFactory();

			if(factory!=null)
			{
				session = factory.openSession();	
				session.getTransaction().begin();
				String hql = "FROM UserEntity WHERE usrEmail = :email";
				Query q = session.createQuery(hql);							
				q.setParameter("email", user.getUsrEmail());				
				UserEntity usrE= (UserEntity) q.uniqueResult();

				if(usrE!=null)
					throw new TaskManagerException("User.Dao.EMAIL_ALREADY_EXIST");


				UserEntity usr = new UserEntity();
				usr.setUsrName(user.getUsrName());
				usr.setPassword(user.getPassword());
				usr.setUsrProfileImage(user.getUsrProfileImage());
				RoleEntity e = (RoleEntity)session.get(RoleEntity.class, user.getRole().getrId());

				usr.setRole(e);
				usr.setUsrCurrentAdd(user.getUsrCurrentAdd());
				usr.setUsrPermanentAdd(user.getUsrPermanentAdd());
				usr.setUsrEmail(user.getUsrEmail());
				usr.setUsrPhno(user.getUsrPhno());
				int id = (int) session.save(usr);
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
			e.printStackTrace();
		}
		finally {
			if(session!=null)
				session.close();
		}


		return null;

	}


	@Override
	public User loginUser(User user) throws TaskManagerException,Exception {
		try {
			factory = HibernateUtility.getSessionFactory();

			if(factory!=null)
			{
				session = factory.openSession();				
				String hql = "FROM UserEntity WHERE usrEmail = :email";
				Query q = session.createQuery(hql);							
				q.setParameter("email", user.getUsrEmail());				
				UserEntity usrE= (UserEntity) q.uniqueResult();
				if(usrE==null)
					throw new TaskManagerException("User.Dao.USER_NOT_FOUND");				
				if(!usrE.getPassword().equals(user.getPassword()))
					throw new TaskManagerException("User.Dao.PASSWORD_EMAIL_MIS_MATCH");
				usrE.setPassword(null);
				User us= usrE.userEntityToModel();
				us.setRole(usrE.getRole().roleEntityToModel());
				List<TaskEntity> taskE = usrE.getTask();
				if(!taskE.isEmpty())
				{
					List<Task> tasks = new ArrayList<Task>();					
					for(TaskEntity tE : taskE)
					{
						Task t = tE.taskEnityToModel();
						tasks.add(t);						
					}
					us.setTasks(tasks);
				}
				return us;
			}


		}
		catch (HibernateException e) {
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
	public List<User> getAllUserDetails() throws TaskManagerException, Exception {
		try {
			List<User> userList = new ArrayList<User>();
			factory = HibernateUtility.getSessionFactory();

			if(factory!=null)
			{
				session = factory.openSession();				
				String hql = "FROM UserEntity WHERE roleId!=1";
				Query q = session.createQuery(hql);							
//				q.setParameter("email", user.getUsrEmail());				
				List<UserEntity> userEn = q.list(); 
				if(userEn==null)
					throw new TaskManagerException("User.Dao.SYSTEM_EMPTY");
				
				for(UserEntity usrE:userEn)
				{
					User usr = usrE.userEntityToModel();
					Role r = usrE.getRole().roleEntityToModel();
					usr.setRole(r);
					List<TaskEntity> taskE = usrE.getTask();
					
					if(!taskE.isEmpty())
					{
						List<Task> tasks = new ArrayList<Task>();					
						for(TaskEntity tE : taskE)
						{
							Task t = tE.taskEnityToModel();
							tasks.add(t);						
						}
						usr.setTasks(tasks);
					}
					userList.add(usr);
					
				}
			
			}


			return userList;
		}
		catch (HibernateException e) {
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if(session!=null)
				session.close();
		}
	}

}
