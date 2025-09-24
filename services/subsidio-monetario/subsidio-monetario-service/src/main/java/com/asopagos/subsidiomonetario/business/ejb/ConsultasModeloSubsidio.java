package com.asopagos.subsidiomonetario.business.ejb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TemporalType;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;

import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.subsidiomonetario.liquidacion.BeneficiariosAfiliadoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.CondicionEntidadDescuentoLiquidacionDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.CondicionPersonaLiquidacionDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DescuentoEntidadBeneficiarioFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DetalleLiquidacionBeneficiarioFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionResultadoMedioPagoFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ItemDetalleLiquidacionBeneficiarioFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ItemDispersionResultadoMedioPagoFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ItemResultadoLiquidacionFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.KeyPersonaDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.PersonaFallecidaTrabajadorDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoLiquidacionFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoPorAdministradorLiquidacionFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ResumenResultadosEvaluacionDTO;
import com.asopagos.dto.subsidiomonetario.pagos.InformacionGrupoFamiliarDTO;
import com.asopagos.dto.subsidiomonetario.pagos.ItemSubsidioBeneficiarioDTO;
import com.asopagos.dto.subsidiomonetario.pagos.SubsidioAfiliadoDTO;
import com.asopagos.entidades.subsidiomonetario.liquidacion.BloqueoBeneficiarioCuotaMonetaria;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoTarjetaMultiserviciosEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.EstadoDerechoSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.dispersion.EstadoCumplimientoPersonaFallecimientoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ConjuntoValidacionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoAporteSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.GrupoAplicacionValidacionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ItemsCantidadEmpresaTrabajadorEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ItemsMontoLiquidadoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoCumplimientoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoPeriodoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoRetroactivoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoCuotaSubsidioEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.pagination.QueryBuilder;
import com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio;
import com.asopagos.subsidiomonetario.constants.NamedQueriesConstants;
import com.asopagos.subsidiomonetario.dto.BloqueoBeneficiarioCuotaMonetariaDTO;
import com.asopagos.subsidiomonetario.dto.CantidadEmpresaTrabajadorDTO;
import com.asopagos.subsidiomonetario.dto.ConsultaBeneficiarioBloqueadosDTO;
import com.asopagos.subsidiomonetario.dto.ConsultaDescuentosSubsidioTrabajadorGrupoDTO;
import com.asopagos.subsidiomonetario.dto.ConsultaLiquidacionSubsidioMonetarioDTO;
import com.asopagos.subsidiomonetario.dto.DetalleCantidadEmpresaTrabajadorDTO;
import com.asopagos.subsidiomonetario.dto.DetalleItemEmpresaTrabajadorDTO;
import com.asopagos.subsidiomonetario.dto.DetalleResultadoPorAdministradorDTO;
import com.asopagos.subsidiomonetario.dto.ItemBeneficiarioPorAdministradorDTO;
import com.asopagos.subsidiomonetario.dto.RegistroLiquidacionSubsidioDTO;
import com.asopagos.subsidiomonetario.dto.RegistroSinDerechoSubsidioDTO;
import com.asopagos.subsidiomonetario.dto.RespuestaVerificarPersonasSinCondicionesDTO;
import com.asopagos.subsidiomonetario.dto.ResultadoLiquidacionMasivaDTO;
import com.asopagos.subsidiomonetario.dto.ResultadoMontoLiquidadoDTO;
import com.asopagos.subsidiomonetario.dto.TemporalAsignacionDerechoDTO;
import com.asopagos.subsidiomonetario.load.source.ArchivoLiquidacionFilterDTO;
import com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionCondicionesSubsidioModeloDTO;
import com.asopagos.subsidiomonetario.util.SubsidioDateUtils;
import com.asopagos.util.CalendarUtils;
import com.asopagos.entidades.subsidiomonetario.liquidacion.BloqueoAfiliadoBeneficiarioCM;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.time.Instant;
import java.text.SimpleDateFormat;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de información en
 * el modelo de datos subsidio <br/>
 * <b>Módulo:</b> Asopagos - HU-311-435<br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 */

@Stateless
public class ConsultasModeloSubsidio implements IConsultasModeloSubsidio, Serializable {

