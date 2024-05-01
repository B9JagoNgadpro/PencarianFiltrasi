package jagongadpro.pencarianfiltrasi.controller;

import jagongadpro.pencarianfiltrasi.model.Game;
import jagongadpro.pencarianfiltrasi.service.GameSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameSearchService gameSearchService;

    @Autowired
    public GameController(GameSearchService gameSearchService) {
        this.gameSearchService = gameSearchService;
    }

    @GetMapping
    public ResponseEntity<List<Game>> searchGamesByName(@RequestParam("name") String name) {
        List<Game> games = gameSearchService.findGamesByName(name);
        return ResponseEntity.ok(games);
    }
}