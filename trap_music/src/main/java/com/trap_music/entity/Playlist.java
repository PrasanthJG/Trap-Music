package com.trap_music.entity;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Playlist {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)				// Auto-generating id for the playlist
    public int id;
	
    public String name;
    
    @ManyToOne 														// Many playlists can belong to one user
    @JoinColumn(name = "user_id")									// Mapping the user's id as a foreign key
    public User user;
    
    @ManyToMany														// Many-to-many relationship with songs
    public List<Song> songs = new ArrayList<>();					// List to hold songs in the playlist
    
    
	public Playlist() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Playlist(int id, String name, User user, List<Song> songs) {
		super();
		this.id = id;
		this.name = name;
		this.user = user;
		this.songs = songs;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public List<Song> getSongs() {
		return songs;
	}


	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}


	@Override
	public String toString() {
		return "Playlist [id=" + id + ", name=" + name + ", user=" + user + ", songs=" + songs + "]";
	}
	
	
}