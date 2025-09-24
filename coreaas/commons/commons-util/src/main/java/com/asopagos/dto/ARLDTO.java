package com.asopagos.dto;

import java.io.Serializable;
import com.asopagos.entidades.transversal.personas.ARL;

public class ARLDTO implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	
	private Short idARL;

	private String nombre;

	public static ARLDTO convertArlToDTO(ARL arl) {
        ARLDTO arlDTO = new ARLDTO();
        arlDTO.setIdARL(arl.getIdARL());
        arlDTO.setNombre(arl.getNombre());
        return arlDTO;
    }
	/**
	 * @return the idARL
	 */
	public Short getIdARL() {
		return idARL;
	}

	/**
	 * @param idARL the idARL to set
	 */
	public void setIdARL(Short idARL) {
		this.idARL = idARL;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
