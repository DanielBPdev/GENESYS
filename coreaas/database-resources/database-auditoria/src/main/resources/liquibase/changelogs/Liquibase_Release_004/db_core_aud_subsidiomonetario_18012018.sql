--liquibase formatted sql

--changeset rlopez:01
--comment: Se adicionan campos en la tabla ParametrizacionCondicionesSubsidio_aud
ALTER TABLE ParametrizacionCondicionesSubsidio_aud ADD pcsCantidadSubsidiosLiquidados INT NULL;
ALTER TABLE ParametrizacionCondicionesSubsidio_aud ADD pcsMontoSubsidiosLiquidados NUMERIC(10,0) NULL;
ALTER TABLE ParametrizacionCondicionesSubsidio_aud ADD pcsCantidadSubsidiosLiquidadosInvalidez INT NULL;
ALTER TABLE ParametrizacionCondicionesSubsidio_aud ADD pcsCantidadPeriodosRetroactivosMes INT NULL;
