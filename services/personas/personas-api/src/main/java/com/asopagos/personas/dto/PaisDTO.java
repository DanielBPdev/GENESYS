package com.asopagos.personas.dto;

import java.io.Serializable;

import com.asopagos.entidades.ccf.personas.Pais;

public class PaisDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private String codigo;
    private String descripcion;
    private String nombre;

    /**
     *
     */
    public PaisDTO() {

    }


    /**
     * constructor de la consulta consultarPaisPorCodigo de PersonasBusiness
     * @param id
     * @param codigo
     * @param nombre
     */
    public PaisDTO(Long id, String codigo, String nombre) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    /**
     * Convierte un Pais en un PaisDTO
     *
     * @param Pais
     * @return PaisDTO
     */
    public PaisDTO obtenerPaisDTO(Pais pais) {
        if (pais != null) {
            PaisDTO paisDTO = new PaisDTO();
            paisDTO.id = pais.getIdPais();
            paisDTO.codigo = pais.getCodigo();
            //paisDTO.descripcion = pais.getDescripcion();
            paisDTO.nombre = pais.getDescripcion();
            return paisDTO;
        }
        return null;
    }

    /**
     * Convierte un PaisDTO en un Pais
     *
     * @param PaisDTO
     * @return Pais
     */
    public Pais obtenerPais(PaisDTO paisDTO) {
        if (paisDTO != null) {
            Pais pais = new Pais();
            pais.setIdPais(paisDTO.getId());
            pais.setCodigo(paisDTO.getCodigo());
            pais.setDescripcion(paisDTO.getNombre() != null ? paisDTO.getNombre() : paisDTO.getDescripcion());
            return pais;
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}