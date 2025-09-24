--liquibase formatted sql

--changeset  sbriñez:01
--comment: Cambio Sergio Briñez cambio tipo solicitante
update Requisito set reqEstado='HABILITADO' ;
update RequisitoCajaClasificacion set rtsCajaCompensacion = (select ccfId from cajacompensacion where ccfNombre like '%CONFAMILIARES%');
ALTER TABLE RequisitoCajaClasificacion drop UK_RequisitoTipoSolicitante;
ALTER TABLE RequisitoCajaClasificacion ADD CONSTRAINT UK_RequisitoCajaClasificacion_rtsRequisito_rtsClasificacion_rtsTipoTransaccion_rtsCajaCompensacion UNIQUE (rtsRequisito, rtsClasificacion, rtsTipoTransaccion,rtsCajaCompensacion);

--changeset  sbriñez:02
--comment: Adición UK
ALTER TABLE Parametro ADD CONSTRAINT UK_Parametro_prmNombre UNIQUE(prmNombre);

--changeset  sbriñez:03
--comment: Eliminación campo solTipoSolicitante
ALTER TABLE Solicitud DROP COLUMN solTipoSolicitante;
