/****** Object:  StoredProcedure [dbo].[CreateFovisJsonInformacionPostulacion]    Script Date: 22/12/2023 11:10:45 ******/
CREATE OR ALTER   PROCEDURE [dbo].[CreateFovisJsonInformacionPostulacion] @pofId nvarchar(30), @json NVARCHAR(max) OUTPUT AS
BEGIN
    DECLARE @postulacion NVARCHAR(max);
    DECLARE @Solicitud NVARCHAR(max);
    DECLARE @jefeHogar NVARCHAR(max);
    DECLARE @proyectoSolucionVivienda NVARCHAR(max);
    DECLARE @proyectoSolucionViviendaMedioPagoConsignacion NVARCHAR(max);
    DECLARE @proyectoSolucionViviendaMedioPagoEfectivo NVARCHAR(max);
    DECLARE @ahorroProgramado NVARCHAR(max);
    DECLARE @ParametrizacionModalidades NVARCHAR(max);
    DECLARE @FormaPagoModalidad NVARCHAR(max);
	DECLARE @Integrantes NVARCHAR(max);
/*-------------------------------------------------------------------------SOLICITUD---------------------------------------------------------------*/

    SET @Solicitud = (SELECT DISTINCT solicitudPostulacion.spoId              as idSolicitudPostulacion,
                                      solicitudPostulacion.spoSolicitudGlobal as idSolicitud,
                                      solicitudGlobal.solNumeroRadicacion     as numeroRadicacion,
                                      solicitudGlobal.solInstanciaProceso     as idInstanciaProceso,
                                      solicitudPostulacion.spoEstadoSolicitud as estadoSolicitud,
                                      solicitudGlobal.solCanalRecepcion       as canalRecepcion,
                                      solicitudGlobal.solTipoTransaccion      as tipoTransaccionEnum,
                                      solicitudGlobal.solMetodoEnvio          as metodoEnvio,
                                      solicitudGlobal.solObservacion          as observaciones,
                                      DATEDIFF_BIG(MS, '1970-01-01 00:00:00', solicitudGlobal.solFechaRadicacion)  as fechaRadicacion
                      FROM SolicitudPostulacion solicitudPostulacion
                               inner join
                           Solicitud solicitudGlobal
                           on solicitudPostulacion.spoSolicitudGlobal = solicitudGlobal.solId
                               inner join
                           PostulacionFOVIS postulacion
                           on solicitudPostulacion.spoPostulacionFOVIS = postulacion.pofId

                      WHERE (postulacion.pofId = @pofId)
                      FOR JSON AUTO)

    SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].numeroRadicacion',
                                 JSON_VALUE(@Solicitud, '$[0].solicitudGlobal[0].numeroRadicacion'));
    SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].idInstanciaProceso',
                                 JSON_VALUE(@Solicitud, '$[0].solicitudGlobal[0].idInstanciaProceso'));
    SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].canalRecepcion',
                                 JSON_VALUE(@Solicitud, '$[0].solicitudGlobal[0].canalRecepcion'));
    SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].tipoTransaccionEnum',
                                 JSON_VALUE(@Solicitud, '$[0].solicitudGlobal[0].tipoTransaccionEnum'));
    SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].metodoEnvio',
                                 JSON_VALUE(@Solicitud, '$[0].solicitudGlobal[0].metodoEnvio'));
    SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].observaciones',
                                 JSON_VALUE(@Solicitud, '$[0].solicitudGlobal[0].observaciones'));
    SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].solFechaRadicacion',
                                 JSON_VALUE(@Solicitud, '$[0].solicitudGlobal[0].solFechaRadicacion'));
    SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].solicitudGlobal', null);


/*-------------------------------------------------------------------------POSTULACION---------------------------------------------------------------*/

    SET @postulacion = (SELECT DISTINCT pof.pofId                                   as idPostulacion,
                                        pof.pofCicloAsignacion                      as idCicloAsignacion,
                                        pof.pofJefeHogar                            as idJefeHogar,
                                        pof.pofModalidad                            as idModalidad,
                                        pof.pofCondicionHogar                       as condicionHogar,
                                        pof.pofEstadoHogar                          as estadoHogar,
                                        pof.pofCondicionHogar                       as condicionHogar,
                                        pof.pofHogarPerdioSubsidioNoPago            as hogarPerdioSubsidioNoPago,
                                        pof.pofCantidadFolios                       as cantidadFolios,
										ISNULL(pof.pofValorSFVSolicitado,0)			as valorSFVSolicitado,
                                        pof.pofResultadoAsignacion                  as resultadoAsignacion,
                                        pof.pofValorAsignadoSFV                     as valorAsignadoSFV,
                                        pof.pofPuntaje                              as puntaje,
                                        DATEDIFF_BIG(MS, '1970-01-01 00:00:00', pof.pofFechaCalificacion)                    as fechaCalificacion,
                                        pof.pofPrioridadAsignacion                  as prioridadAsignacion,
                                        pof.pofValorCalculadoSFV                    as valorCalculadoSFV,
                                        pof.pofValorProyectoVivienda                as valorProyectoVivienda,
                                        pof.pofMotivoDesistimiento                  as motivoDesistimientoPostulacion,
                                        pof.pofMotivoHabilitacion                   as motivoHabilitacion,
                                        pof.pofRestituidoConSancion                 as restituidoConSancion,
                                        pof.pofTiempoSancion                        as tiempoSancion,
                                        pof.pofMotivoRestitucion                    as motivoRestitucion,
                                        pof.pofMotivoEnajenacion                    as motivoEnajenacion,
                                        pof.pofValorAjusteIPCSFV                    as valorAjusteIPCSFV,
                                        pof.pofMotivoRechazo                        as motivoRechazo,
                                        pof.pofValorAvaluoCatastral                 as avaluoCatastralVivienda,
                                        pof.pofInfoParametrizacion                  as informacionParametrizacion,
                                        pof.pofValorSFVAjustado                     as valorSFVAjustado,
                                        pof.pofMatriculaInmobiliariaInmueble        as matriculaInmobiliariaInmueble,
                                        DATEDIFF_BIG(MS, '1970-01-01 00:00:00', pof.pofFechaRegistroEscritura)               as fechaRegistroEscritura,
                                        pof.pofLoteUrbanizado                       as loteUrbanizado,
                                        pof.pofPoseedorOcupanteVivienda             as poseedorOcupanteVivienda,
                                        pof.pofNumeroEscritura                      as numeroEscritura,
										case POF.pofUbicacionIgualProyecto
										when 'true' then 1 else 0 end				as ubicacionViviendaMismaProyecto,
                                        DATEDIFF_BIG(MS, '1970-01-01 00:00:00', pof.pofFechaEscritura)                       as fechaEscritura,
                                        cicloAsignacion.ciaId                       as idCicloAsignacion,
                                        cicloAsignacion.ciaNombre                   as nombre,
                                        DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cicloAsignacion.ciaFechaInicio)              as fechaInicio,
                                        DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cicloAsignacion.ciaFechaFin)                 as fechaFin,
                                        cicloAsignacion.ciaCicloPredecesor          as cicloPredecesor,
                                        cicloAsignacion.ciaEstadoCicloAsignacion    as estadoCicloAsignacion,
                                        cicloAsignacion.ciaCicloActivo              as cicloActivo,
                                        cicloAsignacion.ciaValorDisponible          as valorDisponible,
                                        pof.pofId                                   as jefeHogar,
                                        oferente.ofeEmpresa                         as empresa,
                                        oferente.ofePersona                         as persona,
                                        oferente.ofeBanco                           as banco,
                                        oferente.ofeTipoCuenta                      as tipoCuenta,
                                        oferente.ofeNumeroCuenta                    as numeroCuenta,
                                        oferente.ofeTipoIdentificacionTitular       as tipoIdentificacionTitular,
                                        oferente.ofeNumeroIdentificacionTitular     as numeroIdentificacionTitular,
                                        oferente.ofeNombreTitularCuenta             as nombreTitularCuenta,
                                        oferente.ofeDigitoVerificacionTitular       as digitoVerificacionTitular,
                                        oferente.ofeEstado                          as estado,
                                        oferente.ofeCuentaBancaria                  as cuentaBancaria,
                                        ubicacionVivienda.ubiId                     as idUbicacion,
                                        ubicacionVivienda.ubiDireccionFisica        as direccionFisica,
                                        ubicacionVivienda.ubiCodigoPostal           as codigoPostal,
                                        ubicacionVivienda.ubiTelefonoFijo           as telefonoFijo,
                                        ubicacionVivienda.ubiIndicativoTelFijo      as indicativoTelFijo,
                                        ubicacionVivienda.ubiTelefonoFijo           as telefonoCelular,
                                        ubicacionVivienda.ubiEmail                  as email,
                                        ubicacionVivienda.ubiAutorizacionEnvioEmail as autorizacionEnvioEmail,
                                        ubicacionVivienda.ubiMunicipio              as idMunicipio,
                                        ubicacionVivienda.ubiDescripcionIndicacion  as descripcionIndicacion

                        FROM PostulacionFOVIS pof
                                 inner join
                             ProyectoSolucionVivienda proyectoSolucionVivienda
                             on pof.pofProyectoSolucionVivienda = ProyectoSolucionVivienda.psvId
                                 inner join
                             Oferente oferente
                             on proyectoSolucionVivienda.psvOferente = oferente.ofeId
                                 inner join
                             CicloAsignacion cicloAsignacion
                             on pof.pofCicloAsignacion = cicloAsignacion.ciaId
                                 inner join
                             Ubicacion ubicacionVivienda
                             on pof.pofUbicacionVivienda = ubicacionVivienda.ubiId

                        WHERE (pof.pofId = @pofId)
                        FOR JSON AUTO, ROOT('postulacion'))


/*-------------------------------------------------------------------------CICLO ASIGNACION---------------------------------------------------------------*/

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].cicloAsignacion',
                                   JSON_QUERY(@postulacion, '$.postulacion[0].cicloAsignacion[0]'));

/*------------------------------------------------------------------------- OFERENTE ---------------------------------------------------------------*/
    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].oferente',
                                   JSON_QUERY(@postulacion, '$.postulacion[0].cicloAsignacion.oferente[0]'));

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].cicloAsignacion.oferente', null);

/*-------------------------------------------------------------------------UBICACION VIVIENDA---------------------------------------------------------------*/

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].ubicacionVivienda',
                                   JSON_QUERY(@postulacion, '$.postulacion[0].oferente.ubicacionVivienda[0]'));

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].oferente.ubicacionVivienda', null);

