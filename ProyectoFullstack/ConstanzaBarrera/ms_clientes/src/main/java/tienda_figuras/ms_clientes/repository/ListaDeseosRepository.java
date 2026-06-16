package tienda_figuras.ms_clientes.repository;

import tienda_figuras.ms_clientes.model.ListaDeseos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ListaDeseosRepository extends JpaRepository<ListaDeseos, Long>{

    Optional<ListaDeseos> findByIdListaDeseosAndIdFigura(Long idListaDeseos, Long idFigura);
    
}
