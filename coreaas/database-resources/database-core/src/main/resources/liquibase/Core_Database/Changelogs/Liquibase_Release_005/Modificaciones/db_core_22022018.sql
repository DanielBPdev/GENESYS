--liquibase formatted sql

--changeset hhernandez:01
--comment:Creacion de la tabla TransaccionFallidaOperacionSubsidio
CREATE TABLE TransaccionFallidaOperacionSubsidio(
	tfoId BIGINT NOT NULL IDENTITY(1,1),
	tfoTransaccionesFallidasSubsidio BIGINT NOT NULL,
	tfoRegistroOperacionesSubsidio BIGINT NOT NULL,
CONSTRAINT PK_TransaccionFallidaOperacionSubsidio_tfoId PRIMARY KEY (tfoId)
);
ALTER TABLE TransaccionFallidaOperacionSubsidio ADD CONSTRAINT FK_TransaccionFallidaOperacionSubsidio_tfoTransaccionesFallidas FOREIGN KEY (tfoTransaccionesFallidasSubsidio) REFERENCES TransaccionesFallidasSubsidio(tfsId);
ALTER TABLE TransaccionFallidaOperacionSubsidio ADD CONSTRAINT FK_TransaccionFallidaOperacionSubsidio_tfoRegistroOperacionesSubsidio FOREIGN KEY (tfoRegistroOperacionesSubsidio) REFERENCES RegistroOperacionesSubsidio(rosId);  

--changeset hhernandez:02
--comment:Modificacion de campo de la tabla TransaccionesFallidasSubsidio
ALTER TABLE TransaccionesFallidasSubsidio ALTER COLUMN tfsCuentaAdministradorSubsidio BIGINT NULL;

--changeset squintero:03
--comment:Insercion de registros en la tabla PlantillaComunicado
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Aviso afiliación empresas presencial tiempo de proceso solicitud','Cuerpo','Encabezado','Mensaje','Aviso afiliación empresas presencial tiempo de proceso solicitud','Pie','COM_AVI_AEP_TIM_PS');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Aviso afiliación empresas presencial asignar solicitud empleador','Cuerpo','Encabezado','Mensaje','Aviso afiliación empresas presencial asignar solicitud empleador','Pie','COM_AVI_AEP_TIM_ASE');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Aviso afiliación empresas presencial tiempo de gestión de la solicitud back','Cuerpo','Encabezado','Mensaje','Aviso afiliación empresas presencial tiempo de gestión de la solicitud back','Pie','COM_AVI_AEP_TIM_GSB');

--changeset squintero:04
--comment:Insercion de registros en la tabla VariableComunicado - Variables
--COM_AVI_AEP_TIM_PS
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_PS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoDeTransaccion}','0','Tipo de transacción','Tipo de transacción','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_PS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo identificación','Tipo identificación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_PS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','Número Identificación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_PS'));
--COM_AVI_AEP_TIM_ASE
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_ASE'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoDeTransaccion}','0','Tipo de transacción','Tipo de transacción','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_ASE'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo identificación','Tipo identificación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_ASE'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','Número Identificación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_ASE'));
--COM_AVI_AEP_TIM_GSB
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_GSB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoDeTransaccion}','0','Tipo de transacción','Tipo de transacción','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_GSB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo identificación','Tipo identificación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_GSB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','Número Identificación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_GSB'));

--changeset squintero:05
--comment:Insercion de registros en la tabla VariableComunicado - Constantes
--COM_AVI_AEP_TIM_PS
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_PS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_PS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','responsable caja de compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_PS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo del responsable del envío del comunicado en la caja','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_PS'));
--COM_AVI_AEP_TIM_ASE
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_ASE'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_ASE'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','responsable caja de compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_ASE'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo del responsable del envío del comunicado en la caja','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_ASE'));
--COM_AVI_AEP_TIM_GSB
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_GSB'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_GSB'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','responsable caja de compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_GSB'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo del responsable del envío del comunicado en la caja','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEP_TIM_GSB'));

