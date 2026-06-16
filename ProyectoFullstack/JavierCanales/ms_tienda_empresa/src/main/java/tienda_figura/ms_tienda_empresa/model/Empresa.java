package tienda_figura.ms_tienda_empresa.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "empresa")

@JsonPropertyOrder({

    "idEmpresa",
    "identificacionTributaria",
    "nombreEmpresa",
    "correoEmpresa",
    "telefonoEmpresa",
    "listaTienda"

})

public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpresa;

    private int identificacionTributaria;
    private String nombreEmpresa;
    private String correoEmpresa;
    private int telefonoEmpresa;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("empresa")
    private List<Tienda> listaTienda;

}
