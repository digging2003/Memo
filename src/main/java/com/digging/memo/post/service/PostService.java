package com.digging.memo.post.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.digging.memo.post.domain.Post;
import com.digging.memo.post.repository.PostRepository;

import jakarta.persistence.PersistenceException;

@Service
public class PostService {

	private final PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	public boolean addPost(int userId, String title, String contents) {
			
		Post post = Post.builder()
		.userId(userId)
		.title(title)
		.contents(contents)
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
		
	
}
