package tienda_figuras.ms_clientes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tienda_figuras.ms_clientes.model.Cliente;
import tienda_figuras.ms_clientes.model.ListaDeseos;

import tienda_figuras.ms_clientes.service.ClienteService;
import tienda_figuras.ms_clientes.service.ListaDeseosService;
import tienda_figuras.ms_clientes.clients.FiguraClientRest;
import tienda_figuras.ms_clientes.dto.FiguraDTO;
import tienda_figuras.ms_clientes.dto.ResponseListaDeseosDTO;

@RestController
@RequestMapping("/api/v1/msclientes")

public class MsClientesController {
    
    @Autowired 
    private ClienteService clienteService;

    @Autowired 
    private ListaDeseosService listaDeseosService;

    @Autowired
    private FiguraClientRest figuraClientRest;

    // Clientes ---

    @GetMapping("/clientes/buscartodos")
    public ResponseEntity<List<Cliente>> clienteFindALL (){

        List<Cliente> listaClientes = clienteService.clienteBuscarTodo();

        if(listaClientes.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaClientes);
        }

    }// FIND ALL

    @PostMapping("/clientes/guardar")
    public ResponseEntity<Cliente> clienteSave (@RequestBody Cliente cliente){

        Cliente clienteNew = clienteService.clienteGuardar(cliente);
        return ResponseEntity.ofNullable(clienteNew);

    }// CREATE

    @GetMapping("/clientes/buscarporid/{id}")
    public ResponseEntity<Cliente> clienteFindById (@PathVariable Long id){

        Cliente clienteNew = clienteService.clienteBuscarPorId(id);
        return ResponseEntity.ofNullable(clienteNew);
        
    }// READ

    @PutMapping("/clientes/actualizar/{id}")
    public ResponseEntity<Cliente> clienteUpdate(@PathVariable Long id, @RequestBody Cliente cliente){

        Cliente clienteNew = clienteService.clienteBuscarPorId(id);
        
        if(cliente != null){
            clienteService.clienteActualizar(id, cliente);
            return ResponseEntity.ok(clienteNew);
        }else{
             return ResponseEntity.notFound().build();
        }

    }// UPDATE

    @DeleteMapping("/clientes/eliminarporid/{id}")
    public ResponseEntity<Void> clienteDelete (@PathVariable Long id){

        Cliente clienteNew = clienteService.clienteBuscarPorId(id);

        if(clienteNew != null){
            clienteService.clienteEliminar(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }// DELETE

    //-----------------------------------------------------------------------

    @GetMapping("/clientes/autenticacion")
    public ResponseEntity<List<Cliente>> clienteAccAuth (@RequestParam String correo, @RequestParam String contrasena){

        List<Cliente> listaClientes = clienteService.clienteAutenticarCuenta(correo, contrasena);

        if(listaClientes.isEmpty()){
            return ResponseEntity.noContent().build(); 
        }else{
            return ResponseEntity.ok(listaClientes);
        }

    }// 1

    @GetMapping("/clientes/buscarporrun")
    public ResponseEntity<List<Cliente>> clienteFindByRun (@RequestParam String run){

        List<Cliente> listaClientes = clienteService.clienteBuscarPorRun(run);

        if(listaClientes.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaClientes);
        }

    }// 2

    @GetMapping("/clientes/buscarpordireccion")
    public ResponseEntity<List<Cliente>> clienteFindByAddres (@RequestParam String direccion){

        List<Cliente> listaClientes = clienteService.clienteBuscarPorDireccion(direccion);

        if(listaClientes.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaClientes);
        }

    }// 3

    // Lista Deseos ---

    @GetMapping("/listadeseos/buscartodas")
    public ResponseEntity<List<ListaDeseos>> listaDeseosFindAll (){

        List<ListaDeseos> listaDeseos = listaDeseosService.listaDeseosBuscarTodo();

        if(listaDeseos.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaDeseos);
        }

    }// FIND ALL

    @PostMapping("/listadeseos/guardar")
    public ResponseEntity<ListaDeseos> listaDeseosSave (@RequestBody ListaDeseos listaDeseos){

        ListaDeseos listaNew = listaDeseosService.listaDeseosGuardar(listaDeseos);
        return ResponseEntity.ofNullable(listaNew);

    }// CREATE

    @GetMapping("/listadeseos/buscarporid/{id}")
    public ResponseEntity<ListaDeseos> listaDeseosFindById (@PathVariable Long id){

        ListaDeseos listaNew = listaDeseosService.listaDeseosBuscarPorId(id);
        return ResponseEntity.ofNullable(listaNew);

    }// READ

    @PutMapping("/listadeseos/actualizar/{id}")
    public ResponseEntity<ListaDeseos> listaDeseosUpdate (@PathVariable Long id, @RequestBody ListaDeseos listaDeseos){

        ListaDeseos listaNew = listaDeseosService.listaDeseosBuscarPorId(id);

        if(listaNew != null){
            listaDeseosService.listaDeseosActualizar(id, listaDeseos);
            return ResponseEntity.ok(listaNew);
        }else{
            return ResponseEntity.notFound().build();
        }

    }// UPDATE

    @DeleteMapping("/listadeseos/eliminarporid/{id}")
    public ResponseEntity<Void> listaDeseosDelete (@PathVariable Long id){

        ListaDeseos listaNew = listaDeseosService.listaDeseosBuscarPorId(id);

        if(listaNew != null){
            listaDeseosService.listaDeseosEliminar(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }// DELETE    

    //-----------------------------------------------------------------------

    @PutMapping("/listadeseos/{idListaDeseos}/vincular/{idCliente}")
    public ResponseEntity<?> linkListaDeseostoCliente (@PathVariable Long idListaDeseos, @PathVariable Long idCliente) {
        
        ListaDeseos listaDeseos = listaDeseosService.listaDeseosBuscarPorId(idListaDeseos);

        Cliente cliente = clienteService.clienteBuscarPorId(idCliente);
        
        if (listaDeseos != null) {
            if (cliente != null) {
                listaDeseosService.vincularListaDeseosACliente(idListaDeseos, idCliente);
                return ResponseEntity.ok(cliente);
            }else{
                return ResponseEntity.notFound().build();
            }
        }else{
            return ResponseEntity.notFound().build();
        }

    }// 1

    @PutMapping("/listadeseos/{idListaDeseos}/desvincular")
    public ResponseEntity<?> unlinkListaDeseosFromCliente (@PathVariable Long idListaDeseos){

        ListaDeseos listaDeseos = listaDeseosService.listaDeseosBuscarPorId(idListaDeseos);

        if(listaDeseos != null) {
            listaDeseosService.desvincularListaDeseosDeCliente(idListaDeseos);
            return ResponseEntity.ok(listaDeseos);
        }else{
            return ResponseEntity.notFound().build();
        }
        
    }//2

    // Conexion

    @GetMapping("/listadeseos/verfigurasenlistadedeseos/listadeseos/{idListaDeseos}/figura/{idFigura}")
    public ResponseEntity<ResponseListaDeseosDTO> obtenerResponse(@PathVariable Long idListaDeseos, @PathVariable Long idFigura){

        return ResponseEntity.ok(listaDeseosService.obtenerResponse(idListaDeseos, idFigura));

    }

    @PutMapping("/listadeseos/vincular/listadeseos/{idListaDeseos}/figura/{idFigura}")
    public ResponseEntity<?> vincularFiguraAListaDeseos(@PathVariable Long idListaDeseos, @PathVariable Long idFigura){

        FiguraDTO figura = figuraClientRest.obtenerDetalleFigura(idFigura);

        if(figura.getIdFigura() != null){
            ListaDeseos listaDeseos = listaDeseosService.vincularFiguraAListaDeseos(idListaDeseos, idFigura);
            return ResponseEntity.ok(listaDeseos);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
}