--liquibase formatted sql

--changeset atoro:01
--comment:Se adicionan campos en la tabla DevolucionAporte_aud y RegistroNovedadFutura_aud
ALTER TABLE DevolucionAporte_aud ADD otraCaja VARCHAR(255) NULL;
ALTER TABLE DevolucionAporte_aud ADD otroMotivo VARCHAR(255) NULL;
ALTER TABLE RegistroNovedadFutura_aud ADD rnfClasificacion VARCHAR(48) NULL;
ALTER TABLE RegistroNovedadFutura_aud ADD rnfEmpleador BIGINT NULL;

--changeset atoro:02
--comment:Se adicionan campos en la tabla DevolucionAporte_aud y RegistroNovedadFutura_aud
EXEC sp_rename 'DevolucionAporte_aud.otraCaja', 'dapOtraCaja', 'COLUMN';
EXEC sp_rename 'DevolucionAporte_aud.otroMotivo', 'dapOtroMotivo', 'COLUMN';
