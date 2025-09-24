-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/01/18 
-- Description:	
-- =============================================
CREATE OR ALTER TRIGGER [dbo].[TRG_AF_UPD_RolAfiliado]
    ON [dbo].[RolAfiliado]
    AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @roaId BIGINT
    DECLARE @roaAfiliado BIGINT
    DECLARE @afiPersona BIGINT
    DECLARE @emplId BIGINT
    declare @idAfiliado bigInt
    DECLARE @roaTipoAfiliado VARCHAR(100)
    DECLARE @roaEstadoAfiliado VARCHAR(8)
    DECLARE @roaEstadoAfiliadoDel VARCHAR(8)
    DECLARE @motivoDesafiliacion VARCHAR(50)
    DECLARE @tablaVacia AS TablaIdType;
    DECLARE @solCanalRecepcionRolAfiliado as VARCHAR(21)
    DECLARE @solCanalRecepcion as VARCHAR(21)

    SELECT @roaId = roaId,
           @roaAfiliado = roaAfiliado,
           @roaTipoAfiliado = roaTipoAfiliado,
           @roaEstadoAfiliado = roaEstadoAfiliado,
           @motivoDesafiliacion = roaMotivoDesafiliacion,
           @idAfiliado = roaAfiliado,
           @solCanalRecepcionRolAfiliado = roaCanalReingreso
    FROM INSERTED


    select @solCanalRecepcion = solCanalRecepcion
    from Solicitud
    where solId = (SELECT  max(sapSolicitudGlobal) from SolicitudAfiliacionPersona
                   where sapRolAfiliado = @roaId)




    SELECT @roaEstadoAfiliadoDel = roaEstadoAfiliado
    FROM DELETED

    IF UPDATE (roaAfiliado) OR UPDATE (roaFechaRetiro) OR UPDATE (roaEstadoAfiliado) OR UPDATE (roaTipoAfiliado) OR UPDATE(roaEmpleador)
        BEGIN
            SELECT @afiPersona = afi.afiPersona
            FROM RolAfiliado roa
                     INNER JOIN Afiliado afi ON afi.afiId = roa.roaAfiliado
            WHERE roa.roaId = @roaId
        END

    IF UPDATE (roaAfiliado) OR UPDATE (roaEmpleador) OR UPDATE (roaFechaRetiro) OR UPDATE (roaEstadoAfiliado)
        BEGIN
            SELECT @emplId = empl.empId
            FROM RolAfiliado roa
                     LEFT JOIN Empleador empl ON empl.empId = roa.roaEmpleador
                     LEFT JOIN Empresa emp ON emp.empId = empl.empEmpresa
            WHERE roa.roaId = @roaId

            IF @emplId IS NOT NULL
                BEGIN

                    EXEC USP_REP_INS_EstadoAfiliacionPersonaEmpresa @afiPersona, @emplId, @tablaVacia
                END
        END

    IF UPDATE(roaTipoAfiliado) OR UPDATE (roaEstadoAfiliado) OR UPDATE (roaAfiliado) OR UPDATE (roaEmpleador)
        BEGIN
            IF @roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'
                BEGIN
                    EXEC USP_REP_INS_EstadoAfiliacionPersonaIndependiente @afiPersona, @tablaVacia
                END

            IF @roaTipoAfiliado = 'PENSIONADO'
                BEGIN
                    EXEC USP_REP_INS_EstadoAfiliacionPersonaPensionado @afiPersona, @tablaVacia
                END
        END

    IF UPDATE (roaEstadoAfiliado) OR UPDATE (roaAfiliado) OR UPDATE (roaFechaRetiro)
        BEGIN
            EXEC USP_REP_INS_EstadoAfiliacionPersonaCaja @afiPersona, @tablaVacia
        END

    declare @TempCantidadVecesActivo int=0;
    IF @roaEstadoAfiliado='INACTIVO' 
        BEGIN
            select @TempCantidadVecesActivo=count(*) from RolAfiliado r with (nolock)
            where r.roaAfiliado=@roaAfiliado AND r.roaTipoAfiliado='TRABAJADOR_DEPENDIENTE' and r.roaEstadoAfiliado='ACTIVO'
        END

    IF UPDATE(roaEstadoAfiliado) or UPDATE(roaMotivoDesafiliacion)
        --if @roaTipoAfiliado = 'INACTIVO'
        BEGIN
            IF @roaEstadoAfiliado IS NOT NULL
                BEGIN
                     IF (EXISTS(select * from RolAfiliado r where r.roaAfiliado=@roaAfiliado
                                                                     and r.roaId=@roaId  AND r.roaTipoAfiliado='TRABAJADOR_DEPENDIENTE') AND
                                @roaEstadoAfiliado='INACTIVO' AND ((SELECT e.empMotivoDesafiliacion FROM Empleador e  WHERE e.empId= @emplId) IN ('EXPULSION_POR_MOROSIDAD',
                                                                                                                                                       'EXPULSION_POR_USO_INDEBIDO_DE_SERVICIOS','EXPULSION_POR_INFORMACION_INCORRECTA') OR @motivoDesafiliacion='AFILIACION_ANULADA')) and @TempCantidadVecesActivo=0
                                BEGIN
                                    DECLARE @CategoriaAfiliado BIGINT;

                                    IF @TempCantidadVecesActivo > 0
                                        BEGIN
                                            execute [dbo].[USP_REP_CalcularCategoriaNuevaAfiliacion] @idAfiliado, @emplId, @afiPersona, @roaEstadoAfiliado, @roaId
                                        END
                                    ELSE
                                        BEGIN
                                        IF ( SELECT top 1 ctaMotivoCambioCategoria FROM CategoriaAfiliado where ctaAfiliado=@roaAfiliado and ctaTipoAfiliado='TRABAJADOR_DEPENDIENTE' order by ctaFechaCambioCategoria desc ) !='RETIRO_NO_APLICA_SERVICIOS_CAJA' AND  @roaEstadoAfiliado='INACTIVO'
											begin
                                                INSERT INTO CategoriaAfiliado (ctaAfiliado,ctaTipoAfiliado,ctaClasificacion,ctaTotalIngresoMesada,ctaCategoria,ctaEstadoAfiliacion,ctaFechaFinServicioSinAfiliacion,ctaMotivoCambioCategoria,ctaFechaCambioCategoria,ctaTarifaUVT)
                                                VALUES (@roaAfiliado,@roaTipoAfiliado,@roaTipoAfiliado,(select top 1 c.ctaTotalIngresoMesada from CategoriaAfiliado c where c.ctaAfiliado=@roaAfiliado order by c.ctaId desc),
                                                        'SIN_CATEGORIA','INACTIVO',NULL,'RETIRO_NO_APLICA_SERVICIOS_CAJA',dbo.GetLocalDate(),'SIN_TARIFA')
                                            END
                                        END
                                END
                            ELSE
                                BEGIN
                                    IF ( SELECT count(*) FROM Afiliado  a
                                                                  inner join RolAfiliado r on r.roaAfiliado=a.afiId
                                                                  inner join SolicitudAfiliacionPersona s on s.sapRolAfiliado=r.roaId
                                                                  inner join Solicitud so on so.solId=s.sapSolicitudGlobal
                                                                  inner join ProductoNoConforme p on p.pncSolicitud=so.solId
                                         where a.afiId=@roaAfiliado and p.pncSubsanable=0)=0
                                        begin
                                            IF
                                                    (select COUNT(*) from Solicitud s
                                                                              inner join SolicitudAfiliacionPersona sf on sf.sapSolicitudGlobal=s.solId
                                                                              inner join RolAfiliado r on r.roaId=sf.sapRolAfiliado
                                                     where r.roaId=@roaId
                                                       and (s.solTipoTransaccion='AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION' or s.solTipoTransaccion='AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO')
                                                       and  sf.sapEstadoSolicitud='PRE_RADICADA'   --and @roaEstadoAfiliadoDel='INACTIVO'
                                                    )=0
                                                BEGIN

                                                    --EXEC USP_REP_CambioCategoriaAfiliado @roaAfiliado
                                                    --EXEC USP_REP_NuevaCategoriaAfiliado @roaAfiliado
                                                    --== Entra por la nueva forma de calcular la categoria.
                                                    --=================================================================================
                                                    --=========== Se agrega validación, para evaluar las categorias por retiros masivos.
                                                    --=========== 2023-02-13
                                                    --=================================================================================
                                                    declare @validarCant int = 1
                                                    set @validarCant = (select COUNT(*) from inserted)
                                                    if @validarCant = 1
                                                        begin
                                                            execute [dbo].[USP_REP_CalcularCategoriaNuevaAfiliacion] @idAfiliado, @emplId, @afiPersona, @roaEstadoAfiliado, @roaId
                                                        end
                                                    else
                                                        begin
                                                            create table #execCa(id int identity (1,1), comando nvarchar(200))
                                                            insert #execCa (comando)
                                                            select concat(N'execute [dbo].[USP_REP_CalcularCategoriaNuevaAfiliacion] ', roaAfiliado, N',', roaEmpleador, N',', a.afiPersona, N',',CHAR(39), r.roaEstadoAfiliado,CHAR(39), N',', r.roaId)
                                                            from inserted as r
                                                                     inner join dbo.Afiliado as a on roaAfiliado = a.afiId

                                                            declare @cont int = (select COUNT(*) from #execCa)
                                                            declare @conta int = 1
                                                            while @conta <= @cont
                                                                begin
                                                                    declare @coman nvarchar(250) = (select comando from #execCa where id = @conta)
                                                                    execute (@coman)
                                                                    set @conta += 1
                                                                end
                                                        end
                                                    --=================================================================================
                                                    --=========== Se agrega validación, para evaluar las categorias por retiros masivos.
                                                    --=================================================================================

                                                END
                                        end
                                END

                END
        END
    IF UPDATE(roaFechaRetiro)
        BEGIN
            UPDATE hra
            SET hraFechaRetiro = roa.roaFechaRetiro, hraMotivoDesafiliacion = roa.roaMotivoDesafiliacion
            FROM HistoricoRolAfiliado hra
                     INNER JOIN INSERTED roa on roa.roaAfiliado = hra.hraAfiliado AND (roa.roaEmpleador = hra.hraEmpleador OR (hraEmpleador IS NULL AND roa.roaEmpleador IS NULL))
            WHERE hra.hraId = (SELECT MAX(hraId)
                               FROM HistoricoRolAfiliado
                               WHERE hraAfiliado = roa.roaAfiliado
                                 AND (hraEmpleador = roa.roaEmpleador OR (hraEmpleador IS NULL AND roa.roaEmpleador IS NULL))
                                 AND hraTipoAfiliado = @roaTipoAfiliado)
              AND roa.roaFechaRetiro IS NOT NULL
              AND hra.hraFechaRetiro IS NULL
        END
    -- HISTORICO ROLAFILIADO
    ---A este condicional entra el canal presencial, en trigger de afiliaion persona update entra pila
    IF ((UPDATE(roaCanalReingreso) OR UPDATE(roaFechaAfiliacion) ) AND @roaEstadoAfiliado='INACTIVO' )
        BEGIN
            IF
                    (select COUNT(*) from Solicitud s
                                              inner join SolicitudAfiliacionPersona sf on sf.sapSolicitudGlobal=s.solId
                                              inner join RolAfiliado r on r.roaId=sf.sapRolAfiliado
                     where r.roaId=@roaId
                       and (s.solTipoTransaccion='AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION' or s.solTipoTransaccion='AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO'or solTipoTransaccion='AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION' or solTipoTransaccion='AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO')
                       and  sf.sapEstadoSolicitud='PRE_RADICADA' --and @roaEstadoAfiliadoDel='INACTIVO'
                    )=0
                BEGIN

                    UPDATE HistoricoRolAfiliado set hraFechaRetiro=(select i.roaFechaRetiro from inserted i) where hraId=(
                        select top 1  h.hraId from HistoricoRolAfiliado h where h.hraAfiliado=(select i.roaAfiliado from inserted i) order by h.hraFechaIngreso desc)
                    /*INSERT HistoricoRolAfiliado (hraFechaIngreso,hraFechaRetiro,hraTipoAfiliado,hraAfiliado,hraEmpleador,hraCanalRecepcion,hraSolicitud,hraRadicado,hraMotivoDesafiliacion)
                    SELECT roa.roaFechaAfiliacion,
                            NULL,
                            roa.roaTipoAfiliado,
                            roa.roaAfiliado,
                            roa.roaEmpleador,

                            CASE WHEN roa.roaCanalReingreso <> 'NOVEDAD_SUS_PATR' THEN 'PILA'
                            ELSE (select top 1 s.solCanalRecepcion from RolAfiliado r
                            inner join SolicitudNovedadPersona sp on sp.snpRolAfiliado=r.roaId
                            inner join SolicitudNovedad sn on sn.snoId=sp.snpSolicitudNovedad
                            inner join Solicitud s on s.solId=sn.snoSolicitudGlobal
                            where r.roaId=roa.roaId order by s.solId desc) END canalRecepcion,
                            NULL,
                            NULL,
                            roa.roaMotivoDesafiliacion
                    FROM INSERTED roa*/
                    -- WHERE roa.roaCanalReingreso IS NOT NULL
                END
        END
    IF ((UPDATE(roaCanalReingreso) OR UPDATE(roaFechaAfiliacion)) AND @roaEstadoAfiliado='ACTIVO')
        BEGIN
            IF
                    (select COUNT(*) from Solicitud s
                                              inner join SolicitudAfiliacionPersona sf on sf.sapSolicitudGlobal=s.solId
                                              inner join RolAfiliado r on r.roaId=sf.sapRolAfiliado
                     where( r.roaId=@roaId
                       and (s.solTipoTransaccion='AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION' or s.solTipoTransaccion='AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO' or solTipoTransaccion='AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION' or solTipoTransaccion='AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO')
                       and  sf.sapEstadoSolicitud='PRE_RADICADA') --and @roaEstadoAfiliadoDel='INACTIVO'
                    )=0


 
                BEGIN
                    INSERT HistoricoRolAfiliado (hraFechaIngreso,hraFechaRetiro,hraTipoAfiliado,hraAfiliado,hraEmpleador,hraCanalRecepcion,hraSolicitud,hraRadicado,hraMotivoDesafiliacion)
                    SELECT
                        case when roa.roaFechaAfiliacion is not null then  roa.roaFechaAfiliacion else  roa.roaFechaIngreso end,
                        --roa.roaFechaAfiliacion,
                        NULL,
                        roa.roaTipoAfiliado,
                        roa.roaAfiliado,
                        roa.roaEmpleador,
                        case when @solCanalRecepcionRolAfiliado is null then @solCanalRecepcion else @solCanalRecepcionRolAfiliado end,
                        NULL,
                        NULL,
                        roa.roaMotivoDesafiliacion
                    FROM INSERTED roa
                     --WHERE roa.roaCanalReingreso IS NOT NULL
                END
    END

END
