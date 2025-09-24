--liquibase formatted sql

--changeset ecastano:01 runOnChange:true runAlways:true
--Creación del Function [dbo].[ufn_CantidadConLetra]

/****** Object:  UserDefinedFunction [dbo].[ufn_CantidadConLetra]   ******/
IF (OBJECT_ID('ufn_CantidadConLetra') IS NOT NULL)
	DROP FUNCTION [dbo].[ufn_CantidadConLetra]
GO

-- =============================================
-- Author: Eduward Leonardo Castaño Ramirez
-- Create date: 2017/12/05
-- Description:	Función que permite convertir numeros a letras
-- =============================================

--- Object:  UserDefinedFunction [dbo].[Split] 
CREATE FUNCTION dbo.ufn_CantidadConLetra
(
	@Numero Decimal(19,5)
)
RETURNS Varchar(180)
AS
BEGIN

DECLARE @lnEntero BIGINT,
		@lcRetorno VARCHAR(512),
		@lnTerna BIGINT,
		@lcMiles VARCHAR(512),
		@lcCadena VARCHAR(512),
		@lnUnidades BIGINT,
		@lnDecenas BIGINT,
		@lnCentenas BIGINT,
		@lnFraccion BIGINT

SELECT 	@lnEntero = CAST(@Numero AS BIGINT),
		@lnFraccion = (@Numero - @lnEntero) * 100,
		@lcRetorno = '',
		@lnTerna = 1

WHILE @lnEntero > 0
BEGIN /* WHILE */

--Recorro columna por columna
SELECT @lcCadena = ''
SELECT @lnUnidades = @lnEntero % 10
SELECT @lnEntero = CAST(@lnEntero/10 AS BIGINT)
SELECT @lnDecenas = @lnEntero % 10
SELECT @lnEntero = CAST(@lnEntero/10 AS BIGINT)
SELECT @lnCentenas = @lnEntero % 10
SELECT @lnEntero = CAST(@lnEntero/10 AS BIGINT)

--prBIGINT cast(@lnCentenas as varchar(3)) + cast(@lnDecenas as varchar(3))+ cast(@lnUnidades as varchar(3))
--prBIGINT @lnEntero

-- Analizo las unidades
SELECT @lcCadena =
CASE /* UNIDADES */
	WHEN @lnUnidades = 1 AND @lnTerna = 1 THEN 'UNO ' + @lcCadena
	WHEN @lnUnidades = 1 AND @lnTerna <> 1 THEN 'UN ' + @lcCadena
	WHEN @lnUnidades = 2 THEN 'DOS ' + @lcCadena
	WHEN @lnUnidades = 3 THEN 'TRES ' + @lcCadena
	WHEN @lnUnidades = 4 THEN 'CUATRO ' + @lcCadena
	WHEN @lnUnidades = 5 THEN 'CINCO ' + @lcCadena
	WHEN @lnUnidades = 6 THEN 'SEIS ' + @lcCadena
	WHEN @lnUnidades = 7 THEN 'SIETE ' + @lcCadena
	WHEN @lnUnidades = 8 THEN 'OCHO ' + @lcCadena
	WHEN @lnUnidades = 9 THEN 'NUEVE ' + @lcCadena
ELSE @lcCadena
END /* UNIDADES */

--prBIGINT @lcCadena

