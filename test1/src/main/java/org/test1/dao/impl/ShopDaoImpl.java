package org.test1.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.test1.dao.ShopDao;
import org.test1.model.Shop;
import org.test1.model.Stock;

@Repository("shopDao")
public class ShopDaoImpl implements ShopDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(Shop shop) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(shop);
	}

	@Override
	public void update(Shop shop) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(shop);
	}

	@Override
	public void delete(Shop shop) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(shop);
	}

	@Override
	public Shop findByShopName(String name) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Shop where name= :name");
		query.setString("name", name);
		List list = query.list();
		return (Shop) list.get(0);
	}

}
