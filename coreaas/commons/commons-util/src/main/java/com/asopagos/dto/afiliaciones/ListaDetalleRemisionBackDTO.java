package com.asopagos.dto.afiliaciones;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripción:</b> DTO para los detalles del DTO RemisionBackDTO 
 * del servicio generarListadoSolicitudesRemisionBack HU
 * <b>Historia de Usuario:</b> HU-086
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
@XmlRootElement
public class ListaDetalleRemisionBackDTO {
	
	private String remitente;
	
	private String destinatario;
	
	private String sedeDestinatario;
	
	private String idInstanciaProceso;
	
	private List<ItemDetalleRemisionBackDTO> items;

	/**
	 * @return the remitente
	 */
	public String getRemitente() {
		return remitente;
	}

	/**
	 * @param remitente the remitente to set
	 */
	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}

	/**
	 * @return the destinatario
	 */
	public String getDestinatario() {
		return destinatario;
	}

	/**
	 * @param destinatario the destinatario to set
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	/**
	 * @return the sedeDestinatario
	 */
	public String getSedeDestinatario() {
		return sedeDestinatario;
	}

	/**
	 * @param sedeDestinatario the sedeDestinatario to set
	 */
	public void setSedeDestinatario(String sedeDestinatario) {
		this.sedeDestinatario = sedeDestinatario;
	}

    /**
     * @return the idInstanciaProceso
     */
    public String getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    /**
     * @param idInstanciaProceso the idInstanciaProceso to set
     */
    public void setIdInstanciaProceso(String idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }

    /**
	 * @return the items
	 */
	public List<ItemDetalleRemisionBackDTO> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<ItemDetalleRemisionBackDTO> items) {
		this.items = items;
	}

    @Override
    public String toString() {
        return "ListaDetalleRemisionBackDTO [remitente=" + remitente + ", destinatario=" + destinatario + ", sedeDestinatario="
                + sedeDestinatario + ", idInstanciaProceso=" + idInstanciaProceso + ", items=" + items + "]";
    }
	
	
}
