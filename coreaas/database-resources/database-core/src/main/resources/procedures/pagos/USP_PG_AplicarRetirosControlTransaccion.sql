-- =============================================
-- Author: rcastillo
-- Create Date: 2024-12-09
-- Description: Procedimiento encargado de controlar la transacción por 
-- =============================================
create or alter procedure [dbo].[USP_PG_AplicarRetirosControlTransaccion] (@idAmin bigInt, @num bigInt output)
as
begin

    set nocount on;
	set xact_abort on;

		begin try

			declare @numId bigInt
			
			begin transaction
				
				if not exists (select 1 from dbo.transaccionTP where idAdministrador = @idAmin)
					begin
						insert dbo.transaccionTP (idAdministrador, id)
						values (@idAmin, 0)
					end
			
				update dbo.transaccionTP set @numId = id = id + 1 where idAdministrador = @idAmin
			
				if not exists (select 1 from dbo.transaccionTP2 where idAdministrador = @idAmin)
					begin
						insert dbo.transaccionTP2 (idAdministrador, id, operacion)
						values (@idAmin, @numId, 'P')
					end
				else
					begin
						update dbo.transaccionTP2 set id = @numId, operacion = 'P' where idAdministrador = @idAmin
					end
			
			
			select @num = @numId

			commit transaction

		end try
		begin catch
			
			if (XACT_STATE()) = -1  
			begin 
			    rollback transaction;
			end;  

			if (XACT_STATE()) = 1  
			begin
			    commit transaction;     
			end; 
		end catch;

end;