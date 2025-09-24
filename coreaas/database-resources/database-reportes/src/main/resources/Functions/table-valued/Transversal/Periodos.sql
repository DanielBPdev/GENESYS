--liquibase formatted sql

--changeset arocha:01 runOnChange:true
--Creación del Function [dbo].[Periodos]


/****** Object:  UserDefinedFunction [dbo].[Periodos]    Script Date: 23/06/2017 11:58:48 a.m. ******/
IF (OBJECT_ID('Periodos') IS NOT NULL)
	DROP FUNCTION [dbo].[Periodos]
GO
-- =============================================
-- Author:		Andrés Julián Rocha Cruz
-- Create date: 2018/05/30
-- Description:	Devuelve una tabla con los periodos y frecuencias usados en reportes
-- =============================================
CREATE FUNCTION [dbo].[Periodos]()
RETURNS @TablaPeriodos TABLE (Periodo VARCHAR(20), Numero TINYINT, Texto VARCHAR(3), TextoLargo VARCHAR(20), Bimestre TINYINT, Trimestre TINYINT, Semestre TINYINT, PeriodoSemestral TINYINT)
AS
BEGIN

	INSERT INTO @TablaPeriodos
	VALUES 
		('MENSUAL',1,'Ene','Enero',1,1,1,1),
		('MENSUAL',2,'Feb','Febrero',1,1,1,1),
		('MENSUAL',3,'Mar','Marzo',2,1,1,1),
		('MENSUAL',4,'Abr','Abril',2,2,1,1),
		('MENSUAL',5,'May','Mayo',3,2,1,1),
		('MENSUAL',6,'Jun','Junio',3,2,1,1),
		('MENSUAL',7,'Jul','Julio',4,3,2,2),
		('MENSUAL',8,'Ago','Agosto',4,3,2,2),
		('MENSUAL',9,'Sep','Septiembre',5,3,2,2),
		('MENSUAL',10,'Oct','Octubre',5,4,2,2),
		('MENSUAL',11,'Nov','Noviembre',6,4,2,2),
		('MENSUAL',12,'Dic','Diciembre',6,4,2,2),
		('BIMENSUAL',1,'I','I',0,0,0,1),
		('BIMENSUAL',2,'II','II',0,0,0,1),
		('BIMENSUAL',3,'III','III',0,0,0,1),
		('BIMENSUAL',4,'IV','IV',0,0,0,2),
		('BIMENSUAL',5,'V','V',0,0,0,2),
		('BIMENSUAL',6,'VI','VI',0,0,0,2),
		('TRIMESTRAL',1,'I','I',0,0,0,1),
		('TRIMESTRAL',2,'II','II',0,0,0,1),
		('TRIMESTRAL',3,'III','III',0,0,0,2),
		('TRIMESTRAL',4,'IV','IV',0,0,0,2),
		('SEMESTRAL',1,'I','I',0,0,0,1),
		('SEMESTRAL',2,'II','II',0,0,0,1),
		('ANUAL',1,NULL,NULL,0,0,0,NULL)

	RETURN
END
GO

