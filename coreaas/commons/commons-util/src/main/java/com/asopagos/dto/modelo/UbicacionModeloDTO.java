package com.asopagos.dto.modelo;	
import java.io.Serializable;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.personas.SectorUbicacionEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO con los datos del Modelo de Ubicación.
 * GLPI 49270: Fovis - Agregar segundo correo electronico.
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
 @JsonIgnoreProperties(ignoreUnknown = true)
public class UbicacionModeloDTO implements Serializable{
	
	/**
	 * Código identificador de llave primaria de la persona 
	 */
	private Long idUbicacion;
	
	/**
	 * Descripción de la dirección física
	 */
	private String direccionFisica;
	
	/**
	 * Códgigo postal
	 */
	private String codigoPostal;
	
	/**
	 * Descripción del teléfono fijo
	 */
	private String telefonoFijo;
	
	/**
	 * Indicativo del teléfono fijo asociado a la ubicación
	 */
	private String indicativoTelFijo;
	
	/**
	 * Número telefónico del celular asociado a la ubicación
	 */
	private String telefonoCelular;
	
	/**
	 * Descripción del correo electrónico asociado a la ubicación
	 */
	private String email;
	
	/**
	 * Indicador S/N autoriza envío de email [S=Si N=No]
	 * Default false
	 */
	private Boolean autorizacionEnvioEmail;

	/**
	 * Id que identifica al municipio asoaciado a la ubicación
	 */
	private Short idMunicipio;

	/**
	 * Id que identifica al departamento asoaciado a la ubicación
	 */
	private Short idDepartamento;
	
	private String descripcionIndicacion;
	
	/**
     * Sector Ubicación asociado al grupo Familiar
     */
    private SectorUbicacionEnum sectorUbicacion;
    
    /**
    * Descripción del correo electrónico secundario asociado a la ubicación
    */
   private String emailSecundario;

   
    /**
     * Asocia los datos del DTO a la Entidad
     * @return Ubicacion
     */
    public Ubicacion convertToEntity() {
    	Ubicacion ubicacion = new Ubicacion();
    	ubicacion.setAutorizacionEnvioEmail(this.getAutorizacionEnvioEmail());
    	ubicacion.setCodigoPostal(this.getCodigoPostal());
    	ubicacion.setDescripcionIndicacion(this.getDescripcionIndicacion());
    	ubicacion.setDireccionFisica(this.getDireccionFisica());
    	ubicacion.setEmail(this.getEmail());
        ubicacion.setEmailSecundario(this.getEmailSecundario());
    	ubicacion.setIdUbicacion(this.getIdUbicacion());
    	ubicacion.setIndicativoTelFijo(this.getIndicativoTelFijo());
    	if (this.getIdMunicipio() != null) {
    		Municipio municipio = new Municipio();
    		municipio.setIdMunicipio(this.getIdMunicipio());
    		ubicacion.setMunicipio(municipio);
			if(ubicacion.getMunicipio().getIdDepartamento() != null){
				this.setIdDepartamento(ubicacion.getMunicipio().getIdDepartamento());
			}
    	}
    	ubicacion.setTelefonoCelular(this.getTelefonoCelular());
    	ubicacion.setTelefonoFijo(this.getTelefonoFijo());
    	ubicacion.setSectorUbicacion(this.getSectorUbicacion());
    	return ubicacion;
    }
    
    /**
     * @param Asocia los datos de la Entidad al DTO
     * @return UbicacionModeloDTO
     */
    public void convertToDTO (Ubicacion ubicacion) {
    	this.setAutorizacionEnvioEmail(ubicacion.getAutorizacionEnvioEmail());
    	this.setCodigoPostal(ubicacion.getCodigoPostal());
    	this.setDescripcionIndicacion(ubicacion.getDescripcionIndicacion());
    	this.setDireccionFisica(ubicacion.getDireccionFisica());
    	this.setEmail(ubicacion.getEmail());
        this.setEmailSecundario(ubicacion.getEmailSecundario());
    	if (ubicacion.getMunicipio() != null && ubicacion.getMunicipio().getIdMunicipio() != null) {
    		this.setIdMunicipio(ubicacion.getMunicipio().getIdMunicipio());
			if(ubicacion.getMunicipio().getIdDepartamento() != null){
				this.setIdDepartamento(ubicacion.getMunicipio().getIdDepartamento());
			}
    	}
    	this.setIdUbicacion(ubicacion.getIdUbicacion());
    	this.setIndicativoTelFijo(ubicacion.getIndicativoTelFijo());
    	this.setTelefonoCelular(ubicacion.getTelefonoCelular());
    	this.setTelefonoFijo(ubicacion.getTelefonoFijo());
    	this.setSectorUbicacion(ubicacion.getSectorUbicacion());
    }
    
