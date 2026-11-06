# Product Backlog PA3

44 historias: las 40 originales se conservan y se añaden HU41-HU44.

15 terminadas acumuladas y 29 pendientes.

Responsables de revisión y exposición. Esta asignación no atribuye automáticamente autoría de código ni reuniones.

| Orden | ID | Historia | Prioridad | Revisión | Estado |
| --- | --- | --- | --- | --- | --- |
| 1 | HU01 | Registrar un reporte sísmico inicial | Alta | Fabrizio | Terminado |
| 2 | HU02 | Consultar la lista de reportes activos | Alta | Luis | Terminado |
| 3 | HU03 | Ver el detalle de un reporte | Alta | Adriano | Terminado |
| 4 | HU04 | Corregir la información de un reporte | Alta | Sebastian | Terminado |
| 5 | HU05 | Dar de baja un reporte tras confirmarlo | Alta | Fabrizio | Terminado |
| 6 | HU06 | Buscar reportes por código | Alta | Luis | Terminado |
| 7 | HU07 | Filtrar los reportes por estado | Alta | Adriano | Terminado |
| 8 | HU08 | Actualizar el estado de un reporte | Alta | Sebastian | Terminado |
| 9 | HU09 | Registrar el canal de recepción del reporte | Alta | Fabrizio | Terminado |
| 10 | HU10 | Clasificar la región del reporte | Alta | Luis | Terminado |
| 11 | HU39 | Registrar coordenadas verificadas | Alta | Adriano | Terminado |
| 12 | HU41 | Importar los catálogos iniciales desde CSV | Alta | Adriano | Terminado |
| 13 | HU42 | Clasificar el sismo con un tipo de alerta activo | Alta | Fabrizio | Terminado |
| 14 | HU43 | Registrar un reporte de daño asociado a un sismo | Alta | Luis | Terminado |
| 15 | HU44 | Consultar los daños asociados a un sismo | Alta | Sebastian | Terminado |
| 16 | HU11 | Adjuntar una imagen de respaldo | Media | Adriano | Pendiente |
| 17 | HU12 | Marcar un reporte como urgente | Media | Sebastian | Pendiente |
| 18 | HU13 | Asignarme un reporte pendiente | Media | Fabrizio | Pendiente |
| 19 | HU14 | Dejar una nota interna de revisión | Media | Luis | Pendiente |
| 20 | HU15 | Recibir aviso cuando un reporte se verifica | Media | Adriano | Pendiente |
| 21 | HU16 | Comparar el reporte con su documento de origen | Media | Sebastian | Pendiente |
| 22 | HU17 | Ver solo reportes pendientes | Media | Fabrizio | Pendiente |
| 23 | HU18 | Iniciar una revisión asignada | Media | Luis | Pendiente |
| 24 | HU19 | Cerrar una revisión con respaldo | Media | Adriano | Pendiente |
| 25 | HU20 | Descartar un reporte con motivo | Media | Sebastian | Pendiente |
| 26 | HU21 | Registrar la razón de una corrección | Baja | Fabrizio | Pendiente |
| 27 | HU22 | Comparar un reporte con otros de fecha cercana | Baja | Luis | Pendiente |
| 28 | HU23 | Ver el historial de cambios de un reporte | Baja | Adriano | Pendiente |
| 29 | HU24 | Señalar un reporte que requiere datos adicionales | Baja | Sebastian | Pendiente |
| 30 | HU25 | Ver cuántos reportes hay por estado | Baja | Fabrizio | Pendiente |
| 31 | HU26 | Consultar reportes de un rango de fechas | Baja | Luis | Pendiente |
| 32 | HU27 | Exportar el listado de reportes | Baja | Adriano | Pendiente |
| 33 | HU28 | Consultar los reportes dados de baja | Baja | Sebastian | Pendiente |
| 34 | HU29 | Restaurar un reporte dado de baja | Baja | Fabrizio | Pendiente |
| 35 | HU30 | Ver la distribución de reportes por ubicación | Baja | Luis | Pendiente |
| 36 | HU31 | Consultar la actividad diaria de reportes | Baja | Adriano | Pendiente |
| 37 | HU32 | Identificar reportes pendientes desde hace más de un día | Baja | Sebastian | Pendiente |
| 38 | HU33 | Crear cuentas | Baja | Fabrizio | Pendiente |
| 39 | HU34 | Asignar un rol a cada usuario | Baja | Luis | Pendiente |
| 40 | HU35 | Desactivar una cuenta | Baja | Adriano | Pendiente |
| 41 | HU36 | Consultar un registro de acciones | Baja | Sebastian | Pendiente |
| 42 | HU37 | Adjuntar una fuente de recepción al reporte | Baja | Fabrizio | Pendiente |
| 43 | HU38 | Marcar la ubicación como aproximada | Baja | Luis | Pendiente |
| 44 | HU40 | Descargar un resumen de reportes verificados | Baja | Sebastian | Pendiente |

