USE pedidos;

-- Tabla TipoPago

INSERT INTO tipopago (nombre_tipo_pago) VALUES ('Tarjeta de Crédito');
INSERT INTO tipopago (nombre_tipo_pago) VALUES ('Transferencia Bancaria');
INSERT INTO tipopago (nombre_tipo_pago) VALUES ('Efectivo');

-- Tabla Pedido

INSERT INTO pedido (estado_pedido, direccion_envio, fecha_entrega, fecha_emision, id_tipo_pago) 
VALUES ('PENDIENTE', 'Calle Principal 123, Santiago', '2026-12-01', CURDATE(), 1);

INSERT INTO pedido (estado_pedido, direccion_envio, fecha_entrega, fecha_emision, id_tipo_pago) 
VALUES ('ENTREGADO', 'Avenida Libertad 456, Valparaíso', '2026-11-20', '2026-11-15', 2);

-- Tabla DetallePedido

INSERT INTO detalle_pedido (cantidad, precio_unitario, id_pedido, monto_total) 
VALUES (2, 5000, 1, 0);

INSERT INTO detalle_pedido (cantidad, precio_unitario, id_pedido, monto_total) 
VALUES (1, 15000, 1, 0);

INSERT INTO detalle_pedido (cantidad, precio_unitario, id_pedido, monto_total) 
VALUES (3, 2000, 2, 0);

SELECT * FROM pedido;

