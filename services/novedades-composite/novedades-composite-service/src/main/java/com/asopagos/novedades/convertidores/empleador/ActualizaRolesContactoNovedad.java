/**
 * 
 */
package com.asopagos.novedades.convertidores.empleador;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.RolContactoEmpleador;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoRolContactoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.clients.EjecutarCambiosRolesContacto;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.DatosNovedadRolContactoDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para actualizar Roles de Contacto
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 *
 */
public class ActualizaRolesContactoNovedad implements NovedadCore{

	private final ILogger logger = LogManager.getLogger(ActualizaRolesContactoNovedad.class);
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.debug("Inicio de método ActualizarEmpleadorNovedad.transformarServicio");
		
		/*se transforma a un objeto de datos del empleador*/
		DatosEmpleadorNovedadDTO datosEmpleador = (DatosEmpleadorNovedadDTO) solicitudNovedadDTO.getDatosEmpleador();
		TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();
		DatosNovedadRolContactoDTO datosNovedadRolContactoDTO = new DatosNovedadRolContactoDTO();
		datosNovedadRolContactoDTO.setIdEmpleador(datosEmpleador.getIdEmpleador());
		
		if(novedad.equals(TipoTransaccionEnum.CAMBIOS_ROLES_CONTACTO_PRESENCIAL) ||
				novedad.equals(TipoTransaccionEnum.CAMBIOS_ROLES_CONTACTO_WEB)) {
			//Se asignan los datos del Rol Contacto Afiliación.
			datosNovedadRolContactoDTO.setRolAfiliacion(this.asignarDatosRolContactoAfiliacion(datosEmpleador));
			datosNovedadRolContactoDTO.setIdRolAfiliacion(datosEmpleador.getIdRolAfiliacion());
			
			//Se asignan los datos del Rol Contacto Aportes.
			datosNovedadRolContactoDTO.setRolAportes(this.asignarDatosRolContactoAportes(datosEmpleador));
			datosNovedadRolContactoDTO.setIdRolAportes(datosEmpleador.getIdRolAportes());
			
			//Se asignan los datos del Rol Contacto Subsidio.
			datosNovedadRolContactoDTO.setRolSubsidio(this.asignarDatosRolContactoSubsidio(datosEmpleador));
			datosNovedadRolContactoDTO.setIdRolSubsidio(datosEmpleador.getIdRolSubsidio());
		}
		
