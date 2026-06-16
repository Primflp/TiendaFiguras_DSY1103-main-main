package tienda_figuras.ms_clientes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda_figuras.ms_clientes.model.Cliente;
import tienda_figuras.ms_clientes.repository.ClienteRepository;

import java.util.List;

@Service

public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Read All

    public List<Cliente> clienteBuscarTodo (){
        return clienteRepository.findAll();
    }

    // 1. Create

    public Cliente clienteGuardar (Cliente cliente){
        return clienteRepository.save(cliente);
    }

    // 2. Read 

    public Cliente clienteBuscarPorId (Long id){
        return clienteRepository.findById(id).orElse(null);
    }

    // 3. Update

    public Cliente clienteActualizar (Long id, Cliente actualizarCliente){
    
        if (clienteRepository.existsById(id)) {
            
            Cliente clienteExistente = clienteRepository.findById(id).orElse(null);
                
                clienteExistente.setNombresCliente(actualizarCliente.getNombresCliente());
                clienteExistente.setApPaternoCliente(actualizarCliente.getApPaternoCliente());
                clienteExistente.setApMaternoCliente(actualizarCliente.getApMaternoCliente());
                clienteExistente.setDireccionCliente(actualizarCliente.getDireccionCliente());
                clienteExistente.setNumeroCliente(actualizarCliente.getNumeroCliente());
                clienteExistente.setCorreoCliente(actualizarCliente.getCorreoCliente());
                clienteExistente.setContrasenaCliente(actualizarCliente.getContrasenaCliente());

            return clienteRepository.save(clienteExistente);
        }   
        return null;
    }

    // 4. Delete

    public void clienteEliminar (Long id){
        clienteRepository.deleteById(id);
    }

    // Metodo Custom 1

    public List<Cliente> clienteAutenticarCuenta (String correo, String contrasena){
        return clienteRepository.autenticarCuenta(correo, contrasena);
    }

    // Metodo Custom 2
    
    public List<Cliente> clienteBuscarPorRun (String run){
        return clienteRepository.buscarPorRun(run);
    }

    // Metodo Custom 3

    public List<Cliente> clienteBuscarPorDireccion (String direccion){
        return clienteRepository.buscarPorDireccion(direccion);
    }

}
