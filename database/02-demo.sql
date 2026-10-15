-- DATOS SINTÉTICOS: no corresponden a alertas o reportes oficiales.
SET NAMES utf8mb4;
USE sismos_pa2;
INSERT INTO ubicacion(codigo,departamento,provincia,distrito) VALUES
('UB-LIM-001','Lima','Lima','Lima'),
('UB-ARE-001','Arequipa','Camaná','Camaná'),
('UB-PIU-001','Piura','Paita','Paita');
INSERT INTO sismo(codigo,fecha_hora,referencia,magnitud,profundidad_km,estado,observaciones,ubicacion_id,canal,latitud,longitud) VALUES
('SIS-2026-001','2026-09-18 09:20:00','Referencia de demostración - Lima',4.20,38.00,'PENDIENTE','Dato sintético para fines académicos.',1,'LLAMADA',NULL,NULL),
('SIS-2026-002','2026-09-19 14:05:00','Referencia de demostración - Camaná',5.10,62.50,'EN_VERIFICACION','Dato sintético; ubicación pendiente de revisión.',2,'CORREO',NULL,NULL),
('SIS-2026-003','2026-09-20 18:40:00','Referencia de demostración - Paita',3.70,22.00,'VERIFICADO','Dato sintético; coordenadas de ejemplo.',3,'MENSAJE',-5.089170,-81.114440);
