package com.asopagos.dto.modelo;	
import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.ccf.personas.NovedadDetalle;

/**
 * DTO con los datos del Modelo de NovedadPila.
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class NovedadDetalleModeloDTO implements Serializable{
	
	/**
	 * Código identificador de llave primaria de la novedad de pila
	 */
	private Long idNovedadDetalle;

	/**
	 * Rol afiliado asociado a la novedad proveniente de pila
	 */
	private Long idSolicitudNovedad;

	/**
	 * Fecha inicio de la novedad de PILA.
	 */
	private Long fechaInicio;

	/**
	 * Fecha fin de la novedad de PILA.
	 */
	private Long fechaFin;

	/**
	 * Atributo que indica si la novedad proveniente de PILA esta vigente o no.
	 */
	private Boolean vigente;
	
    /**
     * Asocia los datos del DTO a la Entidad
     * @return NovedadPila
     */
    public NovedadDetalle convertToEntity() {
    	NovedadDetalle novedadPila = new NovedadDetalle();
    	novedadPila.setIdNovedadDetalle(this.getIdNovedadDetalle());
    	novedadPila.setIdSolicitudNovedad(this.getIdSolicitudNovedad());
    	if (this.getFechaInicio() != null) {
    		novedadPila.setFechaInicio(new Date(this.getFechaInicio()));
    	}
    	if (this.getFechaFin() != null) {
    		novedadPila.setFechaFin(new Date(this.getFechaFin()));
    	}
    	novedadPila.setVigente(this.getVigente());
    	return novedadPila;
    }
    
    /**
     * @param Asocia los datos de la Entidad al DTO
     * @return NovedadPilaModeloDTO
     */
    public void convertToDTO (NovedadDetalle novedadPila) {
    	this.setIdNovedadPila(novedadPila.getIdNovedadDetalle());
    	this.setIdSolicitudNovedad(novedadPila.getIdSolicitudNovedad());
    	if (novedadPila.getFechaInicio() != null) {
    		this.setFechaInicio(novedadPila.getFechaInicio().getTime());
    	}
    	if (novedadPila.getFechaFin() != null) {
    		this.setFechaFin(novedadPila.getFechaFin().getTime());
    	}
    	this.setVigente(novedadPila.getVigente());
    }
    
    /**
     * Copia los datos del DTO a la Entidad.
     * @param novedadPila previamente consultada.
     * @param novedadPilaModeloDTO DTO a copiar.
     */
    public static void copyDTOToEntity (NovedadDetalle novedadPila, NovedadDetalleModeloDTO novedadPilaModeloDTO) {
		if (novedadPilaModeloDTO.getIdNovedadDetalle() != null) {
			novedadPila.setIdNovedadDetalle(novedadPilaModeloDTO.getIdNovedadDetalle());
		}
		if (novedadPilaModeloDTO.getIdSolicitudNovedad() != null) {
			novedadPila.setIdSolicitudNovedad(novedadPilaModeloDTO.getIdSolicitudNovedad());
		}
		if (novedadPilaModeloDTO.getFechaInicio() != null) {
			novedadPila.setFechaInicio(new Date(novedadPilaModeloDTO.getFechaInicio()));
		}
		if (novedadPilaModeloDTO.getFechaFin() != null) {
			novedadPila.setFechaFin(new Date(novedadPilaModeloDTO.getFechaFin()));
		}
		if (novedadPilaModeloDTO.getVigente() != null) {
			novedadPila.setVigente(novedadPilaModeloDTO.getVigente());
		}
    }

	/**
	 * @return the idNovedadDetalle
	 */
	public Long getIdNovedadDetalle() {
		return idNovedadDetalle;
	}

	/**
	 * @param idNovedadPila the idNovedadDetalle to set
	 */
	public void setIdNovedadPila(Long idNovedadDetalle) {
		this.idNovedadDetalle = idNovedadDetalle;
	}

	/**
	 * @return the idSolicitudNovedad
	 */
	public Long getIdSolicitudNovedad() {
		return idSolicitudNovedad;
	}

	/**
	 * @param idSolicitudNovedad the idSolicitudNovedad to set
	 */
	public void setIdSolicitudNovedad(Long idSolicitudNovedad) {
		this.idSolicitudNovedad = idSolicitudNovedad;
	}

	/**
	 * @return the fechaInicio
	 */
	public Long getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Long fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public Long getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Long fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the vigente
	 */
	public Boolean getVigente() {
		return vigente;
	}

	/**
	 * @param vigente the vigente to set
	 */
	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}
}
