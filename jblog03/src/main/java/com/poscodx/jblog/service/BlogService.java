package com.poscodx.jblog.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;
import com.poscodx.jblog.vo.UserVo;
import com.poscodx.jblog.repository.BlogRepository;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;
	
//	public void makeBlog(@Valid UserVo vo) {
//		blogRepository.insert(vo);
//	}
	
	public BlogVo getBlogAdmin(String blogId) {
		return blogRepository.find(blogId);
	}

	public void updateAdminBasic(BlogVo vo) {
		blogRepository.updateBasic(vo);
	}

	public void addCategory(CategoryVo vo) {
		blogRepository.insertCategory(vo);
	}

	public List<CategoryVo> getCategoryList(String blogId) {
		return blogRepository.findAll(blogId);
	}

	public void deleteCategory(String blogId, Long no) {
		blogRepository.deleteCategory(blogId, no);
	}

	public void addPost(PostVo vo) {
		blogRepository.insertPost(vo);
	}

	public List<PostVo> getPostList(String blogId) {
		return blogRepository.findAllPost(blogId);
	}

	public List<PostVo> getPostListByCategory(String blogId, Long categoryNo) {
		return blogRepository.findAllPostByCategory(blogId, categoryNo);
	}

//	public PostVo getPostByPostNo(String blogId, Long postNo) {
//		return blogRepository.findPostByPostNo(blogId, postNo);
//	}
}
