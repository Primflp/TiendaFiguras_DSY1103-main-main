package tienda_figura.ms_tienda_empresa.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import tienda_figura.ms_tienda_empresa.clients.FiguraClientRest;
import tienda_figura.ms_tienda_empresa.clients.PreventaClientRest;
import tienda_figura.ms_tienda_empresa.dto.PreventaDTO;
import tienda_figura.ms_tienda_empresa.dto.ResponsePreventaPorTiendaDTO;
import tienda_figura.ms_tienda_empresa.dto.ResponseTiendaDTO;
import tienda_figura.ms_tienda_empresa.dtofigura.FiguraDTO;
import tienda_figura.ms_tienda_empresa.dtofigura.ResponseFiguraDTO;
import tienda_figura.ms_tienda_empresa.model.Tienda;
import tienda_figura.ms_tienda_empresa.repository.TiendaRepository;

@SpringBootTest
public class TiendaServiceTest {

    @Autowired
    private TiendaService tiendaService;

    @MockitoBean
    private TiendaRepository tiendaRepository;

    @MockitoBean
    private PreventaClientRest preventaClientRest;

    @MockitoBean
    private FiguraClientRest figuraClientRest;

    @Test
    public void testTiendaFindAll() {
        when(tiendaRepository.findAll()).thenReturn(List.of(new Tienda()));

        List<Tienda> resultado = tiendaService.tiendaFindAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void testTiendaFindById() {
        Long id = 1L;
        Tienda tienda = new Tienda();
        when(tiendaRepository.findById(id)).thenReturn(Optional.of(tienda));

        Tienda found = tiendaService.tiendaFindById(id);

        assertNotNull(found);
    }

    @Test
    public void testTiendaSave() {
        Tienda tienda = new Tienda();
        when(tiendaRepository.save(any(Tienda.class))).thenReturn(tienda);

        Tienda saved = tiendaService.tiendaSave(tienda);

        assertNotNull(saved);
    }

    @Test
    public void testTiendaDelete() {
        Long id = 1L;
        doNothing().when(tiendaRepository).deleteById(id);

        tiendaService.tiendaDelete(id);

        verify(tiendaRepository, times(1)).deleteById(id);
    }

    @Test
    public void testTiendaFindByDireccion() {
        String direccion = "Av. Providencia 1234";
        Tienda tienda = new Tienda();
        when(tiendaRepository.tiendaFindByDireccion(direccion)).thenReturn(tienda);

        Tienda result = tiendaService.tiendaFindByDireccion(direccion);

        assertNotNull(result);
    }

    @Test
    public void testTiendaFindByNombre() {
        String nombre = "Tienda Central";
        Tienda tienda = new Tienda();
        when(tiendaRepository.tiendaFindByNombre(nombre)).thenReturn(tienda);

        Tienda result = tiendaService.tiendaFindByNombre(nombre);

        assertNotNull(result);
    }

    @Test
    public void testTiendaUpdate_SiExiste_DebeActualizarCampos() {
        Long id = 1L;
        Tienda existente = new Tienda();
        existente.setNombreTienda("Tienda Alfa");

        Tienda datosNuevos = new Tienda();
        datosNuevos.setNombreTienda("Tienda Omega");
        datosNuevos.setDireccionTienda("Nueva Calle 456");
        datosNuevos.setTelefonoTienda(22334455);

        when(tiendaRepository.existsById(id)).thenReturn(true);
        when(tiendaRepository.findById(id)).thenReturn(Optional.of(existente));
        when(tiendaRepository.save(any(Tienda.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Tienda updated = tiendaService.tiendaUpdate(id, datosNuevos);

        assertNotNull(updated);
        assertEquals("Tienda Omega", updated.getNombreTienda());
        assertEquals("Nueva Calle 456", updated.getDireccionTienda());
    }

    @Test
    public void testTiendaUpdate_NoExiste_DebeRetornarNull() {
        Long id = 1L;
        Tienda datosNuevos = new Tienda();

        when(tiendaRepository.existsById(id)).thenReturn(false);

        Tienda updated = tiendaService.tiendaUpdate(id, datosNuevos);

        assertNull(updated);
        verify(tiendaRepository, never()).save(any(Tienda.class));
    }

    @Test
    public void testBuscarPreventaPorTienda_CasoExitoso() {
        Long idTienda = 1L;
        Long idPreventa = 10L;

        Tienda tienda = new Tienda();
        tienda.setNombreTienda("Tienda Vitacura");
        tienda.setDireccionTienda("Av. Vitacura 999");
        tienda.setTelefonoTienda(5551234);

        PreventaDTO preventaDto = new PreventaDTO();
        preventaDto.setNombrePreventa("Preventa Myth Cloth");

        when(tiendaRepository.findByIdTiendaAndIdPreventa(idTienda, idPreventa)).thenReturn(Optional.of(tienda));
        when(preventaClientRest.obtenerDetallePreventa(idPreventa)).thenReturn(preventaDto);

        ResponseTiendaDTO response = tiendaService.buscarPreventaPorTienda(idTienda, idPreventa);

        assertNotNull(response);
        assertEquals("Preventa Myth Cloth", response.getNombrePreventa());
        assertEquals("Tienda Vitacura", response.getNombreTienda());
    }

    @Test
    public void testBuscarPreventaPorTienda_ExceptionCuandoNoExiste() {
        Long idTienda = 1L;
        Long idPreventa = 10L;

        when(tiendaRepository.findByIdTiendaAndIdPreventa(idTienda, idPreventa)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            tiendaService.buscarPreventaPorTienda(idTienda, idPreventa);
        });
    }

    @Test
    public void testObtenerPreventasPorTienda_CasoExitoso() {
        Long idTienda = 1L;

        Tienda tienda = new Tienda();
        tienda.setNombreTienda("Tienda Mall");
        tienda.setIdPreventa(new ArrayList<>(List.of(101L, 102L)));

        PreventaDTO p1 = new PreventaDTO();
        PreventaDTO p2 = new PreventaDTO();

        when(tiendaRepository.findById(idTienda)).thenReturn(Optional.of(tienda));
        when(preventaClientRest.obtenerDetallePreventa(101L)).thenReturn(p1);
        when(preventaClientRest.obtenerDetallePreventa(102L)).thenReturn(p2);

        ResponsePreventaPorTiendaDTO response = tiendaService.obtenerPreventasPorTienda(idTienda);

        assertNotNull(response);
        assertEquals("Tienda Mall", response.getNombreTienda());
        assertEquals(2, response.getPreventasAsociadas().size());
    }

    @Test
    public void testVincularPreventaATienda() {
        Long idTienda = 1L;
        Long idPreventa = 500L;

        Tienda tienda = new Tienda();
        tienda.setIdPreventa(new ArrayList<>());

        when(tiendaRepository.findById(idTienda)).thenReturn(Optional.of(tienda));
        when(tiendaRepository.save(any(Tienda.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Tienda result = tiendaService.vincularPreventaATienda(idTienda, idPreventa);

        assertNotNull(result);
        assertTrue(result.getIdPreventa().contains(idPreventa));
    }

    @Test
    public void testBuscarFiguraPorTienda_CasoExitoso() {
        Long idTienda = 1L;
        Long idFigura = 20L;

        Tienda tienda = new Tienda();
        tienda.setNombreTienda("Tienda Otaku");

        FiguraDTO figuraDto = new FiguraDTO();
        figuraDto.setNombreFigura("Evangelion EVA-01");

        when(tiendaRepository.findById(idTienda)).thenReturn(Optional.of(tienda));
        when(figuraClientRest.obtenerDatosFigura(idFigura)).thenReturn(figuraDto);

        ResponseFiguraDTO response = tiendaService.buscarFiguraPorTienda(idTienda, idFigura);

        assertNotNull(response);
        assertEquals("Evangelion EVA-01", response.getNombreFigura());
        assertEquals("Tienda Otaku", response.getNombreTienda());
    }

    @Test
    public void testVincularFiguraATienda_CasoExitoso() {
        Long idTienda = 1L;
        Long idFigura = 20L;

        Tienda tienda = new Tienda();
        FiguraDTO figuraDto = new FiguraDTO();
        figuraDto.setIdFigura(idFigura);

        when(tiendaRepository.findById(idTienda)).thenReturn(Optional.of(tienda));
        when(figuraClientRest.obtenerDatosFigura(idFigura)).thenReturn(figuraDto);
        when(tiendaRepository.save(any(Tienda.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Tienda result = tiendaService.vincularFiguraATienda(idTienda, idFigura);

        assertNotNull(result);
        assertEquals(idFigura, result.getIdFigura());
    }
}