package com.asopagos.parametros.service;


import java.util.List;

import javax.servlet.http.HttpServletResponse;
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
import javax.ws.rs.core.UriInfo;

import com.asopagos.dto.ElementoListaDTO;
import com.asopagos.entidades.transversal.core.Requisito;
import com.asopagos.listaschequeo.dto.RequisitoCajaClasificacionDTO;
import com.asopagos.parametros.dto.TablaParametroDTO;
import com.asopagos.subsidiomonetario.dto.ParametrizacionCondicionesSubsidioCajaDTO;
import com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionLiquidacionSubsidioModeloDTO;

/**
 * <b>Descripción:</b> Define los métodos de negocio relacionados
 * con la administración de los parametros ASOPAGOS
 *
 * @author Carlos García <cargarcia@heinsohn.com.co>
 */
@Path("parametros")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface ParametroService {

	@POST
	@Path("/admon/{nombreTabla}")
	public void crearParametro(
			String parametro, 
			@PathParam("nombreTabla") String nombreTabla);
	
	@PUT
	@Path("/admon/{nombreTabla}")
	public void modificarParametro(String parametro, 
			@PathParam("nombreTabla") String nombreTabla);
	
	@DELETE
	@Path("/admon/{nombreTabla}/{idEntidad}")
	public void eliminarParametro(
			@PathParam("idEntidad") String idEntidad, 
			@PathParam("nombreTabla") String nombreTabla);
	
	@GET
	@Path("/admon/{nombreClase}/{idEntidad}")
	public Object consultarParametroPorID(
			@PathParam("idEntidad") String idEntidad, 
			@PathParam("nombreClase") String nombreClase);
	
	@GET
	@Path("/listaTablaParametros")
	public List<ElementoListaDTO> listaTablaParametros();
	
	@GET
    @Path("/informacionTablasParametricas")
    public TablaParametroDTO informacionTablasParametricas(@QueryParam("nombreTabla") String nombreTabla);
	
	@GET
	@Path("/datosTablasParametricas")
    public List<Object> datosTablasParametricas(@QueryParam("nombreTabla") String nombreTabla, @Context UriInfo uri, @Context HttpServletResponse response);
    
	@POST
	@Path("/parametrosSubsidio")
	public void replicacionParametrosSubsidio(ParametrizacionCondicionesSubsidioCajaDTO parametrizacionCondicionesSubsidioCaja);
	
	@POST
	@Path("/valoresAnuales")
	public void replicacionValoresAnuales(List<ParametrizacionLiquidacionSubsidioModeloDTO> parametrizacionesLiq);
	
    @POST
    @Path("/replicarInsercionCajaListaEspecialRevision")
    public void replicarInsercionCajaListaEspecialRevision(String mensaje);
    
    @POST
    @Path("/replicarActualizacionListaEspecialRevision")
    public void replicarActualizacionListaEspecialRevision(String mensaje);

    @POST
    @Path("/replicarListaChequeo")
    public void replicarListaChequeo(Requisito requisito);

    @POST
    @Path("/replicarCreacionListaChequeoClasificacion")
    public void replicarCreacionListaChequeoClasificacion(List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion);
    
    @POST
    @Path("/replicarActualizacionListaChequeoClasificacion")
    public void replicarActualizacionListaChequeoClasificacion(List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion);
    
    @POST
    @Path("/replicarCreacionListaChequeoClasificacionPorCaja")
    public void replicarCreacionListaChequeoClasificacionPorCaja(List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion);
    
    @POST
    @Path("/replicarActualizacionListaChequeoClasificacionPorCaja")
    public void replicarActualizacionListaChequeoClasificacionPorCaja(List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion);
}
