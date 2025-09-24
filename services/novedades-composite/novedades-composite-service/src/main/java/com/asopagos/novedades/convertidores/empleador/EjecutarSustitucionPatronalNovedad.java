/**
 * 
 */
package com.asopagos.novedades.convertidores.empleador;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ConsultarRolesEmpleadorAfiliado;
import com.asopagos.afiliados.clients.ValidarEmpleadorCeroTrabajadores;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.empleadores.clients.ConsultarEmpleadorTipoNumero;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.clients.EjecutarSustitucionTrabajadores;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadCascada;
import com.asopagos.novedades.composite.dto.EmpleadorAfiliadosDTO;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.DatosNovedadCascadaDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.novedades.dto.SucursalPersonaDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;

/**
 * Clase que contiene la lógica para realizar la sustitucion patronal de un empleador.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class EjecutarSustitucionPatronalNovedad implements NovedadCore{

	
	/**
	 * Logger.
	 */
	private final ILogger logger = LogManager.getLogger(EjecutarSustitucionPatronalNovedad.class);
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.info("Inicio de mÃ©todo EjecutarSustitucionPatronalNovedad.transformarServicio");
		
		
		/*se transforma a un objeto de datos del empleador*/
		DatosEmpleadorNovedadDTO datosEmpleador = (DatosEmpleadorNovedadDTO) solicitudNovedadDTO.getDatosEmpleador();
		
		/*consultar empleador*/
		ConsultarEmpleadorTipoNumero consultarEmpleadorService = new ConsultarEmpleadorTipoNumero(
				datosEmpleador.getNumeroIdentificacion(),datosEmpleador.getTipoIdentificacion());
		consultarEmpleadorService.execute();
		EmpleadorModeloDTO empleadorDTO = consultarEmpleadorService.getResult();
		
		List<Long> idPersonasAfiliado = new ArrayList<>();
		
		for (SucursalPersonaDTO sucursalPersona : datosEmpleador.getTrabajadoresSustPatronal()) {
			if (sucursalPersona.getIdPersona() != null) {
				idPersonasAfiliado.add(sucursalPersona.getIdPersona());
			}
		}
		/*se consulta los roles afiliados para  el empleador objeto de novedad y los afiliados seleccionado previamente*/
		ConsultarRolesEmpleadorAfiliado consultarRolesService = new ConsultarRolesEmpleadorAfiliado(datosEmpleador.getIdEmpleador(),idPersonasAfiliado);
		consultarRolesService.execute();
		List<RolAfiliadoModeloDTO> rolesDTO = (List<RolAfiliadoModeloDTO>) consultarRolesService.getResult();
		
        Long fechaIngreso = Calendar.getInstance().getTimeInMillis();
        if(solicitudNovedadDTO.getFechaInicioAfiliacion() != null){
            fechaIngreso = solicitudNovedadDTO.getFechaInicioAfiliacion();
        }else if(solicitudNovedadDTO.getDatosEmpleador() != null && solicitudNovedadDTO.getDatosEmpleador().getFechaInicioAfiliacion() != null){
            fechaIngreso = solicitudNovedadDTO.getDatosEmpleador().getFechaInicioAfiliacion();
        }
        logger.info("Inicio de metodo EjecutarSustitucionPatronalNovedad fechaIngreso 1.1: "+fechaIngreso);
        //logger.info("Inicio de mÃ©todo EjecutarSustitucionPatronalNovedad solicitudNovedadDTO.getFechaInicioAfiliacion(): "+solicitudNovedadDTO.getFechaInicioAfiliacion());
        
