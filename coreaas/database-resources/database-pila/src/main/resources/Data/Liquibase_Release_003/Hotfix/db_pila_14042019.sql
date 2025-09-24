--liquibase formatted sql

--changeset abaquero:01
--comment: Adición de campos para proceso en PILA 2 de planillas mixtas
alter table PilaErrorValidacionLog add pevIdRegistroTipo2 bigint
alter table staging.RegistroDetallado add redIdRegistro2pila bigint
alter table staging.RegistroDetallado add redOUTEnviadoAFiscalizacionInd bit
alter table staging.RegistroDetallado add redOUTMotivoFiscalizacionInd varchar(30)
alter table staging.RegistroDetallado add redOUTRegistroActual bit
alter table staging.RegistroDetalladoNovedad add rdnOUTTipoAfiliado varchar(50)