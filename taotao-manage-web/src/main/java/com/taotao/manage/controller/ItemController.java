package com.taotao.manage.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;

@Controller
@RequestMapping("item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PicUploadController.class);

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> save(Item item, @RequestParam("desc") String desc) {
		try {
			if (LOGGER.isInfoEnabled()){
				LOGGER.info("inf is [{}] and [{}]", item, desc);
			}
			if (StringUtils.isEmpty(item.getTitle())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

			this.itemService.saveItem(item, desc);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<EasyUIResult> queryItemList(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "30") Integer rows) {
		try {
			EasyUIResult result = this.itemService.queryItemList(page, rows);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
