package com.asopagos.entidades.pagadoras.dto;

import java.util.Date;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAsociacionPersonaEntidadPagadora;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudPersonaEntidadPagadoraEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoGestionSolicitudAsociacionEnum;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.entidadesPagadoras.ResultadoSolicitudPersonaEntidadPagadoraEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.PersonasUtils;

/**
 * <b>Descripción:</b> DTO para el servicio entidades pagadoras 
 * <b>Historia de Usuario:</b> HU-133
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class SolicitudAsociacionPersonaEntidadPagadoraDTO {
	
	private String numeroRadicado;
	
	private TipoIdentificacionEnum tipoIdentificacion;
	
	private String numeroIdentificacion;
	
	private String nombre;
	
	private String tipoAfiliacion;
	
	private EstadoAfiliadoEnum estadoPersona;
	
	private Date ultimaFechaNovedad;
	
	private EstadoActivoInactivoEnum estadoEnEntidadPagadora;
	
	private EstadoSolicitudPersonaEntidadPagadoraEnum estadoSolicitud;
	
	private TipoGestionSolicitudAsociacionEnum tipoGestion;
	
	private String identificadorArchivoPlano;
	
	private String identificadorArchivoCarta;
	
	private ResultadoSolicitudPersonaEntidadPagadoraEnum resultadoSolicitud;
	
	private String numeroAfiliado;
	
	private Long idSolicitudGlobal;
	
	private String consecutivo;

    public SolicitudAsociacionPersonaEntidadPagadoraDTO() {
        super();
    }

    public SolicitudAsociacionPersonaEntidadPagadoraDTO(SolicitudAsociacionPersonaEntidadPagadora solicitudAsociacionPersonaEntidadPagadora,
            Solicitud solicitud, RolAfiliado rolAfiliado, Persona persona) {
        this.setConsecutivo(solicitudAsociacionPersonaEntidadPagadora.getConsecutivo());
        this.setEstadoSolicitud(solicitudAsociacionPersonaEntidadPagadora.getEstado());
        this.setTipoGestion(solicitudAsociacionPersonaEntidadPagadora.getTipoGestion());
        this.setIdentificadorArchivoPlano(solicitudAsociacionPersonaEntidadPagadora.getIdentificadorArchivoPlano());
        this.setIdentificadorArchivoCarta(solicitudAsociacionPersonaEntidadPagadora.getIdentificadorArchivoCarta());
        this.setUltimaFechaNovedad(solicitudAsociacionPersonaEntidadPagadora.getFechaGestion());
        
        this.setIdSolicitudGlobal(solicitud.getIdSolicitud());
        this.setNumeroRadicado(solicitud.getNumeroRadicacion());
        
        this.setNumeroAfiliado(rolAfiliado.getIdentificadorAnteEntidadPagadora());
        this.setTipoAfiliacion(rolAfiliado.getTipoAfiliado().getDescripcion());
        this.setEstadoPersona(rolAfiliado.getEstadoAfiliado());
        this.setEstadoEnEntidadPagadora(rolAfiliado.getEstadoEnEntidadPagadora());
        
        this.setTipoIdentificacion(persona.getTipoIdentificacion());
        this.setNumeroIdentificacion(persona.getNumeroIdentificacion());
        this.setNombre(PersonasUtils.obtenerNombreORazonSocial(persona));
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
	 * @return the tipoAfiliacion
	 */
	public String getTipoAfiliacion() {
		return tipoAfiliacion;
	}

	/**
	 * @param tipoAfiliacion the tipoAfiliacion to set
	 */
	public void setTipoAfiliacion(String tipoAfiliacion) {
		this.tipoAfiliacion = tipoAfiliacion;
	}

	/**
	 * @return the estadoPersona
	 */
	public EstadoAfiliadoEnum getEstadoPersona() {
		return estadoPersona;
	}

	/**
	 * @param estadoPersona the estadoPersona to set
	 */
	public void setEstadoPersona(EstadoAfiliadoEnum estadoPersona) {
		this.estadoPersona = estadoPersona;
	}

	/**
	 * @return the ultimaFechaNovedad
	 */
	public Date getUltimaFechaNovedad() {
		return ultimaFechaNovedad;
	}

	/**
	 * @param ultimaFechaNovedad the ultimaFechaNovedad to set
	 */
	public void setUltimaFechaNovedad(Date ultimaFechaNovedad) {
		this.ultimaFechaNovedad = ultimaFechaNovedad;
	}

	/**
	 * @return the estadoEnEntidadPagadora
	 */
	public EstadoActivoInactivoEnum getEstadoEnEntidadPagadora() {
		return estadoEnEntidadPagadora;
	}

	/**
	 * @param estadoEnEntidadPagadora the estadoEnEntidadPagadora to set
	 */
	public void setEstadoEnEntidadPagadora(EstadoActivoInactivoEnum estadoEnEntidadPagadora) {
		this.estadoEnEntidadPagadora = estadoEnEntidadPagadora;
	}

	/**
	 * @return the estadoSolicitud
	 */
	public EstadoSolicitudPersonaEntidadPagadoraEnum getEstadoSolicitud() {
		return estadoSolicitud;
	}

	/**
	 * @param estadoSolicitud the estadoSolicitud to set
	 */
	public void setEstadoSolicitud(EstadoSolicitudPersonaEntidadPagadoraEnum estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	/**
	 * @return the tipoGestion
	 */
	public TipoGestionSolicitudAsociacionEnum getTipoGestion() {
		return tipoGestion;
	}

	/**
	 * @param tipoGestion the tipoGestion to set
	 */
	public void setTipoGestion(TipoGestionSolicitudAsociacionEnum tipoGestion) {
		this.tipoGestion = tipoGestion;
	}

	/**
	 * @return the identificadorArchivoPlano
	 */
	public String getIdentificadorArchivoPlano() {
		return identificadorArchivoPlano;
	}

	/**
	 * @param identificadorArchivoPlano the identificadorArchivoPlano to set
	 */
	public void setIdentificadorArchivoPlano(String identificadorArchivoPlano) {
		this.identificadorArchivoPlano = identificadorArchivoPlano;
	}

	/**
	 * @return the identificadorArchivoCarta
	 */
	public String getIdentificadorArchivoCarta() {
		return identificadorArchivoCarta;
	}

	/**
	 * @param identificadorArchivoCarta the identificadorArchivoCarta to set
	 */
	public void setIdentificadorArchivoCarta(String identificadorArchivoCarta) {
		this.identificadorArchivoCarta = identificadorArchivoCarta;
	}

	/**
	 * @return the resultadoSolicitud
	 */
	public ResultadoSolicitudPersonaEntidadPagadoraEnum getResultadoSolicitud() {
		return resultadoSolicitud;
	}

	/**
	 * @param resultadoSolicitud the resultadoSolicitud to set
	 */
	public void setResultadoSolicitud(ResultadoSolicitudPersonaEntidadPagadoraEnum resultadoSolicitud) {
		this.resultadoSolicitud = resultadoSolicitud;
	}

	/**
	 * @return the numeroAfiliado
	 */
	public String getNumeroAfiliado() {
		return numeroAfiliado;
	}

	/**
	 * @param numeroAfiliado the numeroAfiliado to set
	 */
	public void setNumeroAfiliado(String numeroAfiliado) {
		this.numeroAfiliado = numeroAfiliado;
	}

    /**
     * @return the idSolicitudGlobal
     */
    public Long getIdSolicitudGlobal() {
        return idSolicitudGlobal;
    }

    /**
     * @param idSolicitudGlobal the idSolicitudGlobal to set
     */
    public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
        this.idSolicitudGlobal = idSolicitudGlobal;
    }

    /**
     * Método que retorna el valor de consecutivo.
     * @return valor de consecutivo.
     */
    public String getConsecutivo() {
        return consecutivo;
    }

    /**
     * Método encargado de modificar el valor de consecutivo.
     * @param valor para modificar consecutivo.
     */
    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }
	
}
