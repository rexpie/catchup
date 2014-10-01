package tokenTest.bo.impl;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tokenTest.Util.Constants;
import tokenTest.bo.PictureBo;
import tokenTest.dao.PictureDao;
import tokenTest.dao.UserDao;
import tokenTest.exception.PictureNotFoundException;
import tokenTest.model.Picture;
import tokenTest.model.User;

@Service("pictureBo")
public class PictureBoImpl implements PictureBo {
	@Autowired
	private PictureDao pictureDao;

	@Autowired
	private UserDao userDao;

	public void setPictureDao(PictureDao pictureDao) {
		this.pictureDao = pictureDao;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void save(User user, MultipartFile file, Picture picture,
			String path, boolean isProfile) throws IOException {
		// TODO Auto-generated method stub

		/* 建立文件来存储上传的图片 */
		File destination = new File(path + File.separator
				+ Constants.THUMB_PICTURE_PATH);
		if (!destination.exists())
			destination.mkdirs();

		destination = new File(path + File.separator
				+ Constants.RIGIN_PICTURE_PATH);
		if (!destination.exists())
			destination.mkdirs();

		/* �����ļ��� */
		String filename = RandomStringUtils.randomAlphanumeric(30);
		destination = new File(path + File.separator
				+ Constants.RIGIN_PICTURE_PATH + File.separator + filename
				+ "." + Constants.PICTURE_FORMAT);
		while (destination.exists()) {
			filename = RandomStringUtils.randomAlphanumeric(30);
			destination = new File(path + File.separator
					+ Constants.RIGIN_PICTURE_PATH + File.separator + filename
					+ "." + Constants.PICTURE_FORMAT);
		}

		/* ����ԭͼƬ�ļ� */
		destination.createNewFile();

		/* ����ԭͼƬ�ļ� */
		if (file != null)
			file.transferTo(destination);

		/* ���СͼƬ */
		saveScaleImage(path + File.separator + Constants.RIGIN_PICTURE_PATH
				+ File.separator + filename + "." + Constants.PICTURE_FORMAT,
				path + File.separator + Constants.THUMB_PICTURE_PATH
						+ File.separator + filename + "."
						+ Constants.PICTURE_FORMAT, Constants.THUMBNAIL_WIDTH,
				Constants.THUMBNAIL_HEIGHT);


		/* 将图片添加到用户 */

		if (picture != null)
			picture.setFilename(destination.getName());
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

		File pictureFile = new File(path + File.separator
				+ Constants.RIGIN_PICTURE_PATH + File.separator
				+ picture.getFilename());
		if (pictureFile.exists())
			pictureFile.delete();

		pictureFile = new File(path + File.separator
				+ Constants.THUMB_PICTURE_PATH + File.separator
				+ picture.getFilename());
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
	public Picture findById(Long id) throws PictureNotFoundException {
		// TODO Auto-generated method stub
		Picture picture = pictureDao.findPictureById(id);
		if (picture == null)
			throw new PictureNotFoundException();
		return picture;
	}

	@Transactional
	public File getFileById(Long id, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveScaleImage(String originalImage, String newImage,
			Integer width, Integer height) throws IOException {
		BufferedImage oldImg = ImageIO.read(new File(originalImage));
		BufferedImage newImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D grph = (Graphics2D) newImg.getGraphics();
		// grph.scale(width / oldImg.getWidth(), height / oldImg.getHeight());
		grph.drawImage(
				oldImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,
				0, null);
		grph.dispose();
		ImageIO.write(newImg, "png", new File(newImage));
	}
}
