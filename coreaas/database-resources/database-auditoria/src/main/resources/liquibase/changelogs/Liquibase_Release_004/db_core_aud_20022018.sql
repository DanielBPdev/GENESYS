--liquibase formatted sql

--changeset flopez:01
--comment: Se elimina campo de la tabla ProyectoSolucionVivienda_aud
ALTER TABLE ProyectoSolucionVivienda_aud DROP COLUMN psvValorProyectoVivienda;

--changeset rlopez:02
--comment: Se ajusta parametrizacion para agregar marca de fecha de dispersion de la liquidacion (pagos)
ALTER TABLE SolicitudLiquidacionSubsidio_aud DROP COLUMN IF EXISTS slsFechaDispersion;
ALTER TABLE SolicitudLiquidacionSubsidio_aud ADD slsFechaDispersion DATETIME;

--changeset jzambrano:03
--comment:Actualizacion de registros de la tabla RolAfiliado_aud y Empleador_aud
UPDATE RolAfiliado_aud SET roaEstadoAfiliado = NULL WHERE roaEstadoAfiliado IN('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_CON_INFORMACION');
UPDATE Empleador_aud SET empEstadoEmpleador = NULL WHERE empEstadoEmpleador IN('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_CON_INFORMACION');
UPDATE RolAfiliado_aud SET roaEstadoAfiliado = 'INACTIVO' WHERE roaEstadoAfiliado = 'NO_FORMALIZADO_RETIRADO_CON_APORTES';
UPDATE Empleador_aud SET empEstadoEmpleador = 'INACTIVO' WHERE empEstadoEmpleador = 'NO_FORMALIZADO_RETIRADO_CON_APORTES';

--changeset jzambrano:04
--comment: Se modifican los tamaños de campos en las tablas Empleador y RolAfiliado
ALTER TABLE RolAfiliado_aud ALTER COLUMN roaEstadoAfiliado VARCHAR(8);
ALTER TABLE Empleador_aud ALTER COLUMN empEstadoEmpleador VARCHAR(8);
