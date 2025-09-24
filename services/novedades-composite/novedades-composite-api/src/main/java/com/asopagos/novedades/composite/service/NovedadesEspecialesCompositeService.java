package com.asopagos.novedades.composite.service;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.novedades.composite.dto.BeneficiarioGrupoAfiliadoDTO;
import com.asopagos.novedades.composite.dto.EmpleadorAfiliadosDTO;
import com.asopagos.novedades.composite.dto.SolicitudAfiliacionRolDTO;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.novedades.dto.DatosNovedadRepresentanteDTO;
import com.asopagos.novedades.dto.DatosNovedadRolContactoDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados 
 * con la ejecución de novedades especiales <b>Historia de
 * Usuario:</b> Proceso 1.3
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@Path("novedadesEspecialesComposite")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface NovedadesEspecialesCompositeService {

	/**	
	 * Operación encargada de ejecutar la novedad para establecer cambios en representante
	 * legal, suplente y socios 
	 * 
	 * @param datosNovedadRepresentanteDTO
	 *            datos asociados al Representante Legal, Suplente y Socios.
	 */
	@POST
	@Path("/ejecutarCambiosRepresentantes")
	public void ejecutarCambiosRepresentantes(@NotNull DatosNovedadRepresentanteDTO datosNovedadRepresentanteDTO);
	
	/**	
	 * Operación encargada de ejecutar la novedad para actualizar Roles de Contacto Afiliacion/Aportes/Subsidio
	 * 
	 * @param datosNovedadRolContactoDTO
	 *            datos asociados a Roles de contacto Afiliacion/Aportes/Subsidio.
	 */
	@POST
	@Path("/ejecutarCambiosRolesContacto")
	public void ejecutarCambiosRolesContacto(@NotNull DatosNovedadRolContactoDTO datosNovedadRolContactoDTO);

	/**
	 * Servicio que se encarga de desafiliar un empleador y los roles asociados.
	 * @param desafiliacionDTO dto con los datos para desafiliar.
	 */
	@POST
	@Path("/ejecutarDesafiliacion")
	public void ejecutarDesafiliacion(@NotNull EmpleadorAfiliadosDTO desafiliacionDTO);
	
	/**
	 * Servicio que se encarga de ejecuta la sustitución patronal.
	 * Se llama la desafiliacion si el empleador llega con estado inactivo.
	 * @param sustitucion dto con la información necesaria.
	 */
	@POST
	@Path("/ejecutarSustitucionTrabajadores")
	public void ejecutarSustitucionTrabajadores(@NotNull EmpleadorAfiliadosDTO sustitucion);
	
	/**
	 * Servicio que se encarga de ejecutar el retiro de un trabajador.
	 * @param sustitucion dto con la información necesaria.
	 */
	@POST
	@Path("/ejecutarRetiroTrabajadores")
	public void ejecutarRetiroTrabajadores(@NotNull RolAfiliadoModeloDTO rolAfiliadoModeloDTO);
	
	/**
	 * Métood encargado de ejecutar la actualizacion de un beneficiario.
	 * @param beneficiarioGrupoAfiliadoDTO beneficiario a actualizar.
	 */
	@POST
	@Path("/ejecutarActualizacionBeneficiario")
	public void ejecutarActualizacionBeneficiario(@NotNull BeneficiarioGrupoAfiliadoDTO beneficiarioGrupoAfiliadoDTO);
	
	/**
	 * Servicio encargado de ejecutar la actualizarcion de una solicitud de afiliación y actualizar los roles afiliado.
	 * @param solicitudAfiliacionRolDTO solicitud de afiliación y su rol.
	 */
	@POST
	@Path("/ejecutarActualizacionSolicitud")
	public void ejecutarActualizacionSolicitud(@NotNull SolicitudAfiliacionRolDTO solicitudAfiliacionRolDTO);

    /**
     * Realiza el registro de la desafiliación de los empleadores por novedades automáticas
     * @param datosEmpleadorNovedad
     *        Objeto con la información de los empleadores a desafiliar
     */
    @POST
    @Path("/desafiliarEmpleadoresAutomatico")
    public void desafiliarEmpleadoresAutomatico(DatosNovedadAutomaticaDTO datosEmpleadorNovedad);

	/**
	 * Método encargado de ejecutar la actualizacion de grupo familiar de un beneficiario.
	 * @param beneficiarioGrupoAfiliadoDTO beneficiario a actualizar.
	 */
	@POST
	@Path("/ejecutarTrasladoBeneficiariosGrupoFamiliar")
	public void ejecutarTrasladoBeneficiariosGrupoFamiliar(@NotNull BeneficiarioGrupoAfiliadoDTO beneficiarioGrupoAfiliadoDTO);
	
}
