package com.asopagos.cartera.composite.service.factories;

import java.util.List;
import java.util.Map;

import com.asopagos.cartera.composite.constants.ConstanteCartera;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoParametrizacionGestionCobroEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripcion:</b> Clase que representa los atributos y acciones específicos
 * relacionados a la asignación de solicitudes de la acción de cobro 2C <br/>
 * <b>Módulo:</b> Asopagos - HU 186<br/>
 *
 * @author <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */
public class AsignacionSolicitudGestionCobro2C extends AsignacionSolicitudGestionCobro {

	/**
	 * Logger
	 */
	private final ILogger logger = LogManager.getLogger(AsignacionSolicitudGestionCobro2C.class);

	/**
	 * Constructor
	 * 
	 * @param userDTO
	 *            Información del usuario que realiza la asignación
	 */
	public AsignacionSolicitudGestionCobro2C(UserDTO userDTO) {
		super(userDTO);
		this.setAccionCobro(TipoAccionCobroEnum.C2);
		this.setTipoParametrizacion(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2C);
		this.setTipoTransaccionElectronica(TipoTransaccionEnum.ACCION_COBRO_2C_ELECTRONICO);
		this.setTipoTransaccionFisica(TipoTransaccionEnum.ACCION_COBRO_2C_FISICO);
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
		this.getPlantillasComunicado().add(EtiquetaPlantillaComunicadoEnum.CIT_NTF_PER);
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
