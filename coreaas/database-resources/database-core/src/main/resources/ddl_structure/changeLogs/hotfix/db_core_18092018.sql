--liquibase formatted sql

--changeset jvelandia:01
--comment: Acutalizacion PlantillaComunicado
UPDATE PlantillaComunicado set pcoCuerpo='<p style=''text-align:justify;line-height:normal''>
S - AP</p>  <p style=''text-align:justify;line-height:normal''>FU - SUB</p>  <p>&nbsp;</p>  
<p style=''text-align:justify;line-height:normal''>(<i style=''mso-bidi-font-style:normal''>Ciudad</i>), 
${fechaGeneracion}</p>  <p>&nbsp;</p>  <p style=''text-align:justify;line-height:normal''>Señor (a)</p>  
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${nombreCompleto}</b></p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${tipoSolicitante}</b></p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''><span style=''mso-spacerun:yes''> 
</span>${direccionPrincipal} – ${municipio} ${departamento}</b></p>  <p style=''text-align:justify;line-height:normal''>
<b style=''mso-bidi-font-weight:normal''>${telefono}</b></p>  <p>&nbsp;</p>  <p style=''text-align:justify;line-height:normal''>
Ref.: ${tipoCertificado} </p>  <p>&nbsp;</p>  <p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>${nombreCcf}</b></p>  
<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Sección de afiliación ${seccionAfiliacion}</b></p>  
<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Certifica</b></p>  <p>&nbsp;</p>  <p>&nbsp;</p>  
<p style=''text-align:justify;line-height:normal''>Que ${tipoTrabajador} <b style=''mso-bidi-font-weight:normal''>${nombreCompleto} 
${tipoIdentificacion} número ${numeroIdentificacion}   a la fecha ${fechaGeneracion} se encuentra afiliada a esta CAJA DE COMPENSACION FAMILIAR${trabajador}${nombreRazonSocialEmpleador}.</b></p>  <p>&nbsp;</p>  
<p><span style=''color:black;mso-themecolor:text1''>${parrafoNoValidez}</span></p>  <p>&nbsp;</p>  <p><span style=''color:black;mso-themecolor:text1''>Se expide a solicitud del interesado.</span></p>  
<p><span style=''color:black;mso-themecolor:text1''>&nbsp;</span></p>  
<p><span style=''color:black;mso-themecolor:text1''>NOS RESERVAMOS EL DERECHO DE REALZAR AUDITORIA POSTERIOR.</span></p>  
<p style=''text-align:justify''>&nbsp;</p>  <p style=''text-align:justify;line-height:normal''>${contenido}</p>  
<p>&nbsp;</p> <p><i style=''mso-bidi-font-style:normal''>Cordialmente, </i></p>
<p><span>&nbsp;</span></p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${responsableCcf}</b></p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${cargoResponsableCcf}</b></p>',
pcoPie='<p><span>&nbsp;</span></p>'
WHERE pcoEtiqueta='COM_GEN_CER_AFI';

--changeset fvasquez:02
--comment: 

INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta)
VALUES ('Liquidación de aportes en mora manual','<p>Cuerpo</p>
<p>${consecutivoLiquidacion}</p>
<p>jytfiytfi7tf7ff&nbsp;${razonSocial/Nombre}. fdfljdblajdsbljdgljdfgdfg, ${tipoDeIdentificacion}</p>
<p>&nbsp;</p>
<p>${numeroIdentificacion}</p>
<p>&nbsp;</p>
<p>${fechaSuspencionAutomatica}</p>
<p>&nbsp;</p>
<p>${nombreComunicado}</p>
<p>&nbsp;</p>
<p>${fechaComunicado}</p>
<p>&nbsp;</p>
<p>${fechaNotificacionPersonal}</p>
<p>&nbsp;</p>
<p>${tabla}</p>
<p>&nbsp;</p>
<p>${totalLiquidacion}</p>
<p>&nbsp;</p>
<p>${fechaLiquidacion}</p>
<p>&nbsp;</p>
<p>${nombreCcf}</p>
<p>&nbsp;</p>
<p>${logoDeLaCcf}</p>
<p>&nbsp;</p>
<p>${departamentoCcf}</p>
<p>&nbsp;</p>
<p>${ciudadCcf}</p>
<p>&nbsp;</p>
<p>${direccionCcf}</p>
<p>&nbsp;</p>
<p>${telefonoCcf}</p>
<p>&nbsp;</p>
<p>${webCcf}</p>
<p>&nbsp;</p>
<p>${logoSuperservicios}</p>
<p>&nbsp;</p>
<p>${responsableCcf}</p>
<p>&nbsp;</p>
<p>${cargoResponsableCcf}</p>',
'Encabezado','Mensaje<br /> ${consecutivoLiquidacion}<br /> ${razonSocial/Nombre}<br /> ${tipoDeIdentificacion}<br /> ${numeroIdentificacion}<br /> ${fechaSuspencionAutomatica}<br /> ${nombreComunicado}<br /> ${fechaComunicado}<br /> ${fechaNotificacionPersonal}<br /> ${tabla}<br /> ${totalLiquidacion}<br /> ${fechaLiquidacion}<br /> ${nombreCcf}<br /> ${logoDeLaCcf}<br /> ${departamentoCcf}<br /> ${ciudadCcf}<br /> ${direccionCcf}<br /> ${telefonoCcf}<br /> ${webCcf}<br /> ${logoSuperservicios}<br /> ${responsableCcf}<br /> ${cargoResponsableCcf}',
'Liquidación de aportes en mora manual','Pie','LIQ_APO_MAN');

INSERT [DestinatarioComunicado] ([dcoProceso], [dcoEtiquetaPlantilla]) VALUES (N'GESTION_COBRO_MANUAL', N'LIQ_APO_MAN');

declare @idCom bigint
select @idCom = pcoid FROM plantillacomunicado where pcoEtiqueta = 'LIQ_APO_MAN'
 
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${consecutivoLiquidacion}','Número consecutivo de la liquidación','Consecutivo Liquidación ',@idCom,NULL,'VARIABLE','0')
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${razonSocial/Nombre}','Razón social de la empresa a la cual se encuentra asociado el representante legal','Razón social / Nombre',@idCom,NULL,'VARIABLE','0')
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${tipoDeIdentificacion}','Tipo de identificación del aportante que realizó la solicitud','Tipo de identificación',@idCom,NULL,'VARIABLE','0')
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${numeroIdentificacion}','Número de identificación del aportante que realizó la solicitud','Número Identificación',@idCom,NULL,'VARIABLE','0')
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${fechaSuspencionAutomatica}','Fecha de ejecución de la acción de cobro A','Fecha Suspención Automatica',@idCom,NULL,'VARIABLE','0')
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${nombreComunicado}','Nombre del comunicado suspensión automatica','Nombre Comunicado',@idCom,NULL,'VARIABLE','0')
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${fechaComunicado}','Fecha de generación del comunicado suspensión automatica','Fecha Comunicado',@idCom,NULL,'VARIABLE','0')
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${fechaNotificacionPersonal}','Fecha de ejecución de la acción de cobro 2C','Fecha Notificación Personal',@idCom,NULL,'VARIABLE','0')
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${tabla}','Tabla donde contiene los periodos adeudados','Tabla',@idCom,NULL,'REPORTE_VARIABLE','0')
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${totalLiquidacion}','El valor total de la liquidación para el aportante ','Total Liquidación',@idCom,NULL,'REPORTE_VARIABLE','0')
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${fechaLiquidacion}','Fecha para la que se efectua la liquidación para el aportante','Fecha Liquidación',@idCom,NULL,'VARIABLE','0')
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${nombreCcf}','Nombre de la caja de Compensación','Nombre CCF',@idCom,'NOMBRE_CCF','CONSTANTE',NULL)
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${logoDeLaCcf}','Logotipo de la Caja de Compensación','Logo de la CCF',@idCom,'LOGO_DE_LA_CCF','CONSTANTE',NULL)
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${departamentoCcf}','Departamento de la caja de Compensación','Departamento CCF',@idCom,'DEPARTAMENTO_CCF','CONSTANTE',NULL)
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${ciudadCcf}','Ciudad de la caja de Compensación','Ciudad CCF',@idCom,'CIUDAD_CCF','CONSTANTE',NULL)
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${direccionCcf}','Dirección de la caja de Compensación','Dirección CCF',@idCom,'DIRECCION_CCF','CONSTANTE',NULL)
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${telefonoCcf}','Teléfono de la caja de Compensación','Teléfono CCF',@idCom,'TELEFONO_CCF','CONSTANTE',NULL)
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${webCcf}','Web de la caja de Compensación','Web CCF',@idCom,'WEB_CCF','CONSTANTE',NULL)
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${logoSuperservicios}','Logo de la caja de Compensación','Logo SuperServicios',@idCom,'LOGO_SUPERSERVICIOS','CONSTANTE',NULL)
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${responsableCcf}','responsable caja de compensación','Responsable CCF',@idCom,'RESPONSABLE_CCF','USUARIO_CONSTANTE',NULL)
INSERT INTO variablecomunicado(vcoclave,vcodescripcion,vconombre,vcoplantillacomunicado,vconombreconstante,vcotipovariablecomunicado,vcoorden) values('${cargoResponsableCcf}','Cargo del responsable del envío del comunicado en la caja','Cargo responsable CCF',@idCom,'CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',NULL);

