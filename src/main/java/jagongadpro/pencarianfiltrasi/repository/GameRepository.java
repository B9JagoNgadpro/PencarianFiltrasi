package jagongadpro.pencarianfiltrasi.repository;

import jagongadpro.pencarianfiltrasi.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByNameContaining(String nama);
}