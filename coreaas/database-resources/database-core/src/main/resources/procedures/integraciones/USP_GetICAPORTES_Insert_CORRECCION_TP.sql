CREATE OR ALTER  PROCEDURE [sap].[USP_GetICAPORTES_Insert_CORRECCION_TP] 	@planilla VARCHAR(20), @regid BIGINT, @apgid BIGINT,@solNumeroRadicacion VARCHAR (30),	
	@solTipoTransaccion VARCHAR (30),	@operadorinformacion INT
AS
SET ANSI_NULLS ON
SET  NOCOUNT ON 
SET QUOTED_IDENTIFIER ON


--IF (SELECT COUNT(*) FROM sap.ejecucion_aportes2 WITH (NOLOCK)) <= 0 BEGIN	
	
--	INSERT INTO sap.ejecucion_aportes2
--		SELECT 1

--SET @solNumeroRadicacion = '032023006544' 
--SET @solTipoTransaccion = 'CORRECCION_APORTES' 
--SET @apgid ='2590312'

DECLARE @referenciaNum BIGINT, @consecutivo BIGINT, @PerNumD VARCHAR(16), @perTipD VARCHAR(10)
DECLARE @numeroDocumento VARCHAR(50), @tipoDocumento VARCHAR(20), @usuario VARCHAR(30) = 'soportetecnico@asopagos.com'

DROP TABLE IF EXISTS #registrogeneralcr  


--create table #registrogeneralcr
--	(
--	regid varchar(15),
--	regNumPlanilla varchar(12),
--	regValorIntMora varchar(15),
--	regTipoIdentificacionAportante varchar(20),
--	regNumeroIdentificacionAportante varchar(16),
--	regNombreAportante varchar(100)
	
--	)
CREATE TABLE #IC_Aportes_Detcr
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
		[regId] [bigint] NULL,
		[fechaRecaudo] [DATE] NULL,
		[fechaProcesamiento] [DATE] NULL,
		[EstadoRegistroAporteAportante] [VARCHAR](30) NULL,
		[FormaReconocimientoAporte] [VARCHAR](75) NULL,
		[apgEstadoAportante] [VARCHAR](50) NULL,
		[marcaPeriodo] [VARCHAR](19) NULL,
		[tipoSolicitante] [VARCHAR](13) NULL,
		[valorIntMora] [NUMERIC](21,5) NULL,
		[tarifa] [NUMERIC](7,5) NULL,
		[apgOrigenAporte] [VARCHAR] (26) NULL,
		[regIdorg] [bigint] NULL,
		[docorg] [varchar](12) NULL,
		[claseDeAporteorg] [varchar](3) NULL,
		[tipoInteresOrg] [varchar] (3) Null,
		[operadorinformacion] [int] Null,
		[noIdentificadoOri] [varchar](2),
		[apgIdorg] [BIGINT] Null,
		[numdoc] [varchar](12) NOT NULL,
		[adelantadoOri] [varchar](2) NULL
			
	)

	CREATE TABLE #IC_Aportes_Enccr
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

