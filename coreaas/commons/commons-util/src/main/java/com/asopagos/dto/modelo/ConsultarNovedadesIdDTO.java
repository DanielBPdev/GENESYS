package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.List;

public class ConsultarNovedadesIdDTO implements Serializable {
    private static final long serialVersionUID = -8013069654864678043L;

    private List<Long> listIdPersons;

    public List<Long> getListIdPersons() {
        return listIdPersons;
    }

    public void setListIdPersons(List<Long> listIdPersons) {
        this.listIdPersons = listIdPersons;
    }

    public ConsultarNovedadesIdDTO() {
    }

    public ConsultarNovedadesIdDTO(List<Long> listIdPersons) {
        this.listIdPersons = listIdPersons;
    }
}
