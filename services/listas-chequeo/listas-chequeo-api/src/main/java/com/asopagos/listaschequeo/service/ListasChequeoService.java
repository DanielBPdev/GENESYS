package com.asopagos.listaschequeo.service;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.dto.DocumentoRequisitoDTO;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.listaschequeo.dto.EtiquetaSolicitudesDTO;

/**
 * <b>Descripción:</b> Interfaz de servicios Web REST para adminsitración de
 * listas de chequeo <b>Historia de Usuario:</b> HU-TRA-061 Administración
 * general de listas de chequeo
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Path("listasChequeo")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface ListasChequeoService {

	/**
	 * Método encargado de agregar un nuevo registro de lista de chequeo<br/>
	 * 
	 * @param listaChequeo
	 * @return El identificador del nuevo registro
	 */
	@POST
	public List<Long> guardarListaChequeo(@NotNull @Valid ListaChequeoDTO listaChequeo);

	/**
	 * Método encargado de consultar la lista de chequeo de acuerdo al número de
	 * solicitud y los datos de identificación de la persona
	 * 
	 * @param idSolicitud
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	@POST
	@Path("consultarListaChequeo")
	public List<ListaChequeoDTO> consultarListaChequeo(@NotNull @QueryParam("idSolicitud") Long idSolicitud,
			@NotNull @QueryParam("clasificacion") ClasificacionEnum clasificacion,
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion, List<String> grupos);

	/**
	 * Método encargado de consultar la lista de chequeo segun los datos de la
	 * persona
	 * 
	 * @param idSolicitud
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @param clasificacion
	 * @return
	 */
	@POST
	@Path("/consultarListaChequeoPorClasificacion")
	public ListaChequeoDTO consultarListaChequeoPorClasificacion(@NotNull @QueryParam("idSolicitud") Long idSolicitud,
			@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
			@NotNull @QueryParam("clasificacion") ClasificacionEnum clasificacion);

	/**
	 * Metodo para consultar los items de chequeo
	 * 
	 * @param numeroRadicacion
	 * @return listaItems
	 */
	@GET
	@Path("/consultaItemsChequeo")
	public List<EtiquetaSolicitudesDTO> consultaItemsChequeo(@QueryParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Consulta los documentos tipificados asociados a la persona
     * @param tipoIdentificacion
     *        Tipo de identificación de persona
     * @param numeroIdentificacion
     *        Numero de identificación de persona
     * @return Lista de documentos tipificados o lista vacia si no existen datos
     */
    @GET
    @Path("/consultarDocumentosRequisito")
    public List<DocumentoRequisitoDTO> consultarDocumentosRequisitoPersona(
            @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);
}
