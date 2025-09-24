/**
 * 
 */
package com.asopagos.dto.modelo;

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
public class ParametrizacionNovedadModeloDTO {

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
	 * Nombre legible de la novedad.
	 */
	private String nombre;
	
	/**
	 * Método que convierte la entidad a una novedad.
	 * @param novedad representada en forma de entidad.
	 * @return novedad en forma de dto.
	 */
	public void convertToDTO(ParametrizacionNovedad novedad){
		
		this.setIdNovedad(novedad.getIdNovedad());
		this.setPuntoResolucion(novedad.getPuntoResolucion());
		this.setNovedad(novedad.getNovedad());
		this.setTipoNovedad(novedad.getTipoNovedad());
		this.setRutaCualificada(novedad.getRutaCualificada());
		this.setNombre(novedad.getNovedad().getDescripcion());
	}
	
	/**
	 * Método que convierte de DTO a una Entidad.
	 * @return novedad convertir.
	 */
	public ParametrizacionNovedad convertToEntity(){
	
		ParametrizacionNovedad novedad = new ParametrizacionNovedad();
		novedad.setIdNovedad(this.getIdNovedad());
		novedad.setPuntoResolucion(this.getPuntoResolucion());
		novedad.setNovedad(this.getNovedad());
		novedad.setTipoNovedad(this.getTipoNovedad());
		novedad.setRutaCualificada(this.getRutaCualificada());
		return novedad;
	}
	
    /**
     * Copia los datos del DTO a la Entidad.
     * @param novedad previamente consultada.
     */
	public ParametrizacionNovedad copyDTOToEntity(ParametrizacionNovedad novedad) {
		if (this.getIdNovedad() != null) {
			novedad.setIdNovedad(this.getIdNovedad());
		}
		if (this.getPuntoResolucion() != null) {
			novedad.setPuntoResolucion(this.getPuntoResolucion());
		}
		if (this.getNovedad() != null) {
			novedad.setNovedad(this.getNovedad());
		}
		if (this.getTipoNovedad() != null) {
			novedad.setTipoNovedad(this.getTipoNovedad());
		}
		if (this.getRutaCualificada() != null) {
			novedad.setRutaCualificada(this.getRutaCualificada());
		}
		return novedad;
		
	}

	/**
	 * Método que retorna el valor de idNovedad.
	 * 
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
	 * Método que retorna el valor de nombre.
	 * @return valor de nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método encargado de modificar el valor de nombre.
	 * @param valor para modificar nombre.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	

}
