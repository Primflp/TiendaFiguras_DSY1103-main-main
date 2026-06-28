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
import tienda_figura.ms_tienda_empresa.model.Empresa;
import tienda_figura.ms_tienda_empresa.service.EmpresaService;
import tienda_figura.ms_tienda_empresa.service.TiendaService;

@WebMvcTest(OrganizacionController.class)
public class EmpresaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EmpresaService empresaService;

    @MockitoBean
    private TiendaService tiendaService;

    @MockitoBean
    private PreventaClientRest preventaClientRest;

    @MockitoBean
    private FiguraClientRest figuraClientRest;

    private Empresa empresa;

    @TestConfiguration
    static class ObjectMapperTestConfig {
        @Bean
        public ObjectMapper testObjectMapper() {
            return new ObjectMapper();
        }
    }

    @BeforeEach
    void setUp() {
        empresa = new Empresa();
        // Inicializa aquí las propiedades de tu modelo Empresa si es necesario
    }

    @Test
    public void testEmpresaFindAll_ConDatos() throws Exception {
        when(empresaService.empresaFindAll()).thenReturn(List.of(empresa));

        mockMvc.perform(get("/api/v1/organizacion/empresa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testEmpresaFindAll_Vacio() throws Exception {
        when(empresaService.empresaFindAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/organizacion/empresa"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testEmpresaFindById_Existe() throws Exception {
        when(empresaService.empresaFindById(1L)).thenReturn(empresa);

        mockMvc.perform(get("/api/v1/organizacion/empresa/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testEmpresaFindById_NoExiste() throws Exception {
        when(empresaService.empresaFindById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/organizacion/empresa/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testEmpresaSave() throws Exception {
        when(empresaService.empresaSave(any(Empresa.class))).thenReturn(empresa);

        mockMvc.perform(post("/api/v1/organizacion/empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empresa)))
                .andExpect(status().isOk());
    }

    @Test
    public void testEmpresaDelete_Existe() throws Exception {
        when(empresaService.empresaFindById(1L)).thenReturn(empresa);
        doNothing().when(empresaService).empresaDelete(1L);

        mockMvc.perform(delete("/api/v1/organizacion/empresa/1"))
                .andExpect(status().isNoContent());

        verify(empresaService, times(1)).empresaDelete(1L);
    }

    @Test
    public void testEmpresaDelete_NoExiste() throws Exception {
        when(empresaService.empresaFindById(1L)).thenReturn(null);

        mockMvc.perform(delete("/api/v1/organizacion/empresa/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testEmpresaFindByIdTributaria_Existe() throws Exception {
        when(empresaService.empresaFindByIdTributaria(123456)).thenReturn(empresa);

        mockMvc.perform(get("/api/v1/organizacion/empresa/tributaria/123456"))
                .andExpect(status().isOk());
    }

    @Test
    public void testEmpresaUpdate_Existe() throws Exception {
        when(empresaService.empresaFindById(1L)).thenReturn(empresa);
        when(empresaService.empresaUpdate(eq(1L), any(Empresa.class))).thenReturn(empresa);

        mockMvc.perform(put("/api/v1/organizacion/empresa/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empresa)))
                .andExpect(status().isOk());
    }

    @Test
    public void testEmpresaUpdate_NoExiste() throws Exception {
        when(empresaService.empresaFindById(1L)).thenReturn(null);

        mockMvc.perform(put("/api/v1/organizacion/empresa/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empresa)))
                .andExpect(status().isNotFound());
    }
}