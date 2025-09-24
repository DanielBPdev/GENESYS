-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/01/18 
-- Description:	
-- =============================================
CREATE OR ALTER TRIGGER [dbo].[TRG_AF_INS_RolAfiliado]
ON [dbo].[RolAfiliado]
AFTER INSERT AS
BEGIN
	SET NOCOUNT ON;
	DECLARE @roaId BIGINT
    DECLARE @afiPersona BIGINT
    DECLARE @emplId BIGINT
    DECLARE @roaAfiliado BIGINT
    DECLARE @roaEstadoAfiliado VARCHAR(8)
    DECLARE @roaTipoAfiliado VARCHAR(100)
    DECLARE @tablaVacia AS TablaIdType;  
    DECLARE @idAfiliado BIGINT;
	declare @roaCanal varchar(50);
	DECLARE @fechaIngreso DATE
    
	SELECT @roaId = roaId,@idAfiliado=roaAfiliado FROM INSERTED

    SELECT @afiPersona = afi.afiPersona,
    		@emplId = empl.empId,
    		@roaAfiliado = roa.roaAfiliado,
            @roaTipoAfiliado = roa.roaTipoAfiliado,
            @roaEstadoAfiliado = roa.roaEstadoAfiliado
    FROM RolAfiliado roa
    INNER JOIN Afiliado afi ON afi.afiId = roa.roaAfiliado
    LEFT JOIN Empleador empl ON empl.empId = roa.roaEmpleador
    LEFT JOIN Empresa emp ON emp.empId = empl.empEmpresa
    WHERE roa.roaId = @roaId

    IF @emplId IS NOT NULL 
    BEGIN        
        EXEC USP_REP_INS_EstadoAfiliacionPersonaEmpresa @afiPersona, @emplId, @tablaVacia      
	END			

    IF @roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'
    BEGIN
        EXEC USP_REP_INS_EstadoAfiliacionPersonaIndependiente @afiPersona, @tablaVacia
    END

    IF @roaTipoAfiliado = 'PENSIONADO'
    BEGIN
        EXEC USP_REP_INS_EstadoAfiliacionPersonaPensionado @afiPersona, @tablaVacia
    END
    
    EXEC USP_REP_INS_EstadoAfiliacionPersonaCaja @afiPersona, @tablaVacia   	

        -- CATEGORIA AFILIADO **********************************************************************************    
    IF @roaEstadoAfiliado IS NOT NULL and @roaCanal not in ('PILA','APORTE_MANUAL')
    BEGIN

		   IF ( SELECT count(*) FROM Afiliado  a 
			inner join RolAfiliado r on r.roaAfiliado=a.afiId
			inner join SolicitudAfiliacionPersona s on s.sapRolAfiliado=r.roaId
			inner join Solicitud so on so.solId=s.sapSolicitudGlobal
			inner join ProductoNoConforme p on p.pncSolicitud=so.solId
			where a.afiId=@idAfiliado and p.pncSubsanable=0)=0 
			BEGIN
			--EXEC USP_REP_CambioCategoriaAfiliado @roaAfiliado
			--EXEC USP_REP_NuevaCategoriaAfiliado @roaAfiliado   
			--== Entra por la nueva forma de calcular la categoria. 
			IF 	(select COUNT(*) from Solicitud s
					inner join SolicitudAfiliacionPersona sf on sf.sapSolicitudGlobal=s.solId
					inner join RolAfiliado r on r.roaId=sf.sapRolAfiliado
					where r.roaId=@roaId
					and (s.solTipoTransaccion='AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION' OR s.solTipoTransaccion='NOVEDAD_REINTEGRO') 
					and  sf.sapEstadoSolicitud = 'PRE_RADICADA' and (@roaEstadoAfiliado='ACTIVO' or @roaEstadoAfiliado is null))=0
					BEGIN

					execute [dbo].[USP_REP_CalcularCategoriaNuevaAfiliacion] @idAfiliado, @emplId, @afiPersona, @roaEstadoAfiliado, @roaId
			
					END
			--execute [dbo].[USP_REP_CalcularCategoriaNuevaAfiliacion] @idAfiliado, @emplId, @afiPersona, @roaEstadoAfiliado, @roaId
			END
    END   

       -- HISTORICO ROLAFILIADO  
	select @roaCanal = roaCanalReingreso,@fechaIngreso=inserted.roaFechaIngreso
	from inserted

	if @roaCanal in ('NOVEDAD_SUS_PATR')
		begin
			INSERT HistoricoRolAfiliado (hraFechaIngreso,hraFechaRetiro,hraTipoAfiliado,hraAfiliado,hraEmpleador,hraCanalRecepcion,hraSolicitud,hraRadicado,hraMotivoDesafiliacion)
			SELECT 
					case when roa.roaFechaIngreso is not null then roa.roaFechaIngreso else roa.roaFechaAfiliacion end,
					--roa.roaFechaAfiliacion,
			        NULL,
			        roa.roaTipoAfiliado,
			        roa.roaAfiliado,
			        roa.roaEmpleador,
			        roa.roaCanalReingreso canalRecepcion,
			        NULL,
			        NULL,
			        NULL
			FROM INSERTED roa
			WHERE roa.roaCanalReingreso IN ('NOVEDAD_SUS_PATR');
		end

		IF @roaCanal in ('PILA','APORTE_MANUAL') and @roaEstadoAfiliado='ACTIVO' and @fechaIngreso is not null-- Reintegros por diferente empleador
    BEGIN 
			
			INSERT HistoricoRolAfiliado (hraFechaIngreso,hraFechaRetiro,hraTipoAfiliado,hraAfiliado,hraEmpleador,hraCanalRecepcion,hraSolicitud,hraRadicado,hraMotivoDesafiliacion)
			SELECT 
					case when roa.roaFechaIngreso is not null then roa.roaFechaIngreso else roa.roaFechaAfiliacion end,
					--roa.roaFechaAfiliacion,
					NULL,
					roa.roaTipoAfiliado,
					roa.roaAfiliado,
					roa.roaEmpleador,
					@roaCanal,
					NULL,
					NULL,
					NULL
			FROM INSERTED roa;

			/*execute [dbo].[USP_REP_CalcularCategoriaNuevaAfiliacion] @roaAfiliado, @emplId, @afiPersona, @roaEstadoAfiliado, @roaId*/
    END
END;