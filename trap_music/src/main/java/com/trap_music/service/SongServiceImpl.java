package com.trap_music.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trap_music.entity.Song;
import com.trap_music.repository.SongRepository;
import com.trap_music.repository.UserRepository;

@Service
public class SongServiceImpl implements SongService{
	@Autowired
	public SongRepository songRepository;
	
	@Autowired
	public UserRepository userRepository;

	@Override
	public boolean songExists(String name) {
		Song song = songRepository.findByName(name);				// Check if a song with the given name exists
		return song != null;
	}

	@Override
	public void addSong(Song song) {
		 songRepository.save(song);									// Adding & Save the song to the database
	}

	@Override
	public List<Song> fetchAllSongs() {
		List<Song> songslist = songRepository.findAll();			// Fetch all songs from the database
		return songslist;
	}

	@Override
	public List<Song> searchSongs(String keyword) {					// Search songs by name or artist containing the given keyword
		return songRepository.findByNameContainingIgnoreCaseOrArtistContainingIgnoreCase(keyword, keyword);
	}	
	

	@Override
	public void updateSong(Song song) {
		songRepository.save(song);									 // Update the song in the database
	}

	@Override
	public Song getSongById(int songId) {
	    return songRepository.findById(songId).orElse(null);		// Get the song by its ID from the database
	}

}