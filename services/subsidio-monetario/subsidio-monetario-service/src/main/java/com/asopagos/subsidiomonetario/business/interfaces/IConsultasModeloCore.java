package com.asopagos.subsidiomonetario.business.interfaces;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Local;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriInfo;

import com.asopagos.dto.EmpleadorDTO;
import com.asopagos.dto.RolAfiliadoDTO;
import com.asopagos.dto.modelo.BancoModeloDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.AplicacionValidacionSubsidioModeloDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.AplicacionValidacionSubsidioPersonaModeloDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.BeneficiariosAfiliadoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.CargueArchivoBloqueoCMDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.PersonaFallecidaTrabajadorDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoHistoricoLiquidacionFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.TrazabilidadSubsidioEspecificoDTO;
import com.asopagos.dto.subsidiomonetario.pagos.InformacionAdminSubsidioDTO;
import com.asopagos.dto.subsidiomonetario.pagos.SubsidioAfiliadoDTO;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ConjuntoValidacionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.GrupoAplicacionValidacionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoValidacionLiquidacionEspecificaEnum;
import com.asopagos.notificaciones.dto.AutorizacionEnvioComunicadoDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.subsidiomonetario.dto.AprobacionRechazoSubsidioMonetarioDTO;
import com.asopagos.subsidiomonetario.dto.ArchivoLiquidacionSubsidioModeloDTO;
import com.asopagos.subsidiomonetario.dto.BloqueoBeneficiarioCuotaMonetariaDTO;
import com.asopagos.subsidiomonetario.dto.CargueBloqueoCMDTO;
import com.asopagos.subsidiomonetario.dto.CondicionesEspecialesLiquidacionEspecificaDTO;
import com.asopagos.dto.subsidiomonetario.pagos.InformacionLiquidacionFallecimientoDTO;
import com.asopagos.subsidiomonetario.dto.ConsultaBeneficiarioBloqueadosDTO;
import com.asopagos.subsidiomonetario.dto.ConsultaLiquidacionSubsidioMonetarioDTO;
import com.asopagos.subsidiomonetario.dto.CuotaMonetariaIVRDTO;
import com.asopagos.subsidiomonetario.dto.EspecieLiquidacionManualDTO;
import com.asopagos.subsidiomonetario.dto.IniciarSolicitudLiquidacionMasivaDTO;
import com.asopagos.subsidiomonetario.dto.LiquidacionEspecificaDTO;
import com.asopagos.subsidiomonetario.dto.PersonaLiquidacionEspecificaDTO;
import com.asopagos.subsidiomonetario.dto.RegistroLiquidacionSubsidioDTO;
import com.asopagos.subsidiomonetario.dto.RegistroSinDerechoSubsidioDTO;
import com.asopagos.subsidiomonetario.dto.RespuestaGenericaDTO;
import com.asopagos.subsidiomonetario.dto.ResultadoHistoricoLiquidacionMasivaDTO;
import com.asopagos.subsidiomonetario.dto.ResultadoValidacionArchivoBloqueoCMDTO;
import com.asopagos.subsidiomonetario.dto.ValorPeriodoDTO;
import com.asopagos.subsidiomonetario.dto.VerificarPersonasSinCondicionesDTO;
import com.asopagos.subsidiomonetario.modelo.dto.CuentaCCFModeloDTO;
import com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionCondicionesSubsidioModeloDTO;
import com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionLiquidacionSubsidioModeloDTO;
import com.asopagos.subsidiomonetario.modelo.dto.SolicitudLiquidacionSubsidioModeloDTO;
import com.asopagos.subsidiomonetario.dto.EspecieLiquidacionManualDTO;

/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de
 * información en el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU-311-434 <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */

@Local
public interface IConsultasModeloCore {

    /**
     * Metodo que ejecuta una liquidacion masiva
     *
     * @param liquidacion
     * @author rarboleda
     * @return
     */
    public RespuestaGenericaDTO ejecutarLiquidacionMasiva(SolicitudLiquidacionSubsidioModeloDTO liquidacion, Long periodo);

    /**
     * Metodo que guarda una liquidacion masiva para su posterior ejecución
     *
     * @param liquidacion
     * @author rarboleda
     * @return
     */
    public RespuestaGenericaDTO guardarLiquidacionMasiva(SolicitudLiquidacionSubsidioModeloDTO liquidacion, Long periodo, UserDTO userDTO);

    /**
     * Metodo para cancelar una liquidacion masiva en proceso
     *
     * @author rarboleda
     * @return
     */
    public Boolean cancelarLiquidacionMasiva();

    /**
     * Método que permite consultar el histórico de una liquidación masiva,
     * dados los criterios de busqueda
     *
     * @param periodoRegular periodo asociado al proceso de liquidación
     * @param fechaInicial límite inferior para el rango de fechas de los
     * procesos de liquidación
     * @param fechaFin límite superior para el rango de fechas de los procesos
     * de liquidación
     * @param numeroOperacion número de operación correspondiente al proceso de
     * liquidación
     * @return DTO´s con la información obtenida
     */
    public List<ResultadoHistoricoLiquidacionMasivaDTO> consultarHistoricoLiquidacionMasiva(Long periodoRegular, Long fechaInicio,
            Long fechaFin, String numeroOperacion, UriInfo uri, HttpServletResponse response);

