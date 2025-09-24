--liquibase db_core_21082024.sql

if not exists (select 1 from sys.indexes where object_id = OBJECT_ID('CuentaAdministradorSubsidio') and name = 'IXNC_casAdministradorSubsidio')
 begin
		create nonclustered index [IXNC_casAdministradorSubsidio] on [dbo].[CuentaAdministradorSubsidio]
		([casAdministradorSubsidio] desc) with (statistics_norecompute = off, drop_existing = off, online = off, optimize_for_sequential_key = off) on [PRIMARY]
 end

if not exists (select 1 from sys.indexes where object_id = OBJECT_ID('AdministradorSubsidio') and name = 'IXNC_asuPersona')
 begin
		create nonclustered index [IXNC_asuPersona] ON [dbo].[AdministradorSubsidio]
		([asuPersona] desc) with (statistics_norecompute = off, drop_existing = off, online = off, optimize_for_sequential_key = off) on [PRIMARY]
 end