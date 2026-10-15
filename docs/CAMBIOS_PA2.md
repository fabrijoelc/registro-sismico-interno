# Evolución PA1 → PA2

PA2 parte de la misma aplicación. No son dos sistemas separados ni dos informes que deban pegarse.

| Aspecto | PA1 | PA2 |
| --- | --- | --- |
| Persistencia | Map en memoria | MySQL con JPA/Hibernate |
| Identificador | Secuencia en memoria | IDENTITY / AUTO_INCREMENT |
| Modelo | Sismo | Sismo y Ubicacion, relacionados por clave foránea |
| Historias | HU01 a HU08 | HU01 a HU10 y HU39: 11 acumuladas |
| Bajas | Inactivo en memoria | Inactivo persistido en base |
| Ubicación | Texto libre | Referencia textual + catálogo geográfico |
| Nuevos datos | — | Canal de recepción y coordenadas |
| Pruebas automatizadas | 3 de repositorio en memoria | 23 JUnit + 16 comprobaciones web/HTTP |

## Decisiones para explicar

- Se conserva Servlets + JSP/JSTL + Maven + MVC; no se migra a Spring.
- El catálogo tiene CRUD completo y no admite baja si conserva sismos activos.
- El código de negocio ahora es único también entre registros inactivos. No reutilizar códigos de ensayos anteriores.
- Los rangos de magnitud y profundidad siguen siendo reglas académicas de PA1.
- Las coordenadas se validan en formato y rango; no se verifica automáticamente un sismo real.
- El listener inicializa la fábrica JPA, no vuelve a cargar los datos cada vez que arranca.
- No se implementan todavía login, roles, estaciones, adjuntos o auditoría. El README y backlog los distinguen.

## Qué archivos usar

El PDF de PA2 es el informe actualizado. El ZIP incluye toda la aplicación vigente, no solo un parche. El video de PA1 no reemplaza al video nuevo de PA2.

El historial de PA1 se conserva en los commits anteriores. El código actualizado sustituye la versión en memoria en la rama principal.
