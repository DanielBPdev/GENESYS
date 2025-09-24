--liquibase formatted sql

--changeset dsuesca:01 runOnChange:true
--Funcion que devuelve la anterior fecha dado el numero del dia, el dia 1 es el domingo el dia 7 es el sabado


IF (OBJECT_ID('GetAnteriorFecha') IS NOT NULL)
	DROP FUNCTION [dbo].[GetAnteriorFecha]
GO

CREATE FUNCTION GetAnteriorFecha
(
	@dFecha DATE,
	@iAnteriorDia SMALLINT
)
RETURNS DATE
AS
BEGIN
	DECLARE @anteriorFecha DATE
	SET @dFecha = DATEADD(DAY,-8,@dFecha)
	SELECT @anteriorFecha = DATEADD(DAY, (DATEDIFF(DAY, ((@iAnteriorDia + 5) % 7), @dFecha) / 7) * 7 + 7, ((@iAnteriorDia + 5) % 7))
	RETURN @anteriorFecha
END
;
