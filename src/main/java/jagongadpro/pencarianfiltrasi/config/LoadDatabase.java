package jagongadpro.pencarianfiltrasi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jagongadpro.pencarianfiltrasi.model.Game;
import jagongadpro.pencarianfiltrasi.repository.GameRepository;

@Configuration
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(GameRepository repository) {
        return args -> {
            repository.save(new Game("1", "Mario", "Game petualangan Mario, penuh dengan tantangan di berbagai kerajaan", 200000, "Adventure", 100));
            repository.save(new Game("2", "Zelda", "Game Zelda klasik, jelajahi dunia dan temukan rahasia tersembunyi", 250000, "Adventure", 50));
            repository.save(new Game("3", "FIFA 2021", "Game sepak bola terbaru dengan grafik yang realistis", 500000, "Sports", 200));
            repository.save(new Game("4", "Call of Duty", "FPS game dengan berbagai mode dan map yang menantang", 600000, "Shooter", 150));
            repository.save(new Game("5", "The Witcher 3", "RPG mendalam dengan cerita yang kaya dan dunia yang luas", 400000, "RPG", 85));
            repository.save(new Game("6", "Stardew Valley", "Game simulasi pertanian dengan elemen RPG, bangun dan kembangkan pertanianmu", 150000, "Simulation", 120));
            repository.save(new Game("7", "Among Us", "Game multiplayer yang membutuhkan kerja sama dan penipuan", 50000, "Casual", 300));
            repository.save(new Game("8", "Cyberpunk 2077", "Game aksi dunia terbuka di kota masa depan yang penuh neon", 700000, "Action", 80));
            repository.save(new Game("9", "Monster Hunter World", "Berburu monster dalam dunia besar bersama teman atau solo", 300000, "Action RPG", 95));
            repository.save(new Game("10", "Civilization VI", "Strategi turn-based, bangun peradabanmu dari awal hingga mendominasi dunia", 450000, "Strategy", 110));
        };
    }
}