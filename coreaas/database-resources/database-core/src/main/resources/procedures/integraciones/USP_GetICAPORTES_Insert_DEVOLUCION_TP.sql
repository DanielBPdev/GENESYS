CREATE OR ALTER      PROCEDURE [sap].[USP_GetICAPORTES_Insert_DEVOLUCION_TP] 	 @planilla VARCHAR(20), @regid BIGINT, @apgid BIGINT,@solNumeroRadicacion VARCHAR (30),	
	@solTipoTransaccion VARCHAR (30),	@operadorinformacion INT
AS
BEGIN
SET NOCOUNT ON; 



DECLARE @referenciaNum BIGINT, @consecutivo BIGINT, @comentario VARCHAR(2),@DevolucionT BIT
DECLARE @numeroDocumento VARCHAR(50), @tipoDocumento VARCHAR(20), @usuario VARCHAR(30) = 'soportetecnico@asopagos.com'

	DROP TABLE IF EXISTS  #registrogeneraldv
	SELECT regid, regNumPlanilla, regNombreAportante, regValorIntMora,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,regOperadorInformacion
	INTO #registrogeneraldv  
	FROM core.pila.RegistroGeneral	
	Where regDateTimeInsert IS NULL OR regDateTimeInsert >= dateadd(year,-5,getdate())

	--SET @solNumeroRadicacion ='032024510968' 		
	--SET @solTipoTransaccion  ='DEVOLUCION_APORTES'
	--SET @apgid = '2950682'

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
		[ref3] [varchar](50) NOT NULL,
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
		devolucionCCF INT NULL,
		regIdorg [bigint] NULL,
		docorg [varchar](12) NULL,
		[claseDeAporteorg] [varchar](3) NULL,
		tipoInteresOrg [varchar] (3) Null,
		operadorinformacion int Null,
		[noIdentificadoOri] [varchar](2),
		ValorAporte  NUMERIC(21,5) NOT NULL
			
	)

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




