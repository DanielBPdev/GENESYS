--liquibase formatted sql

--changeset atoro:01
--comment:Se adicionan campos en la tabla DevolucionAporte y RegistroNovedadFutura
ALTER TABLE DevolucionAporte ADD otraCaja VARCHAR(255) NULL;
ALTER TABLE DevolucionAporte ADD otroMotivo VARCHAR(255) NULL;
ALTER TABLE RegistroNovedadFutura ADD rnfClasificacion VARCHAR(48) NULL;
ALTER TABLE RegistroNovedadFutura ADD rnfEmpleador BIGINT NULL;
ALTER TABLE RegistroNovedadFutura ADD CONSTRAINT FK_RegistroNovedadFutura_rnfEmpleador FOREIGN KEY (rnfEmpleador) REFERENCES Empleador (empId);

--changeset atoro:02
--comment:Se adicionan campos en la tabla DevolucionAporte y RegistroNovedadFutura
EXEC sp_rename 'DevolucionAporte.otraCaja', 'dapOtraCaja', 'COLUMN';
EXEC sp_rename 'DevolucionAporte.otroMotivo', 'dapOtroMotivo', 'COLUMN';