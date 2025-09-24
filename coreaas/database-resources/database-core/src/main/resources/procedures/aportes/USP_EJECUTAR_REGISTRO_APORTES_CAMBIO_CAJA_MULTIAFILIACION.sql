CREATE OR ALTER  PROCEDURE [dbo].[USP_EJECUTAR_REGISTRO_APORTES_CAMBIO_CAJA_MULTIAFILIACION]
@tablaEjecucion varchar(50),
@apgMarcaPeriodo varchar(19),
@apgId BIGINT,
@apgEstadoAportante varchar(50),
@varApgEmpresa varchar(200),
@varApgPeriodoAporte varchar(200),
--APORTE DETALLADO
 @apdId BIGINT,
 @apdMarcaPeriodo VARCHAR(19)
AS

BEGIN
	set xact_abort on;
SET NOCOUNT ON;

declare @fechaInicio varchar(50);
declare @fechaFin varchar(50);
DECLARE @ESTADOFINALAPORTE varchar(200) = 'REGISTRADO';
DECLARE @varEmpFechaCambioEstadoAfiliacion varchar(50);
DECLARE @varEmpFechaRetiro varchar(50);
DECLARE @varFechaRetiroYYYYMM varchar(50);
DECLARE @varFechaAfiliacionYYYYMM varchar(50);

--VARIABLES PARA APORTE DETALLADO
  declare @varApgPeriodoAporteOrdenado varchar(200);
  DECLARE @estadoPersona varchar(100);
 declare @varEmpid varchar(200);
  declare @varApgPersona varchar(200);
  DECLARE @apdEstadoCotizante varchar(50);
  DECLARE @varApgFechaRecaudo DATE;
   DECLARE @varApgFechaRecaudoYYYYMM varchar(50);
  DECLARE  @varApdTipoCotizante varchar(200);

