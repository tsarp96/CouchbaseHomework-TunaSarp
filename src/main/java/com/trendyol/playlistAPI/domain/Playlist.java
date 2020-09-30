package com.trendyol.playlistAPI.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Playlist {

    private String playlistId;
    private String name;
    private String description;
    private int followersCount;
    private List<Track> tracks;
    private int trackCount;
    private String userId;

    public Playlist(){
        this.playlistId = UUID.randomUUID().toString();
        this.name = "default";
        this.description = "default description";
        this.followersCount = 0;
        this.tracks = new ArrayList<>();
        this.trackCount = 0;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public int getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return playlistId;
    }

    public void setId(String id) {
        this.playlistId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
