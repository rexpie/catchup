package tokenTest.bo.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tokenTest.bo.PictureBo;
import tokenTest.dao.PictureDao;
import tokenTest.model.Picture;

@Service("pictureBo")
public class PictureBoImpl implements PictureBo {
	@Autowired
	private PictureDao pictureDao;

	public void setPictureDao(PictureDao pictureDao) {
		this.pictureDao = pictureDao;
	}

	@Transactional
	public void save(Picture picture) {
		// TODO Auto-generated method stub
		pictureDao.save(picture);
	}

	@Transactional
	public void update(Picture picture) {
		// TODO Auto-generated method stub
		pictureDao.update(picture);
	}

	@Transactional
	public void delete(Picture picture) {
		// TODO Auto-generated method stub
		pictureDao.delete(picture);
	}

}
