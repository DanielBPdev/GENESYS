+ï»¿-- =============================================
+-- Author:		Diego Suesca
+-- Create date: 2019/03/14
+-- Description:	Inserta datos para reporte 
+-- =============================================
+CREATE PROCEDURE USP_GET_HistoricoNovedadesAfiliadosYSubsidios
+(
@fechaInicio DATE,
@fechaFin DATE,
@historico BIT = NULL
+)
+AS
+BEGIN	
SET NOCOUNT ON;
+
DECLARE @DBNAME VARCHAR(255),
		@sql NVARCHAR(4000);
	
--DROP TABLE #PersonasModificacionDatosBasicos
--DROP TABLE #FechasNovedadesSolicituNovedad
--DROP TABLE #FechasNovedadesSolicituNovedadFovis
CREATE TABLE #PersonasModificacionDatosBasicos(perId BIGINT, actualNumeroId VARCHAR(16) , actualTipoId VARCHAR(20), anteriorNumeroId VARCHAR(16), anteriorTipoId VARCHAR(20), sl VARCHAR(500) )
CREATE TABLE #FechasNovedadesSolicituNovedad(snoId BIGINT, fechaRegistroNovedadCerrada DATE, sl VARCHAR(500))
CREATE TABLE #FechasNovedadesSolicituNovedadFovis(snfId BIGINT, fechaRegistroNovedadCerrada DATE, sl VARCHAR(500))



--se obtienen las personas que se les ha cambiado el numero de identificaciÃ³n y/Ã³ el tipo de documento de identificaciÃ³n
SET @sql = '
 SELECT DISTINCT perA.perid, perA.perNumeroIdentificacion actualNumeroId, perA.perTipoIdentificacion actualTipoId,
 	    aux.perNumeroIdentificacion anteriorNumeroId, aux.perTipoIdentificacion anterioTipoId 
 FROM Persona_aud perA
 INNER JOIN ( 
	 SELECT per.perId,per.REV,per.perNumeroIdentificacion, per.perTipoIdentificacion
	 FROM Persona_aud per 
	 inner JOIN Persona_aud perAux ON perAux.perId = per.perId AND perAux.perNumeroIdentificacion <> per.perNumeroIdentificacion
	 GROUP BY per.perNumeroIdentificacion, per.perTipoIdentificacion,per.REV,per.perId 
 ) aux ON aux.perId = perA.perId AND aux.perNumeroIdentificacion <> perA.pernumeroidentificacion AND aux.Rev < perA.REV
 UNION
 SELECT DISTINCT perB.perid, perB.perNumeroIdentificacion actualNumeroId, perB.perTipoIdentificacion actualTipoId,
 	    tablaAux.perNumeroIdentificacion anteriorNumeroId, tablaAux.perTipoIdentificacion anterioTipoId  
 FROM Persona_aud perB
 INNER JOIN(
 	 SELECT  per.perId, per.REV, per.perNumeroIdentificacion, per.perTipoIdentificacion
	 FROM Persona_aud per 
	 INNER JOIN dbo.Persona_aud perAux ON perAux.perId = per.perId AND perAux.perTipoIdentificacion <> per.perTipoIdentificacion
	 GROUP BY per.perNumeroIdentificacion, per.perTipoIdentificacion,per.REV,per.perId 
 ) tablaAux ON tablaAux.perId = perB.perId AND tablaAux.perTipoIdentificacion <> perB.perTipoIdentificacion  AND tablaAux.Rev < perB.REV '
 

INSERT INTO #PersonasModificacionDatosBasicos (perId , actualNumeroId, actualTipoId, anteriorNumeroId, anteriorTipoId, sl)
EXEC sp_execute_remote CoreAudReferenceData, @sql	

