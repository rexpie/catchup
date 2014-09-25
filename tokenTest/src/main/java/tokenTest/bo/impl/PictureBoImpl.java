package tokenTest.bo.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.RollbackException;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tokenTest.Util.Status;
import tokenTest.bo.PictureBo;
import tokenTest.dao.PictureDao;
import tokenTest.dao.UserDao;
import tokenTest.model.Picture;
import tokenTest.model.User;

@Service("pictureBo")
public class PictureBoImpl implements PictureBo {
	@Autowired
	private PictureDao pictureDao;

	@Autowired
	private UserDao userDao;

	private static String TYPE = ".png";

	public void setPictureDao(PictureDao pictureDao) {
		this.pictureDao = pictureDao;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void save(User user, MultipartFile file, Picture picture,
			String path, boolean isProfile) throws Exception {
		// TODO Auto-generated method stub
		/* 建立文件来存储上传的图片 */
		File destination = new File(path);
		if (!destination.exists())
			destination.mkdirs();

		String filename = path + File.separator
				+ RandomStringUtils.randomAlphanumeric(30) + TYPE;
		destination = new File(filename);
		while (destination.exists()) {
			filename = path + File.separator
					+ RandomStringUtils.randomAlphanumeric(30) + TYPE;
			destination = new File(filename);
		}
		destination.createNewFile();
		if (file != null)
			file.transferTo(destination);
		if (picture != null)
			picture.setFilename(destination.getName());

		/* 将图片添加到用户 */
		if (isProfile) {
			user.setPic(picture);
		} else {
			user.getPicture().add(picture);
		}
		userDao.update(user);

		/* 级联操作，不需要另外保存图片 */
		// pictureDao.save(picture);
	}

	@Transactional
	public void update(Picture picture) {
		// TODO Auto-generated method stub
		if (picture == null)
			return;
		pictureDao.update(picture);
	}

	@Transactional(rollbackOn = { Exception.class })
	public void delete(User user, Picture picture, String path) {
		// TODO Auto-generated method stub
		user.getPicture().remove(picture);
		userDao.update(user);
		File pictureFile = new File(path + picture.getFilename());
		if (pictureFile.exists())
			pictureFile.delete();
		/* 级联操作，不需要另外删除图片 */
		// pictureDao.delete(picture);
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

	@Transactional
	public File getFileById(Long id, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public BufferedImage zoomOutImage(BufferedImage originalImage,
			Integer width) {
		double times = originalImage.getWidth() / width;
		int height = (int) (originalImage.getHeight() / times);
		BufferedImage newImage = new BufferedImage(width, height,
				originalImage.getType());
		Graphics g = newImage.getGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return newImage;
	}
}
