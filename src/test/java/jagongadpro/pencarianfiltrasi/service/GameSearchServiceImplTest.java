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
import java.util.Optional;
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

    @Test
    public void testFindGameById_Found() {
        Game game = new Game("1", "Mario", "Description of Mario", 50, "Adventure", 10);
        when(gameRepository.findById("1")).thenReturn(Optional.of(game));

        CompletableFuture<GameResponse> future = gameSearchService.findGameById("1");
        GameResponse result = future.join();

        assertNotNull(result);
        assertEquals("Mario", result.getNama());
    }

    @Test
    public void testFindGameById_NotFound() {
        when(gameRepository.findById("2")).thenReturn(Optional.empty());

        CompletableFuture<GameResponse> future = gameSearchService.findGameById("2");
        GameResponse result = future.join();

        assertNull(result);
    }

    @Test
    public void testSearchGames() {
        Game game1 = new Game("1", "Mario", "Jump and run game", 50, "Adventure", 100);
        Game game2 = new Game("2", "Zelda", "Explore and discover", 70, "Adventure", 60);

        when(gameRepository.searchGames("Mario")).thenReturn(Arrays.asList(game1));
        when(gameRepository.searchGames("Adventure")).thenReturn(Arrays.asList(game1, game2));

        CompletableFuture<List<GameResponse>> futureMario = gameSearchService.searchGames("Mario");
        List<GameResponse> resultsMario = futureMario.join();
        assertNotNull(resultsMario);
        assertEquals(1, resultsMario.size());
        assertEquals("Mario", resultsMario.get(0).getNama());

        CompletableFuture<List<GameResponse>> futureAdventure = gameSearchService.searchGames("Adventure");
        List<GameResponse> resultsAdventure = futureAdventure.join();
        assertNotNull(resultsAdventure);
        assertEquals(2, resultsAdventure.size());
        assertEquals("Mario", resultsAdventure.get(0).getNama());
        assertEquals("Zelda", resultsAdventure.get(1).getNama());
    }
}