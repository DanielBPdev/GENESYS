-- =============================================
-- Author:     Oscar Gabriel Ocampo
-- Create Date: 2020/08/10
-- Description: SP para crear la auditoria a las tablas CargueBloqueoCuotaMonetaria y BloqueoBeneficiarioCuotaMonetaria
-- =============================================
CREATE PROCEDURE [dbo].[USP_UTIL_GET_CrearRevisionDesbloqueoCuotaMonetaria]
(
    -- Add the parameters for the stored procedure here
	@sUsuario VARCHAR(255),
	@idBloqueo BIGINT	
)
AS
BEGIN

	DECLARE
			@millisec BIGINT,
			@iRevision bigInt;


	SELECT @millisec = DATEDIFF_BIG (ms, '1969-12-31 19:00:00', dbo.GetLocalDate())

	INSERT aud.Revision (revNombreUsuario, revTimeStamp) values (@sUsuario, @millisec)

	SELECT @iRevision = SCOPE_IDENTITY()
		
	INSERT aud.RevisionEntidad (reeEntityClassName,reeRevisionType,reeRevision) VALUES ('com.asopagos.entidades.subsidiomonetario.liquidacion.BloqueoBeneficiarioCuotaMonetaria',1,@iRevision)

	INSERT INTO aud.BloqueoBeneficiarioCuotaMonetaria_aud (bbcId,REV,REVTYPE,bbcPersona,bbcTipoIdentificacionBeneciario,bbcNumeroIdentificacionBeneficiario,bbcCausalBloqueo,bbcCargueBloqueoCuotaMonetaria,bbcBloqueado,bbcBeneficiario,bbcAsignacionCuotaPorOtraCCF,bbcBeneficiarioComoAfiliadoOtraCCF)
		SELECT bbcId,@iRevision,1,bbcPersona,bbcTipoIdentificacionBeneciario,bbcNumeroIdentificacionBeneficiario,bbcCausalBloqueo,bbcCargueBloqueoCuotaMonetaria,0,bbcBeneficiario,bbcAsignacionCuotaPorOtraCCF,bbcBeneficiarioComoAfiliadoOtraCCF
		FROM BloqueoBeneficiarioCuotaMonetaria
	WHERE bbcId= @idBloqueo  
END;


