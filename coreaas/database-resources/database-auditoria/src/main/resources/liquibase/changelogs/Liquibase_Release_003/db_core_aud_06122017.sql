--liquibase formatted sql

--changeset juagonzalez:01
--comment:Creacion de tabla ClaseTrabajador
CREATE TABLE ClaseTrabajador_aud (
	cltId BIGINT NOT NULL,
	cltTipo VARCHAR (19) NOT NULL,
);

--changeset juagonzalez:02
--comment:Se modifican campos en la tabla RolAfiliado
UPDATE RolAfiliado_aud SET roaClaseTrabajador = 1 WHERE roaClaseTrabajador = 'MADRE_COMUNITARIA';
UPDATE RolAfiliado_aud SET roaClaseTrabajador = 2 WHERE roaClaseTrabajador = 'SERVICIO_DOMESTICO';
UPDATE RolAfiliado_aud SET roaClaseTrabajador = 3 WHERE roaClaseTrabajador = 'REGULAR';
UPDATE RolAfiliado_aud SET roaClaseTrabajador = 4 WHERE roaClaseTrabajador = 'TRABAJADOR_POR_DIAS';
UPDATE RolAfiliado_aud SET roaClaseTrabajador = 2 WHERE roaClaseTrabajador = 'TAXISTA';

--changeset juagonzalez:03
--comment:Se modifican campos en la tabla RolAfiliado
ALTER TABLE RolAfiliado_aud ALTER COLUMN roaClaseTrabajador BIGINT NULL;

--changeset juagonzalez:04
--comment:Se adicionan campos en la tabla ClaseTrabajador_aud
ALTER TABLE ClaseTrabajador_aud ADD REV int NOT NULL;
ALTER TABLE ClaseTrabajador_aud ADD REVTYPE smallint NULL;
ALTER TABLE ClaseTrabajador_aud ADD CONSTRAINT FK_ClaseTrabajador_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
