package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.cartera.CicloAportante;
import com.asopagos.entidades.ccf.cartera.SolicitudFiscalizacion;
import com.asopagos.entidades.ccf.cartera.SolicitudGestionCobroManual;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.cartera.EstadoFiscalizacionEnum;
import com.asopagos.enumeraciones.cartera.EstadoSolicitudPreventivaEnum;
import com.asopagos.enumeraciones.cartera.TipoGestionCarteraEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;

/**
 * DTO que contiene los datos después de ejecutada una simulación.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 * @updated 29-sept.-2017 2:35:50 p.m.
 */
@XmlRootElement
public class SimulacionDTO implements Serializable {

    /**
     * Serial de la versión UID
     */
    private static final long serialVersionUID = 8601491906127665890L;
    /**
     * Tipo de identificación.
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    /**
     * Número de identificación.
     */
    private String numeroIdentificacion;
    /**
     * Nombre completo o razón social.
     */
    private String nombreRazonSocial;
    /**
     * Correo electronico.
     */
    private String correoElectronico;
    /**
     * Estado actual de la cartera.
     */
    private EstadoCarteraEnum estadoActualCartera;
    /**
     * Valor promedio de los aportes.
     */
    private BigDecimal valorPromedioAportes;
    /**
     * Cantidad de trabajadores activos
     */
    private Integer trabajadoresActivos;
    /**
     * Cantidad de veces en estado moroso.
     */
    private Short cantidadVecesMoroso;
    /**
     * Tipo de gestion preventiva.
     */
    private TipoGestionCarteraEnum tipoGestionPreventiva;
    /**
     * Nombre del usuario a asignar la tarea.
     */
    private String usuarioAsignacion;
    /**
     * Tipo de aportante/solicitante.
     */
    private TipoSolicitanteMovimientoAporteEnum tipoAportante;

    /**
     * Nombre de usuario del analista (debe quedar en la solicitud)
     */
    private String usuarioAnalista;
    /**
     * Ciclo de fiscalizacion
     */
    private EstadoFiscalizacionEnum estadoFiscalizacion;
    /**
     * Estado del afiliado si tipoAportante es persona
     */
    private EstadoAfiliadoEnum estadoAfiliado;
    /**
     * Identificador de la instancia proceso
     */
    private String idInstanciaProceso;
    /**
     * Estado del empleador
     */
    private EstadoEmpleadorEnum estadoEmpleador;
    /**
     * Identificador del ciclo de aporte
     */
    private Long idCicloAportante;
    /**
     * Número de operación (Equivale al número de radicado de la solicitud)
     */
    private String numeroOperacion;
    /**
     * Identificador de la solicitud global
     */
    private Long idSolicitudGlobal;
    
    /**
     * Atributo que indica la fecha de limite de pago.
     */
    private Long fechaLimitePago;
    
    /**
     * Estado de la solicitud preventiva.
     */
    private EstadoSolicitudPreventivaEnum estadoSolicitudPreventiva;
      /**
     * Estado de linea de cobro.
     */
    private TipoLineaCobroEnum tipoLineaCobro;
    /**
     * 
     * @param persona
     * @param estadoEmpleador
     */
    public SimulacionDTO(Persona persona, EstadoEmpleadorEnum estadoEmpleador) {
        super();
        this.tipoIdentificacion = persona.getTipoIdentificacion();
        this.numeroIdentificacion = persona.getNumeroIdentificacion();
        this.nombreRazonSocial = persona.getRazonSocial();
        this.correoElectronico = persona.getUbicacionPrincipal() != null ? persona.getUbicacionPrincipal().getEmail() : null;
        this.estadoEmpleador = estadoEmpleador;
    }

    /**
     * 
     * @param persona
     */
    public SimulacionDTO(Persona persona) {
        super();
        this.tipoIdentificacion = persona.getTipoIdentificacion();
        this.numeroIdentificacion = persona.getNumeroIdentificacion();
        this.nombreRazonSocial = persona.getRazonSocial();
        this.correoElectronico = persona.getUbicacionPrincipal() != null ? persona.getUbicacionPrincipal().getEmail() : null;
    }

