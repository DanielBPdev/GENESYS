package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.MotivoProcesoPilaManualEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoCargaArchivoEnum;


/**<b>Descripción: </b> DTO que contiene la estructura de datos de un registro de <code>PilaIndicePlanilla</code> <br/>
 * <b>Historia de Usuario: </b> Transversal 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez Benavides</a>
 */
@XmlRootElement
public class IndicePlanillaModeloDTO implements Serializable {
    private static final long serialVersionUID = -8598343558544320642L;

    /**
     * Código identificador de llave primaria de la entrada de índice de planilla de Operador de Información
     */
    private Long id;

    /**
     * Número de la planilla cargada
     */
    private Long idPlanilla;

    /**
     * Descripción del tipo de archivo
     */
    private TipoArchivoPilaEnum tipoArchivo;

    /**
     * Nombre del archivo cargado
     */
    private String nombreArchivo;

    /**
     * Fecha y hora de carga del archivo al índice de planillas
     */
    private Date fechaRecibo;

    /**
     * Fecha y hora de ultima modificación del archivo
     */
    private Date fechaFtp;

    /**
     * Descripción del código del Operador de Información de quien se recibe el archivo
     */
    private String codigoOperadorInformacion;

    /**
     * Descripción del estado actual del proceso del archivo
     */
    private EstadoProcesoArchivoEnum estadoArchivo;

    /**
     * Decripción del tipo de carga del archivo
     */
    private TipoCargaArchivoEnum tipoCargaArchivo;

    /**
     * Descripción del usuario que realiza la carga
     */
    private String usuario;

    /**
     * Identificador del archivo almacenado en el repositorio ECM
     */
    private String idDocumento;

    /**
     * Versión del documento en el repositorio ECM
     */
    private String versionDocumento;

    /**
     * Fecha y hora en la que inicia el procesamiento del archivo
     */
    private Date fechaProceso;

    /**
     * Descripción del usuario que inicia el procesamiento del archivo
     */
    private String usuarioProceso;

    /**
     * Fecha y hora en la que se solicita la eliminación o anulación del archivo en el caso de no ser válido para su procesamiento
     */
    private Date fechaEliminacion;

    /**
     * Descripción del usuario que solicita la eliminación o anulación del archivo
     */
    private String usuarioEliminacion;

    /**
     * Indicador que determina sí el archivo se puede procesar
     */
    private Boolean procesar;

    /**
     * Indicador del estado activo del registro
     */
    private Boolean registroActivo;

    /**
     * Identificador que determina que el registro se encuentra incluido en una lista de procesamiento
     */
    private Boolean enLista;

    /**
     * Tamaño del archivo en Bytes
     */
    private Long tamanoArchivo;

    /**
     * Indicador de archivo habilitado para gestión manual de PILA 2
     */
    private Boolean habilitadoProcesoManual;
    
    /**
     * Indicador de presencia de un registro tipo 4
     * */
    private Boolean presentaRegistro4;
    
    /**
     * Motivo para para remitir la planilla a gestión manual de PILA 2
     * */
    private MotivoProcesoPilaManualEnum motivoProcesoManual;
    
    /**
     * Constructor por defecto
     * */
    public IndicePlanillaModeloDTO (){
        super();
    }
    
    /**
     * Constructor con base en Entity
     * */
    public IndicePlanillaModeloDTO(IndicePlanilla indicePlanilla){
        super();
        convertToDTO(indicePlanilla);
    }

