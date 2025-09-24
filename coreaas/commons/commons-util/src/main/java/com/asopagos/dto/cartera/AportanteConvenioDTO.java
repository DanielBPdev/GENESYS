package com.asopagos.dto.cartera;

import java.io.Serializable;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.cartera.EstadoConvenioPagoEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO que representa los datos del aportante al cual se pretende realizar un convenio de pago 
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class AportanteConvenioDTO implements Serializable {

    /**
     * Tipo de identificacion del aportante
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    
    /**
     * Número de identificación del aportante
     */
    private String numeroIdentificacion;
    
    /**
     * Tipo de solicitante EMPLEADOR, INDEPENDIENTE, PENSIONADO
     */
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
    
    /**
     * Nombre completo del aportante o razón social del empleador
     */
    private String nombreRazonSocial;
    
    /**
     * Primer nombre del aportante
     */
    private String primerNombre;
    
    /**
     * Segundo Nombre del aportante
     */
    private String segundoNombre;
    
    /**
     * Primer apellido del aportante
     */
    private String primerApellido;
    
    /**
     * Segundo apellido del aportante
     */
    private String segundoApellido;
    
    /**
     * Estado del aportante respecto a la caja de compensación
     */
    private EstadoAfiliadoEnum estadoCCF;
    
    /**
     * Estado del aprtante en cartera
     */
    private EstadoCarteraEnum estadoCartera;
    
    /**
     * Fecha de registro para el convenio de pago (Este atributo aplica para la consulta de convenios de pago "Ver", "Anular")
     */
    private Long fechaRegistro;
    
    /**
     * Número de registro que es el mismo identificador del convenio de pago (Este atributo aplica para la consulta de convenios de pago "Ver", "Anular")
     */
    private Long numeroRegistro;
    
    /**
     * Estado del convenio de pago (Este atributo aplica para la consulta de convenios de pago "Ver", "Anular")
     */
    private EstadoConvenioPagoEnum estadoConvenio;
    
    /**
     * Identificador de la persona como aportante
     */
    private Long idPersona;
    
    /**
     * Método constructor
     */
    public AportanteConvenioDTO() {
    }
    
    /**
     * 
     * @param persona
     * @param estadoEmpleador
     */
    public AportanteConvenioDTO(Persona persona) {
        this.idPersona = persona.getIdPersona();
        this.tipoIdentificacion = persona.getTipoIdentificacion();
        this.numeroIdentificacion = persona.getNumeroIdentificacion();
        if(persona.getRazonSocial()==null){
            StringBuilder nombre = new StringBuilder();
            nombre.append(persona.getPrimerNombre() + " ");
            nombre.append(persona.getSegundoNombre() != null ? persona.getSegundoNombre()+ " " : "");
            nombre.append(persona.getPrimerApellido() + " ");
            nombre.append(persona.getSegundoApellido() != null ? persona.getSegundoApellido() : "");
            this.setNombreRazonSocial(nombre.toString());
        } else {
            this.nombreRazonSocial = persona.getRazonSocial();
        }
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
     * @return the tipoSolicitante
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * @param tipoSolicitante the tipoSolicitante to set
     */
    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * @return the nombreRazonSocial
     */
    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    /**
     * @param nombreRazonSocial the nombreRazonSocial to set
     */
    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
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
     * @return the estadoCCF
     */
    public EstadoAfiliadoEnum getEstadoCCF() {
        return estadoCCF;
    }

    /**
     * @param estadoCCF the estadoCCF to set
     */
    public void setEstadoCCF(EstadoAfiliadoEnum estadoCCF) {
        this.estadoCCF = estadoCCF;
    }


    /**
     * @return the estadoCartera
     */
    public EstadoCarteraEnum getEstadoCartera() {
        return estadoCartera;
    }


    /**
     * @param estadoCartera the estadoCartera to set
     */
    public void setEstadoCartera(EstadoCarteraEnum estadoCartera) {
        this.estadoCartera = estadoCartera;
    }

    /**
     * @return the fechaRegistro
     */
    public Long getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Long fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return the numeroRegistro
     */
    public Long getNumeroRegistro() {
        return numeroRegistro;
    }

    /**
     * @param numeroRegistro the numeroRegistro to set
     */
    public void setNumeroRegistro(Long numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    /**
     * @return the estadoConvenio
     */
    public EstadoConvenioPagoEnum getEstadoConvenio() {
        return estadoConvenio;
    }

    /**
     * @param estadoConvenio the estadoConvenio to set
     */
    public void setEstadoConvenio(EstadoConvenioPagoEnum estadoConvenio) {
        this.estadoConvenio = estadoConvenio;
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
    
}
