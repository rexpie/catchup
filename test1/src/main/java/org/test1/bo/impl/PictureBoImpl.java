package org.test1.bo.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.test1.bo.PictureBo;
import org.test1.dao.PictureDao;
import org.test1.model.Picture;


@Service("pictureBo")
public class PictureBoImpl implements PictureBo {
	@Autowired
	private PictureDao pictureDao;

	public void setPictureDao(PictureDao pictureDao) {
		this.pictureDao = pictureDao;
	}

	@Override
	@Transactional
	public void save(Picture picture) {
		// TODO Auto-generated method stub
		pictureDao.save(picture);
	}

	@Override
	@Transactional
	public void update(Picture picture) {
		// TODO Auto-generated method stub
		pictureDao.update(picture);
	}

	@Override
	@Transactional
	public void delete(Picture picture) {
		// TODO Auto-generated method stub
		pictureDao.delete(picture);
	}

}
