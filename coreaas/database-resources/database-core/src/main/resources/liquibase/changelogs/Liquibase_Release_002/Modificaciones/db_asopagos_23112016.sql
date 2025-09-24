--liquibase formatted sql

--changeset  jocampo:03
--comment:  Creación tabla CargueAfiliacionMultiple
CREATE TABLE   CargueAfiliacionMultiple(
	camId bigint IDENTITY(1,1) NOT NULL,
	camCodigoCargueMultiple bigint NOT NULL,
	camIdSucursalEmpleador bigint NOT NULL, 
    camIdEmpleador  bigint NOT NULL,
    camTipoSolicitante varchar(30) NOT NULL, 
	camClasificacion varchar(100) NOT NULL,
	camTipoTransaccion varchar(100) NOT NULL,
	camProceso varchar(100) NOT NULL,
	camEstado varchar(20) NOT NULL,
	camFechaCarga date NOT NULL,
	camCodigoIdentificacionECM varchar(255) NOT NULL
	CONSTRAINT PK_CargueAfiliacionMultiple_camId PRIMARY KEY (camId)
);

ALTER TABLE CargueAfiliacionMultiple ADD CONSTRAINT
FK_CargueAfiliacionMultiple_camIdSucursalEmpleador FOREIGN KEY (camIdSucursalEmpleador) REFERENCES SucursalEmpresa;

ALTER TABLE CargueAfiliacionMultiple ADD CONSTRAINT
FK_SucursalEmpresa_camIdEmpleador FOREIGN KEY (camIdEmpleador) REFERENCES Empleador;

ALTER TABLE Solicitud ADD solCargaAfiliacionMultipleEmpleador BIGINT;

ALTER TABLE Solicitud ADD CONSTRAINT
FK_Solicitud_solCargaAfiliacionMultipleEmpleador FOREIGN KEY (solCargaAfiliacionMultipleEmpleador) REFERENCES CargueAfiliacionMultiple;


--changeset  lzarate:04
--comment:  adición campo pmaGrupo
ALTER TABLE ParametrizacionMetodoAsignacion ADD pmaGrupo VARCHAR(50);

--changeset  lzarate:05
--comment:  Actualizacion enumeración estado civil
UPDATE PERSONA SET perEstadoCivil='CASADO_UNION_LIBRE' WHERE perEstadoCivil in ('CASADO','UNION_LIBRE');