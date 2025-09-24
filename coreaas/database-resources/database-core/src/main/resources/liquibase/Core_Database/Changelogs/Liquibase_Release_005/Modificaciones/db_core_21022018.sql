--liquibase formatted sql

--changeset squintero:01
--comment:Se adicionan campo y actualizan registros a las tablas ParametrizacionCondicionesSubsidio y ParametrizacionLiquidacionSubsidio
ALTER TABLE ParametrizacionCondicionesSubsidio ADD pcsCodigoCajaCompensacion VARCHAR(5) NULL;
ALTER TABLE ParametrizacionLiquidacionSubsidio ADD plsCodigoCajaCompensacion VARCHAR(5) NULL;
UPDATE ParametrizacionCondicionesSubsidio SET pcsCodigoCajaCompensacion = (SELECT cnsValor FROM Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO');
UPDATE ParametrizacionLiquidacionSubsidio SET plsCodigoCajaCompensacion = (SELECT cnsValor FROM Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO');
ALTER TABLE ParametrizacionCondicionesSubsidio ALTER COLUMN pcsCodigoCajaCompensacion VARCHAR(5) NOT NULL;
ALTER TABLE ParametrizacionLiquidacionSubsidio ALTER COLUMN plsCodigoCajaCompensacion VARCHAR(5) NOT NULL;
