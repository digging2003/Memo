package com.digging.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digging.memo.user.domain.User;
import com.digging.memo.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

// API 구성을 위한 Controller 
// @ReseponseBody
@RequestMapping("/user")
@RestController // @Controller + @ResponseBody
public class UserRestController {
	
	private final UserService userService;
	
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	// 회원 가입 API
	@PostMapping("/join")
	public Map<String, String> join(
			@RequestParam("loginId") String loginId
			, @RequestParam("password") String password
			, @RequestParam("name") String name
			, @RequestParam("email") String email) {
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(userService.addUser(loginId, password, name, email)) {
			// 성공
			resultMap.put("result", "success");
		} else {
			// 실패
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
	// 로그인 API 
	@PostMapping("/login")
	public Map<String, String> login(
			@RequestParam("loginId") String loginId
			, @RequestParam("password") String password
			, HttpServletRequest request) {
		
		User user = userService.getUser(loginId, password);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(user != null) {
			// 성공
			HttpSession session = request.getSession();
			// 로그인 성공 이후 사용자 정보를 session에 저장한다. 
			// session은 특정 클라이언트의 정보를 저장한다.
			// 다른 요청에서도 같은 클라이언트라면 해당 값을 사용할 수 있다.
			session.setAttribute("userId", user.getId());
			session.setAttribute("userName", user.getName());
			
			resultMap.put("result", "success");
		} else {
			// 실패
			resultMap.put("result", "fail");
		}
		
		return resultMap;
		
	}
	
	
	
	
	
	
	
	
	

}