declare @idPlantillaComunicado smallInt;
declare @clave varchar(55);

-----------------Aviso afiliación empresas presencial asignar solicitud empleador-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Aviso afiliación empresas presencial asignar solicitud empleador');

set @clave = '${fechaDelSistema}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave, -- vcoClave
	'dd/mm/aaaa proporcionado por el sistema al generar el comunicado', --vcoDescripcion
	'Fecha del sistema',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'FECHA_LARGA',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${direccionempleador}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Dirección capturada en Información de Ubicación y Correspondencia - Ubicación Oficina Principal',--vcoDescripcion
	'Dirección empleador',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'VARIABLE',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${Ciudad}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,--vcoClave
	'Ciudad capturada en Información de Ubicación y Correspondencia - Ubicación Oficina Principal',--vcoDescripcion
	'Ciudad empleador',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Aviso afiliación empresas presencial tiempo de gestión de la solicitud back-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Aviso afiliación empresas presencial tiempo de gestión de la solicitud back')

set @clave = '${fechaDelSistema}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave, -- vcoClave
	'dd/mm/aaaa proporcionado por el sistema al generar el comunicado', --vcoDescripcion
	'Fecha del sistema',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'FECHA_LARGA',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${direccionempleador}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Dirección capturada en Información de Ubicación y Correspondencia - Ubicación Oficina Principal',--vcoDescripcion
	'Dirección empleador',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'VARIABLE',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${Ciudad}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,--vcoClave
	'Ciudad capturada en Información de Ubicación y Correspondencia - Ubicación Oficina Principal',--vcoDescripcion
	'Ciudad empleador',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Aviso afiliación empresas presencial tiempo de proceso solicitud-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Aviso afiliación empresas presencial tiempo de proceso solicitud')

set @clave = '${fechaDelSistema}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave, -- vcoClave
	'dd/mm/aaaa proporcionado por el sistema al generar el comunicado', --vcoDescripcion
	'Fecha del sistema',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'FECHA_LARGA',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${direccionempleador}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Dirección capturada en Información de Ubicación y Correspondencia - Ubicación Oficina Principal',--vcoDescripcion
	'Dirección empleador',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'VARIABLE',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${Ciudad}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,--vcoClave
	'Ciudad capturada en Información de Ubicación y Correspondencia - Ubicación Oficina Principal',--vcoDescripcion
	'Ciudad empleador',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Aviso de incumplimiento-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Aviso de incumplimiento')
set @clave = '${direccionEmpleador}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Dirección capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Dirección empleador',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'VARIABLE',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Creación de usuario exitosa-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Creación de usuario exitosa')
set @clave = '${nombreYApellidosRepresentanteLegal}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Nombres y Apellidos del representante legal de la empresa',--vcoDescripcion
	'Nombre y Apellidos Representante Legal',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'VARIABLE',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${fechaDelSistema}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave, -- vcoClave
	'dd/mm/aaaa proporcionado por el sistema al generar el comunicado', --vcoDescripcion
	'Fecha del sistema',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'FECHA_LARGA',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${direccionempleador}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Dirección capturada en Información de Ubicación y Correspondencia - Ubicación Oficina Principal',--vcoDescripcion
	'Dirección empleador',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'VARIABLE',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${Ciudad}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,--vcoClave
	'Ciudad capturada en Información de Ubicación y Correspondencia - Ubicación Oficina Principal',--vcoDescripcion
	'Ciudad empleador',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Liquidación de aportes en mora-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Liquidación de aportes en mora')

set @clave = '${fechaDelSistema}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave, -- vcoClave
	'dd/mm/aaaa proporcionado por el sistema al generar el comunicado', --vcoDescripcion
	'Fecha del sistema',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'FECHA_LARGA',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Liquidación de aportes en mora manual-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Liquidación de aportes en mora manual')

set @clave = '${fechaDelSistema}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave, -- vcoClave
	'dd/mm/aaaa proporcionado por el sistema al generar el comunicado', --vcoDescripcion
	'Fecha del sistema',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'FECHA_LARGA',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de dispersión de pagos subsidio fallecimiento al administrador subsidio-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre in ('Notificación de dispersión de pagos subsidio fallecimiento al admin subsidio', 'Notificación de dispersión de pagos subsidio fallecimiento al administrador subsidio'));

