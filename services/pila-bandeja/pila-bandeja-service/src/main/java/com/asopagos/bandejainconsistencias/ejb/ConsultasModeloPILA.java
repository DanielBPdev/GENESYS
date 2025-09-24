package com.asopagos.bandejainconsistencias.ejb;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.rmi.CORBA.UtilDelegate;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;
import org.apache.commons.lang.StringUtils;
import com.asopagos.bandejainconsistencias.constants.ConstantesPilaBandeja;
import com.asopagos.bandejainconsistencias.constants.NamedQueriesConstants;
import com.asopagos.bandejainconsistencias.dto.ActivosCorreccionIdAportanteDTO;
import com.asopagos.bandejainconsistencias.dto.ConsultaDatosExtraInconsistenciaDTO;
import com.asopagos.bandejainconsistencias.dto.IdentificadorDocumentoDTO;
import com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO;
import com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA;
import com.asopagos.bandejainconsistencias.service.ejb.PilaBandejaBusiness;
import com.asopagos.bandejainconsistencias.util.FuncionesUtilitarias;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.IndicePlanillaModeloDTO;
import com.asopagos.dto.modelo.TemNovedadModeloDTO;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoAPRegistro1;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoARegistro1;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoFRegistro6;
import com.asopagos.entidades.pila.procesamiento.ErrorValidacionLog;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloque;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloqueOF;
import com.asopagos.entidades.pila.procesamiento.HistorialEstadoBloque;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.entidades.pila.procesamiento.SolicitudCambioNumIdentAportante;
import com.asopagos.enumeraciones.aportes.TipoInconsistenciasEnum;
import com.asopagos.enumeraciones.aportes.TipoOperadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.AccionCorreccionPilaEnum;
import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.CamposNombreArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoConciliacionArchivoFEnum;
import com.asopagos.enumeraciones.pila.EstadoGestionInconsistenciaEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.RazonRechazoSolicitudCambioIdenEnum;
import com.asopagos.enumeraciones.pila.SubTipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pagination.QueryBuilder;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.util.CalendarUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.asopagos.pila.clients.ConsultarIndicePlanillaEntidad;


/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de información en
 * el modelo de datos de PILA <br/>
 * <b>Módulo:</b> Asopagos - HU-211-401 y HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 * @author <a href="mailto:anbuitrago@heinsohn.com.co">Andres Felipe Buitrago Feria</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson A. Arboleda</a>
 */
