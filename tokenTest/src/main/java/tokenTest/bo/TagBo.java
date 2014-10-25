package tokenTest.bo;

import tokenTest.model.Tag;

public interface TagBo {
	void save(Tag tag);

	void update(Tag tag);

	void delete(Tag tag);

	Tag findByTagName(String name);
}
