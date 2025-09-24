--liquibase formatted sql

--changeset squintero:01
--comment:Insercion de registros en la tabla Constante
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('SOLICITUD_ANULACION_SUBSIDIO_COBRADO','om.asopagos.coreaas.bpm.solicitud_anulacion_subsidio_cobrado:solicitud_anulacion_subsidio_cobrado:0.0.2-SNAPSHOT' ,'Identificador de versión de proceso BPM para  solicitud de anulación de subsidio cobrado');

--changeset flopez:02
--comment: Se elimina campo de la tabla ProyectoSolucionVivienda
ALTER TABLE ProyectoSolucionVivienda DROP COLUMN psvValorProyectoVivienda;

--changeset flopez:03
--comment: Se elimina constraint unique y se crea indice unique
ALTER TABLE ProyectoSolucionVivienda DROP CONSTRAINT UK_ProyectoSolucionVivienda_psvMatriculaInmobiliariaInmueble;
CREATE UNIQUE INDEX UK_ProyectoSolucionVivienda_psvMatriculaInmobiliariaInmueble ON ProyectoSolucionVivienda (psvMatriculaInmobiliariaInmueble) WHERE psvMatriculaInmobiliariaInmueble IS NOT NULL;

--changeset jvelandia:04
--comment: Insercion de registros en las tablas PlantillaComuicado y VariableComunicado
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Encabezado_158 Resumen datos de la solicitud ','Cuerpo','Encabezado','Mensaje','Encabezado_158 Resumen datos de la solicitud ','Pie','HU_PROCESO_TRA_140_FOVIS_LEGAL');
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroDeSolicitud}','1','Número de solicitud','Campo corresponde al número asignado automáticamente ','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_TRA_140_FOVIS_LEGAL'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaYHoraDeSolicitud}','2','Fecha y hora de solicitud','Corresponde al campo "Fecha y hora de radicación"','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_TRA_140_FOVIS_LEGAL'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${usuarioQueRadico}','3','Usuario que radicó','Campo corresponde al Solicitante que asigna automáticamente','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_TRA_140_FOVIS_LEGAL'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaYHoraDeAsignacion}','4','Fecha y hora de asignación','Fecha y hora de asignación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_TRA_140_FOVIS_LEGAL'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${estadoDeLaSolicitud}','5','Estado de la Solicitud','Estado de la Solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_TRA_140_FOVIS_LEGAL'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${canalDeRecepcion}','6','Canal de recepción','Fecha y hora de asignación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_TRA_140_FOVIS_LEGAL'));

--changeset jzambrano:05
--comment:Actualizacion de registros de la tabla RolAfiliado y empleador
UPDATE RolAfiliado SET roaEstadoAfiliado = NULL WHERE roaEstadoAfiliado IN('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_CON_INFORMACION');
UPDATE Empleador SET empEstadoEmpleador = NULL WHERE empEstadoEmpleador IN('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_CON_INFORMACION');
UPDATE RolAfiliado SET roaEstadoAfiliado = 'INACTIVO' WHERE roaEstadoAfiliado = 'NO_FORMALIZADO_RETIRADO_CON_APORTES';
UPDATE Empleador SET empEstadoEmpleador = 'INACTIVO' WHERE empEstadoEmpleador = 'NO_FORMALIZADO_RETIRADO_CON_APORTES';

--changeset jzambrano:06
--comment: Se modifican los tamaños de campos en las tablas Empleador y RolAfiliado
ALTER TABLE RolAfiliado ALTER COLUMN roaEstadoAfiliado VARCHAR(8);
ALTER TABLE Empleador ALTER COLUMN empEstadoEmpleador VARCHAR(8);
