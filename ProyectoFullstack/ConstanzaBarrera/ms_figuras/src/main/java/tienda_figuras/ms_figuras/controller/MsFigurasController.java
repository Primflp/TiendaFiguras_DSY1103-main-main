package tienda_figuras.ms_figuras.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tienda_figuras.ms_figuras.model.Figura;
import tienda_figuras.ms_figuras.model.CategoriaFigura;

import tienda_figuras.ms_figuras.service.FiguraService;
import tienda_figuras.ms_figuras.service.CategoriaFiguraService;

import tienda_figuras.ms_figuras.model.StockReservadoDTO;
import tienda_figuras.ms_figuras.model.StockTotalDTO;


@RestController
@RequestMapping("/api/v1/msfiguras")

public class MsFigurasController {
    
    @Autowired
    private FiguraService figuraService;

    @Autowired
    private CategoriaFiguraService categoriaFiguraService;

    // Figuras ---

    @GetMapping("/figuras/buscartodas")
    public ResponseEntity<List<Figura>> figuraFindALL (){

        List<Figura> listaFiguras = figuraService.figuraBuscarTodo();

        if(listaFiguras.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaFiguras);
        }

    }// FIND ALL

    @PostMapping("/figuras/guardar")
    public ResponseEntity<Figura> figuraSave (@RequestBody Figura figura){

        Figura figuraNew = figuraService.figuraGuardar(figura);
        return ResponseEntity.ofNullable(figuraNew);

    }// CREATE

    @GetMapping("/figuras/buscarporid/{id}")
    public ResponseEntity<Figura> figuraFindById (@PathVariable Long id){

        Figura figuraNew = figuraService.figuraBuscarPorId(id);
        return ResponseEntity.ofNullable(figuraNew);

    }// READ

    @PutMapping("/figuras/actualizar/{id}")
    public ResponseEntity<Figura> figuraUpdate (@PathVariable Long id, @RequestBody Figura figura){

        Figura figuraNew = figuraService.figuraBuscarPorId(id);

        if(figuraNew != null){
            figuraService.figuraActualizar(id, figura);
            return ResponseEntity.ok(figuraNew);
        }else{
            return ResponseEntity.notFound().build();
        }

    }// UPDATE

    @DeleteMapping("/figuras/eliminarporid/{id}")
    public ResponseEntity<Void> figuraDelete (@PathVariable Long id){

        Figura figuraNew = figuraService.figuraBuscarPorId(id);

        if(figuraNew != null){
            figuraService.figuraEliminar(id); 
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }// DELETE

    //-----------------------------------------------------------------------

    @GetMapping("/figuras/stocktotal")
    public ResponseEntity<List<StockTotalDTO>> figuraTotalStock (){

        List<StockTotalDTO> ListaSTDTO = figuraService.figuraStockTotal();

        if(ListaSTDTO.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(ListaSTDTO);
        }

    }// 1

    @GetMapping("/figuras/stockreservado")
    public ResponseEntity<List<StockReservadoDTO>> figuraReservedStock (){

        List<StockReservadoDTO> listaSRDTO = figuraService.figuraStockReservado();

        if(listaSRDTO.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaSRDTO);
        }

    }// 2

    @GetMapping("/figuras/buscarportamano")
    public ResponseEntity<List<Figura>> figuraFindBySize (@RequestParam float tamano){

        List<Figura> listaFiguras = figuraService.figuraBuscarPorTamano(tamano);

        if(listaFiguras.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaFiguras);
        }

    }// 3

    @GetMapping("/figuras/buscarpormarca")
    public ResponseEntity<List<Figura>> figuraFindByBrand (@RequestParam String marca){

        List<Figura> listaFiguras = figuraService.figuraBuscarPorMarca(marca);

        if(listaFiguras.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaFiguras);
        }

    }// 4

    @GetMapping("/figuras/buscarportipocaja")
    public ResponseEntity<List<Figura>> figuraFindByBoxType (@RequestParam String tipo){

        List<Figura> listaFiguras = figuraService.figuraBuscarPorTipoCaja(tipo);

        if(listaFiguras.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaFiguras);
        }

    }// 5

    // Categoria Figura ---

    @GetMapping("/categoria/buscartodas")
    public ResponseEntity<List<CategoriaFigura>> categoriaFiguraFindAll (){

        List<CategoriaFigura> listaCategorias = categoriaFiguraService.categoriaFiguraBuscarTodo();

        if(listaCategorias.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaCategorias);
        }

    }// FIND ALL
    
    @PostMapping("/categoria/guardar")
    public ResponseEntity<CategoriaFigura> categoriaFiguraSave (@RequestBody CategoriaFigura categoriaFigura){

        CategoriaFigura categoriaNew = categoriaFiguraService.categoriaFiguraGuardar(categoriaFigura);
        return ResponseEntity.ofNullable(categoriaNew);

    }// CREATE

    @GetMapping("/categoria/buscarporid/{id}")
    public ResponseEntity<CategoriaFigura> categoriaFiguraFindById (@PathVariable Long id){

        CategoriaFigura categoriaNew = categoriaFiguraService.categoriaFiguraBuscarPorId(id);
        return ResponseEntity.ofNullable(categoriaNew);

    }// READ

    @PutMapping("/categoria/actualizar/{id}")
    public ResponseEntity<CategoriaFigura> categoriaFiguraUpdate (@PathVariable Long id, @RequestBody CategoriaFigura categoriaFigura){

        CategoriaFigura categoriaNew = categoriaFiguraService.categoriaFiguraBuscarPorId(id);

        if(categoriaNew != null){
            categoriaFiguraService.categoriaFiguraActualizar(id, categoriaFigura);
            return ResponseEntity.ok(categoriaNew);
        }else{
            return ResponseEntity.notFound().build();
        }

    }// UPDATE

    @DeleteMapping("/categoria/eliminarporid/{id}")
    public ResponseEntity<Void> categoriaFiguraDelete (@PathVariable Long id){

        CategoriaFigura categoriaNew = categoriaFiguraService.categoriaFiguraBuscarPorId(id);

        if(categoriaNew != null){
            categoriaFiguraService.categoriaFiguraEliminar(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }// DELETE
    
    //-----------------------------------------------------------------------

    @PutMapping("/categoria/{idCategoria}/vincular/{idFigura}")
    public ResponseEntity<?> linkCategoriaToFigura (@PathVariable Long idCategoria, @PathVariable Long idFigura) {
        
        CategoriaFigura categoriaFigura = categoriaFiguraService.categoriaFiguraBuscarPorId(idCategoria);

        Figura figura = figuraService.figuraBuscarPorId(idFigura);
        
        if (categoriaFigura != null) {
            if (figura != null) {
                categoriaFiguraService.vincularCategoriaAFigura(idCategoria, idFigura);
                return ResponseEntity.ok(figura);
            }else{
                return ResponseEntity.notFound().build();
            }
        }else{
            return ResponseEntity.notFound().build();
        }

    }// 1

    @PutMapping("/categoria/{idCategoria}/desvincular")
    public ResponseEntity<?> unlinkCategoriaFromFigura (@PathVariable Long idCategoria){

        CategoriaFigura categoriaFigura = categoriaFiguraService.categoriaFiguraBuscarPorId(idCategoria);

        if(categoriaFigura != null) {
            categoriaFiguraService.desvincularCategoriaDeFigura(idCategoria);
            return ResponseEntity.ok(categoriaFigura);
        }else{
            return ResponseEntity.notFound().build();
        }
        
    }// 2
    
} 
