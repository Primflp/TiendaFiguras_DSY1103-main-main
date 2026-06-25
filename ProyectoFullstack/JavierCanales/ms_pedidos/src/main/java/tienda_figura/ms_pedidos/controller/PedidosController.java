package tienda_figura.ms_pedidos.controller;

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
import tienda_figura.ms_pedidos.clients.ClienteClientRest;
import tienda_figura.ms_pedidos.clients.FiguraClientRest;
import tienda_figura.ms_pedidos.dtoCliente.ClienteDTO;
import tienda_figura.ms_pedidos.dtoCliente.ResponseClienteDTO;
import tienda_figura.ms_pedidos.dtoFigura.FiguraDTO;
import tienda_figura.ms_pedidos.dtoFigura.ResponseFiguraDTO;
import tienda_figura.ms_pedidos.model.DetallePedido;
import tienda_figura.ms_pedidos.model.EstadoPedidoDTO;
import tienda_figura.ms_pedidos.model.Pedido;
import tienda_figura.ms_pedidos.model.TipoPago;
import tienda_figura.ms_pedidos.service.DetallePedidoService;
import tienda_figura.ms_pedidos.service.PedidoService;
import tienda_figura.ms_pedidos.service.TipoPagoService;

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedidos", description = "Acciones relacionadas con pedidos")

