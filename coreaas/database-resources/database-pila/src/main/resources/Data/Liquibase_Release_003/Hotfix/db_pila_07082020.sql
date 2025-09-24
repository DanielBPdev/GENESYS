--liquibase formatted sql

--changeset abaquero:01
--comment: se actualizan los valores de proceso en el control de ejecución de planillas para las planillas que ya tienen registro general
UPDATE cep
SET cepEjecutado = 1 
FROM staging.ControlEjecucionPlanillas cep
INNER JOIN staging.RegistroGeneral ON regRegistroControl = cepIdPlanilla AND regRegistroControlManual IS NULL