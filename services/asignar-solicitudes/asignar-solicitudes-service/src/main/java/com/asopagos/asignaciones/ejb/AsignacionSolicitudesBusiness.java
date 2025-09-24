package com.asopagos.asignaciones.ejb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Context;
import org.apache.commons.lang3.StringUtils;
import com.asopagos.asignaciones.constants.NamedQueriesConstants;
import com.asopagos.asignaciones.dto.InformacionProcesoDTO;
import com.asopagos.asignaciones.dto.InicioTareaDTO;
import com.asopagos.asignaciones.service.AsignacionSolicitudesService;
import com.asopagos.asignaciones.util.CacheMetodoAsignacion;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.entidades.ccf.core.SedeCajaCompensacion;
import com.asopagos.entidades.ccf.general.InicioTarea;
import com.asopagos.entidades.ccf.general.ParametrizacionMetodoAsignacion;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.core.MetodoAsignacionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.usuarios.EstadoUsuarioEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.tareashumanas.clients.ObtenerTareasAsignadasUsuario;
import com.asopagos.tareashumanas.clients.ObtenerTareasAsignadasUsuarios;
import com.asopagos.tareashumanas.dto.TareaDTO;
import com.asopagos.usuarios.clients.ConsultarUsuarioSede;
import com.asopagos.usuarios.clients.ObtenerMiembrosGrupo;
import com.asopagos.usuarios.clients.UsuariosEnSesion;
import com.asopagos.usuarios.dto.UsuarioDTO;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la asignacion de solicitudes <b>Historia de Usuario:</b> HU-TRA-084
 * Administrar asignación de solicitudes
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */

@Stateless
public class AsignacionSolicitudesBusiness implements AsignacionSolicitudesService {

	/**
	 * Referencia al logger
	 */
	private final ILogger logger = LogManager.getLogger(AsignacionSolicitudesBusiness.class);

	@PersistenceContext(unitName = "asignacionSolicitudes_PU")
	private EntityManager entityManager;

	/**
	 * <b>Descripción</b>Método que se encarga de actualizar la informacion de
	 * una plantilla<br/>
	 * <code>plantillaComunicado contiene la información que se va a actualizar, idPlantillaComunicado es
	 * el id de la PlantillaComunicado que se va a Actualizar</code>
	 * 
	 * @param plantillaComunicado
	 *            Es la infomracion de la plantilla que se va a actualizar
	 * @param idPlantillaComunicado
	 *            Id de la PlantillaComunicado a Actualizar
	 */
	@Override
	public List<SedeCajaCompensacion> consultarSedesCajaCompensacion() {
		logger.debug("Inicia consultarSedesCajaCompensacion(Long)");
		List<SedeCajaCompensacion> sedes = entityManager
				.createNamedQuery(NamedQueriesConstants.BUSCAR_SEDES_CAJA_COMPENSACION_FAMILIAR,
						SedeCajaCompensacion.class)
				.getResultList();
		if (sedes != null && !sedes.isEmpty()) {
			return sedes;
		}
		logger.debug("Finaliza consultarSedesCajaCompensacion(Long)");
		return null;
	}

	/**
	 * Método que permite consultar la parametrizacion de metodos de asignacion
	 * 
	 * @return listado de metodos de asignacion
	 */
	@Override
	public List<ParametrizacionMetodoAsignacion> consultarParametrizacionMetodoAsignacion() {
		logger.debug("Inicia consultarSedesCajaCompensacion(Long)");
		List<ParametrizacionMetodoAsignacion> parametrizaciones = entityManager
				.createNamedQuery(NamedQueriesConstants.BUSCAR_PARAMETRIZACIONES_METODOS_ASIGNACION,
						ParametrizacionMetodoAsignacion.class)
				.getResultList();
		if (parametrizaciones != null && !parametrizaciones.isEmpty()) {
			return parametrizaciones;
		}
		logger.debug("Finaliza consultarSedesCajaCompensacion(Long)");
		return null;
	}

