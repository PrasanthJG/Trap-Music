package com.trap_music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trap_music.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);

	User findByEmail(String email);

}
