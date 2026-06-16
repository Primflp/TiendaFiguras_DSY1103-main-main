package tienda_figura.ms_preventa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda_figura.ms_preventa.model.Preventa;
import tienda_figura.ms_preventa.repository.PreventaRepository;

@Service
public class PreventaService {
    
    @Autowired
    private PreventaRepository preventaRepository;

    public List<Preventa> preventaFindAll(){
        return preventaRepository.findAll();
    }

    public Preventa preventaFindById(Long id){
        return preventaRepository.findById(id).orElse(null);
    }

    public Preventa preventaSave(Preventa preventa){
        return preventaRepository.save(preventa);
    }

    public void preventaDelete(Long id){
        preventaRepository.deleteById(id);
    }
    
    // custom

    public Preventa preventaUpdate(Long id, Preventa preventaUpdate){

        if(preventaRepository.existsById(id)){

            Preventa preventa = preventaRepository.findById(id).orElse(null);

            preventa.setNombrePreventa(preventaUpdate.getNombrePreventa());
            
            return preventaRepository.save(preventa);
        }
        return null;
        
    }

    public long contarPreventas(){
        return preventaRepository.count();
    }
}
