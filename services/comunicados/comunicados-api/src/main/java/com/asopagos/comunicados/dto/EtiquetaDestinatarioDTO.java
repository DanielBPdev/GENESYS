/**
 * 
 */
package com.asopagos.comunicados.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.notificaciones.RolContactoEnum;

/**
 * <b>Descripcion:</b> <br/>
 * <b>Historia de Usuario:</b> <br/>
 *
 * @author <a href="mailto:jusanchez@heinsohn.com.co"> jusanchez</a>
 */
@XmlRootElement
public class EtiquetaDestinatarioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Etiqueta a la que pertenece el comunicado
	 */
	private EtiquetaPlantillaComunicadoEnum etiqueta;
	/**
	 * Nombre del rol contacto
	 */
	private RolContactoEnum rolContacto;

	/**
	 * Método constructor por defecto
	 */
	public EtiquetaDestinatarioDTO() {

	}

	public EtiquetaDestinatarioDTO(EtiquetaPlantillaComunicadoEnum etiqueta, RolContactoEnum rolConctacto) {
		super();
		this.etiqueta = etiqueta;
		this.rolContacto = rolConctacto;
	}

	/**
	 * @return the etiqueta
	 */
	public EtiquetaPlantillaComunicadoEnum getEtiqueta() {
		return etiqueta;
	}

	/**
	 * @param etiqueta
	 *            the etiqueta to set
	 */
	public void setEtiqueta(EtiquetaPlantillaComunicadoEnum etiqueta) {
		this.etiqueta = etiqueta;
	}

	/**
	 * @return the rolConctacto
	 */
	public RolContactoEnum getRolContacto() {
		return rolContacto;
	}

	/**
	 * @param rolContacto
	 *            the rolConctacto to set
	 */
	public void setRolContacto(RolContactoEnum rolContacto) {
		this.rolContacto = rolContacto;
	}

}
