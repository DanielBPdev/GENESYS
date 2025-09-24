package com.asopagos.fovis.composite.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.dto.fovis.OferenteDTO;
import com.asopagos.dto.modelo.AhorroPrevioModeloDTO;
import com.asopagos.dto.modelo.ProyectoSolucionViviendaModeloDTO;
import com.asopagos.dto.modelo.RecursoComplementarioModeloDTO;

/**
 * <b>Descripción: </b> DTO que representa los datos de vivienda y cierre
 * financiero en una solicitud FOVIS <br/>
 * <b>Historia de Usuario: </b> HU-023
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class InformacionViviendaDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -2607038899546134414L;

	/**
	 * Lista de chequeo de requisitos documentales
	 */
//	private List<ListaChequeoDocumentoDTO> listaChequeo;

	/**
	 * Información del oferente
	 */
	private OferenteDTO oferente;

	/**
	 * Lista de proyectos de vivienda asociados al oferente
	 */
	private List<ProyectoSolucionViviendaModeloDTO> listaProyectos;

	/**
	 * Datos de ahorro previo
	 */
	private AhorroPrevioModeloDTO ahorroPrevio;

	/**
	 * Información de recursos complementarios
	 */
	private RecursoComplementarioModeloDTO recursoComplementario;
}
