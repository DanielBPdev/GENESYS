package com.asopagos.dto.modelo;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.cartera.Cartera;
import com.asopagos.entidades.ccf.cartera.CicloAportante;
import com.asopagos.entidades.ccf.cartera.SolicitudGestionCobroManual;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoFiscalizacionEnum;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;

/**
 * <b>Descripción: </b> Clase que representa los datos de una solicitud de
 * gestión de cobro Manual <br/>
 * <b>Historia de Usuario: </b>
 * 
 * @author <a href="mailto:jusanchez@heinsohn.com.co">Julian Andres Sanchez Bedoya</a>
 */
@XmlRootElement
public class SolicitudGestionCobroManualModeloDTO extends SolicitudModeloDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 2117486390536445375L;

	/**
	 * Identificador único, llave primaria de la solicitud de gestión de cobro 
	 */
	private Long idSolicitudGestionCobroManual;
	/**
	 * Id del ciclo del aportante
	 */
	private Long idCicloAportante;
	/**
	 * Estado de la solicitud de gestión de cobro manual 
	 */
	private EstadoFiscalizacionEnum estadoSolicitud;
	/**
	 * Línea de cobro perteneciente a la solicitud 
	 */
	private TipoLineaCobroEnum lineaCobro;
	/**
	 * Identificador de cartera
	 */
	private Long idCartera;
	/**
	 * Id de la persona 
	 */
	private Long idPersona;
	/**
	 * Tipo de solicitante de la persona perteneciente a la solicitud 
	 */
	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
	/**       
	 * Tipo de actividad de la cartera perteneciente a la solicitud 
	 */
	private TipoAccionCobroEnum actividad;
	
	/**
     * 
     */
    public SolicitudGestionCobroManualModeloDTO() {
        
    }
	
    /**
     * Método constructor de la solicitud de gestión de cobro manual
     * @param solicitudGestionCobroManual,
     *        solicitud de gestión de cobro manual
     * @param cicloAportante,
     *        ciclo del aportante
     * @param actividad, actividad de la cartera 
     */
    public SolicitudGestionCobroManualModeloDTO(SolicitudGestionCobroManual solicitudGestionCobroManual, CicloAportante cicloAportante) {
        convertToDTO(solicitudGestionCobroManual);
        this.setIdPersona(cicloAportante.getIdPersona());
        this.setTipoSolicitante(cicloAportante.getTipoSolicitanteMovimientoAporteEnum());        
    }
    
    /**
     * Constructor
     * @param solicitudGestionCobroManual
     * @param cicloAportante
     * @param cartera
     */
    public SolicitudGestionCobroManualModeloDTO(SolicitudGestionCobroManual solicitudGestionCobroManual, CicloAportante cicloAportante, Cartera cartera) {
        convertToDTO(solicitudGestionCobroManual);
        this.setIdPersona(cicloAportante.getIdPersona());
        this.setTipoSolicitante(cicloAportante.getTipoSolicitanteMovimientoAporteEnum());   
        this.setIdCartera(cartera.getIdCartera());
    }
	 
    /**
     * Método encargado de convertir el DTO a entidad
     * 
     * @return entidad La entidad equivalente
     */
    public SolicitudGestionCobroManual convertToEntity() {
        SolicitudGestionCobroManual solicitudGestionCobroManual = new SolicitudGestionCobroManual();
        solicitudGestionCobroManual.setIdSolicitudGestionCobroManual(this.getIdSolicitudGestionCobroManual());
        solicitudGestionCobroManual.setEstadoSolicitud(this.getEstadoSolicitud());
        solicitudGestionCobroManual.setIdCicloAportante(this.getIdCicloAportante());
        solicitudGestionCobroManual.setLineaCobro(this.getLineaCobro());
        Solicitud solicitudGlobal = super.convertToSolicitudEntity();
        solicitudGestionCobroManual.setSolicitudGlobal(solicitudGlobal);
        return solicitudGestionCobroManual;
    }
 
    /**
     * Método encargado de convertir una entidad
     * <code>SolicitudGestionCobroManual</code> al DTO
     * 
     * @param solicitudGestionCobroManual
     *        La entidad a convertir
     */
    public void convertToDTO(SolicitudGestionCobroManual solicitudGestionCobroManual) {
        if (solicitudGestionCobroManual.getSolicitudGlobal() != null) {
            super.convertToDTO(solicitudGestionCobroManual.getSolicitudGlobal());
        } 
        this.setIdSolicitudGestionCobroManual(solicitudGestionCobroManual.getIdSolicitudGestionCobroManual());
        this.setEstadoSolicitud(solicitudGestionCobroManual.getEstadoSolicitud());
        this.setIdCicloAportante(solicitudGestionCobroManual.getIdCicloAportante());
        this.setLineaCobro(solicitudGestionCobroManual.getLineaCobro());
    }

    /**
     * Método que retorna el valor de idSolicitudGestionCobroManual.
     * @return valor de idSolicitudGestionCobroManual.
     */
    public Long getIdSolicitudGestionCobroManual() {
        return idSolicitudGestionCobroManual;
    }

    /**
     * Método que retorna el valor de idCicloCartera.
     * @return valor de idCicloCartera.
     */
    public Long getIdCicloAportante() {
        return idCicloAportante;
    }

    /**
     * Método que retorna el valor de estadoSolicitud.
     * @return valor de estadoSolicitud.
     */
    public EstadoFiscalizacionEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * Método encargado de modificar el valor de idSolicitudGestionCobroManual.
     * @param valor
     *        para modificar idSolicitudGestionCobroManual.
     */
    public void setIdSolicitudGestionCobroManual(Long idSolicitudGestionCobroManual) {
        this.idSolicitudGestionCobroManual = idSolicitudGestionCobroManual;
    }

    /**
     * Método encargado de modificar el valor de idCicloCartera.
     * @param valor
     *        para modificar idCicloCartera.
     */
    public void setIdCicloAportante(Long idCicloAportante) {
        this.idCicloAportante = idCicloAportante;
    }

    /**
     * Método encargado de modificar el valor de estadoSolicitud.
     * @param valor
     *        para modificar estadoSolicitud.
     */
    public void setEstadoSolicitud(EstadoFiscalizacionEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * Método que retorna el valor de lineaCobro.
     * @return valor de lineaCobro.
     */
    public TipoLineaCobroEnum getLineaCobro() {
        return lineaCobro;
    }

    /**
     * Método encargado de modificar el valor de lineaCobro.
     * @param valor
     *        para modificar lineaCobro.
     */
    public void setLineaCobro(TipoLineaCobroEnum lineaCobro) {
        this.lineaCobro = lineaCobro;
    }

    /**
     * Método que retorna el valor de idPersona.
     * @return valor de idPersona.
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * Método que retorna el valor de tipoSolicitante.
     * @return valor de tipoSolicitante.
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * Método encargado de modificar el valor de idPersona.
     * @param valor
     *        para modificar idPersona.
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * Método encargado de modificar el valor de tipoSolicitante.
     * @param valor
     *        para modificar tipoSolicitante.
     */
    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * Método que retorna el valor de actividad.
     * @return valor de actividad.
     */
    public TipoAccionCobroEnum getActividad() {
        return actividad;
    }

    /**
     * Método encargado de modificar el valor de actividad.
     * @param valor para modificar actividad.
     */
    public void setActividad(TipoAccionCobroEnum actividad) {
        this.actividad = actividad;
    }

	/**Obtiene el valor de idCartera
	 * @return El valor de idCartera
	 */
	public Long getIdCartera() {
		return idCartera;
	}

	/** Establece el valor de idCartera
	 * @param idCartera El valor de idCartera por asignar
	 */
	public void setIdCartera(Long idCartera) {
		this.idCartera = idCartera;
	}    
    
}
