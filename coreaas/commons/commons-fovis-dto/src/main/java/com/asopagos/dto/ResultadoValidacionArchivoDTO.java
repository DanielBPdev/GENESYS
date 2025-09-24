/**
 * 
 */
package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.dto.cargaMultiple.TrabajadorCandidatoNovedadDTO;
import com.asopagos.entidades.transversal.core.CargueTrasladoMedioPagoTranferencia;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import com.asopagos.enumeraciones.novedades.EstadoCargueArchivoActualizacionEnum;
import com.asopagos.enumeraciones.novedades.EstadoCargueSupervivenciaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.entidadDescuento.EstadoCargaArchivoDescuentoEnum;
import com.asopagos.enumeraciones.aportes.EstadoCargaArchivoCrucesAportesEnum;
import com.asopagos.entidades.transversal.core.CargueCertificadosMasivos;

/**
 * <b>Descripción:</b> DTO transportar los datos de múltiple afiliación
 * trabajador candidato <b>Historia de Usuario:</b> 122-360,122-364
 *
 * @author Juan Diego Ocampo Q<jocampo@heinsohn.com.co>
 */
@XmlRootElement
public class ResultadoValidacionArchivoDTO implements Serializable {

	/**
	 * SERIAL
	 */
	private static final long serialVersionUID = -3859432814424072116L;

	/**
	 * listado de trabajadores candidatos pertenecientes a afiliciones multiples
	 */
	private List<AfiliarTrabajadorCandidatoDTO> afiliarTrabajadorCandidatoDTO;
	/**
	 * listado de trabajadores candidatos para novedades
	 */
	private List<TrabajadorCandidatoNovedadDTO> trabajadorCandidatoNovedadDTO;
	/**
	 * lista de resultados de supervivencia
	 */
	private List<ResultadoSupervivenciaDTO> lstResultadoSupervivenciaDTO;
	
	/**
	 * Resultado de cruce FOVIS
	 */
	private ResultadoCruceFOVISDTO resultadoCruceFOVISDTO;
	
	/**
	 * Lista de informacion a revisar para el registro de novedades
	 */
	private List<InformacionActualizacionNovedadDTO> listActualizacionInfoNovedad;
	
	/**
	 * listado de errores presentados.
	 */
	private List<ResultadoHallazgosValidacionArchivoDTO> resultadoHallazgosValidacionArchivoDTO;
	/**
	 * Estado del cargue Multiple
	 */
	private EstadoCargaMultipleEnum estadoCargue;
	/**
	 * Id perteneciente al cargue multiple
	 */
	private Long idCargue;
	/**
	 * Listado de los candidatos con una llave y un json
	 */
	private Map<Long, String> lstCandidatos;
	/**
	 * Nombre del archivo que se valido
	 */
	private String nombreArchivo;
	/**
	 * Se cuenta con el total de registros cargados en el archivo
	 */
	private Long totalRegistro;
	/**
	 * Se tiene el total de registros con errores
	 */
	private Long registrosConErrores;
	/**
	 * Se tiene el total de registros validos
	 */
	private Long registrosValidos;

	/**
	 * Fecha en que se realizo el cargue múltiple
	 */
	private Long fechaCargue;
	/**
	 * FileDefinition id que se tiene por el cargue
	 */
	private Long fileDefinitionId;
	/**
	 * Bandera encargada de identificar si el tipo de archivo es de registros
	 * encontrados(true) o no(false)
	 */
	private boolean archivoRegEncontrado;
	/**
	 * Usuario que realiza el cargue
	 */
	private String usuarioRegistro;
	/**
	 * Estado del cargue de supervivencia
	 */
	private EstadoCargueSupervivenciaEnum estadoCargueSupervivencia;
	
	/**
     * Estado del cargue de supervivencia
     */
    private EstadoCargueArchivoActualizacionEnum estadoCargueActualizacion;
    
    /**
     * Estado del cargue del archivo de descuento
     */
    private EstadoCargaArchivoDescuentoEnum estadoCargueArchivoDescuento;

    /**
     * Estado del cargue del archivo de estadoCargueArchivoCrucesAportes
     */
    private EstadoCargaArchivoCrucesAportesEnum estadoCargueArchivoCrucesAportes;
    /**
     * FileLoaded id que representa el cargue
     */
    private Long fileLoadedId;

	/**
	 * listado de cargueTrasladoMedioPagoTranferencia
	 */
	private List<CargueTrasladoMedioPagoTranferencia> cargueTrasladoMedioPagoTranferencia;

