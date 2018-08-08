package com.elementspeed.framework.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *  转发ifream请求到指定页面
 */
@Controller
@RequestMapping("/system/ifream")
public class SysIfreamCtrl {
	
	@RequestMapping("/toUrl")
	public ModelAndView toUrl(@RequestParam String url) {
		if(url.indexOf(".") != -1)
			return new ModelAndView(url.substring(0, url.indexOf(".")));
		else
			return new ModelAndView("forward:/" + url);
	}
}
