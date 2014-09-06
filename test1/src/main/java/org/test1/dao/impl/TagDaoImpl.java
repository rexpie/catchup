package org.test1.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.test1.dao.TagDao;
import org.test1.model.Tag;

@Repository("tagDao")
public class TagDaoImpl implements TagDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(Tag tag) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(tag);
	}

	@Override
	public void update(Tag tag) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(tag);
	}

	@Override
	public void delete(Tag tag) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(tag);
	}

	@Override
	public Tag findByTagName(String name) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Stock where name= :name");
		query.setString("name", name);
		List list = query.list();
		return (Tag) list.get(0);
	}

}
