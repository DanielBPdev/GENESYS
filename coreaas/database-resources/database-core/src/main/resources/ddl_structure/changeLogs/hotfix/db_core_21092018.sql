--liquibase formatted sql

--changeset dsuesca:01
--comment: Adición de parámetro de tiempo de reintegro para grupo familiar
INSERT INTO dbo.Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion)
VALUES ('TIEMPO_REINTEGRO_GF','31536000000',0,'EJECUCION_TIMER','TIEMPO QUE TIENE UN GRUPO FAMILIAR DE UN TRABAJADOR PARA VOLVERSE A ACTIVAR SIN REALIZAR UN NUEVO TRAMITE')

--changeset rarboleda:02
--comment: Adición de validación de empleador de destino activo
INSERT INTO dbo.ValidacionProceso
(vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
VALUES('122-369-1', 'VALIDACION_EMPLEADOR_DESTINO_ACTIVO', 'AFILIACION_PERSONAS_PRESENCIAL', 'ACTIVO', 1, 'TRABAJADOR_DEPENDIENTE', 0);