package com.poscodx.jblog.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.UserVo;
import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BlogRepository blogRepository;

	@Transactional
	public void join(@Valid UserVo userVo, CategoryVo categoryVo) {
		userRepository.insert(userVo);
		blogRepository.insert(userVo);
		blogRepository.insertCategory(categoryVo);
	}

	public UserVo getUser(String id, String password) {
		UserVo authUser = userRepository.findByIdAndPassword(id, password);
		
		return authUser;
	}
}
