--liquibase formatted sql

--changeset  edelgado:01
--comment:  Actualización del CK CK_RequisitoCajaClasificacion_rtsTipoTransaccion 
UPDATE Requisito SET reqTipoRequisito='NINGUNA' where reqDescripcion not like '%ormulario%';
UPDATE Requisito SET reqTipoRequisito='FORMULARIO_AFILIACION' where reqDescripcion like '%ormulario%';


--changeset  jcamargo:03
INSERT INTO PlantillaComunicado (pcoAsunto,pcoMensaje,pcoEtiqueta)
VALUES ('Recuperación contraseña exitosa','Señor(a)<br/>[nombreUsuario]<br/><br/><br/>Cordial Saludo<br/><br/>Se ha recuperado satisfactoriamente su contraseña. Por favor utilice las siguientes credenciales para autenticarse en el portal:<br/><br/>Usuario: [usuario]<br/>Password: [password]<br/><br/><br/>Cordialmente, <br/><br/><br/>Administrador GENESYS','NOTIFICACION_RECUPERACION_CONTRASENA_EXITOSA');