	/**
	 * Metodo que permite actualizar los metodos de asignacion con respecto a
	 * los procesos y sede de caja de compensacion familiar
	 * 
	 * @param parametrizaciones,
	 *            Listado de parametrizaciones a actualizar
	 */
	@Override
	public void actualizarMetodoAsignacion(List<ParametrizacionMetodoAsignacion> parametrizaciones) {
		logger.debug("Inicia actualizarMetodoAsignacion(List<DefinicionMetodoAsignacion>)");
		if (parametrizaciones == null || parametrizaciones.isEmpty()) {
			logger.debug(
					"Finaliza actualizarMetodoAsignacion(List<DefinicionMetodoAsignacion>): Parámetros no válidos");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}
		for (ParametrizacionMetodoAsignacion definicionMetodoAsignacion : parametrizaciones) {
            definicionMetodoAsignacion.setGrupo(definicionMetodoAsignacion.getProceso().getGrupo().getNombre());
            if (!definicionMetodoAsignacion.getMetodo().equals(MetodoAsignacionEnum.PREDEFINIDO)) {
                if (definicionMetodoAsignacion.getMetodo().equals(MetodoAsignacionEnum.CONSECUTIVO_TURNOS)
                        && definicionMetodoAsignacion.getUsuario() != null && !definicionMetodoAsignacion.getUsuario().equals("")) {
                    definicionMetodoAsignacion.setUsuario(definicionMetodoAsignacion.getUsuario());
                }
                definicionMetodoAsignacion.setUsuario(null);
            }
            if (definicionMetodoAsignacion.getIdParametrizacionMetodoAsignacion() != null) {
                entityManager.merge(definicionMetodoAsignacion);
            }
            else {
                entityManager.persist(definicionMetodoAsignacion);
            }
        }
        logger.debug("Finaliza actualizarMetodoAsignacion(List<DefinicionMetodoAsignacion>)");
    }

