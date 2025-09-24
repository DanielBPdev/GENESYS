package com.asopagos.tareashumanas.service;

import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción:</b> Interfaz de servicios Web REST para la invocación de 
 * servicios relacionados con los procesos en el BPM
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Path("procesos")
@Consumes({"application/json; charset=UTF-8",MediaType.TEXT_PLAIN})
@Produces("application/json; charset=UTF-8")
public interface ProcesosService {
    
    /**
     * Método que envía una señal a una instancia de proceso con parametros 
     * de tipo de dato simples
     * 
     * @param proceso
     * @param tipoSenal
     * @param idInstanciaProceso
     */
    
    @POST
    @Path("enviarSenal/{proceso}/{tipoSenal}")
    public void enviarSenal(@PathParam("proceso")ProcesoEnum proceso, @PathParam("tipoSenal") String tipoSenal,
            @QueryParam("idInstanciaProceso") Long idInstanciaProceso,@QueryParam("event")String event,@Context UserDTO user);
    /**
	 * Método que inicia un proces con parámetros
	 * 
	 * @param proceso
	 *            Es el proceso a iniciar
	 * @param params
	 *            Son los parámetros de inicio
	 * @return Identificador de la instancia de proceso creada
	 */
    
	@POST
	@Path("{proceso}/iniciarProceso")
	public Long iniciarProceso(@PathParam("proceso") ProcesoEnum proceso, Map<String, Object> params,@Context UserDTO user);
	
	/**
	 * Metodo encargado de abortar procesos de negocio
	 * @param proceso, Proceso a abortar
	 * @param idInstanciaProceso, is instancia del proceso
	 * @param user, usuario
	 */
	@POST
	@Path("{proceso}/abortar")
    public String abortarProceso(@PathParam("proceso") ProcesoEnum proceso, @QueryParam("idInstanciaProceso") Long idInstanciaProceso,
            @Context UserDTO user);
	
	
	/**
     * Metodo encargado de abortar varios procesos de negocio
     * @param proceso, Proceso a abortar
     * @param idInstanciaProceso, recibe varias instancias de proceso
     * @param user, usuario
     */
    @POST
    @Path("{proceso}/abortarMultiplesProcesos")
    public void abortarProcesos(@PathParam("proceso") ProcesoEnum proceso, List<Long> idInstanciaProceso,
            @Context UserDTO user);
}
