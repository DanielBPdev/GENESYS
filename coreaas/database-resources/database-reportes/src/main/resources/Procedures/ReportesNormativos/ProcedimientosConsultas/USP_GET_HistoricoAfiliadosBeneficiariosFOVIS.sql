-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/14
-- Description:	Inserta datos para reporte 
-- =============================================
CREATE PROCEDURE USP_GET_HistoricoAfiliadosBeneficiariosFOVIS
(
	@fechaInicio DATE,
	@fechaFin DATE,
	@historico BIT = NULL
)
AS
BEGIN TRY
	SET NOCOUNT ON;

	IF @historico = 1
	BEGIN
		INSERT rno.HistoricoAfiliadosBeneficiariosFOVIS(
			habFechaHistorico,
			habValor,
			habNumeroIdentificacion1,
			habApellido,
			habNombre,
			habValor2,
			habSafFechaAceptacion,
			habValorAsignadoSFV,
			habNumeroResolucion,
			habVacio,
			habTipoIdentificacion,
			habFechaInicialReporte,
			habFechaFinalReporte)
		SELECT @fechaFin,datos.Valor, datos.NumeroIdentificacion1, datos.Apellido, datos.Nombre, datos.Valor2, 
		datos.safFechaAceptacion, datos.ValorAsignadoSFV, datos.NumeroResolucion, 
		datos.vacio, datos.TipoIdentificacion,@fechaInicio,	@fechaFin
		FROM (
	    	SELECT DISTINCT prm.prmValor AS Valor,
	        per.perNumeroIdentificacion AS NumeroIdentificacion1,
	        CONCAT(per.perPrimerApellido, 
	            CASE WHEN per.perSegundoApellido IS NOT NULL 
	                THEN CONCAT(' ', per.perSegundoApellido) ELSE '' END) AS Apellido,
	        CONCAT(per.perPrimerNombre,
	            CASE WHEN per.perSegundoNombre IS NOT NULL
	                THEN CONCAT(' ', per.perSegundoNombre) ELSE '' END) AS Nombre,
	        prma.prmValor AS Valor2,
	        CAST(FORMAT(saf.safFechaAceptacion, 'yyyy/MM/dd')AS VARCHAR(20)) AS safFechaAceptacion,
	        CASE WHEN (pof.pofValorAsignadoSFV % 1 ) > 0 
	            THEN CAST(pof.pofValorAsignadoSFV AS VARCHAR)
	            ELSE CAST(CAST(pof.pofValorAsignadoSFV AS BIGINT) AS VARCHAR) 
	        END AS ValorAsignadoSFV,
	        '6' AS h,
	        (SELECT TOP 1 aaf.aafNumeroOficio
	            FROM ActaAsignacionFovis aaf
	            WHERE aaf.aafSolicitudAsignacion = saf.safId
	            ORDER BY saf.safFechaAceptacion DESC) AS NumeroResolucion,
	        '' AS vacio,
	        CASE per.perTipoIdentificacion 
	            WHEN 'CEDULA_CIUDADANIA' THEN '1'
	            WHEN 'CEDULA_EXTRANJERIA' THEN '2'
	            WHEN 'TARJETA_IDENTIDAD' THEN '3'
	        END AS TipoIdentificacion,
	        (SELECT sol.solId 
	            FROM SolicitudNovedadFovis snf 
	            JOIN Solicitud sol ON (snf.snfSolicitudGlobal = sol.solId)
	            JOIN SolicitudNovedadPersonaFovis spf ON (snf.snfId = spf.spfSolicitudNovedadFovis)
	            WHERE sol.solTipoTransaccion = 'RENUNCIO_SUBISIDIO_ASIGNADO'
	            AND sol.solResultadoProceso = 'APROBADA'
	            AND snf.snfEstadoSolicitud = 'NOV_FOVIS_CERRADA'
	            AND spf.spfPostulacionFovis = pof.pofId) AS solicitud
	    FROM SolicitudAsignacion saf
	    INNER JOIN PostulacionFOVIS pof ON (saf.safId = pof.pofSolicitudAsignacion)
	    INNER JOIN (
	        SELECT jeh.jehId AS idJefe, jeh.jehEstadoHogar AS estado, afi.afiPersona AS idPersona
	        FROM JefeHogar jeh
	        JOIN Afiliado afi ON (jeh.jehAfiliado = afi.afiId)
	        UNION
	        SELECT inh.inhJefeHogar, inh.inhEstadoHogar, inh.inhPersona
	        FROM IntegranteHogar inh) AS integrante ON (pof.pofJefeHogar = integrante.idJefe)
	    INNER JOIN Persona per ON (integrante.idPersona = per.perId)
	    LEFT JOIN PersonaDetalle ped ON (per.perId = ped.pedPersona)
	    INNER JOIN Parametro prm ON (prm.prmNombre = 'NUMERO_ID_CCF')
	    INNER JOIN Parametro prma ON (prma.prmNombre = 'NOMBRE_CCF')
	    WHERE pof.pofResultadoAsignacion = 'ESTADO_ASIGNADO'
	    AND integrante.estado = 'ACTIVO'
	    AND DATEDIFF(YEAR, ped.pedFechaNacimiento, dbo.GetLocalDate()) >= 18 
	    AND saf.safFechaAceptacion BETWEEN @fechaInicio AND @fechaFin) AS datos
		WHERE datos.solicitud IS NULL
		AND datos.TipoIdentificacion IS NOT NULL
	END
	ELSE	
	BEGIN
		SELECT datos.Valor, datos.NumeroIdentificacion1, datos.Apellido, datos.Nombre, datos.Valor2, 
		datos.safFechaAceptacion, datos.ValorAsignadoSFV, datos.NumeroResolucion, 
		datos.vacio, datos.TipoIdentificacion
		FROM (
		    SELECT DISTINCT prm.prmValor AS Valor,
	        per.perNumeroIdentificacion AS NumeroIdentificacion1,
	        CONCAT(per.perPrimerApellido, 
	            CASE WHEN per.perSegundoApellido IS NOT NULL 
	                THEN CONCAT(' ', per.perSegundoApellido) ELSE '' END) AS Apellido,
	        CONCAT(per.perPrimerNombre,
	            CASE WHEN per.perSegundoNombre IS NOT NULL
	                THEN CONCAT(' ', per.perSegundoNombre) ELSE '' END) AS Nombre,
	        prma.prmValor AS Valor2,
	        CAST(FORMAT(saf.safFechaAceptacion, 'yyyy/MM/dd')AS VARCHAR(20)) AS safFechaAceptacion,
	        CASE WHEN (pof.pofValorAsignadoSFV % 1 ) > 0 
	            THEN CAST(pof.pofValorAsignadoSFV AS VARCHAR)
	            ELSE CAST(CAST(pof.pofValorAsignadoSFV AS BIGINT) AS VARCHAR) 
	        END AS ValorAsignadoSFV,
	        '6' AS h,
	        (SELECT TOP 1 aaf.aafNumeroResolucion
	            FROM ActaAsignacionFovis aaf
	            WHERE aaf.aafSolicitudAsignacion = saf.safId
	            ORDER BY saf.safFechaAceptacion DESC) AS NumeroResolucion,
	        '' AS vacio,
	        CASE per.perTipoIdentificacion 
	            WHEN 'CEDULA_CIUDADANIA' THEN '1'
	            WHEN 'CEDULA_EXTRANJERIA' THEN '2'
	            WHEN 'TARJETA_IDENTIDAD' THEN '3'
	        END AS TipoIdentificacion,
	        (SELECT sol.solId 
	            FROM SolicitudNovedadFovis snf 
	            JOIN Solicitud sol ON (snf.snfSolicitudGlobal = sol.solId)
	            JOIN SolicitudNovedadPersonaFovis spf ON (snf.snfId = spf.spfSolicitudNovedadFovis)
	            WHERE sol.solTipoTransaccion = 'RENUNCIO_SUBISIDIO_ASIGNADO'
	            AND sol.solResultadoProceso = 'APROBADA'
	            AND snf.snfEstadoSolicitud = 'NOV_FOVIS_CERRADA'
	            AND spf.spfPostulacionFovis = pof.pofId) AS solicitud
	    FROM SolicitudAsignacion saf
	    INNER JOIN PostulacionFOVIS pof ON (saf.safId = pof.pofSolicitudAsignacion)
	    INNER JOIN (
	        SELECT jeh.jehId AS idJefe, jeh.jehEstadoHogar AS estado, afi.afiPersona AS idPersona
	        FROM JefeHogar jeh
	        JOIN Afiliado afi ON (jeh.jehAfiliado = afi.afiId)
	        UNION
	        SELECT inh.inhJefeHogar, inh.inhEstadoHogar, inh.inhPersona
	        FROM IntegranteHogar inh) AS integrante ON (pof.pofJefeHogar = integrante.idJefe)
	    INNER JOIN Persona per ON (integrante.idPersona = per.perId)
	    LEFT JOIN PersonaDetalle ped ON (per.perId = ped.pedPersona)
	    INNER JOIN Parametro prm ON (prm.prmNombre = 'NUMERO_ID_CCF')
	    INNER JOIN Parametro prma ON (prma.prmNombre = 'NOMBRE_CCF')
	    WHERE pof.pofResultadoAsignacion = 'ESTADO_ASIGNADO'
	    AND integrante.estado = 'ACTIVO'
	    AND DATEDIFF(YEAR, ped.pedFechaNacimiento, dbo.GetLocalDate()) >= 18 
	    AND saf.safFechaAceptacion BETWEEN @fechaInicio AND @fechaFin) AS datos
		WHERE datos.solicitud IS NULL
		AND datos.TipoIdentificacion IS NOT NULL
	END	
END TRY
BEGIN CATCH
	THROW;
END CATCH
;
