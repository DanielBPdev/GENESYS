package com.asopagos.pila.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class DetalleAporteVista360DTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    //INFORMACIÓN DEL PERIODO
    
    private String numeroOperacion; //pendiente
    private String numeroPlanilla; 
    private String tipoPlanilla;
    private Date fechaRecaudo;
    private String estadoArchivo; //pendiente
    private String periodo;
    private String metodoRecaudo; //pendiente
    private String tipoArchivoDetalle; //pendiente
    private int tieneModificaciones;
    private int condetalle;

    //INFORMACIÓN DEL COTIZANTE

    private TipoIdentificacionEnum tipoIdentificacion;
    private String numeroIdentificacion;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private Short estadoAfiliado;
    private EstadoAfiliadoEnum estadoAfiliadoEnum;
    private String tipoAfiliado;
    private int aportTieneModificaciones; //por ahora igual a this.tieneModificaciones
    private Date fechaIngreso;
    private Date fechaRetiro;
    
    // INFORMACIÓN DEL APORTANTE
    private TipoIdentificacionEnum tipoIdAportante;
    private String numeroIdAportante;
    
    //INFORMACIÓN HISTORICO APORTES
    private List<HistoricoMovimientoAporte360DTO> historicoMovimientosAporte;
    private List<HistoricoNovedades360DTO> historicoNovedades;
    private InformacionAporte360DTO informacionAporte;
    
    /**
     * 
     */
    public DetalleAporteVista360DTO() {
    }

    
    
    /**
     * @param numeroPlanilla
     * @param tipoPlanilla
     * @param fechaRecaudo
     * @param periodo
     * @param tieneModificaciones
     * @param condetalle
     * @param tipoIdentificacion
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param tipoAfiliado
     * @param aportTieneModificaciones
     * @param fechaIngreso
     * @param fechaRetiro
     * @param numeroIdentificacion
     */
    public DetalleAporteVista360DTO(String numeroPlanilla, String tipoPlanilla, Date fechaRecaudo, String periodo, int tieneModificaciones,
            int condetalle, TipoIdentificacionEnum tipoIdentificacion, String primerNombre, String segundoNombre, String primerApellido,
            String segundoApellido, String tipoAfiliado, int aportTieneModificaciones, Date fechaIngreso, Date fechaRetiro,
            String numeroIdentificacion, TipoIdentificacionEnum tipoIdAportante, String numeroIdAportante) {
        this.numeroPlanilla = numeroPlanilla;
        this.tipoPlanilla = tipoPlanilla;
        this.fechaRecaudo = fechaRecaudo;
        this.periodo = periodo;
        this.tieneModificaciones = tieneModificaciones;
        this.condetalle = condetalle;
        this.tipoIdentificacion = tipoIdentificacion;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.tipoAfiliado = tipoAfiliado;
        this.aportTieneModificaciones = aportTieneModificaciones;
        this.fechaIngreso = fechaIngreso;
        this.fechaRetiro = fechaRetiro;
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoIdAportante = tipoIdAportante;
        this.numeroIdAportante = numeroIdAportante;
    }



    /**
     * @param numeroOperacion
     * @param numeroPlanilla
     * @param tipoPlanilla
     * @param fechaRecaudo
     * @param estadoArchivo
     * @param periodo
     * @param metodoRecaudo
     * @param tipoArchivoDetalle
     * @param tieneModificaciones
     * @param condetalle
     * @param tipoIdentificacion
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param estadoAfiliado
     * @param tipoAfiliado
     * @param aportTieneModificaciones
     * @param fechaIngreso
     * @param fechaRetiro
     */
    public DetalleAporteVista360DTO(String numeroOperacion, String numeroPlanilla, String tipoPlanilla, Date fechaRecaudo,
            String estadoArchivo, String periodo, String metodoRecaudo, String tipoArchivoDetalle, int tieneModificaciones, int condetalle,
            TipoIdentificacionEnum tipoIdentificacion, String primerNombre, String segundoNombre, String primerApellido,
            String segundoApellido, Short estadoAfiliado, String tipoAfiliado, int aportTieneModificaciones, Date fechaIngreso,
            Date fechaRetiro) {
        this.numeroOperacion = numeroOperacion;
        this.numeroPlanilla = numeroPlanilla;
        this.tipoPlanilla = tipoPlanilla;
        this.fechaRecaudo = fechaRecaudo;
        this.estadoArchivo = estadoArchivo;
        this.periodo = periodo;
        this.metodoRecaudo = metodoRecaudo;
        this.tipoArchivoDetalle = tipoArchivoDetalle;
        this.tieneModificaciones = tieneModificaciones;
        this.condetalle = condetalle;
        this.tipoIdentificacion = tipoIdentificacion;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.estadoAfiliado = estadoAfiliado;
        this.tipoAfiliado = tipoAfiliado;
        this.aportTieneModificaciones = aportTieneModificaciones;
        this.fechaIngreso = fechaIngreso;
        this.fechaRetiro = fechaRetiro;
    }

    /**
     * @return the numeroOperacion
     */
    public String getNumeroOperacion() {
        return numeroOperacion;
    }
    /**
     * @param numeroOperacion the numeroOperacion to set
     */
    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }
    /**
     * @return the numeroPlanilla
     */
    public String getNumeroPlanilla() {
        return numeroPlanilla;
    }
    /**
     * @param numeroPlanilla the numeroPlanilla to set
     */
    public void setNumeroPlanilla(String numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }
    /**
     * @return the tipoPlanilla
     */
    public String getTipoPlanilla() {
        return tipoPlanilla;
    }
    /**
     * @param tipoPlanilla the tipoPlanilla to set
     */
    public void setTipoPlanilla(String tipoPlanilla) {
        this.tipoPlanilla = tipoPlanilla;
    }
    /**
     * @return the fechaRecaudo
     */
    public Date getFechaRecaudo() {
        return fechaRecaudo;
    }
    /**
     * @param fechaRecaudo the fechaRecaudo to set
     */
    public void setFechaRecaudo(Date fechaRecaudo) {
        this.fechaRecaudo = fechaRecaudo;
    }
    /**
     * @return the estadoArchivo
     */
    public String getEstadoArchivo() {
        return estadoArchivo;
    }
    /**
     * @param estadoArchivo the estadoArchivo to set
     */
    public void setEstadoArchivo(String estadoArchivo) {
        this.estadoArchivo = estadoArchivo;
    }
    /**
     * @return the periodo
     */
    public String getPeriodo() {
        return periodo;
    }
    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    /**
     * @return the metodoRecaudo
     */
    public String getMetodoRecaudo() {
        return metodoRecaudo;
    }
    /**
     * @param metodoRecaudo the metodoRecaudo to set
     */
    public void setMetodoRecaudo(String metodoRecaudo) {
        this.metodoRecaudo = metodoRecaudo;
    }
    /**
     * @return the tipoArchivoDetalle
     */
    public String getTipoArchivoDetalle() {
        return tipoArchivoDetalle;
    }
    /**
     * @param tipoArchivoDetalle the tipoArchivoDetalle to set
     */
    public void setTipoArchivoDetalle(String tipoArchivoDetalle) {
        this.tipoArchivoDetalle = tipoArchivoDetalle;
    }
    /**
     * @return the tieneModificaciones
     */
    public int getTieneModificaciones() {
        return tieneModificaciones;
    }
    /**
     * @param tieneModificaciones the tieneModificaciones to set
     */
    public void setTieneModificaciones(int tieneModificaciones) {
        this.tieneModificaciones = tieneModificaciones;
    }
    /**
     * @return the condetalle
     */
    public int getCondetalle() {
        return condetalle;
    }
    /**
     * @param condetalle the condetalle to set
     */
    public void setCondetalle(int condetalle) {
        this.condetalle = condetalle;
    }
    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }
    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
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
     * @return the estadoAfiliado
     */
    public Short getEstadoAfiliado() {
        return estadoAfiliado;
    }
    /**
     * @param estadoAfiliado the estadoAfiliado to set
     */
    public void setEstadoAfiliado(Short estadoAfiliado) {
        this.estadoAfiliado = estadoAfiliado;
    }
    /**
     * @return the tipoAfiliado
     */
    public String getTipoAfiliado() {
        return tipoAfiliado;
    }
    /**
     * @param tipoAfiliado the tipoAfiliado to set
     */
    public void setTipoAfiliado(String tipoAfiliado) {
        this.tipoAfiliado = tipoAfiliado;
    }
    /**
     * @return the aportTieneModificaciones
     */
    public int getAportTieneModificaciones() {
        return aportTieneModificaciones;
    }
    /**
     * @param aportTieneModificaciones the aportTieneModificaciones to set
     */
    public void setAportTieneModificaciones(int aportTieneModificaciones) {
        this.aportTieneModificaciones = aportTieneModificaciones;
    }
    /**
     * @return the fechaIngreso
     */
    public Date getFechaIngreso() {
        return fechaIngreso;
    }
    /**
     * @param fechaIngreso the fechaIngreso to set
     */
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    /**
     * @return the fechaRetiro
     */
    public Date getFechaRetiro() {
        return fechaRetiro;
    }
    /**
     * @param fechaRetiro the fechaRetiro to set
     */
    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }
    /**
     * @return the historicoMovimientosAporte
     */
    public List<HistoricoMovimientoAporte360DTO> getHistoricoMovimientosAporte() {
        return historicoMovimientosAporte;
    }
    /**
     * @param historicoMovimientosAporte the historicoMovimientosAporte to set
     */
    public void setHistoricoMovimientosAporte(List<HistoricoMovimientoAporte360DTO> historicoMovimientosAporte) {
        this.historicoMovimientosAporte = historicoMovimientosAporte;
    }
    /**
     * @return the historicoNovedades
     */
    public List<HistoricoNovedades360DTO> getHistoricoNovedades() {
        return historicoNovedades;
    }
    /**
     * @param historicoNovedades the historicoNovedades to set
     */
    public void setHistoricoNovedades(List<HistoricoNovedades360DTO> historicoNovedades) {
        this.historicoNovedades = historicoNovedades;
    }
    /**
     * @return the informacionAporte
     */
    public InformacionAporte360DTO getInformacionAporte() {
        return informacionAporte;
    }
    /**
     * @param informacionAporte the informacionAporte to set
     */
    public void setInformacionAporte(InformacionAporte360DTO informacionAporte) {
        this.informacionAporte = informacionAporte;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }
    
    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the estadoAfiliadoEnum
     */
    public EstadoAfiliadoEnum getEstadoAfiliadoEnum() {
        return estadoAfiliadoEnum;
    }

    /**
     * @param estadoAfiliadoEnum the estadoAfiliadoEnum to set
     */
    public void setEstadoAfiliadoEnum(EstadoAfiliadoEnum estadoAfiliadoEnum) {
        this.estadoAfiliadoEnum = estadoAfiliadoEnum;
    }

    /**
     * @return the tipoIdAportante
     */
    public TipoIdentificacionEnum getTipoIdAportante() {
        return tipoIdAportante;
    }

    /**
     * @param tipoIdAportante the tipoIdAportante to set
     */
    public void setTipoIdAportante(TipoIdentificacionEnum tipoIdAportante) {
        this.tipoIdAportante = tipoIdAportante;
    }

    /**
     * @return the numeroIdAportante
     */
    public String getNumeroIdAportante() {
        return numeroIdAportante;
    }

    /**
     * @param numeroIdAportante the numeroIdAportante to set
     */
    public void setNumeroIdAportante(String numeroIdAportante) {
        this.numeroIdAportante = numeroIdAportante;
    }
}
