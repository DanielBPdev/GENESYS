package com.asopagos.afiliaciones.personas.composite.dto;

import java.util.List;
import com.asopagos.dto.afiliaciones.Afiliado25AniosDTO;
import com.asopagos.dto.afiliaciones.Afiliado25AniosExistenteDTO;

public class ListasPensionadosDTO {

    private List<Afiliado25AniosDTO> listaNuevos;
    private List<Afiliado25AniosExistenteDTO> listaExistetes;

    public ListasPensionadosDTO(List<Afiliado25AniosDTO> listaNuevos, List<Afiliado25AniosExistenteDTO> listaExistetes) {
        this.listaNuevos = listaNuevos;
        this.listaExistetes = listaExistetes;
    }

    public ListasPensionadosDTO() {}

    public List<Afiliado25AniosDTO> getListaNuevos() {
        return this.listaNuevos;
    }

    public void setListaNuevos(List<Afiliado25AniosDTO> listaNuevos) {
        this.listaNuevos = listaNuevos;
    }

    public List<Afiliado25AniosExistenteDTO> getListaExistetes() {
        return this.listaExistetes;
    }

    public void setListaExistetes(List<Afiliado25AniosExistenteDTO> listaExistetes) {
        this.listaExistetes = listaExistetes;
    }

    @Override
    public String toString() {
        return "{" +
            " listaNuevos='" + getListaNuevos() + "'" +
            ", listaExistetes='" + getListaExistetes() + "'" +
            "}";
    }
}