## Historias y criterios de aceptación

### HU01. Registrar un reporte sísmico inicial

Como Operador, quiero registrar un reporte sísmico inicial para centralizar la información recibida.

- CA1: El formulario solicita código, fecha y hora, ubicación, magnitud, profundidad y estado.
- CA2: Al guardar datos válidos, el reporte aparece en el listado con un ID interno.

Estado: Terminado. Prioridad: Alta. Revisión: Fabrizio.

### HU02. Consultar la lista de reportes activos

Como Operador, quiero consultar la lista de reportes activos para ubicar los registros disponibles.

- CA1: La lista muestra código, fecha, ubicación, magnitud y estado de cada reporte activo.
- CA2: Los reportes dados de baja no aparecen en el listado normal.

Estado: Terminado. Prioridad: Alta. Revisión: Luis.

### HU03. Ver el detalle de un reporte

Como Operador, quiero ver el detalle de un reporte para revisar toda su información antes de actuar.

- CA1: Al seleccionar Ver se muestran todos los datos del reporte elegido.
- CA2: La vista ofrece acciones para editar, dar de baja y volver al listado.

Estado: Terminado. Prioridad: Alta. Revisión: Adriano.

### HU04. Corregir la información de un reporte

Como Operador, quiero corregir la información de un reporte para mantener actualizados los datos recibidos.

- CA1: El formulario de edición carga los datos actuales del reporte.
- CA2: Tras guardar datos válidos, el detalle muestra los cambios realizados.

Estado: Terminado. Prioridad: Alta. Revisión: Sebastian.

### HU05. Dar de baja un reporte tras confirmarlo

Como Operador, quiero dar de baja un reporte tras confirmarlo para retirar registros erróneos sin perderlos.

- CA1: Antes de la baja se muestra el código y una advertencia con Confirmar y Cancelar.
- CA2: Al confirmar, el registro queda inactivo y desaparece del listado; cancelar no lo modifica.

Estado: Terminado. Prioridad: Alta. Revisión: Fabrizio.

### HU06. Buscar reportes por código

Como Operador, quiero buscar reportes por código para encontrar rápidamente un registro específico.

- CA1: La búsqueda muestra los reportes activos cuyo código contiene el texto ingresado.
- CA2: Si no hay coincidencias, el sistema informa que no se encontraron reportes.

Estado: Terminado. Prioridad: Alta. Revisión: Luis.

### HU07. Filtrar los reportes por estado

Como Verificador, quiero filtrar los reportes por estado para revisar primero los que requieren atención.

- CA1: El filtro permite seleccionar un estado disponible o mostrar todos.
- CA2: El listado contiene únicamente reportes activos del estado elegido.

Estado: Terminado. Prioridad: Alta. Revisión: Adriano.

### HU08. Actualizar el estado de un reporte

Como Verificador, quiero actualizar el estado de un reporte para reflejar el avance de su revisión.

- CA1: La edición ofrece Pendiente, En verificación, Verificado y Descartado.
- CA2: El nuevo estado se muestra en el detalle y en el listado después de guardar.

Estado: Terminado. Prioridad: Alta. Revisión: Sebastian.

### HU09. Registrar el canal de recepción del reporte

Como Operador, quiero registrar el canal de recepción del reporte para identificar si el dato llegó por llamada, correo o mensaje.

- CA1: El formulario permite elegir un canal entre los definidos por el equipo.
- CA2: El canal guardado aparece en el detalle del reporte.

Estado: Terminado. Prioridad: Alta. Revisión: Fabrizio.

### HU10. Clasificar la región del reporte

Como Operador, quiero clasificar la región del reporte para agrupar entradas sin depender del texto libre de ubicación.

- CA1: El formulario permite seleccionar una región del catálogo disponible.
- CA2: El detalle muestra la región separada de la ubicación textual.

Estado: Terminado. Prioridad: Alta. Revisión: Luis.

### HU39. Registrar coordenadas verificadas

Como Verificador, quiero registrar coordenadas verificadas para precisar la ubicación del evento.

