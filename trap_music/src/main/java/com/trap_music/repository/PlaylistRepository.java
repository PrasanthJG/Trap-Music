package com.trap_music.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trap_music.entity.Playlist;
import com.trap_music.entity.User;

public interface PlaylistRepository extends JpaRepository<Playlist, Integer>{

	List<Playlist> findByUser(User user);		// Method signature to find playlists by associated user

}