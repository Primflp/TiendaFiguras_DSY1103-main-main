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

import tienda_figura.ms_preventa.model.Preventa;
import tienda_figura.ms_preventa.service.DetallePreventaService;
import tienda_figura.ms_preventa.service.PreventaService;

@WebMvcTest(PreventasController.class)
public class PreventaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PreventaService preventaService;

    @MockitoBean
    private DetallePreventaService detallePreventaService;

    private Preventa preventa;

    @TestConfiguration
    static class ObjectMapperTestConfig {
        @Bean
        public ObjectMapper testObjectMapper() {
            return new ObjectMapper();
        }
    }

    @BeforeEach
    void setUp() {
        preventa = new Preventa();
        // Configura propiedades internas de la preventa si es necesario
    }

    @Test
    public void testPreventaFindAll_ConDatos() throws Exception {
        when(preventaService.preventaFindAll()).thenReturn(List.of(preventa));

        mockMvc.perform(get("/api/v1/preventas/preventa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testPreventaFindAll_Vacio() throws Exception {
        when(preventaService.preventaFindAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/preventas/preventa"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testPreventaFindById_Existe() throws Exception {
        when(preventaService.preventaFindById(1L)).thenReturn(preventa);

        mockMvc.perform(get("/api/v1/preventas/preventa/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPreventaFindById_NoExiste() throws Exception {
        when(preventaService.preventaFindById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/preventas/preventa/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPreventaSave() throws Exception {
        when(preventaService.preventaSave(any(Preventa.class))).thenReturn(preventa);

        mockMvc.perform(post("/api/v1/preventas/preventa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(preventa)))
                .andExpect(status().isOk());
    }

    @Test
    public void testPreventaDelete_Existe() throws Exception {
        when(preventaService.preventaFindById(1L)).thenReturn(preventa);
        doNothing().when(preventaService).preventaDelete(1L);

        mockMvc.perform(delete("/api/v1/preventas/preventa/1"))
                .andExpect(status().isNoContent());

        verify(preventaService, times(1)).preventaDelete(1L);
    }

    @Test
    public void testPreventaDelete_NoExiste() throws Exception {
        when(preventaService.preventaFindById(1L)).thenReturn(null);

        mockMvc.perform(delete("/api/v1/preventas/preventa/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPreventaUpdate_Existe() throws Exception {
        when(preventaService.preventaFindById(1L)).thenReturn(preventa);
        when(preventaService.preventaUpdate(eq(1L), any(Preventa.class))).thenReturn(preventa);

        mockMvc.perform(put("/api/v1/preventas/preventa/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(preventa)))
                .andExpect(status().isOk());
    }

    @Test
    public void testPreventaUpdate_NoExiste() throws Exception {
        when(preventaService.preventaFindById(1L)).thenReturn(null);

        mockMvc.perform(put("/api/v1/preventas/preventa/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(preventa)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testContarPreventas() throws Exception {
        when(preventaService.contarPreventas()).thenReturn(15L);

        mockMvc.perform(get("/api/v1/preventas/preventa/contar"))
                .andExpect(status().isOk())
                .andExpect(content().string("15"));
    }
}