package com.asopagos.entidades.pagadoras.composite.service;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.entidades.pagadoras.dto.ConsultarEntidadPagadoraOutDTO;
import com.asopagos.entidades.pagadoras.dto.EntidadPagadoraDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validation.annotation.ValidacionCreacion;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de las entidades pagadoras <b>Historia de Usuario:</b> 121-109, 121-133
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
@Path("entidadesPagadorasComposite")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface EntidadPagadoraCompositeService {

    /**
     * Método que se encarga de buscar la informacion basica de las entidades pagadoras por los filtros enviados
     *
     * @param tipoIdentificacion
     *        Tipo de identificación entidad
     * @param numeroIdentificacion
     *        Numero de identificación entidad
     * @param razonSocial
     *        Nombre entidad
     * @return Lista de entidades pagadoras
     *
     */
    @GET
    @Path("/buscar")
    public List<ConsultarEntidadPagadoraOutDTO> buscarEntidadPagadoraIdentificacion(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("razonSocial") String razonSocial);

    /**
     * Método que se encarga de registrar la entidad pagadora
     *
     * @param entidadPagadoraDTO
     *        Informacion de la entidad pagadora
     * @return Long identificador de la entidad pagadora
     *
     */
    @POST
    @Path("/registrar")
    public Long registrarEntidadPagadora(@NotNull @Valid @ValidacionCreacion EntidadPagadoraDTO entidadPagadoraDTO);

    /**
     * Método que se encarga de consultar la informacion completa de una entidad pagadora
     * 
     * @param tipoIdentificacion
     *        tipo de identifiación entidad
     * @param numeroIdentificacion
     *        Número de identificación entidad
     * @return EntidadPagadoraDTO Objeto con la informacion de la entidad pagadora
     */
    @GET
    @Path("/consultarInformacion")
    public EntidadPagadoraDTO consultarInformacionEntidadPagadora(
            @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);

}
