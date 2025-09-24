package com.asopagos.usuarios.service;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.enumeraciones.usuarios.EstadoUsuarioEnum;
import com.asopagos.usuarios.constants.TipoGrupoEnum;
import com.asopagos.usuarios.dto.GrupoDTO;
import com.asopagos.usuarios.dto.RolDTO;
import com.asopagos.usuarios.dto.UsuarioDTO;

/**
 * <b>Descripcion:</b> Interfaz de servicios Web REST para adminsitración de grupos en el modulo de seguridad del sistema<br/>
 * <b>Historia de Usuario:</b> HU-SEG<br/>
 * @author Luis Arturo Zarate Ayala <a href="mailto:lzarate@heinsohn.com.co"></a>
 */
@Path("grupos")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface GruposService {

    /**
     * Metodo encargado de obtener los usuarios pertenecientes a un grupo en especifico
     * @param id,
     *        identificador del grup
     * @param sede,
     *        sede de la caja de compensacion familiar
     * @param estado,
     *        estado de los usuarios a consultar
     * @return
     */
    @GET
    @Path("/{idGrupo}/miembros")
    public List<UsuarioDTO> obtenerMiembrosGrupo(@PathParam("idGrupo") String id, @QueryParam("sede") String sede,
            @QueryParam("estado") EstadoUsuarioEnum estado);

    /**
     * Metodo encargado de realizar la consulta de los grupos, mediante el valor del atributo tipo del Grupo
     * @param tipo,
     *        indica el tipo de grupo, por el cual desea realizar el filtrp de consulta
     * @return Grupos pertenecientes a un tipo especificado por parametro
     */
    @GET
    @Path("{tipo}")
    public List<GrupoDTO> consultarGrupos(@PathParam("tipo") TipoGrupoEnum tipo);

    /**
     * Servicio que permite determinar si un usuario pertenece a un determinado grupo
     * @param grupo,
     *        grupo en keycloak
     * @param nombreUsuario,
     *        nombre de usuario
     * @return true en caso de pertenecer, false caso contrario
     */
    @GET
    @Path("/{grupo}/miembro")
    public Boolean esMiembroGrupo(@PathParam("grupo") String grupo, @QueryParam("nombreUsuario") String nombreUsuario);

    /**
     * Servicio encargado de consultar los roles de un grupo
     * @param idGrupo,
     *        identificador del grupo a consultar los roles
     * @return retorna la lista de roles pertenecientes al grupo
     */
    @GET
    @Path("/roles/{idGrupo}")
    public List<RolDTO> consultarRolesGrupo(@NotNull @PathParam("idGrupo") String idGrupo);

    /**
     * Método encargado de actualiza los roles pertenecientes a un grupo
     * @param id,
     *        id del grupo a actualizar
     * @param lstRoles,
     *        lista de roles a actualizar del grupo
     */
    @PUT
    @Path("/actualizarGrupoRoles/{idGrupo}")
    public void actualizarGrupoRoles(@NotNull @PathParam("idGrupo") String id, List<RolDTO> lstRoles);

    /**
     * Metodo encargado de inactivar los miembros de un grupo
     * @param id,
     *        identificador del grupo
     */
    @PUT
    @Path("/{idGrupo}/inactivar")
    public void inactivarMiembrosGrupo(@NotNull @PathParam("idGrupo") String id);

    /**
     * Metodo encargado de consultar los identificadores de los grupos mediante un listado de nombre de grupos
     * @param nombreGrupos,
     *        listado de nombre de los grupos
     * @return mapa, llave-valor nombre-id de los grupos enviados como parametro
     */
    @POST
    @Path("/identificadores")
    public Map<String, String> consultarIdsPorNombresGrupos(@NotNull List<String> nombreGrupos);
    
    /**
     * Metodo encargado de consultar los nombres de los grupos por medio de su identificador
     * @param identificadores,
     *        listado de identificadores a consultar
     * @return mapa, llave-valorm id-nombre
     */
    @POST
    @Path("/nombres")
    public Map<String, String> consultarNombresPorIdsGrupos(@NotNull List<String> ids);
    
    /**
     * Servicio encargado de consultar los roles de la aplicación
     * @return retorna la lista de roles existentes
     */
    @GET
    @Path("/roles")
    public List<RolDTO> consultarRoles();
    
}
