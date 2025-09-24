--liquibase formatted sql
--changeset abaquero:01 runAlways:true runOnChange:true
--comment: Creación de borrado de CKs 2018-12-26T13:20:15Z
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EntidadDescuento_endTipo')) ALTER TABLE EntidadDescuento DROP CONSTRAINT CK_EntidadDescuento_endTipo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EntidadDescuento_endEstado')) ALTER TABLE EntidadDescuento DROP CONSTRAINT CK_EntidadDescuento_endEstado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ConjuntoValidacionSubsidio_cvsTipoValidacion')) ALTER TABLE ConjuntoValidacionSubsidio DROP CONSTRAINT CK_ConjuntoValidacionSubsidio_cvsTipoValidacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ConjuntoValidacionSubsidio_cvsTipoProceso')) ALTER TABLE ConjuntoValidacionSubsidio DROP CONSTRAINT CK_ConjuntoValidacionSubsidio_cvsTipoProceso;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PeriodoLiquidacion_pelTipoPeriodo')) ALTER TABLE PeriodoLiquidacion DROP CONSTRAINT CK_PeriodoLiquidacion_pelTipoPeriodo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudLiquidacionSubsidio_slsTipoLiquidacion')) ALTER TABLE SolicitudLiquidacionSubsidio DROP CONSTRAINT CK_SolicitudLiquidacionSubsidio_slsTipoLiquidacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudLiquidacionSubsidio_slsTipoLiquidacionEspecifica')) ALTER TABLE SolicitudLiquidacionSubsidio DROP CONSTRAINT CK_SolicitudLiquidacionSubsidio_slsTipoLiquidacionEspecifica;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudLiquidacionSubsidio_slsEstadoLiquidacion')) ALTER TABLE SolicitudLiquidacionSubsidio DROP CONSTRAINT CK_SolicitudLiquidacionSubsidio_slsEstadoLiquidacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudLiquidacionSubsidio_slsTipoEjecucionProceso')) ALTER TABLE SolicitudLiquidacionSubsidio DROP CONSTRAINT CK_SolicitudLiquidacionSubsidio_slsTipoEjecucionProceso;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudLiquidacionSubsidio_slsRazonRechazoLiquidacion')) ALTER TABLE SolicitudLiquidacionSubsidio DROP CONSTRAINT CK_SolicitudLiquidacionSubsidio_slsRazonRechazoLiquidacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudLiquidacionSubsidio_slsTipoDesembolso')) ALTER TABLE SolicitudLiquidacionSubsidio DROP CONSTRAINT CK_SolicitudLiquidacionSubsidio_slsTipoDesembolso;