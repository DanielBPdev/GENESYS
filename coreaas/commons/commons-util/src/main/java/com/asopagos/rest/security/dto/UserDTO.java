package com.asopagos.rest.security.dto;

import java.io.Serializable;
import java.util.List;

/**
 * <b>Descripción:</b> DTO para los datos del usuario que realiza peticiones
 * sobre el sistema
 * <b>Historia de Usuario:</b> Transversal
 *
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class UserDTO implements Serializable {

    /**
     * Serial version ID
     */
    private static final long serialVersionUID = -380253835596532641L;

    /**
     * Nombre del usuario
     */
    private String nombreUsuario;

    /**
     * Código de la sede para la caja de compensación
     */
    private String sedeCajaCompensacion;

    /**
     * Ciudad de la sede para la caja de compensación
     */
    private String ciudadSedeCajaCompensacion;

    /**
     * Roles relacionados al usuario
     */
    private List<String> roles;

    /**
     * Correo electronico del usuario
     */
    private String email;

    /**
     * Auditoria
     */
    private AuditDTO auditoria = new AuditDTO();

    /**
     * Grupos a los que pertenece el usuario
     */
    private List<String> grupos;

    /**
	 * Token de autorazación ante el BPM. No se setea desde la pantalla
	 * Solo se usa para invocar el servicio asincrono.
	 */
	private String token;
    
    /**
     * Método constructor
     */
    public UserDTO() {
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *        the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario
     *        the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return the sedeCajaCompensacion
     */
    public String getSedeCajaCompensacion() {
        return sedeCajaCompensacion;
    }

    /**
     * @param sedeCajaCompensacion
     *        the sedeCajaCompensacion to set
     */
    public void setSedeCajaCompensacion(String sedeCajaCompensacion) {
        this.sedeCajaCompensacion = sedeCajaCompensacion;
    }

    /**
     * @return the ciudadSedeCajaCompensacion
     */
    public String getCiudadSedeCajaCompensacion() {
        return ciudadSedeCajaCompensacion;
    }

    /**
     * @param ciudadSedeCajaCompensacion
     *        the ciudadSedeCajaCompensacion to set
     */
    public void setCiudadSedeCajaCompensacion(String ciudadSedeCajaCompensacion) {
        this.ciudadSedeCajaCompensacion = ciudadSedeCajaCompensacion;
    }

    /**
     * @return the roles
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * @param roles
     *        the roles to set
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    /**
     * @return the auditoria
     */
    public AuditDTO getAuditoria() {
        return auditoria;
    }

    /**
     * @param auditoria
     *        the auditoria to set
     */
    public void setAuditoria(AuditDTO auditoria) {
        this.auditoria = auditoria;
    }

    /**
     * @return the grupos
     */
    public List<String> getGrupos() {
        return grupos;
    }

    /**
     * @param grupos
     *        the grupos to set
     */
    public void setGrupos(List<String> grupos) {
        this.grupos = grupos;
    }
    
    /**
     * @return the token
     */
    public String getToken() {
		return token;
	}

    /**
     * @param token the token to set
     */
	public void setToken(String token) {
		this.token = token;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombreUsuario == null) ? 0 : nombreUsuario.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserDTO other = (UserDTO) obj;
        if (nombreUsuario == null) {
            if (other.nombreUsuario != null)
                return false;
        }
        else if (!nombreUsuario.equals(other.nombreUsuario))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "nombreUsuario=" + nombreUsuario + ", sedeCajaCompensacion=" + sedeCajaCompensacion + ", ciudadSedeCajaCompensacion=" + ciudadSedeCajaCompensacion + ", roles=" + roles + ", email=" + email + ", auditoria=" + auditoria + ", grupos=" + grupos + ", token=" + token + '}';
    }

}
