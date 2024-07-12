package com.trap_music.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.trap_music.entity.User;
import com.trap_music.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
public class SubscriptionController {

    @Autowired
    private UserService userService;

    @PostMapping("/createOrder")
    @ResponseBody
    public String createOrder() {
        Order order = null;
        try {													// My razorpay ID
            RazorpayClient razorpay = new RazorpayClient("rzp_test_bFLRfwc71TUtp7", "Qbl3K7HL7JVB4VRSrJFlacyo");

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", 50000); // Example amount -- 50000 paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "receipt#1");
            JSONObject notes = new JSONObject();
            notes.put("notes_key_1", "Tea, Earl Grey, Hot");
            orderRequest.put("notes", notes);

            order = razorpay.orders.create(orderRequest);		  // Create the order using Razorpay API
        } catch (Exception e) {
            System.out.println("Exception while creating order");
        }
        return order.toString();
    }

    @PostMapping("/verify")
    @ResponseBody
    public boolean verifyPayment(@RequestParam String orderId, @RequestParam String paymentId,
            @RequestParam String signature) {
        try {
        	// Initialize Razorpay client with your API key and secret
	        @SuppressWarnings("unused")
            RazorpayClient razorpayClient = new RazorpayClient("rzp_test_bFLRfwc71TUtp7", "Qbl3K7HL7JVB4VRSrJFlacyo");
	        
	        // Create a signature verification data string
            String verificationData = orderId + "|" + paymentId;
            
            // Use Razorpay's utility function to verify the signature
            boolean isValidSignature = Utils.verifySignature(verificationData, signature, "Qbl3K7HL7JVB4VRSrJFlacyo");

            return isValidSignature;
        } catch (RazorpayException e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/auth/payment-success")
    public ModelAndView paymentSuccess(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        String email = (String) session.getAttribute("email");		// Retrieve email from session
        User user = userService.getUser(email);						// Retrieve user from database based on email
        if (user != null) {
            user.setPremiumAccount(true);							// Set user's account as premium
            userService.updateUser(user);							// Update user's details in the database
            modelAndView.setViewName("redirect:/auth/customerhomepage");
        } else {
            // If user is not found, redirect to login page
            modelAndView.setViewName("redirect:/auth/login");
        }
        return modelAndView;										// Pass data between the controller and the view in Spring MVC
    }
}
