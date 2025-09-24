/**
 * 
 */
package com.asopagos.aportes.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO que se encarga de consultar cotizantes en proceso de gestión de
 * devolución de aportes.
 * 
 * @author <a href="mailto:criparra@heinsohn.com.co">Cristian David Parra
 *         Zuluaga</a>
 */
@XmlRootElement
public class ConsultarCotizanteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5935850087254719947L;

	/**
	 * Id del aporte seleccionado.
	 */
	private Long idAporte;

	/**
	 * Tipo de identificación del cotizante.
	 */
	private TipoIdentificacionEnum tipoIdentificacion;

	/**
	 * Número de identificación del cotizante.
	 */
	private String numeroIdentificacion;

	/**
	 * Digito de verificación en caso de que sea NIT.
	 */
	private Short dv;

	/**
	 * Primer nombre del aportante(caso Persona).
	 */
	private String primerNombre;

	/**
	 * Segundo nombre del aportante(caso Persona).
	 */
	private String segundoNombre;

	/**
	 * Primer apellido del aportante (caso Persona).
	 */
	private String primerApellido;

	/**
	 * Segundo apellido del aportante (caso Persona).
	 */
	private String segundoApellido;

	/**
	 * Número de operación al nivel de cotizante
	 */
	private Long numeroOperacionCotizante;
	
	/**
	 * Método que retorna el valor de idAporte.
	 * 
	 * @return valor de idAporte.
	 */
	public Long getIdAporte() {
		return idAporte;
	}

	/**
	 * Método encargado de modificar el valor de idAporte.
	 * 
	 * @param valor
	 *            para modificar idAporte.
	 */
	public void setIdAporte(Long idAporte) {
		this.idAporte = idAporte;
	}

	/**
	 * Método que retorna el valor de tipoIdentificacion.
	 * 
	 * @return valor de tipoIdentificacion.
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacion.
	 * 
	 * @param valor
	 *            para modificar tipoIdentificacion.
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacion.
	 * 
	 * @return valor de numeroIdentificacion.
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacion.
	 * 
	 * @param valor
	 *            para modificar numeroIdentificacion.
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * Método que retorna el valor de dv.
	 * 
	 * @return valor de dv.
	 */
	public Short getDv() {
		return dv;
	}

	/**
	 * Método encargado de modificar el valor de dv.
	 * 
	 * @param valor
	 *            para modificar dv.
	 */
	public void setDv(Short dv) {
		this.dv = dv;
	}

	/**
	 * Método que retorna el valor de primerNombre.
	 * 
	 * @return valor de primerNombre.
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}

	/**
	 * Método encargado de modificar el valor de primerNombre.
	 * 
	 * @param valor
	 *            para modificar primerNombre.
	 */
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	/**
	 * Método que retorna el valor de segundoNombre.
	 * 
	 * @return valor de segundoNombre.
	 */
	public String getSegundoNombre() {
		return segundoNombre;
	}

	/**
	 * Método encargado de modificar el valor de segundoNombre.
	 * 
	 * @param valor
	 *            para modificar segundoNombre.
	 */
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	/**
	 * Método que retorna el valor de primerApellido.
	 * 
	 * @return valor de primerApellido.
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * Método encargado de modificar el valor de primerApellido.
	 * 
	 * @param valor
	 *            para modificar primerApellido.
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	/**
	 * Método que retorna el valor de segundoApellido.
	 * 
	 * @return valor de segundoApellido.
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}

	/**
	 * Método encargado de modificar el valor de segundoApellido.
	 * 
	 * @param valor
	 *            para modificar segundoApellido.
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

    /**
     * @return the numeroOperacionCotizante
     */
    public Long getNumeroOperacionCotizante() {
        return numeroOperacionCotizante;
    }

    /**
     * @param numeroOperacionCotizante the numeroOperacionCotizante to set
     */
    public void setNumeroOperacionCotizante(Long numeroOperacionCotizante) {
        this.numeroOperacionCotizante = numeroOperacionCotizante;
    }
}
