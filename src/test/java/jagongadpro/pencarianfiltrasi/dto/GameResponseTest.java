package jagongadpro.pencarianfiltrasi.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameResponseTest {

    @Test
    void testGameResponseGettersAndSetters() {
        GameResponse response = GameResponse.builder()
                .id("1")
                .nama("Test Game")
                .deskripsi("Test Desc")
                .harga(100)
                .kategori("Action")
                .stok(5)
                .build();

        assertEquals("1", response.getId());
        assertEquals("Test Game", response.getNama());
        assertEquals("Test Desc", response.getDeskripsi());
        assertEquals(100, response.getHarga());
        assertEquals("Action", response.getKategori());
        assertEquals(5, response.getStok());
    }

    @Test
    void testGameResponseBuilder() {
        GameResponse response = GameResponse.builder()
                .id("1")
                .nama("Test Game")
                .deskripsi("Test Desc")
                .harga(100)
                .kategori("Action")
                .stok(5)
                .build();

        assertNotNull(response);
    }
}
