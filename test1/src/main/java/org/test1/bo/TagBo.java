package org.test1.bo;

import org.test1.model.Tag;

public interface TagBo {
	void save(Tag tag);

	void update(Tag tag);

	void delete(Tag tag);

	Tag findByStockCode(String name);
}
