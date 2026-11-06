/* Recorrido real PA3. Requiere Node, Chrome y npm install --no-save playwright.
 * Crea reportes QA sintéticos y un maestro de prueba que deja inactivo.
 * Reimporta los CSV del curso; no borra datos ni volúmenes.
 * Reinicia únicamente el servicio web de este compose para comprobar persistencia.
 */
const {chromium}=require('playwright');
const assert=require('node:assert/strict');
const fs=require('node:fs');
const path=require('node:path');
const {execFileSync}=require('node:child_process');
const project=path.resolve(__dirname,'..');
const out=path.join(project,'evidencias');fs.mkdirSync(out,{recursive:true});
const base=process.env.APP_URL||'http://localhost:18084/sismos-pa3';
const suffix=Date.now().toString().slice(-9),codigo='SIS-QA-'+suffix;
const pruebas=[];
function ok(nombre){pruebas.push({nombre,resultado:'APROBADO'});console.log('OK:',nombre);}
function sql(query){return execFileSync('docker',['compose','exec','-T','db','sh','-c','MYSQL_PWD="$MYSQL_PASSWORD" mysql -u "$MYSQL_USER" -D "$MYSQL_DATABASE" --default-character-set=utf8mb4 --batch --skip-column-names'],{cwd:project,input:query,encoding:'utf8'}).trim();}
(async()=>{
 const browser=await chromium.launch({channel:'chrome',headless:true});
 const page=await browser.newPage({viewport:{width:1280,height:1000},locale:'es-PE'});
 const errors=[];page.on('pageerror',e=>errors.push(e.message));
 async function token(){return page.locator('input[name=_csrf]').first().inputValue();}
 async function load(route){const r=await page.goto(base+route);assert.equal(r.status(),200,route);}
 async function post(route,form){const r=await page.request.post(base+route,{form:{_csrf:await token(),...form}});assert.ok(r.status()<500,route+' '+r.status());return r;}
 async function picture(name,locator){await (locator||page.locator('main')).screenshot({path:path.join(out,name)});}
 try{
  await load('/sismos');assert.match(await page.title(),/SISMO|Sismo|sísmic/i);ok('Arranque Spring Boot y vista Thymeleaf');
  const cargas=[];
  for(const [tipo,file,count]of [['UBIGEO','ubigeo.csv',25],['ALERTA','tipo_alerta.csv',4],['DANO','tipo_dano.csv',7]]){
   await load('/importaciones');await page.locator('[name=tipo]').selectOption(tipo);await page.locator('[name=archivo]').setInputFiles(path.join(project,'datos',file));
   await Promise.all([page.waitForNavigation(),page.getByRole('button',{name:/Importar/}).click()]);
   const body=await page.locator('body').innerText();assert.ok(!body.includes('No se importó'));assert.match(body,new RegExp(String(count)));
   cargas.push({archivo:file,esperados:count});ok('Importación de '+file+' ('+count+')');
  }
  assert.equal(sql("SELECT COUNT(*) FROM ubigeo WHERE codigo REGEXP '^[0-9]{2}$';"),'25');
  assert.equal(sql("SELECT COUNT(*) FROM tipo_alerta WHERE codigo IN ('INFO','PREV','ALER','EMER');"),'4');
  assert.equal(sql("SELECT COUNT(*) FROM tipo_dano WHERE codigo REGEXP '^DAN-0[1-7]$';"),'7');
  assert.match(sql("SELECT nombre FROM ubigeo WHERE codigo='02';"),/Áncash/);ok('MySQL: 25/4/7 maestros y UTF-8 con ceros iniciales');
  await load('/importaciones');
  const recarga=await page.request.post(base+'/importaciones',{multipart:{_csrf:await token(),tipo:'UBIGEO',archivo:{name:'ubigeo.csv',mimeType:'text/csv',buffer:fs.readFileSync(path.join(project,'datos/ubigeo.csv'))}}});
  assert.equal(recarga.status(),200);assert.match(await recarga.text(),/25/);assert.equal(sql("SELECT COUNT(*) FROM ubigeo WHERE codigo REGEXP '^[0-9]{2}$';"),'25');ok('Recarga idempotente sin duplicar maestros');
  await page.goto(base+'/importaciones');
  const invalid='codigo,nombre,tipo,estado\nQA-'+suffix+',Prueba transaccional,DEPARTAMENTO,ACTIVO\nBAD-'+suffix+',,DEPARTAMENTO,ACTIVO\n';
  const atomic=await page.request.post(base+'/importaciones',{multipart:{_csrf:await token(),tipo:'UBIGEO',archivo:{name:'invalido.csv',mimeType:'text/csv',buffer:Buffer.from(invalid)}}});
  assert.match(await atomic.text(),/Fila 3/);assert.equal(sql("SELECT COUNT(*) FROM ubigeo WHERE codigo='QA-"+suffix+"';"),'0');ok('Archivo inválido informa fila y revierte la fila válida anterior');
  const conflict='codigo,nombre,tipo,estado\n01,Nombre cambiado,DEPARTAMENTO,ACTIVO\n';
  const conf=await page.request.post(base+'/importaciones',{multipart:{_csrf:await token(),tipo:'UBIGEO',archivo:{name:'conflicto.csv',mimeType:'text/csv',buffer:Buffer.from(conflict)}}});
  assert.match(await conf.text(),/datos diferentes/);assert.equal(sql("SELECT nombre FROM ubigeo WHERE codigo='01';"),'Amazonas');ok('Conflicto de código no sobrescribe datos');
  await load('/importaciones');await page.locator('[name=tipo]').selectOption('DANO');await page.locator('[name=archivo]').setInputFiles(path.join(project,'datos/tipo_dano.csv'));
  await Promise.all([page.waitForNavigation(),page.getByRole('button',{name:/Importar/}).click()]);await picture('01-importacion.png');
  await load('/catalogos/ubigeos');await picture('02-catalogo.png');
  for(const route of ['/catalogos/alertas','/catalogos/danos','/catalogos/ubigeos/nuevo','/catalogos/alertas/nuevo','/catalogos/danos/nuevo'])await load(route);
  ok('Tres maestros: listados y formularios disponibles');
  await load('/sismos/nuevo');
  const ubigeo=await page.locator('[name=ubigeoId] option').filter({hasText:/Lima/}).getAttribute('value');
  const alerta=await page.locator('[name=tipoAlertaId] option').filter({hasText:/Preventiva/}).getAttribute('value');
  const form={codigo,fechaHora:'2026-10-15T09:18',magnitud:'4.20',profundidadKm:'35.00',referencia:'A 15 km al sur de Lima',ubigeoId:ubigeo,tipoAlertaId:alerta,canal:'CORREO',estado:'PENDIENTE',latitud:'-12.046374',longitud:'-77.042793',observaciones:'Registro sintético de prueba PA3.'};
  const future=await post('/sismos/nuevo',{...form,fechaHora:'2099-01-01T00:00'});assert.match(await future.text(),/futuro/);ok('Fecha futura rechazada');
  const pair=await post('/sismos/nuevo',{...form,longitud:''});assert.match(await pair.text(),/juntas|ambas/);ok('Coordenadas incompletas rechazadas');
  const badref=await post('/sismos/nuevo',{...form,ubigeoId:'999999999'});assert.match(await badref.text(),/ubigeo activo/);ok('Servicio rechaza referencia inexistente');
  for(const [name,value]of Object.entries(form)){const field=page.locator('[name='+name+']');if(await field.evaluate(e=>e.tagName)==='SELECT')await field.selectOption(value);else await field.fill(value);}
  await Promise.all([page.waitForURL(/\/sismos\/\d+$/),page.getByRole('button',{name:/Guardar reporte/}).click()]);
  const id=page.url().split('/').pop();assert.match(await page.locator('body').innerText(),new RegExp(codigo));await picture('03-sismo.png');ok('Caso de uso Registrar sismo con ubigeo y alerta');
  await load('/sismos/nuevo');const dupe=await post('/sismos/nuevo',form);assert.match(await dupe.text(),/código ya está registrado/);ok('Código de sismo único');
  await load('/sismos/'+id+'/editar');await page.locator('[name=estado]').selectOption('EN_VERIFICACION');
  await Promise.all([page.waitForURL(base+'/sismos/'+id),page.getByRole('button',{name:/Guardar/}).click()]);assert.match(await page.locator('body').innerText(),/En verificación/);ok('Edición conserva registro y cambia el estado');
  await load('/sismos?q='+codigo+'&estado=EN_VERIFICACION');assert.equal(await page.locator('tbody tr').count(),1);assert.match(await page.locator('tbody').innerText(),new RegExp(codigo));ok('Búsqueda por código y filtro por estado');
  await load('/sismos/'+id+'/danos/nuevo');
  const vivienda=await page.locator('[name=tipoDanoId] option').filter({hasText:/Vivienda/}).getAttribute('value');
  await page.locator('[name=tipoDanoId]').selectOption(vivienda);await page.locator('[name=cantidadAfectada]').fill('0');await page.locator('[name=descripcion]').fill('Tres viviendas con grietas. Dato sintético de demostración.');
  await Promise.all([page.waitForNavigation(),page.getByRole('button',{name:/Guardar|Registrar daño/}).click()]);
  assert.match(await page.locator('body').innerText(),/mayor que cero/);await picture('05-validacion.png');ok('Cantidad cero rechazada por Bean Validation');
  await page.locator('[name=cantidadAfectada]').fill('3');await Promise.all([page.waitForURL(/\/sismos\/\d+(#danos)?$/),page.getByRole('button',{name:/Guardar|Registrar daño/}).click()]);
  assert.match(await page.locator('#danos').innerText(),/Tres viviendas/);await picture('04-danos.png',page.locator('#danos'));ok('Caso de uso Registrar daño y consultar vínculo con el sismo');
  assert.equal(sql('SELECT COUNT(*) FROM reporte_dano WHERE sismo_id='+id+';'),'1');
  await load('/catalogos/alertas/'+alerta+'/estado');const protect=await post('/catalogos/alertas/'+alerta+'/estado',{});assert.match(await protect.text(),/asociad|utilizad|referenc/i);assert.equal(sql('SELECT estado FROM tipo_alerta WHERE id='+alerta+';'),'ACTIVO');ok('Protección de maestro con sismos activos');
  await load('/sismos/'+id+'/baja');const history=await post('/sismos/'+id+'/baja',{});assert.match(await history.text(),/daño asociados|daños asociados/);assert.equal(sql('SELECT activo+0 FROM sismo WHERE id='+id+';'),'1');ok('Conservación del sismo con daños asociados');
  await load('/sismos/nuevo');const aux=await post('/sismos/nuevo',{...form,codigo:'SIS-BAJA-'+suffix});const auxId=new URL(aux.url()).pathname.split('/').pop();assert.match(auxId,/^\d+$/);
  await load('/sismos/'+auxId+'/baja');await page.getByRole('button',{name:/Confirmar/}).click();assert.equal(sql('SELECT activo+0 FROM sismo WHERE id='+auxId+';'),'0');ok('Baja lógica sin eliminación física');
  const noCsrf=await page.request.post(base+'/sismos/nuevo',{form});assert.equal(noCsrf.status(),403);ok('Formularios POST protegidos con token CSRF');
  const missing=await page.request.get(base+'/sismos/999999999');assert.equal(missing.status(),404);ok('Registro inexistente devuelve una pantalla 404');
  await load('/sismos/'+id);await page.setViewportSize({width:390,height:844});assert.ok(await page.evaluate(()=>document.documentElement.scrollWidth<=innerWidth));await picture('06-movil.png');await page.setViewportSize({width:1280,height:1000});ok('Vista adaptable sin desbordamiento de página');
  execFileSync('docker',['compose','restart','web'],{cwd:project,stdio:'pipe'});
  let alive=false;for(let i=0;i<40;i++){try{const r=await page.goto(base+'/sismos/'+id,{timeout:3000});if(r.status()===200){alive=true;break;}}catch{}await page.waitForTimeout(1000);}
  assert.ok(alive,'Reinicio del servicio web');assert.match(await page.locator('#danos').innerText(),/Tres viviendas/);assert.match(await page.locator('body').innerText(),new RegExp(codigo));ok('Sismo y daño persisten después de reiniciar Spring Boot');
  assert.deepEqual(errors,[]);ok('Sin errores JavaScript durante el recorrido');
  const result={fecha:new Date().toISOString(),resultado:'APROBADO',codigo,sismoId:Number(id),cargas,pruebas};
  fs.writeFileSync(path.join(out,'pruebas-web-pa3.json'),JSON.stringify(result,null,2));
  fs.writeFileSync(path.join(out,'consulta-mysql-pa3.txt'),sql('SELECT s.id,s.codigo,u.codigo AS ubigeo,a.codigo AS alerta,s.activo,COUNT(d.id) AS danos FROM sismo s JOIN ubigeo u ON u.id=s.ubigeo_id JOIN tipo_alerta a ON a.id=s.tipo_alerta_id LEFT JOIN reporte_dano d ON d.sismo_id=s.id WHERE s.id='+id+' GROUP BY s.id,s.codigo,u.codigo,a.codigo,s.activo;')+'\n');
  console.log(JSON.stringify(result));
 }finally{await browser.close();}
})().catch(e=>{console.error(e);process.exit(1)});
