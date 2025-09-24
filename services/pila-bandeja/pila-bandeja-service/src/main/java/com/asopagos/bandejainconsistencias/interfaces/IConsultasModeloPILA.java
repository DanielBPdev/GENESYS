package com.asopagos.bandejainconsistencias.interfaces;

import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;
import com.asopagos.bandejainconsistencias.dto.ActivosCorreccionIdAportanteDTO;
import com.asopagos.bandejainconsistencias.dto.IdentificadorDocumentoDTO;
import com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO;
import com.asopagos.dto.modelo.IndicePlanillaModeloDTO;
import com.asopagos.dto.modelo.TemNovedadModeloDTO;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoAPRegistro1;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoARegistro1;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoFRegistro6;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloque;
import com.asopagos.entidades.pila.procesamiento.HistorialEstadoBloque;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.entidades.pila.procesamiento.SolicitudCambioNumIdentAportante;
import com.asopagos.enumeraciones.aportes.TipoInconsistenciasEnum;
import com.asopagos.enumeraciones.aportes.TipoOperadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoGestionInconsistenciaEnum;
import com.asopagos.enumeraciones.pila.RazonRechazoSolicitudCambioIdenEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de información en
 * el modelo de datos de PILA <br/>
 * <b>Módulo:</b> Asopagos - HU-211-401 y HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 * @author <a href="mailto:anbuitrago@heinsohn.com.co">Andres Felipe Buitrago </a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson A. Arboleda </a>
 */
@Local
public interface IConsultasModeloPILA {

    /**
     * Se obtiene el registro A correspondiente al numero de planilla
     * 
     * @return PilaArchivoARegistro1 con la cantidad total de archivos
     */
    public PilaArchivoARegistro1 obtenerRegistroTipoA(Long numeroPlanilla, TipoArchivoPilaEnum tipoArchivoI, String operadorInformacion);

    /**
     * Se obtiene el registro AP correspondiente al numero de planilla
     * 
     * @return PilaArchivoARegistro1 con la cantidad total de archivos
     */
    public PilaArchivoAPRegistro1 obtenerRegistroTipoAP(Long idPlanilla, TipoArchivoPilaEnum tipoArchivoIP, String operadorInformacion);

    /**
     * Se obtiene el registro 6 de OF a partir del # de planilla, id de aportante, cód de OI y período de aporte
     * @author abaquero
     */
    public PilaArchivoFRegistro6 obtenerRegistro6Of(String numeroPlanilla, String codOperadorInformacion,
            String periodoPago);

    /**
     * Consultar id del indice de planilla para el archivo I,
     * se necesita para cambiar el estado en bloques de validacion
     * 
     * @param idPlanilla
     * @return Long
     *         Con el id del indice de planilla requerido
     */
    public IndicePlanilla obtenerIdIndicePlanillaArchivoI(Long idPlanilla, TipoArchivoPilaEnum tipoArchivo);

    /**
     * Modifica los estados asociados al indice planilla
     * @param archivoIId
     * @param nombreUsuario 
     */
    public IndicePlanilla modificarEstadosArchivo(IndicePlanilla archivoIId, String nombreUsuario);

    /**
     * Método para la consulta general de archivos con inconsistencia
     * 
     * @param tipoIdentificacion
     *        El tipo de identificacion para la busqueda
     * @param numeroPlanilla
     *        Numero de planilla asociado a la busqueda
     * @param fechaInicio
     *        Fecha inicial de busqueda
     * @param fechaFin
     *        Fecha final de busqueda
     * @param numeroIdentificacion
     *        Numero de identificacion de busqueda
     * @param operador
     *        Tipo de operador de la busqueda
     * @param userDTO
     *        Datos del usuario
     * @return List<InconsistenciaDTO> Lista de las inconsistencias encontradas
     *         asociadas a los parametros de busqueda
     */
    public List<InconsistenciaDTO> consultarArchivosInconsistentesResumen(TipoIdentificacionEnum tipoIdentificacion, String numeroPlanilla,
            Long fechaInicio, Long fechaFin, String numeroIdentificacion, TipoOperadorEnum operador, Short digitoVerificacion, String bloqueValidacion, Boolean ocultarBlq5);

    /**
     * Metodo para consultar que tipo de inconsistencias posee un item
     * 
     * @param inconsistencia
     *        Contiene datos referentes a la inconsistencia encontrada
     * 
     * 
     * @return List<TipoInconsistenciasEnum> lista que contiene los tipo de
     *         inconsistencias que posee el item seleccionado
     */

