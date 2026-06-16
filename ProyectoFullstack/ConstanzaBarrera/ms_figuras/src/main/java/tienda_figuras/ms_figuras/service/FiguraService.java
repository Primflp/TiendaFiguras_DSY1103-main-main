package tienda_figuras.ms_figuras.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda_figuras.ms_figuras.model.Figura;
import tienda_figuras.ms_figuras.model.StockReservadoDTO;
import tienda_figuras.ms_figuras.model.StockTotalDTO;
import tienda_figuras.ms_figuras.repository.FiguraRepository;

import java.util.List;

@Service

public class FiguraService {
    
    @Autowired
    private FiguraRepository figuraRepository;
    
    // Read All
   
    public List<Figura> figuraBuscarTodo (){
        return figuraRepository.findAll();
    }

    // 1. Create

    public Figura figuraGuardar (Figura figura){
        return figuraRepository.save(figura);
    }

    // 2. Read 

    public Figura figuraBuscarPorId (Long id){
        return figuraRepository.findById(id).orElse(null);
    }

    // 3. Update

    public Figura figuraActualizar(Long id, Figura actualizarFigura){
    
        if (figuraRepository.existsById(id)) {
            
            Figura figuraExistente = figuraRepository.findById(id).orElse(null);

                figuraExistente.setNombreFigura(actualizarFigura.getNombreFigura());
                figuraExistente.setTamanoFigura(actualizarFigura.getTamanoFigura());
                figuraExistente.setMarcaFigura(actualizarFigura.getMarcaFigura());
                figuraExistente.setTipoCajaFigura(actualizarFigura.getTipoCajaFigura());
                figuraExistente.setStockTotalFigura(actualizarFigura.getStockTotalFigura());
                figuraExistente.setStockReservadoFigura(actualizarFigura.getStockReservadoFigura());
                
            return figuraRepository.save(figuraExistente);
    
        }   
        return null;
    }

    // 4. Delete

    public void figuraEliminar (Long id){
        figuraRepository.deleteById(id);
    }

    // Metodo Custom 1

    public List<StockTotalDTO> figuraStockTotal (){
        return figuraRepository.verificarStockTotal();
    }

    // Metodo Custom 2

    public List<StockReservadoDTO> figuraStockReservado (){
        return figuraRepository.verificarStockReservado();
    }

    // Metodo Custom 3

    public List<Figura> figuraBuscarPorTamano (float tamano){
        return figuraRepository.buscarPorTamano(tamano);
    }

    // Metodo Custom 4

    public List<Figura> figuraBuscarPorMarca (String marca){
        return figuraRepository.buscarPorMarca(marca);
    }

    // Metodo Custom 5

    public List<Figura> figuraBuscarPorTipoCaja (String tipo){
        return figuraRepository.buscarPorTipoCaja(tipo);
    }

}   


