package tokenTest.bo.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tokenTest.bo.ShopBo;
import tokenTest.dao.ShopDao;
import tokenTest.model.Shop;

@Service("shopBo")
public class ShopBoImpl implements ShopBo {
	@Autowired
	private ShopDao shopDao;

	public void setShopDao(ShopDao shopDao) {
		this.shopDao = shopDao;
	}

	@Transactional
	public void save(Shop shop) {
		// TODO Auto-generated method stub
		shopDao.save(shop);
	}

	@Transactional
	public void update(Shop shop) {
		// TODO Auto-generated method stub
		shopDao.update(shop);
	}

	@Transactional
	public void delete(Shop shop) {
		// TODO Auto-generated method stub
		shopDao.delete(shop);
	}

	@Transactional
	public Shop findByShopName(String name) {
		// TODO Auto-generated method stub
		return shopDao.findByShopName(name);
	}

}
