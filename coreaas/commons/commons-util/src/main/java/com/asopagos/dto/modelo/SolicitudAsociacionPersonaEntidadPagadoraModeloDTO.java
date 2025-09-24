package com.asopagos.dto.modelo;

import java.util.Date;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAsociacionPersonaEntidadPagadora;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudPersonaEntidadPagadoraEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoGestionSolicitudAsociacionEnum;

public class SolicitudAsociacionPersonaEntidadPagadoraModeloDTO extends SolicitudModeloDTO{
    
    private static final long serialVersionUID = -5923473874997097537L;
    
    private Long idSolicitudAsociacionPersonaEntidadPagadora;
    private RolAfiliado rolAfiliado;
    private EstadoSolicitudPersonaEntidadPagadoraEnum estado;
    private TipoGestionSolicitudAsociacionEnum tipoGestion;
    private Date fechaGestion;
    private String consecutivo;
    private String identificadorArchivoPlano;
    private String identificadorArchivoCarta;
    private String identificadorCartaResultadoGestion;
    private String usuarioGestion;
    
    
    public void convertToDTO(SolicitudAsociacionPersonaEntidadPagadora solicitudEntidadPagadora){
        this.idSolicitudAsociacionPersonaEntidadPagadora = solicitudEntidadPagadora.getIdSolicitudAsociacionPersonaEntidadPagadora();
        this.rolAfiliado = solicitudEntidadPagadora.getRolAfiliado();
        this.estado = solicitudEntidadPagadora.getEstado();
        this.tipoGestion = solicitudEntidadPagadora.getTipoGestion();
        this.fechaGestion = solicitudEntidadPagadora.getFechaGestion();
        this.consecutivo = solicitudEntidadPagadora.getConsecutivo();
        this.identificadorArchivoPlano = solicitudEntidadPagadora.getIdentificadorArchivoPlano();
        this.identificadorArchivoCarta = solicitudEntidadPagadora.getIdentificadorArchivoCarta();
        this.identificadorCartaResultadoGestion = solicitudEntidadPagadora.getIdentificadorCartaResultadoGestion();
        this.usuarioGestion = solicitudEntidadPagadora.getUsuarioGestion();
        
        if (solicitudEntidadPagadora.getSolicitudGlobal() != null) {
            super.convertToDTO(solicitudEntidadPagadora.getSolicitudGlobal());
        }
    }
    
    public SolicitudAsociacionPersonaEntidadPagadora convertToEntity(){
        SolicitudAsociacionPersonaEntidadPagadora solicitudEntidadPagadora = new SolicitudAsociacionPersonaEntidadPagadora();
        
        solicitudEntidadPagadora.setIdSolicitudAsociacionPersonaEntidadPagadora(idSolicitudAsociacionPersonaEntidadPagadora);
        solicitudEntidadPagadora.setRolAfiliado(rolAfiliado);
        solicitudEntidadPagadora.setEstado(estado);
        solicitudEntidadPagadora.setTipoGestion(tipoGestion);
        solicitudEntidadPagadora.setFechaGestion(fechaGestion);
        solicitudEntidadPagadora.setConsecutivo(consecutivo);
        solicitudEntidadPagadora.setIdentificadorArchivoPlano(identificadorArchivoPlano);
        solicitudEntidadPagadora.setIdentificadorArchivoCarta(identificadorArchivoCarta);
        solicitudEntidadPagadora.setIdentificadorCartaResultadoGestion(identificadorCartaResultadoGestion);
        solicitudEntidadPagadora.setUsuarioGestion(usuarioGestion);
        
        Solicitud solicitudGlobal = super.convertToSolicitudEntity();
        solicitudEntidadPagadora.setSolicitudGlobal(solicitudGlobal);
        
        return solicitudEntidadPagadora;
    }
    
    
    public Long getIdSolicitudAsociacionPersonaEntidadPagadora() {
        return idSolicitudAsociacionPersonaEntidadPagadora;
    }
    public RolAfiliado getRolAfiliado() {
        return rolAfiliado;
    }
    public EstadoSolicitudPersonaEntidadPagadoraEnum getEstado() {
        return estado;
    }
    public TipoGestionSolicitudAsociacionEnum getTipoGestion() {
        return tipoGestion;
    }
    public Date getFechaGestion() {
        return fechaGestion;
    }
    public String getConsecutivo() {
        return consecutivo;
    }
    public String getIdentificadorArchivoPlano() {
        return identificadorArchivoPlano;
    }
    public String getIdentificadorArchivoCarta() {
        return identificadorArchivoCarta;
    }
    public String getIdentificadorCartaResultadoGestion() {
        return identificadorCartaResultadoGestion;
    }
    public String getUsuarioGestion() {
        return usuarioGestion;
    }
    public void setIdSolicitudAsociacionPersonaEntidadPagadora(Long idSolicitudAsociacionPersonaEntidadPagadora) {
        this.idSolicitudAsociacionPersonaEntidadPagadora = idSolicitudAsociacionPersonaEntidadPagadora;
    }
    public void setRolAfiliado(RolAfiliado rolAfiliado) {
        this.rolAfiliado = rolAfiliado;
    }
    public void setEstado(EstadoSolicitudPersonaEntidadPagadoraEnum estado) {
        this.estado = estado;
    }
    public void setTipoGestion(TipoGestionSolicitudAsociacionEnum tipoGestion) {
        this.tipoGestion = tipoGestion;
    }
    public void setFechaGestion(Date fechaGestion) {
        this.fechaGestion = fechaGestion;
    }
    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }
    public void setIdentificadorArchivoPlano(String identificadorArchivoPlano) {
        this.identificadorArchivoPlano = identificadorArchivoPlano;
    }
    public void setIdentificadorArchivoCarta(String identificadorArchivoCarta) {
        this.identificadorArchivoCarta = identificadorArchivoCarta;
    }
    public void setIdentificadorCartaResultadoGestion(String identificadorCartaResultadoGestion) {
        this.identificadorCartaResultadoGestion = identificadorCartaResultadoGestion;
    }
    public void setUsuarioGestion(String usuarioGestion) {
        this.usuarioGestion = usuarioGestion;
    }


}
