if not exists (select * from sys.objects as o where o.name ='PostulacionProveedor')
begin
	create table [dbo].[PostulacionProveedor](
		[prpId] [bigint] identity(1,1) NOT NULL,
		[prpSolicitudLegalizacionFovis] [bigint] NULL,
		[prpProveedor] [varchar](30) NULL,
		[prpValorDesembolsoProveedor] [numeric] NULL,
		[prpUltimaFechaModificacion] [datetime] NULL,
	 constraint [PK_PostulacionProveedor_prpId] primary key clustered
	([prpId] asc)) on [primary]
end
