package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class ConsultaAfiliadoRecaudosPagosDTO {
    /**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

    private String TransactionId;
    private String TransmissionDateTime;
    private Boolean ResponseCode;
    private String UserName;
    private String UserEmail;
    private String UserCellPhone;
    private Long ErrorID;
    private String AdditionalData;

	public ConsultaAfiliadoRecaudosPagosDTO() {
	}

	public ConsultaAfiliadoRecaudosPagosDTO(String transactionId, String transmissionDateTime, Boolean responseCode, String userName, String userEmail, String userCellPhone, Long errorID, String additionalData) {
		TransactionId = transactionId;
		TransmissionDateTime = transmissionDateTime;
		ResponseCode = responseCode;
		UserName = userName;
		UserEmail = userEmail;
		UserCellPhone = userCellPhone;
		ErrorID = errorID;
		AdditionalData = additionalData;
	}

	public String getTransactionId() {
		return TransactionId;
	}

	public void setTransactionId(String transactionId) {
		TransactionId = transactionId;
	}

	public String getTransmissionDateTime() {
		return TransmissionDateTime;
	}

	public void setTransmissionDateTime(String transmissionDateTime) {
		TransmissionDateTime = transmissionDateTime;
	}

	public Boolean getResponseCode() {
		return ResponseCode;
	}

	public void setResponseCode(Boolean responseCode) {
		ResponseCode = responseCode;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserEmail() {
		return UserEmail;
	}

	public void setUserEmail(String userEmail) {
		UserEmail = userEmail;
	}

	public String getUserCellPhone() {
		return UserCellPhone;
	}

	public void setUserCellPhone(String userCellPhone) {
		UserCellPhone = userCellPhone;
	}

	public Long getErrorID() {
		return ErrorID;
	}

	public void setErrorID(Long errorID) {
		ErrorID = errorID;
	}

	public String getAdditionalData() {
		return AdditionalData;
	}

	public void setAdditionalData(String additionalData) {
		AdditionalData = additionalData;
	}
}
