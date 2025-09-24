package com.asopagos.dto.fovis;

import java.io.Serializable;

import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción: </b> DTO que almacena los resultados de asignación FOVIS
 * <br/>
 * <b>Historia de Usuario: </b> HU-047
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class ResultadoAsignacionDTO extends PostulacionFOVISModeloDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1879875452457454L;

	/**
	 * Número de radicación de la solicitud de postulación FOVIS
	 */
	private String numeroRadicacion;

	/**
	 * Tipo de identificación del jefe del hogar
	 */
	private TipoIdentificacionEnum tipoIdentificacionJefeHogar;

	/**
	 * Número de identificación del jefe del hogar
	 */
	private String numeroIdentificacionJefeHogar;

	/**
	 * Nombres del jefe del hogar
	 */
	private String nombreJefeHogar;

	/**
	 * Apellidos del jefe del hogar
	 */
	private String apellidoJefeHogar;
	
	/**
     * recurso prioridad de la asignacion
     */
    private String recursoPrioridad;
	

	public ResultadoAsignacionDTO() {
		super();
	}
	
	/**
	 * Constructor
	 */
	public ResultadoAsignacionDTO(PostulacionFOVISModeloDTO postulacionFOVISDTO) {
		super.convertToDTO(postulacionFOVISDTO.convertToEntity());
	}

	/**
	 * Obtiene el valor de numeroRadicacion
	 * 
	 * @return El valor de numeroRadicacion
	 */
	public String getNumeroRadicacion() {
		return numeroRadicacion;
	}

	/**
	 * Establece el valor de numeroRadicacion
	 * 
	 * @param numeroRadicacion
	 *            El valor de numeroRadicacion por asignar
	 */
	public void setNumeroRadicacion(String numeroRadicacion) {
		this.numeroRadicacion = numeroRadicacion;
	}

	/**
	 * Obtiene el valor de tipoIdentificacionJefeHogar
	 * 
	 * @return El valor de tipoIdentificacionJefeHogar
	 */
	public TipoIdentificacionEnum getTipoIdentificacionJefeHogar() {
		return tipoIdentificacionJefeHogar;
	}

	/**
	 * Establece el valor de tipoIdentificacionJefeHogar
	 * 
	 * @param tipoIdentificacionJefeHogar
	 *            El valor de tipoIdentificacionJefeHogar por asignar
	 */
	public void setTipoIdentificacionJefeHogar(TipoIdentificacionEnum tipoIdentificacionJefeHogar) {
		this.tipoIdentificacionJefeHogar = tipoIdentificacionJefeHogar;
	}

	/**
	 * Obtiene el valor de numeroIdentificacionJefeHogar
	 * 
	 * @return El valor de numeroIdentificacionJefeHogar
	 */
	public String getNumeroIdentificacionJefeHogar() {
		return numeroIdentificacionJefeHogar;
	}

	/**
	 * Establece el valor de numeroIdentificacionJefeHogar
	 * 
	 * @param numeroIdentificacionJefeHogar
	 *            El valor de numeroIdentificacionJefeHogar por asignar
	 */
	public void setNumeroIdentificacionJefeHogar(String numeroIdentificacionJefeHogar) {
		this.numeroIdentificacionJefeHogar = numeroIdentificacionJefeHogar;
	}

	/**
	 * Obtiene el valor de nombreJefeHogar
	 * 
	 * @return El valor de nombreJefeHogar
	 */
	public String getNombreJefeHogar() {
		return nombreJefeHogar;
	}

	/**
	 * Establece el valor de nombreJefeHogar
	 * 
	 * @param nombreJefeHogar
	 *            El valor de nombreJefeHogar por asignar
	 */
	public void setNombreJefeHogar(String nombreJefeHogar) {
		this.nombreJefeHogar = nombreJefeHogar;
	}

	/**
	 * Obtiene el valor de apellidoJefeHogar
	 * 
	 * @return El valor de apellidoJefeHogar
	 */
	public String getApellidoJefeHogar() {
		return apellidoJefeHogar;
	}

	/**
	 * Establece el valor de apellidoJefeHogar
	 * 
	 * @param apellidoJefeHogar
	 *            El valor de apellidoJefeHogar por asignar
	 */
	public void setApellidoJefeHogar(String apellidoJefeHogar) {
		this.apellidoJefeHogar = apellidoJefeHogar;
	}
	
	/**
	 * Obtiene el nombre del recurso de la prioridad
	 * 
     * @return the recursoPrioridad
     */
    public String getRecursoPrioridad() {
        return recursoPrioridad;
    }

    /**
     * Establece el nombre del recurso de la prioridad
     * 
     * @param recursoPrioridad
     *        the recursoPrioridad to set
     */
    public void setRecursoPrioridad(String recursoPrioridad) {
        this.recursoPrioridad = recursoPrioridad;
    }
	
}