    /**
     * Método que permite realizar la consulta de liquidaciones históricas por
     * los datos de liquidación
     *
     * @param tipoLiquidacion tipo de liquidación especifica sobre la que se
     * desea consultar el histórico (reconocimiento o ajuste de cuota)
     * @param numeroOperacion número de operación relacionado con el proceso de
     * liquidación
     * @param fechaInicio límite inferior para el rango de fechas de los
     * procesos de liquidación
     * @param fechaFin límite superior para el rango de fechas de los procesos
     * de liquidación
     * @param uri parámetros de la consulta sobre el recurso
     * @param response respuesta
     * @author rlopez
     * @return DTO´s con la información de las liquidaciones históricas
     */
    public List<ResultadoHistoricoLiquidacionMasivaDTO> consultarHistoricoLiquidacionEspecifica(TipoProcesoLiquidacionEnum tipoLiquidacion,
            TipoLiquidacionEspecificaEnum tipoLiquidacionEspecifica, Long periodoRegular, Long fechaInicio, Long fechaFin,
            String numeroOperacion, UriInfo uri, HttpServletResponse response);

    /**
     * Método que se encarga de actualizar una parametrizacion
     *
     * @param parametrizacion DTO con la información de la parametrización
     * @return identificador de la parametrización
     */
    public Long actualizarParametrizacion(ParametrizacionLiquidacionSubsidioModeloDTO parametrizacion);

    /**
     * Método que se encarga de crear una parametrización
     *
     * @param parametrizacion DTO con la información de la parametrización
     * @author rlopez
     * @return identificador de la parametrización
     */
    public Long crearParametrizacion(ParametrizacionLiquidacionSubsidioModeloDTO parametrizacion);

    /**
     * Método que se encarga de actualizar una condición
     *
     * @param condicionDTO DTO con la información del DTO
     * @author rlopez
     * @return identificador de la condición
     */
    public Long actualizarCondicion(ParametrizacionCondicionesSubsidioModeloDTO condicionDTO);

    /**
     * Método que se encarga de crear un condición
     *
     * @param condicionDTO DTO con la información de la condición
     * @author rlopez
     * @return identificador de la condición
     */
    public Long crearCondicion(ParametrizacionCondicionesSubsidioModeloDTO condicionDTO);

    /**
     * Metodo para inicializar la pantalla de solicitud de liquidacion de
     * subsidio
     *
     * @return DTO con la informacion inicial de la pantalla
     * @author rarboleda
     */
    public IniciarSolicitudLiquidacionMasivaDTO inicializarPantallaSolicitudLiquidacion(UserDTO userDTO);

    /**
     * Metodo para inicializar la pantalla de solicitud de liquidacion de
     * subsidio
     *
     * @param userDTO
     * @return
     */
    //public IniciarSolicitudLiquidacionMasivaDTO inicializarPantallaSolicitudLiquidacionCerrada(UserDTO userDTO);
    /**
     * Método que permite consultar una solicitud de liquidación dado el número
     * de radicado
     *
     * @param numeroRadicado valor del número de radicado
     * @author rlopez
     * @return identificador de la solicitud de liquidacion
     */
    public Long consultarIdSolicitud(String numeroRadicado);

    /**
     * Método que se encarga de registrar la información para una aprobación de
     * susbsidio monetario en primer nivel
     *
     * @param numeroSolicitud número de la solicitud de liquidación
     * @param userDTO usuario que realiza la aprobación
     * @return identificador de la solicitud
     */
    public Long aprobarLiquidacionMasivaPrimerNivel(String numeroSolicitud, UserDTO userDTO);

    /**
     * Método que se encarga de registrar la información para un rechazo de
     * subsidio monetario en primer nivel
     *
     * @param numeroSolicitud número de solicitud
     * @param aprobacionRechazoSubsidioMonetarioDTO DTO con la información a
     * registrar
     * @param userDTO usuario que realiza el rechazo
     * @return identificador de la solicitud
     */
    public Long rechazarLiquidacionMasivaPrimerNivel(String numeroSolicitud,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, UserDTO userDTO);

    /**
     * Método que se encarga de registrar la información para una aprobación de
     * susbsidio monetario en segundo nivel
     *
     * @param numeroSolicitud número de solicitud
     * @param aprobacionRechazoSubsidioMonetarioDTO DTO con la información a
     * registrar
     * @param userDTO usuario que realiza la aprobación
     * @return DTO con la información de la solicitud
     */
    public SolicitudLiquidacionSubsidioModeloDTO aprobarLiquidacionMasivaSegundoNivel(String numeroSolicitud,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, UserDTO userDTO);

    /**
     * Método que se encarga de registar la información para un rechazo de
     * subsidio monetario en segundo nivel
     *
     * @param numeroSolicitud número de la solicitud
     * @param aprobacionRechazoSubsidioMonetarioDTO DTO con la información a
     * registrar
     * @param userDTO usuario que realiza el rechazo
     * @return DTO con la información de la liquidación
     */
    public SolicitudLiquidacionSubsidioModeloDTO rechazarLiquidacionMasivaSegundoNivel(String numeroSolicitud,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, UserDTO userDTO);

    /**
     * Actualiza la solicitud global con el número de instrancia generado por el
     * BPM
     *
     * @param idInstancia Número de instancia
     * @param idSolicitudGlobal
     * @return
     * @author rarboleda
     */
    public void actualizarInstanciaSolicitudGlobal(Long idInstancia, Long idSolicitudGlobal);

    /**
     * <b>Descripcion</b>Metodo que se encarga de actualizar el estado de la
     * socilictud de liquidacion
     *
     * @param idSolicitudLiquidacion, identificador que permite buscar la
     * solicitud
     * @param estado Referencia al estado al cual se va actualizar la solicitud
     * de liquidacion
     */
    public void actualizarEstadoSolicitudLiquidacion(Long idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum estado);

