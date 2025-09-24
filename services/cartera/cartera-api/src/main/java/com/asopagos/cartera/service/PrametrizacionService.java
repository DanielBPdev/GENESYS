package com.asopagos.cartera.service;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.enumeraciones.cartera.ParametrizacionEnum;
import com.asopagos.enumeraciones.cartera.TipoParametrizacionCarteraEnum;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la parametrización de los datos temporales de la Caja de compensación.<b>Módulo:</b> Asopagos - Cartera<br/>
 *
 * @author Julian Andres Sanchez Bedoya <jusanchez@heinsohn.com.co>
 */
@Path("parametrizacion")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface PrametrizacionService {

    /**
     * Servicio encargado de consultar la pametrización
     * @param tipoParametrizacion,
     *        tipo de parametrización a guardar
     * @return el json del dto encontrado
     */
    @GET
    @Path("/consultarDatoTemporalParametrizacion")
    public String consultarDatoTemporalParametrizacion(@NotNull @QueryParam("parametrizacion") ParametrizacionEnum parametrizacion);

    /**
     * Servicio encargado de guardar el dato temporal de una parametrización
     * @param prametrizacion,
     *        parametrización a cual pertenece el dato temporal
     * @param jsonPayload,
     *        json que contiene la información a almacenar
     */
    @POST
    @Path("/guardarParametrizacion")
    public void guardarDatoTemporalParametrizacion(@QueryParam("parametrizacion") ParametrizacionEnum parametrizacion,
            @NotNull String jsonPayload);

}
