package com.asopagos.aportes.masivos.dto;

import com.asopagos.dto.modelo.DevolucionAporteModeloDTO;
import com.asopagos.dto.CargueArchivoActualizacionDTO;
import java.io.Serializable;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Clase DTO con los datos de Devoluciones manejados temporalmente.
 * 
 * @author <a href="andres.rey@asopagos">Andres Felipe Rey Pedrazas </a>
*/
@XmlRootElement
public class ArchivoDevolucionDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 482785595804133596L;

	/**
	 * Campo tipo DTO que representa los datos de la solicitud.
	 */
	private CargueArchivoActualizacionDTO cargue;

	/**
	 * Campo tipo DTO que da forma al análisis de la devolución.
	 */
	private DevolucionAporteModeloDTO devolucion;

    private List<DatosRadicacionMasivaDTO> datosRadicacionMasiva;

    private Boolean incluirSubsidio;

    public CargueArchivoActualizacionDTO getCargue() {
        return this.cargue;
    }

    public void setCargue(CargueArchivoActualizacionDTO cargue) {
        this.cargue = cargue;
    }

    public DevolucionAporteModeloDTO getDevolucion() {
        return this.devolucion;
    }

    public void setDevolucion(DevolucionAporteModeloDTO devolucion) {
        this.devolucion = devolucion;
    }

    public List<DatosRadicacionMasivaDTO> getDatosRadicacionMasiva() {
        return this.datosRadicacionMasiva;
    }

    public void setDatosRadicacionMasiva(List<DatosRadicacionMasivaDTO> datosRadicacionMasiva) {
        this.datosRadicacionMasiva = datosRadicacionMasiva;
    }

    public Boolean getIncluirSubsidio() {
        return this.incluirSubsidio;
    }

    public void setIncluirSubsidio(Boolean incluirSubsidio) {
        this.incluirSubsidio = incluirSubsidio;
    }
	
	

	
}
