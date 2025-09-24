-- =============================================
-- Author:  Robinson Castillo
-- Create Date: 2022-02-16
-- Description: Actualizar tabla cotReintegro, para proceso de novedades. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[ASP_COTREINTEGRO]
AS
BEGIN
    SET NOCOUNT ON
	begin
		if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA = 'dbo' and TABLE_NAME = 'cotReintegro')
			begin
				CREATE TABLE [dbo].[cotReintegro]([id] [bigint] NOT NULL,[perNumeroIdentificacion] [varchar](30) NULL,[perTipoIdentificacion] [varchar](30) NULL,
				[roaEstadoAfiliado] [varchar](20) NULL,[roaOportunidadPago] [varchar](30) NULL,[roaFechaRetiro] [date], [tiempoReinAfi] [smallInt], [aplicarReintegro] [smallInt])
				create clustered index numId on dbo.cotReintegro (perNumeroIdentificacion)
				merge dbo.cotReintegro as d
				using core.cotReintegro as o on o.id = d.id
				when matched then update set d.perNumeroIdentificacion = o.perNumeroIdentificacion, d.perTipoIdentificacion = o.perTipoIdentificacion, 
				d.roaEstadoAfiliado = o.roaEstadoAfiliado, d.roaOportunidadPago = o.roaOportunidadPago, d.roaFechaRetiro = o.roaFechaRetiro, d.tiempoReinAfi = o.tiempoReinAfi
				when not matched then insert (id, perNumeroIdentificacion, perTipoIdentificacion, roaEstadoAfiliado, roaOportunidadPago, roaFechaRetiro, tiempoReinAfi)
				values (o.id, o.perNumeroIdentificacion, o.perTipoIdentificacion,  o.roaEstadoAfiliado, o.roaOportunidadPago,  o.roaFechaRetiro, o.tiempoReinAfi)
				when not matched by source then delete;
			end
		else
			begin
				merge dbo.cotReintegro as d
				using core.cotReintegro as o on o.id = d.id
				when matched then update set d.perNumeroIdentificacion = o.perNumeroIdentificacion,  d.perTipoIdentificacion = o.perTipoIdentificacion, 
				d.roaEstadoAfiliado = o.roaEstadoAfiliado, d.roaOportunidadPago = o.roaOportunidadPago, d.roaFechaRetiro = o.roaFechaRetiro, d.tiempoReinAfi = o.tiempoReinAfi
				when not matched then insert (id, perNumeroIdentificacion, perTipoIdentificacion, roaEstadoAfiliado, roaOportunidadPago, roaFechaRetiro, tiempoReinAfi)
				values (o.id, o.perNumeroIdentificacion, o.perTipoIdentificacion, o.roaEstadoAfiliado, o.roaOportunidadPago, o.roaFechaRetiro, o.tiempoReinAfi)
				when not matched by source then delete;
			end
	end
END