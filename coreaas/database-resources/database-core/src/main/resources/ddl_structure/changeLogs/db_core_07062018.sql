--liquibase formatted sql

--changeset alquintero:01
--comment: Se crea tabla ReporteKPI
CREATE TABLE ReporteKPI(
	rekId bigint IDENTITY(1,1) NOT NULL,
	rekIdReporte uniqueidentifier NOT NULL,
	rekIdGrupo uniqueidentifier NOT NULL,
	rekNombreReporte varchar(25) NOT NULL,
	rekFrecuencia varchar(10) NOT NULL,
 CONSTRAINT PK_ReporteKPI_rekId PRIMARY KEY (rekId ASC)
 );

--changeset alquintero:02
--comment: Se inserta parametro
INSERT INTO Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('URL_TOKEN_REPORTES_KPI', 'http://powerbigettokenservice-hbt-asopagos-pruebas.azurewebsites.net/api/Token/', 0, 'REPORTES', 'Corresponde a la dirección donde se harán las consultas de los reportes');

--changeset alquintero:03
--comment: Inserciones tabla ReporteKPI
INSERT ReporteKPI (rekIdReporte, rekIdGrupo, rekNombreReporte, rekFrecuencia) VALUES ('f921d0ce-25b7-4eb5-b212-9b9fa4966e1d', 'f56a16eb-43ce-433f-8290-f5ee5b75393a', 'AFILIACIONES_EMPLEADOR', 'MENSUAL');
INSERT ReporteKPI (rekIdReporte, rekIdGrupo, rekNombreReporte, rekFrecuencia) VALUES ('fcad0d08-28bd-4901-8fa5-abc34fc42e27', 'f56a16eb-43ce-433f-8290-f5ee5b75393a', 'AFILIACIONES_EMPLEADOR', 'BIMENSUAL');
INSERT ReporteKPI (rekIdReporte, rekIdGrupo, rekNombreReporte, rekFrecuencia) VALUES ('ff21c40a-1d91-412b-a77c-b5928f1b29ab', 'f56a16eb-43ce-433f-8290-f5ee5b75393a', 'AFILIACIONES_EMPLEADOR', 'TRIMESTRAL');
INSERT ReporteKPI (rekIdReporte, rekIdGrupo, rekNombreReporte, rekFrecuencia) VALUES ('bd659b5d-7c29-4a9b-8203-6eeee33ff7db', 'f56a16eb-43ce-433f-8290-f5ee5b75393a', 'AFILIACIONES_EMPLEADOR', 'SEMESTRAL');
INSERT ReporteKPI (rekIdReporte, rekIdGrupo, rekNombreReporte, rekFrecuencia) VALUES ('4a5ede63-13c3-4ce6-b024-48c237036326', 'f56a16eb-43ce-433f-8290-f5ee5b75393a', 'AFILIACIONES_EMPLEADOR', 'ANUAL');
