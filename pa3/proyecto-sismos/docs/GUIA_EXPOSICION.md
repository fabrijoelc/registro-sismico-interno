# Guía de exposición PA3 · Grupo 8

Duración objetivo: 11 minutos. Material a mostrar: informe, código y aplicación local. La guía PA3 no exige Figma ni PPT. Hablar con la aplicación abierta, sin leer el informe completo.

| Integrante | Tiempo | Pantalla o material |
| --- | --- | --- |
| Sebastian | 0:00-2:00 | Informe: objetivo, backlog, 15 terminadas y pendientes. |
| Adriano | 2:00-5:00 | Código Controller/Service/Repository; pantalla Importar y catálogos. |
| Fabrizio | 5:00-7:30 | Nuevo reporte y detalle con ubigeo y alerta. |
| Luis | 7:30-11:00 | Registrar daño, error de cantidad, detalle y persistencia. |

## Preparación antes de grabar

- Abrir Docker Desktop y ejecutar `docker compose up -d --build`.
- Abrir http://localhost:18084/sismos-pa3/ en el equipo que comparte pantalla.
- Tener los tres CSV de `datos/` a mano. Si ya se importaron al ensayar, usar la recarga sin duplicados y explicar el resultado real. No borrar una base con datos para simular una primera importación.
- Elegir un código nuevo para el video, por ejemplo `SIS-EXPO-001` y cambiar el sufijo si ya existe.
- Usar una fecha y hora anterior al momento de la grabación, en hora de Perú.
- Datos sugeridos: referencia «A 15 km al sur de Lima», magnitud 4.2, profundidad 35.00 km, canal Correo, estado Pendiente, ubigeo Lima, alerta Preventiva.
- Daño de ejemplo: Vivienda, cantidad 3, descripción «Tres viviendas con grietas reportadas por el operador. Dato sintético para demostración».

## 1. Sebastian: objetivo y backlog

Mostrar la portada y avanzar al backlog.

“Continuamos el sistema de registro interno de sismos desarrollado en PA1 y PA2. Esta entrega integra el registro del evento con su ubicación, clasificación de alerta y reportes de daños. La aplicación ahora usa Spring Boot y conserva los datos en MySQL.

El backlog mantiene las 40 historias originales y sus criterios de aceptación. Incorporamos cuatro historias para cubrir la carga de maestros, la clasificación de alerta y el registro y consulta de daños. Tenemos quince historias acumuladas terminadas; las demás se identifican como pendientes. No estamos presentando todo el sistema como finalizado.

La prioridad fue completar un recorrido útil: cargar catálogos, registrar un sismo y documentar sus posibles afectaciones. Cada historia tiene un responsable de revisión y una evidencia. Las cuentas, permisos, adjuntos y otras funciones siguen en el backlog futuro. A continuación mostraremos la arquitectura y cómo se cargan los datos que alimentan ese recorrido.”

Señalar una historia nueva y sus dos criterios, sin leer toda la lista. Explicar la responsabilidad del grupo, sin atribuir entrevistas o reuniones no realizadas.

## 2. Adriano: arquitectura e importación

Mostrar brevemente `pom.xml`, una entidad, un controlador, un servicio y un repositorio.

“La migración incorpora Spring Boot para configurar y ejecutar la aplicación con Maven. Las vistas son Thymeleaf. El controlador recibe la solicitud y los errores del formulario; el servicio aplica las reglas y las transacciones; el repositorio utiliza Spring Data JPA para acceder a MySQL. Por eso una validación importante, como impedir una referencia inactiva, permanece protegida aunque el dato se envíe fuera del formulario.

Los CSV entregados por el curso evitan digitar los maestros uno por uno. Ubigeo aporta las ubicaciones, TipoAlerta la clasificación y TipoDano las categorías de afectación.”

En Importar, cargar los tres archivos o demostrar su recarga. Mostrar los resultados reales: 25, 4 y 7 creados o ya existentes según la base utilizada.

