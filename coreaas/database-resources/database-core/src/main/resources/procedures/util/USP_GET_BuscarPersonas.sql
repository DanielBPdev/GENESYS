CREATE PROCEDURE USP_GET_BuscarPersonas
(
	@valorTI VARCHAR(20) = NULL,
	@valorNI VARCHAR(16) = NULL,
	@primerNombre VARCHAR(50) = NULL,
	@primerApellido VARCHAR(50) = NULL,
	@fechaNacimiento BIGINT = NULL, 
	@idEmpleador BIGINT = NULL,
	@segundoNombre VARCHAR(50) = NULL,
	@segundoApellido VARCHAR(50) = NULL,
	@esVista360Web BIT = NULL
)
AS

	IF
;