package tienda_figura.ms_pedidos.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import tienda_figura.ms_pedidos.dtoCliente.ClienteDTO;
import tienda_figura.ms_pedidos.dtoCliente.ResponseClienteDTO;
import tienda_figura.ms_pedidos.model.EstadoPedidoDTO;
import tienda_figura.ms_pedidos.model.Pedido;
import tienda_figura.ms_pedidos.service.DetallePedidoService;
import tienda_figura.ms_pedidos.service.PedidoService;
import tienda_figura.ms_pedidos.service.TipoPagoService;

@WebMvcTest(PedidosController.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PedidoService pedidoService;

    @MockitoBean
    private DetallePedidoService detallePedidoService;

    @MockitoBean
    private TipoPagoService tipoPagoService;

    @MockitoBean
    private FiguraClientRest figuraClientRest;

    @MockitoBean
    private ClienteClientRest clienteClientRest;

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
        pedido = new Pedido();
    }

    @Test
    public void testPedidoFindAll_ConDatos() throws Exception {
        when(pedidoService.pedidoFindAll()).thenReturn(List.of(pedido));

        mockMvc.perform(get("/api/v1/pedidos/pedido"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPedidoFindAll_Vacio() throws Exception {
        when(pedidoService.pedidoFindAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/pedidos/pedido"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testPedidoFindById_Existe() throws Exception {
        when(pedidoService.pedidoFindById(1L)).thenReturn(pedido);

        mockMvc.perform(get("/api/v1/pedidos/pedido/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPedidoSave() throws Exception {
        when(pedidoService.pedidoSave(any(Pedido.class))).thenReturn(pedido);

        mockMvc.perform(post("/api/v1/pedidos/pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isOk());
    }

    @Test
    public void testPedidoDelete_Existe() throws Exception {
        when(pedidoService.pedidoFindById(1L)).thenReturn(pedido);
        doNothing().when(pedidoService).pedidoDelete(1L);

        mockMvc.perform(delete("/api/v1/pedidos/pedido/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testPedidoDelete_NoExiste() throws Exception {
        when(pedidoService.pedidoFindById(1L)).thenReturn(null);

        mockMvc.perform(delete("/api/v1/pedidos/pedido/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testVerEstadoPedidoDTO_Existe() throws Exception {
        EstadoPedidoDTO estadoDto = mock(EstadoPedidoDTO.class);
        when(pedidoService.verEstadoPedido(1L)).thenReturn(estadoDto);

        mockMvc.perform(get("/api/v1/pedidos/pedido/estadoPedido/1"))
                .andExpect(status().isOk());
}

    @Test
    public void testVerEstadoPedidoDTO_NoExiste() throws Exception {
        when(pedidoService.verEstadoPedido(1L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/pedidos/pedido/estadoPedido/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPedidoUpdate_Existe() throws Exception {
        when(pedidoService.pedidoFindById(1L)).thenReturn(pedido);
        when(pedidoService.pedidoUpdate(eq(1L), any(Pedido.class))).thenReturn(pedido);

        mockMvc.perform(put("/api/v1/pedidos/pedido/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isOk());
    }

    @Test
    public void testBuscarClientePorPedido() throws Exception {
        ResponseClienteDTO responseCliente = new ResponseClienteDTO();
        when(pedidoService.buscarClientePorPedido(1L, 5L)).thenReturn(responseCliente);

        mockMvc.perform(get("/api/v1/pedidos/pedido/buscar/pedido/1/cliente/5"))
                .andExpect(status().isOk());
    }

    @Test
    public void testVincularClienteAPedido_Exitoso() throws Exception {
        ClienteDTO cliente = new ClienteDTO();
        when(clienteClientRest.obtenerDatosCliente(5L)).thenReturn(cliente);
        when(pedidoService.vincularClienteAPedido(1L, 5L)).thenReturn(pedido);

        mockMvc.perform(put("/api/v1/pedidos/pedido/vincular/pedido/1/cliente/5"))
                .andExpect(status().isOk());
    }

    @Test
    public void testVincularClienteAPedido_ClienteNoExiste() throws Exception {
        when(clienteClientRest.obtenerDatosCliente(5L)).thenReturn(null);

        mockMvc.perform(put("/api/v1/pedidos/pedido/vincular/pedido/1/cliente/5"))
                .andExpect(status().isNotFound());
    }
}