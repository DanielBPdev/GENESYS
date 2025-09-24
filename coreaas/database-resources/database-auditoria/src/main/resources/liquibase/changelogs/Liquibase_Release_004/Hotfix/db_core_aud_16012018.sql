--liquibase formatted sql

--changeset jzambrano:02
--comment:Se elimina campo en la tabla Empleador_aud
ALTER TABLE Empleador_aud DROP COLUMN empEstadoAportesEmpleador;

--changeset mosanchez:03 
--comment:Actualizacion de registros de la tabla RolAfiliado_aud y Empleador_aud
UPDATE RolAfiliado_aud SET roaEstadoAfiliado = NULL WHERE roaEstadoAfiliado IN('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_CON_INFORMACION');
UPDATE Empleador_aud SET empEstadoEmpleador = NULL WHERE empEstadoEmpleador IN('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_CON_INFORMACION');
UPDATE RolAfiliado_aud SET roaEstadoAfiliado = 'INACTIVO' WHERE roaEstadoAfiliado = 'NO_FORMALIZADO_RETIRADO_CON_APORTES';
UPDATE Empleador_aud SET empEstadoEmpleador = 'INACTIVO' WHERE empEstadoEmpleador = 'NO_FORMALIZADO_RETIRADO_CON_APORTES';

--changeset mosanchez:04 
--comment:Se modifican tamaños de campos de las tablas RolAfiliado_aud, Empleador_aud, Solicitud_aud y Categoria_aud
ALTER TABLE RolAfiliado_aud ALTER COLUMN roaEstadoAfiliado VARCHAR(8);
ALTER TABLE Empleador_aud ALTER COLUMN empEstadoEmpleador VARCHAR(8);
ALTER TABLE Solicitud_aud ALTER COLUMN solClasificacion VARCHAR(48);
ALTER TABLE Categoria_aud ALTER COLUMN catClasificacion VARCHAR(48) NOT NULL;

--changeset mosanchez:05 
--comment:Se modifican tamaños de campos de las tablas RolAfiliado_aud, Empleador_aud, Solicitud_aud y Categoria_aud
ALTER TABLE RolAfiliado_aud ALTER COLUMN roaEstadoAfiliado VARCHAR(41);
ALTER TABLE Empleador_aud ALTER COLUMN empEstadoEmpleador VARCHAR(41);