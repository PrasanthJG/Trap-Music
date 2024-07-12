package com.trap_music.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trap_music.entity.Playlist;
import com.trap_music.entity.Song;
import com.trap_music.entity.User;
import com.trap_music.service.PlaylistService;
import com.trap_music.service.SongService;

import jakarta.servlet.http.HttpSession;


@RequestMapping("/songs")					// Mapping all requests under '/songs' directory to this controller
@Controller
public class PlaylistController 
{
	@Autowired
	PlaylistService playlistService;
	
	@Autowired
	SongService songService;
	
	@GetMapping("/createplaylist")
	public String createPlayList(Model model) {
		List<Song> songslist=songService.fetchAllSongs(); 			// Fetching all songs using song service
		model.addAttribute("songslist",songslist); 					// Adding the songs in the model
		return "songs/createplaylist"; 								// returning createplaylist
	}
	
	@PostMapping("/addplaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist, HttpSession session) {
	    User user = (User) session.getAttribute("user");
	    if (user != null) {
	        playlist.setUser(user); 								// Associate the playlist with the logged-in user
	        playlistService.addPlaylist(playlist, user); 			// Pass the user object to addPlaylist method to add the playlist to the database
	        List<Song> songs = playlist.getSongs();
	        for (Song song : songs) {
	            song.getPlaylist().add(playlist); 					// Add the playlist to the song's playlist collection
	            songService.updateSong(song); 						// Update the song in the database
	        }
	    }
	    return "redirect:/songs/viewplaylist"; 						// Redirect to the viewPlaylists page
	}


	
	@GetMapping("/viewplaylist")
	public String viewPlaylists(Model model, HttpSession session) {
	    User user = (User) session.getAttribute("user");
	    if (user != null) {
	        List<Playlist> playlists = playlistService.fetchPlaylistsByUser(user);		 // Fetch playlists associated with the logged-in user
	        model.addAttribute("playlists", playlists);
	    }
	    return "songs/viewplaylist";
	}


	@PostMapping("/deleteplaylist")
	public String deletePlaylist(@RequestParam("playlistId") int playlistId) {
	    playlistService.deletePlaylist(playlistId);					// Delete the playlist from the database
	    return "redirect:/songs/viewplaylist";
	}

}
