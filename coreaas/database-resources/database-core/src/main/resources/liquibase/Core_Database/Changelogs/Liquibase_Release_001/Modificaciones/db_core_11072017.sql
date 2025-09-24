--liquibase formatted sql

--changeset hhernandez:01
--comment:Modificacion a las tablas AporteGeneral y AporteDetallado
ALTER TABLE dbo.AporteGeneral DROP CONSTRAINT FK_AporteGeneral_apgIndicePlanilla;
ALTER TABLE dbo.AporteGeneral DROP CONSTRAINT FK_AporteGeneral_apgAportante;
ALTER TABLE dbo.AporteGeneral DROP COLUMN apgIndicePlanilla;
ALTER TABLE dbo.AporteGeneral DROP COLUMN apgAportante;
ALTER TABLE dbo.AporteGeneral ADD apgRegistroGeneral bigint NOT NULL;
ALTER TABLE dbo.AporteGeneral ADD apgPersona bigint NULL;
ALTER TABLE dbo.AporteGeneral ADD apgEmpresa bigint NULL;
ALTER TABLE dbo.AporteGeneral ADD apgSucursalEmpresa bigint NULL;
ALTER TABLE dbo.AporteGeneral ADD apgEstadoAportante varchar(50) NULL;
ALTER TABLE dbo.AporteGeneral ADD apgEstadoAporteAportante varchar(40) NULL;
ALTER TABLE dbo.AporteGeneral ADD apgEstadoRegistroAporteAportante varchar(30) NULL;
ALTER TABLE dbo.AporteGeneral ADD apgPagadorPorTerceros bit NULL;
ALTER TABLE dbo.AporteGeneral ALTER COLUMN apgValTotalApoObligatorio numeric(19,5) NULL;
ALTER TABLE dbo.AporteGeneral ALTER COLUMN apgValorIntMora numeric(19,5) NULL;
ALTER TABLE dbo.AporteGeneral ADD CONSTRAINT FK_AporteGeneral_apgPersona FOREIGN KEY (apgPersona) REFERENCES dbo.Persona (perId);
ALTER TABLE dbo.AporteGeneral ADD CONSTRAINT FK_AporteGeneral_apgEmpresa FOREIGN KEY (apgEmpresa) REFERENCES dbo.Empresa (empId);
ALTER TABLE dbo.AporteGeneral ADD CONSTRAINT FK_AporteGeneral_apgSucursalEmpresa FOREIGN KEY (apgSucursalEmpresa) REFERENCES dbo.SucursalEmpresa (sueId);
ALTER TABLE dbo.AporteDetallado DROP CONSTRAINT FK_AporteDetallado_apdArchivoIPRegistro2;
ALTER TABLE dbo.AporteDetallado DROP CONSTRAINT FK_AporteDetallado_apdArchivoIRegistro2;
ALTER TABLE dbo.AporteDetallado DROP CONSTRAINT FK_AporteDetallado_apdCotizante;
ALTER TABLE dbo.AporteDetallado DROP COLUMN apdArchivoIRegistro2;
ALTER TABLE dbo.AporteDetallado DROP COLUMN apdArchivoIPRegistro2;
ALTER TABLE dbo.AporteDetallado DROP COLUMN apdCotizante;
ALTER TABLE dbo.AporteDetallado ADD apdRegistroDetallado bigint NOT NULL;
ALTER TABLE dbo.AporteDetallado ADD apdTipoCotizante varchar(100) NULL;
ALTER TABLE dbo.AporteDetallado ADD apdEstadoCotizante varchar(60) NULL;
ALTER TABLE dbo.AporteDetallado ADD apdEstadoAporteCotizante varchar(50) NULL;
ALTER TABLE dbo.AporteDetallado ADD apdEstadoRegistroAporteCotizante varchar(40) NULL;
ALTER TABLE dbo.AporteDetallado ADD apdPersona bigint NULL;
ALTER TABLE dbo.AporteDetallado ALTER COLUMN apdSalarioBasico numeric(19,5) NULL;
ALTER TABLE dbo.AporteDetallado ALTER COLUMN apdValorIBC numeric(19,5) NULL;
ALTER TABLE dbo.AporteDetallado ALTER COLUMN apdValorIntMora numeric(19,5) NULL;
ALTER TABLE dbo.AporteDetallado ALTER COLUMN apdValorSaldoAporte numeric(19,5) NULL;
ALTER TABLE dbo.AporteDetallado ALTER COLUMN apdAporteObligatorio numeric(19,5) NULL;
ALTER TABLE dbo.AporteDetallado ADD CONSTRAINT FK_AporteDetallado_apdPersona FOREIGN KEY (apdPersona) REFERENCES dbo.Persona (perId);

--changeset flopez:02
--comment:Modificacion a la tabla BeneficioEmpleador
ALTER TABLE dbo.BeneficioEmpleador DROP CONSTRAINT UK_BeneficioEmpleador_bemEmpleador;
ALTER TABLE dbo.BeneficioEmpleador DROP COLUMN bemMotivoInactivacionBeneficioLey1429;
ALTER TABLE dbo.BeneficioEmpleador DROP COLUMN bemMotivoInactivacionBeneficioLey590;
ALTER TABLE dbo.BeneficioEmpleador DROP COLUMN bemTipoBeneficio;
ALTER TABLE dbo.BeneficioEmpleador ADD bemBeneficio bigint NOT NULL;
ALTER TABLE dbo.BeneficioEmpleador ADD bemMotivoInactivacion varchar (50) NULL;
EXEC sp_rename 'dbo.BeneficioEmpleador.bemFechaBeneficioInicio', 'bemFechaVinculacion', 'COLUMN';
EXEC sp_rename 'dbo.BeneficioEmpleador.bemFechaBeneficioFin', 'bemFechaDesvinculacion', 'COLUMN';
ALTER TABLE dbo.BeneficioEmpleador ADD CONSTRAINT FK_BeneficioEmpleador_bemBeneficio FOREIGN KEY (bemBeneficio) REFERENCES dbo.Beneficio (befId);
