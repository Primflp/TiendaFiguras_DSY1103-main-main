package tienda_figura.ms_pedidos.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pedido")

@JsonPropertyOrder({

    "idPedido",
    "estadoPedido",
    "direccionEnvio",
    "fechaEntrega",
    "fechaEmision",
    "detallePedidos",
    "tipoPago"

})

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;
    private String estadoPedido;
    private String direccionEnvio;
    private Date fechaEntrega;
    private Date fechaEmision;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("pedido")
    private List<DetallePedido> detallePedidos;
    
    @ManyToOne
    @JoinColumn(name = "idTipoPago")
    @JsonIgnoreProperties("listaPedidos")
    private TipoPago tipoPago;

    private Long idCliente;
}
