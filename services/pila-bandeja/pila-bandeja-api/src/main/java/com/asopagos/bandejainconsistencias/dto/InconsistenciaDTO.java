package com.asopagos.bandejainconsistencias.dto;

import java.math.BigDecimal;
import java.util.Date;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.enumeraciones.aportes.TipoOperadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.AccionCorreccionPilaEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoGestionInconsistenciaEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.RazonRechazoSolicitudCambioIdenEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;

/**
 * 
 * <b>Descripcion:</b> DTO que posee los datos del manejo de la HU-392 <br/>
 * <b>Módulo:</b> Asopagos - HU 392<br/>
 *
 * @author <a href="mailto:anbuitrago@heinsohn.com.co"> anbuitrago</a>
 */
public class InconsistenciaDTO {

    private Long idErrorValidacion;
    private Date fechaProcesamiento;
    private Long numeroPlanilla;
    private String NumeroIdAportante;
    private String nombreArchivo;
    private String nombreArchivoPar;
    private TipoArchivoPilaEnum tipoArchivo;
    private EstadoProcesoArchivoEnum estadoArchivo;
    private Short numeroLinea;
    private BloqueValidacionEnum bloque;
    private String campo;
    private Short posInicio;
    private Short PosFinal;
    private String valorCampoInformacion;
    private String mensaje;
    private TipoErrorValidacionEnum tipoError;
    private TipoOperadorEnum tipoOperador;
    private String numeroPlanillaO;
    private Long cantidadErrores;
    private String codigoError;
    private String descripcionInconsistencia;
    private String valorCampoFinanciero;
    // este identificador es unico para este DTO puede ser de un indice de
    // planilla financiera o de informacion
    private Long indicePlanilla;
    private Long indicePlanillaPar;
    private String numeroReprocesos;
    private EstadoGestionInconsistenciaEnum estadoGestion;
    private RazonRechazoSolicitudCambioIdenEnum razonRechazo;
    private String comentarios;
    private AccionCorreccionPilaEnum accionCorreccion;
    private String nombreArchivoOF;
    private BigDecimal totalRecaudoOF;
    private Boolean registroProcesado;
    // Razon social o nombre del aportante
    private String razonSocialAportante;
    // indicador de aporte propio
    private Boolean esAportePropio;

    /** Representacion en String del numero para evitar la notacion cientifica */
    private String totalRecaudoOFStr;

    /** Tipo de identificacion del aportante */
    private TipoIdentificacionEnum tipoIdentificacion;

    /** Cantidad de registros 6 del archivo F */
    private Long cantidadRegistros6ArchivoF;
    
    /** Persona asociada al cambio de nombre */
    private PersonaModeloDTO nombresNuevoAportante;

    /**
     * @return the estadoGestion
     */
    public EstadoGestionInconsistenciaEnum getEstadoGestion() {
        return estadoGestion;
    }

