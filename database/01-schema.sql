-- PA2 | MySQL 8.4 | Solo crea objetos si no existen. Datos de muestra en 02-demo.sql.
CREATE DATABASE IF NOT EXISTS sismos_pa2 CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE sismos_pa2;
CREATE TABLE IF NOT EXISTS ubicacion (
 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 codigo VARCHAR(20) NOT NULL,
 departamento VARCHAR(80) NOT NULL,
 provincia VARCHAR(80) NOT NULL,
 distrito VARCHAR(80) NOT NULL,
 activo BIT(1) NOT NULL DEFAULT b'1',
 CONSTRAINT uk_ubicacion_codigo UNIQUE(codigo)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS sismo (
 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 codigo VARCHAR(40) NOT NULL,
 fecha_hora DATETIME(6) NOT NULL,
 referencia VARCHAR(120) NOT NULL,
 magnitud DECIMAL(4,2) NOT NULL,
 profundidad_km DECIMAL(7,2) NOT NULL,
 estado VARCHAR(20) NOT NULL,
 observaciones VARCHAR(500),
 activo BIT(1) NOT NULL DEFAULT b'1',
 ubicacion_id BIGINT NOT NULL,
 canal VARCHAR(20) NOT NULL,
 latitud DECIMAL(9,6),
 longitud DECIMAL(9,6),
 CONSTRAINT uk_sismo_codigo UNIQUE(codigo),
 CONSTRAINT fk_sismo_ubicacion FOREIGN KEY(ubicacion_id) REFERENCES ubicacion(id),
 CONSTRAINT ck_magnitud CHECK(magnitud BETWEEN 0 AND 10),
 CONSTRAINT ck_profundidad CHECK(profundidad_km BETWEEN 0 AND 1000),
 CONSTRAINT ck_estado CHECK(estado IN ('PENDIENTE','EN_VERIFICACION','VERIFICADO','DESCARTADO')),
 CONSTRAINT ck_canal CHECK(canal IN ('LLAMADA','CORREO','MENSAJE')),
 CONSTRAINT ck_coordenadas CHECK(
   (latitud IS NULL AND longitud IS NULL) OR
   (latitud IS NOT NULL AND longitud IS NOT NULL AND latitud BETWEEN -90 AND 90 AND longitud BETWEEN -180 AND 180)),
 INDEX ix_sismo_activo_estado(activo,estado),
 INDEX ix_sismo_ubicacion(ubicacion_id)
) ENGINE=InnoDB;
