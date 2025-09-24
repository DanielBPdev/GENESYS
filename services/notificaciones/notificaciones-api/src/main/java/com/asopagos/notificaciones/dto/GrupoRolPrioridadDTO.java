package com.asopagos.notificaciones.dto;

import java.util.List;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class GrupoRolPrioridadDTO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * Etiqueta comunicado 
     */
    private EtiquetaPlantillaComunicadoEnum etiquetaComunicado;
    /**
     * Lista de roles pertenecientes a la etiqueta 
     */
    private List<GrupoPrioridadDTO> listaGrupoPrioridadDTO;

    /**
     * Método constructor
     */
    public GrupoRolPrioridadDTO() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the listaGrupoPrioridadDTO
     */
    public List<GrupoPrioridadDTO> getListaGrupoPrioridadDTO() {
        return listaGrupoPrioridadDTO;
    }

    /**
     * @param listaGrupoPrioridadDTO the listaGrupoPrioridadDTO to set
     */
    public void setListaGrupoPrioridadDTO(List<GrupoPrioridadDTO> listaGrupoPrioridadDTO) {
        this.listaGrupoPrioridadDTO = listaGrupoPrioridadDTO;
    }

    /**
     * @return the etiquetaComunicado
     */
    public EtiquetaPlantillaComunicadoEnum getEtiquetaComunicado() {
        return etiquetaComunicado;
    }

    /**
     * @param etiquetaComunicado
     *            the etiquetaComunicado to set
     */
    public void setEtiquetaComunicado(EtiquetaPlantillaComunicadoEnum etiquetaComunicado) {
        this.etiquetaComunicado = etiquetaComunicado;
    }
}
