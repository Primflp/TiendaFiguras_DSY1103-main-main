package tienda_figuras.ms_clientes.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

@Table(name = "cliente")

@JsonPropertyOrder({
    "idCliente",
    "runCliente",
    "nombresCliente",
    "apPaternoCliente",
    "apMaternoCliente",
    "direccionCliente",
    "numeroCliente",
    "correoCliente",
    "contrasenaCliente",
    "listasDeseos"
})

public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(unique = true, nullable = false)
    private String runCliente;

    private String nombresCliente;
    private String apPaternoCliente;
    private String apMaternoCliente;
    private String direccionCliente;
    private Integer numeroCliente;
    private String correoCliente;
    private String contrasenaCliente;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("cliente")
    private List<ListaDeseos> listasDeseos;
}
