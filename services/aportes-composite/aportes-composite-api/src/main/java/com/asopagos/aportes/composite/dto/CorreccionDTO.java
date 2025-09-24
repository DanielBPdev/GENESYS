package com.asopagos.aportes.composite.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.aportes.dto.SolicitudCorreccionDTO;
import com.asopagos.dto.AnalisisDevolucionDTO;
import com.asopagos.dto.aportes.CorreccionAportanteDTO;
import com.asopagos.dto.aportes.CotizanteDTO;
import com.asopagos.dto.aportes.HistoricoDTO;

/**
 * 
 * Clase DTO que encarga de contener la informacion de las correciones
 * pertenecientes al aportante
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 *
 */
@XmlRootElement
public class CorreccionDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
	 * Solicitud de Correciones DTO
	 */
	private SolicitudCorreccionDTO solicitudCorrecionDTO;
	/**
	 * Listado de cotizantes DTO
	 */
	private List<CotizanteDTO> lstCotizantes;

	/**
	 * Aporte general seleccionado (para corrección es un único aporte)
	 */
	private AnalisisDevolucionDTO analisis;

    /**
     * Correccion del aportante (caso sin detalle).
     */
    private CorreccionAportanteDTO correccion;
    
    /**
     * Historico del cotizante (Caso sin detalle)
     */
    private HistoricoDTO historico;
    
    /**
	 * Constructor
	 */
	public CorreccionDTO() {
		
	}
	
	/**
	 * Método que retorna el valor de solicitudCorrecionDTO.
	 * 
	 * @return valor de solicitudCorrecionDTO.
	 */
	public SolicitudCorreccionDTO getSolicitudCorrecionDTO() {
		return solicitudCorrecionDTO;
	}

	/**
	 * Método encargado de modificar el valor de solicitudCorrecionDTO.
	 * 
	 * @param valor
	 *            para modificar solicitudCorrecionDTO.
	 */
	public void setSolicitudCorrecionDTO(SolicitudCorreccionDTO solicitudCorrecionDTO) {
		this.solicitudCorrecionDTO = solicitudCorrecionDTO;
	}

	/**
	 * Método que retorna el valor de lstCotizantes.
	 * 
	 * @return valor de lstCotizantes.
	 */
	public List<CotizanteDTO> getLstCotizantes() {
		return lstCotizantes;
	}

	/**
	 * Método encargado de modificar el valor de lstCotizantes.
	 * 
	 * @param valor
	 *            para modificar lstCotizantes.
	 */
	public void setLstCotizantes(List<CotizanteDTO> lstCotizantes) {
		this.lstCotizantes = lstCotizantes;
	}
	
	/**
     * Método que retorna el valor de analisis.
     * @return valor de analisis.
     */
    public AnalisisDevolucionDTO getAnalisis() {
        return analisis;
    }

    /**
     * Método encargado de modificar el valor de analisis.
     * @param valor para modificar analisis.
     */
    public void setAnalisis(AnalisisDevolucionDTO analisis) {
        this.analisis = analisis;
    }

    /**
     * @return the correccion
     */
    public CorreccionAportanteDTO getCorreccion() {
        return correccion;
    }

    /**
     * @param correccion the correccion to set
     */
    public void setCorreccion(CorreccionAportanteDTO correccion) {
        this.correccion = correccion;
    }

    /**
     * @return the historico
     */
    public HistoricoDTO getHistorico() {
        return historico;
    }

    /**
     * @param historico the historico to set
     */
    public void setHistorico(HistoricoDTO historico) {
        this.historico = historico;
    }
    
}
