<%@ page pageEncoding="UTF-8" %>
<%@ include file="../sismos/cabecera.jspf" %>
<div class="encabezado-pagina"><div><p class="sobrelinea">MÓDULO 02 / CATÁLOGO GEOGRÁFICO</p><h1>Ubicaciones</h1><p class="subtitulo">Una región consistente para cada reporte. HU10 · relación con Sismo.</p></div><a class="boton primario" href="${pageContext.request.contextPath}/ubicaciones/nueva">+ Nueva ubicación</a></div>
<c:if test="${param.baja eq '1'}"><div class="aviso exito">Ubicación dada de baja correctamente.</div></c:if>
<section class="tarjeta"><div class="tabla-scroll"><table><thead><tr><th>Código</th><th>Departamento</th><th>Provincia</th><th>Distrito</th><th>Acciones</th></tr></thead><tbody>
<c:forEach items="${ubicaciones}" var="u"><tr><td class="codigo"><c:out value="${u.codigo}"/></td><td><c:out value="${u.departamento}"/></td><td><c:out value="${u.provincia}"/></td><td><c:out value="${u.distrito}"/></td><td class="acciones"><a href="${pageContext.request.contextPath}/ubicaciones/ver?id=${u.id}">Ver</a><a href="${pageContext.request.contextPath}/ubicaciones/editar?id=${u.id}">Editar</a><a class="peligro-texto" href="${pageContext.request.contextPath}/ubicaciones/eliminar?id=${u.id}">Dar de baja</a></td></tr></c:forEach>
<c:if test="${empty ubicaciones}"><tr><td colspan="5" class="vacio">No hay ubicaciones activas. Registra una para asociarla a los reportes.</td></tr></c:if>
</tbody></table></div><div class="tarjeta-pie">${fn:length(ubicaciones)} ubicación(es) activa(s). No se eliminan físicamente los datos históricos.</div></section>
<%@ include file="../sismos/pie.jspf" %>
