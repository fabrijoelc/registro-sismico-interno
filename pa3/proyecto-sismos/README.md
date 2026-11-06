# SISMO · Registro interno de reportes

PA3 · Grupo 8 · NRC 3703 · ISIL · 2026-20.

La aplicación continúa la PA2 con Spring Boot, Spring Data JPA, MySQL, Thymeleaf y Bean Validation. Permite importar catálogos, registrar sismos y documentar daños asociados. Los datos de ejemplo se usan para una demostración académica; la aplicación no emite alertas oficiales.

## Empezar y ensayar

1. Instalar y abrir Docker Desktop con contenedores Linux.
2. Extraer el ZIP completo. Abrir una terminal en la carpeta que contiene `compose.yaml`.
3. Ejecutar:

```powershell
docker compose up -d --build
```

4. Abrir [la aplicación](http://localhost:18084/sismos-pa3/).
5. Entrar en **Importar catálogos** y cargar los tres archivos de `datos/`, seleccionando el catálogo correspondiente: `ubigeo.csv` (25), `tipo_alerta.csv` (4), `tipo_dano.csv` (7).
6. Registrar un sismo. Abrir su detalle y elegir **Registrar daño**.
7. Ensayar el recorrido de [la guía del grupo](docs/GUIA_EXPOSICION.md).

La primera compilación descarga dependencias y ejecuta las pruebas. Si todavía está iniciando, consultar `docker compose logs web --tail 60`. Los catálogos no se cargan automáticamente: su carga es parte de la demostración. Reimportar un archivo idéntico no duplica registros.

## Qué entregar

- El informe PA3, que documenta este incremento y los dos casos de uso con flujos alternos.
- El ZIP del proyecto completo, con código, CSV originales, esquema, pruebas, instrucciones y JAR ejecutable.
- Un video de 8 a 12 minutos con los cuatro integrantes. La guía escrita es una ayuda para ensayar y no sustituye el video.

No es necesario concatenar el informe PA2. El profesor aclaró el 30/10/2026 (1:50:01) que las 15 historias terminadas son acumuladas. Se conservan las 40 historias originales, sus identificadores y sus dos criterios; se añaden HU41-HU44 para cubrir los nuevos requisitos. El estado definitivo y sus evidencias se consultan en `docs/BACKLOG.md`.

Figma, PPT y login no aparecen como entregables o funcionalidades obligatorias en la guía PA3. El Administrador es el actor del proceso de carga; esta versión no implementa cuentas ni permisos. Las historias de cuentas del backlog siguen pendientes.

## Actualizar sin perder los datos

Si trabajas con Git, primero revisa `git status` y conserva tus cambios. Después:

```powershell
git pull --ff-only
docker compose up -d --build
```

Para detener y volver a abrir:

```powershell
docker compose stop
docker compose up -d
```

La PA3 usa su propio volumen `datos_sismos_pa3`, base `sismos_pa3`, puerto web 18084 y puerto MySQL 13309. La PA2 conserva su base y su aplicación independientes. Los reportes guardados en PA2 no se trasladan automáticamente; este incremento migra el código y carga los catálogos del curso. El JAR y Docker ejecutan la misma versión Spring Boot.

No ejecutar `docker compose down -v` para actualizar: elimina los datos de esta entrega. La clave incluida como valor de demostración es local y reemplazable mediante `.env`; no es una contraseña real del equipo. `.env` está excluido de Git. Si cambias la clave después de crear el volumen, también debes modificar el usuario de MySQL; editar `.env` por sí solo no cambia una clave existente.

## Alternativa sin Docker para la aplicación

Requiere Java 21, Maven 3.9 y MySQL 8.4. Crear `sismos_pa3` con UTF-8 y un usuario que pueda crear las tablas iniciales. El esquema también está en `database/001_schema.sql`.

```powershell
$env:DB_URL='jdbc:mysql://localhost:13309/sismos_pa3?allowPublicKeyRetrieval=true&useSSL=false&connectionTimeZone=America/Lima'
$env:DB_USER='sismos'
$env:DB_PASSWORD='TU_CLAVE_LOCAL'
mvn clean verify
java -jar target/sismos-pa3.jar
```

El JAR ya compilado se incluye en `bin/sismos-pa3.jar`. No necesita instalar Tomcat por separado. La configuración usa variables de entorno en `application.properties`. No copiar una contraseña personal al archivo ni al repositorio.

## Cómo se conectan los módulos

`Ubigeo → Sismo ← TipoAlerta` y `Sismo → ReporteDano ← TipoDano`.

Cada sismo selecciona un ubigeo y una alerta activos. Cada daño pertenece a un sismo y a un tipo de daño activo. Se conservan código, fecha, magnitud, profundidad, referencia, estado de revisión, canal y coordenadas de PA2. El catálogo Ubicacion evoluciona a Ubigeo según el CSV del curso: son 24 departamentos y la Provincia Constitucional del Callao, no todos los distritos del país.

La alerta es una clasificación interna seleccionada por el operador, no un cálculo automático de emergencia por magnitud. La cantidad afectada se interpreta según el tipo de daño y la descripción. No se suman viviendas, personas y establecimientos como si fueran una misma unidad.

## Arquitectura y lectura del código

- `web/`: controladores HTTP, formularios, BindingResult y vistas.
- `service/`: reglas de negocio, importación y transacciones.
- `repository/`: interfaces `JpaRepository` y consultas.
- `model/`: las cinco entidades y sus relaciones JPA.
- `dto/`: formularios con Bean Validation.
- `templates/`: vistas Thymeleaf; sin SQL ni reglas de negocio.
- `static/`: estilos locales; no requiere servicios de diseño externos.
- `db/schema.sql` y `database/001_schema.sql`: esquema relacional equivalente.
- `datos/`: los tres CSV originales recibidos, conservando códigos como `01`.
- `evidencias/`: resultados y capturas obtenidos al probar esta versión.

## Pruebas

```powershell
mvn verify
```

Las pruebas automáticas usan H2 solo para aislar reglas y persistencia durante la compilación. La aplicación real utiliza MySQL. `scripts/prueba-navegador.cjs` realiza el recorrido contra la aplicación local con Node.js, Chrome y Playwright. Consultar su cabecera antes de ejecutarlo: crea datos de demostración con códigos de prueba y conserva los maestros cargados. Los resultados concretos se incluyen en `evidencias/`.

## Material para la exposición

| Integrante | Parte | Tiempo orientativo |
| --- | --- | --- |
| Sebastian | Contexto, backlog, continuidad y pendientes | 0:00-2:00 |
| Adriano | Arquitectura Spring y carga de los tres CSV | 2:00-5:00 |
| Fabrizio | Registro y consulta de Sismo con ubigeo y alerta | 5:00-7:30 |
| Luis | Daño asociado, validaciones, persistencia y cierre | 7:30-11:00 |

Los responsables son de revisión y exposición; no se atribuyen reuniones, entrevistas o commits individuales que no consten. Confirmar la participación real antes de presentar. Más detalle: [casos de uso](docs/CASOS_DE_USO.md), [rúbrica](docs/RUBRICA.md) y [guion](docs/GUIA_EXPOSICION.md).
