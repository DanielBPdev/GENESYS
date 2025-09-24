package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.aportes.dto.DetalleRegistroDTO;
import com.asopagos.aportes.dto.ResumenCierreRecaudoDTO;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@XmlRootElement
public class RegistrosArchivoAporteDTO implements Serializable {

    /**
     * Serial version
     */
    private static final long serialVersionUID = -4222883676431948952L;

    /**
     * Lista que contiene los valores del cuadro del resumen para el archivo del cierre de recaudo de aporte
     */
    private List<ResumenCierreRecaudoDTO> resumenCierreRecaudo;
    
    /**
     * Lista que contiene los valores de los detalles por registro para el archivo del cierre de recaudo de aporte
     */
    private List<DetalleRegistroDTO> detallePorRegistro;

    /**
     * @return the resumenCierreRecaudo
     */
    public List<ResumenCierreRecaudoDTO> getResumenCierreRecaudo() {
        return resumenCierreRecaudo;
    }

    /**
     * @param resumenCierreRecaudo the resumenCierreRecaudo to set
     */
    public void setResumenCierreRecaudo(List<ResumenCierreRecaudoDTO> resumenCierreRecaudo) {
        this.resumenCierreRecaudo = resumenCierreRecaudo;
    }

    /**
     * @return the detallePorRegistro
     */
    public List<DetalleRegistroDTO> getDetallePorRegistro() {
        return detallePorRegistro;
    }

    /**
     * @param detallePorRegistro the detallePorRegistro to set
     */
    public void setDetallePorRegistro(List<DetalleRegistroDTO> detallePorRegistro) {
        this.detallePorRegistro = detallePorRegistro;
    }
}
