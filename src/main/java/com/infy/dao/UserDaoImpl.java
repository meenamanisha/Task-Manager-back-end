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
				String hql = "FROM UserEntity WHERE usrEmail = :email";
				Query q = session.createQuery(hql);							
				q.setParameter("email", user.getUsrEmail());				
				UserEntity usrE= (UserEntity) q.uniqueResult();

				if(usrE!=null)
					throw new TaskManagerException("User.Dao.EMAIL_ALREADY_EXIST");
				UserEntity urE = (UserEntity) session.get(UserEntity.class, user.getUsrMId());
				if(urE!=null && urE.getRole().getrId()!=2)
					throw new TaskManagerException("User.Dao.MANAGER_NOT_EXIST");

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
				if(usrE.getUsrMId()!=null)
				{
					UserEntity uE =(UserEntity) session.get(UserEntity.class, usrE.getUsrMId()); 
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
				String hql = "FROM UserEntity WHERE roleId=3";
				Query q = session.createQuery(hql);							
//				q.setParameter("email", user.getUsrEmail());				
				@SuppressWarnings("unchecked")
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
				String hql = "FROM UserEntity WHERE roleId=2";
				Query q = session.createQuery(hql);							

				
				@SuppressWarnings("unchecked")
				List<UserEntity> userEn = q.list(); 
				if(userEn==null)
					throw new TaskManagerException("User.Dao.NO_MANAGER_FOUND");
				
				for(UserEntity usrE:userEn)
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
					UserEntity usrEn = (UserEntity)session.get(UserEntity.class, u.getUsrId());
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
