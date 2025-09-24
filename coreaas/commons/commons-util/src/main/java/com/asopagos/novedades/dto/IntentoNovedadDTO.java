package com.asopagos.novedades.dto;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.afiliaciones.CausaIntentoFallidoNovedadEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * Clase DTO para el intento de novedad.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
@XmlRootElement
public class IntentoNovedadDTO{
    
	/**
	 * Código identificador de llave primaria del intento de novedad 
	 */  
    private Long idIntentoNovedad;
    
	/**
	 * Id que identifica a la solicitud asociada al intento de novedad
	 */
    private Long idSolicitud;
    
    /**
     * Descripción de la causa por la que un intento de novedad no resulta exitoso
     */
    private CausaIntentoFallidoNovedadEnum causaIntentoFallido;
    
    /**
     * Descripción del tipo de transacción del proceso
     */
    private TipoTransaccionEnum tipoTransaccion;

    /**
     * Fecha de incio del proceso
     */
    private Date fechaCreacion;
    
    /**
     * Usuario que realiza el intento de novedad.
     */
    private String usuarioCreacion;
    
    /**
     * Clasificación asociada al intento de novedad.
     */
    private ClasificacionEnum clasificacion;
    
    /**
     * Canal de recepción.
     */
    private CanalRecepcionEnum canalRecepcion;
    
    /**
     * Id del empleador
     */
    private Long idEmpleador;
    
    /**
     * Tipo identificacion de la persona para una novedad de personas..
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    
    /**
     * Número identificacion de la persona para una novedad de personas.
     */
    private String numeroIdentificacion;
    
	/**
	 * Excepción tipo dos.
	 */
	private Boolean excepcionTipoDos;
    
    /**
     * Requisitos asociados al momento de intentar la novedad.
     */
    private List<Long> requisitos;
    
    /**
     * Relacion de una novedad de personas con el rol afiliado (si aplica)
     */
    private Long idRolAfiliado;
    /**
     * Relacion de una novedad de perosnas con el id del beneficiario (si aplica)
     */
    private Long idBeneficiario;
    
    /**
     * Atributo que contien 
     */
    private Long idRegistroDetallado;
    
	/**
	 * Id Persona
	 */
    private Long idPersona; 


	private Long idRegistroDetalladoNovedad;
	/**
	 * Método que retorna el valor de idIntentoNovedad.
	 * @return valor de idIntentoNovedad.
	 */
	public Long getIdIntentoNovedad() {
		return idIntentoNovedad;
	}

	/**
	 * Método encargado de modificar el valor de idIntentoNovedad.
	 * @param valor para modificar idIntentoNovedad.
	 */
	public void setIdIntentoNovedad(Long idIntentoNovedad) {
		this.idIntentoNovedad = idIntentoNovedad;
	}

	/**
	 * Método que retorna el valor de idSolicitud.
	 * @return valor de idSolicitud.
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * Método encargado de modificar el valor de idSolicitud.
	 * @param valor para modificar idSolicitud.
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * Método que retorna el valor de causaIntentoFallido.
	 * @return valor de causaIntentoFallido.
	 */
	public CausaIntentoFallidoNovedadEnum getCausaIntentoFallido() {
		return causaIntentoFallido;
	}

	/**
	 * Método encargado de modificar el valor de causaIntentoFallido.
	 * @param valor para modificar causaIntentoFallido.
	 */
	public void setCausaIntentoFallido(CausaIntentoFallidoNovedadEnum causaIntentoFallido) {
		this.causaIntentoFallido = causaIntentoFallido;
	}

	/**
	 * Método que retorna el valor de tipoTransaccion.
	 * @return valor de tipoTransaccion.
	 */
	public TipoTransaccionEnum getTipoTransaccion() {
		return tipoTransaccion;
	}

	/**
	 * Método encargado de modificar el valor de tipoTransaccion.
	 * @param valor para modificar tipoTransaccion.
	 */
	public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	/**
	 * Método que retorna el valor de fechaCreacion.
	 * @return valor de fechaCreacion.
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Método encargado de modificar el valor de fechaCreacion.
	 * @param valor para modificar fechaCreacion.
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Método que retorna el valor de usuarioCreacion.
	 * @return valor de usuarioCreacion.
	 */
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	/**
	 * Método encargado de modificar el valor de usuarioCreacion.
	 * @param valor para modificar usuarioCreacion.
	 */
	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	/**
	 * Método que retorna el valor de clasificacion.
	 * @return valor de clasificacion.
	 */
	public ClasificacionEnum getClasificacion() {
		return clasificacion;
	}

