--liquibase formatted sql

--changeset dsuesca:01 runOnChange:true
--Funcion que devuelve la proxima fecha dado el numero del dia, el dia 1 es el domingo el dia 7 es el sabado


IF (OBJECT_ID('GetProximaFecha') IS NOT NULL)
	DROP FUNCTION [dbo].[GetProximaFecha]
GO

CREATE FUNCTION GetProximaFecha
(
	@dFecha DATE,
	@iProximoDia SMALLINT
)
RETURNS DATE
AS
BEGIN
	DECLARE @proximaFecha DATE
	SELECT @proximaFecha = DATEADD(DAY, (DATEDIFF(DAY, ((@iProximoDia + 5) % 7), @dFecha) / 7) * 7 + 7, ((@iProximoDia + 5) % 7))
	RETURN @proximaFecha
END
;
