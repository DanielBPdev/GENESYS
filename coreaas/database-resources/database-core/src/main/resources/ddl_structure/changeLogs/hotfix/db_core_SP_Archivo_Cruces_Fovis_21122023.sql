/****** Object:  StoredProcedure [dbo].[SP_FOVIS_Procesos_archivo_Cruces]    Script Date: 21/12/2023 15:51:00 ******/
--DROP PROCEDURE [dbo].[SP_FOVIS_Procesos_archivo_Cruces]
/****** Object:  StoredProcedure [dbo].[SP_FOVIS_Procesos_archivo_Cruces]    Script Date: 21/12/2023 15:51:00 ******/
-- =============================================
-- Author:      <John Martínez>
-- Create Date: <2023-09-20>
-- Description: <Creación tabla temporal para generación de vista>
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[SP_FOVIS_Procesos_archivo_Cruces]
(
	@idCargue bigint
)
AS
SET NOCOUNT ON;

--Recupera valor del Nit de la CCF que se encuentra parametrizada en el ambiente
DECLARE @NitCCF as varchar(11)
SET @NitCCF = (select ISNULL(replace(prmValor,'-',''),0) from parametro where prmNombre = 'NUMERO_ID_CCF')

--Variable para identificar si debe o no realizar el cruce de beneficiarios con el nit de la CCF - GAP 72452
DECLARE @CruzaconCCF as int
SET @CruzaconCCF = (SELECT ISNULL(pafValor,0) FROM ParametrizacionFOVIS WHERE pafNombre = 'CRUCE_BENEFICIARIOS_MISMA_CAJA')

--Elimina tabla temporal
drop table if exists #temp_cruces;

--Creación tabla temporal
create table #temp_cruces
(
    [cruCargueArchivoCruceFovis] [bigint] NULL,
    [cruNumeroPostulacion] [varchar](20) NULL,
    [cruPersona] [bigint] NULL,
    [cruEstadoCruce] [varchar](22) NOT NULL,
    [cruFechaRegistro] [datetime] NOT NULL,
    [crdCausalCruce] [varchar](30) NULL,
    [crdNitEntidad] [varchar](16) NULL,
    [crdNombreEntidad] [varchar](100) NULL,
    [crdNumeroIdentificacion] [varchar](16) NULL,
    [crdApellidos] [varchar](100) NULL,
    [crdNombres] [varchar](100) NULL,
    [crdCedulaCatastral] [varchar](50) NULL,
    [crdDireccionInmueble] [varchar](300) NULL,
    [crdMatriculaInmobiliaria] [varchar](50) NULL,
    [crdDepartamento] [varchar](100) NULL,
    [crdMunicipio] [varchar](50) NULL,
    [crdFechaActualizacionMinisterio] [date] NULL,
    [crdFechaCorteEntidad] [date] NULL,
    [crdApellidosNombres] [varchar](200) NULL,
    [crdPuntaje] [varchar](10) NULL,
    [crdSexo] [varchar](20) NULL,
    [crdZona] [varchar](30) NULL,
    [crdParentesco] [varchar](30) NULL,
    [crdFolio] [varchar](30) NULL,
    [crdTipodocumento] [varchar](30) NULL,
    [crdFechaSolicitud] [date] NULL,
    [crdEntidadOtorgante] [varchar](30) NULL,
    [crdCajaCompensacion] [varchar](30) NULL,
    [crdAsignadoPosterior] [varchar](30) NULL,
    [crdTipo] [varchar](15) NULL,
    [crdResultadoValidacion] [varchar](255) NULL,
    [crdClasificacion] [varchar](30) NULL
	);
/***************Recupera e inserta cruces afiliados******************/

insert into #temp_cruces (cruCargueArchivoCruceFovis,cruNumeroPostulacion,cruPersona,cruEstadoCruce,cruFechaRegistro,
                          crdCausalCruce,crdNitEntidad,crdNombreEntidad,crdNumeroIdentificacion, crdApellidos, crdNombres, crdFechaActualizacionMinisterio,
                          crdFechaCorteEntidad, crdTipodocumento, crdTipo)
select  cfaCargueArchivoCruceFovis
     ,solNumeroRadicacion
     ,perId
     ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END estadoCruce
     ,GETDATE() fechaRegistro
     ,'AFILIACION_CAJA' causalCruce
     ,cfaNitEntidad nitEntidad
     ,cfaNombreEntidad nombreEntidad
     ,cfaIdentificacion numeroIdentificacion
     ,cfaApellidos apellidos
     ,cfaNombres nombres
     ,cffFechaActualizacion
     ,cffFechaCorte
     ,cfaTipoIdentificacion tipoDocumento
     , 'AFILIADOS' tipo
from
    CargueArchivoCruceFovisCedula
        inner join CargueArchivoCruceFovisAfiliado
                   on cfcCargueArchivoCruceFovis = cfaCargueArchivoCruceFovis
                       and cfcNroCedula = cfaIdentificacion
        INNER JOIN CargueArchivoCruceFovisFechasCorte ON cffCargueArchivoCruceFovis = cfaCargueArchivoCruceFovis
        AND cffNitEntidad = cfaNitEntidad and cffTipoInformacion = 'AFILIADOS'
        INNER JOIN Persona ON perNumeroIdentificacion = cfaIdentificacion
        INNER JOIN Afiliado ON afiPersona = perId
        INNER JOIN JefeHogar ON jehAfiliado = afiId
        INNER JOIN PostulacionFOVIS ON pofJefeHogar = jehId
        INNER JOIN SolicitudPostulacion ON pofId = spoPostulacionFOVIS
        INNER JOIN Solicitud ON spoSolicitudGlobal = solId
        LEFT JOIN CruceDetalle on crdTipo = 'AFILIADOS'
        AND crdTipodocumento = cfaTipoIdentificacion
        AND crdNumeroIdentificacion = cfaIdentificacion
where cfcCargueArchivoCruceFovis = @idCargue
  AND pofEstadoHogar in ('POSTULADO','HABIL','HABIL_SEGUNDO_ANIO')
  AND crdid IS NULL AND cfaNitEntidad <> @NitCCF

    insert into #temp_cruces (cruCargueArchivoCruceFovis,cruNumeroPostulacion,cruPersona,cruEstadoCruce,cruFechaRegistro,
                              crdCausalCruce,crdNitEntidad,crdNombreEntidad,crdNumeroIdentificacion, crdApellidos, crdNombres, crdFechaActualizacionMinisterio,
                              crdFechaCorteEntidad, crdTipodocumento, crdTipo)
