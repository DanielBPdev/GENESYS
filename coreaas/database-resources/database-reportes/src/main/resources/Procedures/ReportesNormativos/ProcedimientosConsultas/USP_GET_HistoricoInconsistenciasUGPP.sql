--- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/14
-- Modified:	Francisco Alejandro Hoyos Rojas
-- Create date: 2020/08/13
-- Description:	Inserta datos para reporte HistoricoInconsistenciasUGPP
-- =============================================
CREATE PROCEDURE USP_GET_HistoricoInconsistenciasUGPP
(
	@fechaInicio DATE,
	@fechaFin DATE,
	@historico BIT = NULL
)
AS
BEGIN TRY
	SET NOCOUNT ON;

	DECLARE @sql NVARCHAR(4000);

	DECLARE @tCartera AS TABLE(
	    perAportante BIGINT, 
        perCotizante BIGINT, 
        estado VARCHAR(10), 
        fechaInicio DATETIME, 
        fecRev DATETIME, 
        deuda NUMERIC(19,5), 
        carId BIGINT, 
        shard VARCHAR(500)
    )

	SET @sql = 
    '
		SELECT T.perAportante, T.perCotizante, T.estado, T.fechaInicio, T.fecRev, 
			CASE WHEN T.cadId IS NULL 
				THEN (SELECT TOP 1 car.carDeudaPresunta FROM dbo.Cartera_aud car WHERE car.carId = T.carId and car.REV = T.rev)
				ELSE (SELECT TOP 1 cad.cadDeudaPresunta FROM dbo.CarteraDependiente_aud cad WHERE cad.cadId = T.cadId and cad.REV = T.rev)
			END deuda, T.carId
		FROM (
			SELECT car.carPersona perAportante, cad.cadPersona perCotizante, car.carEstadoOperacion estado, car.carFechaCreacion fechaInicio, 
				MAX(DATEADD(SECOND, revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'') fecRev, 
				car.carId, cad.cadId, MAX(car.REV) rev
			FROM dbo.Cartera_aud car
			INNER JOIN dbo.Revision rev ON rev.revId = car.REV
			LEFT JOIN dbo.CarteraDependiente_aud cad ON car.carId = cad.cadCartera
			WHERE 1 = 1
			AND car.carTipoLineaCobro IN (''LC2'',''LC3'')
			AND (DATEADD(SECOND, revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'') BETWEEN @fechaInicio AND @fechaFin
			GROUP BY car.carPersona, cad.cadPersona, car.carEstadoOperacion, car.carFechaCreacion, car.carId, cad.cadId
		) AS T'

	INSERT INTO @tCartera (perAportante, perCotizante, estado, fechaInicio, fecRev, deuda, carId, shard)
	EXEC sp_execute_remote CoreAudReferenceData,
	@sql,
	N'@fechaInicio DATE, @fechaFin DATE',
	@fechaInicio = @fechaInicio, @fechaFin = @fechaFin
    
    UPDATE tcar SET tcar.deuda = cd.cadDeudaPresunta  
	  FROM @tCartera tcar
	INNER JOIN carteradependiente cd ON cd.cadpersona = tcar.perCotizante
	 
	DELETE @tCartera WHERE deuda=0

    IF @historico = 1
	BEGIN
		INSERT rno.HistoricoInconsistenciasUGPP(
			hinFechaHistorico,
			hinTipoAdmin,
			hinCodAdmin,
			hinNomAdmin,
			hinTipoIdAportante,
			hinNumIdAportante,
			hinRazonSocial,
			hinCodigoDep,
			hinCod,
			hinDireccion,
			hinTipoIdCotizante,
			hinNumIdCotizante,
			hinConcepto,
			hinAnioInicio,
			hinMesInicio,
			hinAnioFin,
			hinMesFin,
			hinDeuda,
			hinUltimaAccion,
			hinFechaAccion,
			hinObservaciones,
			hinFechaInicialReporte,
			hinFechaFinalReporte)
        SELECT
            @fechaFin,
			'CCF' tipoAdmin,
			(SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') codAdmin,
			(SELECT prmValor FROM PARAMETRO WHERE prmNombre='NOMBRE_CCF') nomAdmin,
			CASE
				WHEN per.perTipoIdentificacion = 'NIT' THEN 'NI'
				WHEN per.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
				WHEN per.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
				WHEN per.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
				WHEN per.perTipoIdentificacion = 'REGISTRO_CIVIL' THEN 'RC'
				WHEN per.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
				WHEN per.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD'
			END tipoIdAportante,
			per.perNumeroIdentificacion numIdAportante,
			CASE WHEN ISNULL(per.perPrimerNombre, '') = '' THEN per.perRazonSocial
			ELSE (
				per.perPrimerNombre +
				CASE WHEN per.perSegundoNombre IS NULL THEN ' ' ELSE ' '+per.perSegundoNombre+' ' END +
				per.perPrimerApellido +
				CASE WHEN per.perSegundoApellido IS NULL THEN '' ELSE ' '+per.perSegundoApellido END
			) END razonSocial,
			dep.depCodigo codigoDep,
			substring(mun.munCodigo,3,6) cod,
			ubi.ubiDireccionFisica direccion,
			CASE
                WHEN ptr.perTipoIdentificacion = 'NIT' THEN 'NI'
				WHEN ptr.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
				WHEN ptr.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
				WHEN ptr.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
				WHEN ptr.perTipoIdentificacion = 'REGISTRO_CIVIL' THEN 'RC'
				WHEN ptr.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
				WHEN ptr.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD'
			END tipoIdCotizante,
            ptr.perNumeroIdentificacion numIdCotizante,
            (SELECT CASE WHEN  w.empestadoempleador IN ('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_RETIRADO_CON_APORTES') THEN 'Omiso_afiliación'
            WHEN inex.apgEmpresa IS NOT NULL THEN 'Inexactitud' END 
			 FROM VW_EstadoAfiliacionEmpleadorCaja w where w.perid = per.perid) as concepto,
            DATEPART(YEAR, car.fechaInicio ) anioInicio,
			DATEPART(MONTH,  car.fechaInicio ) mesInicio,
			CASE WHEN car.estado = 'VIGENTE' THEN NULL ELSE DATEPART(YEAR, car.fecRev) END anioFin,
			CASE WHEN car.estado = 'VIGENTE' THEN NULL ELSE DATEPART(MONTH, car.fecRev) END mesFin,
			CONVERT(NUMERIC(9, 0), ISNULL(car.deuda, 0)) deuda,
			A.accion ultimaAccion,
			A.fecha fechaAccion,
			NULL AS observaciones,
            @fechaInicio,
			@fechaFin 
        	FROM dbo.Persona per
		INNER JOIN Empresa emp ON per.perId = emp.empPersona
		INNER JOIN @tCartera car ON car.perAportante = per.perId
		LEFT JOIN UbicacionEmpresa ube ON ube.ubeEmpresa = emp.empId AND ube.ubeTipoUbicacion='ENVIO_CORRESPONDENCIA'
		LEFT JOIN dbo.Ubicacion ubi ON ube.ubeUbicacion = ubi.ubiId
		LEFT JOIN dbo.Municipio mun ON ubi.ubiMunicipio = mun.munId
		LEFT JOIN dbo.Departamento dep ON mun.munDepartamento = dep.depId
		LEFT JOIN dbo.Persona ptr ON car.perCotizante = ptr.perId
        LEFT JOIN (
            SELECT accion,
				MAX(fecha) fecha, carId
			FROM (
				SELECT
					CASE
						WHEN acrActividadCartera IN ('FISCALIZACION_TELEFONICA', 'GESTION_TELEFONICA', 'LLAMADO') THEN 'LLAMADA'
						WHEN acrActividadCartera IN ('SOLICITAR_DOCUMENTACION') THEN 'OFICIO'
						ELSE NULL
					END accion,
					CONVERT(DATE, acrFecha, 121) fecha, acrCartera carId
			   	FROM ActividadCartera
				WHERE acrCartera IS NOT NULL
					AND acrFecha BETWEEN @fechaInicio AND @fechaFin
                UNION ALL
                SELECT
					CASE
						WHEN agrVisitaAgenda IN ('FISCALIZACION_TELEFONICA', 'GESTION_TELEFONICA', 'LLAMADO') THEN 'LLAMADA'
						WHEN agrVisitaAgenda IN ('VISITA', 'VISITA_A_CAJA') THEN 'VISITA'
						ELSE NULL
					END accion, agrFecha fecha, agrCartera carId
				FROM AgendaCartera
                WHERE agrCartera IS NOT NULL
					AND agrFecha BETWEEN @fechaInicio AND @fechaFin
				 UNION ALL
			   SELECT DISTINCT 
					CASE   
						WHEN bcaMedio IN ('DOCUMENTO_FISICO') AND bcaResultado = 'EXITOSO' THEN 'OFICIO'
						WHEN bcaMedio IN ('ELECTRONICO') AND bcaResultado = 'EXITOSO' THEN 'EMAIL' 
						ELSE NULL
					END accion, bcaFecha fecha, carid  
				FROM BitacoraCartera inner join cartera on carpersona = bcapersona 
				WHERE carid IS NOT NULL
					AND bcaFecha BETWEEN @fechaInicio AND @fechaFin
			) AS T
            WHERE accion IS NOT NULL
			GROUP BY accion, carId
		) AS A ON A.carId = car.carId
		LEFT JOIN VW_EstadoAfiliacionEmpleadorCaja est ON est.perId = per.perId
		LEFT JOIN (SELECT 
						apg.apgEmpresa,
						apd.apdPersona
		            FROM
					AporteGeneral apg
					INNER JOIN aportedetallado apd ON apg.apgId = apd.apdAporteGeneral
					INNER JOIN RegistroDetallado red ON red.redId = apd.apdRegistroDetallado
					INNER JOIN carteradependiente cad on cadpersona = apd.apdpersona 
					inner join cartera c on c.carid = cad.cadcartera and carTipoLineaCobro IN ('LC2','LC3')
					WHERE red.redOUTEstadoValidacionV2 = 'NO_OK'
					   OR red.redOUTEstadoValidacionV3 = 'NO_OK'
				  ) inex 
				  ON inex.apgEmpresa = emp.empId 
				  AND inex.apdPersona = ptr.perId
        WHERE (est.empEstadoEmpleador IN ('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_RETIRADO_CON_APORTES') 
		  	OR 
			  inex.apgEmpresa IS NOT NULL)
        UNION
        SELECT 
        	@fechaFin,
			'CCF' tipoAdmin,
			(SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') codAdmin,
			(SELECT prmValor FROM PARAMETRO WHERE prmNombre='NOMBRE_CCF') nomAdmin,
			CASE
				WHEN per.perTipoIdentificacion = 'NIT' THEN 'NI'
				WHEN per.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
				WHEN per.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
				WHEN per.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
				WHEN per.perTipoIdentificacion = 'REGISTRO_CIVIL' THEN 'RC'
				WHEN per.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
				WHEN per.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD'
			END tipoIdAportante,
			per.perNumeroIdentificacion numIdAportante,
			CASE WHEN ISNULL(per.perPrimerNombre, '') = '' THEN per.perRazonSocial
			ELSE (
				per.perPrimerNombre +
				CASE WHEN per.perSegundoNombre IS NULL THEN ' ' ELSE ' '+per.perSegundoNombre+' ' END +
				per.perPrimerApellido +
				CASE WHEN per.perSegundoApellido IS NULL THEN '' ELSE ' '+per.perSegundoApellido END
			) END razonSocial,
			dep.depCodigo codigoDep,
			substring(mun.munCodigo,3,6) cod,
			ubi.ubiDireccionFisica direccion,
			CASE
				WHEN per.perTipoIdentificacion = 'NIT' THEN 'NI'
				WHEN per.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
				WHEN per.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
				WHEN per.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
				WHEN per.perTipoIdentificacion = 'REGISTRO_CIVIL' THEN 'RC'
				WHEN per.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
				WHEN per.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD'			
			END tipoIdCotizante,
			CASE WHEN per.perNumeroIdentificacion IS NOT NULL THEN per.perNumeroIdentificacion ELSE per.perNumeroIdentificacion END numIdCotizante, 
			 	(select	 CASE WHEN  w.roaestadoafiliado  IN ('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_RETIRADO_CON_APORTES') THEN 'Omiso_afiliación'
			WHEN inex.apdPersona IS NOT NULL THEN 'Inexactitud' END FROM VW_EstadoAfiliacionPersonaCaja w where w.perid = per.perid) concepto,
			DATEPART(YEAR,car.fechaInicio) as anioInicio, 
			DATEPART(month,car.fechaInicio) as mesInicio, 
			CASE WHEN car.estado = 'VIGENTE' THEN NULL ELSE DATEPART(YEAR, car.fecRev) END anioFin,
			CASE WHEN car.estado = 'VIGENTE' THEN NULL ELSE DATEPART(MONTH, car.fecRev) END mesFin, 
			CONVERT(NUMERIC(9, 0), ISNULL(car.deuda, 0)) deuda, 
			A.accion ultimaAccion,
			A.fecha fechaAccion,
			NULL AS observaciones,
			@fechaInicio,
			@fechaFin
            FROM dbo.Persona per
		    INNER JOIN Afiliado afi ON afi.afiPersona=per.perId
		    INNER JOIN RolAfiliado roa ON roa.roaAfiliado = afi.afiId AND roa.roaTipoAfiliado <> 'TRABAJADOR_DEPENDIENTE'	
		    INNER JOIN @tCartera car ON car.perCotizante = per.perId
		    LEFT JOIN dbo.Ubicacion ubi ON per.perUbicacionPrincipal = ubi.ubiId
		    LEFT JOIN dbo.Municipio mun ON ubi.ubiMunicipio = mun.munId
		    LEFT JOIN dbo.Departamento dep ON mun.munDepartamento = dep.depId
		    LEFT JOIN (
                	SELECT accion,
				MAX(fecha) fecha, carId
			FROM (
				SELECT 
					CASE
						WHEN acrActividadCartera IN ('FISCALIZACION_TELEFONICA', 'GESTION_TELEFONICA', 'LLAMADO') THEN 'LLAMADA'
						WHEN acrActividadCartera IN ('SOLICITAR_DOCUMENTACION') THEN 'OFICIO'
						ELSE NULL
					END accion, 
					CONVERT(DATE, acrFecha, 121) fecha, acrCartera carId
				FROM ActividadCartera
				WHERE acrCartera IS NOT NULL 
					AND acrFecha BETWEEN @fechaInicio AND @fechaFin
				UNION ALL
				SELECT 
					CASE
						WHEN agrVisitaAgenda IN ('FISCALIZACION_TELEFONICA', 'GESTION_TELEFONICA', 'LLAMADO') THEN 'LLAMADA'
						WHEN agrVisitaAgenda IN ('VISITA', 'VISITA_A_CAJA') THEN 'VISITA'
						ELSE NULL
					END accion, agrFecha fecha, agrCartera carId
				FROM AgendaCartera
				WHERE agrCartera IS NOT NULL 
					AND agrFecha BETWEEN @fechaInicio AND @fechaFin
				UNION ALL
			   SELECT DISTINCT 
					CASE   
						WHEN bcaMedio IN ('DOCUMENTO_FISICO') AND bcaResultado = 'EXITOSO' THEN 'OFICIO'
						WHEN bcaMedio IN ('ELECTRONICO') AND bcaResultado = 'EXITOSO' THEN 'EMAIL' 
						ELSE NULL
					END accion, bcaFecha fecha, carid  
				FROM BitacoraCartera INNER JOIN cartera ON carpersona = bcapersona 
				WHERE carid IS NOT NULL
					AND bcaFecha BETWEEN @fechaInicio AND @fechaFin
			) AS T
			WHERE accion IS NOT NULL
			GROUP BY accion, carId
		) AS A ON A.carId = car.carId
		LEFT JOIN (SELECT apd.apdPersona
					FROM aportedetallado apd
					INNER JOIN RegistroDetallado red ON red.redId = apd.apdRegistroDetallado
					INNER JOIN carteradependiente cad ON cadpersona = apd.apdpersona 
					INNER JOIN cartera c ON c.carid = cad.cadcartera and carTipoLineaCobro IN ('LC2','LC3')
					WHERE apd.apdTipoCotizante <> 'TRABAJADOR_DEPENDIENTE'
					  AND (red.redOUTEstadoValidacionV2 = 'NO_OK'
					  OR red.redOUTEstadoValidacionV3 = 'NO_OK')
				  ) inex ON inex.apdPersona = per.perId
		LEFT JOIN VW_EstadoAfiliacionPersonaCaja est ON est.perId = per.perId
		WHERE  (est.roaEstadoAfiliado IN ('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_RETIRADO_CON_APORTES')
		  	OR inex.apdPersona IS NOT NULL)
        END
END TRY
BEGIN CATCH
	THROW;
END CATCH
;