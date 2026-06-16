package tienda_figura.ms_pedidos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tienda_figura.ms_pedidos.dtoFigura.FiguraDTO;

@FeignClient(name = "ms-figuras", url = "localhost:8081/api/v1/msfiguras")
public interface FiguraClientRest {
    @GetMapping("/figuras/buscarporid/{id}")
    FiguraDTO obtenerDatosFiguras(@PathVariable("id") Long idFigura);
}
