package tienda_figura.ms_preventa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda_figura.ms_preventa.model.DetallePreventa;
import tienda_figura.ms_preventa.repository.DetallePreventaRepository;

@Service
public class DetallePreventaService {

    @Autowired
    private DetallePreventaRepository detallePreventaRepository;

    public List<DetallePreventa> detallePreventaFindAll(){
        return detallePreventaRepository.findAll();
    }

    public DetallePreventa detallePreventaFindById(Long id){
        return detallePreventaRepository.findById(id).orElse(null);
    }

    public DetallePreventa detallePreventaSave(DetallePreventa detallePreventa){
        if(detallePreventa.getFechaInicioPreventa().compareTo(detallePreventa.getFechaTerminoPreventa()) > 0){
            return null;
        }else{
            return detallePreventaRepository.save(detallePreventa);
        }
        
    }

    public void detallePreventaDelete(Long id){
        detallePreventaRepository.deleteById(id);
    }

    // custom

    public List<DetallePreventa> detallePreventaFindByMonth(int mes){
        return detallePreventaRepository.detallePreventaFindByMonth(mes);
    }

    public List<DetallePreventa> detallePreventaFiltrarPrecio(int desde, int hasta){
        return detallePreventaRepository.detallePreventaFiltrarPrecio(desde, hasta);
    }

    public DetallePreventa detallePreventaUpdate(Long id, DetallePreventa detallePreventaUpdate){

        if(detallePreventaRepository.existsById(id)){

            DetallePreventa detallePreventa = detallePreventaRepository.findById(id).orElse(null);

            detallePreventa.setPrecioPreventa(detallePreventaUpdate.getPrecioPreventa());
            detallePreventa.setFechaInicioPreventa(detallePreventaUpdate.getFechaInicioPreventa());
            detallePreventa.setFechaTerminoPreventa(detallePreventaUpdate.getFechaTerminoPreventa());

            return detallePreventaRepository.save(detallePreventa);

        }
        return null;

    }
}
