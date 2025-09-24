--liquibase formatted sql

--changeset juagonzalez:01
--comment:Creacion de tabla ClaseTrabajador
CREATE TABLE ClaseTrabajador (
	cltId BIGINT NOT NULL IDENTITY(1,1),
	cltTipo VARCHAR (19) NOT NULL,
CONSTRAINT PK_ClaseTrabajador_cltId PRIMARY KEY (cltId)
);

--changeset juagonzalez:02
--comment:Insercion de registros en la tabla ClaseTrabajador
INSERT ClaseTrabajador (cltTipo) VALUES ('MADRE_COMUNITARIA');
INSERT ClaseTrabajador (cltTipo) VALUES ('SERVICIO_DOMESTICO');
INSERT ClaseTrabajador (cltTipo) VALUES ('REGULAR');
INSERT ClaseTrabajador (cltTipo) VALUES ('TRABAJADOR_POR_DIAS');
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaClaseTrabajador')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaClaseTrabajador;

--changeset juagonzalez:03
--comment:Actualizacion de registros en la tabla RolAfiliado
UPDATE RolAfiliado SET RolAfiliado.roaClaseTrabajador = clt.cltId FROM RolAfiliado roa, ClaseTrabajador clt WHERE clt.cltTipo = 'MADRE_COMUNITARIA' AND roa.roaClaseTrabajador = 'MADRE_COMUNITARIA';
UPDATE RolAfiliado SET RolAfiliado.roaClaseTrabajador = clt.cltId FROM RolAfiliado roa, ClaseTrabajador clt WHERE clt.cltTipo = 'SERVICIO_DOMESTICO' AND roa.roaClaseTrabajador = 'SERVICIO_DOMESTICO';
UPDATE RolAfiliado SET RolAfiliado.roaClaseTrabajador = clt.cltId FROM RolAfiliado roa, ClaseTrabajador clt WHERE clt.cltTipo = 'REGULAR' AND roa.roaClaseTrabajador = 'REGULAR';
UPDATE RolAfiliado SET RolAfiliado.roaClaseTrabajador = clt.cltId FROM RolAfiliado roa, ClaseTrabajador clt WHERE clt.cltTipo = 'TRABAJADOR_POR_DIAS' AND roa.roaClaseTrabajador = 'TRABAJADOR_POR_DIAS';
UPDATE RolAfiliado SET RolAfiliado.roaClaseTrabajador = clt.cltId FROM RolAfiliado roa, ClaseTrabajador clt WHERE clt.cltTipo = 'SERVICIO_DOMESTICO' AND roa.roaClaseTrabajador = 'TAXISTA';

--changeset juagonzalez:04
--comment:Se modifican campos en la tabla RolAfiliado
ALTER TABLE RolAfiliado ALTER COLUMN roaClaseTrabajador BIGINT NULL;
ALTER TABLE RolAfiliado ADD CONSTRAINT FK_RolAfiliado_roaClaseTrabajador FOREIGN KEY (roaClaseTrabajador) REFERENCES ClaseTrabajador (cltId);
