--liquibase formatted sql

--changeset abaquero:01
--comment: Creacion de tablas del modelo de datos para aportes

CREATE TABLE Aportante(
	[apoId] bigint NOT NULL IDENTITY,
	[apoEmpleador] bigint,
	[apoEntidadPagadora] bigint,
	[apoPersona] bigint,
	[apoEstadoAportanteInicial] varchar(50),
	[apoEstadoAportanteFinal] varchar(60),
	[apoEstadoAporteAportante] varchar(40),
	[apoValorMoraAportante] numeric(30,2),
	[apoEstadoRegistroAporteAportante] varchar(30),
	CONSTRAINT PK_Aportante_apoId PRIMARY KEY (apoId)
);

CREATE TABLE Cotizante(
	[cotId] bigint NOT NULL IDENTITY,
	[cotTipoCotizante] varchar(100),
	[cotAfiliadoCotizante] bigint,
	[cotEstadoCotizanteInicial] varchar(60),
	[cotEstadoCotizanteFinal] varchar(60),
	[cotValorMoraCotizante] numeric(30,2), 
	[cotEstadoAporteCotizante] varchar(50),
	[cotEstadoRegistroAporteCotizante] varchar(40),
	CONSTRAINT PK_Cotizante_cotId PRIMARY KEY (cotId)
);

CREATE TABLE AporteGeneral(
	[apgId] bigint NOT NULL IDENTITY,
	[apgIndicePlanilla] bigint,
	[apgPeriodoAporte] varchar(7), 
	[apgValTotalApoObligatorio] bigint,
	[apgValorIntMora] bigint,
	[apgFechaRecaudo] date,
	[apgFechaProcesamiento] datetime,
	[apgAportante] bigint, 
	[apgCodEntidadFinanciera] smallint,
	[apgOperadorInformacion] bigint,
	[apgModalidadPlanilla] varchar(40),
	[apgModalidadRecaudoAporte] varchar(40),
	CONSTRAINT PK_AporteGeneral_apgId PRIMARY KEY (apgId)
);

CREATE TABLE AporteDetallado(
	[apdId] bigint NOT NULL IDENTITY,
	[apdAporteGeneral] bigint,   
	[apdArchivoIRegistro2] bigint,
	[apdArchivoIPRegistro2] bigint,
	[apdCotizante] bigint, 
	[apdDiasCotizados] smallint,
	[apdHorasLaboradas] smallint,
	[apdSalarioBasico] bigint,
	[apdUbicacionLaboral] bigint,
	[apdValorIBC] int,
	[apdValorIntMora] int, 
	[apdTarifa] numeric (5,5),
	[apdValorSalarioIntegral] bigint,            
	[apdAporteObligatorio] bigint,
	[apdValorSaldoAporte] bigint,
	[apdCorrecciones] varchar(400),
	[apdFechaProcesamiento] datetime,
	[apdEstadoAporteRecaudo] varchar(50),
	[apdEstadoAporteAjuste] varchar(50),
	[apdEstadoRegistroAporte] varchar(50),
	CONSTRAINT PK_AporteDetallado_apdId PRIMARY KEY (apdId)
);

CREATE TABLE MovimientoAjusteAporte(
	[maaId] bigint NOT NULL IDENTITY,
	[maaTipoMovimientoRecaudoAporte] varchar(40),
	[maaIndicePlanillaOriginal] bigint,
	[maaIndicePlanillaCorregida] bigint,
	[maaAporteDetalladoOriginal] bigint,
	[maaAporteDetalladoCorregido] bigint,
	[maaPeriodoMovimientoAporte] varchar(7),
	[maaFechaRegisroMovimientoAporte] datetime,
	[maaValorAporteRegOriginal] numeric(38,3),
	[maaValorIntAporteRegOriginal] numeric(38,3),
	[maaValorTotalAporteRegOriginal] numeric(38,3),
	[maaTipoAjusteMovimientoAporte] varchar(40),
	[maaValorAjusteRegAporte] numeric(38,3),
	[maaValorIntAjusteRegAporte] numeric(38,3),
	[maaValorTotalAjusteRegAporte] numeric(38,3),
	[maaValorFinalRegAporte] numeric(38,3),
	[maaValorIntFinalRegAporte] numeric(38,3),
	[maaValorTotalFinalRegAporte] numeric(38,3),
	[maaEstadoAjusteRegAporte] varchar(40),   
	CONSTRAINT PK_MovimientoAjusteAporte_maaId PRIMARY KEY (maaId)
);

