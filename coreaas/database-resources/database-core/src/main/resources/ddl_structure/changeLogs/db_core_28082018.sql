--liquibase formatted sql

--changeset jvelandia:01
--comment: 

INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Certificado Paz y Salvo','<p style=''text-align:justify;line-height:normal''>S - AP</p>
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
	Solicito su desafiliación para afiliación a otra Caja de Compensación Familiar, pagando sus aportes correspondientes al 4% a ésta corporación hasta el mes de ${mesFinAporteCertificado} </b></p>
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
	<p>&nbsp;</p>','<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>Certificado Paz y Salvo</b></p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal; margin-top:5px;''>${logoDeLaCcf}</p>
	<p>&nbsp;</p>',null,'Certificado Paz y Salvo','<p><i style=''mso-bidi-font-style:normal''>Cordialmente, </i></p>
	<p><span>&nbsp;</span></p>
	<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${responsableCcf}</b></p>
	<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${cargoResponsableCcf}</b></p>','COM_GEN_CER_PYS_EMP') ;


INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS_EMP')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','responsable caja de compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS_EMP')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo del responsable del envío del comunicado en la caja','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS_EMP')) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaGeneracion}','0','Fecha de generación','Fecha de generación del certificado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCompleto}','0','Nombre de la persona','Nombre de la persona sujeta al certificado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento  asociado a la direccion de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${seccionAfiliacion}','0','Texto variable','Texto que cambia según si es persona o empleador','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoTrabajador}','0','Texto trabajador','Texto pa indicar si es señor o empresa','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo de identificacion','Tipo de identificacion de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número de identificacion','Número de identificacion de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','0','Nombre de la CCF','Nombre de la Caja escogido en la generación del certificado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${contenido}','0','Texto usuario','Texto que adiciona el usuario','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${mesFinAporteCertificado}','0','Texto usuario','Mes Fin correspondiente certificado Paz y Salvo','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${mesInAporteCertificado}','0','Texto usuario','Mes Inicial correspondiente certificado Paz y Salvo','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS_EMP') ) ;


DELETE FROM PlantillaComunicado WHERE pcoEtiqueta='COM_GEN_CER_PYS'

INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Certificado Paz y Salvo','<p style=''text-align:justify;line-height:normal''>S - AP</p>
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
	Solicito su desafiliación de la caja como ${tipoAfiliado} , pagando sus aportes correspondientes según el tipo de afiliación hasta el mes de ${mesFinAporteCertificado} </b></p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Se expide la presente certificación a ${fechaGeneracion} y es válida como Paz y Salvo.</p>
	<p style=''text-align:justify''>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>${contenido}</p>
	<p><span style=''color:black;mso-themecolor:text1''>NOS RESERVAMOS EL DERECHO DE REALZAR AUDITORIA POSTERIOR.</span></p>
	<p><span style=''color:black;mso-themecolor:text1''>&nbsp;</span></p>
	<p><span style=''color:black;mso-themecolor:text1''>Se expide a solicitud del interesado.</span></p>
	<p>&nbsp;</p>','<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>Certificado Paz y Salvo</b></p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal; margin-top:5px;''>${logoDeLaCcf}</p>
	<p>&nbsp;</p>',null,'Certificado Paz y Salvo','<p><i style=''mso-bidi-font-style:normal''>Cordialmente, </i></p>
	<p><span>&nbsp;</span></p>
	<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${responsableCcf}</b></p>
	<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${cargoResponsableCcf}</b></p>','COM_GEN_CER_PYS') ;


INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','responsable caja de compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo del responsable del envío del comunicado en la caja','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS')) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaGeneracion}','0','Fecha de generación','Fecha de generación del certificado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCompleto}','0','Nombre de la persona','Nombre de la persona sujeta al certificado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento  asociado a la direccion de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${seccionAfiliacion}','0','Texto variable','Texto que cambia según si es persona o empleador','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoTrabajador}','0','Texto trabajador','Texto pa indicar si es señor o empresa','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo de identificacion','Tipo de identificacion de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número de identificacion','Número de identificacion de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','0','Nombre de la CCF','Nombre de la Caja escogido en la generación del certificado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${contenido}','0','Texto usuario','Texto que adiciona el usuario','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${mesFinAporteCertificado}','0','Texto usuario','Mes Fin correspondiente certificado Paz y Salvo','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoAfiliado}','0','Tipo de afiliado','Tipo de afiliado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_PYS') ) ;

