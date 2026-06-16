package tienda_figuras.ms_figuras.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda_figuras.ms_figuras.model.CategoriaFigura;
import tienda_figuras.ms_figuras.model.Figura;
import tienda_figuras.ms_figuras.repository.CategoriaFiguraRepository;
import tienda_figuras.ms_figuras.repository.FiguraRepository;

import java.util.List;

@Service

public class CategoriaFiguraService {

    @Autowired
    private CategoriaFiguraRepository categoriaFiguraRepository;

    @Autowired
    private FiguraRepository figuraRepository;

    // Read All

    public List<CategoriaFigura> categoriaFiguraBuscarTodo (){
        return categoriaFiguraRepository.findAll();
    }

    // 1. Create

    public CategoriaFigura categoriaFiguraGuardar (CategoriaFigura categoria){
        return categoriaFiguraRepository.save(categoria);
    }

    // 2. Read

    public CategoriaFigura categoriaFiguraBuscarPorId (Long id){
        return categoriaFiguraRepository.findById(id).orElse(null);
    }

    // 3. Update

    public CategoriaFigura categoriaFiguraActualizar (Long id, CategoriaFigura actualizarCategoriaFigura){
    
        if (categoriaFiguraRepository.existsById(id)) {
            
            CategoriaFigura categoriaFiguraExistente = categoriaFiguraRepository.findById(id).orElse(null);

                categoriaFiguraExistente.setNombreCategoriaFigura(actualizarCategoriaFigura.getNombreCategoriaFigura());

            return categoriaFiguraRepository.save(categoriaFiguraExistente);
    
        }   
        return null;
    }

    // 4. delete

    public void categoriaFiguraEliminar (Long id){
        categoriaFiguraRepository.deleteById(id);
    }

    // Metodo Custom 1

    public void vincularCategoriaAFigura (Long idCategoriaFigura, Long idFigura) {
    
        CategoriaFigura categoriaFigura = categoriaFiguraRepository.findById(idCategoriaFigura)
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));


        Figura figura = figuraRepository.findById(idFigura)
        .orElseThrow(() -> new RuntimeException("Figura no encontrada"));
        
        categoriaFigura.setFigura(figura);
        categoriaFiguraRepository.save(categoriaFigura);
    }

    // Metodo Custom 2

    public void desvincularCategoriaDeFigura(Long idCategoriaFigura) {
        
        CategoriaFigura categoriaFigura = categoriaFiguraRepository.findById(idCategoriaFigura)
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        categoriaFigura.setFigura(null);
        categoriaFiguraRepository.save(categoriaFigura);
    }

}
