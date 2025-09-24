CREATE OR ALTER PROCEDURE [sap].[sp_UpdateEmpresaInfoSAP] (
 @codigosap AS varchar (10) = '',
 @tipodoc AS varchar (2) = '',
 @numerodoc AS varchar (16) = '',
 @digverif AS varchar (1) = '',
 @nombreempresa AS varchar (70) = '',
 @direcc AS varchar (60) = '',
 @ciudad AS varchar (40) = '',
 @region AS varchar (3) = '',
 @telefono AS varchar (30) = '',
 @celular AS varchar (30) = '',
 @email AS varchar (241) = '',
 @celcorresp AS varchar (10) = '',
 @dptocorresp AS varchar (3) = '',
 @dircorresp AS varchar (60) = '',
 @muncorresp AS varchar (5) = '',
 @telcorresp AS varchar (7) = '',
 @paginaweb AS varchar (150) = '',
 @telefono2 AS varchar (10) = ''
)
AS
BEGIN
	SET @digverif = ISNULL(@digverif, '');
	SET @codigosap = CAST(@codigosap AS BIGINT);

	INSERT INTO SAP.Empresas_E2G (fecing, horaing, direccion, municipio, departamento, telefono, celular, direccioncorrespondencia,
	            municipiocorrespondencia, departamentocorrespondencia, telefonocorrespondencia, celularcorrespondencia, email,
                codigosap, nitempresa, digverif, nombreempresa, tipoid, pagweb, telefono2, estadoreg, operacion)
	VALUES ([SAP].GetLocalDate(), [SAP].GetLocalDate(), @direcc, @ciudad, @region, @telefono, @celular, @dircorresp, @muncorresp, @dptocorresp, 
	            @telcorresp, @celcorresp, @email, @codigosap, @numerodoc, @digverif, @nombreempresa, @tipodoc, @paginaweb, @telefono2, 
				'P', 'U');
END;