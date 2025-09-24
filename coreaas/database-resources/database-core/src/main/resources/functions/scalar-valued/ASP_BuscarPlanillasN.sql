--IF (OBJECT_ID('ASP_BuscarPlanillasN]') IS NOT NULL)
--	DROP FUNCTION [dbo].[ASP_BuscarPlanillasN]


create or alter function [dbo].[ASP_BuscarPlanillasN] 
(@planillaAntes bigInt)
returns varchar(max)
as
begin
declare @planillaN varchar(50)
declare @resultado varchar(max)
declare @planillaN1 varchar(50)

select distinct @planillaN=planillaN 
from dbo.aporteDetalladoRegistroControlN with (nolock)
where planillaAntes = @planillaAntes

select distinct  @planillaN1=planillaN 
from dbo.aporteDetalladoRegistroControlN with (nolock)
where planillaAntes = @planillaN

select @resultado=STRING_AGG(planillaN, '\') 
from (
    select distinct planillaN 
    from dbo.aporteDetalladoRegistroControlN 
    where planillaAntes = @planillaN
	union
	select distinct planillaN 
    from dbo.aporteDetalladoRegistroControlN 
    where planillaAntes = @planillaN1
  ) as Subconsulta
return @planillaN + '\' +  @resultado
end