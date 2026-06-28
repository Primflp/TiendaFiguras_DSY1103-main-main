package tienda_figura.ms_tienda_empresa.controller;

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

import tienda_figura.ms_tienda_empresa.clients.FiguraClientRest;
import tienda_figura.ms_tienda_empresa.clients.PreventaClientRest;
import tienda_figura.ms_tienda_empresa.dto.PreventaDTO;
import tienda_figura.ms_tienda_empresa.dto.ResponsePreventaPorTiendaDTO;
import tienda_figura.ms_tienda_empresa.dto.ResponseTiendaDTO;
import tienda_figura.ms_tienda_empresa.dtofigura.FiguraDTO;
import tienda_figura.ms_tienda_empresa.dtofigura.ResponseFiguraDTO;
import tienda_figura.ms_tienda_empresa.model.Tienda;
import tienda_figura.ms_tienda_empresa.service.EmpresaService;
import tienda_figura.ms_tienda_empresa.service.TiendaService;

@WebMvcTest(OrganizacionController.class)
public class TiendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TiendaService tiendaService;

    @MockitoBean
    private EmpresaService empresaService;

    @MockitoBean
    private PreventaClientRest preventaClientRest;

    @MockitoBean
    private FiguraClientRest figuraClientRest;

    private Tienda tienda;

    @TestConfiguration
    static class ObjectMapperTestConfig {
        @Bean
        public ObjectMapper testObjectMapper() {
            return new ObjectMapper();
        }
    }

    @BeforeEach
    void setUp() {
        tienda = new Tienda();
        // Inicializa propiedades de Tienda si corresponde
    }

    @Test
    public void testTiendaFindAll_ConDatos() throws Exception {
        when(tiendaService.tiendaFindAll()).thenReturn(List.of(tienda));

        mockMvc.perform(get("/api/v1/organizacion/tienda"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testTiendaFindAll_Vacio() throws Exception {
        when(tiendaService.tiendaFindAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/organizacion/tienda"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testTiendaFindById_Existe() throws Exception {
        when(tiendaService.tiendaFindById(1L)).thenReturn(tienda);

        mockMvc.perform(get("/api/v1/organizacion/tienda/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testTiendaFindById_NoExiste() throws Exception {
        when(tiendaService.tiendaFindById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/organizacion/tienda/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testTiendaSave() throws Exception {
        when(tiendaService.tiendaSave(any(Tienda.class))).thenReturn(tienda);

        mockMvc.perform(post("/api/v1/organizacion/tienda")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tienda)))
                .andExpect(status().isOk());
    }

    @Test
    public void testTiendaDelete_Existe() throws Exception {
        when(tiendaService.tiendaFindById(1L)).thenReturn(tienda);
        doNothing().when(tiendaService).tiendaDelete(1L);

        mockMvc.perform(delete("/api/v1/organizacion/tienda/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testTiendaFindByDireccion() throws Exception {
        when(tiendaService.tiendaFindByDireccion("Av. Providencia 1234")).thenReturn(tienda);

        mockMvc.perform(get("/api/v1/organizacion/tienda/buscarPorDireccion/Av. Providencia 1234"))
                .andExpect(status().isOk());
    }

    @Test
    public void testTiendaFindByNombre() throws Exception {
        when(tiendaService.tiendaFindByNombre("Central")).thenReturn(tienda);

        mockMvc.perform(get("/api/v1/organizacion/tienda/buscarPorNombre/Central"))
                .andExpect(status().isOk());
    }

    @Test
    public void testTiendaUpdate_Existe() throws Exception {
        when(tiendaService.tiendaFindById(1L)).thenReturn(tienda);
        when(tiendaService.tiendaUpdate(eq(1L), any(Tienda.class))).thenReturn(tienda);

        mockMvc.perform(put("/api/v1/organizacion/tienda/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tienda)))
                .andExpect(status().isOk());
    }

    // --- Pruebas OpenFeign Integradas ---

    @Test
    public void testBuscarPreventaPorTienda() throws Exception {
        ResponseTiendaDTO responseDTO = new ResponseTiendaDTO();
        when(tiendaService.buscarPreventaPorTienda(1L, 2L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/organizacion/tienda/buscarPreventaPorTienda/1/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testObtenerPreventasPorTienda() throws Exception {
        ResponsePreventaPorTiendaDTO responseDTO = new ResponsePreventaPorTiendaDTO();
        when(tiendaService.obtenerPreventasPorTienda(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/organizacion/tienda/preventaPorTienda/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testVincularPreventaATienda_Existe() throws Exception {
        PreventaDTO preventaDTO = new PreventaDTO(); // Simula que el cliente OpenFeign encuentra la preventa
        when(preventaClientRest.obtenerDetallePreventa(2L)).thenReturn(preventaDTO);
        when(tiendaService.vincularPreventaATienda(1L, 2L)).thenReturn(tienda);

        mockMvc.perform(put("/api/v1/organizacion/tienda/vincular/tienda/1/preventa/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testVincularPreventaATienda_NoExisteEnCliente() throws Exception {
        when(preventaClientRest.obtenerDetallePreventa(2L)).thenReturn(null);

        mockMvc.perform(put("/api/v1/organizacion/tienda/vincular/tienda/1/preventa/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testBuscarFiguraPorTienda() throws Exception {
        ResponseFiguraDTO responseDTO = new ResponseFiguraDTO();
        when(tiendaService.buscarFiguraPorTienda(1L, 5L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/organizacion/tienda/buscarFiguraPorTienda/tienda/1/figura/5"))
                .andExpect(status().isOk());
    }

    @Test
    public void testVincularFiguraATienda_Existe() throws Exception {
        FiguraDTO figuraDTO = new FiguraDTO();
        figuraDTO.setIdFigura(5L); // Asegura cumplimiento de la condición if
        when(figuraClientRest.obtenerDatosFigura(5L)).thenReturn(figuraDTO);
        when(tiendaService.vincularFiguraATienda(1L, 5L)).thenReturn(tienda);

        mockMvc.perform(put("/api/v1/organizacion/tienda/vincular/tienda/1/figura/5"))
                .andExpect(status().isOk());
    }

    @Test
    public void testVincularFiguraATienda_NoExisteEnCliente() throws Exception {
        when(figuraClientRest.obtenerDatosFigura(5L)).thenReturn(null);

        mockMvc.perform(put("/api/v1/organizacion/tienda/vincular/tienda/1/figura/5"))
                .andExpect(status().isNotFound());
    }
}