package com.asopagos.fovis.service;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.dto.CargueArchivoCruceFovisDTO;
import com.asopagos.dto.CruceDTO;
import com.asopagos.dto.CruceDetalleDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.InformacionCruceFovisDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.dto.SolicitudGestionCruceDTO;
import com.asopagos.dto.modelo.SolicitudPostulacionModeloDTO;
import com.asopagos.enumeraciones.fovis.EstadoCruceHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudGestionCruceEnum;
import com.asopagos.enumeraciones.fovis.TipoCruceEnum;
import com.asopagos.enumeraciones.fovis.TipoInformacionCruceEnum;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de del Cargue de archivo cruce del proceso FOVIS <b>Historia de Usuario:</b> Proceso 3.2.1
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
@Path("fovisCargue")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface FovisCargueService {

    /**
     * Verifica la estructura del archivo que contiene la informacion de cruce
     * @param archivo
     *        Informacion archivo
     * @return Informacion archivo verificado
     */
    @POST
    @Path("/verificarEstructuraArchivoCruce")
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoCruce(@NotNull InformacionArchivoDTO archivo);

    /**
     * Crea o modifica un registro de cargue de archivo cruce FOVIS
     * @param cargueArchivoCruceFovisDTO
     *        Informacion archivo
     * @return Identificador del archivo creado o modificado
     */
    @POST
    @Path("/crearCargueArchivoCruce")
    public Long crearCargueArchivoCruce(CargueArchivoCruceFovisDTO cargueArchivoCruceFovisDTO);

    /**
     * Consulta la informacion registrada de un archivo de cruce FOVIS
     * @param idCargue
     *        Identificador del cargue
     * @return Cargue archivo cruce
     */
    @GET
    @Path("/consultarCargueArchivoCruce")
    public CargueArchivoCruceFovisDTO consultarCargueArchivoCruce(@QueryParam("idCargue") Long idCargue);

    /**
     * Consulta la informacion de las cedulas cargadas en el archivo de cruce
     * @param idCargue
     *        Identificador del cargue
     * @return informacion de las cc del archivo cruce
     */
    @GET
    @Path("/consultarInformacionArchivoCruces")
    public List<Object[]> consultarInformacionArchivoCruces(@QueryParam("idCargue") Long idCargue);


    /**
     * Consulta la informacion registrada en cada hoja del cruce
     * @param idCargue
     *        Identificador del cargue
     * @return Contenido archivo cruce cargado
     */
    @GET
    @Path("/consultarContenidoArchivoCargueFovis")
    public InformacionCruceFovisDTO consultarContenidoArchivoCargueFovis(@QueryParam("idCargue") Long idCargue);

    /**
     * Registra la lista de cruce FOVIS
     * 
     * @param listCruceDetalle
     *        Lista de cruces
     */
    @POST
    @Path("/crearRegistroCruce")
    public void crearRegistroCruce(List<CruceDetalleDTO> listCruceDetalle);

    /**
     * Consulta la informacion registrada de un cruce FOVIS con los datos enviados
     * @param tipo
     *        Tipo de informacion cruce a buscar
     * @param identificacion
     *        Identificacion persona cruce
     * @return Lista de cruce
     */
    @GET
    @Path("/consultarCruceFiltro")
    public List<CruceDetalleDTO> consultarCruceFiltro(@QueryParam("tipo") TipoInformacionCruceEnum tipo,
            @QueryParam("identificacion") String identificacion);

    /**
     * Consulta los cruces hechos por la lectura de un archivo cruce FOVIS
     * @param idSolicitud
     *        Identificador de la solicitud
     * @return Lista de cruces
     */
    @GET
    @Path("/consultarCruces")
    public List<CruceDetalleDTO> consultarCruces(@QueryParam("idSolicitud") Long idSolicitud, @QueryParam("tipoCruce") TipoCruceEnum tipoCruce);

    /**
     * Consulta el numero de postulacion asociado a la cedula de la persona
     * @param identificacion
     *        Nro de cedula de la persona objeto de cruce
     * @return Numero de postulacion
     */
    @GET
    @Path("/consultarNumeroPostulacionPersona")
    public String consultarNumeroPostulacionPersona(@QueryParam("identificacion") String identificacion);

    /**
     * Consulta los cruces asociados a un numero de postulacion y un id archivo FOVIS
     * en estado NUEVO
     * @param idCargue
     *        Identificador del cargue
     * @param numeroPostulacion
     *        Numero de la postulacion de los cruces
     * @return Lista Cruces
     */
    @GET
    @Path("/consultarCrucesNuevosByNroPostulacionIdCargue")
    public List<CruceDetalleDTO> consultarCrucesNuevosPorNumeroPostulacionIdCargueArchivo(@QueryParam("idCargue") Long idCargue,
            @QueryParam("numeroPostulacion") String numeroPostulacion);

    /**
     * Consulta la solicitud de postulacion fovis por medio del numero de postulacion(numero de radicado)
     * @param numeroPostulacion
     *        Numero de la postulacion de los cruces
     * @return Informacion solicitud
     */
    @GET
    @Path("/consultarSolicitudPostulacionPorNumeroPostulacion")
    public SolicitudPostulacionModeloDTO consultarSolicitudPostulacionPorNumeroPostulacion(
            @NotNull @QueryParam("numeroPostulacion") String numeroPostulacion);

    /**
     * Consulta las solicitud de postulacion fovis por medio del numero de cedula
     * @param listNumeroCedula
     *        Numero de la postulacion de los cruces
     * @return Informacion solicitud por cedula
     */
    @POST
    @Path("/consultarSolicitudPostulacionPorNumeroCedula")
    public Map<String, SolicitudPostulacionModeloDTO> consultarSolicitudPostulacionPorNumeroCedula(List<String> listNumeroCedula);

    /**
     * Registra la lista de gestion cruce enviada
     * @param listSolicitudGestionCruce
     *        Lista de solicitudes de gestion cruce
     * @return Lista de solicitudes insertadas
     */
    @POST
    @Path("/crearRegistroListaSolicituGestionCruce")
    public List<SolicitudGestionCruceDTO> crearRegistroListaSolicituGestionCruce(List<SolicitudGestionCruceDTO> listSolicitudGestionCruce);

    /**
     * Registra la información de gestion de cruce con los cruces
     * @param solicitudGestionCruceDTO Informacion cruce
     * @return Solicitud registrada
     */
    @POST
    @Path("/crearRegistroSolicituGestionCruce")
    public SolicitudGestionCruceDTO crearRegistroSolicituGestionCruce(SolicitudGestionCruceDTO solicitudGestionCruceDTO);

    /**
     * Actuliza la solicud gestion cruce enviada
     * @param solicitudGestionCruce
     *        Info solicitude de gestion cruce a actualizar
     * @return Solicitud actualizada
     */
    @POST
    @Path("/actualizarSolicitudGestionCruce")
    public SolicitudGestionCruceDTO actualizarSolicitudGestionCruce(SolicitudGestionCruceDTO solicitudGestionCruce);

    /**
     * Consulta la informacion de una solicitud de gestion cruce
     * @param idSolicitud
     *        Identificador de solicitud
     * @return Solicitud gestion cruce
     */
    @GET
    @Path("/consultarSolicitudGestionCruce")
    public SolicitudGestionCruceDTO consultarSolicitudGestionCruce(@QueryParam("idSolicitud") Long idSolicitud);

    /**
     * Consulta la información de una solicitud de gestion cruce por el identificador de la Solicitud de Postulación y el tipo de cruce.
     * @param idSolicitudPostulacion
     *        Identificador de solicitud de postulación.
     * @param tipoCruce
     *        Tipo de cruce.
     * @return Solicitud gestion cruce.
     */
    @GET
    @Path("/consultarSolicitudGestionCrucePorPostulacionTipoCruce")
    public List<SolicitudGestionCruceDTO> consultarSolicitudGestionCrucePorPostulacionTipoCruce(
            @NotNull @QueryParam("idSolicitudPostulacion") Long idSolicitudPostulacion,
            @NotNull @QueryParam("tipoCruce") TipoCruceEnum tipoCruce);
    
    /**
     * Consulta la información de una solicitud de gestion cruce por el identificador de la Solicitud de Postulación
     * @param idSolicitudPostulacion
     *        Identificador de solicitud de postulación.
     * @return Solicitud gestion cruce.
     */
    @GET
    @Path("/consultarSolicitudGestionCrucePorSolicitudPostulacion")
    public List<SolicitudGestionCruceDTO> consultarSolicitudGestionCrucePorSolicitudPostulacion(
            @NotNull @QueryParam("idSolicitudPostulacion") Long idSolicitudPostulacion);
    
    /**
     * Consulta los cruces por tipo y estados.
     * @param tipoCruce
     * @param estadoSolicitudGestion
     * @param estadoCruceHogar
     * @return lista de solicitud gestión cruce
     */
    @GET
    @Path("/consultarSolicitudGestionTipoCruceEstado")
    public List<SolicitudGestionCruceDTO> consultarSolicitudGestionTipoCruceEstado(@NotNull @QueryParam("tipoCruce")TipoCruceEnum tipoCruce, 
    		@NotNull @QueryParam("estadoSolicitudGestion") EstadoSolicitudGestionCruceEnum estadoSolicitudGestion, 
    		@NotNull @QueryParam("estadoCruceHogar")EstadoCruceHogarEnum estadoCruceHogar);
    /**
     * Consulta la informacion de los cruces por el identificador de la Solicitud de Postulación.
     * @param idSolicitudPostulacion
     *        Identificador de solicitud de Postulacion
     * @return los Cruces encontrados.
     */
    @GET
    @Path("/consultarCrucePorSolicitudPostulacion")
    public List<CruceDTO> consultarCrucePorSolicitudPostulacion(
            @QueryParam("idSolicitudPostulacion") @NotNull Long idSolicitudPostulacion);

    /**
     * servicio que permite registrar los cruces.
     * @param crucesDTO
     *        Informacion de cruce
     * @return Identificador de cruce registrado.
     */
    @POST
    @Path("/registrarActualizarCruces")
    public void registrarActualizarCruces(@NotNull List<CruceDTO> crucesDTO);
    
    /**
     * Consulta los cruces generados durante la ejecucion asincrona de las validaciones de postulaciones 
     * @param idProcesoAsincrono Identificador procesoAsincrono
     * @return Lista de cruces detalle con la informacion de las validaciones fallidas
     */
    @GET
    @Path("/consultarCrucesPorProcesoAsincrono")
    public List<CruceDetalleDTO> consultarCrucesPorProcesoAsincrono(@QueryParam("idProcesoAsincrono") Long idProcesoAsincrono);

    /**
     * Consulta la información de las solicitudes de gestion cruce por la lista de identificadores de la Solicitud de Postulación y el tipo
     * de cruce.
     * @param listIdSolicitudPostulacion
     *        Lista de Identificador de solicitud de postulación.
     * @param tipoCruce
     *        Tipo de cruce.
     * @return Lista Solicitud gestion cruce.
     */
    @GET
    @Path("/consultarSolicitudGestionCrucePorListPostulacionTipoCruce")
    public List<SolicitudGestionCruceDTO> consultarSolicitudGestionCrucePorListPostulacionTipoCruce(
            @NotNull @QueryParam("listIdSolicitudPostulacion") List<Long> listIdSolicitudPostulacion,
            @NotNull @QueryParam("tipoCruce") TipoCruceEnum tipoCruce);

    /**
     * Consulta las solicitudes de postulacion fovis por medio de la lista de numero de postulacion(numero de radicado) 
     * @param listNumeroPostulacion
     *        Lista Numero de la postulacion de los cruces
     * @return Informacion solicitud
     */
    @POST
    @Path("/consultarSolicitudPostulacionPorListaNumeroPostulacion")
    public List<SolicitudPostulacionModeloDTO> consultarSolicitudPostulacionPorListaNumeroPostulacion(List<String> listNumeroPostulacion);
    
	/**
	 * Consulta la informacion registrada de un cruce FOVIS para todos los tipos
	 * de informacion por el numero de identificacion
	 * 
	 * @param identificacion
	 *            Identificacion persona cruce
	 * @return Mapa con la lista de cruces por tipos de informacion
	 */
	@GET
	@Path("/consultarCruceTodosTiposInformacion")
	public List<CruceDetalleDTO> consultarCruceTodosTiposInformacion(
			@QueryParam("identificacion") String identificacion);

    /**
     * Consulta la informacion de una solicitud de gestion cruce por el id de solicitud global
     * @param idSolicitudGlobal
     *        Identificador de solicitud global
     * @return Solicitud gestion cruce
     */
    @GET
    @Path("/consultarSolicitudGestionCrucePorSolicitudGlobal")
    public SolicitudGestionCruceDTO consultarSolicitudGestionCrucePorSolicitudGlobal(
            @QueryParam("idSolicitudGlobal") Long idSolicitudGlobal);

    /**
     * Servicio que actualiza el estado de la solicitud de asignación.
     * @param idSolicitudGlobal
     *        id de la solicitud.
     * @param estadoSolicitud
     *        Nuevo estado para la solicitud de postulación.
     */
    @POST
    @Path("/{idSolicitudGlobal}/estadoSolicitudGestionCruce")
    public void actualizarEstadoSolicitudGestionCruce(@PathParam("idSolicitudGlobal") @NotNull Long idSolicitudGlobal,
            @QueryParam("estadoSolicitud") @NotNull EstadoSolicitudGestionCruceEnum estadoSolicitud);
    
    /**
     * Servicio que actualiza el estado de cruces del hogar a Subsanados.
     * @param idSolicitudPostulacion
     * 
     */
    @POST
    @Path("/{idSolicitudPostulacion}/actualizarSolicitudesGestionCruceASubsanadas")
    public void actualizarSolicitudesGestionCruceASubsanadas(@NotNull @PathParam("idSolicitudPostulacion") Long idSolicitudPostulacion);

}
