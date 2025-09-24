CREATE OR ALTER   PROCEDURE   [dbo].[USP_ExecuteCARTERAActualizaDeudaPresuntaEmpleadores]
					@idPersonaEmpleador BIGINT, -- Identificador único de persona
					@idEmpleador BIGINT, -- Identificador único de empleador
					@idEmpresa BIGINT, -- Identificador único de empresa
					@periodoEvaluacion VARCHAR(7), -- Periodo en evaluación YYYY-MM
					@crearNuevoRegistro BIT, -- 1: Si se va a crear un registro en Cartera. 0: Si se va a actualizar el registro
					@idCartera BIGINT, -- Identificador de cartera. NULL cuando @crearNuevoRegistro = 1
					@tablaPersonasConNovedad TablaPersonaIdType READONLY,
					@activaParametrizacionLC2 BIT, -- 1: Representa si se encuentra habilitada la parametrización para la linea de cobro 2
					@activaParametrizacionLC3 BIT -- 1: Representa si se encuentra habilitada la parametrización para la linea de cobro 3
AS
   
   
    -- se agrega registro en bitacora de ejecución
    IF EXISTS(SELECT 1
              FROM BitacoraEjecucionSp
              WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERAActualizaDeudaPresuntaEmpleadores')
        BEGIN
            UPDATE BitacoraEjecucionSp
            SET besUltimoInicio = dbo.getLocalDate()
            WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERAActualizaDeudaPresuntaEmpleadores'
        END
    ELSE
        BEGIN
            INSERT INTO BitacoraEjecucionSp (besNombreStoredProcedure, besUltimoInicio)
            VALUES ('USP_ExecuteCARTERAActualizaDeudaPresuntaEmpleadores', dbo.getLocalDate())
        END

    DECLARE @fechaInicioEjecucion DATETIME = dbo.GetLocalDate()
 
    DECLARE @valorAporte NUMERIC(19, 5)

    DECLARE @fechaRetiro VARCHAR(7)  
    DECLARE @fechaPeriodoEvaluacion DATE
    DECLARE @fechaActual DATETIME
    DECLARE @fechaDummy DATETIME -- Para pruebas

    DECLARE @lineaCobro VARCHAR(3)
    DECLARE @periodo1MesAtras VARCHAR(7)
    DECLARE @periodo1AnioAtras VARCHAR(7)
    DECLARE @periodo2AniosAtras VARCHAR(7)
    DECLARE @periodoAporte VARCHAR(7)
    DECLARE @periodoCambioEstadoAfiliacion VARCHAR(7)

    DECLARE @periodosCursor AS CURSOR

    DECLARE @deudaPresunta NUMERIC(19, 5)
    DECLARE @deudaPresuntaLC NUMERIC(19, 5)
    DECLARE @deudaPresuntaUnitaria NUMERIC(19, 5)
    DECLARE @aporte NUMERIC(19, 5)

    DECLARE @metodo VARCHAR(8)
    DECLARE @fechaAsignacionAccion DATETIME
    DECLARE @TipoAccionCobro VARCHAR(4)
    DECLARE @numeroOperacionLC1 BIGINT = 0

    DECLARE @iNewId BIGINT
    DECLARE @iRevision BIGINT

    DECLARE @tipoLineaCobro VARCHAR(3)

    DECLARE @dFechaBeneficio DATE
    DECLARE @bDepartamentoEspecial BIT
    DECLARE @porcentajeEmpleador NUMERIC(6, 5)

    -- Fechas y periodos
    SET @fechaPeriodoEvaluacion = CONVERT(DATE, @periodoEvaluacion + '-01', 121)
    SET @periodo1MesAtras = CONVERT(VARCHAR(7), DATEADD(mm, -1, @fechaPeriodoEvaluacion), 20)
 
 
    SET @fechaActual = dbo.GetLocalDate()

    -- Consulta @fechaDummy, para pruebas
    SELECT @fechaDummy = CONVERT(DATETIME, cnsValor, 120) FROM Constante WHERE cnsNombre = 'FECHA_DUMMY'
    IF @fechaDummy IS NOT NULL
        BEGIN
            SET @fechaActual = @fechaDummy
        END
 

-- Almacena el identificador de las personas que son trabajadores o cotizantes
    DECLARE @tablaTrabajadoresCotizantesSinNov AS TABLE
                                                  (
                                                      perId                        BIGINT,
                                                      trabajadorActivo             BIT,
                                                      trabajadorActivoAlMenosUnDia BIT,
                                                      sinArportePeriodoEvaluacion  BIT,
                                                      cadDeudaPresunta             NUMERIC
                                                  )

 
 
-- Almacena los aportes del periodos por empresa/aportante
    DECLARE @tablaAportesVigentes TABLE
                                  (
                                      perId           BIGINT,
                                      aporte          NUMERIC(19, 5),
                                      deudaIndividual NUMERIC(19, 5),
                                      tipoAporte      VARCHAR(37),
                                      DiasCotizados   NUMERIC(19, 5),
                                      SalarioBasico   NUMERIC(19, 5),
                                      tarifa          NUMERIC(5, 5)
                                  )

    -- temporal para las carteras creadas
    DECLARE @carteraCreada AS TABLE
                              (
                                  carDeudaPresunta         NUMERIC(19, 5),
                                  carDeudaPresuntaUnitaria NUMERIC(19, 5),
                                  carEstadoCartera         varchar(6),
                                  carEstadoOperacion       varchar(10),
                                  carFechaCreacion         datetime,
                                  carPersona               bigint,
                                  carMetodo                varchar(8),
                                  carPeriodoDeuda          date,
                                  carRiesgoIncobrabilidad  varchar(48),
                                  carTipoAccionCobro       varchar(4),
                                  carTipoDeuda             varchar(11),
                                  carTipoLineaCobro        varchar(3),
                                  carTipoSolicitante       varchar(13),
                                  carFechaAsignacionAccion datetime,
                                  carUsuarioTraspaso       varchar(255),
                                  carId                    bigint ,
								  carDeudaParcial  VARCHAR(35)
                              )

    -- temporal para las carteras dependientes creadas
    DECLARE @carteraDependienteCreada AS TABLE
                                         (
                                             cadDeudaPresunta   numeric(19, 5),
                                             cadEstadoOperacion varchar(10),
                                             cadCartera         bigint,
                                             cadPersona         bigint,
                                             cadDeudaReal       numeric(19, 5),
                                             cadAgregadoManual  bigint,
                                             cadId              bigint,
                                             cdcAccion          VARCHAR(1)
                                         )

  

    -- Almacena los aportes de la empresa
    DECLARE @tablaAportesTemp AS TABLE
                                 (
                                     apdPersona                     BIGINT,
                                     apdAporteObligatorio           NUMERIC(19, 5),
                                     apdSalarioBasico               NUMERIC(19, 5),
                                     apdEstadoRegistroAporteArchivo VARCHAR(60),
                                     apdEstadoAporteAjuste          VARCHAR(50),
                                     apgPeriodoAporte               VARCHAR(7),
                                     apgEstadoAporteAportante       VARCHAR(40),
                                     apdDiasCotizados               NUMERIC(19, 5),
                                     apdtarifa                      NUMERIC(19, 5),
                                     apdSalarioIntegral             BIT,
                                     apdValorIBC					NUMERIC(19, 5)
                                 )

    -- En esta tabla se agregan las novedades que no se identificaron en el primer punto por no tener novedad detalle
    -- y la fecha se restringe en este caso con roaFechaRetiro
    DECLARE @novedadRetiro AS TABLE
                              (
                                  norPersona BIGINT, fecharetiro VARCHAR(7)
                              )
 
   ---TABLAS CON LA INFORMACION 

        
    
   
   BEGIN TRY

 
  
        -- Se consultan los aportes de la empresa de los periodos indicados
        INSERT INTO @tablaAportesTemp (apdPersona, apdAporteObligatorio, apdSalarioBasico,
                                       apdEstadoRegistroAporteArchivo, apdEstadoAporteAjuste,
                                       apgPeriodoAporte, apgEstadoAporteAportante, apdDiasCotizados,
                                       apdtarifa, apdSalarioIntegral, apdValorIBC)
        SELECT apdPersona,
               sum(apdAporteObligatorio) [apdAporteObligatorio],
               apdSalarioBasico,
               apdEstadoRegistroAporteArchivo,
               apdEstadoAporteAjuste,
               apgPeriodoAporte,
               apgEstadoAporteAportante,
               apdDiasCotizados,
               apdTarifa,apdSalarioIntegral, apdValorIBC
        FROM AporteDetallado apd WITH (NOLOCK)
  INNER JOIN AporteGeneral apg WITH (NOLOCK) ON apd.apdAporteGeneral = apg.apgId
        WHERE apg.apgEmpresa = @idEmpresa
          AND apd.apdEstadoCotizante IN ('ACTIVO', 'INACTIVO')
          AND apg.apgPeriodoAporte IN (@periodoEvaluacion, @periodo1MesAtras)
          AND apd.apdTipoCotizante = 'TRABAJADOR_DEPENDIENTE'
        Group by  apdPersona,apdSalarioBasico,apdEstadoRegistroAporteArchivo,apdEstadoAporteAjuste,
        apgPeriodoAporte,apgEstadoAporteAportante,apdDiasCotizados,apdTarifa,apdSalarioIntegral, apdValorIBC,apdEstadoCotizante
        
        UNION

        SELECT DISTINCT apdPersona,
                        sum(apdAporteObligatorio) [apdAporteObligatorio],
                        apdSalarioBasico,
                        apdEstadoRegistroAporteArchivo,
                        apdEstadoAporteAjuste,
                        LEFT(apgPeriodoAporte,7),
                        apgEstadoAporteAportante,
                        apdDiasCotizados,
                        apdTarifa,apdSalarioIntegral, apdValorIBC
            FROM AporteDetallado apd WITH (NOLOCK)
        INNER JOIN persona pt on pt.perid = apd.apdPersona
        INNER JOIN Afiliado a ON a.afiPersona = pt.perid
        INNER JOIN RolAfiliado r ON r.roaAfiliado = a.afiid
            AND roaEstadoAfiliado = 'ACTIVO'
        INNER JOIN SolicitudAfiliacionPersona sap
                ON sap.sapRolAfiliado = r.roaid and sapEstadoSolicitud = 'CERRADA'
        INNER JOIN Solicitud s ON s.solid = sap.sapSolicitudGlobal
        INNER JOIN AporteGeneral apg WITH (NOLOCK)
                ON apd.apdAporteGeneral = apg.apgId
        INNER JOIN empresa e ON e.empid = apg.apgEmpresa
        INNER JOIN persona pe ON pe.perid =  e.emppersona
        INNER JOIN Cartera ON carPersona = PE.perId
            AND carEstadoCartera ='MOROSO'
            AND LEFT(carPeriodoDeuda,7) IN ( LEFT(@periodoEvaluacion,7), LEFT(@periodo1MesAtras,7))
            AND LEFT(carPeriodoDeuda,7) = LEFT(apg.apgPeriodoAporte,7)
        INNER JOIN CarteraDependiente
                ON cadcartera = carid
            AND cadpersona = pt.perid  AND cadDeudaPresunta>0
            WHERE apg.apgEmpresa = @idEmpresa
            AND apd.apdEstadoCotizante IN ('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES')-----CAMBIO OLGA VEGA 20230614 CAMBIO ES PARA LOS QUE TIENEN NOV DE ING AL MISMO TIEMPO
            AND LEFT(apg.apgPeriodoAporte,7) IN (LEFT(@periodoEvaluacion,7), LEFT(@periodo1MesAtras ,7))
            AND apd.apdTipoCotizante = 'TRABAJADOR_DEPENDIENTE'
        Group by  apdPersona,apdSalarioBasico,apdEstadoRegistroAporteArchivo,apdEstadoAporteAjuste,
        apgPeriodoAporte,apgEstadoAporteAportante,apdDiasCotizados,apdTarifa,apdSalarioIntegral, apdValorIBC,apdEstadoCotizante

    END TRY
    BEGIN CATCH
        INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
        VALUES (dbo.getLocalDate(), 'USP_ExecuteCARTERAActualizaDeudaPresuntaEmpleadores Sgft:Consulta de aportes',
                ERROR_MESSAGE());
        THROW;
    END CATCH


    BEGIN TRY
       
	   INSERT INTO @tablaAportesVigentes (perId, aporte, deudaIndividual, tipoAporte)
        SELECT apdPersona,
               apdAporteObligatorio,
               0,
               ISNULL(apdEstadoRegistroAporteArchivo, '')
        FROM @tablaAportesTemp
        WHERE apgPeriodoAporte = @periodoEvaluacion
          AND apgEstadoAporteAportante = 'VIGENTE'
          AND apdEstadoAporteAjuste = 'VIGENTE'


    END TRY
    BEGIN CATCH
        INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
        VALUES (dbo.getLocalDate(),
                'USP_ExecuteCARTERAActualizaDeudaPresuntaEmpleadores S:Selección de aportes vigentes para trabajadores/cotizantes',
                ERROR_MESSAGE());
        THROW;
    END CATCH

     BEGIN TRY
 
           BEGIN
           --  SELECT @periodoEvaluacion,'@periodoEvaluacion',@idEmpleador,'@idEmpleador'

			 INSERT INTO @novedadRetiro   (norPersona, fecharetiro) 
				 SELECT snpPersona ,  CONVERT(VARCHAR(7), roaFechaRetiro, 120) AS fecharetiro
				   FROM SolicitudNovedadPersona WITH(NOLOCK)
			 INNER JOIN SolicitudNovedad  WITH(NOLOCK) ON snoId = snpSolicitudNovedad
			 INNER JOIN Solicitud   WITH(NOLOCK) ON solId = snoSolicitudGlobal
			 INNER JOIN Persona  WITH(NOLOCK) ON perId = snpPersona
			 INNER JOIN Afiliado  WITH(NOLOCK) ON afiPersona = perId
			 INNER JOIN RolAfiliado  WITH(NOLOCK) ON roaAfiliado = afiId AND snpRolAfiliado= roaid 
				  WHERE solTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE')
					AND roaEmpleador = @idEmpleador
					AND CONVERT(VARCHAR(7), roaFechaRetiro, 120) <= @periodoEvaluacion

			UNION
				-----ANEXO POR GLPI		
				SELECT canPersona , CONVERT(VARCHAR(7),canFechaInicio, 120) AS fecharetiro
				  FROM carteranovedad 
				 WHERE canTipoNovedad IN ('RETIRO_TRABAJADOR_DEPENDIENTE')
				   AND CONVERT(VARCHAR(7),canFechaInicio, 120) <= @periodoEvaluacion
			UNION
			SELECT canPersona , CONVERT(VARCHAR(7),canFechaInicio, 120) AS fecharetiro
				  FROM carteranovedad 
				 WHERE canTipoNovedad IN (
											'INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL',
											'SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL',
											'LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL',
											'INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_PRESENCIAL',
											'PROCESO_INTERNO_DE_LA_CCF'
										  )
					AND CONVERT(VARCHAR(7),canFechaInicio, 120) >= @periodoEvaluacion 
					--AND CONVERT(VARCHAR(7),canFechaFin, 120)<= @periodoEvaluacion
			--SELECT '@novedadRetiro',* FROM @novedadRetiro
         END
  
  
  
  END TRY
    BEGIN CATCH
        INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
        VALUES (dbo.getLocalDate(),
                'USP_ExecuteCARTERAActualizaDeudaPresuntaEmpleadores S:Selección de novedades de retiro para trabajadores/cotizantes',
                ERROR_MESSAGE());
        THROW;
    END CATCH

   
        --BEGIN
        --    -- acualización de la deuda presunta
        --    BEGIN TRY
		
       
		BEGIN
				--PERSONAS CON NOVEDAD DE RETIRO  cdcAccion R retiro
			--	SELECT 'RETIRO'

		         INSERT @carteradependienteCreada
		  		 SELECT 0 as cadDeudaPresunta,'NO_VIGENTE' as cadEstadoOperacion,cd.cadCartera,cd.cadPersona,
				        0 as cadDeudaReal,cd.cadAgregadoManual,cd.cadId, 'R' as cdcAccion
				 ---UPDATE c 
				  FROM cartera c  WITH(NOLOCK)
			INNER JOIN Persona pe  WITH(NOLOCK)
			        ON pe.perid = c.carpersona 
		    INNER JOIN Carteradependiente cd  WITH(NOLOCK)
			        ON c.carid = cd.cadcartera 
			INNER JOIN Persona pt  WITH(NOLOCK)
			        ON pt.perid = cd.cadpersona
			INNER JOIN @novedadRetiro ret 
			        ON ret.norpersona = pt.perid 
				   AND CONVERT(VARCHAR(7), ret.fecharetiro,120) = CONVERT(VARCHAR(7),c.carperiododeuda,120)
			     WHERE pe.perid = @idPersonaEmpleador 
				   AND c.cardeudapresunta > 0 

			UNION


		  		 SELECT 0 as cadDeudaPresunta,'NO_VIGENTE' as cadEstadoOperacion,cd.cadCartera,cd.cadPersona,
				        0 as cadDeudaReal,cd.cadAgregadoManual,cd.cadId, 'R' as cdcAccion
				 ---UPDATE c 
				  FROM cartera c  WITH(NOLOCK)
			INNER JOIN Persona pe  WITH(NOLOCK)
			        ON pe.perid = c.carpersona 
			INNER JOIN Empresa e ON  e.empPersona = pe.perid 
			INNER JOIN Empleador em ON em.empEmpresa= e.empid  
		    INNER JOIN Carteradependiente cd  WITH(NOLOCK)
			        ON c.carid = cd.cadcartera 
			INNER JOIN Persona pt  WITH(NOLOCK)
			        ON pt.perid = cd.cadpersona
			INNER JOIN afiliado on afipersona = pt.perid 
			INNER JOIN RolAfiliado on roaAfiliado= afiid 
			       AND roaEmpleador= em.empid 
				   AND roaEstadoAfiliado = 'INACTIVO'
			INNER JOIN @novedadRetiro ret 
			        ON ret.norpersona = pt.perid 
				   AND CONVERT(VARCHAR(7), ret.fecharetiro,120) <= CONVERT(VARCHAR(7),c.carperiododeuda,120)
			     WHERE pe.perid = @idPersonaEmpleador 
				   AND c.cardeudapresunta > 0 

	 
		 -----PERSONA CON NOVEDADES DE PILA O PRESENCIAL  cdcAccion N NOVEDADES QUE EXCLUYEN DE CARTERA 

			--	SELECT 'NOVEDADES'

		         INSERT @carteradependienteCreada
		  		 SELECT 0 as cadDeudaPresunta,'NO_VIGENTE' as cadEstadoOperacion,cd.cadCartera,cd.cadPersona,
				        0 as cadDeudaReal,cd.cadAgregadoManual,cd.cadId, 'N' as cdcAccion
 				  FROM cartera c  WITH(NOLOCK)
			INNER JOIN Persona pe WITH(NOLOCK) 
			        ON pe.perid = c.carpersona 
		    INNER JOIN Carteradependiente cd  WITH(NOLOCK)
			        ON c.carid = cd.cadcartera 
			INNER JOIN Persona pt WITH(NOLOCK) 
			        ON pt.perid = cd.cadpersona
			INNER JOIN @tablaPersonasConNovedad pn ON pn.perId= cadpersona 
			 LEFT JOIN @carteradependienteCreada X ON X.cadCartera= cd.cadcartera and  x.cadid = cd.cadid 
			     WHERE pe.perid = @idPersonaEmpleador 
				  AND cd.caddeudapresunta>0 
				  AND CONVERT(VARCHAR(7),c.carperiododeuda,120) = @periodoEvaluacion
				  and x.cadid is null
				  
	 
 
--------DECRECE LA DEUDA cdcAccion D
--     BEGIN TRY

  --  SELECT 'DISMINUCION DE LA DEUDA'
	--	SELECT '@idPersonaEmpleador', @idPersonaEmpleador

                INSERT INTO @carteraDependienteCreada 
                SELECT DISTINCT  CASE WHEN  cad.cadDeudaPresunta - ISNULL(tap.apdaporteobligatorio,0)<=0 THEN 0
									  ELSE  cad.cadDeudaPresunta - ISNULL(tap.apdaporteobligatorio,0) END AS cadDeudaPresunta, 
						CASE WHEN cad.cadDeudaPresunta - ISNULL(tap.apdaporteobligatorio,0)<= 0 THEN 'NO_VIGENTE' 
						     WHEN cad.cadDeudaPresunta - ISNULL(tap.apdaporteobligatorio,0)>0 THEN 'VIGENTE' ELSE  'ALGOESTAMAL' END, cadCartera,cadPersona,
				        CASE WHEN cad.cadDeudaPresunta - ISNULL(tap.apdaporteobligatorio,0)<=0 THEN 0
						     WHEN cad.cadDeudaPresunta - ISNULL(tap.apdaporteobligatorio,0)>0 THEN 0 END AS cadDeudaReal,
							 cadAgregadoManual,cadId,'D'as cdcAccion
                  FROM CarteraDependiente cad WITH(NOLOCK)
			INNER JOIN Cartera c  WITH(NOLOCK) ON c.carid = cad.cadcartera 
            INNER JOIN @tablaAportesTemp tap ON tap.apdPersona = cad.cadpersona
			       AND LEFT(c.carperiododeuda,7)  =  tap.apgperiodoaporte
                 WHERE c.carpersona = @idPersonaEmpleador  
				   AND CONVERT(VARCHAR(7),c.carperiododeuda,120) = @periodoEvaluacion 
				   AND c.cardeudapresunta >0
				   AND cad.caddeudapresunta >0
                   AND cad.cadid NOT IN (SELECT cdc.cadid FROM @carteradependienteCreada cdc WHERE cdcAccion IN ('R','N'))
                
		
		----PARA NO TOCAR LO QUE NO CAMBIA
				DELETE  cdc 
			         FROM Carteradependiente cd 
		       INNER JOIN @Carteradependientecreada cdc 
			           ON cd.cadid = cdc.cadid 
					WHERE cd.cadDeudaPresunta = cdc.cadDeudaPresunta 
					AND cd.cadEstadoOperacion = cdc.cadEstadoOperacion 
					AND cd.cadDeudaReal = cdc.cadDeudaReal 


	 	-----ACTUALIZAR TODAS LAS CARTERAS DEPENDIENTES 


			UPDATE cd SET cd.cadDeudaPresunta = cdc.cadDeudaPresunta,
						  cd.cadEstadoOperacion = cdc.cadEstadoOperacion,
						  cd.cadDeudaReal = cdc.cadDeudaReal 
			         FROM Carteradependiente cd 
		       INNER JOIN @Carteradependientecreada cdc 
			           ON cd.cadid = cdc.cadid 

 

				UPDATE c SET c.carDeudaPresunta = ISNULL(sumaDependientes,0),
						      c.carEstadoCartera =      CASE WHEN sumaDependientes <= 0  THEN 'AL_DIA' 
															 WHEN sumaDependientes >  0  AND c.carTipoLineaCobro = 'LC1' THEN 'MOROSO' 
															 WHEN sumaDependientes >  0  AND c.carTipoLineaCobro IN ('LC1','C6') THEN 'MOROSO'
															 WHEN sumaDependientes >  0  AND c.carTipoLineaCobro NOT IN ('LC1','C6') THEN 'AL_DIA' END  ,														
						      c.carEstadoOperacion =  CASE WHEN sumaDependientes<= 0 THEN 'NO_VIGENTE' ELSE 'VIGENTE' END
			      FROM cartera c  WITH(NOLOCK)
			INNER JOIN Persona pe WITH(NOLOCK) 
			        ON pe.perid = c.carpersona 	   
			 INNER JOIN (SELECT cadCartera, ISNULL(SUM(cadDeudaPresunta),0) AS sumaDependientes
						 FROM CarteraDependiente WITH(NOLOCK)
						--WHERE cadEstadoOperacion ='VIGENTE' 
						GROUP BY cadCartera) as dependiente 
						ON dependiente.cadCartera  = carId
			    WHERE c.carid IN (SELECT cadcartera FROM @Carteradependientecreada GROUP BY cadcartera)

  
		  --SELECT * FROM @Carteradependientecreada  

				---*/////auditoria ///***----

	  IF (SELECT cadcartera FROM @Carteradependientecreada GROUP BY cadcartera) >0
	  BEGIN
 	       EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.ActualizaCarteraXPago.Cartera', 1, '', 'ACTUALIZA_CARTERA',@iRevision OUTPUT
	  
	
	--EXEC USP_UTIL_GET_CrearRevisionActualizar 'com.asopagos.ActualizaCarteraXPago.Cartera', 1, '', 'ACTUALIZA_CARTERA',@iRevision OUTPUT
 -- SELECT @iRevision,'@iRevision'

 /****************************************************************
 * Proceso que verifica si para el empleador que se esta evaluando 
 * existe un descuadre entre la cartera y su detalle en cualquiera 
 * de los periodos por los que esta en cartera
 * Ajuste por el GLPI 91697
 ****************************************************************/

 IF EXISTS (select distinct 1 from Persona p
			inner join Empresa epr on p.perId = epr.empPersona
			inner join Empleador epl on epl.empEmpresa = epr.empId
			inner join Cartera c on c.carPersona = p.perId
			inner join (select d.cadCartera,sum(d.cadDeudaPresunta) as [sumaDependientes] from CarteraDependiente d  group by d.cadCartera) cd on c.carId = cd.cadCartera
			where c.carDeudaPresunta != cd.sumaDependientes and epr.empId = @idEmpresa
			)  
BEGIN
/** 
*
* Se debe corrige la diferencia en la Cartera y se actualiza la 
* @Carteradependientecreada para dejar el registro de la correccion en la auditoria
*
**/
	-- ### Se crea una temporal para capturar las carteras que presentan diferencias
	DROP TABLE IF EXISTS #tmpCorreccionCartera
	select distinct
		 p.perTipoIdentificacion,p.perNumeroIdentificacion,p.perRazonSocial,epr.empId [idEmpresa],epl.empId[idEmpleador],p.perId[idPersonaEmpleador]
		,c.carId,c.carDeudaPresunta,cd.sumaDependientes,c.carEstadoCartera,c.carEstadoOperacion,c.carTipoLineaCobro,c.carPeriodoDeuda,c.carTipoDeuda,c.carTipoSolicitante	
	into #tmpCorreccionCartera
	from Persona p
	inner join Empresa epr on p.perId = epr.empPersona
	inner join Empleador epl on epl.empEmpresa = epr.empId
	inner join Cartera c on c.carPersona = p.perId
	inner join (select d.cadCartera,sum(d.cadDeudaPresunta) as [sumaDependientes] from CarteraDependiente d  group by d.cadCartera) cd on c.carId = cd.cadCartera
	where c.carDeudaPresunta != cd.sumaDependientes and epr.empId = @idEmpresa
	order by p.perNumeroIdentificacion,c.carPeriodoDeuda desc



	-- ### SE LIMPIA LOS REGISTROS DE LA #tmpCorreccionCartera si ya existen
	DELETE FROM @carteraDependienteCreada WHERE cadCartera in (select carId from #tmpCorreccionCartera)

	-- ### SE INSERTAN LOS REGISTROS CORRECTOS DE LA CARTERA DEPENDIENTE
	INSERT INTO @carteraDependienteCreada 
	select cd.sumaDependientes,cd.cadEstadoOperacion,cd.cadCartera,cd.cadPersona,cd.cadDeudaReal,cd.cadAgregadoManual,cd.cadId,NULL cdcAccion
	from Persona p
	inner join Empresa epr on p.perId = epr.empPersona
	inner join Empleador epl on epl.empEmpresa = epr.empId
	inner join Cartera c on c.carPersona = p.perId
	inner join (select sum(d.cadDeudaPresunta) as [sumaDependientes],d.cadEstadoOperacion,d.cadCartera,d.cadPersona,d.cadDeudaReal,
						d.cadAgregadoManual,d.cadId
				from CarteraDependiente d  
				group by d.cadEstadoOperacion,d.cadCartera,d.cadPersona,d.cadDeudaReal,d.cadAgregadoManual,d.cadId) cd on c.carId = cd.cadCartera
	where c.carDeudaPresunta != cd.sumaDependientes and epr.empId = (select distinct t.idEmpresa from #tmpCorreccionCartera t)
	and cd.cadCartera in (select carId from #tmpCorreccionCartera)


				UPDATE c SET c.carDeudaPresunta = ISNULL(sumaDependientes,0),
						      c.carEstadoCartera =      CASE WHEN sumaDependientes <= 0  THEN 'AL_DIA' 
															 WHEN sumaDependientes >  0  AND c.carTipoLineaCobro = 'LC1' THEN 'MOROSO' 
															 WHEN sumaDependientes >  0  AND c.carTipoLineaCobro IN ('LC1','C6') THEN 'MOROSO'
															 WHEN sumaDependientes >  0  AND c.carTipoLineaCobro NOT IN ('LC1','C6') THEN 'AL_DIA' END  ,														
						      c.carEstadoOperacion =  CASE WHEN sumaDependientes<= 0 THEN 'NO_VIGENTE' ELSE 'VIGENTE' END
	--SELECT  ISNULL(sumaDependientes,0),
	--		CASE WHEN sumaDependientes<= 0  THEN 'AL_DIA' 
	--			 WHEN sumaDependientes> 0  AND c.carTipoLineaCobro = 'LC1' THEN 'MOROSO' 
	--			 WHEN sumaDependientes> 0  AND c.carTipoLineaCobro<> 'LC1' THEN 'AL_DIA' END,	 
	--		CASE WHEN sumaDependientes<= 0 THEN 'NO_VIGENTE' ELSE 'VIGENTE' END
	from Persona p
	inner join Empresa epr on p.perId = epr.empPersona
	inner join Empleador epl on epl.empEmpresa = epr.empId
	inner join Cartera c on c.carPersona = p.perId
	inner join (select d.cadCartera,sum(d.cadDeudaPresunta) as [sumaDependientes] from CarteraDependiente d  group by d.cadCartera) cd on c.carId = cd.cadCartera
	where c.carDeudaPresunta != cd.sumaDependientes and epr.empId in (select distinct t.idEmpresa from #tmpCorreccionCartera t)


END
			 	 
									/*CORE.aud.CARTERA_AUD*/
                            INSERT INTO CORE.aud.CARTERA_AUD
                            SELECT DISTINCT  @iRevision AS REV, 
                                    1 AS REVTYPE, 
                                    c.carDeudaPresunta, 
                                    c.carEstadoCartera, 
                                    c.carEstadoOperacion, 
                                    c.carFechaCreacion, 
                                    c.carPersona, 
                                    c.carMetodo, 
                                    c.carPeriodoDeuda, 
                                    c.carRiesgoIncobrabilidad, 
                                    c.carTipoAccionCobro, 
                                    c.carTipoDeuda, 
                                    c.carTipoLineaCobro, 
                                    c.carTipoSolicitante, 
                                    c.carFechaAsignacionAccion, 
                                    c.carId, 
                                    c.carUsuarioTraspaso, 
                                    c.carDeudaPresuntaUnitaria,null ,c.carPrescribir
                            FROM cartera c 
							 INNER JOIN @Carteradependientecreada cc 
							         ON c.carId = cc.cadCartera 
				                
									ORDER BY  carId;
		 
 

                            INSERT INTO  aud.CARTERADEPENDIENTE_AUD
                            SELECT cd.cadId, 
                                    @iRevision as revId,
                                    1 AS REVTYPE,
                                    x.cadDeudaPresunta,
                                    x.cadEstadoOperacion,
                                    x.cadCartera,
                                    x.cadPersona,
                                    MAX(cd.cadDeudaReal) AS  cadDeudaReal,
                                    MAX(cd.cadAgregadoManual ) AS cadAgregadoManual
                            FROM CarteraDependiente cd  
						    INNER JOIN @Carteradependientecreada x 
							          ON cd.cadCartera = x.cadCartera 
									  AND cd.cadId = X.cadId
								      AND cd.cadDeudaPresunta = X.cadDeudaPresunta
								-- WHERE  cd.cadDeudaPresunta>0
							 GROUP BY x.cadDeudaPresunta,
										x.cadEstadoOperacion,
										x.cadCartera,
										x.cadPersona,
										 cd.cadId , X.cadId

  
				END

EXEC USP_ExecuteCARTERACalculoDeudaParcial @idCartera
 
--    END TRY
--            BEGIN CATCH
--                INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
--                VALUES (dbo.getLocalDate(),
--                        'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Actualizar monto por aportes recibido',
--                        ERROR_MESSAGE());
--                THROW;
--            END CATCH
 
	END