package com.poscodx.jblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.UserVo;

@Controller
public class MainController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("")
	public String index(Model model) {
		List<UserVo> userList = userService.getUsers();
		model.addAttribute("userList", userList);
		
		return "main/index";
	}
}
