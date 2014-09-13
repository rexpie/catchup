package tokenTest.dao;

import tokenTest.model.Tag;

public interface TagDao {
	void save(Tag tag);
	
	void update(Tag tag);
	
	void delete(Tag tag);
	
	Tag findByTagName(String name);
}
