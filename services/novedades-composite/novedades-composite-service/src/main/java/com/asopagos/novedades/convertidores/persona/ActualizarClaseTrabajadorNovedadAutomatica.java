package com.asopagos.novedades.convertidores.persona;

import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ActualizarAfiliadosExVeteranos;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;
import java.util.List;
import java.util.ArrayList;
public class ActualizarClaseTrabajadorNovedadAutomatica implements NovedadCore{
    private final ILogger logger = LogManager.getLogger(ActualizarCategoriaBeneficiarioEdadX.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(
	 * com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedad) {
		DatosPersonaNovedadDTO datosPersona = (DatosPersonaNovedadDTO) solicitudNovedad.getDatosPersona();

		List<Long> idsRolAfiliado = new ArrayList<>();
		idsRolAfiliado.add(datosPersona.getIdRolAfiliado()); // Agrega la ID existente a la lista

		// Puedes agregar más IDs a la lista si es necesario

		ActualizarAfiliadosExVeteranos actualizarExVeteranos = new ActualizarAfiliadosExVeteranos(idsRolAfiliado);
		return actualizarExVeteranos;
	}


    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }
}
