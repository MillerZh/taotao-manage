package com.taotao.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;

@Service
public class ItemService extends BaseService<Item>{
	@Autowired
	private ItemDescService itemDescService;
	
	@Autowired
	private ItemParamItemService itemParamItemService;
	
	@Autowired
	private ItemMapper itemMapper;
	
	/**
	 * 保存item
	 * 
	 * @param item
	 * @param desc
	 * @param itemParams 
	 */
	public void saveItem(Item item, String desc, String itemParams){
		
		item.setStatus(1);
		item.setId(null);
		super.save(item);
		
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		this.itemDescService.save(itemDesc);
		
		// 保存商品规格
		ItemParamItem itemParamItem = new ItemParamItem();
		itemParamItem.setItemId(item.getId());
		itemParamItem.setParamData(itemParams);
		this.itemParamItemService.save(itemParamItem);
		
	}
	
	/**
	 * 更新item
	 * 
	 * @param item
	 * @param desc
	 * @param itemParams 
	 * @return 
	 */
	public Boolean updateItem(Item item, String desc, String itemParams){
		item.setCreated(null);
		item.setStatus(null);
		Integer count1 = super.updateSelective(item);
		
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		Integer count2 = this.itemDescService.updateSelective(itemDesc);
		
		//更新规格参数数据
		Integer count3 = this.itemParamItemService.updateItemParamItem(item.getId(), itemParams);
		
		return count1.intValue() == 1 && count2.intValue() == 1 && count3.intValue() == 1;
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
