package tienda_figuras.ms_clientes.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tienda_figuras.ms_clientes.dto.FiguraDTO;

@FeignClient(name = "ms-figuras", url = "http://localhost:8081/api/v1/msfiguras/figuras")

public interface FiguraClientRest {

    @GetMapping("/buscarporid/{id}")
    public FiguraDTO obtenerDetalleFigura (@PathVariable("id") Long id);

}
