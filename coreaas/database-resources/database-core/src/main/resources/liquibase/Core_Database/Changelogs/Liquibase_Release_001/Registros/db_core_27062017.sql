--liquibase formatted sql

--changeset atoro:01
--comment: Insercion a la tabla ParametrizacionMetodoAsignacion
INSERT ParametrizacionMetodoAsignacion (pmaSedeCajaCompensacion,pmaProceso,pmaMetodoAsignacion,pmaUsuario,pmaGrupo) VALUES (1,'PAGO_APORTES_MANUAL','PREDEFINIDO','tese1tintegracion@heinsohn.com','analista_aportes')
