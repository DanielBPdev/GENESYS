package com.asopagos.dto.webservices;

import java.lang.Long;
import java.io.Serializable;
import com.asopagos.enumeraciones.CodigosErrorWebservicesEnum;

public class ResponseDTO implements Serializable, Cloneable{
    
    private Long codigoRespuesta;

    private String mensajeError;

    private Object data;

    public ResponseDTO(){}

    public ResponseDTO(CodigosErrorWebservicesEnum codigoError,Object data){
        this.codigoRespuesta =  codigoError.getCodigoError();
        this.mensajeError = codigoError.getMensajeError();
        this.data = data;
    }

    public Long getCodigoRespuesta() {
        return this.codigoRespuesta;
    }

    public void setCodigoRespuesta(Long codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getMensajeError() {
        return this.mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
