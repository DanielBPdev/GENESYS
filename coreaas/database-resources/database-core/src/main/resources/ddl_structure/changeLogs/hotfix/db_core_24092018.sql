--liquibase formatted sql

--changeset jvelandia:01
--comment: Actualizacion tabla datoTemporalComunicado mantis
UPDATE datoTemporalComunicado
SET dtcIdTarea = NULL
WHERE dtcIdTarea IN(
						SELECT dtcIdTarea
						FROM datoTemporalComunicado					
						GROUP BY dtcIdTarea
						having COUNT(*) > 1
						)
AND dtcId NOT IN(
				SELECT MAX(dtcId) 
				  FROM datoTemporalComunicado
				WHERE dtcIdTarea IN (
									SELECT dtcIdTarea
										FROM datoTemporalComunicado
									GROUP BY dtcIdTarea
									having COUNT(*) > 1)
				GROUP BY dtcIdTarea
);


--changeset jvelandia:02
--comment: Se crea llave unica tabla DatoTemporalComunicad
CREATE UNIQUE NONCLUSTERED INDEX UK_DatoTemporalComunicado_dtcIdTarea ON DatoTemporalComunicado(dtcIdTarea) WHERE dtcIdTarea IS NOT NULL;

--changeset mosorio:03
--comment: Se crea tabla GeneracionReporteNormativo
CREATE TABLE GeneracionReporteNormativo(
	grnId BIGINT NOT NULL IDENTITY(1,1),
	gnrNombreReporte VARCHAR(200) NOT NULL,
	gnrUsuarioGeneracion VARCHAR (200) NOT NULL,
	gnrFechaGeneracion DATETIME NOT NULL, 
	gnrReporteNormativo VARCHAR (60) NOT NULL,
	gnrIdentificadorArchivo VARCHAR(255) NOT NULL,
	gnrReporteOficial BIT NOT NULL,
	gnrFechaInicio DATE NOT NULL,
	gnrFechaFin DATE NULL,
CONSTRAINT PK_GeneracionReporteNormativo_grnId PRIMARY KEY (grnId)
);