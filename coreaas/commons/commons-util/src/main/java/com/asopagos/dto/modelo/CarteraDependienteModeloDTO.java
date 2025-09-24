package com.asopagos.dto.modelo;

import java.math.BigDecimal;

import com.asopagos.entidades.ccf.cartera.CarteraDependiente;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.cartera.EstadoOperacionCarteraEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción: </b> Entidad que representa los datos de la tabla
 * <code>CarteraDependiente</code> para gestión de mora en aportes de afiliados
 * dependientes <br/>
 * <b>Historia de Usuario: </b> HU-169
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
public class CarteraDependienteModeloDTO {
	/**
	 * Identificador único, llave primaria
	 */
	private Long idCarteraDependiente;

	/**
	 * Valor de deuda presunta
	 */
	private BigDecimal deudaPresunta;

	/**
	 * Valor de deuda real
	 */
	private BigDecimal deudaReal;

	/**
	 * Indica si el trabajador hace parte de la cartera del empleador
	 */
	private EstadoAfiliadoEnum estadoCotizante;

	/**
	 * Indica si el trabajador hace parte de la cartera del empleador
	 */
	private EstadoOperacionCarteraEnum estadoOperacion;

	/**
	 * Identificador de la persona reportada en cartera dependiente
	 */
	private Long idPersona;

	/**
	 * Identificador de la cartera del empleador relacionado al trabajador
	 * dependiente
	 */
	private Long idCartera;

	/**
	 * Tipo de identificacion del cotizante
	 */
	private TipoIdentificacionEnum tipoIdentificacion;

	/**
	 * Número de identificación del cotizante
	 */
	private String numeroIdentificacion;

	/**
	 * Primer nombre del cotizante
	 */

	private String primerNombre;
	/**
	 * Segundo Nombre del cotizante
	 */
	private String segundoNombre;

	/**
	 * Primer Apellido del cotizante
	 */
	private String primerApellido;

	/**
	 * Segundo Apellido del cotizante
	 */
	private String segundoApellido;

	/**
	 * Indica si el trabajador fue agregado manualmente mediante la
	 * funcionalidad de gestión de deuda HU-239
	 */
	private Boolean agregadoManualmente;
	
	/**
     * Identificador del empleador para poder obtener el estado del trabajador
     */
    private Long idEmpleador;

    /**
     * Identificador del afiliado para poder obtener el estado del trabajador 
     */
    private Long idAfiliado;

	/**
	 * Método constructor
	 */
	public CarteraDependienteModeloDTO() {
	}



	private  CarteraDependienteModeloDTO (Long idCarteraDependiente,Long idCartera, BigDecimal deudaPresunta, BigDecimal deudaReal){
		this.idCarteraDependiente = idCarteraDependiente;
		this.idCartera = idCartera;
		this.deudaPresunta = deudaPresunta;
		this.deudaReal =deudaReal;

	}

	/**
	 * Método constructor con base en Entity
	 * */
	public CarteraDependienteModeloDTO(CarteraDependiente carteraDependiente){
	    super();
	    convertToDTO(carteraDependiente);
	}

	/**
	 * Método constructor
	 * 
	 * @param idCarteraDependiente
	 * @param deudaPresunta
	 * @param estadoOperacion
	 * @param idPersona
	 * @param idCartera
	 */
	public CarteraDependienteModeloDTO(CarteraDependiente carteraDependiente, Persona persona, RolAfiliado rolAfiliado) {
        this.idCarteraDependiente = carteraDependiente.getIdCarteraDependiente();
        this.deudaPresunta = carteraDependiente.getDeudaPresunta() != null ? carteraDependiente.getDeudaPresunta() : BigDecimal.ZERO;
        this.deudaReal = carteraDependiente.getDeudaReal() != null ? carteraDependiente.getDeudaReal() : BigDecimal.ZERO;
		this.idEmpleador = rolAfiliado.getEmpleador().getIdEmpleador();
		this.idAfiliado = rolAfiliado.getAfiliado().getIdAfiliado();
		this.estadoOperacion = carteraDependiente.getEstadoOperacion();
		this.idPersona = carteraDependiente.getIdPersona();
		this.idCartera = carteraDependiente.getIdCartera();
		this.tipoIdentificacion = persona.getTipoIdentificacion();
		this.numeroIdentificacion = persona.getNumeroIdentificacion();
		this.primerNombre = persona.getPrimerNombre();
		this.segundoNombre = persona.getSegundoNombre();
		this.primerApellido = persona.getPrimerApellido();
		this.segundoApellido = persona.getSegundoApellido();
		this.agregadoManualmente = carteraDependiente.getAgregadoManualmente();
	}

