--liquibase formatted sql

--changeset jvelandia:01
--comment: Se crea tabla Certificado
CREATE TABLE Certificado(
	cerId bigint IDENTITY(1,1) NOT NULL,
	cerTipoCertificado varchar(40) NOT NULL,
	cerFechaGeneracion datetime2 NOT NULL,
    cerDirigidoA varchar(255) NOT NULL,
    cerPersona bigint NOT NULL,
	cerGeneradoComoEmpleador bit NOT NULL,
    cerTipoSolicitud varchar(100) NULL,
    cerComunicado bigint NULL,
    cerAnio smallint NULL,
    cerTipoAfiliado varchar(30) NULL,
    cerEmpleador bigint NULL
 CONSTRAINT PK_Certificado_cerId PRIMARY KEY CLUSTERED (cerId ASC)
);

--changeset jvelandia:02
--comment: Se crea tabla Certificado
ALTER TABLE Certificado  WITH CHECK ADD  CONSTRAINT FK_Certificado_cerPersona FOREIGN KEY(cerPersona)
REFERENCES Persona (perId)
ALTER TABLE Certificado CHECK CONSTRAINT FK_Certificado_cerPersona;

ALTER TABLE Certificado  WITH CHECK ADD  CONSTRAINT FK_Certificado_cerComunicado FOREIGN KEY(cerComunicado)
REFERENCES Comunicado (comId)
ALTER TABLE Certificado CHECK CONSTRAINT FK_Certificado_cerComunicado;

ALTER TABLE Certificado  WITH CHECK ADD  CONSTRAINT FK_Certificado_cerEmpleador FOREIGN KEY(cerEmpleador)
REFERENCES Empleador (empId)
ALTER TABLE Certificado CHECK CONSTRAINT FK_Certificado_cerEmpleador;

--changeset jvelandia:03
--comment: Se crea tabla Certificado_aud
CREATE TABLE aud.Certificado_aud(
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	cerId bigint NOT NULL,
	cerTipoCertificado varchar(40) NOT NULL,
	cerFechaGeneracion datetime2 NOT NULL,
    cerDirigidoA varchar(255) NOT NULL,
    cerPersona bigint NOT NULL,
	cerGeneradoComoEmpleador bit NOT NULL,
    cerTipoSolicitud varchar(100) NULL,
    cerComunicado bigint NULL,
    cerAnio smallint NULL,
    cerTipoAfiliado varchar(30) NULL,
    cerEmpleador bigint NULL 
);

--changeset jvelandia:04
--comment: Se agregan constraints tabla Certificado_aud
ALTER TABLE aud.Certificado_aud  WITH CHECK ADD  CONSTRAINT FK_Certificado_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId);
ALTER TABLE aud.Certificado_aud CHECK CONSTRAINT FK_Certificado_aud_REV;

