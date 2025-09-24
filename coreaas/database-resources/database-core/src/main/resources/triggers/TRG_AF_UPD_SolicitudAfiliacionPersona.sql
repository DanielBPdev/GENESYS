-- =============================================
-- Author:      Diego Suesca
-- Create date: 2019/01/18 
-- Description: 
-- =============================================
CREATE  OR ALTER TRIGGER TRG_AF_UPD_SolicitudAfiliacionPersona
ON [dbo].[SolicitudAfiliacionPersona]
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON; 
    DECLARE @afiId BIGINT
    DECLARE @sapRolAfiliado BIGINT  
    DECLARE @sapEstadoSolicitudaAfi VARCHAR(50)
	DECLARE @sapEstadoSolicitudAfiliadoAntes VARCHAR(50)
	DECLARE @afiPersona BIGINT
	DECLARE @emplId BIGINT
	DECLARE @idSolicitud BIGINT
	DECLARE @roaEstadoAfiliado VARCHAR(8)

    IF UPDATE (sapEstadoSolicitud)
    BEGIN
        SELECT @sapRolAfiliado = sapRolAfiliado,
                @sapEstadoSolicitudaAfi = sapEstadoSolicitud,
				@idSolicitud=sapSolicitudGlobal
        FROM INSERTED
        -- CATEGORIA AFILIADO **********************************************************************************    
		SELECT @sapEstadoSolicitudAfiliadoAntes=d.sapEstadoSolicitud FROM DELETED d

        SELECT @afiId = roa.roaAfiliado
        FROM RolAfiliado roa
        WHERE roa.roaId = @sapRolAfiliado
			IF @sapEstadoSolicitudaAfi='CERRADA' AND @sapEstadoSolicitudAfiliadoAntes='APROBADA' AND ( SELECT count(*) FROM Afiliado  a 
			inner join RolAfiliado r on r.roaAfiliado=a.afiId
			inner join SolicitudAfiliacionPersona s on s.sapRolAfiliado=r.roaId
			inner join Solicitud so on so.solId=s.sapSolicitudGlobal
			inner join ProductoNoConforme p on p.pncSolicitud=so.solId
			where a.afiId=@afiId and p.pncSubsanable=0)=0 

			BEGIN
			SELECT @afiPersona = afi.afiPersona, @emplId=roa.roaEmpleador,@roaEstadoAfiliado=roa.roaEstadoAfiliado
			FROM RolAfiliado roa
			INNER JOIN Afiliado afi ON afi.afiId = roa.roaAfiliado
			WHERE roa.roaId = @sapRolAfiliado
					--EXEC USP_REP_CambioCategoriaAfiliado @roaAfiliado
					--EXEC USP_REP_NuevaCategoriaAfiliado @roaAfiliado
					--== Entra por la nueva forma de calcular la categoria. 
					execute [dbo].[USP_REP_CalcularCategoriaNuevaAfiliacion] @afiId, @emplId, @afiPersona, @roaEstadoAfiliado, @sapRolAfiliado
			   
				--EXEC USP_REP_CambioCategoriaAfiliado @afiId
				--EXEC USP_REP_NuevaCategoriaAfiliado @afiId

        ---- HISTORICO ROLAFILIADO 
			INSERT HistoricoRolAfiliado (hraFechaIngreso,hraFechaRetiro,hraTipoAfiliado,hraAfiliado,hraEmpleador,hraCanalRecepcion,hraSolicitud,hraRadicado,hraMotivoDesafiliacion)
			SELECT 
							CASE WHEN roa.roaFechaAfiliacion IS NOT NULL THEN roa.roaFechaAfiliacion ELSE dbo.GetlocalDate() END,
				--CASE WHEN roa.roaFechaAfiliacion IS NOT NULL THEN roa.roaFechaAfiliacion ELSE dbo.GetlocalDate() END,
				NULL,
				roa.roaTipoAfiliado,
				roa.roaAfiliado,
				roa.roaEmpleador,
				sol.solCanalRecepcion,
				sol.solId,
				sol.solNumeroRadicacion,
				NULL
			FROM INSERTED sap 
			JOIN Solicitud sol on sap.sapSolicitudGlobal = sol.solId 
			JOIN RolAfiliado roa on sap.sapRolAfiliado = roa.roaId
			WHERE sap.sapEstadoSolicitud IN ('CERRADA')

		END
    END 
END