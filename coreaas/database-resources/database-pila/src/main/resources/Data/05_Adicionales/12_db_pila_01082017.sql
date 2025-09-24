--liquibase formatted sql

--changeset arocha:01
--comment: Se agregan campos a la tabla staging.RegistroGeneral y se actualizan campos
ALTER TABLE staging.RegistroGeneral ADD regEsSimulado BIT;
ALTER TABLE staging.RegistroGeneral ADD regEstadoEvaluacion VARCHAR (22); 
UPDATE staging.RegistroGeneral SET regEsSimulado = 0;
UPDATE staging.RegistroGeneral SET regEstadoEvaluacion = 'VIGENTE';
ALTER TABLE staging.RegistroGeneral ALTER COLUMN regEsSimulado BIT NOT NULL;
ALTER TABLE staging.RegistroGeneral ALTER COLUMN regEstadoEvaluacion VARCHAR (22) NOT NULL; 

--changeset arocha:02
--comment: Se agregan campos a la tabla staging.RegistroDetallado y se actualizan campos
ALTER TABLE staging.RegistroDetallado ADD redEstadoEvaluacion VARCHAR (22); 
UPDATE staging.RegistroDetallado SET redEstadoEvaluacion = 'VIGENTE';
ALTER TABLE staging.RegistroDetallado ALTER COLUMN redEstadoEvaluacion VARCHAR (22) NOT NULL; 
