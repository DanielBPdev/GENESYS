--liquibase formatted sql

--changeset dsuesca:01
--comment: Cambio tipo de datos diferentes de core a core_aud
ALTER TABLE aud.Beneficiario_aud DROP COLUMN benCertificadoEscolaridad;
ALTER TABLE aud.Beneficiario_aud DROP COLUMN benEstadoBeneficiarioCaja;
ALTER TABLE aud.Beneficiario_aud DROP COLUMN benFechaRecepcionCertificadoEscolar;
ALTER TABLE aud.Beneficiario_aud DROP COLUMN benFechaVencimientoCertificadoEscolar;
ALTER TABLE aud.Beneficiario_aud DROP COLUMN benLabora;
ALTER TABLE aud.Beneficiario_aud DROP COLUMN benSalarioMensualBeneficiario;

--changeset mosorio:02
--comment: 
ALTER TABLE ConvenioTerceroPagador ALTER COLUMN conAcuerdoDeFacturacion varchar(255) NULL;











