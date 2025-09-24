package com.asopagos.dto.modelo;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedadPila;

/**
 * DTO que representa las Solicitudes de Novedad asociadas a PILA
 * 
 * @author <a href="flopez@heinsohn.com.co">Fabian López</a>
 */
@XmlRootElement
public class SolicitudNovedadPilaModeloDTO implements Serializable {
    private static final long serialVersionUID = 4103223490809790690L;

    /**
     * Código identificador de llave primaria de la Solicitud Novedad Pila
     */
    private Long id;

    /**
   	 * Referencia la SolicitudNovedad asociada a la Solicitud Novedad Pila
   	 */
	private Long idSolicitudNovedad;
       
	 /**
	 * Referencia el Registro Detallado(Pila) asociado a la Solicitud de Novedad.
	 */
	private Long idRegistroDetallado;
    
    /**
     * Referencia el indicador de registro originado en aporte manual
     * */
    private Boolean originadoEnAporteManual;

      private Long idRegistroDetalladoNovedad;
    
    /**
     * Método encargado de convertir de DTO a Entidad.
     * @return entidad convertida.
     */
    public SolicitudNovedadPila convertToEntity(){
        SolicitudNovedadPila solicitudNovedadPila = new SolicitudNovedadPila();
        solicitudNovedadPila.setIdSolicitudNovedadPila(this.getId());
        solicitudNovedadPila.setIdSolicitudNovedad(this.getIdSolicitudNovedad());
        solicitudNovedadPila.setIdRegistroDetallado(this.getIdRegistroDetallado());
        solicitudNovedadPila.setOriginadoEnAporteManual(this.getOriginadoEnAporteManual());
        solicitudNovedadPila.setIdRegistroDetalladoNovedad(this.getIdRegistroDetalladoNovedad());
        return solicitudNovedadPila;
    }
    /**
     * Método encargado de convertir de Entidad a DTO.
     * @param solicitud entidad a convertir.
     */
    public void convertToDTO(SolicitudNovedadPila solicitudNovedadPila){
        this.setId(solicitudNovedadPila.getIdSolicitudNovedadPila());
        this.setIdSolicitudNovedad(solicitudNovedadPila.getIdSolicitudNovedad());
        this.setIdRegistroDetallado(solicitudNovedadPila.getIdRegistroDetallado());
        this.setOriginadoEnAporteManual(solicitudNovedadPila.getOriginadoEnAporteManual());
         this.setIdRegistroDetalladoNovedad(solicitudNovedadPila.getIdRegistroDetalladoNovedad());
        
    }
    

    /**
     * Método que retorna el valor de id.
     * @return valor de id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Método encargado de modificar el valor de id.
     * @param valor para modificar id.
     */
    public void setId(Long id) {
        this.id = id;
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
	 * @return the idRegistroDetallado
	 */
	public Long getIdRegistroDetallado() {
		return idRegistroDetallado;
	}
	/**
	 * @param idRegistroDetallado the idRegistroDetallado to set
	 */
	public void setIdRegistroDetallado(Long idRegistroDetallado) {
		this.idRegistroDetallado = idRegistroDetallado;
	}
    /**
     * @return the originadoEnAporteManual
     */
    public Boolean getOriginadoEnAporteManual() {
        return originadoEnAporteManual;
    }
    /**
     * @param originadoEnAporteManual the originadoEnAporteManual to set
     */
    public void setOriginadoEnAporteManual(Boolean originadoEnAporteManual) {
        this.originadoEnAporteManual = originadoEnAporteManual;
    }


         /**
     * @return the idRegistroDetalladoNovedad pila
     */
    public Long getIdRegistroDetalladoNovedad() {
        return idRegistroDetalladoNovedad;
    }

    /**
     * @param idRegistroDetalladoNovedad the idRegistroDetalladoNovedad to set
     */
    public void setIdRegistroDetalladoNovedad(Long idRegistroDetalladoNovedad) {
        this.idRegistroDetalladoNovedad = idRegistroDetalladoNovedad;
    }
}