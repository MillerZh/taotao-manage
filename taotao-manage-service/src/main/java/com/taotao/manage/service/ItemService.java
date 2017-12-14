package com.taotao.manage.service;

import java.util.List;

import org.apache.commons.logging.impl.Log4JLogger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;

@Service
public class ItemService extends BaseService<Item>{
	@Autowired
	private ItemDescService itemDescService;
	
	@Autowired
	private ItemMapper itemMapper;
	
	public void saveItem(Item item, String desc){
		
		item.setStatus(1);
		item.setId(null);
		super.save(item);
		
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		this.itemDescService.save(itemDesc);
	}
	
	/**
	 * 查询ItemList
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUIResult queryItemList(Integer page, Integer rows){
		PageHelper.startPage(page, rows);
		Example example = new Example(Item.class);
		example.setOrderByClause("updated DESC");
		List<Item> list = this.itemMapper.selectByExample(example);
		PageInfo<Item> pageInfo = new PageInfo<Item>(list);
		
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}
}
