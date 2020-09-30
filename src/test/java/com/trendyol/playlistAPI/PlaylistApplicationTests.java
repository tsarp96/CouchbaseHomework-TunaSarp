package com.trendyol.playlistAPI;

import com.trendyol.playlistAPI.domain.Playlist;
import com.trendyol.playlistAPI.repository.PlaylistRepository;
import com.trendyol.playlistAPI.service.PlaylistService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;


@SpringBootTest
class PlaylistApplicationTests {

    @Mock
    PlaylistRepository playlistRepository = mock(PlaylistRepository.class);

    @Autowired
    private PlaylistService playlistService;

    @Test
    public void createPlaylistByUserId_whenUserIdIsNotNumeric_shouldThrowException(){
        //Arrange
        String test_string = "-";
        Playlist playlist = new Playlist();
        //Act
        Throwable throwable = catchThrowable(() -> playlistService.createPlaylist(playlist));
        //Verify
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class).hasMessage("Input must be a number");
    }

    @Test
    public void createPlaylistByUserId_whenEverythingIsOk_shouldReturnTrue(){

    }

}