    private static final long serialVersionUID = 6909763347730963359L;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloCore.class);

    /** Entity Manager */
    @PersistenceContext(unitName = "subsidio_PU")
    private EntityManager entityManagerSubsidio;

    final String cadenaVacia = "";

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#iniciarLiquidacionMasiva(java.lang.String,
     *      java.lang.Long)
     */
    //@Asynchronous
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void iniciarLiquidacionMasiva(String numeroRadicado, Long periodo) {
        String firmaMetodo = "ConsultasModeloSubsidio.iniciarLiquidacionMasiva(String, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        /*
         * try{
         * iniciarLiquidacionMasivaAsync(numeroRadicado, periodo);
         * }catch(Exception e){
         * logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
         * throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
         * }
         */
        StoredProcedureQuery storedProcedure = entityManagerSubsidio
                .createStoredProcedureQuery(NamedQueriesConstants.EJECUTAR_SP_ORQUESTADOR_LIQUIDACION_MASIVA);
        storedProcedure.registerStoredProcedureParameter("sNumeroRadicado", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("dPeriodo", Date.class, ParameterMode.IN);
        storedProcedure.setParameter("dPeriodo", new Date(periodo));
        storedProcedure.setParameter("sNumeroRadicado", numeroRadicado);
        storedProcedure.execute();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

//    /**
//     * Operacion asyncrona para ejecutar
//     * @param fechaActual
//     */
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    private void iniciarLiquidacionMasivaAsync(String numeroRadicado, Long periodo) {
//        String firmaMetodo = "ConsultasModeloSubsidio.iniciarLiquidacionMasivaAsync(String, Long)";
//        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
//
//        StoredProcedureQuery storedProcedure = entityManagerSubsidio
//                .createStoredProcedureQuery(NamedQueriesConstants.EJECUTAR_SP_ORQUESTADOR_LIQUIDACION_MASIVA);
//        storedProcedure.registerStoredProcedureParameter("sNumeroRadicado", String.class, ParameterMode.IN);
//        storedProcedure.registerStoredProcedureParameter("dPeriodo", Date.class, ParameterMode.IN);
//        storedProcedure.setParameter("dPeriodo", new Date(periodo));
//        storedProcedure.setParameter("sNumeroRadicado", numeroRadicado);
//        storedProcedure.execute();
//
//        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
//    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#ejecutarOrquestadorStagin()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void ejecutarOrquestadorStagin(Long fechaActual) {
        String firmaMetodo = "ConsultasModeloSubsidio.ejecutarOrquestadorStagin()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            StoredProcedureQuery storedProcedure = entityManagerSubsidio
                    .createStoredProcedureQuery(NamedQueriesConstants.EJECUTAR_SP_ORQUESTADOR_STAGING);
            storedProcedure.registerStoredProcedureParameter("dFechaAuditoria", Date.class, ParameterMode.IN);
            storedProcedure.setParameter("dFechaAuditoria", new Date(fechaActual));
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
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#ejecutarOrquestadorStaginFallecimiento()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void ejecutarOrquestadorStaginFallecimiento(Long fechaActual, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.ejecutarOrquestadorStaginFallecimiento()";
        String tipoIdentificacionString = String.valueOf(tipoIdentificacion); 
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo + "..."+tipoIdentificacion);
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo + "..."+tipoIdentificacionString);

        try {
            StoredProcedureQuery storedProcedure = entityManagerSubsidio
                    .createStoredProcedureQuery(NamedQueriesConstants.EJECUTAR_SP_ORQUESTADOR_STAGING_FALLECIMIENTO);
            storedProcedure.registerStoredProcedureParameter("dFechaAuditoria", Date.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("TipoIdentificacionAfi", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("numeroIdentificacionAfi", String.class, ParameterMode.IN);
            storedProcedure.setParameter("dFechaAuditoria", new Date(fechaActual));
            storedProcedure.setParameter("TipoIdentificacionAfi", tipoIdentificacionString);
            storedProcedure.setParameter("numeroIdentificacionAfi", numeroIdentificacion);
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
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#ejecutarOrquestadorStaginIntervaloFechas(java.lang.Long,
     *      java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void ejecutarOrquestadorStaginIntervaloFechas(Long fechaInicio, Long fechaFin) {
        String firmaMetodo = "ConsultasModeloSubsidio.ejecutarOrquestadorStagin()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Date finFecha;
        if (fechaFin != null) {
            finFecha = new Date(fechaFin);
        }
        else {
            finFecha = new Date();
        }

        Calendar calIni = new GregorianCalendar();
        calIni.setTime(CalendarUtils.truncarHora(new Date(fechaInicio)));
        Calendar calFin = new GregorianCalendar();
        calFin.setTime(CalendarUtils.truncarHora(finFecha));

        while (calIni.before(calFin) || calIni.equals(calFin)) {
            ejecutarOrquestadorStagin(calIni.getTime().getTime());
            calIni.add(Calendar.DAY_OF_YEAR, 1);
            System.out.println(calIni.toString());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#eliminarLiquidacionSP(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Boolean eliminarLiquidacionSP(String numeroRadicado, Boolean isAsync) {
        String firmaMetodo = "ConsultasModeloSubsidio.eliminarLiquidacionSP(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo + " - numeroRadicado:" + numeroRadicado);
        
        StoredProcedureQuery storedProcedure = entityManagerSubsidio
                .createStoredProcedureQuery(NamedQueriesConstants.EJECUTAR_SP_ELIMINAR_LIQUIDACION);
        storedProcedure.registerStoredProcedureParameter("sNumeroRadicado", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("isAsync", Boolean.class, ParameterMode.IN);
        storedProcedure.setParameter("sNumeroRadicado", numeroRadicado);
        storedProcedure.setParameter("isAsync", isAsync);
        storedProcedure.registerStoredProcedureParameter("bLiquidORechazoOStagingEnProceso", String.class, ParameterMode.OUT);        
        storedProcedure.execute();      
        
        Boolean resultado = storedProcedure.getOutputParameterValue("bLiquidORechazoOStagingEnProceso").toString().equals("0")?Boolean.FALSE:Boolean.TRUE;

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        
        return resultado;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#eliminarLiquidacionSP(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarLiquidacionSP(String numeroRadicado,int fallecido) {
        String firmaMetodo = "ConsultasModeloSubsidio.ejecutarOrquestadorStagin()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

       
        StoredProcedureQuery storedProcedure = entityManagerSubsidio
                .createStoredProcedureQuery(NamedQueriesConstants.EJECUTAR_SP_ELIMINAR_LIQUIDACION);
        storedProcedure.registerStoredProcedureParameter("sNumeroRadicado", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("isAsync", Boolean.class, ParameterMode.IN);
        storedProcedure.setParameter("sNumeroRadicado", numeroRadicado);
        storedProcedure.setParameter("isAsync", Boolean.FALSE);
        storedProcedure.registerStoredProcedureParameter("bLiquidORechazoOStagingEnProceso", String.class, ParameterMode.OUT);        
        storedProcedure.execute();  
        

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#eliminarLiquidacionSP(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean consultarEjecucionProcesoSubsidio(String numeroRadicado) {
        String firmaMetodo = "ConsultasModeloSubsidio.ejecutarOrquestadorStagin()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);   
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return entityManagerSubsidio
                .createNamedQuery(
                        NamedQueriesConstants.CONSULTAR_PROCESO_SUBSIDIO).getSingleResult().toString().equals("1")?true:false;    
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#obtenerDescuentosBeneficiarios(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<String, String> obtenerDescuentosBeneficiarios(String numeroRadicacion,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.obtenerDescuentosBeneficiarios(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<String, String> descuentosMap = new HashMap<String, String>();
        try {
            List<Object[]> descuentos = new ArrayList<Object[]>();
            if (tipoIdentificacion != null && numeroIdentificacion != null) {
                descuentos = entityManagerSubsidio
                        .createNamedQuery(
                                NamedQueriesConstants.CONSULTAR_DESCUENTOS_BENEFICIARIOS_POR_LIQUIDACION_EMPLEADOR)
                        .setParameter("numeroRadicacion", numeroRadicacion)
                        .setParameter("tipoIdentificacion", tipoIdentificacion)
                        .setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
            } else {
                descuentos = entityManagerSubsidio
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_DESCUENTOS_BENEFICIARIOS_POR_LIQUIDACION)
                        .setParameter("numeroRadicacion", numeroRadicacion).getResultList();
            }

            descuentosMap = agruparDescuentosPorResultadoValidacionLiquidacion(descuentos);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return descuentosMap;
    }

    /**
     * Método que se encarga de agrupar los descuentos registrados en BD, por beneficiario
     * @param descuentos
     *        lista de objetos que representan los descuentos
     * @return mapa con la información agrupada
     * @author rlopez
     */
    private Map<String, String> agruparDescuentosPorResultadoValidacionLiquidacion(List<Object[]> descuentos) {
        String firmaMetodo = "ConsultasModeloSubsidio.agruparDescuentosPorResultadoValidacionLiquidacion(List)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<String, Set<String>> descuentosAgrupados = new HashMap<>();

        for (Object[] descuentoRVL : descuentos) {
            String identificadorRVL = descuentoRVL[0].toString();
            if (!descuentosAgrupados.containsKey(identificadorRVL)) {
                Set<String> codigosEntidades = new HashSet<>();
                codigosEntidades.add(String.format("%04d", Long.parseLong(descuentoRVL[1].toString()))); 
                descuentosAgrupados.put(identificadorRVL, codigosEntidades);
            }
            else {
                Set<String> codigosEntidades = descuentosAgrupados.get(identificadorRVL);
                codigosEntidades.add(String.format("%04d", Long.parseLong(descuentoRVL[1].toString()))); 
                descuentosAgrupados.put(identificadorRVL, codigosEntidades);
            }
        }
        Map<String, String> descuentosAgrupadosFormato = darFormatoMapaDescuentos(descuentosAgrupados);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return descuentosAgrupadosFormato;
    }

    /**
     * Método que permite dar formato al mapa de descuentos, que almacena los códigos de las entidades relacionadas a un subsidio
     * @param descuentosAgrupados
     *        mapa con los descuentos previamente agrupados
     * @return mapa con el formato dado
     * @author rlopez
     */
    private Map<String, String> darFormatoMapaDescuentos(Map<String, Set<String>> descuentosAgrupados) {
        String firmaMetodo = "ConsultasModeloSubsidio.darFormatoMapaDescuentos(Map)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<String, String> descuentosAgrupadosFormato = new HashMap<>();
        final String separadorComa = ",";

        for (Map.Entry<String, Set<String>> entryMap : descuentosAgrupados.entrySet()) {
            Set<String> codigosDescuento = entryMap.getValue();
            StringBuilder aux = new StringBuilder();
            Iterator<String> iterador = codigosDescuento.iterator();

            while (iterador.hasNext()) {
                aux.append(iterador.next());
                if (iterador.hasNext()) {
                    aux.append(separadorComa);
                }
            }
            descuentosAgrupadosFormato.put(entryMap.getKey(), aux.toString());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return descuentosAgrupadosFormato;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#temporalDerechoBeneficiarios(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<TemporalAsignacionDerechoDTO> temporalDerechoBeneficiarios(String numeroRadicacion) {
        //TODO eliminar este servicio ya que es temporal

        List<TemporalAsignacionDerechoDTO> derechosBeneficiarios = new ArrayList<TemporalAsignacionDerechoDTO>();

        try {
            List<Object[]> derechos = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DERECHOS_BENEFICIARIOS_TEMPORAL)
                    .setParameter("numeroRadicacion", numeroRadicacion).getResultList();

            derechos = replaceNulls(derechos);
            derechosBeneficiarios = convertToTemporalDTO(derechos);

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        return derechosBeneficiarios;
    }

    private List<Object[]> replaceNulls(List<Object[]> derechos) {
        for (Object[] derecho : derechos) {
            for (int i = 0; i < derecho.length; i++) {
                if (derecho[i] == null) {
                    derecho[i] = "";
                }
            }
        }
        return derechos;
    }

    private List<TemporalAsignacionDerechoDTO> convertToTemporalDTO(List<Object[]> derechos) {
        List<TemporalAsignacionDerechoDTO> derechosBeneficiariios = new ArrayList<>();
        for (Object[] derecho : derechos) {
            TemporalAsignacionDerechoDTO temporalDTO = new TemporalAsignacionDerechoDTO();
            temporalDTO.setNumeroIdentificacionBeneficiario(derecho[0].toString());
            temporalDTO.setTipoIdentificacionBeneficiario(derecho[1].toString());
            temporalDTO.setNumeroIdentificacionEmpleador(derecho[2].toString());
            temporalDTO.setTipoIdentificacionEmpleador(derecho[3].toString());
            temporalDTO.setNumeroIdentificacionTrabajador(derecho[4].toString());
            temporalDTO.setTipoIdentificacionTrabajador(derecho[5].toString());
            temporalDTO.setResultadoCausal(derecho[6].toString());
            temporalDTO.setEstadoDerecho(derecho[7].toString());
            temporalDTO.setValorCuota(new BigDecimal(derecho[8].toString()));
            derechosBeneficiariios.add(temporalDTO);
        }
        return derechosBeneficiariios;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidios.business.interfaces.IConsultasModeloCore#consutarResultadosLiquidacionMasiva(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ResultadoLiquidacionMasivaDTO consultarResultadoLiquidacionMasiva(String numeroSolicitud, Date periodo) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarResultadosLiquidacionMasiva(String,Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoLiquidacionMasivaDTO result = new ResultadoLiquidacionMasivaDTO();

        ParametrizacionCondicionesSubsidioModeloDTO parametrizacionCondicionesDTO = consultarParametrizacionCondicionesLiquidacion(
                numeroSolicitud, periodo);

        List<Object[]> registros = calcularMatrizResultado(numeroSolicitud, periodo, TipoProcesoLiquidacionEnum.MASIVA.name())
                .getResultList();

        List<ResultadoMontoLiquidadoDTO> resultadoMonto = generarMontos(registros);
        List<CantidadEmpresaTrabajadorDTO> resultadoCantidad = calcularCantidades(numeroSolicitud, parametrizacionCondicionesDTO, periodo);

        result.setResultadoMonto(resultadoMonto);
        result.setCantidadEmpresaTrabajador(resultadoCantidad);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Método que permite consultar la parametrización anual asociada a un proceso de liquidación
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return DTO con la información de la parametrización
     * @author rlopez
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private ParametrizacionCondicionesSubsidioModeloDTO consultarParametrizacionCondicionesLiquidacion(String numeroRadicacion,
            Date periodo) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarParametrizacionCondicionesLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        logger.debug("numeroRadicacion: "+numeroRadicacion + " - periodo: "+periodo);

        ParametrizacionCondicionesSubsidioModeloDTO parametrizacionDTO = new ParametrizacionCondicionesSubsidioModeloDTO();
       
        Object[] parametrizacion = (Object[]) entityManagerSubsidio
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACION_POR_RADICACION)
                .setParameter("numeroRadicacion", numeroRadicacion).setParameter("periodo", periodo).getSingleResult();

        parametrizacionDTO.setIdParametrizacionCondicionesSubsidio(Long.parseLong(parametrizacion[0].toString()));
        parametrizacionDTO.setAnioVigenciaParametrizacion(Integer.parseInt(parametrizacion[1].toString()));
        parametrizacionDTO.setFechaPeriodoInicial(CalendarUtils.darFormatoYYYYMMDDGuionDate(parametrizacion[2].toString()));
        parametrizacionDTO.setFechaPeriodoFinal(CalendarUtils.darFormatoYYYYMMDDGuionDate(parametrizacion[3].toString()));
        parametrizacionDTO.setValorCuotaAnualBase(BigDecimal.valueOf(Double.parseDouble(parametrizacion[4].toString())));
        parametrizacionDTO.setValorCuotaAnualAgraria(BigDecimal.valueOf(Double.parseDouble(parametrizacion[5].toString())));
        parametrizacionDTO.setEsProgramaDecreto4904(Boolean.valueOf(parametrizacion[6].toString()));
        parametrizacionDTO.setEsRetroactivoNovInvalidez(Boolean.valueOf(parametrizacion[7].toString()));
        parametrizacionDTO.setEsRetroactivoReingresoEmpleadores(Boolean.valueOf(parametrizacion[8].toString()));
        parametrizacionDTO.setCantidadSubsidiosLiquidados(Integer.parseInt(parametrizacion[9].toString()));
        parametrizacionDTO.setMontoSubsidiosLiquidados(BigDecimal.valueOf(Double.parseDouble(parametrizacion[10].toString())));
        parametrizacionDTO.setCantidadSubsidiosLiquidadosInvalidez(Integer.parseInt(parametrizacion[11].toString()));
        parametrizacionDTO.setCantidadPeriodosRetroactivosMes(Integer.parseInt(parametrizacion[12].toString()));
        parametrizacionDTO.setDiasNovedadFallecimiento(Integer.parseInt(parametrizacion[13].toString()));

       

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return parametrizacionDTO;
    }

    /**
     * Sección de consultas para la tabla resultado del monto liquidado
     */

    /**
     * Método que se encarga de ejecutar el procedimiento almacenado que permite obtener la matriz de resultado para una liquidación
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param periodo
     *        periodo regular
     * @return procedimiento almacenado
     * @author rlopez
     */
    private StoredProcedureQuery calcularMatrizResultado(String numeroRadicacion, Date periodo, String tipoLiquidacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.calcularMatrizResultado(String,Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            StoredProcedureQuery procedimiento = entityManagerSubsidio
                    .createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_GET_RESULTADO_LIQUIDACION)
                    .setParameter("NumeroRadicado", numeroRadicacion).setParameter("PeriodoRegular", periodo)
                    .setParameter("TipoLiquidacion", tipoLiquidacion);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            logger.info("Resultado del metodo calcularMatrizResultado es: " + procedimiento);
            return procedimiento;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Método utilitario que permite realizar la conversión de los datos obtenidos por el procedimiento almacenado
     * @param registros
     *        información obtenida por el SP
     * @return Lista de DTO con la información de montos
     * @author rlopez
     */
    private List<ResultadoMontoLiquidadoDTO> generarMontos(List<Object[]> registros) {
        String firmaMetodo = "ConsultasModeloSubsidio.calcularMatrizResultado(String,Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ResultadoMontoLiquidadoDTO> resultadoMonto = new ArrayList<>();
       
        for (Object[] registroMonto : registros) {
            ResultadoMontoLiquidadoDTO monto = new ResultadoMontoLiquidadoDTO();

            monto.setNombre(ItemsMontoLiquidadoEnum.valueOf(registroMonto[0].toString()));
            monto.setValor(BigDecimal.valueOf(Double.parseDouble(registroMonto[1].toString())));
            monto.setValorPromedio(BigDecimal.valueOf(Double.parseDouble(registroMonto[2].toString())));
            monto.setValorAnteriores(BigDecimal.valueOf(Double.parseDouble(registroMonto[3].toString())));
            monto.setValorAcumulado(BigDecimal.valueOf(Double.parseDouble(registroMonto[4].toString())));

            resultadoMonto.add(monto);
        }        

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultadoMonto;
    }

    /**
     * Sección de consultas de las cantidades
     */

    /**
     * Método que permite agrupar el calculo de las cantidades asociadas a los umbrales parametrizados
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return DTO con la información de las cantidades
     * @author rlopez
     */
    private List<CantidadEmpresaTrabajadorDTO> calcularCantidades(String numeroRadicacion,
            ParametrizacionCondicionesSubsidioModeloDTO parametrizacionCondicionesDTO, Date periodo) {
        List<CantidadEmpresaTrabajadorDTO> cantidades = new ArrayList<>();

        cantidades.add(calcularCantidadTrabajadoresMasXSubsidios(numeroRadicacion,
                parametrizacionCondicionesDTO.getCantidadSubsidiosLiquidados()));
        cantidades
                .add(calcularCantidadTrabajadoresMasXMonto(numeroRadicacion, parametrizacionCondicionesDTO.getMontoSubsidiosLiquidados()));
        cantidades.add(calcularCantidadTrabajadoresMasXSubsidiosInvalidez(numeroRadicacion,
                parametrizacionCondicionesDTO.getCantidadSubsidiosLiquidadosInvalidez()));
        cantidades.add(calcularCantidadEmpresasMasXPeriodosRetroactivos(numeroRadicacion,
                parametrizacionCondicionesDTO.getCantidadPeriodosRetroactivosMes(), periodo));
        cantidades.add(calcularCantidadEmpresas1429(numeroRadicacion));
        cantidades.add(calcularMontoDescontadoPersonas(numeroRadicacion));

        return cantidades;
    }

    /**
     * Método que permite calcular la cantidad de trabajadores con más subsidios liquidados que la cantidad parametrizada
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param umbral
     *        valor parametrizado
     * @return DTO con la información de la cantidad
     * @author rlopez
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private CantidadEmpresaTrabajadorDTO calcularCantidadTrabajadoresMasXSubsidios(String numeroRadicacion, Integer umbral) {
        String firmaMetodo = "ConsultasModeloSubsidio.calcularCantidadTrabajadoresMasXSubsidios(String,Integer)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        CantidadEmpresaTrabajadorDTO cantidadDTO = new CantidadEmpresaTrabajadorDTO();
        try {
            Integer cantidad = ((Number) entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CANTIDAD_TRABAJADORES_MAS_X_SUBSIDIOS)
                    .setParameter("numeroRadicacion", numeroRadicacion).setParameter("cantidadSubsidios", umbral).getSingleResult())
                            .intValue();

            cantidadDTO.setNombre(ItemsCantidadEmpresaTrabajadorEnum.CANTIDAD_TRABAJADORES_MAS_X_SUBSIDIOS);
            cantidadDTO.setUmbral(BigDecimal.valueOf(umbral));
            cantidadDTO.setValor(BigDecimal.valueOf(cantidad));

        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return cantidadDTO;
    }

    /**
     * Método que permite calcular la cantidad de trabajadores con más monto liquidado que la cantidad parametrizada
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param umbral
     *        valor parametrizado
     * @return DTO con la información de la cantidad
     * @author rlopez
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private CantidadEmpresaTrabajadorDTO calcularCantidadTrabajadoresMasXMonto(String numeroRadicacion, BigDecimal umbral) {
        String firmaMetodo = "ConsultasModeloSubsidio.calcularCantidadTrabajadoresMasXMonto(String,BigDecimal)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        CantidadEmpresaTrabajadorDTO cantidadDTO = new CantidadEmpresaTrabajadorDTO();
        try {
            BigDecimal cantidad = BigDecimal.valueOf(
                    ((Number) entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_CANTIDAD_TRABAJADORES_MAS_X_MONTO)
                            .setParameter("numeroRadicacion", numeroRadicacion).setParameter("montoSubsidios", umbral).getSingleResult())
                                    .doubleValue());

            cantidadDTO.setNombre(ItemsCantidadEmpresaTrabajadorEnum.CANTIDAD_TRABAJADORES_MAS_X_MONTO);
            cantidadDTO.setUmbral(umbral);
            cantidadDTO.setValor(cantidad);

        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return cantidadDTO;
    }

    /**
     * Método que permite calcular la cantidad de trabajadores con más subsidios de invalidez liquidados que la cantidada parametrizada
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param umbral
     *        valor parametrizado
     * @return DTO con la información de la cantidad
     * @author rlopez
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private CantidadEmpresaTrabajadorDTO calcularCantidadTrabajadoresMasXSubsidiosInvalidez(String numeroRadicacion, Integer umbral) {
        String firmaMetodo = "ConsultasModeloSubsidio.calcularCantidadTrabajadoresMasXSubsidiosInvalidez(String,Integer)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        CantidadEmpresaTrabajadorDTO cantidadDTO = new CantidadEmpresaTrabajadorDTO();
        try {
            Integer cantidad = ((Number) entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CANTIDAD_TRABAJADORES_MAS_X_SUBSIDIOS_INVALIDEZ)
                    .setParameter("numeroRadicacion", numeroRadicacion).setParameter("cantidadSubsidios", umbral).getSingleResult())
                            .intValue();

            cantidadDTO.setNombre(ItemsCantidadEmpresaTrabajadorEnum.CANTIDAD_TRABAJADORES_MAS_X_NUM_SUBSIDIOS_INVALIDEZ);
            cantidadDTO.setUmbral(BigDecimal.valueOf(umbral));
            cantidadDTO.setValor(BigDecimal.valueOf(cantidad));

        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return cantidadDTO;
    }

    /**
     * Método que permite calcular la cantidad de empresas con más periodos retroactivos que la cantidad parametrizada
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param umbral
     *        valor parametrizado
     * @return DTO con la información de la cantidad
     * @author rlopez
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private CantidadEmpresaTrabajadorDTO calcularCantidadEmpresasMasXPeriodosRetroactivos(String numeroRadicacion, Integer umbral,
            Date periodo) {
        String firmaMetodo = "ConsultasModeloSubsidio.calcularCantidadEmpresasMasXPeriodosRetroactivos(String,Integer)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        CantidadEmpresaTrabajadorDTO cantidadDTO = new CantidadEmpresaTrabajadorDTO();
        try {
            Integer cantidad = ((Number) entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CANTIDAD_EMPRESAS_MAS_X_PERIODOS_RRETROACTIVOS)
                    .setParameter("numeroRadicacion", numeroRadicacion).setParameter("cantidadPeriodosRetroactivos", umbral)
                    .getSingleResult()).intValue();

            cantidadDTO.setNombre(ItemsCantidadEmpresaTrabajadorEnum.CANTIDAD_EMPRESAS_MAS_X_PERIODOS_RETROACTIVOS_MES);
            cantidadDTO.setUmbral(BigDecimal.valueOf(umbral));
            cantidadDTO.setValor(BigDecimal.valueOf(cantidad));

        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return cantidadDTO;
    }

    /**
     * Método que permite calcular la cantidad de empresas que se encuentran en el año 1 y 2 del beneficio 1429 con subsidio mayor a cero
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return DTO con la información de la cantidad
     * @author rlopez
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private CantidadEmpresaTrabajadorDTO calcularCantidadEmpresas1429(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.calcularCantidadEmpresas1429(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        CantidadEmpresaTrabajadorDTO cantidadDTO = new CantidadEmpresaTrabajadorDTO();
        try {
            Integer cantidad = ((Number) entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_CANTIDAD_EMPRESAS_1429)
                    .setParameter("numeroRadicacion", numeroRadicacion).getSingleResult()).intValue();

            cantidadDTO.setNombre(ItemsCantidadEmpresaTrabajadorEnum.CANTIDAD_EMPRESAS_1429_ANIO_1_2_BENEFICIO_MAYOR_CERO);
            cantidadDTO.setValor(BigDecimal.valueOf(cantidad));

        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return cantidadDTO;
    }

    /**
     * Método que permite calcular el monto descontado sobre las personas que se les causo derecho en el proceso de liquidación
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return DTO con la información de la cantidad
     * @author rlopez
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private CantidadEmpresaTrabajadorDTO calcularMontoDescontadoPersonas(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.calcularMontoDescontadoPersonas(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        CantidadEmpresaTrabajadorDTO cantidadDTO = new CantidadEmpresaTrabajadorDTO();
        try {
            Integer cantidad = ((Number) entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_MONTO_DESCONTADO_PERSONAS)
                    .setParameter("numeroRadicacion", numeroRadicacion).getSingleResult()).intValue();

            cantidadDTO.setNombre(ItemsCantidadEmpresaTrabajadorEnum.MONTO_DESCONTADO_PERSONAS);
            cantidadDTO.setValor(BigDecimal.valueOf(cantidad));

        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return cantidadDTO;
    }

    /**
     * Método encargado de consultar la cantidad de personas con descuento en una solicitud
     * @param numeroSolicitud
     *        valor del número de solicitud
     * @return cantidad de personas con descuento
     * @author rlopez
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private Integer calcularCantidadPersonasConDescuentos(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.calcularCantidadPersonasConDescuentos(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Integer cantidad = 0;
        try {
            cantidad = ((Number) entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_CANTIDAD_PERSONAS_DESCUENTO)
                    .setParameter("numeroRadicacion", numeroRadicacion).getSingleResult()).intValue();

        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return cantidad;
    }

    /**
     * Sección de consultas para las tablas de detalle relacionadas con los umbrales
     */

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidios.business.interfaces.IConsultasModeloCore#consultarCantidadTrabajadoresMasSubsidiosLiquidados(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DetalleCantidadEmpresaTrabajadorDTO subsidiosLiquidadosPorTrabajadores(String numeroSolicitud, Date periodoLiquidado) {
        String firmaMetodo = "ConsultasModeloSubsidio.subsidiosLiquidadosPorTrabajadores(String,Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DetalleCantidadEmpresaTrabajadorDTO detalleDTO = new DetalleCantidadEmpresaTrabajadorDTO();
        try {
            Integer cantidadSubsidios = consultarParametrizacionCondicionesLiquidacion(numeroSolicitud, periodoLiquidado)
                    .getCantidadSubsidiosLiquidados();

            List<Object[]> detalles = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_TRABAJADORES_MAS_X_SUBSIDIOS)
                    .setParameter("numeroRadicacion", numeroSolicitud).setParameter("cantidadSubsidios", cantidadSubsidios).getResultList();

            Map<KeyPersonaDTO, Integer> trabajadoresProcesados = new HashMap<>();
            Integer numeral = 0;

            List<DetalleItemEmpresaTrabajadorDTO> detallesItemEmpresaTrabajador = new ArrayList<>();
            for (Object[] detalle : detalles) {
                DetalleItemEmpresaTrabajadorDTO detalleItem = new DetalleItemEmpresaTrabajadorDTO();

                detalleItem.setTipoIdEmpleador(TipoIdentificacionEnum.valueOf(detalle[0].toString()));
                detalleItem.setIdEmpleador(detalle[1].toString());
                detalleItem.setNombreEmpleador(detalle[2].toString());
                detalleItem.setTipoIdTrabajador(TipoIdentificacionEnum.valueOf(detalle[3].toString()));
                detalleItem.setIdTrabajador(detalle[4].toString());
                detalleItem.setNombreTrabajador(detalle[5].toString());
                detalleItem.setTipoIdBeneficiario(TipoIdentificacionEnum.valueOf(detalle[6].toString()));
                detalleItem.setIdBeneficiario(detalle[7].toString());
                detalleItem.setNombreBeneficiario(detalle[8].toString());
                detalleItem.setValorCuota(BigDecimal.valueOf(Double.parseDouble(detalle[9].toString())));
                detalleItem.setPeriodoLiquidado(CalendarUtils.darFormatoYYYYMMDDGuionDate(detalle[10].toString()));

                if (!trabajadoresProcesados.containsKey(new KeyPersonaDTO(detalleItem.getTipoIdTrabajador(), detalleItem.getIdTrabajador()))) {
                    numeral++;
                    trabajadoresProcesados.put(new KeyPersonaDTO(detalleItem.getTipoIdTrabajador(), detalleItem.getIdTrabajador()), numeral);
                }
                detalleItem.setNumeral(
                        trabajadoresProcesados.get(new KeyPersonaDTO(detalleItem.getTipoIdTrabajador(), detalleItem.getIdTrabajador())));

                detallesItemEmpresaTrabajador.add(detalleItem);
            }
            detalleDTO.setDetalleItems(detallesItemEmpresaTrabajador);
            CantidadEmpresaTrabajadorDTO cantidadDTO = calcularCantidadTrabajadoresMasXSubsidios(numeroSolicitud, cantidadSubsidios);
            detalleDTO.setCantidadTrabajadores(cantidadDTO.getValor().intValue());
            detalleDTO.setUmbral(cantidadDTO.getUmbral());
            detalleDTO.setPeriodoLiquidado(periodoLiquidado);

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return detalleDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidios.business.interfaces.IConsultasModeloCore#consultarCantidadTrabajadoresMasMontoSubsidioLiquidado(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DetalleCantidadEmpresaTrabajadorDTO montoSubsidiosLiquidadosPorTrabajadores(String numeroSolicitud, Date periodoLiquidado) {
        String firmaMetodo = "ConsultasModeloSubsidio.montoSubsidiosLiquidadosPorTrabajadores(String,Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DetalleCantidadEmpresaTrabajadorDTO detalleDTO = new DetalleCantidadEmpresaTrabajadorDTO();
        try {
            BigDecimal montoLiquidado = consultarParametrizacionCondicionesLiquidacion(numeroSolicitud, periodoLiquidado)
                    .getMontoSubsidiosLiquidados();

            List<Object[]> detalles = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_TRABAJADORES_MAS_X_MONTO)
                    .setParameter("numeroRadicacion", numeroSolicitud).setParameter("montoSubsidios", montoLiquidado).getResultList();

            Map<KeyPersonaDTO, Integer> trabajadoresProcesados = new HashMap<>();
            Integer numeral = 0;

            List<DetalleItemEmpresaTrabajadorDTO> detallesItemEmpresaTrabajador = new ArrayList<>();
            for (Object[] detalle : detalles) {
                DetalleItemEmpresaTrabajadorDTO detalleItem = new DetalleItemEmpresaTrabajadorDTO();

                detalleItem.setTipoIdEmpleador(TipoIdentificacionEnum.valueOf(detalle[0].toString()));
                detalleItem.setIdEmpleador(detalle[1].toString());
                detalleItem.setNombreEmpleador(detalle[2].toString());
                detalleItem.setTipoIdTrabajador(TipoIdentificacionEnum.valueOf(detalle[3].toString()));
                detalleItem.setIdTrabajador(detalle[4].toString());
                detalleItem.setNombreTrabajador(detalle[5].toString());
                detalleItem.setTipoIdBeneficiario(TipoIdentificacionEnum.valueOf(detalle[6].toString()));
                detalleItem.setIdBeneficiario(detalle[7].toString());
                detalleItem.setNombreBeneficiario(detalle[8].toString());
                detalleItem.setValorCuota(BigDecimal.valueOf(Double.parseDouble(detalle[9].toString())));
                detalleItem.setPeriodoLiquidado(CalendarUtils.darFormatoYYYYMMDDGuionDate(detalle[10].toString()));

                if (!trabajadoresProcesados.containsKey(new KeyPersonaDTO(detalleItem.getTipoIdTrabajador(), detalleItem.getIdTrabajador()))) {
                    numeral++;
                    trabajadoresProcesados.put(new KeyPersonaDTO(detalleItem.getTipoIdTrabajador(), detalleItem.getIdTrabajador()), numeral);
                }
                detalleItem.setNumeral(
                        trabajadoresProcesados.get(new KeyPersonaDTO(detalleItem.getTipoIdTrabajador(), detalleItem.getIdTrabajador())));

                detallesItemEmpresaTrabajador.add(detalleItem);
            }
            detalleDTO.setDetalleItems(detallesItemEmpresaTrabajador);
            CantidadEmpresaTrabajadorDTO cantidadDTO = calcularCantidadTrabajadoresMasXMonto(numeroSolicitud, montoLiquidado);
            detalleDTO.setCantidadTrabajadores(cantidadDTO.getValor().intValue());
            detalleDTO.setUmbral(cantidadDTO.getUmbral());
            detalleDTO.setPeriodoLiquidado(periodoLiquidado);

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return detalleDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidios.business.interfaces.IConsultasModeloCore#consultarCantidadTrabajadoresMasSubsidiosInvalidez(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DetalleCantidadEmpresaTrabajadorDTO subsidiosInvalidezPorTrabajadores(String numeroSolicitud, Date periodoLiquidado) {
        String firmaMetodo = "ConsultasModeloSubsidio.subsidiosInvalidezPorTrabajadores(String,Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DetalleCantidadEmpresaTrabajadorDTO detalleDTO = new DetalleCantidadEmpresaTrabajadorDTO();
        try {
            Integer cantidadSubsidiosInvalidez = consultarParametrizacionCondicionesLiquidacion(numeroSolicitud, periodoLiquidado)
                    .getCantidadSubsidiosLiquidadosInvalidez();

            List<Object[]> detalles = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_TRABAJADORES_MAS_X_SUBSIDIOS_INVALIDEZ)
                    .setParameter("numeroRadicacion", numeroSolicitud)
                    .setParameter("cantidadSubsidiosInvalidez", cantidadSubsidiosInvalidez).getResultList();

            Map<KeyPersonaDTO, Integer> trabajadoresProcesados = new HashMap<>();
            Integer numeral = 0;

            List<DetalleItemEmpresaTrabajadorDTO> detallesItemEmpresaTrabajador = new ArrayList<>();
            for (Object[] detalle : detalles) {
                DetalleItemEmpresaTrabajadorDTO detalleItem = new DetalleItemEmpresaTrabajadorDTO();

                detalleItem.setTipoIdEmpleador(TipoIdentificacionEnum.valueOf(detalle[0].toString()));
                detalleItem.setIdEmpleador(detalle[1].toString());
                detalleItem.setNombreEmpleador(detalle[2].toString());
                detalleItem.setTipoIdTrabajador(TipoIdentificacionEnum.valueOf(detalle[3].toString()));
                detalleItem.setIdTrabajador(detalle[4].toString());
                detalleItem.setNombreTrabajador(detalle[5].toString());
                detalleItem.setTipoIdBeneficiario(TipoIdentificacionEnum.valueOf(detalle[6].toString()));
                detalleItem.setIdBeneficiario(detalle[7].toString());
                detalleItem.setNombreBeneficiario(detalle[8].toString());
                detalleItem.setValorCuota(BigDecimal.valueOf(Double.parseDouble(detalle[9].toString())));
                detalleItem.setPeriodoLiquidado(CalendarUtils.darFormatoYYYYMMDDGuionDate(detalle[10].toString()));

                if (!trabajadoresProcesados.containsKey(new KeyPersonaDTO(detalleItem.getTipoIdTrabajador(), detalleItem.getIdTrabajador()))) {
                    numeral++;
                    trabajadoresProcesados.put(new KeyPersonaDTO(detalleItem.getTipoIdTrabajador(), detalleItem.getIdTrabajador()), numeral);
                }
                detalleItem.setNumeral(
                        trabajadoresProcesados.get(new KeyPersonaDTO(detalleItem.getTipoIdTrabajador(), detalleItem.getIdTrabajador())));

                detallesItemEmpresaTrabajador.add(detalleItem);
            }
            detalleDTO.setDetalleItems(detallesItemEmpresaTrabajador);
            CantidadEmpresaTrabajadorDTO cantidadDTO = calcularCantidadTrabajadoresMasXSubsidiosInvalidez(numeroSolicitud,
                    cantidadSubsidiosInvalidez);
            detalleDTO.setCantidadTrabajadores(cantidadDTO.getValor().intValue());
            detalleDTO.setUmbral(cantidadDTO.getUmbral());
            detalleDTO.setPeriodoLiquidado(periodoLiquidado);

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return detalleDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidios.business.interfaces.IConsultasModeloCore#consultarCantidadEmpresasMasPeriodosRetroactivosMes(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DetalleCantidadEmpresaTrabajadorDTO periodosRetroactivosMes(String numeroSolicitud, Date periodoLiquidado) {
        String firmaMetodo = "ConsultasModeloSubsidio.periodosRetroactivosMes(String,Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DetalleCantidadEmpresaTrabajadorDTO detalleDTO = new DetalleCantidadEmpresaTrabajadorDTO();
        try {
            Integer cantidadPeriodosRetroactivos = consultarParametrizacionCondicionesLiquidacion(numeroSolicitud, periodoLiquidado)
                    .getCantidadPeriodosRetroactivosMes();

            List<Object[]> detalles = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESAS_MAS_X_PERIODOS_RERTROACTIVOS)
                    .setParameter("numeroRadicacion", numeroSolicitud)
                    .setParameter("cantidadPeriodosRetroactivos", cantidadPeriodosRetroactivos).getResultList();

            Map<KeyPersonaDTO, Integer> empleadoresProcesados = new HashMap<>();
            Integer numeral = 0;

            List<DetalleItemEmpresaTrabajadorDTO> detallesItemEmpresaTrabajador = new ArrayList<>();
            for (Object[] detalle : detalles) {
                DetalleItemEmpresaTrabajadorDTO detalleItem = new DetalleItemEmpresaTrabajadorDTO();

                detalleItem.setTipoIdEmpleador(TipoIdentificacionEnum.valueOf(detalle[0].toString()));
                detalleItem.setIdEmpleador(detalle[1].toString());
                detalleItem.setNombreEmpleador(detalle[2].toString());
                detalleItem.setTipoIdTrabajador(TipoIdentificacionEnum.valueOf(detalle[3].toString()));
                detalleItem.setIdTrabajador(detalle[4].toString());
                detalleItem.setNombreTrabajador(detalle[5].toString());
                detalleItem.setTipoIdBeneficiario(TipoIdentificacionEnum.valueOf(detalle[6].toString()));
                detalleItem.setIdBeneficiario(detalle[7].toString());
                detalleItem.setNombreBeneficiario(detalle[8].toString());
                detalleItem.setValorCuota(BigDecimal.valueOf(Double.parseDouble(detalle[9].toString())));
                detalleItem.setPeriodoLiquidado(CalendarUtils.darFormatoYYYYMMDDGuionDate(detalle[10].toString()));

                if (!empleadoresProcesados.containsKey(new KeyPersonaDTO(detalleItem.getTipoIdEmpleador(), detalleItem.getIdEmpleador()))) {
                    numeral++;
                    empleadoresProcesados.put(new KeyPersonaDTO(detalleItem.getTipoIdEmpleador(), detalleItem.getIdEmpleador()), numeral);
                }
                detalleItem.setNumeral(
                        empleadoresProcesados.get(new KeyPersonaDTO(detalleItem.getTipoIdEmpleador(), detalleItem.getIdEmpleador())));

                detallesItemEmpresaTrabajador.add(detalleItem);
            }
            detalleDTO.setDetalleItems(detallesItemEmpresaTrabajador);
            CantidadEmpresaTrabajadorDTO cantidadDTO = calcularCantidadEmpresasMasXPeriodosRetroactivos(numeroSolicitud,
                    cantidadPeriodosRetroactivos, periodoLiquidado);
            detalleDTO.setCantidadTrabajadores(cantidadDTO.getValor().intValue());
            detalleDTO.setUmbral(cantidadDTO.getUmbral());
            detalleDTO.setPeriodoLiquidado(periodoLiquidado);

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return detalleDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidios.business.interfaces.IConsultasModeloCore#consultarCantidadEmpresas1429(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DetalleCantidadEmpresaTrabajadorDTO empresasBeneficio1429(String numeroSolicitud, Date periodoLiquidado) {
        String firmaMetodo = "ConsultasModeloSubsidio.empresasBeneficio1429(String,Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DetalleCantidadEmpresaTrabajadorDTO detalleDTO = new DetalleCantidadEmpresaTrabajadorDTO();
        try {
            List<Object[]> detalles = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESAS_1429)
                    .setParameter("numeroRadicacion", numeroSolicitud).getResultList();

            Map<KeyPersonaDTO, Integer> empleadoresProcesados = new HashMap<>();
            Integer numeral = 0;

            List<DetalleItemEmpresaTrabajadorDTO> detallesItemEmpresaTrabajador = new ArrayList<>();
            for (Object[] detalle : detalles) {
                DetalleItemEmpresaTrabajadorDTO detalleItem = new DetalleItemEmpresaTrabajadorDTO();

                detalleItem.setTipoIdEmpleador(TipoIdentificacionEnum.valueOf(detalle[0].toString()));
                detalleItem.setIdEmpleador(detalle[1].toString());
                detalleItem.setNombreEmpleador(detalle[2].toString());
                detalleItem.setAnioBeneficio(Integer.parseInt(detalle[3].toString()));
                detalleItem.setTipoIdTrabajador(TipoIdentificacionEnum.valueOf(detalle[4].toString()));
                detalleItem.setIdTrabajador(detalle[5].toString());
                detalleItem.setNombreTrabajador(detalle[6].toString());
                detalleItem.setTipoIdBeneficiario(TipoIdentificacionEnum.valueOf(detalle[7].toString()));
                detalleItem.setIdBeneficiario(detalle[8].toString());
                detalleItem.setNombreBeneficiario(detalle[9].toString());
                detalleItem.setValorCuota(BigDecimal.valueOf(Double.parseDouble(detalle[10].toString())));
                detalleItem.setPeriodoLiquidado(CalendarUtils.darFormatoYYYYMMDDGuionDate(detalle[11].toString()));

                if (!empleadoresProcesados.containsKey(new KeyPersonaDTO(detalleItem.getTipoIdEmpleador(), detalleItem.getIdEmpleador()))) {
                    numeral++;
                    empleadoresProcesados.put(new KeyPersonaDTO(detalleItem.getTipoIdEmpleador(), detalleItem.getIdEmpleador()), numeral);
                }
                detalleItem.setNumeral(
                        empleadoresProcesados.get(new KeyPersonaDTO(detalleItem.getTipoIdEmpleador(), detalleItem.getIdEmpleador())));

                detallesItemEmpresaTrabajador.add(detalleItem);
            }
            detalleDTO.setDetalleItems(detallesItemEmpresaTrabajador);
            detalleDTO.setCantidadTrabajadores(calcularCantidadEmpresas1429(numeroSolicitud).getValor().intValue());
            detalleDTO.setPeriodoLiquidado(periodoLiquidado);

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return detalleDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidios.business.interfaces.IConsultasModeloCore#consultarMontoDescontadoPersonas(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DetalleCantidadEmpresaTrabajadorDTO personasConDescuentos(String numeroSolicitud, Date periodoLiquidado) {
        String firmaMetodo = "ConsultasModeloSubsidio.personasConDescuentos(String,Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DetalleCantidadEmpresaTrabajadorDTO detalleDTO = new DetalleCantidadEmpresaTrabajadorDTO();
        try {
            //Se consultan todos los descuentos relacionados a una solicitud de liquidación
            List<Object[]> descuentos = consultarDescuentosDetalleLiquidacion(numeroSolicitud);
            List<BigDecimal> ids = new ArrayList<>();

            //Se obtienen los identificadores de RVL asociados a los descuentos
            for (Object[] registroDescuento : descuentos) {
                BigDecimal identificadorRVL = BigDecimal.valueOf(Double.parseDouble(registroDescuento[0].toString()));
                if (!ids.contains(identificadorRVL)) {
                    ids.add(identificadorRVL);
                }
            }

            List<DetalleItemEmpresaTrabajadorDTO> detallesItemEmpresaTrabajador = new ArrayList<>();
            if (!ids.isEmpty()) {
                //Se consulta la información detallada realacionada a los RVL con descuentos
                List<Object[]> detalles = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_DESCUENTOS_PERSONAS)
                        .setParameter("numeroRadicacion", numeroSolicitud)
                        .getResultList();

                Map<KeyPersonaDTO, Integer> beneficiariosProcesados = new HashMap<>();
                Integer numeral = 0;

                for (Object[] detalle : detalles) {
                    //Se obtiene el idRVL del detalle y sus correspondientes descuentos asociados
                    BigDecimal idRVL = BigDecimal.valueOf(Double.parseDouble(detalle[0].toString()));
                    List<Object[]> descuentosRVL = obtenerDescuentosRVL(idRVL, descuentos);
                    
                    //Se crean tantos objetos como descuentos por RVL se encuentren
                    for (Object[] descuentoRVL : descuentosRVL) {
                        DetalleItemEmpresaTrabajadorDTO detalleItem = new DetalleItemEmpresaTrabajadorDTO();

                        detalleItem.setTipoIdEmpleador(TipoIdentificacionEnum.valueOf(detalle[1].toString()));
                        detalleItem.setIdEmpleador(detalle[2].toString());
                        detalleItem.setNombreEmpleador(detalle[3].toString());
                        if (detalle[4] != null) {
                            detalleItem.setAnioBeneficio(Integer.parseInt(detalle[4].toString()));
                        }
                        detalleItem.setTipoIdTrabajador(TipoIdentificacionEnum.valueOf(detalle[5].toString()));
                        detalleItem.setIdTrabajador(detalle[6].toString());
                        detalleItem.setNombreTrabajador(detalle[7].toString());
                        detalleItem.setTipoIdBeneficiario(TipoIdentificacionEnum.valueOf(detalle[8].toString()));
                        detalleItem.setIdBeneficiario(detalle[9].toString());
                        detalleItem.setNombreBeneficiario(detalle[10].toString());
                        detalleItem.setValorCuota(BigDecimal.valueOf(Double.parseDouble(descuentoRVL[3].toString())));
                        detalleItem.setPeriodoLiquidado(CalendarUtils.darFormatoYYYYMMDDGuionDate(detalle[12].toString()));

                        detalleItem.setCodigoEntidadDescuento(String.format("%04d", Long.parseLong(descuentoRVL[1].toString())));
                        detalleItem.setNombreEntidadDescuento(descuentoRVL[2].toString());

                        if (!beneficiariosProcesados
                                .containsKey(new KeyPersonaDTO(detalleItem.getTipoIdBeneficiario(), detalleItem.getIdBeneficiario()))) {
                            numeral++;
                            beneficiariosProcesados
                                    .put(new KeyPersonaDTO(detalleItem.getTipoIdBeneficiario(), detalleItem.getIdBeneficiario()), numeral);
                        }
                        detalleItem.setNumeral(beneficiariosProcesados
                                .get(new KeyPersonaDTO(detalleItem.getTipoIdBeneficiario(), detalleItem.getIdBeneficiario())));
                        
                        detallesItemEmpresaTrabajador.add(detalleItem);
                    }
                }
            }
            detalleDTO.setDetalleItems(detallesItemEmpresaTrabajador);
            detalleDTO.setCantidadTrabajadores(calcularCantidadPersonasConDescuentos(numeroSolicitud));
            detalleDTO.setPeriodoLiquidado(periodoLiquidado);

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return detalleDTO;
    }

    /**
     * Método que permite obtener los descuentos y dar el formato para generar la asociación con las personas
     * @param numeroSolicitud
     *        valor del número de radicación
     * @author rlopez
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<Object[]> consultarDescuentosDetalleLiquidacion(String numeroSolicitud) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarDescuentosDetalleLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            List<Object[]> descuentos = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DESCUENTOS_BENEFICIARIOS_POR_LIQUIDACION)
                    .setParameter("numeroRadicacion", numeroSolicitud).getResultList();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return descuentos;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Método que permite obtener los descuentos asociados a un Resultado de Validación de Liquidación
     * @param idRVL
     *        valor del identificador del Resultado Validación Liquidación
     * @param descuentos
     *        lista de descuentos asociados a la liquidación
     * @return lista de descuentos asociados al Resultado Validacion Liquidacion
     * @author rlopez
     */
    private List<Object[]> obtenerDescuentosRVL(BigDecimal idRVL, List<Object[]> descuentos) {
        List<Object[]> descuentosRVL = new ArrayList<>();
        for (Object[] registroDescuento : descuentos) {
            if (BigDecimal.valueOf(Double.parseDouble(registroDescuento[0].toString())).equals(idRVL)) {
                descuentosRVL.add(registroDescuento);
            }
        }
        return descuentosRVL;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#enviarResultadoLiquidacionAPagos(java.lang.String,
     *      java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void enviarResultadoLiquidacionAPagos(String nombreUsuario, String numeroRadicado) {
        String firmaMetodo = "ConsultasModeloSubsidio.enviarResultadoLiquidacionAPagos(String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

     //  try {
            StoredProcedureQuery procedimiento = entityManagerSubsidio
                    .createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_SM_UTIL_ENVIAR_CONSOLIDADO_A_PAGOS)
                    .setParameter("sNumeroRadicado", numeroRadicado).setParameter("sUsuario", nombreUsuario)
                    .setParameter("bFormaPago", Boolean.TRUE);

            procedimiento.execute();
       /*} catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        */

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#ejecutarSPLiquidacionEspecifica(com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum,
     *      java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void ejecutarSPLiquidacionEspecifica(TipoLiquidacionEspecificaEnum tipoLiquidacionEspecifica, String numeroRadicado) {
        String firmaMetodo = "ConsultasModeloSubsidio.ejecutarSPLiquidacionEspecifica(TipoLiquidacionEspecificaEnum,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        StoredProcedureQuery storedProcedure = entityManagerSubsidio
                .createStoredProcedureQuery(NamedQueriesConstants.EJECUTAR_SP_ORQUESTADOR_LIQUIDACION_ESPECIFICA);
        storedProcedure.registerStoredProcedureParameter("sTipoLiquidacionAjuste", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("sNumeroRadicado", String.class, ParameterMode.IN);
        storedProcedure.setParameter("sTipoLiquidacionAjuste", tipoLiquidacionEspecifica.toString());
        storedProcedure.setParameter("sNumeroRadicado", numeroRadicado);
        storedProcedure.execute();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#actualizarEstadoDerechoLiquidacion(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarEstadoDerechoLiquidacion(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.actualizarEstadoDerechoLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ESTADO_DERECHO_LIQUIDACION)
                    .setParameter("numeroRadicacion", numeroRadicacion).executeUpdate();

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarResultadoLiquidacionEspecifica(java.lang.String,
     *      java.util.Date, com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionCondicionesSubsidioModeloDTO)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ResultadoLiquidacionMasivaDTO consultarResultadoLiquidacionEspecifica(String numeroSolicitud, Date periodo) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarResultadoLiquidacionEspecifica(String,Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        logger.debug("String numeroSolicitud: " + numeroSolicitud + " - seguimiento undefined");

        ResultadoLiquidacionMasivaDTO result = new ResultadoLiquidacionMasivaDTO();

        ParametrizacionCondicionesSubsidioModeloDTO parametrizacionCondicionesDTO = consultarParametrizacionCondicionesLiquidacion(
                numeroSolicitud, periodo);
        
        Object[] avanceProcesoLiquidacion = consultarAvanceProcesoLiquidacion(numeroSolicitud);
        
        BigDecimal porcentaje =  (BigDecimal)avanceProcesoLiquidacion[1];
        Double porcentajeD =  porcentaje.doubleValue();
        Date fechaFin =  (Date)avanceProcesoLiquidacion[6];
        
        

        List<Object[]> registros = calcularMatrizResultado(numeroSolicitud, periodo, TipoProcesoLiquidacionEnum.AJUSTES_DE_CUOTA.name())
                .getResultList();
        
        if((porcentajeD < 100 && fechaFin != null) || (porcentajeD == 100 && (registros == null || registros.isEmpty()))){
            result.setFalloLiquidacion(Boolean.TRUE);
            return result;
        }
        
        result.setFalloLiquidacion(Boolean.FALSE);

        if (registros == null || registros.isEmpty()){
            result.setEstadoProceso(true);            
        }else{
            List<ResultadoMontoLiquidadoDTO> resultadoMonto = generarMontos(registros);
            result.setResultadoMonto(resultadoMonto);
            result.setEstadoProceso(false);
            List<CantidadEmpresaTrabajadorDTO> resultadoCantidad = calcularCantidades(numeroSolicitud, parametrizacionCondicionesDTO, periodo);
            result.setCantidadEmpresaTrabajador(resultadoCantidad);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#ejecutarSPLiquidacionReconocmiento(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void ejecutarSPLiquidacionReconocimiento(String numeroRadicado) {
        String firmaMetodo = "ConsultasModeloSubsidio.ejecutarSPLiquidacionReconocmiento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        StoredProcedureQuery storedProcedure = entityManagerSubsidio
                .createStoredProcedureQuery(NamedQueriesConstants.EJECUTAR_SP_ORQUESTADOR_LIQUIDACION_RECONOCIMIENTO);
        storedProcedure.registerStoredProcedureParameter("sNumeroRadicado", String.class, ParameterMode.IN);
        storedProcedure.setParameter("sNumeroRadicado", numeroRadicado);
        storedProcedure.execute();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#ejecutarSPLiquidacionReconocmiento(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<String> ejecutarSPGestionarColaEjecucionLiquidacion() {
        String firmaMetodo = "ConsultasModeloSubsidio.ejecutarSPGestionarColaEjecucionLiquidacion()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        //List<String[]> resultado = null;
        List<String> radicados = new ArrayList<>();
        
        radicados = entityManagerSubsidio
                .createStoredProcedureQuery(NamedQueriesConstants.EJECUTAR_SP_GESTIONAR_COLA_EJECUTION_LIQUIDACION)
                .getResultList(); 
        
      /*  if (resultado != null){
            for (String objects : resultado) {
            radicados.add(objects.toString());
            logger.debug("ZZZ:" + objects.toString());
            }
        }
*/
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return radicados;
    }


    /**
     * Método que permite obtener el estado que determina el resultado del proceso de liquidación específica
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return indicador del resultado del proceso de liquidación
     * 
     * @author rlopez
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean consultarEstadoProcesoLiquidacionEspecifica(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarEstadoProcesoLiquidacionEspecifica(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            Integer total = ((Number) entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_TOTAL_DERECHOS_LIQUIDACION_ESPECIFICA)
                    .setParameter("numeroRadicacion", numeroRadicacion).getSingleResult()).intValue();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return total.intValue() != 0;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#activarEnColaProcesoLiquidacionRechazada(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void activarEnColaProcesoLiquidacion(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.activarEnColaProcesoLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            StoredProcedureQuery procedimiento = entityManagerSubsidio
                    .createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_SM_UTIL_ACTIVAR_EN_COLA_PROCESO)
                    .setParameter("sNumeroRadicado", numeroRadicacion);

            procedimiento.execute();
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Sección de consultas para la liquidación de fallecimiento
     */

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarResultadoLiquidacionFallecimiento(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ResultadoLiquidacionFallecimientoDTO consultarResultadoLiquidacionFallecimiento(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarResultadoLiquidacionFallecimiento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoLiquidacionFallecimientoDTO resultadoLiquidacionDTO = null;
       // try {

        List<Object[]> registrosLiquidacion = entityManagerSubsidio
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_RESULTADO_LIQUIDACION_FALLECIMIENTO)
                .setParameter("numeroRadicacion", numeroRadicacion).getResultList();

        if (!registrosLiquidacion.isEmpty()) {
            resultadoLiquidacionDTO = procesarDatosResultadoLiquidacionFallecimiento(registrosLiquidacion);
            resultadoLiquidacionDTO.setResultadoProceso(consultarEstadoProcesoLiquidacionEspecifica(numeroRadicacion));
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultadoLiquidacionDTO;
       /* } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        */
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarResultadoLiquidacionFallecimiento(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ResultadoLiquidacionFallecimientoDTO consultarResultadoLiquidacionFallecimientoConfirmados(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarResultadoLiquidacionFallecimiento(String)"; 
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoLiquidacionFallecimientoDTO resultadoLiquidacionDTO = null;       

        List<Object[]> registrosLiquidacion = entityManagerSubsidio
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_RESULTADO_LIQUIDACION_FALLECIMIENTO_CONFIRMADOS)
                .setParameter("numeroRadicacion", numeroRadicacion).getResultList();

        if (!registrosLiquidacion.isEmpty()) {
            resultadoLiquidacionDTO = procesarDatosResultadoLiquidacionFallecimiento(registrosLiquidacion);
            resultadoLiquidacionDTO.setResultadoProceso(consultarEstadoProcesoLiquidacionEspecifica(numeroRadicacion));
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultadoLiquidacionDTO;
      
    }

    /**
     * Método que permite procesar la información de resultado para una liquidación de fallecimiento
     * 
     * @param registrosLiquidacion
     *        registros de la liquidación por fallecimiento
     * @return DTO con la información de la liquidación
     * 
     * @author rlopez
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private ResultadoLiquidacionFallecimientoDTO procesarDatosResultadoLiquidacionFallecimiento(List<Object[]> registrosLiquidacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.procesarDatosResultadoLiquidacionFallecimiento(List<Object[]>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        final Boolean valorUno = true;
        ResultadoLiquidacionFallecimientoDTO resultadoLiquidacionDTO = new ResultadoLiquidacionFallecimientoDTO();

        ResumenResultadosEvaluacionDTO resumenEvaluacionDTO = new ResumenResultadosEvaluacionDTO();
        resumenEvaluacionDTO.setIdCondicionTrabajador(Long.parseLong(registrosLiquidacion.get(0)[4].toString()));
        resumenEvaluacionDTO.setTipoIdentificacionTrabajador(TipoIdentificacionEnum.valueOf(registrosLiquidacion.get(0)[5].toString()));
        resumenEvaluacionDTO.setNumeroIdentificacionTrabajador(registrosLiquidacion.get(0)[6].toString());
        resumenEvaluacionDTO.setNombreTrabajador(registrosLiquidacion.get(0)[7].toString());
        resumenEvaluacionDTO.setIdCondicionPersona(Long.parseLong(registrosLiquidacion.get(0)[20].toString()));

        if (EstadoCumplimientoPersonaFallecimientoEnum.TRABAJADOR_NO_CUMPLE.name().equals(registrosLiquidacion.get(0)[21].toString())) {
            resumenEvaluacionDTO.setCumpleCondicionesTrabajador(TipoCumplimientoEnum.NO_CUMPLE);
            resultadoLiquidacionDTO.setResumenEvaluacion(resumenEvaluacionDTO);
            return resultadoLiquidacionDTO;
        }
        //El trabajador si cumple, ahora se debe evaluar cada beneficiario
        resumenEvaluacionDTO.setCumpleCondicionesTrabajador(TipoCumplimientoEnum.CUMPLE);
        resultadoLiquidacionDTO.setResumenEvaluacion(resumenEvaluacionDTO);

        List<ItemResultadoLiquidacionFallecimientoDTO> itemsResultadoEvaluacion = new ArrayList<>();
        List<ResultadoPorAdministradorLiquidacionFallecimientoDTO> resultadosPorAdministrador = new ArrayList<>();

        Map<KeyPersonaDTO, Integer> administradoresProcesados = new HashMap<>();
        Integer indicadorProcesamiento = 0;

        for (Object[] registroLiquidacion : registrosLiquidacion) {
            ItemResultadoLiquidacionFallecimientoDTO itemResultado = new ItemResultadoLiquidacionFallecimientoDTO();

            itemResultado.setIdCondicionBeneficiarioAfiliado(Long.valueOf(registroLiquidacion[0].toString()));
            itemResultado.setTipoIdentificacionBeneficiarioAfiliado(TipoIdentificacionEnum.valueOf(registroLiquidacion[1].toString()));
            itemResultado.setNumeroIdentificacionBeneficiarioAfiliado(registroLiquidacion[2].toString());
            itemResultado.setNombreBeneficiarioAfiliado(registroLiquidacion[3].toString());
            itemResultado.setParentesco(ClasificacionEnum.valueOf(registroLiquidacion[12].toString()));
            itemResultado.setTotalDerecho(BigDecimal.valueOf(Double.parseDouble(registroLiquidacion[13].toString())));
            if(registroLiquidacion[14] != null)
            	itemResultado.setTotalDescuentos(BigDecimal.valueOf(Double.parseDouble(registroLiquidacion[14].toString())));
            if(registroLiquidacion[22] != null){
                itemResultado.setTotalDescuentosPorEntidad(BigDecimal.valueOf(Double.parseDouble(registroLiquidacion[22].toString())));
            }
            itemResultado.setTotalPagar(BigDecimal.valueOf(Double.parseDouble(registroLiquidacion[15].toString())));
            itemResultado.setIdCondicionPersona(Long.parseLong(registroLiquidacion[19].toString()));
            itemResultado.setIndicadorConfirmacion(Boolean.FALSE);

            if (EstadoCumplimientoPersonaFallecimientoEnum.BENEFICIARIO_NO_CUMPLE.name().equals(registroLiquidacion[21].toString())) {
                itemResultado.setResultado(TipoCumplimientoEnum.NO_CUMPLE);
            }
            else {
                itemResultado.setResultado(TipoCumplimientoEnum.CUMPLE);
            }
            itemsResultadoEvaluacion.add(itemResultado);

            //Se evalua el caso en el que el administrador de subsidio no se ha procesado
            
            if (registroLiquidacion[8] != null && registroLiquidacion[9] != null && !administradoresProcesados.containsKey(new KeyPersonaDTO(TipoIdentificacionEnum.valueOf(registroLiquidacion[8].toString()),
                    registroLiquidacion[9].toString()))) {

                ResultadoPorAdministradorLiquidacionFallecimientoDTO resultadoPorAdmin = new ResultadoPorAdministradorLiquidacionFallecimientoDTO();

                resultadoPorAdmin.setTipoIdentificacionAdministrador(TipoIdentificacionEnum.valueOf(registroLiquidacion[8].toString()));
                if(registroLiquidacion[9] != null)                
                	resultadoPorAdmin.setNumeroIdentificacionAdministrador(registroLiquidacion[9].toString());
                if(registroLiquidacion[10] != null)
                	resultadoPorAdmin.setNombreAdministrador(registroLiquidacion[10].toString());
                if(registroLiquidacion[11] != null)
                	resultadoPorAdmin.setMedioDePagoAdministrador(registroLiquidacion[11].toString());

                List<ItemResultadoLiquidacionFallecimientoDTO> itemsAdmin = new ArrayList<>();
                if (registroLiquidacion[18].toString().equals(valorUno.toString())) {
                    //Se evalua el caso en el que el beneficiario de subsidio ya se haya confirmado
                    itemResultado.setIndicadorConfirmacion(Boolean.TRUE);
                    itemsAdmin.add(itemResultado);
                }
                resultadoPorAdmin.setItemsBeneficiarios(itemsAdmin);

                resultadosPorAdministrador.add(resultadoPorAdmin);
                administradoresProcesados.put(new KeyPersonaDTO(TipoIdentificacionEnum.valueOf(registroLiquidacion[8].toString()),
                        registroLiquidacion[9].toString()), indicadorProcesamiento++);
            }
            else {
                //Caso en el cual el administrador de subsidio asociado al beneficiario ya se ha procesado
                if (registroLiquidacion[18].toString().equals(valorUno.toString())) {
                    //Se evalua el caso en el que el beneficiario de subsidio ya se haya confirmado
                    itemResultado.setIndicadorConfirmacion(Boolean.TRUE);
                    resultadosPorAdministrador
                            .get(administradoresProcesados.get(new KeyPersonaDTO(
                                    TipoIdentificacionEnum.valueOf(registroLiquidacion[8].toString()), registroLiquidacion[9].toString())))
                            .getItemsBeneficiarios().add(itemResultado);
                }
            }
        }
        resultadoLiquidacionDTO.setItemsResultadoEvaluacion(itemsResultadoEvaluacion);
        resultadoLiquidacionDTO.setResultadosPorAdministrador(resultadosPorAdministrador);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultadoLiquidacionDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#confirmarBeneficiarioLiquidacionFallecimiento(java.lang.String,
     *      java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void confirmarBeneficiarioLiquidacionFallecimiento(String numeroRadicacion, Long idCondicionBeneficiario) {
        String firmaMetodo = "ConsultasModeloSubsidio.confirmarBeneficiarioLiquidacionFallecimiento(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_GESTION_SUBSIDIO_FALLECIMIENTO_BENEFICIARIO_CONFIRMADO)
                    .setParameter("numeroRadicacion", numeroRadicacion).setParameter("idCondicionBeneficiario", idCondicionBeneficiario)
                    .executeUpdate();

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#confirmarAfiliadoLiquidacionFallecimiento(java.lang.String,
     *      java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void confirmarAfiliadoLiquidacionFallecimiento(String numeroRadicacion, Long idCondicionAfiliado) {
        String firmaMetodo = "ConsultasModeloSubsidio.confirmarAfiliadoLiquidacionFallecimiento(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_GESTION_SUBSIDIO_FALLECIMIENTO_AFILIADO_CONFIRMADO)
                    .setParameter("numeroRadicacion", numeroRadicacion).setParameter("idCondicionTrabajador", idCondicionAfiliado)
                    .executeUpdate();

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarDetalleBeneficiarioLiquidacionFallecimiento(java.lang.String,
     *      java.lang.Long)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DetalleLiquidacionBeneficiarioFallecimientoDTO consultarDetalleBeneficiarioLiquidacionFallecimiento(String numeroRadicacion,
            Long idCondicionBeneficiario) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarDetalleBeneficiarioLiquidacionFallecimiento(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            List<Object[]> registrosDetalle = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_BENEFICIARIO_SUBSIDIO_FALLECIMIENTO)
                    .setParameter("numeroRadicacion", numeroRadicacion).setParameter("idCondicionBeneficiario", idCondicionBeneficiario)
                    .getResultList();

            Map<String, Integer> proyeccionesProcesadas = new HashMap<>();
            Integer indicadorProcesamientoProyecciones = 0;
            Map<String, Integer> entidadesProcesadas = new HashMap<>();
            Integer indicadorProcesamientoEntidades = 0;

            DetalleLiquidacionBeneficiarioFallecimientoDTO detalleBeneficiarioDTO = new DetalleLiquidacionBeneficiarioFallecimientoDTO();
            if (!registrosDetalle.isEmpty()) {
                detalleBeneficiarioDTO.setNombreBeneficiario(registrosDetalle.get(0)[1].toString());
                detalleBeneficiarioDTO
                        .setTipoIdentificacionBeneficiario(TipoIdentificacionEnum.valueOf(registrosDetalle.get(0)[2].toString()));
                detalleBeneficiarioDTO.setNumeroIdentificacionBeneficiario(registrosDetalle.get(0)[3].toString());
                detalleBeneficiarioDTO.setParentesco(registrosDetalle.get(0)[4].toString());
                //Unicamente los beneficiarios que se han confirmado con resultado CUMPLE aparecen en la sección de resultados por administrador, 
                //de tal forma que esto siempre va a ser cumple 
                detalleBeneficiarioDTO.setResultado(TipoCumplimientoEnum.CUMPLE);
                detalleBeneficiarioDTO.setPeriodo(CalendarUtils.darFormatoYYYYMMDDGuionDate(registrosDetalle.get(0)[13].toString()));

                //Se obtiene el primer periodo de la cuota, a través del cual se calculan las posiciones de los descuentos
                LocalDate fechaPrimerCuota = LocalDate.parse(registrosDetalle.get(0)[5].toString());

                List<ItemDetalleLiquidacionBeneficiarioFallecimientoDTO> itemsDetalleBeneficiario = new ArrayList<>();
                List<DescuentoEntidadBeneficiarioFallecimientoDTO> detallesDescuentosEntidades = new ArrayList<>();

                for (Object[] registroDetalle : registrosDetalle) {

                    if (!proyeccionesProcesadas.containsKey(registroDetalle[0].toString())) {
                        ItemDetalleLiquidacionBeneficiarioFallecimientoDTO itemDetalleBeneficiario = new ItemDetalleLiquidacionBeneficiarioFallecimientoDTO();

                        itemDetalleBeneficiario.setPeriodo(CalendarUtils.darFormatoYYYYMMDDGuionDate(registroDetalle[5].toString()));
                        itemDetalleBeneficiario
                                .setMontoCuotaLiquidada(BigDecimal.valueOf(Double.parseDouble(registroDetalle[6].toString())));
                        itemDetalleBeneficiario
                                .setDescuentoCuotaMonetaria(BigDecimal.valueOf(Double.parseDouble(registroDetalle[7].toString())));
                        itemDetalleBeneficiario
                                .setDescuentoEntidades(BigDecimal.valueOf(Double.parseDouble(registroDetalle[8].toString())));
                        itemDetalleBeneficiario.setTotalPagar(BigDecimal.valueOf(Double.parseDouble(registroDetalle[9].toString())));
                        logger.info("REGISTRO DETALLE");
                        logger.info(registroDetalle[14]);
                        itemDetalleBeneficiario
                                .setValorCuotaAjustada(BigDecimal.valueOf(Double.parseDouble(registroDetalle[14].toString())));

                        itemsDetalleBeneficiario.add(itemDetalleBeneficiario);

                        if (registroDetalle[10] != null) {
                            //Se evalua el caso en el que se tenga un descuento por una entidad para el periodo

                            //Se calcula el mes de descuento
                            LocalDate fechaPeriodoDescuento = LocalDate.parse(registroDetalle[5].toString());
                            Integer mesDescuento = Period.between(fechaPrimerCuota, fechaPeriodoDescuento).getMonths();

                            if (entidadesProcesadas.containsKey(registroDetalle[10].toString())) {
                                //Se evalua el caso en el que ya se haya procesado algún registro con descuento para la misma entidad

                                BigDecimal descuentoActual = detallesDescuentosEntidades
                                        .get(entidadesProcesadas.get(registroDetalle[10].toString())).getDescuentos().get(mesDescuento);

                                detallesDescuentosEntidades.get(entidadesProcesadas.get(registroDetalle[10].toString())).getDescuentos()
                                .set(mesDescuento, descuentoActual
                                        .add(BigDecimal.valueOf(Double.parseDouble(registroDetalle[8].toString()))));
                                //detallesDescuentosEntidades.get(entidadesProcesadas.get(registroDetalle[10].toString())).getDescuentos()
                                //        .set(mesDescuento, descuentoActual
                                //                .add(BigDecimal.valueOf(Double.parseDouble(registroDetalle[12].toString()))));
                            }
                            else {
                                //Se evalua el caso en el que no se haya procesado la entidad
                                DescuentoEntidadBeneficiarioFallecimientoDTO descuentoEntidad = new DescuentoEntidadBeneficiarioFallecimientoDTO();

                                Supplier<BigDecimal> supplier = () -> new BigDecimal(0);
                                List<BigDecimal> descuentos = Stream.generate(supplier).limit(12).collect(Collectors.toList());
                                
                                if(registroDetalle[11] != null)
                                    descuentoEntidad.setNombreEntidad(registroDetalle[11].toString());
                                descuentoEntidad.setDescuentos(descuentos);
                                descuentoEntidad.getDescuentos().set(mesDescuento,
                                        BigDecimal.valueOf(Double.parseDouble(registroDetalle[8].toString())));
                                //descuentoEntidad.getDescuentos().set(mesDescuento,
                                //        BigDecimal.valueOf(Double.parseDouble(registroDetalle[12].toString())));

                                detallesDescuentosEntidades.add(descuentoEntidad);
                                entidadesProcesadas.put(registroDetalle[10].toString(), indicadorProcesamientoEntidades++);
                            }
                        }
                        proyeccionesProcesadas.put(registroDetalle[0].toString(), indicadorProcesamientoProyecciones++);
                    }
                    else {
                        //Si se presenta una proyección que ya se procesó, es a causa de un nuevo registro con descuento.
                        if (registroDetalle[10] != null) {
                            //Se calcula el mes de descuento
                            LocalDate fechaPeriodoDescuento = LocalDate.parse(registroDetalle[5].toString());
                            Integer mesDescuento = Period.between(fechaPrimerCuota, fechaPeriodoDescuento).getMonths();

                            if (entidadesProcesadas.containsKey(registroDetalle[10].toString())) {
                                //Se evalua el caso en el que ya se haya procesado algún registro con descuento para una entidad ya procesada
                                BigDecimal descuentoActual = detallesDescuentosEntidades
                                        .get(entidadesProcesadas.get(registroDetalle[10].toString())).getDescuentos().get(mesDescuento);
                                detallesDescuentosEntidades.get(entidadesProcesadas.get(registroDetalle[10].toString())).getDescuentos()
                                         .set(mesDescuento, descuentoActual
                                                  .add(BigDecimal.valueOf(Double.parseDouble(registroDetalle[8].toString()))));
                                //detallesDescuentosEntidades.get(entidadesProcesadas.get(registroDetalle[10].toString())).getDescuentos()
                                //        .set(mesDescuento, descuentoActual
                                //                .add(BigDecimal.valueOf(Double.parseDouble(registroDetalle[12].toString()))));
                            }
                            else {
                                //Se evalua el caso en el que no se haya procesado un descuento para la entidad
                                DescuentoEntidadBeneficiarioFallecimientoDTO descuentoEntidad = new DescuentoEntidadBeneficiarioFallecimientoDTO();

                                Supplier<BigDecimal> supplier = () -> new BigDecimal(0);
                                List<BigDecimal> descuentos = Stream.generate(supplier).limit(12).collect(Collectors.toList());
                                
                                if(registroDetalle[11] != null)
                                    descuentoEntidad.setNombreEntidad(registroDetalle[11].toString());
                                descuentoEntidad.setDescuentos(descuentos);
                                descuentoEntidad.getDescuentos().set(mesDescuento,
                                                BigDecimal.valueOf(Double.parseDouble(registroDetalle[8].toString())));
                                //descuentoEntidad.getDescuentos().set(mesDescuento,
                                //        BigDecimal.valueOf(Double.parseDouble(registroDetalle[12].toString())));

                                detallesDescuentosEntidades.add(descuentoEntidad);
                                entidadesProcesadas.put(registroDetalle[10].toString(), indicadorProcesamientoEntidades++);
                            }
                        }
                    }
                }
                detalleBeneficiarioDTO.setItemsDetalle(itemsDetalleBeneficiario);
                detalleBeneficiarioDTO.setDetallesDescuentosEntidades(detallesDescuentosEntidades);
            }
            return detalleBeneficiarioDTO;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarEstadoDerechoBeneficiarios(com.asopagos.dto.subsidiomonetario.liquidacion.PersonaFallecidaTrabajadorDTO)
     */
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarEstadoDerechoBeneficiarios(com.asopagos.dto.subsidiomonetario.liquidacion.PersonaFallecidaTrabajadorDTO)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public PersonaFallecidaTrabajadorDTO consultarEstadoDerechoBeneficiarios(PersonaFallecidaTrabajadorDTO persona) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarEstadoDerechoBeneficiarios(PersonaFallecidaTrabajadorDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        PersonaFallecidaTrabajadorDTO per = persona;
        
        List<BeneficiariosAfiliadoDTO> beneficiarios = persona.getListaBeneficiarios();
        
        for(BeneficiariosAfiliadoDTO ben : beneficiarios){
            List<String> registros = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_DERECHO_BENEFICIARIOS)
                    .setParameter("tipoDocumento", ben.getTipoDocumentoBeneficiario().toString())
                    .setParameter("numeroDocumento", ben.getNumeroIdentificacionBeneficiario())
                    //.setParameter("periodo", SubsidioDateUtils.ponerFechaEnPrimerDia(new Date()))
                    .getResultList();
            
            if(!registros.isEmpty()){
                if((registros.get(0) != null) && 
                        (registros.get(0).equals(EstadoDerechoSubsidioEnum.DERECHO_ASIGNADO.toString()))){
                    ben.setDerechoUltimaLiquidacion(Boolean.valueOf(true));
                }else{
                    ben.setDerechoUltimaLiquidacion(Boolean.valueOf(false));
                }
            }else{
                ben.setDerechoUltimaLiquidacion(Boolean.valueOf(false));
            }
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return per;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarCondicionesPersonas(java.lang.String,
     *      java.util.List)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<String, CondicionPersonaLiquidacionDTO> consultarCondicionesPersonas(String numeroRadicacion,
            List<Long> identificadoresCondiciones) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarCondicionesPersonas(String,List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            Map<String, CondicionPersonaLiquidacionDTO> codicionesPersonasDTO = new LinkedHashMap<>();
            List<Object[]> registrosCondiciones = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONDICIONES_PERSONAS_LIQUIDACION)
                    .setParameter("numeroRadicacion", numeroRadicacion)
                    .setParameter("identificadoresCondiciones", identificadoresCondiciones).getResultList();

            for (Object[] registroCondicion : registrosCondiciones) {
                CondicionPersonaLiquidacionDTO condicionPersonaDTO = new CondicionPersonaLiquidacionDTO();

                condicionPersonaDTO.setIdCondicionPersona(Long.parseLong(registroCondicion[0].toString()));
                condicionPersonaDTO.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(registroCondicion[1].toString()));
                condicionPersonaDTO.setNumeroIdentificacion(registroCondicion[2].toString());
                condicionPersonaDTO.setRazonSocial(registroCondicion[3].toString());

                codicionesPersonasDTO.put(condicionPersonaDTO.getIdCondicionPersona().toString(), condicionPersonaDTO);
            }

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return codicionesPersonasDTO;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarCondicionesEntidadesDescuento(java.lang.String,
     *      java.util.List)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<Long, CondicionEntidadDescuentoLiquidacionDTO> consultarCondicionesEntidadesDescuento(String numeroRadicacion,
            List<Long> identificadoresCondiciones) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarCondicionesEntidadesDescuento(String,List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            Map<Long, CondicionEntidadDescuentoLiquidacionDTO> codicionesEntidadesDTO = new LinkedHashMap<>();

            List<Object[]> registrosCondiciones = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONDICIONES_ENTIDADES_DESCUENTO_LIQUIDACION)
                    .setParameter("numeroRadicacion", numeroRadicacion)
                    .setParameter("identificadoresCondiciones", identificadoresCondiciones).getResultList();

            for (Object[] registroCondicion : registrosCondiciones) {
                CondicionEntidadDescuentoLiquidacionDTO condicionEntidadDTO = new CondicionEntidadDescuentoLiquidacionDTO();

                condicionEntidadDTO.setIdPrioridad(Long.parseLong(registroCondicion[0].toString()));
                condicionEntidadDTO.setNombreEntidad(registroCondicion[1].toString());

                codicionesEntidadesDTO.put(condicionEntidadDTO.getIdPrioridad(), condicionEntidadDTO);
            }

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return codicionesEntidadesDTO;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#ejecutarSPLiquidacionFallecimiento(java.lang.String, java.lang.Long, java.lang.Boolean)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void ejecutarSPLiquidacionFallecimiento(String numeroRadicado, Long periodo, Boolean beneficiarioFallecido) {
        String firmaMetodo = "ConsultasModeloSubsidio.ejecutarSPLiquidacionFallecimiento(String, Long, Boolean)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        StoredProcedureQuery storedProcedure = entityManagerSubsidio
                .createStoredProcedureQuery(NamedQueriesConstants.EJECUTAR_SP_ORQUESTADOR_LIQUIDACION_FALLECIMIENTO);
        storedProcedure.registerStoredProcedureParameter("sNumeroRadicado", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("dPeriodo", Date.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("bBeneficiarioFallecido", Boolean.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("bformaPago", Boolean.class, ParameterMode.IN);
        storedProcedure.setParameter("sNumeroRadicado", numeroRadicado);
        storedProcedure.setParameter("dPeriodo", SubsidioDateUtils.ponerFechaEnPrimerDia(new Date(periodo)));
        storedProcedure.setParameter("bBeneficiarioFallecido", beneficiarioFallecido);
        storedProcedure.setParameter("bformaPago", beneficiarioFallecido);
        storedProcedure.execute();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarPeriodoFallecimientoAfiliado(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Date consultarPeriodoFallecimientoAfiliado(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarPeriodoFallecimientoAfiliado(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            LocalDate fecha = LocalDate
                    .parse(entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERIODO_FALLECIMIENTO_AFILIADO)
                            .setParameter("numeroRadicacion", numeroRadicacion).getSingleResult().toString())
                    .withDayOfMonth(1);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return CalendarUtils.darFormatoYYYYMMDDGuionDate(fecha.toString());
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarPeriodoFallecimientoAfiliado(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Date consultarPeriodoFallecimientoBeneficiario(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarPeriodoFallecimientoBeneficiario(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            LocalDate fecha = LocalDate
                    .parse(entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERIODO_FALLECIMIENTO_BENEFICIARIO)
                            .setParameter("numeroRadicacion", numeroRadicacion).getSingleResult().toString())
                    .withDayOfMonth(1);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return CalendarUtils.darFormatoYYYYMMDDGuionDate(fecha.toString());
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarDesembolsoSubsidioLiquidacionFallecimiento(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ResultadoLiquidacionFallecimientoDTO consultarDesembolsoSubsidioLiquidacionFallecimiento(String numeroRadicacion,
            Date periodoFallecimiento) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarDesembolsoSubsidioLiquidacionFallecimiento(String,Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoLiquidacionFallecimientoDTO resultadoLiquidacionDTO = new ResultadoLiquidacionFallecimientoDTO();
        try {
            String estadoAporte = (String) (entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_APORTE_AFILIADO)
                    .setParameter("numeroRadicacion", numeroRadicacion).setParameter("periodo", periodoFallecimiento).getSingleResult());

            resultadoLiquidacionDTO.setEstadoAporte(EstadoAporteSubsidioEnum.valueOf(estadoAporte));
            resultadoLiquidacionDTO
                    .setExistenciaAportesPeriodo(resultadoLiquidacionDTO.getEstadoAporte().equals(EstadoAporteSubsidioEnum.SIN_INFORMACION)
                            ? Boolean.FALSE : Boolean.TRUE);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return resultadoLiquidacionDTO;
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarCondicionesBeneficiarioFallecido(com.asopagos.dto.subsidiomonetario.liquidacion.PersonaFallecidaTrabajadorDTO)
     */    
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public PersonaFallecidaTrabajadorDTO consultarCondicionesBeneficiarioFallecido(PersonaFallecidaTrabajadorDTO persona) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarDesembolsoSubsidioLiquidacionFallecimiento(String,Date)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        PersonaFallecidaTrabajadorDTO per = persona;        
        List<BeneficiariosAfiliadoDTO> beneficiarios = persona.getListaBeneficiarios();
        
        for (BeneficiariosAfiliadoDTO ben : beneficiarios) {
            // Buscar el caso 6
            Date fechaFallecido = ben.getFechaFallecido() != null ? ben.getFechaFallecido() : new Date();
            List<Boolean> regC6 = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_AFILIACION_AFILIADO_FECHA_DEFUN_BENE)
                    .setParameter("tipoDocumento", ben.getTipoDocumentoBeneficiario().toString())
                    .setParameter("numeroDocumento", ben.getNumeroIdentificacionBeneficiario())
                    .setParameter("periodo", SubsidioDateUtils.ponerFechaEnPrimerDia(fechaFallecido), TemporalType.DATE)
                    .setParameter("idPersona", persona.getIdPersona()).getResultList();
            //Si el beneficiario esta activo en el CASO 6 (Caso 5), se pone la variable como FALSE
            //            if(!regC6.isEmpty() && (regC6.size() == 1) && (regC6.get(0).equals(Boolean.TRUE))){
            //                ben.setActivoAntesDeMorir(Boolean.FALSE);
            //            }else {
            //                ben.setActivoAntesDeMorir(regC6.get(0));
            //            }  
            logger.info("Datos beneficiario:" + ben.getTipoDocumentoBeneficiario().toString() + " , " +ben.getNumeroIdentificacionBeneficiario());
            logger.info("Periodo: " + SubsidioDateUtils.ponerFechaEnPrimerDia(fechaFallecido));
            logger.info("idPersona: " + persona.getIdPersona());

            logger.info("Resultado regC6 test: " + regC6);

            if (regC6 != null) {
                logger.info("Resultado regC6.size(): " + regC6.size());
                if (!regC6.isEmpty()) {
                    logger.info("entra aca 1");
                    ben.setActivoAntesDeMorir(Boolean.FALSE);
                } else {
                    logger.info("entra aca 2");
                    ben.setActivoAntesDeMorir(Boolean.TRUE);
                }
            } else {
                logger.info("entra aca 3");
                ben.setActivoAntesDeMorir(Boolean.TRUE);
            }

           logger.info("activo antes de morir: " + ben.getActivoAntesDeMorir());
           logger.info("fecha fallecimiento: "+ben.getFechaFallecido());

            // Verifica si una persona ha recibido subsidio en el periodo de fallecimiento
            Integer resultadoSubsidio = (Integer) entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SUBSIDIO_FALLECIMIENTO_BENEFICIARIO)
                    .setParameter("tipoIdentificacion", ben.getTipoDocumentoBeneficiario().toString())
                    .setParameter("numeroIdentificacion", ben.getNumeroIdentificacionBeneficiario())
                    .getSingleResult();

            // Se asume que la consulta devuelve una lista de enteros donde 1 significa que recibió el subsidio
            boolean recibioSubsidio = (resultadoSubsidio != null && resultadoSubsidio.equals(1));
            ben.setValidacionSubsidioFallecimientoBeneficiario(recibioSubsidio);
 
            //CASO 3 HU-317-506
            ben.setTrabajadorActivoAlFallecerBeneficiario(
                    validarTrabajadorActivoAlFallecerBeneficiario(persona.getIdPersona(), ben.getFechaFallecido()));

            // CASO 9 HU-317-506 
            ben.setBeneficiarioRegistraAdmonSubsidio(validarBeneficiarioRegistraAdmonSubsidio(ben.getTipoDocumentoBeneficiario().toString(), ben.getNumeroIdentificacionBeneficiario()));
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return per;
    }

    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarBeneficiarioActivoAlFallecerAfiliado(com.asopagos.dto.subsidiomonetario.liquidacion.PersonaFallecidaTrabajadorDTO)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public PersonaFallecidaTrabajadorDTO consultarBeneficiarioActivoAlFallecerAfiliado(PersonaFallecidaTrabajadorDTO persona) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarBeneficiarioActivoAlFallecerAfiliado(PersonaFallecidaTrabajadorDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        PersonaFallecidaTrabajadorDTO per = persona;

        Long count = Long.parseLong(entityManagerSubsidio
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_BENE_ACTIVO_AL_MORIR_AFILIADO)
                .setParameter("tipoDocumento", per.getTipoIdentificacion().toString())
                .setParameter("numeroDocumento", per.getNumeroIdentificacion())
                .setParameter("periodo", SubsidioDateUtils.ponerFechaEnPrimerDia(new Date()), TemporalType.DATE)
                .getSingleResult().toString());
 
        if(count.longValue() > 0){            
            per.setBeneficiarioActivoAlMorirAfiliado(Boolean.TRUE); 
        }else{
            per.setBeneficiarioActivoAlMorirAfiliado(Boolean.FALSE);   
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return per;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#enviarResultadoLiquidacionAPagosFallecimiento(java.lang.String,
     *      java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void enviarResultadoLiquidacionAPagosFallecimiento(String nombreUsuario, String numeroRadicado,
            ModoDesembolsoEnum modoDesembolso) {
        String firmaMetodo = "ConsultasModeloSubsidio.enviarResultadoLiquidacionAPagosFallecimiento(String,String, ModoDesembolsoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        logger.info("dmorales -- Entra a enviarResultadoLiquidacionAPagosFallecimiento");
        try {
            Boolean formaPago = ModoDesembolsoEnum.MES_POR_MES.equals(modoDesembolso) ? Boolean.FALSE : Boolean.TRUE;
            logger.info("dmorales -- formaPago: " + formaPago);
            logger.info("dmorales -- nombreUsuario: " + nombreUsuario); 
            logger.info("dmorales -- numeroRadicado: " + numeroRadicado);
            StoredProcedureQuery procedimiento = entityManagerSubsidio
                    .createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_SM_UTIL_ENVIAR_CONSOLIDADO_A_PAGOS)
                    .setParameter("sNumeroRadicado", numeroRadicado).setParameter("sUsuario", nombreUsuario)
                    .setParameter("bFormaPago", formaPago);

            procedimiento.execute();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarValidacionFallidaPersonaFallecimiento(java.lang.String,
     *      java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ConjuntoValidacionSubsidioEnum consultarValidacionFallidaPersonaFallecimiento(String numeroRadicacion, Long condicionPersona) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarValidacionFallidaPersonaFallecimiento(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        //try {
            List<String> validaciones = //ConjuntoValidacionSubsidioEnum.valueOf(
                    entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_VALIDACION_FALLIDA_PERSONA_FALLECIMIENTO)
                            .setParameter("numeroRadicacion", numeroRadicacion).setParameter("condicionPersona", condicionPersona)
                            .getResultList();
                            //.getSingleResult().toString());
            
            ConjuntoValidacionSubsidioEnum validacion = null;
            
            if (validaciones.size()>0){
                validacion =  ConjuntoValidacionSubsidioEnum.valueOf(validaciones.get(validaciones.size()-1).toString()); 
            } 
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return validacion;
        /*} catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        */
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarIdentificadorPersonaCore(java.lang.String,
     *      java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Long consultarIdentificadorPersonaCore(String numeroRadicacion, Long condicionPersona) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarIdentificadorPersonaCore(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            Long idPersonaCore = ((Number) entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_IDENTIFICADOR_PERSONA_CORE)
                    .setParameter("numeroRadicacion", numeroRadicacion).setParameter("condicionPersona", condicionPersona)
                    .getSingleResult()).longValue();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return idPersonaCore;
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#ejecutarSPLiquidacionFallecimiento(java.lang.String,
     *      java.lang.Long, java.lang.Boolean)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void ejecutarSPLiquidacionFallecimientoGestionPersona(String numeroRadicado, Long periodo, Boolean beneficiarioFallecido,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.ejecutarSPLiquidacionFallecimiento(String, Long, Boolean)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        StoredProcedureQuery storedProcedure = entityManagerSubsidio
                .createStoredProcedureQuery(NamedQueriesConstants.EJECUTAR_SP_ORQUESTADOR_LIQUIDACION_FALLECIMIENTO);
        storedProcedure.registerStoredProcedureParameter("sNumeroRadicado", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("dPeriodo", Date.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("bBeneficiarioFallecido", Boolean.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("bformaPago", Boolean.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("iNumeroIdentificacion", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("sTipoIdentificacion", String.class, ParameterMode.IN);
        storedProcedure.setParameter("sNumeroRadicado", numeroRadicado);
        storedProcedure.setParameter("dPeriodo", new Date(periodo));
        storedProcedure.setParameter("bBeneficiarioFallecido", beneficiarioFallecido);
        storedProcedure.setParameter("bformaPago", beneficiarioFallecido);
        storedProcedure.setParameter("iNumeroIdentificacion", numeroIdentificacion);
        storedProcedure.setParameter("sTipoIdentificacion", tipoIdentificacion.name());

        storedProcedure.execute();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarInformacionComunicadosLiquidacionMasiva(java.lang.String,
     *      com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object[]> consultarInformacionComunicadosLiquidacionMasiva(String numeroRadicacion, EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicado) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarInformacionComunicadosLiquidacionMasiva(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        String namedQuery;
        String namedQueryRegistros;
        switch (etiquetaPlantillaComunicado) {
        case COM_SUB_DIS_PAG_EMP:
            namedQuery = NamedQueriesConstants.CONSULTAR_VALORES_COMUNICADO_63;
            namedQueryRegistros = NamedQueriesConstants.CONSULTAR_NUMERO_VALORES_COMUNICADO_63;
            break;
        case COM_SUB_DIS_PAG_TRA:
            namedQuery = NamedQueriesConstants.CONSULTAR_VALORES_COMUNICADO_64;
            namedQueryRegistros = NamedQueriesConstants.CONSULTAR_NUMERO_VALORES_COMUNICADO_64;
            break;
        case COM_SUB_DIS_PAG_ADM_SUB:
        case COM_SUB_DIS_FAL_PRO_ADM_SUB:
        case COM_SUB_DIS_FAL_PAG_ADM_SUB:
            namedQuery = NamedQueriesConstants.CONSULTAR_VALORES_COMUNICADO_65;
            namedQueryRegistros = NamedQueriesConstants.CONSULTAR_NUMERO_VALORES_COMUNICADO_65;
            break;
        case COM_SUB_DIS_FAL_PRO_TRA:
        case COM_SUB_DIS_FAL_PAG_TRA:
            namedQuery = NamedQueriesConstants.CONSULTAR_VALORES_COMUNICADO_74_77;
            namedQueryRegistros = NamedQueriesConstants.CONSULTAR_NUMERO_VALORES_COMUNICADO_74_77;
            break;
        default:
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + "LA ETIQUETA NO CORRESPONDE A NINGUN PROCESO VALIDO");
            return null;
        }
        List<Object[]> variables = (List<Object[]>) entityManagerSubsidio
                .createNamedQuery(namedQuery)
                .setParameter("numeroRadicacion", numeroRadicacion).getResultList();
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + "Agregando todas las variables . TOTAL REGISTROS : "+ variables.size());
        return variables;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarPorcentajeAvanceProcesoLiquidacion(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Integer consultarPorcentajeAvanceProcesoLiquidacion(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarPorcentajeAvanceProcesoLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            Integer porcentajeAvance = ((Number) entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PORCENTAJE_AVANCE_LIQUIDACION)
                    .setParameter("numeroRadicacion", numeroRadicacion).getSingleResult()).intValue();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return porcentajeAvance;
        } catch (NonUniqueResultException e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            //throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (NoResultException e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            return 0;

            //throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            //throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        return -1;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarPorcentajeAvanceProcesoLiquidacion(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Object[] consultarAvanceProcesoLiquidacion(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarPorcentajeAvanceProcesoLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        Object[] apl = null; 
 
        try {
            apl = (Object[]) entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_AVANCE_PROCESO_LIQUIDACION)
                    .setParameter("numeroRadicacion", numeroRadicacion).getSingleResult();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);  
            
            return apl;
        } catch (NonUniqueResultException e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            //throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (NoResultException e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            //throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            //throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }        
        
        return apl;
    }

    
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#cancelarProcesoLiquidacion(java.lang.String)
     */
    @Override
    public void cancelarProcesoLiquidacion(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.cancelarProcesoLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.REGISTRAR_CANCELACION_PROCESO_LIQUIDACION)
                    .setParameter("numeroRadicacion", numeroRadicacion).executeUpdate();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /**
     * Método encargado de realizar la validación del CASO 3 HU-317-506
     * CASO 3 (Liquidación no procedente - Afiliado): Validar que en al menos un día del periodo en el que fallece
     * el beneficiario,el trabajador tenga “Estado de afiliación como dependiente” igual a “Activo”
     * @param idPersona
     *        <code>Long</code>
     *        Identificador principal del trabajador
     * @param fechaFallecimientoBeneficiario
     *        <code>Date</code>
     *        Fecha en la cual el beneficiario fallecio
     * @return FALSE si el trabajador estuvo al menos un día activo en el periodo de los beneficiarios, TRUE si no lo estuvo.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private Boolean validarTrabajadorActivoAlFallecerBeneficiario(Long idPersona, Date fechaFallecimientoBeneficiario) {
        String firmaMetodo = "ConsultasModeloSubsidio.validarTrabajadroActivoAlFallecerBeneficiario(Long,Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String esValidado = null;
        try {
            logger.info("=====Validacion Trabajador Activo Fallecimiento Beneficiario=====");
            logger.info(idPersona);
            logger.info(fechaFallecimientoBeneficiario);
            esValidado = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_TRABAJADOR_ACTIVO_AL_FALLECER_BENEFICIARIO_HU_317506_CASO3)
                    .setParameter("idTrabajador", idPersona).setParameter("fechaFallecimiento", fechaFallecimientoBeneficiario)
                    .getSingleResult().toString();

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " Ocurrio un error al obtener la validación del Caso 3 HU 317-506");
        }
        logger.info("Resultado de la validación del Caso 3 HU 317-506: " + esValidado);
        logger.info("Condicion de la validación del Caso 3 HU 317-506: " + esValidado.equals("1"));
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return esValidado.equals("1") ? Boolean.TRUE : Boolean.FALSE;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private Boolean validarBeneficiarioRegistraAdmonSubsidio(String tipoDocumentoBeneficiario, String numeroIdentificacionBeneficiario) {
        String firmaMetodo = "ConsultasModeloSubsidio.validarBeneficiarioRegistraAdmonSubsidio(String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String esValidado = null;
        try {
            logger.info(tipoDocumentoBeneficiario);
            logger.info(numeroIdentificacionBeneficiario);

            esValidado = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ADMON_SUBSIDIO_POR_BENEFICIARIO_HU_317506_CASO9)
                    .setParameter("tipoDocumentoBeneficiario", tipoDocumentoBeneficiario).setParameter("numeroIdentificacionBeneficiario", numeroIdentificacionBeneficiario)
                    .getSingleResult().toString();

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " Ocurrio un error al obtener la validación del Caso 9 HU 317-506");
        }
        return esValidado.equals("1") ? Boolean.TRUE : Boolean.FALSE;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarNombreFallecido(java.lang.String)
     */
    public List<Object[]> consultarNombreFallecido(String numeroRadicacion){
        String firmaMetodo = "ConsultasModeloSubsidio.consultarNombreFallecido(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        List<Object[]> variables = (List<Object[]>) entityManagerSubsidio
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_VALORES_COMUNICADO_74_75_77_78)
                .setParameter("numeroRadicacion", numeroRadicacion).getResultList();
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return variables;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#iniciarAvanceProcesoLiquidacion(java.lang.String)
     */
    @Override
    public void iniciarAvanceProcesoLiquidacion(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.iniciarAvanceProcesoLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.REGISTRAR_AVANCE_PROCESO_LIQUIDACION)
                    .setParameter("numeroRadicacion", numeroRadicacion).executeUpdate();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarCancelacionProcesoLiquidacion(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Boolean consultarCancelacionProcesoLiquidacion(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarCancelacionProcesoLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {
            Boolean cancelada = (Boolean) entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_CANCELACION_PROCESO_LIQUIDACION)
                    .setParameter("numeroRadicacion", numeroRadicacion).getSingleResult();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return cancelada;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarLiquidacionesPorEmpleador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.util.Date, java.util.Date, java.util.Date, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConsultaLiquidacionSubsidioMonetarioDTO> consultarLiquidacionesPorEmpleador(
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, Date periodo, Date fechaInicio,
            Date fechaFin, String numeroRadicacion, UriInfo uri, HttpServletResponse response) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarLiquidacionesPorEmpleador(TipoIdentificacionEnum, String, Long, Long, Long, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {

            logger.warn("-0-0-0-0-0-0- parametros");
            try{
                logger.info("tipoIdentificacion: "+tipoIdentificacion.name());
                logger.info("numeroIdentificacion: "+numeroIdentificacion);
                logger.info("periodo: "+periodo);
                logger.info("fechaInicio: "+fechaInicio);
                logger.info("fechaFin: "+fechaFin);
                logger.info("fechnumeroRadicacionaFin: "+numeroRadicacion);
            }catch(Exception e){

            }
            
            List<Object[]> resultado = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_VISTA_360_SOLICITUDES_LIQUIDACION_EMPLEADOR)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name() == null ? "" : tipoIdentificacion.name())
                    .setParameter("numeroIdentificacion", numeroIdentificacion == null ? ""  : numeroIdentificacion).setParameter("periodo", periodo ==  null ? "" : format.format(periodo))
                    .setParameter("fechaInicio", fechaInicio == null ? "" : format.format(fechaInicio)).setParameter("fechaFin",fechaFin == null ? "" : format.format(fechaFin))
                    .setParameter("numeroRadicado", numeroRadicacion == null ? "": numeroRadicacion)
                    .getResultList();
            
            List<ConsultaLiquidacionSubsidioMonetarioDTO> liquidaciones = new ArrayList<>();
            ConsultaLiquidacionSubsidioMonetarioDTO liquidacion;
            String tipo = null;
            for (Object[] obj : resultado) {
                liquidacion = new ConsultaLiquidacionSubsidioMonetarioDTO();
                if(obj[0] != null) {
                    tipo = (TipoProcesoLiquidacionEnum.valueOf(obj[0].toString())).getDescripcionVista360();
                }
                if(obj[1] != null) {
                    tipo = (TipoLiquidacionEspecificaEnum.valueOf(obj[1].toString())).getDescripcionVista360();
                }
                liquidacion.setTipoLiquidacion(tipo);
                liquidacion.setNumeroRadicado(obj[2] == null ? null : obj[2].toString());
                liquidacion.setFechaLiquidacion(obj[3] == null ? null : (Date) obj[3]);
                //liquidacion.setCumple(Integer.valueOf((Integer) obj[4]).equals(1) ? Boolean.TRUE : Boolean.FALSE);
                liquidacion.setCumple(obj[5] == null ? Boolean.TRUE : Boolean.FALSE);
                liquidacion.setRazonCausal(obj[5] == null ? null : obj[5].toString());
                liquidacion.setPeriodo(obj[6] == null ? null : CalendarUtils.darFormatoYYYYMMDDGuionDate(obj[6].toString()));
                liquidacion.setNumeroTrabajadores(Long.parseLong(obj[7] == null ? "0": obj[7].toString()));
                liquidacion.setMontoLiquidado(BigDecimal.valueOf(Double.parseDouble(obj[8] == null ? "0": obj[8].toString())));
                liquidacion.setMontoDescontado(BigDecimal.valueOf(Double.parseDouble(obj[9] == null ? "0": obj[9].toString())));
                liquidacion.setMontoParaPago(BigDecimal.valueOf(Double.parseDouble(obj[10] == null ? "0": obj[10].toString())));
                
                liquidaciones.add(liquidacion);
            }
            
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return liquidaciones;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarLiquidacionesPorTrabajador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.util.Date, java.util.Date, java.util.Date, java.lang.String, com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum, javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    // public List<ConsultaLiquidacionSubsidioMonetarioDTO> consultarLiquidacionesPorTrabajador(
    //         TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, List<Date> periodo, Date fechaInicio,
    //         Date fechaFin, String numeroRadicacion, TipoProcesoLiquidacionEnum tipoLiquidacion, UriInfo uri, HttpServletResponse response) {
        
    //     String firmaMetodo = "SubsidioMonetarioBusiness.consultarLiquidacionesPorTrabajador(TipoIdentificacionEnum, String, List<Date>, Date, Date, String, TipoProcesoLiquidacionEnum, UriInfo, HttpServletResponse)";
    //     logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

    //     try {
    //         Crear los CompletableFuture para ejecutar las consultas en paralelo
    //         CompletableFuture<List<Object[]>> future1 = CompletableFuture.supplyAsync(() -> {
    //             return entityManagerSubsidio
    //                 .createNamedQuery(NamedQueriesConstants.CONSULTAR_VISTA_360_SOLICITUDES_LIQUIDACION_TRABAJADOR)
    //                 .setParameter("tipoIdentificacion", tipoIdentificacion.toString())
    //                 .setParameter("numeroIdentificacion", numeroIdentificacion)
    //                 .setParameter("periodo", periodo)
    //                 .setParameter("numPeriodo", (periodo == null) ? 0 : periodo.size())
    //                 .setParameter("fechaInicio", fechaInicio)
    //                 .setParameter("fechaFin", fechaFin)
    //                 .setParameter("numeroRadicado", numeroRadicacion)
    //                 .setParameter("tipoLiquidacion", tipoLiquidacion == null? null : tipoLiquidacion.toString())
    //                 .getResultList();
    //         });
            
    //         CompletableFuture<List<Object[]>> future2 = CompletableFuture.supplyAsync(() -> {
    //             return entityManagerSubsidio
    //                 .createNamedQuery(NamedQueriesConstants.CONSULTAR_VISTA_360_SOLICITUDES_LIQUIDACION_TRABAJADOR_2)
    //                 .setParameter("tipoIdentificacion", tipoIdentificacion.toString())
    //                 .setParameter("numeroIdentificacion", numeroIdentificacion)
    //                 .setParameter("periodo", periodo)
    //                 .setParameter("numPeriodo", (periodo == null) ? 0 : periodo.size())
    //                 .setParameter("fechaInicio", fechaInicio)
    //                 .setParameter("fechaFin", fechaFin)
    //                 .setParameter("numeroRadicado", numeroRadicacion)
    //                 .setParameter("tipoLiquidacion", tipoLiquidacion == null? null : tipoLiquidacion.toString())
    //                 .getResultList();
    //         });

    //         Esperar a que ambas consultas se completen para obtener resultados
    //         List<Object[]> resultado1 = future1.get();
    //         List<Object[]> resultado2 = future2.get();

    //         Crear lista de resultados
    //         List<ConsultaLiquidacionSubsidioMonetarioDTO> liquidaciones = new ArrayList<>();

    //         Procesar los resultados de la primera consulta
    //         resultado1.parallelStream()
    //             .map(obj -> procesarDatosResultadoConsultarLiquidacionesPorTrabajador(obj, true))
    //             .forEach(liquidaciones::add);

    //         Procesar los resultados de la primera consulta
    //         resultado2.parallelStream()
    //             .map(obj -> procesarDatosResultadoConsultarLiquidacionesPorTrabajador(obj, false))
    //             .forEach(liquidaciones::add);
            
    //         logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    //         return liquidaciones;

    //     } catch (InterruptedException e) {
    //         logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    //         Thread.currentThread().interrupt();
    //         throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
    //     } catch (ExecutionException e) {
    //         logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    //         throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
    //     }
    // } 
    public List<ConsultaLiquidacionSubsidioMonetarioDTO> consultarLiquidacionesPorTrabajador(
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, List<Date> periodo, Date fechaInicio,
            Date fechaFin, String numeroRadicacion, TipoProcesoLiquidacionEnum tipoLiquidacion, UriInfo uri, HttpServletResponse response) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarLiquidacionesPorEmpleador(TipoIdentificacionEnum, String, Long, Long, Long, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        String periodoFormateado = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            
            if(periodo != null){
                for(Date fecha : periodo){
                    periodoFormateado = "'"+format.format(fecha)+"',";
                }
            }
            List<Object[]> resultado = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_VISTA_360_SOLICITUDES_LIQUIDACION_TRABAJADOR)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.toString())
                    .setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("periodo", periodo)
                    .setParameter("numPeriodo", (periodo == null) ? 0 : periodo.size())
                    .setParameter("fechaInicio", fechaInicio).setParameter("fechaFin", fechaFin)
                    .setParameter("numeroRadicado", numeroRadicacion)
                    .setParameter("tipoLiquidacion", tipoLiquidacion == null? null : tipoLiquidacion.toString()).getResultList();
            
            StoredProcedureQuery query = entityManagerSubsidio.createStoredProcedureQuery(NamedQueriesConstants.USP_SM_GET_SOLICITUDES_LIQUIDACION_TRABAJADOR)
            .registerStoredProcedureParameter("tipoIdentificacion", String.class, ParameterMode.IN)
            .setParameter("tipoIdentificacion", tipoIdentificacion.name())
            .registerStoredProcedureParameter("numeroIdentificacion", String.class, ParameterMode.IN)
            .setParameter("numeroIdentificacion", numeroIdentificacion)
            .registerStoredProcedureParameter("tipoLiquidacion", String.class, ParameterMode.IN)
            .setParameter("tipoLiquidacion",tipoLiquidacion == null ? "":tipoLiquidacion.name())
            .registerStoredProcedureParameter("periodo", String.class, ParameterMode.IN)
            .setParameter("periodo", periodoFormateado == null ? "" : periodoFormateado)
            .registerStoredProcedureParameter("numeroRadicado", String.class, ParameterMode.IN)
            .setParameter("numeroRadicado",numeroRadicacion == null ? "" : numeroRadicacion)
            .registerStoredProcedureParameter("fechaInicio", String.class, ParameterMode.IN)
            .setParameter("fechaInicio",fechaInicio == null ? "" : format.format(fechaInicio))
            .registerStoredProcedureParameter("fechaFin", String.class, ParameterMode.IN)
            .setParameter("fechaFin", fechaFin == null ? "":format.format(fechaFin));
            
            List<Object[]> resultado2 = query.getResultList();
            
            final int iNumeroRadicado = 0;
            final int iFechaInicio = 1;
            final int iTipoLiquidacion = 2;
            final int iTipoLiquidacionEspecifica = 3;
            final int iRazonSocial = 4;
            final int iCumple = 5;
            final int iMontoLiquidado = 6;
            final int iMontoDescontado = 7;
            final int iMontoParaPago = 8; 
            final int iTipoIdentificacionEmp = 9;
            final int iNumeroIdentificacionEmp = 10;
            final int iTotalDescuentosPorEntidad = 11;
            final int iCumpleSubsidioPeriodoFallecimiento = 12;

            List<ConsultaLiquidacionSubsidioMonetarioDTO> liquidaciones = new ArrayList<>();
            ConsultaLiquidacionSubsidioMonetarioDTO liquidacion;
            String tipo = null;
            for (Object[] obj : resultado) {
                liquidacion = new ConsultaLiquidacionSubsidioMonetarioDTO();
                if(obj[iTipoLiquidacion] != null) {
                    liquidacion.setTipoProcesoLiquidacion(TipoProcesoLiquidacionEnum.valueOf(obj[iTipoLiquidacion].toString()));
                    tipo = liquidacion.getTipoProcesoLiquidacion().getDescripcionVista360();
                }
                if(obj[iTipoLiquidacionEspecifica] != null) {
                    liquidacion.setTipoLiquidacionEspecifica(TipoLiquidacionEspecificaEnum.valueOf(obj[iTipoLiquidacionEspecifica].toString()));
                    tipo = liquidacion.getTipoLiquidacionEspecifica().getDescripcionVista360();
                }
                liquidacion.setTipoLiquidacion(tipo);
                liquidacion.setNumeroRadicado(obj[iNumeroRadicado] == null ? null : obj[iNumeroRadicado].toString());
                liquidacion.setFechaLiquidacion(obj[iFechaInicio] == null ? null : (Date) obj[iFechaInicio]);
                liquidacion.setRazonSocialEmpleador(obj[iRazonSocial] == null ? null : obj[iRazonSocial].toString());
                liquidacion.setNumeroIdentificacionEmpl(obj[iNumeroIdentificacionEmp] == null ? null : obj[iNumeroIdentificacionEmp].toString());
                if(obj[iTipoIdentificacionEmp] != null) {
                    liquidacion.setTipoIdentificacionEmpl(TipoIdentificacionEnum.valueOf(obj[iTipoIdentificacionEmp].toString()));
                }
                liquidacion.setCumple(Integer.valueOf((Integer) obj[iCumple]).equals(1) ? Boolean.TRUE : Boolean.FALSE);
                liquidacion.setMontoLiquidado(BigDecimal.valueOf(Double.parseDouble(obj[iMontoLiquidado] == null ? "0": obj[iMontoLiquidado].toString())));

                logger.info("conteo liquidacion items");
                logger.info(obj.length);

                if(obj.length > iTotalDescuentosPorEntidad && obj[iTotalDescuentosPorEntidad] != null) {
                    liquidacion.setTotalDescuentosPorEntidad(BigDecimal.valueOf(Double.parseDouble(obj[iTotalDescuentosPorEntidad] == null ? "0": obj[iTotalDescuentosPorEntidad].toString())));
                }

                if(obj.length > iCumpleSubsidioPeriodoFallecimiento && obj[iCumpleSubsidioPeriodoFallecimiento] != null) {
                    liquidacion.setCumpleSubsidioPeriodoFallecimiento(Integer.valueOf((Integer) obj[iCumpleSubsidioPeriodoFallecimiento]).equals(1) ? Boolean.TRUE : Boolean.FALSE);
                }

                liquidacion.setMontoDescontado(BigDecimal.valueOf(Double.parseDouble(obj[iMontoDescontado] == null ? "0": obj[iMontoDescontado].toString())));
                liquidacion.setMontoParaPago(BigDecimal.valueOf(Double.parseDouble(obj[iMontoParaPago] == null ? "0": obj[iMontoParaPago].toString())));
                liquidacion.setMontoRetirado(BigDecimal.ZERO);
                
                liquidacion.setMontoDispersado(BigDecimal.ZERO);
                liquidacion.setMontoProgramado(BigDecimal.ZERO);
                liquidacion.setTotalPagar(BigDecimal.ZERO);
                
                liquidaciones.add(liquidacion);
            }
            for (Object[] obj : resultado2) {
                liquidacion = new ConsultaLiquidacionSubsidioMonetarioDTO();
                if(obj[iTipoLiquidacion] != null) {
                    liquidacion.setTipoProcesoLiquidacion(TipoProcesoLiquidacionEnum.valueOf(obj[iTipoLiquidacion].toString()));
                    tipo = liquidacion.getTipoProcesoLiquidacion().getDescripcionVista360();
                }
                if(obj[iTipoLiquidacionEspecifica] != null) {
                    liquidacion.setTipoLiquidacionEspecifica(TipoLiquidacionEspecificaEnum.valueOf(obj[iTipoLiquidacionEspecifica].toString()));
                    tipo = liquidacion.getTipoLiquidacionEspecifica().getDescripcionVista360();
                }
                liquidacion.setTipoLiquidacion(tipo);
                liquidacion.setNumeroRadicado(obj[iNumeroRadicado] == null ? null : obj[iNumeroRadicado].toString());
                liquidacion.setFechaLiquidacion(obj[iFechaInicio] == null ? null : (Date) obj[iFechaInicio]);
                liquidacion.setRazonSocialEmpleador(obj[iRazonSocial] == null ? null : obj[iRazonSocial].toString());
                liquidacion.setNumeroIdentificacionEmpl(obj[iNumeroIdentificacionEmp] == null ? null : obj[iNumeroIdentificacionEmp].toString());
                if(obj[iTipoIdentificacionEmp] != null) {
                    liquidacion.setTipoIdentificacionEmpl(TipoIdentificacionEnum.valueOf(obj[iTipoIdentificacionEmp].toString()));
                }
                liquidacion.setCumple(Integer.valueOf((Integer) obj[iCumple]).equals(1) ? Boolean.TRUE : Boolean.FALSE);
                liquidacion.setMontoLiquidado(BigDecimal.valueOf(Double.parseDouble(obj[iMontoLiquidado] == null ? "0": obj[iMontoLiquidado].toString())));
                liquidacion.setMontoDescontado(BigDecimal.valueOf(Double.parseDouble(obj[iMontoDescontado] == null ? "0": obj[iMontoDescontado].toString())));
                liquidacion.setMontoParaPago(BigDecimal.valueOf(Double.parseDouble(obj[iMontoParaPago] == null ? "0": obj[iMontoParaPago].toString())));
                liquidacion.setMontoRetirado(BigDecimal.ZERO);
                
                liquidacion.setMontoDispersado(BigDecimal.ZERO);
                liquidacion.setMontoProgramado(BigDecimal.ZERO);
                liquidacion.setTotalPagar(BigDecimal.ZERO);
                
                liquidaciones.add(liquidacion);
            }
            
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return liquidaciones;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    // private ConsultaLiquidacionSubsidioMonetarioDTO procesarDatosResultadoConsultarLiquidacionesPorTrabajador (Object[] obj, boolean primerConsulta) {
        
    //     String firmaMetodo = "SubsidioMonetarioBusiness.procesarDatosResultadoConsultarLiquidacionesPorTrabajador(Object[])";
    //     logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

    //     try{
    //         final int iNumeroRadicado = 0;
    //         final int iFechaInicio = 1;
    //         final int iTipoLiquidacion = 2;
    //         final int iTipoLiquidacionEspecifica = 3;
    //         final int iRazonSocial = 4;
    //         final int iCumple = 5;
    //         final int iMontoLiquidado = 6;
    //         final int iMontoDescontado = 7;
    //         final int iMontoParaPago = 8; 
    //         final int iTipoIdentificacionEmp = 9;
    //         final int iNumeroIdentificacionEmp = 10;

    //         //Crear DTO de la liquidación
    //         ConsultaLiquidacionSubsidioMonetarioDTO liquidacion = new ConsultaLiquidacionSubsidioMonetarioDTO();
    //         String tipo = null;
            
    //         if(obj[iTipoLiquidacion] != null) {
    //             liquidacion.setTipoProcesoLiquidacion(TipoProcesoLiquidacionEnum.valueOf(obj[iTipoLiquidacion].toString()));
    //             tipo = liquidacion.getTipoProcesoLiquidacion().getDescripcionVista360();
    //         }
    //         if(obj[iTipoLiquidacionEspecifica] != null) {
    //             liquidacion.setTipoLiquidacionEspecifica(TipoLiquidacionEspecificaEnum.valueOf(obj[iTipoLiquidacionEspecifica].toString()));
    //             tipo = liquidacion.getTipoLiquidacionEspecifica().getDescripcionVista360();
    //         }
    //         liquidacion.setTipoLiquidacion(tipo);
    //         liquidacion.setNumeroRadicado(obj[iNumeroRadicado] == null ? null : obj[iNumeroRadicado].toString());
    //         liquidacion.setFechaLiquidacion(obj[iFechaInicio] == null ? null : (Date) obj[iFechaInicio]);
    //         liquidacion.setRazonSocialEmpleador(obj[iRazonSocial] == null ? null : obj[iRazonSocial].toString());
    //         liquidacion.setNumeroIdentificacionEmpl(obj[iNumeroIdentificacionEmp] == null ? null : obj[iNumeroIdentificacionEmp].toString());
    //         if(obj[iTipoIdentificacionEmp] != null) {
    //             liquidacion.setTipoIdentificacionEmpl(TipoIdentificacionEnum.valueOf(obj[iTipoIdentificacionEmp].toString()));
    //         }
    //         liquidacion.setCumple(Integer.valueOf((Integer) obj[iCumple]).equals(1) ? Boolean.TRUE : Boolean.FALSE);
    //         liquidacion.setMontoLiquidado(BigDecimal.valueOf(Double.parseDouble(obj[iMontoLiquidado] == null ? "0": obj[iMontoLiquidado].toString())));
    //         liquidacion.setMontoDescontado(BigDecimal.valueOf(Double.parseDouble(obj[iMontoDescontado] == null ? "0": obj[iMontoDescontado].toString())));
    //         liquidacion.setMontoParaPago(BigDecimal.valueOf(Double.parseDouble(obj[iMontoParaPago] == null ? "0": obj[iMontoParaPago].toString())));
    //         liquidacion.setMontoRetirado(BigDecimal.ZERO);
            
    //         liquidacion.setMontoDispersado(BigDecimal.ZERO);
    //         liquidacion.setMontoProgramado(BigDecimal.ZERO);
    //         liquidacion.setTotalPagar(BigDecimal.ZERO);

    //         logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    //         return liquidacion;

    //     } catch (Exception e) {
    //         logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
    //         throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
    //     }
    // }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarValidacionesLiquidacionesPorTrabajador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.String, java.lang.Boolean)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConsultaLiquidacionSubsidioMonetarioDTO> consultarValidacionesLiquidacionesPorTrabajador(
                                                                            TipoIdentificacionEnum tipoIdentificacion, 
                                                                            String numeroIdentificacion, 
                                                                            String numeroRadicacion, 
                                                                            Boolean segmentoTrabajador, 
                                                                            TipoIdentificacionEnum tipoIdentificacionEmpl, 
                                                                            String numeroIdentificacionEmpl) {

        String firmaMetodo = "SubsidioMonetarioBusiness.consultarValidacionesLiquidacionesPorTrabajador(TipoIdentificacionEnum, String, String, Boolean, TipoIdentificacionEnum, String)";
        
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        logger.info("tipoIdAfi: " + tipoIdentificacion.name() + " numeroIdAfi: " + numeroIdentificacion );
        logger.info(" tipoIdEmpl: " + tipoIdentificacionEmpl.name() + "numeroIdEmpl: " + numeroIdentificacionEmpl);
        long timeconsultarValidacionesLiquidacionesPorTrabajador = System.currentTimeMillis();
        List<ConsultaLiquidacionSubsidioMonetarioDTO> validaciones = new ArrayList<>();
        
        try {
       /*     Instant startTime = Instant.now();

            CompletableFuture<List<Object[]>> consultarValidacionesLiquidacionesFuturas = CompletableFuture.supplyAsync(() -> {
                logger.info("Inicio tiempo storedProcedure consultarValidacionesLiquidacionesFuturas: " + Instant.now());
                List<Object[]> resultado = ejecutarConsultarValidacionesLiquidaciones(segmentoTrabajador, tipoIdentificacion, numeroIdentificacion, 
                                                        numeroRadicacion, tipoIdentificacionEmpl, numeroIdentificacionEmpl);
                logger.debug("Fin tiempo consulta consultarValidacionesLiquidacionesFuturas: " + Instant.now()); 
                return resultado;
            });
            CompletableFuture<Date> consultarPeriodoRegularLiquidacion = CompletableFuture.supplyAsync(() -> {
                logger.info("Inicio tiempo query consultarPeriodoRegularLiquidacion: " + Instant.now());
                Date periodoRegular = ejecutarConsultarPeriodoRegularLiquidacion(numeroRadicacion);
                logger.info("Fin tiempo query consultarPeriodoRegularLiquidacion: " + Instant.now());
                return periodoRegular;
            });*/

            List<Object[]> resultado = ejecutarConsultarValidacionesLiquidaciones(segmentoTrabajador, tipoIdentificacion, numeroIdentificacion, 
            numeroRadicacion, tipoIdentificacionEmpl, numeroIdentificacionEmpl);
            long timeejecutarConsultarValidacionesLiquidaciones = System.currentTimeMillis();
            logger.info("Finaliza Cuenta aporte ejecutarConsultarValidacionesLiquidaciones " + (timeejecutarConsultarValidacionesLiquidaciones - timeconsultarValidacionesLiquidacionesPorTrabajador) + " ms");

            Date periodoRegular = ejecutarConsultarPeriodoRegularLiquidacion(numeroRadicacion);
            long timeejecutarConsultarPeriodoRegularLiquidacion = System.currentTimeMillis();
            logger.info("Finaliza Cuenta aporte ejecutarConsultarPeriodoRegularLiquidacion " + (timeejecutarConsultarPeriodoRegularLiquidacion - timeconsultarValidacionesLiquidacionesPorTrabajador) + " ms");

           // List<Object[]> resultado = consultarValidacionesLiquidacionesFuturas.get();
           // Date periodoRegular = consultarPeriodoRegularLiquidacion.get();

            validaciones = procesarResultados(resultado, periodoRegular, segmentoTrabajador);
            long timeprocesarResultados = System.currentTimeMillis();
            logger.info("Finaliza Cuenta aporte procesarResultados " + (timeprocesarResultados - timeconsultarValidacionesLiquidacionesPorTrabajador) + " ms");

            logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
 
        } catch (Exception e) {
            logger.error("Excepción durante la consulta" + e);
            logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        return validaciones;
    }

    private List<Object[]> ejecutarConsultarValidacionesLiquidaciones(Boolean segmentoTrabajador, TipoIdentificacionEnum tipoIdentificacion, 
                                        String numeroIdentificacion, String numeroRadicacion, TipoIdentificacionEnum tipoIdentificacionEmpl, 
                                        String numeroIdentificacionEmpl) {
        
        String firmaMetodo = "SubsidioMonetarioBusiness.ejecutarConsultarValidacionesLiquidaciones(Boolean, TipoIdentificacionEnum, String, String, TipoIdentificacionEnum, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo); 
        
        List<Object[]> resultado = null;

        if (Boolean.TRUE.equals(segmentoTrabajador)) {
            logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            resultado = entityManagerSubsidio.createStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_VALIDACIONES_LIQUIDACION)
                    .registerStoredProcedureParameter("numeroRadicacion", String.class, ParameterMode.IN)
                    .setParameter("numeroRadicacion", numeroRadicacion)
                    .registerStoredProcedureParameter("tipoIdentificacion", String.class, ParameterMode.IN)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.toString())
                    .registerStoredProcedureParameter("numeroIdentificacion", String.class, ParameterMode.IN)
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .registerStoredProcedureParameter("tipoIdentificacionEmpl", String.class, ParameterMode.IN)
                    .setParameter("tipoIdentificacionEmpl", tipoIdentificacionEmpl.toString())
                    .registerStoredProcedureParameter("numeroIdentificacionEmpl", String.class, ParameterMode.IN)
                    .setParameter("numeroIdentificacionEmpl", numeroIdentificacionEmpl)
                    .getResultList();
            logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo); 
            return resultado;
        } else {
            resultado = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_VISTA_360_VALIDACIONES_LIQUIDACION_EMPLEADOR)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.toString())
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("tipoIdentificacionEmpl", tipoIdentificacionEmpl.toString())
                    .setParameter("numeroIdentificacionEmpl", numeroIdentificacionEmpl)
                    .setParameter("numeroRadicado", numeroRadicacion)
                    .getResultList();
            logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return resultado;
        }  
    } 

    private Date ejecutarConsultarPeriodoRegularLiquidacion(String numeroRadicacion) {

        String firmaMetodo = "SubsidioMonetarioBusiness.ejecutarConsultarPeriodoRegularLiquidacion(String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo); 

        Date resultado = (Date) entityManagerSubsidio
            .createNamedQuery(NamedQueriesConstants.CONSULTAR_MAXIMO_PERIODO_REGULAR_LIQUIDACION)
            .setParameter("numeroRadicado", numeroRadicacion)
            .getSingleResult();
        
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        return resultado;
    }

    private List<ConsultaLiquidacionSubsidioMonetarioDTO> procesarResultados(List<Object[]> resultado, Date periodoRegular, Boolean segmentoTrabajador) {
        
        String firmaMetodo = "SubsidioMonetarioBusiness.procesarResultados(List<Object[]>, Date, Boolean)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        final int iTipoPeriodo = 0;
        final int iPeriodo = 1;
        final int iCumple = 2;
    
        List<ConsultaLiquidacionSubsidioMonetarioDTO> validaciones = new ArrayList<>();
    
        Calendar cPeriodoRegular = new GregorianCalendar();
        cPeriodoRegular.setTime(periodoRegular);
    
        Calendar cPeriodoRet1 = new GregorianCalendar();
        cPeriodoRet1.setTime(periodoRegular);
        cPeriodoRet1.add(Calendar.MONTH, -1);
    
        Calendar cPeriodoRet2 = new GregorianCalendar();
        cPeriodoRet2.setTime(periodoRegular);
        cPeriodoRet2.add(Calendar.MONTH, -2);
    
        logger.info("Inicia tiempo procesarResultados en paralelo: " + Instant.now());
    
        resultado.parallelStream().forEach(obj -> {
            Boolean cumple = Integer.valueOf((Integer) obj[iCumple]).equals(1);
            TipoPeriodoEnum tipoPeriodo = obj[iTipoPeriodo] != null ? TipoPeriodoEnum.valueOf(obj[iTipoPeriodo].toString()) : null;
            Date periodo = obj[iPeriodo] == null ? null : CalendarUtils.darFormatoYYYYMMDDGuionDate(obj[iPeriodo].toString());
    
            procesarValidacion(obj, periodo, cumple, tipoPeriodo, periodoRegular, cPeriodoRegular, cPeriodoRet1, cPeriodoRet2, segmentoTrabajador, validaciones);
        });
    
        logger.info("Finaliza tiempo procesarResultados en paralelo: " + Instant.now());
        
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo); 
        return validaciones;
    }

    private void procesarValidacion(Object[] obj, Date periodo, Boolean cumple, TipoPeriodoEnum tipoPeriodo, Date periodoRegular, 
                                Calendar cPeriodoRegular, Calendar cPeriodoRet1, Calendar cPeriodoRet2, Boolean segmentoTrabajador, 
                                List<ConsultaLiquidacionSubsidioMonetarioDTO> validaciones) {
        
        String firmaMetodo = "SubsidioMonetarioBusiness.procesarValidacion(Object[], Date, Boolean, TipoPeriodoEnum, Date, Calendar, Calendar, Calendar, Boolean, List<ConsultaLiquidacionSubsidioMonetarioDTO>)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);  

        final int iCumple = 2;
        final int iEnColaProcesoPorNovedad = 3;
        final int iEnColaProcesoPorAporte = 4;
        final int iPresentaReingreso = 5;
        final int iResultadoCausal = 6;
        final int iMontoLiquidado = 7;
        final int iMontoDescontado = 8;
        final int iMontoParaPago = 9;

        StringBuilder causalTMP = new StringBuilder();

        ConsultaLiquidacionSubsidioMonetarioDTO vRegular = new ConsultaLiquidacionSubsidioMonetarioDTO();
        ConsultaLiquidacionSubsidioMonetarioDTO vRetNovedad = new ConsultaLiquidacionSubsidioMonetarioDTO();
        ConsultaLiquidacionSubsidioMonetarioDTO vRetAporte = new ConsultaLiquidacionSubsidioMonetarioDTO();
        ConsultaLiquidacionSubsidioMonetarioDTO vRetReingreso = new ConsultaLiquidacionSubsidioMonetarioDTO();

        if (TipoPeriodoEnum.REGULAR.equals(tipoPeriodo)) {
            vRegular = inicializarDatosValidaciones(vRegular, tipoPeriodo, periodo, segmentoTrabajador);
            if (Boolean.TRUE.equals(cumple)) {
                vRegular.setCumple(Boolean.TRUE);
                if (Boolean.TRUE.equals(segmentoTrabajador)) {
                    vRegular.setMontoLiquidado(BigDecimal.valueOf(Double.parseDouble(obj[iMontoLiquidado] == null ? "0" : obj[iMontoLiquidado].toString())));
                    vRegular.setMontoDescontado(BigDecimal.valueOf(Double.parseDouble(obj[iMontoDescontado] == null ? "0" : obj[iMontoDescontado].toString())));
                    vRegular.setMontoParaPago(BigDecimal.valueOf(Double.parseDouble(obj[iMontoParaPago] == null ? "0" : obj[iMontoParaPago].toString())));
                }
            } else {
                vRegular.setRazonCausal(obj[iResultadoCausal] == null ? null : obj[iResultadoCausal].toString());
            }
            validaciones.add(vRegular);
        } else {
            Boolean mostrarNovRei = Boolean.FALSE;
            Calendar cPeriodo = new GregorianCalendar();
            cPeriodo.setTime(periodo);

            if (cPeriodo.equals(cPeriodoRet1) || cPeriodo.equals(cPeriodoRet2)) {
                mostrarNovRei = Boolean.TRUE;
            }

            Integer numeroValidaciones = validaciones.size();
            
            vRetNovedad = inicializarDatosValidaciones(vRetNovedad, tipoPeriodo, periodo, segmentoTrabajador);
            vRetNovedad.setTipoRetroactivo(TipoRetroactivoEnum.POR_NOVEDAD);
            if ((Boolean.TRUE.equals(obj[iEnColaProcesoPorNovedad]) ||
                    (Boolean.FALSE.equals(obj[iEnColaProcesoPorNovedad]) &&
                    Boolean.FALSE.equals(obj[iEnColaProcesoPorAporte]) &&
                    Boolean.FALSE.equals(obj[iPresentaReingreso]))
                ) && cumple) {
                vRetNovedad.setCumple(Boolean.TRUE);
            } else if (
                    Boolean.FALSE.equals(obj[iEnColaProcesoPorNovedad]) &&
                    Boolean.FALSE.equals(obj[iEnColaProcesoPorAporte]) &&
                    Boolean.FALSE.equals(obj[iPresentaReingreso]) && 
                    cumple) {
                if(cPeriodo.equals(cPeriodoRet1) || cPeriodo.equals(cPeriodoRet2)) {
                    vRetNovedad.setCumple(Boolean.TRUE);
                }
            }
            
            vRetAporte = inicializarDatosValidaciones(vRetAporte, tipoPeriodo, periodo, segmentoTrabajador);
            vRetAporte.setTipoRetroactivo(TipoRetroactivoEnum.POR_APORTE);
            if (Boolean.TRUE.equals(obj[iEnColaProcesoPorAporte]) && cumple){
                vRetAporte.setCumple(Boolean.TRUE);
            }
            
            vRetReingreso = inicializarDatosValidaciones(vRetReingreso, tipoPeriodo, periodo, segmentoTrabajador);
            vRetReingreso.setTipoRetroactivo(TipoRetroactivoEnum.POR_REINGRESO);
            if (Boolean.TRUE.equals(obj[iPresentaReingreso]) && cumple){
                vRetReingreso.setCumple(Boolean.TRUE);
            }
            
            if(Boolean.TRUE.equals(segmentoTrabajador)) {     
                if (Boolean.TRUE.equals(vRetReingreso.getCumple())) {
                    vRetReingreso.setMontoLiquidado(BigDecimal.valueOf(Double.parseDouble(obj[iMontoLiquidado] == null ? "0": obj[iMontoLiquidado].toString())));
                    vRetReingreso.setMontoDescontado(BigDecimal.valueOf(Double.parseDouble(obj[iMontoDescontado] == null ? "0": obj[iMontoDescontado].toString())));
                    vRetReingreso.setMontoParaPago(BigDecimal.valueOf(Double.parseDouble(obj[iMontoParaPago] == null ? "0": obj[iMontoParaPago].toString())));
                } else if (Boolean.TRUE.equals(vRetNovedad.getCumple())) {
                    vRetNovedad.setMontoLiquidado(BigDecimal.valueOf(Double.parseDouble(obj[iMontoLiquidado] == null ? "0": obj[iMontoLiquidado].toString())));
                    vRetNovedad.setMontoDescontado(BigDecimal.valueOf(Double.parseDouble(obj[iMontoDescontado] == null ? "0": obj[iMontoDescontado].toString())));
                    vRetNovedad.setMontoParaPago(BigDecimal.valueOf(Double.parseDouble(obj[iMontoParaPago] == null ? "0": obj[iMontoParaPago].toString())));
                } else if (Boolean.TRUE.equals(vRetAporte.getCumple())) {
                    vRetAporte.setMontoLiquidado(BigDecimal.valueOf(Double.parseDouble(obj[iMontoLiquidado] == null ? "0": obj[iMontoLiquidado].toString())));
                    vRetAporte.setMontoDescontado(BigDecimal.valueOf(Double.parseDouble(obj[iMontoDescontado] == null ? "0": obj[iMontoDescontado].toString())));
                    vRetAporte.setMontoParaPago(BigDecimal.valueOf(Double.parseDouble(obj[iMontoParaPago] == null ? "0": obj[iMontoParaPago].toString())));
                } else {
                    vRetNovedad.setMontoLiquidado(BigDecimal.valueOf(Double.parseDouble(obj[iMontoLiquidado] == null ? "0": obj[iMontoLiquidado].toString())));
                    vRetNovedad.setMontoDescontado(BigDecimal.valueOf(Double.parseDouble(obj[iMontoDescontado] == null ? "0": obj[iMontoDescontado].toString())));
                    vRetNovedad.setMontoParaPago(BigDecimal.valueOf(Double.parseDouble(obj[iMontoParaPago] == null ? "0": obj[iMontoParaPago].toString())));
                } 
            }
            
            causalTMP.setLength(0);
            if (Boolean.TRUE.equals(vRetAporte.getCumple())) {
                if(causalTMP.length() == 0) {
                    causalTMP.append("Cumple por ");
                }
                causalTMP.append("aporte");
            } 
            if (Boolean.TRUE.equals(vRetNovedad.getCumple()) && Boolean.TRUE.equals(mostrarNovRei)) {
                if(causalTMP.length() == 0) {
                    causalTMP.append("Cumple por ");
                } else {
                    causalTMP.append(", ");
                }
                causalTMP.append("novedad");
            }
            if (Boolean.TRUE.equals(vRetReingreso.getCumple()) && Boolean.TRUE.equals(mostrarNovRei)) {
                if(causalTMP.length() == 0) {
                    causalTMP.append("Cumple por ");
                } else {
                    causalTMP.append(", ");
                }
                causalTMP.append("reingreso");
            }
            
            if (Boolean.FALSE.equals(vRetReingreso.getCumple())) {
                if(causalTMP.length() > 0) {
                    vRetReingreso.setRazonCausal(causalTMP.toString());
                } else {
                    vRetReingreso.setRazonCausal(obj[iResultadoCausal] == null ? null : obj[iResultadoCausal].toString());
                }
            } 
            if (Boolean.FALSE.equals(vRetAporte.getCumple())) {
                if(causalTMP.length() > 0) {
                    vRetAporte.setRazonCausal(causalTMP.toString());
                } else {
                    vRetAporte.setRazonCausal(obj[iResultadoCausal] == null ? null : obj[iResultadoCausal].toString());
                }
            } 
            if (Boolean.FALSE.equals(vRetNovedad.getCumple())) {
                if(causalTMP.length() > 0) {
                    vRetNovedad.setRazonCausal(causalTMP.toString());
                } else {
                    vRetNovedad.setRazonCausal(obj[iResultadoCausal] == null ? null : obj[iResultadoCausal].toString());
                }
            }
            
            if (Boolean.TRUE.equals(vRetAporte.getCumple())) {
                validaciones.add(vRetAporte);
            }
            if(Boolean.TRUE.equals(mostrarNovRei)) {
                if (Boolean.TRUE.equals(vRetNovedad.getCumple())) {
                    validaciones.add(vRetNovedad);
                }
                if (Boolean.TRUE.equals(vRetReingreso.getCumple())) {
                    validaciones.add(vRetReingreso);
                }
            }
            
            // Mantis 268189
            if(numeroValidaciones == validaciones.size() && Boolean.FALSE.equals(cumple)) {
                if(Boolean.TRUE.equals(obj[iEnColaProcesoPorAporte]) && vRetNovedad.getRazonCausal() != null){
                    validaciones.add(vRetAporte);
                }else if(Boolean.TRUE.equals(obj[iEnColaProcesoPorNovedad]) && vRetNovedad.getRazonCausal() != null){
                    validaciones.add(vRetNovedad);
                }else if (Boolean.TRUE.equals(obj[iPresentaReingreso]) && vRetReingreso.getRazonCausal() != null){
                    validaciones.add(vRetReingreso);
                }
            }
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo); 
    }
        
    /**
     * Metodo que se encarga de inicializar los datos de las validaciones por
     * sus valores por defecto
     * 
     * @param validacion
     * @param tipoPeriodo
     * @param periodo
     * @param segmentoTrabajador
     * @return
     */
    private ConsultaLiquidacionSubsidioMonetarioDTO inicializarDatosValidaciones(
            ConsultaLiquidacionSubsidioMonetarioDTO validacion, TipoPeriodoEnum tipoPeriodo,
            Date periodo, Boolean segmentoTrabajador) {
        
        String firmaMetodo = "SubsidioMonetarioBusiness.inicializarDatosValidaciones(ConsultaLiquidacionSubsidioMonetarioDTO, TipoIdentificacionEnum, Date, Boolean)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        validacion.setTipoPeriodo(tipoPeriodo);
        validacion.setPeriodo(periodo);
        validacion.setCumple(Boolean.FALSE);
        if(Boolean.TRUE.equals(segmentoTrabajador)) {
            validacion.setMontoLiquidado(BigDecimal.ZERO);
            validacion.setMontoDescontado(BigDecimal.ZERO);
            validacion.setMontoParaPago(BigDecimal.ZERO);
            validacion.setMontoRetirado(BigDecimal.ZERO);
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return validacion;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarPeriodoFallecimientoBenAfiliadoNoCumpleValidaciones(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Date consultarPeriodoFallecimientoBenAfiliadoNoCumpleValidaciones(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarPeriodoFallecimientoBenAfiliadoNoCumpleValidaciones(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {
            LocalDate fecha = LocalDate
                    .parse(entityManagerSubsidio
                            .createNamedQuery(
                                    NamedQueriesConstants.CONSULTAR_PERIODO_FALLECIMIENTO_BENEFICIARIO_AFILIADO_NO_CUMPLE_VALIDACIONES)
                            .setParameter("numeroRadicacion", numeroRadicacion).getSingleResult().toString())
                    .withDayOfMonth(1);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return CalendarUtils.darFormatoYYYYMMDDGuionDate(fecha.toString());
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarUltimaFechaCorteAportes()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Date consultarUltimaFechaCorteAportes() {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarUltimaFechaCorteAportes()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        //try {
            Date fecha = (Date) entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_FECHA_ULTIMA_ACTUALIZACION_STAGIN)
                    .getSingleResult();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return fecha;
       /* } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        */
    }

    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarGrupoFamiliarLiquidacionesPorTrabajador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.String, java.util.Date)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DetalleResultadoPorAdministradorDTO> consultarGrupoFamiliarLiquidacionesPorTrabajador(
            TipoIdentificacionEnum tipoIdentificacion, 
            String numeroIdentificacion, 
            TipoIdentificacionEnum tipoIdentificacionEmp, 
            String numeroIdentificacionEmp,
            String numeroRadicacion, 
            Date periodo,
            Map<Long, String> sitiosPago) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarGrupoFamiliarLiquidacionesPorTrabajador(TipoIdentificacionEnum, String, String, Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        final int iParentesco = 0;
        final int iBenRazonSocial = 1;
        final int iBenTipoIdentificacion = 2;
        final int iBenNumeroIdentificacion = 3;
        final int iPeriodo = 4;
        final int iEstadoRespectoEmpleador = 5; 
        final int iCumple = 6;
        final int iRazon = 7;
        final int iTipoCuotaSubsidio = 8;
        final int iEstadoDerecho = 9;
        final int iValorCuota = 10;
        final int iValorDescuento = 11;
        final int iValorCuotaAjustada = 12;
        final int iRecibeCuotaXSegundoAfi = 13;
        final int iRazonSocialSegundoAfi = 14;
        final int iGrupoFamiliar = 15;
        final int iAdmRazonSocial = 16;
        final int iTipoMedioDePago = 17;
        final int iFechaInicioLiquidacion = 18;
        final int iSitioPago = 19;
        final int iRvlId = 20;
        final int iAdmTipoIdentificacion = 21;
        final int iAdmNumeroIdentificacion = 22;
        final int iCondicionBeneficiario = 23;
        final int iSapActivoRespectoSap = 24;
        final int iConyugeCuidador = 25;
        final int iValorDescuentoPorEntidad = 26;
        
        if(sitiosPago == null) {
            sitiosPago = new HashMap<>();
        }
        
        List<Object[]> resultado = entityManagerSubsidio
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_VISTA_360_GRUPO_FAMILIAR_LIQUIDACION_TRABAJADOR)
                .setParameter("tipoIdentificacion", (tipoIdentificacion == null)? tipoIdentificacion : tipoIdentificacion.toString())
                .setParameter("numeroIdentificacion", numeroIdentificacion)
                .setParameter("tipoIdentificacionEmp", (tipoIdentificacionEmp == null)? tipoIdentificacionEmp : tipoIdentificacionEmp.toString())
                .setParameter("numeroIdentificacionEmp", numeroIdentificacionEmp)
                .setParameter("numeroRadicado", numeroRadicacion)
                .setParameter("periodo", periodo).getResultList();
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        
        List<DetalleResultadoPorAdministradorDTO> gruposFamiliares = new ArrayList<>();
        DetalleResultadoPorAdministradorDTO grupo = null;
        ItemBeneficiarioPorAdministradorDTO beneficiario;
        Long codigoGrupoFamiliar = -1L;
        Long cod;
        Long recibeXSegundoAfiliado;
        Long conyugeCuidador;
        Long idSitioPago;
        for (Object[] obj : resultado) {
            beneficiario = new ItemBeneficiarioPorAdministradorDTO();
            cod = Long.parseLong(obj[iGrupoFamiliar] == null ? "0" : obj[iGrupoFamiliar].toString());
            if (!codigoGrupoFamiliar.equals(cod)) {
                codigoGrupoFamiliar = cod;
                grupo = new DetalleResultadoPorAdministradorDTO();
                grupo.setBeneficiariosPorAdministrador(new ArrayList<>());
                grupo.setTotalDerecho(BigDecimal.ZERO);
                grupo.setTotalDescuentos(BigDecimal.ZERO);
                grupo.setTotalPago(BigDecimal.ZERO);
                grupo.setTotalRetirado(BigDecimal.ZERO);
                grupo.setTotalDescuentosPorEntidad(BigDecimal.ZERO);
                gruposFamiliares.add(grupo);
            }
            
            if(obj[iParentesco] != null) {
                beneficiario.setTipoBeneficiario(TipoBeneficiarioEnum.valueOf(obj[iParentesco].toString()));
            }
            beneficiario.setNombre(obj[iBenRazonSocial] == null ? null : obj[iBenRazonSocial].toString());
            if(obj[iBenTipoIdentificacion] != null) {
                beneficiario.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(obj[iBenTipoIdentificacion].toString()));
            }
            beneficiario.setNumeroIdentificacion(obj[iBenNumeroIdentificacion] == null ? null : obj[iBenNumeroIdentificacion].toString());
            beneficiario.setNombre(obj[iBenRazonSocial] == null ? null : obj[iBenRazonSocial].toString());
            beneficiario.setCumple(TipoCumplimientoEnum.NO_CUMPLE);
            if(Boolean.TRUE.equals(obj[iCumple])) {
                beneficiario.setCumple(TipoCumplimientoEnum.CUMPLE);
            }
            if(obj[iEstadoDerecho] != null) {
                beneficiario.setEstadoDerechoSubsidio(EstadoDerechoSubsidioEnum.valueOf(obj[iEstadoDerecho].toString()));
            }
            
            if(obj[iEstadoRespectoEmpleador] != null) {
                beneficiario.setEstadoRespectoAfiliado(EstadoAfiliadoEnum.valueOf(obj[iEstadoRespectoEmpleador].toString()));
            }
            
            beneficiario.setRecibeXSegundoAfiliado(Boolean.FALSE);
            if(obj[iRecibeCuotaXSegundoAfi] != null) {
                recibeXSegundoAfiliado = Long.parseLong(obj[iRecibeCuotaXSegundoAfi] == null ? "0" : obj[iRecibeCuotaXSegundoAfi].toString());
                if(!recibeXSegundoAfiliado.equals(0L)) {
                    beneficiario.setRecibeXSegundoAfiliado(Boolean.TRUE);   
                }  
            }

            beneficiario.setConyugeCuidador(obj[iConyugeCuidador] == null || !obj[iConyugeCuidador].toString().equals("true")  ? Boolean.FALSE : Boolean.TRUE);
 
            if(obj[iSapActivoRespectoSap] != null) { 
                beneficiario.setNombreXSegundoAfiliado(obj[iRazonSocialSegundoAfi] == null ? null : obj[iRazonSocialSegundoAfi].toString());
            }
            beneficiario.setTotalDerecho(BigDecimal.valueOf(Double.parseDouble(obj[iValorCuota] == null ? "0": obj[iValorCuota].toString())));
            beneficiario.setTotalDescuentos(BigDecimal.valueOf(Double.parseDouble(obj[iValorDescuento] == null ? "0": obj[iValorDescuento].toString())));
            beneficiario.setTotalPago(BigDecimal.valueOf(Double.parseDouble(obj[iValorCuotaAjustada] == null ? "0": obj[iValorCuotaAjustada].toString())));
            beneficiario.setTotalDescuentosPorEntidad(BigDecimal.valueOf(Double.parseDouble(obj[iValorDescuentoPorEntidad] == null ? "0": obj[iValorDescuentoPorEntidad].toString())));

            if(obj[iTipoCuotaSubsidio] != null) {
                beneficiario.setTipoCuotaSubsidio(TipoCuotaSubsidioEnum.valueOf(obj[iTipoCuotaSubsidio].toString()));
            }
            beneficiario.setCausal(obj[iRazon] == null ? null : obj[iRazon].toString());
            
            beneficiario.setIdResultadoValidacionLiquidacion(Long.parseLong(obj[iRvlId] == null ? "0" : obj[iRvlId].toString()));
            
            beneficiario.setIdCondicionBeneficiario(Long.parseLong(obj[iCondicionBeneficiario] == null ? "0" : obj[iCondicionBeneficiario].toString()));

            
            grupo.getBeneficiariosPorAdministrador().add(beneficiario);
            grupo.setNombre(obj[iAdmRazonSocial] == null ? null : obj[iAdmRazonSocial].toString());
            if(obj[iAdmTipoIdentificacion] != null) {
                grupo.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(obj[iAdmTipoIdentificacion].toString()));
            }
            grupo.setNumeroIdentificacion(obj[iAdmNumeroIdentificacion] == null ? null : obj[iAdmNumeroIdentificacion].toString());
            grupo.setTotalDerecho(grupo.getTotalDerecho().add(beneficiario.getTotalDerecho()));
            grupo.setTotalDescuentos(grupo.getTotalDescuentos().add(beneficiario.getTotalDescuentos()));
            grupo.setTotalPago(grupo.getTotalPago().add(beneficiario.getTotalPago()));
            grupo.setTotalDescuentosPorEntidad(grupo.getTotalDescuentosPorEntidad().add(beneficiario.getTotalDescuentosPorEntidad()));
            if(obj[iTipoMedioDePago] != null) {
                grupo.setMedioPago(TipoMedioDePagoEnum.valueOf(obj[iTipoMedioDePago].toString()));
            }
            grupo.setFechaLiquidacion((Date)obj[iFechaInicioLiquidacion]);
            grupo.setPeriodo((Date)obj[iPeriodo]);
            idSitioPago = Long.parseLong(obj[iSitioPago] == null ? "0" : obj[iSitioPago].toString());
            if(sitiosPago.containsKey(idSitioPago)) {
                grupo.setSitioPago(sitiosPago.get(idSitioPago));
            }
        }
        
        return gruposFamiliares;
    }
    



    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarInfoLiquidacionFallecimientoVista360(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Object[] consultarInfoLiquidacionFallecimientoVista360(String numeroRadicado) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarInfoLiquidacionFallecimientoVista360(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {
            Object[] result = (Object[]) entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_VISTA_360_LIQUIDACION_FALLECIMIENTO_SUBSIDIO)
                    .setParameter("numeroRadicado", numeroRadicado).getSingleResult();
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return result;
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarBeneficiarioPadre(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Boolean consultarBeneficiarioPadre(Long idCondicionBeneficiario,String numeroRadicado) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarInfoLiquidacionFallecimientoVista360(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {
            Object[] result = (Object[]) entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CATEGORIA_PADRES_BENEFICIARIO)
                    .setParameter("numeroRadicado", numeroRadicado)
                    .setParameter("condicionBeneficiario", idCondicionBeneficiario).getSingleResult();
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return Boolean.TRUE;
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return Boolean.FALSE;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarInformacionComunicado137(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<Object[]> consultarInformacionComunicado137(String numeroRadicacion, Long causa) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarInformacionComunicado137(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<Object[]> resultado = new ArrayList<>();
        try {
            resultado = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFORMACION_COMUNICADO_137)
                    .setParameter("numeroRadicado", numeroRadicacion).setParameter("causa", causa).getResultList();
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return resultado;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarInformacionComunicado138(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<Object[]> consultarInformacionComunicado138(String numeroRadicacion, Long causa) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarInformacionComunicado138(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<Object[]> resultado = new ArrayList<>();
        try {
            resultado = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFORMACION_COMUNICADO_138)
                    .setParameter("numeroRadicado", numeroRadicacion).setParameter("causa", causa).getResultList();
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return resultado;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#exportarLiquidacionesPorEmpleadorNoDispersadas(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RegistroLiquidacionSubsidioDTO> exportarLiquidacionesPorEmpleadorNoDispersadas(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion, Date periodo, Date fechaInicio,  Date fechaFin, String numeroRadicacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.exportarLiquidacionesPorEmpleadorNoDispersadas(TipoIdentificacionEnum,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<Object[]> resultado = new ArrayList<>();
        try {
            resultado = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_INFORMACION_EMPLEADOR_VISTA360_EXPORTAR_SUBSIDIO)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("periodo", periodo)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .setParameter("numeroRadicacion", numeroRadicacion)
                    .getResultList();
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        List<RegistroLiquidacionSubsidioDTO> respuesta = new ArrayList<>();
        if (!resultado.isEmpty()) {
            for (Object[] objeto : resultado) {
                RegistroLiquidacionSubsidioDTO registro = new RegistroLiquidacionSubsidioDTO();
                registro.setNumeroRadicado(objeto[0].toString());
                registro.setFechaLiquidacion((Date)objeto[1]);
                registro.setTipoIdentificacionTrabajador(objeto[2].toString());
                registro.setNumeroIdentificacionTrabajador(objeto[3].toString());
                registro.setNombreTrabajador(objeto[4].toString());
                registro.setTipoIdentificacionBeneficiario(objeto[5].toString());
                registro.setNumeroIdentificacionBeneficiario(objeto[6].toString());
                registro.setNombreBeneficiario(objeto[7].toString());
                registro.setTipoIdentificacionAdministrador(objeto[8].toString());
                registro.setNumeroIdentificacionAdministrador(objeto[9].toString());
                registro.setNombreAdministrador(objeto[10].toString());
                registro.setValorCuota(BigDecimal.valueOf(Double.valueOf(objeto[11].toString())));
                registro.setDescuento(BigDecimal.valueOf(Double.valueOf(objeto[12].toString())));
                registro.setValorPagar(BigDecimal.valueOf(Double.valueOf(objeto[13].toString())));
                registro.setMedioDePago(objeto[14].toString());
                
                respuesta.add(registro);
            }
        }
        return respuesta.isEmpty()?null:respuesta;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consolidarSubsidiosFallecimiento(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void consolidarSubsidiosFallecimiento(String numeroRadicado, ModoDesembolsoEnum modoDesembolso) {
        String firmaMetodo = "ConsultasModeloSubsidio.consolidarSubsidiosFallecimiento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean formaPago = ModoDesembolsoEnum.MES_POR_MES.equals(modoDesembolso) ? Boolean.FALSE : Boolean.TRUE;
        
        try {
            StoredProcedureQuery procedimiento = entityManagerSubsidio
                    .createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_SM_UTIL_CONSOLIDARSUBSIDIOS)
                    .setParameter("sNumeroRadicado", numeroRadicado)
                    .setParameter("bFormaPago", formaPago);

            procedimiento.execute();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#obtenerPrimeraValidacionSubsidio(java.lang.String, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public GrupoAplicacionValidacionSubsidioEnum obtenerPrimeraValidacionSubsidio(String numeroRadicacion, Long idCondicionPersona,
            TipoLiquidacionEspecificaEnum tipoLiquidacion, Boolean cumple, Boolean esTrabajadorFallecido) {
        String firmaMetodo = "ConsultasModeloSubsidio.obtenerPrimeraValidacionSubsidio(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Object[]> resultado = new ArrayList<>();
        GrupoAplicacionValidacionSubsidioEnum validacion = null;

        try {
            resultado = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_VALIDACIONES_SUBSIDIO_POR_RADICADO_IDCONDICIONPERSONA)
                    .setParameter("numeroRadicacion", numeroRadicacion).setParameter("idCondicionPer", idCondicionPersona).getResultList();
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        //if (!resultado.isEmpty() &&(cumple && TipoLiquidacionEspecificaEnum.DEFUNCION_TRABAJADOR_DEPENDIENTE.equals(tipoLiquidacion) && esTrabajadorFallecido) ||
        //    (cumple && TipoLiquidacionEspecificaEnum.DEFUNCION_BENEFICIARIO.equals(tipoLiquidacion)&& !esTrabajadorFallecido)) {
        if (!resultado.isEmpty() &&(cumple && TipoLiquidacionEspecificaEnum.DEFUNCION_TRABAJADOR_DEPENDIENTE.equals(tipoLiquidacion)) ||
               (cumple && TipoLiquidacionEspecificaEnum.DEFUNCION_BENEFICIARIO.equals(tipoLiquidacion))) {
            logger.debug("obtenerPrimeraValidacionSubsidio-1");
            //si la liquidación es por trabajador y cumple ó la liquidación es por beneficiario y cumple

            //se obtiene la primera validación
            validacion = GrupoAplicacionValidacionSubsidioEnum.valueOf(resultado.get(0)[1].toString());
            //se obtienen los id de las validaciones a diferencia de la primera del trabajador para ser eliminadas
            List<Long> lstIdValidaciones = null;
            try {
                lstIdValidaciones = entityManagerSubsidio
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_VALIDACIONES_SUBSIDIO_TRABAJADOR_CUMPLE)
                        .setParameter("validacion", validacion.name()).setParameter("numeroRadicacion", numeroRadicacion).getResultList();
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            } catch (Exception e) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
            }
            //se eliminan las validaciones relacionadas al numero de radicación con excepción de la primera
            if(lstIdValidaciones != null && !lstIdValidaciones.isEmpty())
                eliminarValidacionesGestionadas(lstIdValidaciones);

            //se obtienen los id de las validaciones a diferencia de la primera del trabajador para ser eliminadas
            List<Long> lstIdRegistros = null;
            try {
                lstIdRegistros = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTROS_TRABAJADOR_CUMPLE)
                        .setParameter("numeroRadicacion", numeroRadicacion).getResultList();
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            } catch (Exception e) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
            }
           
            if (!lstIdRegistros.isEmpty()){
                 //eliminar registro de la tabla GestionSubsidioFallecimiento
                eliminarRegistrosGestionSubsidioFallecimiento(lstIdRegistros);
                //eliminar proyeccionCuotaFallecimiento de los registros
                eliminarRegistrosValidacionesEnProyeccionCuotaFallecimiento(lstIdRegistros);
                //dejar el primer registro en la tabla ResultadoValidacionLiquidacion (se borran los demas)
                eliminarRegistroValidacionLiquidacionTrabajadorCumple(numeroRadicacion, lstIdRegistros);
                
            }
              
            //primero se elimina la liquidación
            eliminarLiquidacionSP(numeroRadicacion, Boolean.FALSE);
            //se genera nuevamente liquidación con los datos del trabajador, si es trabajdor dependiente se envia un FALSE al Sp
            ejecutarSPLiquidacionFallecimiento(numeroRadicacion, new Date().getTime(), TipoLiquidacionEspecificaEnum.DEFUNCION_BENEFICIARIO.equals(tipoLiquidacion)?Boolean.TRUE:Boolean.FALSE);

        }
        else if (cumple && !esTrabajadorFallecido && !resultado.isEmpty()) { //si el beneficiario de la liquidación del trabajador cumple
            logger.debug("obtenerPrimeraValidacionSubsidio-2");
            validacion = GrupoAplicacionValidacionSubsidioEnum.valueOf(resultado.get(0)[1].toString());

            Long idRegistro = null;
            try {
                idRegistro = Long
                        .valueOf(
                                entityManagerSubsidio
                                        .createNamedQuery(
                                                NamedQueriesConstants.CONSULTAR_ID_RESULTADO_VALIDACION_LIQUIDACION_ID_CONDICION_BEN)
                                        .setParameter("numeroRadicacion", numeroRadicacion)
                                        .setParameter("idCondicionPer", idCondicionPersona).getSingleResult().toString());
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            } catch (Exception e) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
            }

            //actualizar la condicion beneficiario de ese registro, SE ENVIA EN EL NUMERO DE RADICACION EL ID DE REGISTRO
            actualizarRegistroResultadoValidacionTrabajadorCumple(idRegistro.toString(), validacion.getDescripcion());

        }
        else if (!resultado.isEmpty() && resultado.size() != 1) {
            logger.debug("obtenerPrimeraValidacionSubsidio-3");
            //se obtiene la primera validación
            validacion = GrupoAplicacionValidacionSubsidioEnum.valueOf(resultado.get(0)[1].toString());
            //se elimina el primer elementon
            resultado.remove(0);
            //se obtiene los id de las demas validaciones para ser eliminadas
            List<Long> lstIdValidaciones = resultado.stream().map(v -> Long.valueOf(v[0].toString())).collect(Collectors.toList());

            if (!lstIdValidaciones.isEmpty()) {
                logger.debug("obtenerPrimeraValidacionSubsidio-4");
                //se actualiza el mensaje del resgitro de la tabla ResultadoValidacionLiquidacion con la primera validación que tenia previamente
                entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.UPDATE_REGISTRO_RESULTADOVALIDACIONLIQ_PRIMERA_VALIDACION)
                        //.setParameter("tipoLiquidacion", tipoLiquidacion.name())
                        .setParameter("numeroRadicacion", numeroRadicacion).setParameter("idCondicionPer", idCondicionPersona)
                        .setParameter("mensaje", validacion.getDescripcion()).executeUpdate();

                //se eliminan las validaciones gestionadas
                eliminarValidacionesGestionadas(lstIdValidaciones);

            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return validacion;
    }

    /**
     * Metodo que permite eliminar las validaciones gestionadas para una pesona
     * @param lstIdValidaciones
     *        lista de ids de las validaciones gestionadas
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void eliminarValidacionesGestionadas(List<Long> lstIdValidaciones){
        entityManagerSubsidio
        .createNamedQuery(
                NamedQueriesConstants.ELIMINAR_REGISTROS_VALIDACIONES_SUBSIDIO)
        .setParameter("lstIdValidaciones", lstIdValidaciones).executeUpdate();
    }
    
    /**
     * Metodo que elimina los registros de la tabla RegistroValidacionLiquidacion
     * que pertecen a los beneficiarios y a un trabajador, con excepción del primer
     * registro
     * 
     * @param numeroRadicado
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void eliminarRegistroValidacionLiquidacionTrabajadorCumple(String numeroRadicado,List<Long>lstIdRegistros){
        entityManagerSubsidio
        .createNamedQuery(
                NamedQueriesConstants.ELIMINAR_REGISTRO_VALIDACION_TRABAJADOR_CUMPLE)
        .setParameter("lstIdRegistros", lstIdRegistros)
        .setParameter("numeroRadicacion", numeroRadicado).executeUpdate();
    }
    
    /**
     * Metodo que elimina los registros de la tabla GestionSubsidioFallecimiento
     * relacionados con los registros de la tabla ResultadoValidacionLiquidacion
     * 
     * @param lstIdRegistros
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void eliminarRegistrosGestionSubsidioFallecimiento(List<Long> lstIdRegistros) {
        entityManagerSubsidio
        .createNamedQuery(
                NamedQueriesConstants.ELIMINAR_REGISTRO_VALIDACION_GESTION_SUBSIDIO_FALLECIMIENTO)
        .setParameter("lstIdRegistros", lstIdRegistros).executeUpdate();
    }
    
    /**
     * 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void actualizarRegistroResultadoValidacionTrabajadorCumple(String numeroRadicacion, String mensaje) {
            entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.UPDATE_REGISTRO_RESULTADO_VALIDACION_LIQ_BENEFICIARIO_CUMPLE)
                    .setParameter("numeroRadicacion", Long.valueOf(numeroRadicacion)).setParameter("mensaje", mensaje).executeUpdate();
    }
    
    /**
     * @param lstIdRegistros
     */
    private void eliminarRegistrosValidacionesEnProyeccionCuotaFallecimiento(List<Long> lstIdRegistros) {
        entityManagerSubsidio
        .createNamedQuery(
                NamedQueriesConstants.ELIMINAR_REGISTROS_VALIDACIONES_RELACIONADOS_PROYECCION_CUOTA_FALLECIMIENTO)
        .setParameter("lstIdRegistros", lstIdRegistros).executeUpdate();
        
    }
    
    /**
     * Metodo que consulta que el beneficiario tenga derecho asignado para el mismo periodo
     */
    @Override
    public Integer consultarBeneficiarioConDerechoAsignadoEnMismoPeriodo(String numeroRadicado) {
        Integer cantBenfeciarios = (Integer) entityManagerSubsidio
        .createNamedQuery(
                NamedQueriesConstants.CONSULTAR_BENEFICIARIO_DERECHO_ASIGNADO)
        .setParameter("numeroRadicado", numeroRadicado).getSingleResult();
        return cantBenfeciarios;
    }

    @Override
    public void insertarDatosSubsidioBloqueoAfiliadoBeneficiarioCM(BloqueoAfiliadoBeneficiarioCM bloqueoAfiliadoBeneficiarioCM) {
        logger.info("DEMO BLOQUEO AFILIADO BENEFICIARIO");
        logger.info("tipoIdentificacionAfiliado: " + bloqueoAfiliadoBeneficiarioCM.getTipoIdentificacionAfiliado());
        logger.info("numeroIdentificacionAfiliado: " + bloqueoAfiliadoBeneficiarioCM.getNumeroIdentificacionAfiliado());
        logger.info("personaAfiliado: " + bloqueoAfiliadoBeneficiarioCM.getPersonaAfiliado());
        logger.info("tipoIdentificacionBeneficiario: " + bloqueoAfiliadoBeneficiarioCM.getTipoIdentificacionBeneficiario());
        logger.info("numeroIdentificacionBeneficiario: " + bloqueoAfiliadoBeneficiarioCM.getNumeroIdentificacionBeneficiario());
        logger.info("personaBeneficiario: " + bloqueoAfiliadoBeneficiarioCM.getPersonaBeneficiario());
        logger.info("motivoNoAcreditado: " + bloqueoAfiliadoBeneficiarioCM.getMotivoNoAcreditado());
        logger.info("motivoFraude: " + bloqueoAfiliadoBeneficiarioCM.getMotivoFraude());
        logger.info("motivoOtro: " + bloqueoAfiliadoBeneficiarioCM.getMotivoOtro());
        logger.info("bloqueado: " + bloqueoAfiliadoBeneficiarioCM.getBloqueado());
        logger.info("periodoInicio: " + bloqueoAfiliadoBeneficiarioCM.getPeriodoInicio());
        logger.info("periodoFin: " + bloqueoAfiliadoBeneficiarioCM.getPeriodoFin());
        logger.info("bbcId: "+ bloqueoAfiliadoBeneficiarioCM.getBbcId());
        logger.info("causalBloqueo: " + bloqueoAfiliadoBeneficiarioCM.getCausalBloqueo());
    
        entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.INSERTAR_BLOQUEO_AFILIADO_RELACION_BENEFICIARIO)
            .setParameter("tipoIdentificacionAfiliado", bloqueoAfiliadoBeneficiarioCM.getTipoIdentificacionAfiliado().toString())
            .setParameter("numeroIdentificacionAfiliado", bloqueoAfiliadoBeneficiarioCM.getNumeroIdentificacionAfiliado().toString())
            .setParameter("personaAfiliado", bloqueoAfiliadoBeneficiarioCM.getPersonaAfiliado().toString())
            .setParameter("tipoIdentificacionBeneficiario", bloqueoAfiliadoBeneficiarioCM.getTipoIdentificacionBeneficiario().toString())
            .setParameter("numeroIdentificacionBeneficiario", bloqueoAfiliadoBeneficiarioCM.getNumeroIdentificacionBeneficiario().toString())
            .setParameter("personaBeneficiario", bloqueoAfiliadoBeneficiarioCM.getPersonaBeneficiario().toString())
            .setParameter("motivoNoAcreditado", bloqueoAfiliadoBeneficiarioCM.getMotivoNoAcreditado().toString())
            .setParameter("motivoFraude", bloqueoAfiliadoBeneficiarioCM.getMotivoFraude().toString())
            .setParameter("motivoOtro", bloqueoAfiliadoBeneficiarioCM.getMotivoOtro().toString())
            .setParameter("bloqueado", bloqueoAfiliadoBeneficiarioCM.getBloqueado().toString())
            .setParameter("periodoInicio", bloqueoAfiliadoBeneficiarioCM.getPeriodoInicio())
            .setParameter("periodoFin", bloqueoAfiliadoBeneficiarioCM.getPeriodoFin())
            .setParameter("bbcId", bloqueoAfiliadoBeneficiarioCM.getBbcId())
            .setParameter("causalBloqueo", bloqueoAfiliadoBeneficiarioCM.getCausalBloqueo().toString())
            .executeUpdate();
    }

    /**
     * Metodo que actualiza el estado de derecho a DERECHO_NO_APROBADO
     */
    @Override
    public void actualizarResultadoValidacionLiquidacionDerechoNoAsignado(String numeroRadicado, String mensaje) {
        entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ESTADO_DERECHO_RADICADO)
                .setParameter("numeroRadicado", numeroRadicado)
               // .setParameter("mensaje", mensaje)
                .executeUpdate();
    }
    
    public Boolean validarEnProcesoStaging(){
        Integer procesoEjecutado = (Integer) entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_PROCESO_ACTUALIZACION_STAGING)
            .getSingleResult();
        return procesoEjecutado>0?true:false;
    }

    /**
     * Método encargado de verificar si hay personas afiliadas sin condiciones en el staging
     */
    @Override
    public RespuestaVerificarPersonasSinCondicionesDTO consultarPersonasAfiliadosSinCondiciones(List<Long> periodos, List<Integer> idPersonas) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarPersonasAfiliadosSinCondiciones(Long perido, List<Integer> idPersonas)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        RespuestaVerificarPersonasSinCondicionesDTO resultVerificacion = new RespuestaVerificarPersonasSinCondicionesDTO();
        List<Date> periodosList = new ArrayList<Date>();
        List<Long> periodosSinCondiciones = new ArrayList<Long>();
        List<BigInteger> personasSinCondiciones = new ArrayList<BigInteger>();
        List<Integer> personasSinCondToInt = new ArrayList<Integer>();
        
        for (Long periodo : periodos) {
            periodosList.add(new Date(periodo));
        }
        
        try{
            // cuando la peticion viene desde seleccionar persona
            if ((idPersonas.size() == 1 && periodos.size() > 1) || (idPersonas.size() > 1 && periodos.size() > 1)) {
                for (Date periodo : periodosList) {
                    personasSinCondiciones = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.VERIFICAR_CONDICION_PERSONA_AFILIADO).
                                        setParameter("listaIdAfiliados", idPersonas ).
                                        setParameter("periodo", periodo).getResultList();
                    
                    if (!personasSinCondiciones.isEmpty()) {
                        periodosSinCondiciones.add(periodo.getTime());
                    }
                    
                }
                
                if (!periodosSinCondiciones.isEmpty()) {
                    resultVerificacion.setPeriodosSinCondiciones(periodosSinCondiciones);
                    resultVerificacion.setSinCondiciones(Boolean.TRUE);
                } else {
                    resultVerificacion.setSinCondiciones(Boolean.FALSE);
                }
                
              //Cuando la peticion viene desde periodo
            } else if (idPersonas.size() > 1 && periodos.size() == 1) {
                personasSinCondiciones =  entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.VERIFICAR_CONDICION_PERSONA_AFILIADO).
                        setParameter("listaIdAfiliados", idPersonas ).
                        setParameter("periodo", new Date(periodos.get(0))).getResultList();
   
                if (!personasSinCondiciones.isEmpty()) {
                    for (BigInteger persona : personasSinCondiciones) {
                        personasSinCondToInt.add(((Number) persona).intValue());
                    }
                    resultVerificacion.setPersonasSinCondiciones(personasSinCondToInt);
                    resultVerificacion.setSinCondiciones(Boolean.TRUE);
                } else {
                    resultVerificacion.setSinCondiciones(Boolean.FALSE);
                }
              // Cuando la peticion viene de cualquiera de los dos cuando hay una persona o un periodo
            } else if (idPersonas.size() == 1 && periodos.size() == 1) {
                
                for (Date periodo : periodosList) {
                    personasSinCondiciones = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.VERIFICAR_CONDICION_PERSONA_AFILIADO).
                                        setParameter("listaIdAfiliados", idPersonas ).
                                        setParameter("periodo", periodo).getResultList();
                    
                    if (!personasSinCondiciones.isEmpty()) {
                        periodosSinCondiciones.add(periodo.getTime());
                    }
                    
                }
                
                if (!periodosSinCondiciones.isEmpty()) {
                    resultVerificacion.setPeriodosSinCondiciones(periodosSinCondiciones);
                }
                
                personasSinCondiciones = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.VERIFICAR_CONDICION_PERSONA_AFILIADO).
                        setParameter("listaIdAfiliados", idPersonas ).
                        setParameter("periodo", new Date(periodos.get(0))).getResultList();
   
                if (!personasSinCondiciones.isEmpty()) {
                    for (BigInteger persona : personasSinCondiciones) {
                        personasSinCondToInt.add(((Number) persona).intValue());
                    }
                    resultVerificacion.setPersonasSinCondiciones(personasSinCondToInt);
                    resultVerificacion.setSinCondiciones(Boolean.TRUE);
                } else {
                    resultVerificacion.setSinCondiciones(Boolean.FALSE);
                }
            }
            
        } catch(Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultVerificacion;
    }

    /**
     * Método encargado de verificar si hay personas de un empleador sin condiciones en el staging
     */
    @Override
    public RespuestaVerificarPersonasSinCondicionesDTO consultarPersonasEmpleadoresSinCondiciones(List<Long> periodos, List<Integer> idEmpleadores) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarPersonasEmpleadoresSinCondiciones(Long periodo, List<Integer> idEmpleadores)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        RespuestaVerificarPersonasSinCondicionesDTO resultVerificacion = new RespuestaVerificarPersonasSinCondicionesDTO();
        List<Date> periodosList = new ArrayList<Date>();
        List<Long> periodosSinCondiciones = new ArrayList<Long>();
        List<BigInteger> empleadoresSinCondiciones = new ArrayList<BigInteger>();
        List<Integer> personasSinCondToInt = new ArrayList<Integer>();
        
        for (Long periodo : periodos) {
            periodosList.add(new Date(periodo));
        }
        
        try{
            // cuando la peticion viene desde seleccionar persona
            if ((idEmpleadores.size() == 1 && periodos.size() > 1) || (idEmpleadores.size() > 1 && periodos.size() > 1)) {
                for (Date periodo : periodosList) {
                    empleadoresSinCondiciones = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.VERIFICAR_CONDICION_PERSONA_EMPLEADOR).
                                        setParameter("listaIdEmpleadores", idEmpleadores ).
                                        setParameter("periodo", periodo).getResultList();
                    
                    if (!empleadoresSinCondiciones.isEmpty()) {
                        periodosSinCondiciones.add(periodo.getTime());
                    }
                    
                }
                
                if (!periodosSinCondiciones.isEmpty()) {
                    resultVerificacion.setPeriodosSinCondiciones(periodosSinCondiciones);
                    resultVerificacion.setSinCondiciones(Boolean.TRUE);
                } else {
                    resultVerificacion.setSinCondiciones(Boolean.FALSE);
                }
                
              //Cuando la peticion viene desde periodo
            } else if (idEmpleadores.size() > 1 && periodos.size() == 1) {
                empleadoresSinCondiciones =  entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.VERIFICAR_CONDICION_PERSONA_EMPLEADOR).
                        setParameter("listaIdEmpleadores", idEmpleadores ).
                        setParameter("periodo", new Date(periodos.get(0))).getResultList();
   
                if (!empleadoresSinCondiciones.isEmpty()) {
                    for (BigInteger persona : empleadoresSinCondiciones) {
                        personasSinCondToInt.add(((Number) persona).intValue());
                    }
                    resultVerificacion.setPersonasSinCondiciones(personasSinCondToInt);
                    resultVerificacion.setSinCondiciones(Boolean.TRUE);
                } else {
                    resultVerificacion.setSinCondiciones(Boolean.FALSE);
                }
              // Cuando la peticion viene de cualquiera de los dos cuando hay una persona o un periodo
            } else if (idEmpleadores.size() == 1 && periodos.size() == 1) {
                //valida los periodos
                for (Date periodo : periodosList) {
                    empleadoresSinCondiciones = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.VERIFICAR_CONDICION_PERSONA_EMPLEADOR).
                                        setParameter("listaIdEmpleadores", idEmpleadores ).
                                        setParameter("periodo", periodo).getResultList();
                    
                    if (!empleadoresSinCondiciones.isEmpty()) {
                        periodosSinCondiciones.add(periodo.getTime());
                    }
                    
                }
                
                if (!periodosSinCondiciones.isEmpty()) {
                    resultVerificacion.setPeriodosSinCondiciones(periodosSinCondiciones);
                }
                //valida las personas
                empleadoresSinCondiciones = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.VERIFICAR_CONDICION_PERSONA_EMPLEADOR).
                        setParameter("listaIdEmpleadores", idEmpleadores ).
                        setParameter("periodo", new Date(periodos.get(0))).getResultList();
   
                if (!empleadoresSinCondiciones.isEmpty()) {
                    for (BigInteger persona : empleadoresSinCondiciones) {
                        personasSinCondToInt.add(((Number) persona).intValue());
                    }
                    resultVerificacion.setPersonasSinCondiciones(personasSinCondToInt);
                    resultVerificacion.setSinCondiciones(Boolean.TRUE);
                } else {
                    resultVerificacion.setSinCondiciones(Boolean.FALSE);
                }
            }
            
        } catch(Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultVerificacion;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#ejecutarSPLiquidacionReconocmiento(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Boolean validarMarcaAprobacionSegNivel(String numeroRadicado) {
        String firmaMetodo = "ConsultasModeloSubsidio.validarMarcaAprobacionSegNivel(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Boolean resultado = new Boolean(false);

        StoredProcedureQuery storedProcedure = entityManagerSubsidio
                .createStoredProcedureQuery(NamedQueriesConstants.EJECUTAR_SP_USP_SM_GET_MARCAAPROBACIONSEGNIVEL);
        storedProcedure.registerStoredProcedureParameter("sNumeroRadicado", String.class, ParameterMode.IN);
        storedProcedure.setParameter("sNumeroRadicado", numeroRadicado);
        storedProcedure.registerStoredProcedureParameter("procesando", String.class, ParameterMode.OUT);        
        storedProcedure.execute();
        
        
        resultado = storedProcedure.getOutputParameterValue("procesando").toString().equals("0")?Boolean.FALSE:Boolean.TRUE;
        logger.debug("resultado:" + resultado);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#ejecutarSPLiquidacionReconocmiento(java.lang.String)
     */    
    @Override
    public void eliminarMarcaAprobacionSegNivel() {  
        String firmaMetodo = "ConsultasModeloSubsidio.validarMarcaAprobacionSegNivel(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);  
        
        entityManagerSubsidio
        .createNamedQuery(
                NamedQueriesConstants.ELIMINAR_MARCA_PROCESO_APROBACION_SEG_NIVEL).executeUpdate();     
  
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);

    }
    
    /**
     * Consulta que busca los beneficiarios bloqueados en la tabla de bloqueos para afiliado beneficiario
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> consultarBeneficiarioBloqueadosSubsidio() {
        
        //List<String[]> beneficiarioBloq = new ArrayList<>();
        
        List<Object[]> consultaBenBloq = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_PAR_AFILIADO_BENEFICIARIO_BLOQUEADOS)
                .getResultList();
        
        return consultaBenBloq;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#obtenerAniosCondicionesParametrizadosSubsidio(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int desbloquearBeneficiariosCMSubsidio(List<Long> idBeneficiarioBloqueados){ 
         String firmaMetodo = "ConsultasModeloCore.validarExistenciaBeneficiarios(Long)";        
         logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
         int filas = 0;
         try {
             
            for (Long idBeneficiarioBloqueo : idBeneficiarioBloqueados) {
                int fila = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.DESBLOQUEAR_PAR_AFILIADO_BENEFICIARIO)
                .setParameter("idBloqueoAfiliadoBeneficiarioCM", idBeneficiarioBloqueo)
                .executeUpdate();
                filas = filas + fila;
                
            }
        
             logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);             
         } catch (Exception e) {
             logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
             throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
         }
         return filas;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int consultarAfiliadoBeneficiarioCM(Long idBeneficiarioBloqueado){ 
         String firmaMetodo = "ConsultasModeloCore.consultarAfiliadoBeneficiarioCM(Long)";    
         logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
         Integer afiliadoBeneficiarioCM = 0;
         try{
            afiliadoBeneficiarioCM = ((Number) entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_TRABAJADOR_BLOQUEADO_SUBSIDIO)
                    .setParameter("bbcId", idBeneficiarioBloqueado)
                    .getSingleResult())
                    .intValue();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);         
         } catch (Exception e) {
             logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
             throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
         }
         return afiliadoBeneficiarioCM;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BloqueoBeneficiarioCuotaMonetariaDTO> consultarBeneficiariosBloqueadosSubsidioFiltros(
            ConsultaBeneficiarioBloqueadosDTO consulta) {
        String firmaServicio = "AfiliadosBusiness.consultarBeneficiariosBloqueados()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<BloqueoBeneficiarioCuotaMonetariaDTO> listaBeneficiarios = null;
        List<Object[]> lstParAfiBen = null;
        
        final String PORCENTAJE = "%";

        String parametroPrimerNombre = null;
        String parametroSegundoNombre = null;
        String parametroPrimerApellido = null;
        String parametroSegundoApellido = null;

        if (consulta.getPrimerNombre() != null) {
            parametroPrimerNombre = PORCENTAJE + consulta.getPrimerNombre() + PORCENTAJE;
        }
        if (consulta.getSegundoNombre() != null) {
            parametroSegundoNombre = PORCENTAJE + consulta.getSegundoNombre() + PORCENTAJE;
        }
        if (consulta.getPrimerApellido() != null) {
            parametroPrimerApellido = PORCENTAJE + consulta.getPrimerApellido() + PORCENTAJE;
        }
        if (consulta.getSegundoApellido() != null) {
            parametroSegundoApellido = PORCENTAJE + consulta.getSegundoApellido() + PORCENTAJE; 
        }           
        
        Date fechaNa = (consulta.getFechaNacimiento() == null) ? CalendarUtils.darFormatoYYYYMMDDGuionDate("1800-01-01"):consulta.getFechaNacimiento();
        
        lstParAfiBen = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_PAR_AFILIADOS_BENEFICIARIOS_BLOQUEADOS_POR_FILTROS)                     
                .setParameter("tipoIdentificacion",
                        consulta.getTipoIdentificacion() != null ? consulta.getTipoIdentificacion().toString() : null)
                .setParameter("numeroIdentificacion", consulta.getNumeroIdentificacion())
                .setParameter("primerNombre", parametroPrimerNombre)
                .setParameter("segundoNombre", parametroSegundoNombre)
                .setParameter("primerApellido", parametroPrimerApellido)
                .setParameter("segundoApellido", parametroSegundoApellido)
                .setParameter("fechaNacimiento", fechaNa)
                .getResultList();
        /*} catch (Exception e) {
            logger.error("Ocurrió un error inesperado en " + firmaServicio, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }*/
        
        

        if (lstParAfiBen.isEmpty()) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
            return listaBeneficiarios;
        }

        listaBeneficiarios = new ArrayList<>();
        
        for (int i = 0; i < lstParAfiBen.size(); i++) {
            BloqueoBeneficiarioCuotaMonetariaDTO bloqueo = new BloqueoBeneficiarioCuotaMonetariaDTO();
            bloqueo.setIdBloqueoAfiliadoBeneficiarioCM(Long.valueOf((lstParAfiBen.get(i)[0]).toString()));
            bloqueo.setPersonaBeneficiario(Long.valueOf((lstParAfiBen.get(i)[1]).toString()));
            bloqueo.setTipoIdentificacionBeneciario(TipoIdentificacionEnum.valueOf(lstParAfiBen.get(i)[2].toString()));             
            bloqueo.setNumeroIdentificacionBeneficiario(lstParAfiBen.get(i)[3].toString());             
            bloqueo.setRazonSocialBeneficiario(lstParAfiBen.get(i)[4]==null?"": lstParAfiBen.get(i)[4].toString());  
            bloqueo.setEstadoBeneficiario(lstParAfiBen.get(i)[5].toString());
            
            bloqueo.setPersonaAfiliado(Long.valueOf((lstParAfiBen.get(i)[6]).toString()));
            bloqueo.setTipoIdentificacionAfiliado(TipoIdentificacionEnum.valueOf(lstParAfiBen.get(i)[7].toString()));
            bloqueo.setNumeroIdentificacionAfiliado(lstParAfiBen.get(i)[8].toString());
            bloqueo.setBloqueoParMotivoFraude(lstParAfiBen.get(i)[9]==null?null:((Boolean)lstParAfiBen.get(i)[9]));
            bloqueo.setBloqueoParMotivoNoAcreditado(lstParAfiBen.get(i)[10]==null?null:((Boolean)lstParAfiBen.get(i)[10]));
            bloqueo.setBloqueoParMotivoOtro(lstParAfiBen.get(i)[11]==null?null:((Boolean)lstParAfiBen.get(i)[11]));
            bloqueo.setPeriodoInicio((Date)lstParAfiBen.get(i)[12]);
            bloqueo.setPeriodoFin((Date)lstParAfiBen.get(i)[13]);
            bloqueo.setRelacionAfiBen(lstParAfiBen.get(i)[14].toString());
            listaBeneficiarios.add(bloqueo);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaBeneficiarios.isEmpty() ? null : listaBeneficiarios;
    }
    
    @Override
    public Boolean consultarExistenciaBeneficiariosBloqueadosSubsidio() { 

        String firmaServicio = "AfiliadosBusiness.consultarBeneficiariosBloqueados()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Object resultado = new Object();
        
        try {
            resultado = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_EXISTENCIA_BENEFICIARIOS_BLOQUEADOS_SUBSIDIO)                     
                    .getSingleResult();
        } catch (NoResultException nre) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
            return Boolean.FALSE;
        } catch (NonUniqueResultException nur) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return Boolean.TRUE;
        }
        

        if (resultado != null) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
            return Boolean.TRUE;
        } else {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
            return Boolean.FALSE;
        }
        
    }

    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoProyeccionCuotas(
            String numeroRadicacion, Long identificadorCondicion) {
        String firmaServicio = "ConsultasModeloSubsidio.consultarDispersionMontoLiquidadoFallecimientoProyeccionCuotas(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        try {
            List<Object[]> registrosDetalle = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_POR_NUMERO_RADICADO_PROYECCION_SUBSIDIO)
                    .setParameter("numeroRadicacion", numeroRadicacion)
                    .setParameter("identificadorCondicion", identificadorCondicion).getResultList();

            DispersionResultadoMedioPagoFallecimientoDTO detalleEfectivoDTO = new DispersionResultadoMedioPagoFallecimientoDTO();
            if (!registrosDetalle.isEmpty()) {
                detalleEfectivoDTO.setIdentificadorCondicionAdministrador(Long.parseLong(registrosDetalle.get(0)[1].toString()));
                detalleEfectivoDTO
                        .setTipoIdentificacionAdministrador(TipoIdentificacionEnum.valueOf(registrosDetalle.get(0)[2].toString()));
                detalleEfectivoDTO.setNumeroIdentificacionAdministrador(registrosDetalle.get(0)[3].toString());
                detalleEfectivoDTO.setNombreAdministrador(registrosDetalle.get(0)[4].toString());
                detalleEfectivoDTO.setBanco(registrosDetalle.get(0)[5] != null ? registrosDetalle.get(0)[5].toString() : null);
                detalleEfectivoDTO.setTipoCuenta(registrosDetalle.get(0)[6] != null ? TipoCuentaEnum.valueOf(registrosDetalle.get(0)[6].toString()) : null);
                detalleEfectivoDTO.setNumeroCuenta(registrosDetalle.get(0)[7] != null ? registrosDetalle.get(0)[7].toString() : null);
                detalleEfectivoDTO.setTitularCuenta(registrosDetalle.get(0)[8] != null ? registrosDetalle.get(0)[8].toString() : null);
                detalleEfectivoDTO.setNumeroTarjeta(registrosDetalle.get(0)[14]  != null ? registrosDetalle.get(0)[14].toString() : null);
                detalleEfectivoDTO.setEstadoTarjeta(registrosDetalle.get(0)[15] != null ? EstadoTarjetaMultiserviciosEnum.valueOf(registrosDetalle.get(0)[15].toString()) : null);
                
                List<Long> identificadoresCondiciones = new ArrayList<>();
                identificadoresCondiciones.add(detalleEfectivoDTO.getIdentificadorCondicionAdministrador());

                List<ItemDispersionResultadoMedioPagoFallecimientoDTO> itemsBeneficiarios = new ArrayList<>();
                Map<KeyPersonaDTO, Integer> beneficiariosProcesados = new HashMap<>();
                Integer indicadorProcesamiento = 0;

                for (Object[] registroDetalle : registrosDetalle) {

                    if (!beneficiariosProcesados.containsKey(new KeyPersonaDTO(
                            TipoIdentificacionEnum.valueOf(registroDetalle[9].toString()), registroDetalle[10].toString()))) {
                        ItemDispersionResultadoMedioPagoFallecimientoDTO itemBeneficiario = new ItemDispersionResultadoMedioPagoFallecimientoDTO();

                        itemBeneficiario.setIdentificadorCondicion(Long.parseLong(registroDetalle[0].toString()));
                        itemBeneficiario.setTipoIdentificacionBeneficiario(TipoIdentificacionEnum.valueOf(registroDetalle[9].toString()));
                        itemBeneficiario.setNumeroIdentificacionBeneficiario(registroDetalle[10].toString());
                        itemBeneficiario.setNombreBeneficiario(registroDetalle[11].toString());

                        List<BigDecimal> valoresDispersion = new ArrayList<>();
                        valoresDispersion.add(BigDecimal.valueOf(Double.parseDouble(registroDetalle[13].toString())));
                        itemBeneficiario.setValoresDispersion(valoresDispersion);
                        
                        List<Date> fechasDispersion = new ArrayList<>();
                        fechasDispersion.add(CalendarUtils.darFormatoYYYYMMDDGuionDate(registroDetalle[12].toString()));
                        itemBeneficiario.setFechasDispersion(fechasDispersion);

                        itemsBeneficiarios.add(itemBeneficiario);
                        beneficiariosProcesados.put(new KeyPersonaDTO(TipoIdentificacionEnum.valueOf(registroDetalle[9].toString()),
                                registroDetalle[10].toString()), indicadorProcesamiento++);

                        identificadoresCondiciones.add(itemBeneficiario.getIdentificadorCondicion());
                    }
                    else {
                        itemsBeneficiarios
                                .get(beneficiariosProcesados.get(new KeyPersonaDTO(
                                        TipoIdentificacionEnum.valueOf(registroDetalle[9].toString()), registroDetalle[10].toString())))
                                .getValoresDispersion().add(BigDecimal.valueOf(Double.parseDouble(registroDetalle[13].toString())));
                        
                        itemsBeneficiarios
                        .get(beneficiariosProcesados.get(new KeyPersonaDTO(
                                TipoIdentificacionEnum.valueOf(registroDetalle[9].toString()), registroDetalle[10].toString())))
                        .getFechasDispersion().add(CalendarUtils.darFormatoYYYYMMDDGuionDate(registroDetalle[12].toString()));
                    }
                }
                detalleEfectivoDTO.setIdentificadoresCondiciones(identificadoresCondiciones);
                detalleEfectivoDTO.setItemsDetalle(itemsBeneficiarios);
            }

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return detalleEfectivoDTO;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarValidacionFallidaPersonaFallecimiento(java.lang.String,
     *      java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean consultarValidacionAporteMinimoFallecimiento(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarValidacionFallidaPersonaFallecimiento(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
          List<String> validaciones = null;
        try{
         validaciones = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_VALIDACION_FALLIDA_PERSONA)
                    .setParameter("numeroRadicacion", numeroRadicacion)
                    .setParameter("tipoValidacion", ConjuntoValidacionSubsidioEnum.TRABAJADOR_NO_APORTE_MINIMO.name())
                    .getResultList();
        }catch(NoResultException e ){
          logger.info("consultarValidacionAporteMinimoFallecimiento Consulta vacia "+e);  
        }
        if (validaciones == null){
            return Boolean.FALSE;
        } 
        
        if (validaciones.size()>0){
            return Boolean.TRUE;
        } 
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return Boolean.FALSE;
       
    }
    
    /** 
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarCondicionPersonaRadicacion(java.lang.String)
     */
    @SuppressWarnings("unchecked")
	@Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Long consultarCondicionPersonaRadicacion(String numeroRadicacion) {
    	String firmaMetodo = "ConsultasModeloSubsidio.consultarCondicionPersonaRadicacion(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Long idCondicionPersona = null;
        
        List<BigInteger> idCondicionPersonaList = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_IDCONDICIONPERSONA_RADICACION)
                .setParameter("numeroRadicacion", numeroRadicacion)
                .getResultList();
        
        if (idCondicionPersonaList != null && !idCondicionPersonaList.isEmpty()) {
        	idCondicionPersona = (idCondicionPersonaList.get(0)).longValue();
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    	return idCondicionPersona;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#confirmarBeneficiarioLiquidacionFallecimiento(java.lang.String,
     *      java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void confirmarLiquidacionFallecimientoAporteMinimo(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.confirmarBeneficiarioLiquidacionFallecimiento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_GESTION_SUBSIDIO_FALLECIMIENTO_CONFIRMADO_APORTE_MINIMO)
                    .setParameter("numeroRadicacion", numeroRadicacion)
                    .executeUpdate();

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#consultarResultadoLiquidacionGestionAporteMinimo(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ResultadoLiquidacionFallecimientoDTO consultarResultadoLiquidacionGestionAporteMinimo(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarResultadoLiquidacionFallecimiento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoLiquidacionFallecimientoDTO resultadoLiquidacionDTO = new ResultadoLiquidacionFallecimientoDTO();
        resultadoLiquidacionDTO.setResultadoProceso(consultarEstadoProcesoLiquidacionEspecifica(numeroRadicacion));

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultadoLiquidacionDTO;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#gestionProcesoEliminacion(java.lang.String, java.lang.Boolean)
     */
    @Override
    public void gestionProcesoEliminacion(String numeroRadicado) {
    	
    	 String firmaMetodo = "ConsultasModeloSubsidio.gestionProcesoEliminacion(String, Boolean)";
         logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
         try {
        	 entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.SUBSIDIO_INICIO_PROCESO_ELIMINACION)
          		.setParameter("fechaInicio", Calendar.getInstance().getTime())
          		.setParameter("numeroRadicado", numeroRadicado).executeUpdate();
        	 entityManagerSubsidio.flush();
             logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
         } catch (Exception e) {
             logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
             throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
         }
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#eliminarLiquidacionSP(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean consultarEjecucionProcesoEliminacion(String numeroRadicado) {
        String firmaMetodo = "ConsultasModeloSubsidio.ejecutarOrquestadorStagin()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);   
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return entityManagerSubsidio
                .createNamedQuery(
                        NamedQueriesConstants.CONSULTAR_PROCESO_SUBSIDIO_ELIMINACION)
                .setParameter("numeroRadicado", numeroRadicado).getSingleResult().toString().equals("1")?true:false;    
    }
    
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConsultaDescuentosSubsidioTrabajadorGrupoDTO> consultarDescuentosSubsidioTrabajador(
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String numeroRadicacion) {
    	String firmaMetodo = "ConsultasModeloSubsidio.consultarDescuentosSubsidioTrabajador()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);   
        
    	List<ConsultaDescuentosSubsidioTrabajadorGrupoDTO> descuentos = new ArrayList<>();
    	
    	List<Object[]> resultadosDescuentos = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTAR_DESCUENTOS_SUBSIDIO_TRABAJADOR)
    	.setParameter("numeroIdentificacion", numeroIdentificacion)
    	.setParameter("tipoIdentificacion", tipoIdentificacion.name())
        .setParameter("numeroRadicacion", numeroRadicacion).getResultList();
    	
    	if (resultadosDescuentos != null && !resultadosDescuentos.isEmpty()) {
    		for (Object[] object : resultadosDescuentos) {
    			ConsultaDescuentosSubsidioTrabajadorGrupoDTO descuento = new ConsultaDescuentosSubsidioTrabajadorGrupoDTO();
				descuento.setNumeroGrupoFamiliar(((Short)object[0]).byteValue());
				descuento.setMontoDescontado((BigDecimal)object[1]);
				descuento.setIdEntidadDescuento(((BigInteger)object[2]).longValue());
				descuento.setCodigoReferencia(object.length == 4 ? object[3] != null ? object[3].toString() : "" : "");
				descuentos.add(descuento);
			}
    	}
    	logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    	return descuentos;
    }

    @Override
    public void guardarListaComoCSV(String resultado, String numeroRadicacion) throws IOException {
        logger.info("Inicio el metodo guardarListaComoCSV en la clase Consultas Modelo Subsidio");
        String nombreArchivo = "SD_LIQUIDACION_" + numeroRadicacion + ".csv";
        String rutaDescargas = System.getProperty("user.home") + "/Downloads/";
        logger.info("Nombre del archivo es: " + nombreArchivo);
        logger.info("La ruta de descarga es: " + rutaDescargas);
        File archivoCSV = new File(rutaDescargas, nombreArchivo);
        logger.info("paso todos los llamados de File: ");
        //String cadena = resultado;
        //String nombreArchivo1 = "datos.csv";
        String ruta = System.getProperty("user.home") + "/Downloads/" + nombreArchivo;
        logger.info("paso todos los llamados de  ruta: ");
        FileWriter fw = new FileWriter(ruta);
        logger.info("paso todos los llamados de  FileWriter fw: ");
        BufferedWriter bw = new BufferedWriter(fw);
        logger.info("llamados de BufferedWriter bw: ");
        logger.info("paso todos los llamados de File: ");
    }

    @Override
    public void iniciarProcesoGeneracionArchivoSinDerecho(String numeroRadicacion) {
        List<Object[]> resultadosSinDerecho = new ArrayList<>();
        resultadosSinDerecho = entityManagerSubsidio
                    .createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_GET_RESULTADO_LIQUIDACION_SIN_DERECHO)            
                    .setParameter("sNumeroRadicado", numeroRadicacion)
                    .setParameter("tipoIdentificacion", "null")
                    .setParameter("numeroIdentificacion", "null")
                    .setParameter("offset", 0)
                    .setParameter("rows", 1)
                    .getResultList();
    }

    @Override
    public void limpiarGeneracionArchivoSinDerecho(String numeroRadicacion){
        logger.info("Inicio el metodo limpiarGeneracionArchivoSinDerecho");

        entityManagerSubsidio.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_LIMPIAR_RESULTADO_LIQUIDACION_SIN_DERECHO)
                        .setParameter("sNumeroRadicado", numeroRadicacion)
                        .execute();

        logger.info("Finaliza el metodo limpiarGeneracionArchivoSinDerecho");
    }
    
    @Override
    public Long consultarNumeroRegistrosSinDerecho(String numeroRadicacion) {

        List<Object> resultado;
        resultado = entityManagerSubsidio
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTROS_LIQUIDACION_SIN_DERECHO)
                .setParameter("numeroRadicado", numeroRadicacion).getResultList();
        
        if (resultado != null && !resultado.isEmpty()) {
            return Long.parseLong(resultado.get(0).toString());
        }
        return 0L;   
    }

    @Override
    public List<RegistroSinDerechoSubsidioDTO> generarDataLiquidaciomSinDerecho(String numeroRadicacion, Integer offset,
            Integer rows,TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String firmaMetodo = "consultasModeloCore.generarDataLiquidaciomSinDerecho(QueryFilterInDTO,int,int,EntityManager)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        logger.debug("Inicio el metodo generarDataLiquidaciomSinDerecho en la clase DataSouceLineSinDerecho");
        List<RegistroSinDerechoSubsidioDTO> personasSinDerechoDTO = new ArrayList<RegistroSinDerechoSubsidioDTO>();
        List<Object[]> resultadosSinDerecho = new ArrayList<>();
        try {
            if (numeroRadicacion != null) {
                logger.debug("Ingreso al if de generarDataLiquidaciomSinDerecho del metodo modelo core con el valpor de: "+ numeroRadicacion);
               /* resultadosSinDerecho = entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.CONSULTA_LIQUIDACION_MASIVA_SIN_DERECHO)
                        .setParameter("numeroRadicacion", numeroRadicacion)
                        .getResultList();*/
                if(offset == null){
                    offset = 100000;
                }
                logger.info(numeroRadicacion);
                logger.info(offset);
                logger.info(rows);
                logger.info(tipoIdentificacion);
                logger.info(numeroIdentificacion);
                logger.info("===============================");
                resultadosSinDerecho = entityManagerSubsidio
                        .createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_GET_RESULTADO_LIQUIDACION_SIN_DERECHO)
                        .setParameter("sNumeroRadicado", numeroRadicacion)
                        .setParameter("offset", offset)
                        .setParameter("rows", rows)
                        .setParameter("tipoIdentificacion", tipoIdentificacion != null ? tipoIdentificacion.name():"null")
                        .setParameter("numeroIdentificacion", numeroIdentificacion != null ?numeroIdentificacion:"null")
                        .getResultList();

                /*
                resultadosSinDerecho = entityManagerSubsidio
                        .createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_GET_RESULTADO_LIQUIDACION_SIN_DERECHO)
                        .setParameter("sNumeroRadicado", numeroRadicacion)
                        .setParameter("offset", offset)
                        .setParameter("rows", rows)
                        .setParameter("tipoIdentificacion", tipoIdentificacion != null ? tipoIdentificacion.name():"null")
                        .setParameter("numeroIdentificacion", numeroIdentificacion != null ?numeroIdentificacion:"null")
                        .getResultList();*/

            } else {

                logger.debug("Ingreso al else de generarDataLiquidaciomSinDerecho con el valpor de: " + numeroRadicacion);

            }
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            logger.debug("Ingreso al catch de generarDataLiquidaciomSinDerecho, error al consultar SP");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug("Ingresa a realizar la iteracion del objeto");
        for (Object[] registro : resultadosSinDerecho) {
            registro = reemplazarNulos(registro);
            RegistroSinDerechoSubsidioDTO sinDerechoDTO = new RegistroSinDerechoSubsidioDTO();
            //logger.debug("numEmp: " + registro[5].toString()+ " - numTrabaj: " + registro[11]!=null?registro[11].toString():"null");

            if (!registro[1].toString().equals(cadenaVacia)) {
                sinDerechoDTO.setFechaLiquidacion(CalendarUtils.darFormatoYYYYMMDDGuionDate(registro[1].toString()));
            }

            sinDerechoDTO.setTipoLiquidacion(registro[2].toString());
            sinDerechoDTO.setSubtipoLiquidacion(registro[3].toString());
            sinDerechoDTO.setTipoIdentificacionEmpleador(registro[4].toString());
           /*  sinDerechoDTO.setTipoLiquidacion(validarExistenciaValorEnumeracion(TipoProcesoLiquidacionEnum.class, registro[1].toString())
                    ? TipoProcesoLiquidacionEnum.valueOf(registro[2].toString()).equals(TipoProcesoLiquidacionEnum.MASIVA)
                            ? TipoProcesoLiquidacionEnum.MASIVA.getDescripcion() : TipoLiquidacionEnum.ESPECIFICA.getDescripcion()
                    : "");
            //TipoProcesoLiquidacionEnum.MASIVA.name().equals(registro[2].toString()) ? TipoLiquidacionEnum.MASIVA.getDescripcion() : TipoLiquidacionEnum.ESPECIFICA.getDescripcion() : "");
            sinDerechoDTO
                    .setSubtipoLiquidacion(validarExistenciaValorEnumeracion(TipoProcesoLiquidacionEnum.class, registro[1].toString())
                            ? TipoProcesoLiquidacionEnum.valueOf(registro[2].toString()).equals(TipoProcesoLiquidacionEnum.MASIVA)
                                    ? TipoProcesoLiquidacionEnum.MASIVA.getDescripcion()
                                    : TipoProcesoLiquidacionEnum.valueOf(registro[2].toString()).getDescripcion()
                            : "");
            //TipoProcesoLiquidacionEnum.MASIVA.name().equals(registro[2].toString())? "" :TipoProcesoLiquidacionEnum.valueOf(registro[1].toString()).getDescripcion(): "");
            sinDerechoDTO.setTipoIdentificacionEmpleador(
                    validarExistenciaValorEnumeracion(TipoIdentificacionEnum.class, registro[4].toString())
                            ? TipoIdentificacionEnum.valueOf(registro[4].toString()).getDescripcion() : "");*/
            sinDerechoDTO.setNumeroIdentificacionEmpleador(registro[5].toString());
            sinDerechoDTO.setNombreEmpleador(registro[6].toString());

            sinDerechoDTO.setCiiu(registro[7].toString());

          /*   try {
                // Intentamos convertir el valor a un entero
                int ciiu = Integer.parseInt(registro[7].toString());

                // Si la conversión es exitosa, lo guardamos en sinDerechoDTO
                sinDerechoDTO.setCiiu(Integer.toString(ciiu));
            } catch (NumberFormatException e) {

                sinDerechoDTO.setCiiu(registro[8].toString());
            }*/
            sinDerechoDTO.setCondicionAgraria(registro[8].toString().equals(Boolean.TRUE.toString()) ? "A" : "N");
            sinDerechoDTO.setCodigoSucursal(registro[9].toString());
            /*if (!registro[10].toString().equals(cadenaVacia)) {
                sinDerechoDTO.setAnioBeneficio1429(registro[10].toString().equals(null) ? "":"");
            }*/

            if(registro[10] == null){
                sinDerechoDTO.setAnioBeneficio1429(cadenaVacia);
            }else {
                sinDerechoDTO.setAnioBeneficio1429(registro[10].toString());
            }
            sinDerechoDTO.setNumeroIdentificacionTrabajador(registro[11].toString());
           /*  sinDerechoDTO.setTipoIdentificacionTrabajador(
                    validarExistenciaValorEnumeracion(TipoIdentificacionEnum.class, registro[12].toString())
                            ? TipoIdentificacionEnum.valueOf(registro[12].toString()).getDescripcion() : "");*/
            sinDerechoDTO.setTipoIdentificacionTrabajador(registro[12].toString());
            sinDerechoDTO.setNombreTrabajador(registro[13].toString());
            sinDerechoDTO.setNumeroIdentificacionBeneficiario(registro[14].toString());
            /*sinDerechoDTO.setTipoIdentificacionBeneficiario(
                    validarExistenciaValorEnumeracion(TipoIdentificacionEnum.class, registro[15].toString())
                            ? TipoIdentificacionEnum.valueOf(registro[15].toString()).getDescripcion() : "");*/
            sinDerechoDTO.setTipoIdentificacionBeneficiario(registro[15].toString());
            sinDerechoDTO.setNombreBeneficiario(registro[16].toString());
            /*sinDerechoDTO.setTipoSolicitante(validarExistenciaValorEnumeracion(TipoBeneficiarioEnum.class, registro[17].toString())
                    ? TipoBeneficiarioEnum.valueOf(registro[17].toString()).getDescripcion() : "");
            sinDerechoDTO.setClasificacion(validarExistenciaValorEnumeracion(ClasificacionEnum.class, registro[18].toString())
                    ? ClasificacionEnum.valueOf(registro[18].toString()).getDescripcion() : "");*/
            sinDerechoDTO.setTipoSolicitante(registro[17].toString());
            sinDerechoDTO.setClasificacion(registro[18].toString());
            sinDerechoDTO.setRazonesSinDerecho(registro[19].toString());
            sinDerechoDTO.setPeriodoLiquidado(registro[20].toString());
            sinDerechoDTO.setTipoPeriodo(registro[21].toString());

            personasSinDerechoDTO.add(sinDerechoDTO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        logger.debug("Respuesta de parte de la DB de liquidacion de personas sin derecho DTO: ");
        return personasSinDerechoDTO;

    }
    private Object[] reemplazarNulos(Object[] registro) {
        for (int i = 0; i < registro.length; i++) {
            registro[i] = (registro[i] == null) ? cadenaVacia : registro[i];
        }
        return registro;
    }

    @Override
    public void insercionCondicionesDbo(String numeroRadicado){
        try{
            entityManagerSubsidio.createNamedStoredProcedureQuery(NamedQueriesConstants.USP_SM_UTIL_INSERT_CONDICIONES_DBO)
                                .setParameter("sNumeroRadicado", numeroRadicado)
                                .execute();
        }catch(Exception e){
            logger.error("fallo la ejecucion del sp USP_SM_UTIL_INSERT_CONDICIONES_DBO");
            e.printStackTrace();
        }
    }
}
