package tienda_figura.ms_tienda_empresa;

import tienda_figura.ms_tienda_empresa.model.*;
import tienda_figura.ms_tienda_empresa.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Profile("test")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // 1. Generar e insertar Empresas
        for (int i = 0; i < 5; i++) {
            Empresa empresa = new Empresa();
            // Identificación Tributaria (ej: RUT o similar número entero)
            empresa.setIdentificacionTributaria(faker.number().numberBetween(10000000, 99999999));
            empresa.setNombreEmpresa(faker.company().name());
            empresa.setCorreoEmpresa(faker.internet().emailAddress());
            empresa.setTelefonoEmpresa(faker.number().numberBetween(900000000, 999999999));
            
            empresaRepository.save(empresa);
        }
        List<Empresa> listaEmpresas = empresaRepository.findAll();

        // 2. Generar e insertar Tiendas
        for (int i = 0; i < 15; i++) {
            Tienda tienda = new Tienda();
            tienda.setNombreTienda(faker.commerce().department() + " Figures");
            tienda.setDireccionTienda(faker.address().fullAddress());
            tienda.setTelefonoTienda(faker.number().numberBetween(220000000, 229999999));
            
            // Asignación de la relación @ManyToOne hacia Empresa
            tienda.setEmpresa(listaEmpresas.get(random.nextInt(listaEmpresas.size())));
            
            // Llenar la @ElementCollection idPreventa (Lista de Longs)
            List<Long> idsPreventasFalsas = new ArrayList<>();
            int cantidadPreventas = random.nextInt(3) + i; // Agrega entre 1 y 3 IDs ficticios
            for (int p = 0; p < cantidadPreventas; p++) {
                idsPreventasFalsas.add((long) faker.number().numberBetween(1, 10));
            }
            tienda.setIdPreventa(idsPreventasFalsas);
            
            // Asignar una ID de figura externa referencial
            tienda.setIdFigura((long) faker.number().numberBetween(1, 50));

            tiendaRepository.save(tienda);
        }
    }
}