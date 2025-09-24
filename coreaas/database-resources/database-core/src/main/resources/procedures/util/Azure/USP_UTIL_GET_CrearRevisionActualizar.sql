-- =============================================
-- Author:		Fabian López
-- Create date: 2020/08/06
-- Description:	SP para registrar los datos de auditoria para procesos de Actualización.
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_UTIL_GET_CrearRevisionActualizar]
	@sEntityClassName VARCHAR(255),
	@iNumeroRegistros BIGINT,
	@sIpUsuario VARCHAR(15) = '',
	@sUsuarioProceso VARCHAR(255),
	@iRevision BIGINT OUTPUT
AS
BEGIN
	--print 'Inicia USP_UTIL_GET_CrearRevisionActualizar'	

	DECLARE @iUltimaRevision BIGINT,
			@millisec BIGINT,
			@contador BIGINT = 1;

	SELECT @millisec = DATEDIFF_BIG (ms, '1969-12-31 19:00:00', dbo.GetLocalDate())

	INSERT aud.Revision (revNombreUsuario, revTimeStamp) values (@sUsuarioProceso, @millisec)

	SELECT @iRevision = SCOPE_IDENTITY()
	
	INSERT aud.RevisionEntidad (reeEntityClassName,reeRevisionType,reeRevision) VALUES (@sEntityClassName,1,@iRevision)

	/*WHILE @contador <= @iNumeroRegistros
	BEGIN
		INSERT aud.RevisionEntidad (reeEntityClassName,reeRevisionType,reeRevision)
		VALUES (@sEntityClassName,0,@iRevision)

		SET @contador = @contador + 1;
	END*/

	--print 'Finaliza USP_UTIL_GET_CrearRevisionActualizar'		
END ;