--DECLARE  @solNumeroRadicacion varchar(30) = '032023006544' 
--DECLARE  @solTipoTransaccion varchar(30)  = 'CORRECCION_APORTES' 
--DECLARE  @apgid  varchar(10)  ='2590312'
	
	-- DETALLE: reconoce los registros de DEVOLUCION
	----------------------------------------------------------INSERT CORRECCION-------------------------------------------------------------------------

	--INSERT 
	SELECT regid, regNumPlanilla, regNombreAportante, regValorIntMora,regTipoIdentificacionAportante,regNumeroIdentificacionAportante
	INTO #registrogeneralcr  
	FROM core.pila.RegistroGeneral	
	Where regDateTimeInsert IS NULL OR regDateTimeInsert >= dateadd(year,-5,getdate())




	INSERT INTO #IC_Aportes_Detcr(
		[consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
		[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
		[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
		[claseCuenta],[usuario],regId,EstadoRegistroAporteAportante,valorintmora,tarifa,apgOrigenAporte,regIdorg,docorg, FormaReconocimientoAporte,
		tiposolicitante,noIdentificadoOri,apgIdorg,numdoc,adelantadoOri
	)
	Select	MIN([consecutivo]) AS consecutivo,codigoSap,sum(convert(numeric,ImporteDocumento)),operador,claveCont,asignacion,textoposicion,ref1,ref2,tipoDocumento,ref3,
	noIdentificado,adelantado,identificadorDelBanco,codigoBanco,transitoria,claseDeAporte,claseDeAportePrescripcion,tieneIntereses,tipoInteres,correccion,tipoMora,id,codigoGenesys,
	tipo,claseCuenta,usuario,regId,EstadoRegistroAporteAportante,valorintmora,tarifa,apgOrigenAporte,regIdorg,docorg, FormaReconocimientoAporte,tiposolicitante,
		noIdentificadoOri,apgIdorg,numdoc,adelantadoOri
	from(
	SELECT DISTINCT
		ROW_NUMBER() OVER(ORDER BY regId ASC) AS consecutivo,
		'' AS codigoSap,
		round(ISNULL(apg2.apgValTotalApoObligatorio, 0) + ISNULL(apg2.apgValorIntMora, 0),0)
		 AS ImporteDocumento,  --modificado por valor de la correccion en parciales y totales.(CAMILO GOMEZ)
		'' AS operador,			-- Solo aplica para la INTEGRACIoN DE CARTERA
		'40' AS claveCont,
		CASE WHEN reg.regNumPlanilla IS NOT NULL THEN ISNULL(reg.regnumplanilla,'')  ELSE '' /* CONCAT('M',apgId)*/ END AS asignacion,
		'' as textoposicion,
		regNumeroIdentificacionAportante AS ref1,
		CONCAT('M', ISNULL(apg2.apgId, '')) as ref2,
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
		CASE  WHEN (apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO')    AND (reg.regValorIntMora <> 0 OR reg.regValorIntMora > 0) AND apg.apgTipoSolicitante = 'EMPLEADOR' 
			   AND (apd2.apdTarifa = 0.00 OR apd2.apdTarifa = 0.04 OR apd2.apdTarifa = 0.01 OR apd2.apdTarifa = 0.02 OR apd2.apdTarifa = 0.03 OR apd2.apdTarifa = 0.06) THEN 'IN1'
			  WHEN (apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (reg.regValorIntMora <> 0 OR reg.regValorIntMora > 0) AND apg2.apgTipoSolicitante = 'EMPLEADOR' AND (apd2.apdId is null) THEN 'IN1' 
			  WHEN (apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (reg.regValorIntMora <> 0 OR reg.regValorIntMora > 0) AND apg2.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd2.apdTarifa = 0.00 or apd2.apdTarifa = 0.02 OR apd2.apdTarifa = 0.04) THEN 'IN2'
			  WHEN (apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (reg.regValorIntMora <> 0 OR reg.regValorIntMora > 0) AND apg2.apgTipoSolicitante = 'PENSIONADO' AND apd2.apdTarifa <> 0.006 THEN 'IN3'
			  WHEN (apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (reg.regValorIntMora <> 0 OR reg.regValorIntMora > 0) AND apg2.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd2.apdTarifa = 0.006 OR apd2.apdTarifa = 0.01) THEN 'IN4'
			  WHEN (apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (reg.regValorIntMora <> 0 OR reg.regValorIntMora > 0) AND apg2.apgTipoSolicitante = 'PENSIONADO' AND apd2.apdTarifa = 0.006 THEN 'IN5'
			  
			  WHEN (apg2.apgEstadoRegistroAporteAportante = 'REGISTRADO') AND (reg.regValorIntMora <> 0 OR reg.regValorIntMora > 0) AND apg2.apgTipoSolicitante = 'EMPLEADOR' AND (apd2.apdTarifa = 0.00 OR apd2.apdTarifa = 0.04 OR apd2.apdTarifa = 0.01 OR apd2.apdTarifa = 0.02 OR apd2.apdTarifa = 0.03 OR apd2.apdTarifa = 0.06) THEN 'IM1'
			  WHEN (apg2.apgEstadoRegistroAporteAportante = 'REGISTRADO') AND (reg.regValorIntMora <> 0 OR reg.regValorIntMora > 0) AND apg2.apgTipoSolicitante = 'EMPLEADOR' AND (apd2.apdId is null) THEN 'IM1'---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
			  WHEN (apg2.apgEstadoRegistroAporteAportante = 'REGISTRADO') AND (reg.regValorIntMora <> 0 OR reg.regValorIntMora > 0) AND apg2.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd2.apdTarifa = 0.00 or apd2.apdTarifa = 0.02 OR apd2.apdTarifa = 0.04) THEN 'IM2'
			  WHEN (apg2.apgEstadoRegistroAporteAportante = 'REGISTRADO') AND (reg.regValorIntMora <> 0 OR reg.regValorIntMora > 0) AND apg2.apgTipoSolicitante = 'PENSIONADO' AND apd2.apdTarifa <> 0.006 THEN 'IM3'
			  WHEN (apg2.apgEstadoRegistroAporteAportante = 'REGISTRADO') AND (reg.regValorIntMora <> 0 OR reg.regValorIntMora > 0) AND apg2.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd2.apdTarifa = 0.006 OR apd2.apdTarifa = 0.01) THEN 'IM4'
			  WHEN (apg2.apgEstadoRegistroAporteAportante = 'REGISTRADO') AND (reg.regValorIntMora <> 0 OR reg.regValorIntMora > 0) AND apg2.apgTipoSolicitante = 'PENSIONADO' AND apd2.apdTarifa = 0.006 THEN 'IM5'

		ELSE '*' END  AS tipoInteres,
		0 AS correccion,			-- Se define con los registros en la tabla Movimientoaporte en moaTipoAjuste = ''CORRECCION_A_LA_BAJA' va a ser anulacion.
		'' AS tipoMora,				-- Solo aplica para la INTEGRACIoN DE CARTERA
		0 AS id,
		NULL AS codigoGenesys,
		'=' AS tipo,
		'S' AS claseCuenta,
		'soportetecnico@asopagos.com' AS usuario,
		apg2.apgRegistroGeneral as regid,
		apg2.apgEstadoRegistroAporteAportante as EstadoRegistroAporteAportante,
		0 as valorIntMora,
		0 as tarifa,
		apg2.apgOrigenAporte,
		regId as regidorg,
		reg.regNumeroIdentificacionAportante as docorg,
		apd2.apdFormaReconocimientoAporte as FormaReconocimientoAporte,
		apg.apgtiposolicitante as tiposolicitante,
		'' as noIdentificadoOri,
		apg.apgId as apgIdorg,
		pernumeroidentificacion as numdoc,
		'' as adelantadoOri
	FROM #registrogeneralcr reg
	INNER JOIN core.[sap].[MaestraIdentificacion] mi WITH(NOLOCK) ON reg.regTipoIdentificacionAportante = mi.tipoIdGenesys
	INNER JOIN core.dbo.AporteGeneral apg WITH(NOLOCK) ON reg.regId = apg.apgRegistroGeneral  
	INNER JOIN core.dbo.SolicitudCorreccionAporte sca WITH(NOLOCK) ON  apg.apgId = sca.scaAporteGeneral 
	INNER JOIN core.dbo.Solicitud sol WITH(NOLOCK) ON sca.scaSolicitudGlobal = sol.solId
	INNER JOIN core.dbo.Correccion cor WITH(NOLOCK) ON sca.scaid = cor.corsolicitudcorreccionaporte
	INNER JOIN core.dbo.AporteGeneral apg2 WITH(NOLOCK) ON  cor.corAporteGeneral =  apg2.apgid 
	INNER JOIN core.dbo.AporteDetallado apd2  WITH(NOLOCK) ON apg.apgId = apd2.apdAporteGeneral
	LEFT JOIN core.dbo.Empresa emp WITH(NOLOCK) ON  apg2.apgEmpresa = empId 
	LEFT JOIN core.dbo.persona p WITH(NOLOCK) ON case when empPersona is null then apd2.apdPersona else empPersona END = perId
	WHERE YEAR (sol.solFechaRadicacion) > 2010 -- verificar desde que ano se activan las opciones.
	AND solTipoTransaccion = @solTipoTransaccion
	AND sol.solNumeroRadicacion = @solNumeroRadicacion
	AND apg2.apgId = @apgid
	--AND sol.solnumeroradicacion NOT IN (SELECT solNumeroRadicacion FROM sap.ContablesCtrl WITH (NOLOCK))
	AND (len(reg.regnumplanilla) <= 10 or regNumPlanilla is null)) as CR 

	group by codigoSap,operador,claveCont,asignacion,textoposicion,ref1,ref2,tipoDocumento,ref3,
	noIdentificado,adelantado,identificadorDelBanco,codigoBanco,transitoria,claseDeAporte,claseDeAportePrescripcion,tieneIntereses,tipoInteres,correccion,tipoMora,id,codigoGenesys,
	tipo,claseCuenta,usuario,regId,EstadoRegistroAporteAportante,valorintmora,tarifa,apgOrigenAporte,regIdorg,docorg, FormaReconocimientoAporte,tiposolicitante,
		noIdentificadoOri,apgIdorg,numdoc,adelantadoOri


	declare @tipoSolicitante [VARCHAR](13)
	Select @tipoSolicitante = tiposolicitante from #IC_Aportes_Detcr

	select * from #IC_Aportes_Detcr 

		----------------------------  Actualizacion para importe de documento cuando se duplica el registro en la tabla correccion -------------------------------------------------
	IF(select Distinct count(corAporteDetallado) From Correccion inner join AporteGeneral on corAporteGeneral = apgId where apgid  = @apgid) > 1
			
			begin
				print 'Entra en actualizacion de importe'

			select Distinct apgValTotalApoObligatorio From Correccion
			inner join AporteGeneral on corAporteGeneral = apgId
			where apgid  = @apgid

			Update #IC_Aportes_Detcr set importeDocumento = apgValTotalApoObligatorio From Correccion
			inner join AporteGeneral on corAporteGeneral = apgId
			where apgid  = @apgid

			end

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------


	UPDATE	#IC_Aportes_Detcr SET asignacion = (select  top 1 CONCAT('M', apgIdorg) from #IC_Aportes_Detcr) WHERE asignacion = ''
	
	DELETE #IC_Aportes_Detcr WHERE importeDocumento = 0 	
	--Select * from #IC_Aportes_Detcr
		
	IF EXISTS (SELECT * FROM #IC_Aportes_Detcr) 
		BEGIN

---------------------------------------------Actualizacion de campos del detalle---------------------------------------------------------

			UPDATE iad SET 
			
				iad.importeDocumento = CASE WHEN iad.importeDocumento = 0 THEN iad.importeDocumento + (ISNULL(apg.apgValorIntMora, 0))
										ELSE iad.importeDocumento
										END,

				iad.asignacion = CASE WHEN iad.asignacion = ''  THEN CONCAT('M', ISNULL(iad.apgIdorg, '')) ELSE iad.asignacion END, 

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

				--iad.tieneIntereses = CASE WHEN iad.tieneIntereses = 0 AND apg.apgValorIntMora = 0 THEN 0
				--						WHEN iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0 THEN 1
				--						ELSE iad.tieneIntereses
				--						END,
						
				iad.tipoInteres = CASE
						 
				        ---- Intereses no identificados
									WHEN (apg.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04 OR apd.apdTarifa = 0.01 OR apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.03 OR apd.apdTarifa = 0.06) THEN 'IN1'
									WHEN (apg.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'IN1'---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
									WHEN (apg.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.00 or apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'IN2'
									WHEN (apg.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'IN3'
									WHEN (apg.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'IN4'
									WHEN (apg.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'IN5'
						---- Intereses por mora
									WHEN apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04 OR apd.apdTarifa = 0.01 OR apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.03 OR apd.apdTarifa = 0.06) THEN 'IM1'
									WHEN apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'IM1' ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
									WHEN apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.00 or apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'IM2'
									WHEN apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'IM3'
									WHEN apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'IM4'
									WHEN apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'IM5'
						---- Rendimientos prescritos
									WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04 OR apd.apdTarifa = 0.01 OR apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.03 OR apd.apdTarifa = 0.06) THEN 'RP1'
									WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'RP1' ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
									WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.00 or apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'RP2'
									WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'RP3'
									WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'RP4'
									WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'RP5'
									ELSE '*'
								END,

				iad.fechaRecaudo = apg.apgFechaRecaudo ,

				iad.fechaProcesamiento = CASE WHEN [SAP].[calcularPrimerDiaHabilMes]() = [SAP].[calcularSiguienteDiaHabil]() AND DAY(DATEADD(HOUR,-5,GETDATE())) >= [SAP].[calcularPrimerDiaHabilMes]() AND DATEDIFF(DAY, apg.apgFechaRecaudo, DATEADD(HOUR,-5,GETDATE())) > 0
											THEN EOMONTH(DATEADD(HOUR,-5,GETDATE()),-1)
											ELSE DATEADD(HOUR,-5,GETDATE()) END,
				--iad.EstadoRegistroAporteAportante =  CASE WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES'  THEN iad.EstadoRegistroAporteAportante ELSE  apg.apgEstadoRegistroAporteAportante END,
				
				iad.FormaReconocimientoAporte = apg.apgFormaReconocimientoAporte,

				iad.apgEstadoAportante = (Select Estadoafi.roaEstadoAfiliado from (select top 1 MAX (roaFechaAfiliacion) as fechaafiliacion, apgEstadoAportante,apdEstadoCotizante,roaEstadoAfiliado,roaEstadoEnEntidadPagadora ,perNumeroIdentificacion 
																					from AporteGeneral 
																					inner join AporteDetallado on apgid = apdAporteGeneral
																					inner join persona on apgPersona = perid 
																					inner join Afiliado on perid = afiPersona
																					inner join RolAfiliado on afiid = roaAfiliado
																					where apgId = @apgid --and perId = '2228667'
																					group by apgEstadoAportante,apdEstadoCotizante,roaEstadoAfiliado,roaEstadoEnEntidadPagadora,roaFechaAfiliacion,perNumeroIdentificacion
																					order by roaFechaAfiliacion desc) Estadoafi),

				iad.marcaPeriodo = apg.apgMarcaPeriodo,
				iad.tipoSolicitante = apg.apgtipoSolicitante,
				iad.valorIntMora = CASE WHEN iad.valorIntMora = 0 THEN apg.apgvalorIntMora 
									ELSE iad.valorIntMora END,
				iad.tarifa = CASE WHEN  apd.apdId is null AND apg.apgTipoSolicitante = 'EMPLEADOR'  then 0.04 Else apd.apdTarifa  End,-----  Este campo es obligatorio, si viene en 0 o null no realiza el proceso para el tipo de aporte. (revisar con robinson)
				--- se agrega case when para el proceso cualdo es aporte a aportante y viene sin detalle.
				--------- Para Movimiento A12 -------------------
			
			iad.claseDeAporteorg = CASE WHEN apg2.apgTipoSolicitante = 'EMPLEADOR' AND (apd2.apdTarifa = 0.00 OR apd2.apdTarifa = 0.04)  THEN 'CA1'
				WHEN apg2.apgTipoSolicitante = 'EMPLEADOR' AND apd2.apdTarifa < 0.04 THEN 'CA1'
				WHEN apg2.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd2.apdTarifa = 0.02 OR apd2.apdTarifa = 0.04) THEN 'CA2'
				WHEN apg2.apgTipoSolicitante = 'PENSIONADO' AND apd2.apdTarifa <> 0.006 THEN 'CA3'
				WHEN apg2.apgTipoSolicitante = 'PENSIONADO' AND apd2.apdTarifa = 0.006 THEN 'CA4'
				WHEN apg2.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd2.apdTarifa = 0.006 OR apd2.apdTarifa = 0.01) THEN 'CA5'
				ELSE '**'
			END,
			iad.tipoInteresOrg = CASE WHEN (apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR apg2.apgValorIntMora > 0) AND apg2.apgTipoSolicitante = 'EMPLEADOR' AND (apd2.apdTarifa = 0.00 OR apd2.apdTarifa = 0.04) THEN 'IN1'
					WHEN (apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg2.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd2.apdTarifa = 0.02 OR apd2.apdTarifa = 0.04) THEN 'IN2'
					WHEN (apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg2.apgTipoSolicitante = 'PENSIONADO' AND apd2.apdTarifa <> 0.006 THEN 'IN3'
					WHEN (apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg2.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd2.apdTarifa = 0.006 OR apd2.apdTarifa = 0.01) THEN 'IN4'
					WHEN (apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO') AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg2.apgTipoSolicitante = 'PENSIONADO' AND apd2.apdTarifa = 0.006 THEN 'IN5'
					-- Intereses por mora
					WHEN apg2.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg2.apgTipoSolicitante = 'EMPLEADOR' AND (apd2.apdTarifa = 0.00 OR apd2.apdTarifa = 0.04) THEN 'IM1'
					WHEN apg2.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg2.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd2.apdTarifa = 0.02 OR apd2.apdTarifa = 0.04) THEN 'IM2'
					WHEN apg2.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg2.apgTipoSolicitante = 'PENSIONADO' AND apd2.apdTarifa <> 0.006 THEN 'IM3'
					WHEN apg2.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg2.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd2.apdTarifa = 0.006 OR apd2.apdTarifa = 0.01) THEN 'IM4'
					WHEN apg2.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg2.apgTipoSolicitante = 'PENSIONADO' AND apd2.apdTarifa = 0.006 THEN 'IM5'
					-- Rendimientos prescritos
					WHEN apg2.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg2.apgTipoSolicitante = 'EMPLEADOR' AND (apd2.apdTarifa = 0.00 OR apd2.apdTarifa = 0.04) THEN 'RP1'
					WHEN apg2.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg2.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd2.apdTarifa = 0.02 OR apd2.apdTarifa = 0.04) THEN 'RP2'
					WHEN apg2.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg2.apgTipoSolicitante = 'PENSIONADO' AND apd2.apdTarifa <> 0.006 THEN 'RP3'
					WHEN apg2.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg2.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd2.apdTarifa = 0.006 OR apd2.apdTarifa = 0.01) THEN 'RP4'
					WHEN apg2.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND (iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0) AND apg2.apgTipoSolicitante = 'PENSIONADO' AND apd2.apdTarifa = 0.006 THEN 'RP5'
					ELSE '*'
			END,
			noIdentificadoOri = CASE WHEN apg2.apgEstadoRegistroAporteAportante IN ('RELACIONADO','OTROS_INGRESOS') AND apg2.apgTipoSolicitante = 'EMPLEADOR' AND (apd2.apdTarifa = 0.00 OR apd2.apdTarifa = 0.04 OR apd2.apdTarifa = 0.02 OR apd2.apdTarifa = 0.01 or apd2.apdTarifa = 0.03) THEN 'N1'
				WHEN apg2.apgEstadoRegistroAporteAportante IN ('RELACIONADO','OTROS_INGRESOS') AND apg2.apgTipoSolicitante = 'EMPLEADOR' AND (apd2.apdId is null) THEN 'N1'   ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022   
				WHEN apg2.apgEstadoRegistroAporteAportante IN ('RELACIONADO','OTROS_INGRESOS') AND apg2.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd2.apdTarifa = 0.02 OR apd2.apdTarifa = 0.04) THEN 'N2'
				WHEN apg2.apgEstadoRegistroAporteAportante IN ('RELACIONADO','OTROS_INGRESOS') AND apg2.apgTipoSolicitante = 'PENSIONADO' AND apd2.apdTarifa <> 0.006 THEN 'N3'
				WHEN apg2.apgEstadoRegistroAporteAportante IN ('RELACIONADO','OTROS_INGRESOS') AND apg2.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd2.apdTarifa = 0.006 OR apd2.apdTarifa = 0.01) THEN 'N4'
				WHEN apg2.apgEstadoRegistroAporteAportante IN ('RELACIONADO','OTROS_INGRESOS') AND apg2.apgTipoSolicitante = 'PENSIONADO' AND apd2.apdTarifa = 0.006 THEN 'N5'
				ELSE '**' 
			END,

			iad.adelantadoOri = CASE WHEN apd2.apdMarcaPeriodo = 'PERIODO_FUTURO' AND apg2.apgTipoSolicitante = 'EMPLEADOR' AND (apd2.apdTarifa = 0.00 OR apd2.apdTarifa = 0.04 OR apd2.apdTarifa = 0.02 OR apd2.apdTarifa = 0.01 or apd2.apdTarifa = 0.03) THEN 'A1'
										WHEN apd2.apdMarcaPeriodo = 'PERIODO_FUTURO' AND apg2.apgTipoSolicitante = 'EMPLEADOR' AND (apd2.apdId is null) THEN 'A1'---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
										WHEN apd2.apdMarcaPeriodo = 'PERIODO_FUTURO' AND apg2.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd2.apdTarifa = 0.02 OR apd2.apdTarifa = 0.04) THEN 'A2'   
										WHEN apd2.apdMarcaPeriodo = 'PERIODO_FUTURO' AND apg2.apgTipoSolicitante = 'PENSIONADO' AND apd2.apdTarifa <> 0.006 THEN 'A3'
										WHEN apd2.apdMarcaPeriodo = 'PERIODO_FUTURO' AND apg2.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd2.apdTarifa = 0.006 OR apd2.apdTarifa = 0.01) THEN 'A4'
										WHEN apd2.apdMarcaPeriodo = 'PERIODO_FUTURO' AND apg2.apgTipoSolicitante = 'PENSIONADO' AND apd2.apdTarifa = 0.006 THEN 'A5'
										ELSE '**' 
										END

		-------------------------------------------------------------------------
		
				FROM core.dbo.AporteGeneral apg WITH(NOLOCK)
					LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON  apg.apgId  = apd.apdAporteGeneral --- CAMBIO A LEFT CUANDO EL DOCUMENTO NO TRAE LOS DETALLES DE LA TRANSACCION EN AporteDetallado apd
					INNER JOIN #IC_Aportes_Detcr iad WITH(NOLOCK) ON  apg.apgId = @apgid
					LEFT JOIN core.dbo.AporteGeneral apg2 WITH(NOLOCK) ON iad.regIdorg = apg2.apgRegistroGeneral and apg2.apgid = iad.apgIdorg
					LEFT JOIN core.dbo.AporteDetallado apd2 WITH(NOLOCK) ON  apg2.apgId = apd2.apdAporteGeneral AND apg2.apgid = iad.apgIdorg
					LEFT JOIN persona per on perNumeroIdentificacion = iad.numdoc and perId = apd2.apdPersona   -- se cambia a left para casos de empleador en donde no crusa con la tabla persona. Camilo 14-11-2023
					LEFT JOIN core.dbo.CUENTAS_BANCARIAS cb WITH(NOLOCK) ON apg.apgCuentaBancariaRecaudo = cb.ID  -- agregado posterior a gap vuenta bancaria 18/10/2022
					LEFT JOIN core.sap.MaestracodigoEntidad ce WITH(NOLOCK) ON cb.ID = ce.idCuentasBancarias

					
				
