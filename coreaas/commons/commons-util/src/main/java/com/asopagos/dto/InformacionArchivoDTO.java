package com.asopagos.dto;

import java.io.Serializable;
import javax.ws.rs.FormParam;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.archivos.TipoPropietarioArchivoEnum;

/**
 * Clase que representa los metadatos de los archivos a cargar en el ECM
 * @author Leonardo Giral <a href="mailto:ogiral@heinsohn.com.co"></a>
 * @author Ricardo Hernandez Cediel <a href="mailto:hhernandez@heinsohn.com.co"> </a>
 */
public class InformacionArchivoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
    /**
     * Es el nombre del proceso en el que se carga el archivo.
     */
    @FormParam("processName")
    private String processName;
    /**
     * Es la instancia del proceso en la que se genera el archivo
     */
    @FormParam("idInstanciaProceso")
    private String idInstanciaProceso;
    /**
     * Es el nombre del archivo a cargar
     */
    @FormParam("docName")
    private String docName;
    /**
     * Descripción a adicionarse en el ECM como parte de la carga de este archivo
     */
    @FormParam("description")
    private String description;
    /**
     * Tipo de identificación del propietario del archivo
     */
    @FormParam("tipoIdentificacionPropietario")
    private TipoIdentificacionEnum tipoIdentificacionPropietario;
    /**
     * Número de identificación del propietario del archivo
     */
    @FormParam("numeroIdentificacionPropietario")
    private String numeroIdentificacionPropietario;
    /**
     * Id de la persona propietario del archivo
     */
    @FormParam("tipoPropietario")
    private TipoPropietarioArchivoEnum tipoPropietario;
    /**
     * Id del requisito documental
     */
    @FormParam("idRequisito")
    private Long idRequisito;
    /**
     * Id de la solicitud global
     */
    @FormParam("idSolicitud")
    private Long idSolicitud;
    
    /**
     * Id de la Persona para escaneo masivo
     */
    private Long idPersona;
   
	/**
     * Id de la solicitud global
     */
    @FormParam("metadata")
    private String jsonMetadata;
    /**
     * Se utiliza oara indicar que son peticiones desde el frontEnd Angular.
     */
    @FormParam("isFront")
    private boolean front;
    /**
     * Es el nombre del archivo
     */
    @FormParam("fileName")
    private String fileName;
    /**
     * Es el tipo del archivo que se está cargando
     */
    @FormParam("fileType")
    private String fileType;
    /**
     * Representa la información del archivo cargado
     */
    @FormParam("file")
    private byte[] dataFile;

    /** Identificador de la familia de un documento en el ECM */
    private String identificadorDocumento;

    /** versión del documento en el ECM */
    private String versionDocumento;
    
    /** Tamaño del archivo */
    private Long size;
    
    /** ruta del archivo */
    private String pathFile;
    
    /** time de la fecha de modificación */
    private Long fechaModificacion;
    
    /** Indica si el archivo es procedente para clasificar (asociarlo a un requisito documental) */
    private boolean clasificable;
    
    /** ruta del archivo */
    private String tipoCarga;

    @FormParam("numeroRadicado")
    private String numeroRadicado;
    
    @Override
    public String toString()
    {
      return ToStringBuilder.reflectionToString(this);
    }
    
	/**
     * @return the processName
     */
    public String getProcessName() {
        return processName;
    }

    /**
     * @param processName
     *        the processName to set
     */
    public void setProcessName(String processName) {
        this.processName = processName;
    }

    /**
     * @return the idInstanciaProceso
     */
    public String getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    /**
     * @param idInstanciaProceso
     *        the idInstanciaProceso to set
     */
    public void setIdInstanciaProceso(String idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }

    /**
     * @return the docName
     */
    public String getDocName() {
        return docName;
    }

    /**
     * @param docName
     *        the docName to set
     */
    public void setDocName(String docName) {
        this.docName = docName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *        the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the front
     */
    public boolean isFront() {
        return front;
    }
    /**
     * @param description
     *        the description to set
     */
    public void setIsFront(boolean isFront) {
        this.front = isFront;
    }
    /**
     * @param front
     *        the front to set
     */
    public void setFront(boolean front) {
        this.front = front;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     *        the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType
     *        the fileType to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * @return the dataFile
     */
    public byte[] getDataFile() {
        return dataFile;
    }

    /**
     * @param dataFile
     *        the dataFile to set
     */
    public void setDataFile(byte[] dataFile) {
        this.dataFile = dataFile;
    }

    /**
     * @return the identificadorDocumento
     */
    public String getIdentificadorDocumento() {
        return identificadorDocumento;
    }

    /**
     * @param identificadorDocumento
     *        the identificadorDocumento to set
     */
    public void setIdentificadorDocumento(String identificadorDocumento) {
        this.identificadorDocumento = identificadorDocumento;
    }

    /**
     * @return the versionDocumento
     */
    public String getVersionDocumento() {
        return versionDocumento;
    }

    /**
     * @param versionDocumento
     *        the versionDocumento to set
     */
    public void setVersionDocumento(String versionDocumento) {
        this.versionDocumento = versionDocumento;
    }
    
    /**
     * @return the size
     */
    public Long getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Long size) {
        this.size = size;
    }
    
    /**
     * @return the pathFile
     */
    public String getPathFile() {
        return pathFile;
    }

    /**
     * @param pathFile
     *        the pathFile to set
     */
    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }
    
    /**
     * @return the fechaModificacion
     */
    public Long getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     * @param fechaModificacion
     *        the fechaModificacion to set
     */
    public void setFechaModificacion(Long fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    /**
     * @return the tipoPropietario
     */
    public TipoPropietarioArchivoEnum getTipoPropietario() {
        return tipoPropietario;
    }

    /**
     * @param tipoPropietario the tipoPropietario to set
     */
    public void setTipoPropietario(TipoPropietarioArchivoEnum tipoPropietario) {
        this.tipoPropietario = tipoPropietario;
    }

    /**
     * @return the tipoIdentificacionPropietario
     */
    public TipoIdentificacionEnum getTipoIdentificacionPropietario() {
        return tipoIdentificacionPropietario;
    }

    /**
     * @param tipoIdentificacionPropietario the tipoIdentificacionPropietario to set
     */
    public void setTipoIdentificacionPropietario(TipoIdentificacionEnum tipoIdentificacionPropietario) {
        this.tipoIdentificacionPropietario = tipoIdentificacionPropietario;
    }

    /**
     * @return the numeroIdentificacionPropietario
     */
    public String getNumeroIdentificacionPropietario() {
        return numeroIdentificacionPropietario;
    }

    /**
     * @param numeroIdentificacionPropietario the numeroIdentificacionPropietario to set
     */
    public void setNumeroIdentificacionPropietario(String numeroIdentificacionPropietario) {
        this.numeroIdentificacionPropietario = numeroIdentificacionPropietario;
    }

    /**
     * @return the idRequisito
     */
    public Long getIdRequisito() {
        return idRequisito;
    }

    /**
     * @param idRequisito the idRequisito to set
     */
    public void setIdRequisito(Long idRequisito) {
        this.idRequisito = idRequisito;
    }

    /**
     * @return the idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud the idSolicitud to set
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
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
     * @return the jsonMetadata
     */
    public String getJsonMetadata() {
        return jsonMetadata;
    }

    /**
     * @param jsonMetadata the jsonMetadata to set
     */
    public void setJsonMetadata(String jsonMetadata) {
        this.jsonMetadata = jsonMetadata;
    }
 
    /**
     * @return the clasificable
     */
    public boolean isClasificable() {
		return clasificable;
	}

    /**
     * @param clasificable the clasificable to set
     */
	public void setClasificable(boolean clasificable) {
		this.clasificable = clasificable;
	}
	
	public String getTipoCarga() {
		return tipoCarga;
	}

	public void setTipoCarga(String tipoCarga) {
		this.tipoCarga = tipoCarga;
	}

    public boolean getFront() {
        return this.front;
    }


    public boolean getClasificable() {
        return this.clasificable;
    }


    public String getNumeroRadicado() {
        return this.numeroRadicado;
    }

    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }
}