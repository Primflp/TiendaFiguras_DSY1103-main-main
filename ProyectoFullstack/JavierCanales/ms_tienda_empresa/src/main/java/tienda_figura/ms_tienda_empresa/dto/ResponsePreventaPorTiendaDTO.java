package tienda_figura.ms_tienda_empresa.dto;

import java.util.List;

import lombok.Data;

@Data
public class ResponsePreventaPorTiendaDTO {
    private String nombreTienda;
    private String direccionTienda;
    private int telefonoTienda;
    private List<PreventaDTO> preventasAsociadas;
}
