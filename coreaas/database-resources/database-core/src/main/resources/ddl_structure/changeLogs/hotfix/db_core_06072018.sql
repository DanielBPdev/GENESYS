--liquibase formatted sql

--changeset jvelandia:01
--comment: Actualizacion tabla VariableComunicado campo vcoTipoVariableComunicado
UPDATE VariableComunicado SET vcoTipoVariableComunicado='CONSTANTE' WHERE vcoClave='${responsableNovedadesCcf}';
UPDATE VariableComunicado SET vcoTipoVariableComunicado='CONSTANTE' WHERE vcoClave='${cargoResponsableNovedadesCcf}';