/*-------------------------------------------------------------------------AHORRO PROGRAMADO---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT ahorroProgramado.ahpId                  as idAhorroPrevio,
                                             ahorroProgramado.ahpNombreAhorro        as nombreAhorro,
                                             ahorroProgramado.ahpEntidad             as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', ahorroProgramado.ahpFechaInicial)        as fechaInicial,
                                             ahorroProgramado.ahpValor               as valor,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', ahorroProgramado.ahpFechaInmovilizacion) as fechaInmovilizacion,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', ahorroProgramado.ahpFechaAdquisicion)    as fechaAdquisicion,
                                             ahorroProgramado.ahpPostulacionFOVIS    as idPostulacion,
                                             ahorroProgramado.ahpAhorroMovilizado    as ahorroMovilizado
                             FROM PostulacionFOVIS pof
                                      inner join
                                  AhorroPrevio ahorroProgramado
                                  on pof.pofId = ahorroProgramado.ahpPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND ahorroProgramado.ahpNombreAhorro = 'AHORRO_PROGRAMADO')
                             FOR JSON AUTO, ROOT('ahorroProgramado'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].ahorroProgramado',
                                   JSON_QUERY(@ahorroProgramado, '$.ahorroProgramado[0]'));
/*-------------------------------------------------------------------------APORTES PERIODICOS---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT ahorroProgramadoContractual.ahpId                  as idAhorroPrevio,
                                             ahorroProgramadoContractual.ahpNombreAhorro        as nombreAhorro,
                                             ahorroProgramadoContractual.ahpEntidad             as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', ahorroProgramadoContractual.ahpFechaInicial)        as fechaInicial,
                                             ahorroProgramadoContractual.ahpValor               as valor,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', ahorroProgramadoContractual.ahpFechaInmovilizacion) as fechaInmovilizacion,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', ahorroProgramadoContractual.ahpFechaAdquisicion)    as fechaAdquisicion,
                                             ahorroProgramadoContractual.ahpPostulacionFOVIS    as idPostulacion,
                                             ahorroProgramadoContractual.ahpAhorroMovilizado    as ahorroMovilizado
                             FROM PostulacionFOVIS pof
                                      inner join
                                  AhorroPrevio ahorroProgramadoContractual
                                  on pof.pofId = ahorroProgramadoContractual.ahpPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND ahorroProgramadoContractual.ahpNombreAhorro =
                                                           'AHORRO_PROGRAMADO_CONTRACTUAL_EVALUACION_CREDITICIA_FAVORABLE_FNA')
                             FOR JSON AUTO, ROOT('ahorroProgramadoContractual'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].ahorroProgramadoContractual',
                                   JSON_QUERY(@ahorroProgramado, '$.ahorroProgramadoContractual[0]'));

/*-------------------------------------------------------------------------APORTES PERIODICOS---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT aportesPeriodicos.ahpId                  as idAhorroPrevio,
                                             aportesPeriodicos.ahpNombreAhorro        as nombreAhorro,
                                             aportesPeriodicos.ahpEntidad             as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', aportesPeriodicos.ahpFechaInicial)        as fechaInicial,
                                             aportesPeriodicos.ahpValor               as valor,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', aportesPeriodicos.ahpFechaInmovilizacion) as fechaInmovilizacion,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', aportesPeriodicos.ahpFechaAdquisicion)    as fechaAdquisicion,
                                             aportesPeriodicos.ahpPostulacionFOVIS    as idPostulacion,
                                             aportesPeriodicos.ahpAhorroMovilizado    as ahorroMovilizado
                             FROM PostulacionFOVIS pof
                                      inner join
                                  AhorroPrevio aportesPeriodicos
                                  on pof.pofId = aportesPeriodicos.ahpPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND aportesPeriodicos.ahpNombreAhorro = 'APORTES_PERIODICOS')
                             FOR JSON AUTO, ROOT('aportesPeriodicos'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].aportesPeriodicos',
                                   JSON_QUERY(@ahorroProgramado, '$.aportesPeriodicos[0]'));

/*-------------------------------------------------------------------------CESANTIAS INMOVILIZADAS---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT cesantiasInmovilizadas.ahpId                  as idAhorroPrevio,
                                             cesantiasInmovilizadas.ahpNombreAhorro        as nombreAhorro,
                                             cesantiasInmovilizadas.ahpEntidad             as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cesantiasInmovilizadas.ahpFechaInicial)        as fechaInicial,
                                             cesantiasInmovilizadas.ahpValor               as valor,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cesantiasInmovilizadas.ahpFechaInmovilizacion) as fechaInmovilizacion,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cesantiasInmovilizadas.ahpFechaAdquisicion)    as fechaAdquisicion,
                                             cesantiasInmovilizadas.ahpPostulacionFOVIS    as idPostulacion,
                                             cesantiasInmovilizadas.ahpAhorroMovilizado    as ahorroMovilizado
                             FROM PostulacionFOVIS pof
                                      inner join
                                  AhorroPrevio cesantiasInmovilizadas
                                  on pof.pofId = cesantiasInmovilizadas.ahpPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND
                                    cesantiasInmovilizadas.ahpNombreAhorro = 'CESANTIAS_INMOVILIZADAS')
                             FOR JSON AUTO, ROOT('cesantiasInmovilizadas'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].cesantiasInmovilizadas',
                                   JSON_QUERY(@ahorroProgramado, '$.cesantiasInmovilizadas[0]'));

/*-------------------------------------------------------------------------CUOTA INICIAL---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT cuotaInicial.ahpId                  as idAhorroPrevio,
                                             cuotaInicial.ahpNombreAhorro        as nombreAhorro,
                                             cuotaInicial.ahpEntidad             as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cuotaInicial.ahpFechaInicial)        as fechaInicial,
                                             cuotaInicial.ahpValor               as valor,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cuotaInicial.ahpFechaInmovilizacion) as fechaInmovilizacion,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cuotaInicial.ahpFechaAdquisicion)    as fechaAdquisicion,
                                             cuotaInicial.ahpPostulacionFOVIS    as idPostulacion,
                                             cuotaInicial.ahpAhorroMovilizado    as ahorroMovilizado
                             FROM PostulacionFOVIS pof
                                      inner join
                                  AhorroPrevio cuotaInicial
                                  on pof.pofId = cuotaInicial.ahpPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND cuotaInicial.ahpNombreAhorro = 'CUOTA_INICIAL')
                             FOR JSON AUTO, ROOT('cuotaInicial'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].cuotaInicial',
                                   JSON_QUERY(@ahorroProgramado, '$.cuotaInicial[0]'));

/*-------------------------------------------------------------------------CUOTA PAGADAS---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT cuotasPagadas.ahpId                  as idAhorroPrevio,
                                             cuotasPagadas.ahpNombreAhorro        as nombreAhorro,
                                             cuotasPagadas.ahpEntidad             as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cuotasPagadas.ahpFechaInicial)        as fechaInicial,
                                             cuotasPagadas.ahpValor               as valor,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cuotasPagadas.ahpFechaInmovilizacion) as fechaInmovilizacion,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cuotasPagadas.ahpFechaAdquisicion)    as fechaAdquisicion,
                                             cuotasPagadas.ahpPostulacionFOVIS    as idPostulacion,
                                             cuotasPagadas.ahpAhorroMovilizado    as ahorroMovilizado
                             FROM PostulacionFOVIS pof
                                      inner join
                                  AhorroPrevio cuotasPagadas
                                  on pof.pofId = cuotasPagadas.ahpPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND cuotasPagadas.ahpNombreAhorro = 'CUOTAS_PAGADAS')
                             FOR JSON AUTO, ROOT('cuotasPagadas'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].cuotasPagadas',
                                   JSON_QUERY(@ahorroProgramado, '$.cuotasPagadas[0]'));

/*-------------------------------------------------------------------------VALOR LOTE O TERRENO PROPIO---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT cuotasPagadas.ahpId                  as idAhorroPrevio,
                                             cuotasPagadas.ahpNombreAhorro        as nombreAhorro,
                                             cuotasPagadas.ahpEntidad             as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cuotasPagadas.ahpFechaInicial)        as fechaInicial,
                                             cuotasPagadas.ahpValor               as valor,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cuotasPagadas.ahpFechaInmovilizacion) as fechaInmovilizacion,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cuotasPagadas.ahpFechaAdquisicion)    as fechaAdquisicion,
                                             cuotasPagadas.ahpPostulacionFOVIS    as idPostulacion,
                                             cuotasPagadas.ahpAhorroMovilizado    as ahorroMovilizado
                             FROM PostulacionFOVIS pof
                                      inner join
                                  AhorroPrevio cuotasPagadas
                                  on pof.pofId = cuotasPagadas.ahpPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND
                                    cuotasPagadas.ahpNombreAhorro = 'VALOR_LOTE_O_TERRENO_PROPIO')
                             FOR JSON AUTO, ROOT('valorLoteTerreno'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].valorLoteTerreno',
                                   JSON_QUERY(@ahorroProgramado, '$.valorLoteTerreno[0]'));

/*-------------------------------------------------------------------------VALOR LOTE OPV---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT valorLoteOPV.ahpId                  as idAhorroPrevio,
                                             valorLoteOPV.ahpNombreAhorro        as nombreAhorro,
                                             valorLoteOPV.ahpEntidad             as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', valorLoteOPV.ahpFechaInicial)        as fechaInicial,
                                             valorLoteOPV.ahpValor               as valor,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', valorLoteOPV.ahpFechaInmovilizacion) as fechaInmovilizacion,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', valorLoteOPV.ahpFechaAdquisicion)    as fechaAdquisicion,
                                             valorLoteOPV.ahpPostulacionFOVIS    as idPostulacion,
                                             valorLoteOPV.ahpAhorroMovilizado    as ahorroMovilizado
                             FROM PostulacionFOVIS pof
                                      inner join
                                  AhorroPrevio valorLoteOPV
                                  on pof.pofId = valorLoteOPV.ahpPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND valorLoteOPV.ahpNombreAhorro = 'VALOR_LOTE_OPV')
                             FOR JSON AUTO, ROOT('valorLoteOPV'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].valorLoteOPV',
                                   JSON_QUERY(@ahorroProgramado, '$.valorLoteOPV[0]'));

/*-------------------------------------------------------------------------VALOR LOTE POR SUBSIDIO MUNICIPAL O DEPARTAMENTAL---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT valorLoteSubsidioMunicipal.ahpId                  as idAhorroPrevio,
                                             valorLoteSubsidioMunicipal.ahpNombreAhorro        as nombreAhorro,
                                             valorLoteSubsidioMunicipal.ahpEntidad             as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', valorLoteSubsidioMunicipal.ahpFechaInicial)        as fechaInicial,
                                             valorLoteSubsidioMunicipal.ahpValor               as valor,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', valorLoteSubsidioMunicipal.ahpFechaInmovilizacion) as fechaInmovilizacion,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', valorLoteSubsidioMunicipal.ahpFechaAdquisicion)    as fechaAdquisicion,
                                             valorLoteSubsidioMunicipal.ahpPostulacionFOVIS    as idPostulacion,
                                             valorLoteSubsidioMunicipal.ahpAhorroMovilizado    as ahorroMovilizado
                             FROM PostulacionFOVIS pof
                                      inner join
                                  AhorroPrevio valorLoteSubsidioMunicipal
                                  on pof.pofId = valorLoteSubsidioMunicipal.ahpPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND valorLoteSubsidioMunicipal.ahpNombreAhorro =
                                                           'VALOR_LOTE_POR_SUBSIDIO_MUNICIPAL_O_DEPARTAMENTAL')
                             FOR JSON AUTO, ROOT('valorLoteSubsidioMunicipal'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].valorLoteSubsidioMunicipal',
                                   JSON_QUERY(@ahorroProgramado, '$.valorLoteSubsidioMunicipal[0]'));

/*-------------------------------------------------------------------------AHORRO OTRAS MODALIDADES---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT ahorroOtrasModalidades.recId               as idRecurso,
                                             ahorroOtrasModalidades.recNombre           as nombre,
                                             ahorroOtrasModalidades.recEntidad          as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', ahorroOtrasModalidades.recFecha)            as fecha,
                                             ahorroOtrasModalidades.recOtroRecurso      as otroRecurso,
                                             ahorroOtrasModalidades.recValor            as valor,
                                             ahorroOtrasModalidades.recPostulacionFOVIS as postulacion
                             FROM PostulacionFOVIS pof
                                      inner join
                                  RecursoComplementario ahorroOtrasModalidades
                                  on pof.pofId = ahorroOtrasModalidades.recPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND
                                    ahorroOtrasModalidades.recNombre = 'AHORRO_OTRAS_MODALIDADES')
                             FOR JSON AUTO, ROOT('ahorroOtrasModalidades'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].ahorroOtrasModalidades',
                                   JSON_QUERY(@ahorroProgramado, '$.ahorroOtrasModalidades[0]'));

/*-------------------------------------------------------------------------APORTES ENTE TERRITORIAL---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT aportesEnteTerritorial.recId               as idRecurso,
                                             aportesEnteTerritorial.recNombre           as nombre,
                                             aportesEnteTerritorial.recEntidad          as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', aportesEnteTerritorial.recFecha)            as fecha,
                                             aportesEnteTerritorial.recOtroRecurso      as otroRecurso,
                                             aportesEnteTerritorial.recValor            as valor,
                                             aportesEnteTerritorial.recPostulacionFOVIS as postulacion
                             FROM PostulacionFOVIS pof
                                      inner join
                                  RecursoComplementario aportesEnteTerritorial
                                  on pof.pofId = aportesEnteTerritorial.recPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND
                                    aportesEnteTerritorial.recNombre = 'APORTES_ENTE_TERRITORIAL')
                             FOR JSON AUTO, ROOT('aportesEnteTerritorial'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].aportesEnteTerritorial',
                                   JSON_QUERY(@ahorroProgramado, '$.aportesEnteTerritorial[0]'));

/*-------------------------------------------------------------------------APORTES_SOLIDARIOS---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT aportesSolidarios.recId               as idRecurso,
                                             aportesSolidarios.recNombre           as nombre,
                                             aportesSolidarios.recEntidad          as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00',aportesSolidarios.recFecha)            as fecha,
                                             aportesSolidarios.recOtroRecurso      as otroRecurso,
                                             aportesSolidarios.recValor            as valor,
                                             aportesSolidarios.recPostulacionFOVIS as postulacion
                             FROM PostulacionFOVIS pof
                                      inner join
                                  RecursoComplementario aportesSolidarios
                                  on pof.pofId = aportesSolidarios.recPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND aportesSolidarios.recNombre = 'APORTES_SOLIDARIOS')
                             FOR JSON AUTO, ROOT('aportesSolidarios'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].aportesSolidarios',
                                   JSON_QUERY(@ahorroProgramado, '$.aportesSolidarios[0]'));

/*-------------------------------------------------------------------------CESANTIAS_NO_INMOVILIZADAS---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT cesantiasNoInmovilizadas.recId               as idRecurso,
                                             cesantiasNoInmovilizadas.recNombre           as nombre,
                                             cesantiasNoInmovilizadas.recEntidad          as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cesantiasNoInmovilizadas.recFecha)            as fecha,
                                             cesantiasNoInmovilizadas.recOtroRecurso      as otroRecurso,
                                             cesantiasNoInmovilizadas.recValor            as valor,
                                             cesantiasNoInmovilizadas.recPostulacionFOVIS as postulacion
                             FROM PostulacionFOVIS pof
                                      inner join
                                  RecursoComplementario cesantiasNoInmovilizadas
                                  on pof.pofId = cesantiasNoInmovilizadas.recPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND
                                    cesantiasNoInmovilizadas.recNombre = 'CESANTIAS_NO_INMOVILIZADAS')
                             FOR JSON AUTO, ROOT('cesantiasNoInmovilizadas'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].cesantiasNoInmovilizadas',
                                   JSON_QUERY(@ahorroProgramado, '$.cesantiasNoInmovilizadas[0]'));

/*-------------------------------------------------------------------------CREDITO_APROBADO---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT creditoAprobado.recId               as idRecurso,
                                             creditoAprobado.recNombre           as nombre,
                                             creditoAprobado.recEntidad          as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', creditoAprobado.recFecha)            as fecha,
                                             creditoAprobado.recOtroRecurso      as otroRecurso,
                                             creditoAprobado.recValor            as valor,
                                             creditoAprobado.recPostulacionFOVIS as postulacion
                             FROM PostulacionFOVIS pof
                                      inner join
                                  RecursoComplementario creditoAprobado
                                  on pof.pofId = creditoAprobado.recPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND creditoAprobado.recNombre = 'CREDITO_APROBADO')
                             FOR JSON AUTO, ROOT('creditoAprobado'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].creditoAprobado',
                                   JSON_QUERY(@ahorroProgramado, '$.creditoAprobado[0]'));

/*-------------------------------------------------------------------------DONACION_OTRAS_ENTIDADES---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT donacionOtrasEntidades.recId               as idRecurso,
                                             donacionOtrasEntidades.recNombre           as nombre,
                                             donacionOtrasEntidades.recEntidad          as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', donacionOtrasEntidades.recFecha)            as fecha,
                                             donacionOtrasEntidades.recOtroRecurso      as otroRecurso,
                                             donacionOtrasEntidades.recValor            as valor,
                                             donacionOtrasEntidades.recPostulacionFOVIS as postulacion
                             FROM PostulacionFOVIS pof
                                      inner join
                                  RecursoComplementario donacionOtrasEntidades
                                  on pof.pofId = donacionOtrasEntidades.recPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND
                                    donacionOtrasEntidades.recNombre = 'DONACION_OTRAS_ENTIDADES')
                             FOR JSON AUTO, ROOT('donacionOtrasEntidades'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].donacionOtrasEntidades',
                                   JSON_QUERY(@ahorroProgramado, '$.donacionOtrasEntidades[0]'));

/*-------------------------------------------------------------------------EVALUACION_CREDITICIA---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT evaluacionCrediticia.recId               as idRecurso,
                                             evaluacionCrediticia.recNombre           as nombre,
                                             evaluacionCrediticia.recEntidad          as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', evaluacionCrediticia.recFecha)            as fecha,
                                             evaluacionCrediticia.recOtroRecurso      as otroRecurso,
                                             evaluacionCrediticia.recValor            as valor,
                                             evaluacionCrediticia.recPostulacionFOVIS as postulacion
                             FROM PostulacionFOVIS pof
                                      inner join
                                  RecursoComplementario evaluacionCrediticia
                                  on pof.pofId = evaluacionCrediticia.recPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND evaluacionCrediticia.recNombre = 'EVALUACION_CREDITICIA')
                             FOR JSON AUTO, ROOT('evaluacionCrediticia'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].evaluacionCrediticia',
                                   JSON_QUERY(@ahorroProgramado, '$.evaluacionCrediticia[0]'));

/*-------------------------------------------------------------------------OTROS_RECURSOS---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT otrosRecursos.recId               as idRecurso,
                                             otrosRecursos.recNombre           as nombre,
                                             otrosRecursos.recEntidad          as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', otrosRecursos.recFecha)            as fecha,
                                             otrosRecursos.recOtroRecurso      as otroRecurso,
                                             otrosRecursos.recValor            as valor,
                                             otrosRecursos.recPostulacionFOVIS as postulacion
                             FROM PostulacionFOVIS pof
                                      inner join
                                  RecursoComplementario otrosRecursos
                                  on pof.pofId = otrosRecursos.recPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND otrosRecursos.recNombre = 'OTROS_RECURSOS')
                             FOR JSON AUTO, ROOT('otrosRecursos'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].otrosRecursos',
                                   JSON_QUERY(@ahorroProgramado, '$.otrosRecursos[0]'));


/*-------------------------------------------------------------------------VALOR_AVANCE_OBRA---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT valorAvanceObra.recId               as idRecurso,
                                             valorAvanceObra.recNombre           as nombre,
                                             valorAvanceObra.recEntidad          as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', valorAvanceObra.recFecha)            as fecha,
                                             valorAvanceObra.recOtroRecurso      as otroRecurso,
                                             valorAvanceObra.recValor            as valor,
                                             valorAvanceObra.recPostulacionFOVIS as postulacion
                             FROM PostulacionFOVIS pof
                                      inner join
                                  RecursoComplementario valorAvanceObra
                                  on pof.pofId = valorAvanceObra.recPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND valorAvanceObra.recNombre = 'VALOR_AVANCE_OBRA')
                             FOR JSON AUTO, ROOT('valorAvanceObra'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].valorAvanceObra',
                                   JSON_QUERY(@ahorroProgramado, '$.valorAvanceObra[0]'));


/*-------------------------------------------------------------------------JEFE HOGAR---------------------------------------------------------------*/

    SET @jefeHogar = (SELECT DISTINCT persona.perId                                       as idPersona,
                                      persona.perTipoIdentificacion                       as tipoIdentificacion,
                                      persona.perNumeroIdentificacion                     as numeroIdentificacion,
                                      persona.perDigitoVerificacion                       as digitoVerificacion,
                                      persona.perPrimerNombre                             as primerNombre,
                                      persona.perSegundoNombre                            as segundoNombre,
                                      persona.perPrimerApellido                           as primerApellido,
                                      persona.perSegundoApellido                          as segundoApellido,
                                      persona.perRazonSocial                              as razonSocial,
                                      persona.perCreadoPorPila                            as creadoPorPila,
                                      personaDetalle.pedId                                as idPersonaDetalle,
                                      personaDetalle.pedFechaNacimiento                  as fechaNacimiento,
                                      DATEDIFF_BIG(MS, '1970-01-01 00:00:00', personaDetalle.pedFechaExpedicionDocumento)          as fechaExpedicionDocumento,
                                      personaDetalle.pedGenero                            as genero,
                                      personaDetalle.pedOcupacionProfesion                as idOcupacionProfesion,
                                      personaDetalle.pedNivelEducativo                    as nivelEducativo,
                                      personaDetalle.pedGradoAcademico                    as gradoAcademico,
                                      personaDetalle.pedCabezaHogar                       as cabezaHogar,
                                      case personaDetalle.pedHabitaCasaPropia
									  when 'true' then 1 else 0 end						  as habitaCasaPropia,
									  case personaDetalle.pedAutorizaUsoDatosPersonales
									  when 'true' then 1 else 0 end						  as autorizaUsoDatosPersonales,
                                      case personaDetalle.pedResideSectorRural
									  when 'true' then 1 else 0 end						  as resideSectorRural,
                                      personaDetalle.pedEstadoCivil                       as estadoCivil,
                                      personaDetalle.pedFallecido                         as fallecido,
                                      DATEDIFF_BIG(MS, '1970-01-01 00:00:00', personaDetalle.pedFechaFallecido)                    as fechaFallecido,
                                      DATEDIFF_BIG(MS, '1970-01-01 00:00:00', personaDetalle.pedFechaDefuncion)                    as fechaDefuncion,
                                      personaDetalle.pedEstudianteTrabajoDesarrolloHumano as estudianteTrabajoDesarrolloHumano,
                                      personaDetalle.pedPersonaPadre                      as idPersonaPadre,
                                      personaDetalle.pedPersonaMadre                      as idPersonaMadre,
                                      personaDetalle.pedOrientacionSexual                 as orientacionSexual,
                                      personaDetalle.pedFactorVulnerabilidad              as factorVulnerabilidad,
                                      personaDetalle.pedPertenenciaEtnica                 as pertenenciaEtnica,
                                      personaDetalle.pedPaisResidencia                    as idPaisResidencia,
									  personaDetalle.pedResideSectorRural				as resideSectorRural,
									  personaDetalle.pedHabitaCasaPropia				as pedHabitaCasaPropia,
                                      jefeHogar.jehId                                     as idJefeHogar,
                                      jefeHogar.jehAfiliado                               as idAfiliado,
                                      jefeHogar.jehEstadoHogar                            as estadoHogar,
                                      jefeHogar.jehIngresoMensual                         as ingresosMensuales,
									  0              as beneficiarioSubsidio,
                                      ubicacionModeloDTO.ubiId                            as idUbicacion,
                                      ubicacionModeloDTO.ubiDireccionFisica               as direccionFisica,
                                      ubicacionModeloDTO.ubiCodigoPostal                  as codigoPostal,
                                      ubicacionModeloDTO.ubiTelefonoFijo                  as telefonoFijo,
                                      ubicacionModeloDTO.ubiIndicativoTelFijo             as indicativoTelFijo,
                                      ubicacionModeloDTO.ubiTelefonoCelular				  as telefonoCelular,
                                      ubicacionModeloDTO.ubiEmail                         as email,
                                      ubicacionModeloDTO.ubiAutorizacionEnvioEmail        as autorizacionEnvioEmail,
                                      ubicacionModeloDTO.ubiMunicipio                     as idMunicipio,
                                      ubicacionModeloDTO.ubiDescripcionIndicacion         as descripcionIndicacion,
                                      ubicacionModeloDTO.ubiSectorUbicacion               as sectorUbicacion


                      FROM PostulacionFOVIS pof
                               inner join
                           JefeHogar jefeHogar
                           on pof.pofJefeHogar = jefeHogar.jehId
                               INNER JOIN
                           Afiliado afiliado
                           on afiliado.afiId = jefeHogar.jehAfiliado
                               inner join
                           Persona persona
                           on afiliado.afiPersona = persona.perId
                               inner join
                           PersonaDetalle personaDetalle
                           on personaDetalle.pedPersona = persona.perId
                               inner join
                           Ubicacion ubicacionModeloDTO
                           on persona.perUbicacionPrincipal = ubicacionModeloDTO.ubiId

                      WHERE (pof.pofId = @pofId)
                      FOR JSON AUTO, ROOT('jefeHogar'))

    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].idPersonaDetalle',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].idPersonaDetalle'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].fechaNacimiento',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].fechaNacimiento'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].fechaExpedicionDocumento',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].fechaExpedicionDocumento'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].genero',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].genero'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].idOcupacionProfesion',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].idOcupacionProfesion'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].nivelEducativo',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].nivelEducativo'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].gradoAcademico',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].gradoAcademico'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].cabezaHogar',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].cabezaHogar'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].nivelEducativo',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].nivelEducativo'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].habitaCasaPropia',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].habitaCasaPropia'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].autorizaUsoDatosPersonales',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].autorizaUsoDatosPersonales'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].resideSectorRural',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].resideSectorRural'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].estadoCivil',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].estadoCivil'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].fallecido',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].fallecido'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].fechaFallecido',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].fechaFallecido'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].beneficiarioSubsidio',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].beneficiarioSubsidio'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].fechaDefuncion',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].fechaDefuncion'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].estudianteTrabajoDesarrolloHumano',
                                 JSON_VALUE(@jefeHogar,
                                            '$.jefeHogar[0].personaDetalle[0].estudianteTrabajoDesarrolloHumano'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].idPersonaPadre',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].idPersonaPadre'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].idPersonaMadre',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].idPersonaMadre'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].factorVulnerabilidad',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].factorVulnerabilidad'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].pertenenciaEtnica',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].pertenenciaEtnica'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].idPaisResidencia',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].idPaisResidencia'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].idJefeHogar',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].jefeHogar[0].idJefeHogar'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].idAfiliado',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].jefeHogar[0].idAfiliado'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].estadoHogar',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].jefeHogar[0].estadoHogar'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].ingresosMensuales',
                                 JSON_VALUE(@jefeHogar,
                                            '$.jefeHogar[0].personaDetalle[0].jefeHogar[0].ingresosMensuales'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].beneficiarioSubsidio',
                                 JSON_VALUE(@jefeHogar,
                                            '$.jefeHogar[0].personaDetalle[0].jefeHogar[0].beneficiarioSubsidio'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].ubicacionModeloDTO',
                                 JSON_QUERY(@jefeHogar,
                                            '$.jefeHogar[0].personaDetalle[0].jefeHogar[0].ubicacionModeloDTO[0]'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].personaDetalle', null);
    SET @postulacion =
            JSON_MODIFY(@postulacion, '$.postulacion[0].jefeHogar', JSON_QUERY(@jefeHogar, '$.jefeHogar[0]'));

