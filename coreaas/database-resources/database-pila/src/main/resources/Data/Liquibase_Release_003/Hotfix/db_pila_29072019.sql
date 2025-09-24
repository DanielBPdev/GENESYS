--liquibase formatted sql

--changeset abaquero:01
--comment: Adición de tabla para control de planillas a procesar en PILA M2
CREATE TABLE staging.ControlEjecucionPlanillas (
	cepId BIGINT NOT NULL,
	cepIdPaquete BIGINT NOT NULL,
	cepIdPlanilla BIGINT NOT NULL,
	cepEjecutado BIT NOT NULL
)

--changeset abaquero:02
--comment: Se crea la secuencia para el manejo de los valores de PK
DECLARE @inicioSeq BIGINT
SELECT @inicioSeq = max(cepId) + 1 FROM staging.ControlEjecucionPlanillas
SELECT @inicioSeq = ISNULL(@inicioSeq, 1)
EXEC('CREATE SEQUENCE Sec_ControlEjecucionPlanillas AS BIGINT START WITH ' + @inicioSeq + ' INCREMENT BY 1;')

--changeset abaquero:03
--comment: Se añaden campos de control de proceso en TemAporte y TemNovedad
ALTER TABLE TemAporte ADD temEnProceso BIT;
ALTER TABLE TemNovedad ADD tenEnProceso BIT;