--changeset jvelandia:02
--comment: 

UPDATE PlantillaComunicado set pcoCuerpo='<br style=''mso-ignore:vglayout'' clear=ALL>
<p style=''text-align:justify;line-height:normal''>S - AP</p>
<p style=''text-align:justify;line-height:normal''>FU - SUB</p>
<p>&nbsp;</p>
<p style=''text-align:justify;line-height:normal''>(<i style=''mso-bidi-font-style:normal''>Ciudad</i>), ${fechaGeneracion}</p>
<p>&nbsp;</p>
<p style=''text-align:justify;line-height:normal''>Señor (a)</p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${nombreCompleto}</b></p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${tipoSolicitante}</b></p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''><span style=''mso-spacerun:yes''> </span>${direccionPrincipal} – ${municipio} ${departamento}</b></p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${telefono}</b></p>
<p>&nbsp;</p>
<p style=''text-align:justify;line-height:normal''>Ref.: Certificado de historia de afiliación ${refCertificado}</p>
<p>&nbsp;</p>
<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>${nombreCcf}</b></p>
<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Sección de afiliación ${seccionAfiliacion}</b></p>
<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Certifica</b></p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p style=''text-align:justify;line-height:normal''>Que ${tipoTrabajador} <b style=''mso-bidi-font-weight:normal''>${nombreCompleto} ${tipoIdentificacion} número ${numeroIdentificacion} 
a la fecha ${fechaGeneracion} presenta la siguiente historia de afiliación a esta CAJA DE COMPENSACION FAMILIAR.</b></p>
<p>&nbsp;</p>
${tabla}
<p>&nbsp;</p>
<p><span style=''color:black;mso-themecolor:text1''>Esta certificación no es válida para retirarse de <b style=''mso-bidi-font-weight:normal''>${nombreCcf}</b>, ni afiliarse a otra Caja de Compensación.</span></p>
<p>&nbsp;</p>
<p><span style=''color:black;mso-themecolor:text1''>Se expide a solicitud del interesado.</span></p>
<p><span style=''color:black;mso-themecolor:text1''>&nbsp;</span></p>
<p><span style=''color:black;mso-themecolor:text1''>NOS RESERVAMOS EL DERECHO DE REALZAR AUDITORIA POSTERIOR.</span></p>
<p style=''text-align:justify''>&nbsp;</p>
<p style=''text-align:justify;line-height:normal''>${contenido}</p>
<p>&nbsp;</p> <p><i style=''mso-bidi-font-style:normal''>Cordialmente, </i></p>
<p><span>&nbsp;</span></p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${responsableCcf}</b></p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${cargoResponsableCcf}</b></p>',
pcoPie='<p><span>&nbsp;</span></p>'
WHERE pcoEtiqueta='COM_GEN_CER_HIS_AFI';


