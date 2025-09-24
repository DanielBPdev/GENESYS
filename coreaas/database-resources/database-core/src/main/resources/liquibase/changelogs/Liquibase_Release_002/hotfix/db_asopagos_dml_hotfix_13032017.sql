--liquibase formatted sql

--changeset jusanchez:01
--comment: hotfix de las validaciones del proceso 122 Cargue Afiliación múltiple

delete from ValidacionProceso where vapBloque='122-363-1' and vapValidacion='VALIDACION_EXISTENCIA_PERSONA'
and vapProceso='AFILIACION_DEPENDIENTE_WEB' and vapEstadoProceso='ACTIVO' and vapOrden=1 and vapObjetoValidacion='TRABAJADOR_DEPENDIENTE'

delete from ValidacionProceso where vapBloque='122-363-1N' and vapValidacion='VALIDACION_EXISTENCIA_PERSONA'
and vapProceso='AFILIACION_DEPENDIENTE_WEB' and vapEstadoProceso='ACTIVO' and vapOrden=1 and vapObjetoValidacion='TRABAJADOR_DEPENDIENTE'