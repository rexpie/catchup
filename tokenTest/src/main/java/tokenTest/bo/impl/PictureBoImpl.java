package tokenTest.bo.impl;

import java.io.File;
import java.io.InputStream;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tokenTest.bo.PictureBo;
import tokenTest.dao.PictureDao;
import tokenTest.model.Picture;

@Service("pictureBo")
public class PictureBoImpl implements PictureBo {
	@Autowired
	private PictureDao pictureDao;

	private static String DIR = "picture";

	public void setPictureDao(PictureDao pictureDao) {
		this.pictureDao = pictureDao;
	}

	@Transactional
	public void save(MultipartFile file, Picture picture) throws Exception {
		// TODO Auto-generated method stub
		if (picture == null || file == null)
			return;
		String filename = System.getProperty("user.dir") + File.pathSeparator
				+ DIR + File.pathSeparator
				+ RandomStringUtils.randomAlphanumeric(30);
		File destination = new File(filename);
		while (destination.exists()) {
			filename = System.getProperty("user.dir") + File.pathSeparator
					+ DIR + File.pathSeparator
					+ RandomStringUtils.randomAlphanumeric(30);
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
	public void delete(Picture picture) {
		// TODO Auto-generated method stub
		if (picture == null)
			return;
		pictureDao.delete(picture);
	}

	@Transactional
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		if (id == null)
			return;
		Picture picture = pictureDao.findPictureById(id);
		File pictureFile = new File(System.getProperty("user.dir")
				+ File.pathSeparator + DIR + File.pathSeparator
				+ picture.getFilename());
		pictureFile.deleteOnExit();
		pictureDao.delete(picture);
	}
}
