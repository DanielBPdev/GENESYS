/**
 * 
 */
package com.asopagos.dto;

import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.core.ParametrizacionNovedad;
import com.asopagos.enumeraciones.core.PuntoResolucionEnum;
import com.asopagos.enumeraciones.core.TipoNovedadEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * Clase DTO que contiene los datos de una novedad
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class ParametrizacionNovedadDTO {

	/**
	 * Id de la solicitud asociada a la novedad.
	 */
	private Long idNovedad;

	/**
	 * Nombre de la novedad
	 */
	private PuntoResolucionEnum puntoResolucion;

	
	/**
	 * Tipo de transaccion de la novedad.
	 */
	private TipoTransaccionEnum novedad;
	
	/**
	 * Ruta cualificada del servicio que guarda la novedad.
	 */
	private String rutaCualificada;
	
	/**
	 * Variable tipoNovedad
	 */
	private TipoNovedadEnum tipoNovedad;
	
	/**
	 * Variable nombre novedad
	 */
	private String nombreNovedad;
	
	/**
	 * Método que convierte la entidad a una novedad.
	 * @param novedad representada en forma de entidad.
	 * @return novedad en forma de dto.
	 */
	public static ParametrizacionNovedadDTO convertNovedadToDTO(ParametrizacionNovedad novedad){
		
		ParametrizacionNovedadDTO novedadDTO = new ParametrizacionNovedadDTO();
		novedadDTO.setIdNovedad(novedad.getIdNovedad());
		novedadDTO.setPuntoResolucion(novedad.getPuntoResolucion());
		novedadDTO.setNovedad(novedad.getNovedad());
		novedadDTO.setTipoNovedad(novedad.getTipoNovedad());
		novedadDTO.setRutaCualificada(novedad.getRutaCualificada());
		novedadDTO.setNombreNovedad(novedad.getNovedad().getDescripcion());
		
		return novedadDTO;
	}

	/**
	 * Método que retorna el valor de idNovedad.
	 * @return valor de idNovedad.
	 */
	public Long getIdNovedad() {
		return idNovedad;
	}

	/**
	 * Método encargado de modificar el valor de idNovedad.
	 * @param valor para modificar idNovedad.
	 */
	public void setIdNovedad(Long idNovedad) {
		this.idNovedad = idNovedad;
	}

	/**
	 * Método que retorna el valor de puntoResolucion.
	 * @return valor de puntoResolucion.
	 */
	public PuntoResolucionEnum getPuntoResolucion() {
		return puntoResolucion;
	}

	/**
	 * Método encargado de modificar el valor de puntoResolucion.
	 * @param valor para modificar puntoResolucion.
	 */
	public void setPuntoResolucion(PuntoResolucionEnum puntoResolucion) {
		this.puntoResolucion = puntoResolucion;
	}

	/**
	 * Método que retorna el valor de novedad.
	 * @return valor de novedad.
	 */
	public TipoTransaccionEnum getNovedad() {
		return novedad;
	}

	/**
	 * Método encargado de modificar el valor de novedad.
	 * @param valor para modificar novedad.
	 */
	public void setNovedad(TipoTransaccionEnum novedad) {
		this.novedad = novedad;
	}


	/**
	 * Método que retorna el valor de rutaCualificada.
	 * @return valor de rutaCualificada.
	 */
	public String getRutaCualificada() {
		return rutaCualificada;
	}

	/**
	 * Método encargado de modificar el valor de rutaCualificada.
	 * @param valor para modificar rutaCualificada.
	 */
	public void setRutaCualificada(String rutaCualificada) {
		this.rutaCualificada = rutaCualificada;
	}

	/**
	 * Método encargado de retornar el valor del campo tipoNovedad
	 * @return el campo tipoNovedad
	 */
	public TipoNovedadEnum getTipoNovedad() {
		return tipoNovedad;
	}

	/**
	 * Método encargado de asignar el valor al campo tipoNovedad
	 * @param tipoNovedad tipoNovedad a asignar
	 */
	public void setTipoNovedad(TipoNovedadEnum tipoNovedad) {
		this.tipoNovedad = tipoNovedad;
	}

    /**
     * Método que retorna el nombre de la novedad
     * @return  nombreNovedad
     */
    public String getNombreNovedad() {
        return nombreNovedad;
    }

    /**
     * Método que asigna el nombre de una novedad
     * @param nombreNovedad nombreNovedad a asignar
     */
    public void setNombreNovedad(String nombreNovedad) {
        this.nombreNovedad = nombreNovedad;
    }


	@Override
	public String toString() {
		return "{" +
			" idNovedad='" + getIdNovedad() + "'" +
			", puntoResolucion='" + getPuntoResolucion() + "'" +
			", novedad='" + getNovedad() + "'" +
			", rutaCualificada='" + getRutaCualificada() + "'" +
			", tipoNovedad='" + getTipoNovedad() + "'" +
			", nombreNovedad='" + getNombreNovedad() + "'" +
			"}";
	}
	

}
