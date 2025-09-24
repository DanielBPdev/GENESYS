--liquibase formatted sql

--changeset jcamargo:01
/* [HU110][Integración][Sprint2][RolContactoEmpleador]*/
ALTER TABLE RolContactoEmpleador ALTER COLUMN rceToken VARCHAR(50);

--changeset mgiraldo:02
ALTER TABLE DocumentoEntidadPagadora ADD CONSTRAINT FK_DocumentoEntidadPagadora_dpgEntidadPagadora FOREIGN KEY (dpgEntidadPagadora) REFERENCES EntidadPagadora;

--changeset halzate:03
/*HU133*/
ALTER TABLE SolicitudAsociacionPersonaEntidadPagadora ALTER COLUMN  soaConsecutivo VARCHAR(11);

--changeset sbrinez:04
/*HU-121-133*/
ALTER TABLE EntidadPagadora ADD epaTipoAfiliacion VARCHAR(50);
ALTER TABLE UbicacionEmpresa ADD CONSTRAINT UK_UbicacionEmpresa_ubeEmpresa_ubeTipoUbicacion UNIQUE (ubeEmpresa,ubeTipoUbicacion);

--changeset sbrinez:05
ALTER TABLE Empleador ALTER COLUMN empPeriodoUltimaNomina DATE;
