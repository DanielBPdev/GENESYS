package com.asopagos.fovis.composite.service;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.dto.fovis.EjecucionAsignacionDTO;
import com.asopagos.dto.fovis.InformacionDocumentoActaAsignacionDTO;
import com.asopagos.dto.modelo.ActaAsignacionFOVISModeloDTO;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.dto.modelo.SolicitudAsignacionFOVISModeloDTO;
import com.asopagos.dto.modelo.SolicitudPostulacionModeloDTO;
import com.asopagos.fovis.composite.dto.AsignaResultadoCruceDTO;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción: </b> Interfaz que define los métodos de negocio relacionados
 * con la gestión del proceso de asignación FOVIS 3.2.3 <br/>
 * <b>Historia de Usuario: </b> HU-323
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@Path("asignacionFovisComposite")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AsignacionCompositeService {

    /**
     * Servicio encargado de ejecutar las validaciones sobre el hogar, jefe hogar y miembros del hogar asociados a cada solicitud de
     * posutlacion enviada de manera asincrona
     * @param postulaciones
     *        Listado de postulaciones a validar
     */
    @POST
    @Path("/validarHogar")
    public void validarHogar(@QueryParam("idCicloAsignacion") Long idCicloAsignacion, @QueryParam("incluyeIngresos") Boolean incluyeIngresos, @NotNull List<SolicitudPostulacionModeloDTO> postulaciones);

    /**
     * Servicio que cancela el proceso de validación automatica de hogares
     * 
     * @param idCicloAsignacion
     *        Identificador de ciclo asignación
     */
    @POST
    @Path("/cancelarValidacionHogar")
    public void cancelarValidacionHogar(@QueryParam("idCicloAsignacion") Long idCicloAsignacion);

    /**
     * Realiza el registro de la informacion del cruce y asigna las tareas a los usuario seleccionados
     * @param asignarResultadoCruce
     *        Informacion cruces hechos y usuarios a asignar
     */
    @POST
    @Path("/aceptarResultadoCruceInterno")
    public void aceptarResultadoCruceInterno(@NotNull AsignaResultadoCruceDTO asignarResultadoCruce, @Context UserDTO userDTO);

    /**
     * Realiza el registro de rechazo de la informacion de cruce encontrado en la ejecucion de validaciones
     * @param asignarResultadoCruce
     *        Informacion proceso asincrono realizado
     */
    @POST
    @Path("/rechazarResultadoCruceInterno")
    public void rechazarResultadoCruceInterno(@NotNull AsignaResultadoCruceDTO asignarResultadoCruce);

    /**
     * Servicio que calcula y guarda el puntaje de un hogar, cuando éste aplica a un
     * subsidio de vivienda familiar
     * 
     * @param postulacionFOVISDTO
     *        Información de la postulación del hogar
     * @return Objeto <code>PostulacionFOVISModeloDTO</code> que incluye el puntaje calculado
     */
    @POST
    @Path("/calcularCalificacionPostulacion")
    PostulacionFOVISModeloDTO calcularCalificacionPostulacion(@NotNull PostulacionFOVISModeloDTO postulacionFOVISDTO);


    /**
     * Servicio que ejecuta de manera asíncrona, el cálculo y almacenamiento de los puntajes para cada hogar perteneciente a un ciclo de
     * asignación enviado como parámetro
     * @param idCicloAsignacion
     *        Nombre del ciclo de asignación
     */
    @GET
    @Path("/calcularGuardarCalificacionHogaresCicloSincrono")
    void calcularGuardarCalificacionHogaresCicloSincrono(@QueryParam("idCicloAsignacion") Long idCicloAsignacion,
                                                         @QueryParam("valorDisponible") BigDecimal valorDisponible);

    /**
     * Servicio que obtiene los resultados del proceso de asignación (puntaje FOVIS), ordenados por prioridades
     * 
     * @param nombreCicloAsignacion
     *        Nombre del ciclo de asignación
     * @param valorDisponible
     *        Valor disponible para el subsidio, tipo <code>String</code>
     * @return Lista de hogares/postulaciones ordenados por prioridad + resumen por prioridad
     */
    @GET
    @Path("/obtenerResultadosAsignacion")
    EjecucionAsignacionDTO obtenerResultadosAsignacion(@QueryParam("idCicloAsignacion") Long idCicloAsignacion);

    /**
     * Servicio que almacena la informarcion del resultado de asignación para las diferentes postulaciones
     * 
     * @author <a href="mailto:ecastano@heinsohn.com.co">Edward Castano</a>
     * 
     * @param solicitudAsignacion
     *        solicitud de asignacion a procesar
     * @return DTO con la informacion de la solicitud de asignacion procesada
     */
    @POST
    @Path("/almacenarResultadoAsignacion")
    public SolicitudAsignacionFOVISModeloDTO almacenarResultadoAsignacion(@NotNull SolicitudAsignacionFOVISModeloDTO solicitudAsignacion);

    /**
     * Metodo encargado de aprobar el documento del acta de asignacion con la respectivas firmas
     * 
     * @author <a href="mailto:ecastano@heinsohn.com.co">Edward Castano</a>
     * 
     * @param actaAsignacionFOVISDTO
     *        DTO con la informacion del acta de asignacion y el identificador del documento con las firmas
     * @return ActaAsignacionFOVISModeloDTO DTO con la informacion procesada
     */
    @POST
    @Path("/aprobarDocumentoAsignacion")
    public ActaAsignacionFOVISModeloDTO aprobarDocumentoAsignacion(@NotNull ActaAsignacionFOVISModeloDTO actaAsignacionFOVISDTO);

    /**
     * Servicio que realiza el proceso de rechazar la solicitud de asignacion
     * 
     * @author <a href="mailto:ecastano@heinsohn.com.co">Edward Castano</a>
     * 
     * @param solicitudAsignacion
     *        solicitud de asignacion a procesar
     * @return DTO con la informacion de la solicitud de asignacion procesada
     */
    @POST
    @Path("/rechazarResultadoSolicitudAsignacion")
    public SolicitudAsignacionFOVISModeloDTO rechazarResultadoSolicitudAsignacion(
            @NotNull SolicitudAsignacionFOVISModeloDTO solicitudAsignacion);

    /**
     * Servicio que ejecuta de manera asíncrona, el cálculo y almacenamiento de los puntajes para cada hogar perteneciente a un ciclo de
     * asignación enviado como parámetro
     * @param nombreCicloAsignacion
     *        Nombre del ciclo de asignación
     */
    @GET
    @Path("/calcularGuardarCalificacionHogaresCiclo")
    void calcularGuardarCalificacionHogaresCiclo(@QueryParam("idCicloAsignacion") Long idCicloAsignacion,
            @QueryParam("valorDisponible") BigDecimal valorDisponible);

    /**
     * Servicio que radica la asignación de hogares a subsidio FOVIS - HU-047
     * @param ejecucionAsignacionDTO
     *        Información de los resultados de asignación
     * @param userDTO
     *        Información del usuario, tomada del contexto
     */
    @POST
    @Path("/aceptarResultadosEjecucionAsignacion")
    void aceptarResultadosEjecucionAsignacion(@NotNull EjecucionAsignacionDTO ejecucionAsignacionDTO, @Context UserDTO userDTO);

    /**
     * Servicio que radica la asignación de hogares a subsidio FOVIS - HU-047
     * @param ejecucionAsignacionDTO
     *        Información de los resultados de asignación
     * @param userDTO
     *        Información del usuario, tomada del contexto
     */
    @POST
    @Path("/cancelarEjecucionAsignacion")
    public void cancelarEjecucionAsignacion(@QueryParam("idCicloAsignacion") Long idCicloAsignacion);

    /**
     * Servicio que obtiene la información para generar el documento de soporte de asignación - HU-050
     * 
     * @param idSolicitudGlobal
     *        identificador de la solicitud global asociada al proceso
     * 
     * @return DTO con la información para la generacion del documento soporte acta de asignación
     */
    @GET
    @Path("/consultarInformacionDocumentoSoporteActaAsignacion")
    public InformacionDocumentoActaAsignacionDTO consultarInformacionDocumentoSoporteActaAsignacion(
            @QueryParam("idSolicitudGlobal") Long idSolicitudGlobal);

    /**
     * Servicio encargado de almacenar el documento y la informacion del acta de asignacion
     * @author <a href="mailto:ecastano@heinsohn.com.co">Edward Castano</a>
     * 
     * @param idSolicitudGlobal
     *        identificador de la solicitud globlal
     * @param actaAsignacionFOVISModeloDTO
     *        DTO con la informacion a almacenar del acta de asignacion
     * 
     */
    @POST
    @Path("/guardarInformacionYDocumentoActaAsignacion")
    public ActaAsignacionFOVISModeloDTO guardarInformacionYDocumentoActaAsignacion(@QueryParam("idSolicitudGlobal") Long idSolicitudGlobal,
            @NotNull ActaAsignacionFOVISModeloDTO actaAsignacionFOVISModeloDTO);

    /**
     * Finaliza ejecución validaciones sin cruces
     */
    @POST
    @Path("/finalizarValidacionesHogarSinCruces")
    public void finalizarValidacionesHogarSinCruces();

    /**
     * No acepta resultado de los cruces
     * @param asignarResultadoCruce
     */
    @POST
    @Path("/noAceptarResultadoCruceInterno")
    public void noAceptarResultadoCruceInterno(@NotNull AsignaResultadoCruceDTO asignarResultadoCruce);

}
