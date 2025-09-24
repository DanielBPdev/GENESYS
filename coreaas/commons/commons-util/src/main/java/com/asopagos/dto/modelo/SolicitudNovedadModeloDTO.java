package com.asopagos.dto.modelo;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedad;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;

/**
 * DTO que contiene los campos de un Afiliado.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class SolicitudNovedadModeloDTO extends SolicitudModeloDTO implements Serializable {
	/**
	 * Código identificador de la solicitud de novedad.
	 */
	private Long idSolicitudNovedad;
    
	/**
	 * Descripción del estado de la solicitud de novedad.
	 */
    private EstadoSolicitudNovedadEnum estadoSolicitud;
	
	/**
	 * Id de la novedad asociada a la solicitud (es la parametrizacion de Novedad).
	 */
	private Long idNovedad;
	
	/**
	 * Observaciones de la novedad.
	 */
	private String observacionesNovedad;

	/**
	 * Atributo que indica si la solicitud se genero por una carga multiple.
	 */
	private Boolean cargaMultiple;
	
    /**
     * Método encargado de convertir de DTO a Entidad.
     * @return entidad convertida.
     */
    public SolicitudNovedad convertToEntity(){
    	SolicitudNovedad solicitudNovedad = new SolicitudNovedad();
        solicitudNovedad.setIdSolicitudNovedad(this.getIdSolicitudNovedad());
        solicitudNovedad.setIdNovedad(this.getIdNovedad());
        solicitudNovedad.setEstadoSolicitud(this.getEstadoSolicitud());
        solicitudNovedad.setObservaciones(this.getObservacionesNovedad());
        Solicitud solicitudGlobal = super.convertToSolicitudEntity();
        solicitudNovedad.setSolicitudGlobal(solicitudGlobal);
        solicitudNovedad.setCargaMultiple(this.cargaMultiple);
    	return solicitudNovedad;
    }
    /**
     * Método encargado de convertir de Entidad a DTO.
     * @param solicitud entidad a convertir.
     */
    public void convertToDTO(SolicitudNovedad solicitudNovedad){
    	if(solicitudNovedad.getSolicitudGlobal()!=null){
    		super.convertToDTO(solicitudNovedad.getSolicitudGlobal());
    	}
    	this.setIdSolicitudNovedad(solicitudNovedad.getIdSolicitudNovedad());
    	this.setIdNovedad(solicitudNovedad.getIdNovedad());
    	this.setEstadoSolicitud(solicitudNovedad.getEstadoSolicitud());
    	this.setObservacionesNovedad(solicitudNovedad.getObservaciones());
    	this.setCargaMultiple(solicitudNovedad.getCargaMultiple());
    	
    }
    
    /**
     * Método encargado de copiar un DTO  a una Entidad.
     * @param solicitudNovedad previamente consultado.
     * @return solicitudNovedad solicitud modificada con los datos del DTO.
     */
	public SolicitudNovedad copyDTOToEntiy(SolicitudNovedad solicitudNovedad) {
		if(this.getIdSolicitudNovedad()!=null){
    		solicitudNovedad.setIdSolicitudNovedad(this.getIdSolicitudNovedad());
    	}
    	if(this.getIdNovedad()!= null){
    		solicitudNovedad.setIdNovedad(solicitudNovedad.getIdNovedad());
    	}
    	if(this.getEstadoSolicitud()!=null){
    		solicitudNovedad.setEstadoSolicitud(this.getEstadoSolicitud());	
    	}
    	if(this.getObservacionesNovedad()!=null){
    		solicitudNovedad.setObservaciones(this.getObservacionesNovedad());
    	}
    	Solicitud solicitudGlobal = super.copyDTOToEntiy(solicitudNovedad.getSolicitudGlobal());
    	if(solicitudGlobal.getIdSolicitud()!=null){
    		solicitudNovedad.setSolicitudGlobal(solicitudGlobal);
    	}
    	if(this.getCargaMultiple()!=null){
    		solicitudNovedad.setCargaMultiple(this.getCargaMultiple());
    	}
    	return solicitudNovedad;
	}
	/**
	 * Método que retorna el valor de idSolicitudNovedad.
	 * @return valor de idSolicitudNovedad.
	 */
	public Long getIdSolicitudNovedad() {
		return idSolicitudNovedad;
	}
	/**
	 * Método encargado de modificar el valor de idSolicitudNovedad.
	 * @param valor para modificar idSolicitudNovedad.
	 */
	public void setIdSolicitudNovedad(Long idSolicitudNovedad) {
		this.idSolicitudNovedad = idSolicitudNovedad;
	}
	/**
	 * Método que retorna el valor de estadoSolicitud.
	 * @return valor de estadoSolicitud.
	 */
	public EstadoSolicitudNovedadEnum getEstadoSolicitud() {
		return estadoSolicitud;
	}
	/**
	 * Método encargado de modificar el valor de estadoSolicitud.
	 * @param valor para modificar estadoSolicitud.
	 */
	public void setEstadoSolicitud(EstadoSolicitudNovedadEnum estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
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
	 * Método que retorna el valor de observacionesNovedad.
	 * @return valor de observacionesNovedad.
	 */
	public String getObservacionesNovedad() {
		return observacionesNovedad;
	}
	/**
	 * Método encargado de modificar el valor de observacionesNovedad.
	 * @param valor para modificar observacionesNovedad.
	 */
	public void setObservacionesNovedad(String observacionesNovedad) {
		this.observacionesNovedad = observacionesNovedad;
	}
	/**
	 * Método que retorna el valor de cargaMultiple.
	 * @return valor de cargaMultiple.
	 */
	public final Boolean getCargaMultiple() {
		return cargaMultiple;
	}
	/**
	 * Método encargado de modificar el valor de cargaMultiple.
	 * @param valor para modificar cargaMultiple.
	 */
	public final void setCargaMultiple(Boolean cargaMultiple) {
		this.cargaMultiple = cargaMultiple;
	}
	
}
