package org.test1.bo.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.test1.bo.ShopBo;
import org.test1.dao.ShopDao;
import org.test1.model.Shop;

@Service("shopBo")
public class ShopBoImpl implements ShopBo {
	@Autowired
	private ShopDao shopDao;

	public void setShopDao(ShopDao shopDao) {
		this.shopDao = shopDao;
	}

	@Override
	@Transactional
	public void save(Shop shop) {
		// TODO Auto-generated method stub
		shopDao.save(shop);
	}

	@Override
	@Transactional
	public void update(Shop shop) {
		// TODO Auto-generated method stub
		shopDao.update(shop);
	}

	@Override
	@Transactional
	public void delete(Shop shop) {
		// TODO Auto-generated method stub
		shopDao.delete(shop);
	}

	@Override
	@Transactional
	public Shop findByShopName(String name) {
		// TODO Auto-generated method stub
		return shopDao.findByShopName(name);
	}

}
