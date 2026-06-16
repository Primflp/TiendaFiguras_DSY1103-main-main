package tienda_figura.ms_tienda_empresa.dtofigura;

import lombok.Data;

@Data
public class FiguraDTO {
    private Long idFigura;
    private String nombreFigura;
    private float tamanoFigura;
    private String marcaFigura;
    private String tipoCajaFigura;
}
