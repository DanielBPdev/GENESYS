package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.UbicacionDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class DataTemporalDTO implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private infoTemporalEmpleadorDTO dto;
    
    @JsonIgnore
    private String token;
    
    @JsonIgnore
    private Object empleador;
    
    @JsonIgnore
    private List<Object> ubicaciones;

    public DataTemporalDTO() {
        
    }

	/**
	 * @return the dto
	 */
	public infoTemporalEmpleadorDTO getDto() {
		return dto;
	}

	/**
	 * @param dto the dto to set
	 */
	public void setDto(infoTemporalEmpleadorDTO dto) {
		this.dto = dto;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the empleador
	 */
	public Object getEmpleador() {
		return empleador;
	}

	/**
	 * @param empleador the empleador to set
	 */
	public void setEmpleador(Object empleador) {
		this.empleador = empleador;
	}

	/**
	 * @return the ubicaciones
	 */
	public List<Object> getUbicaciones() {
		return ubicaciones;
	}

	/**
	 * @param ubicaciones the ubicaciones to set
	 */
	public void setUbicaciones(List<Object> ubicaciones) {
		this.ubicaciones = ubicaciones;
	}
}
