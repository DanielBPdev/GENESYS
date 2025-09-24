package com.asopagos.bandejainconsistencias.interfaces;

import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;
import com.asopagos.bandejainconsistencias.dto.BusquedaPorPersonaRespuestaDTO;
import com.asopagos.bandejainconsistencias.dto.CriteriosDTO;
import com.asopagos.dto.InconsistenciaRegistroAporteDTO;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.dto.pila.RespuestaConsultaEmpleadorDTO;
import com.asopagos.entidades.pila.staging.RegistroDetallado;
import com.asopagos.entidades.pila.staging.RegistroGeneral;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de información en
 * el modelo de datos de Staging <br/>
 * <b>Módulo:</b> Asopagos - HU-211-401 y HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Robinson A. Arboleda</a>
 */
@Local
public interface IConsultasModeloStaging {

    /**
     * Método para la actualización de un registro detallado
     * @param registroDetallado
     *        <code>RegistroDetallado</code>
     *        Registro detallado a actualizar
     */
    public RegistroDetallado actualizarRegistroDetallado(RegistroDetallado registroDetallado);

    /**
     * Metodo para buscar por personas con criterios de busqueda variables
     * @param CriteriosPersonaDTO
     *        DTO con la lista de criterios de busqueda
     */
    public List<BusquedaPorPersonaRespuestaDTO> buscarPorPersonaCriterios(CriteriosDTO criterios, UriInfo uri,
            HttpServletResponse response);

    /**
     * Metodo para buscar por aportante con criterios de busqueda variables
     * @param criterios
     *        DTO con la lista de criterios de busqueda
     * @param uri
     * @param response
     * @return
     */
    public List<BusquedaPorPersonaRespuestaDTO> buscarPorAportanteCriterios(CriteriosDTO criterios, UriInfo uri,
            HttpServletResponse response);

    /**
     * Capacidad que permite la consulta de las planillas con alguna
     * inconsistencia en el aporte para ser gestionadas en la bandeja de aportes
     * 
     * @return <code>Long</code> El total de archivos de planillas con
     *         inconsistencias
     */
    public Integer contarPlanillasConInconsistenciasPorGestionar();

    /**
     * Capacidad que permite la consulta de los registros de aporte de las
     * planillas con alguna inconsistencia para ser gestionadas en la bandeja de
     * aportes
     * 
     * @param tipoIdentificacion
     *        <code>TipoIdentificacionEnum</code> El tipo de identificacion
     *        del aportante a consultar
     * @param numeroIdentificacionAportante
     *        <code>String</code> El numero de identificacion del aportante
     *        a consultar
     * @param digitoVerificacionAportante
     *        <code>Short</code> El digito de verificacion del aportante
     * @param fechaInicio
     *        <code>Long</code> la fecha de inicio del procesamiento del
     *        aporte de la planilla
     * @param fechaFin
     *        <code>Long</code> la fecha de fin del procesamiento del aporte
     *        de la planilla
     * 
     * @return List<code>InconsistenciaRegistroAporteDTO</code> La lista de los
     *         registros con inconsistencias de archivos de planilla
     */
    public List<InconsistenciaRegistroAporteDTO> consultarPlanillasPorGestionarConInconsistenciasValidacion(
            TipoIdentificacionEnum tipoIdentificacionAportante, String numeroIdentificacionAportante, Short digitoVerificacionAportante,
            Long fechaInicio, Long fechaFin);

    /**
     * Capacidad que permite la consulta de los registros de aporte de las
     * planillas con alguna inconsistencia para ser aprobadas en la bandeja de
     * aportes
     * 
     * @param tipoIdentificacion
     *        <code>TipoIdentificacionEnum</code> El tipo de identificacion
     *        del aportante a consultar
     * @param numeroIdentificacionAportante
     *        <code>String</code> El numero de identificacion del aportante
     *        a consultar
     * @param digitoVerificacionAportante
     *        <code>Short</code> El digito de verificacion del aportante
     * @param fechaInicio
     *        <code>Long</code> la fecha de inicio del procesamiento del
     *        aporte de la planilla
     * @param fechaFin
     *        <code>Long</code> la fecha de fin del procesamiento del aporte
     *        de la planilla
     * 
     * @return List<code>InconsistenciaRegistroAporteDTO</code> La lista de los
     *         registros con inconsistencias de archivos de planilla
     */
    public List<InconsistenciaRegistroAporteDTO> consultarPlanillasPorAprobarConInconsistenciasValidacion(
            TipoIdentificacionEnum tipoIdentificacionAportante, String numeroIdentificacionAportante, Short digitoVerificacionAportante,
            Long fechaInicio, Long fechaFin);

