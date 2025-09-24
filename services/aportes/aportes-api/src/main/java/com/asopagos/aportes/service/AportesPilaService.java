package com.asopagos.aportes.service;

import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.asopagos.entidades.ccf.aportes.PilaEstadoTransitorio;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con las acciones de registro o relación de aportes de una planilla pila
 * 
 * <b>Módulo:</b> Asopagos - HU-211-397 <br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 */
@Path("aportesPila")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AportesPilaService {

	/**
	 * Servicio encargado lanzar el copiado de los aportes temporales a core
	 * 
	 * @param idPlanilla identificador de la planilla a la cual se le deben copiar
	 *                   los aportes en core desde los aportes temporales de pila
	 */
	@POST
	@Path("/copiarDatosAportesPlanilla")
	public void copiarDatosAportesPlanilla(@NotNull @QueryParam("indicePlanilla") Long idPlanilla);
	
	/**
	 * Servicio encargado guardar el estado de la acción transitoria 
	 * 
	 * @param pilaEstadoTransitorio
	 */
	@POST
	@Path("/crearPilaEstadoTransitorio")
	public void crearPilaEstadoTransitorio(@NotNull PilaEstadoTransitorio pilaEstadoTransitorio);

	
	/**
	 * Consulta que valida si la novedad se proceso correctamente antes de 
	 * notificar
	 * 
	 * @param idPlanilla
	 *           idPlanilla
	 * @return true si la validacion es superada, false si la validación no es
	 *         superada.
	 */
	@GET
	@Path("/validarProcesadoNovedad")
	public Boolean validarProcesadoNovedad(
		    @NotNull @QueryParam("idPlanilla") Long idPlanilla);	
	
	
	/**
     * Retorna el número de aportes temporales existentes para una planilla
     * @param idPlanilla
     * @return
     */
	@GET
	@Path("/crearPilaEstadoTransitorio")
    public Long contarTemAportesPendientes(@NotNull @QueryParam("indicePlanilla") Long idPlanilla);
}
