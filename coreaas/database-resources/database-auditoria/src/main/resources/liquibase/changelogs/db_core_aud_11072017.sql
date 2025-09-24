--liquibase formatted sql

--changeset hhernandez:01
--comment:Modificacion a las tablas AporteGeneral_aud y AporteDetallado_aud
ALTER TABLE dbo.AporteGeneral_aud DROP COLUMN apgIndicePlanilla;
ALTER TABLE dbo.AporteGeneral_aud DROP COLUMN apgAportante;
ALTER TABLE dbo.AporteGeneral_aud ADD apgRegistroGeneral bigint NOT NULL;
ALTER TABLE dbo.AporteGeneral_aud ADD apgPersona bigint NULL;
ALTER TABLE dbo.AporteGeneral_aud ADD apgEmpresa bigint NULL;
ALTER TABLE dbo.AporteGeneral_aud ADD apgSucursalEmpresa bigint NULL;
ALTER TABLE dbo.AporteGeneral_aud ADD apgEstadoAportante varchar(50) NULL;
ALTER TABLE dbo.AporteGeneral_aud ADD apgEstadoAporteAportante varchar(40) NULL;
ALTER TABLE dbo.AporteGeneral_aud ADD apgEstadoRegistroAporteAportante varchar(30) NULL;
ALTER TABLE dbo.AporteGeneral_aud ADD apgPagadorPorTerceros bit NULL;
ALTER TABLE dbo.AporteGeneral_aud ALTER COLUMN apgValTotalApoObligatorio numeric(19,5) NULL;
ALTER TABLE dbo.AporteGeneral_aud ALTER COLUMN apgValorIntMora numeric(19,5) NULL;
ALTER TABLE dbo.AporteDetallado_aud DROP COLUMN apdArchivoIRegistro2;
ALTER TABLE dbo.AporteDetallado_aud DROP COLUMN apdArchivoIPRegistro2;
ALTER TABLE dbo.AporteDetallado_aud DROP COLUMN apdCotizante;
ALTER TABLE dbo.AporteDetallado_aud ADD apdRegistroDetallado bigint NOT NULL;
ALTER TABLE dbo.AporteDetallado_aud ADD apdTipoCotizante varchar(100) NULL;
ALTER TABLE dbo.AporteDetallado_aud ADD apdEstadoCotizante varchar(60) NULL;
ALTER TABLE dbo.AporteDetallado_aud ADD apdEstadoAporteCotizante varchar(50) NULL;
ALTER TABLE dbo.AporteDetallado_aud ADD apdEstadoRegistroAporteCotizante varchar(40) NULL;
ALTER TABLE dbo.AporteDetallado_aud ADD apdPersona bigint NULL;
ALTER TABLE dbo.AporteDetallado_aud ALTER COLUMN apdSalarioBasico numeric(19,5) NULL;
ALTER TABLE dbo.AporteDetallado_aud ALTER COLUMN apdValorIBC numeric(19,5) NULL;
ALTER TABLE dbo.AporteDetallado_aud ALTER COLUMN apdValorIntMora numeric(19,5) NULL;
ALTER TABLE dbo.AporteDetallado_aud ALTER COLUMN apdValorSaldoAporte numeric(19,5) NULL;
ALTER TABLE dbo.AporteDetallado_aud ALTER COLUMN apdAporteObligatorio numeric(19,5) NULL;

--changeset flopez:02
--comment:Modificacion a la tabla BeneficioEmpleador
ALTER TABLE dbo.BeneficioEmpleador_aud DROP COLUMN bemMotivoInactivacionBeneficioLey1429;
ALTER TABLE dbo.BeneficioEmpleador_aud DROP COLUMN bemMotivoInactivacionBeneficioLey590;
ALTER TABLE dbo.BeneficioEmpleador_aud DROP COLUMN bemTipoBeneficio;
ALTER TABLE dbo.BeneficioEmpleador_aud ADD bemBeneficio bigint NOT NULL;
ALTER TABLE dbo.BeneficioEmpleador_aud ADD bemMotivoInactivacion varchar (50) NULL;
EXEC sp_rename 'dbo.BeneficioEmpleador_aud.bemFechaBeneficioInicio', 'bemFechaVinculacion', 'COLUMN';
EXEC sp_rename 'dbo.BeneficioEmpleador_aud.bemFechaBeneficioFin', 'bemFechaDesvinculacion', 'COLUMN';