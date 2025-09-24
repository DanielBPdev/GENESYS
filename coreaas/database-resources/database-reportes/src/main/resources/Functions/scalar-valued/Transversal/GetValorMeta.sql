--liquibase formatted sql

--changeset arocha:01 runOnChange:true
--Creación del Function [dbo].[GetValorMeta]


IF (OBJECT_ID('GetValorMeta') IS NOT NULL)
	DROP FUNCTION [dbo].[GetValorMeta]
GO

CREATE FUNCTION [dbo].[GetValorMeta]
(
	@sMeta VARCHAR(44), --PORCENTAJE_AFILIACIONES_EMPLEADORES, PORCENTAJE_RECHAZOS_AFILIACIONES_EMPLEADORES
	@sFrecuencia VARCHAR(10), --MENSUAL, BIMENSUAL, TRIMESTRAL, SEMESTRAL, ANUAL
	@iAnio SMALLINT,
	@iCodPeriodo TINYINT,
	@iDimCanal TINYINT
)
RETURNS INT
AS
BEGIN
	DECLARE @iValor INT	 = 0

	IF @sMeta = 'PORCENTAJE_AFILIACIONES_EMPLEADORES'
	BEGIN

		IF @sFrecuencia = 'MENSUAL'
		BEGIN
			SELECT TOP 1 @iValor = dmc.dmcMetaAfiEmpMes 
			FROM dbo.DimMetasCanalPeriodo dmc
			INNER JOIN dbo.DimPeriodo dip ON dmc.dmcDimPeriodo = dip.dipId
			WHERE dip.dipMes = @iCodPeriodo
			AND dip.dipAnio = @iAnio
			AND dmc.dmcDimCanal = @iDimCanal
		END
		IF @sFrecuencia = 'BIMENSUAL'
		BEGIN
			SELECT TOP 1 @iValor = dmc.dmcMetaAfiEmpBimensual 
			FROM dbo.DimMetasCanalPeriodo dmc
			INNER JOIN dbo.DimPeriodo dip ON dmc.dmcDimPeriodo = dip.dipId
			WHERE dip.dipBimestre = @iCodPeriodo
			AND dip.dipAnio = @iAnio
			AND dmc.dmcDimCanal = @iDimCanal
		END
		IF @sFrecuencia = 'TRIMESTRAL'
		BEGIN
			SELECT TOP 1 @iValor = dmc.dmcMetaAfiEmpTrimestral 
			FROM dbo.DimMetasCanalPeriodo dmc
			INNER JOIN dbo.DimPeriodo dip ON dmc.dmcDimPeriodo = dip.dipId
			WHERE dip.dipTrimestre = @iCodPeriodo
			AND dip.dipAnio = @iAnio
			AND dmc.dmcDimCanal = @iDimCanal
		END
		IF @sFrecuencia = 'SEMESTRAL'
		BEGIN
			SELECT TOP 1 @iValor = dmc.dmcMetaAfiEmpSemestral
			FROM dbo.DimMetasCanalPeriodo dmc
			INNER JOIN dbo.DimPeriodo dip ON dmc.dmcDimPeriodo = dip.dipId
			WHERE dip.dipSemestre = @iCodPeriodo
			AND dip.dipAnio = @iAnio
			AND dmc.dmcDimCanal = @iDimCanal
		END
		IF @sFrecuencia = 'ANUAL'
		BEGIN
			SELECT TOP 1 @iValor = dmc.dmcMetaAfiEmpAnual
			FROM dbo.DimMetasCanalPeriodo dmc
			INNER JOIN dbo.DimPeriodo dip ON dmc.dmcDimPeriodo = dip.dipId
			WHERE dip.dipAnio = @iAnio
			AND dmc.dmcDimCanal = @iDimCanal
		END
		
	END
	IF @sMeta = 'PORCENTAJE_RECHAZOS_AFILIACIONES_EMPLEADORES'
	BEGIN

		IF @sFrecuencia = 'MENSUAL'
		BEGIN
			SELECT TOP 1 @iValor = dmc.dmcMetaRechEmpMes
			FROM dbo.DimMetasCanalPeriodo dmc
			INNER JOIN dbo.DimPeriodo dip ON dmc.dmcDimPeriodo = dip.dipId
			WHERE dip.dipMes = @iCodPeriodo
			AND dip.dipAnio = @iAnio
			AND dmc.dmcDimCanal = @iDimCanal
		END
		IF @sFrecuencia = 'BIMENSUAL'
		BEGIN
			SELECT TOP 1 @iValor = dmc.dmcMetaRechEmpBimensual 
			FROM dbo.DimMetasCanalPeriodo dmc
			INNER JOIN dbo.DimPeriodo dip ON dmc.dmcDimPeriodo = dip.dipId
			WHERE dip.dipBimestre = @iCodPeriodo
			AND dip.dipAnio = @iAnio
			AND dmc.dmcDimCanal = @iDimCanal
		END
		IF @sFrecuencia = 'TRIMESTRAL'
		BEGIN
			SELECT TOP 1 @iValor = dmc.dmcMetaRechEmpTrimestral 
			FROM dbo.DimMetasCanalPeriodo dmc
			INNER JOIN dbo.DimPeriodo dip ON dmc.dmcDimPeriodo = dip.dipId
			WHERE dip.dipTrimestre = @iCodPeriodo
			AND dip.dipAnio = @iAnio
			AND dmc.dmcDimCanal = @iDimCanal
		END
		IF @sFrecuencia = 'SEMESTRAL'
		BEGIN
			SELECT TOP 1 @iValor = dmc.dmcMetaRechEmpSemestral
			FROM dbo.DimMetasCanalPeriodo dmc
			INNER JOIN dbo.DimPeriodo dip ON dmc.dmcDimPeriodo = dip.dipId
			WHERE dip.dipSemestre = @iCodPeriodo
			AND dip.dipAnio = @iAnio
			AND dmc.dmcDimCanal = @iDimCanal
		END
		IF @sFrecuencia = 'ANUAL'
		BEGIN
			SELECT TOP 1 @iValor = dmc.dmcMetaRechEmpAnual
			FROM dbo.DimMetasCanalPeriodo dmc
			INNER JOIN dbo.DimPeriodo dip ON dmc.dmcDimPeriodo = dip.dipId
			WHERE dip.dipAnio = @iAnio
			AND dmc.dmcDimCanal = @iDimCanal
		END

	END

	RETURN @iValor
END