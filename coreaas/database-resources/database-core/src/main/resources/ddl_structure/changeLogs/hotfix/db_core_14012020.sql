--liquibase formatted sql

--changeset mamonroy:01
--comment:
INSERT INTO ValidacionProceso (vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa) VALUES
('ACTIVAR_BENEFICIARIO_CONYUGE_DEPWEB','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_DEPENDIENTE_WEB','ACTIVO',1,'CONYUGE',0),
('ACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'CONYUGE',0),
('ACTIVAR_BENEFICIARIO_CONYUGE_WEB','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'CONYUGE',0),
('ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_DEPWEB','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_DEPENDIENTE_WEB','ACTIVO',1,'HERMANO_HUERFANO_DE_PADRES',0),
('ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_PRESENCIAL','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'HERMANO_HUERFANO_DE_PADRES',0),
('ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_WEB','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'HERMANO_HUERFANO_DE_PADRES',0),
('ACTIVAR_BENEFICIARIO_HIJASTRO_DEPWEB','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_DEPENDIENTE_WEB','ACTIVO',1,'HIJASTRO',0),
('ACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'HIJASTRO',0),
('ACTIVAR_BENEFICIARIO_HIJASTRO_WEB','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'HIJASTRO',0),
('ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_DEPWEB','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_DEPENDIENTE_WEB','ACTIVO',1,'HIJO_BIOLOGICO',0),
('ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'HIJO_BIOLOGICO',0),
('ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_WEB','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'HIJO_BIOLOGICO',0),
('ACTIVAR_BENEFICIARIO_MADRE_DEPWEB','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_DEPENDIENTE_WEB','ACTIVO',1,'MADRE',0),
('ACTIVAR_BENEFICIARIO_MADRE_PRESENCIAL','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'MADRE',0),
('ACTIVAR_BENEFICIARIO_MADRE_WEB','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'MADRE',0),
('ACTIVAR_BENEFICIARIO_PADRE_DEPWEB','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_DEPENDIENTE_WEB','ACTIVO',1,'PADRE',0),
('ACTIVAR_BENEFICIARIO_PADRE_PRESENCIAL','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'PADRE',0),
('ACTIVAR_BENEFICIARIO_PADRE_WEB','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'PADRE',0),
('ACTIVA_BENEFICIARIO_EN_CUSTODIA_DEPWEB','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_DEPENDIENTE_WEB','ACTIVO',1,'BENEFICIARIO_EN_CUSTODIA',0),
('ACTIVA_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'BENEFICIARIO_EN_CUSTODIA',0),
('ACTIVA_BENEFICIARIO_EN_CUSTODIA_WEB','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'BENEFICIARIO_EN_CUSTODIA',0),
('ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_DEPWEB','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_DEPENDIENTE_WEB','ACTIVO',1,'HIJO_ADOPTIVO',0),
('ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_PRESENCIAL','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'HIJO_ADOPTIVO',0),
('ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_WEB','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'HIJO_ADOPTIVO',0);


--changeset mamonroy:02
--comment:
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) 
VALUES ('Certificado Aportes por Año Persona', NULL,NULL,NULL,NULL, 'Certificado de aportes - Persona', NULL, 'COM_GEN_CER_APO_PER');

INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) 
VALUES ('Certificado de Afiliación Dependiente', NULL,NULL,NULL,NULL, 'Certificado de Afiliación - Dependiente', NULL, 'COM_GEN_CER_AFI_DEP');

INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) 
VALUES ('Certificado de Afiliación Pensionado', NULL,NULL,NULL,NULL, 'Certificado de Afiliación - Pensionado', NULL, 'COM_GEN_CER_AFI_PNS');

INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) 
VALUES ('Certificado de Afiliación Independiente', NULL,NULL,NULL,NULL, 'Certificado de Afiliación - Independiente', NULL, 'COM_GEN_CER_AFI_IDPT');

INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) 
VALUES ('Certificado Historico de Afiliación Persona', NULL,NULL,NULL,NULL, 'Certificado Historico de Afiliación - Persona', NULL, 'COM_GEN_CER_HIS_AFI_PER');

INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) 
VALUES ('Certificado Aportes por Año Empleador', NULL,NULL,NULL,NULL, 'Certificado de aportes - Empleador', NULL, 'COM_GEN_CER_APO_EMP');

INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) 
VALUES ('Certificado de Afiliación Empleador', NULL,NULL,NULL,NULL, 'Certificado de Afiliación - Empleador', NULL, 'COM_GEN_CER_AFI_EMP');

INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) 
VALUES ('Certificado Historico de Afiliación Empleador', NULL,NULL,NULL,NULL, 'Certificado Historico de Afiliación - Empleador', NULL, 'COM_GEN_CER_HIS_AFI_EMP');

INSERT INTO VariableComunicado(vcoClave ,vcoDescripcion ,vcoNombre ,vcoPlantillaComunicado ,vcoNombreConstante ,vcoTipoVariableComunicado ,vcoOrden) 
VALUES ('${logoDeLaCcf}','Logotipo de la Caja de Compensación','Logo de la CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_PER'),'LOGO_DE_LA_CCF','CONSTANTE',0),
('${ciudadCcf}','Ciudad de Ubicación de la Caja de Compensación','Ciudad CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_PER'),'CIUDAD_CCF','CONSTANTE',0),
('${fechaGeneracion}','Fecha de generación del certificado','Fecha de generación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_PER'),'','VARIABLE',0),
('${nombreCompleto}','Nombre de la persona sujeta al certificado','Nombre de la persona',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_PER'),'','VARIABLE',0),
('${direccionPrincipal}','Dirección de la persona','Dirección oficina principal',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_PER'),'','VARIABLE',0),
('${municipio}','Municipio asociado a la dirección de la persona','Municipio',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_PER'),'','VARIABLE',0),
('${departamento}','Departamento  asociado a la dirección de la persona','Departamento',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_PER'),'','VARIABLE',0),
('${telefono}','Teléfono asociado a la persona','Teléfono',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_PER'),'','VARIABLE',0),
('${nombreCcf}','Nombre de la Caja de compensación','Nombre de la CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_PER'),'','VARIABLE',0),
('${tipoIdentificacion}','Tipo de identificación de la persona','Tipo de identificación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_PER'),'','VARIABLE',0),
('${numeroIdentificacion}','Número de identificación de la persona','Número de identificación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_PER'),'','VARIABLE',0),
('${responsableCcf}','Responsable caja de compensación','Responsable CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_PER'),'RESPONSABLE_CCF','CONSTANTE',0),
('${cargoResponsableCcf}','Cargo del responsable del envío del comunicado en la caja','Cargo responsable CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_PER'),'CARGO_RESPONSABLE_CCF','CONSTANTE',0),
('${anio}','Año en que se realizaron los aportes','Año certificado',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_PER'),'','VARIABLE',0),
('${tabla}','Tabla','Tabla con el histórico de aportes del año en cuestión',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_PER'),'','REPORTE_VARIABLE',0),
('${sumAportes}','Suma de todos los aportes recibidos','Texto usuario',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_PER'),'','REPORTE_VARIABLE',0),
('${contenido}','Texto que adiciona el usuario','Texto usuario',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_PER'),'','VARIABLE',0);

INSERT INTO VariableComunicado(vcoClave ,vcoDescripcion ,vcoNombre ,vcoPlantillaComunicado ,vcoNombreConstante ,vcoTipoVariableComunicado ,vcoOrden) 
VALUES ('${logoDeLaCcf}','Logotipo de la Caja de Compensación','Logo de la CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'),'LOGO_DE_LA_CCF','CONSTANTE',0),
('${ciudadCcf}','Ciudad de Ubicación de la Caja de Compensación','Ciudad CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'),'CIUDAD_CCF','CONSTANTE',0),
('${fechaGeneracion}','Fecha de generación del certificado','Fecha de generación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'),'','VARIABLE',0),
('${nombreCompleto}','Nombre de la persona sujeta al certificado','Nombre de la persona',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'),'','VARIABLE',0),
('${direccionPrincipal}','Dirección de la persona','Dirección oficina principal',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'),'','VARIABLE',0),
('${municipio}','Municipio asociado a la dirección de la persona','Municipio',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'),'','VARIABLE',0),
('${departamento}','Departamento  asociado a la dirección de la persona','Departamento',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'),'','VARIABLE',0),
('${telefono}','Teléfono asociado a la persona','Teléfono',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'),'','VARIABLE',0),
('${nombreCcf}','Nombre de la Caja de compensación','Nombre de la CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'),'','VARIABLE',0),
('${tipoIdentificacion}','Tipo de identificación de la persona','Tipo de identificación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'),'','VARIABLE',0),
('${numeroIdentificacion}','Número de identificación de la persona','Número de identificación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'),'','VARIABLE',0),
('${razonSocialEmpleador}','Nombre o razón social del empleador asociado a la persona','Razón social empleador',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'),'','VARIABLE',0),
('${responsableCcf}','Responsable caja de compensación','Responsable CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'),'RESPONSABLE_CCF','CONSTANTE',0),
('${cargoResponsableCcf}','Cargo del responsable del envío del comunicado en la caja','Cargo responsable CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'),'CARGO_RESPONSABLE_CCF','CONSTANTE',0),
('${contenido}','Texto que adiciona el usuario','Texto usuario',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'),'','VARIABLE',0);

INSERT INTO VariableComunicado(vcoClave ,vcoDescripcion ,vcoNombre ,vcoPlantillaComunicado ,vcoNombreConstante ,vcoTipoVariableComunicado ,vcoOrden) 
VALUES ('${logoDeLaCcf}','Logotipo de la Caja de Compensación','Logo de la CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS'),'LOGO_DE_LA_CCF','CONSTANTE',0),
('${ciudadCcf}','Ciudad de Ubicación de la Caja de Compensación','Ciudad CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS'),'CIUDAD_CCF','CONSTANTE',0),
('${fechaGeneracion}','Fecha de generación del certificado','Fecha de generación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS'),'','VARIABLE',0),
('${nombreCompleto}','Nombre de la persona sujeta al certificado','Nombre de la persona',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS'),'','VARIABLE',0),
('${direccionPrincipal}','Dirección de la persona','Dirección oficina principal',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS'),'','VARIABLE',0),
('${municipio}','Municipio asociado a la dirección de la persona','Municipio',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS'),'','VARIABLE',0),
('${departamento}','Departamento  asociado a la dirección de la persona','Departamento',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS'),'','VARIABLE',0),
('${telefono}','Teléfono asociado a la persona','Teléfono',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS'),'','VARIABLE',0),
('${nombreCcf}','Nombre de la Caja de compensación','Nombre de la CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS'),'','VARIABLE',0),
('${tipoIdentificacion}','Tipo de identificación de la persona','Tipo de identificación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS'),'','VARIABLE',0),
('${numeroIdentificacion}','Número de identificación de la persona','Número de identificación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS'),'','VARIABLE',0),
('${responsableCcf}','Responsable caja de compensación','Responsable CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS'),'RESPONSABLE_CCF','CONSTANTE',0),
('${cargoResponsableCcf}','Cargo del responsable del envío del comunicado en la caja','Cargo responsable CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS'),'CARGO_RESPONSABLE_CCF','CONSTANTE',0),
('${contenido}','Texto que adiciona el usuario','Texto usuario',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS'),'','VARIABLE',0);

INSERT INTO VariableComunicado(vcoClave ,vcoDescripcion ,vcoNombre ,vcoPlantillaComunicado ,vcoNombreConstante ,vcoTipoVariableComunicado ,vcoOrden) 
VALUES ('${logoDeLaCcf}','Logotipo de la Caja de Compensación','Logo de la CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT'),'LOGO_DE_LA_CCF','CONSTANTE',0),
('${ciudadCcf}','Ciudad de Ubicación de la Caja de Compensación','Ciudad CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT'),'CIUDAD_CCF','CONSTANTE',0),
('${fechaGeneracion}','Fecha de generación del certificado','Fecha de generación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT'),'','VARIABLE',0),
('${nombreCompleto}','Nombre de la persona sujeta al certificado','Nombre de la persona',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT'),'','VARIABLE',0),
('${direccionPrincipal}','Dirección de la persona','Dirección oficina principal',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT'),'','VARIABLE',0),
('${municipio}','Municipio asociado a la dirección de la persona','Municipio',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT'),'','VARIABLE',0),
('${departamento}','Departamento  asociado a la dirección de la persona','Departamento',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT'),'','VARIABLE',0),
('${telefono}','Teléfono asociado a la persona','Teléfono',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT'),'','VARIABLE',0),
('${nombreCcf}','Nombre de la Caja de compensación','Nombre de la CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT'),'','VARIABLE',0),
('${tipoIdentificacion}','Tipo de identificación de la persona','Tipo de identificación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT'),'','VARIABLE',0),
('${numeroIdentificacion}','Número de identificación de la persona','Número de identificación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT'),'','VARIABLE',0),
('${responsableCcf}','Responsable caja de compensación','Responsable CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT'),'RESPONSABLE_CCF','CONSTANTE',0),
('${cargoResponsableCcf}','Cargo del responsable del envío del comunicado en la caja','Cargo responsable CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT'),'CARGO_RESPONSABLE_CCF','CONSTANTE',0),
('${contenido}','Texto que adiciona el usuario','Texto usuario',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT'),'','VARIABLE',0);

INSERT INTO VariableComunicado(vcoClave ,vcoDescripcion ,vcoNombre ,vcoPlantillaComunicado ,vcoNombreConstante ,vcoTipoVariableComunicado ,vcoOrden) 
VALUES ('${logoDeLaCcf}','Logotipo de la Caja de Compensación','Logo de la CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_PER'),'LOGO_DE_LA_CCF','CONSTANTE',0),
('${ciudadCcf}','Ciudad de Ubicación de la Caja de Compensación','Ciudad CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_PER'),'CIUDAD_CCF','CONSTANTE',0),
('${fechaGeneracion}','Fecha de generación del certificado','Fecha de generación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_PER'),'','VARIABLE',0),
('${nombreCompleto}','Nombre de la persona sujeta al certificado','Nombre de la persona',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_PER'),'','VARIABLE',0),
('${direccionPrincipal}','Dirección de la persona','Dirección oficina principal',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_PER'),'','VARIABLE',0),
('${municipio}','Municipio asociado a la dirección de la persona','Municipio',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_PER'),'','VARIABLE',0),
('${departamento}','Departamento  asociado a la dirección de la persona','Departamento',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_PER'),'','VARIABLE',0),
('${telefono}','Teléfono asociado a la persona','Teléfono',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_PER'),'','VARIABLE',0),
('${nombreCcf}','Nombre de la Caja de compensación','Nombre de la CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_PER'),'','VARIABLE',0),
('${tipoIdentificacion}','Tipo de identificación de la persona','Tipo de identificación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_PER'),'','VARIABLE',0),
('${numeroIdentificacion}','Número de identificación de la persona','Número de identificación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_PER'),'','VARIABLE',0),
('${responsableCcf}','Responsable caja de compensación','Responsable CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_PER'),'RESPONSABLE_CCF','CONSTANTE',0),
('${cargoResponsableCcf}','Cargo del responsable del envío del comunicado en la caja','Cargo responsable CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_PER'),'CARGO_RESPONSABLE_CCF','CONSTANTE',0),
('${tabla}','Tabla con el histórico de afiliaciones','Tabla',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_PER'),'','REPORTE_VARIABLE',0),
('${contenido}','Texto que adiciona el usuario','Texto usuario',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_PER'),'','VARIABLE',0);

INSERT INTO VariableComunicado(vcoClave ,vcoDescripcion ,vcoNombre ,vcoPlantillaComunicado ,vcoNombreConstante ,vcoTipoVariableComunicado ,vcoOrden) 
VALUES ('${logoDeLaCcf}','Logotipo de la Caja de Compensación','Logo de la CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_EMP'),'LOGO_DE_LA_CCF','CONSTANTE',0),
('${ciudadSolicitud}','Ciudad de la sede donde se realiza la solicitud','Ciudad solicitud',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_EMP'),'CIUDAD_CCF','CONSTANTE',0),
('${fechaGeneracion}','Fecha de generación del certificado','Fecha de generación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_EMP'),'','VARIABLE',0),
('${nombreRazonSocialEmpleador}','Nombre o razón social del empleador','Nombre o razón social del empleador',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_EMP'),'','VARIABLE',0),
('${tipoSolicitante}','Tipo de solicitante','Tipo solicitante',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_EMP'),'','VARIABLE',0),
('${direccionPrincipal}','Direccion principal asociada al empleador','Direccion principal',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_EMP'),'','VARIABLE',0),
('${municipio}','Municipio asociado a la dirección del empleador','Municipio',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_EMP'),'','VARIABLE',0),
('${departamento}','Departamento  asociado a la dirección del empleador','Departamento',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_EMP'),'','VARIABLE',0),
('${telefono}','Teléfono asociado al empleador','Teléfono',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_EMP'),'','VARIABLE',0),
('${nombreCcf}','Nombre de la Caja de compensación','Nombre de la CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_EMP'),'','VARIABLE',0),
('${tipoIdentificacion}','Tipo de identificación del empleador','Tipo de identificación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_EMP'),'','VARIABLE',0),
('${numeroIdentificacion}','Número de identificación del empleador','Número de identificación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_EMP'),'','VARIABLE',0),
('${anio}','Año en que se realizaron los aportes','Año certificado',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_EMP'),'','VARIABLE',0),
('${tabla}','Tabla','Tabla con el histórico de aportes del año en cuestión',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_EMP'),'','REPORTE_VARIABLE',0),
('${sumAportes}','Suma de todos los aportes recibidos','Texto usuario',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_EMP'),'','REPORTE_VARIABLE',0),
('${responsableCcf}','Responsable caja de compensación','Responsable CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_EMP'),'RESPONSABLE_CCF','CONSTANTE',0),
('${cargoResponsableCcf}','Cargo del responsable del envío del comunicado en la caja','Cargo responsable CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_EMP'),'CARGO_RESPONSABLE_CCF','CONSTANTE',0),
('${contenido}','Texto que adiciona el usuario','Texto usuario',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_EMP'),'','VARIABLE',0);

INSERT INTO VariableComunicado(vcoClave ,vcoDescripcion ,vcoNombre ,vcoPlantillaComunicado ,vcoNombreConstante ,vcoTipoVariableComunicado ,vcoOrden) 
VALUES ('${logoDeLaCcf}','Logotipo de la Caja de Compensación','Logo de la CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_EMP'),'LOGO_DE_LA_CCF','CONSTANTE',0),
('${ciudadSolicitud}','Ciudad de la sede donde se realiza la solicitud','Ciudad solicitud',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_EMP'),'CIUDAD_CCF','CONSTANTE',0),
('${fechaGeneracion}','Fecha de generación del certificado','Fecha de generación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_EMP'),'','VARIABLE',0),
('${nombreRazonSocialEmpleador}','Nombre o razón social del empleador','Nombre o razón social del empleador',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_EMP'),'','VARIABLE',0),
('${tipoSolicitante}','Tipo de solicitante','Tipo solicitante',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_EMP'),'','VARIABLE',0),
('${direccionPrincipal}','Direccion principal asociada al empleador','Direccion principal',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_EMP'),'','VARIABLE',0),
('${municipio}','Municipio asociado a la dirección del empleador','Municipio',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_EMP'),'','VARIABLE',0),
('${departamento}','Departamento  asociado a la dirección del empleador','Departamento',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_EMP'),'','VARIABLE',0),
('${telefono}','Teléfono asociado al empleador','Teléfono',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_EMP'),'','VARIABLE',0),
('${nombreCcf}','Nombre de la Caja de compensación','Nombre de la CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_EMP'),'','VARIABLE',0),
('${tipoIdentificacion}','Tipo de identificación del empleador','Tipo de identificación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_EMP'),'','VARIABLE',0),
('${numeroIdentificacion}','Número de identificación del empleador','Número de identificación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_EMP'),'','VARIABLE',0),
('${responsableCcf}','Responsable caja de compensación','Responsable CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_EMP'),'RESPONSABLE_CCF','CONSTANTE',0),
('${cargoResponsableCcf}','Cargo del responsable del envío del comunicado en la caja','Cargo responsable CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_EMP'),'CARGO_RESPONSABLE_CCF','CONSTANTE',0),
('${contenido}','Texto que adiciona el usuario','Texto usuario',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_EMP'),'','VARIABLE',0);

INSERT INTO VariableComunicado(vcoClave ,vcoDescripcion ,vcoNombre ,vcoPlantillaComunicado ,vcoNombreConstante ,vcoTipoVariableComunicado ,vcoOrden) 
VALUES ('${logoDeLaCcf}','Logotipo de la Caja de Compensación','Logo de la CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_EMP'),'LOGO_DE_LA_CCF','CONSTANTE',0),
('${ciudadSolicitud}','Ciudad de la sede donde se realiza la solicitud','Ciudad solicitud',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_EMP'),'CIUDAD_CCF','CONSTANTE',0),
('${fechaGeneracion}','Fecha de generación del certificado','Fecha de generación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_EMP'),'','VARIABLE',0),
('${nombreRazonSocialEmpleador}','Nombre o razón social del empleador','Nombre o razón social del empleador',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_EMP'),'','VARIABLE',0),
('${tipoSolicitante}','Tipo de solicitante','Tipo solicitante',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_EMP'),'','VARIABLE',0),
('${direccionPrincipal}','Direccion principal asociada al empleador','Direccion principal',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_EMP'),'','VARIABLE',0),
('${municipio}','Municipio asociado a la dirección del empleador','Municipio',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_EMP'),'','VARIABLE',0),
('${departamento}','Departamento  asociado a la dirección del empleador','Departamento',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_EMP'),'','VARIABLE',0),
('${telefono}','Teléfono asociado al empleador','Teléfono',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_EMP'),'','VARIABLE',0),
('${nombreCcf}','Nombre de la Caja de compensación','Nombre de la CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_EMP'),'','VARIABLE',0),
('${tipoIdentificacion}','Tipo de identificación del empleador','Tipo de identificación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_EMP'),'','VARIABLE',0),
('${numeroIdentificacion}','Número de identificación del empleador','Número de identificación',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_EMP'),'','VARIABLE',0),
('${responsableCcf}','Responsable caja de compensación','Responsable CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_EMP'),'RESPONSABLE_CCF','CONSTANTE',0),
('${cargoResponsableCcf}','Cargo del responsable del envío del comunicado en la caja','Cargo responsable CCF',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_EMP'),'CARGO_RESPONSABLE_CCF','CONSTANTE',0),
('${tabla}','Tabla','Tabla con el histórico de aportes del año en cuestión',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_EMP'),'','REPORTE_VARIABLE',0),
('${contenido}','Texto que adiciona el usuario','Texto usuario',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_EMP'),'','VARIABLE',0);

--changeset mamonroy:03
--comment:
INSERT INTO DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES 
('VISTAS_360','COM_GEN_CER_APO_PER'),
('VISTAS_360','COM_GEN_CER_AFI_DEP'),
('VISTAS_360','COM_GEN_CER_AFI_PNS'),
('VISTAS_360','COM_GEN_CER_AFI_IDPT'),
('VISTAS_360','COM_GEN_CER_HIS_AFI_PER'),
('VISTAS_360','COM_GEN_CER_APO_EMP'),
('VISTAS_360','COM_GEN_CER_AFI_EMP'),
('VISTAS_360','COM_GEN_CER_HIS_AFI_EMP');

--changeset mamonroy:04
--comment:
INSERT INTO VariableComunicado(vcoClave ,vcoDescripcion ,vcoNombre ,vcoPlantillaComunicado ,vcoNombreConstante ,vcoTipoVariableComunicado ,vcoOrden) 
VALUES ('${tipoSolicitante}','Tipo de solicitante','Tipo solicitante',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'),'','VARIABLE',0),
('${tipoSolicitante}','Tipo de solicitante','Tipo solicitante',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS'),'','VARIABLE',0),
('${tipoSolicitante}','Tipo de solicitante','Tipo solicitante',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT'),'','VARIABLE',0);

--changeset mamonroy:05
--comment:
UPDATE PlantillaComunicado SET pcoCuerpo = '<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p>&nbsp;</p>
<p style="line-height: normal; text-align: center;"><strong>SECCI&Oacute;N DE APORTES Y SUBSIDIOS</strong></p>
<p style="text-align: center;"><strong>CERTIFICA</strong></p>
<p style="text-align: justify; line-height: normal;">Que el se&ntilde;or&nbsp;<strong>${nombreCompleto} ${tipoIdentificacion} ${numeroIdentificacion}&nbsp;</strong>a la fecha ${fechaGeneracion} se encuentra afiliada a la&nbsp;CAJA DE COMPENSACION FAMILIAR DE SUCRE.&nbsp;</p>
<p>Esta certificaci&oacute;n no es v&aacute;lida para retirarse de ${nombreCcf}, ni para afiliarse a otra Caja de Compensaci&oacute;n.</p>
<p><span>Se expide a solicitud del interesado.</span></p>
<p>${ciudadCcf},&nbsp; ${fechaGeneracion}</p>
<p>&nbsp;</p>
<p>Cordialmente,</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;"><strong>YARLEDY BLAND&Oacute;N BLAND&Oacute;N</strong></p>
<p style="text-align: justify; line-height: normal;"><strong>Subgerente Aportes y Subsidios</strong></p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;"><strong>Tipo Solicitante ${tipoSolicitante}</strong></p>' ,pcoEncabezado = '<p style="text-align: justify; line-height: normal;">${logoDeLaCcf}</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">S - AP</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">FU - SUB</p>' , pcoMensaje = '<p>${logoDeLaCcf}</p>
<p>&nbsp;</p>
<p>${ciudadCcf},&nbsp;${fechaGeneracion}&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>Se&ntilde;or aportante, adjunto le estamos haciendo llegar el certificado solicitado.</p>
<p>&nbsp;</p>' ,pcoPie = '<p>&nbsp;&nbsp;</p>' FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP';

UPDATE PlantillaComunicado SET pcoCuerpo = '<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p>&nbsp;</p>
<p style="line-height: normal; text-align: center;"><strong>SECCI&Oacute;N DE APORTES Y SUBSIDIOS</strong></p>
<p style="text-align: center;"><strong>CERTIFICA</strong></p>
<p style="text-align: justify; line-height: normal;">Que la empresa&nbsp;<strong>${nombreRazonSocialEmpleador}</strong>&nbsp;<strong>&nbsp;${tipoIdentificacion} ${numeroIdentificacion}&nbsp;</strong>a la fecha ${fechaGeneracion} se encuentra afiliada a la&nbsp;CAJA DE COMPENSACION FAMILIAR DE SUCRE.&nbsp;</p>
<p>Esta certificaci&oacute;n no es v&aacute;lida para retirarse de ${nombreCcf}, ni para afiliarse a otra Caja de Compensaci&oacute;n.</p>
<p>Se expide a solicitud del interesado.</p>
<p>${ciudadSolicitud},&nbsp; ${fechaGeneracion}</p>
<p>&nbsp;</p>
<p>Cordialmente,</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;"><strong>YARLEDY BLAND&Oacute;N BLAND&Oacute;N</strong></p>
<p style="text-align: justify; line-height: normal;"><strong>Subgerente Aportes y Subsidios</strong></p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;"><strong>Tipo Solicitante ${tipoSolicitante}</strong>&nbsp;</p>' ,pcoEncabezado = '<p style="text-align: justify; line-height: normal;">${logoDeLaCcf}</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">S - AP</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">FU - SUB</p>' , pcoMensaje = '<p>${logoDeLaCcf}</p>
<p>&nbsp;</p>
<p>${ciudadSolicitud},&nbsp;${fechaGeneracion}&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>Se&ntilde;or aportante, adjunto le estamos haciendo llegar el certificado solicitado.</p>
<p>&nbsp;</p>' ,pcoPie = '<p>&nbsp; &nbsp; &nbsp;</p>' FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_EMP';

UPDATE PlantillaComunicado SET pcoCuerpo = '<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p>&nbsp;</p>
<p style="line-height: normal; text-align: center;"><strong>SECCI&Oacute;N DE APORTES Y SUBSIDIOS</strong></p>
<p style="text-align: center;"><strong>CERTIFICA</strong></p>
<p style="text-align: justify; line-height: normal;">Que el se&ntilde;or&nbsp;<strong>${nombreCompleto} ${tipoIdentificacion} ${numeroIdentificacion}&nbsp;</strong>a la fecha ${fechaGeneracion} se encuentra afiliada a la&nbsp;CAJA DE COMPENSACION FAMILIAR DE SUCRE.&nbsp;</p>
<p>Esta certificaci&oacute;n no es v&aacute;lida para retirarse de ${nombreCcf}, ni para afiliarse a otra Caja de Compensaci&oacute;n.</p>
<p>Se expide a solicitud del interesado.</p>
<p>${ciudadCcf},&nbsp; ${fechaGeneracion}</p>
<p>&nbsp;</p>
<p>Cordialmente,</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;"><strong>YARLEDY BLAND&Oacute;N BLAND&Oacute;N</strong></p>
<p style="text-align: justify; line-height: normal;"><strong>Subgerente Aportes y Subsidios</strong></p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;"><strong>Tipo Solicitante ${tipoSolicitante}</strong></p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>' ,pcoEncabezado = '<p style="text-align: justify; line-height: normal;">${logoDeLaCcf}</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">S - AP</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">FU - SUB</p>' , pcoMensaje = '<p>${logoDeLaCcf}</p>
<p>&nbsp;</p>
<p>${ciudadCcf},&nbsp;${fechaGeneracion}&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>Se&ntilde;or aportante, adjunto le estamos haciendo llegar el certificado solicitado.</p>
<p>&nbsp;</p>' ,pcoPie = '<p>&nbsp; &nbsp; &nbsp;</p>' FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT';

UPDATE PlantillaComunicado SET pcoCuerpo = '<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p>&nbsp;</p>
<p style="line-height: normal; text-align: center;"><strong>SECCI&Oacute;N DE APORTES Y SUBSIDIOS</strong></p>
<p style="text-align: center;"><strong>CERTIFICA</strong></p>
<p style="text-align: justify; line-height: normal;">Que el se&ntilde;or&nbsp;<strong>${nombreCompleto} ${tipoIdentificacion} ${numeroIdentificacion}&nbsp;</strong>a la fecha ${fechaGeneracion} se encuentra afiliada a la&nbsp;CAJA DE COMPENSACION FAMILIAR DE SUCRE.&nbsp;</p>
<p>Esta certificaci&oacute;n no es v&aacute;lida para retirarse de ${nombreCcf}, ni para afiliarse a otra Caja de Compensaci&oacute;n.</p>
<p>Se expide a solicitud del interesado.</p>
<p>${ciudadCcf},&nbsp; ${fechaGeneracion}</p>
<p>&nbsp;</p>
<p>Cordialmente,</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;"><strong>YARLEDY BLAND&Oacute;N BLAND&Oacute;N</strong></p>
<p style="text-align: justify; line-height: normal;"><strong>Subgerente Aportes y Subsidios</strong></p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;"><strong>Tipo Solicitante ${tipoSolicitante}</strong></p>' ,pcoEncabezado = '<p style="text-align: justify; line-height: normal;">${logoDeLaCcf}</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">S - AP</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">FU - SUB</p>' , pcoMensaje = '<p>${logoDeLaCcf}</p>
<p>&nbsp;</p>
<p>${ciudadCcf},&nbsp;${fechaGeneracion}&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>Se&ntilde;or aportante, adjunto le estamos haciendo llegar el certificado solicitado.</p>
<p>&nbsp;</p>' ,pcoPie = '<p>&nbsp; &nbsp;</p>' FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS';

UPDATE PlantillaComunicado SET pcoCuerpo = '<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p>Para los efectos del art&iacute;culo 3 de la Ley 21 de 1982</p>
<p>&nbsp;</p>
<p style="text-align: center;">&nbsp;</p>
<p style="text-align: center;"><strong>CERTIFICA</strong></p>
<p>&nbsp;&nbsp;</p>
<p style="text-align: justify; line-height: normal;">Que la empresa ${nombreRazonSocialEmpleador} con identificaci&oacute;n ${tipoIdentificacion} ${numeroIdentificacion} ha pagado a la Caja de Compensaci&oacute;n Familiar ${nombreCcf} , la suma de&nbsp;${sumAportes} por concepto de pago de aportes parafiscales con destino al Sistema de Compensaci&oacute;n Familiar, por la vigencia de&nbsp;<strong>${anio}</strong>&nbsp;del per&iacute;odo comprendido entre Enero y Diciembre.</p>
<p>&nbsp;</p>
<p>A efecto que se le reconozca la deducci&oacute;n a que tenga derecho por los salarios declarados y que sirvieron de base para la liquidaci&oacute;n.</p>
<p>&nbsp;</p>
<p>El presente documento no es v&aacute;lido para traslado a otra Caja.</p>
<p>&nbsp;</p>
<p>${municipio}, ${fechaGeneracion}</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p style="text-align: justify;">Cordialmente,</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>YARLEDY BLAND&Oacute;N BLAND&Oacute;N</p>
<p>Subgerente de Aportes y Subsidios&nbsp;</p>
<p>&nbsp;</p>' ,pcoEncabezado = '<p style="text-align: justify; line-height: normal;">${logoDeLaCcf}</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">S - AP</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">FU - SUB</p>' , pcoMensaje = '<p>&nbsp;${logoDeLaCcf}&nbsp;</p>
<p>&nbsp;</p>
<p>${municipio},&nbsp;${fechaGeneracion}&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>Se&ntilde;or (a)</p>
<p>${nombreRazonSocialEmpleador}</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p><strong>Referencia:</strong>&nbsp;Certificado de aportes</p>' ,pcoPie = '<p>&nbsp; &nbsp;&nbsp;</p>' FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_EMP';

UPDATE PlantillaComunicado SET pcoCuerpo = '<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p>Para los efectos del art&iacute;culo 3 de la Ley 21 de 1982</p>
<p>&nbsp;</p>
<p style="text-align: center;">&nbsp;</p>
<p style="text-align: center;"><strong>CERTIFICA</strong></p>
<p>&nbsp;&nbsp;</p>
<p style="text-align: justify; line-height: normal;">Que el se&ntilde;or&nbsp;${nombreCompleto}&nbsp;con identificaci&oacute;n ${tipoIdentificacion} ${numeroIdentificacion} ha pagado a la Caja de Compensaci&oacute;n Familiar ${nombreCcf} , la suma de&nbsp;${sumAportes} por concepto de pago de aportes parafiscales con destino al Sistema de Compensaci&oacute;n Familiar, por la vigencia de&nbsp;<strong>${anio}</strong>&nbsp;del per&iacute;odo comprendido entre Enero y Diciembre.</p>
<p>&nbsp;</p>
<p>A efecto que se le reconozca la deducci&oacute;n a que tenga derecho por los salarios declarados y que sirvieron de base para la liquidaci&oacute;n.</p>
<p>&nbsp;</p>
<p>El presente documento no es v&aacute;lido para traslado a otra Caja.</p>
<p>&nbsp;</p>
<p>${municipio}, ${fechaGeneracion}</p>
<p>&nbsp;&nbsp;</p>
<p>&nbsp;</p>
<p style="text-align: justify;">Cordialmente,</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>YARLEDY BLAND&Oacute;N BLAND&Oacute;N</p>
<p>Subgerente de Aportes y Subsidios&nbsp;</p>' ,pcoEncabezado = '<p style="text-align: justify; line-height: normal;">${logoDeLaCcf}</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">S - AP</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">FU - SUB</p>' , pcoMensaje = '<p>&nbsp;${logoDeLaCcf}&nbsp;</p>
<p>&nbsp;</p>
<p>${municipio},&nbsp;${fechaGeneracion}&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>Se&ntilde;or (a)</p>
<p>${<span style="text-align: justify;">nombreCompleto</span>}</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p><strong>Referencia:</strong> Certificado de aportes</p>' ,pcoPie = '<p>&nbsp; &nbsp;</p>' FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_APO_PER';

UPDATE PlantillaComunicado SET pcoCuerpo = '<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="line-height: normal; text-align: center;"><strong>SECCI&Oacute;N DE APORTES Y SUBSIDIOS</strong></p>
<p style="text-align: center;"><strong>CERTIFICA</strong></p>
<p>&nbsp;</p>
<p style="text-align: justify; line-height: normal;">Que la empresa&nbsp;${nombreRazonSocialEmpleador}&nbsp;<strong>&nbsp;${tipoIdentificacion} ${numeroIdentificacion}&nbsp;</strong>a la fecha ${fechaGeneracion} presenta la siguiente historia de afiliaci&oacute;n a esta CAJA DE COMPENSACION FAMILIAR.</p>
<p>&nbsp;</p>
<p>${tabla}</p>
<p>&nbsp;</p>
<p>Esta certificaci&oacute;n no es v&aacute;lida para retirarse de<strong>&nbsp;${nombreCcf}</strong>, ni afiliarse a otra Caja de Compensaci&oacute;n.</p>
<p>&nbsp;</p>
<p>Se expide a solicitud del interesado.</p>
<p>${<span style="text-align: justify;">ciudadSolicitud</span>}, ${fechaGeneracion}</p>
<p>&nbsp;</p>
<p>Cordialmente,</p>
<p>&nbsp;</p>
<p style="text-align: justify; line-height: normal;"><strong>YARLEDY BLAND&Oacute;N BLAND&Oacute;N</strong></p>
<p style="text-align: justify; line-height: normal;"><strong>Subgerente Aportes y Subsidios</strong></p>
<p style="text-align: justify;">&nbsp;</p>' ,pcoEncabezado = '<p style="text-align: justify; line-height: normal;">${logoDeLaCcf}</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">S - AP</p>
<p style="text-align: justify; line-height: normal;">FU - SUB&nbsp;</p>' , pcoMensaje = '<p>${logoDeLaCcf}</p>
<p>&nbsp;</p>
<p>${ciudadCcf},&nbsp;${fechaGeneracion}&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>Se&ntilde;or aportante, adjunto le estamos haciendo llegar el certificado solicitado.</p>' ,pcoPie = '<p>&nbsp; &nbsp;</p>' FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_EMP';

UPDATE PlantillaComunicado SET pcoCuerpo = '<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="line-height: normal; text-align: center;"><strong>SECCI&Oacute;N DE APORTES Y SUBSIDIOS</strong></p>
<p style="text-align: center;"><strong>CERTIFICA</strong></p>
<p>&nbsp;</p>
<p style="text-align: justify; line-height: normal;">Que el se&ntilde;or&nbsp;<strong>${nombreCompleto} ${tipoIdentificacion} ${numeroIdentificacion}&nbsp;</strong>a la fecha ${fechaGeneracion} presenta la siguiente historia de afiliaci&oacute;n a esta CAJA DE COMPENSACION FAMILIAR.</p>
<p>&nbsp;</p>
<p>${tabla}</p>
<p>&nbsp;</p>
<p><span>Esta certificaci&oacute;n no es v&aacute;lida para retirarse de<strong> ${nombreCcf}</strong>, ni afiliarse a otra Caja de Compensaci&oacute;n.</span></p>
<p>&nbsp;</p>
<p><span>Se expide a solicitud del interesado.</span></p>
<p>${ciudadCcf}, ${fechaGeneracion}</p>
<p>&nbsp;</p>
<p>Cordialmente,</p>
<p>&nbsp;</p>
<p style="text-align: justify; line-height: normal;"><strong>YARLEDY BLAND&Oacute;N BLAND&Oacute;N</strong></p>
<p style="text-align: justify; line-height: normal;"><strong>Subgerente Aportes y Subsidios</strong></p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>' ,pcoEncabezado = '<p style="text-align: justify; line-height: normal;">${logoDeLaCcf}</p>
<p style="text-align: justify; line-height: normal;">&nbsp;</p>
<p style="text-align: justify; line-height: normal;">S - AP</p>
<p style="text-align: justify; line-height: normal;">FU - SUB&nbsp;</p>' , pcoMensaje = '<p>${logoDeLaCcf}</p>
<p>&nbsp;</p>
<p>${ciudadCcf},&nbsp;${fechaGeneracion}&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>Se&ntilde;or aportante, adjunto le estamos haciendo llegar el certificado solicitado.</p>' ,pcoPie = '<p>&nbsp; &nbsp;&nbsp;</p>' FROM PlantillaComunicado WHERE pcoEtiqueta = 'COM_GEN_CER_HIS_AFI_PER';

UPDATE PlantillaComunicado
SET pcoNombre = 'CERTIFICADO PAZ Y SALVO - EMPLEADOR', pcoAsunto = 'CERTIFICADO PAZ Y SALVO EMPLEADOR'
WHERE pcoEtiqueta = 'COM_GEN_CER_PYS_EMP';

UPDATE PlantillaComunicado
SET pcoNombre = 'CERTIFICADO PAZ Y SALVO - PERSONA', pcoAsunto = 'CERTIFICADO PAZ Y SALVO PERSONA'
WHERE pcoEtiqueta = 'COM_GEN_CER_PYS';

--changeset mamonroy:06
--comment:
IF NOT EXISTS (SELECT * FROM PlantillaComunicado WHERE pcoEtiqueta = 'NTF_CRCN_USR_CCF_EXT')
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta)
VALUES ('CREACIÓN DE USUARIO EXITOSA','<p>Para autenticarse debe hacer uso de la siguiente&nbsp;informaci&oacute;n:<br /><br />Usuario:&nbsp; &nbsp; &nbsp; &nbsp;[usuario]<br />Contrase&ntilde;a:&nbsp; [Password]</p>','<p>&nbsp;</p><p>&nbsp;</p>
<p>Se&ntilde;or (a)&nbsp;<br />[nombreUsuario]&nbsp;<br /><br /><br />Su usuario para ingresar al&nbsp;portal de operativo ha sido creado exitosamente.&nbsp;</p>',NULL,'<p>Se&ntilde;or (a) <br /> [nombreUsuario] <br /><br /><br />Su usuario para ingresar al portal operativo ha sido creado exitosamente. <br /><br />Para autenticarse debe hacer uso de la siguiente informaci&oacute;n:<br /><br />Usuario:&nbsp; &nbsp; &nbsp; &nbsp;[usuario]<br />Contrase&ntilde;a:&nbsp; [Password]</p>
<p><br /><br />Cordialmente,<br /><br /><br /><br />Administrador Genesys</p>','Creación de usuario CCF exitosa','<p>Cordialmente,<br /><br /><br /><br />Administrador</p>','NTF_CRCN_USR_CCF_EXT');