--changeset squintero:06
--comment:Actualizacion de registros en la tabla PlantillaComunicado - Variables
--COM_AVI_AEP_TIM_PS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${solicitudDeNovedad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${solicitudDeNovedad}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_PS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoDeTransaccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoDeTransaccion}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_PS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_PS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_PS';
--COM_AVI_AEP_TIM_ASE
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${solicitudDeNovedad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${solicitudDeNovedad}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_ASE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoDeTransaccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoDeTransaccion}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_ASE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_ASE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_ASE';
--COM_AVI_AEP_TIM_GSB
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${solicitudDeNovedad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${solicitudDeNovedad}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_GSB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoDeTransaccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoDeTransaccion}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_GSB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_GSB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_GSB';

--changeset squintero:07
--comment:Actualizacion de registros en la tabla PlantillaComunicado - Constantes
--COM_AVI_AEP_TIM_PS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_PS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_PS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_PS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_PS';
--COM_AVI_AEP_TIM_ASE
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_ASE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_ASE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_ASE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_ASE';
--COM_AVI_AEP_TIM_GSB
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_GSB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_GSB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_GSB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'COM_AVI_AEP_TIM_GSB';

--changeset squintero:08
--comment:Insercion de registros en las tablas DestinatarioComunicado, GrupoPrioridad, DestinatarioGrupo y PrioridadDestinatario
INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('AFILIACION_EMPRESAS_PRESENCIAL','COM_AVI_AEP_TIM_PS');
INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('AFILIACION_EMPRESAS_PRESENCIAL','COM_AVI_AEP_TIM_ASE');
INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('AFILIACION_EMPRESAS_PRESENCIAL','COM_AVI_AEP_TIM_GSB');
INSERT GrupoPrioridad (gprNombre) VALUES ('SUPERVISOR_AFILIACION_EMPLEADOR');
INSERT DestinatarioGrupo (dgrGrupoPrioridad,dgrRolContacto) VALUES ((SELECT gprId FROM GrupoPrioridad where gprNombre = 'SUPERVISOR_AFILIACION_EMPLEADOR'),'SUPERVISOR_AFILIACION_EMPLEADOR');
INSERT PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT dcoId FROM DestinatarioComunicado where dcoEtiquetaPlantilla = 'COM_AVI_AEP_TIM_PS'),(SELECT gprId FROM GrupoPrioridad WHERE gprNombre = 'SUPERVISOR_AFILIACION_EMPLEADOR'),'1');
INSERT PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT dcoId FROM DestinatarioComunicado where dcoEtiquetaPlantilla = 'COM_AVI_AEP_TIM_ASE'),(SELECT gprId FROM GrupoPrioridad WHERE gprNombre = 'SUPERVISOR_AFILIACION_EMPLEADOR'),'1');
INSERT PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT dcoId FROM DestinatarioComunicado where dcoEtiquetaPlantilla = 'COM_AVI_AEP_TIM_GSB'),(SELECT gprId FROM GrupoPrioridad WHERE gprNombre = 'SUPERVISOR_AFILIACION_EMPLEADOR'),'1');

--changeset squintero:09
--comment:Insercion de registros en las tabla Parametro
INSERT Parametro(prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('BPM_AEP_TIEMPO_PROCESO_SOLICITUD','2d',0,'EJECUCION_TIMER','BPM afiliacion empresas presencial - Tiempo limite que se tiene para procesar la solcicitud');
INSERT Parametro(prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('BPM_AEP_TIEMPO_ASIGNACION_BACK','1d',0,'EJECUCION_TIMER','BPM afiliacion empresas presencial - Tiempo limite que se tiene para que el back desde que recibe la solicitud hasta finalizarla');
INSERT Parametro(prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('BPM_AEP_TIEMPO_SOL_PENDIENTE_DOCUMENTOS','1d',0,'EJECUCION_TIMER','BPM afiliacion empresas presencial - Tiempo limite que tiene la solicitud a la espera de comumentos físicos');