    /**
     * Método que permite obtener la información de una solicitud de liquidación
     * dado su número de radicado
     *
     * @param numeroRadicado valor del número de radicado
     * @return DTO con la información de la solicitud de liquidación
     */
    public SolicitudLiquidacionSubsidioModeloDTO consultarSolicitudLiquidacion(String numeroRadicado);

    /**
     * Metodo que se ejecuta al seleccionar una persona luego de haberla buscado
     *
     * @param persona
     * @return
     */
    public RespuestaGenericaDTO seleccionarPersona(Long personaId);

    /**
     * Metodo que se ejecuta al seleccionar una empresa luego de haberla buscado
     *
     * @param empleador
     * @return
     */
    public RespuestaGenericaDTO seleccionarEmpleador(Long empleadorId);

    /**
     * Metodo que permite consultar los beneficiarios de una persona
     *
     * @param idPersona
     * @return
     */
    public List<BeneficiariosAfiliadoDTO> consultarBeneficiariosAfiliado(Long idPersona);

    /**
     * Metodo que permite generar un nuevo periodo
     */
    public void generarNuevoPeriodo();

    /**
     * Metodo que permite generar un nuevo periodo
     */
    public void generarNuevoPeriodo(Long periodo);

    /**
     * Metodo encargado de consultar las
     *
     * @return
     */
    public RespuestaGenericaDTO consultarLiquidacionesProgramadasAbiertas();

    /**
     * Metodo para actualizar un estado de liquidacion a partir del número de
     * radicado
     *
     * @param numeroRadicado
     */
    public void actualizarEstadoSolicitudLiquidacionXNumRadicado(String numeroRadicado, EstadoProcesoLiquidacionEnum estado);

    /**
     * Metodo encargado de procesar una liquidacion especifica
     *
     * @param liquidacionEspecifica
     * @return Respuesta del servicio
     * @author rarboleda
     */
    public RespuestaGenericaDTO ejecutarLiquidacionEspecifica(LiquidacionEspecificaDTO liquidacionEspecifica);

    /**
     * Metodo encargado de consultar
     *
     * @param periodo
     * @return
     */
    public BigDecimal consultarValorCuotaPeriodo(Long periodo);

    /**
     * Método que permite obtener la información referente a los identificadores
     * de los archivos de una liquidación
     *
     * @param numeroRadicacion valor del número de radicación
     * @return DTO con la información de los identificadores
     * @author rlopez
     */
    public ArchivoLiquidacionSubsidioModeloDTO consultarArchivosLiquidacion(String numeroRadicacion);
    public ArchivoLiquidacionSubsidioModeloDTO consultarArchivosLiquidacionPorId(Long idArchivoLiquidacion);

    /**
     * Método que permite actualizar la información de los identificadores en el
     * ECM para los archivos de una liquidación
     *
     * @param archivoLiquidacionDTO DTO con la información de archivo(s)
     * @return indentificador del registro actualizado
     * @author rlopez
     */
    public Long actualizarArchivosLiquidacion(ArchivoLiquidacionSubsidioModeloDTO archivoLiquidacionDTO);

    /**
     * Método que permite registrar la información de los indentificadores en el
     * ECM para los archivos de una liquidación
     *
     * @param archivoLiquidacionDTO DTO con la información de archivo(s)
     * @return identificador del registro creado
     * @author rlopez
     */
    public Long registrarArchivosLiquidacion(ArchivoLiquidacionSubsidioModeloDTO archivoLiquidacionDTO);

    /**
     * Método que permite realizar la consulta del periodo regular asociado a
     * una liquidación
     *
     * @param numeroRadicacion valor del número de radicado
     * @return periodo regular
     * @author rlopez
     */
    public Date consultarPeriodoRegularRadicacion(String numeroRadicacion);

    /**
     * Permite consultar una liquidacion en progreso
     *
     * @return Boolean indicador de liquidacion en progreso
     */
    public Boolean verificarLiquidacionEnProceso();

    /**
     * Metodo que guarda una liquidacion especifica
     *
     * @param liquidacionEspecifica
     * @return
     */
    public RespuestaGenericaDTO guardarLiquidacionEspecifica(LiquidacionEspecificaDTO liquidacionEspecifica, UserDTO userDTO);

    /**
     * @param periodo
     * @return
     */
    public ValorPeriodoDTO consultarValorCuotaAnualYAgrariaPeriodos(Long periodo);

    /**
     * Verifica si un periodo existe
     *
     * @param periodo
     * @return
     */
    public Boolean verificarExistenciaPeriodo(List<ValorPeriodoDTO> periodos);

    /**
     * Metodo para guardar los periodos asociados a la liquidacion especifica
     *
     * @param periodos
     * @param idSolicitudLiquidacion
     * @return
     * @author rarboleda
     */
    public Boolean guardarPeriodosLiquidacion(List<ValorPeriodoDTO> periodos, Long idSolicitudLiquidacion, TipoLiquidacionEspecificaEnum tipoAjuste);

    /**
     * Actualizar el estado de una solicitud X numero de radicado
     *
     * @param numeroRadicado
     */
//    public void actualizarEstadoSolicitudLiquidacionXNumeroRadicado(String numeroRadicado);
    /**
     * Metodo que permite obtener los factores de discapacidad para los periodos
     * dados
     *
     * @param periodos para los cuales se desea obtener el factor de
     * discapacidad
     * @return Lista con los factores de discapacidad asociados al periodo
     */
    public BigDecimal consultarFactorCuotaDiscapacidadPeriodos(Long periodo);

