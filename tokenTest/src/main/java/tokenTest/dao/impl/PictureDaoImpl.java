package tokenTest.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
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

	public Picture findPictureById(Long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from Picture where id= :id");
		query.setLong("id", id);
		List list = query.list();
		if (list.size() > 0)
			return (Picture) list.get(0);
		else
			return null;
	}

}
