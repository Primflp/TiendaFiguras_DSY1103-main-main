package tienda_figura.ms_pedidos.dtoFigura;

import java.sql.Date;

import lombok.Data;

@Data
public class ResponseFiguraDTO {
    private String nombreFigura;
    private float tamanoFigura;
    private String marcaFigura;
    private String tipoCajaFigura;
    private int cantidad;
    private int precioUnitario;
    private Double montoTotal;
    private String estadoPedido;
    private String direccionEnvio;
    private Date fechaEntrega;
    private Date fechaEmision;
}
