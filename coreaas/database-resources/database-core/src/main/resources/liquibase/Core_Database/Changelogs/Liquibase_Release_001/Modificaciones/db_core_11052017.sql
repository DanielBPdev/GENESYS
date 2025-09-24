--liquibase formatted sql

--changeset atoro:01
--comment: Actualizacion en la tabla RequisitoCajaClasificacion
UPDATE RequisitoCajaClasificacion SET rtsRequisito =(SELECT reqId FROM Requisito WHERE reqDescripcion='Copia documento identidad  representante legal / administrador / empleador') WHERE rtsTipoTransaccion='CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_WEB' AND rtsRequisito is null;
UPDATE RequisitoCajaClasificacion SET rtsRequisito =(SELECT reqId FROM Requisito WHERE reqDescripcion='Copia documento identidad  representante legal / administrador / empleador') WHERE rtsTipoTransaccion='CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_PRESENCIAL' AND rtsRequisito is null;
UPDATE RequisitoCajaClasificacion SET rtsRequisito =(SELECT reqId FROM Requisito WHERE reqDescripcion='Copia documento identidad  representante legal / administrador / empleador') WHERE rtsTipoTransaccion='CAMBIO_RAZON_SOCIAL_NOMBRE' AND rtsRequisito is null;
UPDATE RequisitoCajaClasificacion SET rtsRequisito =(SELECT reqId FROM Requisito WHERE reqDescripcion='Documento acredita existencia/representación legal o Personería Jurídica') WHERE rtsTipoTransaccion='ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_WEB' AND rtsRequisito is null;

--changeset atoro:02
--comment: Se agrega UK en la tabla CondicionInvalidez
ALTER TABLE CondicionInvalidez ADD CONSTRAINT UK_CondicionInvalidez_coiPersona UNIQUE (coiPersona); 