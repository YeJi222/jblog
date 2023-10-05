package com.poscodx.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;
import com.poscodx.jblog.vo.UserVo;

@Repository
public class BlogRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public Boolean insert(@Valid UserVo userVo) {
		BlogVo vo = new BlogVo();
		vo.setBlogId(userVo.getId());
		vo.setTitle("블로그 타이틀을 설정하세요");
		vo.setImage("/assets/images/spring-logo.jpg");
		
		int count = sqlSession.insert("blog.insert", vo);
		return count == 1;
	}
	
	public BlogVo find(String blogId) {
		return sqlSession.selectOne("blog.find", blogId);
	}

	public void updateBasic(BlogVo vo) {
		sqlSession.update("blog.updateBasic", vo);
		
	}

	public Boolean insertCategory(CategoryVo vo) {
		int count = sqlSession.insert("category.insertCategory", vo);
		return count == 1;
	}

	public List<CategoryVo> findAll(String blogId) {
		return sqlSession.selectList("category.findAll", blogId);
	}

	public int deleteCategory(String blogId, Long no) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("no", no);
        map.put("blogId", blogId);
        
		return sqlSession.delete("category.delete", map);
	}

	public Boolean insertPost(PostVo vo) {
		int count = sqlSession.insert("post.insertPost", vo);
		return count == 1;
	}

	public List<PostVo> findAllPost(String blogId) {
		return sqlSession.selectList("post.findAll", blogId);
	}

	public List<PostVo> findAllPostByCategory(String blogId, Long categoryNo) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("blogId", blogId);
        map.put("categoryNo", categoryNo);
        
		return sqlSession.selectList("post.findAllByCategory", map);
	}

	public int getPostCount(String blogId, Long categoryNo) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("blogId", blogId);
        map.put("categoryNo", categoryNo);
        
		return sqlSession.selectOne("post.getPostCount", map);
	}
}
