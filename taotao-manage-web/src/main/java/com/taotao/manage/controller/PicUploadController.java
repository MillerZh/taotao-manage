package com.taotao.manage.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manage.bean.PicUploadResult;
import com.taotao.manage.service.PropertiesService;

@Controller
@RequestMapping("/pic")
public class PicUploadController {
	@Autowired
	private PropertiesService properties;

	private static final ObjectMapper mapper = new ObjectMapper();

	private static final String[] IMG_TYPE = new String[] { ".jpg", ".png" };

	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String upload(@RequestParam("uploadFile") MultipartFile uploadFile, HttpServletResponse response)
			throws Exception {
		// 校验文件格式
		Boolean isLegal = false;
		for (String type : IMG_TYPE) {
			if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
				isLegal = true;
				break;
			}
		}

		// 定义返回对象
		PicUploadResult result = new PicUploadResult();

		// 校验文件错误
		result.setError(isLegal ? 0 : 1);

		// 生成返回文件
		String filePath = getFilePath(uploadFile.getOriginalFilename());
		String picUrl = StringUtils.replace(StringUtils.substringAfter(filePath, properties.img_location), "\\", "/");
		result.setUrl(properties.img_base_url + picUrl);

		// 文件写入磁盘
		File file = new File(filePath);
		uploadFile.transferTo(file);

		// 校验文件内容
		isLegal = false;
		try {
			BufferedImage bi = ImageIO.read(file);
			if (!(bi == null)) {
				result.setWidth(bi.getWidth() + "");
				result.setHeight(bi.getHeight() + "");
				isLegal = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setError(isLegal ? 0 : 1);

		// 如果不合法，就删除文件
		if (!isLegal) {
			file.delete();
		}

		// 将java对象转化成json字符串
		return mapper.writeValueAsString(result);
	}

	// D:\\Java\\data\\img\\2015\\11\\13\\20151113111111111.jpg
	private String getFilePath(String originalFilename) {
		// 创建文件路径
		Date now = new Date();
		String path = properties.img_location + File.separator + new DateTime(now).toString("yyyy") + File.separator
				+ new DateTime(now).toString("MM") + File.separator + new DateTime(now).toString("dd");
		File file = new File(path);
		if (!file.isDirectory()) {
			file.mkdirs();
		}

		// 生成文件名
		String name = new DateTime(now).toString("yyyyMMddhhmmssSSSS") + RandomUtils.nextInt(100, 9999) + "."
				+ StringUtils.substringAfterLast(originalFilename, ".");
		return path + File.separator + name;
	}
}