    /**
     * Metodo que permite obtener el valor de la cuota agraria para los periodos
     * dados
     *
     * @param periodos para los cuales se desea obtener el valor de la cuota
     * agraria
     * @return Lista con los valores de las cuotas agrarias para el periodo
     */
    public BigDecimal consultarValorCuotaAgrariaPeriodo(Long periodo);

    /**
     * Metodo para persistir las personas/empresas asociadas a una liquidacion
     * especifica (HU's 143-144-146-148)
     *
     * @param liquidacionEspecifica Informacion de la liquidacion especifica que
     * viene de pantallas idSolicitudLiquidacion Id de la solicitud de
     * liquidacion creada actualmente
     * @return Boolean indicando si el proceso fue satisfactorio
     * @author rarboleda
     */
    public Boolean guardarPersonasLiquidacionEspecifica(LiquidacionEspecificaDTO liquidacionEspecifica, Long idSolicitudLiquidacion);

    /**
     * Método que permite actualizar la solicitud de liquidación, registrando la
     * fecha de dispersión
     *
     * @param idSolicitudLiquidacion valor del identificador de la solicitud de
     * liquidación
     * @author rlopez
     */
    public void actualizarFechaDispersion(Long idSolicitudLiquidacion);

    /**
     * Método que permite consultar la parametrización de condiciones para un
     * periodo definido
     *
     * @param periodo valor del periodo
     * @return información de la parametrización
     * @author rlopez
     */
    public ParametrizacionCondicionesSubsidioModeloDTO consultarCondicionesPeriodo(Date periodo);

    /**
     * Método que permite consultar la parametrización de la liquidación para un
     * periodo definido
     *
     * @param periodo valor del periodo
     * @return información de la parametrización
     * @author jocampo
     */
    public ParametrizacionLiquidacionSubsidioModeloDTO consultarParametrosLiquidacionPeriodo(Date periodo);

    /**
     * Metodo encargado de almacenar las condiciones especiales asociadas a una
     * liquidacion especifica por reconocimiento
     *
     * @param condiciones Condiciones especiales
     * @param idSolicitudLiquidacion Identificador de la solicitud de
     * liquidacion
     */
    public void guardarCondicionesEspecialesReconocimiento(CondicionesEspecialesLiquidacionEspecificaDTO condiciones,
            Long idSolicitudLiquidacion);

    /**
     * Metodo que realiza las validaciones asociadas a la HU-317-503 para el
     * subsidio de fallecimiento trabajador
     *
     * @param tipoLiquidacion
     */
    public PersonaFallecidaTrabajadorDTO seleccionarPersonaSubsidioFallecimientoTrabajador(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion, TipoLiquidacionEspecificaEnum tipoLiquidacion);

    /**
     * Metodo para consultar las condiciones asociadas al beneficiario fallecido
     *
     * @param persona
     * @return
     */
    public PersonaFallecidaTrabajadorDTO consultarCondicionesBeneficiarioFallecido(PersonaFallecidaTrabajadorDTO persona);

    /**
     * Método que se encarga de actualizar la solicitud de liquidación con base
     * en los parámetros definidos para el desembolso de subsidio en una
     * liquidación de fallecimiento
     *
     * @param numeroRadicacion valor del número de radicación
     * @param consideracionAportes indicador de consideración de aportes
     * @param tipoDesembolso modo en que se realizará el desembolso
     * @author rlopez
     */
    public void actualizarDesembolsoSubsidioLiquidacionFallecimiento(String numeroRadicacion, Boolean consideracionAportes,
            ModoDesembolsoEnum tipoDesembolso);

    /**
     * Método que permite consultar los nombres de las cajas de compensación
     * parametrizadas en el sistema
     *
     * @return mapa con los códigos y nombres de las cajas
     * @author rlopez
     */
    public Map<String, String> consultarNombresCajas();

    /**
     * Método que permite obtener la información histórica de las liquidaciones
     * específicas de fallecimiento
     *
     * @param periodoRegular Periodo regular asociado a la liquidación de
     * fallecimiento
     * @param fechaInicio Filtro de fecha inicial para la consulta de históricos
     * @param fechaFin Filtro de fecha final para la consulta de históricos
     * @param tipoIdentificacion Tipo de identificación del fallecido
     * @param numeroIdentificacion Número de identificación del fallecido
     * @param numeroRadicacion Valor del número de radicación
     * @param uri
     * @param response
     * @return Lista de DTO´s con la información histórica
     * @author rlopez
     */
    public List<ResultadoHistoricoLiquidacionFallecimientoDTO> consultarHistoricoLiquidacionFallecimiento(Long periodoRegular,
            Long fechaInicio, Long fechaFin, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            String numeroRadicacion, UriInfo uri, HttpServletResponse response);

    /**
     * Método para consultar las parametrizaciones de una liquidacion
     * especifica, incluidas ajuste, recocimiento y defuncion
     *
     * @param numeroRadicado Número de radicado asociado
     * @return DTO con la parametrizacion asociada
     */
    public LiquidacionEspecificaDTO consultarParametrizacionLiqEspecifica(String numeroRadicado);

