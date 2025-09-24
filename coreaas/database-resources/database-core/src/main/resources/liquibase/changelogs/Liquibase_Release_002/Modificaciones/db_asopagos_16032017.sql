--liquibase formatted sql

--changeset ogiral:01
--comment: actualizacion en tabla requisito y en RequisitoCajaClasificacion 
update requisito set reqTipoRequisito = 'SECUNDARIO_AFILIACION' where reqTipoRequisito = 'FORMULARIO_AFILIACION';
update requisito set reqTipoRequisito = 'ESTANDAR' where reqTipoRequisito = 'NINGUNA';
DELETE FROM RequisitoCajaClasificacion where rtstipotransaccion ='AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION';
update RequisitoCajaClasificacion set rtstipotransaccion ='AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO' where rtstipotransaccion ='AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO';

--changeset ogiral:02
--comment: actualizacion en tabla intentoafiliacion
update intentoafiliacion set iafTipoTransaccion = 'AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION' where iafTipoTransaccion ='AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION';

--changeset atoro:03
--comment: Se modifica la tabla NOVEDAD y se actualiza el campo novTipoNovedad y novProceso
ALTER TABLE NOVEDAD ADD novProceso varchar(50) NULL; 
--SE MODIFICA NOVEDAD PARA QUE SEA DE TIPO AUTOMATICA
update novedad set novTipoNovedad='AUTOMATICA' where novTipoTransaccion in ('INACTIVAR_AUTOMATICAMENTE_BENEFICIO_LEY_1429_2010','INACTIVAR_AUTOMATICAMENTE_BENEFICIOS_LEY_590_2000','INACTIVAR_AUTOMATICAMENTE_CUENTA_WEB_EMPLEADOR');
--SE MODIFICA EL PROCESO PARA NOVEDADES WEB
update novedad set novProceso ='NOVEDADES_EMPRESAS_WEB' where novTipoTransaccion in ('CAMBIO_ACTIVIDAD_ECONOMICA_PRINCIPAL_WEB','ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_WEB','ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_WEB','ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_WEB','CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_WEB','CAMBIO_MEDIO_PAGO_EMPLEADOR_WEB','CAMBIO_DATOS_SUCURSAL_WEB','CAMBIO_ACTIVIDAD_ECONOMICA_SUCURSAL_WEB','CAMBIO_MEDIO_PAGO_SUCURSAL_WEB','CAMBIOS_ROLES_CONTACTO_WEB','ACTIVAR_BENEFICIOS_LEY_1429_2010_WEB','INACTIVAR_BENEFICIOS_LEY_1429_2010_WEB','ACTIVAR_BENEFICIOS_LEY_590_2000_WEB','INACTIVAR_BENEFICIOS_LEY_590_2000_WEB');
--SE MODIFICA NOVEDAD PARA PROCESO EMPRESAS PRESENCIAL
update novedad set novProceso ='NOVEDADES_EMPRESAS_PRESENCIAL' where novTipoTransaccion in ('CAMBIO_TIPO_NUMERO_DOCUMENTO','CAMBIO_RAZON_SOCIAL_NOMBRE','CAMBIO_NATURALEZA_JURIDICA','CAMBIO_ACTIVIDAD_ECONOMICA_PRINCIPAL_PRESENCIAL','CAMBIOS_OTROS_DATOS_IDENTIFICACION_EMPLEADOR','ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_PRESENCIAL','ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_PRESENCIAL','ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_PRESENCIAL','CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_PRESENCIAL','CAMBIO_MEDIO_PAGO_EMPLEADOR_PRESENCIAL','CAMBIO_CODIGO_NOMBRE_SUCURSAL','CAMBIO_DATOS_SUCURSAL_PRESENCIAL','CAMBIO_ACTIVIDAD_ECONOMICA_SUCURSAL_PRESENCIAL','CAMBIO_MEDIO_PAGO_SUCURSAL_PRESENCIAL','ACTIVAR_INACTIVAR_CODIGO_SUCURSAL_DEBE_COINCIDIR_CON_PILA','AGREGAR_SUCURSAL','INACTIVAR_SUCURSAL','CAMBIOS_ROLES_CONTACTO_PRESENCIAL','CAMBIO_RESPONSABLE_CONTACTOS_CFF','ACTIVAR_BENEFICIOS_LEY_1429_2010_PRESENCIAL','INACTIVAR_BENEFICIOS_LEY_1429_2010_PRESENCIAL','ACTIVAR_BENEFICIOS_LEY_590_2000_PRESENCIAL','INACTIVAR_BENEFICIOS_LEY_590_2000_PRESENCIAL','TRASLADO_TRABAJADORES_ENTRE_SUCURSALES','SUSTITUCION_PATRONAL','DESAFILIACION','ANULACION_AFILIACION');

--changeset halzate:04
--comment: Se agrega columna vcoTipoVariableComunicado en la tabla VariableComunicado
ALTER TABLE VariableComunicado ADD vcoTipoVariableComunicado varchar(10) NULL;

--changeset cargarcia:05
--comment: Se agrega columna ccfCodigoRedeban en la tabla CajaCompensacion
ALTER TABLE CajaCompensacion ADD ccfCodigoRedeban integer NULL;
UPDATE CajaCompensacion SET ccfCodigoRedeban = 19 WHERE ccfCodigo ='CCF02';
UPDATE CajaCompensacion SET ccfCodigoRedeban = 7 WHERE ccfCodigo ='CCF03';
UPDATE CajaCompensacion SET ccfCodigoRedeban = 14 WHERE ccfCodigo ='CCF06';
UPDATE CajaCompensacion SET ccfCodigoRedeban = 9 WHERE ccfCodigo ='CCF11';
UPDATE CajaCompensacion SET ccfCodigoRedeban = 10 WHERE ccfCodigo ='CCF13';
UPDATE CajaCompensacion SET ccfCodigoRedeban = 11 WHERE ccfCodigo ='CCF014';
UPDATE CajaCompensacion SET ccfCodigoRedeban = 16 WHERE ccfCodigo ='CCF15';
UPDATE CajaCompensacion SET ccfCodigoRedeban = 13 WHERE ccfCodigo ='CCF16';
UPDATE CajaCompensacion SET ccfCodigoRedeban = 12 WHERE ccfCodigo ='CCF30';
UPDATE CajaCompensacion SET ccfCodigoRedeban = 6 WHERE ccfCodigo ='CCF32'; 
UPDATE CajaCompensacion SET ccfCodigoRedeban = 15 WHERE ccfCodigo ='CCF35';
UPDATE CajaCompensacion SET ccfCodigoRedeban = 20 WHERE ccfCodigo ='CCF39';
UPDATE CajaCompensacion SET ccfCodigoRedeban = 17 WHERE ccfCodigo ='CCF48'; 

--changeset clmarin:06
--comment: Se borran todas las VariableComunicado
DELETE from VariableComunicado where vcoId>=11216

--changeset flopez:07 
--comment: Actualizacion en la tabla Novedad
UPDATE Novedad SET novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarInactivarCuentaWeb' WHERE novTipoTransaccion ='INACTIVAR_AUTOMATICAMENTE_CUENTA_WEB_EMPLEADOR'

--changeset atoro:08
--comment: Se borra un registro en la tabla novedad 
delete novedad where novTipoTransaccion='ANULACION_AFILIACION';

--changeset jocampo:09
--comment: Se agrega nuevo campo a la tabla ItemChequeo
ALTER TABLE ItemChequeo ADD ichIdentificadorDocumentoPrevio varchar (255) NULL
