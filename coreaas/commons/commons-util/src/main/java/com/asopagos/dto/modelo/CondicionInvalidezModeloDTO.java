package com.asopagos.dto.modelo;	
import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.ccf.personas.CondicionInvalidez;

/**
 * DTO con los datos del Modelo de CondicionInvalidez.
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class CondicionInvalidezModeloDTO implements Serializable{
	
	/**
     * Código identificador de llave primaria de la condición de invalidez
     */
    private Long idCondicionInvalidez;
    
    /**
     * Identificador de la persona con condición de invalidez
     */
    private Long idPersona;
        
    /**
     * Atributo que indica si la condición de invalidez esta activa o no.
     */
    private Boolean condicionInvalidez;
    
    /**
     * Atributo que indica la fecha en al que se reporta la condición de invalidez.
     */
    private Long fechaReporteInvalidez;
    
    /**
     * Atributo que indica los comentarios de la condición de invalidez.
     */
    private String comentarioInvalidez;
    
    /**
     * Atributo que indica la fecha de inicio de la condición de invalidez.
     */
    private Long fechaInicioInvalidez;

	private Boolean conyugeCuidador;

	private Long fechaInicioConyugeCuidador;

    private Long fechaFinConyugeCuidador;

	private Long idConyugeCuidador;
	
    /**
     * Asocia los datos del DTO a la Entidad
     * @return CondicionInvalidez
     */
    public CondicionInvalidez convertToEntity() {
    	CondicionInvalidez condicionInvalidez = new CondicionInvalidez();
    	condicionInvalidez.setIdCondicionInvalidez(this.getIdCondicionInvalidez());
    	condicionInvalidez.setComentarioInvalidez(this.getComentarioInvalidez());
    	condicionInvalidez.setCondicionInvalidez(this.getCondicionInvalidez());
    	condicionInvalidez.setIdPersona(this.getIdPersona());
    	if (this.getFechaReporteInvalidez() != null) {
    		condicionInvalidez.setFechaReporteInvalidez(new Date(this.getFechaReporteInvalidez()));
    	}
    	if (this.getFechaInicioInvalidez() != null) {
            condicionInvalidez.setFechaInicioInvalidez(new Date(this.getFechaInicioInvalidez()));
        }
		if (this.getConyugeCuidador() != null) {
            condicionInvalidez.setConyugeCuidador(this.getConyugeCuidador());
        }
		if(this.getFechaInicioConyugeCuidador() != null) {
			condicionInvalidez.setFechaInicioConyugeCuidador(new Date(this.getFechaInicioConyugeCuidador()));
		}
		if(this.getFechaFinConyugeCuidador() != null) {
			condicionInvalidez.setFechaFinConyugeCuidador(new Date(this.getFechaFinConyugeCuidador()));
		}
		if(this.getIdConyugeCuidador() != null) {
			condicionInvalidez.setIdConyugeCuidador(this.getIdConyugeCuidador());
		}
    	return condicionInvalidez;
    }
    
    /**
     * @param Asocia los datos de la Entidad al DTO
     * @return CondicionInvalidezModeloDTO
     */
    public void convertToDTO (CondicionInvalidez condicionInvalidez) {
    	this.setIdCondicionInvalidez(condicionInvalidez.getIdCondicionInvalidez());
    	this.setComentarioInvalidez(condicionInvalidez.getComentarioInvalidez());
    	this.setCondicionInvalidez(condicionInvalidez.getCondicionInvalidez());
    	if (condicionInvalidez.getFechaReporteInvalidez() != null) {
    		this.setFechaReporteInvalidez(condicionInvalidez.getFechaReporteInvalidez().getTime());
    	}
    	if (condicionInvalidez.getFechaInicioInvalidez() != null) {
            this.setFechaInicioInvalidez(condicionInvalidez.getFechaInicioInvalidez().getTime());
        }
		if (condicionInvalidez.getConyugeCuidador() != null) {
            this.setConyugeCuidador(condicionInvalidez.getConyugeCuidador());
        }
		if(condicionInvalidez.getFechaInicioConyugeCuidador() != null) {
			this.setFechaInicioConyugeCuidador(condicionInvalidez.getFechaInicioConyugeCuidador().getTime());
		}
		if(condicionInvalidez.getFechaFinConyugeCuidador() != null) {
			this.setFechaFinConyugeCuidador(condicionInvalidez.getFechaFinConyugeCuidador().getTime());
		}
		if(condicionInvalidez.getIdConyugeCuidador() != null) {
			this.setIdConyugeCuidador(condicionInvalidez.getIdConyugeCuidador());
		}
    	this.setIdPersona(condicionInvalidez.getIdPersona());
    
    }
    
    /**
     * Copia los datos del DTO a la Entidad.
     * @param condicionInvalidez previamente consultada.
     * @param condicionInvalidezModeloDTO DTO a copiar.
     */
    public static void copyDTOToEntity (CondicionInvalidez condicionInvalidez, CondicionInvalidezModeloDTO condicionInvalidezModeloDTO) {
		if (condicionInvalidezModeloDTO.getIdCondicionInvalidez() != null) {
			condicionInvalidez.setIdCondicionInvalidez(condicionInvalidezModeloDTO.getIdCondicionInvalidez());
		}
		if (condicionInvalidezModeloDTO.getCondicionInvalidez() != null) {
			condicionInvalidez.setCondicionInvalidez(condicionInvalidezModeloDTO.getCondicionInvalidez());
		}
		if (condicionInvalidezModeloDTO.getComentarioInvalidez() != null) {
			condicionInvalidez.setComentarioInvalidez(condicionInvalidezModeloDTO.getComentarioInvalidez());
		}
		if (condicionInvalidezModeloDTO.getFechaInicioInvalidez() != null) {
            condicionInvalidez.setFechaInicioInvalidez(new Date(condicionInvalidezModeloDTO.getFechaInicioInvalidez()));
        }
		if (condicionInvalidezModeloDTO.getFechaReporteInvalidez() != null) {
			condicionInvalidez.setFechaReporteInvalidez(new Date(condicionInvalidezModeloDTO.getFechaReporteInvalidez()));
		}
		if (condicionInvalidezModeloDTO.getIdPersona() != null) {
			condicionInvalidez.setIdPersona(condicionInvalidezModeloDTO.getIdPersona());
		}
		if (condicionInvalidezModeloDTO.getConyugeCuidador() != null) {
			condicionInvalidez.setConyugeCuidador(condicionInvalidezModeloDTO.getConyugeCuidador());
		}
		if (condicionInvalidezModeloDTO.getFechaInicioConyugeCuidador() != null) {
			condicionInvalidez.setFechaInicioConyugeCuidador(new Date(condicionInvalidezModeloDTO.getFechaInicioConyugeCuidador()));
		}
		if (condicionInvalidezModeloDTO.getFechaFinConyugeCuidador() != null) {
			condicionInvalidez.setFechaFinConyugeCuidador(new Date(condicionInvalidezModeloDTO.getFechaFinConyugeCuidador()));
		}
		if (condicionInvalidezModeloDTO.getIdConyugeCuidador() != null) {
			condicionInvalidez.setIdConyugeCuidador(condicionInvalidezModeloDTO.getIdConyugeCuidador());
		}
    }

	/**
	 * @return the idCondicionInvalidez
	 */
	public Long getIdCondicionInvalidez() {
		return idCondicionInvalidez;
	}

	/**
	 * @param idCondicionInvalidez the idCondicionInvalidez to set
	 */
	public void setIdCondicionInvalidez(Long idCondicionInvalidez) {
		this.idCondicionInvalidez = idCondicionInvalidez;
	}

	/**
	 * @return the idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * @return the condicionInvalidez
	 */
	public Boolean getCondicionInvalidez() {
		return condicionInvalidez;
	}

	/**
	 * @param condicionInvalidez the condicionInvalidez to set
	 */
	public void setCondicionInvalidez(Boolean condicionInvalidez) {
		this.condicionInvalidez = condicionInvalidez;
	}

	/**
	 * @return the fechaReporteInvalidez
	 */
	public Long getFechaReporteInvalidez() {
		return fechaReporteInvalidez;
	}

	/**
	 * @param fechaReporteInvalidez the fechaReporteInvalidez to set
	 */
	public void setFechaReporteInvalidez(Long fechaReporteInvalidez) {
		this.fechaReporteInvalidez = fechaReporteInvalidez;
	}

	/**
	 * @return the comentarioInvalidez
	 */
	public String getComentarioInvalidez() {
		return comentarioInvalidez;
	}

	/**
	 * @param comentarioInvalidez the comentarioInvalidez to set
	 */
	public void setComentarioInvalidez(String comentarioInvalidez) {
		this.comentarioInvalidez = comentarioInvalidez;
	}

    /**
     * @return the fechaInicioInvalidez
     */
    public Long getFechaInicioInvalidez() {
        return fechaInicioInvalidez;
    }

    /**
     * @param fechaInicioInvalidez the fechaInicioInvalidez to set
     */
    public void setFechaInicioInvalidez(Long fechaInicioInvalidez) {
        this.fechaInicioInvalidez = fechaInicioInvalidez;
    }
	public Boolean getConyugeCuidador() {
		return this.conyugeCuidador;
	}

	public void setConyugeCuidador(Boolean conyugeCuidador) {
		this.conyugeCuidador = conyugeCuidador;
	}

	public Long getFechaInicioConyugeCuidador() {
		return this.fechaInicioConyugeCuidador;
	}

	public void setFechaInicioConyugeCuidador(Long fechaInicioConyugeCuidador) {
		this.fechaInicioConyugeCuidador = fechaInicioConyugeCuidador;
	}

	public Long getFechaFinConyugeCuidador() {
		return this.fechaFinConyugeCuidador;
	}

	public void setFechaFinConyugeCuidador(Long fechaFinConyugeCuidador) {
		this.fechaFinConyugeCuidador = fechaFinConyugeCuidador;
	}

	public Long getIdConyugeCuidador() {
		return this.idConyugeCuidador;
	}

	public void setIdConyugeCuidador(Long idConyugeCuidador) {
		this.idConyugeCuidador = idConyugeCuidador;
	}
	
	
}
