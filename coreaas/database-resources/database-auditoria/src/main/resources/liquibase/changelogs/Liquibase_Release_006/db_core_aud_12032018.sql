--liquibase formatted sql

--changeset squintero:01
--comment:Se adiciona campo en la tabla Empresa_aud
ALTER TABLE Empresa_aud ADD empFechaFiscalizacion DATE NULL;

--changeset rlopez:02
--comment:Se adiciona campos en la tabla SolicitudLiquidacionSubsidio_aud
ALTER TABLE SolicitudLiquidacionSubsidio_aud ADD slsConsideracionAporteDesembolso BIT NULL; 
ALTER TABLE SolicitudLiquidacionSubsidio_aud ADD slsTipoDesembolso VARCHAR(40) NULL;
