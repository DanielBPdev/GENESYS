--liquibase formatted sql

--changeset rarboleda:01
--comment: Se elimina las tablas BandejaEmpCeroTrabajadoresActivos y BandejaEmpleadorSinAfiliar
ALTER TABLE BandejaEmpCeroTrabajadoresActivos DROP CONSTRAINT FK_BandejaEmpCeroTrabajadoresActivos_becEmpleador;
ALTER TABLE BandejaEmpleadorSinAfiliar DROP CONSTRAINT FK_BandejaEmpleadorSinAfiliar_besEmpresa;
ALTER TABLE BandejaEmpleadorSinAfiliar DROP CONSTRAINT UK_BandejaEmpleadorSinAfiliar_besEmpresa;
DROP TABLE BandejaEmpCeroTrabajadoresActivos;
DROP TABLE BandejaEmpleadorSinAfiliar;

--changeset jocampo:02
--comment: Se modifica el tamaño del campo roaEstadoAfiliado
ALTER TABLE RolAfiliado ALTER COLUMN roaEstadoAfiliado varchar(50); 
