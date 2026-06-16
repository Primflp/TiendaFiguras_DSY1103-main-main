package tienda_figura.ms_tienda_empresa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tienda_figura.ms_tienda_empresa.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>{

    @Query("SELECT e FROM Empresa e WHERE e.identificacionTributaria = :numTributario")
    Empresa empresaFindByIdTributaria(int numTributario);

}
