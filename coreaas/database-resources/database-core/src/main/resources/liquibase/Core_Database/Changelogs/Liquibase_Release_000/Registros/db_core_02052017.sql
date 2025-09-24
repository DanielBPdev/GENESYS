--liquibase formatted sql

--changeset ogiral:01
--comment: Corrección registrocajaclasificación
UPDATE RequisitoCajaClasificacion 
SET rtsRequisito = (SELECT reqId FROM Requisito WHERE reqDescripcion='Copia documento identidad  representante legal / administrador / empleador')
WHERE rtsEstado = 'OBLIGATORIO' 
AND rtsClasificacion = 'PERSONA_NATURAL' 
AND rtsTipoTransaccion = 'CAMBIO_RAZON_SOCIAL_NOMBRE' 
AND rtsCajaCompensacion = (SELECT ccfId FROM cajacompensacion WHERE ccfNombre = 'CONFAMILIARES') 
AND rtsTextoAyuda = 'Se revisa: -Que sea legible la copia del documento -Sin tachones ni enmendaduras -Ampliada al 150% -Que el nombre y número de la cédula corresponda a la cédula del representante legal en el formulario y demás documentos soporte. -Si es cédula de extranjería que no esté vencida ni próxima a vencer'; 

UPDATE RequisitoCajaClasificacion 
SET rtsRequisito = (SELECT reqId FROM Requisito WHERE reqDescripcion='Copia documento identidad  representante legal / administrador / empleador') 
WHERE rtsEstado = 'OBLIGATORIO' 
AND rtsClasificacion = 'EMPLEADOR_DE_SERVICIO_DOMESTICO' 
AND rtsTipoTransaccion = 'CAMBIO_RAZON_SOCIAL_NOMBRE' 
AND rtsCajaCompensacion = (SELECT ccfId FROM cajacompensacion WHERE ccfNombre = 'CONFAMILIARES')  
AND rtsTextoAyuda = 'Se revisa: -Que sea legible la copia del documento -Sin tachones ni enmendaduras -Ampliada al 150% -Que el nombre y número de la cédula corresponda a la cédula del representante legal en el formulario y demás documentos soporte. -Si es cédula de extranjería que no esté vencida ni próxima a vencer';