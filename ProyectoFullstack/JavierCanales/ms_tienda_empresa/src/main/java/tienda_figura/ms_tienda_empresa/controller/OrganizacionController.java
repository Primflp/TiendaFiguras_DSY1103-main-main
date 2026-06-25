package tienda_figura.ms_tienda_empresa.controller;

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
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import tienda_figura.ms_tienda_empresa.clients.FiguraClientRest;
import tienda_figura.ms_tienda_empresa.clients.PreventaClientRest;
import tienda_figura.ms_tienda_empresa.dto.PreventaDTO;
import tienda_figura.ms_tienda_empresa.dto.ResponsePreventaPorTiendaDTO;
import tienda_figura.ms_tienda_empresa.dto.ResponseTiendaDTO;
import tienda_figura.ms_tienda_empresa.dtofigura.FiguraDTO;
import tienda_figura.ms_tienda_empresa.dtofigura.ResponseFiguraDTO;
import tienda_figura.ms_tienda_empresa.model.Empresa;
import tienda_figura.ms_tienda_empresa.model.Tienda;
import tienda_figura.ms_tienda_empresa.service.EmpresaService;
import tienda_figura.ms_tienda_empresa.service.TiendaService;

@RestController
@RequestMapping("/api/v1/organizacion")
@Tag(name = "Organización", description = "Acciones relacionadas con Empresas y Tiendas")
public class OrganizacionController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private TiendaService tiendaService;

    @Autowired
    private PreventaClientRest preventaClientRest;

    @Autowired 
    private FiguraClientRest figuraClientRest;

    // Empresa

    @GetMapping("/empresa")
    @Operation(summary = "Obtener una lista de todas las empresas", description = "Obtiene una lista de todas las empresas presentes en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se obtuvo el listado correctamente", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Empresa.class))),
            @ApiResponse(
                        responseCode = "204", description = "No existe ninguna empresa en el sistema")
    })
    public ResponseEntity<List<Empresa>> empresaFindAll(){
        
        List<Empresa> listaEmpresa = empresaService.empresaFindAll();

        if(listaEmpresa.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaEmpresa);
        }
    }

    @GetMapping("/empresa/{id}")
    @Operation(summary = "Buscar una empresa utilizando una id", description = "Obtiene una empresa en la cual su ID coincida con la ID de la búsqueda")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se obtuvo la empresa con la ID buscada", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Empresa.class))),
            @ApiResponse(
                        responseCode = "404", description = "No existe ninguna empresa que coincida con esa ID")
    })
    public ResponseEntity<Empresa> empresaFindById(@PathVariable Long id){
        
        Empresa empresa = empresaService.empresaFindById(id);

        return ResponseEntity.ofNullable(empresa);
    }

    @PostMapping("/empresa")
    @Operation(summary = "Guarda una empresa", description = "Guarda una nueva empresa en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se guardó correctamente la nueva empresa", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Empresa.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se pudo guardar la empresa")
    })
    public ResponseEntity<Empresa> empresaSave(@RequestBody Empresa empresa){
        
        Empresa empresa2 = empresaService.empresaSave(empresa);

        return ResponseEntity.ofNullable(empresa2);
    }

    @DeleteMapping("/empresa/{id}")
    @Operation(summary = "Elimina una empresa", description = "Elimina una empresa del sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se ha eliminado correctamente la empresa con la ID seleccionada"),
            @ApiResponse(
                        responseCode = "404", description = "La empresa que está intentando eliminar no existe")
    })
    public ResponseEntity<Void> empresaDelete(@PathVariable Long id){
        
        Empresa empresa = empresaService.empresaFindById(id);

        if(empresa != null){
            empresaService.empresaDelete(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // Métodos custom Empresa

    @GetMapping("/empresa/tributaria/{numTributario}")
    @Operation(summary = "Buscar una empresa por su número tributario", description = "Obtiene los detalles de una empresa utilizando su número tributario único")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se obtuvo la empresa correctamente", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Empresa.class))),
            @ApiResponse(
                        responseCode = "404", description = "No existe ninguna empresa con ese número tributario")
    })
    public ResponseEntity<Empresa> empresaFindByIdTributaria(@PathVariable int numTributario){
        
        Empresa empresa = empresaService.empresaFindByIdTributaria(numTributario);

        return ResponseEntity.ofNullable(empresa);
    }

    @PutMapping("/empresa/actualizar/{id}")
    @Operation(summary = "Actualizar una empresa", description = "Actualiza los datos de una empresa existente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se actualizó correctamente la empresa", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Empresa.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se pudo encontrar la empresa a actualizar")
    })
    public ResponseEntity<Empresa> empresaUpdate(@PathVariable Long id, @RequestBody Empresa empresaUpdate){

        Empresa empresa = empresaService.empresaFindById(id);

        if(empresa != null){
            empresaService.empresaUpdate(id, empresaUpdate);
            return ResponseEntity.ok(empresa);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // Tienda

    @GetMapping("/tienda")
    @Operation(summary = "Obtener una lista de todas las tiendas", description = "Obtiene una lista de todas las tiendas presentes en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se obtuvo el listado correctamente", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Tienda.class))),
            @ApiResponse(
                        responseCode = "204", description = "No existe ninguna tienda en el sistema")
    })
    public ResponseEntity<List<Tienda>> tiendaFindAll(){
        
        List<Tienda> listaTienda = tiendaService.tiendaFindAll();

        if(listaTienda.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaTienda);
        }
    }

    @GetMapping("/tienda/{id}")
    @Operation(summary = "Buscar una tienda utilizando una id", description = "Obtiene una tienda en la cual su ID coincida con la ID de la búsqueda")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se obtuvo la tienda con la ID buscada", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Tienda.class))),
            @ApiResponse(
                        responseCode = "404", description = "No existe ninguna tienda que coincida con esa ID")
    })
    public ResponseEntity<Tienda> tiendaFindById(@PathVariable Long id){
            
        Tienda tienda = tiendaService.tiendaFindById(id);

        return ResponseEntity.ofNullable(tienda);
    }

    @PostMapping("/tienda")
    @Operation(summary = "Guarda una tienda", description = "Guarda una nueva tienda en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se guardó correctamente la nueva tienda", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Tienda.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se pudo guardar la tienda")
    })
    public ResponseEntity<Tienda> tiendaSave(@RequestBody Tienda tienda){
        
        Tienda tienda2 = tiendaService.tiendaSave(tienda);

        return ResponseEntity.ofNullable(tienda2);
    }

    @DeleteMapping("/tienda/{id}")
    @Operation(summary = "Elimina una tienda", description = "Elimina una tienda del sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se ha eliminado correctamente la tienda con la ID seleccionada"),
            @ApiResponse(
                        responseCode = "404", description = "La tienda que está intentando eliminar no existe")
    })
    public ResponseEntity<Void> tiendaDelete(@PathVariable Long id){
        
        Tienda tienda = tiendaService.tiendaFindById(id);

        if(tienda != null){
            tiendaService.tiendaDelete(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // Métodos custom Tienda

    @GetMapping("/tienda/buscarPorDireccion/{direccion}")
    @Operation(summary = "Buscar tienda por dirección", description = "Busca una tienda cuya dirección coincida exactamente con el parámetro provisto")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se encontró la tienda correctamente", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Tienda.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se encontró ninguna tienda con esa dirección")
    })
    public ResponseEntity<Tienda> tiendaFindByDireccion(@PathVariable String direccion){
        
        Tienda tienda = tiendaService.tiendaFindByDireccion(direccion);

        return ResponseEntity.ofNullable(tienda);
    }

    @GetMapping("/tienda/buscarPorNombre/{nombre}")
    @Operation(summary = "Buscar tienda por nombre", description = "Busca una tienda cuyo nombre coincida con el parámetro provisto")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se encontró la tienda correctamente", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Tienda.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se encontró ninguna tienda con ese nombre")
    })
    public ResponseEntity<Tienda> tiendaFindByNombre(@PathVariable String nombre){
        
        Tienda tienda = tiendaService.tiendaFindByNombre(nombre);

        return ResponseEntity.ofNullable(tienda);
    }

    @PutMapping("/tienda/actualizar/{id}")
    @Operation(summary = "Actualizar una tienda", description = "Actualiza los datos de una tienda existente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se actualizó correctamente la tienda", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Tienda.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se pudo encontrar la tienda a actualizar")
    })
    public ResponseEntity<Tienda> tiendaUpdate(@PathVariable Long id, @RequestBody Tienda tiendaUpdate){

        Tienda tienda = tiendaService.tiendaFindById(id);

        if(tienda != null){
            tiendaService.tiendaUpdate(id, tiendaUpdate);
            return ResponseEntity.ok(tienda);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // Métodos OpenFeign Preventas

    @GetMapping("/tienda/buscarPreventaPorTienda/{idTienda}/{idPreventa}")
    @Operation(summary = "Busca una preventa asignada a una tienda", description = "Valida y obtiene los datos estructurados de una preventa vinculada a una tienda específica")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se encontró la relación correctamente", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ResponseTiendaDTO.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se encontró la tienda o la preventa especificada")
    })
    public ResponseEntity<ResponseTiendaDTO> buscarPreventaPorTienda(@PathVariable Long idTienda, @PathVariable Long idPreventa){

        return ResponseEntity.ok(tiendaService.buscarPreventaPorTienda(idTienda, idPreventa));
    }

    @GetMapping("/tienda/preventaPorTienda/{idTienda}")
    @Operation(summary = "Obtener todas las preventas asociadas a una tienda", description = "Obtiene un consolidado de todas las preventas registradas en la tienda indicada")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se obtuvo el listado de preventas con éxito", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ResponsePreventaPorTiendaDTO.class)))
    })
    public ResponseEntity<ResponsePreventaPorTiendaDTO> obtenerPreventasPorTienda(@PathVariable Long idTienda){
        return ResponseEntity.ok(tiendaService.obtenerPreventasPorTienda(idTienda));
    }

    @PutMapping("/tienda/vincular/tienda/{idTienda}/preventa/{idPreventa}")
    @Operation(summary = "Vincula una preventa externa a una tienda", description = "Utiliza OpenFeign para verificar la existencia de la preventa y asociarla a la tienda elegida")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se vinculó correctamente la preventa a la tienda", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Tienda.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se encontró la tienda o la preventa externa para realizar el vínculo")
    })
    public ResponseEntity<?> vincularPreventaATienda(@PathVariable Long idTienda, @PathVariable Long idPreventa){

        PreventaDTO preventa = preventaClientRest.obtenerDetallePreventa(idPreventa);

        if(preventa != null){
            Tienda tienda = tiendaService.vincularPreventaATienda(idTienda, idPreventa);
            return ResponseEntity.ok(tienda);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    // Métodos OpenFeign Figura

    @GetMapping("/tienda/buscarFiguraPorTienda/tienda/{idTienda}/figura/{idFigura}")
    @Operation(summary = "Busca una figura en una tienda", description = "Verifica la asignación de una figura en el inventario/catálogo de una tienda específica")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se localizó la figura en la tienda con éxito", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ResponseFiguraDTO.class))),
            @ApiResponse(
                        responseCode = "404", description = "La tienda o la figura no se encuentran registradas")
    })
    public ResponseEntity<ResponseFiguraDTO> buscarFiguraPorTienda(@PathVariable Long idTienda, @PathVariable Long idFigura){

        return ResponseEntity.ok(tiendaService.buscarFiguraPorTienda(idTienda, idFigura));
    }

    @PutMapping("/tienda/vincular/tienda/{idTienda}/figura/{idFigura}")
    @Operation(summary = "Vincula una figura a una tienda", description = "Verifica mediante OpenFeign los datos de la figura externa y la vincula al catálogo de la tienda")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se vinculó correctamente la figura a la tienda", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Tienda.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se localizó la tienda o la figura seleccionada")
    })
    public ResponseEntity<?> vincularFiguraATienda(@PathVariable Long idTienda, @PathVariable Long idFigura){

        FiguraDTO figura = figuraClientRest.obtenerDatosFigura(idFigura);

        if(figura != null && figura.getIdFigura() != null){
            Tienda tienda = tiendaService.vincularFiguraATienda(idTienda, idFigura);
            return ResponseEntity.ok(tienda);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}