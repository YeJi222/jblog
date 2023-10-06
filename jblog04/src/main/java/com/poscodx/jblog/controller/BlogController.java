package com.poscodx.jblog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	public String index(
		@PathVariable("id") String blogId,
		@PathVariable("categoryNo") Optional<Long> categoryNo,
		@PathVariable("postNo") Optional<Long> postNo,
		@RequestParam(value="type", required=true, defaultValue="") String type,
		Model model) {
		
		BlogVo vo = blogService.getBlogAdmin(blogId);
		if(vo == null) { // blogId 없으면 안뜨게 
			return "error/404";
		}
		
		model.addAttribute("blogVo", vo);
		model.addAttribute("blogId", blogId);
		
		List<CategoryVo> categoryList = blogService.getCategoryList(blogId);
		model.addAttribute("categoryList", categoryList);
		
		List<PostVo> postList = new ArrayList<>();
		Long postCount = (long) 0;
		String typeStr = "";
		
		if(categoryNo.isEmpty() && postNo.isEmpty()) { // blogId만 입력받는 경우 
			postList = blogService.getPostList(blogId);
			typeStr = "total";
		} else if(categoryNo.isPresent() && postNo.isEmpty()) { // blogId, categoryNo 입력받은 경우 
			postList = blogService.getPostListByCategory(blogId, categoryNo.get());
			typeStr = "category";
		} else { // blogId, categoryNo, postNo 입력받은 경우
			postList = new ArrayList<>();
			if("total".equals(type)) {
				postList = blogService.getPostList(blogId);
			} else {
				postList = blogService.getPostListByCategory(blogId, categoryNo.get());
			}
			
			Long idx = (long) 0;
			for(PostVo data : postList) {
				if(data.getNo() == postNo.get()) break;
				idx++;
			}
			postCount = idx;
			typeStr = type;
		}
		emptyPost(postList, model); // post가 없는 경우
		
		model.addAttribute("postList", postList);
		model.addAttribute("postNo", postCount);
		model.addAttribute("type", typeStr);
		
		return "blog/main";
	}
	
	public void emptyPost(List<PostVo> postList, Model model) {
		if(postList.isEmpty()) {
			PostVo postVo = new PostVo();
			postVo.setContents("아직 등록된 게시글이 없습니다");
			postList.add(postVo);
			model.addAttribute("postList", postList);
		}
	}
	
	////////////////// Basic //////////////////
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
		
		System.out.println("file - " + vo);

		// siteVo profile 셋 해주기 
		if(url == null) { // before url로 세팅 
			url = vo.getImage();
		}
		vo.setBlogId(blogId);
		vo.setImage(url);
		blogService.updateAdminBasic(vo);
		
		return "redirect:/" + vo.getBlogId() + "/admin/basic";
	}
	
	////////////////// Category //////////////////
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String blogId, Model model) {
		
		model.addAttribute("selected", "category");
		model.addAttribute("blogId", blogId);
		
		List<CategoryVo> list = blogService.getCategoryList(blogId);
		List<String> postCountList = new ArrayList<>();
		for(int i = 0 ; i < list.size() ; i++) {
			Long categoryNo = list.get(i).getNo();
			System.out.println("category name : " + list.get(i).getName());
			
			int postCount = blogService.getPostCount(blogId, categoryNo);
			postCountList.add(Integer.toString(postCount));
		}
		
		model.addAttribute("list", list);
		model.addAttribute("postCountList", postCountList);
		
		String title = blogService.getBlogAdmin(blogId).getTitle();
		BlogVo blogVo = new BlogVo();
		blogVo.setTitle(title);
		
		model.addAttribute("blogVo", blogVo);
		
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
		
		String title = blogService.getBlogAdmin(blogId).getTitle();
		BlogVo blogVo = new BlogVo();
		blogVo.setTitle(title);
		
		model.addAttribute("blogVo", blogVo);
		
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
