package jagongadpro.pencarianfiltrasi.service;

import jagongadpro.pencarianfiltrasi.dto.GameResponse;
import jagongadpro.pencarianfiltrasi.model.Game;
import jagongadpro.pencarianfiltrasi.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class GameSearchServiceImpl implements GameSearchService {

    private final GameRepository gameRepository;

    @Autowired
    public GameSearchServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public CompletableFuture<List<GameResponse>> findGamesByName(String name) {
        return CompletableFuture.supplyAsync(() ->
                gameRepository.findByNamaContaining(name).stream()
                        .map(this::toGameResponse)
                        .collect(Collectors.toList())
        );
    }

    private GameResponse toGameResponse(Game game) {
        return GameResponse.builder()
                .id(game.getId())
                .nama(game.getNama())
                .deskripsi(game.getDeskripsi())
                .harga(game.getHarga())
                .kategori(game.getKategori())
                .stok(game.getStok())
                .build();
    }
}