--liquibase formatted sql

--changeset clmarin:01
--comment: Insercion en la tabla PlantillaComunicado
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoIdentificadorImagenPie,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Notificación desbloqueo de cuenta','<p>Señor(a) <br />${nombreUsuario} <br />Su cuenta ha sido desbloqueada por favor ingrese con usuario: ${usuario}  y contraseña:${password}</p>','${fechaDelSistema}',NULL,'Aviso desbloqueo cuenta de usuario','Notificación desbloqueo de cuenta','<p>Cordialmente,Administrador GENESYS</p>','NTF_BLQ_CTA_USR ');
