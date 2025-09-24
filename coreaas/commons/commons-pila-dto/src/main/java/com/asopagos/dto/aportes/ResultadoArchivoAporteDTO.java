package com.asopagos.dto.aportes;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;

/**
 * Clase DTO con los datos del resultado de validacion . Proceso: 2.1.2 - HU
 * -482
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 *
 */
@XmlRootElement
public class ResultadoArchivoAporteDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Lista de los cotizantes dto
	 */
	private List<CotizanteDTO> lstCotizantes;
	/**
	 * Lista de los hallazgos dto
	 */
	private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;
    /**
     * Método que retorna el valor de lstCotizantes.
     * @return valor de lstCotizantes.
     */
    public List<CotizanteDTO> getLstCotizantes() {
        return lstCotizantes;
    }
    /**
     * Método encargado de modificar el valor de lstCotizantes.
     * @param valor para modificar lstCotizantes.
     */
    public void setLstCotizantes(List<CotizanteDTO> lstCotizantes) {
        this.lstCotizantes = lstCotizantes;
    }
    /**
     * Método que retorna el valor de lstHallazgos.
     * @return valor de lstHallazgos.
     */
    public List<ResultadoHallazgosValidacionArchivoDTO> getLstHallazgos() {
        return lstHallazgos;
    }
    /**
     * Método encargado de modificar el valor de lstHallazgos.
     * @param valor para modificar lstHallazgos.
     */
    public void setLstHallazgos(List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos) {
        this.lstHallazgos = lstHallazgos;
    }

}

