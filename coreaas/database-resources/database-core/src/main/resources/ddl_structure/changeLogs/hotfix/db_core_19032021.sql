--liquibase formatted sql

--changeset arocha:01
--comment: 
INSERT INTO dbo.Constante (cnsNombre,cnsValor,cnsDescripcion,cnsTipoDato) VALUES ('LATENCIA_MINUTOS_REVISION_AUD', 5, 'Cantidad de minutos para obtener la revisión anterior en base a la última revisión registrada en auditoría', 'NUMBER');