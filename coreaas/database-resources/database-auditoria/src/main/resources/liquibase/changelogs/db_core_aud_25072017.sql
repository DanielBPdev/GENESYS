--liquibase formatted sql

--changeset rarboleda:01
--comment: Se elimina las tablas BandejaEmpCeroTrabajadoresActivos_aud y BandejaEmpleadorSinAfiliar_aud
ALTER TABLE BandejaEmpCeroTrabajadoresActivos_aud DROP CONSTRAINT FK_BandejaEmpCeroTrabajadoresActivos_aud_REV;
ALTER TABLE BandejaEmpleadorSinAfiliar_aud DROP CONSTRAINT FK_BandejaEmpleadorSinAfiliar_aud_REV;
DROP TABLE BandejaEmpCeroTrabajadoresActivos_aud;
DROP TABLE BandejaEmpleadorSinAfiliar_aud;

--changeset jocampo:02
--comment: Se modifica el tamaño del campo roaEstadoAfiliado
ALTER TABLE RolAfiliado_aud ALTER COLUMN roaEstadoAfiliado varchar(50); 

--changeset jocampo:03
--comment: Eliminacion del campo afiEstadoAfiliadoCaja
ALTER TABLE Afiliado_aud DROP COLUMN afiEstadoAfiliadoCaja;