    /**
     * @param estadoGestion
     *        the estadoGestion to set
     */
    public void setEstadoGestion(EstadoGestionInconsistenciaEnum estadoGestion) {
        this.estadoGestion = estadoGestion;
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
     * @return the valorCampoFinanciero
     */
    public String getValorCampoFinanciero() {
        return valorCampoFinanciero;
    }

    /**
     * @param valorCampoFinanciero
     *        the valorCampoFinanciero to set
     */
    public void setValorCampoFinanciero(String valorCampoFinanciero) {
        this.valorCampoFinanciero = valorCampoFinanciero;
    }

    /**
     * Constructor para la pantalla de busqueda HU392
     * @param fechaProcesamiento
     * @param numeroPlanilla
     * @param nombreArchivo
     * @param tipoArchivo
     * @param cantidadErrores
     * @param estadoArchivo
     * @param indicePlanilla
     */
    public InconsistenciaDTO(Date fechaProcesamiento, Long numeroPlanilla, String nombreArchivo, TipoArchivoPilaEnum tipoArchivo,
            Long cantidadErrores, EstadoProcesoArchivoEnum estadoArchivo, Long indicePlanilla) {
        // , TipoOperadorEnum tipoOperador
        this.fechaProcesamiento = fechaProcesamiento;
        this.numeroPlanilla = numeroPlanilla;
        this.nombreArchivo = nombreArchivo;
        this.tipoArchivo = tipoArchivo;
        this.cantidadErrores = cantidadErrores;
        this.estadoArchivo = estadoArchivo;
        this.indicePlanilla = indicePlanilla;
        this.registroProcesado = false;
    }

    /**
     * Constructor para la pantalla de busqueda HU392 - Incluye archivo de OF y total de recaudo OF
     * 
     * @param fechaProcesamiento
     * @param numeroPlanilla
     * @param nombreArchivo
     * @param tipoArchivo
     * @param cantidadErrores
     * @param estadoArchivo
     * @param indicePlanilla
     * @param nombreArchivoOF
     * @param totalRecaudoOF
     */
    public InconsistenciaDTO(Date fechaProcesamiento, Long numeroPlanilla, String nombreArchivo, TipoArchivoPilaEnum tipoArchivo, 
            Long cantidadErrores, EstadoProcesoArchivoEnum estadoArchivo, Long indicePlanilla, String nombreArchivoOF, BigDecimal totalRecaudoOF) {
        // , TipoOperadorEnum tipoOperador
        this.fechaProcesamiento = fechaProcesamiento;
        this.numeroPlanilla = numeroPlanilla;
        this.nombreArchivo = nombreArchivo;
        this.tipoArchivo = tipoArchivo;
        this.cantidadErrores = cantidadErrores;
        this.estadoArchivo = estadoArchivo;
        this.indicePlanilla = indicePlanilla;
        this.nombreArchivoOF = nombreArchivoOF;
        if (totalRecaudoOF != null) {
            // Si no se hace retorna números grandes en notacion cientifica
            this.totalRecaudoOFStr = totalRecaudoOF.toBigInteger().toString();
        }
        this.registroProcesado = false;
    }

    /**
     * Constructor para la pantalla de busqueda HU392 - Incluye archivo de OF y total de recaudo OF
     * 
     * @param fechaProcesamiento
     * @param numeroPlanilla
     * @param nombreArchivo
     * @param tipoArchivo
     * @param cantidadErrores
     * @param estadoArchivo
     * @param indicePlanilla
     * @param nombreArchivoOF
     * @param totalRecaudoOF
     */
    public InconsistenciaDTO(Date fechaProcesamiento, Long numeroPlanilla, String nombreArchivo, String tipoArchivo,
            Long cantidadErrores, String estadoArchivo, Long indicePlanilla, String nombreArchivoOF, BigDecimal totalRecaudoOF) {
        this.fechaProcesamiento = fechaProcesamiento;
        this.numeroPlanilla = numeroPlanilla;
        this.nombreArchivo = nombreArchivo;
        this.tipoArchivo = TipoArchivoPilaEnum.valueOf(tipoArchivo);
        this.cantidadErrores = cantidadErrores;
        this.estadoArchivo = EstadoProcesoArchivoEnum.valueOf(estadoArchivo);
        this.indicePlanilla = indicePlanilla;
        this.nombreArchivoOF = nombreArchivoOF;
        if (totalRecaudoOF != null) {
            // Si no se hace retorna números grandes en notacion cientifica
            this.totalRecaudoOFStr = totalRecaudoOF.toBigInteger().toString();
        }
        this.registroProcesado = false;
    }
    
    /**
     * Constructor para la pantalla de busqueda HU392 - Incluye archivo de OF y total de recaudo OF
     * 
     * @param fechaProcesamiento
     * @param numeroPlanilla
     * @param nombreArchivo
     * @param tipoArchivo
     * @param cantidadErrores
     * @param estadoArchivo
     * @param indicePlanilla
     * @param nombreArchivoOF
     * @param totalRecaudoOF
     * @param bloqueo
     */
    public InconsistenciaDTO(Date fechaProcesamiento, Long numeroPlanilla, String nombreArchivo, String tipoArchivo,
            Long cantidadErrores, String estadoArchivo, Long indicePlanilla, String nombreArchivoOF, BigDecimal totalRecaudoOF, String bloqueo) {
        
    	
    	this.fechaProcesamiento = fechaProcesamiento;
        this.numeroPlanilla = numeroPlanilla;
        this.nombreArchivo = nombreArchivo;
        this.tipoArchivo = TipoArchivoPilaEnum.valueOf(tipoArchivo);
        this.cantidadErrores = cantidadErrores;
        this.estadoArchivo = EstadoProcesoArchivoEnum.valueOf(estadoArchivo);
        this.indicePlanilla = indicePlanilla;
        this.nombreArchivoOF = nombreArchivoOF;
        if (totalRecaudoOF != null) {
            // Si no se hace retorna números grandes en notacion cientifica
            this.totalRecaudoOFStr = totalRecaudoOF.toBigInteger().toString();
        }
        this.registroProcesado = false;
        this.bloque = BloqueValidacionEnum.valueOf(bloqueo);
    }

    /**
     * Constructor para la pantalla de busqueda HU392 para planillas tipo F
     * @param fechaProcesamiento
     * @param idErrorValidacion
     * @param nombreArchivo
     * @param tipoArchivo
     * @param cantidadErrores
     * @param estadoArchivo
     * @param fechaRecibo
     * @param indicePlanilla
     */
    public InconsistenciaDTO(Date fechaProcesamiento, Long idErrorValidacion, String nombreArchivo, TipoArchivoPilaEnum tipoArchivo,
            Long cantidadErrores, EstadoProcesoArchivoEnum estadoArchivo, Date fechaRecibo, Long indicePlanilla) {

        this.fechaProcesamiento = fechaProcesamiento;
        this.idErrorValidacion = idErrorValidacion;
        this.estadoArchivo = estadoArchivo;
        this.nombreArchivo = nombreArchivo;
        this.tipoArchivo = tipoArchivo;
        this.cantidadErrores = cantidadErrores;
        this.indicePlanilla = indicePlanilla;
        this.registroProcesado = false;
    }

    /**
     * Constructor para la pantalla de busqueda HU-392 para planillas tipo F
     * @param fechaProcesamiento
     * @param nombreArchivo
     * @param tipoArchivo
     * @param cantidadErrores
     * @param estadoArchivo
     * @param fechaRecibo
     * @param indicePlanilla
     */
    public InconsistenciaDTO(Date fechaProcesamiento, String nombreArchivo, TipoArchivoPilaEnum tipoArchivo,
            Long cantidadErrores, EstadoProcesoArchivoEnum estadoArchivo, Date fechaRecibo, Long indicePlanilla) {

        this.fechaProcesamiento = fechaProcesamiento;        
        this.estadoArchivo = estadoArchivo;
        this.nombreArchivo = nombreArchivo;
        this.tipoArchivo = tipoArchivo;
        this.cantidadErrores = cantidadErrores;
        this.indicePlanilla = indicePlanilla;
        this.registroProcesado = false;
    }

    /**
     * Constructor para la pantalla de busqueda HU-392 para planillas tipo F
     * @param fechaProcesamiento
     * @param nombreArchivo
     * @param tipoArchivo
     * @param cantidadErrores
     * @param estadoArchivo
     * @param fechaRecibo
     * @param indicePlanilla
     */
    public InconsistenciaDTO(Date fechaProcesamiento, String nombreArchivo, String tipoArchivo,
            Long cantidadErrores, String estadoArchivo, Date fechaRecibo, Long indicePlanilla) {

        this.fechaProcesamiento = fechaProcesamiento;        
        this.estadoArchivo = EstadoProcesoArchivoEnum.valueOf(estadoArchivo);
        this.nombreArchivo = nombreArchivo;
        this.tipoArchivo = TipoArchivoPilaEnum.valueOf(tipoArchivo);
        this.cantidadErrores = cantidadErrores;
        this.indicePlanilla = indicePlanilla;
        this.registroProcesado = false;
    }
    
    /**
     * Constructor para la pantalla de busqueda HU-392 para planillas tipo F con campo Bloque
     * @param fechaProcesamiento
     * @param nombreArchivo
     * @param tipoArchivo
     * @param cantidadErrores
     * @param estadoArchivo
     * @param fechaRecibo
     * @param indicePlanilla
     * @param bloque
     */
    public InconsistenciaDTO(Date fechaProcesamiento, String nombreArchivo, String tipoArchivo,
            Long cantidadErrores, String estadoArchivo, Date fechaRecibo, Long indicePlanilla, String bloque) {

        this.fechaProcesamiento = fechaProcesamiento;        
        this.estadoArchivo = EstadoProcesoArchivoEnum.valueOf(estadoArchivo);
        this.nombreArchivo = nombreArchivo;
        this.tipoArchivo = TipoArchivoPilaEnum.valueOf(tipoArchivo);
        this.cantidadErrores = cantidadErrores;
        this.indicePlanilla = indicePlanilla;
        this.registroProcesado = false;
        this.bloque = BloqueValidacionEnum.valueOf(bloque);
    }
    
    /**
     * constructor para el detalle de las pestañas del detalle de la pantalla de
     * busqueda I
     * @param idErrorValidacion
     * @param nombreArchivo
     * @param numeroLinea
     * @param campo
     * @param posInicio
     * @param posFinal
     * @param valorCampo
     * @param tipoError
     * @param codigoError
     * @param indicePlanilla
     * @param tipoArchivo
     * @param numeroPlanilla
     * @param bloque
     * @param descripcionInconsistencia
     * @param estadoGestion
     */
    public InconsistenciaDTO(Long idErrorValidacion, String nombreArchivo, Short numeroLinea, String campo, Short posInicio, Short posFinal,
            String valorCampo, TipoErrorValidacionEnum tipoError, String codigoError, Long indicePlanilla, TipoArchivoPilaEnum tipoArchivo,
            Long numeroPlanilla, BloqueValidacionEnum bloque, String descripcionInconsistencia,
            EstadoGestionInconsistenciaEnum estadoGestion) {
        this.idErrorValidacion = idErrorValidacion;
        this.nombreArchivo = nombreArchivo;
        this.numeroLinea = numeroLinea;
        this.campo = campo;
        this.posInicio = posInicio;
        this.PosFinal = posFinal;
        this.valorCampoInformacion = valorCampo;
        this.tipoError = tipoError;
        this.codigoError = codigoError;
        this.tipoArchivo = tipoArchivo;
        this.indicePlanilla = indicePlanilla;
        this.numeroPlanilla = numeroPlanilla;
        this.bloque = bloque;
        this.descripcionInconsistencia = descripcionInconsistencia;
        this.estadoGestion = estadoGestion;
        this.registroProcesado = false;
    }

    /**
     * constructor para el detalle de las pestañas del detalle de la pantalla de
     * busqueda F
     * @param nombreArchivo
     * @param numeroLinea
     * @param campo
     * @param posInicio
     * @param posFinal
     * @param valorCampoInformacion
     * @param tipoError
     * @param codigoError
     */
    public InconsistenciaDTO(Long idErrorValidacion, String nombreArchivo, Short numeroLinea, String campo, Short posInicio, Short posFinal,
            String valorCampo, TipoErrorValidacionEnum tipoError, String codigoError, Long IndicePlanilla, TipoArchivoPilaEnum tipoArchivo,
            BloqueValidacionEnum bloque, String descripcionInconsistencia) {
        this.idErrorValidacion = idErrorValidacion;
        this.nombreArchivo = nombreArchivo;
        this.numeroLinea = numeroLinea;
        this.campo = campo;
        this.posInicio = posInicio;
        this.PosFinal = posFinal;
        this.valorCampoInformacion = valorCampo;
        this.tipoError = tipoError;
        this.codigoError = codigoError;
        this.tipoArchivo = tipoArchivo;
        this.indicePlanilla = IndicePlanilla;
        this.bloque = bloque;
        this.descripcionInconsistencia = descripcionInconsistencia;
        this.registroProcesado = false;
    }

    /**
     * @param numeroPlanilla
     * @param campo
     * @param valorCampoInformacion
     */
    public InconsistenciaDTO(Long numeroPlanilla, String campo, String valorCampo) {
        super();
        this.numeroPlanilla = numeroPlanilla;
        this.campo = campo;
        this.valorCampoInformacion = valorCampo;
        this.registroProcesado = false;
    }

    /**
     * 
     */
    public InconsistenciaDTO() {

    }

    /**
     * Constructor para la pantalla modal de respuesta de la solicitud de cambio de identificacion
     * @param razonRechazo
     * @param comentarios
     */
    public InconsistenciaDTO(RazonRechazoSolicitudCambioIdenEnum razonRechazo, String comentarios,
            AccionCorreccionPilaEnum accionCorreccion, Long idPlanilla) {
        super();
        this.razonRechazo = razonRechazo;
        this.comentarios = comentarios;
        this.accionCorreccion = accionCorreccion;
        this.numeroPlanilla = idPlanilla;
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
     * @return the numeroPlanilla
     */
    public Long getNumeroPlanilla() {
        return numeroPlanilla;
    }

    /**
     * @param numeroPlanilla
     *        the numeroPlanilla to set
     */
    public void setNumeroPlanilla(Long numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }

    /**
     * @return the numeroIdAportante
     */
    public String getNumeroIdAportante() {
        return NumeroIdAportante;
    }

    /**
     * @param numeroIdAportante
     *        the numeroIdAportante to set
     */
    public void setNumeroIdAportante(String numeroIdAportante) {
        NumeroIdAportante = numeroIdAportante;
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
     * @return the tipoArchivo
     */
    public TipoArchivoPilaEnum getTipoArchivo() {
        return tipoArchivo;
    }

    /**
     * @param tipoArchivo
     *        the tipoArchivo to set
     */
    public void setTipoArchivo(TipoArchivoPilaEnum tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    /**
     * @return the estadoArchivo
     */
    public EstadoProcesoArchivoEnum getEstadoArchivo() {
        return estadoArchivo;
    }

    /**
     * @param estadoArchivo
     *        the estadoArchivo to set
     */
    public void setEstadoArchivo(EstadoProcesoArchivoEnum estadoArchivo) {
        this.estadoArchivo = estadoArchivo;
    }

    /**
     * @return the numeroLinea
     */
    public Short getNumeroLinea() {
        return numeroLinea;
    }

    /**
     * @param numeroLinea
     *        the numeroLinea to set
     */
    public void setNumeroLinea(Short numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    /**
     * @return the bloque
     */
    public BloqueValidacionEnum getBloque() {
        return bloque;
    }

    /**
     * @param bloque
     *        the bloque to set
     */
    public void setBloque(BloqueValidacionEnum bloque) {
        this.bloque = bloque;
    }

    /**
     * @return the campo
     */
    public String getCampo() {
        return campo;
    }

    /**
     * @param campo
     *        the campo to set
     */
    public void setCampo(String campo) {
        this.campo = campo;
    }

    /**
     * @return the posInicio
     */
    public Short getPosInicio() {
        return posInicio;
    }

    /**
     * @param posInicio
     *        the posInicio to set
     */
    public void setPosInicio(Short posInicio) {
        this.posInicio = posInicio;
    }

    /**
     * @return the posFinal
     */
    public Short getPosFinal() {
        return PosFinal;
    }

    /**
     * @param posFinal
     *        the posFinal to set
     */
    public void setPosFinal(Short posFinal) {
        PosFinal = posFinal;
    }

    /**
     * @return the valorCampoInformacion
     */
    public String getValorCampo() {
        return valorCampoInformacion;
    }

    /**
     * @param valorCampoInformacion
     *        the valorCampoInformacion to set
     */
    public void setValorCampo(String valorCampo) {
        this.valorCampoInformacion = valorCampo;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje
     *        the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the tipoError
     */
    public TipoErrorValidacionEnum getTipoError() {
        return tipoError;
    }

    /**
     * @param tipoError
     *        the tipoError to set
     */
    public void setTipoError(TipoErrorValidacionEnum tipoError) {
        this.tipoError = tipoError;
    }

    /**
     * @return the tipoOperador
     */
    public TipoOperadorEnum getTipoOperador() {
        return tipoOperador;
    }

    /**
     * @param tipoOperador
     *        the tipoOperador to set
     */
    public void setTipoOperador(TipoOperadorEnum tipoOperador) {
        this.tipoOperador = tipoOperador;
    }

    /**
     * @return the cantidadErrores
     */
    public Long getCantidadErrores() {
        return cantidadErrores;
    }

    /**
     * @param cantidadErrores
     *        the cantidadErrores to set
     */
    public void setCantidadErrores(Long cantidadErrores) {
        this.cantidadErrores = cantidadErrores;
    }

    /**
     * @return the numeroPlanillaO
     */
    public String getNumeroPlanillaO() {
        return numeroPlanillaO;
    }

    /**
     * @param numeroPlanillaO
     *        the numeroPlanillaO to set
     */
    public void setNumeroPlanillaO(String numeroPlanillaO) {
        this.numeroPlanillaO = numeroPlanillaO;
    }

    /**
     * @return the codigoError
     */
    public String getCodigoError() {
        return codigoError;
    }

    /**
     * @param codigoError
     *        the codigoError to set
     */
    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }

    /**
     * @return the descripcionInconsistencia
     */
    public String getDescripcionInconsistencia() {
        return descripcionInconsistencia;
    }

    /**
     * @param descripcionInconsistencia
     *        the descripcionInconsistencia to set
     */
    public void setDescripcionInconsistencia(String descripcionInconsistencia) {
        this.descripcionInconsistencia = descripcionInconsistencia;
    }

    /**
     * @return the idErrorValidacion
     */
    public Long getIdErrorValidacion() {
        return idErrorValidacion;
    }

    /**
     * @param idErrorValidacion
     *        the idErrorValidacion to set
     */
    public void setIdErrorValidacion(Long idErrorValidacion) {
        this.idErrorValidacion = idErrorValidacion;
    }

    /**
     * @return the numeroReprocesos
     */
    public String getNumeroReprocesos() {
        return numeroReprocesos;
    }

    /**
     * @param numeroReprocesos
     *        the numeroReprocesos to set
     */
    public void setNumeroReprocesos(String numeroReprocesos) {
        this.numeroReprocesos = numeroReprocesos;
    }

    /**
     * @return the razonRechazo
     */
    public RazonRechazoSolicitudCambioIdenEnum getRazonRechazo() {
        return razonRechazo;
    }

    /**
     * @param razonRechazo
     *        the razonRechazo to set
     */
    public void setRazonRechazo(RazonRechazoSolicitudCambioIdenEnum razonRechazo) {
        this.razonRechazo = razonRechazo;
    }

    /**
     * @return the comentarios
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios
     *        the comentarios to set
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * @return the accionCorreccion
     */
    public AccionCorreccionPilaEnum getAccionCorreccion() {
        return accionCorreccion;
    }

    /**
     * @param accionCorreccion
     *        the accionCorreccion to set
     */
    public void setAccionCorreccion(AccionCorreccionPilaEnum accionCorreccion) {
        this.accionCorreccion = accionCorreccion;
    }

    /**
     * @return the nombreArchivoOF
     */
    public String getNombreArchivoOF() {
        return nombreArchivoOF;
    }

    /**
     * @param nombreArchivoOF
     *        the nombreArchivoOF to set
     */
    public void setNombreArchivoOF(String nombreArchivoOF) {
        this.nombreArchivoOF = nombreArchivoOF;
    }

    /**
     * @return the totalRecaudoOF
     */
    public BigDecimal getTotalRecaudoOF() {
        return totalRecaudoOF;
    }

    /**
     * @param totalRecaudoOF
     *        the totalRecaudoOF to set
     */
    public void setTotalRecaudoOF(BigDecimal totalRecaudoOF) {
        this.totalRecaudoOF = totalRecaudoOF;
    }

    /**
     * @return the totalRecaudoOFStr
     */
    public String getTotalRecaudoOFStr() {
        return totalRecaudoOFStr;
    }

    /**
     * @param totalRecaudoOFStr
     *        the totalRecaudoOFStr to set
     */
    public void setTotalRecaudoOFStr(String totalRecaudoOFStr) {
        this.totalRecaudoOFStr = totalRecaudoOFStr;
    }

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion
     *        the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the cantidadRegistros6ArchivoF
     */
    public Long getCantidadRegistros6ArchivoF() {
        return cantidadRegistros6ArchivoF;
    }

    /**
     * @param cantidadRegistros6ArchivoF
     *        the cantidadRegistros6ArchivoF to set
     */
    public void setCantidadRegistros6ArchivoF(Long cantidadRegistros6ArchivoF) {
        this.cantidadRegistros6ArchivoF = cantidadRegistros6ArchivoF;
    }

    /**
     * @return the registroProcesado
     */
    public Boolean getRegistroProcesado() {
        return registroProcesado;
    }

    /**
     * @param registroProcesado
     *        the registroProcesado to set
     */
    public void setRegistroProcesado(Boolean registroProcesado) {
        this.registroProcesado = registroProcesado;
    }

    /**
     * @return the nombreArchivoPar
     */
    public String getNombreArchivoPar() {
        return nombreArchivoPar;
    }

    /**
     * @param nombreArchivoPar
     *        the nombreArchivoPar to set
     */
    public void setNombreArchivoPar(String nombreArchivoPar) {
        this.nombreArchivoPar = nombreArchivoPar;
    }

    /**
     * @return
     */
    public String getRazonSocialAportante() {
        return razonSocialAportante;
    }

    /**
     * @param razonSocialAportante
     */
    public void setRazonSocialAportante(String razonSocialAportante) {
        this.razonSocialAportante = razonSocialAportante;
    }

    /**
     * @return
     */
    public PersonaModeloDTO getNombresNuevoAportante() {
        return nombresNuevoAportante;
    }

    /**
     * @param nombresNuevoAportante
     */
    public void setNombresNuevoAportante(PersonaModeloDTO nombresNuevoAportante) {
        this.nombresNuevoAportante = nombresNuevoAportante;
    }

    /**
     * @return the esAportePropio
     */
    public Boolean getEsAportePropio() {
        return esAportePropio;
    }

    /**
     * @param esAportePropio the esAportePropio to set
     */
    public void setEsAportePropio(Boolean esAportePropio) {
        this.esAportePropio = esAportePropio;
    }

    /**
     * @return the indicePlanillaPar
     */
    public Long getIndicePlanillaPar() {
        return indicePlanillaPar;
    }

    /**
     * @param indicePlanillaPar the indicePlanillaPar to set
     */
    public void setIndicePlanillaPar(Long indicePlanillaPar) {
        this.indicePlanillaPar = indicePlanillaPar;
    }
    
}