	/**
	 * Método que convierte el DTO a entidad
	 * 
	 * @return La entidad <code>CarteraDependiente</code> equivalente
	 */
	public CarteraDependiente convertToEntity() {
		CarteraDependiente carteraDependiente = new CarteraDependiente();
		carteraDependiente.setDeudaPresunta(this.getDeudaPresunta());
		carteraDependiente.setDeudaReal(this.getDeudaReal() !=null ? this.getDeudaReal() : BigDecimal.ZERO);
		carteraDependiente.setEstadoOperacion(this.getEstadoOperacion());
		carteraDependiente.setIdCartera(this.getIdCartera());
		carteraDependiente.setIdCarteraDependiente(this.getIdCarteraDependiente());
		carteraDependiente.setIdPersona(this.getIdPersona());
		carteraDependiente.setAgregadoManualmente(this.getAgregadoManualmente());
		return carteraDependiente;
	}
	
	/**
	 * Método que convierte de Entity a DTO (sólo CarteraDependiente)
	 * */
	private void convertToDTO (CarteraDependiente carteraDependiente){
	    this.idCarteraDependiente = carteraDependiente.getIdCarteraDependiente();
        this.deudaPresunta = carteraDependiente.getDeudaPresunta();
        this.deudaReal = carteraDependiente.getDeudaPresunta();
        this.estadoOperacion = carteraDependiente.getEstadoOperacion();
        this.idPersona = carteraDependiente.getIdPersona();
        this.idCartera = carteraDependiente.getIdCartera();
        this.agregadoManualmente = carteraDependiente.getAgregadoManualmente();
	}

	/**
	 * Obtiene el valor de idCarteraDependiente
	 * 
	 * @return El valor de idCarteraDependiente
	 */
	public Long getIdCarteraDependiente() {
		return idCarteraDependiente;
	}

	/**
	 * Establece el valor de idCarteraDependiente
	 * 
	 * @param idCarteraDependiente
	 *            El valor de idCarteraDependiente por asignar
	 */
	public void setIdCarteraDependiente(Long idCarteraDependiente) {
		this.idCarteraDependiente = idCarteraDependiente;
	}

	/**
	 * Obtiene el valor de deudaPresunta
	 * 
	 * @return El valor de deudaPresunta
	 */
	public BigDecimal getDeudaPresunta() {
		return deudaPresunta;
	}

	/**
	 * Establece el valor de deudaPresunta
	 * 
	 * @param deudaPresunta
	 *            El valor de deudaPresunta por asignar
	 */
	public void setDeudaPresunta(BigDecimal deudaPresunta) {
		this.deudaPresunta = deudaPresunta;
	}

	/**
	 * Obtiene el valor de deudaReal
	 * 
	 * @return El valor de deudaReal
	 */
	public BigDecimal getDeudaReal() {
		return deudaReal;
	}

	/**
	 * Establece el valor de deudaReal
	 * 
	 * @param deudaReal
	 *            El valor de deudaReal por asignar
	 */
	public void setDeudaReal(BigDecimal deudaReal) {
		this.deudaReal = deudaReal;
	}

	/**
	 * Obtiene el valor de estadoCotizante
	 * 
	 * @return El valor de estadoCotizante
	 */
	public EstadoAfiliadoEnum getEstadoCotizante() {
		return estadoCotizante;
	}

