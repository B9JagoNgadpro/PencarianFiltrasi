package jagongadpro.pencarianfiltrasi.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameResponse {
    private String id;
    private String nama;
    private String deskripsi;
    private Integer harga;
    private String kategori;
    private Integer stok;
}
