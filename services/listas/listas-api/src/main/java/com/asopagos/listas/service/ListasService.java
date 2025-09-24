package com.asopagos.listas.service;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.dto.ElementoListaDTO;
import com.asopagos.dto.modelo.CuentasBancariasRecaudoDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados 
 * con la consulta de Listas genéricas
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Path("listasValores")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface ListasService {
	
	/**
     * Método que implementa la capacidad consultarListasValores de la 
     * especificación de servicios
     * 
     * @param idsListaValores
     * @return 
     */	
	@GET
	public List<ElementoListaDTO> consultarListasValores(@QueryParam("idListaValores") List<Integer> idsListaValores);
	
	/**
     * Método que implementa la capacidad consultarListaValores de la 
     * especificación de servicios
     * 
     * @param idListaValores
     * @param nombreFiltro
     * @param valorFiltro
     * @return 
     */       
	@GET
	@Path("{idListaValores}")
	public List<ElementoListaDTO> consultarListaValores(@PathParam("idListaValores") Integer idListaValores, 
            @QueryParam("nombreFiltro") String nombreFiltro, @QueryParam("valorFiltro") String valorFiltro);

	@GET
	@Path("pruebaCacheDos")
	public Object pruebaCacheDos(@QueryParam("key")String key);

        @GET
            @Path("parametro")
        public String consultarParametro(@QueryParam("key")String key);

        @GET
            @Path("constante")
        public String consultarConstante(@QueryParam("key")String key);

        @GET
        @Path("consultarCuentasBancariasRecaudo")    
        public List<CuentasBancariasRecaudoDTO> consultarCuentasBancariasRecaudo();
    
}
