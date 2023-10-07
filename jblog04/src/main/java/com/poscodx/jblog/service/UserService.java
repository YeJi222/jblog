package com.poscodx.jblog.service;

import java.util.List;

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
	public Boolean join(@Valid UserVo userVo, CategoryVo categoryVo) {
		int userExist = userRepository.userCheck(userVo.getId());
		
		if(userExist == 0) {
			userRepository.insert(userVo);
			blogRepository.insert(userVo);
			blogRepository.insertCategory(categoryVo);
			
			return true;
		} else {
			return false;
		}
	}

	public UserVo getUser(String id, String password) {
		UserVo authUser = userRepository.findByIdAndPassword(id, password);
		
		return authUser;
	}

	public List<UserVo> getUsers() {
		return userRepository.getUsers();
	}
}
