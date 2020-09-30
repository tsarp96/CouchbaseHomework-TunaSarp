package com.trendyol.playlistAPI.controller;

import com.trendyol.playlistAPI.domain.Playlist;
import com.trendyol.playlistAPI.domain.Track;
import com.trendyol.playlistAPI.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService){
        this.playlistService = playlistService;
    }

    @GetMapping
    public ResponseEntity<List<Playlist>> listPlaylists(){
        return ResponseEntity.ok(playlistService.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> createPlaylist(@RequestBody  Playlist playlist){
        playlistService.createPlaylist(playlist);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(playlist.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<Playlist> findById(@PathVariable String playlistId){
        return ResponseEntity.ok(playlistService.findById(playlistId));
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<Playlist> deleteById(@PathVariable String playlistId){
        playlistService.deleteById(playlistId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<Playlist>> findAllByUserId(@RequestParam String userId){
        return ResponseEntity.ok(playlistService.findAllByUserId(userId));
    }

    @PostMapping("/{playlistId}/tracks")
    public ResponseEntity<Void> addTrackToPlaylist(@PathVariable String playlistId, Track track){
        playlistService.addTrackToPlaylist(playlistId, track);
        URI uri = URI.create(String.format("/playlists/%s/tracks/%s", playlistId, track.getId()));
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("{playlistId}/tracks/{trackId}")
    public ResponseEntity<Void> deleteTrackFromPlaylist(@PathVariable String playlistId,
                                                        @PathVariable String trackId){
        playlistService.deleteTrackFromPlaylist(playlistId,trackId);
        return ResponseEntity.noContent().build();
    }
}
