--liquibase formatted sql

--changeset abaquero:01
--comment: Se adicionan campos en la tabla staging.RegistroDetallado
ALTER TABLE staging.RegistroDetallado ADD redOUTAporteObligatorioMod numeric(19,5);
ALTER TABLE staging.RegistroDetallado ADD redOUTDiasCotizadosMod smallint;
ALTER TABLE staging.RegistroDetallado ADD redOUTRegistradoAporte bit;
ALTER TABLE staging.RegistroDetallado ADD redOUTRegistradoNovedad bit;