- CA1: La latitud aceptada está entre -90 y 90 y la longitud entre -180 y 180.
- CA2: Las coordenadas guardadas aparecen en el detalle junto a la ubicación textual.

Estado: Terminado. Prioridad: Alta. Revisión: Adriano.

### HU41. Importar los catálogos iniciales desde CSV

Como Administrador, quiero importar los catálogos de ubigeo, alerta y daño desde CSV para preparar datos consistentes sin digitarlos uno por uno.

- CA1: Los tres archivos producen 25 ubigeos, 4 alertas y 7 tipos de daño con campos mínimos y códigos conservados; los maestros pueden listarse y registrarse manualmente.
- CA2: La recarga idéntica no duplica filas y un error cancela el archivo completo; editar o desactivar maestros valida sus campos y protege las referencias existentes.

Estado: Terminado. Prioridad: Alta. Revisión: Adriano.

### HU42. Clasificar el sismo con un tipo de alerta activo

Como Operador, quiero asignar a cada sismo un tipo de alerta activo para registrar una clasificación común que pueda consultarse junto al evento.

- CA1: El registro y edición requieren seleccionar un tipo de alerta existente y activo; el servicio rechaza referencias inexistentes o inactivas.
- CA2: El listado y el detalle muestran la clasificación de alerta guardada, diferenciada del estado de revisión.

Estado: Terminado. Prioridad: Alta. Revisión: Fabrizio.

### HU43. Registrar un reporte de daño asociado a un sismo

Como Operador, quiero registrar un daño dentro del detalle de un sismo para documentar la afectación recibida sin perder el vínculo con el evento.

- CA1: El formulario guarda un tipo de daño activo, una cantidad entera positiva y una descripción obligatoria vinculados al sismo activo.
- CA2: Las entradas inválidas o referencias inactivas se rechazan sin guardar; una operación válida persiste en una transacción y vuelve al mismo evento.

Estado: Terminado. Prioridad: Alta. Revisión: Luis.

### HU44. Consultar los daños asociados a un sismo

Como Supervisor, quiero consultar los daños registrados en el detalle de un sismo para revisar el impacto documentado de ese evento.

- CA1: El detalle muestra solo los daños del sismo elegido, con tipo, cantidad, descripción y fecha de registro.
- CA2: Los daños permanecen consultables tras reiniciar la aplicación y el detalle informa cuando el evento todavía no tiene daños.

Estado: Terminado. Prioridad: Alta. Revisión: Sebastian.

### HU11. Adjuntar una imagen de respaldo

Como Operador, quiero adjuntar una imagen de respaldo para conservar la evidencia recibida con el reporte.

- CA1: Se admite un archivo de imagen con tamaño y formato permitidos.
- CA2: El detalle permite visualizar o descargar el adjunto vinculado al mismo ID.

Estado: Pendiente. Prioridad: Media. Revisión: Adriano.

### HU12. Marcar un reporte como urgente

Como Operador, quiero marcar un reporte como urgente para avisar que necesita revisión prioritaria.

- CA1: La marca de urgencia exige indicar una justificación breve.
- CA2: Los reportes urgentes se distinguen visualmente en la cola de revisión.

Estado: Pendiente. Prioridad: Media. Revisión: Sebastian.

### HU13. Asignarme un reporte pendiente

Como Verificador, quiero asignarme un reporte pendiente para evitar que dos personas lo revisen a la vez.

- CA1: Un reporte sin asignación puede tomarse desde la cola.
- CA2: La cola muestra quién lo tiene asignado y desde cuándo.

Estado: Pendiente. Prioridad: Media. Revisión: Fabrizio.

### HU14. Dejar una nota interna de revisión

Como Verificador, quiero dejar una nota interna de revisión para documentar dudas sin alterar el reporte original.

- CA1: La nota registra texto, autor y fecha de creación.
- CA2: Las notas internas son visibles en el detalle solo para roles autorizados.

Estado: Pendiente. Prioridad: Media. Revisión: Luis.

### HU15. Recibir aviso cuando un reporte se verifica

Como Supervisor, quiero recibir aviso cuando un reporte se verifica para dar seguimiento a los reportes concluidos.

- CA1: El sistema registra un aviso al pasar un reporte a Verificado.
- CA2: El aviso muestra código, fecha y responsable de la verificación.

Estado: Pendiente. Prioridad: Media. Revisión: Adriano.

### HU16. Comparar el reporte con su documento de origen

