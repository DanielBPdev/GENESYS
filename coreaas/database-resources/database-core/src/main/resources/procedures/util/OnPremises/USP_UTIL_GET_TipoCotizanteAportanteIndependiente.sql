-- =============================================
-- Author:		Alfonso Baquero Echeverry
-- Create date: 2018/05/31
-- Description:	Alternativa OnPremises para la consulta del tipo de cotizante 
-- de un aportante independiente en el registro general del área de trabajo de PILA
-- =============================================
CREATE PROCEDURE dbo.USP_UTIL_GET_TipoCotizanteAportanteIndependiente
	@sTipoID VARCHAR(20),
	@sNumID VARCHAR(16),
	@iTipoCot SMALLINT OUTPUT
AS

BEGIN
	print 'Inicia USP_UTIL_GET_TipoCotizanteAportanteIndependiente'
	DECLARE @DBNAME VARCHAR(255)
	SET @DBNAME = dbo.FN_GET_PILA_DBNAME(SUSER_SNAME())

	print @DBNAME

	DECLARE @sql NVARCHAR(4000)
	DECLARE @sPeriodoActual VARCHAR(7) = CONVERT(VARCHAR(7), dbo.GetLocalDate(), 120)

	CREATE TABLE #resultadoLinkedServer(resTipoCot SMALLINT)
	
	SET @sql = '
		SELECT TOP 1 redTipoCotizante 
		FROM ##pila##.staging.RegistroGeneral
		INNER JOIN ##pila##.staging.RegistroDetallado ON redRegistroGeneral = regId
		WHERE redTipoCotizante IN (57)
		AND regTipoIdentificacionAportante = @sTipoID
		AND regNumeroIdentificacionAportante = @sNumID
		AND regPeriodoAporte <= @sPeriodoActual'
	SET @sql = REPLACE(@sql,'@sTipoID',''''''+@sTipoID+'''''')
	SET @sql = REPLACE(@sql,'@sNumID',''''''+@sNumID+'''''')
	SET @sql = REPLACE(@sql,'@sPeriodoActual',''''''+@sPeriodoActual+'''''')
	SET @sql = REPLACE(@sql,'##pila##',@DBNAME)
	SET @sql = N'SELECT * FROM OPENQUERY(LinkedServerCore, ''' + @sql + ''')'

	INSERT INTO #resultadoLinkedServer (resTipoCot)
	EXEC sp_executesql @sql 

	SELECT @iTipoCot = resTipoCot FROM #resultadoLinkedServer

	DROP TABLE #resultadoLinkedServer
	
	print 'Finaliza USP_UTIL_GET_TipoCotizanteAportanteIndependiente'
END;