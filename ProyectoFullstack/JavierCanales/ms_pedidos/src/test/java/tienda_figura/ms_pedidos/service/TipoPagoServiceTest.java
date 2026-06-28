package tienda_figura.ms_pedidos.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import tienda_figura.ms_pedidos.model.Pedido;
import tienda_figura.ms_pedidos.model.TipoPago;
import tienda_figura.ms_pedidos.repository.PedidoRepository;
import tienda_figura.ms_pedidos.repository.TipoPagoRepository;

@SpringBootTest
public class TipoPagoServiceTest {

    @Autowired
    private TipoPagoService tipoPagoService;

    @MockitoBean
    private TipoPagoRepository tipoPagoRepository;

    @MockitoBean
    private PedidoRepository pedidoRepository;

    @Test
    public void testTipoPagoFindAll() {
        when(tipoPagoRepository.findAll()).thenReturn(List.of(new TipoPago()));

        List<TipoPago> resultado = tipoPagoService.tipoPagoFindAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void testTipoPagoFindById() {
        Long id = 1L;
        TipoPago tipoPago = new TipoPago();

        when(tipoPagoRepository.findById(id)).thenReturn(Optional.of(tipoPago));

        TipoPago found = tipoPagoService.tipoPagoFindById(id);

        assertNotNull(found);
    }

    @Test
    public void testTipoPagoSave() {
        TipoPago tipoPago = new TipoPago();
        when(tipoPagoRepository.save(any(TipoPago.class))).thenReturn(tipoPago);

        TipoPago saved = tipoPagoService.tipoPagoSave(tipoPago);

        assertNotNull(saved);
    }

    @Test
    public void testTipoPagoDelete() {
        Long id = 1L;
        doNothing().when(tipoPagoRepository).deleteById(id);

        tipoPagoService.tipoPagoDelete(id);

        verify(tipoPagoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testVincularPedido() {
        Long idTipoPago = 1L;
        Long idPedido = 2L;

        TipoPago tipoPago = new TipoPago();
        Pedido pedido = new Pedido();

        when(tipoPagoRepository.findById(idTipoPago)).thenReturn(Optional.of(tipoPago));
        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        tipoPagoService.vincularPedido(idTipoPago, idPedido);

        assertEquals(tipoPago, pedido.getTipoPago());
        verify(pedidoRepository, times(1)).save(pedido);
    }
}