UPDATE PlantillaComunicado SET pcoCuerpo='<p style=''text-align:justify;line-height:normal''>S - AP</p>
	<p style=''text-align:justify;line-height:normal''>FU - SUB</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>(<i style=''mso-bidi-font-style:normal''>Ciudad</i>), ${fechaGeneracion}</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Señor (a)</p>
	<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${nombreCompleto}</b></p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Certificado de Paz y Salvo para desafiliación y cambio de caja</p>
	<p>&nbsp;</p>
	<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>${nombreCcf}</b></p>
	<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Sección de afiliación ${seccionAfiliacion}</b></p>
	<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Certifica</b></p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Que ${tipoTrabajador} <b style=''mso-bidi-font-weight:normal''>${nombreCompleto} ${tipoIdentificacion} número ${numeroIdentificacion} </b>
	Solicito su desafiliación de la caja como ${tipoAfiliado} , pagando sus aportes correspondientes según el tipo de afiliación hasta el mes de ${mesFinAporteCertificado} </p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Se expide la presente certificación a ${fechaGeneracion} y es válida como Paz y Salvo.</p>
	<p style=''text-align:justify''>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>${contenido}</p>
	<p><span style=''color:black;mso-themecolor:text1''>NOS RESERVAMOS EL DERECHO DE REALZAR AUDITORIA POSTERIOR.</span></p>
	<p><span style=''color:black;mso-themecolor:text1''>&nbsp;</span></p>
	<p><span style=''color:black;mso-themecolor:text1''>Se expide a solicitud del interesado.</span></p>
	<p>&nbsp;</p> <p><i style=''mso-bidi-font-style:normal''>Cordialmente, </i></p>
	<p><span>&nbsp;</span></p>
	<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${responsableCcf}</b></p>
	<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${cargoResponsableCcf}</b></p>',
	pcoPie='<p><span>&nbsp;</span></p>'
	where pcoEtiqueta='COM_GEN_CER_PYS';


UPDATE PlantillaComunicado SET pcoCuerpo='<p style=''text-align:justify;line-height:normal''>S - AP</p>
	<p style=''text-align:justify;line-height:normal''>FU - SUB</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>(<i style=''mso-bidi-font-style:normal''>Ciudad</i>), ${fechaGeneracion}</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Señor (a)</p>
	<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${nombreCompleto}</b></p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Certificado de Paz y Salvo para desafiliación y cambio de caja</p>
	<p>&nbsp;</p>
	<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>${nombreCcf}</b></p>
	<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Sección de afiliación ${seccionAfiliacion}</b></p>
	<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Certifica</b></p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Que ${tipoTrabajador} <b style=''mso-bidi-font-weight:normal''>${nombreCompleto} ${tipoIdentificacion} número ${numeroIdentificacion} </b>
	Solicito su desafiliación para afiliación a otra Caja de Compensación Familiar, pagando sus aportes correspondientes al 4% a ésta corporación hasta el mes de ${mesFinAporteCertificado} </p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Los aportes correspondientes al mes de ${mesInAporteCertificado} deberán cancelarlos con la nueva Caja de Compensación a la que se afilien</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Se expide la presente certificación a ${fechaGeneracion} y es válida como Paz y Salvo para cambio de Caja en el Departamento ${departamentoCerPazySalvo}</p>
	<p style=''text-align:justify''>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>${contenido}</p>
	<p><span style=''color:black;mso-themecolor:text1''>NOS RESERVAMOS EL DERECHO DE REALZAR AUDITORIA POSTERIOR.</span></p>
	<p><span style=''color:black;mso-themecolor:text1''>&nbsp;</span></p>
	<p><span style=''color:black;mso-themecolor:text1''>Se expide a solicitud del interesado.</span></p>
	<p>&nbsp;</p> <p><i style=''mso-bidi-font-style:normal''>Cordialmente, </i></p>
	<p><span>&nbsp;</span></p>
	<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${responsableCcf}</b></p>
	<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${cargoResponsableCcf}</b></p>',
	pcoPie='<p><span>&nbsp;</span></p>'
