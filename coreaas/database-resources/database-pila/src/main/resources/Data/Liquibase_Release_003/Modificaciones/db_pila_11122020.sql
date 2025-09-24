--liquibase formatted sql

--changeset silopez:01
--comment: IInsercion nuevas planillas
INSERT INTO PilaOportunidadPresentacionPlanilla (popTipoPlanilla , popOportunidad )
VALUES ('O', 'MES_VENCIDO');

INSERT INTO PilaOportunidadPresentacionPlanilla (popTipoPlanilla , popOportunidad )
VALUES ('Q', 'MES_VENCIDO');