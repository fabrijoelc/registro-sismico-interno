# Registro interno de sismos - PA1

Aplicación académica para consolidar **reportes sísmicos de prueba**. No emite alertas oficiales ni predice sismos.

**Java 21 · Servlets Jakarta · JSP/JSTL · Maven · Tomcat 11 · MVC**

Permite registrar, consultar, buscar, filtrar, editar y dar de baja reportes. Incluye validaciones del lado del servidor y datos iniciales para demostrar el flujo completo.

## Requisitos

- JDK 21
- Maven 3.9+
- Apache Tomcat 11

## Compilar y ejecutar

1. En esta carpeta, ejecutar `mvn clean verify`.
2. Copiar `target/sismos-pa1.war` a la carpeta `webapps` de Tomcat 11.
3. Iniciar Tomcat y abrir `http://localhost:8080/sismos-pa1/`.

El listener crea un repositorio compartido y tres registros de ejemplo. Los cambios se conservan durante la ejecución de Tomcat; al reiniciar se restablecen los datos iniciales.

`mvn clean verify` ejecuta tres pruebas automáticas del repositorio (alta, búsqueda, filtros, unicidad, edición y baja lógica) antes de generar el WAR.

## Rutas

| Ruta | Función |
| --- | --- |
| `/sismos` | Listado, búsqueda y filtro por estado |
| `/sismos/nuevo` | Registro |
| `/sismos/ver?id=...` | Detalle |
| `/sismos/editar?id=...` | Edición |
| `/sismos/eliminar?id=...` | Confirmación y baja lógica |

La baja lógica conserva el registro en memoria con `activo=false` y lo excluye del listado normal. Toda operación de guardado y baja se realiza mediante POST.

## Estructura

- `model`: entidad y estado.
- `repository`: operaciones CRUD y unicidad del código.
- `listener`: inicialización del repositorio compartido y datos de prueba.
- `servlet`: rutas, validación y control del flujo.
- `WEB-INF/views/sismos`: JSP de presentación.

## Prueba manual mínima

Registrar un sismo y comprobar que aparece en la lista. Abrir su detalle, editarlo y confirmar el cambio. Intentar guardar un código duplicado, campos vacíos, magnitud fuera de rango y profundidad negativa: deben aparecer mensajes sin perder los datos ingresados. Confirmar una baja y comprobar que desaparece del listado. Cancelar otra baja y comprobar que el registro sigue visible.
