CREATE OR ALTER PROCEDURE [sap].[USP_GetICAPORTES_Insert_A35] 	 @planilla VARCHAR(20),	@planillaN VARCHAR(20), @apgid BIGINT,	@solNumeroRadicacion VARCHAR (30),	@solTipoTransaccion VARCHAR (30),@regidO INT, @regidN INT
AS
BEGIN
SET NOCOUNT ON; 

--DECLARE @planilla VARCHAR(20),	@planillaN VARCHAR(20), @apgid BIGINT,	@solNumeroRadicacion VARCHAR (30),	@solTipoTransaccion VARCHAR (30),@regidO INT, @regidN INT
DECLARE @referenciaNum BIGINT, @consecutivo BIGINT, @PerNumD VARCHAR(16), @perTipD VARCHAR(10), @apgOr BIGINT, @operadorinformacion  INT


CREATE TABLE #IC_Aportes_Det
	(
		[consecutivo] [bigint] NOT NULL,
		[codigoSap] [varchar](10) NULL,
		[importeDocumento] NUMERIC(21,5) NOT NULL,
		[operador] [varchar](1) NULL,
		[claveCont] [varchar](2) NOT NULL,
		[asignacion] [varchar](18) NOT NULL,
		[textoPosicion] [varchar] (50) Null,
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
		regId bigint NULL,
		[fechaRecaudo] [date] NULL,
		[fechaProcesamiento] [date] NULL,
		[EstadoRegistroAporteAportante] [varchar](30) NULL,
		[TipoSolicitante][VARCHAR](30) NULL,
		[interes]numeric NULL,
		[Tarifa]numeric (7,5) NULL,
		[operadorinformacion] INT NULL,
		[apgid] VARCHAR (20),
		[EstadoAporte] varchar(50) NULL,
		[MarcaPeriodo] varchar(20) NULL,
		[FormaReconocimiento] varchar(50) NULL,
		[TipoCotizante] varchar(50) NULL			
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

		
	CREATE TABLE #registroO
	( 
		regid VARCHAR(20),
		regTipoPlanilla VARCHAR(10),
		regNumPlanilla VARCHAR(30),
		regTipoIdentificacionAportante VARCHAR(20),
		regNumeroIdentificacionAportante VARCHAR(20),
		regNombreAportante VARCHAR(40)
	
	)

	/*
	create table #registrogeneralA35
	(
	regid bigint,
	regNumPlanilla varchar(12),
	regOperadorInformacion bigint,
	regValTotalApoObligatorio numeric,
	regValorIntMora numeric,
	regTipoIdentificacionAportante varchar(20),
	regNumeroIdentificacionAportante varchar(16),
	regTipoPlanilla varchar(1),
	regDateTimeInsert datetime,
	regOUTEstadoArchivo varchar(60),
	regNumPlanillaAsociada varchar(12),
	regNombreAportante varchar(100)
	
	)
	*/

BEGIN TRY 
	BEGIN TRANSACTION


	--SET @solNumeroRadicacion = '032023115012'
	--SET @regidO = '2390606'
	--SET @regidN = '2390612'

	--set @solNumeroRadicacion = '032023114958'
	--SET @regidO ='2375924'
	--SET @regidN ='2390595'


	--INSERT 
	SELECT regid, regNumPlanilla, regOperadorInformacion,regValTotalApoObligatorio, regValorIntMora,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
	regTipoPlanilla,regDateTimeInsert,regOUTEstadoArchivo,regNumPlanillaAsociada,regNombreAportante
	INTO  #registrogeneralA35
	FROM core.pila.RegistroGeneral	
	WHERE regid = @regidO

	SET @planilla = (SELECT TOP 1 regnumplanilla FROM #registrogeneralA35 WHERE regId = @regidO AND regOUTEstadoArchivo = 'RECAUDO_NOTIFICADO' )
	SET @operadorinformacion = (SELECT TOP 1 regOperadorInformacion FROM #registrogeneralA35 WHERE regId = @regidO AND regOUTEstadoArchivo = 'RECAUDO_NOTIFICADO')


	SET @apgid = (Select apg.apgId FROM #registrogeneralA35 reg
					INNER JOIN core.[sap].[MaestraIdentificacion] mi WITH(NOLOCK) ON reg.regTipoIdentificacionAportante = mi.tipoIdGenesys
					INNER JOIN core.dbo.AporteGeneral apg WITH(NOLOCK) ON reg.regId = apg.apgRegistroGeneral  
					INNER JOIN core.dbo.SolicitudCorreccionAporte sca WITH(NOLOCK) ON  apg.apgId = sca.scaAporteGeneral 
					INNER JOIN core.dbo.Solicitud sol WITH(NOLOCK) ON sca.scaSolicitudGlobal = sol.solId
					INNER JOIN core.dbo.Correccion cor WITH(NOLOCK) ON sca.scaid = cor.corsolicitudcorreccionaporte
					INNER JOIN core.dbo.AporteGeneral apg2 WITH(NOLOCK) ON  cor.corAporteGeneral=apg2.apgid
					WHERE regId = @regidO AND apg2.apgRegistroGeneral = @regidN)


	--- INSERT TABLA REGISTRO GENERAL, SOLO TIPO Y TERCERO PAGADOR, DEACUERDO AL PARAMETRO ENVIADO @regid
	IF @planilla IS NULL OR @planilla = ''
	BEGIN
		INSERT INTO #registroO
		SELECT regid, regTipoPlanilla, regNumPlanilla, regTipoIdentificacionAportante, regNumeroIdentificacionAportante, regNombreAportante
		FROM pila.RegistroGeneral WHERE   (regId = @regido OR regnumplanilla = @planilla) 
	END
	ELSE
	BEGIN
		INSERT INTO #registroO
		SELECT regid, regTipoPlanilla, regNumPlanilla, regTipoIdentificacionAportante, regNumeroIdentificacionAportante, regNombreAportante
		FROM pila.RegistroGeneral WHERE   (regId = @regido OR regnumplanilla = @planilla) AND regOUTEstadoArchivo = 'RECAUDO_NOTIFICADO'
	END

	SELECT regid, regTipoPlanilla, regNumPlanilla, regTipoIdentificacionAportante, regNumeroIdentificacionAportante, regNombreAportante
	INTO #registroN
	FROM pila.RegistroGeneral WHERE  regId = @regidN





	-- CALCULO PERIODO----


	--ANTIGUO 
	INSERT INTO #IC_Aportes_Det
	SELECT 
	ROW_NUMBER() OVER(ORDER BY apd.apdId ASC) AS consecutivo,''
	AS codigoSap,
	apd.apdAporteObligatorio  AS ImporteDocumento,
	'' AS operador,			-- Solo aplica para la INTEGRACION DE CARTERA
	'40' AS claveCont,
	case when @planilla is null then '' else @planilla end AS asignacion,
	'' as textoPosicion,
	per.perNumeroIdentificacion AS ref1,
	'' AS ref2,
	mai.TipoIdHomologado AS tipoDocumento,
	LEFT(per.perRazonSocial, 20) AS ref3,
	CASE WHEN apg.apgEstadoRegistroAporteAportante  = 'RELACIONADO' AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04 OR apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.01 or apd.apdTarifa = 0.03) THEN 'N1'
		 WHEN apg.apgEstadoRegistroAporteAportante  = 'RELACIONADO' AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'N1'  
		 WHEN apg.apgEstadoRegistroAporteAportante  = 'RELACIONADO' AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'N2'
		 WHEN apg.apgEstadoRegistroAporteAportante  = 'RELACIONADO' AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'N3'
		 WHEN apg.apgEstadoRegistroAporteAportante  = 'RELACIONADO' AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'N4'
		 WHEN apg.apgEstadoRegistroAporteAportante  = 'RELACIONADO' AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'N5'
		ELSE '' END  noIdentificado,

	CASE WHEN CONCAT (apgperiodoaporte,'-01') > apgFechaProcesamiento AND apg.apgEstadoRegistroAporteAportante ='RELACIONADO' AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04 OR apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.01 or apd.apdTarifa = 0.03) THEN 'A1'
		 WHEN CONCAT (apgperiodoaporte,'-01') > apgFechaProcesamiento AND apg.apgEstadoRegistroAporteAportante ='RELACIONADO' AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'A1'  
		 WHEN CONCAT (apgperiodoaporte,'-01') > apgFechaProcesamiento AND apg.apgEstadoRegistroAporteAportante ='RELACIONADO' AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'A2'
		 WHEN CONCAT (apgperiodoaporte,'-01') > apgFechaProcesamiento AND apg.apgEstadoRegistroAporteAportante ='RELACIONADO' AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'A3'
		 WHEN CONCAT (apgperiodoaporte,'-01') > apgFechaProcesamiento AND apg.apgEstadoRegistroAporteAportante ='RELACIONADO' AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'A4'
		 WHEN CONCAT (apgperiodoaporte,'-01') > apgFechaProcesamiento AND apg.apgEstadoRegistroAporteAportante ='RELACIONADO' AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'A5'
	ELSE '' END AS adelantado,
	'' AS identificadorDelBanco,
	'' AS codigoBanco,
	'' AS transitoria,
	CASE WHEN apg.apgEstadoRegistroAporteAportante in ('REGISTRADO','OTROS_INGRESOS') AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04)  THEN 'CA1'
		 WHEN apg.apgEstadoRegistroAporteAportante in ('REGISTRADO','OTROS_INGRESOS') AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null)  THEN 'CA1' ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
		 WHEN apg.apgEstadoRegistroAporteAportante in ('REGISTRADO','OTROS_INGRESOS') AND apg.apgTipoSolicitante = 'EMPLEADOR' AND apd.apdTarifa < 0.04 THEN 'CA1'
		 WHEN apg.apgEstadoRegistroAporteAportante ='REGISTRADO' AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'CA2'
		 WHEN apg.apgEstadoRegistroAporteAportante ='REGISTRADO' AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'CA3'
		 WHEN apg.apgEstadoRegistroAporteAportante ='REGISTRADO' AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'CA4'
		 WHEN apg.apgEstadoRegistroAporteAportante ='REGISTRADO' AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'CA5'
		 ELSE ''	END AS claseDeAporte,
	CASE WHEN apg.apgFormaReconocimientoAporte = 'RECONOCIMIENTO_RETROACTIVO_MANUAL' THEN '1' 
		 ELSE '2' END AS claseDeAportePrescripcion,
	CASE WHEN apg.apgValorIntMora > 0 THEN 1 
		 ELSE 0 END AS tieneIntereses,
		 ''	 AS tipoInteres,		 	
		 0 AS correccion,			-- Se define con los registros en la tabla Movimientoaporte en moaTipoAjuste = ''CORRECCION_A_LA_BAJA' va a ser anulacion.
		 '' AS tipoMora,			-- Solo aplica para la INTEGRACIoN DE CARTERA
		 0 AS id,
		 perId AS codigoGenesys,
		 CASE WHEN per.perTipoIdentificacion = 'NIT' THEN 'E'	ELSE 'P' END AS tipo,
		 'S' AS claseCuenta,
		 'soportetecnico@asopagos.com' AS usuario,
		 apg.apgRegistroGeneral as regId,
		 apg.apgFechaRecaudo AS fechaRecaudo, 
		 CASE WHEN [SAP].[calcularPrimerDiaHabilMes]() = [SAP].[calcularSiguienteDiaHabil]() AND DAY(DATEADD(HOUR,-5,GETDATE())) >= [SAP].[calcularPrimerDiaHabilMes]() AND DATEDIFF(DAY, apg.apgFechaRecaudo, DATEADD(HOUR,-5,GETDATE())) > 0
		 	THEN EOMONTH(DATEADD(HOUR,-5,GETDATE()),-1)
		 	ELSE DATEADD(HOUR,-5,GETDATE()) END AS fechaProcesamiento,
		 apg.apgEstadoRegistroAporteAportante AS EstadoRegistroAporteAportante,
		 apg.apgTipoSolicitante AS TipoSolicitante,
		 apdValorIntMora AS interes, 
		 apd.apdTarifa AS Tarifa,
		 0 AS operadorinformacion,
		 apg.apgId AS apgid,
		apgEstadoAportante as [EstadoAporte],
		apgMarcaPeriodo  as [MarcaPeriodo] ,
		apgFormaReconocimientoAporte  as [FormaReconocimiento] ,
		apd.apdTipoCotizante as [TipoCotizante]
	FROM Solicitud sol 
	INNER JOIN SolicitudCorreccionAporte sca ON sol.solId =scaSolicitudGlobal AND scaResultadoSupervisor ='APROBADA'
	INNER JOIN AporteGeneral apg  ON scaAporteGeneral = apg.apgId
	INNER JOIN Correccion cor ON sca.scaId = cor.corSolicitudCorreccionAporte 
	INNER JOIN AporteDetallado apd ON cor.corAporteDetallado = apd.apdId AND apd.apdEstadoAporteAjuste = 'ANULADO'
	INNER JOIN Persona per ON  apd.apdPersona =  per.perId
	INNER JOIN core.sap.MaestraTiposIdentificacion mai ON per.perTipoIdentificacion = mai.TipoIdGenesys
	LEFT JOIN sap.PlanillasCtrl ctr ON @planilla = ctr.regNumPlanilla and per.perNumeroIdentificacion =  ctr.regNumeroIdentificacionAportante AND ctr.Tipomovimiento = 'A35'
	LEFT JOIN sap.PlanillasCtrl ctr1 ON  concat('M', @apgid) = ctr1.regNumPlanilla and per.perNumeroIdentificacion =  ctr1.regNumeroIdentificacionAportante
	AND ctr1.Tipomovimiento = 'A35'
	WHERE solNumeroRadicacion = @solNumeroRadicacion
	AND ctr.regNumPlanilla IS NULL --or  ctr1.regNumPlanilla IS NULL
	GROUP BY apd.apdAporteObligatorio, apg.apgValTotalApoObligatorio,per.perNumeroIdentificacion, mai.TipoIdHomologado, per.perRazonSocial,per.perTipoIdentificacion,apg.apgEstadoRegistroAporteAportante,apg.apgTipoSolicitante, apd.apdTarifa, apdId,
	per.perId,apgperiodoaporte, apg.apgFormaReconocimientoAporte,apg.apgValorIntMora, apg.apgRegistroGeneral, apg.apgFechaRecaudo, apd.apdTarifa, apg.apgId, apdValorIntMora ,
	apgMarcaPeriodo,apgFormaReconocimientoAporte,apgFormaReconocimientoAporte,apdTipoCotizante, apgEstadoAportante,apgFechaProcesamiento
	
	
	--podria actualizar los campos filtrando con  regid insertar en  una tabla temporal 
	---NUEVO
	INSERT INTO #IC_Aportes_Det
	SELECT 
	ROW_NUMBER() OVER(ORDER BY apd.apdId ASC) AS consecutivo,''
	AS codigoSap,
	apg.apgValTotalApoObligatorio  AS ImporteDocumento,
	'' AS operador,			-- Solo aplica para la INTEGRACIoN DE CARTERA
	'50' AS claveCont,
	case when @planilla is null then '' else @planilla end  AS asignacion,
	'' as textoPosicion,
	per.perNumeroIdentificacion AS ref1,
	'' AS ref2,
	mai.TipoIdHomologado AS tipoDocumento,
	LEFT(per.perRazonSocial, 20) AS ref3,
	CASE WHEN apg.apgEstadoRegistroAporteAportante  = 'RELACIONADO'  AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04 OR apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.01 or apd.apdTarifa = 0.03) THEN 'N1'
		 WHEN apg.apgEstadoRegistroAporteAportante  = 'RELACIONADO'  AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'N1'  
		 WHEN apg.apgEstadoRegistroAporteAportante  = 'RELACIONADO'  AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'N2'
		 WHEN apg.apgEstadoRegistroAporteAportante  = 'RELACIONADO'  AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'N3'
		 WHEN apg.apgEstadoRegistroAporteAportante  = 'RELACIONADO'  AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'N4'
		 WHEN apg.apgEstadoRegistroAporteAportante  = 'RELACIONADO'  AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'N5'
		ELSE '' END  noIdentificado,

	CASE WHEN CONCAT (apgperiodoaporte,'-01') > apgFechaProcesamiento AND apg.apgEstadoRegistroAporteAportante ='RELACIONADO' AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04 OR apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.01 or apd.apdTarifa = 0.03) THEN 'A1'
		 WHEN CONCAT (apgperiodoaporte,'-01') > apgFechaProcesamiento AND apg.apgEstadoRegistroAporteAportante ='RELACIONADO' AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'A1'  
		 WHEN CONCAT (apgperiodoaporte,'-01') > apgFechaProcesamiento AND apg.apgEstadoRegistroAporteAportante ='RELACIONADO' AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'A2'
		 WHEN CONCAT (apgperiodoaporte,'-01') > apgFechaProcesamiento AND apg.apgEstadoRegistroAporteAportante ='RELACIONADO' AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'A3'
		 WHEN CONCAT (apgperiodoaporte,'-01') > apgFechaProcesamiento AND apg.apgEstadoRegistroAporteAportante ='RELACIONADO' AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'A4'
		 WHEN CONCAT (apgperiodoaporte,'-01') > apgFechaProcesamiento AND apg.apgEstadoRegistroAporteAportante ='RELACIONADO' AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'A5'
	ELSE '' END AS adelantado,
	'' AS identificadorDelBanco,
	'' AS codigoBanco,
	'' AS transitoria,
	CASE WHEN apg.apgEstadoRegistroAporteAportante in ('REGISTRADO','OTROS_INGRESOS') AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04)  THEN 'CA1'
		 WHEN apg.apgEstadoRegistroAporteAportante in ('REGISTRADO','OTROS_INGRESOS') AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null)  THEN 'CA1' ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
		 WHEN apg.apgEstadoRegistroAporteAportante in ('REGISTRADO','OTROS_INGRESOS') AND apg.apgTipoSolicitante = 'EMPLEADOR' AND apd.apdTarifa < 0.04 THEN 'CA1'
		 WHEN apg.apgEstadoRegistroAporteAportante ='REGISTRADO' AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'CA2'
		 WHEN apg.apgEstadoRegistroAporteAportante ='REGISTRADO' AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'CA3'
		 WHEN apg.apgEstadoRegistroAporteAportante ='REGISTRADO' AND apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'CA4'
		 WHEN apg.apgEstadoRegistroAporteAportante ='REGISTRADO' AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'CA5'
		 ELSE ''	END AS claseDeAporte,
	CASE WHEN apg.apgFormaReconocimientoAporte = 'RECONOCIMIENTO_RETROACTIVO_MANUAL' THEN '1' 
		 ELSE '2' END AS claseDeAportePrescripcion,
		CASE WHEN apg.apgValorIntMora > 0 THEN 1 
		 ELSE 0 END  AS tieneIntereses,
	'' AS tipoInteres,		 	
		 0 AS correccion,			-- Se define con los registros en la tabla Movimientoaporte en moaTipoAjuste = ''CORRECCION_A_LA_BAJA' va a ser anulacion.
		 '' AS tipoMora,				-- Solo aplica para la INTEGRACIoN DE CARTERA
		 0 AS id,
		 perId AS codigoGenesys,
		CASE WHEN per.perTipoIdentificacion = 'NIT' THEN 'E'	ELSE 'P' END AS tipo,
		 'S' AS claseCuenta,
		 solUsuarioRadicacion AS usuario,
		 apg.apgRegistroGeneral as regId,
		 apg.apgFechaRecaudo AS fechaRecaudo, 
		 CASE WHEN [SAP].[calcularPrimerDiaHabilMes]() = [SAP].[calcularSiguienteDiaHabil]() AND DAY(DATEADD(HOUR,-5,GETDATE())) >= [SAP].[calcularPrimerDiaHabilMes]() AND DATEDIFF(DAY, apg.apgFechaRecaudo, DATEADD(HOUR,-5,GETDATE())) > 0
		 	THEN EOMONTH(DATEADD(HOUR,-5,GETDATE()),-1)
		 	ELSE DATEADD(HOUR,-5,GETDATE()) END AS fechaProcesamiento,
		 apg.apgEstadoRegistroAporteAportante AS EstadoRegistroAporteAportante,
		 apg.apgTipoSolicitante AS TipoSolicitante,
		 apdValorIntMora AS interes, 
		 apd.apdTarifa AS Tarifa,
		 0 AS operadorinformacion,
		 apg.apgId AS apgid,
		 '' as [EstadoAporte],
		''  as [MarcaPeriodo] ,
		''  as [FormaReconocimiento] ,
		'' as [TipoCotizante]
	FROM Solicitud S
	INNER JOIN SolicitudCorreccionAporte  C  ON S.solId = C.scaSolicitudGlobal
	INNER JOIN core.dbo.Correccion cor WITH(NOLOCK) ON C.scaid = cor.corsolicitudcorreccionaporte
	INNER JOIN AporteGeneral apg ON cor.corAporteGeneral =  apg.apgid
	INNER JOIN AporteDetallado apd ON apg.apgId = apd.apdAporteGeneral
	INNER JOIN Persona per ON apd.apdPersona = per.perId 
	INNER JOIN core.sap.MaestraTiposIdentificacion mai ON per.perTipoIdentificacion = mai.TipoIdGenesys
	WHERE solNumeroRadicacion = @solNumeroRadicacion
	GROUP BY apg.apgValTotalApoObligatorio, per.perNumeroIdentificacion, mai.TipoIdHomologado, per.perRazonSocial, per.perTipoIdentificacion, apg.apgEstadoRegistroAporteAportante,apg.apgTipoSolicitante, apd.apdTarifa, apdId,
	 per.perId, apg.apgFormaReconocimientoAporte,apg.apgValorIntMora, apg.apgRegistroGeneral, apdValorIntMora, apg.apgFechaRecaudo, apd.apdTarifa, apg.apgId ,solUsuarioRadicacion,
	 apgperiodoaporte,apgFechaProcesamiento

	-- select * from #IC_Aportes_Det


