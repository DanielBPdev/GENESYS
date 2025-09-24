-- =============================================
-- Author:  Robinson Castillo
-- Create Date: 2022-02-16
-- Description: Actualizar tabla cotFechaIngreso, para proceso de novedades. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[ASP_COTFECHAINGRESO]
AS
BEGIN
    SET NOCOUNT ON
	begin
		if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA = 'dbo' and TABLE_NAME = 'cotFechaIngreso')
			begin
				CREATE TABLE [dbo].[cotFechaIngreso]([id] [bigint] NOT NULL,[perNumeroIdentificacionCot] [varchar](30) NULL,[perTipoIdentificacionCot] [varchar](30) NULL,
				[roaEstadoAfiliado] [varchar](20) NULL,[perNumeroIdentificacionAport] [varchar](30) NULL,[roaFechaIngreso] [date] NULL, [roaFechaRetiro] [date] null)
				create clustered index numId on dbo.cotFechaIngreso (perNumeroIdentificacionCot,perNumeroIdentificacionAport)
				merge dbo.cotFechaIngreso as d
				using core.cotFechaIngreso as o on o.id = d.id
				when matched then update set d.perNumeroIdentificacionCot = o.perNumeroIdentificacionCot, d.perTipoIdentificacionCot = o.perTipoIdentificacionCot,
				d.roaEstadoAfiliado = o.roaEstadoAfiliado, d.perNumeroIdentificacionAport = o.perNumeroIdentificacionAport, d.roaFechaIngreso = o.roaFechaIngreso, d.roaFechaRetiro = o.roaFechaRetiro
				when not matched then insert (id, perNumeroIdentificacionCot, perTipoIdentificacionCot, roaEstadoAfiliado, perNumeroIdentificacionAport, roaFechaIngreso, roaFechaRetiro)
				values (o.id, o.perNumeroIdentificacionCot, o.perTipoIdentificacionCot, o.roaEstadoAfiliado, o.perNumeroIdentificacionAport, o.roaFechaIngreso, o.roaFechaRetiro)
				when not matched by source then delete;
			end
		else
			begin
				merge dbo.cotFechaIngreso as d
				using core.cotFechaIngreso as o on o.id = d.id
				when matched then update set d.perNumeroIdentificacionCot = o.perNumeroIdentificacionCot, d.perTipoIdentificacionCot = o.perTipoIdentificacionCot,
				d.roaEstadoAfiliado = o.roaEstadoAfiliado, d.perNumeroIdentificacionAport = o.perNumeroIdentificacionAport, d.roaFechaIngreso = o.roaFechaIngreso, d.roaFechaRetiro = o.roaFechaRetiro
				when not matched then insert (id, perNumeroIdentificacionCot, perTipoIdentificacionCot, roaEstadoAfiliado, perNumeroIdentificacionAport, roaFechaIngreso, roaFechaRetiro)
				values (o.id, o.perNumeroIdentificacionCot, o.perTipoIdentificacionCot, o.roaEstadoAfiliado, o.perNumeroIdentificacionAport, o.roaFechaIngreso, roaFechaRetiro)
				when not matched by source then delete;
			end
	end
END