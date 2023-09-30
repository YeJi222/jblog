package com.poscodx.jblog.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.FileUploadService;
import com.poscodx.jblog.vo.BlogVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private BlogService blogService;
	
	// @ResponseBody
	@RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	public String index(
		@PathVariable("id") String blogId,
		@PathVariable("categoryNo") @Nullable Long categoryNo,
		@PathVariable("postNo") @Nullable Long postNo,
		Model model) {
		
		BlogVo vo = blogService.getBlogAdmin(blogId);
		model.addAttribute("blogVo", vo);
		
//		System.out.println("blog id : " + blogId);
//		System.out.println("categoryNo : " + categoryNo);
//		System.out.println("postNo : " + postNo);
		
		model.addAttribute("blogId", blogId);
		
		return "blog/main";
	}
	
	// @ResponseBody
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String blogId, Model model) {
		BlogVo vo = blogService.getBlogAdmin(blogId);
		model.addAttribute("blogVo", vo);
		model.addAttribute("selected", "basic");
		model.addAttribute("blogId", blogId);
		
		return "blog/admin-basic";
	}
	
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String blogId, Model model) {
		
		model.addAttribute("selected", "category");
		model.addAttribute("blogId", blogId);
		
		return "blog/admin-category";
	}
	
	@RequestMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String blogId, Model model) {
		
		model.addAttribute("selected", "write");
		model.addAttribute("blogId", blogId);
		
		return "blog/admin-write";
	}
	
	@RequestMapping("/main/update")
	public String update(
			@PathVariable("id") String blogId,
			BlogVo vo, 
			@RequestParam(value = "logo-file") MultipartFile file) {
		
		/* 이미지 파일 업로드 처리 */
		String url = fileUploadService.restore(file);

		// siteVo profile 셋 해주기 
		if(url == null) { // before url로 세팅 
			url = vo.getImage();
		}
		vo.setBlogId(blogId);
		vo.setImage(url);
		System.out.println("BlogVo" + vo);
//		
//		/* 
//		BlogVo blog = applicationContext.getBean(BlogVo.class);
//		*/
//		
		blogService.updateAdminBasic(vo);
//		
//		/* ApplicationContext 주입받은 것 사용 */ 
//		/*
//		site.setTitle(vo.getTitle());
//		site.setWelcome(vo.getWelcome());
//		site.setProfile(vo.getProfile());
//		site.setDescription(vo.getDescription());
//		*/ 
//		// BeanUtils.copyProperties(vo, blog); // 위 코드 한 줄로 대체 가능 
//		
		// return "blog/admin-basic";
		return "redirect:/" + vo.getBlogId() + "/admin/basic";
	}
}