UPDATE PlantillaComunicado set pcoCuerpo='<p style=''text-align:justify;line-height:normal''>S - AP</p>  <p style=''text-align:justify;line-height:normal''>FU - SUB</p>  <p>&nbsp;</p>  <p style=''text-align:justify;line-height:normal''>(<i style=''mso-bidi-font-style:normal''>Ciudad</i>), ${fechaGeneracion}</p>  <p>&nbsp;</p>  <p style=''text-align:justify;line-height:normal''>Señor (a)</p>  <p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${nombreCompleto}</b></p>  <p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''><span style=''mso-spacerun:yes''> </span>${direccionPrincipal} – ${municipio} ${departamento}</b></p>  <p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${telefono}</b></p>  <p>&nbsp;</p>  <p style=''text-align:justify;line-height:normal''>Ref.: ${tipoCertificado} </p>  <p>&nbsp;</p>  <p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>${nombreCcf}</b></p>  <p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Sección de afiliación ${seccionAfiliacion}</b></p>  <p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Certifica</b></p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p style=''text-align:justify;line-height:normal''>Que ${tipoTrabajador} <b style=''mso-bidi-font-weight:normal''>${nombreCompleto} ${tipoIdentificacion} número ${numeroIdentificacion}   a la fecha ${fechaGeneracion} se encuentra afiliada a esta CAJA DE COMPENSACION FAMILIAR${trabajador}${nombreRazonSocialEmpleador}.</b></p>  <p>&nbsp;</p>  <p><span style=''color:black;mso-themecolor:text1''>${parrafoNoValidez}</span></p>  <p>&nbsp;</p>  <p><span style=''color:black;mso-themecolor:text1''>Se expide a solicitud del interesado.</span></p>  <p><span style=''color:black;mso-themecolor:text1''>&nbsp;</span></p>  <p><span style=''color:black;mso-themecolor:text1''>NOS RESERVAMOS EL DERECHO DE REALZAR AUDITORIA POSTERIOR.</span></p>  <p style=''text-align:justify''>&nbsp;</p>  <p style=''text-align:justify;line-height:normal''>${contenido}</p>  <p>&nbsp;</p>',
pcoEncabezado='<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>Certificado de afiliación ${tipoAfiliado}</b></p>  <p style=''text-align:justify;line-height:normal''>&nbsp;</p>  <p style=''text-align:justify;line-height:normal''>&nbsp;</p>  <p style=''text-align:justify;line-height:normal; margin-top:5px;''>${logoDeLaCcf}</p>  <p style=''text-align:justify;line-height:normal''>&nbsp;</p>  <p style=''text-align:justify;line-height:normal''>&nbsp;</p>'
WHERE pcoEtiqueta='COM_GEN_CER_AFI';

UPDATE PlantillaComunicado set pcoCuerpo='<p style=''text-align:justify;line-height:normal''>S - AP</p>  <p style=''text-align:justify;line-height:normal''>FU - SUB</p>  <p>&nbsp;</p>  <p style=''text-align:justify;line-height:normal''>(<i style=''mso-bidi-font-style:normal''>Ciudad</i>), ${fechaGeneracion}</p>  <p>&nbsp;</p>  <p style=''text-align:justify;line-height:normal''>Señor (a)</p>  <p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${nombreCompleto}</b></p>  <p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${tipoSolicitante}</b></p>  <p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''><span style=''mso-spacerun:yes''> </span>${direccionPrincipal} – ${municipio} ${departamento}</b></p>  <p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${telefono}</b></p>  <p>&nbsp;</p>  <p style=''text-align:justify;line-height:normal''>Ref.: Certificado de historia de afiliación ${refCertificado}</p>  <p>&nbsp;</p>  <p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>${nombreCcf}</b></p>  <p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Sección de afiliación ${seccionAfiliacion}</b></p>  <p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Certifica</b></p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p style=''text-align:justify;line-height:normal''>Que ${tipoTrabajador} <b style=''mso-bidi-font-weight:normal''>${nombreCompleto} ${tipoIdentificacion} número ${numeroIdentificacion}   a la fecha ${fechaGeneracion} presenta la siguiente historia de afiliación a esta CAJA DE COMPENSACION FAMILIAR.</b></p>  <p>&nbsp;</p>  ${tabla}  <p>&nbsp;</p>  <p><span style=''color:black;mso-themecolor:text1''>Esta certificación no es válida para retirarse de <b style=''mso-bidi-font-weight:normal''>${nombreCcf}</b>, ni afiliarse a otra Caja de Compensación.</span></p>  <p>&nbsp;</p>  <p><span style=''color:black;mso-themecolor:text1''>Se expide a solicitud del interesado.</span></p>  <p><span style=''color:black;mso-themecolor:text1''>&nbsp;</span></p>  <p><span style=''color:black;mso-themecolor:text1''>NOS RESERVAMOS EL DERECHO DE REALZAR AUDITORIA POSTERIOR.</span></p>  <p style=''text-align:justify''>&nbsp;</p>  <p style=''text-align:justify;line-height:normal''>${contenido}</p>  <p>&nbsp;</p>',
pcoEncabezado='<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>Certificado histórico de afiliación</b></p>  <p>&nbsp;</p>  <p style=''text-align:justify;line-height:normal; margin-top:5px;''>${logoDeLaCcf}</p>  <p>&nbsp;</p>'
WHERE pcoEtiqueta='COM_GEN_CER_HIS_AFI';

