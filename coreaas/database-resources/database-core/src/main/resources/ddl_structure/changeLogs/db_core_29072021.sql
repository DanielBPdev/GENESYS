--liquibase formatted sql

--changeset rcastillo:01
--comment:Ajuste para el control de duplicados planillas en aportes. 
if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA = 'dbo' and TABLE_NAME = 'AportesControlDuplicados')
	begin
		CREATE TABLE [dbo].[AportesControlDuplicados](
					[apgPeriodoAporte] [varchar](7) NULL,
					[apgPersona] [bigint] NULL,
					[apgEmpresa] [bigint] NULL,
					[apgTipoSolicitante] [varchar](100) NULL,
					[apgEstadoAporteAportante] [varchar](100) NULL,
					[apgRegistroGeneral] [bigint] NULL,
					[fechaIntento] [datetime] NULL,
					[comentario] [varchar](100) NULL
				) ON [PRIMARY]
	end
else
	begin
		print 'La tabla ya está creada'
	end