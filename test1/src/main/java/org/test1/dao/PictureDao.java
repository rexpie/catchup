package org.test1.dao;

import org.test1.model.Picture;

public interface PictureDao {
	void save(Picture picture);

	void update(Picture picture);

	void delete(Picture picture);
}
