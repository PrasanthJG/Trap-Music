package com.trap_music.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class NavigationController {
	 	
		@GetMapping("/")
		public String index() {
			return "index";
		}

	    @GetMapping("/auth/register")
	    public String register() {
	        return "auth/register";
	    }

	    @GetMapping("/auth/login")
	    public String login() {
	        return "auth/login";
	    }

	    @GetMapping("/auth/forgotpassword")
	    public String updatePassword() {
	        return "auth/updatepassword";
	    }
	    
	    @GetMapping("/auth/adminhomepage")
	    public String adminHomepage() {
	        return "auth/adminhomepage";
	    }

	    @GetMapping("/auth/customerhomepage")
	    public String customerHomepage() {
	        return "auth/customerhomepage";
	    }
	    
	    @GetMapping("/songs/addsongs")
	    public String addSongs() {
	        return "songs/addsongs";
	    }

	    @GetMapping("/auth/subscriptionpage")
	    public String payment() {
	        return "auth/subscriptionpage";
	    }
	    
	    @GetMapping("auth/about-us")
	    public String aboutUs() {
	        return "auth/about-us";
	    }
	    
	    
	    @GetMapping("/logout")
	    public String logoutUser(HttpSession session) {
	        session.invalidate(); // Invalidate session on logout
	        return "index";
	    }
}
