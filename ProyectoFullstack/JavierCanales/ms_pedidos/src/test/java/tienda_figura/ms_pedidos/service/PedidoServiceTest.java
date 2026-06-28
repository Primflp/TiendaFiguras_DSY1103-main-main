package tienda_figura.ms_pedidos.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import tienda_figura.ms_pedidos.clients.ClienteClientRest;
import tienda_figura.ms_pedidos.dtoCliente.ClienteDTO;
import tienda_figura.ms_pedidos.dtoCliente.ResponseClienteDTO;
import tienda_figura.ms_pedidos.model.EstadoPedidoDTO;
import tienda_figura.ms_pedidos.model.Pedido;
import tienda_figura.ms_pedidos.model.TipoPago;
import tienda_figura.ms_pedidos.repository.PedidoRepository;

@SpringBootTest
public class PedidoServiceTest {

    @Autowired
    private PedidoService pedidoService;

    @MockitoBean
    private PedidoRepository pedidoRepository;

    @MockitoBean
    private ClienteClientRest clienteClientRest;

    @Test
    public void testPedidoFindAll() {
        when(pedidoRepository.findAll()).thenReturn(List.of(new Pedido()));

        List<Pedido> resultado = pedidoService.pedidoFindAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void testPedidoFindById() {
        Long id = 1L;
        Pedido pedido = new Pedido();

        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));

        Pedido found = pedidoService.pedidoFindById(id);

        assertNotNull(found);
    }

    @Test
    public void testPedidoSave() {
        Pedido pedido = new Pedido();
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido saved = pedidoService.pedidoSave(pedido);

        assertNotNull(saved);
    }

    @Test
    public void testPedidoDelete() {
        Long id = 1L;
        doNothing().when(pedidoRepository).deleteById(id);

        pedidoService.pedidoDelete(id);

        verify(pedidoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testVerEstadoPedido() {
        Long id = 1L;
        EstadoPedidoDTO dto = mock(EstadoPedidoDTO.class);
        when(pedidoRepository.verEstadoPedido(id)).thenReturn(dto);

        EstadoPedidoDTO result = pedidoService.verEstadoPedido(id);

        assertNotNull(result);
    }

    @Test
    public void testPedidoUpdate_SiExiste_DebeActualizarCampos() {
        Long id = 1L;
        Pedido existente = new Pedido();
        existente.setEstadoPedido("PENDIENTE");

        TipoPago tipoPagoNuevo = new TipoPago();
        Pedido datosNuevos = new Pedido();
        datosNuevos.setEstadoPedido("ENVIADO");
        datosNuevos.setDireccionEnvio("Nueva Direccion 123");
        datosNuevos.setTipoPago(tipoPagoNuevo);

        when(pedidoRepository.existsById(id)).thenReturn(true);
        when(pedidoRepository.findById(id)).thenReturn(Optional.of(existente));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pedido updated = pedidoService.pedidoUpdate(id, datosNuevos);

        assertNotNull(updated);
        assertEquals("ENVIADO", updated.getEstadoPedido());
        assertEquals("Nueva Direccion 123", updated.getDireccionEnvio());
        assertEquals(tipoPagoNuevo, updated.getTipoPago());
    }

    @Test
    public void testPedidoUpdate_NoExiste_DebeRetornarNull() {
        Long id = 1L;
        Pedido datosNuevos = new Pedido();

        when(pedidoRepository.existsById(id)).thenReturn(false);

        Pedido updated = pedidoService.pedidoUpdate(id, datosNuevos);

        assertNull(updated);
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    public void testBuscarClientePorPedido_CasoExitoso() {
        Long idPedido = 1L;
        Long idCliente = 50L;

        Pedido pedido = new Pedido();
        pedido.setEstadoPedido("ENTREGADO");
        pedido.setDireccionEnvio("Santiago, Chile");

        ClienteDTO clienteDto = new ClienteDTO();
        clienteDto.setNombresCliente("Javier Andrés");
        clienteDto.setApPaternoCliente("Canales");
        clienteDto.setDireccionCliente("La Florida");
        clienteDto.setCorreoCliente("javier@example.com");

        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedido));
        when(clienteClientRest.obtenerDatosCliente(idCliente)).thenReturn(clienteDto);

        ResponseClienteDTO response = pedidoService.buscarClientePorPedido(idPedido, idCliente);

        assertNotNull(response);
        assertEquals("Javier Andrés", response.getNombresCliente());
        assertEquals("ENTREGADO", response.getEstadoPedido());
        assertEquals("Santiago, Chile", response.getDireccionEnvio());
    }

    @Test
    public void testVincularClienteAPedido_CasoExitoso() {
        Long idPedido = 1L;
        Long idCliente = 50L;

        Pedido pedido = new Pedido();
        ClienteDTO clienteDto = new ClienteDTO();
        clienteDto.setIdCliente(idCliente);

        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedido));
        when(clienteClientRest.obtenerDatosCliente(idCliente)).thenReturn(clienteDto);
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pedido result = pedidoService.vincularClienteAPedido(idPedido, idCliente);

        assertNotNull(result);
        assertEquals(idCliente, result.getIdCliente());
    }
}