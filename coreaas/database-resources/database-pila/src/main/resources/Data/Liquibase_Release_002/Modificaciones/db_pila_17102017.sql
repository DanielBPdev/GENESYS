--liquibase formatted sql

--changeset arocha:01
--comment:Se adicionan campos en el esquema staging para las tablas Aportante,Cotizante,Novedad,NovedadSituacionPrimaria,AportePeriodo,BeneficioEmpresaPeriodo,SucursalEmpresa
ALTER TABLE staging.Aportante ADD apoShardName VARCHAR(500);
ALTER TABLE staging.Cotizante ADD cotShardName VARCHAR(500);
ALTER TABLE staging.Novedad ADD novShardName VARCHAR(500);
ALTER TABLE staging.NovedadSituacionPrimaria ADD nspShardName VARCHAR(500);
ALTER TABLE staging.AportePeriodo ADD appShardName VARCHAR(500);
ALTER TABLE staging.BeneficioEmpresaPeriodo ADD bepShardName VARCHAR(500);
ALTER TABLE staging.SucursalEmpresa ADD sueShardName VARCHAR(500);