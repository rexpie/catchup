package tokenTest.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tokenTest.dao.PictureDao;
import tokenTest.model.Picture;

@Repository("pictureDao")
public class PictureDaoImpl implements PictureDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void save(Picture picture) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(picture);
	}

	public void update(Picture picture) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(picture);
	}

	public void delete(Picture picture) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(picture);
	}

}
