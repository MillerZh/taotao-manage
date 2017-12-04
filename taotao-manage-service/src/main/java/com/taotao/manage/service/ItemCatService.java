package com.taotao.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;

@Service
public class ItemCatService {

	@Autowired
	private ItemCatMapper itemcatMapper;

	public List<ItemCat> queryItemCatListByParentId(Long parentId) {
//		List<ItemCat> list = this.itemcatMapper.selectByExample(parentId);
//		return list;
		ItemCat record = new ItemCat();
		record.setParentId(parentId);
		return this.itemcatMapper.select(record);
	}
	
}
