# Product Backlog PA2

40 historias originales; 11 implementadas y 29 pendientes. Responsables de revisión, no atribución automática de commits.

## 1. HU01 - Registrar un reporte sísmico inicial

Prioridad: Alta · Estado: PA1 migrada a JPA · Revisión: Fabrizio

Como Operador, quiero registrar un reporte sísmico inicial para centralizar la información recibida.

- CA1: El formulario solicita código, fecha y hora, ubicación, magnitud, profundidad y estado.
- CA2: Al guardar datos válidos, el reporte aparece en el listado con un ID interno.

## 2. HU02 - Consultar la lista de reportes activos

Prioridad: Alta · Estado: PA1 migrada a JPA · Revisión: Luis

Como Operador, quiero consultar la lista de reportes activos para ubicar los registros disponibles.

- CA1: La lista muestra código, fecha, ubicación, magnitud y estado de cada reporte activo.
- CA2: Los reportes dados de baja no aparecen en el listado normal.

## 3. HU03 - Ver el detalle de un reporte

Prioridad: Alta · Estado: PA1 migrada a JPA · Revisión: Adriano

Como Operador, quiero ver el detalle de un reporte para revisar toda su información antes de actuar.

- CA1: Al seleccionar Ver se muestran todos los datos del reporte elegido.
- CA2: La vista ofrece acciones para editar, dar de baja y volver al listado.

## 4. HU04 - Corregir la información de un reporte

Prioridad: Alta · Estado: PA1 migrada a JPA · Revisión: Sebastian

Como Operador, quiero corregir la información de un reporte para mantener actualizados los datos recibidos.

- CA1: El formulario de edición carga los datos actuales del reporte.
- CA2: Tras guardar datos válidos, el detalle muestra los cambios realizados.

## 5. HU05 - Dar de baja un reporte tras confirmarlo

Prioridad: Alta · Estado: PA1 migrada a JPA · Revisión: Fabrizio

Como Operador, quiero dar de baja un reporte tras confirmarlo para retirar registros erróneos sin perderlos.

- CA1: Antes de la baja se muestra el código y una advertencia con Confirmar y Cancelar.
- CA2: Al confirmar, el registro queda inactivo y desaparece del listado; cancelar no lo modifica.

## 6. HU06 - Buscar reportes por código

Prioridad: Alta · Estado: PA1 migrada a JPA · Revisión: Luis

Como Operador, quiero buscar reportes por código para encontrar rápidamente un registro específico.

- CA1: La búsqueda muestra los reportes activos cuyo código contiene el texto ingresado.
- CA2: Si no hay coincidencias, el sistema informa que no se encontraron reportes.

## 7. HU07 - Filtrar los reportes por estado

Prioridad: Alta · Estado: PA1 migrada a JPA · Revisión: Adriano

Como Verificador, quiero filtrar los reportes por estado para revisar primero los que requieren atención.

- CA1: El filtro permite seleccionar un estado disponible o mostrar todos.
- CA2: El listado contiene únicamente reportes activos del estado elegido.

## 8. HU08 - Actualizar el estado de un reporte

Prioridad: Alta · Estado: PA1 migrada a JPA · Revisión: Sebastian

Como Verificador, quiero actualizar el estado de un reporte para reflejar el avance de su revisión.

- CA1: La edición ofrece Pendiente, En verificación, Verificado y Descartado.
- CA2: El nuevo estado se muestra en el detalle y en el listado después de guardar.

## 9. HU09 - Registrar el canal de recepción del reporte

Prioridad: Alta · Estado: Implementada PA2 · Revisión: Fabrizio

Como Operador, quiero registrar el canal de recepción del reporte para identificar si el dato llegó por llamada, correo o mensaje.

- CA1: El formulario permite elegir un canal entre los definidos por el equipo.
- CA2: El canal guardado aparece en el detalle del reporte.

## 10. HU10 - Clasificar la región del reporte

Prioridad: Alta · Estado: Implementada PA2 · Revisión: Luis

