--liquibase formatted sql
--changeset Kvides:01 runAlways:true runOnChange:true


if exists (select 1 from sys.objects WHERE object_id = object_id(N'df_Efectivo_revtype')) alter table dbo.MedioEfectivo_aud drop constraint df_Efectivo_revtype;
alter table dbo.MedioEfectivo_aud add constraint df_Efectivo_revtype default 0 for REVTYPE;

if exists (select 1 from sys.objects WHERE object_id = object_id(N'df_Transferencia_revtype')) alter table dbo.MedioTransferencia_aud drop constraint df_Transferencia_revtype;
alter table dbo.MedioTransferencia_aud add constraint df_Transferencia_revtype default 0 for REVTYPE;

if exists (select 1 from sys.objects WHERE object_id = object_id(N'df_Tarjeta_revtype')) alter table dbo.MedioTarjeta_aud drop constraint df_Tarjeta_revtype;
alter table dbo.MedioTarjeta_aud add constraint df_Tarjeta_revtype default 0 for REVTYPE;
