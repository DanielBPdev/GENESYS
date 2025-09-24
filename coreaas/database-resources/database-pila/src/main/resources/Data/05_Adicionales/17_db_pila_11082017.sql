--liquibase formatted sql

--changeset abaquero:01
--comment: Se adiciona campos en las tablas staging.SucursalEmpresa, staging.RegistroGeneral, staging.RegistroDetallado y dbo.TemAportante 
ALTER TABLE staging.SucursalEmpresa ADD sueEsSucursalPrincipal bit;
ALTER TABLE dbo.TemAportante DROP COLUMN tapCodSucursal; 
ALTER TABLE dbo.TemAportante DROP COLUMN tapNomSucursal; 
ALTER TABLE dbo.TemAportante ADD tapMarcaSucursal bit;
ALTER TABLE staging.RegistroGeneral ADD regOUTMarcaSucursalPILA bit;
ALTER TABLE staging.RegistroGeneral ADD regOUTCodSucursalPrincipal VARCHAR(10);
ALTER TABLE staging.RegistroGeneral ADD regOUTNomSucursalPrincipal VARCHAR(100);
ALTER TABLE staging.RegistroDetallado ADD redOUTCodSucursal VARCHAR(10);
ALTER TABLE staging.RegistroDetallado ADD redOUTNomSucursal VARCHAR(100);

--changeset arocha:02
--comment: Se adiciona campos en las tablas staging.SucursalEmpresa
ALTER TABLE staging.RegistroDetallado ADD redOUTDiasCotizadosPlanillas SMALLINT;
ALTER TABLE staging.RegistroDetallado ADD redOUTDiasCotizadosBD SMALLINT;
ALTER TABLE staging.RegistroDetallado ADD redOUTDiasCotizadosNovedades SMALLINT;
