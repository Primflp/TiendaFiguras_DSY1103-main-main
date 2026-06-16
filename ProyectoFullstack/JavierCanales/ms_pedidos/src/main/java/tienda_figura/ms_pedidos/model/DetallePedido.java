package tienda_figura.ms_pedidos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "detallePedido")

@JsonPropertyOrder({

    "idDetallePedido",
    "cantidad",
    "precioUnitario",
    "montoTotal",
    "Pedido",

})

public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetallePedido;
    private int cantidad;
    private int precioUnitario;
    private Double montoTotal;

    @ManyToOne
    @JoinColumn(name = "idPedido")
    @JsonIgnoreProperties("detallePedidos")
    private Pedido pedido;

    private Long idFigura;

    @PostLoad
    @PrePersist
    @PreUpdate
    public void calcularMontoMasIva(){
        montoTotal = precioUnitario * 1.19;
    }

    
}
