package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción:</b> DTO para la lista de chequeo
 * <b>Historia de Usuario:</b> 111-059, 112-118, 121-139
 *
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@XmlRootElement
public class ListaChequeoDTO implements Serializable {
    

    @NotNull
    private TipoIdentificacionEnum tipoIdentificacion;
    
    @NotNull
    private String numeroIdentificacion;
    
    @NotNull
    private Long idSolicitudGlobal;
    
    @NotNull
    @Valid
    private List<ItemChequeoDTO> listaChequeo;
    
    /**Bandera para saber si la lista de chequeo pertenece a FOVIS.*/
    private Boolean listaFOVIS;
    
    /**
     * Fecha de la recepción de los documentos registrada en la lista de chequeo del afiliado principal
     */
    private Long fechaRecepcionDocumentos;

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the idSolicitudGlobal
     */
    public Long getIdSolicitudGlobal() {
        return idSolicitudGlobal;
    }

    /**
     * @param idSolicitudGlobal the idSolicitudGlobal to set
     */
    public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
        this.idSolicitudGlobal = idSolicitudGlobal;
    }

    /**
     * @return the listaChequeo
     */
    public List<ItemChequeoDTO> getListaChequeo() {
        return listaChequeo;
    }

    /**
     * @param listaChequeo the listaChequeo to set
     */
    public void setListaChequeo(List<ItemChequeoDTO> listaChequeo) {
        this.listaChequeo = listaChequeo;
    }

	/**
	 * @return the listaFOVIS
	 */
	public Boolean getListaFOVIS() {
		return listaFOVIS;
	}

	/**
	 * @param listaFOVIS the listaFOVIS to set
	 */
	public void setListaFOVIS(Boolean listaFOVIS) {
		this.listaFOVIS = listaFOVIS;
	}

    /**
     * @return the fechaRecepcionDocumentos
     */
    public Long getFechaRecepcionDocumentos() {
        return fechaRecepcionDocumentos;
    }

    /**
     * @param fechaRecepcionDocumentos the fechaRecepcionDocumentos to set
     */
    public void setFechaRecepcionDocumentos(Long fechaRecepcionDocumentos) {
        this.fechaRecepcionDocumentos = fechaRecepcionDocumentos;
    }

}
