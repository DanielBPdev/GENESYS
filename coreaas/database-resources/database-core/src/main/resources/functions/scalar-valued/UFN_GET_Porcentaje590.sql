/****** Object:  UserDefinedFunction [dbo].[UFN_GET_Porcentaje590]    Script Date: 12/05/2023 11:06:33 a. m. ******/

CREATE OR ALTER FUNCTION [dbo].[UFN_GET_Porcentaje590]
(
    @dPeriodo DATE,				
    @dFechaBeneficio DATE 	
    
)
RETURNS NUMERIC(6,5)
AS
BEGIN
    DECLARE @TablaBeneficio AS TABLE (pbePeriodo SMALLINT, pbePorcentaje NUMERIC(5,5))
    DECLARE @Porcentaje NUMERIC(6,5)
    
    IF @dFechaBeneficio IS NOT NULL
    BEGIN
 
       
        BEGIN
            INSERT INTO @TablaBeneficio
            VALUES (1,0.25),(2,0.50),(3,0.75),(4,0),(5,0),(6,0),(7,0),(8,0)
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