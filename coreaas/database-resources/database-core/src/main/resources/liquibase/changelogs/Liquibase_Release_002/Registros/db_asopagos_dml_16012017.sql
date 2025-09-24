--liquibase formatted sql

--changeset  lzarate:01
--comment:Actualización datos ParametrizacionMetodoAsignacion [HU-123-377] 
update ParametrizacionMetodoAsignacion set pmaGrupo ='back_afiliacion_personas' where pmaProceso in ('AFILIACION_DEPENDIENTE_WEB','AFILIACION_INDEPENDIENTE_WEB');

--changeset  lzarate:02
--comment:Actualización datos ParametrizacionMetodoAsignacion [HU-123-378] 
update PlantillaComunicado set pcoAsunto = 'Bienvenido a su caja de compensación', pcoMensaje = 'Señor(a) <br/>[nombreCompleto]<br/><br/><br/>Reciba un cordial saludo.<br/><br/>En nombre de la [nombreCCF] le damos la bienvenida. Lo invitamos a disfrutar de los distintos servicios que le ofrecemos.<br/><br/><br/>Cordialmente,<br/><br/>[nombreCCF].' where pcoEtiqueta in ('CARTA_BIENVENIDA_INDEPENDIENTE','CARTA_BIENVENIDA_PENSIONADO');
update PlantillaComunicado set pcoAsunto = 'Aceptación de afiliación', pcoMensaje = 'Señor(a) <br/>[nombreCompleto]<br/><br/><br/>Reciban un cordial saludo.<br/><br/>Le informamos que la afiliación a [nombreCCF] fue aceptada satisfactoriamente.<br/><br/><br/>Cordialmente,<br/><br/>[nombreCCF].' where pcoEtiqueta in ('CARTA_ACEPTACION_INDEPENDIENTE','CARTA_ACEPTACION_PENSIONADO');

--changeset  lzarate:03
--comment:Actualización datos PlantillaComunicado [HU-123-380 
update PlantillaComunicado set pcoAsunto = 'Solicitud afiliación requiere subsanación', pcoMensaje = 'Señor(a) <br/>[nombreCompleto]<br/><br/><br/>Reciba un cordial saludo.<br/><br/>La solicitud de afiliación con número de radicado [numeroRadicado] requiere ser subsanada para seguir con la vinculación a [nombreCCF]. Lo invitamos a realizar el seguimiento y/o correciones a su solicitud de afiliación.<br/><br/><br/>Cordialmente,<br/><br/>[nombreCCF].' where pcoEtiqueta in ('NOTIFICACION_REQUERIR_SUBSANACION_SOLICITUD_AFILIACION_INDEPENDIENTE','NOTIFICACION_REQUERIR_SUBSANACION_SOLICITUD_AFILIACION_PENSIONADO');


