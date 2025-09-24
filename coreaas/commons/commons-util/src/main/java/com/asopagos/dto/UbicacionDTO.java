package com.asopagos.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.core.TipoUbicacionEnum;
import com.asopagos.enumeraciones.personas.SectorUbicacionEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * <b>Descripción:</b> DTO que transporta los datos de una ubicación
 * GLPI 49270: Fovis - Agregar segundo correo electronico.
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class UbicacionDTO implements Serializable, Cloneable {

	private Long idUbicacion;

	private Short idDepartamento;

	private Short idMunicipio;

	private String direccion;

	private String codigoPostal;

	private String telefonoFijo;

	private String indicativoTelefonoFijo;

	private String telefonoCelular;

	private String correoElectronico;

	private Boolean autorizacionEnvioEmail;
	
	private String descripcionIndicacion;
	
	private TipoUbicacionEnum tipoUbicacion; 
	
	/**
     * Sector Ubicación asociado al grupo Familiar
     */
    private SectorUbicacionEnum sectorUbicacion;
    
    private String correoElectronicoSecundario;

	
	/**
	 * Constructir de la clase
	 */
	public UbicacionDTO (){
	    
	}
	/**
	 * Metodo que obtiene la ubicacion con su respectivo tipo
	 */
	public UbicacionDTO (Ubicacion ubicacion, TipoUbicacionEnum tipoUbicacion){
	    if (ubicacion != null) {
            this.setIdUbicacion(ubicacion.getIdUbicacion());
            this.setCodigoPostal(ubicacion.getCodigoPostal());
            this.setCorreoElectronico(ubicacion.getEmail());
            this.setCorreoElectronicoSecundario(ubicacion.getEmailSecundario());
            this.setDireccion(ubicacion.getDireccionFisica());
            if (ubicacion.getMunicipio() != null) {
                if (ubicacion.getMunicipio().getIdDepartamento() != null) {
                    this.setIdDepartamento(ubicacion.getMunicipio().getIdDepartamento());
                }
                if (ubicacion.getMunicipio().getIdMunicipio() != null) {
                    this.setIdMunicipio(ubicacion.getMunicipio().getIdMunicipio());
                }
            }
            this.setIndicativoTelefonoFijo(ubicacion.getIndicativoTelFijo());
            this.setTelefonoCelular(ubicacion.getTelefonoCelular());
            this.setTelefonoFijo(ubicacion.getTelefonoFijo());
            this.setAutorizacionEnvioEmail(ubicacion.getAutorizacionEnvioEmail());
            this.setDescripcionIndicacion(ubicacion.getDescripcionIndicacion());
            this.setTipoUbicacion(tipoUbicacion);
            this.setSectorUbicacion(ubicacion.getSectorUbicacion());
        }
	}
	
	/**
     * Asocia los datos del DTO a la Entidad
     * @return Ubicacion
     */
    public Ubicacion convertToEntity() {
    	Ubicacion ubicacion = new Ubicacion();
    	ubicacion.setAutorizacionEnvioEmail(this.getAutorizacionEnvioEmail());
    	ubicacion.setCodigoPostal(this.getCodigoPostal());
    	ubicacion.setDescripcionIndicacion(this.getDescripcionIndicacion());
    	ubicacion.setDireccionFisica(this.getDireccion());
    	ubicacion.setEmail(this.getCorreoElectronico());
        ubicacion.setEmailSecundario(this.getCorreoElectronicoSecundario());
    	ubicacion.setIdUbicacion(this.getIdUbicacion());
    	ubicacion.setIndicativoTelFijo(this.getIndicativoTelefonoFijo());
    	if (this.getIdMunicipio() != null) {
    		Municipio municipio = new Municipio();
    		municipio.setIdMunicipio(this.getIdMunicipio());
    		ubicacion.setMunicipio(municipio);	
    	}
    	ubicacion.setTelefonoCelular(this.getTelefonoCelular());
    	ubicacion.setTelefonoFijo(this.getTelefonoFijo());
    	ubicacion.setSectorUbicacion(this.getSectorUbicacion());
    	return ubicacion;
    }

	/**
	 * Método encargado de crear un objeto UbicacionDTO mediante la entidad
	 * Ubicacion
	 * 
	 * @param ubicacion,
	 *            entidad Ubicacion
	 * @return el objeto dto Ubicacion
	 */
	public static UbicacionDTO obtenerUbicacionDTO(Ubicacion ubicacion) {
		if (ubicacion != null) {
			UbicacionDTO ubicacionDTO = new UbicacionDTO();
			ubicacionDTO.setIdUbicacion(ubicacion.getIdUbicacion());
			ubicacionDTO.setCodigoPostal(ubicacion.getCodigoPostal());
			ubicacionDTO.setCorreoElectronico(ubicacion.getEmail());
            ubicacionDTO.setCorreoElectronicoSecundario(ubicacion.getEmailSecundario());
			ubicacionDTO.setDireccion(ubicacion.getDireccionFisica());
			if (ubicacion.getMunicipio() != null) {
				if (ubicacion.getMunicipio().getIdDepartamento() != null) {
					ubicacionDTO.setIdDepartamento(ubicacion.getMunicipio().getIdDepartamento());
				}
				if (ubicacion.getMunicipio().getIdMunicipio() != null) {
					ubicacionDTO.setIdMunicipio(ubicacion.getMunicipio().getIdMunicipio());
				}
			}
			ubicacionDTO.setIndicativoTelefonoFijo(ubicacion.getIndicativoTelFijo());
			ubicacionDTO.setTelefonoCelular(ubicacion.getTelefonoCelular());
			ubicacionDTO.setTelefonoFijo(ubicacion.getTelefonoFijo());
			ubicacionDTO.setAutorizacionEnvioEmail(ubicacion.getAutorizacionEnvioEmail());
			ubicacionDTO.setDescripcionIndicacion(ubicacion.getDescripcionIndicacion());
			ubicacionDTO.setSectorUbicacion(ubicacion.getSectorUbicacion());
			return ubicacionDTO;
		}
		return null;
	}
	
	public static UbicacionDTO obtenerUbicacionDTO(Ubicacion ubicacion, Municipio municipio) {
		if (ubicacion != null) {
			UbicacionDTO ubicacionDTO = new UbicacionDTO();
			ubicacionDTO.setIdUbicacion(ubicacion.getIdUbicacion());
			ubicacionDTO.setCodigoPostal(ubicacion.getCodigoPostal());
			ubicacionDTO.setCorreoElectronico(ubicacion.getEmail());
            ubicacionDTO.setCorreoElectronicoSecundario(ubicacion.getEmailSecundario());
			ubicacionDTO.setDireccion(ubicacion.getDireccionFisica());
			if (municipio != null) {
				if (municipio.getIdDepartamento() != null) {
					ubicacionDTO.setIdDepartamento(municipio.getIdDepartamento());
				}
				if (municipio.getIdMunicipio() != null) {
					ubicacionDTO.setIdMunicipio(municipio.getIdMunicipio());
				}
			}
			ubicacionDTO.setIndicativoTelefonoFijo(ubicacion.getIndicativoTelFijo());
			ubicacionDTO.setTelefonoCelular(ubicacion.getTelefonoCelular());
			ubicacionDTO.setTelefonoFijo(ubicacion.getTelefonoFijo());
			ubicacionDTO.setAutorizacionEnvioEmail(ubicacion.getAutorizacionEnvioEmail());
			ubicacionDTO.setDescripcionIndicacion(ubicacion.getDescripcionIndicacion());
			ubicacionDTO.setSectorUbicacion(ubicacion.getSectorUbicacion());
			return ubicacionDTO;
		}
		return null;
	}
	
	/**
	 * Método encargado de crear un objeto Ubicacion mediante el DTO
	 * UbicacionDTO
	 * 
	 * @param ubicacionDTO,
	 *            DTO Ubicacion
	 * @return el objeto Ubicacion
	 */
	public static Ubicacion obtenerUbicacion(UbicacionDTO ubicacionDTO) {
		if (ubicacionDTO != null) {
			Ubicacion ubicacion = new Ubicacion();
			ubicacion.setIdUbicacion(ubicacionDTO.getIdUbicacion());
			ubicacion.setCodigoPostal(ubicacionDTO.getCodigoPostal());
			ubicacion.setEmail(ubicacionDTO.getCorreoElectronico());
                        ubicacion.setEmailSecundario(ubicacionDTO.getCorreoElectronicoSecundario());
			ubicacion.setDireccionFisica(ubicacionDTO.getDireccion());
			if (ubicacionDTO.getIdMunicipio()!= null) {
				Municipio m = new Municipio();
				m.setIdMunicipio(ubicacionDTO.getIdMunicipio());
				if(ubicacionDTO.getIdDepartamento() !=null){
					m.setIdDepartamento(ubicacionDTO.getIdDepartamento());
				}
				ubicacion.setMunicipio(m);
			}
			ubicacion.setIndicativoTelFijo(ubicacionDTO.getIndicativoTelefonoFijo());
			ubicacion.setTelefonoCelular(ubicacionDTO.getTelefonoCelular());
			ubicacion.setTelefonoFijo(ubicacionDTO.getTelefonoFijo());
			ubicacion.setAutorizacionEnvioEmail(ubicacionDTO.getAutorizacionEnvioEmail());
			ubicacion.setDescripcionIndicacion(ubicacionDTO.getDescripcionIndicacion());
			ubicacion.setSectorUbicacion(ubicacionDTO.getSectorUbicacion());
			return ubicacion;
		}
		return null;
	}
	
	/**
	 * Asigna los datos asociados a la Ubicacion.
	 * @param ubicacion
	 * @return Ubicacion
	 */
	public Ubicacion asignarDatosUbicacion(Ubicacion ubicacion) {
		if (getAutorizacionEnvioEmail() != null) {
			ubicacion.setAutorizacionEnvioEmail(getAutorizacionEnvioEmail());
		}
		if (getCodigoPostal() != null) {
			ubicacion.setCodigoPostal(getCodigoPostal());
		}
		if (getCorreoElectronico() != null) {
			ubicacion.setEmail(getCorreoElectronico());
		}
                if (getCorreoElectronicoSecundario() != null) {
			ubicacion.setEmailSecundario(getCorreoElectronicoSecundario());
		}
		if (getDireccion() != null) {
			ubicacion.setDireccionFisica(getDireccion());
		}
		if (getIdMunicipio() != null) {
			Municipio municipio = new Municipio();
			municipio.setIdMunicipio(getIdMunicipio());
			ubicacion.setMunicipio(municipio);
		}
		if (getIndicativoTelefonoFijo() != null) {
			ubicacion.setIndicativoTelFijo(getIndicativoTelefonoFijo());
		}
		if (getTelefonoCelular() != null) {
			ubicacion.setTelefonoCelular(getTelefonoCelular());	
		}
		if (getTelefonoFijo() != null) {
			ubicacion.setTelefonoFijo(getTelefonoFijo());
		}
		if (getDescripcionIndicacion() != null) {
            ubicacion.setDescripcionIndicacion(getDescripcionIndicacion());
        }
		if (getSectorUbicacion() != null) {
            ubicacion.setSectorUbicacion(getSectorUbicacion());
        }
		
		return ubicacion;
	}
	
	/**
	 * Método encargado de asignar los datos de la ubicación dto a la persona 
	 * @param ubicacion, ubicación 
	 * @return retorna la ubicación dto
	 */
	public UbicacionDTO asignarDatosUbicacionDTO(Object[] ubicacion){
	    UbicacionDTO ubicacionDTO=new UbicacionDTO();
	    ubicacionDTO.setIdUbicacion(ubicacion[0] != null ? new Long(ubicacion[0].toString()) : null);
        ubicacionDTO.setAutorizacionEnvioEmail(ubicacion[1] != null ? new Boolean(ubicacion[1].toString()) : null);
        ubicacionDTO.setCodigoPostal(ubicacion[2] != null ? ubicacion[2].toString() : null);
        ubicacionDTO.setDireccion(ubicacion[3] != null ? ubicacion[3].toString() : null);
        ubicacionDTO.setCorreoElectronico(ubicacion[4] != null ? ubicacion[4].toString() : null);
        ubicacionDTO.setIndicativoTelefonoFijo(ubicacion[5] != null ? ubicacion[5].toString() : null);
        ubicacionDTO.setTelefonoCelular(ubicacion[6] != null ? ubicacion[6].toString() : null);
        ubicacionDTO.setTelefonoFijo(ubicacion[7] != null ? ubicacion[7].toString() : null);
        ubicacionDTO.setIdMunicipio(ubicacion[8] != null ? new Short(ubicacion[8].toString()) : null);
        ubicacionDTO.setDescripcionIndicacion(ubicacion[9] != null ? ubicacion[9].toString() : null);
        ubicacionDTO.setTipoUbicacion(ubicacion[10] != null ? TipoUbicacionEnum.valueOf(ubicacion[10].toString()): null);
        return ubicacionDTO;
	}
	
	/**
     * Método encargado de asignar los datos de la ubicación dto a la persona 
     * @param ubicacion, ubicación 
     * @return retorna la ubicación dto
     */
    public UbicacionDTO asignarDatosUbicacionAfiliadoDTO(Object[] ubicacion){
        UbicacionDTO ubicacionDTO=new UbicacionDTO();
        ubicacionDTO.setIdUbicacion(ubicacion[0] != null ? new Long(ubicacion[0].toString()) : null);
        ubicacionDTO.setAutorizacionEnvioEmail(ubicacion[1] != null ? new Boolean(ubicacion[1].toString()) : null);
        ubicacionDTO.setCodigoPostal(ubicacion[2] != null ? ubicacion[2].toString() : null);
        ubicacionDTO.setDireccion(ubicacion[3] != null ? ubicacion[3].toString() : null);
        ubicacionDTO.setCorreoElectronico(ubicacion[4] != null ? ubicacion[4].toString() : null);
        ubicacionDTO.setIndicativoTelefonoFijo(ubicacion[5] != null ? ubicacion[5].toString() : null);
        ubicacionDTO.setTelefonoCelular(ubicacion[6] != null ? ubicacion[6].toString() : null);
        ubicacionDTO.setTelefonoFijo(ubicacion[7] != null ? ubicacion[7].toString() : null);
        ubicacionDTO.setIdMunicipio(ubicacion[8] != null ? new Short(ubicacion[8].toString()) : null);
        ubicacionDTO.setDescripcionIndicacion(ubicacion[9] != null ? ubicacion[9].toString() : null);
        return ubicacionDTO;
    }
	
	/**
	 * Método que retorna el valor de idUbicacion.
	 * @return valor de idUbicacion.
	 */
	public Long getIdUbicacion() {
		return idUbicacion;
	}

	/**
	 * Método encargado de modificar el valor de idUbicacion.
	 * @param valor para modificar idUbicacion.
	 */
	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	/**
	 * @return the idDepartamento
	 */
	public Short getIdDepartamento() {
		return idDepartamento;
	}

	/**
	 * @param idDepartamento
	 *            the idDepartamento to set
	 */
	public void setIdDepartamento(Short idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	/**
	 * @return the idMunicipio
	 */
	public Short getIdMunicipio() {
		return idMunicipio;
	}

	/**
	 * @param idMunicipio
	 *            the idMunicipio to set
	 */
	public void setIdMunicipio(Short idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 *            the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal
	 *            the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/**
	 * @return the telefonoFijo
	 */
	public String getTelefonoFijo() {
		return telefonoFijo;
	}

	/**
	 * @param telefonoFijo
	 *            the telefonoFijo to set
	 */
	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	/**
	 * @return the indicativoTelefonoFijo
	 */
	public String getIndicativoTelefonoFijo() {
		return indicativoTelefonoFijo;
	}

	/**
	 * @param indicativoTelefonoFijo
	 *            the indicativoTelefonoFijo to set
	 */
	public void setIndicativoTelefonoFijo(String indicativoTelefonoFijo) {
		this.indicativoTelefonoFijo = indicativoTelefonoFijo;
	}

	/**
	 * @return the telefonoCelular
	 */
	public String getTelefonoCelular() {
		return telefonoCelular;
	}

	/**
	 * @param telefonoCelular
	 *            the telefonoCelular to set
	 */
	public void setTelefonoCelular(String telefonoCelular) {
		this.telefonoCelular = telefonoCelular;
	}

	/**
	 * @return the correoElectronico
	 */
	public String getCorreoElectronico() {
		return correoElectronico;
	}

	/**
	 * @param correoElectronico
	 *            the correoElectronico to set
	 */
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	/**
	 * @return the autorizacionEnvioEmail
	 */
	public Boolean getAutorizacionEnvioEmail() {
		return autorizacionEnvioEmail;
	}

	/**
	 * @param autorizacionEnvioEmail
	 *            the autorizacionEnvioEmail to set
	 */
	public void setAutorizacionEnvioEmail(Boolean autorizacionEnvioEmail) {
		this.autorizacionEnvioEmail = autorizacionEnvioEmail;
	}

    /**
     * @return the descripcionIndicacion
     */
    public String getDescripcionIndicacion() {
        return descripcionIndicacion;
    }

    /**
     * @param descripcionIndicacion the descripcionIndicacion to set
     */
    public void setDescripcionIndicacion(String descripcionIndicacion) {
        this.descripcionIndicacion = descripcionIndicacion;
    }

    /**
     * Método que retorna el valor de tipoUbicacion.
     * @return valor de tipoUbicacion.
     */
    public TipoUbicacionEnum getTipoUbicacion() {
        return tipoUbicacion;
    }

    /**
     * Método encargado de modificar el valor de tipoUbicacion.
     * @param valor para modificar tipoUbicacion.
     */
    public void setTipoUbicacion(TipoUbicacionEnum tipoUbicacion) {
        this.tipoUbicacion = tipoUbicacion;
    }
	public SectorUbicacionEnum getSectorUbicacion() {
        return sectorUbicacion;
    }
    public void setSectorUbicacion(SectorUbicacionEnum sectorUbicacion) {
        this.sectorUbicacion = sectorUbicacion;
    }
    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UbicacionDTO [idUbicacion=");
		builder.append(idUbicacion);
		builder.append(", idDepartamento=");
		builder.append(idDepartamento);
		builder.append(", idMunicipio=");
		builder.append(idMunicipio);
		builder.append(", direccion=");
		builder.append(direccion);
		builder.append(", codigoPostal=");
		builder.append(codigoPostal);
		builder.append(", telefonoFijo=");
		builder.append(telefonoFijo);
		builder.append(", indicativoTelefonoFijo=");
		builder.append(indicativoTelefonoFijo);
		builder.append(", telefonoCelular=");
		builder.append(telefonoCelular);
		builder.append(", correoElectronico=");
		builder.append(correoElectronico);
		builder.append(", autorizacionEnvioEmail=");
		builder.append(autorizacionEnvioEmail);
		builder.append(", descripcionIndicacion=");
		builder.append(descripcionIndicacion);
		builder.append(", tipoUbicacion=");
		builder.append(tipoUbicacion);
		builder.append(", sectorUbicacion=");
        builder.append(sectorUbicacion);
                builder.append(", correoElectronicoSecundario=");
		builder.append(correoElectronicoSecundario);
		builder.append("]");
		return builder.toString();
	}

    public String getCorreoElectronicoSecundario() {
        return correoElectronicoSecundario;
    }

    public void setCorreoElectronicoSecundario(String correoElectronicoSecundario) {
        this.correoElectronicoSecundario = correoElectronicoSecundario;
    }

	@Override
	public UbicacionDTO clone() {
		try {
			return (UbicacionDTO) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(); // No debería pasar, ya que implementas Cloneable
		}
	}
}
