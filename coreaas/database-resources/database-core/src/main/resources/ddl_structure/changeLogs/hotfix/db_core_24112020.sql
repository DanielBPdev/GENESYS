--liquibase formatted sql

--changeset jocampo:01
--comment: 

INSERT INTO [dbo].[Parametro] (
      [prmNombre]
      ,[prmValor]
      ,[prmCargaInicio]
      ,[prmSubCategoriaParametro]
      ,[prmDescripcion]
      ,[prmTipoDato])
VALUES (
    'PILA_DIAS_REINTENTO_PROCESAMIENTO'
    ,3
    ,0
    ,'VALOR_GLOBAL_TECNICO'
    ,'Numero de dias a restar a la fecha del sistema para filtar las planillas detenidas a reprocesar'
    ,'NUMBER'
    )