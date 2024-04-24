package jagongadpro.pencarianfiltrasi.service;

import jagongadpro.pencarianfiltrasi.model.Game;
import java.util.List;

public interface GameSearchService {
    List<Game> findGamesByName(String nama);
}