package tienda_figura.ms_pedidos.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tipopago")

@JsonPropertyOrder({

    "idTipoPago",
    "nombreTipoPago",
    "listaPedidos",

})

public class TipoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoPago;

    private String nombreTipoPago;

    @OneToMany(mappedBy = "tipoPago", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("tipoPago")
    private List<Pedido> listaPedidos;
}