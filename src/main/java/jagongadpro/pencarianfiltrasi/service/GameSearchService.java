package jagongadpro.pencarianfiltrasi.service;

import jagongadpro.pencarianfiltrasi.dto.GameResponse;
import java.util.List;

public interface GameSearchService {
    List<GameResponse> findGamesByName(String nama);
}
