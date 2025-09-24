package com.asopagos.dto.pila;

import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoRegistroAportesArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoValidacionRegistroAporteEnum;
import com.asopagos.enumeraciones.pila.EstadoValidacionRegistroCorreccionEnum;

/**
 * <b>Descripcion:</b> DTO que presenta los resultados de validación de PILA 2 <br/>
 * <b>Módulo:</b> Asopagos - HU-211-389 y HU-211-410 <br/>
 *
 * @author <a href="mailto:anbuitrago@heinsohn.com.co"> anbuitrago</a>
 * @author <a href="mailto:anbuitrago@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
@XmlRootElement
public class DetalleTablaAportanteDTO {

    private String idCotizante;
    private TipoIdentificacionEnum tipoIdCotizante;
    private Long secuencia;
    private String idPlanilla;
    private String tipoArchivo;
    private Date fechaProcesamiento;
    private BigDecimal aporteObligatorio;
    private EstadoValidacionRegistroAporteEnum v0;
    private EstadoValidacionRegistroAporteEnum v1;
    private EstadoValidacionRegistroAporteEnum v2;
    private EstadoValidacionRegistroAporteEnum v3;
    private String tipoCotizante;
    private Long indicePlanilla;
    private String estadoRegistro;
    private String estadoAportante;
    
    private Long idRegistroGeneral;
    private Long idRegistroDetallado;
    private EstadoRegistroAportesArchivoEnum outEstadoRegistroAporte;
    private String primerApellido;
    private String primerNombre;
    private String segundoApellido;
    private String segundoNombre;
    

    /** Estado del registro frente a correciones o adiciones "Evaluación vs BD" */
    private EstadoAporteEnum evaluacionVsBd;

    /** Valor del campo "Correciones" */
    private String correcciones;

    /** Estado de validación de registros de corrección */
    private EstadoValidacionRegistroCorreccionEnum estadoValidacionCorreccion;

    /** Constructor por defecto para JSON */
    public DetalleTablaAportanteDTO() {
    }

    /**
     * Constructor para NamedQuery <code>PilaService.RegistroDetallado.ConsultarEstadoDetalladoPlanilla</code>
     * 
     * @param idCotizante
     * @param tipoIdCotizante
     * @param secuencia
     * @param evaluacionVsBd
     * @param aporteObligatorio
     * @param v0
     * @param v1
     * @param v2
     * @param v3
     * @param tipoCotizante
     */
    public DetalleTablaAportanteDTO(String idCotizante, TipoIdentificacionEnum tipoIdCotizante, Long secuencia,
            EstadoAporteEnum evaluacionVsBd, BigDecimal aporteObligatorio, EstadoValidacionRegistroAporteEnum v0,
            EstadoValidacionRegistroAporteEnum v1, EstadoValidacionRegistroAporteEnum v2, EstadoValidacionRegistroAporteEnum v3,
            String tipoCotizante, String correcciones) {
        this.idCotizante = idCotizante;
        this.tipoIdCotizante = tipoIdCotizante;
        this.secuencia = secuencia;
        this.evaluacionVsBd = evaluacionVsBd;
        if (aporteObligatorio != null) {
            this.aporteObligatorio = aporteObligatorio;
        }
        else {
            this.aporteObligatorio = new BigDecimal(0);
        }
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.tipoCotizante = tipoCotizante;
        this.correcciones = correcciones;
    }
    
    /**
     * Constructor para NamedQuery <code>PilaService.RegistroDetallado.ConsultarEstadoDetalladoPlanilla</code>
     * 
     * @param idCotizante
     * @param tipoIdCotizante
     * @param secuencia
     * @param evaluacionVsBd
     * @param aporteObligatorio
     * @param v0
     * @param v1
     * @param v2
     * @param v3
     * @param tipoCotizante
     * @param idRegistroGeneral
     * @param idRegistroDetallado
     */
    public DetalleTablaAportanteDTO(String idCotizante, TipoIdentificacionEnum tipoIdCotizante, Long secuencia,
            EstadoAporteEnum evaluacionVsBd, BigDecimal aporteObligatorio, EstadoValidacionRegistroAporteEnum v0,
            EstadoValidacionRegistroAporteEnum v1, EstadoValidacionRegistroAporteEnum v2, EstadoValidacionRegistroAporteEnum v3,
            String tipoCotizante, String correcciones,
            Long idRegistroGeneral, Long idRegistroDetallado, EstadoRegistroAportesArchivoEnum outEstadoRegistroAporte,
            String primerApellido, String primerNombre, String segundoApellido, String segundoNombre) {
        
        this.idCotizante = idCotizante;
        this.tipoIdCotizante = tipoIdCotizante;
        this.secuencia = secuencia;
        this.evaluacionVsBd = evaluacionVsBd;
        if (aporteObligatorio != null) {
            this.aporteObligatorio = aporteObligatorio;
        }
        else {
            this.aporteObligatorio = BigDecimal.ZERO;
        }
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.tipoCotizante = tipoCotizante;
        this.correcciones = correcciones;
        this.idRegistroGeneral = idRegistroGeneral;
        this.idRegistroDetallado = idRegistroDetallado;
        this.outEstadoRegistroAporte = outEstadoRegistroAporte;
        this.primerApellido = primerApellido;
        this.primerNombre = primerNombre;
        this.segundoApellido = segundoApellido;
        this.segundoNombre = segundoNombre;
        
    }

    /**
     * @return the idCotizante
     */
    public String getIdCotizante() {
        return idCotizante;
    }

    /**
     * @param idCotizante
     *        the idCotizante to set
     */
    public void setIdCotizante(String idCotizante) {
        this.idCotizante = idCotizante;
    }

    /**
     * @return the secuencia
     */
    public Long getSecuencia() {
        return secuencia;
    }

    /**
     * @param secuencia
     *        the secuencia to set
     */
    public void setSecuencia(Long secuencia) {
        this.secuencia = secuencia;
    }

    /**
     * @return the idPlanilla
     */
    public String getIdPlanilla() {
        return idPlanilla;
    }

    /**
     * @param idPlanilla
     *        the idPlanilla to set
     */
    public void setIdPlanilla(String idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    /**
     * @return the tipoArchivo
     */
    public String getTipoArchivo() {
        return tipoArchivo;
    }

    /**
     * @param tipoArchivo
     *        the tipoArchivo to set
     */
    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    /**
     * @return the fechaProcesamiento
     */
    public Date getFechaProcesamiento() {
        return fechaProcesamiento;
    }

    /**
     * @param fechaProcesamiento
     *        the fechaProcesamiento to set
     */
    public void setFechaProcesamiento(Date fechaProcesamiento) {
        this.fechaProcesamiento = fechaProcesamiento;
    }

    /**
     * @return the aporteObligatorio
     */
    public BigDecimal getAporteObligatorio() {
        return aporteObligatorio;
    }

    /**
     * @param aporteObligatorio
     *        the aporteObligatorio to set
     */
    public void setAporteObligatorio(BigDecimal aporteObligatorio) {
        this.aporteObligatorio = aporteObligatorio;
    }

    /**
     * @return the v0
     */
    public EstadoValidacionRegistroAporteEnum getV0() {
        return v0;
    }

    /**
     * @param v0
     *        the v0 to set
     */
    public void setV0(EstadoValidacionRegistroAporteEnum v0) {
        this.v0 = v0;
    }

    /**
     * @return the v1
     */
    public EstadoValidacionRegistroAporteEnum getV1() {
        return v1;
    }

    /**
     * @param v1
     *        the v1 to set
     */
    public void setV1(EstadoValidacionRegistroAporteEnum v1) {
        this.v1 = v1;
    }

    /**
     * @return the v2
     */
    public EstadoValidacionRegistroAporteEnum getV2() {
        return v2;
    }

    /**
     * @param v2
     *        the v2 to set
     */
    public void setV2(EstadoValidacionRegistroAporteEnum v2) {
        this.v2 = v2;
    }

    /**
     * @return the v3
     */
    public EstadoValidacionRegistroAporteEnum getV3() {
        return v3;
    }

    /**
     * @param v3
     *        the v3 to set
     */
    public void setV3(EstadoValidacionRegistroAporteEnum v3) {
        this.v3 = v3;
    }

    /**
     * @return the tipoCotizante
     */
    public String getTipoCotizante() {
        return tipoCotizante;
    }

    /**
     * @param tipoCotizante
     *        the tipoCotizante to set
     */
    public void setTipoCotizante(String tipoCotizante) {
        this.tipoCotizante = tipoCotizante;
    }

    /**
     * @return the tipoIdCotizante
     */
    public TipoIdentificacionEnum getTipoIdCotizante() {
        return tipoIdCotizante;
    }

    /**
     * @param tipoIdCotizante
     *        the tipoIdCotizante to set
     */
    public void setTipoIdCotizante(TipoIdentificacionEnum tipoIdCotizante) {
        this.tipoIdCotizante = tipoIdCotizante;
    }

    /**
     * @return the indicePlanilla
     */
    public Long getIndicePlanilla() {
        return indicePlanilla;
    }

    /**
     * @param indicePlanilla
     *        the indicePlanilla to set
     */
    public void setIndicePlanilla(Long indicePlanilla) {
        this.indicePlanilla = indicePlanilla;
    }

    /**
     * @return the estadoRegistro
     */
    public String getEstadoRegistro() {
        return estadoRegistro;
    }

    /**
     * @param estadoRegistro
     *        the estadoRegistro to set
     */
    public void setEstadoRegistro(String estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    /**
     * @return the evaluacionVsBd
     */
    public EstadoAporteEnum getEvaluacionVsBd() {
        return evaluacionVsBd;
    }

    /**
     * @param evaluacionVsBd
     *        the evaluacionVsBd to set
     */
    public void setEvaluacionVsBd(EstadoAporteEnum evaluacionVsBd) {
        this.evaluacionVsBd = evaluacionVsBd;
    }

    /**
     * @return the correcciones
     */
    public String getCorrecciones() {
        return correcciones;
    }

    /**
     * @param correcciones
     *        the correcciones to set
     */
    public void setCorrecciones(String correcciones) {
        this.correcciones = correcciones;
    }

    /**
     * @return the estadoValidacionCorreccion
     */
    public EstadoValidacionRegistroCorreccionEnum getEstadoValidacionCorreccion() {
        return estadoValidacionCorreccion;
    }

    /**
     * @param estadoValidacionCorreccion
     *        the estadoValidacionCorreccion to set
     */
    public void setEstadoValidacionCorreccion(EstadoValidacionRegistroCorreccionEnum estadoValidacionCorreccion) {
        this.estadoValidacionCorreccion = estadoValidacionCorreccion;
    }

    /**
     * @return the estadoAportante
     */
    public String getEstadoAportante() {
        return estadoAportante;
    }

    /**
     * @param estadoAportante the estadoAportante to set
     */
    public void setEstadoAportante(String estadoAportante) {
        this.estadoAportante = estadoAportante;
    }

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

    /**
     * @return the idRegistroDetallado
     */
    public Long getIdRegistroDetallado() {
        return idRegistroDetallado;
    }

    /**
     * @param idRegistroDetallado the idRegistroDetallado to set
     */
    public void setIdRegistroDetallado(Long idRegistroDetallado) {
        this.idRegistroDetallado = idRegistroDetallado;
    }

    /**
     * @return the outEstadoRegistroAporte
     */
    public EstadoRegistroAportesArchivoEnum getOutEstadoRegistroAporte() {
        return outEstadoRegistroAporte;
    }

    /**
     * @param outEstadoRegistroAporte the outEstadoRegistroAporte to set
     */
    public void setOutEstadoRegistroAporte(EstadoRegistroAportesArchivoEnum outEstadoRegistroAporte) {
        this.outEstadoRegistroAporte = outEstadoRegistroAporte;
    }

    /**
     * @return the primerApellido
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * @param primerApellido the primerApellido to set
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * @return the primerNombre
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * @param primerNombre the primerNombre to set
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * @return the segundoApellido
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * @param segundoApellido the segundoApellido to set
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * @return the segundoNombre
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * @param segundoNombre the segundoNombre to set
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

}
