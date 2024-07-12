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

import com.trap_music.entity.Song;
import com.trap_music.entity.User;
import com.trap_music.service.SongService;
import com.trap_music.service.UserService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/songs")						// Mapping all requests under '/songs' directory to this controller
@Controller
public class SongController {
    
	
    @Autowired									// Autowiring SongService to handle song-related operations
    public SongService songService;

    @Autowired									// Autowiring UserService to handle user-related operations
    public UserService userService;
    
    
    @PostMapping("/addsongs")
    public String addSongs(@ModelAttribute Song song, Model model) {
        boolean status = songService.songExists(song.getName());		// Check if the song already exists
        if (!status) {
            songService.addSong(song);									// Add the song if it does not exist
            return "redirect:/songs/viewsongs";							// Redirect to view all songs after adding
        } else {
            return "redirect:/auth/adminhomepage";						// Redirect to admin homepage if the song already exists
        }
    }
    
    @GetMapping("/viewsongs")
    public String viewSongs(Model model) {
        List<Song> songslist = songService.fetchAllSongs();				// Fetch all songs from the database
        model.addAttribute("songslist", songslist);						// Add songs to the model for rendering in the view
        return "songs/viewsongs";
    }
    
    @GetMapping("/search")
    public String searchSongs(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            List<Song> searchResults = songService.searchSongs(keyword);	// Search songs based on the provided keyword
            model.addAttribute("searchResults", searchResults);
        }
        return "songs/searchresults";
    }
    
    @GetMapping("/togglefavorite") 
    public String toggleFavorite(@RequestParam("songId") int songId, HttpSession session) {
        User user = (User) session.getAttribute("user");					// Retrieve the logged-in user from the session
        Song song = songService.getSongById(songId);						// Retrieve the song by its ID

        if (user != null && song != null) {
            List<Song> favoriteSongs = user.getFavoriteSongs();				// Retrieve the list of favorite songs for the user

            if (favoriteSongs.contains(song)) {								// Remove the song from favorites if already favorited
                favoriteSongs.remove(song);
                song.getFavoritedBy().remove(user);
            } else {
                favoriteSongs.add(song);									// Add the song to favorites if not favorited
                song.getFavoritedBy().add(user);
            }
            userService.updateUser(user);									// Update user and song entities in the database
            songService.updateSong(song);
            return "redirect:/songs/favorites";
        } else {
            return "redirect:/songs/viewsongs";
        }
    }


    
    @GetMapping("/favorites")
    public String viewFavorites(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");					// Retrieve the logged-in user from the session
        if (user != null) {
            List<Song> favorites = user.getFavoriteSongs();					// Retrieve the list of favorite songs for the user
            model.addAttribute("favorites", favorites);						// Add favorite songs to the model for rendering in the view
            return "songs/favorites"; 										// Return view for displaying favorite songs
        } else {															// Handle case when user is not authenticated
            return "redirect:/auth/login";
        }
    }


}