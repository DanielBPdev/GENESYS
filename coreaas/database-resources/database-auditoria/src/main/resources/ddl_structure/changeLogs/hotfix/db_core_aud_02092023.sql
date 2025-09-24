IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'AccionCobro2D_aud' AND TABLE_SCHEMA = 'dbo') 
alter table dbo.AccionCobro2D_aud add aodAnexoLiquidacion bit null