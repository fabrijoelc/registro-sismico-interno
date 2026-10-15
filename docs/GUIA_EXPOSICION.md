# Guion PA2 · Grupo 8 · objetivo: 11 minutos

Mostrar PDF + IDE + aplicación real. No hace falta un PPT adicional. Figma no es el centro de PA2: el docente pide MVC, JPA y persistencia.

La distribución asigna responsabilidades de revisión y sustentación. Cada integrante debe ejecutar su parte antes de grabar. No afirmar reuniones, commits o pruebas individuales que no se hayan realizado.

| Turno | Persona | Tiempo | Qué debe verse |
| --- | --- | --- | --- |
| 1 | Sebastian | 0:00-2:00 | PDF: objetivo, backlog, 11 implementadas y 29 pendientes |
| 2 | Adriano | 2:00-4:30 | IDE: MVC, entidades, relación y transacción |
| 3 | Fabrizio | 4:30-7:00 | Aplicación: CRUD de Ubicación y conexión con Sismo |
| 4 | Luis | 7:00-11:00 | Aplicación: CRUD de Sismo, validación, persistencia y cierre |

## Antes de grabar

- Iniciar Docker Desktop y ejecutar docker compose up -d --build.
- Abrir http://localhost:18083/sismos-pa2/.
- Abrir Sismo.java, Ubicacion.java, JpaRepository.java y SismoRepository.java en el IDE.
- Tener el informe en las secciones backlog y arquitectura.
- Usar códigos libres: UB-EXPO-01, UB-EXPO-02 y SIS-EXPO-01. Si ya se usaron, cambiar sufijo: no se reutilizan códigos de bajas.
- Datos: 01/10/2026 09:30, magnitud 4.5, profundidad 25.5, latitud -9.527790 y longitud -77.527780.
- Grabar entre 8 y 12 minutos, incluidas transiciones y acciones. Micrófono de los cuatro.
- No mostrar claves ni datos ajenos.

## 1. Sebastian · contexto y backlog

**Mostrar:** portada, objetivo y tabla de las historias del incremento.

“Presentamos la segunda entrega del Registro interno de reportes sísmicos. Partimos del mismo problema del caso: cuando la información llega por diferentes canales y queda dispersa, es más difícil localizar un reporte, corregirlo y conocer su estado.

En PA1 ya teníamos registro, consulta, edición y baja. Sin embargo, los datos se guardaban en memoria. El objetivo de PA2 es mantener la estructura MVC y persistir los datos en una base relacional usando JPA.

Nuestro backlog conserva las 40 historias originales. El incremento acumula once: las ocho de PA1, migradas a persistencia, y tres seleccionadas del backlog. Son HU09, para identificar el canal de recepción; HU10, para clasificar la ubicación desde un catálogo; y HU39, para registrar coordenadas.

Priorizamos estas tres porque completan el contexto del reporte y se relacionan directamente con Sismo. No necesitamos implementar todo el sistema ni añadir módulos ajenos al caso. El catálogo de ubicaciones tiene un CRUD completo y permite evidenciar una relación entre entidades.

Las 29 historias restantes siguen pendientes. Por ejemplo: adjuntos, cuentas y roles, auditoría y análisis estadístico. No las presentamos como terminadas. La sustentación está distribuida en cuatro bloques: backlog, arquitectura, catálogo y flujo de reportes.”

**Acción:** señalar HU09, HU10 y HU39; mostrar una pendiente, sin leer las 40.
**Enlace:** “Ahora veremos cómo esta selección se refleja en el código.”

## 2. Adriano · MVC, entidades y transacciones

**Mostrar:** paquetes; después Ubicacion, Sismo y JpaRepository.

“La aplicación conserva Java Web con Servlets y JSP. La vista está en WEB-INF/views y presenta formularios y tablas. Los Servlets reciben solicitudes, validan parámetros y deciden qué vista mostrar o a qué ruta redirigir. Los repositorios ejecutan las operaciones de persistencia. Así evitamos colocar SQL o almacenamiento dentro de las JSP.

Tenemos dos entidades principales: Sismo y Ubicacion. Ambas usan Entity, Id y GeneratedValue con estrategia IDENTITY. La base genera la clave técnica. El código visible es otro campo, con restricción de unicidad; no sustituye al ID.

Cada sismo pertenece a una ubicación y una ubicación puede utilizarse en varios sismos. En Java se expresa mediante ManyToOne y JoinColumn; en MySQL mediante ubicacion_id y una clave foránea. No hay eliminación en cascada: una baja del catálogo no debe eliminar reportes.

El listener crea una EntityManagerFactory para la aplicación. Cada operación abre su propio EntityManager y lo cierra al terminar. No compartimos ese objeto entre peticiones concurrentes.

Las escrituras pasan por este método: inicia la transacción, ejecuta el trabajo y confirma. Si ocurre un error, hace rollback. Para crear usamos persist; para buscar, find; para editar Sismo, merge. Las bajas conservan la fila y cambian activo a falso dentro de una transacción.

