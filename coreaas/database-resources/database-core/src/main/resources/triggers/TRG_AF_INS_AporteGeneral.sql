-- =============================================
-- Author:      Diego Suesca
-- Create date: 2019/01/18 
-- Description: 
-- Modified: Se agrega cambios GLPI 57808 - Eprocess
-- =============================================
CREATE OR ALTER   TRIGGER [dbo].[TRG_AF_INS_AporteGeneral]
ON [dbo].[AporteGeneral]
AFTER INSERT AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @apgId BIGINT
    DECLARE @apgEmpresa BIGINT      
    DECLARE @empPersona BIGINT
    DECLARE @apdPersonas AS TablaIdType; 
    declare @nuevoApgRegistrogeneral bigint;
    DECLARE @cuentaBancariaRecaudo BIGINT;
    DECLARE @apgMarcaPeriodo varchar(19);
	declare @varApgPeriodoAporte varchar(200);
	DECLARE @varApgEmpresa varchar(200);
	DECLARE @apgEstadoAportante varchar(50);
	DECLARE @aporteFuturoGap varchar(20);
	DECLARE @aporteFuturo varchar(10);

    SELECT @apgId = apgId, 
            @apgEmpresa = apgEmpresa,
            @nuevoApgRegistrogeneral = apgRegistrogeneral,
            @cuentaBancariaRecaudo = apgCuentaBancariaRecaudo,
			@apgMarcaPeriodo = apgMarcaPeriodo,
			@apgEstadoAportante = apgEstadoAportante,
			@varApgEmpresa = apgempresa,
			@varApgPeriodoAporte = apgPeriodoAporte 
    FROM INSERTED


    INSERT @apdPersonas (perIdAfiliado,idEmpleador)
    SELECT apd.apdPersona,
            empl.empId
    FROM AporteGeneral apg
    INNER JOIN AporteDetallado apd ON apd.apdAporteGeneral = apg.apgId
    INNER JOIN Empresa emp on emp.empid = apg.apgEmpresa
    INNER JOIN Empleador empl on emp.empId = empl.empEmpresa
    WHERE apg.apgId = @apgId
   
    --***************************
    -- INICIO TRIGGER GLPI 57808
    -- ****************************
    IF @cuentaBancariaRecaudo IS NULL
    BEGIN
        CREATE TABLE #temporalCuentaBancariaRecaudo 
        (
            idCuentaBancariaRecaudo varchar(300),
            informacionEjecucion varchar(1000)
        );
        declare @consultaSQL nvarchar(300);
        declare @idCuentaBancariaRecaudo nvarchar(300);
        set @consultaSQL = concat('select top 1 regCuentaBancariaRecaudo  from staging.RegistroGeneral where regid =',@nuevoApgRegistrogeneral);
        insert into #temporalCuentaBancariaRecaudo 
            EXEC sp_execute_remote pilaReferenceData, @consultaSQL
        select @idCuentaBancariaRecaudo = idCuentaBancariaRecaudo from #temporalCuentaBancariaRecaudo;
        update AporteGeneral set apgCuentaBancariaRecaudo = @idCuentaBancariaRecaudo where apgregistrogeneral = @nuevoApgRegistrogeneral;
        drop table #temporalCuentaBancariaRecaudo;
    END
    --***************************
    -- FIN TRIGGER GLPI 57808
    -- ****************************
		/*
		--GLPI 50417
		--GLPI 63385
		 SELECT @aporteFuturoGap =  pg.prgEstado from ParametrizacionGaps pg where pg.prgNombre = 'APORTES_FUTURO';
		 SELECT @aporteFuturo =  p.prmValor from Parametro p where p.prmNombre = 'REGISTRO_APORTES_FUTURO';
	   IF (@apgMarcaPeriodo = 'PERIODO_FUTURO' AND @aporteFuturoGap ='INACTIVO')
	   BEGIN
		update AporteGeneral set apgEstadoRegistroAporteAportante = 'RELACIONADO' where apgId = @apgId;
	   END
	   IF (@apgMarcaPeriodo = 'PERIODO_FUTURO' AND @aporteFuturoGap ='ACTIVO')
	   BEGIN
	    IF(@aporteFuturo = 'SI')
		BEGIN
			IF (@apgEstadoAportante='ACTIVO' OR @apgEstadoAportante='INACTIVO' OR @apgEstadoAportante='NO_FORMALIZADO_RETIRADO_CON_APORTES' )
			BEGIN
				update AporteGeneral set apgEstadoRegistroAporteAportante = 'REGISTRADO' where apgId = @apgId;
			END
		END
		ELSE
		BEGIN
			update AporteGeneral set apgEstadoRegistroAporteAportante = 'RELACIONADO' where apgId = @apgId;
		END
	   END
	   */
	--****************
	--SP ENCARGADO REALIZAR GLPI 61052 QUE TIENE EN CUENTA EL PARAMETRO PARAMETRIZACIONGAPS
	--*****************
	IF EXISTS (SELECT 1 FROM ParametrizacionGaps WHERE prgNombre='REGISTRO_APORTES_CAMBIO_CAJA_MULTIAFILIACION' and prgEstado='ACTIVO')
	begin 
	--IF PARA GLPI 61052 PARAMETROS APORTE_GENERAL
	 	--select 'APORTE_GENERAL',@apgMarcaPeriodo,@apgId,@apgEstadoAportante,@varApgEmpresa,@varApgPeriodoAporte,NULL,NULL,NULL
		execute USP_EJECUTAR_REGISTRO_APORTES_CAMBIO_CAJA_MULTIAFILIACION 'APORTE_GENERAL',@apgMarcaPeriodo,@apgId,@apgEstadoAportante,@varApgEmpresa,@varApgPeriodoAporte,NULL,NULL
	END
	ELSE IF @apgMarcaPeriodo = 'PERIODO_FUTURO'
		IF EXISTS (SELECT 1 FROM ParametrizacionGaps WHERE prgNombre='APORTES_FUTURO' and prgEstado='ACTIVO') 
		AND EXISTS (SELECT 1 FROM Parametro WHERE prmNombre='REGISTRO_APORTES_FUTURO' and prmValor='SI')
			BEGIN
				IF (@apgEstadoAportante='ACTIVO' OR @apgEstadoAportante='INACTIVO' OR @apgEstadoAportante='NO_FORMALIZADO_RETIRADO_CON_APORTES' )
				BEGIN
					update AporteGeneral set apgEstadoRegistroAporteAportante = 'REGISTRADO' where apgId = @apgId;
				END
			END
			ELSE
			BEGIN
				update AporteGeneral set apgEstadoRegistroAporteAportante = 'RELACIONADO' where apgId = @apgId;
			END
	ELSE IF @apgEstadoAportante='ACTIVO' OR @apgEstadoAportante='INACTIVO' OR @apgEstadoAportante='NO_FORMALIZADO_RETIRADO_CON_APORTES' 
		BEGIN
			update AporteGeneral set apgEstadoRegistroAporteAportante = 'REGISTRADO' where apgId = @apgId;
		END
	ELSE 
		update AporteGeneral set apgEstadoRegistroAporteAportante = 'RELACIONADO' where apgId = @apgId;
	--***************
	--FIN SP ENCARGADO REALIZAR GLPI 61052 QUE TIENE EN CUENTA EL PARAMETRO PARAMETRIZACIONGAPS
	--*****************


    IF @apgEmpresa IS NOT NULL
    BEGIN
        SELECT @empPersona = emp.empPersona
        FROM Empresa emp                       
        WHERE emp.empId = @apgEmpresa               

        IF @empPersona IS NOT NULL 
        BEGIN
            EXEC USP_REP_INS_EstadoAfiliacionEmpleadorCaja @empPersona  
            EXEC USP_REP_INS_EstadoAfiliacionPersonaEmpresa NULL, NULL, @apdPersonas
        END   
    END
        
    EXEC USP_REP_INS_EstadoAfiliacionPersonaCaja null, @apdPersonas    

    EXEC USP_REP_INS_EstadoAfiliacionPersonaIndependiente NULL, @apdPersonas  

    EXEC USP_REP_INS_EstadoAfiliacionPersonaPensionado NULL, @apdPersonas  
END