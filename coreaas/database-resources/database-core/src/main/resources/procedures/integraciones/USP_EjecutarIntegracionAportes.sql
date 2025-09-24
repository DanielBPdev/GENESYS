CREATE OR ALTER   PROCEDURE   [sap].[USP_EjecutarIntegracionAportes] AS

BEGIN

SET NOCOUNT ON;
BEGIN TRANSACTION
	

	----- insercion en tabla de log para ejecucion del procedimiento. 
	Declare @idlog int
	Insert Into sap.IC_LogEjecucion ([Integracion],[fechahorainicio],[fechahorafinal],[RegistrosEnviados]) VALUES ('APORTES',GETDATE() -'05:00:00' ,GETDATE() -'05:00:00', '')
	set  @idlog = SCOPE_IDENTITY()

	print'INICIO'

-- Valida que no se este en una ejecucion anterior, ya que este porcedimiento es ejecutado cada n minutos y se pueden abrir varias transacciones.
	IF (SELECT COUNT(*) FROM sap.ejecucion_int_aportes  WITH (NOLOCK)) <= 0 BEGIN
		
		  
			INSERT sap.ejecucion_int_aportes
			SELECT 1
		
	
		DECLARE	@planilla VARCHAR(20),@solNumeroRadicacion VARCHAR (MAX),@solTipoTransaccion VARCHAR (30), @planillaN VARCHAR(20), @planillaT VARCHAR(20)
		DECLARE	@solNumeroRadicacionC VARCHAR (MAX),@solTipoTransaccionC VARCHAR (30), @apgid BIGINT, @estadoAporteTM VARCHAR(20)
		DECLARE @regId BIGINT, @operadorinformacion INT, @operadorinformacionN INT,@EstadoAporte VARCHAR(20), @operadorinformacionT INT, @regIdT BIGINT
		DECLARE @fechahorainicio DATETIME, @fechahorafinal DATETIME, @estadoAportePT VARCHAR(20), @apgidPT BIGINT, @planillaPT VARCHAR(20)
		DECLARE @operadorinformacionPT INT, @regidPT BIGINT,@operadorinformacionNTP INT;
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			-- Busca las planillas en construccion de los flujos
			-- Crear las conslutas para:
			-- 1. Flujo de novedades que son Devoluciones
			--		exec sap.USP_Interfazcontableaportes '', 0, '<solNumeroRadicacion>', 'DEVOLUCION_APORTES'
			-- lA TABLA DE CONTROL EN ESTE ASO ES LA DE LAS SOLICITUDES
			-- 2. Flujo de novedades que son Correcciones
			--		exec sap.USP_Interfazcontableaportes '', 0, '<solNumeroRadicacion>', 'CORRECCION_APORTES'
			-- lA TABLA DE CONTROL EN ESTE ASO ES LA DE LAS SOLICITUDES
			-- 3. Flujo de planillas (fue lo que ejecutaron esta semana)
			--		exec sap.USP_Interfazcontableaportes '<NUMERO DE LA PLANILLA>', 0, '', ''
			-- LA TABLA DE CONTROL ES LA DE LAS PLANILLAS Y MOVIMIENTO, PERO SE DEBE VERIFICAR LOS CAMBIOS DE ESTADOS QUE OCURREN EN GENESYS
			-- 4. Flujo de aportes manuales.
			--		exec sap.USP_Interfazcontableaportes '', <REGID DE REGISTROGENERAL>, '', ''
			-- VERIFICAR QUE TABLA DE CONTROL SE REALIZA PARA QUE LOS APORTES MNANUALES NO SE PROCESEN DOS VECES.
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	CREATE TABLE #registros (Nregistros int)

	SELECT @fechahorainicio = GETDATE()-'05:00:00'
	DROP TABLE IF EXISTS #registrogeneral 
	SELECT regid, regNumPlanilla, regOperadorInformacion,regValTotalApoObligatorio, regValorIntMora,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
	regTipoPlanilla,regDateTimeInsert,regOUTEstadoArchivo,regNumPlanillaAsociada
	INTO #registrogeneral  
	FROM core.pila.RegistroGeneral	
	Where regDateTimeInsert IS NULL OR regDateTimeInsert >= dateadd(year,-5,getdate())


-------------------------------------------- Validacion y Actualizacion para planillas N sin Original --------------------------------------------------

DROP TABLE IF EXISTS #Nsinorigen
Create TABLE #Nsinorigen(
regId bigint,
regNumPlanilla	varchar(12),
regTipoPlanilla	varchar(2),
redRegistroDetalladoAnterior bigint,
ruta varchar (100)
)



 DECLARE @SQL NVARCHAR(MAX)
 	SELECT @SQL  = N'SELECT regid,regnumplanilla,regtipoplanilla,redRegistroDetalladoAnterior FROM staging.RegistroDetalladoPlanillaN
					INNER JOIN staging.registrogeneral on regid = redregistrogeneral
					WHERE redregistrodetalladoanterior = -2'
	INSERT INTO #Nsinorigen
	EXEC sp_execute_remote N'PilaReferenceData',
	@SQL


update #registrogeneral set regTipoPlanilla = Case When (select Distinct apgTipoSolicitante from #registrogeneral reg
															inner join #Nsinorigen nso on reg.regNumPlanilla = nso.regNumPlanilla
															inner join AporteGeneral apg on reg.regId = apg.apgRegistroGeneral
															Where apgEmpresaTramitadoraAporte is null 
															) = 'EMPLEADOR' Then 'E' 
												   When (select Distinct apgTipoSolicitante from #registrogeneral reg
															inner join #Nsinorigen nso on reg.regNumPlanilla = nso.regNumPlanilla
															inner join AporteGeneral apg on reg.regId = apg.apgRegistroGeneral
															Where apgEmpresaTramitadoraAporte is null 
															) = 'INDEPENDIENTE'	Then 'I' 	
													ELSE 'Y' END
		from #registrogeneral reg  inner join #Nsinorigen nso on reg.regNumPlanilla = nso.regNumPlanilla
		Where nso.redRegistroDetalladoAnterior = '-2' and reg.regTipoPlanilla = 'N'


------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------Validacion para envio de A34 RECAUDO MANUAL TERCERO PAGADOR -- AGREGADO POR YESIKA ---------------------------------


