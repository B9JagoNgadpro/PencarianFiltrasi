package jagongadpro.pencarianfiltrasi.service;

import jagongadpro.pencarianfiltrasi.dto.GameResponse;
import jagongadpro.pencarianfiltrasi.model.Game;
import jagongadpro.pencarianfiltrasi.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

        CompletableFuture<List<GameResponse>> future = gameSearchService.findGamesByName("Adventure");
        List<GameResponse> results = future.join();

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Mario", results.get(0).getNama());
        assertEquals("Zelda", results.get(1).getNama());
    }

    @Test
    public void testFindGamesByName_NoResults() {
        when(gameRepository.findByNamaContaining("Nonexistent")).thenReturn(Arrays.asList());

        CompletableFuture<List<GameResponse>> future = gameSearchService.findGamesByName("Nonexistent");
        List<GameResponse> results = future.join();

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void testFilterGames() {
        Game game1 = new Game("1", "Mario", "Description of Mario", 50, "Adventure", 10);
        Game game2 = new Game("2", "Zelda", "Description of Zelda", 60, "Adventure", 5);

        when(gameRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(game1, game2));

        CompletableFuture<List<GameResponse>> future = gameSearchService.filterGames("Zel", "Adventure", 10, 100);
        List<GameResponse> results = future.join();

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Mario", results.get(0).getNama());
        assertEquals("Zelda", results.get(1).getNama());
    }

    @Test
    public void testFilterGames_NoResults() {
        when(gameRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList());

        CompletableFuture<List<GameResponse>> future = gameSearchService.filterGames("Nonexistent", "Sports", 100, 500);
        List<GameResponse> results = future.join();

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}