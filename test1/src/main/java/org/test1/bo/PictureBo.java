package org.test1.bo;

import org.test1.model.Picture;

public interface PictureBo {
	void save(Picture picture);

	void update(Picture picture);

	void delete(Picture picture);
}
