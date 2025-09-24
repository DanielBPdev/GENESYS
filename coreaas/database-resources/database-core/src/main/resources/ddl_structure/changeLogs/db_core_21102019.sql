--liquibase formatted sql

--changeset mamonroy:01
--comment:
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta)
VALUES ('CREACIÓN DE USUARIO EXITOSA','<p>Para autenticarse debe hacer uso de la siguiente&nbsp;informaci&oacute;n:<br /><br />Usuario:&nbsp; &nbsp; &nbsp; &nbsp;[usuario]<br />Contrase&ntilde;a:&nbsp; [Password]</p>','<p>&nbsp;</p><p>&nbsp;</p>
<p>Se&ntilde;or (a)&nbsp;<br />[nombreUsuario]&nbsp;<br /><br /><br />Su usuario para ingresar al&nbsp;portal de operativo ha sido creado exitosamente.&nbsp;</p>',NULL,'<p>Se&ntilde;or (a) <br /> [nombreUsuario] <br /><br /><br />Su usuario para ingresar al portal operativo ha sido creado exitosamente. <br /><br />Para autenticarse debe hacer uso de la siguiente informaci&oacute;n:<br /><br />Usuario:&nbsp; &nbsp; &nbsp; &nbsp;[usuario]<br />Contrase&ntilde;a:&nbsp; [Password]</p>
<p><br /><br />Cordialmente,<br /><br /><br /><br />Administrador Genesys</p>','Creación de usuario CCF exitosa','<p>Cordialmente,<br /><br /><br /><br />Administrador</p>','NTF_CRCN_USR_EXT');

--changeset mamonroy:02
--comment:
UPDATE PLantillaComunicado
SET pcoEtiqueta = 'NTF_CRCN_USR_CCF_EXT'
WHERE pcoEtiqueta = 'NTF_CRCN_USR_EXT'
AND pcoNombre = 'Creación de usuario CCF exitosa';

--changeset mamonroy:03
--comment:Complemento CCP1OCT2019 [40]
DELETE FROM ValidacionProceso
WHERE vapBloque = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS'
AND vapValidacion = 'VALIDACION_TIPO_DOCUMENTO_PERSONA'
AND vapProceso = 'NOVEDADES_PERSONAS_PRESENCIAL';

--changeset clmarin:04
--comment:
ALTER TABLE ItemChequeo ADD ichFechaRecepcionDocumento DATE NULL;
ALTER TABLE aud.ItemChequeo_aud ADD ichFechaRecepcionDocumento DATE NULL;