-- Analizo las decenas
SELECT @lcCadena =
CASE /* DECENAS */
	WHEN @lnDecenas = 1 THEN
	CASE @lnUnidades
		WHEN 0 THEN 'DIEZ '
		WHEN 1 THEN 'ONCE '
		WHEN 2 THEN 'DOCE '
		WHEN 3 THEN 'TRECE '
		WHEN 4 THEN 'CATORCE '
		WHEN 5 THEN 'QUINCE '
	ELSE 'DIECI' + @lcCadena
	END
	WHEN @lnDecenas = 2 AND @lnUnidades = 0 THEN 'VEINTE ' + @lcCadena
	WHEN @lnDecenas = 2 AND @lnUnidades <> 0 THEN 'VEINTI' + @lcCadena
	WHEN @lnDecenas = 3 AND @lnUnidades = 0 THEN 'TREINTA ' + @lcCadena
	WHEN @lnDecenas = 3 AND @lnUnidades <> 0 THEN 'TREINTA Y ' + @lcCadena
	WHEN @lnDecenas = 4 AND @lnUnidades = 0 THEN 'CUARENTA ' + @lcCadena
	WHEN @lnDecenas = 4 AND @lnUnidades <> 0 THEN 'CUARENTA Y ' + @lcCadena
	WHEN @lnDecenas = 5 AND @lnUnidades = 0 THEN 'CINCUENTA ' + @lcCadena
	WHEN @lnDecenas = 5 AND @lnUnidades <> 0 THEN 'CINCUENTA Y ' + @lcCadena
	WHEN @lnDecenas = 6 AND @lnUnidades = 0 THEN 'SESENTA ' + @lcCadena
	WHEN @lnDecenas = 6 AND @lnUnidades <> 0 THEN 'SESENTA Y ' + @lcCadena
	WHEN @lnDecenas = 7 AND @lnUnidades = 0 THEN 'SETENTA ' + @lcCadena
	WHEN @lnDecenas = 7 AND @lnUnidades <> 0 THEN 'SETENTA Y ' + @lcCadena
	WHEN @lnDecenas = 8 AND @lnUnidades = 0 THEN 'OCHENTA ' + @lcCadena
	WHEN @lnDecenas = 8 AND @lnUnidades <> 0 THEN 'OCHENTA Y ' + @lcCadena
	WHEN @lnDecenas = 9 AND @lnUnidades = 0 THEN 'NOVENTA ' + @lcCadena
	WHEN @lnDecenas = 9 AND @lnUnidades <> 0 THEN 'NOVENTA Y ' + @lcCadena
ELSE @lcCadena
END /* DECENAS */

--prBIGINT @lcCadena

-- Analizo las centenas
SELECT @lcCadena =
CASE /* CENTENAS */
	WHEN @lnCentenas = 1 AND @lnUnidades = 0 AND @lnDecenas = 0 THEN 'CIEN ' + @lcCadena
	WHEN @lnCentenas = 1 AND NOT(@lnUnidades = 0 AND @lnDecenas = 0) THEN 'CIENTO ' + @lcCadena
	WHEN @lnCentenas = 2 THEN 'DOSCIENTOS ' + @lcCadena
	WHEN @lnCentenas = 3 THEN 'TRESCIENTOS ' + @lcCadena
	WHEN @lnCentenas = 4 THEN 'CUATROCIENTOS ' + @lcCadena
	WHEN @lnCentenas = 5 THEN 'QUINIENTOS ' + @lcCadena
	WHEN @lnCentenas = 6 THEN 'SEISCIENTOS ' + @lcCadena
	WHEN @lnCentenas = 7 THEN 'SETECIENTOS ' + @lcCadena
	WHEN @lnCentenas = 8 THEN 'OCHOCIENTOS ' + @lcCadena
	WHEN @lnCentenas = 9 THEN 'NOVECIENTOS ' + @lcCadena
ELSE @lcCadena
END /* CENTENAS */
--prBIGINT @lcCadena

-- Analizo los millares
SELECT @lcCadena =
CASE /* TERNA */
	WHEN @lnTerna = 1 THEN @lcCadena
	WHEN @lnTerna = 2 AND (@lnUnidades + @lnDecenas + @lnCentenas <> 0) THEN @lcCadena + ' MIL '
	WHEN @lnTerna = 3 AND (@lnUnidades + @lnDecenas + @lnCentenas <> 0) AND
	@lnUnidades = 1 AND @lnDecenas = 0 AND @lnCentenas = 0 THEN @lcCadena + ' MILLON '
	WHEN @lnTerna = 3 AND (@lnUnidades + @lnDecenas + @lnCentenas <> 0) AND
	NOT (@lnUnidades = 1 AND @lnDecenas = 0 AND @lnCentenas = 0) THEN @lcCadena + ' MILLONES '
	WHEN @lnTerna = 4 AND (@lnUnidades + @lnDecenas + @lnCentenas <> 0) THEN @lcCadena + ' MIL MILLONES'
ELSE ''
END /* MILLARES */
--prBIGINT @lcCadena

-- Armo el retorno columna a columna
--prBIGINT @lcCadena
SELECT @lcRetorno = @lcCadena + @lcRetorno
SELECT @lnTerna = @lnTerna + 1

END /* WHILE */

IF @lnTerna = 1
SELECT @lcRetorno = 'CERO'

RETURN RTRIM(@lcRetorno)
END