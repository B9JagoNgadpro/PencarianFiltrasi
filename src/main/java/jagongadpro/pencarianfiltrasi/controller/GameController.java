package jagongadpro.pencarianfiltrasi.controller;

import jagongadpro.pencarianfiltrasi.dto.GameResponse;
import jagongadpro.pencarianfiltrasi.service.GameSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
                .thenApply(ResponseEntity::ok);
    }
}