--liquibase formatted sql

--changeset mamonroy:01
--comment: Utilitario Errores BPM - Tareas CREATED sin propietario
CREATE TABLE ResultadoEjecucionUtilitarioBPM (
	reuId bigint IDENTITY(1,1),
	reuNumeroRadicado varchar(15), 
	reuFechaProcesamiento datetime2, 
	reuResultadaoProceso varchar(20), 
	reuUsuario varchar(50));