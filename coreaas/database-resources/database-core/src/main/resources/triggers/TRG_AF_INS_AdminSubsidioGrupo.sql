-- =============================================
-- Author:      Diego Suesca
-- Create date: 2019/01/18 
-- Description: 
-- =============================================
CREATE TRIGGER TRG_AF_INS_AdminSubsidioGrupo
ON AdminSubsidioGrupo
AFTER INSERT AS
BEGIN
    SET NOCOUNT ON;
    
	
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
    WHERE NOT EXISTS (SELECT 1
                        FROM HistoricoAdminsubsidioGrupo
                        WHERE hasId = (SELECT MAX(hasId)
                                        FROM HistoricoAdminsubsidioGrupo
                                        WHERE hasTipoIdentificacionAdmin = per.perTipoIdentificacion
                                          AND hasNumeroIdentificacionAdmin = per.perNumeroIdentificacion)
                          AND hasGrupoFamiliar = ins.asgGrupoFamiliar
                        )
	
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
        WHERE EXISTS (SELECT 1
                        FROM HistoricoAdminsubsidioGrupo
                        WHERE hasId = (SELECT MAX(hasId)
                                        FROM HistoricoAdminsubsidioGrupo
                                        WHERE hasTipoIdentificacionAdmin = per.perTipoIdentificacion
                                          AND hasNumeroIdentificacionAdmin = per.perNumeroIdentificacion
										  AND hasMedioDePago != ins.asgMedioDePago)
                          AND hasGrupoFamiliar = ins.asgGrupoFamiliar
                        )
END