package com.asopagos.rest.security.dto;

import java.io.Serializable;

/**
 * <b>Descripción:</b> DTO para los datos de auditoria del usuario que realiza peticiones 
 * sobre el sistema
 * <b>Historia de Usuario:</b> Transversal
 *
 * @author Andrey Lopez <alopez@heinsohn.com.co>
 */
public class AuditDTO implements Serializable {
    
    /** version serial */
    private static final long serialVersionUID = 1L;
    
    private String ip;
    
    private String txId;
    
    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the txId
     */
    public String getTxId() {
        return txId;
    }

    /**
     * @param txId the txId to set
     */
    public void setTxId(String txId) {
        this.txId = txId;
    }

}