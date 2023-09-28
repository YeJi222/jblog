package com.poscodx.jblog.controller;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	
	// @ResponseBody
	@RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	public String index(
		@PathVariable("id") String blogId,
		@PathVariable("categoryNo") @Nullable Long categoryNo,
		@PathVariable("postNo") @Nullable Long postNo) {
		
		System.out.println("blog id : " + blogId);
		System.out.println("categoryNo : " + categoryNo);
		System.out.println("postNo : " + postNo);
		return "blog/main";
	}
	
	// @ResponseBody
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String blogId) {
		System.out.println("blog id : " + blogId);
		
		return "blog/admin-basic";
	}
	
}
