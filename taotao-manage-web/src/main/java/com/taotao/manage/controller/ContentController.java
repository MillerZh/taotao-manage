package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentService;

@RequestMapping("content")
@Controller
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveContent(Content content){
		try {
			content.setId(null);
			this.contentService.save(content);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/**
	 * 根据categoryId查询页面列表
	 * 
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<EasyUIResult> queryListByCategoryId(@RequestParam("categoryId") Long categoryId,
			@RequestParam(value = "page", defaultValue ="0") Integer page,
			@RequestParam(value = "rows", defaultValue = "10") Integer rows){
		try {
			EasyUIResult result = this.contentService.queryListByCategoryId(categoryId, page, rows);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		
		
	}
}