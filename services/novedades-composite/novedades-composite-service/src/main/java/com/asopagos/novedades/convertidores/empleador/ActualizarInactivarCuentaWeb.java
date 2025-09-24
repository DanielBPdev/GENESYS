/**
 * 
 */
package com.asopagos.novedades.convertidores.empleador;

import javax.persistence.EntityManager;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;
import com.asopagos.usuarios.clients.BloquearUsuariosPendienteInactivacion;

/**
 * <b>Descripción:</b> Clase que contiene la lógica para Inactivar Cuenta Web Masivamente.
 * Novedad 30.
 * 
 * <b>Historia de Usuario:</b> HU 496
 * proceso 1.3
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarInactivarCuentaWeb implements NovedadCore{

	private final ILogger logger = LogManager.getLogger(ActualizarInactivarCuentaWeb.class);
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.debug("Inicio de método ActualizarInactivarCuentaWeb.transformarServicio");
		
		//Se realiza el bloqueo de los usuarios (como parametro se manda false para decir que no es de consulta, sino de bloqueo de usuarios)
		BloquearUsuariosPendienteInactivacion usuariosPendienteInactivacion=new BloquearUsuariosPendienteInactivacion(false);
		usuariosPendienteInactivacion.execute();

		logger.debug("Fin de método ActualizarInactivarCuentaWeb.transformarServicio");
		return usuariosPendienteInactivacion;
	}

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }
	
}
