package tienda_figura.ms_preventa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tienda_figura.ms_preventa.model.DetallePreventa;

@Repository
public interface DetallePreventaRepository extends JpaRepository<DetallePreventa, Long>{

    // Buscar por mes de lanzamiento de preventa
    @Query("SELECT dpr FROM DetallePreventa dpr WHERE EXTRACT(MONTH FROM dpr.fechaInicioPreventa) = :mes")
    List<DetallePreventa> detallePreventaFindByMonth(int mes);

    // Buscar preventa por filtrado de un rango de precio
    @Query("SELECT dpr FROM DetallePreventa dpr WHERE dpr.precioPreventa BETWEEN :desde AND :hasta")
    List<DetallePreventa> detallePreventaFiltrarPrecio(int desde, int hasta);

}
