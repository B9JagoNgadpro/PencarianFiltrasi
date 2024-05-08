package jagongadpro.pencarianfiltrasi.controller;

import jagongadpro.pencarianfiltrasi.dto.GameResponse;
import jagongadpro.pencarianfiltrasi.service.GameSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GameControllerTest {

    @Mock
    private GameSearchService gameSearchService;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchGamesByName() throws Exception {
        String name = "test";
        List<GameResponse> games = new ArrayList<>();
        GameResponse game1 = GameResponse.builder()
                .id("id1")
                .nama("Test Game 1")
                .deskripsi("Description 1")
                .harga(50)
                .kategori("Action")
                .stok(10)
                .build();

        GameResponse game2 = GameResponse.builder()
                .id("id2")
                .nama("Test Game 2")
                .deskripsi("Description 2")
                .harga(40)
                .kategori("Adventure")
                .stok(20)
                .build();

        games.add(game1);
        games.add(game2);

        when(gameSearchService.findGamesByName(name)).thenReturn(CompletableFuture.completedFuture(games));

        CompletableFuture<ResponseEntity<List<GameResponse>>> responseFuture = gameController.searchGamesByName(name);
        ResponseEntity<List<GameResponse>> responseEntity = responseFuture.join();

        assertEquals(2, responseEntity.getBody().size());
        assertEquals(game1, responseEntity.getBody().get(0));
        assertEquals(game2, responseEntity.getBody().get(1));
    }
}