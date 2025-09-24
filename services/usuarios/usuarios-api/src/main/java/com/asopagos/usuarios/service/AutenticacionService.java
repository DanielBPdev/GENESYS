package com.asopagos.usuarios.service;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.entidades.seguridad.Pregunta;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.usuarios.dto.TokenDTO;

/**
 * 
 */
@Path("autenticacion")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AutenticacionService {
	
	/**
	 * Retorna un token de autenticacion para un usuario externo.
	 *
	 * @return El token generado en Base64
	 */
	@POST
	@Path("generarToken")
	public TokenDTO generarTokenAcceso(@QueryParam("tipoIdentificacion")TipoIdentificacionEnum tipoIdentificacion, @QueryParam("numeroIdentificacion")String numeroIdentificacion,@QueryParam("digitoVerificacion")Short digitoVerificacion);
	
	/**
	 * Invalida un token generado para autenticar un usuario 
	 */
	@DELETE
	@Path("expirarToken")
	public void eliminarTokenAcceso(@QueryParam("tipoIdentificacion")TipoIdentificacionEnum tipoIdentificacion, @QueryParam("numeroIdentificacion")String numeroIdentificacion,@QueryParam("digitoVerificacion")Short digitoVerificacion, @QueryParam("obligarEliminar")Boolean obligarEliminar);

	@GET
	@Path("preguntas/{estado}")
	public List<Pregunta> consultarPreguntasPorEstado(@PathParam("estado")EstadoActivoInactivoEnum estado);
	
	/**
	 * Retorna un token de autenticacion para un usuario externo.
	 *
	 * @return El token generado en Base64
	 */
	@GET
	@Path("generarTokenCore")
	public TokenDTO generarTokenAccesoCore();
	
	/**
     * Metodo encargado de validar si el usuario y contraseña corresponden correctamente
     * @param userName,
     *        nombre de usuario
     * @param password,
     *        contraseña codificada con el algoritmo pbkdf2
     * @return true en caso de coincidir, false caso contrario
     */
	@POST
    @Path("")
    public Boolean validarCredencialesUsuario(@QueryParam("userName") String userName, @QueryParam("password") String password);
	
	/**
	 * Retorna un token de autenticacion el client System.
	 *
	 * @return El token generado en Base64
	 */
	@GET
	@Path("generarTokenSystem")
	public TokenDTO generarTokenAccesoSystem();
    
    @GET
	@Path("validarTokenAccesoTemporal/{sessionId}")    
    public Boolean validarTokenAccesoTemporal(@PathParam("sessionId") String sessionId);
    
    @POST
	@Path("cerrarSesion")       
    public void cerrarSesionesUsuario(String userName);

    /**
     * Actualiza la configuración de Access Token Lifespan en Keycloak
     * @param tiempoToken Tiempo en segundos
     */
    @POST
    @Path("tiempoToken")
    public void actualizarTiempoTokenWeb(@QueryParam("tiempo") Integer tiempoToken);
    
    /**
     * Obtiene un nuevo token de acceso de un usuario por medio de
     * un refresh_token
     * @param tiempoToken Tiempo en segundos
     */
    @POST
    @Path("/obtenerTokenAcceso")
    public String obtenerTokenAcceso(@NotNull @QueryParam("refreshToken") String refreshToken);

	@POST
	@Path("/obtenerTokenAccesoV2")
	public String obtenerTokenAccesoV2(@NotNull @QueryParam("refreshToken") String refreshToken);
}
