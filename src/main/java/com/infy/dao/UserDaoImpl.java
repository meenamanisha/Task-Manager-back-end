package com.infy.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.infy.entity.UserEntity;
import com.infy.entity.HibernateUtility;
import com.infy.entity.RoleEntity;
import com.infy.model.User;

public class UserDaoImpl implements UserDao {

	SessionFactory factory;
	Session session;


	@Override
	public User getEmployeeById(int empId) throws Exception {
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
	public Integer registerUser(User user) throws Exception {

		try {
			factory = HibernateUtility.getSessionFactory();

			if(factory!=null)
			{
				session = factory.openSession();	
				session.getTransaction().begin();
				UserEntity usr = new UserEntity();
				usr.setUsrName(user.getUsrName());
				usr.setPassword(user.getPassword());
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

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if(session!=null)
				session.close();
		}


		return null;

	}

}
