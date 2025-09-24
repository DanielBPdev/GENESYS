package com.asopagos.reportes.service;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.dto.DashBoardConsultaDTO;
import com.asopagos.reportes.microsoft.BearerToken;
import com.asopagos.reportes.microsoft.EmbeddedReport;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de Power BI DashBoard 
 * <b>Historia de Usuario:</b> TRASVERSALES
 * 
 * @author Ricardo Hernandez Cediel <hhernandez@heinsohn.com.co>
 */
@Path("dashboard")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface ConsultasPowerBIService {

	/**
	 * Capacidad que permite obtener los roles definidos en el keycloack para el usuario del contexto 
	 * 
     * @param rolUsuario
     *        <code>String></code>
     *        El rol asociado al usuario y que corresponde con los parametrizados en DashBoardConsultas
	 * 
	 * @return <code>DashBoardConsultaDTO</code>
     * 	      El permiso asociado al usuario de los parametrizados en dashboard
	 */
	@GET
	@Path("/consultar/permisos/{rolUsuario}")
	public DashBoardConsultaDTO consultarPermisos(@PathParam("rolUsuario") 
		String rolUsuario, @Context UserDTO userDTO);
	
	/**
	 * Obtiene los datos de un reporte embebido de PowerBI 
	 * @param groupId Identificador del grupo de PowerBI donde se encuentra el reporte
	 * @param reportId Identificador del reporte
	 * @return Objeto con los datos necesario para solicitar a PowerBI la renderización de un reporte embebido
	 */
	@GET
    @Path("/embeddedReport")
    public EmbeddedReport getEmbeddedReport(
            @NotNull @QueryParam("groupId") String groupId, 
            @NotNull @QueryParam("reportId") String reportId);
	
}