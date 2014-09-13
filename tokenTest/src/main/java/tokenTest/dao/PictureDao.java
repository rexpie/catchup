package tokenTest.dao;

import tokenTest.model.Picture;

public interface PictureDao {
	void save(Picture picture);

	void update(Picture picture);

	void delete(Picture picture);
}
