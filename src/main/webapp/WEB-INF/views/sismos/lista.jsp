<%@ include file="cabecera.jspf" %>
<div class="encabezado-pagina">
  <div><p class="sobrelinea">MÓDULO 01 / REPORTES</p><h1>Reportes sísmicos</h1><p class="subtitulo">Consulta y actualización de reportes iniciales recibidos por operadores.</p></div>
  <a class="boton primario" href="${pageContext.request.contextPath}/sismos/nuevo">+ Nuevo reporte</a>
</div>
<c:if test="${param.eliminado eq '1'}"><div class="aviso exito" role="status">Reporte dado de baja correctamente.</div></c:if>
<section class="tarjeta">
  <form class="filtros" method="get" action="${pageContext.request.contextPath}/sismos">
    <label>Buscar por código<input type="search" name="q" placeholder="Ej. SIS-2026-001" value="<c:out value='${q}'/>"></label>
    <label>Estado<select name="estado"><option value="">Todos los estados</option><c:forEach items="${estados}" var="opcion"><option value="${opcion.name()}" <c:if test="${estadoSeleccionado eq opcion.name()}">selected</c:if>><c:out value="${opcion.etiqueta}"/></option></c:forEach></select></label>
    <button class="boton secundario" type="submit">Aplicar filtros</button>
    <a class="enlace-limpiar" href="${pageContext.request.contextPath}/sismos">Limpiar</a>
  </form>
  <div class="tabla-scroll"><table><thead><tr><th>Código</th><th>Fecha y hora</th><th>Ubicación</th><th>Magnitud</th><th>Estado</th><th>Acciones</th></tr></thead><tbody>
    <c:forEach items="${sismos}" var="sismo"><tr><td class="codigo"><c:out value="${sismo.codigo}"/></td><td><c:out value="${sismo.fechaHoraTexto}"/></td><td><c:out value="${sismo.ubicacion}"/></td><td><c:out value="${sismo.magnitud}"/></td><td><span class="estado"><c:out value="${sismo.estado.etiqueta}"/></span></td><td class="acciones"><a href="${pageContext.request.contextPath}/sismos/ver?id=${sismo.id}">Ver</a><a href="${pageContext.request.contextPath}/sismos/editar?id=${sismo.id}">Editar</a><a class="peligro-texto" href="${pageContext.request.contextPath}/sismos/eliminar?id=${sismo.id}">Eliminar</a></td></tr></c:forEach>
    <c:if test="${empty sismos}"><tr><td colspan="6" class="vacio">No hay reportes para los filtros seleccionados.</td></tr></c:if>
  </tbody></table></div>
  <div class="tarjeta-pie"><c:out value="${fn:length(sismos)}"/> reporte(s) visible(s)</div>
</section>
<%@ include file="pie.jspf" %>
