package tienda_figura.ms_pedidos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tienda_figura.ms_pedidos.model.DetallePedido;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long>{

    Optional<DetallePedido> findByIdDetallePedidoAndIdFigura(Long idDetallePedido, Long idFigura);

    @Query("SELECT dp FROM DetallePedido dp WHERE dp.precioUnitario BETWEEN :desde AND :hasta")
    List<DetallePedido> detallePedidoFiltrarPrecio(int desde, int hasta);

}
