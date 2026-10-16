# Registro interno de sismos | PA2

Grupo 8 · NRC 3703 · ISIL · 2026-20.

Evolución de PA1: misma aplicación Java Web MVC, ahora con **JPA/Hibernate, MySQL y catálogo de ubicaciones**. No usa Spring ni DAO JDBC como capa principal.

## Empieza aquí si vas a ensayar

1. Lee [la guía de exposición](docs/GUIA_EXPOSICION.md): incluye qué mostrar, qué decir y los datos de ejemplo.
2. Revisa [el backlog](docs/BACKLOG.md): 40 historias, 11 implementadas y 29 pendientes.
3. Arranca la aplicación siguiendo la sección de Docker de abajo.
4. Ejecuta tu parte una vez antes de grabar. Los botones de Figma no sustituyen esta demostración.

### Qué presentar en PA2

**Informe PA2 + ZIP del proyecto completo + video nuevo de 8 a 12 minutos.** Todos deben intervenir.

PA2 continúa el mismo proyecto, pero la guía no solicita concatenar el informe de PA1: no se suman sus páginas. El informe PA2 explica la evolución, conserva el backlog y documenta el incremento. El código sí incluye las funciones anteriores más las nuevas.

Figma y un PPT adicional no figuran como entregables obligatorios en la guía PA2. Durante la exposición se muestran el **PDF, el IDE y la aplicación real**. Si el docente publica otra indicación posterior, debe atenderse.

