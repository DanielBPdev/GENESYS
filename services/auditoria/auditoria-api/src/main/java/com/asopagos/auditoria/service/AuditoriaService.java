package com.asopagos.auditoria.service;

import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import com.asopagos.auditoria.dto.RevisionDTO;
import com.asopagos.auditoria.dto.ParametrizacionTablaAuditableDTO;
import com.asopagos.entidades.enumeraciones.auditoria.TipoOperacionEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */
@Path("auditoria")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AuditoriaService {

    @GET
    @Path("/consultarLog")
    public List<RevisionDTO> consultarLog(@QueryParam("usuario") @NotNull String usuario, @QueryParam("fechaInicio") @NotNull Long fechaInicio, @QueryParam("fechaFin") @NotNull Long fechaFin, @QueryParam("tipoOperacion") @NotNull TipoOperacionEnum tipoOperacion, @QueryParam("tablas") List<String> tablas,@Context UriInfo uri, @Context HttpServletResponse response);

   
    @GET
    @Path("/consultarOperacionesAuditoria")
    public List<ParametrizacionTablaAuditableDTO> consultarOperacionesAuditoria(@QueryParam("tablas") List<String> tablasConsultar);

    @POST
    @Path("/actualizarTablasAuditables")
    public void actualizarTablasAuditables(List<ParametrizacionTablaAuditableDTO> tablasAuditablesIn);
    
    @GET
    @Path("/listadoTablasAuditables")
    public Set<String> listarTablasAuditables();
    
}


