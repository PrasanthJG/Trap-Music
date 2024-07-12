package com.trap_music.service;

import java.util.List;

import com.trap_music.entity.Playlist;
import com.trap_music.entity.User;

public interface PlaylistService {

	void deletePlaylist(int playlistId);				// Method signature to delete a playlist by its ID

	void addPlaylist(Playlist playlist, User user);		// Method signature to add a playlist associated with a user

	List<Playlist> fetchPlaylistsByUser(User user);		// Method signature to fetch playlists by user
	
}