Como Verificador, quiero comparar el reporte con su documento de origen para confirmar que los datos transcritos son correctos.

- CA1: La vista presenta los datos del reporte junto a la referencia de origen.
- CA2: El verificador puede registrar el resultado de la comparación sin sobrescribir el original.

Estado: Pendiente. Prioridad: Media. Revisión: Sebastian.

### HU17. Ver solo reportes pendientes

Como Verificador, quiero ver solo reportes pendientes para organizar mi cola de revisión.

- CA1: La vista de pendientes excluye reportes verificados y descartados.
- CA2: Cada elemento permite abrir su detalle para comenzar la revisión.

Estado: Pendiente. Prioridad: Media. Revisión: Fabrizio.

### HU18. Iniciar una revisión asignada

Como Verificador, quiero iniciar una revisión asignada para dejar constancia de quién se hizo cargo.

- CA1: Solo el verificador asignado puede iniciar la revisión desde su cola.
- CA2: El sistema registra responsable y hora de inicio al cambiar a En verificación.

Estado: Pendiente. Prioridad: Media. Revisión: Luis.

### HU19. Cerrar una revisión con respaldo

Como Verificador, quiero cerrar una revisión con respaldo para justificar la confirmación de un reporte.

- CA1: Para marcar Verificado se exige una referencia de contraste.
- CA2: El cierre registra responsable, fecha y referencia sin alterar el ID del reporte.

Estado: Pendiente. Prioridad: Media. Revisión: Adriano.

### HU20. Descartar un reporte con motivo

Como Verificador, quiero descartar un reporte con motivo para explicar por qué no se considera confiable.

- CA1: Para marcar Descartado es obligatorio indicar un motivo.
- CA2: El detalle conserva el motivo y la identidad del verificador que decidió el descarte.

Estado: Pendiente. Prioridad: Media. Revisión: Sebastian.

### HU21. Registrar la razón de una corrección

Como Verificador, quiero registrar la razón de una corrección para dar contexto al cambio realizado.

- CA1: El formulario permite asociar un motivo de cambio al reporte.
- CA2: El detalle muestra el motivo y la fecha de la corrección.

Estado: Pendiente. Prioridad: Baja. Revisión: Fabrizio.

### HU22. Comparar un reporte con otros de fecha cercana

Como Verificador, quiero comparar un reporte con otros de fecha cercana para detectar posibles duplicados.

- CA1: La vista propone reportes activos dentro de un intervalo temporal definido.
- CA2: Cada candidato muestra código, ubicación, fecha y magnitud para compararlos.

Estado: Pendiente. Prioridad: Baja. Revisión: Luis.

### HU23. Ver el historial de cambios de un reporte

Como Verificador, quiero ver el historial de cambios de un reporte para entender cómo evolucionó su información.

- CA1: Cada cambio registrado muestra fecha, campo y valor anterior y nuevo.
- CA2: El historial pertenece al mismo ID aunque cambie el código del reporte.

Estado: Pendiente. Prioridad: Baja. Revisión: Adriano.

### HU24. Señalar un reporte que requiere datos adicionales

Como Verificador, quiero señalar un reporte que requiere datos adicionales para devolverlo para corrección.

- CA1: El reporte puede marcarse como Requiere información con una nota.
- CA2: El operador puede identificar la nota al abrir el reporte.

Estado: Pendiente. Prioridad: Baja. Revisión: Sebastian.

### HU25. Ver cuántos reportes hay por estado

Como Supervisor, quiero ver cuántos reportes hay por estado para conocer la carga de revisión.

- CA1: El resumen muestra cantidades de pendientes, en verificación, verificados y descartados.
- CA2: El total coincide con los reportes activos del periodo elegido.

Estado: Pendiente. Prioridad: Baja. Revisión: Fabrizio.

### HU26. Consultar reportes de un rango de fechas

Como Supervisor, quiero consultar reportes de un rango de fechas para analizar un periodo específico.

- CA1: Se pueden elegir fecha inicial y fecha final válidas.
- CA2: Los resultados excluyen reportes fuera del rango seleccionado.

Estado: Pendiente. Prioridad: Baja. Revisión: Luis.

### HU27. Exportar el listado de reportes

Como Supervisor, quiero exportar el listado de reportes para analizarlo fuera del sistema.

- CA1: La exportación incluye código, fecha, ubicación, magnitud, profundidad y estado.
- CA2: El archivo respeta los filtros activos al momento de exportar.

Estado: Pendiente. Prioridad: Baja. Revisión: Adriano.

