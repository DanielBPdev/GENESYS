package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.Visita;

/**
 * DTO que contiene los datos relacionados a la entidad Visita
 * @author Mario Andrés Monroy Monroy <mamonroy@heinsohn.com.co>
 *
 */
@XmlRootElement
public class VisitaModeloDTO implements Serializable{
	
	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
	 * Llave primaria
	 */
	private Long idVisita;
	
	/**
	 * Fecha en que se realiza la visita
	 */
	private Date fechaVisita;
	
	/**
	 * Nombre del responsable de la visita
	 */
	private String nombresEncargadoVisita;
	
	/**
	 * Identificador del archivo asociado a la vista
	 */
	private String codigoIdentificadorVisita;

	/**
	 * Constructor por defecto
	 */
	public VisitaModeloDTO() {
	}

	
	public Visita convertToEntity(){
		Visita visita = new Visita();
		
		visita.setIdVisita(this.idVisita);
		visita.setFechaVisita(this.fechaVisita);
		visita.setNombresEncargadoVisita(this.nombresEncargadoVisita);
		visita.setCodigoIdentificadorVisita(this.codigoIdentificadorVisita);
		
		return visita;
	}

	/**
	 * @return the idVisita
	 */
	public Long getIdVisita() {
		return idVisita;
	}

	/**
	 * @param idVisita the idVisita to set
	 */
	public void setIdVisita(Long idVisita) {
		this.idVisita = idVisita;
	}

	/**
	 * @return the fechaVisita
	 */
	public Date getFechaVisita() {
		return fechaVisita;
	}

	/**
	 * @param fechaVisita the fechaVisita to set
	 */
	public void setFechaVisita(Date fechaVisita) {
		this.fechaVisita = fechaVisita;
	}

	/**
	 * @return the nombresEncargadoVisita
	 */
	public String getNombresEncargadoVisita() {
		return nombresEncargadoVisita;
	}

	/**
	 * @param nombresEncargadoVisita the nombresEncargadoVisita to set
	 */
	public void setNombresEncargadoVisita(String nombresEncargadoVisita) {
		this.nombresEncargadoVisita = nombresEncargadoVisita;
	}


    /**
     * @return the codigoIdentificadorVisita
     */
    public String getCodigoIdentificadorVisita() {
        return codigoIdentificadorVisita;
    }


    /**
     * @param codigoIdentificadorVisita the codigoIdentificadorVisita to set
     */
    public void setCodigoIdentificadorVisita(String codigoIdentificadorVisita) {
        this.codigoIdentificadorVisita = codigoIdentificadorVisita;
    }

}