    /**
     * Método que permite obtener el identificador de
     * PersonaLiquidacionEspecifica para el trabajador relacionado en una
     * liquidación de fallecimiento
     *
     * @param numeroRadicacion Valor del número de radicación
     * @param idPersona Identificador de la persona
     * @return Identificador de PersonaLiquidacionEspecifica
     * @author rlopez
     */
    public Long seleccionarPersonaLiquidacionEspecificaTrabajadorLiquidacionFallecimiento(String numeroRadicacion, Long idPersona);

    /**
     * Método que permite obtener el identificador de
     * PersonaLiquidacionEspecifica para el beneficiario relacionado en una
     * liquidación de fallecimiento
     *
     * @param numeroRadicacion Valor del número de radicación
     * @param idPersona Identificador de la persona
     * @return Identificador de PersonaLiquidacionEspecifica
     * @author rlopez
     */
    public Long seleccionarPersonaLiquidacionEspecificaBeneficiarioLiquidacionFallecimiento(String numeroRadicacion, Long idPersona);

    /**
     * Método que se encarga de consultar las validaciones existentes para el
     * tipo de proceso parametrizado
     *
     * @param tipoProceso Tipo de proceso
     * @return Lista de validaciones para el tipo de proceso
     * @author rlopez
     */
    public List<ConjuntoValidacionSubsidioEnum> consultarValidacionesTipoProceso(TipoValidacionLiquidacionEspecificaEnum tipoProceso);

    /**
     * Método que se encarga de consultar el identificador correspondiente al
     * conjunto validación parametrizado
     *
     * @param validacion Valor de la validación
     * @return Identificador del conjunto validación
     * @author rlopez
     */
    public Long consultarIdentificadorConjuntoValidacion(ConjuntoValidacionSubsidioEnum validacion);

    /**
     * Método que se encarga de registrar una aplicación validación subsidio
     *
     * @param aplicacionValidacionDTO DTO con la información de la
     * AplicacionValidacionSubsidio
     * @return Identificador de la AplicacionValidacionSubsidio registrada
     * @author rlopez
     */
    public Long registrarAplicacionValidacionSubsidio(AplicacionValidacionSubsidioModeloDTO aplicacionValidacionDTO);

    /**
     * Método que se encarga de registrar una aplicación validación subsidio
     * persona
     *
     * @param aplicacionValidacionPersonaDTO DTO con la información de la
     * aplicación validación persona
     * @return Identificador de la AplicacionValidacionPersonaDTO
     * @author rlopez
     */
    public Long registrarAplicacionValidacionSubsidioPersona(AplicacionValidacionSubsidioPersonaModeloDTO aplicacionValidacionPersonaDTO);

    /**
     * Método que se encarga de consultar el periodo relacionado a la
     * liquidación a partir de su número de radicación
     *
     * @param numeroRadicacion Valor del número de radicación
     * @return Periodo de la liquidación
     * @author rlopez
     */
    public Date consultarPeriodoLiquidacionRadicacion(String numeroRadicacion);

    /**
     * Método que permite obtener la información de parametrización para una
     * liquidación masiva
     *
     * @param numeroRadicacion Valor del número de radicación
     * @return DTO con la información de parametrización
     * @author rlopez
     */
    public IniciarSolicitudLiquidacionMasivaDTO consultarParametrizacionLiquidacionMasiva(String numeroRadicacion);

    /**
     * Método que permite consultar los correos de los destinatarios del
     * comunicado 63, 64, 65
     *
     * @param idPersonasEmpresas lista de identificadores de personas
     * @param etiquetaPlantillaComunicado tipo de destinatario que cumple la
     * persona
     * @return mapa con los códigos y correos de las empresas
     * @author jocampo
     */
    public Map<Long, AutorizacionEnvioComunicadoDTO> consultarDestinatariosComunicadosLiquidacionMasiva(Set<Long> idPersonasEmpresas, EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicado);

    /**
     * 317-508 Método encargado de validar si es el día acordado en la
     * parametrización del periodo para agregar los datos de la tabla
     * DetalleSubsidioAsignadoProgramado a la tabla DetalleSubsidioAsignado
     *
     * @return True si las validaciones se cumple; False de lo contrario
     */
    public Boolean consultarDiaDelPeriodoDispersionDetallesProgramadosToDetallesAsignados();

    /**
     * 317-508 Metodo encargado de ejecutar el SP que registra los detalles de
     * subsidios programados a la tabla detalle de subsidio asignados
     */
    public void agregarRegistroDeDetallesProgramadosToDetallesAsignados();

    /**
     * Permite consultar si hay una liquidacion de fallecimiento en progreso
     *
     * @return Boolean indicador de liquidacion en progreso
     * @autor jocampo
     */
    public Boolean verificarLiquidacionFallecimientoEnProceso();

    /**
     * Metodo para consultar los beneficiarios que posee un empleado dependiente
     * asociados a las condiciones seleccionadas en la liquidación
     *
     * @param idSolicitud identificador unico de la solicitud global
     * @param idPersona identificador unico de la persona
     * @return Lista con los beneficarios asociados a las condiciones de la
     * liquidación
     */
    public List<BeneficiariosAfiliadoDTO> consultarBeneficiariosAfiliadoCondicionesLiquidacion(Long idSolicitud, Long idPersona);

    /**
     * Método encargado de obtener la aplicacion validacion de subsidio por
     * medio del id de la solicitud de liquidación del subsidio por la cual esta
     * asociada.
     *
     * @param idProcesoLiquidacionSubsidio identificador unico de la solicitud
     * de liquidación del subsidio asociada.
     * @param validacion primera validación que tenia la persona
     * @return DTO del registro Aplicacion Validacion subsidio
     */
    public List<Long> obtenerAplicacionValidacionSubsidioPorIDSolicitudLiquidacionSubsidio(Long idProcesoLiquidacionSubsidio, GrupoAplicacionValidacionSubsidioEnum validacion);

