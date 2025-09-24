--liquibase formatted sql

--changeset mosanchez:01
--comment:Se agregan campos a la tabla CodigoCIIU_aud
ALTER TABLE dbo.CodigoCIIU_aud ADD ciiCodigoSeccion varchar(1) NULL;
ALTER TABLE dbo.CodigoCIIU_aud ADD ciiDescripcionSeccion varchar(200) NULL;
ALTER TABLE dbo.CodigoCIIU_aud ADD ciiCodigoDivision varchar(2) NULL;
ALTER TABLE dbo.CodigoCIIU_aud ADD ciiDescripcionDivision varchar(250) NULL;
ALTER TABLE dbo.CodigoCIIU_aud ADD ciiCodigoGrupo varchar(3) NULL;
ALTER TABLE dbo.CodigoCIIU_aud ADD ciiDescripcionGrupo varchar(200) NULL;

--changeset atoro:02
--comment:Modificacion a la tabla SolicitudAporte
ALTER TABLE dbo.SolicitudAporte_aud ALTER COLUMN soaAporteGeneral bigint NULL;

--changeset ogiral:03
--comment:Modificacion a la tabla ParametrizacionNovedad
ALTER TABLE dbo.ParametrizacionNovedad_aud ADD novAplicaTodosRoles bit NULL;

