package com.asopagos.afiliaciones.service;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.afiliaciones.dto.ActualizacionEstadoListaEspecialDTO;
import com.asopagos.afiliaciones.dto.CertificadoEscolaridadOutDTO;
import com.asopagos.afiliaciones.dto.ConsultarInformacionCompletaAfiliadoDTO;
import com.asopagos.afiliaciones.dto.ConsultarInformacionCompletaBeneficiarioDTO;
import com.asopagos.afiliaciones.dto.HistoricoAfiliacion360DTO;
import com.asopagos.afiliaciones.dto.InfoHijoBiologicoOutDTO;
import com.asopagos.afiliaciones.dto.IntentoAfiliacionEmpleador360DTO;
import com.asopagos.afiliaciones.dto.IntentoAfiliacionInDTO;
import com.asopagos.afiliaciones.dto.ListaEspecialRevisionDTO;
import com.asopagos.afiliaciones.dto.RelacionTrabajadorEmpresaDTO;
import com.asopagos.dto.AfiliadoNovedadRetiroNoAplicadaDTO;
import com.asopagos.dto.NumeroRadicadoCorrespondenciaDTO;
import com.asopagos.dto.ProductoNoConformeDTO;
import com.asopagos.dto.SolicitudAfiliacionPersonaDTO;
import com.asopagos.dto.SolicitudDTO;
import com.asopagos.dto.modelo.IntentoAfiliacionDTO;
import com.asopagos.dto.modelo.SolicitudAfiliacionPersonaModeloDTO;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.personas.ListaEspecialRevision;
import com.asopagos.enumeraciones.afiliaciones.EstadoDocumentacionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoEtiquetaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.afiliaciones.dto.InfoPadresBiologicosOutDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.novedades.dto.ArchivoSupervivenciaDTO;
import com.asopagos.dto.ConsolaEstadoCargueProcesoDTO;
import com.asopagos.afiliaciones.dto.InfoPersonasEnRelacionHijosOutDTO;
import com.asopagos.entidades.ccf.afiliaciones.ArchivosTrasladosEmpresasCCF;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response; 



/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de empleadores <b>Módulo:</b> Asopagos - transversal<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author Jerson Zambrano
 *         <a href="jzambrano:jzambrano@heinsohn.com.co"> jzambrano</a>
 *
 * @author <a href="mailto:jopinzon@heinsohn.com.co"> jopinzon</a>
 */
