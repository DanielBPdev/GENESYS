-- =============================================
-- Author:    Andres Julian Rocha Cruz
-- Create date: 2021/02/16
-- Description: Función de tipo tabla la cual retorna el procesamiento las novedades referenciales
-- cuya lógica se migra de procedimiento almacenado USP_RegistrarRelacionarNovedadesReferenciales
-- antes invocado a través de un cursor desde el procedimiento USP_ValidarNovedadesEmpleadorActivo
-- =============================================
CREATE FUNCTION [dbo].[FN_RegistrarRelacionarNovedadesReferenciales] 
(
  @IdRegistroDetalle Bigint,
  @estadoCotizante Varchar(50),
  @fechaIngresoCotizante Date,
  @periodoAporte VARCHAR(7), 
  @EsTrabajadorReintegrable BIT
)
RETURNS @tabla TABLE (
  tipoNovedad Varchar(15),
  tipoTransaccion Varchar(100),
  fechaInicioNovedad Date,
  fechaFinNovedad Date,
  idRegistroNovedad Bigint,
  idRegistroDetalle BIGINT,
  accionNovedad VARCHAR(20),
  situacionPrimaria VARCHAR(20),
  fechaSituacionPrimaria DATETIME,
  estadoCotizante Varchar(50),
  tipoNovedadReferencia VARCHAR(25),
  mensaje Varchar(250)
)
AS
BEGIN
    
  DECLARE @Novedades AS TABLE 
  (ID INT IDENTITY, 
    TipoNovedad VARCHAR(15), 
    Tipotransaccion VARCHAR(100), 
    FechaInicio DATE, 
    FechaFin DATE, 
    SituacionPrimaria BIT, 
    idRegistroNovedad BIGINT,
    FechaRetiroCotizanteOUT DATE
  )
  
  INSERT INTO @Novedades (TipoNovedad,Tipotransaccion,FechaInicio,FechaFin,SituacionPrimaria,idRegistroNovedad,FechaRetiroCotizanteOUT)
  SELECT rdnTipoNovedad, rdnTipotransaccion, rdnFechaInicioNovedad, rdnFechaFinNovedad, 0, rdnId, redOUTFechaRetiroCotizante
  FROM staging.RegistroDetalladoNovedad rdn
  INNER JOIN staging.RegistroDetallado red ON rdn.rdnRegistroDetallado = red.redId
  WHERE red.redId = @IdRegistroDetalle
  ORDER BY rdnFechaInicioNovedad
  
  INSERT INTO @Novedades (TipoNovedad,FechaInicio,FechaFin,SituacionPrimaria,idRegistroNovedad,FechaRetiroCotizanteOUT)
  SELECT redOUTTipoNovedadSituacionPrimaria, redOUTFechaInicioNovedadSituacionPrimaria, redOUTFechaFinNovedadSituacionPrimaria, 1, 0, redOUTFechaRetiroCotizante
  FROM staging.RegistroDetallado red
  WHERE red.redId = @IdRegistroDetalle
  AND red.redOUTTipoNovedadSituacionPrimaria IS NOT NULL
    
    
  IF @@ROWCOUNT = 0
  BEGIN
    INSERT INTO @Novedades (TipoNovedad,FechaInicio,FechaFin,SituacionPrimaria,idRegistroNovedad)
    VALUES ('SIN_NOVEDAD',NULL,NULL,1,0)  
  END
  
  DECLARE @situacionPrimaria Varchar(20) = (SELECT Top 1 TipoNovedad FROM @Novedades 
                        WHERE SituacionPrimaria = 'True')
  DECLARE @fechaSituacionPrimaria Date = (SELECT Top 1 FechaInicio FROM @Novedades 
                        WHERE SituacionPrimaria = 'True')
                          
  -- Inicio mantis 261766                     
  DECLARE @fechaInicioNovRetiro DATE = NULL
  SELECT @fechaInicioNovRetiro = FechaInicio FROM @Novedades WHERE TipoNovedad = 'NOVEDAD_RET'
  -- Fin mantis 261766
                          
  DECLARE @NovedadesCursor AS CURSOR
  DECLARE
  @TipoNovedad Varchar(15),
  @TipoTransaccion Varchar(100),
  @FechaInicioNovedad Date,
  @FechaFinNovedad Date,
  @IdRegistroNovedad Bigint,
  @FechaRetiroCotizanteOUT date
  
  SET @NovedadesCursor = CURSOR FAST_FORWARD FOR
  
  SELECT TipoNovedad,TipoTransaccion,FechaInicio,FechaFin,idRegistroNovedad,FechaRetiroCotizanteOUT
  FROM @Novedades 
  WHERE SituacionPrimaria = 'False' 
  ORDER BY fechaInicio ASC
  
  OPEN @NovedadesCursor
  FETCH NEXT FROM @NovedadesCursor INTO
  @TipoNovedad,
  @TipoTransaccion,
  @FechaInicioNovedad,
  @FechaFinNovedad, 
  @IdRegistroNovedad,
  @FechaRetiroCotizanteOUT
  
  WHILE @@FETCH_STATUS = 0
  BEGIN
    --print @situacionPrimaria +'  '+@TipoNovedad
    DECLARE @respuesaAccionNovedad Varchar(20) = NULL
     
    DECLARE @tipoNovedadReferencia Varchar(25) = NULL
    DECLARE @accionNovedad Varchar(20) = NULL
    DECLARE @condicionPrevia Varchar(15) = NULL
    DECLARE @mensaje Varchar(250) = NULL
  
    SET @estadoCotizante = ISNULL(@estadoCotizante,'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES')
  
    -- tratamiento de novedad con fecha
    IF @FechaInicioNovedad IS NOT NULL
    BEGIN
      SELECT @tipoNovedadReferencia = renTipoNovedad , @accionNovedad = renResultado,@condicionPrevia = renCondicion,@mensaje = renMensaje 
      FROM referenciaNovedad
      WHERE renEstadoCotizante= @estadoCotizante AND renSituacionPrimaria= @situacionPrimaria
      AND renTipoNovedad = @tipoNovedad
  
      IF(@condicionPrevia ='CONDICION_1' OR @condicionPrevia ='CONDICION_2' OR @condicionPrevia ='CONDICION_3'
        OR @condicionPrevia ='CONDICION_4' OR @condicionPrevia ='CONDICION_5' OR @condicionPrevia ='CONDICION_9')
      BEGIN
      
        IF @fechaIngresoCotizante IS NULL
        BEGIN
          SET @accionNovedad = 'APLICAR_NOVEDAD'
        END
        ELSE IF(@fechaIngresoCotizante > @fechaInicioNovedad )
        BEGIN
          SET @accionNovedad = 'RELACIONAR_NOVEDAD'
        END
      END
        IF(@condicionPrevia ='CONDICION_6' OR @condicionPrevia ='CONDICION_7' OR @condicionPrevia ='CONDICION_8'  
          OR @condicionPrevia ='CONDICION_10' OR @condicionPrevia ='CONDICION_11' OR @condicionPrevia ='CONDICION_12')
      BEGIN
        
        IF @fechaIngresoCotizante IS NULL OR @fechaSituacionPrimaria IS NULL
        BEGIN
          SET @accionNovedad = 'APLICAR_NOVEDAD'
        END
        ELSE IF(@fechaIngresoCotizante > @fechaInicioNovedad AND @fechaSituacionPrimaria > @fechaInicioNovedad )
        BEGIN
          SET @accionNovedad = 'RELACIONAR_NOVEDAD'
        END
      END
      
      --APLAZAR LOGICA
      /*
              --Tratamiento de novedad de ingreso con previo retiro
          IF ((@tipoNovedad = 'NOVEDAD_ING') AND EXISTS (
              SELECT * 
              FROM #Novedades
              WHERE tenRegistroGeneral = @IdRegistroGeneral
              AND tenTipoIdCotizante = @tipoIdentificacionCotizante 
              AND tenNumeroIdCotizante = @numeroIdentificacionCotizante
              AND tenAccionNovedad = 'APLICAR_NOVEDAD'
              AND tenEsRetiro = 1
              AND tenFechaInicioNovedad < @FechaInicioNovedad
          ))
          BEGIN
              SET @accionNovedad = 'APLICAR_NOVEDAD'
              SET @estadoCotizante = 'ACTIVO'
              SET @mensaje = '- Aplicada por retiro previo en misma planilla'
                  
              -- Se cambia trabajador a reintegrable para poder aplicar novedad de ingreso
              -- Por defecto se indica que el grupo familiar de igual manera es reintegrable para que  
              -- desde código se realizan las validaciones respectivas que indicarán si el GF es o no reintegrable
              UPDATE staging.RegistroDetallado
              SET redOUTEsTrabajadorReintegrable = 1, redOUTGrupoFamiliarReintegrable = 1
              FROM staging.RegistroDetallado 
              WHERE redId = @IdRegistroDetalle
  
          END -- Fin tratamiento novedad ingre3so con previo retiro
      */
    END
    -- tratamiento de novedad sin fecha
    ELSE
    BEGIN
      --print 'Novedad sin fecha'
          
      -- se actualiza la fecha de la novedad, al �ltimo d�a calendario del mes del aporte
      SET @fechaInicioNovedad = EOMONTH(CONVERT(Date,@periodoAporte+'-01'))
  
      IF (@estadoCotizante = 'ACTIVO' AND @fechaIngresoCotizante IS NOT NULL AND CONVERT(VARCHAR(7), @fechaIngresoCotizante, 121) < @periodoAporte 
          AND @TipoNovedad NOT IN ('NOVEDAD_LMA', 'NOVEDAD_IGE', 'NOVEDAD_IRP')) 
        OR (@TipoNovedad = 'NOVEDAD_ING' AND @EsTrabajadorReintegrable = 1)
      BEGIN
        SET @accionNovedad = 'APLICAR_NOVEDAD'
  
        SELECT @tipoNovedadReferencia = renTipoNovedad , @accionNovedad = renResultado,@condicionPrevia = renCondicion,@mensaje = renMensaje 
        FROM referenciaNovedad
        WHERE renEstadoCotizante = @estadoCotizante AND renSituacionPrimaria = 'SIN_NOVEDAD'
        AND renTipoNovedad = @tipoNovedad
      END
      -- novedad sin fecha no aplicable
      ELSE 
      BEGIN
  
        SELECT @tipoNovedadReferencia = renTipoNovedad, @accionNovedad = renResultado, @condicionPrevia = renCondicion, @mensaje = renMensaje 
        FROM referenciaNovedad
        WHERE renEstadoCotizante = @estadoCotizante 
        AND renSituacionPrimaria = @situacionPrimaria
        AND renTipoNovedad = @tipoNovedad
      END
  
      -- si la novedad tiene fecha fin, se le iguala a la inicial
      IF @TipoNovedad NOT IN ('NOVEDAD_ING', 'NOVEDAD_RET', 'NOVEDAD_VSP')
      BEGIN
        SET @FechaFinNovedad = @fechaInicioNovedad
      END
  
      -- se controla la relaci�n de novedades respecto al per�odo del aporte y la fecha de activaci�n
      IF @estadoCotizante = 'ACTIVO' AND CONVERT(VARCHAR(7), ISNULL(@fechaIngresoCotizante, dbo.GetLocalDate()), 121) >= @periodoAporte
      BEGIN
        SELECT @tipoNovedadReferencia = renTipoNovedad, @accionNovedad = renResultado, @condicionPrevia = renCondicion, @mensaje = renMensaje 
        FROM referenciaNovedad
        WHERE renResultado = 'RELACIONAR_NOVEDAD'
        AND renTipoNovedad = @tipoNovedad
      END
    END
      
    -- Inicio mantis 261766
    IF (@TipoNovedad NOT IN ('NOVEDAD_ING', 'NOVEDAD_RET') AND @fechaInicioNovRetiro IS NOT NULL AND @fechaInicioNovedad > @fechaInicioNovRetiro)
    BEGIN
      SET @accionNovedad = 'RELACIONAR_NOVEDAD'
    END
    -- Fin mantis 261766
  
    --APLAZAR LOGICA
    /*
    IF(@tipoNovedadReferencia IS NOT NULL)
    BEGIN
  
      UPDATE rdn
      SET rdnAccionNovedad = @accionNovedad,
        rdnMensajeNovedad = @mensaje, 
        rdnFechaInicioNovedad = @fechaInicioNovedad, 
        rdnFechaFinNovedad = @FechaFinNovedad,
        rdnDateTimeUpdate =  dbo.getLocalDate()
      FROM staging.RegistroDetalladoNovedad rdn
      WHERE rdn.rdnRegistroDetallado = @IdRegistroDetalle
      AND rdn.rdnTipoNovedad = @TipoNovedad
  
      INSERT INTO #Novedades (tenIdTransaccion,tenMarcaNovedadSimulado,tenMarcaNovedadManual,tenRegistroGeneral,tenRegistroDetallado,tenTipoIdAportante,tenNumeroIdAportante
                  ,tenTipoIdCotizante,tenNumeroIdCotizante,tenTipoTransaccion, tenEsIngreso, tenEsRetiro, tenFechaInicioNovedad
                  ,tenFechaFinNovedad,tenAccionNovedad,tenMensajeNovedad,tenPrimerNombre,tenSegundoNombre,tenPrimerApellido
                  ,tenSegundoApellido,tenCodigoDepartamento,tenCodigoMunicipio,tenRegistroDetalladoNovedad,tenEnProceso)
      VALUES(@IdRegistroDetalle,@EsSimulado,@EsRegistroManual,@IdRegistroGeneral,@IdRegistroDetalle,@tipoIdentificacionAportante,@numeroIdentificacionAportante
          ,@tipoIdentificacionCotizante,@numeroIdentificacionCotizante
          ,@TipoTransaccion
          ,CASE WHEN @tipoNovedadReferencia = 'NOVEDAD_ING' THEN 1 ELSE 0 END
          ,CASE WHEN @tipoNovedadReferencia = 'NOVEDAD_RET' THEN 1 ELSE 0 END
          ,@fechaInicioNovedad,@fechaFinNovedad,@accionNovedad,@mensaje
          ,@primerNombre,@segundoNombre,@primerApellido,@segundoApellido,
          @codDepartamento,@codMunicipio,@IdRegistroNovedad,0)
    END
    */
  
  
    IF(@accionNovedad = 'APLICAR_NOVEDAD' AND @TipoNovedad != 'NOVEDAD_ING')
    BEGIN
      SET @situacionPrimaria = @TipoNovedad
      SET @fechaSituacionPrimaria = @FechaInicioNovedad
    END
    ELSE IF(@accionNovedad = 'APLICAR_NOVEDAD' AND @TipoNovedad = 'NOVEDAD_ING')
    BEGIN
      SET @situacionPrimaria = 'SIN_NOVEDAD'
      SET @estadoCotizante = 'ACTIVO'
    END

    -- 0270521
    IF @fechaIngresoCotizante IS NOT NULL AND @fechaIngresoCotizante < @FechaRetiroCotizanteOUT 
    BEGIN
      SET @accionNovedad = 'RELACIONAR_NOVEDAD'
    END
/*
    INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
    VALUES (dbo.GetLocalDate(), 
    '@FechaRetiroCotizanteOUT = ' + CAST(@FechaRetiroCotizanteOUT AS VARCHAR(10)), 
    '@fechaIngresoCotizante: ' + CAST(@fechaIngresoCotizante AS VARCHAR(10)));
*/

    INSERT INTO @tabla VALUES (
    @tipoNovedad ,
    @tipoTransaccion ,
    @fechaInicioNovedad ,
    @fechaFinNovedad ,
    @idRegistroNovedad ,
    @idRegistroDetalle ,
    @accionNovedad ,
    @situacionPrimaria ,
    @fechaSituacionPrimaria ,
    @estadoCotizante ,
    @tipoNovedadReferencia ,
    @mensaje 
    )
  
    FETCH NEXT FROM @NovedadesCursor INTO
    @TipoNovedad,
    @TipoTransaccion,
    @FechaInicioNovedad,
    @FechaFinNovedad,
    @IdRegistroNovedad,
    @FechaRetiroCotizanteOUT
  END
  CLOSE @NovedadesCursor;
  DEALLOCATE @NovedadesCursor;

  RETURN
END;