    public SimulacionDTO() {
    }

    /**
     * Método constructor
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param nombreRazonSocial
     * @param usuarioAnalista
     * @param cicloAportante
     * @param solicitudFiscalizacion
     */
    public SimulacionDTO(EstadoEmpleadorEnum estadoEmpleador, Persona persona, CicloAportante cicloAportante,
            SolicitudFiscalizacion solicitudFiscalizacion) {
        super();
        this.tipoIdentificacion = persona.getTipoIdentificacion();
        //TODO jusanchez Se debe de modificar los estado, por definir con atoro 
        this.estadoActualCartera = EstadoCarteraEnum.AL_DIA;
        this.estadoEmpleador = estadoEmpleador;
        this.numeroIdentificacion = persona.getNumeroIdentificacion();
        this.nombreRazonSocial = persona.getRazonSocial();
        this.usuarioAnalista = solicitudFiscalizacion.getSolicitudGlobal().getDestinatario();
        this.estadoFiscalizacion = solicitudFiscalizacion.getEstadoFiscalizacion();
        this.tipoAportante = cicloAportante.getTipoSolicitanteMovimientoAporteEnum();
        this.idInstanciaProceso = solicitudFiscalizacion.getSolicitudGlobal().getIdInstanciaProceso();
        this.numeroOperacion = solicitudFiscalizacion.getSolicitudGlobal().getNumeroRadicacion();
        this.idSolicitudGlobal = solicitudFiscalizacion.getSolicitudGlobal().getIdSolicitud();
    }
    
    /**
     * 
     * @param estadoEmpleador
     * @param persona
     * @param cicloAportante
     * @param solicitudGestionCobro
     */
    public SimulacionDTO(EstadoEmpleadorEnum estadoEmpleador, Persona persona, CicloAportante cicloAportante,
            SolicitudGestionCobroManual solicitudGestionCobro) {
        super();
        this.tipoIdentificacion = persona.getTipoIdentificacion();
        //TODO jusanchez Se debe de modificar los estado, por definir con atoro 
        this.estadoActualCartera = EstadoCarteraEnum.AL_DIA;
        this.estadoEmpleador = estadoEmpleador; 
        this.numeroIdentificacion = persona.getNumeroIdentificacion();
        this.nombreRazonSocial = persona.getRazonSocial();
        this.usuarioAnalista = solicitudGestionCobro.getSolicitudGlobal().getDestinatario();
        this.estadoFiscalizacion = solicitudGestionCobro.getEstadoSolicitud();
        this.tipoAportante = cicloAportante.getTipoSolicitanteMovimientoAporteEnum();
        this.idInstanciaProceso = solicitudGestionCobro.getSolicitudGlobal().getIdInstanciaProceso();
        this.numeroOperacion = solicitudGestionCobro.getSolicitudGlobal().getNumeroRadicacion();
        this.idSolicitudGlobal = solicitudGestionCobro.getSolicitudGlobal().getIdSolicitud();
    }

    /**
     * Método que retorna el valor de tipoIdentificacion.
     * @return valor de tipoIdentificacion.
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacion.
     * @param valor
     *        para modificar tipoIdentificacion.
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * Método que retorna el valor de numeroIdentificacion.
     * @return valor de numeroIdentificacion.
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacion.
     * @param valor
     *        para modificar numeroIdentificacion.
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * Método que retorna el valor de nombreRazonSocial.
     * @return valor de nombreRazonSocial.
     */
    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    /**
     * Método encargado de modificar el valor de nombreRazonSocial.
     * @param valor
     *        para modificar nombreRazonSocial.
     */
    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    /**
     * Método que retorna el valor de correoElectronico.
     * @return valor de correoElectronico.
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Método encargado de modificar el valor de correoElectronico.
     * @param valor
     *        para modificar correoElectronico.
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * Método que retorna el valor de estadoActualCartera.
     * @return valor de estadoActualCartera.
     */
    public EstadoCarteraEnum getEstadoActualCartera() {
        return estadoActualCartera;
    }

