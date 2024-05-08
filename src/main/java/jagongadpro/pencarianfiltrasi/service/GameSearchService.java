package jagongadpro.pencarianfiltrasi.service;

import jagongadpro.pencarianfiltrasi.dto.GameResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GameSearchService {
    CompletableFuture<List<GameResponse>> findGamesByName(String name);
}