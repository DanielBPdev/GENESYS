package com.asopagos.cartera.composite.service.factories;

import java.util.List;
import java.util.Map;

import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoParametrizacionGestionCobroEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripcion:</b> Clase que representa los atributos y acciones específicos
 * relacionados a la asignación de solicitudes de la acción de cobro 2D <br/>
 * <b>Módulo:</b> Asopagos - HU 187<br/>
 *
 * @author <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */
public class AsignacionSolicitudGestionCobro2D extends AsignacionSolicitudGestionCobro {

	/**
	 * Logger
	 */
	private final ILogger logger = LogManager.getLogger(AsignacionSolicitudGestionCobro2D.class);

	/**
	 * Constructor
	 * 
	 * @param userDTO
	 *            Información del usuario que realiza la asignación
	 */
	public AsignacionSolicitudGestionCobro2D(UserDTO userDTO) {
		super(userDTO);
		this.setAccionCobro(TipoAccionCobroEnum.D2);
		this.setTipoParametrizacion(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2D);
		this.setTipoTransaccionElectronica(TipoTransaccionEnum.ACCION_COBRO_2D_ELECTRONICO);
		this.setTipoTransaccionFisica(TipoTransaccionEnum.ACCION_COBRO_2D_FISICO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.cartera.composite.service.factories.
	 * AsignacionSolicitudGestionCobro#asignarComunicados()
	 */
	@Override
	public void asignarComunicados() {
		logger.debug("Inicia método asignarComunicados");
		this.getPlantillasComunicado().add(EtiquetaPlantillaComunicadoEnum.NTF_AVI);
		logger.debug("Finaliza método asignarComunicados");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.cartera.composite.service.factories.
	 * AsignacionSolicitudGestionCobro#consultarParametrizacion(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void consultarParametrizacion(List<Object> lista) {
		logger.debug("Inicia método consultarParametrizacion");
		parametrizacion = (Map<String, Object>) lista.get(0);
		logger.debug("Finaliza método consultarParametrizacion");
	}
}
