--liquibase formatted sql

--changeset jusanchez:01
--comment:Se modifica campo en la tabla SolicitudGestionCobroManual_aud
EXEC sp_rename 'SolicitudGestionCobroManual_aud.scmCicloCartera', 'scmCicloAportante', 'COLUMN'; 

--changeset clmarin:02
--comment:Se adiciona campo en la tabla DocumentoCartera_aud
ALTER TABLE DocumentoCartera_aud ADD dcaAccionCobro VARCHAR(4) NULL;

--changeset jocorrea:03
--comment:Se adiciona campo en la tabla AhorroPrevio_aud
ALTER TABLE AhorroPrevio_aud ADD ahpAhorroMovilizado BIT NULL;

--changeset clmarin:04
--comment:Se modifica campos en la tabla ParametrizacionDesafiliacion_aud
ALTER TABLE ParametrizacionDesafiliacion_aud ALTER COLUMN pdeProgramacionEjecucion VARCHAR(8) NULL;
ALTER TABLE ParametrizacionDesafiliacion_aud ALTER COLUMN pdeMontoMoraInexactitud NUMERIC (19,5) NULL;
ALTER TABLE ParametrizacionDesafiliacion_aud ALTER COLUMN pdePeriodosMora BIGINT NULL;
ALTER TABLE ParametrizacionDesafiliacion_aud ALTER COLUMN pdeMetodoEnvioComunicado VARCHAR(11) NULL;
ALTER TABLE ParametrizacionDesafiliacion_aud ALTER COLUMN pdeSiguienteAccion VARCHAR(29) NULL;
