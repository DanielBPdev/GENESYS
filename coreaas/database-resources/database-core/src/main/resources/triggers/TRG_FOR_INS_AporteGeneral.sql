--***************************************
-- Trigger para Aporte General
--***************************************
CREATE OR ALTER   TRIGGER [dbo].[Trigger_AporteGeneral] ON [dbo].[AporteGeneral] 
	FOR INSERT
AS 
	set xact_abort on;
SET NOCOUNT ON;
DECLARE @apgId bigint;
DECLARE @apgMarcaPeriodo varchar(19);
DECLARE @apgEstadoAportante varchar(50);
DECLARE @contadorGlpi61052 int;
declare @fechaInicio varchar(50);
declare @fechaFin varchar(50);
DECLARE @dateinicio DATETIME;
DECLARE @datefin DATETIME;
DECLARE @dateAporte DATETIME;
declare @varApdTipoCotizante varchar(200);
declare @varEmpid varchar(200);
declare @varApgPeriodoAporte varchar(200);
declare @varApgPeriodoAporteOrdenado varchar(200);
declare @varApgPersona varchar(200);
DECLARE @ESTADOFINALAPORTE varchar(200) = 'REGISTRADO';
DECLARE @apdId bigint;
DECLARE @varApgEmpresa varchar(200);
DECLARE @varEmpFechaCambioEstadoAfiliacion varchar(50);
DECLARE @varEmpFechaRetiro varchar(50);
DECLARE @varPeriodoAporte varchar(50);
DECLARE @varFechaRetiroYYYYMM varchar(50);
DECLARE @varFechaAfiliacionYYYYMM varchar(50);

select	@apgMarcaPeriodo = m.apgMarcaPeriodo, 
		@apgId = m.apgId, 
		@apgEstadoAportante = m.apgEstadoAportante,
		@varApgEmpresa = m.apgempresa,
		@varPeriodoAporte = m.apgPeriodoAporte,
		@varApgPeriodoAporte = m.apgPeriodoAporte 
		from inserted m;

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
	/*
	ELSE
	begin
		set @ESTADOFINALAPORTE = 'REGISTRADO';
	end
	--estados a tener en cuenta como regla general
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
	*/