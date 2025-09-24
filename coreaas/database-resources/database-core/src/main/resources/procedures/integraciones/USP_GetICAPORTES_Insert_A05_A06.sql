CREATE OR ALTER    PROCEDURE [sap].[USP_GetICAPORTES_Insert_A05_A06] 
	@planilla VARCHAR(20),	@planillaN VARCHAR(20),	@estadoAporte VARCHAR (20),	@apgid BIGINT,	@solNumeroRadicacion VARCHAR (30),	
	@solTipoTransaccion VARCHAR (30),	@operadorinformacion INT
AS
BEGIN
SET NOCOUNT ON; 


	DECLARE @regIdT bigint = (select apgRegistroGeneral  from AporteGeneral where apgId = @apgid)

	SELECT regid, regNumPlanilla, regOperadorInformacion,regValTotalApoObligatorio, regValorIntMora,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
			regTipoPlanilla,regDateTimeInsert,regOUTEstadoArchivo,regNumPlanillaAsociada,regNombreAportante,regPeriodoAporte
	INTO #registrogeneralA05			
	FROM core.pila.RegistroGeneral	
	WHERE (regnumplanilla = @planilla AND regOperadorInformacion = @operadorinformacion) OR (regid = @regIdT AND regid <> 0) 	


	DECLARE @planillaA BIGINT,@referenciaNum BIGINT,  @consecutivo BIGINT, @consecutivoTemp  BIGINT, @interes VARCHAR(5), @regid BIGINT,@fechafi DATE

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

			regId bigint NULL,
			[fechaRecaudo] [date] NULL,
			[fechaProcesamiento] [date] NULL,
			[EstadoRegistroAporteAportante] [varchar](30) NULL,
			[TipoSolicitante][VARCHAR](30) NULL,
			[interes]numeric (21,5) NULL,
			[Tarifa]numeric (7,5) NULL,
			[operadorinformacion] INT NULL,
			[apgid] varchar (20)
			
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

		

		CREATE TABLE #MarcaPeriodo
		(
			MesAporte SMALLINT,
			AnAporte INT,
			PeriodoAporte VARCHAR(7),
			MesRecaudo SMALLINT,
			AnRecaudo INT,
			FechaRecaudo DATE,
			Numeroplanilla VARCHAR(30),
			apgid VARCHAR(30)
		)

		CREATE TABLE #APLICA
		(
			[Aplica] [INT] NULL,
			[Planilla][varchar](30) NULL,
			[regid][BIGINT])

	-- 7860565988 51, 9441049095 84, A05
	--60097017 89, 9441619214 84,  60220394 89,  A06
	BEGIN TRY 
		BEGIN TRANSACTION
	  /*
		SELECT regid, regNumPlanilla, regOperadorInformacion,regValTotalApoObligatorio, regValorIntMora,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,regNombreAportante
		INTO #registrogeneral
		FROM core.pila.RegistroGeneral
	*/
	--- LLENAR VARIABLE PARA VEIFICAR ESTADO DEL ARCHIVO EN PLANILLA
	
	-----------INCIO--------------- Validar periodo  --------------------------------------------------------------------------

	if (select regTipoPlanilla from pila.RegistroGeneral where regNumPlanilla = @planilla ) <> 'N'
		BEGIN

			INSERT INTO #MarcaPeriodo
			SELECT TOP 1 SUBSTRING(regPeriodoAporte,6,7) MesAporte, left(regPeriodoAporte,4) AnoAporte,regPeriodoAporte, 
			SUBSTRING(CONVERT(varchar, (apg.apgFechaRecaudo)),6,2) as MesRecaudo,
			SUBSTRING(CONVERT (varchar, (apg.apgFechaProcesamiento),9),7,5) as AnoRecaudo,apg.apgFechaRecaudo,reg.regnumplanilla,apgid 
			FROM #registrogeneralA05 reg
			INNER JOIN core.dbo.AporteGeneral apg WITH (NOLOCK) ON reg.regId = apg.apgRegistroGeneral
			LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgid = apd.apdAporteGeneral
			WHERE ((reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @operadorinformacion) OR (apg.apgid = @apgid))
			AND apgEstadoRegistroAporteAportante = 'RELACIONADO'

		--SELECT * FROM  #MarcaPeriodo

		--4992355	RELACIONADO
		-- VALIDACIoN PLANILLAS Y RECAUDO MANUAL
			INSERT INTO #APLICA
			SELECT CASE WHEN apg.apgEstadoRegistroAporteAportante = 'RELACIONADO' AND (ro.roaEstadoAfiliado <> 'ACTIVO' or ro.roaEstadoAfiliado is null )
			 AND (((mp.AnAporte = mp.AnRecaudo  AND mp.MesAporte = mp.MesRecaudo) OR  (mp.AnAporte <= mp.AnRecaudo  AND mp.MesAporte <= mp.MesRecaudo) OR (mp.AnAporte <= mp.AnRecaudo ))
			 OR ((mp1.AnAporte = mp1.AnRecaudo  AND mp1.MesAporte = mp1.MesRecaudo) OR  (mp1.AnAporte <= mp1.AnRecaudo  AND mp1.MesAporte <= mp1.MesRecaudo) OR (mp1.AnAporte <= mp1.AnRecaudo))
			 OR (mp.MesAporte > mp.MesRecaudo)) 
			THEN 1 ELSE 0 END AS Aplica,  reg.regnumplanilla AS Planilla, regId FROM #registrogeneralA05 reg
					INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on regId = apgRegistroGeneral
					LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgid = apd.apdAporteGeneral
					LEFT JOIN #MarcaPeriodo mp WITH(NOLOCK) ON reg.regNumPlanilla = mp.Numeroplanilla
					LEFT JOIN #MarcaPeriodo mp1 WITH(NOLOCK) ON apg.apgId = mp1.apgid 
					LEFT JOIN persona p WITH(NOLOCK) on apg.apgPersona = p.perId
					LEFT JOIN afiliado af WITH(NOLOCK) on p.perid = af.afiPersona
					LEFT JOIN RolAfiliado ro WITH(NOLOCK) on  af.afiId = ro.roaAfiliado  AND apdTipoCotizante = roaTipoAfiliado 
					WHERE (reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @operadorinformacion) OR (apg.apgId = @apgid) 
					GROUP BY apg.apgEstadoRegistroAporteAportante, apgFormaReconocimientoAporte,apgMarcaPeriodo,apgEstadoAportante,
					reg.regNumPlanilla,regId,mp1.AnAporte,mp1.AnRecaudo,mp1.MesAporte,mp1.MesRecaudo,mp.AnAporte,mp.AnRecaudo,mp.MesAporte,mp.MesRecaudo,roaFechaAfiliacion,roaEstadoAfiliado
					,perNumeroIdentificacion
				
		END
		ELSE
		BEGIN

			INSERT INTO #MarcaPeriodo
			SELECT TOP 1 SUBSTRING(regPeriodoAporte,6,7) MesAporte, left(regPeriodoAporte,4) AnoAporte,regPeriodoAporte, 
			SUBSTRING(CONVERT(varchar, (apg.apgFechaRecaudo)),6,2) as MesRecaudo,
			SUBSTRING(CONVERT (varchar, (apg.apgFechaProcesamiento),9),7,5) as AnoRecaudo,apg.apgFechaRecaudo,reg.regnumplanilla,apgid 
			FROM #registrogeneralA05 reg
			INNER JOIN core.dbo.aporteDetalladoRegistroControlN	apdn  WITH(NOLOCK) ON reg.regId = apdn.registroGeneralNuevo
			INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on apdn.apdAporteGeneral = apg.apgId
			LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgid = apd.apdAporteGeneral
			WHERE ((reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @operadorinformacion) OR (apg.apgid = @apgid))
			AND apgEstadoRegistroAporteAportante = 'RELACIONADO'


			INSERT INTO #APLICA
				SELECT CASE WHEN apg.apgEstadoRegistroAporteAportante = 'RELACIONADO' AND (ro.roaEstadoAfiliado <> 'ACTIVO' or ro.roaEstadoAfiliado is null )
				 AND (((mp.AnAporte = mp.AnRecaudo  AND mp.MesAporte = mp.MesRecaudo) OR  (mp.AnAporte <= mp.AnRecaudo  AND mp.MesAporte <= mp.MesRecaudo) OR (mp.AnAporte <= mp.AnRecaudo ))
				 OR ((mp1.AnAporte = mp1.AnRecaudo  AND mp1.MesAporte = mp1.MesRecaudo) OR  (mp1.AnAporte <= mp1.AnRecaudo  AND mp1.MesAporte <= mp1.MesRecaudo) OR (mp1.AnAporte <= mp1.AnRecaudo))
				 OR (mp.MesAporte > mp.MesRecaudo)) 
				THEN 1 ELSE 0 END AS Aplica,  reg.regnumplanilla AS Planilla, regId FROM #registrogeneralA05 reg
						INNER JOIN core.dbo.aporteDetalladoRegistroControlN	apdn  WITH(NOLOCK) ON reg.regId = apdn.registroGeneralNuevo
						INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on apdn.apdAporteGeneral = apg.apgId
						LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgid = apd.apdAporteGeneral
						LEFT JOIN #MarcaPeriodo mp WITH(NOLOCK) ON reg.regNumPlanilla = mp.Numeroplanilla
						LEFT JOIN #MarcaPeriodo mp1 WITH(NOLOCK) ON apg.apgId = mp1.apgid 
						LEFT JOIN persona p WITH(NOLOCK) on apg.apgPersona = p.perId
						LEFT JOIN afiliado af WITH(NOLOCK) on p.perid = af.afiPersona
						LEFT JOIN RolAfiliado ro WITH(NOLOCK) on  af.afiId = ro.roaAfiliado  AND apdTipoCotizante = roaTipoAfiliado 
						WHERE (reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @operadorinformacion) OR (apg.apgId = @apgid) 
						GROUP BY apg.apgEstadoRegistroAporteAportante, apgFormaReconocimientoAporte,apgMarcaPeriodo,apgEstadoAportante,
						reg.regNumPlanilla,regId,mp1.AnAporte,mp1.AnRecaudo,mp1.MesAporte,mp1.MesRecaudo,mp.AnAporte,mp.AnRecaudo,mp.MesAporte,mp.MesRecaudo,roaFechaAfiliacion,roaEstadoAfiliado
						,perNumeroIdentificacion
		END
								
  DELETE #APLICA WHERE Aplica = 0
	--- PARA ELIMINAR DUPLICADOS RECAUDO MANUAL	   				
	IF(SELECT top 1 apgid FROM #MarcaPeriodo) = (SELECT top 1 asignacion FROM sap.IC_Aportes_Enc enc  INNER JOIN SAP.IC_Aportes_Det det ON  enc.consecutivo = det.consecutivo WHERE asignacion = concat ('M', @apgid) AND tipoMovimiento IN('A05', 'A06') AND estadoReg = 'S')
	BEGIN
	  DELETE FROM #APLICA
	END
	---ELIMINAR DUPLICADOS PLANILLAS
	
	IF(SELECT top 1 Numeroplanilla FROM #MarcaPeriodo) = (SELECT top 1 asignacion FROM sap.IC_Aportes_Enc enc  INNER JOIN SAP.IC_Aportes_Det det ON  enc.consecutivo = det.consecutivo WHERE asignacion = @planilla AND tipoMovimiento IN('A05', 'A06') AND estadoReg = 'S')
	BEGIN
	  DELETE FROM #APLICA
	END
	
	-- ASIGNAR VARIABLE A REGID SI ES MANUAL
	IF (SELECT TOP 1 Planilla FROM #APLICA ) IS NULL BEGIN  SELECT @regid = (SELECT regid FROM #APLICA) END

	IF (SELECT MAX(Aplica) FROM #APLICA) = 1

	BEGIN

		IF(SELECT TOP 1 regValTotalApoObligatorio FROM  #registrogeneralA05 WHERE regValTotalApoObligatorio <> 0 AND (regNumPlanilla = @planilla AND regOperadorInformacion = @operadorinformacion) OR (regid = @regid AND regid <> 0)) <> 0 --AND (SELECT  regValorIntMora FROM  core.pila.RegistroGeneral WHERE regNumPlanilla = @regNumPlanilla AND regOperadorInformacion = @operadorinformacion ) <> 0
		BEGIN
		-- DETALLE: reconoce los registros de PILA
		print 'Ingresa'
		if (select regTipoPlanilla from pila.RegistroGeneral where regNumPlanilla = @planilla ) <> 'N'
			BEGIN
			----------------------------------------------------------Insert planila-------------------------------------------------------------------------
				INSERT INTO #IC_Aportes_Det 
				 SELECT DISTINCT
						ROW_NUMBER() OVER(ORDER BY reg.regId ASC) AS consecutivo,
						'' AS codigoSap, 
						 apd.apdAporteObligatorio  AS ImporteDocumento,
						'' AS operador,			
						'40' AS claveCont,
						 ISNULL(reg.regnumplanilla,'')  AS asignacion,
						 ''as textoposicion,
						reg.regNumeroIdentificacionAportante AS ref1,
						'' AS ref2,
						TipoIdHomologado AS tipoDocumento,
						LEFT(reg.regNombreAportante, 20) AS ref3,
						'' AS noIdentificado,
						'' AS adelantado,
						CASE WHEN apg.apgCuentaBancariaRecaudo ='5' THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(cb.NUMERO_CUENTA, '')),'-', ''))
							 WHEN apg.apgCodEntidadFinanciera IN('51','') THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''))
							 WHEN apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '' THEN REPLACE(cb.NUMERO_CUENTA,'-', '')
							 WHEN (apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo IN ('1','2','3','4') THEN REPLACE(REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', ''), '000000', '')
							 WHEN ( apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo = ('5') THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', ''))
							 WHEN apg.apgCodEntidadFinanciera IN('7','')  THEN REPLACE(REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''), '000000', '')
							  ELSE apg.apgNumeroCuenta END AS identificadorDelBanco,
						CASE WHEN (apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') and apg.apgCuentaBancariaRecaudo IS NOT NULL AND apg.apgCuentaBancariaRecaudo = 5 THEN ce.codigoEntidad --agregado con tabla de homologacion core.sap.MaestracodigoEntidad 31/10/2022
							 WHEN (apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo IS NOT NULL  THEN CONCAT ( '0', CAST(ce.codigoEntidad AS VARCHAR(2)))
							 WHEN apg.apgCuentaBancariaRecaudo IN ('1','2','3','4') AND apg.apgNumeroCuenta IS NULL THEN '7' -- agregado posterior a gap vuenta bancaria 18/10/2022
							 WHEN apg.apgCuentaBancariaRecaudo = ('5') AND apg.apgNumeroCuenta IS NULL then '51' -- agregado posterior a gap vuenta bancaria 18/10/2022
							 WHEN LEN(apg.apgCodEntidadFinanciera) = 1 THEN CONCAT('0', CAST(apg.apgCodEntidadFinanciera AS VARCHAR(2))) ELSE CAST(apg.apgCodEntidadFinanciera AS VARCHAR(2)) END AS codigoBanco,
						CASE WHEN apg.apgModalidadRecaudoAporte IN('MANUAL', 'PILA', 'PILA_MANUAL') THEN '3' ELSE '9' END AS transitoria,
						'' AS claseDeAporte,
						'' AS claseDeAportePrescripcion,
						0 AS tieneIntereses,
						'' AS tipoInteres,
						0 AS correccion,			-- Se define con los registros en la tabla Movimientoaporte en moaTipoAjuste = ''CORRECCION_A_LA_BAJA' va a ser anulacion.
						'' AS tipoMora,				-- Solo aplica para la INTEGRACIoN DE CARTERA
						0 AS id,
						NULL AS codigoGenesys,
						'' AS tipo,
						'S' AS claseCuenta,
						'soportetecnico@asopagos.com' AS usuario,
						reg.regid as regId,
						apg.apgFechaRecaudo AS fechaRecaudo, 
						CASE WHEN [SAP].[calcularPrimerDiaHabilMes]() = [SAP].[calcularSiguienteDiaHabil]() AND DAY(DATEADD(HOUR,-5,GETDATE())) >= [SAP].[calcularPrimerDiaHabilMes]() AND DATEDIFF(DAY, apg.apgFechaRecaudo, DATEADD(HOUR,-5,GETDATE())) > 0
							THEN EOMONTH(DATEADD(HOUR,-5,GETDATE()),-1)
							ELSE DATEADD(HOUR,-5,GETDATE()) END AS fechaProcesamiento,
						apg.apgEstadoRegistroAporteAportante AS EstadoRegistroAporteAportante,
						apg.apgTipoSolicitante AS TipoSolicitante,
						apdValorIntMora AS interes,
						apd.apdTarifa AS Tarifa,
						reg.regOperadorInformacion as [operadorinformacion],
						apg.apgId AS apgid
					FROM #registrogeneralA05 reg
					INNER JOIN core.sap.MaestraTiposIdentificacion mai ON reg.regTipoIdentificacionAportante = mai.TipoIdGenesys
					INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on reg.regId = apgRegistroGeneral
					INNER JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgid = apd.apdAporteGeneral	
					LEFT JOIN core.dbo.CUENTAS_BANCARIAS cb WITH(NOLOCK) on apg.apgCuentaBancariaRecaudo = cb.ID 
					LEFT JOIN core.sap.MaestracodigoEntidad ce WITH(NOLOCK) on cb.ID = ce.idCuentasBancarias 
					LEFT JOIN persona p WITH(NOLOCK) on apg.apgPersona = p.perId
					LEFT JOIN afiliado af WITH(NOLOCK) on p.perid = af.afiPersona
					LEFT JOIN RolAfiliado ro WITH(NOLOCK) on  af.afiId = ro.roaAfiliado  AND apdTipoCotizante = roaTipoAfiliado 
					WHERE  apg.apgEstadoRegistroAporteAportante = 'RELACIONADO' 
					AND ((roaEstadoAfiliado IS NULL OR roaEstadoAfiliado  = 'INACTIVO') AND apgEstadoAportante <> 'ACTIVO')
					AND ((reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @operadorinformacion) OR (regId = @regid)	)
					AND (round(reg.regValTotalApoObligatorio + (ISNULL(reg.regValorIntMora, 0)),0))<>0
					GROUP BY  reg.regId, reg.regValTotalApoObligatorio,reg.regnumplanilla,reg.regNumeroIdentificacionAportante,TipoIdHomologado,reg.regNombreAportante,apg.apgNumeroCuenta,
					cb.NUMERO_CUENTA,apg.apgCuentaBancariaRecaudo,apg.apgCodEntidadFinanciera,apg.apgCuentaBancariaRecaudo,apg.apgCodEntidadFinanciera,apg.apgModalidadRecaudoAporte,
					apg.apgFechaRecaudo,apg.apgEstadoRegistroAporteAportante,apg.apgTipoSolicitante,apd.apdTarifa,ce.codigoEntidad, reg.regOperadorInformacion, apgId,
					apdAporteObligatorio, apdValorIntMora
			END
			ELSE
			BEGIN
			INSERT INTO #IC_Aportes_Det 
				 SELECT DISTINCT
						ROW_NUMBER() OVER(ORDER BY reg.regId ASC) AS consecutivo,
						'' AS codigoSap, 
						sum(diferenciaAporteCot)  AS ImporteDocumento,
						'' AS operador,			
						'40' AS claveCont,
						 ISNULL(reg.regnumplanilla,'')  AS asignacion,
						 '' as textoposicion,
						reg.regNumeroIdentificacionAportante AS ref1,
						'' AS ref2,
						TipoIdHomologado AS tipoDocumento,
						LEFT(reg.regNombreAportante, 20) AS ref3,
						'' AS noIdentificado,
						'' AS adelantado,
						CASE WHEN apg.apgCuentaBancariaRecaudo ='5' THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(cb.NUMERO_CUENTA, '')),'-', ''))
							 WHEN apg.apgCodEntidadFinanciera IN('51','') THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''))
							 WHEN apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '' THEN REPLACE(cb.NUMERO_CUENTA,'-', '')
							 WHEN (apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo IN ('1','2','3','4') THEN REPLACE(REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', ''), '000000', '')
							 WHEN ( apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo = ('5') THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', ''))
							 WHEN apg.apgCodEntidadFinanciera IN('7','')  THEN REPLACE(REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''), '000000', '')
							  ELSE apg.apgNumeroCuenta END AS identificadorDelBanco,
						CASE WHEN (apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') and apg.apgCuentaBancariaRecaudo IS NOT NULL AND apg.apgCuentaBancariaRecaudo = 5 THEN ce.codigoEntidad --agregado con tabla de homologacion core.sap.MaestracodigoEntidad 31/10/2022
							 WHEN (apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo IS NOT NULL  THEN CONCAT ( '0', CAST(ce.codigoEntidad AS VARCHAR(2)))
							 WHEN apg.apgCuentaBancariaRecaudo IN ('1','2','3','4') AND apg.apgNumeroCuenta IS NULL THEN '7' -- agregado posterior a gap vuenta bancaria 18/10/2022
							 WHEN apg.apgCuentaBancariaRecaudo = ('5') AND apg.apgNumeroCuenta IS NULL then '51' -- agregado posterior a gap vuenta bancaria 18/10/2022
							 WHEN LEN(apg.apgCodEntidadFinanciera) = 1 THEN CONCAT('0', CAST(apg.apgCodEntidadFinanciera AS VARCHAR(2))) ELSE CAST(apg.apgCodEntidadFinanciera AS VARCHAR(2)) END AS codigoBanco,
						CASE WHEN apg.apgModalidadRecaudoAporte IN('MANUAL', 'PILA', 'PILA_MANUAL') THEN '3' ELSE '9' END AS transitoria,
						'' AS claseDeAporte,
						'' AS claseDeAportePrescripcion,
						0 AS tieneIntereses,
						'' AS tipoInteres,
						0 AS correccion,			-- Se define con los registros en la tabla Movimientoaporte en moaTipoAjuste = ''CORRECCION_A_LA_BAJA' va a ser anulacion.
						'' AS tipoMora,				-- Solo aplica para la INTEGRACIoN DE CARTERA
						0 AS id,
						NULL AS codigoGenesys,
						'' AS tipo,
						'S' AS claseCuenta,
						'soportetecnico@asopagos.com' AS usuario,
						reg.regid as regId,
						apg.apgFechaRecaudo AS fechaRecaudo, 
						CASE WHEN [SAP].[calcularPrimerDiaHabilMes]() = [SAP].[calcularSiguienteDiaHabil]() AND DAY(DATEADD(HOUR,-5,GETDATE())) >= [SAP].[calcularPrimerDiaHabilMes]() AND DATEDIFF(DAY, apg.apgFechaRecaudo, DATEADD(HOUR,-5,GETDATE())) > 0
							THEN EOMONTH(DATEADD(HOUR,-5,GETDATE()),-1)
							ELSE DATEADD(HOUR,-5,GETDATE()) END AS fechaProcesamiento,
						apg.apgEstadoRegistroAporteAportante AS EstadoRegistroAporteAportante,
						apg.apgTipoSolicitante AS TipoSolicitante,
						diferenciaMora AS interes,
						apdn.apdTarifa AS Tarifa,
						reg.regOperadorInformacion as [operadorinformacion],
						apg.apgId AS apgid
					FROM #registrogeneralA05 reg
					INNER JOIN core.sap.MaestraTiposIdentificacion mai ON reg.regTipoIdentificacionAportante = mai.TipoIdGenesys
					INNER JOIN core.dbo.aporteDetalladoRegistroControlN	apdn  WITH(NOLOCK) ON reg.regId = apdn.registroGeneralNuevo
					INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on apdn.apdAporteGeneral = apg.apgId
					INNER JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgid = apd.apdAporteGeneral	
					LEFT JOIN core.dbo.CUENTAS_BANCARIAS cb WITH(NOLOCK) on apg.apgCuentaBancariaRecaudo = cb.ID 
					LEFT JOIN core.sap.MaestracodigoEntidad ce WITH(NOLOCK) on cb.ID = ce.idCuentasBancarias 
					LEFT JOIN persona p WITH(NOLOCK) on apg.apgPersona = p.perId
					LEFT JOIN afiliado af WITH(NOLOCK) on p.perid = af.afiPersona
					LEFT JOIN RolAfiliado ro WITH(NOLOCK) on  af.afiId = ro.roaAfiliado  AND apdTipoCotizante = roaTipoAfiliado 
					WHERE  apg.apgEstadoRegistroAporteAportante = 'RELACIONADO' 
					AND ((roaEstadoAfiliado IS NULL OR roaEstadoAfiliado  = 'INACTIVO') AND apgEstadoAportante <> 'ACTIVO')
					AND ((reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @operadorinformacion) OR (regId =  @regid))
					AND (round(reg.regValTotalApoObligatorio + (ISNULL(reg.regValorIntMora, 0)),0))<>0
					GROUP BY  reg.regId, reg.regValTotalApoObligatorio,reg.regnumplanilla,reg.regNumeroIdentificacionAportante,TipoIdHomologado,reg.regNombreAportante,apg.apgNumeroCuenta,
					cb.NUMERO_CUENTA,apg.apgCuentaBancariaRecaudo,apg.apgCodEntidadFinanciera,apg.apgCuentaBancariaRecaudo,apg.apgCodEntidadFinanciera,apg.apgModalidadRecaudoAporte,
					apg.apgFechaRecaudo,apg.apgEstadoRegistroAporteAportante,apg.apgTipoSolicitante,apd.apdTarifa,ce.codigoEntidad, reg.regOperadorInformacion, apgId,
					apd.apdAporteObligatorio, apdValorIntMora, diferenciaMora,apdn.apdTarifa 

			END
-------------------------- Actualizacion temporal codigo entidad financiera en planillas mientras solucionan GLPI 68257 29/06/2023 Camilo-----------------------------------------
/*		IF (select top 1 codigoBanco from #IC_Aportes_Det where claveCont = '40') IS NULL
			
			BEGIN 
				Update  iad set	
				iad.codigoBanco =
						 CASE WHEN apg.apgCodEntidadFinanciera IS NULL AND apg.apgnumerocuenta = '00000000292647573' THEN '07'
							 WHEN apg.apgCodEntidadFinanciera IS NULL AND apg.apgnumerocuenta = '265-99174-5' THEN '51'
							 ELSE CAST(apg.apgCodEntidadFinanciera AS VARCHAR(2))
							 END
							 FROM #IC_Aportes_Det iad 
							Inner Join core.dbo.AporteGeneral  apg WITH(NOLOCK) on iad.apgid = apg.apgid
							LEFT JOIN core.dbo.CUENTAS_BANCARIAS cb WITH(NOLOCK) ON apg.apgCuentaBancariaRecaudo = cb.ID
							LEFT JOIN core.sap.MaestracodigoEntidad ce WITH(NOLOCK) ON cb.ID = ce.idCuentasBancarias 
							WHere iad.clavecont = '40'

				Update iad set 

				iad.identificadorDelBanco =
							CASE WHEN iad.codigoBanco IN('51') THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''))
							 WHEN iad.codigoBanco IN('07')  THEN REPLACE(REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''), '000000', '')
							 ELSE apg.apgNumeroCuenta END 
							 FROM #IC_Aportes_Det iad 
							 Inner Join core.dbo.AporteGeneral  apg WITH(NOLOCK) on iad.apgid = apg.apgid
							WHere iad.clavecont = '40'
							
			END		
*/
-------------------- Actualizacion de asignacion ------------------------------------------------------ 
		UPDATE	#IC_Aportes_Det SET asignacion = CONCAT('M', @apgid) WHERE asignacion = ''  ------ SE COLOCA EN ESTA POSICION PARA RECAUDO MANUAL 25/09/2023  Camilo Gomez 


	--Select * from #IC_Aportes_Det
-------------------------------------------------------------------------------------------------------------------------------------------------------

DELETE #IC_Aportes_Det WHERE importeDocumento = 0 AND interes = 0


			--RECAUDO MANUAL
			IF (SELECT top 1 Planilla FROM #APLICA ) IS NULL 
			BEGIN 
				DELETE #IC_Aportes_Det WHERE apgid <> @apgid
			END
	

			 -- PLANILLA
			 --Detectar apgid a integrar

			SELECT apgid, asignacion 
			INTO #apgid
			FROM  #IC_Aportes_Det WHERE importeDocumento > 0 

			IF (SELECT TOP 1 Planilla FROM #APLICA) IS NOT NULL 
			BEGIN			 


				UPDATE #IC_Aportes_Det  SET  consecutivo = 3 	

					
				INSERT INTO #IC_Aportes_Det
				SELECT ROW_NUMBER() OVER(ORDER BY regId ASC) AS Consecutivo, [codigoSap], SUM([ImporteDocumento]), [operador], '40' [claveCont], [asignacion],[textoposicion], [ref1], [ref2], [tipoDocumento], [ref3], [noIdentificado],
				[adelantado], [identificadorDelBanco], [codigoBanco], [transitoria], [claseDeAporte], [claseDeAportePrescripcion], [tieneIntereses], [tipoInteres], [correccion],
				[tipoMora], [id], [codigoGenesys], [tipo], [claseCuenta], [usuario], [regId],[fechaRecaudo],  [fechaProcesamiento], [EstadoRegistroAporteAportante], [TipoSolicitante], SUM([interes]), [Tarifa],[operadorinformacion],''
				FROM #IC_Aportes_Det  det	
				GROUP BY [codigoSap], [operador], [asignacion],[textoposicion], [ref1], [ref2], [tipoDocumento], [ref3], [noIdentificado],
				[adelantado], [identificadorDelBanco], [codigoBanco], [transitoria],[claseDeAporte], [claseDeAportePrescripcion], [tieneIntereses], [tipoInteres], [correccion],
				[tipoMora], [id], [codigoGenesys], [tipo], [claseCuenta], [usuario], [regId],[fechaRecaudo],  [fechaProcesamiento], [EstadoRegistroAporteAportante], [TipoSolicitante], [Tarifa],[operadorinformacion]
	

				DELETE FROM #IC_Aportes_Det  WHERE consecutivo > 2 
		
			END
		
	

			IF EXISTS (SELECT * FROM #IC_Aportes_Det) 
			BEGIN
			
				INSERT INTO #IC_Aportes_Det 
				SELECT [consecutivo], [codigoSap], [ImporteDocumento], [operador], '50' [claveCont], [asignacion],[textoposicion], [ref1], [ref2], [tipoDocumento], [ref3], [noIdentificado],
				[adelantado], '', '', '', [claseDeAporte], [claseDeAportePrescripcion], [tieneIntereses], [tipoInteres], [correccion],
				[tipoMora], [id], [codigoGenesys], [tipo], [claseCuenta], [usuario], [regId],[fechaRecaudo],  [fechaProcesamiento], [EstadoRegistroAporteAportante], [TipoSolicitante], [interes], [Tarifa],[operadorinformacion],''
				FROM #IC_Aportes_Det  det											 
		
			if (select regTipoPlanilla from pila.RegistroGeneral where regNumPlanilla = @planilla ) <> 'N'
			BEGIN
				--Actualizacion de campos clave contable 50 -------------- PLANILLAS 
				UPDATE #IC_Aportes_Det   SET   noIdentificado = CASE WHEN EstadoRegistroAporteAportante IN ('RELACIONADO') AND TipoSolicitante = 'EMPLEADOR' AND (Tarifa = 0.00 OR Tarifa = 0.04 OR Tarifa = 0.02 OR Tarifa = 0.01 or Tarifa = 0.03) THEN 'N1'
																	 WHEN EstadoRegistroAporteAportante IN ('RELACIONADO') AND TipoSolicitante = 'EMPLEADOR' AND (apd.apdId IS NULL) THEN 'N1'   ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022   
																	 WHEN EstadoRegistroAporteAportante IN ('RELACIONADO') AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.02 OR Tarifa = 0.04) THEN 'N2'
																	 WHEN EstadoRegistroAporteAportante IN ('RELACIONADO') AND TipoSolicitante = 'PENSIONADO' AND Tarifa <> 0.006 THEN 'N3'
																	 WHEN EstadoRegistroAporteAportante IN ('RELACIONADO') AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.006 OR Tarifa = 0.01) THEN 'N4'
																	 WHEN EstadoRegistroAporteAportante IN ('RELACIONADO') AND TipoSolicitante = 'PENSIONADO' AND Tarifa = 0.006 THEN 'N5'
																	 ELSE '**' END,
											fechaRecaudo = apg.apgFechaRecaudo, 
											fechaProcesamiento   = CASE WHEN [SAP].[calcularPrimerDiaHabilMes]() = [SAP].[calcularSiguienteDiaHabil]() AND DAY(DATEADD(HOUR,-5,GETDATE())) >= [SAP].[calcularPrimerDiaHabilMes]() AND DATEDIFF(DAY, apg.apgFechaRecaudo, DATEADD(HOUR,-5,GETDATE())) > 0
													THEN EOMONTH(DATEADD(HOUR,-5,GETDATE()),-1)
													ELSE DATEADD(HOUR,-5,GETDATE()) END, 
											EstadoRegistroAporteAportante = apg.apgEstadoRegistroAporteAportante,
											TipoSolicitante = apgTipoSolicitante
					FROM #registrogeneralA05 reg
					INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) ON regId = apgRegistroGeneral
					LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgid = apd.apdAporteGeneral
					LEFT JOIN #APLICA WITH (NOLOCK) ON reg.regnumplanilla = Planilla 
					WHERE claveCont = '50' AND apg.apgEstadoRegistroAporteAportante = 'RELACIONADO' AND ((reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @operadorinformacion) OR (reg.regid = @regid AND apg.apgId = @apgid)) 
			END			
			ELSE
			BEGIN
				UPDATE #IC_Aportes_Det   SET   noIdentificado = CASE WHEN EstadoRegistroAporteAportante IN ('RELACIONADO') AND TipoSolicitante = 'EMPLEADOR' AND (Tarifa = 0.00 OR Tarifa = 0.04 OR Tarifa = 0.02 OR Tarifa = 0.01 or Tarifa = 0.03) THEN 'N1'
																		 WHEN EstadoRegistroAporteAportante IN ('RELACIONADO') AND TipoSolicitante = 'EMPLEADOR' AND (apd.apdId IS NULL) THEN 'N1'   ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022   
																		 WHEN EstadoRegistroAporteAportante IN ('RELACIONADO') AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.02 OR Tarifa = 0.04) THEN 'N2'
																		 WHEN EstadoRegistroAporteAportante IN ('RELACIONADO') AND TipoSolicitante = 'PENSIONADO' AND Tarifa <> 0.006 THEN 'N3'
																		 WHEN EstadoRegistroAporteAportante IN ('RELACIONADO') AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.006 OR Tarifa = 0.01) THEN 'N4'
																		 WHEN EstadoRegistroAporteAportante IN ('RELACIONADO') AND TipoSolicitante = 'PENSIONADO' AND Tarifa = 0.006 THEN 'N5'
																		 ELSE '**' END,
												fechaRecaudo = apg.apgFechaRecaudo, 
												fechaProcesamiento   = CASE WHEN [SAP].[calcularPrimerDiaHabilMes]() = [SAP].[calcularSiguienteDiaHabil]() AND DAY(DATEADD(HOUR,-5,GETDATE())) >= [SAP].[calcularPrimerDiaHabilMes]() AND DATEDIFF(DAY, apg.apgFechaRecaudo, DATEADD(HOUR,-5,GETDATE())) > 0
														THEN EOMONTH(DATEADD(HOUR,-5,GETDATE()),-1)
														ELSE DATEADD(HOUR,-5,GETDATE()) END, 
												EstadoRegistroAporteAportante = apg.apgEstadoRegistroAporteAportante,
												TipoSolicitante = apgTipoSolicitante
						FROM #registrogeneralA05 reg
						INNER JOIN core.dbo.aporteDetalladoRegistroControlN	apdn  WITH(NOLOCK) ON reg.regId = apdn.registroGeneralNuevo
						INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on apdn.apdAporteGeneral = apg.apgId
						LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgid = apd.apdAporteGeneral
						LEFT JOIN #APLICA WITH (NOLOCK) ON reg.regnumplanilla = Planilla 
						WHERE  claveCont = '50' AND apg.apgEstadoRegistroAporteAportante = 'RELACIONADO' AND ((reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @operadorinformacion) OR (reg.regid = @regid AND apg.apgId = @apgid)) 
	

			END
			
	----------------------------------------------------------------------------------------------------------------------------------------
			UPDATE #IC_Aportes_Det SET	codigoGenesys = per.perId,	tipo = CASE WHEN tipoDocumento = 'NIT' THEN 'E'	ELSE 'P' END								
			FROM #registrogeneralA05 reg
				INNER JOIN core.dbo.Persona per WITH(NOLOCK) ON reg.regNumeroIdentificacionAportante = per.perNumeroIdentificacion
					WHERE (reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @OperadorInformacion) OR(reg.regId = @regId)

		IF (SELECT top 1 Planilla FROM #APLICA) IS NULL
		BEGIN
			UPDATE #IC_Aportes_Det SET	codigoGenesys = apg.apgPersona,	tipo = CASE WHEN tipoDocumento = 'NIT' THEN 'E'	ELSE 'P' END								
				FROM dbo.AporteGeneral  apg
				WHERE apg.apgId = @apgid
		END
	

				-- Validacion para crear otra detalle con clave contable si es tipo movimiento A06

					IF (SELECT TOP 1 regValorIntMora FROM  core.pila.RegistroGeneral WHERE (regNumPlanilla = @planilla) OR (regId = @regid AND regNumPlanilla IS NULL)) > 0
					BEGIN
						INSERT INTO #IC_Aportes_Det 
						SELECT [consecutivo], [codigoSap], interes AS [ImporteDocumento], [operador], '50' [claveCont], [asignacion],[textoposicion], [ref1], [ref2], [tipoDocumento], [ref3], '',
						[adelantado], [identificadorDelBanco], [codigoBanco], [transitoria], '', '', 1 AS [tieneIntereses], 
			 			CASE
							WHEN EstadoRegistroAporteAportante = 'RELACIONADO'  AND TipoSolicitante = 'EMPLEADOR' AND (Tarifa = 0.00 OR Tarifa = 0.04 OR Tarifa = 0.03 OR Tarifa = 0.02) THEN 'IN1'
							WHEN EstadoRegistroAporteAportante = 'RELACIONADO'  AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.02 OR Tarifa = 0.04) THEN 'IN2'
							WHEN EstadoRegistroAporteAportante = 'RELACIONADO'  AND TipoSolicitante = 'PENSIONADO' AND Tarifa <> 0.006 THEN 'IN3'
							WHEN EstadoRegistroAporteAportante = 'RELACIONADO'  AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.006 OR Tarifa = 0.01) THEN 'IN4'
							WHEN EstadoRegistroAporteAportante = 'RELACIONADO'  AND TipoSolicitante = 'PENSIONADO' AND Tarifa = 0.006 THEN 'IN5'
							ELSE '*'	END AS [tipoInteres], 
							[correccion],	[tipoMora], [id], [codigoGenesys], [tipo], [claseCuenta], [usuario], [regId],[fechaRecaudo],  [fechaProcesamiento], [EstadoRegistroAporteAportante], [TipoSolicitante], [interes], [tarifa], '',''
						FROM #IC_Aportes_Det  det	WHERE claveCont = '50'
					END
	

			INSERT INTO #IC_Aportes_Enc(
						[fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[consecutivo],[observacion],
						[moneda],[documentoContable],[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario],[estadoaporteaportantetercero]
					)
					SELECT TOP 1
						CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
						CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
						ISNULL(fechaRecaudo, '') AS fechaDocumento,
						ISNULL(fechaProcesamiento, '') AS fechaContabilizacion,
						MONTH(fechaProcesamiento) AS periodo, 
						'' AS referencia,
						CASE det.interes WHEN 0 THEN 'A05' ELSE 'A06' END  AS tipoMovimiento,
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
						 usuario AS usuario,
						 det.EstadoRegistroAporteAportante as estadoaporteaportantetercero
					FROM #IC_Aportes_Det det WITH(NOLOCK)
					--GROUP BY fechaRecaudo,fechaProcesamiento, usuario,det.EstadoRegistroAporteAportante,det.interes,det.tieneIntereses, regId,det.consecutivo

		


		--------------------------------------------------------------------------------------------------------------------------------------------
		
		IF (SELECT COUNT(claveCont) FROM #IC_Aportes_Det WHERE claveCont = '40') > 1
		BEGIN
			DELETE #IC_Aportes_Det WHERE  claveCont = '40'  AND consecutivo = (SELECT  MAX(consecutivo) FROM  #IC_Aportes_Det) 
			UPDATE #IC_Aportes_Det SET  consecutivo = (SELECT  MIN(consecutivo) FROM  #IC_Aportes_Det)
			UPDATE #IC_Aportes_Det SET  importeDocumento = (SELECT  SUM(D.importeDocumento)	FROM #IC_Aportes_Det D WHERE claveCont = '50' 
															) 
																WHERE claveCont = '40' 
		END
		ELSE
			BEGIN
			UPDATE #IC_Aportes_Det SET  importeDocumento = (SELECT  SUM(D.importeDocumento)  FROM #IC_Aportes_Det D WHERE D.claveCont = '50'
															) 												
															WHERE claveCont = '40' 
		END
		

		------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------Actualizacion campo texto posicion ----------------------------------------------------------------------------------------------------------------------------------
		
		--Select Distinct planillaAntes from aporteDetalladoRegistroControlN where planillaN = '65670464' --65470009
		Update #IC_Aportes_Det set textoPosicion = planillaAntes from aporteDetalladoRegistroControlN where planillaN = @planilla

		IF (SELECT DISTINCT reg.regtipoplanilla From pila.RegistroGeneral reg inner join #IC_Aportes_Det det on reg.regnumplanilla = det.textoPosicion ) = 'N'
			BEGIN

				--select Distinct reg.regNumPlanilla,apdn.planillaN,apdn.planillaAntes  from #IC_Aportes_Det d
				--				inner join aporteDetalladoRegistroControlN apdn on d.asignacion = apdn.planillaN 
				--				inner join AporteGeneral apg on apdn.apdAporteGeneral = apgId
				--				inner join pila.RegistroGeneral reg on reg.regId = apg.apgRegistroGeneral

				UPDATE #IC_Aportes_Det set textoPosicion = CONCAT(reg.regNumPlanilla,'\',apdn.planillaAntes)
								 from #IC_Aportes_Det d
								inner join aporteDetalladoRegistroControlN apdn on d.asignacion = apdn.planillaN 
								inner join AporteGeneral apg on apdn.apdAporteGeneral = apg.apgId
								inner join pila.RegistroGeneral reg on reg.regId = apg.apgRegistroGeneral
			END

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		--UPDATE  #IC_Aportes_Det SET importeDocumento = sum(importeDocumento) + interes WHERE claveCont = '40'  
		--UPDATE	#IC_Aportes_Det SET asignacion = CONCAT('M', @apgid) WHERE asignacion = ''    comentaerado 25/09/2023 Camilo Gomez se envia mas arriba por Recaudo Manual



			SELECT @referenciaNum = valorActual 
				FROM sap.ic_referencia
				WHERE comentario ='A'
				AND estado = 'A';



				UPDATE #IC_Aportes_Enc
					SET referencia =  @referenciaNum
    
--Comentarear
		
		if (Select count(*) from #IC_Aportes_Det)>0
					BEGIN 
						UPDATE sap.ic_referencia
							SET valorActual = @referenciaNum + 1
							WHERE comentario ='A'
							AND estado = 'A';	
					END
	
-- fin comentario		 
		  
		
			END
		END
	END

		--comentarear	
	
	
		IF ((SELECT referencia FROM #IC_Aportes_Enc ) IS NOT NULL )
		BEGIN

		
			IF EXISTS (SELECT enca.consecutivo FROM #IC_Aportes_Enc enca INNER JOIN #IC_Aportes_Det deta on enca.consecutivo = deta.consecutivo where deta.importeDocumento <> 0)
			BEGIN
	     
		 
				INSERT INTO IC_Aportes_Enc(
					[fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[observacion],
					[moneda],[documentoContable],[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario])
				SELECT distinct [fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[observacion],
					[moneda],[documentoContable],[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario]
				FROM #IC_Aportes_Enc WITH(NOLOCK) ; 
 
			
					-- Actualizar el consecutivo definitivo en la tabla de integracion de encabezado con el detalle
         
					SELECT @consecutivo=MAX(consecutivo) FROM IC_Aportes_Enc

					UPDATE #IC_Aportes_Det
					SET consecutivo = @consecutivo
		
			
			
				INSERT INTO sap.IC_Aportes_Det([consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
						[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
						[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
						[claseCuenta],[usuario],fechaEjecucion)
				SELECT [consecutivo],[codigoSap],CAST(CAST([importeDocumento] AS BIGINT) AS VARCHAR(18)),[operador],[claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
						[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
						[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
						[claseCuenta],[usuario], GETDATE()-'05:00:00'
				FROM #IC_Aportes_Det  WITH(NOLOCK)
				WHERE importeDocumento <> 0  ---  para cuando el registro viene sin detalle y que no traiga lineas en valores 0 08/10/2022
		 
			
				-- INSERCIoN EN LA TABLA DE CONTROL DE PLANILLAS
					UPDATE  #IC_Aportes_Det
					SET consecutivo =(SELECT consecutivo FROM #IC_Aportes_Enc)
			

				INSERT INTO sap.PlanillasCtrl
         		SELECT DISTINCT LEFT(det.asignacion, 12), 1, det.ref1, det.tipoDocumento, SAP.GetLocalDate(), 'APORTES', det.ref3 + enc.observacion, enc.tipoMovimiento,estadoaporteaportantetercero,operadorinformacion, case when @apgid is null or @apgid = '' then ap.apgid else @apgid end
				FROM #IC_Aportes_Det det WITH(NOLOCK)
				INNER JOIN #IC_Aportes_Enc enc WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
				INNER JOIN #apgid ap  WITH(NOLOCK) ON  det.asignacion = ap.asignacion
				WHERE det.EstadoRegistroAporteAportante IS NOT NULL --AND operadorinformacion IS NOT NULL

	

	

			END
		END	
	
	
	-- Fin Comentario



				--Detalle
			
				SELECT DISTINCT * FROM #IC_Aportes_Enc
				ORDER BY tipoMovimiento, consecutivo

				SELECT * FROM #IC_Aportes_Det WITH(NOLOCK)
				WHERE importeDocumento <> 0  ---  para cuando el registro viene sin detalle y que no traiga lineas en valores 0 08/10/2022
				ORDER BY consecutivo
		
				DROP TABLE IF EXISTS #IC_Aportes_Det;	
				DROP TABLE IF EXISTS #IC_Aportes_Enc;					
				DROP TABLE IF EXISTS #APLICA 
				--DROP TABLE IF EXISTS #registrogeneral
	
	
			COMMIT TRANSACTION
		END TRY

		BEGIN CATCH

			ROLLBACK TRANSACTION

			SELECT  ERROR_NUMBER() AS ErrorNumber  ,ERROR_SEVERITY() AS ErrorSeverity  
			,ERROR_STATE() AS ErrorState,ERROR_PROCEDURE() AS ErrorProcedure  
			,ERROR_LINE() AS ErrorLine,ERROR_MESSAGE() AS ErrorMessage
			,GETDATE() -'05:00:00' as Fechaing;  
 
			INSERT INTO core.sap.LogErrorAportes 
			SELECT @planilla, @planillaN,  @regId,@solNumeroRadicacion,@solTipoTransaccion,@operadorinformacion, ERROR_NUMBER(),ERROR_SEVERITY(),ERROR_STATE(),ERROR_PROCEDURE(),ERROR_LINE(),ERROR_MESSAGE(),GETDATE() -'05:00:00'
 

		END CATCH
	SELECT 'ok ejec A05 A06 TP',GETDATE() 

END