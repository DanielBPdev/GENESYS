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
    'PILA_CANTIDAD_ARCHIVO_MASIVO'
    ,100
    ,0
    ,'VALOR_GLOBAL_TECNICO'
    ,'Número de planillas que se cargan en simultaneo, entre mayor es el número mayor el consumo de recursos'
    ,'NUMBER'
    )