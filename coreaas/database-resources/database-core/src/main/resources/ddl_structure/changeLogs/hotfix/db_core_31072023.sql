CREATE TABLE [dbo].[ListaRecursosPrioridad](
	[lrpId] [bigint] IDENTITY(1,1) NOT NULL,
	[lrpRecursoPrioridad] [varchar](50) NULL,
	[lrpEstado] [bit] NULL
 CONSTRAINT [PK_ListaRecursosPrioridad_lrpId] PRIMARY KEY CLUSTERED 
(
	[lrpId] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
-----------------------------------------------------
insert into [dbo].[ListaRecursosPrioridad] (lrpRecursoPrioridad, lrpEstado)
  VALUES ('RECURSO_PRIMERA_PRIORIDAD',1),( 'RECURSO_SEGUNDA_PRIORIDAD', 1),('RECURSO_TERCERA_PRIORIDAD', 1)

-----------------------------------------------------
    alter table aud.PostulacionAsignacion_aud add pasRecursoPrioridad varchar(50)
    
    alter table PostulacionAsignacion add pasRecursoPrioridad varchar(50)