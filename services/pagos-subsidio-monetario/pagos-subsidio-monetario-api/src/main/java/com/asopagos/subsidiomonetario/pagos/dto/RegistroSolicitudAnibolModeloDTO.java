package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.subsidiomonetario.pagos.RegistroSolicitudAnibol;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoSolicitudAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.TipoOperacionAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.MotivoAnulacionSubsidioAsignadoEnum;

/**
 * <b>Descripcion:</b> Clase DTO que representa el modelo del registro de solicitud hacía anibol <br/>
 * <b>Módulo:</b> Asopagos - HU - 31 - XXX<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> mosorio</a>
 */
@XmlRootElement
public class RegistroSolicitudAnibolModeloDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1787235096283329655L;

    /**
     * Identificador del registro de la solicitud de anibol
     */
    private Long idRegistroSolicitudAnibol;

    /**
     * Indica la fecha y hora de creación de registro de la solicitud
     */
    private Date fechaHoraRegistro;

    /**
     * Tipo de operación que se realiza al momento de registrar la solicitud
     */
    private TipoOperacionAnibolEnum tipoOperacionAnibol;

    /**
     * Parametros enviados al servicio de ANIBOL
     */
    private String parametrosIN;

    /**
     * Parametros de salida del servicio de ANIBOL
     */
    private String parametrosOUT;
    
    /**
     * id de proceso generado por anibol
     */
    private String idProceso;
    
    /**
     * Identificador de SolicitudLiquidacionSubsidio
     */
    private Long solicitudLiquidacionSubsidio;
    
    /**
     * Número de radicación
     */
    private String numeroRadicacion;
    
    /**
     * Estado de la solicitud frente a ANIBOL
     */
    private EstadoSolicitudAnibolEnum estadoSolicitudAnibol;

    /**
     * Motivo de anulacion que sera puesto en el motivo de anulacion de los
     * detalles una vez sea confirmada la anulacion por ANIBOL
     */
    private String motivoAnulacion;

    private Long idMedioDePagoDestino;

    private String parametrosTraslado;

    
    /**
     * Metodo que convierte el DTO en Entidad.
     * @return <code>RegistroSolicitudAnibol</code>
     *         Entidad del modelo RegistroSolicitudAnibol
     */
    public RegistroSolicitudAnibol convertToEntity(){
        
        RegistroSolicitudAnibol registroSolicitudAnibol = new RegistroSolicitudAnibol();
        registroSolicitudAnibol.setIdRegistroSolicitudAnibol(this.getIdRegistroSolicitudAnibol());
        registroSolicitudAnibol.setFechaHoraRegistro(this.getFechaHoraRegistro());
        registroSolicitudAnibol.setTipoOperacionAnibol(this.getTipoOperacionAnibol());
        registroSolicitudAnibol.setParametrosIN(this.getParametrosIN());
        registroSolicitudAnibol.setParametrosOUT(this.getParametrosOUT());
        registroSolicitudAnibol.setIdProceso(this.getIdProceso());
        registroSolicitudAnibol.setSolicitudLiquidacionSubsidio(this.getSolicitudLiquidacionSubsidio());
        registroSolicitudAnibol.setNumeroRadicacion(this.numeroRadicacion);
        registroSolicitudAnibol.setEstadoSolicitudAnibol(this.getEstadoSolicitudAnibol());
        registroSolicitudAnibol.setMotivoAnulacion(this.getMotivoAnulacion());
        registroSolicitudAnibol.setIdMedioDePagoDestino(this.getIdMedioDePagoDestino());
        registroSolicitudAnibol.setParametrosTraslado(this.getParametrosTraslado());

        return registroSolicitudAnibol;
    }

    /**
     * @return the idRegistroSolicitudAnibol
     */
    public Long getIdRegistroSolicitudAnibol() {
        return idRegistroSolicitudAnibol;
    }

    /**
     * @param idRegistroSolicitudAnibol
     *        the idRegistroSolicitudAnibol to set
     */
    public void setIdRegistroSolicitudAnibol(Long idRegistroSolicitudAnibol) {
        this.idRegistroSolicitudAnibol = idRegistroSolicitudAnibol;
    }

    /**
     * @return the fechaHoraRegistro
     */
    public Date getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    /**
     * @param fechaHoraRegistro
     *        the fechaHoraRegistro to set
     */
    public void setFechaHoraRegistro(Date fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }
    

    /**
     * @return the tipoOperacionAnibol
     */
    public TipoOperacionAnibolEnum getTipoOperacionAnibol() {
        return tipoOperacionAnibol;
    }

    /**
     * @param tipoOperacionAnibol
     *        the tipoOperacionAnibol to set
     */
    public void setTipoOperacionAnibol(TipoOperacionAnibolEnum tipoOperacionAnibol) {
        this.tipoOperacionAnibol = tipoOperacionAnibol;
    }

    /**
     * @return the parametrosIN
     */
    public String getParametrosIN() {
        return parametrosIN;
    }

    /**
     * @param parametrosIN
     *        the parametrosIN to set
     */
    public void setParametrosIN(String parametrosIN) {
        this.parametrosIN = parametrosIN;
    }

    /**
     * @return the parametrosOUT
     */
    public String getParametrosOUT() {
        return parametrosOUT;
    }

    /**
     * @param parametrosOUT
     *        the parametrosOUT to set
     */
    public void setParametrosOUT(String parametrosOUT) {
        this.parametrosOUT = parametrosOUT;
    }

    /**
     * @return the idProceso
     */
    public String getIdProceso() {
        return idProceso;
    }

    /**
     * @param idProceso the idProceso to set
     */
    public void setIdProceso(String idProceso) {
        this.idProceso = idProceso;
    }

    /**
     * @return the solicitudLiquidacionSubsidio
     */
    public Long getSolicitudLiquidacionSubsidio() {
        return solicitudLiquidacionSubsidio;
    }

    /**
     * @param solicitudLiquidacionSubsidio the solicitudLiquidacionSubsidio to set
     */
    public void setSolicitudLiquidacionSubsidio(Long solicitudLiquidacionSubsidio) {
        this.solicitudLiquidacionSubsidio = solicitudLiquidacionSubsidio;
    }

    /**
     * @return the numeroRadicacion
     */
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    /**
     * @param numeroRadicacion the numeroRadicacion to set
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

	/**
	 * @return the estadoSolicitudAnibol
	 */
	public EstadoSolicitudAnibolEnum getEstadoSolicitudAnibol() {
		return estadoSolicitudAnibol;
	}

	/**
	 * @param estadoSolicitudAnibol the estadoSolicitudAnibol to set
	 */
	public void setEstadoSolicitudAnibol(EstadoSolicitudAnibolEnum estadoSolicitudAnibol) {
		this.estadoSolicitudAnibol = estadoSolicitudAnibol;
	}

    /**
	 * @return the motivoAnulacion
	 */
    public String getMotivoAnulacion() {
        return this.motivoAnulacion;
    }

    /**
	 * @param motivoAnulacion the motivoAnulacion to set
	 */
    public void setMotivoAnulacion(String motivoAnulacion) {
        this.motivoAnulacion = motivoAnulacion;
    }


    public Long getIdMedioDePagoDestino() {
        return this.idMedioDePagoDestino;
    }

    public void setIdMedioDePagoDestino(Long idMedioDePagoDestino) {
        this.idMedioDePagoDestino = idMedioDePagoDestino;
    }

    public String getParametrosTraslado() {
        return this.parametrosTraslado;
    }

    public void setParametrosTraslado(String parametrosTraslado) {
        this.parametrosTraslado = parametrosTraslado;
    }


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegistroSolicitudAnibolModeloDTO [idRegistroSolicitudAnibol=");
		builder.append(idRegistroSolicitudAnibol);
		builder.append(", fechaHoraRegistro=");
		builder.append(fechaHoraRegistro);
		builder.append(", tipoOperacionAnibol=");
		builder.append(tipoOperacionAnibol);
		builder.append(", parametrosIN=");
		builder.append(parametrosIN);
		builder.append(", parametrosOUT=");
		builder.append(parametrosOUT);
		builder.append(", idProceso=");
		builder.append(idProceso);
		builder.append(", solicitudLiquidacionSubsidio=");
		builder.append(solicitudLiquidacionSubsidio);
		builder.append(", numeroRadicacion=");
		builder.append(numeroRadicacion);
		builder.append(", estadoSolicitudAnibol=");
		builder.append(estadoSolicitudAnibol);
        builder.append(", motivoAnulacion=");
		builder.append(motivoAnulacion);
		builder.append("]");
		return builder.toString();
	}
	
	
}