Como Operador, quiero clasificar la región del reporte para agrupar entradas sin depender del texto libre de ubicación.

- CA1: El formulario permite seleccionar una región del catálogo disponible.
- CA2: El detalle muestra la región separada de la ubicación textual.

## 11. HU39 - Registrar coordenadas verificadas

Prioridad: Alta · Estado: Implementada PA2 · Revisión: Adriano

Como Verificador, quiero registrar coordenadas verificadas para precisar la ubicación del evento.

- CA1: La latitud aceptada está entre -90 y 90 y la longitud entre -180 y 180.
- CA2: Las coordenadas guardadas aparecen en el detalle junto a la ubicación textual.

## 12. HU11 - Adjuntar una imagen de respaldo

Prioridad: Media · Estado: Pendiente · Revisión: Adriano

Como Operador, quiero adjuntar una imagen de respaldo para conservar la evidencia recibida con el reporte.

- CA1: Se admite un archivo de imagen con tamaño y formato permitidos.
- CA2: El detalle permite visualizar o descargar el adjunto vinculado al mismo ID.

## 13. HU12 - Marcar un reporte como urgente

Prioridad: Media · Estado: Pendiente · Revisión: Sebastian

Como Operador, quiero marcar un reporte como urgente para avisar que necesita revisión prioritaria.

- CA1: La marca de urgencia exige indicar una justificación breve.
- CA2: Los reportes urgentes se distinguen visualmente en la cola de revisión.

## 14. HU13 - Asignarme un reporte pendiente

Prioridad: Media · Estado: Pendiente · Revisión: Fabrizio

Como Verificador, quiero asignarme un reporte pendiente para evitar que dos personas lo revisen a la vez.

- CA1: Un reporte sin asignación puede tomarse desde la cola.
- CA2: La cola muestra quién lo tiene asignado y desde cuándo.

## 15. HU14 - Dejar una nota interna de revisión

Prioridad: Media · Estado: Pendiente · Revisión: Luis

Como Verificador, quiero dejar una nota interna de revisión para documentar dudas sin alterar el reporte original.

- CA1: La nota registra texto, autor y fecha de creación.
- CA2: Las notas internas son visibles en el detalle solo para roles autorizados.

## 16. HU15 - Recibir aviso cuando un reporte se verifica

Prioridad: Media · Estado: Pendiente · Revisión: Adriano

Como Supervisor, quiero recibir aviso cuando un reporte se verifica para dar seguimiento a los reportes concluidos.

- CA1: El sistema registra un aviso al pasar un reporte a Verificado.
- CA2: El aviso muestra código, fecha y responsable de la verificación.

## 17. HU16 - Comparar el reporte con su documento de origen

Prioridad: Media · Estado: Pendiente · Revisión: Sebastian

Como Verificador, quiero comparar el reporte con su documento de origen para confirmar que los datos transcritos son correctos.

- CA1: La vista presenta los datos del reporte junto a la referencia de origen.
- CA2: El verificador puede registrar el resultado de la comparación sin sobrescribir el original.

## 18. HU17 - Ver solo reportes pendientes

Prioridad: Media · Estado: Pendiente · Revisión: Fabrizio

Como Verificador, quiero ver solo reportes pendientes para organizar mi cola de revisión.

- CA1: La vista de pendientes excluye reportes verificados y descartados.
- CA2: Cada elemento permite abrir su detalle para comenzar la revisión.

## 19. HU18 - Iniciar una revisión asignada

Prioridad: Media · Estado: Pendiente · Revisión: Luis

Como Verificador, quiero iniciar una revisión asignada para dejar constancia de quién se hizo cargo.

- CA1: Solo el verificador asignado puede iniciar la revisión desde su cola.
- CA2: El sistema registra responsable y hora de inicio al cambiar a En verificación.

## 20. HU19 - Cerrar una revisión con respaldo

Prioridad: Media · Estado: Pendiente · Revisión: Adriano

