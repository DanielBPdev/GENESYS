declare @exists INT;

select @exists = COUNT(*)
from parametrizacionejecucionprogramada
where pepproceso = 'CAMBIO_AUTOMATICO_CATEGORIA_BENEFICIARIO_CIRCULAR_UNICA_II';

if @exists > 0
begin
update parametrizacionejecucionprogramada
			set pepproceso = 'CAMBIO_AUTOMATICO_CATEGORIA_BENEFICIARIO_CIRCULAR_UNICA_ENTRE_X_EDAD'
			where pepproceso = 'CAMBIO_AUTOMATICO_CATEGORIA_BENEFICIARIO_CIRCULAR_UNICA_II'
end