    /**
     * Método encargado de eliminar el registro de aplicacion validacion
     * subsidio persona asociado al registro de aplicacion validacion subsidio
     * para retroceder lo que se realizo en las HUs de gestionar
     * trabajar/beneficiario en fallecimiento
     *
     * @param idAplicacionValidacionDTO identificador unico del registro
     * AplicacionValidacionSubsidios asociado al registro
     * AplicacionValidacionSubsidioPersona
     */
    public void eliminarRegistroAplicacionValidacionSubsidioPersona(List<Long> idAplicacionValidacionDTO);

    /**
     * Método encargado de eliminar el registro de la tabla
     * AplicacionValidacionSubsidio por medio del id
     *
     * @param idAplicacionValidacionDTO identificador unico del registro
     */
    public void eliminarRegistroAplicacionValidacionSubsidioPorId(List<Long> idAplicacionValidacionDTO);

    /**
     * Método que actualiza la observacion del proceso
     *
     * @param String numeroSolicitud numero de radicado de la solicitud de
     * liquidacion
     * @param observacion observación del proceso a actualizar
     */
    public void cancelarMasivaActualizarObservacionesProceso(String numeroSolicitud, String observacion);

    /**
     * Método encargado de obtener los nombres sitios de pago de forma llave
     * valor.
     *
     * @return llave valor de identificador del sitio de pago y nombre.
     */
    public Map<Long, String> consultarSitiosPago();

    /**
     * Método que se encarga de buscar liquidaciones por empleador para generar
     * el reporte a descargar
     *
     * @param tipoIdentificacion Valor del tipo de identificación del empleador
     * @param numeroIdentificacion Valor del número de identificación del
     * empleador
     * @param periodo periodo liquidado
     * @param fechaInicio Rango inicial de la fecha de la liquidación
     * @param fechaFin Rango inicial de la fecha de la liquidación
     * @param numeroRadicacion numero de radicación de la liquidación
     * @return listado de liquidaciones que cumplen con los criterios de
     * búsqueda
     * @author jocampo
     *
     */
    public List<RegistroLiquidacionSubsidioDTO> exportarLiquidacionesPorEmpleador(
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, Date periodo, Date fechaInicio,
            Date fechaFin, String numeroRadicacion, UriInfo uri, HttpServletResponse response);

    /**
     * Consulta información de pagos de cada resultado de validaciones de
     * liquidación
     *
     * @param listaIdsRvl
     * @return
     * @author jocampo
     */
    public List<Object[]> consultarPagosPorResultadoValidacionLiquidacion(List<Long> listaIdsRvl);

    /**
     * Permite consultar la información de la vista 360 para liquidaciones de
     * fallecimiento
     *
     * @param numeroRadicado
     * @return
     * @author jocampo
     */
    public Object[] consultarInfoLiquidacionFallecimientoVista360(String numeroRadicado);

    /**
     * Metodo encargado de obtener la información
     *
     * @param tipoIdAdminSubsidio <code>TipoIdentificacionEnum</code> Tipo de
     * identificación del administrador del subsidio
     * @param numeroIdAdminSubsidio <code>String</code> Número de identificación
     * del administrador del subsidio
     * @return información del administrador del subsidio y sus grupos
     * familiares
     */
    public InformacionAdminSubsidioDTO obtenerInfoAdministradorSubsidio(TipoIdentificacionEnum tipoIdAdminSubsidio,
            String numeroIdAdminSubsidio);

    /**
     * Método encargado de obtener la información asociada a la Cuota Monetaria
     * por Afiliado.
     *
     * @param tipoIdPersona <code>TipoIdentificacionEnum</code> Tipo de
     * identificación del afiliado o beneficiario
     * @param numeroIdPersona <code>String</code> Número de identificación del
     * afiliado o beneficiario
     * @param periodo <code>Long</code> Periodo de los subsidios
     * @return La información de la cuota monetaria por afiliado o beneficiario
     */
    public SubsidioAfiliadoDTO obtenerCuotaMonetaria(TipoIdentificacionEnum tipoIdPersona, String numeroIdPersona, Long periodo);

    /**
     * Método que se encarga de buscar liquidaciones por trabajador
     *
     * @param tipoIdentificacion Valor del tipo de identificación del trabajador
     * @param numeroIdentificacion Valor del número de identificación del
     * trabajador
     * @param numerosRadicados numero de radicación de la liquidación
     * @return listado de pagos de liquidaciones que cumplen con los criterios
     * de búsqueda
     * @author jocampo
     */
    public List<Object[]> consultarPagosLiquidacionesPorTrabajador(
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, List<String> numerosRadicados);


    /**
     * Método que se encarga de buscar liquidaciones por trabajador
     *
     * @param tipoIdentificacion Valor del tipo de identificación del trabajador
     * @param numeroIdentificacion Valor del número de identificación del
     * trabajador
     * @param numerosRadicados numero de radicación de la liquidación
     * @return listado de pagos de liquidaciones que cumplen con los criterios
     * de búsqueda
     * @author jocampo
     */
    public List<Object[]> consultarPagosLiquidacionesPorTrabajadorPeriodo(
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, List<String> numerosRadicados,TipoIdentificacionEnum tipoIdentificacionEmpleador,String numeroIdentificacionEmpleador);

