package com.asopagos.legalizacionfovis.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.asopagos.dto.fovis.AnticipoLegalizacionDesembolsoDTO;
import com.asopagos.dto.fovis.CondicionesLegalizacionDesembolsoDTO;
import com.asopagos.dto.fovis.ConsultarSubsidiosFOVISLegalizacionDTO;
import com.asopagos.dto.fovis.DocumentoSoporteOferenteDTO;
import com.asopagos.dto.fovis.DocumentoSoporteProveedorDTO;
import com.asopagos.dto.fovis.DocumentoSoporteProyectoViviendaDTO;
import com.asopagos.dto.fovis.HistoricoLegalizacionFOVISDTO;
import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import com.asopagos.dto.fovis.VisitaDTO;
import com.asopagos.dto.modelo.AhorroPrevioModeloDTO;
import com.asopagos.dto.modelo.CondicionVisitaModeloDTO;
import com.asopagos.dto.modelo.DocumentoSoporteModeloDTO;
import com.asopagos.dto.modelo.IntentoLegalizacionDesembolsoModeloDTO;
import com.asopagos.dto.modelo.IntentoLegalizacionDesembolsoRequisitoModeloDTO;
import com.asopagos.dto.modelo.JefeHogarModeloDTO;
import com.asopagos.dto.modelo.LegalizacionDesembolsoModeloDTO;
import com.asopagos.dto.modelo.LicenciaDetalleModeloDTO;
import com.asopagos.dto.modelo.LicenciaModeloDTO;
import com.asopagos.dto.modelo.OferenteModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.ProyectoSolucionViviendaModeloDTO;
import com.asopagos.dto.modelo.RecursoComplementarioModeloDTO;
import com.asopagos.dto.modelo.SolicitudLegalizacionDesembolsoModeloDTO;
import com.asopagos.dto.modelo.VisitaModeloDTO;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudLegalizacionDesembolsoEnum;
import com.asopagos.enumeraciones.fovis.TipoAhorroPrevioEnum;
import com.asopagos.enumeraciones.fovis.TipoRecursoComplementarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.legalizacionfovis.dto.SolicitudPostulacionLegalizacionDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de del Proceso de Legalizacion FOVIS <b>Historia de Usuario:</b> Proceso 3.2.4
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@Path("legalizacionFovis")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface LegalizacionFovisService {

    /**
     * 
     * Servicio que crea o actualiza la licencia en la tabla
     * <code>Licencia</code>
     * 
     * @param licenciaDTO
     *        Información del registro a crear/actualizar
     * @return El registro actualizado
     */
    @POST
    @Path("/crearActualizarLicencia")
    LicenciaModeloDTO crearActualizarLicencia(@NotNull LicenciaModeloDTO licenciaDTO);

    /**
     * Servicio que crea o actualiza el detalle de la licencia en la tabla
     * <code>LicenciaDetalle</code>
     * 
     * @param licenciaDetalleDTO
     *        Información del registro a crear/actualizar
     * @return El registro actualizado
     */
    @POST
    @Path("/crearActualizarDetalleLicencia")
    LicenciaDetalleModeloDTO crearActualizarDetalleLicencia(@NotNull LicenciaDetalleModeloDTO licenciaDetalleDTO);

    /**
     * Servicio que crea los registros documentales del oferente
     * <code>DocumentoSoporteOferente</code>
     * 
     * @param DocumentoSoporteOferenteDTO
     *        Información del registro a crear/actualizar
     * @return El registro actualizado
     */
    @POST
    @Path("/registrarRequisitosDocumentalesOferente")
    DocumentoSoporteOferenteDTO registrarRequisitosDocumentalesOferente(@NotNull DocumentoSoporteOferenteDTO documentoSoporteOferenteDTO);
    
    /**
     * Servicio que crea los registros documentales del proveedor
     * <code>DocumentoSoporteProveedor</code>
     * 
     * @param documentoSoporteProveedorDTO
     *        Información del registro a crear/actualizar
     * @return El registro actualizado
     */
    @POST
    @Path("/registrarRequisitosDocumentalesProveedor")
    DocumentoSoporteProveedorDTO registrarRequisitosDocumentalesProveedor(@NotNull DocumentoSoporteProveedorDTO documentoSoporteProveedorDTO);

    
    /**
     * Servicio que crea el registro de los documentos soporte
     * <code>DocumentoSoporte</code>
     * 
     * @param DocumentoSoporteModeloDTO
     *        Información del registro a crear/actualizar
     * @return El registro actualizado
     */
    @POST
    @Path("/registrarDocumentoSoporte")
    DocumentoSoporteModeloDTO registrarDocumentoSoporte(@NotNull DocumentoSoporteModeloDTO documentoSoporteDTO);

    /**
     * Servicio que crea los registros documentales del proyecto de vivienda
     * <code>DocumentoSoporteProyectoVivienda</code>
     * 
     * @param DocumentoSoporteOferenteDTO
     *        Información del registro a crear/actualizar
     * @return El registro actualizado
     */
    @POST
    @Path("/registrarRequisitosDocumentalesProyectoVivienda")
    DocumentoSoporteProyectoViviendaDTO registrarRequisitosDocumentalesProyectoVivienda(
            @NotNull DocumentoSoporteProyectoViviendaDTO documentoSoporteProyectoViviendaDTO);

    /**
     * Servicio que consulta las solicitudes de postulación asociadas
     * al jefe del hogar, por en número de radicado de la solicitud y/
     * o los datos del jefe del hogar, las cuales son posibles candidatas a
     * iniciar un proceso de legalización y desembolso.
     * @param numeroRadicadoSolicitud
     *        Número de radicado de la solicitud.
     * @param tipoIdentificacion
     *        Tipo de identificación de la persona.
     * @param numeroIdentificacion
     *        Número de identificación de la persona.
     * @return
     *         Lista de solicitudes de postulación encontradas.
     */

    @GET
    @Path("/consultarPostulacionesParaLegalizacionYDesembolso")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SolicitudPostulacionLegalizacionDTO> consultarPostulacionesParaLegalizacionYDesembolso(
            @QueryParam("numeroRadicadoSolicitud") String numeroRadicadoSolicitud,
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Servicio que crea o actualiza un registro en la tabla
     * <code>SolicitudLegalizacionDesembolso</code>
     * 
     * @param solicitudLegalizacionDesembolsoDTO
     *        La información del registro a crear/actualizar
     * @return La información del registro actualizado
     */
    @POST
    @Path("/crearActualizarSolicitudLegalizacionDesembolso")
    @Produces(MediaType.APPLICATION_JSON)
    public SolicitudLegalizacionDesembolsoModeloDTO crearActualizarSolicitudLegalizacionDesembolso(
            @NotNull SolicitudLegalizacionDesembolsoModeloDTO solicitudLegalizacionDesembolsoDTO);

    /**
     * <b>Descripción:</b> Método que permite registrar un intento de legalización y desembolso en la base de datos.
     * @param intentoLegalizacionDesembolsoModeloDTO
     *        el objeto de tipo IntentoLegalizacionDesembolsoDTO que será persistido
     */
    @POST
    @Path("/registrarIntentoLegalizacionDesembolsoFOVIS")
    public Long registrarIntentoLegalizacionDesembolsoFOVIS(
            @NotNull IntentoLegalizacionDesembolsoModeloDTO intentoLegalizacionDesembolsoModeloDTO);

    /** Servicio que valida las solicitudes de desembolso en estado cerrada de una postulacion fovis
     * <code>SolicitudLegalizacionDesembolso</code>
     * 
     * @param solicitudLegalizacionDesembolsoDTO
     *        La información del registro a crear/actualizar
     * @return La información del registro actualizado
     */
    @POST
    @Path("/validarSolicitudesLegalizacionYDesembolsoCerrado")
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean validarSolicitudesLegalizacionYDesembolsoCerrado(
            @NotNull Long idPostulacionFOVIS);


    /**
     * Servicio que registra los requisitos que no cumplieron en un determinado intento de legalización y desembolso.
     * @param intentoLegalizacionDesembolsoRequisitosDTO
     *        Lista con los requisitos por intento de legalización y desembolso a registrar.
     */
    @POST
    @Path("/registrarIntentoLegalizacionDesembolsoRequisito")
    public void registrarIntentoLegalizacionDesembolsoRequisito(
            List<IntentoLegalizacionDesembolsoRequisitoModeloDTO> intentoLegalizacionDesembolsoRequisitosDTO);

    /**
     * Servicio encargado de consultar una solicitud de legalización y desembolso por el id de la solicitud global.
     * @param idSolicitudGlobal
     *        Identificador global de la solicitud de legalización y desembolso.
     * @return Solicitud de legalización y desembolso encontrada.
     */
    @GET
    @Path("/consultarSolicitudLegalizacionDesembolso")
    @Produces(MediaType.APPLICATION_JSON)
    public SolicitudLegalizacionDesembolsoModeloDTO consultarSolicitudLegalizacionDesembolso(
            @QueryParam("idSolicitud") @NotNull Long idSolicitudGlobal);

    /**
     * Servicio que actualiza el estado de la solicitud de legalizacion y desembolso.
     * @param idSolicitudGlobal
     *        id de la solicitud.
     * @param estadoSolicitud
     *        Nuevo estado para la solicitud de postulación.
     */
    @POST
    @Path("/{idSolicitudGlobal}/estadoSolicitud")
    public void actualizarEstadoSolicitudLegalizacionDesembolso(@PathParam("idSolicitudGlobal") @NotNull Long idSolicitudGlobal,
            @QueryParam("estadoSolicitud") @NotNull EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitud);

    /**
     * Servicio que crea o actualiza un registro de Legalización y desembolso
     * 
     * @param legalizacionFOVISDTO
     *        La información del registro a crear/actualizar.
     * @return La información del registro actualizado.
     */
    @POST
    @Path("/crearActualizarLegalizacionDesembolso")
    public LegalizacionDesembolsoModeloDTO crearActualizarLegalizacionDesembolso(
            @NotNull LegalizacionDesembolsoModeloDTO legalizacionDesembolsoDTO);

    /**
     * Servicio que consulta el listado de anticipos desembolsados para una Postulación Fovis
     * @param idPostulacionFovis
     *        Identificador de la postulación Fovis
     * @return El listado de legalizaciones desembolsadas para la postulación
     */
    @GET
    @Path("/consultarAnticiposDesembolsados")
    public List<AnticipoLegalizacionDesembolsoDTO> consultarAnticiposDesembolsados(
            @NotNull @QueryParam("idPostulacion") Long idPostulacionFovis);

    /**
     * Servicio que consulta el id de la postulacion Fovis por medio del Id de la solicitud
     * 
     * @param idSolicitud
     * @return Id de la postulacion Fovis
     */
    @GET
    @Path("/consultarIdPostulacionFovisPorSolicitud")
    public Long consultarIdPostulacionFovisPorSolicitud(@QueryParam("idSolicitud") @NotNull Long idSolicitud);

    /**
     * Servicio que consulta los datos del jefe de hogar por medio del id de la postulacion Fovis
     * 
     * @param idPostulacionFovis
     * @return Postulacion con los datos del jefe de hogar
     */
    @GET
    @Path("/consultarDatosJefeHogarPostulacion")
    public JefeHogarModeloDTO consultarDatosJefeHogarPostulacion(@QueryParam("idPostulacionFovis") @NotNull Long idPostulacionFovis);

    /**
     * Servicio que consulta el id de la persona asociada a un oferente postulacion Fovis
     * @param idPostulacionFovis
     * @return El Id de la persona
     */
    @GET
    @Path("/consultarPersonaOferente")
    public PersonaModeloDTO consultarPersonaOferente(@QueryParam("idPostulacionFovis") @NotNull Long idPostulacionFovis);

    /**
     * Servicio que consulta los datos del proyecto de solucion de vivienda partiendo
     * del id de la Postulacion Fovis
     * @param idPostulacionFovis
     * @return DTO del proyecto de solucion de vivienda
     */
    @GET
    @Path("/consultarProyectoSolucionVivienda")
    public ProyectoSolucionViviendaModeloDTO consultarProyectoSolucionVivienda(
            @QueryParam("idPostulacionFovis") @NotNull Long idPostulacionFovis);

    /**
     * Servicio que consulta los datos de la licencia asociada a un proyecto de solucion de vivienda
     * @param idProyectoVivienda
     * @return Lista de los DTO de las licencias
     */
    @GET
    @Path("/consultarLicencia")
    public List<LicenciaModeloDTO> consultarLicencia(@QueryParam("idProyectoVivienda") @NotNull Long idProyectoVivienda);

    /**
     * 
     * @param visitaDTO
     * @return DTO de la visita creada
     */
    @POST
    @Path("/crearVisita")
    public VisitaModeloDTO crearVisita(@NotNull VisitaModeloDTO visitaDTO);

    /**
     * Servicio que persiste las condiciones asociadas a una visita
     * @param condicionesVisitaDTO
     * @return DTO con las condiciones de visita creadas
     */
    @POST
    @Path("/crearCondicionesVisita")
    public List<CondicionVisitaModeloDTO> crearCondicionesVisita(@NotNull List<CondicionVisitaModeloDTO> condicionesVisitaDTO);

    /**
     * Servicio que consulta las condiciones asociadas a una visita
     * @param idVisita
     * @return Lista de DTO de las condiciones de la visita
     */
    @GET
    @Path("/consultarCondicionesVisita")
    public List<CondicionVisitaModeloDTO> consultarCondicionesVisita(@QueryParam("idVisita") @NotNull Long idVisita);

    /**
     * Servicio que consulta los datos generales de una visita
     * @param idVisita
     * @return DTO de la visita
     */
    @GET
    @Path("/consultarVisita")
    public VisitaDTO consultarVisita(@QueryParam("idVisita") @NotNull Long idVisita);

    /**
     * Servicio que consulta los detalles asociados a una licencia
     * @param idLicencia
     * @return Lista de DTO de los detalles de la licencia
     */
    @GET
    @Path("/consultarDetalleLicencia")
    public List<LicenciaDetalleModeloDTO> consultarDetalleLicencia(@QueryParam("idLicencia") @NotNull Long idLicencia);

    /**
     * Servicio encargado de consultar una solicitud de legalización y desembolso por el id de la solicitud global.
     * @param idSolicitudGlobal
     *        Identificador global de la solicitud de legalización y desembolso.
     * @return Solicitud de legalización y desembolso encontrada.
     */
    @GET
    @Path("/consultarProyectoViviendaPorOferenteNombreProyecto")
    @Produces(MediaType.APPLICATION_JSON)
    public ProyectoSolucionViviendaModeloDTO consultarProyectoViviendaPorOferenteNombreProyecto(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("nombreProyecto") String nombreProyecto);

    /**
     * Servicio que consulta los documentos soporte del oferente por el identificador del oferente
     * @param idOferente
     *        identificador del oferente
     * @return lista de documentos asociados al oferente
     */
    @GET
    @Path("/consultarDocumentosSoporteOferentePorIdOferente")
    public List<DocumentoSoporteModeloDTO> consultarDocumentosSoporteOferentePorIdOferente(
            @QueryParam("idOferente") @NotNull Long idOferente);
    
    /**
     * Servicio que consulta los documentos soporte del proveedor por el identificador del proveedor
     * @param idProveedor
     *        identificador del proveedor
     * @return lista de documentos asociados al proveedor
     */
    @GET
    @Path("/consultarDocumentosSoporteProveedorPorIdProveedor")
    public List<DocumentoSoporteModeloDTO> consultarDocumentosSoporteProveedorPorIdProveedor(
            @QueryParam("idProveedor") @NotNull Long idProveedor);


    /**
     * Servicio que consulta los documentos asociados al proyecto de vivienda
     * @param idProyectoVivienda
     *        identificador del proyecto de vivienda
     * @return lista de documentos asociados al proyecto de vivienda
     */
    @GET
    @Path("/consultarDocumentosSoporteProyectoPorIdProyecto")
    public List<DocumentoSoporteModeloDTO> consultarDocumentosSoporteProyectoPorIdProyecto(
            @QueryParam("idProyectoVivienda") @NotNull Long idProyectoVivienda);

    /**
     * 
     * Servicio que consulta el oferente identificador
     * @param consultarOferentePorId
     *        identificador del oferente
     * @return DTO con los datos del oferente
     */
    @GET
    @Path("/consultarOferentePorId")
    public OferenteModeloDTO consultarOferentePorId(@QueryParam("idOferente") @NotNull Long idOferente);

    /**
     * Consulta los datos del hogar antes de iniciar el proceso de legalización
     * @param numeroRadicado
     *        Numero de radicado de la solicitud de postulación
     * @return Objeto de solicitud de postulación con la información de la postulación al terminar la asignación
     * @throws IOException
     *         Excepción lanzada en caso de encontrar error en la generación
     */
    @GET
    @Path("/consultarDatosHogarPreLegalizacionDesembolso")
    public SolicitudPostulacionFOVISDTO consultarDatosHogarPreLegalizacionDesembolso(
            @QueryParam("numeroRadicado") @NotNull String numeroRadicado) throws IOException;

    /**
     * Consulta el nombre de los tipos de ahorro previo asociados a una postulacion FOVIS
     * @param idPostulacionFovis
     * @return Lista de nombres de ahorros previos
     */
    @GET
    @Path("/consultarTiposAhorroPostulacion")
    public List<TipoAhorroPrevioEnum> consultarTiposAhorroPostulacion(@QueryParam("idPostulacionFovis") @NotNull Long idPostulacionFovis);

    /**
     * Consulta el nombre de los recursos complementarios asociados a una postualacion
     * @param idPostulacionFovis
     * @return
     */
    @GET
    @Path("/consultarTiposRecursosPostulacion")
    public List<TipoRecursoComplementarioEnum> consultarTiposRecursosPostulacion(
            @QueryParam("idPostulacionFovis") @NotNull Long idPostulacionFovis);

    /**
     * Consulta un recurso conplementario especifico asociado a una postulacion
     * @param idPostulacion
     * @param tipoRecursoComplementario
     * @return DTO del recurso complementario
     */
    @GET
    @Path("/consultarRecursoComplementario")
    public RecursoComplementarioModeloDTO consultarRecursoComplementario(@QueryParam("idPostulacion") @NotNull Long idPostulacion,
            @QueryParam("tipoRecursoComplementario") @NotNull TipoRecursoComplementarioEnum tipoRecursoComplementario);

    /**
     * Consulta los ahorros previos asociados a una postulacion Fovis
     * @param idPostulacion
     * @return Lista de DTO de ahorros previos
     */
    @GET
    @Path("/consultarListaAhorrosPreviosPostulacion")
    public List<AhorroPrevioModeloDTO> consultarListaAhorrosPreviosPostulacion(@QueryParam("idPostulacion") @NotNull Long idPostulacion);

    /**
     * Consulta los recursos complementarios asociados a una postulacion
     * @param idPostulacion
     * @return Lista de DTO de recursos complementarios
     */
    @GET
    @Path("/consultarListaRecursosComplementariosPostulacion")
    public List<RecursoComplementarioModeloDTO> consultarListaRecursosComplementariosPostulacion(
            @QueryParam("idPostulacion") @NotNull Long idPostulacion);

    /**
     * Consulta si existe una Licencia por Matrícula
     * @param matriculaInmobiliaria
     * @return true - existe Licencia Matrícula.
     */
    @GET
    @Path("/existeLicenciaMatricula")
    public Boolean existeLicenciaMatricula(@QueryParam("matriculaInmobiliaria") String matriculaInmobiliaria);
    
	/**
	 * Servicio que consulta los datos más recientes asociados con los
	 * resultados de la inspección de existencia y habitabilidad
	 * 
	 * @param numeroIdentificacion
	 *            Numero de identificación de la persona
	 * @param tipoIdentificacion
	 *            Tipo de identificación de la persona
	 * @return DTO de la visita
	 */
	@GET
	@Path("/consultarResultadosExistenciaHabitabilidad")
	public VisitaDTO consultarResultadosExistenciaHabitabilidad(@QueryParam("numeroRadicacion") String numeroRadicacion);
	
	/**
	 * Servicio que consulta el historico de desembolsos para el tipo y numero
	 * de identificacion del jefe de hogar asociado a la postulacion
	 * 
	 * @param idPostulacionFovis
	 *            Identificador de la postulación Fovis
	 * @return El listado de legalizaciones desembolsadas para la postulación
	 */
	@GET
	@Path("/consultarHistoricoDesembolsoPorNumeroRadicado")
	public CondicionesLegalizacionDesembolsoDTO consultarHistoricoDesembolsoPorNumeroRadicado(@QueryParam("numeroRadicacion") String numeroRadicacion);
	

	/**
	 * Servicio que consulta el listado de hogares que presentaron solicitud de
	 * legalización y desembolso del subsidio FOVIS y esta ya ha sido aprobada o
	 * desembolsada (solicitudes que han tenido el estado
	 * "Legalización y desembolso no autorizado",
	 * "Legalización y desembolso autorizado" o alguno que indique que el
	 * desembolso fue exitoso
	 * 
	 * @param consultarSubsidiosFOVISLegalizacionDTO
	 *            DTO con los filtros de consulta
	 * 
	 * @return ConsultarSubsidiosFOVISLegalizacionDTO DTO con los listado de
	 *         hogares que presentaron solicitud de legalización y desembolso
	 *         del subsidio FOVIS y este procesada.
	 */
	@POST
	@Path("/consultarSubsidiosFOVISLegalizadosDesembolsados")
	public List<ConsultarSubsidiosFOVISLegalizacionDTO> consultarSubsidiosFOVISLegalizadosDesembolsados(
			@NotNull ConsultarSubsidiosFOVISLegalizacionDTO consultarSubsidiosFOVISLegalizacionDTO);

    /**
     * Consula las solicitudes de legalizacion de desembolso asociadas a la postulacion filtro
     * @param numeroRadicacion
     *        Numero identificacion jefe hogar
     * @return Lista historico de legalizaciones de desembolso tramitadas
     */
    @GET
    @Path("/consultarHistoricoLegalizacion")
    public List<HistoricoLegalizacionFOVISDTO> consultarHistoricoLegalizacion(@QueryParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Servicio para calcular el valor a restituir en el registro de restitucion de subsidio
     * 
     * @param numeroRadicadoPostulacion
     *        Numero de radicado de la postulación FOVIS para obtener los datos
     * @return Valores para la restitucion y reembolso a realizar
     */
    @GET
    @Path("/calcularValorRestituir")
    public CondicionesLegalizacionDesembolsoDTO calcularValorRestituir(@NotNull @QueryParam("numeroRadicado") String numeroRadicadoPostulacion);
}
