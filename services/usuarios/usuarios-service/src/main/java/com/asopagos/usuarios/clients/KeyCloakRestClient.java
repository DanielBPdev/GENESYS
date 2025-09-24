package com.asopagos.usuarios.clients;

import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.asopagos.usuarios.dto.CambiarContrasenaDTO;
import com.asopagos.usuarios.dto.GrupoDTO;
import com.asopagos.usuarios.dto.ResultadoDTO;
import com.asopagos.usuarios.dto.RolDTO;
import com.asopagos.usuarios.dto.UsuarioDTO;

/**
 * Interface con las operaciones disponibles en runtime para KeyCloak rest
 * @author lzarate@heinsohn.com.co
 */
@Path("rest")
@Consumes(value = MediaType.APPLICATION_JSON)
public interface KeyCloakRestClient {

    /**
     * Metodo encargado de cambiar la contraseña de un usuario en KeyCloack
     * @param nombreUsuario,
     *        nombre de usuario
     * @param actual,
     *        contrasenia actual
     * @param nueva,
     *        nueva contrasenia
     * @param confirmacion,
     *        confirmacion de la contrasenia
     * @return estado del cambio de credenciales
     */
    @POST
    @Path("changePassword")
    public ResultadoDTO cambiarContrasenia(CambiarContrasenaDTO changePasswordDTO);
    
    @GET
    @Path("userPagedAudited")
    public List<UsuarioDTO> consultarUsuariosAuditoriaPaginado(@QueryParam("nombreUsuario") String nombreUsuario,@QueryParam("firstResult") Integer firstResult,
            @QueryParam("maxResults") Integer maxResults);

    /**
     * Metodo encargado de consultar los usuarios auditados
     * @return Listado de usuarios dto
     */
    @GET
    @Path("userAudited")
    public List<UsuarioDTO> consultarUsuariosAuditoria();
    
    /**
     * Metodo encargado de consultar los usuarios habilitados
     * @return Listado de usuarios dto
     */
    @GET
    @Path("userEnabled")
    @Produces("application/json; charset=UTF-8")
    public List<UsuarioDTO> consultarUsuariosActivos();

    /**
     * Metodo encargado de validar si el usuario y contraseña corresponden correctamente
     * @param userName,
     *        nombre de usuario
     * @param password,
     *        contraseña
     * @return true en caso de coincidir, false caso contrario
     */
    @POST
    @Path("authentication")
    @Produces("application/json; charset=UTF-8")
    public Boolean autenticarUsuario(@QueryParam("userName") String userName, @QueryParam("password") String password);
    
    /**
     * Metodo encargado de actualizar los roles a un grupo de usuarios
     * @param idGrupo
     * @param lstRoles
     */
    @PUT
    @Path("groupRol")
    @Produces("application/json; charset=UTF-8")
    public void actualizarRolesGrupos(@QueryParam("idGrupo") String idGrupo, List<RolDTO> lstRoles);
    
    /**
     * Metodo encargado de obtener los grupos en KeyCloak
     * @param tipo,
     *        tipo de grupo a consultar
     * @return listado de grupos
     */
    @GET
    @Path("groups")
    @Produces("application/json; charset=UTF-8")
    public List<GrupoDTO> consultarGrupos(@QueryParam("tipo") String tipo);
    
    @POST
    @Path("attributes")
    @Produces("application/json; charset=UTF-8")
    public ResultadoDTO obtenerUsuariosPorAtributos(Map<String,String> attributes);
    
    /**
     * Metodo encargado de consultar los datos de un usuario
     * @return UsuarioDTO
     */
    @GET
    @Path("user")
    public UsuarioDTO consultarUsuario(@QueryParam("nombreUsuario")String nombreUsuario);
    
}
