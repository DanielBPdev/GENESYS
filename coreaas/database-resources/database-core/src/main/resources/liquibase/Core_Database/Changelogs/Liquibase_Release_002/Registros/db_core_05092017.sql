--liquibase formatted sql

--changeset atoro:01
--comment: Insercion de registro en la tabla ParametrizacionMetodoAsignacion
INSERT ParametrizacionMetodoAsignacion (pmaSedeCajaCompensacion,pmaProceso,pmaMetodoAsignacion,pmaGrupo) VALUES (1,'DEVOLUCION_APORTES','NUMERO_SOLICITUDES','AnaApo');