    /**
     * Metodo encargado de traer la información de
     *
     * @param lstIdPersona Lista con los identificadores de personas
     * @return Mapa con el numero de cedula y el correo de los destinarios
     */
    public Map<Long, AutorizacionEnvioComunicadoDTO> consultarDestinatariosComunicadosFallecimiento137_138(List<Long> lstIdPersona);

    /**
     * Metodo que obtiene los periodos y la liquidación respectiva en formato de
     * tabla para enviar a la HU TRA comunicados
     *
     * @param numeroRadicacion
     * @return
     */
    public String consultarPeriodosLiquidadosDispersionFallecimiento(String numeroRadicacion, String query);

    /**
     * Metodo que obtiene la información relevante de la ubicación sea del
     * administrador o del trabajador
     *
     * @param id
     * @return
     */
    public Object[] consultarInforUbicacionAdminTrabajadorDispersion508(Long id);

    /**
     * Metodo encargado de obtener las liquidaciones que pasan las validaciones
     * MASIVAS: deben estar en estado DISPERSADA o CERRADA ESPECIFICAS: deben
     * estar en estado CERRADA
     *
     * @param resultado Lista con las liquidaciones
     */
    public void obtenerLiquidacionesporTrabajadorVista360(List<ConsultaLiquidacionSubsidioMonetarioDTO> resultado);

    /**
     * Metodo que obtiene la trazabilidad de estados de una liquidacion
     * especifica
     *
     * @param id
     * @return
     */
    public List<TrazabilidadSubsidioEspecificoDTO> obtenerTrazabilidadSubsidioEspecifico(Long identificadorLiquidacion);

    /**
     * Metodo que obtiene informacion de la cuenta de confa para consignar
     * susbidios monetarios
     *
     * @return CuentaCCFModeloDTO
     */
    public CuentaCCFModeloDTO obtenerCuentaCCF();

    /**
     * Metodo que obtiene lista de bancos
     *
     * @return CuentaCCFModeloDTO
     */
    public List<BancoModeloDTO> obtenerBancos();

    /**
     * Método que se encarga de registrar una cuentaCCF
     *
     * @param CuentaCCFModeloDTO DTO con la información de la cuenta
     * @return Identificador de la CuentaCCFModeloDTO
     * @author dsuesca
     */
    public Long registrarCuentaCCF(CuentaCCFModeloDTO cuentaDTO);

    /**
     * Método que aumenta consecutivo a secuencia y devuelve el valor antes de
     * aumentar
     *
     * @param cantidadValores cantidad de valores que se aumentaran en la
     * secuencia
     * @param nombreSecuencia nombre de la secuencia
     * @return ultimo valor antes de aumentar consecutivo
     * @author dsuesca
     */
    public Long obtenerValorSecuencia(int cantidadValores, String nombreSecuencia);

    /**
     * Método que persiste los registros del archivo de bloqueo
     *
     * @param archivo
     * @param resultadoValidacion
     * @return ultimo valor antes de aumentar consecutivo
     * @author dsuesca
     */
    public Long persistirBloqueoBeneficiario(ArrayList<String[]> lineas, ResultadoValidacionArchivoBloqueoCMDTO resultadoValidacion, CargueArchivoBloqueoCMDTO cargue);

     /**
     * Método que persiste los registros del archivo de bloqueo trabajador - beneficio
     *
     * */
    public Long persistirBloqueoTrabajadorBeneficiario(ArrayList<String[]> lineas, ResultadoValidacionArchivoBloqueoCMDTO resultadoValidacion, CargueArchivoBloqueoCMDTO cargue);

    /**
     * Método que valida existencia de beneficiarios cargados para bloqueo,
     * elimina los registros que no existan en la db
     *
     * @param archivo
     * @param resultadoValidacion
     * @return dto con resultado de validaciones
     * @author dsuesca
     */
    public ResultadoValidacionArchivoBloqueoCMDTO validarExistenciaBeneficiarios(Long idCargueBloqueoCuotaMonetaria, UserDTO userDTO);

    /**
     * Método que radica bloqueo CM
     *
     * @param idCargueBloqueoCuotaMonetaria
     * @return registros afectados
     * @author dsuesca
     */
    public int radicarBloqueoCM(Long idCargueBloqueoCuotaMonetaria);

    /**
     * Método que cancela bloqueo CM
     *
     * @param idCargueBloqueoCuotaMonetaria
     * @return registros afectados
     * @author dsuesca
     */
    public int cancelarBloqueoCM(Long idCargueBloqueoCuotaMonetaria);

    /**
     * <b>Descripción:</b>Servicio que consulta el beneficiario o beneficiarios
     * que coincidan con los filtros de busqueda.</br>
     *
     * @param tipoIdentificacion tipo de identificación del beneficiario
     * @param numeroIdentificacion número de identificación del beneficiario
     * @param primerNombre primer nombre del beneficiario
     * @param segundoNombre segundo nombre del beneficiario
     * @param primerApellido primer apellido del beneficiario
     * @param segundoApellido segundo apellido del beneficiario
     * @return beneficiario o beneficiarios que coincidan con los filtros.
     * @author mosorio
     */
    public List<BeneficiarioModeloDTO> consultarBeneficiario(
            TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion, String primerNombre,
            String segundoNombre, String primerApellido,
            String segundoApellido, Date fechaNacimiento);

    /**
     * Método que persiste los registros del archivo de bloqueo
     *
     * @param archivo
     * @param resultadoValidacion
     * @return ultimo valor antes de aumentar consecutivo
     * @author dsuesca
     */
    public Long persistirBloqueoBeneficiario(CargueBloqueoCMDTO cargue);