- [Informe PDF](https://github.com/fabrijoelc/registro-sismico-interno/blob/main/entregables/PA2_Registro_Interno_de_Sismos_NRC_3703.pdf)
- [ZIP listo para descargar](https://github.com/fabrijoelc/registro-sismico-interno/raw/refs/heads/main/entregables/PA2_Proyecto_Sismos_NRC_3703.zip)
- [Guion por integrante](docs/GUIA_EXPOSICION.md)
- [Cambios de PA1 a PA2](docs/CAMBIOS_PA2.md)

| Integrante | Parte de la exposición | Tiempo orientativo |
| --- | --- | --- |
| Sebastian | Objetivo, backlog, responsables y pendientes | 0:00-2:00 |
| Adriano | Arquitectura MVC, entidades, relación y transacciones | 2:00-4:30 |
| Fabrizio | CRUD de Ubicación y relación con Sismo | 4:30-7:00 |
| Luis | CRUD de Sismo, validaciones, persistencia y cierre | 7:00-11:00 |

### Obtener la versión actual

Actualización de interfaz (16/10/2026): logo de un solo color, textos más claros y sin etiquetas académicas o de tecnologías en las pantallas. El CRUD y las reglas de validación no cambian. El ZIP incluye esta interfaz y el WAR recompilado.

Si ya tenías la aplicación abierta, después de actualizar el código ejecuta `docker compose up -d --build` y recarga el navegador. No borres el volumen de MySQL: tus datos se conservan.

Si todavía no tienes el repositorio:

~~~powershell
git clone https://github.com/fabrijoelc/registro-sismico-interno.git
cd registro-sismico-interno
~~~

Si ya lo clonaste, entra en esa carpeta y revisa primero:

~~~powershell
git status
git pull --ff-only
~~~

Si tienes cambios locales o Git avisa de un conflicto, consérvalos y coordina antes de continuar; no uses un reset para descartarlos. La versión PA1 permanece en el historial Git.

## Arranque recomendado (Docker Desktop)

1. Extraer todo el ZIP y abrir una terminal en la carpeta de este README.
2. Tener Docker Desktop iniciado (contenedores Linux).
3. Ejecutar:

~~~powershell
docker compose up -d --build
~~~

4. La primera ejecución descarga imágenes/dependencias. Al terminar, esperar unos segundos a Tomcat y abrir **http://localhost:18083/sismos-pa2/**.
5. Para revisar el arranque: docker compose logs web --tail 60

No hace falta instalar Java, Maven o MySQL localmente con esta opción: Docker compila el código, ejecuta las pruebas y despliega el WAR. Los puertos 18083 y 13308 están limitados a localhost.

Las claves de compose son **demostrativas y solo para uso local**. Para cambiarlas ANTES de crear la base, copiar .env.example a .env y editarlo. Cambiarlas después NO modifica las claves de un volumen ya inicializado.

### Detener y volver a abrir sin perder datos

~~~powershell
docker compose stop
docker compose up -d
~~~

Para reiniciar ambos servicios de forma ordenada:

~~~powershell
docker compose stop web
docker compose restart db
docker compose up -d --wait db
docker compose start web
~~~

No iniciar Tomcat antes de que MySQL esté listo. No utilizar **docker compose down -v** si se desean conservar los datos: elimina el volumen de esta aplicación. Los scripts SQL solo se ejecutan automáticamente al inicializar un volumen vacío.

## Alternativa: Java 21 + Maven 3.9 + Tomcat 11 + MySQL 8.4

1. Ejecutar database/01-schema.sql en MySQL. En una base nueva y vacía, cargar database/02-demo.sql para datos de muestra.
2. Crear un usuario exclusivo con SELECT, INSERT, UPDATE y DELETE sobre sismos_pa2.*. No usar root como usuario de aplicación.
3. Configurar estas variables en el proceso que inicia Tomcat:

~~~powershell
$env:DB_URL='jdbc:mysql://localhost:3306/sismos_pa2?connectionTimeZone=America/Lima'
$env:DB_USER='sismos'
$env:DB_PASSWORD='TU_CLAVE_LOCAL'
~~~

4. Ejecutar **mvn clean verify**.
5. Copiar target/sismos-pa2.war a webapps de Tomcat 11 e iniciarlo desde la terminal con esas variables.
6. Abrir http://localhost:8080/sismos-pa2/ (o el puerto configurado).

También se incluye war/sismos-pa2.war ya compilado. Tomcat 9 no es compatible con los paquetes Jakarta del proyecto. Para conectar Tomcat local a MySQL Docker, usar la URL de compose cambiando db:3306 por localhost:13308.

## Alcance verificable

| Historias | Entrega y estado |
| --- | --- |
| HU01-HU08 | Funciones PA1 migradas a persistencia JPA |
| HU09 | Canal: llamada, correo o mensaje |
| HU10 | Región desde catálogo Ubicacion, con CRUD completo |
| HU39 | Coordenadas con rangos y consulta en detalle |
| Otras 29 | Pendientes, no se presentan como implementadas |

Se conserva la referencia textual de ubicación. Una clave foránea relaciona cada Sismo con una Ubicacion.

### Reglas y decisiones

- Código único global, incluso entre inactivos. PA2 refina la regla PA1 para no reutilizar códigos históricos.
- Fecha desde 1900 y no futura, interpretada en hora de Perú.
- Magnitud 0-10 y profundidad 0-1000 km: reglas académicas de PA1; hasta dos decimales.
- Coordenadas opcionales pero en pareja: latitud -90 a 90, longitud -180 a 180; hasta seis decimales. Se valida formato/rango, no el evento en una fuente oficial.
- Solo se permite asociar ubicaciones activas.
- Bajas lógicas de ambas entidades: activo=false. No se borran filas históricas.
- Se bloquea la baja de una ubicación con reportes activos. Reasignar o dar de baja esos reportes primero.
- HTML escapado con JSTL, UTF-8 y token CSRF en POST.
- Sin login ni autorización por roles en esta entrega; los actores son roles del análisis.
- Datos sintéticos: no son alertas oficiales ni predicciones.

## Arquitectura

- model/Sismo.java, model/Ubicacion.java: entidades JPA y ManyToOne.
- repository/JpaRepository.java: EntityManager por operación, begin/commit/rollback y cierre.
- repository/SismoRepository.java: persist/find/merge, JPQL parametrizado y baja lógica.
- repository/UbicacionRepository.java: CRUD, unicidad y protección de referencias activas.
- listener/AplicacionListener.java: fábrica JPA compartida y configuración por entorno.
- servlet/: rutas; SismoForm valida; SeguridadFilter maneja UTF-8/CSRF.
- WEB-INF/views/: JSP/JSTL, sin SQL en las vistas.
- META-INF/persistence.xml: RESOURCE_LOCAL y validación del esquema; no recrea tablas.
- database/: SQL y tres registros sintéticos iniciales.
- docs/GUIA_EXPOSICION.md: reparto y guion de 11 minutos.
- evidencias/: capturas y resultados reproducibles.

## Pruebas

**mvn clean verify** ejecuta 23 pruebas: 12 de repositorios/JPA con H2 y 11 de formulario. H2 es solo para tests; la aplicación utiliza MySQL.

scripts/prueba-navegador.cjs ejecuta un flujo real HTTP/navegador. Requiere Node.js, Chrome y Playwright:

~~~powershell
npm install --no-save playwright
$env:REINICIAR='1'
node scripts/prueba-navegador.cjs
~~~

Esta prueba crea registros QA, realiza bajas lógicas y, con REINICIAR=1, reinicia solo los servicios de este compose. Usar únicamente en demostración local.

## Límites y entrega

Sin estaciones, mapas, adjuntos, cuentas, auditoría completa o estadísticas. Ubicaciones y estaciones son sugerencias de PA2; se selecciona Ubicacion por HU10. Figma no es necesario para demostrar persistencia: mostrar la aplicación real.

Entregar informe PDF, ZIP y **video nuevo de 8-12 minutos con los cuatro integrantes**. La guía no sustituye la participación real. Revisar porcentajes declarados y ensayar en el equipo de grabación.
