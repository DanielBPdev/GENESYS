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
import com.asopagos.dto.afiliaciones.Afiliado25AniosDTO;
import com.asopagos.dto.afiliaciones.Afiliado25AniosExistenteDTO;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import com.asopagos.enumeraciones.novedades.EstadoCargueArchivoActualizacionEnum;
import com.asopagos.enumeraciones.novedades.EstadoCargueSupervivenciaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.entidadDescuento.EstadoCargaArchivoDescuentoEnum;


/**
 * <b>Descripción:</b> DTO transportar los datos de múltiple afiliación
 * trabajador candidato <b>Historia de Usuario:</b> 122-360,122-364
 *
 * @author Juan Diego Ocampo Q<jocampo@heinsohn.com.co>
 */
@XmlRootElement
public class ResultadoArchivo25AniosDTO implements Serializable {

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
	 * Lista de afiliaciones 25 años
	 * 
	 */
	private List<Afiliado25AniosDTO> listaCandidatosAfiliar25Anios;
	

	/**
	 * Lista de afiliaciones 25 años ya existentes
	 * 
	 */
	private List<Afiliado25AniosExistenteDTO> listaCandidatosExistentes;
	
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
     * FileLoaded id que representa el cargue
     */
    private Long fileLoadedId;
    
	public ResultadoArchivo25AniosDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the totalRegistro
	 */

	public List<AfiliarTrabajadorCandidatoDTO> getAfiliarTrabajadorCandidatoDTO() {
		return this.afiliarTrabajadorCandidatoDTO;
	}

	public void setAfiliarTrabajadorCandidatoDTO(List<AfiliarTrabajadorCandidatoDTO> afiliarTrabajadorCandidatoDTO) {
		this.afiliarTrabajadorCandidatoDTO = afiliarTrabajadorCandidatoDTO;
	}

	public List<TrabajadorCandidatoNovedadDTO> getTrabajadorCandidatoNovedadDTO() {
		return this.trabajadorCandidatoNovedadDTO;
	}

	public void setTrabajadorCandidatoNovedadDTO(List<TrabajadorCandidatoNovedadDTO> trabajadorCandidatoNovedadDTO) {
		this.trabajadorCandidatoNovedadDTO = trabajadorCandidatoNovedadDTO;
	}

	public List<ResultadoSupervivenciaDTO> getLstResultadoSupervivenciaDTO() {
		return this.lstResultadoSupervivenciaDTO;
	}

	public void setLstResultadoSupervivenciaDTO(List<ResultadoSupervivenciaDTO> lstResultadoSupervivenciaDTO) {
		this.lstResultadoSupervivenciaDTO = lstResultadoSupervivenciaDTO;
	}

	public ResultadoCruceFOVISDTO getResultadoCruceFOVISDTO() {
		return this.resultadoCruceFOVISDTO;
	}

	public void setResultadoCruceFOVISDTO(ResultadoCruceFOVISDTO resultadoCruceFOVISDTO) {
		this.resultadoCruceFOVISDTO = resultadoCruceFOVISDTO;
	}

	public List<InformacionActualizacionNovedadDTO> getListActualizacionInfoNovedad() {
		return this.listActualizacionInfoNovedad;
	}

	public void setListActualizacionInfoNovedad(List<InformacionActualizacionNovedadDTO> listActualizacionInfoNovedad) {
		this.listActualizacionInfoNovedad = listActualizacionInfoNovedad;
	}

	public List<Afiliado25AniosDTO> getListaCandidatosAfiliar25Anios() {
		return this.listaCandidatosAfiliar25Anios;
	}

	public void setListaCandidatosAfiliar25Anios(List<Afiliado25AniosDTO> listaCandidatosAfiliar25Anios) {
		this.listaCandidatosAfiliar25Anios = listaCandidatosAfiliar25Anios;
	}

	public List<Afiliado25AniosExistenteDTO> getListaCandidatosExistentes() {
		return this.listaCandidatosExistentes;
	}

