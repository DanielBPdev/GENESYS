package com.asopagos.cartera.composite.service.factories;

import java.util.HashMap;
import java.util.List;

import com.asopagos.cartera.clients.ConsultarParametrizacionDesafiliacion;
import com.asopagos.cartera.composite.constants.ConstanteCartera;
import com.asopagos.dto.modelo.ParametrizacionDesafiliacionModeloDTO;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoParametrizacionGestionCobroEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción: </b> Clase que representa los atributos y acciones
 * específicos relacionados a la asignación de solicitudes de la acción de cobro
 * LC5C. Ver <code>AsignacionSolicitudGestionCobroFactory</code> <br/>
 * <b>Historia de Usuario: </b> HU-164
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class AsignacionSolicitudGestionCobroLC5C extends AsignacionSolicitudGestionCobro {

	/**
	 * Logger
	 */
	private final ILogger logger = LogManager.getLogger(AsignacionSolicitudGestionCobroLC5C.class);

	/**
	 * Constructor
	 * 
	 * @param userDTO
	 *            Información del usuario que realiza la asignación
	 */
	public AsignacionSolicitudGestionCobroLC5C(UserDTO userDTO) {
		super(userDTO);
		this.setAccionCobro(TipoAccionCobroEnum.LC5C);
		this.setTipoParametrizacion(TipoParametrizacionGestionCobroEnum.LINEA_COBRO);
		this.setTipoTransaccionElectronica(TipoTransaccionEnum.ACCION_COBRO_LC5C_ELECTRONICO);
		this.setTipoTransaccionFisica(TipoTransaccionEnum.ACCION_COBRO_LC5C_FISICO);
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
		this.getPlantillasComunicado().add(EtiquetaPlantillaComunicadoEnum.CAR_PER_EXP);
		logger.debug("Finaliza método asignarComunicados");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.cartera.composite.service.factories.
	 * AsignacionSolicitudGestionCobro#consultarParametrizacion(java.util.List)
	 */
	@Override
	public void consultarParametrizacion(List<Object> lista) {
		logger.debug("Inicia método consultarParametrizacion");
		ParametrizacionDesafiliacionModeloDTO parametrizacionDTO = consultarParametrizacionDesafiliacion(TipoLineaCobroEnum.LC5);
		parametrizacion = new HashMap<String, Object>();
		parametrizacion.put(ConstanteCartera.METODO_ENVIO_COMUNICADO, parametrizacionDTO.getMetodoEnvioComunicado());
		logger.debug("Finaliza método consultarParametrizacion");
	}

	/**
	 * Método que invoca el servicio de consulta de parametrización de
	 * desafiliación
	 * 
	 * @param lineaCobro
	 *            Línea de cobro
	 * @return La parametrización de desafiliación para la línea de cobro
	 */
	private ParametrizacionDesafiliacionModeloDTO consultarParametrizacionDesafiliacion(TipoLineaCobroEnum lineaCobro) {
		logger.debug("Inicia método consultarParametrizacion");
		ConsultarParametrizacionDesafiliacion service = new ConsultarParametrizacionDesafiliacion(lineaCobro);
		service.execute();
		logger.debug("Finaliza método consultarParametrizacion");
		return service.getResult();
	}
}
