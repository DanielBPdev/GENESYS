package com.asopagos.subsidiomonetario.service;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.subsidiomonetario.dto.ParametrizacionCondicionesSubsidioCajaDTO;
import com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionLiquidacionSubsidioModeloDTO;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para
 * parametrizar conceptos y condiciones en subsidios<br/>
 */

@Path("subsidioMonetario")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface SubsidioMonetarioParametrosService {

    /**
     * Método que permite gestionar (crear y actualizar) parametrizaciones anuales de subsidio monetario
     * @param parametrizacionesLiq
     *        Parametrizaciones anuales a gestionar
     * @author rlopez
     * @return listado de indentificadores de las parametrizaciones gestionadas
     */
    @POST
    @Path("/gestionarConceptos")
    public List<Long> gestionarConceptos(@NotNull @Valid List<ParametrizacionLiquidacionSubsidioModeloDTO> parametrizacionesLiq);

    /**
     * Método que permite gestionar (crear y actualizar) parametrización de condiciones anuales de subsidio monetario
     * @param condiciones
     *        condiciones anuales a gestionar
     * @author rlopez
     * @return listado de identificadores de las parametrizaciones gestionadas
     */
    @POST
    @Path("/gestionarCondiciones")
    public List<Long> gestionarCondiciones(@NotNull @Valid ParametrizacionCondicionesSubsidioCajaDTO condiciones);

    /**
     * Método que permite obtener la lista de parametrizaciones de condiciones registradas
     * @author rlopez
     * @return DTO con la información de la parametrización de condiciones
     */
    @GET
    @Path("/consultarCondiciones")
    public List<ParametrizacionCondicionesSubsidioCajaDTO> consultarCondiciones();

    /**
     * Método que permite obtener la lista de parametrizaciones de condiciones registradas por año
     * @author dsuesca
     * @return DTO con la información de la parametrización de condiciones
     */
    @GET
    @Path("/consultarCondicionesPorAnio")
    public List<ParametrizacionCondicionesSubsidioCajaDTO> consultarCondicionesPorAnio(@QueryParam("anio") Integer anio);

    /**
     * Servicio que permite obtener lista de años parametrizados de condiciones
     * @return anios que se tienen parametrizados
     * @author dsuesca
     */
    @GET
    @Path("/obtenerAniosCondicionesParametrizadosSubsidio")
    public List<Integer> obtenerAniosCondicionesParametrizadosSubsidio();

    /**
     * Método que permite obtener la lista de parametrizaciones de conceptos registradas
     * @author rlopez
     * @return lista de parametrizaciones
     */
    @GET
    @Path("/consultarConceptos")
    public List<ParametrizacionLiquidacionSubsidioModeloDTO> consultarConceptos();

    /**
     * Método que permite obtener la lista de parametrizaciones de conceptos registrados por anio
     * @author dsuesca
     * @return DTO con la información de la parametrización de conceptos
     */
    @GET
    @Path("/consultarConceptosPorAnio")
    public List<ParametrizacionLiquidacionSubsidioModeloDTO> consultarConceptosPorAnio(@QueryParam("anio") Integer anio);

    /**
     * Servicio que permite obtener lista de años parametrizados de conceptos
     * 
     * @return anios que se tienen parametrizados
     * @author dsuesca
     */
    @GET
    @Path("/obtenerAniosConceptosParametrizadosSubsidio")
    public List<Integer> obtenerAniosConceptosParametrizadosSubsidio();

    @POST
    @Path("/procesarParametrosSubsidioCaja")
    public void procesarParametrosSubsidioCaja(@NotNull ParametrizacionCondicionesSubsidioCajaDTO parametrizacionCondicionesSubsidioCaja);
}