IF @tablaEjecucion='APORTE_GENERAL'
BEGIN
	--GLPI 49449
	--INICIA REGISTRADO 
	    set @ESTADOFINALAPORTE = 'REGISTRADO';
	

	   IF (@apgEstadoAportante = 'NO_FORMALIZADO_RETIRADO_CON_APORTES' or @apgEstadoAportante = 'INACTIVO') and @ESTADOFINALAPORTE != 'RELACIONADO'
	   BEGIN
	   	IF EXISTS (
				select  0  FROM 
				Empresa emp
				INNER JOIN Persona per ON per.perId = emp.empPersona
				inner JOIN Empleador empl ON empl.empEmpresa = emp.empId			
				inner JOIN SolicitudNovedadEmpleador sne ON empl.empId = sne.sneIdEmpleador
				inner join SolicitudNovedad sno on sne.sneIdSolicitudNovedad = sno.snoId
				inner JOIN Solicitud sol ON sno.snoSolicitudGlobal = sol.solId
				WHERE 
				empl.empMotivoDesafiliacion in ('MULTIAFILIACION', 'RETIRO_POR_CAMBIO_DE_CAJA_COMPENSACION','RETIRO_POR_TRASLADO_OTRA_CCF')
				and empl.empEstadoEmpleador ='INACTIVO' 
				AND Sol.solTipoTransaccion like 'DESAFILIACION%'   
				AND Sol.solcanalrecepcion = 'PRESENCIAL'
				AND empl.empEmpresa = @varApgEmpresa)
		BEGIN
				select
				@varEmpFechaCambioEstadoAfiliacion = FORMAT(empl.empFechaCambioEstadoAfiliacion, 'yyyy-MM'),
				@varEmpFechaRetiro = FORMAT(empl.empFechaRetiro, 'yyyy-MM'),
				@fechaInicio = CONVERT(varchar,empFechaCambioEstadoAfiliacion,101),
				@fechaFin = CONVERT(varchar,empFechaRetiro,101),
				@varFechaRetiroYYYYMM = FORMAT(empFechaRetiro, 'yyyy-MM'),
				@varFechaAfiliacionYYYYMM = FORMAT(empFechaCambioEstadoAfiliacion, 'yyyy-MM')
				FROM 
				Empresa emp
				INNER JOIN Persona per ON per.perId = emp.empPersona
				inner JOIN Empleador empl ON empl.empEmpresa = emp.empId			
				inner JOIN SolicitudNovedadEmpleador sne ON empl.empId = sne.sneIdEmpleador
				inner join SolicitudNovedad sno on sne.sneIdSolicitudNovedad = sno.snoId
				inner JOIN Solicitud sol ON sno.snoSolicitudGlobal = sol.solId
				WHERE 
				empl.empMotivoDesafiliacion in ('MULTIAFILIACION', 'RETIRO_POR_CAMBIO_DE_CAJA_COMPENSACION','RETIRO_POR_TRASLADO_OTRA_CCF')
				and empl.empEstadoEmpleador ='INACTIVO' 
				AND Sol.solTipoTransaccion like 'DESAFILIACION%'   
				AND Sol.solcanalrecepcion = 'PRESENCIAL'
				AND empl.empEmpresa = @varApgEmpresa

					if (@varApgPeriodoAporte is not null and @varFechaRetiroYYYYMM is not null and @varFechaAfiliacionYYYYMM is not null)
					begin 
						if (@varApgPeriodoAporte = @varFechaRetiroYYYYMM or @varApgPeriodoAporte < @varFechaAfiliacionYYYYMM )
						begin
							--update AporteGeneral set apgEstadoRegistroAporteAportante = 'RELACIONADO' where apgId = @apgId;
							if  @varApgPeriodoAporte < @varFechaRetiroYYYYMM
							begin 
							set @ESTADOFINALAPORTE = 'REGISTRADO';
							end
							else
							begin
							set @ESTADOFINALAPORTE = 'RELACIONADO';
							end
						end
						else
						begin 
							--update AporteGeneral set apgEstadoRegistroAporteAportante = 'REGISTRADO' where apgId = @apgId;
							if  @varApgPeriodoAporte < @varFechaRetiroYYYYMM
							begin 
							set @ESTADOFINALAPORTE = 'REGISTRADO';
							end
							else
							begin
							set @ESTADOFINALAPORTE = 'RELACIONADO';
							end
						end
			
					end
			    end
	    END
	ELSE
	begin
		set @ESTADOFINALAPORTE = 'REGISTRADO';
	end
	---estado a contemplar con regla general
	IF @apgEstadoAportante = 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' or @apgEstadoAportante = 'NO_FORMALIZADO_CON_INFORMACION'
	BEGIN
		--update AporteGeneral set apgEstadoRegistroAporteAportante = 'RELACIONADO' where apgId = @apgId;
		set @ESTADOFINALAPORTE = 'RELACIONADO';
	END
	update AporteGeneral set apgEstadoRegistroAporteAportante = @ESTADOFINALAPORTE where apgId = @apgId;
	--GLPI 50417
	   IF @apgMarcaPeriodo = 'PERIODO_FUTURO'
	   BEGIN
		update AporteGeneral set apgEstadoRegistroAporteAportante = 'RELACIONADO' where apgId = @apgId;
		set @ESTADOFINALAPORTE = 'RELACIONADO';
	   END
 END 
 IF @tablaEjecucion='APORTE_DETALLADO'
	 BEGIN

	    select 
        @varApdTipoCotizante = ApdTipoCotizante,
        @varEmpid = apgempresa, 
        @varApgPeriodoAporte = apgPeriodoAporte, 
        @varApgPersona = apgPersona,
        @apgEstadoAportante = ag.apgEstadoAportante,
        @apdMarcaPeriodo = apdMarcaPeriodo,
        @apdEstadoCotizante = apdEstadoCotizante,
        @varApgFechaRecaudo = apgFechaRecaudo,
        @varApgFechaRecaudoYYYYMM = FORMAT(apgFechaRecaudo, 'yyyy-MM')
        from AporteGeneral ag
        inner join AporteDetallado ad on ad.apdAporteGeneral = ag.apgId
        where apdid = @apdId
	    -- GLPI 61052
       if (@ESTADOFINALAPORTE !=  'RELACIONADO')
       begin

        --set @data = concat('@apdId',@apdId);

        set @varApgPeriodoAporteOrdenado =  CONVERT(DATETIME, CONVERT(DATE,(@varApgPeriodoAporte+'-01')),101);
        --print @varApdTipoCotizante

        if (@varApdTipoCotizante = 'TRABAJADOR_DEPENDIENTE')
        begin
			-- SABER SI EL TRABAJOR ESTÁ INACTIVO

			--SELECT TOP 10 * FROM APORTEDETALLADO 
			
			select @estadoPersona = ra.roaEstadoAfiliado from APORTEDETALLADO ad
			inner join persona p on perId = ad.apdPersona
			inner join afiliado a on a.afiPersona = p.perid
			inner join rolafiliado ra on ra.roaafiliado = a.afiid
			where 
			apdid = @apdId
			and ra.roaEmpleador = @varEmpid

			IF(@estadoPersona = 'INACTIVO')
			begin
				IF EXISTS (
				select
				0
				FROM 
				Empresa emp
				INNER JOIN Persona per ON per.perId = emp.empPersona
				inner JOIN Empleador empl ON empl.empEmpresa = emp.empId			
				inner JOIN SolicitudNovedadEmpleador sne ON empl.empId = sne.sneIdEmpleador
				inner join SolicitudNovedad sno on sne.sneIdSolicitudNovedad = sno.snoId
				--	inner JOIN Solicitud sol ON sno.snoSolicitudGlobal = sol.solId
				WHERE 
				empl.empMotivoDesafiliacion in ('MULTIAFILIACION', 'RETIRO_POR_CAMBIO_DE_CAJA_COMPENSACION','RETIRO_POR_TRASLADO_OTRA_CCF')
				and empl.empEstadoEmpleador ='INACTIVO' 
				--AND Sol.solTipoTransaccion like 'DESAFILIACION%'   
				--AND Sol.solcanalrecepcion = 'PRESENCIAL'
				AND empl.empId = @varEmpid)
				BEGIN
					
					--set @ESTADOFINALAPORTE = 'RELACIONADO';

					select 
					@fechaInicio = CONVERT(varchar,empFechaCambioEstadoAfiliacion,101),
					@fechaFin = CONVERT(varchar,empFechaRetiro,101),
					@varFechaRetiroYYYYMM = FORMAT(empFechaRetiro, 'yyyy-MM'),
					@varFechaAfiliacionYYYYMM = FORMAT(empFechaCambioEstadoAfiliacion, 'yyyy-MM')
					from 
					Empresa emp
					INNER JOIN Persona per ON per.perId = emp.empPersona
					inner JOIN Empleador empl ON empl.empEmpresa = emp.empId			
					inner JOIN SolicitudNovedadEmpleador sne ON empl.empId = sne.sneIdEmpleador
					inner join SolicitudNovedad sno on sne.sneIdSolicitudNovedad = sno.snoId
					--	inner JOIN Solicitud sol ON sno.snoSolicitudGlobal = sol.solId
					WHERE 
					empl.empMotivoDesafiliacion in ('MULTIAFILIACION', 'RETIRO_POR_CAMBIO_DE_CAJA_COMPENSACION','RETIRO_POR_TRASLADO_OTRA_CCF')
					and empl.empEstadoEmpleador ='INACTIVO' 
					--AND Sol.solTipoTransaccion like 'DESAFILIACION%'   
					--AND Sol.solcanalrecepcion = 'PRESENCIAL'
					AND empl.empId = @varEmpid

					if (@varApgPeriodoAporte is not null and @varFechaRetiroYYYYMM is not null and @varFechaAfiliacionYYYYMM is not null)
					begin 
						if (@varApgPeriodoAporte = @varFechaRetiroYYYYMM or @varApgPeriodoAporte < @varFechaAfiliacionYYYYMM )
						begin
							--print 'IGUAL - Mes/año del aporte igual a Mes/año de fecha de recaudo';
							if  @varApgPeriodoAporte < @varFechaRetiroYYYYMM
							begin 
							    set @ESTADOFINALAPORTE = 'REGISTRADO';
							end
							else
							begin
							    set @ESTADOFINALAPORTE = 'RELACIONADO';
							end
						end
						else
						begin
							--print 'NO IGUAL - Mes/año del aporte igual a Mes/año de fecha de recaudo';
							if  @varApgPeriodoAporte < @varFechaRetiroYYYYMM
							begin 
							    set @ESTADOFINALAPORTE = 'REGISTRADO';
							end
							else
							begin
							    set @ESTADOFINALAPORTE = 'RELACIONADO';
							end
						end
					end;
				END
				
			end
			else
			begin
				set @ESTADOFINALAPORTE = 'REGISTRADO';
			end

			-- EVALUAR SI EL EMPLEADOR SE RETIRÓ POR ALGUNOS MOTIVOS ('MULTIAFILIACION', 'RETIRO_POR_CAMBIO_DE_CAJA_COMPENSACION','RETIRO_POR_TRASLADO_OTRA_CCF')

        end;

        if (@varApdTipoCotizante in ('TRABAJADOR_INDEPENDIENTE','PENSIONADO'))
        begin
            --print concat('Es ', @varApdTipoCotizante);

            

            set @fechaInicio = CAST(@fechaInicio AS DATETIME);
            set @fechaFin = CAST(@fechaFin AS DATETIME);
           -- set @dateAporte = CAST(@varApgPeriodoAporteOrdenado AS DATETIME);

			select @estadoPersona = ra.roaEstadoAfiliado from APORTEDETALLADO ad
			inner join persona p on perId = ad.apdPersona
			inner join afiliado a on a.afiPersona = p.perid
			inner join rolafiliado ra on ra.roaafiliado = a.afiid
			where 
			apdid = @apdId
			and ra.roaTipoAfiliado = @varApdTipoCotizante

			IF(@estadoPersona = 'INACTIVO')
			begin
				IF EXISTS (
				select
				0
				FROM 
				Persona p   
                   inner join Afiliado a   on a.afiPersona=p.perId  
                   inner join RolAfiliado r on r.roaAfiliado=a.afiId  
                   --INNER JOIN SolicitudNovedadPersona sp on sp.snpRolAfiliado=r.roaId  
                   --inner join SolicitudNovedad sn on sn.snoId=sp.snpSolicitudNovedad  
                   --inner join Solicitud s on s.solId=sn.snoSolicitudGlobal  
                   where
                   r.roaEstadoAfiliado='INACTIVO'    
                   and r.roaMotivoDesafiliacion in ('MULTIAFILIACION', 'RETIRO_POR_CAMBIO_DE_CAJA_COMPENSACION','RETIRO_POR_TRASLADO_OTRA_CCF')   
                   --AND S.solTipoTransaccion like 'RETIRO%'   
                   --AND S.solcanalrecepcion = 'PRESENCIAL'   
                   --and p.pernumeroidentificacion = '75098591'
                   and p.perId = @varApgPersona
					and r.roaTipoAfiliado = @varApdTipoCotizante
					)

				BEGIN
					
					SELECT 
					@fechaInicio = CONVERT(varchar,r.roaFechaAfiliacion,101),
					@fechaFin = CONVERT(varchar,r.roaFechaRetiro,101),
					@varFechaRetiroYYYYMM = FORMAT(r.roaFechaRetiro, 'yyyy-MM'),
					@varFechaAfiliacionYYYYMM = FORMAT(r.roaFechaAfiliacion, 'yyyy-MM')
                   FROM Persona p   
                   inner join Afiliado a   on a.afiPersona=p.perId  
                   inner join RolAfiliado r on r.roaAfiliado=a.afiId  
                   --INNER JOIN SolicitudNovedadPersona sp on sp.snpRolAfiliado=r.roaId  
                   --inner join SolicitudNovedad sn on sn.snoId=sp.snpSolicitudNovedad  
                   --inner join Solicitud s on s.solId=sn.snoSolicitudGlobal  
                   where
                   r.roaEstadoAfiliado='INACTIVO'    
                   and r.roaMotivoDesafiliacion in ('MULTIAFILIACION', 'RETIRO_POR_CAMBIO_DE_CAJA_COMPENSACION','RETIRO_POR_TRASLADO_OTRA_CCF')   
                   --AND S.solTipoTransaccion like 'RETIRO%'   
                   --AND S.solcanalrecepcion = 'PRESENCIAL'   
                   --and p.pernumeroidentificacion = '75098591'
                   and p.perId = @varApgPersona
				   and r.roaTipoAfiliado = @varApdTipoCotizante
				   ;
					
					if (@varApgPeriodoAporte is not null and @varFechaRetiroYYYYMM is not null and @varFechaAfiliacionYYYYMM is not null)
					begin 
						if (@varApgPeriodoAporte = @varFechaRetiroYYYYMM or @varApgPeriodoAporte < @varFechaAfiliacionYYYYMM )
						begin
							--print 'IGUAL - Mes/año del aporte igual a Mes/año de fecha de recaudo';
							if  @varApgPeriodoAporte < @varFechaRetiroYYYYMM
							begin 
							    set @ESTADOFINALAPORTE = 'REGISTRADO';
							end
							else
							begin
							    set @ESTADOFINALAPORTE = 'RELACIONADO';
							end
						end
						else
						begin
							--print 'NO IGUAL - Mes/año del aporte igual a Mes/año de fecha de recaudo';
							if  @varApgPeriodoAporte < @varFechaRetiroYYYYMM
							begin 
							set @ESTADOFINALAPORTE = 'REGISTRADO';
							end
							else
							begin
							set @ESTADOFINALAPORTE = 'RELACIONADO';
							end
						end
					end;
				END
				
			end
			else
			begin
				set @ESTADOFINALAPORTE = 'REGISTRADO';
			end

            
        end;

    end;
    --GLPI 49449
    IF @apdEstadoCotizante = 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' or  @apdEstadoCotizante ='NO_FORMALIZADO_CON_INFORMACION'
    BEGIN
        set @ESTADOFINALAPORTE = 'RELACIONADO';
    END 
       IF @ESTADOFINALAPORTE is not null         
       BEGIN
          

            update AporteDetallado set apdEstadoRegistroAporte = @ESTADOFINALAPORTE, apdEstadoRegistroAporteCotizante = @ESTADOFINALAPORTE   
            where apdId = @apdId;  

       END

	 END
 END