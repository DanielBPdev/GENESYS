--liquibase formatted sql

--changeset borozco:01
--comment:Se adicionan campos en las tablas Empleador_aud y RolAfiliado_aud
ALTER TABLE Empleador_aud ADD empMarcaExpulsion VARCHAR (22) NULL;
ALTER TABLE RolAfiliado_aud ADD roaMarcaExpulsion VARCHAR (22) NULL;

--changeset fvasquez:02
--comment:Se adiciona campo en la tabla DocumentoCartera_aud
ALTER TABLE DocumentoCartera_aud ADD dcaConsecutivoLiquidacion VARCHAR(10) NULL;