select  cfaCargueArchivoCruceFovis
     ,solNumeroRadicacion
     ,perId
     ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END estadoCruce
     ,GETDATE() fechaRegistro
     ,'AFILIACION_CAJA' causalCruce
     ,cfaNitEntidad nitEntidad
     ,cfaNombreEntidad nombreEntidad
     ,cfaIdentificacion numeroIdentificacion
     ,cfaApellidos apellidos
     ,cfaNombres nombres
     ,cffFechaActualizacion
     ,cffFechaCorte
     ,cfaTipoIdentificacion tipoDocumento
     , 'AFILIADOS' tipo
from
    CargueArchivoCruceFovisCedula
        inner join CargueArchivoCruceFovisAfiliado
                   on cfcCargueArchivoCruceFovis = cfaCargueArchivoCruceFovis
                       and cfcNroCedula = cfaIdentificacion
        INNER JOIN CargueArchivoCruceFovisFechasCorte ON cffCargueArchivoCruceFovis = cfaCargueArchivoCruceFovis
        AND cffNitEntidad = cfaNitEntidad and cffTipoInformacion = 'AFILIADOS'
        INNER JOIN Persona ON perNumeroIdentificacion = cfaIdentificacion
        INNER JOIN IntegranteHogar ON inhPersona = perId
        INNER JOIN PostulacionFOVIS ON pofId = inhPostulacionFovis
        INNER JOIN SolicitudPostulacion ON pofId = spoPostulacionFOVIS
        INNER JOIN Solicitud ON spoSolicitudGlobal = solId
        LEFT JOIN CruceDetalle on crdTipo = 'AFILIADOS'
        AND crdTipodocumento = cfaTipoIdentificacion
        AND crdNumeroIdentificacion = cfaIdentificacion
where cfcCargueArchivoCruceFovis = @idCargue
  AND pofEstadoHogar in ('POSTULADO','HABIL','HABIL_SEGUNDO_ANIO')
  AND crdid IS NULL AND cfaNitEntidad <> @NitCCF


    insert into #temp_cruces (cruCargueArchivoCruceFovis,cruNumeroPostulacion,cruPersona,cruEstadoCruce,cruFechaRegistro,
                              crdCausalCruce,crdNitEntidad,crdNombreEntidad,crdNumeroIdentificacion, crdApellidos, crdNombres, crdFechaActualizacionMinisterio,
                              crdFechaCorteEntidad, crdTipodocumento, crdTipo)
select distinct cfaCargueArchivoCruceFovis
              ,solNumeroRadicacion
              ,perId
              ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END estadoCruce
              ,GETDATE() fechaRegistro
              ,'AFILIACION_CAJA' causalCruce
              ,cfaNitEntidad nitEntidad
              ,cfaNombreEntidad nombreEntidad
              ,cfaIdentificacion numeroIdentificacion
              ,cfaApellidos apellidos
              ,cfaNombres nombres
              ,cffFechaActualizacion
              ,cffFechaCorte
              ,cfaTipoIdentificacion tipoDocumento
              , 'AFILIADOS' tipo
from
    CargueArchivoCruceFovisCedula
        inner join CargueArchivoCruceFovisAfiliado
                   on cfcCargueArchivoCruceFovis = cfaCargueArchivoCruceFovis
                       and cfcNroCedula = cfaIdentificacion
        INNER JOIN CargueArchivoCruceFovisFechasCorte ON cffCargueArchivoCruceFovis = cfaCargueArchivoCruceFovis
        AND cffNitEntidad = cfaNitEntidad and cffTipoInformacion = 'AFILIADOS'
        INNER JOIN Persona ON perNumeroIdentificacion = cfaIdentificacion
        INNER JOIN Afiliado ON afiPersona = perId
        INNER JOIN JefeHogar ON jehAfiliado = afiId
        INNER JOIN PostulacionFOVIS ON pofJefeHogar = jehId
        INNER JOIN SolicitudPostulacion ON pofId = spoPostulacionFOVIS
        INNER JOIN Solicitud ON spoSolicitudGlobal = solId
        LEFT JOIN CruceDetalle on crdTipo = 'AFILIADOS'
        AND crdTipodocumento = cfaTipoIdentificacion
        AND crdNumeroIdentificacion = cfaIdentificacion
where cfcCargueArchivoCruceFovis = @idCargue
  AND pofEstadoHogar in ('POSTULADO','HABIL','HABIL_SEGUNDO_ANIO')
  AND crdid IS not NULL AND cfaNitEntidad <> @NitCCF

    insert into #temp_cruces (cruCargueArchivoCruceFovis,cruNumeroPostulacion,cruPersona,cruEstadoCruce,cruFechaRegistro,
                              crdCausalCruce,crdNitEntidad,crdNombreEntidad,crdNumeroIdentificacion, crdApellidos, crdNombres, crdFechaActualizacionMinisterio,
                              crdFechaCorteEntidad, crdTipodocumento, crdTipo)
select  distinct cfaCargueArchivoCruceFovis
               ,solNumeroRadicacion
               ,perId
               ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END estadoCruce
               ,GETDATE() fechaRegistro
               ,'AFILIACION_CAJA' causalCruce
               ,cfaNitEntidad nitEntidad
               ,cfaNombreEntidad nombreEntidad
               ,cfaIdentificacion numeroIdentificacion
               ,cfaApellidos apellidos
               ,cfaNombres nombres
               ,cffFechaActualizacion
               ,cffFechaCorte
               ,cfaTipoIdentificacion tipoDocumento
               , 'AFILIADOS' tipo
from
    CargueArchivoCruceFovisCedula
        inner join CargueArchivoCruceFovisAfiliado
                   on cfcCargueArchivoCruceFovis = cfaCargueArchivoCruceFovis
                       and cfcNroCedula = cfaIdentificacion
        INNER JOIN CargueArchivoCruceFovisFechasCorte ON cffCargueArchivoCruceFovis = cfaCargueArchivoCruceFovis
        AND cffNitEntidad = cfaNitEntidad and cffTipoInformacion = 'AFILIADOS'
        INNER JOIN Persona ON perNumeroIdentificacion = cfaIdentificacion
        INNER JOIN IntegranteHogar ON inhPersona = perId
        INNER JOIN PostulacionFOVIS ON pofId = inhPostulacionFovis
        INNER JOIN SolicitudPostulacion ON pofId = spoPostulacionFOVIS
        INNER JOIN Solicitud ON spoSolicitudGlobal = solId
        LEFT JOIN CruceDetalle on crdTipo = 'AFILIADOS'
        AND crdTipodocumento = cfaTipoIdentificacion
        AND crdNumeroIdentificacion = cfaIdentificacion
