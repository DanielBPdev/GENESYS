/**
 * 
 */
package com.asopagos.aportes.masivos.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.enumeraciones.novedades.EstadoCargueSupervivenciaEnum;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * DTO que contendra los archivos de aportes del HU 035-212-xxx
 * 
 * @author Juan David Quintero <a href="mailto:juan.quintero@asopagos.com"></a>
 */
@XmlRootElement
public class ArchivoAportesDTO implements Serializable {

    /**
     * SERIAL
     */
    private static final long serialVersionUID = 357448766592913292L;

	/**
	 * Codigo del identificador del archivo en el ECM
	 */
	private String codigoECM;

	private Long idArchivoMasivo;
	/**
	 * Identificador del cargue multiple de aportes
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
	 * Nombre del archivo
	 */
	private String nombreArchivo;
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
	public ArchivoAportesDTO() {
	}


	public String getCodigoECM() {
		return this.codigoECM;
	}

	public void setCodigoECM(String codigoECM) {
		this.codigoECM = codigoECM;
	}

	public Long getIdentificadorCargue() {
		return this.identificadorCargue;
	}

	public void setIdentificadorCargue(Long identificadorCargue) {
		this.identificadorCargue = identificadorCargue;
	}

	public Long getFechaIngreso() {
		return this.fechaIngreso;
	}

	public void setFechaIngreso(Long fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNombreArchivo() {
		return this.nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public InformacionArchivoDTO getInfoArchivoCargado() {
		return this.infoArchivoCargado;
	}

	public void setInfoArchivoCargado(InformacionArchivoDTO infoArchivoCargado) {
		this.infoArchivoCargado = infoArchivoCargado;
	}

	public Long getFileDefinitionId() {
		return this.fileDefinitionId;
	}

	public void setFileDefinitionId(Long fileDefinitionId) {
		this.fileDefinitionId = fileDefinitionId;
	}


	public Long getIdArchivoMasivo() {
		return this.idArchivoMasivo;
	}

	public void setIdArchivoMasivo(Long idArchivoMasivo) {
		this.idArchivoMasivo = idArchivoMasivo;
	}


}
