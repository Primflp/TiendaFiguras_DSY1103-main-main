package tienda_figura.ms_preventa.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "preventa")

@JsonPropertyOrder({

    "idPreventa",
    "nombrePreventa",
    "listaDetallePreventa"

})

public class Preventa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPreventa;
    
    private String nombrePreventa;

    @OneToMany(mappedBy = "preventa", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("preventa")
    private List<DetallePreventa> listaDetallePreventa;
    
}
