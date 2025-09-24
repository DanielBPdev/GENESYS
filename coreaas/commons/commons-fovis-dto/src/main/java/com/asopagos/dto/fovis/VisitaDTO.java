package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.util.List;

import com.asopagos.dto.modelo.CondicionVisitaModeloDTO;
import com.asopagos.entidades.ccf.fovis.CondicionVisita;
import com.asopagos.entidades.ccf.fovis.Visita;

/**
 * <b>Descripción: </b> DTO que representa los datos asociados a una visita <br/>
 * <b>Historia de Usuario: HU-058 </b>
 * 
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class VisitaDTO implements Serializable{

    /**
	 * Serial
	 */
	private static final long serialVersionUID = -7282579483824664942L;

	/**
     * Llave primaria
     */
    private Long idVisita;
    
    /**
     * Fecha visita
     */
    private Long fechaVisita;
    
    /**
     * Nombre del encargado de la visita
     */
    private String nombresEncargadoVisita;
    
    /**
     * Ubicacion del documento a adjuntar
     */
    private String codigoIdentificadorVisita;
    
    /**
     * Listado de condiciones asociadas a una visita
     */
    private List<CondicionVisitaModeloDTO> listaCondiciones;
    
    /**
     * Condicion asociadas a una visita
     */
    private CondicionVisitaModeloDTO condicionDTO;
    
    /**
     * Constructor por defecto
     */
    public VisitaDTO() {
		// TODO Auto-generated constructor stub
	}
    
    /**
     * Constructor a partir de una entidad Visita
     * @param visita
     */
    public VisitaDTO(Visita visita) {
    	
    	this.setFechaVisita(visita.getFechaVisita().getTime());
    	this.setCodigoIdentificadorVisita(visita.getCodigoIdentificadorVisita());
    	this.setIdVisita(visita.getIdVisita());
    	this.setNombresEncargadoVisita(visita.getNombresEncargadoVisita());
    }
    
	/**
	 * Constructor a partir de una entidad Visita y las condiciones
	 * 
	 * @param visita
	 */
	public VisitaDTO(Visita visita, CondicionVisita cov) {

		this.setFechaVisita(visita.getFechaVisita().getTime());
		this.setCodigoIdentificadorVisita(visita.getCodigoIdentificadorVisita());
		this.setIdVisita(visita.getIdVisita());
		this.setNombresEncargadoVisita(visita.getNombresEncargadoVisita());
		// Se asocian las condiciones a la vista
		condicionDTO = new CondicionVisitaModeloDTO(cov);
	}
	/**
	 * @return the idVisita
	 */
	public Long getIdVisita(){
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
	public Long getFechaVisita() {
		return fechaVisita;
	}

	/**
	 * @param fechaVisita the fechaVisita to set
	 */
	public void setFechaVisita(Long fechaVisita) {
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

	/**
	 * @return the listaCondiciones
	 */
	public List<CondicionVisitaModeloDTO> getListaCondiciones() {
		return listaCondiciones;
	}

	/**
	 * @param listaCondiciones the listaCondiciones to set
	 */
	public void setListaCondiciones(List<CondicionVisitaModeloDTO> listaCondiciones) {
		this.listaCondiciones = listaCondiciones;
	}

	/**
	 * @return the condicionDTO
	 */
	public CondicionVisitaModeloDTO getCondicionDTO() {
		return condicionDTO;
	}

	/**
	 * @param condicionDTO the condicionDTO to set
	 */
	public void setCondicionDTO(CondicionVisitaModeloDTO condicionDTO) {
		this.condicionDTO = condicionDTO;
	}


    
    
}
