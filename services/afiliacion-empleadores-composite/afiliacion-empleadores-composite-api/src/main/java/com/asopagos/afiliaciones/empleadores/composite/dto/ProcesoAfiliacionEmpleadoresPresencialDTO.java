package com.asopagos.afiliaciones.empleadores.composite.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.afiliaciones.CausaIntentoFallidoAfiliacionEnum;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la gestión de listas de chequeo para la afiliación de empleadores
 * <b>Historia de Usuario:</b> HU-TRA-061 Administración general de listas de
 * chequeo
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcesoAfiliacionEmpleadoresPresencialDTO implements Serializable {

	private Boolean estado;

	private Empleador empleador;
	
	private List<ItemChequeoDTO> listaChequeo;

	private String usuarioRadicador;

	private FormatoEntregaDocumentoEnum metodoEnvio;

	private ClasificacionEnum clasificacion;

	private TipoTransaccionEnum tipoTransaccion;

	private CausaIntentoFallidoAfiliacionEnum causaIntentoFallido;

	/**
	 * @return the estado
	 */
	public Boolean getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	/**
	 * @return the empleador
	 */
	public Empleador getEmpleador() {
		return empleador;
	}

	/**
	 * @param empleador
	 *            the empleador to set
	 */
	public void setEmpleador(Empleador empleador) {
		this.empleador = empleador;
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
	 * @return the usuarioRadicador
	 */
	public String getUsuarioRadicador() {
		return usuarioRadicador;
	}

	/**
	 * @param usuarioRadicador
	 *            the usuarioRadicador to set
	 */
	public void setUsuarioRadicador(String usuarioRadicador) {
		this.usuarioRadicador = usuarioRadicador;
	}

	/**
	 * 
	 * @return the metodoEnvio
	 */
	public FormatoEntregaDocumentoEnum getMetodoEnvio() {
		return metodoEnvio;
	}

	/**
	 * 
	 * @param metodoEnvio
	 *            the metodoEnvio to set
	 */
	public void setMetodoEnvio(FormatoEntregaDocumentoEnum metodoEnvio) {
		this.metodoEnvio = metodoEnvio;
	}


	/**
	 * @return the tipoTransaccion
	 */
	public TipoTransaccionEnum getTipoTransaccion() {
		return tipoTransaccion;
	}

	/**
	 * @param tipoTransaccion
	 *            the tipoTransaccion to set
	 */
	public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	/**
	 * @return the causaIntentoFallido
	 */
	public CausaIntentoFallidoAfiliacionEnum getCausaIntentoFallido() {
		return causaIntentoFallido;
	}

	/**
	 * @param causaIntentoFallido
	 *            the causaIntentoFallido to set
	 */
	public void setCausaIntentoFallido(CausaIntentoFallidoAfiliacionEnum causaIntentoFallido) {
		this.causaIntentoFallido = causaIntentoFallido;
	}

	/**
	 * @return
	 */
	public ClasificacionEnum getClasificacion() {
		return clasificacion;
	}

	/**
	 * @param clasificacion
	 */
	public void setClasificacion(ClasificacionEnum clasificacion) {
		this.clasificacion = clasificacion;
	}

}