CREATE TABLE GlosaComentarioNovedad(
	[gcnId] bigint NOT NULL IDENTITY,
	[gcnNombreGlosaNovedad] varchar(60),
	[gcnDescripcionGlosaNovedad] varchar(400),
	[gcnEstadoGlosaNovedad] bit,
	CONSTRAINT PK_GlosaComentarioNovedad_gcnId PRIMARY KEY (gcnId)
);

CREATE TABLE RegistroPilaNovedad(
	[rpnId] bigint NOT NULL IDENTITY,
	[rpnRegistroAporteDetallado] bigint,
	[rpnTipoNovedadPila] varchar(30),
	[rpnFechaRegistroNovedad] datetime,
	[rpnTipoTransaccionNovedad] varchar(90),    
	[rpnFechaInicialNovedad] datetime,
	[rpnFechaFinalNovedad] datetime,
	[rpnMarcaNovedad] varchar(40),      
	[rpnFechaProcesamientoNovedad] datetime,
	[rpnUsuarioProcesamientoNovedad] varchar(100),
	[rpnCanalRecepcion] varchar(30),
	[rpnEstadoNovedad] varchar(40),
	[rpnGlosaNovedad] bigint,
	CONSTRAINT PK_RegistroPilaNovedad_rpnId PRIMARY KEY (rpnId)
);

CREATE TABLE AporteGeneralSimulado(
	[agsId] bigint NOT NULL IDENTITY,
	[agsIndicePlanilla] bigint,
	[agsPeriodoAporte] varchar(7), 
	[agsValTotalApoObligatorio] bigint,    
	[agsValorIntMora] bigint,
	[agsFechaRecaudo] date,
	[agsFechaProcesamiento] datetime,
	[agsAportante] bigint,       
	[agsCodEntidadFinanciera] smallint,
	[agsOperadorInformacion] bigint,
	[agsModalidadPlanilla] varchar(40),
	[agsModalidadRecaudoAporte] varchar(40),
	CONSTRAINT PK_AporteGeneral_agsId PRIMARY KEY (agsId)
);

CREATE TABLE AporteDetalladoSimulado(
	[adsId] bigint NOT NULL IDENTITY,
	[adsAporteGeneral] bigint,   
	[adsArchivoIRegistro2] bigint,
	[adsArchivoIPRegistro2] bigint,
	[adsCotizante] bigint, 
	[adsDiasCotizados] smallint,
	[adsHorasLaboradas] smallint,
	[adsSalarioBasico] bigint,
	[adsUbicacionLaboral] bigint,
	[adsValorIBC] int,
	[adsValorIntMora] int, 
	[adsTarifa] numeric (5,5),
	[adsValorSalarioIntegral] bigint,               
	[adsAporteObligatorio] bigint,
	[adsValorSaldoAporte] bigint,
	[adsValorTotalAporteObligatorio] bigint, 
	[adsCorrecciones] varchar(400),
	[adsFechaProcesamiento] datetime,
	[adsEstadoAporteRecaudo] varchar(50),
	[adsEstadoAporteAjuste] varchar(50),
	[adsEstadoRegistroAporte] varchar(50),
	[adsFechaProcesamientoValidRegAporte] datetime,
	[adsEstadoValidacionV0] varchar(30),
	[adsEstadoValidacionV1] varchar(30),
	[adsEstadoValidacionV2] varchar(30),
	[adsEstadoValidacionV3] varchar(30),
	CONSTRAINT PK_AporteDetallado_adsId PRIMARY KEY (adsId)
);

ALTER TABLE [Aportante] ADD CONSTRAINT [FK_Aportante_apoEmpleador] FOREIGN KEY([apoEmpleador]) REFERENCES [Empleador] ([empId]);
ALTER TABLE [Aportante] ADD CONSTRAINT [FK_Aportante_apoEntidadPagadora] FOREIGN KEY([apoEntidadPagadora]) REFERENCES [EntidadPagadora] ([epaId]);
ALTER TABLE [Aportante] ADD CONSTRAINT [FK_Aportante_apoPersona] FOREIGN KEY([apoPersona]) REFERENCES [Persona] ([perId]);

