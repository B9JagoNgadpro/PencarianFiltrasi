package jagongadpro.pencarianfiltrasi.controller;

import jagongadpro.pencarianfiltrasi.dto.GameResponse;
import jagongadpro.pencarianfiltrasi.service.GameSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Test
    public void testFilterGames() {
        String name = "Zelda";
        String category = "Adventure";
        Integer minPrice = 10;
        Integer maxPrice = 100;
        List<GameResponse> expectedGames = Arrays.asList(
                new GameResponse("1", "Zelda", "A great game", 50, "Adventure", 15)
        );

        when(gameSearchService.filterGames(name, category, minPrice, maxPrice))
                .thenReturn(CompletableFuture.completedFuture(expectedGames));

        CompletableFuture<ResponseEntity<List<GameResponse>>> responseFuture =
                gameController.filterGames(name, category, minPrice, maxPrice);
        ResponseEntity<List<GameResponse>> responseEntity = responseFuture.join();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals("Zelda", responseEntity.getBody().get(0).getNama());
    }

    @Test
    public void testGetGameById_Found() {
        String gameId = "1";
        GameResponse gameResponse = new GameResponse("1", "Mario", "Description of Mario", 50, "Adventure", 10);

        when(gameSearchService.findGameById(gameId)).thenReturn(CompletableFuture.completedFuture(gameResponse));

        CompletableFuture<ResponseEntity<GameResponse>> responseFuture = gameController.getGameById(gameId);
        ResponseEntity<GameResponse> responseEntity = responseFuture.join();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(gameResponse, responseEntity.getBody());
    }

    @Test
    public void testGetGameById_NotFound() {
        String gameId = "2";

        when(gameSearchService.findGameById(gameId)).thenReturn(CompletableFuture.completedFuture(null));

        CompletableFuture<ResponseEntity<GameResponse>> responseFuture = gameController.getGameById(gameId);
        ResponseEntity<GameResponse> responseEntity = responseFuture.join();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }
}