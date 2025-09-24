--liquibase formatted sql

--changeset mosanchez:01 
--comment:Actualizacion de registros de la tabla RolAfiliado
UPDATE RolAfiliado SET roaEstadoAfiliado = NULL WHERE roaEstadoAfiliado IN('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_CON_INFORMACION');
UPDATE Empleador SET empEstadoEmpleador = NULL WHERE empEstadoEmpleador IN('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_CON_INFORMACION');
UPDATE RolAfiliado SET roaEstadoAfiliado = 'INACTIVO' WHERE roaEstadoAfiliado = 'NO_FORMALIZADO_RETIRADO_CON_APORTES';
UPDATE Empleador SET empEstadoEmpleador = 'INACTIVO' WHERE empEstadoEmpleador = 'NO_FORMALIZADO_RETIRADO_CON_APORTES';

--changeset mosanchez:02 
--comment:Se modifican tamaños de campos de las tablas RolAfiliado, Empleador, Solicitud y Categoria
ALTER TABLE RolAfiliado ALTER COLUMN roaEstadoAfiliado VARCHAR(8);
ALTER TABLE Empleador ALTER COLUMN empEstadoEmpleador VARCHAR(8);
ALTER TABLE Solicitud ALTER COLUMN solClasificacion VARCHAR(48);
ALTER TABLE Categoria ALTER COLUMN catClasificacion VARCHAR(48) NOT NULL;

--changeset jocorrea:03 
--comment:Se actualiza registro en la tabla ParametrizacionNovedad y se hace insercion de registro en la tabla ValidacionProceso
UPDATE ParametrizacionNovedad SET novTipoNovedad = 'GRUPO_FAMILIAR', novProceso = 'NOVEDADES_PERSONAS_PRESENCIAL' WHERE novTipoTransaccion = 'DESACTIVAR_ACTIVAR_GRUPO_FAMILIAR_INEMBARGABLE_GRUPO_FAMILIAR';
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('NOVEDAD_DESACTIVAR_ACTIVAR_GRUPO_FAMILIAR_INEMBARGABLE_GRUPO_FAMILIAR',NULL,'NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);

--changeset mosanchez:04 
--comment:Se modifican tamaños de campos de las tablas RolAfiliado, Empleador, Solicitud y Categoria
ALTER TABLE RolAfiliado ALTER COLUMN roaEstadoAfiliado VARCHAR(41);
ALTER TABLE Empleador ALTER COLUMN empEstadoEmpleador VARCHAR(41);