La búsqueda usa JPQL con parámetros, trabajando con entidades y atributos. El esquema SQL coincide con las anotaciones y se valida al arrancar: la aplicación no borra ni recrea las tablas.”

**Acción:** señalar anotaciones, begin/commit/rollback y consulta JPQL. No leer todo el código.
**Enlace:** “Fabrizio mostrará el catálogo que hace posible esta relación.”

## 3. Fabrizio · catálogo de ubicaciones

**Mostrar:** aplicación real, no Figma.

“Este es el catálogo geográfico. Su propósito es que la región no dependa únicamente de una referencia libre. Dos operadores pueden redactar referencias diferentes y aun así asociar el reporte a la misma ubicación.

Primero creamos una ubicación con código único, departamento, provincia y distrito. Para la demostración usamos Áncash, Huaraz e Independencia. Al guardar, se abre el detalle y aparece el ID asignado por la base.”

**Acción:** crear UB-EXPO-01; abrir detalle.

“Ahora editamos el distrito a Huaraz. El formulario carga el valor anterior y, al guardar, vemos el cambio. Con esto demostramos alta, lectura y actualización del catálogo.”

**Acción:** editar y consultar.

“Para completar el CRUD podemos dar de baja esta ubicación de prueba. La aplicación solicita confirmación. Si cancelamos, no cambia nada; si confirmamos, deja de aparecer en el listado. Es una baja lógica: el registro histórico permanece.”

**Acción:** cancelar una vez y luego confirmar. Crear UB-EXPO-02 con Áncash/Huaraz/Huaraz para Luis.

“Dejo una segunda ubicación para asociarla a un reporte. En el formulario de Sismo aparece en el selector. Se mantiene separada de la referencia textual: por ejemplo, ‘a quince kilómetros al sur de Huaraz’. Una cosa es el catálogo y otra el detalle de referencia.

Hay además una protección de integridad: si una ubicación tiene reportes activos, se bloquea su baja. Primero deben reasignarse o darse de baja esos reportes. Esto evita datos inconsistentes.

La región cumple HU10; el canal y las coordenadas se completan en el flujo que Luis demostrará ahora.”

## 4. Luis · caso completo y cierre

“Vamos a registrar un reporte de demostración. Ingresamos código, fecha y hora, referencia textual, magnitud, profundidad y estado. Seleccionamos la ubicación recién creada, el canal de recepción y las coordenadas del ejemplo.”

**Acción:** crear SIS-EXPO-01 con UB-EXPO-02.

“El detalle muestra el canal y la región separados de la referencia. La ubicación está relacionada por ID, no copiada como texto independiente. Aquí se completan los criterios de las tres historias nuevas.”

**Acción:** editar canal a Correo y estado a Verificado; guardar; buscar código y filtrar Verificado.

“Corregimos el estado y el canal. Al consultar vemos los cambios. La búsqueda y el filtro se ejecutan sobre datos persistidos mediante JPQL.

Las validaciones evitan datos inválidos. Una latitud de 91 no es válida: el rango es de menos 90 a 90. La misma regla se verifica en el servidor aunque se omita la validación del navegador.”

**Acción:** intentar latitud 91. Para mostrar el rechazo del servidor, usar la captura del informe; no hace falta abrir DevTools en vivo.

“Tampoco se permite dar de baja la ubicación mientras este reporte esté activo.”

**Acción:** intentar baja de UB-EXPO-02; mostrar bloqueo.

“La información ya no vive solo en memoria. En las pruebas se reiniciaron MySQL y Tomcat en orden y se recuperó el mismo reporte, con su ID y relación. La evidencia está en el informe y en el archivo de resultados.”

**Acción:** mostrar evidencia de reinicio o ejecutar secuencia README si queda tiempo. No afirmar que se hizo en vivo si solo se muestra la evidencia.

“Finalmente damos de baja el reporte. Confirmamos y desaparece del listado activo; la fila continúa en la base como inactiva. Completamos crear, consultar, actualizar y dar de baja.

Esta entrega no es el sistema completo. Demuestra once historias acumuladas, dos entidades relacionadas y persistencia real. Las siguientes entregas incorporarán historias pendientes manteniendo la trazabilidad del backlog.”

## Preguntas rápidas

- ¿Por qué no hay Estacion? Es sugerida, no obligatoria. Ubicacion responde a HU10 y tiene CRUD completo.
- ¿Dónde está JPA? Entidades, persistence.xml, listener y repositorios; Hibernate es el proveedor.
- ¿Se pierde al reiniciar? No. MySQL y su volumen conservan las filas.
- ¿Eliminar es DELETE? Se decidió baja lógica transaccional para conservar historia. No se usa remove.
- ¿Verificado es oficial? No. Es un estado académico. Sin integración con IGP ni verificación automática.
- ¿Seguridad por roles? Aún no. Hay validación, escape y CSRF; login/permisos siguen pendientes.
- ¿Quién hizo qué? Explicar aportes y revisiones realmente realizados; no inventar commits ni reuniones.