IF (SELECT COUNT(*) FROM sap.ejecucionInternAaportes WITH (NOLOCK)) <= 0 BEGIN
		

	INSERT sap.ejecucionInternAaportes
	SELECT 1

	SELECT DISTINCT TOP 10 ap.apgEstadoRegistroAporteAportante, ap.apgid, ap.apgRegistroGeneral
	INTO #TerceroPTM
	FROM Solicitud S
			INNER JOIN SolicitudAfiliacionPersona SP ON S.solId = SP.sapSolicitudGlobal
			INNER JOIN RolAfiliado R ON SP.sapRolAfiliado = roaId
			INNER JOIN Afiliado A ON R.roaAfiliado = A.afiId
			INNER JOIN Persona P ON A.afiPersona = P.perId
			INNER JOIN AporteGeneral ap ON p.perId = ap.apgPersona
			INNER JOIN #registrogeneral reg ON ap.apgRegistroGeneral = reg.regId
			INNER JOIN sap.IC_Aportes_Det det  WITH(NOLOCK) ON  CONCAT('M', ap.apgId) = det.asignacion
			INNER JOIN core.sap.IC_Aportes_Enc enc WITH(NOLOCK) ON  det.consecutivo = enc.consecutivo
			INNER JOIN core.sap.MaestraTiposIdentificacion mi WITH (NOLOCK) ON reg.regTipoIdentificacionAportante = mi.tipoIdGenesys
			LEFT JOIN core.sap.planillasctrl ctr with(nolock) on CONCAT('M', ISNULL(ap.apgId, '')) = ctr.regNumPlanilla AND reg.regnumeroidentificacionaportante = ctr.regNumeroIdentificacionAportante and mi.TipoIdHomologado = ctr.regTipoIdentificacionAportante and ap.apgEstadoRegistroAporteAportante = ctr.apgEstadoRegistroAporteAportante
			LEFT JOIN core.sap.LogErrorAportes err with(nolock) on reg.regId = err.regId
		WHERE  s.solTipoTransaccion like '%AFILIACION%'   and solFechaRadicacion >= '2024-03-20' 
			AND apgMarcaPeriodo ='PERIODO_FUTURO' AND enc.tipoMovimiento = 'A05'  AND enc.estadoReg = 'S' AND  NOT EXISTS(SELECT Tipomovimiento FROM core.sap.planillasctrl C INNER JOIN #registrogeneral R ON C.regNumPlanilla = CONCAT('M', ISNULL(ap.apgId, '')) WHERE Tipomovimiento = 'A34')
			AND	ctr.regNumPlanilla IS NULL AND err.planilla IS NULL 
	UNION
		SELECT DISTINCT ap.apgEstadoRegistroAporteAportante, ap.apgid, ap.apgRegistroGeneral
		FROM solicitud s 
			INNER JOIN SolicitudAfiliaciEmpleador se on solId = saeSolicitudGlobal
			INNER JOIN Empleador E ON se.saeEmpleador = e.empId
			INNER JOIN empresa em ON e.empEmpresa= em.empId	
			INNER JOIN persona p	ON em.empPersona = perId
			INNER JOIN AporteGeneral  ap ON em.empId = apgEmpresa
			INNER JOIN #registrogeneral reg ON ap.apgRegistroGeneral = reg.regId
			INNER JOIN sap.IC_Aportes_Det det  WITH(NOLOCK) ON  CONCAT('M', ap.apgId) = det.asignacion
			INNER JOIN core.sap.IC_Aportes_Enc enc WITH(NOLOCK) ON  det.consecutivo = enc.consecutivo
			INNER JOIN core.sap.MaestraTiposIdentificacion mi WITH (NOLOCK) ON reg.regTipoIdentificacionAportante = mi.tipoIdGenesys
			LEFT JOIN core.sap.planillasctrl ctr with(nolock) on CONCAT('M', ISNULL(ap.apgId, '')) = ctr.regNumPlanilla AND reg.regnumeroidentificacionaportante = ctr.regNumeroIdentificacionAportante and mi.TipoIdHomologado = ctr.regTipoIdentificacionAportante and ap.apgEstadoRegistroAporteAportante = ctr.apgEstadoRegistroAporteAportante
			AND ctr.tipomovimiento = 'A34'
			LEFT JOIN core.sap.LogErrorAportes err with(nolock) on reg.regId = err.regId 
			where  s.solTipoTransaccion like '%AFILIACION%'   and solFechaRadicacion >= '2024-03-20'
						AND apgMarcaPeriodo ='PERIODO_FUTURO' 
						AND enc.tipoMovimiento = 'A05'  AND enc.estadoReg = 'S' AND  NOT EXISTS(SELECT Tipomovimiento FROM core.sap.planillasctrl C INNER JOIN #registrogeneral R ON C.regNumPlanilla = CONCAT('M', ISNULL(ap.apgId, '')) WHERE Tipomovimiento = 'A34')
						AND	ctr.regNumPlanilla IS NULL AND err.planilla IS NULL 

		
		DECLARE cursor_terceroPTM CURSOR FOR 
			SELECT apgEstadoRegistroAporteAportante,apgid,apgRegistroGeneral
			FROM #TerceroPTM
			GROUP BY apgEstadoRegistroAporteAportante, apgid, apgRegistroGeneral
	
		OPEN cursor_terceroPTM
		FETCH NEXT FROM cursor_terceroPTM  INTO @estadoAportePT,@apgidPT, @RegidPT
	
		WHILE @@FETCH_STATUS = 0 
		BEGIN
					
			 EXEC [sap].[USP_GetICAPORTES_Insert_A34] '','',@estadoAportePT,@apgidPT,'','','', @regidPT;
			
	DELETE FROM #TerceroPTM
		
			FETCH NEXT FROM cursor_terceroPTM INTO @estadoAportePT,@apgidPT, @RegidPT
		END 

		CLOSE cursor_terceroPTM 
		DEALLOCATE cursor_terceroPTM

	print 'ok A34  Manual'
	SELECT 'ok ejec',GETDATE() 
	DELETE FROM	sap.ejecucionInternAaportes
	END
	ELSE BEGIN

		SELECT 'El proceso ya se encuentra en ejecucion'
	END

	
--------------------------------------Validacion para envio de A34 PLANILLA  TERCERO PAGADOR -- AGREGADO POR YESIKA ---------------------------------

IF (SELECT COUNT(*) FROM sap.ejecucionInternAaportes WITH (NOLOCK)) <= 0 BEGIN
		
		  
	INSERT sap.ejecucionInternAaportes
	SELECT 1


			SELECT DISTINCT ap.apgEstadoRegistroAporteAportante, ap.apgid, ap.apgRegistroGeneral, reg.regNumPlanilla,regOperadorInformacion
			INTO #RegistroPT 
			FROM  #registrogeneral reg
			INNER JOIN AporteGeneral ap  WITH(NOLOCK) ON reg.regId = ap.apgRegistroGeneral
			INNER JOIN Aportedetallado apd  WITH(NOLOCK) ON ap.apgid = apd.apdaportegeneral 
			INNER JOIN Persona P  WITH(NOLOCK) ON ap.apgPersona = p.perId
			INNER JOIN Afiliado A  WITH(NOLOCK) ON P.perId = A.afiPersona
			INNER JOIN RolAfiliado R  WITH(NOLOCK) ON A.afiId=R.roaAfiliado AND apdTipoCotizante = R.roaTipoAfiliado 
			INNER JOIN SolicitudAfiliacionPersona SP  WITH(NOLOCK) ON R.roaId = SP.sapRolAfiliado 
			INNER JOIN Solicitud S  WITH(NOLOCK) ON  SP.sapSolicitudGlobal= S.solId 
			INNER JOIN sap.IC_Aportes_Det det  WITH(NOLOCK) ON reg.regNumPlanilla = det.asignacion
			INNER JOIN core.sap.IC_Aportes_Enc enc WITH(NOLOCK) ON  det.consecutivo = enc.consecutivo
			INNER JOIN core.sap.MaestraTiposIdentificacion mi WITH (NOLOCK) ON reg.regTipoIdentificacionAportante = mi.tipoIdGenesys
			LEFT JOIN core.sap.planillasctrl ctr WITH(NOLOCK) ON reg.regNumPlanilla = ctr.regNumPlanilla AND reg.regnumeroidentificacionaportante = ctr.regNumeroIdentificacionAportante and mi.TipoIdHomologado = ctr.regTipoIdentificacionAportante and ap.apgEstadoRegistroAporteAportante = ctr.apgEstadoRegistroAporteAportante
			AND ap.apgId=ctr.apgid  AND ctr.Tipomovimiento = 'A34'
			LEFT JOIN core.sap.LogErrorAportes err WITH(NOLOCK) ON reg.regId = err.regId
			WHERE  s.solTipoTransaccion like '%AFILIACION%' and solFechaRadicacion >= reg.regDateTimeInsert
			AND apgMarcaPeriodo ='PERIODO_FUTURO' AND enc.tipoMovimiento ='A05'  AND enc.estadoReg = 'S' --AND ctr.regNumPlanilla IS NULL AND err.planilla IS NULL
			AND ap.apgEstadoRegistroAporteAportante = 'RELACIONADO' -- AND CONCAT (apgperiodoaporte,'-01') > ap.apgfechaprocesamiento 'Preguntar si va esta validación'
		UNION
		
			select Distinct ap.apgEstadoRegistroAporteAportante, ap.apgid, ap.apgRegistroGeneral, reg.regNumPlanilla,regOperadorInformacion From Solicitud S
			inner join SolicitudAfiliaciEmpleador on s.solid = saeSolicitudGlobal
			inner join Empleador emd on saeempleador =emd.empid
			inner join empresa  emp on emd.empempresa = emp.empid
			inner join persona per on emp.empPersona = perId
			inner join AporteGeneral ap on emp.empid = ap.apgempresa
			inner join aportedetallado apd on ap.apgId = apd.apdAporteGeneral
			inner join #registrogeneral reg on ap.apgRegistroGeneral = reg.regId
			INNER JOIN sap.IC_Aportes_Det det  WITH(NOLOCK) ON reg.regNumPlanilla = det.asignacion
			INNER JOIN core.sap.IC_Aportes_Enc enc WITH(NOLOCK) ON  det.consecutivo = enc.consecutivo
			INNER JOIN core.sap.MaestraTiposIdentificacion mi WITH (NOLOCK) ON reg.regTipoIdentificacionAportante = mi.tipoIdGenesys
			LEFT JOIN core.sap.planillasctrl ctr WITH(NOLOCK) ON reg.regNumPlanilla = ctr.regNumPlanilla AND reg.regnumeroidentificacionaportante = ctr.regNumeroIdentificacionAportante and mi.TipoIdHomologado = ctr.regTipoIdentificacionAportante and ap.apgEstadoRegistroAporteAportante = ctr.apgEstadoRegistroAporteAportante
			AND ap.apgId=ctr.apgid  AND ctr.Tipomovimiento = 'A34'
			LEFT JOIN core.sap.LogErrorAportes err WITH(NOLOCK) ON reg.regId = err.regId
			WHERE s.solTipoTransaccion like '%AFILIACION%' and solFechaRadicacion >= reg.regDateTimeInsert
			AND apgMarcaPeriodo ='PERIODO_FUTURO' AND enc.tipoMovimiento ='A05'  AND enc.estadoReg = 'S' --AND ctr.regNumPlanilla IS NULL AND err.planilla IS NULL
			AND ap.apgEstadoRegistroAporteAportante = 'RELACIONADO'
			
			
			

		SELECT apgid,regNumPlanilla 
		INTO #TerceroPT
		FROM #RegistroPT 
		EXCEPT 
		SELECT apgId,regNumPlanilla FROM SAP.PlanillasCtrl WHERE tipoMovimiento = 'A34'

		Select t.apgId,t.regNumPlanilla,r.apgRegistroGeneral 
		into #TerceroPT34
		from #TerceroPT t
		Inner join #RegistroPT r on t.apgId = r.apgId


						
	DECLARE cursor_terceroPT CURSOR FOR 
			SELECT apgid,apgRegistroGeneral
			FROM #TerceroPT34
			GROUP BY apgid, apgRegistroGeneral
	
		OPEN cursor_terceroPT
		FETCH NEXT FROM cursor_terceroPT  INTO @apgidPT, @RegidPT
	
		WHILE @@FETCH_STATUS = 0 
		BEGIN
					
			 EXEC [sap].[USP_GetICAPORTES_Insert_A34] '','','',@apgidPT,'','','', @regidPT;
			
	DELETE FROM #TerceroPT34
		
			FETCH NEXT FROM cursor_terceroPT INTO @apgidPT, @RegidPT
		END 

		CLOSE cursor_terceroPT 
		DEALLOCATE cursor_terceroPT

	print 'ok A34 Planilla'

	SELECT 'ok ejec',GETDATE() 
	DELETE FROM	sap.ejecucionInternAaportes
	END
	ELSE BEGIN

		SELECT 'El proceso ya se encuentra en ejecucion'
	END


	--7882085092	7	RELACIONADO
-----------------Planillas  Tercero Pagador (Agregado Yesika Bernal) ---------------------------------------------------------------------------------------------------------
	
	IF (SELECT COUNT(*) FROM sap.ejecucionInternAaportes WITH (NOLOCK)) <= 0 BEGIN
	
		  
		INSERT sap.ejecucionInternAaportes
		SELECT 1
		
	
		SELECT pipIdPlanilla, pipTipoArchivo
		INTO #registrogP
		FROM pila.PilaIndicePlanilla
		WHERE pipTipoArchivo = 'ARCHIVO_OI_IP'
	
	
	
	SELECT  DISTINCT
	reg.regNumPlanilla, reg.regOperadorInformacion, apg.apgEstadoRegistroAporteAportante,reg.regDateTimeInsert,
	CASE		
			WHEN d.asignacion = reg.regNumPlanilla AND e.tipoMovimiento in ('A07', 'A34') AND e.estadoReg = 'S' AND (CONCAT (apgperiodoaporte,'-01')) < apgfechaprocesamiento  --AND roaEstadoAfiliado = 'ACTIVO'
			AND apdValorIntMora = 0 AND apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' THEN 'A08'
			WHEN roa.roaEstadoAfiliado = 'ACTIVO' AND apg.apgEstadoRegistroAporteAportante = 'RELACIONADO' AND   apdValorIntMora = 0  THEN 'A07' 
			WHEN d.asignacion <> reg.regNumPlanilla and CONCAT (apgperiodoaporte,'-01') < apgfechaprocesamiento AND apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND  apdValorIntMora = 0 THEN 'A01' 
			WHEN CONCAT (apgperiodoaporte,'-01') < apgFechaRecaudo AND apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND  apdValorIntMora > 0 THEN 'A02' 
			WHEN (roa.roaEstadoAfiliado <> 'ACTIVO' OR roa.roaEstadoAfiliado IS NULL) AND apg.apgEstadoRegistroAporteAportante = 'RELACIONADO' AND  apdValorIntMora = 0 THEN 'A05' 
			WHEN (roa.roaEstadoAfiliado <> 'ACTIVO' OR roa.roaEstadoAfiliado IS NULL) AND apg.apgEstadoRegistroAporteAportante = 'RELACIONADO' AND  apdValorIntMora > 0 THEN 'A06'			
			WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND apdValorIntMora = 0  THEN 'A09'
			WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND apdValorIntMora <> 0  THEN 'A10'

	END AS TipoMovimiento, apg.apgid
	INTO #TerceroP
	FROM  #registrogeneral reg 	 
	INNER JOIN core.dbo.AporteGeneral apg WITH (NOLOCK) ON reg.regId = apg.apgRegistroGeneral 
	LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgid = apd.apdAporteGeneral and (round(apd.apdValorIntMora + (ISNULL(apd.apdAporteObligatorio, 0)),0))>0
	INNER JOIN core.dbo.Persona per WITH(NOLOCK) ON reg.regTipoIdentificacionAportante = per.perTipoIdentificacion and reg.regNumeroIdentificacionAportante =  per.perNumeroIdentificacion
	LEFT JOIN dbo.Afiliado afi ON apgPersona = afi.afiPersona
	LEFT JOIN dbo.RolAfiliado roa ON  afi.afiId = roa.roaAfiliado  AND apdTipoCotizante = roa.roaTipoAfiliado 					
	LEFT JOIN core.dbo.Empresa emp WITH(NOLOCK) ON per.perId = emp.empPersona
	LEFT JOIN core.dbo.EntidadPagadora epa WITH(NOLOCK) ON emp.empId = epa.epaEmpresa
	LEFT JOIN  #registrogP	rei ON reg.regNumPlanilla =  pipIdPlanilla 
	INNER JOIN core.sap.MaestraTiposIdentificacion mi WITH (NOLOCK) ON reg.regTipoIdentificacionAportante = mi.tipoIdGenesys		
	LEFT JOIN  core.sap.LogErrorAportes err WITH (NOLOCK) ON reg.regNumPlanilla = err.planilla
	LEFT JOIN  core.sap.planillasctrl ctr WITH (NOLOCK) ON reg.regNumPlanilla = ctr.regNumPlanilla AND reg.regnumeroidentificacionaportante = ctr.regNumeroIdentificacionAportante 
	AND mi.TipoIdHomologado = ctr.regTipoIdentificacionAportante AND apg.apgEstadoRegistroAporteAportante = ctr.apgEstadoRegistroAporteAportante AND reg.regOperadorInformacion = ctr.operadorinformacion
	AND apg.apgId  = ctr.apgid
	LEFT JOIN sap.IC_Aportes_Det d ON reg.regNumPlanilla = d.asignacion 
	LEFT JOIN sap.IC_Aportes_Enc e ON d.consecutivo = e.consecutivo
	WHERE  --reg.regNumPlanilla = '70207998'AND
	((reg.regTipoPlanilla ='Y'and regDateTimeInsert >='2024-03-20') OR (pipTipoArchivo = 'ARCHIVO_OI_IP' AND reg.regOUTEstadoArchivo ='RECAUDO_NOTIFICADO' AND regDateTimeInsert >='2024-03-20')
	OR ((reg.regTipoPlanilla ='Y' AND (reg.regDateTimeInsert IS NULL OR reg.regDateTimeInsert >= dateadd(year,-5,getdate())) AND apg.apgFechaReconocimiento >= '2024-03-20' AND apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS') OR (pipTipoArchivo = 'ARCHIVO_OI_IP' AND (reg.regDateTimeInsert IS NULL OR reg.regDateTimeInsert >= dateadd(year,-5,getdate())) AND apg.apgFechaReconocimiento >= '2024-03-20' AND apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS'))
	AND ctr.regNumPlanilla IS NULL
	AND (err.planilla is null AND (round(apd.apdValorIntMora + (ISNULL(apd.apdAporteObligatorio, 0)),0))>0 AND (regDateTimeInsert <> '')))
	 

	 -- Toma las planillas de APORTES y ejecuta los movimientos contables de APORTES.
		SELECT tipomovimiento,regNumPlanilla, regOperadorInformacion,apgEstadoRegistroAporteAportante,apgId 
		INTO  #TerceroPAI
		FROM  #TerceroP  WHERE tipomovimiento IS NOT NULL 
		EXCEPT SELECT tipomovimiento,regNumPlanilla,operadorinformacion,apgEstadoRegistroAporteAportante, apgid FROM SAP.PlanillasCtrl
		WHERE tipomovimiento <> 'A15'
		
		SELECT DISTINCT C.regNumPlanilla,C.operadorinformacion,C.apgEstadoRegistroAporteAportante,C.apgid,C.Tipomovimiento
		INTO #A15
		FROM SAP.PlanillasCtrl C
		INNER JOIN  #TerceroPAI T ON C.regNumPlanilla = T.regNumPlanilla --AND c.operadorinformacion = t.regOperadorInformacion
		WHERE C.tipomovimiento = 'A15'

		If (Select count(*) from #A15)>0
			BEGIN

					Update #TerceroPAI set TipoMovimiento = 'A0'
					FROM 
				(SELECT Distinct  P.regNumPlanilla, P.OperadorInformacion,P.apgEstadoRegistroAporteAportante,P.apgId,P.TipoMovimiento FROM #A15 A
				INNER JOIN sap.PlanillasCtrl P ON  CONCAT (A.regNumPlanilla, A.OperadorInformacion,A.apgEstadoRegistroAporteAportante,A.apgId,A.Tipomovimiento) =  CONCAT (P.regNumPlanilla, P.OperadorInformacion,P.apgEstadoRegistroAporteAportante,P.apgId,P.TipoMovimiento)) as fin
				inner join #TerceroPAI t on fin.regNumPlanilla = t.regNumPlanilla 

				Delete from #TerceroPAI where TipoMovimiento = 'A0'
			
			END
   
			
		SELECT top 10 t.tipomovimiento,t.regNumPlanilla, t.regOperadorInformacion,t.apgEstadoRegistroAporteAportante,t.apgId
		INTO  #TerceroPA
		FROM  #TerceroPAI t
		--where regNumPlanilla = '9465701395' --not in ( '7779000249','7779000246','7779000248')
		EXCEPT SELECT tipomovimiento,regNumPlanilla,operadorinformacion,apgEstadoRegistroAporteAportante,apgid FROM SAP.PlanillasCtrl 
		order by regNumPlanilla

	--- Actualizacion y comparación si existe un mov A34 en planillas
		update #TerceroPA  set apgEstadoRegistroAporteAportante = '0' from #TerceroPT34 a
		inner join  #TerceroPA  b on a.regNumPlanilla = b.regNumPlanilla and a.apgId = b.apgId 
		where b.TipoMovimiento = 'A07'

	--- Actualizacion y comparación si existe un mov A34 en Manuales
		update #TerceroPA  set apgEstadoRegistroAporteAportante = '0'  from #TerceroPTM a
		inner join #registrogeneral r on a.apgRegistroGeneral = r.regId
		inner join  #TerceroPA  b on r.regNumPlanilla = b.regNumPlanilla and 
		a.apgId = b.apgId and a.apgEstadoRegistroAporteAportante = b.apgEstadoRegistroAporteAportante 
		where b.TipoMovimiento = 'A07'

	-- Actualización A01, A02 cuando existe un Mov A05 y A06		
		update #TerceroPA  set apgEstadoRegistroAporteAportante = '0' from #TerceroPA tpa
		inner join sap.PlanillasCtrl ct on tpa.regNumPlanilla = ct.regNumPlanilla and tpa.apgId = ct.apgid
		where (tpa.TipoMovimiento in ('A01','A02') AND ct.Tipomovimiento in ('A05','A06'))

		
	-- Actualizacion A08 cuando exista un mov A05, DEBE ELIMINARLO
		update #TerceroPA  set apgEstadoRegistroAporteAportante = '0' from #TerceroPA b
		inner join sap.PlanillasCtrl ct on b.Tipomovimiento = 'A08' AND ct.Tipomovimiento ='A05' and b.apgId = ct.apgid

	-- Si Existe lo debe eliminar, para que no integre el A07 ----------------
		delete from #TerceroPA where apgEstadoRegistroAporteAportante = '0'

		--select * from #TerceroPA
	------------------------------------------------------------------------	

		print 'pasa'

		DECLARE @count BIGINT = 0
		DECLARE @count2 BIGINT = (SELECT COUNT(*) FROM #TerceroPA)

		WHILE (@count < @count2)
			BEGIN
			
			print 'ingresa'
			
					BEGIN TRANSACTION
					-- Obtener el primer registro de la tabla temporal
					SELECT TOP 1  @planillaT=regNumPlanilla, @operadorinformacionT=regOperadorInformacion,@estadoAporte= apgEstadoRegistroAporteAportante
					FROM #TerceroPA tm 
					print @planillaT
										
			IF (SELECT  TOP 1 TipoMovimiento  FROM #TerceroPA  WHERE regNumPlanilla = @planillaT) IN('A01', 'A02')
				BEGIN
					print 'entra A01'		 			
					EXEC [sap].[USP_GetICAPORTES_Insert_A01_A02] @planillaT,'',@estadoAporte,'','','',@operadorinformacionT;
					print 'Sale A01'
				END

			IF (SELECT  TOP 1 TipoMovimiento  FROM #TerceroPA  WHERE regNumPlanilla = @planillaT) IN('A05', 'A06')
				BEGIN
					print 'entra A05'
					EXEC [sap].[USP_GetICAPORTES_Insert_A05_A06] @planillaT,'',@estadoAporte,'','','',@operadorinformacionT; 
					print 'Sale A05'
				END

			IF (SELECT  TOP 1 TipoMovimiento  FROM #TerceroPA  WHERE regNumPlanilla = @planillaT) IN('A07', 'A08')
				BEGIN

					print 'entra A07'
					EXEC [sap].[USP_GetICAPORTES_Insert_A07_A08] @planillaT,'',@estadoAporte,'','','',@operadorinformacionT;
					print 'Sale A07'
				END

			IF (SELECT  TOP 1 TipoMovimiento  FROM #TerceroPA  WHERE regNumPlanilla = @planillaT) IN('A09', 'A10')
				BEGIN

					print 'entra A09'
					EXEC [sap].[USP_GetICAPORTES_Insert_A09_A10] @planillaT,'',@estadoAporte,'','','',@operadorinformacionT;
					print 'Sale A09'
				END

				print 'entra borrar tercero PA'
				DELETE FROM #TerceroPA
					WHERE regNumPlanilla = @planillaT
						AND apgEstadoRegistroAporteAportante = @estadoAporte
						AND regOperadorInformacion = @operadorinformacionT; 
						print 'sale borrar tercero PA'

					SET @count = @count+1
					PRINT (@count)
		
			
			
			COMMIT TRANSACTION
			
		END

					   
		Print CONCAT ('ok ejec Planillas  Tercero Pagador ', GETDATE()) 

			
		UPDATE sap.ejecucionInternAaportes SET valoractual = 0 WHERE valoractual = 1
		DELETE FROM	sap.ejecucionInternAaportes 
		END
		ELSE BEGIN

			SELECT 'El proceso ya se encuentra en ejecucion'
		END
			   		

select valorActual from sap.IC_Referencia where clase = 'TE' and comentario = 'A' and estado = 'A'
Select 'planillas Tercero'


--------------------------------------Validacion para envio de A03_a A04 TERCERO PAGADOR AGREGADO POR YESIKA ---------------------------------


IF (SELECT COUNT(*) FROM sap.ejecucionInternAaportes WITH (NOLOCK)) <= 0 BEGIN
	
	print 'Ingresa Tercero A03_A04'	
		  
	INSERT sap.ejecucionInternAaportes
	SELECT 1



		SELECT DISTINCT  reg.regnumplanilla, apg.apgId, reg.regOperadorInformacion
		INTO #TerceroTpId
		FROM AporteGeneral apg
				INNER JOIN #registrogeneral reg  on apg.apgRegistroGeneral = regId
				INNER JOIN core.sap.MaestraTiposIdentificacion mi WITH (NOLOCK) ON reg.regTipoIdentificacionAportante = mi.tipoIdGenesys
				LEFT JOIN  core.sap.planillasctrl ctr with(nolock) on reg.regNumPlanilla = ctr.regNumPlanilla AND reg.regnumeroidentificacionaportante = ctr.regNumeroIdentificacionAportante 
				AND mi.TipoIdHomologado = ctr.regTipoIdentificacionAportante and apg.apgEstadoRegistroAporteAportante = ctr.apgEstadoRegistroAporteAportante AND ctr.apgid = apg.apgid
				LEFT JOIN core.sap.LogErrorAportes err with(nolock) on reg.regId = err.regId 
				INNER JOIN Persona P ON apg.apgPersona = P.perId
				WHERE regDateTimeInsert >= '2024-03-20' AND apg.apgEstadoRegistroAporteAportante = 'REGISTRADO'
				  AND ctr.regNumPlanilla IS NULL 	AND err.planilla IS NULL  AND (reg.regTipoPlanilla = 'Y' OR reg.regTipoPlanilla IS NULL)
				  AND concat (reg.regNumPlanilla, apg.apgid) IN (SELECT CONCAT(regNumPlanilla, apgid) FROM #registrogeneral inner join  AporteGeneral WITH (NOLOCK) ON regId = apgRegistroGeneral EXCEPT SELECT CONCAT (regNumPlanilla, apgid) FROM SAP.PlanillasCtrl WHERE tipoMovimiento IN ('A03', 'A04'))
				  AND (round(apg.apgValTotalApoObligatorio + (ISNULL(apg.apgValorIntMora, 0)),0))<>0
	UNION	

		SELECT DISTINCT  TOP 5 reg.regNumPlanilla, ap.apgId, reg.regOperadorInformacion	
		FROM Solicitud S
				INNER JOIN SolicitudAfiliacionPersona SP ON S.solId = SP.sapSolicitudGlobal --AND sp.sapEstadoSolicitud ='CERRADA'
				INNER JOIN RolAfiliado R ON SP.sapRolAfiliado = roaId
				INNER JOIN Afiliado A ON R.roaAfiliado = A.afiId
				INNER JOIN Persona P ON A.afiPersona = P.perId
				INNER JOIN AporteGeneral ap ON p.perId = ap.apgPersona
				INNER JOIN #registrogeneral reg ON ap.apgRegistroGeneral = reg.regId
				INNER JOIN sap.IC_Aportes_Det det  WITH(NOLOCK) ON reg.regNumPlanilla = det.asignacion
				INNER JOIN core.sap.IC_Aportes_Enc enc WITH(NOLOCK) ON  det.consecutivo = enc.consecutivo 	AND enc.tipoMovimiento IN('A05', 'A06')  AND enc.estadoReg = 'S' 
				INNER JOIN core.sap.MaestraTiposIdentificacion mi WITH (NOLOCK) ON reg.regTipoIdentificacionAportante = mi.tipoIdGenesys
				LEFT JOIN core.sap.planillasctrl ctr with(nolock) on reg.regNumPlanilla = ctr.regNumPlanilla AND reg.regnumeroidentificacionaportante = ctr.regNumeroIdentificacionAportante 
				AND mi.TipoIdHomologado = ctr.regTipoIdentificacionAportante and ap.apgEstadoRegistroAporteAportante = ctr.apgEstadoRegistroAporteAportante AND ctr.apgid = ap.apgid --and ctr.Tipomovimiento IN('A05', 'A06')
				LEFT JOIN core.sap.LogErrorAportes err with(nolock) on reg.regId = err.regId 
				WHERE (s.solTipoTransaccion like '%AFILIACION%' OR s.solTipoTransaccion = 'NOVEDAD_REINTEGRO') --AND S.solResultadoProceso = 'APROBADA'  AND sapEstadoSolicitud ='CERRADA'
				 AND roaEstadoAfiliado = 'ACTIVO' --- agregado 12/07/2023 en sesion
				 AND (solFechaRadicacion >= regDateTimeInsert) AND regDateTimeInsert >= '2024-03-20'
				 AND ctr.regNumPlanilla IS NULL 	AND err.planilla IS NULL  
				 AND reg.regNumPlanilla IN (select regNumPlanilla from #registrogeneral EXCEPT SELECT regNumPlanilla FROM SAP.PlanillasCtrl WHERE tipoMovimiento IN ('A03', 'A04'))
				 and (round(reg.regValTotalApoObligatorio + (ISNULL(reg.regValorIntMora, 0)),0))<>0
			


	SELECT DISTINCT  temp.regNumPlanilla, temp.apgId, temp.regOperadorInformacion,apgEstadoRegistroAporteAportante 
	into #TerceroTpIdA03
	FROM  #TerceroTpId temp WITH(NOLOCK)  
	INNER JOIN core.sap.IC_Aportes_Det det WITH(NOLOCK) ON  regNumPlanilla = det.asignacion  OR CONCAT ('M',apgId) = det.asignacion 
	INNER JOIN core.sap.IC_Aportes_Enc enc WITH(NOLOCK) ON  det.consecutivo = enc.consecutivo AND enc.estadoReg = 'S' AND enc.Tipomovimiento in ('A05', 'A06') 
	LEFT JOIN core.sap.planillasctrl ctr with(nolock) on temp.regNumPlanilla = ctr.regNumPlanilla AND temp.apgid = ctr.apgid and ctr.Tipomovimiento in ('A05', 'A06')
	where ctr.apgid is not null --and temp.regNumPlanilla = '777000410'


-- Actualización A03 cuando existe un Mov A05 y un A34
	update #TerceroTpIdA03  set apgEstadoRegistroAporteAportante = '0' from #TerceroTpIdA03 temp
		inner join sap.PlanillasCtrl ct on temp.regNumPlanilla = ct.regNumPlanilla and temp.apgid = ct.apgid
		where ct.tipomovimiento = 'A34'

		delete from #TerceroTpIdA03 where apgEstadoRegistroAporteAportante = '0'

	   						
	DECLARE @countAT BIGINT = 0
	DECLARE @countAT2 BIGINT = (SELECT COUNT(*) FROM #TerceroTpIdA03)

		WHILE (@countAT < @countAT2)
			BEGIN
					SELECT TOP 1 @planillaPT = regnumplanilla, @apgidPT = apgid,  @operadorinformacionPT = regOperadorInformacion FROM #TerceroTpIdA03

					PRINT @planillaPT
					EXEC [sap].[USP_GetICAPORTES_Insert_A03_A04] @planillaPT,'','',@apgidPT,'','',@OperadorInformacionPT;

					DELETE FROM #TerceroTpIdA03 WHERE regnumplanilla = @planillaPT AND apgid=@apgidPT AND  regOperadorInformacion = @operadorinformacionPT
				
	
				SET @countAT = @countAT+1
				PRINT (concat(@countAT, 'CICLO A03_A04'))
				
			END 

	print 'ok ejec Tercero A03_A04'		
	SELECT 'ok ejec Tercero A03_A04',GETDATE() 
	UPDATE SAP.ejecucionInternAaportes SET valoractual = 0 WHERE valoractual = 1
	DELETE FROM	sap.ejecucionInternAaportes 
	END
	ELSE BEGIN

		SELECT 'El proceso ya se encuentra en ejecucion'
	END

	
--------------------------------------------------------------------Planillas-----------------------------------------------------------------

IF (SELECT COUNT(*) FROM sap.ejecucionInternAaportes WITH (NOLOCK)) <= 0 BEGIN
		
		print 'Planillas'	
		  
			INSERT sap.ejecucionInternAaportes
			SELECT 1

------------ nuevo ajuste para rendimiento, sin cursor ------------------------------

			SELECT DISTINCT top 30 reg.regnumplanilla,reg.regOperadorInformacion
			INTO #tmp_filaplanilla_pr
				FROM #registrogeneral reg 
				INNER JOIN core.dbo.AporteGeneral apg with(nolock) ON reg.regId = apg.apgRegistroGeneral
				INNER JOIN core.sap.MaestraTiposIdentificacion mi with(nolock) ON reg.regTipoIdentificacionAportante = mi.tipoIdGenesys
				LEFT JOIN core.sap.LogErrorAportes err with(nolock) on reg.regNumPlanilla = err.planilla
				LEFT JOIN core.sap.planillasctrl ctr with(nolock) on reg.regNumPlanilla = ctr.regNumPlanilla AND reg.regnumeroidentificacionaportante = ctr.regNumeroIdentificacionAportante and mi.TipoIdHomologado = ctr.regTipoIdentificacionAportante and apg.apgEstadoRegistroAporteAportante = ctr.apgEstadoRegistroAporteAportante and reg.regOperadorInformacion = ctr.operadorinformacion
				WHERE  reg.regnumplanilla IS NOT NULL
				AND  ctr.regNumPlanilla IS NULL AND err.planilla IS NULL
				and reg.regTipoPlanilla <> 'Y'
				AND reg.regDateTimeInsert >= '2024-03-20'
				and (round(reg.regValTotalApoObligatorio + (ISNULL(reg.regValorIntMora, 0)),0))<>0
				--and reg.regNumPlanilla in ('788000804')

		UNION

				SELECT DISTINCT top 10 reg.regnumplanilla,reg.regOperadorInformacion
				FROM #registrogeneral reg 
				INNER JOIN core.dbo.AporteGeneral apg with(nolock) ON reg.regId = apg.apgRegistroGeneral
				INNER JOIN core.sap.MaestraTiposIdentificacion mi with(nolock) ON reg.regTipoIdentificacionAportante = mi.tipoIdGenesys
				LEFT JOIN core.sap.LogErrorAportes err with(nolock) on reg.regNumPlanilla = err.planilla
				LEFT JOIN core.sap.planillasctrl ctr with(nolock) on reg.regNumPlanilla = ctr.regNumPlanilla AND reg.regnumeroidentificacionaportante = ctr.regNumeroIdentificacionAportante and mi.TipoIdHomologado = ctr.regTipoIdentificacionAportante and apg.apgEstadoRegistroAporteAportante = ctr.apgEstadoRegistroAporteAportante and reg.regOperadorInformacion = ctr.operadorinformacion
				WHERE  reg.regnumplanilla IS NOT NULL
				AND  ctr.regNumPlanilla IS NULL AND err.planilla IS NULL
				and reg.regTipoPlanilla <> 'Y'
				and (round(reg.regValTotalApoObligatorio + (ISNULL(reg.regValorIntMora, 0)),0))<>0
				AND (apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS'  AND (reg.regDateTimeInsert IS NULL OR reg.regDateTimeInsert >= dateadd(year,-5,getdate())and apg.apgFechaReconocimiento >= '2024-03-20'))
				--AND reg.regNumPlanilla in ('788000804')
		UNION 

				SELECT DISTINCT top 10 reg.regnumplanilla,reg.regOperadorInformacion
				FROM #registrogeneral reg 
				INNER JOIN core.dbo.AporteGeneral apg with(nolock) ON reg.regId = apg.apgRegistroGeneral
				INNER JOIN core.sap.MaestraTiposIdentificacion mi with(nolock) ON reg.regTipoIdentificacionAportante = mi.tipoIdGenesys
				LEFT JOIN core.sap.LogErrorAportes err with(nolock) on reg.regNumPlanilla = err.planilla
				LEFT JOIN core.sap.planillasctrl ctr with(nolock) on reg.regNumPlanilla = ctr.regNumPlanilla AND reg.regnumeroidentificacionaportante = ctr.regNumeroIdentificacionAportante and mi.TipoIdHomologado = ctr.regTipoIdentificacionAportante and apg.apgEstadoRegistroAporteAportante = ctr.apgEstadoRegistroAporteAportante and reg.regOperadorInformacion = ctr.operadorinformacion
				WHERE  reg.regnumplanilla IS NOT NULL
				AND  ctr.regNumPlanilla IS NULL AND err.planilla IS NULL
				and reg.regTipoPlanilla <> 'Y'
				and (round(reg.regValTotalApoObligatorio + (ISNULL(reg.regValorIntMora, 0)),0))<>0
				AND (CONVERT(DATE,reg.regDateTimeInsert) <= apg.apgFechaReconocimiento AND apg.apgFechaReconocimiento >= '2024-03-20')
				--AND reg.regNumPlanilla in ('788000804')
print(concat('select planillas normales ',@@ROWCOUNT))

		DECLARE @countP BIGINT = 0
		DECLARE @countP2 BIGINT = (SELECT COUNT(*) FROM #tmp_filaplanilla_pr)
		WHILE (@countP < @countP2)
			BEGIN
					SELECT TOP 1 @planilla = regnumplanilla, @operadorinformacion = regOperadorInformacion FROM #tmp_filaplanilla_pr
					EXEC [sap].[USP_Interfazcontableaportes_2] @planilla,'','','',@operadorinformacion

					DELETE FROM #tmp_filaplanilla_pr WHERE regnumplanilla = @planilla and regOperadorInformacion = @operadorinformacion
				
	
				SET @countP = @countP+1
				PRINT (concat(@countP, 'planilla normal'))
				
			END 


	print 'ok ejec planillas'			
	SELECT 'ok ejec',GETDATE() 
	DELETE FROM	sap.ejecucionInternAaportes
	END
	ELSE BEGIN

		SELECT 'El proceso ya se encuentra en ejecucion'
	END
	
	
select valorActual from sap.IC_Referencia where clase = 'TE' and comentario = 'A' and estado = 'A'  
Select 'Planillas Normales'
	

--------------------------Consulta para envio de planillas N integracion contable (creado desde 06/03/2023 Agregado Yesika Bernal)--------------------

IF (SELECT COUNT(*) FROM sap.ejecucionInternAaportes WITH (NOLOCK)) <= 0 BEGIN		
		  
			INSERT sap.ejecucionInternAaportes
			SELECT 1

SELECT  DISTINCT TOP 20   reg.regNumPlanilla, reg.regOperadorInformacion, apg.apgEstadoRegistroAporteAportante
		INTO #tmp_filaplanillas_N
		FROM #registrogeneral reg
		INNER JOIN  #registrogeneral reg1 ON reg.regNumPlanillaAsociada = reg1.regNumPlanilla
		INNER JOIN dbo.AporteGeneral apg WITH (NOLOCK) ON apg.apgRegistroGeneral = reg1.regId
		INNER JOIN sap.MaestraTiposIdentificacion mi WITH (NOLOCK) ON mi.tipoIdGenesys = reg.regTipoIdentificacionAportante		
		LEFT JOIN sap.LogErrorAportes err WITH (NOLOCK) ON reg.regNumPlanilla = err.planillaN
		LEFT JOIN sap.planillasctrl ctr WITH (NOLOCK) ON reg.regNumPlanilla = ctr.regNumPlanilla AND reg.regnumeroidentificacionaportante = ctr.regNumeroIdentificacionAportante 
		AND mi.TipoIdHomologado = ctr.regTipoIdentificacionAportante /*AND apg.apgEstadoRegistroAporteAportante = ctr.apgEstadoRegistroAporteAportante*/ AND reg.regOperadorInformacion = ctr.operadorinformacion
		WHERE 
		reg.regtipoplanilla = 'N' And reg1.regtipoplanilla <> 'Y' AND ctr.regNumPlanilla IS NULL AND err.planilla IS NULL AND apg.apgEstadoRegistroAporteAportante <> 'OTROS_INGRESOS'
		AND reg.regOUTEstadoArchivo ='RECAUDO_NOTIFICADO' AND (round(reg.regValTotalApoObligatorio + (ISNULL(reg.regValorIntMora, 0)),0))<>0
		AND (ctr.apgestadoregistroaporteaportante is null)
		--and reg.regnumplanilla = '7900000016'

		UNION 
----- para planilla N de la N
		SELECT  DISTINCT TOP 20   reg.regNumPlanilla, reg.regOperadorInformacion, apg.apgEstadoRegistroAporteAportante
		FROM #registrogeneral reg
		inner join aporteDetalladoRegistroControlN apdn on reg.regNumPlanilla = apdn.planillaN
		inner join aporteDetalladoRegistroControlN apdn2 on apdn.planillaAntes = apdn2.planillaN
		INNER JOIN  #registrogeneral reg1 ON  apdn2.planillaAntes = reg1.regNumPlanilla
		inner join AporteGeneral apg on  apdn.apdAporteGeneral = apgId
		INNER JOIN sap.MaestraTiposIdentificacion mi WITH (NOLOCK) ON mi.tipoIdGenesys = reg.regTipoIdentificacionAportante		
		LEFT JOIN sap.LogErrorAportes err WITH (NOLOCK) ON reg.regNumPlanilla = err.planillaN
		LEFT JOIN sap.planillasctrl ctr WITH (NOLOCK) ON reg.regNumPlanilla = ctr.regNumPlanilla AND reg.regnumeroidentificacionaportante = ctr.regNumeroIdentificacionAportante 
		AND mi.TipoIdHomologado = ctr.regTipoIdentificacionAportante /*AND apg.apgEstadoRegistroAporteAportante = ctr.apgEstadoRegistroAporteAportante*/ AND reg.regOperadorInformacion = ctr.operadorinformacion
		WHERE 
		reg.regtipoplanilla = 'N' And reg1.regtipoplanilla <> 'Y' AND ctr.regNumPlanilla IS NULL AND err.planilla IS NULL AND apg.apgEstadoRegistroAporteAportante <> 'OTROS_INGRESOS'
		AND reg.regOUTEstadoArchivo ='RECAUDO_NOTIFICADO' AND (round(reg.regValTotalApoObligatorio + (ISNULL(reg.regValorIntMora, 0)),0))<>0
		AND (ctr.apgestadoregistroaporteaportante is null)





		DECLARE cursor_planillasN CURSOR FOR 
			SELECT regnumplanilla,regOperadorInformacion, apgEstadoRegistroAporteAportante 
			FROM #tmp_filaplanillas_N
			GROUP BY regNumPlanilla,regOperadorInformacion,apgEstadoRegistroAporteAportante
				
	
		OPEN cursor_planillasN 
		FETCH NEXT FROM cursor_planillasN INTO @planillaN,@operadorinformacionN,@EstadoAporte
	
		WHILE @@FETCH_STATUS = 0 
		BEGIN
						
			
			EXEC [sap].[USP_Interfazcontableaportes_PlanillaN]  @planillaN,@EstadoAporte,@operadorinformacionN;
		
			FETCH NEXT FROM cursor_planillasN INTO @planillaN,@operadorinformacionN,@EstadoAporte
		END 
		CLOSE cursor_planillasN 
		DEALLOCATE cursor_planillasN

	
	print 'ok ejec planilla N'
	SELECT 'ok ejec',GETDATE() 
	DELETE FROM	sap.ejecucionInternAaportes
	END
	ELSE BEGIN

		SELECT 'El proceso ya se encuentra en ejecucion'
	END
	
		SELECT valorActual FROM sap.ic_referencia where comentario IN ('P','A') AND estado = 'A'

		
	
--------------------------Consulta para envio de planillas N tercero pagador integracion contable (creado desde 30/03/2023  Yesika Bernal)--------------------

	IF (SELECT COUNT(*) FROM sap.ejecucionInternAaportes WITH (NOLOCK)) <= 0 BEGIN
		
		  
			INSERT sap.ejecucionInternAaportes
			SELECT 1
		PRINT 'ENTRA PLANILLA N TP'
			--Separa movimientos A07, A05 mas los demas movimientos estos para esta consulta los tomaremos como A00 

		SELECT DISTINCT TOP 5 reg.regNumPlanilla, reg.regOperadorInformacion, apg.apgEstadoRegistroAporteAportante,apg.apgEmpresaTramitadoraAporte,
		CASE	WHEN roa.roaEstadoAfiliado = 'ACTIVO' AND apg.apgEstadoRegistroAporteAportante = 'RELACIONADO' AND   apdValorIntMora = 0  THEN 'A07'
				WHEN (roa.roaEstadoAfiliado <> 'ACTIVO' OR roa.roaEstadoAfiliado IS NULL) AND apg.apgEstadoRegistroAporteAportante = 'RELACIONADO' AND  apdValorIntMora = 0 THEN 'A05' 
				else 'A00' END AS TipoMovimiento
		INTO #tmp_filaplanillas_NP
			FROM #registrogeneral reg
				INNER JOIN aporteDetalladoRegistroControlN apdn ON reg.regId = apdn.registroGeneralNuevo
				INNER JOIN AporteGeneral apg  WITH(NOLOCK) ON  apdn.apdAporteGeneral = apg.apgId
				LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgid = apd.apdAporteGeneral and (round(apd.apdValorIntMora + (ISNULL(apd.apdAporteObligatorio, 0)),0))>0
				LEFT JOIN sap.LogErrorAportes err WITH (NOLOCK) ON reg.regNumPlanilla = err.planilla
				LEFT JOIN sap.planillasctrl ctr WITH (NOLOCK) ON reg.regNumPlanilla = ctr.regNumPlanilla AND reg.regnumeroidentificacionaportante = ctr.regNumeroIdentificacionAportante
				AND apg.apgId  = ctr.apgid AND apg.apgEstadoRegistroAporteAportante = ctr.apgEstadoRegistroAporteAportante
				LEFT JOIN dbo.Afiliado afi ON apgPersona = afi.afiPersona
				LEFT JOIN dbo.RolAfiliado roa ON  afi.afiId = roa.roaAfiliado  AND apdTipoCotizante = roa.roaTipoAfiliado 						
				WHERE  reg.regTipoPlanilla = 'N'  AND reg.regnumplanilla IS NOT NULL	AND reg.regOUTEstadoArchivo ='RECAUDO_NOTIFICADO'
					AND  ctr.regNumPlanilla IS NULL AND err.planilla IS NULL	  AND (reg.regDateTimeInsert >= '2024-03-20' 
					OR (CONVERT(DATE,reg.regDateTimeInsert) <= apg.apgFechaReconocimiento AND apg.apgFechaReconocimiento >= '2024-03-20'))
					AND (round(reg.regValTotalApoObligatorio + (ISNULL(reg.regValorIntMora, 0)),0))<>0 --and 
					AND apg.apgEmpresaTramitadoraAporte is not null
					AND apg.apgEstadoRegistroAporteAportante <> 'OTROS_INGRESOS'
				GROUP BY reg.regNumPlanilla, reg.regOperadorInformacion,  apg.apgEstadoRegistroAporteAportante, roa.roaEstadoAfiliado,apdValorIntMora,apg.apgEmpresaTramitadoraAporte

				
			
		DECLARE @countN BIGINT = 0
		DECLARE @countN2 BIGINT = (SELECT COUNT(*) FROM #tmp_filaplanillas_NP)

		WHILE (@countN < @countN2)
			BEGIN
			
			print 'ingresa' 

					BEGIN TRANSACTION
					-- Obtener el primer registro de la tabla temporal
					SELECT TOP 1  @planillaN=regNumPlanilla, @operadorinformacionNTP=regOperadorInformacion,@EstadoAporte= apgEstadoRegistroAporteAportante
					FROM #tmp_filaplanillas_NP tm 
					
		

			IF (SELECT  TOP 1 TipoMovimiento  FROM #tmp_filaplanillas_NP  WHERE regNumPlanilla = @planillaN) ='A05'
				BEGIN
					print 'entra A05'
					EXEC [sap].[USP_GetICAPORTES_Insert_A05_A06] @planillaN,'',@EstadoAporte,'','','',@operadorinformacionNTP; 
					print 'Sale A05'

				print 'entra borrar tercero PA N'
				DELETE FROM #tmp_filaplanillas_NP
					WHERE regNumPlanilla = @planillaN
						AND apgEstadoRegistroAporteAportante = @EstadoAporte
						AND regOperadorInformacion = @operadorinformacionNTP;
						print 'sale borrar tercero PA N'

					SET @countN = @countN+1
					PRINT (@countN)
		

				END

			IF (SELECT  TOP 1 TipoMovimiento  FROM #tmp_filaplanillas_NP  WHERE regNumPlanilla = @planillaN) = 'A07'
				BEGIN

					print 'entra A07'
					EXEC [sap].[USP_GetICAPORTES_Insert_A07_A08] @planillaN,'',@EstadoAporte,'','','',@operadorinformacionNTP;
					print 'Sale A07'

				print 'entra borrar tercero PA N'
				DELETE FROM #tmp_filaplanillas_NP
					WHERE regNumPlanilla = @planillaN
						AND apgEstadoRegistroAporteAportante = @EstadoAporte
						AND regOperadorInformacion = @operadorinformacionNTP; 
						print 'sale borrar tercero PA N'

					SET @countN = @countN+1
					PRINT (@countN)
		

				END

			IF (SELECT  TOP 1 TipoMovimiento  FROM #tmp_filaplanillas_NP  WHERE regNumPlanilla = @planillaN) = 'A00'
				BEGIN
					
					print 'entra A00 Demas planillas'
					EXEC [sap].[USP_Interfazcontableaportes_PlanillaN]  @planillaN,@EstadoAporte,@operadorinformacionNTP;
					print 'Sale A00 Demas planillas'

				print 'entra borrar tercero PA N'
				DELETE FROM #tmp_filaplanillas_NP
					WHERE regNumPlanilla = @planillaN
						AND apgEstadoRegistroAporteAportante = @EstadoAporte
						AND regOperadorInformacion = @operadorinformacionNTP; 
						print 'sale borrar tercero PA N'

					SET @countN = @countN+1
					PRINT (@countN)
		
				END


		
			
			
			
			COMMIT TRANSACTION
			
		END


	print 'ok ejec planilla N Tercero P'
	SELECT 'ok ejec',GETDATE() 
	DELETE FROM	sap.ejecucionInternAaportes
	END
	ELSE BEGIN

		SELECT 'El proceso ya se encuentra en ejecucion'
	END


	--SELECT valorActual FROM sap.ic_referencia where comentario IN ('P','A') AND estado = 'A'
	

------------------------- validacion para solicitud devoluciones ------------------------------------------------ 

	
---------------------------------Devoluciones -----------------------------------------------------------------------------

	
	IF (SELECT COUNT(*) FROM sap.ejecucionInternAaportes WITH (NOLOCK)) <= 0 BEGIN
		
		  
			INSERT sap.ejecucionInternAaportes
			SELECT 1

			PRINT 'INGRESO A DEVOLUCION'

		SELECT DISTINCT top 5 sol.solNumeroRadicacion,sol.solTipoTransaccion,apgId 
			INTO #tmp_solicitudes_Dtp
				FROM core.dbo.SolicitudDevolucionAporte sda WITH(NOLOCK) 
				INNER JOIN core.dbo.DevolucionAporte dap WITH(NOLOCK) ON sda.sdaDevolucionAporte = dap.dapid
				INNER JOIN core.dbo.DevolucionAporteDetalle dad WITH(NOLOCK) ON dap.dapid = dad.dadDevolucionAporte
				INNER JOIN core.dbo.MovimientoAporte moa WITH(NOLOCK) ON dad.dadMovimientoAporte = moa.moaid
				INNER JOIN core.dbo.AporteGeneral apg WITH(NOLOCK) ON moa.moaAporteGeneral = apg.apgid
				INNER JOIN core.dbo.Solicitud sol WITH(NOLOCK) ON sda.sdaSolicitudGlobal = sol.solId
				INNER JOIN #registrogeneral reg ON apg.apgRegistroGeneral = reg.regId
				LEFT JOIN sap.ContablesCtrl ctr ON sol.solNumeroRadicacion = ctr.solNumeroRadicacion
				LEFT JOIN sap.LogErrorAportes err ON sol.solNumeroRadicacion = err.solNumeroRadicacion
			--	LEFT JOIN  #registrogP	rei ON reg.regNumPlanilla =  pipIdPlanilla 
		WHERE sol.solFechaRadicacion >= '2024-03-20' AND ctr.solNumeroRadicacion IS NULL  AND err.solNumeroRadicacion IS NULL 
		--79161 
			--And sol.soltipotransaccion = 'DEVOLUCION_APORTES'
			--and sol.solNumeroRadicacion in ('0320241308787','0320241340412')
			--and sol.solnumeroradicacion in ('032024368526','032024371590','032024207623')



		-- Toma las solicitudes de Devoluvion de APORTES y ejecuta los movimientos contables de APORTES.
		DECLARE cursor_devoluciones_tp CURSOR FOR
			SELECT solNumeroRadicacion,solTipoTransaccion,apgid
				FROM #tmp_solicitudes_Dtp
				GROUP BY solNumeroRadicacion,solTipoTransaccion,apgid


				
			-- Listar los numeros de radicado asociados	 
			OPEN cursor_devoluciones_tp

			FETCH NEXT FROM cursor_devoluciones_tp INTO @solNumeroRadicacion,@solTipoTransaccion,@apgid
	
			WHILE @@FETCH_STATUS = 0 
			BEGIN
							
				-- Llamado de los movimientos contables
				exec [sap].[USP_GetICAPORTES_Insert_DEVOLUCION_TP]  '','',@apgid,@solNumeroRadicacion,@solTipoTransaccion,'';
			
				FETCH NEXT FROM cursor_devoluciones_tp INTO @solNumeroRadicacion,@solTipoTransaccion,@apgid
			END 
			CLOSE cursor_devoluciones_tp 
			DEALLOCATE cursor_devoluciones_tp

			delete #tmp_solicitudes_Dtp

		print 'ok Devolucion'
		SELECT 'ok ejec devolucion',GETDATE() 
		DELETE FROM	sap.ejecucionInternAaportes
		END
		ELSE BEGIN

			SELECT 'El proceso ya se encuentra en ejecucion'
	END
	
	select valorActual from sap.IC_Referencia where clase = 'TE' and comentario = 'A' and estado = 'A'
	Select 'devoluciones'
	
-------------------------------------------------------------------------------------------------------------------
	
-------------------------------Validacion Para Aporte Manual(REGID)---------------------------------------------------------


IF (SELECT COUNT(*) FROM sap.ejecucionInternAaportes WITH (NOLOCK)) <= 0 BEGIN
		
		  
			INSERT sap.ejecucionInternAaportes
			SELECT 1

	SELECT  DISTINCT TOP 10 reg.regId
	INTO #tmp_aportemanual_R
	FROM #registrogeneral reg
		INNER JOIN AporteGeneral apg with(nolock) ON reg.regId = apg.apgRegistroGeneral
		LEFT JOIN AporteDetallado apd with(nolock) ON apg.apgId = apd.apdAporteGeneral
		LEFT JOIN core.dbo.Persona per WITH(NOLOCK) ON reg.regTipoIdentificacionAportante = per.perTipoIdentificacion and reg.regNumeroIdentificacionAportante =  per.perNumeroIdentificacion
		LEFT JOIN core.dbo.Empresa emp WITH(NOLOCK) ON per.perId = emp.empPersona
		LEFT JOIN core.dbo.EntidadPagadora epa WITH(NOLOCK) ON emp.empId = epa.epaEmpresa	 
		INNER JOIN core.sap.MaestraTiposIdentificacion mi with(nolock) ON reg.regTipoIdentificacionAportante = mi.tipoIdGenesys
		LEFT JOIN sap.MaestraCondicionesCuentas cc with(nolock) ON apg.apgEstadoRegistroAporteAportante = cc.apgEstadoRegistroAporteAportante
					AND ISNULL(cc.apgFormaReconocimientoAporte, '') = ISNULL(apg.apgFormaReconocimientoAporte, '')
					AND cc.apgEstadoAportante = apg.apgEstadoAportante
					AND cc.apgmarcaperiodo = apg.apgmarcaperiodo
					AND CONVERT(VARCHAR, apd.apdTarifa) = CONVERT(VARCHAR, cc.apdTarifa)
					AND cc.apgTipoSolicitante = apg.apgtiposolicitante 
					AND CASE WHEN ISNULL(apd.apdValorIntMora,0) > 0 THEN '1' ELSE '0' END = cc.tieneinteres
		LEFT JOIN core.dbo.Correccion cor on apg.apgId = cor.corAporteGeneral
		LEFT JOIN core.sap.planillasctrl ctr with(nolock) on CONCAT('M', ISNULL(apg.apgId, '')) = ctr.regNumPlanilla AND reg.regnumeroidentificacionaportante = ctr.regNumeroIdentificacionAportante and mi.TipoIdHomologado = ctr.regTipoIdentificacionAportante and apg.apgEstadoRegistroAporteAportante = ctr.apgEstadoRegistroAporteAportante
		LEFT JOIN core.sap.LogErrorAportes err with(nolock) on reg.regId = err.regId
	   WHERE epa.epaEstadoEntidadPagadora IS NULL AND reg.regnumplanilla IS NULL  --(Agregado Yesika Bernal) validar si viene de un tercero pagador
		AND apgFechaProcesamiento >= '2024-03-20' 
		AND  ctr.regNumPlanilla IS NULL AND err.planilla IS NULL
		--AND concat(CONCAT('M', ISNULL(apg.apgId, '')),reg.regNumeroIdentificacionAportante,mi.tipoDocumentoCaja,apg.apgEstadoRegistroAporteAportante) NOT IN (select concat(regnumplanilla,regnumeroidentificacionAportante,regtipoidentificacionaportante,apgEstadoRegistroAporteAportante) from sap.planillasctrl)
		and cor.corId is null 

		--and NOT EXISTS (select err.regId from sap.LogErrorAportes err WITH (NOLOCK) where reg.regId = err.regId )
		-- Toma las planillas de APORTES y ejecuta los movimientos contables de APORTES.
		DECLARE cursor_aportemanual CURSOR FOR 
			SELECT regId
			FROM #tmp_aportemanual_R
			GROUP BY regId
				
		-- Listar los numeros de radicado asociados	 
		OPEN cursor_aportemanual 
		FETCH NEXT FROM cursor_aportemanual INTO @regId
	
		WHILE @@FETCH_STATUS = 0 
		BEGIN
						
			-- Llamado de los movimientos contables
			exec sap.USP_interfazcontableaportes_2  '',@regId,'','','';
		
			FETCH NEXT FROM cursor_aportemanual INTO @regId
		END 
		CLOSE cursor_aportemanual 
		DEALLOCATE cursor_aportemanual

		delete #tmp_aportemanual_R

	print 'ok Aporte Manual'
	SELECT 'ok ejec',GETDATE() 
	DELETE FROM	sap.ejecucionInternAaportes
	END
	ELSE BEGIN

		SELECT 'El proceso ya se encuentra en ejecucion'
	END
		
	SELECT valorActual FROM sap.ic_referencia where comentario IN ('P','A') AND estado = 'A'

	

	
-------------------------------Validacion  Aporte Manual Tercero pagador (Agregado Yesika Bernal)--------------------------------------------------------

		
IF (SELECT COUNT(*) FROM sap.ejecucionInternAaportes WITH (NOLOCK)) <= 0 BEGIN		
		  
			INSERT sap.ejecucionInternAaportes
			SELECT 1

	PRINT 'INGRESO A MANUAL TERCERO PAGADOR'
	SELECT DISTINCT top 10 apg.apgId, apg.apgEstadoRegistroAporteAportante, reg.regNumPlanilla
	INTO #TerceroPM
	FROM  #registrogeneral	 reg 	
	INNER JOIN core.dbo.AporteGeneral apg WITH (NOLOCK) ON reg.regId = apg.apgRegistroGeneral
	INNER JOIN core.dbo.AporteDetallado apd WITH (NOLOCK) ON apg.apgId = apd.apdAporteGeneral
	INNER JOIN core.dbo.Persona per WITH(NOLOCK) ON reg.regTipoIdentificacionAportante = per.perTipoIdentificacion and reg.regNumeroIdentificacionAportante =  per.perNumeroIdentificacion
	INNER JOIN core.dbo.Empresa emp WITH(NOLOCK) ON per.perId = emp.empPersona
	INNER JOIN core.dbo.EntidadPagadora epa WITH(NOLOCK) ON emp.empId = epa.epaEmpresa
	INNER JOIN core.sap.MaestraTiposIdentificacion mi WITH (NOLOCK) ON reg.regTipoIdentificacionAportante = mi.tipoIdGenesys		
	LEFT JOIN core.sap.planillasctrl ctr with(nolock) on CONCAT('M', ISNULL(apg.apgId, '')) = ctr.regNumPlanilla AND reg.regnumeroidentificacionaportante = ctr.regNumeroIdentificacionAportante and mi.TipoIdHomologado = ctr.regTipoIdentificacionAportante and apg.apgEstadoRegistroAporteAportante = ctr.apgEstadoRegistroAporteAportante
	LEFT JOIN core.sap.LogErrorAportes err with(nolock) on  CONCAT('M', ISNULL(apg.apgId, '')) = err.planilla 
	WHERE apd.apdAporteObligatorio > 0 AND (epa.epaEstadoEntidadPagadora  = 'HABILITADO' AND apgFechaProcesamiento >= '2024-03-20'  AND (reg.regId IS NOT NULL AND reg.regNumPlanilla IS NULL ) 
	AND ctr.regNumPlanilla IS NULL 	AND err.planilla IS NULL AND ( apg.apgOrigenAporte <> 'CORRECCION_APORTE' OR apg.apgOrigenAporte is null ))
	OR (apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND ctr.regNumPlanilla IS NULL 	AND err.planilla IS NULL AND reg.regNumPlanilla IS NULL  AND (reg.regNumPlanilla IS NULL  AND reg.regDateTimeInsert IS NULL OR reg.regDateTimeInsert >= DATEADD(YEAR,-9,GETDATE())AND apg.apgFechaReconocimiento >= '2024-03-20'))
	OR (reg.regNumPlanilla IS NULL AND CONVERT(DATE,reg.regDateTimeInsert) <= apg.apgFechaReconocimiento AND apg.apgFechaReconocimiento >= '2024-03-20')
	

		DECLARE @countPM BIGINT = 0
		DECLARE @countPM2 BIGINT = (SELECT COUNT(*) FROM #TerceroPM)

		WHILE (@countPM < @countPM2)
			BEGIN
			
			print 'ingresa'
			
					BEGIN TRANSACTION
				
					SELECT TOP 1 @apgid =apgid, @estadoAporteTM =apgEstadoRegistroAporteAportante FROM #TerceroPM	GROUP BY apgid, apgEstadoRegistroAporteAportante

						EXEC [sap].[USP_GetICAPORTES_Insert_A01_A02] '','',@estadoAporteTM,@apgid,'','','';                    
						EXEC [sap].[USP_GetICAPORTES_Insert_A03_A04] '','',@estadoAporteTM,@apgid,'','','';
						EXEC [sap].[USP_GetICAPORTES_Insert_A05_A06] '','',@estadoAporteTM,@apgid,'','',''; 
						EXEC [sap].[USP_GetICAPORTES_Insert_A07_A08] '','',@estadoAporteTM,@apgid,'','','';
						EXEC [sap].[USP_GetICAPORTES_Insert_A09_A10] '','',@estadoAporteTM,@apgid,'','','';

						print 'entra borrar tercero PA'
						DELETE FROM #TerceroPM
							WHERE apgid = @apgid
								AND apgEstadoRegistroAporteAportante = @estadoAporteTM;
								
								print 'sale borrar tercero PA'

							SET @countPM = @countPM+1
							PRINT (@countPM)
		
			
			
			COMMIT TRANSACTION
			
		END
		
	print 'ok Aporte Manual Tercero'

	SELECT 'ok ejec',GETDATE() 
	DELETE FROM	sap.ejecucionInternAaportes
	END
	ELSE BEGIN

		SELECT 'El proceso ya se encuentra en ejecucion'
	END

	SELECT valorActual FROM sap.ic_referencia where comentario IN ('P','A') AND estado = 'A'

	select valorActual from sap.IC_Referencia where clase = 'TE' and comentario = 'A' and estado = 'A'
	Select 'manuales terceros'

--------------------------------------------------CORRECCION A35 Y CORRECCION  AGREGADO POR YESIKA------------------------------------------------------------------------------------

IF (SELECT COUNT(*) FROM sap.ejecucionInternAaportes WITH (NOLOCK)) <= 0 BEGIN
		
		  
	INSERT sap.ejecucionInternAaportes
	SELECT 1

	PRINT 'INGRESO A35'
	
		
		DECLARE @solNumeroRadicacionA VARCHAR(30), @regidA varchar(10), @apgidA varchar(10), @regidO varchar(10)--, @solNumeroRadicacion varchar(30)

		DECLARE @A35 TABLE (id int identity, solNumeroRadicacion varchar(30), apgRegistroGeneral varchar(10), apdPersona varchar(10),apgEmpresaTramitadoraAporte varchar(10),apgempresa varchar(10),regtipoplanilla varchar(1))

			INSERT   @A35	
			SELECT DISTINCT sol.solNumeroRadicacion, apgRegistroGeneral, apdPersona ,apg.apgEmpresaTramitadoraAporte,apg.apgEmpresa,reg.regtipoplanilla	FROM Solicitud sol WITH (NOLOCK)
			INNER JOIN SolicitudCorreccionAporte sca WITH (NOLOCK) ON sol.solId =scaSolicitudGlobal
			INNER JOIN AporteGeneral apg  WITH (NOLOCK) ON scaAporteGeneral = apg.apgId
			INNER JOIN Correccion cor WITH (NOLOCK) ON sca.scaId = cor.corSolicitudCorreccionAporte AND scaResultadoSupervisor ='APROBADA'
			INNER JOIN AporteDetallado apd WITH (NOLOCK) ON cor.corAporteDetallado = apd.apdId AND apd.apdEstadoAporteAjuste = 'ANULADO'
			LEFT JOIN sap.ContablesCtrl pct WITH (NOLOCK) ON sol.solNumeroRadicacion = pct.solNumeroRadicacion
			INNER JOIN #registrogeneral reg on apg.apgregistrogeneral = reg.regid
			WHERE   sol.solTipoTransaccion  ='CORRECCION_APORTES'
			AND pct.solNumeroRadicacion IS NULL 
			and sol.solFechaRadicacion >= '2024-03-20'
			and sol.solNumeroRadicacion not in ('032024985266','032024496900')
			

		DECLARE @countA BIGINT = 0
		DECLARE @countA2 BIGINT = (SELECT COUNT(*) FROM @A35)

		
		WHILE (@countA < @countA2)
			BEGIN 
				BEGIN TRANSACTION
				SET @regidO = (select top 1  apgRegistroGeneral from @A35)
				SET @solNumeroRadicacionA = (select top 1 solNumeroRadicacion from @A35)
			
					DECLARE @A35p TABLE (id int identity, solNumeroRadicacion varchar(30), apgRegistroGeneral varchar(10), apdPersona varchar(10),apgEmpresaTramitadoraAporte varchar(10),apgempresa varchar(10), apgid varchar(10))
					INSERT  @A35p
					SELECT  S.solNumeroRadicacion, apg.apgRegistroGeneral, apd.apdPersona,apgEmpresaTramitadoraAporte,apgEmpresa, apgId
						FROM  Solicitud S WITH(NOLOCK)
							INNER JOIN SolicitudCorreccionAporte  C WITH(NOLOCK) ON S.solId = C.scaSolicitudGlobal
							INNER JOIN core.dbo.Correccion cor WITH(NOLOCK) ON C.scaid = cor.corsolicitudcorreccionaporte
							INNER JOIN AporteGeneral apg WITH(NOLOCK) ON cor.corAporteGeneral =  apg.apgid
							INNER JOIN AporteDetallado apd WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral	
							LEFT JOIN sap.ContablesCtrl pct WITH (NOLOCK) ON s.solNumeroRadicacion = pct.solNumeroRadicacion
			  				where s.solNumeroRadicacion = @solNumeroRadicacionA AND pct.solNumeroRadicacion IS NULL	
							
							

			print @solNumeroRadicacionA
			print'ingresa al ciclo'
	
						   
					if ((select top 1 apgEmpresaTramitadoraAporte from @A35) <> (select top 1 apgEmpresaTramitadoraAporte from @A35p) OR (select top 1 apgEmpresa from @A35) <> (select top 1 apgEmpresa from @A35p)) 
						begin 
							print 'entra A35' 		
							SET @regidA = (select top 1  apgRegistroGeneral from @A35p)

							EXEC [sap].[USP_GetICAPORTES_Insert_A35] '','','',@solNumeroRadicacionA,'',@regido,@regidA
				
							print 'A35'
						END

					else if ((((Select top 1 regtipoplanilla from @A35) = 'I') or ((Select top 1 regtipoplanilla from @A35) is null)) AND (Select top 1 apgEmpresaTramitadoraAporte from @A35) is null)
								begin 
									print 'entra A35I' 		
									SET @regidA = (select top 1  apgRegistroGeneral from @A35p)

									select @regidO
									select @regidA
									select @solNumeroRadicacionA
									EXEC [sap].[USP_GetICAPORTES_Insert_A35] '','','',@solNumeroRadicacionA,'',@regido,@regidA
				
									print 'A35I'
								END
						Else		
								begin
									print 'Entra A12'
									SET @apgidA = (select top 1 apgId from @A35P)
									SET @solNumeroRadicacion = (select top 1 solNumeroRadicacion from @A35)
									PRINT @solNumeroRadicacion
									EXEC [sap].[USP_GetICAPORTES_Insert_CORRECCION_TP] '','',@apgidA,@solNumeroRadicacion,'CORRECCION_APORTES',''
									print 'A12'
								END
					
				
				-- Si o si deben estar no comentariar
				DELETE @A35 WHERE solNumeroRadicacion = @solNumeroRadicacionA 
				DELETE @A35p WHERE solNumeroRadicacion = @solNumeroRadicacionA 
												
		
				set @countA = @countA+1
				print (@countA)
		

				COMMIT TRANSACTION
			END
		
	print 'ok A35'
	SELECT 'ok ejec  A35',GETDATE() 
	DELETE FROM	sap.ejecucionInternAaportes
	END
	ELSE BEGIN
		SELECT 'El proceso ya se encuentra en ejecucion'
	END

	--select valorActual from sap.IC_Referencia where clase = 'TE' and comentario = 'A' and estado = 'A'
	--Select 'correcciones'
	
---------------------------------------------------FIN ---------------------------------------------------------------------------------------------------


	
		 

-------------------------------------- INSERTAR VALORES INTEGRADOS EN TABLA DE MONITOREO -----------------------------------------------------
	SELECT @fechahorafinal = GETDATE() -'05:00:00'

	INSERT INTO #registros
     SELECT COUNT(*) FROM sap.IC_Aportes_Det WHERE fechaEjecucion >=@fechahorainicio AND FechaEjecucion <=@fechahorafinal	

	 	 
	INSERT INTO #registros
		 SELECT COUNT(*) FROM sap.IC_Aportes_Enc WHERE fecIng >= convert (date,@fechahorainicio) AND fecIng <= convert (date,@fechahorafinal)
		 and horaIng >= convert (varchar,@fechahorainicio, 108)  and horaIng <= convert (varchar,@fechahorafinal, 108)

	 UPDATE sap.IC_LogEjecucion SET RegistrosEnviados =(SELECT SUM(Nregistros) FROM #registros)
		WHERE id = @idlog

	
	UPDATE sap.IC_LogEjecucion set fechahorafinal = GETDATE() -'05:00:00'  where id = @idlog
	
--------------------------------------------------------------------------------------------------------------------------------------------
	SELECT 'ok ejec',GETDATE() 
	
		DROP TABLE IF EXISTS #registros
		DROP TABLE IF EXISTS #registrogeneral
		UPDATE SAP.ejecucion_int_aportes SET actual = 0 WHERE actual = 1
		DELETE FROM	sap.ejecucion_int_aportes 
	END
	ELSE BEGIN

		SELECT 'El proceso ya se encuentra en ejecucion'
	
	END
	
		
	
COMMIT TRANSACTION

END



/*
 DELETE FROM	sap.ejecucion_int_aportes WHERE ACTUAL = 1
 DELETE FROM	sap.ejecucionInternAaportes
 DELETE FROM	sap.ic_internoA
  

/*
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
SELECT count(*) FROM sap.ejecucion_int_aportes   WITH (NOLOCK)
SELECT count(*) FROM sap.ejecucionInternAaportes WITH (NOLOCK)
SELECT count(*) FROM sap.ic_internoA WITH (NOLOCK)

SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
SELECT * FROM sap.ejecucion_int_aportes  WITH(READUNCOMMITTED)
*/
--UPDATE sap.ejecucion_int_aportes SET actual = 1
*/