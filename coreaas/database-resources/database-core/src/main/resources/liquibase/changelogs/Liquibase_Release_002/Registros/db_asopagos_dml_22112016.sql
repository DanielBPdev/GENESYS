--liquibase formatted sql

--changeset  lzarate:01
--comment:  Actualización de la plantilla de comunicado
update PlantillaComunicado set pcoAsunto = 'Notificación Radicacion Solicitud Afiliación', pcoMensaje='Señor(a):<br/>[nombreCompleto]<br/><br/>La solicitud ha sido radicada y enviada para revisión. Recuerde que para conocer el estado de su solicitud debe ingresar a la pantalla de consulta de seguimiento <a href="[pantallaSeguimiento]">[pantallaSeguimiento]</a>, con el número de radicado [numeroRadicado].<br/><br/>Cordialmente,<br/><br/>[nombreSedeCCF].' where pcoEtiqueta in ('NOTIFICACION_RADICACION_SOLICITUD_AFILIACION_INDEPENDIENTE_WEB','NOTIFICACION_RADICACION_SOLICITUD_AFILIACION_PENSIONADO_WEB');

--changeset  jocampo:02
--comment:  Actualización de la tabla parametro
UPDATE Parametro SET prmValor='asopagos-proyecto@heinsohn.com.co' WHERE prmNombre='mail.smtp.user' ;
UPDATE Parametro SET prmValor='zDgxdoNnBaLbivLqKjtnww==' WHERE prmNombre='mail.smtp.password' ;