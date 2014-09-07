package org.test1.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.test1.dao.StockDao;
import org.test1.model.Stock;

import com.google.common.collect.Lists;

@Repository("stockDao")
public class StockDaoImpl implements StockDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	public void save(Stock stock) {
		session = sessionFactory.getCurrentSession();
		session.save(stock);
	}

	public void update(Stock stock) {
		session = sessionFactory.getCurrentSession();
		session.update(stock);
	}

	public void delete(Stock stock) {
		session = sessionFactory.getCurrentSession();
		session.delete(stock);
	}

	public Stock findByStockCode(String stockCode) {
		session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from Stock where stockCode= :stockCode");
		query.setString("stockCode", stockCode);
		List<Stock> list = query.list();
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	@Override
	public List<Stock> getAllStocks() {
		session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from Stock");
		List<Stock> list = query.list();
		return list;
	}

}