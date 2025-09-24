package com.asopagos.dto.afiliaciones;

import java.util.Date;

/**
 * <b>Descripción:</b> DTO para los items de detalle del DTO RemisionBackDTO 
 * del servicio generarListadoSolicitudesRemisionBack HU
 * <b>Historia de Usuario:</b> HU-086
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ItemDetalleRemisionBackDTO {
	
	private String numeroRadicado;
	
	private String numeroIdentificacionSolicitante;
	
	private String nombre;
	
	private Date fechaRadicacion;
	
	private String nombreDocumentoRequisito;
	
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
	 * @return the numeroIdentificacionSolicitante
	 */
	public String getNumeroIdentificacionSolicitante() {
		return numeroIdentificacionSolicitante;
	}

	/**
	 * @param numeroIdentificacionSolicitante the numeroIdentificacionSolicitante to set
	 */
	public void setNumeroIdentificacionSolicitante(String numeroIdentificacionSolicitante) {
		this.numeroIdentificacionSolicitante = numeroIdentificacionSolicitante;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the fechaRadicacion
	 */
	public Date getFechaRadicacion() {
		return fechaRadicacion;
	}

	/**
	 * @param fechaRadicacion the fechaRadicacion to set
	 */
	public void setFechaRadicacion(Date fechaRadicacion) {
		this.fechaRadicacion = fechaRadicacion;
	}

	/**
	 * @return the nombreDocumentoRequisito
	 */
	public String getNombreDocumentoRequisito() {
		return nombreDocumentoRequisito;
	}

	/**
	 * @param nombreDocumentoRequisito the nombreDocumentoRequisito to set
	 */
	public void setNombreDocumentoRequisito(String nombreDocumentoRequisito) {
		this.nombreDocumentoRequisito = nombreDocumentoRequisito;
	}

    @Override
    public String toString() {
        return "ItemDetalleRemisionBackDTO [numeroRadicado=" + numeroRadicado + ", numeroIdentificacionSolicitante="
                + numeroIdentificacionSolicitante + ", nombre=" + nombre + ", fechaRadicacion=" + fechaRadicacion
                + ", nombreDocumentoRequisito=" + nombreDocumentoRequisito + "]";
    }
	
	
}