--- Agrupamiento mas de un afiliado en una solictud de coreccion ------------------------------------
	UPDATE #IC_Aportes_Det set importeDocumento =(SELECT SUM(importeDocumento) FROM #IC_Aportes_Det)
					FROM #IC_Aportes_Det WHERE consecutivo = 1 

	DECLARE @Interes int =(SELECT SUM(interes) FROM #IC_Aportes_Det)
					
	DELETE FROM #IC_Aportes_Det WHERE  consecutivo > 1
-------------------------------------------------------------------------------------------------------

----Cambio de estado para calcular mov Original -------------------------------------------------------

		update #IC_Aportes_Det set [EstadoAporte] = (
		case when EstadoAporte is null then (
			select roaEstadoAfiliado from #IC_Aportes_Det
			LEFT JOIN dbo.Afiliado afi ON codigoGenesys = afi.afiPersona
			LEFT JOIN dbo.RolAfiliado roa ON  afi.afiId = roa.roaAfiliado  AND TipoCotizante = roa.roaTipoAfiliado
			where roaFechaRetiro is null and claveCont = '40'
			and (select max(roaFechaAfiliacion)
				FROM RolAfiliado
				INNER JOIN Afiliado ON roaAfiliado = afiId  
				INNER JOIN #IC_Aportes_Det ON afiPersona = codigoGenesys
				where roaEstadoAfiliado = 'ACTIVO')   > 
				(select max(roaFechaRetiro)  
				FROM RolAfiliado
				INNER JOIN Afiliado ON roaAfiliado = afiId  
				INNER JOIN #IC_Aportes_Det ON afiPersona = codigoGenesys
				where roaEstadoAfiliado = 'INACTIVO'
				)) else EstadoAporte end)
				
				
	

