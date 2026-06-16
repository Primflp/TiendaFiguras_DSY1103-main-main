package tienda_figura.ms_pedidos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda_figura.ms_pedidos.clients.ClienteClientRest;
import tienda_figura.ms_pedidos.dtoCliente.ClienteDTO;
import tienda_figura.ms_pedidos.dtoCliente.ResponseClienteDTO;
import tienda_figura.ms_pedidos.model.EstadoPedidoDTO;
import tienda_figura.ms_pedidos.model.Pedido;
import tienda_figura.ms_pedidos.repository.PedidoRepository;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteClientRest clienteClientRest;

    public List<Pedido> pedidoFindAll(){
        return pedidoRepository.findAll();
    }

    public Pedido pedidoFindById(Long id){
        return pedidoRepository.findById(id).orElse(null);
    }

    public Pedido pedidoSave(Pedido pedido){
        return pedidoRepository.save(pedido);
    }

    public void pedidoDelete(Long id){
        pedidoRepository.deleteById(id);
    }

    // custom

    public EstadoPedidoDTO verEstadoPedido(Long id){
        return pedidoRepository.verEstadoPedido(id);
    }

    public Pedido pedidoUpdate(Long id, Pedido pedidoUpdate){
    
        if (pedidoRepository.existsById(id)) {
            
            Pedido pedido = pedidoRepository.findById(id).orElse(null);

                pedido.setEstadoPedido(pedidoUpdate.getEstadoPedido());
                pedido.setDireccionEnvio(pedidoUpdate.getDireccionEnvio());
                pedido.setFechaEntrega(pedidoUpdate.getFechaEntrega());
                pedido.setFechaEmision(pedidoUpdate.getFechaEmision());
                pedido.setTipoPago(pedidoUpdate.getTipoPago());
                
            return pedidoRepository.save(pedido);
    
        }   
        return null;
    }

    // metodos custom para OpenFeign

    public ResponseClienteDTO buscarClientePorPedido(Long idPedido, Long idCliente){

        Pedido pedido = pedidoRepository.findById(idPedido)
        .orElseThrow(() -> new RuntimeException("hubo un error")); 

        ClienteDTO cliente = clienteClientRest.obtenerDatosCliente(idCliente);

            ResponseClienteDTO response = new ResponseClienteDTO();
                response.setNombresCliente(cliente.getNombresCliente());
                response.setApPaternoCliente(cliente.getApPaternoCliente());
                response.setDireccionCliente(cliente.getDireccionCliente());
                response.setCorreoCliente(cliente.getCorreoCliente());
                response.setEstadoPedido(pedido.getEstadoPedido());
                response.setDireccionEnvio(pedido.getDireccionEnvio());
                response.setFechaEntrega(pedido.getFechaEntrega());
                response.setFechaEmision(pedido.getFechaEmision());
            
            return response;

    }

    public Pedido vincularClienteAPedido(Long idPedido, Long idCliente){
        
        Pedido pedido = pedidoRepository.findById(idPedido)
        .orElseThrow(() -> new RuntimeException("hubo un error"));

        ClienteDTO cliente = clienteClientRest.obtenerDatosCliente(idCliente);

        pedido.setIdCliente(cliente.getIdCliente());
        
        return pedidoRepository.save(pedido);

    }

}