    public RegistroDetallado consultarRegistroAporteConInconsistencia(Long idRegistroAporteDetallado);

    /**
     * 389 Metodo para la busqueda del control de resultados dde una persona
     * <code>SolicitudCambioNumIdentAportante</code>
     * 
     * @param tipoDocumento
     *        <code>TipoIdentificacionEnum</code> contiene la identificacion
     *        de la persona
     * @param numeroIdentificacion
     *        <code>String</code> Contiene el valor de la identificacion del
     *        aportante
     * @param numeroPlanilla
     *        <code>Long</code> Contiene el numero de planilla por el que se
     *        desea consultar
     * @param periodo
     *        <code>Long</code> Contiene la fecha por la que se desea
     *        consultar
     * @return List<code>RespuestaConsultaEmpleadorDTO</code> DTO que contiene
     *         como cabecera la identificacion del aportante y una lista de
     *         cotizaciones
     */
    public List<RespuestaConsultaEmpleadorDTO> buscarControlResultadosPersona(TipoIdentificacionEnum tipoDocumento, String idAportante,
            Long numeroPlanilla, Long periodo, UserDTO userDTO);

    /**
     * 389 Metodo para la busqueda del control de resultados de parte del
     * aportante
     * 
     * @param tipoDocumento
     *        <code>TipoIdentificacionEnum</code> contiene la identificacion
     *        de la persona
     * @param numeroIdentificacion
     *        <code>String</code> Contiene el valor de la identificacion del
     *        aportante
     * @param numeroPlanilla
     *        <code>Long</code> Contiene el numero de planilla por el que se
     *        desea consultar
     * @param periodo
     *        <code>Long</code> Contiene la fecha por la que se desea
     *        consultar
     * @return List<code>RespuestaConsultaEmpleadorDTO</code> DTO que contiene
     *         como cabecera la identificacion del aportante y una lista de
     *         cotizaciones
     */
    public List<RespuestaConsultaEmpleadorDTO> buscarControlResultadosEmpleador(TipoIdentificacionEnum tipoDocumento, String idAportante,
            Long numeroPlanilla, Long periodo, UserDTO userDTO);

    /**
     * @param idRegistroGeneral
     * @return
     */
    public List<RegistroDetallado> consultarRegistroDetalladoxRegistroGeneral(Long idRegistroGeneral);

    /**
     * @param idRegistroGeneral
     * @return
     */
    public RegistroGeneral consultarRegistroGeneralxId(Long idRegistroGeneral);

    /**
     * @param registroGeneral
     */
    public void actualizarRegistroGeneral(RegistroGeneral registroGeneral);

    /**
     * @param criterios
     * @param uri
     * @param response
     * @return
     */
    public List<RegistroDetalladoModeloDTO> detalleAportanteCriterios(CriteriosDTO criterios, UriInfo uri, HttpServletResponse response);

    /**
     * @param idsRegistrosDetallados
     * @param usuarioAprobador
     */
    public void aprobarRegistrosDetalladosPorId(List<Long> idsRegistrosDetallados, String usuarioAprobador);

    /**
     * @param idsRegistrosGenerales
     * @param idTransaccion
     * @param esSimulado
     */
    public void asignarIdTransaccionYEstadoBase(List<Long> idsRegistrosGenerales, Long idTransaccion, BloqueValidacionEnum bloque,
            Boolean esSimulado);

    /**
     * @param idsRegistroGeneral
     * @param esReproceso
     * @param esSimulado
     * @return
     */
    public List<RegistroGeneralModeloDTO> consultarRegistrosGeneralesPorListaId(List<Long> idsRegistroGeneral, Boolean esReproceso,
            Boolean esSimulado);

	/**
	 * @param idTransaccion
	 */
	public void recalcularEstadoRegistroGeneral(Long idTransaccion);
	
	/**
	 * Consulta el registro general por el registro de control
	 * 
	 * @param idRegistroControl
	 * @return
	 */
	public RegistroGeneral consultarRegistroGeneralxRegistroControl(Long idRegistroControl);

}
