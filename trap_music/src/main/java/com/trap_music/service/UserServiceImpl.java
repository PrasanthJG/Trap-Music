package com.trap_music.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trap_music.entity.User;
import com.trap_music.repository.SongRepository;
import com.trap_music.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	 	@Autowired
	    public UserRepository userRepository;
	 	
	 	@Autowired
		public SongRepository songRepository;

	    @Override
	    public String addUser(User user) {
	        userRepository.save(user);
	        return "User added successfully";
	    }

	    @Override
	    public boolean emailExists(String email) {
	        return userRepository.existsByEmail(email);
	    }

	    @Override
	    public boolean validateUser(String email, String password) {
	        User user = userRepository.findByEmail(email);
	        return user != null && user.getPassword().equals(password);
	    }

	    @Override
	    public String getRole(String email) {
	        User user = userRepository.findByEmail(email);
	        return user != null ? user.getRole() : null;
	    }

	    @Override
	    public User getUser(String email) {
	        return userRepository.findByEmail(email);
	    }

	    @Override
	    public void updateUser(User user) {
	        userRepository.save(user);
	    }
	    

		@Override
		public boolean updatePassword(String email, String newPassword) {
			User user = userRepository.findByEmail(email);
		    if (user != null) {
		        user.setPassword(newPassword);
		        userRepository.save(user);
		        return true;
		    } else {
		      return false;
		    }
		}

}