@Path("afiliaciones")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AfiliacionesService {

	/**
	 * Pendiente por definir, no se encuentra necesidad en HU
	 */
	@POST
	@Path("/asignarSolicitudAlBack")
	public void asignarSolicitudAlBack();

	/**
	 * Valida si una eyiqueta puede ser usada para una caja
	 *
	 * @param codigoEtiqueta
	 *                       Codigo de etiqueta que se pretende usar
	 * @return retorna los posibles estado de la etiqueta:
	 *         EXITO, ESTA_USO, NO_EXISTE_CODIGO
	 */
	@POST
	@Path("/etiquetas/{codigoEtiqueta}")
	public String validarEtiqueta(@PathParam("codigoEtiqueta") String codigoEtiqueta);

	/**
	 * Metodo que crea el registro de intento de afiliación
	 *
	 * @param intentoAfiliacionInDTO
	 * @param userDTO,
	 *                               usuario autenticado
	 * @return El id del registro de intento de afiliación
	 */
	@POST
	@Path("intentosAfiliacion")
	public Long registrarIntentoAfliliacion(IntentoAfiliacionInDTO intentoAfiliacionInDTO, @Context UserDTO userDTO);

	/**
	 * Método que radica la solicitud enviada por la sede indicada
	 * 
	 * <ul>
	 * <li>número de radicado CCAAAA###### donde:
	 * <ul>
	 * <li>CC: código de la caja de compensación</li>
	 * <li>AAAA: año</li>
	 * <li>######: número de consecutivo (se reinicia cada año)</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * 
	 * @param idSolicitud
	 * @param sccID
	 * @param userDTO,
	 *                    usuario autenticado
	 * @return numero de la radicacion de la solicitud
	 */
	@POST
	@Path("/radicarSolicitud/{idSolicitud}")
	public String radicarSolicitud(@PathParam("idSolicitud") Long idSolicitud, @QueryParam("sede") String sccID,
			@Context UserDTO userDTO);

	/**
	 * Método que consulta las solicitudes de afiliación de empleador por
	 * coincidencia del número de radicado
	 * 
	 * @param numeroRadicado,
	 *                        numero de radicado
	 * @return listado de solicitudes
	 */
	@GET
	@Path("/solicitudes")
	public List<SolicitudDTO> buscarSolicitud(@QueryParam("numeroRadicado") String numeroRadicado);

	/**
	 * Busca una solicitud pos su ID
	 * 
	 * @param idSolicitud,
	 *                     identificador de la solicitud
	 * @return la solicitud
	 */
	@GET
	@Path("/solicitudes/{idSolicitud}")
	public Solicitud buscarSolicitudPorId(@PathParam("idSolicitud") Long idSolicitud);

	/**
	 * Actualiza una solicitud
	 * 
	 * @param idSolicitud,
	 *                     identificador de la solicitud
	 * @param solicitud
	 */
	@POST
	@Path("/actualizarSolicitud")
	public void actualizarSolicitud(@QueryParam("idSolicitud") Long idSolicitud, Solicitud solicitud);

	/**
	 * <b>Descripcion</b>Metodo que se encarga de actualizar el estado de la
	 * socilictud del empleador
	 *
	 * @param idSolicitudAfiliacion,
	 *                               identificador que permite buscar la solicitud
	 * @param estado
	 *                               Referencia al estado al cual se va actualizar
	 *                               la solicitud de
	 *                               afiliacion del empleador
	 */
	@PUT
	@Path("/{idSolicitudGlobal}/estadoDocumentacion")
	public void actualizarEstadoDocumentacionAfiliacion(@PathParam("idSolicitudGlobal") Long idSolicitudGlobal,
			EstadoDocumentacionEnum estado);

	/**
	 * Metodo que crea un producto no conforme asociado a una solicitud de
	 * afiliaciòn
	 *
	 * @param idSolicitudAfiliacion
	 *                              Identificador de solicitudes
	 * @param productoNoConforme
	 *                              recursos que se desea guardar.
	 * @return Retorna el identificador del recurso persistido en base de datos.
	 */
	@POST
	@Path("/{idSolicitudAfiliacion}/productoNoConforme")
	public List<Long> crearProductosNoConforme(@PathParam("idSolicitudAfiliacion") Long idSolicitudAfiliacion,
			List<ProductoNoConformeDTO> productosNoConforme);

	/**
	 * Metodo encargado de actualizar una producto no conforme de una solitud
	 * dada.
	 *
	 * @param idSolicitudAfiliacion
	 *                              Identificador de solicitudes
	 * @param idProductoNoConforme
	 *                              identificador del producto no conforme.
	 * @param productoNoConforme,
	 *                              recursos que se desea actualizar
	 */
	@PUT
	@Path("/{idSolicitudAfiliacion}/productoNoConforme/{idProductoNoConforme}")
	public void actualizarProductoNoConforme(@PathParam("idSolicitudAfiliacion") Long idSolicitudAfiliacion,
			@PathParam("idProductoNoConforme") Long idProductoNoConforme, ProductoNoConformeDTO productoNoConforme);

	/**
	 * Metodo encargado de actualizar una producto no conforme de una solitud
	 * dada.
	 *
	 * @param idSolicitudAfiliacion
	 *                               Identificador de solicitudes
	 * @param listProductoNoConforme
	 *                               recursos que se desea actualizar.
	 */
	@PUT
	@Path("/{idSolicitudAfiliacion}/productoNoConforme")
	public void actualizarProductoNoConforme(@PathParam("idSolicitudAfiliacion") Long idSolicitudAfiliacion,
			List<ProductoNoConformeDTO> listProductoNoConforme);

	/**
	 * Metodo que conuslta los productos no conformes de una solicitud, que
	 * pueden estar resueltos o no resueltos
	 *
	 * @param idSolicitudAfiliacion
	 *                              recursos que se desea actualizar.
	 * @param resuelto
	 *                              Estado actual que indica si esta resuelto o no
	 * @return Lista de productos no confome segun los criterios de busqueda
	 */
	@GET
	@Path("/{idSolicitudAfiliacion}/productoNoConforme")
	public List<ProductoNoConformeDTO> consultarProductosNoConforme(
			@PathParam("idSolicitudAfiliacion") Long idSolicitudAfiliacion, @QueryParam("resuelto") Boolean resuelto);

	/**
	 * Método encargado de eliminar un producto no conforme de una solitud dada.
	 *
	 * @param idSolicitudAfiliacionEmpleador
	 *                                       Identificador de solicituds de
	 *                                       afiliacion de un empleador
	 * @param idProductoNoConforme,
	 *                                       identificador del producto no conforme
	 */
	@DELETE
	@Path("/{idSolicitudAfiliacion}/productoNoConforme/{idProductoNoConforme}")
	public void eliminarProductoNoConforme(@PathParam("idSolicitudAfiliacion") Long idSolicitudAfiliacion,
			@PathParam("idProductoNoConforme") Long idProductoNoConforme);

	/**
	 * Metodo encargado de guardar la instancia de proceso
	 * 
	 * @param idSolicitudGlobal,
	 *                            identificador de la solicitud
	 * @param idInstanciaProceso,
	 *                            identificador de la solicitud global
	 */
	@PUT
	@Path("/{idSolicitudGlobal}/instanciaProceso")
	public void guardarInstanciaProceso(@PathParam("idSolicitudGlobal") Long idSolicitudGlobal,
			String idInstanciaProceso);

	@GET
	@Path("/generarIdentificadorCargaMultiple")
	public String generarIdentificadorCargaMultiple(@Context UserDTO userDTO);

	/**
	 * Metodo encargado de registrar una persona en lista de especial revision
	 * 
	 * @param listaEspecialRevisionDTO,
	 *                                  dto que contiene los datos a registrar
	 * @return retorna la lista de especial revision
	 */
	@POST
	@Path("/registarListaEspecial")
	public ListaEspecialRevision registrarPersonaEnListaEspecialRevision(
			ListaEspecialRevisionDTO listaEspecialRevisionDTO,
			@Context UserDTO userDTO);

	/**
	 * Servicio encargado de consultar la ListaEspecialRevision de manera
	 * dinamica
	 * 
	 * @param tipoIdentificacion,
	 *                               tipo de identificacion
	 * @param numeroIdentificacion,
	 *                               número de identificacion
	 * @param digitoVerificacion,
	 *                               digito de verificacion
	 * @param fechaInicio,
	 *                               fecha Inicio
	 * @param fechaFin,
	 *                               fecha fina
	 * @param nombreEmpleador,nombre
	 *                               el empleador (razoSocial)
	 * @return retorna el DTO ListaEspecialRevisionDTO
	 */
	@GET
	@Path("/consultarListaEspecialRevision")
	public List<ListaEspecialRevisionDTO> consultarListaEspecialRevision(
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion,
			@QueryParam("digitoVerificacion") Byte digitoVerificacion,
			@QueryParam("fechaInicioInclusion") Long fechaInicio,
			@QueryParam("fechaFinInclusion") Long fechaFin, @QueryParam("nombreEmpleador") String nombreEmpleador);

	/**
	 * Servicio encargado de cambiar el estado del registro de la lista
	 * 
	 * @param tipoIdentificacion,
	 *                                            tipo de identificacion
	 * @param numeroIdentificacion,
	 *                                            numero de identificacion
	 * @param digitoVerificacion,
	 *                                            digito de verificacion
	 * @param actualizacionEstadoListaEspecialDTO
	 * @param userDTO
	 */
	@PUT
	@Path("/cambiarEstadoRegistroListaEspecial")
	public void cambiarEstadoRegistroLista(ListaEspecialRevision lista, @Context UserDTO userDTO);

	/**
	 * Este servicio consulta la ultima solicitud aprobada de una persona dado el id
	 * del rol afiliado
	 * 
	 * @param idRolAfiliado
	 * @param userDTO
	 * @return
	 */
	@GET
	@Path("/consultarUltimaAfiliacionPersona")
	public SolicitudAfiliacionPersonaDTO consultarUltimaAfiliacionPersona(
			@QueryParam("idRolAfiliado") Long idRolAfiliado,
			@Context UserDTO userDTO);

	/**
	 * Este servicio consulta la ultima solicitud afiliación de Persona por el id
	 * del rol afiliado
	 * 
	 * @param idRolAfiliado
	 * @return SOlicitud
	 */
	@GET
	@Path("/consultarUltimaSolicitudAfiliacion")
	public SolicitudAfiliacionPersonaModeloDTO consultarUltimaSolicitudAfiliacion(
			@QueryParam("idRolAfiliado") Long idRolAfiliado);

	/**
	 * Este servicio actualiza la SOlicitud de Afiliación Persona, se actualiza como
	 * llegan
	 * los datos al DTO deben tener asociado el Id de la Entidad
	 * SolicitudAfiliacionPersona
	 * 
	 * @param solicitudAfiliacionDTO
	 */
	@POST
	@Path("/actualizarSolicitudAfiliacionPersona")
	public void actualizarSolicitudAfiPersona(SolicitudAfiliacionPersonaModeloDTO solicitudAfiliacionDTO);

	/**
	 * Este servicio consulta la la causa de intento fallido de afiliacion
	 * 
	 * @param idSolicitud
	 * @return IntentoAfiliacionDTO
	 */
	@GET
	@Path("/consultarIntentoAfiliacion")
	public IntentoAfiliacionDTO consultarIntentoAfiliacion(
			@NotNull @Min(value = 1L) @QueryParam("idSolicitud") Long idRolAfiliado);

	/**
	 * <b>Descripción:</b> método que se encuarga de consultar el municipio dado su
	 * código
	 * 
	 * @param codigoMunicipio el código del municipio que se busca
	 * @return Short con el id del municipio encontrado
	 */
	@GET
	@Path("/buscarMunicipio/{codigoMunicipio}")
	public Short buscarMunicipio(@PathParam("codigoMunicipio") String codigoMunicipio);

	/**
	 * Servicio encargado de consultar el id de la solicitud global dado el id de un
	 * rol afiliado
	 * 
	 * @param idRolAfiliado
	 *                      identificador del rol afiliado con el cual se realizará
	 *                      la busqueda
	 * 
	 * @return Long con el id de la solicitud general
	 */
	@POST
	@Path("/consultarIdSolicitudGlobal")
	public Long consultarIdSolicitudGlobal(Long idRolAfiliado);

	/**
	 * Método encargado de consultar la clasificación de la persona
	 * 
	 * @param tipoIdentificacion,   tipo de identificación de la persona
	 * @param numeroIdentificacion, número de identificación del persona
	 * @return retorna la clasificación
	 */
	@GET
	@Path("/consultarUltimaClasificacionPersona")
	public ClasificacionEnum consultarUltimaClasificacionPersona(
			@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);

	/**
	 * Servicio para la radicacion de la lista de solicitudes enviadas por
	 * parametro<br>
	 * Se genera el numero de radicado así:
	 * <ul>
	 * <li>número de radicado CCAAAA###### donde:
	 * <ul>
	 * <li>CC: código de la caja de compensación</li>
	 * <li>AAAA: año</li>
	 * <li>######: número de consecutivo (se reinicia cada año)</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * 
	 * @param listSolicitudes
	 *                        Lista solicitudes
	 * @param sccID
	 *                        Informacion sede que radica la solicitud
	 * @param userDTO
	 *                        Informacion usuario logueado
	 * @return Mapa con solicitud y numero radicado
	 */
	@POST
	@Path("/radicarListaSolicitudes")
	public Map<String, String> radicarListaSolicitudes(@QueryParam("sede") String sccID, List<Long> listSolicitudes,
			@Context UserDTO userDTO);

	/**
	 * Servicio encargado de consultar el historico de intentos de afiliación del
	 * empleador.
	 * 
	 * @param tipoIdentificacion
	 *                             tipo de identificación de la persona relacionada
	 *                             al empleador/empresa.
	 * 
	 * @param numeroIdentificacion
	 *                             numero de identificacion de la persona
	 *                             realcionada al empleador/empresa.
	 * 
	 * @return List<IntentoAfiliacionEmpleador360DTO> con la información solicitada.
	 * 
	 * @author squintero
	 */
	@GET
	@Path("/consultarIntentosAfiliacionEmpleador")
	public List<IntentoAfiliacionEmpleador360DTO> consultarIntentosAfiliacionEmpleador(
			@QueryParam("numeroIdentificacion") String numeroIdentificacion,
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion);

	/**
	 * Método que consulta las solicitudes por coincidencia del número de radicado
	 * para el proceso de recepcion de documentación fisica
	 * 
	 * @param numeroRadicado,
	 *                        numero de radicado
	 * @return listado de solicitudes
	 */
	@GET
	@Path("/solicitudes/recepcionDocumento")
	public List<SolicitudDTO> buscarSolicitudRecepcionDocumento(
			@NotNull @QueryParam("numeroRadicado") String numeroRadicado);

	/**
	 * Servicio encargado de consultar el Histórico de solicitudes de afiliación de
	 * la
	 * persona dada.
	 * 
	 * @param tipoIdAfiliado
	 *                         tipo de identificación de la persona.
	 * 
	 * @param numeroIdAfiliado
	 *                         numero de identificacion de la persona.
	 * 
	 * @param idEmpleador
	 *                         id en tabla del empleador.
	 * 
	 * @return List<HistoricoAfiliacion360DTO> con el listado de solicitudes
	 *         de afiliación de la persona con respecto al empleador.
	 */
	@GET
	@Path("/consultarHistoricoAfiliacionDependiente")
	public List<HistoricoAfiliacion360DTO> consultarHistoricoAfiliacionDependiente(
			@QueryParam("tipoIdAfiliado") TipoIdentificacionEnum tipoIdAfiliado,
			@QueryParam("numeroIdAfiliado") String numeroIdAfiliado,
			@QueryParam("idEmpleador") Long idEmpleador);

	/**
	 * Servicio encargado de consultar el Histórico de solicitudes de afiliación
	 * como independiente
	 * de la persona dada.
	 * 
	 * @param tipoIdAfiliado
	 *                         tipo de identificación de la persona.
	 * 
	 * @param numeroIdAfiliado
	 *                         numero de identificacion de la persona.
	 * 
	 * @return List<HistoricoAfiliacion360DTO> con el listado de solicitudes
	 *         de afiliación de la persona.
	 */
	@GET
	@Path("/consultarHistoricoAfiliacionIndependiente")
	public List<HistoricoAfiliacion360DTO> consultarHistoricoAfiliacionIndependiente(
			@QueryParam("tipoIdAfiliado") TipoIdentificacionEnum tipoIdAfiliado,
			@QueryParam("numeroIdAfiliado") String numeroIdAfiliado);

	/**
	 * Servicio encargado de consultar el Histórico de solicitudes de afiliación
	 * como pensionado
	 * de la persona dada.
	 * 
	 * @param tipoIdAfiliado
	 *                         tipo de identificación de la persona.
	 * 
	 * @param numeroIdAfiliado
	 *                         numero de identificacion de la persona.
	 * 
	 * @return List<HistoricoAfiliacion360DTO> con el listado de solicitudes
	 *         de afiliación de la persona.
	 */
	@GET
	@Path("/consultarHistoricoAfiliacionPensionado")
	public List<HistoricoAfiliacion360DTO> consultarHistoricoAfiliacionPensionado(
			@QueryParam("tipoIdAfiliado") TipoIdentificacionEnum tipoIdAfiliado,
			@QueryParam("numeroIdAfiliado") String numeroIdAfiliado);

	/**
	 * Servicio encargado de consultar la lista de especial revision en base al id
	 */
	@PUT
	@Path("/consultarRegistroListaEspecialRevision")
	public ListaEspecialRevision consultarRegistroListaEspecialRevision(
			ActualizacionEstadoListaEspecialDTO actualizacionEstadoListaEspecialDTO,
			@Context UserDTO userDTO);

	/**
	 * Servicio que genera y retonra la información de un numero de radicado para
	 * asignar
	 * 
	 * @param tipoEtiqueta, tipo de radicado de solicitud o de correspondencia
	 *                      fisica
	 * @param cantidad,     cantidad de radicados a generar
	 * @param userDTO
	 * @return NumeroRadicadoCorrespondenciaDTO
	 */
	@GET
	@Path("/obtenerNumeroRadicadoCorrespondencia")
	public NumeroRadicadoCorrespondenciaDTO obtenerNumeroRadicadoCorrespondencia(
			@NotNull @QueryParam("tipoEtiqueta") TipoEtiquetaEnum tipoEtiqueta,
			@NotNull @QueryParam("cantidad") Integer cantidad,
			@Context UserDTO userDTO);

	/**
	 * Asocia el comunicado envíado al momento de registrar el intento de afiliación
	 * 
	 * @param intentoAfiliacion
	 * @param idComunicado
	 */
	@POST
	@Path("/asociarComunicadoIntentoAfiliacion")
	public void asociarComunicadoIntentoAfiliacion(@NotNull @QueryParam("IdIntentoAfiliacion") Long intentoAfiliacion,
			@NotNull @QueryParam("idComunicado") Long idComunicado);

	@GET
	@Path("/utilitarioDatosLiquidacion0620")
	public Integer utilitarioDatosLiquidacion0620();

	/**
	 * CC Vistas 360
	 * 
	 * @param tipoID:         Tipo de identificación.
	 * @param identificacion: Número de identificación del afiliado.
	 * 
	 * @return InfoPadresBiologicosOutDTO
	 * 
	 * @author squintero
	 */
	@GET
	@Path("/obtenerPadresBiologicosPersona")
	public InfoPadresBiologicosOutDTO obtenerPadresBiologicosPersona(
			@NotNull @QueryParam("tipoID") TipoIdentificacionEnum tipoID,
			@NotNull @QueryParam("identificacion") String identificacion);

	/**
	 * CC Vistas 360
	 * 
	 * @param tipoID:         Tipo de identificación.
	 * @param identificacion: Número de identificación del afiliado.
	 * 
	 * @return InfoPadresBiologicosOutDTO
	 * 
	 * @author squintero
	 */
	@GET
	@Path("/obtenerHijosBiologicosPersona")
	public List<InfoHijoBiologicoOutDTO> obtenerHijosBiologicosPersona(
			@NotNull @QueryParam("tipoID") TipoIdentificacionEnum tipoID,
			@NotNull @QueryParam("identificacion") String identificacion);

	/**
	 * 
	 * @param tipoID
	 * @param identificacion
	 * @return
	 */
	@GET
	@Path("obtenerCertificadosEscolaridad")
	public List<CertificadoEscolaridadOutDTO> obtenerCertificadosEscolaridad(
			@NotNull @QueryParam("tipoID") TipoIdentificacionEnum tipoID,
			@NotNull @QueryParam("identificacion") String identificacion);

	/**
	 * Relaciona la fecha de creación del certificado escolar (aud) al registro en
	 * core
	 * 
	 * @return Catidad de registros procesados
	 */
	@GET
	@Path("/actualizarFechaCreacionCertificadoEscolar")
	public Integer actualizarFechaCreacionCertificadoEscolar();

	/**
	 * Persiste el certificado con la fecha de creación
	 * 
	 * @param idCertificado
	 */
	@POST
	@Path("/persistirFechaCreacionCertificado")
	public void persistirFechaCreacionCertificado(@QueryParam("idCertificado") Long idCertificado);

	/**
	 * Consulta los trabajadores activos relacionados con empleadores inactivos
	 */
	@GET
	@Path("/consultarTrabajadoresActivosConEmpleadorInactivo")
	public List<RelacionTrabajadorEmpresaDTO> consultarTrabajadoresActivosConEmpleadorInactivo();

	@GET
	@Path("/obtenerNovedadesRetiroNoProcesadasPila")
	public List<AfiliadoNovedadRetiroNoAplicadaDTO> obtenerNovedadesRetiroNoProcesadasPila();

	@GET
	@Path("/marcarNovedadesRetiroNoProcesadasPila")
	public void marcarNovedadesRetiroNoProcesadasPila(@QueryParam("id") Long id);

	@GET
	@Path("/consultarInformacionCompletaAfiliado")
	public List<ConsultarInformacionCompletaAfiliadoDTO> consultarInformacionCompletaAfiliadoConfa(
			@QueryParam("tipoIdentificacionAfiliado") TipoIdentificacionEnum tipoIdentificacionAfiliado,
			@QueryParam("numeroIdentificacionAfiliado") String numeroIdentificacionAfiliado);

	/* Consulta para traer la informacion completa de beneficiario confa */
	@GET
	@Path("/consultarInformacionCompletaBeneficiario")
	public List<ConsultarInformacionCompletaBeneficiarioDTO> consultarInformacionCompletaBeneficiarioConfa(
			@QueryParam("tipoIdentificacionBeneficiario") TipoIdentificacionEnum tipoIdentificacionBeneficiario,
			@QueryParam("numeroIdentificacionBeneficiario") String numeroIdentificacionBeneficiario);


	@GET
	@Path("/obtenerPersonasEnRelacionHijos")
	public List<InfoPersonasEnRelacionHijosOutDTO> obtenerPersonasEnRelacionHijos(
			@NotNull @QueryParam("tipoIdentificacionAfiliado") TipoIdentificacionEnum tipoIdentificacionAfiliado,
			@NotNull @QueryParam("numeroIdentificacionAfiliado") String numeroIdentificacionAfiliado,
			@QueryParam("tipo") Boolean tipo);

	/**
	 * Persiste el certificado con la fecha de creación
	 * 
	 * @param archivoSupervivenciaDTO
	 */
	@POST
	@Path("/actualizarCargueSupervivencia")
	public Long actualizarCargueSupervivencia(ArchivoSupervivenciaDTO archivoSupervivenciaDTO);
	/**
	 * Persiste el certificado con la fecha de creación
	 * 
	 * @param archivoSupervivenciaDTO
	 */
	@GET
	@Path("/consultarUltimaClasificacionCategoria")
	public String consultarUltimaClasificacionCategoria( 
		@QueryParam("numeroDocumento") String numeroDocumento, 
		@QueryParam("tipoDocumento") TipoIdentificacionEnum tipoDocumento);

	@GET
	@Path("/consultarDepartamentoPorIdMunicipio/{codigoMunicipio}")
	public Departamento consultarDepartamentoPorIdMunicipio(@PathParam("codigoMunicipio") Short codigoMunicipio);

	@POST
	@Path("/desistirSolicitudAfiliacion")
    public void desistirSolicitudAfiliacion();

	@POST
	@Path("/crearArchivoTrasladosCFF")
    public ArchivosTrasladosEmpresasCCF crearArchivoTrasladosCFF(
	@QueryParam("IdCargueC") Long IdCargueC, 
	@QueryParam("idEmpleadorE") Long idEmpleadorE,
	String datosTemporales);

	@GET
	@Path("/consultarArchivosTrasladosCCF")
    public List<ArchivosTrasladosEmpresasCCF> consultarArchivosTrasladosCCF(
		@NotNull @QueryParam("numeroDocumentoEmpleador") String numeroDocumentoEmpleador,
		@NotNull @QueryParam("tipoDocumentoEmpleador") TipoIdentificacionEnum tipoDocumentoEmpleador);


	@POST
	@Path("/cancelarCarguesEmpleador")
    public void cancelarCarguesEmpleador(
		@NotNull @QueryParam("numeroDocumentoEmpleador") String numeroDocumentoEmpleador,
		@NotNull @QueryParam("tipoDocumentoEmpleador") TipoIdentificacionEnum tipoDocumentoEmpleador);

	@GET
	@Path("/validarNumeroIdentificacionExistente")
    public Boolean validarNumeroIdentificacionExistente( @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
		@NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);
	
}