    /**
     * Método encargado de modificar el valor de estadoActualCartera.
     * @param valor
     *        para modificar estadoActualCartera.
     */
    public void setEstadoActualCartera(EstadoCarteraEnum estadoActualCartera) {
        this.estadoActualCartera = estadoActualCartera;
    }

    /**
     * Método que retorna el valor de valorPromedioAportes.
     * @return valor de valorPromedioAportes.
     */
    public BigDecimal getValorPromedioAportes() {
        return valorPromedioAportes;
    }

    /**
     * Método encargado de modificar el valor de valorPromedioAportes.
     * @param valor
     *        para modificar valorPromedioAportes.
     */
    public void setValorPromedioAportes(BigDecimal valorPromedioAportes) {
        this.valorPromedioAportes = valorPromedioAportes;
    }

    /**
     * Método que retorna el valor de trabajadoresActivos.
     * @return valor de trabajadoresActivos.
     */
    public Integer getTrabajadoresActivos() {
        return trabajadoresActivos;
    }

    /**
     * Método encargado de modificar el valor de trabajadoresActivos.
     * @param valor
     *        para modificar trabajadoresActivos.
     */
    public void setTrabajadoresActivos(Integer trabajadoresActivos) {
        this.trabajadoresActivos = trabajadoresActivos;
    }

    /**
     * Método que retorna el valor de cantidadVecesMoroso.
     * @return valor de cantidadVecesMoroso.
     */
    public Short getCantidadVecesMoroso() {
        return cantidadVecesMoroso;
    }

    /**
     * Método encargado de modificar el valor de cantidadVecesMoroso.
     * @param valor
     *        para modificar cantidadVecesMoroso.
     */
    public void setCantidadVecesMoroso(Short cantidadVecesMoroso) {
        this.cantidadVecesMoroso = cantidadVecesMoroso;
    }

    /**
     * @return the tipoGestionPreventiva
     */
    public TipoGestionCarteraEnum getTipoGestionPreventiva() {
        return tipoGestionPreventiva;
    }

    /**
     * @param tipoGestionPreventiva
     *        the tipoGestionPreventiva to set
     */
    public void setTipoGestionPreventiva(TipoGestionCarteraEnum tipoGestionPreventiva) {
        this.tipoGestionPreventiva = tipoGestionPreventiva;
    }

    /**
     * Método que retorna el valor de usuarioAsignacion.
     * @return valor de usuarioAsignacion.
     */
    public String getUsuarioAsignacion() {
        return usuarioAsignacion;
    }

    /**
     * Método encargado de modificar el valor de usuarioAsignacion.
     * @param valor
     *        para modificar usuarioAsignacion.
     */
    public void setUsuarioAsignacion(String usuarioAsignacion) {
        this.usuarioAsignacion = usuarioAsignacion;
    }

    /**
     * Método que retorna el valor de tipoAportante.
     * @return valor de tipoAportante.
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoAportante() {
        return tipoAportante;
    }

    /**
     * Método encargado de modificar el valor de tipoAportante.
     * @param valor
     *        para modificar tipoAportante.
     */
    public void setTipoAportante(TipoSolicitanteMovimientoAporteEnum tipoAportante) {
        this.tipoAportante = tipoAportante;
    }

    /**
     * Método que retorna el valor de usuarioAnalista.
     * @return valor de usuarioAnalista.
     */
    public String getUsuarioAnalista() {
        return usuarioAnalista;
    }

    /**
     * Método encargado de modificar el valor de usuarioAnalista.
     * @param valor
     *        para modificar usuarioAnalista.
     */
    public void setUsuarioAnalista(String usuarioAnalista) {
        this.usuarioAnalista = usuarioAnalista;
    }

    /**
     * Método que retorna el valor de estadoFiscalizacion.
     * @return valor de estadoFiscalizacion.
     */
    public EstadoFiscalizacionEnum getEstadoFiscalizacion() {
        return estadoFiscalizacion;
    }

