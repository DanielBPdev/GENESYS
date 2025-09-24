CREATE OR ALTER PROCEDURE [sap].[USP_Interfazcontableaportes_3] 

	@planilla VARCHAR(20),
	@planillaN VARCHAR(20),
	@regId BIGINT,
	@solNumeroRadicacion VARCHAR (30),
	@solTipoTransaccion VARCHAR (30),
	@operadorinformacion INT


AS 
BEGIN
SET NOCOUNT ON;


--IF (SELECT COUNT(*) FROM sap.ejecucion_aportes2) <= 0 BEGIN	
	
--	INSERT INTO sap.ejecucion_aportes2
--		SELECT 1

--IF (SELECT COUNT(*) FROM sys.tables WHERE name = 'ejecucion_int_aportes2') = 0 BEGIN
--		CREATE TABLE core.sap.ejecucion_int_aportes2(actual char(1))
--		INSERT INTO core.sap.ejecucion_int_aportes2 values ('1')



DECLARE	@referencia VARCHAR(16), @referenciaNum BIGINT, @consecutivo BIGINT, @comentario VARCHAR(2), @DevolucionT BIT
DECLARE @numeroDocumento VARCHAR(50), @tipoDocumento VARCHAR(20), @usuario VARCHAR(30) = 'soportetecnico@asopagos.com', @consecutivodet BIGINT





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
		devolucionCCF INT NULL,
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
		devolucionCCF INT NULL,
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
			[consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[ref1],[tipoDocumento],[ref3],
			[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
			[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
			[claseCuenta],[usuario],regId,valorIntMora,tarifa,apgOrigenAporte,devolucionCCF,operadorinformacion	)
		SELECT DISTINCT
			ROW_NUMBER() OVER(ORDER BY regId ASC) AS consecutivo,
			'' AS codigoSap, 
			--round(ISNULL(reg.regValTotalApoObligatorio, 0) + ISNULL(reg.regValorIntMora, 0),0) AS ImporteDocumento,
			round(ISNULL(apg.apgValTotalApoObligatorio, 0) + ISNULL(reg.regValorIntMora, 0),0) AS ImporteDocumento,
			'' AS operador,			-- Solo aplica para la INTEGRACIoN DE CARTERA
			'40' AS claveCont,
			ISNULL(reg.regnumplanilla,'') AS asignacion,
			reg.regNumeroIdentificacionAportante AS ref1,
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
			0, --valorIntMora
			0, --tarifa
			'', --apgOrigenAporte
			0,	--devolucionCCF
			reg.regOperadorInformacion AS operadorinformacion
		FROM pila.RegistroGeneral reg
		INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) ON regId = apgRegistroGeneral
		INNER JOIN core.[sap].[MaestraIdentificacion] mi WITH(NOLOCK)ON reg.regTipoIdentificacionAportante = mi.tipoIdGenesys
		-- VERIFICA SI PROVIENTE DE UNA PLANILLA PILA (planilla) O UN APORTE MANUAL (regid)
		WHERE ( reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @operadorinformacion) OR (reg.regid = @regid AND reg.regid <> 0 ) 
	
	--AND @solNumeroRadicacion = ''
	--AND @solTipoTransaccion = ''


	-- DETALLE: reconoce los registros de la tabla Solicitud para el caso de las DEVOLUCIONES.
	INSERT INTO #IC_Aportes_Det(
		[consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[ref1],[tipoDocumento],[ref3],
		[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
		[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
		[claseCuenta],[usuario],regId,valorintmora,tarifa,apgOrigenAporte,devolucionCCF, EstadoRegistroAporteAportante,
		TipoSolicitante 
	)
	SELECT DISTINCT 
		ROW_NUMBER() OVER(ORDER BY regId ASC) AS consecutivo,
		'' AS codigoSap, 
		round(ISNULL(moa.moaValorAporte, 0) + ISNULL(moa.moaValorInteres, 0),0) AS ImporteDocumento,
		'' AS operador,			-- Solo aplica para la INTEGRACIoN DE CARTERA
		'40' AS claveCont,
		ISNULL(reg.regnumplanilla,'') AS asignacion,
		-- Calcula si la devolucion es a una Caja de Compensacion
		per.perNumeroIdentificacion AS ref1,
		mi.tipoDocumentoCaja AS tipoDocumento,
		LEFT(per.perRazonSocial, 20) AS ref3,
		'=' AS noIdentificado,
		'=' AS adelantado,
		'=' AS identificadorDelBanco,
		'=' AS codigoBanco,
		'=' AS transitoria,
		'=' AS claseDeAporte,
		'=' AS claseDeAportePrescripcion,
		CASE WHEN moa.moaValorInteres = 0 THEN 0 
				WHEN moa.moaValorInteres > 0 THEN 1 END AS tieneIntereses,
		'=' AS tipoInteres,
		0 AS correccion,			-- Se define con los registros en la tabla Movimientoaporte en moaTipoAjuste = ''CORRECCION_A_LA_BAJA' va a ser anulacion.
		'' AS tipoMora,				-- Solo aplica para la INTEGRACIoN DE CARTERA
		0 AS id,
		NULL AS codigoGenesys,
		'=' AS tipo,
		'S' AS claseCuenta,
		'soportetecnico@asopagos.com' AS usuario,
		reg.regId,
		CAST(CONVERT(NUMERIC(21,5),ISNULL(moa.moaValorInteres, 0)) AS VARCHAR(18)), --valorIntMora
		0, --tarifa
		'', --apgOrigenAporte
		dap.dapCajaCompensacion,	--devolucionCCF
		apg.apgEstadoRegistroAporteAportante as EstadoRegistroAporteAportante,
		apgTipoSolicitante AS TipoSolicitante
	FROM core.dbo.SolicitudDevolucionAporte sda WITH(NOLOCK) 
	INNER JOIN core.dbo.DevolucionAporte dap WITH(NOLOCK) ON sda.sdaDevolucionAporte = dap.dapid
	INNER JOIN core.dbo.DevolucionAporteDetalle dad WITH(NOLOCK) ON dap.dapid = dad.dadDevolucionAporte
	INNER JOIN core.dbo.MovimientoAporte moa WITH(NOLOCK) ON dad.dadMovimientoAporte = moa.moaid
	INNER JOIN core.dbo.AporteGeneral apg WITH(NOLOCK) ON moa.moaAporteGeneral = apg.apgid
	INNER JOIN core.dbo.Solicitud sol WITH(NOLOCK) ON sda.sdaSolicitudGlobal = sol.solId
	INNER JOIN pila.RegistroGeneral reg ON apg.apgRegistroGeneral = reg.regId 
	INNER JOIN core.dbo.Persona per WITH(NOLOCK) ON sda.sdaPersona = per.perId
	INNER JOIN core.[sap].[MaestraIdentificacion] mi WITH(NOLOCK) ON per.perTipoIdentificacion = mi.tipoIdGenesys
	WHERE YEAR (sol.solFechaRadicacion) > 2010 -- verificar desde que ano se activan las opciones.
	AND sol.solTipoTransaccion = @solTipoTransaccion
	AND sol.solNumeroRadicacion = @solNumeroRadicacion
	--AND sol.solnumeroradicacion NOT IN (SELECT solNumeroRadicacion FROM sap.ContablesCtrl WITH (NOLOCK))
	AND (len(reg.regnumplanilla) <= 10 or regNumPlanilla is null)
	/*	
	GROUP BY 
		ISNULL(moa.moaValorAporte, 0) + ISNULL(moa.moaValorInteres, 0),
		ISNULL(reg.regnumplanilla,''),
		--reg.regNumeroIdentificacionAportante,
		reg.regNumeroIdentificacionAportante,
		mi.tipoDocumentoCaja,
		LEFT(reg.regNombreAportante, 20),
		moa.moaValorInteres,
		reg.regId,
		dap.dapCajaCompensacion
	*/

	-- DETALLE: reconoce los registros de la tabla Solicitud para el caso de las CORRECCIONES.
	INSERT INTO #IC_Aportes_Det(
		[consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[ref1],[ref2],[tipoDocumento],[ref3],
		[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
		[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
		[claseCuenta],[usuario],regId,valorintmora,tarifa,apgOrigenAporte,devolucionCCF,regIdorg,docorg, FormaReconocimientoAporte,
		noIdentificadoOri
	)
	SELECT DISTINCT top 1
		ROW_NUMBER() OVER(ORDER BY regId ASC) AS consecutivo,
		'' AS codigoSap,
		--round(ISNULL(reg.regValTotalApoObligatorio, 0),0 )AS ImporteDocumento,
	    --round(ISNULL(apg.apgValTotalApoObligatorio, 0) + ISNULL(reg.regValorIntMora, 0),0) AS ImporteDocumento,
		-- ISNULL(apg.apgValTotalApoObligatorio, 0) AS ImporteDocumento, -- JV: comentariado el 8 de agosto por el valor de la devolucion que se toma.
		--Case when apg.apgValTotalApoObligatorio = 0 and apg.apgValorIntMora = 0
		--then round(ISNULL(apg2.apgValTotalApoObligatorio, 0) + ISNULL(apg2.apgValorIntMora, 0),0)
		--else round(ISNULL(apg.apgValTotalApoObligatorio, 0) + ISNULL(apg.apgValorIntMora, 0),0)
		--else  round(ISNULL(reg.regValTotalApoObligatorio, 0) + ISNULL(reg.regValorIntMora, 0),0)-- agregado 02/03/2023 para correcciones de valor parcial.(CAMILO GOMEZ)
		--end
		round(ISNULL(apg2.apgValTotalApoObligatorio, 0) + ISNULL(apg2.apgValorIntMora, 0),0)
		 AS ImporteDocumento,  --modificado por valor de la correccion en parciales y totales.(CAMILO GOMEZ)
		'' AS operador,			-- Solo aplica para la INTEGRACIoN DE CARTERA
		'40' AS claveCont,
		ISNULL(reg.regnumplanilla, '') AS asignacion,
		perNumeroIdentificacion AS ref1,
		CONCAT('M', ISNULL(apg.apgId, '')) as ref2,
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
		apg2.apgRegistroGeneral,
		0, --valorIntMora
		0, --tarifa
		apg2.apgOrigenAporte,
		0,	--devolucionCCF
		regId,
		reg.regNumeroIdentificacionAportante as docorg,
		apd2.apdFormaReconocimientoAporte as FormaReconocimientoAporte,
		'' as noIdentificadoOri 
	FROM pila.RegistroGeneral reg
	INNER JOIN core.[sap].[MaestraIdentificacion] mi WITH(NOLOCK) ON reg.regTipoIdentificacionAportante = mi.tipoIdGenesys
	INNER JOIN core.dbo.AporteGeneral apg WITH(NOLOCK) ON reg.regId = apg.apgRegistroGeneral  
	INNER JOIN core.dbo.SolicitudCorreccionAporte sca WITH(NOLOCK) ON  apg.apgId = sca.scaAporteGeneral 
	INNER JOIN core.dbo.Solicitud sol WITH(NOLOCK) ON sca.scaSolicitudGlobal = sol.solId
	INNER JOIN core.dbo.Correccion cor WITH(NOLOCK) ON sca.scaid = cor.corsolicitudcorreccionaporte
	INNER JOIN core.dbo.AporteGeneral apg2 WITH(NOLOCK) ON  cor.corAporteGeneral =  apg2.apgid 
	INNER JOIN core.dbo.AporteDetallado apd2  WITH(NOLOCK) ON apg.apgId = apd2.apdAporteGeneral
	LEFT JOIN core.dbo.Empresa emp WITH(NOLOCK) ON  apg2.apgEmpresa = empId 
	LEFT JOIN core.dbo.persona WITH(NOLOCK) ON case when empPersona is null then apg2.apgPersona else empPersona END = perId
	WHERE YEAR (sol.solFechaRadicacion) > 2010 -- verificar desde que ano se activan las opciones.
	AND solTipoTransaccion = @solTipoTransaccion
	AND sol.solNumeroRadicacion = @solNumeroRadicacion
	--AND sol.solnumeroradicacion NOT IN (SELECT solNumeroRadicacion FROM sap.ContablesCtrl WITH (NOLOCK))
	AND (len(reg.regnumplanilla) <= 10 or regNumPlanilla is null)

	-- En el presente bloque se integran los registros cuando se encuentran segmentados y se unifican en un solo valor de importe de documento y valorOntMora.
	INSERT INTO #IC_Aportes_Det_Swap(
		[consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[ref1],[ref2],[tipoDocumento],[ref3],
		[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
		[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
		[claseCuenta],[usuario],regId,fechaRecaudo,fechaProcesamiento,EstadoRegistroAporteAportante,FormaReconocimientoAporte,
		apgEstadoAportante,marcaPeriodo,tipoSolicitante,valorIntMora,tarifa,apgOrigenAporte,devolucionCCF,regIdorg,docorg,operadorinformacion
	)
	SELECT DISTINCT MIN([consecutivo]),[codigoSap], SUM([importeDocumento]),[operador],[claveCont],[asignacion],[ref1],[ref2],[tipoDocumento],[ref3],
		[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
		[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
		[claseCuenta],[usuario],regId,fechaRecaudo,fechaProcesamiento,EstadoRegistroAporteAportante,FormaReconocimientoAporte,
		apgEstadoAportante,marcaPeriodo,tipoSolicitante,SUM(valorIntMora),tarifa,apgOrigenAporte,devolucionCCF,regIdorg,docorg,operadorinformacion
	FROM #IC_Aportes_Det
	GROUP BY [codigoSap],[operador],[claveCont],[asignacion],[ref1],[ref2],[tipoDocumento],[ref3],
		[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
		[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
		[claseCuenta],[usuario],regId,fechaRecaudo,fechaProcesamiento,EstadoRegistroAporteAportante,FormaReconocimientoAporte,
		apgEstadoAportante,marcaPeriodo,tipoSolicitante,tarifa,apgOrigenAporte,devolucionCCF,regIdorg,docorg,operadorinformacion;	

	TRUNCATE TABLE #IC_Aportes_Det;

	INSERT INTO #IC_Aportes_Det(
		[consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[ref1],[ref2],[tipoDocumento],[ref3],
		[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
		[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
		[claseCuenta],[usuario],regId,fechaRecaudo,fechaProcesamiento,EstadoRegistroAporteAportante,FormaReconocimientoAporte,
		apgEstadoAportante,marcaPeriodo,tipoSolicitante,valorIntMora,tarifa,apgOrigenAporte,devolucionCCF,regIdorg,docorg,operadorinformacion
	)
	SELECT DISTINCT [consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[ref1],[ref2],[tipoDocumento],[ref3],
		[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
		[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
		[claseCuenta],[usuario],regId,fechaRecaudo,fechaProcesamiento,EstadoRegistroAporteAportante,FormaReconocimientoAporte,
		apgEstadoAportante,marcaPeriodo,tipoSolicitante,valorIntMora,tarifa,apgOrigenAporte,devolucionCCF,regIdorg,docorg,operadorinformacion
	FROM #IC_Aportes_Det_Swap;
	-- fin del bloque de unificacion de valores.

	IF EXISTS (SELECT * FROM #IC_Aportes_Det) BEGIN
	/*
		-- INICIO: Validacion de registros cuando es a traves de un tercero pagador.
		-- 1. detectar un tercero pagoador
		TRUNCATE TABLE #IC_Aportes_Det_Swap;
	
		INSERT INTO #IC_Aportes_Det_Swap(
			[consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[ref1],[tipoDocumento],[ref3],
			[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
			[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
			[claseCuenta],[usuario],regId,fechaRecaudo,fechaProcesamiento,EstadoRegistroAporteAportante,FormaReconocimientoAporte,
			apgEstadoAportante,marcaPeriodo,tipoSolicitante,valorIntMora,tarifa,apgOrigenAporte,devolucionCCF
		)
		SELECT DISTINCT ROW_NUMBER() OVER(ORDER BY regId ASC) AS consecutivo,
			[codigoSap],
			SUM(apd.apdAporteObligatorio),
			[operador],[claveCont],[asignacion],[ref1],[tipoDocumento],[ref3],
			[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
			[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
			[claseCuenta],[usuario],
			regId,fechaRecaudo,fechaProcesamiento,apg.apgEstadoRegistroAporteAportante,apg.apgFormaReconocimientoAporte,
			apg.apgEstadoAportante,apg.apgMarcaPeriodo,tipoSolicitante,SUM(valorIntMora),apd.apdTarifa,apg.apgOrigenAporte,devolucionCCF 
		
		FROM AporteGeneral apg WITH(NOLOCK)
		INNER JOIN AporteDetallado apd WITH(NOLOCK) ON apd.apdAporteGeneral = apg.apgId
		INNER JOIN #IC_Aportes_Det iad ON iad.regId = apg.apgRegistroGeneral
		GROUP BY [codigoSap],[operador],[claveCont],[asignacion],[ref1],[tipoDocumento],[ref3],
		[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
		[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
		[claseCuenta],[usuario],regId,fechaRecaudo,fechaProcesamiento,apg.apgEstadoRegistroAporteAportante,apg.apgFormaReconocimientoAporte,
		apg.apgEstadoAportante,apg.apgMarcaPeriodo,tipoSolicitante,apd.apdTarifa,apg.apgOrigenAporte,devolucionCCF;
		
		TRUNCATE TABLE #IC_Aportes_Det;

		INSERT INTO #IC_Aportes_Det(
			[consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[ref1],[tipoDocumento],[ref3],
			[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
			[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
			[claseCuenta],[usuario],regId,fechaRecaudo,fechaProcesamiento,EstadoRegistroAporteAportante,FormaReconocimientoAporte,
			apgEstadoAportante,marcaPeriodo,tipoSolicitante,valorIntMora,tarifa,apgOrigenAporte,devolucionCCF
		)
		SELECT DISTINCT [consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[ref1],[tipoDocumento],[ref3],
			[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
			[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
			[claseCuenta],[usuario],regId,fechaRecaudo,fechaProcesamiento,EstadoRegistroAporteAportante,FormaReconocimientoAporte,
			apgEstadoAportante,marcaPeriodo,tipoSolicitante,valorIntMora,tarifa,apgOrigenAporte,devolucionCCF 
		FROM #IC_Aportes_Det_Swap;
	*/
		
		SELECT regId, apg.apgEstadoRegistroAporteAportante, apg.apgFormaReconocimientoAporte, /*apg.apgEstadoAportante, */apg.apgMarcaPeriodo, apd.apdTarifa, SUM(apd.apdAporteObligatorio), CEILING(SUM(apd.apdValorIntMora))
		FROM core.dbo.AporteGeneral apg WITH(NOLOCK)
		LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON  apg.apgId = apd.apdAporteGeneral
		INNER JOIN #IC_Aportes_Det iad WITH(NOLOCK) ON iad.regId = apg.apgRegistroGeneral
		GROUP BY regId, apg.apgEstadoRegistroAporteAportante, apg.apgFormaReconocimientoAporte, /*apg.apgEstadoAportante,*/apg.apgMarcaPeriodo, apd.apdTarifa;
		
		-- FIN: Validacion de registros cuando es a traves de un tercero pagador.

		-- CALCULO DEL CAMPOS DE APORTEDETALLADO Y APORTEGENERAL con el valor de intereses si los hay
	--	SELECT * FROM #IC_Aportes_Det
		
		

		-- Calcula la asignacion para el caso de las planillas manuales donde no se guarda el #, envia el valor del # Operacion
		UPDATE iad
		SET iad.importeDocumento = CASE WHEN iad.importeDocumento = 0 THEN round(iad.importeDocumento + (ISNULL(apg.apgValorIntMora, 0)),0)
									ELSE round(iad.importeDocumento,0)
									END,
			iad.asignacion = CASE WHEN iad.asignacion = '' AND @solTipoTransaccion = 'CORRECCION_APORTES' then ref2  --- agregado 16/02/2023 para correcciones manuales
				WHEN iad.asignacion = ''  THEN CONCAT('M', ISNULL(apg.apgId, '')) ELSE iad.asignacion END, 

			iad.ref2 = case when @solTipoTransaccion = 'CORRECCION_APORTES' then CONCAT('M', ISNULL(apg.apgId, ''))else ' ' END, --- agregado 16/02/2023 para correcciones manuales

				-- no identificados
			iad.noIdentificado = CASE WHEN apg.apgEstadoRegistroAporteAportante IN ('RELACIONADO','OTROS_INGRESOS') AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04 OR apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.01 or apd.apdTarifa = 0.03) THEN 'N1'
				WHEN apg.apgEstadoRegistroAporteAportante IN ('RELACIONADO','OTROS_INGRESOS') AND apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'N1'   ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022   
				WHEN apg.apgEstadoRegistroAporteAportante IN ('RELACIONADO','OTROS_INGRESOS') AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'N2'
				--WHEN apg.apgEstadoRegistroAporteAportante IN ('RELACIONADO','OTROS_INGRESOS') AND apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.04) THEN 'N1' --- cambiado 29/08/2022 revision caso A10 independiente con tarifa del 4%     
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
			 
			iad.identificadorDelBanco = CASE --WHEN apg.apgNumeroCuenta is null or apg.apgNumeroCuenta = '' then REPLACE(NUMERO_CUENTA,'-', '')  -- agregado posterior a gap cuenta bancaria 18/10/2022
			                            WHEN (apg.apgNumeroCuenta is null or apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo in ('1','2','3','4') THEN REPLACE(REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', ''), '000000', '')
										WHEN ( apg.apgNumeroCuenta is null or apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo = ('5') THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', ''))-- agregado posterior a gap vuenta bancaria 18/10/2022
										WHEN apg.apgCodEntidadFinanciera = 7 THEN REPLACE(REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''), '000000', '')
										WHEN apg.apgCodEntidadFinanciera = 51 THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''))
										WHEN apg.apgCodEntidadFinanciera IS NULL  AND apg2.apgCodEntidadFinanciera = 7 THEN REPLACE(REPLACE(UPPER(ISNULL(apg2.apgNumeroCuenta, '')),'-', ''), '000000', '')
										WHEN apg.apgCodEntidadFinanciera is null AND apg2.apgCodEntidadFinanciera = 51 THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(apg2.apgNumeroCuenta, '')),'-', '')) else apg.apgNumeroCuenta END,  --Intento de formato de las cuentas bancarias.
			iad.codigoBanco = CASE WHEN (apg.apgNumeroCuenta is null or apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo is not null AND apg.apgCuentaBancariaRecaudo = 5 THEN ce.codigoEntidad --agregado con tabla de homologacion sap.maestracodigoentidad 31/10/2022
								WHEN (apg.apgNumeroCuenta is null or apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo is not null  THEN CONCAT ( '0', CAST(ce.codigoEntidad as varchar(2)))
							--WHEN apg.apgCuentaBancariaRecaudo in ('1','2','3','4') AND apg.apgNumeroCuenta is null then '7' -- agregado posterior a gap vuenta bancaria 18/10/2022
							--WHEN apg.apgCuentaBancariaRecaudo = ('5') AND apg.apgNumeroCuenta is null then '51' -- agregado posterior a gap vuenta bancaria 18/10/2022
							WHEN LEN(apg.apgCodEntidadFinanciera) = 1 THEN CONCAT('0', CAST(apg.apgCodEntidadFinanciera AS VARCHAR(2)))
							WHEN apg.apgCodEntidadFinanciera is null AND LEN(apg2.apgCodEntidadFinanciera) = 1 THEN CONCAT('0', CAST(apg2.apgCodEntidadFinanciera AS VARCHAR(2))) ELSE CAST(apg.apgCodEntidadFinanciera AS VARCHAR(2)) END,

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
			iad.claseDeAportePrescripcion = CASE WHEN apg.apgFormaReconocimientoAporte = 'RECONOCIMIENTO_RETROACTIVO_MANUAL' AND apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' THEN '1' 
				ELSE '2' 
			END,
			iad.tieneIntereses = CASE WHEN iad.tieneIntereses = 0 AND apg.apgValorIntMora = 0 THEN 0
									WHEN iad.tieneIntereses = 1 OR apg.apgValorIntMora > 0 THEN 1
									ELSE iad.tieneIntereses
			END,
					
			iad.tipoInteres = CASE
						
					WHEN @solTipoTransaccion ='CORRECCION_APORTES'  THEN iad.tipoInteres

					  ----Devolucion tercero pagador
					WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES' AND iad.EstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR iad.valorIntMora > 0) AND iad.tipoSolicitante = 'EMPLEADOR' AND  (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04 OR apd.apdTarifa = 0.01 OR apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.03 OR apd.apdTarifa = 0.06) THEN 'IM1'
					WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES' AND iad.EstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND iad.TipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'IM1' 
					WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES' AND iad.EstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR iad.valorIntMora > 0) AND iad.TipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.00 or apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'IM2'
					WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES' AND iad.EstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND iad.TipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'IM3'
					WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES' AND iad.EstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND iad.TipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'IM4'
					WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES' AND iad.EstadoRegistroAporteAportante = 'REGISTRADO' AND (iad.tieneIntereses = 1 OR iad.ValorIntMora > 0) AND iad.TipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'IM5'
		
			        -- Intereses no identificados
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
			iad.fechaRecaudo = apg.apgFechaRecaudo ,
			iad.fechaProcesamiento = CASE WHEN [SAP].[calcularPrimerDiaHabilMes]() = [SAP].[calcularSiguienteDiaHabil]() AND DAY(DATEADD(HOUR,-5,GETDATE())) >= [SAP].[calcularPrimerDiaHabilMes]() AND DATEDIFF(DAY, apg.apgFechaRecaudo, DATEADD(HOUR,-5,GETDATE())) > 0
										THEN EOMONTH(DATEADD(HOUR,-5,GETDATE()),-1)
										ELSE DATEADD(HOUR,-5,GETDATE()) END,
			iad.EstadoRegistroAporteAportante =  CASE WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES'  THEN iad.EstadoRegistroAporteAportante ELSE  apg.apgEstadoRegistroAporteAportante END,
			iad.FormaReconocimientoAporte = apg.apgFormaReconocimientoAporte,
			iad.apgEstadoAportante = case when @solTipoTransaccion = 'CORRECCION_APORTES' then  apg2.apgEstadoAportante else  apg.apgEstadoAportante END,
			iad.marcaPeriodo = apg.apgMarcaPeriodo,
			iad.tipoSolicitante = apg.apgtipoSolicitante,
			iad.valorIntMora = CASE WHEN iad.valorIntMora = 0 THEN apg.apgvalorIntMora 
								ELSE iad.valorIntMora END,
			iad.tarifa = Case When  apd.apdId is null AND apg.apgTipoSolicitante = 'EMPLEADOR'  then 0.04 Else apd.apdTarifa  End, -----  Este campo es obligatorio, si viene en 0 o null no realiza el proceso para el tipo de aporte. (revisar con robinson)
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
			END

		---------------------------------------------------------------------------
		FROM core.dbo.AporteGeneral apg WITH(NOLOCK)
		LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON  apg.apgId  = apd.apdAporteGeneral --- CAMBIO A LEFT CUANDO EL DOCUMENTO NO TRAE LOS DETALLES DE LA TRANSACCION EN AporteDetallado apd
		INNER JOIN #IC_Aportes_Det iad WITH(NOLOCK) ON  apg.apgRegistroGeneral = iad.regId
		LEFT JOIN core.dbo.AporteGeneral apg2 WITH(NOLOCK) ON iad.regIdorg = apg2.apgRegistroGeneral 
		LEFT JOIN core.dbo.AporteDetallado apd2 WITH(NOLOCK) ON  apg2.apgId = apd2.apdAporteGeneral
		LEFT JOIN core.dbo.CUENTAS_BANCARIAS cb WITH(NOLOCK) ON apg.apgCuentaBancariaRecaudo = cb.ID  -- agregado posterior a gap vuenta bancaria 18/10/2022
		LEFT JOIN core.sap.MaestracodigoEntidad ce WITH(NOLOCK) ON cb.ID = ce.idCuentasBancarias 
		

		
	--SELECT apg2.apgEstadoRegistroAporteAportante,apg.apgValorIntMora,apg2.apgTipoSolicitante,apd2.apdTarifa, * FROM #IC_Aportes_Det iad  --para consultar los campos que llegan 
	--INNER JOIN AporteGeneral apg2 ON apg2.apgRegistroGeneral = iad.regIdorg
	--INNER JOIN AporteDetallado apd2 ON apd2.apdAporteGeneral = apg2.apgId
		
		----- A07 EN CASO DE UNA CORRECION QUE DISPARA A08
		IF EXISTS ( SELECT CTR.Tipomovimiento FROM core.dbo.AporteGeneral apg WITH(NOLOCK)
		INNER JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON  apg.apgId = apd.apdAporteGeneral  --- CAMBIO A LEFT CUANDO EL DOCUMENTO NO TRAE LOS DETALLES DE LA TRANSACCION EN AporteDetallado apd
		INNER JOIN #IC_Aportes_Det iad WITH(NOLOCK) ON case when asignacion like 'M%' then iad.regId else iad.regIdorg END = apg.apgRegistroGeneral
		INNER JOIN core.sap.PlanillasCtrl ctr WITH(NOLOCK) ON iad.ref1 = ctr.regNumeroIdentificacionAportante AND iad.tipoDocumento = ctr.regTipoIdentificacionAportante AND CTR.Tipomovimiento = 'A07'
		INNER JOIN core.sap.MaestraCondicionesCuentas cc2 WITH(NOLOCK) ON CONVERT(VARCHAR, apd.apdTarifa ) = CONVERT(VARCHAR, cc2.apdTarifa) AND apg.apgTipoSolicitante = cc2.apgtiposolicitante AND CC2.Tipomovimiento= 'A08'
		WHERE @solTipoTransaccion = 'CORRECCION_APORTES')
BEGIN 
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
		INNER JOIN #IC_Aportes_Det iad WITH(NOLOCK) ON  apg.apgRegistroGeneral = iad.regId 
		LEFT JOIN core.sap.PlanillasCtrl ctr WITH(NOLOCK) ON iad.ref1 = ctr.regNumeroIdentificacionAportante AND iad.tipoDocumento = ctr.regTipoIdentificacionAportante AND CTR.Tipomovimiento = 'A07'
		LEFT JOIN core.sap.MaestraCondicionesCuentas cc2 WITH(NOLOCK) ON CONVERT(VARCHAR, apd.apdTarifa ) = CONVERT(VARCHAR, cc2.apdTarifa) AND apg.apgTipoSolicitante = cc2.apgtiposolicitante AND CC2.Tipomovimiento= 'A08'

		UPDATE #IC_Aportes_Det 
		SET	fechaRecaudo = apg.apgFechaRecaudo
		FROM core.dbo.AporteGeneral apg 
		INNER JOIN core.dbo.SolicitudCorreccionAporte sca WITH(NOLOCK) ON  apg.apgId = sca.scaAporteGeneral
		INNER JOIN core.dbo.Solicitud sol WITH(NOLOCK) ON sca.scaSolicitudGlobal = sol.solId 
		INNER JOIN core.dbo.Persona per WITH(NOLOCK) ON sca.scaPersona = per.perId
		WHERE sol.solNumeroRadicacion = @solNumeroRadicacion
		AND @solTipoTransaccion = 'CORRECCION_APORTES'
END
ELSE 
BEGIN
		
		--------- actualizacion de fecha y planilla en correcciones  26/09/2022 -----------------------

		UPDATE #IC_Aportes_Det 
		SET	fechaRecaudo = apg.apgFechaRecaudo
		FROM core.dbo.AporteGeneral apg 
		INNER JOIN core.dbo.SolicitudCorreccionAporte sca WITH(NOLOCK) ON apg.apgId = sca.scaAporteGeneral
		INNER JOIN core.dbo.Solicitud sol WITH(NOLOCK) ON sca.scaSolicitudGlobal = sol.solId
		INNER JOIN core.dbo.Persona per WITH(NOLOCK) ON sca.scaPersona = per.perId
		INNER JOIN pila.RegistroGeneral reg ON apg.apgRegistroGeneral = reg.regid 
		WHERE sol.solNumeroRadicacion = @solNumeroRadicacion
		AND @solTipoTransaccion = 'CORRECCION_APORTES'

		END

		--UPDATE #IC_Aportes_Enc SET fechaDocumento = apgFechaRecaudo
		--FROM AporteGeneral apg 
		--INNER JOIN SolicitudCorreccionAporte sca ON sca.scaAporteGeneral = apg.apgId
		--INNER JOIN Solicitud sol ON sol.solId = sca.scaSolicitudGlobal
		--INNER JOIN Persona per ON sca.scaPersona = per.perId
		--INNER JOIN #IC_Aportes_Det iad ON iad.regId = apg.apgRegistroGeneral
		--WHERE sol.solNumeroRadicacion = @solNumeroRadicacion AND @solTipoTransaccion = 'CORRECCION_APORTES'


		------------------------------------------------------------------------------------------
		
		UPDATE iad
		SET iad.codigoGenesys = per.perId,
		iad.tipo = CASE WHEN per.perTipoIdentificacion = 'NIT' THEN 'E'
			ELSE 'P' END



----------------------------------
	--iad.identificadorDelBanco = CASE WHEN iad.identificadorDelBanco = NUMERO_CUENTA AND apg.apgCodEntidadFinanciera = 51 THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', ''))
													
									--	else NUMERO_CUENTA END,   -- agregado posterior a gap cuenta bancaria 20/10/2022

		--								--WHEN apg.apgNumeroCuenta is null or apg.apgNumeroCuenta = '' then REPLACE(NUMERO_CUENTA,'-', '')
		--								--WHEN apg.apgCodEntidadFinanciera = 7 THEN REPLACE(REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''), '000000', '')
		--								--WHEN apg.apgCodEntidadFinanciera = 51 THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', '')) else NUMERO_CUENTA END,  --Intento de formato de las cuentas bancarias.



-----------------------
		-- INICIO: Hasta entrega del GAP046 - Campo Cta Bancaria Recaudo de Aportes Manual GLPI 57808
		--iad.identificadorDelBanco = CASE WHEN iad.identificadorDelBanco = '' THEN '00217185112' ELSE ISNULL(iad.identificadorDelBanco, '00217185112') END, 
		--iad.codigoBanco = CASE WHEN iad.codigoBanco = '' THEN '07' ELSE ISNULL(iad.codigoBanco, '07') END
		-- FIN: Hasta entrega del GAP046 - Campo Cta Bancaria Recaudo de Aportes Manual GLPI 57808


		FROM core.dbo.Persona per 
		INNER JOIN core.[sap].[MaestraIdentificacion] mi WITH(NOLOCK) ON per.perTipoIdentificacion = mi.tipoIdGenesys
		INNER JOIN #IC_Aportes_Det iad WITH(NOLOCK) ON  per.perNumeroIdentificacion = iad.ref1  AND mi.tipoDocumentoCaja = iad.tipoDocumento 
		--INNER JOIN AporteGeneral apg ON per.perId = apg.apgPersona  -- agregado posterior a gap vuenta bancaria 18/10/2022
		--INNER JOIN CUENTAS_BANCARIAS cb ON apg.apgCuentaBancariaRecaudo = cb.ID  -- agregado posterior a gap vuenta bancaria 18/10/2022
		

		-- INSERCIoN DEL ENCABEZADO
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

			CORRECCIONES
			A07 -> A08
			A05 -> A34 o A03
			A06 -> A04
			A08 -> AX1 -- Este movimiento no se envia porque duplica el movimiento A08, solo hace un cambio de periodo, y se elimina en la presente integracion.
			A05 -> AX5
			TODOS -> A12

			*/
			CASE 
				--	Agregado Yesika Bernal, Validacion oviento A03 Y A04
				WHEN ((SELECT TOP 1 d.asignacion FROM sap.IC_Aportes_Det d INNER JOIN sap.IC_Aportes_enc e WITH(NOLOCK) ON d.consecutivo = e.consecutivo 
																	INNER JOIN #IC_Aportes_Det dt WITH(NOLOCK) ON d.asignacion = dt.asignacion
																	WHERE Tipomovimiento = 'A05') = (SELECT dt.asignacion FROM #IC_Aportes_Det dt))  AND 
				det.apgEstadoAportante <> 'ACTIVO' AND EstadoRegistroAporteAportante = 'REGISTRADO' AND marcaperiodo in ('PERIODO_REGULAR','PERIODO_RETROACTIVO') THEN 'A03'
				WHEN ((SELECT TOP 1 d.asignacion FROM sap.IC_Aportes_Det d INNER JOIN sap.IC_Aportes_enc e WITH(NOLOCK) ON d.consecutivo = e.consecutivo 
																	INNER JOIN #IC_Aportes_Det dt WITH(NOLOCK) ON d.asignacion = dt.asignacion
																	  WHERE Tipomovimiento = 'A06' ) = (SELECT dt.asignacion FROM #IC_Aportes_Det dt)) 
				AND det.apgEstadoAportante <> 'ACTIVO' AND EstadoRegistroAporteAportante = 'REGISTRADO' AND marcaperiodo IN ('PERIODO_REGULAR','PERIODO_RETROACTIVO') THEN 'A04'


				-- DEVOLUCION_APORTES
				WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES' AND cc.Tipomovimiento = 'A01' THEN 'A19'
				WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES' AND cc.Tipomovimiento = 'A08' THEN 'A19'
				WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES' AND cc.Tipomovimiento = 'A02' AND det.importeDocumento > det.valorIntMora THEN 'A20' -- PENDIENTE PRUEBAS
				WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES' AND cc.Tipomovimiento = 'A02' AND det.importeDocumento = det.valorIntMora THEN 'A15' -- PENDIENTE PRUEBAS
				WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES' AND cc.Tipomovimiento = 'A05' THEN 'A16'
				WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES' AND cc.Tipomovimiento = 'A06' AND det.importeDocumento > det.valorIntMora THEN 'A17'
				WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES' AND cc.Tipomovimiento = 'A06' AND det.importeDocumento = det.valorIntMora THEN 'A15'
				WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES' AND cc.Tipomovimiento = 'A04' AND det.importeDocumento > det.valorIntMora THEN 'A20' -- PENDIENTE PRUEBAS
				WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES' AND cc.Tipomovimiento = 'A04' AND det.importeDocumento = det.valorIntMora THEN 'A15' -- PENDIENTE PRUEBAS
				WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES' AND cc.Tipomovimiento = 'A07' THEN 'A21' -- PENDIENTE PRUEBAS
				WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES' AND cc.Tipomovimiento = 'A34' THEN 'A21' -- PENDIENTE PRUEBAS
				WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES' AND cc.Tipomovimiento = 'A09' THEN 'A36' 
				WHEN @solTipoTransaccion = 'DEVOLUCION_APORTES' AND cc.Tipomovimiento = 'A10' THEN 'A37' 


				-- CORRECCION_APORTES
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND (SELECT TOP 1 tipoMovimiento FROM sap.IC_Aportes_Det d INNER JOIN #IC_Aportes_Det dt ON  d.asignacion = dt.asignacion	INNER JOIN sap.IC_Aportes_Enc e ON d.consecutivo = e.consecutivo WHERE e.tipoMovimiento = 'A06' GROUP BY tipoMovimiento HAVING MAX(d.consecutivo) = MAX(e.consecutivo)) = 'A06'
				THEN 'A06_A35'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND det.adelantado in ('A1','A2','A3','A4','A5')  AND CTR.Tipomovimiento = 'A07' AND cc.Tipomovimiento = 'A01' THEN 'A08'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND CC.Tipomovimiento = 'A01' AND (SELECT top 1 regNumeroIdentificacionAportante FROM pila.RegistroGeneral
				INNER JOIN #IC_Aportes_Det det ON regNumPlanilla = asignacion)<> (SELECT top 1 ref1 FROM #IC_Aportes_Det) THEN 'A01_A35'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND CC.Tipomovimiento = 'A02' AND (SELECT top 1 regNumeroIdentificacionAportante FROM pila.RegistroGeneral
				INNER JOIN #IC_Aportes_Det det ON regNumPlanilla = asignacion)<> (SELECT top 1 ref1 FROM #IC_Aportes_Det) THEN 'A02_A35'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND CC.Tipomovimiento = 'A03' AND (SELECT top 1 regNumeroIdentificacionAportante FROM pila.RegistroGeneral
				INNER JOIN #IC_Aportes_Det det ON regNumPlanilla = asignacion)<> (SELECT top 1 ref1 FROM #IC_Aportes_Det) THEN 'A03_A35'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND CC.Tipomovimiento = 'A04' AND (SELECT top 1 regNumeroIdentificacionAportante FROM pila.RegistroGeneral
				INNER JOIN #IC_Aportes_Det det ON regNumPlanilla = asignacion)<> (SELECT top 1 ref1 FROM #IC_Aportes_Det) THEN 'A04_A35'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND CC.Tipomovimiento = 'A05' AND (SELECT top 1 regNumeroIdentificacionAportante FROM pila.RegistroGeneral
				INNER JOIN #IC_Aportes_Det det ON regNumPlanilla = asignacion)<> (SELECT top 1 ref1 FROM #IC_Aportes_Det) THEN 'A05_A35'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND CC.Tipomovimiento = 'A06' AND (SELECT top 1 regNumeroIdentificacionAportante FROM pila.RegistroGeneral
				INNER JOIN #IC_Aportes_Det det ON regNumPlanilla = asignacion)<> (SELECT top 1 ref1 FROM #IC_Aportes_Det) THEN 'A06_A35'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND CC.Tipomovimiento = 'A07' AND (SELECT top 1 regNumeroIdentificacionAportante FROM pila.RegistroGeneral
				INNER JOIN #IC_Aportes_Det det ON regNumPlanilla = asignacion)<> (SELECT top 1 ref1 FROM #IC_Aportes_Det) THEN 'A07_A35'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND CC.Tipomovimiento = 'A07' AND (SELECT top 1 det.docorg FROM sap.PlanillasCtrl Pc
				INNER JOIN #IC_Aportes_Det det ON pc.regNumPlanilla = det.asignacion)<> (SELECT top 1 ref1 FROM #IC_Aportes_Det) THEN 'A07_A35' ----- para recaudo manual 24/03/2023
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND CC.Tipomovimiento = 'A08' AND (SELECT top 1 regNumeroIdentificacionAportante FROM pila.RegistroGeneral
				INNER JOIN #IC_Aportes_Det det ON regNumPlanilla = asignacion)<> (SELECT top 1 ref1 FROM #IC_Aportes_Det) THEN 'A08_A35'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND CC.Tipomovimiento = 'A09' AND (SELECT top 1 regNumeroIdentificacionAportante FROM pila.RegistroGeneral
				INNER JOIN #IC_Aportes_Det det ON regNumPlanilla = asignacion)<> (SELECT top 1 ref1 FROM #IC_Aportes_Det) THEN 'A09_A35'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND CC.Tipomovimiento = 'A10' AND (SELECT top 1 regNumeroIdentificacionAportante FROM pila.RegistroGeneral
				INNER JOIN #IC_Aportes_Det det ON regNumPlanilla = asignacion)<> (SELECT top 1 ref1 FROM #IC_Aportes_Det) THEN 'A10_A35'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A02' THEN 'A12'
				--WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A07' THEN 'A08'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A05' AND det.marcaPeriodo = 'PERIODO_FUTURO' THEN 'A34' -- El A03 lo toma con las reglas definidas
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A05' THEN 'A12'  
				WHEN @solTipoTransaccion = 'ACTIVO'             AND cc.Tipomovimiento = 'A05' THEN 'A34' -- para A05 que pasa a A34 cuando se cambia de inactivo a Activo. 22/09/20
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A04' THEN 'A12'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A03' AND claseDeAporteorg <> claseDeAporte THEN 'A12'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A06'  AND det.EstadoRegistroAporteAportante = 'RELACIONADO' THEN 'A06_A12'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A06' THEN 'A04'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A07' AND det.EstadoRegistroAporteAportante = 'RELACIONADO' THEN 'A07_A12'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A08' THEN 'A12'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A09' THEN 'A12'
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A10' THEN 'A12'
				--WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A08' THEN 'AX1' -- Caso del movimiento AX1 que debe omitirse por que la novedad duplicaria el valor valor del importe.
				WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND det.apgOrigenAporte = 'CORRECCION_APORTE' AND cc.Tipomovimiento = 'A01' THEN 'A12' -- Cuando es un movimiento A12
				--WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A01' THEN 'A35' -- Cuando es un movimiento A35
				--WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND cc.Tipomovimiento = 'A01' THEN 'A03' -- PENDIENTE REVISAR PARA TODAS LOS MOVIMIENTOS
				--WHEN @solTipoTransaccion = 'CORRECCION_APORTES' AND det.adelantado in ('A1','A2','A3','A4','A5') AND MONTH(fechaRecaudo) = MONTH(ctr.fechaIngreso) AND CTR.Tipomovimiento = 'A07' AND cc.Tipomovimiento = 'A01' THEN 'A08'
				-- movimiento NORMAL
				WHEN '' = '' THEN ISNULL(cc.Tipomovimiento, 'XXX') -- movimientos definidos en la tabla maestra, que no son devoluciones ni correcciones
				ELSE ISNULL(cc.Tipomovimiento, 'DEV')
			END AS tipomovimiento,
			consecutivo AS consecutivo,
			CASE WHEN MONTH(fechaRecaudo) = MONTH(ctr.fechaIngreso) AND CTR.Tipomovimiento = 'A07' AND cc.Tipomovimiento = 'A01' AND @solNumeroRadicacion <> '' AND @solTipoTransaccion = 'ACTIVO'
					THEN  CONCAT(cc2.apgEstadoRegistroAporteAportante,' / ',cc2.apgFormaReconocimientoAporte,' / ',cc2.apgEstadoAportante,' / ',cc2.apgmarcaperiodo,' / ',cc2.apdTarifa,' / ',cc2.apgTipoSolicitante,' / ',cc2.tieneInteres)
			     WHEN @solTipoTransaccion = 'ACTIVO' THEN ISNULL(EstadoRegistroAporteAportante,'') + ' / ' + ISNULL(FormaReconocimientoAporte, '') + ' / '+ ISNULL(@solTipoTransaccion, '') + ' / ' + ISNULL(marcaperiodo, '') + ' / ' + CONVERT(VARCHAR, ISNULL(tarifa, 0)) + ' / ' + ISNULL(tiposolicitante, '') +' / '+ CASE WHEN ISNULL(valorIntMora,0) > 0 THEN '1' ELSE '0' END
				 ELSE ISNULL(EstadoRegistroAporteAportante,'') + ' / ' + ISNULL(FormaReconocimientoAporte, '') + ' / '+ ISNULL(det.apgEstadoAportante, '') + ' / ' + ISNULL(marcaperiodo, '') + ' / ' + CONVERT(VARCHAR, ISNULL(tarifa, 0)) + ' / ' + ISNULL(tiposolicitante, '') +' / '+ CASE WHEN ISNULL(valorIntMora,0) > 0 THEN '1' ELSE '0' END END AS observacion,
			--ISNULL(EstadoRegistroAporteAportante,'') + ' / ' + ISNULL(FormaReconocimientoAporte, '') + ' / '+ ISNULL(det.apgEstadoAportante, '') + ' / ' + ISNULL(marcaperiodo, '') + ' / ' + CONVERT(VARCHAR, ISNULL(tarifa, 0)) + ' / ' + ISNULL(tiposolicitante, '') +' / '+ CASE WHEN ISNULL(valorIntMora,0) > 0 THEN '1' ELSE '0' END AS observacion,
			--EstadoRegistroAporteAportante + FormaReconocimientoAporte + marcaperiodo + CONVERT(VARCHAR, tarifa) + tiposolicitante + CASE WHEN ISNULL(valorIntMora,0) > 0 THEN '1' ELSE '0' END AS observacion,
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
			LEFT JOIN core.sap.MaestraCondicionesCuentas cc2 WITH(NOLOCK) ON CONVERT(VARCHAR, Tarifa) = CONVERT(VARCHAR, cc2.apdTarifa) AND tiposolicitante = cc2.apgtiposolicitante AND CC2.Tipomovimiento= 'A08'AND cc2.apgEstadoAportante = 'ACTIVO'
		------------------------------
	
	

		--Trae el numero de asignacion anterior en el A12 para clave 40

		IF NOT EXISTS (SELECT * FROM #IC_Aportes_Enc enc WITH(NOLOCK)
		INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON det.consecutivo = enc.consecutivo
		WHERE tipoMovimiento = 'A12' AND claveCont = '40')

		BEGIN 
	
		UPDATE #IC_Aportes_Det 
		SET asignacion =  CASE WHEN ISNULL(reg.regNumPlanilla,'') = ''  THEN CONCAT('M', ISNULL(apg.apgId, '')) ELSE reg.regNumPlanilla END
		-- ref2 =  CASE WHEN ISNULL(reg.regNumPlanilla,'') = ''  THEN CONCAT('M', ISNULL(apg.apgId, '')) ELSE reg.regNumPlanilla END
		FROM core.dbo.AporteGeneral apg WITH(NOLOCK)
		INNER JOIN core.dbo.SolicitudCorreccionAporte sca WITH(NOLOCK) ON  apg.apgId = sca.scaAporteGeneral
		INNER JOIN core.dbo.Solicitud sol WITH(NOLOCK) ON  sca.scaSolicitudGlobal = sol.solId
		INNER JOIN core.dbo.Persona per WITH(NOLOCK) ON sca.scaPersona = per.perId
		INNER JOIN pila.RegistroGeneral reg  ON apg.apgRegistroGeneral = reg.regid
		WHERE sol.solNumeroRadicacion = @solNumeroRadicacion
		AND @solTipoTransaccion = 'CORRECCION_APORTES'
		End

		-------------------------------------------------------

		-- Revisa la cantidad de registros asociados a la planilla y si encuentra un movimiento adicional tipoMovimiento = 'XXX' 
		-- lo elimina y detecta las devoluciones y las correcciones a traves de las novedades.
		-- Elimina los movimientos AX1.

		DELETE #IC_Aportes_Det 
		WHERE consecutivo IN (
			SELECT consecutivo 
			FROM #IC_Aportes_Enc
			WHERE tipoMovimiento IN ('AX1')
		);

		DELETE #IC_Aportes_Enc 
		WHERE tipoMovimiento IN ('AX1');

		IF (SELECT COUNT(1) FROM #IC_Aportes_Enc WITH(NOLOCK)) > 1 BEGIN
	
			--SELECT COUNT(1) AS 'Registros Encontrados'
			--FROM #IC_Aportes_Det
			
			UPDATE #IC_Aportes_Enc 
			SET tipoMovimiento = 'A21'
			WHERE tipoMovimiento = 'A07'
			AND @solTipoTransaccion = 'DEVOLUCION_APORTES';
			

			DELETE #IC_Aportes_Det 
			WHERE consecutivo IN (
				SELECT consecutivo 
				FROM #IC_Aportes_Enc
				WHERE tipoMovimiento IN ('XXX', 'DEV')
			);

			DELETE #IC_Aportes_Enc 
			WHERE tipoMovimiento IN ('XXX', 'DEV');

		END --IF

		-- Sobre-escribe el valor del importe para los movimientos A04 Cuenta pasivo con intereses y A10 prescripcion con intereses
		UPDATE det 
		SET det.importeDocumento = round(det.importeDocumento - valorIntMora,0)
		FROM #IC_Aportes_Enc enc WITH(NOLOCK)
		INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A04','A04_A35','A10','A10_A35')
		
		-- Adicionalmente en el movimiento A03 y A04 calcula el campo de noIdentificado por la especifidad del movimiento
		UPDATE det
		SET det.noIdentificado = CASE WHEN enc.tipoMovimiento IN ('A03','A03_A35','A04','A04_A35') AND det.EstadoRegistroAporteAportante = 'REGISTRADO' AND det.TipoSolicitante = 'EMPLEADOR' AND (det.tarifa = 0.00 OR det.tarifa = 0.04 OR det.tarifa = 0.02 OR det.tarifa = 0.01 or det.tarifa = 0.03) THEN 'N1'
				WHEN enc.tipoMovimiento IN ('A03','A03_A35','A04','A04_A35') AND det.EstadoRegistroAporteAportante = 'REGISTRADO' AND det.TipoSolicitante = 'INDEPENDIENTE' AND (det.Tarifa = 0.02 OR det.Tarifa = 0.04) THEN 'N2'
				WHEN enc.tipoMovimiento IN ('A03','A03_A35','A04','A04_A35') AND det.EstadoRegistroAporteAportante = 'REGISTRADO' AND det.TipoSolicitante = 'PENSIONADO' AND det.Tarifa <> 0.006 THEN 'N3'
				WHEN enc.tipoMovimiento IN ('A03','A03_A35','A04','A04_A35') AND det.EstadoRegistroAporteAportante = 'REGISTRADO' AND det.TipoSolicitante = 'INDEPENDIENTE' AND (det.Tarifa = 0.006 OR det.Tarifa = 0.01) THEN 'N4'
				WHEN enc.tipoMovimiento IN ('A03','A03_A35','A04','A04_A35') AND det.EstadoRegistroAporteAportante = 'REGISTRADO' AND det.TipoSolicitante = 'PENSIONADO' AND det.Tarifa = 0.006 THEN 'N5'
				ELSE '**'
		END
		FROM #IC_Aportes_Enc enc WITH(NOLOCK)
		INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A03','A03_A35','A04','A04_A35')

		-- Sobre-escribe el valor del importe para el movimiento A35
		--**************************** AQUI VOY CON EL MOVIMIENTO 35 ******************************************************************************************
		--UPDATE det
		--SET det.importeDocumento = 0 -- apgValTotalApoObligatorio
		--FROM #IC_Aportes_Enc enc 
		--INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		--WHERE enc.tipoMovimiento IN ('A35')

		-- Calcular el valor del campo referencia clase "AL" para movimientos de prescripcion (A09 y A10), los demas movimientos con la clase "TE"
		IF EXISTS ( SELECT * FROM #IC_Aportes_Enc WITH(NOLOCK) WHERE tipoMovimiento IN ('A09','A09_A35','A10','A10_A35','A36','A37')) BEGIN
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


	--IF @referenciaNum -1 = (select   max(CONVERT(bigint, referencia)) from sap.IC_Aportes_Enc INNER JOIN sap.IC_Referencia ON (CONVERT(bigint, referencia)+1)  = valorActual AND estado = 'A'and comentario =@comentario)
	 --BEGIN

				UPDATE #IC_Aportes_Enc
				SET referencia =  @referenciaNum
--comentarear
/*
  			UPDATE sap.IC_Referencia
			SET valorActual = @referenciaNum + (SELECT COUNT (1) FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det ON enc.consecutivo = det.consecutivo WHERE det.importedocumento <> 0) -- Actualiza los numeros de referencia de acuerdo a la cantidad de encabezados.
			WHERE comentario = @comentario
			AND estado = 'A';
*/
	--ENd

---- fin comentario
		
		

	-- REGISTRO PARA COMPLETAR EL MOVIMIENTO CON LA CLAVE DE CONTABILIZACIoN DE ACUERDO AL TIPO DE MOVIMIENTO
	
	INSERT INTO #IC_Aportes_Det(
		[consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[ref1],[ref2],[tipoDocumento],[ref3],
		[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
		[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
		[claseCuenta],[usuario],regId,valorIntMora,tarifa,devolucionCCF
	)
	SELECT DISTINCT
		det.consecutivo,
		det.codigoSAP,
		-- Los movimientos A04 Cuenta pasivo y A10 prescripcion son atipicos en los intereses
		CASE WHEN enc.tipoMovimiento in ('A04','A04_A35','A06_A35') OR enc.tipoMovimiento in ('A10','A10_A35') THEN round(det.importeDocumento ,0)
			ELSE  round (ISNULL(det.importeDocumento, 0) - ISNULL(det.valorIntMora, 0),0) 
		END,
		det.operador,
		'50' AS claveCont,
		det.asignacion,
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
		0, --tarifa
		det.devolucionCCF
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
		0, --tarifa
		det.devolucionCCF
	FROM #IC_Aportes_Det det WITH(NOLOCK) INNER JOIN #IC_Aportes_Enc enc WITH(NOLOCK) ON det.consecutivo = enc.consecutivo
	WHERE claveCont = '40'
	AND valorintmora > 0;

	INSERT INTO #IC_Aportes_Det(
		[consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[ref1],[ref2],[tipoDocumento],[ref3],
		[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
		[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
		[claseCuenta],[usuario],regId,valorIntMora,devolucionCCF
	)
	--Calcula la linea adicional si el registro tiene intereses
		SELECT DISTINCT
		det.consecutivo,
		det.codigoSAP,
		round (ISNULL(det.valorintmora, 0),0),
		det.operador,
		'40' AS claveCont,
		det.asignacion,
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
		det.devolucionCCF
	FROM #IC_Aportes_Det det WITH(NOLOCK) INNER JOIN #IC_Aportes_Enc enc WITH(NOLOCK) ON  det.consecutivo = enc.consecutivo
	WHERE claveCont = '40'
	AND valorintmora > 0
	AND enc.tipoMovimiento IN ('A04', 'A04_A35','A06_A35','A10','A10_A35')




	--SELECT * FROM #IC_Aportes_Enc

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
		WHERE enc.tipoMovimiento IN ('A01_A35')
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
		WHERE enc.tipoMovimiento IN ('A01_A35')
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
		WHERE enc.tipoMovimiento IN ('A02','A02_A35')
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
		WHERE enc.tipoMovimiento IN ('A02','A02_A35')
		AND claveCont = '50'
		AND valorIntMora > 0
			
		----------------- A02_A35-------------------------------------	

		UPDATE det
		SET importedocumento =  round(ISNULL(det.importedocumento,0)-ISNULL(det.valorIntMora, 0),0), 
		noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A02_A35')
		AND claveCont = '40'

		------------------------------------------------------

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
		WHERE enc.tipoMovimiento IN ('A03','A03_A35')
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
		tipoMora = '',
		ref1 = case when @solTipoTransaccion = 'CORRECCION_APORTES' then docorg else ref1 END
 		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A03','A03_A35')
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

		-------A04_A35 ----------------
		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A04_A35')
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
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A04_A35')
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
		---- Calcula en el movimiento A04, sobre los interes en la clave 40 para los No Identificados.
		--tipoInteres = CASE WHEN tipoInteres = 'IM1' THEN 'IN1' 
		--		WHEN tipoInteres = 'IM2' THEN 'IN2' 
		--		WHEN tipoInteres = 'IM3' THEN 'IN3'
		--		WHEN tipoInteres = 'IM4' THEN 'IN4'
		--		WHEN tipoInteres = 'IM5' THEN 'IN5'
		--		END,
		tieneIntereses = 1,
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A04_A35')
		AND claveCont = '40'
		AND valorIntMora > 0
		AND fechaRecaudo IS NULL
		
		UPDATE det
		SET adelantado = '',
		noIdentificado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A04_A35')
		AND claveCont = '40'
		AND valorIntMora > 0 
		AND fechaRecaudo IS NOT NULL

		----------------------

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
		WHERE enc.tipoMovimiento IN ('A05','A05_A35')
		AND claveCont = '50'

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
		WHERE enc.tipoMovimiento IN ('A05_A35')
		AND claveCont = '40'

		--A06_A35 IDENTIFICADO EN LA CORECCION, SIN ENVIAR UN MOVIMIENTO IDENTIFICADO
		IF @solTipoTransaccion = 'CORRECCION_APORTES' AND (SELECT TOP 1 tipoMovimiento FROM sap.IC_Aportes_Det d 
															INNER JOIN #IC_Aportes_Det dt ON  d.asignacion = dt.asignacion	
															INNER JOIN sap.IC_Aportes_Enc e ON d.consecutivo = e.consecutivo 
															WHERE e.tipoMovimiento = 'A06' GROUP BY tipoMovimiento HAVING MAX(d.consecutivo) = MAX(e.consecutivo))= 'A06'
		BEGIN
			
				UPDATE det
				SET noIdentificado = '',
				adelantado = '',
				identificadorDelBanco = '',
				codigoBanco = '',
				transitoria = '',			
				tieneIntereses = 0,
				tipoInteres = '',
				tipoMora = ''
				FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
				WHERE enc.tipoMovimiento = 'A06_A35'
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
				tipoMora = ''			
				FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
				WHERE enc.tipoMovimiento = 'A06_A35'
				AND claveCont = '50'
				AND valorIntMora > 0

				UPDATE det
				SET noIdentificado = det.noIdentificadoOri,
				adelantado = '',
				identificadorDelBanco = '',
				codigoBanco = '',
				transitoria = '',	
				claseDeAporte = '',
				claseDeAportePrescripcion = '',
				tipoMora = '',
				tipoInteres = '',
				tieneIntereses = ''
				FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo										 
				WHERE enc.tipoMovimiento = 'A06_A35' 
				AND det.claveCont = '40' AND tipoInteresOrg IS NOT NULL
				
				     
				UPDATE det
				SET noIdentificado = '',
				adelantado = '',
				identificadorDelBanco = '',
				codigoBanco = '',
				transitoria = '',	
				claseDeAporte = '',
				claseDeAportePrescripcion = '',
				tipoMora = '',
				tipoInteres = det1.tipoInteresOrg
				FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
										 INNER JOIN #IC_Aportes_Det det1 ON enc.consecutivo = det.consecutivo
				WHERE enc.tipoMovimiento = 'A06_A35' AND det.claveCont = '40' AND det.tipoInteresOrg IS  NULL
     
			END

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
		WHERE enc.tipoMovimiento IN ('A06'/*,'A06_A35'*/)
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
		WHERE enc.tipoMovimiento IN ('A06'/*,'A06_A35'*/)
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
		WHERE enc.tipoMovimiento IN ('A06'/*,'A06_A35'*/)
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

		--A07_A35
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
		WHERE enc.tipoMovimiento IN ('A07_A35')
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
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A07_A35')
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
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A08','A08_A35')
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
		WHERE enc.tipoMovimiento IN ('A09','A09_A35')
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
		WHERE enc.tipoMovimiento IN ('A09','A09_A35')
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
		WHERE enc.tipoMovimiento IN ('A10','A10_A35')
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
		WHERE enc.tipoMovimiento IN ('A10','A10_A35')
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
		WHERE enc.tipoMovimiento IN ('A10','A10_A35')
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
		WHERE enc.tipoMovimiento IN ('A10','A10_A35')
		AND claveCont = '40'
		AND tarifa IS NOT NULL

		--A12

		IF((SELECT MAX (tipoMovimiento) FROM #IC_Aportes_Enc WITH(NOLOCK)) = 'A12' AND (SELECT MAX (valorIntMora) FROM #IC_Aportes_Det WITH(NOLOCK) WHERE claveCont = '40')>0)
		BEGIN
		INSERT INTO #IC_Aportes_Det 
		SELECT 	[consecutivo],[codigoSap],round (valorIntMora,0) as [importeDocumento],[operador],[claveCont],[asignacion],[ref1],[ref2],[tipoDocumento],[ref3],
		[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
		[claseDeAportePrescripcion],1 as [tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
		[claseCuenta],[usuario],regId,fechaRecaudo,fechaProcesamiento,EstadoRegistroAporteAportante,FormaReconocimientoAporte,
		apgEstadoAportante,marcaPeriodo,tipoSolicitante,valorIntMora,tarifa,apgOrigenAporte,devolucionCCF,regIdorg,docorg,claseDeAporteorg,tipoInteresOrg,operadorinformacion,noIdentificadoOri
		FROM #IC_Aportes_Det  WHERE claveCont = '40'
		END

		
		
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
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A12'
		AND claveCont = '40' AND valorIntMora <> importeDocumento

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
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A12'
		AND claveCont = '40'AND valorIntMora = importeDocumento

		UPDATE det
		SET noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipointeres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A12'AND valorIntMora = 0
		AND claveCont = '50'

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
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A12' AND valorIntMora > 0
		AND claveCont = '50'

		----------A06_A12-----------------------------

		--A06_A12

		IF((SELECT MAX (tipoMovimiento) FROM #IC_Aportes_Enc WITH(NOLOCK)) = 'A06_A12' AND (SELECT MAX (valorIntMora) FROM #IC_Aportes_Det WITH(NOLOCK) WHERE claveCont = '40')>0)
		BEGIN
		INSERT INTO #IC_Aportes_Det 
		SELECT 	[consecutivo],[codigoSap],round (valorIntMora,0) as [importeDocumento],1 as [operador],[claveCont],[asignacion],[ref1],[ref2],[tipoDocumento],[ref3],
		[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
		[claseDeAportePrescripcion],1 as [tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
		[claseCuenta],[usuario],regId,fechaRecaudo,fechaProcesamiento,EstadoRegistroAporteAportante,FormaReconocimientoAporte,
		apgEstadoAportante,marcaPeriodo,tipoSolicitante,valorIntMora,tarifa,apgOrigenAporte,devolucionCCF,regIdorg,docorg,claseDeAporteorg,tipoInteresOrg,operadorinformacion,noIdentificadoOri
		FROM #IC_Aportes_Det WITH(NOLOCK) WHERE claveCont = '50' AND valorIntMora>0
		END
	
		
		UPDATE det
		SET adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipointeres = '',
		tipoMora = '',
		importeDocumento = round(importeDocumento - valorIntMora,0),
		claseDeAporte = '',
		claseDeAportePrescripcion = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK)  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A06_A12'
		AND claveCont = '40' AND valorIntMora <> importeDocumento

		UPDATE det
		SET 
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
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A06_A12'
		AND claveCont = '40'AND valorIntMora = importeDocumento

		UPDATE det
		SET adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipointeres = '',
		tipoMora = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = ''
		FROM #IC_Aportes_Enc enc  INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A06_A12'AND valorIntMora = 0
		AND claveCont = '50'

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
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A06_A12' AND valorIntMora > 0
		AND claveCont = '50'


		UPDATE det
		SET operador = '',
		clavecont = '40',
		noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		--tieneIntereses = 0,
		--tipointeres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A06_A12' AND valorIntMora > 0
		AND claveCont = '50' AND operador = 1

		UPDATE enc
		SET tipoMovimiento = 'A12'
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
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
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A07_A12')
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
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A07_A12')
		AND claveCont = '40'


		UPDATE enc
		SET tipoMovimiento = 'A12'
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento = 'A07_A12' 

		--A15
		UPDATE det
		SET clavecont = '31',
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
		AND claveCont IN ('50')

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
		WHERE enc.tipoMovimiento IN ('A16')
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
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A16')
		AND claveCont = '40'

		--A17
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
		tipointeres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A17')
		AND claveCont IN ('40')

		UPDATE det
		SET claveCont = '40',
		--noIdentificado = 'NI',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',
		claseDeAportePrescripcion = '',
		tieneIntereses = 0,
		tipointeres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK)ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A17')
		AND claveCont IN ('50')
		AND valorIntMora = 0
		
		UPDATE det
		SET claveCont = '40',
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
		WHERE enc.tipoMovimiento IN ('A17')
		AND claveCont IN ('50')
		AND valorIntMora > 0

		--A19
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
		WHERE enc.tipoMovimiento IN ('A19')
		AND claveCont = '50'

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
		SET  claveCont = '31',
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
		AND claveCont = '40'
		AND valorIntMora > 0

		UPDATE det
		SET  claveCont = '40',
		noIdentificado = '',
		adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A20')
		AND claveCont = '50'
		AND valorIntMora = 0

		UPDATE det
		SET claveCont = '40',
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
		WHERE enc.tipoMovimiento IN ('A20')
		AND claveCont = '50'
		AND valorIntMora > 0

		--A21
		UPDATE det
		SET claveCont = '31',
		noIdentificado = '',
		adelantado = '',
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
		AND claveCont = '50'

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

		--A34
		UPDATE det
		SET noIdentificado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',					-- por definir
		claseDeAportePrescripcion = '',		-- por definir
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A34')
		AND claveCont = '50'

		UPDATE det
		SET adelantado = '',
		identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		claseDeAporte = '',					-- por definir
		claseDeAportePrescripcion = '',		-- por definir
		tieneIntereses = 0,
		tipoInteres = '',
		tipoMora = '',
		ref1 = case when @solTipoTransaccion = 'CORRECCION_APORTES' then docorg else ref1 END
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK)  ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A34')
		AND claveCont = '40'

		--A35
		UPDATE det
		SET identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A35')
		AND claveCont = '50'

		UPDATE det
		SET identificadorDelBanco = '',
		codigoBanco = '',
		transitoria = '',
		tipoMora = ''
		FROM #IC_Aportes_Enc enc INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE enc.tipoMovimiento IN ('A35')
		AND claveCont = '40'

		-------36
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

		-----A37
		UPDATE det
		SET  claveCont = '31',
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
		AND claveCont = '40'
		AND valorIntMora > 0

		UPDATE det
		SET  claveCont = '40',
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
		AND claveCont = '50'
		AND valorIntMora = 0

		UPDATE det
		SET claveCont = '40',
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
		AND claveCont = '50'
		AND valorIntMora > 0


		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		-- ENVIAR EL ACREEDOR SI NO EXISTE PARA EL MOVIMIENTO  CLAVE CONTABLE 31
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		-- Calcula el codigo SAP para el movimiento 31, cambia la clase de cuenta a 'K'.
		UPDATE A31
		SET codigoSAP = codsap.codigoSAP,
		claseCuenta  = 'K'
		FROM [sap].[CodSAPGenesysAcreedor] codsap, #IC_Aportes_Det A31
		WHERE codsap.codigoGenesys = A31.codigoGenesys
		AND A31.claveCont = '31'
		


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
		SELECT @numeroDocumento = ref1, @tipoDocumento = tipoDocumento FROM #IC_Aportes_Det WHERE claveCont = '31'
		

--SELECT * FROM #IC_Aportes_Enc
------ inicio para mov A35 --------------------

IF EXISTS (SELECT * FROM #IC_Aportes_Enc enc WHERE enc.tipoMovimiento in ('A02_A35','A04_A35','A06_A35','A10_A35') AND @solTipoTransaccion = 'CORRECCION_APORTES')
BEGIN

	INSERT INTO #IC_Aportes_Det(
		[consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[ref1],[ref2],[tipoDocumento],[ref3],
		[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
		[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],[codigoGenesys],[tipo],
		[claseCuenta],[usuario],regId,valorIntMora,devolucionCCF)

-----------------Para A02_A35 -----------------------------
	SELECT DISTINCT
		det.consecutivo,
		det.codigoSAP,
		round (ISNULL(det.valorintmora, 0),0) as importeDocumento,
		det.operador,
		'40' AS claveCont,
		det.asignacion,
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
		det.devolucionCCF
	FROM #IC_Aportes_Det det WITH(NOLOCK) INNER JOIN #IC_Aportes_Enc enc WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
	WHERE claveCont = '50' AND enc.tipoMovimiento = 'A02_A35'
	AND valorintmora > 0

END

-----------------------------------------------

		IF ( (SELECT top 1  enc.tipoMovimiento FROM #IC_Aportes_Enc enc)  in ('A01_A35','A02_A35','A03_A35','A04_A35','A05_A35','A06_A35','A07_A35','A08_A35','A09_A35','A10_A35') AND @solTipoTransaccion = 'CORRECCION_APORTES' )
		BEGIN 
		UPDATE #IC_Aportes_Enc SET tipoMovimiento = 'A35';
		Update det SET ref1 = per.perNumeroIdentificacion, tipoDocumento = ma.TipoIdHomologado , codigogenesys = per.perId, 
		tipo=CASE WHEN per.perTipoIdentificacion = 'NIT' THEN 'E' ELSE 'P' END
		FROM pila.RegistroGeneral reg
		INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON reg.regNumPlanilla = det.asignacion
		--INNER JOIN AporteGeneral apg ON reg.regId = apg.apgRegistroGeneral
		INNER JOIN persona per WITH(NOLOCK) ON reg.regNumeroIdentificacionAportante = per.perNumeroIdentificacion AND reg.regTipoIdentificacionAportante = per.perTipoIdentificacion
		INNER JOIN sap.MaestraTiposIdentificacion ma WITH(NOLOCK) ON per.perTipoIdentificacion = ma.TipoIdGenesys
		WHERE det.clavecont = '40'

		UPDATE det SET ref3 =  LEFT(per.perRazonSocial,20), codigoGenesys =  perId, tipoDocumento =  ma.TipoIdHomologado, tipo=CASE WHEN per.perTipoIdentificacion = 'NIT' THEN 'E'
			ELSE 'P' END FROM #IC_Aportes_Det det 
													INNER JOIN Persona per ON per.perNumeroIdentificacion = det.ref1 
													INNER JOIN sap.MaestraTiposIdentificacion ma WITH(NOLOCK) ON per.perTipoIdentificacion = ma.TipoIdGenesys
													WHERE det.claveCont = '50' 

		UPDATE det SET ref3 = Left(per.perRazonSocial,20), tipodocumento = ma.TipoIdHomologado FROM #IC_Aportes_Det det
		INNER JOIN aportegeneral apg WITH(NOLOCK) ON apg.apgregistrogeneral = det.regid
		INNER JOIN empresa emp WITH(NOLOCK) ON apg.apgempresa = emp.empid
		INNER JOIN persona per WITH(NOLOCK) ON emp.emppersona = per.perid
		INNER JOIN sap.maestratiposidentificacion ma WITH(NOLOCK) ON per.pertipoidentificacion = ma.TipoIdGenesys 
		WHERE det.clavecont = '50'

		UPDATE det SET codigoGenesys = per.perId FROM #IC_Aportes_Det det
		INNER JOIN aportegeneral apg WITH(NOLOCK) ON apg.apgregistrogeneral = det.regid
		INNER JOIN empresa emp WITH(NOLOCK) ON apg.apgempresa = emp.empid
		INNER JOIN persona per WITH(NOLOCK) ON emp.emppersona = per.perid
		WHERE det.clavecont = '50'


		END
		
-------- fin A35 --------------	


---- Actualizar el consecutivo definitivo en la tabla de integracion de encabezado con el detalle
		SELECT @consecutivo = ISNULL(MAX(consecutivo), 0) FROM sap.IC_Aportes_Enc
		--SELECT @consecutivodet = ISNULL( max(consecutivo),0) FROM sap.IC_Aportes_Det


		UPDATE #IC_Aportes_Det
		SET consecutivo = consecutivo + @consecutivo

		UPDATE #IC_Aportes_Enc
		SET consecutivo = consecutivo + @consecutivo

-------------------------------------Actualizacion Clave contable 40 devoluciones tercero pagador------------------------------
SELECT @DevolucionT = (SELECT TOP 1 CASE WHEN reg.regNumPlanilla = det.asignacion AND epa.epaEstadoEntidadPagadora  = 'HABILITADO' AND  reg.regTipoPlanilla ='Y'
						THEN 1 ELSE 0 END	FROM pila.RegistroGeneral reg 
						INNER JOIN #IC_Aportes_Det det WITH (NOLOCK) ON  reg.regId =  det.regId 
						INNER JOIN core.dbo.AporteGeneral apg WITH (NOLOCK) ON reg.regId = apg.apgRegistroGeneral
						INNER JOIN core.dbo.Persona per WITH(NOLOCK) ON reg.regTipoIdentificacionAportante = per.perTipoIdentificacion and reg.regNumeroIdentificacionAportante =  per.perNumeroIdentificacion
						INNER JOIN core.dbo.Empresa emp WITH(NOLOCK) ON per.perId = emp.empPersona
						INNER JOIN core.dbo.EntidadPagadora epa WITH(NOLOCK) ON emp.empId = epa.epaEmpresa
						INNER JOIN core.sap.MaestraTiposIdentificacion mi WITH (NOLOCK) ON reg.regTipoIdentificacionAportante = mi.tipoIdGenesys		
						WHERE reg.regNumPlanilla = det.asignacion AND epa.epaEstadoEntidadPagadora  = 'HABILITADO' AND reg.regTipoPlanilla ='Y')


	IF  @DevolucionT = 1
	BEGIN
		UPDATE #IC_Aportes_Det
		SET ref1 =dev.ref1,
		tipoDocumento =dev.tipoDocumento,
		ref3 = dev.ref3,
		codigoGenesys = dev.codigoGenesys,
		tipo = dev.tipo
		FROM(
		SELECT reg.regNumeroIdentificacionAportante AS ref1, mi.tipoDocumentoCaja AS tipoDocumento,
				LEFT(reg.regNombreAportante, 20) AS ref3, regId, perId AS codigoGenesys, 
				CASE WHEN mi.tipoDocumentoCaja = 'NIT' THEN 'E' ELSE 'P' END AS	tipo FROM core.dbo.Solicitud sol WITH(NOLOCK)	
				INNER JOIN core.dbo.SolicitudDevolucionAporte sda WITH(NOLOCK) ON sol.solId = sda.sdaSolicitudGlobal
				INNER JOIN core.dbo.DevolucionAporte dap WITH(NOLOCK) ON sda.sdaDevolucionAporte = dap.dapid
				INNER JOIN core.dbo.DevolucionAporteDetalle dad WITH(NOLOCK) ON dap.dapid = dad.dadDevolucionAporte
				INNER JOIN core.dbo.MovimientoAporte moa WITH(NOLOCK) ON dad.dadMovimientoAporte = moa.moaid
				INNER JOIN core.dbo.AporteGeneral apg WITH(NOLOCK) ON moa.moaAporteGeneral = apg.apgid
				INNER JOIN pila.RegistroGeneral reg ON apg.apgRegistroGeneral = reg.regId 
				INNER JOIN persona per WITH(NOLOCK) ON  regNumeroIdentificacionAportante = per.perNumeroIdentificacion
				INNER JOIN core.[sap].[MaestraIdentificacion] mi WITH(NOLOCK) ON reg.regTipoIdentificacionAportante = mi.tipoIdGenesys
				WHERE solTipoTransaccion = @solTipoTransaccion AND  solNumeroRadicacion = @solNumeroRadicacion) dev
				INNER JOIN #IC_Aportes_Det det WITH(NOLOCK) ON dev.regId = det.regId
				INNER JOIN #IC_Aportes_Enc enc WITH(NOLOCK) ON det.consecutivo = enc.consecutivo
				WHERE claveCont = '40' AND enc.tipoMovimiento IN ('A15','A16', 'A17', 'A19','A20', 'A21','comentarA36','A37')  
							   	
	END

	select referencia  from #IC_Aportes_Enc
--------------------------------------------------------------FIN--------------------------------------------------------------------------------

	--comentarear	

/*

	IF ((SELECT referencia FROM #IC_Aportes_Enc ) IS NOT NULL )
	BEGIN
	

	--	PRINT 'SI FUNCIONA'
		-----------------




		IF NOT EXISTS (SELECT acre.codigoSAP FROM [sap].[CodSAPGenesysAcreedor] acre INNER JOIN Persona per ON acre.codigoGenesys = per.perId WHERE per.perNumeroIdentificacion = @numeroDocumento) BEGIN
			EXEC [sap].[USP_GetAcreedoresAportes_DocumentoId] @numeroDocumento, @tipoDocumento, @usuario, @planilla
		END
	


		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		-- FIN ENVIAR EL ACREEDOR SI NO EXISTE PARA EL MOVIMIENTO 31
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
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

		--IF EXISTS (SELECT * FROM SAP.IC_Aportes_Enc enca INNER JOIN #IC_Aportes_Det deta ON enca.consecutivo = deta.consecutivo)
		--BEGIN
		INSERT INTO core.sap.IC_Aportes_Det ([consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[ref1],[ref2],[tipoDocumento],[ref3],
				[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
				[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
				[claseCuenta],[usuario],[fechaEjecucion])
		SELECT [consecutivo],[codigoSap],CAST(CAST([importeDocumento] AS BIGINT) AS VARCHAR(18)),[operador],[claveCont],[asignacion],[ref1],[ref2],[tipoDocumento],[ref3],
				[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
				[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
				[claseCuenta],[usuario],GETDATE() -'05:00:00'
        FROM #IC_Aportes_Det  WITH(NOLOCK)
		WHERE importeDocumento <> 0  ---  para cuando el registro viene sin detalle y que no traiga lineas en valores 0 08/10/2022
		--END
		
		
		-- Insercion en la tabla de control de planillas

		INSERT INTO core.sap.PlanillasCtrl
		SELECT DISTINCT LEFT(det.asignacion, 12), 1, det.ref1, det.tipoDocumento, SAP.GetLocalDate(), 'APORTES', det.ref3 + enc.observacion, enc.tipoMovimiento,estadoaporteaportantetercero,operadorinformacion,1 
		FROM #IC_Aportes_Det det WITH(NOLOCK)
		INNER JOIN #IC_Aportes_Enc enc WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
		WHERE det.EstadoRegistroAporteAportante IS NOT NULL

		

		-- Insercion en la tabla de control de solicitudes Devoluciones
		INSERT INTO core.sap.ContablesCtrl
		SELECT DISTINCT @solNumeroRadicacion, 1 as estado, perNumeroIdentificacion, perTipoIdentificacion,SAP.GetLocalDate(),'APORTES',solTipoTransaccion 
		FROM Solicitud sol WITH(NOLOCK)
		INNER JOIN  SolicitudDevolucionAporte sda WITH(NOLOCK) ON sda.sdaSolicitudGlobal = sol.solId
		INNER JOIN Persona per WITH(NOLOCK) ON sda.sdaPersona = per.perId WHERE solNumeroRadicacion = @solNumeroRadicacion AND solTipoTransaccion = 'DEVOLUCION_APORTES'


		-- Insercion en la tabla de control de solicitudes Correcciones
		INSERT INTO core.sap.ContablesCtrl
		SELECT DISTINCT solNumeroRadicacion, 1 as estado, perNumeroIdentificacion, perTipoIdentificacion,SAP.GetLocalDate(),'APORTES',solTipoTransaccion 
		FROM Solicitud sol WITH(NOLOCK)
		INNER JOIN  SolicitudCorreccionAporte sca WITH(NOLOCK) ON sca.scaSolicitudGlobal = sol.solId
		INNER JOIN Persona per WITH(NOLOCK) ON sca.scaPersona = per.perId WHERE solNumeroRadicacion = @solNumeroRadicacion AND solTipoTransaccion = 'CORRECCION_APORTES'

	END
	 
END
*/

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
 SELECT @planilla,@planillaN,@regId,@solNumeroRadicacion,@solTipoTransaccion,@operadorinformacion, ERROR_NUMBER(),ERROR_SEVERITY(),ERROR_STATE(),ERROR_PROCEDURE(),ERROR_LINE(),ERROR_MESSAGE(),GETDATE() -'05:00:00'
 

END CATCH

	--DELETE FROM sap.ejecucion_aportes
	--END
	--ELSE BEGIN
	--	SELECT 'El proceso ya se encuentra en ejecucion'
	--END
	

--DROP TABLE core.sap.ejecucion_int_aportes2
--	END
--	ELSE BEGIN
--		SELECT 'El proceso ya se encuentra en ejecucion'
--	END
END


