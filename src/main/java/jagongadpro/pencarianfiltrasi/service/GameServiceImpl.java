package jagongadpro.pencarianfiltrasi.service;

import jagongadpro.pencarianfiltrasi.dto.GameResponse;
import jagongadpro.pencarianfiltrasi.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepository gameRepository;

    @Override
    public List<GameResponse> searchByName(String name) {
        return null;
    }
}