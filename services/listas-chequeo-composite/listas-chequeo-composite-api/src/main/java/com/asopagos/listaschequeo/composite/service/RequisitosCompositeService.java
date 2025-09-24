package com.asopagos.listaschequeo.composite.service;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.listaschequeo.dto.ConsultarRequisitosListaChequeoOutDTO;
import com.asopagos.listaschequeo.dto.RequisitoCajaClasificacionDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.validation.annotation.ValidacionActualizacion;
import com.asopagos.validation.annotation.ValidacionCreacion;


/**
 * <b>Descripción:</b> Interfaz de servicios Web REST para adminsitración de 
 * requisitos documentales
 * <b>Historia de Usuario:</b> HU-TRA-061 Administración general de listas de
 * chequeo
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Path("requisitosComposite")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface RequisitosCompositeService {
    
    /**
     * Método encargado de actualizar la información de los requisitos y las 
     * cajas de compensación relacionadas
     *
     * @param requisitosCajaClasificacion
     * @param userDTO
     */
    @POST
    @Path("cajasCompensacion/clasificaciones")
    public void guardarCrearRequisitosCajaClasificacion(
            @NotNull @ValidacionCreacion List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion);     
    
    /**
     * Método encargado de actualizar la información de los requisitos y las 
     * cajas de compensación relacionadas
     *
     * @param requisitosCajaClasificacion
     * @param userDTO
     */
    @PUT
    @Path("cajasCompensacion/clasificaciones")
    public void guardarActualizarRequisitosCajaClasificacion(
            @NotNull @ValidacionActualizacion List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion); 
    
    /**
     * Método encargado de consultar la lista de chequeo de acuerdo al número de
     * solicitud y los datos de identificación de la persona
     * @param idSolicitud
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     */
    @GET
    public List<ListaChequeoDTO> consultarListaChequeo(@NotNull @QueryParam("idSolicitud") Long idSolicitud,
            @NotNull @QueryParam("tipoTransaccion") TipoTransaccionEnum tipoTransaccion, 
            @NotNull @QueryParam("clasificacion") ClasificacionEnum clasificacion,
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @Context UserDTO userDTO);
    
    /**
     * Método encargado de consultar la información de las listas de chequeo, 
     * para el tipo de solicitante, caja de compensación, clasificación y 
     * persona recibidos por parámetro
     * 
     * @param tipoTransaccion
     * @param clasificacion
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return lista de requisitos listas de chequeo
     */
    @GET
    @Path("listasChequeo")
    public List<ConsultarRequisitosListaChequeoOutDTO> consultarRequisitosListaChequeo(
            @NotNull @QueryParam("tipoTransaccion") TipoTransaccionEnum tipoTransaccion, 
            @NotNull @QueryParam("clasificacion") ClasificacionEnum clasificacion,
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, 
            @Context UserDTO userDTO);
}
