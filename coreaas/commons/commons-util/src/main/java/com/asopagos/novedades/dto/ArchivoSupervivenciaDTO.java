/**
 * 
 */
package com.asopagos.novedades.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.enumeraciones.novedades.EstadoCargueSupervivenciaEnum;

/**
 * DTO que contendra los archivos de supervivencia cargados den la HU-498
 * 
 * @author Julian Andres Sanchez <a href="mailto:jusanchez@heinsohn.com.co"></a>
 */
public class ArchivoSupervivenciaDTO implements Serializable {

    /**
     * SERIAL
     */
    private static final long serialVersionUID = 357448766592913292L;

	/**
	 * Codigo del identificador del archivo en el ECM encontrado
	 */
	private String codigoECMRegisEncontrado;
	/**
	 * Codigo del identificador del archivo en el ECM no encontrado
	 */
	private String codigoECMRegisNoEncontrado;
	/**
	 * Identificador del cargue multiple de supervivencia
	 */
	private Long identificadorCargue;
	/**
	 * Fecha de Ingreso en que se registra el cargue multiple de supervivencia
	 */
	private Long fechaIngreso;
	/**
	 * Usuario al que pertenece el cargue múltiple de supervivencia
	 */
	private String usuario;
	/**
	 * Periodo del cargue múltiple de supervivencia
	 */
	private Long periodo;
	/**
	 * Estado del cargue múltiple supervivencia
	 */
	private EstadoCargueSupervivenciaEnum estadoCargue;
	/**
	 * Identificador del ECM a registrar
	 */
	private String identificadorECMRegistro;
	/**
	 * Nombre del archivo perteneciente a cargue de supervivencia
	 */
	private String nombreArhivo;
	/**
	 * Id del cargue 
	 */
	private Long idCargue;
	/**
	 * Información de archivo cargado 
	 */
	private InformacionArchivoDTO infoArchivoCargado;
	/**
	 * Identificador file definition Id de archivo
	 */
	private Long fileDefinitionId;

	/**
	 * Constructor por defecto de la clase
	 */
	public ArchivoSupervivenciaDTO() {
	}

	/**
	 * @return the codigoECMRegisEncontrado
	 */
	public String getCodigoECMRegisEncontrado() {
		return codigoECMRegisEncontrado;
	}

	/**
	 * @return the codigoECMRegisNoEncontrado
	 */
	public String getCodigoECMRegisNoEncontrado() {
		return codigoECMRegisNoEncontrado;
	}

	/**
	 * @param codigoECMRegisEncontrado
	 *            the codigoECMRegisEncontrado to set
	 */
	public void setCodigoECMRegisEncontrado(String codigoECMRegisEncontrado) {
		this.codigoECMRegisEncontrado = codigoECMRegisEncontrado;
	}

	/**
	 * @param codigoECMRegisNoEncontrado
	 *            the codigoECMRegisNoEncontrado to set
	 */
	public void setCodigoECMRegisNoEncontrado(String codigoECMRegisNoEncontrado) {
		this.codigoECMRegisNoEncontrado = codigoECMRegisNoEncontrado;
	}

	/**
	 * @return the fechaIngreso
	 */
	public Long getFechaIngreso() {
		return fechaIngreso;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @return the periodo
	 */
	public Long getPeriodo() {
		return periodo;
	}

	/**
	 * @return the estadoCargue
	 */
	public EstadoCargueSupervivenciaEnum getEstadoCargue() {
		return estadoCargue;
	}

	/**
	 * @param fechaIngreso
	 *            the fechaIngreso to set
	 */
	public void setFechaIngreso(Long fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @param periodo
	 *            the periodo to set
	 */
	public void setPeriodo(Long periodo) {
		this.periodo = periodo;
	}

	/**
	 * @param estadoCargue
	 *            the estadoCargue to set
	 */
	public void setEstadoCargue(EstadoCargueSupervivenciaEnum estadoCargue) {
		this.estadoCargue = estadoCargue;
	}

	/**
	 * @return the identificadorCargue
	 */
	public Long getIdentificadorCargue() {
		return identificadorCargue;
	}

	/**
	 * @param identificadorCargue
	 *            the identificadorCargue to set
	 */
	public void setIdentificadorCargue(Long identificadorCargue) {
		this.identificadorCargue = identificadorCargue;
	}

	/**
	 * @return the identificadorECMRegistro
	 */
	public String getIdentificadorECMRegistro() {
		return identificadorECMRegistro;
	}

	/**
	 * @param identificadorECMRegistro
	 *            the identificadorECMRegistro to set
	 */
	public void setIdentificadorECMRegistro(String identificadorECMRegistro) {
		this.identificadorECMRegistro = identificadorECMRegistro;
	}

	/**
	 * @return the nombreArhivo
	 */
	public String getNombreArhivo() {
		return nombreArhivo;
	}

	/**
	 * @param nombreArhivo
	 *            the nombreArhivo to set
	 */
	public void setNombreArhivo(String nombreArhivo) {
		this.nombreArhivo = nombreArhivo;
	}

    /**
     * @return the idCargue
     */
    public Long getIdCargue() {
        return idCargue;
    }

    /**
     * @param idCargue the idCargue to set
     */
    public void setIdCargue(Long idCargue) {
        this.idCargue = idCargue;
    }

    /**
     * @return the infoArchivoCargado
     */
    public InformacionArchivoDTO getInfoArchivoCargado() {
        return infoArchivoCargado;
    }

    /**
     * @param infoArchivoCargado the infoArchivoCargado to set
     */
    public void setInfoArchivoCargado(InformacionArchivoDTO infoArchivoCargado) {
        this.infoArchivoCargado = infoArchivoCargado;
    }

    /**
     * @return the fileDefinitionId
     */
    public Long getFileDefinitionId() {
        return fileDefinitionId;
    }

    /**
     * @param fileDefinitionId the fileDefinitionId to set
     */
    public void setFileDefinitionId(Long fileDefinitionId) {
        this.fileDefinitionId = fileDefinitionId;
    }

}