/*-------------------------------------------------------------------------PROYECTO SOLUCION VIVIENDA---------------------------------------------------------------*/
    SET @proyectoSolucionVivienda = (SELECT DISTINCT proyectoSolucionVivienda.psvId                          as idProyectoVivienda,
                                                     proyectoSolucionVivienda.psvNombreProyecto              as nombreProyecto,
                                                     proyectoSolucionVivienda.psvCodigoProyectoElegibilidad  as codigoProyecto,
                                                     proyectoSolucionVivienda.psvNumeroDocumentoElegibilidad as numeroDocumentoElegibilidad,
                                                     proyectoSolucionVivienda.psvNombreEntidadElegibilidad   as nombreEntidadElegibilidad,
                                                     DATEDIFF_BIG(MS, '1970-01-01 00:00:00', proyectoSolucionVivienda.psvFechaElegibilidad)           as fechaElegibilidad,
                                                     proyectoSolucionVivienda.psvNumeroViviendaElegibilidad  as numeroViviendaElegibilidad,
                                                     proyectoSolucionVivienda.psvTipoInmuebleElegibilidad    as tipoInmuebleElegibilidad,
                                                     proyectoSolucionVivienda.psvComentariosElegibilidad     as comentariosElegibilidad,
                                                     proyectoSolucionVivienda.psvObservaciones               as observaciones,
                                                     proyectoSolucionVivienda.psvRegistrado                  as registrado,
                                                     proyectoSolucionVivienda.psvComparteCuentaOferente      as comparteCuentaOferente,
                                                     proyectoSolucionVivienda.psvDisponeCuentaBancaria       as disponeCuentaBancaria,
                                                     oferente.ofeId                                          as idOferente,
                                                     oferente.ofeEmpresa                                     as empresa,
                                                     oferente.ofeBanco                                       as banco,
                                                     oferente.ofeTipoCuenta                                  as tipoCuenta,
                                                     oferente.ofeNumeroCuenta                                as primerNombre,
                                                     persona.perId                                       as idPersona,
                                                     persona.perTipoIdentificacion                       as tipoIdentificacion,
                                                     persona.perNumeroIdentificacion                     as numeroIdentificacion,
                                                     persona.perDigitoVerificacion                       as digitoVerificacion,
                                                     persona.perPrimerNombre                             as primerNombre,
                                                     persona.perSegundoNombre                            as segundoNombre,
                                                     persona.perPrimerApellido                           as primerApellido,
                                                     persona.perSegundoApellido                          as segundoApellido,
                                                     persona.perRazonSocial                              as razonSocial,
                                                     persona.perCreadoPorPila                            as creadoPorPila,
                                                     personaDetalle.pedId                                as idPersonaDetalle,
                                                     DATEDIFF_BIG(MS, '1970-01-01 00:00:00', personaDetalle.pedFechaNacimiento)                   as fechaNacimiento,
                                                     DATEDIFF_BIG(MS, '1970-01-01 00:00:00', personaDetalle.pedFechaExpedicionDocumento)          as fechaExpedicionDocumento,
                                                     personaDetalle.pedGenero                            as genero,
                                                     personaDetalle.pedOcupacionProfesion                as idOcupacionProfesion,
                                                     personaDetalle.pedNivelEducativo                    as nivelEducativo,
                                                     personaDetalle.pedGradoAcademico                    as gradoAcademico,
                                                     personaDetalle.pedCabezaHogar                       as CabezaHogar,
                                                     personaDetalle.pedHabitaCasaPropia                  as habitaCasaPropia,
                                                     personaDetalle.pedAutorizaUsoDatosPersonales        as autorizaUsoDatosPersonales,
                                                     personaDetalle.pedResideSectorRural                 as resideSectorRural,
                                                     personaDetalle.pedEstadoCivil                       as estadoCivil,
                                                     personaDetalle.pedFallecido                         as fallecido,
                                                     DATEDIFF_BIG(MS, '1970-01-01 00:00:00', personaDetalle.pedFechaFallecido)                    as fechaFallecido,
                                                     personaDetalle.pedBeneficiarioSubsidio              as beneficiarioSubsidio,
                                                     DATEDIFF_BIG(MS, '1970-01-01 00:00:00', personaDetalle.pedFechaDefuncion)                    as fechaDefuncion,
                                                     personaDetalle.pedEstudianteTrabajoDesarrolloHumano as estudianteTrabajoDesarrolloHumano,
                                                     personaDetalle.pedPersonaPadre                      as idPersonaPadre,
                                                     personaDetalle.pedPersonaMadre                      as idPersonaMadre,
                                                     personaDetalle.pedOrientacionSexual                 as orientacionSexual,
                                                     personaDetalle.pedFactorVulnerabilidad              as factorVulnerabilidad,
                                                     personaDetalle.pedPertenenciaEtnica                 as pertenenciaEtnica,
                                                     personaDetalle.pedPaisResidencia                    as idPaisResidencia,
                                                     persona.perTipoIdentificacion                           as tipoIdentificacionTitular,
                                                     persona.perNumeroIdentificacion                         as numeroIdentificacionTitular,
                                                     persona.perRazonSocial                                  as nombreTitularCuenta,
                                                     persona.perDigitoVerificacion                           as digitoVerificacionTitular,
                                                     oferente.ofeEstado                                      as estado,
                                                     oferente.ofeCuentaBancaria                              as cuentaBancaria,
                                                     ubicacionProyecto.ubiId                                 as idUbicacion,
                                                     ubicacionProyecto.ubiDireccionFisica                    as direccionFisica,
                                                     ubicacionProyecto.ubiCodigoPostal                       as codigoPostal,
                                                     ubicacionProyecto.ubiTelefonoFijo                       as telefonoFijo,
                                                     ubicacionProyecto.ubiIndicativoTelFijo                  as indicativoTelFijo,
                                                     ubicacionProyecto.ubiTelefonoFijo                       as telefonoCelular,
                                                     ubicacionProyecto.ubiEmail                              as email,
                                                     ubicacionProyecto.ubiAutorizacionEnvioEmail             as autorizacionEnvioEmail,
                                                     ubicacionProyecto.ubiMunicipio                          as idMunicipio,
                                                     ubicacionProyecto.ubiDescripcionIndicacion              as descripcionIndicacion

                                     FROM PostulacionFOVIS pof
                                              inner join
                                          ProyectoSolucionVivienda proyectoSolucionVivienda
                                          on pof.pofProyectoSolucionVivienda = ProyectoSolucionVivienda.psvId
                                              left join
                                          Ubicacion ubicacionProyecto
                                          on proyectoSolucionVivienda.psvUbicacionProyecto = ubicacionProyecto.ubiId
                                              inner join
                                          Oferente oferente
                                          on proyectoSolucionVivienda.psvOferente = oferente.ofeId
                                              inner join
                                          Persona persona
                                          on oferente.ofePersona = persona.perId
                                              left join
                                          PersonaDetalle personaDetalle
                                          on persona.perId = personaDetalle.pedPersona
                                     WHERE (pof.pofId =  @pofId)
                                     FOR JSON AUTO, ROOT('proyectoSolucionVivienda'), include_null_values )

    SET @proyectoSolucionVivienda =
            JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].oferente[0].tipoIdentificacionTitular',
                        JSON_VALUE(@proyectoSolucionVivienda,
                                   '$.proyectoSolucionVivienda[0].oferente[0].persona[0].tipoIdentificacionTitular'));
    SET @proyectoSolucionVivienda =
            JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].oferente[0].numeroIdentificacionTitular',
                        JSON_VALUE(@proyectoSolucionVivienda,
                                   '$.proyectoSolucionVivienda[0].oferente[0].persona[0].numeroIdentificacionTitular'));
    SET @proyectoSolucionVivienda =
            JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].oferente[0].nombreTitularCuenta',
                        JSON_VALUE(@proyectoSolucionVivienda,
                                   '$.proyectoSolucionVivienda[0].oferente[0].persona[0].nombreTitularCuenta'));
    SET @proyectoSolucionVivienda =
            JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].oferente[0].digitoVerificacionTitular',
                        JSON_VALUE(@proyectoSolucionVivienda,
                                   '$.proyectoSolucionVivienda[0].oferente[0].persona[0].digitoVerificacionTitular'));
    SET @proyectoSolucionVivienda =
            JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].ubicacionProyecto',
                        JSON_QUERY(@proyectoSolucionVivienda,
                                   '$.proyectoSolucionVivienda[0].oferente[0].persona[0].ubicacionProyecto[0]'));
