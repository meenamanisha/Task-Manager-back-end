package com.infy.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.infy.entity.UserEntity;
import com.infy.entity.HibernateUtility;
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

}