set @clave = '${fechaLiquidacion}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Fecha para la que se efectua la liquidación para el aportante',--vcoDescripcion
	'Fecha Liquidación',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'FECHA_LARGA',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de enrolamiento afiliación empleador web-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de enrolamiento afiliación empleador web')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Liquidación de aportes en mora manual-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de enrolamiento afiliación independiente web')

set @clave = '${fechaDelSistema}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave, -- vcoClave
	'dd/mm/aaaa proporcionado por el sistema al generar el comunicado', --vcoDescripcion
	'Fecha del sistema',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'FECHA_LARGA',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturado de Datos de ubicación',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de enrolamiento afiliación pensionado web-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de enrolamiento afiliación pensionado web')

set @clave = '${fechaDelSistema}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave, -- vcoClave
	'dd/mm/aaaa proporcionado por el sistema al generar el comunicado', --vcoDescripcion
	'Fecha del sistema',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'FECHA_LARGA',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturado de Datos de ubicación',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de pago de aportes a dependientes – a nivel de aportante-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de pago de aportes a dependientes – a nivel de aportante')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturado de Datos de ubicación',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${departamentoafiliado}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Departamento de ubicación del afiliado principal',--vcoDescripcion
	'Departamento afiliado',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de pago de aportes a dependientes – a nivel de aportante-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de pago de aportes a dependientes – a nivel de aportante y cotizante')

set @clave = '${tipoDeIdentificacion}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Tipo de identificación del aportante que realizó la solicitud',--vcoDescripcion
	'Tipo de identificación',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'VARIABLE',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${numeroIdentificacion}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Número de identificación del aportante que realizó la solicitud',--vcoDescripcion
	'Número identificación ',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'VARIABLE',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${tipoDeIdentificacion}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Tipo de identificación del aportante que realizó la solicitud',--vcoDescripcion
	'Tipo de identificación',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'VARIABLE',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${numeroIdentificacion}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Número de identificación del aportante que realizó la solicitud',--vcoDescripcion
	'Número identificación ',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'VARIABLE',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de radicación solicitud de afiliación empleador web-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de radicación solicitud de afiliación empleador web')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 1 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 1 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${tipoSolicitante}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Tipo de solicitante objeto de la solicitud',--vcoDescripcion
	'Tipo solicitante',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'VARIABLE',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 1 - Independiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 1 - Independiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${tipoSolicitante}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Tipo de solicitante objeto de la solicitud',--vcoDescripcion
	'Tipo solicitante',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'VARIABLE',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 1 - Pensionado-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 1 - Pensionado')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${tipoSolicitante}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Tipo de solicitante objeto de la solicitud',--vcoDescripcion
	'Tipo solicitante',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'VARIABLE',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 10 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 10 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${tipoSolicitante}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Tipo de solicitante objeto de la solicitud',--vcoDescripcion
	'Tipo solicitante',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'VARIABLE',--vcoTipoVariableComunicado
	0);--vcoOrden
end