	/**
	 * Metodo que se invoca desde el bpm y se encarga de ejecuta los distintos
	 * metodos de asignacion
	 * 
	 * @param sedeCajaCompensacion,
	 *            identificador de la caja de compensacion
	 * @param procesoEnum,
	 *            identificador del proceso
	 * @return el usuario asignado a la ejecucion de la tarea
	 */
	@Override
	public String ejecutarAsignacion(ProcesoEnum procesoEnum,Long sedeCajaCompensacion) {
		if (procesoEnum == null || (sedeCajaCompensacion == null && !procesoEnum.getWeb())) {
			logger.debug("Finaliza ejecutarAsignacion(Long, ProcesoEnum): Parámetros no válidos");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}
		try {
		    logger.debug("Inicia ejecutarAsignacion(Long,ProcesoEnum)");
		    Long sede = null;
		    if (!procesoEnum.getWeb()) {
		        sede = sedeCajaCompensacion;                
            }			
            ParametrizacionMetodoAsignacion parametrizacionMetodoAsignacion = consultarMetodoAsignacion(procesoEnum, sede);
            
            if (parametrizacionMetodoAsignacion == null || parametrizacionMetodoAsignacion.getMetodo() == null
                    || MetodoAsignacionEnum.MANUAL == parametrizacionMetodoAsignacion.getMetodo()) {
                logger.debug("Finaliza ejecutarAsignacion(Long,ProcesoEnum): La asignacion es manual");
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
            }
            
			String usuarioAsignado = CacheMetodoAsignacion.getInstance().ejecutar(parametrizacionMetodoAsignacion, sede, entityManager);
			logger.debug("Finaliza ejecutarAsignacion(Long,ProcesoEnum)");
			if (usuarioAsignado == null) {
				throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_USUARIO_NO_DISPONIBLE_PARA_ASIGNACION,
						procesoEnum.getNombreProcesoBPM(),parametrizacionMetodoAsignacion.getMetodo().getNombre());
			}
			return usuarioAsignado;
		} catch (Exception e) {
			logger.error("No es posible instanciar el metodo de asignacion", e);
			logger.debug(
					"Finaliza ejecutarAsignacion(Long,ProcesoEnum): No es posible instanciar el metodo de asignacion");
			throw new TechnicalException("No es posible instanciar el metodo de asignacion", e);
		}
	}
	
    /**
	 * Metodo que permite consultar la definicion del metodo de asignacion para
	 * el proceso y la sede enviadas como parametros
	 * 
	 * @param proceso,
	 *            identificador del proceso
	 * @param sede,
	 *            identificador de la sede
	 * @return la definicion del metodos de asignacion
	 */
	@Override
	public ParametrizacionMetodoAsignacion consultarMetodoAsignadoProcesoSede(ProcesoEnum proceso, Long sede) {
		return consultarMetodoAsignacion(proceso, sede);
	}

	/**
	 * Metodo que permite consultar la definicion del metodo de asignacion para
	 * el proceso y la sede enviadas como parametros
	 * 
	 * @param proceso,
	 *            identificador del proceso
	 * @param sede,
	 *            identificador de la sede
	 * @return la definicion del metodos de asignacion
	 */
	private ParametrizacionMetodoAsignacion consultarMetodoAsignacion(ProcesoEnum proceso, Long sede) {
	    logger.info("Inicia consultarMetodoAsignacion(ProcesoEnum "+proceso+", Long "+sede+")");
	    ParametrizacionMetodoAsignacion definicion;
	    try {
    		if (proceso == null || (sede == null && !proceso.getWeb())) {
                logger.debug("Finaliza consultarMetodoAsignadoProcesoSede(ProcesoEnum, Long): Parámetros no válidos");
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
            }
    		logger.debug("Finaliza consultarMetodoAsignacion(ProcesoEnum, Long)");
    		if(proceso.getWeb()){
    		    definicion = entityManager
                        .createNamedQuery(NamedQueriesConstants.BUSCAR_METODO_ASIGNACION_PARAMETRIZADO_VIRTUAL,
                                ParametrizacionMetodoAsignacion.class)
                        .setParameter("proceso", proceso)
                        .getSingleResult();                
    		}else{
    		    definicion = entityManager
                        .createNamedQuery(NamedQueriesConstants.BUSCAR_METODO_ASIGNACION_PARAMETRIZADO,
                                ParametrizacionMetodoAsignacion.class)
                        .setParameter("proceso", proceso)
                        .setParameter("sedeCajaCompensacion", sede)
                        .getSingleResult();
                
    		}			
		} catch (NoResultException nre) {
			logger.error("No existe la definicion del metodo de asignacion", nre);
			logger.debug(
					"Finaliza consultarMetodoAsignacion(ProcesoEnum, Long): No existe datos segun los criterios de búsqueda");
			return null;
		} catch (NonUniqueResultException nure) {
			logger.error("Existe más de una definicion de metodo de asignacion para el proceso - sede", nure);
			logger.debug(
					"Finaliza consultarMetodoAsignacion(ProcesoEnum, Long): Existe más de un resultado para los criterios de búsqueda");
			return null;
		}
	    return definicion;
	}

    /* (non-Javadoc)
     * @see com.asopagos.asignaciones.service.AsignacionSolicitudesService#consultarProcesosReasignacion(com.asopagos.enumeraciones.core.ProcesoEnum, java.lang.String, java.lang.Long, java.lang.String)
     */
    @Override
    public List<InformacionProcesoDTO> consultarProcesosReasignacion(ProcesoEnum proceso, String grupo, Long sede, String usuario) {
    	 List<InformacionProcesoDTO> informacionProcesoList = new ArrayList<>();
    	if(StringUtils.isNotEmpty(usuario)){
    	    List<TareaDTO> tareas = obtenerTareasAsignadas(usuario);
    	    informacionProcesoList = construirListaInformacionProceso(proceso, usuario, tareas);
        }else if(sede != null){
            List<UsuarioDTO> usuarios;
            if(StringUtils.isNotEmpty(grupo)){
                ObtenerMiembrosGrupo obtenerMiembrosGrupo=new ObtenerMiembrosGrupo(grupo, sede.toString(), EstadoUsuarioEnum.ACTIVO);          
                obtenerMiembrosGrupo.execute();            
                usuarios = obtenerMiembrosGrupo.getResult();
            }else{
                ConsultarUsuarioSede consultarUsuarioSede=new ConsultarUsuarioSede(sede, EstadoActivoInactivoEnum.ACTIVO, true);
                consultarUsuarioSede.execute();         
                usuarios = consultarUsuarioSede.getResult();
            }
            logger.info("Cantidad de usuarios:" + usuarios.size());
            if (!usuarios.isEmpty()){
                List<TareaDTO> tareasAsignadas = obtenerTareasAsignadas(usuarios);
                logger.info("Cantidad de tareas:" + tareasAsignadas.size());
                informacionProcesoList = construirListaInformacionProceso(proceso, tareasAsignadas);
            }
        }
    	return informacionProcesoList;
    }

    private List<InformacionProcesoDTO> removerTareasIniciadas(List<InformacionProcesoDTO> informacionProcesoList) {
        List<InformacionProcesoDTO> informacionProcesoListFiltrado = new ArrayList<>();
        informacionProcesoListFiltrado.addAll(informacionProcesoList);
        List<InicioTarea> tareasIniciadas = consultarTareasIniciadas();
        
        if(tareasIniciadas != null && !tareasIniciadas.isEmpty()){
            UsuariosEnSesion usuariosEnSesion = new UsuariosEnSesion();
            usuariosEnSesion.execute();
            List<UsuarioDTO> usuariosSesionActiva = usuariosEnSesion.getResult();
            for(InformacionProcesoDTO proceso : informacionProcesoList){
                for(InicioTarea inicioTarea : tareasIniciadas){
                    String nombreUsuario = inicioTarea.getUsuario();
                    boolean usuarioTieneTareaIniciada = proceso.getNombreUsuario().equals(nombreUsuario)
                            && inicioTarea.getProceso().equals(proceso.getTareaDTO().getIdInstanciaProceso()) 
                            && inicioTarea.getTarea().equals(proceso.getTareaDTO().getId());
                    boolean usuarioEnSesion = validarUsuarioEnSesion(nombreUsuario, usuariosSesionActiva);
                    if(usuarioTieneTareaIniciada && usuarioEnSesion){
                        informacionProcesoListFiltrado.remove(proceso);    
                    }
                }
            }    
        }
        return informacionProcesoListFiltrado;
    }    
    private boolean validarUsuarioEnSesion(String nombreUsuario, List<UsuarioDTO> usuariosSesionActiva) {
        if(usuariosSesionActiva != null && !usuariosSesionActiva.isEmpty()){
            for (UsuarioDTO usuarioDTO : usuariosSesionActiva) {
                if(usuarioDTO.getNombreUsuario().equals(nombreUsuario)){
                    return true;
                }
            }
        }
        return false;
    }

    private List<InicioTarea> consultarTareasIniciadas() {
        return entityManager.createNamedQuery("AsignacionSolicitudes.buscar.tareasIniciadasEnSesion", InicioTarea.class).getResultList();
    }

    @Override
    public void limpiarRegistrosInicioTarea(@Context UserDTO userDTO) {
        String usuario = userDTO.getNombreUsuario();
        entityManager.createNamedQuery("AsignacionSolicitudes.limpiar.tareasIniciadasEnSesion")
            .setParameter("usuario", usuario).executeUpdate();
    }

    /**
     * Metodo encargado de obtener los resultados de la reasignacion de de las las solicitudes en los distintos procesos
     * @param proceso
     * @param sede
     *        caja de compensacion
     * @param usuario
     * @param procesos,
     *        informacion de los procesos reasignados
     */
	private List<InformacionProcesoDTO> construirListaInformacionProceso(ProcesoEnum proceso, String usuario, List<TareaDTO> tareas) {	
		List<InformacionProcesoDTO> procesos = new ArrayList<>();
        for (TareaDTO tareaDTO : tareas) {
            if(proceso==null || proceso.getDescripcion().equals(tareaDTO.getNombreProceso())){
                InformacionProcesoDTO dto = new InformacionProcesoDTO();
                dto.setTareaDTO(tareaDTO);
                dto.setNombreUsuario(usuario);        
                procesos.add(dto);
            }
        }
        return procesos;
	}
	
	private List<InformacionProcesoDTO> construirListaInformacionProceso(ProcesoEnum proceso, List<TareaDTO> tareas) { 
        List<InformacionProcesoDTO> procesos = new ArrayList<>();
        for (TareaDTO tareaDTO : tareas) {
            if(proceso==null || proceso.getDescripcion().equals(tareaDTO.getNombreProceso())){
                InformacionProcesoDTO dto = new InformacionProcesoDTO();
                dto.setTareaDTO(tareaDTO);
                dto.setNombreUsuario(tareaDTO.getPropietario());
                procesos.add(dto);
            }
        }
        return procesos;
	}
	
    
    /**
     * Metodo encargado de obtener las tareas adignadas a un determinado usuario
     * @param usuario,
     *        nombre del usuario
     * @return listado de tareas asignadas
     */
    private List<TareaDTO> obtenerTareasAsignadas(String usuario){
    	ObtenerTareasAsignadasUsuario asignadas = new ObtenerTareasAsignadasUsuario(usuario);
		asignadas.execute();
		List<TareaDTO> tareas = asignadas.getResult();
		if(tareas == null){
		    return Collections.emptyList();
		}
        return tareas;
    }
    
    private List<TareaDTO> obtenerTareasAsignadas(List<UsuarioDTO> list){
        List<String> nombresUsuario = new ArrayList<>();
        for(UsuarioDTO usuario : list){
            nombresUsuario.add(usuario.getNombreUsuario());
        }
        ObtenerTareasAsignadasUsuarios asignadas = new ObtenerTareasAsignadasUsuarios(nombresUsuario);
        asignadas.execute();
        List<TareaDTO> tareas = asignadas.getResult();
        if(tareas == null){
            return Collections.emptyList();
        }
        return tareas;
    }
    
    @Override
    public void registrarInicioTarea(InicioTareaDTO inicioTareaDTO, @Context UserDTO userDTO) {
        InicioTarea inicioTarea = new InicioTarea();
        inicioTarea.setProceso(inicioTareaDTO.getProceso());
        inicioTarea.setTarea(inicioTareaDTO.getTarea());
        inicioTarea.setFecha(inicioTareaDTO.getFecha());
        inicioTarea.setUsuario(userDTO.getNombreUsuario());
        entityManager.persist(inicioTarea);
    }

}
