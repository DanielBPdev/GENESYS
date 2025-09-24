--liquibase formatted sql

--changeset dsuesca:01
--comment: 
INSERT [dbo].[ConjuntoValidacionSubsidio] (cvsId,cvsTipoValidacion,cvsTipoProceso) 
VALUES (66,'TRABAJADOR_NO_APORTE_MINIMO','BENEFICIARIO_TRABAJADOR');

--changeset dsuesca:02
--comment: 
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso)
VALUES (67,'TRABAJADOR_CON_SUBSIDIO_FALLECIMIENTO','BENEFICIARIO_TRABAJADOR');

INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso)
VALUES (68,'BENEFICIARIO_CON_SUBSIDIO_FALLECIMIENTO','FALLECIMIENTO_BENEFICIARIO');