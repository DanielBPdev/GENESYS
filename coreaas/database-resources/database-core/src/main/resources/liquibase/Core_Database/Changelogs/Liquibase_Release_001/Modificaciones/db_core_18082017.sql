--liquibase formatted sql

--changeset abaquero:01
--comment: Se actualizan registros de la tabla ValidatorParamValue
UPDATE ValidatorParamValue SET value='I215::ING,I216::RET,I219::SLN,I220::IGE,I221::LMA,I222::VAC-LR,I217::VSP,I218::VST,I223::IRL' WHERE id=2111589;