    public List<TipoInconsistenciasEnum> accionBandejaInconsistencias(InconsistenciaDTO inconsistencia);

//    /**
//     * Metodo para consultar el detalle del tipo de inconsistencias de una
//     * planilla
//     * 
//     * 
//     * @param inconsistencias
//     *        DTO con los datos referentes a la planilla que contiene
//     *        errores
//     * @param tipoInconsistencia
//     *        El tipo de inconsistencias que se desea consultar acerca de la
//     *        planilla
//     * 
//     * @return List<InconsistenciaDTO> lista que contiene la descripcion de los
//     *         datos de inconsistencias segun el tipo y la planilla
//     */
//    public List<InconsistenciaDTO> accionBandejaDetalleInconsistencias(InconsistenciaDTO inconsistencias,
//            TipoInconsistenciasEnum tipoInconsistencia);

    /**
     * Metodo para consultar el detalle del tipo de inconsistencias de una
     * planilla
     * 
     * @param inconsistencias
     *        DTO con los datos referentes a la planilla que contiene
     *        errores
     * 
     * @param bloquesConsulta
     *        Listado con los bloque de validación consultados
     * 
     * @return List<InconsistenciaDTO> lista que contiene la descripcion de los
     *         datos de inconsistencias segun el tipo y la planilla
     */
    public List<InconsistenciaDTO> consultaDetalleInconsistenciasBandeja(InconsistenciaDTO inconsistencia,
            List<BloqueValidacionEnum> bloquesConsulta, UriInfo uri, HttpServletResponse response);

    /**
     * Metodo que anula las planillas que se envien
     * 
     * @param inconsistencia
     *        Contiene los datos del archivo que se desea establecer como
     *        anulado
     * @return Boolean indicando true si fue exitoso o false si no se pudo
     *         realizar la modificacion
     */
    public void anularPlanillaOI(InconsistenciaDTO inconsistencia, UserDTO user);

    /**
     * Metodo que establece en las plantillas el estado Estructura validada
     * 
     * @param inconsistencia
     *        DTO que contiene los datos de la planilla que se va a
     *        modificar
     * 
     */
    public void validarEstructuraPlanilla(InconsistenciaDTO inconsistencia);

    public void persistirHistoricoBloque2(Long indicePlanilla, String nombreUsuario);


    /**
     * Metodo que establece en las planillas financieras el estado anulado
     * 
     * @param inconsistencia
     *        DTO que contiene los datos de la planilla que se va a
     *        modificar
     * 
     */
    public void anularPlanillaOF(InconsistenciaDTO inconsistencia, UserDTO user);

    /**
     * Metodo que envia una solicitud de cambio de identificacion del aportante
     * a la bandeja del supervisor
     * 
     * @param solicitudCambio
     *        contiene los campos requeridos para enviar a la bandeja
     * @param user
     *        contiene los datos del usuario que esta realizando la
     *        solicitud
     */
    public void enviarSolicitudCambioIden(InconsistenciaDTO inconsistencias, Long numeroIdentificacion, UserDTO user);

    /**
     * Método para consultar los ID de índice de planilla no anulados relacionados a un número de planilla PILA
     * @param numeroPlanilla
     *        Número de la planilla para ubicar
     * @param tipoArchivo
     *        Tipo de archivo a buscar
     * @result <b>IndicePlanillaModeloDTO<Long></b>
     *         DTO que representa el índices de planilla no anulado relacionado
     */
    public IndicePlanillaModeloDTO consultarIndicesOIporNumeroYTipo(IndicePlanilla indicePlanilla, TipoArchivoPilaEnum tipoArchivo);

    /**
     * Método encargado de consultar un Registro tipo 1 de archivo PILA por Id de Planilla y tipo de archivo
     * @param idPlanilla
     *        ID de índice de planilla consultado
     * @param tipoArchivo
     *        Tipo de archivo para determinar la tabla de persistencia a consultar
     * @return <b>Object</b>
     *         Objeto que contiene el registro tipo 1 encontrado
     */
    public Object consultarRegistro1PorIdPlanillaYTipoArchivo(Long idPlanilla, TipoArchivoPilaEnum tipoArchivo);

    /**
     * Metodo que rechaza las solicutudes de cambio de identificacion
     * 
     * @param List<SolicitudCambioNumIdentAportante>
     *        contiene las solicitudes que se van a aprobar
     * @param user
     *        contiene los datos del usuario que esta realizando la
     *        solicitud
     */
    public void rechazarSolicitudCambioIden(List<SolicitudCambioNumIdentAportante> listaSolicitudes, UserDTO user,
            RazonRechazoSolicitudCambioIdenEnum razonRechazo, String comentarios);

