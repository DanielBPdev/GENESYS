/**
 * 
 */
package com.asopagos.usuarios.dto;

/**
 * @author alopez
 *
 */
public class UsuarioEmpleadorDTO extends UsuarioDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4030013520449771431L;
	
	/**
	 * Solcitud global asociada al proceso a conveniencia.
	 */
	private Long idSolicitudGlobal;

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
}
