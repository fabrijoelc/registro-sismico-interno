<%@ page pageEncoding="UTF-8" %>
<%@ include file="../sismos/cabecera.jspf" %>
<nav class="migas"><a href="${pageContext.request.contextPath}/ubicaciones">Ubicaciones</a><span>/</span><span>Formulario</span></nav>
<div class="encabezado-pagina"><div><p class="sobrelinea">Catálogo geográfico</p><h1>${accion eq 'editar' ? 'Editar ubicación' : 'Nueva ubicación'}</h1><p class="subtitulo">Todos los campos son obligatorios. El código identifica una ubicación del catálogo.</p></div></div>
<section class="tarjeta formulario-tarjeta">
<c:if test="${not empty errores}"><div class="aviso error" role="alert">Revisa los campos señalados.</div></c:if>
<form method="post" action="${pageContext.request.contextPath}/ubicaciones/${accion}">
<input type="hidden" name="csrf" value="${sessionScope.csrf}">
<c:if test="${accion eq 'editar'}"><input type="hidden" name="id" value="${id}"></c:if>
<div class="form-grid">
<label>Código *<input name="codigo" maxlength="20" value="<c:out value='${valores.codigo}'/>" required><span class="campo-error"><c:out value="${errores.codigo}"/></span></label>
<label>Departamento / región *<input name="departamento" maxlength="80" value="<c:out value='${valores.departamento}'/>" required><span class="campo-error"><c:out value="${errores.departamento}"/></span></label>
<label>Provincia *<input name="provincia" maxlength="80" value="<c:out value='${valores.provincia}'/>" required><span class="campo-error"><c:out value="${errores.provincia}"/></span></label>
<label>Distrito *<input name="distrito" maxlength="80" value="<c:out value='${valores.distrito}'/>" required><span class="campo-error"><c:out value="${errores.distrito}"/></span></label>
</div><div class="botonera"><button class="boton primario" type="submit">Guardar ubicación</button><a class="boton secundario" href="${pageContext.request.contextPath}/ubicaciones">Cancelar</a></div></form></section>
<%@ include file="../sismos/pie.jspf" %>
