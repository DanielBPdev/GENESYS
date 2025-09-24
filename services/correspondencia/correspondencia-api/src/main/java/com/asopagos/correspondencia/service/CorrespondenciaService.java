package com.asopagos.correspondencia.service;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.dto.NumeroRadicadoCorrespondenciaDTO;
import com.asopagos.dto.afiliaciones.DestinatarioCajaCorrespondenciaDTO;
import com.asopagos.entidades.ccf.core.CajaCorrespondencia;
import com.asopagos.enumeraciones.core.EstadoCajaCorrespondenciaEnum;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados con la gestión de empleadores <b>Módulo:</b> Asopagos -
 * transversal<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author Jerson Zambrano <a href="jzambrano:jzambrano@heinsohn.com.co"> jzambrano</a>
 *
 * @author <a href="mailto:jopinzon@heinsohn.com.co"> jopinzon</a>
 */
@Path("")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface CorrespondenciaService {
    
    /**
     * Método que permite obtener la caja de corrrspondencia abierta para una 
     * sede de la caja de compensación
     * @param codigoSedeCajaCompensacion
     * @param userDTO
     * @return 
     */
    @GET
    @Path("cajasCorrespondencia/abierta")
    public CajaCorrespondencia obtenerCajaCorrespondenciaAbierta(
            @QueryParam("codigoSede") String codigoSedeCajaCompensacion, @Context UserDTO userDTO);
    
    /**
     * Método que recupera el listrado de números de radicación de solicitudes 
     * para una caja de correspondencia
     * @param codigoEtiquetaCajaCorrespondencia
     * @return 
     */    
    @GET
    @Path("cajasCorrespondencia/{codigoEtiqueta}/solicitudes")
    public List<String> obtnenerListaSolicitudesCajaCorrespondecia(
            @PathParam("codigoEtiqueta") String codigoEtiquetaCajaCorrespondencia);
    
    /**
     * Método que realiza búsqueda de cajas de correspondencia de acuerdo a los
     * criterios recibidos por parámetro
     * @param codigoEtiqueta
     * @param codigoSede
     * @param fechaInicio
     * @param fechaFin
     * @param estado
     * @return 
     */    
    @GET
    @Path("cajasCorrespondencia/buscar")
    public List<CajaCorrespondencia> buscarCajasCorrespondencia(@QueryParam("codigoEtiqueta") String codigoEtiqueta, 
            @QueryParam("codigoSede") String codigoSede, @QueryParam("fechaInicio") Long fechaInicio, 
            @QueryParam("fechaFin") Long fechaFin, @QueryParam("estado") EstadoCajaCorrespondenciaEnum estado);
    
    /**
     * Método que implementa la lógica para cerrar una caja de correspondencia
     * @param codigoEtiqueta
     * @param inDTO
     */    
    @POST
    @Path("cajasCorrespondencia/{codigoEtiqueta}/cerrar")
    public void cerrarCajaCorrespondencia(
            @PathParam("codigoEtiqueta") String codigoEtiqueta,            
            DestinatarioCajaCorrespondenciaDTO inDTO);
    
    /**
     * Método que actualiza el estado de la caja de correspondencia
     *
     * @param codigoEtiqueta
     */
    @POST
    @Path("cajasCorrespondencia/{codigoEtiqueta}/recibir")
    public void registrarRecepcionCajaCorrespondencia(
            @PathParam("codigoEtiqueta") String codigoEtiqueta,@Context UserDTO userDTO);    
    
    /**
	 * Método que se encarga de retornar un número de sticker para
	 * correspondencia fisica determinando si se auto genera o se trata de uno
	 * preimpreso
	 * 
	 * <ul>
	 * <li>número de radicado CCAAAA###### donde:
	 * <ul>
	 * <li>CC: código de la caja de compensación</li>
	 * <li>AA: año</li>
	 * <li>BB: oficina / sede / punto de atención que envía</li>
	 * <li>######: número consecutivo (se reinicia cada año)</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * 
     * @param sccID
     * @return
     */
    @POST
    @Path("/cajasCorrespondencia")
    @Deprecated
	public NumeroRadicadoCorrespondenciaDTO obtenerStickerCorrespondenciaFisica(@QueryParam("sede") String sccID,
			@Context UserDTO userDTO);
}
