/**
 * 
 */
package com.asopagos.novedades.convertidores.empleador;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.empleadores.clients.ConsultarEmpleador;
import com.asopagos.empresas.clients.CrearSucursalEmpresa;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para crear La Sucursal por novedad.
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 *
 */
public class CrearSucursalNovedad implements NovedadCore{

	private final ILogger logger = LogManager.getLogger(CrearSucursalNovedad.class);
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.debug("Inicio de método CrearSucursalNovedad.transformarServicio");
		
		/*se transforma a un objeto de datos del empleador*/
		DatosEmpleadorNovedadDTO datosEmpleador = (DatosEmpleadorNovedadDTO) solicitudNovedadDTO.getDatosEmpleador();
		
		TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();
		
		/*Se consulta el empleador*/
		Empleador empleador = new Empleador();
		ConsultarEmpleador consultarEmpledorService = new ConsultarEmpleador(datosEmpleador.getIdEmpleador());
		consultarEmpledorService.execute();
		empleador = (Empleador) consultarEmpledorService.getResult();
		
		List<SucursalEmpresa> sucursalesEmpresa = new ArrayList<>();
		
		if (novedad.equals(TipoTransaccionEnum.AGREGAR_SUCURSAL)){
			SucursalEmpresa sucursalEmpresa = agregarSucursal(empleador.getEmpresa().getIdEmpresa(), datosEmpleador);
			sucursalesEmpresa.add(sucursalEmpresa);
		}
		
		/*Se instancia el servicio de la novedad*/
		CrearSucursalEmpresa crearSucursalEmpresa = new CrearSucursalEmpresa(empleador.getEmpresa().getIdEmpresa(), sucursalesEmpresa);
		return crearSucursalEmpresa;
	}
	
	/**
	 * Método que se encarga de agregar una nueva Sucursal al sistema
	 * @param idEmpresa
	 * @param datosEmpleador
	 */
	private SucursalEmpresa agregarSucursal(Long idEmpresa, DatosEmpleadorNovedadDTO datosEmpleador) {
		
		logger.debug("Inicio de método agregarSucursal(SucursalEmpresa sucursalEmpresa, DatosEmpleadorNovedadDTO datosEmpleador)");
		
		SucursalEmpresa sucursalEmpresa = new SucursalEmpresa();
		sucursalEmpresa.setIdEmpresa(idEmpresa);
		sucursalEmpresa.setCodigo(datosEmpleador.getCodigoSucursal());
		sucursalEmpresa.setNombre(datosEmpleador.getNombreSucursal());
		sucursalEmpresa.setMedioDePagoSubsidioMonetario(datosEmpleador.getMedioDePagoSubsidioMonetarioSucursal());
		sucursalEmpresa.setCodigoCIIU(datosEmpleador.getCodigoCIIUSucursal());
		//Se asigna estado Activo a la Sucursal
		sucursalEmpresa.setEstadoSucursal(EstadoActivoInactivoEnum.ACTIVO);
		
		//Se asignan los datos de la Ubicación asociada a la Sucursal
		Ubicacion ubicacionSucursal = new Ubicacion();
		ubicacionSucursal.setMunicipio(datosEmpleador.getMunicipioSucursal());
		ubicacionSucursal.setDireccionFisica(datosEmpleador.getDireccionFisicaSucursal());
		ubicacionSucursal.setCodigoPostal(datosEmpleador.getCodigoPostalSucursal());
		if(datosEmpleador.getIndicativoTelFijoSucursal() != null){
			ubicacionSucursal.setIndicativoTelFijo(datosEmpleador.getIndicativoTelFijoSucursal().toString());
		}
		ubicacionSucursal.setTelefonoFijo(datosEmpleador.getTelefonoFijoSucursal());
		ubicacionSucursal.setTelefonoCelular(datosEmpleador.getTelefonoCelularSucursal());
		
		sucursalEmpresa.setUbicacion(ubicacionSucursal);
		
		logger.debug("Fin de método agregarSucursal(SucursalEmpresa sucursalEmpresa, DatosEmpleadorNovedadDTO datosEmpleador)");
		return sucursalEmpresa;
	}

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }
	
}