	/**
	 * Establece el valor de estadoCotizante
	 * 
	 * @param estadoCotizante
	 *            El valor de estadoCotizante por asignar
	 */
	public void setEstadoCotizante(EstadoAfiliadoEnum estadoCotizante) {
		this.estadoCotizante = estadoCotizante;
	}

	/**
	 * Obtiene el valor de estadoOperacion
	 * 
	 * @return El valor de estadoOperacion
	 */
	public EstadoOperacionCarteraEnum getEstadoOperacion() {
		return estadoOperacion;
	}

	/**
	 * Establece el valor de estadoOperacion
	 * 
	 * @param estadoOperacion
	 *            El valor de estadoOperacion por asignar
	 */
	public void setEstadoOperacion(EstadoOperacionCarteraEnum estadoOperacion) {
		this.estadoOperacion = estadoOperacion;
	}

	/**
	 * Obtiene el valor de idPersona
	 * 
	 * @return El valor de idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * Establece el valor de idPersona
	 * 
	 * @param idPersona
	 *            El valor de idPersona por asignar
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * Obtiene el valor de idCartera
	 * 
	 * @return El valor de idCartera
	 */
	public Long getIdCartera() {
		return idCartera;
	}

	/**
	 * Establece el valor de idCartera
	 * 
	 * @param idCartera
	 *            El valor de idCartera por asignar
	 */
	public void setIdCartera(Long idCartera) {
		this.idCartera = idCartera;
	}

	/**
	 * Obtiene el valor de tipoIdentificacion
	 * 
	 * @return El valor de tipoIdentificacion
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * Establece el valor de tipoIdentificacion
	 * 
	 * @param tipoIdentificacion
	 *            El valor de tipoIdentificacion por asignar
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * Obtiene el valor de numeroIdentificacion
	 * 
	 * @return El valor de numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * Establece el valor de numeroIdentificacion
	 * 
	 * @param numeroIdentificacion
	 *            El valor de numeroIdentificacion por asignar
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * Obtiene el valor de primerNombre
	 * 
	 * @return El valor de primerNombre
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}

	/**
	 * Establece el valor de primerNombre
	 * 
	 * @param primerNombre
	 *            El valor de primerNombre por asignar
	 */
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	/**
	 * Obtiene el valor de segundoNombre
	 * 
	 * @return El valor de segundoNombre
	 */
	public String getSegundoNombre() {
		return segundoNombre;
	}

	/**
	 * Establece el valor de segundoNombre
	 * 
	 * @param segundoNombre
	 *            El valor de segundoNombre por asignar
	 */
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	/**
	 * Obtiene el valor de primerApellido
	 * 
	 * @return El valor de primerApellido
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * Establece el valor de primerApellido
	 * 
	 * @param primerApellido
	 *            El valor de primerApellido por asignar
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	/**
	 * Obtiene el valor de segundoApellido
	 * 
	 * @return El valor de segundoApellido
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}

	/**
	 * Establece el valor de segundoApellido
	 * 
	 * @param segundoApellido
	 *            El valor de segundoApellido por asignar
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	/**
	 * Obtiene el valor de agregadoManualmente
	 * 
	 * @return El valor de agregadoManualmente
	 */
	public Boolean getAgregadoManualmente() {
		return agregadoManualmente;
	}

	/**
	 * Establece el valor de agregadoManualmente
	 * 
	 * @param agregadoManualmente
	 *            El valor de agregadoManualmente por asignar
	 */
	public void setAgregadoManualmente(Boolean agregadoManualmente) {
		this.agregadoManualmente = agregadoManualmente;
	}

    /**
     * @return the idEmpleador
     */
    public Long getIdEmpleador() {
        return idEmpleador;
    }

    /**
     * @param idEmpleador the idEmpleador to set
     */
    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
    }

    /**
     * @return the idAfiliado
     */
    public Long getIdAfiliado() {
        return idAfiliado;
    }

    /**
     * @param idAfiliado the idAfiliado to set
     */
    public void setIdAfiliado(Long idAfiliado) {
        this.idAfiliado = idAfiliado;
    }
}
