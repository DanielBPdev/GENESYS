/**
 * 
 */
package com.asopagos.novedades.convertidores.empleador;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ActualizarRolesAfiliado;
import com.asopagos.afiliados.clients.ConsultarRolesEmpleadorAfiliado;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import com.asopagos.empresas.clients.ConsultarSucursal;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.novedades.dto.SucursalPersonaDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para realizar el traslado de trabajadores por novedad.
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 *
 */
public class TrasladarTrabajadoresNovedad implements NovedadCore{

	private final ILogger logger = LogManager.getLogger(TrasladarTrabajadoresNovedad.class);
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.info("Inicio de método TrasladarTrabajadoresNovedad.transformarServicio");
		
		/*se transforma a un objeto de datos del empleador*/
		DatosEmpleadorNovedadDTO datosEmpleador = (DatosEmpleadorNovedadDTO) solicitudNovedadDTO.getDatosEmpleador();
		TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();		
		
		List<Long> idsPersona = null;
		List<RolAfiliadoModeloDTO> rolesDTO = null;
		switch (novedad) {
		case TRASLADO_TRABAJADORES_ENTRE_SUCURSALES:
			idsPersona = datosEmpleador.getTrabajadoresTraslado();
			rolesDTO = consultarRolesEmpleadorAfiliado(idsPersona, datosEmpleador.getIdEmpleador());
			
                        logger.info("Epr datosEmpleador: " + datosEmpleador);
                        logger.info("Epr datosEmpleador.getSucursalDestinoTraslado(): " + datosEmpleador.getSucursalDestinoTraslado());
                        
			/*Se consulta la sucursal destino*/
			ConsultarSucursal consultarSucursalService = new ConsultarSucursal(datosEmpleador.getSucursalDestinoTraslado());
			consultarSucursalService.execute();
			SucursalEmpresaModeloDTO sucursalEmpresa = consultarSucursalService.getResult();
                        
                        logger.info("Epr sucursalEmpresa: " + sucursalEmpresa);
			
			/*se recorre la lista de los roles afiliados que cambian de sucursal*/
			for (RolAfiliadoModeloDTO rolAfiliadoModeloDTO : rolesDTO) {
				rolAfiliadoModeloDTO.setIdSucursalEmpleador(sucursalEmpresa.getIdSucursalEmpresa());
			}
			break;
		case SUSTITUCION_PATRONAL:
			idsPersona = getListPersonaSusticionPatronal(datosEmpleador.getTrabajadoresSustPatronal());
			rolesDTO = consultarRolesEmpleadorAfiliado(idsPersona, datosEmpleador.getIdEmpleador());

			// Se itera la lista de trabajadores de la susticion patronal
			// para actualizar la sucursal seleccionada para cada uno
			for (SucursalPersonaDTO sucursalPersonaDTO : datosEmpleador.getTrabajadoresSustPatronal()) {
				// Se recorre la lista de roles afiliados
				// para asignar la sucursal que cambia 
				for (RolAfiliadoModeloDTO rolAfiliadoModeloDTO : rolesDTO) {
					if (sucursalPersonaDTO.getIdPersona().equals(rolAfiliadoModeloDTO.getAfiliado().getIdPersona())) {
						rolAfiliadoModeloDTO.setIdSucursalEmpleador(sucursalPersonaDTO.getIdSucursal());
					}
				}
			}
			break;
		default:
			break;
		}
		ActualizarRolesAfiliado actualizarRoles = new ActualizarRolesAfiliado(rolesDTO);
		logger.info("Fin de método TrasladarTrabajadoresNovedad.transformarServicio");
		
		return actualizarRoles;
	}

	/**
	 * Ejecuta la conuslta de roles de afiliado por empleador
	 * @param idsPersona Lista de identificadores de persona
	 * @param idEmpleador Identificador empleador 
	 * @return Lista de roles afiliado
	 */
	private List<RolAfiliadoModeloDTO> consultarRolesEmpleadorAfiliado(List<Long> idsPersona, Long idEmpleador){
		ConsultarRolesEmpleadorAfiliado consultarRolesService = new ConsultarRolesEmpleadorAfiliado(idEmpleador,idsPersona);
		consultarRolesService.execute();
		return (List<RolAfiliadoModeloDTO>) consultarRolesService.getResult();
	}
	
	/**
	 * Se obtiene la lista de identificadores de persona de los trabajadores objeto de la susticion patronal
	 * @param trabajadoresSustPatronal Lista trabajadores objeto de la susticion patronal
	 * @return Lista de identificadores de persona
	 */
	private List<Long> getListPersonaSusticionPatronal(List<SucursalPersonaDTO> trabajadoresSustPatronal){
		List<Long> idPersona = new ArrayList<>();
		for (SucursalPersonaDTO sucursalPersonaDTO : trabajadoresSustPatronal) {
			if (sucursalPersonaDTO.getIdPersona() != null) {
				idPersona.add(sucursalPersonaDTO.getIdPersona());
			}
		}
		return idPersona;
	}

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }
}
