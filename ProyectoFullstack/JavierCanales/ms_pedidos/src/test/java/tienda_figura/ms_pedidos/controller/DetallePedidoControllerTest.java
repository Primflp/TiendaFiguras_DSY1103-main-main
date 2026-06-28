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
import tienda_figura.ms_pedidos.dtoFigura.FiguraDTO;
import tienda_figura.ms_pedidos.dtoFigura.ResponseFiguraDTO;
import tienda_figura.ms_pedidos.model.DetallePedido;
import tienda_figura.ms_pedidos.service.DetallePedidoService;
import tienda_figura.ms_pedidos.service.PedidoService;
import tienda_figura.ms_pedidos.service.TipoPagoService;

@WebMvcTest(PedidosController.class)
public class DetallePedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DetallePedidoService detallePedidoService;

    @MockitoBean
    private PedidoService pedidoService;

    @MockitoBean
    private TipoPagoService tipoPagoService;

    @MockitoBean
    private FiguraClientRest figuraClientRest;

    @MockitoBean
    private ClienteClientRest clienteClientRest;

    private DetallePedido detalle;

    @TestConfiguration
    static class ObjectMapperTestConfig {
        @Bean
        public ObjectMapper testObjectMapper() {
            return new ObjectMapper();
        }
    }

    @BeforeEach
    void setUp() {
        detalle = new DetallePedido();
        // Asumiendo un setter hipotético para ID si aplica, o inicializando propiedades básicas
    }

    @Test
    public void testDetallePedidoFindAll_ConDatos() throws Exception {
        when(detallePedidoService.detallePedidoFindAll()).thenReturn(List.of(detalle));

        mockMvc.perform(get("/api/v1/pedidos/detallePedido"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testDetallePedidoFindAll_Vacio() throws Exception {
        when(detallePedidoService.detallePedidoFindAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/pedidos/detallePedido"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDetallePedidoFindById_Existe() throws Exception {
        when(detallePedidoService.detallePedidoFindById(1L)).thenReturn(detalle);

        mockMvc.perform(get("/api/v1/pedidos/detallePedido/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDetallePedidoFindById_NoExiste() throws Exception {
        when(detallePedidoService.detallePedidoFindById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/pedidos/detallePedido/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDetallePedidoSave() throws Exception {
        when(detallePedidoService.detallePedidoSave(any(DetallePedido.class))).thenReturn(detalle);

        mockMvc.perform(post("/api/v1/pedidos/detallePedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detalle)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDetallePedidoDelete_Existe() throws Exception {
        when(detallePedidoService.detallePedidoFindById(1L)).thenReturn(detalle);
        doNothing().when(detallePedidoService).detallePedidoDelete(1L);

        mockMvc.perform(delete("/api/v1/pedidos/detallePedido/1"))
                .andExpect(status().isNoContent());

        verify(detallePedidoService, times(1)).detallePedidoDelete(1L);
    }

    @Test
    public void testDetallePedidoDelete_NoExiste() throws Exception {
        when(detallePedidoService.detallePedidoFindById(1L)).thenReturn(null);

        mockMvc.perform(delete("/api/v1/pedidos/detallePedido/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDetallePedidoFiltrarPrecio_ConDatos() throws Exception {
        when(detallePedidoService.detallePedidoFiltrarPrecio(1000, 5000)).thenReturn(List.of(detalle));

        mockMvc.perform(get("/api/v1/pedidos/detallePedido/filtrarPrecio")
                        .param("desde", "1000")
                        .param("hasta", "5000"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDetallePedidoUpdate_Existe() throws Exception {
        when(detallePedidoService.detallePedidoFindById(1L)).thenReturn(detalle);
        when(detallePedidoService.detallePedidoUpdate(eq(1L), any(DetallePedido.class))).thenReturn(detalle);

        mockMvc.perform(put("/api/v1/pedidos/detallePedido/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detalle)))
                .andExpect(status().isOk());
    }

    @Test
    public void testBuscarFiguraPorDetallePedido() throws Exception {
        ResponseFiguraDTO responseFigura = new ResponseFiguraDTO();
        when(detallePedidoService.buscarFiguraPorDetallePedido(1L, 10L)).thenReturn(responseFigura);

        mockMvc.perform(get("/api/v1/pedidos/detallePedido/buscar/detallePedido/1/figura/10"))
                .andExpect(status().isOk());
    }

    @Test
    public void testVincularFiguraADetallePedido_Exitoso() throws Exception {
        FiguraDTO figura = new FiguraDTO();
        when(figuraClientRest.obtenerDatosFiguras(10L)).thenReturn(figura);
        when(detallePedidoService.vincularFiguraADetallePedido(1L, 10L)).thenReturn(detalle);

        mockMvc.perform(put("/api/v1/pedidos/detallePedido/vincular/detallePedido/1/figura/10"))
                .andExpect(status().isOk());
    }

    @Test
    public void testVincularFiguraADetallePedido_FiguraNoExiste() throws Exception {
        when(figuraClientRest.obtenerDatosFiguras(10L)).thenReturn(null);

        mockMvc.perform(put("/api/v1/pedidos/detallePedido/vincular/detallePedido/1/figura/10"))
                .andExpect(status().isNotFound());
    }
}