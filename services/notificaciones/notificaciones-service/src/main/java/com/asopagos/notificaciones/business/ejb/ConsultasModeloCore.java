package com.asopagos.notificaciones.business.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import com.asopagos.entidades.ccf.comunicados.VariableComunicado;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.business.interfaces.IConsultasModeloCore;
import com.asopagos.notificaciones.constants.NamedQueriesConstants;
import com.asopagos.entidades.transversal.core.ModuloPlantillaComunicado;
import javax.persistence.NoResultException;

/**
 * Implementación a la interfaz de contrato de servicios de consulta del model
 * core
 * 
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 */
@Stateless
public class ConsultasModeloCore implements IConsultasModeloCore {

	/**
	 * Referencia al logger
	 */
	private static final ILogger logger = LogManager.getLogger(ConsultasModeloCore.class);

	/**
	 * Referencia a la unidad de persistencia del servicio
	 */
	@PersistenceContext(unitName = "notificaciones_PU")
	private EntityManager entityManager;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.notificaciones.business.interfaces.IConsultasModeloCore#consultarPlantillaComunicado(com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PlantillaComunicado consultarPlantillaComunicado(EtiquetaPlantillaComunicadoEnum etiqueta) {
		logger.debug("Inicia consultarComunicado(EtiquetaPlantillaComunicadoEnum)");
		PlantillaComunicado plantilla = (PlantillaComunicado) entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANTILLA_COMUNICADO_POR_ETIQUETA)
				.setParameter("etiqueta", etiqueta).getSingleResult();
		logger.debug("Finaliza consultarComunicado(EtiquetaPlantillaComunicadoEnum)");
		return plantilla;
	}


	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.notificaciones.business.interfaces.IConsultasModeloCore#consultarPlantillaComunicado(com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ModuloPlantillaComunicado obtenerModuloDePlantilla(Long idPlantillaComunicado) {
		logger.debug("Inicia obtenerModuloDePlantilla(idPlantillaComunicado)");
		try {
			ModuloPlantillaComunicado plantilla = (ModuloPlantillaComunicado) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANTILLA_COMUNICADO_MODULO)
					.setParameter("idPlantillaComunicado", idPlantillaComunicado).getSingleResult();
			logger.debug("Finaliza obtenerModuloDePlantilla(idPlantillaComunicado)");
			return plantilla;
		} catch (NoResultException e) {
			logger.warn("No se encontró el módulo para la plantilla con ID: " + idPlantillaComunicado);
			ModuloPlantillaComunicado moduloPorDefecto = new ModuloPlantillaComunicado();
			moduloPorDefecto.setModulo("DEFAULT");
			return moduloPorDefecto;
		} catch (Exception e) {
			logger.error("Error al obtener el módulo de la plantilla: " + idPlantillaComunicado, e);
			throw new RuntimeException("Error al consultar el módulo de la plantilla", e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.notificaciones.business.interfaces.IConsultasModeloCore#consultarVariableComunicado(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<VariableComunicado> consultarVariableComunicado(Long idPlantillaComunicado) {
		logger.debug("Inicia consultarVariableComunicado(Long)");
		List<VariableComunicado> variables = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_VARIABLE_COMUNICADO_POR_ETIQUETA,
						VariableComunicado.class)
				.setParameter("idPlantillaComunicado", idPlantillaComunicado).getResultList();
		logger.debug("Finaliza consultarVariableComunicado(Long)");
		return variables;
	}

}
