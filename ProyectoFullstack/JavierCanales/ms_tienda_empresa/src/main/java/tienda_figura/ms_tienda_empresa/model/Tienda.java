package tienda_figura.ms_tienda_empresa.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tienda")

@JsonPropertyOrder({

    "idTienda",
    "nombreTienda",
    "direccionTienda",
    "telefonoTienda",
    "empresa",
    "idPreventa",
    "idFigura"

})

public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTienda;

    private String nombreTienda;
    private String direccionTienda;
    private int telefonoTienda;

    @ManyToOne
    @JoinColumn(name = "idEmpresa")
    @JsonIgnoreProperties("listaTienda")
    private Empresa empresa;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tiendaPreventas", joinColumns = @JoinColumn(name = "id_tienda"))
    private List<Long> idPreventa;

    private Long idFigura;

}
