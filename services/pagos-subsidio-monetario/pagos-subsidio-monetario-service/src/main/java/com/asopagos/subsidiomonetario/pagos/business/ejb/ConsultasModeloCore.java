package com.asopagos.subsidiomonetario.pagos.business.ejb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;


import com.asopagos.archivos.clients.EliminarArchivo;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConsultasDinamicasConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.DocumentoSoporteModeloDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.dto.subsidiomonetario.pagos.PagoSubsidioProgramadoDTO;
import com.asopagos.entidades.ccf.core.DocumentoSoporte;
import com.asopagos.entidades.ccf.core.SitioPago;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.MedioEfectivo;
import com.asopagos.entidades.ccf.personas.MedioTarjeta;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.subsidiomonetario.liquidacion.SolicitudLiquidacionSubsidio;
import com.asopagos.entidades.subsidiomonetario.pagos.ArchivoRetiroTerceroPagador;
import com.asopagos.entidades.subsidiomonetario.pagos.ArchivoTransDetaSubsidio;
import com.asopagos.entidades.subsidiomonetario.pagos.ConvenioTerceroPagador;
import com.asopagos.entidades.subsidiomonetario.pagos.CuentaAdministradorSubsidio;
import com.asopagos.entidades.subsidiomonetario.pagos.DetalleSolicitudAnulacionSubsidioCobrado;
import com.asopagos.entidades.subsidiomonetario.pagos.DetalleSubsidioAsignado;
import com.asopagos.entidades.subsidiomonetario.pagos.DocumentoSoporteConvenio;
import com.asopagos.entidades.subsidiomonetario.pagos.RegistroArchivoRetiroTerceroPagador;
import com.asopagos.entidades.subsidiomonetario.pagos.RegistroInconsistenciaTarjeta;
import com.asopagos.entidades.subsidiomonetario.pagos.RegistroOperacionTransaccionSubsidio;
import com.asopagos.entidades.subsidiomonetario.pagos.RegistroSolicitudAnibol;
import com.asopagos.entidades.subsidiomonetario.pagos.RetiroPersonaAutorizadaCobroSubsidio;
import com.asopagos.entidades.subsidiomonetario.pagos.SolicitudAnulacionSubsidioCobrado;
import com.asopagos.entidades.subsidiomonetario.pagos.TransaccionFallidaOperacionTransacionSubsidio;
import com.asopagos.entidades.subsidiomonetario.pagos.TransaccionesFallidasSubsidio;
import com.asopagos.entidades.subsidiomonetario.pagos.anibol.ArchivoConsumosAnibol;
import com.asopagos.entidades.subsidiomonetario.pagos.anibol.ArchivoRetiroTerceroPagadorEfectivo;
import com.asopagos.entidades.subsidiomonetario.pagos.anibol.CampoArchivoConsumosAnibol;
import com.asopagos.entidades.subsidiomonetario.pagos.anibol.RegistroArchivoConsumosAnibol;
import com.asopagos.entidades.transversal.core.TempArchivoRetiroTerceroPagadorEfectivo;
import com.asopagos.entidades.transversal.core.ValidacionNombreArchivoTerceroPagador;

import com.asopagos.enumeraciones.subsidiomonetario.pagos.ResultadoGestionTransaccionFallidaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoTransaccionSubsidioEnum;

import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ReultadoValidacionCampoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.dispersion.TipoTransaccionPagoBancoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.entidadDescuento.TipoEntidadDescuentoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoAbonoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoArchivoRetiroTercerPagadorEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoArchivoTransDetaSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoConvenioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoRegistroCargueArchivoRetiroTerceroPagador;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoResolucionInconsistenciaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoSolicitudAnulacionSubsidioCobradoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoSubsidioAsignadoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoTransaccionFallidaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoTransaccionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.MotivoAnulacionSubsidioAsignadoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.OrigenRegistroSubsidioAsignadoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.OrigenTransaccionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ResultadoGestionInconsistenciaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ResultadoValidacionNombreArchivoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoCuotaSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoLiquidacionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoNovedadInconsistenciaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoOperacionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoTransaccionSubsidioMonetarioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ValidacionNombreArchivoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoArchivoConsumoTerceroPagadorEfectivo;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoRegistroArchivoConsumoAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoSolicitudAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.InconsistenciaResultadoValidacionArchivoConsumoAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.TipoInconsistenciaCampoArchivoConsumosAnibolEnum;
import com.asopagos.jpa.JPAUtils;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pagination.QueryBuilder;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore;
import com.asopagos.subsidiomonetario.pagos.constants.CamposArchivoConstants;
import com.asopagos.subsidiomonetario.pagos.constants.NamedQueriesConstants;
import com.asopagos.subsidiomonetario.pagos.constants.PagosSubsidioMonetarioConstants;
import com.asopagos.subsidiomonetario.pagos.constants.TipoErrorArchivoTerceroPagadorEfectivo;
import com.asopagos.subsidiomonetario.pagos.dto.AbonosAnulacionSubsidioCobradoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.AbonosSolicitudAnulacionSubsidioCobradoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.ArchivoConsumosAnibolModeloDTO;
import com.asopagos.subsidiomonetario.pagos.dto.ArchivoRetiroTerceroPagadorEfectivoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.ConvenioTercerPagadorDTO;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DescuentoSubsidioAsignadoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleResultadoEntidadDescuentoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleResultadoPagoEfectivoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleResultadoPagoTarjetaDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleSubsidioAsignadoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleTransaccionAsignadoConsultadoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionMontoLiquidadoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionResultadoEntidadDescuentoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionResultadoPagoBancoConsignacionesDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionResultadoPagoBancoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionResultadoPagoBancoPagoJuducialDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionResultadoPagoEfectivoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionResultadoPagoTarjetaDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionResumenEntidadDescuentoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionResumenPagoBancoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionResumenPagoEfectivoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionResumenPagoTarjetaDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionResumenTotalDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DocumentoSoporteConvenioModeloDTO;
import com.asopagos.subsidiomonetario.pagos.dto.GruposMedioTarjetaDTO;
import com.asopagos.subsidiomonetario.pagos.dto.IncosistenciaConciliacionConvenioDTO;
import com.asopagos.subsidiomonetario.pagos.dto.InfoDetallesSubsidioAgrupadosDTO;
import com.asopagos.subsidiomonetario.pagos.dto.InfoPersonaExpedicionDTO;
import com.asopagos.subsidiomonetario.pagos.dto.InfoPersonaExpedicionValidacionesDTO;
import com.asopagos.subsidiomonetario.pagos.dto.InfoPersonaReexpedicionDTO;
import com.asopagos.subsidiomonetario.pagos.dto.ItemResultadoEntidadDescuentoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.ItemResultadoPagoBancoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.ListadoSubsidiosAnularDTO;
import com.asopagos.subsidiomonetario.pagos.dto.RegistroSolicitudAnibolModeloDTO;
import com.asopagos.subsidiomonetario.pagos.dto.ResumenListadoSubsidiosAnularDTO;
import com.asopagos.subsidiomonetario.pagos.dto.RetiroCandidatoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.SolicitudAnulacionSubsidioCobradoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.SubsidioAnularDTO;
import com.asopagos.subsidiomonetario.pagos.dto.SubsidioMonetarioConsultaCambioPagosDTO;
import com.asopagos.subsidiomonetario.pagos.dto.SubsidioMonetarioPrescribirAnularFechaDTO;
import com.asopagos.subsidiomonetario.pagos.dto.SubsidioPerdidaDerechoInformesConsumoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.SubsidiosConsultaAnularPerdidaDerechoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.TarjetaRetiroCandidatoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.TempArchivoRetiroTerceroPagadorEfectivoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.TransaccionConsultadaDTO;
import com.asopagos.subsidiomonetario.pagos.dto.TransaccionFallidaDTO;
import com.asopagos.util.CalendarUtils;

import java.util.logging.Logger;

import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.notificaciones.dto.AutorizacionEnvioComunicadoDTO;

import java.text.ParseException;
import java.util.logging.Level;
import com.asopagos.entidades.subsidiomonetario.pagos.AuditoriaRecaudosYPagos;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.asopagos.entidades.ccf.personas.MedioDePago;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoBandejaTransaccionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ProcesoBandejaTransaccionEnum;
import com.asopagos.entidades.subsidiomonetario.pagos.BandejaDeTransacciones;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleBandejaTransaccionesDTO;
import com.asopagos.subsidiomonetario.pagos.dto.GestionDeTransaccionesDTO;
import java.text.NumberFormat;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import com.asopagos.pagination.PaginationQueryParamsEnum;

//import com.asopagos.dto.InformacionArchivoDTO;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de información en el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU-31-XXX<br/>
 *
 * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy Lader Lopez</a>
 */

@Stateless
public class ConsultasModeloCore implements IConsultasModeloCore, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloCore.class);

    /**
     * Entity Manager
     */
    @PersistenceContext(unitName = "pagossubsidiomonetario_PU")
    private EntityManager entityManagerCore;

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#registrarCargueArchivoTerceroPagador(com.asopagos.entidades.subsidiomonetario.pagos.ArchivoRetiroTerceroPagador)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public Long registrarCargueArchivoTerceroPagador(ArchivoRetiroTerceroPagador cargueArchivoRetiro) {

        String firmaServicio = "ConsultasModeloCore.registrarCargueArchivoTerceroPagador(ArchivoRetiroTerceroPagador)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        try {
            entityManagerCore.persist(cargueArchivoRetiro);
            entityManagerCore.flush();
        } catch (Exception e2) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio + ": parámetros no válidos, unique constraint");
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_YA_ESTA_REGISTRADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);

        return cargueArchivoRetiro.getIdCargueArchivoRetTerPagSub();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#registrarCargueArchivoTerceroPagador(com.asopagos.entidades.subsidiomonetario.pagos.ArchivoRetiroTerceroPagador)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public Long registrarArchivoTerceroPagadorEfectivo(ArchivoRetiroTerceroPagadorEfectivo cargueArchivoRetiro) {

        String firmaServicio = "ConsultasModeloCore.registrarCargueArchivoTerceroPagador(ArchivoRetiroTerceroPagador)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        try {
            entityManagerCore.persist(cargueArchivoRetiro);
            entityManagerCore.flush();
        } catch (Exception e2) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio + ": parámetros no válidos, unique constraint");
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_YA_ESTA_REGISTRADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);

        return cargueArchivoRetiro.getIdArchivoRetiroTerceroPagadorEfectivo();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#registrarCargueArchivoTerceroPagador(com.asopagos.entidades.subsidiomonetario.pagos.ArchivoRetiroTerceroPagador)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public Long actualizarEstadoProcesadoArchivoTerceroPagadorEfectivo(Long id, EstadoArchivoConsumoTerceroPagadorEfectivo estado) {
        String firmaServicio = "ConsultasModeloCore.actualizarEstadoProcesadoArchivoTerceroPagadorEfectivo(cargueArchivoRetiro)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        ArchivoRetiroTerceroPagadorEfectivo convenioTerceroPagador = null;
        try {
            convenioTerceroPagador = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_TERCERO_PAGADOR_EFECTIVO_POR_ID, ArchivoRetiroTerceroPagadorEfectivo.class)
                    .setParameter("id", id).getSingleResult();

            convenioTerceroPagador.setEstadoArchivo(estado);
            convenioTerceroPagador.setFechaHoraProcesamiento(new Date());

            entityManagerCore.persist(convenioTerceroPagador);
            entityManagerCore.flush();
        } catch (Exception e2) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio + ": parámetros no válidos, unique constraint");
            //  throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_YA_ESTA_REGISTRADO);
            e2.printStackTrace();
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);

        return convenioTerceroPagador.getIdArchivoRetiroTerceroPagadorEfectivo();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#crearRegistrosCamposArchivoTerceroPagador(java.util.List,
     * java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void crearRegistrosCamposArchivoTerceroPagador(List<RetiroCandidatoDTO> retirosCandidatos, Long idCargueArchivoRetiro) {
        String firmaServicio = "ConsultasModeloCore.crearRegistrosCamposArchivoTerceroPagador(List<RetiroCandidatoDTO>, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        for (RetiroCandidatoDTO retiroCandidato : retirosCandidatos) {

            //se convierte la fecha en string
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = df.format(retiroCandidato.getFechaTransaccion());

            RegistroArchivoRetiroTerceroPagador registroArchivo = new RegistroArchivoRetiroTerceroPagador();

            //se insertan los campos del archivo en el registro para ser persistidos.
            registroArchivo.setIdArchivoRetiroTerceroPagador(idCargueArchivoRetiro);
            registroArchivo.setIdentificadorTransaccionTerceroPagador(retiroCandidato.getIdentificacionTransaccionTerceroPagador());
            registroArchivo.setTipoIdentificacionAdmonSubsidio(retiroCandidato.getTipoIdentificacionAdministradorSubsidio());
            registroArchivo.setNumeroIdentificacionAdmonSubsidio(retiroCandidato.getNumeroIdentificacionAdministradorSubsidio());
            registroArchivo.setValorRealTransaccion(retiroCandidato.getValorRealTransaccion());
            registroArchivo.setFechaTransaccion(fecha);
            registroArchivo.setHoraTransaccion(retiroCandidato.getHoraTransaccion());
            registroArchivo.setCodigoDepartamento(retiroCandidato.getDepartamento());
            registroArchivo.setCodigoMunicipio(retiroCandidato.getMunicipio());

            try {
                entityManagerCore.persist(registroArchivo);
                entityManagerCore.flush();
            } catch (Exception e2) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio + ": parámetros no válidos, unique constraint");
                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_YA_ESTA_REGISTRADO);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#crearSolicitudRegistroConvenio(com.asopagos .
     * subsidiomonetario.pagos.dto.ConvenioTercerPagadorDTO,
     *)
     */
    @Override
    public Long crearSolicitudRegistroConvenio(ConvenioTercerPagadorDTO convenioDTO) {

        String firmaServicio = "ConsultasModeloCore.crearSolicitudRegistroConvenio(ConvenioTercerPagadorDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ConvenioTercerPagadorDTO convenioTerceroPagador = consultarConvenioTerceroPagadorPorIdEmpresa(convenioDTO.getIdEmpresa());
        //si existe, se propaga el error.
        if (convenioTerceroPagador != null) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio + ": parámetros no válidos, el convenio ya se encuentra registrado.");
            //si el recurso ya se encuentra creado, se envia un cero a pantallas para que manejen la excepción
            return 0L;
        }

        ConvenioTercerPagadorDTO convenioTercoPagadorNombre = consultarConvenioTerceroPagadorPorNombre(convenioDTO.getNombreConvenio());
        //si existe el convenio con dicho nombre se propaga el error.
        if (convenioTercoPagadorNombre != null) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio + ": parámetros no válidos, ya existe un convenio creado con ese nombre.");
            //si el recurso ya se encuentra creado, se envia un menos uno a pantallas para que manejen la excepción
            return -1L;
        }
        //Se guarda la ubicación del convenio y se pone elid en la ubicacionModeloDTO
        Long idUbicacion = crearUbicacion(convenioDTO.getUbicacionModeloDTO());
        convenioDTO.getUbicacionModeloDTO().setIdUbicacion(idUbicacion);
        //se le asigna el estado del convenio
        convenioDTO.setEstadoConvenio(EstadoConvenioEnum.ACTIVO);
        //se persiste el convenio
        Long idConvenio = guardarConvenioTerceroPagador(convenioDTO);
        //si ingresaron documentos de soporte para el convenio, se realiza el registro
        if (convenioDTO.getListaDocumentosSoporte() != null && !convenioDTO.getListaDocumentosSoporte().isEmpty()) {
            //se crean los documentos de soporte del convenio
            registrarDocumentosSoporteConvenioTerceroPagador(convenioDTO.getListaDocumentosSoporte(), idConvenio);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return idConvenio;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#crearDetallesSubsidiosAsignados(java.util.List)
     */
    @Override
    public void crearDetallesSubsidiosAsignados(List<DetalleSubsidioAsignadoDTO> listaDetallesSubsidioAsignado) {

        String firmaServicio = "ConsultasModeloCore.crearDetallesSubsidiosAsignados(List<DetalleSubsidioAsignadoDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        for (DetalleSubsidioAsignadoDTO detalleDTO : listaDetallesSubsidioAsignado) {

            crearDetalleSubsidioAsignado(detalleDTO);
        }
        logger.error(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#crearCuentaAdministradorSubsidio(com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public Long crearCuentaAdministradorSubsidio(CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO) {

        String firmaServicio = "ConsultasModeloCore.crearCuentaAdministradorSubsidio(CuentaAdministradorSubsidioDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        CuentaAdministradorSubsidio cuentaAdministradorSubsidio = cuentaAdministradorSubsidioDTO.convertToEntity();

        try {
            if(!buscarExistenciaRetiroPorIdTransaccionTerceroPagadorRetiro(cuentaAdministradorSubsidioDTO.getIdTransaccionTerceroPagador(), 
                    cuentaAdministradorSubsidioDTO.getNombreTerceroPagador(), cuentaAdministradorSubsidioDTO.getIdPuntoDeCobro())) {
                        entityManagerCore.persist(cuentaAdministradorSubsidio);
            }else {
                logger.info("crearCuentaAdministradorSubsidio - No se pudo persistir el retiro, ya existe el idTransaccionTercerPagador");
                cuentaAdministradorSubsidio.setIdCuentaAdministradorSubsidio(0L);
            }

                //idTransaccionTercerPagador, usuario, idPuntoCobro)
            
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaServicio
                    + " Ocurrió un error  en la creación de la cuenta del administrador de subsidio", e);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.error(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return cuentaAdministradorSubsidio.getIdCuentaAdministradorSubsidio();
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#crearCuentaAdministradorSubsidio(com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public CuentaAdministradorSubsidio crearCuentaAdministradorSubsidioCuenta(CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO) {

        String firmaServicio = "ConsultasModeloCore.crearCuentaAdministradorSubsidio(CuentaAdministradorSubsidioDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        CuentaAdministradorSubsidio cuentaAdministradorSubsidio = cuentaAdministradorSubsidioDTO.convertToEntity();

        try {
            entityManagerCore.persist(cuentaAdministradorSubsidio);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaServicio
                    + " Ocurrió un error  en la creación de la cuenta del administrador de subsidio", e);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.error(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return cuentaAdministradorSubsidio;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarCuentaAdministradorSubsidio(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public CuentaAdministradorSubsidioDTO consultarCuentaAdministradorSubsidio(Long idCuentaAdmonSubsidio) {

        String firmaServicio = "ConsultasModeloCore.consultarCuentaAdministradorSubsidio(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO = null;

        try {
            cuentaAdministradorSubsidioDTO = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_ADMIN_SUBSIDIO_DTO_POR_ID,
                            CuentaAdministradorSubsidioDTO.class)
                    .setParameter("idCuentaAdmonSubsidio", idCuentaAdmonSubsidio).getSingleResult();

        } catch (NoResultException e) {
            cuentaAdministradorSubsidioDTO = null;
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return cuentaAdministradorSubsidioDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#actualizarCuentaAdministradorSubsidio(com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void actualizarCuentaAdministradorSubsidio(CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO) {

        String firmaServicio = "ConsultasModeloCore.actualizarCuentaAdministradorSubsidio(CuentaAdministradorSubsidioDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio + " con parámetro de entrada: " + cuentaAdministradorSubsidioDTO.toString());
        CuentaAdministradorSubsidio cuentaAdministradorSubsidio = null;
        // se consulta la entidad de descuento registrada en el sistema
        try {
            cuentaAdministradorSubsidio = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_ADMIN_SUBSIDIO_POR_ID, CuentaAdministradorSubsidio.class)
                    .setParameter("idCuentaAdmonSubsidio", cuentaAdministradorSubsidioDTO.getIdCuentaAdministradorSubsidio())
                    .getSingleResult();

        } catch (NoResultException e) {
            logger.error(
                    "Ocurrió un error inesperado, el id de la cuenta del administrador del subsidio no se encontro para ser actualizada.");
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        }

        if (cuentaAdministradorSubsidioDTO.getUsuarioUltimaModificacion() != null && cuentaAdministradorSubsidioDTO.getUsuarioUltimaModificacion().equals("Genesys")) {
            logger.debug("UsuarioUltimaModificacion = (Genesys)");
            //modificación cuando se realiza un reverso de un retiro
            cuentaAdministradorSubsidio.setEstadoTransaccionSubsidio(cuentaAdministradorSubsidioDTO.getEstadoTransaccion());
            cuentaAdministradorSubsidio
                    .setIdCuentaAdmonSubsidioRelacionado(cuentaAdministradorSubsidioDTO.getIdCuentaAdminSubsidioRelacionado());
            cuentaAdministradorSubsidio.setFechaHoraUltimaModificacion(cuentaAdministradorSubsidioDTO.getFechaHoraUltimaModificacion());
            cuentaAdministradorSubsidio.setUsuarioUltimaModificacion(cuentaAdministradorSubsidioDTO.getUsuarioUltimaModificacion());
            cuentaAdministradorSubsidio.setTipoTransaccionSubsidio(cuentaAdministradorSubsidioDTO.getTipoTransaccion());
            cuentaAdministradorSubsidio.setOrigenTransaccion(cuentaAdministradorSubsidioDTO.getOrigenTransaccion());
            cuentaAdministradorSubsidio.setFechaHoraTransaccion(cuentaAdministradorSubsidioDTO.getFechaHoraTransaccion());
//            cuentaAdministradorSubsidio.setNombreTerceroPagador(cuentaAdministradorSubsidioDTO.getNombreTerceroPagador());
        } else {
            logger.debug("UsuarioUltimaModificacion <> (Genesys)");
            cuentaAdministradorSubsidio.setEstadoTransaccionSubsidio(cuentaAdministradorSubsidioDTO.getEstadoTransaccion());
            cuentaAdministradorSubsidio.setFechaHoraUltimaModificacion(cuentaAdministradorSubsidioDTO.getFechaHoraUltimaModificacion());
            cuentaAdministradorSubsidio.setUsuarioUltimaModificacion(cuentaAdministradorSubsidioDTO.getUsuarioUltimaModificacion());
            logger.info("cuentaAdministradorSubsidio  " +cuentaAdministradorSubsidio.getIdCuentaAdministradorSubsidio());
            logger.info("cuentaAdministradorSubsidioDTO  " +cuentaAdministradorSubsidioDTO.getIdCuentaAdminSubsidioRelacionado());
            logger.info("cuentaAdministradorSubsidioDTO  2" +cuentaAdministradorSubsidioDTO.getEstadoTransaccion());
            cuentaAdministradorSubsidio.setIdCuentaAdmonSubsidioRelacionado(cuentaAdministradorSubsidioDTO.getIdCuentaAdminSubsidioRelacionado());
//            cuentaAdministradorSubsidio.setNombreTerceroPagador(cuentaAdministradorSubsidioDTO.getNombreTerceroPagador());

            if (cuentaAdministradorSubsidioDTO.getValorRealTransaccion() != null)
                logger.debug("ValorRealTransaccion <> null");
                logger.debug("ValorRealTransaccion: " + cuentaAdministradorSubsidioDTO.getValorRealTransaccion());
            cuentaAdministradorSubsidio.setValorRealTransaccion(cuentaAdministradorSubsidioDTO.getValorRealTransaccion());
            if (cuentaAdministradorSubsidioDTO.getIdSitioDeCobro() != null)
                cuentaAdministradorSubsidio.setIdSitioDeCobro(cuentaAdministradorSubsidioDTO.getIdSitioDeCobro());
        }

        logger.info("cuentaAdministradorSubsidio finn 1 " +cuentaAdministradorSubsidio.getIdCuentaAdmonSubsidioRelacionado());
        logger.info("cuentaAdministradorSubsidio finn 2 " +cuentaAdministradorSubsidio.getEstadoTransaccionSubsidio());
        logger.info("cuentaAdministradorSubsidio finn 3 " +cuentaAdministradorSubsidio.toString());
        cuentaAdministradorSubsidio.setFechaHoraUltimaModificacion(new Date());

        try {
            entityManagerCore.merge(cuentaAdministradorSubsidio);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#actualizarCuentaAdministradorSubsidio(com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void actualizarCuentaAdministradorSubsidioValor(CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO) {

        String firmaServicio = "ConsultasModeloCore.actualizarCuentaAdministradorSubsidio(CuentaAdministradorSubsidioDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        CuentaAdministradorSubsidio cuentaAdministradorSubsidio = null;
        // se consulta la entidad de descuento registrada en el sistema
        try {
            cuentaAdministradorSubsidio = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_ADMIN_SUBSIDIO_POR_ID, CuentaAdministradorSubsidio.class)
                    .setParameter("idCuentaAdmonSubsidio", cuentaAdministradorSubsidioDTO.getIdCuentaAdministradorSubsidio())
                    .getSingleResult();

        } catch (NoResultException e) {
            logger.error(
                    "Ocurrió un error inesperado, el id de la cuenta del administrador del subsidio no se encontro para ser actualizada.");
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        }

        if (cuentaAdministradorSubsidioDTO.getUsuarioUltimaModificacion().equals("Genesys")) {
            //modificación cuando se realiza un reverso de un retiro
            cuentaAdministradorSubsidio.setEstadoTransaccionSubsidio(cuentaAdministradorSubsidioDTO.getEstadoTransaccion());
            cuentaAdministradorSubsidio
                    .setIdCuentaAdmonSubsidioRelacionado(cuentaAdministradorSubsidioDTO.getIdCuentaAdminSubsidioRelacionado());
            cuentaAdministradorSubsidio.setFechaHoraUltimaModificacion(cuentaAdministradorSubsidioDTO.getFechaHoraUltimaModificacion());
            cuentaAdministradorSubsidio.setUsuarioUltimaModificacion(cuentaAdministradorSubsidioDTO.getUsuarioUltimaModificacion());
            cuentaAdministradorSubsidio.setTipoTransaccionSubsidio(cuentaAdministradorSubsidioDTO.getTipoTransaccion());
            cuentaAdministradorSubsidio.setOrigenTransaccion(cuentaAdministradorSubsidioDTO.getOrigenTransaccion());
            if (cuentaAdministradorSubsidioDTO.getValorRealTransaccion() != null)
                cuentaAdministradorSubsidio.setValorRealTransaccion(cuentaAdministradorSubsidioDTO.getValorRealTransaccion());

        } else {
            cuentaAdministradorSubsidio.setEstadoTransaccionSubsidio(cuentaAdministradorSubsidioDTO.getEstadoTransaccion());
            cuentaAdministradorSubsidio.setFechaHoraUltimaModificacion(cuentaAdministradorSubsidioDTO.getFechaHoraUltimaModificacion());
            cuentaAdministradorSubsidio.setUsuarioUltimaModificacion(cuentaAdministradorSubsidioDTO.getUsuarioUltimaModificacion());
            cuentaAdministradorSubsidio
                    .setIdCuentaAdmonSubsidioRelacionado(cuentaAdministradorSubsidioDTO.getIdCuentaAdminSubsidioRelacionado());

            if (cuentaAdministradorSubsidioDTO.getValorRealTransaccion() != null)
                cuentaAdministradorSubsidio.setValorRealTransaccion(cuentaAdministradorSubsidioDTO.getValorRealTransaccion());
            if (cuentaAdministradorSubsidioDTO.getIdSitioDeCobro() != null)
                cuentaAdministradorSubsidio.setIdSitioDeCobro(cuentaAdministradorSubsidioDTO.getIdSitioDeCobro());
        }

        try {
            entityManagerCore.merge(cuentaAdministradorSubsidio);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#actualizarDetalleSubsidioAsignado(com.asopagos.subsidiomonetario.pagos.dto.DetalleSubsidioAsignadoDTO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void actualizarDetalleSubsidioAsignado(DetalleSubsidioAsignadoDTO detalleSubsidioAsignadoDTO) {

        String firmaServicio = "ConsultasModeloCore.actualizarDetalleSubsidioAsignado(DetalleSubsidioAsignadoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        DetalleSubsidioAsignado detalleSubsidioAsignado = detalleSubsidioAsignadoDTO.convertToEntity();

        try {
            entityManagerCore.merge(detalleSubsidioAsignado);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#obtenerListaIdsDetallesSubsidioAsignado(java.util.List)
     */
    @Override
    public List<Long> obtenerListaIdsDetallesSubsidioAsignado(List<DetalleSubsidioAsignadoDTO> listaDetalles) {

        String firmaServicio = "ConsultasModeloCore.obtenerListaIdsDetallesSubsidioAsignado(List<DetalleSubsidioAsignadoDTO> )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<Long> listaIdsDetalles = new ArrayList<>();

        for (DetalleSubsidioAsignadoDTO detalle : listaDetalles) {
            listaIdsDetalles.add(detalle.getIdDetalleSubsidioAsignado());
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return listaIdsDetalles;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarDetallesSubsidioAsinadosNoAnulados(java.util.List,
     * java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesSubsidioAsinadosNoAnulados(List<Long> listaDetallesDTO,
                                                                                        Long idCuentaAdmonSubsidio) {

        String firmaServicio = "ConsultasModeloCore.consultarDetallesSubsidioAsinadosNoAnulados(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<DetalleSubsidioAsignadoDTO> listaDetallesSubsidioAsignado = null;

        try {
            listaDetallesSubsidioAsignado = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLES_SUBSIDIO_ASIGNADO_DTO_NO_ANULADOS,
                            DetalleSubsidioAsignadoDTO.class)
                    .setParameter("listaDetallesAnulados", listaDetallesDTO).setParameter("idCuentaAdmonSubsidio", idCuentaAdmonSubsidio)
                    .getResultList();

        } catch (NoResultException e) {
            listaDetallesSubsidioAsignado = null;
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return listaDetallesSubsidioAsignado;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#crearTransaccionFallida(com.asopagos.subsidiomonetario.pagos.dto.TransaccionFallidaDTO)
     */
    @Override
    public Long crearTransaccionFallida(TransaccionFallidaDTO transaccionFallidaDTO) {

        String firmaServicio = "ConsultasModeloCore.crearTransaccionFallida(TransaccionFallidaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        TransaccionesFallidasSubsidio transaccionFallida = transaccionFallidaDTO.convertToEntity();

        try {
            entityManagerCore.persist(transaccionFallida);

        } catch (Exception e1) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio + ": parámetros no válidos en la creación de la transacción fallida");
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_YA_ESTA_REGISTRADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return transaccionFallida.getIdTransaccionFallidaSubsidio();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#actualizarTransaccionFallida(com.asopagos.subsidiomonetario.pagos.dto.TransaccionFallidaDTO)
     */
    @Override
    public Long actualizarTransaccionFallida(TransaccionFallidaDTO transaccionFallidaDTO) {

        String firmaServicio = "ConsultasModeloCore.actualizarTransaccionFallida(TransaccionFallidaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        TransaccionesFallidasSubsidio transacionFallidaSubsidio = null;
        // se consulta la entidad de descuento registrada en el sistema
        try {
            transacionFallidaSubsidio = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_TRANSACCION_FALLIDA_SUBSIDIO_POR_ID,
                            TransaccionesFallidasSubsidio.class)
                    .setParameter("idTransaccion", transaccionFallidaDTO.getIdTransaccionesFallidasSubsidio()).getSingleResult();

        } catch (NoResultException e) {
            logger.error("Ocurrió un error inesperado, el id de la transacción fallida no se encontro para ser actualizada.");
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        }

        // se editan los campos  accionesRealizadasParaResolverCaso, resultadoGestion y estadoResolucion.
        transacionFallidaSubsidio.setAccionesRealizadasParaResolverCaso(transaccionFallidaDTO.getAccionesRealizadasParaResolverCaso());
        transacionFallidaSubsidio.setResultadoGestion(transaccionFallidaDTO.getResultadoGestion());
        transacionFallidaSubsidio.setEstadoResolucion(EstadoTransaccionFallidaEnum.CERRADO);

        try {
            entityManagerCore.merge(transacionFallidaSubsidio);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado al cerrar la transacción fallida del subsidio", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return transacionFallidaSubsidio.getIdTransaccionFallidaSubsidio();
    }

    private List<TransaccionFallidaDTO> ejecutarConsultarTransaccionesFallidasPorFechas(Date fechaRangoInicial, Date fechaRangoFinal) {
        String firmaServicio = "ConsultasModeloCore.consultarTransaccionesFallidasPorFechas(Date,Date)";
        List<TransaccionFallidaDTO> res = new ArrayList<>();

        try {
            logger.debug("Iniciando consulta de transacciones fallidas por fechas: " + firmaServicio);

            List<Object[]> outDtos = (List<Object[]>) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_TRANSACCIONES_FALLIDAS_DTO_POR_RANGO_DE_FECHAS)
                    .setParameter("fechaInicial", CalendarUtils.truncarHora(fechaRangoInicial))
                    .setParameter("fechaFinal", CalendarUtils.truncarHoraMaxima(fechaRangoFinal))
                    .getResultList();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (Object[] v : outDtos) {
                TransaccionFallidaDTO modelDtoTxfll = new TransaccionFallidaDTO();
                CuentaAdministradorSubsidioDTO mDtoAdm = new CuentaAdministradorSubsidioDTO();

                modelDtoTxfll.setIdTransaccionesFallidasSubsidio(parseLongValue(v[0]));
                modelDtoTxfll.setFechaHoraRegistro(parseDateValue(v[1], sdf));
                modelDtoTxfll.setCanal(parseEnumValue(v[2], CanalRecepcionEnum.class));
                modelDtoTxfll.setEstadoResolucion(parseEnumValue(v[3], EstadoTransaccionFallidaEnum.class));
                modelDtoTxfll.setResultadoGestion(parseEnumValue(v[4], ResultadoGestionTransaccionFallidaEnum.class));
                modelDtoTxfll.setAccionesRealizadasParaResolverCaso(parseStringValue(v[5]));
                modelDtoTxfll.setTipoTransaccionPagoSubsidio(parseEnumValue(v[6], TipoTransaccionSubsidioEnum.class));
                modelDtoTxfll.setIdCuentaAdmonSubsidio(parseLongValue(v[7]));

                if (v[8] != null && !v[8].toString().equals("")) {
                    mDtoAdm.setIdCuentaAdministradorSubsidio(parseLongValue(v[8]));
                    mDtoAdm.setFechaHoraCreacionRegistro(parseDateValue(v[9], sdf));
                    mDtoAdm.setUsuarioCreacionRegistro(parseStringValue(v[10]));
                    mDtoAdm.setTipoTransaccion(parseEnumValue(v[11], TipoTransaccionSubsidioMonetarioEnum.class));
                    mDtoAdm.setEstadoTransaccion(parseEnumValue(v[12], EstadoTransaccionSubsidioEnum.class));
                    mDtoAdm.setOrigenTransaccion(parseEnumValue(v[13], OrigenTransaccionEnum.class));
                    mDtoAdm.setMedioDePago(parseEnumValue(v[14], TipoMedioDePagoEnum.class));
                    //mDtoAdm.setValorOriginalTransaccion(parseBigDecimalValue(v[15]));
                    mDtoAdm.setCodigoBancoAdminSubsidio(parseStringValue(v[16]));
                    mDtoAdm.setNombreBancoAdminSubsidio(parseStringValue(v[17]));
                    mDtoAdm.setTipoCuentaAdminSubsidio(parseEnumValue(v[18], TipoCuentaEnum.class));
                    mDtoAdm.setNumeroCuentaAdminSubsidio(parseStringValue(v[19]));
                    mDtoAdm.setTipoIdentificacionTitularCuentaAdminSubsidio(parseEnumValue(v[20], TipoIdentificacionEnum.class));
                    mDtoAdm.setNumeroIdentificacionTitularCuentaAdminSubsidio(parseStringValue(v[21]));
                    mDtoAdm.setNombreTitularCuentaAdminSubsidio(parseStringValue(v[17]));
                    mDtoAdm.setValorRealTransaccion(parseBigDecimalValue(v[22]));
                    mDtoAdm.setFechaHoraTransaccion(parseDateValue(v[23], sdf));
                    mDtoAdm.setUsuarioTransaccionLiquidacion(parseStringValue(v[24]));
                    mDtoAdm.setIdCuentaAdminSubsidioRelacionado(parseLongValue(v[25]));
                    mDtoAdm.setFechaHoraUltimaModificacion(parseDateValue(v[26], sdf));
                    mDtoAdm.setUsuarioUltimaModificacion(parseStringValue(v[27]));
                    mDtoAdm.setIdAdministradorSubsidio(parseLongValue(v[28]));
                    mDtoAdm.setNombresApellidosAdminSubsidio(parseStringValue(v[17]));
                    mDtoAdm.setTipoIdAdminSubsidio(parseEnumValue(v[20], TipoIdentificacionEnum.class));
                    mDtoAdm.setNumeroIdAdminSubsidio(parseStringValue(v[21]));
                    mDtoAdm.setIdMedioDePago(parseLongValue(v[29]));
                    mDtoAdm.setIdEmpleador(parseLongValue(v[30]));
                    mDtoAdm.setIdAfiliadoPrincipal(parseLongValue(v[31]));
                    mDtoAdm.setIdBeneficiarioDetalle(parseLongValue(v[32]));
                    mDtoAdm.setIdGrupoFamiliar(parseLongValue(v[33]));
                    mDtoAdm.setSolicitudLiquidacionSubsidio(parseLongValue(v[34]));

                    modelDtoTxfll.setCuentaAdministradorSubsidioDTO(mDtoAdm);
                }

                res.add(modelDtoTxfll);
            }

            logger.info("Consulta de transacciones fallidas por fechas completada. Resultados: " + res.size());

        } catch (Exception e) {
            logger.error("Error durante la consulta de transacciones fallidas por fechas: " + firmaServicio, e);
            // Handle the exception or rethrow it based on your requirement.
        }

        return res.isEmpty() ? null : res;
    }

    // Helper methods for parsing values
    private BigDecimal parseBigDecimalValue(Object value) {
        if (value != null && !value.toString().equals("")) {
            try {
                return new BigDecimal(value.toString());
            } catch (NumberFormatException e) {
                // Handle the exception or log an error based on your requirement.
                return null;
            }
        }
        return null;
    }

    private Long parseLongValue(Object value) {
        if (value != null && !value.toString().equals("")) {
            return Long.parseLong(value.toString());
        }
        return null;
    }

    private Date parseDateValue(Object value, SimpleDateFormat sdf) throws ParseException {
        if (value != null && !value.toString().equals("")) {
            return sdf.parse(value.toString());
        }
        return null;
    }

    private <T extends Enum<T>> T parseEnumValue(Object value, Class<T> enumType) {
        if (value != null && !value.toString().equals("")) {
            return Enum.valueOf(enumType, value.toString());
        }
        return null;
    }

    private String parseStringValue(Object value) {
        if (value != null && !value.toString().equals("")) {
            return value.toString();
        }
        return null;
    }

    private List<TransaccionFallidaDTO> ejecutarConsultarTransaccionesFallidasPorFechas1(Date fechaRangoInicial, Date fechaRangoFinal) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String firmaServicio = "ConsultasModeloCore.consultarTransaccionesFallidasPorFechas(Date,Date)";
        final String primeraFecha = "fechaInicial";
        final String segundaFecha = "fechaFinal";
        List<TransaccionFallidaDTO> res = new ArrayList<>();
        List<Object[]> outDtos = (List<Object[]>) entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_TRANSACCIONES_FALLIDAS_DTO_POR_RANGO_DE_FECHAS)
                .setParameter(primeraFecha, CalendarUtils.truncarHora(fechaRangoInicial))
                .setParameter(segundaFecha, CalendarUtils.truncarHoraMaxima(fechaRangoFinal))
                .getResultList();

        //logger.info("Size -----------------------------------------------------//////######## => " + outDtos.length());
        for (Object[] v : outDtos) {
            try {
                TransaccionFallidaDTO modelDtoTxfll = new TransaccionFallidaDTO();
                CuentaAdministradorSubsidioDTO mDtoAdm = new CuentaAdministradorSubsidioDTO();
                modelDtoTxfll.setIdTransaccionesFallidasSubsidio(v[0] != null && !v[0].toString().equals("") ? Long.parseLong(v[0].toString()) : null);
                modelDtoTxfll.setFechaHoraRegistro(v[1] != null && !v[1].toString().equals("") ? sdf.parse(v[1].toString()) : null);
                modelDtoTxfll.setCanal(v[2] != null && !v[2].toString().equals("") ? CanalRecepcionEnum.valueOf(v[2].toString()) : null);
                modelDtoTxfll.setEstadoResolucion(v[3] != null && !v[3].toString().equals("") ? EstadoTransaccionFallidaEnum.valueOf(v[3].toString()) : null);
                modelDtoTxfll.setResultadoGestion(v[4] != null && !v[4].toString().equals("") ? ResultadoGestionTransaccionFallidaEnum.valueOf(v[4].toString()) : null);
                modelDtoTxfll.setAccionesRealizadasParaResolverCaso(v[5] != null && !v[5].toString().equals("") ? v[5].toString() : null);
                modelDtoTxfll.setTipoTransaccionPagoSubsidio(v[6] != null && !v[6].toString().equals("") ? TipoTransaccionSubsidioEnum.valueOf(v[6].toString()) : null);
                modelDtoTxfll.setIdCuentaAdmonSubsidio(v[7] != null && !v[7].toString().equals("") ? Long.parseLong(v[7].toString()) : null);
                if (v[8] != null && !v[8].toString().equals("")) {
                    mDtoAdm.setIdCuentaAdministradorSubsidio(v[8] != null && !v[8].toString().equals("") ? Long.parseLong(v[8].toString()) : null);
                    mDtoAdm.setFechaHoraCreacionRegistro(v[9] != null && !v[9].toString().equals("") ? sdf.parse(v[9].toString()) : null);
                    mDtoAdm.setUsuarioCreacionRegistro(v[10] != null && !v[10].toString().equals("") ? v[10].toString() : null);
                    mDtoAdm.setTipoTransaccion(v[11] != null && !v[11].toString().equals("") ? TipoTransaccionSubsidioMonetarioEnum.valueOf(v[11].toString()) : null);
                    mDtoAdm.setEstadoTransaccion(v[12] != null && !v[12].toString().equals("") ? EstadoTransaccionSubsidioEnum.valueOf(v[12].toString()) : null);
                    mDtoAdm.setOrigenTransaccion(v[13] != null && !v[13].toString().equals("") ? OrigenTransaccionEnum.valueOf(v[13].toString()) : null);
                    mDtoAdm.setMedioDePago(v[14] != null && !v[14].toString().equals("") ? TipoMedioDePagoEnum.valueOf(v[14].toString()) : null);
                    //mDtoAdm.setValorOriginalTransaccion(v[15] != null && !v[15].toString().equals("") ? new BigDecimal(v[15].toString()) : null);
                    mDtoAdm.setCodigoBancoAdminSubsidio(v[16] != null && !v[16].toString().equals("") ? v[16].toString() : null);
                    mDtoAdm.setNombreBancoAdminSubsidio(v[17] != null && !v[17].toString().equals("") ? v[17].toString() : null);
                    mDtoAdm.setTipoCuentaAdminSubsidio(v[18] != null && !v[18].toString().equals("") ? TipoCuentaEnum.valueOf(v[18].toString()) : null);
                    mDtoAdm.setNumeroCuentaAdminSubsidio(v[19] != null && !v[19].toString().equals("") ? v[19].toString() : null);
                    mDtoAdm.setTipoIdentificacionTitularCuentaAdminSubsidio(v[20] != null && !v[20].toString().equals("") ? TipoIdentificacionEnum.valueOf(v[20].toString()) : null);
                    mDtoAdm.setNumeroIdentificacionTitularCuentaAdminSubsidio(v[21] != null && !v[21].toString().equals("") ? v[21].toString() : null);
                    mDtoAdm.setNombreTitularCuentaAdminSubsidio(v[17] != null && !v[17].toString().equals("") ? v[17].toString() : null);
                    mDtoAdm.setValorRealTransaccion(v[22] != null && !v[22].toString().equals("") ? new BigDecimal(v[22].toString()) : null);
                    mDtoAdm.setFechaHoraTransaccion(v[23] != null && !v[23].toString().equals("") ? sdf.parse(v[23].toString()) : null);
                    mDtoAdm.setUsuarioTransaccionLiquidacion(v[24] != null && !v[24].toString().equals("") ? v[24].toString() : null);
                    mDtoAdm.setIdCuentaAdminSubsidioRelacionado(v[25] != null && !v[25].toString().equals("") ? Long.parseLong(v[25].toString()) : null);
                    mDtoAdm.setFechaHoraUltimaModificacion(v[26] != null && !v[26].toString().equals("") ? sdf.parse(v[26].toString()) : null);
                    mDtoAdm.setUsuarioUltimaModificacion(v[27] != null && !v[27].toString().equals("") ? v[27].toString() : null);
                    mDtoAdm.setIdAdministradorSubsidio(v[28] != null && !v[28].toString().equals("") ? Long.parseLong(v[28].toString()) : null);
                    mDtoAdm.setNombresApellidosAdminSubsidio(v[17] != null && !v[17].toString().equals("") ? v[17].toString() : null);
                    mDtoAdm.setTipoIdAdminSubsidio(v[20] != null && !v[20].toString().equals("") ? TipoIdentificacionEnum.valueOf(v[20].toString()) : null);
                    mDtoAdm.setNumeroIdAdminSubsidio(v[21] != null && !v[21].toString().equals("") ? v[21].toString() : null);
                    mDtoAdm.setIdMedioDePago(v[29] != null && !v[29].toString().equals("") ? Long.parseLong(v[29].toString()) : null);
                    mDtoAdm.setIdEmpleador(v[30] != null && !v[30].toString().equals("") ? Long.parseLong(v[30].toString()) : null);
                    mDtoAdm.setIdAfiliadoPrincipal(v[31] != null && !v[31].toString().equals("") ? Long.parseLong(v[31].toString()) : null);
                    mDtoAdm.setIdBeneficiarioDetalle(v[32] != null && !v[32].toString().equals("") ? Long.parseLong(v[32].toString()) : null);
                    mDtoAdm.setIdGrupoFamiliar(v[33] != null && !v[33].toString().equals("") ? Long.parseLong(v[33].toString()) : null);
                    mDtoAdm.setSolicitudLiquidacionSubsidio(v[34] != null && !v[34].toString().equals("") ? Long.parseLong(v[34].toString()) : null);
                    modelDtoTxfll.setCuentaAdministradorSubsidioDTO(mDtoAdm);
                }
                res.add(modelDtoTxfll);
            } catch (ParseException ex) {
                Logger.getLogger(ConsultasModeloCore.class.getName()).log(Level.SEVERE, null, ex);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }
        }
        logger.info("Finalizó transacción exitosa => " + res.size() + " ejecutarConsultarTransaccionesFallidasPorFechas");
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        res = res.isEmpty() ? null : res;
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return res;
    }

    /**
     * (non-Javadoc)
     * Nueva mejora del método en el cual se cambia de una consulta JPQL a Nativa por cuestiones de rendimiento. glpi 62810
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarTransaccionesFallidasPorFechas(java.util.Date,
     * java.util.Date) Weizman
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<TransaccionFallidaDTO> consultarTransaccionesFallidasPorFechas(Long fechaInicial, Long fechaFinal) {
        String firmaServicio = "ConsultasModeloCore.consultarTransaccionesFallidasPorFechas(Date,Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        if (fechaInicial != null && fechaFinal != null) {
            return ejecutarConsultarTransaccionesFallidasPorFechas(new Date(fechaInicial), new Date(fechaFinal));
        } else {
            Calendar fecha = Calendar.getInstance();
            Date fechaRangoFinal = fecha.getTime();
            Date fechaRangoInicial = CalendarUtils.restarDias(fechaRangoFinal, 5);
            return ejecutarConsultarTransaccionesFallidasPorFechas(fechaRangoInicial, fechaRangoFinal);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarTransaccionesFallidasPorFechas(java.util.Date,
     * java.util.Date)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<TransaccionFallidaDTO> consultarTransaccionesFallidasPorFechasDeprecated(Long fechaInicial, Long fechaFinal) {

        String firmaServicio = "ConsultasModeloCore.consultarTransaccionesFallidasPorFechas(Date,Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        final String primeraFecha = "fechaInicial";
        final String segundaFecha = "fechaFinal";

        List<TransaccionFallidaDTO> transaccionesFallidasDTO = null;

        if (fechaInicial != null && fechaFinal != null) {

            Date fechaRangoInicial = new Date(fechaInicial);
            Date fechaRangoFinal = new Date(fechaFinal);

            try {
                logger.info("Primer logg consulta");
                transaccionesFallidasDTO = entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_TRANSACCIONES_FALLIDAS_DTO_POR_RANGO_DE_FECHAS,
                                TransaccionFallidaDTO.class)
                        .setParameter(primeraFecha, CalendarUtils.truncarHora(fechaRangoInicial))
                        .setParameter(segundaFecha, CalendarUtils.truncarHoraMaxima(fechaRangoFinal)).getResultList();
                logger.info("Segundo logg consulta");

            } catch (Exception e) {
                logger.error("Ocurrió un error inesperado", e);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }

            if (transaccionesFallidasDTO.isEmpty()) {
                transaccionesFallidasDTO = null;
                logger.info("Tercer logg consulta");
            }

        } else {

            Calendar fecha = Calendar.getInstance();

            Date fechaRangoFinal = fecha.getTime();

            Date fechaRangoInicial = CalendarUtils.restarDias(fechaRangoFinal, 5);

            try {
                logger.info("Primer logg consulta segunda consulta");
                transaccionesFallidasDTO = entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_TRANSACCIONES_FALLIDAS_DTO_POR_RANGO_DE_FECHAS,
                                TransaccionFallidaDTO.class)
                        .setParameter(primeraFecha, CalendarUtils.truncarHora(fechaRangoInicial))
                        .setParameter(segundaFecha, CalendarUtils.truncarHoraMaxima(fechaRangoFinal)).getResultList();
                logger.info("Segundo logg consulta segunda consulta");
            } catch (Exception e) {
                logger.error("Ocurrió un error inesperado", e);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }
            logger.info("Tercer logg consulta segunda consulta");
            if (transaccionesFallidasDTO.isEmpty()) {
                transaccionesFallidasDTO = null;
            }

        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return transaccionesFallidasDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarDetallesSubsidiosAsignadosAsociadosAbono(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesSubsidiosAsignadosAsociadosAbono(Long idCuentaAdminSubsidio) {

        String firmaServicio = "ConsultasModeloCore.consultarDetallesSubsidiosAsignadosAsociadosAbono(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<DetalleSubsidioAsignadoDTO> listaDetalles = null;

        try {
            listaDetalles = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLES_SUBSIDIO_ASIGNADO_DTO_ASOCIADOS_CUENTA_ADMIN_SUBSIDIO,
                            DetalleSubsidioAsignadoDTO.class)
                    .setParameter("idCuentaAdmonSubsidio", idCuentaAdminSubsidio).getResultList();

        } catch (NoResultException e) {

            listaDetalles = new ArrayList<>();
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaDetalles;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarAbonosEnviadosMedioDePagoBancos()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarAbonosEnviadosMedioDePagoBancos() {

        String firmaServicio = "ConsultasModeloCore.consultarAbonosEnviadosMedioDePagoBancos()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<CuentaAdministradorSubsidioDTO> listaCuentasAdminSubsidios = null;

        try {

            listaCuentasAdminSubsidios = entityManagerCore.createNamedQuery(
                    NamedQueriesConstants.CONSULTAR_CUENTA_ADMIN_SUBSIDIO_POR_TIPO_ABONO_ESTADO_ENVIADO_MEDIO_DE_PAGO_BANCO,
                    CuentaAdministradorSubsidioDTO.class).getResultList();

        } catch (Exception e) {
            logger.debug("Ocurrio un error inesperado " + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return listaCuentasAdminSubsidios;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarAbonosEnviadosMedioDePagoBancos()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarAbonosEnviadosMedioDePagoBancosPaginado(UriInfo uriInfo,
                                                                                                 HttpServletResponse response) {

        String firmaServicio = "ConsultasModeloCore.consultarAbonosEnviadosMedioDePagoBancos()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        QueryBuilder queryBuilder = new QueryBuilder(entityManagerCore, uriInfo, response);
        queryBuilder.addOrderByDefaultParam("idCuentaAdministradorSubsidio");
        Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_CUENTA_ADMIN_SUBSIDIO_POR_TIPO_ABONO_ESTADO_ENVIADO_MEDIO_DE_PAGO_BANCO, null);

        List<CuentaAdministradorSubsidioDTO> listaCuentasAdminSubsidios = query.getResultList();

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return listaCuentasAdminSubsidios;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarTransacciones(com.asopagos.subsidiomonetario.pagos.dto.TransaccionConsultadaDTO)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarTransacciones(DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada) {

        String firmaServicio = "ConsultasModeloCore.consultarTransacciones(TransaccionConsultada)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<CuentaAdministradorSubsidioDTO> listaCuentasAdminSubsidios = new ArrayList<>();
        List<Object[]> lstCuentas = null;

        Date fechaRangoInicial = null;
        Date fechaRangoFinal = null;

        if (transaccionConsultada.getFechaInicio() == null && transaccionConsultada.getFechaFin() == null) {
            //se crea la fecha inicial con el rango inicial minimo permito 01/01/1990
            Long fechaInicio = new Long("631170000000");
            fechaRangoInicial = new Date(fechaInicio);
            fechaRangoFinal = new Date();
        } else {
            fechaRangoInicial = new Date(transaccionConsultada.getFechaInicio());
            fechaRangoFinal = new Date(transaccionConsultada.getFechaFin());
        }

        if (transaccionConsultada.getListaIdAdminSubsidios() != null && transaccionConsultada.getListaIdAdminSubsidios().isEmpty()) {
            transaccionConsultada.setListaIdAdminSubsidios(null);
        }

        //try {

        List<Long> sqlHack = new ArrayList<Long>();
        sqlHack.add(0L);

        lstCuentas = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_ADMIN_SUBSIDIO_DTO_TRANSACCIONES_POR_FILTROS)
                .setParameter("tipoTransaccion",
                        (transaccionConsultada.getTipoTransaccion() == null) ? null
                                : transaccionConsultada.getTipoTransaccion().name())
                .setParameter("estadoTransaccion",
                        (transaccionConsultada.getEstadoTransaccion() == null) ? null
                                : transaccionConsultada.getEstadoTransaccion().name())
                .setParameter("medioDePago", (transaccionConsultada.getMedioDePago() == null) ? null
                        : transaccionConsultada.getMedioDePago().name())
                .setParameter("fechaInicio", CalendarUtils.truncarHora(fechaRangoInicial))
                .setParameter("fechaFin", CalendarUtils.truncarHoraMaxima(fechaRangoFinal))
                .setParameter("listaIdAdminSubsidio", (transaccionConsultada.getListaIdAdminSubsidios() == null
                        || transaccionConsultada.getListaIdAdminSubsidios().isEmpty()) ? sqlHack : transaccionConsultada.getListaIdAdminSubsidios())
                .setParameter("sizeListaIdAdmin",
                        (transaccionConsultada.getListaIdAdminSubsidios() == null
                                || transaccionConsultada.getListaIdAdminSubsidios().isEmpty()) ? 0 : 1)

                .setParameter("listaidEmpleador", (transaccionConsultada.getListaIdEmpleadores() == null
                        || transaccionConsultada.getListaIdEmpleadores().isEmpty()) ? sqlHack : transaccionConsultada.getListaIdEmpleadores())
                .setParameter("sizeListaidEmpleador",
                        (transaccionConsultada.getListaIdEmpleadores() == null
                                || transaccionConsultada.getListaIdEmpleadores().isEmpty()) ? 0 : 1)

                .setParameter("listaidAfiliadoPrincipal", (transaccionConsultada.getListaIdAfiliadosPrincipales() == null
                        || transaccionConsultada.getListaIdAfiliadosPrincipales().isEmpty()) ? sqlHack : transaccionConsultada.getListaIdAfiliadosPrincipales())
                .setParameter("sizeListaidAfiliadoPrincipal",
                        (transaccionConsultada.getListaIdAfiliadosPrincipales() == null
                                || transaccionConsultada.getListaIdAfiliadosPrincipales().isEmpty()) ? 0 : 1)

                .setParameter("listaidBeneficiarioDetalle", (transaccionConsultada.getListaIdBeneficiarios() == null
                        || transaccionConsultada.getListaIdBeneficiarios().isEmpty()) ? sqlHack : transaccionConsultada.getListaIdBeneficiarios())
                .setParameter("sizeListaidBeneficiarioDetalle",
                        (transaccionConsultada.getListaIdBeneficiarios() == null
                                || transaccionConsultada.getListaIdBeneficiarios().isEmpty()) ? 0 : 1)

                .setParameter("listaidGrupoFamiliar", (transaccionConsultada.getListaIdGruposFamiliares() == null
                        || transaccionConsultada.getListaIdGruposFamiliares().isEmpty()) ? sqlHack : transaccionConsultada.getListaIdGruposFamiliares())
                .setParameter("sizeListaidGrupoFamiliar",
                        (transaccionConsultada.getListaIdGruposFamiliares() == null
                                || transaccionConsultada.getListaIdGruposFamiliares().isEmpty()) ? 0 : 1)
                .getResultList();

        for (Object[] result : lstCuentas) {

            CuentaAdministradorSubsidioDTO abono = new CuentaAdministradorSubsidioDTO();
            abono.setIdCuentaAdministradorSubsidio(Long.valueOf(result[0].toString()));
            if (result[1] != null)
                abono.setFechaHoraCreacionRegistro((Date) result[1]);
            if (result[2] != null)
                abono.setUsuarioCreacionRegistro(result[2].toString());
            if (result[3] != null)
                abono.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.valueOf(result[3].toString()));
            if (result[4] != null)
                abono.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.valueOf(result[4].toString()));
            if (result[6] != null)
                abono.setOrigenTransaccion(OrigenTransaccionEnum.valueOf(result[6].toString()));
            if (result[7] != null)
                abono.setMedioDePago(TipoMedioDePagoEnum.valueOf(result[7].toString()));
            if (result[8] != null)
                abono.setNumeroTarjetaAdminSubsidio(result[8].toString());
            if (result[9] != null)
                abono.setCodigoBancoAdminSubsidio(result[9].toString());
            if (result[10] != null)
                abono.setNombreBancoAdminSubsidio(result[10].toString());
            if (result[11] != null)
                abono.setTipoCuentaAdminSubsidio(TipoCuentaEnum.valueOf(result[11].toString()));
            if (result[12] != null)
                abono.setNumeroCuentaAdminSubsidio(result[12].toString());
            if (result[13] != null)
                abono.setTipoIdentificacionTitularCuentaAdminSubsidio(TipoIdentificacionEnum.valueOf(result[13].toString()));
            if (result[14] != null)
                abono.setNumeroIdentificacionTitularCuentaAdminSubsidio(result[14].toString());
            if (result[15] != null)
                abono.setNombreTitularCuentaAdminSubsidio(result[15].toString());
            if (result[16] != null)
                abono.setFechaHoraTransaccion((Date) result[16]);
            if (result[17] != null)
                abono.setUsuarioTransaccionLiquidacion(result[17].toString());
            if (result[18] != null)
                abono.setValorOriginalTransaccion(BigDecimal.valueOf(Double.parseDouble(result[18].toString())));
            if (result[19] != null)
                abono.setValorRealTransaccion(BigDecimal.valueOf(Double.parseDouble(result[19].toString())));
            if (result[20] != null)
                abono.setIdTransaccionOriginal(Long.parseLong(result[20].toString()));
            if (result[21] != null)
                abono.setIdRemisionDatosTerceroPagador(result[21].toString());
            if (result[22] != null)
                abono.setIdTransaccionTerceroPagador(result[22].toString());
            if (result[23] != null)
                abono.setNombreTerceroPagador(result[23].toString());
            if (result[24] != null)
                abono.setIdCuentaAdminSubsidioRelacionado(Long.parseLong(result[24].toString()));
            if (result[25] != null)
                abono.setFechaHoraUltimaModificacion((Date) result[25]);
            if (result[26] != null)
                abono.setUsuarioUltimaModificacion(result[26].toString());
            if (result[33] != null)
                abono.setNombreSitioPago(result[33].toString());
            if (result[34] != null)
                abono.setNombreSitioCobro(result[34].toString());
            if (result[35] != null)
                abono.setNombresApellidosAdminSubsidio(result[35].toString());
            if (result[36] != null)
                abono.setNombrePersonaAutorizada(result[36].toString());

            if ((abono.getIdTransaccionTerceroPagador() != null) && (abono.getIdRemisionDatosTerceroPagador() == null)) {
                abono.setIdTransaccionTerceroPagador(null);
                abono.setNombreTerceroPagador(null);
            }

            if (result[37] != null && result[38] != null) {
                abono.setTipoIdAdminSubsidio(TipoIdentificacionEnum.valueOf(result[37].toString()));
                abono.setNumeroIdAdminSubsidio(result[38].toString());
            }
            if (result[39] != null)
                abono.setIdEmpleador(Long.valueOf(result[39].toString()));
            if (result[40] != null)
                abono.setIdAfiliadoPrincipal(Long.valueOf(result[40].toString()));
            if (result[41] != null)
                abono.setIdBeneficiarioDetalle(Long.valueOf(result[41].toString()));
            if (result[42] != null)
                abono.setIdPuntoDeCobro(result[42].toString());

            listaCuentasAdminSubsidios.add(abono);
        }
                
/*
        } catch (Exception e) {
            logger.debug("Ocurrio un error inesperado " + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
  */
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return listaCuentasAdminSubsidios.isEmpty() ? null : listaCuentasAdminSubsidios;
    }

    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarTransaccionesPaginada(UriInfo uriInfo,
                                                                               HttpServletResponse response, DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada) {
        String firmaServicio = "ConsultasModeloCore.consultarTransacciones(TransaccionConsultada)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<CuentaAdministradorSubsidioDTO> listaCuentasAdminSubsidios = new ArrayList<>();
        List<Object[]> lstCuentas = null;
        Integer offset = Integer.parseInt(uriInfo.getQueryParameters().get(PaginationQueryParamsEnum.OFFSET.getValor()).get(0));
        Integer limit = Integer.parseInt(uriInfo.getQueryParameters().get(PaginationQueryParamsEnum.LIMIT.getValor()).get(0));
        Object totalRegistro = null;

        Date fechaRangoInicial = null;
        Date fechaRangoFinal = null;

        if (transaccionConsultada.getFechaInicio() == null && transaccionConsultada.getFechaFin() == null) {
            //se crea la fecha inicial con el rango inicial minimo permito 01/01/1990
            Long fechaInicio = new Long("631170000000");
            fechaRangoInicial = new Date(fechaInicio);
            fechaRangoFinal = new Date();
        } else {
            if (transaccionConsultada.getFechaInicio() != null) {
                fechaRangoInicial = new Date(transaccionConsultada.getFechaInicio());
            } else {
                Long fechaInicio = new Long("631170000000");
                fechaRangoInicial = new Date(fechaInicio);
            }
            if (transaccionConsultada.getFechaFin() != null) {
                fechaRangoFinal = new Date(transaccionConsultada.getFechaFin());
            }
        }
        List<Long> sqlHack = new ArrayList<Long>();
        sqlHack.add(0L);

        // QueryBuilder queryBuilder = new QueryBuilder(entityManagerCore, uriInfo, response);
        // //Se consultan las novedades registradas a la persona.
        // queryBuilder.addParam("tipoTransaccion", (transaccionConsultada.getTipoTransaccion() == null) ? null
        //         : transaccionConsultada.getTipoTransaccion().name());
        // queryBuilder.addParam("estadoTransaccion", (transaccionConsultada.getEstadoTransaccion() == null) ? null
        //         : transaccionConsultada.getEstadoTransaccion().name());
        // queryBuilder.addParam("medioDePago", (transaccionConsultada.getMedioDePago() == null) ? null
        //         : transaccionConsultada.getMedioDePago().name());
        // queryBuilder.addParam("fechaInicio", CalendarUtils.truncarHora(fechaRangoInicial));
        // queryBuilder.addParam("fechaFin", CalendarUtils.truncarHoraMaxima(fechaRangoFinal));
        // queryBuilder.addParam("listaIdAdminSubsidio", (transaccionConsultada.getListaIdAdminSubsidios() == null
        //         || transaccionConsultada.getListaIdAdminSubsidios().isEmpty()) ? sqlHack : transaccionConsultada.getListaIdAdminSubsidios());
        // queryBuilder.addParam("sizeListaIdAdmin", (transaccionConsultada.getListaIdAdminSubsidios() == null
        //         || transaccionConsultada.getListaIdAdminSubsidios().isEmpty()) ? 0 : 1);
        // Map<String, String> hints = new HashMap<String, String>();

        // hints.put("idCuentaAdministradorSubsidio", "cas.casId");
        // hints.put("fechaHoraCreacionRegistro", "cas.casFechaHoraCreacionRegistro");
        // hints.put("usuarioCreacionRegistro", "cas.casUsuarioCreacionRegistro");
        // hints.put("tipoTransaccion", "cas.casTipoTransaccionSubsidio");
        // hints.put("estadoTransaccion", "cas.casEstadoTransaccionSubsidio");
        // hints.put("origenTransaccion", "cas.casOrigenTransaccion");
        // hints.put("medioDePago", "cas.casMedioDePagoTransaccion");
        // hints.put("numeroTarjetaAdminSubsidio", "cas.casNumeroTarjetaAdmonSubsidio");
        // hints.put("codigoBancoAdminSubsidio", "cas.casCodigoBanco");
        // hints.put("nombreBancoAdminSubsidio", "cas.casNombreBanco");
        // hints.put("tipoCuentaAdminSubsidio", "cas.casTipoCuentaAdmonSubsidio");
        // hints.put("numeroCuentaAdminSubsidio", "cas.casNumeroCuentaAdmonSubsidio");
        // hints.put("tipoIdentificacionTitularCuentaAdminSubsidio", "cas.casTipoIdentificacionTitularCuentaAdmonSubsidio");
        // hints.put("numeroIdentificacionTitularCuentaAdminSubsidio", "cas.casNumeroIdentificacionTitularCuentaAdmonSubsidio");
        // hints.put("nombreTitularCuentaAdminSubsidio", "cas.casNombreTitularCuentaAdmonSubsidio");
        // hints.put("fechaHoraTransaccion", "cas.casFechaHoraTransaccion");
        // hints.put("usuarioTransaccionLiquidacion", "cas.casUsuarioTransaccion");
        // hints.put("valorOriginalTransaccion", "cas.casValorOriginalTransaccion");
        // hints.put("valorRealTransaccion", "cas.casValorRealTransaccion");
        // hints.put("idTransaccionOriginal", "cas.casIdTransaccionOriginal");
        // hints.put("idRemisionDatosTerceroPagador", "cas.casIdRemisionDatosTerceroPagador");
        // hints.put("idTransaccionTerceroPagador", "cas.casIdTransaccionTerceroPagador");
        // hints.put("nombreTerceroPagador", "cas.casNombreTerceroPagado");
        // hints.put("idCuentaAdminSubsidioRelacionado", "cas.casIdCuentaAdmonSubsidioRelacionado");
        // hints.put("fechaHoraUltimaModificacion", "cas.casFechaHoraUltimaModificacion");
        // hints.put("usuarioUltimaModificacion", "cas.casUsuarioUltimaModificacion");
        // hints.put("tipoIdAdminSubsidio", "adminPer.tipoIdAdminSubsidio");
        // hints.put("numeroIdAdminSubsidio", "numIdAdminSubsidio");
        // hints.put("nombresApellidosAdminSubsidio", "adminSubsidio");
        // hints.put("nombreSitioCobro", "nombreSitioCobro");
        // hints.put("idPuntoDeCobro", "casIdPuntoDeCobro");
        // hints.put("nombreSitioPago", "nombreSitioPago");
        // hints.put("nombrePersonaAutorizada", "personaAutorizada");
        // queryBuilder.setHints(hints);

        String queryString = " ;with cuentaAdministrador as (SELECT cas.casId,  \n " +
        " cas.casFechaHoraCreacionRegistro ,  \n " +
        " cas.casUsuarioCreacionRegistro ,  \n " +
        " cas.casTipoTransaccionSubsidio ,  \n " +
        " cas.casEstadoTransaccionSubsidio ,  \n " +
        " cas.casOrigenTransaccion ,  \n " +
        " cas.casMedioDePagoTransaccion ,  \n " +
        " cas.casNumeroTarjetaAdmonSubsidio ,  \n " +
        " cas.casCodigoBanco ,  \n " +
        " cas.casNombreBanco ,  \n " +
        " cas.casTipoCuentaAdmonSubsidio ,  \n " +
        " cas.casNumeroCuentaAdmonSubsidio ,  \n " +
        " cas.casTipoIdentificacionTitularCuentaAdmonSubsidio ,  \n " +
        " cas.casNumeroIdentificacionTitularCuentaAdmonSubsidio ,  \n " +
        " cas.casNombreTitularCuentaAdmonSubsidio ,  \n " +
        " cas.casFechaHoraTransaccion ,  \n " +
        " cas.casUsuarioTransaccion ,  \n " +
        " cas.casValorOriginalTransaccion ,  \n " +
        " cas.casValorRealTransaccion ,  \n " +
        " cas.casIdTransaccionOriginal ,  \n " +
        " cas.casIdRemisionDatosTerceroPagador ,  \n " +
        " cas.casIdTransaccionTerceroPagador ,  \n " +
        " cas.casIdCuentaAdmonSubsidioRelacionado ,  \n " +
        " cas.casFechaHoraUltimaModificacion ,  \n " +
        " cas.casUsuarioUltimaModificacion ,  \n " +
		"cas.casSitioDePago,  \n " +
		"cas.casSitioDeCobro,  \n " +
		"cas.casNombreTerceroPagado,  \n " +
		"cas.casIdPuntoDeCobro,  \n " +
		"cas.casAdministradorSubsidio  \n " +
"FROM dbo.CuentaAdministradorSubsidio as cas  \n " +
"where (cas.casFechaHoraTransaccion BETWEEN :fechaInicio AND :fechaFin))  \n " +
"select distinct  \n " +
                "cas.casid,\n " +
                "cas.casFechaHoraCreacionRegistro,\n " +
                "casUsuarioCreacionRegistro ,\n " +
                "casTipoTransaccionSubsidio ,\n " +
                "casEstadoTransaccionSubsidio ,\n " +
                "casOrigenTransaccion,\n " +
                "casMedioDePagoTransaccion,\n " +
                "casNumeroTarjetaAdmonSubsidio ,\n " +
                "casCodigoBanco ,\n " +
                "casNombreBanco ,\n " +
                "casTipoCuentaAdmonSubsidio ,\n " +
                "casNumeroCuentaAdmonSubsidio ,\n " +
                "casTipoIdentificacionTitularCuentaAdmonSubsidio ,\n " +
                "casNumeroIdentificacionTitularCuentaAdmonSubsidio ,\n " +
                "casNombreTitularCuentaAdmonSubsidio ,\n " +
                "casFechaHoraTransaccion,\n " +
                "casUsuarioTransaccion,\n " +
                "casValorOriginalTransaccion ,\n " +
                "casValorRealTransaccion ,\n " +
                "casIdTransaccionOriginal ,\n " +
                "casIdRemisionDatosTerceroPagador,\n " +
                "casIdTransaccionTerceroPagador ,\n " +
                "casNombreTerceroPagado ,\n " +
                "casIdCuentaAdmonSubsidioRelacionado ,\n " +
                "casFechaHoraUltimaModificacion ,\n " +
                "casUsuarioUltimaModificacion,\n " +
                "adminPer.perTipoIdentificacion AS tipoIdAdminSubsidio,\n " +
                "adminPer.perNumeroIdentificacion AS numIdAdminSubsidio,\n " +
                "adminPer.perRazonSocial AS adminSubsidio,\n " +
                "CASE\n " +
                "WHEN cas.casSitioDeCobro IS NOT NULL\n " +
                "THEN (\n " +
                "SELECT CONCAT(Departamento.depNombre, ', ',Municipio.munNombre)\n " +
                "FROM dbo.SitioPago\n " +
                "INNER JOIN Infraestructura ON SitioPago.sipInfraestructura = Infraestructura.infId\n " +
                "INNER JOIN Municipio ON Municipio.munId = Infraestructura.infMunicipio\n " +
                "INNER JOIN Departamento ON Departamento.depId = Municipio.munDepartamento\n " +
                "WHERE SitioPago.sipId = cas.casSitioDeCobro\n " +
                ")\n " +
                "	ELSE NULL\n " +
                "END AS nombreSitioCobro,\n " +
                "casIdPuntoDeCobro,\n " +
                "			\n " +
                "CASE\n " +
                "	WHEN cas.casSitioDePago IS NOT NULL\n " +
                "	THEN (\n " +
                "			SELECT SitioPago.sipNombre\n " +
                "			FROM SitioPago\n " +
                "			WHERE SitioPago.sipId = cas.casSitioDePago\n " +
                "			)\n " +
                "	ELSE NULL\n " +
                "END AS nombreSitioPago,\n " +
                "personaAutorizada.perRazonSocial AS personaAutorizada,\n " +
                "cas.casNombreTerceroPagado as establecimientoCodigo,\n " +
                "est.estNombre AS establecimientoNombre,\n " +
                "rac.racFechaTransaccion as fechaTransaccionConsumo\n " +
                "from cuentaAdministrador as cas\n " +
                "LEFT JOIN DetalleSubsidioAsignado as dsa on cas.casid= dsa.dsaCuentaAdministradorSubsidio\n " +
                "INNER JOIN AdministradorSubsidio AS admin ON admin.asuId = cas.casAdministradorSubsidio\n " +
                "INNER JOIN Persona AS adminPer ON adminPer.perId =  admin.asuPersona\n " +
                "LEFT JOIN RetiroPersonaAutorizadaCobroSubsidio AS retPer ON cas.casId = retPer.rpaCuentaAdministradorSubsidio\n " +
                "LEFT JOIN Persona AS personaAutorizada ON personaAutorizada.perId = retPer.rpaPersonaAutorizada\n " +
                "LEFT JOIN EstablecimientosMediosPago AS est ON est.estCodigo = cas.casNombreTerceroPagado\n " +
                "LEFT JOIN RegistroArchivoConsumosAnibol AS rac on rac.racCodigoAutorizacion = cas.casIdTransaccionTerceroPagador\n " +
                "where (:estadoTransaccion IS NULL OR cas.casEstadoTransaccionSubsidio = :estadoTransaccion) AND (:tipoTransaccion IS NULL OR cas.casTipoTransaccionSubsidio = :tipoTransaccion) \n " +
                "AND (:medioDePago IS NULL OR cas.casMedioDePagoTransaccion = :medioDePago)\n " +
                "AND (:sizeListaIdAdmin = 0 OR dsa.dsaAdministradorSubsidio IN (:listaIdAdminSubsidio)\n " +
                "OR cas.casAdministradorSubsidio IN (:listaIdAdminSubsidio))\n "+
                "ORDER BY 1 OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY";
        Query query = entityManagerCore.createNativeQuery(queryString);
        query.setParameter("tipoTransaccion", (transaccionConsultada.getTipoTransaccion() == null) ? null
                : transaccionConsultada.getTipoTransaccion().name());
        query.setParameter("estadoTransaccion", (transaccionConsultada.getEstadoTransaccion() == null) ? null
                : transaccionConsultada.getEstadoTransaccion().name());
        query.setParameter("medioDePago", (transaccionConsultada.getMedioDePago() == null) ? null
                : transaccionConsultada.getMedioDePago().name());
        query.setParameter("fechaInicio", CalendarUtils.truncarHora(fechaRangoInicial));
        query.setParameter("fechaFin", CalendarUtils.truncarHoraMaxima(fechaRangoFinal));
        query.setParameter("listaIdAdminSubsidio", (transaccionConsultada.getListaIdAdminSubsidios() == null
                || transaccionConsultada.getListaIdAdminSubsidios().isEmpty()) ? sqlHack : transaccionConsultada.getListaIdAdminSubsidios());
        query.setParameter("sizeListaIdAdmin", (transaccionConsultada.getListaIdAdminSubsidios() == null
                || transaccionConsultada.getListaIdAdminSubsidios().isEmpty()) ? 0 : 1);
        query.setParameter("offset", offset);
        query.setParameter("limit", limit);
        Query query2 = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_ADMIN_SUBSIDIO_PAGINADA_COUNT);
        query2.setParameter("tipoTransaccion", (transaccionConsultada.getTipoTransaccion() == null) ? null
                : transaccionConsultada.getTipoTransaccion().name());
                query2.setParameter("estadoTransaccion", (transaccionConsultada.getEstadoTransaccion() == null) ? null
                : transaccionConsultada.getEstadoTransaccion().name());
                query2.setParameter("medioDePago", (transaccionConsultada.getMedioDePago() == null) ? null
                : transaccionConsultada.getMedioDePago().name());
                query2.setParameter("fechaInicio", CalendarUtils.truncarHora(fechaRangoInicial));
        query2.setParameter("fechaFin", CalendarUtils.truncarHoraMaxima(fechaRangoFinal));
        query2.setParameter("listaIdAdminSubsidio", (transaccionConsultada.getListaIdAdminSubsidios() == null
                || transaccionConsultada.getListaIdAdminSubsidios().isEmpty()) ? sqlHack : transaccionConsultada.getListaIdAdminSubsidios());
                query2.setParameter("sizeListaIdAdmin", (transaccionConsultada.getListaIdAdminSubsidios() == null
                || transaccionConsultada.getListaIdAdminSubsidios().isEmpty()) ? 0 : 1);
        lstCuentas = query.getResultList();
        
        totalRegistro = query2.getSingleResult();
        for (Object[] result : lstCuentas) {

            CuentaAdministradorSubsidioDTO abono = new CuentaAdministradorSubsidioDTO();
            abono.setIdCuentaAdministradorSubsidio(Long.valueOf(result[0].toString()));
            if (result[1] != null)
                abono.setFechaHoraCreacionRegistro((Date) result[1]);
            if (result[2] != null)
                abono.setUsuarioCreacionRegistro(result[2].toString());
            if (result[3] != null)
                abono.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.valueOf(result[3].toString()));
            if (result[4] != null)
                abono.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.valueOf(result[4].toString()));
            if (result[5] != null)
                abono.setOrigenTransaccion(OrigenTransaccionEnum.valueOf(result[5].toString()));
            if (result[6] != null)
                abono.setMedioDePago(TipoMedioDePagoEnum.valueOf(result[6].toString()));
            if (result[7] != null)
                abono.setNumeroTarjetaAdminSubsidio(result[7].toString());
            if (result[8] != null)
                abono.setCodigoBancoAdminSubsidio(result[8].toString());
            if (result[9] != null)
                abono.setNombreBancoAdminSubsidio(result[9].toString());
            if (result[10] != null)
                abono.setTipoCuentaAdminSubsidio(TipoCuentaEnum.valueOf(result[10].toString()));
            if (result[11] != null)
                abono.setNumeroCuentaAdminSubsidio(result[11].toString());
            if (result[12] != null)
                abono.setTipoIdentificacionTitularCuentaAdminSubsidio(TipoIdentificacionEnum.valueOf(result[12].toString()));
            if (result[13] != null)
                abono.setNumeroIdentificacionTitularCuentaAdminSubsidio(result[13].toString());
            if (result[14] != null)
                abono.setNombreTitularCuentaAdminSubsidio(result[14].toString());
            if (result[15] != null)
                abono.setFechaHoraTransaccion((Date) result[15]);
            if (result[16] != null)
                abono.setUsuarioTransaccionLiquidacion(result[16].toString());
            if (result[17] != null)
                abono.setValorOriginalTransaccion(BigDecimal.valueOf(Double.parseDouble(result[17].toString())));
            if (result[18] != null)
                abono.setValorRealTransaccion(BigDecimal.valueOf(Double.parseDouble(result[18].toString())));
            if (result[19] != null)
                abono.setIdTransaccionOriginal(Long.parseLong(result[19].toString()));
            if (result[20] != null)
                abono.setIdRemisionDatosTerceroPagador(result[20].toString());
            if (result[21] != null)
                abono.setIdTransaccionTerceroPagador(result[21].toString());
            if (result[22] != null)
                abono.setNombreTerceroPagador(result[22].toString());
            if (result[23] != null)
                abono.setIdCuentaAdminSubsidioRelacionado(Long.parseLong(result[23].toString()));
            if (result[24] != null)
                abono.setFechaHoraUltimaModificacion((Date) result[24]);
            if (result[25] != null)
                abono.setUsuarioUltimaModificacion(result[25].toString());
            if (result[26] != null && result[27] != null) {
                abono.setTipoIdAdminSubsidio(TipoIdentificacionEnum.valueOf(result[26].toString()));
                abono.setNumeroIdAdminSubsidio(result[27].toString());
            }
            if (result[28] != null)
                abono.setNombresApellidosAdminSubsidio(result[28].toString());
            if (result[29] != null)
                abono.setNombreSitioCobro(result[29].toString());
            if (result[30] != null)
                abono.setIdPuntoDeCobro(result[30].toString());
            if (result[31] != null)
                abono.setNombreSitioPago(result[31].toString());
            if (result[32] != null)
                abono.setNombrePersonaAutorizada(result[32].toString());
            if ((abono.getIdTransaccionTerceroPagador() != null) && (abono.getIdRemisionDatosTerceroPagador() == null && abono.getNombreTerceroPagador() == null)) {
                abono.setIdTransaccionTerceroPagador(null);
                abono.setNombreTerceroPagador(null);
            }
            if (result[33] != null)
                abono.setEstablecimientoCodigo(result[33].toString());
            if (result[34] != null)
                abono.setEstablecimientoNombre(result[34].toString());
            if (result[35] != null)
                abono.setFechaTransaccionConsumo(result[35].toString());
            abono.setTotalRegistro(totalRegistro.toString());
            listaCuentasAdminSubsidios.add(abono);
        }
        logger.info("**__**FINALIZA SERVICIO consultarTransaccionesPaginada");
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return listaCuentasAdminSubsidios.isEmpty() ? null : listaCuentasAdminSubsidios;
    }

    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<Object[]> consultarTransaccionesSP(UriInfo uriInfo,
                                                   HttpServletResponse response, DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada, Integer offSetParametro) {

        String firmaServicio = "ConsultasModeloCore.consultarTransacciones(TransaccionConsultada)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<Object[]> lstCuentas = null;

        Date fechaRangoInicial = null;
        Date fechaRangoFinal = null;

        if (transaccionConsultada.getFechaInicio() == null && transaccionConsultada.getFechaFin() == null) {
            //se crea la fecha inicial con el rango inicial minimo permito 01/01/1990
            Long fechaInicio = new Long("631170000000");
            fechaRangoInicial = new Date(fechaInicio);
            fechaRangoFinal = new Date();
        } else {
            if (transaccionConsultada.getFechaInicio() != null) {
                fechaRangoInicial = new Date(transaccionConsultada.getFechaInicio());
            } else {
                Long fechaInicio = new Long("631170000000");
                fechaRangoInicial = new Date(fechaInicio);
            }
            if (transaccionConsultada.getFechaFin() != null) {
                fechaRangoFinal = new Date(transaccionConsultada.getFechaFin());
            }
        }
        StringBuilder idAdminSubsidioStr = new StringBuilder();
        int count = 0;
        if (transaccionConsultada.getListaIdAdminSubsidios() != null && !transaccionConsultada.getListaIdAdminSubsidios().isEmpty()) {
            for (Long idAdminSubsidio : transaccionConsultada.getListaIdAdminSubsidios()) {
                idAdminSubsidioStr.append(idAdminSubsidio);
                if (count < transaccionConsultada.getListaIdAdminSubsidios().size()) {
                    idAdminSubsidioStr.append(",");
                }
                count++;
            }
        }
        lstCuentas = entityManagerCore.createStoredProcedureQuery(NamedQueriesConstants.USP_PG_GET_CONSULTARTRANSACCIONESSUBSIDIO)
                .registerStoredProcedureParameter("estadoTransaccion", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("medioDePago", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("tipoTransaccion", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("fechaInicio", Date.class, ParameterMode.IN)
                .registerStoredProcedureParameter("fechaFin", Date.class, ParameterMode.IN)
                .registerStoredProcedureParameter("idsAminSubsidio", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("offsetParametro", Integer.class, ParameterMode.IN)
                .setParameter("estadoTransaccion", transaccionConsultada.getEstadoTransaccion() != null ? transaccionConsultada.getEstadoTransaccion().name() : "")
                .setParameter("medioDePago", transaccionConsultada.getMedioDePago() != null ? transaccionConsultada.getMedioDePago().name() : "")
                .setParameter("tipoTransaccion", transaccionConsultada.getTipoTransaccion() != null ? transaccionConsultada.getTipoTransaccion().name() : "")
                .setParameter("fechaInicio", fechaRangoInicial)
                .setParameter("fechaFin", fechaRangoFinal)
                .setParameter("idsAminSubsidio", idAdminSubsidioStr.toString())
                .setParameter("offsetParametro", offSetParametro).getResultList();
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return lstCuentas.isEmpty() ? null : lstCuentas;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarDetalles(com.asopagos.subsidiomonetario.pagos.dto.DetalleTransaccionAsignadoConsultadoDTO)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<DetalleSubsidioAsignadoDTO> consultarDetalles(DetalleTransaccionAsignadoConsultadoDTO detalleConsultado) {

        String firmaServicio = "ConsultasModeloCore.consultarDetalles(DetalleTransaccionAsignadoConsultadoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        //se obtienen los detalles que cumplen con los filtros.
        List<DetalleSubsidioAsignadoDTO> listaDetalles = consultarDetallesPorFiltros(detalleConsultado);

        return (listaDetalles != null && listaDetalles.isEmpty()) ? null : listaDetalles;
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarDetalles(com.asopagos.subsidiomonetario.pagos.dto.DetalleTransaccionAsignadoConsultadoDTO)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesCuentas(DetalleTransaccionAsignadoConsultadoDTO detalleConsultado, List<CuentaAdministradorSubsidioDTO> listaCuentas) {

        String firmaServicio = "ConsultasModeloCore.consultarDetalles(DetalleTransaccionAsignadoConsultadoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        //se obtienen los detalles que cumplen con los filtros.
        List<DetalleSubsidioAsignadoDTO> listaDetalles = consultarDetallesPorFiltros(detalleConsultado);

        List<DetalleSubsidioAsignadoDTO> listaDetallesRespuesta = new ArrayList<>();

        if (listaDetalles != null && listaCuentas != null) {
            for (CuentaAdministradorSubsidioDTO cuenta : listaCuentas) {
                for (DetalleSubsidioAsignadoDTO detalle : listaDetalles) {
                    //si el detalle filtrado pertenece a la cuenta filtrada, ese detalle es uno de los seleccionados en la busqueda.
                    if (detalle.getIdCuentaAdministradorSubsidio().equals(cuenta.getIdCuentaAdministradorSubsidio())) {

                        detalle.setMedioDePago(cuenta.getMedioDePago());
                        detalle.setNumeroTarjetaAdminSubsidio(cuenta.getNumeroTarjetaAdminSubsidio());
                        detalle.setCodigoBancoAdminSubsidio(cuenta.getCodigoBancoAdminSubsidio());
                        detalle.setNombreBancoAdminSubsidio(cuenta.getNombreBancoAdminSubsidio());
                        detalle.setTipoCuentaAdminSubsidio(cuenta.getTipoCuentaAdminSubsidio());
                        detalle.setNumeroCuentaAdminSubsidio(cuenta.getNumeroCuentaAdminSubsidio());
                        detalle.setTipoIdentificacionTitularCuentaAdminSubsidio(cuenta.getTipoIdentificacionTitularCuentaAdminSubsidio());
                        detalle.setNumeroIdentificacionTitularCuentaAdminSubsidio(cuenta.getNumeroIdentificacionTitularCuentaAdminSubsidio());
                        detalle.setNombreTitularCuentaAdminSubsidio(cuenta.getNombreTitularCuentaAdminSubsidio());
                        listaDetallesRespuesta.add(detalle);

                        //break;
                    }
                }
            }

            // if(!listaDetallesRespuesta.isEmpty())
            //     obtenerDatosFaltantesDetallesSubsidioMonetarioPorIdDetalle(listaDetallesRespuesta);
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return listaDetallesRespuesta.isEmpty() ? null : listaDetallesRespuesta;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarTransaccionesDetalles(com.asopagos.subsidiomonetario.pagos.dto.DetalleTransaccionAsignadoConsultadoDTO)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarTransaccionesDetalles(
            DetalleTransaccionAsignadoConsultadoDTO transaccionDetalleSubsidio) {
        String firmaServicio = "ConsultasModeloCore.consultarTransaccionesDetalles(DetalleTransaccionAsignadoConsultadoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        if (transaccionDetalleSubsidio == null)
            transaccionDetalleSubsidio = new DetalleTransaccionAsignadoConsultadoDTO();
        //se obtienen las cuentas que cumplen con los filtros.
        
        /*
        List<CuentaAdministradorSubsidioDTO> listaCuentas = (transaccionDetalleSubsidio.getListaIdEmpleadores() != null
        || transaccionDetalleSubsidio.getListaIdAfiliadosPrincipales() != null
        || transaccionDetalleSubsidio.getListaIdBeneficiarios() != null
        || transaccionDetalleSubsidio.getListaIdAdminSubsidios() != null)?consultarCuentasPorListas(transaccionDetalleSubsidio):consultarCuentasFiltros(transaccionDetalleSubsidio);        
        */
        List<CuentaAdministradorSubsidioDTO> listaTransaccionesDetalles = consultarTransacciones(transaccionDetalleSubsidio);

        //se obtienen los detalles que cumplen con los filtros.
        List<DetalleSubsidioAsignadoDTO> listaDetalles = consultarDetallesCuentas(transaccionDetalleSubsidio, listaTransaccionesDetalles);

        List<CuentaAdministradorSubsidioDTO> lista = null;


        if ((listaDetalles != null && !listaDetalles.isEmpty()) && (listaTransaccionesDetalles != null && !listaTransaccionesDetalles.isEmpty())) {
            //se obtiene la información faltante de los detalles
            //obtenerDatosFaltantesDetallesSubsidioMonetarioPorIdDetalle(listaDetalles);            


            for (int i = 0; i < listaTransaccionesDetalles.size(); i++) {

                if ((listaTransaccionesDetalles.get(i).getIdTransaccionTerceroPagador() != null) && (listaTransaccionesDetalles.get(i).getIdRemisionDatosTerceroPagador() == null)) {
                    listaTransaccionesDetalles.get(i).setIdTransaccionTerceroPagador(null);
                    listaTransaccionesDetalles.get(i).setNombreTerceroPagador(null);
                }

                for (DetalleSubsidioAsignadoDTO detalle : listaDetalles) {

                    //si el detalle filtrado pertenece a la cuenta filtrada, dicha cuenta es una de las seleccionadas en en la consulta.
                    if (listaTransaccionesDetalles.get(i).getIdCuentaAdministradorSubsidio()
                            .equals(detalle.getIdCuentaAdministradorSubsidio())) {

                        if (listaTransaccionesDetalles.get(i).getListaDetallesSubsidioAsignadoDTO() == null) {
                            listaTransaccionesDetalles.get(i).setListaDetallesSubsidioAsignadoDTO(new ArrayList<>());
                        }
                        listaTransaccionesDetalles.get(i).getListaDetallesSubsidioAsignadoDTO().add(detalle);
                    }
                }

                // si no tiene detalles, se relaciona con los detalles de la cuenta original
                if (listaTransaccionesDetalles.get(i).getListaDetallesSubsidioAsignadoDTO() == null
                        || listaTransaccionesDetalles.get(i).getListaDetallesSubsidioAsignadoDTO().isEmpty()) {
                    for (DetalleSubsidioAsignadoDTO detalle : listaDetalles) {
                        if (detalle.getIdCuentaAdministradorSubsidio().equals(listaTransaccionesDetalles.get(i).getIdTransaccionOriginal())) {

                            if (listaTransaccionesDetalles.get(i).getListaDetallesSubsidioAsignadoDTO() == null) {
                                listaTransaccionesDetalles.get(i).setListaDetallesSubsidioAsignadoDTO(new ArrayList<>());
                            }
                            listaTransaccionesDetalles.get(i).getListaDetallesSubsidioAsignadoDTO().add(detalle);
                        }
                    }
                }


//                if (listaTransaccionesDetalles.get(i).getListaDetallesSubsidioAsignadoDTO() == null
//                        || listaTransaccionesDetalles.get(i).getListaDetallesSubsidioAsignadoDTO().isEmpty()) {
//
//                    listaTransaccionesDetalles.get(i).setListaDetallesSubsidioAsignadoDTO(null);//consultarDetallesRetirosAnulacionCuenta(
//                                    //listaTransaccionesDetalles.get(i).getIdCuentaAdministradorSubsidio(),
//                                    //listaTransaccionesDetalles.get(i).getTipoTransaccion()));
//                  
//                }
//                
//                if (listaTransaccionesDetalles.get(i).getListaDetallesSubsidioAsignadoDTO()!=null){
//                   
//                    for (DetalleSubsidioAsignadoDTO detalleOriginal : listaTransaccionesDetalles.get(i).getListaDetallesSubsidioAsignadoDTO()) {
//                        if (listaTransaccionesDetalles.get(i).getIdCuentaAdministradorSubsidio()
//                                .equals(detalleOriginal.getIdCuentaAdministradorSubsidio())) {
//                            detalleOriginal.setMedioDePago(listaTransaccionesDetalles.get(i).getMedioDePago());
//                            detalleOriginal.setNumeroTarjetaAdminSubsidio(listaTransaccionesDetalles.get(i).getNumeroTarjetaAdminSubsidio());
//                            detalleOriginal.setCodigoBancoAdminSubsidio(listaTransaccionesDetalles.get(i).getCodigoBancoAdminSubsidio());
//                            detalleOriginal.setNombreBancoAdminSubsidio(listaTransaccionesDetalles.get(i).getNombreBancoAdminSubsidio());
//                            detalleOriginal.setTipoCuentaAdminSubsidio(listaTransaccionesDetalles.get(i).getTipoCuentaAdminSubsidio());
//                            detalleOriginal.setNumeroCuentaAdminSubsidio(listaTransaccionesDetalles.get(i).getNumeroCuentaAdminSubsidio());
//                            detalleOriginal.setTipoIdentificacionTitularCuentaAdminSubsidio(listaTransaccionesDetalles.get(i).getTipoIdentificacionTitularCuentaAdminSubsidio());
//                            detalleOriginal.setNumeroIdentificacionTitularCuentaAdminSubsidio(listaTransaccionesDetalles.get(i).getNumeroIdentificacionTitularCuentaAdminSubsidio());
//                            detalleOriginal.setNombreTitularCuentaAdminSubsidio(listaTransaccionesDetalles.get(i).getNombreTitularCuentaAdminSubsidio());                            
//                             break;
//                        }
//                    }
//                    //obtenerDatosFaltantesDetallesSubsidioMonetarioPorIdDetalle(listaTransaccionesDetalles.get(i).getListaDetallesSubsidioAsignadoDTO());
//
//                }


            }

            lista = listaTransaccionesDetalles;
        } else {
            if (listaTransaccionesDetalles != null) {
                //List<Long> lstIdCuentas = listaTransaccionesDetalles.stream().map(cuenta -> cuenta.getIdCuentaAdministradorSubsidio())
                //       .collect(Collectors.toList());
                lista = consultarTransacciones(transaccionDetalleSubsidio);

//                lista.forEach( cuenta -> {
//                    List<DetalleSubsidioAsignadoDTO> detalles = new ArrayList<>();//consultarDetallesRetirosAnulacionCuenta(cuenta.getIdCuentaAdministradorSubsidio(), cuenta.getTipoTransaccion());
//                    cuenta.setListaDetallesSubsidioAsignadoDTO(detalles);
//                });                
            }
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return lista != null ? lista : null;
    }

    @Override
    public List<DetalleSubsidioAsignadoDTO> consultaDetallesRetirosAnulacionCuenta(Long idCuentaAdministradorSubsidio, TipoTransaccionSubsidioMonetarioEnum tipoTransaccion) {
        return consultarDetallesRetirosAnulacionCuenta(idCuentaAdministradorSubsidio, tipoTransaccion);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarRegistrosAbonosParaCalcularSaldoSubsidio(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarRegistrosAbonosParaCalcularSaldoSubsidio(TipoIdentificacionEnum tipoIdAdmin,
                                                                                                  String numeroIdAdmin, TipoMedioDePagoEnum medioDePago) 
    {
        String firmaServicio = "ConsultasModeloCore.consultarRegistrosAbonosParaCalcularSaldoSubsidio(TipoIdentificacionEnum,String,TipoMedioDePagoEnum)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<CuentaAdministradorSubsidioDTO> listaCuentas = null;

        listaCuentas = entityManagerCore
                .createNamedQuery(
                        NamedQueriesConstants.CONSULTAR_SALDO_CUENTA_ADMIN_SUBSIDIO_ESTADO_APLICADO_MEDIOS_DE_PAGOS_ADMIN_SUBSIDIO,
                        CuentaAdministradorSubsidioDTO.class)
                .setParameter("medioDePago", medioDePago).setParameter("tipoIdAdmin", tipoIdAdmin)
                .setParameter("numeroIdAdmin", numeroIdAdmin).getResultList();

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaCuentas.isEmpty() ? null : listaCuentas;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#registrarOperacionesSubsidio(com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoOperacionSubsidioEnum,
     * java.lang.String, java.lang.String, java.lang.Long)
     */
    @Override
    public Long registrarOperacionesSubsidio(TipoOperacionSubsidioEnum tipoDeOperacion, String parametrosIN, String usuarioOperacion,
                                             Long idAdminSubsidio) {

        String firmaServicio = "ConsultasModeloCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum,String,String,String,Long)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        RegistroOperacionTransaccionSubsidio registroOperacionesSubsidio = new RegistroOperacionTransaccionSubsidio();

        registroOperacionesSubsidio.setFechaHoraOperacion(new Date());
        registroOperacionesSubsidio.setTipoOperacion(tipoDeOperacion);
        registroOperacionesSubsidio.setUsuarioOperacion(usuarioOperacion);
        registroOperacionesSubsidio.setParametrosIN(parametrosIN);
        registroOperacionesSubsidio.setIdAdministradorSubsidio(idAdminSubsidio);

        try {
            entityManagerCore.persist(registroOperacionesSubsidio);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaServicio
                    + " Ocurrió un error  en la creación de del registro de operaciones de subsidio", e);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return registroOperacionesSubsidio.getIdRegistroOperacionesSubsidio();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarSitioDePago(java.lang.String,
     * java.lang.Long, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Long consultarSitioDePago(String municipio) {
        String firmaServicio = "ConsultasModeloCore.consultarSitioDePago(String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<Object[]> listaSitioPago = null;

        try {
            listaSitioPago = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_SITIO_DE_PAGO_POR_MUNICIPIO)
                    .setParameter("codigoMunicipio", municipio).getResultList();

        } catch (Exception e) {
            logger.debug("Ocurrio un error inesperado " + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        Long idSitioPago = null;

        if (!listaSitioPago.isEmpty()) {
            //Si el municipio tiene más de un sitio pago asociado se asigna el sitio pago principal
            if (listaSitioPago.size() > 1) {
                for (Object[] sitioPago : listaSitioPago) {
                    if (Boolean.valueOf(sitioPago[5].toString()).equals(Boolean.TRUE)) {
                        idSitioPago = Long.parseLong((sitioPago[0]).toString());
                        break;
                    } else {
                        idSitioPago = Long.parseLong((sitioPago[0]).toString());
                    }
                }
            } else {
                idSitioPago = Long.parseLong((listaSitioPago.get(0)[0]).toString());
            }
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return idSitioPago;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public String consultarDepartamentoPorMunicipio(String codMunicipio) {
        try {
            String firmaMetodo = "ConsultasModeloCore.consultarDepartamentoPorMunicipio(String)";
            logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
            String codDepartamentoRespuesta = "";

            Object codDepartamento = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DEPARTAMENTO_POR_MUNICIPIO)
                    .setParameter("codigoMunicipio", codMunicipio).getSingleResult();
            if (codDepartamento != null) {
                codDepartamentoRespuesta = codDepartamento.toString();
            }

            logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return codDepartamentoRespuesta;
        } catch (Exception e) {
            System.out.println("ERROR CONSULTA DEPARTAMENTO: " + e);
            System.out.println("ERROR CONSULTA DEPARTAMENTO: " + e.getMessage());
            System.out.println("ERROR CONSULTA DEPARTAMENTO: " + e.getCause());
            return "";
        }
    }

    /**
     *
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public String consultarNombreTercerPagadorIdConvenio(Long idConvenio) {
        String firmaServicio = "ConsultasModeloCore.consultarNombreTercerPagadorIdConvenio(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        String nombreConvenio = null;

        List<ConvenioTerceroPagador> convenios = null;
        try {
            System.out.println("idConvenio: " + idConvenio);
            convenios = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONVENIO_NOMBRE_ID_CONVENIO, ConvenioTerceroPagador.class)
                    .setParameter("idConvenio", idConvenio).getResultList();

        } catch (Exception e) {
            logger.debug("Ocurrio un error inesperado " + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        if (!convenios.isEmpty()) {
            nombreConvenio = convenios.get(0).getNombre();
        }
        System.out.println("nombreConvenio" + nombreConvenio);
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return nombreConvenio;
    }

    /**
     *
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ConvenioTerceroPagador consultarConvenioTerceroPagador(Long idConvenio) {
        String firmaServicio = "ConsultasModeloCore.consultarConvenioTerceroPagador(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        ConvenioTerceroPagador convenio = null;

        List<ConvenioTerceroPagador> convenios = null;
        System.out.println("idConvenio: " + idConvenio);
        convenios = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONVENIO_NOMBRE_ID_CONVENIO, ConvenioTerceroPagador.class)
                .setParameter("idConvenio", idConvenio).getResultList();

        if (!convenios.isEmpty()) {
            convenio = convenios.get(0);
        }
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return convenio;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarNombreTercerPagadorConvenio(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public String consultarNombreTercerPagadorConvenio(String usuario) {
        String firmaServicio = "ConsultasModeloCore.consultarNombreTercerPagadorConvenio(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        String nombreConvenio = null;

        List<ConvenioTerceroPagador> convenios = null;
        try {
            convenios = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONVENIO_NOMBRE_USUARIO_GENESYS, ConvenioTerceroPagador.class)
                    .setParameter("nombreUsuario", usuario).getResultList();

        } catch (Exception e) {
            logger.debug("Ocurrio un error inesperado " + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        if (!convenios.isEmpty()) {
            nombreConvenio = convenios.get(0).getNombre();
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return nombreConvenio;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarConveniosTercerPagador()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ConvenioTercerPagadorDTO> consultarConveniosTercerPagador() {

        String firmaServicio = "ConsultasModeloCore.consultarConveniosTercerPagador()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<ConvenioTercerPagadorDTO> listaConvenios = null;
        try {
            listaConvenios = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONVENIOS_TERCERO_PAGADOR_DTO_CON_DOCUMENTOS_SOPORTE,
                            ConvenioTercerPagadorDTO.class)
                    .getResultList();

        } catch (Exception e) {
            logger.debug("Ocurrio un error inesperado " + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        List<ConvenioTercerPagadorDTO> conveniosSinDocumentos = null;
        try {
            conveniosSinDocumentos = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONVENIOS_TERCERO_PAGADOR_DTO_SIN_DOCUMENTOS_SOPORTE,
                            ConvenioTercerPagadorDTO.class)
                    .getResultList();
        } catch (Exception e) {
            logger.debug("Ocurrio un error inesperado " + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        ConvenioTercerPagadorDTO convenio = new ConvenioTercerPagadorDTO();
        List<ConvenioTercerPagadorDTO> lista = convenio.unirDocumentosConConvenio(listaConvenios);
        for (ConvenioTercerPagadorDTO convenioSinDocumento : conveniosSinDocumentos) {
            lista.add(convenioSinDocumento);
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return lista;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarConveniosTercerPagador()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ConvenioTercerPagadorDTO consultarConvenioTercerPagador(Long idConvenio) {

        String firmaServicio = "ConsultasModeloCore.consultarConvenioTercerPagador() : " + idConvenio.toString();
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ConvenioTercerPagadorDTO convenio = null;
        try {
            convenio = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONVENIOS_TERCERO_PAGADOR_DTO,
                            ConvenioTercerPagadorDTO.class)
                    .setParameter("idConvenio", idConvenio)
                    .getSingleResult();

        } catch (NonUniqueResultException e) {
            e.getStackTrace();
        } catch (NoResultException e) {
            return null;
        }
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return convenio;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarCuentasAdministradoresSubsidio()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasAdministradoresSubsidio() {

        String firmaServicio = "ConsultasModeloCore.consultarAbonos()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<CuentaAdministradorSubsidioDTO> listaCuentas = null;
        try {
            listaCuentas = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_ADMIN_SUBSIDIO_TODOS_DTO, CuentaAdministradorSubsidioDTO.class)
                    .getResultList();

        } catch (Exception e) {
            logger.debug("Ocurrio un error inesperado " + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return listaCuentas;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarDetallesDTOPorIDs(java.util.List)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesDTOPorIDs(List<Long> listaIdsDetalles) {

        String firmaServicio = "ConsultasModeloCore.consultarListadoAbonosPorFechaDeVencimiento(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<DetalleSubsidioAsignadoDTO> listaDetalles = new ArrayList<>();
        List<DetalleSubsidioAsignadoDTO> listaDetallesPagina = null;
        List<Long> idsDetallePagina = new ArrayList<>();

        try {

            for (Long idDetalle : listaIdsDetalles) {
                idsDetallePagina.add(idDetalle);
                if (idsDetallePagina.size() == 1000) {
                    listaDetallesPagina = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLES_SUBSIDIOS_ASIGNADOS_DTO_POR_IDS,
                            DetalleSubsidioAsignadoDTO.class).setParameter("listaIdsDetalles", idsDetallePagina).getResultList();
                    idsDetallePagina.clear();
                    listaDetalles.addAll(listaDetallesPagina);
                }
            }

            listaDetallesPagina = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLES_SUBSIDIOS_ASIGNADOS_DTO_POR_IDS,
                    DetalleSubsidioAsignadoDTO.class).setParameter("listaIdsDetalles", idsDetallePagina).getResultList();
            listaDetalles.addAll(listaDetallesPagina);

        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return listaDetalles;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarPersonaAsociadaPorIdCuentaAdminSubsidio(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Long consultarPersonaAsociadaPorIdCuentaAdminSubsidio(Long idCuentaAdminSubsidio) {
        String firmaServicio = "ConsultasModeloCore.consultarPersonaAsociadaPorIdCuentaAdminSubsidio(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Long idPersonaAsociada = null;
        try {
            idPersonaAsociada = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_IDENTIFICADOR_PERSONA_AUTORIZADA_CUENTA_ADMIN_SUBSIDIO, Long.class)
                    .getSingleResult();

        } catch (NoResultException e) {
            idPersonaAsociada = null;
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return idPersonaAsociada;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#actualizarConvenioTerceroPagador(com.asopagos.subsidiomonetario.pagos.dto.ConvenioTercerPagadorDTO)
     */
    @Override
    public Long actualizarConvenioTerceroPagador(ConvenioTercerPagadorDTO convenioTercerPagadorDTO) {

        String firmaServicio = "ConsultasModeloCore.actualizarConvenioTerceroPagador(ConvenioTercerPagadorDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ConvenioTerceroPagador convenioTerceroPagador = null;
        String nombreConvenio = convenioTercerPagadorDTO.getNombreConvenio();

        try {
            convenioTerceroPagador = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONVENIO_TERCERO_PAGADOR_POR_ID, ConvenioTerceroPagador.class)
                    .setParameter("idConvenioTerceroPagador", convenioTercerPagadorDTO.getIdConvenio()).getSingleResult();
        } catch (NoResultException e) {
            logger.error(firmaServicio + "no se encontro el convenio para su actualización", e);
            return 0L;
        }

        //se actualizan los datos de la ubicación relacionada con el convenio
        actualizarUbicacion(convenioTercerPagadorDTO.getUbicacionModeloDTO());
        //se actualizan los datos del convenio del tercero pagador.
        convenioTerceroPagador.setEstado(convenioTercerPagadorDTO.getEstadoConvenio());
        convenioTerceroPagador.setNombreCompletoContacto(convenioTercerPagadorDTO.getNombreCompletoContacto());
        convenioTerceroPagador.setCargoContacto(convenioTercerPagadorDTO.getCargoContacto());
        convenioTerceroPagador.setIndTelFijoContacto(convenioTercerPagadorDTO.getIndTelFijoContacto());
        convenioTerceroPagador.setTelefonoCelularContacto(convenioTercerPagadorDTO.getTelefonoCelularContacto());
        convenioTerceroPagador.setTelefonoFijoContacto(convenioTercerPagadorDTO.getTelefonoFijoContacto());
        convenioTerceroPagador.setEmailContacto(convenioTercerPagadorDTO.getEmailContacto());
        convenioTerceroPagador.setMedioDePago(convenioTercerPagadorDTO.getTipoMedioDePago());
        convenioTerceroPagador.setUsuarioAsignadoConvenio(convenioTercerPagadorDTO.getNombreUsuarioGenesys());
        convenioTerceroPagador.setAcuerdoDeFacturacion(convenioTercerPagadorDTO.getAcuerdoDeFacturacion());
        convenioTerceroPagador.setNombre(nombreConvenio);

        try {
            entityManagerCore.merge(convenioTerceroPagador);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        //se valida que hayan documentos de soporte para relacionar los con el convenio
        if (!convenioTercerPagadorDTO.getListaDocumentosSoporte().isEmpty()) {

            List<DocumentoSoporteConvenioModeloDTO> listaDocumentosSoporte = new ArrayList<>();
            for (DocumentoSoporteConvenioModeloDTO documentoSoporte : convenioTercerPagadorDTO.getListaDocumentosSoporte()) {
                //se agregan los nuevos documentos de soporte
                if (documentoSoporte.getIdConvenioTerceroPagador() == null)
                    listaDocumentosSoporte.add(documentoSoporte);
            }
            //si hay nuevos documentos de soporte, se agregan al convenio que ya existe
            if (!listaDocumentosSoporte.isEmpty())
                registrarDocumentosSoporteConvenioTerceroPagador(listaDocumentosSoporte, convenioTerceroPagador.getIdConvenio());
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return convenioTerceroPagador.getIdConvenio();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarInformesRetiros(com.asopagos.subsidiomonetario.pagos.dto.SubsidioPerdidaDerechoInformesConsumoDTO)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarInformesRetiros(SubsidioPerdidaDerechoInformesConsumoDTO suConsumoDTO) {
        String firmaServicio = "ConsultasModeloCore.consultarInformesRetiros(SubsidioPerdidaDerechoInformesConsumoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<CuentaAdministradorSubsidioDTO> listaRetiros = null;

        Date fechaInicial = null;
        Date fechaFinal = null;

        if (suConsumoDTO.getFechaInicial() == null && suConsumoDTO.getFechaFinal() == null) {
            //se crea la fecha inicial con el rango inicial minimo permito 01/01/1990
            Long fechaInicio = new Long("631170000000");
            fechaInicial = new Date(fechaInicio);
            fechaFinal = new Date();
        } else {
            fechaInicial = new Date(suConsumoDTO.getFechaInicial());
            fechaFinal = new Date(suConsumoDTO.getFechaFinal());
        }

        Date periodo = null;
        if (suConsumoDTO.getPeriodoLiquidado() != null) {
            Calendar periodoLiquidado = Calendar.getInstance();
            periodoLiquidado.setTime(new Date(suConsumoDTO.getPeriodoLiquidado()));

            periodo = CalendarUtils.formatearFechaSinHora(periodoLiquidado).getTime();

        }

        EstadoTransaccionSubsidioEnum estado = null;//cuando es SIN CONCILIAR, se queda el estado en nulo.

        if (suConsumoDTO.getEstado() != null
                && EstadoRegistroCargueArchivoRetiroTerceroPagador.CONCILIADO.equals(suConsumoDTO.getEstado())) {
            estado = EstadoTransaccionSubsidioEnum.CONCILIADO;
        }else if (suConsumoDTO.getEstado() != null
        && EstadoRegistroCargueArchivoRetiroTerceroPagador.SIN_CONCILIAR.equals(suConsumoDTO.getEstado())){
            estado = EstadoTransaccionSubsidioEnum.SIN_CONCILIAR;
        }

        int sizeAfiliados = 0;
        if (suConsumoDTO.getListaIdsAfiliadosPrincipalesRelacionados() != null
                && !suConsumoDTO.getListaIdsAfiliadosPrincipalesRelacionados().isEmpty()) {
            sizeAfiliados = 1;
        } else if (sizeAfiliados == 0 && suConsumoDTO.getListaIdsAfiliadosPrincipalesRelacionados() != null
                && suConsumoDTO.getListaIdsAfiliadosPrincipalesRelacionados().isEmpty()) {
            suConsumoDTO.setIdAfiliadoPrincipalRelacionado(null);
        }

        int sizeBeneficiarios = 0;
        if (suConsumoDTO.getListaIdsBeneficiariosRelacionados() != null && !suConsumoDTO.getListaIdsBeneficiariosRelacionados().isEmpty()) {
            sizeBeneficiarios = 1;
        } else if (sizeBeneficiarios == 0 && suConsumoDTO.getListaIdsBeneficiariosRelacionados() != null
                && suConsumoDTO.getListaIdsBeneficiariosRelacionados().isEmpty()) {
            suConsumoDTO.setIdBeneficiarioRelacionado(null);
        }

        Date feInicial = CalendarUtils.truncarHora(fechaInicial);
        Date feFinal = CalendarUtils.truncarHoraMaxima(fechaFinal);
        List<Object[]> lstCuentas = null;
        logger.info("fecha inicial " + feInicial);
        logger.info("fecha final " + feFinal);
        logger.info("Consultando informe de retiros "+estado);
        if (estado != null &&  estado.equals(EstadoTransaccionSubsidioEnum.SIN_CONCILIAR)) {
            logger.info("Consultando informe de retiros NULL");
            lstCuentas = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_INFORMES_CONSUMOS_RETIRO_CUENTA_ADMIN_SUBSIDIO_UNO)
                    .setParameter("idDetalleSubsidio", suConsumoDTO.getIdentificadorSubsidioAsignado())
                    .setParameter("nombreConvenio", suConsumoDTO.getNombreConvenio())
                    .setParameter("periodoLiquidado", periodo)
                    .setParameter("fechaInicial", feInicial).setParameter("fechaFinal", feFinal)
                    .setParameter("sizeListaIdsAfiliados", sizeAfiliados)
                    .setParameter("listaIdsAfiliados", suConsumoDTO.getListaIdsAfiliadosPrincipalesRelacionados())
                    .setParameter("sizeListaIdsBeneficiarios", sizeBeneficiarios)
                    .setParameter("listaIdsBeneficiarios", suConsumoDTO.getListaIdsBeneficiariosRelacionados())
                    .setParameter("medioDePago", suConsumoDTO.getMedioDePago() == null ? null : suConsumoDTO.getMedioDePago().name())
                    .setParameter("codigoBanco", suConsumoDTO.getCodigoBancoAdmin())
                    .setParameter("tipoCuenta", suConsumoDTO.getTipoCuentaAdminSubsidio())
                    .setParameter("numCuentaAdmon", suConsumoDTO.getNumeroCuentaAdminSubsidio())
                    .setParameter("tipoIdTitularCuentaAdmon", suConsumoDTO.getTipoIdTitularCuenta())
                    .setParameter("numIdTitularCuentaAdmon", suConsumoDTO.getNumeroIdTitularCuenta())
                    .setParameter("numTarjetaAdmon", suConsumoDTO.getNumeroTarjetaAdmin()).getResultList();

            logger.info("Termino consultando informe de retiros");
        } else {
            logger.info("Consultando informe de retiros NOT NULL "+estado);
            lstCuentas = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_INFORMES_CONSUMOS_RETIRO_CUENTA_ADMIN_SUBSIDIO_DOS)
                    .setParameter("idDetalleSubsidio", suConsumoDTO.getIdentificadorSubsidioAsignado())
                    .setParameter("nombreConvenio", suConsumoDTO.getNombreConvenio())
                    .setParameter("periodoLiquidado", periodo)
                    .setParameter("estadoTransaccion", estado != null ? estado.name() : null)
                    .setParameter("fechaInicial", feInicial).setParameter("fechaFinal", feFinal)
                    .setParameter("sizeListaIdsAfiliados", sizeAfiliados)
                    .setParameter("listaIdsAfiliados", suConsumoDTO.getListaIdsAfiliadosPrincipalesRelacionados())
                    .setParameter("sizeListaIdsBeneficiarios", sizeBeneficiarios)
                    .setParameter("listaIdsBeneficiarios", suConsumoDTO.getListaIdsBeneficiariosRelacionados())
                    .setParameter("medioDePago", suConsumoDTO.getMedioDePago() == null ? null : suConsumoDTO.getMedioDePago().name())
                    .setParameter("codigoBanco", suConsumoDTO.getCodigoBancoAdmin())
                    .setParameter("tipoCuenta", suConsumoDTO.getTipoCuentaAdminSubsidio())
                    .setParameter("numCuentaAdmon", suConsumoDTO.getNumeroCuentaAdminSubsidio())
                    .setParameter("tipoIdTitularCuentaAdmon", suConsumoDTO.getTipoIdTitularCuenta())
                    .setParameter("numIdTitularCuentaAdmon", suConsumoDTO.getNumeroIdTitularCuenta())
                    .setParameter("numTarjetaAdmon", suConsumoDTO.getNumeroTarjetaAdmin()).getResultList();
        }

        List<CuentaAdministradorSubsidioDTO> listaCuentasAdminSubsidios = new ArrayList<>();
        try {

            logger.info("setear datos");
            List<Long> sqlHack = new ArrayList<Long>();
            sqlHack.add(0L);
            for (Object[] result : lstCuentas) {
                CuentaAdministradorSubsidioDTO abono = new CuentaAdministradorSubsidioDTO();
                abono.setIdCuentaAdministradorSubsidio(Long.valueOf(result[0].toString()));
                if (result[1] != null)
                    abono.setFechaHoraCreacionRegistro((Date) result[1]);
                if (result[2] != null)
                    abono.setUsuarioCreacionRegistro(result[2].toString());
                if (result[3] != null)
                    abono.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.valueOf(result[3].toString()));
                if (result[4] != null)
                    abono.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.valueOf(result[4].toString()));
                if (result[6] != null)
                    abono.setOrigenTransaccion(OrigenTransaccionEnum.valueOf(result[6].toString()));
                if (result[7] != null)
                    abono.setMedioDePago(TipoMedioDePagoEnum.valueOf(result[7].toString()));
                if (result[8] != null)
                    abono.setNumeroTarjetaAdminSubsidio(result[8].toString());
                if (result[9] != null)
                    abono.setCodigoBancoAdminSubsidio(result[9].toString());
                if (result[10] != null)
                    abono.setNombreBancoAdminSubsidio(result[10].toString());
                if (result[11] != null)
                    abono.setTipoCuentaAdminSubsidio(TipoCuentaEnum.valueOf(result[11].toString()));
                if (result[12] != null)
                    abono.setNumeroCuentaAdminSubsidio(result[12].toString());
                if (result[13] != null)
                    abono.setTipoIdentificacionTitularCuentaAdminSubsidio(TipoIdentificacionEnum.valueOf(result[13].toString()));
                if (result[14] != null)
                    abono.setNumeroIdentificacionTitularCuentaAdminSubsidio(result[14].toString());
                if (result[15] != null)
                    abono.setNombreTitularCuentaAdminSubsidio(result[15].toString());
                if (result[16] != null)
                    abono.setFechaHoraTransaccion((Date) result[16]);
                if (result[17] != null)
                    abono.setUsuarioTransaccionLiquidacion(result[17].toString());
                if (result[18] != null)
                    abono.setValorOriginalTransaccion(BigDecimal.valueOf(Double.parseDouble(result[18].toString())));
                if (result[19] != null)
                    abono.setValorRealTransaccion(BigDecimal.valueOf(Double.parseDouble(result[19].toString())));
                if (result[20] != null)
                    abono.setIdTransaccionOriginal(Long.parseLong(result[20].toString()));
                if (result[21] != null)
                    abono.setIdRemisionDatosTerceroPagador(result[21].toString());
                if (result[22] != null)
                    abono.setIdTransaccionTerceroPagador(result[22].toString());
                if (result[23] != null)
                    abono.setNombreTerceroPagador(result[23].toString());
                if (result[24] != null)
                    abono.setIdCuentaAdminSubsidioRelacionado(Long.parseLong(result[24].toString()));
                if (result[25] != null)
                    abono.setFechaHoraUltimaModificacion((Date) result[25]);
                if (result[26] != null)
                    abono.setUsuarioUltimaModificacion(result[26].toString());
                if (result[33] != null)
                    abono.setNombreSitioPago(result[33].toString());
                if (result[34] != null)
                    abono.setNombreSitioCobro(result[34].toString());
                if (result[35] != null)
                    abono.setNombresApellidosAdminSubsidio(result[35].toString());
                if (result[36] != null)
                    abono.setNombrePersonaAutorizada(result[36].toString());

                if ((abono.getIdTransaccionTerceroPagador() != null) && (abono.getIdRemisionDatosTerceroPagador() == null && abono.getNombreTerceroPagador() == null)) {
                    abono.setIdTransaccionTerceroPagador(null);
                    abono.setNombreTerceroPagador(null);
                }

                if (result[37] != null && result[38] != null) {
                    abono.setTipoIdAdminSubsidio(TipoIdentificacionEnum.valueOf(result[37].toString()));
                    abono.setNumeroIdAdminSubsidio(result[38].toString());
                }
                if (result[39] != null)
                    abono.setIdEmpleador(Long.valueOf(result[39].toString()));
                if (result[40] != null)
                    abono.setIdAfiliadoPrincipal(Long.valueOf(result[40].toString()));
                if (result[41] != null)
                    abono.setIdBeneficiarioDetalle(Long.valueOf(result[41].toString()));
                if (result[42] != null)
                    abono.setIdPuntoDeCobro(result[42].toString());
                if (result[43] != null)
                    abono.setEstablecimientoCodigo(result[43].toString());
                if (result[44] != null)
                    abono.setEstablecimientoNombre(result[44].toString());
                if (result[45] != null)
                    abono.setFechaTransaccionConsumo(result[45].toString());

                listaCuentasAdminSubsidios.add(abono);
            }
            logger.info("fin setear datos");


        } catch (Exception e) {
            logger.debug("Ocurrio un error inesperado " + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return listaCuentasAdminSubsidios.isEmpty() ? null : listaCuentasAdminSubsidios;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#generarListadoSubsidiosAnularPorPerdidaDeDerecho(com.asopagos.subsidiomonetario.pagos.dto.SubsidioPerdidaDerechoInformesConsumoDTO)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    @Override
    public List<SubsidiosConsultaAnularPerdidaDerechoDTO> generarListadoSubsidiosAnularPorPerdidaDeDerecho(
            SubsidioPerdidaDerechoInformesConsumoDTO suConsumoDTO) {

        String firmaServicio = "ConsultasModeloCore.generarListadoSubsidiosAnularPorPerdidaDeDerecho(SubsidioPerdidaDerechoInformesConsumoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Date periodo = null;
        if (suConsumoDTO.getPeriodoLiquidado() != null) {
            Calendar periodoLiquidado = Calendar.getInstance();
            periodoLiquidado.setTime(new Date(suConsumoDTO.getPeriodoLiquidado()));

            periodo = CalendarUtils.formatearFechaSinHora(periodoLiquidado).getTime();

        }

        Date fechaInicial = null;
        Date fechaFinal = null;

        if (suConsumoDTO.getFechaInicial() == null && suConsumoDTO.getFechaFinal() == null) {
            //se crea la fecha inicial con el rango inicial minimo permito 01/01/1990
            Long fechaInicio = new Long("631170000000");
            fechaInicial = new Date(fechaInicio);
            fechaFinal = new Date();
        } else {
            fechaInicial = new Date(suConsumoDTO.getFechaInicial());
            fechaFinal = new Date(suConsumoDTO.getFechaFinal());
        }

        List<SubsidiosConsultaAnularPerdidaDerechoDTO> subsidiosMonetariosAnular = new ArrayList<>();

        List<String> mediosDePago = new ArrayList<>();

        if (suConsumoDTO.getMedioDePago() != null) {
            mediosDePago.add(suConsumoDTO.getMedioDePago().toString());
        } else {
            mediosDePago.add(TipoMedioDePagoEnum.EFECTIVO.toString());
            mediosDePago.add(TipoMedioDePagoEnum.TARJETA.toString());
        }

        List<String> listaIdAfiliado = new ArrayList<>();

        if (suConsumoDTO.getListaIdsAfiliadosPrincipalesRelacionados() != null
                && !suConsumoDTO.getListaIdsAfiliadosPrincipalesRelacionados().isEmpty()) {

            for (Long idAfiliado : suConsumoDTO.getListaIdsAfiliadosPrincipalesRelacionados()) {
                listaIdAfiliado.add(idAfiliado.toString());
            }
        }

        List<String> listaIdBeneficiario = new ArrayList<>();

        if (suConsumoDTO.getListaIdsBeneficiariosRelacionados() != null && !suConsumoDTO.getListaIdsBeneficiariosRelacionados().isEmpty()) {

            for (Long idBeneficiario : suConsumoDTO.getListaIdsBeneficiariosRelacionados()) {
                listaIdBeneficiario.add(idBeneficiario.toString());
            }
        }

        List<String> listaIdGrupoFamiliar = new ArrayList<>();

        if (suConsumoDTO.getListaIdGrupoFamiliar() != null && !suConsumoDTO.getListaIdGrupoFamiliar().isEmpty()) {

            for (Long idGrupoFamiliar : suConsumoDTO.getListaIdGrupoFamiliar()) {
                listaIdGrupoFamiliar.add(idGrupoFamiliar.toString());
            }
        }

        List<Object[]> subsidios = null;
        try {

            subsidios = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTADO_PARA_ANULACION_POR_PERDIDA_DE_DERECHO)
                    .setParameter("idDetalleSubsidio", suConsumoDTO.getIdentificadorSubsidioAsignado())
                    .setParameter("periodoLiquidado", periodo).setParameter("fechaInicial", fechaInicial)
                    .setParameter("fechaFinal", fechaFinal)
                    .setParameter("sizeListaIdAfiliados", listaIdAfiliado.isEmpty() ? null : listaIdAfiliado.size())
                    .setParameter("listaIdAfiliados", listaIdAfiliado.isEmpty() ? null : listaIdAfiliado)
                    .setParameter("sizeListaIdBeneficiarios", listaIdBeneficiario.isEmpty() ? null
                            : listaIdBeneficiario.size())
                    .setParameter("listaIdBeneficiarios", listaIdBeneficiario.isEmpty() ? null : listaIdBeneficiario)
                    .setParameter("sizeListaIdGrupoFamiliar", listaIdGrupoFamiliar.isEmpty() ? null : listaIdGrupoFamiliar.size())
                    .setParameter("listaIdGrupoFamiliar", listaIdGrupoFamiliar.isEmpty() ? null : listaIdGrupoFamiliar)
                    .setParameter("mediosDePago", mediosDePago).setParameter("numTarjetaAdmon", suConsumoDTO.getNumeroTarjetaAdmin())
                    .getResultList();

        } catch (Exception e) {
            logger.debug("Ocurrio un error inesperado " + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        for (int i = 0; i < subsidios.size(); i++) {

            SubsidiosConsultaAnularPerdidaDerechoDTO subsidioAnular = new SubsidiosConsultaAnularPerdidaDerechoDTO();

            //0. Identificador transacción abono relacionado 
            subsidioAnular.setIdCuentaAdminSubsidio(Long.parseLong((subsidios.get(i)[0].toString())));

            //1. Periodo de liquidación de la solicitud de liquidación del subsidio
            subsidioAnular.setFechaHoraCreacionRegistro((Date) (subsidios.get(i)[1]));

            //2. Liquidación asociada
            subsidioAnular.setUsuarioCreacionRegistro(subsidios.get(i)[2].toString());

            //3. fecha liquidación asociada
            subsidioAnular.setTipoTransaccionSubsidio(subsidios.get(i)[3].toString());

            subsidioAnular.setEstadoTransaccionSubsidio(subsidios.get(i)[4].toString());

            subsidioAnular.setOrigenTransaccion(OrigenTransaccionEnum.valueOf((subsidios.get(i)[5]).toString()));

            //Creación del afiliado relacionado con el detalle
            PersonaModeloDTO afiliado = new PersonaModeloDTO();
            //7. tipo identificación afiliado
            afiliado.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((subsidios.get(i)[6]).toString()));
            //8. numero identificacion afiliado
            afiliado.setNumeroIdentificacion((subsidios.get(i)[7]).toString());
            //9. primer nombre afiliado
            afiliado.setPrimerNombre(subsidios.get(i)[8] != null ? subsidios.get(i)[8].toString() : null);
            //10. segundo nombre afiliado
            afiliado.setSegundoNombre(subsidios.get(i)[9] != null ? subsidios.get(i)[9].toString() : null);
            // 11. Primer apellido afiliado
            afiliado.setPrimerApellido(subsidios.get(i)[10] != null ? subsidios.get(i)[10].toString() : null);

            // 12. Segundo apellido afiliado
            afiliado.setSegundoApellido(subsidios.get(i)[11] != null ? subsidios.get(i)[11].toString() : null);
            subsidioAnular.setAfiliadoPrincipal(afiliado);

            // Medio de pago
            subsidioAnular.setMedioDePago(TipoMedioDePagoEnum.valueOf(subsidios.get(i)[12].toString()));

            // Número de tarjeta de administración de subsidio
            subsidioAnular.setNumeroTarjetaAdminSubsidio(subsidios.get(i)[13] != null ? subsidios.get(i)[13].toString() : null);

            // Código de banco
            subsidioAnular.setCodigoBancoAdminSubsidio(subsidios.get(i)[14] != null ? subsidios.get(i)[14].toString() : null);

            // Nombre del banco
            subsidioAnular.setNombreBancoAdminSubsidio(subsidios.get(i)[15] != null ? subsidios.get(i)[15].toString() : null);

            // Tipo de cuenta de administración de subsidio
            subsidioAnular.setTipoCuentaAdmonSubsidio(subsidios.get(i)[16] != null ? TipoCuentaEnum.valueOf(subsidios.get(i)[16].toString()) : null);

            // Número de cuenta de administración de subsidio
            subsidioAnular.setNumeroCuentaAdmonSubsidio(subsidios.get(i)[17] != null ? subsidios.get(i)[17].toString() : null);

            // Tipo de identificación del titular de la cuenta de administración de subsidio
            subsidioAnular.setTipoIdentificacionTitularCuentaAdmonSubsidio(subsidios.get(i)[18] != null ? TipoIdentificacionEnum.valueOf(subsidios.get(i)[18].toString()) : null);

            // Número de identificación del titular de la cuenta
            subsidioAnular.setNumeroIdentificacionTitularCuentaAdmonSubsidio(subsidios.get(i)[19] != null ? subsidios.get(i)[19].toString() : null);

            // Nombre del titular de la cuenta
            subsidioAnular.setNombreTitularCuentaAdmonSubsidio(subsidios.get(i)[20] != null ? subsidios.get(i)[20].toString() : null);

            // Sitio de pago
            subsidioAnular.setSitioDePago(subsidios.get(i)[21] != null ? subsidios.get(i)[21].toString() : null);


            subsidioAnular.setValorOriginalTransaccion(BigDecimal.valueOf(Double.parseDouble((subsidios.get(i)[22]).toString())));
            
            subsidioAnular.setValorRealTransaccion(BigDecimal.valueOf(Double.parseDouble((subsidios.get(i)[23]).toString())));

            subsidioAnular.setFechaHoraTransaccion((Date) (subsidios.get(i)[24]));

            subsidioAnular.setUsuarioTransaccion(subsidios.get(i)[25].toString());
            
            subsidioAnular.setFechaHoraUltimaModificacion((Date) (subsidios.get(i)[26]));
            
          // Usuario de última modificación
            subsidioAnular.setUsuarioUltimaModificacion(subsidios.get(i)[27] != null ? subsidios.get(i)[27].toString() : null);

            // ID de cuenta de administración de subsidio relacionado
            subsidioAnular.setIdCuentaAdmonSubsidioRelacionado(subsidios.get(i)[28] != null ? subsidios.get(i)[28].toString() : null);

            // ID de transacción original
            subsidioAnular.setIdTransaccionOriginal(subsidios.get(i)[29] != null ? Long.parseLong(subsidios.get(i)[29].toString()) : null);

    

            subsidiosMonetariosAnular.add(subsidioAnular);
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return subsidiosMonetariosAnular.isEmpty() ? null : subsidiosMonetariosAnular;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#compararRegistrosCamposArchivoTerceroPagadorSP(java.lang.String,
     * java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void compararRegistrosCamposArchivoTerceroPagadorSP(String identificadorDocumento, String versionDocumento,
                                                               String nombreTerceroPagador) {

        String firmaMetodo = "ConsultasModeloCore.compararRegistrosCamposArchivoTerceroPagadorSP(String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        logger.info("identificadorDocumento "+identificadorDocumento);
        logger.info("versionDocumento "+versionDocumento);
        logger.info("nombreTerceroPagador "+nombreTerceroPagador);
        try {
            StoredProcedureQuery storedProcedure = entityManagerCore
                    .createStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_VALIDAR_CAMPOS_ARCHIVO_RETIRO_PAGOS);
            storedProcedure.registerStoredProcedureParameter("IdDocumentoRetiroTerceroPagador", String.class, ParameterMode.IN);
            storedProcedure.setParameter("IdDocumentoRetiroTerceroPagador", identificadorDocumento);
            storedProcedure.registerStoredProcedureParameter("NumeroVersionDocumento", String.class, ParameterMode.IN);
            storedProcedure.setParameter("NumeroVersionDocumento", versionDocumento);
            storedProcedure.registerStoredProcedureParameter("NombreTerceroPagador", String.class, ParameterMode.IN);
            storedProcedure.setParameter("NombreTerceroPagador", nombreTerceroPagador);
            storedProcedure.execute();

        } catch (QueryTimeoutException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        } catch (PersistenceException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarEstadoArchivoRetiroTerceroPagador(java.lang.String,
     * java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public EstadoArchivoRetiroTercerPagadorEnum consultarEstadoArchivoRetiroTerceroPagador(String identificadorDocumento,
                                                                                           String versionDocumento) {

        String firmaMetodo = "ConsultasModeloCore.consultarEstadoArchivoRetiroTerceroPagador(String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        EstadoArchivoRetiroTercerPagadorEnum estado = null;

        try {

            estado = EstadoArchivoRetiroTercerPagadorEnum.valueOf(
                    entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_CONCILIACION_ARCHIVO_RETIRO_TERCERO_PAGADOR)
                            .setParameter("documento", identificadorDocumento).setParameter("versionDoc", versionDocumento)
                            .getSingleResult().toString());

        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return estado;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarInconsistenciasArchivoRetiroTerceroPagador(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<IncosistenciaConciliacionConvenioDTO> consultarInconsistenciasArchivoRetiroTerceroPagador(
            Long idArchivoRetiroTerceroPagador) {
        String firmaMetodo = "ConsultasModeloCore.consultarInconsistenciasArchivoRetiroTerceroPagador(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<IncosistenciaConciliacionConvenioDTO> listaInconsistencias = null;

        try {

            listaInconsistencias = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_INCOSISTENCIAS_ARCHIVO_RETIRO_TERCERO_PAGADOR,
                            IncosistenciaConciliacionConvenioDTO.class)
                    .setParameter("idArchivo", idArchivoRetiroTerceroPagador).getResultList();

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listaInconsistencias;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#actualizarEstadoRetirosConciliadosTerceroPagador(java.lang.Long)
     */
    @Override
    public void actualizarEstadoRetirosConciliadosTerceroPagador(Long idArchivoRetiroTerceroPagador) {
        String firmaMetodo = "ConsultasModeloCore.actualizarEstadoRetirosConciliadosTerceroPagador(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {

            entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ESTADO_RETIRO_CUENTA_CONCILIADO_ARCHIVO_RETIRO_TERCERO_PAGAGOR)
                    .setParameter("idArchivo", idArchivoRetiroTerceroPagador).executeUpdate();

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarSubsidiosCambioMedioDePago(com.asopagos.subsidiomonetario.pagos.dto.SubsidioMonetarioConsultaCambioPagosDTO)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarSubsidiosCambioMedioDePago(
            SubsidioMonetarioConsultaCambioPagosDTO cambioPagosDTO) {

        String firmaMetodo = "ConsultasModeloCore.consultarSubsidiosCambioMedioDePago(SubsidioMonetarioConsultaCambioPagosDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<CuentaAdministradorSubsidioDTO> listaSubsidios = null;

        Date fechaRangoInicial = null;
        Date fechaRangoFinal = null;

        if (cambioPagosDTO.getFechaInicial() == null && cambioPagosDTO.getFechaFinal() == null) {
            //se crea la fecha inicial con el rango inicial minimo permito 01/01/1990
            Long fechaInicio = new Long("631170000000");

            fechaRangoInicial = new Date(fechaInicio);
            fechaRangoFinal = new Date();

        } else {
            fechaRangoInicial = new Date(cambioPagosDTO.getFechaInicial());
            fechaRangoFinal = new Date(cambioPagosDTO.getFechaFinal());
        }

        try {

            listaSubsidios = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CANDIDATOS_SUBSIDIOS_CAMBIO_MEDIO_DE_PAGO,
                            CuentaAdministradorSubsidioDTO.class)
                    .setParameter("idCuentaAdmin", cambioPagosDTO.getIdentificadorTransaccionAbono())
                    .setParameter("tipoIdAdmin", cambioPagosDTO.getTipoIdAdmin())
                    .setParameter("numeroIdAdmin", cambioPagosDTO.getNumeroIdAdmin())
                    .setParameter("medioDePago", cambioPagosDTO.getMedioDePago())
                    .setParameter("fechaInicial", CalendarUtils.truncarHora(fechaRangoInicial))
                    .setParameter("fechaFinal", CalendarUtils.truncarHoraMaxima(fechaRangoFinal))
                    .setParameter("codigoBanco", cambioPagosDTO.getCodigoBancoAdmin())
                    .setParameter("tipoCuenta", cambioPagosDTO.getTipoCuentaAdminSubsidio())
                    .setParameter("numeroCuenta", cambioPagosDTO.getNumeroCuentaAdminSubsidio())
                    .setParameter("tipoIdTitular", cambioPagosDTO.getTipoIdTitularCuenta())
                    .setParameter("numeroIdTitular", cambioPagosDTO.getNumeroIdTitularCuenta())
                    .setParameter("numeroTarjetaAdmin", cambioPagosDTO.getNumeroTarjetaAdmin()).getResultList();

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        if (listaSubsidios.isEmpty())
            listaSubsidios = null;

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        return listaSubsidios;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarTransaccionesAbonoCobrados(com.asopagos.subsidiomonetario.pagos.dto.DetalleTransaccionAsignadoConsultadoDTO)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarTransaccionesAbonoCobrados(
            DetalleTransaccionAsignadoConsultadoDTO transaccionDetalleSubsidio) {
        String firmaMetodo = "ConsultasModeloCore.consultarTransaccionesAbonoCobrados(DetalleTransaccionAsignadoConsultadoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<CuentaAdministradorSubsidioDTO> lstCuentaAdministradorSubsidioDTO = null;
        CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO = null;
        DetalleSubsidioAsignadoDTO detalleSubsidioAsignadoDTO = null;

        List<Object[]> lstTransaccionesAbonoCobradosResult = null;

        Date fechaRangoInicial = null;
        Date fechaRangoFinal = null;
        Date periodoLiquidacionAsociada = null;

        if (transaccionDetalleSubsidio.getFechaInicio() == null && transaccionDetalleSubsidio.getFechaFin() == null) {
            //se crea la fecha inicial con el rango inicial minimo permito 01/01/1990
            Long fechaInicio = new Long("631170000000");

            fechaRangoInicial = new Date(fechaInicio);
            fechaRangoFinal = new Date();

        } else {
            fechaRangoInicial = new Date(transaccionDetalleSubsidio.getFechaInicio());
            fechaRangoFinal = new Date(transaccionDetalleSubsidio.getFechaFin());
        }


        if (transaccionDetalleSubsidio.getFechaPeriodoLiquidado() != null) {
            Calendar periodoLiquidado = Calendar.getInstance();
            periodoLiquidado.setTime(transaccionDetalleSubsidio.getFechaPeriodoLiquidado());
            periodoLiquidacionAsociada = CalendarUtils.formatearFechaSinHora(periodoLiquidado).getTime();
        }

        lstTransaccionesAbonoCobradosResult = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_TRANSACCIONES_ABONO_COBRADOS)
                .setParameter("idSubsidioAsignadoLiquidado", transaccionDetalleSubsidio.getIdSubsidioAsignadoLiquidado())
                .setParameter("fechaInicial", CalendarUtils.truncarHora(fechaRangoInicial))
                .setParameter("fechaFinal", CalendarUtils.truncarHoraMaxima(fechaRangoFinal))
                .setParameter("sizeListaIdAfiliadosPrincipales",
                        (transaccionDetalleSubsidio.getListaIdAfiliadosPrincipales() != null
                                && !transaccionDetalleSubsidio.getListaIdAfiliadosPrincipales().isEmpty())
                                ? transaccionDetalleSubsidio.getListaIdAfiliadosPrincipales().size() : null)
                .setParameter("listaIdAfiliadosPrincipales", transaccionDetalleSubsidio.getListaIdAfiliadosPrincipales())
                .setParameter("sizeListaIdGruposFamiliares",
                        (transaccionDetalleSubsidio.getListaIdGruposFamiliares() != null
                                && !transaccionDetalleSubsidio.getListaIdGruposFamiliares().isEmpty())
                                ? transaccionDetalleSubsidio.getListaIdGruposFamiliares().size() : null)
                .setParameter("listaIdGruposFamiliares", transaccionDetalleSubsidio.getListaIdGruposFamiliares())
                .setParameter("sizeListaIdBeneficiarios",
                        (transaccionDetalleSubsidio.getListaIdBeneficiarios() != null
                                && !transaccionDetalleSubsidio.getListaIdBeneficiarios().isEmpty())
                                ? transaccionDetalleSubsidio.getListaIdBeneficiarios().size() : null)
                .setParameter("listaIdBeneficiarios", transaccionDetalleSubsidio.getListaIdBeneficiarios())
                .setParameter("medioDePago", transaccionDetalleSubsidio.getMedioDePago() != null ? transaccionDetalleSubsidio.getMedioDePago().toString() : null)
                .setParameter("periodoLiquidado", periodoLiquidacionAsociada).getResultList();

        if (lstTransaccionesAbonoCobradosResult != null && !lstTransaccionesAbonoCobradosResult.isEmpty()) {
            lstCuentaAdministradorSubsidioDTO = new ArrayList<>();
            for (Object[] arrTransaccionAbonoCobrado : lstTransaccionesAbonoCobradosResult) {
                cuentaAdministradorSubsidioDTO = new CuentaAdministradorSubsidioDTO();
                cuentaAdministradorSubsidioDTO.setIdCuentaAdministradorSubsidio(
                        (arrTransaccionAbonoCobrado[0] != null) ? Long.parseLong(arrTransaccionAbonoCobrado[0].toString()) : null);

                detalleSubsidioAsignadoDTO = new DetalleSubsidioAsignadoDTO();
                Date periodo = null;
                if (arrTransaccionAbonoCobrado[1] != null) {
                    Calendar periodoLiquidado = Calendar.getInstance();
                    periodoLiquidado.setTime((Date) arrTransaccionAbonoCobrado[1]);
                    periodo = CalendarUtils.formatearFechaSinHora(periodoLiquidado).getTime();
                    detalleSubsidioAsignadoDTO.setPeriodoLiquidado(periodo);
                }
                detalleSubsidioAsignadoDTO.setFechaTransaccionRetiro((arrTransaccionAbonoCobrado[45] != null) ? (Date) arrTransaccionAbonoCobrado[45] : null);

                detalleSubsidioAsignadoDTO.setIdSolicitudLiquidacionSubsidio(
                        (arrTransaccionAbonoCobrado[2] != null) ? Long.parseLong(arrTransaccionAbonoCobrado[2].toString()) : null);

                detalleSubsidioAsignadoDTO
                        .setFechaHoraCreacion((arrTransaccionAbonoCobrado[3] != null) ? (Date) arrTransaccionAbonoCobrado[3] : null);

                EmpleadorModeloDTO empleadorModeloDTO = new EmpleadorModeloDTO();
                empleadorModeloDTO.setIdEmpleador(
                        (arrTransaccionAbonoCobrado[4] != null) ? Long.parseLong(arrTransaccionAbonoCobrado[4].toString()) : null);
                empleadorModeloDTO.setTipoIdentificacion((arrTransaccionAbonoCobrado[5] != null)
                        ? TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(arrTransaccionAbonoCobrado[5].toString()) : null);
                empleadorModeloDTO.setNumeroIdentificacion(
                        (arrTransaccionAbonoCobrado[5] != null) ? arrTransaccionAbonoCobrado[6].toString() : null);
                empleadorModeloDTO
                        .setRazonSocial((arrTransaccionAbonoCobrado[7] != null) ? arrTransaccionAbonoCobrado[7].toString() : null);
                detalleSubsidioAsignadoDTO.setEmpleador(empleadorModeloDTO);

                AfiliadoModeloDTO afiliadoModeloDTO = new AfiliadoModeloDTO();
                afiliadoModeloDTO.setIdAfiliado(
                        (arrTransaccionAbonoCobrado[8] != null) ? Long.parseLong(arrTransaccionAbonoCobrado[8].toString()) : null);
                afiliadoModeloDTO.setTipoIdentificacion((arrTransaccionAbonoCobrado[9] != null)
                        ? TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(arrTransaccionAbonoCobrado[9].toString()) : null);
                afiliadoModeloDTO.setNumeroIdentificacion(
                        (arrTransaccionAbonoCobrado[10] != null) ? arrTransaccionAbonoCobrado[10].toString() : null);
                afiliadoModeloDTO
                        .setPrimerNombre((arrTransaccionAbonoCobrado[11] != null) ? arrTransaccionAbonoCobrado[11].toString() : null);
                afiliadoModeloDTO
                        .setSegundoNombre((arrTransaccionAbonoCobrado[12] != null) ? arrTransaccionAbonoCobrado[12].toString() : null);
                afiliadoModeloDTO
                        .setPrimerApellido((arrTransaccionAbonoCobrado[13] != null) ? arrTransaccionAbonoCobrado[13].toString() : null);
                afiliadoModeloDTO.setSegundoApellido(
                        (arrTransaccionAbonoCobrado[14] != null) ? arrTransaccionAbonoCobrado[14].toString() : null);
                detalleSubsidioAsignadoDTO.setAfiliadoPrincipal(afiliadoModeloDTO);

                detalleSubsidioAsignadoDTO.setIdBeneficiarioDetalle(
                        (arrTransaccionAbonoCobrado[15] != null) ? Long.parseLong(arrTransaccionAbonoCobrado[15].toString()) : null);

                PersonaModeloDTO beneficiarioPersonaModeloDTO = new PersonaModeloDTO();
                beneficiarioPersonaModeloDTO.setTipoIdentificacion((arrTransaccionAbonoCobrado[16] != null)
                        ? TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(arrTransaccionAbonoCobrado[16].toString()) : null);
                beneficiarioPersonaModeloDTO.setNumeroIdentificacion(
                        (arrTransaccionAbonoCobrado[17] != null) ? arrTransaccionAbonoCobrado[17].toString() : null);
                beneficiarioPersonaModeloDTO
                        .setPrimerNombre((arrTransaccionAbonoCobrado[18] != null) ? arrTransaccionAbonoCobrado[18].toString() : null);
                beneficiarioPersonaModeloDTO
                        .setSegundoNombre((arrTransaccionAbonoCobrado[19] != null) ? arrTransaccionAbonoCobrado[19].toString() : null);
                beneficiarioPersonaModeloDTO
                        .setPrimerApellido((arrTransaccionAbonoCobrado[20] != null) ? arrTransaccionAbonoCobrado[20].toString() : null);
                beneficiarioPersonaModeloDTO.setSegundoApellido(
                        (arrTransaccionAbonoCobrado[21] != null) ? arrTransaccionAbonoCobrado[21].toString() : null);
                detalleSubsidioAsignadoDTO.setBeneficiario(beneficiarioPersonaModeloDTO);

                detalleSubsidioAsignadoDTO.setIdAdministradorSubsidio(
                        (arrTransaccionAbonoCobrado[22] != null) ? Long.parseLong(arrTransaccionAbonoCobrado[22].toString()) : null);

                PersonaModeloDTO admonSubsidioPersonaModeloDTO = new PersonaModeloDTO();
                admonSubsidioPersonaModeloDTO.setTipoIdentificacion((arrTransaccionAbonoCobrado[23] != null)
                        ? TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(arrTransaccionAbonoCobrado[23].toString()) : null);
                admonSubsidioPersonaModeloDTO.setNumeroIdentificacion(
                        (arrTransaccionAbonoCobrado[24] != null) ? arrTransaccionAbonoCobrado[24].toString() : null);
                admonSubsidioPersonaModeloDTO
                        .setPrimerNombre((arrTransaccionAbonoCobrado[25] != null) ? arrTransaccionAbonoCobrado[25].toString() : null);
                admonSubsidioPersonaModeloDTO
                        .setSegundoNombre((arrTransaccionAbonoCobrado[26] != null) ? arrTransaccionAbonoCobrado[26].toString() : null);
                admonSubsidioPersonaModeloDTO
                        .setPrimerApellido((arrTransaccionAbonoCobrado[27] != null) ? arrTransaccionAbonoCobrado[27].toString() : null);
                admonSubsidioPersonaModeloDTO.setSegundoApellido(
                        (arrTransaccionAbonoCobrado[28] != null) ? arrTransaccionAbonoCobrado[28].toString() : null);
                detalleSubsidioAsignadoDTO.setAdministradorSubsidio(admonSubsidioPersonaModeloDTO);

                detalleSubsidioAsignadoDTO.setIdDetalleSubsidioAsignado(
                        (arrTransaccionAbonoCobrado[43] != null) ? Long.parseLong(arrTransaccionAbonoCobrado[43].toString()) : null);

                //se establece de codigo de grupo familiar en el campo identificadorGrupoFamiliar
                detalleSubsidioAsignadoDTO.setIdGrupoFamiliar(
                        (arrTransaccionAbonoCobrado[44] != null) ? Long.parseLong(arrTransaccionAbonoCobrado[44].toString()) : null);

                cuentaAdministradorSubsidioDTO.setDetalleSubsidioAsignadoDTO(detalleSubsidioAsignadoDTO);

                cuentaAdministradorSubsidioDTO.setIdMedioDePago(
                        (arrTransaccionAbonoCobrado[29] != null) ? Long.parseLong(arrTransaccionAbonoCobrado[29].toString()) : null);
                cuentaAdministradorSubsidioDTO.setMedioDePago((arrTransaccionAbonoCobrado[30] != null)
                        ? TipoMedioDePagoEnum.obtenerTipoMedioDePagoEnum(arrTransaccionAbonoCobrado[30].toString()) : null);
                cuentaAdministradorSubsidioDTO.setNumeroTarjetaAdminSubsidio(
                        (arrTransaccionAbonoCobrado[31] != null) ? arrTransaccionAbonoCobrado[31].toString() : null);
                cuentaAdministradorSubsidioDTO.setCodigoBancoAdminSubsidio(
                        (arrTransaccionAbonoCobrado[32] != null) ? arrTransaccionAbonoCobrado[32].toString() : null);
                cuentaAdministradorSubsidioDTO.setNombreBancoAdminSubsidio(
                        (arrTransaccionAbonoCobrado[33] != null) ? arrTransaccionAbonoCobrado[33].toString() : null);
                cuentaAdministradorSubsidioDTO.setTipoCuentaAdminSubsidio((arrTransaccionAbonoCobrado[34] != null)
                        ? TipoCuentaEnum.obtenerTipoCuentaEnum(arrTransaccionAbonoCobrado[34].toString()) : null);
                cuentaAdministradorSubsidioDTO.setNumeroCuentaAdminSubsidio(
                        (arrTransaccionAbonoCobrado[35] != null) ? arrTransaccionAbonoCobrado[35].toString() : null);
                cuentaAdministradorSubsidioDTO.setTipoIdentificacionTitularCuentaAdminSubsidio((arrTransaccionAbonoCobrado[36] != null)
                        ? TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(arrTransaccionAbonoCobrado[36].toString()) : null);

                cuentaAdministradorSubsidioDTO.setNumeroIdentificacionTitularCuentaAdminSubsidio(
                        (arrTransaccionAbonoCobrado[37] != null) ? arrTransaccionAbonoCobrado[37].toString() : null);
                cuentaAdministradorSubsidioDTO.setNombreTitularCuentaAdminSubsidio(
                        (arrTransaccionAbonoCobrado[38] != null) ? arrTransaccionAbonoCobrado[38].toString() : null);
                cuentaAdministradorSubsidioDTO.setFechaHoraTransaccion(
                        (arrTransaccionAbonoCobrado[39] != null) ? (Date) arrTransaccionAbonoCobrado[39] : null);
                cuentaAdministradorSubsidioDTO.setUsuarioCreacionRegistro(
                        (arrTransaccionAbonoCobrado[40] != null) ? arrTransaccionAbonoCobrado[40].toString() : null);
                cuentaAdministradorSubsidioDTO.setValorOriginalTransaccion(
                        (arrTransaccionAbonoCobrado[41] != null) ? new BigDecimal(arrTransaccionAbonoCobrado[41].toString()) : null);
                cuentaAdministradorSubsidioDTO.setValorRealTransaccion(
                        (arrTransaccionAbonoCobrado[42] != null) ? new BigDecimal(arrTransaccionAbonoCobrado[42].toString()) : null);

                lstCuentaAdministradorSubsidioDTO.add(cuentaAdministradorSubsidioDTO);
            }
        } else {
            logger.warn(firmaMetodo + ": No se logro obtener resultados para la los criterios de busqueda");

        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstCuentaAdministradorSubsidioDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#registrarAnulacionSubsidioCobrado(com.asopagos.subsidiomonetario.pagos.dto.SolicitudAnulacionSubsidioCobradoDTO,
     * java.lang.String)
     */
    @Override
    public SolicitudAnulacionSubsidioCobradoDTO registrarAnulacionSubsidioCobrado(
            SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobradoDTO, String nombreUsuario) {
        String firmaMetodo = "ConsultasModeloCore.registrarAnulacionSubsidioCobrado(SolicitudAnulacionSubsidioCobradoDTO, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Solicitud solicitudGlobal = null;
        SolicitudAnulacionSubsidioCobrado solAnulacionSubsidioCobrado = null;
        Long idSolicitud = null;
        Long idSolicitudAnulacionSubsidioCobrado = null;


        //Guardar una nueva solicitud global
        solicitudGlobal = crearSolicitudGlobal(nombreUsuario);
        if (solicitudGlobal != null) {
            idSolicitud = solicitudGlobal.getIdSolicitud();
            // Agregar id solicitud a la respuesta
            solicitudAnulacionSubsidioCobradoDTO.setIdSolicitud(idSolicitud);
            solAnulacionSubsidioCobrado = crearSolicitudAnulacionSubsidioCobrado(idSolicitud);
            if (solAnulacionSubsidioCobrado != null) {
                idSolicitudAnulacionSubsidioCobrado = solAnulacionSubsidioCobrado.getIdSolicitudAnulacionSubsidioCobrado();
                solicitudAnulacionSubsidioCobradoDTO.setIdSolicitudAnulacionSubsidioCobrado(idSolicitudAnulacionSubsidioCobrado);

                //asociamos los registros de transacciones de los abonos cobrados a la solicitud de anulación
                asociarAbonosSolicitudAnulacionSubsidioCobrado(idSolicitudAnulacionSubsidioCobrado,
                        solicitudAnulacionSubsidioCobradoDTO.getAbonosAnulacionSubsidioCobradoDTO());
                solicitudAnulacionSubsidioCobradoDTO.setRegistroExitoso(Boolean.TRUE);
            } else {
                solicitudAnulacionSubsidioCobradoDTO
                        .setCausaError(PagosSubsidioMonetarioConstants.ERROR_MESSAGE_CREACION_SOLICITUD_ANULACION);
                solicitudAnulacionSubsidioCobradoDTO.setRegistroExitoso(Boolean.FALSE);
                logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo
                        + ": No se logro realizar la creacion de la solicitud de anulación de subsidio cobrado");
            }
        } else {
            solicitudAnulacionSubsidioCobradoDTO.setCausaError(PagosSubsidioMonetarioConstants.ERROR_MESSAGE_CREACION_SOLICITUD_GLOBAL);
            solicitudAnulacionSubsidioCobradoDTO.setRegistroExitoso(Boolean.FALSE);
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo
                    + ": No se logro realizar la creacion de la solicitud global para la solicitud de anulación de subsidio cobrado");
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return solicitudAnulacionSubsidioCobradoDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#mostrarNombreConveniosTerceroPagador()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ConvenioTercerPagadorDTO> mostrarNombreConveniosTerceroPagador() {
        String firmaMetodo = "ConsultasModeloCore.mostrarNombreConveniosTerceroPagador()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ConvenioTercerPagadorDTO> listaConvenios = null;

        try {
            listaConvenios = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_NOMBRES_CONVENIOS_TERCEROS_PAGADORES, ConvenioTercerPagadorDTO.class)
                    .getResultList();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + "Error en la busqueda de convenios");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listaConvenios.isEmpty() ? null : listaConvenios;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#actualizarRegistroOperacionSubsidio(java.lang.Long,
     * java.lang.String)
     */
    @Override
    public void actualizarRegistroOperacionSubsidio(Long identificadorRespuesta, String parametrosOUT, String tiempo, String url) {
        String firmaMetodo = "ConsultasModeloCore.actualizarRegistroOperacionSubsidio()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        logger.info("identificadorRespuesta actualizarRegistroOperacionSubsidio" +identificadorRespuesta);

        //se busca el registro de operaciones del subsidio.
        RegistroOperacionTransaccionSubsidio registroOperacionesSubsidio = buscarRegistroOperacionSubsidio(identificadorRespuesta);
        //se actualiza el registro de operaciones de subsidio
        modificarRegistroOperacionSubsidio(registroOperacionesSubsidio, parametrosOUT, tiempo, url);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#buscarRetiroPorIdTransaccionTerceroPagadorRetiro(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public CuentaAdministradorSubsidioDTO buscarRetiroPorIdTransaccionTerceroPagadorRetiro(String idTransaccionTercerPagador,
                                                                                           String usuario, String idPuntoCobro) {
        String firmaMetodo = "ConsultasModeloCore.buscarIdTransaccionTerceroPagadorRetiro(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        CuentaAdministradorSubsidioDTO respuesta = null;
        //se valida que si viene null por ventanilla se retorne un nulo para seguir el proceso
        if (idTransaccionTercerPagador != null) {

            try {
                if (idPuntoCobro != null && !idPuntoCobro.equals("0")) {
                    respuesta = entityManagerCore
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_ADMIN_SUBDIO_POR_ID_TRANSACCION_TERCERO_PAGADOR,
                                    CuentaAdministradorSubsidioDTO.class)
                            .setParameter("idTransaccion", idTransaccionTercerPagador)
                            .setParameter("usuario", usuario)
                            .setParameter("idPuntoCobro", idPuntoCobro).getSingleResult();
                } else {
                    respuesta = entityManagerCore
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_ADMIN_SUBDIO_POR_ID_TRANSACCION_TERCERO,
                                    CuentaAdministradorSubsidioDTO.class)
                            .setParameter("idTransaccion", idTransaccionTercerPagador).getSingleResult();
                }
            } catch (NoResultException e) {
                respuesta = null;
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuesta;
    }

    public boolean buscarExistenciaRetiroPorIdTransaccionTerceroPagadorRetiro(
            String idTransaccionTercerPagador, String usuario, String idPuntoCobro) {
        String firmaMetodo = "ConsultasModeloCore.buscarExistenciaRetiroPorIdTransaccionTerceroPagadorRetiro(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
                
        Object consulta = null;
        boolean respuesta = false;
        // se valida que si viene null por ventanilla se retorne un nulo para seguir el
        // proceso
        if (idTransaccionTercerPagador != null) {

            try {
                if (idPuntoCobro != null && !idPuntoCobro.equals("0")) {
                    consulta = entityManagerCore
                            .createNamedQuery(
                                    NamedQueriesConstants.CONSULTAR_EXISTENCIA_CUENTA_ADMIN_SUBDIO_POR_ID_TRANSACCION_TERCERO_PAGADOR)
                            .setParameter("idTransaccion", idTransaccionTercerPagador)
                            .setParameter("usuario", usuario)
                            .setParameter("idPuntoCobro", idPuntoCobro)
                            .getSingleResult();
                } else {
                    usuario = null;
                    idPuntoCobro = null;
                    consulta = entityManagerCore
                            .createNamedQuery(
                                    NamedQueriesConstants.CONSULTAR_EXISTENCIA_CUENTA_ADMIN_SUBDIO_POR_ID_TRANSACCION_TERCERO_PAGADOR)
                            .setParameter("idTransaccion", idTransaccionTercerPagador)
                            .setParameter("usuario", usuario)
                            .setParameter("idPuntoCobro", idPuntoCobro)
                            .getSingleResult();
                }
                if(consulta != null){
                    respuesta = true;
                }
            } catch (NoResultException e) {
                respuesta = false;
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuesta;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarMediosDePagoRelacionadosAdminSubsidio(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public MedioDePagoModeloDTO consultarMediosDePagoPorId(Long idMedioDePago) {
        String firmaMetodo = "ConsultasModeloCore.consultarMediosDePagoPorId(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        MedioDePagoModeloDTO medioDePago = null;

        try {
            medioDePago = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIO_DE_PAGO_POR_ID,
                            MedioDePagoModeloDTO.class)
                    .setParameter("idMedioDePago", idMedioDePago).getSingleResult();
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return medioDePago;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarMediosDePagoRelacionadosAdminSubsidio(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<TipoMedioDePagoEnum> consultarMediosDePagoRelacionadosAdminSubsidio(Long idAdminSubsidio) {
        String firmaMetodo = "ConsultasModeloCore.consultarMediosDePagoRelacionadosAdminSubsidio(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<TipoMedioDePagoEnum> mediosDePago = null;

        try {
            mediosDePago = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIOS_DE_PAGO_ASOCIADOS_ADMINISTRADOR_SUBSIDIO,
                            TipoMedioDePagoEnum.class)
                    .setParameter("idAdminSubsidio", idAdminSubsidio).getResultList();
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        //siempre se muestra por defecto efectivo
        mediosDePago.add(TipoMedioDePagoEnum.EFECTIVO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return mediosDePago;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarMediosDePagoRelacionadosAdminSubsidio(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<TipoMedioDePagoEnum> consultarMediosDePagosInactivosRelacionadosAdminSubsidio(Long idAdminSubsidio, List<Long> cuentas) {
        String firmaMetodo = "ConsultasModeloCore.consultarMediosDePagosInactivosRelacionadosAdminSubsidio(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<TipoMedioDePagoEnum> mediosDePago = new ArrayList<TipoMedioDePagoEnum>();

        List<Object> mediosObjecto = null;

        //try {
        mediosObjecto = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIOS_DE_PAGO_INACTIVOS_ASOCIADOS_ADMINISTRADOR_SUBSIDIO)
                .setParameter("idAdminSubsidio", idAdminSubsidio)
                .setParameter("cuentas", cuentas).getResultList();

        for (Object object : mediosObjecto) {
            if (object != null)
                mediosDePago.add(TipoMedioDePagoEnum.valueOf(object.toString()));
        }
        /*} catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        */
        //siempre se muestra por defecto efectivo
        mediosDePago.add(TipoMedioDePagoEnum.EFECTIVO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return mediosDePago;
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarMedioDePagoAsignarAdminSubsidio(com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum, java.lang.Long, java.util.List)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<MedioDePagoModeloDTO> consultarMedioDePagoAsignarAdminSubsidio(TipoMedioDePagoEnum medioDePago, Long idAdminSubsidio, List<Long> lstIdsCuentas) {
        String firmaMetodo = "ConsultasModeloCore.consultarMedioDePagoAsignarAdminSubsidio(TipoMedioDePagoEnum,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<MedioDePagoModeloDTO> mediosDePago = null;

        if (TipoMedioDePagoEnum.TARJETA.equals(medioDePago)) {

            try {

                List<MedioTarjeta> mediosDePagoTarjetas = entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIOS_DE_PAGO_ADMIN_SUBSIDIO_POR_TIPO_TARJETA_ACTIVO_CAMBIO,
                                MedioTarjeta.class)
                        .setParameter("idAdminSubsidio", idAdminSubsidio)
                        .setParameter("lstIdsCuentas", lstIdsCuentas)
//                        .setParameter("sizeMediosDePago", (lstMediosDePago == null || lstMediosDePago.isEmpty()) ? null : 1)
                        .getResultList();

                mediosDePago = new ArrayList<>();
                for (MedioTarjeta medioTarjeta : mediosDePagoTarjetas) {
                    MedioDePagoModeloDTO modeloDTO = new MedioDePagoModeloDTO();
                    modeloDTO.convertToDTO(medioTarjeta);
                    mediosDePago.add(modeloDTO);
                }

            } catch (Exception e) {
                logger.error("Ocurrió un error inesperado", e);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }

        } else if (TipoMedioDePagoEnum.TRANSFERENCIA.equals(medioDePago)) {

            try {

                mediosDePago = entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIOS_DE_PAGO_ADMIN_SUBSIDIO_POR_TIPO_TRANSEFERENCIA_ACTIVO_CAMBIO,
                                MedioDePagoModeloDTO.class)
                        .setParameter("idAdminSubsidio", idAdminSubsidio)
                        .setParameter("lstIdsCuentas", lstIdsCuentas)
//                        .setParameter("sizeMediosDePago", (lstMediosDePago == null || lstMediosDePago.isEmpty()) ? null : 1)
                        .getResultList();

            } catch (Exception e) {
                logger.error("Ocurrió un error inesperado", e);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }

        } else {

            try {

                List<MedioEfectivo> mediosDePagoEfectivos = entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIOS_DE_PAGO_ADMIN_SUBSIDIO_POR_TIPO_EFECTIVO_CAMBIO,
                                MedioEfectivo.class)
                        .setParameter("idAdminSubsidio", idAdminSubsidio).getResultList();

                mediosDePago = new ArrayList<>();
                //for (MedioEfectivo medioEfectivo : mediosDePagoEfectivos) {
                MedioDePagoModeloDTO modeloDTO = new MedioDePagoModeloDTO();
                if (!mediosDePagoEfectivos.isEmpty()) {
                    modeloDTO.convertToDTO(mediosDePagoEfectivos.get(0));
                    mediosDePago.add(modeloDTO);
                }
                //}

            } catch (Exception e) {
                logger.error("Ocurrió un error inesperado", e);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return mediosDePago.isEmpty() ? null : mediosDePago;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarMedioDePagoAsignarAdminSubsidio(com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum, java.lang.Long, java.util.List)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<MedioDePagoModeloDTO> consultarMedioDePagoInactivoAsignarAdminSubsidio(TipoMedioDePagoEnum medioDePago, Long idAdminSubsidio, List<Long> lstMediosDePago) {
        String firmaMetodo = "ConsultasModeloCore.consultarMedioDePagoInactivoAsignarAdminSubsidio(TipoMedioDePagoEnum,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<MedioDePagoModeloDTO> mediosDePago = null;

        if (TipoMedioDePagoEnum.TARJETA.equals(medioDePago)) {

            try {

                List<MedioTarjeta> mediosDePagoTarjetas = entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIOS_DE_PAGO_INACTIVO_ADMIN_SUBSIDIO_POR_TIPO_TARJETA,
                                MedioTarjeta.class)
                        .setParameter("idAdminSubsidio", idAdminSubsidio)
//                        .setParameter("lstIdsMediosDePago", lstMediosDePago)
//                        .setParameter("sizeMediosDePago", (lstMediosDePago == null || lstMediosDePago.isEmpty()) ? null : 1)
                        .getResultList();

                mediosDePago = new ArrayList<>();
                for (MedioTarjeta medioTarjeta : mediosDePagoTarjetas) {
                    MedioDePagoModeloDTO modeloDTO = new MedioDePagoModeloDTO();
                    modeloDTO.convertToDTO(medioTarjeta);
                    mediosDePago.add(modeloDTO);
                }

            } catch (Exception e) {
                logger.error("Ocurrió un error inesperado", e);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }

        } else if (TipoMedioDePagoEnum.TRANSFERENCIA.equals(medioDePago)) {

            try {

                mediosDePago = entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIOS_DE_PAGO_INACTIVO_ADMIN_SUBSIDIO_POR_TIPO_TRANSEFERENCIA,
                                MedioDePagoModeloDTO.class)
                        .setParameter("idAdminSubsidio", idAdminSubsidio)
//                        .setParameter("lstIdsMediosDePago", lstMediosDePago)
//                        .setParameter("sizeMediosDePago", (lstMediosDePago == null || lstMediosDePago.isEmpty()) ? null : 1)
                        .getResultList();

            } catch (Exception e) {
                logger.error("Ocurrió un error inesperado", e);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }

        } else {

            try {

                List<MedioEfectivo> mediosDePagoEfectivos = entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIOS_DE_PAGO_INACTIVO_ADMIN_SUBSIDIO_POR_TIPO_EFECTIVO,
                                MedioEfectivo.class)
                        .setParameter("idAdminSubsidio", idAdminSubsidio).getResultList();

                mediosDePago = new ArrayList<>();
                for (MedioEfectivo medioEfectivo : mediosDePagoEfectivos) {
                    MedioDePagoModeloDTO modeloDTO = new MedioDePagoModeloDTO();
                    modeloDTO.convertToDTO(medioEfectivo);
                    mediosDePago.add(modeloDTO);
                }

            } catch (Exception e) {
                logger.error("Ocurrió un error inesperado", e);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return mediosDePago.isEmpty() ? null : mediosDePago;
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarDetallesSubsidioAsignadosSaldoAdminSubsidio(java.util.List)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesSubsidioAsignadosSaldoAdminSubsidio(
            List<CuentaAdministradorSubsidioDTO> listaCuentaAdminSubsidio) {

        String firmaMetodo = "ConsultasModeloCore.consultarDetallesSubsidioAsignadosSaldoAdminSubsidio(List<CuentaAdministradorSubsidioDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<String> listaIdCuentas = new ArrayList<>();

        for (CuentaAdministradorSubsidioDTO cuenta : listaCuentaAdminSubsidio) {
            listaIdCuentas.add(cuenta.getIdCuentaAdministradorSubsidio().toString());
        }

        List<Object[]> listaDetallesObjetos = null;

        List<DetalleSubsidioAsignadoDTO> listaDetalles = new ArrayList<>();

        try {
            listaDetallesObjetos = entityManagerCore
                    .createNamedQuery(
                            NamedQueriesConstants.CONSULTAR_DETALLES_SUBSIDIOS_ASIGNADOS_RELACIONADOS_CON_CONSULTA_SALDO_ADMIN_SUBSIDIO)
                    .setParameter("listaIdCuentas", listaIdCuentas).getResultList();

            for (int i = 0; i < listaDetallesObjetos.size(); i++) {

                DetalleSubsidioAsignadoDTO detalle = new DetalleSubsidioAsignadoDTO();

                //0. Identificador transacción abono relacionado 
                detalle.setPeriodoLiquidado((Date) listaDetallesObjetos.get(i)[0]);
                //Se crea el empleador
                EmpleadorModeloDTO empleadorModeloDTO = new EmpleadorModeloDTO();
                //1. Razon social empleador
                empleadorModeloDTO.setRazonSocial(listaDetallesObjetos.get(i)[1].toString());
                //2. Número Identificación empleador
                empleadorModeloDTO.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((listaDetallesObjetos.get(i)[2]).toString()));
                //3. Tipo Identificación empleador
                empleadorModeloDTO.setNumeroIdentificacion(listaDetallesObjetos.get(i)[3].toString());
                detalle.setEmpleador(empleadorModeloDTO);
                //Se crea el afiliado
                AfiliadoModeloDTO afiliadoModeloDTO = new AfiliadoModeloDTO();
                //4. Tipo Identificación afiliado
                afiliadoModeloDTO.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((listaDetallesObjetos.get(i)[4]).toString()));
                //5. Número de Identificación Afiliado
                afiliadoModeloDTO.setNumeroIdentificacion((listaDetallesObjetos.get(i)[5]).toString());
                //6. Primer Nombre Afiliado 
                afiliadoModeloDTO.setPrimerNombre((listaDetallesObjetos.get(i)[6]).toString());
                //7. Segundo Nombre Afiliado
                if (listaDetallesObjetos.get(i)[7] != null)
                    afiliadoModeloDTO.setSegundoApellido((listaDetallesObjetos.get(i)[7]).toString());
                //8. Primer Apellido Afiliado
                afiliadoModeloDTO.setPrimerApellido((listaDetallesObjetos.get(i)[8]).toString());
                //9. Segundo Apellido Afiliado
                if (listaDetallesObjetos.get(i)[9] != null)
                    afiliadoModeloDTO.setSegundoApellido((listaDetallesObjetos.get(i)[9]).toString());
                detalle.setAfiliadoPrincipal(afiliadoModeloDTO);
                //10. codigo grupo familiar
                detalle.setNumeroGrupoFamilarRelacionador(Short.valueOf((listaDetallesObjetos.get(i)[10]).toString()));
                //Se crea el beneficiario
                PersonaModeloDTO beneficiarioDTO = new PersonaModeloDTO();
                //11. Tipo de Identificación del beneficiario
                beneficiarioDTO.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((listaDetallesObjetos.get(i)[11]).toString()));
                //12. Número de Identificacion del beneficiario
                beneficiarioDTO.setNumeroIdentificacion((listaDetallesObjetos.get(i)[12]).toString());
                //13. Primer nombre del beneficiario
                beneficiarioDTO.setPrimerNombre((listaDetallesObjetos.get(i)[13]).toString());
                //14. Segundo nombre del beneficiario
                if (listaDetallesObjetos.get(i)[14] != null)
                    beneficiarioDTO.setSegundoNombre((listaDetallesObjetos.get(i)[14]).toString());
                //15. Primer apellido del beneficiario
                beneficiarioDTO.setPrimerApellido((listaDetallesObjetos.get(i)[15]).toString());
                //16. Segundo apellido del beneficiario
                if (listaDetallesObjetos.get(i)[16] != null)
                    beneficiarioDTO.setSegundoApellido((listaDetallesObjetos.get(i)[16]).toString());
                detalle.setBeneficiario(beneficiarioDTO);
                //17. valor subsidio asignado
                detalle.setValorSubsidioMonetario(BigDecimal.valueOf(Double.parseDouble((listaDetallesObjetos.get(i)[17]).toString())));
                //18. valor descuento
                detalle.setValorDescuento(BigDecimal.valueOf(Double.parseDouble((listaDetallesObjetos.get(i)[18]).toString())));
                //19. valor total
                detalle.setValorTotal(BigDecimal.valueOf(Double.parseDouble((listaDetallesObjetos.get(i)[19]).toString())));
                //20. Tipo descuento (código entidad de descuento, si se encuentra asociada)
                if (listaDetallesObjetos.get(i)[20] != null)
                    detalle.setTipoDescuento(Long.valueOf((listaDetallesObjetos.get(i)[20]).toString()));

                listaDetalles.add(detalle);
            }

        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listaDetalles.isEmpty() ? null : listaDetalles;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarAbonosEstadoSolicitadoPorSolicitudRetiro(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarAbonosEstadoSolicitadoPorSolicitudRetiro(TipoIdentificacionEnum tipoIdAdmin,
                                                                                                  String numeroIdAdmin, String idTransaccionTercerPagador) {
        String firmaMetodo = "ConsultasModeloCore.consultarAbonosEstadoSolicitadoPorSolicitudRetiro(TipoIdentificacionEnum, String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<CuentaAdministradorSubsidioDTO> listaAbonos = null;

        try {
            listaAbonos = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ABONOS_ESTADO_SOLICITADO_POR_RETIRO_ADMIN_SUBSIDIO,
                            CuentaAdministradorSubsidioDTO.class)
                    .setParameter("idTransaccionTerceroPagador", idTransaccionTercerPagador).setParameter("tipoIdAdmin", tipoIdAdmin)
                    .setParameter("numeroIdAdmin", numeroIdAdmin).getResultList();

        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listaAbonos.isEmpty() ? null : listaAbonos;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarAbonosEstadoSolicitadoPorSolicitudRetiro(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarAbonosEstadoSolicitadoPorSolicitudRetiro(TipoIdentificacionEnum tipoIdAdmin,
                                                                                                  String numeroIdAdmin, String idTransaccionTercerPagador, String usuario, String idPuntoCobro) {
        String firmaMetodo = "ConsultasModeloCore.consultarAbonosEstadoSolicitadoPorSolicitudRetiro(TipoIdentificacionEnum, String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<CuentaAdministradorSubsidioDTO> listaAbonos = null;

        try {
            listaAbonos = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ABONOS_ESTADO_SOLICITADO_POR_RETIRO_ADMIN_SUBSIDIO_PUNTO_COBRO,
                            CuentaAdministradorSubsidioDTO.class)
                    .setParameter("idTransaccionTerceroPagador", idTransaccionTercerPagador).setParameter("tipoIdAdmin", tipoIdAdmin)
                    .setParameter("numeroIdAdmin", numeroIdAdmin)
                    .setParameter("usuario", usuario)
                    .setParameter("idPuntoCobro", idPuntoCobro).getResultList();

        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listaAbonos.isEmpty() ? null : listaAbonos;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#obtenerValorRetiroPorAdminSubsidioIdTransaccionTerceroPagador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public BigDecimal obtenerValorRetiroPorAdminSubsidioIdTransaccionTerceroPagador(TipoIdentificacionEnum tipoIdAdmin,
                                                                                    String numeroIdAdmin, String idTransaccionTercerPagador) {
        String firmaMetodo = "ConsultasModeloCore.obtenerValorRetiroPorAdminSubsidioIdTransaccionTerceroPagador(TipoIdentificacionEnum, String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        BigDecimal valorRetiro = null;

        try {
            valorRetiro = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_VALOR_RETIRO_POR_ADMIN_SUBSIDIO_E_ID_TERCERO_PAGADOR,
                            BigDecimal.class)
                    .setParameter("idTransaccionTerceroPagador", idTransaccionTercerPagador).setParameter("tipoIdAdmin", tipoIdAdmin)
                    .setParameter("numeroIdAdmin", numeroIdAdmin).getSingleResult();

        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return valorRetiro.negate();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#obtenerValorRetiroPorAdminSubsidioIdTransaccionTerceroPagador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public BigDecimal obtenerValorRetiroPorIdCuentaAdminstradorSubsidio(Long idCuentaAdminSubsidio) {
        String firmaMetodo = "ConsultasModeloCore.obtenerValorRetiroPorIdCuentaAdminstradorSubsidio(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        BigDecimal valorRetiro = null;

        valorRetiro = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_VALOR_RETIRO_POR_ID_CUENTA,
                        BigDecimal.class)
                .setParameter("idCuentaAdminSubsidio", idCuentaAdminSubsidio).getSingleResult();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return valorRetiro.negate();
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#registrarPersonaAutorizadaParaRealizarRetiro(java.lang.Long,
     * java.lang.Long, com.asopagos.dto.modelo.DocumentoSoporteModeloDTO)
     */
    @Override
    public void registrarPersonaAutorizadaParaRealizarRetiro(Long idPersona, Long idRetiroCuenta,
                                                             DocumentoSoporteModeloDTO documentoSoporte) {
        String firmaMetodo = "ConsultasModeloCore.registrarPersonaAutorizadaParaRealizarRetiro(Long, Long, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long idDocumentoSoporte = null;

        if (documentoSoporte != null) {

            DocumentoSoporte documentoSoporteAutorizacion = documentoSoporte.convertToEntity();
            //creación del documento soporte
            try {
                entityManagerCore.persist(documentoSoporteAutorizacion);
                idDocumentoSoporte = documentoSoporteAutorizacion.getIdDocumentoSoporte();
            } catch (Exception e) {
                logger.error("Ocurrió un error inesperado", e);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }
        }
        //creación del retiro de la persona autorizada.
        try {

            RetiroPersonaAutorizadaCobroSubsidio autorizadaCobroSubsidio = new RetiroPersonaAutorizadaCobroSubsidio();
            autorizadaCobroSubsidio.setIdCuentaAdministradorSubsidio(idRetiroCuenta);
            autorizadaCobroSubsidio.setIdPersonaAutorizada(idPersona);
            autorizadaCobroSubsidio.setIdDocumentoSoporte(idDocumentoSoporte);
            entityManagerCore.persist(autorizadaCobroSubsidio);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarDispersionMontoLiquidacion(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DispersionMontoLiquidadoDTO consultarDispersionMontoLiquidacion(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarDispersionMontoLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        //try {
        DispersionMontoLiquidadoDTO montoLiquidadoDTO = consultarTotales(numeroRadicacion);
        montoLiquidadoDTO.setResumenPagoTarjeta(consultarResumenPagosTarjeta(numeroRadicacion));
        montoLiquidadoDTO.setResumenPagoEfectivo(consultarResumenPagosEfectivo(numeroRadicacion));
        montoLiquidadoDTO.setResumenPagoBanco(consultarResumenPagosBanco(numeroRadicacion));
        montoLiquidadoDTO.setResumenPagoEntidadDescuento(consultarResumenPagosEntidadesDescuento(numeroRadicacion));

        DispersionResumenTotalDTO resumenTotalDTO = new DispersionResumenTotalDTO();
        resumenTotalDTO.setCantidadAdministradoresSubsidio(montoLiquidadoDTO.getCantidadAdministradorSubsidios());
        resumenTotalDTO.setCantidadCuotas(montoLiquidadoDTO.getCantidadCuotasDispersar());
        resumenTotalDTO.setMontoTotalDispersado(montoLiquidadoDTO.getMontoTotalDispersion());

        montoLiquidadoDTO.setResumenTotal(resumenTotalDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return montoLiquidadoDTO;
        /*} catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }*/
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarDispersionMontoLiquidadoPagoTarjeta(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DispersionResultadoPagoTarjetaDTO consultarDispersionMontoLiquidadoPagoTarjeta(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarDispersionMontoLiquidadoPagoTarjeta(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            DispersionResultadoPagoTarjetaDTO resultadoPagoTarjetaDTO = new DispersionResultadoPagoTarjetaDTO();

            Object[] registroEncabezado = consultarDatosEncabezadoDetallePagos(numeroRadicacion, TipoMedioDePagoEnum.TARJETA);
            resultadoPagoTarjetaDTO.setPeriodoLiquidado(CalendarUtils.darFormatoYYYYMMDDGuionDate(registroEncabezado[0].toString()));
            resultadoPagoTarjetaDTO.setFechaLiquidacion(CalendarUtils.darFormatoYYYYMMDDGuionDate(registroEncabezado[1].toString()));
            resultadoPagoTarjetaDTO.setFechaGeneracion(CalendarUtils.darFormatoYYYYMMDDGuionDate(registroEncabezado[2].toString()));

            List<Object[]> registrosPagosTarjeta = entityManagerCore
                    .createStoredProcedureQuery(NamedQueriesConstants.USP_SM_GET_CONSULTARDISPERSIONMONTOLIQUIDADO)
                    .registerStoredProcedureParameter("estadoLiquidacion", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("medioDePago", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("numeroRadicado", String.class, ParameterMode.IN)
                    .setParameter("estadoLiquidacion", EstadoTransaccionSubsidioEnum.GENERADO.name())
                    .setParameter("medioDePago", TipoMedioDePagoEnum.TARJETA.name())
                    .setParameter("numeroRadicado", numeroRadicacion).getResultList();

            List<DetalleResultadoPagoTarjetaDTO> pagosTarjetaDTO = new ArrayList<DetalleResultadoPagoTarjetaDTO>();
            for (Object[] registroPagoTarjeta : registrosPagosTarjeta) {
                DetalleResultadoPagoTarjetaDTO pagoTarjetaDTO = new DetalleResultadoPagoTarjetaDTO();

                pagoTarjetaDTO.setTipoIdentificacionAdministradorSubsidio(TipoIdentificacionEnum.valueOf(registroPagoTarjeta[0].toString()));
                pagoTarjetaDTO.setNumeroIdentificacionAdministradorSubsidio(registroPagoTarjeta[1].toString());
                pagoTarjetaDTO.setNombreAdministradorSubsidio(registroPagoTarjeta[2] != null ? registroPagoTarjeta[2].toString() : null);
                pagoTarjetaDTO.setNumeroTarjeta(registroPagoTarjeta[3] != null ? registroPagoTarjeta[3].toString() : null);

                if (!registroPagoTarjeta[4].toString().isEmpty()) {
                    pagoTarjetaDTO.setTipoIndentificacionTrabajador(TipoIdentificacionEnum.valueOf(registroPagoTarjeta[4].toString()));
                }
                if (!registroPagoTarjeta[5].toString().isEmpty()) {
                    pagoTarjetaDTO.setNumeroIdentificacionTrabajador(registroPagoTarjeta[5].toString());
                }
                if (!registroPagoTarjeta[6].toString().isEmpty()) {
                    pagoTarjetaDTO.setNombreTrabajador(registroPagoTarjeta[6].toString());
                }


                pagoTarjetaDTO.setMonto(BigDecimal.valueOf(Double.parseDouble(registroPagoTarjeta[7].toString())));
                pagosTarjetaDTO.add(pagoTarjetaDTO);
            }
            resultadoPagoTarjetaDTO.setListaDetalleAbonoTarjeta(pagosTarjetaDTO);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return resultadoPagoTarjetaDTO;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarDispersionMontoLiquidadoPagoEfectivo(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DispersionResultadoPagoEfectivoDTO consultarDispersionMontoLiquidadoPagoEfectivo(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarDispersionMontoLiquidadoPagoEfectivo(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            DispersionResultadoPagoEfectivoDTO resultadoPagoEfectivoDTO = new DispersionResultadoPagoEfectivoDTO();

            List<Long> identificadoresCondiciones = new ArrayList<>();

            Object[] registroEncabezado = consultarDatosEncabezadoDetallePagos(numeroRadicacion, TipoMedioDePagoEnum.EFECTIVO);
            resultadoPagoEfectivoDTO.setPeriodoLiquidado(CalendarUtils.darFormatoYYYYMMDDGuionDate(registroEncabezado[0].toString()));
            resultadoPagoEfectivoDTO.setFechaLiquidacion(CalendarUtils.darFormatoYYYYMMDDGuionDate(registroEncabezado[1].toString()));
            resultadoPagoEfectivoDTO.setFechaGeneracion(CalendarUtils.darFormatoYYYYMMDDGuionDate(registroEncabezado[2].toString()));

            List<Object[]> registrosPagosEfectivo = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_PAGOS_MEDIO_EFECTIVO)
                    .setParameter("numeroRadicacion", numeroRadicacion).getResultList();

            List<DetalleResultadoPagoEfectivoDTO> pagosEfectivoDTO = new ArrayList<DetalleResultadoPagoEfectivoDTO>();
            for (Object[] registroPagoEfectivo : registrosPagosEfectivo) {
                DetalleResultadoPagoEfectivoDTO pagoEfectivoDTO = new DetalleResultadoPagoEfectivoDTO();

                pagoEfectivoDTO.setNumeroOperacion(BigDecimal.valueOf(Double.parseDouble(registroPagoEfectivo[0].toString())));
                pagoEfectivoDTO
                        .setTipoIdentificacionAdministradorSubsidio(TipoIdentificacionEnum.valueOf(registroPagoEfectivo[1].toString()));
                pagoEfectivoDTO.setNumeroIdentificacionAdministradorSubsidio(registroPagoEfectivo[2].toString());
                pagoEfectivoDTO.setNombreAdministradorSubsidio(registroPagoEfectivo[3].toString());
                pagoEfectivoDTO.setMontoPendientePorDispersar(BigDecimal.valueOf(Double.parseDouble(registroPagoEfectivo[4].toString())));

                if (registroPagoEfectivo[5] != null) {
                    identificadoresCondiciones.add(Long.parseLong(registroPagoEfectivo[5].toString()));
                    pagoEfectivoDTO.setIdCondicionAdministrador(Long.parseLong(registroPagoEfectivo[5].toString()));
                }

                pagosEfectivoDTO.add(pagoEfectivoDTO);
            }
            resultadoPagoEfectivoDTO.setListaDetallePagoEfectivo(pagosEfectivoDTO);
            resultadoPagoEfectivoDTO.setIdentificadoresCondiciones(identificadoresCondiciones);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return resultadoPagoEfectivoDTO;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarDispersionMontoLiquidadoPagoBanco(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DispersionResultadoPagoBancoDTO consultarDispersionMontoLiquidadoPagoBanco(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarDispersionMontoLiquidadoPagoBanco(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            DispersionResultadoPagoBancoDTO resultadoPagoBancoDTO = new DispersionResultadoPagoBancoDTO();

            List<Long> identificadoresCondiciones = new ArrayList<>();

            //Se consulta la información del encabezado
            Object[] registroEncabezado = consultarDatosEncabezadoDetallePagos(numeroRadicacion, TipoMedioDePagoEnum.TRANSFERENCIA);
            resultadoPagoBancoDTO.setPeriodoLiquidado(CalendarUtils.darFormatoYYYYMMDDGuionDate(registroEncabezado[0].toString()));
            resultadoPagoBancoDTO.setFechaLiquidacion(CalendarUtils.darFormatoYYYYMMDDGuionDate(registroEncabezado[1].toString()));
            resultadoPagoBancoDTO.setFechaGeneracion(CalendarUtils.darFormatoYYYYMMDDGuionDate(registroEncabezado[2].toString()));

            //Se consultan los pagos a bancos
            List<Object[]> registrosPagosBanco = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_PAGOS_MEDIO_BANCO)
                    .setParameter("numeroRadicacion", numeroRadicacion).getResultList();

            List<DispersionResultadoPagoBancoConsignacionesDTO> pagosBancoDTO = new ArrayList<DispersionResultadoPagoBancoConsignacionesDTO>();
            List<DispersionResultadoPagoBancoPagoJuducialDTO> pagosJudicialesBancoDTO = new ArrayList<DispersionResultadoPagoBancoPagoJuducialDTO>();

            //Se agrupan los detalles de pago por banco
            Map<String, Integer> registrosBancoProcesados = new HashMap<String, Integer>();
            Integer indicadorRegistros = 0;
            Boolean esJudicial = null;
            StringBuilder key = new StringBuilder();
            for (Object[] registroPagoBanco : registrosPagosBanco) {

                ItemResultadoPagoBancoDTO itemPagoBancoDTO = new ItemResultadoPagoBancoDTO();
                itemPagoBancoDTO
                        .setTipoIdentificacionAdministradorSubsidio(TipoIdentificacionEnum.valueOf(registroPagoBanco[3].toString()));
                itemPagoBancoDTO.setNumeroIdentificacionAdministradorSubsidio(registroPagoBanco[4].toString());
                itemPagoBancoDTO.setNombreAdministradorSubsidio(registroPagoBanco[5].toString());
                itemPagoBancoDTO.setTipoCuenta(TipoCuentaEnum.valueOf(registroPagoBanco[6].toString()));
                itemPagoBancoDTO.setNumeroCuenta(registroPagoBanco[7].toString());
                itemPagoBancoDTO.setMonto(BigDecimal.valueOf(Double.parseDouble(registroPagoBanco[8].toString())));


                if (registroPagoBanco[9] != null) {
                    itemPagoBancoDTO.setIdCondicionAdministrador(Long.parseLong(registroPagoBanco[9].toString()));
                    identificadoresCondiciones.add(Long.parseLong(registroPagoBanco[9].toString()));
                }

                if (registroPagoBanco[10] != null && Boolean.parseBoolean(registroPagoBanco[10].toString())) {
                    esJudicial = Boolean.TRUE;
                } else {
                    esJudicial = Boolean.FALSE;
                }

                key.setLength(0);
                key.append(registroPagoBanco[0].toString());
                key.append("_");
                key.append(esJudicial.toString());

                //En caso de un registro asociado a un banco que no se ha procesado
                if (!registrosBancoProcesados.containsKey(key.toString())) {

                    if (Boolean.TRUE.equals(esJudicial)) {
                        //pago judicial
                        DispersionResultadoPagoBancoPagoJuducialDTO pagoJudicialBancoDTO = new DispersionResultadoPagoBancoPagoJuducialDTO();
                        pagoJudicialBancoDTO.setNombreBanco(registroPagoBanco[1].toString());
                        pagoJudicialBancoDTO.setNIT(registroPagoBanco[2] == null ? null : registroPagoBanco[2].toString());
                        pagoJudicialBancoDTO.setTotalMonto(itemPagoBancoDTO.getMonto());
                        pagoJudicialBancoDTO.setTipoCuenta(TipoCuentaEnum.valueOf(registroPagoBanco[6].toString()));
                        pagoJudicialBancoDTO.setCuentaBanco(registroPagoBanco[7].toString());

                        List<ItemResultadoPagoBancoDTO> itemsPagosJudicialesDTO = new ArrayList<>();

                        itemsPagosJudicialesDTO.add(itemPagoBancoDTO);
                        pagoJudicialBancoDTO.setLstConsignaciones(itemsPagosJudicialesDTO);
                        pagosJudicialesBancoDTO.add(pagoJudicialBancoDTO);

                        registrosBancoProcesados.put(key.toString(), indicadorRegistros);
                    } else {
                        //consignaciones
                        DispersionResultadoPagoBancoConsignacionesDTO pagoBancoDTO = new DispersionResultadoPagoBancoConsignacionesDTO();

                        pagoBancoDTO.setIdentificadorBanco(BigDecimal.valueOf(Double.parseDouble(registroPagoBanco[0].toString())));
                        pagoBancoDTO.setNombreBanco(registroPagoBanco[1].toString());
                        pagoBancoDTO.setNIT(registroPagoBanco[2] == null ? null : registroPagoBanco[2].toString());

                        List<ItemResultadoPagoBancoDTO> itemsPagoBancoDTO = new ArrayList<ItemResultadoPagoBancoDTO>();

                        itemsPagoBancoDTO.add(itemPagoBancoDTO);
                        pagoBancoDTO.setLstConsignaciones(itemsPagoBancoDTO);
                        pagosBancoDTO.add(pagoBancoDTO);

                        registrosBancoProcesados.put(key.toString(), indicadorRegistros);
                    }
                    indicadorRegistros++;
                } else {
                    //En caso de un registro asociado a un banco que ya se proceso

                    if (Boolean.TRUE.equals(esJudicial)) {//pagos judiciales
                        pagosJudicialesBancoDTO.stream().forEach(jud -> {
                            if (jud.getNIT().equalsIgnoreCase(registroPagoBanco[2].toString())) {
                                jud.getLstConsignaciones().add(itemPagoBancoDTO);
                                jud.setTotalMonto(
                                        BigDecimal.valueOf(jud.getTotalMonto().doubleValue() + itemPagoBancoDTO.getMonto().doubleValue()));
                            }
                        });
                        //pagosJudicialesBancoDTO.get(registrosBancoProcesados.get(registroPagoBanco[0].toString())).getLstConsignaciones()
                        //        .add(itemPagoBancoDTO);
                    } else {//consignaciones
                        pagosBancoDTO.stream().
                                filter(ban -> ban.getIdentificadorBanco().compareTo(BigDecimal.valueOf(Double.parseDouble(registroPagoBanco[0].toString()))) == 0)
                                .findFirst().get().getLstConsignaciones().add(itemPagoBancoDTO);
                    }
                }
            }
            resultadoPagoBancoDTO.setLstConsignaciones(pagosBancoDTO);
            resultadoPagoBancoDTO.setLstPagosJudiciales(pagosJudicialesBancoDTO);
            resultadoPagoBancoDTO.setIdentificadoresCondiciones(identificadoresCondiciones);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return resultadoPagoBancoDTO;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarDispersonMontoDescuentosPorEntidad(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DispersionResultadoEntidadDescuentoDTO consultarDispersionMontoDescuentosPorEntidad(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarDispersonMontoDescuentosPorEntidad(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            DispersionResultadoEntidadDescuentoDTO resultadoEntidadDescuentoDTO = new DispersionResultadoEntidadDescuentoDTO();

            List<Long> identificadoresCondiciones = new ArrayList<>();

            Object[] registroEncabezado = consultarDatosEncabezadoDetallePagos(numeroRadicacion, TipoMedioDePagoEnum.TRANSFERENCIA);
            if (registroEncabezado == null || registroEncabezado.length == 0) {
                registroEncabezado = consultarDatosEncabezadoDetallePagos(numeroRadicacion, TipoMedioDePagoEnum.TARJETA);
                if (registroEncabezado == null || registroEncabezado.length == 0) {
                    registroEncabezado = consultarDatosEncabezadoDetallePagos(numeroRadicacion, TipoMedioDePagoEnum.EFECTIVO);
                }
            }
            resultadoEntidadDescuentoDTO.setPeriodoLiquidado(CalendarUtils.darFormatoYYYYMMDDGuionDate(registroEncabezado[0].toString()));
            resultadoEntidadDescuentoDTO.setFechaLiquidacion(CalendarUtils.darFormatoYYYYMMDDGuionDate(registroEncabezado[1].toString()));
            resultadoEntidadDescuentoDTO.setFechaGeneracion(CalendarUtils.darFormatoYYYYMMDDGuionDate(registroEncabezado[2].toString()));
            resultadoEntidadDescuentoDTO.setMontoTotalDescontado(consultarTotales(numeroRadicacion).getTotalDescuentosAplicados());

            List<Object[]> registrosPagoEntidades = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_PAGOS_ENTIDADES_DESCUENTO)
                    .setParameter("numeroRadicacion", numeroRadicacion).getResultList();

            List<DetalleResultadoEntidadDescuentoDTO> pagosEntidadDTO = new ArrayList<DetalleResultadoEntidadDescuentoDTO>();

            Map<String, Integer> registrosEntidadProcesados = new HashMap<String, Integer>();
            Integer indicadorRegistros = 0;
            for (Object[] registroPagoEntidad : registrosPagoEntidades) {

                ItemResultadoEntidadDescuentoDTO itemPagoEntidadDTO = new ItemResultadoEntidadDescuentoDTO();
                itemPagoEntidadDTO
                        .setTipoIdentificacionAdministradorSubsidio(TipoIdentificacionEnum.valueOf(registroPagoEntidad[3].toString()));
                itemPagoEntidadDTO.setNumeroIdentificacionAdministradorSubsidio(registroPagoEntidad[4].toString());
                itemPagoEntidadDTO.setNombreAdministradorSubsidio(registroPagoEntidad[5].toString());
                itemPagoEntidadDTO.setNumeroOperacion(Long.parseLong(registroPagoEntidad[6].toString()));
                itemPagoEntidadDTO.setMontoDescontado(BigDecimal.valueOf(Double.parseDouble(registroPagoEntidad[7].toString())));

                if (registroPagoEntidad[8] != null) {
                    itemPagoEntidadDTO.setIdCondicionAdministrador(Long.parseLong(registroPagoEntidad[8].toString()));
                    identificadoresCondiciones.add(Long.parseLong(registroPagoEntidad[8].toString()));
                }

                //En caso de un registro asociado a una entidad que no se ha procesado
                if (!registrosEntidadProcesados.containsKey(registroPagoEntidad[0].toString())) {
                    DetalleResultadoEntidadDescuentoDTO pagoEntidadDTO = new DetalleResultadoEntidadDescuentoDTO();

                    pagoEntidadDTO.setIdentificadorEntidad(BigDecimal.valueOf(Double.parseDouble(registroPagoEntidad[0].toString())));
                    pagoEntidadDTO.setNombreEntidad(registroPagoEntidad[1].toString());
                    pagoEntidadDTO.setNIT(registroPagoEntidad[2].toString());
                    pagoEntidadDTO.setTotalEntidad(itemPagoEntidadDTO.getMontoDescontado());

                    List<ItemResultadoEntidadDescuentoDTO> itemsPagoEntidadDTO = new ArrayList<ItemResultadoEntidadDescuentoDTO>();

                    itemsPagoEntidadDTO.add(itemPagoEntidadDTO);
                    pagoEntidadDTO.setLstItemsDescuentos(itemsPagoEntidadDTO);
                    pagosEntidadDTO.add(pagoEntidadDTO);

                    registrosEntidadProcesados.put(registroPagoEntidad[0].toString(), indicadorRegistros);
                    indicadorRegistros++;
                } else {
                    //En caso de un registro asociado a una entidad que ya se proceso
                    pagosEntidadDTO.get(registrosEntidadProcesados.get(registroPagoEntidad[0].toString())).getLstItemsDescuentos()
                            .add(itemPagoEntidadDTO);
                    BigDecimal totalParcial = pagosEntidadDTO.get(registrosEntidadProcesados.get(registroPagoEntidad[0].toString()))
                            .getTotalEntidad();
                    pagosEntidadDTO.get(registrosEntidadProcesados.get(registroPagoEntidad[0].toString()))
                            .setTotalEntidad(totalParcial.add(itemPagoEntidadDTO.getMontoDescontado()));
                }

            }
            resultadoEntidadDescuentoDTO.setLstDescuentos(pagosEntidadDTO);
            resultadoEntidadDescuentoDTO.setIdentificadoresCondiciones(identificadoresCondiciones);
            logger.info(
                    "registroEncabezado:"
                            + registroEncabezado[0].toString()
                            + registroEncabezado[1].toString()
                            + registroEncabezado[2].toString()
                            + "resultadoEntidadDescuentoDTO:"
                            + resultadoEntidadDescuentoDTO
            );
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return resultadoEntidadDescuentoDTO;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#registrarTransaccionesFallidasRegistroOperacionesSubsidio(java.lang.Long,
     * java.lang.Long)
     */
    @Override
    public void registrarTransaccionesFallidasRegistroOperacionesSubsidio(Long idRegistroOperacion, Long idTransaccionFallida) {
        String firmaMetodo = "ConsultasModeloCore.consultarDispersonMontoDescuentosPorEntidad(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            TransaccionFallidaOperacionTransacionSubsidio operacionesSubsidio = new TransaccionFallidaOperacionTransacionSubsidio();
            operacionesSubsidio.setIdRegistroOperacion(idRegistroOperacion);
            operacionesSubsidio.setIdTransaccionFallida(idTransaccionFallida);
            entityManagerCore.persist(operacionesSubsidio);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarSaldoSubsidio(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Map<String, Object> consultarSaldoSubsidio(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin,
                                                      TipoMedioDePagoEnum medioDePago) {
        String firmaMetodo = "ConsultasModeloCore.consultarSaldoSubsidio(TipoIdentificacionEnum, String,TipoMedioDePagoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        //llave : valorSaldo , valor: BigDecimal valor del saldo
        //llave : nombreAdmin, valor: String nombre administrador
        Map<String, Object> respuesta = new HashMap<>();

        try {
            Object[] registros = (Object[]) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SALDO_A_FAVOR_CUENTA_ADMIN_SUBSIDIO_POR_MEDIO_DE_PAGO_ADMIN_SUBSIDIO)
                    .setParameter("tipoIdAdmin", tipoIdAdmin.toString()).setParameter("medioDePago", medioDePago.toString())
                    .setParameter("numeroIdAdmin", numeroIdAdmin).getSingleResult();

            respuesta.put("valorSaldo", BigDecimal.valueOf(Double.parseDouble(registros[0].toString())));
            respuesta.put("nombreAdmin", registros[1].toString());
            respuesta.put("idAdminSubsidio", Long.parseLong(registros[2].toString()));

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        } catch (NoResultException e) {
            respuesta = null;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuesta;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarSaldoSubsidio(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Map<String, Object> consultarSaldoSubsidioAbono(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin,
                                                           TipoMedioDePagoEnum medioDePago) {
        String firmaMetodo = "ConsultasModeloCore.consultarSaldoSubsidio(TipoIdentificacionEnum, String,TipoMedioDePagoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        //llave : valorSaldo , valor: BigDecimal valor del saldo
        //llave : nombreAdmin, valor: String nombre administrador
        Map<String, Object> respuesta = new HashMap<>();
        Object[] registros = null;
        try {
            registros = (Object[]) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SALDO_A_FAVOR_ABONO_CUENTA_ADMIN_SUBSIDIO_POR_MEDIO_DE_PAGO_ADMIN_SUBSIDIO)
                    .setParameter("tipoIdAdmin", tipoIdAdmin.toString()).setParameter("medioDePago", medioDePago.toString())
                    .setParameter("numeroIdAdmin", numeroIdAdmin).getSingleResult();

        } catch (NoResultException e) {
            logger.debug("NoResultException");
            respuesta.put("valorSaldo", BigDecimal.valueOf(0));
            respuesta.put("nombreAdmin", "");
            respuesta.put("idAdminSubsidio", "");
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return respuesta;
        }
        respuesta.put("valorSaldo", BigDecimal.valueOf(Double.parseDouble(registros[0].toString())));
        respuesta.put("nombreAdmin", registros[1].toString());
        respuesta.put("idAdminSubsidio", Long.parseLong(registros[2].toString()));

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuesta;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarConvenioTerceroPagadorPorIdEmpresa(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ConvenioTercerPagadorDTO consultarConvenioTerceroPagadorPorIdEmpresa(Long idEmpresa) {
        String firmaMetodo = "ConsultasModeloCore.consultarConvenioTerceroPagadorPorIdEmpresa(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ConvenioTercerPagadorDTO convenioTercerPagadorDTO = null;
        try {
            //se busca que la empresa relacionada al convenio no este relacionada con otro en la base de datos.
            ConvenioTerceroPagador convenioTerceroPagador = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONVENIO_TERCERO_PAGADOR_POR_ID_EMPRESA, ConvenioTerceroPagador.class)
                    .setParameter("empresaId", idEmpresa).getSingleResult();

            convenioTercerPagadorDTO = new ConvenioTercerPagadorDTO(convenioTerceroPagador);

        } catch (NoResultException e) {
            convenioTercerPagadorDTO = null;
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return convenioTercerPagadorDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarRetiroTarjetaParaReversion(java.lang.String,
     * java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public CuentaAdministradorSubsidioDTO consultarRetiroTarjetaParaReversion(String idTransaccionTerceroPagador, String numeroTarjeta) {
        String firmaMetodo = "ConsultasModeloCore.consultarConvenioTerceroPagadorPorIdEmpresa(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        CuentaAdministradorSubsidioDTO retiro = null;
        try {
            retiro = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_RETIRO_TARJETA_PARA_REALIZAR_REVERSION,
                            CuentaAdministradorSubsidioDTO.class)
                    .setParameter("idTransaccionTerceroPagador", idTransaccionTerceroPagador).setParameter("numeroTarjeta", numeroTarjeta)
                    .getSingleResult();
        } catch (NoResultException e) {
            retiro = null;
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return retiro;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarAbonosCobradosAsociadosRetiroParaReversion(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarAbonosCobradosAsociadosRetiroParaReversion(String idTransaccionTerceroPagador) {
        String firmaMetodo = "ConsultasModeloCore.consultarAbonosCobradosAsociadosRetiroParaReversion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<CuentaAdministradorSubsidioDTO> abonosCobradosRetiro = null;

        try {
            abonosCobradosRetiro = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ABONOS_COBRADOS_ASOCIADOS_RETIRO_PARA_REALIZAR_REVERSION,
                            CuentaAdministradorSubsidioDTO.class)
                    .setParameter("idTransaccionTerceroPagador", idTransaccionTerceroPagador).getResultList();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return abonosCobradosRetiro.isEmpty() ? null : abonosCobradosRetiro;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarNombreArchivoConsumoTarjetaANIBOL(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Boolean consultarNombreArchivoConsumoTarjetaANIBOL(String nombreArchivo) {
        String firmaMetodo = "ConsultasModeloCore.consultarNombreArchivoConsumoTarjetaANIBOL(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean resultado = null;
        try {
            ArchivoConsumosAnibol archivo = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_CONSUMO_TARJETA_ANIBOL_POR_NOMBRE,
                            ArchivoConsumosAnibol.class)
                    .setParameter("nombreDocumento", nombreArchivo).getSingleResult();

            if (archivo != null)
                resultado = true;
        } catch (NoResultException e) {
            resultado = false;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarNombreArchivoConsumoTarjetaANIBOL(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Boolean consultarNombreArchivoConsumoTerceroPagadorEfectivoNombre(String nombreArchivo) {
        String firmaMetodo = "ConsultasModeloCore.consultarNombreArchivoConsumoTarjetaANIBOL(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean resultado = null;
        try {
            ArchivoRetiroTerceroPagadorEfectivo archivo = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_CONSUMO_TERCERO_PAGAGOR_EFECTIVO_POR_NOMBRE,
                            ArchivoRetiroTerceroPagadorEfectivo.class)
                    .setParameter("nombreDocumento", nombreArchivo).getSingleResult();

            if (archivo != null)
                resultado = true;
        } catch (NoResultException e) {
            resultado = false;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#registrarArchivoConsumosAnibol(com.asopagos.subsidiomonetario.pagos.dto.ArchivoConsumosAnibolModeloDTO)
     */
    @Override
    public Long registrarArchivoConsumosAnibol(ArchivoConsumosAnibolModeloDTO archivoConsumosAnibolModeloDTO) {
        String firmaMetodo = "ConsultasModeloCore.registrarArchivoConsumosAnibol(ArchivoConsumosAnibolModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ArchivoConsumosAnibol archivoConsumosAnibol = archivoConsumosAnibolModeloDTO.convertToEntity();
        try {
            entityManagerCore.persist(archivoConsumosAnibol);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return archivoConsumosAnibol.getIdArchivoConsumosSubsidioMonetario();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#registrarArchivoConsumosAnibol(com.asopagos.subsidiomonetario.pagos.dto.ArchivoConsumosAnibolModeloDTO)
     */
    @Override
    public Long registrarArchivoConsumosTerceroPagadorEfectivo(ArchivoRetiroTerceroPagadorEfectivoDTO archivoConsumosDTO) {
        String firmaMetodo = "ConsultasModeloCore.registrarArchivoConsumosTerceroPagadorEfectivo(ArchivoConsumosAnibolModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ArchivoRetiroTerceroPagadorEfectivo archivoConsumos = archivoConsumosDTO.convertToEntity();

        entityManagerCore.persist(archivoConsumos);
        entityManagerCore.flush();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return archivoConsumos.getIdArchivoRetiroTerceroPagadorEfectivo();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#crearCamposInconsistenciasArchivoConsumoTarjetaANIBOL(java.lang.Long,
     * java.util.List, java.util.List, java.util.Map)
     */
    @Override
    public void crearCamposInconsistenciasArchivoConsumoTarjetaANIBOL(Long idArchivoConsumoTarjeta,
                                                                      List<TarjetaRetiroCandidatoDTO> listaErroresHallazgos, List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos,
                                                                      List<ResultadoHallazgosValidacionArchivoDTO> camposErroresPorLinea) {

        String firmaMetodo = "ConsultasModeloCore.crearCamposInconsistenciasArchivoConsumoTarjetaANIBOL(Long idArchivoConsumoTarjeta,List<TarjetaRetiroCandidatoDTO>,List<ResultadoHallazgosValidacionArchivoDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        for (TarjetaRetiroCandidatoDTO registroErrorTarjeta : listaErroresHallazgos) {

            List<ResultadoHallazgosValidacionArchivoDTO> lstValoresCamposErrores = new ArrayList<>();

            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : camposErroresPorLinea) {
                if (hallazgo.getNumeroLinea().compareTo(registroErrorTarjeta.getNumeroLineaError()) == 0) {
                    lstValoresCamposErrores.add(hallazgo);
                }
            }
            //se registra la inconsistencia de la tarjeta
            registroErrorTarjeta.setTipoInconsistenciaResultadoValidacion(
                    InconsistenciaResultadoValidacionArchivoConsumoAnibolEnum.REGISTRO_CON_CAMPOS_INCONSISTENTES);
            registroErrorTarjeta.setEstadoRegistro(EstadoRegistroArchivoConsumoAnibolEnum.PROCESADO_CON_INCONSISTENCIAS);
            //se registra la información de la tarjeta
            Long idRegistroTarjeta = crearRegistroTarjetasArchivoConsumoANIBOL(idArchivoConsumoTarjeta, registroErrorTarjeta);
            //se registra el error del registro de la tarjeta
            crearErrorCampoArchivoConsumoANIBOL(idRegistroTarjeta, lstValoresCamposErrores);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarCuentasAdminSubsidioAsociadasRegistroTarjeta(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasAdminSubsidioAsociadasRegistroTarjeta(String numeroTarjeta) {
        String firmaMetodo = "ConsultasModeloCore.consultarCuentasAdminSubsidioAsociadasRegistroTarjeta(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<CuentaAdministradorSubsidioDTO> lstCuentasRelacionadas = null;

        try {
            lstCuentasRelacionadas = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTAS_ADMIN_SUBSIDIO_RELACIONADAS_REGISTRO_TARJETA_ANIBOL,
                            CuentaAdministradorSubsidioDTO.class)
                    .setParameter("numeroTarjeta", numeroTarjeta).getResultList();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);

        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstCuentasRelacionadas.isEmpty() ? null : lstCuentasRelacionadas;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#guardarRegistrosTarjetasArchivoConsumoANIBOL(java.lang.Long,
     * java.util.List)
     */
    @Override
    public void guardarRegistrosTarjetasArchivoConsumoANIBOL(Long idArchivoConsumoTarjeta,
                                                             List<TarjetaRetiroCandidatoDTO> listaCandidatos) {
        String firmaMetodo = "ConsultasModeloCore.guardarRegistrosTarjetasArchivoConsumoANIBOL(Long idArchivoConsumoTarjeta,List<TarjetaRetiroCandidatoDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        for (TarjetaRetiroCandidatoDTO tarjetaRetiroCandidatoDTO : listaCandidatos) {
            //Se almacena cada registro de tarjeta
            crearRegistroTarjetasArchivoConsumoANIBOL(idArchivoConsumoTarjeta, tarjetaRetiroCandidatoDTO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#actualizarArchivoConsumosAnibol(com.asopagos.subsidiomonetario.pagos.dto.ArchivoConsumosAnibolModeloDTO)
     */
    @Override
    public void actualizarArchivoConsumosAnibol(ArchivoConsumosAnibolModeloDTO archivoConsumosAnibolModeloDTO) {
        String firmaMetodo = "ConsultasModeloCore.actualizarArchivoConsumosAnibol(ArchivoConsumosAnibolModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {
            ArchivoConsumosAnibol archivoConsumosAnibol = archivoConsumosAnibolModeloDTO.convertToEntity();
            entityManagerCore.merge(archivoConsumosAnibol);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Metodo encargado de registrar los documentos de soporte del tercero pagador.
     *
     * @param listaDocumentosSoporte   lista de documentos de soporte del convenio.
     * @param idConvenioTerceroPagador identificador de la base de datos con que quedo registrado el convenio asociado a los documentos de soporte.
     */
    private void registrarDocumentosSoporteConvenioTerceroPagador(List<DocumentoSoporteConvenioModeloDTO> listaDocumentosSoporte,
                                                                  Long idConvenioTerceroPagador) {
        String firmaServicio = "ConsultasModeloCore.registrarDocumentosSoporteConvenioTerceroPagador(List<DocumentoSoporteConvenioModeloDTO>,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        for (DocumentoSoporteConvenioModeloDTO documento : listaDocumentosSoporte) {

            try {
                //Persistencia del documento de soporte 
                Long idDocumentoSoporte = guardarDocumentoSoporte(documento);

                //Persistencia de la entidad que asocia el documento de soporte con el convenio
                guardarDocumentoSoporteConvenio(idConvenioTerceroPagador, idDocumentoSoporte);

            } catch (Exception e2) {
                logger.error("Ocurrió un error inesperado en la creación del documento de soporte del convenio del tercero pagador", e2);
                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }

        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * Metodo que permite filtrar la consulta de busqueda de detalles por medio
     * de la lista de identificadores de empleadores, afiliados principales, grupos familiares
     * y administradores de subsidios.
     *
     * @param detallesTransacciones DTO que contiene los filtros por los cuales se buscaran los detalles.
     * @return lista de detalles.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<DetalleSubsidioAsignadoDTO> consultarDetallesPorFiltros(DetalleTransaccionAsignadoConsultadoDTO detallesTransacciones) {
        String firmaServicio = "ConsultasModeloCore.consultarDetallesPorFiltros(DetalleTransaccionAsignadoConsultadoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        List<DetalleSubsidioAsignadoDTO> listaDetalles = new ArrayList<>();
        List<Long> sqlHack = new ArrayList<Long>();
        sqlHack.add(0L);

        Date fechaRangoInicial = null;
        Date fechaRangoFinal = null;

        if (detallesTransacciones.getFechaInicio() == null) {
            Long fechaInicio = new Long("631170000000");
            fechaRangoInicial = new Date(fechaInicio);
        } else {
            fechaRangoInicial = new Date(detallesTransacciones.getFechaInicio());
        }

        if (detallesTransacciones.getFechaFin() == null) {
            fechaRangoFinal = new Date();
        } else {
            fechaRangoInicial = new Date(detallesTransacciones.getFechaInicio());
            fechaRangoFinal = new Date(detallesTransacciones.getFechaFin());
        }

        List<Object[]> listDetalles = (List<Object[]>) entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_EMPLEADOR_ADMIN_BENEFICIARIO_AFILIADO_DETALLES_SUBSIDIO)
                .setParameter("medioPago", detallesTransacciones.getMedioDePago() != null ? detallesTransacciones.getMedioDePago().name() : null)

                .setParameter("fechaInicio", CalendarUtils.truncarHora(fechaRangoInicial))
                .setParameter("fechaFin", CalendarUtils.truncarHoraMaxima(fechaRangoFinal))

                .setParameter("lstidEmpleadores", (detallesTransacciones.getListaIdEmpleadores() == null || detallesTransacciones.getListaIdEmpleadores().isEmpty()) ? sqlHack : detallesTransacciones.getListaIdEmpleadores())
                .setParameter("sizelstidEmpleadores", (detallesTransacciones.getListaIdEmpleadores() == null || detallesTransacciones.getListaIdEmpleadores().isEmpty()) ? 0 : 1)

                .setParameter("lstidAfiliadoPrincipal", (detallesTransacciones.getListaIdAfiliadosPrincipales() == null || detallesTransacciones.getListaIdAfiliadosPrincipales().isEmpty()) ? sqlHack : detallesTransacciones.getListaIdAfiliadosPrincipales())
                .setParameter("sizelstidAfiliadoPrincipal", (detallesTransacciones.getListaIdAfiliadosPrincipales() == null || detallesTransacciones.getListaIdAfiliadosPrincipales().isEmpty()) ? 0 : 1)

                .setParameter("lstidGrupoFamiliar", (detallesTransacciones.getListaIdGruposFamiliares() == null || detallesTransacciones.getListaIdGruposFamiliares().isEmpty()) ? sqlHack : detallesTransacciones.getListaIdGruposFamiliares())
                .setParameter("sizelstidGrupoFamiliar", (detallesTransacciones.getListaIdGruposFamiliares() == null || detallesTransacciones.getListaIdGruposFamiliares().isEmpty()) ? 0 : 1)

                .setParameter("lstidBeneficiarioDetalle", (detallesTransacciones.getListaIdBeneficiarios() == null || detallesTransacciones.getListaIdBeneficiarios().isEmpty()) ? sqlHack : detallesTransacciones.getListaIdBeneficiarios())
                .setParameter("sizelstidBeneficiarioDetalle", (detallesTransacciones.getListaIdBeneficiarios() == null || detallesTransacciones.getListaIdBeneficiarios().isEmpty()) ? 0 : 1)

                .setParameter("lstidAdministradorSubsidio", (detallesTransacciones.getListaIdAdminSubsidios() == null || detallesTransacciones.getListaIdAdminSubsidios().isEmpty()) ? sqlHack : detallesTransacciones.getListaIdAdminSubsidios())
                .setParameter("sizelstidAdministradorSubsidio", (detallesTransacciones.getListaIdAdminSubsidios() == null || detallesTransacciones.getListaIdAdminSubsidios().isEmpty()) ? 0 : 1)
                .getResultList();

        for (Object[] result : listDetalles) {

            PersonaModeloDTO personaBen = new PersonaModeloDTO();
            PersonaModeloDTO personaAdmin = new PersonaModeloDTO();
            EmpleadorModeloDTO empleador = new EmpleadorModeloDTO();
            AfiliadoModeloDTO afiliado = new AfiliadoModeloDTO();
            DetalleSubsidioAsignadoDTO detalle = new DetalleSubsidioAsignadoDTO();

            BigInteger a = new BigInteger("1");


            // DATOS ADICIONALES 
            //se adiciona el nombre del empleador
            if (result[0] != null) {
                detalle.setIdDetalleSubsidioAsignado(Long.valueOf(result[0].toString()));
            }

            if (result[1] != null) {
                empleador.setRazonSocial(result[1].toString());
                empleador.setTipoIdentificacion(result[48] != null ? TipoIdentificacionEnum.valueOf(result[48].toString()) : null);
                empleador.setNumeroIdentificacion(result[49] != null ? result[49].toString() : null);
                detalle.setEmpleador(empleador);
            }
            //se adiciona el nombre del afiliado
            if (result[2] != null) {
                afiliado.setRazonSocial(result[2].toString());
                afiliado.setTipoIdentificacion(result[50] != null ? TipoIdentificacionEnum.valueOf(result[50].toString()) : null);
                afiliado.setNumeroIdentificacion(result[51] != null ? result[51].toString() : null);
                detalle.setAfiliadoPrincipal(afiliado);
            }
            //se adiciona el nombre del beneficiario
            if (result[3] != null) {
                personaBen.setRazonSocial(result[3].toString());
                personaBen.setTipoIdentificacion(result[52] != null ? TipoIdentificacionEnum.valueOf(result[52].toString()) : null);
                personaBen.setNumeroIdentificacion(result[53] != null ? result[53].toString() : null);
                detalle.setBeneficiario(personaBen);
            }
            //se adiciona el nombre del administrador
            if (result[4] != null) {
                personaAdmin.setRazonSocial(result[4].toString());
                personaAdmin.setTipoIdentificacion(result[54] != null ? TipoIdentificacionEnum.valueOf(result[54].toString()) : null);
                personaAdmin.setNumeroIdentificacion(result[55] != null ? result[55].toString() : null);
                detalle.setAdministradorSubsidio(personaAdmin);
            }
            if (result[5] != null) {
                detalle.setNumeroGrupoFamilarRelacionador(Short.parseShort(result[5].toString()));
            }
            //nombre del tipo de descuento (nombre entidad de descuento)
            if (result[6] != null)
                detalle.setNombreTipoDescuento(result[6].toString());
            //fecha asociada a la liquidación
            if (result[7] != null)
                detalle.setFechaLiquidacionAsociada((Date) result[7]);

            detalle.setUsuarioCreador(result[8].toString());
            detalle.setFechaHoraCreacion((Date) result[9]);
            detalle.setEstado(EstadoSubsidioAsignadoEnum.valueOf(result[10].toString()));
            if (result[11] != null)
                detalle.setMotivoAnulacion(MotivoAnulacionSubsidioAsignadoEnum.valueOf(result[11].toString()));
            if (result[12] != null)
                detalle.setDetalleAnulacion(result[12].toString());

            detalle.setOrigenRegistroSubsidio(OrigenRegistroSubsidioAsignadoEnum.valueOf(result[13].toString()));
            detalle.setTipoLiquidacionSubsidio(TipoLiquidacionSubsidioEnum.valueOf(result[14].toString()));
            detalle.setTipoCuotaSubsidio(TipoCuotaSubsidioEnum.valueOf(result[15].toString()));
            detalle.setValorSubsidioMonetario(BigDecimal.valueOf(Double.parseDouble(result[16].toString())));
            detalle.setValorDescuento(BigDecimal.valueOf(Double.parseDouble(result[17].toString())));
            detalle.setValorOriginalAbonado(BigDecimal.valueOf(Double.parseDouble(result[18].toString())));
            detalle.setValorTotal(BigDecimal.valueOf(Double.parseDouble(result[19].toString())));
            if (result[20] != null)
                detalle.setFechaTransaccionRetiro((Date) result[20]);
            if (result[21] != null)
                detalle.setUsuarioTransaccionRetiro(result[21].toString());
            if (result[22] != null)
                detalle.setFechaTransaccionAnulacion((Date) result[22]);
            if (result[23] != null)
                detalle.setUsuarioTransaccionAnulacion(result[23].toString());
            if (result[24] != null)
                detalle.setFechaHoraUltimaModificacion((Date) result[24]);
            if (result[25] != null)
                detalle.setUsuarioUltimaModificacion(result[25].toString());
            detalle.setIdSolicitudLiquidacionSubsidio(Long.valueOf(result[26].toString()));
            detalle.setIdEmpleador(Long.valueOf(result[27].toString()));
            detalle.setIdAfiliadoPrincipal(Long.valueOf(result[28].toString()));
            detalle.setIdGrupoFamiliar(Long.valueOf(result[29].toString()));
            detalle.setIdAdministradorSubsidio(Long.valueOf(result[30].toString()));
            if (result[31] != null)
                detalle.setIdRegistroOriginalRelacionado(Long.valueOf(result[31].toString()));
            detalle.setIdCuentaAdministradorSubsidio(Long.valueOf(result[32].toString()));
            detalle.setIdBeneficiarioDetalle(Long.valueOf(result[33].toString()));
            detalle.setPeriodoLiquidado((Date) result[34]);
            detalle.setIdResultadoValidacionLiquidacion(Long.valueOf(result[35].toString()));
            if (result[36] != null)
                detalle.setIdCondicionPersonaBeneficiario(Long.valueOf(result[36].toString()));
            if (result[39] != null)
                detalle.setMedioDePago(TipoMedioDePagoEnum.valueOf(result[39].toString()));
            if (result[40] != null)
                detalle.setNumeroTarjetaAdminSubsidio(result[40].toString());
            if (result[41] != null)
                detalle.setCodigoBancoAdminSubsidio(result[41].toString());
            if (result[42] != null)
                detalle.setNombreBancoAdminSubsidio(result[42].toString());
            if (result[43] != null)
                detalle.setTipoCuentaAdminSubsidio(TipoCuentaEnum.valueOf(result[43].toString()));
            if (result[44] != null)
                detalle.setNumeroCuentaAdminSubsidio(result[44].toString());
            if (result[45] != null)
                detalle.setTipoIdentificacionTitularCuentaAdminSubsidio(TipoIdentificacionEnum.valueOf(result[45].toString()));
            if (result[46] != null)
                detalle.setNumeroIdentificacionTitularCuentaAdminSubsidio(result[46].toString());
            if (result[47] != null)
                detalle.setNombreTitularCuentaAdminSubsidio(result[47].toString());
            listaDetalles.add(detalle);
            
            
            
        
/*
        Map<String, String> fields = new HashMap<>();
        Map<String, Object> values = new HashMap<>();

        if (detallesTransacciones.getListaIdEmpleadores() != null && !detallesTransacciones.getListaIdEmpleadores().isEmpty()) {
            fields.put("idEmpleador", ConsultasDinamicasConstants.IN);
            values.put("idEmpleador", detallesTransacciones.getListaIdEmpleadores());
        }
        if (detallesTransacciones.getListaIdAfiliadosPrincipales() != null
                && !detallesTransacciones.getListaIdAfiliadosPrincipales().isEmpty()) {
            fields.put("idAfiliadoPrincipal", ConsultasDinamicasConstants.IN);
            values.put("idAfiliadoPrincipal", detallesTransacciones.getListaIdAfiliadosPrincipales());
        }
        if (detallesTransacciones.getListaIdGruposFamiliares() != null && !detallesTransacciones.getListaIdGruposFamiliares().isEmpty()) {
            fields.put("idGrupoFamiliar", ConsultasDinamicasConstants.IN);
            values.put("idGrupoFamiliar", detallesTransacciones.getListaIdGruposFamiliares());
        }
        if (detallesTransacciones.getListaIdBeneficiarios() != null && !detallesTransacciones.getListaIdBeneficiarios().isEmpty()) {
            fields.put("idBeneficiarioDetalle", ConsultasDinamicasConstants.IN);
            values.put("idBeneficiarioDetalle", detallesTransacciones.getListaIdBeneficiarios());
        }
        if (detallesTransacciones.getListaIdAdminSubsidios() != null && !detallesTransacciones.getListaIdAdminSubsidios().isEmpty()) {
            fields.put("idAdministradorSubsidio", ConsultasDinamicasConstants.IN);
            values.put("idAdministradorSubsidio", detallesTransacciones.getListaIdAdminSubsidios());
        }

        List<DetalleSubsidioAsignadoDTO> listaDetalles = new ArrayList<>();
        List<DetalleSubsidioAsignado> detallesConsultados = JPAUtils.consultaEntidad(entityManagerCore, DetalleSubsidioAsignado.class,
                fields, values);
        for (DetalleSubsidioAsignado detalle : detallesConsultados) {
            listaDetalles.add(new DetalleSubsidioAsignadoDTO(detalle));
        }
        
        */
        }
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return listaDetalles.isEmpty() ? null : listaDetalles;

    }

    /**
     * Metodo que permite filtrar la consulta de busqueda de detalles por medio
     * de la lista de identificadores de empleadores, afiliados principales, grupos familiares
     * y administradores de subsidios.
     *
     * @param detallesTransacciones DTO que contiene los filtros por los cuales se buscaran los detalles.
     * @return lista de detalles.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesPaginado(DetalleTransaccionAsignadoConsultadoDTO detallesTransacciones, UriInfo uri, HttpServletResponse response) {
        String firmaServicio = "ConsultasModeloCore.consultarDetallesPorFiltros(DetalleTransaccionAsignadoConsultadoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        List<DetalleSubsidioAsignadoDTO> listaDetalles = new ArrayList<>();
        List<Long> sqlHack = new ArrayList<Long>();
        sqlHack.add(0L);

        Date fechaRangoInicial;
        Date fechaRangoFinal;
        String filtroFechas = "";

        if (detallesTransacciones.getFechaInicio() != null && detallesTransacciones.getFechaFin() != null) {
            fechaRangoInicial = new Date(detallesTransacciones.getFechaInicio());
            fechaRangoFinal = new Date(detallesTransacciones.getFechaFin());
        } else {

            fechaRangoInicial = new Date();
            fechaRangoFinal = new Date();
            filtroFechas = null;
        }

        QueryBuilder queryBuilder = new QueryBuilder(entityManagerCore, uri, response);
        queryBuilder.addParam("medioPago", detallesTransacciones.getMedioDePago() != null ? detallesTransacciones.getMedioDePago().name() : null);

        queryBuilder.addParam("filtroFechas", filtroFechas);
        queryBuilder.addParam("fechaInicio", CalendarUtils.truncarHora(fechaRangoInicial));
        queryBuilder.addParam("fechaFin", CalendarUtils.truncarHoraMaxima(fechaRangoFinal));
        queryBuilder.addParam("lstidEmpleadores", (detallesTransacciones.getListaIdEmpleadores() == null || detallesTransacciones.getListaIdEmpleadores().isEmpty()) ? sqlHack : detallesTransacciones.getListaIdEmpleadores());
        queryBuilder.addParam("sizelstidEmpleadores", (detallesTransacciones.getListaIdEmpleadores() == null || detallesTransacciones.getListaIdEmpleadores().isEmpty()) ? 0 : 1);
        queryBuilder.addParam("lstidAfiliadoPrincipal", (detallesTransacciones.getListaIdAfiliadosPrincipales() == null || detallesTransacciones.getListaIdAfiliadosPrincipales().isEmpty()) ? sqlHack : detallesTransacciones.getListaIdAfiliadosPrincipales());
        queryBuilder.addParam("sizelstidAfiliadoPrincipal", (detallesTransacciones.getListaIdAfiliadosPrincipales() == null || detallesTransacciones.getListaIdAfiliadosPrincipales().isEmpty()) ? 0 : 1);
        queryBuilder.addParam("lstidBeneficiarioDetalle", (detallesTransacciones.getListaIdBeneficiarios() == null || detallesTransacciones.getListaIdBeneficiarios().isEmpty()) ? sqlHack : detallesTransacciones.getListaIdBeneficiarios());
        queryBuilder.addParam("sizelstidBeneficiarioDetalle", (detallesTransacciones.getListaIdBeneficiarios() == null || detallesTransacciones.getListaIdBeneficiarios().isEmpty()) ? 0 : 1);
        queryBuilder.addParam("lstidAdministradorSubsidio", (detallesTransacciones.getListaIdAdminSubsidios() == null || detallesTransacciones.getListaIdAdminSubsidios().isEmpty()) ? sqlHack : detallesTransacciones.getListaIdAdminSubsidios());
        queryBuilder.addParam("sizelstidAdministradorSubsidio", (detallesTransacciones.getListaIdAdminSubsidios() == null || detallesTransacciones.getListaIdAdminSubsidios().isEmpty()) ? 0 : 1);
        queryBuilder.addParam("lstidAdministradorSubsidio", (detallesTransacciones.getListaIdAdminSubsidios() == null || detallesTransacciones.getListaIdAdminSubsidios().isEmpty()) ? sqlHack : detallesTransacciones.getListaIdAdminSubsidios());
        queryBuilder.addParam("sizelstidAdministradorSubsidio", (detallesTransacciones.getListaIdAdminSubsidios() == null || detallesTransacciones.getListaIdAdminSubsidios().isEmpty()) ? 0 : 1);
        queryBuilder.addParam("lstidGrupoFamiliar", (detallesTransacciones.getListaIdGruposFamiliares() == null || detallesTransacciones.getListaIdGruposFamiliares().isEmpty()) ? sqlHack : detallesTransacciones.getListaIdGruposFamiliares());
        queryBuilder.addParam("sizelstidGrupoFamiliar", (detallesTransacciones.getListaIdGruposFamiliares() == null || detallesTransacciones.getListaIdGruposFamiliares().isEmpty()) ? 0 : 1);
        Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_DATOS_EMPLEADOR_ADMIN_BENEFICIARIO_AFILIADO_DETALLES_SUBSIDIO_PAGINADO, null);
        List<Object[]> listDetalles = query.getResultList();

        for (Object[] result : listDetalles) {

            PersonaModeloDTO personaBen = new PersonaModeloDTO();
            PersonaModeloDTO personaAdmin = new PersonaModeloDTO();
            EmpleadorModeloDTO empleador = new EmpleadorModeloDTO();
            AfiliadoModeloDTO afiliado = new AfiliadoModeloDTO();
            DetalleSubsidioAsignadoDTO detalle = new DetalleSubsidioAsignadoDTO();

            BigInteger a = new BigInteger("1");


            // DATOS ADICIONALES 
            //se adiciona el nombre del empleador
            if (result[0] != null) {
                detalle.setIdDetalleSubsidioAsignado(Long.valueOf(result[0].toString()));
            }

            if (result[1] != null) {
                empleador.setRazonSocial(result[1].toString());
                empleador.setTipoIdentificacion(result[47] != null ? TipoIdentificacionEnum.valueOf(result[47].toString()) : null);
                empleador.setNumeroIdentificacion(result[48] != null ? result[48].toString() : null);
                detalle.setEmpleador(empleador);
            }
            //se adiciona el nombre del afiliado
            if (result[2] != null) {
                afiliado.setRazonSocial(result[2].toString());
                afiliado.setTipoIdentificacion(result[49] != null ? TipoIdentificacionEnum.valueOf(result[49].toString()) : null);
                afiliado.setNumeroIdentificacion(result[50] != null ? result[50].toString() : null);
                detalle.setAfiliadoPrincipal(afiliado);
            }
            //se adiciona el nombre del beneficiario
            if (result[3] != null) {
                personaBen.setRazonSocial(result[3].toString());
                personaBen.setTipoIdentificacion(result[51] != null ? TipoIdentificacionEnum.valueOf(result[51].toString()) : null);
                personaBen.setNumeroIdentificacion(result[52] != null ? result[52].toString() : null);
                detalle.setBeneficiario(personaBen);
            }
            //se adiciona el nombre del administrador
            if (result[4] != null) {
                personaAdmin.setRazonSocial(result[4].toString());
                personaAdmin.setTipoIdentificacion(result[53] != null ? TipoIdentificacionEnum.valueOf(result[53].toString()) : null);
                personaAdmin.setNumeroIdentificacion(result[54] != null ? result[54].toString() : null);
                detalle.setAdministradorSubsidio(personaAdmin);
            }
            if (result[5] != null) {
                detalle.setNumeroGrupoFamilarRelacionador(Short.parseShort(result[5].toString()));
            }
            //fecha asociada a la liquidación
            if (result[6] != null)
                detalle.setFechaLiquidacionAsociada((Date) result[6]);

            detalle.setUsuarioCreador(result[7].toString());
            detalle.setFechaHoraCreacion((Date) result[8]);
            detalle.setEstado(EstadoSubsidioAsignadoEnum.valueOf(result[9].toString()));
            if (result[10] != null)
                detalle.setMotivoAnulacion(MotivoAnulacionSubsidioAsignadoEnum.valueOf(result[10].toString()));
            if (result[11] != null)
                detalle.setDetalleAnulacion(result[11].toString());

            detalle.setOrigenRegistroSubsidio(OrigenRegistroSubsidioAsignadoEnum.valueOf(result[12].toString()));
            detalle.setTipoLiquidacionSubsidio(TipoLiquidacionSubsidioEnum.valueOf(result[13].toString()));
            detalle.setTipoCuotaSubsidio(TipoCuotaSubsidioEnum.valueOf(result[14].toString()));
            detalle.setValorSubsidioMonetario(BigDecimal.valueOf(Double.parseDouble(result[15].toString())));
            detalle.setValorDescuento(BigDecimal.valueOf(Double.parseDouble(result[16].toString())));
            detalle.setValorOriginalAbonado(BigDecimal.valueOf(Double.parseDouble(result[17].toString())));
            detalle.setValorTotal(BigDecimal.valueOf(Double.parseDouble(result[18].toString())));
            if (result[19] != null)
                detalle.setFechaTransaccionRetiro((Date) result[19]);
            if (result[20] != null)
                detalle.setUsuarioTransaccionRetiro(result[20].toString());
            if (result[21] != null)
                detalle.setFechaTransaccionAnulacion((Date) result[21]);
            if (result[22] != null)
                detalle.setUsuarioTransaccionAnulacion(result[22].toString());
            if (result[23] != null)
                detalle.setFechaHoraUltimaModificacion((Date) result[23]);
            if (result[24] != null)
                detalle.setUsuarioUltimaModificacion(result[24].toString());
            detalle.setIdSolicitudLiquidacionSubsidio(Long.valueOf(result[25].toString()));
            detalle.setIdEmpleador(Long.valueOf(result[26].toString()));
            detalle.setIdAfiliadoPrincipal(Long.valueOf(result[27].toString()));
            detalle.setIdGrupoFamiliar(Long.valueOf(result[28].toString()));
            detalle.setIdAdministradorSubsidio(Long.valueOf(result[29].toString()));
            if (result[30] != null)
                detalle.setIdRegistroOriginalRelacionado(Long.valueOf(result[30].toString()));
            detalle.setIdCuentaAdministradorSubsidio(Long.valueOf(result[31].toString()));
            detalle.setIdBeneficiarioDetalle(Long.valueOf(result[32].toString()));
            detalle.setPeriodoLiquidado((Date) result[33]);
            detalle.setIdResultadoValidacionLiquidacion(Long.valueOf(result[34].toString()));
            if (result[35] != null)
                detalle.setIdCondicionPersonaBeneficiario(Long.valueOf(result[35].toString()));
            if (result[38] != null)
                detalle.setMedioDePago(TipoMedioDePagoEnum.valueOf(result[38].toString()));
            if (result[39] != null)
                detalle.setNumeroTarjetaAdminSubsidio(result[39].toString());
            if (result[40] != null)
                detalle.setCodigoBancoAdminSubsidio(result[40].toString());
            if (result[41] != null)
                detalle.setNombreBancoAdminSubsidio(result[41].toString());
            if (result[42] != null)
                detalle.setTipoCuentaAdminSubsidio(TipoCuentaEnum.valueOf(result[42].toString()));
            if (result[43] != null)
                detalle.setNumeroCuentaAdminSubsidio(result[43].toString());
            if (result[44] != null)
                detalle.setTipoIdentificacionTitularCuentaAdminSubsidio(TipoIdentificacionEnum.valueOf(result[44].toString()));
            if (result[45] != null)
                detalle.setNumeroIdentificacionTitularCuentaAdminSubsidio(result[45].toString());
            if (result[46] != null)
                detalle.setNombreTitularCuentaAdminSubsidio(result[46].toString());
            listaDetalles.add(detalle);
        }
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return listaDetalles.isEmpty() ? null : listaDetalles;

    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarDetallesSP(javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse, com.asopagos.subsidiomonetario.pagos.dto.DetalleTransaccionAsignadoConsultadoDTO)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object[]> consultarDetallesSP(UriInfo uriInfo,
                                              HttpServletResponse response, DetalleTransaccionAsignadoConsultadoDTO detallesTransacciones, Integer offsetParametro) {
        String firmaServicio = "ConsultasModeloCore.consultarDetallesSP(TransaccionConsultada)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<Object[]> lstDetalles = null;

        Date fechaRangoInicial;
        Date fechaRangoFinal;
        String filtroFechas = "";

        if (detallesTransacciones.getFechaInicio() != null && detallesTransacciones.getFechaFin() != null) {
            fechaRangoInicial = new Date(detallesTransacciones.getFechaInicio());
            fechaRangoFinal = new Date(detallesTransacciones.getFechaFin());
            filtroFechas = "1";
        } else {

            fechaRangoInicial = new Date();
            fechaRangoFinal = new Date();
        }


        StringBuilder idAdminSubsidioStr = new StringBuilder();
        StringBuilder idAfiliadoStr = new StringBuilder();
        StringBuilder idBeneficiarioStr = new StringBuilder();
        StringBuilder idEmpleadorStr = new StringBuilder();
        StringBuilder idGrupoFamiliarStr = new StringBuilder();
        int count = 0;

        if (detallesTransacciones.getListaIdAdminSubsidios() != null
                && !detallesTransacciones.getListaIdAdminSubsidios().isEmpty()) {
            for (Long idAdminSubsidio : detallesTransacciones.getListaIdAdminSubsidios()) {
                idAdminSubsidioStr.append(idAdminSubsidio);
                if (count < detallesTransacciones.getListaIdAdminSubsidios().size()) {
                    idAdminSubsidioStr.append(",");
                }
                count++;
            }
        }
        if (detallesTransacciones.getListaIdAfiliadosPrincipales() != null
                && !detallesTransacciones.getListaIdAfiliadosPrincipales().isEmpty()) {
            for (Long idAfiliadoPrincipal : detallesTransacciones.getListaIdAfiliadosPrincipales()) {
                idAfiliadoStr.append(idAfiliadoPrincipal);
                if (count < detallesTransacciones.getListaIdAfiliadosPrincipales().size()) {
                    idAfiliadoStr.append(",");
                }
                count++;
            }
        }
        if (detallesTransacciones.getListaIdBeneficiarios() != null
                && !detallesTransacciones.getListaIdBeneficiarios().isEmpty()) {
            for (Long idBeneficiario : detallesTransacciones.getListaIdBeneficiarios()) {
                idBeneficiarioStr.append(idBeneficiario);
                if (count < detallesTransacciones.getListaIdBeneficiarios().size()) {
                    idBeneficiarioStr.append(",");
                }
                count++;
            }
        }
        if (detallesTransacciones.getListaIdEmpleadores() != null
                && !detallesTransacciones.getListaIdEmpleadores().isEmpty()) {
            for (Long idEmpleador : detallesTransacciones.getListaIdEmpleadores()) {
                idEmpleadorStr.append(idEmpleador);
                if (count < detallesTransacciones.getListaIdEmpleadores().size()) {
                    idEmpleadorStr.append(",");
                }
                count++;
            }
        }
        if (detallesTransacciones.getListaIdGruposFamiliares() != null
                && !detallesTransacciones.getListaIdGruposFamiliares().isEmpty()) {
            for (Long idGrupo : detallesTransacciones.getListaIdGruposFamiliares()) {
                idGrupoFamiliarStr.append(idGrupo);
                if (count < detallesTransacciones.getListaIdGruposFamiliares().size()) {
                    idGrupoFamiliarStr.append(",");
                }
                count++;
            }
        }
        lstDetalles = entityManagerCore
                .createStoredProcedureQuery(NamedQueriesConstants.USP_PG_GET_CONSULTARDETALLESSUBSIDIO)
                .registerStoredProcedureParameter("medioDePago", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("fechaInicio", Date.class, ParameterMode.IN)
                .registerStoredProcedureParameter("fechaFin", Date.class, ParameterMode.IN)
                .registerStoredProcedureParameter("idsAminSubsidio", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("idsEmpleadores", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("idsAfiliados", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("idsBeneficiarios", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("idsGrupoFamiliar", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("offsetParametro", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("filtroFechas", String.class, ParameterMode.IN)
                .setParameter("medioDePago", detallesTransacciones.getMedioDePago() != null ? detallesTransacciones.getMedioDePago().name() : "")
                .setParameter("filtroFechas", filtroFechas)
                .setParameter("fechaInicio", fechaRangoInicial)
                .setParameter("fechaFin", fechaRangoFinal)
                .setParameter("idsAminSubsidio", idAdminSubsidioStr.toString())
                .setParameter("idsEmpleadores", idEmpleadorStr.toString())
                .setParameter("idsAfiliados", idAfiliadoStr.toString())
                .setParameter("idsBeneficiarios", idBeneficiarioStr.toString())
                .setParameter("idsGrupoFamiliar", idGrupoFamiliarStr.toString())
                .setParameter("offsetParametro", offsetParametro)
                .getResultList();

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return lstDetalles.isEmpty() ? null : lstDetalles;
    }


    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object[]> consultarTransDetallesSP(UriInfo uriInfo,
                                                   HttpServletResponse response, DetalleTransaccionAsignadoConsultadoDTO detallesTransacciones, Integer offsetParametro) {
        String firmaServicio = "ConsultasModeloCore.consultarTransDetallesSP(TransaccionConsultada)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<Object[]> lstDetalles = null;


        Date fechaRangoInicial;
        Date fechaRangoFinal;
        String filtroFechas = "";

        if (detallesTransacciones.getFechaInicio() != null && detallesTransacciones.getFechaFin() != null) {
            fechaRangoInicial = new Date(detallesTransacciones.getFechaInicio());
            fechaRangoFinal = new Date(detallesTransacciones.getFechaFin());
            filtroFechas = "1";
        } else {

            fechaRangoInicial = new Date();
            fechaRangoFinal = new Date();
        }


        StringBuilder idAdminSubsidioStr = new StringBuilder();
        StringBuilder idAfiliadoStr = new StringBuilder();
        StringBuilder idBeneficiarioStr = new StringBuilder();
        StringBuilder idEmpleadorStr = new StringBuilder();
        StringBuilder idGrupoFamiliarStr = new StringBuilder();
        int count = 0;

        if (detallesTransacciones.getListaIdAdminSubsidios() != null
                && !detallesTransacciones.getListaIdAdminSubsidios().isEmpty()) {
            for (Long idAdminSubsidio : detallesTransacciones.getListaIdAdminSubsidios()) {
                idAdminSubsidioStr.append(idAdminSubsidio);
                if (count < detallesTransacciones.getListaIdAdminSubsidios().size()) {
                    idAdminSubsidioStr.append(",");
                }
                count++;
            }
        }
        if (detallesTransacciones.getListaIdAfiliadosPrincipales() != null
                && !detallesTransacciones.getListaIdAfiliadosPrincipales().isEmpty()) {
            for (Long idAfiliadoPrincipal : detallesTransacciones.getListaIdAfiliadosPrincipales()) {
                idAfiliadoStr.append(idAfiliadoPrincipal);
                if (count < detallesTransacciones.getListaIdAfiliadosPrincipales().size()) {
                    idAfiliadoStr.append(",");
                }
                count++;
            }
        }
        if (detallesTransacciones.getListaIdBeneficiarios() != null
                && !detallesTransacciones.getListaIdBeneficiarios().isEmpty()) {
            for (Long idBeneficiario : detallesTransacciones.getListaIdBeneficiarios()) {
                idBeneficiarioStr.append(idBeneficiario);
                if (count < detallesTransacciones.getListaIdBeneficiarios().size()) {
                    idBeneficiarioStr.append(",");
                }
                count++;
            }
        }
        if (detallesTransacciones.getListaIdEmpleadores() != null
                && !detallesTransacciones.getListaIdEmpleadores().isEmpty()) {
            for (Long idEmpleador : detallesTransacciones.getListaIdEmpleadores()) {
                idEmpleadorStr.append(idEmpleador);
                if (count < detallesTransacciones.getListaIdEmpleadores().size()) {
                    idEmpleadorStr.append(",");
                }
                count++;
            }
        }
        if (detallesTransacciones.getListaIdGruposFamiliares() != null
                && !detallesTransacciones.getListaIdGruposFamiliares().isEmpty()) {
            for (Long idGrupo : detallesTransacciones.getListaIdGruposFamiliares()) {
                idGrupoFamiliarStr.append(idGrupo);
                if (count < detallesTransacciones.getListaIdGruposFamiliares().size()) {
                    idGrupoFamiliarStr.append(",");
                }
                count++;
            }
        }

        logger.info("detallesTransacciones.getMedioDePago() " +detallesTransacciones.getMedioDePago());
        logger.info("detallesTransacciones.getTipoTransaccion() " +detallesTransacciones.getTipoTransaccion());
        logger.info("filtroFechas " +filtroFechas);
        logger.info("fechaRangoInicial " +fechaRangoInicial);
        logger.info("fechaRangoFinal " +fechaRangoFinal);


        logger.info("estadoTransaccion " +detallesTransacciones.getEstadoTransaccion());
        logger.info("idsAminSubsidio " +idAdminSubsidioStr.toString());
        logger.info("idsEmpleadores " +idEmpleadorStr.toString());
        logger.info("idsAfiliados " +idAfiliadoStr.toString());
        logger.info("idBeneficiarioStr " +idBeneficiarioStr.toString());
        logger.info("idGrupoFamiliarStr " +idGrupoFamiliarStr.toString());
        logger.info("offsetParametro " +offsetParametro);


        lstDetalles = entityManagerCore
                .createStoredProcedureQuery(NamedQueriesConstants.USP_PG_GET_CONSULTARTRANSDETALLESSUBSIDIO)
                .registerStoredProcedureParameter("medioDePago", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("tipoTransaccion", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("estadoTransaccion", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("fechaInicio", Date.class, ParameterMode.IN)
                .registerStoredProcedureParameter("fechaFin", Date.class, ParameterMode.IN)
                .registerStoredProcedureParameter("idsAminSubsidio", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("idsEmpleadores", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("idsAfiliados", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("idsBeneficiarios", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("idsGrupoFamiliar", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("offsetParametro", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("filtroFechas", String.class, ParameterMode.IN)
                .setParameter("medioDePago", detallesTransacciones.getMedioDePago() != null ? detallesTransacciones.getMedioDePago().name() : "")
                .setParameter("tipoTransaccion", detallesTransacciones.getTipoTransaccion() != null ? detallesTransacciones.getTipoTransaccion().name() : "")
                .setParameter("estadoTransaccion", detallesTransacciones.getEstadoTransaccion() != null ? detallesTransacciones.getEstadoTransaccion().name() : "")
                .setParameter("filtroFechas", filtroFechas)
                .setParameter("fechaInicio", fechaRangoInicial).setParameter("fechaFin", fechaRangoFinal)
                .setParameter("idsAminSubsidio", idAdminSubsidioStr.toString())
                .setParameter("idsEmpleadores", idEmpleadorStr.toString())
                .setParameter("idsAfiliados", idAfiliadoStr.toString())
                .setParameter("idsBeneficiarios", idBeneficiarioStr.toString())
                .setParameter("idsGrupoFamiliar", idGrupoFamiliarStr.toString())
                .setParameter("offsetParametro", offsetParametro)
                .getResultList();
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return lstDetalles.isEmpty() ? null : lstDetalles;
    }


    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CuentaAdministradorSubsidioDTO> consultarTransaccionesTodosFiltrosSP(UriInfo uriInfo, HttpServletResponse response,
                                                                                     DetalleTransaccionAsignadoConsultadoDTO detallesTransacciones) {
        String firmaServicio = "ConsultasModeloCore.consultarTransaccionesTodosFiltrosSP(TransaccionConsultada)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<Object[]> lstCuentas = null;
        List<CuentaAdministradorSubsidioDTO> listaCuentaAdministrador = null;

        Date fechaRangoInicial;
        Date fechaRangoFinal;
        String filtroFechas = "";

        if (detallesTransacciones.getFechaInicio() != null && detallesTransacciones.getFechaFin() != null) {
            fechaRangoInicial = new Date(detallesTransacciones.getFechaInicio());
            fechaRangoFinal = new Date(detallesTransacciones.getFechaFin());
        } else {
            fechaRangoInicial = new Date();
            fechaRangoFinal = new Date();
            filtroFechas = null;
        }

        List<Long> sqlHack = new ArrayList<Long>();
        sqlHack.add(0L);

        try {
            lstCuentas = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_ADMIN_SUBSIDIO_PAGINADA)
                    .setParameter("filtroFechas", filtroFechas)
                    .setParameter("fechaInicio", CalendarUtils.truncarHora(fechaRangoInicial))
                    .setParameter("fechaFin", CalendarUtils.truncarHoraMaxima(fechaRangoFinal))
                    .setParameter("estadoTransaccion", detallesTransacciones.getEstadoTransaccion() != null ?
                            detallesTransacciones.getEstadoTransaccion().name() : null)
                    .setParameter("tipoTransaccion", (detallesTransacciones.getTipoTransaccion() != null) ?
                            detallesTransacciones.getTipoTransaccion().name() : null)
                    .setParameter("medioDePago", detallesTransacciones.getMedioDePago() != null ?
                            detallesTransacciones.getMedioDePago().name() : null)
                    .setParameter("sizelstidEmpleadores", (detallesTransacciones.getListaIdEmpleadores() == null ||
                            detallesTransacciones.getListaIdEmpleadores().isEmpty()) ? 0 : 1)
                    .setParameter("lstidEmpleadores", (detallesTransacciones.getListaIdEmpleadores() == null ||
                            detallesTransacciones.getListaIdEmpleadores().isEmpty()) ? sqlHack :
                            detallesTransacciones.getListaIdEmpleadores())
                    .setParameter("sizelstidAfiliadoPrincipal", (detallesTransacciones.getListaIdAfiliadosPrincipales() == null ||
                            detallesTransacciones.getListaIdAfiliadosPrincipales().isEmpty()) ? 0 : 1)
                    .setParameter("lstidAfiliadoPrincipal", (detallesTransacciones.getListaIdAfiliadosPrincipales() == null ||
                            detallesTransacciones.getListaIdAfiliadosPrincipales().isEmpty()) ? sqlHack :
                            detallesTransacciones.getListaIdAfiliadosPrincipales())
                    .setParameter("sizelstidBeneficiarioDetalle", (detallesTransacciones.getListaIdBeneficiarios() == null ||
                            detallesTransacciones.getListaIdBeneficiarios().isEmpty()) ? 0 : 1)
                    .setParameter("lstidBeneficiarioDetalle", (detallesTransacciones.getListaIdBeneficiarios() == null ||
                            detallesTransacciones.getListaIdBeneficiarios().isEmpty()) ? sqlHack :
                            detallesTransacciones.getListaIdBeneficiarios())
                    .setParameter("sizelstidAdministradorSubsidio", (detallesTransacciones.getListaIdAdminSubsidios() == null ||
                            detallesTransacciones.getListaIdAdminSubsidios().isEmpty()) ? 0 : 1)
                    .setParameter("lstidAdministradorSubsidio", (detallesTransacciones.getListaIdAdminSubsidios() == null ||
                            detallesTransacciones.getListaIdAdminSubsidios().isEmpty()) ? sqlHack :
                            detallesTransacciones.getListaIdAdminSubsidios())
                    .getResultList();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        listaCuentaAdministrador = listObjetToListCuentaAdministradorSubsidioDTO(lstCuentas);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaCuentaAdministrador;
    }


    private List<CuentaAdministradorSubsidioDTO> listObjetToListCuentaAdministradorSubsidioDTO(List<Object[]> lstDetalles) {

        List<CuentaAdministradorSubsidioDTO> CuentaAdministradorSubsidioList = new ArrayList<CuentaAdministradorSubsidioDTO>();

        if (lstDetalles != null && !lstDetalles.isEmpty()) {

            for (Object[] result : lstDetalles) {
                CuentaAdministradorSubsidioDTO cuentaAdministradorTemp = new CuentaAdministradorSubsidioDTO();

                cuentaAdministradorTemp.setIdCuentaAdministradorSubsidio(Long.valueOf(result[0].toString()));

                if (result[1] != null)
                    cuentaAdministradorTemp.setFechaHoraCreacionRegistro((Date) result[1]);
                if (result[2] != null)
                    cuentaAdministradorTemp.setUsuarioCreacionRegistro(result[2].toString());
                if (result[3] != null)
                    cuentaAdministradorTemp.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.valueOf(result[3].toString()));
                if (result[4] != null)
                    cuentaAdministradorTemp.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.valueOf(result[4].toString()));
                if (result[5] != null)
                    cuentaAdministradorTemp.setOrigenTransaccion(OrigenTransaccionEnum.valueOf(result[5].toString()));
                if (result[6] != null)
                    cuentaAdministradorTemp.setMedioDePago(TipoMedioDePagoEnum.valueOf(result[6].toString()));
                if (result[7] != null)
                    cuentaAdministradorTemp.setNumeroTarjetaAdminSubsidio(result[7].toString());
                if (result[8] != null)
                    cuentaAdministradorTemp.setCodigoBancoAdminSubsidio(result[8].toString());
                if (result[9] != null)
                    cuentaAdministradorTemp.setNombreBancoAdminSubsidio(result[9].toString());
                if (result[10] != null)
                    cuentaAdministradorTemp.setTipoCuentaAdminSubsidio(TipoCuentaEnum.valueOf(result[10].toString()));
                if (result[11] != null)
                    cuentaAdministradorTemp.setNumeroCuentaAdminSubsidio(result[11].toString());
                if (result[12] != null)
                    cuentaAdministradorTemp.setTipoIdentificacionTitularCuentaAdminSubsidio(TipoIdentificacionEnum.valueOf(result[12].toString()));
                if (result[13] != null)
                    cuentaAdministradorTemp.setNumeroIdentificacionTitularCuentaAdminSubsidio(result[13].toString());
                if (result[14] != null)
                    cuentaAdministradorTemp.setNombreTitularCuentaAdminSubsidio(result[14].toString());
                if (result[15] != null)
                    cuentaAdministradorTemp.setFechaHoraTransaccion((Date) result[15]);
                if (result[16] != null)
                logger.info("result[16].toString()"+result[16].toString());
                    cuentaAdministradorTemp.setUsuarioTransaccionLiquidacion(result[16].toString());
                if (result[17] != null)
                    cuentaAdministradorTemp.setValorOriginalTransaccion(BigDecimal.valueOf(Double.parseDouble(result[17].toString())));
                if (result[18] != null)
                    cuentaAdministradorTemp.setValorRealTransaccion(BigDecimal.valueOf(Double.parseDouble(result[18].toString())));
                if (result[19] != null)
                    cuentaAdministradorTemp.setIdTransaccionOriginal(Long.parseLong(result[19].toString()));
                if (result[20] != null)
                    cuentaAdministradorTemp.setIdRemisionDatosTerceroPagador(result[20].toString());
                if (result[21] != null)
                    cuentaAdministradorTemp.setIdTransaccionTerceroPagador(result[21].toString());
                if (result[22] != null)
                    cuentaAdministradorTemp.setNombreTerceroPagador(result[22].toString());
                if (result[23] != null)
                    cuentaAdministradorTemp.setIdCuentaAdminSubsidioRelacionado(Long.parseLong(result[23].toString()));
                if (result[24] != null)
                    cuentaAdministradorTemp.setFechaHoraUltimaModificacion((Date) result[24]);
                if (result[25] != null)
                    cuentaAdministradorTemp.setUsuarioUltimaModificacion(result[25].toString());

                if (result[26] != null && result[27] != null) {
                    cuentaAdministradorTemp.setTipoIdAdminSubsidio(TipoIdentificacionEnum.valueOf(result[26].toString()));
                    cuentaAdministradorTemp.setNumeroIdAdminSubsidio(result[27].toString());
                }
                if (result[28] != null)
                    cuentaAdministradorTemp.setNombresApellidosAdminSubsidio(result[28].toString());
                if (result[29] != null)
                    cuentaAdministradorTemp.setNombreSitioCobro(result[29].toString());
                if (result[30] != null)
                    cuentaAdministradorTemp.setIdPuntoDeCobro(result[30].toString());
                if (result[31] != null)
                    cuentaAdministradorTemp.setNombreSitioPago(result[31].toString());
                if (result[32] != null)
                    cuentaAdministradorTemp.setNombrePersonaAutorizada(result[32].toString());
                if (result[33] != null)
                    cuentaAdministradorTemp.setEstablecimientoCodigo(result[33].toString());
                if (result[34] != null)
                    cuentaAdministradorTemp.setEstablecimientoNombre(result[34].toString());
                if (result[35] != null)
                    cuentaAdministradorTemp.setFechaTransaccionConsumo(result[35].toString());

                if ((cuentaAdministradorTemp.getIdTransaccionTerceroPagador() != null) &&
                        (cuentaAdministradorTemp.getIdRemisionDatosTerceroPagador() == null &&
                                cuentaAdministradorTemp.getNombreTerceroPagador() == null)) {
                    cuentaAdministradorTemp.setIdTransaccionTerceroPagador(null);
                    cuentaAdministradorTemp.setNombreTerceroPagador(null);
                }

                CuentaAdministradorSubsidioList.add(cuentaAdministradorTemp);
            }
        }

        return CuentaAdministradorSubsidioList;
    }


    /**
     * Metodo que permite consultas los registros de la cuenta de administrador de subsidios por medio de un rango de la
     * fecha y hora de transacción del registro y una lista de identificadores de los administradores de subsidios relacionados con
     * las cuentas.
     *
     * @param detallesTransacciones DTO que contiene los filtros para la consulta.
     * @return lista de cuentas de administradores de subsidios.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<CuentaAdministradorSubsidioDTO> consultarCuentasFiltros(TransaccionConsultadaDTO detallesTransacciones) {
        String firmaServicio = "ConsultasModeloCore.consultarCuentasFiltros(DetalleTransaccionAsignadoConsultadoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Map<String, String> fields = new HashMap<>();
        Map<String, Object> values = new HashMap<>();

        if (detallesTransacciones.getFechaInicio() != null) {
            values.put("fechaHoraInicio", detallesTransacciones.getFechaInicio());
        }
        if (detallesTransacciones.getFechaFin() != null) {
            fields.put("fechaHoraTransaccion", ConsultasDinamicasConstants.BETWEEN);
            values.put("fechaHoraFin", detallesTransacciones.getFechaFin());
        }
        if (detallesTransacciones.getTipoTransaccion() != null) {
            fields.put("tipoTransaccionSubsidio", ConsultasDinamicasConstants.IGUAL);
            values.put("tipoTransaccionSubsidio", detallesTransacciones.getTipoTransaccion());
        }
        if (detallesTransacciones.getEstadoTransaccion() != null) {
            fields.put("estadoTransaccionSubsidio", ConsultasDinamicasConstants.IGUAL);
            values.put("estadoTransaccionSubsidio", detallesTransacciones.getEstadoTransaccion());
        }
        if (detallesTransacciones.getMedioDePago() != null) {
            fields.put("medioDePagoTransaccion", ConsultasDinamicasConstants.IGUAL);
            values.put("medioDePagoTransaccion", detallesTransacciones.getMedioDePago());
        }
        if (detallesTransacciones.getListaIdAdminSubsidios() != null && !detallesTransacciones.getListaIdAdminSubsidios().isEmpty()) {
            fields.put("idAdministradorSubsidio", ConsultasDinamicasConstants.IN);
            values.put("idAdministradorSubsidio", detallesTransacciones.getListaIdAdminSubsidios());
        }

        List<CuentaAdministradorSubsidioDTO> listaCuentas = new ArrayList<>();
        List<CuentaAdministradorSubsidio> cuentasConsultadas = JPAUtils.consultaEntidad(entityManagerCore,
                CuentaAdministradorSubsidio.class, fields, values);
        for (CuentaAdministradorSubsidio cuenta : cuentasConsultadas) {
            listaCuentas.add(new CuentaAdministradorSubsidioDTO(cuenta));
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return listaCuentas.isEmpty() ? null : listaCuentas;
    }

    /**
     * Metodo que permite consultas los registros de la cuenta de administrador de subsidios por medio de un rango de la
     * fecha y hora de transacción del registro y una lista de identificadores de los administradores de subsidios relacionados con
     * las cuentas.
     *
     * @param detallesTransacciones DTO que contiene los filtros para la consulta.
     * @return lista de cuentas de administradores de subsidios.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<CuentaAdministradorSubsidioDTO> consultarCuentasPorListas(DetalleTransaccionAsignadoConsultadoDTO detallesTransacciones) {
        String firmaServicio = "ConsultasModeloCore.consultarCuentasPorEmpleadores(DetalleTransaccionAsignadoConsultadoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);


        Map<String, String> fields = new HashMap<>();
        Map<String, Object> values = new HashMap<>();


        if (detallesTransacciones.getListaIdAdminSubsidios() != null && !detallesTransacciones.getListaIdAdminSubsidios().isEmpty()) {
            fields.put("idAdministradorSubsidio", ConsultasDinamicasConstants.IN);
            values.put("idAdministradorSubsidio", detallesTransacciones.getListaIdAdminSubsidios());
        }

        if (detallesTransacciones.getListaIdEmpleadores() != null && !detallesTransacciones.getListaIdEmpleadores().isEmpty()) {
            fields.put("idEmpleador", ConsultasDinamicasConstants.IN);
            values.put("idEmpleador", detallesTransacciones.getListaIdEmpleadores());
        }

        if (detallesTransacciones.getListaIdAfiliadosPrincipales() != null && !detallesTransacciones.getListaIdAfiliadosPrincipales().isEmpty()) {
            fields.put("idAfiliadoPrincipal", ConsultasDinamicasConstants.IN);
            values.put("idAfiliadoPrincipal", detallesTransacciones.getListaIdAfiliadosPrincipales());
        }

        if (detallesTransacciones.getListaIdBeneficiarios() != null && !detallesTransacciones.getListaIdBeneficiarios().isEmpty()) {
            fields.put("idBeneficiarioDetalle", ConsultasDinamicasConstants.IN);
            values.put("idBeneficiarioDetalle", detallesTransacciones.getListaIdBeneficiarios());
        }

        List<CuentaAdministradorSubsidioDTO> listaCuentas = new ArrayList<>();
        List<CuentaAdministradorSubsidio> cuentasConsultadas = JPAUtils.consultaEntidad(entityManagerCore,
                CuentaAdministradorSubsidio.class, fields, values);
        for (CuentaAdministradorSubsidio cuenta : cuentasConsultadas) {
            listaCuentas.add(new CuentaAdministradorSubsidioDTO(cuenta));
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaCuentas.isEmpty() ? null : listaCuentas;
    }

    /**
     * <b>Descripción:</b>Método encargado de registrar anulación de subsidios asignados cobrados.
     * <b>Módulo:</b> Asopagos - HU-31-222<br/>
     *
     * @param nombreUsuario <code>String</code>
     *                      Representa el nombre de usuario de quien realiza el registro de una solicitud de anulación de subsidio cobrado
     * @return <code>Solicitud</code>
     * Representa una solicitud global que sera user usada en el proceso de anulacion de subsidio monetario cobrado
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private Solicitud crearSolicitudGlobal(String usuarioRadicacion) {
        String firmaMetodo = "ConsultasModeloCore.crearSolicitudGlobal( String )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Solicitud solGlobal = null;
        try {
            solGlobal = new Solicitud();
            solGlobal.setCanalRecepcion(CanalRecepcionEnum.WEB);
            solGlobal.setFechaRadicacion(new Date());
            solGlobal.setFechaCreacion(new Date());
            solGlobal.setTipoTransaccion(TipoTransaccionEnum.REGISTRO_SOLICITUD_ANULACION_SUBSIDIO_COBRADO);
            solGlobal.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
            solGlobal.setUsuarioRadicacion(usuarioRadicacion);
            entityManagerCore.persist(solGlobal);
        } catch (PersistenceException pe) {
            logger.error(
                    ConstantesComunes.FIN_LOGGER + firmaMetodo
                            + ": No se logro realizar la creacion de la solicitud global para la solicitud de anulación de subsidio cobrado",
                    pe);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        } catch (Exception e) {
            logger.error(
                    ConstantesComunes.FIN_LOGGER + firmaMetodo
                            + ": No se logro realizar la creacion de la solicitud global para la solicitud de anulación de subsidio cobrado",
                    e);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return solGlobal;
    }

    /**
     * <b>Descripción:</b>Método encargado de crear la solicitud de anulación de subsidios asignados cobrados.
     * <b>Módulo:</b> Asopagos - HU-31-222<br/>
     *
     * @return <code>SolicitudAnulacionSubsidioCobrado</code>
     * Entidad que representa los datos de una solicitud de anulación de subsidio cobrado
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private SolicitudAnulacionSubsidioCobrado crearSolicitudAnulacionSubsidioCobrado(Long idSolicitudGlobal) {
        String firmaMetodo = "ConsultasModeloCore.crearSolicitudAnulacionSubsidioCobrado( Long )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        SolicitudAnulacionSubsidioCobrado solAnulacionSubsidioCobrado = null;
        try {
            solAnulacionSubsidioCobrado = new SolicitudAnulacionSubsidioCobrado();
            solAnulacionSubsidioCobrado.setIdSolicitudGlobal(idSolicitudGlobal);
            solAnulacionSubsidioCobrado.setFechaHoraCreacionSolicitud(new Date());
            solAnulacionSubsidioCobrado.setEstadoSolicitud(EstadoSolicitudAnulacionSubsidioCobradoEnum.PENDIENTE);
            solAnulacionSubsidioCobrado.setMotivoAnulacion(MotivoAnulacionSubsidioAsignadoEnum.COBRADO_DINERO_NO_ENTREGADO);
            entityManagerCore.persist(solAnulacionSubsidioCobrado);
        } catch (PersistenceException pe) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo
                    + ": No se logro realizar la creacion de la solicitud de anulación de subsidio cobrado", pe);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo
                    + ": No se logro realizar la creacion de la solicitud de anulación de subsidio cobrado", e);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return solAnulacionSubsidioCobrado;

    }

    /**
     * <b>Descripción:</b>Método encargado de crear y asociar los abonos al detalle de la solicitud de anulación de subsidios asignados
     * cobrados.
     * <b>Módulo:</b> Asopagos - HU-31-222<br/>
     *
     * @param idSolicitudAnulacionSubsidioCobrado <code>Long</code>
     *                                            Representa el identificador de solicitud Anulacion Subsidio Cobrado para relacionar en el detalle
     * @param idSolicitudAnulacionSubsidioCobrado <code>List<AbonosAnulacionSubsidioCobradoDTO></code>
     *                                            Representa la información que representa los abonos cobrados asociados a una solicitud de anulación de subsidio cobrado
     * @return <code>List<DetalleSolicitudAnulacionSubsidioCobrado></code>
     * Entidad que representa los datos de una solicitud de anulación de subsidio cobrado
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private List<DetalleSolicitudAnulacionSubsidioCobrado> asociarAbonosSolicitudAnulacionSubsidioCobrado(
            Long idSolicitudAnulacionSubsidioCobrado, List<AbonosAnulacionSubsidioCobradoDTO> lstAbonosAnulacionSubsidioCobradoDTO) {
        String firmaMetodo = "ConsultasModeloCore.crearDetalleSolicitudAnulacionSubsidioCobrado( Long , List<AbonosAnulacionSubsidioCobradoDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        DetalleSolicitudAnulacionSubsidioCobrado detalleSolicitudAnulacionSubsidioCobrado = null;
        List<DetalleSolicitudAnulacionSubsidioCobrado> lstDetalleSolicitudAnulacionSubsidioCobrado = null;
        try {
            lstDetalleSolicitudAnulacionSubsidioCobrado = new ArrayList<>();
            for (AbonosAnulacionSubsidioCobradoDTO abonosAnulacionSubsidioCobradoDTO : lstAbonosAnulacionSubsidioCobradoDTO) {
                detalleSolicitudAnulacionSubsidioCobrado = new DetalleSolicitudAnulacionSubsidioCobrado();
                detalleSolicitudAnulacionSubsidioCobrado.setIdSolicitudAnulacionSubsidio(idSolicitudAnulacionSubsidioCobrado);
                detalleSolicitudAnulacionSubsidioCobrado
                        .setIdCuentaAdministradorSubsidio(abonosAnulacionSubsidioCobradoDTO.getIdCuentaAdministradorSubsidio());
                detalleSolicitudAnulacionSubsidioCobrado.setDetalleAnulacion(abonosAnulacionSubsidioCobradoDTO.getOtrosDetallesAnulacion());
                entityManagerCore.persist(detalleSolicitudAnulacionSubsidioCobrado);
                lstDetalleSolicitudAnulacionSubsidioCobrado.add(detalleSolicitudAnulacionSubsidioCobrado);

                //se setean campos de detalle anulacion y motivo
                List<DetalleSubsidioAsignadoDTO> detalles = consultarDetallesSubsidiosAsignadosAsociadosAbono(abonosAnulacionSubsidioCobradoDTO.getIdCuentaAdministradorSubsidio());
                for (DetalleSubsidioAsignadoDTO detalleSubsidioAsignadoDTO : detalles) {
                    DetalleSubsidioAsignado detalle = entityManagerCore.find(DetalleSubsidioAsignado.class, detalleSubsidioAsignadoDTO.getIdDetalleSubsidioAsignado());
                    detalle.setDetalleAnulacion(abonosAnulacionSubsidioCobradoDTO.getOtrosDetallesAnulacion());
                    detalle.setMotivoAnulacion(abonosAnulacionSubsidioCobradoDTO.getMotivoAnulacion());
                    entityManagerCore.merge(detalle);
                }
                // Mantis 0252189 - se asocian los detalles seleccionados para mostrar en la siguiente pantalla
                List<DetalleSubsidioAsignadoDTO> detallesSeleccionados = abonosAnulacionSubsidioCobradoDTO.getListaDetallesSubsidioAsignadoDTO();
                for (DetalleSubsidioAsignadoDTO detalleSeleccionado : detallesSeleccionados) {
                    DetalleSubsidioAsignado detalle = entityManagerCore.find(DetalleSubsidioAsignado.class, detalleSeleccionado.getIdDetalleSubsidioAsignado());
                    detalle.setIdDetalleSolicitudAnulacionSubsidioCobrado(detalleSolicitudAnulacionSubsidioCobrado.getIdDetalleSolicitudAnulacionSubsidioCobrado());
                    entityManagerCore.merge(detalle);
                }

            }
            if (lstDetalleSolicitudAnulacionSubsidioCobrado.size() != lstAbonosAnulacionSubsidioCobradoDTO.size()) {
                logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo
                        + "No se logro realizar la creacion los detalles de la solicitud de anulación de subsidio cobrado correctamente");
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }
        } catch (PersistenceException pe) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo
                    + ": No se logro realizar la creacion los detalles de la solicitud de anulación de subsidio cobrado", pe);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo
                    + ": No se logro realizar la creacion los detalles de la solicitud de anulación de subsidio cobrado", e);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstDetalleSolicitudAnulacionSubsidioCobrado;

    }

    /**
     * Metodo encargado de persistir el convenio del tercero pagador.
     *
     * @param convenioDTO Clase DTO con la información del convenio.
     * @return Id del convenio
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private Long guardarConvenioTerceroPagador(ConvenioTercerPagadorDTO convenioDTO) {

        ConvenioTerceroPagador convenioTerceroPagador = null;
        try {
            //Persistencia del convenio del tercero pagador
            convenioTerceroPagador = convenioDTO.convertToConvenioTerceroPagadorEntity();
            entityManagerCore.persist(convenioTerceroPagador);
            convenioDTO.setIdConvenio(convenioTerceroPagador.getIdConvenio());
        } catch (Exception e2) {
            logger.error("Ocurrió un error inesperado en la creación del convenio del tercero pagador", e2);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        return convenioTerceroPagador.getIdConvenio();
    }

    /**
     * Metodo encargado de persistir el documento de soporte de los convenios del tercero pagador
     *
     * @param documento DTO del documento de soporte
     * @return id documento soporte
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private Long guardarDocumentoSoporte(DocumentoSoporteConvenioModeloDTO documento) {
        DocumentoSoporte documentoSoporte = null;
        try {
            //Persistencia del documento de soporte 
            documentoSoporte = documento.convertToEntity();
            entityManagerCore.persist(documentoSoporte);

        } catch (Exception e2) {
            logger.error("Ocurrió un error inesperado en la creación del documento de soporte", e2);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        return documentoSoporte.getIdDocumentoSoporte();
    }

    /**
     * Metodo encargado de persistir la relación del documento de soporte con el convenio del tercero pagador
     *
     * @param idConvenioTerceroPagador Identificador del convenio del tercero pagador
     * @param idDocumentoSoporte       Identificador del documento de soporte
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void guardarDocumentoSoporteConvenio(Long idConvenioTerceroPagador, Long idDocumentoSoporte) {
        try {
            //Persistencia de la entidad que asocia el documento de soporte con el convenio
            DocumentoSoporteConvenio documentoSoporteConvenio = new DocumentoSoporteConvenio();
            documentoSoporteConvenio.setIdConvenioTerceroPagador(idConvenioTerceroPagador);
            documentoSoporteConvenio.setIdDocumentoSoporte(idDocumentoSoporte);
            entityManagerCore.persist(documentoSoporteConvenio);

        } catch (Exception e2) {
            logger.error("Ocurrió un error inesperado en la creación del documento de soporte del convenio del tercero pagador", e2);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Metodo encargado de crear un detalle de subsidio asignado
     *
     * @param detalleDTO DTO del detalle de subsidio asignado que contiene la información para crear el detalle.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void crearDetalleSubsidioAsignado(DetalleSubsidioAsignadoDTO detalleDTO) {

        DetalleSubsidioAsignado detalleSubsidioAsignado = detalleDTO.convertToEntity();

        if (detalleSubsidioAsignado.getUsuarioCreador() == null) {
            detalleSubsidioAsignado.setUsuarioCreador("");
        }


        // try {
            logger.info("Melissa prueba 1"+ detalleSubsidioAsignado.toString());
            entityManagerCore.persist(detalleSubsidioAsignado);
        // } catch (Exception e) {
        //     logger.error("Ocurrió un error inesperado en crearDetalleSubsidioAsignado", e);
        //     throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        // }
    }

    /**
     * Metodo encargado de crear un detalle de subsidio asignado
     *
     * @param detalleDTO DTO del detalle de subsidio asignado que contiene la información para crear el detalle.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private Long registrarDetalleSubsidioAsignado(DetalleSubsidioAsignadoDTO detalleDTO) {
        logger.info("Brandon---- detalleDTO: " + detalleDTO);
        DetalleSubsidioAsignado detalleSubsidioAsignado = detalleDTO.convertToEntity();
        logger.info("Brandon---- registrarDetalleSubsidioAsignado(DetalleSubsidioAsignadoDTO detalleDTO)");
        logger.info("Brandon---- detalleSubsidioAsignado: ");
        System.out.println(detalleSubsidioAsignado);
        try {
            entityManagerCore.persist(detalleSubsidioAsignado);
            logger.info("Brandon---- detalleSubsidioAsignado.getIdDetalleSubsidioAsignado(): " + detalleSubsidioAsignado.getIdDetalleSubsidioAsignado());
            return detalleSubsidioAsignado.getIdDetalleSubsidioAsignado();
        } catch (Exception e) {
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Metodo que permite actualizar el registro de operación que se esta ejecutando, ingresando los parametros de salida.
     *
     * @param registroOperacionesSubsidio Objeto del registro de operaciones de subsidio que se actualizara con nuevos datos.
     * @param parametrosOUT               Parametros de salida de la operación que se esta ejecutando.
     */
    private void modificarRegistroOperacionSubsidio(RegistroOperacionTransaccionSubsidio registroOperacionesSubsidio,
                                                    String parametrosOUT, String tiempo, String url) {

        try {
            registroOperacionesSubsidio.setParametrosOUT(parametrosOUT);
            registroOperacionesSubsidio.setUrl(url);
            registroOperacionesSubsidio.setTiempo(tiempo);
            entityManagerCore.merge(registroOperacionesSubsidio);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Metodo encargado de buscar un registro de operación de subsidio por medio del identificador
     *
     * @param identificadorRespuesta Identificador principal del registro de operación del subsidio.
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public RegistroOperacionTransaccionSubsidio buscarRegistroOperacionSubsidio(Long identificadorRespuesta) {
        RegistroOperacionTransaccionSubsidio registroOperacionesSubsidio = null;

        try {
            registroOperacionesSubsidio = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_OPERACION_SUBSIDIO_POR_ID,
                            RegistroOperacionTransaccionSubsidio.class)
                    .setParameter("idRegistro", identificadorRespuesta).getSingleResult();

        } catch (NoResultException e1) {
            logger.error("No se encontro el registro de operación de subsidio", e1);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        }
        return registroOperacionesSubsidio;
    }

    /**
     * <b>Descripción:</b>Método que permite obtener los totales asociados a la dispersión de un proceso de liquidación
     *
     * @param numeroRadicacion <code>Long</code>
     *                         Valor del número de radicación
     * @return <code>DispersionMontoLiquidadoDTO</code>
     * Información que representa los totales pendientes por dispersar
     * @author <a>Roy López Cardona</a>
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private DispersionMontoLiquidadoDTO consultarTotales(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarTotales(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            Object[] registroTotales = (Object[]) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DISPERSION_TOTALES_PENDIENTES)
                    .setParameter("numeroRadicacion", numeroRadicacion).getSingleResult();

            DispersionMontoLiquidadoDTO dispersionMontoDTO = new DispersionMontoLiquidadoDTO();
            dispersionMontoDTO.setMontoTotalDispersion(BigDecimal.valueOf(Double.parseDouble(registroTotales[0].toString())));
            dispersionMontoDTO.setTotalDescuentosAplicados(BigDecimal.valueOf(Double.parseDouble(registroTotales[1].toString())));
            dispersionMontoDTO.setMontoTotalLiquidar(BigDecimal.valueOf(Double.parseDouble(registroTotales[2].toString())));
            dispersionMontoDTO.setCantidadCuotasDispersar(Long.parseLong(registroTotales[3].toString()));
            dispersionMontoDTO.setCantidadAdministradorSubsidios(Long.parseLong(registroTotales[4].toString()));

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return dispersionMontoDTO;
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * <b>Descripción:</b>Método que permite obtener los totales asociados a la dispersión para el medio de pago tarjeta
     *
     * @param numeroRadicacion <code>Long</code>
     *                         Valor del número de radicación
     * @return <code>DispersionResumenPagoTarjetaDTO</code>
     * Información que representa los totales pendientes por dispersar al medio de pago tarjeta
     * @author <a>Roy López Cardona</a>
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private DispersionResumenPagoTarjetaDTO consultarResumenPagosTarjeta(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarResumenPagosTarjeta(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            Object[] registroTotalesTarjeta = (Object[]) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_RESUMEN_PAGOS_TARJETA)
                    .setParameter("numeroRadicacion", numeroRadicacion).getSingleResult();

            DispersionResumenPagoTarjetaDTO dispersionTarjetaDTO = new DispersionResumenPagoTarjetaDTO();
            dispersionTarjetaDTO.setNumeroRegistros(Long.parseLong(registroTotalesTarjeta[0].toString()));
            dispersionTarjetaDTO.setMontoTotal(BigDecimal.valueOf(Double.parseDouble(registroTotalesTarjeta[1].toString())));
            dispersionTarjetaDTO.setCantidadCuotas(Long.parseLong(registroTotalesTarjeta[2].toString()));
            dispersionTarjetaDTO.setNumeroAdministradoresSubsidio(Long.parseLong(registroTotalesTarjeta[3].toString()));

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return dispersionTarjetaDTO;
        } catch (NoResultException e) {
            return new DispersionResumenPagoTarjetaDTO();
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * <b>Descripción:</b>Método que permite obtener los totales asociados a la dispersión para el medio de pago efectivo
     *
     * @param numeroRadicacion <code>Long</code>
     *                         Valor del número de radicación
     * @return <code>DispersionResumenPagoEfectivoDTO</code>
     * Información que representa los totales pendientes por dispersar al medio de pago efectivo
     * @author <a>Roy López Cardona</a>
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private DispersionResumenPagoEfectivoDTO consultarResumenPagosEfectivo(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarResumenPagosEfectivo(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            Object[] registroTotalesEfectivo = (Object[]) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_RESUMEN_PAGOS_EFECTIVO)
                    .setParameter("numeroRadicacion", numeroRadicacion).getSingleResult();

            DispersionResumenPagoEfectivoDTO dispersionEfectivoDTO = new DispersionResumenPagoEfectivoDTO();
            dispersionEfectivoDTO.setMontoTotal(BigDecimal.valueOf(Double.parseDouble(registroTotalesEfectivo[0].toString())));
            dispersionEfectivoDTO.setCantidadCuotas(Long.parseLong(registroTotalesEfectivo[1].toString()));
            dispersionEfectivoDTO.setNumeroAdministradoresSubsidio(Long.parseLong(registroTotalesEfectivo[2].toString()));

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return dispersionEfectivoDTO;
        } catch (NoResultException e) {
            return new DispersionResumenPagoEfectivoDTO();
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * <b>Descripción:</b>Método que permite obtener los totales asociados a la dispersión para el medio de pago banco
     *
     * @param numeroRadicacion <code>Long</code>
     *                         Valor del número de radicación
     * @return <code>List<DispersionResumenPagoBancoDTO></code>
     * Información que representa los totales pendientes por dispersar al medio de pago banco
     * @author <a>Roy López Cardona</a>
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<DispersionResumenPagoBancoDTO> consultarResumenPagosBanco(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarResumenPagosBanco(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            List<Object[]> registrosPagosBanco = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_RESUMEN_PAGOS_BANCO)
                    .setParameter("numeroRadicacion", numeroRadicacion).getResultList();

            List<DispersionResumenPagoBancoDTO> pagosBancoDTO = new ArrayList<DispersionResumenPagoBancoDTO>();
            for (Object[] registroPagoBanco : registrosPagosBanco) {
                DispersionResumenPagoBancoDTO pagoBancoDTO = new DispersionResumenPagoBancoDTO();

                pagoBancoDTO.setIdBanco(BigDecimal.valueOf(Integer.parseInt(registroPagoBanco[0].toString())));
                pagoBancoDTO.setNombre(registroPagoBanco[1].toString());
                pagoBancoDTO.setMontoTotal(BigDecimal.valueOf(Double.parseDouble(registroPagoBanco[2].toString())));
                if (registroPagoBanco[3] != null) {
                    Boolean esPagosJudiciales = Boolean.parseBoolean(registroPagoBanco[3].toString());
                    pagoBancoDTO.setTipoTransaccionBanco(esPagosJudiciales ? TipoTransaccionPagoBancoEnum.PAGOS_JUDICIALES
                            : TipoTransaccionPagoBancoEnum.CONSIGNACIONES);
                } else {
                    pagoBancoDTO.setTipoTransaccionBanco(TipoTransaccionPagoBancoEnum.CONSIGNACIONES);
                }

                pagosBancoDTO.add(pagoBancoDTO);
            }

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return pagosBancoDTO;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * <b>Descripción:</b>Método que permite obtener los totales asociados a la dispersión por entidades de descuento
     *
     * @param numeroRadicacion <code>Long</code>
     *                         Valor del número de radicación
     * @return <code>List<DispersionResumenEntidadDescuentoDTO></code>
     * Información que representa los totales pendientes por dispersar para las entidades de descuento
     * @author <a>Roy López Cardona</a>
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<DispersionResumenEntidadDescuentoDTO> consultarResumenPagosEntidadesDescuento(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarResumenPagosEntidadesDescuento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<DispersionResumenEntidadDescuentoDTO> entidadesDescuentoDTO = new ArrayList<DispersionResumenEntidadDescuentoDTO>();
        try {
            List<Object[]> registrosEntidadesInternas = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_RESUMEN_PAGOS_ENTIDAD_DESCUENTO_INTERNA)
                    .setParameter("numeroRadicacion", numeroRadicacion).getResultList();

            for (Object[] registroEntidadInterna : registrosEntidadesInternas) {
                DispersionResumenEntidadDescuentoDTO pagoEntidadDTO = new DispersionResumenEntidadDescuentoDTO();

                pagoEntidadDTO.setCodigoEntidad(registroEntidadInterna[0].toString());
                pagoEntidadDTO.setIdEntidad(Long.parseLong(registroEntidadInterna[1].toString()));
                pagoEntidadDTO.setNombre(registroEntidadInterna[2].toString());
                pagoEntidadDTO.setMontoTotal(BigDecimal.valueOf(Double.parseDouble(registroEntidadInterna[3].toString())));
                pagoEntidadDTO.setTipoEntidad(TipoEntidadDescuentoEnum.INTERNA);

                entidadesDescuentoDTO.add(pagoEntidadDTO);
            }

            List<Object[]> registrosEntidadesExternas = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_RESUMEN_PAGOS_ENTIDAD_DESCUENTO_EXTERNA)
                    .setParameter("numeroRadicacion", numeroRadicacion).getResultList();

            for (Object[] registroEntidadExterna : registrosEntidadesExternas) {
                DispersionResumenEntidadDescuentoDTO pagoEntidadDTO = new DispersionResumenEntidadDescuentoDTO();

                pagoEntidadDTO.setCodigoEntidad(registroEntidadExterna[0].toString());
                pagoEntidadDTO.setIdEntidad(Long.parseLong(registroEntidadExterna[1].toString()));
                pagoEntidadDTO.setNombre(registroEntidadExterna[2].toString());
                pagoEntidadDTO.setMontoTotal(BigDecimal.valueOf(Double.parseDouble(registroEntidadExterna[3].toString())));
                pagoEntidadDTO.setTipoEntidad(TipoEntidadDescuentoEnum.EXTERNA);

                entidadesDescuentoDTO.add(pagoEntidadDTO);
            }

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return entidadesDescuentoDTO;
    }

    /**
     * <b>Descripción:</b>Método que permite obtener los datos del encabezado para el detalle de los pagos pendientes por dispersión
     *
     * @param numeroRadicacion <code>String</code>
     *                         Valor de número de radicación
     * @param medioDePago      <code>TipoMedioDePagoEnum</code>
     *                         Tipo del medio de pago a consultar
     * @return Información para la cabecera correspondiente a lo parametrizado
     * @author <a>Roy López Cardona</a>
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private Object[] consultarDatosEncabezadoDetallePagos(String numeroRadicacion, TipoMedioDePagoEnum medioDePago) {
        String firmaMetodo = "ConsultasModeloCore.consultarDatosEncabezadoDetallePagos(String,TipoMedioDePagoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            Object[] registroEnzabezado = (Object[]) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_PAGOS_ENCABEZADO)
                    .setParameter("numeroRadicacion", numeroRadicacion).setParameter("medioDePago", medioDePago.toString())
                    .getSingleResult();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return registroEnzabezado;
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        }/* catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        */
    }

    /**
     * Método que permite guardar cada registro de la tarjeta que corresponde al archivo de consumo que se carga por parte de ANIBOL.
     *
     * @param idArchivoConsumoTarjeta   <code>Long</code>
     *                                  Identificador principal del registro del archivo conla información global, asociado a cada registro de tarjeta.
     * @param tarjetaRetiroCandidatoDTO <code>TarjetaRetiroCandidatoDTO</code>
     *                                  DTO con la información de cada registro de tarjeta
     * @return
     */
    private Long crearRegistroTarjetasArchivoConsumoANIBOL(Long idArchivoConsumoTarjeta,
                                                           TarjetaRetiroCandidatoDTO tarjetaRetiroCandidatoDTO) {
        String firmaMetodo = "ConsultasModeloCore.crearRegistroTarjetasArchivoConsumoANIBOL(Long, TarjetaRetiroCandidatoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RegistroArchivoConsumosAnibol registroTarjeta = tarjetaRetiroCandidatoDTO.convertToEntity();
        registroTarjeta.setIdArchivoConsumosAnibol(idArchivoConsumoTarjeta);

        try {
            entityManagerCore.persist(registroTarjeta);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return registroTarjeta.getIdRegistroArchivoConsumosSubsidio();
    }

    /**
     * Metodo que permite guardar los errores de los campos de un registro de tarjeta, en el proceso de carga y validación
     * del archivo de consumo de tarjeta ANIBOL.
     *
     * @param idRegistroTarjeta       <code>Long</code>
     *                                Identificador principal del registro de la tarjeta que se le asociara al campo.
     * @param lstValoresCamposErrores <code>List<ResultadoHallazgosValidacionArchivoDTO></code>
     *                                lista de DTOs que contiene la información de los errores ocurridos de los campos asociados al registro de tarjeta
     */
    private void crearErrorCampoArchivoConsumoANIBOL(Long idRegistroTarjeta,
                                                     List<ResultadoHallazgosValidacionArchivoDTO> lstValoresCamposErrores) {

        String firmaMetodo = "ConsultasModeloCore.crearErrorCampoArchivoConsumoANIBOL(Long, ResultadoHallazgosValidacionArchivoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        CampoArchivoConsumosAnibol campoArchivoConsumosAnibol;

        for (ResultadoHallazgosValidacionArchivoDTO valorCampo : lstValoresCamposErrores) {
            campoArchivoConsumosAnibol = new CampoArchivoConsumosAnibol();
            campoArchivoConsumosAnibol.setIdRegistroArchivoConsumosSubsidioMonetario(idRegistroTarjeta);
            TipoInconsistenciaCampoArchivoConsumosAnibolEnum inconsistenciaCampo = TipoInconsistenciaCampoArchivoConsumosAnibolEnum
                    .valueOf(valorCampo.getNombreCampo());
            campoArchivoConsumosAnibol.setInconsistenciaContenidoDetectada(inconsistenciaCampo);
            String[] campos = valorCampo.getError().split("\\.");
            //se obtiene el valor del campo que viene despues del punto del mensaje
            campoArchivoConsumosAnibol.setValorCampoArchivo(campos[1]);
            entityManagerCore.persist(campoArchivoConsumosAnibol);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Método encargado de crear una ubicación.
     *
     * @param ubicacionModeloDTO <code>UbicacionModeloDTO</code>
     *                           DTO que contiene la información correspondiente de la ubicación
     * @return Id de la Ubicación registrada.
     */
    private Long crearUbicacion(UbicacionModeloDTO ubicacionModeloDTO) {
        Ubicacion ubicacion = null;
        try {
            //Persistencia de la ubicación 
            ubicacion = ubicacionModeloDTO.convertToEntity();
            entityManagerCore.persist(ubicacion);
        } catch (Exception e2) {
            logger.error("Ocurrió un error inesperado en la creación de la ubicación", e2);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        return ubicacion.getIdUbicacion();
    }

    /**
     * Método encargado de actualizar una ubicación
     *
     * @param ubicacionModeloDTO <code>UbicacionModeloDTO</code>
     *                           DTO que contiene la información correspondiente de la ubicación
     */
    private void actualizarUbicacion(UbicacionModeloDTO ubicacionModeloDTO) {
        try {
            Ubicacion ubicacion = ubicacionModeloDTO.convertToEntity();
            entityManagerCore.merge(ubicacion);
        } catch (Exception e2) {
            logger.error("Ocurrió un error inesperado en la actualizacion de la ubicación", e2);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#dispersarPagosEstadoEnviado(java.lang.String,
     * com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoTransaccionSubsidioEnum, java.util.List)
     */
    @Override
    //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void dispersarPagosEstadoEnviado(String numeroRadicacion, EstadoTransaccionSubsidioEnum estadoTransaccion,
                                            List<TipoMedioDePagoEnum> mediosDePago, UserDTO userDTO) {
        String firmaMetodo = "ConsultasModeloCore.dispersarPagosEstadoEnviado(String,EstadoTransaccionSubsidioEnum,List<TipoMedioDePagoEnum>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo + "numeroRadicacion: " + numeroRadicacion + "-estadoTransaccion: " + estadoTransaccion + "-mediosDePago: " + mediosDePago);
        /*
        List<String> lstMediosPago = mediosDePago.stream().map(e -> e.name()).collect(Collectors.toList()); 
        
         entityManagerCore.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_DISPERSION_ESTADO)
         .setParameter("numeroRadicacion", numeroRadicacion)
         .setParameter("lstMediosPago", lstMediosPago)
         .setParameter("sizeabonosExitosos", null)
         .setParameter("abonosExitosos", null)    
         .setParameter("estadoTransaccion", estadoTransaccion.name()).executeUpdate();            
        */
        
        /*
        List<CuentaAdministradorSubsidio> cuentasAdministradorSubsidio = consultarCuentasAdministradorPorRadicacionMediosDePago(
                numeroRadicacion, mediosDePago);

        for (CuentaAdministradorSubsidio cuentaAdministradorSubsidio : cuentasAdministradorSubsidio) {
            //Mantis 259718 Si el valor es igual a cero se actualiza a estado COBRADO
            if (cuentaAdministradorSubsidio.getValorRealTransaccion().compareTo(BigDecimal.ZERO) == 0) {
                cuentaAdministradorSubsidio.setEstadoTransaccionSubsidio(EstadoTransaccionSubsidioEnum.COBRADO);
            } else {
                cuentaAdministradorSubsidio.setEstadoTransaccionSubsidio(estadoTransaccion);
            }
            entityManagerCore.merge(cuentaAdministradorSubsidio);
        }
        */

        //UserDTO userDTO = new UserDTO();
        StringBuilder listaMediosPago = new StringBuilder();
        if (!mediosDePago.isEmpty()) {
            int count = 0;
            for (TipoMedioDePagoEnum tipoMedioPago : mediosDePago) {
                listaMediosPago.append(tipoMedioPago.name());
                if (count < mediosDePago.size()) {
                    listaMediosPago.append(",");
                }
                count++;
            }
        }

        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            userDTO.setEmail("");
        }

        /*Se actualiza le estado de las cuentas con abonos exitosos a cobrado o enviado*/
        entityManagerCore.createStoredProcedureQuery(NamedQueriesConstants.USP_PG_DISPERSAR_PAGOS_ESTADO_ENVIADO)
                .registerStoredProcedureParameter("sUsuario", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("sNumeroRadicacion", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("sEstadoTransaccion", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("listaMediosPago", String.class, ParameterMode.IN)
                .setParameter("sUsuario", userDTO.getEmail())
                .setParameter("sNumeroRadicacion", numeroRadicacion)
                .setParameter("sEstadoTransaccion", estadoTransaccion.name())
                .setParameter("listaMediosPago", listaMediosPago.length() == 0 ? "" : listaMediosPago.toString()).execute();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#dispersarPagosEstadoAplicado(java.lang.String,
     * java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void dispersarPagosEstadoAplicado(String numeroRadicacion, List<Long> abonosExitosos, UserDTO userDTO) {
        String firmaMetodo = "ConsultasModeloCore.dispersarPagosEstadoAplicado(String,List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        StringBuilder listaAbonosExitosos = new StringBuilder();
        List<String> lstMediosPago = new ArrayList<>();
        lstMediosPago.add(TipoMedioDePagoEnum.EFECTIVO.name());
        if (!abonosExitosos.isEmpty()) {
            lstMediosPago.add(TipoMedioDePagoEnum.TARJETA.name());

            //Se crea una cadena con los abonos exitosos
            int countAbonos = 0;
            for (Long abonoExitoso : abonosExitosos) {
                listaAbonosExitosos.append(abonoExitoso);
                if (countAbonos < abonosExitosos.size()) {
                    listaAbonosExitosos.append(",");
                }
                countAbonos++;
            }
        }
        /*
        entityManagerCore.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_DISPERSION_ESTADO)
        .setParameter("numeroRadicacion", numeroRadicacion)
        .setParameter("lstMediosPago", lstMediosPago)
        .setParameter("sizeabonosExitosos", abonosExitosos.isEmpty()?null:abonosExitosos.size())
        .setParameter("abonosExitosos", abonosExitosos.isEmpty()?null:abonosExitosos)            
        .setParameter("estadoTransaccion", EstadoTransaccionSubsidioEnum.APLICADO.name()).executeUpdate();          
     */
        /*
        List<CuentaAdministradorSubsidio> cuentasAdministradores = consultarCuentasAdministradorRadicacionMediosDePagoId(
                numeroRadicacion, abonosExitosos);

        for (CuentaAdministradorSubsidio cuentaAdministradorSubsidio : cuentasAdministradores) {
          //Mantis 259718 Si el valor es igual a cero se actualiza a estado COBRADO
            if (cuentaAdministradorSubsidio.getValorRealTransaccion().compareTo(BigDecimal.ZERO) == 0) {
                cuentaAdministradorSubsidio.setEstadoTransaccionSubsidio(EstadoTransaccionSubsidioEnum.COBRADO);
            } else {
                cuentaAdministradorSubsidio.setEstadoTransaccionSubsidio(EstadoTransaccionSubsidioEnum.APLICADO);
            }
            entityManagerCore.merge(cuentaAdministradorSubsidio);
        }
        */

        //UserDTO userDTO = new UserDTO();

        //Se crea una cadena con los medios de pago
        StringBuilder listaMediosPago = new StringBuilder();
        if (!lstMediosPago.isEmpty()) {
            int count = 0;
            for (String tipoMedioPago : lstMediosPago) {
                listaMediosPago.append(tipoMedioPago);
                if (count < lstMediosPago.size()) {
                    listaMediosPago.append(",");
                }
                count++;
            }
        }

        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            userDTO.setEmail("");
        }

        if (!abonosExitosos.isEmpty()) {
            /*Se actuaiza el estado de las cuentas con abonos exitosos a estado cobrado o aplicado para los ids de abonos exitosos*/
            entityManagerCore.createStoredProcedureQuery(NamedQueriesConstants.USP_PG_DISPERSAR_PAGOS_ESTADO_APLICADO)
                    .registerStoredProcedureParameter("sUsuario", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("sNumeroRadicacion", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("sEstadoTransaccion", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("listaMediosPago", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("listaAbonosExitosos", String.class, ParameterMode.IN)
                    .setParameter("sUsuario", userDTO.getEmail())
                    .setParameter("sNumeroRadicacion", numeroRadicacion)
                    .setParameter("sEstadoTransaccion", EstadoTransaccionSubsidioEnum.APLICADO.name())
                    .setParameter("listaMediosPago", listaMediosPago.length() == 0 ? "" : listaMediosPago.toString())
                    .setParameter("listaAbonosExitosos", listaAbonosExitosos.length() == 0 ? "" : listaAbonosExitosos.toString()).execute();
        } else {
            /*
             * Se actualiza el estado de las cuentas con abonos exitosos a estado cobrado o aplicado
             * se utiliza el SP que no recibe los ids de abonos exitosos
             */
            entityManagerCore.createStoredProcedureQuery(NamedQueriesConstants.USP_PG_DISPERSAR_PAGOS_ESTADO_ENVIADO)
                    .registerStoredProcedureParameter("sUsuario", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("sNumeroRadicacion", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("sEstadoTransaccion", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("listaMediosPago", String.class, ParameterMode.IN)
                    .setParameter("sUsuario", userDTO.getEmail())
                    .setParameter("sNumeroRadicacion", numeroRadicacion)
                    .setParameter("sEstadoTransaccion", EstadoTransaccionSubsidioEnum.APLICADO.name())
                    .setParameter("listaMediosPago", listaMediosPago.length() == 0 ? "" : listaMediosPago.toString()).execute();
        }


        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Metodo
     *
     * @param estadoTransaccion
     * @param cuentasAdministradorSubsidio
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void actualizarDispersionEstadoEnviado(EstadoTransaccionSubsidioEnum estadoTransaccion, List<CuentaAdministradorSubsidio> cuentasAdministradorSubsidio) {
        String firmaMetodo = "ConsultasModeloCore.actualizarDispersionEstadoEnviado(EstadoTransaccionSubsidioEnum,List<CuentaAdministradorSubsidio>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        for (CuentaAdministradorSubsidio cuentaAdministradorSubsidio : cuentasAdministradorSubsidio) {
            cuentaAdministradorSubsidio.setEstadoTransaccionSubsidio(estadoTransaccion);
            entityManagerCore.merge(cuentaAdministradorSubsidio);
        }
    }

    /**
     * <b>Descripción:</b>Método que permite obtener las cuentas de administrador dado un número de radicación asociado a una solicitud de
     * liquidación y una lista de medios de pago
     *
     * @param numeroRadicacion <code>String</code>
     *                         Valor del número de radicación
     * @param mediosDePago     <code>List<TipoMedioDePagoEnum></code>
     *                         Lista de medios de pago
     * @return Cuentas de administrador de subsidio
     * @author <a>Roy López Cardona</a>
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<CuentaAdministradorSubsidio> consultarCuentasAdministradorPorRadicacionMediosDePago(String numeroRadicacion,
                                                                                                     List<TipoMedioDePagoEnum> mediosDePago) {
        String firmaMetodo = "ConsultasModeloCore.consultarCuentasAdministradorPorRadicacionMediosDePago(String,List<TipoMedioDePagoEnum>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<CuentaAdministradorSubsidio> cuentasAdministradorSubsidio = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTAS_ADMINISTRADOR_RADICACION_MEDIOS_PAGO,
                        CuentaAdministradorSubsidio.class)
                .setParameter("numeroRadicacion", numeroRadicacion).setParameter("mediosDePago", mediosDePago).getResultList();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return cuentasAdministradorSubsidio;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarCuentasAdministradorMedioTarjeta(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasAdministradorMedioTarjeta(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarCuentasAdministradorMedioTarjeta(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            /*List<CuentaAdministradorSubsidioDTO> cuentasMedioTarjeta = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTAS_ADMINISTRADOR_MEDIO_TARJETA,
                            CuentaAdministradorSubsidioDTO.class)
                    .setParameter("numeroRadicacion", numeroRadicacion).getResultList();*/

            List<Object[]> cuentasMedioTarjetaObj = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTAS_ADMINISTRADOR_MEDIO_TARJETA)
                    .setParameter("numeroRadicacion", numeroRadicacion).getResultList();

            List<CuentaAdministradorSubsidioDTO> cuentasMedioTarjeta = new ArrayList<>();

            for (Object[] object : cuentasMedioTarjetaObj) {
                CuentaAdministradorSubsidioDTO cuenta = new CuentaAdministradorSubsidioDTO((CuentaAdministradorSubsidio) object[0], (Persona) object[1], object[2].toString());
                cuentasMedioTarjeta.add(cuenta);
            }

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return cuentasMedioTarjeta;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }


    /**
     * <b>Descripción:</b>Método que permite obtener las cuentas de administrador dado un número de radicación asociado a una solicitud de
     * liquidación y una lista de medios de pago
     *
     * @param numeroRadicacion <code>String</code>
     *                         Valor del número de radicación
     * @param mediosDePago     <code>List<TipoMedioDePagoEnum></code>
     *                         Lista de medios de pago
     * @return Cuentas de administrador de subsidio
     * @author <a>Roy López Cardona</a>
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<CuentaAdministradorSubsidio> consultarCuentasAdministradorRadicacionMediosDePagoId(String numeroRadicacion,
                                                                                                    List<Long> identificadoresCuentas) {
        String firmaMetodo = "ConsultasModeloCore.consultarCuentasAdministradorRadicacionMediosDePagoId(String,List<TipoMedioDePagoEnum>,List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            List<CuentaAdministradorSubsidio> cuentasAdministradorSubsidio = new ArrayList<CuentaAdministradorSubsidio>();
            List<TipoMedioDePagoEnum> mediosDePago = new ArrayList<TipoMedioDePagoEnum>();
            mediosDePago.add(TipoMedioDePagoEnum.EFECTIVO);

            if (identificadoresCuentas.isEmpty()) {
                cuentasAdministradorSubsidio = consultarCuentasAdministradorPorRadicacionMediosDePago(numeroRadicacion, mediosDePago);
            } else {
                mediosDePago.add(TipoMedioDePagoEnum.TARJETA);
                cuentasAdministradorSubsidio = entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTAS_ADMINISTRADOR_RADICACION_MEDIOS_PAGO_IDS,
                                CuentaAdministradorSubsidio.class)
                        .setParameter("numeroRadicacion", numeroRadicacion).setParameter("mediosDePago", mediosDePago)
                        .setParameter("identificadoresAbonesExitosos", identificadoresCuentas).getResultList();
            }

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return cuentasAdministradorSubsidio;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#dispersarPagosEstadoEnviadoOrigenAnulacion(java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void dispersarPagosEstadoEnviadoOrigenAnulacion(List<Long> identificadoresCuentas) {
        String firmaMetodo = "ConsultasModeloCore.consultarCuentasAdministradoresSubsidio(List<TipoMedioDePagoEnum>,List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            List<TipoMedioDePagoEnum> mediosDePago = new ArrayList<>();
            mediosDePago.add(TipoMedioDePagoEnum.EFECTIVO);
            mediosDePago.add(TipoMedioDePagoEnum.TARJETA);
            //mediosDePago.add(TipoMedioDePagoEnum.TRANSFERENCIA);

            List<CuentaAdministradorSubsidio> cuentasAdministradores = consultarCuentasAdministradoresSubsidio(identificadoresCuentas,
                    mediosDePago);

            for (CuentaAdministradorSubsidio cuentaAdministradorSubsidio : cuentasAdministradores) {
                if (cuentaAdministradorSubsidio.getValorRealTransaccion().compareTo(BigDecimal.valueOf(0)) > 0) {
                    cuentaAdministradorSubsidio.setEstadoTransaccionSubsidio(EstadoTransaccionSubsidioEnum.ENVIADO);
                    entityManagerCore.merge(cuentaAdministradorSubsidio);
                }
            }

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * <b>Descripción:</b>Método que permite consultar las cuentas de administradores de subsidio asociadas a la lista de identificadores
     * parametrizados
     *
     * @param identificadoresCuentas <code>List<Long></code>
     *                               Lista de identificadores parametrizados
     * @return Cuentas de administrador de subsidio
     * @author <a>Roy López Cardona</a>
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<CuentaAdministradorSubsidio> consultarCuentasAdministradoresSubsidio(List<Long> identificadoresCuentas,
                                                                                      List<TipoMedioDePagoEnum> mediosDePago) {
        String firmaMetodo = "ConsultasModeloCore.consultarCuentasAdministradoresSubsidio(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            List<CuentaAdministradorSubsidio> cuentasAdministradoresDTO = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTAS_ADMINISTRADOR_IDS, CuentaAdministradorSubsidio.class)
                    .setParameter("identificadoresCuentas", identificadoresCuentas).setParameter("mediosDePago", mediosDePago)
                    .getResultList();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return cuentasAdministradoresDTO;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarCuentasAdministradorMedioTarjetaOrigenAnulacion(java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasAdministradorMedioTarjetaOrigenAnulacion(
            List<Long> identificadoresCuentas) {
        String firmaMetodo = "ConsultasModeloCore.consultarCuentasAdministradorMedioTarjetaOrigenAnulacion(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            List<CuentaAdministradorSubsidioDTO> cuentasMedioTarjeta = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTAS_ADMINISTRADOR_MEDIO_TARJETA_IDS,
                            CuentaAdministradorSubsidioDTO.class)
                    .setParameter("identificadoresCuentas", identificadoresCuentas).getResultList();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return cuentasMedioTarjeta;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#dispersarPagosEstadoAplicadoOrigenAnulacion(java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void dispersarPagosEstadoAplicadoOrigenAnulacion(List<Long> identificadoresCuentas) {
        String firmaMetodo = "ConsultasModeloCore.dispersarPagosEstadoAplicadoOrigenAnulacion(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            List<TipoMedioDePagoEnum> mediosDePago = new ArrayList<>();
            mediosDePago.add(TipoMedioDePagoEnum.EFECTIVO);
            mediosDePago.add(TipoMedioDePagoEnum.TARJETA);

            List<CuentaAdministradorSubsidio> cuentasAdministrador = consultarCuentasAdministradoresSubsidio(identificadoresCuentas,
                    mediosDePago);

            for (CuentaAdministradorSubsidio cuentaAdministradorSubsidio : cuentasAdministrador) {
                if (cuentaAdministradorSubsidio.getValorRealTransaccion().compareTo(BigDecimal.valueOf(0)) > 0) {
                    cuentaAdministradorSubsidio.setEstadoTransaccionSubsidio(EstadoTransaccionSubsidioEnum.APLICADO);
                    entityManagerCore.merge(cuentaAdministradorSubsidio);
                }
            }

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#actualizarSolicitudAnulacionSubsidioCobrado(com.asopagos.entidades.subsidiomonetario.pagos.SolicitudAnulacionSubsidioCobrado)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Boolean actualizarSolicitudAnulacionSubsidioCobrado(SolicitudAnulacionSubsidioCobrado solicitudAnulacionSubsidioCobrado) {
        String firmaMetodo = "ConsultasModeloCore.actualizarSolicitudAnulacionSubsidioCobrado( SolicitudAnulacionSubsidioCobrado )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Boolean success = Boolean.FALSE;
        try {
            entityManagerCore.merge(solicitudAnulacionSubsidioCobrado);
            success = Boolean.TRUE;
        } catch (Exception e) {
            logger.error(firmaMetodo + ": No se logro actualizar el registro de la solicitud de anulación de subsidio cobrado", e);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return success;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarSolicitudAnulacionSubsidioCobrado(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudAnulacionSubsidioCobrado consultarSolicitudAnulacionSubsidioCobrado(Long idSolicitudAnulacionSubsidioCobrado) {
        String firmaMetodo = "ConsultasModeloCore.consultarSolicitudAnulacionSubsidioCobrado( Long )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        SolicitudAnulacionSubsidioCobrado solicitudAnulacionSubsidioCobrado = null;
        try {
            solicitudAnulacionSubsidioCobrado = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_ANULACION_SUBSIDIO_COBRADO_ID,
                            SolicitudAnulacionSubsidioCobrado.class)
                    .setParameter("idSolicitudAnulacionSubsidioCobrado", idSolicitudAnulacionSubsidioCobrado).getSingleResult();
        } catch (NoResultException e) {
            logger.warn(firmaMetodo + ": No se logro obtener resultado para el idSolicitudAnulacionSubsidioCobrado suministrado: \""
                    + idSolicitudAnulacionSubsidioCobrado + "\"", e);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return solicitudAnulacionSubsidioCobrado;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarDetalleSolicitudAnulacionSubsidioCobrado(com.asopagos.subsidiomonetario.pagos.dto.SolicitudAnulacionSubsidioCobradoDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AbonosSolicitudAnulacionSubsidioCobradoDTO> consultarDetalleSolicitudAnulacionSubsidioCobrado(
            SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobradoDTO) {
        String firmaMetodo = "ConsultasModeloCore.consultarDetalleSolicitudAnulacionSubsidioCobrado( SolicitudAnulacionSubsidioCobradoDTO )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<AbonosSolicitudAnulacionSubsidioCobradoDTO> lstAbonosSolicitudAnulacionSubsidioCobradoDTO = null;
        List<Object[]> lstTransaccionesAbonoCobradosResult = null;

        try {
            lstTransaccionesAbonoCobradosResult = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_TRANSACCIONES_ABONO_COBRADOS_POR_NUMERO_RADICADO)
                    .setParameter("numeroRadicadoSolicitud", solicitudAnulacionSubsidioCobradoDTO.getNumeroRadicado()).getResultList();

            lstAbonosSolicitudAnulacionSubsidioCobradoDTO = setDTOtoNativeQueryResult(lstTransaccionesAbonoCobradosResult);

        } catch (NoResultException e) {
            logger.warn(firmaMetodo + ": No se logro obtener resultados para la los criterios de busqueda", e);
        } catch (Exception e) {
            logger.error(firmaMetodo + ": Se presento un error al consultar el detalle de la solicitud de anulación de subsidio cobrado",
                    e);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);

        return lstAbonosSolicitudAnulacionSubsidioCobradoDTO;
    }

    /**
     * <b>Descripción:</b>Método encargado de arrmar la consulta de los abonos y sus los detalles de subsidio Asignado
     * <b>Módulo:</b> Asopagos - HU-31-227<br/>
     *
     * @param lstTransaccionesAbonoCobradosResult <code>List<Object[]></code>
     *                                            Los datos respuesta de la consulta realizada
     * @return <code>List<AbonosSolicitudAnulacionSubsidioCobradoDTO></code>
     * representa el detalle de los subsidios asignados asociados por abono agrupador a una
     * solicitud de anulación de subsidio cobrado.
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
     */
    private List<AbonosSolicitudAnulacionSubsidioCobradoDTO> setDTOtoNativeQueryResult(List<Object[]> lstTransaccionesAbonoCobradosResult) {
        String firmaMetodo = "ConsultasModeloCore.setDTOtoNativeQueryResult( List<Object[]> )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<AbonosSolicitudAnulacionSubsidioCobradoDTO> lstAbonosSolicitudAnulacionSubsidioCobradoDTO = null;
        AbonosSolicitudAnulacionSubsidioCobradoDTO abonoSolicitudAnulacionSubsidioCobradoDTO = null;
        DetalleSubsidioAsignadoDTO detalleSubsidioAsignadoDTO = null;

        if (lstTransaccionesAbonoCobradosResult != null && !lstTransaccionesAbonoCobradosResult.isEmpty()) {
            lstAbonosSolicitudAnulacionSubsidioCobradoDTO = new ArrayList<>();
            for (Object[] arrTransaccionAbonoCobrado : lstTransaccionesAbonoCobradosResult) {
                abonoSolicitudAnulacionSubsidioCobradoDTO = new AbonosSolicitudAnulacionSubsidioCobradoDTO();
                abonoSolicitudAnulacionSubsidioCobradoDTO.setIdCuentaAdministradorSubsidio(
                        (arrTransaccionAbonoCobrado[0] != null) ? Long.parseLong(arrTransaccionAbonoCobrado[0].toString()) : null);

                detalleSubsidioAsignadoDTO = new DetalleSubsidioAsignadoDTO();
                Date periodo = null;
                if (arrTransaccionAbonoCobrado[1] != null) {
                    Calendar periodoLiquidado = Calendar.getInstance();
                    periodoLiquidado.setTime((Date) arrTransaccionAbonoCobrado[1]);
                    periodo = CalendarUtils.formatearFechaSinHora(periodoLiquidado).getTime();
                    detalleSubsidioAsignadoDTO.setPeriodoLiquidado(periodo);
                }

                detalleSubsidioAsignadoDTO.setIdSolicitudLiquidacionSubsidio(
                        (arrTransaccionAbonoCobrado[2] != null) ? Long.parseLong(arrTransaccionAbonoCobrado[2].toString()) : null);

                detalleSubsidioAsignadoDTO
                        .setFechaHoraCreacion((arrTransaccionAbonoCobrado[3] != null) ? (Date) arrTransaccionAbonoCobrado[3] : null);

                EmpleadorModeloDTO empleadorModeloDTO = new EmpleadorModeloDTO();
                empleadorModeloDTO.setIdEmpleador(
                        (arrTransaccionAbonoCobrado[4] != null) ? Long.parseLong(arrTransaccionAbonoCobrado[4].toString()) : null);
                empleadorModeloDTO.setTipoIdentificacion((arrTransaccionAbonoCobrado[5] != null)
                        ? TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(arrTransaccionAbonoCobrado[5].toString()) : null);
                empleadorModeloDTO
                        .setNumeroIdentificacion((arrTransaccionAbonoCobrado[5] != null) ? arrTransaccionAbonoCobrado[6].toString() : null);
                empleadorModeloDTO
                        .setRazonSocial((arrTransaccionAbonoCobrado[7] != null) ? arrTransaccionAbonoCobrado[7].toString() : null);
                detalleSubsidioAsignadoDTO.setEmpleador(empleadorModeloDTO);

                AfiliadoModeloDTO afiliadoModeloDTO = new AfiliadoModeloDTO();
                afiliadoModeloDTO.setIdAfiliado(
                        (arrTransaccionAbonoCobrado[8] != null) ? Long.parseLong(arrTransaccionAbonoCobrado[8].toString()) : null);
                afiliadoModeloDTO.setTipoIdentificacion((arrTransaccionAbonoCobrado[9] != null)
                        ? TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(arrTransaccionAbonoCobrado[9].toString()) : null);
                afiliadoModeloDTO.setNumeroIdentificacion(
                        (arrTransaccionAbonoCobrado[10] != null) ? arrTransaccionAbonoCobrado[10].toString() : null);
                afiliadoModeloDTO
                        .setPrimerNombre((arrTransaccionAbonoCobrado[11] != null) ? arrTransaccionAbonoCobrado[11].toString() : null);
                afiliadoModeloDTO
                        .setSegundoNombre((arrTransaccionAbonoCobrado[12] != null) ? arrTransaccionAbonoCobrado[12].toString() : null);
                afiliadoModeloDTO
                        .setPrimerApellido((arrTransaccionAbonoCobrado[13] != null) ? arrTransaccionAbonoCobrado[13].toString() : null);
                afiliadoModeloDTO
                        .setSegundoApellido((arrTransaccionAbonoCobrado[14] != null) ? arrTransaccionAbonoCobrado[14].toString() : null);
                detalleSubsidioAsignadoDTO.setAfiliadoPrincipal(afiliadoModeloDTO);

                detalleSubsidioAsignadoDTO.setIdBeneficiarioDetalle(
                        (arrTransaccionAbonoCobrado[15] != null) ? Long.parseLong(arrTransaccionAbonoCobrado[15].toString()) : null);

                PersonaModeloDTO beneficiarioPersonaModeloDTO = new PersonaModeloDTO();
                beneficiarioPersonaModeloDTO.setTipoIdentificacion((arrTransaccionAbonoCobrado[16] != null)
                        ? TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(arrTransaccionAbonoCobrado[16].toString()) : null);
                beneficiarioPersonaModeloDTO.setNumeroIdentificacion(
                        (arrTransaccionAbonoCobrado[17] != null) ? arrTransaccionAbonoCobrado[17].toString() : null);
                beneficiarioPersonaModeloDTO
                        .setPrimerNombre((arrTransaccionAbonoCobrado[18] != null) ? arrTransaccionAbonoCobrado[18].toString() : null);
                beneficiarioPersonaModeloDTO
                        .setSegundoNombre((arrTransaccionAbonoCobrado[19] != null) ? arrTransaccionAbonoCobrado[19].toString() : null);
                beneficiarioPersonaModeloDTO
                        .setPrimerApellido((arrTransaccionAbonoCobrado[20] != null) ? arrTransaccionAbonoCobrado[20].toString() : null);
                beneficiarioPersonaModeloDTO
                        .setSegundoApellido((arrTransaccionAbonoCobrado[21] != null) ? arrTransaccionAbonoCobrado[21].toString() : null);
                detalleSubsidioAsignadoDTO.setBeneficiario(beneficiarioPersonaModeloDTO);

                detalleSubsidioAsignadoDTO.setIdAdministradorSubsidio(
                        (arrTransaccionAbonoCobrado[22] != null) ? Long.parseLong(arrTransaccionAbonoCobrado[22].toString()) : null);

                PersonaModeloDTO admonSubsidioPersonaModeloDTO = new PersonaModeloDTO();
                admonSubsidioPersonaModeloDTO.setTipoIdentificacion((arrTransaccionAbonoCobrado[23] != null)
                        ? TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(arrTransaccionAbonoCobrado[23].toString()) : null);
                admonSubsidioPersonaModeloDTO.setNumeroIdentificacion(
                        (arrTransaccionAbonoCobrado[24] != null) ? arrTransaccionAbonoCobrado[24].toString() : null);
                admonSubsidioPersonaModeloDTO
                        .setPrimerNombre((arrTransaccionAbonoCobrado[25] != null) ? arrTransaccionAbonoCobrado[25].toString() : null);
                admonSubsidioPersonaModeloDTO
                        .setSegundoNombre((arrTransaccionAbonoCobrado[26] != null) ? arrTransaccionAbonoCobrado[26].toString() : null);
                admonSubsidioPersonaModeloDTO
                        .setPrimerApellido((arrTransaccionAbonoCobrado[27] != null) ? arrTransaccionAbonoCobrado[27].toString() : null);
                admonSubsidioPersonaModeloDTO
                        .setSegundoApellido((arrTransaccionAbonoCobrado[28] != null) ? arrTransaccionAbonoCobrado[28].toString() : null);
                detalleSubsidioAsignadoDTO.setAdministradorSubsidio(admonSubsidioPersonaModeloDTO);

                detalleSubsidioAsignadoDTO.setIdDetalleSubsidioAsignado(
                        (arrTransaccionAbonoCobrado[45] != null) ? Long.parseLong(arrTransaccionAbonoCobrado[45].toString()) : null);

                //se establece de codigo de grupo familiar en el campo identificadorGrupoFamiliar
                detalleSubsidioAsignadoDTO.setIdGrupoFamiliar(
                        (arrTransaccionAbonoCobrado[46] != null) ? Long.parseLong(arrTransaccionAbonoCobrado[46].toString()) : null);

                abonoSolicitudAnulacionSubsidioCobradoDTO.setDetalleSubsidioAsignadoDTO(detalleSubsidioAsignadoDTO);

                abonoSolicitudAnulacionSubsidioCobradoDTO.setIdMedioDePago(
                        (arrTransaccionAbonoCobrado[29] != null) ? Long.parseLong(arrTransaccionAbonoCobrado[29].toString()) : null);
                abonoSolicitudAnulacionSubsidioCobradoDTO.setMedioDePago((arrTransaccionAbonoCobrado[30] != null)
                        ? TipoMedioDePagoEnum.obtenerTipoMedioDePagoEnum(arrTransaccionAbonoCobrado[30].toString()) : null);
                abonoSolicitudAnulacionSubsidioCobradoDTO.setNumeroTarjetaAdminSubsidio(
                        (arrTransaccionAbonoCobrado[31] != null) ? arrTransaccionAbonoCobrado[31].toString() : null);
                abonoSolicitudAnulacionSubsidioCobradoDTO.setCodigoBancoAdminSubsidio(
                        (arrTransaccionAbonoCobrado[32] != null) ? arrTransaccionAbonoCobrado[32].toString() : null);
                abonoSolicitudAnulacionSubsidioCobradoDTO.setNombreBancoAdminSubsidio(
                        (arrTransaccionAbonoCobrado[33] != null) ? arrTransaccionAbonoCobrado[33].toString() : null);
                abonoSolicitudAnulacionSubsidioCobradoDTO.setTipoCuentaAdminSubsidio((arrTransaccionAbonoCobrado[34] != null)
                        ? TipoCuentaEnum.obtenerTipoCuentaEnum(arrTransaccionAbonoCobrado[34].toString()) : null);
                abonoSolicitudAnulacionSubsidioCobradoDTO.setNumeroCuentaAdminSubsidio(
                        (arrTransaccionAbonoCobrado[35] != null) ? arrTransaccionAbonoCobrado[35].toString() : null);
                abonoSolicitudAnulacionSubsidioCobradoDTO
                        .setTipoIdentificacionTitularCuentaAdminSubsidio((arrTransaccionAbonoCobrado[36] != null)
                                ? TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(arrTransaccionAbonoCobrado[36].toString()) : null);

                abonoSolicitudAnulacionSubsidioCobradoDTO.setNumeroIdentificacionTitularCuentaAdminSubsidio(
                        (arrTransaccionAbonoCobrado[37] != null) ? arrTransaccionAbonoCobrado[37].toString() : null);
                abonoSolicitudAnulacionSubsidioCobradoDTO.setNombreTitularCuentaAdminSubsidio(
                        (arrTransaccionAbonoCobrado[38] != null) ? arrTransaccionAbonoCobrado[38].toString() : null);
                abonoSolicitudAnulacionSubsidioCobradoDTO
                        .setFechaHoraTransaccion((arrTransaccionAbonoCobrado[39] != null) ? (Date) arrTransaccionAbonoCobrado[39] : null);
                abonoSolicitudAnulacionSubsidioCobradoDTO.setUsuarioCreacionRegistro(
                        (arrTransaccionAbonoCobrado[40] != null) ? arrTransaccionAbonoCobrado[40].toString() : null);
                abonoSolicitudAnulacionSubsidioCobradoDTO.setValorOriginalTransaccion(
                        (arrTransaccionAbonoCobrado[41] != null) ? new BigDecimal(arrTransaccionAbonoCobrado[41].toString()) : null);
                abonoSolicitudAnulacionSubsidioCobradoDTO.setValorRealTransaccion(
                        (arrTransaccionAbonoCobrado[42] != null) ? new BigDecimal(arrTransaccionAbonoCobrado[42].toString()) : null);

                abonoSolicitudAnulacionSubsidioCobradoDTO
                        .setDetalleAnulacion((arrTransaccionAbonoCobrado[43] != null) ? arrTransaccionAbonoCobrado[43].toString() : null);
                abonoSolicitudAnulacionSubsidioCobradoDTO
                        .setMotivoAnulacion((arrTransaccionAbonoCobrado[44] != null) ? MotivoAnulacionSubsidioAsignadoEnum
                                .obtenerMotivoAnulacionSubsidioAsignadoEnum(arrTransaccionAbonoCobrado[44].toString()) : null);
                abonoSolicitudAnulacionSubsidioCobradoDTO.setIdSolicitudAnulacionSubsidio(
                        (arrTransaccionAbonoCobrado[47] != null) ? Long.parseLong(arrTransaccionAbonoCobrado[47].toString()) : null);
                abonoSolicitudAnulacionSubsidioCobradoDTO.setFechaHoraUltimaModificacion(
                        (arrTransaccionAbonoCobrado[48] != null) ? (Date) arrTransaccionAbonoCobrado[48] : null);
                detalleSubsidioAsignadoDTO.setFechaTransaccionRetiro(arrTransaccionAbonoCobrado[49] != null ? (Date) arrTransaccionAbonoCobrado[49] : null);
                lstAbonosSolicitudAnulacionSubsidioCobradoDTO.add(abonoSolicitudAnulacionSubsidioCobradoDTO);
            }
        } else {
            logger.warn(firmaMetodo + ": No se logro obtener resultados para la los criterios de busqueda");

        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstAbonosSolicitudAnulacionSubsidioCobradoDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#buscarMunicipioPorCodigo(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public String buscarMunicipioPorCodigo(String idMunicipio) {
        String firmaMetodo = "ConsultasModeloCore.buscarMunicipioPorCodigo( String )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        String codigoMunicipio = null;
        try {
            codigoMunicipio = entityManagerCore.createNamedQuery(NamedQueriesConstants.BUSCAR_CODIGO_MUNICIPIO_POR_ID)
                    .setParameter("idMunicipio", Short.valueOf(idMunicipio)).getSingleResult().toString();
            logger.debug("Finaliza operación buscarMunicipio(String)");

        } catch (NoResultException nor) {
            logger.warn(
                    firmaMetodo + ":: No se encontraron datos para el id municipio suministrador por parametro: \"" + idMunicipio + "\"");
        } catch (Exception e) {
            logger.error(firmaMetodo + ":: Ocurrió un error inesperado: ", e);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return codigoMunicipio != null ? codigoMunicipio : null;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#registrarSolicitudAnibol(com.asopagos.subsidiomonetario.pagos.dto.RegistroSolicitudAnibolModeloDTO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public Long registrarSolicitudAnibol(RegistroSolicitudAnibolModeloDTO reAnibolModeloDTO) {
        String firmaMetodo = "ConsultasModeloCore.registrarSolicitudAnibol( RegistroSolicitudAnibolModeloDTO )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RegistroSolicitudAnibol registroSolicitudAnibol = reAnibolModeloDTO.convertToEntity();
        entityManagerCore.persist(registroSolicitudAnibol);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);

        return registroSolicitudAnibol.getIdRegistroSolicitudAnibol();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#actualizarRegistroSolicitudAnibol(java.lang.Long, java.lang.String)
     */
    @Override
    public boolean actualizarRegistroSolicitudAnibol(Long idRegistroSolicitudAnibol, String parametrosOUT) {
        String firmaMetodo = "ConsultasModeloCore.actualizarRegistroSolicitudAnibol(Long,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        boolean getError = false;
        RegistroSolicitudAnibol registroSolicitudAnibol = buscarRegistroSolicitudAnibolPorId(idRegistroSolicitudAnibol);
        try {
            registroSolicitudAnibol.setParametrosOUT(parametrosOUT);
            entityManagerCore.merge(registroSolicitudAnibol);
            entityManagerCore.flush();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + "Error: Parametros de Salida de ANIBOL con longitud mayor a 500");
            getError = true;
        }
        return getError;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#actualizarRegistroSolicitudAnibolError(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void actualizarRegistroSolicitudAnibolError(Long idRegistroSolicitudAnibol) {
        RegistroSolicitudAnibol registroSolicitudAnibol = buscarRegistroSolicitudAnibolPorId(idRegistroSolicitudAnibol);
        registroSolicitudAnibol.setParametrosOUT("LONGITUD_DE_PARAMETROS_NO_PERMITIDA");
        try {
            entityManagerCore.merge(registroSolicitudAnibol);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER
                    + "Error: actualización parametros de salida del registro de solicitud Anibol cuando se pasa de la longitud");
        }
    }

    /**
     * Metodo encargado de buscar un registro de solicitud de ANIBOL por medio del identificador
     *
     * @param idRegistroSolicitudAnibol Identificador principal del registro de solicitud de ANIBOL
     * @return <code>RegistroSolicitudAnibol</code>
     * Registro de solicitud relacionado con el Identificador
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private RegistroSolicitudAnibol buscarRegistroSolicitudAnibolPorId(Long idRegistroSolicitudAnibol) {
        RegistroSolicitudAnibol registroSolicitudAnibol = null;

        try {
            registroSolicitudAnibol = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_SOLICITUD_ANIBOL_POR_ID,
                            RegistroSolicitudAnibol.class)
                    .setParameter("idRegistroSolicitud", idRegistroSolicitudAnibol).getSingleResult();

        } catch (NoResultException e1) {
            logger.error("No se encontro el registro de solicitud de ANIBOL", e1);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        }
        return registroSolicitudAnibol;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public RegistroSolicitudAnibol buscarRegistroSolicitudAnibolPorIdProceso(String idProceso) {
        return entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_SOLICITUD_ANIBOL_POR_ID_PROCESO,
                        RegistroSolicitudAnibol.class)
                .setParameter("idProceso", idProceso).getSingleResult();
    }

    /**
     * Metodo que permite buscar un convenio del tercero pagador a partir del nombre.
     *
     * @param nombreConvenio <code>String</code>
     *                       Nombre del convenio del tercero pagador por el cual se va a buscar.
     * @return <code>ConvenioTercerPagadorDTO</code>
     * Si existe un convenio con el mismo nombre, retorna el registro del convenio del tercero pagador;
     * de lo contrario, retorna un null.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ConvenioTercerPagadorDTO consultarConvenioTerceroPagadorPorNombrePagos(String nombreTerceroPagador) {
        String firmaMetodo = "ConsultasModeloCore.consultarConvenioTerceroPagadorPorNombre(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ConvenioTercerPagadorDTO convenioTercerPagadorDTO = null;

        try {
            convenioTercerPagadorDTO = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONVENIO_TERCERO_PAGADOR_POR_NOMBRE_GENESYS,
                            ConvenioTercerPagadorDTO.class)
                    .setParameter("nombreUsuarioGenesys", nombreTerceroPagador).getSingleResult();

        } catch (NoResultException e1) {
            logger.error("No se encontro convenio del tercero pagador con ese nombre");
            convenioTercerPagadorDTO = null;
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return convenioTercerPagadorDTO;
    }

    /**
     * Metodo que permite buscar un convenio del tercero pagador a partir del nombre.
     *
     * @param nombreConvenio <code>String</code>
     *                       Nombre del convenio del tercero pagador por el cual se va a buscar.
     * @return <code>ConvenioTercerPagadorDTO</code>
     * Si existe un convenio con el mismo nombre, retorna el registro del convenio del tercero pagador;
     * de lo contrario, retorna un null.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private ConvenioTercerPagadorDTO consultarConvenioTerceroPagadorPorNombre(String nombreConvenio) {
        String firmaMetodo = "ConsultasModeloCore.consultarConvenioTerceroPagadorPorNombre(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ConvenioTercerPagadorDTO convenioTercerPagadorDTO = null;

        try {
            convenioTercerPagadorDTO = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONVENIO_TERCERO_PAGADOR_POR_NOMBRE,
                            ConvenioTercerPagadorDTO.class)
                    .setParameter("nombreConvenio", nombreConvenio).getSingleResult();

        } catch (NoResultException e1) {
            logger.error("No se encontro convenio del tercero pagador con ese nombre");
            convenioTercerPagadorDTO = null;
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return convenioTercerPagadorDTO;
    }


    /**
     * Metodo que obtiene los datos faltantes del detalle.
     *
     * @param detalles
     * @return lista detalles de subsidios asignados
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void obtenerDatosFaltantesDetallesSubsidioMonetarioPorIdDetalle(
            List<DetalleSubsidioAsignadoDTO> detalles) {

        List<Long> lstIdDetalles = detalles.stream().map(e -> e.getIdDetalleSubsidioAsignado()).collect(Collectors.toList());
        List<Object[]> listDetalles = null;

        try {
            listDetalles = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_EMPLEADOR_ADMIN_BENEFICIARIO_AFILIADO_DETALLES_SUBSIDIO)
                    .setParameter("lstIdDetalles", lstIdDetalles).getResultList();
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        for (Object[] result : listDetalles) {

            PersonaModeloDTO personaBen = new PersonaModeloDTO();
            PersonaModeloDTO personaAdmin = new PersonaModeloDTO();
            EmpleadorModeloDTO empleador = new EmpleadorModeloDTO();
            AfiliadoModeloDTO afiliado = new AfiliadoModeloDTO();
            detalles.stream().forEach(detalle -> {
                if (detalle.getIdDetalleSubsidioAsignado() == Long.parseLong(result[0].toString())) {

                    //se adiciona el nombre del empleador
                    if (result[1] != null) {
                        empleador.setRazonSocial(result[1].toString());
                        detalle.setEmpleador(empleador);
                    }
                    //se adiciona el nombre del afiliado
                    if (result[2] != null) {
                        afiliado.setRazonSocial(result[2].toString());
                        detalle.setAfiliadoPrincipal(afiliado);
                    }
                    //se adiciona el nombre del beneficiario
                    if (result[3] != null) {
                        personaBen.setRazonSocial(result[3].toString());
                        detalle.setBeneficiario(personaBen);
                    }
                    //se adiciona el nombre del administrador
                    if (result[4] != null) {
                        personaAdmin.setRazonSocial(result[4].toString());
                        detalle.setAdministradorSubsidio(personaAdmin);
                    }
                    if (result[5] != null) {
                        detalle.setNumeroGrupoFamilarRelacionador(Short.parseShort(result[5].toString()));
                    }
                    //nombre del tipo de descuento (nombre entidad de descuento)
                    if (result[6] != null)
                        detalle.setNombreTipoDescuento(result[6].toString());
                    //fecha asociada a la liquidación
                    if (result[7] != null)
                        detalle.setFechaLiquidacionAsociada((Date) result[7]);

                }
            });
        }

    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarDetallesProgramadosPorIdCondicionesBeneficiarios(java.util.List)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    @Override
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesProgramadosPorIdCondicionesBeneficiarios(
            List<Long> lstIdsCondicionesBeneficiarios) {
        String firmaMetodo = "ConsultasModeloCore.consultarDetallesProgramadosPorIdCondicionesBeneficiarios(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<DetalleSubsidioAsignadoDTO> lstDetallesProgramados = new ArrayList<>();
        List<Object[]> lstProgramadosResult = null;
        try {
            lstProgramadosResult = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLES_PROGRAMADOS_POR_IDS_CONDICIONES_BENEFICIARIOS)
                    .setParameter("lstCondicionesBeneficiarios", lstIdsCondicionesBeneficiarios).getResultList();

            if (!lstProgramadosResult.isEmpty()) {

                for (Object[] result : lstProgramadosResult) {

                    if (result != null) {

                        DetalleSubsidioAsignadoDTO detalleSubsidioAsignadoDTO = new DetalleSubsidioAsignadoDTO();
                        detalleSubsidioAsignadoDTO.setIdDetalleSubsidioAsignado(Long.parseLong(result[0].toString()));
                        detalleSubsidioAsignadoDTO.setUsuarioCreador(result[1].toString());
                        detalleSubsidioAsignadoDTO.setFechaHoraCreacion((Date) result[2]);
                        detalleSubsidioAsignadoDTO.setEstado(EstadoSubsidioAsignadoEnum.valueOf(result[3].toString()));
                        detalleSubsidioAsignadoDTO
                                .setOrigenRegistroSubsidio(OrigenRegistroSubsidioAsignadoEnum.valueOf(result[6].toString()));
                        detalleSubsidioAsignadoDTO.setTipoLiquidacionSubsidio(TipoLiquidacionSubsidioEnum.valueOf(result[7].toString()));
                        detalleSubsidioAsignadoDTO.setTipoCuotaSubsidio(TipoCuotaSubsidioEnum.valueOf(result[8].toString()));
                        detalleSubsidioAsignadoDTO.setValorSubsidioMonetario(BigDecimal.valueOf(Double.parseDouble(result[9].toString())));
                        detalleSubsidioAsignadoDTO.setValorDescuento(BigDecimal.valueOf(Double.parseDouble(result[10].toString())));
                        detalleSubsidioAsignadoDTO.setValorOriginalAbonado(BigDecimal.valueOf(Double.parseDouble(result[11].toString())));
                        detalleSubsidioAsignadoDTO.setValorTotal(BigDecimal.valueOf(Double.parseDouble(result[12].toString())));
                        detalleSubsidioAsignadoDTO.setIdSolicitudLiquidacionSubsidio(Long.parseLong(result[19].toString()));
                        detalleSubsidioAsignadoDTO.setIdEmpleador(Long.parseLong(result[20].toString()));
                        detalleSubsidioAsignadoDTO.setIdAfiliadoPrincipal(Long.parseLong(result[21].toString()));
                        detalleSubsidioAsignadoDTO.setIdGrupoFamiliar(Long.parseLong(result[22].toString()));
                        detalleSubsidioAsignadoDTO.setIdAdministradorSubsidio(Long.parseLong(result[23].toString()));
                        detalleSubsidioAsignadoDTO.setIdCuentaAdministradorSubsidio(Long.parseLong(result[25].toString()));
                        detalleSubsidioAsignadoDTO.setIdBeneficiarioDetalle(Long.parseLong(result[26].toString()));
                        detalleSubsidioAsignadoDTO.setPeriodoLiquidado((Date) result[27]);
                        detalleSubsidioAsignadoDTO.setIdResultadoValidacionLiquidacion(Long.parseLong(result[28].toString()));
                        detalleSubsidioAsignadoDTO.setIdCondicionPersonaBeneficiario(Long.parseLong(result[29].toString()));
                        lstDetallesProgramados.add(detalleSubsidioAsignadoDTO);
                    }
                }
            }

        } catch (NoResultException e) {
            logger.warn(firmaMetodo + ": No se logro obtener resultados para la los criterios de busqueda", e);
        } catch (Exception e) {
            logger.error(firmaMetodo + ": Se presento un error al consultar los detalles programados del administrador del subsidio",
                    e);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstDetallesProgramados.isEmpty() ? null : lstDetallesProgramados;
    }

    @Override
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesProgramadosPorIdCondicionesBeneficiariosYRadicado(
            List<Long> lstIdsCondicionesBeneficiarios, String numeroRadicado) {
        String firmaMetodo = "ConsultasModeloCore.consultarDetallesProgramadosPorIdCondicionesBeneficiarios(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<DetalleSubsidioAsignadoDTO> lstDetallesProgramados = new ArrayList<>();
        List<Object[]> lstProgramadosResult = null;
        try {
            lstProgramadosResult = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLES_PROGRAMADOS_POR_IDS_CONDICIONES_BENEFICIARIOS_Y_RADICADO)
                    .setParameter("lstCondicionesBeneficiarios", lstIdsCondicionesBeneficiarios)
                    .setParameter("numeroRadicado", numeroRadicado)
                    .getResultList();

            if (!lstProgramadosResult.isEmpty()) {

                for (Object[] result : lstProgramadosResult) {

                    if (result != null) {

                        DetalleSubsidioAsignadoDTO detalleSubsidioAsignadoDTO = new DetalleSubsidioAsignadoDTO();
                        detalleSubsidioAsignadoDTO.setIdDetalleSubsidioAsignado(Long.parseLong(result[0].toString()));
                        detalleSubsidioAsignadoDTO.setUsuarioCreador(result[1].toString());
                        detalleSubsidioAsignadoDTO.setFechaHoraCreacion((Date) result[2]);
                        detalleSubsidioAsignadoDTO.setEstado(EstadoSubsidioAsignadoEnum.valueOf(result[3].toString()));
                        detalleSubsidioAsignadoDTO
                                .setOrigenRegistroSubsidio(OrigenRegistroSubsidioAsignadoEnum.valueOf(result[6].toString()));
                        detalleSubsidioAsignadoDTO.setTipoLiquidacionSubsidio(TipoLiquidacionSubsidioEnum.valueOf(result[7].toString()));
                        detalleSubsidioAsignadoDTO.setTipoCuotaSubsidio(TipoCuotaSubsidioEnum.valueOf(result[8].toString()));
                        detalleSubsidioAsignadoDTO.setValorSubsidioMonetario(BigDecimal.valueOf(Double.parseDouble(result[9].toString())));
                        detalleSubsidioAsignadoDTO.setValorDescuento(BigDecimal.valueOf(Double.parseDouble(result[10].toString())));
                        detalleSubsidioAsignadoDTO.setValorOriginalAbonado(BigDecimal.valueOf(Double.parseDouble(result[11].toString())));
                        detalleSubsidioAsignadoDTO.setValorTotal(BigDecimal.valueOf(Double.parseDouble(result[12].toString())));
                        detalleSubsidioAsignadoDTO.setIdSolicitudLiquidacionSubsidio(Long.parseLong(result[19].toString()));
                        detalleSubsidioAsignadoDTO.setIdEmpleador(Long.parseLong(result[20].toString()));
                        detalleSubsidioAsignadoDTO.setIdAfiliadoPrincipal(Long.parseLong(result[21].toString()));
                        detalleSubsidioAsignadoDTO.setIdGrupoFamiliar(Long.parseLong(result[22].toString()));
                        detalleSubsidioAsignadoDTO.setIdAdministradorSubsidio(Long.parseLong(result[23].toString()));
                        detalleSubsidioAsignadoDTO.setIdCuentaAdministradorSubsidio(Long.parseLong(result[25].toString()));
                        detalleSubsidioAsignadoDTO.setIdBeneficiarioDetalle(Long.parseLong(result[26].toString()));
                        detalleSubsidioAsignadoDTO.setPeriodoLiquidado((Date) result[27]);
                        detalleSubsidioAsignadoDTO.setIdResultadoValidacionLiquidacion(Long.parseLong(result[28].toString()));
                        detalleSubsidioAsignadoDTO.setIdCondicionPersonaBeneficiario(Long.parseLong(result[29].toString()));
                        lstDetallesProgramados.add(detalleSubsidioAsignadoDTO);
                    }
                }
            }

        } catch (NoResultException e) {
            logger.warn(firmaMetodo + ": No se logro obtener resultados para la los criterios de busqueda", e);
        } catch (Exception e) {
            logger.error(firmaMetodo + ": Se presento un error al consultar los detalles programados del administrador del subsidio",
                    e);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstDetallesProgramados.isEmpty() ? null : lstDetallesProgramados;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#actualizarEstadoDetalleADerechoProgramado(java.util.List)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void actualizarEstadoDetalleADerechoProgramado(List<Long> lstDetallesProgramados) {
        String firmaMetodo = "ConsultasModeloCore.actualizarEstadoDetalleADerechoProgramado(List<DetalleSubsidioAsignadoDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {

            entityManagerCore.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ESTADOS_DETALLES_PROGRAMADOS)
                    .setParameter("idsDetallesProgramados", lstDetallesProgramados).executeUpdate();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarTransaccionesSubsidioPorResultadoLiquidacion(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<Object[]> consultarDetallesSubsidioPorResultadoLiquidacion(Long idResultadoValidacionLiquidacion) {
        String firmaServicio = "ConsultasModeloCore.consultarTransaccionesSubsidioPorResultadoLiquidacion(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        try {
            List<Object[]> lstIdsCuentas = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_CUENTA_SUBSIDIO_DTO_TRANSACCIONES_POR_RVL)
                    .setParameter("idResultadoValidacionLiquidacion", idResultadoValidacionLiquidacion).getResultList();

            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
            return lstIdsCuentas;
        } catch (Exception e) {
            logger.debug("Ocurrio un error inesperado " + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#obtenerPagosSubsidiosProgramados(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    @Override
    public List<PagoSubsidioProgramadoDTO> obtenerPagosSubsidiosProgramados(Long idAdminSubsidio, String numeroRadicacion) {
        String firmaServicio = "ConsultasModeloCore.obtenerPagosSubsidiosProgramados(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<PagoSubsidioProgramadoDTO> result = new ArrayList<>();
        List<Object[]> lstDetallesProgramados = null;

        //try {
        lstDetallesProgramados = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.OBTENER_PAGOS_SUBSIDIOS_PENDIENTES_POR_PROGRAMADOS)
                .setParameter("adminSubsidio", idAdminSubsidio)
                .setParameter("numeroRadicacion", numeroRadicacion).getResultList();

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

       /*} catch (Exception e) {
            logger.debug("Ocurrio un error inesperado " + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
*/
        if (!lstDetallesProgramados.isEmpty()) {

            for (Object[] item : lstDetallesProgramados) {
                PagoSubsidioProgramadoDTO programadoDTO = new PagoSubsidioProgramadoDTO();
                if (Boolean.parseBoolean(item[3].toString()))
                    programadoDTO.setEstado(EstadoSubsidioAsignadoEnum.DERECHO_ASIGNADO);
                else
                    programadoDTO.setEstado(EstadoSubsidioAsignadoEnum.PENDIENTE);
                programadoDTO.setPeriodoLiquidacion((Date) item[0]);
                programadoDTO.setFechaParametrizadaPago((Date) item[1]);
                programadoDTO.setFechaProgramadaPago((Date) item[2]);
                result.add(programadoDTO);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result.isEmpty() ? null : result;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#dispersarPagosEstadoEnviadoOrigenAnulacionTransferencia(java.util.List)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void dispersarPagosEstadoEnviadoOrigenAnulacionTransferencia(List<Long> identificadoresCuentas) {

        String firmaMetodo = "ConsultasModeloCore.dispersarPagosEstadoEnviadoOrigenAnulacionTransferencia(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<TipoMedioDePagoEnum> mediosDePago = new ArrayList<>();
        mediosDePago.add(TipoMedioDePagoEnum.TRANSFERENCIA);

        List<CuentaAdministradorSubsidio> cuentasAdministradores = consultarCuentasAdministradoresSubsidio(identificadoresCuentas,
                mediosDePago);

        for (CuentaAdministradorSubsidio cuentaAdministradorSubsidio : cuentasAdministradores) {
            if (cuentaAdministradorSubsidio.getValorRealTransaccion().compareTo(BigDecimal.valueOf(0)) > 0) {
                cuentaAdministradorSubsidio.setEstadoTransaccionSubsidio(EstadoTransaccionSubsidioEnum.ENVIADO);
                entityManagerCore.merge(cuentaAdministradorSubsidio);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarCuentasConFiltros(com.asopagos.subsidiomonetario.pagos.dto.TransaccionConsultadaDTO)
     */
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasConFiltros(TransaccionConsultadaDTO transaccionConsultada) {
        return consultarCuentasFiltros(transaccionConsultada);
    }

    /**
     * Metodo encargado de obtener los detalles de subsidios asignados de los
     * retiros y anulaciones
     *
     * @param idCuentaAdministradorSubsidio Identificador de la cuenta
     * @param tipoTransaccion               Tipo de transacción de la cuenta
     * @return Lista de detalles
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<DetalleSubsidioAsignadoDTO> consultarDetallesRetirosAnulacionCuenta(Long idCuentaAdministradorSubsidio,
                                                                                     TipoTransaccionSubsidioMonetarioEnum tipoTransaccion) {
        String firmaMetodo = "ConsultasModeloCore.consultarDetallesRetirosAnulacionCuenta(Long,TipoTransaccionSubsidioMonetarioEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<DetalleSubsidioAsignadoDTO> lstDetalles = null;

        switch (tipoTransaccion) {
            case RETIRO:

                try {
                    lstDetalles = entityManagerCore
                            .createNamedQuery(NamedQueriesConstants.OBTENER_DETALLES_RETIROS_CUENTAS, DetalleSubsidioAsignadoDTO.class)
                            .setParameter("idRetiro", idCuentaAdministradorSubsidio).getResultList();
                } catch (Exception e) {
                    logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                }

                break;

            case ANULACION:

                lstDetalles = consultarDetallesSubsidiosAsignadosAsociadosAbono(idCuentaAdministradorSubsidio);

                if (lstDetalles.isEmpty()) {
                    try {
                        lstDetalles = entityManagerCore
                                .createNamedQuery(NamedQueriesConstants.OBTENER_DETALLES_RETIROS_CUENTAS, DetalleSubsidioAsignadoDTO.class)
                                .setParameter("idRetiro", idCuentaAdministradorSubsidio).getResultList();
                    } catch (Exception e) {
                        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                        throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                    }
                }

                break;

            default:
                break;
        }

        if (lstDetalles == null || lstDetalles.isEmpty()) {
            try {
                lstDetalles = entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.OBTENER_DETALLES_RETIROS_CUENTAS_SEGUNDO_NIVEL, DetalleSubsidioAsignadoDTO.class)
                        .setParameter("idRetiro", idCuentaAdministradorSubsidio).getResultList();
            } catch (Exception e) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }
        }

        return (lstDetalles == null || lstDetalles.isEmpty()) ? null : lstDetalles;
    }


    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesPorCuenta(Long idCuentaAdministradorSubsidio) {
        String firmaMetodo = "ConsultasModeloCore.consultarDetallesPorCuenta(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<DetalleSubsidioAsignadoDTO> lstDetalles = null;

        try {
            lstDetalles = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.OBTENER_DETALLES_CUENTASADMIN_POR_ID,
                            DetalleSubsidioAsignadoDTO.class)
                    .setParameter("idCuentaAdmin", idCuentaAdministradorSubsidio).getResultList();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);

        return (lstDetalles == null || lstDetalles.isEmpty()) ? null : lstDetalles;
    }


    public List<CuentaAdministradorSubsidioDTO> consultarTransaccionesPorRVL(Long idRvl) {
        String firmaServicio = "ConsultasModeloCore.consultarTransacciones(TransaccionConsultada)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<CuentaAdministradorSubsidioDTO> listaCuentasAdminSubsidios = new ArrayList<>();
        List<Object[]> lstCuentas = null;

        List<Long> sqlHack = new ArrayList<Long>();
        sqlHack.add(0L);

        lstCuentas = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_ADMIN_SUBSIDIO_DTO_TRANSACCIONES_POR_RVL)
                .setParameter("idResultadoValidacionLiquidacion", idRvl)
                .getResultList();

        for (Object[] result : lstCuentas) {

            CuentaAdministradorSubsidioDTO abono = new CuentaAdministradorSubsidioDTO();
            abono.setIdCuentaAdministradorSubsidio(Long.valueOf(result[0].toString()));
            if (result[1] != null)
                abono.setFechaHoraCreacionRegistro((Date) result[1]);
            if (result[2] != null)
                abono.setUsuarioCreacionRegistro(result[2].toString());
            if (result[3] != null)
                abono.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.valueOf(result[3].toString()));
            if (result[4] != null)
                abono.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.valueOf(result[4].toString()));
            if (result[6] != null)
                abono.setOrigenTransaccion(OrigenTransaccionEnum.valueOf(result[6].toString()));
            if (result[7] != null)
                abono.setMedioDePago(TipoMedioDePagoEnum.valueOf(result[7].toString()));
            if (result[8] != null)
                abono.setNumeroTarjetaAdminSubsidio(result[8].toString());
            if (result[9] != null)
                abono.setCodigoBancoAdminSubsidio(result[9].toString());
            if (result[10] != null)
                abono.setNombreBancoAdminSubsidio(result[10].toString());
            if (result[11] != null)
                abono.setTipoCuentaAdminSubsidio(TipoCuentaEnum.valueOf(result[11].toString()));
            if (result[12] != null)
                abono.setNumeroCuentaAdminSubsidio(result[12].toString());
            if (result[13] != null)
                abono.setTipoIdentificacionTitularCuentaAdminSubsidio(TipoIdentificacionEnum.valueOf(result[13].toString()));
            if (result[14] != null)
                abono.setNumeroIdentificacionTitularCuentaAdminSubsidio(result[14].toString());
            if (result[15] != null)
                abono.setNombreTitularCuentaAdminSubsidio(result[15].toString());
            if (result[16] != null)
                abono.setFechaHoraTransaccion((Date) result[16]);
            if (result[17] != null)
                abono.setUsuarioTransaccionLiquidacion(result[17].toString());
            if (result[18] != null)
                abono.setValorOriginalTransaccion(BigDecimal.valueOf(Double.parseDouble(result[18].toString())));
            if (result[19] != null)
                abono.setValorRealTransaccion(BigDecimal.valueOf(Double.parseDouble(result[19].toString())));
            if (result[20] != null)
                abono.setIdTransaccionOriginal(Long.parseLong(result[20].toString()));
            if (result[21] != null)
                abono.setIdRemisionDatosTerceroPagador(result[21].toString());
            if (result[22] != null)
                abono.setIdTransaccionTerceroPagador(result[22].toString());
            if (result[23] != null)
                abono.setNombreTerceroPagador(result[23].toString());
            if (result[24] != null)
                abono.setIdCuentaAdminSubsidioRelacionado(Long.parseLong(result[24].toString()));
            if (result[25] != null)
                abono.setFechaHoraUltimaModificacion((Date) result[25]);
            if (result[26] != null)
                abono.setUsuarioUltimaModificacion(result[26].toString());
            if (result[33] != null)
                abono.setNombreSitioPago(result[33].toString());
            if (result[34] != null)
                abono.setNombreSitioCobro(result[34].toString());
            if (result[35] != null)
                abono.setNombresApellidosAdminSubsidio(result[35].toString());
            if (result[36] != null)
                abono.setNombrePersonaAutorizada(result[36].toString());

            if ((abono.getIdTransaccionTerceroPagador() != null) && (abono.getIdRemisionDatosTerceroPagador() == null)) {
                abono.setIdTransaccionTerceroPagador(null);
                abono.setNombreTerceroPagador(null);
            }

            if (result[37] != null && result[38] != null) {
                abono.setTipoIdAdminSubsidio(TipoIdentificacionEnum.valueOf(result[37].toString()));
                abono.setNumeroIdAdminSubsidio(result[38].toString());
            }
            if (result[39] != null)
                abono.setIdEmpleador(Long.valueOf(result[39].toString()));
            if (result[40] != null)
                abono.setIdAfiliadoPrincipal(Long.valueOf(result[40].toString()));
            if (result[41] != null)
                abono.setIdBeneficiarioDetalle(Long.valueOf(result[41].toString()));
            if (result[42] != null)
                if (result[22] == null ){
                    abono.setEstablecimientoCodigo(result[42].toString());
                }
            if (result[43] != null)
                abono.setEstablecimientoNombre(result[43].toString());
            if (result[44] != null)
                abono.setFechaTransaccionConsumo(result[44].toString());

            listaCuentasAdminSubsidios.add(abono);
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return listaCuentasAdminSubsidios.isEmpty() ? null : listaCuentasAdminSubsidios;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarIdsProcesoAnibol()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<Long> consultarIdsProcesoAnibol() {
        return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUDES_TARJETA_ESTADO_ENVIADO, Long.class)
                .getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<String> consultarRadicadosProcesoAnibol() {
        return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_RADICADOS_TARJETA_ESTADO_ENVIADO, String.class)
                .getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudAnibol() {
        return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_SOLICITUD_ANIBOL, RegistroSolicitudAnibol.class)
                .getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidio> consultarCuentaAdminSubsidioPorSolicitud(Long solicitudLiquidacionSubsidio) {
        return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_ADMIN_SUBSIDIO_POR_SOLICITUD, CuentaAdministradorSubsidio.class)
                .setParameter("solicitudLiquidacionSubsidio", solicitudLiquidacionSubsidio)
                .getResultList();
    }


    /* (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#actualizarEstadoSolicitudAnibol(java.lang.Long, com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoSolicitudAnibolEnum)
     */
    @Override
    public void actualizarEstadoSolicitudAnibol(Long idRegistroSolicitudAnibol, EstadoSolicitudAnibolEnum estadoSolicitudAnibol) {
        String firmaMetodo = "ConsultasModeloCore.actualizarEstadoSolicitudAnibol(Long,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo + "con datos de entrada: " + idRegistroSolicitudAnibol + " - " + (estadoSolicitudAnibol != null ? estadoSolicitudAnibol.name() : null));

        RegistroSolicitudAnibol registroSolicitudAnibol = buscarRegistroSolicitudAnibolPorId(idRegistroSolicitudAnibol);
        registroSolicitudAnibol.setEstadoSolicitudAnibol(estadoSolicitudAnibol);
        entityManagerCore.merge(registroSolicitudAnibol);
        entityManagerCore.flush();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    @Override
    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudDescuentoAnibol() {
        return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_SOLICITUD_DESCUENTO_ANIBOL, RegistroSolicitudAnibol.class)
                .getResultList();
    }

    @Override
    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudDescuentoPrescripcionAnibol(Long idProceso) {

        logger.info("ejecución de consultarRegistroSolicitudDescuentoPrescripcionAnibol(Long)");

        return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_SOLICITUD_DESCUENTO_PRESCRIPCION_ANIBOL, RegistroSolicitudAnibol.class)
                .setParameter("idProceso", idProceso)
                .getResultList();
    }

    @Override
    public List<RegistroSolicitudAnibol> consultarRegistroDispersionAnibol() {
        return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_REG_SOL_DISP_ORIG_CAMBIO_MEDIO_PAGO_ANIBOL, RegistroSolicitudAnibol.class)
                .getResultList();
    }

    @Override
    public CuentaAdministradorSubsidioDTO consultarCuentaAdministradorMedioTarjeta(Long idCuentaAdminSubsidio) {
        String firmaMetodo = "ConsultasModeloCore.consultarCuentaAdministradorMedioTarjeta(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo + " con idCuentaAdminSubsidio: " + idCuentaAdminSubsidio);

        CuentaAdministradorSubsidioDTO cuentasMedioTarjeta = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_ADMIN_MEDIO_TARJETA,
                        CuentaAdministradorSubsidioDTO.class)
                .setParameter("idCuentaAdminSubsidio", idCuentaAdminSubsidio).getSingleResult();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return cuentasMedioTarjeta;

    }

    @Override
    public List<DetalleSubsidioAsignadoDTO> obtenerListaDetallesSubsidioAsingnado(List<Long> listaIdsDetalle) {

        String firma = "obtenerListaDetallesSubsidioAsingnado(List<Long>)";

        logger.debug(ConstantesComunes.INICIO_LOGGER + firma);

        List<DetalleSubsidioAsignadoDTO> lista = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_DETALLES_SUBSIDIO_ASIGNADO)
                .setParameter("listadoIds", listaIdsDetalle).getResultList();

        logger.debug(ConstantesComunes.FIN_LOGGER + firma);
        return lista;
    }

    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasAdminSubsidio(List<Long> listaIds) {
        String firma = "consultarCuentasAdminSubsidio(List<Long>)";

        logger.debug(ConstantesComunes.INICIO_LOGGER + firma);

        List<CuentaAdministradorSubsidio> lista = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_CUENTAS_ADMIN_SUBSIDIO_POR_ID)
                .setParameter("listadoIds", listaIds)
                .getResultList();


        List<CuentaAdministradorSubsidioDTO> cuentasAdminSubDTO = new ArrayList<>();
        if (lista != null && !lista.isEmpty()) {

            CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO;
            for (CuentaAdministradorSubsidio cuentaAdministradorSubsidio : lista) {
                cuentaAdministradorSubsidioDTO = new CuentaAdministradorSubsidioDTO(cuentaAdministradorSubsidio);
                cuentasAdminSubDTO.add(cuentaAdministradorSubsidioDTO);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firma);
        return cuentasAdminSubDTO;
    }

    @Override
    public List<RegistroSolicitudAnibol> consultarLiquidacionFallecimientoAnibol() {
        List<RegistroSolicitudAnibol> listaDetalles = null;

        try {
            listaDetalles = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_REG_SOL_LIQ_FALLECIMIENTO_ANIBOL)
                    .getResultList();

        } catch (NoResultException e) {
            System.out.println("Error en la consulta NoResultException: " + e);
            listaDetalles = new ArrayList<>();
        } catch (Exception x) {
            System.out.println("Error en la consulta cacth: " + x);
            listaDetalles = new ArrayList<>();
        }

        return listaDetalles;

    }

    @Override
    public void aplicarCuentasLiqFallecimiento(List<Long> listaIdsAdminSubsidio) {
        logger.info("Inicia consultasModeloCore.aplicarCuentasLiqFallecimiento(List<Long> listaIdsAdminSubsidio)");
        List<CuentaAdministradorSubsidio> lista = (List<CuentaAdministradorSubsidio>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTAS_ADMINISTRADOR_SUBSIDIO_POR_ID)
                .setParameter("ids", listaIdsAdminSubsidio)
                .getResultList();

        logger.info("Cantidad de cuentas encontradas: " + lista.size());
        if (lista != null && !lista.isEmpty()) {
            for (CuentaAdministradorSubsidio cuentaAdministradorSubsidio : lista) {
                cuentaAdministradorSubsidio.setEstadoTransaccionSubsidio(EstadoTransaccionSubsidioEnum.APLICADO);
                entityManagerCore.merge(cuentaAdministradorSubsidio);
                entityManagerCore.flush();
            }
        }
    }

    @Override
    public InfoPersonaReexpedicionDTO consultarInfoPersonaReexpedicion(String tipoIdentificacion, String identificacion,
                                                                       String numeroTarjeta) {
        try {
            System.out.println("los parametro para consultarInfoPersonaReexpedicion(String tipoIdentificacion, String identificacion, String numeroTarjeta) están llegando: tipoIdentificacion= " + tipoIdentificacion + ", numeroIdentificacion= " + identificacion + ", tarjeta= " + numeroTarjeta);
            return (InfoPersonaReexpedicionDTO) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_EXISTE_Y_ASOCIADA_TARJETA_MULTISERVICIOS)
                    .setParameter("tipoId", tipoIdentificacion)
                    .setParameter("numeroId", identificacion)
                    .setParameter("numeroTarjeta", numeroTarjeta)
                    .getSingleResult();

        } catch (NoResultException nre) {
            return null;
        }
    }


    @Override
    public BigDecimal consultarSaldoTarjetaGenesys(String numeroTarjeta, Long idPersona) {
        try {
            System.out.println("consultarSaldoTarjetaGenesys(String numeroTarjeta, Long idPersona) con parametros: " + numeroTarjeta + " - " + idPersona);
            return (BigDecimal) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_SALDO_TARJETA_GENESYS)
                    .setParameter("idPersona", idPersona)
                    .setParameter("numeroTarjeta", numeroTarjeta)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println("No se encontró ningún resultado para la consulta.");
            return BigDecimal.ZERO; // Devuelve BigDecimal.ZERO como valor predeterminado cuando no se encuentra ningún resultado.
        }
    }

    @Override
    public void bloquearTarjeta(String numeroTarjeta) {
        System.out.println("bloquearTarjeta(String numeroTarjeta) con parametros: " + numeroTarjeta);
        entityManagerCore.createNamedQuery(NamedQueriesConstants.BLOQUEAR_TARJETA_ACTIVA)
                .setParameter("numeroTarjeta", numeroTarjeta).executeUpdate();

    }

    @Override
    public void persistirRegistroInconsistencia(RegistroInconsistenciaTarjeta registroInconsistencia) {
        entityManagerCore.persist(registroInconsistencia);
    }

    @Override
    public List<CuentaAdministradorSubsidio> consultarCuentasAdministradorSubAbono(String numeroTarjeta,
                                                                                   Long idPersona) {

        System.out.println("consultarCuentasAdministradorSubAbono(String numeroTarjeta, Long idPersona) con parametros: " + numeroTarjeta + " - " + idPersona);
        return (List<CuentaAdministradorSubsidio>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_ABONO_CUENTA_ADMIN_SUBSIDIO)
                .setParameter("idPersona", idPersona)
                .setParameter("numeroTarjeta", numeroTarjeta)
                .getResultList();

    }

    @Override
    public List<GruposMedioTarjetaDTO> consultarGruposTrabajadorMedioTarjeta(TipoIdentificacionEnum tipoIdentificacion,
                                                                             String identificacion, String numeroTarjeta) {
        System.out.println("consultarGruposTrabajadorMedioTarjeta(TipoIdentificacionEnum tipoIdentificacion, String identificacion, String numeroTarjeta) con parametros: " + tipoIdentificacion.name() + " - " + identificacion + " - " + numeroTarjeta);
        return (List<GruposMedioTarjetaDTO>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_GRUPOS_TRABAJADOR_MEDIO_TARJETA)
                .setParameter("tipoId", tipoIdentificacion.name())
                .setParameter("numeroId", identificacion)
                .setParameter("numeroTarjeta", numeroTarjeta)
                .getResultList();
    }

    @Override
    public void persistirDetalleSubsidioAsignado(DetalleSubsidioAsignadoDTO detalleSubsidioAsignado) {

        String firmaServicio = "ConsultasModeloCore.crearDetalleSubsidioAsignado(DetalleSubsidioAsignadoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        crearDetalleSubsidioAsignado(detalleSubsidioAsignado);

        logger.error(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    @Override
    public List<RegistroInconsistenciaTarjeta> consultarRegistroInconsistencias(Long fechaInicio, Long fechaFin) {

        Date inicio = fechaInicio == null ? Date.from(LocalDate.now().minusDays(5).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()) : Date.from(Instant.ofEpochMilli(fechaInicio).atZone(ZoneId.systemDefault()).toLocalDate().minusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fin = fechaFin == null ? Date.from(LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()) : Date.from(Instant.ofEpochMilli(fechaFin).atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        return (List<RegistroInconsistenciaTarjeta>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_INCONSISTENCIAS_TARJETA)
                .setParameter("fechaInicial", inicio)
                .setParameter("fechaFinal", fin)
                .getResultList();

    }

    @Override
    public void cerrarCasoInconsistencia(Long idRegistroInconsistencia, ResultadoGestionInconsistenciaEnum resultadoGestion, String detalleResolucion) {

        RegistroInconsistenciaTarjeta registro = (RegistroInconsistenciaTarjeta) entityManagerCore.createNamedQuery(NamedQueriesConstants.BUSCAR_REGISTRO_INCONSISTENCIA_TARJETA_POR_ID)
                .setParameter("idRegistro", idRegistroInconsistencia)
                .getSingleResult();

        registro.setDetalleResolucion(detalleResolucion);
        registro.setResultadoGestion(resultadoGestion);
        registro.setEstadoResolucion(EstadoResolucionInconsistenciaEnum.CERRADO);

        entityManagerCore.merge(registro);
        entityManagerCore.flush();
    }

    @Override
    public List<RegistroInconsistenciaTarjeta> consultarHistoricoRegistroInconsistenciaTarjeta(Long fechaInicial,
                                                                                               Long fechaFinal, TipoIdentificacionEnum tipoId, String numeroId,
                                                                                               EstadoResolucionInconsistenciaEnum estadoResolucion, TipoNovedadInconsistenciaEnum tipoNovedad) {

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        return (List<RegistroInconsistenciaTarjeta>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_REGISTRO_INCONSISTENCIAS_TARJETA)
                .setParameter("fechaInicial", fechaInicial == null ? new Date(0) : Date.from(Instant.ofEpochMilli(fechaInicial).atZone(ZoneId.systemDefault()).toLocalDate().minusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
                .setParameter("fechaFinal", fechaFinal == null ? Date.from(LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()) : Date.from(Instant.ofEpochMilli(fechaFinal).atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
                .setParameter("tipoId", tipoId != null ? tipoId.name() : null)
                .setParameter("numeroId", numeroId)
                .setParameter("estadoResolucion", estadoResolucion != null ? estadoResolucion : null)
                .setParameter("tipoNovedad", tipoNovedad != null ? tipoNovedad : null)
                .getResultList();
    }

    @Override
    public Long persistirDetalleSubsidioAsignadoObtenerId(DetalleSubsidioAsignadoDTO detalleSubsidioAsignado) {
        System.out.println("persistirDetalleSubsidioAsignadoObtenerId(DetalleSubsidioAsignadoDTO detalleSubsidioAsignado) con los parámetros: ");
        System.out.println(detalleSubsidioAsignado.toString());
        return registrarDetalleSubsidioAsignado(detalleSubsidioAsignado);
    }

    @Override
    public List<InfoDetallesSubsidioAgrupadosDTO> obtenerInfoDetallesAgrupados(List<Long> idsDetalles) {

        System.out.println("obtenerInfoDetallesAgrupados(List<Long> idsDetalles) con parametros: ");
        idsDetalles.forEach(System.out::println);
        return (List<InfoDetallesSubsidioAgrupadosDTO>) entityManagerCore.createNamedQuery(NamedQueriesConstants.OBTENER_INFO_DETALLES_AGRUPADOS)
                .setParameter("idsDetalles", idsDetalles)
                .getResultList();
    }

    @Override
    public void actualizarDatosDetallesSubsidioAsignado(List<DetalleSubsidioAsignadoDTO> nuevosDetallesSubsidioAsignado,
                                                        List<InfoDetallesSubsidioAgrupadosDTO> infoDetallesSubsidioAgrupados, String nombreUsuario) {

        nuevosDetallesSubsidioAsignado.stream().forEach(detalle -> {

            for (InfoDetallesSubsidioAgrupadosDTO detalleAgrupado : infoDetallesSubsidioAgrupados) {
                if (detalleAgrupado.getIdGrupoFamiliar() == detalle.getIdGrupoFamiliar()) {
                    detalle.setIdCuentaAdministradorSubsidio(detalleAgrupado.getIdCuentaAdministradorSubsidio());
                }
            }
            Date fechaActual = Calendar.getInstance().getTime();

            detalle.setFechaHoraUltimaModificacion(fechaActual);
            detalle.setUsuarioUltimaModificacion(nombreUsuario);

            actualizarDetalleSubsidioAsignado(detalle);
        });
    }


    /* (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarDetallesSubsidiosAsignadosAsociadosAbonoOrdenados(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesSubsidiosAsignadosAsociadosAbonoOrdenados(Long idCuentaAdminSubsidio) {

        String firmaServicio = "ConsultasModeloCore.consultarDetallesSubsidiosAsignadosAsociadosAbonoOrdenados(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<DetalleSubsidioAsignadoDTO> listaDetalles = null;

        try {
            listaDetalles = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLES_SUBSIDIO_ASIGNADO_ASOCIADOS_CUENTA_ADMIN_SUBSIDIO_ORDENADOS,
                            DetalleSubsidioAsignadoDTO.class)
                    .setParameter("idCuentaAdmonSubsidio", idCuentaAdminSubsidio).getResultList();

        } catch (NoResultException e) {

            listaDetalles = new ArrayList<>();
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return listaDetalles;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void persistirTempArchivoRetiroTerceroPagadorEfectivo(ArrayList<String[]> lineas, String usuario, Long idConvenio, Long idArchivoTerceroPagadorEfectivo) {
        String firmaMetodo = "ConsultasModeloCore.persistirTempArchivoRetiroTerceroPagadorEfectivo(String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        int primerValor = 1;

        for (String[] linea : lineas) {
            logger.debug(Arrays.toString(linea));
            TempArchivoRetiroTerceroPagadorEfectivo archivo = new TempArchivoRetiroTerceroPagadorEfectivo();
            try {
                archivo.setIdConvenio(idConvenio);
                archivo.setArchivoRetiroTerceroPagadorEfectivo(idArchivoTerceroPagadorEfectivo);
                if (linea[0] != null && TipoIdentificacionEnum.obtenerTiposIdentificacionTerceroPagador(linea[0].toString()) != null) {
                    archivo.setTipoIdentificacionAdmin(TipoIdentificacionEnum.obtenerTiposIdentificacionTerceroPagador(linea[0].toString()).name());
                }
                if (linea[1] != null && linea[1].toString().length() < 17) {
                    archivo.setNumeroIdentificacionAdmin(linea[1].toString());
                }
                if (linea[2] == null || linea[2].toString().length() > 20) {
                    archivo.setNombreCampo(CamposArchivoConstants.IDENTIFICACION_TRANSACCION_TERCERO_PAGADOR);
                    archivo.setResultado(ReultadoValidacionCampoEnum.NO_EXITOSO);
                    archivo.setMotivo(TipoErrorArchivoTerceroPagadorEfectivo.ERROR_ID_TRANSACCION_LONG_CARACTERES);
                } else {
                    archivo.setIdTransaccion(linea[2].toString());
                }
                try {
                    archivo.setValorTransaccion(Double.valueOf(linea[3]));
                } catch (NumberFormatException ex) {
                    archivo.setNombreCampo(CamposArchivoConstants.VALOR_TRANSACCION);
                    archivo.setResultado(ReultadoValidacionCampoEnum.NO_EXITOSO);
                    archivo.setMotivo(TipoErrorArchivoTerceroPagadorEfectivo.ERROR_FORMATO_VALOR_TRANSACCION_NO_VALIDO);
                }

                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    format.setLenient(false);
                    archivo.setFechaHoraTransaccion(format.parse(linea[4].toString()));
                } catch (Exception ex) {
                    archivo.setNombreCampo(CamposArchivoConstants.FECHA_TRANSACCION);
                    archivo.setResultado(ReultadoValidacionCampoEnum.NO_EXITOSO);
                    archivo.setMotivo(TipoErrorArchivoTerceroPagadorEfectivo.ERROR_FORMATO_FECHA_HORA_TRANSACCION_NO_VALIDO);
                }
                if (linea[5] != null && linea[5].toString().length() < 3)
                    archivo.setCodigoDepartamento(linea[5].toString());

                if (linea[6] != null && linea[6].toString().length() < 6)
                    archivo.setCodigoMunicipio(linea[6].toString());

                archivo.setLinea(primerValor);
                archivo.setNumeroIdentificacionAdmin(archivo.getNumeroIdentificacionAdmin() == null ? " " : archivo.getNumeroIdentificacionAdmin());
                archivo.setTipoIdentificacionAdmin(archivo.getTipoIdentificacionAdmin() == null ? " " : archivo.getTipoIdentificacionAdmin());

                entityManagerCore.persist(archivo);
                primerValor++;
            } catch (Exception e) {
                archivo.setLinea(primerValor);
                archivo.setResultado(ReultadoValidacionCampoEnum.NO_EXITOSO);
                archivo.setMotivo(TipoErrorArchivoTerceroPagadorEfectivo.ERROR_LEYENDO_DATOS_LINEA);
                archivo.setNombreCampo("");
                archivo.setNumeroIdentificacionAdmin(archivo.getNumeroIdentificacionAdmin() == null ? " " : archivo.getNumeroIdentificacionAdmin());
                archivo.setTipoIdentificacionAdmin(archivo.getTipoIdentificacionAdmin() == null ? " " : archivo.getTipoIdentificacionAdmin());
                entityManagerCore.persist(archivo);
                primerValor++;
                e.printStackTrace();
            }
        }

    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#compararRegistrosCamposArchivoTerceroPagadorSP(java.lang.String,
     * java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void ejecutarSPValidarContenidoArchivoTerceroPagadorEfectivo(Long idConvenio, Long idArchivoTerceroPagadorEfectivo) {

        String firmaMetodo = "ConsultasModeloCore.ejecutarSPValidarContenidoArchivoTerceroPagadorEfectivo(String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        StoredProcedureQuery storedProcedure = entityManagerCore
                .createStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_PG_VALIDAR_CONTENIDO_ARCHIVO_TERCERO_PAGADOR_EFECTIVO);
        storedProcedure.registerStoredProcedureParameter("nIdConvenio", Long.class, ParameterMode.IN);
        storedProcedure.setParameter("nIdConvenio", idConvenio);
        storedProcedure.registerStoredProcedureParameter("nidArchivoTerceroPagadorEfectivo", Long.class, ParameterMode.IN);
        storedProcedure.setParameter("nidArchivoTerceroPagadorEfectivo", idArchivoTerceroPagadorEfectivo);
        storedProcedure.execute();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#compararRegistrosCamposArchivoTerceroPagadorSP(java.lang.String,
     * java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void InsertRestuladosValidacionCargaManualRetiroTerceroPag(InformacionArchivoDTO informacionArchivoDTO, Long idConvenio, String nombreUsuario, Long idArchivoTerceroPagadorEfectivo) {

        String firmaMetodo = "ConsultasModeloCore.InsertRestuladosValidacionCargaManualRetiroTerceroPag(String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String parametros = idConvenio.toString() + "," +
                informacionArchivoDTO.getDocName() + "," +
                nombreUsuario + "," +
                idArchivoTerceroPagadorEfectivo.toString() + "," +
                informacionArchivoDTO.getIdentificadorDocumento() + "," +
                informacionArchivoDTO.getFileName() + ",";

        StoredProcedureQuery storedProcedure = entityManagerCore
                .createStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_PG_INSERT_RESULTADOS_VALIDACION_CARGA_MANUAL_TER_PAG);
        storedProcedure.registerStoredProcedureParameter("parametros", String.class, ParameterMode.IN);
        storedProcedure.setParameter("parametros", parametros);      
        /*
        storedProcedure.registerStoredProcedureParameter("nIdConvenio", Long.class, ParameterMode.IN);
        storedProcedure.setParameter("nIdConvenio", idConvenio);       
        storedProcedure.registerStoredProcedureParameter("sFileLoadedName", String.class, ParameterMode.IN);
        storedProcedure.setParameter("sFileLoadedName", informacionArchivoDTO.getDocName());        
        storedProcedure.registerStoredProcedureParameter("sUsuario", String.class, ParameterMode.IN);
        storedProcedure.setParameter("sUsuario", nombreUsuario);
        storedProcedure.registerStoredProcedureParameter("nidArchivoTerceroPagadorEfectivo", Long.class, ParameterMode.IN);
        storedProcedure.setParameter("nidArchivoTerceroPagadorEfectivo", idArchivoTerceroPagadorEfectivo);
        storedProcedure.registerStoredProcedureParameter("nIdentificacionECM", String.class, ParameterMode.IN);
        storedProcedure.setParameter("nIdentificacionECM", informacionArchivoDTO.getIdentificadorDocumento());
        storedProcedure.registerStoredProcedureParameter("nmNombreArchivo", String.class, ParameterMode.IN);
        storedProcedure.setParameter("nmNombreArchivo", informacionArchivoDTO.getFileName());
        */

        storedProcedure.execute();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarCuentaAdministradorSubsidio(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<TempArchivoRetiroTerceroPagadorEfectivoDTO> consultarTempArchivoRetiroTerceroPagadorEfectivo(Long idConvenio, Long idArchivoRetiroTerceroPagadorEfectivo) {

        String firmaServicio = "ConsultasModeloCore.consultarTempArchivoRetiroTerceroPagadorEfectivo(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<TempArchivoRetiroTerceroPagadorEfectivoDTO> t = null;

        t = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_TEMP_ARCHIVO_RETIRO_TERCERO_PAGADOR_EFECTIVO,
                        TempArchivoRetiroTerceroPagadorEfectivoDTO.class)
                .setParameter("idConvenio", idConvenio)
                .setParameter("idArchivoRetiroTerceroPagadorEfectivo", idArchivoRetiroTerceroPagadorEfectivo)
                .getResultList();


        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return t;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarCuentaAdministradorSubsidio(java.lang.Long)
     */
    @Override
    public Long persistirTempArchivoRetiroTerceroPagadorEfectivo(TempArchivoRetiroTerceroPagadorEfectivoDTO tempArchivoRetiroTerceroPagadorEfectivoDTO) {
        TempArchivoRetiroTerceroPagadorEfectivo entidad = tempArchivoRetiroTerceroPagadorEfectivoDTO.convertToTempArchivoRetiroTerceroPagadorEfectivoEntity();
        entityManagerCore.persist(entidad);
        return entidad.getIdTempArchivoRetiroTerceroPagadorEfectivo();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarCuentaAdministradorSubsidio(java.lang.Long)
     */
    @Override
    public Long actualizarTempArchivoRetiroTerceroPagadorEfectivo(TempArchivoRetiroTerceroPagadorEfectivoDTO tempArchivoRetiroTerceroPagadorEfectivoDTO) {
        TempArchivoRetiroTerceroPagadorEfectivo entidad = tempArchivoRetiroTerceroPagadorEfectivoDTO.convertToTempArchivoRetiroTerceroPagadorEfectivoEntity();
        entityManagerCore.merge(entidad);
        return entidad.getIdTempArchivoRetiroTerceroPagadorEfectivo();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarCuentaAdministradorSubsidio(java.lang.Long)
     */
    @Override
    public void persistirValidacionesNombreArchivoTerceroPagador(Map<ValidacionNombreArchivoEnum, ResultadoValidacionNombreArchivoEnum> validaciones,
                                                                 Long idArchivoRetiroTerceroPagadorEfectivo) {
        for (Map.Entry<ValidacionNombreArchivoEnum, ResultadoValidacionNombreArchivoEnum> entry : validaciones.entrySet()) {
            ValidacionNombreArchivoTerceroPagador validacion = new ValidacionNombreArchivoTerceroPagador();
            validacion.setArchivoRetiroTerceroPagadorEfectivo(idArchivoRetiroTerceroPagadorEfectivo);
            validacion.setVnaResultado(entry.getValue());
            validacion.setVnaValidacion(entry.getKey());
            entityManagerCore.persist(validacion);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarAbonosEnviadosMedioDePagoBancosArchivo()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarAbonosEnviadosMedioDePagoBancosArchivo() {
        List<Object[]> abonos = (List<Object[]>) entityManagerCore.
                createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_ADM_POR_TIPO_ABONO_ESTADO_ENVIADO_MEDIO_DE_PAGO_BANCO).getResultList();

        List<CuentaAdministradorSubsidioDTO> cuentas = new ArrayList<>();
        for (Object[] abono : abonos) {
            CuentaAdministradorSubsidioDTO dato = new CuentaAdministradorSubsidioDTO();
            dato.setIdCuentaAdministradorSubsidio(((BigInteger) abono[0]).longValue());
            dato.setTipoIdAdminSubsidio(abono[1] != null ? TipoIdentificacionEnum.valueOf(abono[1].toString()) : null);
            dato.setNumeroIdAdminSubsidio(abono[2] != null ? abono[2].toString() : "");
            dato.setNombresApellidosAdminSubsidio(abono[3] != null ? abono[3].toString() : "");
            dato.setCodigoBancoAdminSubsidio(abono[4] != null ? abono[4].toString() : "");
            dato.setNombreBancoAdminSubsidio(abono[5] != null ? abono[5].toString() : "");
            dato.setTipoCuentaAdminSubsidio(abono[6] != null ? TipoCuentaEnum.valueOf(abono[6].toString()) : null);
            dato.setNumeroCuentaAdminSubsidio(abono[7] != null ? abono[7].toString() : "");
            dato.setTipoIdentificacionTitularCuentaAdminSubsidio(abono[8] != null ? TipoIdentificacionEnum.valueOf(abono[8].toString()) : null);
            dato.setNumeroIdentificacionTitularCuentaAdminSubsidio(abono[9] != null ? abono[9].toString() : "");
            dato.setNombreTitularCuentaAdminSubsidio(abono[10] != null ? abono[10].toString() : "");
            dato.setValorRealTransaccion(abono[11] != null ? (BigDecimal) abono[11] : null);
            dato.setEstadoAbono(abono[12] != null ? EstadoAbonoEnum.valueOf(abono[12].toString()) : null);
            cuentas.add(dato);
        }
        return cuentas;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarAbonosEnviadosMedioDePagoBancosArchivo()
     */
    @SuppressWarnings("unchecked")
    @Override
    public Boolean existeIdentificadorTransaccionTerceroPagador(String idTransaccionTercerPagador, Long idConvenio) {
        Integer abonos = (Integer) entityManagerCore.
                createNamedQuery(NamedQueriesConstants.CONSULTAR_CANTIDAD_CUENTAS_POR_TRANSACCION_TERCERO_PAGADOR)
                .setParameter("idConvenio", idConvenio)
                .setParameter("idTransaccionTercerPagador", idTransaccionTercerPagador)
                .getSingleResult();
        return abonos > 0 ? true : false;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarAbonosEnviadosMedioDePagoBancosConFiltro()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarAbonosEnviadosMedioDePagoBancosPaginadoConFiltro(
            UriInfo uriInfo, HttpServletResponse response, String textoFiltro) {
        String firmaServicio = "ConsultasModeloCore.consultarAbonosEnviadosMedioDePagoBancosConFiltro()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        String filterText = "%".concat(textoFiltro).concat("%");

        QueryBuilder queryBuilder = new QueryBuilder(entityManagerCore, uriInfo, response);
        queryBuilder.addParam("textoFiltro", filterText);
        queryBuilder.addOrderByDefaultParam("idCuentaAdministradorSubsidio");

        Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_CUENTA_ADMIN_SUBSIDIO_POR_TIPO_ABONO_ESTADO_ENVIADO_MEDIO_DE_PAGO_BANCO_CON_FILTRO, null);

        List<CuentaAdministradorSubsidioDTO> listaCuentasAdminSubsidios = query.getResultList();

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return listaCuentasAdminSubsidios;
    }

    @Override
    public BigDecimal consultarSumatoriaAbonosEnviadosMedioDePagoBancos() {
        String firmaServicio = "ConsultasModeloCore.consultarSumatoriaAbonosEnviadosMedioDePagoBancos()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        BigDecimal resultadoSuma = new BigDecimal(0);

        try {
            resultadoSuma = (BigDecimal) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SUMATORIA_ABONOS_BANCARIOS)
                    .getSingleResult();
        } catch (NoResultException nre) {
            resultadoSuma = new BigDecimal(0);
        } catch (NonUniqueResultException nur) {
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        return resultadoSuma;
    }

    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarRetirosConEstadoSolicitado(Long fechaInicio, Long fechaFin) {
        String firmaServicio = "ConsultasModeloCore.consultarRetirosConEstadoSolicitado(Long, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<CuentaAdministradorSubsidioDTO> resultado = new ArrayList<>();

        Date prametroFechaInicial = new Date(fechaInicio);
        Date parametroFechaFinal = new Date(fechaFin);
        parametroFechaFinal = CalendarUtils.truncarHoraMaxima(parametroFechaFinal);

        try {
            resultado = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_RETIROS_CON_ESTADO_SOLICITADO, CuentaAdministradorSubsidioDTO.class)
                    .setParameter("fechaInicio", prametroFechaInicial)
                    .setParameter("fechaFin", parametroFechaFinal)
                    .getResultList();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaServicio
                    + " Ocurrio un error en la consulta de retiros con estado solicitado", e);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        for (CuentaAdministradorSubsidioDTO cuenta : resultado) {
            if (cuenta.getNombreTerceroPagador() == null || cuenta.getNombreTerceroPagador().isEmpty()) {
                cuenta.setIdTransaccionTerceroPagador("");
            }
        }
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return resultado;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> consultarCuotasDispersadasPorTerceroPagador(Long idTerceroPagador) {
        String firmaServicio = "ConsultasModeloCore.consultarRetirosConEstadoSolicitado(Long, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<Object[]> resultado = new ArrayList<>();

        try {
            resultado = (List<Object[]>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CUOTAS_DIPERSADAS_POR_CONVENIO_TERCERO_PAGADOR)
                    .setParameter("idTerceroPagador", idTerceroPagador)
                    .getResultList();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaServicio
                    + " Ocurrio un error en la consulta de retiros con estado solicitado", e);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultado;
    }

    @Override
    public Object[] consultarEncabezadoCuotasDispersadasPorTerceroPagador(Long idTerceroPagador) {
        String firmaServicio = "consultarEncabezadoCuotasDispersadasPorTerceroPagador(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Object[] resultadoEncabezado;

        try {
            resultadoEncabezado = (Object[]) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_ENCABEZADO_CUOTAS_DISPERSADAS_POR_TERCERO_PAGADOR)
                    .setParameter("idTerceroPagador", idTerceroPagador)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaServicio
                    + " Ocurrio un error en la consulta de retiros con estado solicitado", e);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultadoEncabezado;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void ejecutarAbonosMedioPagoBancos(List<Long> idCuentasAdmonSubsidio, UserDTO userDTO) {

        String firmaServicio = "ConsultasModeloCore.consultarAbonosEnviadosMedioDePagoBancos()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        logger.debug("Inicio consulta" + Calendar.getInstance().getTime());
        StringBuilder abonosNoExitosos = new StringBuilder();
        int count = 0;
        for (Long cuentaAdministradorSubsidio : idCuentasAdmonSubsidio) {
            abonosNoExitosos.append(cuentaAdministradorSubsidio);
            if (count < idCuentasAdmonSubsidio.size()) {
                abonosNoExitosos.append(",");
            }
            count++;
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            userDTO.setEmail("");
        }
        /*Se actualizan al estado APLICADO las cuentas de tipo abono, con estado enviado y medio de pago Bancos (Transferencia en este caso)*/
        entityManagerCore.createStoredProcedureQuery(NamedQueriesConstants.USP_PG_CONFIRMAR_ABONOS_MEDIO_PAGO_BANCOS)
                .registerStoredProcedureParameter("sUsuario", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("abonosNoExitosos", String.class, ParameterMode.IN)
                .setParameter("sUsuario", userDTO.getEmail())
                .setParameter("abonosNoExitosos", abonosNoExitosos.toString()).execute();
    }


    @Override
    public Long persistirArchivoTransDetaSubsidio(ArchivoTransDetaSubsidio archivoTransDetaSubsidio) {
        entityManagerCore.persist(archivoTransDetaSubsidio);
        return archivoTransDetaSubsidio.getIdArchivoTransDetaSubsidio();
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#actualizarSolicitudAnulacionSubsidioCobrado(com.asopagos.entidades.subsidiomonetario.pagos.SolicitudAnulacionSubsidioCobrado)
     */
    @Override
    public void actualizarArchivoTransDetaSubsidio(ArchivoTransDetaSubsidio archivoTransDetaSubsidio) {
        String firmaMetodo = "ConsultasModeloCore.actualizarArchivoTransDetaSubsidio( ArchivoTransDetaSubsidio )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            entityManagerCore.merge(archivoTransDetaSubsidio);
        } catch (Exception e) {
            logger.error(firmaMetodo + ": No se logro actualizar el registro de ArchivoTransDetaSubsidio", e);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);

    }

    @Override
    public List<ArchivoTransDetaSubsidio> consultarArchivoTransDetaSubsidioTodos() {
        logger.debug("Inicia consultarArchivoTransDetaSubsidioTodos()");

        List<ArchivoTransDetaSubsidio> ArchivoTransDetaSubsidioList;

        try {
            ArchivoTransDetaSubsidioList = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_TRANS_DETALLE_SUBSIDIO_TODOS, ArchivoTransDetaSubsidio.class).getResultList();

            // Si la lista no es vacia la retorna
            if (ArchivoTransDetaSubsidioList.size() > 0) {
                return ArchivoTransDetaSubsidioList;
            }
        } catch (NoResultException e) {
            ArchivoTransDetaSubsidioList = null;
        }


        logger.debug("Finaliza consultarArchivoTransDetaSubsidioTodos()");
        return null;
    }


    @Override
    public List<ArchivoTransDetaSubsidio> consultarArchivoTransDetaSubsidioEstado(EstadoArchivoTransDetaSubsidioEnum estadoArchivoTransDeta) {
        logger.debug("Inicia consultarArchivoTransDetaSubsidioEstado()");

        List<ArchivoTransDetaSubsidio> ArchivoTransDetaSubsidioList;

        try {
            ArchivoTransDetaSubsidioList = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_TRANS_DETALLE_SUBSIDIO_ESTADO, ArchivoTransDetaSubsidio.class)
                    .setParameter("estado", estadoArchivoTransDeta.name()).getResultList();

            // Si la lista no es vacia la retorna
            if (ArchivoTransDetaSubsidioList.size() > 0) {
                return ArchivoTransDetaSubsidioList;
            }
        } catch (NoResultException e) {
            ArchivoTransDetaSubsidioList = null;
        }


        logger.debug("Finaliza consultarArchivoTransDetaSubsidioEstado()");
        return null;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void limpiarBufferArchivoTransDetaSubsidio() {
        logger.debug("Inicia limpiarBufferArchivoTransDetaSubsidio()");

        final Integer TAMANYOBUFFER = 10;

        List<ArchivoTransDetaSubsidio> archivoTransDetaSubsidioList;

        archivoTransDetaSubsidioList = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_TRANS_DETALLE_SUBSIDIO_TODOS,
                        ArchivoTransDetaSubsidio.class)
                .getResultList();

        if (archivoTransDetaSubsidioList != null && !archivoTransDetaSubsidioList.isEmpty()) {

            if (archivoTransDetaSubsidioList.size() >= TAMANYOBUFFER) {

                int posicionEliminar = TAMANYOBUFFER;

                boolean eliminoGoogle = false;
                for (; posicionEliminar < archivoTransDetaSubsidioList.size(); posicionEliminar++) {

                    ArchivoTransDetaSubsidio elementoBorrar = archivoTransDetaSubsidioList.get(posicionEliminar);
                    String idArchivo = elementoBorrar.getIdentificadorECMTranDetalles();

                    if (idArchivo != null && idArchivo.length() > 0) {

                        try {
                            int index_ = idArchivo.indexOf("_");
                            idArchivo = idArchivo.substring(0, index_);
                            EliminarArchivo eliminarArchivo = new EliminarArchivo(idArchivo);
                            eliminarArchivo.execute();
                            eliminoGoogle = true;
                        } catch (Exception e) {
                            eliminoGoogle = false;
                        }

                        if (eliminoGoogle) {
                            entityManagerCore.remove(elementoBorrar);
                            entityManagerCore.flush();
                            eliminoGoogle = false;
                        }
                    } else {
                        entityManagerCore.remove(elementoBorrar);
                        entityManagerCore.flush();
                    }
                }
            }
        }

        logger.debug("Finaliza limpiarBufferArchivoTransDetaSubsidio()");
    }

    @Override
    public List<SubsidiosConsultaAnularPerdidaDerechoDTO> consultarCuentasPorAnularMantis266382() {
        logger.debug("Inicia consultarCuentasPorAnularMantis266382()");

        List<Object[]> detallesAAnular = null;

        List<SubsidiosConsultaAnularPerdidaDerechoDTO> subsidiosMonetariosAnular = new ArrayList<SubsidiosConsultaAnularPerdidaDerechoDTO>();

        try {
            detallesAAnular = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLES_A_ANULAR_MANTIS_265820).getResultList();


        } catch (NoResultException e) {
            subsidiosMonetariosAnular = null;
        }

        for (int i = 0; i < detallesAAnular.size(); i++) {

            SubsidiosConsultaAnularPerdidaDerechoDTO subsidioAnular = new SubsidiosConsultaAnularPerdidaDerechoDTO();
            subsidioAnular.setMotivoAnulacion(MotivoAnulacionSubsidioAsignadoEnum.BENEFICIARIO_SIN_DERECHO);

            subsidioAnular.setDetalleAnulacion("Anulado por utilitario");

            //0. Identificador transacción abono relacionado 
            subsidioAnular.setIdCuentaAdminSubsidio(Long.parseLong((detallesAAnular.get(i)[0].toString())));

            //1. Periodo de liquidación de la solicitud de liquidación del subsidio
            subsidioAnular.setPeriodoLiquidado((Date) (detallesAAnular.get(i)[1]));

            //2. Liquidación asociada
            subsidioAnular.setIdLiquidacionAsociada(Long.parseLong(detallesAAnular.get(i)[2].toString()));

            //3. fecha liquidación asociada
            if (detallesAAnular.get(i)[3] != null)
                subsidioAnular.setFechaLiquidacionAsociada((Date) (detallesAAnular.get(i)[3]));

            //Creación del empleador relacionado con el detalle
            PersonaModeloDTO empleador = new PersonaModeloDTO();
            //4. tipo identificación empleador
            empleador.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((detallesAAnular.get(i)[4]).toString()));
            //5. numero identificacion empleador
            empleador.setNumeroIdentificacion((detallesAAnular.get(i)[5]).toString());
            //6. razón social empleador
            if (detallesAAnular.get(i)[6] != null)
                empleador.setRazonSocial((detallesAAnular.get(i)[6]).toString());

            subsidioAnular.setEmpleador(empleador);

            //Creación del afiliado relacionado con el detalle
            PersonaModeloDTO afiliado = new PersonaModeloDTO();
            //7. tipo identificación afiliado
            afiliado.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((detallesAAnular.get(i)[7]).toString()));
            //8. numero identificacion afiliado
            afiliado.setNumeroIdentificacion((detallesAAnular.get(i)[8]).toString());
            //9. primer nombre afiliado
            if (detallesAAnular.get(i)[9] != null)
                afiliado.setPrimerNombre((detallesAAnular.get(i)[9]).toString());
            //10. segundo nombre afiliado
            if (detallesAAnular.get(i)[10] != null)
                afiliado.setSegundoNombre(detallesAAnular.get(i)[10].toString());
            //11. primer apellido afiliado
            if (detallesAAnular.get(i)[11] != null)
                afiliado.setPrimerApellido(detallesAAnular.get(i)[11].toString());
            //12. segundo apellido afiliado
            if (detallesAAnular.get(i)[12] != null)
                afiliado.setSegundoApellido(detallesAAnular.get(i)[12].toString());

            subsidioAnular.setAfiliadoPrincipal(afiliado);
            //13. Código del grupo familiar que tiene asociación con el detalle
            subsidioAnular.setCodigoGrupoFamiliarRelacionado(Short.valueOf((detallesAAnular.get(i)[13]).toString()));

            //Creación del beneficiario relacionado con el detalle
            PersonaModeloDTO beneficiario = new PersonaModeloDTO();
            //14. tipo identificación afiliado
            beneficiario.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((detallesAAnular.get(i)[14]).toString()));
            //15. numero identificacion afiliado
            beneficiario.setNumeroIdentificacion((detallesAAnular.get(i)[15]).toString());
            //16. primer nombre afiliado
            if (detallesAAnular.get(i)[16] != null)
                beneficiario.setPrimerNombre((detallesAAnular.get(i)[16]).toString());
            //17. segundo nombre afiliado
            if (detallesAAnular.get(i)[17] != null)
                beneficiario.setSegundoNombre(detallesAAnular.get(i)[17].toString());
            //18. primer apellido afiliado
            if (detallesAAnular.get(i)[18] != null)
                beneficiario.setPrimerApellido(detallesAAnular.get(i)[18].toString());
            //19. segundo apellido afiliado
            if (detallesAAnular.get(i)[19] != null)
                beneficiario.setSegundoApellido(detallesAAnular.get(i)[19].toString());

            subsidioAnular.setBeneficiario(beneficiario);

            //Creación del administrador relacionado con el detalle
            PersonaModeloDTO administrador = new PersonaModeloDTO();
            //20. tipo identificación afiliado
            administrador.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((detallesAAnular.get(i)[20]).toString()));
            //21. numero identificacion afiliado
            administrador.setNumeroIdentificacion((detallesAAnular.get(i)[21]).toString());
            //22. primer nombre afiliado
            if (detallesAAnular.get(i)[22] != null)
                administrador.setPrimerNombre((detallesAAnular.get(i)[22]).toString());
            //23. segundo nombre afiliado
            if (detallesAAnular.get(i)[23] != null)
                administrador.setSegundoNombre(detallesAAnular.get(i)[23].toString());
            //24. primer apellido afiliado
            if (detallesAAnular.get(i)[24] != null)
                administrador.setPrimerApellido(detallesAAnular.get(i)[24].toString());
            //25. segundo apellido afiliado
            if (detallesAAnular.get(i)[25] != null)
                administrador.setSegundoApellido(detallesAAnular.get(i)[25].toString());

            subsidioAnular.setAdministradorSubsidio(administrador);
            //26. medio de pago
            subsidioAnular.setMedioDePago(TipoMedioDePagoEnum.valueOf((detallesAAnular.get(i)[26]).toString()));
            //27. numero tarjeta admon cuando es medio de pago tarjeta            
            if (detallesAAnular.get(i)[27] != null)
                subsidioAnular.setNumeroTarjetaAdminSubsidio(detallesAAnular.get(i)[27].toString());
            //28. fecha hora transacción
            subsidioAnular.setFechaHoraTransaccion((Date) (detallesAAnular.get(i)[28]));
            //29. usuario transacción
            subsidioAnular.setUsuarioTransaccion(detallesAAnular.get(i)[29].toString());
            //30. valor original transaccion
            subsidioAnular.setValorOriginalTransaccion(BigDecimal.valueOf(Double.parseDouble((detallesAAnular.get(i)[30]).toString())));
            //31. valor real transaccion
            subsidioAnular.setValorRealTransaccion(BigDecimal.valueOf(Double.parseDouble((detallesAAnular.get(i)[31]).toString())));
            //32. id detalle subsidio asignado
            subsidioAnular.setIdDetalleSubsidioAsignado(Long.valueOf(detallesAAnular.get(i)[32].toString()));

            subsidiosMonetariosAnular.add(subsidioAnular);
        }


        logger.debug("Finaliza consultarArchivoTransDetaSubsidioTodos()");
        return subsidiosMonetariosAnular;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public BigDecimal consultarValorTotalSubsidiosAnular(List<String> listaMediosDePago, String diasParametrizadosAnulacion, Date fechaActual) {
        BigDecimal valorTotal = null;
        String firma = "consultarValorTotalSubsidiosAnular(List<String> listaMediosDePago, String diasParametrizadosAnulacion, Date fechaActual)";
        logger.debug("Inicio método " + firma);
        try {
            StoredProcedureQuery query = entityManagerCore.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_LISTADO_ABONOS_POR_VENCIMIENTO_PRESCRIPCION)
                    .setParameter("fechaActual", new Date())
                    .setParameter("dias", Integer.parseInt(diasParametrizadosAnulacion))
                    .setParameter("listaMediosDePago", listaMediosDePago.toString().replace("[", "").replace("]", "").replaceAll(" ", ""))
                    .setParameter("offset", 0)
                    .setParameter("orderBy", "")
                    .setParameter("limit", 0)
                    .setParameter("primeraPeticion", Boolean.TRUE)
                    .setParameter("numeroIdentificacionAdminSub", "null")
                    .setParameter("consultaTotal", Boolean.TRUE);
            query.execute();
            valorTotal = (BigDecimal) query.getResultList().get(0);
        } catch (Exception e) {
            logger.error("Error en el servicio " + firma, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug("Finaliza método " + firma);
        return valorTotal;
    }


    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ResumenListadoSubsidiosAnularDTO generarResumenListadoSubsidiosAnular(List<String> listaMediosDePago, String diasParametrizados, Boolean firstRequest, Integer offset, Integer filter, String orderBy, Integer limit) {
        String firma = "generarResumenListadoSubsidiosAnular(List<String> listaMediosDePago, String diasParametrizados, Boolean firstRequest, Integer offset, Integer filter, String orderBy, Integer limit)";
        logger.debug("Inicio método " + firma);
        List<Object[]> registros = null;
        List<SubsidioAnularDTO> resumen = new ArrayList<>();
        String totalRegistros = "";
        ResumenListadoSubsidiosAnularDTO resumenListadoSubsidiosAnularDTO = null;
        try {
            StoredProcedureQuery query = entityManagerCore.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_LISTADO_ABONOS_POR_VENCIMIENTO_PRESCRIPCION_RESUMEN)
                    .setParameter("fechaActual", new Date())
                    .setParameter("dias", Integer.parseInt(diasParametrizados))
                    .setParameter("listaMediosDePago", listaMediosDePago.toString().replace("[", "").replace("]", "").replaceAll(" ", ""))
                    .setParameter("offset", offset)
                    .setParameter("orderBy", orderBy)
                    .setParameter("limit", limit)
                    .setParameter("filtro", filter)
                    .setParameter("primeraPeticion", firstRequest);
            query.execute();
            totalRegistros = String.valueOf(query.getOutputParameterValue("totalRegistros"));
            registros = query.getResultList();
            registros.stream().forEach(registro -> resumen.add(new SubsidioAnularDTO(registro, filter)));
            resumenListadoSubsidiosAnularDTO = new ResumenListadoSubsidiosAnularDTO(resumen, totalRegistros);
        } catch (Exception e) {
            logger.error("Error en el servicio " + firma, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug("Finaliza método " + firma);
        return resumenListadoSubsidiosAnularDTO;
    }

    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ListadoSubsidiosAnularDTO generarlistadoSubsidiosAnular(List<String> listaMediosDePago, String diasParametrizados, Boolean firstRequest, Integer offset, String orderBy, Integer limit,String numeroIdentificacionAdminSub) {
        String firma = "generarlistadoSubsidiosAnular(List<String> listaMediosDePago, String diasParametrizados, Boolean firstRequest, Integer offset, String orderBy, Integer limit)";
        logger.debug("Inicio método " + firma);
        List<SubsidioMonetarioPrescribirAnularFechaDTO> subsidiosMonetariosAnular = null;
        List<Object[]> subsidios = null;
        String totalRegistros = "";
        ListadoSubsidiosAnularDTO listadoSubsidiosAnularDTO = null;
        try {
            StoredProcedureQuery query = entityManagerCore.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_LISTADO_ABONOS_POR_VENCIMIENTO_PRESCRIPCION)
                    .setParameter("fechaActual", new Date())
                    .setParameter("dias", Integer.parseInt(diasParametrizados))
                    .setParameter("listaMediosDePago", listaMediosDePago.toString().replace("[", "").replace("]", "").replaceAll(" ", ""))
                    .setParameter("offset", offset)
                    .setParameter("orderBy", orderBy)
                    .setParameter("limit", limit)
                    .setParameter("primeraPeticion", firstRequest)
                    .setParameter("consultaTotal", Boolean.FALSE)
                    .setParameter("numeroIdentificacionAdminSub", numeroIdentificacionAdminSub != null ?numeroIdentificacionAdminSub: "null");
            query.execute();
            totalRegistros = String.valueOf(query.getOutputParameterValue("totalRegistros"));
            subsidios = query.getResultList();

            if (subsidios.isEmpty()) {
                return new ListadoSubsidiosAnularDTO(subsidiosMonetariosAnular, totalRegistros);
            } else {
                subsidiosMonetariosAnular = new ArrayList<>();
            }

            for (int i = 0; i < subsidios.size(); i++) {

                //Periodo de liquidación de la solicitud de liquidación del subsidio
                Date fechaPeriodo = (Date) (subsidios.get(i)[0]);

                //Creación de la solicitud de liquidación del subsidio relacionado con el detalle.
                SolicitudLiquidacionSubsidio solicitudLiquidacionSubsidio = new SolicitudLiquidacionSubsidio();
                solicitudLiquidacionSubsidio.setIdProcesoLiquidacionSubsidio(Long.parseLong((subsidios.get(i)[1]).toString()));
                if (subsidios.get(i)[2] != null)
                    solicitudLiquidacionSubsidio.setFechaEvaluacionSegundoNivel((Date) (subsidios.get(i)[2]));

                //Creación del detalle relacionado con el abono
                DetalleSubsidioAsignado detalleSubsidio = new DetalleSubsidioAsignado();
                detalleSubsidio.setTipoLiquidacionSubsidio(TipoLiquidacionSubsidioEnum.valueOf((subsidios.get(i)[3]).toString()));

                //Creación del empleador relacionado con el detalle
                Persona empleador = new Persona();
                empleador.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((subsidios.get(i)[4]).toString()));
                empleador.setNumeroIdentificacion((subsidios.get(i)[5]).toString());
                if (subsidios.get(i)[6] != null)
                    empleador.setRazonSocial((subsidios.get(i)[6]).toString());
                if (subsidios.get(i)[7] != null)
                    empleador.setPrimerNombre((subsidios.get(i)[7]).toString());
                if (subsidios.get(i)[8] != null)
                    empleador.setSegundoNombre((subsidios.get(i)[8]).toString());
                if (subsidios.get(i)[9] != null)
                    empleador.setPrimerApellido((subsidios.get(i)[9]).toString());
                if (subsidios.get(i)[10] != null)
                    empleador.setSegundoApellido((subsidios.get(i)[10]).toString());

                //Creación del afiliado relacionado con el detalle
                Persona afiliado = new Persona();
                afiliado.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((subsidios.get(i)[11]).toString()));
                afiliado.setNumeroIdentificacion((subsidios.get(i)[12]).toString());
                if (subsidios.get(i)[13] != null)
                    afiliado.setPrimerNombre((subsidios.get(i)[13]).toString());
                if (subsidios.get(i)[14] != null)
                    afiliado.setSegundoNombre((subsidios.get(i)[14]).toString());
                if (subsidios.get(i)[15] != null)
                    afiliado.setPrimerApellido((subsidios.get(i)[15]).toString());
                if (subsidios.get(i)[16] != null)
                    afiliado.setSegundoApellido((subsidios.get(i)[16]).toString());

                //Código del grupo familiar que tiene asociación con el detalle
                Byte codigoGrupoFamiliar = Byte.valueOf((subsidios.get(i)[17]).toString());

                //Parentezco familiar del beneficiario
                ClasificacionEnum parentescoBeneficiario = ClasificacionEnum.valueOf((subsidios.get(i)[18]).toString());

                //Creación del beneficiario
                Persona beneficiario = new Persona();
                beneficiario.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((subsidios.get(i)[19]).toString()));
                beneficiario.setNumeroIdentificacion((subsidios.get(i)[20]).toString());
                if (subsidios.get(i)[21] != null)
                    beneficiario.setPrimerNombre((subsidios.get(i)[21]).toString());
                if (subsidios.get(i)[22] != null)
                    beneficiario.setSegundoNombre((subsidios.get(i)[22]).toString());
                if (subsidios.get(i)[23] != null)
                    beneficiario.setPrimerApellido((subsidios.get(i)[23]).toString());
                if (subsidios.get(i)[24] != null)
                    beneficiario.setSegundoApellido((subsidios.get(i)[24]).toString());

                //Se setea el Tipo de cuota del subsidio al detalle
                detalleSubsidio.setTipoCuotaSubsidio(TipoCuotaSubsidioEnum.valueOf((subsidios.get(i)[25]).toString()));

                //Creación del administrador de subsidio relacionado con el detalle y el abono
                Persona adminSubsidio = new Persona();
                adminSubsidio.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((subsidios.get(i)[26]).toString()));
                adminSubsidio.setNumeroIdentificacion((subsidios.get(i)[27]).toString());
                if (subsidios.get(i)[28] != null)
                    adminSubsidio.setPrimerNombre((subsidios.get(i)[28]).toString());
                if (subsidios.get(i)[29] != null)
                    adminSubsidio.setSegundoNombre((subsidios.get(i)[29]).toString());
                if (subsidios.get(i)[30] != null)
                    adminSubsidio.setPrimerApellido((subsidios.get(i)[30]).toString());
                if (subsidios.get(i)[31] != null)
                    adminSubsidio.setSegundoApellido((subsidios.get(i)[31]).toString());

                String cadenaVacia = "";
                //Valor total del detalle
                detalleSubsidio.setValorTotal(BigDecimal.valueOf(Double.parseDouble((subsidios.get(i)[34]).toString())));
                //Código del municipio asociado al sitio de pago
                String codigoMunicipio = subsidios.get(i)[35] != null ? (subsidios.get(i)[35]).toString() : cadenaVacia;
                //Nombre del municipio asociado al sitio de pago
                String nombreMunicipio = subsidios.get(i)[36] != null ? (subsidios.get(i)[36]).toString() : cadenaVacia;
                //Código del departamento asociado al sitio de pago
                String codigoDepartamento = subsidios.get(i)[37] != null ? (subsidios.get(i)[37]).toString() : cadenaVacia;
                //Nombre del departamento asociado al sitio de pago
                String nombreDepartamento = subsidios.get(i)[38] != null ? (subsidios.get(i)[38]).toString() : cadenaVacia;

                //Identificador de la cuenta relacionada con el detalle
                detalleSubsidio.setIdCuentaAdministradorSubsidio(Long.parseLong((subsidios.get(i)[39]).toString()));
                //Identificador del detalle
                detalleSubsidio.setIdDetalleSubsidioAsignado(Long.parseLong((subsidios.get(i)[40]).toString()));

                //Medio de pago relacionado a la cuenta de administrador de subsidio
                TipoMedioDePagoEnum medioDePago = TipoMedioDePagoEnum.valueOf((subsidios.get(i)[41]).toString());

                //Creación del sitio de pago relacionado con el abono
                SitioPago sitioPago = new SitioPago();

                if (TipoMedioDePagoEnum.EFECTIVO.equals(medioDePago)) {
                    if (subsidios.get(i)[32] != null && subsidios.get(i)[33] != null) {
                        sitioPago.setCodigo((subsidios.get(i)[32]).toString());
                        sitioPago.setNombre((subsidios.get(i)[33]).toString());
                    }
                }

                //Creación del subsidio a ser anulado sea por fecha de vencimiento o prescripción
                SubsidioMonetarioPrescribirAnularFechaDTO subsidioMonetarioAnular = new SubsidioMonetarioPrescribirAnularFechaDTO(fechaPeriodo,
                        detalleSubsidio, solicitudLiquidacionSubsidio, empleador, afiliado, beneficiario, adminSubsidio, sitioPago,
                        codigoGrupoFamiliar, nombreDepartamento, nombreMunicipio, medioDePago, parentescoBeneficiario, codigoDepartamento,
                        codigoMunicipio);

                subsidiosMonetariosAnular.add(subsidioMonetarioAnular);

            }
            AtomicInteger numeroRegistro = new AtomicInteger(1);
            subsidiosMonetariosAnular.stream().forEach(subsidioAnular -> subsidioAnular.setNumeroRegistro(numeroRegistro.getAndIncrement()));
            listadoSubsidiosAnularDTO = new ListadoSubsidiosAnularDTO(subsidiosMonetariosAnular, totalRegistros);
        } catch (Exception e) {
            logger.error("Error en el servicio " + firma, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug("Finaliza método " + firma);
        return listadoSubsidiosAnularDTO;
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarDescuentosSubsidio(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DescuentoSubsidioAsignadoDTO> consultarDescuentosSubsidio(Long idDetalleSubsidioAsignado) {

        String firmaServicio = "ConsultasModeloCore.consultarDescuentosSubsidio(idDetalleSubsidioAsignado)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<DescuentoSubsidioAsignadoDTO> descuentosDTO = new ArrayList<>();

        List<Object[]> lstDescuentosObj = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_DESCUENTOS_SUBSIDIO_DETALLE)
                .setParameter("idDetalleSubsidio", idDetalleSubsidioAsignado)
                .getResultList();
        if (lstDescuentosObj != null && !lstDescuentosObj.isEmpty()) {
            for (Object[] descuentoObj : lstDescuentosObj) {
                DescuentoSubsidioAsignadoDTO descuento = new DescuentoSubsidioAsignadoDTO();
                descuento.setCodigoConvenio(descuentoObj[0] != null ? ((BigInteger) descuentoObj[0]).longValue() : null);
                descuento.setNombreConvenio(descuentoObj[1] != null ? descuentoObj[1].toString() : "");
                descuento.setMontoDescontado(descuentoObj[2] != null ? (BigDecimal) descuentoObj[2] : null);
                descuento.setNombreOUT(descuentoObj[3] != null ? descuentoObj[3].toString() : "");
                descuento.setFechaCargue(descuentoObj[4] != null ? (Date) descuentoObj[4] : null);
                descuento.setCodigoReferencia(descuentoObj[5] != null ? descuentoObj[5].toString() : null);
                descuentosDTO.add(descuento);
            }
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return descuentosDTO;
    }

    @Override
    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudDispersionSubsidioMonetario(Long idProceso) {

        logger.info("ejecución de consultarRegistroSolicitudDispersionSubsidioMonetario(Long)");

        return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_SOLICITUD_DISPERSION_SUBSIDIO_MONETARIO, RegistroSolicitudAnibol.class)
                .setParameter("idProceso", idProceso)
                .getResultList();
    }

    @Override
    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudAnulacionSubsidioMonetario(Long idProceso) {

        logger.info("ejecución de consultarRegistroSolicitudAnulacionSubsidioMonetario(Long)");

        return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_SOLICITUD_ANULACION_SUBSIDIO_MONETARIO, RegistroSolicitudAnibol.class)
                .setParameter("idProceso", idProceso)
                .getResultList();
    }

    //comentado error de importacion
    /*public List<InfoAdministradorDTO> consultarAdministrador(TipoIdentificacionEnum tipoIdentificacion, String identificacion){
        List<InfoAdministradorDTO> metodovacio =null;
        return metodovacio;
    }*/
    @Override
    public void actualizaEstadoTransaccionesProcesadasDispersionSubsidioMonetario(Long idProceso) {
        logger.info("ConsultasModeloCore.actualizaEstadoTransaccionesProcesadasDispersionSubsidioMonetario(Long)");
        entityManagerCore.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ESTADO_TRANSACCIONES_PROCESADAS_DISPERSION_SUBSIDIO_MONETARIO).setParameter("idProceso", idProceso).executeUpdate();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Long consultaCuentaAdmonSubsidioDispersionSubsidioMonetario(Long idSolicitud, String numeroTarjetaAdmonSubsidio) {
        String firmaServicio = "ConsultasModeloCore.consultaCuentaAdmonSubsidioDispersionSubsidioMonetario(Long, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Object idCuentaAdmonSubsidio = null;
        try {
            idCuentaAdmonSubsidio = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_CUENTA_ADMIN_SUBSIDIO_DISPERSION_SUBSIDIO_MONETARIO)
                    .setParameter("idProceso", idSolicitud)
                    .setParameter("numeroTarjetaAdmonSubsidio", numeroTarjetaAdmonSubsidio)
                    .getSingleResult();
        } catch (NoResultException e) {
            idCuentaAdmonSubsidio = null;
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return Long.parseLong(String.valueOf(idCuentaAdmonSubsidio));
    }


    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<AutorizacionEnvioComunicadoDTO> obtenerRolesDestinatariosPrescripcion(Long idCuentaAdmonSubsidio) {
        List<AutorizacionEnvioComunicadoDTO> destinatarios = new ArrayList<>();
        //  destinatarios= null;
        try {
            List<AutorizacionEnvioComunicadoDTO> correos = new ArrayList<>();
            List<Object[]> correosObject = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_EMAIL_ADMIN_SUBSIDIO_MONETARIO_PRESCRIPCION)
                    .setParameter("idCuentaAdmonSubsidio", idCuentaAdmonSubsidio)
                    .getResultList();

            for (Object[] correoO : correosObject) {
                AutorizacionEnvioComunicadoDTO correoN = new AutorizacionEnvioComunicadoDTO();
                correoN.setDestinatario(correoO[0] != null ? correoO[0].toString() : null);
                correoN.setAutorizaEnvio(correoO[1] != null ? Boolean.valueOf(String.valueOf(correoO[1])) : Boolean.FALSE);
                correoN.setIdPersona(correoO[2] != null ? Long.valueOf(correoO[2].toString()) : null);
                correos.add(correoN);
            }
            destinatarios.addAll(correos);


        } catch (Exception e) {
            logger.error(
                    "Finaliza el método obtenerRolesDestinatarios(ParametrizacionGestionCobroModeloDTO, TipoIdentificacionEnum, String): Error técnico Inesperado",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        return destinatarios;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<Long> obtenerIdsAbonosPrescripcion(String parametro) {
        List<Long> casIdAvisoPrescripcion = new ArrayList<>();
        String diasAvisoPrescripcion = null;
        String diasPrescripcion = null;

        diasPrescripcion = (String) CacheManager.getParametro(ParametrosSistemaConstants.PARAM_DIAS_PRESCRIPCION_SUBSIDIO_MONETARIO_CCF);

        if (parametro.toString().equals("ENVIO_COMUNICADO_PRESCRIPCION_PAGOS_PRIMER_AVISO")) {
            diasAvisoPrescripcion = (String) CacheManager.getParametro(ParametrosSistemaConstants.ENVIO_COMUNICADO_PRESCRIPCION_PAGOS_PRIMER_AVISO);
        } else if (parametro.toString().equals("ENVIO_COMUNICADO_PRESCRIPCION_PAGOS_SEGUNDO_AVISO")) {
            diasAvisoPrescripcion = (String) CacheManager.getParametro(ParametrosSistemaConstants.ENVIO_COMUNICADO_PRESCRIPCION_PAGOS_SEGUNDO_AVISO);
        } else if (parametro.toString().equals("ENVIO_COMUNICADO_PRESCRIPCION_PAGOS_TERCER_AVISO")) {
            diasAvisoPrescripcion = (String) CacheManager.getParametro(ParametrosSistemaConstants.ENVIO_COMUNICADO_PRESCRIPCION_PAGOS_TERCER_AVISO);
        }
        try {
            List<Long> ids = new ArrayList<>();
            List<Object> listadoCasId = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_CUENTA_ADMIN_AVISO_PRESCRIPCION_PAGOS)
                    .setParameter("diasPrescripcion", diasPrescripcion)
                    .setParameter("diasAvisoPrescripcion", diasAvisoPrescripcion)
                    .getResultList();

            for (Object idsCuentaAdmin : listadoCasId) {
                Long resul = Long.parseLong(idsCuentaAdmin.toString());
                ids.add(resul);
            }
            casIdAvisoPrescripcion.addAll(ids);
        } catch (Exception e) {
            logger.error(e);
        }
        logger.info("**__**Finaliza obtenerIdsAbonosPrescripcion");
        return casIdAvisoPrescripcion;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void ejecutarAbonosMedioPagoBancosArchivo(List<String> idCuentaAdmonSubNoexitoso, List<String> idCuentaAdmonSubExitoso, String userEmail) {

        String firmaServicio = "ConsultasModeloCore.confirmarResultadosAbonosBancariosArchivo()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        logger.debug("Inicio consulta" + Calendar.getInstance().getTime());
        StringBuilder abonosNoExitosos = new StringBuilder();
        StringBuilder abonosExitosos = new StringBuilder();
        int count = 0;

        //Se arma variable que contiene los ABONOS NO EXITOSOS 
        for (String cuentaAdministradorSubsidio : idCuentaAdmonSubNoexitoso) {
            abonosNoExitosos.append(cuentaAdministradorSubsidio);
            if (count < idCuentaAdmonSubNoexitoso.size()) {
                abonosNoExitosos.append(",");
            }
            count++;
        }
         count = 0;
        //Se arma variable que contiene los ABONOS  EXITOSOS 
        for (String cuentaAdministradorSubsidio : idCuentaAdmonSubExitoso) {
            abonosExitosos.append(cuentaAdministradorSubsidio);
            if (count < idCuentaAdmonSubExitoso.size()) {
                abonosExitosos.append(",");
            }
            count++;
        }


        logger.debug("abonosNoExitosos.toString(): " + abonosNoExitosos.toString());
        logger.debug("abonosExitosos.toString(): " + abonosExitosos.toString());

        logger.debug("abonosNoExitosos.length(): " + abonosNoExitosos.length());
        logger.debug("abonosExitosos.length(): " + abonosExitosos.length());



        /*Se actualizan al estado APLICADO las cuentas de tipo abono, con estado enviado y medio de pago Bancos (Transferencia en este caso)*/
        entityManagerCore.createStoredProcedureQuery(NamedQueriesConstants.USP_PG_CONFIRMAR_ABONOS_MEDIO_PAGO_BANCOS_ARCHIVO)
                .registerStoredProcedureParameter("sUsuario", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("abonosNoExitosos", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("abonosExitosos", String.class, ParameterMode.IN)
                .setParameter("sUsuario", userEmail)
                .setParameter("abonosNoExitosos", abonosNoExitosos.toString())
                .setParameter("abonosExitosos", abonosExitosos.toString()).execute(); 
    }

    @Override
    public InfoPersonaExpedicionDTO consultarInfoPersonaExpedicion(String tipoIdentificacion, String identificacion) {
        try {
            System.out.println("los parametro para consultarInfoPersonaEexpedicion(String tipoIdentificacion, String identificacion) están llegando: tipoIdentificacion= " + tipoIdentificacion + ", numeroIdentificacion= " + identificacion);
            return (InfoPersonaExpedicionDTO) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_EXISTE_Y_ESTADO_AFILIACION)
                    .setParameter("tipoIdentificacion", tipoIdentificacion)
                    .setParameter("identificacion", identificacion)
                    .getSingleResult();

        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public List<InfoPersonaExpedicionValidacionesDTO> consultarInfoPersonaExpedicionValidaciones(String tipoIdentificacion, String identificacion) {
        try {
            System.out.println("los parametro para consultarInfoPersonaEexpedicionValidaciones(String tipoIdentificacion, String identificacion) están llegando: tipoIdentificacion= " + tipoIdentificacion + ", numeroIdentificacion= " + identificacion);
            List<InfoPersonaExpedicionValidacionesDTO> infoPersonasValidacion = null;

            infoPersonasValidacion = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_ADMINSUBSIDIO_GRUPOFAMILIAR)
                    .setParameter("tipoIdentificacion", tipoIdentificacion)
                    .setParameter("identificacion", identificacion)
                    .getResultList();
            System.out.println("Resultado de la busqueda del query es:    " + infoPersonasValidacion);
            return infoPersonasValidacion;
        } catch (NoResultException nre) {
            return null;
        }
    }
    @Override
    public Long persistirAuditoriaRecaudoYPagos(AuditoriaRecaudosYPagos auditoriaRecaudosYPagos) {
        AuditoriaRecaudosYPagos managed = entityManagerCore.merge(auditoriaRecaudosYPagos);
        return managed.getIdAuditoriaRecaudosYPagos();
    }
     @Override
    public Map<String,String> modificarCuentaYDetallePorReverso(String idTransaccionTerceroPagador,String nombreTerceroPagador) {
        String firmaServicio = "ConsultasModeloCore.modificarCuentaYDetallePorReverso(cargueArchivoRetiro) ";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Map<String,String> respuesta = new HashMap<String,String>();

            StoredProcedureQuery query =  entityManagerCore
            .createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_PG_MODIFICARCUENTAYDETALLEPORREVERSO)
            .setParameter("idTransaccionTerceroPagador", idTransaccionTerceroPagador)
            .setParameter("nombreTerceroPagador", nombreTerceroPagador);

            query.execute();
            respuesta.put("resultdo",String.valueOf(query.getOutputParameterValue("Resultado")).equals("1") ? "true" : "false");
            respuesta.put("mensajeError",String.valueOf(query.getOutputParameterValue("mensajeError")));

            
       
        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#consultarUsuarioTerceroPagador(String, String, String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Object consultarUsuarioTerceroPagador(String nombreTerceroPagador) {

        String firmaMetodo = "ConsultasModeloCore.consultarUsuarioTerceroPagador(String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Object estadoUsuario = new Object();
        try {
            estadoUsuario = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_USUARIO_TERCERO)
                    .setParameter("usuario", nombreTerceroPagador)
                    .getSingleResult();

            logger.info("consulta el usuario--> " + estadoUsuario.toString());
        } catch (NoResultException e) {
            return null;
        } catch (NullPointerException ex) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return estadoUsuario;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> listadoValoresRetiros(String idTransaccionTerceroPagador,String nombreTerceroPagador, String idPuntoCobro) {
        String firmaMetodo = "ConsultasModeloCore.listadoRetiros(String, String, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<CuentaAdministradorSubsidioDTO> listadoTransaccionesRetiro = new ArrayList<>();

        try{
            List<CuentaAdministradorSubsidioDTO> transaccionesRetiro = new ArrayList<>();
            List<Object[]> retirosObject = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTADO_TRANSACCIONES_RETIRO)
                    .setParameter("idTransaccion", idTransaccionTerceroPagador)
                    .setParameter("usuario", nombreTerceroPagador)
                    .setParameter("idPuntoCobro", idPuntoCobro)
                    .getResultList();

            for (Object[] transaccionRetiro : retirosObject) {
                CuentaAdministradorSubsidioDTO retiro = new CuentaAdministradorSubsidioDTO();
                retiro.setIdCuentaAdministradorSubsidio(transaccionRetiro[0] != null ? Long.valueOf(transaccionRetiro[0].toString()) : null);
                retiro.setValorRealTransaccion(transaccionRetiro[1] != null ? BigDecimal.valueOf(Double.valueOf(transaccionRetiro[1].toString())) : null);
                retiro.setIdAdministradorSubsidio(transaccionRetiro[2] != null ? Long.valueOf(transaccionRetiro[2].toString()) : null);
                retiro.setIdRemisionDatosTerceroPagador(transaccionRetiro[3] != null ? (transaccionRetiro[3].toString()) : null);
                transaccionesRetiro.add(retiro);
            }
            listadoTransaccionesRetiro.addAll(transaccionesRetiro);

        } catch (Exception e) {
            logger.error("Finaliza listadoValoresRetiros",e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listadoTransaccionesRetiro;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> listadoAbonosRetiro(String idTransaccionTerceroPagador, String nombreTerceroPagador, String idPuntoCobro) {
        String firmaMetodo = "ConsultasModeloCore.listadoAbonosRetiro(String, String, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<CuentaAdministradorSubsidioDTO> listadoAbonos = new ArrayList<>();
        try {
            List<CuentaAdministradorSubsidioDTO> abonosRetiro = new ArrayList<>();
            List<Object[]> retirosObject = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTADO_ABONOS_RETIRO)
                    .setParameter("idTransaccion", idTransaccionTerceroPagador)
                    .setParameter("usuario", nombreTerceroPagador)
                    .setParameter("idPuntoCobro", idPuntoCobro)
                    .getResultList();

            for (Object[] transaccionRetiro : retirosObject) {
                CuentaAdministradorSubsidioDTO abonos = new CuentaAdministradorSubsidioDTO();
                abonos.setIdCuentaAdministradorSubsidio(transaccionRetiro[0] != null ? Long.valueOf(transaccionRetiro[0].toString()) : null);
                abonos.setValorRealTransaccion(transaccionRetiro[1] != null ? BigDecimal.valueOf(Double.valueOf(transaccionRetiro[1].toString())) : null);
                logger.info("abonos valor " + abonos.getIdCuentaAdministradorSubsidio());
                abonosRetiro.add(abonos);
            }
            listadoAbonos.addAll(abonosRetiro);
            logger.info("listadoAbonos " + listadoAbonos);
        }catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            logger.error("Finaliza listadoValoresRetiros", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listadoAbonos;
    }

    @Override
    public void eliminarCuentAdministradorSubsidio(CuentaAdministradorSubsidio cuentaAdmin){
        try {
            // Verificar si la entidad está gestionada
            if (!entityManagerCore.contains(cuentaAdmin)) {
                // Reatachar la entidad al contexto de persistencia
                cuentaAdmin = entityManagerCore.merge(cuentaAdmin);
            }
            // Ahora eliminar la entidad
            entityManagerCore.remove(cuentaAdmin);
        } catch (IllegalArgumentException e) {
        }
    }

    @Override
    public void restaurarDetallesSubsidioAsignado(List<DetalleSubsidioAsignado> detallesRestaurar){
        logger.info("ingresa restaurarDetallesSubsidioAsignado");
        if (!detallesRestaurar.isEmpty()) {
            for (DetalleSubsidioAsignado detalle : detallesRestaurar) {
                logger.info("detalle periodo " +detalle.getPeriodoLiquidado());
                detalle.setFechaHoraUltimaModificacion(new Date());
                entityManagerCore.merge(detalle);
            }

            // Opcionalmente, puedes llamar a flush() si necesitas que los cambios se reflejen inmediatamente
            entityManagerCore.flush();
        }

        logger.info("finaliza restaurarDetallesSubsidioAsignado");

    }

    @Override
    public List<DetalleSubsidioAsignado> consultarDetallesRestaurar(List<Long> idCuentaAdminSubsidio){
        try{
            return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLES_SUBSIDIO_ADMIN,DetalleSubsidioAsignado.class).setParameter("id",idCuentaAdminSubsidio).getResultList();
        }catch(NoResultException e){

        }
        List<DetalleSubsidioAsignado> listaVacia = new ArrayList<>();
        return listaVacia;
    }

    @Override
    public void consultarRetirosParciales(Long idRetiro){
        logger.info("ingresa consultarRetirosParciales");
        try{
            List<CuentaAdministradorSubsidio> cuentas = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_SUBSIDIO_ADMIN,CuentaAdministradorSubsidio.class)
                    .setParameter("idRetiro",idRetiro)
                    .getResultList();

            logger.info("cuentas " + cuentas.size());

            if(cuentas != null && !cuentas.isEmpty()) {

                for (CuentaAdministradorSubsidio cuenta : cuentas) {
                    logger.info("cuenta  consultarRetirosParciales " + cuenta);
                    // Verificar si la entidad está gestionada
                    if (!entityManagerCore.contains(cuenta)) {
                        // Reatachar la entidad al contexto de persistencia
                        cuenta = entityManagerCore.merge(cuenta);
                    }

                    List<DetalleSubsidioAsignado> detalle = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLES_SUBSIDIO_ADMIN, DetalleSubsidioAsignado.class).setParameter("id", cuenta.getIdCuentaAdministradorSubsidio()).getResultList();
                    if (!detalle.isEmpty()) {
                        logger.info("detalle " + detalle.get(0));
                        entityManagerCore.remove(detalle.get(0));
                    }
                    // Ahora eliminar la entidad
                    entityManagerCore.remove(cuenta);
                }
            }
        logger.info("finaliza consultarRetirosParciales");
        }catch(NoResultException e){

        }

    }

    @Override
    public void restaurarCuentaSubsidioAsignado(List<CuentaAdministradorSubsidio> cuentasAdmin){
        logger.info("ingresa restaurarCuentaSubsidioAsignado");
        if(!cuentasAdmin.isEmpty()){
            for(CuentaAdministradorSubsidio cuentaAdmin : cuentasAdmin){
                logger.info("cuentaAdmin restaurarCuentaSubsidioAsignado");
                logger.info("cuentaAdmin " + cuentaAdmin.getIdCuentaAdministradorSubsidio());
                entityManagerCore.merge(cuentaAdmin);
            }
        }
        logger.info("finaliza restaurarCuentaSubsidioAsignado");
    }

    @Override
    public Boolean validarExistenciaTarjeta(String numeroExpedido) {
        // try {
            String resultado = (String) entityManagerCore.createNamedQuery(NamedQueriesConstants.VALIDAR_EXISTENCIA_TARJETA_POR_NUMERO)
                                                .setParameter("numeroExpedido", numeroExpedido)
                                                .getSingleResult();
            // Devuelve true si el resultado es 1, false si es 0
            logger.info("eciste tarjeta ? " +resultado);
            return Boolean.valueOf(resultado);
        // } catch (NoResultException e) {
        //     // Manejar el caso en que no haya ningún resultado
        //     return false;
        // } catch (Exception e) {
        //     // Manejar otras posibles excepciones
        //     e.printStackTrace();
        //     return false;
        // }
    }

    @Override
    public List<Long> consultarGruposFamiliaresConMarcaYAdmin(String tipoIdentificacion, String numeroIdentificacion, String numeroTarjeta, Boolean expedicion){
        try{
            return (List<Long>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_GRUPOS_FAMILIARES_CON_MARCA_Y_ADMIN)
                .setParameter("tipoIdentificacion",tipoIdentificacion)
                .setParameter("numeroIdentificacion",numeroIdentificacion)
                .setParameter("numeroTarjeta", numeroTarjeta == null ? "null" : numeroTarjeta)
                .setParameter("expedicion", expedicion ? 1 : 0)
                .getResultList();
        }catch( Exception e){
            logger.error(e);
            return new ArrayList<>();
        }
    }

    @Override
    public String consultarEstadoDispercion(String numeroRadicacion){
        Object resultado =  entityManagerCore
        .createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_DISPERCION)
        .setParameter("numeroRadicacion", numeroRadicacion).getSingleResult();
        String estado = resultado != null ? resultado.toString() : null;
        return estado;
    }

    @Override
    public String consultarMedioYgruposParaTraslado(String numeroDocumento,TipoIdentificacionEnum tipoDocumento,List<TipoMedioDePagoEnum> medioDePago,String numeroTarjeta){
        logger.info("Inicia consultarMedioYgruposParaTraslado(String numeroDocumento,TipoIdentificacionEnum tipoDocumento,List<TipoMedioDePagoEnum> medioDePago) ");
        logger.warn("parametros de entrada");
        logger.warn(tipoDocumento);
        logger.warn(numeroDocumento);
        List<String> mediosPago = new ArrayList();

        if(medioDePago != null && !medioDePago.isEmpty()){
            for(TipoMedioDePagoEnum medioPago : medioDePago){
                mediosPago.add( String.valueOf(medioPago));
            }
        }

        String mediosPagoString = String.join(",", mediosPago);

        return (String) entityManagerCore.createStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_MEDIO_Y_GRUPOS_PARA_TRASLADO)
            .registerStoredProcedureParameter("numeroDocumento", String.class, ParameterMode.IN)
            .registerStoredProcedureParameter("tipoDocumento", String.class, ParameterMode.IN)
            .registerStoredProcedureParameter("mediosDePago", String.class, ParameterMode.IN)
            .registerStoredProcedureParameter("numeroTarjeta", String.class, ParameterMode.IN)
            .setParameter("numeroDocumento",String.valueOf(numeroDocumento))
            .setParameter("tipoDocumento",String.valueOf(tipoDocumento))
            .setParameter("mediosDePago",mediosPagoString)
            .setParameter("numeroTarjeta",numeroTarjeta == null ? "" : numeroTarjeta)
            .getSingleResult();
    }

    @Override
    public List<MedioDePagoModeloDTO> consultarInfoMedioTarjetaTraslado(String idAdmin){
        logger.info("Inicia consultarInfoMedioTarjetaTraslado(String idAdmin)");
        try{
            return (List<MedioDePagoModeloDTO>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_MEDIO_TARJETA_TRASLADO)
                .setParameter("idAdmin",idAdmin)
                .getResultList();
        }catch(Exception e){
            logger.error(e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<MedioDePagoModeloDTO> consultarInfoMedioTransferenciaTraslado(String idAdmin){
        logger.info("Inicia consultarInfoMedioTransferenciaTraslado(String idAdmin)");
        try{
            return (List<MedioDePagoModeloDTO>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_MEDIO_TRANSFERENCIA_TRASLADO)
                .setParameter("idAdmin",idAdmin)
                .getResultList();
        }catch(Exception e){
            logger.error(e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<MedioDePagoModeloDTO> consultarInfoMedioEfectivoTraslado(String idAdmin){
        logger.info("Inicia consultarInfoMedioEfectivoTraslado(String idAdmin)");
        try{
            return (List<MedioDePagoModeloDTO>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_MEDIO_EFECTIVO_TRASLADO)
                .setParameter("idAdmin",idAdmin)
                .getResultList();
        }catch(Exception e){
            logger.error(e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<CuentaAdministradorSubsidio> consultarCuentasAdministradorTraslado(Long idAdmin,List<Long> idsGrupoFamiliar,List<Long> idsMediosDePagoPrevios){
        logger.info("Inicia List<CuentaAdministradorSubsidio> consultarCuentasAdministradorTraslado(Long idAdmin,Long idAfiliadoPrincipal,List<Long> idsGrupoFamiliar,List<Long> IdsMediosDePagoPrevios)");
        try{
            return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTAS_ADMINISTRADOR_TRASLADO, CuentaAdministradorSubsidio.class)
                .setParameter("idAdmin",idAdmin)
                .setParameter("idsGrupoFamiliar", idsGrupoFamiliar)
                .setParameter("idsMediosDePagoPrevios", idsMediosDePagoPrevios)
                .getResultList();
        }catch(Exception e){
            logger.error(e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<DetalleSubsidioAsignado> consultarDetallesCuentaTraslado(Long idCuenta,List<Long> idsGrupoFamiliar){
        logger.info("Inicia consultarDetallesCuentaTraslado(Long idCuenta)");
        try{
            return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLES_CUENTA_TRASLADO, DetalleSubsidioAsignado.class)
                .setParameter("idCuenta",idCuenta)
                .setParameter("idsGrupos",idsGrupoFamiliar)
                .getResultList();
        }catch(Exception e){
            logger.error(e);
            return new ArrayList<>();
        }
    }

    @Override
    public MedioDePagoModeloDTO consultarMedioDePagoTraslado(Long idMedioDePago){
        logger.info("Inicia consultarMedioDePagoTraslado(Long idMedioDePago)");
        try{
            MedioDePago medioDePago = (MedioDePago) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIOPAGO_TRASLADO, MedioDePago.class)
                                                            .setParameter("idMedioPago",idMedioDePago)
                                                            .getSingleResult();
            MedioDePagoModeloDTO medioModelo = new MedioDePagoModeloDTO();
            medioModelo.convertToDTO(medioDePago);

            if(TipoMedioDePagoEnum.TRANSFERENCIA.equals(medioModelo.getTipoMedioDePago())){
                Object[] datosBanco = (Object[]) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_BANCO)
                                                .setParameter("idMedioPago",idMedioDePago)
                                                .getSingleResult();
                medioModelo.setNombreBanco(datosBanco[0].toString());
                medioModelo.setCodigoBanco(datosBanco[1].toString());
            }else if(TipoMedioDePagoEnum.TARJETA.equals(medioModelo.getTipoMedioDePago())){
                Object[] datosTarjeta = (Object[]) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_TARJETA)
                                                .setParameter("idMedioPago",idMedioDePago)
                                                .getSingleResult();
                medioModelo.setNumeroIdentificacionTitular(datosTarjeta[0].toString());
                medioModelo.setTipoIdentificacionTitular(TipoIdentificacionEnum.valueOf(datosTarjeta[1].toString()));
            }
            return medioModelo;
        }catch(Exception e){
            logger.warn("valor para la exepcion>>>>>>"+idMedioDePago);
            logger.error(e);
            return new MedioDePagoModeloDTO();
        }
    }

    @Override
    public void guardarJsonRespuestaAnibol(String salidaRespuestaAnibol, Long idProceso) {
        String firmaMetodo = "ConsultasModeloCore.guardarRespuestaAnibol()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            entityManagerCore
                .createNamedQuery(NamedQueriesConstants.GUARDAR_JSON_RESPUESTA_CONSULTA_PROCESO_ANIBOL)
                .setParameter("idProceso", idProceso)
                .setParameter("salidaRespuestaAnibol", salidaRespuestaAnibol)
                .executeUpdate();
        } catch (Exception e) {
            logger.error(e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    @Override
    public PersonaModeloDTO consultarPersonaAdmin(Long idAdmin){
        logger.info("PersonaModeloDTO consultarPersonaAdmin(Long idAdmin)");
        try{
            return (PersonaModeloDTO) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_ADMIN_TRASLADO)
                                                .setParameter("idAdmin",idAdmin)
                                                .getSingleResult();
        }catch(Exception e){
            logger.error(e);
            return new PersonaModeloDTO();
        }
    }

    @Override
    public void bloquearAbonosATrasladar(List<CuentaAdministradorSubsidio> cuentas,UserDTO user){
        for(CuentaAdministradorSubsidio cuenta : cuentas){
            cuenta.setEstadoTransaccionSubsidio(EstadoTransaccionSubsidioEnum.BLOQUEADO);
            cuenta.setFechaHoraUltimaModificacion(new Date());
            cuenta.setUsuarioUltimaModificacion(user.getNombreUsuario());
            entityManagerCore.merge(cuenta);
        }

    }

    @Override
    public List<RegistroSolicitudAnibol> consultarRegistrosAnibolTraslado(){
        try{
            return (List<RegistroSolicitudAnibol>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_ANIBOL_TRASLADO,RegistroSolicitudAnibol.class).getResultList();
        }catch(Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public void restablecerEstadoCuenta(Long idCuenta, UserDTO user){
        try{
            CuentaAdministradorSubsidio cuenta = (CuentaAdministradorSubsidio) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_REESTABLECER,CuentaAdministradorSubsidio.class)
                                                    .setParameter("idCuenta",idCuenta)
                                                    .getSingleResult();
            cuenta.setEstadoTransaccionSubsidio(EstadoTransaccionSubsidioEnum.APLICADO);
            cuenta.setFechaHoraUltimaModificacion(new Date());
            cuenta.setUsuarioUltimaModificacion(user.getNombreUsuario());
            entityManagerCore.merge(cuenta);
        }catch(Exception e ){
            logger.error("fallo el proceso de restablecer cuenta a su estado original id: " + idCuenta);
            logger.error(e);
        }
    }

    @Override
    public CuentaAdministradorSubsidio consultarCuentasAdministradorTraslado(Long idCuenta){
        logger.info("CuentaAdministradorSubsidio consultarCuentasAdministradorTraslado(Long idCuenta)");
        try{
            return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_ADMINISTRADOR_TRASLADO, CuentaAdministradorSubsidio.class)
                .setParameter("idCuenta",idCuenta)
                .getSingleResult();
        }catch(Exception e){
            logger.error(e);
            return new CuentaAdministradorSubsidio();
        }
    }

    @Override
    public List<DetalleSubsidioAsignado> consultarDetallesTraslado(Long idCuenta, List<Long> idsDetalle){
        logger.info("List<DetalleSubsidioAsignado> consultarDetallesTraslado(Long idCuenta, List<Long> idsDetalles)");
        try{
            return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLES_RETOMA_TRASLADO,DetalleSubsidioAsignado.class)
                .setParameter("idCuenta",idCuenta)
                .setParameter("idsDetalle",idsDetalle)
                .getResultList();
        }catch(Exception e){
            logger.error(e);
            return new ArrayList<>();
        }
    }

    @Override
    public Long crearMedioDePagoParaTraslado(Long idSitioPago){
        MedioEfectivo medioEfectivo = new MedioEfectivo();

        Long sedeCaja = 1L;
        medioEfectivo.setEfectivo(Boolean.TRUE);
        medioEfectivo.setSitioPago(idSitioPago);
        medioEfectivo.setSedeCaja(sedeCaja) ;

        entityManagerCore.persist(medioEfectivo);
        return medioEfectivo.getIdMedioPago();
    }

    @Override 
    public Long registrarProcesoBandeja(BandejaDeTransacciones bandeja){
        entityManagerCore.persist(bandeja);
        return bandeja.getIdBandejaDeTransacciones();
    }

    @Override
    public void actualizarProcesoBandeja(Long idBandeja,EstadoBandejaTransaccionEnum estado,Long idSolicitud){

        BandejaDeTransacciones bandeja = (BandejaDeTransacciones) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_BANDEJA_ACTUALIZAR, BandejaDeTransacciones.class)
            .setParameter("idBandeja",idBandeja).getSingleResult();
        bandeja.setIdBandejaDeTransacciones(idBandeja);
        if(estado != null){
            bandeja.setEstadoBandeja(estado);
        }
        if(idSolicitud !=null){
            bandeja.setIdSolicitud(idSolicitud);
        }
        bandeja.setFechaFin(new Date());
        entityManagerCore.merge(bandeja);
    }

    @Override
    public Object[] consultarDatosAdminRegistro(Long idAdmin){
        logger.warn("consultarDatosAdminRegistro(Long idAdmin) " + idAdmin);
        Object[] datosAdmin = (Object[]) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_ADMIN_REGISTRO_BANDEJA)
                                                .setParameter("idAdmin",idAdmin).getSingleResult();
        return datosAdmin;
    }

    @Override
    public List<Long> consultarBandejaTransacciones(List<Long> idsCuenta,EstadoBandejaTransaccionEnum estado,ProcesoBandejaTransaccionEnum proceso){
        try{
            List<String> idsCuentaS = new ArrayList<>();
            for(Long idCuenta : idsCuenta){
                idsCuenta.add(idCuenta);
            }
            return (List<Long>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_BANDEJA_TRANSACCIONES)
                                    .setParameter("idsCuenta",idsCuentaS)
                                    .setParameter("estado",String.valueOf(estado))
                                    .setParameter("proceso",String.valueOf(proceso)).getResultList();
        }catch(Exception e){
            logger.error(e);
            return new ArrayList<>();
        }
    }

    @Override
    public Long consultarBandejaTransacciones(Long idProceso){
        try{
            return (Long) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_BANDEJA_TRANSACCIONES_ANIBOL)
                                    .setParameter("idProceso",idProceso)
                                    .getSingleResult();
        }catch(Exception e){
            logger.error(e);
            return null;
        }
    }

    public Long consultarUltimaSolicitud(Long idAdmin){
        try{
            return (Long) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_ULTIMA_SOLICITUD)
                                .setParameter("idAdmin",idAdmin)
                                .getSingleResult();
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public Long consultarUltimaSolicitud(TipoIdentificacionEnum tipoIdentificacionAdmin, String numeroIdentificacionAdmin){
        try{
            return (Long) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_ULTIMA_SOLICITUD_DOCUMENTO)
                                .setParameter("tipo",String.valueOf(tipoIdentificacionAdmin))
                                .setParameter("numero",numeroIdentificacionAdmin)
                                .getSingleResult();
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public List<BandejaDeTransacciones> consultarBandejaTransaccionesPorPersona(String proceso, PersonaModeloDTO persona){
        logger.warn("proceso: "+ proceso);
        try{
            return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_BANDEJA_TRANSACCIONES_POR_PERSONA, BandejaDeTransacciones.class)
                        .setParameter("proceso", proceso)
                        .setParameter("numero", persona.getNumeroIdentificacion())
                        .setParameter("tipo", persona.getTipoIdentificacion())
                        .getResultList();
        }catch(Exception e){
            logger.error("fallo consultar bandeja de transacciones por persona: "+ proceso);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<PersonaModeloDTO> consultarPersonasBandejaTransacciones(String proceso,PersonaModeloDTO persona){
        try {
            return (List<PersonaModeloDTO>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONAS_BANDEJA_TRANSACCIONES, PersonaModeloDTO.class)
                                                             .setParameter("proceso", proceso )
                                                             .setParameter("tipoIdentificacion", persona.getTipoIdentificacion() == null ? null :persona.getTipoIdentificacion().name())
                                                             .setParameter("numeroIdentificacion", persona.getNumeroIdentificacion())
                                                             .setParameter("primerNombre",persona.getPrimerNombre())
                                                             .setParameter("segundoNombre",persona.getSegundoNombre())
                                                             .setParameter("primerApellido",persona.getPrimerApellido())
                                                             .setParameter("segundoApellido",persona.getSegundoApellido())
                                                             .getResultList();
        } catch (NoResultException e) {
            logger.error("fallo la consulta");
            e.printStackTrace();
            return new ArrayList<>();
        }
        
    }

    @Override
    public DetalleBandejaTransaccionesDTO consultarDetalleBandejaTransacciones(Long idBandeja){
        try{
            DetalleBandejaTransaccionesDTO detalleBandeja = (DetalleBandejaTransaccionesDTO) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_BANDEJA_TRANSACCIONES, DetalleBandejaTransaccionesDTO.class)
                                                                .setParameter("idBandeja",idBandeja)    
                                                                .getSingleResult();
            MedioDePagoModeloDTO medioOrigenDTO = consultarMedioDPagoPorId(detalleBandeja.getIdMedioDePagoOrigen());
            MedioDePagoModeloDTO medioDestinoDTO = consultarMedioDPagoPorId(detalleBandeja.getIdMedioDePagoDestino());

            detalleBandeja.setMedioDePagoOrigen(medioOrigenDTO);
            detalleBandeja.setMedioDePagoDestino(medioDestinoDTO);

            return detalleBandeja;

        }catch(Exception e){
            logger.error("ocurrio un fallo consultando el detalle de la bandeja id: " + idBandeja);
            logger.error(e);
            return new DetalleBandejaTransaccionesDTO();
        }
    }

    @Override
    public Long consultarIdMedioDePagoTarjeta(String tipoIdentificacion,String numeroIdentificacion){
        logger.info("inicia public Long consultarIdMedioDePagoTarjeta(String tipoIdentificacion,String numeroIdentificacion)");
        try {
            logger.warn("parametros de entrada");
            logger.warn("tipoIdentificacion"+tipoIdentificacion);
            logger.warn("numeroIdentificacion"+numeroIdentificacion);
            return (Long) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_MEDIO_TARJETA)
                            .setParameter("tipoIdentificacion",tipoIdentificacion)
                            .setParameter("numeroIdentificacion",numeroIdentificacion)
                            .getSingleResult();
        } catch (Exception e) {
            logger.error("esta monda fallo");
            logger.error(e);
            return null;
        }
    }

    private MedioDePagoModeloDTO consultarMedioDPagoPorId(Long id){
        return (MedioDePagoModeloDTO) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIO_DE_PAGO_DETALLE_BANDEJA)
                                                    .setParameter("idMedio", id)
                                                    .getSingleResult();
    }

    @Override
    public List<GestionDeTransaccionesDTO> consultarGestionDeTransacciones(){
        logger.info("inicia consultarGestionDeTransacciones()");
        try {
            List<GestionDeTransaccionesDTO> transacciones = (List<GestionDeTransaccionesDTO>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_GESTION_TRANSACCIONES)
                                                                .getResultList();
            for(GestionDeTransaccionesDTO transaccion : transacciones){
                if(transaccion.getIdMedioDePagoDestino() != null){
                    MedioDePagoModeloDTO medioDestinoDTO = consultarMedioDPagoPorId(transaccion.getIdMedioDePagoDestino());
                    transaccion.setMedioDePagoDestino(medioDestinoDTO);
                }
                if(transaccion.getIdMedioDePagoOrigen() != null){
                    MedioDePagoModeloDTO medioOrigenDTO = consultarMedioDPagoPorId(transaccion.getIdMedioDePagoOrigen());
                    transaccion.setMedioDePagoOrigen(medioOrigenDTO);
                }
            }
            return transacciones;
        } catch (Exception e) {
            logger.error("fallo el consultar la gestion de transacciones");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<TipoMedioDePagoEnum> consultarMediosDeTrasladoAdminSubsidio(Long idAdminSubsidio) {
        String firmaMetodo = "ConsultasModeloCore.consultarMediosDeTrasladoAdminSubsidio(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<TipoMedioDePagoEnum> mediosDePago = null;

        try {
            mediosDePago = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIOS_DE_PAGO_TRASLADO_ADMINISTRADOR_SUBSIDIO,
                            TipoMedioDePagoEnum.class)
                    .setParameter("idAdminSubsidio", idAdminSubsidio).getResultList();
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        //siempre se muestra por defecto efectivo
        mediosDePago.add(TipoMedioDePagoEnum.EFECTIVO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return mediosDePago;
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> listadoValoresRetirosIntermedios(String tipoIdAdmin,String numeroIdAdmin,String usuario,String idTransaccionTercerPagador,String idPuntoCobro){
        String firmaMetodo = "ConsultasModeloCore.listadoRetiros(String, String, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<CuentaAdministradorSubsidioDTO> listadoTransaccionesRetiro = new ArrayList<>();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "CO"));
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');

        // Define el patrón y ajusta los símbolos
        DecimalFormat currencyFormat = new DecimalFormat("#,##0.00", symbols);

        try{
            List<CuentaAdministradorSubsidioDTO> transaccionesRetiro = new ArrayList<>();
            List<Object[]> retirosObject = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTADO_TRANSACCIONES_RETIRO_INTERMEDIO)
                    .setParameter("idTransaccion", idTransaccionTercerPagador)
                    .setParameter("usuario", usuario)
                    .setParameter("idPuntoCobro", idPuntoCobro)
                    .getResultList();

            for (Object[] transaccionRetiro : retirosObject) {
                CuentaAdministradorSubsidioDTO retiro = new CuentaAdministradorSubsidioDTO();
                retiro.setNumeroIdAdminSubsidio(transaccionRetiro[0] != null ? transaccionRetiro[0].toString() : null);
                retiro.setTipoIdAdminSubsidio(transaccionRetiro[1] != null ? TipoIdentificacionEnum.valueOf(transaccionRetiro[1].toString()) : null);
                retiro.setNombresApellidosAdminSubsidio(transaccionRetiro[2] != null ? transaccionRetiro[2].toString() : null);
                retiro.setFechaYHoraTransaccionRetiro(transaccionRetiro[3] != null ? transaccionRetiro[3].toString() : null);
                retiro.setIdPuntoDeCobro(transaccionRetiro[4] != null ? transaccionRetiro[4].toString() : null);
                retiro.setIdTransaccionTerceroPagador(transaccionRetiro[5] != null ? transaccionRetiro[5].toString() : null);
                retiro.setIdTransaccionRetiro(transaccionRetiro[6] != null ? Long.valueOf(transaccionRetiro[6].toString()) : null);
                retiro.setValorRealTransaccionRetiro(transaccionRetiro[7] != null ? currencyFormat.format(new BigDecimal(transaccionRetiro[7].toString())) : null);
                retiro.setEstadoTransaccionRetiro(transaccionRetiro[8] != null ? transaccionRetiro[8].toString() : null);
                transaccionesRetiro.add(retiro);
            }
            try{
                if(transaccionesRetiro.isEmpty() || transaccionesRetiro.size()<0){
                    CuentaAdministradorSubsidioDTO retiro = new CuentaAdministradorSubsidioDTO();
                    String parametrosOut = (String) entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETROS_OUT_REGISTRO_OPERACION)
                        .setParameter("idPuntoCobro",idPuntoCobro)
                        .setParameter("usuario",usuario)
                        .setParameter("tipoIdAdmin",tipoIdAdmin)
                        .setParameter("numeroIdAdmin",numeroIdAdmin)
                        .setParameter("idTransaccionTercerPagador",idTransaccionTercerPagador)
                        .getSingleResult();
                    retiro.setParametrosOutRegistroOperacion(parametrosOut);                  
                    transaccionesRetiro.add(retiro);
                }
                listadoTransaccionesRetiro.addAll(transaccionesRetiro);
            }catch(NoResultException e){
                logger.error("Finaliza listadoValoresRetirosIntermedios",e);
                return listadoTransaccionesRetiro;    
            }
        } catch (Exception e) {
            logger.error("Finaliza listadoValoresRetirosIntermedios",e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listadoTransaccionesRetiro;
    }

    @Override
    public void actualizarListaCuentaAdministradorSubsidio(String estado, List<Long> listId, String estadoOperacion)  {
        String firmaMetodo = "ConsultasModeloCore.actualizarListaCuentaAdministradorSubsidio(EstadoTransaccionSubsidioEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        logger.info("listId " + listId.size());
        //try {
            if (!listId.isEmpty()) {
                entityManagerCore.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_LISTA_CUENTASADMIN)
                        .setParameter("estado", estado)
                        .setParameter("listIds", listId)
                        .setParameter("estadoOperacion", estadoOperacion)
                        .executeUpdate();
            }else {
                logger.info("no hay nada para actualizar " + estado);
            }
        /*} catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }*/

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    @Override
    public void actualizarEstadoTransaccionRetiro(String estadoTransaccion, Long id)  {
        String firmaMetodo = "ConsultasModeloCore.actualizarEstadoOperacionListaCuentaAdministradorSubsidio(EstadoTransaccionSubsidioEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        logger.info("listId " + id);
        //try {
        if (id != null && !id.equals("")) {
            entityManagerCore.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_LISTA_CUENTASADMIN_ESTADOOPERACION)
                    .setParameter("estado", estadoTransaccion)
                    .setParameter("id", id)
                    .executeUpdate();
        }else {
            logger.info("no hay nada para actualizar " + estadoTransaccion);
        }
        /*} catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }*/

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    @Override
    public Integer consultarTransaccionProceso(String tipoIdAdmin, String numeroIdAdmin) {
        Object valor = null;
        try {
            valor = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_TRANSACCION_EN_PROCESO)
                    .setParameter("tipoIdAdmin", tipoIdAdmin)
                    .setParameter("numeroIdAdmin", numeroIdAdmin).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Integer) valor;
    }

    @Override
    public List<CuentaAdministradorSubsidio> consultarAbonosRelacionadosRetiro(Long idRetiro) {
        List<CuentaAdministradorSubsidio> cuentas = new ArrayList<>();
        try {
            cuentas = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ABONOS_RELACIONADOS_RETIRO)
                    .setParameter("idRetiro", idRetiro).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            cuentas = new ArrayList<>();
        }
        return cuentas;
    }

    @Override
    public Object registrarRetiroSP(String tipoIdentificacion, String numeroIdentificacion, Long valorSolicitado, String usuario, String idTransaccionTercerPagador, String departamento, String municipio, String idPuntoCobro, String usuarioGenesys){
        String firmaMetodo = "ConsultasModeloCore.registrarRetiroSP()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Object resp = null;
        try {
            StoredProcedureQuery query = entityManagerCore
                    .createNamedStoredProcedureQuery(NamedQueriesConstants.REGISTRAR_RETIRO);
            query.setParameter("tipoIdAdmin", tipoIdentificacion);
            query.setParameter("numeroIdAdmin", numeroIdentificacion);
            query.setParameter("valorSolicitado", valorSolicitado);
            query.setParameter("usuario", usuario);
            query.setParameter("idTransaccionTercerPagador", idTransaccionTercerPagador);
            query.setParameter("departamento", departamento);
            query.setParameter("municipio", municipio);
            query.setParameter("idPuntoCobro", idPuntoCobro);
            query.setParameter("usuarioGenesys", usuarioGenesys);
            resp =  query.getSingleResult();
            logger.info("prueba pagos respuesta " + resp);

        } catch (Exception e) {
            resp = 0;
            logger.info(" :: Hubo un error en el SP: " + e);
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resp;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> listadoAbonosIntermedios(String idTransaccionTerceroPagador,String nombreTerceroPagador, String idPuntoCobro) {
        String firmaMetodo = "ConsultasModeloCore.listadoRetiros(String, String, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<CuentaAdministradorSubsidioDTO> listadoTransaccionesRetiro = new ArrayList<>();

        try{
            List<CuentaAdministradorSubsidioDTO> transaccionesRetiro = new ArrayList<>();
            List<Object[]> retirosObject = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTADO_TRANSACCIONES_RETIRO_INTERMEDIOS)
                    .setParameter("idTransaccion", idTransaccionTerceroPagador)
                    .setParameter("usuario", nombreTerceroPagador)
                    .setParameter("idPuntoCobro", idPuntoCobro)
                    .getResultList();

            for (Object[] transaccionRetiro : retirosObject) {
                logger.warn("cas id "+transaccionRetiro[0].toString());
                logger.warn("estado abono"+transaccionRetiro[1].toString());
                CuentaAdministradorSubsidioDTO retiro = new CuentaAdministradorSubsidioDTO();
                retiro.setIdCuentaAdministradorSubsidio(transaccionRetiro[0] != null ? Long.valueOf(transaccionRetiro[0].toString()) : null);
                retiro.setEstadoTransaccion(transaccionRetiro[1] != null ? EstadoTransaccionSubsidioEnum.valueOf(transaccionRetiro[1].toString()): null);
                transaccionesRetiro.add(retiro);
            }
            listadoTransaccionesRetiro.addAll(transaccionesRetiro);

        } catch (Exception e) {
            logger.error("Finaliza listadoValoresRetiros",e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listadoTransaccionesRetiro;
    }

    public void cambioEstadoCuentasAdminSubsidio (List<Long> idCuentaAdminList, String usuarioNombre){
        String firmaMetodo = "ConsultasModeloCore.cambioEstadoCuentasAdminSubsidio(List<CuentaAdministradorSubsidioDTO>)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try{
            if (idCuentaAdminList != null || !idCuentaAdminList.isEmpty()) {
                for (Long idCuenta : idCuentaAdminList) {
                    CuentaAdministradorSubsidioDTO cuentaDTO = consultarCuentaAdministradorSubsidio(idCuenta);
                    CuentaAdministradorSubsidio cuentaAdmin = cuentaDTO.convertToEntity();
                    if (cuentaDTO.getEstadoTransaccion().equals(EstadoTransaccionSubsidioEnum.APLICADO)) {
                        cuentaAdmin.setFechaHoraUltimaModificacion(new Date());
                        cuentaAdmin.setUsuarioUltimaModificacion(usuarioNombre);
                        cuentaAdmin.setEstadoTransaccionSubsidio(EstadoTransaccionSubsidioEnum.EN_TRAMITE);
                        entityManagerCore.merge(cuentaAdmin);
                    } else if (cuentaDTO.getEstadoTransaccion().equals(EstadoTransaccionSubsidioEnum.EN_TRAMITE)) {
                        cuentaAdmin.setFechaHoraUltimaModificacion(new Date());
                        cuentaAdmin.setUsuarioUltimaModificacion(usuarioNombre);
                        cuentaAdmin.setEstadoTransaccionSubsidio(EstadoTransaccionSubsidioEnum.APLICADO);
                        entityManagerCore.merge(cuentaAdmin);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Finaliza listadoValoresRetiros",e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Long consultarTransaccionesTodosFiltrosSPCount(UriInfo uriInfo, HttpServletResponse response,
                                                          DetalleTransaccionAsignadoConsultadoDTO detallesTransacciones) {
        String firmaServicio = "ConsultasModeloCore.consultarTransaccionesTodosFiltrosSPCount(TransaccionConsultada)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Date fechaRangoInicial;
        Date fechaRangoFinal;
        String filtroFechas = "";

        if (detallesTransacciones.getFechaInicio() != null && detallesTransacciones.getFechaFin() != null) {
            fechaRangoInicial = new Date(detallesTransacciones.getFechaInicio());
            fechaRangoFinal = new Date(detallesTransacciones.getFechaFin());
        } else {
            fechaRangoInicial = new Date();
            fechaRangoFinal = new Date();
            filtroFechas = null;
        }

        logger.info("filtroFechas " +filtroFechas);
        logger.info("fechaRangoFinal " +fechaRangoFinal);
        logger.info("fechaRangoInicial " +fechaRangoInicial);
        logger.info("estadoTransaccion " +detallesTransacciones.getEstadoTransaccion());
        logger.info("tipoTransaccion " +detallesTransacciones.getTipoTransaccion());
        logger.info("medioDePago " +detallesTransacciones.getMedioDePago());

        List<Long> sqlHack = new ArrayList<Long>();
        sqlHack.add(0L);
        Long resultado = 0L;
        try {
            resultado = ((Number) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_ADMIN_SUBSIDIO_PAGINADA_COUNT2)
                    .setParameter("filtroFechas", filtroFechas)
                    .setParameter("fechaInicio", CalendarUtils.truncarHora(fechaRangoInicial))
                    .setParameter("fechaFin", CalendarUtils.truncarHoraMaxima(fechaRangoFinal))
                    .setParameter("estadoTransaccion", detallesTransacciones.getEstadoTransaccion() != null ?
                            detallesTransacciones.getEstadoTransaccion().name() : null)
                    .setParameter("tipoTransaccion", (detallesTransacciones.getTipoTransaccion() != null) ?
                            detallesTransacciones.getTipoTransaccion().name() : null)
                    .setParameter("medioDePago", detallesTransacciones.getMedioDePago() != null ?
                            detallesTransacciones.getMedioDePago().name() : null)
                    .setParameter("sizelstidEmpleadores", (detallesTransacciones.getListaIdEmpleadores() == null ||
                            detallesTransacciones.getListaIdEmpleadores().isEmpty()) ? 0 : 1)
                    .setParameter("lstidEmpleadores", (detallesTransacciones.getListaIdEmpleadores() == null ||
                            detallesTransacciones.getListaIdEmpleadores().isEmpty()) ? sqlHack :
                            detallesTransacciones.getListaIdEmpleadores())
                    .setParameter("sizelstidAfiliadoPrincipal", (detallesTransacciones.getListaIdAfiliadosPrincipales() == null ||
                            detallesTransacciones.getListaIdAfiliadosPrincipales().isEmpty()) ? 0 : 1)
                    .setParameter("lstidAfiliadoPrincipal", (detallesTransacciones.getListaIdAfiliadosPrincipales() == null ||
                            detallesTransacciones.getListaIdAfiliadosPrincipales().isEmpty()) ? sqlHack :
                            detallesTransacciones.getListaIdAfiliadosPrincipales())
                    .setParameter("sizelstidBeneficiarioDetalle", (detallesTransacciones.getListaIdBeneficiarios() == null ||
                            detallesTransacciones.getListaIdBeneficiarios().isEmpty()) ? 0 : 1)
                    .setParameter("lstidBeneficiarioDetalle", (detallesTransacciones.getListaIdBeneficiarios() == null ||
                            detallesTransacciones.getListaIdBeneficiarios().isEmpty()) ? sqlHack :
                            detallesTransacciones.getListaIdBeneficiarios())
                    .setParameter("sizelstidAdministradorSubsidio", (detallesTransacciones.getListaIdAdminSubsidios() == null ||
                            detallesTransacciones.getListaIdAdminSubsidios().isEmpty()) ? 0 : 1)
                    .setParameter("lstidAdministradorSubsidio", (detallesTransacciones.getListaIdAdminSubsidios() == null ||
                            detallesTransacciones.getListaIdAdminSubsidios().isEmpty()) ? sqlHack :
                            detallesTransacciones.getListaIdAdminSubsidios())
                    .getSingleResult()).longValue();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultado;
    }
}

