/**
 * 
 */
package com.asopagos.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.transversal.core.ResultadoHallazgoValidacionArchivo;
import com.asopagos.enumeraciones.core.TipoProcesoMasivoEnum;

/**
 * <b>Descripción:</b> DTO transportar los errores de los datos de múltiple afiliación
 * trabajador candidato <b>Historia de
 * Usuario:</b> 122-360,122-364
 *
 * @author Juan Diego Ocampo Q<jocampo@heinsohn.com.co>
 */
@XmlRootElement
public class ResultadoHallazgosValidacionArchivoDTO implements Serializable {

    /**
     * SERIAL
     */
    private static final long serialVersionUID = 6379204003755500411L;

    private Long item=0L;
    /**
     * Código identificador de llave primaria del log de cargue masivo
     */
    private Long idResultadoHallazgoValidacionArchivo;

    /**
     * Codigo identificador de la consola de estado cargue masivo
     */
    private Long idConsolaEstadoCargueMasivo;

    /**
     * Número de linea que genera el error
     */
    private Long numeroLinea;

    /**
     * Nombre del campo
     */
    private String nombreCampo;

    /**
     * Error presentado
     */
    private String error;

    /**
     * Descripción de tipo de proceso masivo
     */
    private TipoProcesoMasivoEnum tipoProceso;

    /**
     * Nombrel del archivo cargado
     */
    private String nombreArchivo;

    private String numeroDocumento;

    private String tipoDocumento;

    /**
     * Constructor por defecto
     */
    public ResultadoHallazgosValidacionArchivoDTO() {
        super();
    }

    
    
    /**
     * Constructor de hallazgo sin datos de BD
     * @param error
     *        Mensaje de error de validación
     * @param nombreCampo
     *        Nombre del campo
     * @param numeroLinea
     *        Numero de la línea de campo
     */
    public ResultadoHallazgosValidacionArchivoDTO(String error, String nombreCampo, Long numeroLinea) {
        super();
        setError(error);
        setNombreCampo(nombreCampo);
        setNumeroLinea(numeroLinea);
    }

    /**
     * Constructor usado en consulta
     * @param idResultadoHallazgoValidacionArchivo
     *        Identificador del hallazgo
     * @param idConsolaEstadoCargueMasivo
     *        Identificador del registro en consola
     * @param numeroLinea
     *        Numero de la linea
     * @param nombreCampo
     *        Nombre del campo
     * @param error
     *        Mensaje de error
     * @param tipoProceso
     *        Tipo de archivo
     * @param nombreArchivo
     *        Nombre del archivo
     */
    public ResultadoHallazgosValidacionArchivoDTO(Long idResultadoHallazgoValidacionArchivo, Long idConsolaEstadoCargueMasivo,
            Long numeroLinea, String nombreCampo, String error, String tipoProceso, String nombreArchivo) {
        super();
        this.idResultadoHallazgoValidacionArchivo = idResultadoHallazgoValidacionArchivo;
        this.idConsolaEstadoCargueMasivo = idConsolaEstadoCargueMasivo;
        this.numeroLinea = numeroLinea;
        this.nombreCampo = nombreCampo;
        this.error = error;
        if (tipoProceso != null) {
            this.tipoProceso = TipoProcesoMasivoEnum.valueOf(tipoProceso);
        }
        this.nombreArchivo = nombreArchivo;
    }

