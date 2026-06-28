package tienda_figura.ms_tienda_empresa.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import tienda_figura.ms_tienda_empresa.model.Empresa;
import tienda_figura.ms_tienda_empresa.repository.EmpresaRepository;

@SpringBootTest
public class EmpresaServiceTest {

    @Autowired
    private EmpresaService empresaService;

    @MockitoBean
    private EmpresaRepository empresaRepository;

    @Test
    public void testEmpresaFindAll() {
        when(empresaRepository.findAll()).thenReturn(List.of(new Empresa()));

        List<Empresa> resultado = empresaService.empresaFindAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void testEmpresaFindById() {
        Long id = 1L;
        Empresa empresa = new Empresa();

        when(empresaRepository.findById(id)).thenReturn(Optional.of(empresa));

        Empresa found = empresaService.empresaFindById(id);

        assertNotNull(found);
    }

    @Test
    public void testEmpresaSave() {
        Empresa empresa = new Empresa();
        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresa);

        Empresa saved = empresaService.empresaSave(empresa);

        assertNotNull(saved);
    }

    @Test
    public void testEmpresaDelete() {
        Long id = 1L;
        doNothing().when(empresaRepository).deleteById(id);

        empresaService.empresaDelete(id);

        verify(empresaRepository, times(1)).deleteById(id);
    }

    @Test
    public void testEmpresaFindByIdTributaria() {
        int numTributario = 123456;
        Empresa empresa = new Empresa();
        when(empresaRepository.empresaFindByIdTributaria(numTributario)).thenReturn(empresa);

        Empresa result = empresaService.empresaFindByIdTributaria(numTributario);

        assertNotNull(result);
    }

    @Test
    public void testEmpresaUpdate_SiExiste_DebeActualizarCampos() {
        Long id = 1L;
        Empresa existente = new Empresa();
        existente.setNombreEmpresa("Antigua S.A.");

        Empresa datosNuevos = new Empresa();
        datosNuevos.setIdentificacionTributaria(999);
        datosNuevos.setNombreEmpresa("Nueva SpA");
        datosNuevos.setCorreoEmpresa("contacto@nueva.cl");
        datosNuevos.setTelefonoEmpresa(987654321);

        when(empresaRepository.existsById(id)).thenReturn(true);
        when(empresaRepository.findById(id)).thenReturn(Optional.of(existente));
        when(empresaRepository.save(any(Empresa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Empresa updated = empresaService.empresaUpdate(id, datosNuevos);

        assertNotNull(updated);
        assertEquals("Nueva SpA", updated.getNombreEmpresa());
        assertEquals(999, updated.getIdentificacionTributaria());
        assertEquals("contacto@nueva.cl", updated.getCorreoEmpresa());
    }

    @Test
    public void testEmpresaUpdate_NoExiste_DebeRetornarNull() {
        Long id = 1L;
        Empresa datosNuevos = new Empresa();

        when(empresaRepository.existsById(id)).thenReturn(false);

        Empresa updated = empresaService.empresaUpdate(id, datosNuevos);

        assertNull(updated);
        verify(empresaRepository, never()).save(any(Empresa.class));
    }
}