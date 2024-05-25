package jagongadpro.pencarianfiltrasi.service;

import jagongadpro.pencarianfiltrasi.dto.GameResponse;
import jagongadpro.pencarianfiltrasi.dto.WebResponse;
import jagongadpro.pencarianfiltrasi.model.Game;
import jagongadpro.pencarianfiltrasi.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class GameSearchServiceImpl implements GameSearchService {

    private final GameRepository gameRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${microservice.game.url}")
    private String gameServiceUrl;

    @Autowired
    public GameSearchServiceImpl(GameRepository gameRepository, WebClient.Builder webClientBuilder) {
        this.gameRepository = gameRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public void setGameServiceUrl(String gameServiceUrl) {
        this.gameServiceUrl = gameServiceUrl;
    }

    @Override
    public CompletableFuture<List<GameResponse>> findGamesByName(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                WebResponse<List<GameResponse>> response = webClientBuilder.build()
                        .get()
                        .uri(gameServiceUrl + "/games/get?nama=" + name)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<WebResponse<List<GameResponse>>>() {})
                        .block();
                return response.getData();
            } catch (WebClientResponseException e) {
                throw e;
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Override
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
            try {
                WebResponse<GameResponse> response = webClientBuilder.build()
                        .get()
                        .uri(gameServiceUrl + "/games/" + id)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<WebResponse<GameResponse>>() {})
                        .block();
                return response != null ? response.getData() : null;
            } catch (WebClientResponseException e) {
                throw e;
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Override
    public CompletableFuture<List<GameResponse>> searchGames(String query) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                WebResponse<List<GameResponse>> response = webClientBuilder.build()
                        .get()
                        .uri(gameServiceUrl + "/games/get?nama=" + query)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<WebResponse<List<GameResponse>>>() {})
                        .block();
                return response.getData();
            } catch (WebClientResponseException e) {
                throw e;
            } catch (Exception e) {
                throw e;
            }
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