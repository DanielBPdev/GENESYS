--liquibase formatted sql

--changeset abaquero:01
--comment: Creación de campo de referencia al registro inicial

alter table staging.RegistroDetallado add redOUTRegInicial bigint

--changeset abaquero:02
--comment: Actualización de marcas de registro inicial en aporte PILA

declare @registrosOriginalesPila as table (
	regId bigint, 
	redRegistroControl bigint, 
	redId bigint
)

insert into @registrosOriginalesPila (regId, redRegistroControl, redId)
select reg.regId, red.redRegistroControl, min(red.redId)
from staging.RegistroGeneral reg
inner join staging.RegistroDetallado red on red.redRegistroGeneral = reg.regId
where red.redRegistroControl is not null
group by reg.regId, red.redRegistroControl

update red
	set red.redOUTRegInicial = reo.redId
from staging.RegistroDetallado red
inner join @registrosOriginalesPila reo on 
	red.redRegistroGeneral = reo.regId 
	and red.redRegistroControl = reo.redRegistroControl
	and red.redId != reo.redId

--changeset abaquero:03
--comment: Actualización de marcas de registro inicial en aporte manual

declare @registrosOriginalesManual as table (
	regId bigint, 
	redTipoIdentificacionCotizante varchar(20), 
	redNumeroIdentificacionCotizante varchar(16), 
	redId bigint
)

insert into @registrosOriginalesManual (regId, redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante, redId)
select reg.regId, red.redTipoIdentificacionCotizante, red.redNumeroIdentificacionCotizante, min(red.redId)
from staging.RegistroGeneral reg
inner join staging.RegistroDetallado red on red.redRegistroGeneral = reg.regId
where red.redRegistroControl is null
and reg.regRegistroControl is null
group by reg.regId, red.redTipoIdentificacionCotizante, red.redNumeroIdentificacionCotizante

update red
	set red.redOUTRegInicial = reo.redId
from staging.RegistroDetallado red
inner join @registrosOriginalesManual reo on 
	red.redRegistroGeneral = reo.regId 
	and red.redTipoIdentificacionCotizante = reo.redTipoIdentificacionCotizante
	and red.redNumeroIdentificacionCotizante = reo.redNumeroIdentificacionCotizante
	and red.redId != reo.redId