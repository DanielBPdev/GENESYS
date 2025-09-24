-- =============================================
-- Author:		Diego Suesca
-- Create date: 2018/05/29
-- Description:	
-- =============================================
CREATE PROCEDURE [dbo].[USP_UTIL_GET_CrearRevision]
	@sEntityClassName VARCHAR(255),
	@iNumeroRegistros BIGINT,
	@sIpUsuario VARCHAR(15) = '',
	@sUsuarioProceso VARCHAR(255) = 'USUARIO_SISTEMA',
	@iRevision BIGINT OUTPUT
AS
BEGIN
SET NOCOUNT ON;
--	print 'Inicia USP_UTIL_GET_CrearRevision'	

	DECLARE @iUltimaRevision BIGINT,
			@millisec BIGINT,
			@contador BIGINT = 1;

	SELECT @millisec = DATEDIFF_BIG (ms, '1969-12-31 19:00:00', dbo.GetLocalDate())

	INSERT aud.Revision (revTimeStamp, revNombreUsuario) values (@millisec, @sUsuarioProceso)

	SELECT @iRevision = SCOPE_IDENTITY()
	
	INSERT aud.RevisionEntidad (reeEntityClassName,reeRevisionType,reeRevision, reeTimeStamp) VALUES (@sEntityClassName,0,@iRevision, @millisec)

	/*WHILE @contador <= @iNumeroRegistros
	BEGIN
		INSERT aud.RevisionEntidad (reeEntityClassName,reeRevisionType,reeRevision)
		VALUES (@sEntityClassName,0,@iRevision)

		SET @contador = @contador + 1;
	END*/

--	print 'Finaliza USP_UTIL_GET_CrearRevision'		
END ; 