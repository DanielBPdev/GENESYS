package com.asopagos.novedades.fovis.composite.service;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.dto.modelo.SolicitudAnalisisNovedadFOVISModeloDTO;
import com.asopagos.novedades.fovis.composite.dto.AnalisisSolicitudNovedadFovisDTO;
import com.asopagos.novedades.fovis.composite.dto.AsignarSolicitudNovedadFovisDTO;
import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import com.asopagos.novedadesfovis.composite.dto.VerificacionNovedadFovisDTO;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de Proceso de Novedades FOVIS
 * 
 * <b>Historia de Usuario: HU 095 Registrar novedades automáticas FOVIS</b>
 * proceso 3.2.5
 * 
 * @author Edward Castaño <ecastano@heinsohn.com.co>
 */
@Path("novedadesFovisComposite")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface NovedadesFovisCompositeService {

    /**
     * Método encargado de radicar una solicitud de novedad automatica FOVIS.
     * 
     * @param solNovedadDTO
     *        datos de la solicitud de novedad FOVIS.
     * @param userDTO
     *        datos del usuario que radica la solicitud.
     * @return
     */
    @POST
    @Path("/radicarSolicitudNovedadAutomaticaFovis")
    public SolicitudNovedadFovisDTO radicarSolicitudNovedadAutomaticaFovis(SolicitudNovedadFovisDTO solNovedadFovisDTO,
            @Context UserDTO userDTO);

    /**
     * Metodo encargado de registrar el resultado del analisis de hecho a la novedad de persona que afecto la postulacion
     * @param idSolicitud
     *        Identificador solicitud analisis
     * @param idTarea
     *        Identificador tarea BPM
     * @param observaciones
     *        Observaciones a registrar
     */
    @POST
    @Path("/registrarResultadoAnalisis")
    public void registrarResultadoAnalisis(@QueryParam("idSolicitud") Long idSolicitud, @QueryParam("idTarea") Long idTarea,
            String observaciones);

    /**
     * <b>Descripción:</b> Registra un intento de novedad Fovis.
     * @param solicitudNovedadFovisDTO
     *        Datos de la solicitud para registrar en el intento.
     * @param userDTO
     *        Usuario del contexto de seguridad.
     * @return El objeto de solicitud novedad con la informacion de la solicitud registrada para el rechazo
     */
    @POST
    @Path("/registrarIntentoSolicitudNovedadFovis")
    public SolicitudNovedadFovisDTO registrarIntentoSolicitudNovedadFovis(@NotNull SolicitudNovedadFovisDTO solicitudNovedadFovisDTO,
            @Context UserDTO userDTO);

    /**
     * Consulta los datos temporales de la solicitud novedad fovis con el identificador
     * @param idSolicitudGlobal
     *        Identificador global solicitud novedad fovis
     * @return Objeto <code>SolicitudNovedadFovisDTO</code> con la información de la solicitud
     */
    @GET
    @Path("/consultarNovedadFovisTemporal/{idSolicitudGlobal}")
    public SolicitudNovedadFovisDTO consultarNovedadFovisTemporal(@PathParam("idSolicitudGlobal") Long idSolicitudGlobal);

    /**
     * Realiza la radicación de la solicitud novedad de fovis para las novedades regulares
     * @param infoSolicitud
     *        Informacion de la solicitud de novedad
     * @param userDTO
     *        Usuario logueado en el sistema
     * @return Informacion solicitud novedad
     */
    @POST
    @Path("/radicarSolicitudNovedad")
    public SolicitudNovedadFovisDTO radicarSolicitudNovedad(SolicitudNovedadFovisDTO infoSolicitud, @Context UserDTO userDTO);

    /**
     * Realiza la verificación de la solicitud novedad por primera vez
     * @param verificacionNovedadFovis
     *        Información para verificación
     * @param userDTO
     *        Usuario autenticado en el sistema
     */
    @POST
    @Path("/verificarSolicitudNovedadFovis")
    public void verificarSolicitudNovedadFovis(VerificacionNovedadFovisDTO verificacionNovedadFovis, @Context UserDTO userDTO);

    /**
     * Realiza la verificación de la solicitud de novedad luego de gestionar el PNC
     * @param verificacionNovedadFovis
     *        Información para verificación
     * @param userDTO
     *        Usuario autenticado en el sistema
     */
    @POST
    @Path("/verificarPNCSolicitudNovedadFovis")
    public void verificarPNCSolicitudNovedadFovis(VerificacionNovedadFovisDTO verificacionNovedadFovis, @Context UserDTO userDTO);

    /**
     * Método encargado de registrar un análisis a una solicitud de novedad Fovis.
     * 
     * @param analisisSolicitud
     *        Son los datos del análisis de la solicitud
     * @param userDTO
     *        Es el usuario del contexto de seguridad
     */
    @POST
    @Path("/analizarSolicitudNovedadFovis")
    public void analizarSolicitudNovedadFovis(@NotNull AnalisisSolicitudNovedadFovisDTO analisisSolicitud, @Context UserDTO userDTO);

    /**
     * Servicio que registra el resultado definitivo por parte del front, del análisis realizado
     * por los analistas a los que se les escaló la solicitud.
     * @param resultadoEscalamiento
     *        objeto con los datos del resultado
     * @param userDTO
     *        usuario del contexto de seguridad
     */
    @POST
    @Path("/finalizarAnalisisNovedadFovisFront")
    public void finalizarAnalisisNovedadFovisFront(@NotNull AnalisisSolicitudNovedadFovisDTO resultadoAnalisis, @Context UserDTO userDTO);

    /**
     * Servicio que registra el resultado definitivo por parte del back, del análisis realizado
     * por los analistas a los que se les escaló la solicitud.
     * @param resultadoEscalamiento
     *        objeto con los datos del resultado
     * @param userDTO
     *        usuario del contexto de seguridad
     */
    @POST
    @Path("/finalizarAnalisisNovedadFovisBack")
    public void finalizarAnalisisNovedadFovisBack(@NotNull AnalisisSolicitudNovedadFovisDTO resultadoEscalamiento,
            @Context UserDTO userDTO);

    /**
     * Servicio encargado de consultar la informacion de una solicitud de analisis de novedad persona afecta fovis con el detalle de la
     * informacion a presentar en pantalla
     * @param idSolicitud
     *        Identificador solicitud analisis
     * @return DTO con la informacion de la solicitud de analisis
     */
    @GET
    @Path("/consultarSolicitudAnalisisNovedadAfectaFOVIS")
    SolicitudAnalisisNovedadFOVISModeloDTO consultarSolicitudAnalisisNovedadAfectaFOVIS(@QueryParam("idSolicitud") Long idSolicitud);

    /**
     * Servicio registra la gestión de Producto No Conforme para la solicitud de Novedad Fovis.
     * @param idSolicitud
     *        Identificador global de la solicitud.
     * @param userDTO
     *        Es el usuario del contexto de seguridad.
     */
    @POST
    @Path("/gestionarPNCNovedadFovis/{idSolicitudGlobal}")
    public void gestionarPNCNovedadFovis(@NotNull @PathParam("idSolicitudGlobal") Long idSolicitud, @Context UserDTO userDTO);

    /**
     * Servicio que Asigna la Solicitud de Novedad Fovis al back.
     * @param entrada
     *        datos de la asignación
     * @param userDTO
     *        usuario del contexto
     */
    @POST
    @Path("/asignarSolicitudNovedadFovis")
    public void asignarSolicitudNovedadFovis(@NotNull AsignarSolicitudNovedadFovisDTO entrada, @Context UserDTO userDTO);

    /**
     * Servicio para ejecutar el proceso de vencimiento de subsidios fovis asignados inmediato
     * @param userDTO
     *        Usuario del contexto
     */
    @POST
    @Path("/ejecutarNovedadVencimiento")
    public void ejecutarNovedadVencimiento(@Context UserDTO userDTO);

}
