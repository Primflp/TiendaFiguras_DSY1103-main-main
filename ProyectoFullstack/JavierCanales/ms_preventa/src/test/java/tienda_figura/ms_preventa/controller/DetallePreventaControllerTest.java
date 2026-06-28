package tienda_figura.ms_preventa.controller;

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

import tienda_figura.ms_preventa.model.DetallePreventa;
import tienda_figura.ms_preventa.service.DetallePreventaService;
import tienda_figura.ms_preventa.service.PreventaService;

@WebMvcTest(PreventasController.class)
public class DetallePreventaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DetallePreventaService detallePreventaService;

    @MockitoBean
    private PreventaService preventaService;

    private DetallePreventa detalle;

    @TestConfiguration
    static class ObjectMapperTestConfig {
        @Bean
        public ObjectMapper testObjectMapper() {
            return new ObjectMapper();
        }
    }

    @BeforeEach
    void setUp() {
        detalle = new DetallePreventa();
    }

    @Test
    public void testDetallePreventaFindAll_ConDatos() throws Exception {
        when(detallePreventaService.detallePreventaFindAll()).thenReturn(List.of(detalle));

        mockMvc.perform(get("/api/v1/preventas/detallePreventa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testDetallePreventaFindAll_Vacio() throws Exception {
        when(detallePreventaService.detallePreventaFindAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/preventas/detallePreventa"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDetallePreventaFindById_Existe() throws Exception {
        when(detallePreventaService.detallePreventaFindById(1L)).thenReturn(detalle);

        mockMvc.perform(get("/api/v1/preventas/detallePreventa/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDetallePreventaFindById_NoExiste() throws Exception {
        when(detallePreventaService.detallePreventaFindById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/preventas/detallePreventa/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDetallePreventaSave() throws Exception {
        when(detallePreventaService.detallePreventaSave(any(DetallePreventa.class))).thenReturn(detalle);

        mockMvc.perform(post("/api/v1/preventas/detallePreventa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detalle)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDetallePreventaDelete_Existe() throws Exception {
        when(detallePreventaService.detallePreventaFindById(1L)).thenReturn(detalle);
        doNothing().when(detallePreventaService).detallePreventaDelete(1L);

        mockMvc.perform(delete("/api/v1/preventas/detallePreventa/1"))
                .andExpect(status().isNoContent());

        verify(detallePreventaService, times(1)).detallePreventaDelete(1L);
    }

    @Test
    public void testDetallePreventaDelete_NoExiste() throws Exception {
        when(detallePreventaService.detallePreventaFindById(1L)).thenReturn(null);

        mockMvc.perform(delete("/api/v1/preventas/detallePreventa/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDetallePreventaFindByMonth_ConDatos() throws Exception {
        when(detallePreventaService.detallePreventaFindByMonth(5)).thenReturn(List.of(detalle));

        mockMvc.perform(get("/api/v1/preventas/detallePreventa/buscarMes/5"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDetallePreventaFindByMonth_Vacio() throws Exception {
        when(detallePreventaService.detallePreventaFindByMonth(5)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/preventas/detallePreventa/buscarMes/5"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDetallePreventaFiltrarPrecio_ConDatos() throws Exception {
        when(detallePreventaService.detallePreventaFiltrarPrecio(5000, 15000)).thenReturn(List.of(detalle));

        mockMvc.perform(get("/api/v1/preventas/detallePreventa/filtrarPrecio")
                        .param("desde", "5000")
                        .param("hasta", "15000"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDetallePreventaUpdate_Existe() throws Exception {
        when(detallePreventaService.detallePreventaFindById(1L)).thenReturn(detalle);
        when(detallePreventaService.detallePreventaUpdate(eq(1L), any(DetallePreventa.class))).thenReturn(detalle);

        mockMvc.perform(put("/api/v1/preventas/detallePreventa/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detalle)))
                .andExpect(status().isOk());
    }
}