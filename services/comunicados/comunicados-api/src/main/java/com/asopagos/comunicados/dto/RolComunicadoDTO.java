/**
 * 
 */
package com.asopagos.comunicados.dto;

import java.io.Serializable;
import java.util.List;
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
public class RolComunicadoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Etiqueta comunicado 
	 */
	private EtiquetaPlantillaComunicadoEnum etiquetaComunicado;
	/**
	 * Lista de roles pertenecientes a la etiqueta 
	 */
	private List<RolContactoEnum> lstRolesContacto;

	public RolComunicadoDTO() {
	}

	/**
	 * @return the lstRolesContacto
	 */
	public List<RolContactoEnum> getLstRolesContacto() {
		return lstRolesContacto;
	}

	/**
	 * @param lstRolesContacto
	 *            the lstRolesContacto to set
	 */
	public void setLstRolesContacto(List<RolContactoEnum> lstRolesContacto) {
		this.lstRolesContacto = lstRolesContacto;
	}

	/**
	 * @return the etiquetaComunicado
	 */
	public EtiquetaPlantillaComunicadoEnum getEtiquetaComunicado() {
		return etiquetaComunicado;
	}

	/**
	 * @param etiquetaComunicado
	 *            the etiquetaComunicado to set
	 */
	public void setEtiquetaComunicado(EtiquetaPlantillaComunicadoEnum etiquetaComunicado) {
		this.etiquetaComunicado = etiquetaComunicado;
	}

}
