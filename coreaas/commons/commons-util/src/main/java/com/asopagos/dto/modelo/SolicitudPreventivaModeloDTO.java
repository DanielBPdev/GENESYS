package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import com.asopagos.entidades.ccf.cartera.SolicitudPreventiva;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.cartera.EstadoSolicitudPreventivaEnum;
import com.asopagos.enumeraciones.cartera.TipoGestionCarteraEnum;

/**
 * DTO que relaciona una solicitud preventiva para un aportante.
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
public class SolicitudPreventivaModeloDTO extends SolicitudModeloDTO implements Serializable {

    /**
     * Serial version
     */
    private static final long serialVersionUID = 8693475243836227350L;

    /**
     * Identificador único de una solicitud preventiva.
     */
    private Long idSolicitudPreventiva;

    /**
     * Persona o empleador a quien se le genera la solicitud preventiva.
     */
    private Long idPersona;

    /**
     * Tipo de solicitante del aporte.
     */
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;

    /**
     * Estado de la solicitud preventiva.
     */
    private EstadoSolicitudPreventivaEnum estadoSolicitudPreventiva;

    /**
     * Atributo que indica si se requiere o no fiscalización.
     */
    private Boolean requiereFiscalizacion;

    /**
     * Atributo que indica si la actualización de datos fue efectiva.
     */
    private Boolean actualizacionEfectiva;

    /**
     * Atributo que indica si hubo contacto efectivo
     */
    private Boolean contactoEfectivo;

    /**
     * Atributo que indica el back de actualización.
     */
    private String backActualizacion;

    /**
     * Atributo tipo de gestión de cartera
     */
    private TipoGestionCarteraEnum tipoGestionCartera;
    
    /**
     * Atributo que contiene la fecha en que entró a fiscalización por 
     */
    private Long fechaFiscalizacion;
    
    /**
     * Atributo que indica la referencia hacia el id de idSolicitudPreventivaAgrupadora
     */
    private Long idSolicitudPreventivaAgrupadora;
    /**
     * Estado actual de cartera
     */
    private EstadoCarteraEnum estadoActualCartera;
    /**
     * Valor promedio de los aportes.
     */
    private BigDecimal valorPromedioAportes;
    /**
     * Cantidad de trabajadores activos
     */
    private Short trabajadoresActivos;
    /**
     * Cantidad de veces en estado moroso.
     */
    private Short cantidadVecesMoroso;
    /**
     * Atributo que indica la fecha de limite de pago.
     */
    private Long fechaLimitePago;
    /**
     * Método encargado de convertir de DTO a entidad.
     * @return SolicitudPreventiva convertida.
     */
    public SolicitudPreventiva convertToEntity() {
        SolicitudPreventiva solicitudPreventiva = new SolicitudPreventiva();
        solicitudPreventiva.setIdSolicitudPreventiva(this.getIdSolicitudPreventiva());
        solicitudPreventiva.setIdPersona(this.getIdPersona());
        solicitudPreventiva.setTipoSolicitante(this.getTipoSolicitante());
        solicitudPreventiva.setEstadoSolicitudPreventiva(this.getEstadoSolicitudPreventiva());
        solicitudPreventiva.setRequiereFiscalizacion(this.getRequiereFiscalizacion());
        solicitudPreventiva.setActualizacionEfectiva(this.getActualizacionEfectiva());
        solicitudPreventiva.setContactoEfectivo(this.getContactoEfectivo());
        Solicitud solicitudGlobal = super.convertToSolicitudEntity();
        solicitudPreventiva.setSolicitudGlobal(solicitudGlobal);
        solicitudPreventiva.setTipoGestionCartera(this.getTipoGestionCartera());
        if(this.getFechaFiscalizacion()!=null){
            solicitudPreventiva.setFechaFiscalizacion(new Date(this.getFechaFiscalizacion()));
        }
        solicitudPreventiva.setIdSolicitudPreventivaAgrupadora(this.getIdSolicitudPreventivaAgrupadora()!= null ? this.getIdSolicitudPreventivaAgrupadora() : null);
        solicitudPreventiva.setEstadoActualCartera(this.getEstadoActualCartera()!= null ? this.getEstadoActualCartera() : null);
        solicitudPreventiva.setValorPromedioAportes(this.getValorPromedioAportes()!= null ? this.getValorPromedioAportes() :null);
        solicitudPreventiva.setTrabajadoresActivos(this.getTrabajadoresActivos() != null ? this.getTrabajadoresActivos() :null);
        solicitudPreventiva.setCantidadVecesMoroso(this.getCantidadVecesMoroso()!= null ? this.getCantidadVecesMoroso():null);
        if(this.getFechaLimitePago()!=null){
            solicitudPreventiva.setFechaLimitePago(new Date(this.getFechaLimitePago()));
        }
        return solicitudPreventiva;
    }

    /**
     * Método encargado de convertir de Entidad a DTO.
     * @param solicitudPreventiva
     *        entidad a convertir.
     */
    public void convertToDTO(SolicitudPreventiva solicitudPreventiva) {
        if (solicitudPreventiva.getSolicitudGlobal() != null) {
            super.convertToDTO(solicitudPreventiva.getSolicitudGlobal());
        }
        this.setIdSolicitudPreventiva(solicitudPreventiva.getIdSolicitudPreventiva());
        this.setIdPersona(solicitudPreventiva.getIdPersona());
        this.setTipoSolicitante(solicitudPreventiva.getTipoSolicitante());
        this.setEstadoSolicitudPreventiva(solicitudPreventiva.getEstadoSolicitudPreventiva());
        this.setRequiereFiscalizacion(solicitudPreventiva.getRequiereFiscalizacion());
        this.setActualizacionEfectiva(solicitudPreventiva.getActualizacionEfectiva());
        this.setContactoEfectivo(solicitudPreventiva.getContactoEfectivo());
        this.setTipoGestionCartera(solicitudPreventiva.getTipoGestionCartera());
        if(solicitudPreventiva.getFechaFiscalizacion()!= null){
            this.setFechaFiscalizacion(solicitudPreventiva.getFechaFiscalizacion().getTime());
        }
        this.setIdSolicitudPreventivaAgrupadora(solicitudPreventiva.getIdSolicitudPreventivaAgrupadora()!= null ? solicitudPreventiva.getIdSolicitudPreventivaAgrupadora() : null);
        this.setEstadoActualCartera(solicitudPreventiva.getEstadoActualCartera()!= null ? solicitudPreventiva.getEstadoActualCartera() : null);
        this.setValorPromedioAportes(solicitudPreventiva.getValorPromedioAportes()!= null ? solicitudPreventiva.getValorPromedioAportes() :null);
        this.setTrabajadoresActivos(solicitudPreventiva.getTrabajadoresActivos() != null ? solicitudPreventiva.getTrabajadoresActivos() :null);
        this.setCantidadVecesMoroso(solicitudPreventiva.getCantidadVecesMoroso()!= null ? solicitudPreventiva.getCantidadVecesMoroso():null);
        if(solicitudPreventiva.getFechaLimitePago()!= null){
            this.setFechaLimitePago(solicitudPreventiva.getFechaLimitePago().getTime());
        }
    }

    /**
     * Método encargado de actualiar un solicitud preventiva
     * @param solicitudActualizar,
     *        solicitud sobre la cual se realizara la actualizacion
     * @param solicitudNueva,
     *        solicitud preventiva sobre la cual se realizara la actualizacion
     * @return retorna la solicitud preventiva actualizada
     */
    public SolicitudPreventiva actualizarSolicitudPreventiva(SolicitudPreventiva solicitudActualizar,
            SolicitudPreventivaModeloDTO solicitudNueva) {
        if (solicitudActualizar != null && solicitudNueva != null) {
            if (solicitudNueva.getIdPersona() != null) {
                solicitudActualizar.setIdPersona(solicitudNueva.getIdPersona());
            }
            if (solicitudNueva.getTipoSolicitante() != null) {
                solicitudActualizar.setTipoSolicitante(solicitudNueva.getTipoSolicitante());
            }
            if (solicitudNueva.getEstadoSolicitudPreventiva() != null) {
                solicitudActualizar.setEstadoSolicitudPreventiva(solicitudNueva.getEstadoSolicitudPreventiva());
            }
            if (solicitudNueva.getRequiereFiscalizacion() != null) {
                solicitudActualizar.setRequiereFiscalizacion(solicitudNueva.getRequiereFiscalizacion());
            }
            if (solicitudNueva.getActualizacionEfectiva() != null) {
                solicitudActualizar.setActualizacionEfectiva(solicitudNueva.getActualizacionEfectiva());
            }
            if (solicitudNueva.getContactoEfectivo() != null) {
                solicitudActualizar.setContactoEfectivo(solicitudNueva.getContactoEfectivo());
            }
            if (solicitudNueva.getBackActualizacion() != null) {
                solicitudActualizar.setBackActualizacion(solicitudNueva.getBackActualizacion());
            }
            if (solicitudNueva.getTipoGestionCartera() != null) {
                solicitudActualizar.setTipoGestionCartera(solicitudNueva.getTipoGestionCartera());
            }
            if (solicitudNueva.getFechaFiscalizacion() != null) {
                solicitudActualizar.setFechaFiscalizacion(new Date(solicitudNueva.getFechaFiscalizacion()));
            }
            
            if (solicitudNueva.getIdSolicitudPreventivaAgrupadora() != null) {
                solicitudActualizar.setIdSolicitudPreventivaAgrupadora(solicitudNueva.getIdSolicitudPreventivaAgrupadora());
            }
            
            if (solicitudActualizar.getEstadoActualCartera() != null) {
                solicitudActualizar.setEstadoActualCartera(solicitudNueva.getEstadoActualCartera());
            }
            
            if (solicitudActualizar.getValorPromedioAportes() != null) {
                solicitudActualizar.setValorPromedioAportes(solicitudNueva.getValorPromedioAportes());
            }
            
            if (solicitudActualizar.getTrabajadoresActivos() != null) {
                solicitudActualizar.setTrabajadoresActivos(solicitudNueva.getTrabajadoresActivos());
            }
            
            if (solicitudActualizar.getCantidadVecesMoroso() != null) {
                solicitudActualizar.setCantidadVecesMoroso(solicitudNueva.getCantidadVecesMoroso());
            }
            
            if (solicitudNueva.getFechaLimitePago() != null) {
                solicitudActualizar.setFechaLimitePago(new Date(solicitudNueva.getFechaLimitePago()));
            }
            
            Solicitud solicitudGlobal = this.copyDTOToEntiy(solicitudActualizar.getSolicitudGlobal());
            solicitudActualizar.setSolicitudGlobal(solicitudGlobal);
        }
        return solicitudActualizar;
    }

    /**
     * Método que retorna el valor de idSolicitudPreventiva.
     * @return valor de idSolicitudPreventiva.
     */
    public Long getIdSolicitudPreventiva() {
        return idSolicitudPreventiva;
    }

    /**
     * Método encargado de modificar el valor de idSolicitudPreventiva.
     * @param valor
     *        para modificar idSolicitudPreventiva.
     */
    public void setIdSolicitudPreventiva(Long idSolicitudPreventiva) {
        this.idSolicitudPreventiva = idSolicitudPreventiva;
    }

    /**
     * Método que retorna el valor de idPersona.
     * @return valor de idPersona.
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * Método encargado de modificar el valor de idPersona.
     * @param valor
     *        para modificar idPersona.
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * Método que retorna el valor de tipoSolicitante.
     * @return valor de tipoSolicitante.
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * Método encargado de modificar el valor de tipoSolicitante.
     * @param valor
     *        para modificar tipoSolicitante.
     */
    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * @return the estadoSolicitudPreventiva
     */
    public EstadoSolicitudPreventivaEnum getEstadoSolicitudPreventiva() {
        return estadoSolicitudPreventiva;
    }

    /**
     * @param estadoSolicitudPreventiva
     *        the estadoSolicitudPreventiva to set
     */
    public void setEstadoSolicitudPreventiva(EstadoSolicitudPreventivaEnum estadoSolicitudPreventiva) {
        this.estadoSolicitudPreventiva = estadoSolicitudPreventiva;
    }

    /**
     * Método que retorna el valor de requiereFiscalizacion.
     * @return valor de requiereFiscalizacion.
     */
    public Boolean getRequiereFiscalizacion() {
        return requiereFiscalizacion;
    }

    /**
     * Método encargado de modificar el valor de requiereFiscalizacion.
     * @param valor
     *        para modificar requiereFiscalizacion.
     */
    public void setRequiereFiscalizacion(Boolean requiereFiscalizacion) {
        this.requiereFiscalizacion = requiereFiscalizacion;
    }

    /**
     * Método que retorna el valor de actualizacionEfectiva.
     * @return valor de actualizacionEfectiva.
     */
    public Boolean getActualizacionEfectiva() {
        return actualizacionEfectiva;
    }

    /**
     * Método encargado de modificar el valor de actualizacionEfectiva.
     * @param valor
     *        para modificar actualizacionEfectiva.
     */
    public void setActualizacionEfectiva(Boolean actualizacionEfectiva) {
        this.actualizacionEfectiva = actualizacionEfectiva;
    }

    /**
     * @return the contactoEfectivo
     */
    public Boolean getContactoEfectivo() {
        return contactoEfectivo;
    }

    /**
     * @param contactoEfectivo
     *        the contactoEfectivo to set
     */
    public void setContactoEfectivo(Boolean contactoEfectivo) {
        this.contactoEfectivo = contactoEfectivo;
    }

    /**
     * Método que retorna el valor de backActualizacion.
     * @return valor de backActualizacion.
     */
    public String getBackActualizacion() {
        return backActualizacion;
    }

    /**
     * Método encargado de modificar el valor de backActualizacion.
     * @param valor
     *        para modificar backActualizacion.
     */
    public void setBackActualizacion(String backActualizacion) {
        this.backActualizacion = backActualizacion;
    }

    /**
     * Método que retorna el valor de tipoGestionCartera.
     * @return valor de tipoGestionCartera.
     */
    public TipoGestionCarteraEnum getTipoGestionCartera() {
        return tipoGestionCartera;
    }

    /**
     * Método encargado de modificar el valor de tipoGestionCartera.
     * @param valor
     *        para modificar tipoGestionCartera.
     */
    public void setTipoGestionCartera(TipoGestionCarteraEnum tipoGestionCartera) {
        this.tipoGestionCartera = tipoGestionCartera;
    }

    /**
     * Método que retorna el valor de fechaFiscalizacion.
     * @return valor de fechaFiscalizacion.
     */
    public Long getFechaFiscalizacion() {
        return fechaFiscalizacion;
    }

    /**
     * Método encargado de modificar el valor de fechaFiscalizacion.
     * @param valor para modificar fechaFiscalizacion.
     */
    public void setFechaFiscalizacion(Long fechaFiscalizacion) {
        this.fechaFiscalizacion = fechaFiscalizacion;
    }

    /**
     * Método que retorna el valor de idSolicitudPreventivaAgrupadora.
     * @return valor de idSolicitudPreventivaAgrupadora.
     */
    public Long getIdSolicitudPreventivaAgrupadora() {
        return idSolicitudPreventivaAgrupadora;
    }

    /**
     * Método encargado de modificar el valor de idSolicitudPreventivaAgrupadora.
     * @param valor para modificar idSolicitudPreventivaAgrupadora.
     */
    public void setIdSolicitudPreventivaAgrupadora(Long idSolicitudPreventivaAgrupadora) {
        this.idSolicitudPreventivaAgrupadora = idSolicitudPreventivaAgrupadora;
    }

    /**
     * Método que retorna el valor de estadoActualCartera.
     * @return valor de estadoActualCartera.
     */
    public EstadoCarteraEnum getEstadoActualCartera() {
        return estadoActualCartera;
    }

    /**
     * Método que retorna el valor de valorPromedioAportes.
     * @return valor de valorPromedioAportes.
     */
    public BigDecimal getValorPromedioAportes() {
        return valorPromedioAportes;
    }

    /**
     * Método que retorna el valor de trabajadoresActivos.
     * @return valor de trabajadoresActivos.
     */
    public Short getTrabajadoresActivos() {
        return trabajadoresActivos;
    }

    /**
     * Método que retorna el valor de cantidadVecesMoroso.
     * @return valor de cantidadVecesMoroso.
     */
    public Short getCantidadVecesMoroso() {
        return cantidadVecesMoroso;
    }

    /**
     * Método encargado de modificar el valor de estadoActualCartera.
     * @param valor para modificar estadoActualCartera.
     */
    public void setEstadoActualCartera(EstadoCarteraEnum estadoActualCartera) {
        this.estadoActualCartera = estadoActualCartera;
    }

    /**
     * Método encargado de modificar el valor de valorPromedioAportes.
     * @param valor para modificar valorPromedioAportes.
     */
    public void setValorPromedioAportes(BigDecimal valorPromedioAportes) {
        this.valorPromedioAportes = valorPromedioAportes;
    }

    /**
     * Método encargado de modificar el valor de trabajadoresActivos.
     * @param valor para modificar trabajadoresActivos.
     */
    public void setTrabajadoresActivos(Short trabajadoresActivos) {
        this.trabajadoresActivos = trabajadoresActivos;
    }

    /**
     * Método encargado de modificar el valor de cantidadVecesMoroso.
     * @param valor para modificar cantidadVecesMoroso.
     */
    public void setCantidadVecesMoroso(Short cantidadVecesMoroso) {
        this.cantidadVecesMoroso = cantidadVecesMoroso;
    }

    /**
     * Método que retorna el valor de fechaLimitePago.
     * @return valor de fechaLimitePago.
     */
    public Long getFechaLimitePago() {
        return fechaLimitePago;
    }

    /**
     * Método encargado de modificar el valor de fechaLimitePago.
     * @param valor para modificar fechaLimitePago.
     */
    public void setFechaLimitePago(Long fechaLimitePago) {
        this.fechaLimitePago = fechaLimitePago;
    }

}