package com.poscodx.jblog.controller;

import java.util.List;
import java.util.Optional;

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
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

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
		@PathVariable("categoryNo") Optional<Long> categoryNo,
		@PathVariable("postNo") Optional<Long> postNo,
		Model model) {
		
		BlogVo vo = blogService.getBlogAdmin(blogId);
		// System.out.println("vo : " + vo);
		
		if(vo == null) { // blogId 없으면 안뜨게 
			return "error/404";
		}
		
		model.addAttribute("blogVo", vo);
		model.addAttribute("blogId", blogId);
		
		List<CategoryVo> categoryList = blogService.getCategoryList(blogId);
		model.addAttribute("categoryList", categoryList);
		
		List<PostVo> postList = blogService.getPostList(blogId);
		model.addAttribute("postList", postList);
		
		// System.out.println("postList : " + postList);
		
		/*
		if (categoryNo.isPresent()) {
	        Long categoryNumber = categoryNo.get();
	    }
	    if (postNo.isPresent()) {
	        Long postNumber = postNo.get();
	    }
		*/
		
		return "blog/main";
	}
	
	////////////////// Basic //////////////////
	
	// @ResponseBody
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String blogId, Model model) {
		BlogVo vo = blogService.getBlogAdmin(blogId);
		model.addAttribute("blogVo", vo);
		model.addAttribute("selected", "basic");
		model.addAttribute("blogId", blogId);
		
		return "blog/admin-basic";
	}
	
	@RequestMapping("/basic/update")
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
		// System.out.println("BlogVo" + vo);
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

		return "redirect:/" + vo.getBlogId() + "/admin/basic";
	}
	
	////////////////// Category //////////////////
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String blogId, Model model) {
		
		model.addAttribute("selected", "category");
		model.addAttribute("blogId", blogId);
		
		List<CategoryVo> list = blogService.getCategoryList(blogId);
		model.addAttribute("list", list);
		
		return "blog/admin-category";
	}
	
	@RequestMapping("/category/add")
	public String update(
			@PathVariable("id") String blogId,
			CategoryVo vo) {
		
		vo.setBlogId(blogId);
		System.out.println("category vo : " + vo);
		
		blogService.addCategory(vo);
		
		return "redirect:/" + blogId + "/admin/category";
	}
	
	@RequestMapping("/category/delete/{no}")
	public String delete(
			@PathVariable("id") String blogId,
			@PathVariable("no") Long no) {
		
		blogService.deleteCategory(blogId, no);
		
		return "redirect:/" + blogId + "/admin/category";
	}
	
	////////////////// Post //////////////////
	@RequestMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String blogId, Model model) {
		
		model.addAttribute("selected", "write");
		model.addAttribute("blogId", blogId);
		
		List<CategoryVo> list = blogService.getCategoryList(blogId);
		model.addAttribute("list", list);
		
		return "blog/admin-write";
	}
	
	@RequestMapping("/write/add")
	public String adminWriteAdd(
			@PathVariable("id") String blogId, 
			@RequestParam(value="category", required=true, defaultValue="") String categoryNo,
			PostVo vo) {
		
		vo.setCategoryNo(categoryNo);
		System.out.println("post vo : " + vo);

		// add post 
		blogService.addPost(vo);
		
		return "redirect:/" + blogId;
	}
	
	
}
