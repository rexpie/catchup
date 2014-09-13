package tokenTest.bo;

import tokenTest.model.Picture;

public interface PictureBo {
	void save(Picture picture);

	void update(Picture picture);

	void delete(Picture picture);
}