	public void setListaCandidatosExistentes(List<Afiliado25AniosExistenteDTO> listaCandidatosExistentes) {
		this.listaCandidatosExistentes = listaCandidatosExistentes;
	}

	public List<ResultadoHallazgosValidacionArchivoDTO> getResultadoHallazgosValidacionArchivoDTO() {
		return this.resultadoHallazgosValidacionArchivoDTO;
	}

	public void setResultadoHallazgosValidacionArchivoDTO(List<ResultadoHallazgosValidacionArchivoDTO> resultadoHallazgosValidacionArchivoDTO) {
		this.resultadoHallazgosValidacionArchivoDTO = resultadoHallazgosValidacionArchivoDTO;
	}

	public EstadoCargaMultipleEnum getEstadoCargue() {
		return this.estadoCargue;
	}

	public void setEstadoCargue(EstadoCargaMultipleEnum estadoCargue) {
		this.estadoCargue = estadoCargue;
	}

	public Long getIdCargue() {
		return this.idCargue;
	}

	public void setIdCargue(Long idCargue) {
		this.idCargue = idCargue;
	}

	public Map<Long,String> getLstCandidatos() {
		return this.lstCandidatos;
	}

	public void setLstCandidatos(Map<Long,String> lstCandidatos) {
		this.lstCandidatos = lstCandidatos;
	}

	public String getNombreArchivo() {
		return this.nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public Long getTotalRegistro() {
		return this.totalRegistro;
	}

	public void setTotalRegistro(Long totalRegistro) {
		this.totalRegistro = totalRegistro;
	}

	public Long getRegistrosConErrores() {
		return this.registrosConErrores;
	}

	public void setRegistrosConErrores(Long registrosConErrores) {
		this.registrosConErrores = registrosConErrores;
	}

	public Long getRegistrosValidos() {
		return this.registrosValidos;
	}

	public void setRegistrosValidos(Long registrosValidos) {
		this.registrosValidos = registrosValidos;
	}

	public Long getFechaCargue() {
		return this.fechaCargue;
	}

	public void setFechaCargue(Long fechaCargue) {
		this.fechaCargue = fechaCargue;
	}

	public Long getFileDefinitionId() {
		return this.fileDefinitionId;
	}

	public void setFileDefinitionId(Long fileDefinitionId) {
		this.fileDefinitionId = fileDefinitionId;
	}

	public boolean isArchivoRegEncontrado() {
		return this.archivoRegEncontrado;
	}

	public boolean getArchivoRegEncontrado() {
		return this.archivoRegEncontrado;
	}

	public void setArchivoRegEncontrado(boolean archivoRegEncontrado) {
		this.archivoRegEncontrado = archivoRegEncontrado;
	}

	public String getUsuarioRegistro() {
		return this.usuarioRegistro;
	}

	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	public EstadoCargueSupervivenciaEnum getEstadoCargueSupervivencia() {
		return this.estadoCargueSupervivencia;
	}

	public void setEstadoCargueSupervivencia(EstadoCargueSupervivenciaEnum estadoCargueSupervivencia) {
		this.estadoCargueSupervivencia = estadoCargueSupervivencia;
	}

	public EstadoCargueArchivoActualizacionEnum getEstadoCargueActualizacion() {
		return this.estadoCargueActualizacion;
	}

	public void setEstadoCargueActualizacion(EstadoCargueArchivoActualizacionEnum estadoCargueActualizacion) {
		this.estadoCargueActualizacion = estadoCargueActualizacion;
	}

	public EstadoCargaArchivoDescuentoEnum getEstadoCargueArchivoDescuento() {
		return this.estadoCargueArchivoDescuento;
	}

	public void setEstadoCargueArchivoDescuento(EstadoCargaArchivoDescuentoEnum estadoCargueArchivoDescuento) {
		this.estadoCargueArchivoDescuento = estadoCargueArchivoDescuento;
	}

	public Long getFileLoadedId() {
		return this.fileLoadedId;
	}

	public void setFileLoadedId(Long fileLoadedId) {
		this.fileLoadedId = fileLoadedId;
	}
	

}