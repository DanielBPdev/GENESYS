--liquibase formatted sql

--changeSET flopez:01
--comment: Mantis 266388

IF NOT EXISTS(SELECT 1 FROM ConjuntoValidacionSubsidio WHERE cvsId = 71)
INSERT INTO ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (71,'TRABAJADOR_NO_APORTE_MINIMO','BENEFICIARIO_TRABAJADOR')

IF EXISTS(SELECT 1 FROM AplicacionValidacionSubsidio avs JOIN ConjuntoValidacionSubsidio cvs ON cvs.cvsId = avs.avsConjuntoValidacionSubsidio WHERE avs.avsConjuntoValidacionSubsidio = 32 AND cvs.cvsTipoValidacion = 'TRABAJADOR_NO_APORTE_MINIMO')
UPDATE AplicacionValidacionSubsidio SET avsConjuntoValidacionSubsidio = 71 WHERE avsConjuntoValidacionSubsidio = 32

IF EXISTS(SELECT 1 FROM ConjuntoValidacionSubsidio WHERE cvsId = 32 AND cvsTipoValidacion = 'TRABAJADOR_NO_APORTE_MINIMO')
DELETE FROM ConjuntoValidacionSubsidio WHERE cvsId = 32 AND cvsTipoValidacion = 'TRABAJADOR_NO_APORTE_MINIMO'

--changeSET flopez:02
--comment: Mantis 266455

IF (NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'dbo' AND  TABLE_NAME = 'CondicionInvalidez_266455'))
CREATE TABLE [dbo].[CondicionInvalidez_266455](
[coiId] bigint NOT NULL,
[REV] bigint NOT NULL,
[REVTYPE] smallint NULL,
[coiPersona] bigint NOT NULL,
[coiInvalidez] bit NULL,
[coiFechaReporteInvalidez] date NULL,
[coiComentarioInvalidez] varchar(500) COLLATE Latin1_General_CI_AI NULL,
[coiFechaInicioInvalidez] date NULL
);