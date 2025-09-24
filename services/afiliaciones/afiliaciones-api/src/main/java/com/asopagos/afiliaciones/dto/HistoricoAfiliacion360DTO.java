package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;

public class HistoricoAfiliacion360DTO implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String idInstanciaProceso;
    private String numeroRadicado;
    private Long idSolicitud;
    private String fechaIngreso;
    private String fechaRetiro;
    private CanalRecepcionEnum canalAfiliacion;
    private MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion; 

    /**
     * 
     */
    public HistoricoAfiliacion360DTO() {
    }

    /**
     * @param idInstanciaProceso
     * @param numeroRadicado
     * @param fechaIngreso
     * @param fechaRetiro
     * @param canalAfiliacion
     */
    public HistoricoAfiliacion360DTO(String idInstanciaProceso, String numeroRadicado, String fechaIngreso, String fechaRetiro,
            CanalRecepcionEnum canalAfiliacion, MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion) {
        this.idInstanciaProceso = idInstanciaProceso;
        this.numeroRadicado = numeroRadicado;
        this.fechaIngreso = fechaIngreso;
        this.fechaRetiro = fechaRetiro;
        this.canalAfiliacion = canalAfiliacion;
        this.motivoDesafiliacion = motivoDesafiliacion;
    }

    /**
     * @return the idInstanciaProceso
     */
    public String getIdInstanciaProceso() {
        return idInstanciaProceso;
    }
    
    /**
     * @param idInstanciaProceso the idInstanciaProceso to set
     */
    public void setIdInstanciaProceso(String idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }
    
    /**
     * @return the numeroRadicado
     */
    public String getNumeroRadicado() {
        return numeroRadicado;
    }
    
    /**
     * @param numeroRadicado the numeroRadicado to set
     */
    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }
    
    /**
     * @return the fechaIngreso
     */
    public String getFechaIngreso() {
        return fechaIngreso;
    }
    
    /**
     * @param fechaIngreso the fechaIngreso to set
     */
    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    
    /**
     * @return the fechaRetiro
     */
    public String getFechaRetiro() {
        return fechaRetiro;
    }
    
    /**
     * @param fechaRetiro the fechaRetiro to set
     */
    public void setFechaRetiro(String fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }
    
    /**
     * @return the canalAfiliacion
     */
    public CanalRecepcionEnum getCanalAfiliacion() {
        return canalAfiliacion;
    }
    
    /**
     * @param canalAfiliacion the canalAfiliacion to set
     */
    public void setCanalAfiliacion(CanalRecepcionEnum canalAfiliacion) {
        this.canalAfiliacion = canalAfiliacion;
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
     * @return the motivoDesafiliacion
     */
    public MotivoDesafiliacionAfiliadoEnum getMotivoDesafiliacion() {
        return motivoDesafiliacion;
    }

    /**
     * @param motivoDesafiliacion the motivoDesafiliacion to set
     */
    public void setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion) {
        this.motivoDesafiliacion = motivoDesafiliacion;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((canalAfiliacion == null) ? 0 : canalAfiliacion.hashCode());
		result = prime * result + ((fechaIngreso == null) ? 0 : fechaIngreso.hashCode());
		result = prime * result + ((fechaRetiro == null) ? 0 : fechaRetiro.hashCode());
		result = prime * result + ((idInstanciaProceso == null) ? 0 : idInstanciaProceso.hashCode());
		result = prime * result + ((idSolicitud == null) ? 0 : idSolicitud.hashCode());
		result = prime * result + ((motivoDesafiliacion == null) ? 0 : motivoDesafiliacion.hashCode());
		result = prime * result + ((numeroRadicado == null) ? 0 : numeroRadicado.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistoricoAfiliacion360DTO other = (HistoricoAfiliacion360DTO) obj;
		if (canalAfiliacion != other.canalAfiliacion)
			return false;
		if (fechaIngreso == null) {
			if (other.fechaIngreso != null)
				return false;
		} else if (!fechaIngreso.equals(other.fechaIngreso))
			return false;
		if (fechaRetiro == null) {
			if (other.fechaRetiro != null)
				return false;
		} else if (!fechaRetiro.equals(other.fechaRetiro))
			return false;
		if (idInstanciaProceso == null) {
			if (other.idInstanciaProceso != null)
				return false;
		} else if (!idInstanciaProceso.equals(other.idInstanciaProceso))
			return false;
		if (idSolicitud == null) {
			if (other.idSolicitud != null)
				return false;
		} else if (!idSolicitud.equals(other.idSolicitud))
			return false;
		if (motivoDesafiliacion != other.motivoDesafiliacion)
			return false;
		if (numeroRadicado == null) {
			if (other.numeroRadicado != null)
				return false;
		} else if (!numeroRadicado.equals(other.numeroRadicado))
			return false;
		return true;
	}
    
    
}