	private List<CargueCertificadosMasivos> cargueCertificadosMasivos;
    
	public ResultadoValidacionArchivoDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the totalRegistro
	 */
	public Long getTotalRegistro() {
		return totalRegistro;
	}

	/**
	 * @param totalRegistro
	 *            the totalRegistro to set
	 */
	public void setTotalRegistro(Long totalRegistro) {
		this.totalRegistro = totalRegistro;
	}

	/**
	 * @return the idCargue
	 */
	public Long getIdCargue() {
		return idCargue;
	}

	/**
	 * @param idCargue
	 *            the idCargue to set
	 */
	public void setIdCargue(Long idCargue) {
		this.idCargue = idCargue;
	}

	/**
	 * @return the afiliarTrabajadorCandidatoDTO
	 */
	public List<AfiliarTrabajadorCandidatoDTO> getAfiliarTrabajadorCandidatoDTO() {
		return afiliarTrabajadorCandidatoDTO;
	}

	/**
	 * @param afiliarTrabajadorCandidatoDTO
	 *            the afiliarTrabajadorCandidatoDTO to set
	 */
	public void setAfiliarTrabajadorCandidatoDTO(List<AfiliarTrabajadorCandidatoDTO> afiliarTrabajadorCandidatoDTO) {
		this.afiliarTrabajadorCandidatoDTO = afiliarTrabajadorCandidatoDTO;
	}

	/**
	 * @return the resultadoHallazgosValidacionArchivoDTO
	 */
	public List<ResultadoHallazgosValidacionArchivoDTO> getResultadoHallazgosValidacionArchivoDTO() {
		return resultadoHallazgosValidacionArchivoDTO;
	}

	/**
	 * @param resultadoHallazgosValidacionArchivoDTO
	 *            the resultadoHallazgosValidacionArchivoDTO to set
	 */
	public void setResultadoHallazgosValidacionArchivoDTO(
			List<ResultadoHallazgosValidacionArchivoDTO> resultadoHallazgosValidacionArchivoDTO) {
		this.resultadoHallazgosValidacionArchivoDTO = resultadoHallazgosValidacionArchivoDTO;
	}

	/**
	 * @return the estadoCargue
	 */
	public EstadoCargaMultipleEnum getEstadoCargue() {
		return estadoCargue;
	}

	/**
	 * @param estadoCargue
	 *            the estadoCargue to set
	 */
	public void setEstadoCargue(EstadoCargaMultipleEnum estadoCargue) {
		this.estadoCargue = estadoCargue;
	}

	/**
	 * @return the nombreArchivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	/**
	 * @param nombreArchivo
	 *            the nombreArchivo to set
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	/**
	 * @return the lstCandidatos
	 */
	public Map<Long, String> getLstCandidatos() {
		return lstCandidatos;
	}

	/**
	 * @param lstCandidatos
	 *            the lstCandidatos to set
	 */
	public void setLstCandidatos(Map<Long, String> lstCandidatos) {
		this.lstCandidatos = lstCandidatos;
	}

	/**
	 * @return the trabajadorCandidatoNovedadDTO
	 */
	public List<TrabajadorCandidatoNovedadDTO> getTrabajadorCandidatoNovedadDTO() {
		return trabajadorCandidatoNovedadDTO;
	}

	/**
	 * @param trabajadorCandidatoNovedadDTO
	 *            the trabajadorCandidatoNovedadDTO to set
	 */
	public void setTrabajadorCandidatoNovedadDTO(List<TrabajadorCandidatoNovedadDTO> trabajadorCandidatoNovedadDTO) {
		this.trabajadorCandidatoNovedadDTO = trabajadorCandidatoNovedadDTO;
	}

	/**
	 * @return the fechaCargue
	 */
	public Long getFechaCargue() {
		return fechaCargue;
	}

	/**
	 * @param fechaCargue
	 *            the fechaCargue to set
	 */
	public void setFechaCargue(Long fechaCargue) {
		this.fechaCargue = fechaCargue;
	}

	/**
	 * @return the fileDefinitionId
	 */
	public Long getFileDefinitionId() {
		return fileDefinitionId;
	}

	/**
	 * @param fileDefinitionId
	 *            the fileDefinitionId to set
	 */
	public void setFileDefinitionId(Long fileDefinitionId) {
		this.fileDefinitionId = fileDefinitionId;
	}