BEGIN TRY 
	BEGIN TRANSACTION

	
	-- DETALLE: reconoce los registros de DEVOLUCION
	----------------------------------------------------------INSERT DEVOLUCION-------------------------------------------------------------------------
	INSERT INTO #IC_Aportes_Det(
		[consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[textoposicion],[ref1],ref2,[tipoDocumento],[ref3],
		[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
		[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
		[claseCuenta],[usuario],regId,valorintmora,tarifa,apgOrigenAporte,devolucionCCF, EstadoRegistroAporteAportante,
		TipoSolicitante,operadorinformacion,ValorAporte
	)
	SELECT 
ROW_NUMBER() OVER(ORDER BY regId ASC) AS consecutivo,codigoSap,Round(SUM(ImporteDocumento),2) AS ImporteDocumento,operador,claveCont,asignacion,textoposicion,ref1,ref2,tipoDocumento,ref3,noIdentificado,adelantado
,identificadorDelBanco,codigoBanco,transitoria,claseDeAporte,claseDeAportePrescripcion,tieneIntereses,tipoInteres,correccion
,tipoMora,id,codigoGenesys,tipo,claseCuenta,usuario,regId,SUM(ROUND(valorIntMora,3)) valorIntMora,tarifa,apgOrigenAporte,devolucionCCF,EstadoRegistroAporteAportante,TipoSolicitante,operadorinformacion,sum(convert(numeric,ValorAporte))
FROM (
SELECT  
		ROW_NUMBER() OVER(ORDER BY regId ASC) AS consecutivo,
		'' AS codigoSap, 
		ISNULL(moa.moaValorAporte, 0) + ISNULL(moa.moaValorInteres, 0) AS ImporteDocumento,
		'' AS operador,			-- Solo aplica para la INTEGRACIoN DE CARTERA
		'40' AS claveCont,
		ISNULL(reg.regnumplanilla,'') AS asignacion,
		'' as textoposicion,
		-- Calcula si la devolucion es a una Caja de Compensacion
		per.perNumeroIdentificacion AS ref1,
		@apgid as ref2,
		mi.tipoDocumentoCaja AS tipoDocumento,
		LEFT(per.perRazonSocial, 20) AS ref3,
		'=' AS noIdentificado,
		'=' AS adelantado,
		'=' AS identificadorDelBanco,
		'=' AS codigoBanco,
		'=' AS transitoria,
		'=' AS claseDeAporte,
		'=' AS claseDeAportePrescripcion,
		--CASE WHEN moa.moaValorInteres = 0 THEN 0 
		--		WHEN moa.moaValorInteres > 0 THEN 1 END AS tieneIntereses,
		'' AS tieneIntereses,
		'=' AS tipoInteres,
		0 AS correccion,			-- Se define con los registros en la tabla Movimientoaporte en moaTipoAjuste = ''CORRECCION_A_LA_BAJA' va a ser anulacion.
		'' AS tipoMora,				-- Solo aplica para la INTEGRACIoN DE CARTERA
		0 AS id,
		NULL AS codigoGenesys,
		'=' AS tipo,
		'S' AS claseCuenta,
		'soportetecnico@asopagos.com' AS usuario,
		reg.regId,
		CONVERT(NUMERIC(21,5),ISNULL(moa.moaValorInteres, 0)) as valorIntMora,
		0 as tarifa,
		'' as apgOrigenAporte,
		dap.dapCajaCompensacion	as devolucionCCF,
		apg.apgEstadoRegistroAporteAportante as EstadoRegistroAporteAportante,
		apgTipoSolicitante AS TipoSolicitante,
		reg.regoperadorinformacion as operadorinformacion,
		moaValorAporte AS ValorAporte
		FROM core.dbo.SolicitudDevolucionAporte sda WITH(NOLOCK) 
			INNER JOIN core.dbo.DevolucionAporte dap WITH(NOLOCK) ON sda.sdaDevolucionAporte = dap.dapid
			INNER JOIN core.dbo.DevolucionAporteDetalle dad WITH(NOLOCK) ON dap.dapid = dad.dadDevolucionAporte
			INNER JOIN core.dbo.MovimientoAporte moa WITH(NOLOCK) ON dad.dadMovimientoAporte = moa.moaid
			INNER JOIN core.dbo.AporteGeneral apg WITH(NOLOCK) ON moa.moaAporteGeneral = apg.apgid
			INNER JOIN core.dbo.Solicitud sol WITH(NOLOCK) ON sda.sdaSolicitudGlobal = sol.solId
			INNER JOIN #registrogeneraldv reg ON apg.apgRegistroGeneral = reg.regId 
			INNER JOIN core.dbo.Persona per WITH(NOLOCK) ON sda.sdaPersona = per.perId
			INNER JOIN core.[sap].[MaestraIdentificacion] mi WITH(NOLOCK) ON per.perTipoIdentificacion = mi.tipoIdGenesys
			WHERE YEAR (sol.solFechaRadicacion) > 2010 -- verificar desde que ano se activan las opciones.
			AND sol.solTipoTransaccion = @solTipoTransaccion--'DEVOLUCION_APORTES'--@solTipoTransaccion
			AND sol.solNumeroRadicacion = @solNumeroRadicacion --'032023139221'--@solNumeroRadicacion
			AND apgId = @apgid
			--AND sol.solnumeroradicacion NOT IN (SELECT solNumeroRadicacion FROM sap.ContablesCtrl WITH (NOLOCK))
			AND (len(reg.regnumplanilla) <= 10 or regNumPlanilla is null)) as x
GROUP BY codigoSap,operador,claveCont,asignacion,textoposicion,ref1,ref2,tipoDocumento,ref3,noIdentificado,adelantado
,identificadorDelBanco,codigoBanco,transitoria,claseDeAporte,claseDeAportePrescripcion,tieneIntereses,tipoInteres,correccion
,tipoMora,id,codigoGenesys,tipo,claseCuenta,usuario,regId,tarifa,apgOrigenAporte,devolucionCCF,EstadoRegistroAporteAportante,TipoSolicitante,operadorinformacion ;



	
	DELETE #IC_Aportes_Det WHERE importeDocumento = 0 	
	--Select * from #IC_Aportes_Det
		
	IF EXISTS (SELECT * FROM #IC_Aportes_Det) 
		BEGIN


---------------------------------------------Actualizacion de campos del detalle---------------------------------------------------------

			UPDATE iad SET 
			
				iad.importeDocumento = CASE WHEN iad.importeDocumento = 0 THEN iad.importeDocumento + (ISNULL(apg.apgValorIntMora, 0))
										ELSE iad.importeDocumento
										END,

				iad.asignacion = CASE WHEN iad.asignacion = ''  THEN CONCAT('M', ISNULL(apg.apgId, '')) ELSE iad.asignacion END, 

			--	iad.ref2 = CASE WHEN @solTipoTransaccion = 'CORRECCION_APORTES' then CONCAT('M', ISNULL(apg.apgId, ''))else ' ' END, --- agregado 16/02/2023 para correcciones manuales

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
				
				iad.clasedeAporte = CASE WHEN apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04)  THEN 'CA1'
											WHEN apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null)  THEN 'CA1' ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
											WHEN apg.apgTipoSolicitante = 'EMPLEADOR' AND apd.apdTarifa < 0.04 THEN 'CA1'
											WHEN apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'CA2'
											WHEN apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'CA3'
											WHEN apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'CA4'
											WHEN apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'CA5'
											ELSE '**'
											END,					
				iad.claseDeAportePrescripcion = CASE WHEN apg.apgFormaReconocimientoAporte = 'RECONOCIMIENTO_RETROACTIVO_MANUAL' AND apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' THEN '1' 
												ELSE '2' 
												END,

				iad.tieneIntereses = CASE WHEN iad.tieneIntereses = 0 AND apg.apgValorIntMora = 0 THEN 0
										WHEN iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0 THEN 1
										ELSE iad.tieneIntereses
										END,
						
				iad.tipoInteres = CASE
						  ----Devolucion tercero pagador
									WHEN @solTipoTransaccion IN('DEVOLUCION_APORTES','DEVOLUCION_APORTES_MASIVA') AND iad.EstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR iad.valorIntMora > 0) AND iad.tipoSolicitante = 'EMPLEADOR' AND  (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04 OR apd.apdTarifa = 0.01 OR apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.03 OR apd.apdTarifa = 0.06) THEN 'IM1'
									WHEN @solTipoTransaccion IN('DEVOLUCION_APORTES','DEVOLUCION_APORTES_MASIVA') AND iad.EstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND iad.TipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'IM1' 
									WHEN @solTipoTransaccion IN('DEVOLUCION_APORTES','DEVOLUCION_APORTES_MASIVA') AND iad.EstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR iad.valorIntMora > 0) AND iad.TipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.00 or apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'IM2'
									WHEN @solTipoTransaccion IN('DEVOLUCION_APORTES','DEVOLUCION_APORTES_MASIVA') AND iad.EstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND iad.TipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'IM3'
									WHEN @solTipoTransaccion IN('DEVOLUCION_APORTES','DEVOLUCION_APORTES_MASIVA') AND iad.EstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND iad.TipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'IM4'
									WHEN @solTipoTransaccion IN('DEVOLUCION_APORTES','DEVOLUCION_APORTES_MASIVA') AND iad.EstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND iad.TipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'IM5'
		
				        ---- Intereses no identificados
									WHEN (apg.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04 OR apd.apdTarifa = 0.01 OR apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.03 OR apd.apdTarifa = 0.06) THEN 'IN1'
									WHEN (apg.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'IN1'---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
									WHEN (apg.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.00 or apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'IN2'
									WHEN (apg.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'IN3'
									WHEN (apg.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'IN4'
									WHEN (apg.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'IN5'
						---- Intereses por mora
									WHEN apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04 OR apd.apdTarifa = 0.01 OR apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.03 OR apd.apdTarifa = 0.06) THEN 'IM1'
									WHEN apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'IM1' ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
									WHEN apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.00 or apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'IM2'
									WHEN apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'IM3'
									WHEN apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'IM4'
									WHEN apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'IM5'
						---- Rendimientos prescritos
									WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04 OR apd.apdTarifa = 0.01 OR apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.03 OR apd.apdTarifa = 0.06) THEN 'RP1'
									WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'RP1' ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
									WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.00 or apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'RP2'
									WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'RP3'
									WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'RP4'
									WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'RP5'
									ELSE '*'
								END,

				iad.fechaRecaudo = apg.apgFechaRecaudo ,

				iad.fechaProcesamiento = CASE WHEN [SAP].[calcularPrimerDiaHabilMes]() = [SAP].[calcularSiguienteDiaHabil]() AND DAY(DATEADD(HOUR,-5,GETDATE())) >= [SAP].[calcularPrimerDiaHabilMes]() AND DATEDIFF(DAY, apg.apgFechaRecaudo, DATEADD(HOUR,-5,GETDATE())) > 0
											THEN EOMONTH(DATEADD(HOUR,-5,GETDATE()),-1)
											ELSE DATEADD(HOUR,-5,GETDATE()) END,
				iad.EstadoRegistroAporteAportante =  CASE WHEN @solTipoTransaccion IN('DEVOLUCION_APORTES','DEVOLUCION_APORTES_MASIVA')  THEN iad.EstadoRegistroAporteAportante ELSE  apg.apgEstadoRegistroAporteAportante END,
				
				iad.FormaReconocimientoAporte = apg.apgFormaReconocimientoAporte,

				iad.apgEstadoAportante = (Select Estadoafi.roaEstadoAfiliado from (select top 1 MAX (roaFechaAfiliacion) as fechaafiliacion, apgEstadoAportante,apdEstadoCotizante,roaEstadoAfiliado,roaEstadoEnEntidadPagadora ,perNumeroIdentificacion 
																					from AporteGeneral 
																					inner join AporteDetallado on apgid = apdAporteGeneral
																					inner join persona on apgPersona = perid 
																					inner join Afiliado on perid = afiPersona
																					inner join RolAfiliado on afiid = roaAfiliado
																					where apgId = @apgid --and perId = '2228667'
																					and apgTipoSolicitante = roaTipoAfiliado
																					group by apgEstadoAportante,apdEstadoCotizante,roaEstadoAfiliado,roaEstadoEnEntidadPagadora,roaFechaAfiliacion,perNumeroIdentificacion
																					order by roaFechaAfiliacion desc) Estadoafi),

				iad.marcaPeriodo = apg.apgMarcaPeriodo,
				iad.tipoSolicitante = apg.apgtipoSolicitante,
				iad.valorIntMora = CASE WHEN iad.valorIntMora = 0 THEN apg.apgvalorIntMora 
									ELSE iad.valorIntMora END,
				iad.tarifa = CASE WHEN  apd.apdId is null AND apg.apgTipoSolicitante = 'EMPLEADOR'  then 0.04 Else apd.apdTarifa  End-----  Este campo es obligatorio, si viene en 0 o null no realiza el proceso para el tipo de aporte. (revisar con robinson)
				--- se agrega case when para el proceso cualdo es aporte a aportante y viene sin detalle.


				FROM core.dbo.AporteGeneral apg WITH(NOLOCK)
					LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON  apg.apgId  = apd.apdAporteGeneral --- CAMBIO A LEFT CUANDO EL DOCUMENTO NO TRAE LOS DETALLES DE LA TRANSACCION EN AporteDetallado apd
					INNER JOIN #IC_Aportes_Det iad WITH(NOLOCK) ON  apg.apgId = @apgid
					LEFT JOIN core.dbo.AporteGeneral apg2 WITH(NOLOCK) ON iad.regIdorg = apg2.apgRegistroGeneral 
					LEFT JOIN core.dbo.AporteDetallado apd2 WITH(NOLOCK) ON  apg2.apgId = apd2.apdAporteGeneral
					LEFT JOIN core.dbo.CUENTAS_BANCARIAS cb WITH(NOLOCK) ON apg.apgCuentaBancariaRecaudo = cb.ID  -- agregado posterior a gap vuenta bancaria 18/10/2022
					LEFT JOIN core.sap.MaestracodigoEntidad ce WITH(NOLOCK) ON cb.ID = ce.idCuentasBancarias

--select ref2 from #IC_Aportes_Det
--------------- Actualizacion Estado Aportante en caso de estar Vacio en Afiliado----------------------------------

update iad set iad.apgEstadoAportante = CASE WHEN iad.apgEstadoAportante = '' or iad.apgEstadoAportante is null THEN apg.apgEstadoAportante
		ElSe iad.apgEstadoAportante
		End
from #IC_Aportes_Det iad inner join AporteGeneral apg on iad.ref2 = apg.apgId
		
-------------------------------------------------Insert Linea 31 ---------------------------------------------------------------			
			--select * from #IC_Aportes_Det
			INSERT INTO #IC_Aportes_Det 
				Select 	[consecutivo],[codigoSap],[importeDocumento],[operador],'31' AS [claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
						[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
						[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
						[claseCuenta],[usuario],regId,fechaRecaudo,fechaProcesamiento,EstadoRegistroAporteAportante,FormaReconocimientoAporte,apgEstadoAportante,
						marcaPeriodo,tipoSolicitante,valorIntMora,tarifa,apgOrigenAporte,devolucionCCF,regIdorg,docorg,[claseDeAporteorg],tipoInteresOrg,operadorinformacion,[noIdentificadoOri],ValorAporte
						
				FROM #IC_Aportes_Det  det											 

			UPDATE #IC_Aportes_Det SET	codigoGenesys = CASE WHEN apg.apgPersona is null THEN p.perId ELSE apg.apgPersona END ,	
										tipo = CASE WHEN tipoDocumento = 'NIT' THEN 'E'	ELSE 'P' END								

				FROM dbo.AporteGeneral  apg
				LEFT JOIN empresa e on apg.apgEmpresa = e.empId
				LEFT JOIN Persona p on e.empPersona = perid
				WHERE apg.apgId = @apgid


---------------------Actualizacion de importe cuando es empleador y eliminado de lineas extra ------------------------------
 --Select * from #IC_Aportes_Det

 --Select sum(convert(numeric,importeDocumento)) from #IC_Aportes_Det where claveCont = '40'

 If (Select Distinct tipoSolicitante from #IC_Aportes_Det ) = 'EMPLEADOR'
	Begin 
		
		update #IC_Aportes_Det set importeDocumento = (Select sum(importeDocumento) from #IC_Aportes_Det where claveCont = '40')

		Delete from #IC_Aportes_Det where consecutivo >1


	END
 


  ---------------------------------------------------------------------------------------------

 
  ---------------------------------------------------------------------------------------------
  --Validacion existe Mov base en integracion contable
  
  IF NOT EXISTS
	( SELECT DISTINCT CC.Tipomovimiento as [Tipo movimiento maestra], enc.tipoMovimiento FROM  #IC_Aportes_Det	det
	 INNER JOIN  core.sap.MaestraCondicionesCuentas CC WITH(NOLOCK) ON EstadoRegistroAporteAportante  = cc.apgEstadoRegistroAporteAportante
				AND ISNULL(FormaReconocimientoAporte, '') = ISNULL(cc.apgFormaReconocimientoAporte, '')
				AND det.apgEstadoAportante = cc.apgEstadoAportante
				AND marcaperiodo = cc.apgmarcaperiodo
				AND CONVERT(VARCHAR, Tarifa) = CONVERT(VARCHAR, cc.apdTarifa)
				AND tiposolicitante = cc.apgtiposolicitante 
				AND CASE WHEN ISNULL(ValorIntMora,0) > 0 THEN '1' ELSE '0' END = cc.tieneinteres
				AND det.claveCont = '40'
	INNER JOIN core.sap.IC_Aportes_Det di WITH(NOLOCK) ON det.asignacion = di.asignacion
	INNER JOIN core.sap.IC_Aportes_Enc enc WITH(NOLOCK) ON di.consecutivo = enc.consecutivo
	WHERE CC.Tipomovimiento = enc.tipoMovimiento)

	BEGIN
	  DELETE #IC_Aportes_Det
	END
	
 ---------------------------------------------------------------------------------------------------------------------------

--Select det.apgEstadoAportante from #IC_Aportes_Det det
		------------------- inicio Encabezado ---------------------------------------
	
		INSERT INTO #IC_Aportes_Enc(
			[fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[consecutivo],[observacion],
			[moneda],[documentoContable],[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario],[estadoaporteaportantetercero]
		)
		SELECT DISTINCT top 1
			CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
			CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
			ISNULL(fechaRecaudo, '') AS fechaDocumento,
			ISNULL(fechaProcesamiento, '') AS fechaContabilizacion,
			MONTH(fechaProcesamiento) AS periodo, 
			'' AS referencia, 
			/*
			IMPORTANTE: se incluyen los movimientos relacionados con las DEVOLUCIONES, cuando la consulta llega con el valor del numero de radicacion
			Devoluciones:
			A01 -> A19 sin intereses
			A08 -> A19 sin intereses
			A02 -> A15 o A20 con intereses
			A05 -> A03 o A16 sin intereses
			A06 -> A04 o A17 con intereses			
			A04 -> A15 o A20 
			A07 -> A08 o A21
			A34 -> A21
			*/
			CASE 
				-- DEVOLUCION_APORTES
				WHEN @solTipoTransaccion IN('DEVOLUCION_APORTES','DEVOLUCION_APORTES_MASIVA') AND cc.Tipomovimiento IN ('A02', 'A04') AND det.importeDocumento > det.valorIntMora THEN 'A20' 
				WHEN @solTipoTransaccion IN('DEVOLUCION_APORTES','DEVOLUCION_APORTES_MASIVA') AND cc.Tipomovimiento IN ('A01','A02','A03','A04','A05','A06','A07','A08','A09','A10') AND det.importeDocumento = ROUND(det.valorIntMora,0) THEN 'A15' 
				WHEN @solTipoTransaccion IN('DEVOLUCION_APORTES','DEVOLUCION_APORTES_MASIVA') AND cc.Tipomovimiento IN ('A01', 'A08') THEN 'A19'
				WHEN @solTipoTransaccion IN('DEVOLUCION_APORTES','DEVOLUCION_APORTES_MASIVA') AND cc.Tipomovimiento = 'A05' THEN 'A16'
				WHEN @solTipoTransaccion IN('DEVOLUCION_APORTES','DEVOLUCION_APORTES_MASIVA') AND cc.Tipomovimiento = 'A06' AND det.importeDocumento > det.valorIntMora THEN 'A17'
				WHEN @solTipoTransaccion IN('DEVOLUCION_APORTES','DEVOLUCION_APORTES_MASIVA') AND cc.Tipomovimiento IN('A07','A34') THEN 'A21' 
				WHEN @solTipoTransaccion IN('DEVOLUCION_APORTES','DEVOLUCION_APORTES_MASIVA') AND cc.Tipomovimiento = 'A09' THEN 'A36' 
				WHEN @solTipoTransaccion IN('DEVOLUCION_APORTES','DEVOLUCION_APORTES_MASIVA') AND cc.Tipomovimiento = 'A10' THEN 'A37' 
				WHEN @solTipoTransaccion = '' THEN ISNULL(cc.Tipomovimiento, 'XXX') -- movimientos definidos en la tabla maestra, que no son devoluciones ni correcciones
				ELSE ''---ISNULL(cc.Tipomovimiento, 'DEV')
				END AS tipomovimiento,

			consecutivo AS consecutivo,
			CASE WHEN MONTH(fechaRecaudo) = MONTH(ctr.fechaIngreso) AND CTR.Tipomovimiento = 'A07' AND cc.Tipomovimiento = 'A01' AND @solNumeroRadicacion <> '' AND @solTipoTransaccion = 'ACTIVO'
					THEN  CONCAT(cc.apgEstadoRegistroAporteAportante,' / ',cc.apgFormaReconocimientoAporte,' / ',cc.apgEstadoAportante,' / ',cc.apgmarcaperiodo,' / ',cc.apdTarifa,' / ',cc.apgTipoSolicitante,' / ',cc.tieneInteres)
			     WHEN @solTipoTransaccion = 'ACTIVO' THEN ISNULL(EstadoRegistroAporteAportante,'') + ' / ' + ISNULL(FormaReconocimientoAporte, '') + ' / '+ ISNULL(@solTipoTransaccion, '') + ' / ' + ISNULL(marcaperiodo, '') + ' / ' + CONVERT(VARCHAR, ISNULL(tarifa, 0)) + ' / ' + ISNULL(tiposolicitante, '') +' / '+ CASE WHEN ISNULL(valorIntMora,0) > 0 THEN '1' ELSE '0' END
				 ELSE ISNULL(EstadoRegistroAporteAportante,'') + ' / ' + ISNULL(FormaReconocimientoAporte, '') + ' / '+ ISNULL(det.apgEstadoAportante, '') + ' / ' + ISNULL(marcaperiodo, '') + ' / ' + CONVERT(VARCHAR, ISNULL(tarifa, 0)) + ' / ' + ISNULL(tiposolicitante, '') +' / '+ CASE WHEN ISNULL(valorIntMora,0) > 0 THEN '1' ELSE '0' END END AS observacion,
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
			LEFT JOIN core.SAP.PlanillasCtrl ctr WITH(NOLOCK) ON det.ref1 = ctr.regNumeroIdentificacionAportante AND det.tipoDocumento = ctr.regTipoIdentificacionAportante AND CTR.Tipomovimiento = 'A07'
			LEFT JOIN SAP.PlanillasCtrl ctr2 WITH(NOLOCK) ON det.ref1 = ctr2.regNumeroIdentificacionAportante and det.tipoDocumento = ctr2.regTipoIdentificacionAportante and CTR2.Tipomovimiento in ('A05','A06')
			
		-------------------------------------------------------

		-- Revisa la cantidad de registros asociados a la planilla y si encuentra un movimiento adicional tipoMovimiento = 'XXX' 
		-- lo elimina y detecta las devoluciones y las correcciones a traves de las novedades.
		-- Elimina los movimientos AX1.

--------------------------------------------------- Insert Linea 40 para mov con intereses----------------
INSERT INTO #IC_Aportes_Det(
		[consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
		[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
		[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
		[claseCuenta],[usuario],regId,valorIntMora,devolucionCCF,operadorinformacion,ValorAporte
	)
	--Calcula la linea adicional si el registro tiene intereses
		SELECT DISTINCT
		det.consecutivo,det.codigoSAP,ISNULL(det.valorintmora, 0),det.operador,'40' AS claveCont,det.asignacion,det.textoposicion,det.ref1,det.ref2,
		det.tipoDocumento,det.ref3,det.noIdentificado,det.adelantado,det.identificadorDelBanco, det.codigoBanco,det.transitoria,det.claseDeAporte,det.claseDeAportePrescripcion,
		det.tieneIntereses,det.tipoInteres,det.correccion,det.tipoMora,det.id,det.codigoGenesys,det.tipo,det.claseCuenta,det.usuario,det.regId,det.valorIntMora,
		det.devolucionCCF,det.operadorinformacion,ValorAporte
	FROM #IC_Aportes_Det det WITH(NOLOCK) INNER JOIN #IC_Aportes_Enc enc WITH(NOLOCK) ON  det.consecutivo = enc.consecutivo
	WHERE claveCont = '40'
	AND valorintmora > 0
	AND enc.tipoMovimiento IN ('A20','A17','A37')


	Update #IC_Aportes_Det set importeDocumento = ValorAporte
	FROM #IC_Aportes_Det det WITH(NOLOCK) INNER JOIN #IC_Aportes_Enc enc WITH(NOLOCK) ON  det.consecutivo = enc.consecutivo
	WHERE claveCont = '40'
	AND importeDocumento <> valorintmora
	AND enc.tipoMovimiento IN ('A20','A17','A37')

---------------------------------------------------------------------------------
		DELETE #IC_Aportes_Det 
		WHERE consecutivo IN (
			SELECT consecutivo 
			FROM #IC_Aportes_Enc
			WHERE tipoMovimiento IN ('AX1')
		);

		DELETE #IC_Aportes_Enc 
		WHERE tipoMovimiento IN ('AX1');
		END
		IF (SELECT COUNT(1) FROM #IC_Aportes_Enc WITH(NOLOCK)) > 1 
			BEGIN
	
				--SELECT COUNT(1) AS 'Registros Encontrados'
				--FROM #IC_Aportes_Det
				
				UPDATE #IC_Aportes_Enc 
				SET tipoMovimiento = 'A21'
				WHERE tipoMovimiento = 'A07'
				AND @solTipoTransaccion IN('DEVOLUCION_APORTES','DEVOLUCION_APORTES_MASIVA');
				

				DELETE #IC_Aportes_Det 
				WHERE consecutivo IN (
					SELECT consecutivo 
					FROM #IC_Aportes_Enc
					WHERE tipoMovimiento IN ('XXX', 'DEV'));

				DELETE #IC_Aportes_Enc 
				WHERE tipoMovimiento IN ('XXX', 'DEV');

			END --IF

			
		
		-- Calcular el valor del campo referencia clase "AL" para movimientos de prescripcion (A36 y A37), los demas movimientos con la clase "TE"  
		IF EXISTS ( SELECT * FROM #IC_Aportes_Enc WITH(NOLOCK) WHERE tipoMovimiento IN ('A36','A37')) BEGIN -- Agregado por Yesika Bernal
			SELECT @comentario = 'P'
		END ELSE BEGIN
			SELECT @comentario = 'A'
		END
	
	
		SELECT @referenciaNum = valorActual 
			FROM sap.ic_referencia
			WHERE comentario = @comentario
			AND estado = 'A';

			UPDATE #IC_Aportes_Enc
				SET referencia =  @referenciaNum
	
	--comentarear referencia

		UPDATE sap.ic_referencia
			SET valorActual = @referenciaNum + 1
			WHERE comentario = @comentario
			AND estado = 'A';

 --fin comentario

 ---------- numero de identificacion de la persona para devolucion para para actualizacion de textoposicion
		declare @identi varchar (12)
		select @identi = (select Distinct ref1 from #IC_Aportes_Det where claveCont = '40' )
		Print @identi 

 --------------------------------------------- Actualizacion tercero en clave cont 40 ----------------------------------------------
		
		--select * from #IC_Aportes_Det

		if (Select TOP 1 asignacion from #IC_Aportes_Det where claveCont = '40') like '%M%'
			Begin --------------------- para recaudo manual-----------------------------------------------------------------------
					--select perNumeroIdentificacion, perTipoIdentificacion,LEFT (perRazonSocial,20) from AporteGeneral 
					-- inner join Empresa on apgEmpresaTramitadoraAporte = empId 
					-- inner join Persona on empPersona = perId
					--WHERE apgid = @apgid --'4990366'

					UPDATE #IC_Aportes_Det 
						SET ref1 = perNumeroIdentificacion,tipoDocumento = perTipoIdentificacion,ref3 = LEFT (perRazonSocial,20),codigoGenesys = perId
						,tipo = CASE WHEN perTipoIdentificacion = 'NIT' THEN 'E'	ELSE 'P' END
						from AporteGeneral 
					 inner join Empresa on apgEmpresaTramitadoraAporte = empId 
					 inner join Persona on empPersona = perId
					WHERE apgid = @apgid --'4990366'
						And claveCont = '40'
							
			END
			Else  --------------------------------- para planilla 
				BEGIN
					--select regNumeroIdentificacionAportante,regTipoIdentificacionAportante,regnombreaportante from #registrogeneraldv reg
					--inner join #IC_Aportes_Det on regNumPlanilla = asignacion
					--inner join AporteGeneral apg on reg.regId  = apgRegistroGeneral
					--where apg.apgRegistroGeneral = reg.regId
					--and claveCont = '40'

					UPDATE #IC_Aportes_Det 
					SET ref1 = regNumeroIdentificacionAportante,tipoDocumento = mi.tipoDocumentoCaja,ref3 = LEFT (regnombreaportante,20),codigoGenesys = perId
					,tipo = CASE WHEN perTipoIdentificacion = 'NIT' THEN 'E'	ELSE 'P' END
					from #registrogeneraldv reg
					inner join #IC_Aportes_Det on regNumPlanilla = asignacion
					inner join AporteGeneral apg on reg.regId  = apgRegistroGeneral
					inner join persona per on reg.regNumeroIdentificacionAportante = per.perNumeroIdentificacion
					INNER JOIN core.[sap].[MaestraIdentificacion] mi WITH(NOLOCK) ON per.perTipoIdentificacion = mi.tipoIdGenesys
					where apg.apgId = @apgid 
					and claveCont = '40'
				END


--------------Actualizacion campo texto posicion ----------------------------------------------------------------------------------------------------------------------------------

		Declare @origen  varchar(16)
		Select @origen = (select Distinct asignacion from #IC_Aportes_Det)
		------------------------------------------------------------------------------------------------------
			IF (select Distinct asignacion from #IC_Aportes_Det)not like	'%M%'
				BEGIN 
					IF(select Distinct tipoSolicitante from #IC_Aportes_Det where clavecont = '31') = 'EMPLEADOR'
						BEGIN
							
						
							--select @Origen as origen
						
							DECLARE @resultadoE  VARCHAR (max)
							SELECT @resultadoE = (SELECT DISTINCT dbo.ASP_BuscarPlanillasN (@origen))
						
							IF (SELECT @resultadoE) is null
								BEGIN
									UPDATE #IC_Aportes_Det SET textoPosicion = planillaN
									FROM aporteDetalladoRegistroControlN WHERE asignacion not like '%M%' and planillaAntes = asignacion
								END
								ELSE 
									BEGIN
									UPDATE  #IC_Aportes_Det SET textoPosicion = (SELECT DISTINCT dbo.ASP_BuscarPlanillasN (@origen)) WHERE asignacion not like '%M%' and @origen = asignacion
									END 
						END
						ELSE
							BEGIN
						
								declare @planillaNadp varchar (100)
								declare @resultado  varchar (max)
						
								
								select Distinct @planillaNadp = apdn.planillaN
								from dbo.aporteDetalladoRegistroControlN apdn
								where planillaAntes = @origen
								select @resultado = STRING_AGG(CONVERT(NVARCHAR(max), planillaN), '\') 
								from (
										select distinct planillaN
										from dbo.aporteDetalladoRegistroControlN 
										where planillaAntes = @planillaNadp AND redNumeroIdentificacionCotizante = @identi
									) as Subconsulta
								
								--select @resultado + '\' + @planillaNadp as textoposicion
						
						
								if (select @resultado) is null
									begin
										Update #IC_Aportes_Det set textoPosicion = planillaN
										from aporteDetalladoRegistroControlN where asignacion not like '%M%' and planillaAntes = asignacion
									end
									Else 
										begin
										update  #IC_Aportes_Det set textoPosicion = (select @resultado + '\' + @planillaNadp)
										end 
							END
				END
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



	--------------------- Actualizacion "BLANQUEO" de Campos -------------------------------------
	--A15
		UPDATE det
		SET
		noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipointeres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A15')
		AND claveCont IN ('31')

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
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A15')
		AND claveCont IN ('40')
		
		--A16
		UPDATE det
		SET 
		
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A16')
		AND claveCont = '40'

		UPDATE det
		SET --noIdentificado = 'NI',
		noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A16')
		AND claveCont = '31'

		--A17
	UPDATE det
		SET 
		noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A17')
		AND claveCont = '31'
		AND valorIntMora > 0

		UPDATE det
		SET	adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A17')
		AND claveCont = '40'
		AND importeDocumento = ValorAporte

		UPDATE det
		SET	noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 1,
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A17')
		AND claveCont = '40'
		AND importeDocumento = valorIntMora

	--A19
		UPDATE det
		SET 
		noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A19')
		AND claveCont = '31'


		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A19')
		AND claveCont = '40'


		--A20
		UPDATE det
		SET 
		noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A20')
		AND claveCont = '31'
		AND valorIntMora > 0

		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A20')
		AND claveCont = '40'
		AND importeDocumento = ValorAporte

		UPDATE det
		SET	noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 1,
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A20')
		AND claveCont = '40'
		AND importeDocumento = valorIntMora

		--A21
		UPDATE det
		SET
		noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipointeres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A21')
		AND claveCont IN ('31')

		UPDATE det
		SET noIdentificado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		--claseDeAporte = '', -- por definir
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = '',
		claseDeAporte = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A21')
		AND claveCont = '40'

		-------A36
		UPDATE det
		SET claveCont = '31',
		noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A36')
		AND claveCont = '50'

		UPDATE det
		SET 
		noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A36')
		AND claveCont = '31'


		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A36')
		AND claveCont = '40'


		--A37
		UPDATE det
		SET 
		noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A37')
		AND claveCont = '31'

		UPDATE det
		SET
		noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A37')
		AND claveCont = '40'
		AND importeDocumento = ValorAporte
		--AND valorIntMora = 0

		UPDATE det
		SET
		noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 1,
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A37')
		AND claveCont = '40'
		AND importeDocumento = valorIntMora

  
			-- ENVIAR EL ACREEDOR SI NO EXISTE PARA EL MOVIMIENTO  CLAVE CONTABLE 31
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		--  Calcula el acreedor cuando es CCF
		-- AQUI VOY: que pasa si la caja en cuestion no esta en la tabla Persona?
		UPDATE A31
		SET ref1 = ccf.ccfNit,
		tipoDocumento  = 'NIT',
		ref3 = ccf.ccfNombre,
		codigoGenesys = per.perid,
		claseCuenta  = 'K',
		tipo = 'E'
		FROM #IC_Aportes_Det A31, SAP.MaestraCcfConNit ccf, Persona per
		WHERE A31.devolucionCCF = ccf.ccfid
		AND ccf.ccfNit = per.perNumeroIdentificacion
		AND A31.claveCont = '31'

		-- Calcula el codigo SAP para el movimiento 31, cambia la clase de cuenta a 'K'.
		UPDATE A31
		SET codigoSAP = codsap.codigoSAP,
		claseCuenta  = 'K'
		FROM [sap].[CodSAPGenesysAcreedor] codsap, #IC_Aportes_Det A31
		WHERE codsap.codigoGenesys = A31.codigoGenesys
		AND A31.claveCont = '31'
		

		----.. Todo Movimiento 31 con clase de cuenta K ---------------
		UPDATE A31
		SET	claseCuenta  = 'K'
		FROM #IC_Aportes_Det A31
		WHERE A31.claveCont = '31'

		UPDATE #IC_Aportes_Det 
		SET ref1 = serial.prdNumeroIdentificacion
		FROM (SELECT ped.prdNumeroIdentificacion FROM #IC_Aportes_Det det
		INNER JOIN PreRegistroEmpresaDescentralizada ped ON det.ref1 = ped.prdNumeroIdentificacionSerial) as serial
		WHERE clavecont = '31'

			UPDATE #IC_Aportes_Det 
		SET codigoGenesys = serial.perid
		FROM (SELECT per.perId  FROM #IC_Aportes_Det det
		INNER JOIN PreRegistroEmpresaDescentralizada ped ON det.ref1 = ped.prdNumeroIdentificacionSerial
		INNER JOIN persona per ON ped.prdNumeroIdentificacion = per.perNumeroIdentificacion) as serial
		WHERE clavecont = '31'

		
		-- Calculo de las variables para enviar el acreedor
		SELECT @numeroDocumento = ref1, @tipoDocumento = tipoDocumento, @planilla = asignacion FROM #IC_Aportes_Det WHERE claveCont = '31'

		---- Actualizar el consecutivo definitivo en la tabla de integracion de encabezado con el detalle
		SELECT @consecutivo = ISNULL(MAX(consecutivo), 0) FROM sap.IC_Aportes_Enc
		--SELECT @consecutivodet = ISNULL( max(consecutivo),0) FROM sap.IC_Aportes_Det


		UPDATE #IC_Aportes_Det
		SET consecutivo = consecutivo + @consecutivo

		UPDATE #IC_Aportes_Enc
		SET consecutivo = consecutivo + @consecutivo


	-- Calcular el valor del campo referencia clase "AL" para movimientos de prescripcion (A09 y A10), los demas movimientos con la clase "TE"
		IF EXISTS ( SELECT * FROM #IC_Aportes_Enc WITH(NOLOCK) WHERE tipoMovimiento IN ('A36','A37')) BEGIN
			SELECT @comentario = 'P'
		END ELSE BEGIN
			SELECT @comentario = 'A'
		END

		print @comentario

		SELECT @referenciaNum = valorActual 
		FROM sap.IC_Referencia WITH(NOLOCK)
		WHERE comentario = @comentario
		AND estado = 'A';

	print @referenciaNum


--comentarear

	IF ((SELECT referencia FROM #IC_Aportes_Enc ) IS NOT NULL )
	BEGIN

	/*
		print  @numeroDocumento
		print @tipoDocumento
		print @usuario
		print @planilla
	*/
		IF NOT EXISTS (SELECT acre.codigoSAP FROM [sap].[CodSAPGenesysAcreedor] acre INNER JOIN Persona per ON acre.codigoGenesys = per.perId WHERE per.perNumeroIdentificacion = @numeroDocumento) BEGIN
			EXEC [sap].[USP_GetAcreedoresAportes_DocumentoId] @numeroDocumento, @tipoDocumento, @usuario, @planilla
		END
	

	
		IF EXISTS (SELECT enca.consecutivo FROM #IC_Aportes_Enc enca INNER JOIN #IC_Aportes_Det deta on enca.consecutivo = deta.consecutivo where deta.importeDocumento <> 0)
		BEGIN
	      
			INSERT INTO sap.IC_Aportes_Enc(
				[fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[observacion],
				[moneda],[documentoContable],[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario])
			SELECT distinct [fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[observacion],
				[moneda],[documentoContable],[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario]
			FROM #IC_Aportes_Enc WITH(NOLOCK) ; 
         
				-- Actualizar el consecutivo definitivo en la tabla de integracion de encabezado con el detalle
				SELECT @consecutivo=MAX(consecutivo) FROM sap.IC_Aportes_Enc

				UPDATE #IC_Aportes_Det
				SET consecutivo = @consecutivo

      
			INSERT INTO sap.IC_Aportes_Det([consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
					[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
					[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
					[claseCuenta],[usuario],[fechaEjecucion])
			SELECT [consecutivo],[codigoSap],CAST(CAST([importeDocumento] AS BIGINT) AS VARCHAR(18)),[operador],[claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
					[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
					[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
					[claseCuenta],[usuario],GETDATE()-'05:00:00'
			FROM #IC_Aportes_Det  WITH(NOLOCK)
			WHERE importeDocumento <> 0  ---  para cuando el registro viene sin detalle y que no traiga lineas en valores 0 08/10/2022
		
			UPDATE  #IC_Aportes_Det
				SET consecutivo =(SELECT consecutivo FROM #IC_Aportes_Enc)
		
			-- INSERCIoN EN LA TABLA DE CONTROL DE PLANILLAS
 
 
			INSERT INTO sap.PlanillasCtrl
			SELECT DISTINCT LEFT(det.asignacion, 12), 1, det.ref1, det.tipoDocumento, SAP.GetLocalDate(), 'APORTES', det.ref3 + enc.observacion, enc.tipoMovimiento,estadoaporteaportantetercero,operadorinformacion,@apgid 
			FROM #IC_Aportes_Det det WITH(NOLOCK)
			INNER JOIN #IC_Aportes_Enc enc WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
			WHERE det.EstadoRegistroAporteAportante is not null


			-- Insercion en la tabla de control de solicitudes Devoluciones
			INSERT INTO sap.ContablesCtrl
			SELECT DISTINCT @solNumeroRadicacion, 1 as estado, perNumeroIdentificacion, perTipoIdentificacion,SAP.GetLocalDate(),'APORTES',solTipoTransaccion 
			FROM solicitud sol WITH(NOLOCK)
			INNER JOIN  SolicitudDevolucionAporte sda WITH(NOLOCK) ON sda.sdaSolicitudGlobal = sol.solId
			INNER JOIN Persona per WITH(NOLOCK) on sda.sdaPersona = per.perId where solNumeroRadicacion = @solNumeroRadicacion and solTipoTransaccion IN('DEVOLUCION_APORTES','DEVOLUCION_APORTES_MASIVA')
		
		END
	
	END
	

-- Fin Comentario

			--Detalle
			SELECT DISTINCT * FROM #IC_Aportes_Enc
			ORDER BY tipoMovimiento, consecutivo

			SELECT * FROM #IC_Aportes_Det WITH(NOLOCK)
			WHERE importeDocumento <> 0  ---  para cuando el registro viene sin detalle y que no traiga lineas en valores 0 08/10/2022
			ORDER BY claveCont

			DROP TABLE IF EXISTS #IC_Aportes_Det;	
			DROP TABLE IF EXISTS #IC_Aportes_Enc;					
			DROP TABLE IF EXISTS #APLICA 
			

	
		COMMIT TRANSACTION
	END TRY

	BEGIN CATCH

		ROLLBACK TRANSACTION

		SELECT  ERROR_NUMBER() AS ErrorNumber  ,ERROR_SEVERITY() AS ErrorSeverity  
		,ERROR_STATE() AS ErrorState,ERROR_PROCEDURE() AS ErrorProcedure  
		,ERROR_LINE() AS ErrorLine,ERROR_MESSAGE() AS ErrorMessage
		,GETDATE() -'05:00:00' as Fechaing;  
 
		INSERT INTO core.sap.LogErrorAportes 
		SELECT @planilla, '',  @regId,@solNumeroRadicacion,@solTipoTransaccion,@operadorinformacion, ERROR_NUMBER(),ERROR_SEVERITY(),ERROR_STATE(),ERROR_PROCEDURE(),ERROR_LINE(),ERROR_MESSAGE(),GETDATE() -'05:00:00'
 

		END CATCH



		--DELETE FROM sap.ejecucion_aportes2 
		--	END
		--	ELSE BEGIN
		--	SELECT 'El proceso ya se encuentra en ejecucion'
		--	END

END