where cfcCargueArchivoCruceFovis = @idCargue
  AND pofEstadoHogar in ('POSTULADO','HABIL','HABIL_SEGUNDO_ANIO')
  AND crdid IS not NULL AND cfaNitEntidad <> @NitCCF

/***************Fin Recupera e inserta cruces afiliados******************/

/***************Recupera e inserta cruces beneficiarios******************/
/*Se modifica para que haga el cruce por aparte con los nit 8301212085
  y 8999990389 que llegan duplicados desde la hoja de Fechas de Corte  */

/*John Martinez - 20231215
Se agrega la variable @CruzaconCCF para determinar si debe hacer el cruce
de beneficiarios con la misma CCF o no
*/

    insert into #temp_cruces (cruCargueArchivoCruceFovis,cruNumeroPostulacion,cruPersona,cruEstadoCruce,cruFechaRegistro,
                              crdCausalCruce,crdNitEntidad,crdNombreEntidad,crdNumeroIdentificacion, crdApellidos, crdNombres, crdFechaActualizacionMinisterio,
                              crdFechaCorteEntidad, crdTipodocumento, crdTipo)

select cfbCargueArchivoCruceFovis
     ,solNumeroRadicacion
     ,perId
     ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END estadoCruce
     ,GETDATE() fechaRegistro
     ,'BENEFICIO_SUBSIDIO_RECIBIDO' causalCruce
     ,cfbNitEntidad nitEntidad
     ,cfbNombreEntidad nombreEntidad
     ,cfbIdentificacion numeroIdentificacion
     ,cfbApellidos apellidos
     ,cfbNombres nombres
     ,cffFechaActualizacion
     ,cffFechaCorte
     ,cfbTipoIdentificacion tipoDocumento
     , 'BENEFICIARIOS' tipo
from
    CargueArchivoCruceFovisCedula
        inner join CargueArchivoCruceFovisBeneficiario
                   on cfcCargueArchivoCruceFovis = cfbCargueArchivoCruceFovis
                       and cfcNroCedula = cfbIdentificacion
        INNER JOIN CargueArchivoCruceFovisFechasCorte ON cffCargueArchivoCruceFovis = cfbCargueArchivoCruceFovis
        AND cffNitEntidad = cfbNitEntidad and cffTipoInformacion = 'BENEFICIARIOS' and cffNitEntidad not in (8301212085,8999990389)
        INNER JOIN Persona ON perNumeroIdentificacion = cfbIdentificacion
        INNER JOIN Afiliado ON afiPersona = perId
        INNER JOIN JefeHogar ON jehAfiliado = afiId
        INNER JOIN PostulacionFOVIS ON pofJefeHogar = jehId
        INNER JOIN SolicitudPostulacion ON pofId = spoPostulacionFOVIS
        INNER JOIN Solicitud ON spoSolicitudGlobal = solId
        LEFT JOIN CruceDetalle on crdTipo = 'BENEFICIARIOS'
        AND crdTipodocumento = cfbTipoIdentificacion
        AND crdNumeroIdentificacion = cfbIdentificacion
where cfcCargueArchivoCruceFovis = @idCargue
  AND pofEstadoHogar in ('POSTULADO','HABIL','HABIL_SEGUNDO_ANIO')
  AND crdid IS NULL AND cfbNitEntidad <> (CASE WHEN @CruzaconCCF = 0 THEN @NitCCF ELSE '0' END)


    insert into #temp_cruces (cruCargueArchivoCruceFovis,cruNumeroPostulacion,cruPersona,cruEstadoCruce,cruFechaRegistro,
                              crdCausalCruce,crdNitEntidad,crdNombreEntidad,crdNumeroIdentificacion, crdApellidos, crdNombres, crdFechaActualizacionMinisterio,
                              crdFechaCorteEntidad, crdTipodocumento, crdTipo)

select cfbCargueArchivoCruceFovis
     ,solNumeroRadicacion
     ,perId
     ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END estadoCruce
     ,GETDATE() fechaRegistro
     ,'BENEFICIO_SUBSIDIO_RECIBIDO' causalCruce
     ,cfbNitEntidad nitEntidad
     ,cfbNombreEntidad nombreEntidad
     ,cfbIdentificacion numeroIdentificacion
     ,cfbApellidos apellidos
     ,cfbNombres nombres
     ,min(cffFechaActualizacion) cffFechaActualizacion
     ,min(cffFechaCorte) cffFechaCorte
     ,cfbTipoIdentificacion tipoDocumento
     , 'BENEFICIARIOS' tipo
from
    CargueArchivoCruceFovisCedula
        inner join CargueArchivoCruceFovisBeneficiario
                   on cfcCargueArchivoCruceFovis = cfbCargueArchivoCruceFovis
                       and cfcNroCedula = cfbIdentificacion
        INNER JOIN CargueArchivoCruceFovisFechasCorte ON cffCargueArchivoCruceFovis = cfbCargueArchivoCruceFovis
        AND cffNitEntidad = cfbNitEntidad and cffTipoInformacion = 'BENEFICIARIOS' and cffNitEntidad in (8301212085,8999990389)
        INNER JOIN Persona ON perNumeroIdentificacion = cfbIdentificacion
        INNER JOIN Afiliado ON afiPersona = perId
        INNER JOIN JefeHogar ON jehAfiliado = afiId
        INNER JOIN PostulacionFOVIS ON pofJefeHogar = jehId
        INNER JOIN SolicitudPostulacion ON pofId = spoPostulacionFOVIS
        INNER JOIN Solicitud ON spoSolicitudGlobal = solId
        LEFT JOIN CruceDetalle on crdTipo = 'BENEFICIARIOS'
        AND crdTipodocumento = cfbTipoIdentificacion
        AND crdNumeroIdentificacion = cfbIdentificacion
