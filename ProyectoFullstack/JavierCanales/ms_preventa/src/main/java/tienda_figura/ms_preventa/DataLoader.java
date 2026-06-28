package tienda_figura.ms_preventa;

import tienda_figura.ms_preventa.model.*;
import tienda_figura.ms_preventa.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PreventaRepository preventaRepository;

    @Autowired
    private DetallePreventaRepository detallePreventaRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // 1. Generar e insertar Preventas principales
        String[] preventasFamosas = {"Preventa Exclusiva Anime Expo", "Preventa Preventiva Invierno", "Preventa Coleccionistas Marvel", "Preventa Especial Gaming"};
        for (String nombre : preventasFamosas) {
            Preventa preventa = new Preventa();
            preventa.setNombrePreventa(nombre);
            preventaRepository.save(preventa);
        }
        List<Preventa> listaPreventas = preventaRepository.findAll();

        // 2. Generar e insertar Detalles de Preventa asociados
        for (int i = 0; i < 30; i++) {
            DetallePreventa detalle = new DetallePreventa();
            
            // Asignamos un precio aleatorio
            detalle.setPrecioPreventa(faker.number().numberBetween(15000, 85000));
            
            // Configurar fechas utilizando java.util.Date
            Date fechaInicio = new Date(); // Fecha actual
            // La fecha de término será dentro de 15 días (1296000000L milisegundos)
            Date fechaTermino = new Date(System.currentTimeMillis() + 1296000000L);
            
            detalle.setFechaInicioPreventa(fechaInicio);
            detalle.setFechaTerminoPreventa(fechaTermino);
            
            // Asignación de la relación @ManyToOne hacia Preventa
            detalle.setPreventa(listaPreventas.get(random.nextInt(listaPreventas.size())));

            detallePreventaRepository.save(detalle);
        }
    }
}