//        printJsonMessage(solicitudNovedadDTO, "mcuellar: EjecutarSustitucionPatronalNovedad antes solicitudNovedadDTO");
                
		// Se itera la lista de trabajadores de la susticion patronal
		// para actualizar la sucursal seleccionada para cada uno
		for (SucursalPersonaDTO sucursalPersonaDTO : datosEmpleador.getTrabajadoresSustPatronal()) {
			// Se recorre la lista de roles afiliados
			// para asignar la sucursal que cambia 
			// TODO aca validan los roles afiliados
			for (RolAfiliadoModeloDTO rolAfiliadoModeloDTO : rolesDTO) {
				rolAfiliadoModeloDTO.setIdEmpleador(datosEmpleador.getIdEmpleadorDestinoSustPatronal());
				rolAfiliadoModeloDTO.setSustitucionPatronal(Boolean.TRUE);
				rolAfiliadoModeloDTO.setFechaAfiliacion(Calendar.getInstance().getTimeInMillis());
                                rolAfiliadoModeloDTO.setFechaIngreso(fechaIngreso);
				rolAfiliadoModeloDTO.setCanalReingreso(CanalRecepcionEnum.NOVEDAD_SUS_PATR);
				if (sucursalPersonaDTO.getIdPersona().equals(rolAfiliadoModeloDTO.getAfiliado().getIdPersona())) {
					rolAfiliadoModeloDTO.setIdSucursalEmpleador(sucursalPersonaDTO.getIdSucursal());
				}
				logger.info("vamos a validar la clase del rolafiliado"+rolAfiliadoModeloDTO.getClaseTrabajador());
				if(rolAfiliadoModeloDTO.getClaseTrabajador().equals(ClaseTrabajadorEnum.VETERANO_FUERZA_PUBLICA)){
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(fechaIngreso);
					cal.add(Calendar.YEAR, 2);
					rolAfiliadoModeloDTO.setFechaInicioCondicionVet(new Date(fechaIngreso));
					rolAfiliadoModeloDTO.setFechaFinCondicionVet(cal.getTime());
				}
			}
		}
		/*se invoca el servicio para validar si el trabajador quedarÃ­a con cero trabajadores activos.*/
		ValidarEmpleadorCeroTrabajadores validarCeroTrabajadoresService = new ValidarEmpleadorCeroTrabajadores(
				empleadorDTO.getIdEmpleador());
		validarCeroTrabajadoresService.execute();
		Boolean ceroTrabajadores = validarCeroTrabajadoresService.getResult();
		if(ceroTrabajadores){
			/*si despues de sustituir se queda con cero trabajadores se inactvia el empleador*/
			empleadorDTO.setMotivoDesafiliacion(MotivoDesafiliacionEnum.CERO_TRABAJADORES_NOVEDAD_INTERNA);
			empleadorDTO.setEstadoEmpleador(EstadoEmpleadorEnum.INACTIVO);
			Calendar fechaActual = Calendar.getInstance();
                        //GLPI 67296 cc sustitucion patronal
                        if(solicitudNovedadDTO.getFechaFinLaboresSucursalOrigenTraslado() != null){
                            empleadorDTO.setFechaRetiroTotalTrabajadores(solicitudNovedadDTO.getFechaFinLaboresSucursalOrigenTraslado());
                        }else{
                            empleadorDTO.setFechaRetiroTotalTrabajadores(fechaActual.getTimeInMillis());
                        }			
			Short cantidadIngresoCeroTrab = 0;
			if (empleadorDTO.getCantIngresoBandejaCeroTrabajadores() != null) {
				cantidadIngresoCeroTrab = empleadorDTO.getCantIngresoBandejaCeroTrabajadores();
			}
			//Se aumenta contador de cantidad de ingresos a bandeja de Cero trabajadores.
			empleadorDTO.setCantIngresoBandejaCeroTrabajadores(cantidadIngresoCeroTrab++);
		}
		EmpleadorAfiliadosDTO empleadorAfiliadosDTO = new EmpleadorAfiliadosDTO();
		empleadorAfiliadosDTO.setEmpleador(empleadorDTO);
		empleadorAfiliadosDTO.setRoles(rolesDTO);
		empleadorAfiliadosDTO.setInactivarCuentaWeb(datosEmpleador.getRequiereInactivacionCuentaWeb());
		
        // Se realiza la ejecuciÃ³n de retiro de trabajadores en casacada
        DatosNovedadCascadaDTO datosNovedadConsecutivaDTO = new DatosNovedadCascadaDTO();
        //GLPI 67296 cc sustitucion patronal
        if(solicitudNovedadDTO.getFechaFinLaboresSucursalOrigenTraslado() != null){
            datosNovedadConsecutivaDTO.setFechaRetiro(solicitudNovedadDTO.getFechaFinLaboresSucursalOrigenTraslado());
        }else if(solicitudNovedadDTO.getDatosEmpleador() != null && 
                solicitudNovedadDTO.getDatosEmpleador().getFechaFinLaboresOrigenSustPatronal() != null){
            datosNovedadConsecutivaDTO.setFechaRetiro(solicitudNovedadDTO.getDatosEmpleador().getFechaFinLaboresOrigenSustPatronal());
        }else{
            datosNovedadConsecutivaDTO.setFechaRetiro(new Date().getTime());
        }
        datosNovedadConsecutivaDTO.setListaRoles(rolesDTO);
		for (RolAfiliadoModeloDTO rolAfiliadoModeloDTO2 : rolesDTO) {
			logger.info("valor fecha inicio condicion veterania pre" +rolAfiliadoModeloDTO2.getFechaInicioCondicionVet());
			logger.info("valor fecha fin condicion veterania pre" +rolAfiliadoModeloDTO2.getFechaFinCondicionVet());
		}
        datosNovedadConsecutivaDTO.setMotivoDesafiliacionAfiliado(MotivoDesafiliacionAfiliadoEnum.SUSTITUCION_PATRONAL);
        datosNovedadConsecutivaDTO.setNumeroRadicadoOriginal(solicitudNovedadDTO.getNumeroRadicacion());
        datosNovedadConsecutivaDTO.setTipoTransaccionOriginal(solicitudNovedadDTO.getNovedadDTO().getNovedad());
        RadicarSolicitudNovedadCascada novedadCascada = new RadicarSolicitudNovedadCascada(datosNovedadConsecutivaDTO);
        novedadCascada.execute();
		logger.info("roles actualizados sustitucion patronal"+ datosNovedadConsecutivaDTO.getRolAfiliadoDTO());

		
		logger.debug("Fin de mÃ©todo EjecutarSustitucionPatronalNovedad.transformarServicio");
		EjecutarSustitucionTrabajadores ejecutarSustitucionService = new EjecutarSustitucionTrabajadores(empleadorAfiliadosDTO);
		return ejecutarSustitucionService ;
	}

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }
    
    private void printJsonMessage(Object object,String message){
        try{
            //Creating the ObjectMapper object
            ObjectMapper mapper = new ObjectMapper();
            //Converting the Object to JSONString
            String jsonString = mapper.writeValueAsString(object);
            logger.info(message + jsonString);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
