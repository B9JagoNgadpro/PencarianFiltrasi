package jagongadpro.pencarianfiltrasi.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import jagongadpro.pencarianfiltrasi.dto.GameResponse;
import jagongadpro.pencarianfiltrasi.model.Game;
import jagongadpro.pencarianfiltrasi.repository.GameRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GameSearchServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameSearchServiceImpl gameSearchService;

    @Test
    public void testFindGamesByName() {
        Game game1 = new Game("1", "Mario", "Description of Mario", 50, "Adventure", 10);
        Game game2 = new Game("2", "Zelda", "Description of Zelda", 60, "Adventure", 5);
        when(gameRepository.findByNamaContaining("Adventure")).thenReturn(Arrays.asList(game1, game2));

        List<GameResponse> results = gameSearchService.findGamesByName("Adventure");

        assertNotNull(results);
        assertEquals(2, results.size(), "Should return two games");
        assertEquals("Mario", results.get(0).getNama(), "First game should be Mario");
        assertEquals("Zelda", results.get(1).getNama(), "Second game should be Zelda");
    }

    @Test
    public void testFindGamesByName_NoResults() {
        when(gameRepository.findByNamaContaining("Nonexistent")).thenReturn(Arrays.asList());

        List<GameResponse> results = gameSearchService.findGamesByName("Nonexistent");

        assertNotNull(results);
        assertEquals(0, results.size(), "Should return no games");
    }
}