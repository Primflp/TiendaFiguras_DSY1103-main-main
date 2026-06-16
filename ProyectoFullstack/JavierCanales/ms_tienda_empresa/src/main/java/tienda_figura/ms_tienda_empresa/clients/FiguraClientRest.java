package tienda_figura.ms_tienda_empresa.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tienda_figura.ms_tienda_empresa.dtofigura.FiguraDTO;

@FeignClient(name = "ms-figura", url = "localhost:8081/api/v1/msfiguras")
public interface FiguraClientRest {
    @GetMapping("/figuras/buscarporid/{id}")
    FiguraDTO obtenerDatosFigura(@PathVariable("id") Long id);
}
