package tienda_figura.ms_preventa.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import tienda_figura.ms_preventa.model.Preventa;
import tienda_figura.ms_preventa.repository.PreventaRepository;

@SpringBootTest
public class PreventaServiceTest {

    @Autowired
    private PreventaService preventaService;

    @MockitoBean
    private PreventaRepository preventaRepository;

    @Test
    public void testPreventaFindAll() {
        when(preventaRepository.findAll()).thenReturn(List.of(new Preventa()));

        List<Preventa> resultado = preventaService.preventaFindAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void testPreventaFindById() {
        Long id = 1L;
        Preventa preventa = new Preventa();

        when(preventaRepository.findById(id)).thenReturn(Optional.of(preventa));

        Preventa found = preventaService.preventaFindById(id);

        assertNotNull(found);
    }

    @Test
    public void testPreventaSave() {
        Preventa preventa = new Preventa();
        when(preventaRepository.save(any(Preventa.class))).thenReturn(preventa);

        Preventa saved = preventaService.preventaSave(preventa);

        assertNotNull(saved);
    }

    @Test
    public void testPreventaDelete() {
        Long id = 1L;
        doNothing().when(preventaRepository).deleteById(id);

        preventaService.preventaDelete(id);

        verify(preventaRepository, times(1)).deleteById(id);
    }

    @Test
    public void testPreventaUpdate_SiExiste_DebeActualizarCampos() {
        Long id = 1L;
        
        Preventa existente = new Preventa();
        existente.setNombrePreventa("Preventa Antigua");

        Preventa datosNuevos = new Preventa();
        datosNuevos.setNombrePreventa("Preventa Nueva");

        when(preventaRepository.existsById(id)).thenReturn(true);
        when(preventaRepository.findById(id)).thenReturn(Optional.of(existente));
        when(preventaRepository.save(any(Preventa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Preventa updated = preventaService.preventaUpdate(id, datosNuevos);

        assertNotNull(updated);
        assertEquals("Preventa Nueva", updated.getNombrePreventa());
    }

    @Test
    public void testPreventaUpdate_NoExiste_DebeRetornarNull() {
        Long id = 1L;
        Preventa datosNuevos = new Preventa();

        when(preventaRepository.existsById(id)).thenReturn(false);

        Preventa updated = preventaService.preventaUpdate(id, datosNuevos);

        assertNull(updated);
        verify(preventaRepository, never()).save(any(Preventa.class));
    }

    @Test
    public void testContarPreventas() {
        long cantidadEsperada = 15L;
        when(preventaRepository.count()).thenReturn(cantidadEsperada);

        long resultado = preventaService.contarPreventas();

        assertEquals(cantidadEsperada, resultado);
        verify(preventaRepository, times(1)).count();
    }
}