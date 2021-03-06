package tokenTest.dao;

import tokenTest.model.Shop;


public interface ShopDao {
	void save(Shop shop);

	void update(Shop shop);

	void delete(Shop shop);
	
	Shop findByShopName(String name);
	
	Shop findByShopId(Long id);

	Shop findByDianpingId(Long id);
}
