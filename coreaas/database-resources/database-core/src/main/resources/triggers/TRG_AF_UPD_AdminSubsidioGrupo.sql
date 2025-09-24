-- =============================================
-- Author:      Diego Suesca
-- Create date: 2019/01/18 
-- Description: 
-- =============================================
CREATE TRIGGER TRG_AF_UPD_AdminSubsidioGrupo
ON AdminSubsidioGrupo
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;   

    DECLARE @asgGrupoFamiliar BIGINT,
            @tipoIdentificacionAdminOLD varchar(20),
            @numeroIdentificacionAdminOLD varchar(16),
            @tipoIdentificacionAdminNEW varchar(20),
            @numeroIdentificacionAdminNEW varchar(16),
            @idMedioDePago BIGINT

     IF UPDATE (asgMedioPagoActivo) OR UPDATE (asgAdministradorSubsidio)
    BEGIN
	    SELECT @asgGrupoFamiliar = asgGrupoFamiliar FROM INSERTED
	    
	    SELECT @tipoIdentificacionAdminNEW = per.perTipoIdentificacion,
                @numeroIdentificacionAdminNEW = per.perNumeroIdentificacion
        FROM INSERTED ins 
        INNER JOIN AdministradorSubsidio asu ON asu.asuId = ins.asgAdministradorSubsidio
        INNER JOIN Persona per ON per.perId = asu.asuPersona
	    
	    SELECT @tipoIdentificacionAdminOLD = per.perTipoIdentificacion,
                @numeroIdentificacionAdminOLD = per.perNumeroIdentificacion
        FROM DELETED del 
        INNER JOIN AdministradorSubsidio asu ON asu.asuId = del.asgAdministradorSubsidio
        INNER JOIN Persona per ON per.perId = asu.asuPersona
        
        UPDATE has 
        SET has.hasFechaFin = dbo.GetLocalDate()
        FROM HistoricoAdminsubsidioGrupo has
        WHERE has.hasMedioDePago IN ( SELECT ins.asgMedioDePago
        FROM INSERTED ins 
        INNER JOIN AdministradorSubsidio asu ON asu.asuId = ins.asgAdministradorSubsidio
        INNER JOIN Persona per ON per.perId = asu.asuPersona
        WHERE ins.asgMedioPagoActivo = 0
			AND  hasGrupoFamiliar = @asgGrupoFamiliar
			AND hasTipoIdentificacionAdmin = @tipoIdentificacionAdminOLD
			AND hasNumeroIdentificacionAdmin = @numeroIdentificacionAdminOLD)
			
		INSERT HistoricoAdminsubsidioGrupo (
            hasGrupoFamiliar,
            hasMedioDePago,
            hasTipoIdentificacionAdmin,
            hasNumeroIdentificacionAdmin,
            hasFechaInicio,
            hasFechaFin)
        SELECT ins.asgGrupoFamiliar, 
                ins.asgMedioDePago,
                per.perTipoIdentificacion,
                per.perNumeroIdentificacion,
                dbo.GetLocalDate(),
                null
        FROM INSERTED ins 
        INNER JOIN AdministradorSubsidio asu ON asu.asuId = ins.asgAdministradorSubsidio
        INNER JOIN Persona per ON per.perId = asu.asuPersona
        WHERE ins.asgMedioPagoActivo = 1
    END 
END
