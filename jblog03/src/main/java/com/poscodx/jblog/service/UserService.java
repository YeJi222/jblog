package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.vo.UserVo;
import com.poscodx.jblog.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public void join(UserVo userVo) {
		userRepository.insert(userVo);
	}

	public UserVo getUser(String id, String password) {
		UserVo authUser = userRepository.findByIdAndPassword(id, password);
		
		return authUser;
	}

//	public UserVo getUser(Long no) {
//		UserVo authUser = userRepository.findByNo(no);
//		
//		return authUser;
//	}
//
//	public void update(UserVo userVo) {
//		userRepository.update(userVo);
//	}
}
