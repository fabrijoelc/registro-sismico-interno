<%@ page pageEncoding="UTF-8" %>
<%@ include file="cabecera.jspf" %>
<nav class="migas"><a href="${pageContext.request.contextPath}/sismos">Reportes sísmicos</a><span>/</span><span><c:out value="${titulo}"/></span></nav>
<div class="encabezado-pagina"><div><p class="sobrelinea">Gestión de reportes</p><h1><c:out value="${titulo}"/></h1><p class="subtitulo">Los campos marcados con * son obligatorios.</p></div></div>
<section class="tarjeta formulario-tarjeta">
  <c:if test="${not empty form.errores}"><div class="aviso error" role="alert">Revisa los campos señalados antes de guardar.</div></c:if>
  <form method="post" action="${pageContext.request.contextPath}/sismos/${accion}">
    <c:if test="${accion eq 'editar'}"><input type="hidden" name="id" value="${id}"></c:if>
    <input type="hidden" name="csrf" value="${sessionScope.csrf}">
    <div class="form-grid">
      <label>Código *<input type="text" name="codigo" maxlength="40" value="<c:out value='${form.valores.codigo}'/>" placeholder="SIS-2026-004" required><c:if test="${not empty form.errores.codigo}"><span class="campo-error"><c:out value="${form.errores.codigo}"/></span></c:if></label>
      <label>Fecha y hora *<input type="datetime-local" name="fechaHora" value="<c:out value='${form.valores.fechaHora}'/>" required><c:if test="${not empty form.errores.fechaHora}"><span class="campo-error"><c:out value="${form.errores.fechaHora}"/></span></c:if></label>
      <label class="ancho-completo">Referencia de ubicación *<input type="text" name="ubicacion" maxlength="120" value="<c:out value='${form.valores.ubicacion}'/>" placeholder="Departamento, provincia o referencia" required><c:if test="${not empty form.errores.ubicacion}"><span class="campo-error"><c:out value="${form.errores.ubicacion}"/></span></c:if></label>
      <label>Magnitud *<input type="number" name="magnitud" min="0" max="10" step="0.01" value="<c:out value='${form.valores.magnitud}'/>" placeholder="0.0" required><c:if test="${not empty form.errores.magnitud}"><span class="campo-error"><c:out value="${form.errores.magnitud}"/></span></c:if></label>
      <label>Profundidad (km) *<input type="number" name="profundidadKm" min="0" max="1000" step="0.1" value="<c:out value='${form.valores.profundidadKm}'/>" placeholder="0.0" required><c:if test="${not empty form.errores.profundidadKm}"><span class="campo-error"><c:out value="${form.errores.profundidadKm}"/></span></c:if></label>
      <label>Estado *<select name="estado" required><option value="">Selecciona un estado</option><c:forEach items="${estados}" var="opcion"><option value="${opcion.name()}" <c:if test="${form.valores.estado eq opcion.name()}">selected</c:if>><c:out value="${opcion.etiqueta}"/></option></c:forEach></select><c:if test="${not empty form.errores.estado}"><span class="campo-error"><c:out value="${form.errores.estado}"/></span></c:if></label>
      <div class="ancho-completo seccion-form"><h2>Clasificación y procedencia</h2><p>Selecciona la ubicación y el medio por el que recibiste el reporte.</p></div>
      <label class="ancho-completo">Ubicación del catálogo *<select name="ubicacionId" required><option value="">Selecciona una ubicación</option><c:forEach items="${ubicaciones}" var="u"><option value="${u.id}" <c:if test="${form.valores.ubicacionId eq u.id}">selected</c:if>><c:out value="${u.nombreCompleto}"/></option></c:forEach></select><span class="campo-error"><c:out value="${form.errores.ubicacionId}"/></span><a href="${pageContext.request.contextPath}/ubicaciones" target="_blank" rel="noopener">Administrar catálogo (abre otra pestaña)</a></label>
      <label>Canal de recepción *<select name="canal" required><option value="">Selecciona un canal</option><c:forEach items="${canales}" var="c"><option value="${c.name()}" <c:if test="${form.valores.canal eq c.name()}">selected</c:if>><c:out value="${c.etiqueta}"/></option></c:forEach></select><span class="campo-error"><c:out value="${form.errores.canal}"/></span></label>
      <div class="ancho-completo seccion-form"><h2>Coordenadas verificadas</h2><p>Estos datos son opcionales. Si los incluyes, completa latitud y longitud. Revisa su exactitud antes de guardar: el sistema no comprueba la ubicación en un mapa.</p></div>
      <label>Latitud<input type="number" name="latitud" min="-90" max="90" step="0.000001" value="<c:out value='${form.valores.latitud}'/>" placeholder="-12.046374"><span class="campo-error"><c:out value="${form.errores.latitud}"/></span></label>
      <label>Longitud<input type="number" name="longitud" min="-180" max="180" step="0.000001" value="<c:out value='${form.valores.longitud}'/>" placeholder="-77.042793"><span class="campo-error"><c:out value="${form.errores.longitud}"/></span></label>
      <label class="ancho-completo">Observaciones<textarea name="observaciones" maxlength="500" rows="4" placeholder="Información adicional del reporte"><c:out value="${form.valores.observaciones}"/></textarea><c:if test="${not empty form.errores.observaciones}"><span class="campo-error"><c:out value="${form.errores.observaciones}"/></span></c:if></label>
    </div>
    <div class="botonera"><button class="boton primario" type="submit">Guardar reporte</button><a class="boton secundario" href="${pageContext.request.contextPath}/sismos">Cancelar</a></div>
  </form>
</section>
<%@ include file="pie.jspf" %>