“Los códigos conservan sus ceros iniciales, como 01. Si cargamos de nuevo el mismo archivo no se duplican filas. Si una fila es inválida o entra en conflicto, se informa su línea y la transacción impide dejar una carga parcial. Ahora estos catálogos se pueden seleccionar desde los formularios del sistema.”

## 3. Fabrizio: registrar y consultar Sismo

Abrir Nuevo reporte. Completar los datos sugeridos y señalar Ubigeo y Tipo de alerta.

“Voy a registrar un sismo utilizando los catálogos importados. Ingreso un código único, la fecha y hora, referencia, magnitud y profundidad. También conservamos el canal de recepción y el estado de revisión de la versión anterior.

Selecciono Lima como ubigeo y Preventiva como clasificación de alerta. Estas opciones son relaciones con registros del catálogo, no nombres escritos libremente. Solo se pueden utilizar maestros activos. La alerta representa una clasificación interna elegida por el operador; el programa no calcula una emergencia oficial a partir de la magnitud.

Las coordenadas son opcionales, pero si se registra una también debe completarse la otra. Guardo el reporte y el sistema abre su detalle. Aquí comprobamos los datos y las dos clasificaciones relacionadas. Podemos volver al listado, editar o iniciar la baja con confirmación.

En la misma pantalla aparece la sección de daños. De este modo, el daño se registra dentro de un evento concreto y no queda como una entrada aislada. Luis va a completar ese segundo caso de uso.”

Guardar y mantener el detalle abierto para Luis. Si se usa un código ya existente, corregirlo y explicar el mensaje real.

## 4. Luis: registrar daño, validaciones y cierre

Desde el sismo de Fabrizio, elegir Registrar daño. Enviar primero cantidad 0 con los demás datos completos.

“Ahora documentamos una afectación asociada al sismo que acabamos de registrar. Selecciono el tipo de daño del catálogo, escribo la cantidad y describo qué se reportó. Primero voy a ingresar cero para mostrar una validación: el sistema exige una cantidad entera positiva y no guarda esta entrada inválida.

Corrijo la cantidad a tres y guardo. Regresamos al detalle del mismo sismo y ya vemos el tipo de daño, cantidad, descripción y fecha de registro. Un sismo puede tener varios daños; cada daño también conserva su referencia al tipo seleccionado. No sumamos personas, viviendas y establecimientos como si fueran una misma unidad.

Las validaciones del formulario usan Bean Validation. Las reglas sobre maestros activos y referencias se revisan nuevamente en el servicio y el guardado se realiza dentro de una transacción. Los datos se conservan en MySQL; volver a abrir la página o reiniciar la aplicación no los convierte en datos temporales.

Con estos dos casos de uso mostramos el incremento integrado: importamos maestros, registramos un sismo y asociamos un daño que luego podemos consultar. El informe incluye sus flujos alternos, las pruebas y los pendientes para la siguiente versión.”

Para demostrar persistencia, volver al listado y abrir el mismo código. Si van a mostrar un reinicio real, medir su duración antes y reservar unos segundos; las evidencias de pruebas incluyen la comprobación correspondiente.

## Cómo repartir la grabación

Puede compartir pantalla una sola persona y dar el pase de voz a cada compañero. También pueden grabar segmentos en orden y unirlos, respetando el límite de 8-12 minutos. Todos deben explicar su parte y entender el recorrido, aunque una misma persona opere los clics.

## Preguntas cortas para ensayar

- ¿Para qué sirve Service? Para proteger reglas y coordinar transacciones.
- ¿Qué aporta JpaRepository? Operaciones persistentes sin escribir una implementación DAO manual para cada entidad.
- ¿Por qué no aceptar maestros inactivos? Para no registrar eventos o daños con una clasificación que ya no está disponible.
- ¿Qué pasa al reimportar? Los registros idénticos existentes no se duplican; los conflictos se informan.
- ¿Son quince historias nuevas? No: el profesor aclaró que son quince terminadas acumuladas.
- ¿Ya hay login? No; las cuentas y permisos siguen pendientes y no son un requisito de esta PA3.
