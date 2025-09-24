--liquibase formatted sql

--changeset abaquero:01
--comment: Update tabla ValidatorParamValue
UPDATE dbo.ValidatorParamValue SET value='cantidadCotizantes' WHERE id=2111599;
UPDATE dbo.ValidatorParamValue SET value='cantidadCotizantes' WHERE id=2111600;

--changeset jocorrea:02
--comment: Arreglo de valor de columna pmaSedeCajaCompensacion para  pmaProceso = POSTULACION_FOVIS_WEB 
DELETE FROM ParametrizacionMetodoAsignacion WHERE pmaSedeCajaCompensacion = 1 AND pmaProceso = 'POSTULACION_FOVIS_WEB';

IF NOT EXISTS (SELECT 1 FROM ParametrizacionMetodoAsignacion WHERE pmaSedeCajaCompensacion = 2 AND pmaProceso = 'POSTULACION_FOVIS_WEB')
INSERT ParametrizacionMetodoAsignacion (pmaSedeCajaCompensacion, pmaProceso, pmaMetodoAsignacion, pmaUsuario, pmaGrupo, pmaSedeCajaDestino) VALUES ('2', 'POSTULACION_FOVIS_WEB', 'NUMERO_SOLICITUDES', NULL, 'BacPosFov', NULL);
