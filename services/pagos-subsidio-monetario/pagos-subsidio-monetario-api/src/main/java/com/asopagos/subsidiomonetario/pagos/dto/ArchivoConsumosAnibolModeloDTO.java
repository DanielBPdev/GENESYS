package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.subsidiomonetario.pagos.anibol.ArchivoConsumosAnibol;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoArchivoConsumoAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.ResultadoValidacionArchivoConsumoAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.TipoCargueArchivoConsumoAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.TipoInconsistenciaArchivoConsumoAnibolEnum;

/**
 * <b>Descripcion:</b> Clase DTO que representa el modelo del ArchivoConsumosAnibol <br/>
 * <b>Módulo:</b> Asopagos - HU - ANEXO Validacion y carga de archivos de consumo Anibol<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

public class ArchivoConsumosAnibolModeloDTO implements Serializable {

    private static final long serialVersionUID = 8635162127182409212L;

    /**
     * Identificador asociado al archivo de consumos
     */
    private Long idArchivoConsumosSubsidioMonetario;

    /**
     * Indica el nombre del archivo de consumos alojado en el ECM Alfresco
     */
    private String nombreArchivo;

    /**
     * Indica el Identificador del documento alojado en el ECM Alfresco
     */
    private String idDocumento;

    /**
     * Indica la version del documento alojado en ECM Alfresco
     */
    private String versionDocumento;

    /**
     * Indica la Fecha y hora de cargue del archivo de consumos
     */
    private Date fechaHoraCargue;

    /**
     * Indica el usuario que realizo el cargue (Automatico: GENESYS)
     */
    private String usuarioCargue;

    /**
     * Indica la fecha y hora de procesamiento del archivo de consumos
     */
    private Date fechaHoraProcesamiento;

    /**
     * Indica el usuario que realizo el procesamiento del archivo de consumos
     */
    private String usuarioProcesamiento;

    /**
     * Indica el tipo de cargue del archivo de consumos (AMNUAL o AUTOMATICO)
     */
    private TipoCargueArchivoConsumoAnibolEnum tipoCargue;

    /**
     * Indica el estado del archivo de consumos
     */
    private EstadoArchivoConsumoAnibolEnum estadoArchivo;

    /**
     * Indica el resultado de la validacion de estructura del archivo de consumos
     */
    private ResultadoValidacionArchivoConsumoAnibolEnum resultadoValidacionEstructura;

    /**
     * Indica el resultado de validacion de contenido del archivo de consumos
     */
    private ResultadoValidacionArchivoConsumoAnibolEnum resultadoValidacionContenido;

    /**
     * Indica la descripcion de la inconsistencia del archivo de consumos
     */
    private TipoInconsistenciaArchivoConsumoAnibolEnum tipoInconsistenciaArchivo;

    /**
     * Indica si el archivo de consumos en caso de presentar inconsistencias es
     * notificado 1=[Si] o 0: [No]
     */
    private byte archivoNotificado;
    
    /**
     * Constructor vacio que instancia la clase
     * con la fecha de cargue por defecto y el usuario de cargue (Genesys)
     */
    public ArchivoConsumosAnibolModeloDTO(){
        setFechaHoraCargue(new Date());
        setUsuarioCargue("Genesys");
    }
    
    /**
     * Método que convierte el DTO en Entity
     * @return entidad que representa el DTO.
     */
    public ArchivoConsumosAnibol convertToEntity() {
        ArchivoConsumosAnibol archivoConsumo = new ArchivoConsumosAnibol();
        archivoConsumo.setIdArchivoConsumosSubsidioMonetario(getIdArchivoConsumosSubsidioMonetario());
        archivoConsumo.setNombreArchivo(getNombreArchivo());
        archivoConsumo.setIdDocumento(getIdDocumento());
        archivoConsumo.setVersionDocumento(getVersionDocumento());
        archivoConsumo.setFechaHoraCargue(getFechaHoraCargue());
        archivoConsumo.setUsuarioCargue(getUsuarioCargue());
        archivoConsumo.setFechaHoraProcesamiento(getFechaHoraProcesamiento());
        archivoConsumo.setUsuarioProcesamiento(getUsuarioProcesamiento());
        archivoConsumo.setTipoCargue(getTipoCargue());
        archivoConsumo.setEstadoArchivo(getEstadoArchivo());
        archivoConsumo.setResultadoValidacionEstructura(getResultadoValidacionEstructura());
        archivoConsumo.setResultadoValidacionContenido(getResultadoValidacionContenido());
        archivoConsumo.setTipoInconsistenciaArchivo(getTipoInconsistenciaArchivo());
        archivoConsumo.setArchivoNotificado(getArchivoNotificado());
        return archivoConsumo;
    }

    /**
     * @return the idArchivoConsumosSubsidioMonetario
     */
    public Long getIdArchivoConsumosSubsidioMonetario() {
        return idArchivoConsumosSubsidioMonetario;
    }

    /**
     * @param idArchivoConsumosSubsidioMonetario
     *        the idArchivoConsumosSubsidioMonetario to set
     */
    public void setIdArchivoConsumosSubsidioMonetario(Long idArchivoConsumosSubsidioMonetario) {
        this.idArchivoConsumosSubsidioMonetario = idArchivoConsumosSubsidioMonetario;
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

    /**
     * @return the idDocumento
     */
    public String getIdDocumento() {
        return idDocumento;
    }

    /**
     * @param idDocumento
     *        the idDocumento to set
     */
    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
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
     * @return the fechaHoraCargue
     */
    public Date getFechaHoraCargue() {
        return fechaHoraCargue;
    }

    /**
     * @param fechaHoraCargue
     *        the fechaHoraCargue to set
     */
    public void setFechaHoraCargue(Date fechaHoraCargue) {
        this.fechaHoraCargue = fechaHoraCargue;
    }

    /**
     * @return the usuarioCargue
     */
    public String getUsuarioCargue() {
        return usuarioCargue;
    }

    /**
     * @param usuarioCargue
     *        the usuarioCargue to set
     */
    public void setUsuarioCargue(String usuarioCargue) {
        this.usuarioCargue = usuarioCargue;
    }

    /**
     * @return the fechaHoraProcesamiento
     */
    public Date getFechaHoraProcesamiento() {
        return fechaHoraProcesamiento;
    }

    /**
     * @param fechaHoraProcesamiento
     *        the fechaHoraProcesamiento to set
     */
    public void setFechaHoraProcesamiento(Date fechaHoraProcesamiento) {
        this.fechaHoraProcesamiento = fechaHoraProcesamiento;
    }

    /**
     * @return the usuarioProcesamiento
     */
    public String getUsuarioProcesamiento() {
        return usuarioProcesamiento;
    }

    /**
     * @param usuarioProcesamiento
     *        the usuarioProcesamiento to set
     */
    public void setUsuarioProcesamiento(String usuarioProcesamiento) {
        this.usuarioProcesamiento = usuarioProcesamiento;
    }

    /**
     * @return the tipoCargue
     */
    public TipoCargueArchivoConsumoAnibolEnum getTipoCargue() {
        return tipoCargue;
    }

    /**
     * @param tipoCargue
     *        the tipoCargue to set
     */
    public void setTipoCargue(TipoCargueArchivoConsumoAnibolEnum tipoCargue) {
        this.tipoCargue = tipoCargue;
    }

    /**
     * @return the estadoArchivo
     */
    public EstadoArchivoConsumoAnibolEnum getEstadoArchivo() {
        return estadoArchivo;
    }

    /**
     * @param estadoArchivo
     *        the estadoArchivo to set
     */
    public void setEstadoArchivo(EstadoArchivoConsumoAnibolEnum estadoArchivo) {
        this.estadoArchivo = estadoArchivo;
    }

    /**
     * @return the resultadoValidacionEstructura
     */
    public ResultadoValidacionArchivoConsumoAnibolEnum getResultadoValidacionEstructura() {
        return resultadoValidacionEstructura;
    }

    /**
     * @param resultadoValidacionEstructura
     *        the resultadoValidacionEstructura to set
     */
    public void setResultadoValidacionEstructura(ResultadoValidacionArchivoConsumoAnibolEnum resultadoValidacionEstructura) {
        this.resultadoValidacionEstructura = resultadoValidacionEstructura;
    }

    /**
     * @return the resultadoValidacionContenido
     */
    public ResultadoValidacionArchivoConsumoAnibolEnum getResultadoValidacionContenido() {
        return resultadoValidacionContenido;
    }

    /**
     * @param resultadoValidacionContenido
     *        the resultadoValidacionContenido to set
     */
    public void setResultadoValidacionContenido(ResultadoValidacionArchivoConsumoAnibolEnum resultadoValidacionContenido) {
        this.resultadoValidacionContenido = resultadoValidacionContenido;
    }

    /**
     * @return the tipoInconsistenciaArchivo
     */
    public TipoInconsistenciaArchivoConsumoAnibolEnum getTipoInconsistenciaArchivo() {
        return tipoInconsistenciaArchivo;
    }

    /**
     * @param tipoInconsistenciaArchivo
     *        the tipoInconsistenciaArchivo to set
     */
    public void setTipoInconsistenciaArchivo(TipoInconsistenciaArchivoConsumoAnibolEnum tipoInconsistenciaArchivo) {
        this.tipoInconsistenciaArchivo = tipoInconsistenciaArchivo;
    }

    /**
     * @return the archivoNotificado
     */
    public byte getArchivoNotificado() {
        return archivoNotificado;
    }

    /**
     * @param archivoNotificado
     *        the archivoNotificado to set
     */
    public void setArchivoNotificado(byte archivoNotificado) {
        this.archivoNotificado = archivoNotificado;
    }

}
