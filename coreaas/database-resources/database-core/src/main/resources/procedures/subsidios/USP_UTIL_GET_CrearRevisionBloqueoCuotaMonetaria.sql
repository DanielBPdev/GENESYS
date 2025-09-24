-- =============================================
-- Author:     Oscar Gabriel Ocampo
-- Create Date: 2020/08/10
-- Description: SP para crear la auditoria a las tablas CargueBloqueoCuotaMonetaria y BloqueoBeneficiarioCuotaMonetaria
-- =============================================
CREATE PROCEDURE [dbo].[USP_UTIL_GET_CrearRevisionBloqueoCuotaMonetaria]
(
    -- Add the parameters for the stored procedure here
	@sUsuario VARCHAR(255),
	@idCargueCuotaMonetaria BIGINT
)
AS
BEGIN

	DECLARE
			@millisec BIGINT,
			@iRevision bigInt;


	SELECT @millisec = DATEDIFF_BIG (ms, '1969-12-31 19:00:00', dbo.GetLocalDate())

	INSERT aud.Revision (revNombreUsuario, revTimeStamp) values (@sUsuario, @millisec)

	SELECT @iRevision = SCOPE_IDENTITY()
	
	INSERT aud.RevisionEntidad (reeEntityClassName,reeRevisionType,reeRevision) VALUES ('com.asopagos.entidades.subsidiomonetario.liquidacion.CargueBloqueoCuotaMonetaria',0,@iRevision)
	INSERT aud.RevisionEntidad (reeEntityClassName,reeRevisionType,reeRevision) VALUES ('com.asopagos.entidades.subsidiomonetario.liquidacion.BloqueoBeneficiarioCuotaMonetaria',0,@iRevision)


	INSERT INTO aud.CargueBloqueoCuotaMonetaria_aud (cabId,REV,REVTYPE,cabfechaCarga,cabPeriodoInicio,cabPeriodoFin,cabRadicado)
		SELECT cabId,@iRevision,0,cabfechaCarga,cabPeriodoInicio,cabPeriodoFin,cabRadicado
		FROM CargueBloqueoCuotaMonetaria where cabId= @idCargueCuotaMonetaria

	INSERT INTO aud.BloqueoBeneficiarioCuotaMonetaria_aud (bbcId,REV,REVTYPE,bbcPersona,bbcTipoIdentificacionBeneciario,bbcNumeroIdentificacionBeneficiario,bbcCausalBloqueo,bbcCargueBloqueoCuotaMonetaria,bbcBloqueado,bbcBeneficiario)
		SELECT bbcId,@iRevision,0,bbcPersona,bbcTipoIdentificacionBeneciario,bbcNumeroIdentificacionBeneficiario,bbcCausalBloqueo,bbcCargueBloqueoCuotaMonetaria,bbcBloqueado,bbcBeneficiario
		FROM BloqueoBeneficiarioCuotaMonetaria where bbcCargueBloqueoCuotaMonetaria= @idCargueCuotaMonetaria

  
END;