/*...........................................................Oferente Detallado------------------------------------------------------*/
    SET @proyectoSolucionVivienda = JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].oferente[0].persona',
                                                JSON_QUERY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].oferente[0].persona[0]'));

    SET @proyectoSolucionVivienda = JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].oferente[0].persona.idPersonaDetalle',
                                                JSON_VALUE(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].oferente[0].persona.personaDetalle[0].idPersonaDetalle'));

    SET @proyectoSolucionVivienda =
            JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].oferente[0].persona.personaDetalle',null);

    SET @Solicitud =JSON_MODIFY(@Solicitud, '$[0].oferente',JSON_QUERY('{"esOferente": null}'));
    SET @Solicitud =JSON_MODIFY(@Solicitud, '$[0].oferente.oferente',JSON_QUERY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].oferente[0]'));

    SET @proyectoSolucionVivienda =
            JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].oferente[0].persona',null);

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].oferente',
                                   JSON_QUERY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].oferente[0]'));

select @postulacion t1

    SET @proyectoSolucionViviendaMedioPagoConsignacion = (SELECT DISTINCT medioPago.mdpId                                  as idMedioDePago,
                                                                          medioPago.mdpTipo                                as tipoMedioDePago,
                                                                          medioConsignacion.mcoBanco                       as idBanco,
                                                                          banco.banNombre                                  as nombreBanco,
                                                                          banco.banCodigo                                  as codigoBanco,
                                                                          medioConsignacion.mcoTipoCuenta                  as tipoCuenta,
                                                                          medioConsignacion.mcoNumeroCuenta                as numeroCuenta,
                                                                          medioConsignacion.mcoTipoIdentificacionTitular   as tipoIdentificacionTitular,
                                                                          medioConsignacion.mcoNumeroIdentificacionTitular as numeroIdentificacionTitular,
                                                                          medioConsignacion.mcoDigitoVerificacionTitular   as digitoVerificacionTitular,
                                                                          medioConsignacion.mcoNombreTitularCuenta         as nombreTitularCuenta


                                                          FROM PostulacionFOVIS pof
                                                                   inner join
                                                               ProyectoSolucionVivienda proyectoSolucionVivienda
                                                               on pof.pofProyectoSolucionVivienda = ProyectoSolucionVivienda.psvId
                                                                   inner join
                                                               MedioPagoProyectoVivienda medioPagoproyectoVivivienda
                                                               on proyectoSolucionVivienda.psvId =
                                                                  medioPagoproyectoVivivienda.mprProyectoSolucionVivienda
                                                                   inner join
                                                               MedioDePago medioPago
                                                               on medioPagoproyectoVivivienda.mprMedioDePago = medioPago.mdpId
                                                                   inner join
                                                               MedioConsignacion medioConsignacion
                                                               on medioPago.mdpId = medioConsignacion.mdpId
                                                                   inner join
                                                               Banco banco
                                                               on medioConsignacion.mcoBanco = banco.banId
                                                          WHERE (pof.pofId = @pofId)
                                                          FOR JSON AUTO, ROOT('proyectoSolucionViviendaMedioPagoConsignacion'))

SET @proyectoSolucionViviendaMedioPagoEfectivo = (SELECT DISTINCT medioEfectivo.mefEfectivo             as efectivo,
    medioEfectivo.mefSedeCajaCompensacion as sede


    FROM PostulacionFOVIS pof
    inner join
    ProyectoSolucionVivienda proyectoSolucionVivienda
    on pof.pofProyectoSolucionVivienda = ProyectoSolucionVivienda.psvId
    inner join
    MedioPagoProyectoVivienda medioPagoproyectoVivivienda
    on proyectoSolucionVivienda.psvId =
    medioPagoproyectoVivivienda.mprProyectoSolucionVivienda
    inner join
    MedioDePago medioPago
    on medioPagoproyectoVivivienda.mprMedioDePago = medioPago.mdpId
    inner join
    MedioEfectivo medioEfectivo
    on medioPago.mdpId = medioEfectivo.mdpId
    WHERE (pof.pofId = @pofId)
    FOR JSON AUTO, ROOT('proyectoSolucionViviendaMedioPagoEfectivo'))

