package tokenTest.bo;

import org.springframework.web.multipart.MultipartFile;

import tokenTest.model.Picture;

public interface PictureBo {
	void save(MultipartFile file, Picture picture) throws Exception;

	void update(Picture picture);

	void delete(Picture picture);
	
	void deleteById(Long id);
	
	Picture findById(Long id);
}
