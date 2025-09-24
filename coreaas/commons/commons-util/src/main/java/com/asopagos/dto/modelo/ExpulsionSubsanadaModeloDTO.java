package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;

import com.asopagos.entidades.ccf.personas.CondicionInvalidez;
import com.asopagos.entidades.ccf.personas.ExpulsionSubsanada;

/**
 * DTO con los datos del Modelo de CondicionInvalidez.
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class ExpulsionSubsanadaModeloDTO implements Serializable {

	/**
	 * Código identificador de llave primaria de la subsanacion de expulsion
	 */
	private Long idExpulsionSubsanada;
	/**
	 * Indicador S/N si el empleador cuenta con alguna expulsión subsanada[S=Si
	 * N=No]
	 */
	private Boolean expulsionSubsanada;

	/**
	 * Fecha registro subsanacion expulsion
	 */
	private Long fechaSubsancionExpulsion;

	/**
	 * Motivo subsnacion expulsion
	 */
	private String motivoSubsanacionExpulsion;

	/**
	 * Identificador del rol afiliado asociado
	 */
	private Long idRolAfiliado;

	/**
	 * Identificador empleador asociado
	 */
	private Long idEmpleador;

	/**
	 * Asocia los datos del DTO a la Entidad
	 * 
	 * @return ExpulsionSubsanada
	 */
	public ExpulsionSubsanada convertToEntity() {
		ExpulsionSubsanada expulsionSubsanada = new ExpulsionSubsanada();
		expulsionSubsanada.setIdExpulsionSubsanada(this.getIdExpulsionSubsanada());
		expulsionSubsanada.setExpulsionSubsanada(this.getExpulsionSubsanada());
		expulsionSubsanada.setMotivoSubsanacionExpulsion(this.getMotivoSubsanacionExpulsion());
		if (this.getFechaSubsancionExpulsion() != null) {
			expulsionSubsanada.setFechaSubsancionExpulsion(new Date(this.getFechaSubsancionExpulsion()));
		}
		if (this.getIdEmpleador() != null) {
			expulsionSubsanada.setIdEmpleador(this.getIdEmpleador());
		}
		if (this.getIdRolAfiliado() != null) {
			expulsionSubsanada.setIdRolAfiliado(this.getIdRolAfiliado());
		}
		return expulsionSubsanada;
	}

	/**
	 * @param Asocia
	 *            los datos de la Entidad al DTO
	 * @return ExpulsionSubsanadaModeloDTO
	 */
	public void convertToDTO(ExpulsionSubsanada expulsionSubsanada) {
		this.setIdExpulsionSubsanada(expulsionSubsanada.getIdExpulsionSubsanada());
		this.setExpulsionSubsanada(expulsionSubsanada.getExpulsionSubsanada());
		this.setMotivoSubsanacionExpulsion(expulsionSubsanada.getMotivoSubsanacionExpulsion());
		if (expulsionSubsanada.getFechaSubsancionExpulsion() != null) {
			this.setFechaSubsancionExpulsion(expulsionSubsanada.getFechaSubsancionExpulsion().getTime());
		}
		if (expulsionSubsanada.getIdEmpleador() != null) {
			this.setIdEmpleador(expulsionSubsanada.getIdEmpleador());
		}
		if (expulsionSubsanada.getIdRolAfiliado() != null) {
			this.setIdRolAfiliado(expulsionSubsanada.getIdRolAfiliado());
		}
	}

	/**
	 * Copia los datos del DTO a la Entidad.
	 * 
	 * @param expulsionSubsanada
	 *            previamente consultada.
	 * @param expulsionSubsanadaModeloDTO
	 *            DTO a copiar.
	 */
	public static void copyDTOToEntity(ExpulsionSubsanada expulsionSubsanada,
			ExpulsionSubsanadaModeloDTO expulsionSubsanadaModeloDTO) {
		if (expulsionSubsanadaModeloDTO.getIdExpulsionSubsanada() != null) {
			expulsionSubsanada.setIdExpulsionSubsanada(expulsionSubsanadaModeloDTO.getIdExpulsionSubsanada());
		}
		if (expulsionSubsanadaModeloDTO.getExpulsionSubsanada() != null) {
			expulsionSubsanada.setExpulsionSubsanada(expulsionSubsanadaModeloDTO.getExpulsionSubsanada());
		}
		if (expulsionSubsanadaModeloDTO.getMotivoSubsanacionExpulsion() != null) {
			expulsionSubsanada
					.setMotivoSubsanacionExpulsion(expulsionSubsanadaModeloDTO.getMotivoSubsanacionExpulsion());
		}
		if (expulsionSubsanadaModeloDTO.getFechaSubsancionExpulsion() != null) {
			expulsionSubsanada
					.setFechaSubsancionExpulsion(new Date(expulsionSubsanadaModeloDTO.getFechaSubsancionExpulsion()));
		}
		if (expulsionSubsanadaModeloDTO.getIdEmpleador() != null) {
			expulsionSubsanada.setIdEmpleador(expulsionSubsanadaModeloDTO.getIdEmpleador());
		}
		if (expulsionSubsanadaModeloDTO.getIdRolAfiliado() != null) {
			expulsionSubsanada.setIdRolAfiliado(expulsionSubsanadaModeloDTO.getIdRolAfiliado());
		}
	}

	/**
	 * @return the idExpulsionSubsanada
	 */
	public Long getIdExpulsionSubsanada() {
		return idExpulsionSubsanada;
	}

	/**
	 * @param idExpulsionSubsanada
	 *            the idExpulsionSubsanada to set
	 */
	public void setIdExpulsionSubsanada(Long idExpulsionSubsanada) {
		this.idExpulsionSubsanada = idExpulsionSubsanada;
	}

	/**
	 * @return the expulsionSubsanada
	 */
	public Boolean getExpulsionSubsanada() {
		return expulsionSubsanada;
	}

	/**
	 * @param expulsionSubsanada
	 *            the expulsionSubsanada to set
	 */
	public void setExpulsionSubsanada(Boolean expulsionSubsanada) {
		this.expulsionSubsanada = expulsionSubsanada;
	}

	/**
	 * @return the fechaSubsancionExpulsion
	 */
	public Long getFechaSubsancionExpulsion() {
		return fechaSubsancionExpulsion;
	}

	/**
	 * @param fechaSubsancionExpulsion
	 *            the fechaSubsancionExpulsion to set
	 */
	public void setFechaSubsancionExpulsion(Long fechaSubsancionExpulsion) {
		this.fechaSubsancionExpulsion = fechaSubsancionExpulsion;
	}

	/**
	 * @return the motivoSubsanacionExpulsion
	 */
	public String getMotivoSubsanacionExpulsion() {
		return motivoSubsanacionExpulsion;
	}

	/**
	 * @param motivoSubsanacionExpulsion
	 *            the motivoSubsanacionExpulsion to set
	 */
	public void setMotivoSubsanacionExpulsion(String motivoSubsanacionExpulsion) {
		this.motivoSubsanacionExpulsion = motivoSubsanacionExpulsion;
	}

	/**
	 * @return the idRolAfiliado
	 */
	public Long getIdRolAfiliado() {
		return idRolAfiliado;
	}

	/**
	 * @param idRolAfiliado
	 *            the idRolAfiliado to set
	 */
	public void setIdRolAfiliado(Long idRolAfiliado) {
		this.idRolAfiliado = idRolAfiliado;
	}

	/**
	 * @return the idEmpleador
	 */
	public Long getIdEmpleador() {
		return idEmpleador;
	}

	/**
	 * @param idEmpleador
	 *            the idEmpleador to set
	 */
	public void setIdEmpleador(Long idEmpleador) {
		this.idEmpleador = idEmpleador;
	}

}
