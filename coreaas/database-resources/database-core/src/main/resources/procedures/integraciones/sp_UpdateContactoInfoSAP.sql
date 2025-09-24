CREATE OR ALTER PROCEDURE [sap].[sp_UpdateContactoInfoSAP] (
	@codigosap AS varchar (10),
	@tipodoc AS varchar (2),
	@numerodoc AS varchar (16),
	@nombrecompleto AS varchar (100) = '',
	@nombrepila AS varchar (50) = '',
	@estadocivil AS varchar (1) = '',
	@telefono AS varchar (16) = '',
	@celular AS varchar (10) = '',
	@email AS varchar (200) = '',
	@direcc AS varchar (60) = '',
	@ciudad AS varchar (5) = '',
	@dpto AS varchar (5) = '',
	@operacion AS varchar(01),
	@nit AS varchar(16),
	@tipocontacto AS varchar(03)
)
AS
BEGIN
	SET @codigosap = CAST(@codigosap AS BIGINT)
	INSERT INTO SAP.Contactos_E2G(fecing, horaing, operacion, nit, codigosap, tipoidcontacto, cedulacontacto, nombres, apellidos,
                estcivil, departamento, municipio, direccion, telefono, telefonomovil, mail, TipoContacto, estadoreg)
	VALUES ([SAP].GetLocalDate(), [SAP].GetLocalDate(), @operacion, @nit, @codigosap, @tipodoc, @numerodoc, @nombrecompleto, @nombrepila,
	            @estadocivil, @dpto, @ciudad, @direcc, @telefono, @celular, @email, @tipocontacto, 'P');
END;