package tokenTest.bo;

import org.springframework.web.multipart.MultipartFile;

import tokenTest.model.Picture;

public interface PictureBo {
	void save(MultipartFile file, Picture picture,String path) throws Exception;

	void update(Picture picture);

	void delete(Picture picture,String path);
	
	void deleteById(Long id,String path);
	
	Picture findById(Long id);
}
