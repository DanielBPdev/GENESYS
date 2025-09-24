--liquibase formatted sql

--changeset clmarin:01 runOnChange:true runAlways:true
--Creación del Function [dbo].[UFN_GET_Porcentaje1429] 

/****** Object:  UserDefinedFunction [dbo].[UFN_GET_Porcentaje1429]   ******/

IF (OBJECT_ID('UFN_GET_Porcentaje1429') IS NOT NULL)
	DROP FUNCTION [dbo].[UFN_GET_Porcentaje1429]
GO
-- =============================================
-- Author: Claudia Milena Marín Hincapié
-- Create date: 2019/11/12 
-- Description: Obtiene el porcentaje 1429 según un año de beneficio
-- =============================================
CREATE FUNCTION UFN_GET_Porcentaje1429
(
    @dPeriodo DATE,				--Periodo actual 
    @dFechaBeneficio DATE,		--Fecha de vinculación para el beneficio del empleador
    @bDepartamentoEspecial BIT	--Si el empleador pertenece al departamento de Amazonas, Guainía y Vaupés
)
RETURNS NUMERIC(6,5)
AS
BEGIN
    DECLARE @TablaBeneficio AS TABLE (pbePeriodo SMALLINT, pbePorcentaje NUMERIC(5,5))
    DECLARE @Porcentaje NUMERIC(6,5)
    
    IF @dFechaBeneficio IS NOT NULL
    BEGIN
        IF @bDepartamentoEspecial = 0
        BEGIN
            INSERT INTO @TablaBeneficio
            VALUES (1,0),(2,0),(3,0.25),(4,0.5),(5,0.75)
        END
        ELSE
        BEGIN
            INSERT INTO @TablaBeneficio
            VALUES (1,0),(2,0),(3,0),(4,0),(5,0),(6,0),(7,0),(8,0),
                    (9,0.5),(10,0.75)
        END

        DECLARE @iAnioBeneficio SMALLINT
        SET @iAnioBeneficio = (DATEDIFF(YEAR, @dFechaBeneficio, @dPeriodo) +1)

        SELECT @Porcentaje = pbePorcentaje
        FROM @TablaBeneficio
        WHERE pbePeriodo = @iAnioBeneficio

        SET @Porcentaje = ISNULL(@Porcentaje, CAST(1 AS NUMERIC(6, 5))) 
    END
    ELSE
    BEGIN
        SET @Porcentaje = CAST(1 AS NUMERIC(6, 5))
    END

    RETURN @Porcentaje

END