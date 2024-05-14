package jagongadpro.pencarianfiltrasi.repository;

import jagongadpro.pencarianfiltrasi.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, String>, JpaSpecificationExecutor<Game> {
    List<Game> findByNamaContaining(String nama);
}