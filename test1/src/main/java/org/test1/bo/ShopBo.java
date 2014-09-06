package org.test1.bo;

import org.test1.model.Shop;

public interface ShopBo {
	void save(Shop shop);

	void update(Shop shop);

	void delete(Shop shop);

	Shop findByShopName(String name);
}