--changeset jvelandia:05
--comment: Configuracion plantillas
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) 
VALUES('Certificado de Afiliación','<br style=''mso-ignore:vglayout'' clear=ALL>
<p style=''text-align:justify;line-height:normal''>S - AP</p>
<p style=''text-align:justify;line-height:normal''>FU - SUB</p>
<p>&nbsp;</p>
<p style=''text-align:justify;line-height:normal''>(<i style=''mso-bidi-font-style:normal''>Ciudad</i>), ${fechaGeneracion}</p>
<p>&nbsp;</p>
<p style=''text-align:justify;line-height:normal''>Señor (a)</p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${nombreCompleto}</b></p>
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
a la fecha ${fechaGeneracion} se encuentra afiliada a esta CAJA DE COMPENSACION FAMILIAR${trabajador}${nombreRazonSocialEmpleador}.</b></p>
<p>&nbsp;</p>
<p><span style=''color:black;mso-themecolor:text1''>${parrafoNoValidez}</span></p>
<p>&nbsp;</p>
<p><span style=''color:black;mso-themecolor:text1''>Se expide a solicitud del interesado.</span></p>
<p><span style=''color:black;mso-themecolor:text1''>&nbsp;</span></p>
<p><span style=''color:black;mso-themecolor:text1''>NOS RESERVAMOS EL DERECHO DE REALZAR AUDITORIA POSTERIOR.</span></p>
<p style=''text-align:justify''>&nbsp;</p>
<p style=''text-align:justify;line-height:normal''>${contenido}</p>
<p>&nbsp;</p>','<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>Certificado de afiliación ${tipoAfiliado}</b></p>
<p>&nbsp;</p>
<p style=''text-align:justify;line-height:normal''>${logoDeLaCcf}</p>
<p>&nbsp;</p>',null,'Certificado de Afiliación','<p><i style=''mso-bidi-font-style:normal''>Cordialmente, </i></p>
<p><span>&nbsp;</span></p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${responsableCcf}</b></p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${cargoResponsableCcf}</b></p>','COM_GEN_CER_AFI') ;

INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','responsable caja de compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo del responsable del envío del comunicado en la caja','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI')) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoAfiliado}','0','Tipo de afiliado','Tipo de afiliado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaGeneracion}','0','Fecha de generación','Fecha de generación del certificado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCompleto}','0','Nombre de la persona','Nombre de la persona sujeta al certificado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionPrincipal}','0','Direccion principal','Direccion principal de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio asociado a la direccion de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento  asociado a la direccion de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Teléfono de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoCertificado}','0','Tipo de certificado','Tipo de certificado generado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${seccionAfiliacion}','0','Texto variable','Texto que cambia según si es persona o empleador','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoTrabajador}','0','Texto trabajador','Texto pa indicar si es señor o empresa','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo de identificacion','Tipo de identificacion de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número de identificacion','Número de identificacion de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${trabajador}','0','Texto dependientes','Texto quemado para dependientes','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreRazonSocialEmpleador}','0','Nombre empleador dependientes','Nombre del empleador para dependientes','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${parrafoNoValidez}','1','Parrafo empleadores','Parrafo que aplica para empleadores','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','0','Nombre de la CCF','Nombre de la Caja escogido en la generación del certificado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${contenido}','0','Texto usuario','Texto que adiciona el usuario','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI') ) ;

INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Certificado histórico de afiliaciones','<br style=''mso-ignore:vglayout'' clear=ALL>
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
<p>&nbsp;</p>','<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>Certificado histórico de afiliación</b></p>
<p>&nbsp;</p>
<p style=''text-align:justify;line-height:normal''>${logoDeLaCcf}</p>
<p>&nbsp;</p>',null,'Certificado histórico de afiliaciones','<p><i style=''mso-bidi-font-style:normal''>Cordialmente, </i></p>
<p><span>&nbsp;</span></p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${responsableCcf}</b></p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${cargoResponsableCcf}</b></p>','COM_GEN_CER_HIS_AFI') ;

INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_HIS_AFI')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','responsable caja de compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_HIS_AFI')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo del responsable del envío del comunicado en la caja','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_HIS_AFI')) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaGeneracion}','0','Fecha de generación','Fecha de generación del certificado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_HIS_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCompleto}','0','Nombre de la persona','Nombre de la persona sujeta al certificado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_HIS_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionPrincipal}','0','Direccion principal','Direccion principal de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_HIS_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio asociado a la direccion de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_HIS_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento  asociado a la direccion de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_HIS_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Teléfono de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_HIS_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${seccionAfiliacion}','0','Texto variable','Texto que cambia según si es persona o empleador','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_HIS_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoTrabajador}','0','Texto trabajador','Texto pa indicar si es señor o empresa','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_HIS_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo de identificacion','Tipo de identificacion de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_HIS_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número de identificacion','Número de identificacion de la persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_HIS_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoSolicitante}','1','Parrafo empleadores','Parrafo que aplica para empleadores','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_HIS_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','0','Nombre de la CCF','Nombre de la Caja escogido en la generación del certificado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_HIS_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${contenido}','0','Texto usuario','Texto que adiciona el usuario','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_HIS_AFI') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tabla}','0','Tabla','Tabla con el histórico de afiliaciones','REPORTE_VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_HIS_AFI') ) ;

INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Certificado de aportes por año','Cuerpo','Encabezado','Mensaje','Certificado de aportes por año','Pie','COM_GEN_CER_APO') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Certificado de Paz y Salvo','Cuerpo','Encabezado','Mensaje','Certificado de Paz y Salvo','Pie','COM_GEN_CER_PYS') ;




