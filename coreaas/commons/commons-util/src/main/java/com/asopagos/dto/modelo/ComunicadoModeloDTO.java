package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.comunicados.Comunicado;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import com.asopagos.enumeraciones.comunicados.EstadoEnvioComunicadoEnum;
import com.asopagos.enumeraciones.comunicados.MedioComunicadoEnum;

/**
 * Entidad que representa un comunicado <br/>
 * <b>Historia de Usuario: </b>TRA-331 Editar comunicado
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ComunicadoModeloDTO implements Serializable {
	
    /**
     * Código identificador de llave primaria del comunicado 
     */
    private Long idComunicado;
    
    /**
     * Referencia a la solicitud asoaciado al comunicado
     */ 
    private Long idSolicitud;
    
    /**
     * Descripción del texto a adicionar al comunicado
     */
    private String textoAdicionar;
    
    /**
     * Dirección de correo electrónico al que se envía el comunicado
     */
    private String email;
    
    /**
     * Referencia a la plantilla asoaciada al comunicado
     */
    private PlantillaComunicado plantillaComunicado;
    
    /**
     * Identificador del archivo asociado al comunicado
     */
    private String identificadorArchivoComunicado;
    
    /**
     * Fecha del comunicado
     */
    private Long fechaComunicado;   
    
    /**
     * Usuarios remitente del comunicado
     */
    private String remitente;
    
    /**
     * Código de la sede de la caja de compensación 
     */
    private String sedeCajaCompensacion;
    
    /**
     * Número de la notificación enviada asoaciada al comunicado 
     */
    private Long numeroCorreoMasivo;
    
    /**
     * Nombre del destinatario del comunicado
     */
    private String destinatario;
    
    /**
     * Descripción del estado del envío
     */
    private EstadoEnvioComunicadoEnum estadoEnvio;

    /**
     * Descripción del mensaje de envío
     */
    private String mensajeEnvio;
    
    /**
     * Descripción del medio de comunicación
     */
    private MedioComunicadoEnum medioComunicado;
    
   
    /**
     * Método encargado de convertir de Entidad a DTO.
     * @param solicitud entidad a convertir.
     */
    public void convertToDTO(Comunicado comunicado){
        this.setDestinatario(comunicado.getDestinatario());
        this.setEmail(comunicado.getEmail());
        this.setEstadoEnvio(comunicado.getEstadoEnvio());
        if(comunicado.getFechaComunicado()!=null){
            this.setFechaComunicado(comunicado.getFechaComunicado().getTime());
        }
        this.setIdComunicado(comunicado.getIdComunicado());
        this.setIdentificadorArchivoComunicado(comunicado.getIdentificadorArchivoComunicado());
        this.setIdSolicitud(comunicado.getIdSolicitud());
        this.setMedioComunicado(comunicado.getMedioComunicado());
        this.setMensajeEnvio(comunicado.getMensajeEnvio());
        this.setNumeroCorreoMasivo(comunicado.getNumeroCorreoMasivo());
        this.setPlantillaComunicado(comunicado.getPlantillaComunicado());
        this.setRemitente(comunicado.getRemitente());
        this.setSedeCajaCompensacion(comunicado.getSedeCajaCompensacion());
        this.setTextoAdicionar(comunicado.getTextoAdicionar());
      
    }
    
    /**
     * Método que retorna el valor de idComunicado.
     * @return valor de idComunicado.
     */
    public Long getIdComunicado() {
        return idComunicado;
    }

    /**
     * Método encargado de modificar el valor de idComunicado.
     * @param valor para modificar idComunicado.
     */
    public void setIdComunicado(Long idComunicado) {
        this.idComunicado = idComunicado;
    }

    /**
     * Método que retorna el valor de idSolicitud.
     * @return valor de idSolicitud.
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * Método encargado de modificar el valor de idSolicitud.
     * @param valor para modificar idSolicitud.
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * Método que retorna el valor de textoAdicionar.
     * @return valor de textoAdicionar.
     */
    public String getTextoAdicionar() {
        return textoAdicionar;
    }

    /**
     * Método encargado de modificar el valor de textoAdicionar.
     * @param valor para modificar textoAdicionar.
     */
    public void setTextoAdicionar(String textoAdicionar) {
        this.textoAdicionar = textoAdicionar;
    }

    /**
     * Método que retorna el valor de email.
     * @return valor de email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Método encargado de modificar el valor de email.
     * @param valor para modificar email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método que retorna el valor de plantillaComunicado.
     * @return valor de plantillaComunicado.
     */
    public PlantillaComunicado getPlantillaComunicado() {
        return plantillaComunicado;
    }

    /**
     * Método encargado de modificar el valor de plantillaComunicado.
     * @param valor para modificar plantillaComunicado.
     */
    public void setPlantillaComunicado(PlantillaComunicado plantillaComunicado) {
        this.plantillaComunicado = plantillaComunicado;
    }

    /**
     * Método que retorna el valor de identificadorArchivoComunicado.
     * @return valor de identificadorArchivoComunicado.
     */
    public String getIdentificadorArchivoComunicado() {
        return identificadorArchivoComunicado;
    }

    /**
     * Método encargado de modificar el valor de identificadorArchivoComunicado.
     * @param valor para modificar identificadorArchivoComunicado.
     */
    public void setIdentificadorArchivoComunicado(String identificadorArchivoComunicado) {
        this.identificadorArchivoComunicado = identificadorArchivoComunicado;
    }

    /**
     * Método que retorna el valor de fechaComunicado.
     * @return valor de fechaComunicado.
     */
    public Long getFechaComunicado() {
        return fechaComunicado;
    }

    /**
     * Método encargado de modificar el valor de fechaComunicado.
     * @param valor para modificar fechaComunicado.
     */
    public void setFechaComunicado(Long fechaComunicado) {
        this.fechaComunicado = fechaComunicado;
    }

    /**
     * Método que retorna el valor de remitente.
     * @return valor de remitente.
     */
    public String getRemitente() {
        return remitente;
    }

    /**
     * Método encargado de modificar el valor de remitente.
     * @param valor para modificar remitente.
     */
    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    /**
     * Método que retorna el valor de sedeCajaCompensacion.
     * @return valor de sedeCajaCompensacion.
     */
    public String getSedeCajaCompensacion() {
        return sedeCajaCompensacion;
    }

    /**
     * Método encargado de modificar el valor de sedeCajaCompensacion.
     * @param valor para modificar sedeCajaCompensacion.
     */
    public void setSedeCajaCompensacion(String sedeCajaCompensacion) {
        this.sedeCajaCompensacion = sedeCajaCompensacion;
    }

    /**
     * Método que retorna el valor de numeroCorreoMasivo.
     * @return valor de numeroCorreoMasivo.
     */
    public Long getNumeroCorreoMasivo() {
        return numeroCorreoMasivo;
    }

    /**
     * Método encargado de modificar el valor de numeroCorreoMasivo.
     * @param valor para modificar numeroCorreoMasivo.
     */
    public void setNumeroCorreoMasivo(Long numeroCorreoMasivo) {
        this.numeroCorreoMasivo = numeroCorreoMasivo;
    }

    /**
     * Método que retorna el valor de destinatario.
     * @return valor de destinatario.
     */
    public String getDestinatario() {
        return destinatario;
    }

    /**
     * Método encargado de modificar el valor de destinatario.
     * @param valor para modificar destinatario.
     */
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    /**
     * Método que retorna el valor de estadoEnvio.
     * @return valor de estadoEnvio.
     */
    public EstadoEnvioComunicadoEnum getEstadoEnvio() {
        return estadoEnvio;
    }

    /**
     * Método encargado de modificar el valor de estadoEnvio.
     * @param valor para modificar estadoEnvio.
     */
    public void setEstadoEnvio(EstadoEnvioComunicadoEnum estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    /**
     * Método que retorna el valor de mensajeEnvio.
     * @return valor de mensajeEnvio.
     */
    public String getMensajeEnvio() {
        return mensajeEnvio;
    }

    /**
     * Método encargado de modificar el valor de mensajeEnvio.
     * @param valor para modificar mensajeEnvio.
     */
    public void setMensajeEnvio(String mensajeEnvio) {
        this.mensajeEnvio = mensajeEnvio;
    }

    /**
     * Método que retorna el valor de medioComunicado.
     * @return valor de medioComunicado.
     */
    public MedioComunicadoEnum getMedioComunicado() {
        return medioComunicado;
    }

    /**
     * Método encargado de modificar el valor de medioComunicado.
     * @param valor para modificar medioComunicado.
     */
    public void setMedioComunicado(MedioComunicadoEnum medioComunicado) {
        this.medioComunicado = medioComunicado;
    }
}