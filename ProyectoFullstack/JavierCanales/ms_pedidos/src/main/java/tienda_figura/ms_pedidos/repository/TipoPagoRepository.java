package tienda_figura.ms_pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tienda_figura.ms_pedidos.model.TipoPago;

@Repository
public interface TipoPagoRepository extends JpaRepository<TipoPago, Long>{

}
