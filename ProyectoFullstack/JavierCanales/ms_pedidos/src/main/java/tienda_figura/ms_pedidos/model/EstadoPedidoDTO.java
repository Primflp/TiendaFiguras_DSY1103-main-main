package tienda_figura.ms_pedidos.model;

import java.sql.Date;

public interface EstadoPedidoDTO {
    
    String getEstadoPedido();
    String getDireccionEnvio();
    Date getFechaEntrega();

}
