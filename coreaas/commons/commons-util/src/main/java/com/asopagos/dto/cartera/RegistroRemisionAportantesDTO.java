/**
 * 
 */
package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * Clase DTO que contiene la información a guardar de la remisión del
 * comunicado.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
@XmlRootElement
public class RegistroRemisionAportantesDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 234654587213841745L;
	/**
	 * Id de la tarea para finalizar.
	 */
	private Long idTarea;
	/**
	 * Número de radicación de la solicitud de gestión de cobro.
	 */
	private String numeroRadicacion;
	/**
	 * Fecha de remisioón del comunicado.
	 */
	private Long fechaRemision;
	/**
	 * Hora de remisión del comunicado.
	 */
	private Long horaRemision;
	/**
	 * Id del documento adjunto.
	 */
	private String idDocumento;
	/**
	 * Observaciones ingresadas por el usuario back.
	 */
	private String observaciones;
	/**
	 * Atributo que indica si se esta guardando o confirmando.
	 */
	private Boolean guardar;
	/**
	 * Lista de los aportantes.
	 */
	private List<AportanteRemisionComunicadoDTO> aportantes;

	/**
	 * Tipo de acción de cobro
	 */
	private TipoAccionCobroEnum accionCobro;
	
	/**
	 * Token de autorazación ante el BPM. No se setea desde la pantalla
	 * Solo se usa para invocar el servicio asincrono.
	 */
	private String token;
	
	/**
	 * Etiqueta de la plantilla del comnicado que se esta gestionando en el momento de la segunda remisión cuando se va por actaulización de datos 
	 * para actualizar los datos también presentados en el comunicado 
	 */
	private EtiquetaPlantillaComunicadoEnum plantilla;

	/**
     * userDTO para obtener el usuario que esta ejecutando la tarea
     * debido a que el servicio asincrono pierde el contexto de usuario.
     */
	private UserDTO userDTO;
	
    /**
	 * Método que retorna el valor de idTarea.
	 * 
	 * @return valor de idTarea.
	 */
	public Long getIdTarea() {
		return idTarea;
	}

	/**
	 * Método encargado de modificar el valor de idTarea.
	 * 
	 * @param valor
	 *            para modificar idTarea.
	 */
	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}

	/**
	 * Método que retorna el valor de numeroRadicacion.
	 * 
	 * @return valor de numeroRadicacion.
	 */
	public String getNumeroRadicacion() {
		return numeroRadicacion;
	}

	/**
	 * Método encargado de modificar el valor de numeroRadicacion.
	 * 
	 * @param valor
	 *            para modificar numeroRadicacion.
	 */
	public void setNumeroRadicacion(String numeroRadicacion) {
		this.numeroRadicacion = numeroRadicacion;
	}

	/**
	 * Método que retorna el valor de fechaRemision.
	 * 
	 * @return valor de fechaRemision.
	 */
	public Long getFechaRemision() {
		return fechaRemision;
	}

	/**
	 * Método encargado de modificar el valor de fechaRemision.
	 * 
	 * @param valor
	 *            para modificar fechaRemision.
	 */
	public void setFechaRemision(Long fechaRemision) {
		this.fechaRemision = fechaRemision;
	}

	/**
	 * Método que retorna el valor de horaRemision.
	 * 
	 * @return valor de horaRemision.
	 */
	public Long getHoraRemision() {
		return horaRemision;
	}

	/**
	 * Método encargado de modificar el valor de horaRemision.
	 * 
	 * @param valor
	 *            para modificar horaRemision.
	 */
	public void setHoraRemision(Long horaRemision) {
		this.horaRemision = horaRemision;
	}

	/**
	 * Método que retorna el valor de idDocumento.
	 * 
	 * @return valor de idDocumento.
	 */
	public String getIdDocumento() {
		return idDocumento;
	}

	/**
	 * Método encargado de modificar el valor de idDocumento.
	 * 
	 * @param valor
	 *            para modificar idDocumento.
	 */
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	/**
	 * Método que retorna el valor de observaciones.
	 * 
	 * @return valor de observaciones.
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * Método encargado de modificar el valor de observaciones.
	 * 
	 * @param valor
	 *            para modificar observaciones.
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * Método que retorna el valor de guardar.
	 * 
	 * @return valor de guardar.
	 */
	public Boolean getGuardar() {
		return guardar;
	}

	/**
	 * Método encargado de modificar el valor de guardar.
	 * 
	 * @param valor
	 *            para modificar guardar.
	 */
	public void setGuardar(Boolean guardar) {
		this.guardar = guardar;
	}

	/**
	 * Método que retorna el valor de aportantes.
	 * 
	 * @return valor de aportantes.
	 */
	public List<AportanteRemisionComunicadoDTO> getAportantes() {
		return aportantes;
	}

	/**
	 * Método encargado de modificar el valor de aportantes.
	 * 
	 * @param valor
	 *            para modificar aportantes.
	 */
	public void setAportantes(List<AportanteRemisionComunicadoDTO> aportantes) {
		this.aportantes = aportantes;
	}

	/**
	 * Obtiene el valor de accionCobro
	 * 
	 * @return El valor de accionCobro
	 */
	public TipoAccionCobroEnum getAccionCobro() {
		return accionCobro;
	}

	/**
	 * Establece el valor de accionCobro
	 * 
	 * @param accionCobro
	 *            El valor de accionCobro por asignar
	 */
	public void setAccionCobro(TipoAccionCobroEnum accionCobro) {
		this.accionCobro = accionCobro;
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

    /**
     * @return the plantilla
     */
    public EtiquetaPlantillaComunicadoEnum getPlantilla() {
        return plantilla;
    }

    /**
     * @param plantilla the plantilla to set
     */
    public void setPlantilla(EtiquetaPlantillaComunicadoEnum plantilla) {
        this.plantilla = plantilla;
    }
    
    /**
     * @return the userDTO
     */
    public UserDTO getUserDTO() {
        return userDTO;
    }
    
    /**
     * @param userDTO the userDTO to set
     */
    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
	
}
