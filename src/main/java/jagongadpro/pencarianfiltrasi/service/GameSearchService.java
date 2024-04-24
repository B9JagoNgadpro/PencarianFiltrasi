package jagongadpro.pencarianfiltrasi.service;

import jagongadpro.pencarianfiltrasi.model.Game;
import jagongadpro.pencarianfiltrasi.repository.GameRepository;

import java.util.List;

public class GameSearchService {

    private final GameRepository gameRepository;

    public GameSearchService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> findGamesByName(String name) {
        return null;
    }
}