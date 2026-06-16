USE preventa;

-- Tabla Preventa

INSERT INTO preventa (nombre_preventa) 
VALUES ('Venta de Verano 2024');

INSERT INTO preventa (nombre_preventa) 
VALUES ('Lanzamiento Nuevo Producto');

-- Tabla DetallePreventa

INSERT INTO detallepreventa (precio_preventa, fecha_inicio_preventa, fecha_termino_preventa, id_preventa) 
VALUES (1500, '2024-06-01', '2024-06-15', 1);

INSERT INTO detallepreventa (precio_preventa, fecha_inicio_preventa, fecha_termino_preventa, id_preventa) 
VALUES (2000, '2024-06-01', '2024-06-20', 1);

INSERT INTO detallepreventa (precio_preventa, fecha_inicio_preventa, fecha_termino_preventa, id_preventa) 
VALUES (5000, '2024-07-01', '2024-07-10', 2);

SELECT * FROM preventa;

