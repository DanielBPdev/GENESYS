package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.asopagos.dto.modelo.CarteraModeloDTO;
import com.asopagos.dto.modelo.SolicitudFiscalizacionModeloDTO;
import com.asopagos.dto.modelo.SolicitudPreventivaModeloDTO;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción: </b> Clase que representa la información de un aportante en
 * gestión de cartera, para la vista 360 <br/>
 * <b>Historia de Usuario: </b> HU-488, HU-489, TRA-001, TRA-002, TRA-003
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class GestionCarteraDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1221178070782829389L;

	/**
	 * Tipo de identificación del aportante
	 */
	private TipoIdentificacionEnum tipoIdentificacion;

	/**
	 * Número de identificación del aportante
	 */
	private String numeroIdentificacion;

	/**
	 * Dígito de verificación, cuando tipoIdentificacion=NIT
	 */
	private Short digitoVerificacion;

	/**
	 * Tipo de solicitante
	 */
	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;

	/**
	 * Estado de cartera del aportante
	 */
	private EstadoCarteraEnum estadoCartera;

	/**
	 * Total de obligaciones en mora del aportante
	 */
	private BigDecimal totalDeuda;

	/**
	 * Periodos adeudados, de la forma "Enero 2016 / Mayo 2016 / Diciembre 2016"
	 */
	private String periodosAdeudados;

	/**
	 * Lista de registros para gestión de cartera
	 */
	private List<CarteraModeloDTO> listaCarteraDTO;

	/**
	 * Lista de registros para gestión de fiscalización
	 */
	private List<SolicitudFiscalizacionModeloDTO> listaFiscalizacionDTO;

	/**
	 * Lista de registros para gestión preventiva
	 */
	private List<SolicitudPreventivaModeloDTO> listaPreventivaDTO;

	/**
	 * Obtiene el valor de tipoIdentificacion
	 * 
	 * @return El valor de tipoIdentificacion
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * Establece el valor de tipoIdentificacion
	 * 
	 * @param tipoIdentificacion
	 *            El valor de tipoIdentificacion por asignar
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * Obtiene el valor de numeroIdentificacion
	 * 
	 * @return El valor de numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * Establece el valor de numeroIdentificacion
	 * 
	 * @param numeroIdentificacion
	 *            El valor de numeroIdentificacion por asignar
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * Obtiene el valor de digitoVerificacion
	 * 
	 * @return El valor de digitoVerificacion
	 */
	public Short getDigitoVerificacion() {
		return digitoVerificacion;
	}

	/**
	 * Establece el valor de digitoVerificacion
	 * 
	 * @param digitoVerificacion
	 *            El valor de digitoVerificacion por asignar
	 */
	public void setDigitoVerificacion(Short digitoVerificacion) {
		this.digitoVerificacion = digitoVerificacion;
	}

	/**
	 * Obtiene el valor de tipoSolicitante
	 * 
	 * @return El valor de tipoSolicitante
	 */
	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
		return tipoSolicitante;
	}

	/**
	 * Establece el valor de tipoSolicitante
	 * 
	 * @param tipoSolicitante
	 *            El valor de tipoSolicitante por asignar
	 */
	public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
		this.tipoSolicitante = tipoSolicitante;
	}

	/**
	 * Obtiene el valor de estadoCartera
	 * 
	 * @return El valor de estadoCartera
	 */
	public EstadoCarteraEnum getEstadoCartera() {
		return estadoCartera;
	}

	/**
	 * Establece el valor de estadoCartera
	 * 
	 * @param estadoCartera
	 *            El valor de estadoCartera por asignar
	 */
	public void setEstadoCartera(EstadoCarteraEnum estadoCartera) {
		this.estadoCartera = estadoCartera;
	}

	/**
	 * Obtiene el valor de totalDeuda
	 * 
	 * @return El valor de totalDeuda
	 */
	public BigDecimal getTotalDeuda() {
		return totalDeuda;
	}

	/**
	 * Establece el valor de totalDeuda
	 * 
	 * @param totalDeuda
	 *            El valor de totalDeuda por asignar
	 */
	public void setTotalDeuda(BigDecimal totalDeuda) {
		this.totalDeuda = totalDeuda;
	}

	/**
	 * Obtiene el valor de periodosAdeudados
	 * 
	 * @return El valor de periodosAdeudados
	 */
	public String getPeriodosAdeudados() {
		return periodosAdeudados;
	}

	/**
	 * Establece el valor de periodosAdeudados
	 * 
	 * @param periodosAdeudados
	 *            El valor de periodosAdeudados por asignar
	 */
	public void setPeriodosAdeudados(String periodosAdeudados) {
		this.periodosAdeudados = periodosAdeudados;
	}

	/**
	 * Obtiene el valor de listaCarteraDTO
	 * 
	 * @return El valor de listaCarteraDTO
	 */
	public List<CarteraModeloDTO> getListaCarteraDTO() {
		return listaCarteraDTO;
	}

	/**
	 * Establece el valor de listaCarteraDTO
	 * 
	 * @param listaCarteraDTO
	 *            El valor de listaCarteraDTO por asignar
	 */
	public void setListaCarteraDTO(List<CarteraModeloDTO> listaCarteraDTO) {
		this.listaCarteraDTO = listaCarteraDTO;
	}

	/**
	 * Obtiene el valor de listaFiscalizacionDTO
	 * 
	 * @return El valor de listaFiscalizacionDTO
	 */
	public List<SolicitudFiscalizacionModeloDTO> getListaFiscalizacionDTO() {
		return listaFiscalizacionDTO;
	}

	/**
	 * Establece el valor de listaFiscalizacionDTO
	 * 
	 * @param listaFiscalizacionDTO
	 *            El valor de listaFiscalizacionDTO por asignar
	 */
	public void setListaFiscalizacionDTO(List<SolicitudFiscalizacionModeloDTO> listaFiscalizacionDTO) {
		this.listaFiscalizacionDTO = listaFiscalizacionDTO;
	}

	/**
	 * Obtiene el valor de listaPreventivaDTO
	 * 
	 * @return El valor de listaPreventivaDTO
	 */
	public List<SolicitudPreventivaModeloDTO> getListaPreventivaDTO() {
		return listaPreventivaDTO;
	}

	/**
	 * Establece el valor de listaPreventivaDTO
	 * 
	 * @param listaPreventivaDTO
	 *            El valor de listaPreventivaDTO por asignar
	 */
	public void setListaPreventivaDTO(List<SolicitudPreventivaModeloDTO> listaPreventivaDTO) {
		this.listaPreventivaDTO = listaPreventivaDTO;
	}
}
