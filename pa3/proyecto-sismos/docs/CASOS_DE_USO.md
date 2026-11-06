# Casos de uso del incremento PA3

## CU-01. Registrar sismo

Actor principal: Operador. Propósito: centralizar un reporte, ubicarlo y clasificar su alerta.

Precondiciones: aplicación y MySQL disponibles; al menos un ubigeo y un tipo de alerta activos. El código propuesto no debe haberse usado, incluso por un sismo inactivo.

| Paso | Acción del actor | Respuesta del sistema |
| --- | --- | --- |
| 1 | Abre Reportes y elige Nuevo reporte. | Muestra el formulario y los maestros activos. |
| 2 | Escribe código, fecha y hora, referencia, magnitud, profundidad, canal y estado de revisión. | Conserva los datos introducidos mientras se valida el formulario. |
| 3 | Selecciona ubigeo y tipo de alerta. Opcionalmente completa ambas coordenadas y observaciones. | Relaciona las selecciones por identificadores, sin duplicar sus nombres en Sismo. |
| 4 | Pulsa Guardar reporte. | Bean Validation comprueba las entradas; el servicio revisa código único y referencias activas. |
| 5 | Los datos son válidos. | Guarda dentro de una transacción mediante JpaRepository y redirige al detalle. |
| 6 | Consulta el detalle. | Muestra el código, datos recibidos, clasificación de alerta, ubigeo y sección de daños. |

Postcondición de éxito: un Sismo persistente con dos referencias válidas; puede consultarse tras reiniciar la aplicación.

| Flujo alterno | Condición | Resolución |
| --- | --- | --- |
| A1 | No hay maestros activos. | El formulario indica que se carguen o activen los catálogos antes de guardar. |
| A2 | Faltan campos, la fecha es futura o un número está fuera de rango. | Se muestran errores; no se guarda el reporte y el actor corrige los datos. |
| A3 | Se escribe un código ya utilizado. | El servicio rechaza el registro; se utiliza otro código. |
| A4 | Se envía un identificador inexistente o un maestro se desactiva antes de guardar. | El servicio verifica la referencia actual y rechaza la operación. |
| A5 | Solo se completa latitud o longitud. | Se exige completar ambas o dejar ambas vacías. |
| A6 | Se cancela el formulario. | Regresa al listado sin persistir cambios. |
| A7 | Hay un error al persistir. | La transacción revierte la operación; no se presenta éxito. |

La alerta es manual según el catálogo. El sistema no predice sismos ni decide una emergencia oficial. Estado de revisión y tipo de alerta representan conceptos distintos.

## CU-02. Registrar reporte de daño asociado

Actor principal: Operador. Propósito: documentar una afectación del sismo consultado, usando una clasificación común.

Precondiciones: existe un Sismo activo y hay un TipoDano activo. Se ha comprobado a qué evento corresponde el dato recibido.

| Paso | Acción del actor | Respuesta del sistema |
| --- | --- | --- |
| 1 | Abre el detalle de un sismo. | Muestra el código del evento y los daños asociados. |
| 2 | Elige Registrar daño. | Abre el formulario conservando el sismo de destino. |
| 3 | Selecciona el tipo de daño, escribe cantidad afectada y descripción. | Presenta los tipos activos y los errores de formato si los hubiera. |
| 4 | Guarda el reporte de daño. | Valida cantidad positiva entera, descripción y referencias; registra la fecha y hora. |
| 5 | La operación es válida. | Persiste ReporteDano en una transacción con referencias a Sismo y TipoDano. |
| 6 | Regresa al detalle del sismo. | Muestra el daño guardado, su tipo, cantidad, descripción y fecha. |

Postcondición de éxito: el daño queda asociado exclusivamente al sismo elegido y puede consultarse tras reiniciar.

| Flujo alterno | Condición | Resolución |
| --- | --- | --- |
| B1 | Cantidad cero, negativa, decimal o vacía. | Se muestra el error y no se crea un daño. |
| B2 | Descripción vacía o mayor al límite del formulario. | Se solicita corregirla, conservando el resto de los campos. |
| B3 | Tipo de daño inexistente o inactivo. | El servicio rechaza la referencia aunque se haya enviado fuera de la interfaz. |
| B4 | Sismo inexistente o dado de baja. | Se impide registrar el daño sobre ese evento. |
| B5 | Se cancela. | Regresa al detalle sin agregar una fila. |
| B6 | Falla la base de datos durante el guardado. | Se revierte la operación sin modificar los daños existentes. |

La descripción precisa la unidad de la cantidad: por ejemplo, 3 viviendas con grietas. No se calcula un total combinado entre sectores distintos. La interfaz no incluye eliminación física de reportes de daño en este incremento.

## Flujo de apoyo. Importar maestros desde CSV

El Administrador abre Importar catálogos, selecciona el tipo y envía un CSV UTF-8 con las cabeceras indicadas. El servicio analiza el archivo completo, comprueba códigos, campos, enumeraciones y duplicados, y persiste todo en una transacción. Una segunda carga idéntica omite registros existentes sin duplicarlos. Los conflictos se informan con su línea y no dejan cargas parciales. La interfaz muestra el resultado real de cada carga.

El curso entrega 25 ubigeos, 4 alertas y 7 tipos de daño. Esas cifras son controles para comprobar los archivos recibidos; el formulario también permite catálogos válidos de otro tamaño y registro manual.
