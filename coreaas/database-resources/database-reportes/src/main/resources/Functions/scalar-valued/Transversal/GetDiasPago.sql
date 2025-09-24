--liquibase formatted sql

--changeset arocha:01 runOnChange:true
--Creación del Function [dbo].[GetDiasPago]


IF (OBJECT_ID('GetDiasPago') IS NOT NULL)
	DROP FUNCTION [dbo].[GetDiasPago]
GO

CREATE FUNCTION GetDiasPago
(
	@sTipoAportante VARCHAR(20),
	@iTipoCotizantePILA INT,
	@sNumeroDocumento VARCHAR(16),
	@dFechaPago DATETIME,
	@dPeriodo DATETIME,
	@iNumeroTotalTrabajadores INT
)
RETURNS INT
AS
BEGIN
	DECLARE @iDiasPago INT
	DECLARE @iDosUltimosDigitos INT
	DECLARE @iPlazoDiasHabiles INT

	IF @sTipoAportante = 'EMPLEADOR'
	BEGIN
		SET @sTipoAportante = CASE WHEN @iNumeroTotalTrabajadores < 200 THEN 'EMPRESA_MENOR_200' ELSE 'EMPRESA_MAYOR_200' END
		SET @dPeriodo = DATEADD(mm,1,@dPeriodo)
	END
	
	SET @iDosUltimosDigitos = CAST(SUBSTRING(@sNumeroDocumento,len(@sNumeroDocumento)-1,2) AS INT)
	
	SELECT @iPlazoDiasHabiles = DiasHabiles
	FROM 
	(
		VALUES 
		('INDEPENDIENTE', 0, 7 ,1),
		('INDEPENDIENTE',08,14 ,2),
		('INDEPENDIENTE',15,21 ,3),
		('INDEPENDIENTE',22,28 ,4),
		('INDEPENDIENTE',29,35 ,5),
		('INDEPENDIENTE',36,42 ,6),
		('INDEPENDIENTE',43,49 ,7),
		('INDEPENDIENTE',50,56 ,8),
		('INDEPENDIENTE',57,63 ,9),
		('INDEPENDIENTE',64,69 ,10),
		('INDEPENDIENTE',70,75 ,11),
		('INDEPENDIENTE',76,81 ,12),
		('INDEPENDIENTE',82,87 ,13),
		('INDEPENDIENTE',88,93 ,14),
		('INDEPENDIENTE',94,99 ,15),
		('EMPRESA_MAYOR_200', 0,10,1),
		('EMPRESA_MAYOR_200',11,23,2),
		('EMPRESA_MAYOR_200',24,36,3),
		('EMPRESA_MAYOR_200',37,49,4),
		('EMPRESA_MAYOR_200',50,62,5),
		('EMPRESA_MAYOR_200',63,75,6),
		('EMPRESA_MAYOR_200',76,88,7),
		('EMPRESA_MAYOR_200',89,99,8),
		('EMPRESA_MENOR_200', 0, 8,1),
		('EMPRESA_MENOR_200', 9,16,2),
		('EMPRESA_MENOR_200',17,24,3),
		('EMPRESA_MENOR_200',25,32,4),
		('EMPRESA_MENOR_200',33,40,5),
		('EMPRESA_MENOR_200',41,48,6),
		('EMPRESA_MENOR_200',49,56,7),
		('EMPRESA_MENOR_200',57,64,8),
		('EMPRESA_MENOR_200',65,74,9),
		('EMPRESA_MENOR_200',73,79,10),
		('EMPRESA_MENOR_200',80,86,11),
		('EMPRESA_MENOR_200',87,93,12),
		('EMPRESA_MENOR_200',94,99,13)
	) as t(Tipo,RangoMin,RangoMax,DiasHabiles)
	WHERE Tipo = @sTipoAportante AND @iDosUltimosDigitos BETWEEN RangoMin AND RangoMax

	RETURN DATEDIFF(dd,dbo.GetFechaDeber(@dPeriodo,@iPlazoDiasHabiles),@dFechaPago)

END;
