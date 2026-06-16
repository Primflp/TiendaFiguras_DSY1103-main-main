package tienda_figura.ms_pedidos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda_figura.ms_pedidos.clients.FiguraClientRest;
import tienda_figura.ms_pedidos.dtoFigura.FiguraDTO;
import tienda_figura.ms_pedidos.dtoFigura.ResponseFiguraDTO;
import tienda_figura.ms_pedidos.model.DetallePedido;
import tienda_figura.ms_pedidos.repository.DetallePedidoRepository;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private FiguraClientRest figuraClientRest;

    public List<DetallePedido> detallePedidoFindAll(){
        return detallePedidoRepository.findAll();
    }

    public DetallePedido detallePedidoFindById(Long id){
        return detallePedidoRepository.findById(id).orElse(null);
    }

    public DetallePedido detallePedidoSave(DetallePedido detallePedido){
        return detallePedidoRepository.save(detallePedido);
    }

    public void detallePedidoDelete(Long id){
        detallePedidoRepository.deleteById(id);
    }

    // custom

    public List<DetallePedido> detallePedidoFiltrarPrecio(int desde, int hasta){
        return detallePedidoRepository.detallePedidoFiltrarPrecio(desde, hasta);
    }

    public DetallePedido detallePedidoUpdate(Long id, DetallePedido detallePedidoUpdate){
    
        if (detallePedidoRepository.existsById(id)) {
            
            DetallePedido detallePedido = detallePedidoRepository.findById(id).orElse(null);

                detallePedido.setCantidad(detallePedidoUpdate.getCantidad());
                detallePedido.setPrecioUnitario(detallePedidoUpdate.getPrecioUnitario());
                detallePedido.calcularMontoMasIva();
                
            return detallePedidoRepository.save(detallePedido);
    
        }   
        return null;
    }

    // metodos custom para openfeign

    public ResponseFiguraDTO buscarFiguraPorDetallePedido(Long idDetallePedido, Long idFigura){

        DetallePedido detallePedido = detallePedidoRepository.findById(idDetallePedido)
        .orElseThrow(() -> new RuntimeException("hubo un error"));

        FiguraDTO figura = figuraClientRest.obtenerDatosFiguras(idFigura);

        ResponseFiguraDTO response = new ResponseFiguraDTO();
            response.setNombreFigura(figura.getNombreFigura());
            response.setTamanoFigura(figura.getTamanoFigura());
            response.setMarcaFigura(figura.getMarcaFigura());
            response.setTipoCajaFigura(figura.getTipoCajaFigura());
            response.setCantidad(detallePedido.getCantidad());
            response.setPrecioUnitario(detallePedido.getPrecioUnitario());
            response.setMontoTotal(detallePedido.getMontoTotal());
            response.setEstadoPedido(detallePedido.getPedido().getEstadoPedido());
            response.setDireccionEnvio(detallePedido.getPedido().getDireccionEnvio());
            response.setFechaEntrega(detallePedido.getPedido().getFechaEntrega());
            response.setFechaEmision(detallePedido.getPedido().getFechaEmision());

        return response;
            
    }

    public DetallePedido vincularFiguraADetallePedido(Long idDetallePedido, Long idFigura){

        DetallePedido detallePedido = detallePedidoRepository.findById(idDetallePedido)
        .orElseThrow(() -> new RuntimeException("ha ocurrido un error"));

        FiguraDTO figura = figuraClientRest.obtenerDatosFiguras(idFigura);

        detallePedido.setIdFigura(figura.getIdFigura());
        
        return detallePedidoRepository.save(detallePedido);

    }
}