Como Verificador, quiero cerrar una revisión con respaldo para justificar la confirmación de un reporte.

- CA1: Para marcar Verificado se exige una referencia de contraste.
- CA2: El cierre registra responsable, fecha y referencia sin alterar el ID del reporte.

## 21. HU20 - Descartar un reporte con motivo

Prioridad: Media · Estado: Pendiente · Revisión: Sebastian

Como Verificador, quiero descartar un reporte con motivo para explicar por qué no se considera confiable.

- CA1: Para marcar Descartado es obligatorio indicar un motivo.
- CA2: El detalle conserva el motivo y la identidad del verificador que decidió el descarte.

## 22. HU21 - Registrar la razón de una corrección

Prioridad: Baja · Estado: Pendiente · Revisión: Fabrizio

Como Verificador, quiero registrar la razón de una corrección para dar contexto al cambio realizado.

- CA1: El formulario permite asociar un motivo de cambio al reporte.
- CA2: El detalle muestra el motivo y la fecha de la corrección.

## 23. HU22 - Comparar un reporte con otros de fecha cercana

Prioridad: Baja · Estado: Pendiente · Revisión: Luis

Como Verificador, quiero comparar un reporte con otros de fecha cercana para detectar posibles duplicados.

- CA1: La vista propone reportes activos dentro de un intervalo temporal definido.
- CA2: Cada candidato muestra código, ubicación, fecha y magnitud para compararlos.

## 24. HU23 - Ver el historial de cambios de un reporte

Prioridad: Baja · Estado: Pendiente · Revisión: Adriano

Como Verificador, quiero ver el historial de cambios de un reporte para entender cómo evolucionó su información.

- CA1: Cada cambio registrado muestra fecha, campo y valor anterior y nuevo.
- CA2: El historial pertenece al mismo ID aunque cambie el código del reporte.

## 25. HU24 - Señalar un reporte que requiere datos adicionales

Prioridad: Baja · Estado: Pendiente · Revisión: Sebastian

Como Verificador, quiero señalar un reporte que requiere datos adicionales para devolverlo para corrección.

- CA1: El reporte puede marcarse como Requiere información con una nota.
- CA2: El operador puede identificar la nota al abrir el reporte.

## 26. HU25 - Ver cuántos reportes hay por estado

Prioridad: Baja · Estado: Pendiente · Revisión: Fabrizio

Como Supervisor, quiero ver cuántos reportes hay por estado para conocer la carga de revisión.

- CA1: El resumen muestra cantidades de pendientes, en verificación, verificados y descartados.
- CA2: El total coincide con los reportes activos del periodo elegido.

## 27. HU26 - Consultar reportes de un rango de fechas

Prioridad: Baja · Estado: Pendiente · Revisión: Luis

Como Supervisor, quiero consultar reportes de un rango de fechas para analizar un periodo específico.

- CA1: Se pueden elegir fecha inicial y fecha final válidas.
- CA2: Los resultados excluyen reportes fuera del rango seleccionado.

## 28. HU27 - Exportar el listado de reportes

Prioridad: Baja · Estado: Pendiente · Revisión: Adriano

Como Supervisor, quiero exportar el listado de reportes para analizarlo fuera del sistema.

- CA1: La exportación incluye código, fecha, ubicación, magnitud, profundidad y estado.
- CA2: El archivo respeta los filtros activos al momento de exportar.

## 29. HU28 - Consultar los reportes dados de baja

Prioridad: Baja · Estado: Pendiente · Revisión: Sebastian

Como Supervisor, quiero consultar los reportes dados de baja para revisar decisiones anteriores.

- CA1: La vista de archivo incluye solo registros inactivos.
- CA2: Cada registro muestra su código y el estado que tenía antes de la baja.

## 30. HU29 - Restaurar un reporte dado de baja

Prioridad: Baja · Estado: Pendiente · Revisión: Fabrizio

Como Supervisor, quiero restaurar un reporte dado de baja para corregir una baja accidental.