### HU28. Consultar los reportes dados de baja

Como Supervisor, quiero consultar los reportes dados de baja para revisar decisiones anteriores.

- CA1: La vista de archivo incluye solo registros inactivos.
- CA2: Cada registro muestra su código y el estado que tenía antes de la baja.

Estado: Pendiente. Prioridad: Baja. Revisión: Sebastian.

### HU29. Restaurar un reporte dado de baja

Como Supervisor, quiero restaurar un reporte dado de baja para corregir una baja accidental.

- CA1: La restauración solicita confirmación e identifica el reporte.
- CA2: El reporte restaurado vuelve al listado activo si su código no entra en conflicto.

Estado: Pendiente. Prioridad: Baja. Revisión: Fabrizio.

### HU30. Ver la distribución de reportes por ubicación

Como Supervisor, quiero ver la distribución de reportes por ubicación para identificar zonas con mayor carga de registro.

- CA1: El resumen agrupa reportes activos por ubicación normalizada.
- CA2: La suma de los grupos coincide con los reportes activos considerados.

Estado: Pendiente. Prioridad: Baja. Revisión: Luis.

### HU31. Consultar la actividad diaria de reportes

Como Supervisor, quiero consultar la actividad diaria de reportes para dar seguimiento a la recepción.

- CA1: La vista muestra el número de reportes creados por día del periodo elegido.
- CA2: Los días sin reportes aparecen con valor cero dentro del rango.

Estado: Pendiente. Prioridad: Baja. Revisión: Adriano.

### HU32. Identificar reportes pendientes desde hace más de un día

Como Supervisor, quiero identificar reportes pendientes desde hace más de un día para priorizar los atrasados.

- CA1: La vista señala pendientes que superan el umbral configurado.
- CA2: Cada elemento enlaza a su detalle para tomar acción.

Estado: Pendiente. Prioridad: Baja. Revisión: Sebastian.

### HU33. Crear cuentas

Como Administrador, quiero crear cuentas para operadores para controlar quién registra reportes.

- CA1: Una cuenta nueva exige identificador único y estado activo.
- CA2: La cuenta creada puede aparecer en la lista de usuarios.

Estado: Pendiente. Prioridad: Baja. Revisión: Fabrizio.

### HU34. Asignar un rol a cada usuario

Como Administrador, quiero asignar un rol a cada usuario para limitar acciones según responsabilidad.

- CA1: El rol puede ser Operador, Verificador, Supervisor o Administrador.
- CA2: Los permisos visibles corresponden al rol asignado al iniciar sesión.

Estado: Pendiente. Prioridad: Baja. Revisión: Luis.

### HU35. Desactivar una cuenta

Como Administrador, quiero desactivar una cuenta para impedir accesos que ya no corresponden.

- CA1: La desactivación requiere confirmación.
- CA2: Una cuenta inactiva no puede iniciar una nueva sesión.

Estado: Pendiente. Prioridad: Baja. Revisión: Adriano.

### HU36. Consultar un registro de acciones

Como Administrador, quiero consultar un registro de acciones para investigar cambios importantes.

- CA1: El registro identifica acción, usuario, reporte y momento.
- CA2: Las altas, ediciones y bajas quedan incluidas en la consulta.

Estado: Pendiente. Prioridad: Baja. Revisión: Sebastian.

### HU37. Adjuntar una fuente de recepción al reporte

Como Operador, quiero adjuntar una fuente de recepción al reporte para saber de dónde provino el dato.

- CA1: El reporte permite registrar canal y referencia de origen.
- CA2: La fuente se muestra en el detalle sin confundirse con una validación oficial.

Estado: Pendiente. Prioridad: Baja. Revisión: Fabrizio.

### HU38. Marcar la ubicación como aproximada

Como Operador, quiero marcar la ubicación como aproximada para advertir que necesita corrección.

- CA1: El formulario permite indicar si la ubicación es aproximada.
- CA2: El detalle muestra esa advertencia hasta que se confirme la ubicación.

Estado: Pendiente. Prioridad: Baja. Revisión: Luis.

### HU40. Descargar un resumen de reportes verificados

Como Supervisor, quiero descargar un resumen de reportes verificados para compartir resultados de revisión interna.

- CA1: El resumen contiene únicamente reportes con estado Verificado.
- CA2: El documento indica que los datos son de prueba y no constituyen alerta oficial.

Estado: Pendiente. Prioridad: Baja. Revisión: Sebastian.
