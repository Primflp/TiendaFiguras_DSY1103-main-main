package tienda_figura.ms_tienda_empresa.dtofigura;

import lombok.Data;

@Data
public class ResponseFiguraDTO {
    private String nombreFigura;
    private float tamanoFigura;
    private String marcaFigura;
    private String tipoCajaFigura;
    private String nombreTienda;
    private String direccionTienda;
    private int telefonoTienda;
}
