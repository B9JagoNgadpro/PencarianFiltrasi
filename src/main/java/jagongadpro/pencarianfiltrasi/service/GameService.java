package jagongadpro.pencarianfiltrasi.service;

import jagongadpro.pencarianfiltrasi.dto.GameResponse;
import java.util.List;

public interface GameService {
    List<GameResponse> searchByName(String name);
}