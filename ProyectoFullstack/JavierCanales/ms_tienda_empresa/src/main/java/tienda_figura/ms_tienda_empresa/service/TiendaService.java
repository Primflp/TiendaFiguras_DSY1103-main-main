package tienda_figura.ms_tienda_empresa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda_figura.ms_tienda_empresa.clients.FiguraClientRest;
import tienda_figura.ms_tienda_empresa.clients.PreventaClientRest;
import tienda_figura.ms_tienda_empresa.dto.PreventaDTO;
import tienda_figura.ms_tienda_empresa.dto.ResponsePreventaPorTiendaDTO;
import tienda_figura.ms_tienda_empresa.dto.ResponseTiendaDTO;
import tienda_figura.ms_tienda_empresa.dtofigura.FiguraDTO;
import tienda_figura.ms_tienda_empresa.dtofigura.ResponseFiguraDTO;
import tienda_figura.ms_tienda_empresa.model.Tienda;
import tienda_figura.ms_tienda_empresa.repository.TiendaRepository;

@Service
public class TiendaService {

    @Autowired
    private TiendaRepository tiendaRepository;

    @Autowired
    private PreventaClientRest preventaClientRest;

    @Autowired
    private FiguraClientRest figuraClientRest;

    public List<Tienda> tiendaFindAll(){
        return tiendaRepository.findAll();
    }

    public Tienda tiendaFindById(Long id){
        return tiendaRepository.findById(id).orElse(null);
    }

    public Tienda tiendaSave(Tienda tienda){
        return tiendaRepository.save(tienda);
    }

    public void tiendaDelete(Long id){
        tiendaRepository.deleteById(id);
    }

    // custom

    public Tienda tiendaFindByDireccion(String direccion){
        return tiendaRepository.tiendaFindByDireccion(direccion);
    }

    public Tienda tiendaFindByNombre(String nombre){
        return tiendaRepository.tiendaFindByNombre(nombre);
    }

    public Tienda tiendaUpdate(Long id, Tienda tiendaUpdate){

        if(tiendaRepository.existsById(id)){

            Tienda tienda = tiendaRepository.findById(id).orElse(null);

                tienda.setNombreTienda(tiendaUpdate.getNombreTienda());
                tienda.setDireccionTienda(tiendaUpdate.getDireccionTienda());
                tienda.setTelefonoTienda(tiendaUpdate.getTelefonoTienda());

            return tiendaRepository.save(tienda);
        }
        return null;
        
    }

    // metodos OpenFeign Preventa

    public ResponseTiendaDTO buscarPreventaPorTienda(Long idTienda, Long idPreventa){

        Tienda tienda = tiendaRepository.findByIdTiendaAndIdPreventa(idTienda, idPreventa)
        .orElseThrow(() -> new RuntimeException("No existe una preventa para esta tienda"));

        PreventaDTO preventa = preventaClientRest.obtenerDetallePreventa(idPreventa);

        ResponseTiendaDTO response = new ResponseTiendaDTO();
            response.setNombrePreventa(preventa.getNombrePreventa());
            response.setNombreTienda(tienda.getNombreTienda());
            response.setDireccionTienda(tienda.getDireccionTienda());
            response.setTelefonoTienda(tienda.getTelefonoTienda());

        return response;

    }

    public ResponsePreventaPorTiendaDTO obtenerPreventasPorTienda(Long idTienda){
        Tienda tienda = tiendaRepository.findById(idTienda)
        .orElseThrow(() -> new RuntimeException("No existe una preventa para esta tienda"));

        List<PreventaDTO> listaPreventasAsociadas = new ArrayList<>();

        for(Long idCadaPreventa : tienda.getIdPreventa()){

            PreventaDTO preventa = preventaClientRest.obtenerDetallePreventa(idCadaPreventa);

            if(preventa != null){
                listaPreventasAsociadas.add(preventa);
            }

        }
        ResponsePreventaPorTiendaDTO response = new ResponsePreventaPorTiendaDTO();
            response.setNombreTienda(tienda.getNombreTienda());
            response.setDireccionTienda(tienda.getDireccionTienda());
            response.setTelefonoTienda(tienda.getTelefonoTienda());
            response.setPreventasAsociadas(listaPreventasAsociadas);

        return response;
    }

    // vincular Preventa a tienda para OpenFeign

    public Tienda vincularPreventaATienda(Long idTienda, Long idPreventa){

        Tienda tienda = tiendaRepository.findById(idTienda).orElse(null);

        tienda.getIdPreventa().add(idPreventa);

        return tiendaRepository.save(tienda);

    }

    // metodos OpenFeign Figura

    public ResponseFiguraDTO buscarFiguraPorTienda(Long idTienda, Long idFigura){

        Tienda tienda = tiendaRepository.findById(idTienda)
            .orElseThrow(() -> new RuntimeException("No existe una preventa para esta tienda"));

        FiguraDTO figura = figuraClientRest.obtenerDatosFigura(idFigura);

        ResponseFiguraDTO response = new ResponseFiguraDTO();
            response.setNombreFigura(figura.getNombreFigura());
            response.setTamanoFigura(figura.getTamanoFigura());
            response.setMarcaFigura(figura.getMarcaFigura());
            response.setTipoCajaFigura(figura.getTipoCajaFigura());
            response.setNombreTienda(tienda.getNombreTienda());
            response.setDireccionTienda(tienda.getDireccionTienda());
            response.setTelefonoTienda(tienda.getTelefonoTienda());

        return response;
    }

    public Tienda vincularFiguraATienda(Long idTienda, Long idFigura){

        Tienda tienda = tiendaRepository.findById(idTienda)
        .orElseThrow(() -> new RuntimeException("No existe una figura para esta tienda"));

        FiguraDTO figura = figuraClientRest.obtenerDatosFigura(idFigura);

        tienda.setIdFigura(figura.getIdFigura());

        return tiendaRepository.save(tienda);

    }
}

