package jagongadpro.pencarianfiltrasi.service;

import jagongadpro.pencarianfiltrasi.dto.GameResponse;
import jagongadpro.pencarianfiltrasi.repository.GameRepository;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class GameSearchServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameSearchServiceImpl gameSearchService;

    private MockWebServer mockWebServer;

    @Value("${microservice.game.url}")
    private String gameServiceUrl;

    @BeforeEach
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        gameServiceUrl = mockWebServer.url("/").toString();
        gameSearchService = new GameSearchServiceImpl(gameRepository, WebClient.builder());
        gameSearchService.setGameServiceUrl(gameServiceUrl);
    }

    @AfterEach
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void testFindGamesByName() throws Exception {
        String name = "Mario";
        String mockResponseBody = "{ \"data\": [ { \"id\": \"1\", \"nama\": \"Mario\", \"deskripsi\": \"Description of Mario\", \"harga\": 50, \"kategori\": \"Adventure\", \"stok\": 10 }, { \"id\": \"2\", \"nama\": \"Zelda\", \"deskripsi\": \"Description of Zelda\", \"harga\": 60, \"kategori\": \"Adventure\", \"stok\": 5 } ] }";
        mockWebServer.enqueue(new MockResponse().setBody(mockResponseBody).addHeader("Content-Type", "application/json"));

        CompletableFuture<List<GameResponse>> future = gameSearchService.findGamesByName(name);
        List<GameResponse> results = future.join();

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Mario", results.get(0).getNama());
        assertEquals("Zelda", results.get(1).getNama());
    }

    @Test
    public void testFindGamesByName_NoResults() throws Exception {
        String name = "Nonexistent";
        String mockResponseBody = "{ \"data\": [] }";
        mockWebServer.enqueue(new MockResponse().setBody(mockResponseBody).addHeader("Content-Type", "application/json"));

        CompletableFuture<List<GameResponse>> future = gameSearchService.findGamesByName(name);
        List<GameResponse> results = future.join();

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void testFindGameById_Found() throws Exception {
        String gameId = "1";
        String mockResponseBody = "{ \"data\": { \"id\": \"1\", \"nama\": \"Mario\", \"deskripsi\": \"Description of Mario\", \"harga\": 50, \"kategori\": \"Adventure\", \"stok\": 10 } }";
        mockWebServer.enqueue(new MockResponse().setBody(mockResponseBody).addHeader("Content-Type", "application/json"));

        CompletableFuture<GameResponse> future = gameSearchService.findGameById(gameId);
        GameResponse result = future.join();

        assertNotNull(result);
        assertEquals("Mario", result.getNama());
    }

    @Test
    public void testFindGameById_NotFound() throws Exception {
        String gameId = "2";
        String mockResponseBody = "{ \"data\": null }";
        mockWebServer.enqueue(new MockResponse().setBody(mockResponseBody).addHeader("Content-Type", "application/json"));

        CompletableFuture<GameResponse> future = gameSearchService.findGameById(gameId);
        GameResponse result = future.join();

        assertTrue(result == null);
    }

    @Test
    public void testSearchGames() throws Exception {
        String query = "Mario";
        String mockResponseBody = "{ \"data\": [ { \"id\": \"1\", \"nama\": \"Super Mario\", \"deskripsi\": \"Jump and run game\", \"harga\": 50, \"kategori\": \"Adventure\", \"stok\": 100 }, { \"id\": \"2\", \"nama\": \"Zelda\", \"deskripsi\": \"Explore and discover\", \"harga\": 70, \"kategori\": \"Adventure\", \"stok\": 60 } ] }";
        mockWebServer.enqueue(new MockResponse().setBody(mockResponseBody).addHeader("Content-Type", "application/json"));

        CompletableFuture<List<GameResponse>> future = gameSearchService.searchGames(query);
        List<GameResponse> results = future.join();

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Super Mario", results.get(0).getNama());
        assertEquals("Zelda", results.get(1).getNama());
    }
}