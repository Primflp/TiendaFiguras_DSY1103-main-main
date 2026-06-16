package tienda_figura.ms_preventa.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "detallepreventa")

@JsonPropertyOrder({

    "idDetallePreventa",
    "precioPreventa",
    "fechaInicioPreventa",
    "fechaTerminoPreventa",
    "preventa",

})

public class DetallePreventa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetallePreventa;

    private int precioPreventa;
    private Date fechaInicioPreventa;
    private Date fechaTerminoPreventa;

    @ManyToOne
    @JoinColumn(name = "idPreventa")
    @JsonIgnoreProperties("listaDetallePreventa")
    private Preventa preventa;

}
