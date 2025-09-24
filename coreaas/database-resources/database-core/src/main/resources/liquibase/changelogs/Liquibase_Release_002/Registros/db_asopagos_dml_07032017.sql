--liquibase formatted sql

--changeset atoro:01
--comment: Insercion en la tabla ParametrizacionMetodoAsignacion
INSERT ParametrizacionMetodoAsignacion (pmaSedeCajaCompensacion,pmaProceso,pmaMetodoAsignacion,pmaUsuario,pmaGrupo) VALUES (1,'NOVEDADES_EMPRESAS_PRESENCIAL','NUMERO_SOLICITUDES',null,'back_novedades_empleador');
INSERT ParametrizacionMetodoAsignacion (pmaSedeCajaCompensacion,pmaProceso,pmaMetodoAsignacion,pmaUsuario,pmaGrupo) VALUES (99,'NOVEDADES_EMPRESAS_WEB','NUMERO_SOLICITUDES',null,'back_novedades_empleador');
INSERT ParametrizacionMetodoAsignacion (pmaSedeCajaCompensacion,pmaProceso,pmaMetodoAsignacion,pmaUsuario,pmaGrupo) VALUES (1,'NOVEDADES_PERSONAS_PRESENCIAL','NUMERO_SOLICITUDES',null,'back_novedades_personas');
INSERT ParametrizacionMetodoAsignacion (pmaSedeCajaCompensacion,pmaProceso,pmaMetodoAsignacion,pmaUsuario,pmaGrupo) VALUES (99,'NOVEDADES_DEPENDIENTE_WEB','NUMERO_SOLICITUDES',null,'back_novedades_personas');
INSERT ParametrizacionMetodoAsignacion (pmaSedeCajaCompensacion,pmaProceso,pmaMetodoAsignacion,pmaUsuario,pmaGrupo) VALUES (99,'NOVEDADES_PERSONAS_WEB','NUMERO_SOLICITUDES',null,'back_novedades_personas');

--changeset atoro:02
--comment: Eliminacion del campo vapBloque en ValidacionProceso
DELETE FROM historiaResultadoValidacion WHERE hrvvalidacion in(select vapValidacion FROM validacionProceso where vapBloque ='NOVEDAD')
DELETE FROM ValidacionProceso WHERE vapBloque='NOVEDAD';
