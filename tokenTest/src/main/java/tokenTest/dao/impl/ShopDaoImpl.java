package tokenTest.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tokenTest.dao.ShopDao;
import tokenTest.model.Shop;
import tokenTest.model.User;

@Repository("shopDao")
public class ShopDaoImpl implements ShopDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void save(Shop shop) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(shop);
	}

	public void update(Shop shop) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(shop);
	}

	public void delete(Shop shop) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(shop);
	}

	public Shop findByShopName(String name) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Shop where name= :name");
		query.setString("name", name);
		List list = query.list();
		if (list.size() > 0)
			return (Shop) list.get(0);
		else
			return null;
	}

	public Shop findByShopId(Long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Shop where id= :id");
		query.setLong("id", id);
		List list = query.list();
		if (list.size() > 0)
			return (Shop) list.get(0);
		else
			return null;
	}

	@Override
	public Shop findByDianpingId(Long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Shop where dianping_id = :id");
		query.setLong("id", id);
		List list = query.list();
		if (list.size() > 0)
			return (Shop) list.get(0);
		else
			return null;
	}

}
