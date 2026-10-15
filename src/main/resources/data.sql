-- Datos iniciales (se cargan cada vez que inicia la aplicacion).
-- La estadia 1 (ABC-123 en Playa Centro, 3 horas) ya esta registrada: por eso Playa Centro tiene 4 de sus 5 espacios libres.
INSERT INTO vehicles (id, plate, type, member) VALUES
(1, 'ABC-123', 'AUTO', false),
(2, 'XYZ-789', 'CAMIONETA', false),
(3, 'MOT-456', 'MOTO', true);

INSERT INTO parking_lots (id, name, hourly_rate, available_spaces) VALUES
(1, 'Playa Centro', 5.00, 4),
(2, 'Playa Norte', 4.00, 0);

INSERT INTO stays (vehicle_id, lot_id, hours, charge) VALUES
(1, 1, 3, 15.00);
