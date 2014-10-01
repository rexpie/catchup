package tokenTest.bo;

import tokenTest.exception.ShopNotFoundException;
import tokenTest.model.Shop;

public interface ShopBo {
	void save(Shop shop);

	void update(Shop shop);

	void delete(Shop shop);

	Shop findByShopName(String name);

	Shop findByShopId(Long id) throws ShopNotFoundException;
}
