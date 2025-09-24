package com.asopagos.cartera.business.interfaces;

import com.asopagos.cartera.dto.AportanteAccionCobroDTO;
import com.asopagos.cartera.dto.FiltrosParametrizacionDTO;
import com.asopagos.dto.modelo.CarteraDependienteModeloDTO;
import com.asopagos.dto.modelo.ParametrizacionCriteriosGestionCobroModeloDTO;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ejb.Local;
import javax.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.util.List;

/**
 * <b>Descripcion:</b> Interfaz que define los métodos para consultas en la BD de core<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
@Local
public interface IConsultasModeloCore {

    /**
     * Invocación del SP para la actualización de la deuda presunta de la cartera
     */
    public void actualizarDeudaPresuntaCartera(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                               String periodoEvaluacion, TipoSolicitanteMovimientoAporteEnum tipoAportante);

    /**
     * Invocación del SP para la asignación de acción de cobro
     */
    public List<AportanteAccionCobroDTO> asignarAccionCobro(TipoAccionCobroEnum accionCobro);

    /**
     * Invocación del SP para la creación de carteras
     */
    public void crearRegistroCartera();

    /**
     * Invocación del SP para la consulta de secuencias para Cartera
     */
    public List<Long> obtenerValoresSecuencia(String nombreSecuencia, Integer cantidad);

    /**
     * @param carteraDependienteDTO
     */
    public void guardarCarteraDependiente(CarteraDependienteModeloDTO carteraDependienteDTO);

    /**
     * Método para la consulta de detalle de cartera por línea de cobro
     */
    public List<CarteraDependienteModeloDTO> consultarCarteraCotizantesAportanteLC(TipoIdentificacionEnum tipoIdentificacion,
                                                                                   String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante, Long periodo, Long idCartera,
                                                                                   TipoLineaCobroEnum lineaCobro);

    /**
     * @param parametrizacion
     * @param filtrosParametrizacion
     * @return
     */
    public StoredProcedureQuery consultarAportantesGestionCobroEmpleador(ParametrizacionCriteriosGestionCobroModeloDTO parametrizacion,
                                                                         FiltrosParametrizacionDTO filtrosParametrizacion);

    /**
     * @param tipoSolicitante
     * @param parametrizacion
     * @param filtrosParametrizacion
     * @return
     */
    public StoredProcedureQuery consultarAportantesGestionCobroPersona(TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                                                       ParametrizacionCriteriosGestionCobroModeloDTO parametrizacion, FiltrosParametrizacionDTO filtrosParametrizacion);

    /**
     * Consulta los aportantes que cumplen las condiciones de desafiliación
     *
     * @param tipoSolicitante Tipo de solicitante
     * @return Resultado d ela consulta
     */
    public StoredProcedureQuery consultarAportantesDesafiliacion(TipoSolicitanteMovimientoAporteEnum tipoSolicitante);

    /**
     * Servicio que se encarga de consultar la parametrización de gestion de cobro
     *
     * @param lineaCobro linea de cobro a buscar.
     * @return parametrización de gestión de cobro encontrada.
     */
    public List<ParametrizacionCriteriosGestionCobroModeloDTO> consultarCriteriosGestionCobroLinea(List<TipoLineaCobroEnum> lineasCobro);

    /**
     * Método que se encarga de actualizar los registros que se encuentran en la linea de cobro 3 cuando se modifica el estado de registro
     * del aporte de no ok a ok
     */
    public void actualizarRegistrosCarteraOK();

    /**
     * Invocación del SP para la consulta de la deuda presenta en cartera
     */
    public BigDecimal obtenerDeudaPresunta(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                           TipoSolicitanteMovimientoAporteEnum tipoAportante, String periodoEvaluacion);

    /**
     * Envia comunicado en la accion de cobro H2 F1
     */
    public List<AportanteAccionCobroDTO> enviarComunicadoH2C2ToF1C6(TipoAccionCobroEnum accionCobro) throws JsonProcessingException;

    /**
     * Envia comunicado en la accion de cobro Expulsion H2 F1
     */
    public List<AportanteAccionCobroDTO> enviarComunicadoExpulsionH2C2ToF1C6(TipoAccionCobroEnum accionCobro) throws JsonProcessingException;

    public List<AportanteAccionCobroDTO> obtenerAportantesParaExpulsionPorIds(TipoAccionCobroEnum accionCobro, List<Long> idPersonasAProcesar) throws JsonProcessingException;


    public String consultarActividadCarIdNumeroIdentificacion(Long carId, String perNumeroIdentificacion);

    /**
     * Método que se encarga de actualizar los registros que se encuentran en la linea de cobro 3 cuando se modifica el estado de registro
     * del aporte de no ok a ok
     */

}
