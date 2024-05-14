package jagongadpro.pencarianfiltrasi.service;

import jagongadpro.pencarianfiltrasi.dto.GameResponse;
import jagongadpro.pencarianfiltrasi.model.Game;
import jagongadpro.pencarianfiltrasi.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
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

    public CompletableFuture<List<GameResponse>> filterGames(String name, String category, Integer minPrice, Integer maxPrice) {
        return CompletableFuture.supplyAsync(() -> {
            Specification<Game> spec = (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                if (name != null && !name.isEmpty()) {
                    predicates.add(cb.like(cb.lower(root.get("nama")), "%" + name.toLowerCase() + "%"));
                }
                if (category != null && !category.isEmpty()) {
                    predicates.add(cb.equal(cb.lower(root.get("kategori")), category.toLowerCase()));
                }
                if (minPrice != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("harga"), minPrice));
                }
                if (maxPrice != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("harga"), maxPrice));
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            };
            return gameRepository.findAll(spec).stream()
                    .map(this::toGameResponse)
                    .collect(Collectors.toList());
        });
    }

    @Override
    public CompletableFuture<GameResponse> findGameById(String id) {
        return CompletableFuture.supplyAsync(() -> {
            Game game = gameRepository.findById(id).orElse(null);
            if (game != null) {
                return toGameResponse(game);
            }
            return null;
        });
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