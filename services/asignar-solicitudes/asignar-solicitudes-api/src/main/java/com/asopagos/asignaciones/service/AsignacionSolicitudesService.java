package com.asopagos.asignaciones.service;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.asignaciones.dto.InformacionProcesoDTO;
import com.asopagos.asignaciones.dto.InicioTareaDTO;
import com.asopagos.entidades.ccf.core.SedeCajaCompensacion;
import com.asopagos.entidades.ccf.general.ParametrizacionMetodoAsignacion;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripcion:</b> Interfaz de servicios Web REST la asignacion de
 * solicitudes<br/>
 * <b>Historia de Usuario:</b> HU-TRA-084 Administrar asignación de solicitudes
 * <br/>
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@Path("asignacionSolicitud")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AsignacionSolicitudesService {

	/**
	 * Método que permite consultar las sedes vinculadas a la caja de
	 * compensación
	 * 
	 * @param idCajaCompensacion,
	 *            identificacdor de la caja de compensación familiar
	 * @return Listado de sedes de la caja de compensacion familiar
	 */
	@GET
	@Path("sedes")
	public List<SedeCajaCompensacion> consultarSedesCajaCompensacion();

	/**
	 * Método que permite consultar la parametrizacion de metodos de asignacion
	 * 
	 * @return listado de metodos de asignacion
	 */
	@GET
	@Path("parametrizacionesMetodos")
	public List<ParametrizacionMetodoAsignacion> consultarParametrizacionMetodoAsignacion();

	/**
	 * Metodo que permite actualizar los metodos de asignacion con respecto a
	 * los procesos y sede de caja de compensacion familiar
	 * 
	 * @param parametrizaciones,
	 *            Listado de parametrizaciones a actualizar
	 */
	@POST
	@Path("asignaciones")
	public void actualizarMetodoAsignacion(List<ParametrizacionMetodoAsignacion> parametrizaciones);

	/**
	 * Metodo que se invoca desde el bpm y se encarga de ejecuta los distintos metodos de asignacion 
	 * @param sedeCajaCompensacion, identificador de la caja de compensacion
	 * @param procesoEnum, identificador del proceso
	 * @return el usuario asignado a la ejecucion de la tarea
	 */
	@POST
	@Path("{procesoEnum}/asignaciones")
	public String ejecutarAsignacion(
			@PathParam("procesoEnum") ProcesoEnum procesoEnum,@QueryParam("sede") Long sedeCajaCompensacion);
	
	/**
	 * Metodo que permite consultar la parametrizacion del metodo de asignacion para
	 * el proceso y la sede enviadas como parametros
	 * 
	 * @param proceso,
	 *            identificador del proceso
	 * @param sede,
	 *            identificador de la sede
	 * @return la parametrizacion del metodos de asignacion
	 */
	@GET
	@Path("parametrizacionMetodoAsignacion")
	public ParametrizacionMetodoAsignacion consultarMetodoAsignadoProcesoSede (@QueryParam("proceso")ProcesoEnum proceso, @QueryParam("sede")Long sede);
	
    /**
     * Metodo encargado de consultar procesos a reasignar
     * 
     * @param proceso,
     *        filtro que indica a que proceso
     * @param grupo,
     *        filtro que indica el grupo
     * @param sede,
     *        filtro que indica la sede
     * @param usuario,
     *        filtro que indica el usuario
     * @param userDTO,
     *        usuario de la aplicacion
     * @return listado de la informacion de los procesos
     */
	@GET
	@Path("reasignacion/consulta")
    public List<InformacionProcesoDTO> consultarProcesosReasignacion(@QueryParam("proceso") ProcesoEnum proceso,
            @QueryParam("grupo") String grupo, @QueryParam("sede") Long sede, @QueryParam("usuario") String usuario);

    @POST
    @Path("inicioTarea")
    public void registrarInicioTarea(InicioTareaDTO inicioTareaDTO, @Context UserDTO userDTO);

    @POST
    @Path("inicioTarea/limpiar")
    public void limpiarRegistrosInicioTarea(@Context UserDTO userDTO);

}
