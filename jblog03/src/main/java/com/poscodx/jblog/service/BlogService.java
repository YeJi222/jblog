package com.poscodx.jblog.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.UserVo;
import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.repository.UserRepository;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;
	
	public void makeBlog(@Valid UserVo vo) {
		blogRepository.insert(vo);
	}

	public void updateAdminBasic(BlogVo vo) {
		blogRepository.updateBasic(vo);
		
	}

	
	
}