-------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------

UPDATE #IC_Aportes_Det SET tieneIntereses = (SELECT tieneIntereses FROM #IC_Aportes_Det WHERE claveCont = '50')
					   WHERE claveCont = '40'

------------------------------------------------------------------------------------------------

UPDATE #IC_Aportes_Det SET asignacion = CONCAT ('M',@apgid) WHERE asignacion = ''

--Insertar todos los apgid asociados--------------------- 
	SELECT apgid, asignacion 
	INTO #apgid
	FROM  #IC_Aportes_Det WHERE importeDocumento > 0 


IF (SELECT TOP 1 regValorIntMora FROM  core.pila.RegistroGeneral WHERE (regNumPlanilla = @planilla) OR (regId = @regidO AND regNumPlanilla IS NULL)) > 0
			BEGIN
				INSERT INTO #IC_Aportes_Det 
				SELECT 1 AS [consecutivo], [codigoSap], interes AS  [ImporteDocumento], [operador],  [claveCont], [asignacion], [textoPosicion], [ref1], [ref2], [tipoDocumento], [ref3], '',
				[adelantado], '', '', '', '', '', 1 AS [tieneIntereses], 
			 			-- Intereses por mora
				CASE
					WHEN EstadoRegistroAporteAportante = 'RELACIONADO'  AND TipoSolicitante = 'EMPLEADOR' AND (Tarifa = 0.00 OR Tarifa = 0.04) THEN 'IN1'
					WHEN EstadoRegistroAporteAportante = 'RELACIONADO'  AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.02 OR Tarifa = 0.04) THEN 'IN2'
					WHEN EstadoRegistroAporteAportante = 'RELACIONADO'  AND TipoSolicitante = 'PENSIONADO' AND Tarifa <> 0.006 THEN 'IN3'
					WHEN EstadoRegistroAporteAportante = 'RELACIONADO'  AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.006 OR Tarifa = 0.01) THEN 'IN4'
					WHEN EstadoRegistroAporteAportante = 'RELACIONADO'  AND TipoSolicitante = 'PENSIONADO' AND Tarifa = 0.006 THEN 'IN5'

					WHEN EstadoRegistroAporteAportante = 'REGISTRADO'  AND TipoSolicitante = 'EMPLEADOR' AND (Tarifa = 0.00 OR Tarifa = 0.04) THEN 'IM1'
					WHEN EstadoRegistroAporteAportante = 'REGISTRADO'  AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.02 OR Tarifa = 0.04) THEN 'IM2'
					WHEN EstadoRegistroAporteAportante = 'REGISTRADO'  AND TipoSolicitante = 'PENSIONADO' AND Tarifa <> 0.006 THEN 'IM3'
					WHEN EstadoRegistroAporteAportante = 'REGISTRADO'  AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.006 OR Tarifa = 0.01) THEN 'IM4'
					WHEN EstadoRegistroAporteAportante = 'REGISTRADO'  AND TipoSolicitante = 'PENSIONADO' AND Tarifa = 0.006 THEN 'IM5'

					WHEN EstadoRegistroAporteAportante = 'OTROS_INGRESOS'  AND TipoSolicitante = 'EMPLEADOR' AND (Tarifa = 0.00 OR Tarifa = 0.04) THEN 'RP1'
					WHEN EstadoRegistroAporteAportante = 'OTROS_INGRESOS'  AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.02 OR Tarifa = 0.04) THEN 'RP2'
					WHEN EstadoRegistroAporteAportante = 'OTROS_INGRESOS'  AND TipoSolicitante = 'PENSIONADO' AND Tarifa <> 0.006 THEN 'RP3'
					WHEN EstadoRegistroAporteAportante = 'OTROS_INGRESOS'  AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.006 OR Tarifa = 0.01) THEN 'RP4'
					WHEN EstadoRegistroAporteAportante = 'OTROS_INGRESOS'  AND TipoSolicitante = 'PENSIONADO' AND Tarifa = 0.006 THEN 'RP5'
					ELSE '*' END AS [tipoInteres], 
				[correccion],	[tipoMora], [id], [codigoGenesys], [tipo], [claseCuenta], [usuario], [regId],[fechaRecaudo],  [fechaProcesamiento], [EstadoRegistroAporteAportante], 
				[TipoSolicitante], [interes],[Tarifa], [operadorinformacion], [apgid],'','','',''
				FROM #IC_Aportes_Det  det	
				
				
			END

		--SELECT * FROM #IC_Aportes_Det		   			 		  		  		 

