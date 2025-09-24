package com.asopagos.asignaciones.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import com.asopagos.entidades.ccf.general.ParametrizacionMetodoAsignacion;
import com.asopagos.enumeraciones.usuarios.EstadoUsuarioEnum;
import com.asopagos.usuarios.clients.ObtenerMiembrosGrupo;
import com.asopagos.usuarios.dto.UsuarioDTO;


/**
 * <b>Descripción:</b> Representa el metodo de asignacion consecutivo por turnos
 * <b>Historia de Usuario:</b> HU-TRA-084 Administrar asignación de solicitudes
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@Stateless
public class AsignacionConsecutivoTurnos implements MetodoAsignacion {

    @Override
	public String ejecutar(ParametrizacionMetodoAsignacion parametrizacionMetodoAsignacion, Long sede, EntityManager entityManager)
			throws Exception {
		String funcionario = parametrizacionMetodoAsignacion.getGrupo();
        ObtenerMiembrosGrupo miembrosGrupo = new ObtenerMiembrosGrupo(funcionario, parametrizacionMetodoAsignacion.getSedeCajaDestino() != null ? parametrizacionMetodoAsignacion.getSedeCajaDestino().toString() : null,
                EstadoUsuarioEnum.ACTIVO);
		miembrosGrupo.execute();
		List<UsuarioDTO> usuarios = miembrosGrupo.getResult();

		Collections.sort(usuarios, new Comparator<UsuarioDTO>() {
			@Override
			public int compare(UsuarioDTO u1, UsuarioDTO u2) {
				return u1.getNombreUsuario().toUpperCase().compareTo(u2.getNombreUsuario().toUpperCase());
			}
		});

		if (parametrizacionMetodoAsignacion.getUsuario() == null) {
		    UsuarioDTO usuario = usuarios.iterator().next();
			parametrizacionMetodoAsignacion.setUsuario(usuario.getNombreUsuario());
			entityManager.merge(parametrizacionMetodoAsignacion);
			return usuario.getNombreUsuario();
		}

		for (int i = 0; i < usuarios.size(); i++) {
			if (usuarios.get(i).getNombreUsuario().equals(parametrizacionMetodoAsignacion.getUsuario())) {
				if (i != (usuarios.size() - 1)) {
					parametrizacionMetodoAsignacion.setUsuario(usuarios.get(i + 1).getNombreUsuario());
					entityManager.merge(parametrizacionMetodoAsignacion);
					return usuarios.get(i + 1).getNombreUsuario();
				} else {
					parametrizacionMetodoAsignacion.setUsuario(usuarios.get(0).getNombreUsuario());
					entityManager.merge(parametrizacionMetodoAsignacion);
					return usuarios.get(0).getNombreUsuario();
				}
			}
		}
		
		if (usuarios != null && !usuarios.isEmpty()) {
		    parametrizacionMetodoAsignacion.setUsuario(usuarios.get(0).getNombreUsuario());
		    entityManager.merge(parametrizacionMetodoAsignacion);
		    return usuarios.get(0).getNombreUsuario();
        }

        return null;
	}
}
