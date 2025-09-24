--liquibase formatted sql

--changeset flopez:01
--comment: Mantis0265978 - Se agregan campos a HistoricoBeneficiario
 ALTER TABLE HistoricoBeneficiario add hbeMotivoDesafiliacion VARCHAR(70);
 ALTER TABLE HistoricoBeneficiario add hbeFechaRetiro DATE;