-----------------Notificación de recaudo de aportes PILA 11 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 11 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 12 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 12 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 13 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 13 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 14 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 14 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 15 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 15 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 16 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 16 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 16 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 17 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 18 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 18 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 19 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 19 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 2 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 2 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 2 - Independiente -----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 2 - Independiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 2 - Pensionado -----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 2 - Pensionado')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 20 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 20 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 20 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 21 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 21 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 21 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 22 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 22 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 22 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 22 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 24 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 24 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 3 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 3 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 3 - Independiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 3 - Independiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 3 - Independiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 3 - Independiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 3 - Pensionado-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 3 - Pensionado')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 4 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 4 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 4 - Independiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 4 - Independiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 4 - Pensionado-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 4 - Pensionado')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 5 - Dependiente -----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 5 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 5 - Pensionado -----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 5 - Pensionado')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 6 - Dependiente -----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 6 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 6 - Independiente -----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 6 - Independiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 6 - Pensionado -----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 6 - Pensionado')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 7 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 7 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${Ciudad}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,--vcoClave
	'Ciudad capturada en Información de Ubicación y Correspondencia - Ubicación Oficina Principal',--vcoDescripcion
	'Ciudad empleador',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 7 - Independiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 7 - Independiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${Ciudad}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,--vcoClave
	'Ciudad capturada en Información de Ubicación y Correspondencia - Ubicación Oficina Principal',--vcoDescripcion
	'Ciudad empleador',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 7 - Pensionado-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 7 - Pensionado')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${Ciudad}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,--vcoClave
	'Ciudad capturada en Información de Ubicación y Correspondencia - Ubicación Oficina Principal',--vcoDescripcion
	'Ciudad empleador',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 8 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 8 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${Ciudad}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,--vcoClave
	'Ciudad capturada en Información de Ubicación y Correspondencia - Ubicación Oficina Principal',--vcoDescripcion
	'Ciudad empleador',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 8 - Independiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 8 - Independiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${Ciudad}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,--vcoClave
	'Ciudad capturada en Información de Ubicación y Correspondencia - Ubicación Oficina Principal',--vcoDescripcion
	'Ciudad empleador',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 8 - Pensionado-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 8 - Pensionado')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${Ciudad}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,--vcoClave
	'Ciudad capturada en Información de Ubicación y Correspondencia - Ubicación Oficina Principal',--vcoDescripcion
	'Ciudad empleador',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 9 - Dependiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 9 - Dependiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${Ciudad}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,--vcoClave
	'Ciudad capturada en Información de Ubicación y Correspondencia - Ubicación Oficina Principal',--vcoDescripcion
	'Ciudad empleador',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 9 - Independiente-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 9 - Independiente')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${Ciudad}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,--vcoClave
	'Ciudad capturada en Información de Ubicación y Correspondencia - Ubicación Oficina Principal',--vcoDescripcion
	'Ciudad empleador',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación de recaudo de aportes PILA 9 - Pensionado-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación de recaudo de aportes PILA 9 - Pensionado')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${Ciudad}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,--vcoClave
	'Ciudad capturada en Información de Ubicación y Correspondencia - Ubicación Oficina Principal',--vcoDescripcion
	'Ciudad empleador',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Notificación para requerir subsanación de solicitud de afiliación de empleador-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Notificación para requerir subsanación de solicitud de afiliación de empleador')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanado-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanado')

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturada en Información de ubicación y correspondencia del empleador',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

-----------------Recordatorio plazo límite pago aportes personas-----------------
set @idPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoNombre = 'Recordatorio plazo límite pago aportes personas')

set @clave = '${tipoIdentificacion}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Tipo de identificacion de la persona',--vcoDescripcion
	'Tipo de identificacion',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'VARIABLE',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${numeroIdentificacion}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Número Identificación',--vcoDescripcion
	'Número identificación',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'VARIABLE',--vcoTipoVariableComunicado
	0);--vcoOrden
end

set @clave = '${municipio}';
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave=@clave)
begin
	insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
	values(@clave,-- vcoClave
	'Municipio capturado de Datos de ubicación',--vcoDescripcion
	'Municipio',--vcoNombre
	@idPlantillaComunicado,--vcoPlantillaComunicado
	'',--vcoNombreConstante
	'LUGAR_MAYUS',--vcoTipoVariableComunicado
	0);--vcoOrden
end

if exists(select * from ValidacionProceso
	where vapBloque = 'CAMBIO_CLASE_DE_INDEPENDIENTE_PRESENCIAL'
	and vapValidacion = 'VALIDACION_PERSONA_PENSIONADO_ACTIVO'
	and vapEstadoProceso = 'ACTIVO')
begin
	update ValidacionProceso
	set vapEstadoProceso = 'INACTIVO'
	where vapBloque = 'CAMBIO_CLASE_DE_INDEPENDIENTE_PRESENCIAL'
	and vapValidacion = 'VALIDACION_PERSONA_PENSIONADO_ACTIVO'
end

