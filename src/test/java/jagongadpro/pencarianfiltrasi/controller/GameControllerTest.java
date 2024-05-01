package jagongadpro.pencarianfiltrasi.controller;

import jagongadpro.pencarianfiltrasi.model.Game;
import jagongadpro.pencarianfiltrasi.service.GameSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GameControllerTest {

    @Mock
    private GameSearchService gameSearchService;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSearchGamesByName() throws Exception {
        String name = "test";
        List<Game> games = new ArrayList<>();
        Game game1 = new Game("id1", "Test Game 1", "Description 1", 50, "Action", 10);
        Game game2 = new Game("id2", "Test Game 2", "Description 2", 40, "Adventure", 20);
        games.add(game1);
        games.add(game2);

        when(gameSearchService.findGamesByName(name)).thenReturn(games);

        ResponseEntity<List<Game>> responseEntity = gameController.searchGamesByName(name);

        assertEquals(2, responseEntity.getBody().size());
        assertEquals(game1, responseEntity.getBody().get(0));
        assertEquals(game2, responseEntity.getBody().get(1));
    }
}