where cfcCargueArchivoCruceFovis = @idCargue
  AND pofEstadoHogar in ('POSTULADO','HABIL','HABIL_SEGUNDO_ANIO')
  AND crdid IS NULL AND cfbNitEntidad <> (CASE WHEN @CruzaconCCF = 0 THEN @NitCCF ELSE '0' END)
group by
    cfbCargueArchivoCruceFovis
       ,solNumeroRadicacion
       ,perId
       ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END
       ,cfbNitEntidad
       ,cfbNombreEntidad
       ,cfbIdentificacion
       ,cfbApellidos
       ,cfbNombres
       ,cfbTipoIdentificacion

    insert into #temp_cruces (cruCargueArchivoCruceFovis,cruNumeroPostulacion,cruPersona,cruEstadoCruce,cruFechaRegistro,
                              crdCausalCruce,crdNitEntidad,crdNombreEntidad,crdNumeroIdentificacion, crdApellidos, crdNombres, crdFechaActualizacionMinisterio,
                              crdFechaCorteEntidad, crdTipodocumento, crdTipo)

select cfbCargueArchivoCruceFovis
     ,solNumeroRadicacion
     ,perId
     ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END estadoCruce
     ,GETDATE() fechaRegistro
     ,'BENEFICIO_SUBSIDIO_RECIBIDO' causalCruce
     ,cfbNitEntidad nitEntidad
     ,cfbNombreEntidad nombreEntidad
     ,cfbIdentificacion numeroIdentificacion
     ,cfbApellidos apellidos
     ,cfbNombres nombres
     ,cffFechaActualizacion
     ,cffFechaCorte
     ,cfbTipoIdentificacion tipoDocumento
     , 'BENEFICIARIOS' tipo
from
    CargueArchivoCruceFovisCedula
        inner join CargueArchivoCruceFovisBeneficiario
                   on cfcCargueArchivoCruceFovis = cfbCargueArchivoCruceFovis
                       and cfcNroCedula = cfbIdentificacion
        INNER JOIN CargueArchivoCruceFovisFechasCorte ON cffCargueArchivoCruceFovis = cfbCargueArchivoCruceFovis
        AND cffNitEntidad = cfbNitEntidad and cffTipoInformacion = 'BENEFICIARIOS'
        and cffNitEntidad not in (8301212085,8999990389)
        INNER JOIN Persona ON perNumeroIdentificacion = cfbIdentificacion
        INNER JOIN IntegranteHogar ON inhPersona = perId
        INNER JOIN PostulacionFOVIS ON pofId = inhPostulacionFovis

        INNER JOIN SolicitudPostulacion ON pofId = spoPostulacionFOVIS
        INNER JOIN Solicitud ON spoSolicitudGlobal = solId
        LEFT JOIN CruceDetalle on crdTipo = 'BENEFICIARIOS'
        AND crdTipodocumento = cfbTipoIdentificacion
        AND crdNumeroIdentificacion = cfbIdentificacion
where cfcCargueArchivoCruceFovis = @idCargue
  AND pofEstadoHogar in ('POSTULADO','HABIL','HABIL_SEGUNDO_ANIO')
  AND crdid IS NULL AND cfbNitEntidad <> (CASE WHEN @CruzaconCCF = 0 THEN @NitCCF ELSE '0' END)


    insert into #temp_cruces (cruCargueArchivoCruceFovis,cruNumeroPostulacion,cruPersona,cruEstadoCruce,cruFechaRegistro,
                              crdCausalCruce,crdNitEntidad,crdNombreEntidad,crdNumeroIdentificacion, crdApellidos, crdNombres, crdFechaActualizacionMinisterio,
                              crdFechaCorteEntidad, crdTipodocumento, crdTipo)

select cfbCargueArchivoCruceFovis
     ,solNumeroRadicacion
     ,perId
     ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END estadoCruce
     ,GETDATE() fechaRegistro
     ,'BENEFICIO_SUBSIDIO_RECIBIDO' causalCruce
     ,cfbNitEntidad nitEntidad
     ,cfbNombreEntidad nombreEntidad
     ,cfbIdentificacion numeroIdentificacion
     ,cfbApellidos apellidos
     ,cfbNombres nombres
     ,min(cffFechaActualizacion) cffFechaActualizacion
     ,min(cffFechaCorte) cffFechaCorte
     ,cfbTipoIdentificacion tipoDocumento
     , 'BENEFICIARIOS' tipo
from
    CargueArchivoCruceFovisCedula
        inner join CargueArchivoCruceFovisBeneficiario
                   on cfcCargueArchivoCruceFovis = cfbCargueArchivoCruceFovis
                       and cfcNroCedula = cfbIdentificacion
        INNER JOIN CargueArchivoCruceFovisFechasCorte ON cffCargueArchivoCruceFovis = cfbCargueArchivoCruceFovis
        AND cffNitEntidad = cfbNitEntidad and cffTipoInformacion = 'BENEFICIARIOS'
        and cffNitEntidad in (8301212085,8999990389)
        INNER JOIN Persona ON perNumeroIdentificacion = cfbIdentificacion
        INNER JOIN IntegranteHogar ON inhPersona = perId
        INNER JOIN PostulacionFOVIS ON pofId = inhPostulacionFovis

        INNER JOIN SolicitudPostulacion ON pofId = spoPostulacionFOVIS
        INNER JOIN Solicitud ON spoSolicitudGlobal = solId
        LEFT JOIN CruceDetalle on crdTipo = 'BENEFICIARIOS'
        AND crdTipodocumento = cfbTipoIdentificacion
        AND crdNumeroIdentificacion = cfbIdentificacion
where cfcCargueArchivoCruceFovis = @idCargue
  AND pofEstadoHogar in ('POSTULADO','HABIL','HABIL_SEGUNDO_ANIO')
  AND crdid IS NULL AND cfbNitEntidad <> (CASE WHEN @CruzaconCCF = 0 THEN @NitCCF ELSE '0' END)
group by
    cfbCargueArchivoCruceFovis
       ,solNumeroRadicacion
       ,perId
       ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END
       ,cfbNitEntidad
       ,cfbNombreEntidad
       ,cfbIdentificacion
       ,cfbApellidos
       ,cfbNombres
       ,cfbTipoIdentificacion


    insert into #temp_cruces (cruCargueArchivoCruceFovis,cruNumeroPostulacion,cruPersona,cruEstadoCruce,cruFechaRegistro,
                              crdCausalCruce,crdNitEntidad,crdNombreEntidad,crdNumeroIdentificacion, crdApellidos, crdNombres, crdFechaActualizacionMinisterio,
                              crdFechaCorteEntidad, crdTipodocumento, crdTipo)

