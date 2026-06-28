package tienda_figura.ms_preventa.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import tienda_figura.ms_preventa.model.DetallePreventa;
import tienda_figura.ms_preventa.repository.DetallePreventaRepository;

@SpringBootTest
public class DetallePreventaServiceTest {

    @Autowired
    private DetallePreventaService detallePreventaService;

    @MockitoBean
    private DetallePreventaRepository detallePreventaRepository;

    @Test
    public void testDetallePreventaFindAll() {
        when(detallePreventaRepository.findAll()).thenReturn(List.of(new DetallePreventa()));

        List<DetallePreventa> resultado = detallePreventaService.detallePreventaFindAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void testDetallePreventaFindById() {
        Long id = 1L;
        DetallePreventa detalle = new DetallePreventa();
        
        when(detallePreventaRepository.findById(id)).thenReturn(Optional.of(detalle));

        DetallePreventa found = detallePreventaService.detallePreventaFindById(id);

        assertNotNull(found);
    }

    @Test
    public void testDetallePreventaSave_FechasValidas_DebeGuardar() {
        DetallePreventa detalle = new DetallePreventa();
        Date hoy = new Date();
        Date mañana = new Date(hoy.getTime() + 86400000);
        detalle.setFechaInicioPreventa(hoy);
        detalle.setFechaTerminoPreventa(mañana);

        when(detallePreventaRepository.save(any(DetallePreventa.class))).thenReturn(detalle);

        DetallePreventa saved = detallePreventaService.detallePreventaSave(detalle);

        assertNotNull(saved);
    }

    @Test
    public void testDetallePreventaSave_FechaInicioMayorATermino_DebeRetornarNull() {
        DetallePreventa detalle = new DetallePreventa();
        Date hoy = new Date();
        Date ayer = new Date(hoy.getTime() - 86400000);
        
        detalle.setFechaInicioPreventa(hoy);
        detalle.setFechaTerminoPreventa(ayer);

        DetallePreventa saved = detallePreventaService.detallePreventaSave(detalle);

        assertNull(saved);
        verify(detallePreventaRepository, never()).save(any(DetallePreventa.class));
    }

    @Test
    public void testDetallePreventaDelete() {
        Long id = 1L;
        doNothing().when(detallePreventaRepository).deleteById(id);

        detallePreventaService.detallePreventaDelete(id);

        verify(detallePreventaRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDetallePreventaFindByMonth() {
        int mes = 5;
        when(detallePreventaRepository.detallePreventaFindByMonth(mes)).thenReturn(List.of(new DetallePreventa()));

        List<DetallePreventa> resultado = detallePreventaService.detallePreventaFindByMonth(mes);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void testDetallePreventaFiltrarPrecio() {
        int desde = 1000;
        int hasta = 5000;
        when(detallePreventaRepository.detallePreventaFiltrarPrecio(desde, hasta)).thenReturn(List.of(new DetallePreventa()));

        List<DetallePreventa> resultado = detallePreventaService.detallePreventaFiltrarPrecio(desde, hasta);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void testDetallePreventaUpdate_SiExiste_DebeActualizarCampos() {
        Long id = 1L;
        
        DetallePreventa existente = new DetallePreventa();
        existente.setPrecioPreventa(10000);

        DetallePreventa datosNuevos = new DetallePreventa();
        datosNuevos.setPrecioPreventa(25000);
        datosNuevos.setFechaInicioPreventa(new Date());
        datosNuevos.setFechaTerminoPreventa(new Date());

        when(detallePreventaRepository.existsById(id)).thenReturn(true);
        when(detallePreventaRepository.findById(id)).thenReturn(Optional.of(existente));
        when(detallePreventaRepository.save(any(DetallePreventa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DetallePreventa updated = detallePreventaService.detallePreventaUpdate(id, datosNuevos);

        assertNotNull(updated);
        assertEquals(25000, updated.getPrecioPreventa());
    }

    @Test
    public void testDetallePreventaUpdate_NoExiste_DebeRetornarNull() {
        Long id = 1L;
        DetallePreventa datosNuevos = new DetallePreventa();

        when(detallePreventaRepository.existsById(id)).thenReturn(false);

        DetallePreventa updated = detallePreventaService.detallePreventaUpdate(id, datosNuevos);

        assertNull(updated);
        verify(detallePreventaRepository, never()).save(any(DetallePreventa.class));
    }
}