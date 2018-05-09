package com.taotao.manage.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.service.ItemCatService;

@Controller
@RequestMapping("api/item/cat")
public class ApiItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<ItemCatResult> queryItemCatResult(){
		try {
			ItemCatResult itemCatResult = new ItemCatResult();
			itemCatResult = this.itemCatService.queryAllToTree();
			return ResponseEntity.ok(itemCatResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		
	}
}