	/**
	 * Método que crea un objeto <code>IndicePlanillaModeloDTO</code> a partir de una entidad tipo <code>IndicePlanilla</code>
	 * @param indicePlanilla Entidad a partir de la cual se formará el DTO
	 */
	public void convertToDTO(IndicePlanilla indicePlanilla) {
		this.id = indicePlanilla.getId();
		this.idPlanilla = indicePlanilla.getIdPlanilla();
		this.tipoArchivo = indicePlanilla.getTipoArchivo();
		this.nombreArchivo = indicePlanilla.getNombreArchivo();
		this.fechaRecibo = indicePlanilla.getFechaRecibo();
		this.fechaFtp = indicePlanilla.getFechaFtp();
		this.codigoOperadorInformacion = indicePlanilla.getCodigoOperadorInformacion();
		this.estadoArchivo = indicePlanilla.getEstadoArchivo();
		this.tipoCargaArchivo = indicePlanilla.getTipoCargaArchivo();
		this.usuario = indicePlanilla.getUsuario();
		this.idDocumento = indicePlanilla.getIdDocumento();
		this.versionDocumento = indicePlanilla.getVersionDocumento();
		this.fechaProceso = indicePlanilla.getFechaProceso();
		this.usuarioProceso = indicePlanilla.getUsuarioProceso();
		this.fechaEliminacion = indicePlanilla.getFechaEliminacion();
		this.usuarioEliminacion = indicePlanilla.getUsuarioEliminacion();
		this.procesar = indicePlanilla.getProcesar();
		this.registroActivo = indicePlanilla.getRegistroActivo();
		this.enLista = indicePlanilla.getEnLista();
		this.tamanoArchivo = indicePlanilla.getTamanoArchivo();
		this.habilitadoProcesoManual = indicePlanilla.getHabilitadoProcesoManual();
		this.presentaRegistro4 = indicePlanilla.getPresentaRegistro4();
		this.motivoProcesoManual = indicePlanilla.getMotivoProcesoManual();
	}
	
	/**
	 * Método que convierte un <code>IndicePlanillaModeloDTO</code> en una entidad tipo <code>IndicePlanilla</code>
	 * */
	public IndicePlanilla convertToEntity(){
	    IndicePlanilla indicePlanilla = new IndicePlanilla();
	    
	    indicePlanilla.setId(this.getId());
	    indicePlanilla.setIdPlanilla(this.getIdPlanilla());
	    indicePlanilla.setTipoArchivo(this.getTipoArchivo());
	    indicePlanilla.setNombreArchivo(this.getNombreArchivo());
	    indicePlanilla.setFechaRecibo(this.getFechaRecibo());
	    indicePlanilla.setFechaFtp(this.getFechaFtp());
	    indicePlanilla.setCodigoOperadorInformacion(this.getCodigoOperadorInformacion());
	    indicePlanilla.setEstadoArchivo(this.getEstadoArchivo());
	    indicePlanilla.setTipoCargaArchivo(this.getTipoCargaArchivo());
	    indicePlanilla.setUsuario(this.getUsuario());
	    indicePlanilla.setIdDocumento(this.getIdDocumento());
	    indicePlanilla.setVersionDocumento(this.getVersionDocumento());
	    indicePlanilla.setFechaProceso(this.getFechaProceso());
	    indicePlanilla.setUsuarioProceso(this.getUsuarioProceso());
	    indicePlanilla.setFechaEliminacion(this.getFechaEliminacion());
	    indicePlanilla.setUsuarioEliminacion(this.getUsuarioEliminacion());
	    indicePlanilla.setProcesar(this.getProcesar());
	    indicePlanilla.setRegistroActivo(this.getRegistroActivo());
	    indicePlanilla.setEnLista(this.getEnLista());
	    indicePlanilla.setTamanoArchivo(this.getTamanoArchivo());
	    indicePlanilla.setHabilitadoProcesoManual(this.getHabilitadoProcesoManual());
	    indicePlanilla.setPresentaRegistro4(this.getPresentaRegistro4());
	    indicePlanilla.setMotivoProcesoManual(this.getMotivoProcesoManual());
	    
	    return indicePlanilla;
	}

	/**Obtiene el valor de id
	 * @return El valor de id
	 */
	public Long getId() {
		return id;
	}

	/** Establece el valor de id
	 * @param id El valor de id por asignar
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**Obtiene el valor de idPlanilla
	 * @return El valor de idPlanilla
	 */
	public Long getIdPlanilla() {
		return idPlanilla;
	}

	/** Establece el valor de idPlanilla
	 * @param idPlanilla El valor de idPlanilla por asignar
	 */
	public void setIdPlanilla(Long idPlanilla) {
		this.idPlanilla = idPlanilla;
	}

