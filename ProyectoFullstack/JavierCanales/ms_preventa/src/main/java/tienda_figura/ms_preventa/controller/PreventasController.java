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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import tienda_figura.ms_preventa.model.DetallePreventa;
import tienda_figura.ms_preventa.model.Preventa;
import tienda_figura.ms_preventa.service.DetallePreventaService;
import tienda_figura.ms_preventa.service.PreventaService;

@RestController
@RequestMapping("/api/v1/preventas")
@Tag(name = "Preventas", description = "Acciones relacionadas con preventas")
public class PreventasController {

    @Autowired
    private PreventaService preventaService;

    @Autowired
    private DetallePreventaService detallePreventaService;

    // Preventa

    @GetMapping("/preventa")
    @Operation(summary = "Obtener una lista de todas las preventas", description = "Obtiene una lista de todas las preventas presentes en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se obtuvo el listado correctamente", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Preventa.class))),
            @ApiResponse(
                        responseCode = "204", description = "No existe ninguna preventa en el sistema")
    })
    public ResponseEntity<List<Preventa>> preventaFindAll(){

        List<Preventa> listaPreventa = preventaService.preventaFindAll();

        if(listaPreventa.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaPreventa);
        }
    }

    @GetMapping("/preventa/{id}")
    @Operation(summary = "Buscar una preventa utilizando una id", description = "Obtiene una preventa en la cual su ID coincida con la ID de la búsqueda")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se obtuvo la preventa con la ID buscada", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Preventa.class))),
            @ApiResponse(
                        responseCode = "404", description = "No existe ninguna preventa que coincida con esa ID")
    })
    public ResponseEntity<Preventa> preventaFindById(@PathVariable Long id){
        
        Preventa preventa = preventaService.preventaFindById(id);

        return ResponseEntity.ofNullable(preventa);
    }

    @PostMapping("/preventa")
    @Operation(summary = "Guarda una preventa", description = "Guarda una nueva preventa en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se guardó correctamente la nueva preventa", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Preventa.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se pudo guardar la preventa")
    })
    public ResponseEntity<Preventa> preventaSave(@RequestBody Preventa preventa){
        
        Preventa preventa2 = preventaService.preventaSave(preventa);

        return ResponseEntity.ofNullable(preventa2);
    }

    @DeleteMapping("/preventa/{id}")
    @Operation(summary = "Elimina una preventa", description = "Elimina una preventa del sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se ha eliminado correctamente la preventa con la ID seleccionada"),
            @ApiResponse(
                        responseCode = "404", description = "La preventa que está intentando eliminar no existe")
    })
    public ResponseEntity<Void> preventaDelete(@PathVariable Long id){
        
        Preventa preventa = preventaService.preventaFindById(id);

        if(preventa != null){
            preventaService.preventaDelete(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // métodos custom Preventa

    @PutMapping("/preventa/actualizar/{id}")
    @Operation(summary = "Actualizar una preventa", description = "Actualiza los datos de una preventa existente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se actualizó correctamente la preventa", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Preventa.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se pudo encontrar la preventa a actualizar")
    })
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
    @Operation(summary = "Contar preventas", description = "Obtiene la cantidad total de preventas registradas en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se obtuvo el conteo correctamente",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Long.class))),
            @ApiResponse(
                        responseCode = "204", description = "No existe ninguna preventa en el sistema")
                        
    })
    public ResponseEntity<Long> contarPreventas(){
        return ResponseEntity.ok(preventaService.contarPreventas());
    }

    // DetallePreventa

    @GetMapping("/detallePreventa")
    @Operation(summary = "Obtener una lista de todos los detalles de preventas", description = "Obtiene una lista de todos los detalles de cada preventa presente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se obtuvo el listado correctamente", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = DetallePreventa.class))),
            @ApiResponse(
                        responseCode = "204", description = "No existe ningún detalle de preventa en el sistema")
    })
    public ResponseEntity<List<DetallePreventa>> detallePreventaFindAll(){
        
        List<DetallePreventa> listaDetallePreventa = detallePreventaService.detallePreventaFindAll();

        if(listaDetallePreventa.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaDetallePreventa);
        }
    }

    @GetMapping("/detallePreventa/{id}")
    @Operation(summary = "Buscar un detalle de una preventa utilizando una id", description = "Obtiene un detalle de preventa en la cual su ID coincida con la ID de la búsqueda")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se obtuvo el detalle de preventa con la ID buscada", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = DetallePreventa.class))),
            @ApiResponse(
                        responseCode = "404", description = "No existe ningún detalle de preventa que coincida con esa ID")
    })
    public ResponseEntity<DetallePreventa> detallePreventaFindById(@PathVariable Long id){
        
        DetallePreventa detallePreventa = detallePreventaService.detallePreventaFindById(id);

        return ResponseEntity.ofNullable(detallePreventa);
    }

    @PostMapping("/detallePreventa")
    @Operation(summary = "Guardar un detalle preventa", description = "Guarda un nuevo detalle preventa en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se guardó correctamente el nuevo detalle preventa", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = DetallePreventa.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se pudo guardar el detalle preventa")
    })
    public ResponseEntity<DetallePreventa> detallePreventaSave(@RequestBody DetallePreventa detallePreventa){
        
        DetallePreventa detallePreventa2 = detallePreventaService.detallePreventaSave(detallePreventa);

        return ResponseEntity.ofNullable(detallePreventa2);
    }

    @DeleteMapping("/detallePreventa/{id}")
    @Operation(summary = "Elimina un detalle preventa", description = "Elimina un detalle preventa del sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se ha eliminado correctamente el detalle preventa con la ID seleccionada"),
            @ApiResponse(
                        responseCode = "404", description = "El detalle preventa que está intentando eliminar no existe")
    })
    public ResponseEntity<Void> detallePreventaDelete(@PathVariable Long id){
        
        DetallePreventa detallePreventa = detallePreventaService.detallePreventaFindById(id);

        if(detallePreventa != null){
            detallePreventaService.detallePreventaDelete(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // métodos custom DetallePreventa

    @GetMapping("/detallePreventa/buscarMes/{mes}")
    @Operation(summary = "Filtra los detalles de preventa por mes", description = "Obtiene los detalles de preventa asociados a un número de mes específico")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se filtró correctamente los detalles de preventa para el mes indicado", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = DetallePreventa.class))),
            @ApiResponse(
                        responseCode = "204", description = "No se logró encontrar un detalle de preventa para el mes buscado")
    })
    public ResponseEntity<List<DetallePreventa>> detallePreventaFindByMonth(@PathVariable int mes){
        
        List<DetallePreventa> listaDetallePreventa = detallePreventaService.detallePreventaFindByMonth(mes);

        if(listaDetallePreventa.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaDetallePreventa);
        }
    }
    
    @GetMapping("/detallePreventa/filtrarPrecio")
    @Operation(summary = "Filtra los detalles de preventa por precio", description = "Se filtran los detalles de preventa que están dentro de un rango de precio correspondiente")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se filtró correctamente los detalles de preventa dentro del rango de precio buscado", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = DetallePreventa.class))),
            @ApiResponse(
                        responseCode = "204", description = "No se logró encontrar un detalle de preventa dentro del rango de precio buscado")
    })
    public ResponseEntity<List<DetallePreventa>> detallePreventaFiltrarPrecio(@RequestParam int desde, @RequestParam int hasta){
        
        List<DetallePreventa> listaDetallePreventa = detallePreventaService.detallePreventaFiltrarPrecio(desde, hasta);

        if(listaDetallePreventa.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaDetallePreventa);
        }
    }

    @PutMapping("/detallePreventa/actualizar/{id}")
    @Operation(summary = "Actualizar un detalle preventa", description = "Actualiza los datos de un detalle preventa existente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se actualizó correctamente el detalle preventa", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = DetallePreventa.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se pudo encontrar el detalle preventa a actualizar")
    })
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