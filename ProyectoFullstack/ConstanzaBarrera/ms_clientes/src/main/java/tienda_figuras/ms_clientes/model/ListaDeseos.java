package tienda_figuras.ms_clientes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

@Table(name = "listaDeseos")

@JsonPropertyOrder({
    "idListaDeseos",
    "nombreListaDeseos",
    "idFigura",
    "cliente"
})

public class ListaDeseos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idListaDeseos;

    private String nombreListaDeseos;

    @ManyToOne
    @JoinColumn(name = "idCliente") 
    @JsonIgnoreProperties("listaDeseos")
    private Cliente cliente;

    private Long idFigura;
}
