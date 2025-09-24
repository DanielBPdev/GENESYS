package com.asopagos.fovis.composite.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.asopagos.dto.fovis.InformacionSubsidioFOVISDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

@Path("externalAPI/fovis")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface IntegracionFovisService {

    /**
     * Servicio en cargado de obtener la información de la solicitud de subsidio fovis por los parametros de busqueda
     * @param numeroRadicado
     *        Número de radicado de la solicitud de postulación
     * @param tipoIdentificacion
     *        Tipo de identificación del Jefe Hogar
     * @param numeroIdentificacion
     *        Número de identificación del Jefe Hogar
     * @return Informacion de subsidio fovis
     */
    @GET
    @Path("/obtenerInfoFovis")
    public InformacionSubsidioFOVISDTO obtenerInfoFovis(@QueryParam("numeroRad") String numeroRadicado,
            @QueryParam("tipoID") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("numeroID") String numeroIdentificacion);

}
