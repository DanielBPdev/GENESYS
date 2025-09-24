--- Tabla de log para las planillas N
if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'logPlanillasN' and TABLE_SCHEMA = 'dbo')
begin

create table [dbo].[logPlanillasN](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[planillaN] [bigint] NULL,
	[planillaOriginal] [bigint] NULL,
	[fechaProceso] [datetime] NULL,
	[Mensaje] [varchar](800) NULL
)

end

if not exists 
(select  1 from information_schema.columns where table_schema = 'dbo' and table_name = 'logPlanillasN' and column_name = 'fechaPagoAsociado')
begin
alter table dbo.logPlanillasN add fechaPagoAsociado date, periodo varchar(7)
end