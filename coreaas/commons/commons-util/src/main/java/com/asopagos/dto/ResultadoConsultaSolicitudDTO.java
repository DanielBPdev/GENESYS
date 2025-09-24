package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.EstadoTareaEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;

/**
 * Clase que contiene la información de respuesta del servicio de consulta de
 * solicitudes basado en filtros de la solicitud
 * 
 * @author jbuitrago
 * 
 */
public class ResultadoConsultaSolicitudDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	/*
	 * Identificador único de la Solicitud (Tabla general)
	 */
	private BigInteger idSolicitud;
	/*
	 * Usuario que radica la solicitud
	 */
	private String usuarioRadicacion;
	/*
	 * Fecha de radicacion de la solcitud
	 */
	private Date fechaRadicacion;
	/*
	 * Tipo de identificación del solicitante
	 */
	private String tipoIdentificacionSolicitante;
	/*
	 * Numero de identificacion del solicitante
	 */
	private String numeroIdentificacionSolicitante;
	/*
	 * Nombre o razon social del solicitante
	 */
	private String nombreRazonSocialSolictante;
	/*
	 * Identificador de la instancia del proceso, necesario para la consulta en
	 * el BPM
	 */
	private String idInstanciaProceso;
	/*
	 * Identificador unico de la persona, necesario para los filtros por persona
	 * de la solicitud
	 */
	private BigInteger idPersona;
	/*
	 * Estado actual registrado en base de datos de la solicitud
	 */
	private String estadoSolicitud;

	/*
	 * Enumeración del proceso al cual esta asociada la solicitud
	 */
	private ProcesoEnum proceso;
	/*
	 * Identificador de la sucursal de radicación
	 */
	private String sucursalRadicacion;
	/*
	 * Nombre identificador del usuario que tiene asignada la solicitud como
	 * tarea en el BPM
	 */
	private String usuarioAsignado;
	/*
	 * Tarea en la que se encuentra la solicitud en el BPM
	 */
	private EstadoTareaEnum tarea;
	/*
	 * Numero deracidaco de la solicitud
	 */
	private String numeroRadicacion;
	/*
	 * Fecha de creacion de la solciitud - añadido para HU 114
	 */
	private Date fechaCreacion;
	/*
	 * Clasficiacion del tipo de solicitante - - añadido para HU 114
	 */
	private String clasificacion;
	/*
	 * Tipo de tranasaccion asociada a la solicitud
	 */
	private String tipoTransaccion;
	
	/*
	 * Nombre de la tarea activa del proceso  
	 */
    private String nombreTarea;
	
    /**
     * Canal de registro de la solicitud
     */
    private CanalRecepcionEnum canal;

	/*
	 * Getter - Setter
	 */
	public BigInteger getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(BigInteger idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public ProcesoEnum getProceso() {
		return proceso;
	}

	public void setProceso(ProcesoEnum proceso) {
		this.proceso = proceso;
	}

	public BigInteger getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(BigInteger idPersona) {
		this.idPersona = idPersona;
	}

	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public String getSucursalRadicacion() {
		return sucursalRadicacion;
	}

	public void setSucursalRadicacion(String sucursalRadicacion) {
		this.sucursalRadicacion = sucursalRadicacion;
	}

	public String getUsuarioRadicacion() {
		return usuarioRadicacion;
	}

	public void setUsuarioRadicacion(String usuarioRadicacion) {
		this.usuarioRadicacion = usuarioRadicacion;
	}

	public Date getFechaRadicacion() {
		return fechaRadicacion;
	}

	public void setFechaRadicacion(Date fechaRadicacion) {
		this.fechaRadicacion = fechaRadicacion;
	}

	public String getUsuarioAsignado() {
		return usuarioAsignado;
	}

	public void setUsuarioAsignado(String usuarioAsignado) {
		this.usuarioAsignado = usuarioAsignado;
	}

	public String getTipoIdentificacionSolicitante() {
		return tipoIdentificacionSolicitante;
	}

	public void setTipoIdentificacionSolicitante(
			String tipoIdentificacionSolicitante) {
		this.tipoIdentificacionSolicitante = tipoIdentificacionSolicitante;
	}

	public String getNumeroIdentificacionSolicitante() {
		return numeroIdentificacionSolicitante;
	}

	public void setNumeroIdentificacionSolicitante(
			String numeroIdentificacionSolicitante) {
		this.numeroIdentificacionSolicitante = numeroIdentificacionSolicitante;
	}

	public String getNombreRazonSocialSolictante() {
		return nombreRazonSocialSolictante;
	}

	public void setNombreRazonSocialSolictante(
			String nombreRazonSocialSolictante) {
		this.nombreRazonSocialSolictante = nombreRazonSocialSolictante;
	}

	public String getIdInstanciaProceso() {
		return idInstanciaProceso;
	}

	public void setIdInstanciaProceso(String idInstanciaProceso) {
		this.idInstanciaProceso = idInstanciaProceso;
	}

	public EstadoTareaEnum getTarea() {
		return tarea;
	}

	public void setTarea(EstadoTareaEnum tarea) {
		this.tarea = tarea;
	}

	public String getNumeroRadicacion() {
		return numeroRadicacion;
	}

	public void setNumeroRadicacion(String numeroRadicacion) {
		this.numeroRadicacion = numeroRadicacion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public String getTipoTransaccion() {
		return tipoTransaccion;
	}

	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}
	
	public String getNombreTarea() {
        return nombreTarea;
    }
	
    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public CanalRecepcionEnum getCanal() {
        return canal;
    }

    public void setCanal(CanalRecepcionEnum canal) {
        this.canal = canal;
    }

	/*
	 * Constructores de la clase
	 */
	public ResultadoConsultaSolicitudDTO() {
	}

	public ResultadoConsultaSolicitudDTO(
			Object[] ResultadoConsultaSolicitudObject) {
		
		if(ResultadoConsultaSolicitudObject.length == 10){
			this.idSolicitud = ResultadoConsultaSolicitudObject[0] != null ? (BigInteger) ResultadoConsultaSolicitudObject[0]
					: null;
			
			this.usuarioRadicacion = ResultadoConsultaSolicitudObject[1] != null ? (String) ResultadoConsultaSolicitudObject[1]
					: null;
			
			this.fechaRadicacion = ResultadoConsultaSolicitudObject[2] != null ? (Date) ResultadoConsultaSolicitudObject[2]
					: null;
			
			this.idInstanciaProceso = ResultadoConsultaSolicitudObject[3] != null ? ResultadoConsultaSolicitudObject[3]
					.toString() : null;
					
			this.numeroRadicacion = ResultadoConsultaSolicitudObject[4] != null ? ResultadoConsultaSolicitudObject[4]
					.toString() : null;

			this.fechaCreacion = ResultadoConsultaSolicitudObject[5] != null ? (Date) ResultadoConsultaSolicitudObject[5]
					: null;

			this.clasificacion = ResultadoConsultaSolicitudObject[6] != null ? ResultadoConsultaSolicitudObject[6]
					.toString() : null;

			this.tipoTransaccion = ResultadoConsultaSolicitudObject[7] != null ? ResultadoConsultaSolicitudObject[7]
					.toString() : null;

			this.canal = ResultadoConsultaSolicitudObject[8] != null ? 
					CanalRecepcionEnum.valueOf(ResultadoConsultaSolicitudObject[8].toString()) : null;

			this.estadoSolicitud = ResultadoConsultaSolicitudObject[9] != null ? ResultadoConsultaSolicitudObject[9]
					.toString() : null;
		}
		else{
			this.idSolicitud = ResultadoConsultaSolicitudObject[0] != null ? (BigInteger) ResultadoConsultaSolicitudObject[0]
					: null;
			this.usuarioRadicacion = ResultadoConsultaSolicitudObject[1] != null ? (String) ResultadoConsultaSolicitudObject[1]
					: null;
			this.fechaRadicacion = ResultadoConsultaSolicitudObject[2] != null ? (Date) ResultadoConsultaSolicitudObject[2]
					: null;

			this.tipoIdentificacionSolicitante = ResultadoConsultaSolicitudObject[3] != null ? ResultadoConsultaSolicitudObject[3]
					.toString() : null;

			this.numeroIdentificacionSolicitante = ResultadoConsultaSolicitudObject[4] != null ? ResultadoConsultaSolicitudObject[4]
					.toString() : null;

			if (ResultadoConsultaSolicitudObject[5] != null) {
				this.nombreRazonSocialSolictante = ResultadoConsultaSolicitudObject[5]
						.toString();
			} else {
				String primerNombre = ResultadoConsultaSolicitudObject[6] != null ? " "
						+ ResultadoConsultaSolicitudObject[6].toString() + " "
						: "";
				String segundoNombre = ResultadoConsultaSolicitudObject[7] != null ? " "
						+ ResultadoConsultaSolicitudObject[7].toString() + " "
						: "";
				String primerApellido = ResultadoConsultaSolicitudObject[8] != null ? " "
						+ ResultadoConsultaSolicitudObject[8].toString() + " "
						: "";
				String segundoApellido = ResultadoConsultaSolicitudObject[9] != null ? " "
						+ ResultadoConsultaSolicitudObject[9].toString() + " "
						: "";
				this.nombreRazonSocialSolictante = primerNombre + segundoNombre
						+ primerApellido + segundoApellido;
				this.nombreRazonSocialSolictante = this.nombreRazonSocialSolictante
						.trim();
			}

			this.idInstanciaProceso = ResultadoConsultaSolicitudObject[10] != null ? ResultadoConsultaSolicitudObject[10]
					.toString() : null;

			this.idPersona = ResultadoConsultaSolicitudObject[11] != null ? (BigInteger) ResultadoConsultaSolicitudObject[11]
					: null;

			this.numeroRadicacion = ResultadoConsultaSolicitudObject[12] != null ? ResultadoConsultaSolicitudObject[12]
					.toString() : null;

			this.fechaCreacion = ResultadoConsultaSolicitudObject[13] != null ? (Date) ResultadoConsultaSolicitudObject[13]
					: null;

			this.clasificacion = ResultadoConsultaSolicitudObject[14] != null ? ResultadoConsultaSolicitudObject[14]
					.toString() : null;

			this.tipoTransaccion = ResultadoConsultaSolicitudObject[15] != null ? ResultadoConsultaSolicitudObject[15]
					.toString() : null;

			this.canal = ResultadoConsultaSolicitudObject[16] != null ? 
					CanalRecepcionEnum.valueOf(ResultadoConsultaSolicitudObject[16].toString()) : null;

			this.estadoSolicitud = ResultadoConsultaSolicitudObject[17] != null ? ResultadoConsultaSolicitudObject[17]
					.toString() : null;
		}
		
	}

}
