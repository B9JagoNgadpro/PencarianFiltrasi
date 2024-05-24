package jagongadpro.pencarianfiltrasi.controller;

import jagongadpro.pencarianfiltrasi.dto.GameResponse;
import jagongadpro.pencarianfiltrasi.model.Game;
import jagongadpro.pencarianfiltrasi.repository.GameRepository;
import jagongadpro.pencarianfiltrasi.service.GameSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameSearchService gameSearchService;
    private final GameRepository gameRepository;

    @Autowired
    public GameController(GameSearchService gameSearchService, GameRepository gameRepository) {
        this.gameSearchService = gameSearchService;
        this.gameRepository = gameRepository;
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<GameResponse>>> searchGamesByName(@RequestParam("name") String name) {
        return gameSearchService.findGamesByName(name)
                .thenApply(ResponseEntity::ok)
                .exceptionally(e -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @GetMapping("/search")
    public CompletableFuture<ResponseEntity<List<GameResponse>>> searchGames(@RequestParam("query") String query) {
        return gameSearchService.searchGames(query)
                .thenApply(ResponseEntity::ok)
                .exceptionally(e -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @GetMapping("/filter")
    public CompletableFuture<ResponseEntity<List<GameResponse>>> filterGames(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "minPrice", required = false) Integer minPrice,
            @RequestParam(value = "maxPrice", required = false) Integer maxPrice) {
        return gameSearchService.filterGames(name, category, minPrice, maxPrice)
                .thenApply(ResponseEntity::ok)
                .exceptionally(e -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<GameResponse>> getGameById(@PathVariable String id) {
        return gameSearchService.findGameById(id)
                .<ResponseEntity<GameResponse>>handle((gameResponse, ex) -> {
                    if (ex != null) {
                        return ResponseEntity.<GameResponse>status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                    } else if (gameResponse != null) {
                        return ResponseEntity.ok(gameResponse);
                    } else {
                        return ResponseEntity.notFound().build();
                    }
                });
    }

    @PostMapping("/add")
    public Game addGame(@RequestBody Game game) {
        return gameRepository.save(game);
    }
}