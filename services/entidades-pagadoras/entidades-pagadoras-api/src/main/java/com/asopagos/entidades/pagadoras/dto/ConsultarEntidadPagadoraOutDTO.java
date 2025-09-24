package com.asopagos.entidades.pagadoras.dto;

import com.asopagos.entidades.ccf.personas.EntidadPagadora;
import com.asopagos.enumeraciones.afiliaciones.TipoAfiliacionEntidadPagadoraEnum;
import com.asopagos.enumeraciones.core.HabilitadoInhabilitadoEnum;
import com.asopagos.enumeraciones.fovis.NombreCondicionEspecialEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción:</b> DTO para el servicio entidades pagadoras 
 * <b>Historia de Usuario:</b> HU-133
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ConsultarEntidadPagadoraOutDTO {
	
	private Long idEntidadPagadora;
	
	private TipoIdentificacionEnum tipoIdentificacion;
	
	private String numeroIdentificacion;
	
	private String razonSocial;
	
	private Boolean aportante;
	
	private HabilitadoInhabilitadoEnum estadoEntidadPagadora;
	
	private EstadoEmpleadorEnum estadoEmpleador;
	
	private TipoAfiliacionEntidadPagadoraEnum tipoAfiliacion;
	
    /**
     * Digito de verificación de la persona
     */
    private Short digitoVerificacion;

    public ConsultarEntidadPagadoraOutDTO() {
        super();
    }

    public ConsultarEntidadPagadoraOutDTO(EntidadPagadora entidadPagadora) {
        this.setTipoIdentificacion(entidadPagadora.getEmpresa().getPersona().getTipoIdentificacion());
        this.setNumeroIdentificacion(entidadPagadora.getEmpresa().getPersona().getNumeroIdentificacion());
        if (entidadPagadora.getEmpresa().getPersona().getRazonSocial() != null) {
            this.setRazonSocial(entidadPagadora.getEmpresa().getPersona().getRazonSocial());
        }
        else {
            StringBuilder nombreCompleto = new StringBuilder();
            nombreCompleto.append(entidadPagadora.getEmpresa().getPersona().getPrimerNombre() + " ");
            nombreCompleto.append(entidadPagadora.getEmpresa().getPersona().getSegundoNombre() != null
                    ? entidadPagadora.getEmpresa().getPersona().getSegundoNombre() + " " : "");
            nombreCompleto.append(entidadPagadora.getEmpresa().getPersona().getPrimerApellido() + " ");
            nombreCompleto.append(entidadPagadora.getEmpresa().getPersona().getSegundoApellido() != null
                    ? entidadPagadora.getEmpresa().getPersona().getSegundoApellido() : "");
            this.setRazonSocial(nombreCompleto.toString());
        }
        this.setDigitoVerificacion(entidadPagadora.getEmpresa().getPersona().getDigitoVerificacion());
        this.setAportante(entidadPagadora.getAportante());
        this.setEstadoEntidadPagadora(entidadPagadora.getEstadoEntidadPagadora());
        this.setIdEntidadPagadora(entidadPagadora.getIdEntidadPagadora());
        this.setTipoAfiliacion(entidadPagadora.getTipoAfiliacion());
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
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * @return the aportante
	 */
	public Boolean getAportante() {
		return aportante;
	}

	/**
	 * @param aportante the aportante to set
	 */
	public void setAportante(Boolean aportante) {
		this.aportante = aportante;
	}

	/**
	 * @return the estadoEntidadPagadora
	 */
	public HabilitadoInhabilitadoEnum getEstadoEntidadPagadora() {
		return estadoEntidadPagadora;
	}

	/**
	 * @param estadoEntidadPagadora the estadoEntidadPagadora to set
	 */
	public void setEstadoEntidadPagadora(HabilitadoInhabilitadoEnum estadoEntidadPagadora) {
		this.estadoEntidadPagadora = estadoEntidadPagadora;
	}

	/**
	 * @return the estadoEmpleador
	 */
	public EstadoEmpleadorEnum getEstadoEmpleador() {
		return estadoEmpleador;
	}

	/**
	 * @param estadoEmpleador the estadoEmpleador to set
	 */
	public void setEstadoEmpleador(EstadoEmpleadorEnum estadoEmpleador) {
		this.estadoEmpleador = estadoEmpleador;
	}

	/**
	 * @return the idEntidadPagadora
	 */
	public Long getIdEntidadPagadora() {
		return idEntidadPagadora;
	}

	/**
	 * @param idEntidadPagadora the idEntidadPagadora to set
	 */
	public void setIdEntidadPagadora(Long idEntidadPagadora) {
		this.idEntidadPagadora = idEntidadPagadora;
	}

	/**
	 * @return the tipoAfiliacion
	 */
	public TipoAfiliacionEntidadPagadoraEnum getTipoAfiliacion() {
		return tipoAfiliacion;
	}

	/**
	 * @param tipoAfiliacion the tipoAfiliacion to set
	 */
	public void setTipoAfiliacion(TipoAfiliacionEntidadPagadoraEnum tipoAfiliacion) {
		this.tipoAfiliacion = tipoAfiliacion;
	}

    /**
     * @return the digitoVerificacion
     */
    public Short getDigitoVerificacion() {
        return digitoVerificacion;
    }

    /**
     * @param digitoVerificacion the digitoVerificacion to set
     */
    public void setDigitoVerificacion(Short digitoVerificacion) {
        this.digitoVerificacion = digitoVerificacion;
    }

}
