package com.asopagos.correspondencia.service;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.dto.afiliaciones.RecepcionSolicitudDTO;
import com.asopagos.dto.afiliaciones.RemisionBackDTO;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados con la gestión de empleadores <b>Módulo:</b> Asopagos -
 * transversal<br/>
 * <b>Módulo:</b> Asopagos - HU 086 <br/>
 *
 * @author Harold Andrés Alzate Betancur <a href="halzate:halzate@heinsohn.com.co"> halzate</a>
 *
 */

@Path("solicitudes")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface SolicitudesService {
	
	/**
     * <b>Descripción</b>Método encargado de resolver el listado de 
     * las solicitudes 	remisión back <br/>
     * @param fechaInicial,
     *              fecha inicial
     * @param fechaFinal ,
     *              fecha final
     * @param proceso,
     *              enum con el tipo de proceso
     * @param userDTO,
     *              usuario de la sesión
     * @return  RemisionBackDTO objeto con la lista de detalles y resumen
     */
    @GET
    @Path("/remisionBack")
	public RemisionBackDTO generarListadoSolicitudesRemisionBack(
			@QueryParam("fechaInicial") @NotNull Long fechaInicial, 
			@QueryParam("fechaFinal") @NotNull Long fechaFinal, 
			@QueryParam("proceso") @NotNull ProcesoEnum proceso, 
			@QueryParam("nombreUsuario") @NotNull String nombreUsuario);
    
    /**
     * <b>Descripción</b>Método encargado de asociar las solicitudes a la caja 
     * de correspondencia abierta
     * @param codigoSede
     * @param listaRadicados
     * @param userDTO
     * 
     * @return una lista con los ids de instancia de cada proceso implicado
     */
    @POST
    @Path("asociarCajaCorrespondencias/{sedeCajaCompensacion}")
    public List<String> asociarSolicitudesACajaCorrespondencias(
            @PathParam("sedeCajaCompensacion") String codigoSede,
            @NotNull @Size(min = 1) List<String> listaRadicados,
            @Context UserDTO userDTO);
    
    /**
     * <b>Descripción</b>Método encargado de actualizar 
     * @param numeroRadicado
     * 				Número radicado para consultar la Solicitud
     * 				que será actualizada
     * @param estadoDocumento
     * 				estado documento para decidir como actulizar
     * 				el estado de la solicitud
     * @param numeroCustodia
     * 				número custodia que será actualizado
     */
    @POST
    @Path("/{numeroRadicado}/registrarRecepcion")
    public void registrarRecepcionSolicitud(
    		@NotNull @PathParam("numeroRadicado") String numeroRadicado,
    		@NotNull @Valid RecepcionSolicitudDTO datosRecepcionSolicitud);
    
    
    /**
     * UTILITARIO TEMPORAL para el cierre de tareas de "Asignar solicitud afiliación empleador" 
     * en el proceso de afiliacion de empleadores, las cuales se encuentran asignadas al back
     * con documentación FISICA gestionada  
     * @return
     */
    @POST
    @Path("/continuarTareaAfiEmpleadorDocFisica")
    public String continuarTareaAfiEmpleador(@Context UserDTO user);
}
