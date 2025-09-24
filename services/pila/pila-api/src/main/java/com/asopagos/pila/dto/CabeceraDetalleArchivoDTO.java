package com.asopagos.pila.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;

/**
 * <b>Descripcion:</b> DTO que contiene la informacion de la cabecera para el detalle del archivo<br/>
 * <b>Módulo:</b> Asopagos - HU-211-401 <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda </a>
 */
public class CabeceraDetalleArchivoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** Tipo de documento del aportante  */
    private String tipoDocumento;
    
    /** Número de documento del aportante */
    private String numDocumento;
    
    /** Digito de verificación del aportante*/
    private Short digitoVerificacion;
    
    /** Número de la planilla */
    private Long numeroPlanilla;
    
    /** Estado actual del archivo */
    private EstadoProcesoArchivoEnum estadoActual;
    
    /** Nombre del archivo */
    private String nombreArchivo;
    
    /** Tipo de archivo (I, A...) */
    private String tipoArchivo;
    
    /** Enumeracion que contiene el tipo */
    private TipoIdentificacionEnum tipoIdEnum;
    
    /** Lista de los archivos relacionados*/
    private List<String> archivosRelacionados;
    
    /** Identificador del documento */
    private String identificadorDocumento;
    
    /** Version del documento */
    private String versionDocumento;
    
   /** Registor General de procesamiento de planilla */
    private Long idRegistroGeneral;

    /**
     * @param tipoDocumento
     * @param numDocumento
     * @param digitoVerificacion
     * @param numeroPlanilla
     * @param estadoActual
     * @param nombreArchivo
     * @param tipoArchivo
     * @param archivosRelacionados
     */
    public CabeceraDetalleArchivoDTO(String tipoDocumento, String numDocumento, Short digitoVerificacion, Long numeroPlanilla,
            EstadoProcesoArchivoEnum estadoActual, String nombreArchivo, TipoArchivoPilaEnum tipoArchivo, String identificadorDocumento,
            String versionDocumento) {
        this.numDocumento = numDocumento;
        this.digitoVerificacion = digitoVerificacion;
        this.numeroPlanilla = numeroPlanilla;
        this.estadoActual = estadoActual;
        this.nombreArchivo = nombreArchivo;
        this.tipoArchivo = tipoArchivo.name();
        this.identificadorDocumento = identificadorDocumento;
        this.versionDocumento = versionDocumento;
        
        switch(tipoDocumento != null ? tipoDocumento : ""){
            case "NI":
                this.tipoIdEnum = TipoIdentificacionEnum.NIT;
                break;
            case "CC":
                this.tipoIdEnum = TipoIdentificacionEnum.CEDULA_CIUDADANIA;
                break;
            case "RC":
                this.tipoIdEnum = TipoIdentificacionEnum.REGISTRO_CIVIL;
                break;
            case "TI":
                this.tipoIdEnum = TipoIdentificacionEnum.TARJETA_IDENTIDAD;
                break;
            case "CE":
                this.tipoIdEnum = TipoIdentificacionEnum.CEDULA_EXTRANJERIA;
                break;
            case "PA":
                this.tipoIdEnum = TipoIdentificacionEnum.PASAPORTE;
                break;
            case "CD":
                this.tipoIdEnum = TipoIdentificacionEnum.CARNE_DIPLOMATICO;
                break;
            case "SC":
                this.tipoIdEnum = TipoIdentificacionEnum.SALVOCONDUCTO;
                break;
            case "PT":
                this.tipoIdEnum = TipoIdentificacionEnum.PERM_PROT_TEMPORAL;
                break;
            case "PE":
                this.tipoIdEnum = TipoIdentificacionEnum.PERM_ESP_PERMANENCIA;
                break;
            default:
                this.tipoIdEnum = null;
                break;
        }
    }
    
    
    /**
     * @return the tipoDocumento
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }
    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    /**
     * @return the numDocumento
     */
    public String getNumDocumento() {
        return numDocumento;
    }
    /**
     * @param numDocumento the numDocumento to set
     */
    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }
    /**
     * @return the digitoVerificacion
     */
    public Short getDigitoVerificacion() {
        return digitoVerificacion;
    }
    /**
     * @param digitoVerificacion the digitoVerificacion to set
     */
    public void setDigitoVerificacion(Short digitoVerificacion) {
        this.digitoVerificacion = digitoVerificacion;
    }
    /**
     * @return the numeroPlanilla
     */
    public Long getNumeroPlanilla() {
        return numeroPlanilla;
    }
    /**
     * @param numeroPlanilla the numeroPlanilla to set
     */
    public void setNumeroPlanilla(Long numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }
    /**
     * @return the estadoActual
     */
    public EstadoProcesoArchivoEnum getEstadoActual() {
        return estadoActual;
    }
    /**
     * @param estadoActual the estadoActual to set
     */
    public void setEstadoActual(EstadoProcesoArchivoEnum estadoActual) {
        this.estadoActual = estadoActual;
    }
    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }
    /**
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    /**
     * @return the tipoArchivo
     */
    public String getTipoArchivo() {
        return tipoArchivo;
    }
    /**
     * @param tipoArchivo the tipoArchivo to set
     */
    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }
    /**
     * @return the archivosRelacionados
     */
    public List<String> getArchivosRelacionados() {
        return archivosRelacionados;
    }
    /**
     * @param archivosRelacionados the archivosRelacionados to set
     */
    public void setArchivosRelacionados(List<String> archivosRelacionados) {
        this.archivosRelacionados = archivosRelacionados;
    }

    /**
     * @return the tipoIdEnum
     */
    public TipoIdentificacionEnum getTipoIdEnum() {
        return tipoIdEnum;
    }


    /**
     * @param tipoIdEnum the tipoIdEnum to set
     */
    public void setTipoIdEnum(TipoIdentificacionEnum tipoIdEnum) {
        this.tipoIdEnum = tipoIdEnum;
    }


    /**
     * @return the identificadorDocumento
     */
    public String getIdentificadorDocumento() {
        return identificadorDocumento;
    }


    /**
     * @param identificadorDocumento the identificadorDocumento to set
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
     * @param versionDocumento the versionDocumento to set
     */
    public void setVersionDocumento(String versionDocumento) {
        this.versionDocumento = versionDocumento;
    }
    
    public CabeceraDetalleArchivoDTO() {}


    /**
     * @return the idRegistroGeneral
     */
    public Long getIdRegistroGeneral() {
        return idRegistroGeneral;
    }


    /**
     * @param idRegistroGeneral the idRegistroGeneral to set
     */
    public void setIdRegistroGeneral(Long idRegistroGeneral) {
        this.idRegistroGeneral = idRegistroGeneral;
    }
}
