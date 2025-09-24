package com.asopagos.comunicados.service;

import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.notificaciones.dto.ParametrosComunicadoDTO;
import com.asopagos.validation.annotation.ValidacionActualizacion;
//import com.asopagos.entidades.transversal.core.ModuloPlantillaComunicado;

/**
 * <b>Descripcion:</b>Interfaz de servicios Web REST para adminsitración de 
 * plantillas de comunicados<br/>
 * <b>Módulo:</b> Asopagos - HU 000-073 
 * @author  <a href="mailto:jerodriguez@heinsohn.com.co"> jerodriguez</a>
 */
@Path("plantillas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PlantillasService {
    
    /**
     * <b>Descripción</b>Método que se encarga de actualizar la informacion de una plantilla<br/>
     * <code>plantillaComunicado contiene la información que se va a actualizar, idPlantillaComunicado es
     * el id de la PlantillaComunicado que se va a Actualizar</code>
     * @param plantillaComunicado 
     *              Es la infomracion de la plantilla que se va a actualizar
     * @param etiquetaPlantilla
     *              Etiqueta de la PlantillaComunicado a Actualizar
     */
    @PUT
    @Path("/{etiquetaPlantilla}")
    public void actualizarPlantillaComunicado(
            @PathParam("etiquetaPlantilla") EtiquetaPlantillaComunicadoEnum etiquetaPlantilla, 
            @NotNull @Valid @ValidacionActualizacion PlantillaComunicado plantillaComunicado);
	
    /**
     * <b>Descripción</b>Método encargado de consultar una plantilla de comunicado<br/>
     * <code>idPlantillaComunicado es el id de la plantilla</code>     
     * @param etiquetaPlantilla
     *              Etiqueta de la plantilla a consultar
     * @return  plantilla de tipo PlantillaComunicado
     */
    @GET
    @Path("{etiquetaPlantilla}")
    public PlantillaComunicado consultarPlantillaComunicado(
            @PathParam("etiquetaPlantilla") EtiquetaPlantillaComunicadoEnum etiquetaPlantilla);
    
    /**
     * <b>Descripción</b>Método encargado de resolver las variables del comunicado<br/>
     * @param etiquetaPlantillaComunicadoEnum
     *              Etiqueta de la plantilla a consultar
     * @param valor, valor para consultar sobre la tabla varibleComunicado
     * @return  Map<String, Object> objeto map con clave por objecto(registro)
     */
    @GET
    @Path("/{EtiquetaPlantillaComunicadoEnum}/resolverVariables")
	public Map<String, Object> resolverVariablesComunicado(
			@PathParam("EtiquetaPlantillaComunicadoEnum") EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum, 
            @QueryParam("idInstancia") Long idInstancia,
            @QueryParam("idSolicitud") Long idSolicitud, @QueryParam("ordenamiento") String ordenamiento);
    
    /**
     * <b>Descripción</b>Método encargado de resolver las variables del comunicado<br/>
     * @param etiquetaPlantillaComunicadoEnum, Etiqueta de la plantilla a consultar
     * @param idPlantillaComunicado, para evitar la consulta de la planilla dentro del método             
     * @param valor, valor para consultar sobre la tabla varibleComunicado
     * @return  Map<String, Object> objeto map con clave por objecto(registro)
     */
    @GET
    @Path("/{EtiquetaPlantillaComunicadoEnum}/resolverVariablesIdPlantilla")
    public Map<String, Object> resolverVariablesComunicadoIdPlantilla(
            @PathParam("EtiquetaPlantillaComunicadoEnum") EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum, 
            @QueryParam("idPlantillaComunicado") Long idPlantillaComunicado,
            @QueryParam("idInstancia") Long idInstancia,
            @QueryParam("idSolicitud") Long idSolicitud, @QueryParam("ordenamiento") String ordenamiento);
    
    /**
     * <b>Descripción</b>Método encargado de resolver las variables del encabezado<br/>
     * @param etiquetaPlantillaComunicadoEnum
     *              Etiqueta de la plantilla a consultar
     * @param valor, valor para consultar sobre la tabla varibleComunicado
     * @return  Map<String, Object> objeto map con clave por objecto(registro)
     */
    @GET
    @Path("/{EtiquetaPlantillaComunicadoEnum}/resolverVariablesEncabezado")
	public Map<String, Object> resolverVariablesEncabezado(
			@PathParam("EtiquetaPlantillaComunicadoEnum") EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum, 
            @QueryParam("idSolicitud") Long idSolicitud);
    
    /**
     * <b>Descripción</b>Método encargado de resolver las variables del comunicado<br/>
     * @param etiquetaPlantillaComunicadoEnum
     *              Etiqueta de la plantilla a consultar
     * @param valor, valor para consultar sobre la tabla varibleComunicado
     * @return  Map<String, Object> objeto map con clave por objecto(registro)
     */
    @POST
    @Path("/{EtiquetaPlantillaComunicadoEnum}/resolverVariablesParametros")
    public Map<String, Object> resolverVariablesComunicadoParametros(
            @PathParam("EtiquetaPlantillaComunicadoEnum") EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum,
            @QueryParam("idSolicitud") Long idSolicitud, ParametrosComunicadoDTO parametrosComunicadoDTO);
    
    /**
     * Método que resuelve los comunicados del consolidado de cartera
     * @param etiquetaPlantillaComunicadoEnum etiqueta
     * @param idPlantillaComunicado plantilla comunicado
     * @param idSolicitud identificador de la solicitud
     * @param ordenamiento listado con ordenamiento
     * @return
     */
    public Map<String, Object> resolverVariablesConsolidadoCartera(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum,
            Long idPlantillaComunicado, ParametrosComunicadoDTO parametrosComunicadoDTO, Long idSolicitud, String ordenamiento);
    
}
