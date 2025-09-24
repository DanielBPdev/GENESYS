--liquibase formatted sql

--changeset  atoro:01
--comment: Actualización de la tabla calidacionProceso
update validacionProceso set vapObjetoValidacion='TRABAJADOR_DEPENDIENTE' where vapbloque='121-104-1' and vapValidacion='VALIDACION_TIEMPO_REINTEGRO_AFILIADO';
update validacionProceso set vapObjetoValidacion='TRABAJADOR_INDEPENDIENTE' where vapbloque='121-104-3' and vapValidacion='VALIDACION_TIEMPO_REINTEGRO_AFILIADO';
update validacionProceso set vapObjetoValidacion='PENSIONADO' where vapbloque='121-104-4' and vapValidacion='VALIDACION_TIEMPO_REINTEGRO_AFILIADO';