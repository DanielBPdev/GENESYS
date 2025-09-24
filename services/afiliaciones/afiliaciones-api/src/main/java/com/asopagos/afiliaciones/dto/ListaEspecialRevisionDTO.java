package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.transversal.personas.ListaEspecialRevision;
import com.asopagos.enumeraciones.personas.EstadoListaEspecialRevisionEnum;
import com.asopagos.enumeraciones.personas.RazonInclusionListaEspecialRevisionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción:</b> DTO que contendra los datos de listaEspecialRevisionDTO
 * <b>Historia de Usuario:</b> Transversal-336
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
@XmlRootElement
public class ListaEspecialRevisionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idListaEspecialRevision;
	private TipoIdentificacionEnum tipoIdentificacion;
	private String numeroIdentificacion;
	private Byte digitoVerificacion;
	private Integer cajaCompensacion;
	private String nombreEmpleador;
	private Long fechaInicioInclusion;
	private Long fechaFinInclusion;
	private RazonInclusionListaEspecialRevisionEnum razonInclusion;
	private EstadoListaEspecialRevisionEnum estado;
	private String comentario;
	private String cajaCompensacionCodigo;

	/**
	 * 
	 */
	public ListaEspecialRevisionDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Metodo encargado de convertir una entidad persona a un DTO
	 * 
	 * @param persona,
	 *            Persona a capturar los datos
	 * @return dto de persona
	 */
	public static ListaEspecialRevisionDTO convertListaEspecialRevisionDTO(
			ListaEspecialRevision listaEspecialRevision) {
		ListaEspecialRevisionDTO listaEspecialRevisionDTO = new ListaEspecialRevisionDTO();
		listaEspecialRevisionDTO.setIdListaEspecialRevision(listaEspecialRevision.getIdListaEspecialRevision());
		listaEspecialRevisionDTO.setTipoIdentificacion(listaEspecialRevision.getTipoIdentificacion());
		listaEspecialRevisionDTO.setNumeroIdentificacion(listaEspecialRevision.getNumeroIdentificacion());
		listaEspecialRevisionDTO.setDigitoVerificacion(listaEspecialRevision.getDigitoVerificacion());
		listaEspecialRevisionDTO.setCajaCompensacion(listaEspecialRevision.getCajaCompensacion());
		listaEspecialRevisionDTO.setNombreEmpleador(listaEspecialRevision.getNombreEmpleador());
		listaEspecialRevisionDTO.setFechaInicioInclusion(listaEspecialRevision.getFechaInicioInclusion().getTime());
		if (listaEspecialRevision.getFechaFinInclusion() != null && !listaEspecialRevision.getFechaFinInclusion().equals("")) {
			listaEspecialRevisionDTO.setFechaFinInclusion(listaEspecialRevision.getFechaFinInclusion().getTime());
		}
		listaEspecialRevisionDTO.setRazonInclusion(listaEspecialRevision.getRazonInclusion());
		listaEspecialRevisionDTO.setEstado(listaEspecialRevision.getEstado());
		listaEspecialRevisionDTO.setComentario(listaEspecialRevision.getComentario());
		listaEspecialRevisionDTO.setComentario(listaEspecialRevision.getComentario());
		listaEspecialRevisionDTO.setCajaCompensacionCodigo(listaEspecialRevision.getCajaCompensacionCodigo());
		return listaEspecialRevisionDTO;
	}

	/**
	 * @return the tipoIdentificacion
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * @return the numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * @return the digitoVerificacion
	 */
	public Byte getDigitoVerificacion() {
		return digitoVerificacion;
	}

	/**
	 * @return the cajaCompensacion
	 */
	public Integer getCajaCompensacion() {
		return cajaCompensacion;
	}

	/**
	 * @return the nombreEmpleador
	 */
	public String getNombreEmpleador() {
		return nombreEmpleador;
	}

	/**
	 * @return the fechaInicioInclusion
	 */
	public Long getFechaInicioInclusion() {
		return fechaInicioInclusion;
	}

	/**
	 * @return the fechaFinInclusion
	 */
	public Long getFechaFinInclusion() {
		return fechaFinInclusion;
	}

	/**
	 * @return the razonInclusion
	 */
	public RazonInclusionListaEspecialRevisionEnum getRazonInclusion() {
		return razonInclusion;
	}

	/**
	 * @return the estado
	 */
	public EstadoListaEspecialRevisionEnum getEstado() {
		return estado;
	}

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param tipoIdentificacion
	 *            the tipoIdentificacion to set
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * @param numeroIdentificacion
	 *            the numeroIdentificacion to set
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * @param digitoVerificacion
	 *            the digitoVerificacion to set
	 */
	public void setDigitoVerificacion(Byte digitoVerificacion) {
		this.digitoVerificacion = digitoVerificacion;
	}

	/**
	 * @param cajaCompensacion
	 *            the cajaCompensacion to set
	 */
	public void setCajaCompensacion(Integer cajaCompensacion) {
		this.cajaCompensacion = cajaCompensacion;
	}

	/**
	 * @param nombreEmpleador
	 *            the nombreEmpleador to set
	 */
	public void setNombreEmpleador(String nombreEmpleador) {
		this.nombreEmpleador = nombreEmpleador;
	}

	/**
	 * @param fechaInicioInclusion
	 *            the fechaInicioInclusion to set
	 */
	public void setFechaInicioInclusion(Long fechaInicioInclusion) {
		this.fechaInicioInclusion = fechaInicioInclusion;
	}

	/**
	 * @param fechaFinInclusion
	 *            the fechaFinInclusion to set
	 */
	public void setFechaFinInclusion(Long fechaFinInclusion) {
		this.fechaFinInclusion = fechaFinInclusion;
	}

	/**
	 * @param razonInclusion
	 *            the razonInclusion to set
	 */
	public void setRazonInclusion(RazonInclusionListaEspecialRevisionEnum razonInclusion) {
		this.razonInclusion = razonInclusion;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(EstadoListaEspecialRevisionEnum estado) {
		this.estado = estado;
	}

	/**
	 * @param comentario
	 *            the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	/**
	 * @return the idListaEspecialRevision
	 */
	public Long getIdListaEspecialRevision() {
		return idListaEspecialRevision;
	}

	/**
	 * @param idListaEspecialRevision
	 *            the idListaEspecialRevision to set
	 */
	public void setIdListaEspecialRevision(Long idListaEspecialRevision) {
		this.idListaEspecialRevision = idListaEspecialRevision;
	}

    /**
     * @return the cajaCompensacionCodigo
     */
    public String getCajaCompensacionCodigo() {
        return cajaCompensacionCodigo;
    }

    /**
     * @param cajaCompensacionCodigo the cajaCompensacionCodigo to set
     */
    public void setCajaCompensacionCodigo(String cajaCompensacionCodigo) {
        this.cajaCompensacionCodigo = cajaCompensacionCodigo;
    }

}
