package tokenTest.bo;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import tokenTest.exception.PictureNotFoundException;
import tokenTest.model.Picture;
import tokenTest.model.User;

public interface PictureBo {
	void save(User user, MultipartFile file, Picture picture, String path,
			boolean isProfile) throws IOException;

	void update(Picture picture);

	void delete(User user, Picture picture, String path);

	void deleteById(Long id, String path);

	Picture findById(Long id) throws PictureNotFoundException;

	File getFileById(Long id, String path);

	void deletePictureFile(Picture picture, String path);

	void saveScaleImage(String originalImage, String newImage, Integer width,
			Integer height) throws IOException;
}
