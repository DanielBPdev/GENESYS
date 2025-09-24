package com.asopagos.consola.ejecucion.proceso.asincrono.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.dto.EjecucionProcesoAsincronoDTO;
import com.asopagos.enumeraciones.fovis.TipoProcesoAsincronoEnum;

/**
 * <b>Descripción:</b> Interfaz de servicios Web REST la consola de ejecucion procesos asincronos
 * 
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 */
@Path("consolaEjecucionProcesosAsincronos")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface ConsolaEjecucionProcesoAsincronoService {

    /**
     * Servicio encargado de realizar el registro de ejecucion de un proceso asincrono
     * @param ejecucionProcesoAsincronoDTO
     *        Informacion proceso asincrono
     * @return Ejecucion proceso asincrono creada
     */
    @POST
    @Path("/registrarEjecucionProcesoAsincrono")
    public EjecucionProcesoAsincronoDTO registrarEjecucionProcesoAsincrono(EjecucionProcesoAsincronoDTO ejecucionProcesoAsincronoDTO);

    /**
     * Servicio encargado de obtener la ultima ejecucion de proceso asincrono apartir del tipo de proceso
     * @param tipoProceso
     *        Tipo proceso asincrono que se requiere
     * @return EjecucionProcesoAsincrono
     */
    @GET
    @Path("/consultarUltimaEjecucionProcesoAsincrono")
    public EjecucionProcesoAsincronoDTO consultarUltimaEjecucionProcesoAsincrono(
            @QueryParam("tipoProceso") TipoProcesoAsincronoEnum tipoProceso);

    /**
     * Servicio encargado de actualizar la ejecucion de un proceso asincrono
     * @param ejecucionProcesoAsincronoDTO
     *        Informacion proceso asincrono
     */
    @POST
    @Path("/actualizarEjecucionProcesoAsincrono")
    public void actualizarEjecucionProcesoAsincrono(EjecucionProcesoAsincronoDTO ejecucionProcesoAsincronoDTO);

    /**
     * Servicio encargado de la consulta del ultimo proceso asincrono asociado al tipo de proceso y el identificador del proceso
     * @param idProceso
     *        Identificador del proceso que es dependiente del tipo de proceso
     * @param tipoProceso
     *        Tipo de proceso asincrono
     * @return Informacion del proceso ejecutado
     */
    @GET
    @Path("/consultarUltimaEjecucionAsincrona/{idProceso}")
    public EjecucionProcesoAsincronoDTO consultarUltimaEjecucionAsincrona(@PathParam("idProceso") Long idProceso,
            @QueryParam("tipoProceso") TipoProcesoAsincronoEnum tipoProceso);

}
