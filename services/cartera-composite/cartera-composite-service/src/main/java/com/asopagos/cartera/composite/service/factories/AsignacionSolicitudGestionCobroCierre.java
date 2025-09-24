package com.asopagos.cartera.composite.service.factories;

import java.util.List;

import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción: </b> Clase que representa los atributos y acciones
 * específicos relacionados a la asignación acciones de cobro intermedias. Ver
 * <code>AsignacionSolicitudGestionCobroFactory</code> <br/>
 * <b>Historia de Usuario: </b> HU-164
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class AsignacionSolicitudGestionCobroCierre extends AsignacionSolicitudGestionCobro {

	/**
	 * Constructor
	 * 
	 * @param accionCobro
	 *            Acción de cobro
	 * @param userDTO
	 *            Información del usuario que realiza la asignación
	 */
	public AsignacionSolicitudGestionCobroCierre(TipoAccionCobroEnum accionCobro, UserDTO userDTO) {
		super(userDTO);
		this.setAccionCobro(accionCobro);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.cartera.composite.service.factories.
	 * AsignacionSolicitudGestionCobro#asignarComunicados()
	 */
	@Override
	public void asignarComunicados() {
	}

	/* (non-Javadoc)
	 * @see com.asopagos.cartera.composite.service.factories.AsignacionSolicitudGestionCobro#consultarParametrizacion(java.util.List)
	 */
	@Override
	public void consultarParametrizacion(List<Object> lista) {	
	}
}
