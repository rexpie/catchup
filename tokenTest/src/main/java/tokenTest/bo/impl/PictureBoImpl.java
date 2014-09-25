package tokenTest.bo.impl;

import java.io.File;
import java.io.InputStream;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tokenTest.bo.PictureBo;
import tokenTest.dao.PictureDao;
import tokenTest.model.Picture;

@Service("pictureBo")
public class PictureBoImpl implements PictureBo {
	@Autowired
	private PictureDao pictureDao;

	private static String TYPE = ".png";

	public void setPictureDao(PictureDao pictureDao) {
		this.pictureDao = pictureDao;
	}

	@Transactional
	public void save(MultipartFile file, Picture picture, String path)
			throws Exception {
		// TODO Auto-generated method stub
		if (picture == null || file == null || path == null)
			return;

		File picturePath = new File(path);
		if (!picturePath.exists())
			picturePath.mkdirs();

		String filename = path + File.separator
				+ RandomStringUtils.randomAlphanumeric(30) + TYPE;
		File destination = new File(filename);
		while (destination.exists()) {
			filename = path + File.separator
					+ RandomStringUtils.randomAlphanumeric(30) + TYPE;
			destination = new File(filename);
		}
		destination.createNewFile();
		if (file != null)
			file.transferTo(destination);
		if (picture != null)
			picture.setFilename(filename);
		pictureDao.save(picture);
	}

	@Transactional
	public void update(Picture picture) {
		// TODO Auto-generated method stub
		if (picture == null)
			return;
		pictureDao.update(picture);
	}

	@Transactional
	public void delete(Picture picture, String path) {
		// TODO Auto-generated method stub
		if (picture == null || path == null)
			return;

		File pictureFile = new File(path + picture.getFilename());
		if (pictureFile.exists())
			pictureFile.delete();
		pictureDao.delete(picture);
	}

	@Transactional
	public void deleteById(Long id, String path) {
		// TODO Auto-generated method stub
		if (id == null || path == null)
			return;
		Picture picture = pictureDao.findPictureById(id);
		File pictureFile = new File(path + picture.getFilename());
		if (pictureFile.exists())
			pictureFile.delete();
		pictureDao.delete(picture);
	}

	@Transactional
	public Picture findById(Long id) {
		// TODO Auto-generated method stub
		return pictureDao.findPictureById(id);
	}
}