    public ResultadoHallazgosValidacionArchivoDTO(Long idResultadoHallazgoValidacionArchivo, Long idConsolaEstadoCargueMasivo,
            Long numeroLinea, String nombreCampo, String error, String tipoProceso, String nombreArchivo,String numeroDocumento,String tipoDocumento) {
        super();
        this.idResultadoHallazgoValidacionArchivo = idResultadoHallazgoValidacionArchivo;
        this.idConsolaEstadoCargueMasivo = idConsolaEstadoCargueMasivo;
        this.numeroLinea = numeroLinea;
        this.nombreCampo = nombreCampo;
        this.error = error;
        if (tipoProceso != null) {
            this.tipoProceso = TipoProcesoMasivoEnum.valueOf(tipoProceso);
        }
        this.nombreArchivo = nombreArchivo;
        this.numeroDocumento = numeroDocumento;
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * Convierte la información de la entidad al DTO
     * @param resultadoHallazgoValidacionArchivo
     *        Entidad de resultado hallazgo
     */
    public void convertToDTO(ResultadoHallazgoValidacionArchivo resultadoHallazgoValidacionArchivo) {
        this.setError(resultadoHallazgoValidacionArchivo.getError());
        this.setIdConsolaEstadoCargueMasivo(resultadoHallazgoValidacionArchivo.getIdConsolaEstadoCargueMasivo());
        this.setIdResultadoHallazgoValidacionArchivo(resultadoHallazgoValidacionArchivo.getIdResultadoHallazgoValidacionArchivo());
        this.setNombreCampo(resultadoHallazgoValidacionArchivo.getNombreCampo());
        this.setNumeroLinea(resultadoHallazgoValidacionArchivo.getNumeroLinea());
    }

    /**
     * Convierte el DTO a entidad para el manejo de persitencia
     * @return Entidad resultado hallazgo validacion archivo
     */
    public ResultadoHallazgoValidacionArchivo convertToEntity() {
        ResultadoHallazgoValidacionArchivo resultadoHallazgoValidacionArchivo = new ResultadoHallazgoValidacionArchivo();
        resultadoHallazgoValidacionArchivo.setError(this.getError());
        resultadoHallazgoValidacionArchivo.setIdConsolaEstadoCargueMasivo(this.getIdConsolaEstadoCargueMasivo());
        resultadoHallazgoValidacionArchivo.setIdResultadoHallazgoValidacionArchivo(this.getIdConsolaEstadoCargueMasivo());
        resultadoHallazgoValidacionArchivo.setNombreCampo(this.getNombreCampo());
        resultadoHallazgoValidacionArchivo.setNumeroLinea(this.getNumeroLinea());
        return resultadoHallazgoValidacionArchivo;
    }
    public void limpiarNulos() {
        if (this.idResultadoHallazgoValidacionArchivo == null) this.idResultadoHallazgoValidacionArchivo = 0L;
        if (this.idConsolaEstadoCargueMasivo == null) this.idConsolaEstadoCargueMasivo = 0L;
        if (this.numeroLinea == null) this.numeroLinea = 0L;
        if (this.nombreCampo == null) this.nombreCampo = "";
        if (this.error == null) this.error = "";
        if (this.nombreArchivo == null) this.nombreArchivo = "";
        if(this.numeroDocumento == null) this.numeroDocumento = "";
        if(this.tipoDocumento == null) this.tipoDocumento = "";
    }

    public String[] toListString() {
        this.limpiarNulos();
        String[] data = new String[] {
                this.getItem().toString(),
                (this.getTipoProceso()==null || this.getTipoProceso().getDescripcion()==null?"":this.getTipoProceso().getDescripcion()),
                this.getNombreArchivo(),
                this.getNumeroLinea()==0L ? "": this.getNumeroLinea().toString(),
                this.getNombreCampo(),
                this.getError()
        };
        return data;
    }

    public Long getNumeroLinea() {
        return numeroLinea;
    }

    /**
     * @param numeroLinea
     *        the numeroLinea to set
     */
    public void setNumeroLinea(Long numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    /**
     * @return the nombreCampo
     */
    public String getNombreCampo() {
        return nombreCampo;
    }

    /**
     * @param nombreCampo
     *        the nombreCampo to set
     */
    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error
     *        the error to set
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * @return the idResultadoHallazgoValidacionArchivo
     */
    public Long getIdResultadoHallazgoValidacionArchivo() {
        return idResultadoHallazgoValidacionArchivo;
    }

    /**
     * @param idResultadoHallazgoValidacionArchivo
     *        the idResultadoHallazgoValidacionArchivo to set
     */
    public void setIdResultadoHallazgoValidacionArchivo(Long idResultadoHallazgoValidacionArchivo) {
        this.idResultadoHallazgoValidacionArchivo = idResultadoHallazgoValidacionArchivo;
    }

    /**
     * @return the idConsolaEstadoCargueMasivo
     */
    public Long getIdConsolaEstadoCargueMasivo() {
        return idConsolaEstadoCargueMasivo;
    }

    /**
     * @param idConsolaEstadoCargueMasivo
     *        the idConsolaEstadoCargueMasivo to set
     */
    public void setIdConsolaEstadoCargueMasivo(Long idConsolaEstadoCargueMasivo) {
        this.idConsolaEstadoCargueMasivo = idConsolaEstadoCargueMasivo;
    }

    /**
     * @return the tipoProceso
     */
    public TipoProcesoMasivoEnum getTipoProceso() {
        return tipoProceso;
    }

    /**
     * @param tipoProceso
     *        the tipoProceso to set
     */
    public void setTipoProceso(TipoProcesoMasivoEnum tipoProceso) {
        this.tipoProceso = tipoProceso;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo
     *        the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Long getItem() {
        return item;
    }

    public void setItem(Long item) {
        this.item = item;
    }

    public String getNumeroDocumento() {
        return this.numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getTipoDocumento() {
        return this.tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
}