	/**
	 * Método encargado de modificar el valor de clasificacion.
	 * @param valor para modificar clasificacion.
	 */
	public void setClasificacion(ClasificacionEnum clasificacion) {
		this.clasificacion = clasificacion;
	}

	/**
	 * Método que retorna el valor de canalRecepcion.
	 * @return valor de canalRecepcion.
	 */
	public CanalRecepcionEnum getCanalRecepcion() {
		return canalRecepcion;
	}

	/**
	 * Método encargado de modificar el valor de canalRecepcion.
	 * @param valor para modificar canalRecepcion.
	 */
	public void setCanalRecepcion(CanalRecepcionEnum canalRecepcion) {
		this.canalRecepcion = canalRecepcion;
	}

	/**
	 * Método que retorna el valor de requisitos.
	 * @return valor de requisitos.
	 */
	public List<Long> getRequisitos() {
		return requisitos;
	}

	/**
	 * Método encargado de modificar el valor de requisitos.
	 * @param valor para modificar requisitos.
	 */
	public void setRequisitos(List<Long> requisitos) {
		this.requisitos = requisitos;
	}

	/**
	 * Método que retorna el valor de idEmpleador.
	 * @return valor de idEmpleador.
	 */
	public Long getIdEmpleador() {
		return idEmpleador;
	}

	/**
	 * Método encargado de modificar el valor de idEmpleador.
	 * @param valor para modificar idEmpleador.
	 */
	public void setIdEmpleador(Long idEmpleador) {
		this.idEmpleador = idEmpleador;
	}

	/**
	 * Método que retorna el valor de tipoIdentificacion.
	 * @return valor de tipoIdentificacion.
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacion.
	 * @param valor para modificar tipoIdentificacion.
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacion.
	 * @return valor de numeroIdentificacion.
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacion.
	 * @param valor para modificar numeroIdentificacion.
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * Método que retorna el valor de excepcionTipoDos.
	 * @return valor de excepcionTipoDos.
	 */
	public Boolean getExcepcionTipoDos() {
		return excepcionTipoDos;
	}

	/**
	 * Método encargado de modificar el valor de excepcionTipoDos.
	 * @param valor para modificar excepcionTipoDos.
	 */
	public void setExcepcionTipoDos(Boolean excepcionTipoDos) {
		this.excepcionTipoDos = excepcionTipoDos;
	}

	/**
	 * Método que retorna el valor de idRolAfiliado.
	 * @return valor de idRolAfiliado.
	 */
	public Long getIdRolAfiliado() {
		return idRolAfiliado;
	}

	/**
	 * Método encargado de modificar el valor de idRolAfiliado.
	 * @param valor para modificar idRolAfiliado.
	 */
	public void setIdRolAfiliado(Long idRolAfiliado) {
		this.idRolAfiliado = idRolAfiliado;
	}

	/**
	 * Método que retorna el valor de idBeneficiario.
	 * @return valor de idBeneficiario.
	 */
	public Long getIdBeneficiario() {
		return idBeneficiario;
	}

	/**
	 * Método encargado de modificar el valor de idBeneficiario.
	 * @param valor para modificar idBeneficiario.
	 */
	public void setIdBeneficiario(Long idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}

    /**
     * Método que retorna el valor de idRegistroDetallado.
     * @return valor de idRegistroDetallado.
     */
    public Long getIdRegistroDetallado() {
        return idRegistroDetallado;
    }

    /**
     * Método encargado de modificar el valor de idRegistroDetallado.
     * @param valor para modificar idRegistroDetallado.
     */
    public void setIdRegistroDetallado(Long idRegistroDetallado) {
        this.idRegistroDetallado = idRegistroDetallado;
    }

		/**
	 * Método que idPersona
	 * @return valor de idSolicitud.
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * Método encargado de modificar el valor de idSolicitud.
	 * @param valor para modificar idSolicitud.
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	        /**
     * @return the idRegistroDetalladoNovedad
     */
    public Long getIdRegistroDetalladoNovedad() {
        return idRegistroDetalladoNovedad;
    }

    /**
     * @param idRegistroDetalladoNovedad the idRegistroDetalladoNovedad to set
     */
    public void setIdRegistroDetalladoNovedad(Long idRegistroDetalladoNovedad) {
        this.idRegistroDetalladoNovedad = idRegistroDetalladoNovedad;
    }

}
