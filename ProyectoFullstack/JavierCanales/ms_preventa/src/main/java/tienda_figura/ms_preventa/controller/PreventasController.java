package tienda_figura.ms_preventa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tienda_figura.ms_preventa.model.DetallePreventa;
import tienda_figura.ms_preventa.model.Preventa;
import tienda_figura.ms_preventa.service.DetallePreventaService;
import tienda_figura.ms_preventa.service.PreventaService;

@RestController
@RequestMapping("/api/v1/preventas")
public class PreventasController {

    @Autowired
    private PreventaService preventaService;

    @Autowired
    private DetallePreventaService detallePreventaService;

    // Preventa

    @GetMapping("/preventa")
    public ResponseEntity<List<Preventa>> preventaFindAll(){

        List<Preventa> listaPreventa = preventaService.preventaFindAll();

        if(listaPreventa.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaPreventa);
        }
    }

    @GetMapping("/preventa/{id}")
    public ResponseEntity<Preventa> preventaFindById(@PathVariable Long id){
        
        Preventa preventa = preventaService.preventaFindById(id);

        return ResponseEntity.ofNullable(preventa);
    }

    @PostMapping("/preventa")
    public ResponseEntity<Preventa> preventaSave(@RequestBody Preventa preventa){
        
        Preventa preventa2 = preventaService.preventaSave(preventa);

        return ResponseEntity.ofNullable(preventa2);
    }

    @DeleteMapping("/preventa/{id}")
    public ResponseEntity<Void> preventaDelete(@PathVariable Long id){
        
        Preventa preventa = preventaService.preventaFindById(id);

        if(preventa != null){
            preventaService.preventaDelete(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

        // metodos custom Preventa

    @PutMapping("/preventa/actualizar/{id}")
    public ResponseEntity<Preventa> preventaUpdate(@PathVariable Long id, @RequestBody Preventa preventaUpdate){

        Preventa preventa = preventaService.preventaFindById(id);

        if(preventa != null){
            preventaService.preventaUpdate(id, preventaUpdate);
            return ResponseEntity.ok(preventa);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("preventa/contar")
    public ResponseEntity<Long> contarPreventas(){
        return ResponseEntity.ok(preventaService.contarPreventas());
    }

    // DetallePreventa

    @GetMapping("/detallePreventa")
    public ResponseEntity<List<DetallePreventa>> detallePreventaFindAll(){
        
        List<DetallePreventa> listaDetallePreventa = detallePreventaService.detallePreventaFindAll();

        if(listaDetallePreventa.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaDetallePreventa);
        }
    }

    @GetMapping("/detallePreventa/{id}")
    public ResponseEntity<DetallePreventa> detallePreventaFindById(@PathVariable Long id){
        
        DetallePreventa detallePreventa = detallePreventaService.detallePreventaFindById(id);

        return ResponseEntity.ofNullable(detallePreventa);
    }

    @PostMapping("/detallePreventa")
    public ResponseEntity<DetallePreventa> detallePreventaSave(@RequestBody DetallePreventa detallePreventa){
        
        DetallePreventa detallePreventa2 = detallePreventaService.detallePreventaSave(detallePreventa);

        return ResponseEntity.ofNullable(detallePreventa2);
    }

    @DeleteMapping("/detallePreventa/{id}")
    public ResponseEntity<Void> detallePreventaDelete(@PathVariable Long id){
        
        DetallePreventa detallePreventa = detallePreventaService.detallePreventaFindById(id);

        if(detallePreventa != null){
            detallePreventaService.detallePreventaDelete(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

        // metodos custom DetallePreventa

    @GetMapping("/detallePreventa/buscarMes/{mes}")
    public ResponseEntity<List<DetallePreventa>> detallePreventaFindByMonth(@PathVariable int mes){
        
        List<DetallePreventa> listaDetallePreventa = detallePreventaService.detallePreventaFindByMonth(mes);

        if(listaDetallePreventa.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaDetallePreventa);
        }
    }
    
    @GetMapping("/detallePreventa/filtrarPrecio")
    public ResponseEntity<List<DetallePreventa>> detallePreventaFiltrarPrecio(@RequestParam int desde, @RequestParam int hasta){
        
        List<DetallePreventa> listaDetallePreventa = detallePreventaService.detallePreventaFiltrarPrecio(desde, hasta);

        if(listaDetallePreventa.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaDetallePreventa);
        }
    }

    @PutMapping("/detallePreventa/actualizar/{id}")
    public ResponseEntity<DetallePreventa> detallePreventaUpdate(@PathVariable Long id, @RequestBody DetallePreventa detallePreventaUpdate){

        DetallePreventa detallePreventa = detallePreventaService.detallePreventaFindById(id);
        
        if(detallePreventa != null){
            detallePreventaService.detallePreventaUpdate(id, detallePreventaUpdate);
            return ResponseEntity.ok(detallePreventa);
        }else{
            return ResponseEntity.notFound().build();
        }

    }
}
