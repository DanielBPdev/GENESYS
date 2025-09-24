--liquibase formatted sql

--changeset jvelandia:01
--comment: Creacion campos empMotivoInactivaRetencionSubsidio,sueMotivoInactivaRetencionSubsidio
INSERT CONSTANTE(cnsNombre,cnsValor,cnsDescripcion)values('OPEN_OFFICE_END_POINT','10.77.187.51','Endpoint donde se encuentran disponible el servidor openoffice');
INSERT CONSTANTE(cnsNombre,cnsValor,cnsDescripcion)values('OPEN_OFFICE_PORT','8200','Puerto expuesto por el servidor openoffice');