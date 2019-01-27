package cn.becto.findthem.controller;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.becto.findthem.pojo.FindthemResult;
import cn.becto.findthem.pojo.ResultData;
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
}
