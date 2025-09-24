--liquibase formatted sql

--changeset arocha:01 runOnChange:true
--Creación del Function [dbo].[GetCategoria]


IF (OBJECT_ID('GetCategoria') IS NOT NULL)
	DROP FUNCTION [dbo].[GetCategoria]
GO

CREATE FUNCTION [dbo].[GetCategoria]
(
	@sTipoAfiliado VARCHAR(30),
	@sClasificacion VARCHAR(48),
	@iSalario NUMERIC(19,5),
	@sEstadoAfiliacion VARCHAR(8),
	@dfechaFinServicioSinAfiliacion DATE,
	@iSMMLV NUMERIC(19,5)
)
RETURNS VARCHAR(50)
AS
BEGIN


	DECLARE @sCategoria VARCHAR(50)
	--DECLARE @iSMMLV NUMERIC(19,5)
	DECLARE @iSMMLVDesde INT
	DECLARE @iSMMLVHasta INT

	IF @sTipoAfiliado IN ('TRABAJADOR_INDEPENDIENTE', 'PENSIONADO') AND @sClasificacion IS NULL
	BEGIN
		RETURN 'SIN_CATEGORIA'
	END
	IF @sTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
	BEGIN
		SET @sClasificacion = NULL
	END
	IF @iSalario IS NULL OR @sEstadoAfiliacion = 'INACTIVO'
	BEGIN
		RETURN 'SIN_CATEGORIA'
	END
	
	IF @dfechaFinServicioSinAfiliacion IS NOT NULL
	BEGIN
		IF @dfechaFinServicioSinAfiliacion < dbo.GetLocalDate()
			RETURN 'SIN_CATEGORIA'
	END
	
	/*SELECT @iSMMLV = prmValor
	FROM Parametro
	WHERE prmNombre = 'SMMLV'
	*/

	SELECT @sCategoria = pctCategoria
	FROM (
		SELECT pctCategoria, @iSMMLV*ISNULL(pctSMMLVDesde,0) AS pctSMMLVDesde,CASE WHEN pctSMMLVHasta IS NULL THEN @iSalario ELSE (@iSMMLV * pctSMMLVHasta) END AS pctSMMLVHasta
		FROM ParametrizacionCategoria
		WHERE pctTipoAfiliado = @sTipoAfiliado
		AND ISNULL(pctClasificacion,'') = ISNULL(@sClasificacion,'')
	) AS T
	WHERE @iSalario BETWEEN pctSMMLVDesde + 1 AND pctSMMLVHasta


	RETURN ISNULL(@sCategoria,'SIN_CATEGORIA')

END
;