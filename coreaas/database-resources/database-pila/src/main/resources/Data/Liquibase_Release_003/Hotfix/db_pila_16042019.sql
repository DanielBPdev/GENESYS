--liquibase formatted sql

--changeset abaquero:01
--comment: Actualización masiva de marcas de registro detallado actual en PILA
DECLARE @registrosEnAportes AS TABLE (redId BIGINT, shard VARCHAR(500))

INSERT INTO @registrosEnAportes (redId, shard)
EXEC sp_execute_remote CoreReferenceData
, N'SELECT apdRegistroDetallado FROM AporteDetallado GROUP BY apdRegistroDetallado'

UPDATE red
	SET red.redOUTRegistroActual = 1
FROM staging.RegistroDetallado red 
INNER JOIN @registrosEnAportes apd ON red.redId = apd.redId