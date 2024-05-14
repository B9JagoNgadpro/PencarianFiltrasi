package jagongadpro.pencarianfiltrasi.repository;

import jagongadpro.pencarianfiltrasi.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, String>, JpaSpecificationExecutor<Game> {
    List<Game> findByNamaContaining(String nama);
    @Query("SELECT g FROM Game g WHERE " +
            "LOWER(g.nama) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(g.deskripsi) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Game> searchGames(String query);
}