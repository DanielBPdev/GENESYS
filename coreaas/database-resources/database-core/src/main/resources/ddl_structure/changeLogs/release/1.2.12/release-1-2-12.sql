--liquibase formatted sql

--changeset jcangrejo:01
--comment: creacion campos AnexoLiquidacion GLPI 36323


IF NOT EXISTS(SELECT 1 FROM sys.columns
            WHERE Name = 'aodAnexoLiquidacion'
            AND Object_ID = Object_ID('AccionCobro2D'))
BEGIN
alter table AccionCobro2D ADD  aodAnexoLiquidacion  bit
END


IF NOT EXISTS(SELECT 1 FROM sys.columns
              WHERE Name = 'aceAnexoLiquidacion'
                AND Object_ID = Object_ID('AccionCobro2E'))
BEGIN
alter table AccionCobro2E  ADD  aceAnexoLiquidacion  bit
END

IF NOT EXISTS(SELECT 1 FROM sys.columns
              WHERE Name = 'aodAnexoLiquidacion'
                AND Object_ID = Object_ID('aud.AccionCobro2D_aud'))
BEGIN
alter table aud.AccionCobro2D_aud ADD  aodAnexoLiquidacion  bit
END

IF NOT EXISTS(SELECT 1 FROM sys.columns
              WHERE Name = 'aceAnexoLiquidacion'
                AND Object_ID = Object_ID('aud.AccionCobro2E_aud'))
BEGIN
alter table aud.AccionCobro2E_aud ADD aceAnexoLiquidacion  bit
END