package jagongadpro.pencarianfiltrasi.service;

import jagongadpro.pencarianfiltrasi.dto.GameResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GameSearchService {
    CompletableFuture<List<GameResponse>> findGamesByName(String name);
    CompletableFuture<List<GameResponse>> filterGames(String name, String category, Integer minPrice, Integer maxPrice);
    CompletableFuture<GameResponse> findGameById(String id);
    CompletableFuture<List<GameResponse>> searchGames(String query);
}
