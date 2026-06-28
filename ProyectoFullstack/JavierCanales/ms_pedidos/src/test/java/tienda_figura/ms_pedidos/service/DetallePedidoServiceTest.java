package tienda_figura.ms_pedidos.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import tienda_figura.ms_pedidos.clients.FiguraClientRest;
import tienda_figura.ms_pedidos.dtoFigura.FiguraDTO;
import tienda_figura.ms_pedidos.dtoFigura.ResponseFiguraDTO;
import tienda_figura.ms_pedidos.model.DetallePedido;
import tienda_figura.ms_pedidos.model.Pedido;
import tienda_figura.ms_pedidos.repository.DetallePedidoRepository;

@SpringBootTest
public class DetallePedidoServiceTest {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @MockitoBean
    private DetallePedidoRepository detallePedidoRepository;

    @MockitoBean
    private FiguraClientRest figuraClientRest;

    @Test
    public void testDetallePedidoFindAll() {
        when(detallePedidoRepository.findAll()).thenReturn(List.of(new DetallePedido()));

        List<DetallePedido> resultado = detallePedidoService.detallePedidoFindAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void testDetallePedidoFindById() {
        Long id = 1L;
        DetallePedido detalle = new DetallePedido();

        when(detallePedidoRepository.findById(id)).thenReturn(Optional.of(detalle));

        DetallePedido found = detallePedidoService.detallePedidoFindById(id);

        assertNotNull(found);
    }

    @Test
    public void testDetallePedidoSave() {
        DetallePedido detalle = new DetallePedido();
        when(detallePedidoRepository.save(any(DetallePedido.class))).thenReturn(detalle);

        DetallePedido saved = detallePedidoService.detallePedidoSave(detalle);

        assertNotNull(saved);
    }

    @Test
    public void testDetallePedidoDelete() {
        Long id = 1L;
        doNothing().when(detallePedidoRepository).deleteById(id);

        detallePedidoService.detallePedidoDelete(id);

        verify(detallePedidoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDetallePedidoFiltrarPrecio() {
        int desde = 1000;
        int hasta = 5000;
        when(detallePedidoRepository.detallePedidoFiltrarPrecio(desde, hasta)).thenReturn(List.of(new DetallePedido()));

        List<DetallePedido> resultado = detallePedidoService.detallePedidoFiltrarPrecio(desde, hasta);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void testDetallePedidoUpdate_SiExiste_DebeActualizarCampos() {
        Long id = 1L;
        DetallePedido existente = new DetallePedido();
        existente.setCantidad(2);
        existente.setPrecioUnitario(100);

        DetallePedido datosNuevos = new DetallePedido();
        datosNuevos.setCantidad(5);
        datosNuevos.setPrecioUnitario(200);

        when(detallePedidoRepository.existsById(id)).thenReturn(true);
        when(detallePedidoRepository.findById(id)).thenReturn(Optional.of(existente));
        when(detallePedidoRepository.save(any(DetallePedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DetallePedido updated = detallePedidoService.detallePedidoUpdate(id, datosNuevos);

        assertNotNull(updated);
        assertEquals(5, updated.getCantidad());
        assertEquals(200, updated.getPrecioUnitario());
        assertEquals(200 * 5 * 1.19, updated.getMontoTotal()); // Verifica el comportamiento de calcularMontoMasIva()
    }

    @Test
    public void testDetallePedidoUpdate_NoExiste_DebeRetornarNull() {
        Long id = 1L;
        DetallePedido datosNuevos = new DetallePedido();

        when(detallePedidoRepository.existsById(id)).thenReturn(false);

        DetallePedido updated = detallePedidoService.detallePedidoUpdate(id, datosNuevos);

        assertNull(updated);
        verify(detallePedidoRepository, never()).save(any(DetallePedido.class));
    }

    @Test
    public void testBuscarFiguraPorDetallePedido_CasoExitoso() {
        Long idDetalle = 1L;
        Long idFigura = 100L;

        Pedido pedido = new Pedido();
        pedido.setEstadoPedido("PROCESANDO");
        pedido.setDireccionEnvio("Av. Siempreviva 742");

        DetallePedido detalle = new DetallePedido();
        detalle.setCantidad(3);
        detalle.setPrecioUnitario(150);
        detalle.setMontoTotal(450.0);
        detalle.setPedido(pedido);

        FiguraDTO figuraDto = new FiguraDTO();
        figuraDto.setNombreFigura("Goku Super Saiyan");
        figuraDto.setTamanoFigura(25);
        figuraDto.setMarcaFigura("Bandai");
        figuraDto.setTipoCajaFigura("Coleccionista");

        when(detallePedidoRepository.findById(idDetalle)).thenReturn(Optional.of(detalle));
        when(figuraClientRest.obtenerDatosFiguras(idFigura)).thenReturn(figuraDto);

        ResponseFiguraDTO response = detallePedidoService.buscarFiguraPorDetallePedido(idDetalle, idFigura);

        assertNotNull(response);
        assertEquals("Goku Super Saiyan", response.getNombreFigura());
        assertEquals(3, response.getCantidad());
        assertEquals("PROCESANDO", response.getEstadoPedido());
    }

    @Test
    public void testBuscarFiguraPorDetallePedido_ExceptionCuandoNoExisteDetalle() {
        Long idDetalle = 1L;
        Long idFigura = 100L;

        when(detallePedidoRepository.findById(idDetalle)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            detallePedidoService.buscarFiguraPorDetallePedido(idDetalle, idFigura);
        });
        verify(figuraClientRest, never()).obtenerDatosFiguras(anyLong());
    }

    @Test
    public void testVincularFiguraADetallePedido_CasoExitoso() {
        Long idDetalle = 1L;
        Long idFigura = 100L;

        DetallePedido detalle = new DetallePedido();
        FiguraDTO figuraDto = new FiguraDTO();
        figuraDto.setIdFigura(idFigura);

        when(detallePedidoRepository.findById(idDetalle)).thenReturn(Optional.of(detalle));
        when(figuraClientRest.obtenerDatosFiguras(idFigura)).thenReturn(figuraDto);
        when(detallePedidoRepository.save(any(DetallePedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DetallePedido result = detallePedidoService.vincularFiguraADetallePedido(idDetalle, idFigura);

        assertNotNull(result);
        assertEquals(idFigura, result.getIdFigura());
    }
}