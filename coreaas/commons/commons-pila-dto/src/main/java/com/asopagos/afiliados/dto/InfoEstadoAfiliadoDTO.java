package com.asopagos.afiliados.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;

public class InfoEstadoAfiliadoDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private EstadoAfiliadoEnum estadoTrabRespectoEmpl;
    private Date ultimaFechaIngreso;
    private CanalRecepcionEnum canal;
    private Date fechaRetiro;
    private Long idEmpresa;
    private Long idPersona;
    private Long idRolAfiliado;
    private String sucursalAsociada;
    private String numeroRadicado;
    private String idInstanciaProceso;
    private Long idSolicitud;
    private MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion;
    private String municipioLabores;
    private Date fechaRecepcionDocumento;
    
    /**
     * 
     */
    public InfoEstadoAfiliadoDTO() {
    }

    /**
     * @param estadoTrabRespectoEmpl
     * @param ultimaFechaIngreso
     * @param canal
     * @param fechaRetiro
     * @param idEmpresa
     * @param idPersona
     * @param rolAfiliado
     * @param numeroRadicado
     * @param idInstanciaProceso
     * @param idSolicitud
     */
    public InfoEstadoAfiliadoDTO(EstadoAfiliadoEnum estadoTrabRespectoEmpl, Date ultimaFechaIngreso, CanalRecepcionEnum canal,
            Date fechaRetiro, Long idEmpresa, Long idPersona, Long rolAfiliado, String numeroRadicado,
            String idInstanciaProceso, Long idSolicitud) {
        super();
        this.convertToDTO(estadoTrabRespectoEmpl, ultimaFechaIngreso, canal, fechaRetiro, idEmpresa, idPersona, rolAfiliado, numeroRadicado,
                idInstanciaProceso, idSolicitud, null, null, null, null);
    }
    
    public InfoEstadoAfiliadoDTO(EstadoAfiliadoEnum estadoTrabRespectoEmpl, Date ultimaFechaIngreso, CanalRecepcionEnum canal,
            Date fechaRetiro, Long idEmpresa, Long idPersona, Long idRolAfiliado, String sucursalAsociada, String numeroRadicado,
            String idInstanciaProceso, Long idSolicitud) {
        super();
        this.convertToDTO(estadoTrabRespectoEmpl, ultimaFechaIngreso, canal, fechaRetiro, idEmpresa, idPersona, idRolAfiliado,
                numeroRadicado, idInstanciaProceso, idSolicitud, sucursalAsociada, null, null, null);
    }

    /**
     * @param estadoTrabRespectoEmpl
     * @param ultimaFechaIngreso
     * @param canal
     * @param fechaRetiro
     */
    public InfoEstadoAfiliadoDTO(EstadoAfiliadoEnum estadoTrabRespectoEmpl, Date ultimaFechaIngreso, CanalRecepcionEnum canal,
            Date fechaRetiro, Long idEmpresa, Long idPersona, Long rolAfiliado) {
        super();
        this.convertToDTO(estadoTrabRespectoEmpl, ultimaFechaIngreso, canal, fechaRetiro, idEmpresa, idPersona, rolAfiliado, null, null,
                null, null, null, null, null);
    }

    /**
     * Constructor de objeto apartir de resultado de consulta
     * @param estadoTrabRespectoEmpl
     *        Estado de afiliado respecto empleador
     * @param ultimaFechaIngreso
     *        Última fecha de ingreso
     * @param canal
     *        Canal de recepción de la afiliación
     * @param fechaRetiro
     *        Fecha de retiro
     * @param idEmpresa
     *        Identificador empresa empleador
     * @param idPersona
     *        Identificador persona afiliado
     * @param rolAfiliado
     *        Identificador rol de afiliado
     * @param numeroRadicado
     *        Numero de radicación afiliación
     * @param idInstanciaProceso
     *        Identificador instancia de proceso afiliación
     * @param idSolicitud
     *        Identificador solicitud afiliación
     */
    public InfoEstadoAfiliadoDTO(String estadoTrabRespectoEmpl, Date ultimaFechaIngreso, String canal, Date fechaRetiro, Long idEmpresa,
            Long idPersona, Long rolAfiliado, String numeroRadicado, String idInstanciaProceso, Long idSolicitud, String motivoDesafiliacion,
            String municipioLabores, Date fechaRecepcionDocumento) {
        super();
        EstadoAfiliadoEnum estadoAfiliado = null;
        CanalRecepcionEnum canalRecepcion = null;
        MotivoDesafiliacionAfiliadoEnum motivoDesafiliacionAfiliado = null;
        if (estadoTrabRespectoEmpl != null) {
            estadoAfiliado = EstadoAfiliadoEnum.valueOf(estadoTrabRespectoEmpl);
        }
        if (canal != null) {
            canalRecepcion = CanalRecepcionEnum.valueOf(canal);
        }
        if (motivoDesafiliacion != null) {
            motivoDesafiliacionAfiliado = MotivoDesafiliacionAfiliadoEnum.valueOf(motivoDesafiliacion);
        }
        this.convertToDTO(estadoAfiliado, ultimaFechaIngreso, canalRecepcion, fechaRetiro, idEmpresa, idPersona, rolAfiliado,
                numeroRadicado, idInstanciaProceso, idSolicitud, null, motivoDesafiliacionAfiliado, municipioLabores, fechaRecepcionDocumento);
    }

    /**
     * Metodo encargado de inicializar el objeto
     * @param estadoTrabRespectoEmpl
     *        Estado de afiliado respecto empleador
     * @param ultimaFechaIngreso
     *        Última fecha de ingreso
     * @param canal
     *        Canal de recepción de la afiliación
     * @param fechaRetiro
     *        Fecha de retiro
     * @param idEmpresa
     *        Identificador empresa empleador
     * @param idPersona
     *        Identificador persona afiliado
     * @param idRolAfiliado
     *        Identificador rol de afiliado
     * @param numeroRadicado
     *        Numero de radicación afiliación
     * @param idInstanciaProceso
     *        Identificador instancia de proceso afiliación
     * @param idSolicitud
     *        Identificador solicitud afiliación
     * @param sucursalAsociada
     *        Nombre de la sucursal a la que pertence el afiliado
     */
    private void convertToDTO(EstadoAfiliadoEnum estadoTrabRespectoEmpl, Date ultimaFechaIngreso, CanalRecepcionEnum canal,
            Date fechaRetiro, Long idEmpresa, Long idPersona, Long idRolAfiliado, String numeroRadicado, String idInstanciaProceso,
            Long idSolicitud, String sucursalAsociada, MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion,
            String municipioLabores, Date fechaRecepcionDocumento) {
        this.setEstadoTrabRespectoEmpl(estadoTrabRespectoEmpl);
        this.setUltimaFechaIngreso(ultimaFechaIngreso);
        this.setCanal(canal);
        this.setFechaRetiro(fechaRetiro);
        this.setIdEmpresa(idEmpresa);
        this.setIdPersona(idPersona);
        this.setIdRolAfiliado(idRolAfiliado);
        this.setNumeroRadicado(numeroRadicado);
        this.setIdInstanciaProceso(idInstanciaProceso);
        this.setIdSolicitud(idSolicitud);
        this.setSucursalAsociada(sucursalAsociada);
        this.setMotivoDesafiliacion(motivoDesafiliacion);
        this.setMunicipioLabores(municipioLabores);
        this.setFechaRecepcionDocumento(fechaRecepcionDocumento);
    }

    /**
     * @return the estadoTrabRespectoEmpl
     */
    public EstadoAfiliadoEnum getEstadoTrabRespectoEmpl() {
        return estadoTrabRespectoEmpl;
    }

    /**
     * @param estadoTrabRespectoEmpl the estadoTrabRespectoEmpl to set
     */
    public void setEstadoTrabRespectoEmpl(EstadoAfiliadoEnum estadoTrabRespectoEmpl) {
        this.estadoTrabRespectoEmpl = estadoTrabRespectoEmpl;
    }

    /**
     * @return the ultimaFechaIngreso
     */
    public Date getUltimaFechaIngreso() {
        return ultimaFechaIngreso;
    }

    /**
     * @param ultimaFechaIngreso the ultimaFechaIngreso to set
     */
    public void setUltimaFechaIngreso(Date ultimaFechaIngreso) {
        this.ultimaFechaIngreso = ultimaFechaIngreso;
    }

    /**
     * @return the canal
     */
    public CanalRecepcionEnum getCanal() {
        return canal;
    }

    /**
     * @param canal the canal to set
     */
    public void setCanal(CanalRecepcionEnum canal) {
        this.canal = canal;
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
     * @return the idEmpresa
     */
    public Long getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * @param idEmpresa the idEmpresa to set
     */
    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
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
     * @return the idRolAfiliado
     */
    public Long getIdRolAfiliado() {
        return idRolAfiliado;
    }

    /**
     * @param idRolAfiliado the idRolAfiliado to set
     */
    public void setIdRolAfiliado(Long idRolAfiliado) {
        this.idRolAfiliado = idRolAfiliado;
    }

    /**
     * @return the sucursalAsociada
     */
    public String getSucursalAsociada() {
        return sucursalAsociada;
    }

    /**
     * @param sucursalAsociada the sucursalAsociada to set
     */
    public void setSucursalAsociada(String sucursalAsociada) {
        this.sucursalAsociada = sucursalAsociada;
    }

    /**
     * @return the numeroRadicado
     */
    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    /**
     * @param numeroRadicado the numeroRadicado to set
     */
    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

    /**
     * @return the idInstanciaProceso
     */
    public String getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    /**
     * @param idInstanciaProceso the idInstanciaProceso to set
     */
    public void setIdInstanciaProceso(String idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
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
     * @return the motivoDesafiliacion
     */
    public MotivoDesafiliacionAfiliadoEnum getMotivoDesafiliacion() {
        return motivoDesafiliacion;
    }

    /**
     * @param motivoDesafiliacion the motivoDesafiliacion to set
     */
    public void setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion) {
        this.motivoDesafiliacion = motivoDesafiliacion;
    }

    /**
     * @return the municipioLabores
     */
    public String getMunicipioLabores() {
        return municipioLabores;
    }

    /**
     * @param municipioLabores the municipioLabores to set
     */
    public void setMunicipioLabores(String municipioLabores) {
        this.municipioLabores = municipioLabores;
    }

    /**
     * @return the fechaRecepcionDocumento
     */
    public Date getFechaRecepcionDocumento() {
        return fechaRecepcionDocumento;
    }

    /**
     * @param fechaRecepcionDocumento the fechaRecepcionDocumento to set
     */
    public void setFechaRecepcionDocumento(Date fechaRecepcionDocumento) {
        this.fechaRecepcionDocumento = fechaRecepcionDocumento;
    }
    
    
}
