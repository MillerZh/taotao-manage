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
import com.taotao.manage.service.ItemParamService;

@Controller
@RequestMapping("item/param")
public class ItemParamController {
	@Autowired
	private ItemParamService itemParamService;

	/**
	 * 查询item
	 * 
	 * @param itemId
	 * @param paramData
	 * @return
	 */
	@RequestMapping(value = "{itemId}", method = RequestMethod.GET)
	public ResponseEntity<ItemParam> queryItemParam(@PathVariable("itemId") Long itemId) {
		try {
			ItemParam itemParam = new ItemParam();
			itemParam.setItemCatId(itemId);
			// query for itemParam
			ItemParam record = new ItemParam();
			record = this.itemParamService.queryOne(itemParam);
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
	
	/**
	 * 新增itemParam模版
	 * 
	 * @param itemId
	 * @param paramData
	 * @return
	 */
	@RequestMapping(value = "{itemId}", method = RequestMethod.POST)
	public ResponseEntity<Void> saveItemParam(@PathVariable("itemId") Long itemId,
			@RequestParam("paramData") String paramData) {
		try {
			ItemParam itemParam = new ItemParam();
			itemParam.setId(null);
			itemParam.setItemCatId(itemId);
			itemParam.setParamData(paramData);
			
			this.itemParamService.save(itemParam);
			
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
