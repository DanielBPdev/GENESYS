/**
 * 
 */
package com.asopagos.dto.modelo;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.aportes.SolicitudCierreRecaudo;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudCierreRecaudoEnum;
import com.asopagos.enumeraciones.aportes.TipoCierreEnum;

/**
 * DTO que contiene los datos de una solicitud de cierre de recaudo.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class SolicitudCierreRecaudoModeloDTO extends SolicitudModeloDTO{
	
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
	 * Código identificador de llave primaria del aportante
	 */
	private Long idSolicitudCierreRecaudo;
    
    /**
     * Estado de la solicitud de aporte.
     */
    private EstadoSolicitudCierreRecaudoEnum estadoSolicitud;
    
	/**
	 * Fecha inicio del cierre.
	 */
	private Long fechaInicio;
	
	/**
	 * Fecha fin del cierre.
	 */
	private Long fechaFin;
	
    /**
     * Tipo de cierre del recaudo.
     */
    private TipoCierreEnum tipoCierre;
    
    /**
     * Comentarios de resultado de supervisor.
     */
    private String observacionesSupervisor;
    
    /**
     * Comentarios de resultado de analista contable.
     */
    private String observacionesContabilidad;
    
    /**
     * Usuario supervisor.
     */
    private String usuarioSupervisor;
    
    /**
     * Usuario supervisor.
     */
    private String usuarioAnalistaContable;
    
    /**
     * Id del ECM donde se encuentra el archivo a exportar.
     */
    private String idEcm;
    
    /**
     * Json con los datos del resumen.
     */
    private String resumen;
    
    /**
     * Identificadores aportes generales con proceso de conciliación
     */
    private String idsAportesGenerales;
    
    /**
     * Método encargado de convertir de DTO a Entidad.
     * 
     * @return entidad convertida.
     */
    public SolicitudCierreRecaudo convertToEntity() {
        SolicitudCierreRecaudo solicitudCierreRecaudo = new SolicitudCierreRecaudo();
        Solicitud solicitudGlobal = super.convertToSolicitudEntity();
        solicitudCierreRecaudo.setSolicitudGlobal(solicitudGlobal);
        solicitudCierreRecaudo.setIdSolicitudCierreRecaudo(this.getIdSolicitudCierreRecaudo());
        solicitudCierreRecaudo.setEstadoSolicitud(this.getEstadoSolicitud());
        if(this.getFechaFin()!= null){
            solicitudCierreRecaudo.setFechaFin(new Date(this.getFechaFin()));
        }
        if(this.getFechaInicio()!= null){
            solicitudCierreRecaudo.setFechaInicio(new Date(this.getFechaInicio()));
        }
        solicitudCierreRecaudo.setTipoCierre(this.getTipoCierre());
        solicitudCierreRecaudo.setObservacionesContabilidad(this.getObservacionesContabilidad());
        solicitudCierreRecaudo.setObservacionesSupervisor(this.getObservacionesSupervisor());
        solicitudCierreRecaudo.setUsuarioSupervisor(this.getUsuarioSupervisor());
        solicitudCierreRecaudo.setUsuarioAnalistaContable(this.getUsuarioAnalistaContable());
        solicitudCierreRecaudo.setIdEcm(this.getIdEcm());
        solicitudCierreRecaudo.setResumen(this.getResumen());
        solicitudCierreRecaudo.setIdsAportesGenerales(this.getIdsAportesGenerales());
        return solicitudCierreRecaudo;
    }

    /**
     * Método encargado de convertir de Entidad a DTO.
     * 
     * @param solicitud
     *            entidad a convertir.
     */
    public void convertToDTO(SolicitudCierreRecaudo solicitudCierre) {
        if (solicitudCierre.getSolicitudGlobal() != null) {
            super.convertToDTO(solicitudCierre.getSolicitudGlobal());
        }
        this.setIdSolicitudCierreRecaudo(solicitudCierre.getIdSolicitudCierreRecaudo());
        this.setEstadoSolicitud(solicitudCierre.getEstadoSolicitud());
        if(solicitudCierre.getFechaFin()!= null){
            this.setFechaFin(solicitudCierre.getFechaFin().getTime());
        }
        if(solicitudCierre.getFechaInicio()!= null){
            this.setFechaInicio(solicitudCierre.getFechaInicio().getTime());
        }
        this.setTipoCierre(solicitudCierre.getTipoCierre());
        this.setObservacionesContabilidad(solicitudCierre.getObservacionesContabilidad());
        this.setObservacionesSupervisor(solicitudCierre.getObservacionesSupervisor());
        this.setUsuarioSupervisor(solicitudCierre.getUsuarioSupervisor());
        this.setUsuarioAnalistaContable(solicitudCierre.getUsuarioAnalistaContable());
        this.setIdEcm(solicitudCierre.getIdEcm());
        this.setResumen(solicitudCierre.getResumen());
        this.setIdsAportesGenerales(solicitudCierre.getIdsAportesGenerales());
    }

    /**
     * Método encargado de copiar un DTO a una Entidad.
     * 
     * @param solicitudCierreRecaudo
     *            previamente consultado.
     * @return solicitudAporte solicitud modificada con los datos del DTO.
     */
    public SolicitudCierreRecaudo copyDTOToEntiy(SolicitudCierreRecaudo solicitudCierreRecaudo) {
        if (this.getIdSolicitudCierreRecaudo() != null) {
            solicitudCierreRecaudo.setIdSolicitudCierreRecaudo(this.getIdSolicitudCierreRecaudo());
        }
        Solicitud solicitudGlobal = super.copyDTOToEntiy(solicitudCierreRecaudo.getSolicitudGlobal());
        if (solicitudGlobal.getIdSolicitud() != null) {
            solicitudCierreRecaudo.setSolicitudGlobal(solicitudGlobal);
        }
        if(solicitudCierreRecaudo.getEstadoSolicitud()!= null){
            solicitudCierreRecaudo.setEstadoSolicitud(this.getEstadoSolicitud());
        }
        if(this.getFechaFin()!= null){
            solicitudCierreRecaudo.setFechaFin(new Date(this.getFechaFin()));
        }
        if(this.getFechaInicio()!= null){
            solicitudCierreRecaudo.setFechaInicio(new Date(this.getFechaInicio()));
        }
        if(this.getTipoCierre()!= null){
            solicitudCierreRecaudo.setTipoCierre(this.getTipoCierre());
        }
        if(this.getObservacionesContabilidad()!= null){
            solicitudCierreRecaudo.setObservacionesContabilidad(this.getObservacionesContabilidad());
        }
        if(this.getObservacionesSupervisor()!= null){
            solicitudCierreRecaudo.setObservacionesSupervisor(this.getObservacionesSupervisor());
        }
        if(this.getUsuarioSupervisor()!=null){
            solicitudCierreRecaudo.setUsuarioSupervisor(this.getUsuarioSupervisor());
        }
        if(this.getUsuarioSupervisor()!=null){
            solicitudCierreRecaudo.setUsuarioAnalistaContable(this.getUsuarioAnalistaContable());
        }
        if(this.getIdEcm()!=null){
            solicitudCierreRecaudo.setIdEcm(this.getIdEcm());
        }
        if(this.getResumen()!=null){
            solicitudCierreRecaudo.setResumen(this.getResumen());
        }
        if(this.getIdsAportesGenerales()!=null){
            solicitudCierreRecaudo.setIdsAportesGenerales(this.getIdsAportesGenerales());
        }
        return solicitudCierreRecaudo;
    }
    
    /**
     * Método que retorna el valor de idSolicitudCierreRecaudo.
     * @return valor de idSolicitudCierreRecaudo.
     */
    public Long getIdSolicitudCierreRecaudo() {
        return idSolicitudCierreRecaudo;
    }

    /**
     * Método encargado de modificar el valor de idSolicitudCierreRecaudo.
     * @param valor para modificar idSolicitudCierreRecaudo.
     */
    public void setIdSolicitudCierreRecaudo(Long idSolicitudCierreRecaudo) {
        this.idSolicitudCierreRecaudo = idSolicitudCierreRecaudo;
    }

    /**
     * Método que retorna el valor de estadoSolicitud.
     * @return valor de estadoSolicitud.
     */
    public EstadoSolicitudCierreRecaudoEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * Método encargado de modificar el valor de estadoSolicitud.
     * @param valor para modificar estadoSolicitud.
     */
    public void setEstadoSolicitud(EstadoSolicitudCierreRecaudoEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * Método que retorna el valor de fechaInicio.
     * @return valor de fechaInicio.
     */
    public Long getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Método encargado de modificar el valor de fechaInicio.
     * @param valor para modificar fechaInicio.
     */
    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Método que retorna el valor de fechaFin.
     * @return valor de fechaFin.
     */
    public Long getFechaFin() {
        return fechaFin;
    }

    /**
     * Método encargado de modificar el valor de fechaFin.
     * @param valor para modificar fechaFin.
     */
    public void setFechaFin(Long fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Método que retorna el valor de tipoCierre.
     * @return valor de tipoCierre.
     */
    public TipoCierreEnum getTipoCierre() {
        return tipoCierre;
    }

    /**
     * Método encargado de modificar el valor de tipoCierre.
     * @param valor para modificar tipoCierre.
     */
    public void setTipoCierre(TipoCierreEnum tipoCierre) {
        this.tipoCierre = tipoCierre;
    }

    /**
     * Método que retorna el valor de observacionesSupervisor.
     * @return valor de observacionesSupervisor.
     */
    public String getObservacionesSupervisor() {
        return observacionesSupervisor;
    }

    /**
     * Método encargado de modificar el valor de observacionesSupervisor.
     * @param valor para modificar observacionesSupervisor.
     */
    public void setObservacionesSupervisor(String observacionesSupervisor) {
        this.observacionesSupervisor = observacionesSupervisor;
    }

    /**
     * Método que retorna el valor de observacionesContabilidad.
     * @return valor de observacionesContabilidad.
     */
    public String getObservacionesContabilidad() {
        return observacionesContabilidad;
    }

    /**
     * Método encargado de modificar el valor de observacionesContabilidad.
     * @param valor para modificar observacionesContabilidad.
     */
    public void setObservacionesContabilidad(String observacionesContabilidad) {
        this.observacionesContabilidad = observacionesContabilidad;
    }

    /**
     * Método que retorna el valor de usuarioSupervisor.
     * @return valor de usuarioSupervisor.
     */
    public String getUsuarioSupervisor() {
        return usuarioSupervisor;
    }

    /**
     * Método encargado de modificar el valor de usuarioSupervisor.
     * @param valor para modificar usuarioSupervisor.
     */
    public void setUsuarioSupervisor(String usuarioSupervisor) {
        this.usuarioSupervisor = usuarioSupervisor;
    }

    /**
     * Método que retorna el valor de usuarioAnalistaContable.
     * @return valor de usuarioAnalistaContable.
     */
    public String getUsuarioAnalistaContable() {
        return usuarioAnalistaContable;
    }

    /**
     * Método encargado de modificar el valor de usuarioAnalistaContable.
     * @param valor para modificar usuarioAnalistaContable.
     */
    public void setUsuarioAnalistaContable(String usuarioAnalistaContable) {
        this.usuarioAnalistaContable = usuarioAnalistaContable;
    }

    /**
     * @return the idEcm
     */
    public String getIdEcm() {
        return idEcm;
    }

    /**
     * @param idEcm the idEcm to set
     */
    public void setIdEcm(String idEcm) {
        this.idEcm = idEcm;
    }

    /**
     * @return the resumen
     */
    public String getResumen() {
        return resumen;
    }

    /**
     * @param resumen the resumen to set
     */
    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    /**
     * @return the idsAportesGenerales
     */
    public String getIdsAportesGenerales() {
        return idsAportesGenerales;
    }

    /**
     * @param idsAportesGenerales the idsAportesGenerales to set
     */
    public void setIdsAportesGenerales(String idsAportesGenerales) {
        this.idsAportesGenerales = idsAportesGenerales;
    } 
}
