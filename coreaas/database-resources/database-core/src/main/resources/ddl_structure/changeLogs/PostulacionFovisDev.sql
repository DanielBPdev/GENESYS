
if not exists (select * from sys.objects as o where o.name ='postulacionfovisdev') 
begin
	create table [dbo].[postulacionfovisdev](
		[pdfid] [bigint] identity(1,1) NOT NULL,
		[pfdvalorRestituir] [bigint] NULL,
		[pfdmediodepago] [varchar](30) NULL,
		[pfdrendimientoFinanciero] [bigint] NULL,
		[pfdnumeroIdQuienHaceDevolucion] [varchar](16) NULL,
		[pfdtipoIdQuienHaceDevolucion] [varchar](20) NULL,
		[pfdnombreCompleto] [varchar](250) NULL,
		[pfdpostulacionfovis] [bigint] NULL,
		[pfdsolicitudglobal] [bigint] NULL,
		[pfdfecharegistro] [datetime] NULL,
	 constraint [PK_postulacionfovisdev_pdfid] primary key clustered 
	([pdfid] asc)) on [primary]
end

