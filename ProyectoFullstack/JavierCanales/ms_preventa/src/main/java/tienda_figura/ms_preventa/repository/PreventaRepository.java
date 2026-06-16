package tienda_figura.ms_preventa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tienda_figura.ms_preventa.model.Preventa;

@Repository
public interface PreventaRepository extends JpaRepository<Preventa, Long>{

}
