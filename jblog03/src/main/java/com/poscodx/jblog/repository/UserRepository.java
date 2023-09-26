package com.poscodx.jblog.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.UserVo;

@Repository
public class UserRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public Boolean insert(UserVo vo) {
		int count = sqlSession.insert("user.insert", vo);
		return count == 1;
	}
	
	public UserVo findByEmailAndPassword(String email, String password) {
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("password", password);
		
		return sqlSession.selectOne("user.findByEmailAndPassword", map);
	}
	
	public UserVo findByNo(long no) {
		return sqlSession.selectOne("user.findByNo", no);
	}
	
	public void update(UserVo vo) {
		sqlSession.update("user.update", vo);
	}
}
