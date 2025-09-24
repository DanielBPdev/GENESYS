--liquibase formatted sql

--changeset  sbriñez:01
--comment: Cambio Sergio Birñez
ALTER TABLE Requisito ADD  reqEstado varchar(20);
EXEC sp_rename 'dbo.RequisitoTipoSolicitante', 'RequisitoCajaClasificacion';
ALTER TABLE RequisitoCajaClasificacion ADD rtsCajaCompensacion int;
ALTER TABLE RequisitoCajaClasificacion ADD CONSTRAINT FK_RequisitoCajaClasificacion_rtsCajaCompensacion FOREIGN KEY (rtsCajaCompensacion) REFERENCES CajaCompensacion;
ALTER TABLE RequisitoCajaClasificacion ADD rtsTextoAyuda varchar(200);

drop table requisitocajacompensacion;
drop table TipoSolicitanteCajaCompensacion;
