package com.asopagos.aportes.dto;

import com.asopagos.entidades.ccf.aportes.AporteDetallado;
import com.asopagos.entidades.ccf.aportes.AporteGeneral;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.PersonasUtils;

/**
 * Clase DTO con los datos de un solicitante.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 * @author <a href="mailto:criparra@heinsohn.com.co"> Cristian David Parra Zuluaga </a>
 */
public class SolicitanteDTO {
    
    /**
	 * Código identificador de la persona. 
	 */
	private Long idPersona;
	
	/**
	 * Código identificador del empleador. 
	 */
	private Long idEmpresa;
	
	/**
	 * Descripción del tipo de identificación de la persona
	 */
	private TipoIdentificacionEnum tipoIdentificacion;
	
	/**
	 * Número de identificación de la persona
	 */
	private String numeroIdentificacion;
	
    /**
	 * Descripción del primer nombre
	 */
	private String nombreSolicitante;

    /**
     * Estado del solicitante cuando es independiente o pensionado.
     */
    private EstadoAfiliadoEnum estado;
    
    /**
     * Descripción del nombre completo o razon social de la persona
     */
    private String nombreCompletoRazonSocial;
    
    /**
     * Tipo de soliictante asociado a una persona
     */
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
    
    /**
     * Constructor de la clase.
     */
    public SolicitanteDTO() {
		super();
	}
    
    /**
     * Constructor que asigna los datos al DTO de Persona y AporteDetallado cuando el solicitante es independiente o pensionado.
     * @param persona
     * @param aporteDetallado
     */
    public SolicitanteDTO(Persona persona, AporteDetallado aporteDetallado) {
    	if(persona.getIdPersona() != null)
    	{
    		this.setIdPersona(persona.getIdPersona());
    		this.setTipoIdentificacion(persona.getTipoIdentificacion());
	        this.setNumeroIdentificacion(persona.getNumeroIdentificacion());
	        if(persona.getRazonSocial()!= null){
	            this.setNombreCompletoRazonSocial(persona.getRazonSocial());
	        }else{
	            this.setNombreCompletoRazonSocial(PersonasUtils.obtenerNombrePersona(persona));
	        }
	        this.setEstado(aporteDetallado.getEstadoCotizante());
    	}
	}

    /**
     * Constructor que asigna los datos al DTO de Persona y aporte general cuando el solicitante es empleador.
     * @param persona persona que representa el empleador.
     * @param aporteGeneral aporte general.
     */
    public SolicitanteDTO(Persona persona, AporteGeneral aporteGeneral)
    {
    		this.setIdEmpresa(aporteGeneral.getIdEmpresa());
    		this.setTipoIdentificacion(persona.getTipoIdentificacion());
            this.setNumeroIdentificacion(persona.getNumeroIdentificacion());
            if(persona.getRazonSocial()!= null){
                this.setNombreCompletoRazonSocial(persona.getRazonSocial());
            }else{
                this.setNombreCompletoRazonSocial(PersonasUtils.obtenerNombrePersona(persona));
            }
            this.setEstado(null);
    }

    /**
     * Método constructor
     * @param idPersona
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param tipoSolicitante
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param nombreCompletoRazonSocial
     */
    public SolicitanteDTO(Long idPersona,TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            TipoSolicitanteMovimientoAporteEnum tipoSolicitante, String primerNombre, String segundoNombre, String primerApellido,
            String segundoApellido, String nombreCompletoRazonSocial) {
        this.idPersona = idPersona;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoSolicitante = tipoSolicitante;
        if(nombreCompletoRazonSocial!=null){
            this.nombreCompletoRazonSocial = nombreCompletoRazonSocial;
        } else {
            this.nombreCompletoRazonSocial= primerNombre+" "+segundoNombre+" "+primerApellido+" "+segundoApellido;
        }
    }
    
    /**
     * Método constructor
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param tipoSolicitante
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param nombreCompletoRazonSocial
     * @param idEmpresa
     * @param idPersona
     */
    public SolicitanteDTO(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            TipoSolicitanteMovimientoAporteEnum tipoSolicitante, String primerNombre, String segundoNombre, String primerApellido,
            String segundoApellido, String nombreCompletoRazonSocial, Long idEmpresa, Long idPersona) {
        this.idEmpresa = idEmpresa;
        this.idPersona = idPersona;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoSolicitante = tipoSolicitante;
        if(nombreCompletoRazonSocial!=null){
            this.nombreCompletoRazonSocial = nombreCompletoRazonSocial;
        } else {
            this.nombreCompletoRazonSocial= primerNombre+" "+segundoNombre+" "+primerApellido+" "+segundoApellido;
        }
    }
    
    /**
     * Método constructor
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param tipoSolicitante
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param nombreCompletoRazonSocial
     */
    public SolicitanteDTO(Long idPersona,Long idEmpresa, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            TipoSolicitanteMovimientoAporteEnum tipoSolicitante, String primerNombre, String segundoNombre, String primerApellido,
            String segundoApellido, String nombreCompletoRazonSocial) {
        this.idEmpresa = idEmpresa;
        this.idPersona = idPersona;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoSolicitante = tipoSolicitante;
        if(nombreCompletoRazonSocial!=null){
            this.nombreCompletoRazonSocial = nombreCompletoRazonSocial;
        } else {
            this.nombreCompletoRazonSocial= primerNombre+" "+segundoNombre+" "+primerApellido+" "+segundoApellido;
        }
    }
    
	/**
	 * Método que retorna el valor de idPersona.
	 * @return valor de idPersona.
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * Método encargado de modificar el valor de idPersona.
	 * @param valor para modificar idPersona.
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * Método que retorna el valor de idEmpresa.
	 * @return valor de idEmpresa.
	 */
	public Long getIdEmpresa() {
		return idEmpresa;
	}

	/**
	 * Método encargado de modificar el valor de idEmpresa.
	 * @param valor para modificar idEmpresa.
	 */
	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
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
	 * Método que retorna el valor de nombreSolicitante.
	 * @return valor de nombreSolicitante.
	 */
	public String getNombreSolicitante() {
		return nombreSolicitante;
	}

	/**
	 * Método encargado de modificar el valor de nombreSolicitante.
	 * @param valor para modificar nombreSolicitante.
	 */
	public void setNombreSolicitante(String nombreSolicitante) {
		this.nombreSolicitante = nombreSolicitante;
	}

	/**
	 * Método que retorna el valor de estado.
	 * @return valor de estado.
	 */
	public EstadoAfiliadoEnum getEstado() {
		return estado;
	}

	/**
	 * Método encargado de modificar el valor de estado.
	 * @param valor para modificar estado.
	 */
	public void setEstado(EstadoAfiliadoEnum estado) {
		this.estado = estado;
	}

    /**
     * @return the nombreCompletoRazonSocial
     */
    public String getNombreCompletoRazonSocial() {
        return nombreCompletoRazonSocial;
    }

    /**
     * @param nombreCompletoRazonSocial the nombreCompletoRazonSocial to set
     */
    public void setNombreCompletoRazonSocial(String nombreCompletoRazonSocial) {
        this.nombreCompletoRazonSocial = nombreCompletoRazonSocial;
    }

    /**
     * @return the tipoSolicitante
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * @param tipoSolicitante the tipoSolicitante to set
     */
    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }
	
}
