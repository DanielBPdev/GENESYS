package com.asopagos.cartera.composite.service.factories;

import java.util.List;
import java.util.Map;

import com.asopagos.cartera.composite.constants.ConstanteCartera;
import com.asopagos.enumeraciones.cartera.MetodoAccionCobroEnum;
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
 * 1A. Ver <code>AsignacionSolicitudGestionCobroFactory</code> <br/>
 * <b>Historia de Usuario: </b> HU-164
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class AsignacionSolicitudGestionCobro1A extends AsignacionSolicitudGestionCobro {

	/**
	 * Logger
	 */
	private final ILogger logger = LogManager.getLogger(AsignacionSolicitudGestionCobro1A.class);

	/**
	 * Constructor
	 * 
	 * @param userDTO
	 *            Información del usuario que realiza la asignación
	 */
	public AsignacionSolicitudGestionCobro1A(UserDTO userDTO) {
		super(userDTO);
		this.setAccionCobro(TipoAccionCobroEnum.A1);
		this.setTipoParametrizacion(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_A);
		this.setTipoTransaccionElectronica(TipoTransaccionEnum.ACCION_COBRO_1A_ELECTRONICO);
		this.setTipoTransaccionFisica(TipoTransaccionEnum.ACCION_COBRO_1A_FISICO);
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

		if (Boolean.valueOf(this.getParametrizacion().get(ConstanteCartera.SUSPENSION_AUTOMATICA).toString())) {
			this.getPlantillasComunicado().add(EtiquetaPlantillaComunicadoEnum.SUS_NTF_NO_PAG);
		} else {
			this.getPlantillasComunicado().add(EtiquetaPlantillaComunicadoEnum.NTF_NO_REC_APO);
		}

		logger.debug("Finaliza método asignarComunicados");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.cartera.composite.service.factories.
	 * AsignacionSolicitudGestionCobro#consultarParametrizacion()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void consultarParametrizacion(List<Object> lista) {
		logger.debug("Inicia método consultarParametrizacion");

		for (Object registro : lista) {
			parametrizacion = (Map<String, Object>) registro;

			if (parametrizacion.get(ConstanteCartera.METODO).toString().equals(MetodoAccionCobroEnum.METODO_1.name())) {
				break;
			}
		}

		logger.debug("Finaliza método consultarParametrizacion");
	}
}