select cfbCargueArchivoCruceFovis
     ,solNumeroRadicacion
     ,perId
     ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END estadoCruce
     ,GETDATE() fechaRegistro
     ,'BENEFICIO_SUBSIDIO_RECIBIDO' causalCruce
     ,cfbNitEntidad nitEntidad
     ,cfbNombreEntidad nombreEntidad
     ,cfbIdentificacion numeroIdentificacion
     ,cfbApellidos apellidos
     ,cfbNombres nombres
     ,cffFechaActualizacion
     ,cffFechaCorte
     ,cfbTipoIdentificacion tipoDocumento
     , 'BENEFICIARIOS' tipo
from
    CargueArchivoCruceFovisCedula
        inner join CargueArchivoCruceFovisBeneficiario
                   on cfcCargueArchivoCruceFovis = cfbCargueArchivoCruceFovis
                       and cfcNroCedula = cfbIdentificacion
        INNER JOIN CargueArchivoCruceFovisFechasCorte ON cffCargueArchivoCruceFovis = cfbCargueArchivoCruceFovis
        AND cffNitEntidad = cfbNitEntidad and cffTipoInformacion = 'BENEFICIARIOS' and cffNitEntidad not in (8301212085,8999990389)
        INNER JOIN Persona ON perNumeroIdentificacion = cfbIdentificacion
        INNER JOIN Afiliado ON afiPersona = perId
        INNER JOIN JefeHogar ON jehAfiliado = afiId
        INNER JOIN PostulacionFOVIS ON pofJefeHogar = jehId
        INNER JOIN SolicitudPostulacion ON pofId = spoPostulacionFOVIS
        INNER JOIN Solicitud ON spoSolicitudGlobal = solId
        LEFT JOIN CruceDetalle on crdTipo = 'BENEFICIARIOS'
        AND crdTipodocumento = cfbTipoIdentificacion
        AND crdNumeroIdentificacion = cfbIdentificacion
where cfcCargueArchivoCruceFovis = @idCargue
  AND pofEstadoHogar in ('POSTULADO','HABIL','HABIL_SEGUNDO_ANIO')
  AND crdid IS NOT NULL AND cfbNitEntidad <> (CASE WHEN @CruzaconCCF = 0 THEN @NitCCF ELSE '0' END)


    insert into #temp_cruces (cruCargueArchivoCruceFovis,cruNumeroPostulacion,cruPersona,cruEstadoCruce,cruFechaRegistro,
                              crdCausalCruce,crdNitEntidad,crdNombreEntidad,crdNumeroIdentificacion, crdApellidos, crdNombres, crdFechaActualizacionMinisterio,
                              crdFechaCorteEntidad, crdTipodocumento, crdTipo)

select cfbCargueArchivoCruceFovis
     ,solNumeroRadicacion
     ,perId
     ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END estadoCruce
     ,GETDATE() fechaRegistro
     ,'BENEFICIO_SUBSIDIO_RECIBIDO' causalCruce
     ,cfbNitEntidad nitEntidad
     ,cfbNombreEntidad nombreEntidad
     ,cfbIdentificacion numeroIdentificacion
     ,cfbApellidos apellidos
     ,cfbNombres nombres
     ,min(cffFechaActualizacion) cffFechaActualizacion
     ,min(cffFechaCorte) cffFechaCorte
     ,cfbTipoIdentificacion tipoDocumento
     , 'BENEFICIARIOS' tipo
from
    CargueArchivoCruceFovisCedula
        inner join CargueArchivoCruceFovisBeneficiario
                   on cfcCargueArchivoCruceFovis = cfbCargueArchivoCruceFovis
                       and cfcNroCedula = cfbIdentificacion
        INNER JOIN CargueArchivoCruceFovisFechasCorte ON cffCargueArchivoCruceFovis = cfbCargueArchivoCruceFovis
        AND cffNitEntidad = cfbNitEntidad and cffTipoInformacion = 'BENEFICIARIOS' and cffNitEntidad in (8301212085,8999990389)
        INNER JOIN Persona ON perNumeroIdentificacion = cfbIdentificacion
        INNER JOIN Afiliado ON afiPersona = perId
        INNER JOIN JefeHogar ON jehAfiliado = afiId
        INNER JOIN PostulacionFOVIS ON pofJefeHogar = jehId
        INNER JOIN SolicitudPostulacion ON pofId = spoPostulacionFOVIS
        INNER JOIN Solicitud ON spoSolicitudGlobal = solId
        LEFT JOIN CruceDetalle on crdTipo = 'BENEFICIARIOS'
        AND crdTipodocumento = cfbTipoIdentificacion
        AND crdNumeroIdentificacion = cfbIdentificacion
where cfcCargueArchivoCruceFovis = @idCargue
  AND pofEstadoHogar in ('POSTULADO','HABIL','HABIL_SEGUNDO_ANIO')
  AND crdid IS NOT NULL AND cfbNitEntidad <> (CASE WHEN @CruzaconCCF = 0 THEN @NitCCF ELSE '0' END)
group by
    cfbCargueArchivoCruceFovis
       ,solNumeroRadicacion
       ,perId
       ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END
       ,cfbNitEntidad
       ,cfbNombreEntidad
       ,cfbIdentificacion
       ,cfbApellidos
       ,cfbNombres
       ,cfbTipoIdentificacion

    insert into #temp_cruces (cruCargueArchivoCruceFovis,cruNumeroPostulacion,cruPersona,cruEstadoCruce,cruFechaRegistro,
                              crdCausalCruce,crdNitEntidad,crdNombreEntidad,crdNumeroIdentificacion, crdApellidos, crdNombres, crdFechaActualizacionMinisterio,
                              crdFechaCorteEntidad, crdTipodocumento, crdTipo)

select cfbCargueArchivoCruceFovis
     ,solNumeroRadicacion
     ,perId
     ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END estadoCruce
     ,GETDATE() fechaRegistro
     ,'BENEFICIO_SUBSIDIO_RECIBIDO' causalCruce
     ,cfbNitEntidad nitEntidad
     ,cfbNombreEntidad nombreEntidad
     ,cfbIdentificacion numeroIdentificacion
     ,cfbApellidos apellidos
     ,cfbNombres nombres
     ,cffFechaActualizacion
     ,cffFechaCorte
     ,cfbTipoIdentificacion tipoDocumento
     , 'BENEFICIARIOS' tipo
