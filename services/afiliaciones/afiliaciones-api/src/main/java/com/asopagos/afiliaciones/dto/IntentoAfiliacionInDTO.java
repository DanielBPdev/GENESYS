package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.SolicitudDTO;
import com.asopagos.enumeraciones.afiliaciones.CausaIntentoFallidoAfiliacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción:</b> DTO para el registro de intentos de afiliación
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@XmlRootElement
public class IntentoAfiliacionInDTO implements Serializable {
    
    @NotNull
    private Long idSolicitud;
    
    private Date fechaInicioProceso;
    
    @NotNull
    private CausaIntentoFallidoAfiliacionEnum causaIntentoFallido;
    
    @NotNull
    private TipoTransaccionEnum tipoTransaccion;    

    private List<Long> idsRequsitos;
    
    private SolicitudDTO solicitud;
    
    /**
     * Tipo de identificacion de la persona asociada a la empresa/empleador
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    
    /**
     * Número de identificacion de la persona asociada a la empresa/empleador
     */
    private String numeroIdentificacion;
    /**
	 * @return the solicitud
	 */
	public SolicitudDTO getSolicitud() {
		return solicitud;
	}

	/**
	 * @param solicitud the solicitud to set
	 */
	public void setSolicitud(SolicitudDTO solicitud) {
		this.solicitud = solicitud;
	}

	/**
     * @return the idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud the idSolicitud to set
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * @return the fechaInicioProceso
     */
    public Date getFechaInicioProceso() {
        return fechaInicioProceso;
    }

    /**
     * @param fechaInicioProceso the fechaInicioProceso to set
     */
    public void setFechaInicioProceso(Date fechaInicioProceso) {
        this.fechaInicioProceso = fechaInicioProceso;
    }

    /**
     * @return the causaIntentoFallido
     */
    public CausaIntentoFallidoAfiliacionEnum getCausaIntentoFallido() {
        return causaIntentoFallido;
    }

    /**
     * @param causaIntentoFallido the causaIntentoFallido to set
     */
    public void setCausaIntentoFallido(CausaIntentoFallidoAfiliacionEnum causaIntentoFallido) {
        this.causaIntentoFallido = causaIntentoFallido;
    }

    /**
     * @return the tipoTransaccion
     */
    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * @param tipoTransaccion the tipoTransaccion to set
     */
    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    /**
     * @return the idsRequsitos
     */
    public List<Long> getIdsRequsitos() {
        return idsRequsitos;
    }

    /**
     * @param idsRequsitos the idsRequsitos to set
     */
    public void setIdsRequsitos(List<Long> idsRequsitos) {
        this.idsRequsitos = idsRequsitos;
    }

	/**
	 * @return the tipoIdentificacion
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * @param tipoIdentificacion the tipoIdentificacion to set
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * @return the numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * @param numeroIdentificacion the numeroIdentificacion to set
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}
    
    

}
