--liquibase formatted sql

--changeset juagonzalez:01
--comment:Se modifican campos en la tabla RolAfiliado
ALTER TABLE RolAfiliado_aud ALTER COLUMN roaClaseTrabajador VARCHAR(20) NULL;

--changeset juagonzalez:02
--comment:Se modifican campos en la tabla RolAfiliado
UPDATE RolAfiliado_aud SET roaClaseTrabajador = 'MADRE_COMUNITARIA' WHERE roaClaseTrabajador = '1';
UPDATE RolAfiliado_aud SET roaClaseTrabajador = 'SERVICIO_DOMESTICO' WHERE roaClaseTrabajador = '2';
UPDATE RolAfiliado_aud SET roaClaseTrabajador = 'REGULAR' WHERE roaClaseTrabajador = '3';
UPDATE RolAfiliado_aud SET roaClaseTrabajador = 'TRABAJADOR_POR_DIAS' WHERE roaClaseTrabajador = '4';

--changeset juagonzalez:03
--comment:Se adicionan campos en la tabla ClaseTrabajador_aud
ALTER TABLE ClaseTrabajador_aud DROP CONSTRAINT FK_ClaseTrabajador_aud_REV;
DROP TABLE ClaseTrabajador_aud;
