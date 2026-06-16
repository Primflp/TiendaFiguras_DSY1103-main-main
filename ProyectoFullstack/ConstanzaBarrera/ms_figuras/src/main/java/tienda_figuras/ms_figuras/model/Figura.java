package tienda_figuras.ms_figuras.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

@Table(name = "figura")

@JsonPropertyOrder({
    "idFigura",
    "nombreFigura",
    "tamanoFigura",
    "marcaFigura",
    "tipoCajaFigura",
    "stockTotalFigura",
    "stockReservadoFigura",
    "categoriaFigura"
})

public class Figura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFigura;
    
    private String nombreFigura;
    private float tamanoFigura;
    private String marcaFigura;
    private String tipoCajaFigura;
    private Integer stockTotalFigura;
    private Integer stockReservadoFigura;

    @OneToMany(mappedBy = "figura", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("figura")
    private List<CategoriaFigura> categoriaFigura;

}