package cn.becto.findthem.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.becto.findthem.pojo.FindthemResult;
import cn.becto.findthem.service.FindthemService;

@Controller
public class FindthemController {
	
	@Autowired
	private FindthemService findthemService;
	
	/*
	 * 更新solr库
	 */
	@RequestMapping("/sync")
	public FindthemResult syncData() throws Exception {
		return findthemService.syncData();
	}
	
	
	/*
	 * 通过关键词检索，分页显示。如若无关键词，则显示全部
	 */
	@RequestMapping(value = "/find")
	public String findthemByKeyword(@RequestParam(defaultValue = "*") String keyword, 
			@RequestParam(defaultValue = "1") Integer page, Model model) throws Exception {
		keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
		FindthemResult result = findthemService.findthemByKeyword(keyword, page);
		model.addAttribute("result", result.getData());
		model.addAttribute("page", page);
		model.addAttribute("keyword", keyword);
		
		return "findthem";
	}
}