--SE OBTIENEN LAS FECHAS DE REGISTRO DE LAS NOVEDADES QUE ESTUVIERON APROBADAS Y LUEGO CERRADAS CON EL ID DE LA SOLICITUD DE NOVEDAD
SET @sql = '
SELECT sno.snoId, CAST(CONVERT(VARCHAR(10), dateadd(second, revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'' ) AS DATE) AS fechaRegistroNovedadCerrada
FROM SolicitudNovedad_aud sno 
INNER JOIN Revision rev ON (sno.REV = rev.revId)
INNER JOIN(
	SELECT sno1.snoId, sno1.REV  
	FROM  SolicitudNovedad_aud sno1 
	WHERE sno1.snoEstadoSolicitud = ''APROBADA''
	GROUP BY sno1.snoId,sno1.REV 
) aux ON aux.snoId = sno.snoId AND aux.REV < sno.REV
WHERE sno.snoEstadoSolicitud = ''CERRADA''
GROUP BY sno.snoId, revTimeStamp'

INSERT INTO #FechasNovedadesSolicituNovedad(snoId, fechaRegistroNovedadCerrada, sl)
EXEC sp_execute_remote CoreAudReferenceData, @sql	

+   --SE OBTIENEN LAS FECHAS DE REGISTRO DE LAS NOVEDADES DE FOVISO QUE FUERON APROBADAS Y LUEGO CERRADAS CON EL ID DE LA SOLICITUD DE NOVEDAD FOVIS
SET @sql = '
	SELECT sno.snfId, CAST(CONVERT(VARCHAR(10), dateadd(second, revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'' ) AS DATE) AS fechaRegistroNovedadCerrada
FROM SolicitudNovedadFovis_aud sno 
INNER JOIN Revision rev ON (sno.REV = rev.revId)
INNER JOIN(
	SELECT sno1.snfId, sno1.REV  
	FROM SolicitudNovedadFovis_aud sno1 
	WHERE sno1.snfEstadoSolicitud = ''NOV_FOVIS_APROBADA''
	GROUP BY sno1.snfId,sno1.REV 
) aux ON aux.snfId = sno.snfId AND aux.REV < sno.REV
WHERE sno.snfEstadoSolicitud = ''NOV_FOVIS_CERRADA''
GROUP BY sno.snfId, revTimeStamp'

INSERT INTO #FechasNovedadesSolicituNovedadFovis(snfId, fechaRegistroNovedadCerrada, sl)
EXEC sp_execute_remote CoreAudReferenceData, @sql	
+
IF @historico = 1
BEGIN
	INSERT rno.HistoricoNovedadesAfiliadosYSubsidios(
		hnaFechaHistorico,
		hnaTipoRegistro,
		hnaCodigoCCF,
		hnaTipoIdAfiliado,
		hnaNumeroIdAfiliado,
		hnaCodigoGeneroAfiliado,
		hnaFechaNacimientoAfiliado,
		hnaPrimerApellidoAfiliado,
		hnaSegundoApellidoAfiliado,
		hnaPrimerNombreAfiliado,
		hnaSegundoNombreAfiliado,
		hnaCodigoNovedad,
		hnaTipoIdAfiliado2,
		hnaNumeroIdAfiliado2,
		hnaCodigoGeneroAfiliado2,
		hnaFechaNacimientoAfiliado2,
		hnaPrimerApellidoAfiliado2,
		hnaSegundoApellidoAfiliado2,
		hnaPrimerNombreAfiliado2,
		hnaSegundoNombreAfiliado2,
		hnaDepartamentoAfiliado,
		hnaMunicipioAfiliado,
		hnaFechaAfiliacion,
		hnaCodigoTipoAfiliado,
		hnaTipoIdAportante,
		hnaNumeroIdAportante,
		hnaDigitoVerificacionAportante,
		hnaRazonSocialAportante,
		hnaFechaVinculacionAportante,
		hnaDepartamentoUbicacion,
		hnaMunicipioUbicacion,
		hnaCodigoTipoMiembroPoblacionCubierta,
		hnaTipoIdMiembroPoblacionCubierta,
		hnaNumeroIdMiembroPoblacionCubierta,
		hnaCodigoCondicionBeneficiario,
		hnaCodigoTipoRelacionConAfiliadoBen,
		hnaFechaAsignacionSubsidio,
		hnaValorSubsidio,
		hnaCodigoTipoSubsidio,
		hnaEstadoSubsidio,
		hnaDepartamentoSubsidio,
		hnaMunicipioSubsidio,
		hnaFechaEntregaUltimoSubsidio,
		hnaFechaDesvinculacionAportante,
		hnaFechaRetiroAfiliado,
		hnaFechaFallecimiento,
		hnaTipoIdbeneficiario,
		hnaNumeroIdBeneficiario,
		hnaIdentificadorUnicoSubsidio,
		hnaNuevoTipoIdMiembrePoblacionCubierta,
		hnaNuevoNumeroIdMiembroPoblacionCubierta,
		hnaCausaRetiro,
		hnaFechaInicialReporte,
		hnaFechaFinalReporte)
	    SELECT @fechaFin,
    2 AS tipoRegistro,
    (SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') AS codigoCCF,
    CASE perAfiCore.perTipoIdentificacion 
        WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
        WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
        WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
        WHEN 'PASAPORTE' THEN 'PA'
        WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
    END AS tipoIdAfiliado,
    perAfiCore.perNumeroIdentificacion AS numeroIdAfiliado,
    CASE perDetAfiCore.pedGenero
      WHEN 'MASCULINO' THEN 'M'
      ELSE 'F'
    END AS codigoGeneroAfiliado,
    perDetAfiCore.pedFechaNacimiento AS fechaNacimientoAfiliado,
    perAfiCore.perPrimerApellido AS primerApellidoAfiliado,
    perAfiCore.perSegundoApellido AS segundoApellidoAfiliado,
    perAfiCore.perPrimerNombre AS primerNombreAfiliado,
    perAfiCore.perSegundoNombre AS segundoNombreAfiliado,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')
           THEN 'C01'
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' THEN 'C02'
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO',
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')
        THEN 'C03' 
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE') THEN 'C04'
      WHEN (
          SELECT CASE WHEN
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benCore.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1 THEN 'C07' 
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
         (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL ) THEN 'C08'
       WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
           ) = 1 THEN 'C09' 
       WHEN afiModificacionCore.perId IS NOT NULL OR benModificacionCore.perId IS NOT NULL THEN 'C10'  
    END AS codigoNovedad,
    --==== INICIA NOVEDAD C02 =====
    CASE  
        WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
           perAfiCore.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
        WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
           perAfiCore.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
        WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
           perAfiCore.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
        WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
           perAfiCore.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
        WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
           perAfiCore.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD' 
        ELSE NULL
    END AS tipoIdAfiliado2,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' -- CODIGO NOVEDAD C02
      THEN perAfiCore.perNumeroIdentificacion
      ELSE NULL
    END AS numeroIdAfiliado2,
    --========= FIN NOVEDAD C02 ========
    --========= INICIA NOVEDAD C01 =====
    CASE 
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01 
         AND perDetAfiCore.pedGenero = 'FEMENINO' THEN 'F'
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
         AND perDetAfiCore.pedGenero = 'MASCULINO' THEN 'M'
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
         AND perDetAfiCore.pedGenero ='INDEFINIDO' THEN 'I'
      ELSE NULL
    END AS codigoGeneroAfiliado2,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')--codigo noveddad c01
      THEN perDetAfiCore.pedFechaNacimiento 
      ELSE NULL
    END AS fechaNacimientoAfiliado2,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')--codigo noveddad c01
      THEN perAfiCore.perPrimerApellido 
      ELSE NULL
    END AS primerApellidoAfiliado2,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')--codigo noveddad c01
      THEN perAfiCore.perSegundoApellido 
      ELSE NULL
    END AS segundoApellidoAfiliado2,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
      THEN perAfiCore.perPrimerNombre 
      ELSE NULL
    END AS primerNombreAfiliado2,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
      THEN perAfiCore.perSegundoNombre 
      ELSE NULL
    END AS segundoNombreAfiliado2,
    CASE 
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')
      THEN depAfiCore.depCodigo
      ELSE NULL
    END AS departamentoAfiliado,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
      THEN munAfiCore.munCodigo 
      ELSE NULL
    END AS municipioAfiliado,
    --========= FIN NOVEDAD C01 ================
    --====== INICIA NOVEDAD C03 Y C07 ============
    CASE
      WHEN (
          SELECT CASE WHEN
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benCore.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1
      THEN rolAfiCore.roaFechaIngreso 
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --CODIGO NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO') THEN empleadorAportanteCore.empFechaCambioEstadoAfiliacion
        WHEN  dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION', -- CODIGO C03
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO') THEN rolAfiCore.roaFechaIngreso 
      ELSE NULL
    END AS fechaAfiliacion,
    --====== FIN NOVEDAD C07 ============
    ---===== INICIA NOVEDAD C03 ========
    CASE 
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
          AND rolAfiCore.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE' THEN 1
      WHEN ( dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
        AND  rolAfiCore.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' THEN 2 --2 - Trabajador afiliado facultativo
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')) 
         AND rolAfiCore.roaTipoAfiliado = 'PENSIONADO' THEN 3
      ELSE NULL
    END AS codigoTipoAfiliado,
    ---===== INICIA NOVEDAD C04, CONTINUA C03 ========
    CASE 
      WHEN ( (aporteGenAfiCore.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')) 
         AND perAportanteAfiCore.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
        WHEN ( (aporteGenAfiCore.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         AND perAportanteAfiCore.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
        WHEN ( (aporteGenAfiCore.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         AND perAportanteAfiCore.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
        WHEN ( (aporteGenAfiCore.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         AND perAportanteAfiCore.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
        WHEN ( (aporteGenAfiCore.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         AND perAportanteAfiCore.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD'
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         AND perEmpAportanteAfiCore.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
        WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         AND perEmpAportanteAfiCore.perTipoIdentificacion ='TARJETA_IDENTIDAD' THEN 'TI'
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         AND perEmpAportanteAfiCore.perTipoIdentificacion ='CEDULA_EXTRANJERIA' THEN 'CE'
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         AND perEmpAportanteAfiCore.perTipoIdentificacion ='PASAPORTE' THEN 'PA'
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         AND perEmpAportanteAfiCore.perTipoIdentificacion ='CARNE_DIPLOMATICO' THEN 'CD' 
      ELSE NULL
    END AS tipoIdAportante,
    CASE 
      WHEN ( (aporteGenAfiCore.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         THEN perAportanteAfiCore.perNumeroIdentificacion 
      WHEN ( (aporteGenAfiCore.apgPersona IS NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         THEN perEmpAportanteAfiCore.perNumeroIdentificacion 
      ELSE NULL
    END AS numeroIdAportante,
    CASE 
      WHEN ( (aporteGenAfiCore.apgPersona IS NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         THEN perEmpAportanteAfiCore.perDigitoVerificacion
      ELSE NULL
    END AS digitoVerificacionAportante,
    --======== FIN NOVEDAD C04 =========
    --==================================
    CASE 
      WHEN (aporteGenAfiCore.apgPersona IS NOT NULL AND  dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
      THEN CONCAT(perAportanteAfiCore.perPrimerNombre,' ',perAportanteAfiCore.perSegundoNombre, ' ', perAportanteAfiCore.perPrimerApellido, ' ',perAportanteAfiCore.perSegundoApellido)
      WHEN (aporteGenAfiCore.apgPersona IS NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
      THEN perEmpAportanteAfiCore.perRazonSocial
      ELSE NULL
    END AS razonSocialAportante,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')
      THEN aporteGenAfiCore.roaFechaAfiliacion 
      ELSE NULL
    END AS fechaVinculacionAportante,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')
      THEN depEmpAportanteAfiCore.depCodigo 
      ELSE NULL
    END AS departamentoUbicacion,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')
      THEN munEmpAportanteAfiCore.munCodigo 
      ELSE NULL
    END AS municipioUbicacion,
    --========================= FIN NOVEDAD C03 ==================
    --============================================================
    --ESTADO AFILIACIÃ“N 21: NO SE MUESTRA EN NINGUNA DE LAS 'NOVEDADES'
    --AL DIA (1- AL DIA O 2- EN MORA) 22: NO SE MUESTRA EN NINGUNA DE LAS 'NOVEDADES'
    --- ================= INICIO NOVEDAD 'C07' ================-----
    CASE
      WHEN ((
          SELECT CASE WHEN --NOVEDAD C07
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benCore.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1 OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
           AND tablaNuevoAfiBen.idBenAAfi IS NOT NULL THEN 1
      WHEN ((
          SELECT CASE WHEN  --NOVEDAD C07
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benCore.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1 OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')) 
           AND tablaNuevoAfiBen.idAfiABen IS NOT NULL  THEN 2
           
      ELSE NULL
    END AS codigoTipoMiembroPoblacionCubierta, 
    ---==== FIN ULTIMO CAMPO NOVEDAD C03 ============
    --- === NOVEDADES 'C07' Y 'C08' =================
    CASE   
      WHEN ((
          SELECT CASE WHEN  --NOVEDAD C07
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benCore.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1 AND tablaNuevoAfiBen.idBenAAfi IS NOT NULL) OR ( dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
         (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL )  AND rolAfiCore.roaFechaRetiro IS NOT NULL)
      THEN perAfiCore.perTipoIdentificacion
      WHEN ((
          SELECT CASE WHEN
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benCore.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1 AND tablaNuevoAfiBen.idAfiABen IS NOT NULL) OR (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
         (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL ) AND  benCore.benFechaRetiro IS NOT NULL)
      THEN perBenCore.perTipoIdentificacion
      WHEN benModificacionCore.perId IS NOT NULL THEN benModificacionCore.anteriorTipoId --codigoNovedad = 'C10'
      WHEN afiModificacionCore.perId IS NOT NULL THEN afiModificacionCore.anteriorTipoId --codigoNovedad = 'C10'
    END AS tipoIdMiembroPoblacionCubierta,     
    CASE
      WHEN ((
          SELECT CASE WHEN
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benCore.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1  AND tablaNuevoAfiBen.idBenAAfi IS NOT NULL) OR (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
         (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL ) AND rolAfiCore.roaFechaRetiro IS NOT NULL)
      THEN perAfiCore.perNumeroIdentificacion
      WHEN ((
          SELECT CASE WHEN
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benCore.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1  AND tablaNuevoAfiBen.idAfiABen IS NOT NULL) OR (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
         (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL ) AND benCore.benFechaRetiro IS NOT NULL)
      THEN perBenCore.perNumeroIdentificacion
      WHEN  benModificacionCore.perId IS NOT NULL THEN benModificacionCore.anteriorNumeroId --codigoNovedad = 'C10'
      WHEN  afiModificacionCore.perId IS NOT NULL THEN afiModificacionCore.anteriorNumeroId --codigoNovedad = 'C10'
      ELSE NULL
    END AS numeroIdMiembroPoblacionCubierta,   
    --- ==== FIN NOVEDAD 'C08' ========
    
    CASE 
      WHEN benCore.benGradoAcademico IS NOT NULL AND benDetCore.bedCertificadoEscolaridad = 1 THEN 'E' --ESTUDIANTE
      WHEN benCore.benGradoAcademico IS NULL AND benDetCore.bedCertificadoEscolaridad = 0 THEN 'D' --DISCAPACITADO
      ELSE NULL
    END AS codigoCondicionBeneficiario, 
    CASE
      WHEN --codigoNovedad = 'C07' 
        (
          SELECT CASE WHEN
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benCore.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1  AND benCore.benTipoBeneficiario = 'CONYUGE' THEN 1
      WHEN (
          SELECT CASE WHEN
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benCore.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1 AND benCore.benTipoBeneficiario IN ('HIJO_BIOLOGICO','HIJO_ADOPTIVO','HIJASTRO','BENEFICIARIO_EN_CUSTODIA') THEN 2
      WHEN (
          SELECT CASE WHEN
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benCore.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1 AND benCore.benTipoBeneficiario IN ('PADRE','MADRE') THEN 3
      WHEN (
          SELECT CASE WHEN
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benCore.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1 AND benCore.benTipoBeneficiario IN ('HERMANO_HUERFANO_DE_PADRES','HERMANO_HOGAR') THEN 4
      ELSE NULL
    END AS codigoTipoRelacionConAfiliadoBen,
    --- ================= FIN NOVEDAD 'C07' =====================-----
    --- ================= INICIO NOVEDAD 'C09' ====================----
    CASE
     WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
         ) = 1
         THEN dbo.SolicitudLiquidacionSubsidio.slsFechaInicio
         ELSE NULL
    END AS fechaAsignacionSubsidio, 
    CASE
     WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
         ) = 1
         THEN LEFT(CAST(detalle.dsaValorSubsidioMonetario  AS VARCHAR), CHARINDEX('.', CAST(detalle.dsaValorSubsidioMonetario  AS VARCHAR)) - 1)
       ELSE NULL
    END AS valorSubsidio,
    CASE
     WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
         ) = 1
         THEN 1
         ELSE NULL
    END AS codigoTipoSubsidio, -- toma valor 1 para cuota monetaria 
    CASE 
      WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
           ) = 1 AND (cuenta.casEstadoTransaccionSubsidio = 'GENERADO' OR 
         cuenta.casEstadoTransaccionSubsidio = 'ENVIADO' OR 
         cuenta.casEstadoTransaccionSubsidio = 'APLICADO') THEN 1
      WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
           ) = 1 AND cuenta.casEstadoTransaccionSubsidio = 'RETENIDO' THEN 2
        WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
           ) = 1 AND cuenta.casEstadoTransaccionSubsidio = 'COBRADO'  THEN 3
        WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
           ) = 1 AND cuenta.casEstadoTransaccionSubsidio = ''  THEN 4 --FALTA SABER EL ESTADO 4
        WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
         ) = 1 AND cuenta.casEstadoTransaccionSubsidio = 'ANULADO'  THEN 5 
    END AS estadoSubsidio,        --codigoNovedad = 'C09'
    CASE
      WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
           ) = 1
      THEN (SELECT prmValor FROM dbo.Parametro WHERE prmNombre = 'CAJA_COMPENSACION_DEPTO_ID') 
      ELSE NULL
    END AS departamentoSubsidio, --codigoNovedad = 'C09'
    CASE
      WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
           ) = 1
      THEN (SELECT prmValor FROM dbo.Parametro WHERE prmNombre = 'CAJA_COMPENSACION_MUNI_ID') 
      ELSE NULL
    END AS municipioSubsidio,   --codigoNovedad = 'C09'
    CASE
      WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
           ) = 1
      THEN (
           SELECT TOP 1 det.dsaFechaTransaccionRetiro
           FROM dbo.DetalleSubsidioAsignado AS det
           INNER JOIN dbo.CuentaAdministradorSubsidio ON dbo.CuentaAdministradorSubsidio.casId = det.dsaCuentaAdministradorSubsidio
           AND dbo.CuentaAdministradorSubsidio.casEstadoTransaccionSubsidio = 'COBRADO'
           WHERE det.dsaId = detalle.dsaId
           ORDER BY det.dsaFechaTransaccionRetiro DESC
         ) 
         ELSE NULL
     END AS fechaEntregaUltimoSubsidio,
    --- ================= FIN NOVEDAD 'C09' =====================
    --===================================================
    --================== INICIO NOVEDAD C04 ===========================
    CASE
      WHEN  dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE') --codigoNovedad = 'C04'
      THEN aporteGenAfiCore.roaFechaRetiro
      ELSE NULL
    END AS fechaDesvinculacionAportante,
     ---== NOVEDADES C04 Y C08 ====---
    CASE
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN('RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_TRABAJADOR_DEPENDIENTE')) --codigoNovedad = 'C04' 
         OR ( dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS', --codigoNovedad = 'C08'
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
         (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL )  AND rolAfiCore.roaFechaRetiro IS NOT NULL)
      THEN rolAfiCore.roaFechaRetiro
      WHEN ( dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS', --codigoNovedad = 'C08'
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
         (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL ) AND  benCore.benFechaRetiro IS NOT NULL) THEN benCore.benFechaRetiro
      ELSE NULL
    END AS fechaRetiroAfiliado,
    CASE
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN('RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_TRABAJADOR_DEPENDIENTE') --codigoNovedad = 'C04'
          AND perDetAfiCore.pedFechaFallecido IS NOT NULL)
         THEN perDetAfiCore.pedFechaFallecido 
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN('RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_TRABAJADOR_DEPENDIENTE') --codigoNovedad = 'C04'
          AND perDetBenCore.pedFechaFallecido IS NOT NULL) THEN perDetBenCore.pedFechaFallecido
      ELSE NULL
    END AS fechaFallecimiento,
    --============= FIN NOVEDAD C04  ===========
    --- ================= INICIO NOVEDAD 'C09' ====================----
    CASE  
      WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
         ) = 1  AND perBenCore.perTipoIdentificacion = 'REGISTRO_CIVIL' THEN 'RC'
        WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
         ) = 1 AND perBenCore.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
        WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
         ) = 1 AND perBenCore.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
        WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
         ) = 1  AND perBenCore.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
        WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
         ) = 1  AND perBenCore.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
        WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
         ) = 1  AND perBenCore.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD'
        ELSE NULL
    END AS tipoIdbeneficiario, --codigoNovedad = 'C09'
    CASE
      WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
         ) = 1  
      THEN perBenCore.perNumeroIdentificacion  --codigoNovedad = 'C09'
      ELSE NULL
    END AS numeroIdBeneficiario, 
    
    --- ================= FIN NOVEDAD 'C09' =====================
    /*CASE perDetBenFovis.pedGenero   --TODO: DEL CAMPO 40 AL 54 NO SE USAN
      WHEN 'MASCULINO' THEN 'M'
      ELSE 'F'
    END AS codigoGeneroBeneficiario,
    perDetBenFovis.pedFechaExpedicionDocumento AS fechaNacimientoBeneficiario,
    perBenFovis.perPrimerApellido AS primerApellidoBeneficiario,
    perBenFovis.perSegundoApellido AS segundoApellidoBeneficiario,
    perBenFovis.perPrimerNombre  AS primerNombreBeneficiario,
    perBenFovis.perSegundoNombre AS segundoNombreBeneficiario,
    CASE perEmpAportanteAfi.perTipoIdentificacion--perEmpresaFovis.perTipoIdentificacion
        WHEN 'CEDULA_CIUDADANIA' THEN 1
      WHEN 'NIT' THEN 2
    END AS tipoIdEmpresaRecibeSubsidio,
    --perEmpresaFovis.perNumeroIdentificacion
    perEmpAportanteAfi.perNumeroIdentificacion AS numeroIdEmpresaRecibeSubsidio,
    --perEmpresaFovis.perDigitoVerificacion 
    perEmpAportanteAfi.perDigitoVerificacion AS digitoVerificacionIdEmpresaSubsidio,
    --perEmpresaFovis.perRazonSocial 
    perEmpAportanteAfi.perRazonSocial AS razonSocialEmpresaSubsidio,
    1 AS aQuienSeOtorgoSubsidio, 
    ':fechaInicio' AS fechaInicialPeriodoInformacion,
    ':fechaFin' AS fechaFinalPeriodoInformacion,
    0 AS totalRegistroRelacionadosArchivo,
    ':nombreReporte' AS nombreDelArchivo,*/ 
    CASE
      WHEN (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
         ) = 1  
      THEN dbo.SolicitudLiquidacionSubsidio.slsId --codigoNovedad = 'C09' 
      ELSE NULL
    END AS identificadorUnicoSubsidio,
    CASE
      WHEN benModificacionCore.perId IS NOT NULL THEN benModificacionCore.actualTipoId --codigoNovedad = 'C10'
      WHEN afiModificacionCore.perId IS NOT NULL THEN afiModificacionCore.actualTipoId --codigoNovedad = 'C10'
    END AS nuevoTipoIdMiembrePoblacionCubierta,  --CAMPO 56
    CASE
      WHEN benModificacionCore.perId IS NOT NULL THEN benModificacionCore.actualNumeroId --codigoNovedad = 'C10'
      WHEN afiModificacionCore.perId IS NOT NULL THEN afiModificacionCore.actualNumeroId --codigoNovedad = 'C10'
    END AS nuevoNumeroIdMiembroPoblacionCubierta, -- CAMPO57
    CASE
      WHEN ( (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
         (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL ))
         AND rolAfiCore.roaFechaRetiro IS NOT NULL AND rolAfiCore.roaMotivoDesafiliacion = 'DESAFILIACION_EMPLEADOR') 
         THEN 1
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
         (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL )) 
          AND ((rolAfiCore.roaFechaRetiro IS NOT NULL AND rolAfiCore.roaMotivoDesafiliacion = 'FALLECIMIENTO') OR
         (benCore.benFechaRetiro IS NOT NULL AND benCore.benMotivoDesafiliacion = 'FALLECIMIENTO')) THEN 2
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
         (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL )) 
         AND (rolAfiCore.roaMotivoDesafiliacion = 'RETIRO_POR_CAMBIO_DE_CAJA_COMPENSACION' OR
         empleadorAportanteCore.empMotivoDesafiliacion = 'RETIRO_POR_TRASLADO_OTRA_CCF' OR benCore.benMotivoDesafiliacion = 'RETIRO_POR_CAMBIO_DE_CAJA_COMPENSACION' )
         THEN 3
        WHEN ( (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
         (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL ))
            AND dbo.ParametrizacionNovedad.novTipoTransaccion = 'SUSTITUCION_PATRONAL') THEN 6
        WHEN ((dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
         (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL )) AND empleadorAportanteCore.empMotivoDesafiliacion = 'FUSION_ADQUISICION' 
          OR  dbo.ParametrizacionNovedad.novTipoTransaccion = 'DESAFILIAR_AUTOMATICAMENTE_EMPLEADORES_SOLICITUD_RECHAZADA'
          AND rolAfiCore.roaMotivoDesafiliacion = 'DESAFILIACION_EMPLEADOR') THEN 7
      --TODO: FALTAN LAS CAUSAS DE RETIRO DE LA 4 A LA 12
      WHEN ( (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
         (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL )) 
         AND benCore.benFechaRetiro IS NOT NULL AND benCore.benMotivoDesafiliacion = 'OTROS' ) THEN 30 
      ELSE NULL
    END AS causaRetiro,
    @fechaInicio,
    @fechaFin
    FROM dbo.SolicitudLiquidacionSubsidio
    INNER JOIN dbo.DetalleSubsidioAsignado AS detalle ON dbo.SolicitudLiquidacionSubsidio.slsId = detalle.dsaSolicitudLiquidacionSubsidio
    AND detalle.dsaEstado = 'DERECHO_ASIGNADO'
    INNER JOIN dbo.CuentaAdministradorSubsidio AS cuenta ON detalle .dsaCuentaAdministradorSubsidio = cuenta.casId
    INNER JOIN dbo.Afiliado AS afiCore ON detalle.dsaAfiliadoPrincipal = afiCore.afiId
    INNER JOIN dbo.RolAfiliado AS rolAfiCore ON rolAfiCore.roaAfiliado = afiCore.afiId
    INNER JOIN dbo.Persona AS perAfiCore ON afiCore.afiPersona = perAfiCore.perId
    INNER JOIN dbo.Ubicacion AS ubiAfiCore ON ubiAfiCore.ubiId = perAfiCore.perUbicacionPrincipal
    INNER JOIN dbo.Municipio AS munAfiCore ON munAfiCore.munId = ubiAfiCore.ubiMunicipio
    INNER JOIN dbo.Departamento AS depAfiCore ON depAfiCore.depId = munAfiCore.munDepartamento
    INNER JOIN dbo.PersonaDetalle AS perDetAfiCore ON perAfiCore.perId = perDetAfiCore.pedPersona
    INNER JOIN dbo.AporteDetallado AS aporteDetAfiCore ON aporteDetAfiCore.apdPersona = afiCore.afiPersona
    LEFT JOIN (
          SELECT dbo.AporteGeneral.apgId, dbo.AporteGeneral.apgPersona, dbo.AporteGeneral.apgEmpresa, 
               dbo.AporteGeneral.apgFechaProcesamiento, afiRol.roaFechaAfiliacion, afiRol.roaFechaRetiro
          FROM dbo.AporteGeneral
          LEFT JOIN dbo.Empleador AS empleadorAporte ON empleadorAporte.empEmpresa = dbo.AporteGeneral.apgEmpresa
          LEFT JOIN dbo.RolAfiliado AS afiRol ON afiRol.roaEmpleador = empleadorAporte.empId
          --WHERE dbo.AporteGeneral.apgId = aporteDetAfiCore.apdAporteGeneral
          GROUP BY dbo.AporteGeneral.apgId, dbo.AporteGeneral.apgPersona, dbo.AporteGeneral.apgEmpresa, 
               dbo.AporteGeneral.apgFechaProcesamiento, afiRol.roaFechaAfiliacion, afiRol.roaFechaRetiro 
          --ORDER BY dbo.AporteGeneral.apgFechaProcesamiento DESC
    ) AS aporteGenAfiCore ON aporteGenAfiCore.apgId = aporteDetAfiCore.apdAporteGeneral
    LEFT JOIN dbo.Persona AS perAportanteAfiCore ON perAportanteAfiCore.perId = aporteGenAfiCore.apgPersona
    LEFT JOIN dbo.Empresa AS empAportanteAfiCore ON empAportanteAfiCore.empId = aporteGenAfiCore.apgEmpresa
    LEFT JOIN dbo.Empleador AS empleadorAportanteCore ON (empAportanteAfiCore.empId = empleadorAportanteCore.empEmpresa
                            AND detalle.dsaEmpleador = empleadorAportanteCore.empId)
    LEFT JOIN dbo.Persona AS perEmpAportanteAfiCore ON perEmpAportanteAfiCore.perId = empAportanteAfiCore.empPersona
    LEFT JOIN dbo.Ubicacion AS ubiEmpAportanteAfiCore ON ubiEmpAportanteAfiCore.ubiId = perEmpAportanteAfiCore.perUbicacionPrincipal
    LEFT JOIN dbo.Municipio AS munEmpAportanteAfiCore ON munEmpAportanteAfiCore.munId = ubiEmpAportanteAfiCore.ubiMunicipio
    LEFT JOIN dbo.Departamento AS depEmpAportanteAfiCore ON depEmpAportanteAfiCore.depId = munEmpAportanteAfiCore.munDepartamento
    INNER JOIN dbo.Beneficiario AS benCore ON detalle.dsaBeneficiarioDetalle = benCore.benBeneficiarioDetalle
    INNER JOIN dbo.BeneficiarioDetalle AS benDetCore ON benDetCore.bedId = benCore.benBeneficiarioDetalle
    INNER JOIN dbo.Persona AS perBenCore ON perBenCore.perId = benCore.benPersona
    INNER JOIN dbo.PersonaDetalle AS perDetBenCore ON perDetBenCore.pedPersona = perBenCore.perId
    --INNER JOIN dbo.Empleador ON detalle.dsaEmpleador = dbo.Empleador.empId
    --INNER JOIN dbo.Empresa ON dbo.Empleador.empEmpresa = dbo.Empleador.empId
    --INNER JOIN dbo.Persona AS perEmpresaCore ON dbo.Empresa.empPersona = perEmpresaCore.perId
    INNER JOIN dbo.Solicitud ON dbo.SolicitudLiquidacionSubsidio.slsSolicitudGlobal = dbo.Solicitud.solId
    LEFT JOIN dbo.SolicitudNovedad ON dbo.Solicitud.solId = dbo.SolicitudNovedad.snoSolicitudGlobal
    LEFT JOIN #FechasNovedadesSolicituNovedad AS novedadesAprobadas ON novedadesAprobadas.snoId = dbo.SolicitudNovedad.snoId
    LEFT JOIN dbo.ParametrizacionNovedad ON dbo.SolicitudNovedad.snoNovedad = dbo.ParametrizacionNovedad.novId
    LEFT JOIN dbo.NovedadDetalle ON (dbo.SolicitudNovedad.snoId = dbo.NovedadDetalle.nopId AND dbo.NovedadDetalle.nopVigente = 1)
    LEFT JOIN 
    (
      SELECT personaAfiliadoABeneficiario.perId AS idAfiABen, NULL AS idBenAAfi --se obtienen los afiliados que pasaron a ser beneficiarios
      FROM dbo.Afiliado AS afi1
      INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
             AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
      INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
      INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
             --AND benFovis.benId = ben1.benId
             )
      UNION
      SELECT NULL AS idAfiABen, personaBeneficiarioAAFiliado.perId AS idBenAAfi -- se obtienen los beneficiarios que pasaron a ser afiliados
      FROM dbo.Beneficiario AS ben1
      INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
      INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona --AND afi1.afiId = afiFovis.afiId
      INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
      WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
    ) AS tablaNuevoAfiBen ON (tablaNuevoAfiBen.idBenAAfi = perAfiCore.perId OR tablaNuevoAfiBen.idAfiABen = perBenCore.perId)
    --LEFT JOIN dbo.SolicitudNovedadFovis ON dbo.Solicitud.solId = dbo.SolicitudNovedadFovis.snfSolicitudGlobal
    --LEFT JOIN dbo.SolicitudNovedadPersonaFovis ON (dbo.SolicitudNovedadFovis.snfId = dbo.SolicitudNovedadPersonaFovis.spfSolicitudNovedadFovis 
    --       AND dbo.PostulacionFOVIS.pofId = dbo.SolicitudNovedadPersonaFovis.spfPostulacionFovis)
    LEFT JOIN #PersonasModificacionDatosBasicos afiModificacionCore ON afiModificacionCore.perId = perAfiCore.perId   
    LEFT JOIN #PersonasModificacionDatosBasicos benModificacionCore ON benModificacionCore.perId = perBenCore.perId 
      WHERE rolAfiCore.roaEstadoAfiliado = 'ACTIVO' AND 
    ( 
      (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
       'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS', 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS',
        'AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO',
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO',    
       'RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
       'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
       'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
       'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
       'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND  novedadesAprobadas.fechaRegistroNovedadCerrada BETWEEN @fechaInicio AND @fechaFin)
       OR (
          SELECT CASE WHEN
          EXISTS
            ( 
              SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
              WHERE abono.casId = cuenta.casId
              AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
              AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
                SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
                WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
                AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
             )
              UNION
              SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
              WHERE detalle.dsaId = detalleAux.dsaId
              AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
              'DERECHO_ASIGNADO_RETENIDO')
              AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
            )
            THEN 1
            ELSE 0 END
         ) = 1 --codigo novedad: C09
       OR (afiModificacionCore.perId IS NOT NULL AND rolAfiCore.roaFechaAfiliacion BETWEEN @fechaInicio AND @fechaFin)
       OR (benModificacionCore.perId IS NOT NULL AND benCore.benFechaAfiliacion BETWEEN @fechaInicio AND @fechaFin)
       OR 
       (
         (
          SELECT CASE WHEN
          EXISTS
          (
            SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
            FROM dbo.Afiliado AS afi1
            INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
            INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
            INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                   AND benCore.benId = ben1.benId)
            UNION
            SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
            FROM dbo.Beneficiario AS ben1
            INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
            INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
            INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
            WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
          )
          THEN 1
          ELSE 0 END
        ) = 1  AND benCore.benFechaAfiliacion BETWEEN @fechaInicio AND @fechaFin
       )
    )
    
    GROUP BY 
    dbo.SolicitudLiquidacionSubsidio.slsId, detalle.dsaId,
    perAfiCore.perTipoIdentificacion, perAfiCore.perNumeroIdentificacion, perAfiCore.perPrimerApellido, perAfiCore.perSegundoApellido,
    perAfiCore.perPrimerNombre, perAfiCore.perSegundoNombre,perDetAfiCore.pedGenero,perDetAfiCore.pedFechaNacimiento, dbo.SolicitudLiquidacionSubsidio.slsFechaInicio,
    detalle.dsaValorSubsidioMonetario, cuenta.casId, cuenta.casEstadoTransaccionSubsidio, munAfiCore.munCodigo, depAfiCore.depCodigo,
    perBenCore.perTipoIdentificacion, perBenCore.perNumeroIdentificacion, perDetBenCore.pedGenero, perDetBenCore.pedFechaExpedicionDocumento,
    perBenCore.perPrimerApellido, perBenCore.perSegundoApellido, perBenCore.perPrimerNombre, perBenCore.perSegundoNombre, dbo.SolicitudLiquidacionSubsidio.slsId,
    rolAfiCore.roaFechaIngreso,rolAfiCore.roaTipoAfiliado, rolAfiCore.roaFechaRetiro, rolAfiCore.roaMotivoDesafiliacion, aporteGenAfiCore.apgPersona, 
    perAportanteAfiCore.perTipoIdentificacion, perAportanteAfiCore.perNumeroIdentificacion, perAportanteAfiCore.perPrimerNombre ,perAportanteAfiCore.perSegundoNombre,
    perAportanteAfiCore.perPrimerApellido,perAportanteAfiCore.perSegundoApellido, perEmpAportanteAfiCore.perTipoIdentificacion, perEmpAportanteAfiCore.perTipoIdentificacion,
    perEmpAportanteAfiCore.perNumeroIdentificacion, perEmpAportanteAfiCore.perDigitoVerificacion, perEmpAportanteAfiCore.perRazonSocial, afiId, 
    aporteGenAfiCore.roaFechaAfiliacion, aporteGenAfiCore.roaFechaRetiro, depEmpAportanteAfiCore.depCodigo, munEmpAportanteAfiCore.munCodigo,
    benCore.benGradoAcademico, benDetCore.bedCertificadoEscolaridad, benCore.benTipoBeneficiario, perDetAfiCore.pedFechaFallecido, empFechaCambioEstadoAfiliacion, 
    benCore.benId, novTipoTransaccion, benCore.benFechaRetiro, benCore.benMotivoDesafiliacion, rolAfiCore.roaFechaRetiro, idBenAAfi, idAfiABen,
    rolAfiCore.roaMotivoDesafiliacion, benCore.benTipoBeneficiario, afiModificacionCore.perId, benModificacionCore.perId, empleadorAportanteCore.empMotivoDesafiliacion,
    benModificacionCore.actualNumeroId, benModificacionCore.actualTipoId, benModificacionCore.anteriorNumeroId, benModificacionCore.anteriorTipoId,
    afiModificacionCore.actualNumeroId, afiModificacionCore.actualTipoId, afiModificacionCore.anteriorNumeroId, afiModificacionCore.anteriorTipoId,
    perDetBenCore.pedFechaFallecido
    --perEmpresaCore.perTipoIdentificacion, perEmpresaCore.perNumeroIdentificacion, perEmpresaCore.perDigitoVerificacion, perEmpresaCore.perRazonSocial
+
    UNION ALL
    ---- PARTE FOVIS
    SELECT @fechaFin,
    2 AS tipoRegistro,
    (SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') AS codigoCCF,
    CASE perJefeHogarAfiFovis.perTipoIdentificacion 
        WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
        WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
        WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
        WHEN 'PASAPORTE' THEN 'PA'
        WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
    END AS tipoIdAfiliado,
    perJefeHogarAfiFovis.perNumeroIdentificacion AS numeroIdAfiliado,
    CASE perDetJefeHogarAfiFovis.pedGenero
      WHEN 'MASCULINO' THEN 'M'
      ELSE 'F'
    END AS codigoGeneroAfiliado,
    perDetJefeHogarAfiFovis.pedFechaNacimiento AS fechaNacimientoAfiliado,
    perJefeHogarAfiFovis.perPrimerApellido AS primerApellidoAfiliado,
    perJefeHogarAfiFovis.perSegundoApellido AS segundoApellidoAfiliado,
    perJefeHogarAfiFovis.perPrimerNombre AS primerNombreAfiliado,
    perJefeHogarAfiFovis.perSegundoNombre AS segundoNombreAfiliado,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')
           THEN 'C01'
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' THEN 'C02'
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO',
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')
        THEN 'C03' 
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE') THEN 'C04'
      WHEN (
          SELECT CASE WHEN
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benFovis.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1 THEN 'C07' 
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
         (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL ) THEN 'C08'
        WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL THEN 'C09' --si tiene registro en la tabla SolicituNovedadFovis es porque se realizo algÃºn cambio en el subsidio
        WHEN afiModificacionFovis.perId IS NOT NULL OR benModificacionFovis.perId IS NOT NULL THEN 'C10'  
    END AS codigoNovedad,
    --==== INICIA NOVEDAD C02 =====
    CASE  
        WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
           perJefeHogarAfiFovis.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
        WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
           perJefeHogarAfiFovis.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
        WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
           perJefeHogarAfiFovis.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
        WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
           perJefeHogarAfiFovis.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
        WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
           perJefeHogarAfiFovis.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD' 
        ELSE NULL
    END AS tipoIdAfiliado2,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' -- CODIGO NOVEDAD C02
      THEN perJefeHogarAfiFovis.perNumeroIdentificacion
      ELSE NULL
    END AS numeroIdAfiliado2,
    --========= FIN NOVEDAD C02 ========
    --========= INICIA NOVEDAD C01 =====
    CASE 
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01 
         AND perDetJefeHogarAfiFovis.pedGenero = 'FEMENINO' THEN 'F'
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
         AND perDetJefeHogarAfiFovis.pedGenero = 'MASCULINO' THEN 'M'
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
         AND perDetJefeHogarAfiFovis.pedGenero ='INDEFINIDO' THEN 'I'
      ELSE NULL
    END AS codigoGeneroAfiliado2,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')--codigo noveddad c01
      THEN perDetJefeHogarAfiFovis.pedFechaNacimiento 
      ELSE NULL
    END AS fechaNacimientoAfiliado2,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')--codigo noveddad c01
      THEN perJefeHogarAfiFovis.perPrimerApellido 
      ELSE NULL
    END AS primerApellidoAfiliado2,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')--codigo noveddad c01
      THEN perJefeHogarAfiFovis.perSegundoApellido 
      ELSE NULL
    END AS segundoApellidoAfiliado2,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
      THEN perJefeHogarAfiFovis.perPrimerNombre 
      ELSE NULL
    END AS primerNombreAfiliado2,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
      THEN perJefeHogarAfiFovis.perSegundoNombre 
      ELSE NULL
    END AS segundoNombreAfiliado2,
    CASE 
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')
      THEN depAfiFovis.depCodigo
      ELSE NULL
    END AS departamentoAfiliado,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
           'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
      THEN munAfiFovis.munCodigo 
      ELSE NULL
    END AS municipioAfiliado,
    --========= FIN NOVEDAD C01 ================
    --====== INICIA NOVEDAD C03 Y C07 ============
    CASE
      WHEN (
          SELECT CASE WHEN
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benFovis.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1
      THEN rolAfiFovis.roaFechaIngreso 
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --CODIGO NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO') THEN empleadorAportanteFovis.empFechaCambioEstadoAfiliacion
        WHEN  dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION', -- CODIGO C03
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO') THEN rolAfiFovis.roaFechaIngreso 
      ELSE NULL
    END AS fechaAfiliacion,
    --====== FIN NOVEDAD C07 ============
    ---===== INICIA NOVEDAD C03 ========
    CASE 
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
          AND rolAfiFovis.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE' THEN 1
      WHEN ( dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
        AND  rolAfiFovis.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' THEN 2 --2 - Trabajador afiliado facultativo
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')) 
         AND rolAfiFovis.roaTipoAfiliado = 'PENSIONADO' THEN 3
      ELSE NULL
    END AS codigoTipoAfiliado,
    ---===== INICIA NOVEDAD C04, CONTINUA C03 ========
    CASE 
      WHEN ( (aporteGenAfiFovis.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')) 
         AND  perAportanteAfi.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
        WHEN ( (aporteGenAfiFovis.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         AND perAportanteAfi.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
        WHEN ( (aporteGenAfiFovis.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         AND perAportanteAfi.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
        WHEN ( (aporteGenAfiFovis.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         AND perAportanteAfi.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
        WHEN ( (aporteGenAfiFovis.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         AND perAportanteAfi.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD'
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         AND perEmpAportanteAfi.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
        WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         AND perEmpAportanteAfi.perTipoIdentificacion ='TARJETA_IDENTIDAD' THEN 'TI'
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         AND perEmpAportanteAfi.perTipoIdentificacion ='CEDULA_EXTRANJERIA' THEN 'CE'
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         AND perEmpAportanteAfi.perTipoIdentificacion ='PASAPORTE' THEN 'PA'
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         AND perEmpAportanteAfi.perTipoIdentificacion ='CARNE_DIPLOMATICO' THEN 'CD' 
      ELSE NULL
    END AS tipoIdAportante,
    CASE 
      WHEN ( (aporteGenAfiFovis.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         THEN perAportanteAfi.perNumeroIdentificacion 
      WHEN ( (aporteGenAfiFovis.apgPersona IS NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         THEN perEmpAportanteAfi.perNumeroIdentificacion 
      ELSE NULL
    END AS numeroIdAportante,
    CASE 
      WHEN ( (aporteGenAfiFovis.apgPersona IS NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
         'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
         THEN perEmpAportanteAfi.perDigitoVerificacion
      ELSE NULL
    END AS digitoVerificacionAportante,
    --======== FIN NOVEDAD C04 =========
    --==================================
    CASE 
      WHEN (aporteGenAfiFovis.apgPersona IS NOT NULL AND  dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
      THEN CONCAT(perAportanteAfi.perPrimerNombre,' ',perAportanteAfi.perSegundoNombre, ' ', perAportanteAfi.perPrimerApellido, ' ',perAportanteAfi.perSegundoApellido)
      WHEN (aporteGenAfiFovis.apgPersona IS NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
      THEN perEmpAportanteAfi.perRazonSocial
      ELSE NULL
    END AS razonSocialAportante,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')
      THEN aporteGenAfiFovis.roaFechaAfiliacion 
      ELSE NULL
    END AS fechaVinculacionAportante,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')
      THEN depEmpAportanteAfi.depCodigo 
      ELSE NULL
    END AS departamentoUbicacion,
    CASE
      WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')
      THEN munEmpAportanteAfi.munCodigo 
      ELSE NULL
    END AS municipioUbicacion,
    --========================= FIN NOVEDAD C03 ==================
    --============================================================
    --ESTADO AFILIACIÃ“N 21: NO SE MUESTRA EN NINGUNA DE LAS 'NOVEDADES'
    --AL DIA (1- AL DIA O 2- EN MORA) 22: NO SE MUESTRA EN NINGUNA DE LAS 'NOVEDADES'
    --- ================= INICIO NOVEDAD 'C07' ================-----
    CASE
      WHEN ((
          SELECT CASE WHEN --NOVEDAD C07
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benFovis.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1 OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
           AND tablaNuevoAfiBen.idBenAAfi IS NOT NULL THEN 1
      WHEN ((
          SELECT CASE WHEN  --NOVEDAD C07
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benFovis.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1 OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')) 
           AND tablaNuevoAfiBen.idAfiABen IS NOT NULL  THEN 2
           
      ELSE NULL
    END AS codigoTipoMiembroPoblacionCubierta, 
    ---==== FIN ULTIMO CAMPO NOVEDAD C03 ============
    --- === NOVEDADES 'C07' Y 'C08' =================
    CASE   
      WHEN ((
          SELECT CASE WHEN
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benFovis.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1 AND tablaNuevoAfiBen.idBenAAfi IS NOT NULL) OR ( dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
         (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL )  AND rolAfiFovis.roaFechaRetiro IS NOT NULL)
      THEN perJefeHogarAfiFovis.perTipoIdentificacion
      WHEN ((
          SELECT CASE WHEN
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benFovis.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1 AND tablaNuevoAfiBen.idAfiABen IS NOT NULL) OR (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
         (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL ) AND  benFovis.benFechaRetiro IS NOT NULL)
      THEN perBenFovis.perTipoIdentificacion
      WHEN benModificacionFovis.perId IS NOT NULL THEN benModificacionFovis.anteriorTipoId --codigoNovedad = 'C10'
      WHEN afiModificacionFovis.perId IS NOT NULL THEN afiModificacionFovis.anteriorTipoId --codigoNovedad = 'C10'
    END AS tipoIdMiembroPoblacionCubierta,     
    CASE
      WHEN ((
          SELECT CASE WHEN
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benFovis.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1  AND tablaNuevoAfiBen.idBenAAfi IS NOT NULL) OR (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
         (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL ) AND rolAfiFovis.roaFechaRetiro IS NOT NULL)
      THEN perJefeHogarAfiFovis.perNumeroIdentificacion
      WHEN ((
          SELECT CASE WHEN
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benFovis.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1  AND tablaNuevoAfiBen.idAfiABen IS NOT NULL) OR (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
         (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL ) AND  benFovis.benFechaRetiro IS NOT NULL)
      THEN perBenFovis.perNumeroIdentificacion
      WHEN  benModificacionFovis.perId IS NOT NULL THEN benModificacionFovis.anteriorNumeroId --codigoNovedad = 'C10'
      WHEN  afiModificacionFovis.perId IS NOT NULL THEN afiModificacionFovis.anteriorNumeroId --codigoNovedad = 'C10'
      ELSE NULL
    END AS numeroIdMiembroPoblacionCubierta,   
    --- ==== FIN NOVEDAD 'C08' ========
    CASE 
      WHEN benFovis.benGradoAcademico IS NOT NULL AND benDetFovis.bedCertificadoEscolaridad = 1 THEN 'E' --ESTUDIANTE
      WHEN benFovis.benGradoAcademico IS NULL AND benDetFovis.bedCertificadoEscolaridad = 0 THEN 'D' --DISCAPACITADO
      ELSE NULL
    END AS codigoCondicionBeneficiario, 
    CASE
      WHEN --codigoNovedad = 'C07' 
        (
          SELECT CASE WHEN
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benFovis.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1  AND benFovis.benTipoBeneficiario = 'CONYUGE' THEN 1
      WHEN (
          SELECT CASE WHEN
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benFovis.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1 AND benFovis.benTipoBeneficiario IN ('HIJO_BIOLOGICO','HIJO_ADOPTIVO','HIJASTRO','BENEFICIARIO_EN_CUSTODIA') THEN 2
      WHEN (
          SELECT CASE WHEN
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benFovis.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1 AND benFovis.benTipoBeneficiario IN ('PADRE','MADRE') THEN 3
      WHEN (
          SELECT CASE WHEN
          EXISTS
            (
              SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
              FROM dbo.Afiliado AS afi1
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                     AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
              INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
              INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                     AND benFovis.benId = ben1.benId)
              UNION
              SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
              FROM dbo.Beneficiario AS ben1
              INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
              INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
              INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
              WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
            )
            THEN 1
            ELSE 0 END
           ) = 1 AND benFovis.benTipoBeneficiario IN ('HERMANO_HUERFANO_DE_PADRES','HERMANO_HOGAR') THEN 4
      ELSE NULL
    END AS codigoTipoRelacionConAfiliadoBen,
    --- ================= FIN NOVEDAD 'C07' =====================-----
    --- ================= INICIO NOVEDAD 'C09' ====================----
    CASE
      WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL--codigoNovedad = 'C09'
      THEN dbo.SolicitudAsignacion.safFechaAceptacion 
      ELSE NULL
    END AS fechaAsignacionSubsidio, 
    CASE
      WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL--codigoNovedad = 'C09'
      THEN LEFT(CAST(dbo.SolicitudAsignacion.safValorSFVAsignado AS VARCHAR), CHARINDEX('.', CAST(dbo.SolicitudAsignacion.safValorSFVAsignado AS VARCHAR)) - 1)
      ELSE NULL
    END AS valorSubsidio,
    CASE
      WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL--codigoNovedad = 'C09'
      THEN 4 
      ELSE NULL
    END AS codigoTipoSubsidio, -- toma valor 4 para subsidio vivienda (fovis)
    CASE 
      WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND dbo.PostulacionFOVIS.pofEstadoHogar IN ('ASIGNADO_SIN_PRORROGA','ASIGNADO_CON_PRIMERA_PRORROGA',  --codigoNovedad = 'C09' 
         'ASIGNADO_CON_SEGUNDA_PRORROGA', 'SUBSIDIO_LEGALIZADO') THEN 1
      WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND dbo.PostulacionFOVIS.pofEstadoHogar IN ('VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA',
         'VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA', 'PENDIENTE_APROBACION_PRORROGA') THEN 2
        WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND dbo.PostulacionFOVIS.pofEstadoHogar IN ('SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO','SUBSIDIO_DESEMBOLSADO') THEN 3
        WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND dbo.PostulacionFOVIS.pofEstadoHogar = 'VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA' THEN 4
        WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND dbo.PostulacionFOVIS.pofEstadoHogar = 'RECHAZADO' THEN 5 -- TODO: FALTA SABER QUE ANTES ESTUVO ASIGNADO
        ELSE NULL
    END AS estadoSubsidio,
    CASE
      WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL--codigoNovedad = 'C09'
      THEN (SELECT prmValor FROM dbo.Parametro WHERE prmNombre = 'CAJA_COMPENSACION_DEPTO_ID') 
      ELSE NULL
    END AS departamentoSubsidio,
    CASE
      WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL--codigoNovedad = 'C09'
      THEN (SELECT prmValor FROM dbo.Parametro WHERE prmNombre = 'CAJA_COMPENSACION_MUNI_ID') 
      ELSE NULL
    END AS municipioSubsidio,
    CASE
      WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL--codigoNovedad = 'C09'
      THEN Legalizacion.fechaTransferencia 
      ELSE NULL
    END AS fechaEntregaUltimoSubsidio, 
    --- ================= FIN NOVEDAD 'C09' =====================
    --===================================================
    --================== INICIO NOVEDAD C04 ===========================
    CASE
      WHEN  dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE') --codigoNovedad = 'C04'
      THEN aporteGenAfiFovis.roaFechaRetiro
      ELSE NULL
    END AS fechaDesvinculacionAportante,
     ---== NOVEDADES C04 Y C08 ====---
    CASE
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN('RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_TRABAJADOR_DEPENDIENTE')) --codigoNovedad = 'C04' 
         OR ( dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS', --codigoNovedad = 'C08'
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
         (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL )  AND rolAfiFovis.roaFechaRetiro IS NOT NULL)
      THEN rolAfiFovis.roaFechaRetiro
      WHEN ( dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS', --codigoNovedad = 'C08'
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
         (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL ) AND  benFovis.benFechaRetiro IS NOT NULL) THEN benFovis.benFechaRetiro
      ELSE NULL
    END AS fechaRetiroAfiliado,
    CASE
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN('RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_TRABAJADOR_DEPENDIENTE') --codigoNovedad = 'C04'
          AND perDetJefeHogarAfiFovis.pedFechaFallecido IS NOT NULL)
         THEN perDetJefeHogarAfiFovis.pedFechaFallecido 
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN('RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_TRABAJADOR_DEPENDIENTE') --codigoNovedad = 'C04'
          AND  perDetBenFovis.pedFechaFallecido IS NOT NULL) THEN perDetBenFovis.pedFechaFallecido
      ELSE NULL
    END AS fechaFallecimiento,
    --============= FIN NOVEDAD C04  ===========
    --- ================= INICIO NOVEDAD 'C09' ====================----
    CASE  
      WHEN --codigoNovedad = 'C09'
         dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND perBenFovis.perTipoIdentificacion = 'REGISTRO_CIVIL' THEN 'RC'
        WHEN --codigoNovedad = 'C09' 
           dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND perBenFovis.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
        WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND perBenFovis.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
        WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND perBenFovis.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
        WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND perBenFovis.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
        WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND perBenFovis.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD'
        ELSE NULL
    END AS tipoIdbeneficiario,
    CASE
      WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL --codigoNovedad = 'C09' 
      THEN perBenFovis.perNumeroIdentificacion
      ELSE NULL
    END AS numeroIdBeneficiario,
    --- ================= FIN NOVEDAD 'C09' =====================
    /*CASE perDetBenFovis.pedGenero   --TODO: DEL CAMPO 40 AL 54 NO SE USAN
      WHEN 'MASCULINO' THEN 'M'
      ELSE 'F'
    END AS codigoGeneroBeneficiario,
    perDetBenFovis.pedFechaExpedicionDocumento AS fechaNacimientoBeneficiario,
    perBenFovis.perPrimerApellido AS primerApellidoBeneficiario,
    perBenFovis.perSegundoApellido AS segundoApellidoBeneficiario,
    perBenFovis.perPrimerNombre  AS primerNombreBeneficiario,
    perBenFovis.perSegundoNombre AS segundoNombreBeneficiario,
    CASE perEmpAportanteAfi.perTipoIdentificacion--perEmpresaFovis.perTipoIdentificacion
        WHEN 'CEDULA_CIUDADANIA' THEN 1
      WHEN 'NIT' THEN 2
    END AS tipoIdEmpresaRecibeSubsidio,
    --perEmpresaFovis.perNumeroIdentificacion
    perEmpAportanteAfi.perNumeroIdentificacion AS numeroIdEmpresaRecibeSubsidio,
    --perEmpresaFovis.perDigitoVerificacion 
    perEmpAportanteAfi.perDigitoVerificacion AS digitoVerificacionIdEmpresaSubsidio,
    --perEmpresaFovis.perRazonSocial 
    perEmpAportanteAfi.perRazonSocial AS razonSocialEmpresaSubsidio,
    1 AS aQuienSeOtorgoSubsidio, 
    ':fechaInicio' AS fechaInicialPeriodoInformacion,
    ':fechaFin' AS fechaFinalPeriodoInformacion,
    0 AS totalRegistroRelacionadosArchivo,
    ':nombreReporte' AS nombreDelArchivo,*/ 
    CASE
      WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL --codigoNovedad = 'C09' 
      THEN dbo.SolicitudAsignacion.safId 
      ELSE NULL
    END AS identificadorUnicoSubsidio,
    CASE
      WHEN benModificacionFovis.perId IS NOT NULL THEN benModificacionFovis.actualTipoId --codigoNovedad = 'C10'
      WHEN afiModificacionFovis.perId IS NOT NULL THEN afiModificacionFovis.actualTipoId --codigoNovedad = 'C10'
    END AS nuevoTipoIdMiembrePoblacionCubierta,  --CAMPO 56
    CASE
      WHEN benModificacionFovis.perId IS NOT NULL THEN benModificacionFovis.actualNumeroId --codigoNovedad = 'C10'
      WHEN afiModificacionFovis.perId IS NOT NULL THEN afiModificacionFovis.actualNumeroId --codigoNovedad = 'C10'
    END AS nuevoNumeroIdMiembroPoblacionCubierta, -- CAMPO57
    CASE
      WHEN ( (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
         (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL ))
         AND rolAfiFovis.roaFechaRetiro IS NOT NULL AND rolAfiFovis.roaMotivoDesafiliacion = 'DESAFILIACION_EMPLEADOR') 
         THEN 1
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
         (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL )) 
          AND ((rolAfiFovis.roaFechaRetiro IS NOT NULL AND rolAfiFovis.roaMotivoDesafiliacion = 'FALLECIMIENTO') OR
         (benFovis.benFechaRetiro IS NOT NULL AND benFovis.benMotivoDesafiliacion = 'FALLECIMIENTO')) THEN 2
      WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
         (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL )) 
         AND (rolAfiFovis.roaMotivoDesafiliacion = 'RETIRO_POR_CAMBIO_DE_CAJA_COMPENSACION' OR
         empleadorAportanteFovis.empMotivoDesafiliacion = 'RETIRO_POR_TRASLADO_OTRA_CCF' OR benFovis.benMotivoDesafiliacion = 'RETIRO_POR_CAMBIO_DE_CAJA_COMPENSACION' )
         THEN 3
        WHEN ( (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
         (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL ))
            AND dbo.ParametrizacionNovedad.novTipoTransaccion = 'SUSTITUCION_PATRONAL') THEN 6
        WHEN ((dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
         (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL )) AND empleadorAportanteFovis.empMotivoDesafiliacion = 'FUSION_ADQUISICION' 
          OR  dbo.ParametrizacionNovedad.novTipoTransaccion = 'DESAFILIAR_AUTOMATICAMENTE_EMPLEADORES_SOLICITUD_RECHAZADA'
          AND rolAfiFovis.roaMotivoDesafiliacion = 'DESAFILIACION_EMPLEADOR') THEN 7
      --TODO: FALTAN LAS CAUSAS DE RETIRO DE LA 4 A LA 12
      WHEN ( (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
         'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
         'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
         'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
         'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
         (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL )) 
         AND benFovis.benFechaRetiro IS NOT NULL AND benFovis.benMotivoDesafiliacion = 'OTROS' ) THEN 30 
      ELSE NULL
    END AS causaRetiro,
    @fechaInicio,
    @fechaFin
    FROM dbo.SolicitudAsignacion
    INNER JOIN dbo.PostulacionFOVIS ON dbo.PostulacionFOVIS.pofSolicitudAsignacion = dbo.SolicitudAsignacion.safId
    INNER JOIN dbo.JefeHogar ON dbo.JefeHogar.jehId = dbo.PostulacionFOVIS.pofJefeHogar
    INNER JOIN dbo.Afiliado AS afiFovis ON afiFovis.afiId =dbo.JefeHogar.jehAfiliado
    INNER JOIN dbo.RolAfiliado AS rolAfiFovis ON rolAfiFovis.roaAfiliado = afiFovis.afiId
    INNER JOIN dbo.Persona AS perJefeHogarAfiFovis ON perJefeHogarAfiFovis.perId = afiFovis.afiPersona
    INNER JOIN dbo.Ubicacion AS ubiAfiFovis ON ubiAfiFovis.ubiId = perJefeHogarAfiFovis.perUbicacionPrincipal
    INNER JOIN dbo.Municipio AS munAfiFovis ON munAfiFovis.munId = ubiAfiFovis.ubiMunicipio
    INNER JOIN dbo.Departamento AS depAfiFovis ON depAfiFovis.depId = munAfiFovis.munDepartamento
    INNER JOIN dbo.PersonaDetalle AS perDetJefeHogarAfiFovis ON perDetJefeHogarAfiFovis.pedPersona = perJefeHogarAfiFovis.perId
    INNER JOIN dbo.AporteDetallado AS aporteDetAfiFovis ON aporteDetAfiFovis.apdPersona = afiFovis.afiPersona
    LEFT JOIN (
          SELECT dbo.AporteGeneral.apgId, dbo.AporteGeneral.apgPersona, dbo.AporteGeneral.apgEmpresa, 
               dbo.AporteGeneral.apgFechaProcesamiento, afiRol.roaFechaAfiliacion, afiRol.roaFechaRetiro
          FROM dbo.AporteGeneral
          LEFT JOIN dbo.Empleador AS empleadorAporte ON empleadorAporte.empEmpresa = dbo.AporteGeneral.apgEmpresa
          LEFT JOIN dbo.RolAfiliado AS afiRol ON afiRol.roaEmpleador = empleadorAporte.empId
          --WHERE dbo.AporteGeneral.apgId = aporteDetAfiFovis.apdAporteGeneral
          GROUP BY dbo.AporteGeneral.apgId, dbo.AporteGeneral.apgPersona, dbo.AporteGeneral.apgEmpresa, 
               dbo.AporteGeneral.apgFechaProcesamiento, afiRol.roaFechaAfiliacion, afiRol.roaFechaRetiro 
          --ORDER BY dbo.AporteGeneral.apgFechaProcesamiento DESC
    ) AS aporteGenAfiFovis ON aporteGenAfiFovis.apgId = aporteDetAfiFovis.apdAporteGeneral
    LEFT JOIN dbo.Persona AS perAportanteAfi ON perAportanteAfi.perId = aporteGenAfiFovis.apgPersona
    LEFT JOIN dbo.Empresa AS empAportanteAfi ON empAportanteAfi.empId = aporteGenAfiFovis.apgEmpresa
    LEFT JOIN dbo.Empleador AS empleadorAportanteFovis ON empAportanteAfi.empId = empleadorAportanteFovis.empEmpresa
    LEFT JOIN dbo.Persona AS perEmpAportanteAfi ON perEmpAportanteAfi.perId = empAportanteAfi.empPersona
    LEFT JOIN dbo.Ubicacion AS ubiEmpAportanteAfi ON ubiEmpAportanteAfi.ubiId = perEmpAportanteAfi.perUbicacionPrincipal
    LEFT JOIN dbo.Municipio AS munEmpAportanteAfi ON munEmpAportanteAfi.munId = ubiEmpAportanteAfi.ubiMunicipio
    LEFT JOIN dbo.Departamento AS depEmpAportanteAfi ON depEmpAportanteAfi.depId = munEmpAportanteAfi.munDepartamento
    INNER JOIN dbo.Beneficiario AS benFovis ON benFovis.benAfiliado = afiFovis.afiId
    INNER JOIN dbo.BeneficiarioDetalle AS benDetFovis ON benDetFovis.bedId = benFovis.benBeneficiarioDetalle
    INNER JOIN dbo.Persona AS perBenFovis ON perBenFovis.perId = benFovis.benPersona
    INNER JOIN dbo.PersonaDetalle AS perDetBenFovis ON perDetBenFovis.pedPersona = perBenFovis.perId
    INNER JOIN dbo.SolicitudLegalizacionDesembolso ON dbo.SolicitudLegalizacionDesembolso.sldPostulacionFOVIS = dbo.PostulacionFOVIS.pofId
    INNER JOIN (SELECT dbo.LegalizacionDesembolso.lgdFechaTransferencia AS fechaTransferencia, dbo.LegalizacionDesembolso.lgdId AS id
            FROM dbo.LegalizacionDesembolso
            --WHERE LegalizacionDesembolso.lgdFechaTransferencia BETWEEN :fechaInicio AND :fechaFin
          ) AS Legalizacion 
    ON Legalizacion.id = dbo.SolicitudLegalizacionDesembolso.sldLegalizacionDesembolso
    LEFT JOIN dbo.ProyectoSolucionVivienda ON dbo.ProyectoSolucionVivienda.psvId = dbo.PostulacionFOVIS.pofProyectoSolucionVivienda
    LEFT JOIN dbo.Oferente ON dbo.Oferente.ofeId = dbo.ProyectoSolucionVivienda.psvOferente AND  (dbo.Oferente.ofeEmpresa = empAportanteAfi.empId) 
    --LEFT JOIN dbo.Empresa ON dbo.Oferente.ofeEmpresa = dbo.Empresa.empId
    --LEFT JOIN dbo.Persona AS perEmpresaFovis ON perEmpresaFovis.perId = dbo.Empresa.empPersona
    INNER JOIN dbo.Solicitud ON dbo.SolicitudAsignacion.safSolicitudGlobal = dbo.Solicitud.solId
    LEFT JOIN dbo.SolicitudNovedad ON dbo.Solicitud.solId = dbo.SolicitudNovedad.snoSolicitudGlobal
    LEFT JOIN #FechasNovedadesSolicituNovedad AS novedadesAprobadas ON novedadesAprobadas.snoId = dbo.SolicitudNovedad.snoId
    LEFT JOIN dbo.ParametrizacionNovedad ON dbo.SolicitudNovedad.snoNovedad = dbo.ParametrizacionNovedad.novId
    LEFT JOIN dbo.NovedadDetalle ON (dbo.SolicitudNovedad.snoId = dbo.NovedadDetalle.nopId AND dbo.NovedadDetalle.nopVigente = 1)
    LEFT JOIN 
    (
      SELECT personaAfiliadoABeneficiario.perId AS idAfiABen, NULL AS idBenAAfi --se obtienen los afiliados que pasaron a ser beneficiarios
      FROM dbo.Afiliado AS afi1
      INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
             AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
      INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
      INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
             --AND benFovis.benId = ben1.benId
             )
      UNION
      SELECT NULL AS idAfiABen, personaBeneficiarioAAFiliado.perId AS idBenAAfi -- se obtienen los beneficiarios que pasaron a ser afiliados
      FROM dbo.Beneficiario AS ben1
      INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
      INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona --AND afi1.afiId = afiFovis.afiId
      INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
      WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
    ) AS tablaNuevoAfiBen ON (tablaNuevoAfiBen.idBenAAfi = perJefeHogarAfiFovis.perId OR tablaNuevoAfiBen.idAfiABen = perBenFovis.perId)
    LEFT JOIN dbo.SolicitudNovedadFovis ON dbo.Solicitud.solId = dbo.SolicitudNovedadFovis.snfSolicitudGlobal
    LEFT JOIN #FechasNovedadesSolicituNovedadFovis AS novedadesFovisAprobadas ON novedadesFovisAprobadas.snfId = dbo.SolicitudNovedadFovis.snfId
    LEFT JOIN dbo.SolicitudNovedadPersonaFovis ON (dbo.SolicitudNovedadFovis.snfId = dbo.SolicitudNovedadPersonaFovis.spfSolicitudNovedadFovis 
           AND dbo.PostulacionFOVIS.pofId = dbo.SolicitudNovedadPersonaFovis.spfPostulacionFovis)
    LEFT JOIN #PersonasModificacionDatosBasicos afiModificacionFovis ON afiModificacionFovis.perId = perJefeHogarAfiFovis.perId   
    LEFT JOIN #PersonasModificacionDatosBasicos benModificacionFovis ON benModificacionFovis.perId = perBenFovis.perId 
    WHERE rolAfiFovis.roaEstadoAfiliado = 'ACTIVO' AND 
    ( 
      (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
       'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS', 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS',
        'AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO',
         'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
         'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
         'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO',    
       'RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
       'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
       'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
       'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
       'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND  novedadesAprobadas.fechaRegistroNovedadCerrada BETWEEN @fechaInicio AND @fechaFin)
       OR (dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND novedadesFovisAprobadas.fechaRegistroNovedadCerrada BETWEEN @fechaInicio AND @fechaFin)
       OR (afiModificacionFovis.perId IS NOT NULL AND rolAfiFovis.roaFechaAfiliacion BETWEEN @fechaInicio AND @fechaFin)
       OR (benModificacionFovis.perId IS NOT NULL AND benFovis.benFechaAfiliacion BETWEEN @fechaInicio AND @fechaFin)
       OR 
       (
         (
          SELECT CASE WHEN
          EXISTS
          (
            SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
            FROM dbo.Afiliado AS afi1
            INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
                   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
            INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
            INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
                   AND benFovis.benId = ben1.benId)
            UNION
            SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
            FROM dbo.Beneficiario AS ben1
            INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
            INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
            INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
            WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
          )
          THEN 1
          ELSE 0 END
        ) = 1  AND benFovis.benFechaAfiliacion BETWEEN @fechaInicio AND @fechaFin
       )
    )
    
    GROUP BY
    dbo.SolicitudAsignacion.safId, perJefeHogarAfiFovis.perTipoIdentificacion, perJefeHogarAfiFovis.perNumeroIdentificacion,
    perJefeHogarAfiFovis.perPrimerApellido, perJefeHogarAfiFovis.perSegundoApellido, perJefeHogarAfiFovis.perPrimerNombre,
    perJefeHogarAfiFovis.perSegundoNombre, dbo.SolicitudAsignacion.safFechaAceptacion, dbo.SolicitudAsignacion.safValorSFVAsignado,
    dbo.PostulacionFOVIS.pofEstadoHogar, perBenFovis.perTipoIdentificacion, perBenFovis.perNumeroIdentificacion,
    perDetBenFovis.pedGenero, perDetBenFovis.pedFechaExpedicionDocumento, perBenFovis.perPrimerApellido, Legalizacion.fechaTransferencia,
    perBenFovis.perSegundoApellido, perBenFovis.perPrimerNombre, perBenFovis.perSegundoNombre, rolAfiFovis.roaFechaIngreso,
    rolAfiFovis.roaTipoAfiliado, rolAfiFovis.roaFechaRetiro, rolAfiFovis.roaMotivoDesafiliacion, aporteGenAfiFovis.apgPersona, 
    perAportanteAfi.perTipoIdentificacion, perAportanteAfi.perNumeroIdentificacion, perAportanteAfi.perPrimerNombre ,perAportanteAfi.perSegundoNombre,
    perAportanteAfi.perPrimerApellido,perAportanteAfi.perSegundoApellido, perEmpAportanteAfi.perTipoIdentificacion, perEmpAportanteAfi.perTipoIdentificacion,
    perEmpAportanteAfi.perNumeroIdentificacion, perEmpAportanteAfi.perDigitoVerificacion, perEmpAportanteAfi.perRazonSocial,
    perDetJefeHogarAfiFovis.pedGenero, perDetJefeHogarAfiFovis.pedFechaNacimiento, munAfiFovis.munCodigo, depAfiFovis.depCodigo,
    aporteGenAfiFovis.roaFechaAfiliacion, aporteGenAfiFovis.roaFechaRetiro, depEmpAportanteAfi.depCodigo, munEmpAportanteAfi.munCodigo,
    benFovis.benGradoAcademico, benDetFovis.bedCertificadoEscolaridad, benFovis.benTipoBeneficiario, perDetJefeHogarAfiFovis.pedFechaFallecido,
    benFovis.benId, novTipoTransaccion, benFovis.benFechaRetiro, benFovis.benMotivoDesafiliacion, rolAfiFovis.roaFechaRetiro,
    rolAfiFovis.roaMotivoDesafiliacion, benFovis.benTipoBeneficiario, dbo.SolicitudNovedadFovis.snfId, afiModificacionFovis.perId, benModificacionFovis.perId,
    empleadorAportanteFovis.empMotivoDesafiliacion, safId, afiId, empFechaCambioEstadoAfiliacion, idBenAAfi, idAfiABen,
    benModificacionFovis.actualNumeroId, benModificacionFovis.actualTipoId, benModificacionFovis.anteriorNumeroId, benModificacionFovis.anteriorTipoId,
    afiModificacionFovis.actualNumeroId, afiModificacionFovis.actualTipoId, afiModificacionFovis.anteriorNumeroId, afiModificacionFovis.anteriorTipoId,
    perDetBenFovis.pedFechaFallecido
END
ELSE	--else--
BEGIN
	SELECT
	2 AS tipoRegistro,
	(SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') AS codigoCCF,
	CASE perAfiCore.perTipoIdentificacion 
	    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
	    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	    WHEN 'PASAPORTE' THEN 'PA'
	    WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
	END AS tipoIdAfiliado,
	perAfiCore.perNumeroIdentificacion AS numeroIdAfiliado,
	CASE perDetAfiCore.pedGenero
		WHEN 'MASCULINO' THEN 'M'
		ELSE 'F'
	END AS codigoGeneroAfiliado,
	perDetAfiCore.pedFechaNacimiento AS fechaNacimientoAfiliado,
	perAfiCore.perPrimerApellido AS primerApellidoAfiliado,
	perAfiCore.perSegundoApellido AS segundoApellidoAfiliado,
	perAfiCore.perPrimerNombre AS primerNombreAfiliado,
	perAfiCore.perSegundoNombre AS segundoNombreAfiliado,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')
		     THEN 'C01'
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' THEN 'C02'
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO',
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')
			THEN 'C03' 
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE') THEN 'C04'
		WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benCore.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 THEN 'C07' 
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
			 (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL ) THEN 'C08'
	   WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 THEN 'C09' 
	   WHEN afiModificacionCore.perId IS NOT NULL OR benModificacionCore.perId IS NOT NULL THEN 'C10'  
	END AS codigoNovedad,
	--==== INICIA NOVEDAD C02 =====
	CASE  
	    WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
	    	 perAfiCore.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
	    WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
	    	 perAfiCore.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
	    WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
	    	 perAfiCore.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
	    WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
	    	 perAfiCore.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
	    WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
	    	 perAfiCore.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD' 
	    ELSE NULL
	END AS tipoIdAfiliado2,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' -- CODIGO NOVEDAD C02
		THEN perAfiCore.perNumeroIdentificacion
		ELSE NULL
	END AS numeroIdAfiliado2,
	--========= FIN NOVEDAD C02 ========
	--========= INICIA NOVEDAD C01 =====
	CASE 
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01 
			 AND perDetAfiCore.pedGenero = 'FEMENINO' THEN 'F'
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
			 AND perDetAfiCore.pedGenero = 'MASCULINO' THEN 'M'
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
			 AND perDetAfiCore.pedGenero ='INDEFINIDO' THEN 'I'
		ELSE NULL
	END AS codigoGeneroAfiliado2,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')--codigo noveddad c01
		THEN perDetAfiCore.pedFechaNacimiento 
		ELSE NULL
	END AS fechaNacimientoAfiliado2,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')--codigo noveddad c01
		THEN perAfiCore.perPrimerApellido 
		ELSE NULL
	END AS primerApellidoAfiliado2,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')--codigo noveddad c01
		THEN perAfiCore.perSegundoApellido 
		ELSE NULL
	END AS segundoApellidoAfiliado2,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
		THEN perAfiCore.perPrimerNombre 
		ELSE NULL
	END AS primerNombreAfiliado2,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
		THEN perAfiCore.perSegundoNombre 
		ELSE NULL
	END AS segundoNombreAfiliado2,
	CASE 
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')
		THEN depAfiCore.depCodigo
		ELSE NULL
	END AS departamentoAfiliado,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
		THEN munAfiCore.munCodigo 
		ELSE NULL
	END AS municipioAfiliado,
	--========= FIN NOVEDAD C01 ================
	--====== INICIA NOVEDAD C03 Y C07 ============
	CASE
		WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benCore.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1
		THEN rolAfiCore.roaFechaIngreso 
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --CODIGO NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO') THEN empleadorAportanteCore.empFechaCambioEstadoAfiliacion
	    WHEN  dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION', -- CODIGO C03
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO') THEN rolAfiCore.roaFechaIngreso 
		ELSE NULL
	END AS fechaAfiliacion,
	--====== FIN NOVEDAD C07 ============
	---===== INICIA NOVEDAD C03 ========
	CASE 
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			  AND rolAfiCore.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE' THEN 1
		WHEN ( dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			AND	 rolAfiCore.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' THEN 2 --2 - Trabajador afiliado facultativo
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')) 
			 AND rolAfiCore.roaTipoAfiliado = 'PENSIONADO' THEN 3
		ELSE NULL
	END AS codigoTipoAfiliado,
	---===== INICIA NOVEDAD C04, CONTINUA C03 ========
	CASE 
		WHEN ( (aporteGenAfiCore.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')) 
			 AND perAportanteAfiCore.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
	    WHEN ( (aporteGenAfiCore.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 AND perAportanteAfiCore.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
	    WHEN ( (aporteGenAfiCore.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 AND perAportanteAfiCore.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
	    WHEN ( (aporteGenAfiCore.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 AND perAportanteAfiCore.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
	    WHEN ( (aporteGenAfiCore.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 AND perAportanteAfiCore.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD'
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 AND perEmpAportanteAfiCore.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
	    WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 AND perEmpAportanteAfiCore.perTipoIdentificacion ='TARJETA_IDENTIDAD' THEN 'TI'
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 AND perEmpAportanteAfiCore.perTipoIdentificacion ='CEDULA_EXTRANJERIA' THEN 'CE'
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 AND perEmpAportanteAfiCore.perTipoIdentificacion ='PASAPORTE' THEN 'PA'
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 AND perEmpAportanteAfiCore.perTipoIdentificacion ='CARNE_DIPLOMATICO' THEN 'CD' 
		ELSE NULL
	END AS tipoIdAportante,
	CASE 
		WHEN ( (aporteGenAfiCore.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 THEN perAportanteAfiCore.perNumeroIdentificacion 
		WHEN ( (aporteGenAfiCore.apgPersona IS NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 THEN perEmpAportanteAfiCore.perNumeroIdentificacion 
		ELSE NULL
	END AS numeroIdAportante,
	CASE 
		WHEN ( (aporteGenAfiCore.apgPersona IS NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 THEN perEmpAportanteAfiCore.perDigitoVerificacion
		ELSE NULL
	END AS digitoVerificacionAportante,
	--======== FIN NOVEDAD C04 =========
	--==================================
	CASE 
		WHEN (aporteGenAfiCore.apgPersona IS NOT NULL AND  dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
		THEN CONCAT(perAportanteAfiCore.perPrimerNombre,' ',perAportanteAfiCore.perSegundoNombre, ' ', perAportanteAfiCore.perPrimerApellido, ' ',perAportanteAfiCore.perSegundoApellido)
		WHEN (aporteGenAfiCore.apgPersona IS NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
		THEN perEmpAportanteAfiCore.perRazonSocial
		ELSE NULL
	END AS razonSocialAportante,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')
		THEN aporteGenAfiCore.roaFechaAfiliacion 
		ELSE NULL
	END AS fechaVinculacionAportante,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')
		THEN depEmpAportanteAfiCore.depCodigo 
		ELSE NULL
	END AS departamentoUbicacion,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')
		THEN munEmpAportanteAfiCore.munCodigo 
		ELSE NULL
	END AS municipioUbicacion,
	--========================= FIN NOVEDAD C03 ==================
	--============================================================
	--ESTADO AFILIACIÃ“N 21: NO SE MUESTRA EN NINGUNA DE LAS 'NOVEDADES'
	--AL DIA (1- AL DIA O 2- EN MORA) 22: NO SE MUESTRA EN NINGUNA DE LAS 'NOVEDADES'
	--- ================= INICIO NOVEDAD 'C07' ================-----
	CASE
		WHEN ((
			  SELECT CASE WHEN --NOVEDAD C07
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benCore.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
	     	 AND tablaNuevoAfiBen.idBenAAfi IS NOT NULL THEN 1
		WHEN ((
			  SELECT CASE WHEN  --NOVEDAD C07
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benCore.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')) 
	     	 AND tablaNuevoAfiBen.idAfiABen IS NOT NULL  THEN 2
	     	 
		ELSE NULL
	END AS codigoTipoMiembroPoblacionCubierta, 
	---==== FIN ULTIMO CAMPO NOVEDAD C03 ============
	--- === NOVEDADES 'C07' Y 'C08' =================
	CASE   
		WHEN ((
			  SELECT CASE WHEN  --NOVEDAD C07
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benCore.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 AND tablaNuevoAfiBen.idBenAAfi IS NOT NULL) OR ( dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
			 (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL )  AND rolAfiCore.roaFechaRetiro IS NOT NULL)
		THEN perAfiCore.perTipoIdentificacion
		WHEN ((
			  SELECT CASE WHEN
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benCore.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 AND tablaNuevoAfiBen.idAfiABen IS NOT NULL) OR (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
			 (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL ) AND  benCore.benFechaRetiro IS NOT NULL)
		THEN perBenCore.perTipoIdentificacion
		WHEN benModificacionCore.perId IS NOT NULL THEN benModificacionCore.anteriorTipoId --codigoNovedad = 'C10'
		WHEN afiModificacionCore.perId IS NOT NULL THEN afiModificacionCore.anteriorTipoId --codigoNovedad = 'C10'
	END AS tipoIdMiembroPoblacionCubierta,     
	CASE
		WHEN ((
			  SELECT CASE WHEN
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benCore.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1  AND tablaNuevoAfiBen.idBenAAfi IS NOT NULL) OR (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
			 (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL ) AND rolAfiCore.roaFechaRetiro IS NOT NULL)
		THEN perAfiCore.perNumeroIdentificacion
		WHEN ((
			  SELECT CASE WHEN
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benCore.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1  AND tablaNuevoAfiBen.idAfiABen IS NOT NULL) OR (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
			 (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL ) AND benCore.benFechaRetiro IS NOT NULL)
		THEN perBenCore.perNumeroIdentificacion
		WHEN  benModificacionCore.perId IS NOT NULL THEN benModificacionCore.anteriorNumeroId --codigoNovedad = 'C10'
		WHEN  afiModificacionCore.perId IS NOT NULL THEN afiModificacionCore.anteriorNumeroId --codigoNovedad = 'C10'
		ELSE NULL
	END AS numeroIdMiembroPoblacionCubierta,   
	--- ==== FIN NOVEDAD 'C08' ========
	
	CASE 
		WHEN benCore.benGradoAcademico IS NOT NULL AND benDetCore.bedCertificadoEscolaridad = 1 THEN 'E' --ESTUDIANTE
		WHEN benCore.benGradoAcademico IS NULL AND benDetCore.bedCertificadoEscolaridad = 0 THEN 'D' --DISCAPACITADO
		ELSE NULL
	END AS codigoCondicionBeneficiario, 
	CASE
		WHEN --codigoNovedad = 'C07' 
			(
			  SELECT CASE WHEN
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benCore.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 	AND	benCore.benTipoBeneficiario = 'CONYUGE' THEN 1
		WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benCore.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 AND benCore.benTipoBeneficiario IN ('HIJO_BIOLOGICO','HIJO_ADOPTIVO','HIJASTRO','BENEFICIARIO_EN_CUSTODIA') THEN 2
		WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benCore.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 AND benCore.benTipoBeneficiario IN ('PADRE','MADRE') THEN 3
		WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benCore.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 AND benCore.benTipoBeneficiario IN ('HERMANO_HUERFANO_DE_PADRES','HERMANO_HOGAR') THEN 4
		ELSE NULL
	END AS codigoTipoRelacionConAfiliadoBen,
	--- ================= FIN NOVEDAD 'C07' =====================-----
	--- ================= INICIO NOVEDAD 'C09' ====================----
	CASE
	 WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     ) = 1
	     THEN dbo.SolicitudLiquidacionSubsidio.slsFechaInicio
	     ELSE NULL
	END AS fechaAsignacionSubsidio, 
	CASE
	 WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     ) = 1
	     THEN LEFT(CAST(detalle.dsaValorSubsidioMonetario  AS VARCHAR), CHARINDEX('.', CAST(detalle.dsaValorSubsidioMonetario  AS VARCHAR)) - 1)
		 ELSE NULL
	END AS valorSubsidio,
	CASE
	 WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     ) = 1
	     THEN 1
	     ELSE NULL
	END AS codigoTipoSubsidio, -- toma valor 1 para cuota monetaria 
	CASE 
		WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 AND (cuenta.casEstadoTransaccionSubsidio = 'GENERADO' OR 
			 cuenta.casEstadoTransaccionSubsidio = 'ENVIADO' OR 
			 cuenta.casEstadoTransaccionSubsidio = 'APLICADO') THEN 1
		WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	    	 ) = 1 AND cuenta.casEstadoTransaccionSubsidio = 'RETENIDO' THEN 2
	    WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	    	 ) = 1 AND cuenta.casEstadoTransaccionSubsidio = 'COBRADO'  THEN 3
	    WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 AND cuenta.casEstadoTransaccionSubsidio = ''  THEN 4 --FALTA SABER EL ESTADO 4
	    WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     ) = 1 AND cuenta.casEstadoTransaccionSubsidio = 'ANULADO'  THEN 5 
	END AS estadoSubsidio, 				--codigoNovedad = 'C09'
	CASE
		WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1
		THEN (SELECT prmValor FROM dbo.Parametro WHERE prmNombre = 'CAJA_COMPENSACION_DEPTO_ID') 
		ELSE NULL
	END AS departamentoSubsidio, --codigoNovedad = 'C09'
	CASE
		WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1
		THEN (SELECT prmValor FROM dbo.Parametro WHERE prmNombre = 'CAJA_COMPENSACION_MUNI_ID') 
		ELSE NULL
	END AS municipioSubsidio,   --codigoNovedad = 'C09'
	CASE
		WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1
		THEN (
				 SELECT TOP 1 det.dsaFechaTransaccionRetiro
				 FROM dbo.DetalleSubsidioAsignado AS det
				 INNER JOIN dbo.CuentaAdministradorSubsidio ON dbo.CuentaAdministradorSubsidio.casId = det.dsaCuentaAdministradorSubsidio
				 AND dbo.CuentaAdministradorSubsidio.casEstadoTransaccionSubsidio = 'COBRADO'
				 WHERE det.dsaId = detalle.dsaId
				 ORDER BY det.dsaFechaTransaccionRetiro DESC
			 ) 
	     ELSE NULL
	 END AS fechaEntregaUltimoSubsidio,
	--- ================= FIN NOVEDAD 'C09' =====================
	--===================================================
	--================== INICIO NOVEDAD C04 ===========================
	CASE
		WHEN  dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE') --codigoNovedad = 'C04'
		THEN aporteGenAfiCore.roaFechaRetiro
		ELSE NULL
	END AS fechaDesvinculacionAportante,
	 ---== NOVEDADES C04 Y C08 ====---
	CASE
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN('RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_TRABAJADOR_DEPENDIENTE')) --codigoNovedad = 'C04' 
			 OR ( dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS', --codigoNovedad = 'C08'
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
			 (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL )  AND rolAfiCore.roaFechaRetiro IS NOT NULL)
		THEN rolAfiCore.roaFechaRetiro
		WHEN ( dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS', --codigoNovedad = 'C08'
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
			 (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL ) AND  benCore.benFechaRetiro IS NOT NULL) THEN benCore.benFechaRetiro
		ELSE NULL
	END AS fechaRetiroAfiliado,
	CASE
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN('RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_TRABAJADOR_DEPENDIENTE') --codigoNovedad = 'C04'
			  AND perDetAfiCore.pedFechaFallecido IS NOT NULL)
			 THEN perDetAfiCore.pedFechaFallecido 
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN('RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_TRABAJADOR_DEPENDIENTE') --codigoNovedad = 'C04'
			  AND perDetBenCore.pedFechaFallecido IS NOT NULL) THEN perDetBenCore.pedFechaFallecido
		ELSE NULL
	END AS fechaFallecimiento,
	--============= FIN NOVEDAD C04  ===========
	--- ================= INICIO NOVEDAD 'C09' ====================----
	CASE  
		WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     ) = 1  AND perBenCore.perTipoIdentificacion = 'REGISTRO_CIVIL' THEN 'RC'
	    WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     ) = 1 AND perBenCore.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
	    WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     ) = 1 AND perBenCore.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
	    WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     ) = 1  AND perBenCore.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
	    WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     ) = 1  AND perBenCore.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
	    WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     ) = 1  AND perBenCore.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD'
	    ELSE NULL
	END AS tipoIdbeneficiario, --codigoNovedad = 'C09'
	CASE
		WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     ) = 1  
		THEN perBenCore.perNumeroIdentificacion  --codigoNovedad = 'C09'
		ELSE NULL
	END AS numeroIdBeneficiario, 
	
	--- ================= FIN NOVEDAD 'C09' =====================
	/*CASE perDetBenFovis.pedGenero   --TODO: DEL CAMPO 40 AL 54 NO SE USAN
		WHEN 'MASCULINO' THEN 'M'
		ELSE 'F'
	END AS codigoGeneroBeneficiario,
	perDetBenFovis.pedFechaExpedicionDocumento AS fechaNacimientoBeneficiario,
	perBenFovis.perPrimerApellido AS primerApellidoBeneficiario,
	perBenFovis.perSegundoApellido AS segundoApellidoBeneficiario,
	perBenFovis.perPrimerNombre  AS primerNombreBeneficiario,
	perBenFovis.perSegundoNombre AS segundoNombreBeneficiario,
	CASE perEmpAportanteAfi.perTipoIdentificacion--perEmpresaFovis.perTipoIdentificacion
	    WHEN 'CEDULA_CIUDADANIA' THEN 1
		WHEN 'NIT' THEN 2
	END AS tipoIdEmpresaRecibeSubsidio,
	--perEmpresaFovis.perNumeroIdentificacion
	perEmpAportanteAfi.perNumeroIdentificacion AS numeroIdEmpresaRecibeSubsidio,
	--perEmpresaFovis.perDigitoVerificacion 
	perEmpAportanteAfi.perDigitoVerificacion AS digitoVerificacionIdEmpresaSubsidio,
	--perEmpresaFovis.perRazonSocial 
	perEmpAportanteAfi.perRazonSocial AS razonSocialEmpresaSubsidio,
	1 AS aQuienSeOtorgoSubsidio, 
	':fechaInicio' AS fechaInicialPeriodoInformacion,
	':fechaFin' AS fechaFinalPeriodoInformacion,
	0 AS totalRegistroRelacionadosArchivo,
	':nombreReporte' AS nombreDelArchivo,*/ 
	CASE
		WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     ) = 1  
		THEN dbo.SolicitudLiquidacionSubsidio.slsId --codigoNovedad = 'C09' 
		ELSE NULL
	END AS identificadorUnicoSubsidio,
	CASE
		WHEN benModificacionCore.perId IS NOT NULL THEN benModificacionCore.actualTipoId --codigoNovedad = 'C10'
		WHEN afiModificacionCore.perId IS NOT NULL THEN afiModificacionCore.actualTipoId --codigoNovedad = 'C10'
	END AS nuevoTipoIdMiembrePoblacionCubierta,  --CAMPO 56
	CASE
		WHEN benModificacionCore.perId IS NOT NULL THEN benModificacionCore.actualNumeroId --codigoNovedad = 'C10'
		WHEN afiModificacionCore.perId IS NOT NULL THEN afiModificacionCore.actualNumeroId --codigoNovedad = 'C10'
	END AS nuevoNumeroIdMiembroPoblacionCubierta, -- CAMPO57
	CASE
		WHEN ( (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
			 (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL ))
			 AND rolAfiCore.roaFechaRetiro IS NOT NULL AND rolAfiCore.roaMotivoDesafiliacion = 'DESAFILIACION_EMPLEADOR') 
			 THEN 1
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
			 (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL )) 
			  AND ((rolAfiCore.roaFechaRetiro IS NOT NULL AND rolAfiCore.roaMotivoDesafiliacion = 'FALLECIMIENTO') OR
			 (benCore.benFechaRetiro IS NOT NULL AND benCore.benMotivoDesafiliacion = 'FALLECIMIENTO')) THEN 2
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
			 (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL )) 
			 AND (rolAfiCore.roaMotivoDesafiliacion = 'RETIRO_POR_CAMBIO_DE_CAJA_COMPENSACION' OR
			 empleadorAportanteCore.empMotivoDesafiliacion = 'RETIRO_POR_TRASLADO_OTRA_CCF' OR benCore.benMotivoDesafiliacion = 'RETIRO_POR_CAMBIO_DE_CAJA_COMPENSACION' )
			 THEN 3
	    WHEN ( (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
			 (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL ))
	    	  AND dbo.ParametrizacionNovedad.novTipoTransaccion = 'SUSTITUCION_PATRONAL') THEN 6
	    WHEN ((dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
			 (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL )) AND empleadorAportanteCore.empMotivoDesafiliacion = 'FUSION_ADQUISICION' 
			  OR  dbo.ParametrizacionNovedad.novTipoTransaccion = 'DESAFILIAR_AUTOMATICAMENTE_EMPLEADORES_SOLICITUD_RECHAZADA'
			  AND rolAfiCore.roaMotivoDesafiliacion = 'DESAFILIACION_EMPLEADOR') THEN 7
	 	--TODO: FALTAN LAS CAUSAS DE RETIRO DE LA 4 A LA 12
		WHEN ( (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
			 (benCore.benFechaRetiro IS NOT NULL OR rolAfiCore.roaFechaRetiro IS NOT NULL )) 
			 AND benCore.benFechaRetiro IS NOT NULL AND benCore.benMotivoDesafiliacion = 'OTROS' ) THEN 30 
		ELSE NULL
	END AS causaRetiro	
	FROM dbo.SolicitudLiquidacionSubsidio
	INNER JOIN dbo.DetalleSubsidioAsignado AS detalle ON dbo.SolicitudLiquidacionSubsidio.slsId = detalle.dsaSolicitudLiquidacionSubsidio
	AND detalle.dsaEstado = 'DERECHO_ASIGNADO'
	INNER JOIN dbo.CuentaAdministradorSubsidio AS cuenta ON detalle .dsaCuentaAdministradorSubsidio = cuenta.casId
	INNER JOIN dbo.Afiliado AS afiCore ON detalle.dsaAfiliadoPrincipal = afiCore.afiId
	INNER JOIN dbo.RolAfiliado AS rolAfiCore ON rolAfiCore.roaAfiliado = afiCore.afiId
	INNER JOIN dbo.Persona AS perAfiCore ON afiCore.afiPersona = perAfiCore.perId
	INNER JOIN dbo.Ubicacion AS ubiAfiCore ON ubiAfiCore.ubiId = perAfiCore.perUbicacionPrincipal
	INNER JOIN dbo.Municipio AS munAfiCore ON munAfiCore.munId = ubiAfiCore.ubiMunicipio
	INNER JOIN dbo.Departamento AS depAfiCore ON depAfiCore.depId = munAfiCore.munDepartamento
	INNER JOIN dbo.PersonaDetalle AS perDetAfiCore ON perAfiCore.perId = perDetAfiCore.pedPersona
	INNER JOIN dbo.AporteDetallado AS aporteDetAfiCore ON aporteDetAfiCore.apdPersona = afiCore.afiPersona
	LEFT JOIN (
				SELECT dbo.AporteGeneral.apgId, dbo.AporteGeneral.apgPersona, dbo.AporteGeneral.apgEmpresa, 
					   dbo.AporteGeneral.apgFechaProcesamiento, afiRol.roaFechaAfiliacion, afiRol.roaFechaRetiro
				FROM dbo.AporteGeneral
				LEFT JOIN dbo.Empleador AS empleadorAporte ON empleadorAporte.empEmpresa = dbo.AporteGeneral.apgEmpresa
				LEFT JOIN dbo.RolAfiliado AS afiRol ON afiRol.roaEmpleador = empleadorAporte.empId
				--WHERE dbo.AporteGeneral.apgId = aporteDetAfiCore.apdAporteGeneral
				GROUP BY dbo.AporteGeneral.apgId, dbo.AporteGeneral.apgPersona, dbo.AporteGeneral.apgEmpresa, 
						 dbo.AporteGeneral.apgFechaProcesamiento, afiRol.roaFechaAfiliacion, afiRol.roaFechaRetiro 
				--ORDER BY dbo.AporteGeneral.apgFechaProcesamiento DESC
	) AS aporteGenAfiCore ON aporteGenAfiCore.apgId = aporteDetAfiCore.apdAporteGeneral
	LEFT JOIN dbo.Persona AS perAportanteAfiCore ON perAportanteAfiCore.perId = aporteGenAfiCore.apgPersona
	LEFT JOIN dbo.Empresa AS empAportanteAfiCore ON empAportanteAfiCore.empId = aporteGenAfiCore.apgEmpresa
	LEFT JOIN dbo.Empleador AS empleadorAportanteCore ON (empAportanteAfiCore.empId = empleadorAportanteCore.empEmpresa
													AND detalle.dsaEmpleador = empleadorAportanteCore.empId)
	LEFT JOIN dbo.Persona AS perEmpAportanteAfiCore ON perEmpAportanteAfiCore.perId = empAportanteAfiCore.empPersona
	LEFT JOIN dbo.Ubicacion AS ubiEmpAportanteAfiCore ON ubiEmpAportanteAfiCore.ubiId = perEmpAportanteAfiCore.perUbicacionPrincipal
	LEFT JOIN dbo.Municipio AS munEmpAportanteAfiCore ON munEmpAportanteAfiCore.munId = ubiEmpAportanteAfiCore.ubiMunicipio
	LEFT JOIN dbo.Departamento AS depEmpAportanteAfiCore ON depEmpAportanteAfiCore.depId = munEmpAportanteAfiCore.munDepartamento
	INNER JOIN dbo.Beneficiario AS benCore ON detalle.dsaBeneficiarioDetalle = benCore.benBeneficiarioDetalle
	INNER JOIN dbo.BeneficiarioDetalle AS benDetCore ON benDetCore.bedId = benCore.benBeneficiarioDetalle
	INNER JOIN dbo.Persona AS perBenCore ON perBenCore.perId = benCore.benPersona
	INNER JOIN dbo.PersonaDetalle AS perDetBenCore ON perDetBenCore.pedPersona = perBenCore.perId
	--INNER JOIN dbo.Empleador ON detalle.dsaEmpleador = dbo.Empleador.empId
	--INNER JOIN dbo.Empresa ON dbo.Empleador.empEmpresa = dbo.Empleador.empId
	--INNER JOIN dbo.Persona AS perEmpresaCore ON dbo.Empresa.empPersona = perEmpresaCore.perId
	INNER JOIN dbo.Solicitud ON dbo.SolicitudLiquidacionSubsidio.slsSolicitudGlobal = dbo.Solicitud.solId
	LEFT JOIN dbo.SolicitudNovedad ON dbo.Solicitud.solId = dbo.SolicitudNovedad.snoSolicitudGlobal
	LEFT JOIN #FechasNovedadesSolicituNovedad AS novedadesAprobadas ON novedadesAprobadas.snoId = dbo.SolicitudNovedad.snoId
	LEFT JOIN dbo.ParametrizacionNovedad ON dbo.SolicitudNovedad.snoNovedad = dbo.ParametrizacionNovedad.novId
	LEFT JOIN dbo.NovedadDetalle ON (dbo.SolicitudNovedad.snoId = dbo.NovedadDetalle.nopId AND dbo.NovedadDetalle.nopVigente = 1)
	LEFT JOIN 
	(
		SELECT personaAfiliadoABeneficiario.perId AS idAfiABen, NULL AS idBenAAfi --se obtienen los afiliados que pasaron a ser beneficiarios
		FROM dbo.Afiliado AS afi1
		INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
				   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
		INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
		INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
				   --AND benFovis.benId = ben1.benId
				   )
		UNION
		SELECT NULL AS idAfiABen, personaBeneficiarioAAFiliado.perId AS idBenAAfi -- se obtienen los beneficiarios que pasaron a ser afiliados
		FROM dbo.Beneficiario AS ben1
		INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
		INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona --AND afi1.afiId = afiFovis.afiId
		INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
		WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	) AS tablaNuevoAfiBen ON (tablaNuevoAfiBen.idBenAAfi = perAfiCore.perId OR tablaNuevoAfiBen.idAfiABen = perBenCore.perId)
	--LEFT JOIN dbo.SolicitudNovedadFovis ON dbo.Solicitud.solId = dbo.SolicitudNovedadFovis.snfSolicitudGlobal
	--LEFT JOIN dbo.SolicitudNovedadPersonaFovis ON (dbo.SolicitudNovedadFovis.snfId = dbo.SolicitudNovedadPersonaFovis.spfSolicitudNovedadFovis 
	-- 		   AND dbo.PostulacionFOVIS.pofId = dbo.SolicitudNovedadPersonaFovis.spfPostulacionFovis)
	LEFT JOIN #PersonasModificacionDatosBasicos afiModificacionCore ON afiModificacionCore.perId = perAfiCore.perId   
	LEFT JOIN #PersonasModificacionDatosBasicos benModificacionCore ON benModificacionCore.perId = perBenCore.perId 
    WHERE rolAfiCore.roaEstadoAfiliado = 'ACTIVO' AND 
	( 
	  (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
	   'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS', 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS',
	    'AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO',
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO',    
	   'RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
	   'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
	   'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
	   'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
	   'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND  novedadesAprobadas.fechaRegistroNovedadCerrada BETWEEN @fechaInicio AND @fechaFin)
	   OR (
			  SELECT CASE WHEN
			  EXISTS
	     	  ( 
	     	    SELECT abono.casId FROM dbo.CuentaAdministradorSubsidio abono
	     	    WHERE abono.casId = cuenta.casId
	     	    AND abono.casEstadoLiquidacionSubsidio = 'COBRADO' 
	     	    AND abono.casIdCuentaAdmonSubsidioRelacionado IN (
		     	  	SELECT CuentaAdministradorSubsidio.casId FROM CuentaAdministradorSubsidio 
		     	  	WHERE CuentaAdministradorSubsidio.casTipoTransaccionSubsidio = 'RETIRO'
		     	  	AND CuentaAdministradorSubsidio.casFechaHoraCreacionRegistro BETWEEN @fechaInicio AND @fechaFin
		     	 )
	     	  	UNION
	     	  	SELECT detalleAux.dsaCuentaAdministradorSubsidio FROM dbo.DetalleSubsidioAsignado detalleAux
	     	  	WHERE detalle.dsaId = detalleAux.dsaId
	     	  	AND detalleAux.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO',
	     	  	'DERECHO_ASIGNADO_RETENIDO')
	     	  	AND detalleAux.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     ) = 1 --codigo novedad: C09
	   OR (afiModificacionCore.perId IS NOT NULL AND rolAfiCore.roaFechaAfiliacion BETWEEN @fechaInicio AND @fechaFin)
	   OR (benModificacionCore.perId IS NOT NULL AND benCore.benFechaAfiliacion BETWEEN @fechaInicio AND @fechaFin)
	   OR 
	   (
		   (
			  SELECT CASE WHEN
			  EXISTS
		 	  (
		 	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
		 	  	FROM dbo.Afiliado AS afi1
		 	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
		 	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
		 	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
		 	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
		 	  			   AND benCore.benId = ben1.benId)
		 	  	UNION
		 	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
		 	  	FROM dbo.Beneficiario AS ben1
		 	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
		 	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiCore.afiId
		 	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
		 	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
		 	  )
		 	  THEN 1
		 	  ELSE 0 END
		 	) = 1  AND benCore.benFechaAfiliacion BETWEEN @fechaInicio AND @fechaFin
	   )
	)
	
	GROUP BY 
	dbo.SolicitudLiquidacionSubsidio.slsId, detalle.dsaId,
	perAfiCore.perTipoIdentificacion, perAfiCore.perNumeroIdentificacion, perAfiCore.perPrimerApellido, perAfiCore.perSegundoApellido,
	perAfiCore.perPrimerNombre, perAfiCore.perSegundoNombre,perDetAfiCore.pedGenero,perDetAfiCore.pedFechaNacimiento, dbo.SolicitudLiquidacionSubsidio.slsFechaInicio,
	detalle.dsaValorSubsidioMonetario, cuenta.casId, cuenta.casEstadoTransaccionSubsidio, munAfiCore.munCodigo, depAfiCore.depCodigo,
	perBenCore.perTipoIdentificacion, perBenCore.perNumeroIdentificacion, perDetBenCore.pedGenero, perDetBenCore.pedFechaExpedicionDocumento,
	perBenCore.perPrimerApellido, perBenCore.perSegundoApellido, perBenCore.perPrimerNombre, perBenCore.perSegundoNombre, dbo.SolicitudLiquidacionSubsidio.slsId,
	rolAfiCore.roaFechaIngreso,rolAfiCore.roaTipoAfiliado, rolAfiCore.roaFechaRetiro, rolAfiCore.roaMotivoDesafiliacion, aporteGenAfiCore.apgPersona, 
	perAportanteAfiCore.perTipoIdentificacion, perAportanteAfiCore.perNumeroIdentificacion, perAportanteAfiCore.perPrimerNombre ,perAportanteAfiCore.perSegundoNombre,
	perAportanteAfiCore.perPrimerApellido,perAportanteAfiCore.perSegundoApellido, perEmpAportanteAfiCore.perTipoIdentificacion, perEmpAportanteAfiCore.perTipoIdentificacion,
	perEmpAportanteAfiCore.perNumeroIdentificacion, perEmpAportanteAfiCore.perDigitoVerificacion, perEmpAportanteAfiCore.perRazonSocial, afiId, 
	aporteGenAfiCore.roaFechaAfiliacion, aporteGenAfiCore.roaFechaRetiro, depEmpAportanteAfiCore.depCodigo, munEmpAportanteAfiCore.munCodigo,
	benCore.benGradoAcademico, benDetCore.bedCertificadoEscolaridad, benCore.benTipoBeneficiario, perDetAfiCore.pedFechaFallecido, empFechaCambioEstadoAfiliacion, 
	benCore.benId, novTipoTransaccion, benCore.benFechaRetiro, benCore.benMotivoDesafiliacion, rolAfiCore.roaFechaRetiro, idBenAAfi, idAfiABen,
	rolAfiCore.roaMotivoDesafiliacion, benCore.benTipoBeneficiario, afiModificacionCore.perId, benModificacionCore.perId, empleadorAportanteCore.empMotivoDesafiliacion,
	benModificacionCore.actualNumeroId, benModificacionCore.actualTipoId, benModificacionCore.anteriorNumeroId, benModificacionCore.anteriorTipoId,
	afiModificacionCore.actualNumeroId, afiModificacionCore.actualTipoId, afiModificacionCore.anteriorNumeroId, afiModificacionCore.anteriorTipoId,
	perDetBenCore.pedFechaFallecido
	--perEmpresaCore.perTipoIdentificacion, perEmpresaCore.perNumeroIdentificacion, perEmpresaCore.perDigitoVerificacion, perEmpresaCore.perRazonSocial
+
	UNION ALL
	---- PARTE FOVIS
	SELECT
	2 AS tipoRegistro,
	(SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') AS codigoCCF,
	CASE perJefeHogarAfiFovis.perTipoIdentificacion 
	    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
	    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	    WHEN 'PASAPORTE' THEN 'PA'
	    WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
	END AS tipoIdAfiliado,
	perJefeHogarAfiFovis.perNumeroIdentificacion AS numeroIdAfiliado,
	CASE perDetJefeHogarAfiFovis.pedGenero
		WHEN 'MASCULINO' THEN 'M'
		ELSE 'F'
	END AS codigoGeneroAfiliado,
	perDetJefeHogarAfiFovis.pedFechaNacimiento AS fechaNacimientoAfiliado,
	perJefeHogarAfiFovis.perPrimerApellido AS primerApellidoAfiliado,
	perJefeHogarAfiFovis.perSegundoApellido AS segundoApellidoAfiliado,
	perJefeHogarAfiFovis.perPrimerNombre AS primerNombreAfiliado,
	perJefeHogarAfiFovis.perSegundoNombre AS segundoNombreAfiliado,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')
		     THEN 'C01'
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' THEN 'C02'
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO',
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')
			THEN 'C03' 
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE') THEN 'C04'
		WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benFovis.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 THEN 'C07' 
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
			 (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL ) THEN 'C08'
	    WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL THEN 'C09' --si tiene registro en la tabla SolicituNovedadFovis es porque se realizo algÃºn cambio en el subsidio
	    WHEN afiModificacionFovis.perId IS NOT NULL OR benModificacionFovis.perId IS NOT NULL THEN 'C10'  
	END AS codigoNovedad,
	--==== INICIA NOVEDAD C02 =====
	CASE  
	    WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
	    	 perJefeHogarAfiFovis.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
	    WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
	    	 perJefeHogarAfiFovis.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
	    WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
	    	 perJefeHogarAfiFovis.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
	    WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
	    	 perJefeHogarAfiFovis.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
	    WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' AND -- CODIGO NOVEDAD C02
	    	 perJefeHogarAfiFovis.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD' 
	    ELSE NULL
	END AS tipoIdAfiliado2,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion = 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS' -- CODIGO NOVEDAD C02
		THEN perJefeHogarAfiFovis.perNumeroIdentificacion
		ELSE NULL
	END AS numeroIdAfiliado2,
	--========= FIN NOVEDAD C02 ========
	--========= INICIA NOVEDAD C01 =====
	CASE 
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01 
			 AND perDetJefeHogarAfiFovis.pedGenero = 'FEMENINO' THEN 'F'
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
			 AND perDetJefeHogarAfiFovis.pedGenero = 'MASCULINO' THEN 'M'
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
			 AND perDetJefeHogarAfiFovis.pedGenero ='INDEFINIDO' THEN 'I'
		ELSE NULL
	END AS codigoGeneroAfiliado2,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')--codigo noveddad c01
		THEN perDetJefeHogarAfiFovis.pedFechaNacimiento 
		ELSE NULL
	END AS fechaNacimientoAfiliado2,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')--codigo noveddad c01
		THEN perJefeHogarAfiFovis.perPrimerApellido 
		ELSE NULL
	END AS primerApellidoAfiliado2,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')--codigo noveddad c01
		THEN perJefeHogarAfiFovis.perSegundoApellido 
		ELSE NULL
	END AS segundoApellidoAfiliado2,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
		THEN perJefeHogarAfiFovis.perPrimerNombre 
		ELSE NULL
	END AS primerNombreAfiliado2,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
		THEN perJefeHogarAfiFovis.perSegundoNombre 
		ELSE NULL
	END AS segundoNombreAfiliado2,
	CASE 
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS')
		THEN depAfiFovis.depCodigo
		ELSE NULL
	END AS departamentoAfiliado,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
		     'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS') --codigo noveddad c01
		THEN munAfiFovis.munCodigo 
		ELSE NULL
	END AS municipioAfiliado,
	--========= FIN NOVEDAD C01 ================
	--====== INICIA NOVEDAD C03 Y C07 ============
	CASE
		WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benFovis.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1
		THEN rolAfiFovis.roaFechaIngreso 
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --CODIGO NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO') THEN empleadorAportanteFovis.empFechaCambioEstadoAfiliacion
	    WHEN  dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION', -- CODIGO C03
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO') THEN rolAfiFovis.roaFechaIngreso 
		ELSE NULL
	END AS fechaAfiliacion,
	--====== FIN NOVEDAD C07 ============
	---===== INICIA NOVEDAD C03 ========
	CASE 
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			  AND rolAfiFovis.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE' THEN 1
		WHEN ( dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			AND	 rolAfiFovis.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' THEN 2 --2 - Trabajador afiliado facultativo
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')) 
			 AND rolAfiFovis.roaTipoAfiliado = 'PENSIONADO' THEN 3
		ELSE NULL
	END AS codigoTipoAfiliado,
	---===== INICIA NOVEDAD C04, CONTINUA C03 ========
	CASE 
		WHEN ( (aporteGenAfiFovis.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')) 
			 AND  perAportanteAfi.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
	    WHEN ( (aporteGenAfiFovis.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 AND perAportanteAfi.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
	    WHEN ( (aporteGenAfiFovis.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 AND perAportanteAfi.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
	    WHEN ( (aporteGenAfiFovis.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 AND perAportanteAfi.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
	    WHEN ( (aporteGenAfiFovis.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 AND perAportanteAfi.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD'
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 AND perEmpAportanteAfi.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
	    WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 AND perEmpAportanteAfi.perTipoIdentificacion ='TARJETA_IDENTIDAD' THEN 'TI'
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 AND perEmpAportanteAfi.perTipoIdentificacion ='CEDULA_EXTRANJERIA' THEN 'CE'
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 AND perEmpAportanteAfi.perTipoIdentificacion ='PASAPORTE' THEN 'PA'
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE') OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 AND perEmpAportanteAfi.perTipoIdentificacion ='CARNE_DIPLOMATICO' THEN 'CD' 
		ELSE NULL
	END AS tipoIdAportante,
	CASE 
		WHEN ( (aporteGenAfiFovis.apgPersona IS NOT NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 THEN perAportanteAfi.perNumeroIdentificacion 
		WHEN ( (aporteGenAfiFovis.apgPersona IS NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 THEN perEmpAportanteAfi.perNumeroIdentificacion 
		ELSE NULL
	END AS numeroIdAportante,
	CASE 
		WHEN ( (aporteGenAfiFovis.apgPersona IS NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE', --NOVEDAD C04
			 'RETIRO_TRABAJADOR_INDEPENDIENTE')) OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
			 THEN perEmpAportanteAfi.perDigitoVerificacion
		ELSE NULL
	END AS digitoVerificacionAportante,
	--======== FIN NOVEDAD C04 =========
	--==================================
	CASE 
		WHEN (aporteGenAfiFovis.apgPersona IS NOT NULL AND  dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
		THEN CONCAT(perAportanteAfi.perPrimerNombre,' ',perAportanteAfi.perSegundoNombre, ' ', perAportanteAfi.perPrimerApellido, ' ',perAportanteAfi.perSegundoApellido)
		WHEN (aporteGenAfiFovis.apgPersona IS NULL AND dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
		THEN perEmpAportanteAfi.perRazonSocial
		ELSE NULL
	END AS razonSocialAportante,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')
		THEN aporteGenAfiFovis.roaFechaAfiliacion 
		ELSE NULL
	END AS fechaVinculacionAportante,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')
		THEN depEmpAportanteAfi.depCodigo 
		ELSE NULL
	END AS departamentoUbicacion,
	CASE
		WHEN dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')
		THEN munEmpAportanteAfi.munCodigo 
		ELSE NULL
	END AS municipioUbicacion,
	--========================= FIN NOVEDAD C03 ==================
	--============================================================
	--ESTADO AFILIACIÃ“N 21: NO SE MUESTRA EN NINGUNA DE LAS 'NOVEDADES'
	--AL DIA (1- AL DIA O 2- EN MORA) 22: NO SE MUESTRA EN NINGUNA DE LAS 'NOVEDADES'
	--- ================= INICIO NOVEDAD 'C07' ================-----
	CASE
		WHEN ((
			  SELECT CASE WHEN --NOVEDAD C07
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benFovis.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'))
	     	 AND tablaNuevoAfiBen.idBenAAfi IS NOT NULL THEN 1
		WHEN ((
			  SELECT CASE WHEN  --NOVEDAD C07
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benFovis.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 OR dbo.ParametrizacionNovedad.novTipoTransaccion IN ('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO', --NOVEDAD C03
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO')) 
	     	 AND tablaNuevoAfiBen.idAfiABen IS NOT NULL  THEN 2
	     	 
		ELSE NULL
	END AS codigoTipoMiembroPoblacionCubierta, 
	---==== FIN ULTIMO CAMPO NOVEDAD C03 ============
	--- === NOVEDADES 'C07' Y 'C08' =================
	CASE   
		WHEN ((
			  SELECT CASE WHEN
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benFovis.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 AND tablaNuevoAfiBen.idBenAAfi IS NOT NULL) OR ( dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
			 (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL )  AND rolAfiFovis.roaFechaRetiro IS NOT NULL)
		THEN perJefeHogarAfiFovis.perTipoIdentificacion
		WHEN ((
			  SELECT CASE WHEN
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benFovis.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 AND tablaNuevoAfiBen.idAfiABen IS NOT NULL) OR (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
			 (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL ) AND  benFovis.benFechaRetiro IS NOT NULL)
		THEN perBenFovis.perTipoIdentificacion
		WHEN benModificacionFovis.perId IS NOT NULL THEN benModificacionFovis.anteriorTipoId --codigoNovedad = 'C10'
		WHEN afiModificacionFovis.perId IS NOT NULL THEN afiModificacionFovis.anteriorTipoId --codigoNovedad = 'C10'
	END AS tipoIdMiembroPoblacionCubierta,     
	CASE
		WHEN ((
			  SELECT CASE WHEN
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benFovis.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1  AND tablaNuevoAfiBen.idBenAAfi IS NOT NULL) OR (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
			 (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL ) AND rolAfiFovis.roaFechaRetiro IS NOT NULL)
		THEN perJefeHogarAfiFovis.perNumeroIdentificacion
		WHEN ((
			  SELECT CASE WHEN
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benFovis.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1  AND tablaNuevoAfiBen.idAfiABen IS NOT NULL) OR (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
			 (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL ) AND  benFovis.benFechaRetiro IS NOT NULL)
		THEN perBenFovis.perNumeroIdentificacion
		WHEN  benModificacionFovis.perId IS NOT NULL THEN benModificacionFovis.anteriorNumeroId --codigoNovedad = 'C10'
		WHEN  afiModificacionFovis.perId IS NOT NULL THEN afiModificacionFovis.anteriorNumeroId --codigoNovedad = 'C10'
		ELSE NULL
	END AS numeroIdMiembroPoblacionCubierta,   
	--- ==== FIN NOVEDAD 'C08' ========
	CASE 
		WHEN benFovis.benGradoAcademico IS NOT NULL AND benDetFovis.bedCertificadoEscolaridad = 1 THEN 'E' --ESTUDIANTE
		WHEN benFovis.benGradoAcademico IS NULL AND benDetFovis.bedCertificadoEscolaridad = 0 THEN 'D' --DISCAPACITADO
		ELSE NULL
	END AS codigoCondicionBeneficiario, 
	CASE
		WHEN --codigoNovedad = 'C07' 
			(
			  SELECT CASE WHEN
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benFovis.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 	AND	benFovis.benTipoBeneficiario = 'CONYUGE' THEN 1
		WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benFovis.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 AND benFovis.benTipoBeneficiario IN ('HIJO_BIOLOGICO','HIJO_ADOPTIVO','HIJASTRO','BENEFICIARIO_EN_CUSTODIA') THEN 2
		WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benFovis.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 AND benFovis.benTipoBeneficiario IN ('PADRE','MADRE') THEN 3
		WHEN (
			  SELECT CASE WHEN
			  EXISTS
	     	  (
	     	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
	     	  	FROM dbo.Afiliado AS afi1
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
	     	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
	     	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
	     	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	     	  			   AND benFovis.benId = ben1.benId)
	     	  	UNION
	     	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
	     	  	FROM dbo.Beneficiario AS ben1
	     	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
	     	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
	     	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
	     	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	     	  )
	     	  THEN 1
	     	  ELSE 0 END
	     	 ) = 1 AND benFovis.benTipoBeneficiario IN ('HERMANO_HUERFANO_DE_PADRES','HERMANO_HOGAR') THEN 4
		ELSE NULL
	END AS codigoTipoRelacionConAfiliadoBen,
	--- ================= FIN NOVEDAD 'C07' =====================-----
	--- ================= INICIO NOVEDAD 'C09' ====================----
	CASE
		WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL--codigoNovedad = 'C09'
		THEN dbo.SolicitudAsignacion.safFechaAceptacion 
		ELSE NULL
	END AS fechaAsignacionSubsidio, 
	CASE
		WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL--codigoNovedad = 'C09'
		THEN LEFT(CAST(dbo.SolicitudAsignacion.safValorSFVAsignado AS VARCHAR), CHARINDEX('.', CAST(dbo.SolicitudAsignacion.safValorSFVAsignado AS VARCHAR)) - 1)
		ELSE NULL
	END AS valorSubsidio,
	CASE
		WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL--codigoNovedad = 'C09'
		THEN 4 
		ELSE NULL
	END AS codigoTipoSubsidio, -- toma valor 4 para subsidio vivienda (fovis)
	CASE 
		WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND dbo.PostulacionFOVIS.pofEstadoHogar IN ('ASIGNADO_SIN_PRORROGA','ASIGNADO_CON_PRIMERA_PRORROGA',  --codigoNovedad = 'C09' 
			 'ASIGNADO_CON_SEGUNDA_PRORROGA', 'SUBSIDIO_LEGALIZADO') THEN 1
		WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND dbo.PostulacionFOVIS.pofEstadoHogar IN ('VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA',
			 'VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA', 'PENDIENTE_APROBACION_PRORROGA') THEN 2
	    WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND dbo.PostulacionFOVIS.pofEstadoHogar IN ('SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO','SUBSIDIO_DESEMBOLSADO') THEN 3
	    WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND dbo.PostulacionFOVIS.pofEstadoHogar = 'VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA' THEN 4
	    WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND dbo.PostulacionFOVIS.pofEstadoHogar = 'RECHAZADO' THEN 5 -- TODO: FALTA SABER QUE ANTES ESTUVO ASIGNADO
	    ELSE NULL
	END AS estadoSubsidio,
	CASE
		WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL--codigoNovedad = 'C09'
		THEN (SELECT prmValor FROM dbo.Parametro WHERE prmNombre = 'CAJA_COMPENSACION_DEPTO_ID') 
		ELSE NULL
	END AS departamentoSubsidio,
	CASE
		WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL--codigoNovedad = 'C09'
		THEN (SELECT prmValor FROM dbo.Parametro WHERE prmNombre = 'CAJA_COMPENSACION_MUNI_ID') 
		ELSE NULL
	END AS municipioSubsidio,
	CASE
		WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL--codigoNovedad = 'C09'
		THEN Legalizacion.fechaTransferencia 
		ELSE NULL
	END AS fechaEntregaUltimoSubsidio, 
	--- ================= FIN NOVEDAD 'C09' =====================
	--===================================================
	--================== INICIO NOVEDAD C04 ===========================
	CASE
		WHEN  dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE') --codigoNovedad = 'C04'
		THEN aporteGenAfiFovis.roaFechaRetiro
		ELSE NULL
	END AS fechaDesvinculacionAportante,
	 ---== NOVEDADES C04 Y C08 ====---
	CASE
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN('RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_TRABAJADOR_DEPENDIENTE')) --codigoNovedad = 'C04' 
			 OR ( dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS', --codigoNovedad = 'C08'
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
			 (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL )  AND rolAfiFovis.roaFechaRetiro IS NOT NULL)
		THEN rolAfiFovis.roaFechaRetiro
		WHEN ( dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS', --codigoNovedad = 'C08'
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND 
			 (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL ) AND  benFovis.benFechaRetiro IS NOT NULL) THEN benFovis.benFechaRetiro
		ELSE NULL
	END AS fechaRetiroAfiliado,
	CASE
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN('RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_TRABAJADOR_DEPENDIENTE') --codigoNovedad = 'C04'
			  AND perDetJefeHogarAfiFovis.pedFechaFallecido IS NOT NULL)
			 THEN perDetJefeHogarAfiFovis.pedFechaFallecido 
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN('RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_TRABAJADOR_DEPENDIENTE') --codigoNovedad = 'C04'
			  AND  perDetBenFovis.pedFechaFallecido IS NOT NULL) THEN perDetBenFovis.pedFechaFallecido
		ELSE NULL
	END AS fechaFallecimiento,
	--============= FIN NOVEDAD C04  ===========
	--- ================= INICIO NOVEDAD 'C09' ====================----
	CASE  
		WHEN --codigoNovedad = 'C09'
			 dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND perBenFovis.perTipoIdentificacion = 'REGISTRO_CIVIL' THEN 'RC'
	    WHEN --codigoNovedad = 'C09' 
	    	 dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND perBenFovis.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
	    WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND perBenFovis.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
	    WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND perBenFovis.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
	    WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND perBenFovis.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
	    WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND perBenFovis.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD'
	    ELSE NULL
	END AS tipoIdbeneficiario,
	CASE
		WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL --codigoNovedad = 'C09' 
		THEN perBenFovis.perNumeroIdentificacion
		ELSE NULL
	END AS numeroIdBeneficiario,
	--- ================= FIN NOVEDAD 'C09' =====================
	/*CASE perDetBenFovis.pedGenero   --TODO: DEL CAMPO 40 AL 54 NO SE USAN
		WHEN 'MASCULINO' THEN 'M'
		ELSE 'F'
	END AS codigoGeneroBeneficiario,
	perDetBenFovis.pedFechaExpedicionDocumento AS fechaNacimientoBeneficiario,
	perBenFovis.perPrimerApellido AS primerApellidoBeneficiario,
	perBenFovis.perSegundoApellido AS segundoApellidoBeneficiario,
	perBenFovis.perPrimerNombre  AS primerNombreBeneficiario,
	perBenFovis.perSegundoNombre AS segundoNombreBeneficiario,
	CASE perEmpAportanteAfi.perTipoIdentificacion--perEmpresaFovis.perTipoIdentificacion
	    WHEN 'CEDULA_CIUDADANIA' THEN 1
		WHEN 'NIT' THEN 2
	END AS tipoIdEmpresaRecibeSubsidio,
	--perEmpresaFovis.perNumeroIdentificacion
	perEmpAportanteAfi.perNumeroIdentificacion AS numeroIdEmpresaRecibeSubsidio,
	--perEmpresaFovis.perDigitoVerificacion 
	perEmpAportanteAfi.perDigitoVerificacion AS digitoVerificacionIdEmpresaSubsidio,
	--perEmpresaFovis.perRazonSocial 
	perEmpAportanteAfi.perRazonSocial AS razonSocialEmpresaSubsidio,
	1 AS aQuienSeOtorgoSubsidio, 
	':fechaInicio' AS fechaInicialPeriodoInformacion,
	':fechaFin' AS fechaFinalPeriodoInformacion,
	0 AS totalRegistroRelacionadosArchivo,
	':nombreReporte' AS nombreDelArchivo,*/ 
	CASE
		WHEN dbo.SolicitudNovedadFovis.snfId IS NOT NULL --codigoNovedad = 'C09' 
		THEN dbo.SolicitudAsignacion.safId 
		ELSE NULL
	END AS identificadorUnicoSubsidio,
	CASE
		WHEN benModificacionFovis.perId IS NOT NULL THEN benModificacionFovis.actualTipoId --codigoNovedad = 'C10'
		WHEN afiModificacionFovis.perId IS NOT NULL THEN afiModificacionFovis.actualTipoId --codigoNovedad = 'C10'
	END AS nuevoTipoIdMiembrePoblacionCubierta,  --CAMPO 56
	CASE
		WHEN benModificacionFovis.perId IS NOT NULL THEN benModificacionFovis.actualNumeroId --codigoNovedad = 'C10'
		WHEN afiModificacionFovis.perId IS NOT NULL THEN afiModificacionFovis.actualNumeroId --codigoNovedad = 'C10'
	END AS nuevoNumeroIdMiembroPoblacionCubierta, -- CAMPO57
	CASE
		WHEN ( (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
			 (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL ))
			 AND rolAfiFovis.roaFechaRetiro IS NOT NULL AND rolAfiFovis.roaMotivoDesafiliacion = 'DESAFILIACION_EMPLEADOR') 
			 THEN 1
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
			 (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL )) 
			  AND ((rolAfiFovis.roaFechaRetiro IS NOT NULL AND rolAfiFovis.roaMotivoDesafiliacion = 'FALLECIMIENTO') OR
			 (benFovis.benFechaRetiro IS NOT NULL AND benFovis.benMotivoDesafiliacion = 'FALLECIMIENTO')) THEN 2
		WHEN (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
			 (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL )) 
			 AND (rolAfiFovis.roaMotivoDesafiliacion = 'RETIRO_POR_CAMBIO_DE_CAJA_COMPENSACION' OR
			 empleadorAportanteFovis.empMotivoDesafiliacion = 'RETIRO_POR_TRASLADO_OTRA_CCF' OR benFovis.benMotivoDesafiliacion = 'RETIRO_POR_CAMBIO_DE_CAJA_COMPENSACION' )
			 THEN 3
	    WHEN ( (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
			 (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL ))
	    	  AND dbo.ParametrizacionNovedad.novTipoTransaccion = 'SUSTITUCION_PATRONAL') THEN 6
	    WHEN ((dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
			 (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL )) AND empleadorAportanteFovis.empMotivoDesafiliacion = 'FUSION_ADQUISICION' 
			  OR  dbo.ParametrizacionNovedad.novTipoTransaccion = 'DESAFILIAR_AUTOMATICAMENTE_EMPLEADORES_SOLICITUD_RECHAZADA'
			  AND rolAfiFovis.roaMotivoDesafiliacion = 'DESAFILIACION_EMPLEADOR') THEN 7
	 	--TODO: FALTAN LAS CAUSAS DE RETIRO DE LA 4 A LA 12
		WHEN ( (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
			 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
			 'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
			 'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
			 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND
			 (benFovis.benFechaRetiro IS NOT NULL OR rolAfiFovis.roaFechaRetiro IS NOT NULL )) 
			 AND benFovis.benFechaRetiro IS NOT NULL AND benFovis.benMotivoDesafiliacion = 'OTROS' ) THEN 30 
		ELSE NULL
	END AS causaRetiro 
	FROM dbo.SolicitudAsignacion
	INNER JOIN dbo.PostulacionFOVIS ON dbo.PostulacionFOVIS.pofSolicitudAsignacion = dbo.SolicitudAsignacion.safId
	INNER JOIN dbo.JefeHogar ON dbo.JefeHogar.jehId = dbo.PostulacionFOVIS.pofJefeHogar
	INNER JOIN dbo.Afiliado AS afiFovis ON afiFovis.afiId =dbo.JefeHogar.jehAfiliado
	INNER JOIN dbo.RolAfiliado AS rolAfiFovis ON rolAfiFovis.roaAfiliado = afiFovis.afiId
	INNER JOIN dbo.Persona AS perJefeHogarAfiFovis ON perJefeHogarAfiFovis.perId = afiFovis.afiPersona
	INNER JOIN dbo.Ubicacion AS ubiAfiFovis ON ubiAfiFovis.ubiId = perJefeHogarAfiFovis.perUbicacionPrincipal
	INNER JOIN dbo.Municipio AS munAfiFovis ON munAfiFovis.munId = ubiAfiFovis.ubiMunicipio
	INNER JOIN dbo.Departamento AS depAfiFovis ON depAfiFovis.depId = munAfiFovis.munDepartamento
	INNER JOIN dbo.PersonaDetalle AS perDetJefeHogarAfiFovis ON perDetJefeHogarAfiFovis.pedPersona = perJefeHogarAfiFovis.perId
	INNER JOIN dbo.AporteDetallado AS aporteDetAfiFovis ON aporteDetAfiFovis.apdPersona = afiFovis.afiPersona
	LEFT JOIN (
				SELECT dbo.AporteGeneral.apgId, dbo.AporteGeneral.apgPersona, dbo.AporteGeneral.apgEmpresa, 
					   dbo.AporteGeneral.apgFechaProcesamiento, afiRol.roaFechaAfiliacion, afiRol.roaFechaRetiro
				FROM dbo.AporteGeneral
				LEFT JOIN dbo.Empleador AS empleadorAporte ON empleadorAporte.empEmpresa = dbo.AporteGeneral.apgEmpresa
				LEFT JOIN dbo.RolAfiliado AS afiRol ON afiRol.roaEmpleador = empleadorAporte.empId
				--WHERE dbo.AporteGeneral.apgId = aporteDetAfiFovis.apdAporteGeneral
				GROUP BY dbo.AporteGeneral.apgId, dbo.AporteGeneral.apgPersona, dbo.AporteGeneral.apgEmpresa, 
						 dbo.AporteGeneral.apgFechaProcesamiento, afiRol.roaFechaAfiliacion, afiRol.roaFechaRetiro 
				--ORDER BY dbo.AporteGeneral.apgFechaProcesamiento DESC
	) AS aporteGenAfiFovis ON aporteGenAfiFovis.apgId = aporteDetAfiFovis.apdAporteGeneral
	LEFT JOIN dbo.Persona AS perAportanteAfi ON perAportanteAfi.perId = aporteGenAfiFovis.apgPersona
	LEFT JOIN dbo.Empresa AS empAportanteAfi ON empAportanteAfi.empId = aporteGenAfiFovis.apgEmpresa
	LEFT JOIN dbo.Empleador AS empleadorAportanteFovis ON empAportanteAfi.empId = empleadorAportanteFovis.empEmpresa
	LEFT JOIN dbo.Persona AS perEmpAportanteAfi ON perEmpAportanteAfi.perId = empAportanteAfi.empPersona
	LEFT JOIN dbo.Ubicacion AS ubiEmpAportanteAfi ON ubiEmpAportanteAfi.ubiId = perEmpAportanteAfi.perUbicacionPrincipal
	LEFT JOIN dbo.Municipio AS munEmpAportanteAfi ON munEmpAportanteAfi.munId = ubiEmpAportanteAfi.ubiMunicipio
	LEFT JOIN dbo.Departamento AS depEmpAportanteAfi ON depEmpAportanteAfi.depId = munEmpAportanteAfi.munDepartamento
	INNER JOIN dbo.Beneficiario AS benFovis ON benFovis.benAfiliado = afiFovis.afiId
	INNER JOIN dbo.BeneficiarioDetalle AS benDetFovis ON benDetFovis.bedId = benFovis.benBeneficiarioDetalle
	INNER JOIN dbo.Persona AS perBenFovis ON perBenFovis.perId = benFovis.benPersona
	INNER JOIN dbo.PersonaDetalle AS perDetBenFovis ON perDetBenFovis.pedPersona = perBenFovis.perId
	INNER JOIN dbo.SolicitudLegalizacionDesembolso ON dbo.SolicitudLegalizacionDesembolso.sldPostulacionFOVIS = dbo.PostulacionFOVIS.pofId
	INNER JOIN (SELECT dbo.LegalizacionDesembolso.lgdFechaTransferencia AS fechaTransferencia, dbo.LegalizacionDesembolso.lgdId AS id
			    FROM dbo.LegalizacionDesembolso
			    --WHERE LegalizacionDesembolso.lgdFechaTransferencia BETWEEN :fechaInicio AND :fechaFin
	 			) AS Legalizacion 
	ON Legalizacion.id = dbo.SolicitudLegalizacionDesembolso.sldLegalizacionDesembolso
	LEFT JOIN dbo.ProyectoSolucionVivienda ON dbo.ProyectoSolucionVivienda.psvId = dbo.PostulacionFOVIS.pofProyectoSolucionVivienda
	LEFT JOIN dbo.Oferente ON dbo.Oferente.ofeId = dbo.ProyectoSolucionVivienda.psvOferente AND  (dbo.Oferente.ofeEmpresa = empAportanteAfi.empId) 
	--LEFT JOIN dbo.Empresa ON dbo.Oferente.ofeEmpresa = dbo.Empresa.empId
	--LEFT JOIN dbo.Persona AS perEmpresaFovis ON perEmpresaFovis.perId = dbo.Empresa.empPersona
	INNER JOIN dbo.Solicitud ON dbo.SolicitudAsignacion.safSolicitudGlobal = dbo.Solicitud.solId
	LEFT JOIN dbo.SolicitudNovedad ON dbo.Solicitud.solId = dbo.SolicitudNovedad.snoSolicitudGlobal
	LEFT JOIN #FechasNovedadesSolicituNovedad AS novedadesAprobadas ON novedadesAprobadas.snoId = dbo.SolicitudNovedad.snoId
	LEFT JOIN dbo.ParametrizacionNovedad ON dbo.SolicitudNovedad.snoNovedad = dbo.ParametrizacionNovedad.novId
	LEFT JOIN dbo.NovedadDetalle ON (dbo.SolicitudNovedad.snoId = dbo.NovedadDetalle.nopId AND dbo.NovedadDetalle.nopVigente = 1)
	LEFT JOIN 
	(
		SELECT personaAfiliadoABeneficiario.perId AS idAfiABen, NULL AS idBenAAfi --se obtienen los afiliados que pasaron a ser beneficiarios
		FROM dbo.Afiliado AS afi1
		INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
				   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
		INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
		INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
				   --AND benFovis.benId = ben1.benId
				   )
		UNION
		SELECT NULL AS idAfiABen, personaBeneficiarioAAFiliado.perId AS idBenAAfi -- se obtienen los beneficiarios que pasaron a ser afiliados
		FROM dbo.Beneficiario AS ben1
		INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
		INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona --AND afi1.afiId = afiFovis.afiId
		INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
		WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
	) AS tablaNuevoAfiBen ON (tablaNuevoAfiBen.idBenAAfi = perJefeHogarAfiFovis.perId OR tablaNuevoAfiBen.idAfiABen = perBenFovis.perId)
	LEFT JOIN dbo.SolicitudNovedadFovis ON dbo.Solicitud.solId = dbo.SolicitudNovedadFovis.snfSolicitudGlobal
	LEFT JOIN #FechasNovedadesSolicituNovedadFovis AS novedadesFovisAprobadas ON novedadesFovisAprobadas.snfId = dbo.SolicitudNovedadFovis.snfId
	LEFT JOIN dbo.SolicitudNovedadPersonaFovis ON (dbo.SolicitudNovedadFovis.snfId = dbo.SolicitudNovedadPersonaFovis.spfSolicitudNovedadFovis 
			   AND dbo.PostulacionFOVIS.pofId = dbo.SolicitudNovedadPersonaFovis.spfPostulacionFovis)
	LEFT JOIN #PersonasModificacionDatosBasicos afiModificacionFovis ON afiModificacionFovis.perId = perJefeHogarAfiFovis.perId   
	LEFT JOIN #PersonasModificacionDatosBasicos benModificacionFovis ON benModificacionFovis.perId = perBenFovis.perId 
	WHERE rolAfiFovis.roaEstadoAfiliado = 'ACTIVO' AND 
	( 
	  (dbo.ParametrizacionNovedad.novTipoTransaccion IN ('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS',
	   'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB', 'CAMBIO_GENERO_PERSONAS', 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS',
	    'AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO',
			 'AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION',
			 'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
			 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO',    
	   'RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_25ANIOS',
	   'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6',
	   'RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR','RETIRO_CONYUGE','RETIRO_HJO_BIOLOGICO','RETIRO_HIJASTRO','RETIRO_HERMANO_HUERFANO',
	   'RETIRO_HIJO_ADOPTIVO','RETIRO_BENEFICIARIO_CUSTODIA','RETIRO_PADRE','RETIRO_MADRE','RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA',
	   'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD','RETIRO_AUTOMATICO_POR_MORA') AND  novedadesAprobadas.fechaRegistroNovedadCerrada BETWEEN @fechaInicio AND @fechaFin)
	   OR (dbo.SolicitudNovedadFovis.snfId IS NOT NULL AND novedadesFovisAprobadas.fechaRegistroNovedadCerrada BETWEEN @fechaInicio AND @fechaFin)
	   OR (afiModificacionFovis.perId IS NOT NULL AND rolAfiFovis.roaFechaAfiliacion BETWEEN @fechaInicio AND @fechaFin)
	   OR (benModificacionFovis.perId IS NOT NULL AND benFovis.benFechaAfiliacion BETWEEN @fechaInicio AND @fechaFin)
	   OR 
	   (
		   (
			  SELECT CASE WHEN
			  EXISTS
		 	  (
		 	  	SELECT personaAfiliadoABeneficiario.perId,NULL --se obtienen los afiliados que pasaron a ser beneficiarios
		 	  	FROM dbo.Afiliado AS afi1
		 	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId
		 	  			   AND rolAfi1.roaFechaRetiro IS NOT NULL AND rolAfi1.roaEstadoAfiliado = 'INACTIVO')
		 	  	INNER JOIN dbo.Persona AS personaAfiliadoABeneficiario ON afi1.afiPersona = personaAfiliadoABeneficiario.perId
		 	  	INNER JOIN dbo.Beneficiario AS ben1 ON (ben1.benPersona = afi1.afiPersona AND ben1.benEstadoBeneficiarioAfiliado = 'ACTIVO'
		 	  			   AND benFovis.benId = ben1.benId)
		 	  	UNION
		 	  	SELECT NULL,personaBeneficiarioAAFiliado.perId -- se obtienen los beneficiarios que pasaron a ser afiliados
		 	  	FROM dbo.Beneficiario AS ben1
		 	  	INNER JOIN dbo.Persona AS personaBeneficiarioAAFiliado ON ben1.benPersona = personaBeneficiarioAAFiliado.perId
		 	  	INNER JOIN dbo.Afiliado AS afi1 ON afi1.afiPersona = ben1.benPersona AND afi1.afiId = afiFovis.afiId
		 	  	INNER JOIN dbo.RolAfiliado AS rolAfi1 ON (rolAfi1.roaAfiliado = afi1.afiId AND rolAfi1.roaEstadoAfiliado = 'ACTIVO')
		 	  	WHERE ben1.benEstadoBeneficiarioAfiliado ='INACTIVO' AND ben1.benFechaRetiro IS NOT NULL
		 	  )
		 	  THEN 1
		 	  ELSE 0 END
		 	) = 1  AND benFovis.benFechaAfiliacion BETWEEN @fechaInicio AND @fechaFin
	   )
	)
	
	GROUP BY
	dbo.SolicitudAsignacion.safId, perJefeHogarAfiFovis.perTipoIdentificacion, perJefeHogarAfiFovis.perNumeroIdentificacion,
	perJefeHogarAfiFovis.perPrimerApellido, perJefeHogarAfiFovis.perSegundoApellido, perJefeHogarAfiFovis.perPrimerNombre,
	perJefeHogarAfiFovis.perSegundoNombre, dbo.SolicitudAsignacion.safFechaAceptacion, dbo.SolicitudAsignacion.safValorSFVAsignado,
	dbo.PostulacionFOVIS.pofEstadoHogar, perBenFovis.perTipoIdentificacion, perBenFovis.perNumeroIdentificacion,
	perDetBenFovis.pedGenero, perDetBenFovis.pedFechaExpedicionDocumento, perBenFovis.perPrimerApellido, Legalizacion.fechaTransferencia,
	perBenFovis.perSegundoApellido, perBenFovis.perPrimerNombre, perBenFovis.perSegundoNombre, rolAfiFovis.roaFechaIngreso,
	rolAfiFovis.roaTipoAfiliado, rolAfiFovis.roaFechaRetiro, rolAfiFovis.roaMotivoDesafiliacion, aporteGenAfiFovis.apgPersona, 
	perAportanteAfi.perTipoIdentificacion, perAportanteAfi.perNumeroIdentificacion, perAportanteAfi.perPrimerNombre ,perAportanteAfi.perSegundoNombre,
	perAportanteAfi.perPrimerApellido,perAportanteAfi.perSegundoApellido, perEmpAportanteAfi.perTipoIdentificacion, perEmpAportanteAfi.perTipoIdentificacion,
	perEmpAportanteAfi.perNumeroIdentificacion, perEmpAportanteAfi.perDigitoVerificacion, perEmpAportanteAfi.perRazonSocial,
	perDetJefeHogarAfiFovis.pedGenero, perDetJefeHogarAfiFovis.pedFechaNacimiento, munAfiFovis.munCodigo, depAfiFovis.depCodigo,
	aporteGenAfiFovis.roaFechaAfiliacion, aporteGenAfiFovis.roaFechaRetiro, depEmpAportanteAfi.depCodigo, munEmpAportanteAfi.munCodigo,
	benFovis.benGradoAcademico, benDetFovis.bedCertificadoEscolaridad, benFovis.benTipoBeneficiario, perDetJefeHogarAfiFovis.pedFechaFallecido,
	benFovis.benId, novTipoTransaccion, benFovis.benFechaRetiro, benFovis.benMotivoDesafiliacion, rolAfiFovis.roaFechaRetiro,
	rolAfiFovis.roaMotivoDesafiliacion, benFovis.benTipoBeneficiario, dbo.SolicitudNovedadFovis.snfId, afiModificacionFovis.perId, benModificacionFovis.perId,
	empleadorAportanteFovis.empMotivoDesafiliacion, safId, afiId, empFechaCambioEstadoAfiliacion, idBenAAfi, idAfiABen,
	benModificacionFovis.actualNumeroId, benModificacionFovis.actualTipoId, benModificacionFovis.anteriorNumeroId, benModificacionFovis.anteriorTipoId,
	afiModificacionFovis.actualNumeroId, afiModificacionFovis.actualTipoId, afiModificacionFovis.anteriorNumeroId, afiModificacionFovis.anteriorTipoId,
	perDetBenFovis.pedFechaFallecido
END	
+END
+;