	/**Obtiene el valor de tipoArchivo
	 * @return El valor de tipoArchivo
	 */
	public TipoArchivoPilaEnum getTipoArchivo() {
		return tipoArchivo;
	}

	/** Establece el valor de tipoArchivo
	 * @param tipoArchivo El valor de tipoArchivo por asignar
	 */
	public void setTipoArchivo(TipoArchivoPilaEnum tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	/**Obtiene el valor de nombreArchivo
	 * @return El valor de nombreArchivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	/** Establece el valor de nombreArchivo
	 * @param nombreArchivo El valor de nombreArchivo por asignar
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	/**Obtiene el valor de fechaRecibo
	 * @return El valor de fechaRecibo
	 */
	public Date getFechaRecibo() {
		return fechaRecibo;
	}

	/** Establece el valor de fechaRecibo
	 * @param fechaRecibo El valor de fechaRecibo por asignar
	 */
	public void setFechaRecibo(Date fechaRecibo) {
		this.fechaRecibo = fechaRecibo;
	}

	/**Obtiene el valor de fechaFtp
	 * @return El valor de fechaFtp
	 */
	public Date getFechaFtp() {
		return fechaFtp;
	}

	/** Establece el valor de fechaFtp
	 * @param fechaFtp El valor de fechaFtp por asignar
	 */
	public void setFechaFtp(Date fechaFtp) {
		this.fechaFtp = fechaFtp;
	}

	/**Obtiene el valor de codigoOperadorInformacion
	 * @return El valor de codigoOperadorInformacion
	 */
	public String getCodigoOperadorInformacion() {
		return codigoOperadorInformacion;
	}

	/** Establece el valor de codigoOperadorInformacion
	 * @param codigoOperadorInformacion El valor de codigoOperadorInformacion por asignar
	 */
	public void setCodigoOperadorInformacion(String codigoOperadorInformacion) {
		this.codigoOperadorInformacion = codigoOperadorInformacion;
	}

	/**Obtiene el valor de estadoArchivo
	 * @return El valor de estadoArchivo
	 */
	public EstadoProcesoArchivoEnum getEstadoArchivo() {
		return estadoArchivo;
	}

	/** Establece el valor de estadoArchivo
	 * @param estadoArchivo El valor de estadoArchivo por asignar
	 */
	public void setEstadoArchivo(EstadoProcesoArchivoEnum estadoArchivo) {
		this.estadoArchivo = estadoArchivo;
	}

	/**Obtiene el valor de tipoCargaArchivo
	 * @return El valor de tipoCargaArchivo
	 */
	public TipoCargaArchivoEnum getTipoCargaArchivo() {
		return tipoCargaArchivo;
	}

	/** Establece el valor de tipoCargaArchivo
	 * @param tipoCargaArchivo El valor de tipoCargaArchivo por asignar
	 */
	public void setTipoCargaArchivo(TipoCargaArchivoEnum tipoCargaArchivo) {
		this.tipoCargaArchivo = tipoCargaArchivo;
	}

	/**Obtiene el valor de usuario
	 * @return El valor de usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/** Establece el valor de usuario
	 * @param usuario El valor de usuario por asignar
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**Obtiene el valor de idDocumento
	 * @return El valor de idDocumento
	 */
	public String getIdDocumento() {
		return idDocumento;
	}

	/** Establece el valor de idDocumento
	 * @param idDocumento El valor de idDocumento por asignar
	 */
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	/**Obtiene el valor de versionDocumento
	 * @return El valor de versionDocumento
	 */
	public String getVersionDocumento() {
		return versionDocumento;
	}

	/** Establece el valor de versionDocumento
	 * @param versionDocumento El valor de versionDocumento por asignar
	 */
	public void setVersionDocumento(String versionDocumento) {
		this.versionDocumento = versionDocumento;
	}

	/**Obtiene el valor de fechaProceso
	 * @return El valor de fechaProceso
	 */
	public Date getFechaProceso() {
		return fechaProceso;
	}

	/** Establece el valor de fechaProceso
	 * @param fechaProceso El valor de fechaProceso por asignar
	 */
	public void setFechaProceso(Date fechaProceso) {
		this.fechaProceso = fechaProceso;
	}

	/**Obtiene el valor de usuarioProceso
	 * @return El valor de usuarioProceso
	 */
	public String getUsuarioProceso() {
		return usuarioProceso;
	}

	/** Establece el valor de usuarioProceso
	 * @param usuarioProceso El valor de usuarioProceso por asignar
	 */
	public void setUsuarioProceso(String usuarioProceso) {
		this.usuarioProceso = usuarioProceso;
	}

	/**Obtiene el valor de fechaEliminacion
	 * @return El valor de fechaEliminacion
	 */
	public Date getFechaEliminacion() {
		return fechaEliminacion;
	}

	/** Establece el valor de fechaEliminacion
	 * @param fechaEliminacion El valor de fechaEliminacion por asignar
	 */
	public void setFechaEliminacion(Date fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}

	/**Obtiene el valor de usuarioEliminacion
	 * @return El valor de usuarioEliminacion
	 */
	public String getUsuarioEliminacion() {
		return usuarioEliminacion;
	}

	/** Establece el valor de usuarioEliminacion
	 * @param usuarioEliminacion El valor de usuarioEliminacion por asignar
	 */
	public void setUsuarioEliminacion(String usuarioEliminacion) {
		this.usuarioEliminacion = usuarioEliminacion;
	}

	/**Obtiene el valor de procesar
	 * @return El valor de procesar
	 */
	public Boolean getProcesar() {
		return procesar;
	}

	/** Establece el valor de procesar
	 * @param procesar El valor de procesar por asignar
	 */
	public void setProcesar(Boolean procesar) {
		this.procesar = procesar;
	}

	/**Obtiene el valor de registroActivo
	 * @return El valor de registroActivo
	 */
	public Boolean getRegistroActivo() {
		return registroActivo;
	}

	/** Establece el valor de registroActivo
	 * @param registroActivo El valor de registroActivo por asignar
	 */
	public void setRegistroActivo(Boolean registroActivo) {
		this.registroActivo = registroActivo;
	}

	/**Obtiene el valor de enLista
	 * @return El valor de enLista
	 */
	public Boolean getEnLista() {
		return enLista;
	}

	/** Establece el valor de enLista
	 * @param enLista El valor de enLista por asignar
	 */
	public void setEnLista(Boolean enLista) {
		this.enLista = enLista;
	}

	/**Obtiene el valor de tamanoArchivo
	 * @return El valor de tamanoArchivo
	 */
	public Long getTamanoArchivo() {
		return tamanoArchivo;
	}

	/** Establece el valor de tamanoArchivo
	 * @param tamanoArchivo El valor de tamanoArchivo por asignar
	 */
	public void setTamanoArchivo(Long tamanoArchivo) {
		this.tamanoArchivo = tamanoArchivo;
	}

	/**Obtiene el valor de habilitadoProcesoManual
	 * @return El valor de habilitadoProcesoManual
	 */
	public Boolean getHabilitadoProcesoManual() {
		return habilitadoProcesoManual;
	}

	/** Establece el valor de habilitadoProcesoManual
	 * @param habilitadoProcesoManual El valor de habilitadoProcesoManual por asignar
	 */
	public void setHabilitadoProcesoManual(Boolean habilitadoProcesoManual) {
		this.habilitadoProcesoManual = habilitadoProcesoManual;
	}

    /**
     * @return the presentaRegistro4
     */
    public Boolean getPresentaRegistro4() {
        return presentaRegistro4;
    }

    /**
     * @param presentaRegistro4 the presentaRegistro4 to set
     */
    public void setPresentaRegistro4(Boolean presentaRegistro4) {
        this.presentaRegistro4 = presentaRegistro4;
    }

    /**
     * @return the motivoProcesoManual
     */
    public MotivoProcesoPilaManualEnum getMotivoProcesoManual() {
        return motivoProcesoManual;
    }

    /**
     * @param motivoProcesoManual the motivoProcesoManual to set
     */
    public void setMotivoProcesoManual(MotivoProcesoPilaManualEnum motivoProcesoManual) {
        this.motivoProcesoManual = motivoProcesoManual;
    }
}
