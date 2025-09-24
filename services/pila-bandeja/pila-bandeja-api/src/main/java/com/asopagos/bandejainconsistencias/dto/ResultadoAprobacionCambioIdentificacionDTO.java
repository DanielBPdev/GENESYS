package com.asopagos.bandejainconsistencias.dto;

import java.util.ArrayList;
import java.util.List;
import com.asopagos.dto.modelo.IndicePlanillaModeloDTO;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;

/**
 * <b>Descripcion:</b> Clase que contiene los indices de las planillas afectadas por el cambio del numero de identificacion sobre unas
 * planillas <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:anbuitrago@heinsohn.com.co"> anbuitrago</a>
 */

public class ResultadoAprobacionCambioIdentificacionDTO {

    private List<IndicePlanilla> indicesOI;
    private List<IndicePlanillaOF> indicesOF;

    /**
     * 
     */
    public ResultadoAprobacionCambioIdentificacionDTO() {
        super();
    }

    /**
     * @return the indicesOI
     */
    public List<IndicePlanilla> getIndicesOI() {
        return indicesOI;
    }

    /**
     * @param indicesOI
     *        the indicesOI to set
     */
    public void setIndicesOI(List<IndicePlanilla> indicesOI) {
        this.indicesOI = indicesOI;
    }

    /**
     * @return the indicesOF
     */
    public List<IndicePlanillaOF> getIndicesOF() {
        return indicesOF;
    }

    /**
     * @param indicesOF
     *        the indicesOF to set
     */
    public void setIndicesOF(List<IndicePlanillaOF> indicesOF) {
        this.indicesOF = indicesOF;
    }

    /**
     * Método encargado de adicionar índices de planilla a las listas de la respuesta
     * @param indice
     */
    public void agregarIndice(Object indice) {
        if(indice instanceof IndicePlanillaModeloDTO){
            if(this.getIndicesOI() == null){
                this.setIndicesOI(new ArrayList<>());
            }
            
            this.getIndicesOI().add(((IndicePlanillaModeloDTO) indice).convertToEntity());
        }

        if(indice instanceof IndicePlanillaOF){
            if(this.getIndicesOF() == null){
                this.setIndicesOF(new ArrayList<>());
            }
            
            this.getIndicesOF().add((IndicePlanillaOF) indice);
        }
    }

}
