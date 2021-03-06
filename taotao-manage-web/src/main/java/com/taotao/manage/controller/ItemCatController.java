package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;

@Controller
@RequestMapping("item")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;

	@RequestMapping(value = "{cat}", method = RequestMethod.GET)
	public ResponseEntity<List<ItemCat>> queryItemCat(@RequestParam(value = "id", defaultValue = "0") long parentId) {
		try{
			ItemCat record = new ItemCat();
			record.setParentId(parentId);
			List<ItemCat> list = this.itemCatService.queryListByWhere(record);
			
			if(null == list || list.isEmpty()){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}else{
				return ResponseEntity.ok(list);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

}