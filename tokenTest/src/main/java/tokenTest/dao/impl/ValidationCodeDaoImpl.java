package tokenTest.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tokenTest.dao.ValidationCodeDao;
import tokenTest.model.User;
import tokenTest.model.ValidationCode;

@Repository("validationCodeDao")
public class ValidationCodeDaoImpl implements ValidationCodeDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(ValidationCode code) {
		sessionFactory.getCurrentSession().save(code);
	}

	@Override
	public void update(ValidationCode code) {
		sessionFactory.getCurrentSession().update(code);
	}

	@Override
	public void delete(ValidationCode code) {
		sessionFactory.getCurrentSession().delete(code);
	}

	@Override
	public ValidationCode findByPhoneNum(String phoneNum) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from ValidationCode where phone_number= :phone_number");
		query.setString("phone_number", phoneNum);
		List list = query.list();
		if (list.size() > 0)
			return (ValidationCode) list.get(0);
		else
			return null;
	}

}