		EjecutarCambiosRolesContacto ejecutarCambiosRolesContacto = new EjecutarCambiosRolesContacto(datosNovedadRolContactoDTO);
		return ejecutarCambiosRolesContacto;
	}

	/**
	 * Método que se encarga de asignar los datos del Rol Contacto Afiliación
	 * @param datosEmpleador
	 * @return Rol Contacto Afiliación.
	 */
	private RolContactoEmpleador asignarDatosRolContactoAfiliacion(DatosEmpleadorNovedadDTO datosEmpleador) {
		logger.debug("Inicio de método asignarDatosRolContactoAfiliacion(DatosEmpleadorNovedadDTO datosEmpleador)");
		RolContactoEmpleador rolAfiliacion = new RolContactoEmpleador();
		Persona personaRolAfiliacion = new Persona();
		Ubicacion ubicacionRolAfiliacion = new Ubicacion();
		personaRolAfiliacion.setTipoIdentificacion(datosEmpleador.getTipoIdentificacionRolAfiliacion());
		personaRolAfiliacion.setNumeroIdentificacion(datosEmpleador.getNumeroIdentificacionRolAfiliacion());
		personaRolAfiliacion.setPrimerNombre(datosEmpleador.getPrimerNombreRolAfiliacion());
		personaRolAfiliacion.setSegundoNombre(datosEmpleador.getSegundoNombreRolAfiliacion());
		personaRolAfiliacion.setPrimerApellido(datosEmpleador.getPrimerApellidoRolAfiliacion());
		personaRolAfiliacion.setSegundoApellido(datosEmpleador.getSegundoApellidoRolAfiliacion());
		//Se asignan datos de la Ubicación
		ubicacionRolAfiliacion.setEmail(datosEmpleador.getEmailRolAfiliacion());
		if(datosEmpleador.getIndicativoTelFijoRolAfiliacion() != null) {
			ubicacionRolAfiliacion.setIndicativoTelFijo(datosEmpleador.getIndicativoTelFijoRolAfiliacion().toString());
		}
		ubicacionRolAfiliacion.setTelefonoFijo(datosEmpleador.getTelefonoFijoRolAfiliacion());
		ubicacionRolAfiliacion.setTelefonoCelular(datosEmpleador.getTelefonoCelularRolAfiliacion());
		if(datosEmpleador.getSucursalesRolAfiliacion() != null && !datosEmpleador.getSucursalesRolAfiliacion().isEmpty()) {
			List<SucursalEmpresa> sucursalesRolAfiliacion = new ArrayList<>();
			for (Long idSucursalEmpresa : datosEmpleador.getSucursalesRolAfiliacion()) {
				SucursalEmpresa sucursal = new SucursalEmpresa();
				sucursal.setIdSucursalEmpresa(idSucursalEmpresa);
				sucursalesRolAfiliacion.add(sucursal);
			}
			rolAfiliacion.setSucursales(sucursalesRolAfiliacion);
		}
		rolAfiliacion.setUbicacion(ubicacionRolAfiliacion);
		rolAfiliacion.setPersona(personaRolAfiliacion);
		rolAfiliacion.setTipoRolContactoEmpleador(TipoRolContactoEnum.ROL_RESPONSABLE_AFILIACIONES);
		rolAfiliacion.setIdEmpleador(datosEmpleador.getIdEmpleador());
		rolAfiliacion.setIdRolContactoEmpleador(datosEmpleador.getIdRolAfiliacion());
		logger.debug("Fin de método asignarDatosRolContactoAfiliacion(DatosEmpleadorNovedadDTO datosEmpleador)");
		return rolAfiliacion;
	}
	
	/**
	 * Método que se encarga de asignar los datos del Rol Contacto Aportes.
	 * @param datosEmpleador
	 * @return Rol Contacto Aportes
	 */
	private RolContactoEmpleador asignarDatosRolContactoAportes(DatosEmpleadorNovedadDTO datosEmpleador) {
		logger.debug("Inicio de método asignarDatosRolContactoAportes(DatosEmpleadorNovedadDTO datosEmpleador)");
		RolContactoEmpleador rolAportes = new RolContactoEmpleador();
		Persona personaRolAportes = new Persona();
		Ubicacion ubicacionRol = new Ubicacion();
		personaRolAportes.setTipoIdentificacion(datosEmpleador.getTipoIdentificacionRolAportes());
		personaRolAportes.setNumeroIdentificacion(datosEmpleador.getNumeroIdentificacionRolAportes());
		personaRolAportes.setPrimerNombre(datosEmpleador.getPrimerNombreRolAportes());
		personaRolAportes.setSegundoNombre(datosEmpleador.getSegundoNombreRolAportes());
		personaRolAportes.setPrimerApellido(datosEmpleador.getPrimerApellidoRolAportes());
		personaRolAportes.setSegundoApellido(datosEmpleador.getSegundoApellidoRolAportes());
		//Se asignan datos de la Ubicación
		ubicacionRol.setEmail(datosEmpleador.getEmailRolAportes());
		if(datosEmpleador.getIndicativoTelFijoRolAportes() != null) {
			ubicacionRol.setIndicativoTelFijo(datosEmpleador.getIndicativoTelFijoRolAportes().toString());
		}
		ubicacionRol.setTelefonoFijo(datosEmpleador.getTelefonoFijoRolAportes());
		ubicacionRol.setTelefonoCelular(datosEmpleador.getTelefonoCelularRolAportes());
		if(datosEmpleador.getSucursalesRolAfiliacion() != null && !datosEmpleador.getSucursalesRolAportes().isEmpty()) {
			List<SucursalEmpresa> sucursalesRolAportes = new ArrayList<>();
			for (Long idSucursalEmpresa : datosEmpleador.getSucursalesRolAportes()) {
				SucursalEmpresa sucursal = new SucursalEmpresa();
				sucursal.setIdSucursalEmpresa(idSucursalEmpresa);
				sucursalesRolAportes.add(sucursal);
			}
			rolAportes.setSucursales(sucursalesRolAportes);
		}
		rolAportes.setPersona(personaRolAportes);
		rolAportes.setUbicacion(ubicacionRol);
		rolAportes.setTipoRolContactoEmpleador(TipoRolContactoEnum.ROL_RESPONSABLE_APORTES);
		rolAportes.setIdEmpleador(datosEmpleador.getIdEmpleador());
		rolAportes.setIdRolContactoEmpleador(datosEmpleador.getIdRolAportes());
		logger.debug("Fin de método asignarDatosRolContactoAportes(DatosEmpleadorNovedadDTO datosEmpleador)");
		return rolAportes;
	}
	
	/**
	 * Método que se encarga de asignar los datos del Rol Contacto Subsidio.
	 * @param datosEmpleador
	 * @return Rol Contacto Subsidio
	 */
	private RolContactoEmpleador asignarDatosRolContactoSubsidio(DatosEmpleadorNovedadDTO datosEmpleador) {
		logger.debug("Inicio de método asignarDatosRolContactoSubsidio(DatosEmpleadorNovedadDTO datosEmpleador)");
		RolContactoEmpleador rolSubsidio = new RolContactoEmpleador();
		Persona personaRolSubsidio = new Persona();
		Ubicacion ubicacionRol = new Ubicacion();
		personaRolSubsidio.setTipoIdentificacion(datosEmpleador.getTipoIdentificacionRolSubsidio());
		personaRolSubsidio.setNumeroIdentificacion(datosEmpleador.getNumeroIdentificacionRolSubsidio());
		personaRolSubsidio.setPrimerNombre(datosEmpleador.getPrimerNombreRolSubsidio());
		personaRolSubsidio.setSegundoNombre(datosEmpleador.getSegundoNombreRolSubsidio());
		personaRolSubsidio.setPrimerApellido(datosEmpleador.getPrimerApellidoRolSubsidio());
		personaRolSubsidio.setSegundoApellido(datosEmpleador.getSegundoApellidoRolSubsidio());
		//Se asignan datos de la Ubicación
		ubicacionRol.setEmail(datosEmpleador.getEmailRolSubsidio());
		if(datosEmpleador.getIndicativoTelFijoRolSubsidio() != null) {
			ubicacionRol.setIndicativoTelFijo(datosEmpleador.getIndicativoTelFijoRolSubsidio().toString());
		}
		ubicacionRol.setTelefonoFijo(datosEmpleador.getTelefonoFijoRolSubsidio());
		ubicacionRol.setTelefonoCelular(datosEmpleador.getTelefonoCelularRolSubsidio());
		if(datosEmpleador.getSucursalesRolSubsidio() != null && !datosEmpleador.getSucursalesRolSubsidio().isEmpty()) {
			List<SucursalEmpresa> sucursalesRolSubsidio = new ArrayList<>();
			for (Long idSucursalEmpresa : datosEmpleador.getSucursalesRolSubsidio()) {
				SucursalEmpresa sucursal = new SucursalEmpresa();
				sucursal.setIdSucursalEmpresa(idSucursalEmpresa);
				sucursalesRolSubsidio.add(sucursal);
			}
			rolSubsidio.setSucursales(sucursalesRolSubsidio);
		}
		rolSubsidio.setPersona(personaRolSubsidio);
		rolSubsidio.setUbicacion(ubicacionRol);
		rolSubsidio.setTipoRolContactoEmpleador(TipoRolContactoEnum.ROL_RESPONSABLE_SUBSIDIOS);
		rolSubsidio.setIdEmpleador(datosEmpleador.getIdEmpleador());
		rolSubsidio.setIdRolContactoEmpleador(datosEmpleador.getIdRolSubsidio());
		logger.debug("Fin de método asignarDatosRolContactoSubsidio(DatosEmpleadorNovedadDTO datosEmpleador)");
		return rolSubsidio;
	}

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }

}
