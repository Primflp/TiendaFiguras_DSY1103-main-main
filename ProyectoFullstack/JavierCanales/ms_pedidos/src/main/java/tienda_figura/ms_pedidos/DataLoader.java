package tienda_figura.ms_pedidos;

import java.util.Date;
import java.util.List;
import java.util.Random;

import tienda_figura.ms_pedidos.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import net.datafaker.Faker;
import tienda_figura.ms_pedidos.repository.DetallePedidoRepository;
import tienda_figura.ms_pedidos.repository.PedidoRepository;
import tienda_figura.ms_pedidos.repository.TipoPagoRepository;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // 1. Generar e insertar Tipos de Pago ficticios
        String[] nombresPago = {"Efectivo", "Tarjeta de Crédito", "Tarjeta de Débito", "Transferencia Bancaria"};
        for (String nombre : nombresPago) {
            TipoPago tipoPago = new TipoPago();
            tipoPago.setNombreTipoPago(nombre);
            tipoPagoRepository.save(tipoPago);
        }
        List<TipoPago> listaTiposPago = tipoPagoRepository.findAll();

        // 2. Generar e insertar Pedidos
        for (int i = 0; i < 20; i++) {
            Pedido pedido = new Pedido();
            pedido.setEstadoPedido(faker.options().option("Pendiente", "Procesando", "Enviado", "Entregado"));
            pedido.setDireccionEnvio(faker.address().fullAddress());
            
            pedido.setFechaEmision(new Date());

            pedido.setFechaEntrega(new Date(System.currentTimeMillis() + 345600000L));

            pedido.setTipoPago(listaTiposPago.get(random.nextInt(listaTiposPago.size())));
            
            pedido.setIdCliente((long) faker.number().numberBetween(1, 100));

            pedidoRepository.save(pedido);
        }
        List<Pedido> listaPedidos = pedidoRepository.findAll();

        // 3. Generar e insertar Detalles de Pedido asociados
        for (int i = 0; i < 50; i++) {
            DetallePedido detalle = new DetallePedido();
            
            int cantidad = faker.number().numberBetween(1, 5);
            int precioUnitario = faker.number().numberBetween(3000, 25000);
            
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(precioUnitario);
            
            detalle.setMontoTotal((double) (cantidad * precioUnitario) * 1.19);
            
            detalle.setPedido(listaPedidos.get(random.nextInt(listaPedidos.size())));
            
            detalle.setIdFigura((long) faker.number().numberBetween(1, 50));

            detallePedidoRepository.save(detalle);
        }
    }
}