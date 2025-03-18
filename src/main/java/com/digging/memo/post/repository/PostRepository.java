package com.digging.memo.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digging.memo.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	// WHERE `userId` = #{} ORDER BY `id` DESC;
	public List<Post> findByUserIdOrderByIdDesc(int userId);
	

}
