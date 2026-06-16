package tienda_figuras.ms_figuras.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

@Table(name = "categoriaFigura")

@JsonPropertyOrder({
    "idCategoriaFigura",
    "nombreCategoriaFigura"
})

public class CategoriaFigura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCategoriaFigura;
    
    private String nombreCategoriaFigura;

    @ManyToOne
    @JoinColumn(name = "idFigura") 
    @JsonIgnoreProperties("categoriaFigura")
    private Figura figura;

}

