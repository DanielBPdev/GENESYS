--liquibase formatted sql

--changeset  lzarate:01
--comment:Se actualiza plantilla comunicado para la carta de bienvenida del empleador
UPDATE PlantillaComunicado set pcoMensaje = '<p>Se&ntilde;ores&nbsp;</p><br/><p>[razonSocial]<br/><br/><br/>Reciban un cordial saludo.<br/><br/>En nombre de la [nombreCCF] le damos la bienvenida. Lo invitamos a comenzar a gozar de los distintos servicios que le ofrecemos.<br/><br/><br/>Cordialmente,<br/><br/>[nombreCCF]</p>' where pcoEtiqueta = 'CARTA_BIENVENIDA_EMPLEADOR';

--changeset atoro:02
--comment: Creación de la constante enumeración ValidacionCoreEnum
update Empleador SET empMotivoDesafiliacion='CESE_EN_PROCESO_LIQUIDACION_LIQUIDADO_FALLECIDO' where empmotivoDesafiliacion in ('CESE_ACTIVIDADES','EN_PROCESO_LIQUIDACION','LIQUIDACION','FALLECIDO')