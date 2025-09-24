--liquibase formatted sql

--changeset abaquero:01
--comment: INSERT tablas
INSERT PilaOportunidadPresentacionPlanilla (popTipoPlanilla, popOportunidad, popTipoCotizanteEspecifico) VALUES ('Y', 'MES_VENCIDO', '57');
INSERT PilaOportunidadPresentacionPlanilla (popTipoPlanilla, popOportunidad, popTipoCotizanteEspecifico) VALUES ('I', 'MES_VENCIDO', '57');