@Stateless
public class ConsultasModeloPILA implements IConsultasModeloPILA, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloPILA.class);

    /** Entity Manager */
    @PersistenceContext(unitName = "pila_PU")
    private EntityManager entityManager;

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#obtenerRegistroTipoA(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public PilaArchivoARegistro1 obtenerRegistroTipoA(Long numeroPlanilla, TipoArchivoPilaEnum tipoArchivoI, String operadorInformacion) {
        PilaArchivoARegistro1 registroA = null;
        try {
            registroA = entityManager.createNamedQuery(NamedQueriesConstants.BUSQUEDA_REGISTRO_A, PilaArchivoARegistro1.class)
                    .setParameter(ConstantesPilaBandeja.NUMERO_PLANILLA, numeroPlanilla)
                    .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO_A, obtenerTipoArchivoA(tipoArchivoI))
                    .setParameter(ConstantesPilaBandeja.OPERADOR_INFORMACION, operadorInformacion).getSingleResult();
            return registroA;
        } catch (NoResultException nre) {
            logger.debug("no se encontraron registros A");
            return null;
        }
    }

    /**
     * Retorna la pareja A de un archivo I
     * @return
     * @author rarboleda
     */
    private TipoArchivoPilaEnum obtenerTipoArchivoA(TipoArchivoPilaEnum tipoArchivoI) {
        String firmaMetodo = "ConsultasModeloPILA.obtenerIdIndicePlanillaArchivoI(Long, TipoArchivoPilaEnum)";
        logger.debug("Inicia " + firmaMetodo);

        switch (tipoArchivoI) {
            case ARCHIVO_OI_I:
                return TipoArchivoPilaEnum.ARCHIVO_OI_A;
            case ARCHIVO_OI_IR:
                return TipoArchivoPilaEnum.ARCHIVO_OI_AR;
            case ARCHIVO_OI_IPR:
                return TipoArchivoPilaEnum.ARCHIVO_OI_APR;
            case ARCHIVO_OI_IP:
                return TipoArchivoPilaEnum.ARCHIVO_OI_AP;
            default:
                break;
        }
        logger.debug("Finaliza " + firmaMetodo);
        return null;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#obtenerRegistroTipoAP(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public PilaArchivoAPRegistro1 obtenerRegistroTipoAP(Long numeroPlanilla, TipoArchivoPilaEnum tipoArchivoI, String operadorInformacion) {
        PilaArchivoAPRegistro1 registroAP = null;
        try {
            registroAP = entityManager.createNamedQuery(NamedQueriesConstants.BUSQUEDA_REGISTRO_AP, PilaArchivoAPRegistro1.class)
                    .setParameter(ConstantesPilaBandeja.NUMERO_PLANILLA, numeroPlanilla)
                    .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO_A, obtenerTipoArchivoA(tipoArchivoI)).getSingleResult();
            return registroAP;
        } catch (NoResultException nre) {
            logger.debug("no se encontraron registros AP");
            return null;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#obtenerIdIndicePlanillaArchivoI(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public IndicePlanilla obtenerIdIndicePlanillaArchivoI(Long idPlanilla, TipoArchivoPilaEnum tipoArchivo) {
        String firmaMetodo = "ConsultasModeloPILA.obtenerIdIndicePlanillaArchivoI(Long, TipoArchivoPilaEnum)";
        logger.debug("Inicia " + firmaMetodo);

        IndicePlanilla indice = null;
        try {
            indice = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ARCHIVO_I_ID, IndicePlanilla.class)
                    .setParameter(ConstantesPilaBandeja.ID_PLANILLA, idPlanilla)
                    .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO, tipoArchivo).getSingleResult();
            logger.debug("Finaliza " + firmaMetodo);
            return indice;
        } catch (NoResultException nre) {
            logger.debug("No se encontraron resultados");
            logger.debug("Finaliza " + firmaMetodo);
            return null;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#modificarEstadosArchivo(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public IndicePlanilla modificarEstadosArchivo(IndicePlanilla indice, String nombreUsuario) {
        String firmaMetodo = "ConsultasModeloPILA.modificarEstadosArchivo(IndicePlanilla, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        IndicePlanilla ind = null;
        List<ErrorValidacionLog> errores = new ArrayList<>();

        // En error validación log, poner el estado de la inconsistencia como gestionada
        errores = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ERROR_VALIDACION_LOG_ID_PLANILLA, ErrorValidacionLog.class)
                .setParameter(ConstantesPilaBandeja.ID_PLANILLA, indice.getId()).getResultList();
    
        for (ErrorValidacionLog error : errores) {
            error = entityManager.merge(error);
            error.setEstadoInconsistencia(EstadoGestionInconsistenciaEnum.INCONSISTENCIA_GESTIONADA);
        }

        /* En índice planilla cambiar el estado a archivo consistente */
        ind = entityManager.merge(indice);
        ind.setEstadoArchivo(EstadoProcesoArchivoEnum.ARCHIVO_CONSISTENTE);

        /*
         * En estado por bloque poner el ESTADO del BLOQUE 5 en archivo consistente y
         * la ACCIÓN DE BLOQUE 5 en ejecutar bloque 6
         */
        try {
            EstadoArchivoPorBloque estado = entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_ESTADO_BLOQUE_PLANILLA, EstadoArchivoPorBloque.class)
                    .setParameter(ConstantesPilaBandeja.ID_PLANILLA, indice.getId()).getSingleResult();
            estado = entityManager.merge(estado);

            /* Se prepara la entrada en histórico de estad */
            HistorialEstadoBloque historialEstado = new HistorialEstadoBloque();
            historialEstado.setIdIndicePlanilla(indice.getId());
            historialEstado.setEstado(estado.getEstadoBloque5());
            historialEstado.setAccion(estado.getAccionBloque5());
            historialEstado.setFechaEstado(estado.getFechaBloque5());
            historialEstado.setBloque(BloqueValidacionEnum.BLOQUE_5_OI);
            historialEstado.setUsuarioEspecifico(nombreUsuario);
            historialEstado.setClaseUsuario((short) 2); 

            estado.setFechaBloque5(new Date());
            estado.setEstadoBloque5(EstadoProcesoArchivoEnum.ARCHIVO_CONSISTENTE);
            estado.setAccionBloque5(AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_6);

            entityManager.persist(historialEstado);
        } catch (NoResultException nre) {
            logger.error(firmaMetodo + " :: No se logro acualizar el estado del archivo, posible inconsistencia en el estado");
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return ind;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#consultarArchivosInconsistentesResumen(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, com.asopagos.enumeraciones.aportes.TipoOperadorEnum,
     *      java.lang.Short)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InconsistenciaDTO> consultarArchivosInconsistentesResumen(TipoIdentificacionEnum tipoIdentificacion, String numeroPlanilla,
            Long fechaInicio, Long fechaFin, String numeroIdentificacion, TipoOperadorEnum operador, Short digitoVerificacion, String bloqueValidacion, Boolean ocultarBlq5) {

        String firmaMetodo = "consultarArchivosInconsistentesResumen(TipoIdentificacionEnum, String, Long, Long, String, TipoOperadorEnum, Short)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<InconsistenciaDTO> resultI = null;
        List<InconsistenciaDTO> resultF = null;
        List<InconsistenciaDTO> result = new ArrayList<>();

        // se toma el valor PILA del tipo de identificación seleccionado
        String tipoIdentificacionValorPila = tipoIdentificacion != null ? tipoIdentificacion.getValorEnPILA() : null;

        //
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");	
    	Calendar calendar = new GregorianCalendar(1970,0,01);
    	System.out.println(sdf.format(calendar.getTime()));
    	
        //fechas para la busqueda de pilaBandeja
        Date fechaI = fechaInicio != null ? CalendarUtils.truncarHora(new Date(fechaInicio)) : CalendarUtils.truncarHora(calendar.getTime());
        Date fechaF = fechaFin != null ? CalendarUtils.truncarHora(new Date(fechaFin)) : CalendarUtils.truncarHora(new Date());
        
        logger.info("fechaI "+fechaI);
        logger.info("fechaF "+fechaF);

        // banderas para establecer sí se consulta OI, OF o ambos
        Boolean consultaOI = false;
        Boolean consultaOF = false;
        if (operador == null) {
            consultaOI = true;
            consultaOF = true;
        }
        else if (TipoOperadorEnum.OPERADOR_INFORMACION.equals(operador)) {
            consultaOI = true;
        }
        else if (TipoOperadorEnum.OPERADOR_FINANCIERO.equals(operador)) {
            consultaOF = true;
        }
        
        if (consultaOI) {
        	
        	String bloqueValidacionOI = null;
        	
        	if(bloqueValidacion != null) {
        		if(bloqueValidacion.equals("BLOQUE_0")) {
        			bloqueValidacionOI = BloqueValidacionEnum.BLOQUE_0_OI.name();
        		}else if(bloqueValidacion.equals("BLOQUE_1")) {
        			bloqueValidacionOI = BloqueValidacionEnum.BLOQUE_1_OI.name();
        		}else if(bloqueValidacion.equals("BLOQUE_2")) {
        			bloqueValidacionOI = BloqueValidacionEnum.BLOQUE_2_OI.name();
        		}else if(bloqueValidacion.equals("BLOQUE_3")) {
        			bloqueValidacionOI = BloqueValidacionEnum.BLOQUE_3_OI.name();
        		}else if(bloqueValidacion.equals("BLOQUE_5")) {
        			bloqueValidacionOI = BloqueValidacionEnum.BLOQUE_5_OI.name();
        		}else if(bloqueValidacion.equals("BLOQUE_6")) {
        			bloqueValidacionOI = BloqueValidacionEnum.BLOQUE_6_OI.name();
        		}
        	}
        	
            List<String> estadosOI = obtenerListadoEstadosInconsistentes(1);
            
            String namedQueryOI = NamedQueriesConstants.CONSULTAR_BANDEJA_I;
            
            if(ocultarBlq5) {
            	namedQueryOI = NamedQueriesConstants.CONSULTAR_BANDEJA_I_SIN_BLQ5;
            }
            
            resultI = entityManager.createNamedQuery(namedQueryOI, InconsistenciaDTO.class)
                    .setParameter(ConstantesPilaBandeja.TIPO_IDENTIFICACION, tipoIdentificacionValorPila)
                    .setParameter(ConstantesPilaBandeja.NUMERO_IDENTIFICACION, numeroIdentificacion)
                    .setParameter(ConstantesPilaBandeja.FECHA_INICIO, fechaI)
                    .setParameter(ConstantesPilaBandeja.FECHA_FIN, fechaF)
                    .setParameter(ConstantesPilaBandeja.NUMERO_PLANILLA, numeroPlanilla)
                    .setParameter("estadosOI", estadosOI)
                    .setParameter("bloqueValidacion", bloqueValidacionOI)
                    .getResultList();
            
            for(String s : estadosOI) {
            	logger.info("estadosOI "+s);
            }
            
        }
        
        Boolean consultarOFBloques = true;
        
        if(bloqueValidacion != null && (bloqueValidacion.equals("BLOQUE_2") || bloqueValidacion.equals("BLOQUE_3")
        		|| bloqueValidacion.equals("BLOQUE_5") || bloqueValidacion.equals("BLOQUE_6"))) {
        	consultarOFBloques = false;
        }

        if (consultaOF && consultarOFBloques) {
            List<String> estadosOF = obtenerListadoEstadosInconsistentes(2);
            List<String> estadosF6 = obtenerListadoEstadosInconsistentes(3);
            
            for(String s : estadosOF) {
            	logger.info("estadosOF "+s);
            }
            for(String s : estadosF6) {
            	logger.info("estadosF6 "+s);
            }
            
            String bloqueValidacionOF = null;
        	
        	if(bloqueValidacion != null) {
        		if(bloqueValidacion.equals("BLOQUE_0")) {
        			bloqueValidacionOF = BloqueValidacionEnum.BLOQUE_0_OF.name();
        		}else if(bloqueValidacion.equals("BLOQUE_1")) {
        			bloqueValidacionOF = BloqueValidacionEnum.BLOQUE_1_OF.name();
        		}
        	}

            resultF = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BANDEJA_F, InconsistenciaDTO.class)
                    .setParameter(ConstantesPilaBandeja.NUMERO_IDENTIFICACION, numeroIdentificacion)
                    .setParameter(ConstantesPilaBandeja.FECHA_INICIO, fechaI).setParameter(ConstantesPilaBandeja.FECHA_FIN, fechaF)
                    .setParameter(ConstantesPilaBandeja.NUMERO_PLANILLA, numeroPlanilla)
                    .setParameter("estadosOF", estadosOF)
                    .setParameter("estadosF6", estadosF6)
                    .setParameter("bloqueValidacion", bloqueValidacionOF)
                    .getResultList();
        }

        result.addAll(resultI == null ? Collections.emptyList() : resultI);
        result.addAll(resultF == null ? Collections.emptyList() : resultF);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#accionBandejaInconsistencias(com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO)
     */
    @Override
    public List<TipoInconsistenciasEnum> accionBandejaInconsistencias(InconsistenciaDTO inconsistencia) {
        // lista que contendria las pestañas que estarian activas en pantalla
        List<BloqueValidacionEnum> bloques = new ArrayList<>();

        logger.debug(
                "Inicia accionBandejaInconsistencias(List<TipoInconsistenciasEnum>, TipoInconsistenciasEnum):Inicia Busqueda de los tipos de inconsistencias");

        try {
            // se diferencia si la consulta de las pestañas va a ser para
            // operadores financieros o de informacion
            if (inconsistencia.getTipoOperador() == TipoOperadorEnum.OPERADOR_INFORMACION) {
                bloques = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_PESTANAS_INCONSISTENCIAS, BloqueValidacionEnum.class)
                        .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, inconsistencia.getIndicePlanilla())
                        .setParameter(ConstantesPilaBandeja.ESTADO_INCONSISTENCIA, EstadoGestionInconsistenciaEnum.PENDIENTE_GESTION)
                        .getResultList();

                return PilaBandejaBusiness.generarPestanas(bloques);
            }
            else {
                bloques = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_PESTANAS_INCONSISTENCIAS_F, BloqueValidacionEnum.class)
                        .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, inconsistencia.getIndicePlanilla()).getResultList();

                return PilaBandejaBusiness.generarPestanas(bloques);
            }

        } catch (Exception e) {
            logger.error("No es posible realizar la consulta de pestañas", e);
            logger.debug("Finaliza consultarArchivosInconsistentesResumen(List<TipoInconsistenciasEnum>, TipoInconsistenciasEnum)");

            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#consultaDetalleInconsistenciasBandeja
     *      (com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO, java.util.List, javax.ws.rs.core.UriInfo, 
     *      javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InconsistenciaDTO> consultaDetalleInconsistenciasBandeja(InconsistenciaDTO inconsistencia,
            List<BloqueValidacionEnum> bloquesConsulta, UriInfo uri, HttpServletResponse response) {
        String firmaMetodo = "PilaBandejaBusiness.consultaDetalleInconsistenciasBandeja(InconsistenciaDTO, List<BloqueValidacionEnum>, "
                + "UriInfo, HttpServletResponse)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<InconsistenciaDTO> result = null;
        
        Query query = null;
        
        try{
            QueryBuilder queryBuilder = new QueryBuilder(entityManager, uri, response);
            queryBuilder.addParam(ConstantesPilaBandeja.ID_INDICE_PLANILLA, inconsistencia.getIndicePlanilla());
            queryBuilder.addParam(ConstantesPilaBandeja.BLOQUE, bloquesConsulta);
            
            if (!TipoArchivoPilaEnum.ARCHIVO_OF.equals(inconsistencia.getTipoArchivo())) {
                query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE,
                        NamedQueriesConstants.CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE_TOTAL);
            }
            else {
                query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE_F,
                        NamedQueriesConstants.CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE_F_TOTAL);
            }
            
            result = (List<InconsistenciaDTO>) query.getResultList();
            
            // se completa la información de la inconsistencia
            result = completarInformacionInconsistencia(result);
        }catch(NoResultException e){
            result = Collections.emptyList();
        }catch(Exception e){
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Método que completa la información de las inconsistencias presentadas
     * 
     * @param result
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<InconsistenciaDTO> completarInformacionInconsistencia(List<InconsistenciaDTO> inconsistencias) {
        String firmaMetodo = "PilaBandejaBusiness.completarInformacionInconsistencia(List<InconsistenciaDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        List<InconsistenciaDTO> result = inconsistencias;
        
        // se toma el listado de las inconsistencias para preparar los DTO de la consulta
        Map<Long, ConsultaDatosExtraInconsistenciaDTO> mapaDeDatosConsulta = new HashMap<>();
        for (InconsistenciaDTO inconsistencia : result) {
            if (!TipoArchivoPilaEnum.ARCHIVO_OF.equals(inconsistencia.getTipoArchivo())
                    && !mapaDeDatosConsulta.containsKey(inconsistencia.getNumeroPlanilla())) {
                String codigoOI = (String) FuncionesUtilitarias.obtenerCampoNombreArchivo(inconsistencia.getTipoArchivo(),
                        CamposNombreArchivoEnum.CODIGO_OPERADOR_OI, inconsistencia.getNombreArchivo());
                String periodo = (String) FuncionesUtilitarias.obtenerCampoNombreArchivo(inconsistencia.getTipoArchivo(),
                        CamposNombreArchivoEnum.PERIODO_PAGO_OI, inconsistencia.getNombreArchivo());
                if(periodo != null && !periodo.isEmpty()){
                    periodo = periodo.replace("-", "");
                }
                String clave = inconsistencia.getNumeroPlanilla() + "_" + codigoOI + "_" + periodo;
                
                mapaDeDatosConsulta.put(inconsistencia.getNumeroPlanilla(),
                        new ConsultaDatosExtraInconsistenciaDTO(inconsistencia.getNumeroPlanilla(), clave));
            }
        }
        
        try{
            String jsonPayload;
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(Include.NON_NULL);
            jsonPayload = mapper.writeValueAsString(mapaDeDatosConsulta.values());
            
            List<Object[]> resultQuery = (List<Object[]>) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_ADICIONALES_INCONSISTENCIAS)
                    .setParameter("parametros", jsonPayload).getResultList();
            
            Map<Long, Map<String, Object>> mapaResultados = new HashMap<>();
            Map<String, Long> nombres = null;

            Long numPlanilla = null;
            Integer reprocesos = null;
            String nombreArchivo = null;
            Integer cantidad = null;
            Long idPlanilla = null;
            
            for (Object[] resultado : resultQuery) {
                numPlanilla = ((BigInteger) resultado[0]).longValue();
                reprocesos = (Integer) resultado[1];
                nombreArchivo = (String) resultado[2];
                cantidad = (Integer) resultado[3];
                idPlanilla = ((BigInteger) resultado[4]).longValue();
                
                Map<String, Object> mapaBase = null;
                if(!mapaResultados.containsKey(numPlanilla)){
                    mapaBase = new HashMap<>();
                    
                    nombres = new HashMap<>();
                    nombres.put(nombreArchivo, idPlanilla);
                    
                    mapaBase.put("nombres", nombres);
                    mapaBase.put("reprocesos", reprocesos.toString());
                    mapaBase.put("cantidad", cantidad.longValue());
                    
                    mapaResultados.put(numPlanilla, mapaBase);
                }else{
                    mapaBase = mapaResultados.get(numPlanilla);
                    nombres = (Map<String, Long>) mapaBase.get("nombres");
                    nombres.put(nombreArchivo, idPlanilla);
                }
            }
            
            // con el mapa de resultados, se actualiza la información faltante de los DTO de las inconsistencias
            Map<String, Object> mapaBase = null;
            
            for (InconsistenciaDTO inconsistencia : result) {
                mapaBase = mapaResultados.get(inconsistencia.getNumeroPlanilla());
                if (mapaBase == null || mapaBase.isEmpty()) {
                    continue;
                }
                inconsistencia.setNumeroReprocesos((String) mapaBase.get("reprocesos"));
                inconsistencia.setCantidadRegistros6ArchivoF((Long) mapaBase.get("cantidad"));
                
                nombres = (Map<String, Long>) mapaBase.get("nombres");
                
                for (String nombre : nombres.keySet()) {
                    TipoArchivoPilaEnum tipoNombre = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(
                            (String) FuncionesUtilitarias.obtenerCampoNombreArchivo(inconsistencia.getTipoArchivo(),
                                    CamposNombreArchivoEnum.TIPO_ARCHIVO_OI, nombre));
                    
                    if (!tipoNombre.equals(inconsistencia.getTipoArchivo())
                            && ((tipoNombre.isReproceso() && inconsistencia.getTipoArchivo().isReproceso())
                                    || (!tipoNombre.isReproceso() && !inconsistencia.getTipoArchivo().isReproceso()))) {
                        inconsistencia.setNombreArchivoPar(nombre);
                        inconsistencia.setIndicePlanillaPar(nombres.get(nombre));
                        break;
                    }
                }
            }
        }catch(Exception e){
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

//    /**
//     * (non-Javadoc)
//     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#accionBandejaDetalleInconsistencias(com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO,
//     *      com.asopagos.enumeraciones.aportes.TipoInconsistenciasEnum)
//     */
//    @Override
//    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
//    public List<InconsistenciaDTO> accionBandejaDetalleInconsistencias(InconsistenciaDTO inconsistencia,
//            TipoInconsistenciasEnum tipoInconsistencia) {
//        String firmaMetodo = "PilaBandejaBusiness.accionBandejaDetalleInconsistencias(InconsistenciaDTO, TipoInconsistenciasEnum)";
//        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
//
//        // lista que contendra los datos detallados de las inconsistencias
//        List<InconsistenciaDTO> result = new ArrayList<>();
//
//        try {
//            // Se establecen las posibilidades de establecer los detalles del
//            // archivo dependiendo del tipo de inconsistencia solicitado
//            if (TipoInconsistenciasEnum.ARCHIVO.equals(tipoInconsistencia)) {
//                // se condiciona dependiendo del tipo de archivo que se haya
//                // enviado a este punto
//                if (TipoArchivoPilaEnum.ARCHIVO_OF.equals(inconsistencia.getTipoArchivo())) {
//                    try {
//                        result.addAll(entityManager
//                                .createNamedQuery(NamedQueriesConstants.CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE_F,
//                                        InconsistenciaDTO.class)
//                                .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, inconsistencia.getIndicePlanilla())
//                                .setParameter(ConstantesPilaBandeja.BLOQUE, BloqueValidacionEnum.BLOQUE_0_OF)
//                                .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO, inconsistencia.getTipoArchivo()).getResultList());
//                        return establecerDetalleInconsistencia(result);
//
//                    } catch (Exception e) {
//                        logger.error(ConstantesPilaBandeja.ERROR_EN_CONSULTA, e);
//                        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
//                        throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                    }
//                }
//
//                try {
//                    result.addAll(
//                            entityManager
//                                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE,
//                                            InconsistenciaDTO.class)
//                                    .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, inconsistencia.getIndicePlanilla())
//                                    .setParameter(ConstantesPilaBandeja.BLOQUE, BloqueValidacionEnum.BLOQUE_0_OI)
//                                    .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO, inconsistencia.getTipoArchivo()).getResultList());
//                    return establecerDetalleInconsistencia(result);
//
//                } catch (Exception e) {
//                    logger.error(ConstantesPilaBandeja.ERROR_EN_CONSULTA, e);
//                    logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
//                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                }
//            }
//
//            if (tipoInconsistencia == TipoInconsistenciasEnum.ESTRUCTURA) {
//                logger.warn(firmaMetodo + " - Inicia la consulta de errores de estructura");
//                if (inconsistencia.getTipoArchivo() == TipoArchivoPilaEnum.ARCHIVO_OF) {
//                    try {
//                        logger.debug(
//                                "Inicia accionBandejaDetalleInconsistencias(InconsistenciaDTO inconsistencia,TipoInconsistenciasEnum tipoInconsistencia):Inicia Busqueda por archivo financiero ");
//
//                        result.addAll(entityManager
//                                .createNamedQuery(NamedQueriesConstants.CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE_F,
//                                        InconsistenciaDTO.class)
//                                .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, inconsistencia.getIndicePlanilla())
//                                .setParameter(ConstantesPilaBandeja.BLOQUE, BloqueValidacionEnum.BLOQUE_1_OF)
//                                .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO, inconsistencia.getTipoArchivo()).getResultList());
//
//                    } catch (Exception e) {
//                        logger.error(ConstantesPilaBandeja.ERROR_EN_CONSULTA, e);
//                        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
//                        throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                    }
//                }
//
//                try {
//                    logger.debug(
//                            "Inicia accionBandejaDetalleInconsistencias(InconsistenciaDTO inconsistencia,TipoInconsistenciasEnum tipoInconsistencia):Inicia Busqueda por archivos informacion ");
//
//                    result.addAll(
//                            entityManager
//                                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE,
//                                            InconsistenciaDTO.class)
//                                    .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, inconsistencia.getIndicePlanilla())
//                                    .setParameter(ConstantesPilaBandeja.BLOQUE, BloqueValidacionEnum.BLOQUE_1_OI)
//                                    .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO, inconsistencia.getTipoArchivo()).getResultList());
//
//                    result.addAll(
//                            entityManager
//                                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE,
//                                            InconsistenciaDTO.class)
//                                    .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, inconsistencia.getIndicePlanilla())
//                                    .setParameter(ConstantesPilaBandeja.BLOQUE, BloqueValidacionEnum.BLOQUE_2_OI)
//                                    .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO, inconsistencia.getTipoArchivo()).getResultList());
//
//                } catch (Exception e) {
//                    logger.error(ConstantesPilaBandeja.ERROR_EN_CONSULTA, e);
//                    logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
//                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                }
//
//                logger.warn(firmaMetodo + " - Finaliza la consulta de errores de estructura");
//                return establecerDetalleInconsistencia(result);
//            }
//
//            if (tipoInconsistencia == TipoInconsistenciasEnum.PAREJA_DE_ARCHIVOS) {
//                logger.debug(
//                        "Inicia accionBandejaDetalleInconsistencias(InconsistenciaDTO inconsistencia,TipoInconsistenciasEnum tipoInconsistencia):Tipo de inconsistencia pareja de archivos ");
//
//                if (inconsistencia.getEstadoArchivo() == EstadoProcesoArchivoEnum.PAREJA_DE_ARCHIVOS_EN_ESPERA) {
//
//                    return null;
//                }
//
//                if ((inconsistencia.getTipoArchivo() == TipoArchivoPilaEnum.ARCHIVO_OI_I)
//                        || (inconsistencia.getTipoArchivo() == TipoArchivoPilaEnum.ARCHIVO_OI_A)) {
//                    try {
//                        logger.debug(
//                                "Inicia accionBandejaDetalleInconsistencias(InconsistenciaDTO inconsistencia,TipoInconsistenciasEnum tipoInconsistencia):Inicia Busqueda por archivos I y A");
//
//                        result.addAll(
//                                entityManager
//                                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE,
//                                                InconsistenciaDTO.class)
//                                        .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, inconsistencia.getIndicePlanilla())
//                                        .setParameter(ConstantesPilaBandeja.BLOQUE, BloqueValidacionEnum.BLOQUE_3_OI)
//                                        .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO, TipoArchivoPilaEnum.ARCHIVO_OI_I)
//                                        .getResultList());
//
//                        result.addAll(
//                                entityManager
//                                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE,
//                                                InconsistenciaDTO.class)
//                                        .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, inconsistencia.getIndicePlanilla())
//                                        .setParameter(ConstantesPilaBandeja.BLOQUE, BloqueValidacionEnum.BLOQUE_3_OI)
//                                        .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO, TipoArchivoPilaEnum.ARCHIVO_OI_A)
//                                        .getResultList());
//                        return establecerDetalleInconsistencia(result);
//
//                    } catch (Exception e) {
//                        logger.error(ConstantesPilaBandeja.ERROR_EN_CONSULTA, e);
//                        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
//                        throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                    }
//                }
//
//                if ((inconsistencia.getTipoArchivo() == TipoArchivoPilaEnum.ARCHIVO_OI_IP)
//                        || (inconsistencia.getTipoArchivo() == TipoArchivoPilaEnum.ARCHIVO_OI_AP)) {
//                    try {
//                        logger.debug(
//                                "Inicia accionBandejaDetalleInconsistencias(InconsistenciaDTO inconsistencia,TipoInconsistenciasEnum tipoInconsistencia):Inicia Busqueda por archivos IP y AP");
//
//                        result.addAll(
//                                entityManager
//                                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE,
//                                                InconsistenciaDTO.class)
//                                        .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, inconsistencia.getIndicePlanilla())
//                                        .setParameter(ConstantesPilaBandeja.BLOQUE, BloqueValidacionEnum.BLOQUE_3_OI)
//                                        .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO, TipoArchivoPilaEnum.ARCHIVO_OI_IP)
//                                        .getResultList());
//
//                        result.addAll(
//                                entityManager
//                                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE,
//                                                InconsistenciaDTO.class)
//                                        .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, inconsistencia.getIndicePlanilla())
//                                        .setParameter(ConstantesPilaBandeja.BLOQUE, BloqueValidacionEnum.BLOQUE_3_OI)
//                                        .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO, TipoArchivoPilaEnum.ARCHIVO_OI_AP)
//                                        .getResultList());
//                        return establecerDetalleInconsistencia(result);
//
//                    } catch (Exception e) {
//                        logger.error(ConstantesPilaBandeja.ERROR_EN_CONSULTA, e);
//                        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
//                        throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                    }
//                }
//
//                if ((inconsistencia.getTipoArchivo() == TipoArchivoPilaEnum.ARCHIVO_OI_IPR)
//                        || (inconsistencia.getTipoArchivo() == TipoArchivoPilaEnum.ARCHIVO_OI_APR)) {
//                    try {
//                        logger.debug(
//                                "Inicia accionBandejaDetalleInconsistencias(InconsistenciaDTO inconsistencia,TipoInconsistenciasEnum tipoInconsistencia):Inicia Busqueda por archivo IPR y APR ");
//
//                        result.addAll(
//                                entityManager
//                                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE,
//                                                InconsistenciaDTO.class)
//                                        .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, inconsistencia.getIndicePlanilla())
//                                        .setParameter(ConstantesPilaBandeja.BLOQUE, BloqueValidacionEnum.BLOQUE_3_OI)
//                                        .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO, TipoArchivoPilaEnum.ARCHIVO_OI_IPR)
//                                        .getResultList());
//
//                        result.addAll(
//                                entityManager
//                                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE,
//                                                InconsistenciaDTO.class)
//                                        .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, inconsistencia.getIndicePlanilla())
//                                        .setParameter(ConstantesPilaBandeja.BLOQUE, BloqueValidacionEnum.BLOQUE_3_OI)
//                                        .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO, TipoArchivoPilaEnum.ARCHIVO_OI_APR)
//                                        .getResultList());
//                        return establecerDetalleInconsistencia(result);
//
//                    } catch (Exception e) {
//                        logger.error(ConstantesPilaBandeja.ERROR_EN_CONSULTA, e);
//                        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
//                        throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                    }
//                }
//
//                if ((inconsistencia.getTipoArchivo() == TipoArchivoPilaEnum.ARCHIVO_OI_IR)
//                        || (inconsistencia.getTipoArchivo() == TipoArchivoPilaEnum.ARCHIVO_OI_AR)) {
//                    try {
//                        logger.debug(
//                                "Inicia accionBandejaDetalleInconsistencias(InconsistenciaDTO inconsistencia,TipoInconsistenciasEnum tipoInconsistencia):Inicia Busqueda por archivo IR y AR");
//
//                        result.addAll(
//                                entityManager
//                                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE,
//                                                InconsistenciaDTO.class)
//                                        .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, inconsistencia.getIndicePlanilla())
//                                        .setParameter(ConstantesPilaBandeja.BLOQUE, BloqueValidacionEnum.BLOQUE_3_OI)
//                                        .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO, TipoArchivoPilaEnum.ARCHIVO_OI_IR)
//                                        .getResultList());
//
//                        result.addAll(
//                                entityManager
//                                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE,
//                                                InconsistenciaDTO.class)
//                                        .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, inconsistencia.getIndicePlanilla())
//                                        .setParameter(ConstantesPilaBandeja.BLOQUE, BloqueValidacionEnum.BLOQUE_3_OI)
//                                        .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO, TipoArchivoPilaEnum.ARCHIVO_OI_AR)
//                                        .getResultList());
//                        return establecerDetalleInconsistencia(result);
//
//                    } catch (Exception e) {
//
//                        logger.error(ConstantesPilaBandeja.ERROR_EN_CONSULTA, e);
//                        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
//                        throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                    }
//                }
//            }
//
//            if (tipoInconsistencia == TipoInconsistenciasEnum.APORTANTE_NO_IDENTIFICADO) {
//                logger.debug(
//                        "Inicia accionBandejaDetalleInconsistencias(InconsistenciaDTO inconsistencia,TipoInconsistenciasEnum tipoInconsistencia):Tipo de inconsistencia aportante no identificado ");
//
//                try {
//                    logger.debug(
//                            "Inicia accionBandejaDetalleInconsistencias(InconsistenciaDTO inconsistencia,TipoInconsistenciasEnum tipoInconsistencia):Inicia busqueda de las inconsistencias de aportante no identificado ");
//
//                    result.addAll(
//                            entityManager
//                                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE,
//                                            InconsistenciaDTO.class)
//                                    .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, inconsistencia.getIndicePlanilla())
//                                    .setParameter(ConstantesPilaBandeja.BLOQUE, BloqueValidacionEnum.BLOQUE_5_OI)
//                                    .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO, inconsistencia.getTipoArchivo()).getResultList());
//                    return establecerDetalleInconsistencia(result);
//
//                } catch (Exception e) {
//
//                    logger.error(ConstantesPilaBandeja.ERROR_EN_CONSULTA, e);
//                    logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
//                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                }
//            }
//
//            if (tipoInconsistencia == TipoInconsistenciasEnum.CONCILIACION) {
//                logger.debug(
//                        "Inicia accionBandejaDetalleInconsistencias(InconsistenciaDTO inconsistencia,TipoInconsistenciasEnum tipoInconsistencia):Tipo de inconsistencia conciliacion ");
//
//                try {
//                    logger.debug(
//                            "Inicia accionBandejaDetalleInconsistencias(InconsistenciaDTO inconsistencia,TipoInconsistenciasEnum tipoInconsistencia):Inicia Busqueda para conciliacion ");
//                    result.addAll(
//                            entityManager
//                                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE,
//                                            InconsistenciaDTO.class)
//                                    .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, inconsistencia.getIndicePlanilla())
//                                    .setParameter(ConstantesPilaBandeja.BLOQUE, BloqueValidacionEnum.BLOQUE_6_OI)
//                                    .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO, inconsistencia.getTipoArchivo()).getResultList());
//                    result = establecerDetalleInconsistencia(result);
//                    return PilaBandejaBusiness.establecerCamposConciliacion(result);
//
//                } catch (Exception e) {
//                    logger.error(ConstantesPilaBandeja.ERROR_EN_CONSULTA, e);
//                    logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
//                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                }
//            }
//            return null;
//        } catch (Exception e) {
//            logger.error("No es posible realizar la consulta de planillas", e);
//            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
//            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//        }
//
//    }

//    /**
//     * Metodo que establece la descripcion completa de la inconsistencia
//     * 
//     * @param result
//     *        lista con las inconsistencias
//     * @return List<TipoInconsistenciasEnum> Lista con los campos ya
//     *         establecidos de las diferencias
//     */
//    private List<InconsistenciaDTO> establecerDetalleInconsistencia(List<InconsistenciaDTO> result) {
//        for (InconsistenciaDTO inconsistencia : result) {
//            inconsistencia.setNumeroReprocesos("" + entityManager.createNamedQuery(NamedQueriesConstants.CONTEO_REPROCESOS_PLANILLA)
//                    .setParameter("idPlanilla", inconsistencia.getIndicePlanilla()).getSingleResult());
//            try {
//                String nombreIndiceA = entityManager
//                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVOS_ASOCIADOS_INFORMACION_NOMBRE, String.class)
//                        .setParameter(ConstantesPilaBandeja.NUMERO_PLANILLA, inconsistencia.getNumeroPlanilla()).getSingleResult();
//                inconsistencia.setNombreArchivoPar(nombreIndiceA);
//            } catch (NoResultException nre) {
//                logger.debug("No se encontro archivo asociado para establecer su par");
//            } catch (NonUniqueResultException nure) {
//                logger.debug("Existe mas de un resultado ,comuniquese con el administrador");
//            }
//
//            // Buscar la cantidad de registros 6 asociados a la planilla
//            if (!inconsistencia.getTipoArchivo().equals(TipoArchivoPilaEnum.ARCHIVO_OF)) {
//                StringBuilder sb = new StringBuilder(inconsistencia.getNombreArchivo().split("_")[8]);
//                sb.deleteCharAt(4);
//                // Quitar el .txt
//                String periodo = sb.toString().substring(0, 6);
//
//                inconsistencia.setCantidadRegistros6ArchivoF(
//                        entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CANTIDAD_REGISTRO6_ARCHIVOS_F, Long.class)
//                                .setParameter(ConstantesPilaBandeja.NUMERO_PLANILLA, inconsistencia.getNumeroPlanilla().toString())
//                                .setParameter("idAportante", inconsistencia.getNombreArchivo().split("_")[4])
//                                .setParameter("codigoOI", inconsistencia.getNombreArchivo().split("_")[6]).setParameter("periodo", periodo)
//                                .setParameter("estadoConciliacion", EstadoConciliacionArchivoFEnum.REGISTRO_6_ANULADO).getSingleResult());
//            }
//        }
//        return result;
//    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#anularPlanillaOI(com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void anularPlanillaOI(InconsistenciaDTO inconsistencia, UserDTO user) {

        logger.debug("Inicia anularPlanillaOI(InconsistenciaDTO, UserDTO)");

        List<EstadoArchivoPorBloque> estadosBloque = new ArrayList<>();
        List<Long> idsPlanillas = new ArrayList<>();
        idsPlanillas.add(inconsistencia.getIndicePlanilla());
        
        // el archivo par sólo se anula cuando no se trata de un reproceso 
        /**
         * Se modifica condicional, Mantis 0264206, HU: HU-211-392, Comentario validación reproceso
         */
        if (inconsistencia.getIndicePlanillaPar() != null) {
            idsPlanillas.add(inconsistencia.getIndicePlanillaPar());
        }
        
        // Buscar los bloques de validacion actual dependiendo del numero de planilla
        estadosBloque = entityManager
                .createNamedQuery(NamedQueriesConstants.BUSCAR_ESTADOS_POR_BLOQUE_ID_PLANILLA, EstadoArchivoPorBloque.class)
                .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, idsPlanillas).getResultList();

        // Cambiar el estado del bloque dependiendo del bloque del archivo seleccionado en la inconsistencia
        if (!estadosBloque.isEmpty() && inconsistencia.getBloque() != null) {
            procesarAnulacionOI(estadosBloque, inconsistencia.getIndicePlanilla(), inconsistencia.getBloque(), user.getEmail());
        }

        logger.debug("Finaliza anularPlanillaOI(InconsistenciaDTO, UserDTO)");
    }

    /**
     * @param estadosBloque
     * @param indiceInconsistencia
     * @param bloqueOriginal
     * @param usuario
     * 
     */
    private void procesarAnulacionOI(List<EstadoArchivoPorBloque> estadosBloque, Long indiceInconsistencia,
            BloqueValidacionEnum bloqueOriginal, String usuario) {
        Integer cantidadA = 0;
        Integer cantidadI = 0;
        for (EstadoArchivoPorBloque e : estadosBloque) {
            if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(e.getTipoArchivo().getSubtipo())) {
                cantidadI++;
            }
            else {
                cantidadA++;
            }
        }

        for (EstadoArchivoPorBloque e : estadosBloque) {
            Boolean anular = Boolean.FALSE;

            // sí el tipo de archivo es el único activo, es anulable
            if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(e.getTipoArchivo().getSubtipo()) && cantidadI == 1) {
                anular = Boolean.TRUE;
            }
            else if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(e.getTipoArchivo().getSubtipo()) && cantidadA == 1) {
                anular = Boolean.TRUE;
            }
            else
            // sí el índice de planilla es el especificado en la inconsistencia, se anula
            if (e.getIndicePlanilla().getId().equals(indiceInconsistencia)) {
                anular = Boolean.TRUE;
            }
            else if (bloqueOriginal != null) {
                // sí el índice tiene estado de bandeja en el mismo bloque que la inconsistencia
                switch (bloqueOriginal) {
                    case BLOQUE_0_OI:
                        anular = e.getEstadoBloque0() == null ? Boolean.FALSE : e.getEstadoBloque0().getReportarBandejaInconsistencias();
                        break;
                    case BLOQUE_1_OI:
                        anular = e.getEstadoBloque1() == null ? Boolean.FALSE : e.getEstadoBloque1().getReportarBandejaInconsistencias();
                        break;
                    case BLOQUE_2_OI:
                        anular = e.getEstadoBloque2() == null ? Boolean.FALSE : e.getEstadoBloque2().getReportarBandejaInconsistencias();
                        break;
                    case BLOQUE_3_OI:
                        anular = e.getEstadoBloque3() == null ? Boolean.FALSE : e.getEstadoBloque3().getReportarBandejaInconsistencias();
                        break;
                    case BLOQUE_4_OI:
                        anular = e.getEstadoBloque4() == null ? Boolean.FALSE : e.getEstadoBloque4().getReportarBandejaInconsistencias();
                        break;
                    case BLOQUE_5_OI:
                        anular = e.getEstadoBloque5() == null ? Boolean.FALSE : e.getEstadoBloque5().getReportarBandejaInconsistencias();
                        break;
                    case BLOQUE_6_OI:
                        anular = e.getEstadoBloque6() == null ? Boolean.FALSE : e.getEstadoBloque6().getReportarBandejaInconsistencias();
                        break;
                    default:
                        break;
                }
            }

            if (anular) {
                anularOI(e, bloqueOriginal, usuario);
            }
        }
    }

    /**
     * @param estado
     * @param bloqueInconsistencia
     * @param usuario
     * 
     */
    private void anularOI(EstadoArchivoPorBloque estado, BloqueValidacionEnum bloqueInconsistencia, String usuario) {
        estado = entityManager.merge(estado);

        Long numeroPlanilla = estado.getIndicePlanilla().getIdPlanilla();

        // sí el archivo es de información de aportante, el máximo bloque que se anula es el 4
        if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(estado.getTipoArchivo().getSubtipo()) && bloqueInconsistencia != null
                && bloqueInconsistencia.getValidatorProfile().compareTo(4L) > 0) {
            bloqueInconsistencia = BloqueValidacionEnum.BLOQUE_4_OI;
        }

        // se prepara la entrada del historial del estado
        HistorialEstadoBloque historialEstado = new HistorialEstadoBloque();
        historialEstado.setIdIndicePlanilla(estado.getIndicePlanilla().getId());
        historialEstado.setBloque(bloqueInconsistencia);

        if (bloqueInconsistencia != null) {
            switch (bloqueInconsistencia) {
                case BLOQUE_0_OI:
                    historialEstado.setEstado(estado.getEstadoBloque0());
                    historialEstado.setAccion(estado.getAccionBloque0());
                    historialEstado.setFechaEstado(estado.getFechaBloque0());
                    historialEstado.setUsuarioEspecifico(usuario);

                    estado.setEstadoBloque0(EstadoProcesoArchivoEnum.ANULADO);
                    estado.setAccionBloque0(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                    estado.setFechaBloque0(new Date());
                    break;
                case BLOQUE_1_OI:
                    historialEstado.setEstado(estado.getEstadoBloque1());
                    historialEstado.setAccion(estado.getAccionBloque1());
                    historialEstado.setFechaEstado(estado.getFechaBloque1());
                    historialEstado.setUsuarioEspecifico(usuario);


                    estado.setEstadoBloque1(EstadoProcesoArchivoEnum.ANULADO);
                    estado.setAccionBloque1(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                    estado.setFechaBloque1(new Date());
                    break;
                case BLOQUE_2_OI:
                    historialEstado.setEstado(estado.getEstadoBloque2());
                    historialEstado.setAccion(estado.getAccionBloque2());
                    historialEstado.setFechaEstado(estado.getFechaBloque2());
                    historialEstado.setUsuarioEspecifico(usuario);

                    estado.setEstadoBloque2(EstadoProcesoArchivoEnum.ANULADO);
                    estado.setAccionBloque2(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                    estado.setFechaBloque2(new Date());
                    break;
                case BLOQUE_3_OI:
                    historialEstado.setEstado(estado.getEstadoBloque3());
                    historialEstado.setAccion(estado.getAccionBloque3());
                    historialEstado.setFechaEstado(estado.getFechaBloque3());
                    historialEstado.setUsuarioEspecifico(usuario);

                    estado.setEstadoBloque3(EstadoProcesoArchivoEnum.ANULADO);
                    estado.setAccionBloque3(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                    estado.setFechaBloque3(new Date());
                    break;
                case BLOQUE_4_OI:
                    historialEstado.setEstado(estado.getEstadoBloque4());
                    historialEstado.setAccion(estado.getAccionBloque4());
                    historialEstado.setFechaEstado(estado.getFechaBloque4());
                    historialEstado.setUsuarioEspecifico(usuario);

                    estado.setEstadoBloque4(EstadoProcesoArchivoEnum.ANULADO);
                    estado.setAccionBloque4(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                    estado.setFechaBloque4(new Date());
                    break;
                case BLOQUE_5_OI:
                    historialEstado.setEstado(estado.getEstadoBloque5());
                    historialEstado.setAccion(estado.getAccionBloque5());
                    historialEstado.setFechaEstado(estado.getFechaBloque5());
                    historialEstado.setUsuarioEspecifico(usuario);

                    estado.setEstadoBloque5(EstadoProcesoArchivoEnum.ANULADO);
                    estado.setAccionBloque5(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                    estado.setFechaBloque5(new Date());
                    break;
                case BLOQUE_6_OI:
                    historialEstado.setEstado(estado.getEstadoBloque6());
                    historialEstado.setAccion(estado.getAccionBloque6());
                    historialEstado.setFechaEstado(estado.getFechaBloque6());
                    historialEstado.setUsuarioEspecifico(usuario);

                    estado.setEstadoBloque6(EstadoProcesoArchivoEnum.ANULADO);
                    estado.setAccionBloque6(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                    estado.setFechaBloque6(new Date());
                    break;
                default:
                    break;
            }

            // se anula el índice y se agregan los datos relacionados
            estado.getIndicePlanilla().setUsuarioEliminacion(usuario);
            estado.getIndicePlanilla().setFechaEliminacion(new Date());
            estado.getIndicePlanilla().setEstadoArchivo(EstadoProcesoArchivoEnum.ANULADO);
            estado.getIndicePlanilla().setRegistroActivo(Boolean.FALSE);

            // se eliminan las variables de paso
            entityManager.createNamedQuery(NamedQueriesConstants.ELIMINAR_VARIABLES_DE_PASO)
                    .setParameter(ConstantesPilaBandeja.NUMERO_PLANILLA, numeroPlanilla).executeUpdate();

            // Cambiar el estado en error validacion 
            entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ERROR_VALIDACION)
                    .setParameter("estadoInconsistencia", EstadoGestionInconsistenciaEnum.INCONSISTENCIA_GESTIONADA)
                    .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, estado.getIndicePlanilla().getId())
                    .setParameter(ConstantesPilaBandeja.BLOQUE_VALIDACION, PilaBandejaBusiness.prepararListaBloques(bloqueInconsistencia))
                    .executeUpdate();

            // se persiste la entrada de historial
            entityManager.persist(historialEstado);
        }
    }
    @Override
    public IndicePlanilla consultarIndicePlanillaEntidad(Long idIndicePlanilla)
    {
        ConsultarIndicePlanillaEntidad consultarIndicePlanillaEntidad = new ConsultarIndicePlanillaEntidad(idIndicePlanilla);
        consultarIndicePlanillaEntidad.execute();

        return consultarIndicePlanillaEntidad.getResult();
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#validarEstructuraPlanilla(com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO)
     */
    @Override
    public void validarEstructuraPlanilla(InconsistenciaDTO inconsistencia) {
        String firmaMetodo = "validarEstructuraPlanilla(InconsistenciaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Long> indices = null;
        IndicePlanilla indiceUno = null;
        EstadoArchivoPorBloque estadoUno = null;
        IndicePlanilla indiceDos = null;
        EstadoArchivoPorBloque estadoDos = null;
        IndicePlanilla indiceUnico = null;
        EstadoArchivoPorBloque estadoUnico = null;

        IndicePlanilla indiceActual = consultarIndicePlanillaEntidad(inconsistencia.getIndicePlanilla());
        // se realiza la busqueda de los indices asociados al numero de planilla
        indices = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_INDICES_PLANILLAS_OPERADOR_INFORMACION, Long.class)
                .setParameter(ConstantesPilaBandeja.NUMERO_PLANILLA, inconsistencia.getNumeroPlanilla())
                .setParameter(ConstantesPilaBandeja.OPERADOR_INFORMACION, indiceActual.getCodigoOperadorInformacion()).getResultList();
        // si no existe ninguno se genera una excepcion
        if (indices.isEmpty()) {
            logger.error(ConstantesPilaBandeja.NO_HAY_INDICES_ASOCIADOS);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        // se valida la existencia del archivo par
        if (indices.size() == 2) {
            try {
                indiceUno = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INDICE_PLANILLAS, IndicePlanilla.class)
                        .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, indices.get(0)).getSingleResult();
                indiceUno.setEstadoArchivo(EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA);

                estadoUno = entityManager.createNamedQuery(NamedQueriesConstants.BUSQUEDA_BLOQUE_INDICE, EstadoArchivoPorBloque.class)
                        .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, indices.get(0)).getSingleResult();

                indiceDos = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INDICE_PLANILLAS, IndicePlanilla.class)
                        .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, indices.get(1)).getSingleResult();
                indiceDos.setEstadoArchivo(EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA);

                estadoDos = entityManager.createNamedQuery(NamedQueriesConstants.BUSQUEDA_BLOQUE_INDICE, EstadoArchivoPorBloque.class)
                        .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, indices.get(1)).getSingleResult();
            } catch (NoResultException e) {
                logger.error("No logro obtener el indice dos o su estado ");
                throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
            }

            try {
                if (establecerGestionInconsistencias(obtenerIdError(indices, TipoOperadorEnum.OPERADOR_INFORMACION),
                        EstadoGestionInconsistenciaEnum.INCONSISTENCIA_GESTIONADA, inconsistencia.getBloque())) {
                    entityManager
                            .merge(PilaBandejaBusiness.establecerUbicacionBloqueSiguienteBloque(inconsistencia.getBloque(), estadoUno));
                    entityManager.merge(indiceUno);
                    entityManager
                            .merge(PilaBandejaBusiness.establecerUbicacionBloqueSiguienteBloque(inconsistencia.getBloque(), estadoDos));
                    entityManager.merge(indiceDos);

                }
            } catch (NoResultException e) {
                logger.error("Se presento un error al tratar de persistir la informacion asociada con la inconsistencia ");
                throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
            }

        }
        // dado el caso que no tenga pareja se anula solo un indice
        else {
            try {
                indiceUnico = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INDICE_PLANILLAS, IndicePlanilla.class)
                        .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, indices.get(0)).getSingleResult();
                indiceUnico.setEstadoArchivo(EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA);

                estadoUnico = entityManager.createNamedQuery(NamedQueriesConstants.BUSQUEDA_BLOQUE_INDICE, EstadoArchivoPorBloque.class)
                        .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, indices.get(0)).getSingleResult();

            } catch (NoResultException e) {
                logger.error(ConstantesPilaBandeja.NO_HAY_INDICES_ASOCIADOS);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
            }
            try {
                if (establecerGestionInconsistencias(obtenerIdError(indices, TipoOperadorEnum.OPERADOR_INFORMACION),
                        EstadoGestionInconsistenciaEnum.INCONSISTENCIA_GESTIONADA, inconsistencia.getBloque())) {
                    entityManager
                            .merge(PilaBandejaBusiness.establecerUbicacionBloqueSiguienteBloque(inconsistencia.getBloque(), estadoUnico));
                    entityManager.merge(indiceUnico);

                }
            } catch (Exception e) {
                logger.error(ConstantesPilaBandeja.NO_HAY_INDICES_ASOCIADOS);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
            }

        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void persistirHistoricoBloque2(Long indicePlanilla, String nombreUsuario) {
        try {
            List<Object> result = null;
            result = entityManager.createNamedQuery(NamedQueriesConstants.BUCAR_ESTADO_INDICE_PLANILLA_B2)
                .setParameter("indicePlanilla",indicePlanilla)
                .getResultList();
            if (result != null && !result.isEmpty()) {
                String hebEstado = result.get(0).toString();
                HistorialEstadoBloque historicoB2 = new HistorialEstadoBloque();
                historicoB2.setEstado(EstadoProcesoArchivoEnum.valueOf(hebEstado)); 
                historicoB2.setAccion(AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_3); 
                historicoB2.setBloque(BloqueValidacionEnum.BLOQUE_2_OI); 
                historicoB2.setFechaEstado(new Date());
                historicoB2.setIdIndicePlanilla(indicePlanilla);
                historicoB2.setUsuarioEspecifico(nombreUsuario);
                entityManager.persist(historicoB2);
            }
        } catch (Exception e) {
            logger.error("Error en mettodo persistirHistoricoBloque2", e);
        }
    }

    /**
     * Metodo que establece los id de errores asociados a un indice planilla que aun esten activos
     * @param indices
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<Long> obtenerIdError(List<Long> indices, TipoOperadorEnum tipoOperador) {
        List<Long> result = new ArrayList<>();

        Query query = null;

        if (TipoOperadorEnum.OPERADOR_INFORMACION.equals(tipoOperador)) {
            query = entityManager.createNamedQuery(NamedQueriesConstants.BUSQUEDA_ID_ERRORES_POR_INDICE, Long.class);
        }
        else {
            query = entityManager.createNamedQuery(NamedQueriesConstants.BUSQUEDA_ID_ERRORES_POR_INDICE_OF, Long.class);
        }

        try {
            for (Long idIndice : indices) {
                result.addAll(query.setParameter("idIndice", idIndice).getResultList());
            }
        } catch (NoResultException e) {
            logger.debug("No existen errores asociados a ese id de planilla");
        }
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#anularPlanillaOF(com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void anularPlanillaOF(InconsistenciaDTO inconsistencia, UserDTO user) {
        logger.debug("Inicia anularPlanillaOF(InconsistenciaDTO, UserDTO)");

        // Si llega tipo de archivo OF
        if (TipoArchivoPilaEnum.ARCHIVO_OF.equals(inconsistencia.getTipoArchivo())) {
            anularIndiceOF(inconsistencia.getIndicePlanilla(), inconsistencia.getBloque(), Boolean.TRUE, user.getEmail());
        }
        // Errores de conciliacion
        else {
            String codigoOperadorInformacion = inconsistencia.getNombreArchivo().split("_")[6];
            // Borrar el caracter "-" del periodo
            StringBuilder sb = new StringBuilder(inconsistencia.getNombreArchivo().split("_")[8]);
            sb.deleteCharAt(4);
            // Quitar el .txt
            String periodo = sb.toString().substring(0, 6);
            Short codigoOI = new Short(codigoOperadorInformacion);

            try {
                // se consulta el archivo OF completo (Lista de archivos OF)
                List<PilaArchivoFRegistro6> registros6 = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_IDS_REG_PLANILLA_OF, PilaArchivoFRegistro6.class)
                        .setParameter(ConstantesPilaBandeja.NUMERO_PLANILLA, inconsistencia.getNumeroPlanilla().toString())
                        .setParameter("codigoOI", codigoOI).setParameter("periodo", periodo).getResultList();

                /*
                 * se recorren los registros tipo 6 para anular al seleccionado y a su vez, determinar si quedan más registros
                 * sin anular para establecer sí se anula el índice Of
                 */
                Boolean quedanRegistrosValidos = Boolean.FALSE;
                Long idIndiceOF = null;
                for (PilaArchivoFRegistro6 r6 : registros6) {
                    Short codigoOIR6 = new Short(r6.getCodOperadorInformacion());
                    if (r6.getNumeroPlanilla().equals(inconsistencia.getNumeroPlanilla().toString()) && r6.getPeriodoPago().equals(periodo)
                            && codigoOIR6.compareTo(codigoOI) == 0) {
                        // este el registro a anular
                        idIndiceOF = r6.getIndicePlanilla().getId();

                        entityManager.merge(r6);
                        r6.setEstadoConciliacion(EstadoConciliacionArchivoFEnum.REGISTRO_6_ANULADO);
                    }
                    else if (!EstadoConciliacionArchivoFEnum.REGISTRO_6_ANULADO.equals(r6.getEstadoConciliacion())) {
                        // no es el registro a anular y es un registro válido
                        quedanRegistrosValidos = Boolean.TRUE;
                    }
                }

                // sí no quedan registros 6 validos (estado diferente a anulado), se anula también el índice OF
                if (!quedanRegistrosValidos && idIndiceOF != null) {
                    anularIndiceOF(idIndiceOF, inconsistencia.getBloque(), Boolean.FALSE, user.getEmail());
                }
            } catch (Exception e) {
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
            }
        }

        logger.debug("Finaliza anularPlanillaOF(InconsistenciaDTO, inconsistencia)");
    }

    /**
     * @param idIndicePlanilla
     * @param bloque
     * @param resolverInconsistencia
     * @param usuario
     * 
     */
    private void anularIndiceOF(Long idIndicePlanilla, BloqueValidacionEnum bloque, Boolean resolverInconsistencia, String usuario) {
        // se prepara la entrada de histórico
        HistorialEstadoBloque historicoEstado = new HistorialEstadoBloque();

        IndicePlanillaOF indiceOF = new IndicePlanillaOF();
        EstadoArchivoPorBloqueOF estadoBloqueOF = new EstadoArchivoPorBloqueOF();
        // Consultar el archivo OF asociado al indice de planilla
        try {
            // Cambiar el estado del Bloque de validacion
            estadoBloqueOF = entityManager.createNamedQuery(NamedQueriesConstants.BUSQUEDA_BLOQUE_INDICE_OF, EstadoArchivoPorBloqueOF.class)
                    .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, idIndicePlanilla).getSingleResult();

            estadoBloqueOF = entityManager.merge(estadoBloqueOF);

            historicoEstado.setBloque(bloque);
            switch (bloque) {
                case BLOQUE_0_OF:
                    historicoEstado.setEstado(estadoBloqueOF.getEstadoBloque0());
                    historicoEstado.setAccion(estadoBloqueOF.getAccionBloque0());
                    historicoEstado.setFechaEstado(estadoBloqueOF.getFechaBloque0());

                    estadoBloqueOF.setEstadoBloque0(EstadoProcesoArchivoEnum.ANULADO);
                    estadoBloqueOF.setAccionBloque0(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                    estadoBloqueOF.setFechaBloque0(new Date());
                    break;
                case BLOQUE_1_OF:
                    historicoEstado.setEstado(estadoBloqueOF.getEstadoBloque1());
                    historicoEstado.setAccion(estadoBloqueOF.getAccionBloque1());
                    historicoEstado.setFechaEstado(estadoBloqueOF.getFechaBloque1());

                    estadoBloqueOF.setEstadoBloque1(EstadoProcesoArchivoEnum.ANULADO);
                    estadoBloqueOF.setAccionBloque1(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                    estadoBloqueOF.setFechaBloque1(new Date());
                    break;
                case BLOQUE_6_OI:
                    historicoEstado.setEstado(estadoBloqueOF.getEstadoBloque6());
                    historicoEstado.setAccion(estadoBloqueOF.getAccionBloque6());
                    historicoEstado.setFechaEstado(estadoBloqueOF.getFechaBloque6());

                    estadoBloqueOF.setEstadoBloque6(EstadoProcesoArchivoEnum.ANULADO);
                    estadoBloqueOF.setAccionBloque6(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                    estadoBloqueOF.setFechaBloque6(new Date());
                    break;
                default:
                    break;
            }

            // Cambiar el estado del archivo OF
            indiceOF = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INDICE_PLANILLAS_OF, IndicePlanillaOF.class)
                    .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, idIndicePlanilla).getSingleResult();

            historicoEstado.setIdIndicePlanillaOF(indiceOF.getId());

            indiceOF = entityManager.merge(indiceOF);
            indiceOF.setEstado(EstadoProcesoArchivoEnum.ANULADO);
            indiceOF.setRegistroActivo(false);
            indiceOF.setFechaEliminacion(new Date());
            indiceOF.setUsuarioEliminacion(usuario);

            // Cambiar el estado en error validacion 
            if (resolverInconsistencia) {
                entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ERROR_VALIDACION_OF)
                        .setParameter("estadoInconsistencia", EstadoGestionInconsistenciaEnum.INCONSISTENCIA_GESTIONADA)
                        .setParameter("idPlanillaOF", idIndicePlanilla)
                        .setParameter(ConstantesPilaBandeja.BLOQUE_VALIDACION, PilaBandejaBusiness.prepararListaBloques(bloque))
                        .executeUpdate();
            }

            // se persiste la entrada de histórico
            entityManager.persist(historicoEstado);

        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#enviarSolicitudCambioIden(com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO,
     *      java.lang.Long, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void enviarSolicitudCambioIden(InconsistenciaDTO inconsistencia, Long numeroIdentificacion, UserDTO user) {
        String firmaMetodo = "enviarSolicitudCambioIden(InconsistenciaDTO, Long, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Long> indices = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_INDICES_PLANILLAS, Long.class)
                .setParameter(ConstantesPilaBandeja.NUMERO_PLANILLA, inconsistencia.getNumeroPlanilla()).getResultList();
        // se genera una nueva solicitu de correcion
        SolicitudCambioNumIdentAportante solicitud = null;

        // se establecen las posibles planillas que se verian afectadas
        IndicePlanilla indiceI = null;
        IndicePlanilla indiceA = null;
        TipoArchivoPilaEnum tipoArchivoA = null;
        TipoArchivoPilaEnum tipoArchivoI = null;

        switch (inconsistencia.getTipoArchivo()) {
            case ARCHIVO_OI_I:
            case ARCHIVO_OI_A:
                tipoArchivoI = TipoArchivoPilaEnum.ARCHIVO_OI_I;
                tipoArchivoA = TipoArchivoPilaEnum.ARCHIVO_OI_A;
                break;
            case ARCHIVO_OI_IR:
            case ARCHIVO_OI_AR:
                tipoArchivoI = TipoArchivoPilaEnum.ARCHIVO_OI_IR;
                tipoArchivoA = TipoArchivoPilaEnum.ARCHIVO_OI_AR;
                break;
            case ARCHIVO_OI_IP:
            case ARCHIVO_OI_AP:
                tipoArchivoI = TipoArchivoPilaEnum.ARCHIVO_OI_IP;
                tipoArchivoA = TipoArchivoPilaEnum.ARCHIVO_OI_AP;
                break;
            case ARCHIVO_OI_IPR:
            case ARCHIVO_OI_APR:
                tipoArchivoI = TipoArchivoPilaEnum.ARCHIVO_OI_IPR;
                tipoArchivoA = TipoArchivoPilaEnum.ARCHIVO_OI_APR;
                break;
            default:
                break;
        }
        if (tipoArchivoI != null && tipoArchivoA != null) {
            // se realiza la busqueda dependiendo del tipo de archivo
            indiceI = consultarArchivosOIAsociados(inconsistencia, tipoArchivoI);
            indiceA = consultarArchivosOIAsociados(inconsistencia, tipoArchivoA);
        
            // al finalizar se establecen los datos finales de la solicitud
            solicitud = new SolicitudCambioNumIdentAportante();
            solicitud.setIndicePlanilla(
                    entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVOS_ASOCIADOS_INFORMACION_2, IndicePlanilla.class)
                            .setParameter(ConstantesPilaBandeja.NUMERO_PLANILLA, inconsistencia.getNumeroPlanilla())
                            .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO, inconsistencia.getTipoArchivo())
                            .setParameter(ConstantesPilaBandeja.NOMBRE_PLANILLA, inconsistencia.getNombreArchivo())
                            .setParameter(ConstantesPilaBandeja.NOMBRE_PLANILLA_PAR, inconsistencia.getNombreArchivoPar()).getSingleResult());
            if (indiceI != null && indiceA != null) {
                solicitud.setIdPlanillaInformacion(indiceA.getId());

                solicitud.setAccionCorreccion(AccionCorreccionPilaEnum.REGISTRAR_SOLICITUD_CAMBIO_IDENTIFICACION);
                solicitud.setFechaSolicitud(new Date());
                solicitud.setNumeroIdentificacion(numeroIdentificacion);
                solicitud.setUsuarioSolicitud(user.getNombreUsuario());
                solicitud.setIdErrorValidacionLog(inconsistencia.getIdErrorValidacion());
                StringBuilder archivos = new StringBuilder(inconsistencia.getNombreArchivo());
                if(inconsistencia.getNombreArchivoPar() != null){
                    archivos = archivos.append(", "+inconsistencia.getNombreArchivoPar());
                }
                if(inconsistencia.getNombreArchivoOF() != null){
                    archivos = archivos.append(", "+inconsistencia.getNombreArchivoOF());
                }
                solicitud.setArchivosCorrecion(archivos.toString());

                if (establecerGestionInconsistencias(obtenerIdError(indices, TipoOperadorEnum.OPERADOR_INFORMACION),
                        EstadoGestionInconsistenciaEnum.PENDIENTE_POR_APROBACION, BloqueValidacionEnum.BLOQUE_5_OI)) {
                    entityManager.persist(solicitud);

                    // se consulta y actualiza el estado por bloque
                    EstadoArchivoPorBloque estado = entityManager
                            .createNamedQuery(NamedQueriesConstants.BUSCAR_ESTADO_BLOQUE_PLANILLA, EstadoArchivoPorBloque.class)
                            .setParameter(ConstantesPilaBandeja.ID_PLANILLA, indiceI.getId()).getSingleResult();
                    estado = entityManager.merge(estado);

                    /* Se prepara la entrada en histórico de estad */
                    HistorialEstadoBloque historialEstado = new HistorialEstadoBloque();
                    historialEstado.setIdIndicePlanilla(indiceI.getId());
                    historialEstado.setEstado(estado.getEstadoBloque5());
                    historialEstado.setAccion(estado.getAccionBloque5());
                    historialEstado.setFechaEstado(estado.getFechaBloque5());
                    historialEstado.setBloque(BloqueValidacionEnum.BLOQUE_5_OI);
                    historialEstado.setUsuarioEspecifico(user.getNombreUsuario());
                    historialEstado.setClaseUsuario((short) 1);

                    estado.setEstadoBloque5(EstadoProcesoArchivoEnum.PENDIENTE_POR_APROBAR);
                    estado.setAccionBloque5(AccionProcesoArchivoEnum.PASAR_A_BANDEJA);
                    estado.setFechaBloque5(new Date());

                    entityManager.merge(indiceI);
                    indiceI.setEstadoArchivo(EstadoProcesoArchivoEnum.PENDIENTE_POR_APROBAR);

                    // se persiste el historial
                    entityManager.persist(historialEstado);
                }
                else {
                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                }
            }
            else {
                logger.error(ConstantesPilaBandeja.ERROR_EN_CONSULTA + " : No encontro indice de planilla I asociado");
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }
        }
        else {
            logger.error(ConstantesPilaBandeja.ERROR_EN_CONSULTA
                    + " : no viene tipo de archivo de planilla indicado en la inconsistencia a soventar");
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public IndicePlanillaModeloDTO consultarIndicesOIporNumeroYTipo(IndicePlanilla indicePlanilla, TipoArchivoPilaEnum tipoArchivo) {
        String firmaMetodo = "consultarIndicesOIporNumeroYTipo(Long, TipoArchivoPilaEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        IndicePlanillaModeloDTO result = null;

        try {
            IndicePlanilla indice = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVOS_ASOCIADOS_INFORMACION, IndicePlanilla.class)
                            .setParameter(ConstantesPilaBandeja.NUMERO_PLANILLA, indicePlanilla.getIdPlanilla())
                            .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO, tipoArchivo)
                            .setParameter(ConstantesPilaBandeja.OPERADOR_INFORMACION, indicePlanilla.getCodigoOperadorInformacion()).getSingleResult();

            result = new IndicePlanillaModeloDTO();
            result.convertToDTO(indice);
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + ConstantesPilaBandeja.ERROR_EN_CONSULTA, e);
            throw new TechnicalException(ConstantesPilaBandeja.ERROR_EN_CONSULTA, e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Object consultarRegistro1PorIdPlanillaYTipoArchivo(Long idPlanilla, TipoArchivoPilaEnum tipoArchivo) {
        String firmaMetodo = "consultarRegistro1PorIdPlanillaYTipoArchivo(Long, TipoArchivoPilaEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Object result = null;
        Query query = null;

        try {
            if (GrupoArchivoPilaEnum.APORTES_INDEPENDIENTES_DEPENDIENTES.equals(tipoArchivo.getGrupo())
                    && SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(tipoArchivo.getSubtipo())) {
                query = entityManager.createNamedQuery(NamedQueriesConstants.BUSQUEDA_INDICE_PLANILLA_A);
            }
            else if (GrupoArchivoPilaEnum.APORTES_INDEPENDIENTES_DEPENDIENTES.equals(tipoArchivo.getGrupo())
                    && SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(tipoArchivo.getSubtipo())) {
                query = entityManager.createNamedQuery(NamedQueriesConstants.BUSQUEDA_INDICE_PLANILLA_I);
            }
            else if (GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(tipoArchivo.getGrupo())
                    && SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(tipoArchivo.getSubtipo())) {
                query = entityManager.createNamedQuery(NamedQueriesConstants.BUSQUEDA_INDICE_PLANILLA_AP);
            }
            else if (GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(tipoArchivo.getGrupo())
                    && SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(tipoArchivo.getSubtipo())) {
                query = entityManager.createNamedQuery(NamedQueriesConstants.BUSQUEDA_INDICE_PLANILLA_IP);
            }

            if (query != null) {
                query.setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, idPlanilla);
                result = query.getSingleResult();
            }
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + ConstantesPilaBandeja.ERROR_EN_CONSULTA, e);
            throw new TechnicalException(ConstantesPilaBandeja.ERROR_EN_CONSULTA, e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    @Override
    public PilaArchivoFRegistro6 obtenerRegistro6Of(String numeroPlanilla, String codOperadorInformacion, String periodoPago) {
        String firmaMetodo = "obtenerRegistro6Of(String, String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        PilaArchivoFRegistro6 result = null;

        Short codigoOI = codOperadorInformacion != null && StringUtils.isNumeric(codOperadorInformacion) ? new Short(codOperadorInformacion)
                : null;

        try {
            result = entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSQUEDA_INDICE_PLANILLA_CON_NUMERO_PLANILLA_F, PilaArchivoFRegistro6.class)
                    .setParameter(ConstantesPilaBandeja.NUMERO_PLANILLA, numeroPlanilla)
                    .setParameter(ConstantesPilaBandeja.CODIGO_OI, codigoOI).setParameter(ConstantesPilaBandeja.PERIODO_APORTE, periodoPago)
                    .setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + ConstantesPilaBandeja.ERROR_EN_CONSULTA, e);
            throw new TechnicalException(ConstantesPilaBandeja.ERROR_EN_CONSULTA, e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#rechazarSolicitudCambioIden(java.util.List,
     *      com.asopagos.rest.security.dto.UserDTO, com.asopagos.enumeraciones.pila.RazonRechazoSolicitudCambioIdenEnum, java.lang.String)
     */
    @Override
    public void rechazarSolicitudCambioIden(List<SolicitudCambioNumIdentAportante> listaSolicitudes, UserDTO user,
            RazonRechazoSolicitudCambioIdenEnum razonRechazo, String comentarios) {

        try {
            logger.debug(
                    "Inicia rechazarSolicitudCambioIden(List<SolicitudCambioNumIdentAportante> listaSolicitudes, UserDTO user,RazonRechazoSolicitudCambioIdenEnum razonRechazo, String comentarios):Inicia Busqueda, recursos  encontrados");
            // se recorre la lista de los registros que el supervisor haya
            // decidido rechazar
            for (SolicitudCambioNumIdentAportante solicitud : listaSolicitudes) {
                solicitud = entityManager.merge(solicitud);
                solicitud.setAccionCorreccion(AccionCorreccionPilaEnum.ANULAR_SOLICITUD_CAMBIO_IDENTIFICACION);
                solicitud.setFechaRespuesta(new Date());
                solicitud.setUsuarioAprobador(user.getNombreUsuario());
                solicitud.setRazonRechazo(razonRechazo);
                solicitud.setComentarios(comentarios);

                List<Long> indices = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_INDICES_PLANILLAS, Long.class)
                        .setParameter("numeroPlanilla", solicitud.getIndicePlanilla().getIdPlanilla()).getResultList();

                establecerGestionInconsistencias(obtenerIdError(indices, TipoOperadorEnum.OPERADOR_INFORMACION),
                        EstadoGestionInconsistenciaEnum.PENDIENTE_GESTION, BloqueValidacionEnum.BLOQUE_5_OI);

                // Cambiar el estado del indice de planilla asociado a la solicitud
                if (solicitud.getIndicePlanilla() != null) {
                    IndicePlanilla indiceI = solicitud.getIndicePlanilla();

                    // se consulta y actualiza el estado por bloque
                    EstadoArchivoPorBloque estado = entityManager
                            .createNamedQuery(NamedQueriesConstants.BUSCAR_ESTADO_BLOQUE_PLANILLA, EstadoArchivoPorBloque.class)
                            .setParameter(ConstantesPilaBandeja.ID_PLANILLA, indiceI.getId()).getSingleResult();

                    estado = entityManager.merge(estado);

                    /* Se prepara la entrada en histórico de estado */
                    HistorialEstadoBloque historialEstado = new HistorialEstadoBloque();
                    historialEstado.setIdIndicePlanilla(indiceI.getId());
                    historialEstado.setEstado(estado.getEstadoBloque5());
                    historialEstado.setAccion(estado.getAccionBloque5());
                    historialEstado.setFechaEstado(estado.getFechaBloque5());
                    historialEstado.setBloque(BloqueValidacionEnum.BLOQUE_5_OI);
                    historialEstado.setUsuarioEspecifico(user.getNombreUsuario());
                    historialEstado.setClaseUsuario((short) 4);

                    estado.setEstadoBloque5(EstadoProcesoArchivoEnum.RECHAZADO);
                    estado.setAccionBloque5(AccionProcesoArchivoEnum.PASAR_A_BANDEJA);
                    estado.setFechaBloque5(new Date());

                    // se persiste el historial
                    entityManager.persist(historialEstado);

                    indiceI = entityManager.merge(indiceI);
                    indiceI.setEstadoArchivo(EstadoProcesoArchivoEnum.RECHAZADO);
                }
            }
        } catch (NoResultException nre) {
            logger.debug("no se encontraron resultados asociados a la planilla");
        }

    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#busquedaSolicitudCambioIden(java.lang.Long, java.lang.Long,
     *      java.lang.Long)
     */
    @Override
    public List<SolicitudCambioNumIdentAportante> busquedaSolicitudCambioIden(Long numeroPlanilla, Long fechaInicio, Long fechaFin) {

        logger.debug("Inicia busquedaSolicitudCambioIden(Long numeroPlanilla, Long fechaInicio,Long fechaFin");
        List<SolicitudCambioNumIdentAportante> result = null;

        // se establecen las fechas en formato Date para las consultas
        Date fechaI = fechaInicio != null ? new Date(fechaInicio) : null;
        Date fechaF = fechaFin != null ? new Date(fechaFin) : null;
        try {

            if (fechaFin != null && fechaInicio != null) {
                if (numeroPlanilla != null) {
                    // consulta por los 3 valores
                    result = entityManager
                            .createNamedQuery(NamedQueriesConstants.BUSQUEDA_SOLICITUD_FECHA_INICIO_FIN_PLANILLA,
                                    SolicitudCambioNumIdentAportante.class)
                            .setParameter(ConstantesPilaBandeja.NUMERO_PLANILLA, numeroPlanilla)
                            .setParameter(ConstantesPilaBandeja.FECHA_INICIO, CalendarUtils.truncarHora(fechaI))
                            .setParameter(ConstantesPilaBandeja.FECHA_FIN, CalendarUtils.truncarHoraMaxima(fechaF)).getResultList();
                    return PilaBandejaBusiness.filtrarResultadoConsultaSolicitudes(PilaBandejaBusiness.evaluarResultadoSolicitudes(result));
                }
                else {
                    // consulta solo por fechas
                    result = entityManager
                            .createNamedQuery(NamedQueriesConstants.BUSQUEDA_SOLICITUD_FECHA_INICIO_FIN,
                                    SolicitudCambioNumIdentAportante.class)
                            .setParameter(ConstantesPilaBandeja.FECHA_INICIO, CalendarUtils.truncarHora(fechaI))
                            .setParameter(ConstantesPilaBandeja.FECHA_FIN, CalendarUtils.truncarHoraMaxima(fechaF)).getResultList();
                    return PilaBandejaBusiness.filtrarResultadoConsultaSolicitudes(PilaBandejaBusiness.evaluarResultadoSolicitudes(result));
                }

            }
            if (fechaFin != null) {
                if (numeroPlanilla != null) {
                    result = entityManager
                            .createNamedQuery(NamedQueriesConstants.BUSQUEDA_SOLICITUD_FECHA_FIN_PLANILLA,
                                    SolicitudCambioNumIdentAportante.class)
                            .setParameter(ConstantesPilaBandeja.NUMERO_PLANILLA, numeroPlanilla)
                            .setParameter(ConstantesPilaBandeja.FECHA_FIN, CalendarUtils.truncarHoraMaxima(fechaF)).getResultList();
                    return PilaBandejaBusiness.filtrarResultadoConsultaSolicitudes(PilaBandejaBusiness.evaluarResultadoSolicitudes(result));
                }
                else {
                    result = entityManager
                            .createNamedQuery(NamedQueriesConstants.BUSQUEDA_SOLICITUD_FECHA_FIN, SolicitudCambioNumIdentAportante.class)
                            .setParameter(ConstantesPilaBandeja.FECHA_FIN, CalendarUtils.truncarHoraMaxima(fechaF)).getResultList();
                    return PilaBandejaBusiness.filtrarResultadoConsultaSolicitudes(PilaBandejaBusiness.evaluarResultadoSolicitudes(result));
                }
            }
            if (numeroPlanilla != null) {
                result = entityManager
                        .createNamedQuery(NamedQueriesConstants.BUSQUEDA_SOLICITUD_PLANILLA, SolicitudCambioNumIdentAportante.class)
                        .setParameter(ConstantesPilaBandeja.NUMERO_PLANILLA, numeroPlanilla).getResultList();
                return PilaBandejaBusiness.filtrarResultadoConsultaSolicitudes(PilaBandejaBusiness.evaluarResultadoSolicitudes(result));
            }

        } catch (Exception e) {
            logger.error(ConstantesPilaBandeja.ERROR_EN_CONSULTA, e);
            logger.debug("Finaliza busquedaSolicitudCambioIden(Long numeroPlanilla, Long fechaInicio,Long fechaFin");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(
                "Finaliza busquedaSolicitudCambioIden(Long numeroPlanilla, Long fechaInicio,Long fechaFin) : no se encontro una busqueda compatible ");
        return null;

    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#establecerGestionInconsistencias(java.util.List,
     *      com.asopagos.enumeraciones.pila.EstadoGestionInconsistenciaEnum)
     */
    @Override
    public Boolean establecerGestionInconsistencias(List<Long> listaErrores, EstadoGestionInconsistenciaEnum estado,
            BloqueValidacionEnum bloque) {
        for (Long IdErrorValidacion : listaErrores) {
            try {
                ErrorValidacionLog result = entityManager
                        .createNamedQuery(NamedQueriesConstants.BUSQUEDA_ERROR_VALIDACION, ErrorValidacionLog.class)
                        .setParameter("idErrorValidacion", IdErrorValidacion).setParameter("bloque", bloque).getSingleResult();
                switch (estado) {
                    case INCONSISTENCIA_GESTIONADA:
                        // se realiza la busqueda de el error y se establece como gestionado
                        result.setEstadoInconsistencia(EstadoGestionInconsistenciaEnum.INCONSISTENCIA_GESTIONADA);
                        entityManager.merge(result);
                        continue;

                    case PENDIENTE_POR_APROBACION:
                        result.setEstadoInconsistencia(EstadoGestionInconsistenciaEnum.PENDIENTE_POR_APROBACION);
                        entityManager.merge(result);
                        continue;

                    case PENDIENTE_GESTION:
                        // se realiza la busqueda de el error y se establece como gestionado
                        result.setEstadoInconsistencia(EstadoGestionInconsistenciaEnum.PENDIENTE_GESTION);
                        entityManager.merge(result);
                        continue;
                }
            } catch (NoResultException e) {
                continue;
            } catch (Exception e) {
                return false;
            }
        }

        return true;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#veArchivo(java.lang.Long)
     */
    @Override
    public IdentificadorDocumentoDTO veArchivo(Long idPlanilla, TipoArchivoPilaEnum tipoArchivo) {
        // Si el tipo de archivo es distinto a operador financiero
        if (!tipoArchivo.equals(TipoArchivoPilaEnum.ARCHIVO_OF)) {
            try {
                IndicePlanilla indiceOI = entityManager
                        .createNamedQuery(NamedQueriesConstants.OBTENER_INDICE_PLANILLAS, IndicePlanilla.class)
                        .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, idPlanilla).getSingleResult();
                if (indiceOI != null) {
                    return new IdentificadorDocumentoDTO(indiceOI.getIdDocumento(), indiceOI.getVersionDocumento());
                }
            } catch (NoResultException e) {
                return null;
            }
            // Es operador financiero    
        }
        else {
            try {
                IndicePlanillaOF indiceOF = entityManager
                        .createNamedQuery(NamedQueriesConstants.OBTENER_INDICE_PLANILLAS_OF, IndicePlanillaOF.class)
                        .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, idPlanilla).getSingleResult();
                if (indiceOF != null) {
                    return new IdentificadorDocumentoDTO(indiceOF.getIdDocumento(), indiceOF.getVersionDocumento());
                }
            } catch (NoResultException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#obtenerIndicePlanilla(java.lang.Long)
     */
    @Override
    public IndicePlanilla obtenerIndicePlanilla(Long idIndicePlanilla) {
        IndicePlanilla indicePlanilla = null;
        try {
            indicePlanilla = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INDICE_PLANILLAS, IndicePlanilla.class)
                    .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, idIndicePlanilla).getSingleResult();
            return indicePlanilla;
        } catch (NoResultException nre) {
            logger.error("evaluarEstadoArchivoPlanilla(InconsistenciaRegistroAporteDTO) :: No se pudo obtener el registro de la planilla ::"
                    + nre.getMessage());
            return null;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#totalInconsistenciasPendientes()
     */
    @Override
    public Long totalInconsistenciasPendientes() {
        String firmaMetodo = "totalInconsistenciasPendientes()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        Long result = 0L;

        try {
            List<String> estadosOI = obtenerListadoEstadosInconsistentes(1);
            List<String> estadosOF = obtenerListadoEstadosInconsistentes(2);
            List<String> estadosF6 = obtenerListadoEstadosInconsistentes(3);
              
            Object resultQuery = (Object) entityManager.createNamedQuery(NamedQueriesConstants.CONTEO_INCONSISTENCIAS_ERROR_TOTAL)
                    .setParameter("estados", estadosOI).setParameter("estadosOF", estadosOF).setParameter("estadosF6", estadosF6)
                    .getSingleResult();
            
            result = ((Integer) resultQuery).longValue();
            
        } catch (NoResultException nre) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return 0L;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#totalInconsistenciasPendientesAprobacion()
     */
    @Override
    public Long totalInconsistenciasPendientesAprobacion() {
        String firmaMetodo = "totalInconsistenciasPendientesAprobacion()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            Long result = entityManager.createNamedQuery(NamedQueriesConstants.CONTEO_INCONSISTENCIAS_IDENTIFICACION, Long.class)
                    .getSingleResult();
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return result;

        } catch (NoResultException nre) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return 0L;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#consultarEstadoBloquePlanillaXId(java.lang.Long)
     */
    @Override
    public EstadoArchivoPorBloque consultarEstadoBloquePlanillaXId(Long idPlanilla) {
        String firmaMetodo = "consultarEstadoBloquePlanillaXId(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            EstadoArchivoPorBloque estadoArchivoPorBloque = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_BLOQUE_PLANILLA_POR_ID_PLANILLA, EstadoArchivoPorBloque.class)
                    .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, idPlanilla).getSingleResult();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return estadoArchivoPorBloque;
        } catch (NoResultException nre) {
            logger.error(
                    "evaluarEstadoArchivoPlanilla(InconsistenciaRegistroAporteDTO) :: No se pudo obtener los registros detallados asociados al registro general de planilla::"
                            + nre.getMessage());
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#actualizarIndicePlanilla(com.asopagos.entidades.pila.procesamiento.IndicePlanilla)
     */
    @Override
    public void actualizarIndicePlanilla(IndicePlanilla indice) {
        try {
            entityManager.merge(indice);
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#actualizarEstadoBloque(com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloque,
     *      com.asopagos.entidades.pila.procesamiento.HistorialEstadoBloque)
     */
    @Override
    public void actualizarEstadoBloque(EstadoArchivoPorBloque estado, HistorialEstadoBloque historialEstado) {
        try {
            entityManager.merge(estado);
            entityManager.persist(historialEstado);
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#validarRespuestaCambioId(java.lang.Long)
     */
    @Override
    public InconsistenciaDTO validarRespuestaCambioId(Long idErrorValidacion) {
        logger.debug("inicia validarRespuestaCambioId(Long idErrorValidacion)");
        try {
            List<InconsistenciaDTO> result = (entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDAR_EXISTENCIA_RESPUESTA_SOLICITUD_CAMBIO_ID, InconsistenciaDTO.class)
                    .setParameter("idError", idErrorValidacion).getResultList());

            if (!result.isEmpty()) {
                if (result.get(0).getAccionCorreccion() == AccionCorreccionPilaEnum.REGISTRAR_RESPUESTA_CAMBIO_IDENTIFICACION) {
                    List<Long> indices = new ArrayList<>();
                    indices.add(result.get(0).getNumeroPlanilla());
                    establecerGestionInconsistencias(indices, EstadoGestionInconsistenciaEnum.INCONSISTENCIA_GESTIONADA,
                            BloqueValidacionEnum.BLOQUE_5_OI);
                }
                return result.get(0);
            }

        } catch (NoResultException nre) {
            logger.error("No se encontraron resultados");

        } catch (Exception e) {
            logger.error("Se presento un error al ejecutar la consulta ");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug("Finaliza validarRespuestaCambioId(Long idErrorValidacion)");
        return null;
    }

    /**
     * Metodo que permite consultar el archivo de operador financiero asociado a una planilla
     * 
     * @param numeroPlanilla
     *        <code>Long</code>
     *        Número de planilla asociada
     * @param tipoArchivoPila
     *        <code>TipoArchivoPilaEnum</code>
     *        El tipo de archivo esperados en el proceso de carga PILA.
     * @return <code>IndicePlanillaOF</code>
     *         Entidad que representa al índice de archivo de operador financiero
     */
    private IndicePlanilla consultarArchivosOIAsociados(InconsistenciaDTO inconsistenciaDTO, TipoArchivoPilaEnum tipoArchivoPila) {
        String firmaMetodo = "ConsultasModeloPILA.consultarArchivosOIAsociados(Long, TipoArchivoPilaEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        IndicePlanilla indicePlanilla = null;
        try {

            indicePlanilla = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVOS_ASOCIADOS_INFORMACION_2, IndicePlanilla.class)
                    .setParameter(ConstantesPilaBandeja.NUMERO_PLANILLA, inconsistenciaDTO.getNumeroPlanilla())
                    .setParameter(ConstantesPilaBandeja.TIPO_ARCHIVO, tipoArchivoPila)
                    .setParameter(ConstantesPilaBandeja.NOMBRE_PLANILLA, inconsistenciaDTO.getNombreArchivo())
                    .setParameter(ConstantesPilaBandeja.NOMBRE_PLANILLA_PAR, inconsistenciaDTO.getNombreArchivoPar()).getSingleResult();

        } catch (NonUniqueResultException nre) {
            logger.error(ConstantesPilaBandeja.ERROR_EN_CONSULTA
                    + ": Se encontraron varios indices I asociados al mismo numero de planilla Favor confirmar");
            logger.error(ConstantesPilaBandeja.ERROR_EN_CONSULTA, nre);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return indicePlanilla;

    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#consultarArchivosOFAsociados(java.lang.Long)
     */
    @Override
    public IndicePlanillaOF consultarArchivosOFAsociados(Long idPlanilla) {
        String firmaMetodo = "ConsultasModeloPILA.consultarArchivosOFAsociados(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        IndicePlanillaOF indicePlanillaOF = null;
        try {
            indicePlanillaOF = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVOS_ASOCIADOS_FINANCIERO, IndicePlanillaOF.class)
                    .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, idPlanilla).getSingleResult();
        } catch (NoResultException nre) {
            logger.debug(ConstantesComunes.FIN_LOGGER + "No se encontro archivo OF asociado a la planilla");
            return null;
        } catch (Exception e) {
            logger.error(ConstantesPilaBandeja.ERROR_EN_CONSULTA, e);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return indicePlanillaOF;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#consultarRazonSocialAportantes(java.util.List)
     */
    @Override
    public List<InconsistenciaDTO> consultarRazonSocialAportantes(List<InconsistenciaDTO> result) {
        String firmaMetodo = "ConsultasModeloPILA.consultarArchivosOFAsociados(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<InconsistenciaDTO> inconsistencias = result;
        String nombreAportanteEnArchivo = "";

        for (InconsistenciaDTO inc : inconsistencias) {
            try {
                Query query = null;

                switch (inc.getTipoArchivo()) {
                    case ARCHIVO_OI_I:
                    case ARCHIVO_OI_IR:
                        query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOMBRE_APORTANTE_ARCHIVO_I);
                        break;
                    case ARCHIVO_OI_IP:
                    case ARCHIVO_OI_IPR:
                        query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOMBRE_APORTANTE_ARCHIVO_IP);
                        break;
                    default:
                        break;

                }

                if (query != null) {
                    nombreAportanteEnArchivo = (String) query
                            .setParameter(ConstantesPilaBandeja.ID_INDICE_PLANILLA, inc.getIndicePlanilla()).getSingleResult();

                    inc.setRazonSocialAportante(nombreAportanteEnArchivo);
                }

            } catch (NoResultException e) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return inconsistencias;
            } catch (NonUniqueResultException e) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
            } catch (Exception e) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return inconsistencias;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#getionarInconsistenciaPorId(java.lang.Long)
     */
    @Override
    public void getionarInconsistenciaPorId(Long idLog) {
        String firmaMetodo = "ConsultasModeloPILA.getionarInconsistenciaPorId(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            ErrorValidacionLog error = entityManager.find(ErrorValidacionLog.class, idLog);
            if (error != null) {
                entityManager.merge(error);
                error.setEstadoInconsistencia(EstadoGestionInconsistenciaEnum.INCONSISTENCIA_GESTIONADA);
            }
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#actualizarActivosCambioIdAportanteEnBD
     *      (com.asopagos.bandejainconsistencias.dto.ActivosCorreccionIdAportanteDTO)
     */
    @Override
    public void actualizarActivosCambioIdAportanteEnBD(ActivosCorreccionIdAportanteDTO activos) {
        String firmaMetodo = "ConsultasModeloPILA.actualizarActivosCambioIdAportanteEnBD(ActivosCorreccionIdAportanteDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            entityManager.merge(activos.getIndiceI().convertToEntity());
            entityManager.merge(activos.getIndiceA().convertToEntity());
            entityManager.merge(activos.getEstadosI());
            entityManager.merge(activos.getRegistro1A());
            entityManager.merge(activos.getRegistro1I());
            if (activos.getRegistro6F() != null) {
                entityManager.merge(activos.getRegistro6F());
            }
            entityManager.merge(activos.getSolicitudCambio());
            entityManager.persist(activos.getHistorialEstadoI());
            entityManager.persist(activos.getHistorialEstadoIAprobacion());
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA#consultarIndicesOIporIdPlanilla(java.lang.Long)
     */
    @Override
    public IndicePlanillaModeloDTO consultarIndicesOIporIdPlanilla(Long idIndicePlanilla) {
        String firmaMetodo = "ConsultasModeloPILA.consultarIndicesOIporIdPlanilla(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        IndicePlanillaModeloDTO result = null;

        try {
            IndicePlanilla indice = entityManager.find(IndicePlanilla.class, idIndicePlanilla);
            result = new IndicePlanillaModeloDTO();
            result.convertToDTO(indice);
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /**
     * Método para obtener listados de estados con inconsistencias para consulta
     * */
    private List<String> obtenerListadoEstadosInconsistentes(Integer tipo){
        List<String> result = new ArrayList<>();
        
        switch(tipo){
            case 1: 
                result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS.name());
                result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_TAMANO_CERO.name());
                result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_TAMANO_EXCEDIDO.name());
                result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_TAMANO_EXCEDIDO_GRUPO.name());
                result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_EXTENSION_NO_VALIDA.name());
                result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_EXTENSION_DOBLE.name());
                result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_SIN_FECHA_MODIFICACION.name());
                result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_DUPLICADO.name());
                result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_DUPLICADO_ANTERIOR.name());
                result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_GRUPO_NO_VALIDO.name());
                result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_REPROCESO_PREVIO.name());
                result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_ANULACION_FALLIDA.name());
                result.add(EstadoProcesoArchivoEnum.INCONSISTENCIA_NOMBRE_ARCHIVO.name());
                result.add(EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA_CON_INCONSISTENCIA.name());
                result.add(EstadoProcesoArchivoEnum.PAREJA_DE_ARCHIVOS_INCONSISTENTES.name());
                result.add(EstadoProcesoArchivoEnum.APORTANTE_NO_CREADO_EN_BD.name());
                result.add(EstadoProcesoArchivoEnum.RECHAZADO.name());
                result.add(EstadoProcesoArchivoEnum.GESTIONAR_DIFERENCIA_EN_CONCILIACION.name());
                result.add(EstadoProcesoArchivoEnum.RECAUDO_VALOR_CERO_CONCILIADO.name());
                result.add(EstadoProcesoArchivoEnum.PENDIENTE_CONCILIACION.name());
                break;
            case 2:
                result.add(EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA_CON_INCONSISTENCIA.name());
                result.add(EstadoProcesoArchivoEnum.INCONSISTENCIA_NOMBRE_ARCHIVO.name());
                break;
            case 3:
                result.add(EstadoConciliacionArchivoFEnum.REGISTRO_6_GESTIONAR_DIFERENCIAS.name());
                break;
            default:
                break;
        }
        return result;
    }
    
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<IndicePlanilla> listaIndicePlanilla(List<Long> listaIdPlanilla) {
		return entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_LISTA_INDICE_PLANILLA, IndicePlanilla.class)
				.setParameter("listaIds", listaIdPlanilla)
				.getResultList();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TemNovedadModeloDTO> consultarNovedadesTemporales(Long idRegistroGeneral) {
		return entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TEM_NOVEDAD_POR_REGISTRO_GENERAL, TemNovedadModeloDTO.class)
				.setParameter("idRegistroGeneral", idRegistroGeneral)
				.getResultList();
	}
	
	@Override
	public Boolean actualizarEstadoEnProcesoAportes(Long indicePlanilla) {
		int totalAfectados = entityManager
				.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ESTADO_EN_PROCESO_APORTES)
				.setParameter("indicePlanilla", indicePlanilla).executeUpdate();
		return (totalAfectados > 0);
	}
}
