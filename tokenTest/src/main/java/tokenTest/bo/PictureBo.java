package tokenTest.bo;

import java.awt.image.BufferedImage;
import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import tokenTest.model.Picture;
import tokenTest.model.User;

public interface PictureBo {
	void save(User user, MultipartFile file, Picture picture, String path,
			boolean isProfile) throws Exception;

	void update(Picture picture);

	void delete(User user, Picture picture, String path);

	void deleteById(Long id, String path);

	Picture findById(Long id);

	File getFileById(Long id, String path);
	
	BufferedImage zoomOutImage(BufferedImage originalImage,
			Integer width);
}
