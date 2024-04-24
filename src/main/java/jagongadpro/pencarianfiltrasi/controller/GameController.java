package jagongadpro.pencarianfiltrasi.controller;

import jagongadpro.pencarianfiltrasi.dto.WebResponse;
import jagongadpro.pencarianfiltrasi.dto.GameResponse;
import jagongadpro.pencarianfiltrasi.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping("/api/games/search")
    public ResponseEntity<WebResponse<List<GameResponse>>> searchGamesByName(@RequestParam("name") String name) {
        List<GameResponse> games = gameService.searchByName(name);
        return ResponseEntity.ok(WebResponse.<List<GameResponse>>builder().data(games).build());
    }
}