ALTER TABLE [Cotizante] ADD CONSTRAINT [FK_Cotizante_cotAfiliadoCotizante] FOREIGN KEY([cotAfiliadoCotizante]) REFERENCES [RolAfiliado] ([roaId]);

ALTER TABLE [AporteGeneral] ADD CONSTRAINT [FK_AporteGeneral_apgIndicePlanilla] FOREIGN KEY([apgIndicePlanilla]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [AporteGeneral] ADD CONSTRAINT [FK_AporteGeneral_apgAportante] FOREIGN KEY([apgAportante]) REFERENCES [Aportante] ([apoId]);
ALTER TABLE [AporteGeneral] ADD CONSTRAINT [FK_AporteGeneral_apgOperadorInformacion] FOREIGN KEY([apgOperadorInformacion]) REFERENCES [OperadorInformacion] ([oinId]);

ALTER TABLE [AporteDetallado] ADD CONSTRAINT [FK_AporteDetallado_apdAporteGeneral] FOREIGN KEY([apdAporteGeneral]) REFERENCES [AporteGeneral] ([apgId]);
ALTER TABLE [AporteDetallado] ADD CONSTRAINT [FK_AporteDetallado_apdArchivoIRegistro2] FOREIGN KEY([apdArchivoIRegistro2]) REFERENCES [PilaArchivoIRegistro2] ([pi2Id]);
ALTER TABLE [AporteDetallado] ADD CONSTRAINT [FK_AporteDetallado_apdArchivoIPRegistro2] FOREIGN KEY([apdArchivoIPRegistro2]) REFERENCES [PilaArchivoIPRegistro2] ([ip2Id]);
ALTER TABLE [AporteDetallado] ADD CONSTRAINT [FK_AporteDetallado_apdCotizante] FOREIGN KEY([apdCotizante]) REFERENCES [Cotizante] ([cotId]);
ALTER TABLE [AporteDetallado] ADD CONSTRAINT [FK_AporteDetallado_apdUbicacionLaboral] FOREIGN KEY([apdUbicacionLaboral]) REFERENCES [Ubicacion] ([ubiId]);

ALTER TABLE [MovimientoAjusteAporte] ADD CONSTRAINT [FK_MovimientoAjusteAporte_maaIndicePlanillaOriginal] FOREIGN KEY([maaIndicePlanillaOriginal]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [MovimientoAjusteAporte] ADD CONSTRAINT [FK_MovimientoAjusteAporte_maaIndicePlanillaCorregida] FOREIGN KEY([maaIndicePlanillaCorregida]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [MovimientoAjusteAporte] ADD CONSTRAINT [FK_MovimientoAjusteAporte_maaAporteDetalladoOriginal] FOREIGN KEY([maaAporteDetalladoOriginal]) REFERENCES [AporteDetallado] ([apdId]);
ALTER TABLE [MovimientoAjusteAporte] ADD CONSTRAINT [FK_MovimientoAjusteAporte_maaAporteDetalladoCorregido] FOREIGN KEY([maaAporteDetalladoCorregido]) REFERENCES [AporteDetallado] ([apdId]);

ALTER TABLE [RegistroPilaNovedad] ADD CONSTRAINT [FK_RegistroPilaNovedad_rpnRegistroAporteDetallado] FOREIGN KEY([rpnRegistroAporteDetallado]) REFERENCES [AporteDetallado] ([apdId]);
ALTER TABLE [RegistroPilaNovedad] ADD CONSTRAINT [FK_RegistroPilaNovedad_rpnGlosaNovedad] FOREIGN KEY([rpnGlosaNovedad]) REFERENCES [GlosaComentarioNovedad] ([gcnId]);

--changeset jzambrano:02
--comment: Cambio de tamaño del campo usuario en la tabla ConsolaEstadoCargueMasivo
ALTER TABLE ConsolaEstadoCargueMasivo ALTER COLUMN cecUsuario varchar (40)