--A05 EN CASO DE UNA CORRECION QUE DISPARA A03(AX5)

declare @PerOriginal varchar(20) =(select Distinct numdoc from #IC_Aportes_Detcr)

declare @fechaafili date = (select max(roaFechaAfiliacion)from Persona 
								inner join Afiliado afi on perId = afiPersona
								inner join RolAfiliado on roaAfiliado = afi.afiId 
								where  perNumeroIdentificacion = @PerOriginal) 
					
	if(((select Distinct roaEstadoAfiliado from Persona 
					left join Afiliado afi on perId = afiPersona
					left join RolAfiliado on roaAfiliado = afi.afiId 
					where roaFechaAfiliacion = @fechaafili and perNumeroIdentificacion = @PerOriginal) = 'ACTIVO')  
					OR (select em.empEstadoEmpleador from Empleador em 
						inner join Empresa e on em.empEmpresa = e.empId
						inner join Persona p on e.empPersona = p.perId
						--inner join RolAfiliado on em.empId = roaEmpleador
						where pernumeroidentificacion = @PerOriginal) = 'ACTIVO')
					
	begin
					
	----- A07 EN CASO DE UNA CORRECION QUE DISPARA A08(AX1)
		if (select Top 1 tipoMovimiento from #IC_Aportes_Detcr iad
						inner join sap.IC_Aportes_Det d on iad.asignacion = d.asignacion
						inner join sap.IC_Aportes_Enc e on d.consecutivo = e.consecutivo
						inner join AporteGeneral apg on iad.regId = apg.apgRegistroGeneral
						inner join AporteGeneral apg2 on iad.regIdorg = apg2.apgRegistroGeneral
						inner join AporteDetallado apd2 on apg2.apgId = apdAporteGeneral
						where apg.apgPeriodoAporte <> apg2.apgPeriodoAporte
						and tipoMovimiento in( 'A07','A34') and estadoreg = 'S' and apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO'
						and apg.apgEstadoRegistroAporteAportante = 'REGISTRADO')  in ('A07','A34')
				begin 

				PRINT 'ENTRA EN A07'
						UPDATE iad
						SET iad.adelantado = CASE 
								WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND MONTH(fechaRecaudo) = MONTH(ctr.fechaIngreso) AND CTR.Tipomovimiento = 'A07' AND cc2.apgmarcaperiodo = 'PERIODO_FUTURO' AND cc2.apgTipoSolicitante = 'EMPLEADOR' AND (cc2.apdTarifa = 0.00 OR cc2.apdTarifa = 0.04) THEN 'A1'
								WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND MONTH(fechaRecaudo) = MONTH(ctr.fechaIngreso) AND CTR.Tipomovimiento = 'A07' AND cc2.apgmarcaperiodo = 'PERIODO_FUTURO' AND cc2.apgTipoSolicitante = 'INDEPENDIENTE' AND (cc2.apdTarifa = 0.02 OR cc2.apdTarifa = 0.04)  THEN 'A2'
								WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND MONTH(fechaRecaudo) = MONTH(ctr.fechaIngreso) AND CTR.Tipomovimiento = 'A07' AND cc2.apgmarcaperiodo = 'PERIODO_FUTURO' AND cc2.apgTipoSolicitante = 'PENSIONADO' AND cc2.apdTarifa <> 0.006 THEN 'A3'
								WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND MONTH(fechaRecaudo) = MONTH(ctr.fechaIngreso) AND CTR.Tipomovimiento = 'A07' AND cc2.apgmarcaperiodo = 'PERIODO_FUTURO' AND cc2.apgTipoSolicitante = 'INDEPENDIENTE' AND (cc2.apdTarifa = 0.006 OR cc2.apdTarifa = 0.01) THEN 'A4'
								WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND MONTH(fechaRecaudo) = MONTH(ctr.fechaIngreso) AND CTR.Tipomovimiento = 'A07' AND cc2.apgmarcaperiodo = 'PERIODO_FUTURO' AND cc2.apgTipoSolicitante = 'PENSIONADO' AND cc2.apdTarifa = 0.006 THEN 'A5'
								ELSE iad.adelantado
								END, 
							asignacion = ctr.regNumPlanilla,
							fechaRecaudo = apg.apgFechaRecaudo
						FROM core.dbo.AporteGeneral apg WITH(NOLOCK)
						LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON  apg.apgId = apd.apdAporteGeneral   --- CAMBIO A LEFT CUANDO EL DOCUMENTO NO TRAE LOS DETALLES DE LA TRANSACCION EN AporteDetallado apd
						INNER JOIN #IC_Aportes_Detcr iad WITH(NOLOCK) ON  apg.apgRegistroGeneral = iad.regId 
						LEFT JOIN core.sap.PlanillasCtrl ctr WITH(NOLOCK) ON iad.ref1 = ctr.regNumeroIdentificacionAportante AND iad.tipoDocumento = ctr.regTipoIdentificacionAportante AND iad.asignacion = ctr.regNumPlanilla AND CTR.Tipomovimiento in('A07','A34')
						LEFT JOIN core.sap.MaestraCondicionesCuentas cc2 WITH(NOLOCK) ON CONVERT(VARCHAR, apd.apdTarifa ) = CONVERT(VARCHAR, cc2.apdTarifa) AND apg.apgTipoSolicitante = cc2.apgtiposolicitante AND CC2.Tipomovimiento= 'A08'

						---- En AX1 se actualiza campo de adelantado por el valor del origen. 17/01/2024
						Update #IC_Aportes_Detcr
								set adelantado = adelantadoOri
								from #IC_Aportes_Detcr

						UPDATE #IC_Aportes_Detcr 
						SET	fechaRecaudo = apg.apgFechaRecaudo
						FROM core.dbo.AporteGeneral apg 
						INNER JOIN core.dbo.SolicitudCorreccionAporte sca WITH(NOLOCK) ON  apg.apgId = sca.scaAporteGeneral
						INNER JOIN core.dbo.Solicitud sol WITH(NOLOCK) ON sca.scaSolicitudGlobal = sol.solId 
						INNER JOIN core.dbo.Persona per WITH(NOLOCK) ON sca.scaPersona = per.perId
						WHERE sol.solNumeroRadicacion = @solNumeroRadicacion
						AND @solTipoTransaccion = @solTipoTransaccion
				end
	end	
	else 
	begin
	   	print 'ingresa AX5'
			if (select Distinct tipoMovimiento from #IC_Aportes_Detcr iad
				inner join sap.IC_Aportes_Det d on iad.asignacion = d.asignacion
				inner join sap.IC_Aportes_Enc e on d.consecutivo = e.consecutivo
				inner join AporteGeneral apg on iad.regId = apg.apgRegistroGeneral
				inner join AporteGeneral apg2 on iad.regIdorg = apg2.apgRegistroGeneral
				inner join AporteDetallado apd2 on apg2.apgId = apdAporteGeneral
				where apg.apgPeriodoAporte <> apg2.apgPeriodoAporte
				and tipoMovimiento = 'A05' and estadoreg = 'S' and apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO'
				and apg.apgEstadoRegistroAporteAportante = 'REGISTRADO')  = 'A05'
			begin
			print 'Ingresa update AX5'

			UPDATE #IC_Aportes_Detcr   SET  noIdentificado = CASE WHEN apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO' AND apg2.apgTipoSolicitante = 'EMPLEADOR' AND (apd2.apdTarifa = 0.00 OR apd2.apdTarifa = 0.04 OR apd2.apdTarifa = 0.02 OR apd2.apdTarifa = 0.01 or apd2.apdTarifa = 0.03) THEN 'N1'
															    WHEN apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO' AND apg2.apgTipoSolicitante = 'EMPLEADOR' AND (apd2.apdId IS NULL) THEN 'N1'     
															    WHEN apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO' AND apg2.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd2.apdTarifa = 0.02 OR apd2.apdTarifa = 0.04) THEN 'N2'
															    WHEN apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO' AND apg2.apgTipoSolicitante = 'PENSIONADO' AND apd2.apdTarifa <> 0.006 THEN 'N3'
															    WHEN apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO' AND apg2.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd2.apdTarifa = 0.006 OR apd2.apdTarifa = 0.01) THEN 'N4'
															    WHEN apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO' AND apg2.apgTipoSolicitante = 'PENSIONADO' AND apd2.apdTarifa = 0.006 THEN 'N5'
															    ELSE '' END
														 from #IC_Aportes_Detcr iad
																inner join AporteGeneral apg2 on iad.regIdorg = apg2.apgRegistroGeneral
																inner join AporteDetallado apd2 on apg2.apgId = apdAporteGeneral
																inner join Persona on apd2.apdPersona = perId
																where  apg2.apgEstadoRegistroAporteAportante = 'RELACIONADO' and perNumeroIdentificacion = @PerOriginal
										
				--Select * from #IC_Aportes_Detcr
				end
		end


		
		
			--Select * from #IC_Aportes_Detcr
--		------- actualizacion de fecha y planilla en correcciones  26/09/2022 -----------------------

		UPDATE #IC_Aportes_Detcr 
		SET	fechaRecaudo = apg.apgFechaRecaudo
		FROM core.dbo.AporteGeneral apg 
		INNER JOIN core.dbo.SolicitudCorreccionAporte sca WITH(NOLOCK) ON apg.apgId = sca.scaAporteGeneral
		INNER JOIN core.dbo.Solicitud sol WITH(NOLOCK) ON sca.scaSolicitudGlobal = sol.solId
		INNER JOIN core.dbo.Persona per WITH(NOLOCK) ON sca.scaPersona = per.perId
		INNER JOIN #registrogeneralcr reg ON apg.apgRegistroGeneral = reg.regid 
		WHERE sol.solNumeroRadicacion = @solNumeroRadicacion
		AND @solTipoTransaccion = @solTipoTransaccion

		END



--------------- Actualizacion Estado Aportante en caso de estar Vacio en Afiliado----------------------------------

update iad set iad.apgEstadoAportante = CASE WHEN iad.apgEstadoAportante = '' or iad.apgEstadoAportante is null THEN apg.apgEstadoAportante
		ElSe iad.apgEstadoAportante
		End
from #IC_Aportes_Detcr iad inner join AporteGeneral apg on SUBSTRING(iad.ref2,2,9) = apg.apgId
	
		--Select * from #IC_Aportes_Detcr
-------------------------------------------------Insert Linea 50 ---------------------------------------------------------------			
			
			INSERT INTO #IC_Aportes_Detcr 
				Select 	[consecutivo],[codigoSap],[importeDocumento],[operador],'50' AS [claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
						[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
						[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
						[claseCuenta],[usuario],regId,fechaRecaudo,fechaProcesamiento,EstadoRegistroAporteAportante,FormaReconocimientoAporte,apgEstadoAportante,
						marcaPeriodo,tipoSolicitante,valorIntMora,tarifa,apgOrigenAporte,regIdorg,docorg,[claseDeAporteorg],tipoInteresOrg,operadorinformacion,[noIdentificadoOri]
						,apgIdorg,numdoc,adelantadoOri
						
				FROM #IC_Aportes_Detcr  det											 

			UPDATE #IC_Aportes_Detcr SET	codigoGenesys = apg.apgPersona,	tipo = CASE WHEN tipoDocumento = 'NIT' THEN 'E'	ELSE 'P' END								
				FROM dbo.AporteGeneral  apg
				WHERE apg.apgId = @apgid

 ---------------------------------------------------------------------------------------------
  --Validacion existe Mov base en integracion contable

 -- IF NOT EXISTS
	--( SELECT DISTINCT CC.Tipomovimiento as [Tipo movimiento maestra], enc.tipoMovimiento FROM  #IC_Aportes_Detcr	det
	-- INNER JOIN  core.sap.MaestraCondicionesCuentas CC WITH(NOLOCK) ON EstadoRegistroAporteAportante  = cc.apgEstadoRegistroAporteAportante
	--			AND ISNULL(FormaReconocimientoAporte, '') = ISNULL(cc.apgFormaReconocimientoAporte, '')
	--			AND det.apgEstadoAportante = cc.apgEstadoAportante
	--			AND marcaperiodo = cc.apgmarcaperiodo
	--			AND CONVERT(VARCHAR, Tarifa) = CONVERT(VARCHAR, cc.apdTarifa)
	--			AND tiposolicitante = cc.apgtiposolicitante 
	--			AND CASE WHEN ISNULL(ValorIntMora,0) > 0 THEN '1' ELSE '0' END = cc.tieneinteres
	--			AND det.claveCont = '40'
	--INNER JOIN core.sap.IC_Aportes_Det di WITH(NOLOCK) ON det.asignacion = di.asignacion
	--INNER JOIN core.sap.IC_Aportes_Enc enc WITH(NOLOCK) ON di.consecutivo = enc.consecutivo
	--WHERE CC.Tipomovimiento = enc.tipoMovimiento)

	--BEGIN
	--  DELETE #IC_Aportes_Detcr
	--END
 ---------------------------------------------------------------------------------------------------------------------------


--Select det.apgEstadoAportante from #IC_Aportes_Detcr det
		------------------- inicio Encabezado ---------------------------------------


		INSERT INTO #IC_Aportes_Enccr(
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
			
			CASE 
				-- CORRECCION_APORTES
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND det.noIdentificado in  ('N1','N2','N3','N4','N5')  AND cc.Tipomovimiento = 'A01' THEN 'A03'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND det.adelantado in ('A1','A2','A3','A4','A5')  AND CTR.Tipomovimiento in('A07','A34') AND cc.Tipomovimiento = 'A01' THEN 'A08'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A03' AND claseDeAporteorg <> claseDeAporte THEN 'A12'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A06'  AND det.EstadoRegistroAporteAportante = 'RELACIONADO' THEN 'A06_A12'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A06' AND det.EstadoRegistroAporteAportante = 'REGISTRADO' THEN 'A04'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A07' AND det.EstadoRegistroAporteAportante = 'RELACIONADO' THEN 'A07_A12'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A05'  AND det.EstadoRegistroAporteAportante = 'RELACIONADO' THEN 'A05_A12'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento in( 'A02','A04','A08','A09','A10') THEN 'A12'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND det.apgOrigenAporte = 'CORRECCION_APORTE' AND cc.Tipomovimiento = 'A01' THEN 'A12' -- Cuando es un movimiento A12

				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' THEN ISNULL(cc.Tipomovimiento, 'XXX') -- movimientos definidos en la tabla maestra, que no son devoluciones ni correcciones
				ELSE ISNULL(cc.Tipomovimiento, 'DEV')
			END AS tipomovimiento,

			consecutivo AS consecutivo,
			CASE WHEN MONTH(fechaRecaudo) = MONTH(ctr.fechaIngreso) AND CTR.Tipomovimiento = 'A07' AND cc.Tipomovimiento = 'A01' AND @solNumeroRadicacion <> '' AND @solTipoTransaccion = 'ACTIVO'
					THEN  CONCAT(cc2.apgEstadoRegistroAporteAportante,' / ',cc2.apgFormaReconocimientoAporte,' / ',cc2.apgEstadoAportante,' / ',cc2.apgmarcaperiodo,' / ',cc2.apdTarifa,' / ',cc2.apgTipoSolicitante,' / ',cc2.tieneInteres)
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
			FROM #IC_Aportes_Detcr det WITH(NOLOCK)
			LEFT JOIN core.sap.MaestraCondicionesCuentas cc WITH(NOLOCK) ON EstadoRegistroAporteAportante  = cc.apgEstadoRegistroAporteAportante
				AND ISNULL(FormaReconocimientoAporte, '') = ISNULL(cc.apgFormaReconocimientoAporte, '')
				AND det.apgEstadoAportante = cc.apgEstadoAportante
				AND marcaperiodo = cc.apgmarcaperiodo
				AND CONVERT(VARCHAR, Tarifa) = CONVERT(VARCHAR, cc.apdTarifa)
				AND tiposolicitante = cc.apgtiposolicitante 
				AND CASE WHEN ISNULL(ValorIntMora,0) > 0 THEN '1' ELSE '0' END = cc.tieneinteres
				AND det.claveCont = '40'
			LEFT JOIN core.SAP.PlanillasCtrl ctr WITH(NOLOCK) ON det.ref1 = ctr.regNumeroIdentificacionAportante AND det.tipoDocumento = ctr.regTipoIdentificacionAportante AND CTR.Tipomovimiento in ('A07','A34')
			LEFT JOIN SAP.PlanillasCtrl ctr2 WITH(NOLOCK) ON det.ref1 = ctr2.regNumeroIdentificacionAportante and det.tipoDocumento = ctr2.regTipoIdentificacionAportante and CTR2.Tipomovimiento in ('A05','A06')
			LEFT JOIN core.sap.MaestraCondicionesCuentas cc2 WITH(NOLOCK) ON CONVERT(VARCHAR, Tarifa) = CONVERT(VARCHAR, cc2.apdTarifa) AND tiposolicitante = cc2.apgtiposolicitante AND CC2.Tipomovimiento= 'A08'AND cc2.apgEstadoAportante = 'ACTIVO'
			WHERE cc.Tipomovimiento is not null	
		-------------------------------------------------------

		-- Revisa la cantidad de registros asociados a la planilla y si encuentra un movimiento adicional tipoMovimiento = 'XXX' 
		-- lo elimina y detecta las devoluciones y las correcciones a traves de las novedades.
		-- Elimina los movimientos AX1.

		--Select * from #IC_Aportes_Detcr
		--Select * from #IC_Aportes_Enccr
----------------------------------------------------- Insert Linea 40 para mov con intereses----------------
If (Select apdValorIntMora from AporteDetallado inner join AporteGeneral on apdAporteGeneral = apgid Where apgid = @apgid)>0
			BEGIN 
				INSERT INTO #IC_Aportes_Detcr
	
				--Calcula la linea adicional si el registro tiene intereses
						SELECT 	[consecutivo],[codigoSap],isnull(round (valorIntMora,0),0) as [importeDocumento],[operador],[claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
							[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
							[claseDeAportePrescripcion],1 as [tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
							[claseCuenta],[usuario],regId,fechaRecaudo,fechaProcesamiento,EstadoRegistroAporteAportante,FormaReconocimientoAporte,
							apgEstadoAportante,marcaPeriodo,tipoSolicitante,valorIntMora,tarifa,apgOrigenAporte,regIdorg,docorg,claseDeAporteorg,tipoInteresOrg,operadorinformacion,noIdentificadoOri,apgIdorg,numdoc,adelantadoOri
						FROM #IC_Aportes_Detcr
						WHERE claveCont = '40'
							AND valorintmora > 0
			END


---------------------------------------------------------------------------------
----------------------------------------------------- Insert Linea 50 para mov con intereses----------------
If (Select apdValorIntMora from AporteDetallado inner join AporteGeneral on apdAporteGeneral = apgid Where apgid = @apgid)>0
			BEGIN 
				INSERT INTO #IC_Aportes_Detcr
	
				--Calcula la linea adicional si el registro tiene intereses
						SELECT 	[consecutivo],[codigoSap],isnull(round (valorIntMora,0),0) as [importeDocumento],[operador],[claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
							[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
							[claseDeAportePrescripcion],1 as [tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
							[claseCuenta],[usuario],regId,fechaRecaudo,fechaProcesamiento,EstadoRegistroAporteAportante,FormaReconocimientoAporte,
							apgEstadoAportante,marcaPeriodo,tipoSolicitante,valorIntMora,tarifa,apgOrigenAporte,regIdorg,docorg,claseDeAporteorg,tipoInteresOrg,operadorinformacion,noIdentificadoOri,apgIdorg,numdoc,adelantadoOri
						FROM #IC_Aportes_Detcr
						WHERE claveCont = '50'
							AND valorintmora > 0
			END

		
---------------------------------------------------------------------------------
		DELETE #IC_Aportes_Detcr 
		WHERE consecutivo IN (
			SELECT consecutivo 
			FROM #IC_Aportes_Enccr
			WHERE tipoMovimiento IN ('AX1')
		);

		DELETE #IC_Aportes_Enccr 
		WHERE tipoMovimiento IN ('AX1');
		
		IF (SELECT COUNT(1) FROM #IC_Aportes_Enccr WITH(NOLOCK)) > 1 
			BEGIN
	
				--SELECT COUNT(1) AS 'Registros Encontrados'
				--FROM #IC_Aportes_Detcr				

				DELETE #IC_Aportes_Detcr 
				WHERE consecutivo IN (
					SELECT consecutivo 
					FROM #IC_Aportes_Enccr
					WHERE tipoMovimiento IN ('XXX', 'DEV'));

				DELETE #IC_Aportes_Enccr 
				WHERE tipoMovimiento IN ('XXX', 'DEV');

			END --IF



---------- numero de identificacion de la persona para devolucion para para actualizacion de textoposicion
	declare @identi varchar (12)
		select @identi = (select Distinct numdoc from #IC_Aportes_Detcr where claveCont = '40' )
		Print @identi 
 --------------------------------------------- Actualizacion tercero en clave cont 40 ----------------------------------------------
		
		--select regNumeroIdentificacionAportante,regTipoIdentificacionAportante,regnombreaportante from #registrogeneralcr reg
		--inner join #IC_Aportes_Detcr on regNumPlanilla = asignacion
		--inner join AporteGeneral apg on reg.regId  = apgRegistroGeneral
		--where apg.apgId = @apgid 
		--and claveCont = '40'

		UPDATE #IC_Aportes_Detcr SET ref1 = regNumeroIdentificacionAportante,tipoDocumento = regTipoIdentificacionAportante,ref3 = LEFT (regnombreaportante,20)
		from #registrogeneralcr reg
		inner join #IC_Aportes_Detcr on regNumPlanilla = asignacion
		inner join AporteGeneral apg on reg.regId  = apgRegistroGeneral
		where apg.apgId = @apgid 
		and claveCont = '40'


--------------Actualizacion campo texto posicion ----------------------------------------------------------------------------------------------------------------------------------

		Declare @origen  varchar(16)
		
		------------------------------------------------------------------------------------------------------
		IF(select Distinct tipoSolicitante from #IC_Aportes_Detcr) = 'EMPLEADOR'
			BEGIN
				Select @origen = (select Distinct asignacion from #IC_Aportes_Detcr)
			
				--select @Origen as origen
			
				declare @resultadoE  varchar (max)
				select @resultadoE = (select dbo.ASP_BuscarPlanillasN (@origen))
			
				if (select @resultadoE) is null
					begin
						Update #IC_Aportes_Detcr set textoPosicion = planillaN
						from aporteDetalladoRegistroControlN where asignacion not like '%M%' and planillaAntes = asignacion
					end
					Else 
						begin
						update  #IC_Aportes_Detcr set textoPosicion = (select dbo.ASP_BuscarPlanillasN (@origen)) where asignacion not like '%M%' and @origen = asignacion
						end 
			END
			ELSE
				BEGIN
					Select @origen = (select Distinct asignacion from #IC_Aportes_Detcr)
			
					print @Origen
			
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
							Update #IC_Aportes_Detcr set textoPosicion = planillaN
							from aporteDetalladoRegistroControlN where asignacion not like '%M%' and planillaAntes = asignacion
						end
						Else 
							begin
							update  #IC_Aportes_Detcr set textoPosicion = (select @resultado + '\' + @planillaNadp) where asignacion not like '%M%'
							end 
				END
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		
	--------------------- Actualizacion "BLANQUEO" de Campos -------------------------------------

	--AX5 = A03
		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enccr enc  INNER JOIN #IC_Aportes_Detcr det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A03'
		AND claveCont = '50'

		UPDATE det
		SET identificadorDelBanco = '',
		adelantado = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enccr enc  INNER JOIN #IC_Aportes_Detcr det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A03'
		AND claveCont = '40'

	--AX1-A08
		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enccr enc  INNER JOIN #IC_Aportes_Detcr det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A08','A08_A35')
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
		FROM #IC_Aportes_Enccr enc  INNER JOIN #IC_Aportes_Detcr det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A08','A08_A35')
		AND claveCont = '40'


		---- En AX1 se actualiza en clave cont 50 cuando a parte de cambiar el periodo se cambia tambien el aportante 26/01/2023

						IF (Select DIstinct tipoSolicitante from #IC_Aportes_Detcr) = 'EMPLEADOR'

						BEGIN 
							UPDATE #IC_Aportes_Detcr SET ref1 = per.perNumeroIdentificacion,tipoDocumento=mi.tipoDocumentoCaja,ref3 = LEFT(per.perrazonsocial,20)
									from AporteGeneral apg
									inner join empresa emp WITH(NOLOCK) on apg.apgEmpresa = emp.empId
									inner join persona per WITH(NOLOCK) on emp.empPersona = per.perId
									INNER JOIN core.[sap].[MaestraIdentificacion] mi WITH(NOLOCK) ON per.perTipoIdentificacion = mi.tipoIdGenesys
									INNER JOIN #IC_Aportes_Detcr det WITH(NOLOCK) ON  apg.apgRegistroGeneral = det.regId
									INNER JOIN #IC_Aportes_Enccr enc WITH(NOLOCK) ON det.consecutivo = enc.consecutivo
									where apgid = @apgid and claveCont = '50' AND enc.tipoMovimiento IN ('A08','A08_A35')

						END

						ELSE IF  (Select DIstinct tipoSolicitante from #IC_Aportes_Detcr) = 'INDEPENDIENTE'

							BEGIN 
								UPDATE #IC_Aportes_Detcr SET ref1 = per.perNumeroIdentificacion,tipoDocumento=mi.tipoDocumentoCaja,ref3 = LEFT(per.perrazonsocial,20)
										from AporteGeneral apg
										inner join persona per WITH(NOLOCK) on apg.apgPersona = per.perId
										INNER JOIN core.[sap].[MaestraIdentificacion] mi WITH(NOLOCK) ON per.perTipoIdentificacion = mi.tipoIdGenesys
										INNER JOIN #IC_Aportes_Detcr det WITH(NOLOCK) ON  apg.apgRegistroGeneral = det.regId
										INNER JOIN #IC_Aportes_Enccr enc WITH(NOLOCK) ON det.consecutivo = enc.consecutivo
										where apgid = @apgid and claveCont = '50' AND enc.tipoMovimiento IN ('A08','A08_A35') AND apg.apgEmpresaTramitadoraAporte is null

							END

						ELSE 
							BEGIN
								UPDATE #IC_Aportes_Detcr SET ref1 = perNumeroIdentificacion,tipoDocumento=mi.tipoDocumentoCaja,ref3 = empNombreComercial
								FROM aportegeneral apg
								INNER JOIN Empresa emp WITH(NOLOCK) ON apgEmpresaTramitadoraAporte = empId
								INNER JOIN Persona per WITH(NOLOCK) ON empPersona = perId
								INNER JOIN core.[sap].[MaestraIdentificacion] mi WITH(NOLOCK) ON per.perTipoIdentificacion = mi.tipoIdGenesys
								INNER JOIN #IC_Aportes_Detcr det WITH(NOLOCK) ON  apg.apgRegistroGeneral = det.regId
								INNER JOIN #IC_Aportes_Enccr enc WITH(NOLOCK) ON det.consecutivo = enc.consecutivo
								where apgid = @apgid and claveCont = '50' AND enc.tipoMovimiento IN ('A08','A08_A35')
							END

						

	

	--A12	
	
	----- cuando tiene intereses desde el original
	UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
	--	tieneIntereses = 0,
		tipointeres = tipoInteresOrg,
		tipoMora = ''
	--claseDeAporte = claseDeAporteorg
		FROM #IC_Aportes_Enccr enc INNER JOIN #IC_Aportes_Detcr det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A12'
		AND claveCont = '40'AND round(valorIntMora,0) = importeDocumento
		and tieneIntereses = 1

		--------
		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipointeres = '',
		tipoMora = '',
		importeDocumento = round(importeDocumento - valorIntMora,0),
		claseDeAporte = claseDeAporteorg
		FROM #IC_Aportes_Enccr enc INNER JOIN #IC_Aportes_Detcr det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A12'
		AND claveCont = '40' AND valorIntMora <> importeDocumento
		AND tieneIntereses = 0

		----linea 40 con interes

		--UPDATE det
		--SET noIdentificado = '',
		--adelantado = '',
		--importedocumento = round(valorIntMora,0),
		--identificadorDelBanco = '',
		--codigoBanco = '',
		--transitoria = '',
		--claseDeAporte = '',
		----tieneIntereses = 0,
		----tipointeres = ,
		--tipoMora = ''
		--FROM #IC_Aportes_Enccr enc INNER JOIN #IC_Aportes_Detcr det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		--WHERE enc.tipoMovimiento = 'A12' AND valorIntMora > 0
		--AND claveCont = '40' and tieneIntereses = 1
		--AND valorIntMora < importeDocumento


		

		----------------- linea 50 normal aporte sin intereses
		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipointeres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enccr enc  INNER JOIN #IC_Aportes_Detcr det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A12'
		AND claveCont = '50'
		AND tieneIntereses = 0
		------ linea 50 normal aporte con intereses.

		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipointeres = '',
		tipoMora = '',
		importeDocumento = round(importeDocumento - valorIntMora,0)
		FROM #IC_Aportes_Enccr enc  INNER JOIN #IC_Aportes_Detcr det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A12'AND valorIntMora > 0
		AND claveCont = '50' and importeDocumento > valorIntMora
		AND tieneIntereses = 0

		------ linea 50 de intereses.

		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		--tieneIntereses = 0,
		--tipointeres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enccr enc INNER JOIN #IC_Aportes_Detcr det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A12' AND valorIntMora > 0
		AND claveCont = '50' and tieneIntereses > 0
		AND tieneIntereses = 1




		--A05_A12		
		
		UPDATE det
		SET adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipointeres = '',
		tipoMora = '',
		importeDocumento = round(importeDocumento - valorIntMora,0),
		noIdentificado = noIdentificadoOri
		FROM #IC_Aportes_Enccr enc INNER JOIN #IC_Aportes_Detcr det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A05_A12'
		AND claveCont = '40' AND valorIntMora <> importeDocumento

		----------------- linea 50 normal aporte sin intereses
		UPDATE det
		SET
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipointeres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enccr enc  INNER JOIN #IC_Aportes_Detcr det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A05_A12'AND valorIntMora = 0
		AND claveCont = '50'


		UPDATE enc
		SET tipoMovimiento = 'A12'
		FROM #IC_Aportes_Enccr enc INNER JOIN #IC_Aportes_Detcr det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A05_A12' 

		----------A06_A12-----------------------------

		--A06_A12  lineas 40 
		
		UPDATE det
		SET adelantado = '',
		noIdentificado = noIdentificadoOri,
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipointeres = '',
		tipoMora = '',
		importeDocumento = round(importeDocumento - valorIntMora,0),
		claseDeAporte = '',
		claseDeAportePrescripcion = ''
		FROM #IC_Aportes_Enccr enc INNER JOIN #IC_Aportes_Detcr det WITH(NOLOCK)  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A06_A12'
		AND claveCont = '40' AND valorIntMora <> importeDocumento
		and tieneIntereses = 0
	
		------------ 40 con interes

		UPDATE det
		SET 
		noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
	--	tieneIntereses = 0,
		tipointeres = tipoInteresOrg,
		tipoMora = ''
	--claseDeAporte = claseDeAporteorg
		FROM #IC_Aportes_Enccr enc INNER JOIN #IC_Aportes_Detcr det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A06_A12'
		AND claveCont = '40'AND round(valorIntMora,0) = importeDocumento
		and tieneIntereses = 1


	------ linea 50 normal aporte sin intereses.

		UPDATE det
		SET adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipointeres = '',
		tipoMora = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		importeDocumento = round(importeDocumento - valorIntMora,0)
		FROM #IC_Aportes_Enccr enc  INNER JOIN #IC_Aportes_Detcr det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A06_A12'AND valorIntMora = 0
		AND claveCont = '50'
		and tieneIntereses = 0

		-------- linea 50 normal(importe normal) aporte con intereses.

		UPDATE det
		SET adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipointeres = '',
		tipoMora = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		importeDocumento = round(importeDocumento - valorIntMora,0)
		FROM #IC_Aportes_Enccr enc  INNER JOIN #IC_Aportes_Detcr det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A06_A12'AND valorIntMora > 0
		AND claveCont = '50' and importeDocumento > valorIntMora
		and tieneIntereses = 0
		
		----- linea 50 con intereses (importe es intereses)
		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		--tieneIntereses = 0,
		--tipointeres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enccr enc INNER JOIN #IC_Aportes_Detcr det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A06_A12' AND valorIntMora > 0
		AND claveCont = '50' and tieneIntereses = 1


		--UPDATE det
		--SET operador = '',
		--clavecont = '40',
		--noIdentificado = '',
		--adelantado = '',
		--identificadorDelBanco = '',
		--codigoBanco = '',
		--transitoria = '',
		--claseDeAporte = '',
		--claseDeAportePrescripcion = '',
		----tieneIntereses = 0,
		----tipointeres = '',
		--tipoMora = ''
		--FROM #IC_Aportes_Enccr enc INNER JOIN #IC_Aportes_Detcr det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		--WHERE enc.tipoMovimiento = 'A06_A12' AND valorIntMora > 0
		--AND claveCont = '50' AND operador = 1

		UPDATE enc
		SET tipoMovimiento = 'A12'
		FROM #IC_Aportes_Enccr enc INNER JOIN #IC_Aportes_Detcr det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A06_A12' 


		----------------------- A07_A12 ------------------------------------------------

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
		FROM #IC_Aportes_Enccr enc INNER JOIN #IC_Aportes_Detcr det ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A07_A12')
		AND claveCont = '50'

		UPDATE det
		SET noIdentificado = '',
		adelantado = det.adelantadoOri,
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enccr enc INNER JOIN #IC_Aportes_Detcr det ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A07_A12')
		AND claveCont = '40'


		UPDATE enc
		SET tipoMovimiento = 'A12'
		FROM #IC_Aportes_Enccr enc INNER JOIN #IC_Aportes_Detcr det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A07_A12' 



--------------------------------------- Actualizacion de valores cuando es empleador con mas de 1 cotizante -----------------------------------------------------

		IF (@tipoSolicitante ='EMPLEADOR'AND @solTipoTransaccion = 'CORRECCION_APORTES' And @solNumeroRadicacion is not null and @apgid is not null)
			Begin



			Declare @aporte numeric (10)
			Declare @inter numeric (10)

			
				select @aporte = SUM(apgValTotalApoObligatorio), @inter = SUM(apgValorIntMora) from solicitud
				inner join SolicitudCorreccionAporte on solid = scaSolicitudGlobal
				inner join Correccion on scaid  = corSolicitudCorreccionAporte
				inner join AporteGeneral on corAporteGeneral = apgid
				inner join AporteDetallado on apgid = apdaportegeneral
				inner join Persona on apdPersona = perId
				where solNumeroRadicacion = @solNumeroRadicacion

				Update #IC_Aportes_Detcr set importeDocumento = @aporte where tieneIntereses = 0
				Update #IC_Aportes_Detcr set importeDocumento = @inter where tieneIntereses = 1
			END

-------------------------------------------------------------------------------------------------------------------------------------------			


-------------- Asignacion y actualizacion del campo Referencia--------------------------------------			
	
		SELECT @referenciaNum = valorActual 
			FROM sap.ic_referencia
			WHERE comentario ='A'
			AND estado = 'A';

			UPDATE #IC_Aportes_Enccr
				SET referencia =  @referenciaNum
	
	--comentarear referencia

		UPDATE sap.ic_referencia
			SET valorActual = @referenciaNum + 1
			WHERE comentario ='A'
			AND estado = 'A';		

 --fin comentario



--comentarear

	IF ((SELECT referencia FROM #IC_Aportes_Enccr ) IS NOT NULL )
	BEGIN


		IF NOT EXISTS (SELECT acre.codigoSAP FROM [sap].[CodSAPGenesysAcreedor] acre INNER JOIN Persona per ON acre.codigoGenesys = per.perId WHERE per.perNumeroIdentificacion = @numeroDocumento) BEGIN
			EXEC [sap].[USP_GetAcreedoresAportes_DocumentoId] @numeroDocumento, @tipoDocumento, @usuario, @planilla
		END
	

	
		IF EXISTS (SELECT enca.consecutivo FROM #IC_Aportes_Enccr enca INNER JOIN #IC_Aportes_Detcr deta on enca.consecutivo = deta.consecutivo where deta.importeDocumento <> 0)
		BEGIN
	      
			INSERT INTO sap.IC_Aportes_Enc(
				[fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[observacion],
				[moneda],[documentoContable],[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario])
			SELECT distinct [fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[observacion],
				[moneda],[documentoContable],[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario]
			FROM #IC_Aportes_Enccr WITH(NOLOCK) ; 
         
				-- Actualizar el consecutivo definitivo en la tabla de integracion de encabezado con el detalle
				SELECT @consecutivo=MAX(consecutivo) FROM IC_Aportes_Enc

				UPDATE #IC_Aportes_Detcr
				SET consecutivo = @consecutivo

			INSERT INTO sap.IC_Aportes_Det([consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
					[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
					[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
					[claseCuenta],[usuario],[fechaEjecucion])
			SELECT [consecutivo],[codigoSap],CAST(CAST([importeDocumento] AS BIGINT) AS VARCHAR(18)),[operador],[claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
					[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
					[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
					[claseCuenta],[usuario],GETDATE()-'05:00:00'
			FROM #IC_Aportes_Detcr  WITH(NOLOCK)
			WHERE importeDocumento <> 0  ---  para cuando el registro viene sin detalle y que no traiga lineas en valores 0 08/10/2022
		
			UPDATE  #IC_Aportes_Detcr
				SET consecutivo =(SELECT consecutivo FROM #IC_Aportes_Enccr)
		
			-- INSERCIoN EN LA TABLA DE CONTROL DE PLANILLAS
 
 
			INSERT INTO sap.PlanillasCtrl
			SELECT DISTINCT LEFT(det.asignacion, 12), 1, det.ref1, det.tipoDocumento, SAP.GetLocalDate(), 'APORTES', det.ref3 + enc.observacion, enc.tipoMovimiento,estadoaporteaportantetercero,operadorinformacion,@apgid 
			FROM #IC_Aportes_Detcr det WITH(NOLOCK)
			INNER JOIN #IC_Aportes_Enccr enc WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
			WHERE det.EstadoRegistroAporteAportante is not null


			-- Insercion en la tabla de control de solicitudes Devoluciones
			INSERT INTO sap.ContablesCtrl
			SELECT DISTINCT @solNumeroRadicacion, 1 as estado, perNumeroIdentificacion, perTipoIdentificacion,SAP.GetLocalDate(),'APORTES',solTipoTransaccion 
			FROM solicitud sol WITH(NOLOCK)
			INNER JOIN  SolicitudDevolucionAporte sda WITH(NOLOCK) ON sda.sdaSolicitudGlobal = sol.solId
			INNER JOIN Persona per WITH(NOLOCK) on sda.sdaPersona = per.perId where solNumeroRadicacion = @solNumeroRadicacion and solTipoTransaccion = 'DEVOLUCION_APORTES'


			-- Insercion en la tabla de control de solicitudes Correcciones
			INSERT INTO sap.ContablesCtrl
			SELECT DISTINCT solNumeroRadicacion, 1 as estado, perNumeroIdentificacion, perTipoIdentificacion,SAP.GetLocalDate(),'APORTES',solTipoTransaccion 
			FROM Solicitud sol WITH(NOLOCK)
			INNER JOIN  SolicitudCorreccionAporte sca WITH(NOLOCK) ON sca.scaSolicitudGlobal = sol.solId
			INNER JOIN Persona per WITH(NOLOCK) on sca.scaPersona = per.perId where solNumeroRadicacion = @solNumeroRadicacion and solTipoTransaccion = 'CORRECCION_APORTES'
	
	
	
		
		END
	

	END

-- Fin Comentario

			--Detalle
			SELECT DISTINCT * FROM #IC_Aportes_Enccr
			ORDER BY tipoMovimiento, consecutivo

			SELECT * FROM #IC_Aportes_Detcr WITH(NOLOCK)
			WHERE importeDocumento <> 0  ---  para cuando el registro viene sin detalle y que no traiga lineas en valores 0 08/10/2022
			ORDER BY claveCont

			DROP TABLE IF EXISTS #IC_Aportes_Detcr;	
			DROP TABLE IF EXISTS #IC_Aportes_Enccr;					
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

	--	DELETE FROM sap.ejecucion_aportes2
	--END
	--ELSE BEGIN
	--	SELECT 'El proceso ya se encuentra en ejecucion'
	--END



		--DELETE FROM sap.ejecucion_aportes2 
		--	END
		--	ELSE BEGIN
		--	SELECT 'El proceso ya se encuentra en ejecucion'
		--	END