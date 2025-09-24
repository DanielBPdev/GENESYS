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
 * <b>Descripción: </b> Clase que representa los atributos y acciones
 * específicos relacionados a la asignación de solicitudes de la acción de cobro
 * 1F. Ver <code>AsignacionSolicitudGestionCobroFactory</code> <br/>
 * <b>Historia de Usuario: </b> HU-164
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class AsignacionSolicitudGestionCobro1F extends AsignacionSolicitudGestionCobro {

	/**
	 * Logger
	 */
	private final ILogger logger = LogManager.getLogger(AsignacionSolicitudGestionCobro1F.class);

	/**
	 * Constructor
	 * 
	 * @param userDTO
	 *            Información del usuario que realiza la asignación
	 */
	public AsignacionSolicitudGestionCobro1F(UserDTO userDTO) {
		super(userDTO);
		this.setAccionCobro(TipoAccionCobroEnum.F1);
		this.setTipoParametrizacion(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_1F);
		this.setTipoTransaccionElectronica(TipoTransaccionEnum.ACCION_COBRO_1F_ELECTRONICO);
		this.setTipoTransaccionFisica(TipoTransaccionEnum.ACCION_COBRO_1F_FISICO);
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
		this.getPlantillasComunicado().add(EtiquetaPlantillaComunicadoEnum.CAR_EMP_EXP);
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
