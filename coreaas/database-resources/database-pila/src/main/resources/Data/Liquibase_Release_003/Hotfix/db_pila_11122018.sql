--liquibase formatted sql

--changeset abaquero:01
--comment: Actualización de las referencias a RegistroDetalladoNovedad en los registros existentes de TemNovedad para correcto funcionamiento del ESB
update ten
	set ten.tenRegistroDetalladoNovedad = rdn.rdnId 
from dbo.TemNovedad ten
inner join staging.RegistroGeneral reg on ten.tenRegistroGeneral = reg.regId
inner join staging.RegistroDetallado red on ten.tenRegistroDetallado = red.redId and ten.tenRegistroGeneral = red.redRegistroGeneral and red.redRegistroGeneral = reg.regId
inner join staging.RegistroDetalladoNovedad rdn on ten.tenRegistroDetallado = rdn.rdnRegistroDetallado and rdn.rdnRegistroDetallado = red.redId
	and ((isnull(ten.tenEsIngreso, 0) = 1 and rdn.rdnTipoNovedad = 'NOVEDAD_ING')
		or (isnull(ten.tenEsRetiro, 0) = 1 and rdn.rdnTipoNovedad = 'NOVEDAD_RET')
		or ten.tenTipoTransaccion = rdn.rdnTipotransaccion 
	)
where reg.regRegistroControl is not null