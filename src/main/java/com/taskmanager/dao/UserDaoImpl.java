package com.taskmanager.dao;


import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.taskmanager.entity.HibernateUtility;
import com.taskmanager.entity.RoleEntity;
import com.taskmanager.entity.TaskEntity;
import com.taskmanager.entity.User1;
import com.taskmanager.exception.TaskManagerException;
import com.taskmanager.model.Role;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;

@SuppressWarnings("unchecked")
public class UserDaoImpl implements UserDao {
	
	static Logger logger = Logger.getLogger(UserDaoImpl.class);

	SessionFactory factory;
	Session session;

	@Override
	public Integer registerUser(User user) throws TaskManagerException,Exception  {

		try {
			factory = HibernateUtility.getSessionFactory();

			if(factory!=null)
			{
				session = factory.openSession();	
				session.getTransaction().begin();
				String hql = "FROM User1 WHERE usrEmail = :email";
						
				Query<User1> q = session.createQuery(hql);				
				q.setParameter("email", user.getUsrEmail());				
				User1 usrE= q.uniqueResult();

				if(usrE!=null)
					throw new TaskManagerException("User.Dao.EMAIL_ALREADY_EXIST");
				if(user.getUsrMId()!=null)
				{
					
					User1 urE = session.get(User1.class, user.getUsrMId());
					if(urE!=null && urE.getRole().getrId()!=2)
						throw new TaskManagerException("User.Dao.MANAGER_NOT_EXIST");
				}

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
	public User loginUser(User user) throws TaskManagerException,Exception {
		try {
			factory = HibernateUtility.getSessionFactory();

			if(factory!=null)
			{
				session = factory.openSession();				
				String hql = "FROM User1 WHERE usrEmail = :email";
							
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
	public List<User> getAllUserDetails(Integer usrId) throws TaskManagerException, Exception {
		try {
			factory = HibernateUtility.getSessionFactory();

			if(factory!=null)
			{
				List<User> userList = new ArrayList<User>();
				session = factory.openSession();		
				
				
				
				User1 usrEn = session.get(User1.class, usrId);
				if(usrEn==null)
					throw new TaskManagerException("User.Dao.EMAIL_ALREADY_EXIST");
				if(usrEn.getRole().getrId()>2)
					throw new TaskManagerException("User.Dao.NO_AUTHORITY");
				
				String hql = null;
				Query<User1> q = null;
				if(usrEn.getRole().getrId()==1)
				{
					
					hql = "FROM User1 WHERE roleId=3 "; 
					q = session.createQuery(hql);
				}
				else if(usrEn.getRole().getrId()==2)
				{
					hql = "FROM User1 WHERE usrMId = :id"; 
					q = session.createQuery(hql);
					q.setParameter("id", usrId);				
				}
				
				List<User1> userEn = q.list(); 
				if(userEn==null || userEn.size()==0)
					throw new TaskManagerException("User.Dao.NO_EMPLOYEE_FIND");
				
				for(User1 usrE:userEn)
				{
					User usr = usrE.userEntityToModel();
					Role r = usrE.getRole().roleEntityToModel();
					if(usr.getUsrId()!=null)
					{
						String query = "select usrName from User1 where usrId =:id";
						 
						Query<String> q2 = session.createQuery(query);
						q2.setParameter("id", usr.getUsrMId());
						usr.setUsrMName(q2.uniqueResult());
						
					}
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
	public List<User> getManagers(Integer usrId) throws TaskManagerException, Exception {
		try {
			factory = HibernateUtility.getSessionFactory();

			if(factory!=null)
			{
				List<User> userList = new ArrayList<User>();
				session = factory.openSession();		
				User1 usrEn = session.get(User1.class, usrId);
				if(usrEn==null)
					throw new TaskManagerException("User.Dao.EMAIL_ALREADY_EXIST");
				if(usrEn.getRole().getrId()!=1)
					throw new Exception("User.Dao.NO_AUTHORITY");
				
				
				String hql = "FROM User1 WHERE roleId=2";				
				Query<User1> q = session.createQuery(hql);							

				
				List<User1> userEn = q.list(); 
				if(userEn==null || userEn.size()==0)
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
			throw new TaskManagerException("User.Task.DATABASE_PROBLEM");
		}
		catch (TaskManagerException e) {
			throw new TaskManagerException("User.Task.DATABASE_PROBLEM");
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
