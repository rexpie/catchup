package tokenTest.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tokenTest.dao.TagDao;
import tokenTest.model.Tag;

@Repository("tagDao")
public class TagDaoImpl implements TagDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void save(Tag tag) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(tag);
	}

	public void update(Tag tag) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(tag);
	}

	public void delete(Tag tag) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(tag);
	}

	public Tag findByTagName(String name) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Tag where name= :name");
		query.setString("name", name);
		List list = query.list();
		if (list == null || list.size() == 0 ){
			return null;
		}
		return (Tag) list.get(0);
	}

}
