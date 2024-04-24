package jagongadpro.pencarianfiltrasi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import jagongadpro.pencarianfiltrasi.model.Game;
import jagongadpro.pencarianfiltrasi.repository.GameRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class GameSearchServiceTests {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameSearchService gameSearchService;

    @Test
    void testFindGamesByName() {
        Game game1 = new Game("Game 1", "Description", 20, "Action", 100);
        Game game2 = new Game("Game 2", "Description", 25, "Action", 150);
        when(gameRepository.findByNameContaining("Game")).thenReturn(Arrays.asList(game1, game2));

        List<Game> results = gameSearchService.findGamesByName("Game");
        assertEquals(2, results.size());
    }
}