package tokenTest.bo.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tokenTest.bo.TagBo;
import tokenTest.dao.TagDao;
import tokenTest.model.Tag;

@Service("tagBo")
public class TagBoImpl implements TagBo {
	@Autowired
	private TagDao tagDao;

	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}

	@Transactional
	public void save(Tag tag) {
		// TODO Auto-generated method stub
		tagDao.save(tag);
	}

	@Transactional
	public void update(Tag tag) {
		// TODO Auto-generated method stub
		tagDao.update(tag);
	}

	@Transactional
	public void delete(Tag tag) {
		// TODO Auto-generated method stub
		tagDao.delete(tag);
	}

	@Transactional
	public Tag findByTagName(String name) {
		// TODO Auto-generated method stub
		return tagDao.findByTagName(name);
	}

}
