package tienda_figura.ms_tienda_empresa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tienda_figura.ms_tienda_empresa.model.Tienda;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Long>{

    Optional<Tienda> findByIdTiendaAndIdPreventa(Long idTienda, Long idPreventa);

    Optional<Tienda> findByIdTiendaAndIdFigura(Long idTienda, Long idFigura);

    @Query("SELECT t FROM Tienda t WHERE t.direccionTienda LIKE %:direccion%")
    Tienda tiendaFindByDireccion(String direccion);

    @Query("SELECT t FROM Tienda t WHERE t.nombreTienda LIKE %:nombre%")
    Tienda tiendaFindByNombre(String nombre);

}
