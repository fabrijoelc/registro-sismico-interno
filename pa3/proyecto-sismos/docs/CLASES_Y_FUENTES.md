# Clases y fuentes de consulta de la PA3

Este documento registra las indicaciones verificadas en las transcripciones públicas de las tres grabaciones compartidas por el equipo. Los tiempos corresponden al reproductor de Zoom. Las transcripciones automáticas pueden transcribir incorrectamente nombres técnicos; aquí se resumen las explicaciones sin reproducir esos errores. Las indicaciones escritas de la PA3 y su rúbrica son la referencia de evaluación.

## Clase del 16 de octubre de 2026

- Grabación: [3703.202620 — Sala del curso, 16/10/2026](https://isil-pe.zoom.us/rec/play/ne1K11fNKuetSRK_SacDHL-XMvltalZwok0zx1H4agbtppQz_kHh9OjJdmbt4za-5XkBHjftKvCZylw.RnMymg9YwkuV_1BJ).
- Duración mostrada: 1 h 38 min 11 s.
- Tema central: migración de la aplicación Java web a Spring Boot, Spring MVC, Thymeleaf y Spring Data JPA.

| Tiempo | Explicación verificada y aplicación al proyecto |
|---|---|
| 00:16:06–00:16:42 | Spring Boot incorpora un servidor embebido y simplifica la configuración y ejecución de la aplicación. |
| 00:17:45–00:22:49 | El controlador recibe rutas HTTP y decide la vista; Thymeleaf permite crear vistas HTML; Spring Data JPA simplifica repositorios y persistencia. Se separan navegación, reglas de negocio y acceso a datos. |
| 00:25:45–00:26:22 | Se explica el recorrido navegador → Controller → Service → Repository y el retorno a la vista Thymeleaf. |
| 01:18:26–01:20:24 | Se presentan `@Service`, inversión de control, inyección de dependencias y transacciones de lectura y escritura. |
| 01:25:27–01:27:44 | Los controladores manejan rutas y vistas; no deben contener SQL. Las reglas se ubican en los servicios y la persistencia en los repositorios. |
| 01:35:01–01:35:52 | Los dos ejes de PA3 son migrar el proyecto a Spring y migrar/cargar datos para trabajar con información suficiente. |
| 01:36:34–01:37:04 | Se admite apoyo de IA para la migración, verificando de forma específica la configuración y que el resultado se ejecute correctamente. |

## Clase del 23 de octubre de 2026

- Grabación: [3703.202620 — Sala del curso, 23/10/2026](https://isil-pe.zoom.us/rec/play/VEHCcEmJXDEyEfYNyYbdPv_TcSlSsxzFo3FD5JZO86IKBDuV-9cX2IUcAfaxb1t57YQgT1cJn8lCjda-.WCk9FeKHIkkqRrPm).
- Duración mostrada: 1 h 4 min 27 s.
- Tema central: repaso de Spring/JPA y extensión del ejemplo de tienda con compras, detalles y actualización de inventario.

| Tiempo | Explicación verificada y aplicación al proyecto |
|---|---|
| 00:13:27–00:14:24 | Las actualizaciones deben responder a un proceso y sus reglas de negocio, no ser cambios aislados de campos. |
| 00:22:54–00:24:00 | Los métodos de servicio utilizan transacciones; las consultas pueden declararse de solo lectura. |
| 00:24:26–00:28:47 | Una operación puede involucrar varias entidades y repositorios. El ejemplo relaciona compra, detalle y producto. |
| 00:40:26–00:40:57 | Se prueba una validación de duplicidad de comprobante en el ejemplo de compras. |
| 00:42:46–00:44:17 | Las anulaciones o bajas deben conservar los registros mediante estado para mantener trazabilidad; se conecta la recomendación con reportes de afectación asociados a Sismo. |
| 00:44:39–00:44:58 | Se insiste en revisar las relaciones y transacciones y mapear correctamente claves primarias y foráneas. |
| 01:02:26–01:03:24 | Se adelanta que PA3 dará importancia a cargar datos y demostrar su funcionamiento, manteniendo coherencia entre entidades y base de datos. |
| 01:03:25–01:03:38 | Los tipos numéricos de Java y SQL deben ser compatibles. |

## Clase del 30 de octubre de 2026

- Grabación: [3703.202620 — Sala del curso, 30/10/2026](https://isil-pe.zoom.us/rec/play/rlrlE8BSadPqteE7Fv5M2uV80JbhWD7qUWVclnygWtr6gdMzny-W21bLlT2loIcc78GGn6VUmrgebq_6.nNeJ0Dv32_TqG8TT).
- Duración mostrada: 1 h 51 min 19 s.
- Tema central: Bean Validation, errores de formulario, reglas de negocio y presentación de la PA3.

| Tiempo | Explicación verificada y aplicación al proyecto |
|---|---|
| 00:09:35–00:11:19 | Las validaciones del navegador pueden ayudar al usuario, pero el servidor debe proteger los datos y las operaciones. |
| 00:18:08–00:19:50 | Se describe el flujo de validación de entrada, devolución de errores a Thymeleaf, evaluación de reglas en Service y persistencia con JPA. |
| 00:30:55–00:31:03 y 00:43:19–00:43:44 | `@Valid` activa las restricciones del formulario y `BindingResult` permite volver a la vista con errores. |
| 01:19:55–01:28:52 | Se diferencia una restricción de entrada de una regla de negocio dependiente del contexto. Las reglas y transacciones se protegen en Service aunque cambie la interfaz que solicita la operación. |
| 01:41:24–01:42:24 | Se anuncia mayor exigencia de coordinación y comunicación del equipo a partir de PA3. |
| 01:44:26–01:44:52 | Todos los integrantes deben intervenir en la exposición. Se aceptan grabaciones separadas si se incluyen en la misma entrega; se pide no enviarlas por correo. |
| 01:44:57–01:45:41 | El informe debe mostrar historias terminadas y pendientes, prioridad y responsable. Se pueden añadir historias, pero no eliminar ni reducir las existentes. Lo marcado como terminado debe corresponder a lo que funciona en el proyecto y se demuestra. |
| 01:45:43–01:47:51 | Se integran Ubigeo, Tipo de Alerta, Sismo, Tipo de Daño y Reporte de Daño, con sus relaciones. Los tres archivos entregados deben conservar sus campos mínimos; pueden ampliarse o adaptarse. |
| 01:46:18–01:47:04 | La aplicación debe compilar y ejecutar con Maven y Spring Boot. Se exige JPA/JpaRepository, Controller → Service → Repository, vistas Thymeleaf y Bean Validation; las reglas relevantes se mantienen fuera del controlador. |
| 01:47:53–01:48:50 | Se solicitan dos casos de uso completos con flujos alternos: registrar Sismo y registrar un reporte de daño asociado a un Sismo. |
| 01:50:01–01:50:28 | Se aclara que se requieren 15 historias de usuario terminadas en total, acumuladas; no 15 historias nuevas. Deben quedar cubiertos los dos casos de uso solicitados. |

### Sobre autenticación

El docente menciona login como mejora futura de su ejemplo de tienda el 16/10 (01:31:21–01:31:35) y el 23/10 (00:32:37–00:32:55). En los tramos revisados de presentación de PA3 no lo establece como requisito de esta entrega. Esta observación no reemplaza las indicaciones escritas ni futuras aclaraciones del docente.

## Referencias técnicas oficiales

Las siguientes fuentes se consultaron para comprobar la compatibilidad y los patrones usados en la implementación. Son documentación del framework; no amplían el alcance solicitado por la PA3.

| Fuente | Uso en el proyecto |
|---|---|
| [Spring Boot 3.5: requisitos del sistema](https://docs.spring.io/spring-boot/3.5/system-requirements.html) | Compatibilidad con Java 21, Maven y servidor Tomcat embebido. |
| [Spring: acceso a datos con JPA](https://spring.io/guides/gs/accessing-data-jpa/) | Entidades `jakarta.persistence` y repositorios Spring Data para persistencia relacional. |
| [Spring: validación de formularios](https://spring.io/guides/gs/validating-form-input/) | Formularios con restricciones, `@Valid`, `BindingResult` y mensajes mediante `th:errors`. |
| [Spring Boot 3.5: validación](https://docs.spring.io/spring-boot/3.5/reference/io/validation.html) | Dependencia `spring-boot-starter-validation` y restricciones Jakarta Validation. |
| [Spring Boot 3.5: aplicaciones web Servlet](https://docs.spring.io/spring-boot/3.5/reference/web/servlet.html) | Spring MVC, controladores, configuración web y vistas. |
| [Spring Data JPA: transacciones](https://docs.spring.io/spring-data/jpa/reference/jpa/transactions.html) | Límites transaccionales de operaciones que utilizan uno o más repositorios. |

Fecha de revisión de las grabaciones y fuentes: 5 de noviembre de 2026.
