package com.infy.dao;


import java.util.ArrayList;
import java.util.List;
 

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.infy.entity.User1;
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
				User1 usrEn = session.get(User1.class, empId);
				User usr = usrEn.userEntityToModel();				
				return usr;
			}

		}
		catch (HibernateException e) {
			throw e;

		}
//		catch (TaskManagerException e) {
//			throw e;
//		}
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
	public Integer registerUser(User user) throws TaskManagerException,Exception  {

		try {
			factory = HibernateUtility.getSessionFactory();

			if(factory!=null)
			{
				session = factory.openSession();	
				session.getTransaction().begin();
				String hql = "FROM User1 WHERE usrEmail = :email";
				
				@SuppressWarnings("unchecked")
				Query<User1> q = session.createQuery(hql);				
				q.setParameter("email", user.getUsrEmail());				
				User1 usrE= q.uniqueResult();

				if(usrE!=null)
					throw new TaskManagerException("User.Dao.EMAIL_ALREADY_EXIST");
				User1 urE = session.get(User1.class, user.getUsrMId());
				if(urE!=null && urE.getRole().getrId()!=2)
					throw new TaskManagerException("User.Dao.MANAGER_NOT_EXIST");

				User1 usr = new User1();
				usr.setUsrName(user.getUsrName());
				usr.setPassword(user.getPassword());
				usr.setUsrProfileImage(user.getUsrProfileImage());
				RoleEntity e = session.get(RoleEntity.class, user.getRole().getrId());

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
			throw e;
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
				String hql = "FROM User1 WHERE usrEmail = :email";
				
				@SuppressWarnings("unchecked")
				Query<User1> q = session.createQuery(hql);							
				q.setParameter("email", user.getUsrEmail());				
				User1 usrE= q.uniqueResult();
				if(usrE==null)
					throw new TaskManagerException("User.Dao.USER_NOT_FOUND");
				
				if(!usrE.getPassword().equals(user.getPassword()))
					throw new TaskManagerException("User.Dao.PASSWORD_EMAIL_MIS_MATCH");
				usrE.setPassword(null);
				
				User us= usrE.userEntityToModel();
				if(usrE.getUsrMId()!=null)
				{
					User1 uE =session.get(User1.class, usrE.getUsrMId()); 
					if(uE==null)
						throw new TaskManagerException("User.Dao.USER_NOT_FOUND");
//					if(uE.getRole().getrId()!=2)
//						throw new TaskManagerException("User.Dao.MANAGER_NOT_EXIST");
					us.setUsrMName(uE.getUsrName());
					us.setUsrMEmail(uE.getUsrEmail());
				}
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
	public List<User> getAllUserDetails() throws TaskManagerException, Exception {
		try {
			factory = HibernateUtility.getSessionFactory();

			if(factory!=null)
			{
				List<User> userList = new ArrayList<User>();
				session = factory.openSession();				
				String hql = "FROM User1 WHERE roleId=3";
				
				@SuppressWarnings("unchecked")
				Query<User1> q = session.createQuery(hql);							
//				q.setParameter("email", user.getUsrEmail());								
				List<User1> userEn = q.list(); 
				if(userEn==null)
					throw new TaskManagerException("User.Dao.SYSTEM_EMPTY");
				
				for(User1 usrE:userEn)
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
			
				return userList;
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
	public List<User> getManagers() throws TaskManagerException, Exception {
		try {
			factory = HibernateUtility.getSessionFactory();

			if(factory!=null)
			{
				List<User> userList = new ArrayList<User>();
				session = factory.openSession();				
				String hql = "FROM User1 WHERE roleId=2";
				@SuppressWarnings("unchecked")
				Query<User1> q = session.createQuery(hql);							

				
				List<User1> userEn = q.list(); 
				if(userEn==null)
					throw new TaskManagerException("User.Dao.NO_MANAGER_FOUND");
				
				for(User1 usrE:userEn)
				{
					User usr = usrE.userEntityToModel();					
					userList.add(usr);
					
				}
			
				return userList;
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
	public List<Integer> updateMangers(List<User> usrList) throws TaskManagerException, Exception {
		try {
			factory = HibernateUtility.getSessionFactory();

			if(factory!=null)
			{
				List<Integer> userIds = new ArrayList<Integer>();
				session = factory.openSession();				
				session.getTransaction().begin();
				for(User u: usrList)
				{
					User1 usrEn = session.get(User1.class, u.getUsrId());
					if(usrEn==null)
						throw new TaskManagerException("User.Dao.USER_NOT_FOUND");
					usrEn.setUsrMId(u.getUsrMId());
					userIds.add(usrEn.getUsrId());
				}
				session.getTransaction().commit();
							
				return userIds;
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
