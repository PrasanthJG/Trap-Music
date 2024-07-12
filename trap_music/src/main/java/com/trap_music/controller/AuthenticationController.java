package com.trap_music.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trap_music.entity.User;
import com.trap_music.service.SongService;
import com.trap_music.service.UserService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/auth")		// Mapping all requests under '/auth' directory to this controller
@Controller
public class AuthenticationController {

    @Autowired
    public UserService userService;
    
    @Autowired
    public SongService songService;

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, HttpSession session) {
        boolean userExists = userService.emailExists(user.getEmail());			// check user already exist or not
        if (!userExists) {
            userService.addUser(user);					// Add user if not exist
            session.setAttribute("user", user); 		// Store the entire user object in the session
            return "redirect:login"; 					// Redirect to the login page after successful registration
        } else {
            return "redirect:login?error=user already exists";					// Redirect to login page with error message if user already exists
        }
    }

    @PostMapping("/login")
    @ResponseBody													//return value should be the response body
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password, HttpSession session) {
        if (userService.validateUser(email, password)) {			// Validates the user's email and password
            User user = userService.getUser(email);					// Retrieves user information from the database
            session.setAttribute("email", email);					// Stores user information in the session
            session.setAttribute("user", user);
            if (userService.getRole(email).equals("admin")) { 	
            	//Response alert handled in javascript --> see login html
                return ResponseEntity.ok().body(Map.of("success", true, "redirectUrl", "/auth/adminhomepage"));			// Returns a success response with redirect URL for admin login
            } else if (user.isPremiumAccount()) {
                return ResponseEntity.ok().body(Map.of("success", true, "redirectUrl", "/auth/customerhomepage"));		// Returns-redirect a success response for premium users
            } else {
                return ResponseEntity.ok().body(Map.of("success", true, "redirectUrl", "/auth/subscriptionpage"));		// Returns-redirect a success response for new users
            }
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Enter valid Email & Password"));
        }
    }


    @PostMapping("/updatepassword")
    public String updatePassword(@RequestParam("email") String email,@RequestParam("password") String newPassword,@RequestParam("confirm-password") String confirmPassword,Model model) {
        // Validate if the new password matches the confirmation
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "auth/updatepassword"; 					// Return to the update password form with an error message
        }
        userService.updatePassword(email, newPassword);		// Redirect to a success page or login page after updating the password
        return "auth/login";
    }
    
}
