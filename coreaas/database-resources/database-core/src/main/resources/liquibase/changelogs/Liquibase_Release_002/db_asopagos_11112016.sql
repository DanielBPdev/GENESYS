--liquibase formatted sql

--changeset  lzarate:01
--comment: HU-123-374
INSERT INTO ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden) values ('123-374-2','VALIDACION_SOLICITUD_PERSONA','AFILIACION_INDEPENDIENTE_WEB','ACTIVO','1');

--changeset  jcamargo:02
--comment: Actualización de la plantilla comunicado
UPDATE PlantillaComunicado SET pcoAsunto='Notificación de radicación de solicitud',
 pcoMensaje='Señores: <br/>[razonSocial]<br/><br/><br/>Cordial saludo<br/><br/><br/>Le notificamos que la solicitud de afiliación con número de radicado [numero radicado] fue radicada exitosamente <br/><br/> Cordialmente,<br/><br/><br/>[nombreCaja]'
 where pcoEtiqueta='NOTIFICACION_RADICACION_SOLICITUD_AFILIACION_EMPLEADOR_WEB';
 
--changeset  mgiraldo:03
--comment: Eliminación y creaci
DROP INDEX IDX_Empresa_empNombreComercial ON Empresa;  
CREATE INDEX IDX_Empresa_empNombreComercial ON Empresa (empNombreComercial);