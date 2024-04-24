import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Collections;
import java.util.List;

import jagongadpro.pencarianfiltrasi.dto.GameResponse;
import jagongadpro.pencarianfiltrasi.model.Game;
import jagongadpro.pencarianfiltrasi.repository.GameRepository;
import jagongadpro.pencarianfiltrasi.service.GameService;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GameServiceTests {
    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    public void testSearchByNameNotFound() {
        when(gameRepository.findByNameContainingIgnoreCase("NonExistingGame")).thenReturn(Collections.emptyList());
        List<GameResponse> result = gameService.searchByName("NonExistingGame");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchByNameFound() {
        when(gameRepository.findByNameContainingIgnoreCase("Mario")).thenReturn(List.of(new Game("Super Mario", ...)));
        List<GameResponse> result = gameService.searchByName("Mario");
        assertFalse(result.isEmpty());
        assertEquals("Super Mario", result.get(0).getNama());
    }
}