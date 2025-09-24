--liquibase formatted sql

--changeset atoro:01 
--comment: Se agrega campos en la tabla ValidacionProceso y en SucursalEmpresa
ALTER TABLE ValidacionProceso ADD vapInversa bit;
ALTER TABLE SucursalEmpresa ADD sueCoindicirCodigoPila bit;

--changeset atoro:02 
--comment: Eliminacion en ValidacionProceso
-- borrado de todas los registros de la tabla ValidacionProceso correspondientes a RN donde se llame a la validacion VALIDACION_PERSONA_EMPLEADOR_ACTIVO  
DELETE FROM ValidacionProceso WHERE vapValidacion = 'VALIDACION_PERSONA_EMPLEADOR_ACTIVO' AND vapBloque NOT LIKE 'NOVEDAD_%' AND vapProceso IN ('NOVEDADES_PERSONAS_PRESENCIAL','NOVEDADES_PERSONAS_WEB','NOVEDADES_DEPENDIENTE_WEB');