	/**
	 * @return the registrosConErrores
	 */
	public Long getRegistrosConErrores() {
		return registrosConErrores;
	}

	/**
	 * @return the registrosValidos
	 */
	public Long getRegistrosValidos() {
		return registrosValidos;
	}

	/**
	 * @param registrosConErrores
	 *            the registrosConErrores to set
	 */
	public void setRegistrosConErrores(Long registrosConErrores) {
		this.registrosConErrores = registrosConErrores;
	}

	/**
	 * @param registrosValidos
	 *            the registrosValidos to set
	 */
	public void setRegistrosValidos(Long registrosValidos) {
		this.registrosValidos = registrosValidos;
	}

	/**
	 * @return the archivoRegEncontrado
	 */
	public boolean isArchivoRegEncontrado() {
		return archivoRegEncontrado;
	}

	/**
	 * @param archivoRegEncontrado
	 *            the archivoRegEncontrado to set
	 */
	public void setArchivoRegEncontrado(boolean archivoRegEncontrado) {
		this.archivoRegEncontrado = archivoRegEncontrado;
	}

	/**
	 * @return the lstResultadoSupervivenciaDTO
	 */
	public List<ResultadoSupervivenciaDTO> getLstResultadoSupervivenciaDTO() {
		return lstResultadoSupervivenciaDTO;
	}

	/**
	 * @param lstResultadoSupervivenciaDTO
	 *            the lstResultadoSupervivenciaDTO to set
	 */
	public void setLstResultadoSupervivenciaDTO(List<ResultadoSupervivenciaDTO> lstResultadoSupervivenciaDTO) {
		this.lstResultadoSupervivenciaDTO = lstResultadoSupervivenciaDTO;
	}

