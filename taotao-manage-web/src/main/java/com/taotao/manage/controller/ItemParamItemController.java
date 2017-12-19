package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.manage.service.ItemParamItemService;
import com.taotao.manage.service.ItemParamService;

@Controller
@RequestMapping("item/param/item")
public class ItemParamItemController {
	@Autowired
	private ItemParamItemService itemParamItemService;

	/**
	 * 查询item
	 * 
	 * @param itemId
	 * @param paramData
	 * @return
	 */
	@RequestMapping(value = "{itemId}", method = RequestMethod.GET)
	public ResponseEntity<ItemParamItem> queryItemParam(@PathVariable("itemId") Long itemId) {
		try {
			ItemParamItem itemParam = new ItemParamItem();
			itemParam.setItemId(itemId);
			// query for itemParam
			ItemParamItem record = new ItemParamItem();
			record = this.itemParamItemService.queryOne(itemParam);
			if (null == record){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(record);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
