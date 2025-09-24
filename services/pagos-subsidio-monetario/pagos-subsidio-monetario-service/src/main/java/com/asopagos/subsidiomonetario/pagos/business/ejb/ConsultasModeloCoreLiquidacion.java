package com.asopagos.subsidiomonetario.pagos.business.ejb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionMontoLiquidadoFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionResultadoDescuentosFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionResultadoMedioPagoFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionResumenMedioPagoFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ItemDispersionResultadoMedioPagoFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ItemResultadoLiquidacionFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.KeyPersonaDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.KeyPersonaEntidadDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoPorAdministradorLiquidacionFallecimientoDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoTarjetaMultiserviciosEnum;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCoreLiquidacion;
import com.asopagos.subsidiomonetario.pagos.constants.NamedQueriesConstants;
import com.asopagos.util.CalendarUtils;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de información en el modelo de datos Core para el proceso de
 * dispersión <br/>
 * <b>Módulo:</b> Asopagos - HU-317-508 <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
@Stateless
public class ConsultasModeloCoreLiquidacion implements IConsultasModeloCoreLiquidacion, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloCoreLiquidacion.class);

    /** Entity Manager */
    @PersistenceContext(unitName = "pagossubsidiomonetario_PU")
    private EntityManager entityManagerCore;

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCoreLiquidacion#consultarDispersionMontoLiquidacionFallecimiento(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DispersionMontoLiquidadoFallecimientoDTO consultarDispersionMontoLiquidacionFallecimiento(String numeroRadicacion) {
        String firmaServicio = "ConsultasModeloCoreLiquidacion.consultarDispersionMontoLiquidacionFallecimiento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        
            DispersionMontoLiquidadoFallecimientoDTO montoLiquidadoDTO = consultarTotalesFallecimiento(numeroRadicacion);

            //Sección de consulta para el medio de pago tarjeta
            DispersionResumenMedioPagoFallecimientoDTO dispersionTarjetaDTO = consultarPagosTarjetaFallecimiento(numeroRadicacion);
            montoLiquidadoDTO.setResumenPagosTarjeta(dispersionTarjetaDTO);
            montoLiquidadoDTO.setIdentificadoresCondiciones(new ArrayList<>(dispersionTarjetaDTO.getIdentificadoresCondiciones()));

            //Sección de consulta para el medio de pago efectivo
            DispersionResumenMedioPagoFallecimientoDTO dispersionEfectivoDTO = consultarPagosEfectivoFallecimiento(numeroRadicacion);
            montoLiquidadoDTO.setResumenPagosEfectivo(dispersionEfectivoDTO);
            montoLiquidadoDTO.getIdentificadoresCondiciones()
                    .addAll(new ArrayList<>(dispersionEfectivoDTO.getIdentificadoresCondiciones()));

            //Sección de consulta para el medio de pago bancos - consignación
            DispersionResumenMedioPagoFallecimientoDTO dispersionBancoConsignacionesDTO = consultarPagosBancoConsignacionesFallecimiento(
                    numeroRadicacion);
            montoLiquidadoDTO.setResumenPagosBancoConsignacion(dispersionBancoConsignacionesDTO);
            montoLiquidadoDTO.getIdentificadoresCondiciones()
                    .addAll(new ArrayList<>(dispersionBancoConsignacionesDTO.getIdentificadoresCondiciones()));

            //Sección de consulta para el medio de pago bancos - pagos judiciales
            DispersionResumenMedioPagoFallecimientoDTO dispersionBancoJudicialesDTO = consultarPagosBancoJudicialesFallecimiento(numeroRadicacion);
            montoLiquidadoDTO.setResumenPagosBancoPagosJudiciales(dispersionBancoJudicialesDTO);
            montoLiquidadoDTO.getIdentificadoresCondiciones().addAll(new ArrayList<>(dispersionBancoJudicialesDTO.getIdentificadoresCondiciones()));
            
            //Sección de consulta para los descuentos
            DispersionResumenMedioPagoFallecimientoDTO dispersionDescuentosDTO = consultarDescuentosFallecimiento(numeroRadicacion);
            montoLiquidadoDTO.setResumenDescuentos(dispersionDescuentosDTO);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return montoLiquidadoDTO;
      
    }

    /**
     * <b>Descripción:</b>Método que permite obtener los totales asociados a la dispersión de un proceso de liquidación de fallecimiento
     * @author <a>Roy López Cardona</a>
     * 
     * @param numeroRadicacion
     *        <code>Long</code>
     *        Valor del número de radicación
     * 
     * @return <code>DispersionMontoLiquidadoFallecimientoDTO</code>
     *         Información que representa los totales pendientes por dispersar
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private DispersionMontoLiquidadoFallecimientoDTO consultarTotalesFallecimiento(String numeroRadicacion) {
        String firmaServicio = "ConsultasModeloCoreLiquidacion.consultarTotalesFallecimiento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        try {
            Object[] registroTotales = (Object[]) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DISPERSION_FALLECIMIENTO_TOTALES_PENDIENTES)
                    .setParameter("numeroRadicacion", numeroRadicacion).getSingleResult();

            DispersionMontoLiquidadoFallecimientoDTO montoLiquidadoDTO = new DispersionMontoLiquidadoFallecimientoDTO();
            montoLiquidadoDTO.setMontoTotalDispersion(BigDecimal.valueOf(Double.parseDouble(registroTotales[0].toString())));
            montoLiquidadoDTO.setTotalDescuentosAplicados(BigDecimal.valueOf(Double.parseDouble(registroTotales[1].toString())));
            montoLiquidadoDTO.setMontoTotalLiquidar(BigDecimal.valueOf(Double.parseDouble(registroTotales[2].toString())));
            montoLiquidadoDTO.setCantidadAdministradorSubsidios(Long.parseLong(registroTotales[3].toString()));
            montoLiquidadoDTO.setCantidadCuotasDispersar(Long.parseLong(registroTotales[4].toString()));
            montoLiquidadoDTO.setTipoDesembolso(ModoDesembolsoEnum.valueOf(registroTotales[5].toString()));

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return montoLiquidadoDTO;
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return new DispersionMontoLiquidadoFallecimientoDTO();
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        }
    }

    /**
     * <b>Descripción:</b>Método que permite obtener los totales asociados a la dispersión para el medio de pago tarjeta
     * @author <a>Roy López Cardona</a>
     * 
     * @param numeroRadicacion
     *        <code>Long</code>
     *        Valor del número de radicación
     * 
     * @return <code>DispersionResumenMedioPagoFallecimientoDTO</code>
     *         Información que representa los totales para el medio de pago tarjeta
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private DispersionResumenMedioPagoFallecimientoDTO consultarPagosTarjetaFallecimiento(String numeroRadicacion) {
        String firmaServicio = "ConsultasModeloCoreLiquidacion.consultarPagosTarjetaFallecimiento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        
            DispersionResumenMedioPagoFallecimientoDTO dispersionTarjetaDTO = consultarCabeceraDispersionMedioPago(numeroRadicacion);
            dispersionTarjetaDTO = consultarDispersionResumenMedioDePago(dispersionTarjetaDTO, numeroRadicacion,
                    TipoMedioDePagoEnum.TARJETA,null);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return dispersionTarjetaDTO;
      
    }

    /**
     * <b>Descripción:</b>Método que permite obtener los totales asociados a la dispersión para el medio de pago efectivo
     * @author <a>Roy López Cardona</a>
     * 
     * @param numeroRadicacion
     *        <code>Long</code>
     *        Valor del número de radicación
     * 
     * @return <code>DispersionResumenMedioPagoFallecimientoDTO</code>
     *         Información que representa los totales para el medio de pago efectivo
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private DispersionResumenMedioPagoFallecimientoDTO consultarPagosEfectivoFallecimiento(String numeroRadicacion) {
        String firmaServicio = "ConsultasModeloCoreLiquidacion.consultarPagosEfectivoFallecimiento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

      //  try {
            DispersionResumenMedioPagoFallecimientoDTO dispersionEfectivoDTO = new DispersionResumenMedioPagoFallecimientoDTO();
            dispersionEfectivoDTO = consultarDispersionResumenMedioDePago(dispersionEfectivoDTO, numeroRadicacion,
                    TipoMedioDePagoEnum.EFECTIVO,null);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return dispersionEfectivoDTO;
        /*} catch (Exception e) {
        	e.printStackTrace();
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        */
    }

    /**
     * <b>Descripción:</b>Método que permite obtener los totales asociados a la dispersión para el medio de pago banco - consignaciones
     * @author <a>Roy López Cardona</a>
     * 
     * @param numeroRadicacion
     *        <code>Long</code>
     *        Valor del número de radicación
     * 
     * @return <code>DispersionResumenMedioPagoFallecimientoDTO</code>
     *         Información que representa los totales para el medio de pago banco - consignaciones
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private DispersionResumenMedioPagoFallecimientoDTO consultarPagosBancoConsignacionesFallecimiento(String numeroRadicacion) {
        String firmaServicio = "ConsultasModeloCoreLiquidacion.consultarPagosBancoConsignacionesFallecimiento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

       // try {
            DispersionResumenMedioPagoFallecimientoDTO dispersionBancoConsignacionesDTO = new DispersionResumenMedioPagoFallecimientoDTO();
            dispersionBancoConsignacionesDTO = consultarDispersionResumenMedioDePago(dispersionBancoConsignacionesDTO, numeroRadicacion,
                    TipoMedioDePagoEnum.TRANSFERENCIA,Boolean.FALSE);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return dispersionBancoConsignacionesDTO;
        /*} catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }*/
    }

    /**
     * <b>Descripción:</b>Método que se encarga de consultar los descuentos para la liquidación de fallecimiento relacionada al radicado
     * @author <a>Roy López Cardona</a>
     * 
     * @param numeroRadicacion
     *        <code>Long</code>
     *        Valor del número de radicación
     * 
     * @return <code>DispersionResumenMedioPagoFallecimientoDTO</code>
     *         Información que representa los totales para el medio de pago banco - consignaciones
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private DispersionResumenMedioPagoFallecimientoDTO consultarDescuentosFallecimiento(String numeroRadicacion) {
        String firmaServicio = "ConsultasModeloCoreLiquidacion.consultarDescuentosFallecimiento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        //try {
            DispersionResumenMedioPagoFallecimientoDTO dispersionDescuentosDTO = new DispersionResumenMedioPagoFallecimientoDTO();
            List<TipoMedioDePagoEnum> mediosDePago = new ArrayList<>();
            mediosDePago.add(TipoMedioDePagoEnum.EFECTIVO);
            mediosDePago.add(TipoMedioDePagoEnum.TRANSFERENCIA);
            mediosDePago.add(TipoMedioDePagoEnum.TARJETA);
            dispersionDescuentosDTO = consultarDispersionResumenDescuentos(dispersionDescuentosDTO, numeroRadicacion, mediosDePago);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return dispersionDescuentosDTO;
        /*} catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }*/
    }

    /**
     * <b>Descripción:</b>Método que permite obtener los totales asociados a la cabecera para el medio de pago tarjeta
     * @author <a>Roy López Cardona</a>
     * 
     * @param numeroRadicacion
     *        <code>Long</code>
     *        Valor del número de radicación
     * 
     * @return <code>DispersionResumenMedioPagoFallecimientoDTO</code>
     *         Información que representa los totales en la cabecera para el medio de pago tarjeta
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private DispersionResumenMedioPagoFallecimientoDTO consultarCabeceraDispersionMedioPago(String numeroRadicacion) {
        String firmaServicio = "ConsultasModeloCoreLiquidacion.consultarCabeceraMedioPago(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        try {
            Object[] registroMedioPago = (Object[]) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CABECERA_DISPERSION_MEDIO_PAGO)
                    .setParameter("numeroRadicacion", numeroRadicacion).getSingleResult();

            DispersionResumenMedioPagoFallecimientoDTO dispersionTarjetaDTO = new DispersionResumenMedioPagoFallecimientoDTO();
            dispersionTarjetaDTO.setNumeroRegistros(Long.parseLong(registroMedioPago[0].toString()));
            dispersionTarjetaDTO.setMontoTotalLiquidar(BigDecimal.valueOf(Double.parseDouble(registroMedioPago[1].toString())));
            dispersionTarjetaDTO.setCantidadCuotas(Long.parseLong(registroMedioPago[2].toString()));
            dispersionTarjetaDTO.setCantidadAdministradorSubsidios(Long.parseLong(registroMedioPago[3].toString()));

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return dispersionTarjetaDTO;
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Método que permite obtener los resultados agrupados por administrador para el medio de pago parametrizado
     * @param dispersionMedioPagoDTO
     *        DTO con la información de dispersión al medio de pago
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @param medioDePago
     *        Tipo de medio de pago
     * @param bancoPagoJudicial
     *        <code>Boolean</code>
     *        Si es true es porque se requieren los pagos judiciales, si es false se requiere corriente y si es null es porque no es
     *        de medio de pago Transferencia
     * @return DTO con la información agrupada por administrador de subsidio
     * @author rlopez
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private DispersionResumenMedioPagoFallecimientoDTO consultarDispersionResumenMedioDePago(
            DispersionResumenMedioPagoFallecimientoDTO dispersionMedioPagoDTO, String numeroRadicacion, TipoMedioDePagoEnum medioDePago,
            Boolean bancoPagoJudicial) {
        String firmaServicio = "ConsultasModeloCoreLiquidacion.consultarDispersionResumenMedioDePago(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<String> medios = new ArrayList<>();
        medios.add(medioDePago.name());
        logger.debug("medioPago:" + medioDePago.name());
        //try {
        		/*
            	List<Object[]> registrosDispersion = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_RESULTADOS_DISPERSION_FALLECIMIENTO_POR_ADMINISTRADOR_MEDIO_PAGO)
                    .setParameter("numeroRadicacion", numeroRadicacion).setParameter("medioDePago", medios).getResultList();
         			*/
    		List<Object[]> registrosDispersion =  entityManagerCore
            .createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_PG_RESULTADOSDISPERSIONADMINISTRADORMEDIOPAGO)
            .setParameter("sNumeroRadicado", numeroRadicacion)
            .setParameter("sMedio", medioDePago.name()).getResultList();
        
        
            logger.debug("termina consulta");
            List<Long> identificadoresCondiciones = new ArrayList<>();

            List<ResultadoPorAdministradorLiquidacionFallecimientoDTO> resultadosPorAdministrador = new ArrayList<>();
            Map<KeyPersonaDTO, Integer> administradoresProcesados = new HashMap<>();
            Integer indicadorProcesamiento = 0;

            for (Object[] registroDispersion : registrosDispersion) {

                ItemResultadoLiquidacionFallecimientoDTO itemBeneficiario = new ItemResultadoLiquidacionFallecimientoDTO();

                itemBeneficiario.setIdCondicionBeneficiarioAfiliado(Long.parseLong(registroDispersion[1].toString()));
                itemBeneficiario
                        .setTipoIdentificacionBeneficiarioAfiliado(TipoIdentificacionEnum.valueOf(registroDispersion[5].toString()));
                itemBeneficiario.setNumeroIdentificacionBeneficiarioAfiliado(registroDispersion[6].toString());
                itemBeneficiario.setNombreBeneficiarioAfiliado(registroDispersion[7].toString());
                itemBeneficiario.setParentesco(ClasificacionEnum.valueOf(registroDispersion[8].toString()));
                itemBeneficiario.setTotalDerecho(BigDecimal.valueOf(registroDispersion[9] == null ? 0 : Double.parseDouble(registroDispersion[9].toString())));
                itemBeneficiario.setTotalDescuentosPorEntidad(BigDecimal.valueOf(registroDispersion[15] == null ? 0 : Double.parseDouble(registroDispersion[15].toString())));
                itemBeneficiario.setTotalDescuentos(BigDecimal.valueOf(registroDispersion[10] == null ? 0 : Double.parseDouble(registroDispersion[10].toString())));
                itemBeneficiario.setTotalPagar(BigDecimal.valueOf(registroDispersion[11] == null ? 0 : Double.parseDouble(registroDispersion[11].toString())));
                
                
                if (!administradoresProcesados.containsKey(new KeyPersonaDTO(
                        TipoIdentificacionEnum.valueOf(registroDispersion[2].toString()), registroDispersion[3].toString()))) {
                    
                    if((bancoPagoJudicial == null)  //si la variable viene null es porque no es medio de pago Transferencia
                          //si es medio de pago transferencia y consignaciones(variable en FALSE), el ultimo campo de la consulta tiene que venir en cero
                            || (bancoPagoJudicial == Boolean.FALSE && (registroDispersion[14] == null
                                    || (registroDispersion[14] != null && registroDispersion[14].toString().equals("0")))) 
                           //si es medio de pago transferencia y judicial(variable en TRUE), el ultimo campo de la consulta tiene que venir en 1
                            || (bancoPagoJudicial == Boolean.TRUE
                                    && (registroDispersion[14] != null && registroDispersion[14].toString().equals("1")))) {

                        ResultadoPorAdministradorLiquidacionFallecimientoDTO resultadoAdminDTO = new ResultadoPorAdministradorLiquidacionFallecimientoDTO();

                        resultadoAdminDTO.setIdentificadorCondicion(Long.parseLong(registroDispersion[0].toString()));
                        resultadoAdminDTO
                                .setTipoIdentificacionAdministrador(TipoIdentificacionEnum.valueOf(registroDispersion[2].toString()));
                        resultadoAdminDTO.setNumeroIdentificacionAdministrador(registroDispersion[3].toString());
                        resultadoAdminDTO.setNombreAdministrador(registroDispersion[4].toString());
                        resultadoAdminDTO.setMedioDePagoAdministrador(medioDePago.name());

                        if (!medioDePago.equals(TipoMedioDePagoEnum.TARJETA)) {
                            resultadoAdminDTO.setTotalDispersado(BigDecimal
                                    .valueOf(registroDispersion[12] == null ? 0 : Double.parseDouble(registroDispersion[12].toString())));
                            resultadoAdminDTO.setTotalProgramado(BigDecimal
                                    .valueOf(registroDispersion[13] == null ? 0 : Double.parseDouble(registroDispersion[13].toString())));
                        }

                        List<ItemResultadoLiquidacionFallecimientoDTO> itemsBeneficiarios = new ArrayList<>();

                        itemsBeneficiarios.add(itemBeneficiario);
                        resultadoAdminDTO.setItemsBeneficiarios(itemsBeneficiarios);

                        identificadoresCondiciones.add(resultadoAdminDTO.getIdentificadorCondicion());
                        identificadoresCondiciones.add(itemBeneficiario.getIdCondicionBeneficiarioAfiliado());

                        resultadosPorAdministrador.add(resultadoAdminDTO);
                        administradoresProcesados.put(new KeyPersonaDTO(TipoIdentificacionEnum.valueOf(registroDispersion[2].toString()),
                                registroDispersion[3].toString()), indicadorProcesamiento++);
                    }
                }
                else {
                    resultadosPorAdministrador
                            .get(administradoresProcesados.get(new KeyPersonaDTO(
                                    TipoIdentificacionEnum.valueOf(registroDispersion[2].toString()), registroDispersion[3].toString())))
                            .getItemsBeneficiarios().add(itemBeneficiario);
                    identificadoresCondiciones.add(itemBeneficiario.getIdCondicionBeneficiarioAfiliado());
                }
            }
            dispersionMedioPagoDTO.setResultadosPorAdministrador(resultadosPorAdministrador);
            dispersionMedioPagoDTO.setIdentificadoresCondiciones(identificadoresCondiciones);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return dispersionMedioPagoDTO;
      /*  } catch (Exception e) {
        	e.printStackTrace();
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        */
    }

    /**
     * Método que permite obtener los resultados agrupados por administrador para el medio de pago descuentos
     * @param dispersionMedioPagoDTO
     *        DTO con la información de dispersión al medio de pago
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @param medioDePago
     *        Tipo de medio de pago
     * @return DTO con la información agrupada por administrador de subsidio
     * @author rlopez
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private DispersionResumenMedioPagoFallecimientoDTO consultarDispersionResumenDescuentos(
            DispersionResumenMedioPagoFallecimientoDTO dispersionMedioPagoDTO, String numeroRadicacion,
            List<TipoMedioDePagoEnum> mediosDePago) {
        String firmaServicio = "ConsultasModeloCoreLiquidacion.consultarDispersionResumenDescuentos(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<String> medios = convertirMediosDePagoCadenas(mediosDePago);

        try {
            List<Object[]> registrosDispersion = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_RESULTADOS_DISPERSION_FALLECIMIENTO_POR_ADMINISTRADOR_MEDIO_PAGO)
                    .setParameter("numeroRadicacion", numeroRadicacion).setParameter("medioDePago", medios).getResultList();

            List<Long> identificadoresCondiciones = new ArrayList<>();

            List<ResultadoPorAdministradorLiquidacionFallecimientoDTO> resultadosPorAdministrador = new ArrayList<>();
            Map<KeyPersonaDTO, Integer> administradoresProcesados = new HashMap<>();
            Integer indicadorProcesamiento = 0;

            for (Object[] registroDispersion : registrosDispersion) {

                ItemResultadoLiquidacionFallecimientoDTO itemBeneficiario = new ItemResultadoLiquidacionFallecimientoDTO();

                itemBeneficiario.setIdCondicionBeneficiarioAfiliado(Long.parseLong(registroDispersion[1].toString()));
                itemBeneficiario
                        .setTipoIdentificacionBeneficiarioAfiliado(TipoIdentificacionEnum.valueOf(registroDispersion[5].toString()));
                itemBeneficiario.setNumeroIdentificacionBeneficiarioAfiliado(registroDispersion[6].toString());
                itemBeneficiario.setNombreBeneficiarioAfiliado(registroDispersion[7].toString());
                itemBeneficiario.setParentesco(ClasificacionEnum.valueOf(registroDispersion[8].toString()));
                itemBeneficiario.setTotalDerecho(BigDecimal.valueOf(Double.parseDouble(registroDispersion[9].toString())));
                itemBeneficiario.setTotalDescuentosPorEntidad(BigDecimal.valueOf(Double.parseDouble(registroDispersion[15].toString())));
                itemBeneficiario.setTotalDescuentos(BigDecimal.valueOf(Double.parseDouble(registroDispersion[10].toString())));
                itemBeneficiario.setTotalPagar(BigDecimal.valueOf(Double.parseDouble(registroDispersion[11].toString())));

                if (itemBeneficiario.getTotalDescuentos().compareTo(BigDecimal.valueOf(0)) != 0) {
                    if (!administradoresProcesados.containsKey(new KeyPersonaDTO(
                            TipoIdentificacionEnum.valueOf(registroDispersion[2].toString()), registroDispersion[3].toString()))) {
                        ResultadoPorAdministradorLiquidacionFallecimientoDTO resultadoAdminDTO = new ResultadoPorAdministradorLiquidacionFallecimientoDTO();

                        resultadoAdminDTO.setIdentificadorCondicion(Long.parseLong(registroDispersion[0].toString()));
                        resultadoAdminDTO
                                .setTipoIdentificacionAdministrador(TipoIdentificacionEnum.valueOf(registroDispersion[2].toString()));
                        resultadoAdminDTO.setNumeroIdentificacionAdministrador(registroDispersion[3].toString());
                        resultadoAdminDTO.setNombreAdministrador(registroDispersion[4].toString());
                        resultadoAdminDTO.setMedioDePagoAdministrador("Descuentos");

                        List<ItemResultadoLiquidacionFallecimientoDTO> itemsBeneficiarios = new ArrayList<>();

                        itemsBeneficiarios.add(itemBeneficiario);
                        resultadoAdminDTO.setItemsBeneficiarios(itemsBeneficiarios);

                        identificadoresCondiciones.add(resultadoAdminDTO.getIdentificadorCondicion());
                        identificadoresCondiciones.add(itemBeneficiario.getIdCondicionBeneficiarioAfiliado());

                        resultadosPorAdministrador.add(resultadoAdminDTO);
                        administradoresProcesados.put(new KeyPersonaDTO(TipoIdentificacionEnum.valueOf(registroDispersion[2].toString()),
                                registroDispersion[3].toString()), indicadorProcesamiento++);
                    }
                    else {
                        resultadosPorAdministrador.get(administradoresProcesados.get(new KeyPersonaDTO(
                                TipoIdentificacionEnum.valueOf(registroDispersion[2].toString()), registroDispersion[3].toString())))
                                .getItemsBeneficiarios().add(itemBeneficiario);
                        identificadoresCondiciones.add(itemBeneficiario.getIdCondicionBeneficiarioAfiliado());
                    }
                }
            }
            dispersionMedioPagoDTO.setResultadosPorAdministrador(resultadosPorAdministrador);
            dispersionMedioPagoDTO.setIdentificadoresCondiciones(identificadoresCondiciones);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return dispersionMedioPagoDTO;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /** Sección de consultas de detalle */

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCoreLiquidacion#consultarDispersionMontoLiquidadoFallecimientoPagoTarjeta(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoPagoTarjeta(String numeroRadicacion,
            Long identificadorCondicion) {
        String firmaServicio = "ConsultasModeloCoreLiquidacion.consultarDispersionMontoLiquidadoFallecimientoPagoTarjeta(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        try {
            List<Object[]> registrosDetalle = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_POR_ADMINISTRADOR_LIQUIDACION_FALLECIMIENTO_PAGOS_TARJETA)
                    .setParameter("numeroRadicacion", numeroRadicacion).setParameter("condicionAdministrador", identificadorCondicion)
                    .getResultList();

            DispersionResultadoMedioPagoFallecimientoDTO detalleTarjetaDTO = new DispersionResultadoMedioPagoFallecimientoDTO();
            if (!registrosDetalle.isEmpty()) {
                detalleTarjetaDTO.setIdentificadorCondicionAdministrador(Long.parseLong(registrosDetalle.get(0)[0].toString()));
                detalleTarjetaDTO.setTipoIdentificacionAdministrador(TipoIdentificacionEnum.valueOf(registrosDetalle.get(0)[2].toString()));
                detalleTarjetaDTO.setNumeroIdentificacionAdministrador(registrosDetalle.get(0)[3].toString());
                detalleTarjetaDTO.setNombreAdministrador(registrosDetalle.get(0)[4].toString());
                detalleTarjetaDTO.setNumeroTarjeta(registrosDetalle.get(0)[5].toString());
                detalleTarjetaDTO.setEstadoTarjeta(EstadoTarjetaMultiserviciosEnum.valueOf(registrosDetalle.get(0)[6].toString()));

                List<Long> identificadoresCondiciones = new ArrayList<>();
                identificadoresCondiciones.add(detalleTarjetaDTO.getIdentificadorCondicionAdministrador());

                List<ItemDispersionResultadoMedioPagoFallecimientoDTO> itemsBeneficiarios = new ArrayList<>();
                Map<KeyPersonaDTO, Integer> beneficiariosProcesados = new HashMap<>();
                Integer indicadorProcesamiento = 0;

                for (Object[] registroDetalle : registrosDetalle) {

                    if (!beneficiariosProcesados.containsKey(new KeyPersonaDTO(
                            TipoIdentificacionEnum.valueOf(registroDetalle[7].toString()), registroDetalle[8].toString()))) {
                        ItemDispersionResultadoMedioPagoFallecimientoDTO itemBeneficiario = new ItemDispersionResultadoMedioPagoFallecimientoDTO();

                        itemBeneficiario.setIdentificadorCondicion(Long.parseLong(registroDetalle[1].toString()));
                        itemBeneficiario.setTipoIdentificacionBeneficiario(TipoIdentificacionEnum.valueOf(registroDetalle[7].toString()));
                        itemBeneficiario.setNumeroIdentificacionBeneficiario(registroDetalle[8].toString());
                        itemBeneficiario.setNombreBeneficiario(registroDetalle[9].toString());

                        List<BigDecimal> valoresDispersion = new ArrayList<>();
                        valoresDispersion.add(BigDecimal.valueOf(Double.parseDouble(registroDetalle[11].toString())));
                        itemBeneficiario.setValoresDispersion(valoresDispersion);

                        itemsBeneficiarios.add(itemBeneficiario);
                        beneficiariosProcesados.put(new KeyPersonaDTO(TipoIdentificacionEnum.valueOf(registroDetalle[7].toString()),
                                registroDetalle[8].toString()), indicadorProcesamiento++);

                        identificadoresCondiciones.add(itemBeneficiario.getIdentificadorCondicion());
                    }
                    else {
                        itemsBeneficiarios
                                .get(beneficiariosProcesados.get(new KeyPersonaDTO(
                                        TipoIdentificacionEnum.valueOf(registroDetalle[7].toString()), registroDetalle[8].toString())))
                                .getValoresDispersion().add(BigDecimal.valueOf(Double.parseDouble(registroDetalle[11].toString())));
                    }
                }
                detalleTarjetaDTO.setIdentificadoresCondiciones(identificadoresCondiciones);
                detalleTarjetaDTO.setItemsDetalle(itemsBeneficiarios);
            }

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return detalleTarjetaDTO;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCoreLiquidacion#consultarDispersionMontoLiquidadoFallecimientoPagoEfectivo(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoPagoEfectivo(String numeroRadicacion,
            Long identificadorCondicion) {
        String firmaServicio = "ConsultasModeloCoreLiquidacion.consultarDispersionMontoLiquidadoFallecimientoPagoEfectivo(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        try {
            List<Object[]> registrosDetalle = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_POR_ADMINISTRADOR_LIQUIDACION_FALLECIMIENTO_PAGOS_EFECTIVO)
                    .setParameter("numeroRadicacion", numeroRadicacion).setParameter("condicionAdministrador", identificadorCondicion)
                    .getResultList();

            DispersionResultadoMedioPagoFallecimientoDTO detalleEfectivoDTO = new DispersionResultadoMedioPagoFallecimientoDTO();
            if (!registrosDetalle.isEmpty()) {
                detalleEfectivoDTO.setIdentificadorCondicionAdministrador(Long.parseLong(registrosDetalle.get(0)[0].toString()));
                detalleEfectivoDTO
                        .setTipoIdentificacionAdministrador(TipoIdentificacionEnum.valueOf(registrosDetalle.get(0)[2].toString()));
                detalleEfectivoDTO.setNumeroIdentificacionAdministrador(registrosDetalle.get(0)[3].toString());
                detalleEfectivoDTO.setNombreAdministrador(registrosDetalle.get(0)[4].toString());

                List<Long> identificadoresCondiciones = new ArrayList<>();
                identificadoresCondiciones.add(detalleEfectivoDTO.getIdentificadorCondicionAdministrador());

                List<ItemDispersionResultadoMedioPagoFallecimientoDTO> itemsBeneficiarios = new ArrayList<>();
                Map<KeyPersonaDTO, Integer> beneficiariosProcesados = new HashMap<>();
                Integer indicadorProcesamiento = 0;

                for (Object[] registroDetalle : registrosDetalle) {

                    if (!beneficiariosProcesados.containsKey(new KeyPersonaDTO(
                            TipoIdentificacionEnum.valueOf(registroDetalle[5].toString()), registroDetalle[6].toString()))) {
                        ItemDispersionResultadoMedioPagoFallecimientoDTO itemBeneficiario = new ItemDispersionResultadoMedioPagoFallecimientoDTO();

                        itemBeneficiario.setIdentificadorCondicion(Long.parseLong(registroDetalle[1].toString()));
                        itemBeneficiario.setTipoIdentificacionBeneficiario(TipoIdentificacionEnum.valueOf(registroDetalle[5].toString()));
                        itemBeneficiario.setNumeroIdentificacionBeneficiario(registroDetalle[6].toString());
                        itemBeneficiario.setNombreBeneficiario(registroDetalle[7].toString());

                        List<BigDecimal> valoresDispersion = new ArrayList<>();
                        valoresDispersion.add(BigDecimal.valueOf(Double.parseDouble(registroDetalle[9].toString())));
                        itemBeneficiario.setValoresDispersion(valoresDispersion);
                        
                        List<Date> fechasDispersion = new ArrayList<>();
                        fechasDispersion.add(CalendarUtils.darFormatoYYYYMMDDGuionDate(registroDetalle[8].toString()));
                        itemBeneficiario.setFechasDispersion(fechasDispersion);

                        itemsBeneficiarios.add(itemBeneficiario);
                        beneficiariosProcesados.put(new KeyPersonaDTO(TipoIdentificacionEnum.valueOf(registroDetalle[5].toString()),
                                registroDetalle[6].toString()), indicadorProcesamiento++);

                        identificadoresCondiciones.add(itemBeneficiario.getIdentificadorCondicion());
                    }
                    else {
                        itemsBeneficiarios
                                .get(beneficiariosProcesados.get(new KeyPersonaDTO(
                                        TipoIdentificacionEnum.valueOf(registroDetalle[5].toString()), registroDetalle[6].toString())))
                                .getValoresDispersion().add(BigDecimal.valueOf(Double.parseDouble(registroDetalle[9].toString())));
                        
                        itemsBeneficiarios
                        .get(beneficiariosProcesados.get(new KeyPersonaDTO(
                                TipoIdentificacionEnum.valueOf(registroDetalle[5].toString()), registroDetalle[6].toString())))
                        .getFechasDispersion().add(CalendarUtils.darFormatoYYYYMMDDGuionDate(registroDetalle[8].toString()));
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
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCoreLiquidacion#consultarDispersionMontoLiquidadoFallecimientoPagoBancoConsignaciones(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoPagoBancoConsignaciones(
            String numeroRadicacion, Long identificadorCondicion) {
        String firmaServicio = "ConsultasModeloCoreLiquidacion.consultarDispersionMontoLiquidadoFallecimientoPagoBancoConsignaciones(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        try {
            //TODO Aplicar el filtro para indicar que el medio de pago es bancos - consignaciones
            List<Object[]> registrosDetalle = entityManagerCore
                    .createNamedQuery(
                            NamedQueriesConstants.CONSULTAR_DETALLE_POR_ADMINISTRADOR_LIQUIDACION_FALLECIMIENTO_PAGOS_BANCO_CONSIGNACIONES)
                    .setParameter("numeroRadicacion", numeroRadicacion).setParameter("condicionAdministrador", identificadorCondicion)
                    .getResultList();

            DispersionResultadoMedioPagoFallecimientoDTO detalleBancosDTO = new DispersionResultadoMedioPagoFallecimientoDTO();
            if (!registrosDetalle.isEmpty()) {
                detalleBancosDTO.setIdentificadorCondicionAdministrador(Long.parseLong(registrosDetalle.get(0)[0].toString()));
                detalleBancosDTO.setTipoIdentificacionAdministrador(TipoIdentificacionEnum.valueOf(registrosDetalle.get(0)[2].toString()));
                detalleBancosDTO.setNumeroIdentificacionAdministrador(registrosDetalle.get(0)[3].toString());
                detalleBancosDTO.setNombreAdministrador(registrosDetalle.get(0)[4].toString());
                detalleBancosDTO.setBanco(registrosDetalle.get(0)[5].toString());
                detalleBancosDTO.setTipoCuenta(TipoCuentaEnum.valueOf(registrosDetalle.get(0)[6].toString()));
                detalleBancosDTO.setNumeroCuenta(registrosDetalle.get(0)[7].toString());
                detalleBancosDTO.setTitularCuenta(registrosDetalle.get(0)[8].toString());

                List<Long> identificadoresCondiciones = new ArrayList<>();
                identificadoresCondiciones.add(detalleBancosDTO.getIdentificadorCondicionAdministrador());

                List<ItemDispersionResultadoMedioPagoFallecimientoDTO> itemsBeneficiarios = new ArrayList<>();
                Map<KeyPersonaDTO, Integer> beneficiariosProcesados = new HashMap<>();
                Integer indicadorProcesamiento = 0;

                for (Object[] registroDetalle : registrosDetalle) {

                    if (!beneficiariosProcesados.containsKey(new KeyPersonaDTO(
                            TipoIdentificacionEnum.valueOf(registroDetalle[9].toString()), registroDetalle[10].toString()))) {
                        ItemDispersionResultadoMedioPagoFallecimientoDTO itemBeneficiario = new ItemDispersionResultadoMedioPagoFallecimientoDTO();

                        itemBeneficiario.setIdentificadorCondicion(Long.parseLong(registroDetalle[1].toString()));
                        itemBeneficiario.setTipoIdentificacionBeneficiario(TipoIdentificacionEnum.valueOf(registroDetalle[9].toString()));
                        itemBeneficiario.setNumeroIdentificacionBeneficiario(registroDetalle[10].toString());
                        itemBeneficiario.setNombreBeneficiario(registroDetalle[11].toString());

                        List<BigDecimal> valoresDispersion = new ArrayList<>();
                        valoresDispersion.add(BigDecimal.valueOf(Double.parseDouble(registroDetalle[13].toString())));
                        itemBeneficiario.setValoresDispersion(valoresDispersion);

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
                    }
                }
                detalleBancosDTO.setIdentificadoresCondiciones(identificadoresCondiciones);
                detalleBancosDTO.setItemsDetalle(itemsBeneficiarios);
            }

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return detalleBancosDTO;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCoreLiquidacion#consultarDispersionMontoLiquidadoFallecimientoPagoBancoJudiciales(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoPagoBancoJudiciales(
            String numeroRadicacion, Long identificadorCondicion) {
        String firmaServicio = "ConsultasModeloCoreLiquidacion.consultarDispersionMontoLiquidadoFallecimientoPagoBancoJudiciales(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        //TODO implementar este servicio

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return null;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCoreLiquidacion#consultarDispersionMontoLiquidadoFallecimientoDescuentos(java.lang.String,
     *      java.lang.Long)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DispersionResultadoDescuentosFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoDescuentos(String numeroRadicacion,
            Long identificadorCondicion) {
        String firmaServicio = "ConsultasModeloCoreLiquidacion.consultarDispersionMontoLiquidadoFallecimientoDescuentos(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        DispersionResultadoDescuentosFallecimientoDTO dispersionDescuentosDTO = new DispersionResultadoDescuentosFallecimientoDTO();
        try {
            List<Object[]> registrosDetalle = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_POR_ADMINISTRADOR_LIQUIDACION_FALLECIMIENTO_DESCUENTOS)
                    .setParameter("numeroRadicacion", numeroRadicacion).setParameter("identificadorCondicion", identificadorCondicion)
                    .getResultList();

            List<DispersionResultadoMedioPagoFallecimientoDTO> detallesDescuentosDTO = new ArrayList<>();
            List<Long> identificadoresCondiciones = new ArrayList<>();
            Map<String, Integer> entidadesProcesadas = new HashMap<>();
            Map<KeyPersonaEntidadDTO, Integer> beneficiariosProcesados = new HashMap<>();
            Integer indicadorProcesamiento = 0;

            if (!registrosDetalle.isEmpty()) {
                LocalDate periodoPrimeraCuota = consultarPeriodoPrimerCuota(numeroRadicacion, identificadorCondicion);

                //Se toma la información del administrador de subsidio
                DispersionResultadoMedioPagoFallecimientoDTO infoAdminDTO = new DispersionResultadoMedioPagoFallecimientoDTO();
                infoAdminDTO.setTipoIdentificacionAdministrador(TipoIdentificacionEnum.valueOf(registrosDetalle.get(0)[2].toString()));
                infoAdminDTO.setNumeroIdentificacionAdministrador(registrosDetalle.get(0)[3].toString());
                infoAdminDTO.setNombreAdministrador(registrosDetalle.get(0)[4].toString());
                //Condición del administrador
                identificadoresCondiciones.add(identificadorCondicion);
                infoAdminDTO.setIdentificadorCondicionAdministrador(identificadorCondicion);

                for (Object[] registroDetalle : registrosDetalle) {

                    if (!entidadesProcesadas.containsKey(registroDetalle[5].toString())) {
                        //Se evalua el caso en el que la entidad de descuento no se ha procesado
                        DispersionResultadoMedioPagoFallecimientoDTO dispersionDescuentoDTO = infoAdminDTO.clone();

                        dispersionDescuentoDTO.setTipoIdentificacionEntidadDescuento(
                                registroDetalle[6] == null ? null : TipoIdentificacionEnum.valueOf(registroDetalle[6].toString()));
                        dispersionDescuentoDTO
                                .setNumeroIdentificacionEntidadDescuento(registroDetalle[7] == null ? null : registroDetalle[7].toString());
                        dispersionDescuentoDTO.setNombreEntidadDescuento(registroDetalle[8].toString());

                        List<ItemDispersionResultadoMedioPagoFallecimientoDTO> descuentosBeneficiarios = new ArrayList<>();
                        ItemDispersionResultadoMedioPagoFallecimientoDTO descuentosBeneficiario = new ItemDispersionResultadoMedioPagoFallecimientoDTO();

                        descuentosBeneficiario
                                .setTipoIdentificacionBeneficiario(TipoIdentificacionEnum.valueOf(registroDetalle[9].toString()));
                        descuentosBeneficiario.setNumeroIdentificacionBeneficiario(registroDetalle[10].toString());
                        descuentosBeneficiario.setNombreBeneficiario(registroDetalle[11].toString());
                        //Condición del beneficiario
                        descuentosBeneficiario.setIdentificadorCondicion(Long.parseLong(registroDetalle[1].toString()));
                        identificadoresCondiciones.add(descuentosBeneficiario.getIdentificadorCondicion());

                        //Se crea la lista de descuentos para el beneficiario
                        Supplier<BigDecimal> supplier = () -> new BigDecimal(0);
                        List<BigDecimal> descuentos = Stream.generate(supplier).limit(12).collect(Collectors.toList());
                        descuentosBeneficiario.setValoresDispersion(descuentos);

                        //Se define el periodo de descuento
                        LocalDate periodoDescuento = LocalDate.parse(registroDetalle[12].toString());
                        Integer mesDescuento = Period.between(periodoPrimeraCuota, periodoDescuento).getMonths();
                        descuentosBeneficiario.getValoresDispersion().set(mesDescuento,
                                BigDecimal.valueOf(Double.parseDouble(registroDetalle[13].toString())));

                        //Se añade el beneficiario con descuento a la lista
                        descuentosBeneficiarios.add(descuentosBeneficiario);
                        dispersionDescuentoDTO.setItemsDetalle(descuentosBeneficiarios);

                        detallesDescuentosDTO.add(dispersionDescuentoDTO);
                        entidadesProcesadas.put(registroDetalle[5].toString(), indicadorProcesamiento++);
                        beneficiariosProcesados.put(new KeyPersonaEntidadDTO(descuentosBeneficiario.getTipoIdentificacionBeneficiario(),
                                descuentosBeneficiario.getNumeroIdentificacionBeneficiario(), registroDetalle[5].toString()), 0);

                    }
                    else {
                        //Se evalua el caso en el que la entidad de descuento ya se proceso
                        if (!beneficiariosProcesados
                                .containsKey(new KeyPersonaEntidadDTO(TipoIdentificacionEnum.valueOf(registroDetalle[9].toString()),
                                        registroDetalle[10].toString(), registroDetalle[5].toString()))) {
                            //Se evalua el caso en el que no se ha procesado un descuento para el beneficiario por la entidad
                            //Se toma el código de la entidad y se saca la posición de entidades procesadas
                            ItemDispersionResultadoMedioPagoFallecimientoDTO descuentosBeneficiario = new ItemDispersionResultadoMedioPagoFallecimientoDTO();

                            descuentosBeneficiario
                                    .setTipoIdentificacionBeneficiario(TipoIdentificacionEnum.valueOf(registroDetalle[9].toString()));
                            descuentosBeneficiario.setNumeroIdentificacionBeneficiario(registroDetalle[10].toString());
                            descuentosBeneficiario.setNombreBeneficiario(registroDetalle[11].toString());
                            //Condición del beneficiario
                            descuentosBeneficiario.setIdentificadorCondicion(Long.parseLong(registroDetalle[1].toString()));
                            identificadoresCondiciones.add(descuentosBeneficiario.getIdentificadorCondicion());

                            //Se crea la lista de descuentos para el beneficiario
                            Supplier<BigDecimal> supplier = () -> new BigDecimal(0);
                            List<BigDecimal> descuentos = Stream.generate(supplier).limit(12).collect(Collectors.toList());
                            descuentosBeneficiario.setValoresDispersion(descuentos);

                            //Se define el periodo de descuento
                            LocalDate periodoDescuento = LocalDate.parse(registroDetalle[12].toString());
                            Integer mesDescuento = Period.between(periodoPrimeraCuota, periodoDescuento).getMonths();
                            descuentosBeneficiario.getValoresDispersion().set(mesDescuento,
                                    BigDecimal.valueOf(Double.parseDouble(registroDetalle[13].toString())));

                            //Se añade la información de descuento para el beneficiario a la lista de la entidad correspondiente
                            Integer posicionBeneficiario = detallesDescuentosDTO.get(entidadesProcesadas.get(registroDetalle[5].toString()))
                                    .getItemsDetalle().size();
                            detallesDescuentosDTO.get(entidadesProcesadas.get(registroDetalle[5].toString())).getItemsDetalle()
                                    .add(descuentosBeneficiario);
                            beneficiariosProcesados.put(
                                    new KeyPersonaEntidadDTO(descuentosBeneficiario.getTipoIdentificacionBeneficiario(),
                                            descuentosBeneficiario.getNumeroIdentificacionBeneficiario(), registroDetalle[5].toString()),
                                    posicionBeneficiario);
                        }
                        else {
                            //Se evalua el caso para el que ya se proceso un descuento para el beneficiario en la entidad

                            //Se toma el código de la entidad y se saca la posición de entidades procesadas, 
                            //luego se toma el KeyPersonaEntidadDTO y se saca la posición de beneficiarios procesados
                            LocalDate periodoDescuento = LocalDate.parse(registroDetalle[12].toString());
                            Integer mesDescuento = Period.between(periodoPrimeraCuota, periodoDescuento).getMonths();

                            //Se obtiene el valor de descuento actual ya que se puede presentar el caso en el que la persona tenga dos descuentos por
                            //la misma entidad, en el mismo periodo

                            BigDecimal descuentoActual = detallesDescuentosDTO.get(entidadesProcesadas.get(registroDetalle[5].toString()))
                                    .getItemsDetalle()
                                    .get(beneficiariosProcesados
                                            .get(new KeyPersonaEntidadDTO(TipoIdentificacionEnum.valueOf(registroDetalle[9].toString()),
                                                    registroDetalle[10].toString(), registroDetalle[5].toString())))
                                    .getValoresDispersion().get(mesDescuento);

                            detallesDescuentosDTO.get(entidadesProcesadas.get(registroDetalle[5].toString())).getItemsDetalle()
                                    .get(beneficiariosProcesados
                                            .get(new KeyPersonaEntidadDTO(TipoIdentificacionEnum.valueOf(registroDetalle[9].toString()),
                                                    registroDetalle[10].toString(), registroDetalle[5].toString())))
                                    .getValoresDispersion().set(mesDescuento,
                                            descuentoActual.add(BigDecimal.valueOf(Double.parseDouble(registroDetalle[13].toString()))));
                        }
                    }
                }
                dispersionDescuentosDTO.setDetallesDescuentosDTO(detallesDescuentosDTO);
                dispersionDescuentosDTO.setIdentificadoresCondiciones(identificadoresCondiciones);
            }

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return dispersionDescuentosDTO;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /***
     * * <b>Descripción:</b>Método que permite obtener la fecha de la primer cuota para la liquidación de fallecimiento
     * @author <a>Roy López Cardona</a>
     * 
     * @param numeroRadicacion
     *        <code>String</code>
     *        Valor del número de radicación
     * 
     * @param identificadorCondicion
     *        <code>Long</code>
     *        Valor del identificador de condición del administrador de subsidio
     * 
     * @return <code>Date</code>
     *         Periodo de la primera cuota para la liquidación por fallecimiento
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private LocalDate consultarPeriodoPrimerCuota(String numeroRadicacion, Long identificadorCondicion) {
        String firmaServicio = "ConsultasModeloCoreLiquidacion.consultarPeriodoPrimerCuota(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        try {
            LocalDate periodo = LocalDate.parse(
                    entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PRIMER_PERIODO_CUOTAS_LIQUIDACION_FALLECIMIENTO)
                            .setParameter("numeroRadicacion", numeroRadicacion)
                            .setParameter("condicionAdministrador", identificadorCondicion).getSingleResult().toString());

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return periodo;
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /**
     * <b>Descripción:</b>Método que se encarga de realizar la conversión de los medios de pagos a su correspondiente valor en cadena
     * @author <a>Roy López Cardona</a>
     * 
     * @param mediosDePago
     *        <code>List</code>
     *        Lista de medios de pago
     * 
     * @return <code>List</code>
     *         Lista de medios de pago como cadenas
     */
    private List<String> convertirMediosDePagoCadenas(List<TipoMedioDePagoEnum> mediosDePago) {
        List<String> cadenas = new ArrayList<>();
        for (TipoMedioDePagoEnum medioPago : mediosDePago) {
            cadenas.add(medioPago.toString());
        }
        return cadenas;
    }
    
    /**
     * <b>Descripción:</b>Método que permite obtener los totales asociados a la dispersión para el medio de pago banco - pagos judiciales
     * @author mosorio
     * 
     * @param numeroRadicacion
     *        <code>Long</code>
     *        Valor del número de radicación
     * 
     * @return <code>DispersionResumenMedioPagoFallecimientoDTO</code>
     *         Información que representa los totales para el medio de pago banco - Judiciales
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private DispersionResumenMedioPagoFallecimientoDTO consultarPagosBancoJudicialesFallecimiento(String numeroRadicacion) {
        String firmaServicio = "ConsultasModeloCoreLiquidacion.consultarPagosBancoJudicialesFallecimiento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        DispersionResumenMedioPagoFallecimientoDTO dispersionBancoConsignacionesDTO = new DispersionResumenMedioPagoFallecimientoDTO();
        dispersionBancoConsignacionesDTO = consultarDispersionResumenMedioDePago(dispersionBancoConsignacionesDTO, numeroRadicacion,
                TipoMedioDePagoEnum.TRANSFERENCIA,Boolean.TRUE);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return dispersionBancoConsignacionesDTO;
        
    }

}
