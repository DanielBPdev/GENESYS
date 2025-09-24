--liquibase formatted sql

--changeset  atoro:01
--comment:Insert datos ValidacionProceso
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapOrden,vapEstadoProceso)
values('AFILIACION_EMPRESAS_PRESENCIAL','NUEVA_AFILIACION','VALIDACION_ESTADO_NUEVA_AFILIACION',1,'ACTIVO');

--changeset  lzarate:02
--comment:PlantillaComunicado
update PlantillaComunicado set pcoMensaje = 'Señor(a):<br/>[nombreCompleto]<br/><br/>La solicitud de afiliacion ha sido radicada exitosamente con el número de radicado [numeroRadicado].<br/><br/>Cordialmente,<br/><br/>[nombreSedeCCF].' where pcoEtiqueta in ('NOTIFICACION_RADICACION_SOLICITUD_AFILIACION_INDEPENDIENTE_WEB','NOTIFICACION_RADICACION_SOLICITUD_AFILIACION_PENSIONADO_WEB');