	/**
	 * @return the usuarioRegistro
	 */
	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}

	/**
	 * @param usuarioRegistro
	 *            the usuarioRegistro to set
	 */
	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	/**
	 * @return the estadoCargueSupervivencia
	 */
	public EstadoCargueSupervivenciaEnum getEstadoCargueSupervivencia() {
		return estadoCargueSupervivencia;
	}

	/**
	 * @param estadoCargueSupervivencia
	 *            the estadoCargueSupervivencia to set
	 */
	public void setEstadoCargueSupervivencia(EstadoCargueSupervivenciaEnum estadoCargueSupervivencia) {
		this.estadoCargueSupervivencia = estadoCargueSupervivencia;
	}

    /**
     * @return the listActualizacionInfoNovedad
     */
    public List<InformacionActualizacionNovedadDTO> getListActualizacionInfoNovedad() {
        return listActualizacionInfoNovedad;
    }

    /**
     * @param listActualizacionInfoNovedad the listActualizacionInfoNovedad to set
     */
    public void setListActualizacionInfoNovedad(List<InformacionActualizacionNovedadDTO> listActualizacionInfoNovedad) {
        this.listActualizacionInfoNovedad = listActualizacionInfoNovedad;
    }

    /**
     * @return the estadoCargueActualizacion
     */
    public EstadoCargueArchivoActualizacionEnum getEstadoCargueActualizacion() {
        return estadoCargueActualizacion;
    }

    /**
     * @param estadoCargueActualizacion the estadoCargueActualizacion to set
     */
    public void setEstadoCargueActualizacion(EstadoCargueArchivoActualizacionEnum estadoCargueActualizacion) {
        this.estadoCargueActualizacion = estadoCargueActualizacion;
    }

    /**
     * @return the estadoCargueArchivoDescuento
     */
    public EstadoCargaArchivoDescuentoEnum getEstadoCargueArchivoDescuento() {
        return estadoCargueArchivoDescuento;
    }

    /**
     * @param estadoCargueArchivoDescuento the estadoCargueArchivoDescuento to set
     */
    public void setEstadoCargueArchivoDescuento(EstadoCargaArchivoDescuentoEnum estadoCargueArchivoDescuento) {
        this.estadoCargueArchivoDescuento = estadoCargueArchivoDescuento;
    }

    /**
     * @return the EstadoCargueArchivoCrucesAportes
     */
    public EstadoCargaArchivoCrucesAportesEnum getEstadoCargueArchivoCrucesAportes() {
        return estadoCargueArchivoCrucesAportes;
    }

    /**
     * @param estadoCargueArchivoCrucesAportes the EstadoCargueArchivoCrucesAportes to set
     */
    public void setEstadoCargueArchivoCrucesAportes(EstadoCargaArchivoCrucesAportesEnum estadoCargueArchivoCrucesAportes) {
        this.estadoCargueArchivoCrucesAportes = estadoCargueArchivoCrucesAportes;
    }
    /**
     * @return the resultadoCruceFOVISDTO
     */
    public ResultadoCruceFOVISDTO getResultadoCruceFOVISDTO() {
        return resultadoCruceFOVISDTO;
    }

    /**
     * @param resultadoCruceFOVISDTO the resultadoCruceFOVISDTO to set
     */
    public void setResultadoCruceFOVISDTO(ResultadoCruceFOVISDTO resultadoCruceFOVISDTO) {
        this.resultadoCruceFOVISDTO = resultadoCruceFOVISDTO;
    }

    /**
     * @return the fileLoadedId
     */
    public Long getFileLoadedId() {
        return fileLoadedId;
    }

    /**
     * @param fileLoadedId the fileLoadedId to set
     */
    public void setFileLoadedId(Long fileLoadedId) {
        this.fileLoadedId = fileLoadedId;
    }

		/**
	 * @return the cargueTrasladoMedioPagoTranferencia
	 */
	public List<CargueTrasladoMedioPagoTranferencia> getCargueTrasladoMedioPagoTranferencia() {
		return cargueTrasladoMedioPagoTranferencia;
	}

	/**
	 * @param cargueTrasladoMedioPagoTranferencia
	 *            the cargueTrasladoMedioPagoTranferencia to set
	 */
	public void setCargueTrasladoMedioPagoTranferencia(
			List<CargueTrasladoMedioPagoTranferencia> cargueTrasladoMedioPagoTranferencia) {
		this.cargueTrasladoMedioPagoTranferencia = cargueTrasladoMedioPagoTranferencia;
	}

	public boolean getArchivoRegEncontrado() {
		return this.archivoRegEncontrado;
	}


	public List<CargueCertificadosMasivos> getCargueCertificadosMasivos() {
		return this.cargueCertificadosMasivos;
	}

	public void setCargueCertificadosMasivos(List<CargueCertificadosMasivos> cargueCertificadosMasivos) {
		this.cargueCertificadosMasivos = cargueCertificadosMasivos;
	}

	@Override
	public String toString() {
		return "{" +
			" afiliarTrabajadorCandidatoDTO='" + getAfiliarTrabajadorCandidatoDTO() + "'" +
			", trabajadorCandidatoNovedadDTO='" + getTrabajadorCandidatoNovedadDTO() + "'" +
			", lstResultadoSupervivenciaDTO='" + getLstResultadoSupervivenciaDTO() + "'" +
			", resultadoCruceFOVISDTO='" + getResultadoCruceFOVISDTO() + "'" +
			", listActualizacionInfoNovedad='" + getListActualizacionInfoNovedad() + "'" +
			", resultadoHallazgosValidacionArchivoDTO='" + getResultadoHallazgosValidacionArchivoDTO() + "'" +
			", estadoCargue='" + getEstadoCargue() + "'" +
			", idCargue='" + getIdCargue() + "'" +
			", lstCandidatos='" + getLstCandidatos() + "'" +
			", nombreArchivo='" + getNombreArchivo() + "'" +
			", totalRegistro='" + getTotalRegistro() + "'" +
			", registrosConErrores='" + getRegistrosConErrores() + "'" +
			", registrosValidos='" + getRegistrosValidos() + "'" +
			", fechaCargue='" + getFechaCargue() + "'" +
			", fileDefinitionId='" + getFileDefinitionId() + "'" +
			", archivoRegEncontrado='" + isArchivoRegEncontrado() + "'" +
			", usuarioRegistro='" + getUsuarioRegistro() + "'" +
			", estadoCargueSupervivencia='" + getEstadoCargueSupervivencia() + "'" +
			", estadoCargueActualizacion='" + getEstadoCargueActualizacion() + "'" +
			", estadoCargueArchivoDescuento='" + getEstadoCargueArchivoDescuento() + "'" +
			", estadoCargueArchivoCrucesAportes='" + getEstadoCargueArchivoCrucesAportes() + "'" +
			", fileLoadedId='" + getFileLoadedId() + "'" +
			", cargueTrasladoMedioPagoTranferencia='" + getCargueTrasladoMedioPagoTranferencia() + "'" +
			", cargueCertificadosMasivos='" + getCargueCertificadosMasivos() + "'" +
			"}";
	}

}