from
    CargueArchivoCruceFovisCedula
        inner join CargueArchivoCruceFovisBeneficiario
                   on cfcCargueArchivoCruceFovis = cfbCargueArchivoCruceFovis
                       and cfcNroCedula = cfbIdentificacion
        INNER JOIN CargueArchivoCruceFovisFechasCorte ON cffCargueArchivoCruceFovis = cfbCargueArchivoCruceFovis
        AND cffNitEntidad = cfbNitEntidad and cffTipoInformacion = 'BENEFICIARIOS'
        and cffNitEntidad not in (8301212085,8999990389)
        INNER JOIN Persona ON perNumeroIdentificacion = cfbIdentificacion
        INNER JOIN IntegranteHogar ON inhPersona = perId
        INNER JOIN PostulacionFOVIS ON pofId = inhPostulacionFovis

        INNER JOIN SolicitudPostulacion ON pofId = spoPostulacionFOVIS
        INNER JOIN Solicitud ON spoSolicitudGlobal = solId
        LEFT JOIN CruceDetalle on crdTipo = 'BENEFICIARIOS'
        AND crdTipodocumento = cfbTipoIdentificacion
        AND crdNumeroIdentificacion = cfbIdentificacion
where cfcCargueArchivoCruceFovis = @idCargue
  AND pofEstadoHogar in ('POSTULADO','HABIL','HABIL_SEGUNDO_ANIO')
  AND crdid IS NOT NULL AND cfbNitEntidad <> (CASE WHEN @CruzaconCCF = 0 THEN @NitCCF ELSE '0' END)


    insert into #temp_cruces (cruCargueArchivoCruceFovis,cruNumeroPostulacion,cruPersona,cruEstadoCruce,cruFechaRegistro,
                              crdCausalCruce,crdNitEntidad,crdNombreEntidad,crdNumeroIdentificacion, crdApellidos, crdNombres, crdFechaActualizacionMinisterio,
                              crdFechaCorteEntidad, crdTipodocumento, crdTipo)

select cfbCargueArchivoCruceFovis
     ,solNumeroRadicacion
     ,perId
     ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END estadoCruce
     ,GETDATE() fechaRegistro
     ,'BENEFICIO_SUBSIDIO_RECIBIDO' causalCruce
     ,cfbNitEntidad nitEntidad
     ,cfbNombreEntidad nombreEntidad
     ,cfbIdentificacion numeroIdentificacion
     ,cfbApellidos apellidos
     ,cfbNombres nombres
     ,min(cffFechaActualizacion) cffFechaActualizacion
     ,min(cffFechaCorte) cffFechaCorte
     ,cfbTipoIdentificacion tipoDocumento
     , 'BENEFICIARIOS' tipo
from
    CargueArchivoCruceFovisCedula
        inner join CargueArchivoCruceFovisBeneficiario
                   on cfcCargueArchivoCruceFovis = cfbCargueArchivoCruceFovis
                       and cfcNroCedula = cfbIdentificacion
        INNER JOIN CargueArchivoCruceFovisFechasCorte ON cffCargueArchivoCruceFovis = cfbCargueArchivoCruceFovis
        AND cffNitEntidad = cfbNitEntidad and cffTipoInformacion = 'BENEFICIARIOS'
        and cffNitEntidad in (8301212085,8999990389)
        INNER JOIN Persona ON perNumeroIdentificacion = cfbIdentificacion
        INNER JOIN IntegranteHogar ON inhPersona = perId
        INNER JOIN PostulacionFOVIS ON pofId = inhPostulacionFovis

        INNER JOIN SolicitudPostulacion ON pofId = spoPostulacionFOVIS
        INNER JOIN Solicitud ON spoSolicitudGlobal = solId
        LEFT JOIN CruceDetalle on crdTipo = 'BENEFICIARIOS'
        AND crdTipodocumento = cfbTipoIdentificacion
        AND crdNumeroIdentificacion = cfbIdentificacion
where cfcCargueArchivoCruceFovis = @idCargue
  AND pofEstadoHogar in ('POSTULADO','HABIL','HABIL_SEGUNDO_ANIO')
  AND crdid IS NOT NULL AND cfbNitEntidad <> (CASE WHEN @CruzaconCCF = 0 THEN @NitCCF ELSE '0' END)
group by
    cfbCargueArchivoCruceFovis
       ,solNumeroRadicacion
       ,perId
       ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END
       ,cfbNitEntidad
       ,cfbNombreEntidad
       ,cfbIdentificacion
       ,cfbApellidos
       ,cfbNombres
       ,cfbTipoIdentificacion


/***************Fin Recupera e inserta cruces beneficiarios******************/


/***************Recupera e inserta cruces catastros******************/

    insert into #temp_cruces (cruCargueArchivoCruceFovis,cruNumeroPostulacion,cruPersona,cruEstadoCruce,cruFechaRegistro,
                              crdCausalCruce,crdNitEntidad,crdNombreEntidad,crdNumeroIdentificacion,crdApellidosNombres,crdCedulaCatastral,crdDireccionInmueble,
                              crdMatriculaInmobiliaria,crdDepartamento,crdMunicipio,crdFechaActualizacionMinisterio,crdFechaCorteEntidad, crdTipodocumento, crdTipo)

select  cfdCargueArchivoCruceFovis
     ,solNumeroRadicacion
     ,perId
     ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END estadoCruce
     ,GETDATE() fechaRegistro
     ,'REGISTRO_PROPIEDAD' causalCruce
     ,cfdNitEntidad nitEntidad
     ,cfdNombreEntidad nombreEntidad
     ,cfdIdentificacion numeroIdentificacion
     ,cfdApellidosNombres apellidosnombres
     ,cfdCedulaCatastral cedulacatastral
     ,cfdDireccion direccion
     ,cfdMatriculaInmobiliaria matricula
     ,cfdDepartamento departamento
     ,cfdMunicipio municipio
     ,cffFechaActualizacion
     ,cffFechaCorte
     ,cfdTipoIdentificacion tipoDocumento
     , 'CATASTROS' tipo
