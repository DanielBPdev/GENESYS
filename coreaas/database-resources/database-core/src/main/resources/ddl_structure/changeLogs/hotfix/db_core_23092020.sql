--liquibase formatted sql

--changeset cmarin:01
--comment: 
CREATE TABLE TiempoProcesoCartera (
tpcId BIGINT IDENTITY(1,1) NOT NULL,
tpcNombreProceso VARCHAR(250) COLLATE Latin1_General_CI_AI NOT NULL,
tpcFechaInicio DATETIME,
tpcFechaFin DATETIME,
tpcRegistros BIGINT,
tpcMensaje VARCHAR(250) COLLATE Latin1_General_CI_AI NULL,
CONSTRAINT PK_TiempoProcesoCartera_tpcId PRIMARY KEY (tpcId)
)