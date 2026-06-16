package tienda_figura.ms_pedidos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tienda_figura.ms_pedidos.model.EstadoPedidoDTO;
import tienda_figura.ms_pedidos.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

    Optional<Pedido> findByIdPedidoAndIdCliente(Long idPedido, Long idCliente);

    @Query("SELECT p.estadoPedido AS estadoPedido, p.direccionEnvio AS direccionEnvio, p.fechaEntrega AS fechaEntrega FROM Pedido p WHERE p.idPedido = :id")
    EstadoPedidoDTO verEstadoPedido(Long id);

}
