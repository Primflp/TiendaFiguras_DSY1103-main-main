package tienda_figura.ms_pedidos.dtoCliente;

import java.sql.Date;

import lombok.Data;

@Data
public class ResponseClienteDTO {
    private String nombresCliente;
    private String apPaternoCliente;
    private String direccionCliente;
    private String correoCliente;
    private String estadoPedido;
    private String direccionEnvio;
    private Date fechaEntrega;
    private Date fechaEmision;
}
