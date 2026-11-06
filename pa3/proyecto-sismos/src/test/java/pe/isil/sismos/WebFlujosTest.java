package pe.isil.sismos;

import pe.isil.sismos.repository.*;
import pe.isil.sismos.service.ImportacionService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WebFlujosTest {
 @Autowired MockMvc mvc;
 @Autowired ImportacionService imports;
 @Autowired UbigeoRepository ubigeos;
 @Autowired TipoAlertaRepository alertas;
 @Autowired TipoDanoRepository tipos;
 @Autowired SismoRepository sismos;
 @Autowired ReporteDanoRepository danos;
 private MockHttpSession session;
 private String token;
 @BeforeEach void preparar()throws Exception{
  danos.deleteAllInBatch();sismos.deleteAllInBatch();tipos.deleteAllInBatch();alertas.deleteAllInBatch();ubigeos.deleteAllInBatch();
  session=new MockHttpSession();token="token-de-prueba";session.setAttribute("csrf",token);
 }
 private void bases()throws Exception{imports.importar("UBIGEO",Files.readAllBytes(Path.of("datos/ubigeo.csv")));imports.importar("ALERTA",Files.readAllBytes(Path.of("datos/tipo_alerta.csv")));imports.importar("DANO",Files.readAllBytes(Path.of("datos/tipo_dano.csv")));}
 private org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder nuevo(){
  return post("/sismos/nuevo").session(session).param("_csrf",token).param("codigo","SIS-WEB-001").param("fechaHora",LocalDateTime.now(ZoneId.of("America/Lima")).minusHours(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))).param("magnitud","4.55").param("profundidadKm","35.50").param("referencia","A 15 km al sur de Lima").param("ubigeoId",ubigeos.findByCodigo("15").orElseThrow().getId().toString()).param("tipoAlertaId",alertas.findByCodigo("INFO").orElseThrow().getId().toString()).param("canal","LLAMADA").param("estado","PENDIENTE");
 }
 @Test void pantallasSinCatalogosSeMuestranSinErrores()throws Exception{
  for(String ruta:new String[]{"/sismos","/sismos/nuevo","/catalogos/ubigeos","/catalogos/alertas","/catalogos/danos","/catalogos/ubigeos/nuevo","/catalogos/alertas/nuevo","/catalogos/danos/nuevo","/importaciones"})mvc.perform(get(ruta).session(session)).andExpect(status().isOk());
 }
 @Test void postSinTokenNoModificaNada()throws Exception{mvc.perform(post("/catalogos/ubigeos/nuevo").session(session).param("codigo","99").param("nombre","No debe guardarse").param("tipo","DEPARTAMENTO").param("estado","ACTIVO")).andExpect(status().isForbidden());assertThat(ubigeos.count()).isZero();}
 @Test void flujoWebSismoYDanioMuestraRelacionYEscapeHtml()throws Exception{
  bases();mvc.perform(nuevo()).andExpect(status().is3xxRedirection());Long id=sismos.findByCodigo("SIS-WEB-001").orElseThrow().getId();
  mvc.perform(get("/sismos/"+id).session(session)).andExpect(status().isOk()).andExpect(content().string(containsString("SIS-WEB-001")));
  mvc.perform(get("/sismos/"+id+"/danos/nuevo").session(session)).andExpect(status().isOk());
  mvc.perform(post("/sismos/"+id+"/danos/nuevo").session(session).param("_csrf",token).param("tipoDanoId",tipos.findByCodigo("DAN-02").orElseThrow().getId().toString()).param("cantidadAfectada","3").param("descripcion","<script>alert(1)</script> Tres viviendas de prueba.")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/sismos/"+id+"#danos"));
  mvc.perform(get("/sismos/"+id).session(session)).andExpect(status().isOk()).andExpect(content().string(containsString("&lt;script&gt;"))).andExpect(content().string(not(containsString("<script>alert(1)</script>"))));assertThat(danos.count()).isOne();
 }
 @Test void beanValidationRenderizaErroresYConservaFormulario()throws Exception{bases();mvc.perform(nuevo().with(request->{request.setParameter("magnitud","11");return request;})).andExpect(status().isOk()).andExpect(model().attributeHasFieldErrors("form","magnitud")).andExpect(content().string(containsString("La magnitud no puede superar 10.")));assertThat(sismos.count()).isZero();}
 @Test void servicioRechazaRelacionInactivaAunqueSeManipuleHtml()throws Exception{bases();var u=ubigeos.findByCodigo("15").orElseThrow();u.setEstado(pe.isil.sismos.model.EstadoMaestro.INACTIVO);ubigeos.saveAndFlush(u);mvc.perform(nuevo()).andExpect(status().isOk()).andExpect(model().attributeHasFieldErrors("form","ubigeoId"));assertThat(sismos.count()).isZero();}
 @Test void importacionMultipartMuestraConteoReimportacionSinDuplicar()throws Exception{
  byte[] archivo=Files.readAllBytes(Path.of("datos/ubigeo.csv"));MockMultipartFile csv=new MockMultipartFile("archivo","ubigeo.csv","text/csv",archivo);
  mvc.perform(multipart("/importaciones").file(csv).session(session).param("_csrf",token).param("tipo","UBIGEO")).andExpect(status().isOk()).andExpect(model().attributeExists("resultado"));assertThat(ubigeos.count()).isEqualTo(25);
  mvc.perform(multipart("/importaciones").file(csv).session(session).param("_csrf",token).param("tipo","UBIGEO")).andExpect(status().isOk()).andExpect(model().attributeExists("resultado"));assertThat(ubigeos.count()).isEqualTo(25);
 }
 @Test void importacionMultipartInvalidaMuestraErroresSinFilasParciales()throws Exception{
  MockMultipartFile csv=new MockMultipartFile("archivo","malo.csv","text/csv","codigo,nombre,tipo,estado\n01,Amazonas,DEPARTAMENTO,ACTIVO\n02,Áncash,OTRO,ACTIVO\n".getBytes(java.nio.charset.StandardCharsets.UTF_8));
  mvc.perform(multipart("/importaciones").file(csv).session(session).param("_csrf",token).param("tipo","UBIGEO")).andExpect(status().isOk()).andExpect(model().attributeExists("errores")).andExpect(content().string(containsString("Fila 3")));assertThat(ubigeos.count()).isZero();
 }
 @Test void noEncontradoDevuelve404Legible()throws Exception{mvc.perform(get("/sismos/999999").session(session)).andExpect(status().isNotFound()).andExpect(content().string(containsString("no existe")));}
 @Test void estadoCatalogoYEdicionRenderizan()throws Exception{bases();Long id=ubigeos.findByCodigo("02").orElseThrow().getId();mvc.perform(get("/catalogos/ubigeos/"+id+"/editar").session(session)).andExpect(status().isOk());mvc.perform(get("/catalogos/ubigeos/"+id+"/estado").session(session)).andExpect(status().isOk());}
}