SET @proyectoSolucionVivienda =
    JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].medioPago',
    JSON_QUERY(@proyectoSolucionViviendaMedioPagoConsignacion,
    '$.proyectoSolucionViviendaMedioPagoConsignacion[0]'));

SET @proyectoSolucionVivienda =
            JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].medioPago.idBanco',
                        JSON_VALUE(@proyectoSolucionVivienda,
                                   '$.proyectoSolucionVivienda[0].medioPago.medioConsignacion[0].idBanco'));

    SET @proyectoSolucionVivienda =
            JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].medioPago.tipoCuenta',
                        JSON_VALUE(@proyectoSolucionVivienda,
                                   '$.proyectoSolucionVivienda[0].medioPago.medioConsignacion[0].tipoCuenta'));

    SET @proyectoSolucionVivienda =
            JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].medioPago.numeroCuenta',
                        JSON_VALUE(@proyectoSolucionVivienda,
                                   '$.proyectoSolucionVivienda[0].medioPago.medioConsignacion[0].numeroCuenta'));
    SET @proyectoSolucionVivienda =
            JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].medioPago.tipoIdentificacionTitular',
                        JSON_VALUE(@proyectoSolucionVivienda,
                                   '$.proyectoSolucionVivienda[0].medioPago.medioConsignacion[0].tipoIdentificacionTitular'));
    SET @proyectoSolucionVivienda =
            JSON_MODIFY(@proyectoSolucionVivienda,
                        '$.proyectoSolucionVivienda[0].medioPago.numeroIdentificacionTitular',
                        JSON_VALUE(@proyectoSolucionVivienda,
                                   '$.proyectoSolucionVivienda[0].medioPago.medioConsignacion[0].numeroIdentificacionTitular'));
    SET @proyectoSolucionVivienda =
            JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].medioPago.nombreTitularCuenta',
                        JSON_VALUE(@proyectoSolucionVivienda,
                                   '$.proyectoSolucionVivienda[0].medioPago.medioConsignacion[0].nombreTitularCuenta'));
    SET @proyectoSolucionVivienda =
            JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].medioPago.nombreBanco',
                        JSON_VALUE(@proyectoSolucionVivienda,
                                   '$.proyectoSolucionVivienda[0].medioPago.medioConsignacion[0].banco[0].nombreBanco'));

    SET @proyectoSolucionVivienda =
            JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].medioPago.codigoBanco',
                        JSON_VALUE(@proyectoSolucionVivienda,
                                   '$.proyectoSolucionVivienda[0].medioPago.medioConsignacion[0].banco[0].codigoBanco'));

    SET @proyectoSolucionVivienda =
            JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].ubicacionProyecto',
                        JSON_QUERY(@proyectoSolucionVivienda,
                                   '$.proyectoSolucionVivienda[0].oferente[0].ubicacionProyecto[0]'));
    SET @proyectoSolucionVivienda =
            JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].medioPago.medioConsignacion', null);

    SET @proyectoSolucionVivienda =
            JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].oferente[0].ubicacionProyecto[0]',
                        null);

    SET @proyectoSolucionVivienda = JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].oferente',
                                                JSON_QUERY(@proyectoSolucionVivienda,
                                                           '$.proyectoSolucionVivienda[0].oferente[0]'));


	DECLARE @ubicacionProyecto as NVARCHAR(MAX);
    SET @ubicacionProyecto = (SELECT DISTINCT
													 ubicacionProyecto.ubiId                                 as idUbicacion,
                                                     ubicacionProyecto.ubiDireccionFisica                    as direccionFisica,
                                                     ubicacionProyecto.ubiCodigoPostal                       as codigoPostal,
                                                     ubicacionProyecto.ubiTelefonoFijo                       as telefonoFijo,
                                                     ubicacionProyecto.ubiIndicativoTelFijo                  as indicativoTelFijo,
                                                     ubicacionProyecto.ubiTelefonoFijo                       as telefonoCelular,
                                                     ubicacionProyecto.ubiEmail                              as email,
                                                     ubicacionProyecto.ubiAutorizacionEnvioEmail             as autorizacionEnvioEmail,
                                                     ubicacionProyecto.ubiMunicipio                          as idMunicipio,
                                                     ubicacionProyecto.ubiDescripcionIndicacion              as descripcionIndicacion

                                     FROM PostulacionFOVIS pof
                                              inner join
                                          ProyectoSolucionVivienda proyectoSolucionVivienda
                                          on pof.pofProyectoSolucionVivienda = ProyectoSolucionVivienda.psvId
                                              left join
                                          Ubicacion ubicacionProyecto
                                          on proyectoSolucionVivienda.psvUbicacionProyecto = ubicacionProyecto.ubiId
                                              inner join
                                          Oferente oferente
                                          on proyectoSolucionVivienda.psvOferente = oferente.ofeId
                                              inner join
                                          Persona persona
                                          on oferente.ofePersona = persona.perId
                                              left join
                                          PersonaDetalle personaDetalle
                                          on persona.perId = personaDetalle.pedPersona
                                     WHERE (pof.pofId =  @pofId)
                                     FOR JSON PATH, WITHOUT_ARRAY_WRAPPER )


	SET @proyectoSolucionVivienda = JSON_MODIFY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0].ubicacionProyecto',
JSON_QUERY(@ubicacionProyecto,'$'));


    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].proyectoSolucionVivienda',
                                   JSON_QUERY(@proyectoSolucionVivienda, '$.proyectoSolucionVivienda[0]'));

    SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].postulacion', JSON_QUERY(@postulacion, '$.postulacion[0]'));

	SET @Integrantes =  (select
								inhPersona AS idPersona,
								perTipoIdentificacion AS tipoIdentificacion,
								perNumeroIdentificacion AS numeroIdentificacion,
								perDigitoVerificacion AS digitoVerificacion,
								perPrimerNombre AS primerNombre,
								perSegundoNombre AS segundoNombre,
								perPrimerApellido AS primerApellido,
								perSegundoApellido AS segundoApellido,
								perRazonSocial AS razonSocial,
								perCreadoPorPila AS creadoPorPila,
								pedId AS idPersonaDetalle,
								DATEDIFF_BIG(MS, '1969-12-31 05:00:00', pedFechaNacimiento) AS fechaNacimiento,
								DATEDIFF_BIG(MS, '1969-12-31 05:00:00', pedFechaExpedicionDocumento) AS fechaExpedicionDocumento,
								pedGenero AS genero,
								pedOcupacionProfesion AS idOcupacionProfesion,
								pedNivelEducativo AS nivelEducativo,
								pedGradoAcademico AS gradoAcademico,
								pedCabezaHogar AS cabezaHogar,
								pedHabitaCasaPropia AS habitaCasaPropia,
								pedAutorizaUsoDatosPersonales AS autorizaUsoDatosPersonales,
								pedResideSectorRural AS resideSectorRural,
								pedEstadoCivil AS estadoCivil,
								pedFallecido AS fallecido,
								DATEDIFF_BIG(MS, '1969-12-31 05:00:00', pedFechaFallecido) AS fechaFallecido,
								pedBeneficiarioSubsidio AS beneficiarioSubsidio,
								pedFechaDefuncion AS fechaDefuncion,
								pedEstudianteTrabajoDesarrolloHumano AS estudianteTrabajoDesarrolloHumano,
								pedPersonaPadre AS idPersonaPadre,
								pedPersonaMadre AS idPersonaMadre,
								pedOrientacionSexual AS orientacionSexual,
								pedFactorVulnerabilidad AS factorVulnerabilidad,
								pedPertenenciaEtnica AS pertenenciaEtnica,
								pedPaisResidencia AS idPaisResidencia,
								pedResguardo AS idResguardo,
								pedResguardo AS idPuebloIndigena,
								inhId AS idIntegranteHogar,
								inhJefeHogar AS idJefeHogar,
								inhTipoIntegrante AS tipoIntegranteHogar,
								inhIntegranteReemplazaJefeHogar AS integranteReemplazaJefeHogar,
								inhEstadoHogar AS estadoHogar,
								inhSalarioMensual AS ingresosMensuales,
								inhIntegranteValido AS integranteValido,
								inhPostulacionFovis AS idPostulacion
						from IntegranteHogar inner join PersonaDetalle
						on inhPersona = pedPersona
						inner join Persona
						on inhPersona = perId
						where inhPostulacionFovis = @pofId
						FOR JSON PATH)


	SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].integrantesHogar',
	JSON_QUERY(@Integrantes,'$'));

    SET @json = JSON_QUERY(@Solicitud, '$[0]');

Update PostulacionFOVIS SET pofJsonPostulacion = @json Where pofId = @pofId;

----------------------------------------------------------------------JSON INFO ASIGNACION-----------------------------------------------------------------------

/*-------------------------------------------------------------------------SOLICITUD---------------------------------------------------------------*/

SET @Solicitud = (
	SELECT DISTINCT solicitudPostulacion.spoId              as idSolicitudPostulacion,
                                      solicitudPostulacion.spoSolicitudGlobal as idSolicitud,
                                      solicitudGlobal.solNumeroRadicacion     as numeroRadicacion,
                                      solicitudGlobal.solInstanciaProceso     as idInstanciaProceso,
                                      solicitudPostulacion.spoEstadoSolicitud as estadoSolicitud,
                                      solicitudGlobal.solCanalRecepcion       as canalRecepcion,
                                      solicitudGlobal.solTipoTransaccion      as tipoTransaccionEnum,
                                      solicitudGlobal.solMetodoEnvio          as metodoEnvio,
                                      solicitudGlobal.solObservacion          as observaciones,

                                      DATEDIFF_BIG(MS, '1970-01-01 00:00:00', solicitudGlobal.solFechaRadicacion)  as fechaRadicacion
                      FROM SolicitudPostulacion solicitudPostulacion
                               inner join
                           Solicitud solicitudGlobal
                           on solicitudPostulacion.spoSolicitudGlobal = solicitudGlobal.solId
                               inner join
                           PostulacionFOVIS postulacion
                           on solicitudPostulacion.spoPostulacionFOVIS = postulacion.pofId

                      WHERE (postulacion.pofId = @pofId)
                      FOR JSON PATH )

    SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].numeroRadicacion',
                                 JSON_VALUE(@Solicitud, '$[0].solicitudGlobal[0].numeroRadicacion'));
    SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].idInstanciaProceso',
                                 JSON_VALUE(@Solicitud, '$[0].solicitudGlobal[0].idInstanciaProceso'));
    SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].canalRecepcion',
                                 JSON_VALUE(@Solicitud, '$[0].solicitudGlobal[0].canalRecepcion'));
    SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].tipoTransaccionEnum',
                                 JSON_VALUE(@Solicitud, '$[0].solicitudGlobal[0].tipoTransaccionEnum'));
    SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].metodoEnvio',
                                 JSON_VALUE(@Solicitud, '$[0].solicitudGlobal[0].metodoEnvio'));
    SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].observaciones',
                                 JSON_VALUE(@Solicitud, '$[0].solicitudGlobal[0].observaciones'));
    SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].solFechaRadicacion',
                                 JSON_VALUE(@Solicitud, '$[0].solicitudGlobal[0].solFechaRadicacion'));

	SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].solicitudGlobal', null);


	SET @Integrantes =  (select
								inhPersona AS idPersona,
								perTipoIdentificacion AS tipoIdentificacion,
								perNumeroIdentificacion AS numeroIdentificacion,
								perDigitoVerificacion AS digitoVerificacion,
								perPrimerNombre AS primerNombre,
								perSegundoNombre AS segundoNombre,
								perPrimerApellido AS primerApellido,
								perSegundoApellido AS segundoApellido,
								perRazonSocial AS razonSocial,
								perCreadoPorPila AS creadoPorPila,
								pedId AS idPersonaDetalle,
								DATEDIFF_BIG(MS, '1969-12-31 05:00:00', pedFechaNacimiento) AS fechaNacimiento,
								DATEDIFF_BIG(MS, '1969-12-31 05:00:00', pedFechaExpedicionDocumento) AS fechaExpedicionDocumento,
								pedGenero AS genero,
								pedOcupacionProfesion AS idOcupacionProfesion,
								pedNivelEducativo AS nivelEducativo,
								pedGradoAcademico AS gradoAcademico,
								pedCabezaHogar AS cabezaHogar,
								pedHabitaCasaPropia AS habitaCasaPropia,
								pedAutorizaUsoDatosPersonales AS autorizaUsoDatosPersonales,
								pedResideSectorRural AS resideSectorRural,
								pedEstadoCivil AS estadoCivil,
								pedFallecido AS fallecido,
								DATEDIFF_BIG(MS, '1969-12-31 05:00:00', pedFechaFallecido) AS fechaFallecido,
								pedBeneficiarioSubsidio AS beneficiarioSubsidio,
								pedFechaDefuncion AS fechaDefuncion,
								pedEstudianteTrabajoDesarrolloHumano AS estudianteTrabajoDesarrolloHumano,
								pedPersonaPadre AS idPersonaPadre,
								pedPersonaMadre AS idPersonaMadre,
								pedOrientacionSexual AS orientacionSexual,
								pedFactorVulnerabilidad AS factorVulnerabilidad,
								pedPertenenciaEtnica AS pertenenciaEtnica,
								pedPaisResidencia AS idPaisResidencia,
								pedResguardo AS idResguardo,
								pedResguardo AS idPuebloIndigena,
								inhId AS idIntegranteHogar,
								inhJefeHogar AS idJefeHogar,
								inhTipoIntegrante AS tipoIntegranteHogar,
								inhIntegranteReemplazaJefeHogar AS integranteReemplazaJefeHogar,
								inhEstadoHogar AS estadoHogar,
								inhSalarioMensual AS ingresosMensuales,
								inhIntegranteValido AS integranteValido,
								inhPostulacionFovis AS idPostulacion
						from IntegranteHogar inner join PersonaDetalle
						on inhPersona = pedPersona
						inner join Persona
						on inhPersona = perId
						where inhPostulacionFovis = @pofId
						FOR JSON PATH)


	SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].integrantesHogar',
	JSON_QUERY(@Integrantes,'$'));
                               --    JSON_VALUE(@Integrantes,'$[0].integrantesHogar'));
							   --    JSON_QUERY(N'[]','$'));


