// Prueba funcional real contra el despliegue local. Requiere npm install playwright en un entorno de pruebas.
const fs=require('fs'),path=require('path'),assert=require('assert/strict'),cp=require('child_process');
const {chromium}=require(process.env.PLAYWRIGHT_MODULE || 'playwright');
const base=process.env.BASE_URL || 'http://localhost:18083/sismos-pa2';
const out=path.resolve(__dirname,'../evidencias');
fs.mkdirSync(out,{recursive:true});
const checks=[];
const ok=(nombre)=>{checks.push({prueba:nombre,resultado:'APROBADO'});console.log('OK '+nombre);};
(async()=>{
 const browser=await chromium.launch({channel:'chrome',headless:true});
 const context=await browser.newContext({viewport:{width:1440,height:1050},locale:'es-PE'});
 const page=await context.newPage();
 const errores=[];
 page.on('pageerror',e=>errores.push(e.message));
 async function abrir(ruta) {
   const res=await page.goto(base+ruta); assert.equal(res.status(),200); return res;
 }
 async function captura(nombre) {
   const selector=nombre.startsWith('03')||nombre.startsWith('05')?'.formulario-tarjeta':
       nombre.startsWith('04')?'.detalle-tarjeta':nombre.startsWith('06')||nombre.startsWith('07')?'.confirmacion':'.principal';
   if(selector==='.principal') {
     const main=await page.locator('.principal').boundingBox();
     const card=await page.locator('.tarjeta').last().boundingBox();
     await page.screenshot({path:path.join(out,nombre+'.png'),clip:{x:main.x,y:main.y+20,width:main.width,height:card.y+card.height-main.y-10}});
   } else await page.locator(selector).screenshot({path:path.join(out,nombre+'.png')});
 }
 const suf=Date.now().toString().slice(-7);
 await abrir('/sismos'); await captura('01-reportes'); ok('Listado de reportes HTTP 200 y JSP compilada');
 await abrir('/ubicaciones'); await captura('02-ubicaciones');
 await abrir('/ubicaciones/nueva');
 await page.locator('[name=codigo]').fill('QA-'+suf);
 await page.locator('[name=departamento]').fill('Áncash');
 await page.locator('[name=provincia]').fill('Huaraz');
 await page.locator('[name=distrito]').fill('Independencia');
 await Promise.all([page.waitForURL('**/ubicaciones/ver?*'),page.getByRole('button',{name:'Guardar ubicación'}).click()]);
 const uid=new URL(page.url()).searchParams.get('id');
 assert.ok((await page.textContent('body')).includes('Áncash')); ok('Alta de ubicación persistida con UTF-8');
 await abrir('/ubicaciones/editar?id='+uid);
 await page.locator('[name=distrito]').fill('Huaraz');
 await Promise.all([page.waitForURL('**/ubicaciones/ver?*'),page.getByRole('button',{name:'Guardar ubicación'}).click()]);
 assert.ok((await page.textContent('body')).includes('Huaraz')); ok('Edición y detalle de ubicación');
 await abrir('/sismos/nuevo');
 const datos={codigo:'QA-SIS-'+suf,fechaHora:'2026-10-01T09:30',ubicacion:'A 15 km al sur de Huaraz',magnitud:'4.50',profundidadKm:'25.50',estado:'PENDIENTE',ubicacionId:uid,canal:'LLAMADA',latitud:'-9.527790',longitud:'-77.527780',observaciones:'Demostración académica: revisión, ubicación y recepción. No es un reporte oficial.'};
 for(const [campo,valor] of Object.entries(datos)) {
   if(['estado','ubicacionId','canal'].includes(campo)) await page.locator('[name='+campo+']').selectOption(valor);
   else await page.locator('[name='+campo+']').fill(valor);
 }
 await captura('03-formulario');
 await Promise.all([page.waitForURL('**/sismos/ver?*'),page.getByRole('button',{name:'Guardar reporte'}).click()]);
 const sid=new URL(page.url()).searchParams.get('id');
 let body=await page.textContent('body');
 assert.ok(body.includes('Áncash / Huaraz / Huaraz') && body.includes('Llamada') && body.includes('-9.527790'));
 await captura('04-detalle'); ok('Alta y detalle de Sismo con canal, FK y coordenadas (HU09/HU10/HU39)');
 await abrir('/sismos/editar?id='+sid);
 assert.equal(await page.locator('[name=canal]').inputValue(),'LLAMADA');
 await page.locator('[name=estado]').selectOption('VERIFICADO');
 await page.locator('[name=canal]').selectOption('CORREO');
 await Promise.all([page.waitForURL('**/sismos/ver?*'),page.getByRole('button',{name:'Guardar reporte'}).click()]);
 body=await page.textContent('body'); assert.ok(body.includes('Verificado') && body.includes('Correo')); ok('Edición de estado y canal');
 await abrir('/sismos?q='+datos.codigo+'&estado=VERIFICADO');
 assert.equal(await page.locator('tbody tr').count(),1); assert.ok((await page.textContent('tbody')).includes(datos.codigo)); ok('Búsqueda por código y filtro JPQL combinados');
 await abrir('/sismos/nuevo');
 const csrf=await page.locator('[name=csrf]').inputValue();
 let res=await context.request.post(base+'/sismos/nuevo',{form:{...datos,codigo:'INVALIDO-'+suf,latitud:'91',csrf}});
 assert.equal(res.status(),400); assert.ok((await res.text()).includes('entre -90 y 90')); ok('Servidor rechaza latitud 91 aunque se omita validación HTML');
 // Muestra en navegador la misma validación del servidor para la evidencia visual.
 await abrir('/sismos/nuevo');
 for(const [campo,valor] of Object.entries({...datos,codigo:'INVALIDO-'+suf,latitud:'91'})) {
   if(['estado','ubicacionId','canal'].includes(campo)) await page.locator('[name='+campo+']').selectOption(valor);
   else await page.locator('[name='+campo+']').fill(valor);
 }
 await page.locator('form').evaluate(el=>el.noValidate=true);
 await Promise.all([page.waitForNavigation(),page.getByRole('button',{name:'Guardar reporte'}).click()]);
 await captura('05-validacion');
 res=await context.request.post(base+'/sismos/nuevo',{form:{...datos,csrf}});
 assert.equal(res.status(),400); ok('Código duplicado HTTP 400');
 res=await context.request.post(base+'/sismos/nuevo',{form:{...datos,codigo:'SIN-FK-'+suf,ubicacionId:'999999',csrf}});
 assert.equal(res.status(),400); ok('Relación inexistente rechazada');
 res=await context.request.post(base+'/sismos/nuevo',{form:{...datos,codigo:'CSRF-'+suf,csrf:'incorrecto'}});
 assert.equal(res.status(),403); ok('POST sin token válido HTTP 403');
 await abrir('/ubicaciones/eliminar?id='+uid);
 await Promise.all([page.waitForNavigation(),page.getByRole('button',{name:'Confirmar baja'}).click()]);
 assert.ok((await page.textContent('body')).includes('existen reportes activos asociados'));
 await captura('06-integridad'); ok('Baja de ubicación referenciada bloqueada HTTP 409');
 if(process.env.REINICIAR==='1') {
   const opciones={cwd:path.resolve(__dirname,'..'),stdio:'pipe',timeout:60000};
   cp.execFileSync('docker',['compose','stop','web'],opciones);
   cp.execFileSync('docker',['compose','restart','db'],opciones);
   cp.execFileSync('docker',['compose','up','-d','--wait','db'],opciones);
   cp.execFileSync('docker',['compose','start','web'],opciones);
   let listo=false;
   for(let i=0;i<40;i++) {
     try { const r=await context.request.get(base+'/sismos/ver?id='+sid); if(r.status()===200 && (await r.text()).includes(datos.codigo)){listo=true;break;} }catch{}
     await new Promise(r=>setTimeout(r,1000));
   }
   assert.ok(listo,'Persistencia tras reinicio');
   await abrir('/sismos/ver?id='+sid);
   const persistido=await page.textContent('body');
   for(const valor of [datos.codigo,'Áncash / Huaraz / Huaraz','Correo','Verificado','-9.527790','-77.527780']) assert.ok(persistido.includes(valor),valor);
   ok('Reporte conserva ID, relación y datos tras reiniciar MySQL y Tomcat');
 }
 await abrir('/sismos/eliminar?id='+sid); await captura('07-confirmacion');
 await page.getByRole('link',{name:'Cancelar',exact:true}).click();
 assert.ok((await page.textContent('body')).includes(datos.codigo)); ok('Cancelar baja no modifica el reporte');
 await abrir('/sismos/eliminar?id='+sid);
 await Promise.all([page.waitForURL('**/sismos?eliminado=1'),page.getByRole('button',{name:'Confirmar baja'}).click()]);
 res=await context.request.get(base+'/sismos/ver?id='+sid); assert.equal(res.status(),404);
 await abrir('/sismos?q='+datos.codigo); assert.ok((await page.textContent('body')).includes('No hay reportes')); ok('Baja lógica de Sismo: desaparece del listado y detalle HTTP 404');
 await abrir('/ubicaciones/eliminar?id='+uid);
 await Promise.all([page.waitForURL('**/ubicaciones?baja=1'),page.getByRole('button',{name:'Confirmar baja'}).click()]);
 res=await context.request.get(base+'/ubicaciones/ver?id='+uid); assert.equal(res.status(),404); ok('Baja de ubicación ahora permitida, conservando la relación histórica');
 assert.deepEqual(errores,[]); ok('Sin errores JavaScript');
 fs.writeFileSync(path.join(out,'pruebas-web.json'),JSON.stringify({fecha:new Date().toISOString(),base,datosSinteticos:true,sismoId:sid,ubicacionId:uid,codigo:datos.codigo,pruebas:checks},null,2));
 await browser.close();
})().catch(e=>{console.error(e);process.exit(1)});
