package tienda_figura.ms_tienda_empresa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda_figura.ms_tienda_empresa.model.Empresa;
import tienda_figura.ms_tienda_empresa.repository.EmpresaRepository;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;
    
    public List<Empresa> empresaFindAll(){
        return empresaRepository.findAll();
    }

    public Empresa empresaFindById(Long id){
        return empresaRepository.findById(id).orElse(null);
    }

    public Empresa empresaSave(Empresa empresa){
        return empresaRepository.save(empresa);
    }

    public void empresaDelete(Long id){
        empresaRepository.deleteById(id);
    }

    // custom

    public Empresa empresaFindByIdTributaria(int numTributario){
        return empresaRepository.empresaFindByIdTributaria(numTributario);
    }

    public Empresa empresaUpdate(Long id, Empresa empresaUpdate){

        if(empresaRepository.existsById(id)){

            Empresa empresa = empresaRepository.findById(id).orElse(null);

            empresa.setIdentificacionTributaria(empresaUpdate.getIdentificacionTributaria());
            empresa.setNombreEmpresa(empresaUpdate.getNombreEmpresa());
            empresa.setCorreoEmpresa(empresaUpdate.getCorreoEmpresa());
            empresa.setTelefonoEmpresa(empresaUpdate.getTelefonoEmpresa());

            return empresaRepository.save(empresa);

        }
        return null;

    }
}
