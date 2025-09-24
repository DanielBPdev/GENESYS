package com.asopagos.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.transversal.core.CodigoCIIU;

@XmlRootElement
public class CodigoCIIUDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
     * Código identificador de llave primaria del Codigo CIIU 
     */
	private Short idCodigoCIIU;
	
	/**
     * Código único de CIIU
     */
	private String codigo;

	/**
     * Descripción del código CIUU
     */
	private String descripcion;
	
	/**
     * Código unico de sección
     */
	private String codigoSeccion;

    /**
     * Descripcion del código de sección
     */
    private String descripcionSeccion;
    
    /**
     * Código unico de división
     */
    private String codigoDivision;
    
    /**
     * Descripcion del código de división
     */
    private String descripcionDivision;
    
    /**
     * Código unico de grupo
     */
    private String codigoGrupo;

    /**
     * Descripcion del código de grupo
     */
    private String descripcionGrupo;

	/**
	 * @return the idCodigoCIIU
	 */
	public Short getIdCodigoCIIU() {
		return idCodigoCIIU;
	}

	/**
	 * @param idCodigoCIIU
	 *            the idCodigoCIIU to set
	 */
	public void setIdCodigoCIIU(Short idCodigoCIIU) {
		this.idCodigoCIIU = idCodigoCIIU;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
    /**
     * @return the codigoSeccion
     */
    public String getCodigoSeccion() {
        return codigoSeccion;
    }

    /**
     * @param codigoSeccion the codigoSeccion to set
     */
    public void setCodigoSeccion(String codigoSeccion) {
        this.codigoSeccion = codigoSeccion;
    }

    /**
     * @return the descripcionSeccion
     */
    public String getDescripcionSeccion() {
        return descripcionSeccion;
    }

    /**
     * @param descripcionSeccion the descripcionSeccion to set
     */
    public void setDescripcionSeccion(String descripcionSeccion) {
        this.descripcionSeccion = descripcionSeccion;
    }

    /**
     * @return the codigoDivision
     */
    public String getCodigoDivision() {
        return codigoDivision;
    }

    /**
     * @param codigoDivision the codigoDivision to set
     */
    public void setCodigoDivision(String codigoDivision) {
        this.codigoDivision = codigoDivision;
    }

    /**
     * @return the descripcionDivision
     */
    public String getDescripcionDivision() {
        return descripcionDivision;
    }

    /**
     * @param descripcionDivision the descripcionDivision to set
     */
    public void setDescripcionDivision(String descripcionDivision) {
        this.descripcionDivision = descripcionDivision;
    }

    /**
     * @return the codigoGrupo
     */
    public String getCodigoGrupo() {
        return codigoGrupo;
    }

    /**
     * @param codigoGrupo the codigoGrupo to set
     */
    public void setCodigoGrupo(String codigoGrupo) {
        this.codigoGrupo = codigoGrupo;
    }

    /**
     * @return the descripcionGrupo
     */
    public String getDescripcionGrupo() {
        return descripcionGrupo;
    }

    /**
     * @param descripcionGrupo the descripcionGrupo to set
     */
    public void setDescripcionGrupo(String descripcionGrupo) {
        this.descripcionGrupo = descripcionGrupo;
    }

    /**
     * @param codigoCIIU
     * @return
     */
    public static CodigoCIIUDTO convertCodigoCIIUToDto(CodigoCIIU codigoCIIU) {
        CodigoCIIUDTO codigoCIIUDTO = new CodigoCIIUDTO();
        codigoCIIUDTO.setCodigo(codigoCIIU.getCodigo());
        codigoCIIUDTO.setDescripcion(codigoCIIU.getDescripcion());
        codigoCIIUDTO.setCodigoSeccion(codigoCIIU.getCodigoSeccion());
        codigoCIIUDTO.setDescripcionSeccion(codigoCIIU.getDescripcionSeccion());
        codigoCIIUDTO.setCodigoDivision(codigoCIIU.getCodigoDivision());
        codigoCIIUDTO.setDescripcionDivision(codigoCIIU.getDescripcionDivision());
        codigoCIIUDTO.setCodigoGrupo(codigoCIIU.getCodigoGrupo());
        codigoCIIUDTO.setDescripcionGrupo(codigoCIIU.getDescripcionGrupo());
        return codigoCIIUDTO;
    }

}
