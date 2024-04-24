package jagongadpro.pencarianfiltrasi.service;

import jagongadpro.pencarianfiltrasi.model.Game;
import jagongadpro.pencarianfiltrasi.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GameSearchServiceImpl implements GameSearchService {

    private final GameRepository gameRepository;

    @Autowired
    public GameSearchServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public List<Game> findGamesByName(String nama) {
        return gameRepository.findByNameContaining(nama);
    }
}