    /**
     * Método que persiste los registros del archivo de bloqueo
     *
     * @param idCargue
     * @return id
     * @author dsuesca
     */
    public Long persistirBloqueoBeneficiarioAuditoria(Long idCargue, UserDTO userDTO);

    /**
     * Método que persiste los registros del archivo de bloqueo
     *
     * @param idBloqueo
     * @return id
     * @author dsuesca
     */
    public Long persistirDesbloqueoBeneficiarioAuditoria(Long idBloqueo, UserDTO userDTO);

    /**
     * Método que consulta los beneficiarios bloqueados
     *
     * @return lista de beneficiarios bloqueados
     * @author dsuesca
     */
    public List<BloqueoBeneficiarioCuotaMonetariaDTO> consultarBeneficiariosBloqueadosCoreFiltros(ConsultaBeneficiarioBloqueadosDTO consulta);

    /**
     * Método que desbloquea beneficiarios pára cuota monetaria
     *
     * @param idsBloqueoBeneficiarioCuotaMonetaria
     * @return retgistros afectados
     * @author dsuesca
     */
    public int desbloquearBeneficiariosCMCore(List<Long> idBeneficiarioBloqueados, UserDTO userDTO);

    /**
     * Consulta el tipo de liquidacion que se quiere aprobar. CC Liquidacion
     * paralela
     *
     * @param numeroRadicado
     * @return
     */
    public String consultarTipoLiquidacionParalela(String numeroRadicado);

    public VerificarPersonasSinCondicionesDTO consultarPersonaLiquidacionEspecifica(String numeroRadicado);

    /**
     * Consulta el tipo de liquidacion que se quiere aprobar. CC Liquidacion
     * paralela
     *
     * @param numeroRadicado
     * @return
     */
    public List<RolAfiliadoDTO> consultareEmpleadorPorPersonaTrabajador(Long idPersona);

    public Boolean consultarBeneficiariosExistentes(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion);

    /**
     * Consulta los beneficiarios bloqueados en la carga de archivo de bloqueo o
     * por pantalla
     *
     * @return
     */
    public List<Object[]> consultarBeneficiarioBloqueadosCore();

    public Boolean consultarExistenciaBeneficiariosBloqueadosCore();

    public Date consultarBeneficiarioFallecidoPorNumeroRadicado(String numeroRadicado);

    /**
     * Consulta las entidades de descuento asociadas en el sistema.
     *
     * @return
     */
    public Map<Long, String> consultarEntidadesDescuento();

    /**
     * Consulta SERVICIO WEB SUBSIDIO EN ESPECIE LIQUIDACIÓN MANUAL - CONFA GLPI
     * 57870
     *
     * @param tipoIdentificacionAfiliado
     * @param numeroIdentificacionAfiliado
     * @param Periodo
     * @return
     */
    public List<EspecieLiquidacionManualDTO> consultarSubsidioEspecieLiquidacionManualCore(TipoIdentificacionEnum tipoIdentificacionAfiliado, String numeroIdentificacionAfiliado, String periodo);

    /**
     * Consulta SERVICIO WEB SUBSIDIO - ANTIOQUIA GLPI 57020
     *
     * @param tipoIdentificacionAfiliado
     * @param numeroIdentificacionAfiliado
     * @return
     */
    public List<CuotaMonetariaIVRDTO> consultarCuotaMonetariaCanalIVRCore(TipoIdentificacionEnum tipoIdentificacionAfiliado, String numeroIdentificacionAfiliado);

    /**
     * @param fechaInicio
     * @param fechaFin
     * @param tipoIdentificacion
     * @param identificacion
     * @param periodoRegular
     * @param numeroOperacion
     * @param medioPago
     * @param fechaProgramada
     * @return
     */
    public List<InformacionLiquidacionFallecimientoDTO> consultarLiquidacionFallecimiento(Long fechaInicio, Long fechaFin, String tipoIdentificacion, String identificacion, Long periodoRegular, String numeroOperacion, String medioPago, Long fechaProgramada);

    /** Interface para metodo consulta de liquidacion masiva sin derecho */
    public List<RegistroSinDerechoSubsidioDTO> generarDataLiquidaciomSinDerecho(String numeroRadicacion);


    public BigDecimal getMontoRetiroPorLiquidacionTrabajador(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado,
    TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador, String numerosRadicados);

    public void actualizarEcmArchivoLiquidacionSubsidio(String numeroRadicacion, String codigoECM);

    public SolicitudLiquidacionSubsidioModeloDTO consultarLiquidacionEnProceso();

        /**
     * Metodo encargado de obtener la información del subsidio por Afiliado o beneficiario
     * @param tipoIdPersona
     *        <code>TipoIdentificacionEnum</code>
     *        Tipo de identificación del afiliado o beneficiario
     * @param numeroIdAfiliado
     *        <code>String</code>
     *        Número de identificación del afiliado
     * @param numeroIdBeneficiario
     *        <code>String</code>
     *        Número de identificación del beneficiario        
     * @return información del subsidio por afiliado o beneficiario
     */
    public SubsidioAfiliadoDTO obtenerInfoSubsidio(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado, String numeroIdBeneficiario);

public Boolean validarExistenciaRelacionAfiliadoBeneficiario(String tipoIdentificacionAfiliado, String numeroIdentificacionAfiliado, String tipoIdentificacionBeneficiario, String numeroIdentificacionBeneficiario);

}
