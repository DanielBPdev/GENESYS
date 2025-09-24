--liquibase formatted sql

--changeset jvelandia:01
--comment: Actualizacion plantillas
DELETE VariableComunicado WHERE vcoPlantillaComunicado in (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO');
DELETE FROM PlantillaComunicado WHERE pcoEtiqueta='COM_GEN_CER_APO';

INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) 
VALUES('Certificado Aportes Por Año','
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
<p>&nbsp;</p>','<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>Certificado Aportes Por Año</b></p>
<p>&nbsp;</p>
<p style=''text-align:justify;line-height:normal; margin-top:5px;''>${logoDeLaCcf}</p>
<p>&nbsp;</p>',null,'Certificado de Afiliación','<p><i style=''mso-bidi-font-style:normal''>Cordialmente, </i></p>
<p><span>&nbsp;</span></p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${responsableCcf}</b></p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${cargoResponsableCcf}</b></p>','COM_GEN_CER_APO') ;


INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','responsable caja de compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo del responsable del envío del comunicado en la caja','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO')) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoAfiliado}','0','Tipo de afiliado','Tipo de afiliado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaGeneracion}','0','Fecha de generación','Fecha de generación del certificado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCompleto}','0','Nombre de la persona','Nombre de la persona sujeta al certificado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionPrincipal}','0','Direccion principal','Direccion principal de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio asociado a la direccion de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento  asociado a la direccion de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Teléfono de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoCertificado}','0','Tipo de certificado','Tipo de certificado generado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${seccionAfiliacion}','0','Texto variable','Texto que cambia según si es persona o empleador','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoTrabajador}','0','Texto trabajador','Texto pa indicar si es señor o empresa','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo de identificacion','Tipo de identificacion de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número de identificacion','Número de identificacion de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${trabajador}','0','Texto dependientes','Texto quemado para dependientes','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreRazonSocialEmpleador}','0','Nombre empleador dependientes','Nombre del empleador para dependientes','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${parrafoNoValidez}','1','Parrafo empleadores','Parrafo que aplica para empleadores','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','0','Nombre de la CCF','Nombre de la Caja escogido en la generación del certificado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${contenido}','0','Texto usuario','Texto que adiciona el usuario','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${sumAportes}','0','Texto usuario','Suma de todos los aportes recibidos','REPORTE_VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tabla}','Tabla con el histórico de afiliaciones','Tabla',null,'REPORTE_VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO')) ;
