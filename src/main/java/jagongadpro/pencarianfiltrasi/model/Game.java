package jagongadpro.pencarianfiltrasi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Game {
    @Id
    String id;
    String nama;
    String deskripsi;
    Integer harga;
    String kategori;
    Integer stok;
}