    /**
     * Método que retorna el valor de estadoAfiliado.
     * @return valor de estadoAfiliado.
     */
    public EstadoAfiliadoEnum getEstadoAfiliado() {
        return estadoAfiliado;
    }

    /**
     * Método encargado de modificar el valor de estadoFiscalizacion.
     * @param valor
     *        para modificar estadoFiscalizacion.
     */
    public void setEstadoFiscalizacion(EstadoFiscalizacionEnum estadoFiscalizacion) {
        this.estadoFiscalizacion = estadoFiscalizacion;
    }

    /**
     * Método encargado de modificar el valor de estadoAfiliado.
     * @param valor
     *        para modificar estadoAfiliado.
     */
    public void setEstadoAfiliado(EstadoAfiliadoEnum estadoAfiliado) {
        this.estadoAfiliado = estadoAfiliado;
    }

    /**
     * Método que retorna el valor de idInstanciaProceso.
     * @return valor de idInstanciaProceso.
     */
    public String getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    /**
     * Método encargado de modificar el valor de idInstanciaProceso.
     * @param valor
     *        para modificar idInstanciaProceso.
     */
    public void setIdInstanciaProceso(String idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }

    /**
     * Método que retorna el valor de estadoEmpleador.
     * @return valor de estadoEmpleador.
     */
    public EstadoEmpleadorEnum getEstadoEmpleador() {
        return estadoEmpleador;
    }

    /**
     * Método encargado de modificar el valor de estadoEmpleador.
     * @param valor
     *        para modificar estadoEmpleador.
     */
    public void setEstadoEmpleador(EstadoEmpleadorEnum estadoEmpleador) {
        this.estadoEmpleador = estadoEmpleador;
    }

    /**
     * Método que retorna el valor de idCicloAportante.
     * @return valor de idCicloAportante.
     */
    public Long getIdCicloAportante() {
        return idCicloAportante;
    }

    /**
     * Método encargado de modificar el valor de idCicloAportante.
     * @param valor
     *        para modificar idCicloAportante.
     */
    public void setIdCicloAportante(Long idCicloAportante) {
        this.idCicloAportante = idCicloAportante;
    }

    /**
     * Método que retorna el valor de numeroOperacion.
     * @return valor de numeroOperacion.
     */
    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    /**
     * Método encargado de modificar el valor de numeroOperacion.
     * @param valor
     *        para modificar numeroOperacion.
     */
    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    /**
     * Método que retorna el valor de idSolicitudGlobal.
     * @return valor de idSolicitudGlobal.
     */
    public Long getIdSolicitudGlobal() {
        return idSolicitudGlobal;
    }

    /**
     * Método encargado de modificar el valor de idSolicitudGlobal.
     * @param valor
     *        para modificar idSolicitudGlobal.
     */
    public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
        this.idSolicitudGlobal = idSolicitudGlobal;
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

    /**
     * Método que retorna el valor de estadoSolicitudPreventiva.
     * @return valor de estadoSolicitudPreventiva.
     */
    public EstadoSolicitudPreventivaEnum getEstadoSolicitudPreventiva() {
        return estadoSolicitudPreventiva;
    }

    /**
     * Método encargado de modificar el valor de estadoSolicitudPreventiva.
     * @param valor para modificar estadoSolicitudPreventiva.
     */
    public void setEstadoSolicitudPreventiva(EstadoSolicitudPreventivaEnum estadoSolicitudPreventiva) {
        this.estadoSolicitudPreventiva = estadoSolicitudPreventiva;
    }    

        /**
     * Método que retorna el valor de estadoSolicitudPreventiva.
     * @return valor de tipoLineaCobro.
     */
    public TipoLineaCobroEnum getTipoLineaCobro() {
        return tipoLineaCobro;
    }

    /**
     * Método encargado de modificar el valor de estadoSolicitudPreventiva.
     * @param valor para modificar TipoLineaCobro
     */
    public void setTipoLineaCobro(TipoLineaCobroEnum tipoLineaCobro) {
        this.tipoLineaCobro = tipoLineaCobro;
    }  
}