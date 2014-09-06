package org.test1.dao;

import org.test1.model.Tag;

public interface TagDao {
	void save(Tag tag);
	
	void update(Tag tag);
	
	void delete(Tag tag);
	
	Tag findByTagName(String name);
}
