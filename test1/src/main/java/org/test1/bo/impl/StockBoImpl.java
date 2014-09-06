package org.test1.bo.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.test1.bo.StockBo;
import org.test1.dao.StockDao;
import org.test1.model.Stock;

@Service("stockBo")
public class StockBoImpl implements StockBo{
	
	@Autowired
	private StockDao stockDao;
	
	@Transactional
	public void setStockDao(StockDao stockDao) {
		this.stockDao = stockDao;
	}

	@Transactional
	public void save(Stock stock){
		stockDao.save(stock);
	}
	
	@Transactional
	public void update(Stock stock){
		stockDao.update(stock);
	}
	
	@Transactional
	public void delete(Stock stock){
		stockDao.delete(stock);
	}
	
	@Transactional
	public Stock findByStockCode(String stockCode){
		return stockDao.findByStockCode(stockCode);
	}

	@Transactional
	public List<Stock> getAllStocks() {
		return stockDao.getAllStocks();
	}
}