package com.asopagos.bandejainconsistencias.interfaces;

import java.util.List;

import javax.ejb.Local;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;

import com.asopagos.bandejainconsistencias.dto.BandejaEmpleadorCeroTrabajadoresDTO;
import com.asopagos.bandejainconsistencias.dto.CriteriosDTO;
import com.asopagos.dto.EmpAporPendientesPorAfiliarDTO;
import com.asopagos.dto.aportes.ActualizacionDatosEmpleadorModeloDTO;
import com.asopagos.dto.modelo.DepartamentoModeloDTO;
import com.asopagos.dto.modelo.ExcepcionNovedadPilaModeloDTO;
import com.asopagos.entidades.ccf.aportes.PilaEstadoTransitorio;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de información en
 * el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU-211-401 y HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
@Local
public interface IConsultasModeloCore {

    public Persona consultarPersona(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion);

    public Municipio consultarMunicipio(String codigoMunicipio);
    
    public DepartamentoModeloDTO consultarDepartamento(String codigoDepartamento);

    public void persistirUbicacion(Ubicacion ubicacion);

    public void persistirPersona(Persona persona);

    public void actualizarPersona(Persona persona);

    /**
     * Metodo para obtener los empleadores en estado No Formalizado sin afiliacion con Aportes
     * y no formalizados retirados con aportes (Ambos estados calculados). (HU-403)
     * 
     * @param criterios
     *          Criterios de busqueda que vienen desde el formulario
     * @param uri
     * @param response
     * @return
     */
    List<EmpAporPendientesPorAfiliarDTO> consultarEmpPendientesPorAfiliar(CriteriosDTO criterios, UriInfo uri,
            HttpServletResponse response);

    /**
     * HU-404 Metodo para busqueda de empleadores con cero trabajadores activos
     *
     * @param numeroIdentificacion
     * @param tipoIdentificacion
     * @param nombreEmpresa
     * @param digitoVerificacion
     * @param fechaInicioIngresoBandeja
     * @param fechaFinIngresoBandeja
     * @param uri
     * @param response
     * @return
     */
    public List<BandejaEmpleadorCeroTrabajadoresDTO> consultarEmpCeroTrabajadoresActivos(String numeroIdentificacion,
            TipoIdentificacionEnum tipoIdentificacion, String nombreEmpresa, Short digitoVerificacion, Long fechaInicioIngresoBandeja,
            Long fechaFinIngresoBandeja, UriInfo uri, HttpServletResponse response);

    /**
     * HU-405 Metodo de busqueda de los empleadores que presentaron problemas en
     * el envio de una notificacion
     * 
     * @return resultado List<code>ActualizacionDatosEmpleadorModeloDTO</code>
     */
    public List<ActualizacionDatosEmpleadorModeloDTO> consultarActualizacionDatosEmp(TipoIdentificacionEnum tipoDocumento,
            String idAportante, Short digitoVerificacion, Long fechaIngresoBandeja);

    /**
     * HU-405 Metodo de actualizacion de los empleadores que presentaron
     * problemas en el envio de una notificacion
     *
     */
    public void ActualizarActualizacionDatosEmp(List<ActualizacionDatosEmpleadorModeloDTO> listaActualizacion);

    /**
     * HU-404 Metodo para actualizar el registro vigente de la bandeja
     * @param idEmpleadores
     *        ID de los empleadores seleccionados
     * @param user
     *        Usuario que realiza la operacion
     */
    public void actualizarEmpleadoresGestionadosBandejaCero(List<Long> idEmpleadores, UserDTO user);

    /**
     * HU-404 Metodo para mantener una lista de empleadores en gestion enviada desde
     * pantalla
     * @param idEmpleadores
     *        ID de los empleadores seleccionados
     * @param user
     *        Usuario que realiza la operacion
     */
    public void mantenerAfiliacionEmpleadoresBandejaCero(List<Long> idEmpleadores, UserDTO user);

    /**
     * Método para la consulta de la existencia de una persona
     * @param tipoId
     * @param numId
     * @return
     */
    public Boolean buscarPersona(TipoIdentificacionEnum tipoId, String numId);
    
	/**
	 * Servicio encargado de consultar las planillas que sufrieron un fallo en el
	 * procesamiento durante la transición de los datos temporales a core.
	 * 
	 * @return s
	 */
    public List<PilaEstadoTransitorio> bandejaTransitoriaGestion();
    
    /**
     * Servicio encargado de consultar las planillas que sufrieron un fallo en el
     * procesamiento durante la transición de los datos temporales a core.
     * @param tipoIdentificacion 
     * @param numeroIdentificacion 
     * @param numeroPlanilla 
     * @param fechaInicio 
     * @param fechaFin 
     * 
     * @return a
     */
    public List<PilaEstadoTransitorio> bandejaTransitoriaGestionParam(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion, String numeroPlanilla, Long fechaInicio, Long fechaFin);
    
    /**
	 * Servicio encargado de consultar PilaEstadoTransitorio por id
	 * 
	 * @return PilaEstadoTransitorio
	 */
    public PilaEstadoTransitorio consultarBandejaTransitoriaGestion(Long idPilaEstadoTransitorio);
    
    /**
     * Método que consulta los errores de una noveadd temporal durante su formalización 
     * @param idTempNovedad
     * @return
     */
    public List<ExcepcionNovedadPilaModeloDTO> consultarExcepcionNovedadPila(Long idTempNovedad);
    
    
    /**
     * Actualiza el estado de la acción que habia fallado en el proceso de formalizar datos de una planilla
     * 
     * @param indicePlanilla
     * @return true si realizó una actualización
     */
    public Boolean actualizarEstadoBandejaTransitoriaGestion(Long indicePlanilla);
    
}
