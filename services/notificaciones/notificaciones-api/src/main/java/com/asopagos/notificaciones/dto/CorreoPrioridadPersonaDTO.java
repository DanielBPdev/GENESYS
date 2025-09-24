package com.asopagos.notificaciones.dto;

public class CorreoPrioridadPersonaDTO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Direccion email de la entidad objeto del comunicado
     */
	private String email;
	
	/**
	 * Identificador de la persona cuando esta realcionado al comunicado
	 */
	private Long idPersona;
	
	/**
	 * Identificador del empleador cuando esta realcionado al comunicado
	 */
	private Long idEmpleador;
	
	/**
	 * Identificador del empleador cuando esta realcionado al comunicado
	 */
	private Long idEmpresa;
	
	/**
	 * Indica si la entidad objeto del comunicado autoriza el envio de comunicados
	 */
	private Boolean autorizaEnvio;
	
	/**
	 * Indica si la persona tiene un estado de no formalizado sin afiliación con aportes
	 */
	private Boolean noFormalizado;
	
    /**
     * Método constructor
     */
    public CorreoPrioridadPersonaDTO() {
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @param idPersona
	 * @param noFormalizado
	 */
	public CorreoPrioridadPersonaDTO(Long idPersona, Boolean noFormalizado) {
		super();
		this.idPersona = idPersona;
		this.noFormalizado = noFormalizado;
	}
	
	/**
	 * @param idPersona
	 * @param idEmpresa
	 * @param noFormalizado
	 */
	public CorreoPrioridadPersonaDTO(Long idPersona, Long idEmpresa, Boolean noFormalizado) {
		super();
		this.idPersona = idPersona;
		this.idEmpresa = idEmpresa;
		this.noFormalizado = noFormalizado;
	}

	public CorreoPrioridadPersonaDTO(String email, Long idPersona) {
		super();
		this.email = email;
		this.idPersona = idPersona;
	}
	
	public CorreoPrioridadPersonaDTO(String email, Long idPersona, Long idEmpleador){
		//super();
		this.email = email;
		this.idPersona = idPersona;
		this.idEmpleador = idEmpleador;
	}
	
	public CorreoPrioridadPersonaDTO(String email, Long idPersona, Long idEmpleador, Long idEmpresa){
		//super();
		this.email = email;
		this.idPersona = idPersona;
		this.idEmpresa = idEmpresa;
		
	}

	/**
     * @param email
     * @param idPersona
     * @param idEmpleador
     * @param idEmpresa
     * @param autorizaEnvio
     */
    public CorreoPrioridadPersonaDTO(String email, Long idPersona, Long idEmpleador, Long idEmpresa, Boolean autorizaEnvio) {
        super();
        this.email = email;
        this.idPersona = idPersona;
        this.idEmpleador = idEmpleador;
        this.idEmpresa = idEmpresa;
        this.autorizaEnvio = autorizaEnvio;
    }
    
    /**
     * @param email
     * @param idPersona
     * @param autorizaEnvio
     */
    public CorreoPrioridadPersonaDTO(String email, Long idPersona, Boolean autorizaEnvio) {
        super();
        this.email = email;
        this.idPersona = idPersona;
        this.autorizaEnvio = autorizaEnvio;
    }

    public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getIdPersona() {
		return idPersona;
	}
	public void setPersona(Long idPersona) {
		this.idPersona = idPersona;
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
	 * @return the idEmpresa
	 */
	public Long getIdEmpresa() {
		return idEmpresa;
	}

	/**
	 * @param idEmpresa the idEmpresa to set
	 */
	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

    /**
     * @return the autorizaEnvio
     */
    public Boolean getAutorizaEnvio() {
        return autorizaEnvio;
    }

    /**
     * @param autorizaEnvio the autorizaEnvio to set
     */
    public void setAutorizaEnvio(Boolean autorizaEnvio) {
        this.autorizaEnvio = autorizaEnvio;
    }

	/**
	 * @return the noFormalizado
	 */
	public Boolean getNoFormalizado() {
		return noFormalizado;
	}

	/**
	 * @param noFormalizado the noFormalizado to set
	 */
	public void setNoFormalizado(Boolean noFormalizado) {
		this.noFormalizado = noFormalizado;
	}
	
    
}
