package com.asopagos.cartera.composite.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import com.asopagos.cartera.clients.ConsultarParametrizacionGestionCobro;
import com.asopagos.cartera.composite.ejb.CarteraCompositeBusiness;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.ParametrizacionGestionCobroModeloDTO;
import com.asopagos.enumeraciones.cartera.MetodoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.MetodoEnvioComunicadoEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoParametrizacionGestionCobroEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@Stateless
@SuppressWarnings("unchecked")
public class ParametrizarDestinatarios implements Serializable {

    /**
     * Serial version ID
     */
    private static final long serialVersionUID = 8004486306414865580L;

    /**
     * Referencia al logger.
     */
    private final ILogger logger = LogManager.getLogger(CarteraCompositeBusiness.class);

    /**
     * Se consulta la parametrización de gestión de cobro para el proceso de
     * Cartera
     * 
     * @param tipoTx
     *        Tipo de transacción relacionado al porceso de Cartera
     * @return Retorna ParametrizacionGestionCobroModeloDTO
     */
    public ParametrizacionGestionCobroModeloDTO consultarParametrizacion(TipoTransaccionEnum tipoTx) {
        logger.debug("Incio del método consultarParametrizacion(TipoTransaccionEnum tipoTx)");
        try {
            List<Object> accionesCobro = new ArrayList<>();

            if (TipoTransaccionEnum.ACCION_COBRO_1A_ELECTRONICO.equals(tipoTx)
                    || TipoTransaccionEnum.ACCION_COBRO_2A_ELECTRONICO.equals(tipoTx)) {
                accionesCobro = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_A);
                if (TipoTransaccionEnum.ACCION_COBRO_1A_ELECTRONICO.equals(tipoTx)) {
                    for (Object accionCobro : accionesCobro) {
                        Map<String, Object> accion = (Map<String, Object>) accionCobro;
                        if (MetodoAccionCobroEnum.METODO_1.equals(MetodoAccionCobroEnum.valueOf(accion.get("metodo").toString()))) {
                            return construirParametrizacion(accion);
                        }
                    }
                }
                else {
                    for (Object accionCobro : accionesCobro) {
                        Map<String, Object> accion = (Map<String, Object>) accionCobro;
                        if (MetodoAccionCobroEnum.METODO_2.equals(MetodoAccionCobroEnum.valueOf(accion.get("metodo").toString()))) {
                            return construirParametrizacion(accion);
                        }
                    }
                }
                logger.debug("Finaliza del método consultarParametrizacion(TipoTransaccionEnum tipoTx)");
                return null;
            }
            else if (TipoTransaccionEnum.ACCION_COBRO_1B_ELECTRONICO.equals(tipoTx)
                    || TipoTransaccionEnum.ACCION_COBRO_2B_ELECTRONICO.equals(tipoTx)) {
                accionesCobro = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_B);
                if (TipoTransaccionEnum.ACCION_COBRO_1B_ELECTRONICO.equals(tipoTx)) {
                    for (Object accionCobro : accionesCobro) {
                        Map<String, Object> accion = (Map<String, Object>) accionCobro;
                        if (MetodoAccionCobroEnum.METODO_1.equals(MetodoAccionCobroEnum.valueOf(accion.get("metodo").toString()))) {
                            return construirParametrizacion(accion);
                        }
                    }
                }
                else {
                    for (Object accionCobro : accionesCobro) {
                        Map<String, Object> accion = (Map<String, Object>) accionCobro;
                        if (MetodoAccionCobroEnum.METODO_2.equals(MetodoAccionCobroEnum.valueOf(accion.get("metodo").toString()))) {
                            return construirParametrizacion(accion);
                        }
                    }
                }
                logger.debug("Finaliza del método consultarParametrizacion(TipoTransaccionEnum tipoTx)");
                return null;
            }
            else if (TipoTransaccionEnum.ACCION_COBRO_1C_ELECTRONICO.equals(tipoTx)) {
                accionesCobro = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_1C);
                Map<String, Object> accion = (Map<String, Object>) accionesCobro.get(0);
                logger.debug("Finaliza del método consultarParametrizacion(TipoTransaccionEnum tipoTx)");
                return construirParametrizacion(accion);
            }
            else if (TipoTransaccionEnum.ACCION_COBRO_1D_ELECTRONICO.equals(tipoTx)) {
                accionesCobro = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_1D);
                Map<String, Object> accion = (Map<String, Object>) accionesCobro.get(0);
                logger.debug("Finaliza del método consultarParametrizacion(TipoTransaccionEnum tipoTx)");
                return construirParametrizacion(accion);
            }
            else if (TipoTransaccionEnum.ACCION_COBRO_1E_ELECTRONICO.equals(tipoTx)) {
                accionesCobro = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_1E);
                Map<String, Object> accion = (Map<String, Object>) accionesCobro.get(0);
                logger.debug("Finaliza del método consultarParametrizacion(TipoTransaccionEnum tipoTx)");
                return construirParametrizacion(accion);
            }
            else if (TipoTransaccionEnum.ACCION_COBRO_1F_ELECTRONICO.equals(tipoTx)) {
                accionesCobro = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_1F);
                Map<String, Object> accion = (Map<String, Object>) accionesCobro.get(0);
                logger.debug("Finaliza del método consultarParametrizacion(TipoTransaccionEnum tipoTx)");
                return construirParametrizacion(accion);
            }
            else if (TipoTransaccionEnum.ACCION_COBRO_2C_ELECTRONICO.equals(tipoTx)) {
                accionesCobro = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2C);
                Map<String, Object> accion = (Map<String, Object>) accionesCobro.get(0);
                logger.debug("Finaliza del método consultarParametrizacion(TipoTransaccionEnum tipoTx)");
                return construirParametrizacion(accion);
            }
            else if (TipoTransaccionEnum.ACCION_COBRO_2D_ELECTRONICO.equals(tipoTx)) {
                accionesCobro = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2D);
                Map<String, Object> accion = (Map<String, Object>) accionesCobro.get(0);
                logger.debug("Finaliza del método consultarParametrizacion(TipoTransaccionEnum tipoTx)");
                return construirParametrizacion(accion);
            }
            else if (TipoTransaccionEnum.ACCION_COBRO_2F_ELECTRONICO.equals(tipoTx)) {
                accionesCobro = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2F);
                Map<String, Object> accion = (Map<String, Object>) accionesCobro.get(0);
                logger.debug("Finaliza del método consultarParametrizacion(TipoTransaccionEnum tipoTx)");
                return construirParametrizacion(accion);
            }
            else if (TipoTransaccionEnum.ACCION_COBRO_2G_ELECTRONICO.equals(tipoTx)) {
                accionesCobro = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2G);
                Map<String, Object> accion = (Map<String, Object>) accionesCobro.get(0);
                logger.debug("Finaliza del método consultarParametrizacion(TipoTransaccionEnum tipoTx)");
                return construirParametrizacion(accion);
            }
            else if (TipoTransaccionEnum.ACCION_COBRO_2H_ELECTRONICO.equals(tipoTx)) {
                accionesCobro = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2H);
                Map<String, Object> accion = (Map<String, Object>) accionesCobro.get(0);
                logger.debug("Finaliza del método consultarParametrizacion(TipoTransaccionEnum tipoTx)");
                return construirParametrizacion(accion);
            }
            else if (TipoTransaccionEnum.ACCION_COBRO_LC2A_ELECTRONICO.equals(tipoTx)) {
                accionesCobro = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.LINEA_COBRO);

                for (Object registro : accionesCobro) {
                    Map<String, Object> accion = (Map<String, Object>) registro;

                    if (accion.containsKey("tipoLineaCobro") && accion.get("tipoLineaCobro").equals(TipoLineaCobroEnum.LC2.name())) {
                        logger.debug("Finaliza del método consultarParametrizacion(TipoTransaccionEnum tipoTx)");
                        return construirParametrizacion(accion);
                    }
                }
            }
            else if (TipoTransaccionEnum.ACCION_COBRO_LC3A_ELECTRONICO.equals(tipoTx)) {
                accionesCobro = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.LINEA_COBRO);

                for (Object registro : accionesCobro) {
                    Map<String, Object> accion = (Map<String, Object>) registro;

                    if (accion.containsKey("tipoLineaCobro") && accion.get("tipoLineaCobro").equals(TipoLineaCobroEnum.LC3.name())) {
                        logger.debug("Finaliza del método consultarParametrizacion(TipoTransaccionEnum tipoTx)");
                        return construirParametrizacion(accion);
                    }
                }
            }
            else if (TipoTransaccionEnum.ACCION_COBRO_LC4A_ELECTRONICO.equals(tipoTx)) {
                accionesCobro = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.LINEA_COBRO);

                for (Object registro : accionesCobro) {
                    Map<String, Object> accion = (Map<String, Object>) registro;

                    if (accion.containsKey("tipoLineaCobro") && accion.get("tipoLineaCobro").equals(TipoLineaCobroEnum.LC4.name())) {
                        logger.debug("Finaliza del método consultarParametrizacion(TipoTransaccionEnum tipoTx)");
                        return construirParametrizacion(accion);
                    }
                }
            }
            else if (TipoTransaccionEnum.ACCION_COBRO_LC5A_ELECTRONICO.equals(tipoTx)) {
                accionesCobro = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.LINEA_COBRO);

                for (Object registro : accionesCobro) {
                    Map<String, Object> accion = (Map<String, Object>) registro;

                    if (accion.containsKey("tipoLineaCobro") && accion.get("tipoLineaCobro").equals(TipoLineaCobroEnum.LC5.name())) {
                        logger.debug("Finaliza del método consultarParametrizacion(TipoTransaccionEnum tipoTx)");
                        return construirParametrizacion(accion);
                    }
                }
            }

            logger.debug("Finaliza del método consultarParametrizacion(TipoTransaccionEnum tipoTx)");
            return null;
        } catch (Exception e) {
            logger.debug("Finaliza del método consultarParametrizacion(TipoTransaccionEnum tipoTx):Error técnico insperado");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Método privado que construye la parametrización dada una accion de cobro
     * @param accion
     *        Accion de cobro definida en la parametrizacion de Gestión de cobro
     * @return ParametrizacionGestionCobroModeloDTO
     */
    private ParametrizacionGestionCobroModeloDTO construirParametrizacion(Map<String, Object> accion) {
        ParametrizacionGestionCobroModeloDTO gestionCobroDTO = new ParametrizacionGestionCobroModeloDTO();
        Integer idParametrizacionGestionCobro = (Integer) accion.get("idParametrizacionGestionCobro");
        gestionCobroDTO.setIdParametrizacionGestionCobro(idParametrizacionGestionCobro.longValue());
        gestionCobroDTO.setCorrespondenciaFisico((Boolean) accion.get("correspondenciaFisico"));
        gestionCobroDTO.setOficinaPrincipalFisico((Boolean) accion.get("oficinaPrincipalFisico"));
        gestionCobroDTO.setNotificacionJudicialFisico((Boolean) accion.get("notificacionJudicialFisico"));
        gestionCobroDTO.setOficinaPrincipalElectronico((Boolean) accion.get("oficinaPrincipalElectronico"));
        gestionCobroDTO.setRepresentanteLegalElectronico((Boolean) accion.get("representanteLegalElectronico"));
        gestionCobroDTO.setResponsableAportesElectronico((Boolean) accion.get("responsableAportesElectronico"));
        gestionCobroDTO.setMetodoEnvioComunicado(MetodoEnvioComunicadoEnum.valueOf(accion.get("metodoEnvioComunicado").toString()));
        gestionCobroDTO.setTipoParametrizacion(TipoParametrizacionGestionCobroEnum.valueOf(accion.get("tipoParametrizacion").toString()));
        return gestionCobroDTO;
    }

    /**
     * Método que invoca el servicio de consulta de parametrización de acciones de cobro
     * @param tipoParametrizacion
     *        Tipo de parametrización
     * @return Lista de registros de parametrización de la acción de cobro
     */
    private List<Object> consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum tipoParametrizacion) {
        logger.debug("Inicio de método consultarParametrizacionGestionCobro");
        ConsultarParametrizacionGestionCobro service = new ConsultarParametrizacionGestionCobro(tipoParametrizacion);
        service.execute();
        logger.debug("Fin de método consultarParametrizacionGestionCobro");
        return service.getResult();
    }
}
