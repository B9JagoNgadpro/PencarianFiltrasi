package jagongadpro.pencarianfiltrasi.controller;

import jagongadpro.pencarianfiltrasi.dto.GameResponse;
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

    @Autowired
    public GameController(GameSearchService gameSearchService) {
        this.gameSearchService = gameSearchService;
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<GameResponse>>> searchGamesByName(@RequestParam("name") String name) {
        return gameSearchService.findGamesByName(name)
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
}