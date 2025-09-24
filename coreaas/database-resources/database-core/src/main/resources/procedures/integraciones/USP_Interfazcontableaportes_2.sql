CREATE OR ALTER PROCEDURE [sap].[USP_Interfazcontableaportes_2] 

	@planilla VARCHAR(20),
	@regId BIGINT,
	@solNumeroRadicacion VARCHAR (30),
	@solTipoTransaccion VARCHAR (30),
	@operadorinformacion INT


AS 
BEGIN
SET NOCOUNT ON;


IF (SELECT COUNT(*) FROM sap.ejecucion_aportes2 WITH (NOLOCK)) <= 0 BEGIN	
	
	INSERT INTO sap.ejecucion_aportes2
		SELECT 1


	DECLARE	@referencia VARCHAR(16), @referenciaNum BIGINT, @consecutivo BIGINT, @comentario VARCHAR(2) 
	DECLARE @numeroDocumento VARCHAR(50), @tipoDocumento VARCHAR(20), @usuario VARCHAR(30) = 'soportetecnico@asopagos.com', @consecutivodet BIGINT
	
	
	SELECT regid, regNumPlanilla, regOperadorInformacion,regValTotalApoObligatorio, regValorIntMora,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
			regTipoPlanilla,regDateTimeInsert,regOUTEstadoArchivo,regNumPlanillaAsociada,regNombreAportante
	INTO #registrogeneralap2			
	FROM core.pila.RegistroGeneral	
	WHERE (regnumplanilla = @planilla AND regOperadorInformacion = @operadorinformacion) OR (regid = @regId AND regid <> 0) 	--Cambio 20/09/2023 

	--and regDateTimeInsert IS NULL OR regDateTimeInsert >= dateadd(year,-5,getdate())
		 

	CREATE TABLE #IC_Aportes_Enc
	(
		[fecIng] [date] NOT NULL,
		[horaIng] [time](7) NOT NULL,
		[fechaDocumento] [date] NOT NULL,
		[fechaContabilizacion] [date] NOT NULL,
		[periodo] [varchar](2) NOT NULL,
		[referencia] [varchar](16) NOT NULL,
		[tipoMovimiento] [varchar](7) NOT NULL,
		[consecutivo] [bigint] NOT NULL,
		[observacion] [varchar](2000) NULL,
		[moneda] [varchar](5) NOT NULL,
		[documentoContable] [varchar](10) NULL,
		[sociedad] [varchar](4) NOT NULL,
		[ejercicio] [varchar](4) NULL,
		[nroIntentos] [smallint] NOT NULL,
		[fecProceso] [date] NULL,
		[horaProceso] [time](7) NULL,
		[estadoReg] [varchar](1) NOT NULL,
		[usuario] [varchar](50) NOT NULL,
		[estadoaporteaportantetercero] [VARCHAR](30) NULL
	)

	CREATE TABLE #IC_Aportes_Det
	(
		[consecutivo] [bigint] NOT NULL,
		[codigoSap] [varchar](10) NULL,
		[importeDocumento] NUMERIC(21,5) NOT NULL,
		[operador] [varchar](1) NULL,
		[claveCont] [varchar](2) NOT NULL,
		[asignacion] [varchar](18) NOT NULL,
		[textoposicion] varchar (50) Null,
		[ref1] [varchar](12) NOT NULL,
		[ref2] [varchar](12) NULL,
		[tipoDocumento] [varchar](3) NOT NULL,
		[ref3] [varchar](20) NOT NULL,
		[noIdentificado] [varchar](2) NULL,
		[adelantado] [varchar](2) NULL,
		[identificadorDelBanco] [varchar](18) NULL,
		[codigoBanco] [varchar](15) NULL,
		[transitoria] [varchar](1) NULL,
		[claseDeAporte] [varchar](3) NULL,
		[claseDeAportePrescripcion] [varchar](1) NULL,
		[tieneIntereses] [bit] NOT NULL,
		[tipoInteres] [varchar](3) NULL,
		[correccion] [bit] NOT NULL,
		[tipoMora] [varchar](2) NULL,
		[id] [bigint] NOT NULL,
		[codigoGenesys] [bigint] NULL,
		[tipo] [varchar](1) NULL,
		[claseCuenta] [varchar](1) NOT NULL,
		[usuario] [varchar](50) NOT NULL,
		-- registros para trabajar en el movimiento
		regId [bigint] NULL,
		fechaRecaudo DATE NULL,
		fechaProcesamiento DATE NULL,
		EstadoRegistroAporteAportante VARCHAR(30) NULL,
		FormaReconocimientoAporte VARCHAR(75) NULL,
		apgEstadoAportante VARCHAR(50) NULL,
		marcaPeriodo VARCHAR(19) NULL,
		tipoSolicitante VARCHAR(13) NULL,
		valorIntMora NUMERIC(21,5) NULL,
		tarifa NUMERIC(7,5) NULL,
		apgOrigenAporte VARCHAR (26) NULL,
		regIdorg [bigint] NULL,
		docorg [varchar](12) NULL,
		[claseDeAporteorg] [varchar](3) NULL,
		tipoInteresOrg [varchar] (3) Null,
		operadorinformacion int Null,
		[noIdentificadoOri] [varchar](2)
	)

	CREATE TABLE #IC_Aportes_Det_Swap
	(
		[consecutivo] bigint NOT NULL,
		[codigoSap] [varchar](10) NULL,
		[importeDocumento] NUMERIC(21,5) NOT NULL,
		[operador] [varchar](1) NULL,
		[claveCont] [varchar](2) NOT NULL,
		[asignacion] [varchar](18) NOT NULL,
		[ref1] [varchar](12) NOT NULL,
		[ref2] [varchar](12) NULL,
		[tipoDocumento] [varchar](3) NOT NULL,
		[ref3] [varchar](20) NOT NULL,
		[noIdentificado] [varchar](2) NULL,
		[adelantado] [varchar](2) NULL,
		[identificadorDelBanco] [varchar](18) NULL,
		[codigoBanco] [varchar](15) NULL,
		[transitoria] [varchar](1) NULL,
		[claseDeAporte] [varchar](3) NULL,
		[claseDeAportePrescripcion] [varchar](1) NULL,
		[tieneIntereses] [bit] NOT NULL,
		[tipoInteres] [varchar](3) NULL,
		[correccion] [bit] NOT NULL,
		[tipoMora] [varchar](2) NULL,
		[id] [bigint] NOT NULL,
		[codigoGenesys] bigint NULL,
		[tipo] [varchar](1) NULL,
		[claseCuenta] [varchar](1) NOT NULL,
		[usuario] [varchar](50) NOT NULL,
		-- registros para trabajar en el movimiento
		regId bigint NULL,
		fechaRecaudo DATE NULL,
		fechaProcesamiento DATE NULL,
		EstadoRegistroAporteAportante VARCHAR(30) NULL,
		FormaReconocimientoAporte VARCHAR(75) NULL,
		apgEstadoAportante VARCHAR(50) NULL,
		marcaPeriodo VARCHAR(19) NULL,
		tipoSolicitante VARCHAR(13) NULL,
		valorIntMora NUMERIC(21,5) NULL,
		tarifa NUMERIC(7,5) NULL,
		apgOrigenAporte VARCHAR (26) NULL,
		regIdorg [bigint] NULL,
		docorg [varchar](12) NULL,
		[claseDeAporte2] [varchar](3) NULL,
		tipoInteresOrg [varchar] (3) Null,
		operadorinformacion int Null
	)



