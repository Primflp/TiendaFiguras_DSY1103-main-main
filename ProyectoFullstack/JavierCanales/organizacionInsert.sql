USE organizacion;

-- Tabla Empresa

INSERT INTO empresa (identificacion_tributaria, nombre_empresa, correo_empresa, telefono_empresa)
VALUES (12345678, 'Corporación Alfa S.A.', 'contacto@alfa.com', 5550199);

INSERT INTO empresa (identificacion_tributaria, nombre_empresa, correo_empresa, telefono_empresa)
VALUES (87654321, 'Inversiones Beta Ltda.', 'info@beta.com', 5550288);

-- Tabla Tienda

INSERT INTO tienda (nombre_tienda, direccion_tienda, telefono_tienda, id_empresa)
VALUES ('Tienda Alfa - Centro', 'Av. Principal 123, Santiago', 9991111, 1);

INSERT INTO tienda (nombre_tienda, direccion_tienda, telefono_tienda, id_empresa)
VALUES ('Tienda Alfa - Norte', 'Calle del Sol 456, Antofagasta', 9992222, 1);

INSERT INTO tienda (nombre_tienda, direccion_tienda, telefono_tienda, id_empresa)
VALUES ('Megatienda Beta', 'Mall Plaza Local 20, Concepción', 8883333, 2);

INSERT INTO tienda (nombre_tienda, direccion_tienda, telefono_tienda, id_empresa)
VALUES ('Beta Express', 'Paseo Peatonal 789, Valparaíso', 8884444, 2);

SELECT * FROM tienda;

