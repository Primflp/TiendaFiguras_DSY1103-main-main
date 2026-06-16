package tienda_figuras.ms_clientes.repository;

import tienda_figuras.ms_clientes.model.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c WHERE c.correoCliente = :correo AND c.contrasenaCliente = :contrasena") 
    List<Cliente> autenticarCuenta (String correo, String contrasena);
    //metodo custom 1
    
    @Query("SELECT c FROM Cliente c WHERE c.runCliente LIKE :run") 
    List<Cliente> buscarPorRun (String run);
    //metodo custom 2

    @Query("SELECT c FROM Cliente c WHERE c.direccionCliente LIKE :direccion") 
    List<Cliente> buscarPorDireccion (String direccion);
    //metodo custom 2
    
}