package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertiesService {
	@Value("${img_location}")
	public String img_location;
	
	@Value("${img_base_url}")
	public String img_base_url;
}
