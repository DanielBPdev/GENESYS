--liquibase formatted sql

--changeset juagonzalez:01
--comment:Se modifican campos en la tabla RolAfiliado
ALTER TABLE RolAfiliado DROP CONSTRAINT FK_RolAfiliado_roaClaseTrabajador;
ALTER TABLE RolAfiliado ALTER COLUMN roaClaseTrabajador VARCHAR(20) NULL;

--changeset juagonzalez:02
--comment:Actualizacion de registros en la tabla RolAfiliado
UPDATE RolAfiliado SET RolAfiliado.roaClaseTrabajador = clt.cltTipo FROM RolAfiliado roa, ClaseTrabajador clt WHERE clt.cltTipo = 'MADRE_COMUNITARIA' AND roa.roaClaseTrabajador = '1';
UPDATE RolAfiliado SET RolAfiliado.roaClaseTrabajador = clt.cltTipo FROM RolAfiliado roa, ClaseTrabajador clt WHERE clt.cltTipo = 'SERVICIO_DOMESTICO' AND roa.roaClaseTrabajador = '2';
UPDATE RolAfiliado SET RolAfiliado.roaClaseTrabajador = clt.cltTipo FROM RolAfiliado roa, ClaseTrabajador clt WHERE clt.cltTipo = 'REGULAR' AND roa.roaClaseTrabajador = '3';
UPDATE RolAfiliado SET RolAfiliado.roaClaseTrabajador = clt.cltTipo FROM RolAfiliado roa, ClaseTrabajador clt WHERE clt.cltTipo = 'TRABAJADOR_POR_DIAS' AND roa.roaClaseTrabajador = '4';

--changeset juagonzalez:03
--comment:Creacion de tabla ClaseTrabajador
DROP TABLE ClaseTrabajador;