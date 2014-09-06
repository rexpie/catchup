package org.test1.bo;

import java.util.List;

import org.test1.model.Stock;

public interface StockBo {
	
	void save(Stock stock);
	
	void update(Stock stock);
	
	void delete(Stock stock);
	
	Stock findByStockCode(String stockCode);

	List<Stock> getAllStocks();
}
