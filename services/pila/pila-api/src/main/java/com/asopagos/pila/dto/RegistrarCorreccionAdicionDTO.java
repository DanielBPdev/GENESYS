package com.asopagos.pila.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.dto.modelo.RegistroDetalladoNovedadModeloDTO;

/**
 * <b>Descripcion:</b> DTO que representa los parámetros recibidos para la operación de registrar o no registrar
 * una corrección o adición<br/>
 * <b>Módulo:</b> Asopagos - HU-211-410 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class RegistrarCorreccionAdicionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID del registro detallado original */
    private Long idRegistroDetalladoOriginal;

    /** ID del registro detallado de tipo A en corrección */
    private Long idRegistroDetalladoCorreccionA;

    /** ID del registro detallado a marcar */
    private Long idRegistroDetalladoCorreccionC;
    
    /** Listado de IDs de registros detallados de corrección */
    private List<Long> idsRegistrosCorreccion;
    
    /** Indicador para determinar sí se opera para registrar o no la corrección */
    private Boolean registrar;
    
    /** ID de índice planilla para la planilla Original */
    private Long idIndicePlanillaOriginal;
    
    /** ID de Registro General Original */
    private Long idRegistroGeneralOriginal;
    
    /** Número de la planilla planilla Original */
    private Long numeroPlanillaOriginal;
    
    /** ID de índice planilla para la planilla Corrección */
    private Long idIndicePlanillaCorreccion;
    
    /** ID de Registro General Corrección */
    private Long idRegistroGeneralCorreccion;
    
    /** Número de la planilla planilla Corrección */
    private Long numeroPlanillaCorreccion;
    
    /** Listado de marcación de novedades a aplicar */
    private List<RegistroDetalladoNovedadModeloDTO> registrosNovedades;

    public RegistrarCorreccionAdicionDTO() {
		super();
	}
    
    public RegistrarCorreccionAdicionDTO(Long idRegistroDetalladoCorreccionC) {
		super();
		this.idRegistroDetalladoCorreccionC = idRegistroDetalladoCorreccionC;
	}
    
    public RegistrarCorreccionAdicionDTO(Long idRegistroDetalladoOriginal, Long idRegistroDetalladoCorreccionA,
    		Long idRegistroDetalladoCorreccionC, Long idRegistroGeneralCorreccion, Long idRegistroGeneralOriginal, Boolean registrar) {
		super();
		this.idRegistroDetalladoOriginal = idRegistroDetalladoOriginal;
		this.idRegistroDetalladoCorreccionA = idRegistroDetalladoCorreccionA; 
		this.idRegistroDetalladoCorreccionC = idRegistroDetalladoCorreccionC; 
		this.idRegistroGeneralCorreccion = idRegistroGeneralCorreccion; 
		this.idRegistroGeneralOriginal = idRegistroGeneralOriginal;
		this.registrar = registrar;
	}
    
	/**
     * @return the registrar
     */
    public Boolean getRegistrar() {
        return registrar;
    }

    /**
     * @param registrar the registrar to set
     */
    public void setRegistrar(Boolean registrar) {
        this.registrar = registrar;
    }

    /**
     * @return the idRegistroDetalladoOriginal
     */
    public Long getIdRegistroDetalladoOriginal() {
        return idRegistroDetalladoOriginal;
    }

    /**
     * @param idRegistroDetalladoOriginal the idRegistroDetalladoOriginal to set
     */
    public void setIdRegistroDetalladoOriginal(Long idRegistroDetalladoOriginal) {
        this.idRegistroDetalladoOriginal = idRegistroDetalladoOriginal;
    }

    /**
     * @return the idRegistroDetalladoCorreccionA
     */
    public Long getIdRegistroDetalladoCorreccionA() {
        return idRegistroDetalladoCorreccionA;
    }

    /**
     * @param idRegistroDetalladoCorreccionA the idRegistroDetalladoCorreccionA to set
     */
    public void setIdRegistroDetalladoCorreccionA(Long idRegistroDetalladoCorreccionA) {
        this.idRegistroDetalladoCorreccionA = idRegistroDetalladoCorreccionA;
    }

    /**
     * @return the idRegistroDetalladoCorreccionC
     */
    public Long getIdRegistroDetalladoCorreccionC() {
        return idRegistroDetalladoCorreccionC;
    }

    /**
     * @param idRegistroDetalladoCorreccionC the idRegistroDetalladoCorreccionC to set
     */
    public void setIdRegistroDetalladoCorreccionC(Long idRegistroDetalladoCorreccionC) {
        this.idRegistroDetalladoCorreccionC = idRegistroDetalladoCorreccionC;
    }

    /**
     * @return the idIndicePlanillaOriginal
     */
    public Long getIdIndicePlanillaOriginal() {
        return idIndicePlanillaOriginal;
    }

    /**
     * @param idIndicePlanillaOriginal the idIndicePlanillaOriginal to set
     */
    public void setIdIndicePlanillaOriginal(Long idIndicePlanillaOriginal) {
        this.idIndicePlanillaOriginal = idIndicePlanillaOriginal;
    }

    /**
     * @return the numeroPlanillaOriginal
     */
    public Long getNumeroPlanillaOriginal() {
        return numeroPlanillaOriginal;
    }

    /**
     * @param numeroPlanillaOriginal the numeroPlanillaOriginal to set
     */
    public void setNumeroPlanillaOriginal(Long numeroPlanillaOriginal) {
        this.numeroPlanillaOriginal = numeroPlanillaOriginal;
    }

    /**
     * @return the idIndicePlanillaCorreccion
     */
    public Long getIdIndicePlanillaCorreccion() {
        return idIndicePlanillaCorreccion;
    }

    /**
     * @param idIndicePlanillaCorreccion the idIndicePlanillaCorreccion to set
     */
    public void setIdIndicePlanillaCorreccion(Long idIndicePlanillaCorreccion) {
        this.idIndicePlanillaCorreccion = idIndicePlanillaCorreccion;
    }

    /**
     * @return the numeroPlanillaCorreccion
     */
    public Long getNumeroPlanillaCorreccion() {
        return numeroPlanillaCorreccion;
    }

    /**
     * @param numeroPlanillaCorreccion the numeroPlanillaCorreccion to set
     */
    public void setNumeroPlanillaCorreccion(Long numeroPlanillaCorreccion) {
        this.numeroPlanillaCorreccion = numeroPlanillaCorreccion;
    }

    /**
     * @return the idRegistroGeneralOriginal
     */
    public Long getIdRegistroGeneralOriginal() {
        return idRegistroGeneralOriginal;
    }

    /**
     * @param idRegistroGeneralOriginal the idRegistroGeneralOriginal to set
     */
    public void setIdRegistroGeneralOriginal(Long idRegistroGeneralOriginal) {
        this.idRegistroGeneralOriginal = idRegistroGeneralOriginal;
    }

    /**
     * @return the idRegistroGeneralCorreccion
     */
    public Long getIdRegistroGeneralCorreccion() {
        return idRegistroGeneralCorreccion;
    }

    /**
     * @param idRegistroGeneralCorreccion the idRegistroGeneralCorreccion to set
     */
    public void setIdRegistroGeneralCorreccion(Long idRegistroGeneralCorreccion) {
        this.idRegistroGeneralCorreccion = idRegistroGeneralCorreccion;
    }

    /**
     * @return the idsRegistrosCorreccion
     */
    public List<Long> getIdsRegistrosCorreccion() {
        return idsRegistrosCorreccion;
    }

    /**
     * @param idsRegistrosCorreccion the idsRegistrosCorreccion to set
     */
    public void setIdsRegistrosCorreccion(List<Long> idsRegistrosCorreccion) {
        this.idsRegistrosCorreccion = idsRegistrosCorreccion;
    }

    /**
     * @return the registrosNovedades
     */
    public List<RegistroDetalladoNovedadModeloDTO> getRegistrosNovedades() {
        return registrosNovedades;
    }

    /**
     * @param registrosNovedades the registrosNovedades to set
     */
    public void setRegistrosNovedades(List<RegistroDetalladoNovedadModeloDTO> registrosNovedades) {
        this.registrosNovedades = registrosNovedades;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegistrarCorreccionAdicionDTO [");
		if (idRegistroDetalladoOriginal != null) {
			builder.append("idRegistroDetalladoOriginal=");
			builder.append(idRegistroDetalladoOriginal);
			builder.append(", ");
		}
		if (idRegistroDetalladoCorreccionA != null) {
			builder.append("idRegistroDetalladoCorreccionA=");
			builder.append(idRegistroDetalladoCorreccionA);
			builder.append(", ");
		}
		if (idRegistroDetalladoCorreccionC != null) {
			builder.append("idRegistroDetalladoCorreccionC=");
			builder.append(idRegistroDetalladoCorreccionC);
			builder.append(", ");
		}
		if (idsRegistrosCorreccion != null) {
			builder.append("idsRegistrosCorreccion=");
			builder.append(idsRegistrosCorreccion);
			builder.append(", ");
		}
		if (registrar != null) {
			builder.append("registrar=");
			builder.append(registrar);
			builder.append(", ");
		}
		if (idIndicePlanillaOriginal != null) {
			builder.append("idIndicePlanillaOriginal=");
			builder.append(idIndicePlanillaOriginal);
			builder.append(", ");
		}
		if (idRegistroGeneralOriginal != null) {
			builder.append("idRegistroGeneralOriginal=");
			builder.append(idRegistroGeneralOriginal);
			builder.append(", ");
		}
		if (numeroPlanillaOriginal != null) {
			builder.append("numeroPlanillaOriginal=");
			builder.append(numeroPlanillaOriginal);
			builder.append(", ");
		}
		if (idIndicePlanillaCorreccion != null) {
			builder.append("idIndicePlanillaCorreccion=");
			builder.append(idIndicePlanillaCorreccion);
			builder.append(", ");
		}
		if (idRegistroGeneralCorreccion != null) {
			builder.append("idRegistroGeneralCorreccion=");
			builder.append(idRegistroGeneralCorreccion);
			builder.append(", ");
		}
		if (numeroPlanillaCorreccion != null) {
			builder.append("numeroPlanillaCorreccion=");
			builder.append(numeroPlanillaCorreccion);
			builder.append(", ");
		}
		if (registrosNovedades != null) {
			builder.append("registrosNovedades=");
			builder.append(registrosNovedades);
		}
		builder.append("]");
		return builder.toString();
	}


	
	
}