    /**
     * Copia los datos del DTO a la Entidad.
     * @param ubicacion previamente consultada.
     * @return Ubicacion ubicacion modificada.
     */
    public Ubicacion copyDTOToEntity (Ubicacion ubicacion) {
    	if (this.getAutorizacionEnvioEmail() != null) {
			ubicacion.setAutorizacionEnvioEmail(this.getAutorizacionEnvioEmail());
		}
		if (this.getCodigoPostal() != null) {
			ubicacion.setCodigoPostal(this.getCodigoPostal());
		}
		if (this.getDescripcionIndicacion() != null) {
			ubicacion.setDescripcionIndicacion(this.getDescripcionIndicacion());
		}
		if (this.getDireccionFisica() != null) {
			ubicacion.setDireccionFisica(this.getDireccionFisica());
		}
		if (this.getEmail() != null) {
			ubicacion.setEmail(this.getEmail());
		}
                if (this.getEmailSecundario() != null) {
			ubicacion.setEmailSecundario(this.getEmailSecundario());
		}
		if (this.getIdMunicipio() != null) {
			Municipio municipio = new Municipio();
			municipio.setIdMunicipio(this.getIdMunicipio());
			ubicacion.setMunicipio(municipio);
		}
		if (this.getIndicativoTelFijo() != null) {
			ubicacion.setIndicativoTelFijo(this.getIndicativoTelFijo());
		}
		if (this.getTelefonoCelular() != null) {
			ubicacion.setTelefonoCelular(this.getTelefonoCelular());
		}
		if (this.getTelefonoFijo() != null) {
			ubicacion.setTelefonoCelular(this.getTelefonoCelular());
		}
		if (this.getSectorUbicacion() != null) {
            ubicacion.setSectorUbicacion(this.getSectorUbicacion());
        }
		return ubicacion;
    }

	/**
	 * @return the idUbicacion
	 */
	public Long getIdUbicacion() {
		return idUbicacion;
	}

	/**
	 * @param idUbicacion the idUbicacion to set
	 */
	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	/**
	 * @return the direccionFisica
	 */
	public String getDireccionFisica() {
		return direccionFisica;
	}

	/**
	 * @param direccionFisica the direccionFisica to set
	 */
	public void setDireccionFisica(String direccionFisica) {
		this.direccionFisica = direccionFisica;
	}

	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal the codigoPostal to set
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
	 * @param telefonoFijo the telefonoFijo to set
	 */
	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	/**
	 * @return the indicativoTelFijo
	 */
	public String getIndicativoTelFijo() {
		return indicativoTelFijo;
	}

	/**
	 * @param indicativoTelFijo the indicativoTelFijo to set
	 */
	public void setIndicativoTelFijo(String indicativoTelFijo) {
		this.indicativoTelFijo = indicativoTelFijo;
	}

	/**
	 * @return the telefonoCelular
	 */
	public String getTelefonoCelular() {
		return telefonoCelular;
	}

	/**
	 * @param telefonoCelular the telefonoCelular to set
	 */
	public void setTelefonoCelular(String telefonoCelular) {
		this.telefonoCelular = telefonoCelular;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the autorizacionEnvioEmail
	 */
	public Boolean getAutorizacionEnvioEmail() {
		return autorizacionEnvioEmail;
	}

	/**
	 * @param autorizacionEnvioEmail the autorizacionEnvioEmail to set
	 */
	public void setAutorizacionEnvioEmail(Boolean autorizacionEnvioEmail) {
		this.autorizacionEnvioEmail = autorizacionEnvioEmail;
	}

	/**
	 * @return the idMunicipio
	 */
	public Short getIdMunicipio() {
		return idMunicipio;
	}

	/**
	 * @param idMunicipio the idMunicipio to set
	 */
	public void setIdMunicipio(Short idMunicipio) {
		this.idMunicipio = idMunicipio;
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

    public SectorUbicacionEnum getSectorUbicacion() {
        return sectorUbicacion;
    }

    public void setSectorUbicacion(SectorUbicacionEnum sectorUbicacion) {
        this.sectorUbicacion = sectorUbicacion;
    }

    public String getEmailSecundario() {
        return emailSecundario;
    }

    public void setEmailSecundario(String emailSecundario) {
        this.emailSecundario = emailSecundario;
    }

	public Short getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Short idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	@Override
	public String toString() {
		return "UbicacionModeloDTO{" +
				"idUbicacion=" + idUbicacion +
				", direccionFisica='" + direccionFisica + '\'' +
				", codigoPostal='" + codigoPostal + '\'' +
				", telefonoFijo='" + telefonoFijo + '\'' +
				", indicativoTelFijo='" + indicativoTelFijo + '\'' +
				", telefonoCelular='" + telefonoCelular + '\'' +
				", email='" + email + '\'' +
				", autorizacionEnvioEmail=" + autorizacionEnvioEmail +
				", idMunicipio=" + idMunicipio +
				", idDepartamento=" + idDepartamento +
				", descripcionIndicacion='" + descripcionIndicacion + '\'' +
				", sectorUbicacion=" + sectorUbicacion +
				", emailSecundario='" + emailSecundario + '\'' +
				'}';
	}
}
