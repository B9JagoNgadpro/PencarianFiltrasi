package jagongadpro.pencarianfiltrasi.service;

import jagongadpro.pencarianfiltrasi.model.Game;
import jagongadpro.pencarianfiltrasi.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameSearchService {

    private final GameRepository gameRepository;

    @Autowired
    public GameSearchService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> findGamesByName(String nama) {
        return gameRepository.findByNameContaining(nama);
    }
}