<%@ page pageEncoding="UTF-8" %>
<%@ include file="cabecera.jspf" %>
<nav class="migas"><a href="${pageContext.request.contextPath}/sismos">Reportes sísmicos</a><span>/</span><span><c:out value="${titulo}"/></span></nav>
<div class="encabezado-pagina"><div><p class="sobrelinea">MÓDULO 01 / REPORTES</p><h1><c:out value="${titulo}"/></h1><p class="subtitulo">Los campos marcados con * son obligatorios.</p></div></div>
<section class="tarjeta formulario-tarjeta">
  <c:if test="${not empty form.errores}"><div class="aviso error" role="alert">Revisa los campos señalados antes de guardar.</div></c:if>
  <form method="post" action="${pageContext.request.contextPath}/sismos/${accion}">
    <c:if test="${accion eq 'editar'}"><input type="hidden" name="id" value="${id}"></c:if>
    <div class="form-grid">
      <label>Código *<input type="text" name="codigo" maxlength="40" value="<c:out value='${form.valores.codigo}'/>" placeholder="SIS-2026-004" required><c:if test="${not empty form.errores.codigo}"><span class="campo-error"><c:out value="${form.errores.codigo}"/></span></c:if></label>
      <label>Fecha y hora *<input type="datetime-local" name="fechaHora" value="<c:out value='${form.valores.fechaHora}'/>" required><c:if test="${not empty form.errores.fechaHora}"><span class="campo-error"><c:out value="${form.errores.fechaHora}"/></span></c:if></label>
      <label class="ancho-completo">Ubicación *<input type="text" name="ubicacion" maxlength="120" value="<c:out value='${form.valores.ubicacion}'/>" placeholder="Departamento, provincia o referencia" required><c:if test="${not empty form.errores.ubicacion}"><span class="campo-error"><c:out value="${form.errores.ubicacion}"/></span></c:if></label>
      <label>Magnitud *<input type="number" name="magnitud" min="0" max="10" step="0.1" value="<c:out value='${form.valores.magnitud}'/>" placeholder="0.0" required><c:if test="${not empty form.errores.magnitud}"><span class="campo-error"><c:out value="${form.errores.magnitud}"/></span></c:if></label>
      <label>Profundidad (km) *<input type="number" name="profundidadKm" min="0" max="1000" step="0.1" value="<c:out value='${form.valores.profundidadKm}'/>" placeholder="0.0" required><c:if test="${not empty form.errores.profundidadKm}"><span class="campo-error"><c:out value="${form.errores.profundidadKm}"/></span></c:if></label>
      <label>Estado *<select name="estado" required><option value="">Selecciona un estado</option><c:forEach items="${estados}" var="opcion"><option value="${opcion.name()}" <c:if test="${form.valores.estado eq opcion.name()}">selected</c:if>><c:out value="${opcion.etiqueta}"/></option></c:forEach></select><c:if test="${not empty form.errores.estado}"><span class="campo-error"><c:out value="${form.errores.estado}"/></span></c:if></label>
      <label class="ancho-completo">Observaciones<textarea name="observaciones" maxlength="500" rows="4" placeholder="Información adicional del reporte"><c:out value="${form.valores.observaciones}"/></textarea><c:if test="${not empty form.errores.observaciones}"><span class="campo-error"><c:out value="${form.errores.observaciones}"/></span></c:if></label>
    </div>
    <div class="botonera"><button class="boton primario" type="submit">Guardar reporte</button><a class="boton secundario" href="${pageContext.request.contextPath}/sismos">Cancelar</a></div>
  </form>
</section>
<%@ include file="pie.jspf" %>
