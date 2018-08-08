package com.elementspeed.system.login.controller;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.elementspeed.framework.base.controller.BaseCtrl;
import com.elementspeed.framework.common.util.SpringContextUtil;
import com.elementspeed.framework.common.util.StringUtil;
import com.elementspeed.mapper.system.entity.SysModulesDto;
import com.elementspeed.system.usermgt.service.SysModuleService;

/**
 * @ClassName: LoginCtrl
 * @Description: TODO(登陆Ctrl)
 * @author masn
 * @date 2018年8月4日 下午2:45:33
 */
@RequestMapping("/system")
@Controller
public class LoginCtrl extends BaseCtrl {

	@Resource
	public SysModuleService sysModuleService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model,HttpServletRequest request, HttpServletResponse response) {
		return "redirect:/system/toLayout";
	}
	
	@RequestMapping("/toLayout")
	public String toLayout(Model model, HttpServletRequest request) {
		List<SysModulesDto> modules = sysModuleService.getAll();
		//一级菜单
		List<SysModulesDto> level1List = getLevel1(modules);
		//二级菜单
		List<SysModulesDto> level2List = getChildren(modules, level1List);
		//三级菜单
		List<SysModulesDto> level3List = getChildren(modules, level2List);		
		addChildren(level2List, level3List);
		addChildren(level1List, level2List);		
		model.addAttribute("modules", level1List);		
		//页面标题
		model.addAttribute("page_title",SpringContextUtil.getMessage("报名注册管理系统"));		
		return "system/login/layout";
	}
	
	private void addChildren(List<SysModulesDto> parents, List<SysModulesDto> children) {
		for (SysModulesDto parent : parents) {
			for (SysModulesDto child : children) {
				if(StringUtil.isEqual(parent.getId(), child.getParentId())) {
					parent.addChild(child);
				}
			}
		}
	}

	private List<SysModulesDto> getChildren(List<SysModulesDto> modules, List<SysModulesDto> parents) {
		List<SysModulesDto> children = new LinkedList<SysModulesDto>();
		for (SysModulesDto parent : parents) {
			for (SysModulesDto sysModulesDto : modules) {
				if(StringUtil.isEqual(sysModulesDto.getParentId(), parent.getId())) {
					sysModulesDto.setParentName(parent.getI18nText());
					children.add(sysModulesDto);
				}
			}
		}
		return children;
	}

	private List<SysModulesDto> getLevel1(List<SysModulesDto> modules) {
		//一级菜单
		List<SysModulesDto> level1 = new LinkedList<SysModulesDto>();
		for (SysModulesDto sysModulesDto : modules) {
			if(sysModulesDto.isFirstLevel()) {
				level1.add(sysModulesDto);
			}
		}
		return level1;
	}
	
}