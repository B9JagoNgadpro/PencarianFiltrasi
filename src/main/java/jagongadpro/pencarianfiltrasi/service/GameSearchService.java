package jagongadpro.pencarianfiltrasi.service;

import jagongadpro.pencarianfiltrasi.model.Game;
import jagongadpro.pencarianfiltrasi.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameSearchService {

    @Autowired
    private GameRepository gameRepository;

    public List<Game> findGamesByName(String name) {
        return gameRepository.findByNameContaining(name);
    }
}