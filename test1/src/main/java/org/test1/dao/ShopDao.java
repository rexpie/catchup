package org.test1.dao;

import org.test1.model.Shop;


public interface ShopDao {
	void save(Shop shop);

	void update(Shop shop);

	void delete(Shop shop);
	
	Shop findByShopName(String name);
}
