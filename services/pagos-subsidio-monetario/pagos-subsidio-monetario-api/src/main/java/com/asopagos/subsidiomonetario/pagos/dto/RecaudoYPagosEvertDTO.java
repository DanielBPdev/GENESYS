package com.asopagos.subsidiomonetario.pagos.dto;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.util.Date;
import com.asopagos.entidades.subsidiomonetario.pagos.AuditoriaRecaudosYPagos;


public class RecaudoYPagosEvertDTO {

    private Long partnerId;

    private String originID;

    private Long clientId;

    private Long transactionId;

    private String secuenceId;

    private Long currencyCode;

    private Double amountTran;

    private String transmissionDateTime;

    private String transactionType;

    private String businessLine;

    private String state;    

    private String city;

    private String identificacionType;

    private String identifcation;

    private String user;

    private String password;

    private String authorizationRspCode;

    private String additionalData;


    public RecaudoYPagosEvertDTO(Long partnerId, String originID, Long clientId, Long transactionId, String secuenceId, Long currencyCode, Double amountTran, String transmissionDateTime, String transactionType, String businessLine, String state, String city, String identificacionType, String identifcation, String user, String password, String authorizationRspCode, String additionalData) {
        this.partnerId = partnerId;
        this.originID = originID;
        this.clientId = clientId;
        this.transactionId = transactionId;
        this.secuenceId = secuenceId;
        this.currencyCode = currencyCode;
        this.amountTran = amountTran;
        this.transmissionDateTime = transmissionDateTime;
        this.transactionType = transactionType;
        this.businessLine = businessLine;
        this.state = state;
        this.city = city;
        this.identificacionType = identificacionType;
        this.identifcation = identifcation;
        this.user = user;
        this.password = password;
        this.authorizationRspCode = authorizationRspCode;
        this.additionalData = additionalData;
    }


    public AuditoriaRecaudosYPagos convertToEntity(){
        AuditoriaRecaudosYPagos auditoriaRecaudosYPagos = new AuditoriaRecaudosYPagos();
        auditoriaRecaudosYPagos.setIdPartner(this.getPartnerId());
        auditoriaRecaudosYPagos.setIdTerceroPagador(this.getOriginID());
        auditoriaRecaudosYPagos.setIdCliente(this.getClientId());
        auditoriaRecaudosYPagos.setIdTransaccion(this.getTransactionId());
        auditoriaRecaudosYPagos.setNumeroSecuencia(this.getSecuenceId());
        auditoriaRecaudosYPagos.setIdMoneda(this.getCurrencyCode());
        auditoriaRecaudosYPagos.setValorTransaccion(this.getAmountTran());
        auditoriaRecaudosYPagos.setFechaTransaccion(this.getTransmissionDateTime());
        auditoriaRecaudosYPagos.setTipoTransaccion(this.getTransactionType());
        auditoriaRecaudosYPagos.setLineaNegocio(this.getBusinessLine());
        auditoriaRecaudosYPagos.setDepartamento(this.getState());
        auditoriaRecaudosYPagos.setCiudad(this.getCity());
        auditoriaRecaudosYPagos.setTipoIdentificacion(this.getIdentificacionType());
        auditoriaRecaudosYPagos.setNumeroIdentificacion(this.getIdentifcation());
        auditoriaRecaudosYPagos.setUsuario(this.getUser());
        auditoriaRecaudosYPagos.setContrasena(this.getPassword());
        auditoriaRecaudosYPagos.setNumeroAutorizacion(this.getAuthorizationRspCode());
        auditoriaRecaudosYPagos.setCampoAdicional(this.getAdditionalData());

        return auditoriaRecaudosYPagos;
    }

    public Long getPartnerId() {
        return this.partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getOriginID() {
        return this.originID;
    }

    public void setOriginID(String originID) {
        this.originID = originID;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getSecuenceId() {
        return this.secuenceId;
    }

    public void setSecuenceId(String secuenceId) {
        this.secuenceId = secuenceId;
    }

    public Long getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(Long currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getAmountTran() {
        return this.amountTran;
    }

    public void setAmountTran(Double amountTran) {
        this.amountTran = amountTran;
    }

    public String getTransmissionDateTime() {
        return this.transmissionDateTime;
    }

    public void setTransmissionDateTime(String transmissionDateTime) {
        this.transmissionDateTime = transmissionDateTime;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getBusinessLine() {
        return this.businessLine;
    }

    public void setBusinessLine(String businessLine) {
        this.businessLine = businessLine;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIdentificacionType() {
        return this.identificacionType;
    }

    public void setIdentificacionType(String identificacionType) {
        this.identificacionType = identificacionType;
    }

    public String getIdentifcation() {
        return this.identifcation;
    }

    public void setIdentifcation(String identifcation) {
        this.identifcation = identifcation;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthorizationRspCode() {
        return this.authorizationRspCode;
    }

    public void setAuthorizationRspCode(String authorizationRspCode) {
        this.authorizationRspCode = authorizationRspCode;
    }

    public String getAdditionalData() {
        return this.additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }

    
}