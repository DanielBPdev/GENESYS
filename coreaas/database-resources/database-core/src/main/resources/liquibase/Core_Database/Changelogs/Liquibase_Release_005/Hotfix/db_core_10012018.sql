--liquibase formatted sql

--changeset jocorrea:01
--comment:Se actualiza registro en la tabla ParametrizacionNovedad
UPDATE ParametrizacionNovedad SET novTipoNovedad = 'GENERAL', novRutaCualificada = 'com.asopagos.novedades.convertidores.empleador.ActualizarEmpleadorNovedad' WHERE novTipoTransaccion = 'ACTIVAR_INACTIVAR_CODIGO_SUCURSAL_DEBE_COINCIDIR_CON_PILA'

--changeset jzambrano:02 runOnChange:true
--comment:Se elimina campo en la tabla Empleador
IF EXISTS (SELECT * FROM sys.objects 
WHERE object_id = OBJECT_ID(N'CK_Empleador_empEstadoAportesEmpleador')) 
ALTER TABLE Empleador DROP CONSTRAINT CK_Empleador_empEstadoAportesEmpleador;

ALTER TABLE Empleador DROP COLUMN empEstadoAportesEmpleador;  