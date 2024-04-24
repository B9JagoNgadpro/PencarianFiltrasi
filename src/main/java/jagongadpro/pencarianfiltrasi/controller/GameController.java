package jagongadpro.pencarianfiltrasi.controller;

public class GameController {
    @GetMapping("/api/games/search")
    public ResponseEntity<WebResponse<List<GameResponse>>> searchGamesByName(@RequestParam("name") String name) {
        List<GameResponse> games = gameService.searchByName(name);
        return ResponseEntity.ok(WebResponse.<List<GameResponse>>builder().data(games).build());
    }
}
