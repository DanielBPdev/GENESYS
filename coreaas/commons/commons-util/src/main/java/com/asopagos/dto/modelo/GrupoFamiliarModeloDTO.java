package com.asopagos.dto.modelo;	
import java.io.Serializable;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.GrupoFamiliar;

/**
 * DTO con los datos del Modelo de Grupo Familiar.
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class GrupoFamiliarModeloDTO implements Serializable{
	
	/**
	 * Código identificador de llave primaria del grupo familiar 
	 */
    private Long idGrupoFamiliar;
    
    /**
     * Código identificador de llave primaria del afiliado 
     */
    private Long idAfiliado;    
	
    /**
	 * 
	 */
    private Byte numero;
    
    /**
	 * Id que identifica la ubicación asociada al grupo familiar
	 */
    private UbicacionModeloDTO ubicacionGrupoFamiliar;
    
    /**
     * Id que identifica el administrador subsidio asociado al grupo familiar
     */
    private PersonaModeloDTO administradorSubsidio;

    /**
     * Descripción de las observaciones
     */
    private String observaciones;
    
    /**
     * Id que identifica al medio de pago parametrizado para el grupo familiar
     */
    private MedioDePagoModeloDTO medioDePagoModeloDTO;
    
    /**
     * Indica S/N el grupo familiar tiene la condición de inembargable[S=Si N=No]
     */
    private Boolean inembargable;
   
    /**
     * Asocia los datos del DTO a la Entidad
     * @return GrupoFamiliar
     */
    public GrupoFamiliar convertToGrupoFamiliarEntity() {
    	GrupoFamiliar grupoFamiliar = new GrupoFamiliar();
    	if (this.getIdAfiliado() != null) {
    		Afiliado afiliado = new Afiliado();
    		afiliado.setIdAfiliado(this.getIdAfiliado());
    		grupoFamiliar.setAfiliado(afiliado);
    	}
    	grupoFamiliar.setIdGrupoFamiliar(this.getIdGrupoFamiliar());
    	grupoFamiliar.setNumero(this.getNumero());
    	grupoFamiliar.setObservaciones(this.getObservaciones());
    	grupoFamiliar.setInembargable(this.getInembargable());
    	
    	if (this.getUbicacionGrupoFamiliar() != null) {
    		Ubicacion ubicacion = new Ubicacion();
    		ubicacion.setIdUbicacion(this.getUbicacionGrupoFamiliar().getIdUbicacion());
    		grupoFamiliar.setUbicacion(ubicacion);
    	}
    	grupoFamiliar.setInembargable(this.getInembargable());
    	return grupoFamiliar;
    }
    
    /**
     * @param Asocia los datos de la Entidad al DTO
     * @return GrupoFamiliarModeloDTO
     */
    public void convertToDTO (GrupoFamiliar grupoFamiliar) {
    	if (grupoFamiliar.getAfiliado() != null && grupoFamiliar.getAfiliado().getIdAfiliado()!=null) {
    		this.setIdAfiliado(grupoFamiliar.getAfiliado().getIdAfiliado());
    	}
    	this.setIdGrupoFamiliar(grupoFamiliar.getIdGrupoFamiliar());
		this.setNumero(grupoFamiliar.getNumero());
		this.setObservaciones(grupoFamiliar.getObservaciones());
    	if (grupoFamiliar.getUbicacion() != null) {
    		UbicacionModeloDTO ubicacionModeloDTO = new UbicacionModeloDTO();
    		ubicacionModeloDTO.convertToDTO(grupoFamiliar.getUbicacion());
    		this.setUbicacionGrupoFamiliar(ubicacionModeloDTO);
    	}
    	this.setInembargable(grupoFamiliar.getInembargable());
    }
    
    /**
     * Copia los datos del DTO a la Entidad.
     * @param grupoFamiliar previamente consultada.
     * @param grupoFamiliarModeloDTO DTO a copiar.
     */
    public static void copyDTOToEntity (GrupoFamiliar grupoFamiliar, GrupoFamiliarModeloDTO grupoFamiliarModeloDTO) {
		if (grupoFamiliarModeloDTO.getIdAfiliado() != null) {
			Afiliado afiliado = new Afiliado();
			afiliado.setIdAfiliado(grupoFamiliarModeloDTO.getIdAfiliado());
			grupoFamiliar.setAfiliado(afiliado);
		}
		if (grupoFamiliarModeloDTO.getNumero() != null) {
			grupoFamiliar.setNumero(grupoFamiliarModeloDTO.getNumero());
		}
		if (grupoFamiliarModeloDTO.getObservaciones() != null) {
			grupoFamiliar.setObservaciones(grupoFamiliarModeloDTO.getObservaciones());
		}
		if (grupoFamiliarModeloDTO.getUbicacionGrupoFamiliar()!= null) {
			Ubicacion ubicacion = new Ubicacion();
			ubicacion.setIdUbicacion(grupoFamiliarModeloDTO.getUbicacionGrupoFamiliar().getIdUbicacion());
			grupoFamiliar.setUbicacion(ubicacion);
		}
		if (grupoFamiliarModeloDTO.getInembargable() != null) {
			grupoFamiliar.setInembargable(grupoFamiliarModeloDTO.getInembargable());
		}
		
    }

	/**
	 * @return the idGrupoFamiliar
	 */
	public Long getIdGrupoFamiliar() {
		return idGrupoFamiliar;
	}

	/**
	 * @param idGrupoFamiliar the idGrupoFamiliar to set
	 */
	public void setIdGrupoFamiliar(Long idGrupoFamiliar) {
		this.idGrupoFamiliar = idGrupoFamiliar;
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

    /**
	 * @return the numero
	 */
	public Byte getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(Byte numero) {
		this.numero = numero;
	}

	/**
	 * @return the ubicacionGrupoFamiliar
	 */
	public UbicacionModeloDTO getUbicacionGrupoFamiliar() {
		return ubicacionGrupoFamiliar;
	}

	/**
	 * @param ubicacionGrupoFamiliar the ubicacionGrupoFamiliar to set
	 */
	public void setUbicacionGrupoFamiliar(UbicacionModeloDTO ubicacionGrupoFamiliar) {
		this.ubicacionGrupoFamiliar = ubicacionGrupoFamiliar;
	}

	/**
	 * @return the administradorSubsidio
	 */
	public PersonaModeloDTO getAdministradorSubsidio() {
		return administradorSubsidio;
	}

	/**
	 * @param administradorSubsidio the administradorSubsidio to set
	 */
	public void setAdministradorSubsidio(PersonaModeloDTO administradorSubsidio) {
		this.administradorSubsidio = administradorSubsidio;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return the medioDePagoModeloDTO
	 */
	public MedioDePagoModeloDTO getMedioDePagoModeloDTO() {
		return medioDePagoModeloDTO;
	}

	/**
	 * @param medioDePagoModeloDTO the medioDePagoModeloDTO to set
	 */
	public void setMedioDePagoModeloDTO(MedioDePagoModeloDTO medioDePagoModeloDTO) {
		this.medioDePagoModeloDTO = medioDePagoModeloDTO;
	}

	/**
	 * Método que retorna el valor de inembargable.
	 * @return valor de inembargable.
	 */
	public Boolean getInembargable() {
		return inembargable;
	}

	/**
	 * Método encargado de modificar el valor de inembargable.
	 * @param valor para modificar inembargable.
	 */
	public void setInembargable(Boolean inembargable) {
		this.inembargable = inembargable;
	}
}
