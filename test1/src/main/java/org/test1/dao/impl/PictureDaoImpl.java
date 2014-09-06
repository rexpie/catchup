package org.test1.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.test1.dao.PictureDao;
import org.test1.model.Picture;

@Repository("pictureDao")
public class PictureDaoImpl implements PictureDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(Picture picture) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(picture);
	}

	@Override
	public void update(Picture picture) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(picture);
	}

	@Override
	public void delete(Picture picture) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(picture);
	}

}
