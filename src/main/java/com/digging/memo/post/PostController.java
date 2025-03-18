package com.digging.memo.post;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.digging.memo.post.domain.Post;
import com.digging.memo.post.service.PostService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@Controller
public class PostController {
	
	private final PostService postService;
	
	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping("/list-view")
	public String memoList(
			HttpSession session
			, Model model) {
		
		int userId = (Integer) session.getAttribute("userId");
		List<Post> postList = postService.getPostList(userId);
		
		model.addAttribute("postList", postList);
		
		return "post/list";
	}
	
	@GetMapping("/create-view")
	public String inputMemo() {
		return "post/input";
	}
	
	@GetMapping("/detail-view")
	public String memoDetail(
			@RequestParam int id
			, Model model) {
		
		Post post = postService.getPost(id);
		
		model.addAttribute("post", post);
		
		
		
		return "post/detail";
	}
}
