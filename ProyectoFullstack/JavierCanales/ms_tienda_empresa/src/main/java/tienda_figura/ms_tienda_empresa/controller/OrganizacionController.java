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
public class OrganizacionController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private TiendaService tiendaService;

    @Autowired
    private PreventaClientRest preventaClientRest;

    @Autowired FiguraClientRest figuraClientRest;

    //empresa

    @GetMapping("/empresa")
    public ResponseEntity<List<Empresa>> empresaFindAll(){
        
        List<Empresa> listaEmpresa = empresaService.empresaFindAll();

        if(listaEmpresa.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaEmpresa);
        }
    }

    @GetMapping("/empresa/{id}")
    public ResponseEntity<Empresa> empresaFindById(@PathVariable Long id){
        
        Empresa empresa = empresaService.empresaFindById(id);

        return ResponseEntity.ofNullable(empresa);
    }

    @PostMapping("/empresa")
    public ResponseEntity<Empresa> empresaSave(@RequestBody Empresa empresa){
        
        Empresa empresa2 = empresaService.empresaSave(empresa);

        return ResponseEntity.ofNullable(empresa2);
    }

    @DeleteMapping("/empresa/{id}")
    public ResponseEntity<Void> empresaDelete(@PathVariable Long id){
        
        Empresa empresa = empresaService.empresaFindById(id);

        if(empresa != null){
            empresaService.empresaDelete(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

        // metodos custom Empresa

    @GetMapping("/empresa/{numTributario}")
    public ResponseEntity<Empresa> empresaFindByIdTributaria(@PathVariable int numTributario){
        
        Empresa empresa = empresaService.empresaFindByIdTributaria(numTributario);

        return ResponseEntity.ofNullable(empresa);
    }

    @PutMapping("/empresa/actualizar/{id}")
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
    public ResponseEntity<List<Tienda>> tiendaFindAll(){
        
        List<Tienda> listaTienda = tiendaService.tiendaFindAll();

        if(listaTienda.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaTienda);
        }
    }

    @GetMapping("/tienda/{id}")
    public ResponseEntity<Tienda> tiendaFindById(@PathVariable Long id){
            
        Tienda tienda = tiendaService.tiendaFindById(id);

        return ResponseEntity.ofNullable(tienda);
    }

    @PostMapping("/tienda")
    public ResponseEntity<Tienda> tiendaSave(@RequestBody Tienda tienda){
        
        Tienda tienda2 = tiendaService.tiendaSave(tienda);

        return ResponseEntity.ofNullable(tienda2);
    }

    @DeleteMapping("/tienda/{id}")
    public ResponseEntity<Void> tiendaDelete(@PathVariable Long id){
        
        Tienda tienda = tiendaService.tiendaFindById(id);

        if(tienda != null){
            tiendaService.tiendaDelete(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

        // metodos custom Tienda

    @GetMapping("/tienda/buscarPorDireccion/{direccion}")
    public ResponseEntity<Tienda> tiendaFindByDireccion(@PathVariable String direccion){
        
        Tienda tienda = tiendaService.tiendaFindByDireccion(direccion);

        return ResponseEntity.ofNullable(tienda);
    }

    @GetMapping("/tienda/buscarPorNombre/{nombre}")
    public ResponseEntity<Tienda> tiendaFindByNombre(@PathVariable String nombre){
        
        Tienda tienda = tiendaService.tiendaFindByNombre(nombre);

        return ResponseEntity.ofNullable(tienda);
    }

    @PutMapping("/tienda/actualizar/{id}")
    public ResponseEntity<Tienda> tiendaUpdate(@PathVariable Long id, @RequestBody Tienda tiendaUpdate){

        Tienda tienda = tiendaService.tiendaFindById(id);

        if(tienda != null){
            tiendaService.tiendaUpdate(id, tiendaUpdate);
            return ResponseEntity.ok(tienda);
        }else{
            return ResponseEntity.notFound().build();
        }
        
    }

    // metodos OpenFeign Preventas

    @GetMapping("/tienda/buscarPreventaPorTienda/{idTienda}/{idPreventa}")
    public ResponseEntity<ResponseTiendaDTO> buscarPreventaPorTienda(@PathVariable Long idTienda, @PathVariable Long idPreventa){

        return ResponseEntity.ok(tiendaService.buscarPreventaPorTienda(idTienda, idPreventa));
    }

    @GetMapping("/tienda/preventaPorTienda/{idTienda}")
    public ResponseEntity<ResponsePreventaPorTiendaDTO> obtenerPreventasPorTienda(@PathVariable Long idTienda){
        return ResponseEntity.ok(tiendaService.obtenerPreventasPorTienda(idTienda));
    }

    @PutMapping("/tienda/vincular/tienda/{idTienda}/preventa/{idPreventa}")
    public ResponseEntity<?> vincularPreventaATienda(@PathVariable Long idTienda, @PathVariable Long idPreventa){

        PreventaDTO preventa = preventaClientRest.obtenerDetallePreventa(idPreventa);

        if(preventa != null){
            Tienda tienda = tiendaService.vincularPreventaATienda(idTienda, idPreventa);
            return ResponseEntity.ok(tienda);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    // metodos OpenFeign Figura

    @GetMapping("/tienda/buscarFiguraPorTienda/tienda/{idTienda}/figura/{idFigura}")
    public ResponseEntity<ResponseFiguraDTO> buscarFiguraPorTienda(@PathVariable Long idTienda, @PathVariable Long idFigura){

        return ResponseEntity.ok(tiendaService.buscarFiguraPorTienda(idTienda, idFigura));

    }

    @PutMapping("/tienda/vincular/tienda/{idTienda}/figura/{idFigura}")
    public ResponseEntity<?> vincularFiguraATienda(@PathVariable Long idTienda, @PathVariable Long idFigura){

        FiguraDTO figura = figuraClientRest.obtenerDatosFigura(idFigura);

        if(figura.getIdFigura() != null){
            Tienda tienda = tiendaService.vincularFiguraATienda(idTienda, idFigura);
            return ResponseEntity.ok(tienda);
        }else{
            return ResponseEntity.notFound().build();
        }

    }

}
