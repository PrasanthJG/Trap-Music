package com.trap_music.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;    
    public String name;
    public String email;
    public String password;
    public String gender;
    public String role;
    public boolean premiumAccount;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)		// One-to-many relationship mapping with playlists owned by this user
    public List<Playlist> playlists = new ArrayList<>();
    
    // Many-to-many relationship mapping with favorite songs
    @ManyToMany(fetch = FetchType.EAGER)							// Specifies eager loading strategy for loading associated entities eagerly 
    @JoinTable(
            name = "user_favorite_song",							// Name of the join table in the database
            joinColumns = @JoinColumn(name = "user_id"),			// Column name in the join table that references the user's ID
            inverseJoinColumns = @JoinColumn(name = "song_id")		// Column name in the join table that references the song's ID
)
            
    public List<Song> favoriteSongs = new ArrayList<>();

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int id, String name, String email, String password, String gender, String role, boolean premiumAccount,
			List<Playlist> playlists, List<Song> favoriteSongs) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.role = role;
		this.premiumAccount = premiumAccount;
		this.playlists = playlists;
		this.favoriteSongs = favoriteSongs;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isPremiumAccount() {
		return premiumAccount;
	}

	public void setPremiumAccount(boolean premiumAccount) {
		this.premiumAccount = premiumAccount;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	public List<Song> getFavoriteSongs() {
		return favoriteSongs;
	}

	public void setFavoriteSongs(List<Song> favoriteSongs) {
		this.favoriteSongs = favoriteSongs;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", gender="
				+ gender + ", role=" + role + ", premiumAccount=" + premiumAccount + ", playlists=" + playlists
				+ ", favoriteSongs=" + favoriteSongs + "]";
	}
    
	
}