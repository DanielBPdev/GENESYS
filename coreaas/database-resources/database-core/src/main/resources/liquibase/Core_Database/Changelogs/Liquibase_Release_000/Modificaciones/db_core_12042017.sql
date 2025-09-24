--liquibase formatted sql

--changeset atoro:01
--comment: Creacion de la tabla CondicionInvalidez
CREATE TABLE CondicionInvalidez(
	coiId bigint IDENTITY(1,1) NOT NULL,
	coiPersona bigint NOT NULL,
	coiInvalidez bit NULL,
	coiFechaReporteInvalidez date NULL,
	coiComentarioInvalidez varchar(500) NULL
CONSTRAINT PK_CondicionInvalidez_coiId PRIMARY KEY (coiId)
);

ALTER TABLE CondicionInvalidez WITH CHECK ADD CONSTRAINT FK_CondicionInvalidez_coiPersona FOREIGN KEY (coiPersona) REFERENCES Persona (perId);

--changeset atoro:02
--comment: Adición de columnas a la tabla RolAfiliado
ALTER TABLE RolAfiliado ADD roaFechaFinPagadorAportes date NULL;
ALTER TABLE RolAfiliado ADD roaFechaFinPagadorPension date NULL;

--changeset atoro:03
--comment: Adicion de columnas a la tabla Afiliado
ALTER TABLE Afiliado ADD afiPignoracionSubsidio bit NULL;
ALTER TABLE Afiliado ADD afiCesionSubsidio bit NULL;
ALTER TABLE Afiliado ADD afiRetencionSubsidio bit NULL;
ALTER TABLE Afiliado ADD afiServicioSinAfiliacion bit NULL;
ALTER TABLE Afiliado ADD afiCausaSinAfiliacion varchar(20) NULL;

--changeset atoro:04
--comment: Adición de columna snoObservaciones para la tabla SolicitudNovedad
ALTER TABLE SolicitudNovedad ADD snoObservaciones varchar(200) NULL;

--changeset atoro:05
--comment: Creacion de la tabla NovedadPila
CREATE TABLE NovedadPila(
	nopId bigint IDENTITY(1,1) NOT NULL,
	nopRolAfiliado bigint NOT NULL,
	nopTipoNovedadPila varchar(3) NOT NULL,
	nopFechaInicio date NULL,
	nopFechaFin date NULL,
	nopVigente bit NULL
CONSTRAINT PK_CondicionInvalidez_nopId PRIMARY KEY (nopId)
);

ALTER TABLE NovedadPila WITH CHECK ADD CONSTRAINT FK_NovedadPila_nopRolAfiliado FOREIGN KEY (nopRolAfiliado) REFERENCES RolAfiliado (roaId);

--changeset atoro:06
--comment: Cambio de tamaño de la columna nopTipoNovedadPila en NovedadPila
ALTER TABLE NovedadPila ALTER COLUMN nopTipoNovedadPila varchar (15) NOT NULL

--changeset atoro:07
--comment: Se agrega UK en SolicitudNovedad
ALTER TABLE SolicitudNovedad ADD CONSTRAINT UK_SolicitudNovedad_snoNovedad_snoSolicitudGlobal UNIQUE (snoNovedad,snoSolicitudGlobal);

