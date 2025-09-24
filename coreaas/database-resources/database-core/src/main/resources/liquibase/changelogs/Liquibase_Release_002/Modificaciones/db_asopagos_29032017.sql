--liquibase formatted sql

--changeset abaquero:01
--comment: Se agregan nuevos campos a la tabla PilaArchivoIPRegistro2 y PilaArchivoIRegistro2
ALTER TABLE PilaArchivoIPRegistro2 ADD ip2MarcaValRegistroAporte varchar(50);
ALTER TABLE PilaArchivoIPRegistro2 ADD ip2EstadoRegistroAporte varchar(60);
ALTER TABLE PilaArchivoIPRegistro2 ADD ip2FechaProcesamientoValidRegAporte datetime;
ALTER TABLE PilaArchivoIPRegistro2 ADD ip2EstadoValidacionV1 varchar(30);

ALTER TABLE PilaArchivoIRegistro2 ADD pi2MarcaValRegistroAporte varchar(50);
ALTER TABLE PilaArchivoIRegistro2 ADD pi2EstadoRegistroAporte varchar(60);
ALTER TABLE PilaArchivoIRegistro2 ADD pi2FechaProcesamientoValidRegAporte datetime;
ALTER TABLE PilaArchivoIRegistro2 ADD pi2EstadoValidacionV0 varchar(30);
ALTER TABLE PilaArchivoIRegistro2 ADD pi2EstadoValidacionV1 varchar(30);
ALTER TABLE PilaArchivoIRegistro2 ADD pi2EstadoValidacionV2 varchar(30);
ALTER TABLE PilaArchivoIRegistro2 ADD pi2EstadoValidacionV3 varchar(30);

--changeset abaquero:02
--comment: Cambio de tamaño en los campos de PilaArchivoIPRegistro1 
ALTER TABLE PilaArchivoIPRegistro1 ALTER COLUMN ip1TipoIdPagador varchar(20);
ALTER TABLE PilaArchivoIPRegistro2 ALTER COLUMN ip2TipoIdPensionado varchar(20);
ALTER TABLE PilaArchivoIRegistro1 ALTER COLUMN pi1TipoDocAportante varchar(20);
ALTER TABLE PilaArchivoIRegistro2 ALTER COLUMN pi2TipoIdCotizante varchar(20);


