--liquibase formatted sql

--changeset lzarate:01
--comment: Registro PlantillaComunicado
UPDATE PlantillaComunicado set pcoMensaje = 'Señor(a) <br/>[nombrePersona]<br/>Las validaciones preliminares han sido exitosas. Para continuar con el proceso de afiliación por favor seguir el siguiente vínculo:<br/><br/><a href="[linkRegistro]">[linkRegistro]</a><br/><br/>Cordialmente, <br/><br/>[nombreSedeCCF]', pcoAsunto = 'Continuación proceso de afiliación independiente' where pcoEtiqueta = 'NOTIFICACION_ENROLAMIENTO_AFILIACION_INDEPENDIENTE_WEB';
UPDATE PlantillaComunicado set pcoMensaje = 'Señor(a) <br/>[nombrePersona]<br/>Las validaciones preliminares han sido exitosas. Para continuar con el proceso de afiliación por favor seguir el siguiente vínculo:<br/><br/><a href="[linkRegistro]">[linkRegistro]</a><br/><br/>Cordialmente, <br/><br/>[nombreSedeCCF]', pcoAsunto = 'Continuación proceso de afiliación pensionado' where pcoEtiqueta = 'NOTIFICACION_ENROLAMIENTO_AFILIACION_PENSIONADO_WEB';
