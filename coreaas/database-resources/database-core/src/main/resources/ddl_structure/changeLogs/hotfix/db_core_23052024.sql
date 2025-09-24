--liquibase formatted sql

--changeset keynervides
--comment: consultar.saldo.cualquierMedioDePago

declare @sql nvarchar(max)
if exists (select * from sys.indexes where name = 'idx_casMedioDePagoTransaccion' and object_id = object_id('CuentaAdministradorSubsidio'))
	begin
		set @sql = 'drop index idx_casMedioDePagoTransaccion on CuentaAdministradorSubsidio;'
		exec sp_executesql @sql
		set @sql = 'create nonclustered index idx_casMedioDePagoTransaccion on [dbo].[CuentaAdministradorSubsidio] ([casEstadoTransaccionSubsidio],[casMedioDePagoTransaccion],[casTipoTransaccionSubsidio])'
		exec sp_executesql @sql
	end
	else
	begin
		set @sql = 'create nonclustered index idx_casMedioDePagoTransaccion on [dbo].[CuentaAdministradorSubsidio] ([casEstadoTransaccionSubsidio],[casMedioDePagoTransaccion],[casTipoTransaccionSubsidio])'
		exec sp_executesql @sql
	end;
