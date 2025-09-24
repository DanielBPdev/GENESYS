package com.asopagos.cartera.business.ejb;

import com.asopagos.cache.CacheManager;
import com.asopagos.cartera.business.interfaces.IConsultasModeloCore;
import com.asopagos.cartera.constants.NamedQueriesConstants;
import com.asopagos.cartera.dto.AportanteAccionCobroDTO;
import com.asopagos.cartera.dto.FiltrosParametrizacionDTO;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.CarteraDependienteModeloDTO;
import com.asopagos.dto.modelo.ParametrizacionCriteriosGestionCobroModeloDTO;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoOperacionCarteraEnum;
import com.asopagos.enumeraciones.cartera.MetodoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;


/**
 * <b>Descripcion:</b> Clase que implementa los métodos para las consultas de BD de Cartera<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
@Stateless
public class ConsultasModeloCore implements IConsultasModeloCore, Serializable {
    private static final long serialVersionUID = 4909092832526362223L;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloCore.class);

    /**
     * Entity Manager
     */
    @PersistenceContext(unitName = "cartera_PU")
    private EntityManager entityManager;

    /**
     * Constantes para nombramiento de parámetros
     */
    private static final String ACCION_COBRO_FUTURA = "accionCobroFutura";
    private static final String LINEA_COBRO = "lineaCobro";
    private static final String METODO = "metodo";
    private static final String CANTIDAD_VALORES = "iCantidadValores";
    private static final String NOMBRE_SECUENCIA = "sNombreSecuencia";
    private static final String PRIMER_VALOR_SECUENCIA = "iPrimerValor";

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.business.interfaces.IConsultasModeloCore#actualizarDeudaPresuntaCartera(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.lang.String, com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void actualizarDeudaPresuntaCartera(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                               String periodoEvaluacion, TipoSolicitanteMovimientoAporteEnum tipoAportante) {
        String firmaMetodo = "ConsultasModeloCore.actualizarDeudaPresuntaCartera(TipoIdentificacionEnum, String, String, "
                + "TipoSolicitanteMovimientoAporteEnum)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            logger.info("actualizarDeudaPresuntaCartera:::: tipoIdentificacion" + tipoIdentificacion + "numeroIdentificacion" + numeroIdentificacion + "periodoEvaluacion" + periodoEvaluacion + "tipoAportante" + tipoAportante);
            StoredProcedureQuery query = entityManager
                    .createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_EXECUTE_CARTERA_ACTUALIZAR_CARTERA);
            query.setParameter("tipoIdentificacion", tipoIdentificacion.name());
            query.setParameter("numeroIdentificacion", numeroIdentificacion);
            query.setParameter("tipoAportante", tipoAportante.name());
            query.setParameter("periodoEvaluacion", periodoEvaluacion);
            query.execute();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);

    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.business.interfaces.IConsultasModeloCore#asignarAccionCobro(com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AportanteAccionCobroDTO> asignarAccionCobro(TipoAccionCobroEnum accionCobro) {
        String firmaMetodo = "ConsultasModeloCore.asignarAccionCobro(TipoAccionCobroEnum)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<AportanteAccionCobroDTO> listaFinal = new ArrayList<>();

        // Ejecutar para LC1, LC4 y LC5
        TipoLineaCobroEnum[] lineas = {TipoLineaCobroEnum.LC1, TipoLineaCobroEnum.LC4, TipoLineaCobroEnum.LC5};

        for (TipoLineaCobroEnum linea : lineas) {
            listaFinal.addAll(ejecutarAsignacionSP(accionCobro, linea));
        }

        // Eliminar duplicados por numeroIdentificacion
        Map<String, AportanteAccionCobroDTO> sinDuplicados = new HashMap<>();
        for (AportanteAccionCobroDTO dto : listaFinal) {
            sinDuplicados.put(dto.getNumeroIdentificacion(), dto);
        }
        listaFinal = new ArrayList<>(sinDuplicados.values());

        logger.info("listaFinal:" + listaFinal.size());
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listaFinal;
    }



    private List<AportanteAccionCobroDTO> ejecutarAsignacionSP(TipoAccionCobroEnum accionCobro, TipoLineaCobroEnum lineaCobro) {
        String firmaMetodo = "ConsultasModeloCore.ejecutarAsignacionSP";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<Object[]> lista;
        List<AportanteAccionCobroDTO> resultado = new ArrayList<>();

        try {
            StoredProcedureQuery query = entityManager
                    .createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_EXECUTE_CARTERA_ASIGNAR_ACCION_COBRO);
            query.setParameter(ACCION_COBRO_FUTURA, accionCobro.name());
            query.setParameter(LINEA_COBRO, lineaCobro.name());
            query.setParameter(METODO,
                    accionCobro.getMetodo() != null ? accionCobro.getMetodo().name() : MetodoAccionCobroEnum.METODO_1.name());
            query.execute();

            lista = (List<Object[]>) query.getResultList();
            
            if (lista != null && !lista.isEmpty()) {
                for (Object[] registro : lista) {
                    AportanteAccionCobroDTO dto = new AportanteAccionCobroDTO();
                    //dto.setAutorizaEnvioCorreo(registro[0] != null ? Boolean.parseBoolean(registro[0].toString()) : Boolean.FALSE);
                    dto.setAutorizaEnvioCorreo(Boolean.TRUE);
                    logger.info(dto.getAutorizaEnvioCorreo());
                    dto.setTipoAportante(TipoSolicitanteMovimientoAporteEnum.valueOf(registro[1].toString()));
                    dto.setIdCartera(Long.parseLong(registro[2].toString()));
                    dto.setAccionCobro(TipoAccionCobroEnum.valueOf(registro[3].toString()));
                    dto.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(registro[4].toString()));
                    dto.setNumeroIdentificacion(registro[5].toString());

                    resultado.add(dto);
                }
            }

            if (lista != null && !lista.isEmpty()) {
                for (Object[] registro : lista) {
                        logger.info("registro: " + Arrays.toString(registro));
                    }
                }

        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.business.interfaces.IConsultasModeloCore#crearRegistroCartera()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void crearRegistroCartera() {
        String firmaMetodo = "ConsultasModeloCore.crearRegistroCartera()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            StoredProcedureQuery query = entityManager
                    .createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_EXECUTE_CARTERA_CREAR_CARTERA);
            query.execute();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.business.interfaces.IConsultasModeloCore#obtenerValoresSecuencia(java.lang.Integer, java.lang.Integer)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Long> obtenerValoresSecuencia(String nombreSecuencia, Integer cantidad) {
        String firmaMetodo = "ConsultasModeloCore.obtenerValoresSecuencia(Integer, Integer)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Long> result = new ArrayList<>();

        try {
            if (cantidad != 0) {
                StoredProcedureQuery query = entityManager
                        .createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_GESTOR_VALOR_SECUENCIA);

                query.setParameter(CANTIDAD_VALORES, cantidad);
                query.setParameter(NOMBRE_SECUENCIA, nombreSecuencia);

                query.execute();

                Long primerValor = (Long) query.getOutputParameterValue(PRIMER_VALOR_SECUENCIA);
                Long ultimoValor = primerValor + cantidad - 1;

                for (long i = primerValor; i <= ultimoValor; i++) {
                    result.add(i);
                }
            }
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.business.interfaces.IConsultasModeloCore#guardarCarteraDependiente(com.asopagos.dto.modelo.CarteraDependienteModeloDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void guardarCarteraDependiente(CarteraDependienteModeloDTO carteraDependienteDTO) {
        String firmaMetodo = "ConsultasModeloCore.guardarCarteraDependiente(CarteraDependienteModeloDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            logger.info("carteraDependienteDTO:" + objectMapper.writeValueAsString(carteraDependienteDTO));
            entityManager.merge(carteraDependienteDTO.convertToEntity());
            logger.debug("Finaliza guardarCarteraDependiente");
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.business.interfaces.IConsultasModeloCore#consultarCarteraCotizantesAportanteLC(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum, java.lang.Long, java.lang.Long,
     * java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CarteraDependienteModeloDTO> consultarCarteraCotizantesAportanteLC(TipoIdentificacionEnum tipoIdentificacion,
                                                                                   String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante, Long periodo, Long idCartera,
                                                                                   TipoLineaCobroEnum lineaCobro) {
        String firmaMetodo = "ConsultasModeloCore.consultarCarteraCotizantesAportanteLC(TipoIdentificacionEnum, String, "
                + "TipoSolicitanteMovimientoAporteEnum, Long, Long, TipoLineaCobroEnum)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<CarteraDependienteModeloDTO> listaCarteraDependienteDTO = null;

        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            String fecha = dateFormat.format(new Date(periodo));
            List<Object[]> listaCarteraDependiente = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CARTERA_COTIZANTES_APORTANTE)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoSolicitante", tipoSolicitante.name())
                    .setParameter("periodo", fecha).setParameter("idCartera", idCartera).setParameter(LINEA_COBRO, lineaCobro != null ? lineaCobro.name() : null)
                    .getResultList();

            listaCarteraDependienteDTO = new ArrayList<>();

            for (Object[] registro : listaCarteraDependiente) {
                CarteraDependienteModeloDTO carteraDependienteDTO = new CarteraDependienteModeloDTO();
                carteraDependienteDTO.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(registro[0].toString()));
                carteraDependienteDTO.setNumeroIdentificacion(registro[1].toString());
                carteraDependienteDTO.setPrimerNombre(registro[2].toString());
                carteraDependienteDTO.setSegundoNombre(registro[3] != null ? registro[3].toString() : null);
                carteraDependienteDTO.setPrimerApellido(registro[4].toString());
                carteraDependienteDTO.setSegundoApellido(registro[5] != null ? registro[5].toString() : null);
                //carteraDependienteDTO.setDeudaPresunta(new BigDecimal(registro[6].toString()));
                carteraDependienteDTO.setDeudaPresunta(registro[6] != null ? new BigDecimal(registro[6].toString()) : BigDecimal.ZERO);
                carteraDependienteDTO.setDeudaReal(registro[7] != null ? new BigDecimal(registro[7].toString()) : BigDecimal.ZERO);
                carteraDependienteDTO.setEstadoCotizante(registro[8] != null ? EstadoAfiliadoEnum.valueOf(registro[8].toString())
                        : EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION);
                carteraDependienteDTO.setIdCarteraDependiente(registro[9] != null ? Long.parseLong(registro[9].toString()) : null);
                carteraDependienteDTO.setIdPersona(Long.parseLong(registro[10].toString()));
                carteraDependienteDTO.setEstadoOperacion(EstadoOperacionCarteraEnum.valueOf(registro[11].toString()));
                carteraDependienteDTO.setIdCartera(Long.parseLong(registro[12].toString()));
                carteraDependienteDTO.setAgregadoManualmente(registro[13] != null && Integer.parseInt(registro[13].toString()) == 1 ? Boolean.TRUE : Boolean.FALSE);
                listaCarteraDependienteDTO.add(carteraDependienteDTO);
            }
        } catch (NoResultException e) {
            listaCarteraDependienteDTO = Collections.emptyList();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listaCarteraDependienteDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.business.interfaces.IConsultasModeloCore#consultarAportantesGestionCobroEmpleador(com.asopagos.dto.modelo.ParametrizacionCriteriosGestionCobroModeloDTO, com.asopagos.cartera.dto.FiltrosParametrizacionDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public StoredProcedureQuery consultarAportantesGestionCobroEmpleador(ParametrizacionCriteriosGestionCobroModeloDTO parametrizacion,
                                                                         FiltrosParametrizacionDTO filtrosParametrizacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarAportantesGestionCobroEmpleador(ParametrizacionCriteriosGestionCobroModeloDTO parametrizacion, FiltrosParametrizacionDTO filtrosParametrizacion)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {
            BigDecimal promedio = parametrizacion.getMayorValorPromedio() == 0 ? BigDecimal.ZERO
                    : new BigDecimal(parametrizacion.getMayorValorPromedio());
            promedio = promedio.divide(new BigDecimal(100));

            BigDecimal activos = parametrizacion.getMayorTrabajadoresActivos() == 0 ? BigDecimal.ZERO
                    : new BigDecimal(parametrizacion.getMayorTrabajadoresActivos());
            activos = activos.divide(new BigDecimal(100));

            BigDecimal vecesMora = parametrizacion.getMayorVecesMoroso() == 0 ? BigDecimal.ZERO
                    : new BigDecimal(parametrizacion.getMayorVecesMoroso());
            vecesMora = vecesMora.divide(new BigDecimal(100));

            BigDecimal valorPromedioAportes = new BigDecimal(parametrizacion.getValorPromedioAportes().getCantidadSalarios());
            StoredProcedureQuery procedimiento = entityManager
                    .createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_APORTANTES_GESTION_COBRO)
                    .setParameter("estadoAfiliacion", EstadoEmpleadorEnum.ACTIVO.name())
                    .setParameter("estadoCartera", filtrosParametrizacion.getEstadosCartera().get(0))
                    .setParameter(LINEA_COBRO, parametrizacion.getLineaCobro().name())
                    .setParameter("estadoOperacion", EstadoOperacionCarteraEnum.VIGENTE.name())
                    .setParameter("valorPromedioAportes", valorPromedioAportes)
                    .setParameter("periodoAporteInicial", filtrosParametrizacion.getPeriodoInicialAportesEmpleador())
                    .setParameter("periodoAporteFinal", filtrosParametrizacion.getPeriodoFinalAportesEmpleador())
                    .setParameter("trabajadoresActivos", parametrizacion.getTrabajadoresActivos().getCantidad())
                    .setParameter("aplicaCriterio", filtrosParametrizacion.getAplicarCriterio())
                    .setParameter("corte", parametrizacion.getCorteEntidades()).setParameter("criterio1Promedio", promedio)
                    .setParameter("criterio2Activos", activos).setParameter("criterio3VecesMoroso", vecesMora);

            if (filtrosParametrizacion.getAplicarCriterio()) {
                procedimiento.setParameter("periodoEvaluarInicial", filtrosParametrizacion.getPeriodoInicialMorosoEmpleador())
                        .setParameter("periodoEvaluarFinal", filtrosParametrizacion.getPeriodoFinalMorosoEmpleador());
            } else {
                procedimiento.setParameter("periodoEvaluarInicial", new Date())
                        .setParameter("periodoEvaluarFinal", new Date());
            }
            procedimiento.execute();
            return procedimiento;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.business.interfaces.IConsultasModeloCore#consultarAportantesGestionCobroPersona(com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum, com.asopagos.dto.modelo.ParametrizacionCriteriosGestionCobroModeloDTO, com.asopagos.cartera.dto.FiltrosParametrizacionDTO)
     */
    @Override
    public StoredProcedureQuery consultarAportantesGestionCobroPersona(TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                                                       ParametrizacionCriteriosGestionCobroModeloDTO parametrizacion, FiltrosParametrizacionDTO filtrosParametrizacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarAportantesGestionCobroPersona(TipoSolicitanteMovimientoAporteEnum tipoSolicitante,ParametrizacionCriteriosGestionCobroModeloDTO parametrizacion, FiltrosParametrizacionDTO filtrosParametrizacion)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {
            BigDecimal promedio = parametrizacion.getMayorValorPromedio() == 0 ? BigDecimal.ZERO
                    : new BigDecimal(parametrizacion.getMayorValorPromedio());
            promedio = promedio.divide(new BigDecimal(100));

            BigDecimal vecesMora = parametrizacion.getMayorVecesMoroso() == 0 ? BigDecimal.ZERO
                    : new BigDecimal(parametrizacion.getMayorVecesMoroso());
            vecesMora = vecesMora.divide(new BigDecimal(100));

            BigDecimal valorPromedioAportes = new BigDecimal(parametrizacion.getValorPromedioAportes().getCantidadSalarios());

            String tipoAfiliado = TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.equals(tipoSolicitante)
                    ? TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.name() : TipoAfiliadoEnum.PENSIONADO.name();

            StoredProcedureQuery procedimiento = entityManager
                    .createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_APORTANTES_GESTION_COBRO_PERSONA)
                    .setParameter("estadoAfiliacion", EstadoAfiliadoEnum.ACTIVO.name())
                    .setParameter("estadoCartera", filtrosParametrizacion.getEstadosCartera().get(0))
                    .setParameter(LINEA_COBRO, parametrizacion.getLineaCobro().name())
                    .setParameter("estadoOperacion", EstadoOperacionCarteraEnum.VIGENTE.name())
                    .setParameter("valorPromedioAportes", valorPromedioAportes)
                    .setParameter("periodoAporteInicial", filtrosParametrizacion.getPeriodoInicialAportes())
                    .setParameter("periodoAporteFinal", filtrosParametrizacion.getPeriodoFinalAportes())
                    .setParameter("aplicaCriterio", filtrosParametrizacion.getAplicarCriterio())
                    .setParameter("periodoEvaluarInicial", filtrosParametrizacion.getPeriodoInicialMoroso())
                    .setParameter("periodoEvaluarFinal", filtrosParametrizacion.getPeriodoFinalMoroso())
                    .setParameter("corte", parametrizacion.getCorteEntidades()).setParameter("criterio1Promedio", promedio)
                    .setParameter("criterio3VecesMoroso", vecesMora).setParameter("tipoSolicitante", tipoSolicitante.name())
                    .setParameter("tipoAfiliado", tipoAfiliado);

            procedimiento.execute();
            return procedimiento;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.business.interfaces.IConsultasModeloCore#consultarAportantesDesafiliacion(com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum)
     */
    @Override
    public StoredProcedureQuery consultarAportantesDesafiliacion(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        String firmaMetodo = "ConsultasModeloCore.consultarAportantesDesafiliacion(TipoSolicitanteMovimientoAporteEnum tipoSolicitante)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        TipoLineaCobroEnum lineaCobro;
        switch (tipoSolicitante) {
            case EMPLEADOR:
                lineaCobro = TipoLineaCobroEnum.LC1;
                break;
            case INDEPENDIENTE:
                lineaCobro = TipoLineaCobroEnum.LC4;
                break;
            default:
                lineaCobro = TipoLineaCobroEnum.LC5;
                break;
        }

        StoredProcedureQuery procedimiento = entityManager
                .createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_APORTANTES_DESAFILIACION)
                .setParameter("tipoSolicitante", tipoSolicitante.name()).setParameter("lineaCobro", lineaCobro.name())
                .setParameter("estadoOperacion", EstadoOperacionCarteraEnum.VIGENTE.name())
                .setParameter("estadoAfiliado", EstadoAfiliadoEnum.ACTIVO.name());
        procedimiento.execute();
        return procedimiento;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.business.interfaces.IConsultasModeloCore#consultarCriteriosGestionCobroLinea(java.util.List)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ParametrizacionCriteriosGestionCobroModeloDTO> consultarCriteriosGestionCobroLinea(List<TipoLineaCobroEnum> lineasCobro) {
        try {
            logger.debug("Inicio de servicio consultarCriteriosGestionCobro(TipoLineaCobroEnum lineaCobro)");
            List<ParametrizacionCriteriosGestionCobroModeloDTO> parametrizacion = new ArrayList<>();
            Boolean activa = true;
            /* si es línea de cobro 1 se consulta por linea y método */
            Query q = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CRITERIO_GESTION_DE_COBRO_POR_LINEA)
                    .setParameter("tipoLineaCobro", lineasCobro).setParameter("activa", activa);
            parametrizacion.addAll(q.getResultList());
            logger.debug("Fin de servicio consultarCriteriosGestionCobro(TipoLineaCobroEnum lineaCobro)");
            return parametrizacion;
        } catch (NoResultException nre) {
            logger.debug("No hay parametrizado un criterio para las lineas de cobro");
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.business.interfaces.IConsultasModeloCore#actualizarRegistrosCarteraOK()
     */
    @Override
    public void actualizarRegistrosCarteraOK() {
        String firmaMetodo = "ConsultasModeloCore.actualizarRegistrosCarteraOK()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_EXECUTE_CARTERA_ACTUALIZAR_CARTERA_OK);
        query.execute();
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.business.interfaces.IConsultasModeloCore#obtenerValoresSecuencia(java.lang.Integer, java.lang.Integer)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public BigDecimal obtenerDeudaPresunta(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                           TipoSolicitanteMovimientoAporteEnum tipoAportante, String periodoEvaluacion) {
        String firmaMetodo = "ConsultasModeloCore.obtenerDeudaPresunta(String, String, TipoSolicitanteMovimientoAporteEnum tipoAportante, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        BigDecimal deuda = BigDecimal.ZERO;

        try {
            StoredProcedureQuery query = entityManager
                    .createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_OBTENER_DEUDA_PRESUNTA);

            query.setParameter("tipoIdentificacion", tipoIdentificacion.name());
            query.setParameter("numeroIdentificacion", numeroIdentificacion);
            query.setParameter("periodoEvaluacion", periodoEvaluacion);
            query.setParameter("tipoAportante", tipoAportante.name());
            query.execute();

            deuda = (BigDecimal) query.getOutputParameterValue("deuda");

            logger.info("Tipo identificación: " + tipoIdentificacion + " Número identificación: " + numeroIdentificacion
                    + " Tipo Aportante: " + tipoAportante + " Periodo: " + periodoEvaluacion);
            logger.info("deuda: " + deuda);

        } catch (Exception e) {
            logger.info("Tipo identificación: " + tipoIdentificacion + " Número identificación: " + numeroIdentificacion
                    + " Tipo Aportante: " + tipoAportante + " Periodo: " + periodoEvaluacion);
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return deuda;
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.business.interfaces.IConsultasModeloCore#asignarAccionCobro(com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AportanteAccionCobroDTO> enviarComunicadoH2C2ToF1C6(TipoAccionCobroEnum accionCobro) throws JsonProcessingException {
        String firmaMetodo = "ConsultasModeloCore.asignarAccionCobro(TipoAccionCobroEnum)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Object[]> lista;
        Object aplicaEnvioComunicado;
        Integer diasEnvioComunicado = Integer.valueOf(CacheManager.getConstante(ConstantesSistemaConstants.DIAS_ENVIO_COMUNICADO_CARTERA_NOTIFICACION_MORA_H2C2_F1C6).toString());
        List<AportanteAccionCobroDTO> listaAportanteAccionCobroDTO = new ArrayList<>();

        try {

            lista = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CARTERA_ENVIO_COMUNICADO_H2C6_F1C6)
                    .setParameter("accionCobro", accionCobro.getDescripcion().toString())
                    .setParameter("lineaCobro", accionCobro.getLineaCobro().toString())
                    .setParameter("metodo", accionCobro.getMetodo().toString())
                    .getResultList();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        if (lista != null && !lista.isEmpty()) {
            for (Object[] registro : lista) {
                aplicaEnvioComunicado = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_CARTERA_FUNCION_CALCULO_DIAS_ENVIO_COMUNICADO_H2C2_F1C6)
                        .setParameter("numeroIdentificacion", registro[6].toString())
                        .setParameter("diasConstante", diasEnvioComunicado)
                        .setParameter("metodo", accionCobro.getMetodo().toString())
                        .setParameter("acciondeCobro", accionCobro.getDescripcion().toString())
                        .getSingleResult();
                if (Integer.valueOf(aplicaEnvioComunicado.toString()) == 1) {
                    AportanteAccionCobroDTO aportanteAccionCobroDTO = new AportanteAccionCobroDTO();
                    aportanteAccionCobroDTO.setAutorizaEnvioCorreo(Boolean.TRUE);
                    aportanteAccionCobroDTO.setTipoAportante(TipoSolicitanteMovimientoAporteEnum.valueOf((String) registro[2]));
                    aportanteAccionCobroDTO.setIdCartera(Long.parseLong(registro[3].toString()));
                    aportanteAccionCobroDTO.setAccionCobro(TipoAccionCobroEnum.valueOf(registro[4].toString()));
                    aportanteAccionCobroDTO.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(registro[5].toString()));
                    aportanteAccionCobroDTO.setNumeroIdentificacion(registro[6].toString());
                    listaAportanteAccionCobroDTO.add(aportanteAccionCobroDTO);
                }

            }
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listaAportanteAccionCobroDTO;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AportanteAccionCobroDTO> enviarComunicadoExpulsionH2C2ToF1C6(TipoAccionCobroEnum accionCobro) {
        logger.info("ConsultasModeloCore.enviarComunicadoExpulsionH2C2ToF1C6" + accionCobro);
        List<AportanteAccionCobroDTO> listaAportanteAccionCobroDTO = new ArrayList<>();
        List<Object[]> lista;
        List<Object[]> lista2;
        ObjectMapper objectMapper = new ObjectMapper();
        lista2 = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CARTERA_ENVIO_COMUNICADO_H2C6_F1C6_EXPULSION_PRUEBA)
                .getResultList();
        try {
            logger.info("CONSULTAR_CARTERA_ENVIO_COMUNICADO_H2C6_F1C6_EXPULSION_PRUEBA:::" + objectMapper.writeValueAsString(lista2));

            lista = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CARTERA_ENVIO_COMUNICADO_H2C6_F1C6_EXPULSION)
                    .setParameter("accionCobro", accionCobro.getDescripcion().toString())
                    .setParameter("lineaCobro", accionCobro.getLineaCobro().toString())
                    .setParameter("metodo", accionCobro.getMetodo().toString())
                    .getResultList();
        } catch (Exception e) {
            logger.error("ConsultasModeloCore.enviarComunicadoExpulsionH2C2ToF1C6" + accionCobro + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        if (lista != null && !lista.isEmpty()) {
            for (Object[] registro : lista) {
                AportanteAccionCobroDTO aportanteAccionCobroDTO = new AportanteAccionCobroDTO();
                aportanteAccionCobroDTO.setAutorizaEnvioCorreo(Boolean.TRUE);
                aportanteAccionCobroDTO.setTipoAportante(TipoSolicitanteMovimientoAporteEnum.valueOf((String) registro[2]));
                aportanteAccionCobroDTO.setIdCartera(Long.parseLong(registro[3].toString()));
                aportanteAccionCobroDTO.setAccionCobro(TipoAccionCobroEnum.valueOf(registro[4].toString()));
                aportanteAccionCobroDTO.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(registro[5].toString()));
                aportanteAccionCobroDTO.setNumeroIdentificacion(registro[6].toString());
                listaAportanteAccionCobroDTO.add(aportanteAccionCobroDTO);
            }
        }
        logger.info("ConsultasModeloCore.enviarComunicadoExpulsionH2C2ToF1C6 Fin" + listaAportanteAccionCobroDTO);
        return listaAportanteAccionCobroDTO;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AportanteAccionCobroDTO> obtenerAportantesParaExpulsionPorIds(TipoAccionCobroEnum accionCobro, List<Long> idPersonasAProcesar) {
        logger.info("ConsultasModeloCore.obtenerAportantesParaExpulsionPorIds: " + accionCobro.toString() + ", idPersonasAProcesar.size(): " + idPersonasAProcesar.size());
        logger.info("accionCobro: " + accionCobro.getDescripcion().toString() + ", lineaCobro: " + accionCobro.getLineaCobro().toString() + ", metodo: " + accionCobro.getMetodo().toString());
        List<AportanteAccionCobroDTO> listaAportanteAccionCobroDTO = new ArrayList<>();
        List<Object[]> lista;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            lista = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CARTERA_ENVIO_COMUNICADO_H2C6_F1C6_EXPULSION_POR_IDS)
                .setParameter("accionCobro", accionCobro.getDescripcion().toString())
                .setParameter("lineaCobro", accionCobro.getLineaCobro().toString())
                .setParameter("metodo", accionCobro.getMetodo().toString())
                .setParameter("idPersonasAProcesar", idPersonasAProcesar)
                .getResultList();
        } catch (Exception e) {
            logger.error("ConsultasModeloCore.obtenerAportantesParaExpulsionPorIds" + accionCobro + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        if (lista != null && !lista.isEmpty()) {
            for (Object[] registro : lista) {
                AportanteAccionCobroDTO aportanteAccionCobroDTO = new AportanteAccionCobroDTO();
                aportanteAccionCobroDTO.setAutorizaEnvioCorreo(Boolean.TRUE);
                aportanteAccionCobroDTO.setTipoAportante(TipoSolicitanteMovimientoAporteEnum.valueOf((String) registro[2]));
                aportanteAccionCobroDTO.setIdCartera(Long.parseLong(registro[3].toString()));
                aportanteAccionCobroDTO.setAccionCobro(TipoAccionCobroEnum.valueOf(registro[4].toString()));
                aportanteAccionCobroDTO.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(registro[5].toString()));
                aportanteAccionCobroDTO.setNumeroIdentificacion(registro[6].toString());
                listaAportanteAccionCobroDTO.add(aportanteAccionCobroDTO);
            }
        }
        logger.info("ConsultasModeloCore.obtenerAportantesParaExpulsionPorIds Fin " + listaAportanteAccionCobroDTO.size() + " resultados");
        return listaAportanteAccionCobroDTO;
    }

    public String consultarActividadCarIdNumeroIdentificacion(Long carId, String perNumeroIdentificacion) {
        Object result;
        result = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ACTIVIDAD_PERID_CARID)
                .setParameter("carId", carId)
                .setParameter("perNumeroIdentificacion", perNumeroIdentificacion)
                .getSingleResult();
        return String.valueOf(result);
    }
    
}
