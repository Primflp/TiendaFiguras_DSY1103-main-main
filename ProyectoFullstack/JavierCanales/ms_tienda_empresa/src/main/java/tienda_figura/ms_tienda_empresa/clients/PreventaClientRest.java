package tienda_figura.ms_tienda_empresa.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tienda_figura.ms_tienda_empresa.dto.PreventaDTO;

@FeignClient(name = "ms-preventa", url = "localhost:8084/api/v1/preventas/preventa")
public interface PreventaClientRest {
    @GetMapping("/{id}")
    PreventaDTO obtenerDetallePreventa(@PathVariable("id") Long id);

}
