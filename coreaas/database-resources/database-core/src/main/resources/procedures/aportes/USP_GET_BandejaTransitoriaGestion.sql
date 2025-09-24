-- =============================================
-- Author:		Diego Suesca
-- Create date: 2021/04/21
-- Description: 
-- =============================================

CREATE PROCEDURE USP_GET_BandejaTransitoriaGestion
	@tipoIdentificacion varchar(20) = null,
	@numeroIdentificacion varchar(16) = null,
	@numeroPlanilla varchar(10) = null,
	@fechaInicio bigint = null, 
	@fechaFin bigint = null 
AS
begin
	set nocount on;

	IF @tipoIdentificacion = '-1'
		SET @tipoIdentificacion = NULL;
	IF @numeroIdentificacion = '-1'
		SET @numeroIdentificacion = NULL;
	IF @numeroPlanilla = '-1'
		SET @numeroPlanilla = NULL;
	IF @fechaFin = -1
		SET @fechaFin = NULL;
	IF @fechaInicio = -1
		SET @fechaInicio = NULL;


	declare @dFechaIni DATE = dateadd(second, @fechaInicio / 1000, '19700101') AT TIME ZONE 'UTC' AT TIME ZONE 'SA Pacific Standard Time';
	declare @dFechaFin DATE = dateadd(second, @fechaFin / 1000, '19700101') AT TIME ZONE 'UTC' AT TIME ZONE 'SA Pacific Standard Time';

	declare @sql nvarchar(500);
	
	CREATE TABLE #idsPip (pipId bigint, s1 varchar(200))

	DECLARE @filtroPila bit = 0;

	SET @sql = ' SELECT reg.regRegistroControl 
    FROM staging.RegistroGeneral reg
    WHERE 1=1 '

    IF @tipoIdentificacion is not NULL AND @numeroPlanilla IS NOT NULL
    BEGIN    	
    	SET @filtroPila = 1;
    	SET @sql = @sql + N' AND reg.regTipoIdentificacionAportante = @tipoIdentificacion
    					AND reg.regNumeroIdentificacionAportante = @numeroIdentificacion
    					AND reg.regNumPlanilla = @numeroPlanilla ';

    	INSERT #idsPip (pipId, s1)
    	EXEC sp_execute_remote PilaReferenceData,@sql,
    	N'@tipoIdentificacion varchar(20), @numeroIdentificacion varchar(16), @numeroPlanilla varchar(10)',
    	@tipoIdentificacion = @tipoIdentificacion, @numeroIdentificacion = @numeroIdentificacion, @numeroPlanilla = @numeroPlanilla

    END ELSE IF @numeroPlanilla IS NOT NULL
	BEGIN
		SET @filtroPila = 1;
		SET @sql = @sql + N' AND reg.regNumPlanilla = @numeroPlanilla ';

		INSERT #idsPip (pipId, s1)
    	EXEC sp_execute_remote PilaReferenceData,@sql,
    	N'@numeroPlanilla varchar(10)',
    	@numeroPlanilla = @numeroPlanilla

	END ELSE IF @tipoIdentificacion IS NOT NULL 
	BEGIN
		SET @filtroPila = 1;
		SET @sql = @sql + N' AND reg.regTipoIdentificacionAportante = @tipoIdentificacion
    					AND reg.regNumeroIdentificacionAportante = @numeroIdentificacion ';

		INSERT #idsPip (pipId, s1)
    	EXEC sp_execute_remote PilaReferenceData,@sql,
    	N'@tipoIdentificacion varchar(20), @numeroIdentificacion varchar(16)',
    	@tipoIdentificacion = @tipoIdentificacion, @numeroIdentificacion = @numeroIdentificacion
	END
      
    IF @filtroPila = 0
	BEGIN
	    SELECT 
			petPilaIndicePlanilla,
			petAccion,
			petEstado,
			CONVERT(varchar,pedFecha,20) pedFecha,
			CONVERT(varchar,pedFechaReanudado,20) pedFechaReanudado
		FROM PilaEstadoTransitorio pet 
		WHERE pet.petEstado = 'FALLIDO'
		  AND (@dFechaIni IS NULL OR pet.pedFecha >= @dFechaIni)
		  AND (@dFechaFin IS NULL OR pet.pedFecha <= DATEADD(day, 1, @dFechaFin))
	END
	ELSE
	BEGIN
		SELECT 
			petPilaIndicePlanilla,
			petAccion,
			petEstado,
			CONVERT(varchar,pedFecha,20) pedFecha,
			CONVERT(varchar,pedFechaReanudado,20) pedFechaReanudado
		FROM PilaEstadoTransitorio pet 
		JOIN #idsPip p ON p.pipId = pet.petPilaIndicePlanilla
		WHERE pet.petEstado = 'FALLIDO'
		  AND (@dFechaIni IS NULL OR pet.pedFecha >= @dFechaIni)
		  AND (@dFechaFin IS NULL OR pet.pedFecha <= DATEADD(day, 1, @dFechaFin))
	END
end

