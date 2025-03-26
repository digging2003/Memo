package com.digging.memo.post.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.digging.memo.common.FileManager;
import com.digging.memo.post.domain.Post;
import com.digging.memo.post.repository.PostRepository;

import jakarta.persistence.PersistenceException;

@Service
public class PostService {

	private final PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	public boolean addPost(int userId, String title, String contents, MultipartFile file) {
			
		String urlPath = FileManager.saveFile(userId, file);
		
		Post post = Post.builder()
		.userId(userId)
		.title(title)
		.contents(contents)
		.imagePath(urlPath)
		.build();
		
		try {
			postRepository.save(post);
		} catch(PersistenceException e) {
			return false;
		}
		
		return true;
		
	}
	
	public List<Post> getPostList(int userId) {
		return postRepository.findByUserIdOrderByIdDesc(userId);
	}
	
	public Post getPost(int id) {
		
		Optional<Post> optionalPost = postRepository.findById(id);
		
		return optionalPost.orElse(null);
	}
	
	// 메모 수정 기능
	public boolean updatePost(int id, String title, String contents) {
		Optional<Post> optionalPost = postRepository.findById(id);
		
		if(optionalPost.isPresent()) {
			
			Post post = optionalPost.get();
			
			post = post.toBuilder()
			.title(title)
			.contents(contents)
			.build();
			
			try {
				postRepository.save(post);
			} catch(PersistenceException e) {
				return false;
			}
		} else {
			return false;
		}
		
		return true;
	}
	
	// 메모 삭제 기능
	public boolean deletePost(int id) {
		Optional<Post> optionalPost = postRepository.findById(id);
		if(optionalPost.isPresent()) {
			
			Post post = optionalPost.get();
			
			try {
				postRepository.deleteById(id);
			} catch(PersistenceException e) {
				return false;
			}
			
		} else {
			return false;
		}
		
		return true;
		
	}
}
