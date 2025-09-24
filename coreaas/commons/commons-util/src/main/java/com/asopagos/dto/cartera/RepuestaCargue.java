package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.util.List;

public class RepuestaCargue implements Serializable {
    /**
     * Serial version
     */
    private static final long serialVersionUID = -7977673988421442286L;
    private List<String> errores;
    private Long idCartera;

    public RepuestaCargue() {
    }

    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }

    public Long getIdCartera() {
        return idCartera;
    }

    public void setIdCartera(Long idCartera) {
        this.idCartera = idCartera;
    }
}
