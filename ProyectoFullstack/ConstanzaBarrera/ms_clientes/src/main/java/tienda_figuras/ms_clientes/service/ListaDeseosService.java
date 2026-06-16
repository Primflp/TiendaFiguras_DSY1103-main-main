package tienda_figuras.ms_clientes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda_figuras.ms_clientes.model.ListaDeseos;
import tienda_figuras.ms_clientes.model.Cliente;
import tienda_figuras.ms_clientes.repository.ListaDeseosRepository;
import tienda_figuras.ms_clientes.repository.ClienteRepository;

import tienda_figuras.ms_clientes.dto.FiguraDTO;
import tienda_figuras.ms_clientes.dto.ResponseListaDeseosDTO;

import tienda_figuras.ms_clientes.clients.FiguraClientRest;

import java.util.List;

@Service

public class ListaDeseosService {

    @Autowired
    private ListaDeseosRepository listaDeseosRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FiguraClientRest figuraClientRest;

    // Read All

    public List<ListaDeseos> listaDeseosBuscarTodo (){
        return listaDeseosRepository.findAll();
    }

    // 1. Create

    public ListaDeseos listaDeseosGuardar (ListaDeseos listaDeseos){
        return listaDeseosRepository.save(listaDeseos);
    }

    // 2. Read

    public ListaDeseos listaDeseosBuscarPorId (Long id){
        return listaDeseosRepository.findById(id).orElse(null);
    }

    // 3. Update

    public ListaDeseos listaDeseosActualizar (Long id, ListaDeseos actualizarListaDeseos){
    
        if (listaDeseosRepository.existsById(id)) {
            
            ListaDeseos listaDeseosNew = listaDeseosRepository.findById(id).orElse(null);

                listaDeseosNew.setNombreListaDeseos(actualizarListaDeseos.getNombreListaDeseos());
    
            return listaDeseosRepository.save(listaDeseosNew);
    
        }   
        return null;
    }

    // 4. Delete

    public void listaDeseosEliminar (Long id){
        listaDeseosRepository.deleteById(id);
    }

    // Metodo Custom 1

    public void vincularListaDeseosACliente (Long idListaDeseos, Long idCliente) {
    
        ListaDeseos listaDeseosNew = listaDeseosRepository.findById(idListaDeseos)
        .orElseThrow(() -> new RuntimeException("Lista de deseos no encontrada"));


        Cliente cliente = clienteRepository.findById(idCliente)
        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
        listaDeseosNew.setCliente(cliente);
        listaDeseosRepository.save(listaDeseosNew);
    }

    //Metodo Custom 2

    public void desvincularListaDeseosDeCliente (Long idListaDeseos) {
        
        ListaDeseos listaDeseosNew = listaDeseosRepository.findById(idListaDeseos)
        .orElseThrow(() -> new RuntimeException("Lista de deseos no encontrada"));

        listaDeseosNew.setCliente(null);
        listaDeseosRepository.save(listaDeseosNew);
    }
    // Conexion

    public ResponseListaDeseosDTO obtenerResponse (Long idListaDeseos, Long idFigura){

        ListaDeseos listaDeseosNew = listaDeseosRepository.findByIdListaDeseosAndIdFigura(idListaDeseos, idFigura)
            .orElseThrow(() -> new RuntimeException("Hubo un error."));

        FiguraDTO figura = figuraClientRest.obtenerDetalleFigura(idFigura);

        ResponseListaDeseosDTO responseLD = new ResponseListaDeseosDTO();
            
        responseLD.setNombreFigura(figura.getNombreFigura());
        responseLD.setNombreListaDeseos(listaDeseosNew.getNombreListaDeseos());
    
        return responseLD;
    }

    public ListaDeseos vincularFiguraAListaDeseos(Long idListaDeseos, Long idFigura){

        ListaDeseos listaDeseos = listaDeseosRepository.findById(idListaDeseos)
        .orElseThrow(() -> new RuntimeException("No existe una figura para esta tienda"));

        FiguraDTO figura = figuraClientRest.obtenerDetalleFigura(idFigura);

        listaDeseos.setIdFigura(figura.getIdFigura());

        return listaDeseosRepository.save(listaDeseos);

    }
}

