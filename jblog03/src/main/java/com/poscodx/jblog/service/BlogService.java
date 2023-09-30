package com.poscodx.jblog.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.UserVo;
import com.poscodx.jblog.repository.BlogRepository;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;
	
	public void makeBlog(@Valid UserVo vo) {
		blogRepository.insert(vo);
	}
	
	public BlogVo getBlogAdmin(String blogId) {
		return blogRepository.find(blogId);
	}

	public void updateAdminBasic(BlogVo vo) {
		blogRepository.updateBasic(vo);
		
	}

	public void addCategory(CategoryVo vo) {
		blogRepository.insertCategory(vo);
	}

	
	
}
