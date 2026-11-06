package pe.isil.sismos;

import pe.isil.sismos.dto.*;
import pe.isil.sismos.model.*;
import pe.isil.sismos.repository.*;
import pe.isil.sismos.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class FlujosIntegradosTest {
 @Autowired ImportacionService importaciones;
 @Autowired CatalogoService catalogos;
 @Autowired SismoService sismos;
 @Autowired ReporteDanoService danos;
 @Autowired UbigeoRepository ubigeos;
 @Autowired TipoAlertaRepository alertas;
 @Autowired TipoDanoRepository tiposDano;
 @Autowired SismoRepository sismoRepo;
 @Autowired ReporteDanoRepository danoRepo;

 @BeforeEach void limpiar(){danoRepo.deleteAllInBatch();sismoRepo.deleteAllInBatch();tiposDano.deleteAllInBatch();alertas.deleteAllInBatch();ubigeos.deleteAllInBatch();}
 private byte[] bytes(String csv){return csv.getBytes(StandardCharsets.UTF_8);}
 private void importarBases()throws Exception{importaciones.importar("UBIGEO",Files.readAllBytes(Path.of("datos/ubigeo.csv")));importaciones.importar("ALERTA",Files.readAllBytes(Path.of("datos/tipo_alerta.csv")));importaciones.importar("DANO",Files.readAllBytes(Path.of("datos/tipo_dano.csv")));}
 private SismoForm valido(){
  SismoForm f=new SismoForm();f.setCodigo("SIS-TEST-001");f.setFechaHora(LocalDateTime.now(ZoneId.of("America/Lima")).minusHours(1));f.setMagnitud(new BigDecimal("4.5"));f.setProfundidadKm(new BigDecimal("35.50"));f.setReferencia("A 15 km al sur de Lima");f.setUbigeoId(ubigeos.findByCodigo("15").orElseThrow().getId());f.setTipoAlertaId(alertas.findByCodigo("INFO").orElseThrow().getId());f.setCanal("LLAMADA");f.setEstado("PENDIENTE");return f;
 }
 private ReporteDanoForm danoValido(){ReporteDanoForm f=new ReporteDanoForm();f.setTipoDanoId(tiposDano.findByCodigo("DAN-02").orElseThrow().getId());f.setCantidadAfectada(3);f.setDescripcion("Tres viviendas con fisuras; información de demostración.");return f;}

 @Test void importaArchivosOriginalesConConteosYConservaCerosYTildes()throws Exception{
  var u=importaciones.importar("UBIGEO",Files.readAllBytes(Path.of("datos/ubigeo.csv")));
  var a=importaciones.importar("ALERTA",Files.readAllBytes(Path.of("datos/tipo_alerta.csv")));
  var d=importaciones.importar("DANO",Files.readAllBytes(Path.of("datos/tipo_dano.csv")));
  assertThat(u.getImportados()).isEqualTo(25);assertThat(a.getImportados()).isEqualTo(4);assertThat(d.getImportados()).isEqualTo(7);
  assertThat(ubigeos.findByCodigo("02").orElseThrow().getNombre()).isEqualTo("Áncash");assertThat(ubigeos.findByCodigo("01")).isPresent();
  assertThat(tiposDano.findByCodigo("DAN-03").orElseThrow().getNombre()).isEqualTo("Educación");
 }
 @Test void reimportacionIdenticaEsIdempotente()throws Exception{importarBases();var r=importaciones.importar("UBIGEO",Files.readAllBytes(Path.of("datos/ubigeo.csv")));assertThat(r.getImportados()).isZero();assertThat(r.getOmitidos()).isEqualTo(25);assertThat(ubigeos.count()).isEqualTo(25);}
 @Test void unaFilaInvalidaRevierteTambienLasAltasAnteriores(){
  assertThatThrownBy(()->importaciones.importar("UBIGEO",bytes("codigo,nombre,tipo,estado\n01,Amazonas,DEPARTAMENTO,ACTIVO\n02,Áncash,INVALIDO,ACTIVO\n"))).isInstanceOf(ImportacionException.class).satisfies(e->assertThat(((ImportacionException)e).getErrores().getFirst()).contains("Fila 3"));
  assertThat(ubigeos.count()).isZero();
 }
 @Test void duplicadoDentroDelArchivoRevierteTodo(){assertThatThrownBy(()->importaciones.importar("UBIGEO",bytes("codigo,nombre,tipo,estado\n01,Amazonas,DEPARTAMENTO,ACTIVO\n01,Amazonas,DEPARTAMENTO,ACTIVO\n"))).isInstanceOf(ImportacionException.class);assertThat(ubigeos.count()).isZero();}
 @Test void conflictoConCodigoExistenteNoSobrescribeNiGuardaFilasNuevas(){
  importaciones.importar("UBIGEO",bytes("codigo,nombre,tipo,estado\n01,Amazonas,DEPARTAMENTO,ACTIVO\n"));
  assertThatThrownBy(()->importaciones.importar("UBIGEO",bytes("codigo,nombre,tipo,estado\n02,Áncash,DEPARTAMENTO,ACTIVO\n01,Cambio no permitido,DEPARTAMENTO,ACTIVO\n"))).isInstanceOf(ImportacionException.class);
  assertThat(ubigeos.count()).isEqualTo(1);assertThat(ubigeos.findByCodigo("01").orElseThrow().getNombre()).isEqualTo("Amazonas");
 }
 @Test void csvAdmiteBomComillasComasYSaltosEnDescripcion(){
  var r=importaciones.importar("ALERTA",bytes("\uFEFFcodigo,nombre,color,prioridad,descripcion,estado\nQ,\"Aviso, interno\",VERDE,1,\"Primera línea\nSegunda línea con \"\"comillas\"\"\",ACTIVO\n"));
  assertThat(r.getImportados()).isOne();assertThat(alertas.findByCodigo("Q").orElseThrow().getDescripcion()).contains("\n","\"comillas\"");
 }
 @Test void csvNoAdmiteUtf8Roto(){assertThatThrownBy(()->importaciones.importar("UBIGEO",new byte[]{(byte)0xC3,(byte)0x28})).isInstanceOf(ImportacionException.class);assertThat(ubigeos.count()).isZero();}
 @Test void csvNoAdmiteCabeceraEquivocada(){assertThatThrownBy(()->importaciones.importar("UBIGEO",bytes("codigo,nombre,estado,tipo\n01,Amazonas,ACTIVO,DEPARTAMENTO\n"))).isInstanceOf(ImportacionException.class);}
 @Test void csvNoAdmiteColumnasFaltantes(){assertThatThrownBy(()->importaciones.importar("UBIGEO",bytes("codigo,nombre,tipo,estado\n01,Amazonas,DEPARTAMENTO\n"))).isInstanceOf(ImportacionException.class);assertThat(ubigeos.count()).isZero();}
 @Test void csvNoAdmiteArchivoSinRegistros(){assertThatThrownBy(()->importaciones.importar("UBIGEO",bytes("codigo,nombre,tipo,estado\n"))).isInstanceOf(ImportacionException.class);}
 @Test void csvNoAdmiteNombreVacio(){assertThatThrownBy(()->importaciones.importar("UBIGEO",bytes("codigo,nombre,tipo,estado\n01,   ,DEPARTAMENTO,ACTIVO\n"))).isInstanceOf(ImportacionException.class);}
 @ParameterizedTest @ValueSource(strings={"0","-1","2.5","x"})void csvRechazaPrioridadNoPositivaOFraccionaria(String p){assertThatThrownBy(()->importaciones.importar("ALERTA",bytes("codigo,nombre,color,prioridad,descripcion,estado\nA,Alerta,VERDE,"+p+",Texto,ACTIVO\n"))).isInstanceOf(ImportacionException.class);assertThat(alertas.count()).isZero();}
 @Test void csvPermiteInactivoPeroNoApareceComoOpcion(){importaciones.importar("UBIGEO",bytes("codigo,nombre,tipo,estado\n01,Amazonas,DEPARTAMENTO,INACTIVO\n"));assertThat(ubigeos.count()).isOne();assertThat(catalogos.ubigeosActivos()).isEmpty();}
 @Test void flujoSismoDanoPersistenciaYConsultaAsociada()throws Exception{
  importarBases();Sismo s=sismos.guardar(null,valido());ReporteDano d=danos.registrar(s.getId(),danoValido());
  assertThat(d.getId()).isNotNull();assertThat(sismos.obtener(s.getId()).getUbigeo().getCodigo()).isEqualTo("15");assertThat(sismos.obtener(s.getId()).getTipoAlerta().getCodigo()).isEqualTo("INFO");
  assertThat(danos.listar(s.getId())).hasSize(1).allSatisfy(r->{assertThat(r.getCantidadAfectada()).isEqualTo(3);assertThat(r.getTipoDano().getNombre()).isEqualTo("Vivienda");assertThat(r.getFechaRegistro()).isNotNull();});
 }
 @Test void edicionDelSismoPersisteConMismoId()throws Exception{importarBases();Sismo s=sismos.guardar(null,valido());SismoForm f=sismos.formulario(sismos.obtener(s.getId()));f.setReferencia("Referencia corregida");f.setEstado("EN_VERIFICACION");sismos.guardar(s.getId(),f);assertThat(sismos.obtener(s.getId()).getReferencia()).isEqualTo("Referencia corregida");assertThat(sismoRepo.count()).isOne();}
 @Test void bajaLogicaConservaRegistroYReservaCodigo()throws Exception{importarBases();Sismo s=sismos.guardar(null,valido());sismos.darBaja(s.getId());assertThat(sismoRepo.findById(s.getId()).orElseThrow().isActivo()).isFalse();assertThat(sismos.listar("","")).isEmpty();assertThatThrownBy(()->sismos.guardar(null,valido())).isInstanceOf(ReglaNegocioException.class);}
 @Test void bajaConDanosQuedaBloqueadaParaConservarConsulta()throws Exception{importarBases();Sismo s=sismos.guardar(null,valido());danos.registrar(s.getId(),danoValido());assertThatThrownBy(()->sismos.darBaja(s.getId())).isInstanceOf(ReglaNegocioException.class);assertThat(sismos.obtener(s.getId()).isActivo()).isTrue();}
 @Test void filtrosSeCombinanPorCodigoYEstado()throws Exception{importarBases();Sismo s=sismos.guardar(null,valido());assertThat(sismos.listar("test","PENDIENTE")).extracting(Sismo::getId).containsExactly(s.getId());assertThat(sismos.listar("test","VERIFICADO")).isEmpty();}
 @Test void noPermiteUbigeoInactivoNiGuardaSismo()throws Exception{importarBases();Long id=valido().getUbigeoId();catalogos.cambiarEstado("ubigeos",id);assertThatThrownBy(()->sismos.guardar(null,valido())).isInstanceOf(ReglaNegocioException.class);assertThat(sismoRepo.count()).isZero();}
 @Test void noPermiteAlertaInactiva()throws Exception{importarBases();catalogos.cambiarEstado("alertas",valido().getTipoAlertaId());assertThatThrownBy(()->sismos.guardar(null,valido())).isInstanceOf(ReglaNegocioException.class);assertThat(sismoRepo.count()).isZero();}
 @Test void noPermiteTipoDanoInactivo()throws Exception{importarBases();Sismo s=sismos.guardar(null,valido());Long id=danoValido().getTipoDanoId();catalogos.cambiarEstado("danos",id);assertThatThrownBy(()->danos.registrar(s.getId(),danoValido())).isInstanceOf(ReglaNegocioException.class);assertThat(danoRepo.count()).isZero();}
 @Test void noPermiteDanoEnSismoDadoDeBaja()throws Exception{importarBases();Sismo s=sismos.guardar(null,valido());sismos.darBaja(s.getId());assertThatThrownBy(()->danos.registrar(s.getId(),danoValido())).isInstanceOf(NoEncontradoException.class);assertThat(danoRepo.count()).isZero();}
 @Test void proteccionDeMaestrosAsociadosEnCambioEstadoYEdicion()throws Exception{
  importarBases();Sismo s=sismos.guardar(null,valido());danos.registrar(s.getId(),danoValido());
  assertThatThrownBy(()->catalogos.cambiarEstado("ubigeos",s.getUbigeo().getId())).isInstanceOf(ReglaNegocioException.class);
  assertThatThrownBy(()->catalogos.cambiarEstado("alertas",s.getTipoAlerta().getId())).isInstanceOf(ReglaNegocioException.class);
  assertThatThrownBy(()->catalogos.cambiarEstado("danos",danoValido().getTipoDanoId())).isInstanceOf(ReglaNegocioException.class);
  CatalogoForm f=catalogos.formulario(catalogos.obtener("ubigeos",s.getUbigeo().getId()));f.setEstado("INACTIVO");assertThatThrownBy(()->catalogos.guardar("ubigeos",s.getUbigeo().getId(),f)).isInstanceOf(ReglaNegocioException.class);
  assertThat(ubigeos.findById(s.getUbigeo().getId()).orElseThrow().isActivo()).isTrue();
 }
 @ParameterizedTest @ValueSource(strings={"-0.1","10.1","4.555"})void validaMagnitudTambienAlLlamarServicioDirectamente(String valor)throws Exception{importarBases();SismoForm f=valido();f.setMagnitud(new BigDecimal(valor));assertThatThrownBy(()->sismos.guardar(null,f)).isInstanceOf(ReglaNegocioException.class);assertThat(sismoRepo.count()).isZero();}
 @Test void fechaFuturaRechazada()throws Exception{importarBases();SismoForm f=valido();f.setFechaHora(LocalDateTime.now().plusDays(2));assertThatThrownBy(()->sismos.guardar(null,f)).isInstanceOf(ReglaNegocioException.class);}
 @Test void coordenadasDebenIngresarseJuntas()throws Exception{importarBases();SismoForm f=valido();f.setLatitud(new BigDecimal("-12.04"));assertThatThrownBy(()->sismos.guardar(null,f)).isInstanceOf(ReglaNegocioException.class);}
 @Test void cantidadCeroNoGuardaDano()throws Exception{importarBases();Sismo s=sismos.guardar(null,valido());ReporteDanoForm f=danoValido();f.setCantidadAfectada(0);assertThatThrownBy(()->danos.registrar(s.getId(),f)).isInstanceOf(ReglaNegocioException.class);assertThat(danoRepo.count()).isZero();}
 @Test void maestroNuevoEdicionYReactivacionFuncionan(){CatalogoForm f=new CatalogoForm();f.setCodigo("extra");f.setNombre("Extra");Ubigeo u=(Ubigeo)catalogos.guardar("ubigeos",null,f);assertThat(u.getCodigo()).isEqualTo("EXTRA");catalogos.cambiarEstado("ubigeos",u.getId());assertThat(catalogos.ubigeosActivos()).isEmpty();catalogos.cambiarEstado("ubigeos",u.getId());f.setNombre("Nombre corregido");catalogos.guardar("ubigeos",u.getId(),f);assertThat(catalogos.ubigeosActivos()).extracting(Ubigeo::getNombre).containsExactly("Nombre corregido");}
}
