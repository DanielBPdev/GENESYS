--liquibase formatted sql

--changeset hhernandez:01
--comment: Se agrega campo y altera en la tabla AporteDetallado_aud
ALTER TABLE AporteDetallado_aud ADD apdUsuarioAprobadorAporte VARCHAR(50);
ALTER TABLE AporteDetallado_aud ALTER COLUMN apdUsuarioAprobadorAporte VARCHAR(50) NOT NULL;
ALTER TABLE AporteDetallado_aud ADD apdEstadoRegistroAporteArchivo VARCHAR(60);
ALTER TABLE aporteDetallado_aud ALTER COLUMN apdEstadoRegistroAporteArchivo VARCHAR(60) NOT NULL;

--changeset hhernandez:02
--comment: Se elimina campo de la tabla AporteDetallado
ALTER TABLE AporteDetallado_aud DROP COLUMN apdFechaProcesamiento;

--changeset jzambrano:03
--comment: Se alteran tamaño de campos de las tablas Ubicacion_aud y ProductoNoConforme_aud
ALTER TABLE Ubicacion_aud ALTER COLUMN ubiDireccionFisica varchar(300) NULL; 
ALTER TABLE ProductoNoConforme_aud ALTER COLUMN pncValorCampoBack varchar(300) NULL; 
ALTER TABLE ProductoNoConforme_aud ALTER COLUMN pncValorCampoFront varchar(300) NULL; 

--changeset mosanchez:04
--comment: Se adiciona campo en la tabla SucursalEmpresa
ALTER TABLE SucursalEmpresa_aud ADD sueSucursalPrincipal bit NULL;
ALTER TABLE SucursalEmpresa_aud ALTER COLUMN sueCodigo varchar(10) NULL;

--changeset ogiral:05
--comment: Se adiciona campo en la tabla SucursalEmpresa_aud
ALTER TABLE Empleador_aud ADD empValidarSucursalPila bit NULL;

--changeset criparra:06
--comment: Se modifican tamaño de campos de las tablas SolicitudAporte_aud y Trazabilidad_aud
ALTER TABLE SolicitudAporte_aud ALTER COLUMN soaEstadoSolicitud varchar(30);
ALTER TABLE Trazabilidad_aud ALTER COLUMN traEstadoSolicitud varchar(30);
ALTER TABLE Trazabilidad_aud ALTER COLUMN traEstadoInicialSolicitud varchar(30);

--changeset flopez:07
--comment: Se crea la tabla SolicitudNovedadPila_aud y adiciona campo en la tabla RegistroNovedadFutura
CREATE TABLE SolicitudNovedadPila_aud(
	spiId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	spiSolicitudNovedad BIGINT NOT NULL,
	spiRegistroDetallado BIGINT NOT NULL,
);
ALTER TABLE SolicitudNovedadPila_aud WITH CHECK ADD CONSTRAINT FK_SolicitudNovedadPila_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
ALTER TABLE RegistroNovedadFutura_aud ADD rnfRegistroDetallado BIGINT NULL;