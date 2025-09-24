package com.asopagos.dto;

import java.util.List;
import com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum;
import com.asopagos.enumeraciones.core.TipoProcesosMasivosEnum;

import java.io.Serializable;

public class DatosArchivoProcesosProcesosReporteDTO implements Serializable {

    private static final long serialVersionUID = 5165142646354284L;

    private List<String> cabeceras;
    private TipoProcesosMasivosEnum tipoProceso;
	private EstadoCargueMasivoEnum estado;
	private Long fechaFin;
	private Long fechaInicio;
    private String error;

    public List<String> getCabeceras() {
        return this.cabeceras;
    }

    public void setCabeceras(List<String> cabeceras) {
        this.cabeceras = cabeceras;
    }

/**
	 * Retorna el resultado del llamado al servicio
	 */


    public TipoProcesosMasivosEnum getTipoProceso() {
        return this.tipoProceso;
    }

    public void setTipoProceso(TipoProcesosMasivosEnum tipoProceso) {
        this.tipoProceso = tipoProceso;
    }
    
	 public void setEstado (EstadoCargueMasivoEnum estado){
		this.estado=estado;
	}
	
	public EstadoCargueMasivoEnum getEstado (){
		return estado;
	}
	 public void setFechaFin (Long fechaFin){
		this.fechaFin=fechaFin;
	}
	
	public Long getFechaFin (){
		return fechaFin;
	}
	 public void setFechaInicio (Long fechaInicio){
		this.fechaInicio=fechaInicio;
	}
	
	public Long getFechaInicio (){
		return fechaInicio;
	}
	public void setError (String error){
	   this.error=error;
   }
   
   public String getError (){
	   return error;
   }
    @Override
    public String toString() {
        return "{" +
            " cabeceras='" + getCabeceras() + "'" +
            ", proceso ='" + getTipoProceso() + "'" +
            ", estado='" + getEstado() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            "}";
    }
}