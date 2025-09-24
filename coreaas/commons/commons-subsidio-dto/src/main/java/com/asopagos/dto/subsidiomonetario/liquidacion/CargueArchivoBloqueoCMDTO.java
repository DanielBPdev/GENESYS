package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoNivelBloqueoEnum;

import com.asopagos.dto.InformacionArchivoDTO;

/**
 * <b>Descripcion:</b> Clase que contiene la informacion del Cargue Archivo de cruce Fovis<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>321-033
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa</a>
 */
@XmlRootElement
public class CargueArchivoBloqueoCMDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5027908672381412399L;

    
    /**
     * Fecha de procesamiento del archivo
     */
    private Date fechaInicioPeriodo;
    
    /**
     * Fecha de procesamiento del archivo
     */
    private Date fechaFinPeriodo;

    /**
     * Identificador ECM del archivo
     */
    private String codigoIdentificacionECM;
    
    private InformacionArchivoDTO archivo;

    private TipoNivelBloqueoEnum nivelBloqueo;

    /**
     * Constructor
     */
    public CargueArchivoBloqueoCMDTO() {

    }
   
     public TipoNivelBloqueoEnum getNivelBloqueo() {
        return this.nivelBloqueo;
    }

    public void setNivelBloqueo(TipoNivelBloqueoEnum nivelBloqueo) {
        this.nivelBloqueo = nivelBloqueo;
    }

	/**
     * @return the fechaInicioPeriodo
     */
    public Date getFechaInicioPeriodo() {
		return fechaInicioPeriodo;
	}

    /**
     * @param fechaInicioPeriodo
     *        the fechaInicioPeriodo to set
     */
	public void setFechaInicioPeriodo(Date fechaInicioPeriodo) {
		this.fechaInicioPeriodo = fechaInicioPeriodo;
	}



	/**
     * @return the fechaFinPeriodo
     */
	public Date getFechaFinPeriodo() {
		return fechaFinPeriodo;
	}

    /**
     * @param fechaFinPeriodo
     *        the fechaFinPeriodo to set
     */
	public void setFechaFinPeriodo(Date fechaFinPeriodo) {
		this.fechaFinPeriodo = fechaFinPeriodo;
	}

    /**
     * @return the codigoIdentificacionECM
     */
    public String getCodigoIdentificacionECM() {
        return codigoIdentificacionECM;
    }

    /**
     * @param codigoIdentificacionECM
     *        the codigoIdentificacionECM to set
     */
    public void setCodigoIdentificacionECM(String codigoIdentificacionECM) {
        this.codigoIdentificacionECM = codigoIdentificacionECM;
    }



	public InformacionArchivoDTO getArchivo() {
		return archivo;
	}



	public void setArchivo(InformacionArchivoDTO archivo) {
		this.archivo = archivo;
	}   
    
    

}