----------------Actualizacion de campos  detalle--------------------------------------------------------------------
	
	UPDATE #IC_Aportes_Det set importeDocumento = @Interes
				FROM #IC_Aportes_Det WHERE consecutivo = 1 AND tipoInteres <> ''


	UPDATE #IC_Aportes_Det SET claseDeAportePrescripcion = '' FROM #IC_Aportes_Det  WHERE claveCont = '40' AND claseDeAporte IS NULL or claseDeAporte =''

	
	SET @apgOr = (select TOP 1 apgid FROM #IC_Aportes_Det WHERE claveCont = '40')

		UPDATE #IC_Aportes_Det SET asignacion = CASE WHEN @planilla IS NULL THEN CONCAT('M', @apgOr) ELSE @planilla END
					

	UPDATE #IC_Aportes_Det SET ref2 = (SELECT TOP 1 CONCAT('M',apgid) FROM #IC_Aportes_Det WHERE claveCont = '50' )

	IF (SELECT COUNT(*) FROM #registroO) > 0
		BEGIN
			UPDATE #IC_Aportes_Det SET ref1 = regNumeroIdentificacionAportante , tipoDocumento =  mai.TipoIdHomologado, ref3 =  LEFT(regNombreAportante,20),
									codigoGenesys = p.perId	
									FROM #registroO	r
									INNER JOIN Persona p ON r.regNumeroIdentificacionAportante = p.perNumeroIdentificacion
									INNER JOIN core.sap.MaestraTiposIdentificacion mai ON p.perTipoIdentificacion = mai.TipoIdGenesys
									WHERE claveCont = '40'
		END							

	IF (SELECT COUNT(*) FROM #registroN) > 0
	BEGIN
		UPDATE #IC_Aportes_Det SET ref1 = regNumeroIdentificacionAportante , tipoDocumento =  mai.TipoIdHomologado, ref3 =  LEFT(regNombreAportante,20),
								codigoGenesys = p.perId	
								FROM #registroN	r
								INNER JOIN Persona p ON r.regNumeroIdentificacionAportante = p.perNumeroIdentificacion
								INNER JOIN core.sap.MaestraTiposIdentificacion mai ON p.perTipoIdentificacion = mai.TipoIdGenesys
								WHERE claveCont = '50'
	END	

	
 
--------------Fin de actualizacion detalle --------------------------------------------------------------------------

	SELECT DISTINCT  ct.Tipomovimiento
	INTO #MovOriginal
	FROM  #IC_Aportes_Det	det
	 INNER JOIN sap.PlanillasCtrl ct WITH(NOLOCK) ON det.apgid = ct.apgid  
	 INNER JOIN  core.sap.MaestraCondicionesCuentas CC WITH(NOLOCK) ON ct.apgEstadoRegistroAporteAportante  = cc.apgEstadoRegistroAporteAportante
				AND ISNULL(apgFormaReconocimientoAporte, '') = ISNULL(cc.apgFormaReconocimientoAporte, '')
				AND det.EstadoAporte = cc.apgEstadoAportante
				AND marcaperiodo = cc.apgmarcaperiodo
				AND CONVERT(VARCHAR, Tarifa) = CONVERT(VARCHAR, cc.apdTarifa)
				AND tiposolicitante = cc.apgtiposolicitante 
				AND det.tieneIntereses= cc.tieneinteres

	

	IF (SELECT TOP 1 * FROM #MovOriginal WHERE Tipomovimiento = 'A07') = 'A07'
		BEGIN 
			--BEGIN TRANSACTION
				print 'Ingresa AX1'
				Declare @apgidAX1 int
				Set @apgidAX1 = (select apgId from solicitud
								inner join SolicitudCorreccionAporte on solId = scaSolicitudGlobal
								inner join Correccion on scaId = corSolicitudCorreccionAporte
								inner join AporteGeneral on corAporteGeneral = apgId
								where solNumeroRadicacion = @solNumeroRadicacion)

				declare @solNumeroRadicacionAX1 varchar (30) = @solNumeroRadicacion
				print @apgidAX1
				print @solNumeroRadicacionAX1

					exec [sap].[USP_GetICAPORTES_Insert_CORRECCION_TP] '','',@apgidAX1,@solNumeroRadicacionAX1,'CORRECCION_APORTES',''
		END

		ELSE
			begin

				print 'Continua'

	
	---------------------------------------------------------------------------------------------
	 -- --Validacion existe Mov base en integracion contable
	 
	  IF NOT EXISTS
		( SELECT DISTINCT ct.Tipomovimiento as [Tipo movimiento maestra], enc.tipoMovimiento FROM  #IC_Aportes_Det	det
		 INNER JOIN sap.PlanillasCtrl ct WITH(NOLOCK) ON det.apgid = ct.apgid 
		 INNER JOIN  core.sap.MaestraCondicionesCuentas CC WITH(NOLOCK) ON ct.apgEstadoRegistroAporteAportante   = cc.apgEstadoRegistroAporteAportante
					AND ISNULL(apgFormaReconocimientoAporte, '') = ISNULL(cc.apgFormaReconocimientoAporte, '')
					AND det.EstadoAporte = cc.apgEstadoAportante
					AND marcaperiodo = cc.apgmarcaperiodo
					AND CONVERT(VARCHAR, Tarifa) = CONVERT(VARCHAR, cc.apdTarifa)
					AND tiposolicitante = cc.apgtiposolicitante 
					AND det.tieneIntereses= cc.tieneinteres
					AND det.claveCont = '40'
		INNER JOIN core.sap.IC_Aportes_Det di WITH(NOLOCK) ON det.asignacion = di.asignacion
		INNER JOIN core.sap.IC_Aportes_Enc enc WITH(NOLOCK) ON di.consecutivo = enc.consecutivo
		WHERE CC.Tipomovimiento = enc.tipoMovimiento)

		BEGIN
		  DELETE #IC_Aportes_Det
		END
		
	 -------------------------------------------------------------------------------------------------------------------------


	----Inserccion encabezado 	-----------------------------------------
			INSERT INTO #IC_Aportes_Enc(
						[fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[consecutivo],[observacion],
						[moneda],[documentoContable],[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario],[estadoaporteaportantetercero]
					)
					SELECT TOP 1
						CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
						CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
						ISNULL(det.fechaRecaudo, '') AS fechaDocumento,
						ISNULL(det.fechaProcesamiento, '') AS fechaContabilizacion,
						MONTH(det.fechaProcesamiento) AS periodo, 
						'' AS referencia,
						'A35'  AS tipoMovimiento,
						det.consecutivo AS consecutivo,
						'' AS observacion,
						'COP' AS moneda,
						'' AS documentoContable,  
						'COMF' AS sociedad,
						'' AS ejercicio,
						'' AS nroIntentos,
						'' AS fecProceso,
						'' AS horaProceso,
						'P'AS estadoReg,
						 det.usuario AS usuario,
						 det.EstadoRegistroAporteAportante as estadoaporteaportantetercero
					FROM #IC_Aportes_Det det WITH(NOLOCK)
						INNER JOIN #IC_Aportes_Det det1 ON det.asignacion = det1.asignacion  and det.consecutivo =  det1.consecutivo


			

		
		--------------------------------------------------------------------------------------------------------------------------------------------

		IF (SELECT TOP 1 CONCAT (noIdentificado, adelantado, claseDeAporte) FROM #IC_Aportes_Det where claveCont = '40' ) <> (SELECT TOP 1 CONCAT (noIdentificado, adelantado, claseDeAporte) FROM #IC_Aportes_Det where claveCont = '50') 
		BEGIN
			UPDATE #IC_Aportes_Enc SET tipoMovimiento ='A12'   
		END

	
		---------- numero de identificacion de la persona a corregir  para actualizacion de textoposicion
		declare @identi varchar (12)
		select @identi = (select Distinct ref1 from #IC_Aportes_Det where claveCont = '40' )
		Print @identi 

		--------------Actualizacion campo texto posicion ----------------------------------------------------------------------------------------------------------------------------------

		Declare @origen  varchar(16)
		
		------------------------------------------------------------------------------------------------------
		IF(select Distinct tipoSolicitante from #IC_Aportes_Det) = 'EMPLEADOR'
			BEGIN
				Select @origen = (select Distinct asignacion from #IC_Aportes_Det)
			
				--select @Origen as origen
			
				declare @resultadoE  varchar (max)
				select @resultadoE = (select dbo.ASP_BuscarPlanillasN (@origen))
			
				if (select @resultadoE) is null
					begin
						Update #IC_Aportes_Det set textoPosicion = planillaN
						from aporteDetalladoRegistroControlN where asignacion not like '%M%' and planillaAntes = asignacion
					end
					Else 
						begin
						update  #IC_Aportes_Det set textoPosicion = (select dbo.ASP_BuscarPlanillasN (@origen)) where asignacion not like '%M%' and @origen = asignacion
						end 
			END
			ELSE
				BEGIN
					Select @origen = (select Distinct asignacion from #IC_Aportes_Det)
			
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
							Update #IC_Aportes_Det set textoPosicion = planillaN
							from aporteDetalladoRegistroControlN where asignacion not like '%M%' and planillaAntes = asignacion
						end
						Else 
							begin
							update  #IC_Aportes_Det set textoPosicion = (select @resultado + '\' + @planillaNadp) where asignacion not like '%M%'
							end 
				END
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



			SELECT @referenciaNum = valorActual 
				FROM sap.ic_referencia
				WHERE comentario ='A'
				AND estado = 'A';

				UPDATE #IC_Aportes_Enc
					SET referencia =  @referenciaNum
	--comentarear

	
			UPDATE sap.ic_referencia
				SET valorActual = @referenciaNum + 1
				WHERE comentario ='A'
				AND estado = 'A';		
   
   
	
		IF ((SELECT referencia FROM #IC_Aportes_Enc ) IS NOT NULL )
		BEGIN

		
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

      
				INSERT INTO sap.IC_Aportes_Det([consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[ref1],[ref2],[tipoDocumento],[textoPosicion],[ref3],
						[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
						[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
						[claseCuenta],[usuario],[fechaEjecucion])
				SELECT [consecutivo],[codigoSap],CAST(CAST([importeDocumento] AS BIGINT) AS VARCHAR(18)),[operador],[claveCont],[asignacion],[ref1],[ref2],[tipoDocumento],[textoPosicion],[ref3],
						[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
						[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
						[claseCuenta],[usuario], GETDATE()-'05:00:00' 
				FROM #IC_Aportes_Det  WITH(NOLOCK)
				WHERE importeDocumento <> 0  ---  para cuando el registro viene sin detalle y que no traiga lineas en valores 0 08/10/2022
		
				UPDATE  #IC_Aportes_Det
					SET consecutivo =(SELECT consecutivo FROM #IC_Aportes_Enc)
		
				-- INSERCIoN EN LA TABLA DE CONTROL DE PLANILLAS
 
          
				INSERT INTO sap.PlanillasCtrl
				SELECT DISTINCT LEFT(det.asignacion, 12), 1, det.ref1, det.tipoDocumento, SAP.GetLocalDate(), 'APORTES', det.ref3 + enc.observacion, enc.tipoMovimiento,estadoaporteaportantetercero,operadorinformacion,case when @apgid is null or @apgid = '' then ap.apgid else @apgid end 
				FROM #IC_Aportes_Det det WITH(NOLOCK)
				INNER JOIN #apgid ap  WITH(NOLOCK) ON  det.asignacion = ap.asignacion
				INNER JOIN #IC_Aportes_Enc enc WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
				WHERE det.EstadoRegistroAporteAportante is not null



				-- Insercion en la tabla de control de solicitudes Correcciones
				INSERT INTO sap.ContablesCtrl
				SELECT DISTINCT solNumeroRadicacion, 1 as estado, perNumeroIdentificacion, perTipoIdentificacion,SAP.GetLocalDate(),'APORTES',solTipoTransaccion 
				FROM Solicitud sol WITH(NOLOCK)
				INNER JOIN  SolicitudCorreccionAporte sca WITH(NOLOCK) ON sca.scaSolicitudGlobal = sol.solId
				INNER JOIN Persona per WITH(NOLOCK) on sca.scaPersona = per.perId where solNumeroRadicacion = @solNumeroRadicacion and solTipoTransaccion = 'CORRECCION_APORTES'
	
	
	
			END
		END
	
	END

	-- Fin Comentario

				--Detalle
				SELECT DISTINCT * FROM #IC_Aportes_Enc
				ORDER BY tipoMovimiento, consecutivo

				SELECT * FROM #IC_Aportes_Det WITH(NOLOCK)
				--WHERE importeDocumento <> 0  ---  para cuando el registro viene sin detalle y que no traiga lineas en valores 0 08/10/2022
				ORDER BY claveCont

				DROP TABLE IF EXISTS #IC_Aportes_Det;	
				DROP TABLE IF EXISTS #IC_Aportes_Enc;					
				DROP TABLE IF EXISTS #registroO
				DROP TABLE IF EXISTS #registroN
				DROP TABLE IF EXISTS #apgid
				DROP TABLE IF EXISTS #registrogeneralA35
				DROP TABLE IF EXISTS #MovOriginal

		
			
		COMMIT TRANSACTION
			
		END TRY

		BEGIN CATCH

			ROLLBACK TRANSACTION

			SELECT  ERROR_NUMBER() AS ErrorNumber  ,ERROR_SEVERITY() AS ErrorSeverity  
			,ERROR_STATE() AS ErrorState,ERROR_PROCEDURE() AS ErrorProcedure  
			,ERROR_LINE() AS ErrorLine,ERROR_MESSAGE() AS ErrorMessage
			,GETDATE()-'05:00:00'  as Fechaing;  
 
			INSERT INTO core.sap.LogErrorAportes 
			SELECT @planilla, '',  @apgid,@solNumeroRadicacion,@regidO,@regidN, ERROR_NUMBER(),ERROR_SEVERITY(),ERROR_STATE(),ERROR_PROCEDURE(),ERROR_LINE(),ERROR_MESSAGE(),GETDATE()-'05:00:00' 
 

		END CATCH
	
	END