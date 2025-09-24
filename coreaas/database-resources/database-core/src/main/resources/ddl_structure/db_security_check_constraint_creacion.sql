--liquibase formatted sql
--changeset abaquero:01 runAlways:true runOnChange:true
--comment: Creaci�n de CKs 2018-12-26 13:20
ALTER TABLE Pregunta ADD CONSTRAINT CK_Pregunta_preEstado check (preEstado in ('ACTIVO','INACTIVO'));

ALTER TABLE ReferenciaToken ADD CONSTRAINT CK_ReferenciaToken_retTipoIdentificacion check (retTipoIdentificacion in ('REGISTRO_CIVIL','TARJETA_IDENTIDAD','CEDULA_CIUDADANIA','CEDULA_EXTRANJERIA','PASAPORTE','CARNE_DIPLOMATICO','NIT','SALVOCONDUCTO','PERM_ESP_PERMANENCIA','PERM_PROT_TEMPORAL'));

