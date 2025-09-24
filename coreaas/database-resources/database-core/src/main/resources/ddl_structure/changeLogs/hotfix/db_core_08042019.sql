--liquibase formatted sql

--changeset jocampo:01
--comment: se eliminan los constraint para las tabla DetalleSolicitudGestionCobro
ALTER TABLE DetalleSolicitudGestionCobro DROP CONSTRAINT PK_DetalleSolicitudGestionCobro_dsgId

--changeset jocampo:02
--comment: Se agrega una columna temporal para la copia de los valores actuales de la llave a la que se le quitará el IDENTITY
ALTER TABLE DetalleSolicitudGestionCobro ADD dsgId_new BIGINT

--changeset jocampo:03
--comment: Se copian los valores de las llaves a la columna temporal
UPDATE DetalleSolicitudGestionCobro SET dsgId_new = dsgId

--changeset jocampo:04
--comment: Se elimina la columna de llave con IDENTITY
ALTER TABLE DetalleSolicitudGestionCobro DROP COLUMN dsgId

--changeset jocampo:05
--comment: Se renombra la columna
EXEC sp_rename 'dbo.DetalleSolicitudGestionCobro.dsgId_new', 'dsgId_new', 'COLUMN';

--changeset jocampo:06
--comment: Se renombra la columna
EXEC sp_rename 'dbo.DetalleSolicitudGestionCobro.dsgId_new', 'dsgId', 'COLUMN';

--changeset jocampo:07
--comment: Se agrega la restricción de NOT NULL a los campos de llave
ALTER TABLE DetalleSolicitudGestionCobro ALTER COLUMN dsgId BIGINT NOT NULL

--changeset jocampo:08
--comment: Se creas de nuevo los constraint
ALTER TABLE DetalleSolicitudGestionCobro ADD CONSTRAINT PK_DetalleSolicitudGestionCobro_dsgId PRIMARY KEY (dsgId)

--changeset jocampo:09
--comment: Se crea la secuencia para el manejo de los valores de PK y de numero de operación
DECLARE @inicioSeq BIGINT
SELECT @inicioSeq = max(dsgId) + 1 FROM DetalleSolicitudGestionCobro
SELECT @inicioSeq = ISNULL(@inicioSeq, 1)
EXEC('CREATE SEQUENCE Sec_DetalleSolicitudGestionCobro AS BIGINT START WITH ' + @inicioSeq + ' INCREMENT BY 1')

--changeset dsuesca:10
--comment: 
UPDATE ValidacionProceso
SET vapValidacion = 'VALIDACION_EXISTE_REGISTRO_SUBSANACION_AFILIADO_DEPENDIENTE'
WHERE vapBloque = 'NOVEDAD_REGISTRO_SUBSANACION_EXPULSION_DEPENDIENTE'
AND vapValidacion = 'VALIDACION_EXISTE_REGISTRO_SUBSANACION_AFILIADO';

UPDATE ValidacionProceso
SET vapValidacion = 'VALIDACION_EXISTE_REGISTRO_SUBSANACION_AFILIADO_INDEPENDIENTE'
WHERE vapBloque = 'NOVEDAD_REGISTRO_SUBSANACION_EXPULSION_INDEPENDIENTE'
AND vapValidacion = 'VALIDACION_EXISTE_REGISTRO_SUBSANACION_AFILIADO';

UPDATE ValidacionProceso
SET vapValidacion = 'VALIDACION_EXISTE_REGISTRO_SUBSANACION_AFILIADO_PENSIONADO'
WHERE vapBloque IN ('NOVEDAD_REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_25ANIOS',
'NOVEDAD_REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MAYOR_1_5SM_0_6',
'NOVEDAD_REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MAYOR_1_5SM_2',
'NOVEDAD_REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MENOR_1_5SM_0',
'NOVEDAD_REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MENOR_1_5SM_0_6',
'NOVEDAD_REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MENOR_1_5SM_2',
'NOVEDAD_REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_PENSION_FAMILIAR')
AND vapValidacion = 'VALIDACION_EXISTE_REGISTRO_SUBSANACION_AFILIADO';