where pcoEtiqueta='COM_GEN_CER_PYS_EMP';


UPDATE PlantillaComunicado SET pcoCuerpo='<p style=''text-align:justify;line-height:normal''>S - AP</p>
	<p style=''text-align:justify;line-height:normal''>FU - SUB</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>(<i style=''mso-bidi-font-style:normal''>Ciudad</i>), ${fechaGeneracion}</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Señor (a)</p>
	<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${nombreCompleto}</b></p>
	<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${tipoSolicitante}</b></p>
	<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''><span style=''mso-spacerun:yes''> </span>${direccionPrincipal} – ${municipio} ${departamento}</b></p>
	<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${telefono}</b></p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Ref.: ${tipoCertificado} </p>
	<p>&nbsp;</p>
	<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>${nombreCcf}</b></p>
	<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Sección de afiliación ${seccionAfiliacion}</b></p>
	<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Certifica</b></p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Que ${tipoTrabajador} <b style=''mso-bidi-font-weight:normal''>${nombreCompleto} ${tipoIdentificacion} número ${numeroIdentificacion} 
	a la fecha ${fechaGeneracion}</b> ha realizado los siguientes aportes durante el año <b style=''mso-bidi-font-weight:normal''>${anio}</b>, por un total de <b>${sumAportes}</b> por concepto de pago de aportes parafiscales con destino al sistema de compensación familiar, cuyo periodo comprende Enero – Diciembre.</p>
	<p>&nbsp;</p>
	${tabla}
	<p>&nbsp;</p>
	<p><span style=''color:black;mso-themecolor:text1''>Esta certificación no es válida para retirarse de <b style=''mso-bidi-font-weight:normal''>${nombreCcf}</b>, ni afiliarse a otra Caja de Compensación.</span></p>
	<p>&nbsp;</p>
	<p><span style=''color:black;mso-themecolor:text1''>Se expide a solicitud del interesado.</span></p>
	<p><span style=''color:black;mso-themecolor:text1''>&nbsp;</span></p>
	<p><span style=''color:black;mso-themecolor:text1''>NOS RESERVAMOS EL DERECHO DE REALZAR AUDITORIA POSTERIOR.</span></p>
	<p style=''text-align:justify''>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>${contenido}</p>
	<p>&nbsp;</p> <p><i style=''mso-bidi-font-style:normal''>Cordialmente, </i></p>
	<p><span>&nbsp;</span></p>
	<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${responsableCcf}</b></p>
	<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${cargoResponsableCcf}</b></p>',
	pcoPie='<p><span>&nbsp;</span></p>'
where pcoEtiqueta='COM_GEN_CER_APO';

--changeset abaquero:01
--comment: Adición de campos de canal de recepción de reintegro por aportes o afiliación
ALTER TABLE dbo.RolAfiliado ADD roaCanalReingreso varchar(21)
ALTER TABLE dbo.RolAfiliado ADD roaReferenciaAporteReingreso bigint
ALTER TABLE dbo.RolAfiliado ADD roaReferenciaSolicitudReingreso bigint
ALTER TABLE aud.RolAfiliado_aud ADD roaCanalReingreso varchar(21)
ALTER TABLE aud.RolAfiliado_aud ADD roaReferenciaAporteReingreso bigint
ALTER TABLE aud.RolAfiliado_aud ADD roaReferenciaSolicitudReingreso bigint