/*-------------------------------------------------------------------------POSTULACION---------------------------------------------------------------*/

    SET @postulacion = (SELECT DISTINCT pof.pofId                                   as idPostulacion,
                                        pof.pofCicloAsignacion                      as idCicloAsignacion,
                                        pof.pofJefeHogar                            as idJefeHogar,
                                        pof.pofModalidad                            as idModalidad,
                                        pof.pofCondicionHogar                       as condicionHogar,
                                        pof.pofEstadoHogar                          as estadoHogar,
                                        pof.pofCondicionHogar                       as condicionHogar,
                                        pof.pofHogarPerdioSubsidioNoPago            as hogarPerdioSubsidioNoPago,
                                        pof.pofCantidadFolios                       as cantidadFolios,
										ISNULL(pof.pofValorSFVSolicitado,0)			as valorSFVSolicitado,
                                        pof.pofResultadoAsignacion                  as resultadoAsignacion,
                                        pof.pofValorAsignadoSFV                     as valorAsignadoSFV,
                                        pof.pofPuntaje                              as puntaje,
                                        DATEDIFF_BIG(MS, '1970-01-01 00:00:00', pof.pofFechaCalificacion)                    as fechaCalificacion,
                                        pof.pofPrioridadAsignacion                  as prioridadAsignacion,
                                        pof.pofValorCalculadoSFV                    as valorCalculadoSFV,
                                        pof.pofValorProyectoVivienda                as valorProyectoVivienda,
                                        pof.pofMotivoDesistimiento                  as motivoDesistimientoPostulacion,
                                        pof.pofMotivoHabilitacion                   as motivoHabilitacion,
                                        pof.pofRestituidoConSancion                 as restituidoConSancion,
                                        pof.pofTiempoSancion                        as tiempoSancion,
                                        pof.pofMotivoRestitucion                    as motivoRestitucion,
                                        pof.pofMotivoEnajenacion                    as motivoEnajenacion,
                                        pof.pofValorAjusteIPCSFV                    as valorAjusteIPCSFV,
                                        pof.pofMotivoRechazo                        as motivoRechazo,
                                        pof.pofValorAvaluoCatastral                 as avaluoCatastralVivienda,
                                        pof.pofInfoParametrizacion                  as informacionParametrizacion,
                                        pof.pofValorSFVAjustado                     as valorSFVAjustado,
                                        pof.pofMatriculaInmobiliariaInmueble        as matriculaInmobiliariaInmueble,
                                        DATEDIFF_BIG(MS, '1970-01-01 00:00:00', pof.pofFechaRegistroEscritura)               as fechaRegistroEscritura,
                                        pof.pofLoteUrbanizado                       as loteUrbanizado,
                                        pof.pofPoseedorOcupanteVivienda             as poseedorOcupanteVivienda,
                                        pof.pofNumeroEscritura                      as numeroEscritura,
                                        DATEDIFF_BIG(MS, '1970-01-01 00:00:00', pof.pofFechaEscritura)                       as fechaEscritura,
                                        cicloAsignacion.ciaId                       as idCicloAsignacion,
                                        cicloAsignacion.ciaNombre                   as nombre,
                                        DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cicloAsignacion.ciaFechaInicio)              as fechaInicio,
                                        DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cicloAsignacion.ciaFechaFin)                 as fechaFin,
                                        cicloAsignacion.ciaCicloPredecesor          as cicloPredecesor,
                                        cicloAsignacion.ciaEstadoCicloAsignacion    as estadoCicloAsignacion,
                                        cicloAsignacion.ciaCicloActivo              as cicloActivo,
                                        cicloAsignacion.ciaValorDisponible          as valorDisponible,
                                        pof.pofId                                   as jefeHogar

                        FROM PostulacionFOVIS pof
                                 inner join
                             ProyectoSolucionVivienda proyectoSolucionVivienda
                             on pof.pofProyectoSolucionVivienda = ProyectoSolucionVivienda.psvId
                                 inner join
                             Oferente oferente
                             on proyectoSolucionVivienda.psvOferente = oferente.ofeId
                                 inner join
                             CicloAsignacion cicloAsignacion
                             on pof.pofCicloAsignacion = cicloAsignacion.ciaId
                                 inner join
                             Ubicacion ubicacionVivienda
                             on pof.pofUbicacionVivienda = ubicacionVivienda.ubiId

                        WHERE (pof.pofId = @pofId)
                        FOR JSON AUTO, ROOT('postulacion'))


	--declare @arreglo nvarchar(10)
	--set @arreglo = N'[]'
	--JSON_QUERY(@arreglo,'$') as integrantesHogar,
	--JSON_QUERY(N'[]','$')



/*-------------------------------------------------------------------------CICLO ASIGNACION---------------------------------------------------------------*/

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].cicloAsignacion',
                                   JSON_QUERY(@postulacion, '$.postulacion[0].cicloAsignacion[0]'));

