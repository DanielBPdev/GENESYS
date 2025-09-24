package com.asopagos.notificaciones.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.core.TipoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> DTO que representa parametroa adicionales para formar la plantilla del
 * comunicado <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@XmlRootElement
public class ParametrosComunicadoDTO implements Serializable {
	
	/** constante tipo de identificación */
	public static final String TIPO_IDENTIFICACION = "tipoIdentificacion";
	/** constante número de identificación */
	public static final String NUMERO_IDENTIFICACION = "numeroIdentificacion";
	/** constante número de Radicación */
	public static final String NUMERO_RADICACION = "numeroRadicacion";
	/** constante número de identificador de la persona */
	public static final String ID_PERSONA = "idPersona";
	
	/**
     * Serial version ID
     */
    private static final long serialVersionUID = -3074553591739029478L;

    /**
     * Descripción del tipo de identificación de la persona
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * Número de identificación de la persona
     */
    private String numeroIdentificacion;

    /**
     * Lista de ids de solicitud
     */
    private List<Long> idsSolicitud;

    /**
     * Parametro a resolver en el cuerpo del mensaje
     */
    private Map<String, Object> params = null;

    /**
     * Identificador de la entidad Cartera
     */
    private Long idCartera;

    /**
     * <b>Historia de Usuario:</b>HU-31-202 Retiro por Ventanilla
     * Indica el identificador transacción tercero pagador para agrupar los abonos objeto de un retiro.
     * corresponde con el campo código autorización que viene en el archivo de consumos
     */
    private String idTransaccionTerceroPagador;

    /**
     * <b>Historia de Usuario:</b>HU-31-202 Retiro por Ventanilla
     * Indica el identificador de respuesta de un intento de retiro de subsidio monetario (numero de comprobante)
     */
    private String identificadorRespuesta;

    /**
     * <b>Historia de Usuario:</b>ANEXO-Validacion y cargue archivo consumos_validado V.2
     * Indica el identificador del archivo de consumos ANIBOL cargado
     */
    private String idArchivoConsumosAnibol;
    
    /**
     * Indica el número de radicado de la solicitud
     */
    private String numeroRadicacion;
    
    /**
     * Identificador de la persona a enviar el correo
     */
    private Long idPersona;
    
    /**
     * Tipo solicitante al que se enviará el correo
     */
    private TipoTipoSolicitanteEnum tipoSolicitante;

    /**
     * identificador de la planilla comunicado
     */
    private Long idPlantillaComunicado;
    
    
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
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion
     *        the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * Método que retorna el valor de idsSolicitud.
     * @return valor de idsSolicitud.
     */
    public List<Long> getIdsSolicitud() {
        return idsSolicitud;
    }

    /**
     * Método encargado de modificar el valor de idsSolicitud.
     * @param valor
     *        para modificar idsSolicitud.
     */
    public void setIdsSolicitud(List<Long> idsSolicitud) {
        this.idsSolicitud = idsSolicitud;
    }

    /**
     * @return the params
     */
    public Map<String, Object> getParams() {
        return params;
    }

    /**
     * @param params
     *        the params to set
     */
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    /**
     * @return the idCartera
     */
    public Long getIdCartera() {
        return idCartera;
    }

    /**
     * @param idCartera
     *        the idCartera to set
     */
    public void setIdCartera(Long idCartera) {
        this.idCartera = idCartera;
    }

    /**
     * @return the idTransaccionTerceroPagador
     */
    public String getIdTransaccionTerceroPagador() {
        return idTransaccionTerceroPagador;
    }

    /**
     * @param idTransaccionTerceroPagador
     *        the idTransaccionTerceroPagador to set
     */
    public void setIdTransaccionTerceroPagador(String idTransaccionTerceroPagador) {
        this.idTransaccionTerceroPagador = idTransaccionTerceroPagador;
    }

    /**
     * @return the identificadorRespuesta
     */
    public String getIdentificadorRespuesta() {
        return identificadorRespuesta;
    }

    /**
     * @param identificadorRespuesta
     *        the identificadorRespuesta to set
     */
    public void setIdentificadorRespuesta(String identificadorRespuesta) {
        this.identificadorRespuesta = identificadorRespuesta;
    }

    /**
     * @return the idArchivoConsumosAnibol
     */
    public String getIdArchivoConsumosAnibol() {
        return idArchivoConsumosAnibol;
    }

    /**
     * @param idArchivoConsumosAnibol the idArchivoConsumosAnibol to set
     */
    public void setIdArchivoConsumosAnibol(String idArchivoConsumosAnibol) {
        this.idArchivoConsumosAnibol = idArchivoConsumosAnibol;
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
     * @return the tipoSolicitante
     */
    public TipoTipoSolicitanteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * @param tipoSolicitante the tipoSolicitante to set
     */
    public void setTipoSolicitante(TipoTipoSolicitanteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    public Long getIdPlantillaComunicado() {
        return idPlantillaComunicado;
    }

    public void setIdPlantillaComunicado(Long idPlantillaComunicado) {
        this.idPlantillaComunicado = idPlantillaComunicado;
    }
	
}
