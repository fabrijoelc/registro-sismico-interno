<%@ page pageEncoding="UTF-8" %>
<%@ include file="../sismos/cabecera.jspf" %>
<nav class="migas"><a href="${pageContext.request.contextPath}/ubicaciones">Ubicaciones</a><span>/</span><span>Detalle</span></nav>
<div class="encabezado-pagina"><div><p class="sobrelinea">UBICACIÓN #${ubicacion.id}</p><h1><c:out value="${ubicacion.codigo}"/></h1><p class="subtitulo">Datos compartidos por los reportes asociados mediante una clave foránea.</p></div><span class="estado">Activa</span></div>
<c:if test="${param.guardado eq '1'}"><div class="aviso exito">Ubicación guardada correctamente.</div></c:if>
<section class="tarjeta detalle-tarjeta"><h2>Clasificación geográfica</h2><dl class="detalle-grid"><div><dt>Departamento / región</dt><dd><c:out value="${ubicacion.departamento}"/></dd></div><div><dt>Provincia</dt><dd><c:out value="${ubicacion.provincia}"/></dd></div><div><dt>Distrito</dt><dd><c:out value="${ubicacion.distrito}"/></dd></div><div><dt>ID persistente</dt><dd>${ubicacion.id}</dd></div></dl>
<div class="botonera"><a class="boton primario" href="${pageContext.request.contextPath}/ubicaciones/editar?id=${ubicacion.id}">Editar ubicación</a><a class="boton peligro" href="${pageContext.request.contextPath}/ubicaciones/eliminar?id=${ubicacion.id}">Dar de baja</a><a class="boton secundario" href="${pageContext.request.contextPath}/ubicaciones">Volver</a></div></section>
<%@ include file="../sismos/pie.jspf" %>