/*-------------------------------------------------------------------------AHORRO PROGRAMADO---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT ahorroProgramado.ahpId                  as idAhorroPrevio,
                                             ahorroProgramado.ahpNombreAhorro        as nombreAhorro,
                                             ahorroProgramado.ahpEntidad             as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', ahorroProgramado.ahpFechaInicial)        as fechaInicial,
                                             ahorroProgramado.ahpValor               as valor,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', ahorroProgramado.ahpFechaInmovilizacion) as fechaInmovilizacion,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', ahorroProgramado.ahpFechaAdquisicion)    as fechaAdquisicion,
                                             ahorroProgramado.ahpPostulacionFOVIS    as idPostulacion,
                                             ahorroProgramado.ahpAhorroMovilizado    as ahorroMovilizado
                             FROM PostulacionFOVIS pof
                                      inner join
                                  AhorroPrevio ahorroProgramado
                                  on pof.pofId = ahorroProgramado.ahpPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND ahorroProgramado.ahpNombreAhorro = 'AHORRO_PROGRAMADO')
                             FOR JSON AUTO, ROOT('ahorroProgramado'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].ahorroProgramado',
                                   JSON_QUERY(@ahorroProgramado, '$.ahorroProgramado[0]'));
/*-------------------------------------------------------------------------APORTES PERIODICOS---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT ahorroProgramadoContractual.ahpId                  as idAhorroPrevio,
                                             ahorroProgramadoContractual.ahpNombreAhorro        as nombreAhorro,
                                             ahorroProgramadoContractual.ahpEntidad             as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', ahorroProgramadoContractual.ahpFechaInicial)        as fechaInicial,
                                             ahorroProgramadoContractual.ahpValor               as valor,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', ahorroProgramadoContractual.ahpFechaInmovilizacion) as fechaInmovilizacion,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', ahorroProgramadoContractual.ahpFechaAdquisicion)    as fechaAdquisicion,
                                             ahorroProgramadoContractual.ahpPostulacionFOVIS    as idPostulacion,
                                             ahorroProgramadoContractual.ahpAhorroMovilizado    as ahorroMovilizado
                             FROM PostulacionFOVIS pof
                                      inner join
                                  AhorroPrevio ahorroProgramadoContractual
                                  on pof.pofId = ahorroProgramadoContractual.ahpPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND ahorroProgramadoContractual.ahpNombreAhorro =
                                                           'AHORRO_PROGRAMADO_CONTRACTUAL_EVALUACION_CREDITICIA_FAVORABLE_FNA')
                             FOR JSON AUTO, ROOT('ahorroProgramadoContractual'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].ahorroProgramadoContractual',
                                   JSON_QUERY(@ahorroProgramado, '$.ahorroProgramadoContractual[0]'));

/*-------------------------------------------------------------------------APORTES PERIODICOS---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT aportesPeriodicos.ahpId                  as idAhorroPrevio,
                                             aportesPeriodicos.ahpNombreAhorro        as nombreAhorro,
                                             aportesPeriodicos.ahpEntidad             as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', aportesPeriodicos.ahpFechaInicial)        as fechaInicial,
                                             aportesPeriodicos.ahpValor               as valor,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', aportesPeriodicos.ahpFechaInmovilizacion) as fechaInmovilizacion,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', aportesPeriodicos.ahpFechaAdquisicion)    as fechaAdquisicion,
                                             aportesPeriodicos.ahpPostulacionFOVIS    as idPostulacion,
                                             aportesPeriodicos.ahpAhorroMovilizado    as ahorroMovilizado
                             FROM PostulacionFOVIS pof
                                      inner join
                                  AhorroPrevio aportesPeriodicos
                                  on pof.pofId = aportesPeriodicos.ahpPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND aportesPeriodicos.ahpNombreAhorro = 'APORTES_PERIODICOS')
                             FOR JSON AUTO, ROOT('aportesPeriodicos'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].aportesPeriodicos',
                                   JSON_QUERY(@ahorroProgramado, '$.aportesPeriodicos[0]'));

/*-------------------------------------------------------------------------CESANTIAS INMOVILIZADAS---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT cesantiasInmovilizadas.ahpId                  as idAhorroPrevio,
                                             cesantiasInmovilizadas.ahpNombreAhorro        as nombreAhorro,
                                             cesantiasInmovilizadas.ahpEntidad             as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cesantiasInmovilizadas.ahpFechaInicial)        as fechaInicial,
                                             cesantiasInmovilizadas.ahpValor               as valor,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cesantiasInmovilizadas.ahpFechaInmovilizacion) as fechaInmovilizacion,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cesantiasInmovilizadas.ahpFechaAdquisicion)    as fechaAdquisicion,
                                             cesantiasInmovilizadas.ahpPostulacionFOVIS    as idPostulacion,
                                             cesantiasInmovilizadas.ahpAhorroMovilizado    as ahorroMovilizado
                             FROM PostulacionFOVIS pof
                                      inner join
                                  AhorroPrevio cesantiasInmovilizadas
                                  on pof.pofId = cesantiasInmovilizadas.ahpPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND
                                    cesantiasInmovilizadas.ahpNombreAhorro = 'CESANTIAS_INMOVILIZADAS')
                             FOR JSON AUTO, ROOT('cesantiasInmovilizadas'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].cesantiasInmovilizadas',
                                   JSON_QUERY(@ahorroProgramado, '$.cesantiasInmovilizadas[0]'));

/*-------------------------------------------------------------------------CUOTA INICIAL---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT cuotaInicial.ahpId                  as idAhorroPrevio,
                                             cuotaInicial.ahpNombreAhorro        as nombreAhorro,
                                             cuotaInicial.ahpEntidad             as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cuotaInicial.ahpFechaInicial)        as fechaInicial,
                                             cuotaInicial.ahpValor               as valor,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cuotaInicial.ahpFechaInmovilizacion) as fechaInmovilizacion,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cuotaInicial.ahpFechaAdquisicion)    as fechaAdquisicion,
                                             cuotaInicial.ahpPostulacionFOVIS    as idPostulacion,
                                             cuotaInicial.ahpAhorroMovilizado    as ahorroMovilizado
                             FROM PostulacionFOVIS pof
                                      inner join
                                  AhorroPrevio cuotaInicial
                                  on pof.pofId = cuotaInicial.ahpPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND cuotaInicial.ahpNombreAhorro = 'CUOTA_INICIAL')
                             FOR JSON AUTO, ROOT('cuotaInicial'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].cuotaInicial',
                                   JSON_QUERY(@ahorroProgramado, '$.cuotaInicial[0]'));

/*-------------------------------------------------------------------------CUOTA PAGADAS---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT cuotasPagadas.ahpId                  as idAhorroPrevio,
                                             cuotasPagadas.ahpNombreAhorro        as nombreAhorro,
                                             cuotasPagadas.ahpEntidad             as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cuotasPagadas.ahpFechaInicial)        as fechaInicial,
                                             cuotasPagadas.ahpValor               as valor,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cuotasPagadas.ahpFechaInmovilizacion) as fechaInmovilizacion,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cuotasPagadas.ahpFechaAdquisicion)    as fechaAdquisicion,
                                             cuotasPagadas.ahpPostulacionFOVIS    as idPostulacion,
                                             cuotasPagadas.ahpAhorroMovilizado    as ahorroMovilizado
                             FROM PostulacionFOVIS pof
                                      inner join
                                  AhorroPrevio cuotasPagadas
                                  on pof.pofId = cuotasPagadas.ahpPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND cuotasPagadas.ahpNombreAhorro = 'CUOTAS_PAGADAS')
                             FOR JSON AUTO, ROOT('cuotasPagadas'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].cuotasPagadas',
                                   JSON_QUERY(@ahorroProgramado, '$.cuotasPagadas[0]'));

/*-------------------------------------------------------------------------VALOR LOTE O TERRENO PROPIO---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT cuotasPagadas.ahpId                  as idAhorroPrevio,
                                             cuotasPagadas.ahpNombreAhorro        as nombreAhorro,
                                             cuotasPagadas.ahpEntidad             as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cuotasPagadas.ahpFechaInicial)        as fechaInicial,
                                             cuotasPagadas.ahpValor               as valor,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cuotasPagadas.ahpFechaInmovilizacion) as fechaInmovilizacion,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cuotasPagadas.ahpFechaAdquisicion)    as fechaAdquisicion,
                                             cuotasPagadas.ahpPostulacionFOVIS    as idPostulacion,
                                             cuotasPagadas.ahpAhorroMovilizado    as ahorroMovilizado
                             FROM PostulacionFOVIS pof
                                      inner join
                                  AhorroPrevio cuotasPagadas
                                  on pof.pofId = cuotasPagadas.ahpPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND
                                    cuotasPagadas.ahpNombreAhorro = 'VALOR_LOTE_O_TERRENO_PROPIO')
                             FOR JSON AUTO, ROOT('valorLoteTerreno'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].valorLoteTerreno',
                                   JSON_QUERY(@ahorroProgramado, '$.valorLoteTerreno[0]'));

/*-------------------------------------------------------------------------VALOR LOTE OPV---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT valorLoteOPV.ahpId                  as idAhorroPrevio,
                                             valorLoteOPV.ahpNombreAhorro        as nombreAhorro,
                                             valorLoteOPV.ahpEntidad             as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', valorLoteOPV.ahpFechaInicial)        as fechaInicial,
                                             valorLoteOPV.ahpValor               as valor,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', valorLoteOPV.ahpFechaInmovilizacion) as fechaInmovilizacion,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', valorLoteOPV.ahpFechaAdquisicion)    as fechaAdquisicion,
                                             valorLoteOPV.ahpPostulacionFOVIS    as idPostulacion,
                                             valorLoteOPV.ahpAhorroMovilizado    as ahorroMovilizado
                             FROM PostulacionFOVIS pof
                                      inner join
                                  AhorroPrevio valorLoteOPV
                                  on pof.pofId = valorLoteOPV.ahpPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND valorLoteOPV.ahpNombreAhorro = 'VALOR_LOTE_OPV')
                             FOR JSON AUTO, ROOT('valorLoteOPV'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].valorLoteOPV',
                                   JSON_QUERY(@ahorroProgramado, '$.valorLoteOPV[0]'));

/*-------------------------------------------------------------------------VALOR LOTE POR SUBSIDIO MUNICIPAL O DEPARTAMENTAL---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT valorLoteSubsidioMunicipal.ahpId                  as idAhorroPrevio,
                                             valorLoteSubsidioMunicipal.ahpNombreAhorro        as nombreAhorro,
                                             valorLoteSubsidioMunicipal.ahpEntidad             as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', valorLoteSubsidioMunicipal.ahpFechaInicial)        as fechaInicial,
                                             valorLoteSubsidioMunicipal.ahpValor               as valor,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', valorLoteSubsidioMunicipal.ahpFechaInmovilizacion) as fechaInmovilizacion,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', valorLoteSubsidioMunicipal.ahpFechaAdquisicion)    as fechaAdquisicion,
                                             valorLoteSubsidioMunicipal.ahpPostulacionFOVIS    as idPostulacion,
                                             valorLoteSubsidioMunicipal.ahpAhorroMovilizado    as ahorroMovilizado
                             FROM PostulacionFOVIS pof
                                      inner join
                                  AhorroPrevio valorLoteSubsidioMunicipal
                                  on pof.pofId = valorLoteSubsidioMunicipal.ahpPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND valorLoteSubsidioMunicipal.ahpNombreAhorro =
                                                           'VALOR_LOTE_POR_SUBSIDIO_MUNICIPAL_O_DEPARTAMENTAL')
                             FOR JSON AUTO, ROOT('valorLoteSubsidioMunicipal'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].valorLoteSubsidioMunicipal',
                                   JSON_QUERY(@ahorroProgramado, '$.valorLoteSubsidioMunicipal[0]'));

/*-------------------------------------------------------------------------AHORRO OTRAS MODALIDADES---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT ahorroOtrasModalidades.recId               as idRecurso,
                                             ahorroOtrasModalidades.recNombre           as nombre,
                                             ahorroOtrasModalidades.recEntidad          as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', ahorroOtrasModalidades.recFecha)            as fecha,
                                             ahorroOtrasModalidades.recOtroRecurso      as otroRecurso,
                                             ahorroOtrasModalidades.recValor            as valor,
                                             ahorroOtrasModalidades.recPostulacionFOVIS as postulacion
                             FROM PostulacionFOVIS pof
                                      inner join
                                  RecursoComplementario ahorroOtrasModalidades
                                  on pof.pofId = ahorroOtrasModalidades.recPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND
                                    ahorroOtrasModalidades.recNombre = 'AHORRO_OTRAS_MODALIDADES')
                             FOR JSON AUTO, ROOT('ahorroOtrasModalidades'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].ahorroOtrasModalidades',
                                   JSON_QUERY(@ahorroProgramado, '$.ahorroOtrasModalidades[0]'));

/*-------------------------------------------------------------------------APORTES ENTE TERRITORIAL---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT aportesEnteTerritorial.recId               as idRecurso,
                                             aportesEnteTerritorial.recNombre           as nombre,
                                             aportesEnteTerritorial.recEntidad          as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', aportesEnteTerritorial.recFecha)            as fecha,
                                             aportesEnteTerritorial.recOtroRecurso      as otroRecurso,
                                             aportesEnteTerritorial.recValor            as valor,
                                             aportesEnteTerritorial.recPostulacionFOVIS as postulacion
                             FROM PostulacionFOVIS pof
                                      inner join
                                  RecursoComplementario aportesEnteTerritorial
                                  on pof.pofId = aportesEnteTerritorial.recPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND
                                    aportesEnteTerritorial.recNombre = 'APORTES_ENTE_TERRITORIAL')
                             FOR JSON AUTO, ROOT('aportesEnteTerritorial'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].aportesEnteTerritorial',
                                   JSON_QUERY(@ahorroProgramado, '$.aportesEnteTerritorial[0]'));

/*-------------------------------------------------------------------------APORTES_SOLIDARIOS---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT aportesSolidarios.recId               as idRecurso,
                                             aportesSolidarios.recNombre           as nombre,
                                             aportesSolidarios.recEntidad          as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00',aportesSolidarios.recFecha)            as fecha,
                                             aportesSolidarios.recOtroRecurso      as otroRecurso,
                                             aportesSolidarios.recValor            as valor,
                                             aportesSolidarios.recPostulacionFOVIS as postulacion
                             FROM PostulacionFOVIS pof
                                      inner join
                                  RecursoComplementario aportesSolidarios
                                  on pof.pofId = aportesSolidarios.recPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND aportesSolidarios.recNombre = 'APORTES_SOLIDARIOS')
                             FOR JSON AUTO, ROOT('aportesSolidarios'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].aportesSolidarios',
                                   JSON_QUERY(@ahorroProgramado, '$.aportesSolidarios[0]'));

/*-------------------------------------------------------------------------CESANTIAS_NO_INMOVILIZADAS---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT cesantiasNoInmovilizadas.recId               as idRecurso,
                                             cesantiasNoInmovilizadas.recNombre           as nombre,
                                             cesantiasNoInmovilizadas.recEntidad          as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', cesantiasNoInmovilizadas.recFecha)            as fecha,
                                             cesantiasNoInmovilizadas.recOtroRecurso      as otroRecurso,
                                             cesantiasNoInmovilizadas.recValor            as valor,
                                             cesantiasNoInmovilizadas.recPostulacionFOVIS as postulacion
                             FROM PostulacionFOVIS pof
                                      inner join
                                  RecursoComplementario cesantiasNoInmovilizadas
                                  on pof.pofId = cesantiasNoInmovilizadas.recPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND
                                    cesantiasNoInmovilizadas.recNombre = 'CESANTIAS_NO_INMOVILIZADAS')
                             FOR JSON AUTO, ROOT('cesantiasNoInmovilizadas'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].cesantiasNoInmovilizadas',
                                   JSON_QUERY(@ahorroProgramado, '$.cesantiasNoInmovilizadas[0]'));

/*-------------------------------------------------------------------------CREDITO_APROBADO---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT creditoAprobado.recId               as idRecurso,
                                             creditoAprobado.recNombre           as nombre,
                                             creditoAprobado.recEntidad          as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', creditoAprobado.recFecha)            as fecha,
                                             creditoAprobado.recOtroRecurso      as otroRecurso,
                                             creditoAprobado.recValor            as valor,
                                             creditoAprobado.recPostulacionFOVIS as postulacion
                             FROM PostulacionFOVIS pof
                                      inner join
                                  RecursoComplementario creditoAprobado
                                  on pof.pofId = creditoAprobado.recPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND creditoAprobado.recNombre = 'CREDITO_APROBADO')
                             FOR JSON AUTO, ROOT('creditoAprobado'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].creditoAprobado',
                                   JSON_QUERY(@ahorroProgramado, '$.creditoAprobado[0]'));

/*-------------------------------------------------------------------------DONACION_OTRAS_ENTIDADES---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT donacionOtrasEntidades.recId               as idRecurso,
                                             donacionOtrasEntidades.recNombre           as nombre,
                                             donacionOtrasEntidades.recEntidad          as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', donacionOtrasEntidades.recFecha)            as fecha,
                                             donacionOtrasEntidades.recOtroRecurso      as otroRecurso,
                                             donacionOtrasEntidades.recValor            as valor,
                                             donacionOtrasEntidades.recPostulacionFOVIS as postulacion
                             FROM PostulacionFOVIS pof
                                      inner join
                                  RecursoComplementario donacionOtrasEntidades
                                  on pof.pofId = donacionOtrasEntidades.recPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND
                                    donacionOtrasEntidades.recNombre = 'DONACION_OTRAS_ENTIDADES')
                             FOR JSON AUTO, ROOT('donacionOtrasEntidades'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].donacionOtrasEntidades',
                                   JSON_QUERY(@ahorroProgramado, '$.donacionOtrasEntidades[0]'));

/*-------------------------------------------------------------------------EVALUACION_CREDITICIA---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT evaluacionCrediticia.recId               as idRecurso,
                                             evaluacionCrediticia.recNombre           as nombre,
                                             evaluacionCrediticia.recEntidad          as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', evaluacionCrediticia.recFecha)            as fecha,
                                             evaluacionCrediticia.recOtroRecurso      as otroRecurso,
                                             evaluacionCrediticia.recValor            as valor,
                                             evaluacionCrediticia.recPostulacionFOVIS as postulacion
                             FROM PostulacionFOVIS pof
                                      inner join
                                  RecursoComplementario evaluacionCrediticia
                                  on pof.pofId = evaluacionCrediticia.recPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND evaluacionCrediticia.recNombre = 'EVALUACION_CREDITICIA')
                             FOR JSON AUTO, ROOT('evaluacionCrediticia'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].evaluacionCrediticia',
                                   JSON_QUERY(@ahorroProgramado, '$.evaluacionCrediticia[0]'));

/*-------------------------------------------------------------------------OTROS_RECURSOS---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT otrosRecursos.recId               as idRecurso,
                                             otrosRecursos.recNombre           as nombre,
                                             otrosRecursos.recEntidad          as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', otrosRecursos.recFecha)            as fecha,
                                             otrosRecursos.recOtroRecurso      as otroRecurso,
                                             otrosRecursos.recValor            as valor,
                                             otrosRecursos.recPostulacionFOVIS as postulacion
                             FROM PostulacionFOVIS pof
                                      inner join
                                  RecursoComplementario otrosRecursos
                                  on pof.pofId = otrosRecursos.recPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND otrosRecursos.recNombre = 'OTROS_RECURSOS')
                             FOR JSON AUTO, ROOT('otrosRecursos'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].otrosRecursos',
                                   JSON_QUERY(@ahorroProgramado, '$.otrosRecursos[0]'));


/*-------------------------------------------------------------------------OTROS_RECURSOS---------------------------------------------------------------*/
    SET @ahorroProgramado = (SELECT DISTINCT valorAvanceObra.recId               as idRecurso,
                                             valorAvanceObra.recNombre           as nombre,
                                             valorAvanceObra.recEntidad          as entidad,
                                             DATEDIFF_BIG(MS, '1970-01-01 00:00:00', valorAvanceObra.recFecha)            as fecha,
                                             valorAvanceObra.recOtroRecurso      as otroRecurso,
                                             valorAvanceObra.recValor            as valor,
                                             valorAvanceObra.recPostulacionFOVIS as postulacion
                             FROM PostulacionFOVIS pof
                                      inner join
                                  RecursoComplementario valorAvanceObra
                                  on pof.pofId = valorAvanceObra.recPostulacionFOVIS
                             WHERE (pof.pofId = @pofId AND valorAvanceObra.recNombre = 'OTROS_RECURSOS')
                             FOR JSON AUTO, ROOT('valorAvanceObra'))

    SET @postulacion = JSON_MODIFY(@postulacion, '$.postulacion[0].valorAvanceObra',
                                   JSON_QUERY(@ahorroProgramado, '$.valorAvanceObra[0]'));




