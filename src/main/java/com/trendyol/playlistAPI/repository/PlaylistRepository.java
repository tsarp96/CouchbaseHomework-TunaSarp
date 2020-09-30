package com.trendyol.playlistAPI.repository;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.codec.TypeRef;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.kv.LookupInResult;
import com.couchbase.client.java.kv.MutateInSpec;
import com.couchbase.client.java.query.QueryResult;
import com.trendyol.playlistAPI.domain.Playlist;
import com.trendyol.playlistAPI.domain.Track;
import org.springframework.stereotype.Repository;
import java.util.*;
import static com.couchbase.client.java.kv.LookupInSpec.get;
import static com.couchbase.client.java.kv.MutateInSpec.decrement;
import static com.couchbase.client.java.kv.MutateInSpec.replace;


@Repository
public class PlaylistRepository {

    private final Cluster couchbaseCluster;
    private final Collection playlistCollection;

    public PlaylistRepository(Cluster couchbaseCluster, Collection playlistCollection){
        this.couchbaseCluster = couchbaseCluster;
        this.playlistCollection = playlistCollection;
    }

    public List<Playlist> findAll(){
        String statement = "Select id,name,description,followersCount,tracks,trackCount,userId from playlist";
        QueryResult query = couchbaseCluster.query(statement);
        return query.rowsAs(Playlist.class);
    }

    public void createPlaylist(Playlist playlist) {
        playlistCollection.insert(playlist.getId(), playlist);
    }

    public void update(Playlist playlist) {
        playlistCollection.replace(playlist.getId(), playlist);
    }

    public Playlist findById(String id) {
        GetResult getResult = playlistCollection.get(id);
        Playlist playlist = getResult.contentAs(Playlist.class);
        return playlist;
    }

    public List<Playlist> findAllByUserId(String id) {
        String statement = String.format("Select id, name, description, followersCount, tracks, trackCount, userId from playlist where userId = '%s'", id);
        QueryResult query = couchbaseCluster.query(statement);
        return query.rowsAs(Playlist.class);
    }

    public void deleteById(String playlistId) {
        String statement = String.format("Delete from playlist where playlist.id = \"%s\"",playlistId);
        QueryResult query = couchbaseCluster.query(statement);
    }

    public void addTrackToPlaylist(String playlistId, Track track) {
        playlistCollection.mutateIn(playlistId, Arrays.asList(
                MutateInSpec.arrayAppend("tracks", Collections.singletonList(track)),
                MutateInSpec.increment("trackCount",1)
        ));
    }

    public void deleteTrackFromPlaylist(String playlistId, String trackId) {
        List<Track> tracks = getTracks(playlistId);
        tracks.removeIf(track -> track.getId().equals(trackId));
        playlistCollection.mutateIn(playlistId, Arrays.asList(
                replace("tracks", tracks),
                decrement("trackCount", 1)
        ));
    }

    public List<Track> getTracks(String playlistId){
        LookupInResult result = playlistCollection.lookupIn(
                playlistId,
                Collections.singletonList(get("tracks"))
        );
        return result.contentAs(0, new TypeRef<List<Track>>() {
        });
    }
}