- CA1: La restauración solicita confirmación e identifica el reporte.
- CA2: El reporte restaurado vuelve al listado activo si su código no entra en conflicto.

## 31. HU30 - Ver la distribución de reportes por ubicación

Prioridad: Baja · Estado: Pendiente · Revisión: Luis

Como Supervisor, quiero ver la distribución de reportes por ubicación para identificar zonas con mayor carga de registro.

- CA1: El resumen agrupa reportes activos por ubicación normalizada.
- CA2: La suma de los grupos coincide con los reportes activos considerados.

## 32. HU31 - Consultar la actividad diaria de reportes

Prioridad: Baja · Estado: Pendiente · Revisión: Adriano

Como Supervisor, quiero consultar la actividad diaria de reportes para dar seguimiento a la recepción.

- CA1: La vista muestra el número de reportes creados por día del periodo elegido.
- CA2: Los días sin reportes aparecen con valor cero dentro del rango.

## 33. HU32 - Identificar reportes pendientes desde hace más de un día

Prioridad: Baja · Estado: Pendiente · Revisión: Sebastian

Como Supervisor, quiero identificar reportes pendientes desde hace más de un día para priorizar los atrasados.

- CA1: La vista señala pendientes que superan el umbral configurado.
- CA2: Cada elemento enlaza a su detalle para tomar acción.

## 34. HU33 - Crear cuentas

Prioridad: Baja · Estado: Pendiente · Revisión: Fabrizio

Como Administrador, quiero crear cuentas para operadores para controlar quién registra reportes.

- CA1: Una cuenta nueva exige identificador único y estado activo.
- CA2: La cuenta creada puede aparecer en la lista de usuarios.

## 35. HU34 - Asignar un rol a cada usuario

Prioridad: Baja · Estado: Pendiente · Revisión: Luis

Como Administrador, quiero asignar un rol a cada usuario para limitar acciones según responsabilidad.

- CA1: El rol puede ser Operador, Verificador, Supervisor o Administrador.
- CA2: Los permisos visibles corresponden al rol asignado al iniciar sesión.

## 36. HU35 - Desactivar una cuenta

Prioridad: Baja · Estado: Pendiente · Revisión: Adriano

Como Administrador, quiero desactivar una cuenta para impedir accesos que ya no corresponden.

- CA1: La desactivación requiere confirmación.
- CA2: Una cuenta inactiva no puede iniciar una nueva sesión.

## 37. HU36 - Consultar un registro de acciones

Prioridad: Baja · Estado: Pendiente · Revisión: Sebastian

Como Administrador, quiero consultar un registro de acciones para investigar cambios importantes.

- CA1: El registro identifica acción, usuario, reporte y momento.
- CA2: Las altas, ediciones y bajas quedan incluidas en la consulta.

## 38. HU37 - Adjuntar una fuente de recepción al reporte

Prioridad: Baja · Estado: Pendiente · Revisión: Fabrizio

Como Operador, quiero adjuntar una fuente de recepción al reporte para saber de dónde provino el dato.

- CA1: El reporte permite registrar canal y referencia de origen.
- CA2: La fuente se muestra en el detalle sin confundirse con una validación oficial.

## 39. HU38 - Marcar la ubicación como aproximada

Prioridad: Baja · Estado: Pendiente · Revisión: Luis

Como Operador, quiero marcar la ubicación como aproximada para advertir que necesita corrección.

- CA1: El formulario permite indicar si la ubicación es aproximada.
- CA2: El detalle muestra esa advertencia hasta que se confirme la ubicación.

## 40. HU40 - Descargar un resumen de reportes verificados

Prioridad: Baja · Estado: Pendiente · Revisión: Sebastian

Como Supervisor, quiero descargar un resumen de reportes verificados para compartir resultados de revisión interna.

- CA1: El resumen contiene únicamente reportes con estado Verificado.
- CA2: El documento indica que los datos son de prueba y no constituyen alerta oficial.