BEGIN TRY 
	BEGIN TRANSACTION
	
		-- DETALLE: reconoce los registros de PILA
		INSERT INTO #IC_Aportes_Det(
			[consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
			[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
			[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
			[claseCuenta],[usuario],regId,valorIntMora,tarifa,apgOrigenAporte,operadorinformacion)

		SELECT  MIN([consecutivo]) AS consecutivo,codigoSap,sum(convert(numeric,ImporteDocumento)),operador,claveCont,asignacion,textoposicion,ref1,ref2,tipoDocumento,ref3,
				noIdentificado,adelantado,identificadorDelBanco,codigoBanco,transitoria,claseDeAporte,claseDeAportePrescripcion,tieneIntereses,tipoInteres,correccion,tipoMora,id,codigoGenesys,
				tipo,claseCuenta,usuario,regId,valorintmora,tarifa,apgOrigenAporte,operadorinformacion
		FROM (
		
				SELECT DISTINCT
					ROW_NUMBER() OVER(ORDER BY regId ASC) AS consecutivo,
					'' AS codigoSap, 
					--round(ISNULL(reg.regValTotalApoObligatorio, 0) + ISNULL(reg.regValorIntMora, 0),0) AS ImporteDocumento,
					round(ISNULL(apg.apgValTotalApoObligatorio, 0) + ISNULL(apg.apgValorIntMora, 0),0) AS ImporteDocumento,
					'' AS operador,			-- Solo aplica para la INTEGRACIoN DE CARTERA
					'40' AS claveCont,
					ISNULL(reg.regnumplanilla,'') AS asignacion,
					'' as textoposicion,
					reg.regNumeroIdentificacionAportante AS ref1,
					'' as ref2,
					mi.tipoDocumentoCaja AS tipoDocumento,
					LEFT(reg.regNombreAportante, 20) AS ref3,
					'=' AS noIdentificado,
					'=' AS adelantado,
					'=' AS identificadorDelBanco,
					'=' AS codigoBanco,
					'=' AS transitoria,
					'=' AS claseDeAporte,
					'=' AS claseDeAportePrescripcion,
					0 AS tieneIntereses,
					'=' AS tipoInteres,
					0 AS correccion,			-- Se define con los registros en la tabla Movimientoaporte en moaTipoAjuste = ''CORRECCION_A_LA_BAJA' va a ser anulacion.
					'' AS tipoMora,				-- Solo aplica para la INTEGRACIoN DE CARTERA
					0 AS id,
					NULL AS codigoGenesys,
					'=' AS tipo,
					'S' AS claseCuenta,
					'soportetecnico@asopagos.com' AS usuario,
					reg.regId,
					0 as valorIntMora,
					0 as tarifa,
					'' as apgOrigenAporte,
					reg.regOperadorInformacion AS operadorinformacion
				FROM #registrogeneralap2 reg
						INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) ON regId = apgRegistroGeneral
						INNER JOIN core.[sap].[MaestraIdentificacion] mi WITH(NOLOCK)ON reg.regTipoIdentificacionAportante = mi.tipoIdGenesys
						-- VERIFICA SI PROVIENTE DE UNA PLANILLA PILA (planilla) O UN APORTE MANUAL (regid)
				WHERE (reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @operadorinformacion) OR (reg.regid = @regId AND reg.regid <> 0 )) as S1

		Group by codigoSap,operador,claveCont,asignacion,textoposicion,ref1,ref2,tipoDocumento,ref3,
				noIdentificado,adelantado,identificadorDelBanco,codigoBanco,transitoria,claseDeAporte,claseDeAportePrescripcion,tieneIntereses,tipoInteres,correccion,tipoMora,id,codigoGenesys,
				tipo,claseCuenta,usuario,regId,valorintmora,tarifa,apgOrigenAporte,operadorinformacion

select * from #IC_Aportes_Det
	IF EXISTS (SELECT * FROM #IC_Aportes_Det) BEGIN		

		-- Calcula la asignacion para el caso de las planillas manuales donde no se guarda el #, envia el valor del # Operacion
		UPDATE iad

			SET iad.importeDocumento = CASE WHEN iad.importeDocumento = 0 THEN round(iad.importeDocumento + (ISNULL(apg.apgValorIntMora, 0)),0) ELSE round(iad.importeDocumento,0) END,


				iad.asignacion = CASE WHEN iad.asignacion = ''  THEN CONCAT('M', ISNULL(apg.apgId, '')) ELSE iad.asignacion END, 

					-- no identificados
				iad.noIdentificado = CASE WHEN apg.apgEstadoRegistroAporteAportante IN ('RELACIONADO','OTROS_INGRESOS') AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04 OR apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.01 or apd.apdTarifa = 0.03) THEN 'N1'
										  WHEN apg.apgEstadoRegistroAporteAportante IN ('RELACIONADO','OTROS_INGRESOS') AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'N1'   ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022   
										  WHEN apg.apgEstadoRegistroAporteAportante IN ('RELACIONADO','OTROS_INGRESOS') AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'N2'
										  WHEN apg.apgEstadoRegistroAporteAportante IN ('RELACIONADO','OTROS_INGRESOS') AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'N3'
										  WHEN apg.apgEstadoRegistroAporteAportante IN ('RELACIONADO','OTROS_INGRESOS') AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'N4'
										  WHEN apg.apgEstadoRegistroAporteAportante IN ('RELACIONADO','OTROS_INGRESOS') AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'N5'
										  ELSE '**' 
									 END,
					-- adelantados
				iad.adelantado = CASE WHEN apd.apdMarcaPeriodo = 'PERIODO_FUTURO' AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04 OR apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.01 or apd.apdTarifa = 0.03) THEN 'A1'
								   WHEN apd.apdMarcaPeriodo = 'PERIODO_FUTURO' AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'A1'---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
								   WHEN apd.apdMarcaPeriodo = 'PERIODO_FUTURO' AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'A2'   
								   WHEN apd.apdMarcaPeriodo = 'PERIODO_FUTURO' AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'A3'
								   WHEN apd.apdMarcaPeriodo = 'PERIODO_FUTURO' AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'A4'
								   WHEN apd.apdMarcaPeriodo = 'PERIODO_FUTURO' AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'A5'
								   ELSE '**' 
								 END,
				 
				iad.identificadorDelBanco = CASE WHEN (apg.apgNumeroCuenta is null or apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo in ('1','2','3','4') THEN REPLACE(REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', ''), '000000', '')
												 WHEN ( apg.apgNumeroCuenta is null or apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo = ('5') THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', ''))-- agregado posterior a gap vuenta bancaria 18/10/2022
												 WHEN apg.apgCodEntidadFinanciera = 7 THEN REPLACE(REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''), '000000', '')
												 WHEN apg.apgCodEntidadFinanciera = 51 THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''))
												 WHEN apg.apgCodEntidadFinanciera IS NULL  AND apg2.apgCodEntidadFinanciera = 7 THEN REPLACE(REPLACE(UPPER(ISNULL(apg2.apgNumeroCuenta, '')),'-', ''), '000000', '')
												 WHEN apg.apgCodEntidadFinanciera is null AND apg2.apgCodEntidadFinanciera = 51 THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(apg2.apgNumeroCuenta, '')),'-', '')) else apg.apgNumeroCuenta 
											END,  --Intento de formato de las cuentas bancarias.

				iad.codigoBanco = CASE WHEN (apg.apgNumeroCuenta is null or apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo is not null AND apg.apgCuentaBancariaRecaudo = 5 THEN ce.codigoEntidad --agregado con tabla de homologacion sap.maestracodigoentidad 31/10/2022
									   WHEN (apg.apgNumeroCuenta is null or apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo is not null  THEN CONCAT ( '0', CAST(ce.codigoEntidad as varchar(2)))
									   WHEN LEN(apg.apgCodEntidadFinanciera) = 1 THEN CONCAT('0', CAST(apg.apgCodEntidadFinanciera AS VARCHAR(2)))
									   WHEN apg.apgCodEntidadFinanciera is null AND LEN(apg2.apgCodEntidadFinanciera) = 1 THEN CONCAT('0', CAST(apg2.apgCodEntidadFinanciera AS VARCHAR(2))) ELSE CAST(apg.apgCodEntidadFinanciera AS VARCHAR(2)) 
								  END,

				iad.transitoria = CASE WHEN apg.apgModalidadRecaudoAporte = 'MANUAL' OR apg.apgModalidadRecaudoAporte = 'PILA' OR apg.apgModalidadRecaudoAporte = 'PILA_MANUAL' THEN '3' ELSE '9' END,

				iad.clasedeAporte = CASE WHEN apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04)  THEN 'CA1'
										 WHEN apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null)  THEN 'CA1' ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
										 WHEN apg.apgTipoSolicitante = 'EMPLEADOR' AND apd.apdTarifa < 0.04 THEN 'CA1'
										 WHEN apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'CA2'
										 WHEN apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'CA3'
										 WHEN apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'CA4'
										 WHEN apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'CA5'
										 ELSE '**'
									END,

				iad.claseDeAportePrescripcion = CASE WHEN apg.apgFormaReconocimientoAporte = 'RECONOCIMIENTO_RETROACTIVO_MANUAL' AND apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' THEN '1' ELSE '2' END,

				iad.tieneIntereses = CASE WHEN iad.tieneIntereses = 0 AND apg.apgValorIntMora = 0 THEN 0
										  WHEN iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0 THEN 1
										  ELSE iad.tieneIntereses
									 END,
						
				iad.tipoInteres = CASE 	-- Intereses no identificados
										WHEN (apg.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04 OR apd.apdTarifa = 0.01 OR apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.03 OR apd.apdTarifa = 0.06) THEN 'IN1'
										WHEN (apg.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'IN1'---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
										WHEN (apg.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.00 or apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'IN2'
										WHEN (apg.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'IN3'
										WHEN (apg.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'IN4'
										WHEN (apg.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'IN5'
										-- Intereses por mora
										WHEN apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04 OR apd.apdTarifa = 0.01 OR apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.03 OR apd.apdTarifa = 0.06) THEN 'IM1'
										WHEN apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'IM1' ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
										WHEN apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.00 or apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'IM2'
										WHEN apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'IM3'
										WHEN apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'IM4'
										WHEN apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'IM5'
										-- Rendimientos prescritos
										WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04 OR apd.apdTarifa = 0.01 OR apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.03 OR apd.apdTarifa = 0.06) THEN 'RP1'
										WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'RP1' ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
										WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.00 or apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'RP2'
										WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'RP3'
										WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'RP4'
										WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'RP5'
										ELSE '*'
								  END,

				iad.fechaRecaudo = apg.apgFechaRecaudo,

				iad.fechaProcesamiento = CASE WHEN [SAP].[calcularPrimerDiaHabilMes]() = [SAP].[calcularSiguienteDiaHabil]() AND DAY(DATEADD(HOUR,-5,GETDATE())) >= [SAP].[calcularPrimerDiaHabilMes]() AND DATEDIFF(DAY, apg.apgFechaRecaudo, DATEADD(HOUR,-5,GETDATE())) > 0
											  THEN EOMONTH(DATEADD(HOUR,-5,GETDATE()),-1)
											  ELSE DATEADD(HOUR,-5,GETDATE()) 
										   
										 END,

				iad.EstadoRegistroAporteAportante = apg.apgEstadoRegistroAporteAportante,

				iad.FormaReconocimientoAporte = apg.apgFormaReconocimientoAporte,

				iad.apgEstadoAportante = apg.apgEstadoAportante,

				iad.marcaPeriodo = apg.apgMarcaPeriodo,

				iad.tipoSolicitante = apg.apgtipoSolicitante,

				iad.valorIntMora = CASE WHEN iad.valorIntMora = 0 THEN apg.apgvalorIntMora 
										ELSE iad.valorIntMora 
								   END,
				iad.tarifa = Case When  apd.apdId is null AND apg.apgTipoSolicitante = 'EMPLEADOR'  then 0.04 Else apd.apdTarifa  End --- se agrega case when para el proceso cualdo es aporte a aportante y viene sin detalle.
				
				
		---------------------------------------------------------------------------
		FROM core.dbo.AporteGeneral apg WITH(NOLOCK)
			LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON  apg.apgId  = apd.apdAporteGeneral --- CAMBIO A LEFT CUANDO EL DOCUMENTO NO TRAE LOS DETALLES DE LA TRANSACCION EN AporteDetallado apd
			INNER JOIN #IC_Aportes_Det iad WITH(NOLOCK) ON  apg.apgRegistroGeneral = iad.regId
			LEFT JOIN core.dbo.AporteGeneral apg2 WITH(NOLOCK) ON iad.regIdorg = apg2.apgRegistroGeneral 
			LEFT JOIN core.dbo.AporteDetallado apd2 WITH(NOLOCK) ON  apg2.apgId = apd2.apdAporteGeneral
			LEFT JOIN core.dbo.CUENTAS_BANCARIAS cb WITH(NOLOCK) ON apg.apgCuentaBancariaRecaudo = cb.ID  -- agregado posterior a gap vuenta bancaria 18/10/2022
			LEFT JOIN core.sap.MaestracodigoEntidad ce WITH(NOLOCK) ON cb.ID = ce.idCuentasBancarias 
		
------------------------------------------------------------------------------------------
		
		UPDATE iad

			SET iad.codigoGenesys = per.perId,

				iad.tipo = CASE WHEN per.perTipoIdentificacion = 'NIT' THEN 'E'
								ELSE 'P' END
			FROM core.dbo.Persona per 
				INNER JOIN core.[sap].[MaestraIdentificacion] mi WITH(NOLOCK) ON per.perTipoIdentificacion = mi.tipoIdGenesys
				INNER JOIN #IC_Aportes_Det iad WITH(NOLOCK) ON  per.perNumeroIdentificacion = iad.ref1  AND mi.tipoDocumentoCaja = iad.tipoDocumento 


--------------------------------- Actualizacion temporal codigo entidad financiera en planillas mientras solucionan GLPI 68257 AHORA glpi 70078 29/06/2023 Camilo-----------------------------------------
/*
		IF (select TOP 1 codigoBanco from #IC_Aportes_Det where claveCont = '40') IS NULL
			
			BEGIN 
				Update  iad SET	iad.codigoBanco = CASE WHEN apg.apgCodEntidadFinanciera IS NULL AND apg.apgnumerocuenta = '00000000292647573' THEN '07'
											 			WHEN apg.apgCodEntidadFinanciera IS NULL AND apg.apgnumerocuenta = '265-99174-5' THEN '51'
											 			ELSE CAST(apg.apgCodEntidadFinanciera AS VARCHAR(2))
												  END
							FROM #IC_Aportes_Det iad 
									INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on iad.regId = apg.apgRegistroGeneral
									LEFT JOIN  core.dbo.CUENTAS_BANCARIAS cb WITH(NOLOCK) ON apg.apgCuentaBancariaRecaudo = cb.ID
									LEFT JOIN  core.sap.MaestracodigoEntidad ce WITH(NOLOCK) ON cb.ID = ce.idCuentasBancarias 
							WHERE iad.clavecont = '40'

				Update iad SET iad.identificadorDelBanco = CASE WHEN iad.codigoBanco IN('51') THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''))
																WHEN iad.codigoBanco IN('07')  THEN REPLACE(REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''), '000000', '')
																ELSE apg.apgNumeroCuenta 
														   END 
						   FROM #IC_Aportes_Det iad 
									Inner Join core.dbo.AporteGeneral  apg WITH(NOLOCK) on iad.regId = apg.apgRegistroGeneral
						   WHERE iad.clavecont = '40'
							
			END		
*/
		--Select * from #IC_Aportes_Det
-------------------------------------------------------------------------------------------------------------------------------------------------------		

	-- INSERCIoN DEL ENCABEZADO
		INSERT INTO #IC_Aportes_Enc(
			[fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[consecutivo],[observacion],
			[moneda],[documentoContable],[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario],[estadoaporteaportantetercero])

		SELECT DISTINCT
			CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
			CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
			ISNULL(fechaRecaudo, '') AS fechaDocumento,
			ISNULL(fechaProcesamiento, '') AS fechaContabilizacion,
			MONTH(fechaProcesamiento) AS periodo, 
			'' AS referencia, 
			
		
			CASE 
				--	Agregado Yesika Bernal, Validacion movimiento A03 Y A04
				WHEN ((SELECT TOP 1 d.asignacion FROM sap.IC_Aportes_Det d INNER JOIN sap.IC_Aportes_enc e WITH(NOLOCK) ON d.consecutivo = e.consecutivo 
																		   INNER JOIN #IC_Aportes_Det dt WITH(NOLOCK) ON d.asignacion = dt.asignacion
												 WHERE Tipomovimiento = 'A05') = (SELECT DISTINCT dt.asignacion FROM #IC_Aportes_Det dt))  AND 
																		  det.apgEstadoAportante <> 'ACTIVO' AND EstadoRegistroAporteAportante = 'REGISTRADO' AND marcaperiodo in ('PERIODO_REGULAR','PERIODO_RETROACTIVO') And valorIntMora = 0 THEN 'A03'
				WHEN ((SELECT TOP 1 d.asignacion FROM sap.IC_Aportes_Det d INNER JOIN sap.IC_Aportes_enc e WITH(NOLOCK) ON d.consecutivo = e.consecutivo 
																		   INNER JOIN #IC_Aportes_Det dt WITH(NOLOCK) ON d.asignacion = dt.asignacion
												 WHERE Tipomovimiento in ('A05','A06') ) = (SELECT DISTINCT dt.asignacion FROM #IC_Aportes_Det dt)) 
													   AND det.apgEstadoAportante <> 'ACTIVO' AND EstadoRegistroAporteAportante = 'REGISTRADO' AND marcaperiodo IN ('PERIODO_REGULAR','PERIODO_RETROACTIVO') And valorIntMora > 0 THEN 'A04'

				WHEN ((SELECT TOP 1 d.asignacion FROM sap.IC_Aportes_Det d INNER JOIN sap.IC_Aportes_enc e WITH(NOLOCK) ON d.consecutivo = e.consecutivo 
																		   INNER JOIN #IC_Aportes_Det dt WITH(NOLOCK) ON d.asignacion = dt.asignacion
												 WHERE Tipomovimiento in ('A34','A07')) = (SELECT DISTINCT dt.asignacion FROM #IC_Aportes_Det dt))  AND 
													   det.apgEstadoAportante = 'ACTIVO' AND EstadoRegistroAporteAportante = 'REGISTRADO' AND marcaperiodo = 'PERIODO_FUTURO' THEN 'A08'

				ELSE ISNULL(cc.Tipomovimiento, 'XXX') 
				END AS tipomovimiento,

			consecutivo AS consecutivo,
			ISNULL(EstadoRegistroAporteAportante,'') + ' / ' + ISNULL(FormaReconocimientoAporte, '') + ' / '+ ISNULL(det.apgEstadoAportante, '') + ' / ' + ISNULL(marcaperiodo, '') + ' / ' + CONVERT(VARCHAR, ISNULL(tarifa, 0)) + ' / ' + ISNULL(tiposolicitante, '') +' / '+ CASE WHEN ISNULL(valorIntMora,0) > 0 THEN '1' ELSE '0' END AS observacion,

			'COP' AS moneda, 
			'' AS documentoContable,
			'COMF' AS sociedad, 
			'' AS ejercicio, 
			'' AS nrointentos, 
			'' AS fecproceso, 
			'' AS horaproceso, 
			'P' AS estadoreg,
			usuario AS usuario,
			det.EstadoRegistroAporteAportante as estadoaporteaportantetercero

		FROM #IC_Aportes_Det det WITH(NOLOCK)
			LEFT JOIN core.sap.MaestraCondicionesCuentas cc WITH(NOLOCK) ON EstadoRegistroAporteAportante  = cc.apgEstadoRegistroAporteAportante
				AND ISNULL(FormaReconocimientoAporte, '') = ISNULL(cc.apgFormaReconocimientoAporte, '')
				AND det.apgEstadoAportante = cc.apgEstadoAportante
				AND marcaperiodo = cc.apgmarcaperiodo
				AND CONVERT(VARCHAR, Tarifa) = CONVERT(VARCHAR, cc.apdTarifa)
				AND tiposolicitante = cc.apgtiposolicitante 
				AND CASE WHEN ISNULL(ValorIntMora,0) > 0 THEN '1' ELSE '0' END = cc.tieneinteres
				AND det.claveCont = '40'
		------------------------------

		--------------------------------------------validacion de movimiento A07 previo --------------------------------------------------
				IF (SELECT tipomovimiento from #IC_Aportes_Enc) = 'A08'

					BEGIN
						IF NOT EXISTS (SELECT Distinct d.asignacion FROM sap.IC_Aportes_Det d inner join #IC_Aportes_Det iad ON d.asignacion = iad.asignacion inner join sap.IC_Aportes_Enc e on d.consecutivo = e.consecutivo where tipoMovimiento = 'A07' and estadoReg = 'S') 
							BEGIN	
								Update #IC_Aportes_Enc set tipoMovimiento = 'A01';
							END	
					END
		-----------------------------------------------------------------------------------------------------------------------------------
		
		IF (SELECT COUNT(1) FROM #IC_Aportes_Enc WITH(NOLOCK)) > 1 BEGIN
	
			--SELECT COUNT(1) AS 'Registros Encontrados'
			--FROM #IC_Aportes_Det

			DELETE #IC_Aportes_Det 
			WHERE consecutivo IN (
				SELECT consecutivo 
				FROM #IC_Aportes_Enc
				WHERE tipoMovimiento IN ('XXX', 'DEV')
			);

			DELETE #IC_Aportes_Enc 
			WHERE tipoMovimiento IN ('XXX', 'DEV');

		END --IF

		 --Sobre-escribe el valor del importe para los movimientos A04 Cuenta pasivo con intereses y A10 prescripcion con intereses
		UPDATE det 
		SET det.importeDocumento = round(det.importeDocumento - valorIntMora,0)
		FROM #IC_Aportes_Enc enc WITH(NOLOCK)
		INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A04','A10')
		
		-- Adicionalmente en el movimiento A03 y A04 calcula el campo de noIdentificado por la especifidad del movimiento
		UPDATE det
		SET det.noIdentificado = CASE WHEN enc.tipoMovimiento IN ('A03','A04') AND det.EstadoRegistroAporteAportante = 'REGISTRADO' AND det.TipoSolicitante = 'EMPLEADOR' AND (det.tarifa = 0.00 OR det.tarifa = 0.04 OR det.tarifa = 0.02 OR det.tarifa = 0.01 or det.tarifa = 0.03) THEN 'N1'
				WHEN enc.tipoMovimiento IN ('A03','A04') AND det.EstadoRegistroAporteAportante = 'REGISTRADO' AND det.TipoSolicitante = 'INDEPENDIENTE' AND (det.Tarifa = 0.02 OR det.Tarifa = 0.04) THEN 'N2'
				WHEN enc.tipoMovimiento IN ('A03','A04') AND det.EstadoRegistroAporteAportante = 'REGISTRADO' AND det.TipoSolicitante = 'PENSIONADO' AND det.Tarifa <> 0.006 THEN 'N3'
				WHEN enc.tipoMovimiento IN ('A03','A04') AND det.EstadoRegistroAporteAportante = 'REGISTRADO' AND det.TipoSolicitante = 'INDEPENDIENTE' AND (det.Tarifa = 0.006 OR det.Tarifa = 0.01) THEN 'N4'
				WHEN enc.tipoMovimiento IN ('A03','A04') AND det.EstadoRegistroAporteAportante = 'REGISTRADO' AND det.TipoSolicitante = 'PENSIONADO' AND det.Tarifa = 0.006 THEN 'N5'
				ELSE '**'
		END
		FROM #IC_Aportes_Enc enc WITH(NOLOCK)
		INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A03','A04')

		select * From #IC_Aportes_Det
	-- REGISTRO PARA COMPLETAR EL MOVIMIENTO CON LA CLAVE DE CONTABILIZACIoN DE ACUERDO AL TIPO DE MOVIMIENTO
	
	INSERT INTO #IC_Aportes_Det(
		[consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
		[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
		[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
		[claseCuenta],[usuario],regId,valorIntMora,tarifa
	)
	SELECT DISTINCT
		det.consecutivo,
		det.codigoSAP,
		-- Los movimientos A04 Cuenta pasivo y A10 prescripcion son atipicos en los intereses
		CASE WHEN enc.tipoMovimiento in ('A04','A10') THEN round(det.importeDocumento ,0)
			ELSE  round (ISNULL(det.importeDocumento, 0) - ISNULL(det.valorIntMora, 0),0) 
		END,
		det.operador,
		'50' AS claveCont,
		det.asignacion,
		det.textoposicion,
		det.ref1,
		det.ref2,
		det.tipoDocumento,
		det.ref3,
		det.noIdentificado,
		det.adelantado,
		det.identificadorDelBanco, 
		det.codigoBanco,
		det.transitoria,
		det.claseDeAporte,
		det.claseDeAportePrescripcion,
		det.tieneIntereses,
		
		det.tipoInteres,
		det.correccion,
		det.tipoMora,
		det.id,
		det.codigoGenesys,
		det.tipo,
		det.claseCuenta,
		det.usuario,
		det.regId,
		0, --valorIntMora
		0 --tarifa
	FROM #IC_Aportes_Det det WITH(NOLOCK) INNER JOIN #IC_Aportes_Enc enc WITH(NOLOCK) ON  det.consecutivo = enc.consecutivo 
	WHERE claveCont = '40'
	
	--Calcula la linea adicional si el registro tiene intereses
	UNION
		SELECT DISTINCT
		det.consecutivo,
		det.codigoSAP,
		round (ISNULL(det.valorintmora, 0),0),
		det.operador,
		'50' AS claveCont,
		det.asignacion,
		det.textoposicion,
		det.ref1,
		det.ref2,
		det.tipoDocumento,
		det.ref3,
		det.noIdentificado,
		det.adelantado,
		det.identificadorDelBanco, 
		det.codigoBanco,
		det.transitoria,
		det.claseDeAporte,
		det.claseDeAportePrescripcion,
		det.tieneIntereses,
		det.tipoInteres,
		det.correccion,
		det.tipoMora,
		det.id,
		det.codigoGenesys,
		det.tipo,
		det.claseCuenta,
		det.usuario,
		det.regId, 
		det.valorIntMora,
		0 --tarifa
	FROM #IC_Aportes_Det det WITH(NOLOCK) INNER JOIN #IC_Aportes_Enc enc WITH(NOLOCK) ON det.consecutivo = enc.consecutivo
	WHERE claveCont = '40'
	AND valorintmora > 0;

	INSERT INTO #IC_Aportes_Det(
		[consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
		[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
		[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
		[claseCuenta],[usuario],regId,valorIntMora
	)
	--Calcula la linea adicional si el registro tiene intereses
		SELECT DISTINCT
		det.consecutivo,
		det.codigoSAP,
		round (ISNULL(det.valorintmora, 0),0),
		det.operador,
		'40' AS claveCont,
		det.asignacion,
		det.textoposicion,
		det.ref1,
		det.ref2,
		det.tipoDocumento,
		det.ref3,
		det.noIdentificado,
		det.adelantado,
		det.identificadorDelBanco, 
		det.codigoBanco,
		det.transitoria,
		det.claseDeAporte,
		det.claseDeAportePrescripcion,
		det.tieneIntereses,
		det.tipoInteres,
		det.correccion,
		det.tipoMora,
		det.id,
		det.codigoGenesys,
		det.tipo,
		det.claseCuenta,
		det.usuario,
		det.regId, 
		det.valorIntMora
	FROM #IC_Aportes_Det det WITH(NOLOCK) INNER JOIN #IC_Aportes_Enc enc WITH(NOLOCK) ON  det.consecutivo = enc.consecutivo
	WHERE claveCont = '40'
	AND valorintmora > 0
	AND enc.tipoMovimiento IN ('A04','A10')


	--SELECT * FROM #IC_Aportes_Enc

	--------------Actualizacion campo texto posicion ----------------------------------------------------------------------------------------------------------------------------------
		if (select Distinct asignacion from #IC_Aportes_Det ) not like '%M%'
			BEGIN 
				Declare @origen  varchar(20)
				Select @origen = (select Distinct asignacion from #IC_Aportes_Det)

				select @Origen as origen

				declare @resultado  varchar (max)
				select @resultado = (select dbo.ASP_BuscarPlanillasN (@origen))

				if (select @resultado) is null
					begin
						Update #IC_Aportes_Det set textoPosicion = planillaN
						from aporteDetalladoRegistroControlN where asignacion not like '%M%' and planillaAntes = asignacion
					end
					Else 
						begin
						update  #IC_Aportes_Det set textoPosicion = (select dbo.ASP_BuscarPlanillasN (@origen)) where asignacion not like '%M%' and @origen = asignacion
						end 

			END
				----Select Distinct planillaAntes from aporteDetalladoRegistroControlN where planillaN = '65670464' --65470009
				--Update #IC_Aportes_Det set textoPosicion = planillaN
				--from aporteDetalladoRegistroControlN where asignacion not like '%M%' and asignacion = planillaAntes 

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		-- Blanquear datos de acuerdo al movimiento
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		--A01
		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = '',
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A01')
		AND claveCont = '40'

		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = '',
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A01')
		AND claveCont = '50'

			--A02
		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A02')
		AND claveCont = '40'

		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc WITH(NOLOCK) INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A02')
		AND claveCont = '50'
		AND valorIntMora = 0

		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 1,
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A02')
		AND claveCont = '50'
		AND valorIntMora > 0

		--A03
		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A03')
		AND claveCont = '50'
		
		UPDATE det
		SET --noIdentificado = 'NI',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
 		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A03')
		AND claveCont = '40'

			--A04
		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A04')
		AND claveCont = '50'
		AND valorIntMora = 0

		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 1,
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A04')
		AND claveCont = '50'
		AND valorIntMora > 0

		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		-- Calcula en el movimiento A04, sobre los interes en la clave 40 para los No Identificados.
		tipoInteres = CASE WHEN tipoInteres = 'IM1' THEN 'IN1' 
				WHEN tipoInteres = 'IM2' THEN 'IN2' 
				WHEN tipoInteres = 'IM3' THEN 'IN3'
				WHEN tipoInteres = 'IM4' THEN 'IN4'
				WHEN tipoInteres = 'IM5' THEN 'IN5'
				END,
		tieneIntereses = 1,
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A04')
		AND claveCont = '40'
		AND valorIntMora > 0
		AND fechaRecaudo IS NULL
		
		UPDATE det
		SET adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A04')
		AND claveCont = '40'
		AND valorIntMora > 0
		AND fechaRecaudo IS NOT NULL

		--A05
		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A05')
		AND claveCont = '40'
		
		UPDATE det
		SET --noIdentificado = 'NI',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det   ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A05')
		AND claveCont = '50'

		--A06
			
		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A06')
		AND claveCont = '40'
	
		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 1,
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A06')
		AND claveCont = '50'
		AND valorIntMora > 0

		UPDATE det
		SET --noIdentificado = 'NI',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A06')
		AND claveCont = '50'
		AND valorIntMora = 0


		--A07
		UPDATE det
		SET noIdentificado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A07')
		AND claveCont = '50'

		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A07')
		AND claveCont = '40'


		--A08
		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A08')
		AND claveCont = '50'

		UPDATE det
		SET noIdentificado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A08')
		AND claveCont = '40'

		--A09
		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A09')
		AND claveCont = '50'

		UPDATE det
		SET --noIdentificado = 'NI',
		adelantado = '',	
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A09')
		AND claveCont = '40'

		--A10
		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 1,
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A10')
		AND claveCont = '50'
		AND importeDocumento = valorIntMora

		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A10')
		AND claveCont = '50'
		AND importeDocumento <> valorIntMora

		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		-- Calcula en el movimiento A04, sobre los interes en la clave 40 para los No Identificados.
		tipoInteres = CASE WHEN tipoInteres = 'RP1' THEN 'IN1' 
				WHEN tipoInteres = 'RP2' THEN 'IN2' 
				WHEN tipoInteres = 'RP3' THEN 'IN3'
				WHEN tipoInteres = 'RP4' THEN 'IN4'
				WHEN tipoInteres = 'RP5' THEN 'IN5'
				END,
		tieneIntereses = 1,
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A10')
		AND claveCont = '40'
		AND valorIntMora > 0
		AND tarifa IS NULL

		UPDATE det
		SET adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipointeres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A10')
		AND claveCont = '40'
		AND tarifa IS NOT NULL

	

--SELECT * FROM #IC_Aportes_Enc

---- Actualizar el consecutivo definitivo en la tabla de integracion de encabezado con el detalle
		SELECT @consecutivo = ISNULL(MAX(consecutivo), 0) FROM sap.IC_Aportes_Enc
		--SELECT @consecutivodet = ISNULL( max(consecutivo),0) FROM sap.IC_Aportes_Det


		UPDATE #IC_Aportes_Det
		SET consecutivo = consecutivo + @consecutivo

		UPDATE #IC_Aportes_Enc
		SET consecutivo = consecutivo + @consecutivo

	select referencia  from #IC_Aportes_Enc

		-- Calcular el valor del campo referencia clase "AL" para movimientos de prescripcion (A09 y A10), los demas movimientos con la clase "TE"
		IF EXISTS ( SELECT * FROM #IC_Aportes_Enc WITH(NOLOCK) WHERE tipoMovimiento IN ('A09','A10','A36','A37')) BEGIN
			SELECT @comentario = 'P'
		END ELSE BEGIN
			SELECT @comentario = 'A'
		END
	

	print @comentario

		SELECT @referenciaNum = valorActual 
		FROM sap.ic_referencia WITH(NOLOCK)
		WHERE comentario = @comentario
		AND estado = 'A';

	print @referenciaNum


-------------------------------------------------------------------------------------------------------------------------------------------------------------------
				UPDATE #IC_Aportes_Enc
				SET referencia =  @referenciaNum


--comentarear

			IF (SELECT SUM(importeDocumento) FROM #IC_Aportes_Det)>0
				BEGIN 
  					UPDATE sap.ic_referencia
					SET valorActual = @referenciaNum + 1  --(SELECT COUNT (1) FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det ON enc.consecutivo = det.consecutivo WHERE det.importedocumento <> 0) -- Actualiza los numeros de referencia de acuerdo a la cantidad de encabezados.
					WHERE comentario = @comentario
					AND estado = 'A';
				END

	--END

---- fin comentario

--------------------------------------------------------------FIN--------------------------------------------------------------------------------

	--comentarear	



	IF ((SELECT referencia FROM #IC_Aportes_Enc ) IS NOT NULL )
	BEGIN
	

	--	PRINT 'SI FUNCIONA'
		----------------
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		-- INSERCION DE REGISTROS EN LAS TABLAS DE INTEGRACIoN
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
	IF EXISTS (SELECT enca.consecutivo FROM #IC_Aportes_Enc enca INNER JOIN #IC_Aportes_Det deta ON enca.consecutivo = deta.consecutivo WHERE deta.importeDocumento <> 0)
	BEGIN 


		INSERT INTO core.sap.IC_Aportes_Enc(
			[fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[observacion],
			[moneda],[documentoContable],[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario])
		SELECT distinct [fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[observacion],
			[moneda],[documentoContable],[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario]
		FROM #IC_Aportes_Enc WITH(NOLOCK) ; 

			-- Actualizar el consecutivo definitivo en la tabla de integracion de encabezado con el detalle
				SELECT @consecutivo=MAX(consecutivo) FROM IC_Aportes_Enc

				UPDATE #IC_Aportes_Det
				SET consecutivo = @consecutivo

		--IF EXISTS (SELECT * FROM SAP.IC_Aportes_Enc enca INNER JOIN #IC_Aportes_Det deta ON enca.consecutivo = deta.consecutivo)
		--BEGIN
		INSERT INTO core.sap.IC_Aportes_Det([consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
				[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
				[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
				[claseCuenta],[usuario],[fechaEjecucion])
		SELECT [consecutivo],[codigoSap],CAST(CAST([importeDocumento] AS BIGINT) AS VARCHAR(18)),[operador],[claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
				[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
				[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
				[claseCuenta],[usuario],GETDATE() -'05:00:00'
        FROM #IC_Aportes_Det  WITH(NOLOCK)
		WHERE importeDocumento <> 0  ---  para cuando el registro viene sin detalle y que no traiga lineas en valores 0 08/10/2022
		--END

		UPDATE  #IC_Aportes_Det
				SET consecutivo =(SELECT consecutivo FROM #IC_Aportes_Enc)
		
		-- Insercion en la tabla de control de planillas
	
		INSERT INTO core.sap.PlanillasCtrl
		SELECT DISTINCT LEFT(det.asignacion, 12), 1, det.ref1, det.tipoDocumento, SAP.GetLocalDate(), 'APORTES', det.ref3 + enc.observacion, enc.tipoMovimiento,estadoaporteaportantetercero,operadorinformacion,1 
		FROM #IC_Aportes_Det det WITH(NOLOCK)
		INNER JOIN #IC_Aportes_Enc enc WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE det.EstadoRegistroAporteAportante IS NOT NULL

		
	END
	 
END


-- Fin 


		--Detalle
		SELECT DISTINCT * FROM #IC_Aportes_Enc
		ORDER BY tipoMovimiento, consecutivo

		SELECT * FROM #IC_Aportes_Det WITH(NOLOCK)
		WHERE importeDocumento <> 0  ---  para cuando el registro viene sin detalle y que no traiga lineas en valores 0 08/10/2022
		ORDER BY consecutivo
				 
		DROP TABLE #IC_Aportes_Enc;
		DROP TABLE #IC_Aportes_Det;
		DROP TABLE #IC_Aportes_Det_Swap;
	
	END --IF
	
	COMMIT TRANSACTION
END TRY  

BEGIN CATCH

	ROLLBACK TRANSACTION

	SELECT  ERROR_NUMBER() AS ErrorNumber  ,ERROR_SEVERITY() AS ErrorSeverity  
    ,ERROR_STATE() AS ErrorState,ERROR_PROCEDURE() AS ErrorProcedure  
    ,ERROR_LINE() AS ErrorLine,ERROR_MESSAGE() AS ErrorMessage
	,GETDATE() -'05:00:00' as Fechaing;  
 
 INSERT INTO core.sap.LogErrorAportes 
 SELECT @planilla,'',@regId,@solNumeroRadicacion,@solTipoTransaccion,@operadorinformacion, ERROR_NUMBER(),ERROR_SEVERITY(),ERROR_STATE(),ERROR_PROCEDURE(),ERROR_LINE(),ERROR_MESSAGE(),GETDATE() -'05:00:00'
 

END CATCH

	DELETE FROM sap.ejecucion_aportes2
	END
	ELSE BEGIN
		SELECT 'El proceso ya se encuentra en ejecucion'
	END
	


END