from
    CargueArchivoCruceFovisCedula
        inner join CargueArchivoCruceFovisCatastros
                   on cfcCargueArchivoCruceFovis = cfdCargueArchivoCruceFovis
                       and cfcNroCedula = cfdIdentificacion
        INNER JOIN CargueArchivoCruceFovisFechasCorte ON cffCargueArchivoCruceFovis = cfdCargueArchivoCruceFovis
        AND cffNitEntidad = cfdNitEntidad and cffTipoInformacion = 'PROPIETARIOS'
        INNER JOIN Persona ON perNumeroIdentificacion = cfdIdentificacion
        INNER JOIN Afiliado ON afiPersona = perId
        INNER JOIN JefeHogar ON jehAfiliado = afiId
        INNER JOIN PostulacionFOVIS ON pofJefeHogar = jehId
        INNER JOIN SolicitudPostulacion ON pofId = spoPostulacionFOVIS
        INNER JOIN Solicitud ON spoSolicitudGlobal = solId
        LEFT JOIN CruceDetalle on crdTipo = 'CATASTROS'
        AND crdTipodocumento = cfdTipoIdentificacion
        AND crdNumeroIdentificacion = cfdIdentificacion
where cfcCargueArchivoCruceFovis = @idCargue
  AND pofEstadoHogar in ('POSTULADO','HABIL','HABIL_SEGUNDO_ANIO')
  AND crdid IS NULL


    insert into #temp_cruces (cruCargueArchivoCruceFovis,cruNumeroPostulacion,cruPersona,cruEstadoCruce,cruFechaRegistro,
                              crdCausalCruce,crdNitEntidad,crdNombreEntidad,crdNumeroIdentificacion,crdApellidosNombres,crdCedulaCatastral,crdDireccionInmueble,
                              crdMatriculaInmobiliaria,crdDepartamento,crdMunicipio,crdFechaActualizacionMinisterio,crdFechaCorteEntidad, crdTipodocumento, crdTipo)

select  cfdCargueArchivoCruceFovis
     ,solNumeroRadicacion
     ,perId
     ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END estadoCruce
     ,GETDATE() fechaRegistro
     ,'REGISTRO_PROPIEDAD' causalCruce
     ,cfdNitEntidad nitEntidad
     ,cfdNombreEntidad nombreEntidad
     ,cfdIdentificacion numeroIdentificacion
     ,cfdApellidosNombres apellidosnombres
     ,cfdCedulaCatastral cedulacatastral
     ,cfdDireccion direccion
     ,cfdMatriculaInmobiliaria matricula
     ,cfdDepartamento departamento
     ,cfdMunicipio municipio
     ,cffFechaActualizacion
     ,cffFechaCorte
     ,cfdTipoIdentificacion tipoDocumento
     , 'CATASTROS' tipo
from
    CargueArchivoCruceFovisCedula
        inner join CargueArchivoCruceFovisCatastros
                   on cfcCargueArchivoCruceFovis = cfdCargueArchivoCruceFovis
                       and cfcNroCedula = cfdIdentificacion
        INNER JOIN CargueArchivoCruceFovisFechasCorte ON cffCargueArchivoCruceFovis = cfdCargueArchivoCruceFovis
        AND cffNitEntidad = cfdNitEntidad and cffTipoInformacion = 'PROPIETARIOS'

        INNER JOIN Persona ON perNumeroIdentificacion = cfdIdentificacion
        INNER JOIN IntegranteHogar ON inhPersona = perId
        INNER JOIN PostulacionFOVIS ON pofId = inhPostulacionFovis

        INNER JOIN SolicitudPostulacion ON pofId = spoPostulacionFOVIS
        INNER JOIN Solicitud ON spoSolicitudGlobal = solId
        LEFT JOIN CruceDetalle on crdTipo = 'CATASTROS'
        AND crdTipodocumento = cfdTipoIdentificacion
        AND crdNumeroIdentificacion = cfdIdentificacion
where cfcCargueArchivoCruceFovis = @idCargue
  AND pofEstadoHogar in ('POSTULADO','HABIL','HABIL_SEGUNDO_ANIO')
  AND crdid IS  NULL


    insert into #temp_cruces (cruCargueArchivoCruceFovis,cruNumeroPostulacion,cruPersona,cruEstadoCruce,cruFechaRegistro,
                              crdCausalCruce,crdNitEntidad,crdNombreEntidad,crdNumeroIdentificacion,crdApellidosNombres,crdCedulaCatastral,crdDireccionInmueble,
                              crdMatriculaInmobiliaria,crdDepartamento,crdMunicipio,crdFechaActualizacionMinisterio,crdFechaCorteEntidad, crdTipodocumento, crdTipo)

select  cfdCargueArchivoCruceFovis
     ,solNumeroRadicacion
     ,perId
     ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END estadoCruce
     ,GETDATE() fechaRegistro
     ,'REGISTRO_PROPIEDAD' causalCruce
     ,cfdNitEntidad nitEntidad
     ,cfdNombreEntidad nombreEntidad
     ,cfdIdentificacion numeroIdentificacion
     ,cfdApellidosNombres apellidosnombres
     ,cfdCedulaCatastral cedulacatastral
     ,cfdDireccion direccion
     ,cfdMatriculaInmobiliaria matricula
     ,cfdDepartamento departamento
     ,cfdMunicipio municipio
     ,cffFechaActualizacion
     ,cffFechaCorte
     ,cfdTipoIdentificacion tipoDocumento
     , 'CATASTROS' tipo
from
    CargueArchivoCruceFovisCedula
        inner join CargueArchivoCruceFovisCatastros
                   on cfcCargueArchivoCruceFovis = cfdCargueArchivoCruceFovis
                       and cfcNroCedula = cfdIdentificacion
        INNER JOIN CargueArchivoCruceFovisFechasCorte ON cffCargueArchivoCruceFovis = cfdCargueArchivoCruceFovis
        AND cffNitEntidad = cfdNitEntidad and cffTipoInformacion = 'PROPIETARIOS'
        INNER JOIN Persona ON perNumeroIdentificacion = cfdIdentificacion
        INNER JOIN Afiliado ON afiPersona = perId
        INNER JOIN JefeHogar ON jehAfiliado = afiId
        INNER JOIN PostulacionFOVIS ON pofJefeHogar = jehId
        INNER JOIN SolicitudPostulacion ON pofId = spoPostulacionFOVIS
        INNER JOIN Solicitud ON spoSolicitudGlobal = solId
        LEFT JOIN CruceDetalle on crdTipo = 'CATASTROS'
        AND crdTipodocumento = cfdTipoIdentificacion
        AND crdNumeroIdentificacion = cfdIdentificacion
