package com.asopagos.listaschequeo.service;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.dto.ElementoListaDTO;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.listaschequeo.dto.TransaccionRequisitoDTO;

/**
 * <b>Descripción:</b> Interfaz de servicios Web REST para adminsitración de
 * listas de chequeo
 * <b>Historia de Usuario:</b> HU-TRA-061 Administración general de listas de
 * chequeo
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Path("tiposSolicitante")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface TiposSolicitanteService {

    /**
     * Método que permite consultar los tipos de solicitante parametrizados por
     * caja de compensación
     * 
     * @param tipoSolicitante
     * @return
     */
    @GET
    @Path("cajaCompensacion")
    public List<ElementoListaDTO> consultarClasificacionesHabilitadas(@NotNull @QueryParam("tipoSolicitante") String tipoSolicitante);

    /**
     * Obtiene los tipos de solicitante (sujetos trámite) soportados para la transación <code>tipoTx</code> indicada. Si la transacción
     * <code>tipoTx</code> es una tranascción que sólo aplica a una clasificación, entonces se retorna dicha clasificación
     * @param tipoTx
     *        Tipo de transacción {@link TipoTransaccionEnum}
     * @return Los sujetos trámite suceptibles de ser configurados en la lista de chequeo para la transacción dada, en caso de que la
     *         transacción dada aplique para una unica clasificación retorna la lista con un sólo elemento clasificación
     */
    @GET
    public List<ElementoListaDTO> consultarTiposSolicitante(@QueryParam("tipoTx") @NotNull TipoTransaccionEnum tipoTx);

    /**
     * Obtiene las transacciones y su identificación de aplicación para el proceso <code>proceso</code> indicado
     * @param proceso
     *        del que quieren obtenerse las transacciones
     * @return Lista con las transacciones disponibles para el proceso indicado {@link TransaccionRequisitoDTO}. Cada transaccion indica si aplica a una clasificación
     *         específica o a un o unos sujeto trámite
     */
    @GET
    @Path("consultarTransaccionesProceso")
    public List<TransaccionRequisitoDTO> consultarTransaccionesProceso(@QueryParam("proceso") @NotNull ProcesoEnum proceso);

}
