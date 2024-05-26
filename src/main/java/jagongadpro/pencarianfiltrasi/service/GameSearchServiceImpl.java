package jagongadpro.pencarianfiltrasi.service;

import jagongadpro.pencarianfiltrasi.dto.GameResponse;
import jagongadpro.pencarianfiltrasi.dto.WebResponse;
import jagongadpro.pencarianfiltrasi.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
                String url = StringUtils.trimTrailingCharacter(gameServiceUrl, '/') + "/games/get?nama=" + name;
                WebResponse<List<GameResponse>> response = webClientBuilder.build()
                        .get()
                        .uri(url)
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
            try {
                String url = StringUtils.trimTrailingCharacter(gameServiceUrl, '/') + "/games/filter";
                URI uri = UriComponentsBuilder.fromUriString(url)
                        .queryParam("nama", name != null ? name : "")
                        .queryParam("kategori", category != null ? category : "")
                        .queryParam("hargaMin", minPrice != null ? minPrice : "")
                        .queryParam("hargaMax", maxPrice != null ? maxPrice : "")
                        .build().toUri();
                WebResponse<List<GameResponse>> response = webClientBuilder.build()
                        .get()
                        .uri(uri)
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
    public CompletableFuture<GameResponse> findGameById(String id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String url = StringUtils.trimTrailingCharacter(gameServiceUrl, '/') + "/games/" + id;
                WebResponse<GameResponse> response = webClientBuilder.build()
                        .get()
                        .uri(url)
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
                String url = StringUtils.trimTrailingCharacter(gameServiceUrl, '/') + "/games/get?nama=" + query;
                WebResponse<List<GameResponse>> response = webClientBuilder.build()
                        .get()
                        .uri(url)
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
}