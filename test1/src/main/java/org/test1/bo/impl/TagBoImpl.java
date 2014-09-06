package org.test1.bo.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.test1.bo.TagBo;
import org.test1.dao.TagDao;
import org.test1.model.Tag;

@Service("tagBo")
public class TagBoImpl implements TagBo{
	@Autowired
	private TagDao tagDao;
	
	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}

	@Override
	@Transactional
	public void save(Tag tag) {
		// TODO Auto-generated method stub
		tagDao.save(tag);
	}

	@Override
	@Transactional
	public void update(Tag tag) {
		// TODO Auto-generated method stub
		tagDao.update(tag);
	}

	@Override
	@Transactional
	public void delete(Tag tag) {
		// TODO Auto-generated method stub
		tagDao.delete(tag);
	}

	@Override
	@Transactional
	public Tag findByStockCode(String name) {
		// TODO Auto-generated method stub
		return tagDao.findByTagName(name);
	}

}
