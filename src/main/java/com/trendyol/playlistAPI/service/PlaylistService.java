package com.trendyol.playlistAPI.service;

import com.trendyol.playlistAPI.domain.Playlist;
import com.trendyol.playlistAPI.domain.Track;
import com.trendyol.playlistAPI.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository){
        this.playlistRepository = playlistRepository;
    }

    public List<Playlist> findAll(){
        return playlistRepository.findAll();
    }

    public void createPlaylist(Playlist playlist) {
        if(!isNumeric(playlist.getUserId())){
            throw new NumberFormatException("Input must be a number");
        }
        playlist.setUserId(playlist.getUserId());
        playlistRepository.createPlaylist(playlist);
    }

    public Playlist findById(String id) {
        return playlistRepository.findById(id);
    }

    public List<Playlist> findAllByUserId(String id) {
        return playlistRepository.findAllByUserId(id);
    }

    public void deleteById(String playlistId) {
        playlistRepository.deleteById(playlistId);
    }

    public void addTrackToPlaylist(String playlistId, Track track) {
        playlistRepository.addTrackToPlaylist(playlistId, track);
    }

    public void deleteTrackFromPlaylist(String playlistId, String trackId) {
        playlistRepository.deleteTrackFromPlaylist(playlistId,trackId);
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
