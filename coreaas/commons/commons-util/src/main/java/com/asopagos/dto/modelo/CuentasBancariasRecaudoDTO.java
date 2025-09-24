/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.dto.modelo;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mauricio
 */
@XmlRootElement
public class CuentasBancariasRecaudoDTO implements Serializable {
      
    private static final long serialVersionUID = 1L;

    private String ID;
    private String BANCO;
    private String TIPO;
    private String NUMERO_CUENTA;
    private String TIPO_RECAUDO;

    public CuentasBancariasRecaudoDTO() {
    }

    public CuentasBancariasRecaudoDTO(String ID, String BANCO, String TIPO, String NUMERO_CUENTA, String TIPO_RECAUDO) {
        this.ID = ID;
        this.BANCO = BANCO;
        this.TIPO = TIPO;
        this.NUMERO_CUENTA = NUMERO_CUENTA;
        this.TIPO_RECAUDO = TIPO_RECAUDO;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBANCO() {
        return BANCO;
    }

    public void setBANCO(String BANCO) {
        this.BANCO = BANCO;
    }

    public String getTIPO() {
        return TIPO;
    }

    public void setTIPO(String TIPO) {
        this.TIPO = TIPO;
    }

    public String getNUMERO_CUENTA() {
        return NUMERO_CUENTA;
    }

    public void setNUMERO_CUENTA(String NUMERO_CUENTA) {
        this.NUMERO_CUENTA = NUMERO_CUENTA;
    }

    public String getTIPO_RECAUDO() {
        return TIPO_RECAUDO;
    }

    public void setTIPO_RECAUDO(String TIPO_RECAUDO) {
        this.TIPO_RECAUDO = TIPO_RECAUDO;
    }               
    
}
