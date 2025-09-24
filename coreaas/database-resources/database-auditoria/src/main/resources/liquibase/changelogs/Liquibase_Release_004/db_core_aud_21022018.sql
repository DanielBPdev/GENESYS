--liquibase formatted sql

--changeset squintero:01
--comment:Se adicionan campo y actualizan registros a las tablas ParametrizacionCondicionesSubsidio y ParametrizacionLiquidacionSubsidio
ALTER TABLE ParametrizacionCondicionesSubsidio_aud ADD pcsCodigoCajaCompensacion VARCHAR(5) NULL;
ALTER TABLE ParametrizacionLiquidacionSubsidio_aud ADD plsCodigoCajaCompensacion VARCHAR(5) NULL;
UPDATE ParametrizacionCondicionesSubsidio_aud SET pcsCodigoCajaCompensacion = (SELECT cnsValor FROM Constante_aud WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO');
UPDATE ParametrizacionLiquidacionSubsidio_aud SET plsCodigoCajaCompensacion = (SELECT cnsValor FROM Constante_aud WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO');
ALTER TABLE ParametrizacionCondicionesSubsidio_aud ALTER COLUMN pcsCodigoCajaCompensacion VARCHAR(5) NOT NULL;
ALTER TABLE ParametrizacionLiquidacionSubsidio_aud ALTER COLUMN plsCodigoCajaCompensacion VARCHAR(5) NOT NULL;
