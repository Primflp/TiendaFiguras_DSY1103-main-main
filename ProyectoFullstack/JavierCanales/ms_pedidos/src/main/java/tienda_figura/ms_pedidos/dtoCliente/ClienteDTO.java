package tienda_figura.ms_pedidos.dtoCliente;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long idCliente;
    private String nombresCliente;
    private String apPaternoCliente;
    private String direccionCliente;
    private String correoCliente;
}
