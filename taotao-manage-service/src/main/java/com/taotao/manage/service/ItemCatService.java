package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;

@Service
public class ItemCatService extends BaseService<ItemCat> {

	@Autowired
	private ItemCatMapper itemcatMapper;

//	@Override
//	public Mapper<ItemCat> getMapper() {
//		// TODO Auto-generated method stub
//		return this.itemcatMapper;
//	}

	// public List<ItemCat> queryItemCatListByParentId(Long parentId) {
	//// List<ItemCat> list = this.itemcatMapper.selectByExample(parentId);
	//// return list;
	// ItemCat record = new ItemCat();
	// record.setParentId(parentId);
	// return this.itemcatMapper.select(record);
	// }

	// @Override
	// public List<ItemCat> queryListByWhere(ItemCat item){
	// ret;
	//
	// }

}
