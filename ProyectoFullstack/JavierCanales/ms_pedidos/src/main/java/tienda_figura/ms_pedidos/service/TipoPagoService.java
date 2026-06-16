package tienda_figura.ms_pedidos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda_figura.ms_pedidos.model.Pedido;
import tienda_figura.ms_pedidos.model.TipoPago;
import tienda_figura.ms_pedidos.repository.PedidoRepository;
import tienda_figura.ms_pedidos.repository.TipoPagoRepository;

@Service
public class TipoPagoService {
    
    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<TipoPago> tipoPagoFindAll(){
        return tipoPagoRepository.findAll();
    }

    public TipoPago tipoPagoFindById(Long id){
        return tipoPagoRepository.findById(id).orElse(null);
    }

    public TipoPago tipoPagoSave(TipoPago tipoPago){
        return tipoPagoRepository.save(tipoPago);
    }

    public void tipoPagoDelete(Long id){
        tipoPagoRepository.deleteById(id);
    }

    // custom

    public void vincularPedido(Long idTipoPago, Long idPedido){

        TipoPago tipoPago = tipoPagoRepository.findById(idTipoPago).orElse(null);

        Pedido pedido = pedidoRepository.findById(idPedido).orElse(null);

        pedido.setTipoPago(tipoPago);

        pedidoRepository.save(pedido);

    }

}