public class PedidosController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private TipoPagoService tipoPagoService;

    @Autowired
    private FiguraClientRest figuraClientRest;

    @Autowired ClienteClientRest clienteClientRest;

    // DetallePedido

    @GetMapping("/detallePedido")
    @Operation(summary = "Obtener una lista de todos los detalles de pedidos", description = "Obtiene una lista de todos los detalles de cada pedido presente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se obtuvo el listado correctamente", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = DetallePedido.class))),
            @ApiResponse(
                        responseCode = "204", description = "No existe ningun detalle de pedido en el sistema")
    })
    public ResponseEntity<List<DetallePedido>> detallePedidoFindAll(){

        List<DetallePedido> listaDetallePedido = detallePedidoService.detallePedidoFindAll();

        if(listaDetallePedido.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaDetallePedido);
        }     
    }

    @GetMapping("/detallePedido/{id}")
    @Operation(summary = "Buscar un detalle de un pedido utilizando una id", description = "Obtiene un detalle de pedido en la cual su ID coincida con la ID de la busqueda")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se obtuvo el detalle de pedido con la ID buscada", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = DetallePedido.class))),
            @ApiResponse(
                        responseCode = "404", description = "No existe ningun detalle de pedido que coincida con esa ID")
    })
    public ResponseEntity<DetallePedido> detallePedidoFindById(@PathVariable Long id){
        
        DetallePedido detallePedido = detallePedidoService.detallePedidoFindById(id);
        
        return ResponseEntity.ofNullable(detallePedido);
    }

    @PostMapping("/detallePedido")
    @Operation(summary = "Guardar un detalle pedido", description = "Guarda un nuevo detalle pedido en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se guardo correctamente el nuevo detalle pedido", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = DetallePedido.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se pudo guardar el detalle pedido")
    })
    public ResponseEntity<DetallePedido> detallePedidoSave(@RequestBody DetallePedido detallePedido){
        
        DetallePedido detallePedido2 = detallePedidoService.detallePedidoSave(detallePedido);

        return ResponseEntity.ofNullable(detallePedido2);
    }

    @DeleteMapping("/detallePedido/{id}")
    @Operation(summary = "Elimina un detalle pedido", description = "Elimina un detalle pedido del sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se ha eliminado correctamente el detalle pedido con la ID seleccionada", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = DetallePedido.class))),
            @ApiResponse(
                        responseCode = "404", description = "El detalle pedido que esta intentando eliminar no existe")
    })
    
    public ResponseEntity<Void> detallePedidoDelete(@PathVariable Long id){

        DetallePedido detallePedido = detallePedidoService.detallePedidoFindById(id);

        if(detallePedido != null){
            detallePedidoService.detallePedidoDelete(id);
            return ResponseEntity.noContent().build();

        }else{
            return ResponseEntity.notFound().build();
        }

    }

        // metodos custom DetallePedido

    @GetMapping("/detallePedido/filtrarPrecio")
    @Operation(summary = "Filtra los detalle pedido por precio", description = "Se filtra los detalle pedido que estan dentro de un rango de precio correspondiente")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se filtró correctamente los detalle pedido dentro del rango de precio buscado", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = DetallePedido.class))),
            @ApiResponse(
                        responseCode = "204", description = "No se logró encontrar un detalle pedido dentro del rango de precio buscado")
    })
    public ResponseEntity<List<DetallePedido>> detallePedidoFiltrarPrecio(@RequestParam int desde, @RequestParam int hasta){
        
        List<DetallePedido> listaDetallePedido = detallePedidoService.detallePedidoFiltrarPrecio(desde, hasta);

        if(listaDetallePedido.isEmpty()){
            return ResponseEntity.noContent().build();
            
        }else{
            return ResponseEntity.ok(listaDetallePedido);
        }
    }

    @PutMapping("/detallePedido/actualizar/{id}")
    @Operation(summary = "Actualizar un detalle pedido", description = "Actualiza los datos de un detalle pedido existente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se actualizó correctamente el nuevo detalle pedido", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = DetallePedido.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se pudo encontrar el detalle pedido a actualizar")
    })
    public ResponseEntity<DetallePedido> detallePedidoUpdate(@PathVariable Long id, @RequestBody DetallePedido detallePedidoUpdate){

        DetallePedido detallePedido = detallePedidoService.detallePedidoFindById(id);

        if(detallePedido != null){
            detallePedidoService.detallePedidoUpdate(id, detallePedidoUpdate);
            return ResponseEntity.ok(detallePedido);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // metodos de OpenFeign DetallePedido

    @GetMapping("/detallePedido/buscar/detallePedido/{idDetallePedido}/figura/{idFigura}")
    @Operation(summary = "Busca un detalle pedido", description = "Busca un detalle pedido en base a la figura que esta asocidada")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se obtuvo correctamente el detalle pedido asociado a la figura", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = DetallePedido.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se encontro un detalle pedido")
    })
    public ResponseEntity<ResponseFiguraDTO> buscarFiguraPorDetallePedido(@PathVariable Long idDetallePedido, @PathVariable Long idFigura){
        return ResponseEntity.ok(detallePedidoService.buscarFiguraPorDetallePedido(idDetallePedido, idFigura));
    }

    @PutMapping("/detallePedido/vincular/detallePedido/{idDetallePedido}/figura/{idFigura}")
    @Operation(summary = "Vincula un detalle pedido a una figura", description = "Vincula un detalle pedido a una de las figuras existentes")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se vinculo correctamente el detalle pedido con la figura seleccionada", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = DetallePedido.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se encontro un detalle pedido o figura para vincular")
    })
    public ResponseEntity<DetallePedido> vincularFiguraADetallePedido(@PathVariable Long idDetallePedido, @PathVariable Long idFigura){

        FiguraDTO figura = figuraClientRest.obtenerDatosFiguras(idFigura);

        if(figura != null){
            DetallePedido detallePedido = detallePedidoService.vincularFiguraADetallePedido(idDetallePedido, idFigura);
            return ResponseEntity.ok(detallePedido);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // Pedido

    @GetMapping("/pedido")
    @Operation(summary = "Obtener una lista de todos los pedidos", description = "Obtiene una lista de todos los pedidos presente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se obtuvo el listado correctamente", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(
                        responseCode = "204", description = "No existe ningun pedido en el sistema")
    })
    public ResponseEntity<List<Pedido>> pedidoFindAll(){
        
        List<Pedido> listaPedido = pedidoService.pedidoFindAll();

        if(listaPedido.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaPedido);
        }
    }

    @GetMapping("/pedido/{id}")
    @Operation(summary = "Buscar un pedido utilizando una id", description = "Obtiene un pedido en la cual su ID coincida con la ID de la busqueda")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se obtuvo el pedido con la ID buscada", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(
                        responseCode = "404", description = "No existe ningun pedido que coincida con esa ID")
    })
    public ResponseEntity<Pedido> pedidoFindById(@PathVariable Long id){
        Pedido pedido = pedidoService.pedidoFindById(id);

        return ResponseEntity.ofNullable(pedido);
    }

    @PostMapping("/pedido")
    @Operation(summary = "Guarda un pedido", description = "Guarda un nuevo pedido en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se guardo correctamente el nuevo pedido", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se pudo guardar el pedido")
    })
    public ResponseEntity<Pedido> pedidoSave(@RequestBody Pedido pedido){
        Pedido pedido2 = pedidoService.pedidoSave(pedido);

        return ResponseEntity.ofNullable(pedido2);
    }

    @DeleteMapping("/pedido/{id}")
    @Operation(summary = "Elimina un pedido", description = "Elimina un pedido del sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se ha eliminado correctamente el pedido con la ID seleccionada", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(
                        responseCode = "404", description = "El pedido que esta intentando eliminar no existe")
    })
    public ResponseEntity<Void> pedidoDelete(@PathVariable Long id){
        
        Pedido pedido = pedidoService.pedidoFindById(id);

        if(pedido != null){
            pedidoService.pedidoDelete(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

        // metodos custom pedido

    @GetMapping("/pedido/estadoPedido/{id}")
    @Operation(summary = "ver el estado de un pedido", description = "Visualiza la información relevante con respecto al estado de un pedido")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se ha obtenido correctamente el estado del pedido con la ID buscada", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(
                        responseCode = "404", description = "El pedido que esta intentando no existe")
    })
    public ResponseEntity<EstadoPedidoDTO> verEstadoPedidoDTO(@PathVariable Long id){

        EstadoPedidoDTO estadoPedidoDTO = pedidoService.verEstadoPedido(id);
        
        if(estadoPedidoDTO != null){
            return ResponseEntity.ok(estadoPedidoDTO);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/pedido/actualizar/{id}")
    @Operation(summary = "Actualizar un pedido", description = "Actualiza los datos de un pedido existente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se actualizó correctamente el nuevo pedido", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se pudo encontrar el pedido a actualizar")
    })
    public ResponseEntity<Pedido> pedidoUpdate(@PathVariable Long id, @RequestBody Pedido pedidoUpdate){

        Pedido pedido = pedidoService.pedidoFindById(id);

        if(pedido != null){
            pedidoService.pedidoUpdate(id, pedidoUpdate);
            return ResponseEntity.ok(pedido);
        }else{
            return ResponseEntity.notFound().build();
        }

    }

        // metodos custom para OpenFeign Pedido

    @GetMapping("/pedido/buscar/pedido/{idPedido}/cliente/{idCliente}")
    @Operation(summary = "Busca un pedido por cliente", description = "Busca un detalle pedido en base al cliente que esta asocidado")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se obtuvo correctamente el detalle pedido asociado a la figura", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(
                        responseCode = "404", description = "El pedido o cliente que esta intentando utilizar no existen")
    })
    public ResponseEntity<ResponseClienteDTO> buscarClientePorPedido(@PathVariable Long idPedido, @PathVariable Long idCliente){
        return ResponseEntity.ok(pedidoService.buscarClientePorPedido(idPedido, idCliente));
    }

    @PutMapping("/pedido/vincular/pedido/{idPedido}/cliente/{idCliente}")
    @Operation(summary = "Vincula un pedido a un cliente", description = "Vincula un pedido a uno de los clientes existentes")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se vinculo correctamente el pedido con el cliente seleccionado", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se encontro un pedido o cliente para vincular")
    })
    public ResponseEntity<?> vincularClienteAPedido(@PathVariable Long idPedido, @PathVariable Long idCliente){

        ClienteDTO cliente = clienteClientRest.obtenerDatosCliente(idCliente);

        if(cliente != null){
            Pedido pedido = pedidoService.vincularClienteAPedido(idPedido, idCliente);
            return ResponseEntity.ok(pedido);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // TipoPago

    @GetMapping("/tipoPago")
    @Operation(summary = "Obtener una lista de todos los tipos de pago", description = "Obtiene una lista de todos los tipos de pago presentes en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se obtuvo el listado correctamente", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TipoPago.class))),
            @ApiResponse(
                        responseCode = "204", description = "No existe ningun tipo de pago en el sistema")
    })
    public ResponseEntity<List<TipoPago>> tipoPagoFindAll(){
        
        List<TipoPago> listaTipoPago = tipoPagoService.tipoPagoFindAll();

        if(listaTipoPago.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaTipoPago);
        }
    }

    @GetMapping("/tipoPago/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se obtuvo el tipo de pago con la ID buscada", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TipoPago.class))),
            @ApiResponse(
                        responseCode = "404", description = "No existe ningun tipo de pago que coincida con esa ID")
    })
    public ResponseEntity<TipoPago> tipoPagoFindById(@PathVariable Long id){

        TipoPago tipoPago = tipoPagoService.tipoPagoFindById(id);
        
        return ResponseEntity.ofNullable(tipoPago);
    }

    @PostMapping("/tipoPago")
    @Operation(summary = "Guarda un tipo de pago", description = "Guarda un nuevo tipo de pago en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se guardo correctamente el nuevo tipo de pago", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TipoPago.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se pudo guardar el tipo de pago")
    })
    public ResponseEntity<TipoPago> tipoPagoSave(@RequestBody TipoPago tipoPago){
        
        TipoPago tipoPago2 = tipoPagoService.tipoPagoSave(tipoPago);
        
        return ResponseEntity.ofNullable(tipoPago2);
    }

    @DeleteMapping("/tipoPago/{id}")
    @Operation(summary = "Elimina un tipo de pago", description = "Elimina un tipo de pago del sistema")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se ha eliminado correctamente el tipo de pago con la ID seleccionada", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(
                        responseCode = "404", description = "El tipo de pago que esta intentando eliminar no existe")
    })
    public ResponseEntity<Void> tipoPagoDelete(@PathVariable Long id){
        
        TipoPago tipoPago = tipoPagoService.tipoPagoFindById(id);
        
        if(tipoPago != null){
            tipoPagoService.tipoPagoDelete(id);
            return ResponseEntity.noContent().build();    
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // metodos custom TipoPago

    @PutMapping("/tipoPago/vincular/tipoPago/{idTipoPago}/pedido/{idPedido}")
    @Operation(summary = "Vincula un tipo de pago a un pedido", description = "Vincula un tipo de pago a uno de los pedidos existentes")
    @ApiResponses(value = {
            @ApiResponse(
                        responseCode = "200",
                        description = "Se vinculo correctamente el tipo de pago con el pedido seleccionado", 
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = TipoPago.class))),
            @ApiResponse(
                        responseCode = "404", description = "No se encontro un tipo de pago o cliente para vincular")
    })
    public ResponseEntity<?> vincularTipoPago(@PathVariable Long idTipoPago, @PathVariable Long idPedido){

        TipoPago tipoPago = tipoPagoService.tipoPagoFindById(idTipoPago);

        Pedido pedido = pedidoService.pedidoFindById(idPedido);

        if(tipoPago != null){
            if(pedido != null){
                tipoPagoService.vincularPedido(idTipoPago, idPedido);
                return ResponseEntity.ok(pedido);
            }else{
                return ResponseEntity.notFound().build();
            }
        }else{
            return ResponseEntity.notFound().build();
        }

    }
}
