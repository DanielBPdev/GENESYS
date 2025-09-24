/**
 * 
 */
package com.asopagos.novedades.fovis.dto;

import java.util.List;
import com.asopagos.dto.modelo.InhabilidadSubsidioFovisModeloDTO;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;

/**
 * DTO que contiene los campos que pueden modificar las novedades automaticas FOVIS
 * 
 * @author Edward Castaño <ecastano@heinsohn.com.co>
 *
 */
public class DatosNovedadAutomaticaFovisDTO {

    /**
     * Lista de postulaciones que seran procesadas por las novedades automaticas
     */
    private List<PostulacionFOVISModeloDTO> listaPostulaciones;

    /**
     * Lista de inhabilidades que seran procesadas por las novedades automaticas
     */
    private List<InhabilidadSubsidioFovisModeloDTO> listaInhabilidades;

    /**
     * @return the listaPostulaciones
     */
    public List<PostulacionFOVISModeloDTO> getListaPostulaciones() {
        return listaPostulaciones;
    }

    /**
     * @param listaPostulaciones
     *        the listaPostulaciones to set
     */
    public void setListaPostulaciones(List<PostulacionFOVISModeloDTO> listaPostulaciones) {
        this.listaPostulaciones = listaPostulaciones;
    }

    /**
     * @return the listaInhabilidades
     */
    public List<InhabilidadSubsidioFovisModeloDTO> getListaInhabilidades() {
        return listaInhabilidades;
    }

    /**
     * @param listaInhabilidades
     *        the listaInhabilidades to set
     */
    public void setListaInhabilidades(List<InhabilidadSubsidioFovisModeloDTO> listaInhabilidades) {
        this.listaInhabilidades = listaInhabilidades;
    }

}
