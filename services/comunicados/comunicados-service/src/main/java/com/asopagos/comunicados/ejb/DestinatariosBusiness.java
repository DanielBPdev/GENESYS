package com.asopagos.comunicados.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import javax.ws.rs.PathParam;
import com.asopagos.comunicados.constants.NamedQueriesConstants;
import com.asopagos.comunicados.dto.EtiquetaDestinatarioDTO;
import com.asopagos.comunicados.dto.RolComunicadoDTO;
import com.asopagos.comunicados.service.DestinatariosService;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.notificaciones.RolContactoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

/**
 * <b>Descripcion:</b> EJB que implementa los métodos de negocio relacionados
 * con la gestión comunicados en la afiliacion de empleadores <br/>
 * <b>Módulo:</b> Asopagos - HU-331 <br/>
 *
 * @author <a href="mailto:jerodriguez@heinsohn.com.co"> jerodriguez</a>
 */
@Stateless
public class DestinatariosBusiness implements DestinatariosService {

	@PersistenceContext(unitName = "comunicados_PU")
	private EntityManager entityManager;

	/**
	 * Referencia al logger
	 */
	private final ILogger logger = LogManager.getLogger(DestinatariosBusiness.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.comunicados.service.DestinatariosService#
	 * buscarComunicadosPorProceso(com.asopagos.enumeraciones.core.ProcesoEnum)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<RolComunicadoDTO> buscarComunicadosPorProceso(@NotNull @PathParam("proceso") ProcesoEnum proceso) {
		logger.debug("Inicia buscarComunicadosPorProceso(ProcesoEnum)");
		List<EtiquetaDestinatarioDTO> lstEtiquetaDestinatarios = (List<EtiquetaDestinatarioDTO>) entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_COMUNICADO_POR_PROCESO)
				.setParameter("proceso", proceso.toString()).getResultList();
		List<RolComunicadoDTO> lstRolesComunicado = new ArrayList<RolComunicadoDTO>();
		Map<EtiquetaPlantillaComunicadoEnum, List<RolContactoEnum>> lstComunicadosRol = new HashMap<>();
		if (!lstEtiquetaDestinatarios.isEmpty()) {
			for (EtiquetaDestinatarioDTO etiquetaDestinatarioDTO : lstEtiquetaDestinatarios) {
				lstRolesComunicado = construirRolComunicadoDTO(etiquetaDestinatarioDTO.getEtiqueta(),
						etiquetaDestinatarioDTO.getRolContacto(), lstRolesComunicado);
			}
			logger.debug("Finaliza buscarComunicadosPorProceso(ProcesoEnum)");
			return lstRolesComunicado;
		} else {
			logger.debug("Finaliza buscarComunicadosPorProceso(ProcesoEnum)");
			return lstRolesComunicado;
		}
	}

	/**
	 * Método encargado de agregar rol contacto a comunicado
	 * 
	 * @param etiquetaComunicado,
	 *            etiqueta del comunicado al que pertenece
	 * @param rolContactoEnum,
	 *            Rol Contacto enum a agregar
	 * @return retorna el rolComunicadoDTO construido
	 */
	public List<RolComunicadoDTO> construirRolComunicadoDTO(EtiquetaPlantillaComunicadoEnum etiquetaComunicado,
			RolContactoEnum rolContactoEnum, List<RolComunicadoDTO> lstRolesComunicado) {
		RolComunicadoDTO rolComunicado = null;
		List<RolContactoEnum> lstRolesContacto = new ArrayList<RolContactoEnum>();
		for (int i = 0; i < lstRolesComunicado.size(); i++) {
			if (lstRolesComunicado.get(i).getEtiquetaComunicado().equals(etiquetaComunicado)) {
				rolComunicado = lstRolesComunicado.get(i);
			}
		}
		if (rolComunicado != null) {
			rolComunicado.getLstRolesContacto().add(rolContactoEnum);
		} else {
			rolComunicado = new RolComunicadoDTO();
			rolComunicado.setEtiquetaComunicado(etiquetaComunicado);
			lstRolesContacto.add(rolContactoEnum);
			rolComunicado.setLstRolesContacto(lstRolesContacto);
		}
		lstRolesComunicado.add(rolComunicado);
		return lstRolesComunicado;
	}
}
