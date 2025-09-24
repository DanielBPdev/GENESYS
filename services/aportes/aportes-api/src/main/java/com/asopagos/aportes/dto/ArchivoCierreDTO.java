package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.util.List;
import javax.print.attribute.standard.SheetCollate;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@XmlRootElement
public class ArchivoCierreDTO implements Serializable {

    /**
     * Serial version
     */
    private static final long serialVersionUID = 3387792357034011517L;

    /**
     * Archivo excel
     */
    private byte[] archivo;
    
    /**
     * Lista con los identificadores de los aportes generales que estan en proceso de conciliación 
     */
    private String idsAporteGeneral;

    /**
     * @return the archivo
     */
    public byte[] getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    /**
     * @return the idsAporteGeneral
     */
    public String getIdsAporteGeneral() {
        return idsAporteGeneral;
    }

    /**
     * @param idsAporteGeneral the idsAporteGeneral to set
     */
    public void setIdsAporteGeneral(String idsAporteGeneral) {
        this.idsAporteGeneral = idsAporteGeneral;
    }
}
