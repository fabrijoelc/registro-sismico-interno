CREATE TABLE IF NOT EXISTS ubigeo (
 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 codigo VARCHAR(20) NOT NULL UNIQUE,
 nombre VARCHAR(100) NOT NULL,
 tipo VARCHAR(30) NOT NULL,
 estado VARCHAR(10) NOT NULL,
 CONSTRAINT chk_ubigeo_estado CHECK (estado IN ('ACTIVO','INACTIVO')),
 CONSTRAINT chk_ubigeo_tipo CHECK (tipo IN ('DEPARTAMENTO','PROVINCIA_CONSTITUCIONAL'))
);
CREATE TABLE IF NOT EXISTS tipo_alerta (
 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 codigo VARCHAR(20) NOT NULL UNIQUE,
 nombre VARCHAR(100) NOT NULL,
 color VARCHAR(10) NOT NULL,
 prioridad INT NOT NULL,
 descripcion VARCHAR(500) NOT NULL,
 estado VARCHAR(10) NOT NULL,
 CONSTRAINT chk_alerta_prioridad CHECK (prioridad > 0),
 CONSTRAINT chk_alerta_color CHECK (color IN ('VERDE','AMARILLO','NARANJA','ROJO')),
 CONSTRAINT chk_alerta_estado CHECK (estado IN ('ACTIVO','INACTIVO'))
);
CREATE TABLE IF NOT EXISTS tipo_dano (
 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 codigo VARCHAR(20) NOT NULL UNIQUE,
 nombre VARCHAR(100) NOT NULL,
 sector VARCHAR(30) NOT NULL,
 descripcion VARCHAR(500) NOT NULL,
 estado VARCHAR(10) NOT NULL,
 CONSTRAINT chk_dano_sector CHECK (sector IN ('SALUD','VIVIENDA','EDUCACION','INFRAESTRUCTURA','SERVICIOS_BASICOS','AGROPECUARIO','POBLACION')),
 CONSTRAINT chk_dano_estado CHECK (estado IN ('ACTIVO','INACTIVO'))
);
CREATE TABLE IF NOT EXISTS sismo (
 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 codigo VARCHAR(30) NOT NULL UNIQUE,
 fecha_hora DATETIME(6) NOT NULL,
 magnitud DECIMAL(4,2) NOT NULL,
 profundidad_km DECIMAL(6,2) NOT NULL,
 referencia VARCHAR(200) NOT NULL,
 ubigeo_id BIGINT NOT NULL,
 tipo_alerta_id BIGINT NOT NULL,
 canal VARCHAR(15) NOT NULL,
 estado VARCHAR(20) NOT NULL,
 latitud DECIMAL(9,6),
 longitud DECIMAL(9,6),
 observaciones VARCHAR(1000),
 activo BOOLEAN NOT NULL DEFAULT TRUE,
 CONSTRAINT fk_sismo_ubigeo FOREIGN KEY (ubigeo_id) REFERENCES ubigeo(id),
 CONSTRAINT fk_sismo_alerta FOREIGN KEY (tipo_alerta_id) REFERENCES tipo_alerta(id),
 CONSTRAINT chk_sismo_magnitud CHECK (magnitud >= 0 AND magnitud <= 10),
 CONSTRAINT chk_sismo_profundidad CHECK (profundidad_km >= 0 AND profundidad_km <= 1000),
 CONSTRAINT chk_sismo_latitud CHECK (latitud IS NULL OR (latitud >= -90 AND latitud <= 90)),
 CONSTRAINT chk_sismo_longitud CHECK (longitud IS NULL OR (longitud >= -180 AND longitud <= 180)),
 CONSTRAINT chk_sismo_coordenadas CHECK ((latitud IS NULL AND longitud IS NULL) OR (latitud IS NOT NULL AND longitud IS NOT NULL)),
 CONSTRAINT chk_sismo_canal CHECK (canal IN ('LLAMADA','CORREO','MENSAJE')),
 CONSTRAINT chk_sismo_estado CHECK (estado IN ('PENDIENTE','EN_VERIFICACION','VERIFICADO','DESCARTADO'))
);
CREATE TABLE IF NOT EXISTS reporte_dano (
 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 sismo_id BIGINT NOT NULL,
 tipo_dano_id BIGINT NOT NULL,
 cantidad_afectada INT NOT NULL,
 descripcion VARCHAR(1000) NOT NULL,
 fecha_registro DATETIME(6) NOT NULL,
 CONSTRAINT fk_reporte_sismo FOREIGN KEY (sismo_id) REFERENCES sismo(id),
 CONSTRAINT fk_reporte_tipo FOREIGN KEY (tipo_dano_id) REFERENCES tipo_dano(id),
 CONSTRAINT chk_reporte_cantidad CHECK (cantidad_afectada > 0)
);
