--liquibase formatted sql

--changeset arocha:01 runOnChange:true
--Creación del Function [dbo].[Split]


/****** Object:  UserDefinedFunction [dbo].[Split]    Script Date: 23/06/2017 11:58:48 a.m. ******/
IF (OBJECT_ID('Split') IS NOT NULL)
	DROP FUNCTION [dbo].[Split]
GO

/****** Object:  UserDefinedFunction [dbo].[Split]    Script Date: 23/06/2017 11:58:48 a.m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Andrés Julián Rocha Cruz
-- Create date: 2017/06/16
-- Description:	Función que permite convertir una cadena de texto separada
-- por un caracter a un resultado tipo tabla de los elementos del mismo
-- =============================================
CREATE FUNCTION [dbo].[Split]
(
    @String VARCHAR(MAX),
    @Delimiter NCHAR(1)
)
RETURNS TABLE 
AS
RETURN 
(
    WITH Split(stpos,endpos) 
    AS(
        SELECT 0 AS stpos, CHARINDEX(@Delimiter,@String) AS endpos
        UNION ALL
	   SELECT CAST(endpos+1 as int), CHARINDEX(@Delimiter,@String,endpos+1)
	   FROM Split
	   WHERE endpos > 0
    )
    SELECT 'Id' = ROW_NUMBER() OVER (ORDER BY (SELECT 1)),
        'Data' = SUBSTRING(@String,stpos,COALESCE(NULLIF(endpos,0),LEN(@String)+1)-stpos)
    FROM Split
)

GO

