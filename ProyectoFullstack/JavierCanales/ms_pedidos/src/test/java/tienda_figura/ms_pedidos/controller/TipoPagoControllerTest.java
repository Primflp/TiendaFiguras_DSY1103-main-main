package tienda_figura.ms_pedidos.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import tienda_figura.ms_pedidos.clients.ClienteClientRest;
import tienda_figura.ms_pedidos.clients.FiguraClientRest;
import tienda_figura.ms_pedidos.model.Pedido;
import tienda_figura.ms_pedidos.model.TipoPago;
import tienda_figura.ms_pedidos.service.DetallePedidoService;
import tienda_figura.ms_pedidos.service.PedidoService;
import tienda_figura.ms_pedidos.service.TipoPagoService;

@WebMvcTest(PedidosController.class)
public class TipoPagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TipoPagoService tipoPagoService;

    @MockitoBean
    private PedidoService pedidoService;

    @MockitoBean
    private DetallePedidoService detallePedidoService;

    @MockitoBean
    private FiguraClientRest figuraClientRest;

    @MockitoBean
    private ClienteClientRest clienteClientRest;

    private TipoPago tipoPago;
    private Pedido pedido;

    @TestConfiguration
    static class ObjectMapperTestConfig {
        @Bean
        public ObjectMapper testObjectMapper() {
            return new ObjectMapper();
        }
    }

    @BeforeEach
    void setUp() {
        tipoPago = new TipoPago();
        pedido = new Pedido();
    }

    @Test
    public void testTipoPagoFindAll_ConDatos() throws Exception {
        when(tipoPagoService.tipoPagoFindAll()).thenReturn(List.of(tipoPago));

        mockMvc.perform(get("/api/v1/pedidos/tipoPago"))
                .andExpect(status().isOk());
    }

    @Test
    public void testTipoPagoFindAll_Vacio() throws Exception {
        when(tipoPagoService.tipoPagoFindAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/pedidos/tipoPago"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testTipoPagoFindById_Existe() throws Exception {
        when(tipoPagoService.tipoPagoFindById(1L)).thenReturn(tipoPago);

        mockMvc.perform(get("/api/v1/pedidos/tipoPago/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testTipoPagoSave() throws Exception {
        when(tipoPagoService.tipoPagoSave(any(TipoPago.class))).thenReturn(tipoPago);

        mockMvc.perform(post("/api/v1/pedidos/tipoPago")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tipoPago)))
                .andExpect(status().isOk());
    }

    @Test
    public void testTipoPagoDelete_Existe() throws Exception {
        when(tipoPagoService.tipoPagoFindById(1L)).thenReturn(tipoPago);
        doNothing().when(tipoPagoService).tipoPagoDelete(1L);

        mockMvc.perform(delete("/api/v1/pedidos/tipoPago/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testVincularTipoPago_Exitoso() throws Exception {
        when(tipoPagoService.tipoPagoFindById(1L)).thenReturn(tipoPago);
        when(pedidoService.pedidoFindById(2L)).thenReturn(pedido);
        doNothing().when(tipoPagoService).vincularPedido(1L, 2L);

        mockMvc.perform(put("/api/v1/pedidos/tipoPago/vincular/tipoPago/1/pedido/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testVincularTipoPago_TipoPagoNoExiste() throws Exception {
        when(tipoPagoService.tipoPagoFindById(1L)).thenReturn(null);

        mockMvc.perform(put("/api/v1/pedidos/tipoPago/vincular/tipoPago/1/pedido/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testVincularTipoPago_PedidoNoExiste() throws Exception {
        when(tipoPagoService.tipoPagoFindById(1L)).thenReturn(tipoPago);
        when(pedidoService.pedidoFindById(2L)).thenReturn(null);

        mockMvc.perform(put("/api/v1/pedidos/tipoPago/vincular/tipoPago/1/pedido/2"))
                .andExpect(status().isNotFound());
    }
}