where cfcCargueArchivoCruceFovis = @idCargue
  AND pofEstadoHogar in ('POSTULADO','HABIL','HABIL_SEGUNDO_ANIO')
  AND crdid IS NOT NULL


    insert into #temp_cruces (cruCargueArchivoCruceFovis,cruNumeroPostulacion,cruPersona,cruEstadoCruce,cruFechaRegistro,
                              crdCausalCruce,crdNitEntidad,crdNombreEntidad,crdNumeroIdentificacion,crdApellidosNombres,crdCedulaCatastral,crdDireccionInmueble,
                              crdMatriculaInmobiliaria,crdDepartamento,crdMunicipio,crdFechaActualizacionMinisterio,crdFechaCorteEntidad, crdTipodocumento, crdTipo)

select  cfdCargueArchivoCruceFovis
     ,solNumeroRadicacion
     ,perId
     ,CASE WHEN crdid IS NULL THEN 'NUEVO' ELSE 'PREVIAMENTE_REPORTADO' END estadoCruce
     ,GETDATE() fechaRegistro
     ,'REGISTRO_PROPIEDAD' causalCruce
     ,cfdNitEntidad nitEntidad
     ,cfdNombreEntidad nombreEntidad
     ,cfdIdentificacion numeroIdentificacion
     ,cfdApellidosNombres apellidosnombres
     ,cfdCedulaCatastral cedulacatastral
     ,cfdDireccion direccion
     ,cfdMatriculaInmobiliaria matricula
     ,cfdDepartamento departamento
     ,cfdMunicipio municipio
     ,cffFechaActualizacion
     ,cffFechaCorte
     ,cfdTipoIdentificacion tipoDocumento
     , 'CATASTROS' tipo
from
    CargueArchivoCruceFovisCedula
        inner join CargueArchivoCruceFovisCatastros
                   on cfcCargueArchivoCruceFovis = cfdCargueArchivoCruceFovis
                       and cfcNroCedula = cfdIdentificacion
        INNER JOIN CargueArchivoCruceFovisFechasCorte ON cffCargueArchivoCruceFovis = cfdCargueArchivoCruceFovis
        AND cffNitEntidad = cfdNitEntidad and cffTipoInformacion = 'PROPIETARIOS'

        INNER JOIN Persona ON perNumeroIdentificacion = cfdIdentificacion
        INNER JOIN IntegranteHogar ON inhPersona = perId
        INNER JOIN PostulacionFOVIS ON pofId = inhPostulacionFovis

        INNER JOIN SolicitudPostulacion ON pofId = spoPostulacionFOVIS
        INNER JOIN Solicitud ON spoSolicitudGlobal = solId
        LEFT JOIN CruceDetalle on crdTipo = 'CATASTROS'
        AND crdTipodocumento = cfdTipoIdentificacion
        AND crdNumeroIdentificacion = cfdIdentificacion
where cfcCargueArchivoCruceFovis = @idCargue
  AND pofEstadoHogar in ('POSTULADO','HABIL','HABIL_SEGUNDO_ANIO')
  AND crdid IS NOT NULL

/***************Fin Recupera e inserta cruces catastros******************/

/***************Recupera y retorna resultados de la tabla temporal******************/

--DECLARE @JsonV as NVARCHAR(MAX);
--SELECT @JsonV = CONVERT(NVARCHAR(MAX),(
select [cruCargueArchivoCruceFovis]      [cruce.idCargueArchivoCruceFovis]
        ,	[cruNumeroPostulacion] 				  [cruce.numeroPostulacion]
        ,	[cruPersona] 						  [cruce.persona]
        ,	[cruEstadoCruce] 					  [cruce.estadoCruce]
        ,	[cruFechaRegistro] 					  [cruce.fechaRegistro]
        ,	[crdCausalCruce] 					  [causalCruce]
        ,	[crdNitEntidad] 					  [nitEntidad]
        ,	[crdNombreEntidad] 					  [nombreEntidad]
        ,	[crdNumeroIdentificacion]			  [numeroIdentificacion]
        ,	[crdApellidos] 						  [apellidos]
        ,	[crdNombres] 						  [nombres]
        ,	[crdCedulaCatastral] 				  [cedulaCatastral]
        ,	[crdDireccionInmueble] 				  [direccionInmueble]
        ,	[crdMatriculaInmobiliaria] 			  [matriculaInmobiliaria]
        ,	[crdDepartamento] 					  [departamento]
        ,	[crdMunicipio]  					  [municipio]
        ,	[crdFechaActualizacionMinisterio] 	  [fechaActualizacionMinisterio]
        ,	[crdFechaCorteEntidad] 				  [fechaCorteEntidad]
        ,	[crdApellidosNombres] 				  [apellidosNombres]
        ,	[crdTipodocumento]  				  [tipoDocumento]
        ,	[crdTipo] 							  [tipo]
        , perTipoIdentificacion [persona.tipoIdentificacion]
        , perNumeroIdentificacion [persona.numeroIdentificacion]
        , perPrimerNombre [persona.primerNombre]
        , perSegundoNombre [persona.segundoNombre]
        , perPrimerApellido [persona.primerApellido]
        , perSegundoApellido [persona.segundoApellido]
        , perRazonSocial [persona.nombreCompleto]
        , perId [persona.idPersona]
        , perRazonSocial [persona.razonSocial]
        , ubiAutorizacionEnvioEmail [persona.autorizacionEnvioEmail]
        , perCreadoPorPila [persona.creadoPorPila]
        , ubiId [persona.ubicacionDTO.idUbicacion]
        , ubiMunicipio [persona.ubicacionDTO.idMunicipio]
        , ubiDireccionFisica [persona.ubicacionDTO.direccion]
        , ubiIndicativoTelFijo [persona.ubicacionDTO.indicativoTelefonoFijo]
        , ubiTelefonoFijo [persona.ubicacionDTO.telefonoFijo]
        , ubiTelefonoCelular [persona.ubicacionDTO.telefonoCelular]
        , ubiEmail [persona.ubicacionDTO.correoElectronico]
        , ubiAutorizacionEnvioEmail [persona.ubicacionDTO.autorizacionEnvioEmail]
from #temp_cruces
    inner join Persona
on perId = cruPersona
    inner join Ubicacion on
    perUbicacionPrincipal = ubiId

    return