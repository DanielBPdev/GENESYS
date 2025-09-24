package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

public class RegistroErroresArchivoDTO implements Serializable {
    private static final long serialVersionUID = 5165142646354284L;
    private List<String> cabeceras;
    Long idConsola;

    public List<String> getCabeceras() {
        return cabeceras;
    }

    public void setCabeceras(List<String> cabeceras) {
        this.cabeceras = cabeceras;
    }

    public Long getIdConsola() {
        return idConsola;
    }

    public void setIdConsola(Long idConsola) {
        this.idConsola = idConsola;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RegistroErroresArchivoDTO.class.getSimpleName() + "[", "]")
                .add("cabeceras=" + cabeceras)
                .add("idConsola=" + idConsola)
                .toString();
    }
}
