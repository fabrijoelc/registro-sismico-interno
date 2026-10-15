<%@ page pageEncoding="UTF-8" %>
<%@ include file="../sismos/cabecera.jspf" %>
<section class="tarjeta confirmacion"><div class="alerta-icono">!</div><p class="sobrelinea">CONFIRMACIÓN / CATÁLOGO</p><h1>¿Dar de baja esta ubicación?</h1><p><strong><c:out value="${ubicacion.codigo}"/></strong> · <c:out value="${ubicacion.nombreCompleto}"/></p><p>Dejará de estar disponible para nuevos reportes. Si tiene reportes activos asociados, la operación se bloqueará. Su historial se conserva.</p>
<c:if test="${not empty error}"><div class="aviso error" role="alert"><c:out value="${error}"/></div></c:if>
<form method="post" action="${pageContext.request.contextPath}/ubicaciones/eliminar"><input type="hidden" name="id" value="${ubicacion.id}"><input type="hidden" name="csrf" value="${sessionScope.csrf}"><div class="botonera"><button class="boton peligro" type="submit">Confirmar baja</button><a class="boton secundario" href="${pageContext.request.contextPath}/ubicaciones">Cancelar</a></div></form></section>
<%@ include file="../sismos/pie.jspf" %>
