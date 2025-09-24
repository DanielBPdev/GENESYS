-- =============================================
-- Author:      Boris Leon
-- Create Date: 25/04/2024
-- Description: SP para traer los registros de la tabla para la exportacion de los titulos de integracion de clientes
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_consultarDetalleIntegracionTitulosClientesExport]
@direccion varchar(200), 
@integracion varchar(200), 
@identificacion varchar(200), 
@codigoSap varchar(200), 
@fechaInicial varchar(200), 
@fechaFinal varchar(200), 
@estado varchar(200), 
@operacion varchar(200)
AS
BEGIN
    
	declare @select varchar(1500)

	IF @direccion = 'GENESYS-ERP'
	begin

		IF @integracion = 'Personas'
		begin
			set @select ='
				Consecutivo,
				Fecha de ingreso,
				Hora de ingreso,
				Operacion,
				Codigo Sap,
				Codigo Genesys,
				Tipo de documento,
				Numero de documento,
				Primer nombre,
				Segundo Nombre,
				Primer Apellido,
				Segundo Apellido,
				Fecha Nacimiento,
				Estado Civil,
				Dirección,
				Municipio,
				Departamento,
				Telefono,
				Celular,
				Correo Electronico,
				Tipo afiliado,
				Afiliado,
				Categoria,
				Estado Afiliación,
				Genero,
				Autoriza utilización de datos personales,
				Discapacidad,
				Fecha Afiliación,
				Fecha Retiro,
				Fecha formulario,
				Nucleo,
				Fecha de expedicion documento,
				Fecha ingreso a la empresa,
				Grado escolaridad,
				Grupo familiar,
				Beneficiario recibe subsidio,
				Nivel educativo,
				Centro de costo,
				Numero tarjeta,
				Ocupacion,
				% Discapacidad,
				Profesión,
				Rango salarial,
				Sector residencial,
				Tipo contrato,
				Tipo salario,
				Tipo persona,
				Codigo postal,
				Nombre empleador,
				Numero ID empleador,
				Persona en mora,
				Numero de intentos,
				Fecha proceso,
				Hora proceso,
				Estado del registro,
				Observación
			';
		end
		IF @integracion = 'Contactos'
		begin
			set @select = '
				Consecutivo,
				Fecha de ingreso,
				Hora de ingreso,
				Codigo SAP,
				Tipo de  ID,
				Numero ID,
				Nombres,
				Apellidos,
				Tipo contacto,
				Genero,
				Fecha Nacimiento,
				Estado civil,
				Telefono,
				Celular,
				Correo electronico,
				Dirección,
				Municipio,
				Departamento,
				Autorizacion de envio de correo,
				Numero ID Empresa,
				Tipo ID Empresa,
				Codigo Genesys,
				Operación,
				Numero intento,
				Fecha proceso,
				Hora proceso,
				Estado del registro,
				Observación
			';
		end
		IF @integracion = 'Empresas'
		begin
			set @select = '
				Consecutivo,
				Fecha de ingreso,
				Hora de ingreso,
				Operación,
				Codigo SAP,
				Tipo ID Empleador,
				Numero ID Empleador,
				Digito de verificación,
				Nombre empleador,
				Direccion empleador,
				Municipio,
				Departamento,
				Telefono Residencia,
				Celular,
				Correo electronico,
				Tipo de afiliado,
				Afiliado,
				Tarifa servicios,
				Estado Afiliación,
				Sucursal,
				Celular correspondencia ,
				Departamento correspondencia ,
				Direccion correspondencia ,
				Municipio correspondencia ,
				Telefono correspondencia ,
				Empresa en mora,
				Trabajadores Inscritos,
				Solicitud ee paz y salvo,
				Sector ,
				Tipo Cliente,
				Traslado Caja,
				Ultima CCF Procedencia,
				Valor aporte,
				Autoriza utilización de datos personales,
				Fecha constitución,
				Fecha Afiliación,
				Fecha Retiro,
				Fecha formulario,
				Codigo Actividad Economica,
				nucleo,
				Fecha Ingreso,
				Codigo ARL,
				Pagina Web,
				Tipo persona,
				telefono2,
				Autorizacion de envio de correo,
				Codigo postal,
				Numero intento,
				Fecha proceso,
				Hora proceso,
				Estado del registro,
				Observación,
				Codigo Genesys
			';
		end
		IF @integracion = 'Relaciones'
		begin
			set @select = '
				Consecutivo,
				Fecha de ingreso,
				Hora de ingreso,
				Tipo cliente 1,
				Cliente 1,
				Numero de ID Cliente 1 ,
				Codigo de relación,
				Tipo cliente 2,
				Cliente 2,
				Numero de ID Cliente 2,
				Operación,
				nrointentos,
				Fecha proceso,
				Hora proceso,
				Estado del registro,
				Observacion
			';
		end
	end;

	IF @direccion = 'ERP-GENESYS'
	begin
		IF @integracion = 'Personas'
		begin
			set @select = '
				Consecutivo,
				Fecha de ingreso,
				Hora de ingreso,
				Dirección,
				Municipio Residencia,
				Departamento Residencia,
				Telefono Residencia,
				Celular,
				Correo Electronico,
				Codigo SAP,
				Primer nombre,
				Segundo Nombre,
				Primer Apellido,
				Segundo Apellido,
				Tipo de ID ,
				Numero de ID,
				Operación,
				Fecha proceso,
				Hora proceso,
				Estado del registro,
				Observación
			';
		end
		IF @integracion = 'Empresas'
		begin
			set @select = '
				Consecutivo,
				Fecha de ingreso,
				Hora de ingreso,
				Dirección,
				Municipio Residencia,
				Departamento Residencia,
				Telefono Residencia,
				Celular,
				Direccion correspondencia ,
				Municipio correspondencia ,
				Departamento correspondencia ,
				Telefono correspondencia ,
				Celular correspondencia,
				Correo electronico,
				Codigo SAP,
				Numero ID Empleador,
				Digito de verificación,
				Nombre empleador,
				Tipo ID Empleador,
				pagina Web,
				Telefono,
				Fecha proceso,
				Hora proceso,
				Estado del registro,
				Observación,
				Operacion
			';
		end
		IF @integracion = 'Contactos'
		begin
			set @select = '
				Consecutivo,
				Fecha de ingreso,
				Hora de ingreso,
				Operación,
				Numero ID,
				Codigo SAP,
				Tipo ID Contacto,
				Numero ID Contacto,
				Nombres,
				Apellidos,
				Estado civil,
				Departamento,
				Municipio,
				Dirección,
				Telefono,
				Celular,
				Correo electronico,
				Tipo contacto,
				Fecha proceso,
				Hora proceso,
				Estado del registro,
				Observación
			';
		end
	end;

	SELECT @select;

END