package com.poscodx.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	
	@ResponseBody
	@RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	public String index(
		@PathVariable("id") String blogId,
		@PathVariable("categoryNo") Long categoryNo,
		@PathVariable("postNo") Long postNo) {
		return "BlogController.index()";
	}
	
	@ResponseBody
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String blogId) {
		return "BlogController.adminBasic()";
	}
	
}
