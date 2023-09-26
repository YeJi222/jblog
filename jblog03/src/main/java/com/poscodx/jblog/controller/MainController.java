package com.poscodx.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	@RequestMapping("")
	public String index() {
		System.out.println("main controller");
		return "main/index";
	}
}
