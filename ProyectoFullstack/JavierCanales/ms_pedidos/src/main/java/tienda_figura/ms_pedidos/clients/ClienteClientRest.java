package tienda_figura.ms_pedidos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tienda_figura.ms_pedidos.dtoCliente.ClienteDTO;

@FeignClient(name = "ms-clientes", url = "localhost:8082/api/v1/msclientes")
public interface ClienteClientRest {
    @GetMapping("/clientes/buscarporid/{id}")
    ClienteDTO obtenerDatosCliente(@PathVariable("id") Long idCliente);
}