/*-------------------------------------------------------------------------JEFE HOGAR---------------------------------------------------------------*/

    SET @jefeHogar = (SELECT DISTINCT persona.perId                                       as idPersona,
                                      persona.perTipoIdentificacion                       as tipoIdentificacion,
                                      persona.perNumeroIdentificacion                     as numeroIdentificacion,
                                      persona.perDigitoVerificacion                       as digitoVerificacion,
                                      persona.perPrimerNombre                             as primerNombre,
                                      persona.perSegundoNombre                            as segundoNombre,
                                      persona.perPrimerApellido                           as primerApellido,
                                      persona.perSegundoApellido                          as segundoApellido,
                                      persona.perRazonSocial                              as razonSocial,
                                      persona.perCreadoPorPila                            as creadoPorPila,
                                      personaDetalle.pedId                                as idPersonaDetalle,
                                      DATEDIFF_BIG(MS, '1970-01-01 00:00:00',personaDetalle.pedFechaNacimiento)                  as fechaNacimiento,
                                      DATEDIFF_BIG(MS, '1970-01-01 00:00:00', personaDetalle.pedFechaExpedicionDocumento)          as fechaExpedicionDocumento,
                                      personaDetalle.pedGenero                            as genero,
                                      personaDetalle.pedOcupacionProfesion                as idOcupacionProfesion,
                                      personaDetalle.pedNivelEducativo                    as nivelEducativo,
                                      personaDetalle.pedGradoAcademico                    as gradoAcademico,
                                      personaDetalle.pedCabezaHogar                       as cabezaHogar,
                                      personaDetalle.pedHabitaCasaPropia as habitaCasaPropia,
									  personaDetalle.pedAutorizaUsoDatosPersonales as autorizaUsoDatosPersonales,
                                      personaDetalle.pedResideSectorRural as resideSectorRural,
                                      personaDetalle.pedEstadoCivil                       as estadoCivil,
                                      personaDetalle.pedFallecido                         as fallecido,
                                      DATEDIFF_BIG(MS, '1970-01-01 00:00:00', personaDetalle.pedFechaFallecido)                    as fechaFallecido,
                                      DATEDIFF_BIG(MS, '1970-01-01 00:00:00', personaDetalle.pedFechaDefuncion)                    as fechaDefuncion,
                                      personaDetalle.pedEstudianteTrabajoDesarrolloHumano as estudianteTrabajoDesarrolloHumano,
                                      personaDetalle.pedPersonaPadre                      as idPersonaPadre,
                                      personaDetalle.pedPersonaMadre                      as idPersonaMadre,
                                      personaDetalle.pedOrientacionSexual                 as orientacionSexual,
                                      personaDetalle.pedFactorVulnerabilidad              as factorVulnerabilidad,
                                      personaDetalle.pedPertenenciaEtnica                 as pertenenciaEtnica,
                                      personaDetalle.pedPaisResidencia                    as idPaisResidencia,
									  personaDetalle.pedResideSectorRural				as resideSectorRural,
									  personaDetalle.pedHabitaCasaPropia				as pedHabitaCasaPropia,
                                      jefeHogar.jehId                                     as idJefeHogar,
                                      jefeHogar.jehAfiliado                               as idAfiliado,
                                      jefeHogar.jehEstadoHogar                            as estadoHogar,
                                      jefeHogar.jehIngresoMensual                         as ingresosMensuales,
									  'false'              as beneficiarioSubsidio,
                                      ubicacionModeloDTO.ubiId                            as idUbicacion,
                                      ubicacionModeloDTO.ubiDireccionFisica               as direccionFisica,
                                      ubicacionModeloDTO.ubiCodigoPostal                  as codigoPostal,
                                      ubicacionModeloDTO.ubiTelefonoFijo                  as telefonoFijo,
                                      ubicacionModeloDTO.ubiIndicativoTelFijo             as indicativoTelFijo,
                                      ubicacionModeloDTO.ubiTelefonoCelular				  as telefonoCelular,
                                      ubicacionModeloDTO.ubiEmail                         as email,
                                      ubicacionModeloDTO.ubiAutorizacionEnvioEmail        as autorizacionEnvioEmail,
                                      ubicacionModeloDTO.ubiMunicipio                     as idMunicipio,
                                      ubicacionModeloDTO.ubiDescripcionIndicacion         as descripcionIndicacion,
                                      ubicacionModeloDTO.ubiSectorUbicacion               as sectorUbicacion


                      FROM PostulacionFOVIS pof
                               inner join
                           JefeHogar jefeHogar
                           on pof.pofJefeHogar = jefeHogar.jehId
                               INNER JOIN
                           Afiliado afiliado
                           on afiliado.afiId = jefeHogar.jehAfiliado
                               inner join
                           Persona persona
                           on afiliado.afiPersona = persona.perId
                               inner join
                           PersonaDetalle personaDetalle
                           on personaDetalle.pedPersona = persona.perId
                               inner join
                           Ubicacion ubicacionModeloDTO
                           on persona.perUbicacionPrincipal = ubicacionModeloDTO.ubiId

                      WHERE (pof.pofId = @pofId)
                      FOR JSON AUTO, ROOT('jefeHogar'))

    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].idPersonaDetalle',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].idPersonaDetalle'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].fechaNacimiento',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].fechaNacimiento'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].fechaExpedicionDocumento',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].fechaExpedicionDocumento'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].genero',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].genero'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].idOcupacionProfesion',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].idOcupacionProfesion'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].nivelEducativo',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].nivelEducativo'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].gradoAcademico',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].gradoAcademico'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].cabezaHogar',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].cabezaHogar'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].nivelEducativo',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].nivelEducativo'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].habitaCasaPropia',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].habitaCasaPropia'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].autorizaUsoDatosPersonales',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].autorizaUsoDatosPersonales'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].resideSectorRural',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].resideSectorRural'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].estadoCivil',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].estadoCivil'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].fallecido',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].fallecido'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].fechaFallecido',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].fechaFallecido'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].beneficiarioSubsidio',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].beneficiarioSubsidio'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].fechaDefuncion',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].fechaDefuncion'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].estudianteTrabajoDesarrolloHumano',
                                 JSON_VALUE(@jefeHogar,
                                            '$.jefeHogar[0].personaDetalle[0].estudianteTrabajoDesarrolloHumano'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].idPersonaPadre',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].idPersonaPadre'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].idPersonaMadre',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].idPersonaMadre'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].factorVulnerabilidad',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].factorVulnerabilidad'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].pertenenciaEtnica',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].pertenenciaEtnica'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].idPaisResidencia',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].idPaisResidencia'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].idJefeHogar',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].jefeHogar[0].idJefeHogar'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].idAfiliado',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].jefeHogar[0].idAfiliado'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].estadoHogar',
                                 JSON_VALUE(@jefeHogar, '$.jefeHogar[0].personaDetalle[0].jefeHogar[0].estadoHogar'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].ingresosMensuales',
                                 JSON_VALUE(@jefeHogar,
                                            '$.jefeHogar[0].personaDetalle[0].jefeHogar[0].ingresosMensuales'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].beneficiarioSubsidio',
                                 JSON_VALUE(@jefeHogar,
                                            '$.jefeHogar[0].personaDetalle[0].jefeHogar[0].beneficiarioSubsidio'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].ubicacionModeloDTO',
                                 JSON_QUERY(@jefeHogar,
                                            '$.jefeHogar[0].personaDetalle[0].jefeHogar[0].ubicacionModeloDTO[0]'));
    SET @jefeHogar = JSON_MODIFY(@jefeHogar, '$.jefeHogar[0].personaDetalle', null);

	SET @postulacion =
            JSON_MODIFY(@postulacion, '$.postulacion[0].jefeHogar', JSON_QUERY(@jefeHogar, '$.jefeHogar[0]'));

	SET @Solicitud = JSON_MODIFY(@Solicitud, '$[0].postulacion', JSON_QUERY(@postulacion, '$.postulacion[0]'));





    SET @json = JSON_QUERY(@Solicitud, '$[0]');

Update PostulacionFOVIS SET pofInfoAsignacion = @json Where pofId = @pofId;

----------------------------------------------------------------------JSON INFO MODALIDAD-----------------------------------------------------------------------
SET @ParametrizacionModalidades = (SELECT DISTINCT paramModalidad.pmoId                         as idParametrizacionModalidad,
                                                       paramModalidad.pmoNombre                     as nombre,
                                                       paramModalidad.pmoEstado                     as estado,
                                                       paramModalidad.pmoTopeSMLMV                  as topeSMLMV,
                                                       paramModalidad.pmoTopeAvaluoCatastral        as topeAvaluoCatastral,
                                                       rangosSVFPorModalidad.rtvId                  as idRangoTopeValorSFV,
                                                       rangosSVFPorModalidad.rtvNombre              as nombre,
                                                       rangosSVFPorModalidad.rtvValorMinimo         as valorMinimo,
                                                       rangosSVFPorModalidad.rtvValorMaximo         as valorMaximo,
                                                       rangosSVFPorModalidad.rtvOperadorValorMinimo as operadorValorMinimo,
                                                       rangosSVFPorModalidad.rtvOperadorValorMaximo as operadorValorMaximo,
                                                       rangosSVFPorModalidad.rtvTopeSMLMV           as topeSMLMV,
                                                       rangosSVFPorModalidad.rtvModalidad           as idParametrizacionModalidad


                                       FROM PostulacionFOVIS pof
                                                inner join
                                            ParametrizacionModalidad paramModalidad
                                            on pof.pofModalidad = paramModalidad.pmoNombre
                                                inner join
                                            RangoTopeValorSFV rangosSVFPorModalidad
                                            on paramModalidad.pmoNombre = rangosSVFPorModalidad.rtvModalidad
                                       WHERE (pof.pofId = @pofId)
                                       FOR JSON AUTO)

    SET @ParametrizacionModalidades = JSON_QUERY(@ParametrizacionModalidades, '$[0]');

    SET @FormaPagoModalidad = (SELECT DISTINCT formasDePagoModalidad.fpmId        as idFormaPagoModalidad,
                                               formasDePagoModalidad.fpmModalidad as idParametrizacionModalidad,
                                               formasDePagoModalidad.fpmFormaPago as formaPago


                               FROM PostulacionFOVIS pof
                                        inner join
                                    FormaPagoModalidad formasDePagoModalidad
                                    on formasDePagoModalidad.fpmModalidad = pof.pofModalidad
                               WHERE (pof.pofId = @pofId)
                               FOR JSON AUTO, ROOT ('formasDePagoModalidad'))
    SET @ParametrizacionModalidades = JSON_MODIFY(@ParametrizacionModalidades, '$.formasDePagoModalidad',
                                                  JSON_QUERY(@FormaPagoModalidad, '$.formasDePagoModalidad'));

Update PostulacionFOVIS SET pofInfoParametrizacion = @ParametrizacionModalidades Where pofId = @pofId;

END