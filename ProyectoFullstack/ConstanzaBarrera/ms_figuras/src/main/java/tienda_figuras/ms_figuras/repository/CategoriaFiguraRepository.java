package tienda_figuras.ms_figuras.repository;

import tienda_figuras.ms_figuras.model.CategoriaFigura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CategoriaFiguraRepository extends JpaRepository<CategoriaFigura, Long>{

}