    /**
     * Metodo de busqueda de solicitudes de cambio de identificacion para la
     * pantalla de la HU-411
     * 
     * @param numeroPlanilla
     *        numero de planilla a buscar
     * @param fechaInicio
     *        fecha de inicio de busqueda
     * 
     * @param fechaFin
     *        fecha fin de busqueda
     */
    public List<SolicitudCambioNumIdentAportante> busquedaSolicitudCambioIden(Long numeroPlanilla, Long fechaInicio, Long fechaFin);

    /**
     * Método para la consulta de entradas de inconsistencias de validación de PILA M1
     * */
    public Boolean establecerGestionInconsistencias(List<Long> listaErrores, EstadoGestionInconsistenciaEnum estado, BloqueValidacionEnum bloque);

    /**
     * Metodo para retornar un indice de planilla para ver el archivo
     * 
     * @param idPlanilla
     */
    public IdentificadorDocumentoDTO veArchivo(Long idPlanilla, TipoArchivoPilaEnum tipoArchivo);

    /**
     * Metodo para obtener el IndicePlanilla a partir del indice de planilla
     * @param idIndicePlanilla
     * @return
     */
    public IndicePlanilla obtenerIndicePlanilla(Long idIndicePlanilla);

    /**
     * Metodo que obtiene la cantidad de inconsistencias pendientes para la HU
     * 392
     */
    public Long totalInconsistenciasPendientes();

    /**
     * Metodo que obtiene la cantidad de inconsistencias pendientes para la HU
     * 411
     */
    public Long totalInconsistenciasPendientesAprobacion();

    public EstadoArchivoPorBloque consultarEstadoBloquePlanillaXId(Long idPlanilla);

    public void actualizarIndicePlanilla(IndicePlanilla indice);

    public void actualizarEstadoBloque(EstadoArchivoPorBloque estado, HistorialEstadoBloque historialEstado);

    /**
     * Metodo que obtiene la cantidad de inconsistencias pendientes para la HU
     * 411
     */
    public InconsistenciaDTO validarRespuestaCambioId(Long idErrorValidacion);

    /**
     * Metodo para consultar la razon social de cada aportante en la lista
     * @param result
     * @return
     */
    public List<InconsistenciaDTO> consultarRazonSocialAportantes(List<InconsistenciaDTO> result);

    /**
     * Método para dar gestión a una entrada de ErrorValidacionLog
     * @param idLog
     *        ID del log de inconsistencia de PILA a gestionar
     * @author abaquero
     */
    public void getionarInconsistenciaPorId(Long idLog);

    /**
     * Método encargado de la actualización de los datos de los diversas tablas involucradas en el proceso de cambio de
     * ID de aportante por bandeja 392
     * @param activos
     *        DTO que contiene la información a actualizar
     * @author abaquero
     */
    public void actualizarActivosCambioIdAportanteEnBD(ActivosCorreccionIdAportanteDTO activos);

    /**
     * Metodo que permite consultar el archivo de operador financiero asociado a una planilla
     * 
     * @param idPlanilla
     *        Índice de planilla consultado
     * @return <code>IndicePlanillaOF</code>
     *         Entidad que representa al índice de archivo de operador financiero
     */
    public IndicePlanillaOF consultarArchivosOFAsociados(Long idPlanilla);

    /**
     * Método encargado de la consulta de un índice de planilla por su ID
     * @param idIndicePlanilla
     *        ID de índice de planilla consultado
     * @return
     */
    public IndicePlanillaModeloDTO consultarIndicesOIporIdPlanilla(Long idIndicePlanilla);
    
    /**
     * Método encargado de consultar un listado de indce planillas 
     * @param listaIdPlanilla
     * @return
     */
    public List<IndicePlanilla> listaIndicePlanilla(List<Long> listaIdPlanilla);
    
    /**
     * Método que consulta las novedades temporales existentes para una planilla
     * @param idRegistroGeneral
     * @return
     */
    public List<TemNovedadModeloDTO> consultarNovedadesTemporales(Long idRegistroGeneral);
    
    /**
     * Método que actualiza el estado en proceso de los aportes de una planilla
     * 
     * @param indicePlanilla
     * @return true si realizó una actualización
     */
    public Boolean actualizarEstadoEnProcesoAportes(Long indicePlanilla);

    public IndicePlanilla consultarIndicePlanillaEntidad(Long idIndicePlanilla);
}
