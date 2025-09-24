package com.asopagos.archivos.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RespuestaECMExternoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cud;
	private Integer code;
	private String message;
	private String status;
	private String payload;
	
	public RespuestaECMExternoDTO() {
	}

	public RespuestaECMExternoDTO(String cud, Integer code, String message, String status, String payload) {
		super();
		this.cud = cud;
		this.code = code;
		this.message = message;
		this.status = status;
		this.payload = payload;
	}

	/**
	 * @return the cud
	 */
	public String getCud() {
		return cud;
	}

	/**
	 * @param cud the cud to set
	 */
	public void setCud(String cud) {
		this.cud = cud;
	}

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the reeMessage to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the payload
	 */
	public String getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	public void setPayload(String payload) {
		this.payload = payload;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RespuestaECMExternoDTO [cud=");
		builder.append(cud);
		builder.append(", code=");
		builder.append(code);
		builder.append(", message=");
		builder.append(message);
		builder.append(", status=");
		builder.append(status);
		builder.append(", payload=");
		builder.append(payload);
		builder.append("]");
		return builder.toString();
	}
}
