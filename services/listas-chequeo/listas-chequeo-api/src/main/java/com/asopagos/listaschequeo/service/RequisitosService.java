package com.asopagos.listaschequeo.service;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import com.asopagos.entidades.transversal.core.Requisito;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.HabilitadoInhabilitadoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.listaschequeo.dto.ConsultarRequisitosListaChequeoOutDTO;
import com.asopagos.listaschequeo.dto.RequisitoCajaClasificacionDTO;
import com.asopagos.listaschequeo.dto.RequisitoDTO;
import com.asopagos.listaschequeo.dto.RequisitosCajasCompensacionDTO;
import com.asopagos.listaschequeo.dto.RequisitosClasificacionesDTO;
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
@Path("requisitos")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface RequisitosService {
    
    
    /**
     * Método encargado de agregar un nuevo requisito con la información 
     * recibida en el parametro <code>requisito</code>.
     * 
     * @param requisito
     * @return El identificador del nuevo registro
     */       
    @POST
    public List<Long> crearRequisitos(@NotNull @ValidacionCreacion List<RequisitoDTO> requisitos);
    
    /**
     * Método encargado de actualizar los requisitos con la información recibida 
     * en el parametro <code>requisitos</code>.
     * 
     * @param idRequisito
     * @param estado
     */     
    @PUT
    @Path("{idRequisito}/estado/{estado}")
    public void actualizarEstadoRequisito(
            @PathParam("idRequisito") Short idRequisito, 
            @PathParam("estado") HabilitadoInhabilitadoEnum estado);
    
    /**
     * Método que permite la consulta de los requisitos y las cajas de 
     * compensación relacionadas a cada uno de ellos
     * 
     * @param nombre
     * @return DTO que contiene los requisitos y las cajas de compensación
     * relacionadas a cada uno de ellos
     */
    @GET
    public List<RequisitoDTO> consultarRequisitos(@QueryParam("nombre") String nombre);
    
    /**
     * Método que permite la consulta de los requisitos y las cajas de 
     * compensación relacionadas a cada uno de ellos
     * 
     * @param tipoTransaccion
     * @param tipoSolicitante
     * @param clasificacion
     * @return DTO que contiene los requisitos y las cajas de compensación
     * relacionadas a cada uno de ellos
     */
    @GET
    @Path("cajasCompensacion")
    public RequisitosCajasCompensacionDTO consultarRequisitosCajasCompensacion(
            @NotNull @QueryParam("tipoTransaccion") TipoTransaccionEnum tipoTransaccion,
            @NotNull @QueryParam("clasificacion") ClasificacionEnum clasificacion);
    
    /**
     * Método que permite la consulta de los requisitos y las cajas de 
     * compensación relacionadas a cada uno de ellos
     * 
     * @param tipoTransaccion
     * @param tipoSolicitante
     * @param idCajaCompensacion
     * @return DTO que contiene los requisitos y las cajas de compensación
     * relacionadas a cada uno de ellos
     */
    @GET
    @Path("clasificaciones")
    public RequisitosClasificacionesDTO consultarRequisitosClasificaciones(
            @NotNull @QueryParam("tipoTransaccion") TipoTransaccionEnum tipoTransaccion,
            @NotNull @QueryParam("tipoSolicitante") String tipoSolicitante,
            @NotNull @QueryParam("idCajaCompensacion") Integer idCajaCompensacion);
    
    /**
     * Método encargado de actualizar la información de los requisitos y las 
     * cajas de compensación relacionadas
     * 
     * @param requisitosCajaClasificacion
     */
    @POST
    @Path("cajasCompensacion/clasificaciones")
    public void crearRequisitosCajaClasificacion(
            @NotNull @ValidacionCreacion List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion, @Context UserDTO userDTO);     
    
    /**
     * Método encargado de actualizar la información de los requisitos y las 
     * cajas de compensación relacionadas
     * 
     * @param requisitosCajaClasificacion
     */
    @PUT
    @Path("cajasCompensacion/clasificaciones")
    public void actualizarRequisitosCajaClasificacion(
            @NotNull @ValidacionActualizacion List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion, @Context UserDTO userDTO);
    
    /**
     * Método encargado de actualizar la información de los requisitos y las 
     * cajas de compensación relacionadas, adicionalmente envía la información 
     * para que se replique en la caja
     *
     * @param requisitosCajaClasificacion
     * @param userDTO
     */
    @POST
    @Path("clasificaciones")
    public void replicarCrearRequisitosCajaClasificacion(
            @NotNull @ValidacionCreacion List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion);     
    
    /**
     * Método encargado de actualizar la información de los requisitos y las 
     * cajas de compensación relacionadas, adicionalmente envía la información 
     * para que se replique en la caja
     *
     * @param requisitosCajaClasificacion
     * @param userDTO
     */
    @PUT
    @Path("clasificaciones")
    public void replicarActualizarRequisitosCajaClasificacion(
            @NotNull @ValidacionActualizacion List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion); 

    /**
     * Método encargado de actualizar la información de los requisitos y las 
     * cajas de compensación relacionadas, adicionalmente envía la información 
     * para que se replique en la caja
     *
     * @param requisitosCajaClasificacion
     * @param userDTO
     */
    @POST
    @Path("clasificacionesPorCaja")
    public void replicarCrearRequisitosClasificacionPorCaja(
            @NotNull @ValidacionCreacion List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion);     
    
    /**
     * Método encargado de actualizar la información de los requisitos y las 
     * cajas de compensación relacionadas, adicionalmente envía la información 
     * para que se replique en la caja
     *
     * @param requisitosCajaClasificacion
     * @param userDTO
     */
    @PUT
    @Path("clasificacionesPorCaja")
    public void replicarActualizarRequisitosClasificacionPorCaja(
            @NotNull @ValidacionActualizacion List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion);
    
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
    @POST
    @Path("listasChequeo")
    public List<ConsultarRequisitosListaChequeoOutDTO> consultarRequisitosListaChequeo(
            @NotNull @QueryParam("tipoTransaccion") TipoTransaccionEnum tipoTransaccion, 
            @NotNull @QueryParam("clasificacion") ClasificacionEnum clasificacion,
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, 
            List<String> grupos);

    /**
     * Método encargado de actualizar los requisitos con la información recibida 
     * en el parametro <code>requisito</code>.
     * @param requisito
     */
    @PUT
    @Path("estadoRequisito")
    public void actualizarEstadoRequisitoCaja(Requisito requisito, @Context UserDTO userDTO);
}
